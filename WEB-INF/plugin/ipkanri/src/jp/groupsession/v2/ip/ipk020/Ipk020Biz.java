package jp.groupsession.v2.ip.ipk020;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.IpkUsedDataBiz;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.ip.model.IpkGrpModel;
import jp.groupsession.v2.ip.model.IpkNetAdmModel;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.ip.model.ValidateCheckModel;

/**
 * <br>[機  能] IP管理 ネットワーク登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk020Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param netSid ネットワークSid
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Ipk020ParamModel paramMdl, Connection con,
            String netSid, String appRoot, String tempDir, RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {
        //ネットワークアドレス、サブネットマスクを分割してフォームにセットする。
        try {
            IpkBiz ipkBiz = new IpkBiz();
            //添付ファイルをテンポラリディレクトリへ移動する(公開)
            ipkBiz.readIpkBinData(con, NullDefault.getInt(paramMdl.getNetSid(), 0),
                IpkConst.NETWORK_TOUROKU, appRoot, tempDir, reqMdl);

            //ネットワーク情報を取得する。
            IpkNetDao ipkDao = new IpkNetDao(con);
            int intNetSid = Integer.parseInt(netSid);

            ArrayList<IpkNetModel> ret = ipkDao.selectNetwork(intNetSid);
            //ネットワーク情報をセットする。
            String[] netadAry = new String[4];
            String[] sabnetAry = new String[4];
            for (IpkNetModel ipkModel : ret) {
                String netad = NullDefault.getString(ipkModel.getNetNetad(), "");
                String sabnet = NullDefault.getString(ipkModel.getNetSabnet(), "");
                //ネットワークアドレスを配列に格納する。
                netadAry = netad.replaceAll("\\.", ",").split(",");
                //サブネットマスクを配列に格納する。
                sabnetAry = sabnet.replaceAll("\\.", ",").split(",");
                paramMdl.setNetSid(String.valueOf(ipkModel.getNetSid()));
                paramMdl.setNetName(String.valueOf(ipkModel.getNetName()));
                paramMdl.setNetNetad1(netadAry[0]);
                paramMdl.setNetNetad2(netadAry[1]);
                paramMdl.setNetNetad3(netadAry[2]);
                paramMdl.setNetNetad4(netadAry[3]);
                paramMdl.setNetSabnet1(sabnetAry[0]);
                paramMdl.setNetSabnet2(sabnetAry[1]);
                paramMdl.setNetSabnet3(sabnetAry[2]);
                paramMdl.setNetSabnet4(sabnetAry[3]);
                paramMdl.setNetDsp(ipkModel.getNetDsp());
                paramMdl.setNetMsg(ipkModel.getNetMsg());
                paramMdl.setNetMsgHtml(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                ipkModel.getNetMsg()), ""));
                paramMdl.setNetSort(String.valueOf(ipkModel.getNetSort()));
            }

            //添付ファイル一覧を設定
            setTempList(paramMdl, tempDir, con, reqMdl);
        } catch (SQLException e) {
           throw e;
        }
    }

    /**
     * <br>[機  能] 管理者情報を設定する(初期表示)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitAdminData(
            Ipk020ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {
        IpkNetAdmDao ipknetAdmDao = new IpkNetAdmDao(con);
        int intNetSid = NullDefault.getInt(paramMdl.getNetSid(), -2);
        IpkBiz ipkBiz = new IpkBiz();
        int mode = IpkConst.IPK_TUIKA;
        if (intNetSid != -2) {
            mode = IpkConst.IPK_HENSYU;
        }
        //ネットワーク管理者のユーザSIDのリストを取得する。
        ArrayList<String> admSidList = ipknetAdmDao.selectUsrSid(intNetSid);
        //ネットワーク管理者情報を取得する。
        IpkGrpModel model = ipkBiz.setAdminData(con, reqMdl, admSidList, mode);
        //フォームにネットワーク管理者情報をセットする。
        paramMdl.setAdminSidList(model.getAdminSidList());
        paramMdl.setGroupSelect(model.getGroupSelect());
        paramMdl.setAdminUserList(model.getLeftUserList());
    }

    /**
     * <br>[機  能] ネットワーク情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param cntCon 採番コントローラ
     * @param model IpkNetModel
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setNetworkData(
            Ipk020ParamModel paramMdl,
            Connection con,
            int sessionUsrSid,
            MlCountMtController cntCon,
            IpkNetModel model)
    throws SQLException, IOToolsException, IOException, TempFileException {

        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            //ネットワークアドレス4つを一つの文字列にする。
            String netadStr = null;
            netadStr = Integer.parseInt(paramMdl.getNetNetad1()) + "."
            + Integer.parseInt(paramMdl.getNetNetad2()) + "."
            + Integer.parseInt(paramMdl.getNetNetad3()) + "."
            + Integer.parseInt(paramMdl.getNetNetad4());

            //サブネットマスク4つを一つの文字列にする。
            String sabnetStr = null;
            sabnetStr = Integer.parseInt(paramMdl.getNetSabnet1()) + "."
             + Integer.parseInt(paramMdl.getNetSabnet2()) + "."
             + Integer.parseInt(paramMdl.getNetSabnet3()) + "."
             + Integer.parseInt(paramMdl.getNetSabnet4());

            //現在の日時を取得
            UDate now = new UDate();
            // ネットワーク更新情報をモデルにセットする。
            model.setNetSid(NullDefault.getInt(paramMdl.getNetSid(), -2));
            model.setNewNetSid(model.getNewNetSid());
            model.setNetName(NullDefault.getString(paramMdl.getNetName(), ""));
            model.setNetNetad(netadStr);
            model.setNetSabnet(sabnetStr);
            model.setNetDsp(NullDefault.getString(paramMdl.getNetDsp(), ""));
            model.setNetMsg(NullDefault.getString(paramMdl.getNetMsg(), ""));
            model.setNetSort(NullDefault.getInt(paramMdl.getNetSort(), -1));
            model.setNetEuid(sessionUsrSid);
            model.setNetAdate(now);
            model.setUsrSid(sessionUsrSid);
            model.setNetEdate(now);

            String cmd = NullDefault.getString(paramMdl.getCMD(),  "");
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            IpkBinModel binModel = new IpkBinModel();
            IpkUsedDataBiz usedDataBiz = new IpkUsedDataBiz(con);
            int netSid = 0;

            if (cmd.equals("netAdd")) {
                //ネットワーク追加
                ipkNetDao.insert(model);
                binModel.setNewNetSid(model.getNewNetSid());
                netSid = model.getNewNetSid();
            } else if (cmd.equals("netEdit")) {
                //ネットワーク情報のデータ使用量を登録(更新対象のデータ使用量を減算)
                netSid = model.getNetSid();
                usedDataBiz.insertNetworkSize(netSid, false);

                //ネットワーク更新
                ipkNetDao.updateNetworkData(model);
                binModel.setNewNetSid(NullDefault.getInt(paramMdl.getNetSid(), -2));
            }

            binModel.setUsrSid(sessionUsrSid);
            binModel.setNetSid(NullDefault.getInt(paramMdl.getNetSid(), -2));
            binModel.setNow(now);
            binModel.setCmdMode(cmd);
            binModel.setAppRootPath(model.getAppRootPath());
            binModel.setTempDir(model.getTempDir());

            //ネットワーク添付情報の登録
            IpkBiz ipkBiz = new IpkBiz();
            ipkBiz.insertIpkBin(con, cntCon, binModel);

            //ネットワーク情報のデータ使用量を登録
            usedDataBiz.insertNetworkSize(netSid, true);

            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
                log__.debug("コミット完了");
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] ネットワーク管理者情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param newNetSid 採番ネットワークSID
     * @throws SQLException SQL実行例外
     */
    public void setAdmindata(Ipk020ParamModel paramMdl, Connection con,
            int sessionUsrSid, int newNetSid)
    throws SQLException {

        //コミットフラグ
        boolean commitFlg = false;
        //オートコミットしないようにする。
        con.setAutoCommit(false);
        try {
            //日時を取得
            UDate now = new UDate();

            //既存ネットワーク管理者情報を削除(NetSid)
            IpkNetAdmDao ipkAdminDao = new IpkNetAdmDao(con);
            ipkAdminDao.deleteAdmin(NullDefault.getInt(paramMdl.getNetSid(), 0));

            //追加するネットワーク管理者のユーザSidリストを作成する。
            String[] adminList = paramMdl.getAdminSidList();
            if (adminList == null) {
                commitFlg = true;
                return;
            }

            //削除区分ユーザを除外する。
            ArrayList<Integer> userArray = new ArrayList<Integer>();
            for (String strUsrSid : adminList) {
                userArray.add(Integer.parseInt(strUsrSid));
            }
            CmnUsrmDao usrmDao = new CmnUsrmDao(con);
            CmnUsrmDao usrDao = new CmnUsrmDao(con);
            CmnUsrmModel usrModel = null;
            int cnt = usrmDao.getCountDeleteUser(userArray);

            //ネットワーク管理者情報を登録する。
            IpkNetAdmModel model = new IpkNetAdmModel();
            for (String admSid : adminList) {
                int sid = NullDefault.getInt(admSid, 0);

                if (cnt > 0) {
                    usrModel = usrDao.select(sid);
                    if (usrModel.getUsrJkbn() == GSConst.JTKBN_DELETE) {
                        continue;
                    }
                }

                if (paramMdl.getCMD().equals("netAdd")) {
                    model.setNetSid(newNetSid);
                } else if (paramMdl.getCMD().equals("netEdit")) {
                    model.setNetSid(Integer.parseInt(paramMdl.getNetSid()));
                }
                model.setNetAuid(sessionUsrSid);
                model.setNetEdate(now);
                model.setNetEuid(sessionUsrSid);
                model.setNetAdate(now);
                model.setUsrSid(sid);
                ipkAdminDao.insertAdmin(model);
            }
            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
                log__.debug("コミット完了");
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] ネットワークアドレス入力チェック用モデルを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netAd ネットワークアドレス
     * @param con コネクション
     * @return ipadList ネットワークアドレスのリスト
     * @throws SQLException SQL実行時例外
     */
    public ValidateCheckModel getValidateModel(String netAd, Connection con)
    throws SQLException {
        ValidateCheckModel validateCheckModel = new ValidateCheckModel();
        try {
            // ネットワークアドレスリストを設定する。
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            ArrayList<String> netadList = ipkNetDao.selectNetad();
            validateCheckModel.setNetadList(netadList);

            //変更前のネットワークアドレスを設定する。
            validateCheckModel.setBeforeNetad(netAd);

        } catch (SQLException e) {
            throw e;
        }
        return validateCheckModel;
    }

    /**
     * <br>[機  能] 添付ファイル一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリパス
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setTempList(Ipk020ParamModel paramMdl, String tempDir, Connection con,
                            RequestModel reqMdl)
    throws IOToolsException, SQLException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        IpkBiz ipkBiz = new IpkBiz();
        //閲覧モード用
        //添付ファイル一覧を設定(公開)
        ArrayList <IpkBinModel> koukaiBinList = ipkBiz.getTempDataList(
              con, NullDefault.getInt(paramMdl.getNetSid(), 0),
              IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
        paramMdl.setKoukaiBinFileInfList(koukaiBinList);

        //添付ファイル一覧を設定(非公開)
        ArrayList <IpkBinModel> hikoukaiBinList = ipkBiz.getTempDataList(
                con, NullDefault.getInt(paramMdl.getNetSid(),
                        0), IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
        paramMdl.setHikoukaiBinFileInfList(hikoukaiBinList);

        //編集モード用
        //公開添付ファイル
        paramMdl.setIpk020KoukaiFileLabelList(
                cmnBiz.getTempFileLabelList(tempDir + IpkConst.IPK_TEMP_KOUKAI));
        //非公開添付ファイル
        paramMdl.setIpk020HikoukaiFileLabelList(
                cmnBiz.getTempFileLabelList(tempDir + IpkConst.IPK_TEMP_HIKOUKAI));
    }

    /**
     * <br>[機  能] ネットワーク情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException 実行例外
     */
    public void deleteNetwork(int netSid, Connection con, int sessionUsrSid)
    throws SQLException {
        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            //ネットワーク情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            IpkUsedDataBiz usedDataBiz = new IpkUsedDataBiz(con);
            usedDataBiz.insertNetworkSize(netSid, false);

            //ネットワーク情報の削除
            IpkNetDao ipkDao = new IpkNetDao(con);

            //ネットワーク管理者情報を削除する。
            IpkNetAdmDao ipkNetAdmDao = new IpkNetAdmDao(con);
            ipkNetAdmDao.deleteAdmin(netSid);
            IpkAddDao ipkAddDao = new IpkAddDao(con);

            //バイナリー情報の論理削除、添付情報の削除を行う
            IpkBinDao ipkBinDao = new IpkBinDao(con);
            UDate now = new UDate();
            ipkBinDao.removeIpkBinData(netSid, sessionUsrSid, now);
            ipkBinDao.deleteNetworkTemp(netSid);

            //ネットワークのIPアドレスリストを取得する。
            ArrayList<String> iadSidList = ipkAddDao.selectIadSid(netSid);
            ArrayList<Integer> intIadSidList = new ArrayList<Integer>();
            if (iadSidList.size() != 0) {
                for (String iadSid : iadSidList) {
                    intIadSidList.add(Integer.parseInt(iadSid));
                }
                //IPアドレスの使用者削除
                IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
                ipkAddAdmDao.deleteHukusuu(intIadSidList);

                //1つのネットワークのIPアドレス削除する。
                ipkAddDao.deleteNetwork(netSid);
            }
            //ネットワーク情報の削除
            ipkDao.deleteNetwork(netSid);
            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
                log__.debug("コミット完了");
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 選択されている添付ファイル名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param binFilePath バイナリファイルパス
     * @return fileName 選択されているファイル名
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public String getDownloadFile(String tempDir, String binFilePath)
    throws IOToolsException {

        String fileName = "";
        try {
            //添付ファイル一覧を取得する。
            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> tempFileList = (cmnBiz.getTempFileLabelList(tempDir));

            for (LabelValueBean label : tempFileList) {
                if (label.getValue().equals(binFilePath)) {
                    fileName = label.getLabel();
                }
            }
        } catch (IOToolsException e) {
            throw e;
        }
        return fileName;
    }
}