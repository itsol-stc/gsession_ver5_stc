package jp.groupsession.v2.api.schedule.user.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBAPI スケジュール用ユーザ一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchSharedUserAction extends AbstractApiSchAction {
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


        RequestModel reqMdl = getRequestModel(req);

        ApiSchSharedUserForm thisForm = (ApiSchSharedUserForm) form;

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl);
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
        List<UsrLabelValueBean> userList = __getUserLabelList(con, dspGpSid
                , sessionUsrSid, myGroupFlg, reqMdl);

        //登録不可のグループ、ユーザを設定
        SchDao schDao = new SchDao(con);
        List<Integer> nonRegistableList = schDao.getNotRegistUserList(sessionUsrSid);

        //XMLデータ作成
        if (userList != null) {
            resultSet.setAttribute("Count", String.valueOf(userList.size()));
            for (UsrLabelValueBean label : userList) {

                //Result
                Element result = new Element("Result");
                resultSet.addContent(result);

                //Usrsid ユーザSid
                result.addContent(_createElement("UsrSid", label.getValue()));

                //UsrName ユーザ名
                result.addContent(_createElement("UsrName",
                        label.getLabel()));

                //UsrUkoFlg ユーザ無効フラグ
                result.addContent(_createElement("UsrUkoFlg", label.getUsrUkoFlg()));

                //Access アクセス可否
                if (nonRegistableList.contains(Integer.parseInt(label.getValue()))) {
                    //閲覧のみ可
                    result.addContent(_createElement("Access", 2));
                } else {
                    //登録・閲覧可
                    result.addContent(_createElement("Access", 1));
                }

            }
        }
        return doc;

    }
    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSid セッションユーザSID
     * @param myGroupFlg マイグループ選択
     * @param reqMdl リクエストモデル
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private List<UsrLabelValueBean> __getUserLabelList(Connection con,
            int groupSid
            , int userSid
            , boolean myGroupFlg
            , RequestModel reqMdl) throws SQLException {
        //指定無し
        GsMessage gsMsg = new GsMessage(reqMdl);
        List <UsrLabelValueBean> labelList = null;
        UserBiz usrBiz = new UserBiz();
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUserList = null;
        if (myGroupFlg) {
            labelList = usrBiz.getMyGroupUserLabelList(con, userSid, groupSid, null);
            notAccessUserList = schDao.getNotAccessUserList(userSid);
        } else {
            labelList = usrBiz.getNormalUserLabelList(con, groupSid, null, false, gsMsg);
            notAccessUserList = schDao.getNotAccessUserList(groupSid, userSid);
        }

        //閲覧を許可されていないユーザを除外する
        ArrayList<UsrLabelValueBean> labelList2 = new ArrayList<UsrLabelValueBean>();
        for (UsrLabelValueBean label : labelList) {
            if (notAccessUserList.indexOf(Integer.parseInt(label.getValue())) < 0) {
                labelList2.add(label);
            }
        }
        labelList.clear();
        labelList.addAll(labelList2);

        return labelList;
    }

}
