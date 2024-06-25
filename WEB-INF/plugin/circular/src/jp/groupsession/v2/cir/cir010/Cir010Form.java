package jp.groupsession.v2.cir.cir010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.GSValidateCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.model.AccountDataModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.model.LabelDataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir010Form extends AbstractGsForm {

    /** 表示ユーザ */
    private int cirViewUser__ = 0;
    /** 表示アカウントSID */
    private int cirViewAccount__ = 0;
    /** 表示アカウント 名*/
    private String cirViewAccountName__;
    /** 表示アカウント ログイン有効フラグ*/
    private int cirViewAccountUko__ = 0;
    /** アカウント管理モード */
    private int cirAccountMode__ = 0;
    /** アカウントSID */
    private int cirAccountSid__ = 0;
    /** 編集モード */
    private int cirCmdMode__ = GSConstCircular.CMDMODE_ADD;
    /** 回覧板作成可能ユーザ区分 */
    private boolean cirCreateFlg__ = false;

    /** アカウント一覧 */
    private List<AccountDataModel> cir010AccountList__;
    /** アカウントテーマ */
    private String cir010AccountTheme__;

    //入力項目
    /** ページ1 */
    private int cir010pageNum1__;
    /** ページ2 */
    private int cir010pageNum2__;
    /** 削除回覧板SID */
    private String[] cir010delInfSid__;
    /** 検索キーワード */
    private String cir010searchWord__;

    //表示項目
    /** 回覧板リスト */
    private List<CircularDspModel> cir010CircularList__ = null;
    /** ページラベル */
    ArrayList < LabelValueBean > cir010PageLabel__;

    //非表示項目
    /** 処理モード */
    private String cir010cmdMode__ = GSConstCircular.MODE_JUSIN;
    /** オーダーキー */
    private int cir010orderKey__ = GSConst.ORDER_KEY_DESC;
    /** ソートキー */
    private int cir010sortKey__ = GSConstCircular.SORT_DATE;
    /** saveリスト(現在ページ以外でチェックされている値) */
    ArrayList < String > cir010saveList__;
    /** 選択した回覧板SID */
    private String cir010selectInfSid__ = null;
    /** 選択した回覧板の送信受信区分 */
    private String cir010sojuKbn__ = GSConstCircular.MODE_JUSIN;
    /** 自動リロード時間 */
    private int cir010Reload__ = GSConstCircular.AUTO_RELOAD_10MIN;

    //その他パラメータ
    /** 未確認カウント */
    private int mikakkuCount__;
    /** 回覧板 登録モード(新規作成 or 複写して新規作成) */
    private int cirEntryMode__ = GSConstCircular.CIR_ENTRYMODE_NEW;

    /** 編集する回覧板SID */
    private String cirEditInfSid__ = null;

    //管理者権限
    /** 管理者権限有無 */
    private int adminFlg__ = GSConst.USER_NOT_ADMIN;

    /** 遷移元 メイン個人メニュー:2 メイン管理者メニュー:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 管理者ユーザフラグ */
    private boolean cir010adminUser__ = false;

    /** エラー */
    private List<String> errorsList__ = null;


    /** ラベルリスト */
    private List<LabelDataModel> cir010LabelList__ = null;

    /** ラベル追加種別 0:既存のラベルを付加、1:新規登録したラベルを追加 */
    private int cir010addLabelType__ = GSConstCircular.ADDLABEL_NORMAL;

    /** 選択ラベルSID */
    private int cir010SelectLabelSid__ = 0;
    /** 追加ラベルSID */
    private int cir010addLabel__ = 0;
    /** 追加ラベル名(ラベル新規登録) */
    private String cir010addLabelName__ = null;
    /** 削除ラベルSID */
    private int cir010delLabel__ = 0;
    /** タイトルラベル名 */
    private String cir010TitleLabelName__ = null;

    /** 受信未読件数 */
    private int cir010JusinMidokuCnt__ = 0;
    /** ゴミ箱未読件数 */
    private int cir010GomiMidokuCnt__ = 0;



    /**
     * <br>[機  能] ラベルアクセスチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con Connection
     * @throws SQLException  実行例外
     * @return エラー
     */
    public ActionErrors validateLabelAccessCheck(
            RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        CirCommonBiz cirBiz = new CirCommonBiz();
        if (!cirBiz.checkExistLabel(con,
                cir010SelectLabelSid__,
                cirViewAccount__)) {
            String msgKey = "search.data.notfound";
            ActionMessage msg = new ActionMessage(msgKey,
                    gsMsg.getMessage("cmn.label"));
            StrutsUtil.addMessage(errors, msg, msgKey);
        }
        return errors;
    }

    /**
     * <br>[機  能] 選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con Connection
     * @return エラー
     * @throws SQLException SQL例外
     */
    public ActionErrors validateSelectCheck(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();

        //回覧板SID
        GSValidateCircular.validateDeleteCir(
                reqMdl,
                con,
                errors,
                cir010delInfSid__,
                cirViewAccount__
                );

        return errors;
    }

    /**
     * <br>[機  能] 選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con Connection
     * @return エラー
     * @throws SQLException SQL例外
     */
    public ActionErrors validateSelectLabelCheck(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // モードチェック
        if (cir010cmdMode__.equals(GSConstCircular.MODE_GOMI)) {
            String msgKey = "error.access.double.submit";
            ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cmn.draft"));
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }

        //回覧板SID
        GSValidateCircular.validateDeleteCir(
                reqMdl,
                con,
                errors,
                cir010delInfSid__,
                cirViewAccount__
                );

        return errors;
    }


    /**
     * <br>[機  能] ラベルチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con Connection
     * @param type 処理種別 0:ラベル追加 1:ラベル削除
     * @return エラー
     * @throws SQLException SQL例外
     */
    public ActionErrors validateLabelCheck(
            RequestModel reqMdl,
            Connection con,
            int type) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // 新規で追加する場合
        if (type  == GSConstCircular.CIR_LABEL_ADD
                && cir010addLabelType__ == GSConstCircular.ADDLABEL_NEW) {
            GSValidateCircular.validateTextBoxInput(errors, cir010addLabelName__,
                    "cir010LabelName",
                    gsMsg.getMessage("cmn.label.name"),
                    GSConstCircular.MAXLEN_LABEL_NAME,
                    true);
        } else if (type  == GSConstCircular.CIR_LABEL_ADD
                && cir010addLabelType__ == GSConstCircular.ADDLABEL_NORMAL) {
            // 追加でラベルを選択する場合
            CirCommonBiz cirBiz = new CirCommonBiz();
            if (!cirBiz.checkExistLabel(con,
                    cir010addLabel__,
                    cirViewAccount__)) {
                String msgKey = "search.data.notfound";
                ActionMessage msg = new ActionMessage(msgKey,
                        gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(errors, msg, msgKey);
            }
        } else if (type == GSConstCircular.CIR_LABEL_DEL) {
            // 削除でラベルを選択する場合
            CirCommonBiz cirBiz = new CirCommonBiz();
            if (!cirBiz.checkExistLabel(con,
                    cir010delLabel__,
                    cirViewAccount__)) {
                String msgKey = "search.data.notfound";
                ActionMessage msg = new ActionMessage(msgKey,
                        gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(errors, msg, msgKey);
            }
        } else {
            String msgKey = "error.access.double.submit";
            ActionMessage msg = new ActionMessage("error.access.double.submit");
            StrutsUtil.addMessage(errors, msg, msgKey);
        }
        if (!errors.isEmpty()) {
            String msgKey = null;
            if (type  == GSConstCircular.CIR_LABEL_ADD) {
                msgKey = "error.cant.entry.label";
            } else if (type  == GSConstCircular.CIR_LABEL_ADD) {
                msgKey = "error.cant.delete.label";
            }
            if (msgKey != null) {
                ActionMessage msg = new ActionMessage(msgKey);
                StrutsUtil.addMessage(errors, msg, msgKey);
            }
        }
        return errors;
    }


    /**
     * <br>[機  能] 入力チェックを行う(検索)
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateSearch(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        //検索キーワード
        GSValidateCircular.validateSearchKeyword(errors, cir010searchWord__, reqMdl);

        return errors;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        msgForm.addHiddenParam("cirAccountMode", getCirAccountMode());
        msgForm.addHiddenParam("cirAccountSid", getCirAccountSid());
        msgForm.addHiddenParam("cirViewAccount", getCirViewAccount());
        msgForm.addHiddenParam("cirCmdMode", getCirCmdMode());

        msgForm.addHiddenParam("cir010cmdMode", getCir010cmdMode());
        msgForm.addHiddenParam("cir010pageNum1", getCir010pageNum1());
        msgForm.addHiddenParam("cir010pageNum2", getCir010pageNum2());
        msgForm.addHiddenParam("cir010orderKey", getCir010orderKey());
        msgForm.addHiddenParam("cir010sortKey", getCir010sortKey());
        msgForm.addHiddenParam("cir010SelectLabelSid", getCir010SelectLabelSid());
    }

    /**
     * <p>adminFlg を取得します。
     * @return adminFlg
     */
    public int getAdminFlg() {
        return adminFlg__;
    }

    /**
     * <p>adminFlg をセットします。
     * @param adminFlg adminFlg
     */
    public void setAdminFlg(int adminFlg) {
        adminFlg__ = adminFlg;
    }
    /**
     * <p>cir010CircularList を取得します。
     * @return cir010CircularList
     */
    public List<CircularDspModel> getCir010CircularList() {
        return cir010CircularList__;
    }
    /**
     * <p>cir010CircularList をセットします。
     * @param cir010CircularList cir010CircularList
     */
    public void setCir010CircularList(List<CircularDspModel> cir010CircularList) {
        cir010CircularList__ = cir010CircularList;
    }
    /**
     * <p>cir010cmdMode を取得します。
     * @return cir010cmdMode
     */
    public String getCir010cmdMode() {
        return cir010cmdMode__;
    }
    /**
     * <p>cir010cmdMode をセットします。
     * @param cir010cmdMode cir010cmdMode
     */
    public void setCir010cmdMode(String cir010cmdMode) {
        cir010cmdMode__ = cir010cmdMode;
    }
    /**
     * <p>cir010orderKey を取得します。
     * @return cir010orderKey
     */
    public int getCir010orderKey() {
        return cir010orderKey__;
    }
    /**
     * <p>cir010orderKey をセットします。
     * @param cir010orderKey cir010orderKey
     */
    public void setCir010orderKey(int cir010orderKey) {
        cir010orderKey__ = cir010orderKey;
    }
    /**
     * <p>cir010PageLabel を取得します。
     * @return cir010PageLabel
     */
    public ArrayList < LabelValueBean > getCir010PageLabel() {
        return cir010PageLabel__;
    }
    /**
     * <p>cir010PageLabel をセットします。
     * @param cir010PageLabel cir010PageLabel
     */
    public void setCir010PageLabel(ArrayList < LabelValueBean > cir010PageLabel) {
        cir010PageLabel__ = cir010PageLabel;
    }
    /**
     * <p>cir010sortKey を取得します。
     * @return cir010sortKey
     */
    public int getCir010sortKey() {
        return cir010sortKey__;
    }
    /**
     * <p>cir010sortKey をセットします。
     * @param cir010sortKey cir010sortKey
     */
    public void setCir010sortKey(int cir010sortKey) {
        cir010sortKey__ = cir010sortKey;
    }
    /**
     * <p>cir010delInfSid を取得します。
     * @return cir010delInfSid
     */
    public String[] getCir010delInfSid() {
        return cir010delInfSid__;
    }
    /**
     * <p>cir010delInfSid をセットします。
     * @param cir010delInfSid cir010delInfSid
     */
    public void setCir010delInfSid(String[] cir010delInfSid) {
        cir010delInfSid__ = cir010delInfSid;
    }
    /**
     * <p>cir010saveList を取得します。
     * @return cir010saveList
     */
    public ArrayList < String > getCir010saveList() {
        return cir010saveList__;
    }
    /**
     * <p>cir010saveList をセットします。
     * @param cir010saveList cir010saveList
     */
    public void setCir010saveList(ArrayList < String > cir010saveList) {
        cir010saveList__ = cir010saveList;
    }

    /**
     * <p>cir010pageNum1 を取得します。
     * @return cir010pageNum1
     */
    public int getCir010pageNum1() {
        return cir010pageNum1__;
    }

    /**
     * <p>cir010pageNum1 をセットします。
     * @param cir010pageNum1 cir010pageNum1
     */
    public void setCir010pageNum1(int cir010pageNum1) {
        cir010pageNum1__ = cir010pageNum1;
    }

    /**
     * <p>cir010pageNum2 を取得します。
     * @return cir010pageNum2
     */
    public int getCir010pageNum2() {
        return cir010pageNum2__;
    }

    /**
     * <p>cir010pageNum2 をセットします。
     * @param cir010pageNum2 cir010pageNum2
     */
    public void setCir010pageNum2(int cir010pageNum2) {
        cir010pageNum2__ = cir010pageNum2;
    }

    /**
     * <p>cir010selectInfSid を取得します。
     * @return cir010selectInfSid
     */
    public String getCir010selectInfSid() {
        return cir010selectInfSid__;
    }

    /**
     * <p>cir010selectInfSid をセットします。
     * @param cir010selectInfSid cir010selectInfSid
     */
    public void setCir010selectInfSid(String cir010selectInfSid) {
        cir010selectInfSid__ = cir010selectInfSid;
    }

    /**
     * <p>mikakkuCount を取得します。
     * @return mikakkuCount
     */
    public int getMikakkuCount() {
        return mikakkuCount__;
    }

    /**
     * <p>mikakkuCount をセットします。
     * @param mikakkuCount mikakkuCount
     */
    public void setMikakkuCount(int mikakkuCount) {
        mikakkuCount__ = mikakkuCount;
    }

    /**
     * <p>cir010sojuKbn を取得します。
     * @return cir010sojuKbn
     */
    public String getCir010sojuKbn() {
        return cir010sojuKbn__;
    }

    /**
     * <p>cir010sojuKbn をセットします。
     * @param cir010sojuKbn cir010sojuKbn
     */
    public void setCir010sojuKbn(String cir010sojuKbn) {
        cir010sojuKbn__ = cir010sojuKbn;
    }

    /**
     * <p>cir010searchWord を取得します。
     * @return cir010searchWord
     */
    public String getCir010searchWord() {
        return cir010searchWord__;
    }

    /**
     * <p>cir010searchWord をセットします。
     * @param cir010searchWord cir010searchWord
     */
    public void setCir010searchWord(String cir010searchWord) {
        cir010searchWord__ = cir010searchWord;
    }

    /**
     * <p>cir010Reload を取得します。
     * @return cir010Reload
     */
    public int getCir010Reload() {
        return cir010Reload__;
    }

    /**
     * <p>cir010Reload をセットします。
     * @param cir010Reload cir010Reload
     */
    public void setCir010Reload(int cir010Reload) {
        cir010Reload__ = cir010Reload;
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
     * <p>cirEntryMode を取得します。
     * @return cirEntryMode
     */
    public int getCirEntryMode() {
        return cirEntryMode__;
    }

    /**
     * <p>cirEntryMode をセットします。
     * @param cirEntryMode cirEntryMode
     */
    public void setCirEntryMode(int cirEntryMode) {
        cirEntryMode__ = cirEntryMode;
    }

    /**
     * <p>cirViewAccount を取得します。
     * @return cirViewAccount
     */
    public int getCirViewAccount() {
        return cirViewAccount__;
    }

    /**
     * <p>cirViewAccount をセットします。
     * @param cirViewAccount cirViewAccount
     */
    public void setCirViewAccount(int cirViewAccount) {
        cirViewAccount__ = cirViewAccount;
    }

    /**
     * <p>cirAccountMode を取得します。
     * @return cirAccountMode
     */
    public int getCirAccountMode() {
        return cirAccountMode__;
    }

    /**
     * <p>cirAccountMode をセットします。
     * @param cirAccountMode cirAccountMode
     */
    public void setCirAccountMode(int cirAccountMode) {
        cirAccountMode__ = cirAccountMode;
    }

    /**
     * <p>cirAccountSid を取得します。
     * @return cirAccountSid
     */
    public int getCirAccountSid() {
        return cirAccountSid__;
    }

    /**
     * <p>cirAccountSid をセットします。
     * @param cirAccountSid cirAccountSid
     */
    public void setCirAccountSid(int cirAccountSid) {
        cirAccountSid__ = cirAccountSid;
    }

    /**
     * <p>errorsList を取得します。
     * @return errorsList
     */
    public List<String> getErrorsList() {
        return errorsList__;
    }

    /**
     * <p>errorsList をセットします。
     * @param errorsList errorsList
     */
    public void setErrorsList(List<String> errorsList) {
        errorsList__ = errorsList;
    }

    /**
     * <p>cirCmdMode を取得します。
     * @return cirCmdMode
     */
    public int getCirCmdMode() {
        return cirCmdMode__;
    }

    /**
     * <p>cirCmdMode をセットします。
     * @param cirCmdMode cirCmdMode
     */
    public void setCirCmdMode(int cirCmdMode) {
        cirCmdMode__ = cirCmdMode;
    }

    /**
     * <p>cir010adminUser を取得します。
     * @return cir010adminUser
     */
    public boolean isCir010adminUser() {
        return cir010adminUser__;
    }

    /**
     * <p>cir010adminUser をセットします。
     * @param cir010adminUser cir010adminUser
     */
    public void setCir010adminUser(boolean cir010adminUser) {
        cir010adminUser__ = cir010adminUser;
    }

    /**
     * <p>cirViewUser を取得します。
     * @return cirViewUser
     */
    public int getCirViewUser() {
        return cirViewUser__;
    }

    /**
     * <p>cirViewUser をセットします。
     * @param cirViewUser cirViewUser
     */
    public void setCirViewUser(int cirViewUser) {
        cirViewUser__ = cirViewUser;
    }

    /**
     * <p>cir010AccountList を取得します。
     * @return cir010AccountList
     */
    public List<AccountDataModel> getCir010AccountList() {
        return cir010AccountList__;
    }

    /**
     * <p>cir010AccountList をセットします。
     * @param cir010AccountList cir010AccountList
     */
    public void setCir010AccountList(List<AccountDataModel> cir010AccountList) {
        cir010AccountList__ = cir010AccountList;
    }

    /**
     * <p>cir010AccountTheme を取得します。
     * @return cir010AccountTheme
     */
    public String getCir010AccountTheme() {
        return cir010AccountTheme__;
    }

    /**
     * <p>cir010AccountTheme をセットします。
     * @param cir010AccountTheme cir010AccountTheme
     */
    public void setCir010AccountTheme(String cir010AccountTheme) {
        cir010AccountTheme__ = cir010AccountTheme;
    }

    /**
     * <p>cirViewAccountName を取得します。
     * @return cirViewAccountName
     */
    public String getCirViewAccountName() {
        return cirViewAccountName__;
    }

    /**
     * <p>cirViewAccountName をセットします。
     * @param cirViewAccountName cirViewAccountName
     */
    public void setCirViewAccountName(String cirViewAccountName) {
        cirViewAccountName__ = cirViewAccountName;
    }

    /**
     * <p>cirEditInfSid を取得します。
     * @return cirEditInfSid
     */
    public String getCirEditInfSid() {
        return cirEditInfSid__;
    }

    /**
     * <p>cirEditInfSid をセットします。
     * @param cirEditInfSid cirEditInfSid
     */
    public void setCirEditInfSid(String cirEditInfSid) {
        cirEditInfSid__ = cirEditInfSid;
    }

    /**
     * <p>cirCreateFlg を取得します。
     * @return cirCreateFlg
     */
    public boolean isCirCreateFlg() {
        return cirCreateFlg__;
    }

    /**
     * <p>cirCreateFlg をセットします。
     * @param cirCreateFlg cirCreateFlg
     */
    public void setCirCreateFlg(boolean cirCreateFlg) {
        cirCreateFlg__ = cirCreateFlg;
    }

    /**
     * <p>cirViewAccountYuko を取得します。
     * @return cirViewAccountYuko
     */
    public int getCirViewAccountUko() {
        return cirViewAccountUko__;
    }

    /**
     * <p>cirViewAccountYuko をセットします。
     * @param cirViewAccountYuko cirViewAccountYuko
     */
    public void setCirViewAccountUko(int cirViewAccountYuko) {
        cirViewAccountUko__ = cirViewAccountYuko;
    }

    /**
     * <p>cir010LabelList を取得します。
     * @return cir010LabelList
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010LabelList__
     */
    public List<LabelDataModel> getCir010LabelList() {
        return cir010LabelList__;
    }

    /**
     * <p>cir010LabelList をセットします。
     * @param cir010LabelList cir010LabelList
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010LabelList__
     */
    public void setCir010LabelList(List<LabelDataModel> cir010LabelList) {
        cir010LabelList__ = cir010LabelList;
    }

    /**
     * <p>cir010addLabelType を取得します。
     * @return cir010addLabelType
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010addLabelType__
     */
    public int getCir010addLabelType() {
        return cir010addLabelType__;
    }

    /**
     * <p>cir010addLabelType をセットします。
     * @param cir010addLabelType cir010addLabelType
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010addLabelType__
     */
    public void setCir010addLabelType(int cir010addLabelType) {
        cir010addLabelType__ = cir010addLabelType;
    }

    /**
     * <p>cir010SelectLabelSid を取得します。
     * @return cir010SelectLabelSid
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010SelectLabelSid__
     */
    public int getCir010SelectLabelSid() {
        return cir010SelectLabelSid__;
    }

    /**
     * <p>cir010SelectLabelSid をセットします。
     * @param cir010SelectLabelSid cir010SelectLabelSid
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010SelectLabelSid__
     */
    public void setCir010SelectLabelSid(int cir010SelectLabelSid) {
        cir010SelectLabelSid__ = cir010SelectLabelSid;
    }

    /**
     * <p>cir010addLabel を取得します。
     * @return cir010addLabel
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010addLabel__
     */
    public int getCir010addLabel() {
        return cir010addLabel__;
    }

    /**
     * <p>cir010addLabel をセットします。
     * @param cir010addLabel cir010addLabel
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010addLabel__
     */
    public void setCir010addLabel(int cir010addLabel) {
        cir010addLabel__ = cir010addLabel;
    }

    /**
     * <p>cir010addLabelName を取得します。
     * @return cir010addLabelName
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010addLabelName__
     */
    public String getCir010addLabelName() {
        return cir010addLabelName__;
    }

    /**
     * <p>cir010addLabelName をセットします。
     * @param cir010addLabelName cir010addLabelName
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010addLabelName__
     */
    public void setCir010addLabelName(String cir010addLabelName) {
        cir010addLabelName__ = cir010addLabelName;
    }

    /**
     * <p>cir010delLabel を取得します。
     * @return cir010delLabel
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010delLabel__
     */
    public int getCir010delLabel() {
        return cir010delLabel__;
    }

    /**
     * <p>cir010delLabel をセットします。
     * @param cir010delLabel cir010delLabel
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010delLabel__
     */
    public void setCir010delLabel(int cir010delLabel) {
        cir010delLabel__ = cir010delLabel;
    }

    /**
     * <p>cir010TitleLabelName を取得します。
     * @return cir010TitleLabelName
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010TitleLabelName__
     */
    public String getCir010TitleLabelName() {
        return cir010TitleLabelName__;
    }

    /**
     * <p>cir010TitleLabelName をセットします。
     * @param cir010TitleLabelName cir010TitleLabelName
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010TitleLabelName__
     */
    public void setCir010TitleLabelName(String cir010TitleLabelName) {
        cir010TitleLabelName__ = cir010TitleLabelName;
    }

    /**
     * <p>cir010JusinMidokuCnt を取得します。
     * @return cir010JusinMidokuCnt
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010JusinMidokuCnt__
     */
    public int getCir010JusinMidokuCnt() {
        return cir010JusinMidokuCnt__;
    }

    /**
     * <p>cir010JusinMidokuCnt をセットします。
     * @param cir010JusinMidokuCnt cir010JusinMidokuCnt
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010JusinMidokuCnt__
     */
    public void setCir010JusinMidokuCnt(int cir010JusinMidokuCnt) {
        cir010JusinMidokuCnt__ = cir010JusinMidokuCnt;
    }

    /**
     * <p>cir010GomiMidokuCnt を取得します。
     * @return cir010GomiMidokuCnt
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010GomiMidokuCnt__
     */
    public int getCir010GomiMidokuCnt() {
        return cir010GomiMidokuCnt__;
    }

    /**
     * <p>cir010GomiMidokuCnt をセットします。
     * @param cir010GomiMidokuCnt cir010GomiMidokuCnt
     * @see jp.groupsession.v2.cir.cir010.Cir010Form#cir010GomiMidokuCnt__
     */
    public void setCir010GomiMidokuCnt(int cir010GomiMidokuCnt) {
        cir010GomiMidokuCnt__ = cir010GomiMidokuCnt;
    }



}
