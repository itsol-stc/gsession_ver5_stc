package jp.groupsession.v2.api.ntp.nippou.goodjob.user;

import java.sql.Connection;
import java.util.ArrayList;
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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
import jp.groupsession.v2.ntp.model.NtpGoodModel;
/**
 * <br>[機  能] WEBAPI 日報いいね！ユーザ一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouGaveGoodUserAction extends AbstractApiAction {

    /**ロガークラス*/
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        ApiNippouGaveGoodUserForm thisForm = (ApiNippouGaveGoodUserForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        int nipSid = NullDefault.getInt(thisForm.getNipSid(), -1);

        //セッション情報を取得
        BaseUserModel usModel = umodel;

        int sessionUsrSid = usModel.getUsrsid();

        List<CmnUsrmInfModel> goodUsrList = new ArrayList<CmnUsrmInfModel>();
        NtpGoodDao gDao = new NtpGoodDao(con);
        List<NtpGoodModel> ngList = gDao.select(nipSid);
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        resultSet.setAttribute("TotalCount", Integer.toString(0));

        if (!ngList.isEmpty()) {
            ArrayList<String> usidList = new ArrayList<String>();
            for (NtpGoodModel ngMdl : ngList) {
                usidList.add(String.valueOf(ngMdl.getUsrSid()));
            }
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
            goodUsrList =
                cuiDao.getUsersInfList((String[]) usidList.toArray(new String[usidList.size()]));

            if (!goodUsrList.isEmpty()) {


                resultSet.setAttribute("TotalCount", Integer.toString(goodUsrList.size()));

                for (int i = 0; i < goodUsrList.size(); i++) {
                    CmnUsrmInfModel userModel = goodUsrList.get(i);

                    Element result = new Element("Result");
                    resultSet.addContent(result);

                    result.addContent(_createElement("UsrSid", userModel.getUsrSid()));
                    result.addContent(_createElement("UsrName",
                            (userModel.getUsiSei() + " " + userModel.getUsiMei())));
                    result.addContent(_createElement("UsrUkoFlg", userModel.getUsrUkoFlg()));
                    if (userModel.getBinSid() == 0) {
                        //写真なし
                        result.addContent(_createElement("UsrImgPath", ""));
                    } else {
                        if (userModel.getUsiPictKf() == 0) {
                            //写真あり 公開
                            result.addContent(_createElement("UsrImgPath",
                                    "./common/cmn100.do?CMD=getImageFile&cmn100binSid="
                                            + userModel.getBinSid()));

                        } else {
                            //写真あり 非公開
                            result.addContent(_createElement("UsrImgPath", "secret"));
                        }
                    }

                    if (userModel.getUsrSid() == sessionUsrSid) {
                        result.addContent(_createElement("GoodDelFlg", 1));
                    } else {
                        result.addContent(_createElement("GoodDelFlg", 0));
                    }
                }
            }
        }



        return doc;
    }

}
