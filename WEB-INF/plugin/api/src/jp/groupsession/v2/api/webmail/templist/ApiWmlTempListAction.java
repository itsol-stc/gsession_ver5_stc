package jp.groupsession.v2.api.webmail.templist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;

/**
 * <br>[機  能] WEBメールテンプレート一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlTempListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlTempListAction.class);

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

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        ApiWmlTempListForm form = (ApiWmlTempListForm) aForm;

        int wacSid  = form.getWacSid();
        int wtpType = form.getWtpType();

        List<WmlMailTemplateModel> list = null;
        HashMap<Integer, List<MailTempFileModel>> binMap = null;

        try {
            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            RequestModel reqMdl = getRequestModel(req);
            if (!wmlDao.canUseAccount(wacSid, getSessionUserSid(req))) {
                // アカウントがない場合
                GsMessage    gsMsg  = new GsMessage(reqMdl);
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            WmlMailTemplateDao  wmlTmpDao = new WmlMailTemplateDao(con);
            list = wmlTmpDao.getMailTemplateList(wacSid);

            if (list != null && list.size() > 0) {
                List<Integer> wtpSidList = new ArrayList<Integer>();
                for (WmlMailTemplateModel mdl : list) {
                    wtpSidList.add(mdl.getWtpSid());
                }
                ApiWmlTempListDao  tmpDao = new ApiWmlTempListDao(con);
                binMap = tmpDao.getTempFileList(reqMdl, wtpSidList);
            }

        } catch (SQLException e) {
            log__.error("WEBメールテンプレート一覧の取得に失敗", e);
        }

        //Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (list != null) {
            for (WmlMailTemplateModel mdl : list) {
                // 全件取得以外の場合、該当データ以外を除外
                if (wtpType < 0 || mdl.getWtpType() == wtpType) {
                    Element result = new Element("Result");
                    resultSet.addContent(result);
                    result.addContent(_createElement("wacSid",   mdl.getWacSid()));
                    result.addContent(_createElement("wtpSid",   mdl.getWtpSid()));
                    result.addContent(_createElement("wtpName",  mdl.getWtpName()));
                    result.addContent(_createElement("wtpType",  mdl.getWtpType()));
                    result.addContent(_createElement("wtpOrder", mdl.getWtpOrder()));
                    result.addContent(_createElement("title",    mdl.getWtpTitle()));
                    result.addContent(_createElement("body",     mdl.getWtpBody()));

                    // 添付ファイル一覧
                    Element tmpFileSet = new Element("tmpFileSet");
                    result.addContent(tmpFileSet);
                    Integer tmpFileCnt = 0;
                    if (binMap != null && binMap.get(mdl.getWtpSid()) != null) {
                        List<MailTempFileModel> binList = binMap.get(mdl.getWtpSid());
                        mdl.getWtpSid();
                        for (MailTempFileModel file : binList) {
                            Element binEle = new Element("tmpFile");
                            binEle.addContent(_createElement("binSid",   file.getBinSid()));
                            binEle.addContent(_createElement("fileName", file.getFileName()));
                            binEle.addContent(_createElement("filePath", file.getFilePath()));
                            binEle.addContent(_createElement("fileSize", file.getFileSize()));
                            tmpFileSet.addContent(binEle);
                        }
                        tmpFileCnt = binList.size();
                    }
                    tmpFileSet.setAttribute("Count", Integer.toString(tmpFileCnt));

                    resultCnt++;
                }
            }
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;
    }

}
