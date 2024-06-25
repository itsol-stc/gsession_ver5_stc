package jp.groupsession.v2.api.smail.mail.count;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメール未読件数を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailCountAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailCountAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aform  フォーム
     * @param req    リクエスト
     * @param res    レスポンス
     * @param con    DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aform, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        ApiSmlMailCountForm form = (ApiSmlMailCountForm) aform;
        GsMessage gsMsg = new GsMessage(req);

        int sacSid  = form.getSacSid();
        int cntType = form.getCntType();
        int userSid = getSessionUserSid(req);

        int smjCnt   = -1;
        int smwCnt   = -1;
        int trashCnt = -1;
        List<LabelValueBean> labelList = null;

        try {
            // アカウントチェック
            ApiSmlMailBiz biz = new ApiSmlMailBiz();
            ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            //未確認の件数を取得する。
            SmlCommonBiz smlCmnBiz = new SmlCommonBiz(getRequestModel(req));

            //未読受信メール件数取得
            if (cntType < 0 || String.valueOf(cntType).equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                smjCnt = smlCmnBiz.getUnopenedMsgCnt(sacSid, con);
            }

            //草稿メール件数取得
            if (cntType < 0 || String.valueOf(cntType).equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                smwCnt = smlCmnBiz.getSokoMsgCnt(sacSid, con);
            }

            //ゴミ箱メール未読件数取得
            if (cntType < 0 || String.valueOf(cntType).equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                trashCnt = smlCmnBiz.getUnopenedGomiMsgCnt(sacSid, con);
            }

            // ラベル未読件数
            if (cntType < 0 || String.valueOf(cntType).equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
                ApiSmlMailDao apiDao = new ApiSmlMailDao(con);
                labelList = apiDao.getLabelCount(sacSid);
            }

        } catch (SQLException e) {
            log__.error("未開封メッセージカウントの取得に失敗", e);
        }

        //Result
        Element  resultSet = new Element("ResultSet");
        Document doc       = new Document(resultSet);
        Element  result    = null;

        log__.debug("createXml start");
        Integer resultCnt = 0;
        if (smjCnt >= 0) {
            result = new Element("Result");
            result.addContent(_createElement("cntType", GSConstSmail.TAB_DSP_MODE_JUSIN));
            result.addContent(_createElement("cntNum",  smjCnt));   // バッチ表示数値
            resultSet.addContent(result);
            resultCnt++;
        }
        if (smwCnt >= 0) {
            result = new Element("Result");
            result.addContent(_createElement("cntType", GSConstSmail.TAB_DSP_MODE_SOKO));
            result.addContent(_createElement("cntNum",  smwCnt));   // バッチ表示数値
            resultSet.addContent(result);
            resultCnt++;
        }
        if (trashCnt >= 0) {
            result = new Element("Result");
            result.addContent(_createElement("cntType", GSConstSmail.TAB_DSP_MODE_GOMIBAKO));
            result.addContent(_createElement("cntNum",  trashCnt)); // バッチ表示数値
            resultSet.addContent(result);
            resultCnt++;
        }
        if (labelList != null) {
            for (LabelValueBean been : labelList) {
                result = new Element("Result");
                result.addContent(_createElement("cntType", GSConstSmail.TAB_DSP_MODE_LABEL));
                result.addContent(_createElement("slbSid", Integer.valueOf(been.getLabel())));
                result.addContent(_createElement("cntNum", Integer.valueOf(been.getValue())));
                resultSet.addContent(result);
            }
            resultCnt += labelList.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        log__.debug("createXml end");
        return doc;
    }

}
