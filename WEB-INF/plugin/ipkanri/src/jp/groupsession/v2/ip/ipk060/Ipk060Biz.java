package jp.groupsession.v2.ip.ipk060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.ip.model.IpkNetModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] IP管理 IPアドレスインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk060Biz.class);

    /**
     * <br>[機  能] 初期表示の設定をする。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(
            Ipk060ParamModel paramMdl, Connection con, RequestModel reqMdl, int sessionUsrSid)
    throws SQLException, TempFileException {

        try {
            //ネットワーク情報を取得する
            __setNetwork(paramMdl, con, reqMdl);

            //セッションユーザがGS管理者か、全ネットワーク管理者であるかを判定
            IpkAdminInfo ipkKanrisyaKubun = new IpkAdminInfo();
            boolean allAdmin = ipkKanrisyaKubun.isGsIpAdm(reqMdl, con);

            //ネットワーク管理者であるか判定
            IpkNetAdmDao ipkNetAdmDao = new IpkNetAdmDao(con);
            int netAdmCount = ipkNetAdmDao.countUsrAdmNet(
                    NullDefault.getInt(paramMdl.getNetSid(), 0), sessionUsrSid);
            boolean netAdmFlg = IpkConst.KANRI_KENGEN_NASI;
            if (netAdmCount > 0) {
                netAdmFlg = IpkConst.KANRI_KENGEN_ARI;
            }

            if (allAdmin || netAdmFlg) {
                paramMdl.setIadNetAdmFlg(IpkConst.KANRI_KENGEN_ARI);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] IPアドレス情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException 実行例外
     */
    public void deleteNetwork(Ipk060ParamModel paramMdl, Connection con, int sessionUsrSid)
    throws SQLException {
        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            int netSid = NullDefault.getInt(paramMdl.getNetSid(), -2);
            IpkAddDao ipkAddDao = new IpkAddDao(con);

            //バイナリー情報の論理削除、添付情報の削除を行う
            IpkBinDao ipkBinDao = new IpkBinDao(con);
            UDate now = new UDate();
            ipkBinDao.removeBinData(NullDefault.getInt(paramMdl.getNetSid(), 0),
                    sessionUsrSid, now);
            ipkBinDao.deleteIadTemp(netSid);

            //ネットワークのIPアドレスリストを取得する。
            ArrayList<String> iadSidList = ipkAddDao.selectIadSid(netSid);
            ArrayList<Integer> intIadSidList = new ArrayList<Integer>();
            if (iadSidList.size() == 0) {
                return;
            }
            for (String iadSid : iadSidList) {
                intIadSidList.add(Integer.parseInt(iadSid));
            }
            //IPアドレスの使用者削除
            IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
            ipkAddAdmDao.deleteHukusuu(intIadSidList);
            //1つのネットワークのIPアドレス削除する。
            ipkAddDao.deleteNetwork(netSid);
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
     * <br>[機  能] 添付ファイルコンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリパス
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void setTempList(Ipk060ParamModel paramMdl, String tempDir)
    throws IOToolsException {
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setIpk060FileLabelList(cmnBiz.getTempFileLabelList(tempDir));
    }

    /**
     * <br>[機  能] インポート画面へのアクセス権限の有無を判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return errorFlg 0:正常 1:アクセス権限無し 2:不正アクセス
     * @throws SQLException SQL実行時例外
     */
    public String isAccessOk(Ipk060ParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {

        try {

            int netSid = NullDefault.getInt(paramMdl.getNetSid(), 0);

            if (StringUtil.isNullZeroString(paramMdl.getCMD())) {
                return IpkConst.ACCESS_ERROR_NO_PARAM;
            }
            String cmd = paramMdl.getCMD();
            //ネットワークSIDが空ならばエラー
            if (StringUtil.isNullZeroString(paramMdl.getNetSid())) {
                return IpkConst.ACCESS_ERROR_NO_PARAM;
            }

            //インポートボタンクリック(キャンセル)
            if (cmd.equals("importCancel")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //インポートボタンクリック
            if (cmd.equals("iadImp")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //インポートボタンOKボタンクリック
            if (cmd.equals("importOk")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }
            //インポートボタンNGボタンクリック
            if (cmd.equals("impNG")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //IPアドレス一覧画面のインポートボタンクリック
            if (cmd.equals("import")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //取り込みファイル削除ボタンクリック
            if (cmd.equals("delFile")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //戻るボタンクリック
            if (cmd.equals("ipk060Return")) {
                return IpkConst.ACCESS_ERROR_OK;
            }

            //添付ファイルリンククリック
            if (cmd.equals("fileDownload")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //変更ボタンクリック
            if (cmd.equals("netEdit")) {
                if (StringUtil.isNullZeroString(paramMdl.getNetSid())) {
                    return IpkConst.ACCESS_ERROR_NO_PARAM;
                }
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //ネットワーク削除ボタンクリック
            if (cmd.equals("netDelete")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //ネットワーク削除OKボタンクリック
            if (cmd.equals("networkDeleteKn")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //ネットワーク削除Canselボタンクリック
            if (cmd.equals("networkDeleteBack")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //添付ファイル削除ボタンクリック(公開)
            if (cmd.equals("delTempFile")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //添付ファイル削除ボタンクリック(非公開)
            if (cmd.equals("delAdmTempFile")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //添付ファイルダウンロードボタンクリック(公開)
            if (cmd.equals("koukaiTempDownload")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

            //添付ファイルダウンロードボタンクリック(非公開)
            if (cmd.equals("hikoukaiTempDownload")) {
                return __isImportAccessOk(netSid, reqMdl, con);
            }

        } catch (SQLException e) {
            throw e;
        }
        return IpkConst.ACCESS_ERROR_NO_PARAM;
    }

    /**
     * <br>[機  能] GS、全ネットワーク、ネットワーク管理者の権限の有無を判定する。
     * <br>[解  説]
     * <br>[備  考] 一つでも権限があればtrue
     * @param netSid ネットワークSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return errorFlg 0:正常 1:アクセス権限無し 2:不正アクセス
     * @throws SQLException SQL実行時例外
     */
    private String __isImportAccessOk(int netSid, RequestModel reqMdl, Connection con)
    throws SQLException {

      try {
          //セッションユーザSIDを取得
          BaseUserModel usModel = reqMdl.getSmodel();
          int sessionUsrSid = usModel.getUsrsid();

          //ネットワークの管理者か判定する。
          IpkNetAdmDao dao = new IpkNetAdmDao(con);
          int admNetCount = dao.countUsrAdmNet(netSid, sessionUsrSid);
          if (admNetCount > 0) {
               return IpkConst.ACCESS_ERROR_OK;
          }
          //GS管理者か全ネットワーク管理者ならば、TRUE。
          IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
          if (ipkAdminInfo.isGsIpAdm(reqMdl, con)) {
              return IpkConst.ACCESS_ERROR_OK;
          }
      } catch (SQLException e) {
          throw e;
      }
      return IpkConst.ACCESS_ERROR_ADMIN;
    }

    /**
     * <br>[機  能] ネットワーク情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __setNetwork(Ipk060ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException, TempFileException {

        IpkNetDao ipkNetDao = new IpkNetDao(con);
        IpkAddDao ipkAddDao = new IpkAddDao(con);

        //ネットワークSID
        int netSid = NullDefault.getInt(paramMdl.getNetSid(), -2);

        //ネットワーク情報を取得する。
        ArrayList<IpkNetModel> ret = ipkNetDao.selectNetwork(netSid);

        //フォームにセットする。
        for (IpkNetModel ipkModel : ret) {
            paramMdl.setNetName(String.valueOf(ipkModel.getNetName()));
            paramMdl.setNetNetad(ipkModel.getNetNetad());
            paramMdl.setNetSabnet(ipkModel.getNetSabnet());

            //コメント
            paramMdl.setNetMsg(NullDefault.getString(
                               StringUtilHtml.transToHTmlPlusAmparsant(
                               ipkModel.getNetMsg()), ""));
        }

        //IPアドレス使用中の登録台数を取得する。
        String iadCountUse = ipkAddDao.countIadUse(NullDefault.getInt(paramMdl.getNetSid(), -2));

        //IPアドレス未使用の登録台数取得
        String iadCountNotUse
            = ipkAddDao.countIadNotUse(NullDefault.getInt(paramMdl.getNetSid(), -2));

        //全IPアドレス数
        String iadCount = String.valueOf(NullDefault.getInt(iadCountUse, 0)
                + NullDefault.getInt(iadCountNotUse, 0));

        paramMdl.setIadCountUse(iadCountUse);
        paramMdl.setIadCountNotUse(iadCountNotUse);
        paramMdl.setIadCount(iadCount);

        IpkBiz ipkBiz = new IpkBiz();
        //添付ファイル一覧を設定(公開)
        ArrayList <IpkBinModel> koukaiBinList = ipkBiz.getTempDataList(
                con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
        paramMdl.setKoukaiBinFileInfList(koukaiBinList);

        //添付ファイル一覧を設定(非公開)
        ArrayList <IpkBinModel> hikoukaiBinList = ipkBiz.getTempDataList(
                con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
        paramMdl.setHikoukaiBinFileInfList(hikoukaiBinList);

        if (!koukaiBinList.isEmpty() || !hikoukaiBinList.isEmpty()) {
            paramMdl.setTempExist(IpkConst.IPK_TEMP_EXIST);
        }
    }
}