package jp.groupsession.v2.enq.enq960kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.dao.EnqAutodeleteDao;
import jp.groupsession.v2.enq.model.EnqAutodeleteModel;

/**
 * <br>[機  能] 管理者設定 アンケート自動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq960knBiz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Enq960knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 自動削除設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @return delMdl EnqAutodeleteModel
     * @throws SQLException SQL実行例外
     */
    public EnqAutodeleteModel updateAuteDelSetting(RequestModel reqMdl,
                                      Enq960knParamModel paramMdl)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        //ショートメール自動削除設定
        EnqAutodeleteModel delMdl = new EnqAutodeleteModel();

        int sendDelKbn = 0;
        int sendDelYear = 0;
        int sendDelMonth = 0;
        int draftDelKbn = 0;
        int draftDelYear = 0;
        int draftDelMonth = 0;

        //送信フォルダ 削除区分
        sendDelKbn = NullDefault.getInt(paramMdl.getEnq960SendDelKbn(), 0);
        //送信フォルダ 年
        sendDelYear = NullDefault.getInt(paramMdl.getEnq960SelectSendYear(), 0);
        //送信フォルダ 月
        sendDelMonth = NullDefault.getInt(paramMdl.getEnq960SelectSendMonth(), 0);
        //草稿フォルダ 削除区分
        draftDelKbn = NullDefault.getInt(paramMdl.getEnq960DraftDelKbn(), 0);
        //草稿フォルダ 年
        draftDelYear = NullDefault.getInt(paramMdl.getEnq960SelectDraftYear(), 0);
        //草稿フォルダ 月
        draftDelMonth = NullDefault.getInt(paramMdl.getEnq960SelectDraftMonth(), 0);

        delMdl.setEadSendKbn(sendDelKbn);
        delMdl.setEadSendYear(sendDelYear);
        delMdl.setEadSendMonth(sendDelMonth);
        delMdl.setEadDraftKbn(draftDelKbn);
        delMdl.setEadDraftYear(draftDelYear);
        delMdl.setEadDraftMonth(draftDelMonth);
        delMdl.setEadAdate(nowDate);
        delMdl.setEadAuid(sessionUsrSid);
        delMdl.setEadEdate(nowDate);
        delMdl.setEadEuid(sessionUsrSid);

        //DBを更新する
        EnqAutodeleteDao delDao = new EnqAutodeleteDao(con__);
        if (delDao.update(delMdl) == 0) {
            delDao.insert(delMdl);
        }

        return delMdl;
    }

    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel Enq960knParamModel
     */
    public void changeEgnoreYearMonth(Enq960knParamModel paramModel) {
        if (!ValidateUtil.isNumber(
                NullDefault.getString(paramModel.getEnq960SelectSendYear(), "0"))) {
            paramModel.setEnq960SelectSendYear("");
        }
        if (!ValidateUtil.isNumber(
                NullDefault.getString(paramModel.getEnq960SelectSendMonth(), "0"))) {
            paramModel.setEnq960SelectSendMonth("");
        }
        if (!ValidateUtil.isNumber(
                NullDefault.getString(paramModel.getEnq960SelectDraftYear(), "0"))) {
            paramModel.setEnq960SelectDraftYear("");
        }
        if (!ValidateUtil.isNumber(
                NullDefault.getString((paramModel.getEnq960SelectDraftMonth()), "0"))) {
            paramModel.setEnq960SelectDraftMonth("");
        }
    }
}
