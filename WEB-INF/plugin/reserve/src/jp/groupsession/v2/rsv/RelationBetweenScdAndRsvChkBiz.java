package jp.groupsession.v2.rsv;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvScdOperationModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.RsvSchPriConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュールと施設の関連をチェックするための機能を実装したクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RelationBetweenScdAndRsvChkBiz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;

    /** チェック区分 単一予約編集時 */
    public static final int CHK_KBN_TANITU = 1;
    /** チェック区分 繰り返し予約編集時 */
    public static final int CHK_KBN_KURIKAESHI = 2;

    /** エラーコード エラー無し */
    public static final int ERR_CD_NON_ERR = 0;
    /** エラーコード 該当する施設予約情報が無い */
    public static final int ERR_CD_YRK_NONE = 1;
    /** エラーコード 施設予約に関連付いたスケジュールが無い */
    public static final int ERR_CD_SCD_REL_NONE = 2;
    /** エラーコード スケジュール変更不可 */
    public static final int ERR_CD_SCD_CANNOT_EDIT = 3;
    /** エラーコード 該当するスケジュール情報が無い */
    public static final int ERR_CD_SCD_NONE = 4;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public RelationBetweenScdAndRsvChkBiz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /**
     * <br>[機  能] セッションユーザModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return usModel セッションユーザModel
     */
    private BaseUserModel __getSessionUserModel() {
        return reqMdl__.getSmodel();
    }

    /**
     * <br>[機  能] 施設予約SIDを元に関連付いたスケジュールの
     * <br>         編集権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 施設予約SID
     * @param kbn チェック区分
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isScdEdit(int rsySid, int kbn) throws SQLException {

        BaseUserModel usrMdl = __getSessionUserModel();

        //管理者グループに所属している
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, usrMdl, GSConstReserve.PLUGIN_ID_SCHEDULE);
        if (adminUser) {
            return ERR_CD_NON_ERR;
        }

        //施設予約情報取得
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);
        RsvSisYrkModel selParam = new RsvSisYrkModel();
        selParam.setRsySid(rsySid);
        RsvSisYrkModel yrkRet = yrkDao.select(selParam);

        if (yrkRet == null) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

       /*****************************************************************
        *
        * スケジュール情報取得。
        *
        * スケジュールが同時登録されている場合は
        * 複数件のレコードが存在するため全レコードを取得する。
        *
        *****************************************************************/
        RsvScdOperationDao scdDao = new RsvScdOperationDao(con__);
        ArrayList<RsvScdOperationModel> scdRet = null;
        if (yrkRet.getScdRsSid() < 0) {
            //エラー 関連付いたスケジュールが無い
            return ERR_CD_SCD_REL_NONE;
        }
        if (kbn == CHK_KBN_TANITU) {
            scdRet = scdDao.selectSchList(yrkRet.getScdRsSid());
        } else if (kbn == CHK_KBN_KURIKAESHI) {
            scdRet = scdDao.selectSchListGrpSceSid(yrkRet.getScdRsSid());
        }

        if (scdRet.isEmpty()) {
            //エラー 関連付いたスケジュールが無い
            return ERR_CD_SCD_REL_NONE;
        }

       /*****************************************************************
        *
        * 取得したスケジュール情報の権限を判定。
        *
        * 編集権限が「制限なし」以外の場合に「本人」「登録者」の
        * チェックを行う。
        *
        *****************************************************************/
        //セッションユーザSID取得
        int sessionUsrSid = usrMdl.getUsrsid();

        boolean scdErr = false;
        HashMap<Integer, Integer> userMap = new HashMap<Integer, Integer>();
        for (RsvScdOperationModel scdMdl : scdRet) {

            //編集権限取得
            int scdEdit = scdMdl.getScdEdit();

            if (scdEdit != GSConstReserve.EDIT_AUTH_NONE) {
                //本人・登録者であるか判定
                if (sessionUsrSid != scdMdl.getScdUsrSid()
                        && sessionUsrSid != scdMdl.getScdAuid()) {
                    scdErr = true;
                    if (scdEdit == GSConstReserve.EDIT_AUTH_GRP_AND_ADU) {
                        userMap.put(scdMdl.getScdUsrSid(), scdMdl.getScdUsrSid());
                    }
                }
            }
        }

        /*****************************************************************
         *
         * 権限設定が「所属グループ・本人」かつ、
         * その条件に該当しなかったユーザのSIDを元に
         * 「同グループかどうか」の判定を行う。
         *
         *****************************************************************/
        boolean grpErr = false;
        if (!userMap.isEmpty()) {
            ArrayList<Integer> userArray = new ArrayList<Integer>(userMap.values());
            for (int userSid : userArray) {
                //同じグループに所属しているか判定
                if (!scdDao.isScdEditTi(userSid, sessionUsrSid)) {
                    grpErr = true;
                }
            }
            if (!grpErr) {
                scdErr = false;
            }
        }

        if (scdErr || grpErr) {
            return ERR_CD_SCD_CANNOT_EDIT;
        }

        return ERR_CD_NON_ERR;
    }

    /**
     * <br>[機  能] 施設予約SIDを元に自分自身の予約データが
     * <br>         編集可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid スケジュールSID
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isEditMeRsv(int rsySid) throws SQLException {

        BaseUserModel usrMdl = __getSessionUserModel();
        int sessionUsrSid = usrMdl.getUsrsid();

        //管理者グループに所属している
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, usrMdl, GSConstReserve.PLUGIN_ID_RESERVE);
        if (adminUser) {
            return ERR_CD_NON_ERR;
        }

        //施設予約情報取得
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);
        RsvSisYrkModel selParam = new RsvSisYrkModel();
        selParam.setRsySid(rsySid);
        RsvSisYrkModel yrkRet = yrkDao.select(selParam);

        if (yrkRet == null) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

        RsvScdOperationDao opDao = new RsvScdOperationDao(con__);
        ArrayList<RsvScdOperationModel> yrkList = null;

        //繰り返しチェック
        if (yrkRet.getRsrRsid() > 0) {
            yrkList = opDao.selectRsvList(yrkRet.getRsrRsid());
        //単一チェック
        } else {
            yrkList = opDao.selectRsvMdl(rsySid);
        }

        if (yrkList.isEmpty()) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

        //施設グループ管理者情報取得
        HashMap<String, String> rsvGrpAdmMap =
            opDao.selectRsvGrpAdmMap(sessionUsrSid);

       /*****************************************************************
        *
        * 取得した施設情報の権限を判定。
        *
        * 編集権限が「制限なし」以外の場合に「予約者」「使用者」の
        * チェックを行う。
        *
        *****************************************************************/
        boolean rsvErr = false;
        boolean grpChkFlg = false;
        for (RsvScdOperationModel rsvMdl : yrkList) {

            //編集権限取得
            int rsvEdit = rsvMdl.getRsyEdit();

            if (rsvEdit != GSConstReserve.EDIT_AUTH_NONE) {

                //施設グループ管理者か判定
                if (!rsvGrpAdmMap.isEmpty()) {
                    int rsgSid = rsvMdl.getRsgSid();
                    String rsvGrpKey =
                        rsgSid + "-" + sessionUsrSid;

                    if (rsvGrpAdmMap.containsKey(rsvGrpKey)) {
                        continue;
                    }
                }

                //本人または登録者か判定
                if (sessionUsrSid != rsvMdl.getRsyAuid()) {
                    if (rsvEdit == GSConstReserve.EDIT_AUTH_GRP_AND_ADU) {
                        if (!grpChkFlg) {
                            if (!opDao.isScdEditTi(rsvMdl.getRsyAuid(), sessionUsrSid)) {
                                rsvErr = true;
                            }
                            grpChkFlg = true;
                        }
                    } else {
                        rsvErr = true;
                    }
                }
            }
        }

        if (rsvErr) {
            return ERR_CD_SCD_CANNOT_EDIT;
        }

        return ERR_CD_NON_ERR;
    }

    /**
     * <br>[機  能] スケジュールSIDを元に関連付いた施設予約の
     * <br>         編集権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdSid スケジュールSID
     * @param kbn チェック区分
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isRsvEdit(int scdSid, int kbn) throws SQLException {

        BaseUserModel usrMdl = __getSessionUserModel();

        //管理者グループに所属している
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, usrMdl, GSConstReserve.PLUGIN_ID_RESERVE);

        if (adminUser) {
            return ERR_CD_NON_ERR;
        }

       /*****************************************************************
        *
        * ベースになるスケジュール情報取得。
        *
        *****************************************************************/
        RsvScdOperationDao scdDao = new RsvScdOperationDao(con__);
        RsvScdOperationModel scdBase = scdDao.selectSchMdl(scdSid);

        //該当するスケジュール情報が無い
        if (scdBase == null) {
            return ERR_CD_SCD_NONE;
        }

       /*****************************************************************
        *
        * チェック対象のスケジュール情報取得。
        *
        *****************************************************************/
        RsvScdOperationModel scdRet = null;
        if (kbn == CHK_KBN_TANITU) {
            int scdRsSid = scdBase.getScdRsSid();
            if (scdRsSid < 0) {
//              エラー 関連付いたスケジュールが無い
                return ERR_CD_SCD_NONE;
            }
            scdRet = scdDao.selectSchMdlGrpRssid(scdRsSid);
        } else if (kbn == CHK_KBN_KURIKAESHI) {
            scdRet = scdDao.selectSchMdlGrpSceSid(scdSid);
        }

        if (scdRet == null) {
            //エラー 関連付いたスケジュールが無い
            return ERR_CD_SCD_NONE;
        }

       /*****************************************************************
        *
        * セッションユーザSIDの施設グループ管理者情報を取得。
        *
        *****************************************************************/
        //施設グループ管理者マッピング作成
        int sessionUsrSid = usrMdl.getUsrsid();
        HashMap<String, String> rsvGrpAdmMap =
            scdDao.selectRsvGrpAdmMap(sessionUsrSid);

       /*****************************************************************
        *
        * 施設情報取得。
        *
        *****************************************************************/
        //スケジュールリレーションSIDマップを配列に変換
        ArrayList<Integer> rssidArray =
            new ArrayList<Integer>(scdRet.getRssidMap().values());
        Collections.sort(rssidArray);

        ArrayList<RsvScdOperationModel> rsvArray =
            scdDao.selectRsvList(rssidArray);

        if (rsvArray.isEmpty()) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

       /*****************************************************************
        *
        * 取得した施設情報の権限を判定。
        *
        * 編集権限が「制限なし」以外の場合に「予約者」「使用者」の
        * チェックを行う。
        *
        *****************************************************************/
        boolean rsvErr = false;
        boolean grpChkFlg = false;
        HashMap<String, String> usrMap = scdRet.getUsrMap();
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        for (RsvScdOperationModel rsvMdl : rsvArray) {

            //編集権限取得
            int rsvEdit = rsvMdl.getRsyEdit();

            if (rsvEdit != GSConstReserve.EDIT_AUTH_NONE) {

                //施設グループ管理者か判定
                if (!rsvGrpAdmMap.isEmpty()) {
                    int rsgSid = rsvMdl.getRsgSid();
                    String rsvGrpKey =
                        rsgSid + "-" + sessionUsrSid;

                    if (rsvGrpAdmMap.containsKey(rsvGrpKey)) {
                        continue;
                    }
                }

                int rssid = rsvMdl.getScdRsSid();
                String key = rssid + "-" + sessionUsrSid;

                //予約者または使用者か判定
                if (sessionUsrSid != rsvMdl.getRsyAuid()
                        && !usrMap.containsKey(key)) {

                    if (rsvEdit == GSConstReserve.EDIT_AUTH_GRP_AND_ADU) {
                        if (!grpChkFlg) {
                            if (!scdDao.isScdEditTi(rsvMdl.getRsyAuid(), sessionUsrSid)) {
                                rsvErr = true;
                            }
                            grpChkFlg = true;
                        }
                    } else {
                        rsvErr = true;
                    }
                }

            }

            if (!rsvErr) {
                //施設予約の閲覧が可能かを判定
                int powrange = rsvCmnBiz.isWithinPubilicRange(
                        con__, rsvMdl.getRsySid(), sessionUsrSid);

                rsvErr = powrange != GSConstReserve.POWRANGE_IN;
            }
        }

        if (rsvErr) {
            return ERR_CD_SCD_CANNOT_EDIT;
        }

        return ERR_CD_NON_ERR;
    }

    /**
     * <br>[機  能] 指定したグループのグループスケジュールを登録できるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param grpSid グループSID
     * @param sessionUsrSid セッションユーザSID
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException SQL実行時例外
     */
    public boolean validateGroupForSchedule(
                                ActionErrors errors,
                                String grpSid,
                                int sessionUsrSid,
                                String paramName) throws SQLException {


        //管理者設定を取得する。
        RsvScdOperationDao scdOpeDao = new RsvScdOperationDao(con__);
        RsvSchAdmConfModel admModel = scdOpeDao.getAdmConf();

        //共有範囲 = 全員で共有の場合、チェックを行わない
        if (admModel == null || admModel.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            return true;
        }


        //所属グループかを判定
        GroupBiz grpBiz = new GroupBiz();
        if (!grpBiz.isBelongGroup(sessionUsrSid, Integer.parseInt(grpSid), con__)) {
            String fieldfix = paramName + ".";
            ActionMessage msg = new ActionMessage("error.cant.entry.notbelong.grpschedule");
            StrutsUtil.addMessage(errors, msg, fieldfix + "error.cant.entry.notbelong.grpschedule");
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 日付チェック(スケジュール)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param dateList 日付(開始日時、終了日時の組み合わせ)
     * @param users ユーザ
     * @param scdRsSid スケジュール予約SID
     * @param copyFlg 複写フラグ
     * @param sessionUsrSid セッションユーザSID
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException SQL実行時例外
     */
    public boolean validateDateForSchedule(
                                ActionErrors errors,
                                List<UDate[]> dateList,
                                String[] users,
                                int scdRsSid, boolean copyFlg,
                                int sessionUsrSid,
                                String paramName) throws SQLException {

        int[] scdRsSidAry = new int[0];
        if (scdRsSid > 0) {
            scdRsSidAry = new int[] {scdRsSid};
        }

        return  validateDateForSchedule(errors, dateList, users, scdRsSidAry,
                                        copyFlg, sessionUsrSid, paramName);
    }

    /**
     * <br>[機  能] 日付チェック(スケジュール)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param dateList 日付(開始日時、終了日時の組み合わせ)
     * @param users ユーザ
     * @param scdRsSid スケジュール予約SID
     * @param copyFlg 複写フラグ
     * @param sessionUsrSid セッションユーザSID
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException SQL実行時例外
     */
    public boolean validateDateForSchedule(
                                ActionErrors errors,
                                List<UDate[]> dateList,
                                String[] users,
                                int[] scdRsSid,
                                boolean copyFlg,
                                int sessionUsrSid,
                                String paramName) throws SQLException {

        String fieldfix = paramName + ".";

        List<RsvScdOperationModel> rptSchList = getRepertSchList(dateList, users,
                                                                scdRsSid, copyFlg, sessionUsrSid);

        if (rptSchList != null && rptSchList.size() > 0) {

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String textSchedule = gsMsg.getMessage("schedule.108");
            ActionMessage msg = new ActionMessage("error.input.dup", textSchedule);
            StrutsUtil.addMessage(errors, msg, "error.input.dup");
            String title = "";

            RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
            int num = 1;
            for (RsvScdOperationModel ngMdl : rptSchList) {

                //公開区分で判定してタイトルを取得
                title = rsvSchBiz.getDspTitle(reqMdl__, con__, ngMdl, sessionUsrSid);

                String schTime = UDateUtil.getYymdJ(ngMdl.getScdFrDate(), reqMdl__);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdFrDate(), reqMdl__);
                schTime += "～";
                schTime += UDateUtil.getYymdJ(ngMdl.getScdToDate(), reqMdl__);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdToDate(), reqMdl__);

                msg = new ActionMessage("error.input.dup.sch",
                        schTime,
                        StringUtilHtml.transToHTmlPlusAmparsant(title),
                        StringUtilHtml.transToHTmlPlusAmparsant(ngMdl.getScdUserName()));
                StrutsUtil.addMessage(errors, msg, fieldfix + "error.input.dup.sch. " + num);
                num++;
            }
        }

        return true;
    }

    /**
     * <br>[機  能] 重複するスケジュールの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateList 日付(開始日時、終了日時の組み合わせ)
     * @param users ユーザ
     * @param scdRsSid スケジュール予約SID
     * @param copyFlg 複写フラグ
     * @param sessionUsrSid セッションユーザSID
     * @return 重複するスケジュールの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<RsvScdOperationModel> getRepertSchList(List<UDate[]> dateList, String[] users,
                                                    int scdRsSid, boolean copyFlg,
                                                    int sessionUsrSid)
    throws SQLException {

        int[] scdRsSidAry = new int[0];
        if (scdRsSid > 0) {
            scdRsSidAry = new int[] {scdRsSid};
        }

        return getRepertSchList(dateList, users, scdRsSidAry,
                                copyFlg, sessionUsrSid);
    }

    /**
     * <br>[機  能] 重複するスケジュールの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateList 日付(開始日時、終了日時の組み合わせ)
     * @param users ユーザ
     * @param scdRsSid スケジュール予約SID
     * @param copyFlg 複写フラグ
     * @param sessionUsrSid セッションユーザSID
     * @return 重複するスケジュールの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<RsvScdOperationModel> getRepertSchList(List<UDate[]> dateList, String[] users,
                                                    int[] scdRsSid, boolean copyFlg,
                                                    int sessionUsrSid)
    throws SQLException {

        List<RsvScdOperationModel> rptSchList = new ArrayList<RsvScdOperationModel>();

        //自分の予定の場合は編集可能フラグ
        boolean mySchOkFlg = false;

        //管理者設定を取得する
        RsvScdOperationDao scdOpeDao = new RsvScdOperationDao(con__);
        RsvSchAdmConfModel admConf = scdOpeDao.getAdmConf();

        //管理者設定のスケジュール重複設定を使用するか
        boolean useAdmRepeatKbn
            = admConf != null
                && admConf.getSadRepeatStype() == GSConstSchedule.SAD_REPEAT_STYPE_ADM;

        if (useAdmRepeatKbn) {
            mySchOkFlg = admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                        && admConf.getSadRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK;
        } else {
            //個人設定を取得する。
            RsvSchPriConfModel priModel = scdOpeDao.getPriConf(sessionUsrSid);

            mySchOkFlg = priModel != null
                    && priModel.getSccRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                    && priModel.getSccRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK;
        }

        //ユーザリストを作成
        List<Integer> usrList = new ArrayList<Integer>();
        if (users != null && users.length > 0) {
            for (String paramUser : users) {
                if (mySchOkFlg && sessionUsrSid == Integer.parseInt(paramUser)) {
                    continue;
                }
                usrList.add(Integer.parseInt(paramUser));
            }
        }

        List<Integer> ngUsrList = null;
        if (!useAdmRepeatKbn) {
            //重複登録不可にしているユーザリストを取得
            ngUsrList
                = scdOpeDao.getUsrSidListRepeatKbn(usrList, GSConstSchedule.SCH_REPEAT_KBN_NG);
        } else {
            if (admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG) {
                ngUsrList = new ArrayList<Integer>();
                ngUsrList.addAll(usrList);
            }
        }
        if (ngUsrList != null && ngUsrList.size() > 0) {
            //重複登録しているスケジュール一覧を取得する。
            rptSchList = new ArrayList<RsvScdOperationModel>();
            for (UDate[] chkDate : dateList) {
                List<RsvScdOperationModel> schList
                    = scdOpeDao.getSchData(ngUsrList, scdRsSid, chkDate[0], chkDate[1], copyFlg);
                if (schList != null && !schList.isEmpty()) {
                    rptSchList.addAll(schList);
                }
            }
        }

        return rptSchList;
    }


    /**
     * <br>[機  能] 指定したグループのグループスケジュールを登録できるかチェックする
     * <br>[解  説] スケジュール特例アクセス用のチェック
     * <br>[備  考]
     * @param errors ActionErrors
     * @param grpSid グループSID
     * @param sessionUsrSid セッションユーザSID
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException SQL実行時例外
     */
    public boolean validateSpCaceGroupForSchedule(
                                ActionErrors errors,
                                String grpSid,
                                int sessionUsrSid,
                                String paramName) throws SQLException {

        //スケジュール特例アクセス設定で制御されていないかチェックを行う
        SchDao schDao = new SchDao(con__);
        List<Integer> notAccGrpList = schDao.getNotRegistGrpList(sessionUsrSid);
        for (int gsid : notAccGrpList) {
            if (gsid == Integer.parseInt(grpSid)) {
                CmnGroupmDao dao = new CmnGroupmDao(con__);
                CmnGroupmModel grpMdl = dao.select(Integer.parseInt(grpSid));
                String fieldfix = paramName + ".";
                ActionMessage msg = new ActionMessage(
                        "error.cant.entry.grpschedule",
                        StringUtilHtml.transToHTml(NullDefault.getString(grpMdl.getGrpName(), "")));
                StrutsUtil.addMessage(errors, msg, fieldfix + "error.cant.entry.grpschedule");
                return false;
            }
        }

        return true;
    }

    /**
     * <br>[機  能] 指定したユーザのスケジュールを登録できるかチェックする
     * <br>[解  説] スケジュール特例アクセス用のチェック
     * <br>[備  考]
     * @param errors ActionErrors
     * @param users ユーザSID配列
     * @param sessionUsrSid セッションユーザSID
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException SQL実行時例外
     */
    public boolean validateSpCaceUserForSchedule(
                                ActionErrors errors,
                                String[] users,
                                int sessionUsrSid,
                                String paramName) throws SQLException {

        //スケジュール特例アクセス設定で制御されていないかチェックを行う
        SchDao schDao = new SchDao(con__);
        List<Integer> notAccUserList = schDao.getNotRegistUserList(sessionUsrSid);

        CmnUsrmInfDao uDao = new CmnUsrmInfDao(con__);
        for (String usid : users) {
            if (notAccUserList.indexOf(Integer.parseInt(usid)) >= 0) {
                CmnUsrmInfModel uMdl = uDao.select(Integer.parseInt(usid));
                String fieldfix = paramName + ".";
                ActionMessage msg = new ActionMessage(
                        "error.cant.entry.userschedule",
                        StringUtilHtml.transToHTml(uMdl.getUsiSei() + " " + uMdl.getUsiMei()));
                StrutsUtil.addMessage(errors, msg, fieldfix + "error.cant.entry.userschedule");
                return false;
            }
        }

        return true;
    }
}