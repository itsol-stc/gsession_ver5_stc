package jp.groupsession.v2.prj.prj010;

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
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.PrjTreeForm;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] プロジェクト管理 ダッシュボード画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj010Form extends PrjTreeForm {

    /** 処理モード */
    private String prj010cmdMode__ = GSConstProject.MODE_TODO;

    /** プロジェクトSID */
    private int prj010PrjSid__;
    /** プロジェクトバイナリSID */
    private Long prj010PrjBinSid__ = new Long(0);

    //入力項目
    /** ページ1 */
    private int prj010page1__;
    /** ページ2 */
    private int prj010page2__;
    /** ソートキー */
    private int prj010sort__ = GSConstProject.SORT_TODO_WEIGHT;
    /** オーダーキー */
    private int prj010order__ = GSConst.ORDER_KEY_DESC;

    /** 選択されたTODO */
    private String[] prj010selectTodo__;
    /** 選択されたTODO(文字列) */
    private String prj010selectTodoStr__;
    /** 日付変更休日設定  0:反映させる 1:反映させない */
    private int prj010chDateHol__ = 0;
    /** 日付変更移動区分 0:先 1:前 */
    private int prj010chDateKbn__ = 0;
    /** 日付変更 移動月*/
    private int prj010mvMonth__ = 0;
    /** 日付変更 移動日*/
    private int prj010mvDay__ = 0;
    /** 状態変更 選択された状態 */
    private int prj010selectEditStatus__ = GSConstProject.STATUS_100;

    //表示項目
    /** ページラベル */
    private List<LabelValueBean> pageLabel__;
    /** プロジェクト一覧 */
    private List<ProjectItemModel> projectList__;
    /** プロジェクト一覧(全て) */
    private List<ProjectItemModel> allProjectList__;

    /** 状態コンボ(状態変更用) */
    private List<LabelValueBean> editStatusLabel__;

    //フラグ等
    /** 管理者権限 */
    private boolean admin__;
    /** プロジェクト登録権限 */
    private boolean prjAdd__;
    /** 初期表示 */
    private boolean prj010Init__ = true;

    //画面遷移時パラメータ
    //TODO詳細検索
    /** 遷移元画面ID(TODO詳細検索へ) */
    private String prj070scrId__;
    /** 検索フラグ(TODO詳細検索) */
    private int prj070searchFlg__;
    /** プロジェクトSID(TODO詳細検索) */
    private int prj070scPrjSid__ = GSConstCommon.NUM_INIT;
    /** (save)プロジェクトSID */
    private int prj070svScPrjSid__ = GSConstCommon.NUM_INIT;
    //プロジェクト詳細検索
    /** 検索フラグ(プロジェクト詳細検索) */
    private int prj040searchFlg__;
    //TODO登録・編集
    /** 遷移元画面ID(TODO登録・編集へ) */
    private String prj050scrId__;
    /** 処理モード(TODO登録・編集) */
    private String prj050cmdMode__;
    /** プロジェクトSID(TODO登録・編集) */
    private int prj050prjSid__;
    /** TODOSID(TODO登録・編集) */
    private int prj050todoSid__;
    //プロジェクト登録・編集
    /** 遷移元画面ID(プロジェクト登録・編集へ) */
    private String prj020scrId__;
    /** 処理モード(プロジェクト登録・編集) */
    private String prj020cmdMode__;
    /** プロジェクトSID(プロジェクト登録・編集へ) */
    private int prj020prjSid__;
    //プロジェクトメイン
    /** 遷移元画面ID(プロジェクトメインへ) */
    private String prj030scrId__;
    /** プロジェクトSID(プロジェクトメイン) */
    private int prj030prjSid__;
    //TODO参照
    /** 遷移元画面ID(TODO参照へ) */
    private String prj060scrId__;
    /** プロジェクトSID(TODO参照) */
    private int prj060prjSid__;
    /** TODOSID(TODO参照) */
    private int prj060todoSid__;

    /** プロジェクト[ダッシュボード] プロジェクトコンボ選択値 */
    private String selectingProject__ = null;
    /** プロジェクト[ダッシュボード] プロジェクトコンボラベル */
    private List<LabelValueBean> targetProjectLabel__;

    /** ＴＯＤＯ[ダッシュボード] 日付コンボ選択値 */
    private String selectingTodoDay__ = null;
    /** ＴＯＤＯ[ダッシュボード] 状態コンボ選択値 */
    private String selectingTodoSts__ = null;
    /** ＴＯＤＯ[ダッシュボード] プロジェクトコンボ選択値 */
    private String selectingTodoPrj__ = null;

    /** ＴＯＤＯ[ダッシュボード] 日付コンボラベル */
    private List<LabelValueBean> targetTodoDayLabel__;
    /** ＴＯＤＯ[ダッシュボード] 状態コンボラベル */
    private List<LabelValueBean> targetTodoStsLabel__;
    /** ＴＯＤＯ[ダッシュボード] プロジェクトコンボラベル */
    private List<LabelValueBean> targetTodoProjectLabel__;

    /** ＴＯＤＯ[ダッシュボード] 完了件数 */
    private int todoKanryoCnt__ = 0;
    /** ＴＯＤＯ[ダッシュボード] 進行中件数 */
    private int todoSinkotyuCnt__ = 0;
    /** ＴＯＤＯ[ダッシュボード] 未完了件数 */
    private int todoMikanryoCnt__ = 0;

    //テンプレ用
    /** テンプレートモード(継承先で共通使用) */
    private int prjTmpMode__ = -1;
    /** 選択テンプレートSID */
    private int prtSid__ = -1;
    //入力項目
    /** プロジェクトテンプレート名称 */
    private String prj140prtTmpName__;
    /** プロジェクトID */
    private String prj140prtId__;
    /** プロジェクト名称 */
    private String prj140prtName__;
    /** プロジェクト略称 */
    private String prj140prtNameS__;
    /** 予算 */
    private String prj140yosan__;
    /** 公開・非公開 */
    private int prj140koukai__;
    /** 開始年 */
    private String prj140startYear__;
    /** 開始月 */
    private String prj140startMonth__;
    /** 開始日 */
    private String prj140startDay__;
    /** 終了年 */
    private String prj140endYear__;
    /** 終了月 */
    private String prj140endMonth__;
    /** 終了日 */
    private String prj140endDay__;
    /** 開始年月日 */
    private String prj140startDate__;
    /** 終了年月日 */
    private String prj140endDate__;
    /** 状態 */
    private int prj140status__;
    /** 目標・目的 */
    private String prj140mokuhyou__;
    /** 内容 */
    private String prj140naiyou__;
    /** グループ(所属メンバー) */
    private int prj140group__;
    /** メンバー(所属メンバー) */
    private String[] prj140syozokuMember__;
    /** 未所属ユーザ(所属メンバー) */
    private String[] prj140user__;
    /** 所属メンバー */
    private String[] prj140hdnMember__;
    /** 管理者(プロジェクト管理者) */
    private String[] prj140adminMember__;
    /** メンバー(プロジェクト管理者) */
    private String[] prj140prjMember__;
    /** プロジェクト管理者 */
    private String[] prj140hdnAdmin__;
    /** 編集権限 */
    private int prj140kengen__;
    /** ショートメール通知先 */
    private int prj140smailKbn__;
    /** プロジェクト状態Model */
    private ProjectStatusTmpModel prjStatusTmpMdl__;

    /** グループ(所属メンバー)ラベル */
    private List<LabelValueBean> groupLabel__;
    /** メンバー(所属メンバー)ラベル */
    private List<UsrLabelValueBean> syozokuMemberLabel__;
    /** 未所属ユーザ(所属メンバー)ラベル */
    private List<UsrLabelValueBean> userLabel__;
    /** 状態ラベル */
    private List<LabelValueBean> statusLabel__;
    //テンプレ用

    /** 遷移元画面(自由使用) */
    private String movedDspId__;
    /** 遷移元 メイン個人設定メニュー:2 メイン管理者設定メニュー:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;
    /** TODO情報画面遷移後、コメントへカーソルを合わせる */
    private int prjmvComment__ = 0;

    /** ブラウザ判定 */
    private int prj010IeFlg__ = 0;

    /**
     * <p>prj010IeFlg を取得します。
     * @return prj010IeFlg
     */
    public int getPrj010IeFlg() {
        return prj010IeFlg__;
    }

    /**
     * <p>prj010IeFlg をセットします。
     * @param prj010IeFlg prj010IeFlg
     */
    public void setPrj010IeFlg(int prj010IeFlg) {
        prj010IeFlg__ = prj010IeFlg;
    }

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {
        cmn999Form.addHiddenParam("prj010cmdMode", prj010cmdMode__);
        cmn999Form.addHiddenParam("prj010page1", prj010page1__);
        cmn999Form.addHiddenParam("prj010page2", prj010page2__);
        cmn999Form.addHiddenParam("prj010sort", prj010sort__);
        cmn999Form.addHiddenParam("prj010order", prj010order__);
        cmn999Form.addHiddenParam("prj010Init", String.valueOf(prj010Init__));
        cmn999Form.addHiddenParam("prj010selectTodo", prj010selectTodo__);
        cmn999Form.addHiddenParam("prj010selectEditStatus", prj010selectEditStatus__);

        cmn999Form.addHiddenParam("selectingProject", selectingProject__);
        cmn999Form.addHiddenParam("selectingTodoDay", selectingTodoDay__);
        cmn999Form.addHiddenParam("selectingTodoSts", selectingTodoSts__);
        cmn999Form.addHiddenParam("selectingTodoPrj", selectingTodoPrj__);
        cmn999Form.addHiddenParam("selectDir", getSelectDir());
        cmn999Form.addHiddenParam("selectMoveDir", getSelectMoveDir());
    }

    /**
     * <br>[機  能] 変更ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buMdl セッションユーザ情報
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateEditStatus(Connection con,
            BaseUserModel buMdl, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //編集
        String textEdit = gsMsg.getMessage("cmn.edit");
        //状態
        String textStatus = gsMsg.getMessage("cmn.status");
        //状態
        if (prj010selectEditStatus__ != GSConstProject.STATUS_100
        && prj010selectEditStatus__ != GSConstProject.STATUS_0) {
            msg = new ActionMessage("error.select3.required.text", textStatus);
            StrutsUtil.addMessage(errors, msg,
                                "prj010selectEditStatus.error.select3.required.text");
        }

        //選択されたTODO
        if (prj010selectTodo__ == null || prj010selectTodo__.length < 1) {
            msg = new ActionMessage("error.select.required.text", GSConstProject.MSG_TODO);
            StrutsUtil.addMessage(errors, msg, "prj010selectTodo.error.select.required.text");
        }

        if (errors.isEmpty()) {

            List<Integer> prjSidList = new ArrayList<Integer>(prj010selectTodo__.length);
            for (String selectTodo : prj010selectTodo__) {
                prjSidList.add(Prj010Biz.formatSid(selectTodo)[0]);
            }

            validateCanEditTodo(errors, con, buMdl,
                                prjSidList, "prj010selectTodo",
                                textEdit, reqMdl);
        }

        return errors;
    }

    /**
     * <br>[機  能] 日付変更の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buMdl セッションユーザ情報
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateEditDate(Connection con,
            BaseUserModel buMdl, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //編集
        String textEdit = gsMsg.getMessage("cmn.edit");
//        //状態
//        String textStatus = gsMsg.getMessage(req, "cmn.status");
//        //状態
//        if (prj010selectEditStatus__ != GSConstProject.STATUS_100
//        && prj010selectEditStatus__ != GSConstProject.STATUS_0) {
//            msg = new ActionMessage("error.select3.required.text", textStatus);
//            StrutsUtil.addMessage(errors, msg,
//                                "prj010selectEditStatus.error.select3.required.text");
//        }
//
        //選択されたTODO
        if (prj010selectTodo__ == null || prj010selectTodo__.length < 1) {
            msg = new ActionMessage("error.select.required.text", GSConstProject.MSG_TODO);
            StrutsUtil.addMessage(errors, msg, "prj010selectTodo.error.select.required.text");
        }

        if (errors.isEmpty()) {

            List<Integer> prjSidList = new ArrayList<Integer>(prj010selectTodo__.length);
            for (String selectTodo : prj010selectTodo__) {
                prjSidList.add(Prj010Biz.formatSid(selectTodo)[0]);
            }

            validateCanEditTodo(errors, con, buMdl,
                                prjSidList, "prj010selectTodo",
                                textEdit, reqMdl);
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除(TODO情報)ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buMdl セッションユーザ情報]
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateDelTodo(Connection con, BaseUserModel buMdl, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //削除
        String textDelete = gsMsg.getMessage("cmn.delete");
        //選択されたTODO
        if (prj010selectTodo__ == null || prj010selectTodo__.length < 1) {
            msg = new ActionMessage("error.select.required.text", GSConstProject.MSG_TODO);
            StrutsUtil.addMessage(errors, msg, "prj010selectTodo.error.select.required.text");
        }

        if (errors.isEmpty()) {

            List<Integer> prjSidList = new ArrayList<Integer>(prj010selectTodo__.length);
            for (String selectTodo : prj010selectTodo__) {
                prjSidList.add(Prj010Biz.formatSid(selectTodo)[0]);
            }

            validateCanEditTodo(errors, con, buMdl,
                                prjSidList, "prj010selectTodo",
                                textDelete, reqMdl);
        }

        return errors;
    }

    /**
     * <br>[機  能] 指定されたTODOが全て編集可能かをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param con コネクション
     * @param buMdl セッションユーザ情報
     * @param prjSidList 選択されたTODOのプロジェクトSID
     * @param paramName パラメータ名称
     * @param operate 操作 (編集 or 削除)
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCanEditTodo(ActionErrors errors,
                                            Connection con, BaseUserModel buMdl,
                                            List<Integer> prjSidList, String paramName,
                                            String operate, RequestModel reqMdl)
                                            throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, reqMdl);
        for (int prjSid : prjSidList) {
            if (!prjBiz.getTodoEditKengen(prjSid, buMdl)) {
                ActionMessage msg = new ActionMessage("error.select.todo.cant.edit",
                                                    operate);
                StrutsUtil.addMessage(errors, msg,
                                    paramName + ".error.select.todo.cant.edit");
                break;
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 指定されたTODOが全て編集可能かをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public void validatePrm(HttpServletRequest req) {

        //チェックボックスの値を初期化
        if (req.getParameter("prj010selectTodo") != null) {
            String[] array = new String[0];
            prj010selectTodo__ = array;
        }
    }

    /**
     * <p>movedDspId を取得します。
     * @return movedDspId
     */
    public String getMovedDspId() {
        return movedDspId__;
    }
    /**
     * <p>movedDspId をセットします。
     * @param movedDspId movedDspId
     */
    public void setMovedDspId(String movedDspId) {
        movedDspId__ = movedDspId;
    }
    /**
     * <p>targetTodoProjectLabel を取得します。
     * @return targetTodoProjectLabel
     */
    public List<LabelValueBean> getTargetTodoProjectLabel() {
        return targetTodoProjectLabel__;
    }
    /**
     * <p>targetTodoProjectLabel をセットします。
     * @param targetTodoProjectLabel targetTodoProjectLabel
     */
    public void setTargetTodoProjectLabel(
            List<LabelValueBean> targetTodoProjectLabel) {
        targetTodoProjectLabel__ = targetTodoProjectLabel;
    }
    /**
     * <p>selectingProject を取得します。
     * @return selectingProject
     */
    public String getSelectingProject() {
        return selectingProject__;
    }
    /**
     * <p>selectingProject をセットします。
     * @param selectingProject selectingProject
     */
    public void setSelectingProject(String selectingProject) {
        selectingProject__ = selectingProject;
    }
    /**
     * <p>selectingTodoDay を取得します。
     * @return selectingTodoDay
     */
    public String getSelectingTodoDay() {
        return selectingTodoDay__;
    }
    /**
     * <p>selectingTodoDay をセットします。
     * @param selectingTodoDay selectingTodoDay
     */
    public void setSelectingTodoDay(String selectingTodoDay) {
        selectingTodoDay__ = selectingTodoDay;
    }
    /**
     * <p>selectingTodoPrj を取得します。
     * @return selectingTodoPrj
     */
    public String getSelectingTodoPrj() {
        return selectingTodoPrj__;
    }
    /**
     * <p>selectingTodoPrj をセットします。
     * @param selectingTodoPrj selectingTodoPrj
     */
    public void setSelectingTodoPrj(String selectingTodoPrj) {
        selectingTodoPrj__ = selectingTodoPrj;
    }
    /**
     * <p>selectingTodoSts を取得します。
     * @return selectingTodoSts
     */
    public String getSelectingTodoSts() {
        return selectingTodoSts__;
    }
    /**
     * <p>selectingTodoSts をセットします。
     * @param selectingTodoSts selectingTodoSts
     */
    public void setSelectingTodoSts(String selectingTodoSts) {
        selectingTodoSts__ = selectingTodoSts;
    }
    /**
     * <p>targetProjectLabel を取得します。
     * @return targetProjectLabel
     */
    public List<LabelValueBean> getTargetProjectLabel() {
        return targetProjectLabel__;
    }
    /**
     * <p>targetProjectLabel をセットします。
     * @param targetProjectLabel targetProjectLabel
     */
    public void setTargetProjectLabel(List<LabelValueBean> targetProjectLabel) {
        targetProjectLabel__ = targetProjectLabel;
    }
    /**
     * <p>targetTodoDayLabel を取得します。
     * @return targetTodoDayLabel
     */
    public List<LabelValueBean> getTargetTodoDayLabel() {
        return targetTodoDayLabel__;
    }
    /**
     * <p>targetTodoDayLabel をセットします。
     * @param targetTodoDayLabel targetTodoDayLabel
     */
    public void setTargetTodoDayLabel(List<LabelValueBean> targetTodoDayLabel) {
        targetTodoDayLabel__ = targetTodoDayLabel;
    }
    /**
     * <p>targetTodoStsLabel を取得します。
     * @return targetTodoStsLabel
     */
    public List<LabelValueBean> getTargetTodoStsLabel() {
        return targetTodoStsLabel__;
    }
    /**
     * <p>targetTodoStsLabel をセットします。
     * @param targetTodoStsLabel targetTodoStsLabel
     */
    public void setTargetTodoStsLabel(List<LabelValueBean> targetTodoStsLabel) {
        targetTodoStsLabel__ = targetTodoStsLabel;
    }
    /**
     * <p>prj010cmdMode を取得します。
     * @return prj010cmdMode
     */
    public String getPrj010cmdMode() {
        return prj010cmdMode__;
    }
    /**
     * <p>prj010cmdMode をセットします。
     * @param prj010cmdMode prj010cmdMode
     */
    public void setPrj010cmdMode(String prj010cmdMode) {
        prj010cmdMode__ = prj010cmdMode;
    }
    /**
     * <p>prj010page1 を取得します。
     * @return prj010page1
     */
    public int getPrj010page1() {
        return prj010page1__;
    }
    /**
     * <p>prj010page1 をセットします。
     * @param prj010page1 prj010page1
     */
    public void setPrj010page1(int prj010page1) {
        prj010page1__ = prj010page1;
    }
    /**
     * <p>prj010page2 を取得します。
     * @return prj010page2
     */
    public int getPrj010page2() {
        return prj010page2__;
    }
    /**
     * <p>prj010page2 をセットします。
     * @param prj010page2 prj010page2
     */
    public void setPrj010page2(int prj010page2) {
        prj010page2__ = prj010page2;
    }
    /**
     * <p>prj010sort を取得します。
     * @return prj010sort
     */
    public int getPrj010sort() {
        return prj010sort__;
    }
    /**
     * <p>prj010sort をセットします。
     * @param prj010sort prj010sort
     */
    public void setPrj010sort(int prj010sort) {
        prj010sort__ = prj010sort;
    }
    /**
     * <p>prj010order を取得します。
     * @return prj010order
     */
    public int getPrj010order() {
        return prj010order__;
    }
    /**
     * <p>prj010order をセットします。
     * @param prj010order prj010order
     */
    public void setPrj010order(int prj010order) {
        prj010order__ = prj010order;
    }
    /**
     * <p>prjAdd を取得します。
     * @return prjAdd
     */
    public boolean isPrjAdd() {
        return prjAdd__;
    }
    /**
     * <p>prjAdd をセットします。
     * @param prjAdd prjAdd
     */
    public void setPrjAdd(boolean prjAdd) {
        prjAdd__ = prjAdd;
    }
    /**
     * <p>pageLabel を取得します。
     * @return pageLabel
     */
    public List<LabelValueBean> getPageLabel() {
        return pageLabel__;
    }
    /**
     * <p>pageLabel をセットします。
     * @param pageLabel pageLabel
     */
    public void setPageLabel(List<LabelValueBean> pageLabel) {
        pageLabel__ = pageLabel;
    }
    /**
     * <p>projectList を取得します。
     * @return projectList
     */
    public List<ProjectItemModel> getProjectList() {
        return projectList__;
    }
    /**
     * <p>projectList をセットします。
     * @param projectList projectList
     */
    public void setProjectList(List<ProjectItemModel> projectList) {
        projectList__ = projectList;
    }
    /**
     * <p>allProjectList を取得します。
     * @return allProjectList
     */
    public List<ProjectItemModel> getAllProjectList() {
        return allProjectList__;
    }
    /**
     * <p>allProjectList をセットします。
     * @param allProjectList allProjectList
     */
    public void setAllProjectList(List<ProjectItemModel> allProjectList) {
        allProjectList__ = allProjectList;
    }
    /**
     * <p>admin を取得します。
     * @return admin
     */
    public boolean isAdmin() {
        return admin__;
    }
    /**
     * <p>admin をセットします。
     * @param admin admin
     */
    public void setAdmin(boolean admin) {
        admin__ = admin;
    }
    /**
     * <p>prj070scrId を取得します。
     * @return prj070scrId
     */
    public String getPrj070scrId() {
        return prj070scrId__;
    }
    /**
     * <p>prj070scrId をセットします。
     * @param prj070scrId prj070scrId
     */
    public void setPrj070scrId(String prj070scrId) {
        prj070scrId__ = prj070scrId;
    }
    /**
     * <p>prj070searchFlg を取得します。
     * @return prj070searchFlg
     */
    public int getPrj070searchFlg() {
        return prj070searchFlg__;
    }
    /**
     * <p>prj070searchFlg をセットします。
     * @param prj070searchFlg prj070searchFlg
     */
    public void setPrj070searchFlg(int prj070searchFlg) {
        prj070searchFlg__ = prj070searchFlg;
    }
    /**
     * <p>prj040searchFlg を取得します。
     * @return prj040searchFlg
     */
    public int getPrj040searchFlg() {
        return prj040searchFlg__;
    }
    /**
     * <p>prj040searchFlg をセットします。
     * @param prj040searchFlg prj040searchFlg
     */
    public void setPrj040searchFlg(int prj040searchFlg) {
        prj040searchFlg__ = prj040searchFlg;
    }
    /**
     * <p>prj050scrId を取得します。
     * @return prj050scrId
     */
    public String getPrj050scrId() {
        return prj050scrId__;
    }
    /**
     * <p>prj050scrId をセットします。
     * @param prj050scrId prj050scrId
     */
    public void setPrj050scrId(String prj050scrId) {
        prj050scrId__ = prj050scrId;
    }
    /**
     * <p>prj050cmdMode を取得します。
     * @return prj050cmdMode
     */
    public String getPrj050cmdMode() {
        return prj050cmdMode__;
    }
    /**
     * <p>prj050cmdMode をセットします。
     * @param prj050cmdMode prj050cmdMode
     */
    public void setPrj050cmdMode(String prj050cmdMode) {
        prj050cmdMode__ = prj050cmdMode;
    }
    /**
     * <p>prj020scrId を取得します。
     * @return prj020scrId
     */
    public String getPrj020scrId() {
        return prj020scrId__;
    }
    /**
     * <p>prj020scrId をセットします。
     * @param prj020scrId prj020scrId
     */
    public void setPrj020scrId(String prj020scrId) {
        prj020scrId__ = prj020scrId;
    }
    /**
     * <p>prj020cmdMode を取得します。
     * @return prj020cmdMode
     */
    public String getPrj020cmdMode() {
        return prj020cmdMode__;
    }
    /**
     * <p>prj020cmdMode をセットします。
     * @param prj020cmdMode prj020cmdMode
     */
    public void setPrj020cmdMode(String prj020cmdMode) {
        prj020cmdMode__ = prj020cmdMode;
    }
    /**
     * <p>prj030scrId を取得します。
     * @return prj030scrId
     */
    public String getPrj030scrId() {
        return prj030scrId__;
    }
    /**
     * <p>prj030scrId をセットします。
     * @param prj030scrId prj030scrId
     */
    public void setPrj030scrId(String prj030scrId) {
        prj030scrId__ = prj030scrId;
    }
    /**
     * <p>prj030prjSid を取得します。
     * @return prj030prjSid
     */
    public int getPrj030prjSid() {
        return prj030prjSid__;
    }
    /**
     * <p>prj030prjSid をセットします。
     * @param prj030prjSid prj030prjSid
     */
    public void setPrj030prjSid(int prj030prjSid) {
        prj030prjSid__ = prj030prjSid;
    }
    /**
     * <p>prj060scrId を取得します。
     * @return prj060scrId
     */
    public String getPrj060scrId() {
        return prj060scrId__;
    }
    /**
     * <p>prj060scrId をセットします。
     * @param prj060scrId prj060scrId
     */
    public void setPrj060scrId(String prj060scrId) {
        prj060scrId__ = prj060scrId;
    }
    /**
     * <p>prj060prjSid を取得します。
     * @return prj060prjSid
     */
    public int getPrj060prjSid() {
        return prj060prjSid__;
    }
    /**
     * <p>prj060prjSid をセットします。
     * @param prj060prjSid prj060prjSid
     */
    public void setPrj060prjSid(int prj060prjSid) {
        prj060prjSid__ = prj060prjSid;
    }
    /**
     * <p>prj060todoSid を取得します。
     * @return prj060todoSid
     */
    public int getPrj060todoSid() {
        return prj060todoSid__;
    }
    /**
     * <p>prj060todoSid をセットします。
     * @param prj060todoSid prj060todoSid
     */
    public void setPrj060todoSid(int prj060todoSid) {
        prj060todoSid__ = prj060todoSid;
    }
    /**
     * <p>prj020prjSid を取得します。
     * @return prj020prjSid
     */
    public int getPrj020prjSid() {
        return prj020prjSid__;
    }
    /**
     * <p>prj020prjSid をセットします。
     * @param prj020prjSid prj020prjSid
     */
    public void setPrj020prjSid(int prj020prjSid) {
        prj020prjSid__ = prj020prjSid;
    }
    /**
     * <p>prj070scPrjSid を取得します。
     * @return prj070scPrjSid
     */
    public int getPrj070scPrjSid() {
        return prj070scPrjSid__;
    }
    /**
     * <p>prj070scPrjSid をセットします。
     * @param prj070scPrjSid prj070scPrjSid
     */
    public void setPrj070scPrjSid(int prj070scPrjSid) {
        prj070scPrjSid__ = prj070scPrjSid;
    }
    /**
     * <p>prj050prjSid を取得します。
     * @return prj050prjSid
     */
    public int getPrj050prjSid() {
        return prj050prjSid__;
    }
    /**
     * <p>prj050prjSid をセットします。
     * @param prj050prjSid prj050prjSid
     */
    public void setPrj050prjSid(int prj050prjSid) {
        prj050prjSid__ = prj050prjSid;
    }

    /**
     * <p>prj070svScPrjSid を取得します。
     * @return prj070svScPrjSid
     */
    public int getPrj070svScPrjSid() {
        return prj070svScPrjSid__;
    }

    /**
     * <p>prj070svScPrjSid をセットします。
     * @param prj070svScPrjSid prj070svScPrjSid
     */
    public void setPrj070svScPrjSid(int prj070svScPrjSid) {
        prj070svScPrjSid__ = prj070svScPrjSid;
    }
    /**
     * <p>prj050todoSid を取得します。
     * @return prj050todoSid
     */
    public int getPrj050todoSid() {
        return prj050todoSid__;
    }
    /**
     * <p>prj050todoSid をセットします。
     * @param prj050todoSid prj050todoSid
     */
    public void setPrj050todoSid(int prj050todoSid) {
        prj050todoSid__ = prj050todoSid;
    }
    /**
     * <p>todoKanryoCnt を取得します。
     * @return todoKanryoCnt
     */
    public int getTodoKanryoCnt() {
        return todoKanryoCnt__;
    }
    /**
     * <p>todoKanryoCnt をセットします。
     * @param todoKanryoCnt todoKanryoCnt
     */
    public void setTodoKanryoCnt(int todoKanryoCnt) {
        todoKanryoCnt__ = todoKanryoCnt;
    }
    /**
     * <p>todoMikanryoCnt を取得します。
     * @return todoMikanryoCnt
     */
    public int getTodoMikanryoCnt() {
        return todoMikanryoCnt__;
    }
    /**
     * <p>todoMikanryoCnt をセットします。
     * @param todoMikanryoCnt todoMikanryoCnt
     */
    public void setTodoMikanryoCnt(int todoMikanryoCnt) {
        todoMikanryoCnt__ = todoMikanryoCnt;
    }
    /**
     * <p>todoSinkotyuCnt を取得します。
     * @return todoSinkotyuCnt
     */
    public int getTodoSinkotyuCnt() {
        return todoSinkotyuCnt__;
    }
    /**
     * <p>todoSinkotyuCnt をセットします。
     * @param todoSinkotyuCnt todoSinkotyuCnt
     */
    public void setTodoSinkotyuCnt(int todoSinkotyuCnt) {
        todoSinkotyuCnt__ = todoSinkotyuCnt;
    }
    /**
     * <p>prjTmpMode を取得します。
     * @return prjTmpMode
     */
    public int getPrjTmpMode() {
        return prjTmpMode__;
    }
    /**
     * <p>prjTmpMode をセットします。
     * @param prjTmpMode prjTmpMode
     */
    public void setPrjTmpMode(int prjTmpMode) {
        prjTmpMode__ = prjTmpMode;
    }
    /**
     * <p>prtSid を取得します。
     * @return prtSid
     */
    public int getPrtSid() {
        return prtSid__;
    }
    /**
     * <p>prtSid をセットします。
     * @param prtSid prtSid
     */
    public void setPrtSid(int prtSid) {
        prtSid__ = prtSid;
    }
    /**
     * <p>prjStatusTmpMdl を取得します。
     * @return prjStatusTmpMdl
     */
    public ProjectStatusTmpModel getPrjStatusTmpMdl() {
        return prjStatusTmpMdl__;
    }
    /**
     * <p>prjStatusTmpMdl をセットします。
     * @param prjStatusTmpMdl prjStatusTmpMdl
     */
    public void setPrjStatusTmpMdl(ProjectStatusTmpModel prjStatusTmpMdl) {
        prjStatusTmpMdl__ = prjStatusTmpMdl;
    }
    /**
     * <p>groupLabel を取得します。
     * @return groupLabel
     */
    public List<LabelValueBean> getGroupLabel() {
        return groupLabel__;
    }
    /**
     * <p>groupLabel をセットします。
     * @param groupLabel groupLabel
     */
    public void setGroupLabel(List<LabelValueBean> groupLabel) {
        groupLabel__ = groupLabel;
    }
    /**
     * <p>prj140adminMember を取得します。
     * @return prj140adminMember
     */
    public String[] getPrj140adminMember() {
        return prj140adminMember__;
    }
    /**
     * <p>prj140adminMember をセットします。
     * @param prj140adminMember prj140adminMember
     */
    public void setPrj140adminMember(String[] prj140adminMember) {
        prj140adminMember__ = prj140adminMember;
    }
    /**
     * <p>prj140endDay を取得します。
     * @return prj140endDay
     */
    public String getPrj140endDay() {
        return prj140endDay__;
    }
    /**
     * <p>prj140endDay をセットします。
     * @param prj140endDay prj140endDay
     */
    public void setPrj140endDay(String prj140endDay) {
        prj140endDay__ = prj140endDay;
    }
    /**
     * <p>prj140endMonth を取得します。
     * @return prj140endMonth
     */
    public String getPrj140endMonth() {
        return prj140endMonth__;
    }
    /**
     * <p>prj140endMonth をセットします。
     * @param prj140endMonth prj140endMonth
     */
    public void setPrj140endMonth(String prj140endMonth) {
        prj140endMonth__ = prj140endMonth;
    }
    /**
     * <p>prj140endYear を取得します。
     * @return prj140endYear
     */
    public String getPrj140endYear() {
        return prj140endYear__;
    }
    /**
     * <p>prj140endYear をセットします。
     * @param prj140endYear prj140endYear
     */
    public void setPrj140endYear(String prj140endYear) {
        prj140endYear__ = prj140endYear;
    }
    /**
     * <p>prj140group を取得します。
     * @return prj140group
     */
    public int getPrj140group() {
        return prj140group__;
    }
    /**
     * <p>prj140group をセットします。
     * @param prj140group prj140group
     */
    public void setPrj140group(int prj140group) {
        prj140group__ = prj140group;
    }
    /**
     * <p>prj140hdnAdmin を取得します。
     * @return prj140hdnAdmin
     */
    public String[] getPrj140hdnAdmin() {
        return prj140hdnAdmin__;
    }
    /**
     * <p>prj140hdnAdmin をセットします。
     * @param prj140hdnAdmin prj140hdnAdmin
     */
    public void setPrj140hdnAdmin(String[] prj140hdnAdmin) {
        prj140hdnAdmin__ = prj140hdnAdmin;
    }
    /**
     * <p>prj140hdnMember を取得します。
     * @return prj140hdnMember
     */
    public String[] getPrj140hdnMember() {
        return prj140hdnMember__;
    }
    /**
     * <p>prj140hdnMember をセットします。
     * @param prj140hdnMember prj140hdnMember
     */
    public void setPrj140hdnMember(String[] prj140hdnMember) {
        prj140hdnMember__ = prj140hdnMember;
    }
    /**
     * <p>prj140kengen を取得します。
     * @return prj140kengen
     */
    public int getPrj140kengen() {
        return prj140kengen__;
    }
    /**
     * <p>prj140kengen をセットします。
     * @param prj140kengen prj140kengen
     */
    public void setPrj140kengen(int prj140kengen) {
        prj140kengen__ = prj140kengen;
    }
    /**
     * <p>prj140koukai を取得します。
     * @return prj140koukai
     */
    public int getPrj140koukai() {
        return prj140koukai__;
    }
    /**
     * <p>prj140koukai をセットします。
     * @param prj140koukai prj140koukai
     */
    public void setPrj140koukai(int prj140koukai) {
        prj140koukai__ = prj140koukai;
    }
    /**
     * <p>prj140mokuhyou を取得します。
     * @return prj140mokuhyou
     */
    public String getPrj140mokuhyou() {
        return prj140mokuhyou__;
    }
    /**
     * <p>prj140mokuhyou をセットします。
     * @param prj140mokuhyou prj140mokuhyou
     */
    public void setPrj140mokuhyou(String prj140mokuhyou) {
        prj140mokuhyou__ = prj140mokuhyou;
    }
    /**
     * <p>prj140naiyou を取得します。
     * @return prj140naiyou
     */
    public String getPrj140naiyou() {
        return prj140naiyou__;
    }
    /**
     * <p>prj140naiyou をセットします。
     * @param prj140naiyou prj140naiyou
     */
    public void setPrj140naiyou(String prj140naiyou) {
        prj140naiyou__ = prj140naiyou;
    }
    /**
     * <p>prj140prjMember を取得します。
     * @return prj140prjMember
     */
    public String[] getPrj140prjMember() {
        return prj140prjMember__;
    }
    /**
     * <p>prj140prjMember をセットします。
     * @param prj140prjMember prj140prjMember
     */
    public void setPrj140prjMember(String[] prj140prjMember) {
        prj140prjMember__ = prj140prjMember;
    }
    /**
     * <p>prj140prtId を取得します。
     * @return prj140prtId
     */
    public String getPrj140prtId() {
        return prj140prtId__;
    }
    /**
     * <p>prj140prtId をセットします。
     * @param prj140prtId prj140prtId
     */
    public void setPrj140prtId(String prj140prtId) {
        prj140prtId__ = prj140prtId;
    }
    /**
     * <p>prj140prtName を取得します。
     * @return prj140prtName
     */
    public String getPrj140prtName() {
        return prj140prtName__;
    }
    /**
     * <p>prj140prtName をセットします。
     * @param prj140prtName prj140prtName
     */
    public void setPrj140prtName(String prj140prtName) {
        prj140prtName__ = prj140prtName;
    }
    /**
     * <p>prj140prtNameS を取得します。
     * @return prj140prtNameS
     */
    public String getPrj140prtNameS() {
        return prj140prtNameS__;
    }
    /**
     * <p>prj140prtNameS をセットします。
     * @param prj140prtNameS prj140prtNameS
     */
    public void setPrj140prtNameS(String prj140prtNameS) {
        prj140prtNameS__ = prj140prtNameS;
    }
    /**
     * <p>prj140prtTmpName を取得します。
     * @return prj140prtTmpName
     */
    public String getPrj140prtTmpName() {
        return prj140prtTmpName__;
    }
    /**
     * <p>prj140prtTmpName をセットします。
     * @param prj140prtTmpName prj140prtTmpName
     */
    public void setPrj140prtTmpName(String prj140prtTmpName) {
        prj140prtTmpName__ = prj140prtTmpName;
    }
    /**
     * <p>prj140startDay を取得します。
     * @return prj140startDay
     */
    public String getPrj140startDay() {
        return prj140startDay__;
    }
    /**
     * <p>prj140startDay をセットします。
     * @param prj140startDay prj140startDay
     */
    public void setPrj140startDay(String prj140startDay) {
        prj140startDay__ = prj140startDay;
    }
    /**
     * <p>prj140startMonth を取得します。
     * @return prj140startMonth
     */
    public String getPrj140startMonth() {
        return prj140startMonth__;
    }
    /**
     * <p>prj140startMonth をセットします。
     * @param prj140startMonth prj140startMonth
     */
    public void setPrj140startMonth(String prj140startMonth) {
        prj140startMonth__ = prj140startMonth;
    }
    /**
     * <p>prj140startYear を取得します。
     * @return prj140startYear
     */
    public String getPrj140startYear() {
        return prj140startYear__;
    }
    /**
     * <p>prj140startYear をセットします。
     * @param prj140startYear prj140startYear
     */
    public void setPrj140startYear(String prj140startYear) {
        prj140startYear__ = prj140startYear;
    }
    /**
     * <p>prj140status を取得します。
     * @return prj140status
     */
    public int getPrj140status() {
        return prj140status__;
    }
    /**
     * <p>prj140status をセットします。
     * @param prj140status prj140status
     */
    public void setPrj140status(int prj140status) {
        prj140status__ = prj140status;
    }
    /**
     * <p>prj140syozokuMember を取得します。
     * @return prj140syozokuMember
     */
    public String[] getPrj140syozokuMember() {
        return prj140syozokuMember__;
    }
    /**
     * <p>prj140syozokuMember をセットします。
     * @param prj140syozokuMember prj140syozokuMember
     */
    public void setPrj140syozokuMember(String[] prj140syozokuMember) {
        prj140syozokuMember__ = prj140syozokuMember;
    }
    /**
     * <p>prj140user を取得します。
     * @return prj140user
     */
    public String[] getPrj140user() {
        return prj140user__;
    }
    /**
     * <p>prj140user をセットします。
     * @param prj140user prj140user
     */
    public void setPrj140user(String[] prj140user) {
        prj140user__ = prj140user;
    }
    /**
     * <p>prj140yosan を取得します。
     * @return prj140yosan
     */
    public String getPrj140yosan() {
        return prj140yosan__;
    }
    /**
     * <p>prj140yosan をセットします。
     * @param prj140yosan prj140yosan
     */
    public void setPrj140yosan(String prj140yosan) {
        prj140yosan__ = prj140yosan;
    }
    /**
     * <p>statusLabel を取得します。
     * @return statusLabel
     */
    public List<LabelValueBean> getStatusLabel() {
        return statusLabel__;
    }
    /**
     * <p>statusLabel をセットします。
     * @param statusLabel statusLabel
     */
    public void setStatusLabel(List<LabelValueBean> statusLabel) {
        statusLabel__ = statusLabel;
    }
    /**
     * <p>syozokuMemberLabel を取得します。
     * @return syozokuMemberLabel
     */
    public List<UsrLabelValueBean> getSyozokuMemberLabel() {
        return syozokuMemberLabel__;
    }
    /**
     * <p>syozokuMemberLabel をセットします。
     * @param syozokuMemberLabel syozokuMemberLabel
     */
    public void setSyozokuMemberLabel(List<UsrLabelValueBean> syozokuMemberLabel) {
        syozokuMemberLabel__ = syozokuMemberLabel;
    }
    /**
     * <p>userLabel を取得します。
     * @return userLabel
     */
    public List<UsrLabelValueBean> getUserLabel() {
        return userLabel__;
    }
    /**
     * <p>userLabel をセットします。
     * @param userLabel userLabel
     */
    public void setUserLabel(List<UsrLabelValueBean> userLabel) {
        userLabel__ = userLabel;
    }
    /**
     * <p>prj140smailKbn を取得します。
     * @return prj140smailKbn
     */
    public int getPrj140smailKbn() {
        return prj140smailKbn__;
    }
    /**
     * <p>prj140smailKbn をセットします。
     * @param prj140smailKbn prj140smailKbn
     */
    public void setPrj140smailKbn(int prj140smailKbn) {
        prj140smailKbn__ = prj140smailKbn;
    }

    /**
     * <p>prj010Init を取得します。
     * @return prj010Init
     */
    public boolean isPrj010Init() {
        return prj010Init__;
    }

    /**
     * <p>prj010Init をセットします。
     * @param prj010Init prj010Init
     */
    public void setPrj010Init(boolean prj010Init) {
        prj010Init__ = prj010Init;
    }

    /**
     * <p>prj010selectEditStatus を取得します。
     * @return prj010selectEditStatus
     */
    public int getPrj010selectEditStatus() {
        return prj010selectEditStatus__;
    }

    /**
     * <p>prjmvComment を取得します。
     * @return prjmvComment
     */
    public int getPrjmvComment() {
        return prjmvComment__;
    }

    /**
     * <p>prjmvComment をセットします。
     * @param prjmvComment prjmvComment
     */
    public void setPrjmvComment(int prjmvComment) {
        prjmvComment__ = prjmvComment;
    }

    /**
     * <p>prj010selectEditStatus をセットします。
     * @param prj010selectEditStatus prj010selectEditStatus
     */
    public void setPrj010selectEditStatus(int prj010selectEditStatus) {
        prj010selectEditStatus__ = prj010selectEditStatus;
    }

    /**
     * <p>prj010selectTodo を取得します。
     * @return prj010selectTodo
     */
    public String[] getPrj010selectTodo() {
        return prj010selectTodo__;
    }

    /**
     * <p>prj010selectTodo をセットします。
     * @param prj010selectTodo prj010selectTodo
     */
    public void setPrj010selectTodo(String[] prj010selectTodo) {
        prj010selectTodo__ = prj010selectTodo;
    }

    /**
     * <p>editStatusLabel を取得します。
     * @return editStatusLabel
     */
    public List<LabelValueBean> getEditStatusLabel() {
        return editStatusLabel__;
    }

    /**
     * <p>editStatusLabel をセットします。
     * @param editStatusLabel editStatusLabel
     */
    public void setEditStatusLabel(List<LabelValueBean> editStatusLabel) {
        editStatusLabel__ = editStatusLabel;
    }

    /**
     * <p>prj010PrjBinSid を取得します。
     * @return prj010PrjBinSid
     */
    public Long getPrj010PrjBinSid() {
        return prj010PrjBinSid__;
    }

    /**
     * <p>prj010PrjBinSid をセットします。
     * @param prj010PrjBinSid prj010PrjBinSid
     */
    public void setPrj010PrjBinSid(Long prj010PrjBinSid) {
        prj010PrjBinSid__ = prj010PrjBinSid;
    }

    /**
     * <p>prj010PrjSid を取得します。
     * @return prj010PrjSid
     */
    public int getPrj010PrjSid() {
        return prj010PrjSid__;
    }

    /**
     * <p>prj010PrjSid をセットします。
     * @param prj010PrjSid prj010PrjSid
     */
    public void setPrj010PrjSid(int prj010PrjSid) {
        prj010PrjSid__ = prj010PrjSid;
    }

    /**
     * <p>prj010mvMonth を取得します。
     * @return prj010mvMonth
     */
    public int getPrj010mvMonth() {
        return prj010mvMonth__;
    }

    /**
     * <p>prj010mvMonth をセットします。
     * @param prj010mvMonth prj010mvMonth
     */
    public void setPrj010mvMonth(int prj010mvMonth) {
        prj010mvMonth__ = prj010mvMonth;
    }

    /**
     * <p>prj010mvDay を取得します。
     * @return prj010mvDay
     */
    public int getPrj010mvDay() {
        return prj010mvDay__;
    }

    /**
     * <p>prj010mvDay をセットします。
     * @param prj010mvDay prj010mvDay
     */
    public void setPrj010mvDay(int prj010mvDay) {
        prj010mvDay__ = prj010mvDay;
    }

    /**
     * <p>prj010chDateHol を取得します。
     * @return prj010chDateHol
     */
    public int getPrj010chDateHol() {
        return prj010chDateHol__;
    }

    /**
     * <p>prj010chDateHol をセットします。
     * @param prj010chDateHol prj010chDateHol
     */
    public void setPrj010chDateHol(int prj010chDateHol) {
        prj010chDateHol__ = prj010chDateHol;
    }

    /**
     * <p>prj010chDateKbn を取得します。
     * @return prj010chDateKbn
     */
    public int getPrj010chDateKbn() {
        return prj010chDateKbn__;
    }

    /**
     * <p>prj010chDateKbn をセットします。
     * @param prj010chDateKbn prj010chDateKbn
     */
    public void setPrj010chDateKbn(int prj010chDateKbn) {
        prj010chDateKbn__ = prj010chDateKbn;
    }

    /**
     * <p>prj010selectTodoStr を取得します。
     * @return prj010selectTodoStr
     */
    public String getPrj010selectTodoStr() {
        return prj010selectTodoStr__;
    }

    /**
     * <p>prj010selectTodoStr をセットします。
     * @param prj010selectTodoStr prj010selectTodoStr
     */
    public void setPrj010selectTodoStr(String prj010selectTodoStr) {
        prj010selectTodoStr__ = prj010selectTodoStr;
    }


    /**
     * <p>prj140startDate を取得します。
     * @return prj140startDate
     * @see jp.groupsession.v2.prj.prj140.Prj140Form#prj140startDate__
     */
    public String getPrj140startDate() {
        return prj140startDate__;
    }

    /**
     * <p>prj140startDate をセットします。
     * @param prj140startDate prj140startDate
     * @see jp.groupsession.v2.prj.prj140.Prj140Form#prj140startDate__
     */
    public void setPrj140startDate(String prj140startDate) {
        prj140startDate__ = prj140startDate;
    }

    /**
     * <p>prj140endDate を取得します。
     * @return prj140endDate
     * @see jp.groupsession.v2.prj.prj140.Prj140Form#prj140endDate__
     */
    public String getPrj140endDate() {
        return prj140endDate__;
    }

    /**
     * <p>prj140endDate をセットします。
     * @param prj140endDate prj140endDate
     * @see jp.groupsession.v2.prj.prj140.Prj140Form#prj140endDate__
     */
    public void setPrj140endDate(String prj140endDate) {
        prj140endDate__ = prj140endDate;
    }
}