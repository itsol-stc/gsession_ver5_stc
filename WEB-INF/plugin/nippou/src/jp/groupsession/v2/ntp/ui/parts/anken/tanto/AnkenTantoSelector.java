package jp.groupsession.v2.ntp.ui.parts.anken.tanto;

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
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.usr.GSConstUser;

public class AnkenTantoSelector extends UserGroupSelector implements ICustomUserGroupSelector {

    protected AnkenTantoSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<AnkenTantoSelector> builder() {
        ParamForm<AnkenTantoSelector> ret = new ParamForm<>(AnkenTantoSelector.class);
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
            Connection con, ParameterObject paramModel) {

        //管理者ユーザは除外
        ExclusionConf ret = new ExclusionConf(false);
        ret.add(String.valueOf(GSConstUser.SID_ADMIN));
        return ret;
    }
}