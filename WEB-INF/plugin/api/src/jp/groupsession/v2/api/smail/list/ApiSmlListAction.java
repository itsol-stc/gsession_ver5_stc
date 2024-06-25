package jp.groupsession.v2.api.smail.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] 受信ショートメールリストを取得するWEBAPIアクション
 * <br>[解  説] 最新の20件を取得する。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlListAction.class);

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
        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }
        ApiSmlListBiz biz = new ApiSmlListBiz();
        ApiSmlListForm thisForm = (ApiSmlListForm) form;
        ArrayList <SmailModel> smailList = null;
        SmlCommonBiz smlbiz = new SmlCommonBiz(getRequestModel(req));

        GsMessage gsMsg = new GsMessage(req);

        //入力チェック
        ActionErrors errors = thisForm.validateSmlList(gsMsg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        int mode = NullDefault.getInt(thisForm.getMode(), 0);
        try {

            //受信ショートメールリスト取得
            smailList = biz.getSmailJusinList(
               con, smlbiz.getDefaultAccount(con, umodel.getUsrsid()), mode);

        } catch (SQLException e) {
            log__.error("ショートメールリストの取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        SmlBinDao binDao = new SmlBinDao(con);
        ArrayList<CmnBinfModel> retBin = null;

        //XMLデータ作成
        for (SmailModel smlModel : smailList) {

            Element result = new Element("Result");
            resultSet.addContent(result);

            //smjSid ショートメールSID
            result.addContent(_createElement("smjSid", smlModel.getSmlSid()));

            //title 件名
            result.addContent(_createElement("title", smlModel.getSmsTitle()));

            //usrSid 差出人 姓
            result.addContent(_createElement("usrSei", smlModel.getUsiSei()));

            //usrSid 差出人 名
            result.addContent(_createElement("usrMei", smlModel.getUsiMei()));

            //usrSidJkbn 差出人 状態区分
            result.addContent(_createElement("usrJkbn", smlModel.getUsrJkbn()));

            //usrUkoFlg 差出人 ユーザ無効フラグ
            result.addContent(_createElement("usrUkoFlg", smlModel.getUsrUkoFlg()));

            //日時yyyy/MM/dd hh:mm:ss形式に変換
            String strSdate = null;
            if (smlModel.getSmsSdate() != null) {
                strSdate =
                    UDateUtil.getSlashYYMD(smlModel.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(smlModel.getSmsSdate());
            }
            //date 日時
            Element date = new Element("date");
            date.addContent(strSdate);
            result.addContent(date);

            //mark マーク
            result.addContent(_createElement("mark", smlModel.getSmsMark()));

            //body 本文
            result.addContent(_createElement("body", smlModel.getSmsBody()));

            //binSid バイナリSID
            if (smlModel.getBinCnt() > 0) {
                //添付ファイル情報を取得する。
                retBin = binDao.getFileList(smlModel.getSmlSid());
                if (retBin != null || retBin.size() > 0) {
                    for (CmnBinfModel binModel : retBin) {
                        result.addContent(_createElement("binSid", binModel.getBinSid()));
                    }
                }

            }

        }


        log__.debug("createXml end");
        return doc;
    }

}
