package jp.groupsession.v2.cht.cht060kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.cht060.Cht060ParamModel;
import jp.groupsession.v2.cht.model.ChatDeleteModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] チャット手動データ削除設定確認のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht060knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht060knBiz.class);
    /** コネクション */
    private Connection con__ = null;


    /**コンストラクタ
     * @param con コネクション
     * */
    public Cht060knBiz(Connection con) {
        con__ = con;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエスト情報
     *
     * */
    public void setInitData(RequestModel reqMdl, Cht060knParamModel paramMdl) {
        log__.debug("init");
    }

    /**
     * <br>[機  能] 手動削除を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト
     * @param paramMdl 画面パラメータ
     * @param usrSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setDelteData(
            RequestModel reqMdl,
            Cht060ParamModel paramMdl,
            int usrSid)
    throws SQLException {
        ChatDeleteModel delMdl = new ChatDeleteModel();
        delMdl.setDelYear(paramMdl.getCht060ReferenceYear());
        delMdl.setDelMonth(paramMdl.getCht060ReferenceMonth());
        ChtBiz chtBiz = new ChtBiz(con__);
        chtBiz.deleteChtData(delMdl, usrSid);
    }
}
