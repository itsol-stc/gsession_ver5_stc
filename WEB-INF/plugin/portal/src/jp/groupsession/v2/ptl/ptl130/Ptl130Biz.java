package jp.groupsession.v2.ptl.ptl130;

import java.sql.Connection;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル 権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl130Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl130Biz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Ptl130ParamModel paramMdl,
            Connection con) throws Exception {

        log__.debug("setInitData START");

        //初期表示判定
        if (paramMdl.getPtl130init() != 1) {
            //DB値取得
            PtlAdmConfDao dao = new PtlAdmConfDao(con);
            PtlAdmConfModel model = dao.select();

            //modelがnullなら初期値
            if (model != null) {
                paramMdl.setPtl130editKbn(model.getPacPtlEditkbn());
            } else {
                paramMdl.setPtl130editKbn(GSConstPortal.EDIT_KBN_PUBLIC);
            }

            paramMdl.setPtl130init(1);
        }
    }
}
