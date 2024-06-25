package jp.groupsession.v2.prj.prj030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.prj.prj040.Prj040Form;
import jp.groupsession.v2.prj.prj150.model.Prj150DspMdl;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] プロジェクトメイン画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj030Form extends Prj040Form {

    /** プロジェクト編集権限 */
    private boolean prjEdit__;
    /** プラグイン使用区分 (回覧板) */
    private boolean useCircular__;
    /** プラグイン使用区分 (ショートメール) */
    private boolean useSmail__;
    /** プラグイン使用区分 (アドレス帳) */
    private boolean useAddress__;
    /** プラグイン使用区分（在席管理）*/
    private boolean useZaiseki__;
    /** 初期表示 */
    private boolean prj030Init__ = true;

    //入力項目
    /** ソートキー */
    private int prj030sort__ = GSConstProject.SORT_TODO_WEIGHT;
    /** オーダーキー */
    private int prj030order__ = GSConst.ORDER_KEY_DESC;
    /** 送信先SID */
    private String[] prj030sendMember__;
    /** 選択されたTODO */
    private String[] prj030selectTodo__;
    /** 選択されたTODO(文字列) */
    private String prj030selectTodoStr__;
    /** 日付変更休日設定  0:反映させる 1:反映させない */
    private int prj030chDateHol__ = 0;
    /** 日付変更移動区分 0:先 1:前 */
    private int prj030chDateKbn__ = 0;
    /** 日付変更 移動月*/
    private int prj030mvMonth__ = 0;
    /** 日付変更 移動日*/
    private int prj030mvDay__ = 0;
    /** 状態変更 選択された状態 */
    private int prj030selectEditStatus__ = GSConstProject.STATUS_100;

    //表示項目
    /** プロジェクト・TODO情報 */
    private ProjectItemModel projectItem__;
    /** メンバー情報 */
    private List<UserModel> userList__;
    /** プロジェクト状態Model */
    private ProjectStatusModel prjStatusMdl__;
    /** 管理者(プロジェクト管理者)ラベル */
    private List<UsrLabelValueBean> adminMemberLabel__;
    /** メンバー(プロジェクト管理者)ラベル */
    private List<UsrLabelValueBean> memberLabel__;

    //TODO検索条件
    /** 日付コンボ選択値 */
    private String selectingDate__ = null;
    /** 状態コンボ選択値 */
    private String selectingStatus__ = null;
    /** 対象カテゴリコンボ選択値 */
    private String selectingCategory__ = null;
    /** 対象メンバコンボ選択値 */
    private String selectingMember__ = null;
    /** 日付コンボ */
    private List<LabelValueBean> targetDateLabel__;
    /** 状態コンボ */
    private List<LabelValueBean> targetStatusLabel__;
    /** 対象カテゴリコンボ */
    private List<LabelValueBean> targetCategoryLabel__;
    /** 対象メンバコンボ */
    private List<UsrLabelValueBean> targetMemberLabel__;
    /** TODO登録権限 */
    private boolean prjTodoEdit__ = false;

    /** ページ1 */
    private int prj030page1__;
    /** ページ2 */
    private int prj030page2__;
    /** セパレートキー */
    private String prj030SepKey__ = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;

    /** 外部メンバー */
    private List<Prj150DspMdl> outMemberList__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate030(Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //送信先
        String textSendMember = gsMsg.getMessage(req, "project.src.35");
        //送信先
        //未選択チェック
        if (prj030sendMember__ == null || prj030sendMember__.length < 1) {
            msg = new ActionMessage("error.select.required.text", textSendMember);
            StrutsUtil.addMessage(errors, msg, "prj050status.error.select.required.text");

        } else {
            //削除済みチェック
            ArrayList<String> memList = new ArrayList<String>();
            for (String member : prj030sendMember__) {
                memList.add(member);
            }
            CmnUsrmDao udao = new CmnUsrmDao(con);
            int count = udao.getCountDeleteUser(memList);
            if (count > 0) {
                msg = new ActionMessage(
                        "error.select.user.delete", textSendMember);
                StrutsUtil.addMessage(errors, msg, "prj050hdnTanto.error.select.user.delete");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 変更ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buMdl セッションユーザ情報
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateEditStatus(Connection con, BaseUserModel buMdl,
            RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //編集
        String textEdit = gsMsg.getMessage("cmn.edit");
        PrjCommonBiz pcBiz = new PrjCommonBiz(con, gsMsg, reqMdl);
        if (!pcBiz.getTodoEditKengen(getPrj030prjSid(), buMdl)) {
            msg = new ActionMessage("error.not.edit.permissions.project2");
            StrutsUtil.addMessage(errors, msg,
                                "prj030selectEditStatus.error.not.edit.permissions.project");
            return errors;
        }
        //状態
        String textStatus = gsMsg.getMessage("cmn.status");
        //状態
        PrjTodostatusDao statusDao = new PrjTodostatusDao(con);
        if (prj030selectEditStatus__ != GSConstProject.STATUS_100
        && prj030selectEditStatus__ != GSConstProject.STATUS_0
        && !statusDao.existStatus(getPrj030prjSid(), prj030selectEditStatus__)) {
            msg = new ActionMessage("error.select3.required.text", textStatus);
            StrutsUtil.addMessage(errors, msg,
                                "prj030selectEditStatus.error.select3.required.text");
        }

        //選択されたTODO
        if (prj030selectTodo__ == null || prj030selectTodo__.length < 1) {
            msg = new ActionMessage("error.select.required.text", GSConstProject.MSG_TODO);
            StrutsUtil.addMessage(errors, msg, "prj030selectTodo.error.select.required.text");
        }

        if (errors.isEmpty()) {
            List<Integer> prjSidList = new ArrayList<Integer>();
            prjSidList.add(getPrj030prjSid());

            validateCanEditTodo(errors, con, buMdl,
                                prjSidList, "prj030selectTodo",
                                textEdit, reqMdl);
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除(TODO情報)ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buMdl セッションユーザ情報
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateDelTodo(Connection con, BaseUserModel buMdl, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //削除
        String textDelete = gsMsg.getMessage("cmn.delete");
        PrjCommonBiz pcBiz = new PrjCommonBiz(con, gsMsg, reqMdl);
        if (!pcBiz.getTodoEditKengen(getPrj030prjSid(), buMdl)) {
            msg = new ActionMessage("error.not.edit.permissions.project",
                    textDelete);
            StrutsUtil.addMessage(errors, msg,
                                "prj030selectEditStatus.error.not.edit.permissions.project");
            return errors;
        }

        //選択されたTODO
        if (prj030selectTodo__ == null || prj030selectTodo__.length < 1) {
            msg = new ActionMessage("error.select.required.text", GSConstProject.MSG_TODO);
            StrutsUtil.addMessage(errors, msg, "prj030selectTodo.error.select.required.text");
        }

        if (errors.isEmpty()) {
            List<Integer> prjSidList = new ArrayList<Integer>();
            prjSidList.add(getPrj030prjSid());

            validateCanEditTodo(errors, con, buMdl,
                                prjSidList, "prj030selectTodo",
                                textDelete, reqMdl);
        }

        return errors;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {

        super.setcmn999FormParam(cmn999Form);

        cmn999Form.addHiddenParam("prj030scrId", getPrj030scrId());
        cmn999Form.addHiddenParam("prj030prjSid", getPrj030prjSid());
//        cmn999Form.addHiddenParam("prj030mirai", prj030mirai__);
        cmn999Form.addHiddenParam("prj030sort", prj030sort__);
        cmn999Form.addHiddenParam("prj030order", prj030order__);
        cmn999Form.addHiddenParam("prj030sendMember", prj030sendMember__);
        cmn999Form.addHiddenParam("selectingDate", selectingDate__);
        cmn999Form.addHiddenParam("selectingStatus", selectingStatus__);
        cmn999Form.addHiddenParam("selectingCategory", selectingCategory__);
        cmn999Form.addHiddenParam("selectingMember", selectingMember__);
        cmn999Form.addHiddenParam("prj030page1", prj030page1__);
        cmn999Form.addHiddenParam("prj030page2", prj030page2__);
        cmn999Form.addHiddenParam("prj030Init", String.valueOf(isPrj030Init()));
        cmn999Form.addHiddenParam("prj030selectTodo", prj030selectTodo__);
        cmn999Form.addHiddenParam("prj030selectEditStatus", prj030selectEditStatus__);
    }

    /**
     * <br>[機  能] 指定されたTODOが全て編集可能かをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public void validatePrm(HttpServletRequest req) {

        //チェックボックスの値を初期化
        if (req.getParameter("prj030selectTodo") != null) {
            String[] array = new String[0];
            prj030selectTodo__ = array;
        }
    }

    /**
     * <p>prjEdit を取得します。
     * @return prjEdit
     */
    public boolean isPrjEdit() {
        return prjEdit__;
    }

    /**
     * <p>prjEdit をセットします。
     * @param prjEdit prjEdit
     */
    public void setPrjEdit(boolean prjEdit) {
        prjEdit__ = prjEdit;
    }
    /**
     * <p>prj030sort を取得します。
     * @return prj030sort
     */
    public int getPrj030sort() {
        return prj030sort__;
    }

    /**
     * <p>prj030sort をセットします。
     * @param prj030sort prj030sort
     */
    public void setPrj030sort(int prj030sort) {
        prj030sort__ = prj030sort;
    }

    /**
     * <p>prj030order を取得します。
     * @return prj030order
     */
    public int getPrj030order() {
        return prj030order__;
    }

    /**
     * <p>prj030order をセットします。
     * @param prj030order prj030order
     */
    public void setPrj030order(int prj030order) {
        prj030order__ = prj030order;
    }

    /**
     * <p>projectItem を取得します。
     * @return projectItem
     */
    public ProjectItemModel getProjectItem() {
        return projectItem__;
    }

    /**
     * <p>projectItem をセットします。
     * @param projectItem projectItem
     */
    public void setProjectItem(ProjectItemModel projectItem) {
        projectItem__ = projectItem;
    }

    /**
     * <p>userList を取得します。
     * @return userList
     */
    public List<UserModel> getUserList() {
        return userList__;
    }

    /**
     * <p>userList をセットします。
     * @param userList userList
     */
    public void setUserList(List<UserModel> userList) {
        userList__ = userList;
    }
    /**
     * <p>prjStatusMdl を取得します。
     * @return prjStatusMdl
     */
    public ProjectStatusModel getPrjStatusMdl() {
        return prjStatusMdl__;
    }

    /**
     * <p>prjStatusMdl をセットします。
     * @param prjStatusMdl prjStatusMdl
     */
    public void setPrjStatusMdl(ProjectStatusModel prjStatusMdl) {
        prjStatusMdl__ = prjStatusMdl;
    }

    /**
     * <p>adminMemberLabel を取得します。
     * @return adminMemberLabel
     */
    public List<UsrLabelValueBean> getAdminMemberLabel() {
        return adminMemberLabel__;
    }

    /**
     * <p>adminMemberLabel をセットします。
     * @param adminMemberLabel adminMemberLabel
     */
    public void setAdminMemberLabel(List<UsrLabelValueBean> adminMemberLabel) {
        adminMemberLabel__ = adminMemberLabel;
    }

    /**
     * <p>memberLabel を取得します。
     * @return memberLabel
     */
    public List<UsrLabelValueBean> getMemberLabel() {
        return memberLabel__;
    }

    /**
     * <p>memberLabel をセットします。
     * @param memberLabel memberLabel
     */
    public void setMemberLabel(List<UsrLabelValueBean> memberLabel) {
        memberLabel__ = memberLabel;
    }

    /**
     * <p>prj030sendMember を取得します。
     * @return prj030sendMember
     */
    public String[] getPrj030sendMember() {
        return prj030sendMember__;
    }

    /**
     * <p>prj030sendMember をセットします。
     * @param prj030sendMember prj030sendMember
     */
    public void setPrj030sendMember(String[] prj030sendMember) {
        prj030sendMember__ = prj030sendMember;
    }

    /**
     * <p>useCircular を取得します。
     * @return useCircular
     */
    public boolean isUseCircular() {
        return useCircular__;
    }

    /**
     * <p>useCircular をセットします。
     * @param useCircular useCircular
     */
    public void setUseCircular(boolean useCircular) {
        useCircular__ = useCircular;
    }

    /**
     * <p>useSmail を取得します。
     * @return useSmail
     */
    public boolean isUseSmail() {
        return useSmail__;
    }

    /**
     * <p>useSmail をセットします。
     * @param useSmail useSmail
     */
    public void setUseSmail(boolean useSmail) {
        useSmail__ = useSmail;
    }

    /**
     * <p>useAddress を取得します。
     * @return useAddress
     */
    public boolean isUseAddress() {
        return useAddress__;
    }

    /**
     * <p>useAddress をセットします。
     * @param useAddress useAddress
     */
    public void setUseAddress(boolean useAddress) {
        useAddress__ = useAddress;
    }

    /**
     * <p>useZaiseki を取得します。
     * @return useZaiseki
     * @see jp.groupsession.v2.prj.prj030.Prj030Form#useZaiseki__
     */
    public boolean isUseZaiseki() {
        return useZaiseki__;
    }

    /**
     * <p>useZaiseki をセットします。
     * @param useZaiseki useZaiseki
     * @see jp.groupsession.v2.prj.prj030.Prj030Form#useZaiseki__
     */
    public void setUseZaiseki(boolean useZaiseki) {
        useZaiseki__ = useZaiseki;
    }

    /**
     * <p>selectingDate を取得します。
     * @return selectingDate
     */
    public String getSelectingDate() {
        return selectingDate__;
    }

    /**
     * <p>selectingDate をセットします。
     * @param selectingDate selectingDate
     */
    public void setSelectingDate(String selectingDate) {
        selectingDate__ = selectingDate;
    }
    /**
     * <p>selectingStatus を取得します。
     * @return selectingStatus
     */
    public String getSelectingStatus() {
        return selectingStatus__;
    }

    /**
     * <p>selectingStatus をセットします。
     * @param selectingStatus selectingStatus
     */
    public void setSelectingStatus(String selectingStatus) {
        selectingStatus__ = selectingStatus;
    }

    /**
     * <p>targetDateLabel を取得します。
     * @return targetDateLabel
     */
    public List<LabelValueBean> getTargetDateLabel() {
        return targetDateLabel__;
    }

    /**
     * <p>targetDateLabel をセットします。
     * @param targetDateLabel targetDateLabel
     */
    public void setTargetDateLabel(List<LabelValueBean> targetDateLabel) {
        targetDateLabel__ = targetDateLabel;
    }

    /**
     * <p>targetMemberLabel を取得します。
     * @return targetMemberLabel
     */
    public List<UsrLabelValueBean> getTargetMemberLabel() {
        return targetMemberLabel__;
    }

    /**
     * <p>targetMemberLabel をセットします。
     * @param targetMemberLabel targetMemberLabel
     */
    public void setTargetMemberLabel(List<UsrLabelValueBean> targetMemberLabel) {
        targetMemberLabel__ = targetMemberLabel;
    }

    /**
     * <p>targetStatusLabel を取得します。
     * @return targetStatusLabel
     */
    public List<LabelValueBean> getTargetStatusLabel() {
        return targetStatusLabel__;
    }

    /**
     * <p>targetStatusLabel をセットします。
     * @param targetStatusLabel targetStatusLabel
     */
    public void setTargetStatusLabel(List<LabelValueBean> targetStatusLabel) {
        targetStatusLabel__ = targetStatusLabel;
    }
    /**
     * <p>selectingMember を取得します。
     * @return selectingMember
     */
    public String getSelectingMember() {
        return selectingMember__;
    }

    /**
     * <p>selectingMember をセットします。
     * @param selectingMember selectingMember
     */
    public void setSelectingMember(String selectingMember) {
        selectingMember__ = selectingMember;
    }

    /**
     * <p>prjTodoEdit を取得します。
     * @return prjTodoEdit
     */
    public boolean isPrjTodoEdit() {
        return prjTodoEdit__;
    }
    /**
     * <p>prjTodoEdit をセットします。
     * @param prjTodoEdit prjTodoEdit
     */
    public void setPrjTodoEdit(boolean prjTodoEdit) {
        prjTodoEdit__ = prjTodoEdit;
    }

    /**
     * <p>prj030page1 を取得します。
     * @return prj030page1
     */
    public int getPrj030page1() {
        return prj030page1__;
    }

    /**
     * <p>prj030page1 をセットします。
     * @param prj030page1 prj030page1
     */
    public void setPrj030page1(int prj030page1) {
        prj030page1__ = prj030page1;
    }

    /**
     * <p>prj030page2 を取得します。
     * @return prj030page2
     */
    public int getPrj030page2() {
        return prj030page2__;
    }

    /**
     * <p>prj030page2 をセットします。
     * @param prj030page2 prj030page2
     */
    public void setPrj030page2(int prj030page2) {
        prj030page2__ = prj030page2;
    }

    /**
     * <p>prj030SepKey を取得します。
     * @return prj030SepKey
     */
    public String getPrj030SepKey() {
        return prj030SepKey__;
    }

    /**
     * <p>prj030SepKey をセットします。
     * @param prj030SepKey prj030SepKey
     */
    public void setPrj030SepKey(String prj030SepKey) {
        prj030SepKey__ = prj030SepKey;
    }

    /**
     * <p>prj030Init を取得します。
     * @return prj030Init
     */
    public boolean isPrj030Init() {
        return prj030Init__;
    }

    /**
     * <p>prj030Init をセットします。
     * @param prj030Init prj030Init
     */
    public void setPrj030Init(boolean prj030Init) {
        prj030Init__ = prj030Init;
    }

    /**
     * @return outMemberList
     */
    public List<Prj150DspMdl> getOutMemberList() {
        return outMemberList__;
    }

    /**
     * @param outMemberList 設定する outMemberList
     */
    public void setOutMemberList(List<Prj150DspMdl> outMemberList) {
        outMemberList__ = outMemberList;
    }

    /**
     * <p>prj030selectTodo を取得します。
     * @return prj030selectTodo
     */
    public String[] getPrj030selectTodo() {
        return prj030selectTodo__;
    }

    /**
     * <p>prj030selectTodo をセットします。
     * @param prj030selectTodo prj030selectTodo
     */
    public void setPrj030selectTodo(String[] prj030selectTodo) {
        prj030selectTodo__ = prj030selectTodo;
    }

    /**
     * <p>prj030selectEditStatus を取得します。
     * @return prj030selectEditStatus
     */
    public int getPrj030selectEditStatus() {
        return prj030selectEditStatus__;
    }

    /**
     * <p>prj030selectEditStatus をセットします。
     * @param prj030selectEditStatus prj030selectEditStatus
     */
    public void setPrj030selectEditStatus(int prj030selectEditStatus) {
        prj030selectEditStatus__ = prj030selectEditStatus;
    }

    /**
     * <p>selectingCategory を取得します。
     * @return selectingCategory
     */
    public String getSelectingCategory() {
        return selectingCategory__;
    }

    /**
     * <p>selectingCategory をセットします。
     * @param selectingCategory selectingCategory
     */
    public void setSelectingCategory(String selectingCategory) {
        selectingCategory__ = selectingCategory;
    }

    /**
     * <p>targetCategoryLabel を取得します。
     * @return targetCategoryLabel
     */
    public List<LabelValueBean> getTargetCategoryLabel() {
        return targetCategoryLabel__;
    }

    /**
     * <p>targetCategoryLabel をセットします。
     * @param targetCategoryLabel targetCategoryLabel
     */
    public void setTargetCategoryLabel(List<LabelValueBean> targetCategoryLabel) {
        targetCategoryLabel__ = targetCategoryLabel;
    }

    /**
     * <p>prj030selectTodoStr を取得します。
     * @return prj030selectTodoStr
     */
    public String getPrj030selectTodoStr() {
        return prj030selectTodoStr__;
    }

    /**
     * <p>prj030selectTodoStr をセットします。
     * @param prj030selectTodoStr prj030selectTodoStr
     */
    public void setPrj030selectTodoStr(String prj030selectTodoStr) {
        prj030selectTodoStr__ = prj030selectTodoStr;
    }

    /**
     * <p>prj030chDateHol を取得します。
     * @return prj030chDateHol
     */
    public int getPrj030chDateHol() {
        return prj030chDateHol__;
    }

    /**
     * <p>prj030chDateHol をセットします。
     * @param prj030chDateHol prj030chDateHol
     */
    public void setPrj030chDateHol(int prj030chDateHol) {
        prj030chDateHol__ = prj030chDateHol;
    }

    /**
     * <p>prj030chDateKbn を取得します。
     * @return prj030chDateKbn
     */
    public int getPrj030chDateKbn() {
        return prj030chDateKbn__;
    }

    /**
     * <p>prj030chDateKbn をセットします。
     * @param prj030chDateKbn prj030chDateKbn
     */
    public void setPrj030chDateKbn(int prj030chDateKbn) {
        prj030chDateKbn__ = prj030chDateKbn;
    }

    /**
     * <p>prj030mvMonth を取得します。
     * @return prj030mvMonth
     */
    public int getPrj030mvMonth() {
        return prj030mvMonth__;
    }

    /**
     * <p>prj030mvMonth をセットします。
     * @param prj030mvMonth prj030mvMonth
     */
    public void setPrj030mvMonth(int prj030mvMonth) {
        prj030mvMonth__ = prj030mvMonth;
    }

    /**
     * <p>prj030mvDay を取得します。
     * @return prj030mvDay
     */
    public int getPrj030mvDay() {
        return prj030mvDay__;
    }

    /**
     * <p>prj030mvDay をセットします。
     * @param prj030mvDay prj030mvDay
     */
    public void setPrj030mvDay(int prj030mvDay) {
        prj030mvDay__ = prj030mvDay;
    }
}