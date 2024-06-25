package jp.groupsession.v2.ptl.ptl150;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル 管理者設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl150Biz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ptl150Biz() {
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void initDsp(Connection con, Ptl150ParamModel paramMdl, int usrSid)
    throws SQLException {
        log__.debug("start");
        if (paramMdl.getPtl150init() == 0) {
            PtlAdmConfDao confDao = new PtlAdmConfDao(con);
            PtlAdmConfModel confModel = new PtlAdmConfModel();
            confModel = confDao.select();
            if (confModel == null) {
                confModel = new PtlAdmConfModel();
            }
            paramMdl.setPtl150init(1);
            paramMdl.setPtl150ptlInitKbn(Integer.toString(confModel.getPacDefKbn()));
            paramMdl.setPtl150ptlInitType(Integer.toString(confModel.getPacDefType()));
        }
        log__.debug("end");
    }
}
