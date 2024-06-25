package jp.groupsession.v2.cht.cht050kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.cht050.Cht050ParamModel;
import jp.groupsession.v2.cht.dao.ChtAdmConfDao;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] チャット自動データ削除設定確認のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht050knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht050knBiz.class);
    /** コネクション */
    private Connection con__ = null;


    /**コンストラクタ
     * @param con コネクション
     * */
    public Cht050knBiz(Connection con) {
        con__ = con;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエスト情報
     *
     * */
    public void setInitData(RequestModel reqMdl, Cht050knParamModel paramMdl) {
        log__.debug("init");
    }

    /**
     * <br>[機  能] 自動削除を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト
     * @param paramMdl 画面パラメータ
     * @param usrSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setDelteData(
            RequestModel reqMdl,
            Cht050ParamModel paramMdl,
            int usrSid)
    throws SQLException {
        ChtAdmConfModel delMdl = new ChtAdmConfModel();
        delMdl.setCacAtdelY(paramMdl.getCht050ReferenceYear());
        delMdl.setCacAtdelM(paramMdl.getCht050ReferenceMonth());
        delMdl.setCacAtdelFlg(paramMdl.getCht050DoAutoDel());
        delMdl.setCacEuid(reqMdl.getSmodel().getUsrsid());
        UDate now = new UDate();
        delMdl.setCacEdate(now);
        ChtAdmConfDao cacDao = new ChtAdmConfDao(con__);
        if (cacDao.updateAutoDel(delMdl) == 0) {
            ChtAdmConfModel initMdl = new ChtAdmConfModel();
            initMdl.initData(reqMdl.getSmodel().getUsrsid(), now);
            initMdl.setCacAtdelY(paramMdl.getCht050ReferenceYear());
            initMdl.setCacAtdelM(paramMdl.getCht050ReferenceMonth());
            initMdl.setCacAtdelFlg(paramMdl.getCht050DoAutoDel());
            cacDao.insert(initMdl);
        }
    }
}
