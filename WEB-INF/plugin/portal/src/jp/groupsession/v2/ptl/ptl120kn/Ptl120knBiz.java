package jp.groupsession.v2.ptl.ptl120kn;

import java.sql.Connection;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletCategorySortDao;
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl120knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl120knBiz.class);

    /**
     * <br>[機  能] 登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @throws Exception 例外
     */
    public void insert(Ptl120knParamModel paramMdl,
                       Connection con,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {

        log__.debug("insert_start");
        //カテゴリ情報登録
        PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con);
        PtlPortletCategoryModel insertMdl = createModel(paramMdl, con, usrSid, cntCon);
        dao.insert(insertMdl);

        //並び順登録
        PtlPortletCategorySortDao sortDao = new PtlPortletCategorySortDao(con);
        sortDao.insertMaxSort(insertMdl.getPlcSid());
        log__.debug("insert_end");

    }

    /**
     * <br>[機  能] 更新処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @throws Exception 例外
     */
    public void update(Ptl120knParamModel paramMdl,
                       Connection con,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {

        log__.debug("update_start");
        PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con);

        PtlPortletCategoryModel updateMdl = createModel(paramMdl, con, usrSid, cntCon);
        dao.update(updateMdl);
        log__.debug("update_end");
    }

    /**
     * <br>[機  能] 登録・更新用のmodel作成を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @return RngTemplateCategoryModel 登録model
     * @throws Exception 例外
     */
    public PtlPortletCategoryModel createModel(Ptl120knParamModel paramMdl,
                                                Connection con,
                                                int usrSid,
                                                MlCountMtController cntCon)
                                                throws Exception {

        PtlPortletCategoryModel model = new PtlPortletCategoryModel();

        //登録
        if (paramMdl.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
            //カテゴリSID採番
            int catSid = (int) cntCon.getSaibanNumber(GSConstPortal.PLUGIN_ID,
                                                       GSConstPortal.SBNSID_SUB_PLT_CATEGORY,
                                                       usrSid);
            model.setPlcSid(catSid);

        //更新
        } else if (paramMdl.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {

            model.setPlcSid(paramMdl.getPtlPtlCategorytSid());
        }

        model.setPlcName(paramMdl.getPtl120name());
        model.setPlcBiko(paramMdl.getPtl120biko());

        return model;
    }

}
