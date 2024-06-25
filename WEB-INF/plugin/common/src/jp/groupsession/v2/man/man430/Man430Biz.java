package jp.groupsession.v2.man.man430;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnExtPageDao;
import jp.groupsession.v2.cmn.dao.base.CmnPermittedDomainDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnExtPageModel;
import jp.groupsession.v2.cmn.model.base.CmnPermittedDomainModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430Biz {


    /**
     *
     * <br>[機  能]初期表示用のデータを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(RequestModel reqMdl,
            Man430ParamModel paramMdl,
            Connection con) throws SQLException {

        try {
            //データベースから値を取得
           CmnExtPageDao cepDao = new CmnExtPageDao(con);
           CmnExtPageModel cepModel = cepDao.select();

           if (cepModel != null
                   && paramMdl.getMan430InitFlg() == GSConstMain.DSP_FIRST) {
               //制限の有無
               paramMdl.setMan430ExtPageDspKbn(cepModel.getCepLimitDsp());
               //外部ドメイン
               __setExtDomainArea(paramMdl, con);
               //初期表示フラグを表示済みにする
               paramMdl.setMan430InitFlg(GSConstMain.DSP_ALREADY);
           }
        } catch (SQLException e) {
            throw e;
        }

    }

    /**
     *
     * <br>[機  能] データベースに登録されている外部ドメインをパラメータモデルへセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setExtDomainArea(
            Man430ParamModel paramMdl, Connection con) throws SQLException {

        if (paramMdl.getMan430ExtPageDspKbn()
                          == GSConstMain.DSP_EXT_PAGE_LIMITED) {
            CmnPermittedDomainDao cpdDao = new CmnPermittedDomainDao(con);
            List<CmnPermittedDomainModel> cpdModelList = cpdDao.select();

            String lmtText = "";
            for (CmnPermittedDomainModel model : cpdModelList) {
                lmtText = lmtText + model.getCpdExtDomain() + "\n";
            }

            paramMdl.setMan430ExtDomainArea(lmtText);
        }
    }

}
