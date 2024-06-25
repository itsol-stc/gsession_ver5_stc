package jp.groupsession.v2.cir.cir070;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAdelDao;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cir.model.CirInitModel;

/**
 * <br>[機  能] 回覧板 個人設定メニュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir070Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cir070ParamModel paramMdl, Connection con)
        throws SQLException {

        //自動削除設定
        CirAdelDao adelDao = new CirAdelDao(con);

        //ユーザSID = 0 は管理者設定データ
        CirAdelModel adelMdl = adelDao.select(0);

        // デフォルト 自動削除設定値を管理者にセット
        if (adelMdl == null) {
            paramMdl.setCir070CirDelAdminConf(GSConstCircular.CIR_DEL_KBN_ADM_SETTING);
        } else {
            paramMdl.setCir070CirDelAdminConf(adelMdl.getCadDelKbn());
        }

        //初期値表示区分
        CirInitDao initDao = new CirInitDao(con);
        CirInitModel initMdl = new CirInitModel();

        //管理者設定の有無 0:無し
        if (initDao.getCount() != 0) {
            initMdl = initDao.select();
        } else if (initDao.getCount() == 0) {
            //デフォルト値
            initMdl.setCinInitSetKen(GSConstCircular.CIR_INIEDIT_STYPE_USER);
        }


        if (initMdl.getCinInitSetKen() == GSConstCircular.CIR_INIEDIT_STYPE_ADM) {
            paramMdl.setCir070InitSettingDsp(GSConstCircular.CIR_INIT_SET_NODIS);
        } else {
            paramMdl.setCir070InitSettingDsp(GSConstCircular.CIR_INIT_SET_DIS);
        }

        paramMdl.setCirAccountMode(GSConstCircular.ACCOUNTMODE_PSNLSETTING);

    }
}