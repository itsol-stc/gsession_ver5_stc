package jp.groupsession.v2.api.user.belongname;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 *
 * <br>[機  能] グループ所属ユーザ名一覧取得WEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiBelongUserNameAction extends AbstractApiAction {

    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        ApiBelongUserNameForm thisForm = (ApiBelongUserNameForm) form;

        //セッション情報を取得
        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //個人設定よりデフォルト表示グループSIDを取得する。
        SchCommonBiz scBiz = new SchCommonBiz(getRequestModel(req));
        //グループSID
        int dspGpSid = 0;
        boolean myGroupFlg = false;
        //表示グループ
        String dspGpSidStr = NullDefault.getString(thisForm.getGrpSid(),
                scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid));

        if (SchCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = SchCommonBiz.getDspGroupSid(dspGpSidStr);
            myGroupFlg = true;
        } else {
            if (GSValidateUtil.isNumber(dspGpSidStr)) {
                dspGpSid = NullDefault.getInt(dspGpSidStr, dspGpSid);
            } else {
                //不正なgrpSidの場合は０件
                resultSet.setAttribute("Count", "0");
                return doc;
            }
        }

        //除外するユーザSIDを設定
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(new Integer(GSConstUser.SID_ADMIN));
        usrSids.add(new Integer(GSConstUser.SID_SYSTEM_MAIL));

        List<CmnUsrmInfModel> userList = scBiz.getBelongUserList(con,
                dspGpSid,
                usrSids,
                sessionUsrSid,
                myGroupFlg);
        //XMLデータ作成
        if (userList != null) {
            resultSet.setAttribute("Count", String.valueOf(userList.size()));
            for (CmnUsrmInfModel usrInfMdl : userList) {

                //Result
                Element result = new Element("Result");
                resultSet.addContent(result);

                //Usrsid ユーザSid
                result.addContent(_createElement("UsrSid", usrInfMdl.getUsrSid()));

                //UsrName ユーザ名
                result.addContent(_createElement("UsrName",
                        usrInfMdl.getUsiSei() + " "
                            + usrInfMdl.getUsiMei()));

                //UsrUkoFlg ユーザ無効フラグ
                result.addContent(_createElement("UsrUkoFlg", usrInfMdl.getUsrUkoFlg()));
            }
        }
        return doc;
    }


}
