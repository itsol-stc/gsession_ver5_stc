package jp.groupsession.v2.cir.cir180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirAccountSortDao;
import jp.groupsession.v2.cir.model.AccountDataModel;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirAccountSortModel;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 アカウントの管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir180Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir180Biz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                             Cir180ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {

        //アカウント情報を取得する
        Cir180Dao dao = new Cir180Dao(con);
        List<AccountDataModel> accountList = dao.getAccountList(userSid);

        //アカウント並び順再登録
        CirAccountSortDao sortDao = new CirAccountSortDao(con);
        sortDao.deleteAllSort(userSid);
        CirAccountSortModel mdl = null;
        for (int i = 0; i < accountList.size(); i++) {
            AccountDataModel aMdl = accountList.get(i);
            mdl = new CirAccountSortModel();
            mdl.setCacSid(aMdl.getAccountSid());
            mdl.setUsrSid(userSid);
            mdl.setCasSort(i);
            sortDao.insert(mdl);
        }

        List<Cir180AccountDataDspModel> acDspList = new ArrayList<Cir180AccountDataDspModel>();
        Cir180AccountDataDspModel acDatadspMdl = null;
        accountList = dao.getAccountList(userSid);
        for (AccountDataModel acDataMdl : accountList) {
            acDatadspMdl = new Cir180AccountDataDspModel();

            acDatadspMdl.setAccountSid(acDataMdl.getAccountSid());
            acDatadspMdl.setAccountName(acDataMdl.getAccountName());
            acDatadspMdl.setAccountType(acDataMdl.getAccountType());
            acDatadspMdl.setAccountBiko(acDataMdl.getAccountBiko());
            acDatadspMdl.setAccountSort((int) acDataMdl.getAccountSort());
            acDatadspMdl.setAcValue(String.valueOf(acDatadspMdl.getAccountSid()));
            acDatadspMdl.setUsrUkoFlg(acDataMdl.getUsrUkoFlg());
            acDspList.add(acDatadspMdl);
        }

        paramMdl.setAccountList(acDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getCir180sortAccount())
            && accountList.size() > 0) {

            Cir180AccountDataDspModel addspMdl = acDspList.get(0);
            paramMdl.setCir180sortAccount(String.valueOf(addspMdl.getAccountSid()));
        }

        //非管理者でもアカウントを登録できるかどうか
        CirCommonBiz cmnBiz = new CirCommonBiz(con);
        CirInitModel camMdl = cmnBiz.getCirInitConf(userSid, con);

        if (camMdl.getCinAcntMake() == GSConstCircular.KANRI_USER_ONLY) {
            paramMdl.setCir180MakeAcntHnt(GSConstCircular.ACCOUNT_ADD_NG);
        }

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
    public boolean updateSort(Connection con, Cir180ParamModel paramMdl, int userSid, int changeKbn)
        throws SQLException {

        SortResult<AccountDataModel> result = null;
        final Cir180Dao dao = new Cir180Dao(con);
        final CirAccountSortDao casDao = new CirAccountSortDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getCir180sortAccount();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return false;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<AccountDataModel> sortBiz =
                SortChangeBiz.<AccountDataModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getAccountList(userSid);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getAccountSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return (int) m.getAccountSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getAccountSid() == m2.getAccountSid()) {
                        return 0;
                    } else {
                        return (m1.getAccountSid() - m2.getAccountSid()) 
                                / Math.abs(m1.getAccountSid() - m2.getAccountSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    CirAccountSortModel casMdl = new CirAccountSortModel();
                    casMdl.setCasSort(newSort);
                    casMdl.setUsrSid(userSid);
                    casMdl.setCacSid(m.getAccountSid());
                    casDao.delete(m.getAccountSid(), userSid);
                    casDao.insert(casMdl);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] ログ用メッセージ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param selectedSid 選択SID
     * @param con コネクション
     * @return ログ用メッセージ
     * @throws SQLException SQLエラー
     */
    public String getTargetName(String selectedSid, Connection con) throws SQLException {

        //選択された項目のフィルターSID + ソート順
        int motoSid = Integer.parseInt(selectedSid);
        CirAccountDao dao = new CirAccountDao(con);
        CirAccountModel mdl = dao.select(motoSid);

        return mdl.getCacName();
    }
}
