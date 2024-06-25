package jp.groupsession.v2.rsv.restapi.facility_groups.facility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設一覧情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("reserve")
public class RsvFacilityGroupsFacilitiesAction extends AbstractRestApiAction {

    /**
    *
    * <br>[機  能] 施設一覧情報API
    * <br>[解  説] POST
    * <br>[備  考] 施設グループ情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Get
   public void doGet(
           HttpServletRequest req,
           HttpServletResponse res,
           RsvFacilityGroupsFacilitiesGetParamModel param,
           RestApiContext ctx) throws SQLException {

       //施設予約プラグインアクセス権限確認
       List<String> pluginList = new ArrayList<String>();
       pluginList.add(GSConst.PLUGIN_ID_RESERVE);
       if (!canAccessPlugin(pluginList)) {
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "error.cant.use.plugin",
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.reserve")
                   );
       }

       //アクセス可能グループ取得
       RsvSisGrpDao grpDao = new RsvSisGrpDao(ctx.getCon());
       ArrayList<RsvSisGrpModel> grpList = grpDao.getCanReadData(
               ctx.getRequestUserModel().getUsrsid());

       //指定したグループが含まれているか確認する。
       RsvSisGrpModel targetGrpMdl = null;
       for (RsvSisGrpModel grpMdl : grpList) {
           if (grpMdl.getRsgId().equals(param.getGroupId())) {
               targetGrpMdl = grpMdl;
               break;
           }
       }
       if (targetGrpMdl == null) {
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "search.data.notfound",
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.facility.group")
                   );
       }

       //施設予約の管理者
       CommonBiz cmnBiz = new CommonBiz();
       boolean rsvAdmin = cmnBiz.isPluginAdmin(
               ctx.getCon(), ctx.getRequestUserModel(), GSConstSchedule.PLUGIN_ID_RESERVE);

       //施設一覧取得
       int grpSid = Optional.ofNullable(
               targetGrpMdl
               )
               .map(grp -> grp.getRsgSid())
               .orElse(-1);
       RsvSisDataDao dataDao = new RsvSisDataDao(ctx.getCon());
       ArrayList<RsvSisDataModel> sisetuList = null;
       if (rsvAdmin) {
           //全施設取得
           sisetuList = dataDao.selectGrpSisetuList(
                   grpSid, new ArrayList<Integer>());
       } else {
           //閲覧可能施設取得
           sisetuList = dataDao.selectGrpSisetuCanEditList(
                   grpSid, new ArrayList<Integer>(),
                   ctx.getRequestUserModel().getUsrsid());
       }

       //リザルト
       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       for (RsvSisDataModel sisetuMdl : sisetuList) {
           ResultElement result = new ResultElement("result");
           result.addContent(new ResultElement("sid").addContent(
                   String.valueOf(sisetuMdl.getRsdSid())));
           result.addContent(new ResultElement("id").addContent(
                   sisetuMdl.getRsdId()));
           result.addContent(new ResultElement("name").addContent(
                   sisetuMdl.getRsdName()));
           result.addContent(new ResultElement("codeText").addContent(
                   sisetuMdl.getRsdSnum()));
           result.addContent(new ResultElement("ableDuplecateFlg").addContent(
                   sisetuMdl.getRsdProp7()));
           result.addContent(new ResultElement("memoText").addContent(
                   sisetuMdl.getRsdBiko()));
           result.addContent(new ResultElement("preReserveNum").addContent(
                   sisetuMdl.getRsdProp6()));
           result.addContent(new ResultElement("placeText").addContent(
                   sisetuMdl.getRsdPlaCmt()));
           //以下は施設区分によって異なる値
           Integer rskSid = targetGrpMdl.getRskSid();
           if (rskSid == GSConstReserve.RSK_KBN_HEYA
                   || rskSid == GSConstReserve.RSK_KBN_CAR) {
               result.addContent(new ResultElement("chaireCountNum").addContent(
                       sisetuMdl.getRsdProp1()));
               result.addContent(new ResultElement("ableTabacoFlg").addContent(
                       sisetuMdl.getRsdProp2()));
           }
           if (rskSid == GSConstReserve.RSK_KBN_BUPPIN
                   || rskSid == GSConstReserve.RSK_KBN_BOOK) {
               result.addContent(new ResultElement("countNum").addContent(
                       sisetuMdl.getRsdProp1()));
               result.addContent(new ResultElement("ablePickupFlg").addContent(
                       sisetuMdl.getRsdProp3()));
           }
           if (rskSid == GSConstReserve.RSK_KBN_CAR) {
               result.addContent(new ResultElement("registrationNumberText").addContent(
                       sisetuMdl.getRsdProp4()));
           }
           if (rskSid == GSConstReserve.RSK_KBN_BOOK) {
               result.addContent(new ResultElement("isbnText").addContent(
                       sisetuMdl.getRsdProp5()));
           }
           builder.addResult(result);
       }
       builder.build().execute();
   }
}
