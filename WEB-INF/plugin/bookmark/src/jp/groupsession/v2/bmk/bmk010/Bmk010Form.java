package jp.groupsession.v2.bmk.bmk010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.GSValidateBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010BodyModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010InfoModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010LabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] ブックマーク画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010Form extends ActionForm {

    /** 処理モード */
    private int procMode__;
    /** URLSID */
    private int editBmuSid__;
    /** ブックマークSID */
    private int editBmkSid__;
    /** 遷移元 */
    private String returnPage__;

    /** ブックマーク区分 */
    private int bmk010mode__ = GSConstBookmark.BMK_KBN_KOJIN;
    /** 削除ブックマークSID */
    private String[] bmk010delInfSid__;
    /** 削除チェック (現在ページ以外でチェックされている値) */
    private ArrayList < String > bmk010SelectedDelSid__;
    /** 並び順 */
    private int bmk010orderKey__ = GSConstBookmark.ORDERKEY_DESC;
    /** ソートキー */
    private int bmk010sortKey__ = GSConstBookmark.SORTKEY_ADATE;
    /** グループSID */
    private int bmk010groupSid__ = Bmk010Biz.INIT_VALUE;
    /** ユーザSID */
    private int bmk010userSid__ = Bmk010Biz.INIT_VALUE;
    /** ラベル選択 */
    private int bmk010searchLabel__ = Bmk010Biz.INIT_VALUE;
    /** ページ */
    private int bmk010page__ = 0;
    /** ページ上段 */
    private int bmk010pageTop__ = 0;
    /** ページ下段 */
    private int bmk010pageBottom__ = 0;

    /** 選択ラベル名 */
    private String bmk010searchLabelName__ = "";

    /** グループコンボ */
    private List<LabelValueBean> bmk010groupCmbList__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> bmk010userCmbList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> bmk010pageCmbList__ = null;

    /** ブックマーク一覧 */
    private List<Bmk010BodyModel> bmk010BookmarkList__;
    /** ラベル一覧 */
    private List<Bmk010LabelModel> bmk010LabelList__;
    /** 新着ブックマーク一覧 */
    private List<Bmk010InfoModel> bmk010NewList__;
    /** 登録数ランキング一覧 */
    private List<Bmk010InfoModel> bmk010RankingList__;

    /** 管理者設定ボタン表示フラグ */
    private int bmk010viewAdminBtn__ = GSConstBookmark.POW_NO;
    /** グループ編集権限設定ボタン表示フラグ */
    private int bmk010viewGroupBtn__ = GSConstBookmark.POW_NO;
    /** 編集権限有無 */
    private int bmk010editPow__ = GSConstBookmark.POW_NO;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int bmk010searchUse__ = GSConst.PLUGIN_USE;

    /** 表示ページ(新着ブックマーク) */
    private int bmk150PageNum__ = 1;
    /** 評価・コメントから新着ブックマーク遷移フラグ */
    private boolean bmk070ToBmk150DspFlg__ = false;

    /**
     * <p>bmk010NewList を取得します。
     * @return bmk010NewList
     */
    public List<Bmk010InfoModel> getBmk010NewList() {
        return bmk010NewList__;
    }

    /**
     * <p>bmk010NewList をセットします。
     * @param bmk010NewList bmk010NewList
     */
    public void setBmk010NewList(List<Bmk010InfoModel> bmk010NewList) {
        bmk010NewList__ = bmk010NewList;
    }

    /**
     * <p>bmk010RankingList を取得します。
     * @return bmk010RankingList
     */
    public List<Bmk010InfoModel> getBmk010RankingList() {
        return bmk010RankingList__;
    }

    /**
     * <p>bmk010RankingList をセットします。
     * @param bmk010RankingList bmk010RankingList
     */
    public void setBmk010RankingList(List<Bmk010InfoModel> bmk010RankingList) {
        bmk010RankingList__ = bmk010RankingList;
    }

    /**
     * <p>editBmuSid を取得します。
     * @return editBmuSid
     */
    public int getEditBmuSid() {
        return editBmuSid__;
    }

    /**
     * <p>editBmuSid をセットします。
     * @param editBmuSid editBmuSid
     */
    public void setEditBmuSid(int editBmuSid) {
        editBmuSid__ = editBmuSid;
    }

    /**
     * <p>bmk010groupCmbList を取得します。
     * @return bmk010groupCmbList
     */
    public List<LabelValueBean> getBmk010groupCmbList() {
        return bmk010groupCmbList__;
    }

    /**
     * <p>bmk010groupCmbList をセットします。
     * @param bmk010groupCmbList bmk010groupCmbList
     */
    public void setBmk010groupCmbList(List<LabelValueBean> bmk010groupCmbList) {
        bmk010groupCmbList__ = bmk010groupCmbList;
    }

    /**
     * <p>bmk010groupSid を取得します。
     * @return bmk010groupSid
     */
    public int getBmk010groupSid() {
        return bmk010groupSid__;
    }

    /**
     * <p>bmk010groupSid をセットします。
     * @param bmk010groupSid bmk010groupSid
     */
    public void setBmk010groupSid(int bmk010groupSid) {
        bmk010groupSid__ = bmk010groupSid;
    }

    /**
     * <p>bmk010LabelList を取得します。
     * @return bmk010LabelList
     */
    public List<Bmk010LabelModel> getBmk010LabelList() {
        return bmk010LabelList__;
    }

    /**
     * <p>bmk010LabelList をセットします。
     * @param bmk010LabelList bmk010LabelList
     */
    public void setBmk010LabelList(List<Bmk010LabelModel> bmk010LabelList) {
        bmk010LabelList__ = bmk010LabelList;
    }

    /**
     * <p>bmk010mode を取得します。
     * @return bmk010mode
     */
    public int getBmk010mode() {
        return bmk010mode__;
    }

    /**
     * <p>bmk010mode をセットします。
     * @param bmk010mode bmk010mode
     */
    public void setBmk010mode(int bmk010mode) {
        bmk010mode__ = bmk010mode;
    }

    /**
     * <p>bmk010orderKey を取得します。
     * @return bmk010orderKey
     */
    public int getBmk010orderKey() {
        return bmk010orderKey__;
    }

    /**
     * <p>bmk010orderKey をセットします。
     * @param bmk010orderKey bmk010orderKey
     */
    public void setBmk010orderKey(int bmk010orderKey) {
        bmk010orderKey__ = bmk010orderKey;
    }

    /**
     * <p>bmk010page を取得します。
     * @return bmk010page
     */
    public int getBmk010page() {
        return bmk010page__;
    }

    /**
     * <p>bmk010page をセットします。
     * @param bmk010page bmk010page
     */
    public void setBmk010page(int bmk010page) {
        bmk010page__ = bmk010page;
    }

    /**
     * <p>bmk010pageBottom を取得します。
     * @return bmk010pageBottom
     */
    public int getBmk010pageBottom() {
        return bmk010pageBottom__;
    }

    /**
     * <p>bmk010pageBottom をセットします。
     * @param bmk010pageBottom bmk010pageBottom
     */
    public void setBmk010pageBottom(int bmk010pageBottom) {
        bmk010pageBottom__ = bmk010pageBottom;
    }

    /**
     * <p>bmk010pageCmbList を取得します。
     * @return bmk010pageCmbList
     */
    public List<LabelValueBean> getBmk010pageCmbList() {
        return bmk010pageCmbList__;
    }

    /**
     * <p>bmk010pageCmbList をセットします。
     * @param bmk010pageCmbList bmk010pageCmbList
     */
    public void setBmk010pageCmbList(List<LabelValueBean> bmk010pageCmbList) {
        bmk010pageCmbList__ = bmk010pageCmbList;
    }

    /**
     * <p>bmk010pageTop を取得します。
     * @return bmk010pageTop
     */
    public int getBmk010pageTop() {
        return bmk010pageTop__;
    }

    /**
     * <p>bmk010pageTop をセットします。
     * @param bmk010pageTop bmk010pageTop
     */
    public void setBmk010pageTop(int bmk010pageTop) {
        bmk010pageTop__ = bmk010pageTop;
    }

    /**
     * <p>bmk010searchLabel を取得します。
     * @return bmk010searchLabel
     */
    public int getBmk010searchLabel() {
        return bmk010searchLabel__;
    }

    /**
     * <p>bmk010searchLabel をセットします。
     * @param bmk010searchLabel bmk010searchLabel
     */
    public void setBmk010searchLabel(int bmk010searchLabel) {
        bmk010searchLabel__ = bmk010searchLabel;
    }

    /**
     * <p>bmk010sortKey を取得します。
     * @return bmk010sortKey
     */
    public int getBmk010sortKey() {
        return bmk010sortKey__;
    }

    /**
     * <p>bmk010sortKey をセットします。
     * @param bmk010sortKey bmk010sortKey
     */
    public void setBmk010sortKey(int bmk010sortKey) {
        bmk010sortKey__ = bmk010sortKey;
    }

    /**
     * <p>bmk010userCmbList を取得します。
     * @return bmk010userCmbList
     */
    public List<UsrLabelValueBean> getBmk010userCmbList() {
        return bmk010userCmbList__;
    }

    /**
     * <p>bmk010userCmbList をセットします。
     * @param bmk010userCmbList bmk010userCmbList
     */
    public void setBmk010userCmbList(List<UsrLabelValueBean> bmk010userCmbList) {
        bmk010userCmbList__ = bmk010userCmbList;
    }

    /**
     * <p>bmk010userSid を取得します。
     * @return bmk010userSid
     */
    public int getBmk010userSid() {
        return bmk010userSid__;
    }

    /**
     * <p>bmk010userSid をセットします。
     * @param bmk010userSid bmk010userSid
     */
    public void setBmk010userSid(int bmk010userSid) {
        bmk010userSid__ = bmk010userSid;
    }

    /**
     * <p>bmk010viewAdminBtn を取得します。
     * @return bmk010viewAdminBtn
     */
    public int getBmk010viewAdminBtn() {
        return bmk010viewAdminBtn__;
    }

    /**
     * <p>bmk010viewAdminBtn をセットします。
     * @param bmk010viewAdminBtn bmk010viewAdminBtn
     */
    public void setBmk010viewAdminBtn(int bmk010viewAdminBtn) {
        bmk010viewAdminBtn__ = bmk010viewAdminBtn;
    }

    /**
     * <p>procMode を取得します。
     * @return procMode
     */
    public int getProcMode() {
        return procMode__;
    }

    /**
     * <p>procMode をセットします。
     * @param procMode procMode
     */
    public void setProcMode(int procMode) {
        procMode__ = procMode;
    }

    /**
     * <p>returnPage を取得します。
     * @return returnPage
     */
    public String getReturnPage() {
        return returnPage__;
    }

    /**
     * <p>returnPage をセットします。
     * @param returnPage returnPage
     */
    public void setReturnPage(String returnPage) {
        returnPage__ = returnPage;
    }

    /**
     * <p>bmk010searchLabelName を取得します。
     * @return bmk010searchLabelName
     */
    public String getBmk010searchLabelName() {
        return bmk010searchLabelName__;
    }

    /**
     * <p>bmk010searchLabelName をセットします。
     * @param bmk010searchLabelName bmk010searchLabelName
     */
    public void setBmk010searchLabelName(String bmk010searchLabelName) {
        bmk010searchLabelName__ = bmk010searchLabelName;
    }

    /**
     * <p>editBmkSid を取得します。
     * @return editBmkSid
     */
    public int getEditBmkSid() {
        return editBmkSid__;
    }

    /**
     * <p>editBmkSid をセットします。
     * @param editBmkSid editBmkSid
     */
    public void setEditBmkSid(int editBmkSid) {
        editBmkSid__ = editBmkSid;
    }

    /**
     * <p>bmk010editPow を取得します。
     * @return bmk010editPow
     */
    public int getBmk010editPow() {
        return bmk010editPow__;
    }

    /**
     * <p>bmk010editPow をセットします。
     * @param bmk010editPow bmk010editPow
     */
    public void setBmk010editPow(int bmk010editPow) {
        bmk010editPow__ = bmk010editPow;
    }

    /**
     * <p>bmk010viewGroupBtn を取得します。
     * @return bmk010viewGroupBtn
     */
    public int getBmk010viewGroupBtn() {
        return bmk010viewGroupBtn__;
    }

    /**
     * <p>bmk010viewGroupBtn をセットします。
     * @param bmk010viewGroupBtn bmk010viewGroupBtn
     */
    public void setBmk010viewGroupBtn(int bmk010viewGroupBtn) {
        bmk010viewGroupBtn__ = bmk010viewGroupBtn;
    }

    /**
     * <p>bmk010BookmarkList を取得します。
     * @return bmk010BookmarkList
     */
    public List<Bmk010BodyModel> getBmk010BookmarkList() {
        return bmk010BookmarkList__;
    }

    /**
     * <p>bmk010BookmarkList をセットします。
     * @param bmk010BookmarkList bmk010BookmarkList
     */
    public void setBmk010BookmarkList(List<Bmk010BodyModel> bmk010BookmarkList) {
        bmk010BookmarkList__ = bmk010BookmarkList;
    }

    /**
     * <p>bmk010delInfSid を取得します。
     * @return bmk010delInfSid
     */
    public String[] getBmk010delInfSid() {
        return bmk010delInfSid__;
    }

    /**
     * <p>bmk010delInfSid をセットします。
     * @param bmk010delInfSid bmk010delInfSid
     */
    public void setBmk010delInfSid(String[] bmk010delInfSid) {
        bmk010delInfSid__ = bmk010delInfSid;
    }

    /**
     * <p>bmk010SelectedDelSid を取得します。
     * @return bmk010SelectedDelSid
     */
    public ArrayList<String> getBmk010SelectedDelSid() {
        return bmk010SelectedDelSid__;
    }

    /**
     * <p>bmk010SelectedDelSid をセットします。
     * @param bmk010SelectedDelSid bmk010SelectedDelSid
     */
    public void setBmk010SelectedDelSid(ArrayList<String> bmk010SelectedDelSid) {
        bmk010SelectedDelSid__ = bmk010SelectedDelSid;
    }

    /**
     * <p>bmk010searchUse を取得します。
     * @return bmk010searchUse
     */
    public int getBmk010searchUse() {
        return bmk010searchUse__;
    }

    /**
     * <p>bmk010searchUse をセットします。
     * @param bmk010searchUse bmk010searchUse
     */
    public void setBmk010searchUse(int bmk010searchUse) {
        bmk010searchUse__ = bmk010searchUse;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParam(Cmn999Form msgForm) {

        //処理モード
        msgForm.addHiddenParam("procMode", procMode__);
        //URLSID
        msgForm.addHiddenParam("editBmuSid", editBmuSid__);
        //ブックマークSID
        msgForm.addHiddenParam("editBmkSid", editBmkSid__);
        //遷移元
        msgForm.addHiddenParam("returnPage", returnPage__);

        //ブックマーク区分
        msgForm.addHiddenParam("bmk010mode", bmk010mode__);
        //削除ブックマークSID
        msgForm.addHiddenParam("bmk010delInfSid", bmk010delInfSid__);
        //並び順
        msgForm.addHiddenParam("bmk010orderKey", bmk010orderKey__);
        //ソートキー
        msgForm.addHiddenParam("bmk010sortKey", bmk010sortKey__);
        //グループSID
        msgForm.addHiddenParam("bmk010groupSid", bmk010groupSid__);
        //ユーザSID
        msgForm.addHiddenParam("bmk010userSid", bmk010userSid__);
        //ラベル選択
        msgForm.addHiddenParam("bmk010searchLabel", bmk010searchLabel__);
        //ページ
        msgForm.addHiddenParam("bmk010page", bmk010page__);
        //ページ上段
        msgForm.addHiddenParam("bmk010pageTop", bmk010pageTop__);
        //ページ下段
        msgForm.addHiddenParam("bmk010pageBottom", bmk010pageBottom__);
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param umodel セッションユーザ情報
     * @return エラー
     * @throws SQLException 
     */
    public ActionErrors validateCheckBmk010(
            Connection con, RequestModel reqMdl, BaseUserModel umodel) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("bmk.56");

        //削除ブックマークSIDSID
        GSValidateBookmark.validateDeleteCir(errors, bmk010delInfSid__, msg);

        if (errors.size() == 0) {
            ActionMessage message = null;
            message = new ActionMessage("error.input.notvalidate.data", msg);

            //配列内に数値以外が存在しないか確認
            boolean numCheck = Stream.of(bmk010delInfSid__)
                    .allMatch(str -> ValidateUtil.isNumber(str));
            if (!numCheck) {
                StrutsUtil.addMessage(errors, message, "delBmk");
                return errors;
            }

            List<Integer> delSidList = Stream.of(bmk010delInfSid__)
                    .map(Integer::valueOf).collect(Collectors.toList());
            BookmarkBiz bmkBiz = new BookmarkBiz(con, reqMdl);
            if (!bmkBiz.isEditBookmark(
                    con, umodel, -1, -1, GSConstBookmark.BMK_MODE_EDIT, delSidList)) {
                StrutsUtil.addMessage(errors, message, "delBmk");
            }
        }
        return errors;
    }

    /**
     * <p>bmk150PageNum を取得します。
     * @return bmk150PageNum
     */
    public int getBmk150PageNum() {
        return bmk150PageNum__;
    }

    /**
     * <p>bmk150PageNum をセットします。
     * @param bmk150PageNum bmk150PageNum
     */
    public void setBmk150PageNum(int bmk150PageNum) {
        bmk150PageNum__ = bmk150PageNum;
    }

    /**
     * <p>bmk070ToBmk150DspFlg を取得します。
     * @return bmk070ToBmk150DspFlg
     */
    public boolean isBmk070ToBmk150DspFlg() {
        return bmk070ToBmk150DspFlg__;
    }

    /**
     * <p>bmk070ToBmk150DspFlg をセットします。
     * @param bmk070ToBmk150DspFlg bmk070ToBmk150DspFlg
     */
    public void setBmk070ToBmk150DspFlg(boolean bmk070ToBmk150DspFlg) {
        bmk070ToBmk150DspFlg__ = bmk070ToBmk150DspFlg;
    }
}