package jp.groupsession.v2.cir.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cir.cir180.Cir180Dao;
import jp.groupsession.v2.cir.dao.CirUserDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.AccountDataModel;
import jp.groupsession.v2.cir.model.CirSearchModel;
import jp.groupsession.v2.cir.model.CirUserModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirMainBiz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param reqMdl リクエスモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(
            CirMainParamModel paramMdl, Connection con, int cacSid, RequestModel reqMdl)
    throws SQLException {

        log__.debug("start");

        //回覧板個人設定を取得する
        CirUserDao cuDao = new CirUserDao(con);
        CirUserModel cuMdl = cuDao.getCirUserInfo(reqMdl.getSmodel().getUsrsid());
        int limit = GSConst.LIST_COUNT_LIMIT;
        if (cuMdl != null) {
            limit = cuMdl.getCurMaxDsp();
        }

        List < CircularDspModel > cList = null;
        List < CircularDspModel > convList = new ArrayList<CircularDspModel>();
        List < CircularDspModel > convDspList = new ArrayList<CircularDspModel>();
        Cir180Dao cirDao = new Cir180Dao(con);
        List<AccountDataModel> accountList = cirDao.getAccountList(reqMdl.getSmodel().getUsrsid());

        for (AccountDataModel accountMdl : accountList) {
            //受信
            cList = __getJusinList(paramMdl, con, limit, accountMdl.getAccountSid());

            for (int i = 0; i < cList.size(); i++) {
                CircularDspModel clMdl = cList.get(i);
                String strAdate =
                    UDateUtil.getSlashYYMD(clMdl.getCifAdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(clMdl.getCifAdate());
                clMdl.setDspCifAdate(strAdate);
            }

            convList.addAll(cList);
        }

        if (convList != null && !convList.isEmpty()) {
            CircularDspModel dspMdl = null;
            int count = limit;
            if (convList.size() < count) {
                count = convList.size();
            }
            for (int i = 0; i < count; i++) {
                dspMdl = new CircularDspModel();
                dspMdl = convList.get(i);
                convDspList.add(dspMdl);
            }
        }

        //一覧をフォームへセット
        paramMdl.setCir010CircularList(convDspList);

        log__.debug("end");
    }

    /**
     * <br>[機  能] 回覧情報(受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param limit 1ページの表示件数
     * @param cacSid アカウントSID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getJusinList(
        CirMainParamModel paramMdl,
        Connection con,
        int limit,
        int cacSid) throws SQLException {

        log__.debug("start");

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);

        CircularDao cDao = new CircularDao(con);

        log__.debug("end");
        return cDao.getUnopenJusinList(bean, limit);
    }

}
