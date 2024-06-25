package jp.groupsession.v2.api.user.groupl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] グループ一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiGroupLAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiGroupLAction.class);

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
        GroupBiz grpBiz = new GroupBiz();
        ArrayList<GroupModel> grpList = null;
        List<CmnMyGroupModel> cmgList = null;

        ApiGroupLForm aform = (ApiGroupLForm) form;

        boolean isMyGroup     = (aform.getMygroup()  > 0);
        boolean isDefGroup    = (aform.getDefgroup() > 0);
        int     sessionUsrSid = getSessionUserSid(req);
        String  dspGpSid      = null;

        try {
            //グループ一覧を取得する。
            grpList = grpBiz.getGroupList(con);

            if (isMyGroup && sessionUsrSid >= 0) {
                //ユーザSIDからマイグループ情報を取得する
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                cmgList = cmgDao.getMyGroupList(sessionUsrSid);
            }
            if (isDefGroup && sessionUsrSid >= 0) {
                //デフォルト表示グループSIDを取得する。
                GroupBiz gbiz = new GroupBiz();
                int defGroupSid = gbiz.getDefaultGroupSid(sessionUsrSid, con);
                if (defGroupSid >= 0) {
                    dspGpSid = String.valueOf(defGroupSid);
                }
            }
        } catch (SQLException e) {
            log__.error("グループ一覧の取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        int    index  = 0;
        String defIdx = null;
        if (cmgList != null) {
            for (CmnMyGroupModel cmgMdl : cmgList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                //Grpsid グループSID
                String groupSid = "M" + String.valueOf(cmgMdl.getMgpSid());
                result.addContent(_createElement("Grpsid", groupSid));

                //GrpId グループID
                result.addContent(_createElement("Grpid", ""));

                //GrpName グループ名
                result.addContent(_createElement("Grpname", cmgMdl.getMgpName()));

                //ClassLevel 階層レベル
                result.addContent(_createElement("ClassLevel", 1));

                //Grpkbn 所属グループ区分
                result.addContent(_createElement("Grpkbn", 0));

                index++;
            }
        }

        if (grpList != null) {
            //XMLデータ作成
            for (GroupModel grpMdl : grpList) {

                Element result = new Element("Result");
                resultSet.addContent(result);

                //Grpsid グループSID
                result.addContent(_createElement("Grpsid", grpMdl.getGroupSid()));

                //GrpId グループID
                result.addContent(_createElement("Grpid", grpMdl.getGroupId()));

                //GrpName グループ名
                result.addContent(_createElement("Grpname", grpMdl.getGroupName()));

                //ClassLevel 階層レベル
                result.addContent(_createElement("ClassLevel", grpMdl.getClassLevel()));

                //Grpkbn 所属グループ区分
                result.addContent(_createElement("Grpkbn", grpMdl.getGrpKbn()));

                String groupSid = String.valueOf(grpMdl.getGroupSid());
                if (dspGpSid != null && dspGpSid.equals(groupSid)) {
                    defIdx =  String.valueOf(index);
                }

                index++;
            }
        }
        if (defIdx != null) {
            resultSet.setAttribute("DefaultIndex", defIdx);
        }

        log__.debug("createXml end");
        return doc;
    }
}
