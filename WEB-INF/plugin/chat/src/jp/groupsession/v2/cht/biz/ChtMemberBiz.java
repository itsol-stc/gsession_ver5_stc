package jp.groupsession.v2.cht.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessTargetDao;
import jp.groupsession.v2.cht.model.ChatGroupComboModel;
import jp.groupsession.v2.cht.model.ChtGroupUserModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] チャットプラグインで使用される共通メンバービジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtMemberBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(ChtMemberBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     */
    public ChtMemberBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] メンバーを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param reqMdl リクエスト情報
     * @param mdl ChatGroupComboModel
     * @param tokureiFlg 特例アクセス権限を参照するか
     * @throws SQLException 実行例外
     */
    public void setMember(
            RequestModel reqMdl, ChatGroupComboModel mdl, boolean tokureiFlg, int usrSid)
                    throws SQLException {
        /** 情報取得*/
        //特例アクセス情報を取得
        List<Integer> tokureiGroupList = null;
        List<Integer> tokureiMemberList = null;
        if (tokureiFlg) {
            tokureiMemberList = getTokureiUser(usrSid, getTokureiGroup(usrSid));
            tokureiGroupList = getTokureiUserBelong(usrSid, tokureiMemberList);
        }

        // グループリスト取得
        List<GroupModel> allGroupList = __getAllGroupList();
        // 選択されているグループリスト一覧
        List<Integer> selectGroupList = new ArrayList<Integer>();
        // 選択されているメンバーリスト一覧
        List<Integer> selectUserList = new ArrayList<Integer>();
        // 選択グループ・メンバー一覧を設定
        __setSelectMemberSidList(mdl, selectGroupList, selectUserList);
        /** 情報設定*/
        //右側
        // グループコンボラベルリスト設定
        List <LabelValueBean> comboLabelList =
                __getGroupComboLabelList(allGroupList, reqMdl);
        mdl.setGroupList(comboLabelList);
        // セレクトボックスメンバー一覧設定
        List <UsrLabelValueBean> rightMemberLabelList = __getRightMemberLabelList(
                usrSid, allGroupList, tokureiGroupList, tokureiMemberList,
                mdl, selectGroupList, selectUserList);
        mdl.setRightUserList(rightMemberLabelList);
        //左側
        // 管理者メンバー
        List<UsrLabelValueBean> adminLabelList =
         __getSelectGroupLabel(
                 usrSid, mdl.getMemberAdminSid(), tokureiGroupList, tokureiMemberList);
        mdl.setLeftUserAdminList(adminLabelList);
        // 一般
        List<UsrLabelValueBean> generalLabelList =
         __getSelectGroupLabel(
                 usrSid, mdl.getMemberGeneralSid(), tokureiGroupList, tokureiMemberList);
        mdl.setLeftUserGeneralList(generalLabelList);

        String[] adminselectMember = __setSelectMember(adminLabelList);
        mdl.setMemberAdminSid(adminselectMember);
        String[] generalselectMember = __setSelectMember(generalLabelList);
        mdl.setMemberGeneralSid(generalselectMember);

    }
    /**
     * <br>[機  能] メンバーを追加します
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ChatGroupComboModel
     * @param mode 追加先モード
     * @return ChatGroupComboModel
     */
    public ChatGroupComboModel addMember(ChatGroupComboModel mdl, int mode) {

        //選択したメンバー
        String[] selectMember;
        //メンバーの追加先
        String[] targetMember;

        selectMember = mdl.getSelectRightUser();
        if (mode == GSConstChat.ADD_KBN_ADMIN) {
            targetMember = mdl.getMemberAdminSid();

        } else {
            targetMember = mdl.getMemberGeneralSid();
        }
        String[] addMember = __getAddMember(selectMember, targetMember);

        if (mode == GSConstChat.ADD_KBN_ADMIN) {
            mdl.setMemberAdminSid(addMember);
        } else {
            mdl.setMemberGeneralSid(addMember);
        }
        return mdl;
    }

    /**
     * <br>[機  能] メンバーを削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ChatGroupComboModel
     * @param mode 追加先モード
     * @return ChatGroupComboModel
     */
    public ChatGroupComboModel delMember(ChatGroupComboModel mdl, int mode) {
        //選択したメンバー
        String[] selectMember;
        //メンバーの削除先
        String[] targetMember;

        if (mode == GSConstChat.ADD_KBN_ADMIN) {
            selectMember = mdl.getSelectLeftUserAdmin();
            targetMember = mdl.getMemberAdminSid();

        } else {
            selectMember = mdl.getSelectLeftUserGeneral();
            targetMember = mdl.getMemberGeneralSid();
        }
        //削除後のメンバー
        String[] delMember = __getDeleteMember(selectMember, targetMember);

        if (mode == GSConstChat.ADD_KBN_ADMIN) {
            mdl.setMemberAdminSid(delMember);

        } else {
            mdl.setMemberGeneralSid(delMember);
        }
        return mdl;
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param addSelectSid 選択メンバー
     * @param memberSid メンバー
     * @return 追加済みのメンバーリスト
     */
    private String[] __getAddMember(String[] addSelectSid, String[] memberSid) {
        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }
        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();
        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }
        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }
        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param deleteSelectSid 選択メンバー
     * @param memberSid メンバー
     * @return 削除済みのメンバーリスト
     */
    private String[] __getDeleteMember(String[] deleteSelectSid, String[] memberSid) {
        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }
        if (memberSid == null) {
            memberSid = new String[0];
        }
        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < memberSid.length; i++) {
            boolean setFlg = true;
            for (int j = 0; j < deleteSelectSid.length; j++) {
                if (memberSid[i].equals(deleteSelectSid[j])) {
                    setFlg = false;
                    break;
                }
            }
            if (setFlg) {
                list.add(memberSid[i]);
            }
        }
        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }
    /**
     * <br>[機  能] 全てのグループ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    private List<GroupModel> __getAllGroupList()
                    throws SQLException {
        List<GroupModel> ret = new ArrayList<GroupModel>();
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        GroupDao dao = new GroupDao(con__);
        ret = dao.getGroupTree(sortMdl);
        return ret;
    }

    /**
     * <br>[機  能] 特例制限一覧を取得する
     * <br>[解  説] 自身が制限されている特例情報一覧を取得する
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param grpList 特例グループ一覧
     * @return 特例制限ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getTokureiUser(int usrSid, List<Integer> grpList)
                    throws SQLException {
        List<Integer> ret = new ArrayList<Integer>();
        ChtSpaccessTargetDao dao = new ChtSpaccessTargetDao(con__);
        // いずれかの特例アクセス設定で制限されているユーザを取得
        List<Integer> deniedList = dao.getTargetUserMemberList(usrSid, grpList);
        // いずれかの特例アクセス設定で許可されているユーザは除外
        List<Integer> permittedUsrList = dao.getPermittedTarget(usrSid, GSConstChat.CHAT_KBN_USER);
        List<Integer> permittedGrpList = dao.getPermittedTarget(usrSid, GSConstChat.CHAT_KBN_GROUP);
        CmnBelongmDao belongDao = new CmnBelongmDao(con__);
        String[] strGrpSid = new String[permittedGrpList.size()];
        for (int i = 0; i < permittedGrpList.size(); i++) {
            strGrpSid[i] = String.valueOf(permittedGrpList.get(i));
        }
        List<String> permittedMemberList = new ArrayList<String>();
        if (strGrpSid.length > 0) {
            permittedMemberList = belongDao.select(strGrpSid);
        }
        for (int deny : deniedList) {
            if (!permittedUsrList.contains(deny)
                    && !permittedMemberList.contains(String.valueOf(deny))) {
                ret.add(deny);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 特例制限グループ一覧を取得する
     * <br>[解  説] 自身が制限されている特例情報一覧を取得する
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return 特例制限グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getTokureiGroup(int usrSid)
                    throws SQLException {
        List<Integer> ret = new ArrayList<Integer>();
        ChtSpaccessTargetDao dao = new ChtSpaccessTargetDao(con__);
        // いずれかの特例アクセス設定で制限されているグループを取得
        List<Integer> deniedList = dao.getTargetGroupMemberList(usrSid);
        // いずれかの特例アクセス設定で許可されているグループは除外
        List<Integer> permittedList = dao.getPermittedTarget(usrSid, GSConstChat.CHAT_KBN_GROUP);
        for (int deny : deniedList) {
            if (!permittedList.contains(deny)) {
                ret.add(deny);
            }
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] 特例アクセス権限のないユーザが所属しているグループを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param usrList 特例アクセス権限のないユーザSIDリスト
     * @return 特例アクセス権限のないユーザが所属しているグループSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getTokureiUserBelong(int usrSid, List<Integer> usrList)
            throws SQLException {
        List<Integer> tempList = new ArrayList<Integer>();
        CmnBelongmDao belongDao = new CmnBelongmDao(con__);
        List<String> usrStrList = new ArrayList<String>();
        for (int usr : usrList) {
            String usrStr = String.valueOf(usr);
            if (!usrStrList.contains(usrStr)) {
                usrStrList.add(usrStr);
            }
        }
        // 対象ユーザが1000を超える場合、1000件毎に区切る
        if (usrStrList.size() <= GSConst.MAX_NUM_SQL_IN) {
            tempList = belongDao.selectUserBelongGroupSid(usrStrList);
        } else {
            List<String> usrListPerThousand = new ArrayList<String>();
            for (int i = 0; i < usrStrList.size(); i++) {
                if (i == 0 || i % GSConst.MAX_NUM_SQL_IN != 0) {
                    usrListPerThousand.add(usrStrList.get(i));
                } else if (i > 0 && i % GSConst.MAX_NUM_SQL_IN == 0) {
                    tempList.addAll(belongDao.selectUserBelongGroupSid(usrListPerThousand));
                    usrListPerThousand = new ArrayList<String>();
                }
            }
        }
        // いずれかの特例アクセス設定で許可されているグループは除外
        ChtSpaccessTargetDao dao = new ChtSpaccessTargetDao(con__);
        List<Integer> permittedList = dao.getPermittedTarget(usrSid, GSConstChat.CHAT_KBN_GROUP);
        List<Integer> ret = new ArrayList<Integer>();
        for (int grpSid : tempList) {
            if (!permittedList.contains(grpSid) && !ret.contains(grpSid)) {
                ret.add(grpSid);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] グループコンボラベルを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param allGroupList グループ情報
     * @param reqMdl RequestModel
     * @return グループコンボラベル
     */
    private List<LabelValueBean> __getGroupComboLabelList(
             List<GroupModel> allGroupList,
             RequestModel reqMdl) {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        LabelValueBean lavelBean = new LabelValueBean();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textGroupList = gsMsg.getMessage("cmn.grouplist");
        lavelBean.setLabel(textGroupList);
        lavelBean.setValue(GSConstChat.GROUP_COMBO_VALUE);
        labelList.add(lavelBean);
        for (GroupModel grpMdl : allGroupList) {
            labelList.add(new LabelValueBean(grpMdl.getGroupName(),
                    String.valueOf(grpMdl.getGroupSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 追加判定
     * <br>[解  説] 特例制限されているグループは追加しない
     * <br>[備  考] true:追加する false:追加しない
     * @param grpMdl グループ情報一覧
     * @param tokureiGroupList 特例アクセス制限グループ
     * @return 追加判定
     */
    private boolean __addTokureiGroupCheck(
            GroupModel grpMdl,
            List<Integer> tokureiGroupList) {

        if (grpMdl == null) {
            return false;
        }
        if (tokureiGroupList == null || tokureiGroupList.isEmpty()) {
           return true;
        }
        if (tokureiGroupList.contains(grpMdl.getGroupSid())) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 選択中のグループSID・ユーザSID取得
     * <br>[解  説] 管理者・一般メンバーで選択されているグループSIDを取得
     * <br>[備  考]
     * @param mdl グループ情報一覧
     * @param selectGroupList 選択中グループ一覧
     * @param selectUserList 選択中ユーザ一覧
     */
    private void __setSelectMemberSidList(
             ChatGroupComboModel mdl,
             List<Integer> selectGroupList,
             List<Integer> selectUserList) {
        // 管理者メンバー
        String[] adminMember = mdl.getMemberAdminSid();
        for (String member : adminMember) {
            if (member.startsWith(GSConstChat.GROUP_HEADSTR)) {
                String str = (member.substring(1, member.length()));
                int sid = NullDefault.getInt(str, -1);
                selectGroupList.add(sid);
            } else {
                int sid = NullDefault.getInt(member, -1);
                selectUserList.add(sid);
            }
        }
        // 一般メンバー
        String[] generalMember = mdl.getMemberGeneralSid();
        for (String member : generalMember) {
            if (member.startsWith(GSConstChat.GROUP_HEADSTR)) {
                String str = (member.substring(1, member.length()));
                int sid = NullDefault.getInt(str, -1);
                selectGroupList.add(sid);
            } else {
                int sid = NullDefault.getInt(member, -1);
                selectUserList.add(sid);
            }
        }
    }



    /**
     * <br>[機  能] 追加判定
     * <br>[解  説] 既に追加されているグループは追加しない
     * <br>[備  考] true:追加する false:追加しない
     * @param grpMdl グループ情報一覧
     * @param selectGroupList 選択グループ一覧
     * @return 追加判定
     */
    private boolean __addSelectedGroupCheck(GroupModel grpMdl,
             List<Integer> selectGroupList) {
        if (grpMdl == null) {
            return false;
        }
        if (selectGroupList.contains(grpMdl.getGroupSid())) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 右側セレクトボックスメンバーラベルを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param allGroupList グループ情報一覧
     * @param mdl ChatGroupComboModel
     * @param tokureiGroupList 特例アクセス制限グループ
     * @param tokureiMemberList 特例アクセス制限メンバー
     * @param selectGroupList 選択グループ
     * @param selectUserList 選択ユーザ
     * @throws SQLException SQL実行時例外
     * @return ラベル
     */
    private List<UsrLabelValueBean> __getRightMemberLabelList(
                int usrSid,
                List<GroupModel> allGroupList,
                List<Integer> tokureiGroupList,
                List<Integer> tokureiMemberList,
                ChatGroupComboModel mdl,
                List<Integer> selectGroupList,
                List<Integer> selectUserList) throws SQLException {
        ArrayList<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
        if (mdl.getSelectGroupSid()
                == Integer.parseInt(GSConstChat.GROUP_COMBO_VALUE)) {
            //「グループ一覧」選択時
            for (GroupModel grpMdl : allGroupList) {
                // 特例アクセス制限されている場合は、追加しない
                boolean tokureiAddFlg = __addTokureiGroupCheck(grpMdl, tokureiGroupList);
                if (!tokureiAddFlg) {
                    continue;
                }
                // 既に選択されているグループは追加しない
                boolean selectedAddFlg = __addSelectedGroupCheck(grpMdl, selectGroupList);
                if (!selectedAddFlg) {
                    continue;
                }
                labelList.add(new UsrLabelValueBean(
                        grpMdl.getGroupName(),
                        String.valueOf(GSConstChat.GROUP_HEADSTR
                                + grpMdl.getGroupSid())));
            }
        } else {
            // 選択済みメンバー
            ArrayList<Integer> excludeList = new ArrayList<Integer>();
            for (int sid : selectUserList) {
                excludeList.add(sid);
            }
            if (tokureiMemberList != null && !tokureiMemberList.isEmpty()) {
                for (int sid : tokureiMemberList) {
                    if (usrSid != sid) {
                        excludeList.add(sid);
                    }
                }
            }

            // 選択済みメンバーを除外したグループに所属するユーザ情報一覧を取得
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> usList =
                    userBiz.getBelongUserList(con__, mdl.getSelectGroupSid(), excludeList);
            for (CmnUsrmInfModel usrMdl : usList) {
                UsrLabelValueBean usrLabel =  new UsrLabelValueBean(
                        usrMdl.getUsiSei() + usrMdl.getUsiMei(),
                                 String.valueOf(usrMdl.getUsrSid()));
                    usrLabel.setUsrUkoFlg(usrMdl.getUsrUkoFlg());
                    labelList.add(usrLabel);
            }
        }

        return labelList;
    }

    /**
     * <br>[機  能] メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param leftList 取得するユーザSID・グループSID
     * @param usrSid ユーザSID
     * @param tokureiGroupList 特例アクセス権限のないグループ
     * @param tokureiUserList 特例アクセス権限のないユーザ
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getMemberLabel(
            List<String> leftList,
            int usrSid,
            List<Integer> tokureiGroupList,
            List<Integer> tokureiUserList)
                    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (leftList != null) {
            for (String sid : leftList) {
                String str = NullDefault.getString(sid, "-1");
                if (str.startsWith(GSConstChat.GROUP_HEADSTR)) {
                    //グループ
                    grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        UsrLabelValueBean labelBean = null;
        if (grpSids.size() > 0) {
            //グループ情報取得
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con__);
            ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
            for (GroupModel gmodel : glist) {
                int sid = gmodel.getGroupSid();
                labelBean = new UsrLabelValueBean();
                labelBean.setLabel(gmodel.getGroupName());
                labelBean.setValue(GSConstChat.GROUP_HEADSTR
                        + String.valueOf(sid));
                if (tokureiGroupList != null && !tokureiGroupList.isEmpty()) {
                    if (tokureiGroupList.contains(sid)) {
                        labelBean.setUsrUkoFlg(-1);
                    }
                }
                ret.add(labelBean);
            }

        }
        //ユーザ情報取得
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con__);
        List <CmnUsrmInfModel> ulist =
                usiDao.getUsersInfList(
                        (String[]) usrSids.toArray(new String[usrSids.size()]), sortMdl);
        for (CmnUsrmInfModel umodel : ulist) {
            int sid = umodel.getUsrSid();
            labelBean = new UsrLabelValueBean();
            labelBean.setLabel(umodel.getUsiSei() + " " + umodel.getUsiMei());
            labelBean.setValue(String.valueOf(umodel.getUsrSid()));
            labelBean.setUsrUkoFlg(umodel.getUsrUkoFlg());
            if (tokureiUserList != null && !tokureiUserList.isEmpty()) {
                if (tokureiUserList.contains(sid) && sid != usrSid) {
                    labelBean.setUsrUkoFlg(-1);
                }
            }
            ret.add(labelBean);
        }
        return ret;
    }

    /**
     * <br>[機  能] メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param memberSid 取得するユーザSID・グループSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<UsrLabelValueBean> getMemberLabel(
            String[] memberSid)
                    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (memberSid != null) {
            for (String sid : memberSid) {
                String str = NullDefault.getString(sid, "-1");
                if (str.startsWith(GSConstChat.GROUP_HEADSTR)) {
                    //グループ
                    grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        UsrLabelValueBean labelBean = null;
        if (grpSids.size() > 0) {
            //グループ情報取得
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con__);
            ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
            for (GroupModel gmodel : glist) {
                labelBean = new UsrLabelValueBean();
                labelBean.setLabel(gmodel.getGroupName());
                labelBean.setValue(GSConstChat.GROUP_HEADSTR
                        + String.valueOf(gmodel.getGroupSid()));
                ret.add(labelBean);
            }

        }
        //ユーザ情報取得
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con__);
        List <CmnUsrmInfModel> ulist =
                usiDao.getUsersInfList(
                        (String[]) usrSids.toArray(new String[usrSids.size()]), sortMdl);

        for (CmnUsrmInfModel umodel : ulist) {
            labelBean = new UsrLabelValueBean();
            labelBean.setLabel(umodel.getUsiSei() + " " + umodel.getUsiMei());
            labelBean.setValue(String.valueOf(umodel.getUsrSid()));
            labelBean.setUsrUkoFlg(umodel.getUsrUkoFlg());
            ret.add(labelBean);
        }
        return ret;
    }


    /**
     * <br>[機  能] メンバーラベルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param memberSid メンバーSID
     * @param usrSid ユーザSID
     * @param tokureiGroupList 特例グループ一覧
     * @param tokureiUserList 特例ユーザ一覧
     * @return メンバーラベル
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getSelectGroupLabel(
            int usrSid,
            String[] memberSid,
            List<Integer> tokureiGroupList,
            List<Integer> tokureiUserList)
                    throws SQLException {
        List<String> memberSidList = new ArrayList<String>();
        if (memberSid != null) {
            for (String str : memberSid) {
                memberSidList.add(str);
            }
        }

        //メンバー一覧を作成
        return __getMemberLabel(memberSidList, usrSid, tokureiGroupList, tokureiUserList);
    }

    /**
     * グループユーザ情報をパラメータモデルに設定
     * @param userList List<ChtGroupUserModel>
     * @return grpMdl
     * */
    public ChatGroupComboModel setChatGrp(List<ChtGroupUserModel>  userList) {
        ChatGroupComboModel grpMdl = new ChatGroupComboModel();
        List <String> userAdminList = new ArrayList<String>();
        List <String> userGeneralList = new ArrayList<String>();
        for (ChtGroupUserModel mdl:userList) {
            if (mdl.getCguAdminFlg() == GSConstChat.CHAT_GROUP_ADMIN) {
                userAdminList.add(__setGrpUser(mdl));
            } else {
                userGeneralList.add(__setGrpUser(mdl));
            }
        }
        if (!userAdminList.isEmpty()) {
            grpMdl.setMemberAdminSid(userAdminList.toArray(new String[userAdminList.size()]));
        }
        if (!userGeneralList.isEmpty()) {
            grpMdl.setMemberGeneralSid(userGeneralList.toArray(new String[userGeneralList.size()]));
        }
        return grpMdl;
    }

    /**
     * グループユーザ情報をパラメータモデルに設定
     * @param mdl ChtGroupUserModel
     * @return ret
     * */
    private String __setGrpUser(ChtGroupUserModel mdl) {
        String ret = "";
        if (mdl.getCguKbn() == GSConstChat.CHAT_KBN_GROUP) {
            ret = String.valueOf(GSConstChat.GROUP_HEADSTR
                            + mdl.getCguSelectSid());
        } else {
            ret = String.valueOf(mdl.getCguSelectSid());
        }

        return ret;
    }

    /**
     * グループユーザ情報をパラメータモデルに設定
     * @param memberLabel メンバー情報一覧
     * @return grpMdl
     * */
    private String[] __setSelectMember(List<UsrLabelValueBean> memberLabel) {

        List<String> ret = new ArrayList<String>();

        for (UsrLabelValueBean label:memberLabel) {
            ret.add(label.getValue());
        }
        return ret.toArray(new String[ret.size()]);
    }

    /**
     * メンバーSIDからユーザSIDを生成
     * グループは所属ユーザに変更して取得する
     * @param adminMembers 管理者メンバーSID
     * @param generalMembers 一般メンバーSID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     * */
    public String[] createMemberUserSid(
            String[] adminMembers,
            String[] generalMembers) throws SQLException {
        // メンバーユーザSID
        List<String> memberUsers = new ArrayList<String>();


        // 管理者メンバーおよび一般メンバーを取得
        // メンバーにグループが指定されている場合、グループSIDを取得
        List<String> groupSidList = new ArrayList<String>();
        for (String admin : adminMembers) {
            if (admin.startsWith(GSConstChat.GROUP_HEADSTR)) {
                groupSidList.add(admin.replace(GSConstChat.GROUP_HEADSTR, ""));
            } else {
                memberUsers.add(admin);
            }
        }
        for (String general : generalMembers) {
            if (general.startsWith(GSConstChat.GROUP_HEADSTR)) {
                groupSidList.add(general.replace(GSConstChat.GROUP_HEADSTR, ""));
            } else {
                memberUsers.add(general);
            }
        }
        // 選択されたグループの所属ユーザの中で重複のないようメンバーを取得する
        if (groupSidList != null && groupSidList.size() > 0) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con__);
            String[] groupSids = groupSidList.toArray(new String[groupSidList.size()]);
            List<String> belongUserSidList = belongDao.select(groupSids);
            for (String userSid : belongUserSidList) {
                if (!memberUsers.contains(userSid) && Integer.parseInt(userSid) > 100) {
                    memberUsers.add(userSid);
                }
            }
        }
        return memberUsers.toArray(new String[memberUsers.size()]);
    }

    /**
     * 登録されているメンバー一覧を取得
     * また削除されていないメンバーのみ取得
     * @param cgiSid グループSID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     * */
    public List<ChtGroupUserModel> getOldMember(
            int cgiSid) throws SQLException {
        ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
        // 過去のメンバー
        List<ChtGroupUserModel> oldMembers = userDao.select(cgiSid);
        // 削除されていないメンバーを取得
        List<ChtGroupUserModel> ret = new ArrayList<ChtGroupUserModel>();
        for (ChtGroupUserModel oldMember: oldMembers) {
            int sid = oldMember.getCguSelectSid();
            int kbn = oldMember.getCguKbn();
            if (kbn == GSConstChat.CHAT_KBN_GROUP) {
                GroupDao dao = new GroupDao(con__);
                CmnGroupmModel group = dao.getGroup(sid);
                if (group != null) {
                    ret.add(oldMember);
                }
            } else {
                CmnUsrmDao cuDao = new CmnUsrmDao(con__);
                if (cuDao.isExistUser(sid)) {
                    ret.add(oldMember);
                }
            }
        }
        return ret;
    }

    /**
     * 登録されているメンバー一覧を取得
     * グループは所属ユーザに変更して取得する
     * @param cgiSid グループSID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     * */
    public String[] getOldMembers(
            int cgiSid) throws SQLException {
        ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
        // 過去のメンバー
        List<ChtGroupUserModel> userList = userDao.select(cgiSid);
        // ユーザSID
        List<String> memberUsers = new ArrayList<String>();
        // グループSID
        List<String> groupSidList = new ArrayList<String>();

        for (ChtGroupUserModel mdl:userList) {
            int kbn = mdl.getCguKbn();
            int sid = mdl.getCguSelectSid();
            if (kbn == GSConstChat.CHAT_KBN_GROUP) {
                groupSidList.add(String.valueOf(sid));
            } else {
                memberUsers.add(String.valueOf(sid));
            }
        }
        // 選択されたグループの所属ユーザの中で重複のないようメンバーを取得する
        if (groupSidList != null && groupSidList.size() > 0) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con__);
            String[] groupSids = groupSidList.toArray(new String[groupSidList.size()]);
            List<String> belongUserSidList = belongDao.select(groupSids);
            for (String userSid : belongUserSidList) {
                if (!memberUsers.contains(userSid) && Integer.parseInt(userSid) > 100) {
                    memberUsers.add(userSid);
                }
            }
        }
        return memberUsers.toArray(new String[memberUsers.size()]);
    }

    /**
     * 登録されている特例メンバー一覧を取得
     * @param oldMembers 過去メンバー
     * @param tokureiGroups 特例グループ
     * @param tokureiUsers 特例ユーザ
     * @param memberKbn メンバー区分
     * @return ユーザSID
     * */
    public List<ChtGroupUserModel> getTokureiOldMember(
            List<ChtGroupUserModel> oldMembers,
            List<Integer> tokureiGroups,
            List<Integer> tokureiUsers,
            int memberKbn) {
        List<ChtGroupUserModel> ret = new ArrayList<ChtGroupUserModel>();
        for (ChtGroupUserModel oldMember: oldMembers) {
            if (oldMember.getCguAdminFlg() != memberKbn) {
                continue;
            }
            int sid = oldMember.getCguSelectSid();
            int kbn = oldMember.getCguKbn();
            if (kbn == GSConstChat.CHAT_KBN_GROUP) {
                if (tokureiGroups.contains(sid)) {
                    ret.add(oldMember);
                }
            } else {
                if (tokureiUsers.contains(sid)) {
                    ret.add(oldMember);
                }
            }
        }
        return ret;
    }

    /**
     * メンバーの差分を取得
     * @param oldMembers 過去メンバー
     * @param nowMembers 現在メンバー
     * @return 差分メンバー
     * */
    public  List<Integer> getDiffMember(
            String[] nowMembers,
            String[] oldMembers) {
        List<Integer> ret = new ArrayList<Integer>();
        for (String oldMember: oldMembers) {
            boolean chk = true;
            for (String nowMember: nowMembers) {
                if (oldMember.equals(nowMember)) {
                    chk = false;
                    break;
                }
            }
            if (chk) {
                ret.add(NullDefault.getInt(oldMember, -1));
            }
        }
        return ret;
    }





}
