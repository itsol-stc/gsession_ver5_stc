package jp.groupsession.v2.ptl.ptl150kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル 管理者設定 初期値設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl150knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl150knBiz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ptl150knBiz() {
    }

    /**
     * <br>[機  能] 更新処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateData(Ptl150knParamModel paramMdl, Connection con) throws SQLException {

        log__.debug("edit");
        PtlAdmConfDao confDao = new PtlAdmConfDao(con);
        PtlAdmConfModel confModel = new PtlAdmConfModel();

        confModel = createModel(paramMdl, con);
        int count = confDao.updateInitConf(confModel);
        return count;
    }

    /**
     * <br>[機  能] 登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void insertData(Ptl150knParamModel paramMdl, Connection con) throws SQLException {

        log__.debug("insert");
        PtlAdmConfDao confDao = new PtlAdmConfDao(con);
        PtlAdmConfModel confModel = new PtlAdmConfModel();

        confModel = createModel(paramMdl, con);
        confModel.setPacPtlEditkbn(GSConstPortal.EDIT_KBN_PUBLIC);
        confDao.insert(confModel);
    }

    /**
     * <br>[機  能] 登録・更新モデルを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param paramMdl パラメータ情報
     * @return PtlAdmConfModel model
     */
    public PtlAdmConfModel createModel(Ptl150knParamModel paramMdl, Connection con) {

        log__.debug("createModel");
        PtlAdmConfModel model = new PtlAdmConfModel();
        model.setPacDefKbn(Integer.parseInt(paramMdl.getPtl150ptlInitKbn()));
        model.setPacDefType(Integer.parseInt(paramMdl.getPtl150ptlInitType()));

        return model;
    }
}
