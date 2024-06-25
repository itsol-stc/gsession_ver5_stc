package jp.groupsession.v2.ip;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.ip.model.IpkGrpModel;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] IP管理プラグインで共通使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public IpkBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public IpkBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] IP管理添付ファイル情報を元に添付ファイルを指定したテンポラリディレクトリに作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param netSid ネットワークSID
     * @param iadSid ＩＰアドレスSID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param now 現在日時
     * @param tempDsp 添付ファイル公開フラグ
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setIpkanriTempFileData(Connection con,
                                       int netSid,
                                       int iadSid,
                                       String appRoot,
                                       String tempDir,
                                       UDate now,
                                       int tempDsp,
                                       RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {

        //現在日付の文字列(YYYYMMDD)
        String dateStr = now.getDateString();
        IpkBinDao ipkDao = new IpkBinDao(con);
        CommonBiz cmnBiz = new CommonBiz();
        //IP管理バイナリ情報を取得
        List <IpkBinModel> ipkBinList = ipkDao.getIpkanriTmpFileList(netSid, iadSid, tempDsp);

        if (ipkBinList == null || ipkBinList.size() < 1) {
            return;
        }

        String[] binSids = new String[ipkBinList.size()];
        int i = 0;
        for (IpkBinModel mdl : ipkBinList) {
            binSids[i] = String.valueOf(mdl.getBinSid());
            i++;
        }

        //バイナリ情報を取得
        List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, reqMdl.getDomain());

        int fileNum = 1;
        for (CmnBinfModel binData : binList) {
            if (binData.getBinJkbn() == GSConst.JTKBN_DELETE) {
                continue;
            }

            cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
            fileNum++;
        }
        log__.debug("tempDir = " + tempDir);
    }

    /**
     * <br>[機  能] 添付情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param model IpkBinModel バイナリーデータ登録用モデル
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void insertIpkBin(Connection con,
                            MlCountMtController cntCon, IpkBinModel model)
    throws SQLException, IOException, IOToolsException, TempFileException {

        //IP管理添付情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        IpkBinDao binDao = new IpkBinDao(con);
        int iadSid = model.getIadSid();
        if (iadSid == 0) {
            iadSid = IpkConst.NETWORK_TOUROKU;
        }
        //変更の場合、バイナリー情報を削除する。
        if (model.getCmdMode().equals("netEdit") || model.getCmdMode().equals("iadEdit")) {
            //更新の場合はバイナリー情報の論理削除、IPアドレス添付情報の削除を行う
                binDao.removeIpkBinData(model.getNetSid(),
                        iadSid, model.getUsrSid(), model.getNow());
                binDao.delete(model.getNetSid(), iadSid);
        }

        //バイナリー情報の登録(公開)
        List < String > koukaiBinSidList
            = cmnBiz.insertBinInfo(con, model.getTempDir() + IpkConst.IPK_TEMP_KOUKAI,
                    model.getAppRootPath(), cntCon, model.getUsrSid(), model.getNow());

        //バイナリー情報の登録(非公開)
        List < String > hikoukaiBinSidList
            = cmnBiz.insertBinInfo(con, model.getTempDir() + IpkConst.IPK_TEMP_HIKOUKAI,
                    model.getAppRootPath(), cntCon, model.getUsrSid(), model.getNow());

        int newIadSid = model.getNewIadSid();
        if (newIadSid == 0) {
            newIadSid = IpkConst.NETWORK_TOUROKU;
        }

        int newNetSid = model.getNewNetSid();

        //添付ファイル情報を登録する。
        IpkBinModel binMdl = new IpkBinModel();
        binMdl.setNewNetSid(newNetSid);
        binMdl.setNewIadSid(newIadSid);
        __insertBinData(binMdl, koukaiBinSidList, hikoukaiBinSidList, con);

    }

    /**
     * <br>[機  能] 添付ファイル情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param binMdl IpkBinModel
     * @param koukaiBinSidList 添付ファイルSIDリスト(公開)
     * @param hikoukaiBinSidList 添付ファイルSIDリスト(非公開)
     * @param con コネクション
     * @throws SQLException 実行例外
     */
    private void __insertBinData(IpkBinModel binMdl, List < String > koukaiBinSidList,
            List<String> hikoukaiBinSidList, Connection con)
    throws SQLException {

        IpkBinDao binDao = new IpkBinDao(con);
        //添付ファイル情報を登録する。(公開)
        if (koukaiBinSidList != null && !koukaiBinSidList.isEmpty()) {
            binMdl.setTempDsp(IpkConst.IPK_TEMP_DSP_KOUKAI);
            for (String binSid : koukaiBinSidList) {
                binMdl.setBinSid(Long.parseLong(binSid));
                binDao.insert(binMdl);
            }
        }

        //添付ファイル情報を登録する。(非公開)
        if (hikoukaiBinSidList != null && !hikoukaiBinSidList.isEmpty()) {
            binMdl.setTempDsp(IpkConst.IPK_TEMP_DSP_HIKOUKAI);
            for (String binSid : hikoukaiBinSidList) {
                binMdl.setBinSid(Long.parseLong(binSid));
                binDao.insert(binMdl);
            }
        }
    }

    /**
     * <br>[機  能] 管理者情報を設定する(初期表示)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param list 管理者のユーザSIDリスト
     * @param mode 追加モード：0、編集モード：1
     * @return model IpkGrpModel
     * @throws SQLException SQL実行例外
     */
    public IpkGrpModel setAdminData(Connection con, RequestModel reqMdl,
            ArrayList<String> list, int mode)
    throws SQLException {
        String[] leftAry = null;
        IpkGrpModel model = new IpkGrpModel();
        UserBiz userBiz = new UserBiz();

        try {
            //左側の管理者リスト
            if (mode == IpkConst.IPK_HENSYU) {
                if (list.size() > 0) {
                    //ユーザSidリストを配列に変換する。
                    leftAry = (String[]) list.toArray(new String[0]);

                    //管理者ユーザSidの配列をフォームにセットする。(再表示用)
                    model.setAdminSidList(leftAry);

                    //グループに所属するユーザ情報を取得する。
                    List<CmnUsrmInfModel> usList = userBiz.getUserList(con, leftAry);
                    //管理者追加用リストのユーザ姓名をセットする。
                    ArrayList<UsrLabelValueBean> leftList = new ArrayList <UsrLabelValueBean>();
                    for (int i = 0; i < usList.size(); i++) {
                        CmnUsrmInfModel cuiMdl = usList.get(i);
                        leftList.add(new UsrLabelValueBean(cuiMdl));
                    }

                    //フォームにネットワーク管理者の名前のリストをセットする。
                    model.setLeftUserList(leftList);
                }
            }

            //右側の管理者追加用リスト
            //セッションユーザSIDを取得する。
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            GsMessage gsMsg = new GsMessage(reqMdl);

            // グループリストを取得し、セットする。
            GroupBiz gpBiz = new GroupBiz();
            List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);
            model.setGroupList(grpLabelList);
            model.setGroupSelect(String.valueOf(gpBiz.getDefaultGroupSid(sessionUsrSid, con)));

            //追加用ユーザ一覧をセット
            int gpSid = NullDefault.getInt(model.getGroupSelect(), -1);

            //除外するユーザSID
            ArrayList<Integer> usrSids = new ArrayList<Integer>();
            if (leftAry != null) {
                for (int i = 0; i < leftAry.length; i++) {
                    usrSids.add(new Integer(NullDefault.getString(leftAry[i], "")));
                }
            }
            //グループに所属するユーザ情報を取得する。
            List<CmnUsrmInfModel>usList = userBiz.getBelongUserList(con, gpSid, usrSids);
            //管理者追加用リストのユーザ姓名をセットする。
            List<UsrLabelValueBean> labelListAdd = new ArrayList<UsrLabelValueBean>();
            for (int i = 0; i < usList.size(); i++) {
                CmnUsrmInfModel cuiMdl = usList.get(i);
                labelListAdd.add(new UsrLabelValueBean(cuiMdl));
            }
            model.setRightUserList(labelListAdd);
        } catch (SQLException e) {
            throw e;
        }
        return model;
    }

    /**
     * <br>[機  能] 管理者情報を設定する(再表示)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adminSidList 管理者ユーザリスト
     * @param gpSid グループSID
     * @param reqMdl リクエスト情報
     * @return model IpkGrpModel
     * @throws SQLException SQL実行例外
     */
    public IpkGrpModel setAdminDataAg(
            Connection con, String[] adminSidList, int gpSid, RequestModel reqMdl)
    throws SQLException {
        IpkGrpModel model = new IpkGrpModel();
        try {
            //グループに所属するユーザ情報を取得する。
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> leftUsList = userBiz.getUserList(con, adminSidList);
            //管理者追加用リストのユーザ姓名をセットする。
            ArrayList<UsrLabelValueBean> leftList = new ArrayList <UsrLabelValueBean>();
            for (int i = 0; i < leftUsList.size(); i++) {
                CmnUsrmInfModel cuiMdl = leftUsList.get(i);
                leftList.add(new UsrLabelValueBean(cuiMdl));
            }
            //フォームにネットワーク管理者の名前のリストをセットする。
            model.setLeftUserList(leftList);

            // グループリストを取得し、セットする。
            GroupBiz gpBiz = new GroupBiz();
            GsMessage gsMsg = new GsMessage(reqMdl);
            List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);
            model.setGroupList(grpLabelList);

            //追加用ユーザ一覧をセット
            //除外するユーザSID
            ArrayList<Integer> usrSids = new ArrayList<Integer>();
            if (adminSidList != null) {
                for (int i = 0; i < adminSidList.length; i++) {
                    usrSids.add(new Integer(NullDefault.getInt(adminSidList[i], -1)));
                }
            }
            //グループに所属するユーザ情報を取得する。
            List<CmnUsrmInfModel> usList = userBiz.getBelongUserList(con, gpSid, usrSids);

            //管理者追加用リストのユーザ姓名をセットする。
            List<UsrLabelValueBean> labelListAdd = new ArrayList<UsrLabelValueBean>();
            for (int i = 0; i < usList.size(); i++) {
                CmnUsrmInfModel cuiMdl = usList.get(i);
                labelListAdd.add(new UsrLabelValueBean(cuiMdl));
            }
            model.setRightUserList(labelListAdd);
        } catch (SQLException e) {
            throw e;
        }
        return model;
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param netSid ネットワークSID
     * @param mode ネットワークの添付ファイル：-1、IPアドレスの添付ファイル:-1以外
     * @param koukaiMode 公開か非公開
     * @param reqMdl リクエスト情報
     * @return tempList
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public ArrayList<IpkBinModel> getTempDataList(Connection con,
            int netSid, int mode, int koukaiMode, RequestModel reqMdl)
    throws SQLException, TempFileException {
        ArrayList<IpkBinModel> tempList = new ArrayList<IpkBinModel>();
        try {
            IpkBinDao ipkDao = new IpkBinDao(con);
            CommonBiz cmnBiz = new CommonBiz();
            //IP管理バイナリ情報を取得
            List <IpkBinModel> ipkBinList = ipkDao.getIpkanriTmpFileList(netSid, mode, koukaiMode);

            if (ipkBinList == null || ipkBinList.size() < 1) {
                return tempList;
            }


            String[] binSids = new String[ipkBinList.size()];
            int i = 0;
            for (IpkBinModel mdl : ipkBinList) {
                binSids[i] = String.valueOf(mdl.getBinSid());
                i++;
            }

            //バイナリ情報を取得
            List<CmnBinfModel> binFileList = cmnBiz.getBinInfo(con, binSids, reqMdl.getDomain());

            IpkBinModel binModel = null;
            for (CmnBinfModel binData : binFileList) {
                if (binData.getBinJkbn() == GSConst.JTKBN_DELETE) {
                    continue;
                }
                binModel = new IpkBinModel();
                binModel.setBinFileName(binData.getBinFileName());
                binModel.setBinFilePath(binData.getBinFilePath());
                binModel.setBinSid(binData.getBinSid());
                long size = binData.getBinFileSize();
                String strSize = cmnBiz.getByteSizeString(size);
                binModel.setBinFileSizeDsp(strSize);
                tempList.add(binModel);
            }
        } catch (SQLException e) {
            throw e;
        }
        return tempList;
    }

    /**
     * <br>[機  能] IP管理情報の添付ファイルをテンポラリディレクトリに移動する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void readIpkBinData(Connection con, int netSid, int iadSid,
                            String appRoot, String tempDir, RequestModel reqMdl)
    throws IOException, IOToolsException, SQLException, TempFileException {

        IpkBiz ipkBiz = new IpkBiz();
        //公開
        ipkBiz.setIpkanriTempFileData(con, netSid, iadSid,
                appRoot, tempDir + IpkConst.IPK_TEMP_KOUKAI + "/",
                new UDate(), IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
        //非公開
        ipkBiz.setIpkanriTempFileData(con, netSid, iadSid,
                appRoot, tempDir + IpkConst.IPK_TEMP_HIKOUKAI + "/",
                new UDate(), IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
    }

    /**
     * <br>[機  能] ４分割されているアドレスを0付きのファーマットに変換する。
     * <br>[解  説]
     * <br>[備  考] 例：192168001005
     * @param add1 アドレス1
     * @param add2 アドレス2
     * @param add3 アドレス3
     * @param add4 アドレス4
     * @return address 0付きフォーマットのアドレス
     */
    public String toAddressDataFormat(String add1, String add2, String add3, String add4) {
        String address = "";
        address = StringUtil.toDecFormat(add1, IpkConst.IPAD_FORMAT_PART)
            + StringUtil.toDecFormat(add2, IpkConst.IPAD_FORMAT_PART)
            + StringUtil.toDecFormat(add3, IpkConst.IPAD_FORMAT_PART)
            + StringUtil.toDecFormat(add4, IpkConst.IPAD_FORMAT_PART);
        return address;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリファイルSID
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return fileName
     */
    public String doDownLoadTemp(
        Long binSid,
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        String appRootPath,
        RequestModel reqMdl
        ) throws SQLException, Exception {

        //バイナリファイル情報を取得する。
        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid, reqMdl.getDomain());
        String fileName = "";

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDownload = gsMsg.getMessage("cmn.download");

        if (cbMdl != null) {

            //ログ出力処理
            IpkBiz ipkBiz = new IpkBiz(con);
            ipkBiz.outPutLog(map, reqMdl,
                    textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(),
                    String.valueOf(binSid));

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, cbMdl, appRootPath, Encoding.UTF_8);
            fileName = cbMdl.getBinFileName();
        }
        return fileName;
    }

    /**
     * <br>[機  能] ネットワーク詳細情報の添付ファイルがダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param netSid ネットワークSID
     * @param binSid バイナリSID
     * @param userSid ユーザSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLNetwork(Connection con, int netSid, Long binSid, int userSid)
            throws SQLException {


        IpkNetDao dao = new IpkNetDao(con);
        ArrayList<IpkNetModel> netList = dao.selectNetwork(netSid);
        IpkNetModel netMdl = netList.get(0);

        if (netMdl == null) {
            return false;
        }

        //ネットワーク公開区分 公開の場合
        if (netMdl.getNetDsp().equals(IpkConst.IPK_NET_DSP)) {
            IpkBinDao binDao = new IpkBinDao(con);

            //指定したバイナリSIDがネットワーク情報の添付で、添付ファイル公開区分が「公開」である。
            if (binDao.isCheckIpKanriTmpFile(
                    netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_KOUKAI, binSid)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] IP一覧画面のネットワーク詳細情報の添付ファイルがダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param netSid ネットワークSID
     * @param binSid バイナリSID
     * @param userSid ユーザSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLNetworkForIp(
            Connection con, RequestModel reqMdl, int netSid, Long binSid, int userSid)
            throws SQLException {


        IpkNetDao dao = new IpkNetDao(con);
        ArrayList<IpkNetModel> netList = dao.selectNetwork(netSid);
        IpkNetModel netMdl = netList.get(0);


        if (netMdl == null) {
            return false;
        }

        //システム管理者、全ネットワーク管理者、ネットーワーク管理者フラグ
        boolean adminFlg = isNetworkAdmin(netSid, reqMdl, con);

        //ネットワーク公開区分 非公開の場合
        if (netMdl.getNetDsp().equals(IpkConst.IPK_NET_NOT_DSP)) {
            //システム管理者、全ネットワーク管理者、ネットーワーク管理者以外はダウンロード可能
            if (!adminFlg) {
                return false;
            }
        }

        IpkBinDao binDao = new IpkBinDao(con);

        //ネットワークバイナリ情報を取得
        IpkBinModel binMdl = binDao.getNetworkTmpFile(netSid, binSid);
        if (binMdl == null) {
            return false;
        }

        int binDsp = binMdl.getTempDsp();
        //添付ファイルの公開区分「公開」の場合
        if (binDsp == IpkConst.IPK_TEMP_DSP_KOUKAI) {
            //全てのユーザがダウンロード可能
            return true;

            //添付ファイルの公開区分「非公開」の場合
        } else if (binDsp == IpkConst.IPK_TEMP_DSP_HIKOUKAI) {
            //システム管理者、全ネットワーク管理者、ネットーワーク管理者のみダウンロード可能
            if (adminFlg) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] IPアドレス詳細画面でIPアドレス情報の添付ファイルがダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param binSid バイナリSID
     * @param userSid ユーザSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLIpAddress(
            Connection con, RequestModel reqMdl, int netSid, int iadSid, Long binSid, int userSid)
            throws SQLException {

        IpkNetDao dao = new IpkNetDao(con);
        ArrayList<IpkNetModel> netList = dao.selectNetwork(netSid);
        IpkNetModel netMdl = netList.get(0);

        if (netMdl == null) {
            return false;
        }

        //ネットワーク公開区分 公開の場合
        if (netMdl.getNetDsp().equals(IpkConst.IPK_NET_DSP)) {
            IpkBinDao binDao = new IpkBinDao(con);

            //指定したバイナリSIDがIP管理の添付で、添付ファイル公開区分が「公開」である。
            if (binDao.isCheckIpKanriTmpFile(
                    netSid, iadSid, IpkConst.IPK_TEMP_DSP_KOUKAI, binSid)) {
                return true;
            }
        }


        return false;
    }


    /**
     * <br>[機  能] コメントがnum文字以上のある場合「･･･」を表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param msg コメント
     * @param num 最大文字数
     * @return shortMsg 検索結果表示用のコメント
     */
    public String getShortMsg(String msg, int num) {

        String dspMsg = "";
        if (msg.length() <= (num + 2)) {
            dspMsg = msg;
        } else if (msg.length() > (num + 2)) {
            dspMsg = msg.substring(0, num) + "･･･";
        }
        return dspMsg;
    }

    /**
     * <br>[機  能] 非公開のネットワークSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return dspNetSidList 非公開ネットワークSIDリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getNotDspNetList(Connection con, RequestModel reqMdl)
    throws SQLException {

        //セッションユーザSIDを取得する。
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        //セッションユーザがGS管理者か、全ネットワーク管理者であるかを判定
        IpkAdminInfo ipkKanrisyaKubun = new IpkAdminInfo();
        boolean allAdmin = ipkKanrisyaKubun.isGsIpAdm(reqMdl, con);

        ArrayList<Integer> notDspNetSidList = null;
        //非公開ネットワークSIDリストを設定する。
        if (!allAdmin) {
            IpkNetDao dao = new IpkNetDao(con);
            notDspNetSidList = dao.getNotDspNetSid(sessionUsrSid);
        }
        return notDspNetSidList;
    }

    /**
     * <br>[機  能] 非公開ネットワークSIDが不正か判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return true:正常 false:不正アクセス
     * @throws SQLException SQL実行時例外
     */
    public boolean isNotDspNetSid(int netSid, RequestModel reqMdl, Connection con)
    throws SQLException {

        boolean errorFlg = true;
        //非公開ネットワークSIDリストを取得する。
        ArrayList<Integer> notDspNetSidList = getNotDspNetList(con, reqMdl);
        if (notDspNetSidList == null) {
            return true;
        }
        for (int notDspNetSid : notDspNetSidList) {
            if (netSid == notDspNetSid) {
                return false;
            }
        }

        return errorFlg;
    }

    /**
     * <br>[機  能] スペックの名前をhtml表示用に変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param modelList ArrayList
     * @return transToHtmlModelList ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkSpecMstModel> transToHtmlModelList(ArrayList<IpkSpecMstModel> modelList)
    throws SQLException {

        ArrayList<IpkSpecMstModel> transToHtmlModelList = new ArrayList<IpkSpecMstModel>();
        IpkSpecMstModel model = null;
        for (IpkSpecMstModel mdl : modelList) {
            model = new IpkSpecMstModel();
            model.setIsmSid(mdl.getIsmSid());
            model.setIpk100name(StringUtilHtml.transToHTmlPlusAmparsant(mdl.getIpk100name()));
            model.setIpk100level(mdl.getIpk100level());
            model.setIpk100biko(mdl.getIpk100biko());
            model.setSpecKbn(mdl.getSpecKbn());
            transToHtmlModelList.add(model);
        }
        return transToHtmlModelList;
    }

    /**
     * <br>[機  能] IP管理全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value) {
        outPutLog(map, reqMdl, opCode, level, value, null);
    }

    /**
     * <br>[機  能] IP管理全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            String fileId) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textIpkanri = gsMsg.getMessage("cmn.ipkanri");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(IpkConst.PLUGIN_ID_IPKANRI);
        logMdl.setLogPluginName(textIpkanri);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), reqMdl));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * <br>[機  能] プログラムIDからプログラム名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param id アクションID
     * @param reqMdl リクエスト情報
     * @return String
     */
    public String getPgName(String id, RequestModel reqMdl) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.ip.ipk010.Ipk010Action")) {
            String textTitle = gsMsg.getMessage("ipk.12");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk020.Ipk020Action")) {
            String textTitle = gsMsg.getMessage("ipk.13");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk030.Ipk030Action")) {
            String textTitle = gsMsg.getMessage("cmn.preferences");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk040.Ipk040Action")) {
            String textTitle = gsMsg.getMessage("ipk.15");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk050.Ipk050Action")) {
            String textTitle = gsMsg.getMessage("ipk.23");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk060.Ipk060Action")) {
            String textTitle = gsMsg.getMessage("ipk.16");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk100.Ipk100Action")) {
            String textTitle = gsMsg.getMessage("ipk.10");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.ip.ipk100kn.Ipk100knAction")) {
            String textTitle = gsMsg.getMessage("ipk.10");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        return ret;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能]GS、全ネットワーク、ネットワーク管理者の権限の有無を判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return boolean 一つでも権限があればtrue、権限が無ければfalse
     * @throws SQLException SQL実行時例外
     */
    public boolean isNetworkAdmin(int netSid, RequestModel reqMdl, Connection con)
    throws SQLException {

        try {
            //セッションユーザSIDを取得
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            //GS管理者か全ネットワーク管理者ならば、TRUE。
            IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
            if (ipkAdminInfo.isGsIpAdm(reqMdl, con)) {
                return IpkConst.KANRI_KENGEN_ARI;
            }

            //ネットワーク管理者か判定する。
            IpkNetAdmDao dao = new IpkNetAdmDao(con);
            int admNetCount = dao.countUsrAdmNet(netSid, sessionUsrSid);
            if (admNetCount > 0) {
                 return IpkConst.KANRI_KENGEN_ARI;
            }

        } catch (SQLException e) {
            throw e;
        }
        return IpkConst.KANRI_KENGEN_NASI;
    }
}