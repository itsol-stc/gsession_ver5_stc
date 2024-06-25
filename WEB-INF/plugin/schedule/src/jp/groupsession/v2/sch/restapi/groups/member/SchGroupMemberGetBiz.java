package jp.groupsession.v2.sch.restapi.groups.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.sch.biz.ICreateSchLabelListListner;
import jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.restapi.groups.EnumGroupType;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

public class SchGroupMemberGetBiz implements ICreateSchLabelListListner {
    /** パラメータ*/
    private final SchGroupMemberGetParamModel param__;
    /** コンテキスト*/
    private final RestApiContext ctx__;

    /** データキャッシュ */
    private final SchGroupMemberGetExeCash cash__ = SchGroupMemberGetExeCash.getInstance();

    /** 実行結果*/
    private final List<SchGroupMemberResultModel> result__ = new ArrayList<>();
    /** 検索結果件数 */
    private int serachCnt__ = 0;

    /**
     *
     * コンストラクタ
     * @param param
     * @param ctx RestApiコンテキスト
     */
    public SchGroupMemberGetBiz(SchGroupMemberGetParamModel param,
            RestApiContext ctx) {
        param__ = param;
        ctx__ = ctx;
    }

    /**
     *
     * <br>[機  能] 検索の実行
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute() throws SQLException {
        switch (param__.getGroupType()) {
        case EnumGroupType.GROUP_VALUE_STRING:
            __exeGroup();
            break;
        case EnumGroupType.MYGROUP_VALUE_STRING:
            __exeMygroup();
            break;
        case EnumGroupType.SCHEDULELIST_VALUE_STRING:
            __exeSchedulelist();
            break;
        default:
            break;
        }
    }
    
    /**
     *
     * <br>[機  能] グループ所属メンバ取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __exeGroup() throws SQLException {
        List<UsrLabelValueBean> selectUserList =
            __getGroupMembers(String.valueOf(cash__.getRequestGroup(ctx__, param__.getGroupId()).getGrpSid()));
        __createResult(selectUserList);
    }

    /**
     *
     * <br>[機  能] マイグループ所属メンバ取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __exeMygroup() throws SQLException {
        List<UsrLabelValueBean> selectUserList =
            __getGroupMembers(String.format("%s%d", GSConstSchedule.MY_GROUP_STRING, param__.getGroupSid()));
        __createResult(selectUserList);
    }

    /**
     *
     * <br>[機  能] スケジュールリスト所属メンバ取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __exeSchedulelist() throws SQLException {

        List<UsrLabelValueBean> selectUserList = 
            __getGroupMembers(String.format("%s%d", GSConstSchedule.DSP_LIST_STRING, param__.getGroupSid()));

        UserSearchDao usDao = new UserSearchDao(ctx__.getCon());
        Map<Integer, CmnUserModel> userMap = usDao.getUsersDataList(
                                selectUserList
                                .stream()
                                .filter(lbl -> !lbl.getValue().startsWith("G"))
                                .map(lbl -> lbl.getValue())
                                .map(sidStr -> Integer.parseInt(sidStr))
                                .collect(Collectors.toList())
                            )
                            .stream()
                            .collect(
                                Collectors.toMap(
                                    usr -> usr.getUsrSid(),
                                    usr -> usr)
                                );
        //グループ名情報Map
        Map<Integer, CmnGroupmModel> groupMap = new HashMap<Integer, CmnGroupmModel>();
        Set<Integer> groupSidSet =
                selectUserList
                .stream()
                .filter(lbl -> lbl.getValue().startsWith("G"))
                .map(lbl -> lbl.getValue().substring("G".length()))
                .map(sidStr -> Integer.parseInt(sidStr))
                .collect(Collectors.toSet());

        if (groupSidSet.size() > 0) {
            int[] groupSids =
                    groupSidSet.stream()
                    .mapToInt(sid -> sid)
                    .toArray();

            GroupDao gDao = new GroupDao(ctx__.getCon());
            List<CmnGroupmModel> gMdlList = gDao.getGroups(groupSids);
            if (gMdlList != null) {
                for (CmnGroupmModel gMdl : gMdlList) {
                    groupMap.put(gMdl.getGrpSid(), gMdl);
                }
            }
        }

        for (UsrLabelValueBean lbl : selectUserList) {
            SchGroupMemberResultModel resMdl = new SchGroupMemberResultModel();
            if (lbl.getValue().startsWith("G")) {
                CmnGroupmModel grp = groupMap.get(
                        Integer.parseInt(
                                lbl.getValue().substring("G".length()))
                        );
                if (grp == null) {
                    continue;
                }

                resMdl.setMemberFlg(1);
                resMdl.setId(grp.getGrpId());
                resMdl.setSid(grp.getGrpSid());
                resMdl.setName(grp.getGrpName());
            } else {
                CmnUserModel user = userMap.get(Integer.parseInt(lbl.getValue()));
                if (user == null) {
                    continue;
                }

                resMdl.setMemberFlg(0);
                resMdl.setId(user.getUsrLgid());
                resMdl.setSid(user.getUsrSid());
                resMdl.setName(user.getUsiName());
                resMdl.setSeiText(user.getUsiSei());
                resMdl.setMeiText(user.getUsiMei());
                resMdl.setSeiKanaText(user.getUsiSeiKn());
                resMdl.setMeiKanaText(user.getUsiMeiKn());
                resMdl.setLoginStopFlg(user.getUsrUkoFlg());

            }
            result__.add(resMdl);
        }
    }

    /**
     * <br>[機  能] 指定したグループの所属メンバーを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSidStr グループSID/マイグループSID/スケジュールリストSID
     * @return List<UsrLabelValueBean>
     * @throws SQLException SQL実行時例外
     */
    private List<UsrLabelValueBean> __getGroupMembers(String groupSidStr) throws SQLException {
        SchUserGroupSelectInitBiz selectInitBiz = new SchUserGroupSelectInitBiz(
                ctx__.getRequestModel(),
                ctx__.getCon(),
                null,
                groupSidStr,
                this,
                param__.getLimit(),
                param__.getOffset());

        selectInitBiz.initUserAndGroup();
        serachCnt__ = selectInitBiz.getSearchCnt();
        return selectInitBiz.getBaseUsrLabelList();
    }
    
