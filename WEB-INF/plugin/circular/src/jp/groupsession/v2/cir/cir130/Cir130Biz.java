package jp.groupsession.v2.cir.cir130;

import java.sql.Connection;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 個人設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir130Biz {
    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void setInitDataDsp(
            Cir130ParamModel paramMdl,
            Connection con,
            int userSid,
            RequestModel reqMdl) throws Exception {

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = new CirAccountModel();

        if (paramMdl.getCir130AccountSid() > 0) {
            cacMdl = cacDao.select(paramMdl.getCir130AccountSid());
        } else {
            //デフォルトのアカウントを所得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        }

        paramMdl.setCir130AccountSid(cacMdl.getCacSid());
        paramMdl.setCir130AccountName(cacMdl.getCacName());

        //管理者設定になっているか調べる。
        CirInitDao initDao = new CirInitDao(con);
        //管理者設定モデル 何もセットされなかった場合、デフォルト設定
        CirInitModel initMdl = new CirInitModel();

        //CirUserDao userDao = new CirUserDao(con);
        //CirUserModel userModel = userDao.getCirUserInfo(userSid);

        //管理者設定の有無 0:無し
        if (initDao.getCount() != 0) {
            initMdl = initDao.select();
        } else if (initDao.getCount() == 0) {
            initMdl = __getDefaultData();
        }


        //初期設定ユーザに管理者orデフォルト値を設定
        if (cacMdl.getCacInitKbn() == GSConstCircular.CIR_INIT_KBN_NOSET) {
            paramMdl.setCir130memoKbn(initMdl.getCinMemoKbn());
            paramMdl.setCir130memoPeriod(initMdl.getCinMemoDay());
            paramMdl.setCir130show(initMdl.getCinKouKbn());
        } else if (cacMdl.getCacInitKbn() == GSConstCircular.CIR_INIT_KBN_SET) {
            paramMdl.setCir130memoKbn(cacMdl.getCacMemoKbn());
            paramMdl.setCir130memoPeriod(cacMdl.getCacMemoDay());
            paramMdl.setCir130show(cacMdl.getCacKouKbn());
        }
    }


    /**
     * <br>[機  能]初期値が設定されてない場合、デフォルト値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return initMdl 初期値モデル(管理者)
     */
    private CirInitModel __getDefaultData() {
        CirInitModel initMdl = new CirInitModel();
        initMdl.setCinInitSetKen(GSConstCircular.CIR_INIEDIT_STYPE_USER);
        initMdl.setCinMemoKbn(GSConstCircular.CIR_INIT_MEMO_CHANGE_NO);
        initMdl.setCinMemoDay(GSConstCircular.CIR_INIT_MEMO_ONEWEEK);
        initMdl.setCinKouKbn(GSConstCircular.CIR_INIT_SAKI_PUBLIC);

        return initMdl;
    }

}
