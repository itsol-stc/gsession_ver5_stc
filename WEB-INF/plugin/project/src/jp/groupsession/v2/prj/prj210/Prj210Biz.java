package jp.groupsession.v2.prj.prj210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 プロジェクト選択画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj210Biz {

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Prj210Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj210ParamModel
     * @param buMdl ユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Prj210ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {


        //一覧を設定する。
        __setDspList(paramMdl, buMdl);

    }

    /**
     * <br>[機  能] プロジェクト一覧をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj210ParamModel
     * @param buMdl ユーザモデル
     * @throws SQLException SQL実行例外
     */
    private void __setDspList(Prj210ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        //最大件数
        int maxCnt = Prj210Const.PROJECTSEARCH_MAXCOUNT;

        //検索用Modelを作成
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(buMdl.getUsrsid());
        bean.setLimit(maxCnt);
        bean.setOrder(paramMdl.getPrj210order());
        bean.setSort(paramMdl.getPrj210sort());
        bean.setEndPrjFlg(true);
        bean.setPrjStatus(__getSelected(paramMdl));

        int koukaiKbn = NullDefault.getInt(paramMdl.getPrj210KoukaiKbn(), -1);
        int getKbn = 0;
        if (koukaiKbn == Prj210Const.PROJECT_KOUKAI) {
            getKbn = ProjectSearchModel.GET_OPEN_NOT_BELONG;
        } else if (koukaiKbn == Prj210Const.PROJECT_SANKA) {
            getKbn = ProjectSearchModel.GET_BELONG;
        } else {
            //管理者権限がある場合は全て、ない場合は公開プロジェクトのみ取得
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(
                    con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);
            getKbn = ProjectSearchModel.GET_OPEN;
            if (adminUser) {
                getKbn = ProjectSearchModel.GET_ALL;
            }
        }
        bean.setGetKbn(getKbn);

        int progress = NullDefault.getInt(paramMdl.getPrj210Progress(), -1);
        //状態
        if (progress == Prj210Const.PROGRESS_MIKAN) {
            bean.setStatusFrom(0);
            bean.setStatusTo(99);
        } else if (progress == Prj210Const.PROGRESS_END) {
            bean.setStatusFrom(100);
            bean.setStatusTo(100);
        }

        //件数カウント
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        int searchCnt = psDao.getDashBoardProjectCount(bean);

        int limit = Prj210Const.PROJECTSEARCH_MAXCOUNT;
        //ページ調整
        int maxPage = searchCnt / limit;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int nowPage = paramMdl.getPrj210page();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(searchCnt, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);

        if (nowPage < 1) {
            nowPage = 1;
        } else if (nowPage > maxPage) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }
        paramMdl.setPrj210page(nowPage);
        paramMdl.setPrj210pageTop(nowPage);
        paramMdl.setPrj210pageBottom(nowPage);
        bean.setStart(start);
        bean.setLimit(limit);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
        }

        if (searchCnt < 1) {
            return;
        }
        //会社一覧を取得
        List<ProjectItemModel> dspList = null;
        if (searchCnt < 1) {
            dspList = new ArrayList<ProjectItemModel>();
        } else {
            dspList = psDao.getProjectList(bean);

            //時間表示用を設定する。
            for (ProjectItemModel mdl : dspList) {
                UDate sdate = mdl.getStartDate();
                if (sdate != null) {
                    mdl.setStrStartDate(sdate.getYear() + "/"
                            + StringUtil.toDecFormat(sdate.getMonth(), "00") + "/"
                            + StringUtil.toDecFormat(sdate.getIntDay(), "00"));
                }

                UDate edate = mdl.getEndDate();
                if (edate != null) {
                    mdl.setStrEndDate(edate.getYear() + "/"
                            + StringUtil.toDecFormat(edate.getMonth(), "00") + "/"
                            + StringUtil.toDecFormat(edate.getIntDay(), "00"));
                }

            }

        }

        paramMdl.setPrj210ProjectList(dspList);

    }


    /**
     * <br>[機  能] プロジェクト検索条件を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj210ParamModel
     * @return ret 検索条件区分
     * @throws SQLException SQL実行例外
     */
    private int __getSelected(Prj210ParamModel paramMdl) throws SQLException {

        int koukaiKbn = NullDefault.getInt(paramMdl.getPrj210KoukaiKbn(), -1);
        int progress = NullDefault.getInt(paramMdl.getPrj210Progress(), -1);

        int koukaiKbnAll = Integer.parseInt(Prj210Const.KOUKAI_KBN_ALL);
        int progressAll = Integer.parseInt(Prj210Const.PROGRESS_ALL);

        int ret = 0;
        if (koukaiKbn == Prj210Const.PROJECT_SANKA
                && progress == Prj210Const.PROGRESS_MIKAN) {
            //参加プロジェクト 未完
            ret = GSConstProject.PRJ_MEMBER_NOT_END;

        } else if (koukaiKbn == Prj210Const.PROJECT_SANKA
                && progress == Prj210Const.PROGRESS_END) {
            //参加プロジェクト 完了
            ret = GSConstProject.PRJ_MEMBER_END;

        } else if (koukaiKbn == Prj210Const.PROJECT_SANKA
                && progress == progressAll) {
            //参加プロジェクト 全て
            ret = GSConstProject.PRJ_MEMBER_ALL;

        } else if (koukaiKbn == Prj210Const.PROJECT_KOUKAI
                && progress == Prj210Const.PROGRESS_MIKAN) {
            //公開プロジェクト 未完
            ret = GSConstProject.PRJ_OPEN_NOT_END;

        } else if (koukaiKbn == Prj210Const.PROJECT_KOUKAI
                && progress == Prj210Const.PROGRESS_END) {
            //公開プロジェクト 完了
            ret = GSConstProject.PRJ_OPEN_END;

        } else if (koukaiKbn == Prj210Const.PROJECT_KOUKAI
                && progress == progressAll) {
            //公開プロジェクト 全て
            ret = GSConstProject.PRJ_OPEN_ALL;

        } else if (koukaiKbn == koukaiKbnAll
                && progress == Prj210Const.PROGRESS_MIKAN) {
            //全ての未完プロジェクト
            ret = GSConstProject.PRJ_NOT_END_ALL;

        } else if (koukaiKbn == koukaiKbnAll
                && progress == Prj210Const.PROGRESS_END) {
            //全ての完了プロジェクト
            ret = GSConstProject.PRJ_END_ALL;

        } else if (koukaiKbn == koukaiKbnAll
                && progress == progressAll) {
            //全てのプロジェクト
            ret = GSConstProject.PRJ_ALL;

        }

        return ret;
    }

}