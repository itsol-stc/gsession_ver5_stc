package jp.groupsession.v2.api.webmail.mail.check;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.api.webmail.mail.ApiWmlMailBiz;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.exception.WmlTempDirNoneException;
import jp.groupsession.v2.wml.exception.WmlTempFileNameException;
import jp.groupsession.v2.wml.wml012.Wml012AddressModel;
import jp.groupsession.v2.wml.wml012.Wml012Biz;

/**
 * <br>[機  能] WEBメールを送信前チェックするWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlMailCheckAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlMailCheckAction.class);

    /** 送信先ドメイン色固定データ */
    private static String[] colorArray__ = {
        "#0000FF",
        "#FF0000",
        "#009900",
        "#ff9900",
        "#333333",
        "#000080",
        "#ffb6c1",
        "#2f4f4f",
        "#a0522d",
        "#808080"
    };

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

        log__.debug("createXml start");
        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        String  message   = null;

        ApiWmlMailCheckForm form = (ApiWmlMailCheckForm) aForm;
        GsMessage    gsMsg  = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);

        int        userSid     = getSessionUserSid(req);
        String     appRootDir  = getAppRootPath();

        List<Wml012AddressModel> toAdrList  = null;
        List<Wml012AddressModel> ccAdrList  = null;
        List<Wml012AddressModel> bccAdrList = null;
        ActionErrors errors = new ActionErrors();

        boolean commitFlg = false;

        try {
            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(form.getWacSid(), userSid)) {
                //message = gsMsg.getMessage("wml.191");
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            // フォームパラメータチェック
            ApiWmlMailBiz biz = new ApiWmlMailBiz();
            biz.validateFormSmail(errors, con, gsMsg, form, userSid);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return null;
            }

            // パラメータ最大容量チェック
            biz.validateMailSize(errors, req, gsMsg);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return null;
            }

            // 送信メール判定
            if (message == null) {

                String tempDir = WmlMailSendBiz.getSendTempDir(reqMdl);
                String[] errMessages = form.validateSendMail(con, reqMdl, appRootDir);
                
                // 送信時に容量のチェックが行われるため、事前にテンポラリディレクトリに添付ファイルのアップロードを行う
                biz.uploadTmpFile(errors, con, gsMsg, reqMdl, form, appRootDir, tempDir);
                if (!errors.isEmpty()) {
                    addErrors(req, errors);
                    log__.info(errors.toString());
                    return null;
                }

                String[] tmpErrMsg = null;
                String tmpErrText;
                if (errMessages == null || errMessages.length == 0) {
                    WmlMailSendBiz sendBiz = form.createMailSendBiz(
                        con, getGsContext(), appRootDir, tempDir, reqMdl);
                    try {
                        sendBiz.checkMailSize(con, getGsContext(), userSid,
                            appRootDir, tempDir, appRootDir, reqMdl.getLocale(), 0);
                    } catch (WmlTempDirNoneException e) {
                        //テンポラリディレクトリがなかった時
                        tmpErrText = gsMsg.getMessage("wml.222", new String[] {
                                                        gsMsg.getMessage("cmn.main.temp.path")});
                        tmpErrMsg = new String[]{tmpErrText};
                    } catch (WmlMailSizeOverException e) {
                        //メールのサイズが上限を超えていた場合
                        tmpErrText = gsMsg.getMessageVal0("wml.245", e.getMaxSizeText());
                        tmpErrMsg = new String[]{tmpErrText};
                    } catch(WmlDiskSizeOverException e) {
                        //ディスクの容量制限を超えていた場合
                        tmpErrText = e.getErrorMsg();
                        tmpErrMsg = new String[]{tmpErrText};
                    } catch (WmlTempFileNameException e) {
                        //添付ファイル名が不正の場合
                        tmpErrText = e.getMessage();
                        tmpErrMsg = new String[]{tmpErrText};
                    }
                }

                if (errMessages != null && errMessages.length > 0) {
                    message = errMessages[0]; // 送信パラメータエラー
                } else if (tmpErrMsg != null && tmpErrMsg.length > 0) {
                    message = tmpErrMsg[0]; // 送信パラメータエラー
                }
            }

            if (message == null) {
                // メールチェックで問題ない場合、ドメイン情報を返す
                Wml012Biz wml012Biz = new Wml012Biz();
                Map<String, String> domainMap = new HashMap<String, String>();
                toAdrList  = wml012Biz.getAddressList(form.getSendAdrToStr(), domainMap);
                ccAdrList  = wml012Biz.getAddressList(form.getSendAdrCcStr(), domainMap);
                bccAdrList = wml012Biz.getAddressList(form.getSendAdrBccStr(), domainMap);
                commitFlg  = true;
            }
        } catch (ClassNotFoundException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (SQLException e) {
            log__.error("メッセージの送信に失敗", e);
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            throw e;
        } catch (IOException e) {
            log__.error("IOException", e);
            throw e;
        }

        log__.debug("createXml end");

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (commitFlg) {
            // チェックＯＫ → 確認画面の送信先情報を返す
            Element atesakiSet = new Element("atesakiSet");
            this.setWmlElement(atesakiSet, toAdrList,  0);
            this.setWmlElement(atesakiSet, ccAdrList,  1);
            this.setWmlElement(atesakiSet, bccAdrList, 2);
            result.addContent(atesakiSet);
        } else {
            // チェックＮＧ → エラーメッセージを返す
            if (message == null || message.length() == 0) {
                // エラーメッセージがないので、ここで指定
                message = gsMsg.getMessage("wml.136");
            }
            //result.addContent(_createElement("Message", message));
            ActionMessage msg = new ActionMessage("errors.free.msg", message);
            StrutsUtil.addMessage(errors, msg, "meailCheckError");
            addErrors(req, errors);
            return null;
        }
        return doc;
    }

    /**
     * <br>[機  能] webmailアカウント情報をXMLのresult属性にセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param result     エレメント
     * @param adrMdlList アドレス情報一覧
     * @param type       送信先タイプ(0:宛先 1:ＣＣ 2:ＢＣＣ)
     * @throws Exception 実行例外
     */
    protected void setWmlElement(Element result, List<Wml012AddressModel> adrMdlList, int type)
            throws Exception {

        if (adrMdlList != null && adrMdlList.size() > 0) {
            for (Wml012AddressModel mdl : adrMdlList) {
                Element adrElm = new Element("atesaki");

                String colorStr = colorArray__[0];
                if (mdl.getDomainType() != null && mdl.getDomainType().length() > 0) {
                    // ドメインタイプ(1～10)
                    int domainType = Integer.valueOf(mdl.getDomainType()).intValue() - 1;
                    if (domainType >= 0 && domainType < colorArray__.length) {
                        colorStr = colorArray__[domainType];
                    }
                }

                adrElm.addContent(_createElement("address", mdl.getAddress())); // アドレス
                adrElm.addContent(_createElement("sendKbn", type));             // 送信先区分
                adrElm.addContent(_createElement("user",    mdl.getUser()));    // ユーザ名
                adrElm.addContent(_createElement("domain",  mdl.getDomain()));  // ドメイン
                adrElm.addContent(_createElement("color",   colorStr));         // ドメイン色
                result.addContent(adrElm);
            }
        }
    }
}
