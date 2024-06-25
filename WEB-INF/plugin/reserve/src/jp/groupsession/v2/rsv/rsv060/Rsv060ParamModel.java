package jp.groupsession.v2.rsv.rsv060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv050.Rsv050ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設グループ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv060ParamModel extends Rsv050ParamModel {

    /** 初期表示フラグ */
    private boolean rsv060InitFlg__ = true;
    /** 処理モード */
    private String rsv060ProcMode__ = getRsv060ProcMode();
    /** 修正対象の施設グループSID */
    private int rsv060EditGrpSid__ = -1;
    /** 施設グループ名 */
    private String rsv060GrpName__ = null;
    /** 施設グループID */
    private String rsv060GrpId__ = null;
    /** 施設区分選択値 */
    private int rsv060SelectedSisetuKbn__ = -1;
    /** 施設区分コンボリスト */
    private ArrayList<LabelValueBean> rsv060SisetuLabelList__ = null;

    /** グループ管理者 グループコンボ選択値 */
    private int rsv060SelectedGrpComboSid__ = -1;
    /** グループ管理者 選択済みユーザ */
    private String[] saveUser__ = null;
    /** グループ管理者 UI */
    private UserGroupSelector saveUserUI__ = null;

    /** 権限設定 */
    private int rsv060GrpAdmKbn__ = -1;
    /** 所属施設リスト */
    private ArrayList<RsvSisDataModel> rsv060SyozokuList__ = null;
    /** データ存在フラグ */
    private boolean rsv060DataExists__ = true;
    /** 施設予約の承認 */
    private int rsv060apprKbn__ = 0;

    /** アクセス権限 グループSID */
    private int rsv060groupSid__ = Integer.parseInt(GSConstReserve.GROUP_COMBO_VALUE);
    /** メンバーSID 予約可能 */
    private String[] rsv060memberSid__ = new String[0];
    /** メンバーSID 閲覧 */
    private String[] rsv060memberSidRead__ = new String[0];
    /** アクセス権限 UI */
    private UserGroupSelector rsv060memberUI__ = null;

    /** 制限方法 */
    private int rsv060AccessKbn__ = GSConstReserve.RSV_ACCESS_MODE_PERMIT;
    /** アクセス制限表示フラグ */
    private boolean rsv060AccessDspFlg__ = false;


    /**
     * <p>rsv060EditGrpSid__ を取得します。
     * @return rsv060EditGrpSid
     */
    public int getRsv060EditGrpSid() {
        return rsv060EditGrpSid__;
    }
    /**
     * <p>rsv060EditGrpSid__ をセットします。
     * @param rsv060EditGrpSid rsv060EditGrpSid__
     */
    public void setRsv060EditGrpSid(int rsv060EditGrpSid) {
        rsv060EditGrpSid__ = rsv060EditGrpSid;
    }
    /**
     * <p>rsv060GrpAdmKbn__ を取得します。
     * @return rsv060GrpAdmKbn
     */
    public int getRsv060GrpAdmKbn() {
        return rsv060GrpAdmKbn__;
    }
    /**
     * <p>rsv060GrpAdmKbn__ をセットします。
     * @param rsv060GrpAdmKbn rsv060GrpAdmKbn__
     */
    public void setRsv060GrpAdmKbn(int rsv060GrpAdmKbn) {
        rsv060GrpAdmKbn__ = rsv060GrpAdmKbn;
    }
    /**
     * <p>rsv060GrpName__ を取得します。
     * @return rsv060GrpName
     */
    public String getRsv060GrpName() {
        return rsv060GrpName__;
    }
    /**
     * <p>rsv060GrpName__ をセットします。
     * @param rsv060GrpName rsv060GrpName__
     */
    public void setRsv060GrpName(String rsv060GrpName) {
        rsv060GrpName__ = rsv060GrpName;
    }
    /**
     * <p>rsv060InitFlg__ を取得します。
     * @return rsv060InitFlg
     */
    public boolean isRsv060InitFlg() {
        return rsv060InitFlg__;
    }
    /**
     * <p>rsv060InitFlg__ をセットします。
     * @param rsv060InitFlg rsv060InitFlg__
     */
    public void setRsv060InitFlg(boolean rsv060InitFlg) {
        rsv060InitFlg__ = rsv060InitFlg;
    }
    /**
     * <p>rsv060ProcMode__ を取得します。
     * @return rsv060ProcMode
     */
    public String getRsv060ProcMode() {
        return rsv060ProcMode__;
    }
    /**
     * <p>rsv060ProcMode__ をセットします。
     * @param rsv060ProcMode rsv060ProcMode__
     */
    public void setRsv060ProcMode(String rsv060ProcMode) {
        rsv060ProcMode__ = rsv060ProcMode;
    }
    /**
     * <p>rsv060SelectedGrpComboSid__ を取得します。
     * @return rsv060SelectedGrpComboSid
     */
    public int getRsv060SelectedGrpComboSid() {
        return rsv060SelectedGrpComboSid__;
    }
    /**
     * <p>rsv060SelectedGrpComboSid__ をセットします。
     * @param rsv060SelectedGrpComboSid rsv060SelectedGrpComboSid__
     */
    public void setRsv060SelectedGrpComboSid(int rsv060SelectedGrpComboSid) {
        rsv060SelectedGrpComboSid__ = rsv060SelectedGrpComboSid;
    }
    /**
     * <p>rsv060SelectedSisetuKbn__ を取得します。
     * @return rsv060SelectedSisetuKbn
     */
    public int getRsv060SelectedSisetuKbn() {
        return rsv060SelectedSisetuKbn__;
    }
    /**
     * <p>rsv060SelectedSisetuKbn__ をセットします。
     * @param rsv060SelectedSisetuKbn rsv060SelectedSisetuKbn__
     */
    public void setRsv060SelectedSisetuKbn(int rsv060SelectedSisetuKbn) {
        rsv060SelectedSisetuKbn__ = rsv060SelectedSisetuKbn;
    }
    /**
     * <p>rsv060SisetuLabelList__ を取得します。
     * @return rsv060SisetuLabelList
     */
    public ArrayList<LabelValueBean> getRsv060SisetuLabelList() {
        return rsv060SisetuLabelList__;
    }
    /**
     * <p>rsv060SisetuLabelList__ をセットします。
     * @param rsv060SisetuLabelList rsv060SisetuLabelList__
     */
    public void setRsv060SisetuLabelList(
            ArrayList<LabelValueBean> rsv060SisetuLabelList) {
        rsv060SisetuLabelList__ = rsv060SisetuLabelList;
    }
    /**
     * <p>rsv060SyozokuList__ を取得します。
     * @return rsv060SyozokuList
     */
    public ArrayList<RsvSisDataModel> getRsv060SyozokuList() {
        return rsv060SyozokuList__;
    }
    /**
     * <p>rsv060SyozokuList__ をセットします。
     * @param rsv060SyozokuList rsv060SyozokuList__
     */
    public void setRsv060SyozokuList(ArrayList<RsvSisDataModel> rsv060SyozokuList) {
        rsv060SyozokuList__ = rsv060SyozokuList;
    }
    /**
     * <p>saveUser__ を取得します。
     * @return saveUser
     */
    public String[] getSaveUser() {
        return saveUser__;
    }
    /**
     * <p>saveUser__ をセットします。
     * @param saveUser saveUser__
     */
    public void setSaveUser(String[] saveUser) {
        saveUser__ = saveUser;
    }
    /**
     * <p>saveUserUI を取得します。
     * @return saveUserUI
     * @see jp.groupsession.v2.rsv.rsv060.Rsv060ParamModel#saveUserUI__
     */
    public UserGroupSelector getSaveUserUI() {
        return saveUserUI__;
    }
    /**
     * <p>saveUserUI をセットします。
     * @param saveUserUI saveUserUI
     * @see jp.groupsession.v2.rsv.rsv060.Rsv060ParamModel#saveUserUI__
     */
    public void setSaveUserUI(UserGroupSelector saveUserUI) {
        saveUserUI__ = saveUserUI;
    }
    /**
     * <p>rsv060DataExists__ を取得します。
     * @return rsv060DataExists
     */
    public boolean isRsv060DataExists() {
        return rsv060DataExists__;
    }
    /**
     * <p>rsv060DataExists__ をセットします。
     * @param rsv060DataExists rsv060DataExists__
     */
    public void setRsv060DataExists(boolean rsv060DataExists) {
        rsv060DataExists__ = rsv060DataExists;
    }
    /**
     * <p>rsv060GrpId を取得します。
     * @return rsv060GrpId
     */
    public String getRsv060GrpId() {
        return rsv060GrpId__;
    }
    /**
     * <p>rsv060GrpId をセットします。
     * @param rsv060GrpId rsv060GrpId
     */
    public void setRsv060GrpId(String rsv060GrpId) {
        rsv060GrpId__ = rsv060GrpId;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //施設グループ名 未入力チェック
        if (StringUtil.isNullZeroString(rsv060GrpName__)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage(req, "cmn.group.name"));
            StrutsUtil.addMessage(errors, msg, "rsv060GrpName");
        } else {

            //施設グループ名 桁数チェック
            if (rsv060GrpName__.length() > GSConstReserve.MAX_LENGTH_GROUP_NAME
                    && errors.size() == 0) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "cmn.group.name"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_GROUP_NAME));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpName");
            }

            //施設グループ名 スペースのみチェック
            if (ValidateUtil.isSpace(rsv060GrpName__) && errors.size() == 0) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "cmn.group.name"));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpName__");
            }

            //施設グループ名 先頭スペースチェック
            if (ValidateUtil.isSpaceStart(rsv060GrpName__) && errors.size() == 0) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "cmn.group.name"));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpName");
            }

            //施設グループ名 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseString(rsv060GrpName__) && errors.size() == 0) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv060GrpName__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(req, "cmn.group.name"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, "bbs030forumName");
            }
        }

        //施設グループID
        if (!StringUtil.isNullZeroString(rsv060GrpId__)) {
            RsvSisGrpDao rsgDao = new RsvSisGrpDao(con);
            RsvSisGrpModel rsgMdl = rsgDao.getGrpIdData(rsv060GrpId__, rsv060EditGrpSid__);

            //重複チェック
            if (rsgMdl != null) {
                msg =
                    new ActionMessage("error.input.double.timezone",
                            gsMsg.getMessage(req, "cmn.group.id"),
                            gsMsg.getMessage(req, "cmn.group.id"));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpId");
                return errors;
            }

            //施設グループID 桁数チェック
            if (rsv060GrpId__.length() > GSConstReserve.MAX_LENGTH_GROUP_ID) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "cmn.group.id"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_GROUP_ID));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpId");
                return errors;
            }
            //施設グループID スペースのみチェック
            if (ValidateUtil.isSpace(rsv060GrpId__)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "cmn.group.id"));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpId__");
                return errors;
            }
            //施設グループID 先頭スペースチェック
            if (ValidateUtil.isSpaceStart(rsv060GrpId__)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "cmn.group.id"));
                StrutsUtil.addMessage(errors, msg, "rsv060GrpId");
                return errors;
            }
            //施設グループID JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseString(rsv060GrpId__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv060GrpId__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(req, "cmn.group.id"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv060GrpId");
            }
        }

        return errors;
    }
    /**
     * <p>rsv060memberSid を取得します。
     * @return rsv060memberSid
     */
    public String[] getRsv060memberSid() {
        return rsv060memberSid__;
    }
    /**
     * <p>rsv060memberSid をセットします。
     * @param rsv060memberSid rsv060memberSid
     */
    public void setRsv060memberSid(String[] rsv060memberSid) {
        rsv060memberSid__ = rsv060memberSid;
    }
    /**
     * <p>rsv060memberSidRead を取得します。
     * @return rsv060memberSidRead
     */
    public String[] getRsv060memberSidRead() {
        return rsv060memberSidRead__;
    }
    /**
     * <p>rsv060memberSidRead をセットします。
     * @param rsv060memberSidRead rsv060memberSidRead
     */
    public void setRsv060memberSidRead(String[] rsv060memberSidRead) {
        rsv060memberSidRead__ = rsv060memberSidRead;
    }
    /**
     * <p>rsv060memberUI を取得します。
     * @return rsv060memberUI
     * @see jp.groupsession.v2.rsv.rsv060.Rsv060ParamModel#rsv060memberUI__
     */
    public UserGroupSelector getRsv060memberUI() {
        return rsv060memberUI__;
    }
    /**
     * <p>rsv060memberUI をセットします。
     * @param rsv060memberUI rsv060memberUI
     * @see jp.groupsession.v2.rsv.rsv060.Rsv060ParamModel#rsv060memberUI__
     */
    public void setRsv060memberUI(UserGroupSelector rsv060memberUI) {
        rsv060memberUI__ = rsv060memberUI;
    }
    /**
     * <p>rsv060AccessKbn を取得します。
     * @return rsv060AccessKbn
     */
    public int getRsv060AccessKbn() {
        return rsv060AccessKbn__;
    }
    /**
     * <p>rsv060AccessKbn をセットします。
     * @param rsv060AccessKbn rsv060AccessKbn
     */
    public void setRsv060AccessKbn(int rsv060AccessKbn) {
        rsv060AccessKbn__ = rsv060AccessKbn;
    }
    /**
     * <p>rsv060groupSid を取得します。
     * @return rsv060groupSid
     */
    public int getRsv060groupSid() {
        return rsv060groupSid__;
    }
    /**
     * <p>rsv060groupSid をセットします。
     * @param rsv060groupSid rsv060groupSid
     */
    public void setRsv060groupSid(int rsv060groupSid) {
        rsv060groupSid__ = rsv060groupSid;
    }
    /**
     * <p>rsv060AccessDspFlg を取得します。
     * @return rsv060AccessDspFlg
     */
    public boolean isRsv060AccessDspFlg() {
        return rsv060AccessDspFlg__;
    }
    /**
     * <p>rsv060AccessDspFlg をセットします。
     * @param rsv060AccessDspFlg rsv060AccessDspFlg
     */
    public void setRsv060AccessDspFlg(boolean rsv060AccessDspFlg) {
        rsv060AccessDspFlg__ = rsv060AccessDspFlg;
    }
    /**
     * @return rsv060apprKbn
     */
    public int getRsv060apprKbn() {
        return rsv060apprKbn__;
    }
    /**
     * @param rsv060apprKbn 設定する rsv060apprKbn
     */
    public void setRsv060apprKbn(int rsv060apprKbn) {
        rsv060apprKbn__ = rsv060apprKbn;
    }

}