package jp.groupsession.v2.fil.fil030.ui;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
/**
 *
 * <br>[機  能] キャビネット登録画面 個人キャビネット 閲覧ユーザ選択UIクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil030PrivateUserGroupSelector extends UserGroupSelector
implements ICustomUserGroupSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Fil030PrivateUserGroupSelector(
            ParamForm<? extends Fil030PrivateUserGroupSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<? extends Fil030PrivateUserGroupSelector> builder() {
        ParamForm<Fil030PrivateUserGroupSelector> ret
            = new ParamForm<>(Fil030PrivateUserGroupSelector.class);
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

        ExclusionConf ret = new ExclusionConf(false);

        if ((Integer) paramModel.get("fil030PersonalFlg")
                == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {

            String cmdMode = NullDefault.getString((String) paramModel.get("cmnMode"), "");
            if (cmdMode.equals(GSConstFile.CMN_MODE_ADD)) {
                //個人キャビネット登録の場合、ログインユーザ(=個人キャビネット所有者)を除外
                ret.add(String.valueOf(reqMdl.getSmodel().getUsrsid()));
            } else if (cmdMode.equals(GSConstFile.CMN_MODE_EDT)) {
                //個人キャビネット編集の場合、個人キャビネットの所有者を除外
                FileCabinetDao fcbDao = new FileCabinetDao(con);
                int selectCabSid
                    = NullDefault.getInt((String) paramModel.get("fil030SelectCabinet"), 0);
                FileCabinetModel fcbMdl = fcbDao.select(selectCabSid);
                if (fcbMdl != null) {
                    ret.add(String.valueOf(fcbMdl.getFcbOwnerSid()));
                }
            }
        }

        return ret;
    }
}