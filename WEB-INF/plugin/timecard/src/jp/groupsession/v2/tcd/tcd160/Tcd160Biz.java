package jp.groupsession.v2.tcd.tcd160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd160Biz {

    /** 画面ID */
    public static final String SCR_ID = "tcd120";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd160Biz.class);
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd160Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Tcd160ParamModel paramMdl, Connection con)
    throws SQLException {

        //時間帯情報を設定する
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> tcd160HolidayList = dao.getAllHolidayList();
        paramMdl.setTcd160HolidayList(tcd160HolidayList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getTcd160Order())
            && tcd160HolidayList.size() > 0) {
            TcdHolidayInfModel holMdl = tcd160HolidayList.get(0);
            paramMdl.setTcd160Order(String.valueOf(holMdl.getThiSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    protected boolean _updateSort(Connection con, Tcd160ParamModel paramMdl,
            int sortKbn, int userSid) throws SQLException {

        boolean ret = false;
        SortResult<TcdHolidayInfModel> result = null;
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        
        //ラジオ選択値取得
        String sortTtiSid = paramMdl.getTcd160Order();
        if (StringUtil.isNullZeroString(sortTtiSid)) {
            return false;
        }

        int motoSid = Integer.parseInt(sortTtiSid);

        SortChangeBiz<TcdHolidayInfModel> sortBiz =
                SortChangeBiz.<TcdHolidayInfModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getAllHolidayList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getThiSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getThiOrder();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getThiSid() == m2.getThiSid()) {
                        return 0;
                    } else {
                        return (m1.getThiSid() - m2.getThiSid()) 
                                / Math.abs(m1.getThiSid() - m2.getThiSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updateSort(newSort, userSid, new UDate(), m.getThiSid());
                })
                .build();
        
        if (sortKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (sortKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            ret = true;
        }
        return ret;
    }

}
