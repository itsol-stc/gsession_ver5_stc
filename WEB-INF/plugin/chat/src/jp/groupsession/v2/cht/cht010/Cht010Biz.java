package jp.groupsession.v2.cht.cht010;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.FileNameUtil;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cht.biz.ChtUsedDataBiz;
import jp.groupsession.v2.cht.biz.ChtUserPairCreateBiz;
import jp.groupsession.v2.cht.biz.ChtWebSocketBiz;
import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cht.dao.ChtFavoriteDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataSumDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtGroupTargetDao;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cht.dao.ChtGroupViewDao;
import jp.groupsession.v2.cht.dao.ChtLogCountDao;
import jp.groupsession.v2.cht.dao.ChtPriConfDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessPermitDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessTargetDao;
import jp.groupsession.v2.cht.dao.ChtUserDataDao;
import jp.groupsession.v2.cht.dao.ChtUserPairDao;
import jp.groupsession.v2.cht.dao.ChtUserViewDao;
import jp.groupsession.v2.cht.model.ChatAllTempDataModel;
import jp.groupsession.v2.cht.model.ChatGroupComboModel;
import jp.groupsession.v2.cht.model.ChatGroupInfModel;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.cht.model.ChatMessageModel;
import jp.groupsession.v2.cht.model.ChatMidokuModel;
import jp.groupsession.v2.cht.model.ChatUserInfModel;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtFavoriteModel;
import jp.groupsession.v2.cht.model.ChtGroupDataModel;
import jp.groupsession.v2.cht.model.ChtGroupDataSumModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cht.model.ChtGroupUserModel;
import jp.groupsession.v2.cht.model.ChtGroupViewModel;
import jp.groupsession.v2.cht.model.ChtLogCountModel;
import jp.groupsession.v2.cht.model.ChtPriConfModel;
import jp.groupsession.v2.cht.model.ChtUserDataModel;
import jp.groupsession.v2.cht.model.ChtUserViewModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.UserUtil;


