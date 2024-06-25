package jp.groupsession.v2.tcd.tcd120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.dao.TcdTimezoneDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;


/**
 * <br>[機  能] タイムカード 管理者設定 時間設定一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd120Biz {

    /** 画面ID */
    public static final String SCR_ID = "tcd120";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd120Biz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd120Biz(RequestModel reqMdl) {
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Tcd120ParamModel paramMdl, Connection con)
    throws SQLException {

        //時間帯情報を設定する
        TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
        List<TcdTimezoneInfoModel> tcd120TimezoneList = dao.select();
        paramMdl.setTcd120TimezoneList(tcd120TimezoneList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getTcd120SortRadio())
            && tcd120TimezoneList.size() > 0) {
            TcdTimezoneInfoModel tzMdl = tcd120TimezoneList.get(0);
            paramMdl.setTcd120SortRadio(String.valueOf(tzMdl.getTtiSid()));

        }
    }

    /**
     * <br>[機  能] 時間帯削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    protected void _deleteTimezone(Connection con, Tcd120ParamModel paramMdl, int usrSid) throws SQLException {

        boolean commitFlg = false;
        try {
            TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
            TcdTimezoneDao tzDao = new TcdTimezoneDao(con);
            TcdTimezonePriDao priDao = new TcdTimezonePriDao(con);
            infDao.delete(NullDefault.getInt(paramMdl.getTcd120SortRadio(), 0));
            tzDao.delete(NullDefault.getInt(paramMdl.getTcd120SortRadio(), 0));
            priDao.delete(NullDefault.getInt(paramMdl.getTcd120SortRadio(), 0));
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sortKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    protected boolean _updateSort(Connection con, Tcd120ParamModel paramMdl,
            int sortKbn, int userSid) throws SQLException {

        boolean ret = false;
        SortResult<TcdTimezoneInfoModel> result = null;
        TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
        
        //ラジオ選択値取得
        String sortTtiSid = paramMdl.getTcd120SortRadio();
        if (StringUtil.isNullZeroString(sortTtiSid)) {
            return false;
        }

        int motoSid = Integer.parseInt(sortTtiSid);

        SortChangeBiz<TcdTimezoneInfoModel> sortBiz =
                SortChangeBiz.<TcdTimezoneInfoModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getTtiSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getTtiSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getTtiSid() == m2.getTtiSid()) {
                        return 0;
                    } else {
                        return (m1.getTtiSid() - m2.getTtiSid()) 
                                / Math.abs(m1.getTtiSid() - m2.getTtiSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updateSort(newSort, userSid, new UDate(), m.getTtiSid());
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
