package jp.groupsession.v2.fil.fil310;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;

/**
 * <br>[機  能] ファイル管理 外貨マスタ画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil310Biz {


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
            Fil310ParamModel paramMdl,
            int userSid,
            RequestModel reqMdl) throws SQLException {

        List<Fil310DisplayModel> dspList = new ArrayList<Fil310DisplayModel>();
        FileMoneyMasterDao dao = new FileMoneyMasterDao(con);

        //外貨リストを取得
        dspList = dao.getGaikaList();

        //外貨並び順Model
        __getGaikaModel(paramMdl, dspList);

    }

    /**
     * <br>[機  能] 並び順用のFileMoneyMasterModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param dspList 外貨一覧
     */
    private void __getGaikaModel(
            Fil310ParamModel paramMdl,
            List<Fil310DisplayModel> dspList) {

        //外貨を画面表示用に加工する
        Fil310DisplayModel fil310Mdl = null;
        List<Fil310DisplayModel> gaikaList = new ArrayList<Fil310DisplayModel>();

        for (Fil310DisplayModel fil310SetMdl : dspList) {
            fil310Mdl = new Fil310DisplayModel();
            fil310Mdl.setFmmSid(fil310SetMdl.getFmmSid());
            fil310Mdl.setFmmName(fil310SetMdl.getFmmName());
            fil310Mdl.setFmmSort(fil310SetMdl.getFmmSort());
            fil310Mdl.setGaikaValue(String.valueOf(fil310SetMdl.getFmmSid()));
            gaikaList.add(fil310Mdl);
        }
        paramMdl.setGaikaList(gaikaList);
    }

    /**
     * <br>[機  能] 外貨の存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return 存在チェック判定
     * @throws SQLException SQL実行時例外
     */
    protected boolean _existGaika(Connection con, Fil310ParamModel paramMdl)
            throws SQLException {

        FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);
        int gaikaSid = paramMdl.getFilDelGaikaId();
        boolean result = fmmDao.existGaika(gaikaSid);

        return result;
    }

    /**
     * <br>[機  能] 外貨の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _deleteGaika(Connection con, Fil310ParamModel paramMdl)
            throws SQLException {

        //外貨を削除する
        FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);
        int gaikaSid = paramMdl.getFilDelGaikaId();
        fmmDao.delete(gaikaSid);
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
            Fil310ParamModel paramMdl, int userSid, int changeKbn) throws SQLException {

        boolean ret = false;
        SortResult<Fil310DisplayModel> result = null;
        final FileMoneyMasterDao dao = new FileMoneyMasterDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getFil310SortRadio();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return false;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<Fil310DisplayModel> sortBiz =
                SortChangeBiz.<Fil310DisplayModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getGaikaList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getFmmSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getFmmSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getFmmSid() == m2.getFmmSid()) {
                        return 0;
                    } else {
                        return (m1.getFmmSid() - m2.getFmmSid())
                                / Math.abs(m1.getFmmSid() - m2.getFmmSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updateSort(m.getFmmSid(), newSort);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }

        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getFmmSid());
            paramMdl.setFil310SortRadio(newSortRadio);
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] 外貨名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param targetSid ターゲットSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    protected String _getGaikaName(Connection con, int targetSid) throws SQLException {

        FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);
        FileMoneyMasterModel fmmMdl = fmmDao.select(targetSid);

        return fmmMdl.getFmmName();
    }
}