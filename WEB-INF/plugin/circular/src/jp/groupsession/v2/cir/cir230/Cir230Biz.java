package jp.groupsession.v2.cir.cir230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.model.CirLabelModel;
import jp.groupsession.v2.cir.model.LabelDataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;

/**
 * <br>[機  能] ラベル管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir230Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir230Biz.class);

    /** コネクション */
    private Connection con__ = null;
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public Cir230Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Cir230ParamModel paramMdl,
                             int userSid
                             ) throws SQLException {

        //アカウント名取得
        int accountSid = paramMdl.getCirAccountSid();
        CirAccountDao acDao = new CirAccountDao(con__);
        String accountName =  acDao.getCirAccountName(accountSid);
        paramMdl.setCir230accountName(accountName);

        //ラベル情報取得
        CirLabelDao cirDao = new CirLabelDao(con__);
        List<LabelDataModel> labelList = cirDao.selectLabelList(accountSid);
        paramMdl.setCir230LabelList(labelList);

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sortKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return true:変更成功
     */
    public boolean updateSort(Cir230ParamModel paramMdl, int sortKbn, int userSid)
        throws SQLException {
        
        SortResult<CirLabelModel> result = null;
        final CirLabelDao dao = new CirLabelDao(con__);
        boolean ret = false;

        //ラジオ選択値取得
        String selectSid = paramMdl.getCir230SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return false;
        }
        int motoSid = Integer.parseInt(selectSid);
        int cacSid = paramMdl.getCirAccountSid();

        SortChangeBiz<CirLabelModel> sortBiz =
                SortChangeBiz.<CirLabelModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select(cacSid);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getClaSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getClaOrder();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getClaSid() == m2.getClaSid()) {
                        return 0;
                    } else {
                        return (m1.getClaSid() - m2.getClaSid()) 
                                / Math.abs(m1.getClaSid() - m2.getClaSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updateSort(newSort, userSid, new UDate(), m.getClaSid());
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


    /**
     * <br>[機  能] ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteLabel(Cir230ParamModel paramMdl, int userSid) throws SQLException {

        boolean commitFlg = false;
        int claSid = paramMdl.getCir230EditLabelId();
        int cacSid = paramMdl.getCirAccountSid();
        UDate now = new UDate();
        try {

            // ソート番号を取得
            CirLabelDao dao = new CirLabelDao(con__);
            CirLabelModel mdl = dao.select(claSid, cacSid);
            if (mdl == null) {
                return;
            }
            int sort = mdl.getClaOrder();
            // ソート更新
            dao.updateSortAll(cacSid, userSid,  now, sort);
            //ラベルを削除する
            dao.delete(claSid);

            //回覧板送信ラベル削除
            CirInfLabelDao infDao = new CirInfLabelDao(con__);
            infDao.delete(claSid, paramMdl.getCirAccountSid());
            //回覧板受信ラベル削除
            CirViewLabelDao viewDao = new CirViewLabelDao(con__);
            viewDao.delete(claSid, paramMdl.getCirAccountSid());

            //コミット実行
            con__.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con__);
            }
        }
    }

}
