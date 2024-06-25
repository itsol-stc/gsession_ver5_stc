package jp.groupsession.v2.api.user.belong;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] 指定したグループに所属するユーザ一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiBelongAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiBelongAction.class);

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

        ApiBelongForm thisForm = (ApiBelongForm) form;

        //入力チェック
        ActionErrors errors = thisForm.validateUsrBelong(req);
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }

        int grpSid = NullDefault.getInt(thisForm.getGrpSid(), -1);

        log__.debug("createXml start");
        UserBiz usrBiz = new UserBiz();
        ArrayList<CmnUsrmInfModel> usrList = null;
        try {
            //グループ一覧を取得する。
            usrList = usrBiz.getBelongUserList(con, grpSid, null, umodel.getAdminFlg());
        } catch (SQLException e) {
            log__.error("グループ一覧の取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        //XMLデータ作成
        for (CmnUsrmInfModel usrInfMdl : usrList) {

            //Result
            Element result = new Element("Result");
            resultSet.addContent(result);

            //Usrsid ユーザSid
            result.addContent(_createElement("Usrsid", usrInfMdl.getUsrSid()));

            //Usisei ユーザ姓
            result.addContent(_createElement("Usisei", usrInfMdl.getUsiSei()));

            //Usimei ユーザ名
            result.addContent(_createElement("Usimei", usrInfMdl.getUsiMei()));

            //Usisei ユーザ姓カナ
            result.addContent(_createElement("Usiseikn", usrInfMdl.getUsiSeiKn()));

            //Usimeikn ユーザ名カナ
            result.addContent(_createElement("Usimeikn", usrInfMdl.getUsiMeiKn()));

            //UsrUkoFlg ユーザ無効フラグ
            result.addContent(_createElement("UsrUkoFlg", usrInfMdl.getUsrUkoFlg()));

            //SyainNo
            result.addContent(_createElement("SyainNo", usrInfMdl.getUsiSyainNo()));

            //Syozoku
            result.addContent(_createElement("Syozoku", usrInfMdl.getUsiSyozoku()));

            //YakusyokuSid
            result.addContent(_createElement("YakusyokuSid", usrInfMdl.getPosSid()));

            //YakusyokuName
            //役職名称を取得
            String posName = "";
            if (usrInfMdl.getPosSid() > GSConst.POS_DEFAULT) {
                PosBiz pBiz = new PosBiz();
                posName = NullDefault.getString(pBiz.getPosName(con, usrInfMdl.getPosSid()), "");
            }
            result.addContent(_createElement("YakusyokuName", posName));

            //Birthday
            Element birthday = new Element("Birthday");
            birthday.addContent(ApiDataTypeUtil.getDate(usrInfMdl.getUsiBdate()));
            result.addContent(birthday);

            //BirthdayKf
            Element birthdayKf = new Element("BirthdayKf");
            birthdayKf.addContent(Integer.toString(usrInfMdl.getUsiBdateKf()));
            result.addContent(birthdayKf);

            //Mail1
            result.addContent(_createElement("Mail1", usrInfMdl.getUsiMail1()));

            //Mail1Comment
            result.addContent(_createElement("Mail1Comment", usrInfMdl.getUsiMailCmt1()));

            //Mail1Kf
            result.addContent(_createElement("Mail1Kf", usrInfMdl.getUsiMail1Kf()));

            //Mail2
            result.addContent(_createElement("Mail2", usrInfMdl.getUsiMail2()));

            //Mail2Comment
            result.addContent(_createElement("Mail2Comment", usrInfMdl.getUsiMailCmt2()));

            //Mail2Kf
            result.addContent(_createElement("Mail2Kf", usrInfMdl.getUsiMail2Kf()));

            //Mail3
            result.addContent(_createElement("Mail3", usrInfMdl.getUsiMail3()));

            //Mail3Comment
            result.addContent(_createElement("Mail3Comment", usrInfMdl.getUsiMailCmt3()));

            //Mail3Kf
            result.addContent(_createElement("Mail3Kf", usrInfMdl.getUsiMail3Kf()));

            //Zip1
            result.addContent(_createElement("Zip1", usrInfMdl.getUsiZip1()));

            //Zip2
            result.addContent(_createElement("Zip2", usrInfMdl.getUsiZip2()));

            //ZipKf
            result.addContent(_createElement("ZipKf", usrInfMdl.getUsiZipKf()));

            //TodofukenSid
            result.addContent(_createElement("TodofukenSid", usrInfMdl.getTdfSid()));

            //TodofukenName
            int tdfkCode = usrInfMdl.getTdfSid();
            String tdfkName = "";
            if (tdfkCode > 0) {
                CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
                CmnTdfkModel param = new CmnTdfkModel();
                param.setTdfSid(tdfkCode);
                CmnTdfkModel ret = tdfkDao.select(param);
                if (ret != null) {
                    tdfkName = ret.getTdfName();
                }
            }
            result.addContent(_createElement("TodofukenName", tdfkName));

            //TodofukenKf
            result.addContent(_createElement("TodofukenKf", usrInfMdl.getUsiTdfKf()));

            //Address1
            result.addContent(_createElement("Address1", usrInfMdl.getUsiAddr1()));

            //Address1Kf
            result.addContent(_createElement("Address1Kf", usrInfMdl.getUsiAddr1Kf()));

            //Address2
            result.addContent(_createElement("Address2", usrInfMdl.getUsiAddr2()));

            //Address2Kf
            result.addContent(_createElement("Address2Kf", usrInfMdl.getUsiAddr2Kf()));

            //Tel1
            result.addContent(_createElement("Tel1", usrInfMdl.getUsiTel1()));

            //Tel1Naisen
            result.addContent(_createElement("Tel1Naisen", usrInfMdl.getUsiTelNai1()));

            //Tel1Comment
            result.addContent(_createElement("Tel1Comment", usrInfMdl.getUsiTelCmt1()));

            //Tel1Kf
            result.addContent(_createElement("Tel1Kf", usrInfMdl.getUsiTel1Kf()));

            //Tel2
            result.addContent(_createElement("Tel2", usrInfMdl.getUsiTel2()));

            //Tel2Naisen
            result.addContent(_createElement("Tel2Naisen", usrInfMdl.getUsiTelNai2()));

            //Tel2Comment
            result.addContent(_createElement("Tel2Comment", usrInfMdl.getUsiTelCmt2()));

            //Tel2Kf
            result.addContent(_createElement("Tel2Kf", usrInfMdl.getUsiTel2Kf()));

            //Tel3
            result.addContent(_createElement("Tel3", usrInfMdl.getUsiTel3()));

            //Tel3Naisen
            result.addContent(_createElement("Tel3Naisen", usrInfMdl.getUsiTelNai3()));

            //Tel3Comment
            result.addContent(_createElement("Tel3Comment", usrInfMdl.getUsiTelCmt3()));

            //Tel3Kf
            result.addContent(_createElement("Tel3Kf", usrInfMdl.getUsiTel3Kf()));

            //Fax1
            result.addContent(_createElement("Fax1", usrInfMdl.getUsiFax1()));

            //Fax1Comment
            result.addContent(_createElement("Fax1Comment", usrInfMdl.getUsiFaxCmt1()));

            //Fax1Kf
            result.addContent(_createElement("Fax1Kf", usrInfMdl.getUsiFax1Kf()));

            //Fax2
            result.addContent(_createElement("Fax2", usrInfMdl.getUsiFax2()));

            //Fax2Comment
            result.addContent(_createElement("Fax2Comment", usrInfMdl.getUsiFaxCmt2()));

            //Fax2Kf
            result.addContent(_createElement("Fax2Kf", usrInfMdl.getUsiFax2Kf()));

            //Fax3
            result.addContent(_createElement("Fax3", usrInfMdl.getUsiFax3()));

            //Fax3Comment
            result.addContent(_createElement("Fax3Comment", usrInfMdl.getUsiFaxCmt3()));

            //Fax3Kf
            result.addContent(_createElement("Fax3Kf", usrInfMdl.getUsiFax3Kf()));

            //Bikou
            result.addContent(_createElement("Bikou", usrInfMdl.getUsiBiko()));

            //AddDate
            Element addDate = new Element("AddDateTime");
            addDate.addContent(ApiDataTypeUtil.getDateTime(usrInfMdl.getUsiAdate()));
            result.addContent(addDate);
            //EditDate
            Element editDate = new Element("EditDateTime");
            editDate.addContent(ApiDataTypeUtil.getDateTime(usrInfMdl.getUsiEdate()));
            result.addContent(editDate);

        }

        log__.debug("createXml end");
        return doc;
    }
}
