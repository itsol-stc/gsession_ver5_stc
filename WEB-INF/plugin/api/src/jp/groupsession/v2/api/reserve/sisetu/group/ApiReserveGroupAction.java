package jp.groupsession.v2.api.reserve.sisetu.group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisKbnDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvSisKbnModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] 施設予約グループ取得WEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiReserveGroupAction extends AbstractApiAction {

    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //施設予約プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_RESERVE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_RESERVE));
            return null;
        }
        RequestModel reqMdl = getRequestModel(req);
        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID

        CommonBiz cmnBiz = new CommonBiz();

        //施設予約の管理者
        boolean rsvAdmin = cmnBiz.isPluginAdmin(con, umodel, GSConstSchedule.PLUGIN_ID_RESERVE);

        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        RsvUserModel rsvUserConf = rsvCmnBiz.getRevUserModel(sessionUsrSid, con);

        int dfReservGpSid = GSConstReserve.COMBO_DEFAULT_VALUE;
        if (rsvUserConf != null) {
            dfReservGpSid = rsvUserConf.getRsgSid();
        }

        List<RsvSisGrpModel> list = __getReserveGroupList(con, sessionUsrSid, rsvAdmin, reqMdl);
        Map<Integer, RsvSisKbnModel> kbnMap = __getReserveKbnMap(con);



        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        if (list != null) {
            int index = 0;
            for (RsvSisGrpModel model : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                //Rsgsid グループSID
                result.addContent(_createElement("RsgSid", model.getRsgSid()));

                //GrpName グループ名
                result.addContent(_createElement("RsgName", model.getRsgName()));

                // グループID
                result.addContent(_createElement("RsgId",
                        NullDefault.getString(model.getRsgId(), "")));
                // 施設区分SID
                result.addContent(_createElement("RskSid", String.valueOf(model.getRskSid())));
                // 施設区分名
                if (kbnMap.containsKey(model.getRskSid())) {
                    RsvSisKbnModel kbn = kbnMap.get(model.getRskSid());
                    result.addContent(_createElement("RskName", kbn.getRskName()));
                } else {
                    result.addContent(_createElement("RskName", ""));
                }
                if (dfReservGpSid == Integer.valueOf(model.getRsgSid())) {
                    resultSet.setAttribute("DefaultIndex", String.valueOf(index));
                }
                index++;
            }
            resultSet.setAttribute("Count", String.valueOf(list.size()));
        }
        log__.debug("createXml end");

        return doc;
    }
    /**
     *
     * <br>[機  能] 施設グループ一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param admFlg 管理者フラグ
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     * @return 施設グループリスト
     */
    private List<RsvSisGrpModel> __getReserveGroupList(
            Connection con, int sessionUsrSid, boolean admFlg,
            RequestModel reqMdl) throws SQLException {

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> ret = null;

        if (admFlg) {
            //全グループ取得
            ret = dao.selectAllGroupData();
        } else {
            //閲覧可能グループ取得
            ret = dao.getCanReadData(sessionUsrSid);
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textAll = gsMsg.getMessage("cmn.all");
        RsvSisGrpModel top = new RsvSisGrpModel();
        top.setRsgSid(GSConstReserve.COMBO_DEFAULT_VALUE);
        top.setRsgName(textAll);
        ret.add(0, top);

        return ret;
    }
    /**
     *
     * <br>[機  能] 施設グループ区分一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return 施設区分マップ
     */
    private Map<Integer, RsvSisKbnModel> __getReserveKbnMap(
            Connection con) throws SQLException {
        RsvSisKbnDao dao = new RsvSisKbnDao(con);
        ArrayList<RsvSisKbnModel> ret = null;
        ret = dao.selectAllGrpKbn();
        HashMap<Integer, RsvSisKbnModel> map = new HashMap<Integer, RsvSisKbnModel>();

        for (RsvSisKbnModel rsvSisKbnModel__ : ret) {
            map.put(rsvSisKbnModel__.getRskSid(), rsvSisKbnModel__);
        }
        return map;
    }
}
