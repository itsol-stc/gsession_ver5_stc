package jp.groupsession.v2.cht.cht010.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
/**
 *
 * <br>[機  能] チャット一覧画面 ユーザ選択UIクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht010UserGroupSelector extends UserGroupSelector implements ICustomUserGroupSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Cht010UserGroupSelector(ParamForm<? extends Cht010UserGroupSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<? extends Cht010UserGroupSelector> builder() {
        ParamForm<Cht010UserGroupSelector> ret = new ParamForm<>(Cht010UserGroupSelector.class);
        return ret;
    }

    @Override
    public boolean isUseDetailSearch() {
        return false;
    }

    /**
     *
     * <br>[機  能] グループ選択除外設定を返すリスナメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 除外設定
     * @throws SQLException
     */
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {

        return new ExclusionConf(false);
    }

    /**
     *
     * <br>[機  能] ユーザ選択除外設定を返すリスナメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 除外設定
     * @throws SQLException
     */
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException {

       //チャット 特例アクセスにより、アクセスを禁止されているユーザ・グループを取得
       int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
       ChtMemberBiz chtMemBiz = new ChtMemberBiz(con);
       List<Integer> tokureiMemberList
                   = chtMemBiz.getTokureiUser(sessionUsrSid,
                                               chtMemBiz.getTokureiGroup(sessionUsrSid));
       List<Integer> tokureiGroupList
                   = chtMemBiz.getTokureiUserBelong(sessionUsrSid, tokureiMemberList);

       ExclusionConf ret = new ExclusionConf(false);

       //アクセスを禁止されているユーザを設定
       ret.addAll(
               tokureiMemberList
               .stream()
               .filter(sid -> sid != sessionUsrSid)
               .map(sid -> String.valueOf(sid))
               .collect(Collectors.toList())
           );

       //アクセスを禁止されているグループを設定
       ret.addAll(
               tokureiGroupList
               .stream()
               .map(sid -> "G" + String.valueOf(sid))
               .collect(Collectors.toList())
           );

       return ret;
    }
}