package jp.groupsession.v2.api.webmail.account.defaultinput;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.webmail.account.list.ApiWmlAccountListAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn170.dao.Cmn170Dao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;

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
public class ApiWmlAccountDefInputAction extends ApiWmlAccountListAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlAccountDefInputAction.class);

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

        ApiWmlAccountDefInputForm form  = (ApiWmlAccountDefInputForm) aForm;
        int wacSid = form.getWacSid();

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        int userSid = umodel.getUsrsid();

        WmlAccountModel           accountMdl  = null;
        List<WmlDirectoryModel>   dirList     = null;
        List<WmlAccountSignModel> signList    = null;
        WmlAdmConfModel           confMdl     = null;
        int                       themeId     = 0;


        //アカウント一覧を取得する。
        try {
            // テーマ
            Cmn170Dao cmnDao = new Cmn170Dao(con);
            themeId = cmnDao.select2(userSid);

            //アカウント情報を取得する
            WmlAccountDao accountDao = new WmlAccountDao(con);

            // アカウントの指定がある場合、使用可能かを判定する
            if (wacSid >= 0) {
                WmlDao wmlDao = new WmlDao(con);
                if (!wmlDao.canUseAccount(wacSid, userSid)) {
                    wacSid = -1; // 使用不可なので、アカウントSIDを初期化
                }
            }

            // アカウントの指定がない場合、デフォルトアカウントを取得
            if (wacSid < 0) {
                wacSid = accountDao.getDefaultAccountSid(userSid);
            }

            if (wacSid >= 0) {
                // アカウント情報取得
                accountMdl = accountDao.select(wacSid);

                // 管理者設定取得
                WmlAdmConfDao confDao = new WmlAdmConfDao(con);
                confMdl = confDao.selectAdmData();

                // ディレクトリ一覧取得
                WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
                dirList = dirDao.getDirectoryList(wacSid);

                // 署名一覧取得
                WmlAccountSignDao signDao = new WmlAccountSignDao(con);
                signList = signDao.getSignList(wacSid);
            }
        } catch (SQLException e) {
            log__.error("webmailのアカウント取得に失敗", e);
        }

        if (accountMdl == null) {
            // アカウントがない場合
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.not.exist.account");
            StrutsUtil.addMessage(errors, msg, "account");
            addErrors(req, errors);
            return null;
        }

        //Result
        Element result = new Element("Result");
        resultSet.addContent(result);

        // 共通の初期設定情報
        if (confMdl.getWadSendLimit() == GSConstWebmail.WAD_SEND_LIMIT_LIMITED) {
            // 送信メールサイズ制限
            result.addContent(_createElement("sendLimit",  confMdl.getWadSendLimitSize()));
        }
        if (confMdl.getWadBcc() == GSConstWebmail.WAD_BCC_CONVERSION) {
            // ＢＣＣ強制変換
            result.addContent(_createElement("bccTh",  confMdl.getWadBccTh()));
        }

        this.setWmlElement(result, accountMdl, confMdl, dirList, signList, themeId);

        return doc;
    }
}
