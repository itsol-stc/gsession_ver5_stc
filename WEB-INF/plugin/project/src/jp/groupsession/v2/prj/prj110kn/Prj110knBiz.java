package jp.groupsession.v2.prj.prj110kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjAdmConfDao;
import jp.groupsession.v2.prj.model.PrjAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 管理者設定 登録権限設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj110knBiz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj110knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj110knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Prj110knParamModel paramMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //管理者のみ登録可
        String textAdminOnly = gsMsg.getMessage("project.prj110.1");
        //プロジェクト登録権限を設定しない
        String textNoSetting = gsMsg.getMessage("project.prj110.2");
        String strEdit = "";
        if (paramMdl.getPrj110edit() == GSConstProject.PRJ_EDIT_KENGEN_ADM) {
            strEdit = textAdminOnly;
        } else {
            strEdit = textNoSetting;
        }
        paramMdl.setStrEdit(strEdit);
    }

    /**
     * <br>[機  能] 管理者設定情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj110knParamModel
     * @param usid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void update(Prj110knParamModel paramMdl, int usid) throws SQLException {

        UDate now = new UDate();

        //管理者設定情報の更新を行う
        PrjAdmConfModel pacMdl = new PrjAdmConfModel();
        pacMdl.setPacPrjEdit(paramMdl.getPrj110edit());
        pacMdl.setPacEuid(usid);
        pacMdl.setPacEdate(now);

        PrjAdmConfDao pacDao = new PrjAdmConfDao(con__);
        pacDao.update(pacMdl);
    }

}
