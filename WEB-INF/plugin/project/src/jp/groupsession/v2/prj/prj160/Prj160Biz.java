package jp.groupsession.v2.prj.prj160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj160Biz {

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Prj160Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] セッションユーザModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return usModel セッションユーザModel
     */
    private BaseUserModel __getSessionUserModel() {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        return usModel;
    }

    /**
     * <br>[機  能] 表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj160ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setDspData(Prj160ParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
            }
        }
        paramMdl.setPrj160FileLabelList(fileLblList);

        //セッションユーザモデル取得
        BaseUserModel usMdl = __getSessionUserModel();

        //選択プロジェクト設定
        if (paramMdl.getPrj160PrjSid() == -1
                && paramMdl.getPrj030prjSid() > 0) {
            paramMdl.setPrj160PrjSid(paramMdl.getPrj030prjSid());
        }

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con_, usMdl, GSConstProject.PLUGIN_ID_PROJECT);

        //プロジェクトコンボセット
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(usMdl.getUsrsid());
        bean.setOrder(GSConst.ORDER_KEY_ASC);
        bean.setSort(GSConstProject.SORT_PRJECT_START);
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_EDIT);
        bean.setAdmin(adminUser);

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con_, gsMsg);
        List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);

        for (ProjectItemModel piMdl : prjlist) {
            labelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));
        }
        paramMdl.setPrj160ProjectLabel(labelList);

        int prjSid = paramMdl.getPrj160PrjSid();

        //選択されたプロジェクトのカテゴリラベルセット
        paramMdl.setPrj160CategoryList(__getCategory(prjSid));

        //選択されたプロジェクトの状態ラベルセット
        paramMdl.setPrj160StatusList(__getStatus(prjSid));

        //選択されたプロジェクトのメンバ情報セット
        paramMdl.setPrj160MemberList(__getMember(prjSid));

        //マイプロジェクト区分を設定
        __setPrjMyKbn(paramMdl);
    }

    /**
     * <br>[機  能] カテゴリリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return ret カテゴリリスト
     * @throws SQLException SQL実行例外
     */
    private List<PrjTodocategoryModel> __getCategory(int prjSid) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //無し
        String textNo = gsMsg.getMessage("cmn.no3");
        PrjTodocategoryDao dao = new PrjTodocategoryDao(con_);
        List<PrjTodocategoryModel> ret = new ArrayList<PrjTodocategoryModel>();
        PrjTodocategoryModel defMdl = new PrjTodocategoryModel();
        defMdl.setPtcName(textNo);
        ret.add(defMdl);

        List<PrjTodocategoryModel> retDbList = dao.selectProjects(prjSid);
        if (!retDbList.isEmpty()) {
            for (PrjTodocategoryModel category : retDbList) {
                ret.add(category);
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 状態リストを取得する
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return ret 状態リスト
     * @throws SQLException SQL実行例外
     */
    private List<PrjTodostatusModel> __getStatus(int prjSid) throws SQLException {

        PrjTodostatusDao dao = new PrjTodostatusDao(con_);
        List<PrjTodostatusModel> ret = dao.selectProjects(prjSid);

        return ret;
    }

    /**
     * <br>[機  能] プロジェクトメンバリストを取得する
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return ret プロジェクトメンバリスト
     * @throws SQLException SQL実行例外
     */
    private List<UserModel> __getMember(int prjSid) throws SQLException {

        PrjMembersDao dao = new PrjMembersDao(con_);
        List<UserModel> ret = dao.getMemberList(prjSid, false);

        return ret;
    }

    /**
     * <br>[機  能] マイプロジェクト区分を取得する
     * <br>[備  考]
     *
     * @param paramMdl Prj160ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setPrjMyKbn(Prj160ParamModel paramMdl) throws SQLException {

        PrjPrjdataDao dao = new PrjPrjdataDao(con_);
        ProjectItemModel model = dao.getProjectInfo(paramMdl.getPrj160PrjSid());
        int prjMyKbn = model.getPrjMyKbn();
        paramMdl.setPrj160PrjMyKbn(prjMyKbn);
    }
}