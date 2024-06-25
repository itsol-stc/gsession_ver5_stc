package jp.groupsession.v2.api.man.plugin;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] プラグイン情報を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiManPluginAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiManPluginAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("");
        PluginConfig pconfig = getPluginConfig(req);
        pconfig = getPluginConfigForMain(pconfig, con, umodel.getUsrsid());
        
        
        
        

        CmnContmDao cntDao = new CmnContmDao(con);
        int menuStatic = cntDao.getMenuStatic();

        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<CmnTdispModel> dispList = null;
        if (menuStatic == GSConstMain.MENU_STATIC_USE) {
            //メニュー項目固定の場合、管理者設定を優先
            dispList = tdispDao.getAdminTdispList();
        } else {
            dispList = tdispDao.select(getSessionUserModel(req).getUsrsid());
        }


        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //XMLデータ作成
        for (Plugin plugin : pconfig.getPluginDataList()) {

            if (!plugin.getId().equals("common")
                && !plugin.getId().equals("help")
                && !plugin.getId().equals("license")) {
                //Result
                Element result = new Element("Result");
                resultSet.addContent(result);

                //プラグインID
                Element plgid = new Element("Plgid");
                plgid.addContent(plugin.getId());
                result.addContent(plgid);

                //プラグイン名
                Element plgname = new Element("PlgName");
                plgname.addContent(plugin.getName(getRequestModel(req)));
                result.addContent(plgname);

                //プラグイン使用可否
                Element plgkbn = new Element("PlgKbn");
                int kbn;
                if (dispList == null || dispList.isEmpty()) {
                    //トップ表示設定が登録されていない場合
                    kbn = 1;
                } else {
                    kbn = 0;
                    for (CmnTdispModel dbDispMdl : dispList) {
                        if (plugin.getId().equals(dbDispMdl.getTdpPid())) {
                            //使用可
                            kbn = 1;
                            break;
                        }
                    }
                }
                plgkbn.addContent(Integer.toString(kbn));
                result.addContent(plgkbn);

                //プラグインURL
                Element plgurl = new Element("PlgUrl");
                if (plugin.getTopMenuInfo() != null &&
                        !StringUtil.isNullZeroString(plugin.getTopMenuInfo().getUrl())) {
                    plgurl.addContent(plugin.getTopMenuInfo().getUrl());
                    result.addContent(plgurl);
                }
            }
        }
        return doc;
    }

}
