package jp.groupsession.v2.prj.prj160kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjTodoBinModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj160knBiz {

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
    public Prj160knBiz(RequestModel reqMdl, Connection con) {
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
     * @param paramMdl Prj160knParamModel
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(Prj160knParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        String importFileName = "";

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

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                importFileName = fMdl.getFileName();
            }
        }
        paramMdl.setPrj160knImportFileName(importFileName);

        //取り込みプロジェクト名称取得
        String importProjectName = "";
        PrjPrjdataDao prjDataDao = new PrjPrjdataDao(con_);
        PrjPrjdataModel prjDataMdl = prjDataDao.getProjectData(
                paramMdl.getPrj160PrjSid());

        if (prjDataMdl != null) {
            importProjectName = prjDataMdl.getPrjName();
        }

        paramMdl.setPrj160knTargetProjectName(importProjectName);

        //取り込み実行ユーザ名称取得
        String importUsrName = "";
        BaseUserModel usrMdl = __getSessionUserModel();
        if (usrMdl != null) {
            importUsrName = usrMdl.getUsisei() + "  " + usrMdl.getUsimei();
        }

        paramMdl.setPrj160knImportUserName(importUsrName);
    }

    /**
     * <br>[機  能] TODO情報をリセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void resetTodo(int prjSid, int userSid) throws SQLException {

        //TODO情報に紐付く添付ファイル情報を取得する
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(con_);
        List<PrjTodoBinModel> ptbList = ptbDao.getBinList(prjSid);

        List<Long> binList = new ArrayList<Long>();
        for (PrjTodoBinModel ptbMdl : ptbList) {
            binList.add(ptbMdl.getBinSid());
        }

        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(userSid);
        cbMdl.setBinUpdate(new UDate());

        //TODO情報を削除する
        ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con_);
        projectBiz.deleteTodoPrj(cbMdl, binList, prjSid);
    }

    /**
     * <br>[機  能] カテゴリマッピングを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return categoryMap カテゴリマッピング
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, PrjTodocategoryModel> getCategoryMap(int prjSid)
        throws SQLException {

        PrjTodocategoryDao categoryDao = new PrjTodocategoryDao(con_);
        HashMap<String, PrjTodocategoryModel> categoryMap =
            categoryDao.selectProjectsMap(prjSid);

        return categoryMap;
    }

    /**
     * <br>[機  能] 状態マッピングを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return statusMap 状態マッピング
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, PrjTodostatusModel> getStatusMap(int prjSid)
        throws SQLException {

        PrjTodostatusDao statusDao = new PrjTodostatusDao(con_);
        HashMap<String, PrjTodostatusModel> statusMap =
            statusDao.selectProjectsMap(prjSid);

        return statusMap;
    }

    /**
     * <br>[機  能] プロジェクトメンバーマッピングを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return memberMap プロジェクトメンバーマッピング
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, PrjMembersModel> getMemberMap(int prjSid)
        throws SQLException {

        PrjMembersDao memberDao = new PrjMembersDao(con_);
        HashMap<String, PrjMembersModel> memberMap =
            memberDao.selectProjectsMap(prjSid);

        return memberMap;
    }
}