package jp.groupsession.v2.cir.cir140;

import java.sql.Connection;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.model.CirInitModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 管理者設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir140Biz.class);


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cir140Biz() {
    }


    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(
            Cir140ParamModel paramMdl,
            Connection con)  throws Exception {

        log__.debug("start");

        //管理者設定になっているか調べる。
        CirInitDao initDao = new CirInitDao(con);
        //管理者設定モデル 何もセットされなかった場合、デフォルト設定
        CirInitModel initMdl = new CirInitModel();

        //管理者設定の有無 0:無し
        if (initDao.getCount() != 0) {
            initMdl = initDao.select();
        } else if (initDao.getCount() == 0) {
            //デフォルト値を設定する。
            initMdl = getDefaultData();
        }

        paramMdl.setCir140KenKbn(initMdl.getCinInitSetKen());
        paramMdl.setCir140memoKbn(initMdl.getCinMemoKbn());
        paramMdl.setCir140memoPeriod(initMdl.getCinMemoDay());
        paramMdl.setCir140show(initMdl.getCinKouKbn());
    }

    /**
     * <br>[機  能]初期値が設定されてない場合、デフォルト値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return initMdl 初期値モデル(管理者)
     */
    private CirInitModel getDefaultData() {
        CirInitModel initMdl = new CirInitModel();
        initMdl.setCinInitSetKen(GSConstCircular.CIR_INIEDIT_STYPE_USER);
        initMdl.setCinMemoKbn(GSConstCircular.CIR_INIT_MEMO_CHANGE_NO);
        initMdl.setCinMemoDay(GSConstCircular.CIR_INIT_MEMO_ONEWEEK);
        initMdl.setCinKouKbn(GSConstCircular.CIR_INIT_SAKI_PUBLIC);

        return initMdl;
    }

}
