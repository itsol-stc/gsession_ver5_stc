package jp.groupsession.v2.tcd.tcd110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.tcd030.Tcd030Form;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * <br>[機  能] タイムカード 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd110Form extends Tcd030Form implements ISelectorUseForm {

    /** 出力形式 Excel */
    public static final int OUTPUT_TYPE_EXCEL = 0;
    /** 出力形式 PDF */
    public static final int OUTPUT_TYPE_PDF = 1;

    /** 初期表示フラグ */
    private int tcd110initFlg__ = 0;
    /** 出力対象 年 */
    private int tcd110Year__ = 0;
    /** 出力対象 月 */
    private int tcd110Month__ = 0;

    /** 出力対象 年 コンボ */
    private List<LabelValueBean> tcd110YearLabelList__ = null;
    /** 出力対象 月 コンボ */
    private List<LabelValueBean> tcd110MonthLabelList__ = null;

    /** 出力対象 選択中グループ */
    private int tcd110targetGroup__  = -1;
    /** 出力対象 */
    private String[] tcd110target__ = null;
    /** 出力対象 UI */
    private UserGroupSelector tcd110targetUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("tcd.tcd110.02", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "tcd110target")
                    )
                .chainGroupSelectionParamName("tcd110targetGroup")
                .build();

    /** 出力形式 0:Excel, 1:PDF */
    private int tcd110OutputFileType__ = 0;
    /** 勤務表フォーマット存在フラグ */
    private int kinmuFormatExists__;


    /**
     * <p>tcd110initFlg を取得します。
     * @return tcd110initFlg
     */
    public int getTcd110initFlg() {
        return tcd110initFlg__;
    }

    /**
     * <p>tcd110initFlg をセットします。
     * @param tcd110initFlg tcd110initFlg
     */
    public void setTcd110initFlg(int tcd110initFlg) {
        tcd110initFlg__ = tcd110initFlg;
    }

    /**
     * <p>tcd110Year を取得します。
     * @return tcd110Year
     */
    public int getTcd110Year() {
        return tcd110Year__;
    }

    /**
     * <p>tcd110Year をセットします。
     * @param tcd110Year tcd110Year
     */
    public void setTcd110Year(int tcd110Year) {
        tcd110Year__ = tcd110Year;
    }

    /**
     * <p>tcd110Month を取得します。
     * @return tcd110Month
     */
    public int getTcd110Month() {
        return tcd110Month__;
    }

    /**
     * <p>tcd110Month をセットします。
     * @param tcd110Month tcd110Month
     */
    public void setTcd110Month(int tcd110Month) {
        tcd110Month__ = tcd110Month;
    }

    /**
     * <p>tcd110YearLabelList を取得します。
     * @return tcd110YearLabelList
     */
    public List<LabelValueBean> getTcd110YearLabelList() {
        return tcd110YearLabelList__;
    }

    /**
     * <p>tcd110YearLabelList をセットします。
     * @param tcd110YearLabelList tcd110YearLabelList
     */
    public void setTcd110YearLabelList(List<LabelValueBean> tcd110YearLabelList) {
        tcd110YearLabelList__ = tcd110YearLabelList;
    }

    /**
     * <p>tcd110MonthLabelList を取得します。
     * @return tcd110MonthLabelList
     */
    public List<LabelValueBean> getTcd110MonthLabelList() {
        return tcd110MonthLabelList__;
    }

    /**
     * <p>tcd110MonthLabelList をセットします。
     * @param tcd110MonthLabelList tcd110MonthLabelList
     */
    public void setTcd110MonthLabelList(List<LabelValueBean> tcd110MonthLabelList) {
        tcd110MonthLabelList__ = tcd110MonthLabelList;
    }

    /**
     * <p>tcd110target を取得します。
     * @return tcd110target
     */
    public String[] getTcd110target() {
        return tcd110target__;
    }

    /**
     * <p>tcd110target をセットします。
     * @param tcd110target tcd110target
     */
    public void setTcd110target(String[] tcd110target) {
        tcd110target__ = tcd110target;
    }

    /**
     * <p>tcd110targetGroup を取得します。
     * @return tcd110targetGroup
     */
    public int getTcd110targetGroup() {
        return tcd110targetGroup__;
    }

    /**
     * <p>tcd110targetGroup をセットします。
     * @param tcd110targetGroup tcd110targetGroup
     */
    public void setTcd110targetGroup(int tcd110targetGroup) {
        tcd110targetGroup__ = tcd110targetGroup;
    }

    /**
     * <p>tcd110targetUI を取得します。
     * @return tcd110targetUI
     * @see jp.groupsession.v2.tcd.tcd110.Tcd110Form#tcd110targetUI__
     */
    public UserGroupSelector getTcd110targetUI() {
        return tcd110targetUI__;
    }

    /**
     * <p>tcd110targetUI をセットします。
     * @param tcd110targetUI tcd110targetUI
     * @see jp.groupsession.v2.tcd.tcd110.Tcd110Form#tcd110targetUI__
     */
    public void setTcd110targetUI(UserGroupSelector tcd110targetUI) {
        tcd110targetUI__ = tcd110targetUI;
    }

    /**
     * <p>tcd110OutputFileType を取得します。
     * @return tcd110OutputFileType
     */
    public int getTcd110OutputFileType() {
        return tcd110OutputFileType__;
    }

    /**
     * <p>tcd110OutputFileType をセットします。
     * @param tcd110OutputFileType tcd110OutputFileType
     */
    public void setTcd110OutputFileType(int tcd110OutputFileType) {
        tcd110OutputFileType__ = tcd110OutputFileType;
    }

    /**
     * <p>kinmuFormatExists を取得します。
     * @return kinmuFormatExists
     * @see jp.groupsession.v2.tcd.tcd110.Tcd110Form#kinmuFormatExists__
     */
    public int getKinmuFormatExists() {
        return kinmuFormatExists__;
    }

    /**
     * <p>kinmuFormatExists をセットします。
     * @param kinmuFormatExists kinmuFormatExists
     * @see jp.groupsession.v2.tcd.tcd110.Tcd110Form#kinmuFormatExists__
     */
    public void setKinmuFormatExists(int kinmuFormatExists) {
        kinmuFormatExists__ = kinmuFormatExists;
    }

    /**
     * <br>[機  能] タイムカード基本設定画面の入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //出力対象 入力チェック
        //制限対象入力チェック
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        List<String> usrSids = new ArrayList<String>();
        if (tcd110target__ != null) {
            for (String target : tcd110target__) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
        }
        int count = 0;
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
        if (glist != null) {
            count += glist.size();
        }
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        if (ulist != null) {
            count += ulist.size();
        }
        if (count == 0) {
            msg = new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("tcd.tcd110.03"));
            StrutsUtil.addMessage(errors, msg, "tcd110target" + "error.select.required.text");
        }
        return errors;
    }

}
