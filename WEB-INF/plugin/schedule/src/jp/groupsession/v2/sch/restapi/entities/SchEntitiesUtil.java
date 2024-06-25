package jp.groupsession.v2.sch.restapi.entities;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;

public class SchEntitiesUtil {
    /**
     * <br>[機  能] 施設予約登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @param rsvSid 施設SID
     * @return 施設予約登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public static  String createReserveUrl(RestApiContext ctx, int rsvSid) {
        UDate now = new UDate();
        String date = now.getDateString();
        RequestModel reqMdl = ctx.getRequestModel();

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);
            paramUrl += "/" + GSConstReserve.PLUGIN_ID_RESERVE;
            paramUrl += "/rsv110.do";

            paramUrl += "?rsv110ProcMode=" + Integer.parseInt(GSConstReserve.PROC_MODE_EDIT);
            paramUrl += "&rsv110RsySid=" + rsvSid;
            paramUrl += "&rsvDspFrom=" + date;

            String reserveUrl = urlBiz.getAccessUrl(reqMdl, paramUrl);
            return reserveUrl;
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new RuntimeException("URL生成に失敗", e);
        }
    }
    /**
     * <br>[機  能] スケジュール一般登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @param cmd 処理モード
     * @param sch010SchSid スケジュールSID
     * @param usrSid ユーザーSID
     * @param frDateStr スケジュール日付文字列(yyyyMMdd)
     * @param userKbn ユーザー区分（0:ユーザ 1:グループ）
     * @return スケジュール一般登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public static String createScheduleUrlDefo(
            RestApiContext ctx,
            String cmd,
            String sch010SchSid, String usrSid,
            String frDateStr,
            String userKbn) {


        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(ctx.getRequestModel());
            paramUrl += "/" + GSConstSchedule.PLUGIN_ID_SCHEDULE;
            paramUrl += "/sch040.do";
            paramUrl += "?sch010SelectDate=" + frDateStr;
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&sch010SchSid=" + sch010SchSid;
            paramUrl += "&sch010SelectUsrSid=" + usrSid;
            paramUrl += "&sch010SelectUsrKbn=" + userKbn;
            paramUrl += "&sch010DspDate=" + frDateStr;
            paramUrl += "&dspMod=" + GSConstSchedule.DSP_MOD_WEEK;

            return urlBiz.getAccessUrl(ctx.getRequestModel(), paramUrl);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new RuntimeException("URL生成に失敗", e);
        }
    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSidList スケジュールSID
     * @param contactFlg コンタクト履歴変更有無
     * @throws SQLException SQL実行時例外
     */
    public static void deleteSchCompany(Connection con, List<Integer> scdSidList, int contactFlg)
            throws SQLException {

        SchCompanyDao companyDao = new SchCompanyDao(con);
        companyDao.delete(scdSidList);

        SchDao dao = new SchDao(con);
        SchAddressDao addressDao = new SchAddressDao(con);
        for (Integer scdSid : scdSidList) {
            if (contactFlg == 1) {
                dao.deleteScheduleContact(con, scdSid);
            }
            addressDao.delete(scdSid);
        }
    }
    /**
     * <br>[機  能] 既存スケジュール情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param deleteScdSidList 削除対象リスト
     * @param contactEditFlg コンタクト履歴編集フラグ
     * @throws SQLException
     */
    public static void deleteSchedule(
            RestApiContext ctx,
            List<Integer> deleteScdSidList, int contactEditFlg) throws SQLException {
        Connection con = ctx.getCon();
        SchBinDao binDao = new SchBinDao(con);
        SchPushListDao splDao = new SchPushListDao(con);
        SchDataPubDao sdpDao = new SchDataPubDao(con);
        SchDataDao schDao = new SchDataDao(con);
        schDao.delete(new ArrayList<Integer>(deleteScdSidList));
        splDao.delete(new ArrayList<Integer>(deleteScdSidList));

        binDao.deleteTempFile(deleteScdSidList);

        //編集元の公開対象を削除
        sdpDao.delete(deleteScdSidList);

        //変更前スケジュールの会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を削除
        deleteSchCompany(con, deleteScdSidList, contactEditFlg);
    }


}
