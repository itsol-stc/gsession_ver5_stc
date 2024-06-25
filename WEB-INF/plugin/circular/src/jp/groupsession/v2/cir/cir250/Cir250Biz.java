package jp.groupsession.v2.cir.cir250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.dao.AccountDataDao;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.model.AccountDataModel;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir250Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Cir250ParamModel paramMdl, Connection con)
    throws SQLException {

        int usrSid = reqMdl.getSmodel().getUsrsid();
        //アカウント一覧を取得
        AccountDataDao dao = new AccountDataDao(con);
        List<AccountDataModel> accountList = dao.getAccountList(usrSid);
        ArrayList<LabelValueBean> acountDataList = new ArrayList<LabelValueBean>();
        int cacSid = 0;
        for (int idx = 0; idx < accountList.size(); idx++) {
            if (idx == 0) {
                cacSid = accountList.get(idx).getAccountSid();
            }
            acountDataList.add(new LabelValueBean(accountList.get(idx).getAccountName(), 
                    String.valueOf(accountList.get(idx).getAccountSid())));
        }
        paramMdl.setCir250accountList(acountDataList);
        //ショートメール通知の選択値を取得
        CirAccountDao accountDao = new CirAccountDao(con);
        CirAccountModel accountMdl = accountDao.select(cacSid);
        paramMdl.setCir250smlNtf(accountMdl.getCacSmlNtf());
        paramMdl.setCir250smlMemo(accountMdl.getCacSmlMemo());
        paramMdl.setCir250smlEdt(accountMdl.getCacSmlEdit());
    }
    
    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setReloadData(RequestModel reqMdl, Cir250ParamModel paramMdl, Connection con)
    throws SQLException {

        int usrSid = reqMdl.getSmodel().getUsrsid();
        //アカウント一覧を取得
        AccountDataDao dao = new AccountDataDao(con);
        List<AccountDataModel> accountList = dao.getAccountList(usrSid);
        ArrayList<LabelValueBean> acountDataList = new ArrayList<LabelValueBean>();
        int cacSid = Integer.parseInt(paramMdl.getCir250selectAccount());
        boolean sidMatch = false;
        for (int idx = 0; idx < accountList.size(); idx++) {
            if (cacSid == accountList.get(idx).getAccountSid()) {
                sidMatch = true;
            }
            acountDataList.add(new LabelValueBean(accountList.get(idx).getAccountName(), 
                    String.valueOf(accountList.get(idx).getAccountSid())));
        }
        if (!sidMatch) {
            cacSid = accountList.get(0).getAccountSid();
            paramMdl.setCir250selectAccount(String.valueOf(cacSid));
        }
        paramMdl.setCir250accountList(acountDataList);
        //ショートメール通知の選択値を取得
        CirAccountDao accountDao = new CirAccountDao(con);
        CirAccountModel accountMdl = accountDao.select(cacSid);
        paramMdl.setCir250smlNtf(accountMdl.getCacSmlNtf());
        paramMdl.setCir250smlMemo(accountMdl.getCacSmlMemo());
        paramMdl.setCir250smlEdt(accountMdl.getCacSmlEdit());
    }

    /**
     * <br>[機  能] ショートメール通知の更新
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateSmlConf(RequestModel reqMdl, Cir250ParamModel paramMdl, Connection con)
            throws SQLException {
        
        int wacSid = Integer.parseInt(paramMdl.getCir250selectAccount());
        CirAccountModel accountModel = new CirAccountModel();
        accountModel.setCacSid(wacSid);
        CirAccountDao accountDao = new CirAccountDao(con);
        
        accountModel = accountDao.select(wacSid);
        accountModel.setCacSid(wacSid);
        accountModel.setCacSmlEdit(paramMdl.getCir250smlEdt());
        accountModel.setCacSmlMemo(paramMdl.getCir250smlMemo());
        accountModel.setCacSmlNtf(paramMdl.getCir250smlNtf());
        accountDao.updateAccountSml(accountModel);
        
    }


}
