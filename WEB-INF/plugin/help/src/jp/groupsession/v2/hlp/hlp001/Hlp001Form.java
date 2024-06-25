package jp.groupsession.v2.hlp.hlp001;

import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.TopMenuInfo;
import jp.groupsession.v2.struts.AbstractGsForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] ヘルプ フレーム(ヘッダーとボディの)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp001Form extends AbstractGsForm {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Hlp001Form.class);

    /** トップメニューのページ */
    private int menuPage__ = 1;
    /** BODYに表示するURL */
    private String url__ = null;

    /** プラグインID（ヘルプ用） */
    private String pluginid__ = null;
    /** プログラムID（ヘルプ用） */
    private String pgid__ = null;

    /**
     * <p>pgid を取得します。
     * @return pgid
     */
    public String getPgid() {
        return pgid__;
    }

    /**
     * <p>pgid をセットします。
     * @param pgid pgid
     */
    public void setPgid(String pgid) {
        pgid__ = pgid;
    }

    /**
     * <p>menuPage を取得します。
     * @return menuPage
     */
    public int getMenuPage() {
        return menuPage__;
    }

    /**
     * <p>menuPage をセットします。
     * @param menuPage menuPage
     */
    public void setMenuPage(int menuPage) {
        menuPage__ = menuPage;
    }

    /**
     * @return url を戻します。
     */
    public String getUrl() {
        return url__;
    }

    /**
     * @param url 設定する url。
     */
    public void setUrl(String url) {
        url__ = url;
    }

    /**
     * <br>[機  能] ログイン時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig プラグイン情報
     * @return エラー
     */
    public ActionErrors validateBodyUrl(PluginConfig pconfig) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //入力なしの判定
        if (url__ == null || url__.length() == 0) {
            //
            return errors;
        }
        url__ = url__.trim();

        //plugin.xmlに記述されたものは無条件に許可
        List < Plugin > plist = pconfig.getPluginDataList();
        for (Plugin plugin : plist) {
            TopMenuInfo tminfo = plugin.getTopMenuInfo();
            if (tminfo != null) {
                String url = tminfo.getUrl();
                log__.debug("plugin url = " + url + "|||||");
                log__.debug("request url = " + url__ + "|||||");
                if (url__.equals(url)) {
                    return errors;
                }
            }
        }
        
        //改行文字を除去
        if (!StringUtil.isNullZeroString(url__)) {
            url__ = url__.replaceAll("\r", "");
            url__ = url__.replaceAll("\n", "");
        }

        //外部URLは認めない
        String parseUrl = StringUtil.transToLink(url__, 0);
        if (!url__.equals(parseUrl)) {
            //
            msg = new ActionMessage("error.input.gaibu.url");
            StrutsUtil.addMessage(errors, msg, "error.input.gaibu.url");
        }

        //javascriptのURLも認めない
        if (url__.toLowerCase().indexOf("javascript") != -1) {
            //
            msg = new ActionMessage("error.input.js.url");
            StrutsUtil.addMessage(errors, msg, "error.input.js.url");
        }
        return errors;
    }

    /**
     * <p>pluginid を取得します。
     * @return pluginid
     */
    public String getPluginid() {
        return pluginid__;
    }

    /**
     * <p>pluginid をセットします。
     * @param pluginid pluginid
     */
    public void setPluginid(String pluginid) {
        pluginid__ = pluginid;
    }

}
