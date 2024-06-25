package jp.groupsession.v2.ntp.ui.parts.smailmember;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;

public class SmailMemberSelector extends UserGroupSelector implements ICustomUserGroupSelector {

    protected SmailMemberSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<SmailMemberSelector> builder() {
        ParamForm<SmailMemberSelector> ret = new ParamForm<>(SmailMemberSelector.class);
        return ret;
    }

    @Override
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl, Connection con,
                                                ParameterObject paramModel) throws SQLException {
        //案件登録画面用のグループ一覧取得
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl);
        List<NtpLabelValueModel> ntpGroupList =
                ntpBiz.getGroupLabelForNippou(reqMdl.getSmodel().getUsrsid(),
                        con, false);
        List<String> grpSidList = new ArrayList<>();
        grpSidList.addAll(
                ntpGroupList
                    .stream()
                    .map(grp -> grp.getValue())
                    .collect(Collectors.toList())
                );

        return new ExclusionConf(true, grpSidList);
    }

    @Override
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException {

        //閲覧不可ユーザを除外
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        NippouDao ntpDao = new NippouDao(con);
        List<Integer> notAccessUserList
            = ntpDao.getNotAccessUserList(sessionUsrSid);
        ExclusionConf ret = new ExclusionConf(false);
        ret.addAll(
                notAccessUserList.stream()
                .map(val -> String.valueOf(val))
                .collect(Collectors.toList())
        );

        //閲覧不可グループを除外
        List<Integer> notAccessGroupList
            = ntpDao.getNotAccessGrpList(sessionUsrSid);
        ret.addAll(
                notAccessGroupList.stream()
                .map(val -> "G" + String.valueOf(val))
                .collect(Collectors.toList())
        );

        return ret;
    }
}