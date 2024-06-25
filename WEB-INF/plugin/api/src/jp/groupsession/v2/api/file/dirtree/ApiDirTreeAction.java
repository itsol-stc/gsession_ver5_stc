package jp.groupsession.v2.api.file.dirtree;

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
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] ディレクトリツリーを取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiDirTreeAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiDirTreeAction.class);

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

        ApiDirTreeForm thisForm = (ApiDirTreeForm) form;
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        int fcbSid = NullDefault.getInt(thisForm.getFcbSid(), -1);
        RequestModel reqMdl = getRequestModel(req);

        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        //入力チェック
        ActionErrors errors = thisForm.validateDirTree(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        //特権ユーザ判定
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        boolean superUser = cmnBiz.isEditCabinetUser(fcbSid);

        List <FileDirectoryModel> dirList = null;
        try {
            //ディレクトリ情報を取得する。
            dirList = dirDao.getDirectoryList(fcbSid);

        } catch (SQLException e) {
            log__.error("ディレクトリツリーの取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //XMLデータ作成
        int level = 0;
        String key = "";
        String key2 = "";
        HashMap<String, Element> map = new HashMap<String, Element>();
        //閲覧権限の無いディレクトリ格納用
        List<String> invDirKey = new ArrayList<String>();
        for (FileDirectoryModel dirMdl : dirList) {

            level = dirMdl.getFdrLevel();
            key = level + "_" + dirMdl.getFdrSid();
            if (level == 0) {
                //ルートディレクトリ
                Element dir = new Element("Dir");
                __setDirElement(dir, dirMdl);
                map.put(key, dir);
                resultSet.addContent(dir);
                continue;
            }

            //親ディレクトリのエレメントを取得する。
            key2 = (level - 1) + "_" + dirMdl.getFdrParentSid();
            //非表示経路のファイルか確認
            if (invDirKey.contains(key2)) {
                invDirKey.add(key);
                continue;
            }
            Element parentDir = map.get(key2);
            if (parentDir != null) {
                Element dir = new Element("Dir");

                FileDAccessConfDao dao = new FileDAccessConfDao(con);
                if (!superUser && !dao.isAccessUser(dirMdl.getFdrSid(),
                        umodel.getUsrsid(),
                        -1)) {
                    invDirKey.add(key);
                    continue;
                }

                __setDirElement(dir, dirMdl);
                map.put(key, dir);
                parentDir.addContent(dir);
            }

        }

        log__.debug("createXml end");
        return doc;
    }

    /**
     * <br>[機  能] ディレクトリ情報をXMLのDIR属性にセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param dir エレメント
     * @param dirMdl FileDirectoryModel
     * @throws Exception 実行例外
     */
    private void __setDirElement(Element dir, FileDirectoryModel dirMdl)
            throws Exception {

        //FdrName ディレクトリ名
        dir.addContent(_createElement("FdrName", dirMdl.getFdrName()));

        //FdrSid ディレクトリSID
        dir.addContent(_createElement("FdrSid", dirMdl.getFdrSid()));

        //FdrParentSid 親ディレクトリSID
        dir.addContent(_createElement("FdrParentSid", dirMdl.getFdrParentSid()));

        //FdrLevel 階層レベル
        dir.addContent(_createElement("FdrLevel", dirMdl.getFdrLevel()));

    }
}
