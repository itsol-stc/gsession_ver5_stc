package jp.groupsession.v2.ntp.ui.parts.anken.permit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.GSConstUser;

public class AnkenPermitSelector extends UserGroupSelector implements ICustomUserGroupSelector {

    protected AnkenPermitSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<AnkenPermitSelector> builder() {
        ParamForm<AnkenPermitSelector> ret = new ParamForm<>(AnkenPermitSelector.class);
        return ret;
    }

    @Override
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl, Connection con,
                                                ParameterObject paramModel) throws SQLException {
        //グループの除外を行わない
        return new ExclusionConf(false);
    }

    @Override
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) {

        //管理者ユーザは除外
        ExclusionConf ret = new ExclusionConf(false);
        ret.add(String.valueOf(GSConstUser.SID_ADMIN));

        //担当者に指定されたユーザは除外する
        Object tantoUser = paramModel.get("sv_users");
        if (tantoUser != null) {
            ret.addAll(Arrays.asList((String[]) tantoUser));
        }
        return ret;
    }

    

}