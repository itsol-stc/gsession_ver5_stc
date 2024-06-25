package jp.groupsession.v2.rss.rss030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssValidate;
import jp.groupsession.v2.rss.rss020.Rss020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] RSSリーダー RSS登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss030Form extends Rss020Form {

    /** 表示件数 */
    private int rss030ViewCnt__ = GSConstRss.RSS_DEFAULT_VIEWCNT;
    /** メイン表示 */
    private int rss030mainView__ = GSConstRss.RSS_MAIN_VIEWFLG_SHOW;

    /** 表示件数コンボ */
    private List<LabelValueBean> viewCntList__ = null;
    /** ヘルプモード */
    private String helpMode__ = GSConstRss.RSSHELPMODE_ADD;
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, int userSid, RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textRssName = gsMsg.getMessage("rss.14");

        //-- RSS名称チェック --
        errors = RssValidate.validateCmnFieldText(
                                                errors,
                                                textRssName,
                                                getRssTitle(),
                                                "rssTitle",
                                                GSConstRss.MAX_LENGTH_RSSNAME,
                                                true);

        //-- フィードURLチェック --
        errors = super.validateCheck(con, userSid, errors, reqMdl);

        //-- URLチェック --
        errors = RssValidate.validateCmnFieldText(
                                                errors,
                                                GSConstRss.TEXT_RSS_URL,
                                                getRssUrl(),
                                                "rssUrl",
                                                GSConstRss.MAX_LENGTH_URL,
                                                true);
        
        if (!ValidateUtil.isHttpUrlFormat(getRssUrl())) {
            String url = gsMsg.getMessage("cmn.url");
            ActionMessage msg = new ActionMessage("error.input.format.text", url);
            StrutsUtil.addMessage(errors, msg, "error.input.format.url");
        }

        return errors;
    }

    /**
     * <p>rss030ViewCnt を取得します。
     * @return rss030ViewCnt
     */
    public int getRss030ViewCnt() {
        return rss030ViewCnt__;
    }

    /**
     * <p>rss030ViewCnt をセットします。
     * @param rss030ViewCnt rss030ViewCnt
     */
    public void setRss030ViewCnt(int rss030ViewCnt) {
        rss030ViewCnt__ = rss030ViewCnt;
    }

    /**
     * <p>viewCntList を取得します。
     * @return viewCntList
     */
    public List<LabelValueBean> getViewCntList() {
        return viewCntList__;
    }

    /**
     * <p>viewCntList をセットします。
     * @param viewCntList viewCntList
     */
    public void setViewCntList(List<LabelValueBean> viewCntList) {
        viewCntList__ = viewCntList;
    }

    /**
     * <p>rss030mainView を取得します。
     * @return rss030mainView
     */
    public int getRss030mainView() {
        return rss030mainView__;
    }

    /**
     * <p>rss030mainView をセットします。
     * @param rss030mainView rss030mainView
     */
    public void setRss030mainView(int rss030mainView) {
        rss030mainView__ = rss030mainView;
    }

    /**
     * <p>helpMode を取得します。
     * @return helpMode
     */
    public String getHelpMode() {
        return helpMode__;
    }

    /**
     * <p>helpMode をセットします。
     * @param helpMode helpMode
     */
    public void setHelpMode(String helpMode) {
        helpMode__ = helpMode;
    }

}
