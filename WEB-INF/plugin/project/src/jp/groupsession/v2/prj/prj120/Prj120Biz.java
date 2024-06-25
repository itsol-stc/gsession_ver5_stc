package jp.groupsession.v2.prj.prj120;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 プロジェクト選択ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj120Biz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj120Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj120ParamModel
     * @param buMdl セッションユーザModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Prj120ParamModel paramMdl,
                 BaseUserModel buMdl) throws SQLException {

        //プロジェクトリストを取得する
        GsMessage gsMsg = new GsMessage(reqMdl__);
        PrjCommonBiz cBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setProjectList(cBiz.getAllProjectList(buMdl, false));
    }
}
