package jp.groupsession.v2.prj.ptl020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] プロジェクト管理 プラグインポートレット TODO状態内訳のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPtl020Biz.class);

    /** プロジェクト一覧 1ページに表示する最大件数 */
    public static final int PRJ_MAXCOUNT = 10;
    /** ポートレットパラメータ プロジェクトSID */
    public static final String PRJ_PORTLET_PARAM1 = "prjPtl010PrjSid";

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param paramMdl PrjPtl020ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(RequestModel reqMdl, PrjPtl020ParamModel paramMdl,
                            Connection con)
    throws Exception {
        log__.debug("START");

        //プラグインポートレットコンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        paramMdl.setPortletTypeCombo(cmnBiz.getPluginPortletCombo(con, gsMsg,
                reqMdl.getDomain()));

        //プロジェクト一覧を設定する。
        __setProjectList(paramMdl, con);

        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl PrjPtl020ParamModel
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void insertData(PrjPtl020ParamModel paramMdl, Connection con,
                           PluginConfig pconfig) throws SQLException {

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        UDate now = new UDate();
        String itemId = now.getTimeStamp();
        CommonBiz cmnBiz = new CommonBiz();
        int prjSid = paramMdl.getPrjptl020selectPrjSid();

        if (ptlSid < 1 || prjSid < 1) {
            return;
        }

        //プラグイン選択済フラグ
        paramMdl.setPrjptl020selectFlg(true);

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
        String screenId = GSConstProject.SCREENID_PRJPTL020;

        //ポートレット画面IDを取得する。
        String dspScreenId =
            cmnBiz.getPluginPortletScreenId(pconfig, GSConstProject.PLUGIN_ID_PROJECT, screenId);

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(itemId);
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PLUGINPORTLET);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(GSConstProject.PLUGIN_ID_PROJECT);
        posiModel.setMscId(dspScreenId);
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        posiModel.setPtpParamkbn(GSConstPortal.PTP_PARAMKBN_ON);
        ptlPositionDao.insert(posiModel);

        //ポータル_位置設定_パラメータを登録する。
        PtlPortalPositionParamModel positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(1);
        positionParamModel.setPpmParamName(PRJ_PORTLET_PARAM1);
        positionParamModel.setPpmParamValue(String.valueOf(prjSid));
        positionParamDao.insert(positionParamModel);

    }
    /**
     * <br>[機  能] プロジェクト一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl PrjPtl020ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setProjectList(PrjPtl020ParamModel paramMdl, Connection con)
    throws SQLException {

        int ptlSid = paramMdl.getPtlPortalSid();
        PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);

        //既に設定しているプロジェクトを取得する。
        List<PtlPortalPositionParamModel> paramList
                = paramDao.getParamList(ptlSid, PRJ_PORTLET_PARAM1);
        List<Integer> existPrjSidList = new ArrayList<Integer>(paramList.size());
        for (PtlPortalPositionParamModel param : paramList) {
            existPrjSidList.add(NullDefault.getInt(param.getPpmParamValue(), 0));
        }

        PrjPrjdataDao prjDao = new PrjPrjdataDao(con);
        int prjCount = prjDao.getProjectCount(existPrjSidList);
        if (prjCount < 1) {
            return;
        }

        //ページ調整
        int maxPage = prjCount / PRJ_MAXCOUNT;
        if ((prjCount % PRJ_MAXCOUNT) > 0) {
            maxPage++;
        }
        int page = paramMdl.getPrjptl020pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }

        paramMdl.setPrjptl020pageTop(page);
        paramMdl.setPrjptl020pageBottom(page);
        //ページコンボ設定
        paramMdl.setPageCombo(PageUtil.createPageOptions(prjCount, PRJ_MAXCOUNT));
        int start = (page - 1) * PRJ_MAXCOUNT + 1;
        int end = start + PRJ_MAXCOUNT - 1;

        //通常プロジェクト一覧を取得する。
        List<PrjPrjdataModel> prjList = prjDao.getProjectList(existPrjSidList, start, end);
        paramMdl.setPrjptl020dspList(prjList);
    }
}