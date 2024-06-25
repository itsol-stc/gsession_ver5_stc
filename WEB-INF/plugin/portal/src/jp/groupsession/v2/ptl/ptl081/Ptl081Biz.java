package jp.groupsession.v2.ptl.ptl081;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ポータル ポートレット選択画面(ポップアップ)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl081Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl081Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ptl081ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {
        log__.debug("START");

        int categorySid = paramMdl.getPtl081Category();

        PtlPortletCategoryDao portletCateDao = new PtlPortletCategoryDao(con);
        PtlPortletDao portletDao = new PtlPortletDao(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textAll = gsMsg.getMessage("cmn.all");
        String textNoCategory = gsMsg.getMessage("cmn.category.no");

        //カテゴリ一覧を取得する。
        List<PtlPortletCategoryModel> categoryList = portletCateDao.selectSortAll();
        List<LabelValueBean> dspCateList = new ArrayList<LabelValueBean>();
        dspCateList.add(new LabelValueBean(textAll, "-1"));
        dspCateList.add(new LabelValueBean(textNoCategory, "0"));

        for (PtlPortletCategoryModel cateModel : categoryList) {
            dspCateList.add(new LabelValueBean(cateModel.getPlcName(),
                                               String.valueOf(cateModel.getPlcSid())));
        }
        paramMdl.setPtl081CategoryList(dspCateList);

        //ポートレット一覧を取得する。
        ArrayList<PtlPortletModel> dspList = portletDao.selectInCategory(categorySid, 1, reqMdl);

        paramMdl.setPtl081dspList(dspList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] ポートレット追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void insertData(Ptl081ParamModel paramMdl, Connection con) throws SQLException {

        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        UDate now = new UDate();
        if (ptlSid < 1) {
            return;
        }


        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList =
            layoutDao.getLayoutList(ptlSid, GSConstPortal.LAYOUT_VIEW_ON);

        //ポートレットを追加するポジションを取得する。
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

        int portletSid = paramMdl.getPtl081selectPortletSid();
        if (portletSid < 1) {
            return;
        }
        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(now.getTimeStamp());
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PORTLET);
        posiModel.setPltSid(portletSid);
        posiModel.setPctPid(null);
        posiModel.setMscId(null);
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        ptlPositionDao.insert(posiModel);

        //ポートレット選択済フラグ
        paramMdl.setPtl081selectFlg(true);
    }
}