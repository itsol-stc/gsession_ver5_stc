package jp.groupsession.v2.api.webmail.badgenum;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.WmlDirectoryCountModel;
import jp.groupsession.v2.wml.model.WmlLabelCountModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBメールのアカウント一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlBadgeNumAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlBadgeNumAction.class);

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

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        ApiWmlBadgeNumForm form = (ApiWmlBadgeNumForm) aForm;

        int wacSid     = form.getWacSid();
        int type       = form.getBadgeType();
        int totalCount = 0;

        List<WmlDirectoryCountModel> directoryList = null;
        List<WmlLabelCountModel>     labelList     = null;

        try {
            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(wacSid, getSessionUserSid(req))) {
                // アカウントがない場合
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            // ディレクトリ一覧情報取得
            WmlDirectoryDao directoryDao = new WmlDirectoryDao(con);
            if (type < 0 || type == ApiWmlBadgeNumForm.BADGE_TYPE_MAILBOX_) {
                directoryList = directoryDao.getDirectoryList(reqMdl, wacSid);
                if (directoryList != null) {
                    WmlMaildataDao mailDataDao = new WmlMaildataDao(con);
                    for (WmlDirectoryCountModel mdl : directoryList) {
                        if (mdl.getType() != GSConstWebmail.DIR_TYPE_DUST) {
                            // ゴミ箱以外の未読メール数の合計値取得
                            totalCount += mdl.getNoReadCount();
                        }
                        if (mdl.getType() == GSConstWebmail.DIR_TYPE_DRAFT
                         || mdl.getType() == GSConstWebmail.DIR_TYPE_NOSEND) {
                            //予約送信 or 草稿の場合、ディレクトリ内のメール件数を設定する
                            int count = mailDataDao.selectMailCntInDir(wacSid, mdl.getId());
                            mdl.setNoReadCount(count);
                        }
                    }
                }
            }

            // ラベル一覧情報取得
            if (type < 0 || type == ApiWmlBadgeNumForm.BADGE_TYPE_LABEL_) {
                WmlLabelDao labelDao = new WmlLabelDao(con);
                labelList = labelDao.getLabelListWithMidoku(wacSid);
            }
        } catch (SQLException e) {
            log__.error("バッジ数値の取得に失敗", e);
        }

        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //Result
        Integer resultCnt = 0;
        if (directoryList != null) {
            // 合計数値をセット(SID=-1)
            Element result = new Element("Result");
            result.addContent(_createElement("badgeType",
                                                     ApiWmlBadgeNumForm.BADGE_TYPE_MAILBOX_));
            result.addContent(_createElement("badgeSid", -1));         // ディレクトリSID
            result.addContent(_createElement("badgeNum", totalCount)); // バッチ表示数値
            resultSet.addContent(result);

            // 各ディレクトリ(メールボックス)の情報をセット
            for (WmlDirectoryCountModel mdl : directoryList) {
                result = new Element("Result");
                result.addContent(_createElement("badgeType",
                                                         ApiWmlBadgeNumForm.BADGE_TYPE_MAILBOX_));
                result.addContent(_createElement("badgeSid", mdl.getId()));          // ディレクトリSID
                result.addContent(_createElement("badgeNum", mdl.getNoReadCount())); // バッチ表示数値
                resultSet.addContent(result);
            }
            resultCnt += directoryList.size();
        }
        if (labelList != null) {
            // 各ラベルの情報をセット
            for (WmlLabelCountModel mdl : labelList) {
                Element result = new Element("Result");
                result.addContent(_createElement("badgeType",
                                                         ApiWmlBadgeNumForm.BADGE_TYPE_LABEL_));
                result.addContent(_createElement("badgeSid", mdl.getId()));          // ラベルSID
                result.addContent(_createElement("badgeNum", mdl.getNoReadCount())); // バッチ表示数値
                resultSet.addContent(result);
            }
            resultCnt += labelList.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));

        return doc;
    }
}
