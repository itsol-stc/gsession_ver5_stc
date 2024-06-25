package jp.groupsession.v2.api.cmn.otpcheck;

import java.io.IOException;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
/**
 *
 * <br>[機  能] ワンタイムパスワード使用確認API Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCmnOtpCheckAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCmnOtpCheckAction.class);
    /** ワンタイムパスワード使用しない*/
    private static final int OTPCHECK_NOUSE__ = 0;
    /** ワンタイムパスワード使用する*/
    private static final int OTPCHECK_USE__ = 1;
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        try {
            ApiCmnOtpCheckForm thisForm = (ApiCmnOtpCheckForm) form;
            String userId = thisForm.getUserid();

            AuthDao adao = new AuthDao(con);
            BaseUserModel smodel = null;
            try {
                smodel = adao.selectLoginNoPwd(userId, null);
            } catch (SQLException e) {
                log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
                throw e;
            }
            //XML情報の取得
            Document doc = null;
            if (smodel == null) {
                ActionErrors errors = new ActionErrors();
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.search.notfound.user"),
                        "userid");
                writeErrorResponse(map, req, res, errors);
                return null;
            }
            //認証後 個体識別番号制御によるファイアウォールチェック
            FirewallBiz firewall = FirewallBiz.getInstance();
            if (!firewall.additionalCheckForMbl(smodel, con, false)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }

            doc = createXml(form, req, res, con, smodel);
            writeResponse(map, req, res, doc);

        } catch (SocketException e) {
            //クライアントユーザがキャンセルした場合の例外
        } catch (IOException e) {
            Class<? extends IOException> ecs = e.getClass();
            String ename = ecs.getName();
            if ("org.apache.catalina.connector.ClientAbortException".equals(ename)) {
                //クライアントユーザがキャンセルした場合の例外(コンテナTomcat)
                //何もしない
            } else {
                throw e;
            }
        }
        return null;
    }
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");



        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //Result
        Element result = new Element("Result");
        resultSet.addContent(result);

        Element type = new Element("OtpUse");
        //ワンタイムパスワード必要判定
        OnetimePasswordBiz onetimePasswordBiz = new OnetimePasswordBiz();
        if (onetimePasswordBiz.isNeedOtpAuth(req, umodel, con)) {
            type.addContent(Integer.toString(OTPCHECK_USE__));
        } else {
            type.addContent(Integer.toString(OTPCHECK_NOUSE__));
        }

        result.addContent(type);

        log__.debug("createXml end");
        return doc;
    }


}
