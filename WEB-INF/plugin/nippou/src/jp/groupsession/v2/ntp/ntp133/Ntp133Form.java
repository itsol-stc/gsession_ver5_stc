package jp.groupsession.v2.ntp.ntp133;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.ntp.ntp130.Ntp130Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import org.apache.struts.action.ActionErrors;


/**
 * <br>[機  能] 日報 商品カテゴリ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp133Form extends Ntp130Form {


    //入力項目
    /** カテゴリ名 */
    private String ntp133CategoryName__;
    /** 備考 */
    private String ntp133bikou__;
    /** 状態フラグ(カテゴリ内のラベル有無) */
    private int catKbn__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateNtp133(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //カテゴリ名
        AdrValidateUtil.validateTextField(errors,
                                          ntp133CategoryName__,
                                          "ntp133CategoryName",
                                          gsMsg.getMessage(req, "cmn.category.name"),
                                          20,
                                          true);
        //備考
        AdrValidateUtil.validateTextAreaField(errors,
                                              ntp133bikou__,
                                             "ntp133bikou",
                                             gsMsg.getMessage(req, "cmn.memo"),
                                              300,
                                              false);
        return errors;
    }


    /**
     * <p>ntp133CategoryName を取得します。
     * @return ntp133CategoryName
     */
    public String getNtp133CategoryName() {
        return ntp133CategoryName__;
    }

    /**
     * <p>ntp133CategoryName をセットします。
     * @param ntp133CategoryName ntp133CategoryName
     */
    public void setNtp133CategoryName(String ntp133CategoryName) {
        ntp133CategoryName__ = ntp133CategoryName;
    }

    /**
     * <p>ntp133bikou を取得します。
     * @return ntp133bikou
     */
    public String getNtp133bikou() {
        return ntp133bikou__;
    }

    /**
     * <p>ntp133bikou をセットします。
     * @param ntp133bikou ntp133bikou
     */
    public void setNtp133bikou(String ntp133bikou) {
        ntp133bikou__ = ntp133bikou;
    }

    /**
     * <p>catKbn を取得します。
     * @return catKbn
     */
    public int getCatKbn() {
        return catKbn__;
    }

    /**
     * <p>catKbn をセットします。
     * @param catKbn catKbn
     */
    public void setCatKbn(int catKbn) {
        catKbn__ = catKbn;
    }

    /**
     * <br>[機  能] 削除チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL例外発生
     */
    public ActionErrors deleteCheck(Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
//        ActionMessage msg = null;
//        String fieldfix;

//        AdrBelongLabelDao dao = new AdrBelongLabelDao(con);
//        //アドレス帳に付加されているラベルSID一覧取得
//        ArrayList<AdrBelongLabelModel> modelList = dao.getCountLabBelongCat(getNtp133EditSid());
//
//        //アドレス帳に付加ラベルなし
//        if (modelList == null) {
//            return errors;
//        }
//
//        AdrLabelDao labDao = new AdrLabelDao(con);
//        for (AdrBelongLabelModel model : modelList) {
//            AdrLabelModel labModel = labDao.select(model.getAlbSid());
//            fieldfix = "labBelongCat" + ".";
//            String msgKey = "error.user.duplication.label.incategory";
//            msg = new ActionMessage(msgKey, getNtp133CategoryName(),
//                                   labModel.getAlbName(), model.getCount());
//            errors.add(fieldfix, msg);
//        }
        return errors;
    }


}
