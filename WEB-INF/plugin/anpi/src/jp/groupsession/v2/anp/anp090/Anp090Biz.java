package jp.groupsession.v2.anp.anp090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpMtempSortDao;
import jp.groupsession.v2.anp.model.AnpMtempSortModel;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 管理者設定・メールテンプレート選択画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp090Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp090Model パラメータモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp090ParamModel anp090Model, Connection con)
                throws Exception {
        log__.debug("初期データ取得");


        Anp090Dao dao = new Anp090Dao(con);
        List<Anp090TemplateDspModel> tmpList = dao.getTemplateList();
        List<Anp090TemplateDspModel> escTmpDspList = new ArrayList<Anp090TemplateDspModel>();
        Anp090TemplateDspModel escTmpMdl = null;

        //取得したデータより表示順を設定する
        for (Anp090TemplateDspModel mdl : tmpList) {
            escTmpMdl = new Anp090TemplateDspModel();
            escTmpMdl.setTemplateSid(mdl.getTemplateSid());
            escTmpMdl.setTemplateName(mdl.getTemplateName());
            escTmpMdl.setTemplateSort(mdl.getTemplateSort());
            escTmpMdl.setTemplateValue(String.valueOf(mdl.getTemplateSid()));
            escTmpDspList.add(escTmpMdl);
        }
        anp090Model.setAnp090TempList(escTmpDspList);

        if (StringUtil.isNullZeroString(anp090Model.getAnp090SortTemplate())
                && tmpList.size() > 0) {

            Anp090TemplateDspModel appspMdl = escTmpDspList.get(0);
            anp090Model.setAnp090SortTemplate(String.valueOf(appspMdl.getTemplateSid()));
        }
    }


    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param anp090Model パラメータモデル
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Anp090ParamModel anp090Model, int userSid, int changeKbn)
            throws SQLException {

        final Anp090Dao dao = new Anp090Dao(con);
        final AnpMtempSortDao amsDao = new AnpMtempSortDao(con);

        //ラジオ選択値取得
        String selectSid = anp090Model.getAnp090SortTemplate();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<Anp090TemplateDspModel> sortBiz =
                SortChangeBiz.<Anp090TemplateDspModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getTemplateList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getTemplateSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getTemplateSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getTemplateSid() == m2.getTemplateSid()) {
                        return 0;
                    } else {
                        return (m1.getTemplateSid() - m2.getTemplateSid()) 
                                / Math.abs(m1.getTemplateSid() - m2.getTemplateSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    AnpMtempSortModel mdl = new AnpMtempSortModel();
                    mdl.setApmSid(m.getTemplateSid());
                    amsDao.updateSort(mdl, newSort);
                })
                .build();
        
        if (changeKbn == GSConstAnpi.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConstAnpi.SORT_DOWN) {
            sortBiz.down();
        }
    }
}