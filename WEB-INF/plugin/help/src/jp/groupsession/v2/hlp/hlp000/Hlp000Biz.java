package jp.groupsession.v2.hlp.hlp000;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.config.HelpInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.hlp.HelpBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] ヘルプ サイドメニューのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp000Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Hlp000Biz.class);
    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl パラメータ情報
     * @param pconfig プラグイン設定情報
     * @param con コネクション
     * @param contextPath コンテキストパス
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(
            Hlp000ParamModel paramMdl,
            PluginConfig pconfig,
            Connection con,
            String contextPath,
            RequestModel reqMdl) throws SQLException {
        log__.debug("ヘルプサイドメニュー画面表示情報取得開始");

        List<MenuInfo> menuInfoList = __getMenuInfo(paramMdl, pconfig, reqMdl, con, contextPath);
        log__.debug(">>> Hlp000Form :" + paramMdl);
        paramMdl.setMenuInfoList(menuInfoList);
    }

    /**
     * <br>[機  能] メニュー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param pconfig プラグイン設定情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param contextPath コンテキストパス
     * @return メニュー情報
     * @throws SQLException SQL実行例外
     */
    private List<MenuInfo> __getMenuInfo(
            Hlp000ParamModel paramMdl,
            PluginConfig pconfig,
            RequestModel reqMdl,
            Connection con,
            String contextPath)
    throws SQLException {
        log__.debug("start");

        List<MenuInfo> menuInfoList = new ArrayList<MenuInfo>();

        //ヘルプメニュー表示設定
        List<Plugin> pluginList = pconfig.getPluginDataList();

        HelpBiz helpBiz = new HelpBiz();
        for (Plugin pluginData : pluginList) {
            HelpInfo helpInfo = pluginData.getHelpInfo();
            if (helpInfo != null && "true".equals(helpInfo.getView())) {

                menuInfoList.add(
                        __createMenuInfo(pluginData.getId(),
                                    pluginData.getName(reqMdl),
                                    helpBiz.createHelpUrl(reqMdl, contextPath, pluginData.getId()),
                                    helpBiz.createImageUrl(reqMdl, contextPath, pluginData.getId()),
                                    pluginData.getDescription()));
            }
        }

        log__.debug("end");
        return menuInfoList;

    }

    /**
     * <br>[機  能] メニュー情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param id プラグインID
     * @param name プラグイン名称
     * @param url URL
     * @param iUrl 画像のURL
     * @param description 説明
     * @return メニュー情報
     */
    private MenuInfo __createMenuInfo(
            String id,
            String name,
            String url,
            String iUrl,
            String description) {
        MenuInfo menuInfo = new MenuInfo();

        menuInfo.setPluginId(id);
        menuInfo.setName(name);

        menuInfo.setUrl(url);
        menuInfo.setImagesUrl(iUrl);
        menuInfo.setDescription(description);

        return menuInfo;
    }

}
