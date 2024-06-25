package jp.groupsession.v2.api.user.whoami;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] 自分の個人情報を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWhoamiAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWhoamiAction.class);

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

        //ユーザ個人情報を取得
        CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uinf = dao.select(umodel.getUsrsid());

        //ユーザ無効フラグ情報取得
        int ukoFlg = GSConst.YUKOMUKO_YUKO;
        CmnUsrmInfModel jkbninf = dao.selectUserNameAndJtkbn(umodel.getUsrsid());
        if (jkbninf != null) {
            ukoFlg = jkbninf.getUsrUkoFlg();
        }

        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //Result
        Element result = new Element("Result");
        resultSet.addContent(result);


        //Usid
        result.addContent(_createElement("Usid", uinf.getUsrSid()));
        //LoginId
        result.addContent(_createElement("LoginId", umodel.getLgid()));
        //NameSei
        result.addContent(_createElement("NameSei", uinf.getUsiSei()));
        //NameMei
        result.addContent(_createElement("NameMei", uinf.getUsiMei()));
        //NameSeiKn
        result.addContent(_createElement("NameSeiKn", uinf.getUsiSeiKn()));
        //NameMeiKn
        result.addContent(_createElement("NameMeiKn", uinf.getUsiMeiKn()));
        //無効フラグ
        result.addContent(_createElement("UsrUkoFlg", ukoFlg));
        //SyainNo
        result.addContent(_createElement("SyainNo", uinf.getUsiSyainNo()));
        //Syozoku
        result.addContent(_createElement("Syozoku", uinf.getUsiSyozoku()));
        //YakusyokuSid
        result.addContent(_createElement("YakusyokuSid", uinf.getPosSid()));
        //YakusyokuName
        //役職名称を取得
        String posName = "";
        if (uinf.getPosSid() > GSConst.POS_DEFAULT) {
            PosBiz pBiz = new PosBiz();
            posName = NullDefault.getString(pBiz.getPosName(con, uinf.getPosSid()), "");
        }
        result.addContent(_createElement("YakusyokuName", posName));
        //Birthday
        Element birthday = new Element("Birthday");
        birthday.addContent(ApiDataTypeUtil.getDate(uinf.getUsiBdate()));
        result.addContent(birthday);
        //BirthdayKf
        result.addContent(_createElement("BirthdayKf", uinf.getUsiBdateKf()));
        //Mail1
        result.addContent(_createElement("Mail1", uinf.getUsiMail1()));
        //Mail1Comment
        result.addContent(_createElement("Mail1Comment", uinf.getUsiMailCmt1()));
        //Mail1Kf
        result.addContent(_createElement("Mail1Kf", uinf.getUsiMail1Kf()));
        //Mail2
        result.addContent(_createElement("Mail2", uinf.getUsiMail2()));
        //Mail2Comment
        result.addContent(_createElement("Mail2Comment", uinf.getUsiMailCmt2()));
        //Mail2Kf
        result.addContent(_createElement("Mail2Kf", uinf.getUsiMail2Kf()));
        //Mail3
        result.addContent(_createElement("Mail3", uinf.getUsiMail3()));
        //Mail3Comment
        result.addContent(_createElement("Mail3Comment", uinf.getUsiMailCmt3()));
        //Mail3Kf
        result.addContent(_createElement("Mail3Kf", uinf.getUsiMail3Kf()));
        //Zip1
        result.addContent(_createElement("Zip1", uinf.getUsiZip1()));
        //Zip2
        result.addContent(_createElement("Zip2", uinf.getUsiZip2()));
        //ZipKf
        result.addContent(_createElement("ZipKf", uinf.getUsiZipKf()));
        //TodofukenSid
        result.addContent(_createElement("TodofukenSid", uinf.getTdfSid()));
        //TodofukenName
        int tdfkCode = uinf.getTdfSid();
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
        result.addContent(_createElement("TodofukenKf", uinf.getUsiTdfKf()));
        //Address1
        result.addContent(_createElement("Address1", uinf.getUsiAddr1()));
        //Address1Kf
        result.addContent(_createElement("Address1Kf", uinf.getUsiAddr1Kf()));
        //Address2
        result.addContent(_createElement("Address2", uinf.getUsiAddr2()));
        //Address2Kf
        result.addContent(_createElement("Address2Kf", uinf.getUsiAddr2Kf()));

        //Tel1
        result.addContent(_createElement("Tel1", uinf.getUsiTel1()));
        //Tel1Naisen
        result.addContent(_createElement("Tel1Naisen", uinf.getUsiTelNai1()));
        //Tel1Comment
        result.addContent(_createElement("Tel1Comment", uinf.getUsiTelCmt1()));
        //Tel1Kf
        result.addContent(_createElement("Tel1Kf", uinf.getUsiTel1Kf()));

        //Tel2
        result.addContent(_createElement("Tel2", uinf.getUsiTel2()));
        //Tel2Naisen
        result.addContent(_createElement("Tel2Naisen", uinf.getUsiTelNai2()));
        //Tel2Comment
        result.addContent(_createElement("Tel2Comment", uinf.getUsiTelCmt2()));
        //Tel2Kf
        result.addContent(_createElement("Tel2Kf", uinf.getUsiTel2Kf()));

        //Tel3
        result.addContent(_createElement("Tel3", uinf.getUsiTel3()));
        //Tel3Naisen
        result.addContent(_createElement("Tel3Naisen", uinf.getUsiTelNai3()));
        //Tel3Comment
        result.addContent(_createElement("Tel3Comment", uinf.getUsiTelCmt3()));
        //Tel3Kf
        result.addContent(_createElement("Tel3Kf", uinf.getUsiTel3Kf()));

        //Fax1
        result.addContent(_createElement("Fax1", uinf.getUsiFax1()));
        //Fax1Comment
        result.addContent(_createElement("Fax1Comment", uinf.getUsiFaxCmt1()));
        //Fax1Kf
        result.addContent(_createElement("Fax1Kf", uinf.getUsiFax1Kf()));

        //Fax2
        result.addContent(_createElement("Fax2", uinf.getUsiFax2()));
        //Fax2Comment
        result.addContent(_createElement("Fax2Comment", uinf.getUsiFaxCmt2()));
        //Fax2Kf
        result.addContent(_createElement("Fax2Kf", uinf.getUsiFax2Kf()));

        //Fax3
        result.addContent(_createElement("Fax3", uinf.getUsiFax3()));
        //Fax3Comment
        result.addContent(_createElement("Fax3Comment", uinf.getUsiFaxCmt3()));
        //Fax3Kf
        result.addContent(_createElement("Fax3Kf", uinf.getUsiFax3Kf()));

        //Bikou
        result.addContent(_createElement("Bikou", uinf.getUsiBiko()));
        //AddDate
        Element addDate = new Element("AddDateTime");
        addDate.addContent(ApiDataTypeUtil.getDateTime(uinf.getUsiAdate()));
        result.addContent(addDate);
        //EditDate
        Element editDate = new Element("EditDateTime");
        editDate.addContent(ApiDataTypeUtil.getDateTime(uinf.getUsiEdate()));
        result.addContent(editDate);

        return doc;
    }

}
