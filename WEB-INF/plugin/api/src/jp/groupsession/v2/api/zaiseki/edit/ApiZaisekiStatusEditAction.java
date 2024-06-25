package jp.groupsession.v2.api.zaiseki.edit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席状況を変更するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiZaisekiStatusEditAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiZaisekiStatusEditAction.class);

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
        log__.debug("createXml start");
        ApiZaisekiStatusEditForm azForm = (ApiZaisekiStatusEditForm) form;

        //在籍管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstZaiseki.PLUGIN_ID_ZAISEKI, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstZaiseki.PLUGIN_ID_ZAISEKI));
            return null;
        }
        //入力チェック
        ActionErrors errors = azForm.validateSchSearch(req);
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }

        //在席状況を更新
        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));

            CmnUsrInoutModel param = new CmnUsrInoutModel();
            String usid = azForm.getUsid();
            int iusid = 0;
            if (usid == null) {
                iusid = umodel.getUsrsid();
            } else {
                iusid = Integer.parseInt(usid);
            }
            UDate now = new UDate();
            param.setUioSid(iusid);
            param.setUioStatus(Integer.parseInt(azForm.getStatus()));
            //コメント
            if ("0".equals(azForm.getComeflg().trim())) {
                //更新する
                param.setUioBiko(NullDefault.getString(azForm.getComment(), ""));
                log__.debug("コメント 更新しない");
            } else if ("1".equals(azForm.getComeflg().trim())) {
                log__.debug("コメント 更新しない");
                //更新しない
                //既存のコメントを取得
                UserSearchModel sumodel = zbiz.getZskStatusData(iusid, con);
                if (sumodel != null) {
                    param.setUioBiko(sumodel.getUioComment());
                    log__.debug("既存コメント = " + sumodel.getUioComment());
                }
            }

            param.setUioEuid(iusid);
            param.setUioEdate(now);

            int status = param.getUioStatus();

            zbiz.updateZskStatus(con, param);
            String opLog = zbiz.opLogContent(iusid, status, con);
            //ログ出力
            zbiz.outPutApiLog(con, usid, this.getClass().getCanonicalName(),
                    getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE,
                    opLog);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        result.addContent("OK");
        return doc;
    }

}
