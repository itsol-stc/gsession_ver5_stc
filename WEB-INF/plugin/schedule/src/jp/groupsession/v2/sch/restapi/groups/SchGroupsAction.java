package jp.groupsession.v2.sch.restapi.groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
@Plugin(GSConst.PLUGINID_SCH)
public class SchGroupsAction extends AbstractRestApiAction {
    @Get
    public void doGet(RestApiContext ctx, HttpServletResponse res) throws SQLException {
        //セッション情報を取得
        int sessionUsrSid = ctx.getRequestUserSid(); //セッションユーザSID
        RequestModel reqMdl = ctx.getRequestModel();
        Connection con = ctx.getCon();

        SchDao schDao = new SchDao(con);
        //閲覧不可のグループ、ユーザを設定
        List<Integer> nonAccessableList = schDao.getNotAccessGrpList(sessionUsrSid);
        //登録不可のグループ、ユーザを設定
        List<Integer> nonRegistableList = schDao.getNotRegistGrpList(sessionUsrSid);
//
        SchUserGroupSelectInitBiz selectInit = new SchUserGroupSelectInitBiz(reqMdl,
                con,
                null,
                null,
                null);
        selectInit.initGroup();
        List<SchLabelValueModel> groupLabel = selectInit.getBaseGrpLabelList();
        //グループSIDを振り分け
        Set<Integer> grpSids = new HashSet<>();
        //グループ名情報Map
        Map<Integer, CmnGroupmModel> groupMap = new HashMap<Integer, CmnGroupmModel>();
        for (SchLabelValueModel lbl: groupLabel) {
            if (lbl.getValue().startsWith(GSConstSchedule.MY_GROUP_STRING)) {
                continue;
            }
            if (lbl.getValue().startsWith(GSConstSchedule.DSP_LIST_STRING)) {
                continue;
            }
            grpSids.add(NullDefault.getInt(lbl.getValue(), -1));
        }
        if (grpSids.size() > 0) {
            GroupDao gDao = new GroupDao(con);
            List<CmnGroupmModel> gMdlList = gDao.getGroups(
                    grpSids.stream()
                        .mapToInt(sid -> sid)
                        .toArray()
                        );
            if (gMdlList != null) {
                for (CmnGroupmModel gMdl : gMdlList) {
                    groupMap.put(gMdl.getGrpSid(), gMdl);
                }
            }
        }
        if (grpSids.size() > 0) {
            GroupDao gDao = new GroupDao(con);
            List<CmnGroupmModel> gMdlList = gDao.getGroups(
                    grpSids.stream()
                        .mapToInt(sid -> sid)
                        .toArray()
                        );
            if (gMdlList != null) {
                for (CmnGroupmModel gMdl : gMdlList) {
                    groupMap.put(gMdl.getGrpSid(), gMdl);
                }
            }
        }


        List<SchGroupsResultModel> result = new ArrayList<>();
        for (SchLabelValueModel lbl : groupLabel) {
            SchGroupsResultModel ret = new SchGroupsResultModel();

            result.add(ret);
            if (Objects.equals(selectInit.getGrpSidStr(), lbl.getValue())) {
                ret.setDefaultGrpFlg(1);
            }

            if (lbl.getValue().startsWith(GSConstSchedule.MY_GROUP_STRING)) {
                ret.setGroupType(EnumGroupType.MYGROUP.getValue());
                ret.setGroupSid(NullDefault.getInt(
                        lbl.getValue().substring(
                                GSConstSchedule.MY_GROUP_STRING.length()
                                ),
                        -1));
                ret.setGroupName(lbl.getLabel());
                //登録・閲覧可
                ret.setPermissionType(1);

                continue;
            }
            if (lbl.getValue().startsWith(GSConstSchedule.DSP_LIST_STRING)) {
                ret.setGroupType(EnumGroupType.SCHEDULELIST.getValue());
                ret.setGroupSid(NullDefault.getInt(
                        lbl.getValue().substring(
                                GSConstSchedule.DSP_LIST_STRING.length()
                                ),
                        -1));
                ret.setGroupName(lbl.getLabel());
                //登録・閲覧可
                ret.setPermissionType(1);
                continue;
            }
            CmnGroupmModel gmdl = groupMap.get(NullDefault.getInt(lbl.getValue(), -1));
            if (gmdl != null) {
                ret.setGroupType(EnumGroupType.GROUP.getValue());
                ret.setGroupId(gmdl.getGrpId());
                ret.setGroupName(gmdl.getGrpName());
                ret.setGroupSid(gmdl.getGrpSid());
                ret.setClassLevelNum(
                        lbl.getLabel()
                            .indexOf(gmdl.getGrpName()));
                //Access アクセス可否
                if (nonAccessableList.contains(gmdl.getGrpSid())) {
                    //登録・閲覧不可
                    ret.setPermissionType(0);
                } else if (nonRegistableList.contains(gmdl.getGrpSid())) {
                    //閲覧のみ可
                    ret.setPermissionType(2);
                } else {
                    //登録・閲覧可
                    ret.setPermissionType(1);
                }
            }
        }
        RestApiResponseWriter.builder(res, ctx)
        .addResultList(result)
        .build().execute();

    }
}
