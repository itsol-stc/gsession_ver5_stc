package jp.groupsession.v2.rsv.ui.parts.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.usr.GSConstUser;

/*
 * 施設予約登録時のスケジュール同時登録ユーザ選択
 */
public class RsvScheduleSelector extends UserGroupSelector implements ICustomUserGroupSelector {

    protected RsvScheduleSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<RsvScheduleSelector> builder() {
        ParamForm<RsvScheduleSelector> ret = new ParamForm<>(RsvScheduleSelector.class);
        return ret;
    }

    @Override
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl, Connection con,
                                                ParameterObject paramModel) throws SQLException {

        //スケジュールの基本設定・特例アクセス設定を元に、表示するグループを制限する
        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        List<ExtendedLabelValueModel> labelList =
            rsvSchBiz.getGroupLabelForSchedule(
                con, reqMdl, reqMdl.getSmodel().getUsrsid(), false);

        List<String> grpSidList = new ArrayList<>();
        grpSidList.addAll(
                labelList
                    .stream()
                    .map(grp -> grp.getValue())
                    .collect(Collectors.toList())
                );

        return new ExclusionConf(true, grpSidList);
    }

    @Override
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException {

        ExclusionConf ret = new ExclusionConf(false);

        //管理者ユーザを除外
        ret.add(String.valueOf(GSConstUser.SID_ADMIN));

        //スケジュール 特例アクセスにより、スケジュール登録不可に設定されているユーザを除外する
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUsrList
            = schDao.getNotRegistUserList(reqMdl.getSmodel().getUsrsid());
        ret.addAll(
                notAccessUsrList.stream()
                .map(val -> String.valueOf(val))
                .collect(Collectors.toList())
        );

        return ret;
    }
}