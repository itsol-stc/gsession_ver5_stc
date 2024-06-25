package jp.groupsession.v2.prj.prj022;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.GSValidateProject;
import jp.groupsession.v2.prj.prj020.Prj020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] TODOラベル設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj022Form extends Prj020Form {

    //入力項目
    /** カテゴリ名称 */
    private String prj022cateAdd__;
    /** カテゴリ */
    private String prj022cateSlc__;

    /** 選択したカテゴリ */
    private String prj022selectCategory__ = null;

    /** 編集カテゴリ名 */
    private String prj022editCategoryName__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate022(HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //カテゴリ名称
        String textCateName = gsMsg.getMessage(req, "project.src.14");
        //カテゴリ名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj022cateAdd__,
                textCateName,
                GSConstProject.MAX_LENGTH_CATE_NAME,
                true);

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate022edit(HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //カテゴリ名称
        String textCateName = gsMsg.getMessage(req, "project.src.14");

        //カテゴリ名称(編集)
        GSValidateProject.validateTextBoxInput(
                errors,
                prj022editCategoryName__,
                textCateName,
                GSConstProject.MAX_LENGTH_CATE_NAME,
                true);

        return errors;
    }

    /**
     * <p>prj022cateAdd を取得します。
     * @return prj022cateAdd
     */
    public String getPrj022cateAdd() {
        return prj022cateAdd__;
    }
    /**
     * <p>prj022cateAdd をセットします。
     * @param prj022cateAdd prj022cateAdd
     */
    public void setPrj022cateAdd(String prj022cateAdd) {
        prj022cateAdd__ = prj022cateAdd;
    }
    /**
     * <p>prj022cateSlc を取得します。
     * @return prj022cateSlc
     */
    public String getPrj022cateSlc() {
        return prj022cateSlc__;
    }
    /**
     * <p>prj022cateSlc をセットします。
     * @param prj022cateSlc prj022cateSlc
     */
    public void setPrj022cateSlc(String prj022cateSlc) {
        prj022cateSlc__ = prj022cateSlc;
    }

    /**
     * <p>prj022selectCategory を取得します。
     * @return prj022selectCategory
     */
    public String getPrj022selectCategory() {
        return prj022selectCategory__;
    }

    /**
     * <p>prj022selectCategory をセットします。
     * @param prj022selectCategory prj022selectCategory
     */
    public void setPrj022selectCategory(String prj022selectCategory) {
        prj022selectCategory__ = prj022selectCategory;
    }

    /**
     * <p>prj022editCategoryName を取得します。
     * @return prj022editCategoryName
     */
    public String getPrj022editCategoryName() {
        return prj022editCategoryName__;
    }

    /**
     * <p>prj022editCategoryName をセットします。
     * @param prj022editCategoryName prj022editCategoryName
     */
    public void setPrj022editCategoryName(String prj022editCategoryName) {
        prj022editCategoryName__ = prj022editCategoryName;
    }

}
