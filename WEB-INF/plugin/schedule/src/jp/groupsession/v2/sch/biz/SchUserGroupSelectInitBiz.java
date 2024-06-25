package jp.groupsession.v2.sch.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.dao.SchMyviewlistBelongDao;
import jp.groupsession.v2.sch.dao.SchMyviewlistDao;
import jp.groupsession.v2.sch.model.MyViewListBelongModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.model.SchMyviewlistModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] グループ・ユーザ選択コンボ設定 ビジネスロジック
 * <br>[解  説] 月間 ・ 一覧 ・個人週間画面の選択状態を設定する
 * <br>[備  考] 特例アクセス制限、共有設定を反映し、不正入力時の選択変更を行う
 *
 * @author JTS
 */
public class SchUserGroupSelectInitBiz {
    /** エラーコード 成功*/
    private static final int ECODE_SUCCESS = 0;
    /** エラーコード 存在しないユーザ参照*/
    private static final int ECODE_USR_NONE = 1;
    /** エラーコード アクセス権限エラー*/
    private static final int ECODE_USR_PERMISSION = 2;
//    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(SchUserGroupSelectInitBiz.class);
    /** リクエスモデル */
    private RequestModel reqMdl__ = null;
    /** DBコネクション */
    private Connection con__ = null;


    /** 選択状態ユーザSID*/
    private String usrSidStr__ = null;
    /** 選択状態グループSID*/
    private String grpSidStr__ = null;

    /** 入力ユーザSID*/
    private String srcUsrSidStr__ = null;
    /** 入力グループSID*/
    private String srcGrpSidStr__ = null;

    /** ユーザコンボ設定リスナ*/
    ICreateSchLabelListListner listner__;
    /** エラーメッセージ */
    private SchErrorMessage errorMsg__ = null;
    /** 基本グループコンボリスト*/
    private List <SchLabelValueModel > baseGrpLabelList__ = new ArrayList<SchLabelValueModel >();
    /** 基本ユーザコンボリスト*/
    private List<UsrLabelValueBean> baseUsrLabelList__ = new ArrayList<UsrLabelValueBean>();
    /** アクセス制限グループSIDリスト*/
    List<Integer> notAccessGrpList__ = null;
    /** アクセス制限ユーザSIDリスト*/
    List<Integer> notAccessUserList__ = null;

    /** RESTAPI用 limit */
    private int limit__ = 50;
    /** RESTAPI用 offset */
    private int offset__ = 0;
    /** RESTAPI用 検索結果数*/
    private int searchCnt__ = 0;

    /**
     *
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param srcUsrSidStr 入力ユーザ選択値
     * @param srcGrpSidStr 入力グループ選択値
     * @param listner ユーザコンボ設定リスナ
     * @throws SQLException SQL実行時例外
     */
    public SchUserGroupSelectInitBiz(
        RequestModel reqMdl,
        Connection con,
        String srcUsrSidStr,
        String srcGrpSidStr,
        ICreateSchLabelListListner listner) throws SQLException {

        super();
        reqMdl__ = reqMdl;
        con__ = con;
        srcUsrSidStr__ = srcUsrSidStr;
        srcGrpSidStr__ = srcGrpSidStr;
        listner__ = listner;
        limit__ = -1;
        offset__ = -1;

        SchDao schDao = new SchDao(con);
        notAccessGrpList__ = schDao.getNotAccessGrpList(reqMdl.getSmodel().getUsrsid());
        notAccessUserList__ = schDao.getNotAccessUserList(reqMdl.getSmodel().getUsrsid());
        notAccessUserList__.add(GSConst.SYSTEM_USER_ADMIN);
        notAccessUserList__.add(GSConst.SYSTEM_USER_MAIL);
    }

