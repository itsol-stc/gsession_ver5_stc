package jp.groupsession.v2.api.user.image;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
*
* <br>[機  能] WEBAPI ユーザ画像取得
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class ApiUserImageDownloadAction extends AbstractApiAction {
    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiUserImageDownloadAction.class);

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        GsMessage gsMsg = new GsMessage(req);
        ApiUserImageDownloadForm form = (ApiUserImageDownloadForm) aForm;
        ActionErrors errors = form.validateUsrInf(gsMsg);
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }

        int usid = NullDefault.getInt(form.getUsrSid(), -1);
        CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usrInfMdl = null;
        try {
            //ユーザ情報を取得する。
            usrInfMdl = usrInfDao.select(usid);
        } catch (SQLException e) {
            log__.error("ユーザ情報の取得に失敗", e);
        }
        boolean okFlg = false;

        if (usrInfMdl.getBinSid() == 0) {
            //写真なし

            //ファイルのダウンロード
            File file = new File(getAppRootPath() + "/common/images/original/photo.png");
            TempFileUtil.downloadAtachment(
                    req, res, file, "photo.png", Encoding.UTF_8);

            okFlg = true;


        } else {
            if (usrInfMdl.getUsiPictKf() == 0 || usrInfMdl.getUsrSid() == umodel.getUsrsid()) {
                //写真あり 公開

                CommonBiz cmnBiz = new CommonBiz();
                Long binSid = usrInfMdl.getBinSid();

                try {

                    //DBよりファイル情報を取得する。
                    CmnBinfModel binModel = cmnBiz.getBinInfo(con, binSid,
                            GroupSession.getResourceManager().getDomain(req));
                    if (binModel == null
                            || binModel.getBinJkbn() == GSConst.JTKBN_DELETE) {
                        //ファイルが存在しないか、削除されているか場合
                        ActionMessage msg = new ActionMessage("error.input.notfound.file");
                        StrutsUtil.addMessage(errors, msg, "binSid");
                        addErrors(req, errors);
                        return null;
                    }

                    //時間のかかる処理の前にコネクションを破棄
                    JDBCUtil.closeConnectionAndNull(con);

                    //ファイルのダウンロード
                    TempFileUtil.downloadAtachment(
                            req, res, binModel, getAppRootPath(), Encoding.UTF_8);

                    binModel = null;
                    okFlg = true;
                } catch (SQLException e) {
                    log__.error("SQL実行時エラー", e);
                } catch (IOToolsException e) {
                    log__.error("ファイルのダウンロードに失敗", e);
                } finally {
                }

            } else {
                //写真あり 非公開
                //ファイルのダウンロード
                File file = new File(getAppRootPath() + "/common/images/original/photo_hikokai.gif");
                TempFileUtil.downloadAtachment(
                        req, res, file, "photo_hikoukai.gif", Encoding.UTF_8);

                okFlg = true;
            }
            //ルートエレメントResultSet
            Element result = new Element("Result");
            Document doc = new Document(result);
            if (okFlg) {
                result.addContent("OK");
            } else {
                result.addContent("NG");
            }

            log__.debug("createXml end");
            return doc;

        }



        return null;
    }

}
