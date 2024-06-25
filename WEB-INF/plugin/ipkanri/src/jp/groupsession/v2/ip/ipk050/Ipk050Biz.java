package jp.groupsession.v2.ip.ipk050;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.IpkUsedDataBiz;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.dao.IpkSpecMstDao;
import jp.groupsession.v2.ip.model.IpkAddAdmModel;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.ip.model.IpkGrpModel;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;
import jp.groupsession.v2.ip.model.ValidateCheckModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] IP管理 IPアドレス登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk050Biz.class);

    /**
     * <br>[機  能] 管理者情報を設定する(初期表示)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitAdminData(Ipk050ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        try {
            IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
            IpkBiz ipkBiz = new IpkBiz();
            int iadSid = NullDefault.getInt(paramMdl.getIadSid(), -2);
            int mode = IpkConst.IPK_TUIKA;
            if (iadSid != -2) {
                mode = IpkConst.IPK_HENSYU;
            }
            //一つのIPアドレスの管理者ユーザSIDを取得する。
            ArrayList<String> admSidList = ipkAddAdmDao.select(iadSid);
            //ネットワーク管理者情報を取得する。
            IpkGrpModel model = ipkBiz.setAdminData(con, reqMdl, admSidList, mode);
            //フォームにネットワーク管理者情報をセットする。
            paramMdl.setAdminSidList(model.getAdminSidList());
            paramMdl.setGroupSelect(model.getGroupSelect());
            paramMdl.setEmployerUserList(model.getLeftUserList());

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] IPアドレス情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param cntCon 採番コントローラ
     * @param model IpkAddModel
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setiadUpdate(Ipk050ParamModel paramMdl, Connection con, int sessionUsrSid,
            MlCountMtController cntCon, IpkAddModel model)
    throws SQLException, IOException, IOToolsException, TempFileException {

        IpkAddDao ipkAddDao = new IpkAddDao(con);
        //コミットフラグ
        boolean commitFlg = false;
        //オートコミットしない
        con.setAutoCommit(false);

        try {
            IpkBiz ipkBiz = new IpkBiz();
            IpkUsedDataBiz usedDataBiz = new IpkUsedDataBiz(con);

            //IPアドレス情報をモデルにセットする。
            model.setIadSid(NullDefault.getInt(paramMdl.getIadSid(), -2));
            model.setNetSid(NullDefault.getInt(paramMdl.getIpk050NetSid(), -2));
            model.setIadName(NullDefault.getString(paramMdl.getIadMachineName(), ""));
            //IPアドレスをO付きのフォーマットに変更する。
            String strIpad = "";
            strIpad = ipkBiz.toAddressDataFormat(
                    paramMdl.getIadAddText1(), paramMdl.getIadAddText2(),
                    paramMdl.getIadAddText3(), paramMdl.getIadAddText4());
            model.setIadIpad(Long.parseLong(strIpad));
            model.setIadUseKbn(
                    NullDefault.getString(paramMdl.getIadUse(), IpkConst.USEDKBN_MISIYOU));
            model.setIadMsg(NullDefault.getString(paramMdl.getIadMsg(), ""));
            model.setIadAuid(sessionUsrSid);
            model.setIadEuid(sessionUsrSid);
            model.setIadPrtMngNum(NullDefault.getString(paramMdl.getIadPrtMngNum(), ""));
            model.setIadCpu(NullDefault.getInt(paramMdl.getCpuSelect(), 0));
            model.setIadMemory(NullDefault.getInt(paramMdl.getMemorySelect(), -2));
            model.setIadHd(NullDefault.getInt(paramMdl.getHdSelect(), -2));

            //現在日時を取得する。
            UDate now = new UDate();
            model.setIadAdate(now);
            model.setIadEdate(now);
            String cmd = NullDefault.getString(paramMdl.getCMD(),  "");
            if (cmd.equals("iadAdd")) {
                //IPアドレス追加
                ipkAddDao.insert(model);
            } else if (cmd.equals("iadEdit")) {
                //ネットワーク情報のデータ使用量を登録(変更前のデータ使用量を減算)
                usedDataBiz.insertNetworkSize(model.getNetSid(), false);

                //IPアドレス変更
                ipkAddDao.update(model);
            }
            IpkBinModel binModel = new IpkBinModel();
            binModel.setUsrSid(sessionUsrSid);
            binModel.setNetSid(NullDefault.getInt(paramMdl.getIpk050NetSid(), -2));
            binModel.setNewNetSid(NullDefault.getInt(paramMdl.getIpk050NetSid(), -2));
            binModel.setIadSid(NullDefault.getInt(paramMdl.getIadSid(), -2));
            binModel.setNewIadSid(model.getNewIadSid());
            binModel.setNow(now);
            binModel.setCmdMode(cmd);
            binModel.setAppRootPath(model.getAppRootPath());
            binModel.setTempDir(model.getTempDir());
            //IPアドレス添付情報の登録
            ipkBiz.insertIpkBin(con, cntCon, binModel);

            //ネットワーク情報のデータ使用量を登録
            usedDataBiz.insertNetworkSize(model.getNetSid(), true);

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
     * <br>[機  能] IP管理者情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param newIadSid 採番したIPアドレスSID
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setIadAdminUpdate(
            Ipk050ParamModel paramMdl, Connection con, int newIadSid, int sessionUsrSid)
    throws SQLException {

        IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
        //コミットフラグの定義
        boolean commitFlg = false;
        //オートコミットしないようにする。
        con.setAutoCommit(false);

        try {
            //既存管理者情報を削除
            int iadSid = NullDefault.getInt(paramMdl.getIadSid(), -2);
            if (paramMdl.getCMD().equals("iadEdit")) {
              ipkAddAdmDao.delete(iadSid);
            }

            //追加する管理者のユーザSidリストを作成する。
            String[] adminList = paramMdl.getAdminSidList();
            if (adminList == null) {
                return;
            }
            IpkAddAdmModel model = new IpkAddAdmModel();
            for (String admSid : adminList) {
                int sid = NullDefault.getInt(admSid, 0);
                model.setNewIadSid(newIadSid);
                model.setIadSid(newIadSid);
                model.setIadAdmAuid(sessionUsrSid);
                model.setIadAdmEuid(sessionUsrSid);
                //現在日時を取得する。
                UDate now = new UDate();
                model.setIadAdmAdate(now);
                model.setIadAdmEdate(now);
                model.setUsrSid(sid);
                ipkAddAdmDao.insert(model);
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
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Ipk050ParamModel paramMdl, Connection con,
        String appRoot, String tempDir, RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {
        try {
            //ネットワーク情報を取得する
            int netSid = NullDefault.getInt(paramMdl.getIpk050NetSid(), -2);
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            ArrayList<IpkNetModel> ret = ipkNetDao.selectNetwork(netSid);
            //フォームにセットする。
            for (IpkNetModel ipkModel : ret) {
                paramMdl.setNetName(String.valueOf(ipkModel.getNetName()));
                paramMdl.setNetNetad(ipkModel.getNetNetad());
                paramMdl.setNetSabnet(ipkModel.getNetSabnet());
                //コメントをhtml表示用に変換
                paramMdl.setNetMsg(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(
                                     ipkModel.getNetMsg()), ""));
            }

            //ネットワーク添付ファイルをテンポラリディレクトリへ移動する
            IpkBiz ipkBiz = new IpkBiz();
            int iadSid = NullDefault.getInt(paramMdl.getIadSid(), -2);
            ipkBiz.readIpkBinData(con, NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                    IpkConst.NETWORK_TOUROKU, appRoot, tempDir + IpkConst.NETWORK + "/", reqMdl);

            //ネットワーク添付ファイル一覧を設定
            //添付ファイル一覧を設定(公開)
            ArrayList <IpkBinModel> koukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
            paramMdl.setKoukaiBinFileInfList(koukaiBinList);

            //添付ファイル一覧を設定(非公開)
            ArrayList <IpkBinModel> hikoukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
            paramMdl.setHikoukaiBinFileInfList(hikoukaiBinList);


            //IPアドレス添付ファイル一覧を設定(公開)
            ArrayList <IpkBinModel> ipadKoukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, iadSid, IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
            paramMdl.setIpadKoukaiBinFileInfList(ipadKoukaiBinList);

            //IPアドレス添付ファイル一覧を設定(非公開)
            ArrayList <IpkBinModel> ipadHikoukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, iadSid, IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
            paramMdl.setIpadHikoukaiBinFileInfList(ipadHikoukaiBinList);

            //添付ファイル存在フラグを設定
            if (!paramMdl.getKoukaiBinFileInfList().isEmpty()
                    || !paramMdl.getHikoukaiBinFileInfList().isEmpty()) {
                paramMdl.setIpk050TempExist(IpkConst.IPK_TEMP_EXIST);
            } else {
                paramMdl.setIpk050TempExist(IpkConst.IPK_TEMP_NOT_EXIST);
            }
            if (paramMdl.isIpk050AdminFlg()) {
                //CPU、メモリ、HDコンボを設定する。
                __setCpuSelect(paramMdl, con, reqMdl);
            } else {
                //CPU、メモリ、HD名を設定する。
                __setCpuName(paramMdl, con);
            }

            //IPアドレス情報を取得する。
            IpkAddDao addDao = new IpkAddDao(con);
            ArrayList<IpkAddModel> iadModel = addDao.selectIad(iadSid);

            //IPアドレスのテキストボックス値と数を設定する。
            int textNum = IpAddressCheck.checkSubNet(paramMdl.getNetSabnet());
            paramMdl.setTextNum(String.valueOf(textNum));
            String cmd = NullDefault.getString(paramMdl.getCMD(), "");
            if (cmd.equals("ipEdit") || cmd.equals("ipk070detail")) {
                //IPアドレス添付ファイルをテンポラリディレクトリへ移動する
                ipkBiz.readIpkBinData(con, NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                        NullDefault.getInt(paramMdl.getIadSid(), -1), appRoot,
                        tempDir, reqMdl);

                for (IpkAddModel ipkModel : iadModel) {
                    String ipad = getDecFormat(ipkModel.getIadIpad(), IpkConst.IPAD_FORMAT);
                    String ipad1 = String.valueOf(ipad.substring(0, 3));
                    String ipad2 = String.valueOf(ipad.substring(3, 6));
                    String ipad3 = String.valueOf(ipad.substring(6, 9));
                    String ipad4 = String.valueOf(ipad.substring(9, 12));
                    paramMdl.setIadAddText1(NullDefault.getString(
                        paramMdl.getIadAddText1(), String.valueOf(Integer.parseInt(ipad1))));
                    paramMdl.setIadAddText2(NullDefault.getString(
                        paramMdl.getIadAddText2(), String.valueOf(Integer.parseInt(ipad2))));
                    paramMdl.setIadAddText3(NullDefault.getString(
                        paramMdl.getIadAddText3(), String.valueOf(Integer.parseInt(ipad3))));
                    paramMdl.setIadAddText4(NullDefault.getString(
                        paramMdl.getIadAddText4(), String.valueOf(Integer.parseInt(ipad4))));
                    paramMdl.setIadAdd(String.valueOf(ipkModel.getIadIpad()));
                    paramMdl.setIadMachineName(ipkModel.getIadName());
                    paramMdl.setIadUse(String.valueOf(ipkModel.getIadUseKbn()));
                    paramMdl.setIadMsg(ipkModel.getIadMsg());
                    paramMdl.setIadMsgHtml(NullDefault.getString(
                            StringUtilHtml.transToHTmlPlusAmparsant(
                                    ipkModel.getIadMsg()), ""));
                    paramMdl.setIadPrtMngNum(ipkModel.getIadPrtMngNum());
                    paramMdl.setCpuSelect(String.valueOf(ipkModel.getIadCpu()));
                    paramMdl.setMemorySelect(String.valueOf(ipkModel.getIadMemory()));
                    paramMdl.setHdSelect(String.valueOf(ipkModel.getIadHd()));
                }
            } else {
                String netad = paramMdl.getNetNetad();
                //ネットワークアドレスを分割して配列に格納する。
                String[] netadAry = netad.replaceAll("\\.", ",").split(",");
                if (textNum == 2) {
                    paramMdl.setIadAddText1(netadAry[0]);
                } else if (textNum == 3) {
                    paramMdl.setIadAddText1(netadAry[0]);
                    paramMdl.setIadAddText2(netadAry[1]);
                } else if (textNum == 4) {
                    paramMdl.setIadAddText1(netadAry[0]);
                    paramMdl.setIadAddText2(netadAry[1]);
                    paramMdl.setIadAddText3(netadAry[2]);
                }
            }

            //IPアドレス添付ファイル一覧を設定
            //公開
            CommonBiz cmnBiz = new CommonBiz();
            paramMdl.setIpk050KoukaiFileLabelList(
                    cmnBiz.getTempFileLabelList(
                            tempDir + IpkConst.IPK_TEMP_KOUKAI));
            //非公開
            paramMdl.setIpk050HikoukaiFileLabelList(
                    cmnBiz.getTempFileLabelList(
                            tempDir + IpkConst.IPK_TEMP_HIKOUKAI));
       } catch (SQLException e) {
         throw e;
       }
    }

    /**
     * <br>[機  能] 画面の再表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitDataAg(Ipk050ParamModel paramMdl, Connection con,
            String appRoot, String tempDir,
            RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {
        try {
            //ネットワーク情報を取得する
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            IpkBiz ipkBiz = new IpkBiz();
            int netSid = NullDefault.getInt(paramMdl.getIpk050NetSid(), -2);
            ArrayList<IpkNetModel> ret = ipkNetDao.selectNetwork(netSid);
            //フォームにセットする。
            for (IpkNetModel ipkModel : ret) {
                paramMdl.setNetName(String.valueOf(ipkModel.getNetName()));
                paramMdl.setNetNetad(ipkModel.getNetNetad());
                paramMdl.setNetSabnet(ipkModel.getNetSabnet());
                //コメントをhtml表示用に変換
                paramMdl.setNetMsg(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(
                                     ipkModel.getNetMsg()), ""));
            }

            if (paramMdl.isIpk050AdminFlg()) {
                //CPU、メモリ、HDコンボを設定する。
                __setCpuSelect(paramMdl, con, reqMdl);
            } else {
                //CPU、メモリ、HD名を設定する。
                __setCpuName(paramMdl, con);
            }

            //ネットワーク添付ファイルをテンポラリディレクトリへ移動する
            ipkBiz.readIpkBinData(con, NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                    IpkConst.NETWORK_TOUROKU, appRoot, tempDir + IpkConst.NETWORK + "/", reqMdl);

            //ネットワーク添付ファイル一覧を設定(公開)
            ArrayList <IpkBinModel> koukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
            paramMdl.setKoukaiBinFileInfList(koukaiBinList);

            //ネットワーク添付ファイル一覧を設定(非公開)
            ArrayList <IpkBinModel> hikoukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
            paramMdl.setHikoukaiBinFileInfList(hikoukaiBinList);
            //添付ファイル存在フラグ
            if (!paramMdl.getKoukaiBinFileInfList().isEmpty()
                    || !paramMdl.getHikoukaiBinFileInfList().isEmpty()) {
                paramMdl.setIpk050TempExist(IpkConst.IPK_TEMP_EXIST);
            } else {
                paramMdl.setIpk050TempExist(IpkConst.IPK_TEMP_NOT_EXIST);
            }

            //IPアドレス添付ファイル一覧を設定(公開)
            CommonBiz cmnBiz = new CommonBiz();
            paramMdl.setIpk050KoukaiFileLabelList(
                    cmnBiz.getTempFileLabelList(
                            tempDir + IpkConst.IPK_TEMP_KOUKAI));
            //IPアドレス添付ファイル一覧を設定(非公開)
            paramMdl.setIpk050HikoukaiFileLabelList(
                    cmnBiz.getTempFileLabelList(
                            tempDir + IpkConst.IPK_TEMP_HIKOUKAI));

       } catch (SQLException e) {
         throw e;
       }
    }

    /**
     * <br>[機  能] 数字0埋めフォーマット
     * <br>[解  説]
     * <br>[備  考]
     * @param num 数字
     * @param pattern パターン
     * @return フォーマットされた数字文字列
     */
    public static String getDecFormat(long num, String pattern) {
        String snum = Long.toString(num);
        int len = snum.length();
        if (len > pattern.length()) {
            len = snum.length();
        } else {
            len = pattern.length();
        }
        StringBuffer sb = new StringBuffer(len);
        FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
        DecimalFormat df = new DecimalFormat(pattern);
        df.format(num, sb, fp);
        return sb.toString();
    }

    /**
     * <br>[機  能] 入力チェック用モデルを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return String 存在するIPアドレス数
     * @throws SQLException SQL実行時例外
     */
    public ValidateCheckModel getvalidateCheckModel(Ipk050ParamModel paramMdl, Connection con)
    throws SQLException {
        int ipadCount = 0;
        ValidateCheckModel model = new ValidateCheckModel();
        try {
            // IPアドレスリストを取得する。
            String strIpad = "";
            strIpad = StringUtil.toDecFormat(paramMdl.getIadAddText1(), IpkConst.IPAD_FORMAT_PART)
                    + StringUtil.toDecFormat(paramMdl.getIadAddText2(), IpkConst.IPAD_FORMAT_PART)
                    + StringUtil.toDecFormat(paramMdl.getIadAddText3(), IpkConst.IPAD_FORMAT_PART)
                    + StringUtil.toDecFormat(paramMdl.getIadAddText4(), IpkConst.IPAD_FORMAT_PART);
            long ipad = Long.parseLong(strIpad);
            IpkAddDao ipkAddDao = new IpkAddDao(con);
            IpkAddModel ipkAddModel = new IpkAddModel();
            ipkAddModel.setNetSid(NullDefault.getInt(paramMdl.getIpk050NetSid(), 0));
            ipkAddModel.setCmd(NullDefault.getString(paramMdl.getCMD(), ""));
            ipkAddModel.setIadIpad(ipad);
            ArrayList<IpkAddModel> iadList = ipkAddDao.selectIad(
                    NullDefault.getInt(paramMdl.getIadSid(), 0));
            long beforeIpad = 0;
            for (IpkAddModel mdl : iadList) {
                beforeIpad = mdl.getIadIpad();
            }
            //同じIPアドレス数を取得する。
            ipkAddModel.setBeforeIpad(beforeIpad);
            ipadCount = ipkAddDao.countIpad(ipkAddModel);
            model.setIpadCount(ipadCount);

            //ネットワーク情報をモデルにセットする。
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            int netSid = NullDefault.getInt(paramMdl.getIpk050NetSid(), -2);
            ArrayList<IpkNetModel> ret = ipkNetDao.selectNetwork(netSid);
            for (IpkNetModel ipkModel : ret) {
                model.setNetworkAddress(ipkModel.getNetNetad());
                model.setSubnetMask(ipkModel.getNetSabnet());
            }
            //IPアドレスを一つにまとめる。
            String ipAddress = paramMdl.getIadAddText1()
                + "." + paramMdl.getIadAddText2()
                + "." + paramMdl.getIadAddText3()
                + "." + paramMdl.getIadAddText4();
            model.setIpAddress(ipAddress);
        } catch (SQLException e) {
            throw e;
        }
        return model;
    }

    /**
     * <br>[機  能] 添付ファイル一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリパス
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void setTempList(Ipk050ParamModel paramMdl, String tempDir)
    throws IOToolsException {
        CommonBiz cmnBiz = new CommonBiz();

        //公開
        paramMdl.setIpk050KoukaiFileLabelList(
                cmnBiz.getTempFileLabelList(tempDir + IpkConst.IPK_TEMP_KOUKAI));
        //非公開
        paramMdl.setIpk050HikoukaiFileLabelList(
                cmnBiz.getTempFileLabelList(tempDir + IpkConst.IPK_TEMP_HIKOUKAI));
    }

    /**
     * <br>[機  能] IPアドレス情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException 実行例外
     */
    public void deleteIpad(int netSid, int iadSid, Connection con, int sessionUsrSid)
    throws SQLException {

        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            //ネットワーク情報のデータ使用量を登録(削除前のデータ使用量を減算)
            IpkUsedDataBiz usedDataBiz = new IpkUsedDataBiz(con);
            usedDataBiz.insertNetworkSize(netSid, false);

            IpkAddDao ipkAddDao = new IpkAddDao(con);
            IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);

            //IPアドレス情報の削除
            ipkAddDao.delete(iadSid);

            //IPアドレス管理者情報の削除
            ipkAddAdmDao.delete(iadSid);
            commitFlg = true;

            //バイナリー情報の論理削除、添付情報の削除を行う
            IpkBinDao ipkBinDao = new IpkBinDao(con);
            UDate now = new UDate();
            ipkBinDao.removeIadBinData(iadSid, sessionUsrSid, now);
            ipkBinDao.deleteIadTemp(netSid, iadSid);

            //ネットワーク情報のデータ使用量を登録(削除後のデータ使用量を登録)
            usedDataBiz.insertNetworkSize(netSid, true);
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

    /**
     * <br>[機  能]GS、IP、ネットワーク、IPアドレスの管理権限の有無を判定する。
     * <br>[解  説]
     * <br>[備  考]一つでも権限があればtrue
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return errorFlg 権限有り:true、権限なし:false
     * @throws SQLException SQL実行時例外
     */
    public boolean isIpadAdmin(
            int netSid, int iadSid, RequestModel reqMdl, Connection con)
    throws SQLException {

        try {
            //セッションユーザSIDを取得
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            //GS管理者かIP管理者ならば、TRUE。
            IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
            if (ipkAdminInfo.isGsIpAdm(reqMdl, con)) {
                return IpkConst.KANRI_KENGEN_ARI;
            }

            //ネットワークの管理者か判定する。
            IpkNetAdmDao netAdmDao = new IpkNetAdmDao(con);
            int admNetCount = netAdmDao.countUsrAdmNet(netSid,
                               sessionUsrSid);
            if (admNetCount > 0) {
                 return IpkConst.KANRI_KENGEN_ARI;
            }

            if (iadSid != 0) {
                //IPアドレスの管理者か判定する。
                IpkAddAdmDao addAdmDao = new IpkAddAdmDao(con);
                int count = addAdmDao.countUsrAdmIad(iadSid, sessionUsrSid);
                if (count > 0) {
                    return IpkConst.KANRI_KENGEN_ARI;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return IpkConst.KANRI_KENGEN_NASI;
    }

    /**
     * <br>[機  能] CPU、メモリ、HDコンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setCpuSelect(Ipk050ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textSelect = gsMsg.getMessage("cmn.select.plz");
        //CPUコンボを設定する。
        IpkSpecMstDao dao =  new IpkSpecMstDao(con);
        ArrayList<LabelValueBean> cpuLavelList = new  ArrayList <LabelValueBean>();
        cpuLavelList.add(new LabelValueBean(textSelect, "0"));
        String ismSid = "";
        ArrayList<IpkSpecMstModel> cpuInfList = dao.select(IpkConst.IPK_SPECKBN_CPU);
        for (IpkSpecMstModel model : cpuInfList) {
            ismSid = String.valueOf(model.getIsmSid());
            cpuLavelList.add(new LabelValueBean(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getIpk100name()), ismSid));
        }
        paramMdl.setIpk050cpuLabelList(cpuLavelList);

        //メモリコンボを設定する。
        ArrayList<IpkSpecMstModel> memoryInfList = new ArrayList<IpkSpecMstModel>();
        ArrayList<LabelValueBean> memoryLavelList = new  ArrayList <LabelValueBean>();
        memoryLavelList.add(new LabelValueBean(textSelect, "0"));
        memoryInfList = dao.select(IpkConst.IPK_SPECKBN_MEMORY);
        for (IpkSpecMstModel model : memoryInfList) {
            ismSid = String.valueOf(model.getIsmSid());
            memoryLavelList.add(new LabelValueBean(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getIpk100name()), ismSid));
        }
        paramMdl.setIpk050memoryLabelList(memoryLavelList);

        //HDコンボを設定する。
        ArrayList<IpkSpecMstModel> hdInfList = new ArrayList<IpkSpecMstModel>();
        ArrayList<LabelValueBean> hdLavelList = new  ArrayList <LabelValueBean>();
        hdLavelList.add(new LabelValueBean(textSelect, "0"));
        hdInfList = dao.select(IpkConst.IPK_SPECKBN_HD);
        for (IpkSpecMstModel model : hdInfList) {
            ismSid = String.valueOf(model.getIsmSid());
            hdLavelList.add(new LabelValueBean(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getIpk100name()), ismSid));
        }
        paramMdl.setIpk050hdLabelList(hdLavelList);
    }

    /**
     * <br>[機  能] CPU、メモリ、HDの名前を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCpuName(Ipk050ParamModel paramMdl, Connection con)
    throws SQLException {

        //CPU名を設定する。
        IpkSpecMstDao dao =  new IpkSpecMstDao(con);
        String cpuName = dao.selectNameIpad(
                NullDefault.getInt(paramMdl.getIadSid(), 0), IpkConst.IPK_SPECKBN_CPU);
        paramMdl.setCpuName(cpuName);

        //メモリ名を設定する。
        String memoryName = dao.selectNameIpad(
                NullDefault.getInt(paramMdl.getIadSid(), 0), IpkConst.IPK_SPECKBN_MEMORY);
        paramMdl.setMemoryName(memoryName);

        //HD名を設定する。
        String hdName = dao.selectNameIpad(
                NullDefault.getInt(paramMdl.getIadSid(), 0), IpkConst.IPK_SPECKBN_HD);
        paramMdl.setHdName(hdName);
    }
}