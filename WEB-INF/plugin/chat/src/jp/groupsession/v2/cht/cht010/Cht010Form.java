package jp.groupsession.v2.cht.cht010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.ChatValidate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cht.cht010.ui.Cht010UserGroupSelector;
import jp.groupsession.v2.cht.model.ChatGroupInfModel;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.cht.model.ChatMessageModel;
import jp.groupsession.v2.cht.model.ChatMidokuModel;
import jp.groupsession.v2.cht.model.ChatUserInfModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cht.model.ChtGroupUserModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;

/**
 *
 * <br>[機  能] チャット一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht010Form extends AbstractGsForm implements ISelectorUseForm {

    /** 非同期処理モード　トークンエラー*/
    public static final int MODE_TOKEN_ERROR = -2;
    /** 非同期処理モード　エラー*/
    public static final int MODE_ERROR = -1;
    /** 非同期処理モード　完了のみ*/
    public static final int MODE_SUCCESS = 0;
    /** 非同期処理モード　 相手切り替え時 */
    public static final int MODE_PARTNER = 1;
    /** 非同期処理モード 　もっとみる*/
    public static final int MODE_MORE = 2;
    /** 非同期処理モード　送信*/
    public static final int MODE_SEND = 3;
    /** 非同期処理モード　お気に入り*/
    public static final int MODE_FAVORITE = 4;
    /** 非同期処理モード　メッセージ編集*/
    public static final int MODE_EDIT = 5;
    /** 非同期処理モード　スクロール*/
    public static final int MODE_SCROLL = 6;
    /** 非同期処理モード　グループ切り替え*/
    public static final int MODE_CHANGE_GROUP = 7;
    /** 非同期処理モード　グループ名取得*/
    public static final int MODE_GET_GROUP = 8;
    /** 非同期処理モード　メッセージ削除*/
    public static final int MODE_DELETE = 9;

    /** 非同期処理モード:グループ管理　グループが存在しない*/
    public static final int MODE_GRPCONF_GRP_NOTEXIST = -2;
    /** 非同期処理モード:グループ管理　エラー表示*/
    public static final int MODE_GRPCONF_ERROR = -1;
    /** 非同期処理モード:グループ管理　初期表示*/
    public static final int MODE_GRPCONF_INIT = 1;
    /** 非同期処理モード:グループ管理　登録編集表示*/
    public static final int MODE_GRPCONF_ADDEDIT = 2;
    /** 非同期処理モード:グループ管理　登録編集時メンバー変更*/
    public static final int MODE_GRPCONF_MEMBERCHANGE = 3;
    /** 非同期処理モード:グループ管理　編集完了*/
    public static final int MODE_GRPCONF_UPDATE = 4;
    /** 非同期処理モード:グループ管理　削除完了*/
    public static final int MODE_GRPCONF_DELETE = 5;


    /** 添付モード ダイアログ表示*/
    public static final int TMP_MODE_DIALOG = 1;
    /** 添付モード ドラッグ*/
    public static final int TMP_MODE_DRAG = 2;


    /** 管理者フラグ*/
    private int adminFlg__ = GSConst.USER_NOT_ADMIN;
    /** 選択チャット情報*/
    private ChatInformationModel cht010ChtInfMdl__;

    /** 編集表示用ユーザSID*/
    private int cht010EditUsrSid__;

    /** お気に入りグループ*/
    private ArrayList<ChatGroupInfModel> cht010FavoriteGroup__ = new ArrayList<ChatGroupInfModel>();
    /** お気に入りユーザ*/
    private ArrayList<ChatUserInfModel> cht010FavoriteUser__ = new ArrayList<ChatUserInfModel>();
    /** お気に入りフラグ*/
    private int cht010FavoriteFlg__;

    /** グループリスト*/
    private ArrayList<ChatGroupInfModel> cht010GroupList__;

    /** グループ一覧 */
    private List<CmnLabelValueModel> cht010ComboGroupList__ = null;
    /** ユーザ一覧 */
    private List<ChatUserInfModel> cht010UserList__ = null;
    /** コンボグループSID */
    private String cht010GroupSid__ = null;

    /** メッセージリスト*/
    private List<ChatMessageModel> cht010MessageList__ = null;

    /** 未読件数*/
    private int cht010MidokuCount__ = 0;
    /** 未読リスト*/
    private List<ChatMidokuModel> cht010MidokuList__;
    /** 未読のみ表示フラグ*/
    private int cht010TimelineDspOnlyNoRead__ = 0;

    /** メッセージエリア表示区分*/
    private int cht010MessageAreaDisp__ = 0;

    /** Enter送信フラグ*/
    private int cht010EnterSendFlg__ = 0;

    /** チャット相手選択SID*/
    private int cht010SelectPartner__;
    /** チャット相手区分 1:ユーザ 2:グループ*/
    private int cht010SelectKbn__;

    /** 左メニュー選択タブ*/
    private int cht010SelectTab__;

    /** メッセージ内容*/
    private String cht010Message__;

    /** もっとみるボタン表示区分*/
    private int cht010MoreView__;
    /** 未読最終日時*/
    private String cht010MidokuLastDate__;

    /** 選択メッセージSID*/
    private long cht010MessageSid__;
    /** 選択添付ファイルSID*/
    private Long cht010BinSid__;
    /** 一括ダウンロード選択バイナリSID カンマ区切りでつなぐ*/
    private String cht010AllTempSid__;

    /** メッセージエリア内最大or最小メッセージSID*/
    private Long cht010MessageMaxMinSid__;
    /** 読み込みフラグ*/
    private int cht010ReadFlg__;


    /** グループ管理：選択グループSID*/
    private int cht010GrpConfCgiSid__ = -1;
    /** グループ管理：アーカイブ表示区分*/
    private int cht010GrpConfDspArcKbn__ = GSConstChat.CHAT_ARCHIVE_NOT_MODE;
    /** グループ管理：検索*/
    private String cht010GrpConfSearchKeyword__ = null;
    /** グループ管理：グループ全て表示区分*/
    private int cht010GrpConfAllDspKbn__ = GSConstChat.CHAT_CONF_DSP_ADMIN_GROUP;
    /** グループ管理：チャットグループ一覧*/
    private List<ChtGroupInfModel> cht010GrpConfGroupList__ = null;

    /** グループ管理：チャットグループＩＤ */
    private String cht010GrpConfGroupId__ = null;
    /** グループ管理：グループ名 */
    private String cht010GrpConfGroupName__ = null;
    /** グループ管理：カテゴリ */
    private int cht010GrpConfCategory__ = -1;

    ///---- グループ管理 メンバー ----
    /** グループコンボ選択 */
    private int cht010GrpConfMemberGroupSid__ = -9;
    /** 管理者メンバーSID */
    private String[] cht010GrpConfMemberAdminSid__ = new String[0];
    /** 一般メンバーSID */
    private String[] cht010GrpConfMemberGeneralSid__ = new String[0];
    /** グループ管理 メンバー UI */
    private Cht010UserGroupSelector cht010GrpConfMemberUI__ =
            Cht010UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.member", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cht.03", null))
                        .chainParameterName(
                                "cht010GrpConfMemberAdminSid")
                    )
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cht.04", null))
                        .chainParameterName(
                                "cht010GrpConfMemberGeneralSid")
                    )
                .chainGroupSelectionParamName("cht010GrpConfMemberGroupSid")
                .build();
    ///---- グループ管理 メンバー ----

    /** グループ管理：備考 */
    private String cht010GrpConfBiko__ = null;
    /** グループ管理：状態区分 */
    private int cht010GrpConfArchiveKbn__ = GSConstChat.CHAT_ARCHIVE_NOT_MODE;
    /** グループ管理：カテゴリリスト*/
    private List <LabelValueBean> cht010GrpConfCategoryList__ = null;
    /** グループ管理：登録編集区分*/
    private int cht010GrpConfProcMode__ = GSConstChat.CHAT_MODE_ADD;
    /** グループ管理：グループ表示件数 */
    private int cht010GrpConfDspGrpNum__ = 10;
    /** 初期投稿日時取得*/
    private String cht010FirstEntryDay__ = null;

    /** リアルタイム通信用パラメータ 登録または編集されたチャットグループのパラメータ */
    /** メンバーユーザSID */
    private String[] cht010MemberUserSids__ = null;
    /** 編集前のメンバーSID */
    private String[] cht010OldMemberSids__ = null;
    /** リアルタイム通信実行フラグ */
    private int cht010CRealTimeFlg__ = GSConstChat.REAL_TIME_NO;
    /** グループ投稿数 */
    private int cht010MessageCount__ = -1;
    /** 最新投稿日時 */
    private String cht010MessageLastDate__ = null;

    /** グループ編集権限フラグ*/
    private int cht010GroupEditFlg__ = 0;

    /** 全件表示フラグ*/
    private int cht010AllDispFlg__ = 0;

    /** 分割送信時初回送信フラグ*/
    private int cht010SplitMessageFirstFlg__ = 0;

    /** メイン画面遷移フラグ */
    private int cht010FromMain__ = GSConstChat.FROM_NOT_MAIN;

    /** 初期表示フラグ */
    private int cht010InitFlg__ = GSConstChat.DSP_FIRST;

    /** 既読メッセージSID */
    private String cht010KidokuMessageSids__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSendText(
            RequestModel reqMdl, Connection con, Cht010Form form)
                    throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // メッセージ内容
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cmn.message"),
                cht010Message__,
                "message",
                GSConstChat.MAX_LENGTH_MESSAGE,
                true);
        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSendAllText(
            RequestModel reqMdl, Connection con, Cht010Form form)
                    throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // メッセージ内容
        errors = ChatValidate.validateChtAllFieldText(
                errors,
                gsMsg.getMessage("cmn.message"),
                cht010Message__,
                "message",
                true);
        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param adminFlg システム管理者 or プラグイン管理者
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGrpConfSearch(
            RequestModel reqMdl,
            Connection con,
            boolean adminFlg)
                    throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // キーワード検索
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cmn.keyword"),
                cht010GrpConfSearchKeyword__,
                "keyword",
                GSConstChat.MAX_LENGTH_GROUPNAME,
                false);
        //　全て表示
        if (cht010GrpConfAllDspKbn__ != GSConstChat.CHAT_CONF_DSP_ADMIN_GROUP
                && cht010GrpConfAllDspKbn__ != GSConstChat.CHAT_CONF_DSP_ALL) {
            ActionMessage msg =  new ActionMessage("error.input.format.file",
                    gsMsg.getMessage("cht.cht010.31"),
                    gsMsg.getMessage("main.man340.10"));
            String eprefix = "allDsp";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        // アーカイブ
        if (cht010GrpConfDspArcKbn__ != GSConstChat.CHAT_ARCHIVE_NOT_MODE
                && cht010GrpConfDspArcKbn__ != GSConstChat.CHAT_ARCHIVE_MODE) {
            ActionMessage msg =  new ActionMessage("error.input.format.file",
                    gsMsg.getMessage("cht.cht110.03"),
                    gsMsg.getMessage("main.man340.10"));
            String eprefix = "compFlg";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
     * <br>[機  能] 登録編集削除時に使用する検索条件要素の入力チェックを行う
     * <br>[解  説] 各要素に対して入力エラーがある要素は、反映させない
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param adminFlg システム管理者 or プラグイン管理者
     * @throws SQLException SQL実行時例外
     */
    public void chkGrpConfSearch(
            RequestModel reqMdl,
            Connection con,
            boolean adminFlg)
                    throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // キーワード検索
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cmn.keyword"),
                cht010GrpConfSearchKeyword__,
                "keyword",
                GSConstChat.MAX_LENGTH_GROUPNAME,
                false);
        if (!errors.isEmpty()) {
            this.setCht010GrpConfSearchKeyword("");
        }
        //　全て表示
        if (cht010GrpConfAllDspKbn__ != GSConstChat.CHAT_CONF_DSP_ADMIN_GROUP
                && cht010GrpConfAllDspKbn__ != GSConstChat.CHAT_CONF_DSP_ALL) {
            this.setCht010GrpConfAllDspKbn(GSConstChat.CHAT_CONF_DSP_ADMIN_GROUP);
        }
        // アーカイブ
        if (cht010GrpConfDspArcKbn__ != GSConstChat.CHAT_ARCHIVE_NOT_MODE
                && cht010GrpConfDspArcKbn__ != GSConstChat.CHAT_ARCHIVE_MODE) {
            this.setCht010GrpConfArchiveKbn(GSConstChat.CHAT_ARCHIVE_NOT_MODE);
        }
    }





    /**
     * <br>[機  能] グループ管理不正チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param adminFlg システム管理者 or プラグイン管理者
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGroupConfLimitAddEdit(
            RequestModel reqMdl, Connection con, int usrSid, boolean adminFlg) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        // 不正確認
        if (cht010GrpConfProcMode__ < GSConstChat.CHAT_MODE_ADD
                || cht010GrpConfProcMode__ > GSConstChat.CHAT_MODE_EDIT) {
            ActionMessage msg
            =  new ActionMessage("error.access.double.submit");
            String eprefix = "error";
            StrutsUtil.addMessage(errors, msg, eprefix);
            return errors;
        }

        // 登録時グループ作成権限判定
        if (cht010GrpConfProcMode__ == GSConstChat.CHAT_MODE_ADD) {
            ChtBiz biz = new ChtBiz(con);
            if (!biz.isCreateChtGroup(usrSid, adminFlg)) {
                ActionMessage msg
                =  new ActionMessage("error.cant.create.group");
                String eprefix = "notCreate";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else {
         // 編集選択時グループ表示判定
            // グループが存在するか
            if (!ChatValidate.validateIsExitChatGroup(cht010GrpConfCgiSid__, con)) {
                ActionMessage msg
                =  new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cht.01") + gsMsg.getMessage("cmn.group"));
                String eprefix = "notGroup";
                StrutsUtil.addMessage(errors, msg, eprefix);
                return errors;
            }
            // グループの編集権限を持っているか
            if (!adminFlg
                    && !ChatValidate.validateLimitChtGrp(cht010GrpConfCgiSid__, usrSid, con)) {
                ActionMessage msg
                =  new ActionMessage("error.edit.power.user",
                        gsMsg.getMessage("cmn.edit"), gsMsg.getMessage("cmn.edit"));
                String eprefix = "notGroup";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] グループ削除チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param adminFlg システム管理者 or プラグイン管理者
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGroupConfDelete(
            RequestModel reqMdl, Connection con, int usrSid, boolean adminFlg) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // グループが存在するか
        if (!ChatValidate.validateIsExitChatGroup(cht010GrpConfCgiSid__, con)) {
            ActionMessage msg
            =  new ActionMessage("search.data.notfound",
                    gsMsg.getMessage("cht.01") + gsMsg.getMessage("cmn.group"));
            String eprefix = "notGroup";
            StrutsUtil.addMessage(errors, msg, eprefix);
            return errors;
        }
        // グループの編集権限を持っているか
        if (!adminFlg
                && !ChatValidate.validateLimitChtGrp(cht010GrpConfCgiSid__, usrSid, con)) {
            ActionMessage msg
            =  new ActionMessage("error.edit.power.user",
                    gsMsg.getMessage("cmn.edit"), gsMsg.getMessage("cmn.edit"));
            String eprefix = "notGroup";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
     * <br>[機  能] グループ管理登録編集入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param adminFlg システム管理者 or　プラグイン管理者確認
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGroupConf(
            RequestModel reqMdl, Connection con,
            int usrSid, boolean adminFlg) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // チャットグループＩＤ
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("main.src.man220.6"),
                cht010GrpConfGroupId__,
                "groupId",
                GSConstChat.MAX_LENGTH_GROUPID,
                true);

        errors = ChatValidate.validateIsExitGroupId(
                errors,
                cht010GrpConfCgiSid__,
                gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("main.src.man220.6"),
                cht010GrpConfGroupId__,
                "groupId",
                con);

        // グループ名
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cmn.group.name"),
                cht010GrpConfGroupName__,
                "groupName",
                GSConstChat.MAX_LENGTH_GROUPNAME,
                true);

        // カテゴリ
        if (adminFlg && cht010GrpConfProcMode__ == GSConstChat.CHAT_MODE_EDIT) {
        errors = ChatValidate.validateIsExitCategory(
                errors,
                gsMsg.getMessage("user.47"),
                cht010GrpConfCategory__,
                "category",
                con,
                true);
        }

        // メンバー
        boolean notErrorFlg = true;
        ChtMemberBiz memberBiz = new ChtMemberBiz(con);
        List<Integer> tokureiGroups = memberBiz.getTokureiGroup(usrSid);
        List<Integer> tokureiUsers = memberBiz.getTokureiUser(usrSid, tokureiGroups);
        List<Integer> allTokureiGroups = memberBiz.getTokureiUserBelong(usrSid, tokureiUsers);
        List<ChtGroupUserModel> oldMembers = memberBiz.getOldMember(cht010GrpConfCgiSid__);
        List<ChtGroupUserModel> tokureiOldAdminMembers = memberBiz.getTokureiOldMember(
                oldMembers,
                allTokureiGroups,
                tokureiUsers,
                1);

        List<ChtGroupUserModel> tokureiOldGeneralMembers = memberBiz.getTokureiOldMember(
                oldMembers,
                allTokureiGroups,
                tokureiUsers,
                0); // メッセージ
        String adminUser = gsMsg.getMessage("cht.03");
        String generalUser = gsMsg.getMessage("cht.04");
        String proc = gsMsg.getMessage("cmn.entry");
        if (cht010GrpConfProcMode__ == GSConstChat.CHAT_MODE_EDIT) {
            proc = gsMsg.getMessage("cmn.edit");
        }

        //管理者
        String[] adminList = cht010GrpConfMemberAdminSid__;
        if (adminList == null || adminList.length == 0) {
            // 選択されていない場合 → エラーメッセージ
            ActionMessage msg =  new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("cht.03"));
            String eprefix = "adminUser";
            StrutsUtil.addMessage(errors, msg, eprefix);
        } else {
            // 選択されている場合
            for (String sid:adminList) {
                // グループ・ユーザが存在するかチェック
                notErrorFlg = __existGrpUser(con, sid);
                if (!notErrorFlg) {
                    ActionMessage msg
                    =  new ActionMessage("search.data.notfound", adminUser);
                    String eprefix = "existAdminUser";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
                // 特例アクセス対象かチェック
                notErrorFlg = __isTokureiMember(
                         sid,
                         usrSid,
                         allTokureiGroups,
                         tokureiUsers,
                         tokureiOldAdminMembers);
                if (!notErrorFlg) {

                    ActionMessage msg
                    =  new ActionMessage("error.chat.exist.tokureimember",
                            adminUser,
                            proc);
                    String eprefix = "limitTokureiAdminMember";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
            }

            // 特例アクセス対象が消されていないか
            if (!__notDeleteTokureiMember(
                    usrSid,
                    tokureiOldAdminMembers,
                    adminList
                    )) {
                    ActionMessage msg
                    =  new ActionMessage("error.chat.exist.tokureimember",
                            adminUser,
                            proc);
                    String eprefix = "deleteTokureiAdminMember";
                    StrutsUtil.addMessage(errors, msg, eprefix);
            }
        }

      //一般
        String[] generalList = cht010GrpConfMemberGeneralSid__;
        if (generalList == null) {
            generalList = new String[0];
        }
        // 特例アクセス対象が消されていないか
        if (!__notDeleteTokureiMember(
                usrSid,
                tokureiOldGeneralMembers,
                generalList
                )) {
                ActionMessage msg
                =  new ActionMessage("error.chat.exist.tokureimember",
                        generalUser,
                        proc);
                String eprefix = "deleteTokureiGeneralUser";
                StrutsUtil.addMessage(errors, msg, eprefix);
        }
        if (generalList != null && generalList.length > 0) {
            notErrorFlg = true;
            for (String sid:generalList) {
                // グループ・ユーザが存在するかチェック
                notErrorFlg = __existGrpUser(con, sid);
                if (!notErrorFlg) {

                    ActionMessage msg
                    =  new ActionMessage("search.data.notfound", generalUser);
                    String eprefix = "existGeneralUser";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
                // 特例アクセス対象かチェック
                notErrorFlg = __isTokureiMember(
                         sid,
                         usrSid,
                         allTokureiGroups,
                         tokureiUsers,
                         tokureiOldGeneralMembers);
                if (!notErrorFlg) {
                    ActionMessage msg
                    =  new ActionMessage("error.chat.exist.tokureimember",
                            generalUser,
                            proc);
                    String eprefix = "limitTokureiGeneralUser";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
            }

        }


        //備考
        errors = ChatValidate.validateTextarea(
                errors,
                cht010GrpConfBiko__,
                gsMsg.getMessage("cmn.memo"),
                GSConstChat.MAX_LENGTH_BIKO,
                false);
        //アーカイブ
        if (cht010GrpConfArchiveKbn__ != GSConstChat.CHAT_ARCHIVE_MODE
                && cht010GrpConfArchiveKbn__ != GSConstChat.CHAT_ARCHIVE_NOT_MODE) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.cht010.03"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "compFlg";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
    *
    * <br>[機  能]グループ・ユーザの存在を判定
    * <br>[解  説]
    * <br>[備  考]
    *@param con コネクション
    *@param  strSid 判定するSID
    *@return 存在可否(true:存在する)
    *@throws SQLException SQL実行時例外
    */
   private boolean __existGrpUser(Connection con,
           String strSid) throws SQLException {
       if (strSid.startsWith(GSConstChat.GROUP_HEADSTR)) {
           // グループ存在判定
           int sid = NullDefault.getInt(strSid.substring(1), -1);
           if (GSValidateUser.existGroup(sid, con)) {
               return true;
           }
       } else {
           // ユーザ存在判定
           CmnUsrmDao cuDao = new CmnUsrmDao(con);
           int sid = NullDefault.getInt(strSid, -1);
           if (cuDao.isExistUser(sid)) {
              return true;
           }
       }
       return false;
   }

   /**
   *
   * <br>[機  能]特例アクセス対象か判定
   * <br>[解  説]
   * <br>[備  考]
   *@param  strSid 判定するSID
   *@param usrSid ユーザSID
   *@param tokureiGrp 特例グループ
   *@param tokureiUsr 特例ユーザ
   *@param oldMember 過去メンバー
   *@return エラー対象(true:問題なし)
   */

  private boolean __isTokureiMember(
          String strSid,
          int usrSid,
          List<Integer> tokureiGrp,
          List<Integer> tokureiUsr,
          List<ChtGroupUserModel> oldMember) {
      if (strSid.startsWith(GSConstChat.GROUP_HEADSTR)) {
          if (tokureiGrp.isEmpty()) {
              return true;
          }
          int sid = NullDefault.getInt(strSid.substring(1), -1);
          if (tokureiGrp.contains(sid)) {
               if (cht010GrpConfProcMode__ == GSConstChat.CHAT_MODE_ADD) {
                   return false;
               }
               // 特例アクセス対象だが、元々メンバーとして登録していたら問題なし
               for (ChtGroupUserModel mdl:  oldMember) {
                   if (mdl.getCguKbn() == GSConstChat.CHAT_KBN_USER) {
                       continue;
                   }
                   if (sid == mdl.getCguSelectSid()) {
                       return true;
                   }
               }
          } else {
              return true;
          }
      } else {
          if (tokureiUsr.isEmpty()) {
              return true;
          }
          int sid = NullDefault.getInt(strSid, -1);
          if (tokureiUsr.contains(sid) && sid != usrSid) {
              if (cht010GrpConfProcMode__ == GSConstChat.CHAT_MODE_ADD) {
                  return false;
              }
              // 特例アクセス対象だが、元々メンバーとして登録していたら問題なし
              for (ChtGroupUserModel mdl:  oldMember) {
                  if (mdl.getCguKbn() == GSConstChat.CHAT_KBN_GROUP) {
                      continue;
                  }
                  if (sid == mdl.getCguSelectSid()) {
                      return true;
                  }
              }
          } else {
              return true;
          }
      }
      return false;
  }

  /**
  *
  * <br>[機  能]特例アクセス対象か判定
  * <br>[解  説]
  * <br>[備  考]
  *@param usrSid ユーザSID
  *@param nowMembers 選択グループ
  *@param tokureiOldGeneralMembers 過去メンバー
  *@return エラー対象(true:問題なし)
  */

 private boolean __notDeleteTokureiMember(
         int usrSid,
         List<ChtGroupUserModel> tokureiOldGeneralMembers,
         String[] nowMembers
         ) {
         if (cht010GrpConfProcMode__ == GSConstChat.CHAT_MODE_ADD) {
             return true;
         }
         for (ChtGroupUserModel member : tokureiOldGeneralMembers) {
             boolean notDelete = false;
             int sid = member.getCguSelectSid();
             int kbn = member.getCguKbn();
             if (kbn == GSConstChat.CHAT_KBN_GROUP) {
                 String strSid = GSConstChat.GROUP_HEADSTR + String.valueOf(sid);
                 for (String str : nowMembers) {
                     if (str.equals(strSid)) {
                         notDelete = true;
                     }
                 }
             } else {
                 String strSid = String.valueOf(sid);
                 for (String str : nowMembers) {
                     if (str.equals(strSid) || strSid.equals(String.valueOf(usrSid))) {
                         notDelete = true;
                     }
                 }
             }
                 if (!notDelete) {
                     return false;
                 }
             }
          return true;
         }


    /**
     * <p>cht010SelectGroup を取得します。
     * @return cht010SelectGroup
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SelectPartner__
     */
    public int getCht010SelectPartner() {
        return cht010SelectPartner__;
    }

    /**
     * <p>cht010SelectGroup をセットします。
     * @param cht010SelectPartner cht010SelectPartner
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SelectPartner__
     */
    public void setCht010SelectPartner(int cht010SelectPartner) {
        cht010SelectPartner__ = cht010SelectPartner;
    }

    /**
     * <p>cht010Message を取得します。
     * @return cht010Message
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010Message__
     */
    public String getCht010Message() {
        return cht010Message__;
    }

    /**
     * <p>cht010Message をセットします。
     * @param cht010Message cht010Message
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010Message__
     */
    public void setCht010Message(String cht010Message) {
        cht010Message__ = cht010Message;
    }

    /**
     * <p>adminFlg を取得します。
     * @return adminFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#adminFlg__
     */
    public int getAdminFlg() {
        return adminFlg__;
    }

    /**
     * <p>adminFlg をセットします。
     * @param adminFlg adminFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#adminFlg__
     */
    public void setAdminFlg(int adminFlg) {
        adminFlg__ = adminFlg;
    }

    /**
     * <p>cht010ChtInfMdl を取得します。
     * @return cht010ChtInfMdl
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010ChtInfMdl__
     */
    public ChatInformationModel getCht010ChtInfMdl() {
        return cht010ChtInfMdl__;
    }

    /**
     * <p>cht010ChtInfMdl をセットします。
     * @param cht010ChtInfMdl cht010ChtInfMdl
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010ChtInfMdl__
     */
    public void setCht010ChtInfMdl(ChatInformationModel cht010ChtInfMdl) {
        cht010ChtInfMdl__ = cht010ChtInfMdl;
    }

    /**
     * <p>cht010FavoriteGroup を取得します。
     * @return cht010FavoriteGroup
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FavoriteGroup__
     */
    public ArrayList<ChatGroupInfModel> getCht010FavoriteGroup() {
        return cht010FavoriteGroup__;
    }

    /**
     * <p>cht010FavoriteGroup をセットします。
     * @param cht010FavoriteGroup cht010FavoriteGroup
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FavoriteGroup__
     */
    public void setCht010FavoriteGroup(
            ArrayList<ChatGroupInfModel> cht010FavoriteGroup) {
        cht010FavoriteGroup__ = cht010FavoriteGroup;
    }

    /**
     * <p>cht010FavoriteUser を取得します。
     * @return cht010FavoriteUser
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FavoriteUser__
     */
    public ArrayList<ChatUserInfModel> getCht010FavoriteUser() {
        return cht010FavoriteUser__;
    }

    /**
     * <p>cht010FavoriteUser をセットします。
     * @param cht010FavoriteUser cht010FavoriteUser
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FavoriteUser__
     */
    public void setCht010FavoriteUser(
            ArrayList<ChatUserInfModel> cht010FavoriteUser) {
        cht010FavoriteUser__ = cht010FavoriteUser;
    }

    /**
     * <p>cht010GroupList を取得します。
     * @return cht010GroupList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GroupList__
     */
    public ArrayList<ChatGroupInfModel> getCht010GroupList() {
        return cht010GroupList__;
    }

    /**
     * <p>cht010GroupList をセットします。
     * @param cht010GroupList cht010GroupList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GroupList__
     */
    public void setCht010GroupList(ArrayList<ChatGroupInfModel> cht010GroupList) {
        cht010GroupList__ = cht010GroupList;
    }

    /**
     * <p>cht010ComboGroupList を取得します。
     * @return cht010ComboGroupList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010ComboGroupList__
     */
    public List<CmnLabelValueModel> getCht010ComboGroupList() {
        return cht010ComboGroupList__;
    }

    /**
     * <p>cht010ComboGroupList をセットします。
     * @param cht010ComboGroupList cht010ComboGroupList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010ComboGroupList__
     */
    public void setCht010ComboGroupList(
            List<CmnLabelValueModel> cht010ComboGroupList) {
        cht010ComboGroupList__ = cht010ComboGroupList;
    }

    /**
     * <p>cht010userList を取得します。
     * @return cht010userList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010userList__
     */
    public List<ChatUserInfModel> getCht010UserList() {
        return cht010UserList__;
    }

    /**
     * <p>cht010userList をセットします。
     * @param cht010UserList cht010UserList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010userList__
     */
    public void setCht010UserList(List<ChatUserInfModel> cht010UserList) {
        cht010UserList__ = cht010UserList;
    }

    /**
     * <p>cht010GroupSid を取得します。
     * @return cht010GroupSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GroupSid__
     */
    public String getCht010GroupSid() {
        return cht010GroupSid__;
    }

    /**
     * <p>cht010GroupSid をセットします。
     * @param cht010GroupSid cht010GroupSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GroupSid__
     */
    public void setCht010GroupSid(String cht010GroupSid) {
        cht010GroupSid__ = cht010GroupSid;
    }

     /**
     * <p>cht010MassageList を取得します。
     * @return cht010MassageList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageList__
     */
    public List<ChatMessageModel> getCht010MessageList() {
        return cht010MessageList__;
    }

    /**
     * <p>cht010MassageList をセットします。
     * @param cht010MassageList cht010MassageList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageList__
     */
    public void setCht010MessageList(List<ChatMessageModel> cht010MassageList) {
        cht010MessageList__ = cht010MassageList;
    }

    /**
     * <p>cht010EditUsrSid を取得します。
     * @return cht010EditUsrSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010EditUsrSid__
     */
    public int getCht010EditUsrSid() {
        return cht010EditUsrSid__;
    }

    /**
     * <p>cht010EditUsrSid をセットします。
     * @param cht010EditUsrSid cht010EditUsrSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010EditUsrSid__
     */
    public void setCht010EditUsrSid(int cht010EditUsrSid) {
        cht010EditUsrSid__ = cht010EditUsrSid;
    }

    /**
     * <p>cht010MidokuCount を取得します。
     * @return cht010MidokuCount
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MidokuCount__
     */
    public int getCht010MidokuCount() {
        return cht010MidokuCount__;
    }

    /**
     * <p>cht010MidokuCount をセットします。
     * @param cht010MidokuCount cht010MidokuCount
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MidokuCount__
     */
    public void setCht010MidokuCount(int cht010MidokuCount) {
        cht010MidokuCount__ = cht010MidokuCount;
    }

    /**
     * <p>cht010MessageAreaDisp を取得します。
     * @return cht010MessageAreaDisp
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageAreaDisp__
     */
    public int getCht010MessageAreaDisp() {
        return cht010MessageAreaDisp__;
    }

    /**
     * <p>cht010MessageAreaDisp をセットします。
     * @param cht010MessageAreaDisp cht010MessageAreaDisp
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageAreaDisp__
     */
    public void setCht010MessageAreaDisp(int cht010MessageAreaDisp) {
        cht010MessageAreaDisp__ = cht010MessageAreaDisp;
    }

    /**
     * <p>cht010EnterSendFlg を取得します。
     * @return cht010EnterSendFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010EnterSendFlg__
     */
    public int getCht010EnterSendFlg() {
        return cht010EnterSendFlg__;
    }

    /**
     * <p>cht010EnterSendFlg をセットします。
     * @param cht010EnterSendFlg cht010EnterSendFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010EnterSendFlg__
     */
    public void setCht010EnterSendFlg(int cht010EnterSendFlg) {
        cht010EnterSendFlg__ = cht010EnterSendFlg;
    }

    /**
     * <p>cht010MidokuList を取得します。
     * @return cht010MidokuList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MidokuList__
     */
    public List<ChatMidokuModel> getCht010MidokuList() {
        return cht010MidokuList__;
    }

    /**
     * <p>cht010MidokuList をセットします。
     * @param cht010MidokuList cht010MidokuList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MidokuList__
     */
    public void setCht010MidokuList(List<ChatMidokuModel> cht010MidokuList) {
        cht010MidokuList__ = cht010MidokuList;
    }

    /**
     * <p>cht010TimelineDspOnlyNoRead を取得します。
     * @return cht010TimelineDspOnlyNoRead
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010TimelineDspOnlyNoRead__
     */
    public int getCht010TimelineDspOnlyNoRead() {
        return cht010TimelineDspOnlyNoRead__;
    }

    /**
     * <p>cht010TimelineDspOnlyNoRead をセットします。
     * @param cht010TimelineDspOnlyNoRead cht010TimelineDspOnlyNoRead
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010TimelineDspOnlyNoRead__
     */
    public void setCht010TimelineDspOnlyNoRead(
            int cht010TimelineDspOnlyNoRead) {
        cht010TimelineDspOnlyNoRead__ = cht010TimelineDspOnlyNoRead;
    }

    /**
     * <p>cht010SelectKbn を取得します。
     * @return cht010SelectKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SelectKbn__
     */
    public int getCht010SelectKbn() {
        return cht010SelectKbn__;
    }

    /**
     * <p>cht010SelectKbn をセットします。
     * @param cht010SelectKbn cht010SelectKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SelectKbn__
     */
    public void setCht010SelectKbn(int cht010SelectKbn) {
        cht010SelectKbn__ = cht010SelectKbn;
    }

    /**
     * <p>cht010SelectTab を取得します。
     * @return cht010SelectTab
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SelectTab__
     */
    public int getCht010SelectTab() {
        return cht010SelectTab__;
    }

    /**
     * <p>cht010SelectTab をセットします。
     * @param cht010SelectTab cht010SelectTab
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SelectTab__
     */
    public void setCht010SelectTab(int cht010SelectTab) {
        cht010SelectTab__ = cht010SelectTab;
    }

    /**
     * <p>cht010FavoriteFlg を取得します。
     * @return cht010FavoriteFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FavoriteFlg__
     */
    public int getCht010FavoriteFlg() {
        return cht010FavoriteFlg__;
    }

    /**
     * <p>cht010FavoriteFlg をセットします。
     * @param cht010FavoriteFlg cht010FavoriteFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FavoriteFlg__
     */
    public void setCht010FavoriteFlg(int cht010FavoriteFlg) {
        cht010FavoriteFlg__ = cht010FavoriteFlg;
    }

    /**
     * <p>cht010MoreView を取得します。
     * @return cht010MoreView
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MoreView__
     */
    public int getCht010MoreView() {
        return cht010MoreView__;
    }

    /**
     * <p>cht010MoreView をセットします。
     * @param cht010MoreView cht010MoreView
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MoreView__
     */
    public void setCht010MoreView(int cht010MoreView) {
        cht010MoreView__ = cht010MoreView;
    }

    /**
     * <p>cht010MidokuLastDate を取得します。
     * @return cht010MidokuLastDate
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MidokuLastDate__
     */
    public String getCht010MidokuLastDate() {
        return cht010MidokuLastDate__;
    }

    /**
     * <p>cht010MidokuLastDate をセットします。
     * @param cht010MidokuLastDate cht010MidokuLastDate
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MidokuLastDate__
     */
    public void setCht010MidokuLastDate(String cht010MidokuLastDate) {
        cht010MidokuLastDate__ = cht010MidokuLastDate;
    }

    /**
     * <p>cht010MessageSid を取得します。
     * @return cht010MessageSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageSid__
     */
    public long getCht010MessageSid() {
        return cht010MessageSid__;
    }

    /**
     * <p>cht010MessageSid をセットします。
     * @param cht010MessageSid cht010MessageSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageSid__
     */
    public void setCht010MessageSid(long cht010MessageSid) {
        cht010MessageSid__ = cht010MessageSid;
    }

    /**
     * <p>cht010BinSid を取得します。
     * @return cht010BinSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010BinSid__
     */
    public Long getCht010BinSid() {
        return cht010BinSid__;
    }

    /**
     * <p>cht010BinSid をセットします。
     * @param cht010BinSid cht010BinSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010BinSid__
     */
    public void setCht010BinSid(Long cht010BinSid) {
        cht010BinSid__ = cht010BinSid;
    }
    /**
     * <p>cht010GrpConfCgiSid を取得します。
     * @return cht010GrpConfCgiSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfCgiSid__
     */
    public int getCht010GrpConfCgiSid() {
        return cht010GrpConfCgiSid__;
    }
    /**
     * <p>cht010GrpConfCgiSid をセットします。
     * @param cht010GrpConfCgiSid cht010GrpConfCgiSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfCgiSid__
     */
    public void setCht010GrpConfCgiSid(int cht010GrpConfCgiSid) {
        cht010GrpConfCgiSid__ = cht010GrpConfCgiSid;
    }

    /**
     * <p>cht010GrpConfSearchKeyword を取得します。
     * @return cht010GrpConfSearchKeyword
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfSearchKeyword__
     */
    public String getCht010GrpConfSearchKeyword() {
        return cht010GrpConfSearchKeyword__;
    }
    /**
     * <p>cht010GrpConfSearchKeyword をセットします。
     * @param cht010GrpConfSearchKeyword cht010GrpConfSearchKeyword
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfSearchKeyword__
     */
    public void setCht010GrpConfSearchKeyword(String cht010GrpConfSearchKeyword) {
        cht010GrpConfSearchKeyword__ = cht010GrpConfSearchKeyword;
    }
    /**
     * <p>cht010GrpConfAllDspKbn を取得します。
     * @return cht010GrpConfAllDspKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfAllDspKbn__
     */
    public int getCht010GrpConfAllDspKbn() {
        return cht010GrpConfAllDspKbn__;
    }
    /**
     * <p>cht010GrpConfAllDspKbn をセットします。
     * @param cht010GrpConfAllDspKbn cht010GrpConfAllDspKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfAllDspKbn__
     */
    public void setCht010GrpConfAllDspKbn(int cht010GrpConfAllDspKbn) {
        cht010GrpConfAllDspKbn__ = cht010GrpConfAllDspKbn;
    }
    /**
     * <p>cht010GrpConfGroupList を取得します。
     * @return cht010GrpConfGroupList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfGroupList__
     */
    public List<ChtGroupInfModel> getCht010GrpConfGroupList() {
        return cht010GrpConfGroupList__;
    }
    /**
     * <p>cht010GrpConfGroupList をセットします。
     * @param cht010GrpConfGroupList cht010GrpConfGroupList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfGroupList__
     */
    public void setCht010GrpConfGroupList(
            List<ChtGroupInfModel> cht010GrpConfGroupList) {
        cht010GrpConfGroupList__ = cht010GrpConfGroupList;
    }
    /**
     * <p>cht010GrpConfGroupId を取得します。
     * @return cht010GrpConfGroupId
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfGroupId__
     */
    public String getCht010GrpConfGroupId() {
        return cht010GrpConfGroupId__;
    }
    /**
     * <p>cht010GrpConfGroupId をセットします。
     * @param cht010GrpConfGroupId cht010GrpConfGroupId
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfGroupId__
     */
    public void setCht010GrpConfGroupId(String cht010GrpConfGroupId) {
        cht010GrpConfGroupId__ = cht010GrpConfGroupId;
    }
    /**
     * <p>cht010GrpConfGroupName を取得します。
     * @return cht010GrpConfGroupName
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfGroupName__
     */
    public String getCht010GrpConfGroupName() {
        return cht010GrpConfGroupName__;
    }
    /**
     * <p>cht010GrpConfGroupName をセットします。
     * @param cht010GrpConfGroupName cht010GrpConfGroupName
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfGroupName__
     */
    public void setCht010GrpConfGroupName(String cht010GrpConfGroupName) {
        cht010GrpConfGroupName__ = cht010GrpConfGroupName;
    }
    /**
     * <p>cht010GrpConfCategory を取得します。
     * @return cht010GrpConfCategory
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfCategory__
     */
    public int getCht010GrpConfCategory() {
        return cht010GrpConfCategory__;
    }
    /**
     * <p>cht010GrpConfCategory をセットします。
     * @param cht010GrpConfCategory cht010GrpConfCategory
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfCategory__
     */
    public void setCht010GrpConfCategory(int cht010GrpConfCategory) {
        cht010GrpConfCategory__ = cht010GrpConfCategory;
    }
    /**
     * <p>cht010GrpConfMemberGroupSid を取得します。
     * @return cht010GrpConfMemberGroupSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfMemberGroupSid__
     */
    public int getCht010GrpConfMemberGroupSid() {
        return cht010GrpConfMemberGroupSid__;
    }
    /**
     * <p>cht010GrpConfMemberGroupSid をセットします。
     * @param cht010GrpConfMemberGroupSid cht010GrpConfMemberGroupSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfMemberGroupSid__
     */
    public void setCht010GrpConfMemberGroupSid(int cht010GrpConfMemberGroupSid) {
        cht010GrpConfMemberGroupSid__ = cht010GrpConfMemberGroupSid;
    }
    /**
     * <p>cht010GrpConfMemberAdminSid を取得します。
     * @return cht010GrpConfMemberAdminSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfMemberAdminSid__
     */
    public String[] getCht010GrpConfMemberAdminSid() {
        return cht010GrpConfMemberAdminSid__;
    }
    /**
     * <p>cht010GrpConfMemberAdminSid をセットします。
     * @param cht010GrpConfMemberAdminSid cht010GrpConfMemberAdminSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfMemberAdminSid__
     */
    public void setCht010GrpConfMemberAdminSid(
            String[] cht010GrpConfMemberAdminSid) {
        cht010GrpConfMemberAdminSid__ = cht010GrpConfMemberAdminSid;
    }
    /**
     * <p>cht010GrpConfMemberGeneralSid を取得します。
     * @return cht010GrpConfMemberGeneralSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfMemberGeneralSid__
     */
    public String[] getCht010GrpConfMemberGeneralSid() {
        return cht010GrpConfMemberGeneralSid__;
    }
    /**
     * <p>cht010GrpConfMemberGeneralSid をセットします。
     * @param cht010GrpConfMemberGeneralSid cht010GrpConfMemberGeneralSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfMemberGeneralSid__
     */
    public void setCht010GrpConfMemberGeneralSid(
            String[] cht010GrpConfMemberGeneralSid) {
        cht010GrpConfMemberGeneralSid__ = cht010GrpConfMemberGeneralSid;
    }
    /**
     * <p>cht010GrpConfMemberUI を取得します。
     * @return cht010GrpConfMemberUI
     * @see jp.groupsession.v2.cht.cht010.Cht010ParamModel#cht010GrpConfMemberUI__
     */
    public Cht010UserGroupSelector getCht010GrpConfMemberUI() {
        return cht010GrpConfMemberUI__;
    }
    /**
     * <p>cht010GrpConfMemberUI をセットします。
     * @param cht010GrpConfMemberUI cht010GrpConfMemberUI
     * @see jp.groupsession.v2.cht.cht010.Cht010ParamModel#cht010GrpConfMemberUI__
     */
    public void setCht010GrpConfMemberUI(
            Cht010UserGroupSelector cht010GrpConfMemberUI) {
        cht010GrpConfMemberUI__ = cht010GrpConfMemberUI;
    }
    /**
     * <p>cht010GrpConfBiko を取得します。
     * @return cht010GrpConfBiko
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfBiko__
     */
    public String getCht010GrpConfBiko() {
        return cht010GrpConfBiko__;
    }
    /**
     * <p>cht010GrpConfBiko をセットします。
     * @param cht010GrpConfBiko cht010GrpConfBiko
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfBiko__
     */
    public void setCht010GrpConfBiko(String cht010GrpConfBiko) {
        cht010GrpConfBiko__ = cht010GrpConfBiko;
    }
    /**
     * <p>cht010GrpConfArchiveKbn を取得します。
     * @return cht010GrpConfArchiveKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfArchiveKbn__
     */
    public int getCht010GrpConfArchiveKbn() {
        return cht010GrpConfArchiveKbn__;
    }
    /**
     * <p>cht010GrpConfArchiveKbn をセットします。
     * @param cht010GrpConfArchiveKbn cht010GrpConfArchiveKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfArchiveKbn__
     */
    public void setCht010GrpConfArchiveKbn(int cht010GrpConfArchiveKbn) {
        cht010GrpConfArchiveKbn__ = cht010GrpConfArchiveKbn;
    }
    /**
     * <p>cht010GrpConfCategoryList を取得します。
     * @return cht010GrpConfCategoryList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfCategoryList__
     */
    public List<LabelValueBean> getCht010GrpConfCategoryList() {
        return cht010GrpConfCategoryList__;
    }
    /**
     * <p>cht010GrpConfCategoryList をセットします。
     * @param cht010GrpConfCategoryList cht010GrpConfCategoryList
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfCategoryList__
     */
    public void setCht010GrpConfCategoryList(
            List<LabelValueBean> cht010GrpConfCategoryList) {
        cht010GrpConfCategoryList__ = cht010GrpConfCategoryList;
    }
    /**
     * <p>cht010GrpConfProcMode を取得します。
     * @return cht010GrpConfProcMode
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfProcMode__
     */
    public int getCht010GrpConfProcMode() {
        return cht010GrpConfProcMode__;
    }
    /**
     * <p>cht010GrpConfProcMode をセットします。
     * @param cht010GrpConfProcMode cht010GrpConfProcMode
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfProcMode__
     */
    public void setCht010GrpConfProcMode(int cht010GrpConfProcMode) {
        cht010GrpConfProcMode__ = cht010GrpConfProcMode;
    }
    /**
     * <p>cht010AllTempSid を取得します。
     * @return cht010AllTempSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010AllTempSid__
     */
    public String getCht010AllTempSid() {
        return cht010AllTempSid__;
    }
    /**
     * <p>cht010AllTempSid をセットします。
     * @param cht010AllTempSid cht010AllTempSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010AllTempSid__
     */
    public void setCht010AllTempSid(String cht010AllTempSid) {
        cht010AllTempSid__ = cht010AllTempSid;
    }
    /**
     * <p>cht010MessageMaxMinSid を取得します。
     * @return cht010MessageMaxMinSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageMaxMinSid__
     */
    public Long getCht010MessageMaxMinSid() {
        return cht010MessageMaxMinSid__;
    }
    /**
     * <p>cht010MessageMaxMinSid をセットします。
     * @param cht010MessageMaxMinSid cht010MessageMaxMinSid
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageMaxMinSid__
     */
    public void setCht010MessageMaxMinSid(Long cht010MessageMaxMinSid) {
        cht010MessageMaxMinSid__ = cht010MessageMaxMinSid;
    }
    /**
     * <p>cht010ReadFlg を取得します。
     * @return cht010ReadFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010ReadFlg__
     */
    public int getCht010ReadFlg() {
        return cht010ReadFlg__;
    }
    /**
     * <p>cht010ReadFlg をセットします。
     * @param cht010ReadFlg cht010ReadFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010ReadFlg__
     */
    public void setCht010ReadFlg(int cht010ReadFlg) {
        cht010ReadFlg__ = cht010ReadFlg;
    }
    /**
     * <p>cht010GrpConfDspGrpNum を取得します。
     * @return cht010GrpConfDspGrpNum
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfDspGrpNum__
     */
    public int getCht010GrpConfDspGrpNum() {
        return cht010GrpConfDspGrpNum__;
    }
    /**
     * <p>cht010GrpConfDspGrpNum をセットします。
     * @param cht010GrpConfDspGrpNum cht010GrpConfDspGrpNum
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfDspGrpNum__
     */
    public void setCht010GrpConfDspGrpNum(int cht010GrpConfDspGrpNum) {
        cht010GrpConfDspGrpNum__ = cht010GrpConfDspGrpNum;
    }

    /**
     * <p>cht010FirstEntryDay を取得します。
     * @return cht010FirstEntryDay
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FirstEntryDay__
     */
    public String getCht010FirstEntryDay() {
        return cht010FirstEntryDay__;
    }

    /**
     * <p>cht010FirstEntryDay をセットします。
     * @param cht010FirstEntryDay cht010FirstEntryDay
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FirstEntryDay__
     */
    public void setCht010FirstEntryDay(String cht010FirstEntryDay) {
        cht010FirstEntryDay__ = cht010FirstEntryDay;
    }



    /**
     * <p>cht010MemberUserSids を取得します。
     * @return cht010MemberUserSids
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MemberUserSids__
     */
    public String[] getCht010MemberUserSids() {
        return cht010MemberUserSids__;
    }



    /**
     * <p>cht010MemberUserSids をセットします。
     * @param cht010MemberUserSids cht010MemberUserSids
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MemberUserSids__
     */
    public void setCht010MemberUserSids(String[] cht010MemberUserSids) {
        cht010MemberUserSids__ = cht010MemberUserSids;
    }


    /**
     * <p>cht010CRealTimeFlg を取得します。
     * @return cht010CRealTimeFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010CRealTimeFlg__
     */
    public int getCht010CRealTimeFlg() {
        return cht010CRealTimeFlg__;
    }



    /**
     * <p>cht010CRealTimeFlg をセットします。
     * @param cht010cRealTimeFlg cht010CRealTimeFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010CRealTimeFlg__
     */
    public void setCht010CRealTimeFlg(int cht010cRealTimeFlg) {
        cht010CRealTimeFlg__ = cht010cRealTimeFlg;
    }



    /**
     * <p>cht010MessageCount を取得します。
     * @return cht010MessageCount
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageCount__
     */
    public int getCht010MessageCount() {
        return cht010MessageCount__;
    }



    /**
     * <p>cht010MessageCount をセットします。
     * @param cht010MessageCount cht010MessageCount
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageCount__
     */
    public void setCht010MessageCount(int cht010MessageCount) {
        cht010MessageCount__ = cht010MessageCount;
    }



    /**
     * <p>cht010MessageLastDate を取得します。
     * @return cht010MessageLastDate
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageLastDate__
     */
    public String getCht010MessageLastDate() {
        return cht010MessageLastDate__;
    }



    /**
     * <p>cht010MessageLastDate をセットします。
     * @param cht010MessageLastDate cht010MessageLastDate
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010MessageLastDate__
     */
    public void setCht010MessageLastDate(String cht010MessageLastDate) {
        cht010MessageLastDate__ = cht010MessageLastDate;
    }



    /**
     * <p>cht010OldMemberSids を取得します。
     * @return cht010OldMemberSids
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010OldMemberSids__
     */
    public String[] getCht010OldMemberSids() {
        return cht010OldMemberSids__;
    }



    /**
     * <p>cht010OldMemberSids をセットします。
     * @param cht010OldMemberSids cht010OldMemberSids
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010OldMemberSids__
     */
    public void setCht010OldMemberSids(String[] cht010OldMemberSids) {
        cht010OldMemberSids__ = cht010OldMemberSids;
    }



    /**
     * <p>cht010GroupEditFlg を取得します。
     * @return cht010GroupEditFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GroupEditFlg__
     */
    public int getCht010GroupEditFlg() {
        return cht010GroupEditFlg__;
    }



    /**
     * <p>cht010GrpConfDspArcKbn を取得します。
     * @return cht010GrpConfDspArcKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfDspArcKbn__
     */
    public int getCht010GrpConfDspArcKbn() {
        return cht010GrpConfDspArcKbn__;
    }



    /**
     * <p>cht010GrpConfDspArcKbn をセットします。
     * @param cht010GrpConfDspArcKbn cht010GrpConfDspArcKbn
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GrpConfDspArcKbn__
     */
    public void setCht010GrpConfDspArcKbn(int cht010GrpConfDspArcKbn) {
        cht010GrpConfDspArcKbn__ = cht010GrpConfDspArcKbn;
    }



    /**
     * <p>cht010GroupEditFlg をセットします。
     * @param cht010GroupEditFlg cht010GroupEditFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010GroupEditFlg__
     */
    public void setCht010GroupEditFlg(int cht010GroupEditFlg) {
        cht010GroupEditFlg__ = cht010GroupEditFlg;
    }

    /**
     * <p>cht010AllDispFlg を取得します。
     * @return cht010AllDispFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010AllDispFlg__
     */
    public int getCht010AllDispFlg() {
        return cht010AllDispFlg__;
    }

    /**
     * <p>cht010AllDispFlg をセットします。
     * @param cht010AllDispFlg cht010AllDispFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010AllDispFlg__
     */
    public void setCht010AllDispFlg(int cht010AllDispFlg) {
        cht010AllDispFlg__ = cht010AllDispFlg;
    }

    /**
     * <p>cht010SplitMessageFirstFlg を取得します。
     * @return cht010SplitMessageFirstFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SplitMessageFirstFlg__
     */
    public int getCht010SplitMessageFirstFlg() {
        return cht010SplitMessageFirstFlg__;
    }

    /**
     * <p>cht010SplitMessageFirstFlg をセットします。
     * @param cht010SplitMessageFirstFlg cht010SplitMessageFirstFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010SplitMessageFirstFlg__
     */
    public void setCht010SplitMessageFirstFlg(int cht010SplitMessageFirstFlg) {
        cht010SplitMessageFirstFlg__ = cht010SplitMessageFirstFlg;
    }

    /**
     * <p>cht010FromMain を取得します。
     * @return cht010FromMain
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FromMain__
     */
    public int getCht010FromMain() {
        return cht010FromMain__;
    }

    /**
     * <p>cht010FromMain をセットします。
     * @param cht010FromMain cht010FromMain
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010FromMain__
     */
    public void setCht010FromMain(int cht010FromMain) {
        cht010FromMain__ = cht010FromMain;
    }

    /**
     * <p>cht010InitFlg を取得します。
     * @return cht010InitFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010InitFlg__
     */
    public int getCht010InitFlg() {
        return cht010InitFlg__;
    }

    /**
     * <p>cht010InitFlg をセットします。
     * @param cht010InitFlg cht010InitFlg
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010InitFlg__
     */
    public void setCht010InitFlg(int cht010InitFlg) {
        cht010InitFlg__ = cht010InitFlg;
    }

    /**
     * <p>cht010KidokuMessageSids を取得します。
     * @return cht010KidokuMessageSids
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010KidokuMessageSids__
     */
    public String getCht010KidokuMessageSids() {
        return cht010KidokuMessageSids__;
    }

    /**
     * <p>cht010KidokuMessageSids をセットします。
     * @param cht010KidokuMessageSids cht010KidokuMessageSids
     * @see jp.groupsession.v2.cht.cht010.Cht010Form#cht010KidokuMessageSids__
     */
    public void setCht010KidokuMessageSids(String cht010KidokuMessageSids) {
        cht010KidokuMessageSids__ = cht010KidokuMessageSids;
    }

}
