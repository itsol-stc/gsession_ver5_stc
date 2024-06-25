package jp.groupsession.v2.cir.cir080kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAdelDao;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 個人設定 自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir080knBiz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Cir080knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 自動削除設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト譲歩
     * @param paramMdl パラメータ情報
     * @return delMdl CirAdelModel
     * @throws SQLException SQL実行例外
     */
    public CirAdelModel updateAuteDelSetting(RequestModel reqMdl,
                                          Cir080knParamModel paramMdl)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        //回覧板自動削除設定
        CirAdelModel delMdl = new CirAdelModel();

        delMdl.setCacSid(paramMdl.getCirAccountSid());
        delMdl.setCadUsrKbn(GSConstCircular.CIR_ADEL_USR_KBN_USER);
        delMdl.setCadDelKbn(GSConstCircular.CIR_DEL_KBN_USER_SETTING);

        int jdelkbn =
            NullDefault.getInt(
                    paramMdl.getCir080JdelKbn(),
                    GSConstCircular.CIR_AUTO_DEL_NO);

        delMdl.setCadJdelKbn(jdelkbn);

        int jdelYear = 0;
        int jdelMonth = 0;

        if (jdelkbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            jdelYear = NullDefault.getInt(paramMdl.getCir080JYear(), 0);
            jdelMonth = NullDefault.getInt(paramMdl.getCir080JMonth(), 0);
        }

        delMdl.setCadJdelYear(jdelYear);
        delMdl.setCadJdelMonth(jdelMonth);

        int sdelkbn =
            NullDefault.getInt(
                    paramMdl.getCir080SdelKbn(),
                    GSConstCircular.CIR_AUTO_DEL_NO);

        delMdl.setCadSdelKbn(sdelkbn);

        int sdelYear = 0;
        int sdelMonth = 0;

        if (sdelkbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            sdelYear = NullDefault.getInt(paramMdl.getCir080SYear(), 0);
            sdelMonth = NullDefault.getInt(paramMdl.getCir080SMonth(), 0);
        }

        delMdl.setCadSdelYear(sdelYear);
        delMdl.setCadSdelMonth(sdelMonth);

        int ddelkbn =
            NullDefault.getInt(
                    paramMdl.getCir080DdelKbn(),
                    GSConstCircular.CIR_AUTO_DEL_NO);

        delMdl.setCadDdelKbn(ddelkbn);

        int ddelYear = 0;
        int ddelMonth = 0;

        if (ddelkbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            ddelYear = NullDefault.getInt(paramMdl.getCir080DYear(), 0);
            ddelMonth = NullDefault.getInt(paramMdl.getCir080DMonth(), 0);
        }

        delMdl.setCadDdelYear(ddelYear);
        delMdl.setCadDdelMonth(ddelMonth);

        delMdl.setCadAuid(sessionUsrSid);
        delMdl.setCadAdate(nowDate);
        delMdl.setCadEuid(sessionUsrSid);
        delMdl.setCadEdate(nowDate);

        CirAdelDao delDao = new CirAdelDao(con__);
        if (delDao.update(delMdl) == 0) {
            delDao.insert(delMdl);
        }
        return delMdl;
    }

    /**
     * <br>[機  能] 選択した年月に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cir080knParamModel
     */
    public void updateIgnoreYearMonth(Cir080knParamModel paramMdl) {
        if (!ValidateUtil.isNumber(paramMdl.getCir080JYear())) {
            paramMdl.setCir080JYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir080JMonth())) {
            paramMdl.setCir080JMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir080SYear())) {
            paramMdl.setCir080SYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir080SMonth())) {
            paramMdl.setCir080SMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir080DYear())) {
            paramMdl.setCir080DYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir080DMonth())) {
            paramMdl.setCir080DMonth("");
        }
    }
}