/**
 * <br>[機  能] チャット一覧のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht010Biz.class);
    /** ダウンロードチェック エラーコード OK*/
    public static final int DOWNLOADCHECK_CODE_OK = 0;
    /** ダウンロードチェック エラーコード 存在しない*/
    public static final int DOWNLOADCHECK_CODE_NONE = 1;
    /** ダウンロードチェック エラーコード 削除済み*/
    public static final int DOWNLOADCHECK_CODE_DELEATED = 2;
    /** ダウンロードチェック エラーコード ZIPエラー*/
    public static final int DOWNLOADCHECK_CODE_ZIPERROR = 3;

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル*/
    RequestModel reqMdl__ = null;

    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public Cht010Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * コンストラクタ
     */
    public Cht010Biz() {

    }

    /**
     * <br>[機  能]初期設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param admin 管理者フラグ
     * @param cmd コマンド
     * @throws SQLException SQLException
     */
    public void setInitData(Cht010ParamModel paramMdl,
            boolean admin, String cmd)
            throws SQLException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        paramMdl.setCht010EditUsrSid(sessionUsrSid);

        //管理者か設定
        if (admin) {
            paramMdl.setAdminFlg(1);
        } else {
            paramMdl.setAdminFlg(0);
        }

        //管理者設定の取得
        ChtBiz chtBiz = new ChtBiz(con__);
        ChtAdmConfModel adminMdl = chtBiz.getChtAconf();

        //個人設定の取得（デフォルト表示を取得）
        ChtPriConfModel userMdl = chtBiz.getChtUconf(sessionUsrSid);

        /*左メニュー情報の取得*/
        //グループの編集権限があるか判定する
        if (admin || adminMdl.getCacGroupFlg() == GSConstChat.PERMIT_CREATE_GROUP) {
            paramMdl.setCht010GroupEditFlg(GSConstChat.CHAT_GROUP_EDIT);
        } else {
            ChtGroupTargetDao cgtDao = new ChtGroupTargetDao(con__);
            int cnt = cgtDao.select(sessionUsrSid);
            if ((adminMdl.getCacGroupKbn() == GSConstChat.TARGET_PERMIT && cnt > 0)
                    || (adminMdl.getCacGroupKbn() == GSConstChat.TARGET_LIMIT && cnt == 0)) {
                paramMdl.setCht010GroupEditFlg(GSConstChat.CHAT_GROUP_EDIT);
            } else {
                ChtGroupUserDao cguDao = new ChtGroupUserDao(con__);
                int adminCnt = cguDao.getGroupEditUser(sessionUsrSid);
                if (adminCnt > 0) {
                    paramMdl.setCht010GroupEditFlg(GSConstChat.CHAT_GROUP_EDIT);
                } else {
                    paramMdl.setCht010GroupEditFlg(GSConstChat.CHAT_GROUP_NOT_EDIT);
                }
            }
        }

        //未読件数の取得
        ChatDao chtDao = new ChatDao(con__);
        int midokuCount = chtDao.getMidokuCount(sessionUsrSid);
        paramMdl.setCht010MidokuCount(midokuCount);

        boolean onlyNoRead = (paramMdl.getCht010TimelineDspOnlyNoRead() > 0);

        //未読リストの取得
        UDate now = new UDate();
        ArrayList<ChatMidokuModel> mList = new ArrayList<ChatMidokuModel>();
        if (midokuCount == 0 && onlyNoRead) {
            paramMdl.setCht010MidokuList(mList);
        } else {
            mList = chtDao.getTimelineList(sessionUsrSid, now, onlyNoRead);
            //もっとみる表示チェック
            int cnt = chtDao.getTimelineListCount(sessionUsrSid, now, onlyNoRead);
            if (cnt > mList.size()) {
                paramMdl.setCht010MoreView(1);
            }
        }
        paramMdl.setCht010MidokuList(mList);

        //お気に入り情報の取得
        //チャットグループ
        ChtGroupInfDao groupInfDao = new ChtGroupInfDao(con__);
        ArrayList<ChatGroupInfModel> gInfMdlList = chtDao.favoriteGroupSelect(sessionUsrSid);
        paramMdl.setCht010FavoriteGroup(gInfMdlList);
        //ユーザ
        ArrayList<ChatUserInfModel> cmnUserList = chtDao.favoriteUserSelect(sessionUsrSid);
        paramMdl.setCht010FavoriteUser(cmnUserList);

        //チャットグループ情報の取得
        //チャットグループ
        ArrayList<ChatGroupInfModel> groupInfList =  groupInfDao.selectGroup(sessionUsrSid);
        paramMdl.setCht010GroupList(groupInfList);

        //ユーザ情報の取得
        getUser(paramMdl, sessionUsrSid);
        int chtKbn = 0;
        int chtSid = 0;
        /*右チャット一覧情報の取得*/
        if (cmd.equals("pushDsp") || cmd.equals("back")) {
            chtKbn = paramMdl.getCht010SelectKbn();
            chtSid = paramMdl.getCht010SelectPartner();
            if (chtKbn == 0 || chtSid == 0) {
                chtKbn = userMdl.getCpcChatKbn();
                chtSid = userMdl.getCpcSelSid();
            }
            if (cmd.equals("back")) {
                paramMdl.setCht010InitFlg(GSConstChat.DSP_ALREADY);
            }
        } else if (cmd.equals("reload")) {
            // 再読み込みボタン押下
            chtKbn = paramMdl.getCht010SelectKbn();
            chtSid = paramMdl.getCht010SelectPartner();
            paramMdl.setCht010InitFlg(GSConstChat.DSP_RELOAD);
            paramMdl.setCht010FromMain(GSConstChat.FROM_NOT_MAIN);
        } else {
            //デフォルト表示の取得
            chtKbn = userMdl.getCpcChatKbn();
            chtSid = userMdl.getCpcSelSid();
            // メイン画面から遷移してきた場合
            if (cmd.equals("fromMain")) {
                paramMdl.setCht010FromMain(GSConstChat.FROM_MAIN);
            }

        }
        if (chtKbn == GSConstChat.CHAT_KBN_GROUP) {
            //削除されたグループが選択されていないかのチェック
            ChtGroupInfDao cgiDao = new ChtGroupInfDao(con__);
            ChtGroupInfModel cgiMdl = cgiDao.select(chtSid);
            if (cgiMdl == null
                    || cgiMdl.getCgiDelFlg() == GSConstChat.CHAT_MODE_DELETE) {
                chtKbn = GSConstChat.CHAT_KBN_USER;
                chtSid = sessionUsrSid;
            }
            ChtGroupUserDao cguDao = new ChtGroupUserDao(con__);
            //グループメンバーチェック
            int cnt = cguDao.getAuthority(sessionUsrSid, chtSid);
            if (cnt == 0) {
                chtKbn = GSConstChat.CHAT_KBN_USER;
                chtSid = sessionUsrSid;
            }
        } else if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            //選択ユーザの存在確認チェック
            UserBiz usrBiz = new UserBiz();
            List<Integer> userSid = new ArrayList<Integer>();
            userSid.add(chtSid);
            List<CmnUsrmInfModel> uList = usrBiz.getUserList(con__, userSid);
            if (uList.size() == 0) {
                //ユーザが存在しない
                chtKbn = GSConstChat.CHAT_KBN_USER;
                chtSid = sessionUsrSid;
            } else {
                if (uList.get(0).getUsrJkbn() == GSConst.JTKBN_DELETE) {
                    //ユーザが削除済みだった場合過去にそのユーザとチャットを行っていれば表示
                    ChtUserPairDao cupDao = new ChtUserPairDao(con__);
                    if (cupDao.select(sessionUsrSid, chtSid) == 0) {
                        chtKbn = GSConstChat.CHAT_KBN_USER;
                        chtSid = sessionUsrSid;
                    }
                }
            }
        }
        paramMdl.setCht010SelectKbn(chtKbn);
        paramMdl.setCht010SelectPartner(chtSid);
        paramMdl.setCht010SelectTab(userMdl.getCpcSelTab());

        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            //チャット情報の取得
            getUserInfo(paramMdl, sessionUsrSid, chtSid);

            //メッセージ（投稿情報）の取得
            getUserMessageData(paramMdl, sessionUsrSid, chtSid, adminMdl, false);
        } else {
            //グループチャット情報の取得
            getGroupInfo(paramMdl, chtSid);

            //メッセージ（投稿情報）の取得
            getGroupMessageData(paramMdl, sessionUsrSid, chtSid, false);
        }
        //初回投稿情報
        getFirstSend(paramMdl, sessionUsrSid, chtKbn, chtSid);

        //お気に入り情報の取得
        getFavorite(paramMdl, sessionUsrSid, chtKbn, chtSid);

        //メッセージ送信スペース表示設定
        getMessageSendSpace(paramMdl, chtKbn, chtSid, sessionUsrSid, adminMdl);

        //Enterキー送信フラグ
        paramMdl.setCht010EnterSendFlg(userMdl.getCpcEnterFlg());
    }


    /**
     *
     * <br>[機  能]ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQLException
     */
    public void getUser(Cht010ParamModel paramMdl, int sessionUsrSid)
            throws SQLException {
        ChatDao chtDao = new ChatDao(con__);
        //削除済みユーザと過去にユーザ間チャットしているかを取得する
        boolean delUserHave = false;
        ArrayList<CmnUsrmInfModel> delUserList = chtDao.getDeleteUser(sessionUsrSid);
        if (delUserList.size() > 0) {
            delUserHave = true;
        }

        //グループ情報を取得する
        GroupBiz gpBiz = new GroupBiz();
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con__);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(sessionUsrSid);
        List<CmnLabelValueModel> dspGrpList = new ArrayList<CmnLabelValueModel>();
        //マイグループリストをセット
        for (CmnMyGroupModel cmgMdl : cmgList) {
            dspGrpList.add(
                    new CmnLabelValueModel(
                            cmgMdl.getMgpName(), "M" + String.valueOf(cmgMdl.getMgpSid()),
                            "1", false));
        }
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con__, true, gsMsg);
        for (LabelValueBean bean : grpLabelList) {
            dspGrpList.add(new CmnLabelValueModel(bean.getLabel(), bean.getValue(), "0", true));
        }
        if (delUserHave) {
            dspGrpList.add(new CmnLabelValueModel("削除済みユーザ", "-2", "0", true));
        }

        paramMdl.setCht010ComboGroupList(dspGrpList);

        //グループSIDから所属ユーザ一覧を作成
        String groupSid = paramMdl.getCht010GroupSid();
        if (StringUtil.isNullZeroString(groupSid)) {
            GroupBiz grpBiz = new GroupBiz();
            groupSid = String.valueOf(grpBiz.getDefaultGroupSid(sessionUsrSid, con__));
            paramMdl.setCht010GroupSid(groupSid);
        }
        int grpSid = getDspGroupSid(groupSid);
        List<Integer> users = new ArrayList<Integer>();
        if (isMyGroupSid(groupSid)) {
            //マイグループから作成
            CmnMyGroupMsDao mgmsDao = new CmnMyGroupMsDao(con__);
            users = mgmsDao.selectMyGroupUsers(sessionUsrSid, grpSid);

        } else if (grpSid == -2) {
            for (CmnUsrmInfModel mdl : delUserList) {
                users.add(mdl.getUsrSid());
            }
        } else {
            //通常グループから作成
            CmnBelongmDao cmnbDao = new CmnBelongmDao(con__);
            users = cmnbDao.selectBelongUserSid(getDspGroupSid(groupSid));
        }

        //チャットプラグインを使用していないユーザを除外する。
        CommonBiz cmnBiz = new CommonBiz();
        List<Integer> usrList = (ArrayList<Integer>) cmnBiz.getCanUseChatUser(con__, users);

        //システムメールとGS管理者を除外する
        List<Integer> usrDspList = new ArrayList<Integer>();
        for (Integer usid : usrList) {
            if (usid != GSConstUser.SID_ADMIN && usid != GSConstUser.SID_SYSTEM_MAIL) {
                usrDspList.add(usid);
            }
        }
        //ユーザ情報を取得
        List<CmnUsrmInfModel> uList = new ArrayList<CmnUsrmInfModel>();
        if (grpSid != -2) {
            if (users != null && users.size() > 0) {
                //ユーザ情報一覧を作成
                UserBiz usrBiz = new UserBiz();
                uList = usrBiz.getUserList(con__, usrDspList);
            }
        } else {
            if (users != null && users.size() > 0) {
                //ユーザ情報一覧を作成
                UserBiz usrBiz = new UserBiz();
                List<String> sSid = new ArrayList<String>();
                for (Integer sid : usrDspList) {
                    sSid.add(sid.toString());
                }
                String[] sidList = sSid.toArray(new String[sSid.size()]);
                uList = usrBiz.getUserList(con__, sidList, GSConst.JTKBN_DELETE);
                for (int idx = 0; idx < uList.size(); idx++) {
                    uList.get(idx).setUsrJkbn(GSConst.JTKBN_DELETE);
                }
            }
        }
        ArrayList<ChatUserInfModel> cmList = new ArrayList<ChatUserInfModel>();
        if (uList.size() > 0) {
            cmList = chtDao.getUserCntList(sessionUsrSid, uList);
        }
        paramMdl.setCht010UserList(cmList);
    }

    /**
     * <br>[機  能]個人設定の更新
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @throws SQLException SQLException
     */
    public void updatePriConf(Cht010ParamModel paramMdl) throws SQLException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        //個人設定の取得（デフォルト表示を取得）
        ChtBiz chtBiz = new ChtBiz(con__);
        ChtPriConfModel userMdl = chtBiz.getChtUconf(sessionUsrSid);

        if (userMdl.getCpcDefFlg() == GSConstChat.PRI_DEF_FLG_LAST) {
            userMdl.setCpcChatKbn(paramMdl.getCht010SelectKbn());
            userMdl.setCpcSelSid(paramMdl.getCht010SelectPartner());
            ChtPriConfDao confDao = new ChtPriConfDao(con__);
            if (confDao.updateDefaultDsp(userMdl) == 0) {
                confDao.insert(userMdl);
            }
        }
    }

    /**
     * <br>[機  能]初回投稿の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param sessionUsrSid ユーザSID
     * @param chtKbn チャット区分 1:ユーザ 2:グループ
     * @param chtSid 選択相手SID
     * @throws SQLException SQLException
     */
    public void getFirstSend(Cht010ParamModel paramMdl,
            int sessionUsrSid, int chtKbn, int chtSid) throws SQLException {

        String entryDay = "";
        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            ChtUserDataDao usrDao = new ChtUserDataDao(con__);
            entryDay = usrDao.getFirstDate(sessionUsrSid, chtSid);
        } else {
            ChtGroupDataDao grpDao = new ChtGroupDataDao(con__);
            entryDay = grpDao.getFirstDate(chtSid);
        }
        paramMdl.setCht010FirstEntryDay(entryDay);

    }

    /**
     * <br>[機  能]お気に入り情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param sessionUsrSid ユーザSID
     * @param chtKbn チャット区分 1:ユーザ 2:グループ
     * @param chtSid 選択相手SID
     * @throws SQLException SQLException
     */
    public void getFavorite(Cht010ParamModel paramMdl,
            int sessionUsrSid, int chtKbn, int chtSid) throws SQLException {
        ChtFavoriteDao cfDao = new ChtFavoriteDao(con__);
        if (cfDao.select(sessionUsrSid, chtKbn, chtSid) != 0) {
            paramMdl.setCht010FavoriteFlg(1);
        } else {
            paramMdl.setCht010FavoriteFlg(0);
        }
    }

    /**
     * <br>[機  能]メッセージ送信スペース表示設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param chtKbn チャット区分 1:ユーザ 2:グループ
     * @param chtSid 選択相手SID
     * @param sessionUsrSid セッションユーザSID
     * @param adminMdl 管理者モデル
     * @throws SQLException SQLException
     */
    public void getMessageSendSpace(Cht010ParamModel paramMdl,
            int chtKbn, int chtSid, int sessionUsrSid,
            ChtAdmConfModel adminMdl) throws SQLException {

      //ユーザ間チャットの場合相手が特例ユーザかつ自分が権限をもっているかのチェック
        boolean accessUser = true;
        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            if (chtSid != sessionUsrSid) {
                ChtSpaccessTargetDao cstDao = new ChtSpaccessTargetDao(con__);
                ArrayList<Integer> spList = cstDao.selectSid(chtSid);
                if (spList.size() != 0) {
                    ChtSpaccessPermitDao cspDao = new ChtSpaccessPermitDao(con__);
                    int cntSid = cspDao.selectCount(spList, sessionUsrSid);
                    if (cntSid == 0) {
                        accessUser = false;
                    }
                }
            }
        }
        //メッセージ送信スペース表示設定
        if (chtKbn == GSConstChat.CHAT_KBN_USER
                && adminMdl.getCacChatFlg() == GSConstChat.LIMIT_BETWEEN_USERS) {
            paramMdl.setCht010MessageAreaDisp(GSConstChat.NOT_SEND_USER_SEIGEN);
        }
        if (chtKbn == GSConstChat.CHAT_KBN_GROUP
                && paramMdl.getCht010ChtInfMdl().getChatArchive()
                == GSConstChat.CHAT_ARCHIVE_MODE) {
            paramMdl.setCht010MessageAreaDisp(GSConstChat.NOT_SEND_ARCHIVE);

        }
        if (chtKbn == GSConstChat.CHAT_KBN_USER
                && paramMdl.getCht010ChtInfMdl().getChatArchive()
                == GSConstChat.CHAT_ARCHIVE_MODE) {
            paramMdl.setCht010MessageAreaDisp(GSConstChat.NOT_SEND_DELETE_USER);
        }
        if (chtKbn == GSConstChat.CHAT_KBN_USER
                && !accessUser) {
            paramMdl.setCht010MessageAreaDisp(GSConstChat.NOT_SEND_SPACCESS);
        }
        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            CommonBiz cmnBiz = new CommonBiz();
            List<String> menuPluginIdList = cmnBiz.getCanUsePluginIdList(con__, chtSid);
            if (!menuPluginIdList.isEmpty()) {
                boolean bAccess = false;
                for (String pId : menuPluginIdList) {
                    if (pId.equals(GSConstChat.PLUGIN_ID_CHAT)) {
                        bAccess = true;
                        break;
                    }
                }
                if (!bAccess) {
                    paramMdl.setCht010MessageAreaDisp(GSConstChat.NOT_SEND_ACCESS);
                }
            } else {
                paramMdl.setCht010MessageAreaDisp(GSConstChat.NOT_SEND_ACCESS);
            }
        }
    }


    /**
     * <br>[機  能] チャット相手の名称取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @return 名称
     * @throws SQLException SQLException
     */
    public String getPartnerName(Cht010ParamModel paramMdl)
            throws SQLException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        int partnerSid = paramMdl.getCht010SelectPartner();
        int partnerKbn = paramMdl.getCht010SelectKbn();
        if (partnerKbn == GSConstChat.CHAT_KBN_USER) {
            getUserInfo(paramMdl, usrSid, partnerSid);
        } else {
            getGroupInfo(paramMdl, partnerSid);
        }
        String name = "";
        if (paramMdl.getCht010ChtInfMdl() != null) {
            name = paramMdl.getCht010ChtInfMdl().getChatName();
        }
        return name;
    }

    /**
     * <br>[機  能] ユーザ情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param sessionUsrSid ユーザSID
     * @param partnerUsrSid 相手ユーザSID
     * @throws SQLException SQLException
     */
    public void getUserInfo(Cht010ParamModel paramMdl, int sessionUsrSid, int partnerUsrSid)
            throws SQLException {

        ChtUserPairDao cupDao = new ChtUserPairDao(con__);
        int pairSid = cupDao.select(sessionUsrSid, partnerUsrSid);
        ChatDao chtDao = new ChatDao(con__);
        CmnUsrmInfModel usrMdl = chtDao.getUser(partnerUsrSid);
        ChatInformationModel cimMdl = new ChatInformationModel();
        cimMdl.setChatKbn(GSConstChat.CHAT_KBN_USER);
        cimMdl.setChatSid(pairSid);
        cimMdl.setChatName(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
        ArrayList<CmnUsrmInfModel> userList = new ArrayList<CmnUsrmInfModel>();
        userList.add(usrMdl);
        cimMdl.setGeneralMember(userList);
        if (usrMdl.getUsrJkbn() == GSConst.JTKBN_DELETE) {
            cimMdl.setChatArchive(GSConstChat.CHAT_ARCHIVE_MODE);
        }
        paramMdl.setCht010ChtInfMdl(cimMdl);
    }

    /**
     * <br>[機  能]ユーザチャットのメッセージを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param sessionSid セッションユーザSID
     * @param partnerUsrSid 相手ユーザSID
     * @param adminMdl 管理者model
     * @param scrollFlg スクロールフラグ
     * @throws SQLException SQLException
     */
    public void getUserMessageData(Cht010ParamModel paramMdl, int sessionSid,
            int partnerUsrSid,
            ChtAdmConfModel adminMdl, boolean scrollFlg)
            throws SQLException {

        //ペアSIDの取得
        ChtUserPairDao cupDao = new ChtUserPairDao(con__);
        int pairSid = cupDao.select(sessionSid, partnerUsrSid);

        //既読の表示が許可されている場合相手の閲覧情報を取得
        ChtUserViewDao cuvDao = new ChtUserViewDao(con__);
        int partnerViewSid = 0;
        if (adminMdl.getCacReadFlg() == 1) {
            partnerViewSid = cuvDao.selectCudSid(pairSid, partnerUsrSid);
        }

        //投稿情報の取得
        ChatDao cDao = new ChatDao(con__);
        boolean kidokuFlg = false;
        if (adminMdl.getCacReadFlg() == GSConstChat.KIDOKU_DISP) {
            kidokuFlg = true;
        }
        ArrayList<ChatMessageModel> cmList =
                cDao.getUserMessage(pairSid, sessionSid, partnerViewSid, kidokuFlg,
                        scrollFlg, paramMdl.getCht010ReadFlg(),
                        paramMdl.getCht010MessageMaxMinSid());

        for (int idx = 0; idx < cmList.size(); idx++) {
            String message = StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                    NullDefault.getString(cmList.get(idx).getMessageText(), ""));
            cmList.get(idx).setMessageText(message);
        }
        paramMdl.setCht010MessageList(cmList);

        //今取得したメッセージリストが最新まで取得しているかのチェック
        if (GSConstChat.CHAT_TOP_SCROLL != paramMdl.getCht010ReadFlg()) {
            Long maxMesSid = (long) 0;
            for (ChatMessageModel cmMdl : cmList) {
                if (maxMesSid < cmMdl.getMessageSid()) {
                    maxMesSid = cmMdl.getMessageSid();
                }
            }
            ChtUserDataDao cudDao = new ChtUserDataDao(con__);
            Long maxSid = cudDao.getMaxSid(pairSid);
            if (maxSid.equals(maxMesSid)) {
                paramMdl.setCht010AllDispFlg(1);
            } else {
                paramMdl.setCht010AllDispFlg(0);
            }
        }
    }

    /**
     * <br>[機  能]グループ情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param chtSid 選択グループSID
     * @throws SQLException SQLException
     */
    public void getGroupInfo(Cht010ParamModel paramMdl, int chtSid) throws SQLException {

        ChatDao chtDao = new ChatDao(con__);
        //チャット情報の取得
        ChatInformationModel cimMdl = chtDao.getGroupInfoData(chtSid);
        cimMdl.setChatKbn(GSConstChat.CHAT_KBN_GROUP);
        //管理者ユーザの取得
        ArrayList<CmnUsrmInfModel> adminList = new ArrayList<CmnUsrmInfModel>();
        adminList = chtDao.getGroupUser(chtSid, GSConstChat.CHAT_GROUP_ADMIN);
        cimMdl.setAdminMember(adminList);
        ArrayList<CmnGroupmModel> adminGroupList = new ArrayList<CmnGroupmModel>();
        adminGroupList = chtDao.getGroup(chtSid, GSConstChat.CHAT_GROUP_ADMIN);
        cimMdl.setAdminGroup(adminGroupList);
        //一般ユーザの取得
        ArrayList<CmnUsrmInfModel> generalList = new ArrayList<CmnUsrmInfModel>();
        generalList = chtDao.getGroupUser(chtSid, GSConstChat.CHAT_GROUP_NOT_ADMIN);
        cimMdl.setGeneralMember(generalList);
        ArrayList<CmnGroupmModel> genaralGroupList = new ArrayList<CmnGroupmModel>();
        genaralGroupList = chtDao.getGroup(chtSid, GSConstChat.CHAT_GROUP_NOT_ADMIN);
        cimMdl.setGeneralGroup(genaralGroupList);
        cimMdl.setChatName(cimMdl.getChatName());
        paramMdl.setCht010ChtInfMdl(cimMdl);
    }

    /**
     * <br>[機  能]グループチャットのメッセージを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param sessionSid セッションユーザSID
     * @param chtGroupSid チャットグループSID
     * @param scrollFlg スクロールフラグ
     * @throws SQLException SQLException
     */
    public void getGroupMessageData(Cht010ParamModel paramMdl, int sessionSid,
            int chtGroupSid, boolean scrollFlg)
            throws SQLException {

        //メッセージ情報の取得
        ChatDao cDao = new ChatDao(con__);
        ArrayList<ChatMessageModel> cmList =
                cDao.getGroupMessage(chtGroupSid, sessionSid, scrollFlg,
                        paramMdl.getCht010ReadFlg(), paramMdl.getCht010MessageMaxMinSid());

        for (int idx = 0; idx < cmList.size(); idx++) {
            String message = StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                    NullDefault.getString(cmList.get(idx).getMessageText(), ""));
            cmList.get(idx).setMessageText(message);
        }
        paramMdl.setCht010MessageList(cmList);

        //今取得したメッセージリストが最新まで取得しているかのチェック
        if (GSConstChat.CHAT_TOP_SCROLL != paramMdl.getCht010ReadFlg()) {
            Long maxListMesSid = (long) 0;
            for (ChatMessageModel cmMdl : cmList) {
                if (maxListMesSid < cmMdl.getMessageSid()) {
                    maxListMesSid = cmMdl.getMessageSid();
                }
            }
            ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
            Long maxSid = cgdDao.getMaxSid(chtGroupSid);
            if (maxSid.equals(maxListMesSid)) {
                paramMdl.setCht010AllDispFlg(1);
            } else {
                paramMdl.setCht010AllDispFlg(0);
            }
        }
    }

    /**
     * パラメータ.グループコンボ値からグループSID又はマイグループSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (gpSid == null) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     * パラメータ.グループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("M");

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * <br>[機  能]チャット相手切り替え時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @return 正常終了true 権限エラーfalse
     * @throws SQLException SQLException
     */
    public boolean partnerChange(Cht010ParamModel paramMdl) throws SQLException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int chtPartnerSid = paramMdl.getCht010SelectPartner();
        int chtKbn = paramMdl.getCht010SelectKbn();

        // 切り替え先の権限チェック
        if (!__isViewPartner(sessionUsrSid, chtPartnerSid, chtKbn)) {
            return false;
        }

        //管理者設定の取得
        ChtBiz chtBiz = new ChtBiz(con__);
        ChtAdmConfModel adminMdl = chtBiz.getChtAconf();
        //個人設定の取得
        ChtPriConfModel userMdl = chtBiz.getChtUconf(sessionUsrSid);
        paramMdl.setCht010EnterSendFlg(userMdl.getCpcEnterFlg());

        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            getUserInfo(paramMdl, sessionUsrSid, chtPartnerSid);
            getUserMessageData(paramMdl, sessionUsrSid, chtPartnerSid, adminMdl, false);
        }

        if (chtKbn == GSConstChat.CHAT_KBN_GROUP) {
            getGroupInfo(paramMdl, chtPartnerSid);
            getGroupMessageData(paramMdl, sessionUsrSid, chtPartnerSid, false);
        }
        //メッセージ送信スペースの取得
        getMessageSendSpace(paramMdl, chtKbn, chtPartnerSid, sessionUsrSid, adminMdl);
        //初回送信日付
        getFirstSend(paramMdl, sessionUsrSid, chtKbn, chtPartnerSid);
        //お気に入り情報の取得
        getFavorite(paramMdl, sessionUsrSid, chtKbn, chtPartnerSid);
        if (userMdl.getCpcDefFlg() == GSConstChat.PRI_DEF_FLG_LAST) {
            userMdl.setCpcChatKbn(chtKbn);
            userMdl.setCpcSelSid(chtPartnerSid);
            ChtPriConfDao confDao = new ChtPriConfDao(con__);
            if (confDao.updateDefaultDsp(userMdl) == 0) {
                confDao.insert(userMdl);
            }
        }
        return true;
    }

    /**
     * <br>[機  能]スクロール時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @throws SQLException SQLException
     */
    public void scrollRead(Cht010ParamModel paramMdl) throws SQLException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int chtPartnerSid = paramMdl.getCht010SelectPartner();
        int chtKbn = paramMdl.getCht010SelectKbn();
        //管理者設定の取得
        ChtBiz chtBiz = new ChtBiz(con__);
        ChtAdmConfModel adminMdl = chtBiz.getChtAconf();
        //個人設定の取得
        ChtPriConfModel userMdl = chtBiz.getChtUconf(sessionUsrSid);
        paramMdl.setCht010EnterSendFlg(userMdl.getCpcEnterFlg());

        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            getUserMessageData(paramMdl, sessionUsrSid, chtPartnerSid, adminMdl, true);
        }
        if (chtKbn == GSConstChat.CHAT_KBN_GROUP) {
            getGroupMessageData(paramMdl, sessionUsrSid, chtPartnerSid, true);
        }
    }

    /**
     * <br>[機  能]既読アップデート処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @return アップデート件数 権限エラーの場合は-1を返す
     * @throws SQLException SQLException
     */
    public int kidokuUpdate(Cht010ParamModel paramMdl) throws SQLException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int chtPartnerSid = paramMdl.getCht010SelectPartner();
        int chtKbn = paramMdl.getCht010SelectKbn();
        long msgSid = paramMdl.getCht010MessageSid();
        ChatDao cDao = new ChatDao(con__);
        int cnt = 0;
        int delNum = 0;

        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            ChtUserPairDao cupDao = new ChtUserPairDao(con__);
            int cupSid = cupDao.select(sessionUsrSid, chtPartnerSid);
            ChtUserViewDao cuvDao = new ChtUserViewDao(con__);
            // 既存のメッセージSIDより大きい場合削除して挿入 削除対象のレコードが存在しない場合は"1"が返ってくる
            delNum = cuvDao.deleteIfMax(cupSid, sessionUsrSid, msgSid);
            if (delNum > 0) {
                ChtUserDataDao cudDao = new ChtUserDataDao(con__);
                ChtUserViewModel mdl = new ChtUserViewModel();
                mdl.setCudSid(msgSid);
                mdl.setCupSid(cupSid);
                mdl.setCuvUid(sessionUsrSid);
                mdl.setCuvViewcnt(cudDao.countTarget(cupSid, msgSid));
                cuvDao.insert(mdl);
                cnt = cDao.getMidokuCountPair(sessionUsrSid, cupSid);
            }
        } else if (chtKbn == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupViewDao cgvDao = new ChtGroupViewDao(con__);
            // 既存のメッセージSIDより大きい場合削除して挿入 削除対象のレコードが存在しない場合は"1"が返ってくる
            delNum = cgvDao.deleteIfMax(chtPartnerSid, sessionUsrSid, msgSid);
            if (delNum > 0) {
                ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
                ChtGroupViewModel mdl = new ChtGroupViewModel();
                mdl.setCgdSid(msgSid);
                mdl.setCgiSid(chtPartnerSid);
                mdl.setCgvUid(sessionUsrSid);
                mdl.setCgvViewcnt(cgdDao.countTarget(chtPartnerSid, msgSid));
                cgvDao.insert(mdl);
                cnt = cDao.getMidokuCountGroup(sessionUsrSid, chtPartnerSid);
            }
        }
        return cnt;
    }

    /**
     *
     * <br>[機  能] 既読処理時のアクセス権限チェック
     * <br>[解  説] チャット相手に対するアクセス権限がない場合、
     *              もしくは既読にするメッセージがチャット相手とのものではない場合エラーメッセージを返す
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return エラーメッセージ
     * @throws SQLException SQL実行例外
     */
    public String kidokuAccessCheck(Cht010ParamModel paramMdl) throws SQLException {
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int chtPartnerSid = paramMdl.getCht010SelectPartner();
        int chtKbn = paramMdl.getCht010SelectKbn();
        long msgSid = paramMdl.getCht010MessageSid();
        ChtUserPairDao cupDao = new ChtUserPairDao(con__);
        int cupSid = cupDao.select(sessionUsrSid, chtPartnerSid);
        GsMessage gsMsg = new GsMessage(reqMdl__);

        // 権限チェック
        if (!__isViewPartner(sessionUsrSid, chtPartnerSid, chtKbn)
                || !__isMatchPartner(chtKbn, chtPartnerSid, cupSid, msgSid)) {
            return gsMsg.getMessage("cht.cht010.47");
        }

        return "";
    }

    /**
     * <br>[機  能] チャット相手と対象メッセージとの整合性を判定
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 0:ユーザ間チャット、1:グループチャット
     * @param chtPartnerSid チャット相手のSID
     * @param cupSid ペアSID(ユーザ間チャットのみ使用)
     * @param msgSid メッセージSID
     * @return 対象のメッセージがチャット相手との間に存在しなければfalse
     * @throws SQLException SQL実行例外
     */
    private boolean __isMatchPartner(int kbn, int chtPartnerSid, int cupSid, long msgSid)
            throws SQLException {
        if (kbn == GSConstChat.CHAT_KBN_USER) {
            ChtUserDataDao cudDao = new ChtUserDataDao(con__);
            ChtUserDataModel cudMdl = cudDao.select(msgSid);
            if (cudMdl == null || cudMdl.getCupSid() != cupSid) {
                return false;
            }
        } else if (kbn == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
            ChtGroupDataModel cgdMdl = cgdDao.select(msgSid);
            if (cgdMdl == null || cgdMdl.getCgiSid() != chtPartnerSid) {
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 未読取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @param usrSid ユーザSID
     * @throws SQLException SQLException
     */
    public void changeGrp(Cht010ParamModel paramMdl,
            int usrSid) throws SQLException {
        getUser(paramMdl, usrSid);
    }

    /**
     * <br>[機  能] 未読取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @throws SQLException SQLException
     */
    public void moreView(Cht010ParamModel paramMdl) throws SQLException {

        ChatDao chtDao = new ChatDao(con__);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        UDate lastDate = new UDate();
        if (!StringUtil.isNullZeroStringSpace(paramMdl.getCht010MidokuLastDate())) {
            lastDate.setTimeStamp(paramMdl.getCht010MidokuLastDate());
        }
        boolean onlyNoRead = (paramMdl.getCht010TimelineDspOnlyNoRead() > 0);
        ArrayList<ChatMidokuModel> list =
                chtDao.getTimelineList(sessionUsrSid, lastDate, onlyNoRead);

        paramMdl.setCht010MidokuList(list);
        //もっとみる表示チェック
        int cnt = chtDao.getTimelineListCount(sessionUsrSid, lastDate, onlyNoRead);
        if (cnt > list.size()) {
            paramMdl.setCht010MoreView(1);
        }
    }

    /**
     * <br>[機  能] グループ名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @throws SQLException SQLException
     */
    public void getGroupName(Cht010ParamModel paramMdl) throws SQLException {

        ChtGroupInfDao cgiDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel cgiMdl = cgiDao.select(paramMdl.getCht010SelectPartner());
        ChatInformationModel cifMdl = new ChatInformationModel();
        cifMdl.setChatName(cgiMdl.getCgiName());
        paramMdl.setCht010ChtInfMdl(cifMdl);
    }

    /**
     * <br>[機  能] Enter送信更新
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht010ParamModel
     * @throws SQLException SQLException
     */
    public void enterSend(Cht010ParamModel paramMdl) throws SQLException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int enterKbn = paramMdl.getCht010EnterSendFlg();
        ChtPriConfDao cpcDao = new  ChtPriConfDao(con__);
        if (cpcDao.updateEnter(enterKbn, sessionUsrSid) == 0) {
            ChtBiz chtBiz = new ChtBiz(con__);
            ChtPriConfModel userMdl = chtBiz.getChtUconf(sessionUsrSid);
            userMdl.setCpcEnterFlg(enterKbn);
            cpcDao.insert(userMdl);
        }
    }

    /**
     * <br>[機  能] チャットの投稿を行う
     * <br>[解  説] DB登録しWebSocketで送信する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon コントローラ
     * @param binList バイナリリスト
     * @throws Exception 実行例外
     * @return ret メッセージSID
     */
    public List<Long> sendMessage(Cht010ParamModel paramMdl,
            MlCountMtController cntCon, List<String> binList)
    throws Exception {

        log__.debug("START");

        List<Long> ret = new ArrayList<Long>();
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        long cdSid = -1;
        List<ChtUserDataModel> cudList = null;
        List<ChtGroupDataModel> cgdList = null;
        // DB保管部 -------------------------------------------------------------------------
        con__.setAutoCommit(false);

        ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con__);
        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            int nPair = -1;
            /*ユーザ間チャット*/
            ChtUserPairCreateBiz cupBiz = new ChtUserPairCreateBiz();
            nPair = cupBiz.getCupSidAutoCreate(reqMdl__, con__, sessionUsrSid,
                                            paramMdl.getCht010SelectPartner());
            //ユーザCHT_USER_DATAにデータを登録する
            ChtUserDataDao cudDao = new ChtUserDataDao(con__);

            cudList = __createSendUserData(paramMdl, cntCon, nPair, binList);
            List<Long> cudSidList = new ArrayList<Long>();
            if (cudList.size() > 0) {
                for (ChtUserDataModel cudMdl : cudList) {
                    cudDao.insert(cudMdl);
                    cdSid = cudMdl.getCudSid();
                    cudSidList.add(cudMdl.getCudSid());
                }
            }

            //ユーザCHT_USER_VIEWにdeleteinsertする
            ChtUserViewDao cuvDao = new ChtUserViewDao(con__);
            cuvDao.delete(nPair, sessionUsrSid);

            ChtUserViewModel cuvMdl = new ChtUserViewModel();
            cuvMdl.setCupSid(nPair);
            cuvMdl.setCuvUid(sessionUsrSid);
            cuvMdl.setCudSid(cdSid);
            cuvMdl.setCuvViewcnt(cudDao.countTarget(nPair, cdSid));
            cuvDao.insert(cuvMdl);

            //チャット投稿情報のデータ使用量を登録
            usedDataBiz.insertChtDataSize(cudSidList, ChtUsedDataBiz.TYPE_USER, true);
        } else {
            /*グループチャット*/
            //CHT_GROUP_DATAにデータを登録する
            ChtGroupDataDao grpDataDao = new ChtGroupDataDao(con__);
            cgdList =  __createSendGroupData(paramMdl, cntCon, binList);
            List<Long> cgdSidList = new ArrayList<Long>();
            if (cgdList.size() > 0) {
                for (ChtGroupDataModel cgdMdl : cgdList) {
                    grpDataDao.insert(cgdMdl);
                    cdSid = cgdMdl.getCgdSid();
                    cgdSidList.add(cgdMdl.getCgdSid());
                }
            }

            //CHT_GROUP_VIEWのデータをdeleteinsertする
            ChtGroupViewDao cgvDao = new ChtGroupViewDao(con__);
            cgvDao.delete(paramMdl.getCht010SelectPartner(), sessionUsrSid);
            ChtGroupViewModel cgvMdl = new ChtGroupViewModel();
            cgvMdl.setCgiSid(paramMdl.getCht010SelectPartner());
            cgvMdl.setCgvUid(sessionUsrSid);
            cgvMdl.setCgdSid(cdSid);
            cgvMdl.setCgvViewcnt(
                    grpDataDao.countTarget(
                            paramMdl.getCht010SelectPartner(),
                            cdSid));

            //チャット投稿情報のデータ使用量を登録
            usedDataBiz.insertChtDataSize(cgdSidList, ChtUsedDataBiz.TYPE_GROUP, true);

            cgvDao.insert(cgvMdl);
        }
        //統計データ用の登録処理
        ChtLogCountDao clcDao =  new ChtLogCountDao(con__);
        ChtLogCountModel clcMdl = new ChtLogCountModel();
        clcMdl.setClcChatKbn(paramMdl.getCht010SelectKbn());
        clcMdl.setClcUsrSid(sessionUsrSid);
        UDate now = new UDate();
        clcMdl.setClcDate(now);
        clcDao.insert(clcMdl);

        con__.setAutoCommit(true);

        // WebSocketで送信 -------------------------------------------------------------------------
        int kbn = paramMdl.getCht010SelectKbn();
        int selectSid = paramMdl.getCht010SelectPartner();

        if (cudList != null) {
            boolean pushFlg = true;
            for (ChtUserDataModel cudMdl : cudList) {
                ArrayList<ChatMessageModel> cmList =
                        messageDisp(paramMdl, cntCon, cudMdl.getCudSid());
                __sendMessageWS(selectSid, kbn, cmList, pushFlg);
                pushFlg = false;
            }

        }
        if (cgdList != null) {
            boolean pushFlg = true;
            for (ChtGroupDataModel cgdMdl : cgdList) {
                ArrayList<ChatMessageModel> cmList =
                        messageDisp(paramMdl, cntCon, cgdMdl.getCgdSid());
                __sendMessageWS(selectSid, kbn, cmList, pushFlg);
                pushFlg = false;
            }
        }

        log__.debug("End");
        return ret;
    }

    /**
     *
     * <br>[機  能] メッセージ送信処理
     * <br>[解  説] jsonを生成しwebSocketで送信
     * <br>[備  考]
     * @param selectSid チャットルームSID
     * @param kbn 区分
     * @param mesList メッセージ ＋ ひとつ前のメッセージ配列
     * @param pushFlg true:プッシュ通知する, false:プッシュ通知しない
     * @throws Exception 実行時例外
     */
    private void __sendMessageWS(int selectSid, int kbn,
            ArrayList<ChatMessageModel> mesList, boolean pushFlg) throws Exception {
        BaseUserModel smodel = reqMdl__.getSmodel();
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("plugin", "chat");
        jsonData.element("type", "message");
        jsonData.element("senderSid", String.valueOf(smodel.getUsrsid()));
        jsonData.element("selectSid", String.valueOf(selectSid));
        jsonData.element("selectKbn", String.valueOf(kbn));
        jsonData.element("size", 1);
        jsonData.element("sessionSid", smodel.getUsrsid());
        jsonData.element("messageSid_0", String.valueOf(mesList.get(0).getMessageSid()));
        jsonData.element("selectSid_0", String.valueOf(mesList.get(0).getSelectSid()));
        jsonData.element("usrSid_0", String.valueOf(mesList.get(0).getUsrSid()));
        jsonData.element("usrName_0", StringUtilHtml.transToHTmlPlusAmparsant(
                mesList.get(0).getUsrName()));
        jsonData.element("usrJkbn_0", String.valueOf(mesList.get(0).getUsrJkbn()));
        jsonData.element("usrUkoFlg_0", String.valueOf(mesList.get(0).getUsrUkoFlg()));
        jsonData.element("usrBinSid_0", String.valueOf(mesList.get(0).getUsrBinSid()));
        jsonData.element("usrPictKf_0", String.valueOf(mesList.get(0).getUsrPictKf()));
        //描画用(エスケープあり)
        jsonData.element("messageText_0", StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                mesList.get(0).getMessageText()));
        jsonData.element("binSid_0", String.valueOf(mesList.get(0).getBinSid()));
        jsonData.element("binFileName_0", StringUtilHtml.transToHTmlPlusAmparsant(
                mesList.get(0).getTmpFile().getBinFileName()));
        jsonData.element("binFileSizeDsp_0", mesList.get(0).getTmpFile().getBinFileSizeDsp());
        jsonData.element("messageKbn_0", String.valueOf(mesList.get(0).getMessageKbn()));
        jsonData.element("insertUid_0", String.valueOf(mesList.get(0).getInsertUid()));
        jsonData.element("insertDate_0", String.valueOf(mesList.get(0).getInsertDate()));
        jsonData.element("updateUid_0", String.valueOf(mesList.get(0).getUpdateUid()));
        jsonData.element("updateDate_0", String.valueOf(mesList.get(0).getUpdateDate()));
        jsonData.element("tmpFile_0", String.valueOf(mesList.get(0).getTmpFile()));
        jsonData.element("ownKidoku_0", String.valueOf(mesList.get(0).getOwnKidoku()));
        jsonData.element("partnerKidoku_0", String.valueOf(mesList.get(0).getPartnerKidoku()));
        jsonData.element("entryDay_0", String.valueOf(mesList.get(0).getEntryDay()));
        jsonData.element("entryTime_0", String.valueOf(mesList.get(0).getEntryTime()));
        jsonData.element("updateDay_0", String.valueOf(mesList.get(0).getUpdateDay()));
        jsonData.element("updateTime_0", String.valueOf(mesList.get(0).getUpdateTime()));
        jsonData.element("command", "add");
        jsonData.element("pushFlg", pushFlg);

        int hidukeFlg = 0;
        if (mesList.size() == 1
                || !mesList.get(0).getEntryDay().equals(mesList.get(1).getEntryDay())) {
            hidukeFlg = 1;
        }
        jsonData.element("hidukeFlg", String.valueOf(hidukeFlg));
        // WebSocket通信
        ChtWebSocketBiz wsBiz = new ChtWebSocketBiz(con__, reqMdl__);
        wsBiz.sendToWebSocket(jsonData);

    }

    /**
     * <br>[機  能] 画面再表示用データ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon コントローラ
     * @param messageSid メッセージSID
     * @throws Exception 実行例外
     * @return ret メッセージリスト
     */
    public ArrayList<ChatMessageModel> messageDisp(Cht010ParamModel paramMdl,
            MlCountMtController cntCon, long messageSid)
    throws Exception {

        log__.debug("START");
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        ChatDao cDao = new ChatDao(con__);
        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            ret = cDao.getUserMessage(paramMdl.getCht010SelectPartner(), messageSid, sessionUsrSid);
        } else {
            ret = cDao.getGroupMessage(paramMdl.getCht010SelectPartner(), messageSid);
        }
        log__.debug("End");
        return ret;
    }

    /**
     * <br>[機  能] お気に入り変更時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return エラーチェック
     * @throws Exception 実行例外
     */
    public boolean favoriteChange(Cht010ParamModel paramMdl)throws Exception {

        log__.debug("START");
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int chtPartnerSid = paramMdl.getCht010SelectPartner();
        int chtKbn = paramMdl.getCht010SelectKbn();

        // お気に入り対象の権限チェック
        if (!__isViewPartner(sessionUsrSid, chtPartnerSid, chtKbn)) {
            return false;
        }

        ChtFavoriteModel cfMdl = new ChtFavoriteModel();
        cfMdl.setChfUid(sessionUsrSid);
        cfMdl.setChfChatKbn(paramMdl.getCht010SelectKbn());
        cfMdl.setChfSid(paramMdl.getCht010SelectPartner());
        ChtFavoriteDao cfDao = new ChtFavoriteDao(con__);
        if (paramMdl.getCht010FavoriteFlg() == GSConstChat.CHAT_FAVORITE) {
            cfDao.insert(cfMdl);
        } else {
            cfDao.delete(cfMdl);
        }
        ChatDao chtDao = new ChatDao(con__);
        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            ArrayList<ChatUserInfModel> cmnUserList = chtDao.favoriteUserSelect(sessionUsrSid);
            paramMdl.setCht010FavoriteUser(cmnUserList);
        } else if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_GROUP) {
            ArrayList<ChatGroupInfModel> gInfMdlList
            = chtDao.favoriteGroupSelect(sessionUsrSid);
            paramMdl.setCht010FavoriteGroup(gInfMdlList);
        }
        log__.debug("End");
        return true;
    }

    /**
     * <br>[機  能] メッセージ削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void messageDelete(Cht010ParamModel paramMdl)throws Exception {

        log__.debug("START");
        // DB保管部 -------------------------------------------------------------------------
        int sessionUserSid = reqMdl__.getSmodel().getUsrsid();
        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            ChtUserDataDao cudDao = new ChtUserDataDao(con__);
            cudDao.updateStateKbn(paramMdl.getCht010MessageSid(),
                    GSConstChat.TOUKOU_STATUS_DELETE, sessionUserSid, "");
        } else if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
            cgdDao.updateStateKbn(paramMdl.getCht010MessageSid(),
                    GSConstChat.TOUKOU_STATUS_DELETE, sessionUserSid, "");
        }
        // WebSocketで送信 -------------------------------------------------------------------------

        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("plugin", "chat");

        //トランザクショントークン設定
        jsonData.element("type", "message");
        BaseUserModel smodel = reqMdl__.getSmodel();
        jsonData.element("senderSid", String.valueOf(smodel.getUsrsid()));
        jsonData.element("selectSid", String.valueOf(paramMdl.getCht010SelectPartner()));
        jsonData.element("selectKbn", String.valueOf(paramMdl.getCht010SelectKbn()));
        jsonData.element("messageSid", paramMdl.getCht010MessageSid());
        jsonData.element("command", "delete");

        // WebSocketへデータを送信
        ChtWebSocketBiz wsBiz = new ChtWebSocketBiz(con__, reqMdl__);
        wsBiz.sendToWebSocket(jsonData);


        log__.debug("End");
    }

    /**
     * <br>[機  能] メッセージ編集処理
     * <br>[解  説] DB更新後 WebSocket送信
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     * @return ChatMessageModel
     */
    public ChatMessageModel messageEdit(Cht010ParamModel paramMdl)throws Exception {

        log__.debug("START");
        // DB保管部 -------------------------------------------------------------------------
        int sessionUserSid = reqMdl__.getSmodel().getUsrsid();
        ChatMessageModel mdl = new ChatMessageModel();
        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            ChtUserDataDao cudDao = new ChtUserDataDao(con__);
            mdl = cudDao.updateStateKbn(paramMdl.getCht010MessageSid(),
                    GSConstChat.TOUKOU_STATUS_EDIT, sessionUserSid, paramMdl.getCht010Message());
        } else if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
            mdl = cgdDao.updateStateKbn(paramMdl.getCht010MessageSid(),
                    GSConstChat.TOUKOU_STATUS_EDIT, sessionUserSid, paramMdl.getCht010Message());
        }
        // WebSocketで送信 -------------------------------------------------------------------------

        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("plugin", "chat");
        jsonData.element("type", "message");
        jsonData.element("msgContent",
                StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                        String.valueOf(paramMdl.getCht010Message())));
        BaseUserModel smodel = reqMdl__.getSmodel();
        jsonData.element("senderSid", String.valueOf(smodel.getUsrsid()));
        jsonData.element("selectSid", String.valueOf(paramMdl.getCht010SelectPartner()));
        jsonData.element("selectKbn", String.valueOf(paramMdl.getCht010SelectKbn()));
        jsonData.element("updateDay", mdl.getUpdateDay());
        jsonData.element("updateTime", mdl.getUpdateTime());
        jsonData.element("messageSid", paramMdl.getCht010MessageSid());
        jsonData.element("command", "edit");

        // WebSocketへデータを送信
        ChtWebSocketBiz wsBiz = new ChtWebSocketBiz(con__, reqMdl__);
        wsBiz.sendToWebSocket(jsonData);


        log__.debug("End");
        return mdl;
    }

    /**
     * <br>[機  能] 添付追加
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @return メッセージSID
     * @throws Exception 実行例外
     */
    public List<Long> tempAdd(Cht010ParamModel paramMdl,
            MlCountMtController mtCon,
            String appRootPath,
            String tempDir)throws Exception {

        log__.debug("START");
        int sessionUserSid = reqMdl__.getSmodel().getUsrsid();
        List<Long> ret = new ArrayList<Long>();
        //添付ファイル情報の登録
        UDate now = new UDate();

//        String chtTempDir = getChtTempDir(reqMdl__, tempDir);
        CommonBiz cmnBiz = new CommonBiz();
        List<String> binSid = cmnBiz.insertBinInfo(con__, tempDir, appRootPath,
                mtCon, sessionUserSid, now);
        if (binSid.size() > 0) {
            ret = binSid.stream()
                    .map(sid -> Long.parseLong(sid))
                    .collect(Collectors.toList());
            
            sendMessage(paramMdl, mtCon, binSid);
        }
        log__.debug("End");
        return ret;
    }

    /**
     * <br>[機  能] 添付ファイル保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリパス
     * @return アンケート情報の添付ファイル保存先ディレクトリパス
     */
    public String getChtTempDir(RequestModel reqMdl, String tempDir) {
        //$GSTEMPDIR/chat/セッションID/ユーザSID/
        String saveDir = IOTools.replaceSlashFileSep(tempDir);
        if (!saveDir.endsWith("/")) {
            saveDir += "/";
        }
        saveDir += reqMdl.getSmodel().getUsrsid() + "/";

        return saveDir;
    }


    /**
     * <br>[機  能] グループ投稿情報の作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon コントローラ
     * @param binList 添付ファイルリスト
     * @throws Exception 実行例外
     * @return grpDataMdl チャットグループ投稿情報
     */
    private List<ChtGroupDataModel> __createSendGroupData(Cht010ParamModel paramMdl,
            MlCountMtController cntCon, List<String> binList)

    throws Exception {
        List<ChtGroupDataModel> ret = new ArrayList<ChtGroupDataModel>();
        List<String> msgList = new ArrayList<String>();
        if (binList.size() == 0) {
            String msg = paramMdl.getCht010Message();
            int start = 0;
            int maxLength = GSConstChat.MAX_LENGTH_MESSAGE;
            int end = maxLength;

            while (msg.length() > end) {
                String spr = msg.substring(start, end);
                if (!ValidateUtil.isSpaceOrKaigyou(spr)) {
                    msgList.add(spr);
                }
                start = end;
                end += maxLength;
            }
            String spr = msg.substring(start);
            msgList.add(spr);
        }
        Iterator<String> itMsg = msgList.iterator();
        Iterator<String> itBin = binList.iterator();
        while (itMsg.hasNext() || itBin.hasNext()) {

            String msg = null;
            if (itMsg.hasNext()) {
                msg = itMsg.next();
            }
            long binSid = -1;
            if (itBin.hasNext()) {
                binSid = NullDefault.getLong(itBin.next(), -1);
            }
            ChtGroupDataModel grpDataMdl = new ChtGroupDataModel();
            UDate now = new UDate();
            int userSid = reqMdl__.getSmodel().getUsrsid();
            long newId = cntCon.getSaibanNumber("chat", "grpMessageSid", userSid);
            grpDataMdl.setCgdAdate(now);
            grpDataMdl.setCgdAuid(userSid);
            grpDataMdl.setCgdEdate(now);
            grpDataMdl.setCgdEuid(userSid);
            grpDataMdl.setBinSid(binSid);
            grpDataMdl.setCgdSendUid(userSid);
            grpDataMdl.setCgdSid(newId);
            grpDataMdl.setCgdStateKbn(GSConstChat.TOUKOU_STATUS_NORMAL);
            grpDataMdl.setCgdText(msg);
            grpDataMdl.setCgiSid(paramMdl.getCht010SelectPartner());
            ret.add(grpDataMdl);
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザ投稿情報の作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon コントローラ
     * @param nPair ペアSID
     * @param binList 添付ファイルリスト
     * @throws Exception 実行例外
     * @return grpDataMdl チャットグループ投稿情報
     */
    private List<ChtUserDataModel> __createSendUserData(Cht010ParamModel paramMdl,
            MlCountMtController cntCon, int nPair, List<String> binList)
    throws Exception {
        List<ChtUserDataModel> ret = new ArrayList<ChtUserDataModel>();
        List<String> msgList = new ArrayList<String>();
        if (binList.size() == 0) {
            String msg = paramMdl.getCht010Message();
            int start = 0;
            int maxLength = GSConstChat.MAX_LENGTH_MESSAGE;
            int end = maxLength;

            while (msg.length() > end) {
                String spr = msg.substring(start, end);
                if (!ValidateUtil.isSpaceOrKaigyou(spr)) {
                    msgList.add(spr);
                }
                start = end;
                end += maxLength;
            }
            String spr = msg.substring(start);
            msgList.add(spr);
        }
        Iterator<String> itMsg = msgList.iterator();
        Iterator<String> itBin = binList.iterator();
        while (itMsg.hasNext() || itBin.hasNext()) {

            String msg = null;
            if (itMsg.hasNext()) {
                msg = itMsg.next();
            }
            long binSid = -1;
            if (itBin.hasNext()) {
                binSid = NullDefault.getLong(itBin.next(), -1);
            }
            ChtUserDataModel usrDataMdl = new ChtUserDataModel();
            UDate now = new UDate();
            int userSid = reqMdl__.getSmodel().getUsrsid();
            long newId = cntCon.getSaibanNumber("chat", "usrMessageSid", userSid);
            usrDataMdl.setCudSid(newId);
            usrDataMdl.setCupSid(0);
            usrDataMdl.setCudText(msg);
            usrDataMdl.setBinSid(binSid);
            usrDataMdl.setCudSendUid(userSid);
            usrDataMdl.setCudStateKbn(GSConstChat.TOUKOU_STATUS_NORMAL);
            usrDataMdl.setCudAdate(now);
            usrDataMdl.setCudAuid(userSid);
            usrDataMdl.setCudEdate(now);
            usrDataMdl.setCudEuid(userSid);
            usrDataMdl.setCupSid(nPair);

            ret.add(usrDataMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] グループ管理画面
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param adminFlg システムorプラグイン管理者権限
     * @param usrSid ユーザＳＩＤ
     * @throws Exception 実行例外
     */
    public void setGroupConf(
            Cht010ParamModel paramMdl,
            boolean adminFlg,
            int usrSid)
            throws Exception {
        log__.debug("groupConfInit");
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        // グループ情報一覧を取得
        int archiveFlg = paramMdl.getCht010GrpConfDspArcKbn();
        String keyword = paramMdl.getCht010GrpConfSearchKeyword();
        boolean allDsp = false;
        if (adminFlg) {
            int dspFlg = paramMdl.getCht010GrpConfAllDspKbn();
            if (dspFlg == GSConstChat.CHAT_CONF_DSP_ALL) {
                allDsp = true;
            }
        }
        List<ChtGroupInfModel> grpList
            = infDao.getGroupConfList(allDsp, usrSid, archiveFlg, keyword);
        paramMdl.setCht010GrpConfGroupList(grpList);
    }


    /**
     * 登録編集処理
     * @param paramMdl パラメータモデル
     * @param adminFlg システム管理者orプラグイン管理者
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     *
     * */
    public void setAddEditData(Cht010ParamModel paramMdl, boolean adminFlg, int usrSid)
            throws SQLException {
        log__.debug("init");
        //編集の場合、データ取得
        ChtBiz biz = new ChtBiz(con__, reqMdl__);
        ChtMemberBiz memberBiz = new ChtMemberBiz(con__);
        if (paramMdl.getCht010GrpConfProcMode() == GSConstChat.CHAT_MODE_EDIT
                ) {
            int cgiSid = paramMdl.getCht010GrpConfCgiSid();
            ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
            ChatInformationModel infMdl = infDao.selectChatGroupInf(cgiSid);
            __setChatInf(paramMdl, infMdl);
            ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
            List<ChtGroupUserModel> userList = userDao.select(cgiSid);
            ChatGroupComboModel chatGrpMdl = memberBiz.setChatGrp(userList);
            paramMdl.setCht010GrpConfMemberAdminSid(chatGrpMdl.getMemberAdminSid());
            paramMdl.setCht010GrpConfMemberGeneralSid(chatGrpMdl.getMemberGeneralSid());
            paramMdl.setCht010GrpConfCgiSid(cgiSid);
        } else {
            // grpId自動採番
            paramMdl.setCht010GrpConfGroupId(biz.getAutoGeneratedId());
            // 管理者メンバー
            paramMdl.setCht010GrpConfMemberAdminSid(
                    new String[] {String.valueOf(usrSid)});
        }
        if (adminFlg) {
            paramMdl.setAdminFlg(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminFlg(GSConst.USER_NOT_ADMIN);
        }

    }

    /**
     * グループ情報をパラメータモデルに設定
     * @param paramMdl パラメータモデル
     * @param infMdl ChatInformationModel
     * @throws SQLException SQL実行例外
     *
     * */
    private void __setChatInf(Cht010ParamModel paramMdl, ChatInformationModel infMdl)
            throws SQLException {
        paramMdl.setCht010GrpConfGroupId(infMdl.getChatId());
        paramMdl.setCht010GrpConfGroupName(infMdl.getChatName());
        paramMdl.setCht010GrpConfBiko(infMdl.getChatBiko());
        paramMdl.setCht010GrpConfCategory(infMdl.getCategorySid());
        paramMdl.setCht010GrpConfArchiveKbn(infMdl.getChatArchive());;
    }

    /**
     * <br>[機  能] 送信権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param edit メッセージ編集操作時はtrue
     * @return エラーメッセージ
     * @throws Exception 例外
     */
    public String sendCheck(Cht010ParamModel paramMdl, boolean edit)
                       throws Exception {
        log__.debug("insert");
        String ret = "";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        // グループ情報の登録
        int sessionSid = reqMdl__.getSmodel().getUsrsid();
        int selectSid = paramMdl.getCht010SelectPartner();
        int kbn = paramMdl.getCht010SelectKbn();

        // 編集または削除時
        long messageSid = paramMdl.getCht010MessageSid();
        if (messageSid > 0) {
            boolean existMsg = false;
            int senderSid = -1;
            int messageState = -1;
            long binSid = -1;
            if (kbn == GSConstChat.CHAT_KBN_USER) {
                ChtUserDataDao cudDao = new ChtUserDataDao(con__);
                ChtUserDataModel cudMdl  = cudDao.select((long) messageSid);
                if (cudMdl != null) {
                    existMsg = true;
                    senderSid = cudMdl.getCudSendUid();
                    messageState = cudMdl.getCudStateKbn();
                    binSid = cudMdl.getBinSid();
                }
            } else if (kbn == GSConstChat.CHAT_KBN_GROUP) {
                ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
                ChtGroupDataModel cgdMdl = cgdDao.select((long) messageSid);
                if (cgdMdl != null) {
                    existMsg = true;
                    senderSid = cgdMdl.getCgdSendUid();
                    messageState = cgdMdl.getCgdStateKbn();
                    binSid = cgdMdl.getBinSid();
                }
            }
            // メッセージが存在しない
            if (!existMsg) {
                return gsMsg.getMessage("cht.cht010.43");
            // 自分が送信したメッセージでない
            } else if (senderSid != sessionSid) {
                return gsMsg.getMessage("cht.cht010.44");
            // 削除済み
            } else if (messageState == GSConstChat.TOUKOU_STATUS_DELETE) {
                return gsMsg.getMessage("cht.cht010.45");
            // 添付ファイルメッセージの内容を編集しようとしている
            } else if (edit && binSid > -1) {
                return gsMsg.getMessage("cht.cht010.46");
            }
        }

        if (kbn == GSConstChat.CHAT_KBN_USER) {
            ChatDao cDao = new ChatDao(con__);
            //ユーザ削除チェック
            CmnUsrmInfModel cuiMdl = cDao.getUser(selectSid);
            if (cuiMdl == null || cuiMdl.getUsrJkbn() == GSConst.JTKBN_DELETE) {
                return gsMsg.getMessage("cht.cht010.07");
            }
            //特例アクセスチェック
            if (sessionSid != selectSid) {
                ChtSpaccessTargetDao cstDao = new ChtSpaccessTargetDao(con__);
                ArrayList<Integer> spList = cstDao.selectSid(selectSid);
                if (spList.size() != 0) {
                    ChtSpaccessPermitDao cspDao = new ChtSpaccessPermitDao(con__);
                    int cntSid = cspDao.selectCount(spList, sessionSid);
                    if (cntSid == 0) {
                        return gsMsg.getMessage("cht.cht010.08");
                    }
                }
            }
            //ユーザチャット制限チェック
            ChtBiz cBiz = new ChtBiz(con__, reqMdl__);
            ChtAdmConfModel aMdl = cBiz.getChtAconf();
            if (aMdl.getCacChatFlg() == GSConstChat.LIMIT_BETWEEN_USERS) {
                return gsMsg.getMessage("cht.cht010.05");
            }

            //プラグイン使用権限
            CommonBiz cmnBiz = new CommonBiz();
            List<String> menuPluginIdList = cmnBiz.getCanUsePluginIdList(con__, selectSid);
            if (!menuPluginIdList.isEmpty()) {
                boolean bAccess = false;
                for (String pId : menuPluginIdList) {
                    if (pId.equals(GSConstChat.PLUGIN_ID_CHAT)) {
                        bAccess = true;
                        break;
                    }
                }
                if (!bAccess) {
                    return gsMsg.getMessage("cht.cht010.39");
                }
            } else {
                return gsMsg.getMessage("cht.cht010.39");
            }

        } else {
            ChtGroupUserDao cguDao = new ChtGroupUserDao(con__);
            //グループメンバーチェック
            int cnt = cguDao.getAuthority(sessionSid, selectSid);
            if (cnt == 0) {
                return gsMsg.getMessage("cht.cht010.38");
            }
            //グループ存在チェック・アーカイブチェック
            ChtGroupInfDao cgiDao = new ChtGroupInfDao(con__);
            ChtGroupInfModel cgiMdl = cgiDao.select(selectSid);
            if (cgiMdl.getCgiDelFlg() == GSConstChat.CHAT_MODE_DELETE) {
                return gsMsg.getMessage("cht.cht010.37");
            } else if (cgiMdl.getCgiCompFlg() == GSConstChat.CHAT_ARCHIVE_MODE) {
                return gsMsg.getMessage("cht.cht010.06");
            }
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] ダウンロード権限チェック
     * <br>[解  説] 指定したメッセージと選択しているチャットSIDの組み合わせに不正がないことを確認する
     * <br>[備  考] 論理削除済みは有効として返す
     * @param paramMdl パラメータ情報
     * @param ikkatu 一括ダウンロードフラグ
     * @return 0 :ダウンロード可能 1:不正なアクセス 2：メッセージが論理削除
     * @throws SQLException SQL実行時例外
     */
    public int downloadCheck(Cht010ParamModel paramMdl, boolean ikkatu) throws SQLException {
        int sessionSid = reqMdl__.getSmodel().getUsrsid();
        int kbn = paramMdl.getCht010SelectKbn();

        int selectSid;
        if (kbn == GSConstChat.CHAT_KBN_GROUP) {
            selectSid = paramMdl.getCht010SelectPartner();
            ChtGroupUserDao cguDao = new ChtGroupUserDao(con__);
            //グループメンバーチェック
            int cnt = cguDao.getAuthority(sessionSid, selectSid);
            if (cnt == 0) {
                return 1;
            }
            //グループ存在チェック
            ChtGroupInfDao cgiDao = new ChtGroupInfDao(con__);
            ChtGroupInfModel cgiMdl = cgiDao.select(selectSid);
            if (cgiMdl.getCgiDelFlg() == GSConstChat.CHAT_MODE_DELETE) {
                return 1;
            }

        } else {
            ChtUserPairDao cupDao = new ChtUserPairDao(con__);
            selectSid = cupDao.select(sessionSid, paramMdl.getCht010SelectPartner());
            //不正なチャット相手を指定
            if (selectSid == 0) {
                return DOWNLOADCHECK_CODE_NONE;
            }
        }
        List<Long> mesSidList = new ArrayList<>();
        if (ikkatu) {
            if (paramMdl.getCht010AllTempSid() == null
                    || paramMdl.getCht010AllTempSid().length() == 0) {
                return DOWNLOADCHECK_CODE_NONE;
            }
            String sids = paramMdl.getCht010AllTempSid();
            String[] cgSids = sids.split(",");
            for (String sid : cgSids) {
                mesSidList.add(NullDefault.getLong(sid, -1));
            }
        } else {
            mesSidList.add(paramMdl.getCht010MessageSid());
        }
        /**指定したチャットルームSID添付ファイル付きチャット件数 */
        Map<Long, Integer> mesStateMap;
        if (kbn == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
            mesStateMap = cgdDao.selectBinMessageStateMap(mesSidList, selectSid);
        } else {
            ChtUserDataDao cudDao = new ChtUserDataDao(con__);
            mesStateMap = cudDao.selectBinMessageStateMap(mesSidList, selectSid);
        }

        //有効チャット件数が1件もない場合、ダウンロード不可（不正なメッセージを指定している）
        if (mesStateMap.size() <= 0) {
            return DOWNLOADCHECK_CODE_NONE;
        }
        for (Entry<Long, Integer> entry : mesStateMap.entrySet()) {
            if (entry.getValue() == GSConstChat.TOUKOU_STATUS_DELETE) {
                return DOWNLOADCHECK_CODE_DELEATED;
            }
        }
        return DOWNLOADCHECK_CODE_OK;
    }
    /**
     * <p> 相手ユーザおよびグループの閲覧権限チェック
     * @param sessionUsrSid セッションユーザSID
     * @param chtPartnerSid 相手ユーザ/グループSID
     * @param chtKbn 0：ユーザ、1：グループ
     * @return 正常終了：true、権限エラー：false
     * @throws SQLException SQL実行例外
     * */
    private boolean __isViewPartner(int sessionUsrSid, int chtPartnerSid, int chtKbn)
            throws SQLException {
        // ユーザの場合、相手ユーザの権限チェック
        if (chtKbn == GSConstChat.CHAT_KBN_USER) {
            // ユーザ削除チェック
            ChatDao cDao = new ChatDao(con__);
            CmnUsrmInfModel cuiMdl = cDao.getUser(chtPartnerSid);
            if (cuiMdl == null) {
                return false;
            }
            // プラグイン使用権限チェック
            CommonBiz cmnBiz = new CommonBiz();
            List<String> menuPluginIdList = cmnBiz.getCanUsePluginIdList(con__, chtPartnerSid);
            if (!menuPluginIdList.isEmpty()) {
                boolean bAccess = false;
                for (String pId : menuPluginIdList) {
                    if (pId.equals(GSConstChat.PLUGIN_ID_CHAT)) {
                        bAccess = true;
                        break;
                    }
                }
                if (!bAccess) {
                    return false;
                }
            } else {
                return false;
            }
        //グループの場合閲覧権限チェック
        } else if (chtKbn == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupUserDao cguDao = new ChtGroupUserDao(con__);
            if (cguDao.getAuthority(sessionUsrSid, chtPartnerSid) == 0) {
                return false;
            }
            ChtGroupInfDao cgiDao = new ChtGroupInfDao(con__);
            ChtGroupInfModel cgiMdl = cgiDao.select(chtPartnerSid);
            if (cgiMdl == null
                    || cgiMdl.getCgiDelFlg() == GSConstChat.CHAT_MODE_DELETE) {
                return false;
            }
        }
        return true;
    }


    /**
     * <br>[機  能] 登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param adminFlg システム・プラグイン管理者判定
     * @throws Exception 例外
     */
    public void insertGroup(Cht010ParamModel paramMdl,
                       int usrSid,
                       MlCountMtController cntCon,
                       boolean adminFlg)
                       throws Exception {
        log__.debug("insert");
        // グループ情報の登録
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel insertinfMdl
            = createGroupInfMdl(paramMdl, usrSid, cntCon, GSConstChat.CHAT_MODE_ADD, adminFlg);
        infDao.insert(insertinfMdl);

        ChtGroupDataSumDao cgsDao = new ChtGroupDataSumDao(con__);
        ChtGroupDataSumModel cgsModel = new ChtGroupDataSumModel();
        cgsModel.setCgiSid(insertinfMdl.getCgiSid());
        cgsDao.insert(cgsModel);

        // グループユーザ情報の登録
        ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
        List<ChtGroupUserModel> memberList = __createUseMdlList(paramMdl, usrSid);
        for (ChtGroupUserModel model:memberList) {
            userDao.insert(model);
        }
    }

    /**
     * <br>[機  能] 編集処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param adminFlg システム・プラグイン管理者判定
     * @throws Exception 例外
     */
    public void updateGroup(Cht010ParamModel paramMdl,
                       int usrSid,
                       MlCountMtController cntCon,
                       boolean adminFlg)
                       throws Exception {
        log__.debug("insert");
        // グループ情報の編集
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel insertinfMdl
                = createGroupInfMdl(paramMdl, usrSid, cntCon, GSConstChat.CHAT_MODE_EDIT, adminFlg);
        infDao.updateGrp(insertinfMdl, adminFlg);

        // グループユーザ情報の編集

        // 編集前メンバーの取得
        ChtMemberBiz memberBiz = new ChtMemberBiz(con__);
        String[] oldMembers =  memberBiz.getOldMembers(paramMdl.getCht010GrpConfCgiSid());
        paramMdl.setCht010OldMemberSids(oldMembers);

        // 削除して登録
        ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
        userDao.delete(paramMdl.getCht010GrpConfCgiSid());
        List<ChtGroupUserModel> memberList = __createUseMdlList(paramMdl, usrSid);
        for (ChtGroupUserModel model:memberList) {
            userDao.insert(model);
        }
        //閲覧情報の削除
        // 現在のメンバーを取得
        String[] members = memberBiz.createMemberUserSid(
                paramMdl.getCht010GrpConfMemberAdminSid(),
                paramMdl.getCht010GrpConfMemberGeneralSid());
        List<Integer> userList = new ArrayList<Integer>();

        for (String member: members) {
            userList.add(NullDefault.getInt(member, -1));
        }
        if (!userList.isEmpty()) {
            ChtBiz cBiz = new ChtBiz(con__);
            cBiz.groupViewDataDelete(paramMdl.getCht010GrpConfCgiSid(), userList);
        }

    }



    /**
     * <br>[機  能] 登録・更新用のmodel作成を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param mode 登録編集モード
     * @param adminFlg システム・プラグイン管理者判定
     * @return RngTemplateCategoryModel 登録model
     * @throws Exception 例外
     */
    public ChtGroupInfModel createGroupInfMdl(Cht010ParamModel paramMdl,
                                                int usrSid,
                                                MlCountMtController cntCon,
                                                int mode,
                                                boolean adminFlg)
                                                throws Exception {

        UDate date = new UDate();
        ChtGroupInfModel model = new ChtGroupInfModel();
        //登録
        if (mode == GSConstChat.CHAT_MODE_ADD) {
            //グループSID採番
            int cgiSid = (int) cntCon.getSaibanNumber(GSConstChat.SBNSID_CHAT,
                                                       GSConstChat.SBNSID_SUB_CHAT_GROUP,
                                                       usrSid);
            model.setCgiSid(cgiSid);
            model.setCgiCompFlg(GSConstChat.CHAT_ARCHIVE_NOT_MODE);
            paramMdl.setCht010GrpConfCgiSid(cgiSid);
        //更新
        } else  {
            int cgiSid = paramMdl.getCht010GrpConfCgiSid();
            model.setCgiSid(cgiSid);
            model.setCgiCompFlg(paramMdl.getCht010GrpConfArchiveKbn());
        }
        model.setCgiId(paramMdl.getCht010GrpConfGroupId());
        model.setCgiName(paramMdl.getCht010GrpConfGroupName());
        if (adminFlg) {
            model.setChcSid(paramMdl.getCht010GrpConfCategory());
        } else {
            // システム・プラグイン管理者権限を持たないなら、一般ユーザのカテゴリ
            model.setChcSid(GSConstChat.CHAT_CHC_SID_GENERAL);
        }
        model.setCgiContent(paramMdl.getCht010GrpConfBiko());
        model.setCgiAdate(date);
        model.setCgiEdate(date);
        model.setCgiAuid(usrSid);
        model.setCgiEuid(usrSid);
        return model;
    }


    /**
    *
    * <br>[機  能]メンバーリストを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
     * @param usrSid ユーザSID
    * @return ChtGroupUserModel
    */
   private ArrayList<ChtGroupUserModel> __createUseMdlList(
           Cht010ParamModel paramMdl,
           int usrSid) {
       log__.debug("__createUseMdl");
       UDate date = new UDate();
       //使用制限対象ユーザ・グループのSIDを取得
       ArrayList<ChtGroupUserModel> useMdlList = new ArrayList<ChtGroupUserModel>();
       // 管理者
       String[] adminList = paramMdl.getCht010GrpConfMemberAdminSid();
       for (String admin:adminList) {
           ChtGroupUserModel mdl = new ChtGroupUserModel();
           if (admin.startsWith("G")) {
               int sid = NullDefault.getInt(admin.substring(1), -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_GROUP);
               mdl.setCguSelectSid(sid);
           } else {
               int sid = NullDefault.getInt(admin, -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_USER);
               mdl.setCguSelectSid(sid);
           }
           mdl.setCgiSid(paramMdl.getCht010GrpConfCgiSid());
           mdl.setCguAdminFlg(1);
           mdl.setCguAdate(date);
           mdl.setCguEdate(date);
           mdl.setCguAuid(usrSid);
           mdl.setCguEuid(usrSid);
           useMdlList.add(mdl);
       }
       // 一般
       String[] generalList = paramMdl.getCht010GrpConfMemberGeneralSid();
       for (String general:generalList) {
           ChtGroupUserModel mdl = new ChtGroupUserModel();
           if (general.startsWith("G")) {
               int sid = NullDefault.getInt(general.substring(1), -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_GROUP);
               mdl.setCguSelectSid(sid);
           } else {
               int sid = NullDefault.getInt(general, -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_USER);
               mdl.setCguSelectSid(sid);
           }
           mdl.setCgiSid(paramMdl.getCht010GrpConfCgiSid());
           mdl.setCguAdminFlg(0);
           mdl.setCguAdate(date);
           mdl.setCguEdate(date);
           mdl.setCguAuid(usrSid);
           mdl.setCguEuid(usrSid);
           useMdlList.add(mdl);
       }

       return useMdlList;
   }

   /**
    * <br>[機  能] グループチャットを論理削除します
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータ情報
    * @param usrSid ユーザSID
    * @throws Exception 例外
    */
   public void logicDeleteChtGroup(Cht010ParamModel paramMdl, int usrSid) throws Exception {
       log__.debug("deleteGroupChat");
       UDate now = new UDate();
       ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
       infDao.updateGroupLocgicDelete(paramMdl.getCht010GrpConfCgiSid(), usrSid, now);

   }

   /**
    * [機 能] グループ管理のコンボ・チェックボックスを設定します。
    * [解 説]
    * [備 考]
    * @param paramMdl Cht010ParamModel
    * @param usrSid ユーザSID
    * @throws SQLException SQL実行時例外
    * @throws Exception 実行時例外
    */
   public void setGrpConfFormTag(Cht010ParamModel paramMdl, int usrSid)
           throws SQLException, Exception {
       log__.debug("タグ設定");
       ChtBiz biz = new ChtBiz(con__, reqMdl__);
       // カテゴリ設定
       paramMdl.setCht010GrpConfCategoryList(biz.getCategory(false));
   }
   /**
    * <br>[機  能] 添付一括ダウンロード用のZIPデータを作成する。
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param appRootPath アプリケーションルートパス
    * @param tempDir テンポラリディレクトリ
    * @throws SQLException SQL実行例外
    * @throws IOException 入出力時例外
    * @throws IOToolsException ファイルアクセス時例外
    * @throws TempFileException 添付ファイルUtil内での例外
    * @throws CSVException CSV出力時例外
    * @return [0]テンポラリディレクトリ内保存ファイル名
    *              [1]ダウンロード時保存時の表示ファイル名
    */
   public String [] makeAllTmpFile(Cht010ParamModel paramMdl, String appRootPath, String tempDir)
                   throws SQLException, IOException, IOToolsException,
                   TempFileException, CSVException {

       //添付ダウンロードデータを取得する
       ChatAllTempDataModel allDataMdl = __getAllTmpData(paramMdl, reqMdl__.getDomain());
       if (allDataMdl.getAllFileList() == null || allDataMdl.getAllFileList().size() == 0) {
           String [] ret = null;
           return  ret;
       }

       //フォルダ名のエスケープ処理
       String escTitle = FileNameUtil.getTempFileNameTabRemoveNoExt(allDataMdl.getTopTitle());
       String srmEscTitle = escTitle.trim();
       String chtTitle = null;
       GsMessage gsMsg = new GsMessage(reqMdl__);
       if (StringUtil.isNullZeroStringSpace(srmEscTitle)) {
           chtTitle = gsMsg.getMessage("cht.allTmep.download");
       } else {
           chtTitle = srmEscTitle;
       }

       List<CmnBinfModel> allFileList = allDataMdl.getAllFileList();

       //ディレクトリ内にタイトルのフォルダを作成
       File directoryTop = new File(tempDir, chtTitle);
       directoryTop.mkdir();

       CommonBiz cmnBiz = new CommonBiz();

       //出力対象ディレクトリパス
       String outFileDir = tempDir + "/" + chtTitle;

       if (allFileList != null && allFileList.size() > 0) {
           ArrayList<String> fileNameList = new ArrayList<String>();
           for (CmnBinfModel binMdl : allFileList) {
               String usrTmpDir = outFileDir + "/";
               //テンポラリディレクトリに保存できる字数になるようにファイル名のをカットする
               String escFileName =  __fileLengthCheck(
                       usrTmpDir, binMdl.getBinFileName(),
                       binMdl.getBinFileExtension(), chtTitle, fileNameList);
               fileNameList.add(escFileName);

               //ZIPで解凍できるバイト数のタイトルに変換する
               escFileName = __zipBufferCheck(
                       escFileName, chtTitle, binMdl.getBinFileExtension());

               binMdl.setBinFileName(escFileName);
               cmnBiz.saveTempFile(binMdl, appRootPath, usrTmpDir);
           }

       }

       String saveFilePath = tempDir + "/" + "chtAllExp.zip";
       ZipUtil.zipDir(outFileDir, saveFilePath);
       IOTools.deleteDir(outFileDir);
       String bookName = chtTitle + ".zip";
       String [] ret = {saveFilePath, bookName};
       return ret;
   }

   /**
    * <br>[機  能] 添付一括ダウンロード用のZIPデータを作成する。
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param domain ドメイン
    * @return 添付ファイル一括ダウンロード用のデータモデル
    * @throws SQLException SQL実行例外
    * @throws TempFileException 添付ファイルUtil内での例外
    */
   private ChatAllTempDataModel __getAllTmpData(
           Cht010ParamModel paramMdl,
           String domain
           )
           throws SQLException, TempFileException {

       ChatAllTempDataModel ret = new ChatAllTempDataModel();


       //topのファイル名を取得する
       String title = "";
       if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
           UserBiz usrBiz = new UserBiz();
           List<Integer> userSid = new ArrayList<Integer>();
           userSid.add(paramMdl.getCht010SelectPartner());
           List<CmnUsrmInfModel> uList = usrBiz.getUserList(con__, userSid);
           if (uList.size() != 0) {
               title = uList.get(0).getUsiSei() + uList.get(0).getUsiMei();
           } else {
               title = "NoUser";
           }
       } else {
           ChtGroupInfDao iDao = new ChtGroupInfDao(con__);
           ChtGroupInfModel iMdl = iDao.select(paramMdl.getCht010SelectPartner());
           title = iMdl.getCgiName();
       }
       ret.setTopTitle(title);
       //添付ファイル情報をセット
       CommonBiz cmnBiz = new CommonBiz();
       String sids = paramMdl.getCht010AllTempSid();
       String[] cgSids = sids.split(",");
       if (cgSids != null && cgSids.length != 0) {

           ArrayList<String> binsList = new ArrayList<String>();
           if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
               ChtUserDataDao cudDao = new ChtUserDataDao(con__);
               binsList = cudDao.getBinSid(cgSids);
           } else {
               ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
               binsList = cgdDao.getBinSid(cgSids);
           }
           String[] binSids = binsList.toArray(new String[binsList.size()]);
           List<CmnBinfModel> binList = cmnBiz.getBinInfo(con__, binSids, domain);
           ret.setAllFileList(binList);
       }

       return ret;
   }
   /**
    *
    * <br>[機  能] バイナリSIDを取得
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl 画面パラメータ
    * @return バイナリSID
    * @throws SQLException SQL実行時例外
    */
   public long getDownloadBinSid(Cht010ParamModel paramMdl) throws SQLException {
       ArrayList<String> binsList = new ArrayList<String>();
       String[] cgSids = new String[] {
               String.valueOf(paramMdl.getCht010MessageSid())
                       };
       if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
           ChtUserDataDao cudDao = new ChtUserDataDao(con__);
           binsList = cudDao.getBinSid(cgSids);
       } else {
           ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
           binsList = cgdDao.getBinSid(cgSids);
       }
       if (binsList.size() <= 0) {
           return -1;
       }
       return NullDefault.getLong(binsList.get(0), -1);
   }
   /**
    * <br>[機  能] テンポラリディレクトリに保存できるようにファイル名をカットとする。
    * <br>[解  説] テンポラリ + ファイル名 + 拡張子 + (一括ダウンロード時のファイル名) <= 255 になるようにする。
    *                    直接ZIPを開いたときに一括ダウンロード時のファイル名が付与される。
    * <br>[備  考]
    * @param tempdir テンポラリディレクトリ
    * @param fileName ファイル名
    * @param fileExtension 拡張子
    * @param topFolderName 一括ダウンロード時のファイル名
    * @param fileNameList ファイル名リスト
    * @return 字数カットされたファイル名
    */
   private String __fileLengthCheck(String tempdir, String fileName, String fileExtension,
           String topFolderName, List<String> fileNameList) {

       //最大文字数
       int maxLength = 255;

       String editFileName = fileName;
       //もし同一名ファイルが存在する場合(N)と末尾に追加
       int nN = 1;
       boolean bCheck = true;
       String numFileName = editFileName;
       while (bCheck) {
           bCheck = false;
           for (String filename : fileNameList) {
               if (filename.equals(numFileName)) {
                   nN += 1;
                   numFileName = StringUtil.trimRengeString(editFileName,
                           fileName.length() - fileExtension.length());
                   numFileName = numFileName + "(" + nN + ")" + fileExtension;
                   bCheck = true;
                   break;
               }
           }
       }
       editFileName = numFileName;

       String fullPath = tempdir + editFileName;
       if (fullPath.length() <= maxLength) {
           return editFileName;
       }

       //可能文字数
       int okLength = maxLength - tempdir.length() - fileExtension.length();

       //カット
       String escName = StringUtil.trimRengeString(editFileName, okLength);
       //拡張子を追加
       escName = escName + fileExtension;

       return escName;
   }

   /**
    * <br>[機  能] ZIPで解凍できるファイル名にする。
    * <br>[解  説] ディレクトリ名+ファイル名が259バイト以内に収まるようにする
    * <br>[備  考]
    * @param fileName ファイル名
    * @param topDirectName 最上位のフォルダ名(チャットタイトル名)
    * @param fileExtension 拡張子名
    * @return 259バイト以内にカットしたファイル名
    * @throws UnsupportedEncodingException URLのエンコード時エラー
    */
   private String __zipBufferCheck(String fileName, String topDirectName,
           String fileExtension) throws UnsupportedEncodingException {

       String cpFileName = fileName;
       String escFileName = null;
       //使用済バイト数 チャットタイトル + /
       int usedzipPathByteNoExt =
               topDirectName.getBytes("Windows-31J").length + 1;

       //使用可能バイト数 拡張子有り
       int zipPathByteExt = 0;
       if (fileExtension == null) {
           zipPathByteExt = 259 - usedzipPathByteNoExt;
       } else {
           zipPathByteExt = 259 - (usedzipPathByteNoExt
                   + fileExtension.getBytes("Windows-31J").length);
       }

       if (usedzipPathByteNoExt + fileName.getBytes("Windows-31J").length > 259) {
           String formatFileName = "";
           int cntByte = 0;
           while (cntByte < zipPathByteExt) {
               String value = cpFileName.substring(0, 1);
               cntByte += value.getBytes("Windows-31J").length;
               formatFileName += value;
               cpFileName = cpFileName.substring(1);
               if (zipPathByteExt - cntByte == 1) {
                   break;
               }
           }
           if (fileExtension == null) {
               escFileName = formatFileName;
           } else {
               escFileName = formatFileName + fileExtension;
           }
       } else {
           escFileName = fileName;
       }
       return escFileName;
   }


   /**
    * [機 能] 作成・編集時グループリストに表示するか
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータ情報
    * @param adminFlg システムorプラグイン管理者権限
    * @param usrSid ユーザＳＩＤ
    * @return 表示判定
    * @throws Exception 実行例外
    */
   public boolean chkDspGroupList(
           Cht010ParamModel paramMdl,
           boolean adminFlg,
           int usrSid)
           throws Exception {
       boolean ret = false;
       ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
       // グループ情報一覧を取得
       int archiveFlg = paramMdl.getCht010GrpConfDspArcKbn();
       String keyword = paramMdl.getCht010GrpConfSearchKeyword();
       boolean allDsp = false;
       if (adminFlg) {
           int dspFlg = paramMdl.getCht010GrpConfAllDspKbn();
           if (dspFlg == GSConstChat.CHAT_CONF_DSP_ALL) {
               allDsp = true;
           }
       }
       ret =  infDao.chkGrpConfGroupDsp(allDsp, usrSid, archiveFlg, keyword,
               paramMdl.getCht010GrpConfCgiSid());
       return ret;
   }

   /**
    * 作成したチャットグループに関するパラメータを生成
    * @param paramMdl パラメータモデル
    * @throws SQLException SQL実行例外
    * */
   public void createNewGroupParam(Cht010ParamModel paramMdl) throws SQLException {

       // POST通信で送られてきたメンバーSID
       ChtMemberBiz memberBiz = new ChtMemberBiz(con__);
       // 管理者メンバーSID
       String[] adminMembers = paramMdl.getCht010GrpConfMemberAdminSid();
       // 一般メンバーSID
       String[] generalMembers = paramMdl.getCht010GrpConfMemberGeneralSid();
       // メンバーユーザSID
       String[] members = memberBiz.createMemberUserSid(adminMembers, generalMembers);

       // パラメータのセット
       paramMdl.setCht010MemberUserSids(members);
       // 編集時はメッセージ件数も取得
       if (paramMdl.getCht010GrpConfProcMode() == GSConstChat.CHAT_MODE_EDIT) {
           ChtGroupDataDao cgdDao = new ChtGroupDataDao(con__);
           int msgCnt = cgdDao.getExistMessageCount(paramMdl.getCht010GrpConfCgiSid());
           paramMdl.setCht010MessageCount(msgCnt);
           // メッセージがある場合は最新投稿日時を取得
           if (msgCnt > 0) {
               UDate lastDate = cgdDao.selectLastEntryTime(paramMdl.getCht010GrpConfCgiSid());
               paramMdl.setCht010MessageLastDate(NullDefault.getString(lastDate.toString(), ""));
           } else {
               paramMdl.setCht010MessageLastDate("");
           }
       }
   }

   /**
     * <br>[機  能] グループ情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Cht010ParamModel
     * @return jsonData
     */
    private JSONObject __jsonGrpConfInf(JSONObject jsonData,
            Cht010ParamModel paramMdl) {
        jsonData.element("cht010GrpConfCgiSid", paramMdl.getCht010GrpConfCgiSid());
        jsonData.element("cht010GrpConfGroupId",
                StringUtilHtml.transToHTmlPlusAmparsant(
                        paramMdl.getCht010GrpConfGroupId())
                );
        jsonData.element("cht010GrpConfGroupName",
                StringUtilHtml.transToHTmlPlusAmparsant(
                        paramMdl.getCht010GrpConfGroupName())
                );
        jsonData.element("cht010GrpConfCategory", paramMdl.getCht010GrpConfCategory());
        jsonData.element("cht010GrpConfBiko",
                StringUtilHtml.transToHTmlForTextArea(
                        paramMdl.getCht010GrpConfBiko())
                );
        jsonData.element("cht010GrpConfArchiveKbn", paramMdl.getCht010GrpConfArchiveKbn());
        return jsonData;
    }

    /**
     * <br>[機  能] グループ一覧情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param list グループ情報
     * @return jsonData
     */
    private JSONObject __jsonGrpConfGrpList(JSONObject jsonData,
            List<ChtGroupInfModel> list) {
        jsonData.element("size", list.size());
        for (int nIdx = 0; nIdx < list.size(); nIdx++) {
            jsonData.element("cgiSid_"
        + nIdx, String.valueOf(list.get(nIdx).getCgiSid()));
            jsonData.element("groupName_"
        + nIdx, StringUtilHtml.transToHTmlPlusAmparsant(
                String.valueOf(list.get(nIdx).getCgiName())));
            jsonData.element("archiveFlg_"
        + nIdx, String.valueOf(list.get(nIdx).getCgiCompFlg()));
        }

        return jsonData;
    }

    /**
     * <br>[機  能] カテゴリ情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param categoryLabel カテゴリ情報
     * @return jsonData
     */
    private JSONObject __jsonGrpConfCategory(JSONObject jsonData,
            List<LabelValueBean> categoryLabel) {
        jsonData.element("category_size", categoryLabel.size());
        for (int nIdx = 0; nIdx < categoryLabel.size(); nIdx++) {
            jsonData.element("categorySid_"
        + nIdx, String.valueOf(categoryLabel.get(nIdx).getValue()));
            jsonData.element("categoryName_"
        + nIdx, String.valueOf(StringUtilHtml.transToHTmlPlusAmparsant(
                categoryLabel.get(nIdx).getLabel())));
        }
        return jsonData;
    }

    /**
     * <br>[機  能] メンバー設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramModel パラメータ情報
     * @return jsonData
     */
    private JSONObject __jsonGrpConfMember(JSONObject jsonData,
            Cht010ParamModel paramModel) {

        String[] aminSid = paramModel.getCht010GrpConfMemberAdminSid();
        String[] generalSid = paramModel.getCht010GrpConfMemberGeneralSid();
        jsonData.element("adminmemberSid_size", aminSid.length);
        for (int nIdx = 0; nIdx < aminSid.length; nIdx++) {
            jsonData.element("adminmemberSid_"
        + nIdx, String.valueOf(aminSid[nIdx]));
        }
        jsonData.element("generalmemberSid_size", generalSid.length);
        for (int nIdx = 0; nIdx < generalSid.length; nIdx++) {
            jsonData.element("generalmemberSid_"
        + nIdx, String.valueOf(generalSid[nIdx]));
        }
        return jsonData;
    }

    /**
     * <br>[機  能] 登録編集情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param mode 登録編集
     * @return jsonData
     */
    private JSONObject __jsonGrpConfAddEdit(JSONObject jsonData,
            int mode) {
        jsonData.element("grp_procmode", mode);
        return jsonData;
    }

    /**
     * <br>[機  能] 管理者権限情報情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param adminFlg 管理者権限
     * @return jsonData
     */
    private JSONObject __jsonAdminFlg(JSONObject jsonData,
            int adminFlg) {
        jsonData.element("adminFlg", adminFlg);
        return jsonData;
    }

    /**
     * <br>[機  能] グループ作成権限情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param grpFlg グループ作成権限
     * @return jsonData
     */
    private JSONObject __jsonLimitChtGrpFlg(JSONObject jsonData,
            int grpFlg) {
        jsonData.element("limitGrpFlg", grpFlg);
        return jsonData;
    }

    /**
     * <br>[機  能] エラー情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param messageList エラーメッセージ
     * @return jsonData
     */
    private JSONObject __jsonError(JSONObject jsonData, List<String> messageList) {

        jsonData.element("errMessage_size", messageList.size());
        for (int nIdx = 0; nIdx < messageList.size(); nIdx++) {
        jsonData.element("errMessage_"
        + nIdx, String.valueOf(messageList.get(nIdx)));
        }
        return jsonData;
    }

    /**
     * <br>[機  能] グループチャット情報リアルタイム変更
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl グループチャットSID
     * @return jsonData
     */
    private JSONObject __jsonRealTimeUpdateChtGroup(
            JSONObject jsonData,
            Cht010ParamModel paramMdl) {
        jsonData.element("plugin", "chat");
        jsonData.element("type", "chatGroup");
        jsonData.element("chatGroupSid", paramMdl.getCht010GrpConfCgiSid());
        jsonData.element("chatGroupName", StringUtilHtml.transToHTmlPlusAmparsant(
                paramMdl.getCht010GrpConfGroupName()));
        jsonData.element("chatGroupArchiveFlg", paramMdl.getCht010GrpConfArchiveKbn());
        jsonData.element("chatGroupProcMode", paramMdl.getCht010GrpConfProcMode());
        jsonData.element("realTimeFlg", GSConstChat.REAL_TIME_YES);
        jsonData.element("messageCount", paramMdl.getCht010MessageCount());
        jsonData.element("messageLastDate", paramMdl.getCht010MessageLastDate());
        String[] usrSids = paramMdl.getCht010MemberUserSids();
        jsonData.element("usrSidssize", usrSids.length);
        String[] oldSids = paramMdl.getCht010OldMemberSids();
        jsonData.element("oldMembersize", oldSids.length);
        jsonData.element("ownUserSid", reqMdl__.getSmodel().getUsrsid());

        return jsonData;
    }

    /**
     *
     * <br>[機  能] チャットグループがないエラー時 レスポンスjson生成
     * <br>[解  説]
     * <br>[備  考]
     * @param messageList エラーメッセージ
     * @return json
     */
    public JSONObject createJsonGroupConfGrpNotExist(List<String> messageList) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("validate_notExist", true);
        __jsonError(jsonData, messageList);
        return jsonData;
    }
    /**
     *
     * <br>[機  能] その他エラー時 レスポンスjson生成
     * <br>[解  説]
     * <br>[備  考]
     * @param messageList エラーメッセージ
     * @return json
     */
    public JSONObject createJsonGroupConfError(List<String> messageList) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("validate_error", true);
        __jsonError(jsonData, messageList);
        return jsonData;
    }
    /**
     *
     * <br>[機  能] グループ設定初期化 レスポンスjson生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return json
     */
    public JSONObject createJsonGroupConfInit(Cht010ParamModel paramMdl) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        __jsonGrpConfGrpList(jsonData, paramMdl.getCht010GrpConfGroupList());
        __jsonAdminFlg(jsonData, paramMdl.getAdminFlg());
        __jsonLimitChtGrpFlg(jsonData, paramMdl.getCht010GroupEditFlg());
        return jsonData;
    }
    /**
     *
     * <br>[機  能] グループ設定編集画面遷移 レスポンスjson生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return json
     */
    public JSONObject createJsonGroupConfAddEdit(Cht010ParamModel paramMdl) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        __jsonGrpConfCategory(jsonData, paramMdl.getCht010GrpConfCategoryList());
        __jsonGrpConfMember(jsonData, paramMdl);
        __jsonGrpConfInf(jsonData, paramMdl);
        __jsonAdminFlg(jsonData, paramMdl.getAdminFlg());
        __jsonGrpConfAddEdit(jsonData, paramMdl.getCht010GrpConfProcMode());
        return jsonData;
    }
    /**
     *
     * <br>[機  能]  グループ設定メンバー変更 レスポンスjson作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return json
     */
    public JSONObject createJsonGroupConfMemberChange(
            Cht010ParamModel paramMdl) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        __jsonGrpConfMember(jsonData, paramMdl);
        __jsonGrpConfAddEdit(jsonData, paramMdl.getCht010GrpConfProcMode());
        return jsonData;
    }
    /**
     *
     * <br>[機  能]  グループ設定 更新確定 レスポンスjson作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return json
     */
    public JSONObject createJsonGroupConfUpdate(Cht010ParamModel paramMdl) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        __jsonGrpConfGrpList(jsonData, paramMdl.getCht010GrpConfGroupList());
        __jsonRealTimeUpdateChtGroup(jsonData, paramMdl);
        return jsonData;
    }
    /**
     *
     * <br>[機  能]  グループ設定 削除確定 レスポンスjson作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return json
     */
    public JSONObject createJsonGroupConfDelete(Cht010ParamModel paramMdl) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        __jsonGrpConfGrpList(jsonData, paramMdl.getCht010GrpConfGroupList());
        __jsonRealTimeUpdateChtGroup(jsonData, paramMdl);
        return jsonData;
    }

    /**
     *
     * <br>[機  能]  メッセージ送信処理 レスポンスjson作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return json jsonData
     * @throws SQLException SQL実行時例外
     */
    public JSONObject createJsonSendResp(Cht010ParamModel paramMdl) throws SQLException {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("selectSid", String.valueOf(paramMdl.getCht010SelectPartner()));
        jsonData.element("selectKbn", String.valueOf(paramMdl.getCht010SelectKbn()));

        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            UserSearchDao usrDao = new UserSearchDao(con__);
            UserSearchModel usrMdl = usrDao.getUserInfoJtkb(
                    paramMdl.getCht010SelectPartner(),
                    GSConstUser.USER_JTKBN_ACTIVE);

            jsonData.element("chatName", UserUtil.makeName(usrMdl.getUsiSei(), usrMdl.getUsiMei()));
            jsonData.element("usrJkbn", String.valueOf(usrMdl.getUsrJkbn()));
            jsonData.element("usrUkoFlg", String.valueOf(usrMdl.getUsrUkoFlg()));
        } else {
            getGroupName(paramMdl);
            ChatInformationModel cifMdl = paramMdl.getCht010ChtInfMdl();
            jsonData.element("chatName", cifMdl.getChatName());
            jsonData.element("archive", cifMdl.getChatArchive());


        }

        return jsonData;
    }
    /**
     * <br>[機  能] 左メニュータブ切り替え
     * <br>[解  説] タブ切り替え時に非同期実行される。切り替えたタブの保管のみ行う
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    public void changeTab(Cht010ParamModel paramMdl) throws SQLException {
        //不正入力判定
        switch (paramMdl.getCht010SelectTab()) {
            case GSConstChat.CHAT_TAB_ALL:
            case GSConstChat.CHAT_TAB_TIMELINE:
                break;
            default:
                return;
        }
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        ChtPriConfDao confDao = new ChtPriConfDao(con__);

        if (confDao.updateTab(
                paramMdl.getCht010SelectTab(),
                sessionUsrSid) == 0) {
            //設定の取得
            ChtBiz chtBiz = new ChtBiz(con__, reqMdl__);
            ChtPriConfModel userMdl = chtBiz.getChtUconf(sessionUsrSid);
            userMdl.setCpcSelTab(paramMdl.getCht010SelectTab());
            confDao.insert(userMdl);
        }
    }
}
