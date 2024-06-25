package jp.groupsession.v2.api.file.kihoninf;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.model.FileAconfModel;

/**
 * <br>[機  能] ファイル管理 基本設定を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiKihonInfAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiKihonInfAction.class);

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
        RequestModel reqMdl = getRequestModel(req);
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        FileAconfModel model = null;
        try {
            //管理者設定 基本設定を取得
            model = fileBiz.getFileAconfModel();

        } catch (SQLException e) {
            log__.error("ファイル管理 基本設定の取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);

        //FileSize 1ファイルの制限サイズ（単位：MB）
        Element fileSize = new Element("FileSize");
        fileSize.addContent(Integer.toString(model.getFacFileSize()));
        result.addContent(fileSize);

        //LockKbn ロック区分
        Element lockKbn = new Element("LockKbn");
        lockKbn.addContent(Integer.toString(model.getFacLockKbn()));
        result.addContent(lockKbn);

        log__.debug("createXml end");
        return doc;
    }
    @Override
    public boolean isWriteApiLog() {
        return true;
    }
}
