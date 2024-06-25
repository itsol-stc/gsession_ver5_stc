package jp.groupsession.v2.api.user.prefarence.sort;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPriSortConfModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 *
 * <br>[機  能] WEBAPI ユーザ情報ソート条件の取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiUserSortInfoAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiUserSortInfoAction.class);
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        //DBより設定情報を取得。なければデフォルト値とする。
        UsrCommonBiz biz = new UsrCommonBiz(con, getRequestModel(req));
        //管理者が設定したソート設定値を取得
        CmnUsrAdmSortConfModel aconf = biz.getSortAdmConfModel(con);

        //各ユーザ独自のソート設定値を取得
        CmnUsrPriSortConfModel pconf = biz.getSortPriConfModel(con, umodel.getUsrsid(), aconf);


        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //Result
        Element result = new Element("Result");
        resultSet.addContent(result);

        if (aconf.getUasSortKbn() == GSConstUser.DEFO_DSP_USR) {
            //各ユーザが設定したメンバー表示設定を反映
            log__.debug("***ユーザが設定したソート順で表示します***");
            //ソート1
            result.addContent(_createElement("SortKey1", pconf.getUpsSortKey1()));
            result.addContent(_createElement("SortOrder1", pconf.getUpsSortOrder1()));
            //ソート2
            result.addContent(_createElement("SortKey2", pconf.getUpsSortKey2()));
            result.addContent(_createElement("SortOrder2", pconf.getUpsSortOrder2()));

        } else {
            //管理者が設定したデフォルトメンバー表示設定を反映

            //ソート1
            result.addContent(_createElement("SortKey1", aconf.getUasSortKey1()));
            result.addContent(_createElement("SortOrder1", aconf.getUasSortOrder1()));
            //ソート2
            result.addContent(_createElement("SortKey2", aconf.getUasSortKey2()));
            result.addContent(_createElement("SortOrder2", aconf.getUasSortOrder2()));
        }
        log__.debug("createXml end");

        return doc;
    }

}
