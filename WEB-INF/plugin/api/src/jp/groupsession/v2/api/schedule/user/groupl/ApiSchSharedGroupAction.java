package jp.groupsession.v2.api.schedule.user.groupl;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchLabelValueModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * <br>[機  能] スケジュール共有グループ取得APIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchSharedGroupAction extends AbstractApiSchAction {

    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //セッション情報を取得
        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID

        //個人設定よりデフォルト表示グループSIDを取得する。
        //データが存在しない場合、グループが削除されていた場合はデフォルト所属グループを返す
        SchCommonBiz scBiz = new SchCommonBiz(getRequestModel(req));
        //デフォルト表示グループ
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);

        //グループ一覧取得
        List<SchLabelValueModel> groupLabel = scBiz.getGroupLabelForSchedule(
                sessionUsrSid, con, false);

        SchDao schDao = new SchDao(con);
        //閲覧不可のグループ、ユーザを設定
        List<Integer> nonAccessableList = schDao.getNotAccessGrpList(sessionUsrSid);
        //登録不可のグループ、ユーザを設定
        List<Integer> nonRegistableList = schDao.getNotRegistGrpList(sessionUsrSid);


        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        resultSet.setAttribute("DefaultIndex", String.valueOf(0));

        if (groupLabel != null) {
            int index = 0;
            for (SchLabelValueModel schLabelValueModel : groupLabel) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                //Grpsid グループSID
                result.addContent(_createElement("GrpSid", schLabelValueModel.getValue()));

                //GrpName グループ名
                result.addContent(_createElement("GrpName", schLabelValueModel.getLabel()));

                int gsid = NullDefault.getInt(schLabelValueModel.getValue(), -1);
                if (dfGpSidStr.equals(schLabelValueModel.getValue())) {
                    resultSet.setAttribute("DefaultIndex", String.valueOf(index));
                }

                //Access アクセス可否
                if (nonAccessableList.contains(gsid)) {
                    //登録・閲覧不可
                    result.addContent(_createElement("Access", 0));
                } else if (nonRegistableList.contains(gsid)) {
                    //閲覧のみ可
                    result.addContent(_createElement("Access", 2));
                } else {
                    //登録・閲覧可
                    result.addContent(_createElement("Access", 1));
                }

                index++;
            }
            resultSet.setAttribute("Count", String.valueOf(groupLabel.size()));
        }
        return doc;
    }

}
