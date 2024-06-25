package jp.groupsession.v2.api.reserve.sisetu.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] 施設一覧取得WEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiReserveListAction extends AbstractApiAction {

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
        ApiReserveListForm form = (ApiReserveListForm) aForm;

        RequestModel reqMdl = getRequestModel(req);

        ActionErrors err = form.validateCheck(new GsMessage(reqMdl));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        CommonBiz cmnBiz = new CommonBiz();

        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID

        //施設予約の管理者
        boolean rsvAdmin = cmnBiz.isPluginAdmin(con, umodel, GSConstSchedule.PLUGIN_ID_RESERVE);

//      除外する施設SIDを設定
        ArrayList<Integer> resSids = new ArrayList<Integer>();

        ArrayList<RsvSisDataModel> list = __getSisList(con
                , NullDefault.getInt(form.getRsgSid(), 0)
                , rsvAdmin
                , resSids, sessionUsrSid);
        Map<Integer, Integer> kbnMap = __getReserveKbnMap(con);
        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        if (list != null) {
            for (RsvSisDataModel mdl : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                //Rsdsid 施設SID
                result.addContent(_createElement("RsdSid", mdl.getRsdSid()));
                //RsdIｄ   施設ID
                result.addContent(_createElement("RsdId", mdl.getRsdId()));
                //RsdName 施設名
                result.addContent(_createElement("RsdName", mdl.getRsdName()));
                // 資産管理番号
                result.addContent(_createElement("RsdKanriNo", mdl.getRsdSnum()));
                // 重複登録
                result.addContent(_createElement("AbleDoubleRegist", mdl.getRsdProp7()));
                // 備考
                result.addContent(_createElement("Biko", mdl.getRsdBiko()));
                // 事前予約可能期限
                result.addContent(_createElement("MaxPrev", mdl.getRsdProp6()));
                // 場所
                result.addContent(_createElement("Place", mdl.getRsdPlaCmt()));

                //    以下のフィールドは特定の施設区分によって追加される
                Integer rskSid = kbnMap.get(mdl.getRsgSid());
                if (rskSid == null) {
                    continue;
                }
                if (rskSid == GSConstReserve.RSK_KBN_HEYA
                        || rskSid == GSConstReserve.RSK_KBN_CAR) {
                    //    ＜施設区分が部屋または車の場合＞
                    // 座席数/乗員数
                    result.addContent(_createElement("ZasekiCount", mdl.getRsdProp1()));
                    // 喫煙
                    result.addContent(_createElement("AbleSmoke", mdl.getRsdProp2()));
                }
                if (rskSid == GSConstReserve.RSK_KBN_BUPPIN
                        || rskSid == GSConstReserve.RSK_KBN_BOOK) {
                    //    ＜施設区分が物品または書籍の場合＞
                    // 個数（冊数）
                    result.addContent(_createElement("Kosuu", mdl.getRsdProp1()));
                    // 社外持出
                    result.addContent(_createElement("Motidashi", mdl.getRsdProp3()));
                }
                if (rskSid == GSConstReserve.RSK_KBN_CAR) {
                    //    ＜施設区分が車の場合＞
                    // ナンバー
                    result.addContent(_createElement("SyaryouNp", mdl.getRsdProp4()));
                }
                if (rskSid == GSConstReserve.RSK_KBN_BOOK) {
                    //    ＜施設区分が書籍の場合＞
                    // ISBN
                    result.addContent(_createElement("ISBN", mdl.getRsdProp5()));
                }
            }
            resultSet.setAttribute("Count", String.valueOf(list.size()));
        }
        log__.debug("createXml end");
        return doc;
    }
    /**
     *
     * <br>[機  能] 施設一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsgSid 施設グループSID
     * @param rsvAdmin 施設管理者権限
     * @param resSids 除外SID
     * @param sessionUsrSid セッションユーザSID
     * @return 施設一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisDataModel> __getSisList(
            Connection con, int rsgSid,
            boolean rsvAdmin, ArrayList<Integer> resSids,
            int sessionUsrSid) throws SQLException {
        RsvSisDataDao dataDao = new RsvSisDataDao(con);
        ArrayList<RsvSisDataModel> list = null;
        if (rsvAdmin) {
            //全施設を取得する
            list =
                dataDao.selectGrpSisetuList(
                        rsgSid,
                        resSids);
        } else {
            //アクセス権限のある施設を取得
            list =
                dataDao.selectGrpSisetuCanEditList(
                        rsgSid,
                        resSids, sessionUsrSid);
        }
        return list;
    }
    /**
     *
     * <br>[機  能] 施設グループ区分一覧取得
     * <br>[解  説] グループSIDをキーに持つマップを返す
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return 施設区分マップ
     */
    private Map<Integer, Integer> __getReserveKbnMap(
            Connection con) throws SQLException {
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> grpList = grpDao.selectAllGroupData();

        HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();
        for (RsvSisGrpModel grpModel : grpList) {
            ret.put(grpModel.getRsgSid(), grpModel.getRskSid());
        }
        return ret;
    }
}
