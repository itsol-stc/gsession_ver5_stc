package jp.groupsession.v2.ntp.ntp062kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報 インポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp062knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp062knBiz.class);
    /** リクエスモデル */
    public RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp062knBiz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp062knParamModel
     * @param userMdl セッションユーザモデル
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行エラー
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(
            Ntp062knParamModel paramMdl,
            BaseUserModel userMdl,
            Connection con,
            String tempDir) throws SQLException, IOToolsException {

        //担当者
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(Integer.valueOf(GSConstUser.SID_ADMIN));
        ArrayList<Integer> list = null;
        ArrayList<CmnUsrmInfModel> selectUsrList = null;

        String[] users = paramMdl.getSv_users();
        if (users != null && users.length > 0) {
            list = new ArrayList<Integer>();
            for (int i = 0; i < users.length; i++) {
                list.add(Integer.valueOf(users[i]));
                //同時登録ユーザを所属リストから除外する
                usrSids.add(Integer.valueOf(users[i]));
            }

            UserBiz userBiz = new UserBiz();
            selectUsrList = (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con, users);
        }
        paramMdl.setNtp062knSelectUserList(selectUsrList);


        //商品
        if (paramMdl.getNtp061ChkShohinSidList() != null) {
            NtpShohinDao shohinDao = new NtpShohinDao(con);
            List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
            ArrayList<NtpShohinModel> shohinList
                = (ArrayList<NtpShohinModel>) shohinDao.select(
                        paramMdl.getNtp061ChkShohinSidList());

            for (NtpShohinModel mdl : shohinList) {
                labelList.add(
                        new LabelValueBean(mdl.getNhnName(), String.valueOf(mdl.getNhnSid())));
            }
            paramMdl.setNtp061ShohinList(labelList);
        }
        //権限設定ユーザ選択初期化
        GsMessage gsMsg = new GsMessage(reqMdl_);

        UserGroupSelectModel ugsModel = paramMdl.getNtp062NanPermitList();
        ugsModel.setSelected(Ntp061Biz.NAP_USRGRPBOXID_VIEW, paramMdl.getNtp061NanPermitUserView());

        if (paramMdl.getNtp061NanPermitView() == GSConstNippou.NAP_KBN_USERGROUP) {
            //閲覧権限 = 担当者および指定ユーザ の場合
            ugsModel.setSelected(Ntp061Biz.NAP_USRGRPBOXID_EDIT,
                                paramMdl.getNtp061NanPermitUserEdit());
        } else if (paramMdl.getNtp061NanPermitEdit() == GSConstNippou.NAP_KBN_USERGROUP) {
            //編集権限 = 担当者および指定ユーザ の場合
            ugsModel.setSelected(Ntp061Biz.NAP_USRGRPBOXID_EDIT,
                                paramMdl.getNtp061NanPermitEditUser());
        }

        ugsModel.init(con, reqMdl_,
                new String[] {Ntp061Biz.NAP_USRGRPBOXID_VIEW,
                    Ntp061Biz.NAP_USRGRPBOXID_EDIT},
                new String[] {gsMsg.getMessage("address.61"),
                    gsMsg.getMessage("cmn.edit.permissions")},
                "-1", null, paramMdl.getSv_users());

    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private String __getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        log__.debug("添付ファイルの名称 = " + ret);

        return ret;
    }

    /**
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp062knParamModel
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     */
    public void setImportFileName(Ntp062knParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //取込みCSVファイル名称セット
        String fileName = __getFileName(tempDir);
        paramMdl.setNtp062knFileName(fileName);
    }
    /**
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     * @return String 保存しているファイル名
     */
    public String getImportFileName(String tempDir)
        throws SQLException, IOToolsException {

        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                    continue;
                }
                ret = fileName.substring(0, 11);
            }
        }
        return ret;
    }
    /**
     * <br>[機  能] 登録対象の名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp062knParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     */
    public void setImportTargetName(Ntp062knParamModel paramMdl,
                                    String userSid)
        throws SQLException, IOToolsException {

        String impTargetName = "";
        String userId = NullDefault.getString(userSid, "-1");
        log__.debug("userId==>" + userId);
        if (userId.equals(GSConstNippou.USER_NOT_SELECT)) {
            //グループ名取得
            CmnGroupmDao dao = new CmnGroupmDao(con_);
            CmnGroupmModel model = dao.select(NullDefault.getInt(userSid, -1));
            if (model != null) {
                impTargetName = model.getGrpName();
            }
        } else {
            //ユーザ名取得
            CmnUsrmInfDao dao = new CmnUsrmInfDao(con_);
            CmnUsrmInfModel model = dao.select(NullDefault.getInt(userSid, -1));
            if (model != null) {
                impTargetName = model.getUsiSei() + " " + model.getUsiMei();
            }
        }
        paramMdl.setImpTargetName(impTargetName);
    }
}