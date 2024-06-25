package jp.groupsession.v2.cir.cir110kn;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 管理者設定 自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir110knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir110knBiz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cir110knBiz() {
    }

    /**
     * <br>[機  能] 自動削除設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return delMdl CirAdelModel
     * @throws SQLException SQL実行例外
     */
    public CirAdelModel updateAuteDelSetting(RequestModel reqMdl,
                                      Cir110knParamModel paramMdl,
                                      Connection con)
        throws SQLException {

        log__.debug("start");
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        //回覧板自動削除設定
        CirAdelModel delMdl = new CirAdelModel();

        delMdl.setCacSid(GSConstCircular.CIR_ADEL_USR_KBN_ADM);
        delMdl.setCadUsrKbn(GSConstCircular.CIR_ADEL_USR_KBN_ADM);
        delMdl.setCadDelKbn(
                NullDefault.getInt(
                        paramMdl.getCir110DelKbn(),
                        GSConstCircular.CIR_DEL_KBN_ADM_SETTING));

        int jdelKbn = 0;
        int jdelYear = 0;
        int jdelMonth = 0;
        int sdelKbn = 0;
        int sdelYear = 0;
        int sdelMonth = 0;
        int ddelKbn = 0;
        int ddelYear = 0;
        int ddelMonth = 0;

//        //自動削除 = 各ユーザが設定する
//        if (delMdl.getCadDelKbn() == GSConstCircular.CIR_DEL_KBN_ADM_SETTING) {

            jdelKbn =
                NullDefault.getInt(
                        paramMdl.getCir110JdelKbn(),
                        GSConstCircular.CIR_AUTO_DEL_NO);

            if (jdelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                jdelYear = NullDefault.getInt(paramMdl.getCir110JYear(), 0);
                jdelMonth = NullDefault.getInt(paramMdl.getCir110JMonth(), 0);
            }

            sdelKbn =
                NullDefault.getInt(
                        paramMdl.getCir110SdelKbn(),
                        GSConstCircular.CIR_AUTO_DEL_NO);

            if (sdelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                sdelYear = NullDefault.getInt(paramMdl.getCir110SYear(), 0);
                sdelMonth = NullDefault.getInt(paramMdl.getCir110SMonth(), 0);
            }

            ddelKbn =
                NullDefault.getInt(
                        paramMdl.getCir110DdelKbn(),
                        GSConstCircular.CIR_AUTO_DEL_NO);

            if (ddelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                ddelYear = NullDefault.getInt(paramMdl.getCir110DYear(), 0);
                ddelMonth = NullDefault.getInt(paramMdl.getCir110DMonth(), 0);
            }
//        }

        delMdl.setCadJdelKbn(jdelKbn);
        delMdl.setCadJdelYear(jdelYear);
        delMdl.setCadJdelMonth(jdelMonth);
        delMdl.setCadSdelKbn(sdelKbn);
        delMdl.setCadSdelYear(sdelYear);
        delMdl.setCadSdelMonth(sdelMonth);
        delMdl.setCadDdelKbn(ddelKbn);
        delMdl.setCadDdelYear(ddelYear);
        delMdl.setCadDdelMonth(ddelMonth);

        delMdl.setCadAuid(sessionUsrSid);
        delMdl.setCadAdate(nowDate);
        delMdl.setCadEuid(sessionUsrSid);
        delMdl.setCadEdate(nowDate);

        CirAdelDao delDao = new CirAdelDao(con);
        if (delDao.update(delMdl) == 0) {
            delDao.insert(delMdl);
        }
        return delMdl;
    }

    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cir110knParamModel
     */
    public void updateIgnoreYearMonth(Cir110knParamModel paramMdl) {
        if (!ValidateUtil.isNumber(paramMdl.getCir110JYear())) {
            paramMdl.setCir110JYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir110JMonth())) {
            paramMdl.setCir110JMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir110SYear())) {
            paramMdl.setCir110SYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir110SMonth())) {
            paramMdl.setCir110SMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir110DYear())) {
            paramMdl.setCir110DYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir110DMonth())) {
            paramMdl.setCir110DMonth("");
        }
    }
}