    /**
     *
     * コンストラクタ RESTAPI用
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param srcUsrSidStr 入力ユーザ選択値
     * @param srcGrpSidStr 入力グループ選択値
     * @param listner ユーザコンボ設定リスナ
     * @param limit リミット
     * @param offset オフセット
     * @throws SQLException SQL実行時例外
     */
    public SchUserGroupSelectInitBiz(
        RequestModel reqMdl,
        Connection con,
        String srcUsrSidStr,
        String srcGrpSidStr,
        ICreateSchLabelListListner listner,
        int limit,
        int offset) throws SQLException {

        super();
        reqMdl__ = reqMdl;
        con__ = con;
        srcUsrSidStr__ = srcUsrSidStr;
        srcGrpSidStr__ = srcGrpSidStr;
        listner__ = listner;
        limit__ = limit;
        offset__ = offset;

        SchDao schDao = new SchDao(con);
        notAccessGrpList__ = schDao.getNotAccessGrpList(reqMdl.getSmodel().getUsrsid());
        notAccessUserList__ = schDao.getNotAccessUserList(reqMdl.getSmodel().getUsrsid());
        notAccessUserList__.add(GSConst.SYSTEM_USER_ADMIN);
        notAccessUserList__.add(GSConst.SYSTEM_USER_MAIL);
    }


    /**
     * <p>usrSidStr を取得します。
     * @return usrSidStr
     * @see jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz#usrSidStr__
     */
    public String getUsrSidStr() {
        return usrSidStr__;
    }

    /**
     * <p>grpSidStr を取得します。
     * @return grpSidStr
     * @see jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz#grpSidStr__
     */
    public String getGrpSidStr() {
        return grpSidStr__;
    }

    /**
     * <p>baseGrpLabelList を取得します。
     * @return baseGrpLabelList
     * @see jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz#baseGrpLabelList__
     */
    public  List<SchLabelValueModel> getBaseGrpLabelList() {
        return baseGrpLabelList__;
    }
    /**
     * <p>baseUsrLabelList を取得します。
     * @return baseUsrLabelList
     * @see jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz#baseUsrLabelList__
     */
    public List<UsrLabelValueBean> getBaseUsrLabelList() {
        return baseUsrLabelList__;
    }

    public int getSearchCnt() {
        return searchCnt__;
    }

    public void setSearchCnt(int searchCnt) {
        searchCnt__ = searchCnt;
    }

    /**
     *
     * <br>[機  能] 入力値から選択値を設定する
     * <br>[解  説]
     * <br>[備  考] 実行後、getUsrSidStr getGrpSidStrで取得可能
     * @throws SQLException
     */
    public void initUserAndGroup() throws SQLException {
        boolean isNotSetGrp = StringUtil.isNullZeroString(srcGrpSidStr__);
        boolean isNotSetUsr = StringUtil.isNullZeroString(srcUsrSidStr__);

        __createGrpLabel();

        //選択ユーザ、選択グループが未設定時
        if (isNotSetGrp
                && isNotSetUsr) {
            __defaultInit();
        } else if (isNotSetUsr) {
        //選択ユーザが未設定、 選択グループが設定時
            __selectGroupOnly(srcGrpSidStr__);
        } else if (isNotSetGrp) {
        //選択ユーザが設定、選択グループが未設定時
            __selectUserOnly(srcUsrSidStr__);
        } else {
        //選択ユーザ、 選択グループが設定時
            __selectGrpAndUser();
        }
        __createUserLabel();

        if (baseUsrLabelList__ == null || baseUsrLabelList__.size() == 0) {
            //ユーザコンボ内が0件の場合は
            //エラーメッセージを表示
            //ログインユーザのデフォルト所属グループを選択


            //グループ名を取得する。
            for (SchLabelValueModel label : baseGrpLabelList__) {
                if (Objects.equals(label.getValue(), grpSidStr__)) {
                    __setError("error.user.not.exist.belong", label.getLabel().strip());
                    break;
                }
            }

            int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
            usrSidStr__ = String.valueOf(sessionUsrSid);
            GroupBiz gbiz = new GroupBiz();
            grpSidStr__ = String.valueOf(gbiz.getDefaultGroupSid(sessionUsrSid, con__));
            __createUserLabel();

        }

//        上記判定後、
//        選択ユーザがグループに所属しない
//            ユーザはユーザリストの最上段
//            エラーメッセージを表示する
        __finallySelect();


    }
    /**
    *
    * <br>[機  能] 入力値から選択値を設定する
    * <br>[解  説]
    * <br>[備  考] 実行後、getUsrSidStr getGrpSidStrで取得可能
    * @throws SQLException
    */
   public void initGroup() throws SQLException {
       boolean isNotSetGrp = StringUtil.isNullZeroString(srcGrpSidStr__);
       boolean isNotSetUsr = StringUtil.isNullZeroString(srcUsrSidStr__);

       __createGrpLabel();

       //選択ユーザ、選択グループが未設定時
       if (isNotSetGrp
               && isNotSetUsr) {
           __defaultInit();
       } else if (isNotSetUsr) {
       //選択ユーザが未設定、 選択グループが設定時
           __selectGroupOnly(srcGrpSidStr__);
       } else if (isNotSetGrp) {
       //選択ユーザが設定、選択グループが未設定時
           __selectUserOnly(srcUsrSidStr__);
       } else {
       //選択ユーザ、 選択グループが設定時

           __selectGrpYusen();
       }

   }

