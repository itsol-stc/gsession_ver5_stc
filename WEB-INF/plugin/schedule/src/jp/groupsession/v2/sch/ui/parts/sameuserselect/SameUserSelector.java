package jp.groupsession.v2.sch.ui.parts.sameuserselect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sch.biz.SchCommonBiz;

public class SameUserSelector extends UserGroupSelector implements ICustomUserGroupSelector {
    /** オプションパラメータキー 登録対象SID*/
    public static final String OPTIONPARAMKEY_TARGETUSRSID = "targetUserSid";

    protected SameUserSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
    }
    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<SameUserSelector> builder() {
        ParamForm<SameUserSelector> ret = new ParamForm<>(SameUserSelector.class);
        return ret;
    }
    @Override
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException {
        //許可SIDリスト
        //共通処理のラベルリストからSIDリストへ変換
         SchCommonBiz scBiz = new SchCommonBiz(reqMdl);
         List<String> sidList = scBiz.getGroupLabelForSchedule(
                 reqMdl.getSmodel().getUsrsid(), con, false)
                 .stream()
                 .map(lbl -> lbl.getValue())
                 .map(sid -> String.valueOf(sid))
                 .collect(Collectors.toList());

        return new ExclusionConf(true, sidList);
    }

    @Override
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException {
        //特例アクセス制限ユーザーを取得
        //選択ユーザから登録不可ユーザを除外する
        SchDao schDao = new SchDao(con);
        List<String> notAccessUsrList;
        notAccessUsrList = schDao.getNotRegistUserList(reqMdl.getSmodel().getUsrsid())
                                              .stream()
                                              .map(sid -> String.valueOf(sid))
                                              .collect(Collectors.toList());

        //スケジュール登録対象SID
        //同時登録ユーザから登録対象ユーザを除外
          String targetSid = paramModel.get(
                    NullDefault.getString(
                         getOptionParameter(OPTIONPARAMKEY_TARGETUSRSID),
                         ""), String.class);
          ExclusionConf ret = new ExclusionConf(false);
          ret.add(targetSid);
          ret.addAll(notAccessUsrList);
          return ret;
    }


}
