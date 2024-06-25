package jp.groupsession.v2.api.smail.account.defaultinput;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.smail.account.ApiSmlAccountDao;
import jp.groupsession.v2.api.smail.account.ApiSmlAccountModel;
import jp.groupsession.v2.api.smail.account.list.ApiSmlAccountListAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;

/**
 * <br>[機  能] ショートメールアカウント＋初期設定を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlAccountDefInputAction extends ApiSmlAccountListAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlAccountDefInputAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        ApiSmlAccountDefInputForm form  = (ApiSmlAccountDefInputForm) aForm;
        SmlAccountModel    acMdl = null;
        int sacSid  = form.getSacSid();

        int userSid = getSessionUserSid(req);
        List<ApiSmlAccountModel> autoList = null;
        HashMap<Integer, Integer> ukoFlgMap = null;
        try {
            // アカウントの指定がある場合、使用可能かを判定する
            if (sacSid >= 0) {
                SmailDao smlDao = new SmailDao(con);
                if (!smlDao.canUseCheckAccount(userSid, sacSid)) {
                    sacSid = -1; // 使用不可なので、アカウントSIDを初期化
                }
            }
            // アカウントの指定がない場合、デフォルトアカウントを取得
            if (sacSid < 0) {
                SmlCommonBiz biz = new SmlCommonBiz(getRequestModel(req));
                sacSid = biz.getDefaultAccount(con, umodel.getUsrsid());
            }

            if (sacSid > 0) {
                SmlAccountDao  accountDao = new SmlAccountDao(con);
                acMdl = accountDao.select(sacSid);
            }
            if (acMdl != null) {
                List<Integer> sacSidList = new ArrayList<Integer>();
                sacSidList.add(acMdl.getSacSid());
                HashMap<Integer, List<ApiSmlAccountModel>> autoMap =
                                              this.getSendToList(con, userSid, sacSidList);
                if (autoMap != null) {
                    autoList = autoMap.get(Integer.valueOf(acMdl.getSacSid()));
                }
                ApiSmlAccountDao  accountDao = new ApiSmlAccountDao(con);
                ukoFlgMap = accountDao.getUkoFlgMap(sacSidList);
            }

        } catch (SQLException e) {
            log__.error("ショートメール使用可能アカウント一覧の取得に失敗", e);
        }

        //Result
        Element resultSet  = new Element("ResultSet");
        Document doc = new Document(resultSet);

        if (acMdl != null) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            this.setSmlAccountElement(result, acMdl, autoList, ukoFlgMap, null, null, false, req);
        }
        return doc;
    }


}