    /**
     * <br>[機  能] グループ選択の生成
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __createGrpLabel() throws SQLException {
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        baseGrpLabelList__ = scBiz.getGroupLabelForSchedule(
                sessionUsrSid, con__, false);
        if (listner__ instanceof IUserGroupListener) {
            baseGrpLabelList__ = listner__.addExGroupLabelList(baseGrpLabelList__);
        }

        //表示リストが登録されている場合、グループの選択肢に表示リストを設定
        List<SchLabelValueModel> viewGrpList = new ArrayList<SchLabelValueModel>();
        SchMyviewlistDao myViewListDao = new SchMyviewlistDao(con__);
        List<SchMyviewlistModel> myViewList = myViewListDao.select(sessionUsrSid);
        if (!myViewList.isEmpty()) {
            for (SchMyviewlistModel myViewMdl : myViewList) {
                viewGrpList.add(
                        new SchLabelValueModel(
                                myViewMdl.getSmyName(),
                                GSConstSchedule.DSP_LIST_STRING + myViewMdl.getSmySid(),
                                "2"
                                )
                        );

            }
            baseGrpLabelList__.addAll(0, viewGrpList);
        }

    }

    /**
     *
     * <br>[機  能] 選択ユーザ、選択グループが未設定時の選択状態を設定する
     * <br>[解  説]
     * <br>選択ユーザ、選択グループが未設定時
     * <br>ログインユーザの個人設定デフォルト表示グループ選択
     * <br>ユーザにログインユーザを選択
     * <br>個人設定デフォルト表示グループが選択できない場合
     * <br>    ログインユーザのデフォルト所属グループを選択
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __defaultInit() throws SQLException {

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        //        ログインユーザの個人設定デフォルト表示グループ選択
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con__, sessionUsrSid);

        __selectGroupOnly(dfGpSidStr);
    }
    /**
     *
     * <br>[機  能]選択ユーザが未設定、 選択グループが設定時の選択状態を設定する
     * <br>[解  説] 選択グループを引き継ぐ
     * <br>ユーザにログインユーザを選択
     * <br>選択グループが選択できない場合
     * <br>    ログインユーザのデフォルト所属グループを選択
     * <br>[備  考]
     * @param dfGpSidStr 選択グループ
     * @throws SQLException SQL実行時例外
     */
    private void __selectGroupOnly(String dfGpSidStr) throws SQLException {
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
//      ユーザにログインユーザを選択
        usrSidStr__ = String.valueOf(sessionUsrSid);

        GroupBiz gbiz = new GroupBiz();
        int grpSid = gbiz.getDefaultGroupSid(sessionUsrSid, con__);

        grpSidStr__ = scBiz.getEnableSelectGroup(baseGrpLabelList__,
                dfGpSidStr,
                String.valueOf(grpSid));

        if (!Objects.equals(dfGpSidStr, grpSidStr__)) {
            __setErrorGroup(dfGpSidStr);
        }

    }
    /**
     *
     * <br>[機  能] 選択ユーザが設定、選択グループが未設定時の選択状態を設定する
     * <br>[解  説]
     * <br>選択ユーザを引き継ぐ
     * <br>選択ユーザが選択不可の場合
     * <br>・エラーメッセージを表示する
     * <br>・ログインユーザを選択する
     * <br>・ログインユーザのデフォルトグループを選択する
     * <br>選択ユーザが選択可能の場合
     * <br>・選択ユーザのデフォルトグループを選択する
     * <br>選択ユーザのデフォルトグループが選択できない場合
     * <br>ログインユーザが表示可能で所属の一致するグループを選択
     * <br>一つも一致しなかった場合
     * <br>・エラーメッセージを表示する
     * <br>・ログインユーザを選択する
     * <br>・ログインユーザのデフォルトグループを選択する
     * <br>[備  考]
     * @param dfUsrSidStr 選択ユーザ
     * @throws SQLException SQL実行時例外
     */
    private void __selectUserOnly(String dfUsrSidStr) throws SQLException {
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        int userSid = NullDefault.getInt(
                dfUsrSidStr,
                -1);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int ecode = __canAccessUser(userSid);
        boolean canAccessUser = (ecode == ECODE_SUCCESS);
        usrSidStr__ = dfUsrSidStr;
        if (!canAccessUser) {
            __setErrorUser(ecode, dfUsrSidStr);
            userSid = sessionUsrSid;
            usrSidStr__ = String.valueOf(userSid);
        }
        //選択ユーザのデフォルトグループを選択する
        GroupBiz gbiz = new GroupBiz();
        int grpSid = gbiz.getDefaultGroupSid(userSid, con__);

        String dfGrpSidStr = String.valueOf(grpSid);
        grpSidStr__ = scBiz.getEnableSelectGroup(baseGrpLabelList__,
                dfGrpSidStr,
                String.valueOf(-1));
        if (!Objects.equals(grpSidStr__, dfGrpSidStr)) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con__);
//            * <br>選択ユーザのデフォルトグループが選択できない場合
//            * <br>ログインユーザが表示可能で所属の一致するグループを選択
            List<Integer> belongGroupMdlList = belongDao.selectUserBelongGroupSid(userSid);
            boolean search = false;
            for (LabelValueBean label : baseGrpLabelList__) {
                if (!label.getValue().startsWith(GSConstSchedule.MY_GROUP_STRING)) {
                    if (belongGroupMdlList.indexOf(Integer.valueOf(label.getValue())) >= 0) {
                        grpSidStr__ = label.getValue();
                        search = true;
                    }
                }
            }
            if (!search) {
//                * <br>一つも一致しなかった場合
//                * <br>・エラーメッセージを表示する
//                * <br>・ログインユーザを選択する
//                * <br>・ログインユーザのデフォルトグループを選択する
                __setErrorUser(ECODE_USR_PERMISSION, dfUsrSidStr);
                grpSidStr__ = String.valueOf(
                        gbiz.getDefaultGroupSid(sessionUsrSid, con__)
                        );
                usrSidStr__ = String.valueOf(sessionUsrSid);
                return;
            }

        }

    }
    /**
     *
     * <br>[機  能] ユーザSIDに対するアクセス権限チェック
     * <br>[解  説] 特例制限、不正なSIDを指定した場合はfalse
     * <br>[備  考]
     * @param userSid 判定するユーザSID
     * @return
     *  {@link SchUserGroupSelectInitBiz#ECODE_SUCCESS}
     *  {@link SchUserGroupSelectInitBiz#ECODE_USR_PERMISSION}
     *  {@link SchUserGroupSelectInitBiz#ECODE_USR_NONE}
     * @throws SQLException SQL実行時例外
     */
    private int __canAccessUser(int userSid) throws SQLException {
        boolean canAccessUser = (notAccessUserList__.indexOf(userSid) < 0);
        if (!canAccessUser) {
            return ECODE_USR_PERMISSION;
        }

        //scBiz.canAccessUserは不正なSIDをtrueで返すため追加判定
        if (userSid <= GSConstUser.USER_RESERV_SID) {
            return ECODE_USR_NONE;
        }
        UserSearchDao ussDao = new UserSearchDao(con__);
        CmnUsrmInfModel usrMdl = ussDao.getUserInfoJtkb(userSid, GSConst.JTKBN_TOROKU);
        if (usrMdl == null) {
            return ECODE_USR_NONE;
        }
        return ECODE_SUCCESS;
    }


    /**
     *
     * <br>[機  能] 選択ユーザ、 選択グループが設定時の選択状態を設定する
     * <br>[解  説]
     * <br> 選択グループを引き継ぐ
     * <br> 選択ユーザを引き継ぐ
     * <br> 選択ユーザが選択不可の場合
     * <br> ・エラーメッセージを表示する
     * <br> ・選択ユーザが未設定、 選択グループが設定時の動作を実行する
     * <br>  {@link SchUserGroupSelectInitBiz#__selectGroupOnly(String)}
     * <br>
     * <br> 選択ユーザが選択可能かつ選択グループに選択ユーザが所属しない場合
     * <br> 選択ユーザが選択可能かつ選択グループが選択できない場合
     * <br> ・選択ユーザが設定、選択グループが未設定時の処理を実行する
     * <br> {@link SchUserGroupSelectInitBiz#__selectUserOnly(String)}
     * <br>
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __selectGrpAndUser() throws SQLException {
        int userSid = NullDefault.getInt(
                srcUsrSidStr__,
                -1);
        if (userSid >= 0) {
            //選択ユーザがユーザSID
            __selectUserYusen();
            return;
        }
        //選択ユーザがユーザSID以外はグループ優先
        __selectGrpYusen();
    }


    /**
     * <br>[機  能] 選択ユーザ、 選択グループが設定時の選択状態を設定する
     * <br>[解  説]
     * <br> 選択グループが選択できない場合はログインユーザのデフォルトグループ
     * <br> 選択ユーザのデフォルトグループを選択する
     * <br>[備  考]
     * @throws SQLException
     */
    private void __selectGrpYusen() throws SQLException {
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        //選択グループが選択できない場合はログインユーザのデフォルトグループ
        //選択ユーザのデフォルトグループを選択する
        GroupBiz gbiz = new GroupBiz();
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int grpSid = gbiz.getDefaultGroupSid(sessionUsrSid, con__);
        String dfGrpSidStr = String.valueOf(grpSid);
        grpSidStr__ = scBiz.getEnableSelectGroup(baseGrpLabelList__,
                srcGrpSidStr__,
                dfGrpSidStr);
        usrSidStr__ = srcUsrSidStr__;

        if (!Objects.equals(grpSidStr__, srcGrpSidStr__)) {
            __setErrorGroup(srcGrpSidStr__);
        }
    }
    /**
    *
    * <br>[機  能] 選択ユーザ、 選択グループが設定時の選択状態を設定する
    * <br>[解  説]
    * <br> 選択グループを引き継ぐ
    * <br> 選択ユーザを引き継ぐ（選択ユーザがユーザSID以外を考慮しない）
    * <br> 選択ユーザが選択不可の場合
    * <br> ・エラーメッセージを表示する
    * <br> ・選択ユーザが未設定、 選択グループが設定時の動作を実行する
    * <br> {@link SchUserGroupSelectInitBiz#__selectGroupOnly(String)}
    * <br>
    * <br> 選択ユーザが選択可能かつ選択グループに選択ユーザが所属しない場合
    * <br> 選択ユーザが選択可能かつ選択グループが選択できない場合
    * <br> ・選択ユーザが設定、選択グループが未設定時の処理を実行する
    * <br> {@link SchUserGroupSelectInitBiz#__selectUserOnly(String)}
    * <br>
    * <br>[備  考] 選択ユーザSIDが不正な文字列の場合正常に動作しない
     * @throws SQLException SQL実行時例外
    */
    private void __selectUserYusen() throws SQLException {
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        int userSid = NullDefault.getInt(
                srcUsrSidStr__,
                -1);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        int ecode = __canAccessUser(userSid);
        boolean canAccessUser = (ecode == ECODE_SUCCESS);

        if (!canAccessUser) {
            __setErrorUser(ecode, srcUsrSidStr__);
            __selectGroupOnly(srcGrpSidStr__);
            return;
        }


        grpSidStr__ = scBiz.getEnableSelectGroup(baseGrpLabelList__,
                srcGrpSidStr__,
                String.valueOf(-1));

        usrSidStr__ = srcUsrSidStr__;

        if (!Objects.equals(grpSidStr__, srcGrpSidStr__)) {
//            * <br> 選択ユーザが選択可能かつ選択グループが選択できない場合
//            * <br> ・選択ユーザが設定、選択グループが未設定時の処理を実行する
            __selectUserOnly(srcUsrSidStr__);
            return;
        }

//        * <br> 選択ユーザが選択可能かつ選択グループに選択ユーザが所属しない場合
//      * <br> ・選択ユーザが設定、選択グループが未設定時の処理を実行する
        if (grpSidStr__.startsWith(GSConstSchedule.MY_GROUP_STRING)) {
            int mgpSid = Integer.valueOf(
                    grpSidStr__.substring(
                            GSConstSchedule.MY_GROUP_STRING.length()
                       )
                    );
            UserBiz usrBiz = new UserBiz();
            List<UsrLabelValueBean> usrList = usrBiz.getMyGroupUserLabelList(con__,
                    sessionUsrSid,
                    mgpSid, null);
            boolean search = false;
            for (UsrLabelValueBean user : usrList) {
                if (Objects.equals(user.getValue(), String.valueOf(userSid))) {
                    search = true;
                    break;
                }
            }
            if (!search) {
                __selectUserOnly(srcUsrSidStr__);
                return;
            }
        } else if (grpSidStr__.startsWith(GSConstSchedule.DSP_LIST_STRING)) {
            int smySid = Integer.valueOf(
                    grpSidStr__.substring(
                            GSConstSchedule.DSP_LIST_STRING.length()
                       )
                    );
            SchMyviewlistBelongDao myViewBelongDao = new SchMyviewlistBelongDao(con__);
            List<MyViewListBelongModel> viewList = myViewBelongDao.getBelongDataList(smySid);

            boolean grpFlg = srcUsrSidStr__.startsWith("G");
            int selectSid = -1;
            if (grpFlg) {
                selectSid = Integer.valueOf(srcUsrSidStr__.substring(0));
            } else {
                selectSid = Integer.valueOf(srcUsrSidStr__);
            }

            boolean search = false;
            for (MyViewListBelongModel viewMdl : viewList) {
                if (grpFlg && viewMdl.getGrpSid() > -1 && viewMdl.getGrpSid() == selectSid) {
                    search = true;
                    break;
                }

                if ((viewMdl.getUsrSid() > -1 && viewMdl.getUsrSid() == selectSid)) {
                    search = true;
                    break;
                }
            }

            if (!search) {
                __selectUserOnly(srcUsrSidStr__);
                return;
            }
        } else {
            CmnBelongmDao belongDao = new CmnBelongmDao(con__);
            List<Integer> belongGroupMdlList = belongDao.selectUserBelongGroupSid(userSid);
            if (belongGroupMdlList.indexOf(Integer.valueOf(grpSidStr__)) < 0) {
                __selectUserOnly(srcUsrSidStr__);
                return;
            }
        }

    }

    /**
     *
     * <br>[機  能]
     * <br>[解  説] 最終選択判定
     * <br>ユーザコンボリストを生成し
     * <br>選択ユーザがグループに所属しないか判定
     * <br>ユーザはユーザリストの最上段
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __finallySelect() throws SQLException {
        boolean search = false;
        String firstUsrSid = null;
        for (UsrLabelValueBean user : baseUsrLabelList__) {
            if (firstUsrSid == null) {
                firstUsrSid = user.getValue();
            }
            if (Objects.equals(user.getValue(), usrSidStr__)) {
                search = true;
                break;
            }
        }
        if (!search) {
            usrSidStr__ = firstUsrSid;
        }

    }
    /**
     * <br>[機  能] 指定グループに所属するユーザリストを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __createUserLabel() throws SQLException {
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        GsMessage gsMsg = new GsMessage(reqMdl__);

        List < UsrLabelValueBean > labelList = new ArrayList <UsrLabelValueBean>();

        int grpSid = -1;
        boolean mygroup = false;
        boolean dsplist = false;
        if (grpSidStr__.startsWith(GSConstSchedule.MY_GROUP_STRING)) {
            mygroup = true;
            grpSid = Integer.valueOf(
                       grpSidStr__.substring(
                            GSConstSchedule.MY_GROUP_STRING.length()
                       )
                    );
        } else if (grpSidStr__.startsWith(GSConstSchedule.DSP_LIST_STRING)) {
            dsplist = true;
            grpSid = Integer.valueOf(
                       grpSidStr__.substring(
                            GSConstSchedule.DSP_LIST_STRING.length()
                       )
                    );
        } else {
            grpSid = Integer.valueOf(grpSidStr__);
        }
        UserBiz usrBiz = new UserBiz();
        if (mygroup) {
            String[] notAccessUsers = notAccessUserList__.stream()
                                                         .map(u -> u.toString())
                                                         .toArray(String[]::new);
            
            CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con__);
            searchCnt__ = cmgmDao.countMyGroup(sessionUsrSid, grpSid, notAccessUsers, false);
            //limit/offsetが設定されている、かつoffsetが検索結果件数より大きい場合は取得処理を行わない
            if (limit__ >= 1 && offset__ >= 0 && searchCnt__ > offset__
                || limit__ < 1 && offset__ < 0) {
                labelList = usrBiz.getMyGroupUserLabelList(con__, sessionUsrSid, grpSid, notAccessUsers, limit__, offset__);
            }
        } else if (dsplist) {
            CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con__);
            CmnGroupmDao grpDao = new CmnGroupmDao(con__);

            //表示リストに指定されたグループ・ユーザを取得する
            SchMyviewlistBelongDao myViewBelongDao = new SchMyviewlistBelongDao(con__);
            List<MyViewListBelongModel> viewList = new ArrayList<MyViewListBelongModel>();
            
            searchCnt__ = myViewBelongDao.countBelongDataList(grpSid, notAccessUserList__, notAccessGrpList__);
            //limit/offsetが設定されている、かつoffsetが検索結果件数より大きい場合は取得処理を行わない
            if (limit__ >= 1 && offset__ >= 0 && searchCnt__ > offset__
                || limit__ < 1 && offset__ < 0) {
                viewList = myViewBelongDao.getBelongDataList(grpSid, notAccessUserList__, notAccessGrpList__, limit__, offset__);
            }

            for (MyViewListBelongModel viewMdl : viewList) {
                if (viewMdl.getUsrSid() > GSConstUser.USER_RESERV_SID) {
                    //ユーザ
                    CmnUsrmInfModel usrInfMdl = usrInfDao.getUsersInf(viewMdl.getUsrSid());
                    labelList.add(new UsrLabelValueBean(usrInfMdl));
                }
                if (viewMdl.getGrpSid() >= 0) {
                    //グループ
                    CmnGroupmModel grpMdl = grpDao.selectGroup(viewMdl.getGrpSid());
                    labelList.add(new UsrLabelValueBean(grpMdl));
                }
            }
        } else {
            String[] notAccessUsers = notAccessUserList__.stream()
                                                         .map(u -> u.toString())
                                                         .toArray(String[]::new);
            UserSearchDao usDao = new UserSearchDao(con__);
            searchCnt__ = usDao.getBelongUserCount(grpSid, new ArrayList<Integer>(notAccessUserList__));
            //limit/offsetが設定されている、かつoffsetが検索結果件数より大きい場合は取得処理を行わない
            if (limit__ >= 1 && offset__ >= 0 && searchCnt__ > offset__
                || limit__ < 1 && offset__ < 0) {
                    labelList = usrBiz.getNormalUserLabelList(con__, grpSid, notAccessUsers, false, gsMsg, limit__, offset__);
            }
        }

        if (listner__ instanceof ICreateSchLabelListListner) {
            labelList = listner__.addExUserLabelList(grpSidStr__, labelList);
        }
        baseUsrLabelList__ = labelList;
    }

    /**
     * <p>errorMsg を取得します。
     * @return errorMsg
     * @see jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz#errorMsg__
     */
    public SchErrorMessage getErrorMsg() {
        return errorMsg__;
    }
    /**
     *
     * <br>[機  能] 発生した選択エラーを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgKey エラーメッセージID
     * @param errorParams エラーオプションパラメータ
     */
    private void __setError(String msgKey, String... errorParams) {
        if (errorMsg__ != null) {
            return;
        }
        errorMsg__ = new SchErrorMessage(msgKey, errorParams);
    }
    private void __setErrorUser(int ecode, String dfUsrSidStr) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        __setError("error.can.not.select", gsMsg.getMessage("cmn.targeted.user"));

    }
    private void __setErrorGroup(String dfGrpSidStr) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        __setError("error.can.not.select", gsMsg.getMessage("cmn.targeted.group"));
    }



}
