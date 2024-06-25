package jp.groupsession.v2.prj.prj024;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.prj.prj022.Prj022Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] プロジェクト管理 TODOカテゴリ設定削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj024Form extends Prj022Form {

    //入力項目
    /** カテゴリ */
    private String prj024cateSlc__;

    //表示項目
    /** カテゴリ名称 */
    private String cateName__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate024(HttpServletRequest req) throws SQLException {
        GsMessage gsMsg = new GsMessage();
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        //カテゴリ
        String textCategory = gsMsg.getMessage(req, "cmn.label");
        if (NullDefault.getInt(prj024cateSlc__, -2) < -1) {
            msg = new ActionMessage("error.select.required.text", textCategory);
            StrutsUtil.addMessage(errors, msg, "prj020status.error.select.required.text");
        }

        return errors;
    }

    /**
     * <p>prj024cateSlc を取得します。
     * @return prj024cateSlc
     */
    public String getPrj024cateSlc() {
        return prj024cateSlc__;
    }

    /**
     * <p>prj024cateSlc をセットします。
     * @param prj024cateSlc prj024cateSlc
     */
    public void setPrj024cateSlc(String prj024cateSlc) {
        prj024cateSlc__ = prj024cateSlc;
    }

    /**
     * <p>cateName を取得します。
     * @return cateName
     */
    public String getCateName() {
        return cateName__;
    }

    /**
     * <p>cateName をセットします。
     * @param cateName cateName
     */
    public void setCateName(String cateName) {
        cateName__ = cateName;
    }

}
