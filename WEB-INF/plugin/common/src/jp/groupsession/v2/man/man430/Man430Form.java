package jp.groupsession.v2.man.man430;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430Form extends AbstractGsForm {

    /** 外部ページ表示制限の有無 */
    private int man430ExtPageDspKbn__ = GSConstMain.DSP_EXT_PAGE_NO_LIMIT;
    /** 表示を許可する外部ドメイン テキストエリア */
    private String man430ExtDomainArea__ = null;
    /** 初期表示フラグ  */
    private int man430InitFlg__ = GSConstMain.DSP_FIRST;



    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return アクションエラー
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        //外部ドメイン
        String extDomain = gsMsg.getMessage("main.man430.11");
        //外部ページ表示制限
        String outerPageDisp = gsMsg.getMessage("main.man430.2");

        //外部ページ表示制限の選択値をチェック
        if (man430ExtPageDspKbn__ != GSConstMain.DSP_EXT_PAGE_NO_LIMIT
                && man430ExtPageDspKbn__ != GSConstMain.DSP_EXT_PAGE_LIMITED) {
            msg = new ActionMessage("error.select.required.text", outerPageDisp);
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
        }

        //外部ドメインのチェック
        if (man430ExtPageDspKbn__ == GSConstMain.DSP_EXT_PAGE_LIMITED) {
            //未入力の場合はそのまま登録
            if (StringUtil.isNullZeroStringSpace(man430ExtDomainArea__)) {
                return errors;
            }

            //外部ドメインを改行ごとに区切る
            String[] man430ExtDomain = null;
            man430ExtDomain = man430ExtDomainArea__.split("\n");

            //1行ごとにチェック
            for (int i = 0; i < man430ExtDomain.length; i++) {
                man430ExtDomain[i] = StringUtil.toDeleteReturnCode(man430ExtDomain[i]);
                //未入力行
                if (man430ExtDomain[i] == null || ValidateUtil.isSpace(man430ExtDomain[i])) {
                    msg = new ActionMessage("error.input.spase.cl.only", extDomain);
                    StrutsUtil.addMessage(errors, msg, "man430ExtDomain.error.input.spase.cl.only");
                }

                //タブ文字判定
                if (ValidateUtil.isTab(man430ExtDomain[i])) {
                    msg = new ActionMessage("error.input.tab.text", extDomain);
                    StrutsUtil.addMessage(errors, msg, "error.input.tab.text");
                }

                //文字数
                if (StringUtilHtml.deleteHtmlTag(man430ExtDomain[i]).length()
                        > GSConstMain.MAX_LENGTH_DOMAIN) {
                    msg = new ActionMessage("error.input.length.text",
                            extDomain, GSConstMain.MAX_LENGTH_DOMAIN);
                    StrutsUtil.addMessage(errors, msg, "man430ExtDomain.error.input.length.text");
                }
            }
        }

        return errors;
    }


    /**
     * <p>man430ExtPageDspKbnを取得します。
     * @return man430ExtPageDspKbn
     * */
    public int getMan430ExtPageDspKbn() {
        return man430ExtPageDspKbn__;
    }
    /**
     * <p>man430ExtPageDspKbnをセットします。
     * @param man430ExtPageDspKbn man430ExtPageDspKbn
     * */
    public void setMan430ExtPageDspKbn(int man430ExtPageDspKbn) {
        man430ExtPageDspKbn__ = man430ExtPageDspKbn;
    }

    /**
     * <p>man430ExtDomainAreaを取得します。
     * @return man430ExtDomainArea
     * */
    public String getMan430ExtDomainArea() {
        return man430ExtDomainArea__;
    }
    /**
     * <p>man430ExtDomainAreaをセットします。
     * @param man430ExtDomainArea man430ExtDomainArea
     * */
    public void setMan430ExtDomainArea(String man430ExtDomainArea) {
        man430ExtDomainArea__ = man430ExtDomainArea;
    }

    /**
     * <p>man430InitFlgを取得します。
     * @return man430InitFlg
     * */
    public int getMan430InitFlg() {
        return man430InitFlg__;
    }
    /**
     * <p>man430InitFlgをセットします。
     * @param man430InitFlg man430InitFlg
     * */
    public void setMan430InitFlg(int man430InitFlg) {
        man430InitFlg__ = man430InitFlg;
    }

}
