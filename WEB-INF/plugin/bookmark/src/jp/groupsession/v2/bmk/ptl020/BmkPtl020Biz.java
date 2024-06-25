package jp.groupsession.v2.bmk.ptl020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ポータル グループブックマーク管理画面のビジネスロジック
 * @author JTS
 */
public class BmkPtl020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkPtl020Biz.class);
    /** パラメーター名1 */
    public static final String BMK_PORTLET_PARAM1 = "bmkGrpSid";
    /** パラメーター名2 */
    public static final String BMK_PORTLET_PARAM2 = "bmkptl010ItemId";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public BmkPtl020Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkPtl020ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(BmkPtl020ParamModel paramMdl, Connection con)
    throws Exception {
        log__.debug("START");

        //プラグインポートレットコンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setPortletTypeCombo(cmnBiz.getPluginPortletCombo(con, gsMsg,
                reqMdl__.getDomain()));

        //表示グループを設定
        __setGroupList(paramMdl, con);

        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkPtl020ParamModel
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void insertData(BmkPtl020ParamModel paramMdl,
                                Connection con, PluginConfig pconfig) throws SQLException {

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        UDate now = new UDate();
        String itemId = now.getTimeStamp();
        CommonBiz cmnBiz = new CommonBiz();

        int groupSid = paramMdl.getBmkptl020GrpSid();
        //プラグイン選択済フラグ
        paramMdl.setBmkptl020selectFlg(true);

        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList
                = layoutDao.getLayoutList(ptlSid,
                                        GSConstPortal.LAYOUT_VIEW_ON);

        //プラグインを追加するポジションを取得する。
        List<Integer> positionSidList = new ArrayList<Integer>();
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_LEFT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_CENTER);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_RIGHT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_TOP);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_BOTTOM);

        int plyPosition = GSConstPortal.LAYOUT_POSITION_LEFT;
        boolean endFlg = false;
        for (Integer position : positionSidList) {

            for (PtlPortalLayoutModel model : layoutList) {
                if (model.getPlyPosition() == position) {
                    plyPosition = position;
                    endFlg = true;
                    break;
                }
            }
            if (endFlg) {
                break;
            }
        }

        //ポータル位置情報の最大値を取得する。
        int maxSort = ptlPositionDao.getMaxSort(ptlSid, plyPosition);

        //選択画面ID
        String screenId = GSConstBookmark.SCREENID_BMKPTL020;

        //ポートレット画面IDを取得する。
        String dspScreenId =
            cmnBiz.getPluginPortletScreenId(pconfig, GSConstBookmark.PLUGIN_ID_BOOKMARK, screenId);

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(now.getTimeStamp());
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PLUGINPORTLET);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(GSConstBookmark.PLUGIN_ID_BOOKMARK);
        posiModel.setMscId(dspScreenId);
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        posiModel.setPtpParamkbn(GSConstPortal.PTP_PARAMKBN_ON);
        ptlPositionDao.insert(posiModel);

        //ポータル_位置設定_パラメータを登録する。(1つ目 groupSid)
        PtlPortalPositionParamModel positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(1);
        positionParamModel.setPpmParamName(BMK_PORTLET_PARAM1);
        positionParamModel.setPpmParamValue(String.valueOf(groupSid));
        positionParamDao.insert(positionParamModel);

        //ポータル_位置設定_パラメータを登録する(2つ目 itemId)
        positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(2);
        positionParamModel.setPpmParamName(BMK_PORTLET_PARAM2);
        positionParamModel.setPpmParamValue(String.valueOf(itemId));
        positionParamDao.insert(positionParamModel);

    }

    /**
     * <br>[機  能] 表示するグループ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkPtl020ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setGroupList(BmkPtl020ParamModel paramMdl, Connection con) throws SQLException {

        PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        GroupBiz grpBiz = new GroupBiz();
        //全グループのリスト
        ArrayList<GroupModel> tree = grpBiz.getGroupCombList(con);
        if (tree == null) {
            tree = new ArrayList<GroupModel>();
        }

        //すでに設定されているグループのリスト
        List<PtlPortalPositionParamModel> list = paramDao.getParamList(ptlSid, BMK_PORTLET_PARAM1);
        if (list == null) {
            list = new ArrayList<PtlPortalPositionParamModel>();
        }

        //グループSIDのリストを作成
        List<Integer> grepSidList = new ArrayList<Integer>();
        for (PtlPortalPositionParamModel model : list) {
            int sid = NullDefault.getInt(model.getPpmParamValue(), 0);
            grepSidList.add(sid);
        }

        ArrayList<GroupModel> dspList = new ArrayList<GroupModel>();
        for (GroupModel grepModel : tree) {
            if (!grepSidList.contains(grepModel.getGroupSid())) {
                dspList.add(grepModel);
            }
        }
        paramMdl.setTree(dspList);

    }

}
