package jp.groupsession.v2.sch.restapi.otherplugin_entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchAppendDataParam;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchOtherEntitiesQueryBiz {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(SchOtherEntitiesQueryBiz.class);
    /** パラメータ*/
    private final SchOtherEntitiesQueryParamModel param__;
    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 取得結果*/
    private List<SchOtherEntitiesResultModel> result__ = new ArrayList<>();
    /** 結果 最大件数*/
    private int resultMaxCount__ = 0;
    /***/
    GsMessage gsMsg__;

    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     */
    public SchOtherEntitiesQueryBiz(SchOtherEntitiesQueryParamModel param,
            RestApiContext ctx) {
        param__ = param;
        ctx__ = ctx;
        gsMsg__ = new GsMessage(ctx.getRequestModel());
    }
    public Integer getMaxCount() {
        return resultMaxCount__;
    }

    /**
     * <p>result を取得します。
     * @return result
     */
    public List<SchOtherEntitiesResultModel> getResult() {
        return result__;
    }
    public void execute() {

        try {
            result__  = __getAppendPlgData();
            resultMaxCount__ = __getAppendPlgData().size();
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行に失敗", e);
        }

    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @return 外部スケジュール検索結果
     * @throws SQLException
     */
    private List<SchOtherEntitiesResultModel> __getAppendPlgData() throws SQLException {
        List<SchOtherEntitiesResultModel> result = new ArrayList<>();

        Connection con = ctx__.getCon();
        RequestModel reqMdl = ctx__.getRequestModel();
        //他プラグイン情報を取得
        int sessionUsrSid = ctx__.getRequestUserSid();

        //プラグイン設定を取得する
        PluginConfig pconfig
            = ctx__.getPluginConfig();

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl);

        AuthDao adao = new AuthDao(ctx__.getCon());
        BaseUserModel smodel = null;
        smodel = adao.selectLoginNoPwd(param__.getUserId(), null);
        if (smodel == null) {
            throw new RestApiValidateException(
                    EnumError.PARAM_IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx__.getRequestModel())
                    .getMessage("cmn.user"))
            .setParamName("userId");
        }



        int usrSid = smodel.getUsrsid();
        //From
        UDate frDate = param__.getFromDate();
        //To
        UDate toDate = param__.getToDate();

        if (pconfig != null) {
            UDate prmFrDate = frDate.cloneUDate();
            UDate prmToDate = toDate.cloneUDate();
            SchAppendDataParam paramMdl = new SchAppendDataParam();
            paramMdl.setUsrSid(usrSid);
            paramMdl.setFrDate(prmFrDate);
            paramMdl.setToDate(prmToDate);
            paramMdl.setSrcId(GSConstSchedule.DSP_ID_SCH010);


            GsMessage gsMsg = new GsMessage(reqMdl);
            //年
            String textYear = gsMsg.getMessage("cmn.year", new String[] {frDate.getStrYear()});
            //月
            String textMonth = gsMsg.getMessage("cmn.month");
            String dspDate = textYear + frDate.getStrMonth() + textMonth;
            paramMdl.setDspDate(dspDate);
            List<SchDataModel> tmpSchList = new ArrayList<SchDataModel>();
            try {
                tmpSchList = scBiz.getAppendSchData(reqMdl, con, pconfig, paramMdl);
            } catch (Exception e) {
                log__.error("他プラグインのスケジュールデータ取得に失敗", e);
            }

            Set<Integer> userSidSet =
                    tmpSchList.stream()
                    .flatMap(scData -> {
                        Stream.Builder<Integer> builder = Stream.builder();
                        if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                            // 対象ユーザ
                            builder.accept(scData.getScdUsrSid());
                        }
                        return builder.build();
                    })
                    .collect(Collectors.toSet());
            userSidSet.add(usrSid);
            //ユーザ情報Map
            Map<Integer, CmnUserModel> userMap = new HashMap<Integer, CmnUserModel>();
            if (userSidSet.size() > 0) {
                List<CmnUserModel> uMdlList =
                        new UserSearchDao(ctx__.getCon())
                        .getUsersDataList(userSidSet);
                if (uMdlList != null) {
                    for (CmnUserModel uMdl : uMdlList) {
                        userMap.put(uMdl.getUsrSid(), uMdl);
                    }
                }
            }


            //公開区分反映

            //予定あり
            String textYoteiari = gsMsg.getMessage("schedule.src.9");

            for (SchDataModel schDataModel__ : tmpSchList) {

                if (GSConstCommon.PLUGIN_ID_NIPPOU.equals(schDataModel__.getScdAppendId())) {
                    schDataModel__.setScdAppendDspName(gsMsg.getMessage("cmn.action"));
                } else if (GSConstCommon.PLUGIN_ID_PROJECT
                        .equals(schDataModel__.getScdAppendId())) {
                    schDataModel__.setScdAppendDspName("TODO");
                } else {
                    schDataModel__.setScdAppendDspName("");
                }

                usrSid = schDataModel__.getScdUsrSid();
                int usrKbn = schDataModel__.getScdUsrKbn();
                if (usrKbn == GSConstSchedule.USER_KBN_USER
                        && usrSid == sessionUsrSid) {
                    //本人
                } else if (usrKbn == GSConstSchedule.USER_KBN_USER
                        && usrSid != sessionUsrSid) {
                    //他ユーザ
                    if (schDataModel__.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
                        //予定あり
                        schDataModel__.setScdTitle(textYoteiari);
                        schDataModel__.setScdValue("");
                        schDataModel__.setScdBiko("");
                    } else if (schDataModel__.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                        //非公開
                        continue;
                    }
                }
                result.add(
                        new SchOtherEntitiesResultModel(
                                schDataModel__,
                                userMap.get(schDataModel__.getScdUsrSid())));

            }

        }
        return result;
    }

}
