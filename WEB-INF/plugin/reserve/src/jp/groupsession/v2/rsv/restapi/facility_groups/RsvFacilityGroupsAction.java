package jp.groupsession.v2.rsv.restapi.facility_groups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisKbnDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvSisKbnModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設グループ情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("reserve")
public class RsvFacilityGroupsAction extends AbstractRestApiAction {

    /**
    *
    * <br>[機  能] 施設グループ情報API
    * <br>[解  説] GET
    * <br>[備  考] 施設グループ情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Get
   public void doGet(
           HttpServletRequest req,
           HttpServletResponse res,
           RestApiContext ctx) throws SQLException {

       //施設予約プラグインアクセス権限確認
       List<String> pluginList = new ArrayList<String>();
       pluginList.add(GSConst.PLUGIN_ID_RESERVE);
       if (!canAccessPlugin(pluginList)) {
           throw new RestApiValidateException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "error.cant.use.plugin",
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.reserve")
                   );
       }

       //施設予約の管理者
       CommonBiz cmnBiz = new CommonBiz();
       boolean rsvAdmin = cmnBiz.isPluginAdmin(
               ctx.getCon(), ctx.getRequestUserModel(), GSConstSchedule.PLUGIN_ID_RESERVE);

       //施設グループ取得
       RsvSisGrpDao grpDao = new RsvSisGrpDao(ctx.getCon());
       List<RsvSisGrpModel> grpList = new ArrayList<RsvSisGrpModel>();
       if (rsvAdmin) {
           //全グループ取得
           grpList = grpDao.selectAllGroupData();
       } else {
           //閲覧可能グループ取得
           grpList = grpDao.getCanReadData(ctx.getRequestUserModel().getUsrsid());
       }

       //施設グループ区分取得
       RsvSisKbnDao kbnDao = new RsvSisKbnDao(ctx.getCon());
       ArrayList<RsvSisKbnModel> kbnList = kbnDao.selectAllGrpKbn();
       HashMap<Integer, RsvSisKbnModel> kbnMap = new HashMap<Integer, RsvSisKbnModel>();
       for (RsvSisKbnModel rsvSisKbnModel__ : kbnList) {
           kbnMap.put(rsvSisKbnModel__.getRskSid(), rsvSisKbnModel__);
       }

       //リザルト
       List<RsvFacilityGroupsGetResultModel> resultList
       = new ArrayList<RsvFacilityGroupsGetResultModel>();
       for (RsvSisGrpModel grpMdl : grpList) {
           RsvFacilityGroupsGetResultModel resultMdl = new RsvFacilityGroupsGetResultModel();
           resultMdl.setSid(grpMdl.getRsgSid());
           resultMdl.setId(grpMdl.getRsgId());
           resultMdl.setName(grpMdl.getRsgName());
           resultMdl.setType(grpMdl.getRskSid());
           resultMdl.setTypeName(kbnMap.get(grpMdl.getRskSid()).getRskName());
           resultList.add(resultMdl);
       }
       RestApiResponseWriter.builder(res, ctx)
       .addResultList(resultList)
       .build().execute();
   }
}
