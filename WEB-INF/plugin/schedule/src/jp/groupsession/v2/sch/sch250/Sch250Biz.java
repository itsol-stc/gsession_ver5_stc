package jp.groupsession.v2.sch.sch250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.sch.dao.SchMyviewlistDao;
import jp.groupsession.v2.sch.model.SchMyviewlistModel;


/**
 * <br>[機  能] 表示リスト設定画面のビジネスロジックを提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch250Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
            Sch250ParamModel paramMdl, int usrSid) throws SQLException {
        
        SchMyviewlistDao smlDao = new SchMyviewlistDao(con);
        List<SchMyviewlistModel> viewList = smlDao.select(usrSid);
        for (SchMyviewlistModel bean : viewList) {
            String biko = bean.getSmyBiko();
            biko = StringUtilHtml.transToHTmlPlusAmparsant(biko);
            bean.setSmyBiko(biko);
        }
        paramMdl.setSch250ViewList(viewList);
    }
    
    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Sch250ParamModel
     * @param usrSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(
            Connection con,
            Sch250ParamModel paramMdl,
            int usrSid,
            int changeKbn) throws SQLException {

        final SchMyviewlistDao dao = new SchMyviewlistDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getSch250SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }

        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<SchMyviewlistModel> sortBiz =
                SortChangeBiz.<SchMyviewlistModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select(usrSid);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getSmySid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getSmySort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getSmySid() == m2.getSmySid()) {
                        return 0;
                    } else {
                        return (m1.getSmySid() - m2.getSmySid()) 
                                / Math.abs(m1.getSmySid() - m2.getSmySid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setSmySort(newSort);
                    dao.delete(m.getSmySid());
                    dao.insert(m);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
