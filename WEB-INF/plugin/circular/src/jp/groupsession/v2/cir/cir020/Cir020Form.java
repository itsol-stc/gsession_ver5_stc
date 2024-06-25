package jp.groupsession.v2.cir.cir020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.GSValidateCircular;
import jp.groupsession.v2.cir.cir010.Cir010Form;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 回覧板 受信確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020Form extends Cir010Form {

    //入力項目
    /** メモ */
    private String cir020memo__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] cir020UserTmpSelectFiles__ = null;

    //表示項目
    /** 回覧板情報 */
    private CircularDspModel cir020dspMdl__ = null;
    /** 添付ファイルリスト */
    private List<CmnBinfModel> cir020fileList__ = null;
    /** 確認・未確認 */
    private String kakuninStr__ = null;
    /** 受信者確認リスト */
    private List<CircularDspModel> cir030memList__ = null;
    /** メモ欄の修正フラグ(可・不可) */
    private int memoFlg__ = 0;
    /** 回覧先ユーザ用 ファイルコンボ */
    private List<LabelValueBean> cir020UserTmpFileLabelList__ = null;
    /** 回覧先ユーザ用 添付ファイルSID */
    private String[] cir020UserTmpFileSids__ = null;

    //確認リストフィルタリング
    /** 確認リストフィルタリング グループ */
    private String cirMemListGroup__ = String.valueOf(GSConstCircular.GRPFILTER_ALL);
    /** 確認リストフィルタリング グループコンボ */
    private List<CmnLabelValueModel> cirMemListGroupCombo__ = null;

    //非表示項目
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String cir020binSid__ = null;
    /** 添付ファイルのアカウントSID(ダウンロード時) */
    private String cir020cacSid__ = null;
    /** 前への移動が可能か */
    private boolean cir020PrevBound__;
    /** 次への移動が可能か */
    private boolean cir020NextBound__;
    /** オーダーキー */
    private int cir030orderKey__ = GSConst.ORDER_KEY_DESC;
    /** ソートキー */
    private int cir030sortKey__ = GSConstCircular.SAKI_SORT_KAKU;
    /** プラグインID */
    private String cir020pluginId__ = GSConstCircular.PLUGIN_ID_CIRCULAR;

    //前画面(メインメニュー)
    /** 週間スケジュール 表示日付 */
    private String schWeekDate__;
    /** 日間スケジュール 表示日付 */
    private String schDailyDate__;

    //回覧板詳細検索画面のパラメータ////////////////////////////////////
    //入力項目
    /** 回覧板種別 */
    private int cir060syubetsu__ = Integer.parseInt(GSConstCircular.MODE_JUSIN);
    /** 発信者グループ */
    private String cir060groupSid__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 発信者 */
    private int cir060userSid__ = GSConstCommon.NUM_INIT;
    /** キーワード検索区分 */
    private int cir060wordKbn__ = GSConstCircular.KEY_WORD_KBN_AND;
    /** 回覧先SID */
    private String[] cir060selUserSid__ = null;
    /** 検索対象 */
    private String[] cir060searchTarget__ = null;
    /** ソートキー1 */
    private int cir060sort1__ = GSConstCircular.SORT_DATE;
    /** オーダーキー1 */
    private int cir060order1__ = GSConst.ORDER_KEY_DESC;
    /** ソートキー2 */
    private int cir060sort2__ = GSConstCircular.SORT_TITLE;
    /** オーダーキー2 */
    private int cir060order2__ = GSConst.ORDER_KEY_ASC;
    /** ページ1 */
    private int cir060pageNum1__;
    /** ページ2 */
    private int cir060pageNum2__;
    /** セーブ 検索キーワード */
    private String cir010svSearchWord__;
    /** セーブ 回覧板種別 */
    private int cir060svSyubetsu__ = Integer.parseInt(GSConstCircular.MODE_JUSIN);
    /** セーブ 発信者グループ */
    private String cir060svGroupSid__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** セーブ 発信者 */
    private int cir060svUserSid__ = GSConstCommon.NUM_INIT;
    /** セーブ キーワード検索区分 */
    private int cir060svWordKbn__ = GSConstCircular.KEY_WORD_KBN_AND;
    /** セーブ 回覧先SID */
    private String[] cir060svSelUserSid__ = null;
    /** セーブ 検索対象 */
    private String[] cir060svSearchTarget__ = null;
    /** セーブ ソートキー1 */
    private int cir060svSort1__ = GSConstCircular.SORT_DATE;
    /** セーブ オーダーキー1 */
    private int cir060svOrder1__ = GSConst.ORDER_KEY_DESC;
    /** セーブ ソートキー2 */
    private int cir060svSort2__ = GSConstCircular.SORT_TITLE;
    /** セーブ オーダーキー2 */
    private int cir060svOrder2__ = GSConst.ORDER_KEY_ASC;
    /** 検索フラグ */
    private boolean cir060searchFlg__ = false;
    /** 遷移元画面ID */
    private String cir060dspId__ = null;
    /** 削除ボタン押下フラグ */
    private boolean cirDelFlg__ = true;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int cir020searchUse__ = GSConst.PLUGIN_USE;



    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     */
    public ActionErrors validateConfCheck(
            RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();

        //メモ内容
        GSValidateCircular.validateMemo(errors, cir020memo__, reqMdl);
        
        //メモ修正可否チェック
        GSValidateCircular.validateMemoEdit(
                errors, getCir010selectInfSid(), getCirViewAccount(), reqMdl, con);

        return errors;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("cirViewAccount", getCirViewAccount());
        cmn999Form.addHiddenParam("cirAccountSid", getCirAccountSid());
        cmn999Form.addHiddenParam("cir010selectInfSid", getCir010selectInfSid());
        cmn999Form.addHiddenParam("cir010pageNum1", getCir010pageNum1());
        cmn999Form.addHiddenParam("cir010pageNum2", getCir010pageNum2());
        cmn999Form.addHiddenParam("cir010cmdMode", getCir010cmdMode());
        cmn999Form.addHiddenParam("cir010orderKey", getCir010orderKey());
        cmn999Form.addHiddenParam("cir010sortKey", getCir010sortKey());
        cmn999Form.addHiddenParam("schWeekDate", getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", getSchDailyDate());
        cmn999Form.addHiddenParam("cir010SelectLabelSid", getCir010SelectLabelSid());
        cmn999Form.addHiddenParam("cir060searchFlg", String.valueOf(isCir060searchFlg()));
        cmn999Form.addHiddenParam("cir010svSearchWord", getCir010svSearchWord());
        cmn999Form.addHiddenParam("cir060svSyubetsu", getCir060svSyubetsu());
        cmn999Form.addHiddenParam("cir060svGroupSid", getCir060svGroupSid());
        cmn999Form.addHiddenParam("cir060svUserSid", getCir060svUserSid());
        cmn999Form.addHiddenParam("cir060svWordKbn", getCir060svWordKbn());
        cmn999Form.addHiddenParam("cir060svSort1", getCir060svSort1());
        cmn999Form.addHiddenParam("cir060svOrder1", getCir060svOrder1());
        cmn999Form.addHiddenParam("cir060svSort2", getCir060svSort2());
        cmn999Form.addHiddenParam("cir060svOrder2", getCir060svOrder2());
        cmn999Form.addHiddenParam("cir010searchWord", getCir010searchWord());
        cmn999Form.addHiddenParam("cir060syubetsu", getCir060syubetsu());
        cmn999Form.addHiddenParam("cir060groupSid", getCir060groupSid());
        cmn999Form.addHiddenParam("cir060userSid", getCir060userSid());
        cmn999Form.addHiddenParam("cir060wordKbn", getCir060wordKbn());
        cmn999Form.addHiddenParam("cir060sort1", getCir060sort1());
        cmn999Form.addHiddenParam("cir060order1", getCir060order1());
        cmn999Form.addHiddenParam("cir060sort2", getCir060sort2());
        cmn999Form.addHiddenParam("cir060order2", getCir060order2());
        cmn999Form.addHiddenParam("cir060pageNum1", getCir060pageNum1());
        cmn999Form.addHiddenParam("cir060pageNum2", getCir060pageNum2());
        cmn999Form.addHiddenParam("cir060dspId", getCir060dspId());
        cmn999Form.addHiddenParam("cir060selUserSid", getCir060selUserSid());
        cmn999Form.addHiddenParam("cir060svSelUserSid", getCir060svSelUserSid());
        cmn999Form.addHiddenParam("cir060searchTarget", getCir060searchTarget());
        cmn999Form.addHiddenParam("cir060svSearchTarget", getCir060svSearchTarget());
        cmn999Form.addHiddenParam("cir020memo", getCir020memo());
        cmn999Form.addHiddenParam("cir030sortKey", getCir030sortKey());
        cmn999Form.addHiddenParam("cir030orderKey", getCir030orderKey());
        cmn999Form.addHiddenParam("cirDelFlg", String.valueOf(isCirDelFlg()));
    }

    /**
     * <p>cir020dspMdl を取得します。
     * @return cir020dspMdl
     */
    public CircularDspModel getCir020dspMdl() {
        return cir020dspMdl__;
    }

    /**
     * <p>cir020dspMdl をセットします。
     * @param cir020dspMdl cir020dspMdl
     */
    public void setCir020dspMdl(CircularDspModel cir020dspMdl) {
        cir020dspMdl__ = cir020dspMdl;
    }

    /**
     * <p>cir020memo を取得します。
     * @return cir020memo
     */
    public String getCir020memo() {
        return cir020memo__;
    }

    /**
     * <p>cir020memo をセットします。
     * @param cir020memo cir020memo
     */
    public void setCir020memo(String cir020memo) {
        cir020memo__ = cir020memo;
    }

    /**
     * <p>cir020fileList を取得します。
     * @return cir020fileList
     */
    public List<CmnBinfModel> getCir020fileList() {
        return cir020fileList__;
    }

    /**
     * <p>cir020fileList をセットします。
     * @param cir020fileList cir020fileList
     */
    public void setCir020fileList(List<CmnBinfModel> cir020fileList) {
        cir020fileList__ = cir020fileList;
    }

    /**
     * <p>cir020binSid を取得します。
     * @return cir020binSid
     */
    public String getCir020binSid() {
        return cir020binSid__;
    }

    /**
     * <p>cir020binSid をセットします。
     * @param cir020binSid cir020binSid
     */
    public void setCir020binSid(String cir020binSid) {
        cir020binSid__ = cir020binSid;
    }

    /**
     * <p>cir020NextBound を取得します。
     * @return cir020NextBound
     */
    public boolean isCir020NextBound() {
        return cir020NextBound__;
    }

    /**
     * <p>cir020NextBound をセットします。
     * @param cir020NextBound cir020NextBound
     */
    public void setCir020NextBound(boolean cir020NextBound) {
        cir020NextBound__ = cir020NextBound;
    }

    /**
     * <p>cir030orderKey を取得します。
     * @return cir030orderKey
     */
    public int getCir030orderKey() {
        return cir030orderKey__;
    }

    /**
     * <p>cir030orderKey をセットします。
     * @param cir030orderKey cir030orderKey
     */
    public void setCir030orderKey(int cir030orderKey) {
        cir030orderKey__ = cir030orderKey;
    }

    /**
     * <p>cir030sortKey を取得します。
     * @return cir030sortKey
     */
    public int getCir030sortKey() {
        return cir030sortKey__;
    }

    /**
     * <p>cir030sortKey をセットします。
     * @param cir030sortKey cir030sortKey
     */
    public void setCir030sortKey(int cir030sortKey) {
        cir030sortKey__ = cir030sortKey;
    }

    /**
     * <p>cir020PrevBound を取得します。
     * @return cir020PrevBound
     */
    public boolean isCir020PrevBound() {
        return cir020PrevBound__;
    }

    /**
     * <p>cir020PrevBound をセットします。
     * @param cir020PrevBound cir020PrevBound
     */
    public void setCir020PrevBound(boolean cir020PrevBound) {
        cir020PrevBound__ = cir020PrevBound;
    }

    /**
     * <p>schDailyDate を取得します。
     * @return schDailyDate
     */
    public String getSchDailyDate() {
        return schDailyDate__;
    }

    /**
     * <p>schDailyDate をセットします。
     * @param schDailyDate schDailyDate
     */
    public void setSchDailyDate(String schDailyDate) {
        schDailyDate__ = schDailyDate;
    }

    /**
     * <p>schWeekDate を取得します。
     * @return schWeekDate
     */
    public String getSchWeekDate() {
        return schWeekDate__;
    }

    /**
     * <p>schWeekDate をセットします。
     * @param schWeekDate schWeekDate
     */
    public void setSchWeekDate(String schWeekDate) {
        schWeekDate__ = schWeekDate;
    }

    /**
     * <p>kakuninStr を取得します。
     * @return kakuninStr
     */
    public String getKakuninStr() {
        return kakuninStr__;
    }

    /**
     * <p>kakuninStr をセットします。
     * @param kakuninStr kakuninStr
     */
    public void setKakuninStr(String kakuninStr) {
        kakuninStr__ = kakuninStr;
    }

    /**
     * <p>cir030memList を取得します。
     * @return cir030memList
     */
    public List<CircularDspModel> getCir030memList() {
        return cir030memList__;
    }

    /**
     * <p>cir030memList をセットします。
     * @param cir030memList cir030memList
     */
    public void setCir030memList(List<CircularDspModel> cir030memList) {
        cir030memList__ = cir030memList;
    }

    /**
     * <p>cir010svSearchWord を取得します。
     * @return cir010svSearchWord
     */
    public String getCir010svSearchWord() {
        return cir010svSearchWord__;
    }

    /**
     * <p>cir010svSearchWord をセットします。
     * @param cir010svSearchWord cir010svSearchWord
     */
    public void setCir010svSearchWord(String cir010svSearchWord) {
        cir010svSearchWord__ = cir010svSearchWord;
    }

    /**
     * <p>cir060dspId を取得します。
     * @return cir060dspId
     */
    public String getCir060dspId() {
        return cir060dspId__;
    }

    /**
     * <p>cir060dspId をセットします。
     * @param cir060dspId cir060dspId
     */
    public void setCir060dspId(String cir060dspId) {
        cir060dspId__ = cir060dspId;
    }

    /**
     * <p>cir060order1 を取得します。
     * @return cir060order1
     */
    public int getCir060order1() {
        return cir060order1__;
    }

    /**
     * <p>cir060order1 をセットします。
     * @param cir060order1 cir060order1
     */
    public void setCir060order1(int cir060order1) {
        cir060order1__ = cir060order1;
    }

    /**
     * <p>cir060order2 を取得します。
     * @return cir060order2
     */
    public int getCir060order2() {
        return cir060order2__;
    }

    /**
     * <p>cir060order2 をセットします。
     * @param cir060order2 cir060order2
     */
    public void setCir060order2(int cir060order2) {
        cir060order2__ = cir060order2;
    }

    /**
     * <p>cir060pageNum1 を取得します。
     * @return cir060pageNum1
     */
    public int getCir060pageNum1() {
        return cir060pageNum1__;
    }

    /**
     * <p>cir060pageNum1 をセットします。
     * @param cir060pageNum1 cir060pageNum1
     */
    public void setCir060pageNum1(int cir060pageNum1) {
        cir060pageNum1__ = cir060pageNum1;
    }

    /**
     * <p>cir060pageNum2 を取得します。
     * @return cir060pageNum2
     */
    public int getCir060pageNum2() {
        return cir060pageNum2__;
    }

    /**
     * <p>cir060pageNum2 をセットします。
     * @param cir060pageNum2 cir060pageNum2
     */
    public void setCir060pageNum2(int cir060pageNum2) {
        cir060pageNum2__ = cir060pageNum2;
    }

    /**
     * <p>cir060searchFlg を取得します。
     * @return cir060searchFlg
     */
    public boolean isCir060searchFlg() {
        return cir060searchFlg__;
    }

    /**
     * <p>cir060searchFlg をセットします。
     * @param cir060searchFlg cir060searchFlg
     */
    public void setCir060searchFlg(boolean cir060searchFlg) {
        cir060searchFlg__ = cir060searchFlg;
    }

    /**
     * <p>cir060searchTarget を取得します。
     * @return cir060searchTarget
     */
    public String[] getCir060searchTarget() {
        return cir060searchTarget__;
    }

    /**
     * <p>cir060searchTarget をセットします。
     * @param cir060searchTarget cir060searchTarget
     */
    public void setCir060searchTarget(String[] cir060searchTarget) {
        cir060searchTarget__ = cir060searchTarget;
    }

    /**
     * <p>cir060sort1 を取得します。
     * @return cir060sort1
     */
    public int getCir060sort1() {
        return cir060sort1__;
    }

    /**
     * <p>cir060sort1 をセットします。
     * @param cir060sort1 cir060sort1
     */
    public void setCir060sort1(int cir060sort1) {
        cir060sort1__ = cir060sort1;
    }

    /**
     * <p>cir060sort2 を取得します。
     * @return cir060sort2
     */
    public int getCir060sort2() {
        return cir060sort2__;
    }

    /**
     * <p>cir060sort2 をセットします。
     * @param cir060sort2 cir060sort2
     */
    public void setCir060sort2(int cir060sort2) {
        cir060sort2__ = cir060sort2;
    }

    /**
     * <p>cir060svOrder1 を取得します。
     * @return cir060svOrder1
     */
    public int getCir060svOrder1() {
        return cir060svOrder1__;
    }

    /**
     * <p>cir060svOrder1 をセットします。
     * @param cir060svOrder1 cir060svOrder1
     */
    public void setCir060svOrder1(int cir060svOrder1) {
        cir060svOrder1__ = cir060svOrder1;
    }

    /**
     * <p>cir060svOrder2 を取得します。
     * @return cir060svOrder2
     */
    public int getCir060svOrder2() {
        return cir060svOrder2__;
    }

    /**
     * <p>cir060svOrder2 をセットします。
     * @param cir060svOrder2 cir060svOrder2
     */
    public void setCir060svOrder2(int cir060svOrder2) {
        cir060svOrder2__ = cir060svOrder2;
    }

    /**
     * <p>cir060svSearchTarget を取得します。
     * @return cir060svSearchTarget
     */
    public String[] getCir060svSearchTarget() {
        return cir060svSearchTarget__;
    }

    /**
     * <p>cir060svSearchTarget をセットします。
     * @param cir060svSearchTarget cir060svSearchTarget
     */
    public void setCir060svSearchTarget(String[] cir060svSearchTarget) {
        cir060svSearchTarget__ = cir060svSearchTarget;
    }

    /**
     * <p>cir060svSort1 を取得します。
     * @return cir060svSort1
     */
    public int getCir060svSort1() {
        return cir060svSort1__;
    }

    /**
     * <p>cir060svSort1 をセットします。
     * @param cir060svSort1 cir060svSort1
     */
    public void setCir060svSort1(int cir060svSort1) {
        cir060svSort1__ = cir060svSort1;
    }

    /**
     * <p>cir060svSort2 を取得します。
     * @return cir060svSort2
     */
    public int getCir060svSort2() {
        return cir060svSort2__;
    }

    /**
     * <p>cir060svSort2 をセットします。
     * @param cir060svSort2 cir060svSort2
     */
    public void setCir060svSort2(int cir060svSort2) {
        cir060svSort2__ = cir060svSort2;
    }

    /**
     * <p>cir060svSyubetsu を取得します。
     * @return cir060svSyubetsu
     */
    public int getCir060svSyubetsu() {
        return cir060svSyubetsu__;
    }

    /**
     * <p>cir060svSyubetsu をセットします。
     * @param cir060svSyubetsu cir060svSyubetsu
     */
    public void setCir060svSyubetsu(int cir060svSyubetsu) {
        cir060svSyubetsu__ = cir060svSyubetsu;
    }

    /**
     * <p>cir060svUserSid を取得します。
     * @return cir060svUserSid
     */
    public int getCir060svUserSid() {
        return cir060svUserSid__;
    }

    /**
     * <p>cir060svUserSid をセットします。
     * @param cir060svUserSid cir060svUserSid
     */
    public void setCir060svUserSid(int cir060svUserSid) {
        cir060svUserSid__ = cir060svUserSid;
    }

    /**
     * <p>cir060svWordKbn を取得します。
     * @return cir060svWordKbn
     */
    public int getCir060svWordKbn() {
        return cir060svWordKbn__;
    }

    /**
     * <p>cir060svWordKbn をセットします。
     * @param cir060svWordKbn cir060svWordKbn
     */
    public void setCir060svWordKbn(int cir060svWordKbn) {
        cir060svWordKbn__ = cir060svWordKbn;
    }

    /**
     * <p>cir060syubetsu を取得します。
     * @return cir060syubetsu
     */
    public int getCir060syubetsu() {
        return cir060syubetsu__;
    }

    /**
     * <p>cir060syubetsu をセットします。
     * @param cir060syubetsu cir060syubetsu
     */
    public void setCir060syubetsu(int cir060syubetsu) {
        cir060syubetsu__ = cir060syubetsu;
    }

    /**
     * <p>cir060userSid を取得します。
     * @return cir060userSid
     */
    public int getCir060userSid() {
        return cir060userSid__;
    }

    /**
     * <p>cir060userSid をセットします。
     * @param cir060userSid cir060userSid
     */
    public void setCir060userSid(int cir060userSid) {
        cir060userSid__ = cir060userSid;
    }

    /**
     * <p>cir060wordKbn を取得します。
     * @return cir060wordKbn
     */
    public int getCir060wordKbn() {
        return cir060wordKbn__;
    }

    /**
     * <p>cir060wordKbn をセットします。
     * @param cir060wordKbn cir060wordKbn
     */
    public void setCir060wordKbn(int cir060wordKbn) {
        cir060wordKbn__ = cir060wordKbn;
    }

    /**
     * <p>cirDelFlg を取得します。
     * @return cirDelFlg
     */
    public boolean isCirDelFlg() {
        return cirDelFlg__;
    }

    /**
     * <p>cirDelFlg をセットします。
     * @param cirDelFlg cirDelFlg
     */
    public void setCirDelFlg(boolean cirDelFlg) {
        cirDelFlg__ = cirDelFlg;
    }

    /**
     * <p>cir020searchUse を取得します。
     * @return cir020searchUse
     */
    public int getCir020searchUse() {
        return cir020searchUse__;
    }

    /**
     * <p>cir020searchUse をセットします。
     * @param cir020searchUse cir020searchUse
     */
    public void setCir020searchUse(int cir020searchUse) {
        cir020searchUse__ = cir020searchUse;
    }

    /**
     * @return memoFlg
     */
    public int getMemoFlg() {
        return memoFlg__;
    }

    /**
     * @param memoFlg セットする memoFlg
     */
    public void setMemoFlg(int memoFlg) {
        memoFlg__ = memoFlg;
    }

    /**
     * <p>cir020UserTmpFileLabelList を取得します。
     * @return cir020UserTmpFileLabelList
     */
    public List<LabelValueBean> getCir020UserTmpFileLabelList() {
        return cir020UserTmpFileLabelList__;
    }

    /**
     * <p>cir020UserTmpFileLabelList をセットします。
     * @param cir020UserTmpFileLabelList cir020UserTmpFileLabelList
     */
    public void setCir020UserTmpFileLabelList(
            List<LabelValueBean> cir020UserTmpFileLabelList) {
        cir020UserTmpFileLabelList__ = cir020UserTmpFileLabelList;
    }

    /**
     * <p>cir020UserTmpFileSids を取得します。
     * @return cir020UserTmpFileSids
     */
    public String[] getCir020UserTmpFileSids() {
        return cir020UserTmpFileSids__;
    }

    /**
     * <p>cir020UserTmpFileSids をセットします。
     * @param cir020UserTmpFileSids cir020UserTmpFileSids
     */
    public void setCir020UserTmpFileSids(String[] cir020UserTmpFileSids) {
        cir020UserTmpFileSids__ = cir020UserTmpFileSids;
    }

    /**
     * <p>cir020UserTmpSelectFiles を取得します。
     * @return cir020UserTmpSelectFiles
     */
    public String[] getCir020UserTmpSelectFiles() {
        return cir020UserTmpSelectFiles__;
    }

    /**
     * <p>cir020UserTmpSelectFiles をセットします。
     * @param cir020UserTmpSelectFiles cir020UserTmpSelectFiles
     */
    public void setCir020UserTmpSelectFiles(String[] cir020UserTmpSelectFiles) {
        cir020UserTmpSelectFiles__ = cir020UserTmpSelectFiles;
    }

    /**
     * <p>cir020pluginId を取得します。
     * @return cir020pluginId
     */
    public String getCir020pluginId() {
        return cir020pluginId__;
    }

    /**
     * <p>cir020pluginId をセットします。
     * @param cir020pluginId cir020pluginId
     */
    public void setCir020pluginId(String cir020pluginId) {
        cir020pluginId__ = cir020pluginId;
    }

    /**
     * <p>cir060selUserSid を取得します。
     * @return cir060selUserSid
     */
    public String[] getCir060selUserSid() {
        return cir060selUserSid__;
    }

    /**
     * <p>cir060selUserSid をセットします。
     * @param cir060selUserSid cir060selUserSid
     */
    public void setCir060selUserSid(String[] cir060selUserSid) {
        cir060selUserSid__ = cir060selUserSid;
    }

    /**
     * <p>cir060svSelUserSid を取得します。
     * @return cir060svSelUserSid
     */
    public String[] getCir060svSelUserSid() {
        return cir060svSelUserSid__;
    }

    /**
     * <p>cir060svSelUserSid をセットします。
     * @param cir060svSelUserSid cir060svSelUserSid
     */
    public void setCir060svSelUserSid(String[] cir060svSelUserSid) {
        cir060svSelUserSid__ = cir060svSelUserSid;
    }

    /**
     * <p>cir060groupSid を取得します。
     * @return cir060groupSid
     */
    public String getCir060groupSid() {
        return cir060groupSid__;
    }

    /**
     * <p>cir060groupSid をセットします。
     * @param cir060groupSid cir060groupSid
     */
    public void setCir060groupSid(String cir060groupSid) {
        cir060groupSid__ = cir060groupSid;
    }

    /**
     * <p>cir060svGroupSid を取得します。
     * @return cir060svGroupSid
     */
    public String getCir060svGroupSid() {
        return cir060svGroupSid__;
    }

    /**
     * <p>cir060svGroupSid をセットします。
     * @param cir060svGroupSid cir060svGroupSid
     */
    public void setCir060svGroupSid(String cir060svGroupSid) {
        cir060svGroupSid__ = cir060svGroupSid;
    }

    /**
     * <p>cirMemListGroup を取得します。
     * @return cirMemListGroup
     */
    public String getCirMemListGroup() {
        return cirMemListGroup__;
    }

    /**
     * <p>cirMemListGroup をセットします。
     * @param cirMemListGroup cirMemListGroup
     */
    public void setCirMemListGroup(String cirMemListGroup) {
        cirMemListGroup__ = cirMemListGroup;
    }

    /**
     * <p>cirMemListGroupCombo を取得します。
     * @return cirMemListGroupCombo
     */
    public List<CmnLabelValueModel> getCirMemListGroupCombo() {
        return cirMemListGroupCombo__;
    }

    /**
     * <p>cirMemListGroupCombo をセットします。
     * @param cirMemListGroupCombo cirMemListGroupCombo
     */
    public void setCirMemListGroupCombo(
            List<CmnLabelValueModel> cirMemListGroupCombo) {
        cirMemListGroupCombo__ = cirMemListGroupCombo;
    }

    /**
     * <p>cir020cacSid を取得します。
     * @return cir020cacSid
     */
    public String getCir020cacSid() {
        return cir020cacSid__;
    }

    /**
     * <p>cir020cacSid をセットします。
     * @param cir020cacSid cir020cacSid
     */
    public void setCir020cacSid(String cir020cacSid) {
        cir020cacSid__ = cir020cacSid;
    }

}
