package jp.groupsession.v2.prj;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.SchAppendSchData;
import jp.groupsession.v2.cmn.UrlBuilder;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchAppendDataModel;
import jp.groupsession.v2.cmn.model.SchAppendDataParam;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクトのTODOをスケジュールのデータとして返すクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjSchAppendSchData implements SchAppendSchData {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjSchAppendSchData.class);

    /**
     * <br>[機  能] スケジュールに表示する情報を取得する。
     * <br>[解  説]
     * <br>
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @return メッセージのリスト
     * @throws SQLException SQL実行例外
     */
    public List<SchAppendDataModel> getAppendSchData(
            SchAppendDataParam paramMdl, RequestModel reqMdl,
            Connection con) throws SQLException {

        List<SchAppendDataModel> appList = new ArrayList<SchAppendDataModel>();

        //個人設定を取得
        PrjUserConfModel pucMdl = new PrjUserConfModel();
        PrjUserConfDao pucDao = new PrjUserConfDao(con);
        if (reqMdl.getSmodel() != null) {
            pucMdl = pucDao.select(reqMdl.getSmodel().getUsrsid());
        }

        int prjSch = 0;
        if (pucMdl != null) {
            prjSch = pucMdl.getPucSchKbn();
        }

        if (prjSch == 0) {
            //TODOをスケジュールに表示する
            appList = __getSearchModel(con, reqMdl, paramMdl);
        }

        return appList;
    }

    /**
     * <br>[機  能] TODO情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @return ArrayList<SchAppendDataModel>
     * @throws SQLException SQL実行例外
     */
    private ArrayList<SchAppendDataModel> __getSearchModel(Connection con,
                                                RequestModel reqMdl,
                                                SchAppendDataParam paramMdl)
                                                throws SQLException {

        ArrayList<SchAppendDataModel> sadmList = new ArrayList<SchAppendDataModel>();
        log__.debug("START_PrjSchAppendSchData");
        //セッション情報を取得
        BaseUserModel buMdl = reqMdl.getSmodel();

        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(buMdl.getUsrsid());
        //完了プロジェクト表示フラグ true=表示
        bean.setEndPrjFlg(true);
        //未来の予定も表示する
//        bean.setMirai("1");

        //管理者権限がある場合は全て、ない場合は公開プロジェクトのみ取得
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstProject.PLUGIN_ID_PROJECT);
        int getKbn = ProjectSearchModel.GET_OPEN;
        if (adminUser) {
            getKbn = ProjectSearchModel.GET_ALL;
        }
        bean.setGetKbn(getKbn);


        UDate frDate = null;
        UDate toDate = null;
        String[] usrSid = null;
        if (paramMdl.getUsrSidList() != null) {
            List<Integer> usrSidList = paramMdl.getUsrSidList();
            usrSid = new String[usrSidList.size()];

            for (int i = 0; i < usrSidList.size(); i++) {
                Integer sid = usrSidList.get(i);
                usrSid[i] = String.valueOf(sid);
            }
        }

        frDate = paramMdl.getFrDate();
        toDate = paramMdl.getToDate();

        //担当メンバー
        bean.setMemberSid(usrSid);
        bean.setMemberBaseTodo(true);
        //状態From
        bean.setStatusFrom(0);
        //状態To
        bean.setStatusTo(99);
        //開始予定
        bean.setStartFrom(frDate);
        //期限
        bean.setStartTo(toDate);
        //検索区分
        bean.setPrjSearchKbn(1);

        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        ProjectSearchDao psDao = new ProjectSearchDao(con, gsMsg);
        List<ProjectItemModel> prjList = psDao.getTodoList2(bean);

        if (!prjList.isEmpty()) {
            SchAppendDataModel sadm = null;
            for (ProjectItemModel pim : prjList) {
                UDate pFrDate = pim.getStartDate();
                UDate pToDate = pim.getEndDate();
                if (pFrDate == null) {
                    pFrDate = pToDate;
                }
                if (pToDate == null) {
                    pToDate = pFrDate;
                }
                sadm = new SchAppendDataModel();
                sadm.setSchPlgUrl(
                  createTodoUrl(reqMdl, paramMdl, pim.getProjectSid(), pim.getTodoSid()));
                sadm.setSchPlgId(GSConstCommon.PLUGIN_ID_PROJECT);
                sadm.setFromDate(pFrDate);
                sadm.setToDate(pToDate);
                sadm.setTimeKbn(1);
                sadm.setTitle(pim.getTodoTitle());
                sadm.setValueStr(pim.getNaiyou());
                sadm.setReturnUrl(paramMdl.getReturnUrl());
                sadm.setUsrSid(pim.getSchUsrSid());
                sadmList.add(sadm);
            }
        }
        log__.debug("END_PrjSchAppendSchData");
        return sadmList;
    }
    /**
     * <br>[機  能] TODO参照画面URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param prjSid プロジェクトID
     * @param todoSid todoID
     * @return スレッドURL
     */
    public String createTodoUrl(RequestModel reqMdl,
                                SchAppendDataParam paramMdl,
                                int prjSid,
                                int todoSid) {

        //TODO参照画面のURLを作成
        UrlBuilder ub = new UrlBuilder(reqMdl, GSConst.PLUGINID_PRJ, "prj060");
        ub.addUrlParam("prj060scrId", paramMdl.getSrcId());
        ub.addUrlParam("prj060prjSid", prjSid);
        ub.addUrlParam("prj060todoSid", todoSid);
        ub.addUrlParam("prj060schUrl", paramMdl.getReturnUrl());

        return ub.getUrl();
    }
}