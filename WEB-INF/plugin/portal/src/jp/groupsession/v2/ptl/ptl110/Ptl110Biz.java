package jp.groupsession.v2.ptl.ptl110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletCategorySortDao;
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;
import jp.groupsession.v2.ptl.model.PtlPortletCategorySortModel;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl110Biz.class);
    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @param cmd コマンド
     * @throws Exception 例外
     */
    public void init(Ptl110ParamModel paramMdl,
                     Connection con,
                     int sessionUserSid,
                     String cmd)
                     throws Exception {

        PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con);
        List<PtlPortletCategoryModel> catList =  new ArrayList<PtlPortletCategoryModel>();
        catList = dao.selectSortAll();
        if (!catList.isEmpty()) {
            //ラジオの文字列作成
            for (PtlPortletCategoryModel model : catList) {
                int sid = model.getPlcSid();
                String strSort = String.valueOf(sid);
                model.setStrPlcSort(strSort);
            }
        }
        paramMdl.setPtl110categoryList(catList);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getPtl110sortPltCategory())
            && catList.size() > 0) {
            log__.debug("ラジオチェックなし");

            PtlPortletCategoryModel model = catList.get(0);
            paramMdl.setPtl110sortPltCategory(String.valueOf(model.getPlcSid()));
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sortKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ptl110ParamModel paramMdl,
                            int sortKbn, int sessionUserSid)
        throws SQLException {

        SortResult<PtlPortletCategoryModel> result = null;
        final PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con);
        final PtlPortletCategorySortDao pcsDao = new PtlPortletCategorySortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getPtl110sortPltCategory();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<PtlPortletCategoryModel> sortBiz =
                SortChangeBiz.<PtlPortletCategoryModel> builder()
                .setFuncTargetList(() -> {
                    return dao.selectSortAll();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getPlcSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getPlcSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getPlcSid() == m2.getPlcSid()) {
                        return 0;
                    } else {
                        return (m1.getPlcSid() - m2.getPlcSid()) 
                                / Math.abs(m1.getPlcSid() - m2.getPlcSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    pcsDao.delete(m.getPlcSid());
                    PtlPortletCategorySortModel pcsMdl = new PtlPortletCategorySortModel();
                    pcsMdl.setPlcSid(m.getPlcSid());
                    pcsMdl.setPcsSort(newSort);
                    pcsDao.insert(pcsMdl);
                })
                .build();
        
        if (sortKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (sortKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSort = String.valueOf(result.getMdl().getPlcSid());
            paramMdl.setPtl110sortPltCategory(newSort);
        }
    }

}
