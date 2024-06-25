package jp.groupsession.v2.cir.cir140kn;

import java.sql.Connection;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.model.CirInitModel;

/**
 * <br>[機  能] 回覧板 管理者設定 初期値設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir140knBiz {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cir140knBiz() {
    }

    /**
     * <br>[機  能] 設定値をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitAdmDataDB(
            Connection con,
            Cir140knParamModel paramMdl,
            int userSid
            ) throws Exception {

        //管理者設定が行われているか調べる。
        CirInitDao initDao = new CirInitDao(con);
        //管理者設定モデル 何もセットされなかった場合、デフォルト設定
        CirInitModel initMdl = new CirInitModel();

        UDate nowDate = new UDate();

        initMdl.setCinInitSetKen(paramMdl.getCir140KenKbn());
        initMdl.setCinMemoKbn(paramMdl.getCir140memoKbn());
        initMdl.setCinMemoDay(paramMdl.getCir140memoPeriod());
        initMdl.setCinKouKbn(paramMdl.getCir140show());
        initMdl.setCinEuid(userSid);
        initMdl.setCinEdate(nowDate);

        //テーブルにデータが存在するかチェックする
        int count = initDao.getCount();

        //管理者設定の有無 0:無し
        if (count != 0) {
            initDao.updateAdmInitSet(initMdl);
        } else if (count == 0) {
            //管理者設定が未設定だった場合
            initMdl.setCinAuid(userSid);
            initMdl.setCinAdate(nowDate);
            initMdl.setCinAutoDelKbn(0);
            initMdl.setCinAcntMake(0);

            initDao.insertAdmInitSet(initMdl);
        }
    }

}
