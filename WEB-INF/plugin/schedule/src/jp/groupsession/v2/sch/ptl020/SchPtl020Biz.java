package jp.groupsession.v2.sch.ptl020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
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
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] スケジュール ポートレット グループスケジュール選択画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchPtl020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchPtl020Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /** ポートレットパラメータ グループSID */
    public static final String SCH_PORTLET_PARAM1 = "schDspGrpSid";
    /** ポートレットパラメータ アイテムID */
    public static final String SCH_PORTLET_PARAM2 = "schPtl010ItemId";

    /**
     * デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public SchPtl020Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl SchPtl020ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(SchPtl020ParamModel paramMdl, Connection con)
    throws SQLException {
        log__.debug("START");

        CommonBiz cmnBiz = new CommonBiz();

        //プラグインポートレットコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> comboList = cmnBiz.getPluginPortletCombo(con, gsMsg,
                reqMdl__.getDomain());
        paramMdl.setSchPtl020PluginPortletList(comboList);

        //グループ一覧を設定する。
        __setGroupList(paramMdl, con);

        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl SchPtl020ParamModel
     * @param con コネクション
     * @param pconfig PluginConfig
     * @throws SQLException SQL実行例外
     */
    public void insertData(SchPtl020ParamModel paramMdl,
                                Connection con, PluginConfig pconfig) throws SQLException {

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        int grpSid = paramMdl.getSchptl020selectGrpSid();
        UDate now = new UDate();
        String itemId = now.getTimeStamp();
        CommonBiz cmnBiz = new CommonBiz();

        //プラグイン選択済フラグ
        paramMdl.setSchptl020selectFlg(true);

        if (ptlSid < 1 || grpSid < 0) {
            return;
        }
        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList
                = layoutDao.getLayoutList(ptlSid, GSConstPortal.LAYOUT_VIEW_ON);

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
        String screenId = GSConstSchedule.SCREENID_SCHPTL020;

        //ポートレット画面IDを取得する。
        String dspScreenId =
            cmnBiz.getPluginPortletScreenId(pconfig, GSConstSchedule.PLUGIN_ID_SCHEDULE, screenId);

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(itemId);
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PLUGINPORTLET);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(GSConstSchedule.PLUGIN_ID_SCHEDULE);
        posiModel.setMscId(dspScreenId);
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        posiModel.setPtpParamkbn(GSConstPortal.PTP_PARAMKBN_ON);
        ptlPositionDao.insert(posiModel);

        //ポータル_位置設定_パラメータを登録する。
        PtlPortalPositionParamModel positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(1);
        positionParamModel.setPpmParamName(SCH_PORTLET_PARAM1);
        positionParamModel.setPpmParamValue(String.valueOf(grpSid));
        positionParamDao.insert(positionParamModel);

        //ポータル_位置設定_パラメータ2を登録する。
        positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(2);
        positionParamModel.setPpmParamName(SCH_PORTLET_PARAM2);
        positionParamModel.setPpmParamValue(itemId);
        positionParamDao.insert(positionParamModel);

    }

    /**
     * <br>[機  能] 表示するグループ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl SchPtl020ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setGroupList(SchPtl020ParamModel paramMdl, Connection con) throws SQLException {

        GroupBiz grpBiz = new GroupBiz();

        PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();

        //既に設定しているグループ情報を取得する。
        List<PtlPortalPositionParamModel> paramList
                = paramDao.getParamList(ptlSid, SCH_PORTLET_PARAM1);

        //グループ一覧を取得する。
        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);

        List<Integer> delGrpSidList = new ArrayList<Integer>();
        for (PtlPortalPositionParamModel model : paramList) {
            delGrpSidList.add(NullDefault.getInt(model.getPpmParamValue(), 0));
        }

        ArrayList<GroupModel> dspList = new ArrayList<GroupModel>();
        for (GroupModel mdl : grpList) {
            if (!delGrpSidList.contains(mdl.getGroupSid())) {
                dspList.add(mdl);
            }
        }

        paramMdl.setSchptl020dspList(dspList);

    }
}