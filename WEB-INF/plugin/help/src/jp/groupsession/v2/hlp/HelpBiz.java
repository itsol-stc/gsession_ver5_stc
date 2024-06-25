package jp.groupsession.v2.hlp;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] ヘルプ機能共通ビジネスロジッククラス
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HelpBiz {

    /**
     * <br>[機  能] ヘルプトップページの絶対パスを生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param contextPath コンテキストパス
     * @param pluginId プラグインID
     * @return 絶対パス
     */
    public String createHelpUrl(RequestModel reqMdl, String contextPath,
                                String pluginId) {
        StringBuilder buf = new StringBuilder("");
        buf.append(getHelpContextPath(reqMdl, contextPath));

        buf.append(pluginId);
        buf.append("/help");
        buf.append("/");
        buf.append(GSConstHelp.HELP_TOP_PAGE);

        return buf.toString();
    }

    /**
     * <br>[機  能] ヘルプ画像の絶対パスを生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param contextPath コンテキストパス
     * @param pluginId プラグインID
     * @return 絶対パス
     */
    public String createImageUrl(RequestModel reqMdl, String contextPath,
                                String pluginId) {
        StringBuilder buf = new StringBuilder("");
        buf.append(getHelpContextPath(reqMdl, contextPath));
        buf.append(pluginId);
        buf.append("/images/");

        return buf.toString();
    }

    /**
     * <br>[機  能] ヘルプページのコンテキストパスを生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param contextPath コンテキストパス
     * @return 絶対パス
     */
    public String getHelpContextPath(RequestModel reqMdl, String contextPath) {
        StringBuilder buf = new StringBuilder("");
        buf.append(IOTools.replaceSlashFileSep(contextPath));

        String reqDomain = NullDefault.getString(reqMdl.getDomain(), "");
        if (!reqDomain.equals(GSConst.GS_DOMAIN)) {
            buf.append(reqDomain + "/");
        }

        return buf.toString();
    }
}
