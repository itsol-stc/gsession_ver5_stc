package jp.groupsession.v2.api.cmn.tmpfile;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;

/**
 *
 * <br>[機  能] 添付ファイルサイズ制限取得API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCmnTmpFileLimitInfoAction extends AbstractApiAction {
    /** ログ */
    private static Log log__ = LogFactory.getLog(
            new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        CmnFileConfDao cfcDao = new CmnFileConfDao(con);
        //添付ファイル最大容量取得

        CmnFileConfModel cfcMdl = cfcDao.select();


        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);

        //FileSize 1ファイルの制限サイズ（単位：MB）
        Element fileSize = new Element("FileSize");
        fileSize.addContent(Integer.toString(cfcMdl.getFicMaxSize()));
        result.addContent(fileSize);


        log__.debug("createXml end");
        return doc;
    }
    @Override
    public boolean isWriteApiLog() {
        return true;
    }
}
