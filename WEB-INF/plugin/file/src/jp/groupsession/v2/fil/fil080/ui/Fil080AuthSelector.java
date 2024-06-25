package jp.groupsession.v2.fil.fil080.ui;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.fil060.ui.Fil060AuthSelector;
/**
 *
 * <br>[機  能] ファイル登録画面 アクセス権限 選択UIクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil080AuthSelector extends Fil060AuthSelector
implements ICustomUserGroupSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Fil080AuthSelector(
            ParamForm<? extends Fil080AuthSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<? extends Fil080AuthSelector> builder() {
        ParamForm<Fil080AuthSelector> ret
            = new ParamForm<>(Fil080AuthSelector.class);
        return ret;
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

        return super.answerGroupExclusion(reqMdl, con, paramModel);
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

        return super.answerUserExclusion(reqMdl, con, paramModel);
    }

    protected int _getPersonalFlg(ParameterObject paramModel) {
        return (Integer) paramModel.get("fil040PersonalFlg");
    }

    protected int _getParentSid(Connection con, ParameterObject paramModel, RequestModel reqMdl)
    throws SQLException {
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);

        int parentSid = NullDefault.getInt((String) paramModel.get("fil070ParentDirSid"), -1);
        if (parentSid < 1) {
            int dirSid = NullDefault.getInt((String) paramModel.get("fil070DirSid"), -1);
            parentSid = filBiz.getParentDirSid(dirSid);
        }

        return parentSid;
    }
}