    /**
     * <br>[機  能] 指定したグループ or マイグループの所属メンバーの詳細情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param List<UsrLabelValueBean> グループ所属メンバーリスト
     * @throws SQLException SQL実行時例外
     */
    private void __createResult(List<UsrLabelValueBean> selectUserList) throws SQLException {

        UserSearchDao usDao = new UserSearchDao(ctx__.getCon());
        Map<String, CmnUserModel> userMap
            = usDao.getUsersDataList(
                    selectUserList
                    .stream()
                    .map(lbl -> lbl.getValue())
                    .map(sidStr -> Integer.parseInt(sidStr))
                    .collect(Collectors.toList())
                )
                .stream()
                .collect(Collectors.toMap(s -> String.valueOf(s.getUsrSid()), s -> s));

        CmnUserModel user = null;
        for (UsrLabelValueBean selectUser : selectUserList) {
            user = userMap.get(selectUser.getValue());
            if (user != null) {
                SchGroupMemberResultModel resMdl = new SchGroupMemberResultModel();
                result__.add(resMdl);
                resMdl.setMemberFlg(0);
                resMdl.setId(user.getUsrLgid());
                resMdl.setSid(user.getUsrSid());
                resMdl.setName(user.getUsiName());
                resMdl.setSeiText(user.getUsiSei());
                resMdl.setMeiText(user.getUsiMei());
                resMdl.setSeiKanaText(user.getUsiSeiKn());
                resMdl.setMeiKanaText(user.getUsiMeiKn());
                resMdl.setLoginStopFlg(user.getUsrUkoFlg());
            }
        }
    }

    @Override
    public List<UsrLabelValueBean> addExUserLabelList(String grpSidStr,
            List<UsrLabelValueBean> baseUsrList) {
        //指定無し
        GsMessage gsMsg = new GsMessage(ctx__.getRequestModel());
        String textWithoutSpecify = gsMsg.getMessage("cmn.without.specifying");
        List < UsrLabelValueBean > labelList =
                new ArrayList<UsrLabelValueBean>(baseUsrList);
        labelList.add(0, new UsrLabelValueBean(textWithoutSpecify, "-1"));
        return labelList;
    }

    @Override
    public List<SchLabelValueModel> addExGroupLabelList(
            List<SchLabelValueModel> baseGrpList) {
        return baseGrpList;
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<SchGroupMemberResultModel> getResult() {
        return result__;
    }

    /**
     *
     * <br>[機  能] 検索結果件数の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public int getSearchCnt() {
        return serachCnt__;
    }
}
