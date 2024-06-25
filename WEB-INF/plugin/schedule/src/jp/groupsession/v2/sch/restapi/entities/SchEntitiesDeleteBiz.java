package jp.groupsession.v2.sch.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.ScheduleExSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch041kn.Sch041knBiz;

public class SchEntitiesDeleteBiz {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(SchEntitiesDeleteBiz.class);

    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 実行前*/
    private SchEntitiesResultModel prev__ = null;
    /** パラメータ*/
    private SchEntitiesDeleteParamModel param__;
    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     */
    public SchEntitiesDeleteBiz(SchEntitiesDeleteParamModel param,
            RestApiContext ctx) {
        param__ = param;
        ctx__ = ctx;
    }

    public void execute() throws SQLException {
        boolean defAutoCommit = ctx__.getCon().getAutoCommit();
        //実行前状態取得
        prev__ = __createResult(param__.getScheduleSid());

        ctx__.getCon().setAutoCommit(false);
        param__.defaultInit(ctx__);
        //登録処理
        __deleteSchedule();
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);


    }
    private void __deleteSchedule() throws SQLException {
        Connection con = ctx__.getCon();
        RequestModel reqMdl = ctx__.getRequestModel();

        ScheduleSearchDao ssDao = new ScheduleSearchDao(con);
        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        Sch041knBiz sch041knBiz = new Sch041knBiz(con, reqMdl);
        SchCommonBiz cBiz = new SchCommonBiz(con, reqMdl);

        int scdSid = param__.getScheduleSid();
        ArrayList<Integer> scds = ssDao.getScheduleUsrs(
                param__.getScheduleSid(),
                ctx__.getRequestUserSid(),
                param__.getAconf(ctx__).getSadCrange(),
                GSConstSchedule.SSP_AUTHFILTER_EDIT
                );
        ScheduleExSearchModel sceMdl =
                biz.getSchExData(param__.getScheduleSid(), param__.getAconf(ctx__), con);
        log__.debug("削除処理実行");

        //施設予約を削除
        if (param__.getSameFacilityReserveEditFlg() == 1) {
            //施設予約拡張情報を取得
            RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con);
            RsvSisRyrkModel ryrkMdl = ryrkDao.selectFromScdSid(scdSid);

            //同時登録施設予約があれば削除
            int cnt = biz.deleteReserve(scdSid, con, param__.getOldData(ctx__));
            log__.debug("施設予約削除件数=>" + cnt);

            //ひも付いている施設予約情報が無くなった場合、予約拡張データを削除
            if (ryrkMdl != null) {
                int rsrRsid = ryrkMdl.getRsrRsid();
                RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
                if (rsrRsid > -1 && yrkDao.getYrkDataCnt(rsrRsid) < 1) {
                    //件数取得し0件の場合
                    ryrkDao.delete(rsrRsid);
                    //施設予約拡張区分別情報削除
                    RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con);
                    kryrkDao.delete(rsrRsid);
                    //施設予約拡張情報_公開対象の削除
                    RsvExdataPubDao repDao = new RsvExdataPubDao(con);
                    repDao.delete(rsrRsid);
                }
            }
        }

        if (param__.getSameScheduledEditFlg() == 0 || scds.size() < 1) {
            //同時登録反映無しの場合
            cBiz.deleteSchedule(scdSid);
        } else if (param__.getSameScheduledEditFlg() == 1) {

            //同時登録ユーザへ反映更新
            if (scds.size() > 1) {
                cBiz.deleteSchedule(scds);
            } else {
                cBiz.deleteSchedule(scdSid);
            }
        }
        //ひも付いているスケジュール情報が無い拡張データを削除
        if (sceMdl != null) {
            int sceSid = sceMdl.getSceSid();
            if (ssDao.getExCount(sceSid) == 0) {
                sch041knBiz.deleteScheduleEx(sceSid, con, ctx__.getRequestUserSid());
            }
        }

    }

    /**
    *
    * <br>[機  能] 実行結果モデルの生成
    * <br>[解  説]
    * <br>[備  考]
    * @param resultScheduleSid 対象SID
    * @return 実行結果モデル
    */
   private SchEntitiesResultModel __createResult(int resultScheduleSid) {
       SchEntitiesGetParamModel qParam = new SchEntitiesGetParamModel();
       qParam.setScheduleSid(resultScheduleSid);
       SchEntitiesQueryBiz qBiz = new SchEntitiesQueryBiz(
               qParam,
               ctx__);
       qBiz.execute();
       List<SchEntitiesResultModel> result = qBiz.getResult();
       if (result.size() > 0) {
           return result.get(0);
       }
       return null;
   }

    public SchEntitiesResultModel getPrev() {
        return prev__;
    }

}
