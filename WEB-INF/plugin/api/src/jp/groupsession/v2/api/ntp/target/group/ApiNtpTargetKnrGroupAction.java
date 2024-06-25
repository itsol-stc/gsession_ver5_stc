package jp.groupsession.v2.api.ntp.target.group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] WEBAPI 日報目標管理グループ取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpTargetKnrGroupAction extends AbstractApiAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
//        ApiNtpTargetKnrGroupForm thisForm = (ApiNtpTargetKnrGroupForm)form;

        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        List<NtpLabelValueModel> gpList = new ArrayList<NtpLabelValueModel>();
        int grpSid = -1;
        try {
            gpList = __getKnrGroup(req, con, (ApiNtpTargetKnrGroupForm) form, umodel);
            GroupBiz groupBiz = new GroupBiz();
            grpSid = groupBiz.getDefaultGroupSid(umodel.getUsrsid(), con);

        } catch (SQLException e) {
            log__.error("グループ一覧の取得に失敗", e);
        }

        //XMLデータ作成
        for (NtpLabelValueModel label : gpList) {

            Element result = new Element("Result");
            resultSet.addContent(result);

            //Grpsid グループSID
            result.addContent(_createElement("Grpsid", label.getValue()));

            //GrpName グループ名
            result.addContent(_createElement("Grpname", label.getLabel().trim()));
            if (grpSid == Integer.valueOf(label.getValue())) {
                resultSet.setAttribute("defaultGroupSid", label.getValue());
                resultSet.setAttribute("defaultGroupName", label.getLabel());
            }
        }

        return doc;
    }
    /**
     * <br>[機  能] 目標管理グループ取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @param form form
     * @param umodel 接続ユーザモデル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    private List<NtpLabelValueModel> __getKnrGroup(HttpServletRequest req,
            Connection con,
            ApiNtpTargetKnrGroupForm form,
            BaseUserModel umodel
            ) throws SQLException {
        GroupBiz grpBiz = new GroupBiz();
        CommonBiz cmnBiz = new CommonBiz();
        //管理者判定
        if (cmnBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            //すべてのグループを取得
            //管理者設定
            NtpCommonBiz scBiz = new NtpCommonBiz(con, getRequestModel(req));

            //管理者設定
            NtpAdmConfModel admConf = scBiz.getAdmConfModel(con);

            ArrayList<NtpLabelValueModel> labelList = new ArrayList<NtpLabelValueModel>();

            //グループリスト取得
            ArrayList<GroupModel> gpList = null;
            if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_ALL) {
                log__.debug("全員で共有するグループリストを取得");
                //全員で共有
//                GroupDao dao = new GroupDao(con);
//                gpList = dao.getGroupTree();
                GroupBiz groupBiz = new GroupBiz();
                gpList = groupBiz.getGroupList(con);
            } else {
                //所属グループのみで共有
                log__.debug("所属グループのみで共有するグループリストを取得");
                UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
                gpList = gpDao.selectGroupNmListOrderbyClass(umodel.getUsrsid());
            }

//            CmnGroupmModel gpMdl = null;
            for (GroupModel gmodel : gpList) {
                labelList.add(new NtpLabelValueModel(gmodel.getGroupName(), String
                        .valueOf(gmodel.getGroupSid()), "0"));
            }

            return labelList;

        } else {
            GsMessage gsMsg = new GsMessage(req);
            //自分が管理者になっているグループを取得
            List<LabelValueBean> grpLabel = null;
            grpLabel = grpBiz.getAdminGroupLabelList(umodel.getUsrsid(), con, false, gsMsg);
            List<NtpLabelValueModel> ntpLabel = new ArrayList<NtpLabelValueModel>();
            if (!grpLabel.isEmpty()) {
                NtpLabelValueModel labelMdl = null;
                for (LabelValueBean bean : grpLabel) {
                    labelMdl = new NtpLabelValueModel(bean.getLabel(), bean.getValue(), null);
                    ntpLabel.add(labelMdl);
                }
            }
            return ntpLabel;
        }

    }
}
