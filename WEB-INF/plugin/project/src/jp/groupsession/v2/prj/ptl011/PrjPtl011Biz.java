package jp.groupsession.v2.prj.ptl011;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 ポートレット TODO状態内訳のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl011Biz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public PrjPtl011Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] TODO内訳情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl PrjPtl011ParamModel
     * @param buMdl セッションユーザModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(PrjPtl011ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        if (paramMdl.getPrjPtl010PrjSid() < 1) {
            return;
        }

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();

        //表示項目取得
        ProjectItemModel cntMdl = __getTodoCnt(paramMdl, userSid);
        paramMdl.setTodoKanryoCnt(cntMdl.getPrjTodoKanryoCnt());
        paramMdl.setTodoMikanryoCnt(cntMdl.getPrjTodoMikanryoCnt());
        paramMdl.setTodoSinkotyuCnt(cntMdl.getPrjTodoSinkotyuCnt());

        int prjSid = paramMdl.getPrjPtl010PrjSid();

        //プロジェクト名を設定する。
        PrjPrjdataDao prjdataDao = new PrjPrjdataDao(con__);
        PrjPrjdataModel prjModel = prjdataDao.getProjectData(prjSid);
        if (prjModel != null) {
            paramMdl.setPrjPtl010prjName(prjModel.getPrjName());
        }
    }

    /**
     * <br>[機  能] TODO情報の完了・未完了件数を取得する
     * <br>[解  説]
     * <br>[備  考] 全件数をカウント(ページ毎のカウントではない)
     *
     * @param paramMdl PrjPtl011ParamModel
     * @param userSid ログインユーザSID
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private ProjectItemModel __getTodoCnt(PrjPtl011ParamModel paramMdl, int userSid)
    throws SQLException {

        //検索用Modelを作成
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_ALL);
        bean.setSelectingDate(null);
        bean.setSelectingStatus(null);
        bean.setSelectingMember(null);
        bean.setProjectSid(paramMdl.getPrjPtl010PrjSid());

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);

        return psDao.getTodoCnt(bean);
    }

}