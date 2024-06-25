package jp.groupsession.v2.prj.prj190;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 個人設定 ダッシュボード初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj190Biz {
    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Prj190Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期値セット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj190ParamModel
     * @param buMdl ベースユーザモデル
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Prj190ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        PrjUserConfDao confDao = new PrjUserConfDao(con__);
        PrjUserConfModel confMdl = confDao.select(buMdl.getUsrsid());
        if (confMdl != null) {
            paramMdl.setPrj190TodoDay(String.valueOf(confMdl.getPucTodoDate()));
            paramMdl.setPrj190DefDsp(confMdl.getPucDefDsp());

           /******************************************************************
            *
            * 削除された or 所属メンバから外された などの場合は
            * デフォルト値をセット。
            *
            * それ以外の場合は登録済のプロジェクト選択値をセット。
            *
            ******************************************************************/

            boolean todoProjectEffective = false;
            ProjectSearchModel bean = new ProjectSearchModel();
            bean.setUserSid(buMdl.getUsrsid());
            bean.setOrder(GSConst.ORDER_KEY_ASC);
            bean.setSort(GSConstProject.SORT_PRJECT_START);
            bean.setEndPrjFlg(true);
            bean.setGetKbn(ProjectSearchModel.GET_BELONG);
            bean.setProjectSid(confMdl.getPucTodoProject());

            GsMessage gsMsg = new GsMessage(reqMdl__);
            ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
            List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);
            if (!prjlist.isEmpty()) {
                todoProjectEffective = true;
            }

            if (todoProjectEffective) {
                paramMdl.setPrj190TodoPrj(String.valueOf(confMdl.getPucTodoProject()));
            } else {
                paramMdl.setPrj190TodoPrj(String.valueOf(GSConstProject.PROJECT_ALL));
            }

           /******************************************************************
            *
            * 先の判定で登録済のプロジェクトが無効の場合 or
            * プロジェクトが有効であるが選択済の状態区分の値が
            * 削除などにより無効である場合はデフォルト値をセット。
            *
            * それ以外の場合は登録済の状態区分選択値をセット。
            *
            ******************************************************************/

            if (todoProjectEffective) {
                PrjTodostatusDao todoStsDao = new PrjTodostatusDao(con__);
                PrjTodostatusModel todoStsMdl =
                    todoStsDao.select(
                            confMdl.getPucTodoProject(),
                            confMdl.getPucTodoStatus());

                if (todoStsMdl != null
                        || confMdl.getPucTodoStatus() == GSConstProject.STATUS_ALL
                        || confMdl.getPucTodoStatus() == GSConstProject.STATUS_YOTEI_AND_MIKAN
                        || confMdl.getPucTodoStatus() == GSConstProject.STATUS_MIKAN
                        || confMdl.getPucTodoStatus() == GSConstProject.STATUS_SINKO
                        || confMdl.getPucTodoStatus() == GSConstProject.STATUS_KANRYO) {
                    paramMdl.setPrj190TodoSts(String.valueOf(confMdl.getPucTodoStatus()));
                } else {
                    paramMdl.setPrj190TodoSts(
                            String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN));
                }
            }

           /******************************************************************
            *
            * 管理者しか選択できない項目 + 非管理者の場合はデフォルト値をセット。
            * それ以外の場合は登録済のプロジェクト区分をセット。
            *
            ******************************************************************/

            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(
                    con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

            int prjKbn = confMdl.getPucPrjProject();
            if ((prjKbn == GSConstProject.PRJ_NOT_END_ALL
                    || prjKbn == GSConstProject.PRJ_END_ALL
                    || prjKbn == GSConstProject.PRJ_ALL)
                    && !adminUser) {
                paramMdl.setPrj190Project(String.valueOf(GSConstProject.PRJ_MEMBER_NOT_END));
            } else {
                paramMdl.setPrj190Project(String.valueOf(confMdl.getPucPrjProject()));
            }
            //メイン初期値設定
            paramMdl.setPrj190mainDspDate(String.valueOf(confMdl.getPucMainDate()));
            paramMdl.setPrj190mainDspStatus(String.valueOf(confMdl.getPucMainStatus()));
            paramMdl.setPrj190mainDspMember(String.valueOf(confMdl.getPucMainMember()));


            //Todo登録初期設定
            paramMdl.setPrj190todoInputKbn(confMdl.getPucTodoDsp());

        }
    }

    /**
     * <br>[機  能] リストと選択値を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj190ParamModel
     * @param buMdl ベースユーザモデル
     * @throws SQLException SQL実行時例外
     */
    public void setList(Prj190ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);

        //ＴＯＤＯ 日付コンボリスト
        paramMdl.setPrj190TodoDayList(pcBiz.getTargetDateLabel());

        //ＴＯＤＯ 日付選択値
        paramMdl.setPrj190TodoDay(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190TodoDay(),
                        String.valueOf(GSConstProject.DATE_THE_PAST)));

        //ＴＯＤＯ プロジェクトリスト
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(buMdl.getUsrsid());
        bean.setOrder(GSConst.ORDER_KEY_ASC);
        bean.setSort(GSConstProject.SORT_PRJECT_START);
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_BELONG);

        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);

        List<LabelValueBean> todoPrjLabelList = new ArrayList<LabelValueBean>();

        //全て
        String textAll = gsMsg.getMessage("cmn.all");
        //全て
        todoPrjLabelList.add(
                new LabelValueBean(
                        textAll,
                        String.valueOf(GSConstProject.PROJECT_ALL)));

        for (ProjectItemModel piMdl : prjlist) {
            todoPrjLabelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));
        }
        paramMdl.setPrj190TodoProjectList(todoPrjLabelList);

        //ＴＯＤＯ プロジェクト選択値
        paramMdl.setPrj190TodoPrj(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190TodoPrj(),
                        String.valueOf(GSConstProject.PROJECT_ALL)));

        //ＴＯＤＯ 状態リスト
        paramMdl.setPrj190TodoStsList(
                pcBiz.getStatusLabel(
                        NullDefault.getInt(
                                paramMdl.getPrj190TodoPrj(), -1)));

        //ＴＯＤＯ 状態選択値
        paramMdl.setPrj190TodoSts(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190TodoSts(),
                        String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN)));

        //プロジェクト プロジェクトリスト
        paramMdl.setPrj190ProjectList(pcBiz.getTargetProjectLabel(buMdl));

        //プロジェクト プロジェクト選択値
        paramMdl.setPrj190Project(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190Project(),
                        String.valueOf(GSConstProject.PRJ_MEMBER_NOT_END)));


        //メイン表示設定
        //日付コンボリスト
        paramMdl.setPrj190mainDspDateList(pcBiz.getTargetDateLabel());
        //日付選択値
        paramMdl.setPrj190mainDspDate(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190mainDspDate(),
                        String.valueOf(GSConstProject.DATE_THE_PAST)));
        //状態リスト
        paramMdl.setPrj190mainDspStatusList(pcBiz.getStatusLabel());
        //状態選択値
        paramMdl.setPrj190mainDspStatus(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190mainDspStatus(),
                        String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN)));
        //メンバリスト
        paramMdl.setPrj190mainDspMemberList(pcBiz.getMemberLabel(buMdl.getUsrsid()));
        //メンバ選択値
        paramMdl.setPrj190mainDspMember(
                NullDefault.getStringZeroLength(
                        paramMdl.getPrj190mainDspMember(),
                        String.valueOf(buMdl.getUsrsid())));


    }
}