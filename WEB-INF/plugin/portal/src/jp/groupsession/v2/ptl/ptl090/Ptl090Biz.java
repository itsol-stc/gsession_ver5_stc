package jp.groupsession.v2.ptl.ptl090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.dao.PtlPortletSortDao;
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.model.PtlPortletSortModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル ポートレット管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl090Biz.class);
    /** Connection */
    private Connection con__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     */
    Ptl090Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void initDsp(Ptl090ParamModel paramMdl, RequestModel reqMdl) throws SQLException {
        PtlPortletDao dao = new PtlPortletDao(con__);

        //ポートレットを取得する

        ArrayList<PtlPortletModel> list
            = dao.selectInCategory(paramMdl.getPtl090svCategory(), reqMdl);
        if (list == null) {
            log__.debug("ポートレットなし");
            list = new ArrayList<PtlPortletModel>();
        }

        if (!list.isEmpty()) {
            //ラジオの文字列作成
            for (PtlPortletModel model : list) {
                int sid = model.getPltSid();
                String strSort = String.valueOf(sid);
                model.setStrPltSort(strSort);
            }
        }
        paramMdl.setPtl090portletlist(list);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getPtl090sortPortlet())
            && list.size() > 0) {

            PtlPortletModel model = list.get(0);
            paramMdl.setPtl090sortPortlet(String.valueOf(model.getPltSid()));
        }

        //カテゴリコンボを作成する
        createCategoryComb(paramMdl, reqMdl);
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sortKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Ptl090ParamModel paramMdl,
            RequestModel reqMdl, int sortKbn, int sessionUserSid)
    throws SQLException {

        SortResult<PtlPortletModel> result = null;
        final PtlPortletDao dao = new PtlPortletDao(con__);
        final PtlPortletSortDao ppsDao = new PtlPortletSortDao(con__);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getPtl090sortPortlet();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<PtlPortletModel> sortBiz =
                SortChangeBiz.<PtlPortletModel> searchedSortChangeBuilder()
                .setFuncTargetList(() -> {
                    return dao.selectInCategory(paramMdl.getPtl090svCategory(), reqMdl);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getPltSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getPltSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getPltSid() == m2.getPltSid()) {
                        return 0;
                    } else {
                        return (m1.getPltSid() - m2.getPltSid()) 
                                / Math.abs(m1.getPltSid() - m2.getPltSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    ppsDao.delete(m.getPltSid());
                    PtlPortletSortModel ppsMdl = new PtlPortletSortModel();
                    ppsMdl.setPltSid(m.getPltSid());
                    ppsMdl.setPlsSort(newSort);
                    ppsDao.insert(ppsMdl);
                })
                .setFuncTargetListAll(() -> {
                    return dao.selectInCategory(-1, reqMdl);
                })
                .build();
        
        if (sortKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (sortKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSort = String.valueOf(result.getMdl().getPltSid());
            paramMdl.setPtl090sortPortlet(newSort);
        }
    }


    /**
     * <br>[機  能] カテゴリのコンボ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void createCategoryComb(Ptl090ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con__);
        ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String all = gsMsg.getMessage("cmn.all");
        String noCategory = gsMsg.getMessage("cmn.category.no");

        List<PtlPortletCategoryModel> mdlList = dao.selectSortAll();
        //初期値 全て
        list.add(new LabelValueBean(all, "-1"));
        list.add(new LabelValueBean(noCategory, "0"));
        for (PtlPortletCategoryModel model : mdlList) {
            String strName = model.getPlcName();
            String strSid = Integer.toString(model.getPlcSid());
            list.add(new LabelValueBean(strName, strSid));
        }
        paramMdl.setPtl090CategoryList(list);

    }
}
