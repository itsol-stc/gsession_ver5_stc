package jp.groupsession.v2.ptl.ptl030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlPortalSortDao;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.ptl.ptl030.model.Ptl030Model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル ポータル管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl030Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ptl030ParamModel paramMdl, Connection con)
    throws SQLException {
        log__.debug("START");

        PtlPortalDao ptlDao = new PtlPortalDao(con);

        //ポータル一覧を取得する
        List<PtlPortalModel> portalList = ptlDao.getPortaList(GSConstPortal.PTS_KBN_COMMON);

        //表示用のポータル一覧を生成する。
        List<Ptl030Model> dspList = new ArrayList<Ptl030Model>();
        Ptl030Model dspModel = null;
        if (portalList != null && portalList.size() > 0) {
            for (PtlPortalModel model : portalList) {
                dspModel = new Ptl030Model();
                dspModel.setPtlDescriptionView(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(model.getPtlDescription()), ""));
                dspModel.setPtlName(model.getPtlName());
                dspModel.setPtlOpen(model.getPtlOpen());
                dspModel.setPtlSid(model.getPtlSid());
                dspList.add(dspModel);
            }
        }

        paramMdl.setPtl030portalList(dspList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] ポータルの並び順を上へ移動する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行例外
     */
    public void updateSort(Ptl030ParamModel paramMdl, Connection con, int changeKbn)
    throws SQLException {

        SortResult<PtlPortalModel> result = null;
        final PtlPortalDao dao = new PtlPortalDao(con);
        final PtlPortalSortDao sortDao = new PtlPortalSortDao(con);
        int motoSid = paramMdl.getPtl030sortPortal();

        SortChangeBiz<PtlPortalModel> sortBiz =
                SortChangeBiz.<PtlPortalModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getPortaList(GSConstPortal.PTS_KBN_COMMON);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getPtlSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getPtsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getPtlSid() == m2.getPtlSid()) {
                        return 0;
                    } else {
                        return (m1.getPtlSid() - m2.getPtlSid()) 
                                / Math.abs(m1.getPtlSid() - m2.getPtlSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    sortDao.updateSort(m.getPtlSid(), 0, newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            paramMdl.setPtl030sortPortal(result.getMdl().getPtlSid());
        }

    }
}
