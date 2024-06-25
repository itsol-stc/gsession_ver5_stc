package jp.groupsession.v2.api.ntp.sharedgroup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
/**
 * <br>[機  能] WEBAPI 日報用グループ一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpGroupLAction extends AbstractApiAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID

        NtpCommonBiz scBiz = new NtpCommonBiz(con, getRequestModel(req));

        //管理者設定
        NtpAdmConfModel admConf = scBiz.getAdmConfModel(con);

        ArrayList<GroupModel> gpList = new ArrayList<GroupModel>();
        List<CmnMyGroupModel> cmgList = new ArrayList<CmnMyGroupModel>();
        try {
            //グループ一覧を取得する。
            //グループリスト取得
            if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_ALL) {
                log__.debug("全員で共有するグループリストを取得");
                //全員で共有
                GroupBiz groupBiz = new GroupBiz();
                gpList = groupBiz.getGroupList(con);
            } else {
                //所属グループのみで共有
                log__.debug("所属グループのみで共有するグループリストを取得");
                UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
                gpList = gpDao.selectGroupNmListOrderbyClass(sessionUsrSid);
            }
            //共有範囲設定が「全て共有」の場合マイグループを追加
            if (admConf.getNacCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
                //ユーザSIDからマイグループ情報を取得する
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
               cmgList = cmgDao.getMyGroupList(sessionUsrSid);

            }
        } catch (SQLException e) {
            log__.error("グループ一覧の取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //XMLデータ作成
        for (GroupModel grpMdl : gpList) {

            Element result = new Element("Result");
            resultSet.addContent(result);

            //Grpsid グループSID
            result.addContent(_createElement("Grpsid", grpMdl.getGroupSid()));

            //GrpId グループID
            result.addContent(_createElement("Grpid", grpMdl.getGroupId()));

            //GrpName グループ名
            result.addContent(_createElement("Grpname", grpMdl.getGroupName().trim()));

            //ClassLevel 階層レベル
            result.addContent(_createElement("ClassLevel", grpMdl.getClassLevel()));

            //Grpkbn 所属グループ区分
            result.addContent(_createElement("Grpkbn", grpMdl.getGrpKbn()));

        }
        for (CmnMyGroupModel grpMdl :cmgList) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            //Grpsid グループSID
            result.addContent(_createElement("Grpsid"
                    , (GSConstNippou.MY_GROUP_STRING + grpMdl.getMgpSid())));

            //GrpId グループID
            result.addContent(_createElement("Grpid", grpMdl.getMgpSid()));

            //GrpName グループ名
            result.addContent(_createElement("Grpname", grpMdl.getMgpName()));

            //ClassLevel 階層レベル
            result.addContent(_createElement("ClassLevel", 1));

            //Grpkbn 所属グループ区分
            result.addContent(_createElement("Grpkbn", 0));

        }

        log__.debug("createXml end");
        return doc;
    }

}
