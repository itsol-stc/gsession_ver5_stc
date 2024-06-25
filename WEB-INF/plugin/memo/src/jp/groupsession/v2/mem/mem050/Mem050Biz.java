package jp.groupsession.v2.mem.mem050;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.mem.dao.MemoBelongLabelDao;
import jp.groupsession.v2.mem.dao.MemoLabelDao;
import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <br>[機  能] メモ帳 ラベルの管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem050Biz.class);

    /**
     * <br>[機  能] ラベルの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @return 存在チェック判定
     * @throws SQLException SQL実行時例外
     */
    protected boolean _exist050LabelData(
            Connection con,
            Mem050ParamModel paramMdl,
            int usrSid) throws SQLException {

        MemoLabelDao mlDao = new MemoLabelDao(con);
        int mmlSid = paramMdl.getMemEditLabelId();
        boolean result = mlDao.existLabel(mmlSid, usrSid);

        return result;
    }

    /**
     * <br>[機  能] ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _deleteLabel(Connection con, Mem050ParamModel paramMdl) throws SQLException {

        //ラベルを削除する
        MemoLabelDao mlDao = new MemoLabelDao(con);
        MemoBelongLabelDao mblDao = new MemoBelongLabelDao(con);

        mlDao.delete(paramMdl.getMemEditLabelId());
        mblDao.delete(paramMdl.getMemEditLabelId());
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Connection con,
            Mem050ParamModel paramMdl,
            int userSid,
            RequestModel reqMdl) throws SQLException {

        List<Mem050DisplayModel> dspList = new ArrayList<Mem050DisplayModel>();
        MemoLabelDao dao = new MemoLabelDao(con);

        //ラベルリストを取得
        dspList = dao.getLabelNameList(userSid);

        //ラベル並び順Model
        __getLabelModel(paramMdl, dspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getMem050SortRadio())
                && dspList.size() > 0) {

            Mem050DisplayModel mlMdl = dspList.get(0);
            paramMdl.setMem050SortRadio(String.valueOf(mlMdl.getMmlSid()));
        }
    }

    /**
     * <br>[機  能] 並び順用のMemoLabelModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param dspList ラベル一覧
     */
    private void __getLabelModel(
            Mem050ParamModel paramMdl,
            List<Mem050DisplayModel> dspList) {

        //ラベルを画面表示用に加工する
        Mem050DisplayModel m050Mdl = null;
        List<Mem050DisplayModel> lbList = new ArrayList<Mem050DisplayModel>();

        for (Mem050DisplayModel m050SetMdl : dspList) {
            m050Mdl = new Mem050DisplayModel();
            m050Mdl.setMmlSid(m050SetMdl.getMmlSid());
            m050Mdl.setMmlName(m050SetMdl.getMmlName());
            m050Mdl.setMmlSort(m050SetMdl.getMmlSort());
            m050Mdl.setLabelValue(String.valueOf(m050SetMdl.getMmlSid()));
            lbList.add(m050Mdl);
        }
        paramMdl.setLbnList(lbList);
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return updateフラグ
     * @throws SQLException SQL実行時例外
     */
    protected boolean _updateSort(Connection con,
            Mem050ParamModel paramMdl, int userSid, int changeKbn) throws SQLException {

        boolean ret = false;
        SortResult<Mem050DisplayModel> result = null;
        final MemoLabelDao dao = new MemoLabelDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getMem050SortRadio();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return false;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<Mem050DisplayModel> sortBiz =
                SortChangeBiz.<Mem050DisplayModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getLabelNameList(userSid);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getMmlSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getMmlSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getMmlSid() == m2.getMmlSid()) {
                        return 0;
                    } else {
                        return (m1.getMmlSid() - m2.getMmlSid())
                                / Math.abs(m1.getMmlSid() - m2.getMmlSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updateSort(m.getMmlSid(), newSort);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }

        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getMmlSid());
            paramMdl.setMem050SortRadio(newSortRadio);
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] ラベル名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param targetSid ターゲットSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    protected String _getLabelName(Connection con, int targetSid) throws SQLException {

        MemoLabelDao mlDao = new MemoLabelDao(con);
        MemoLabelModel mlMdl = mlDao.select(targetSid);
        return mlMdl.getMmlName();
    }
}