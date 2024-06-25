package jp.groupsession.v2.man.man480;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.man440.Man440ParamModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] CybozuLive Todoリストインポート ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man480Biz {
    /** リクエストモデル*/
    private RequestModel reqMdl__;
    /** コネクション*/
    private Connection con__;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man480Biz.class);
    /**
     *
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Man480Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 描画設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     */
    public void doDsp(Man480ParamModel param, String tempDir) throws Exception {
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        //画面に表示するファイルのリストを作成
        List<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();
        if (fileList != null && fileList.size() > 0) {
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
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        } else {
            param.setMan480fileOk(false);
        }
        param.setMan480FileLabelList(fileLblList);

        param.setMan480grpName(__getGroupName(param));

        param.setMan480projectLabelList(__getProjectLabelList());

        if (param.isMan480fileOk()) {
            LinkedHashMap<String, Integer> svStatusMap = new LinkedHashMap<>();
            ArrayList<LabelValueBean> svStatusList = new ArrayList<>();
            if (param.getMan480selectProject() > 0
                    && param.getMan480mode() == GSConst.CMDMODE_EDIT) {
                PrjTodostatusDao pts = new PrjTodostatusDao(con__);
                List<PrjTodostatusModel> todoStatusList =
                        pts.selectProjects(param.getMan480selectProject());
                for (PrjTodostatusModel mdl : todoStatusList) {
                    if (mdl.getPtsSid() == GSConstProject.STATUS_0
                            || mdl.getPtsSid() == GSConstProject.STATUS_100) {
                        continue;
                    }
                    if (!svStatusMap.containsKey(mdl.getPtsName())) {
                        svStatusMap.put(mdl.getPtsName(), mdl.getPtsRate());
                        svStatusList.add(
                                new LabelValueBean(mdl.getPtsName(),
                                        String.valueOf(mdl.getPtsRate())));
                    }
                }
            }
            param.setMan480svStatusLabelList(svStatusList);
            CybTodoCsvImport csvImp = new CybTodoCsvImport(null,
                    reqMdl__,
                    con__, null, -1);
            Set<String> readStatusSet = csvImp.readStatusSet(tempDir);

            //既存ステータスとの重複を除去
            Set<String> statusSet = new HashSet<String>();
            for (String status : readStatusSet) {
                if (!svStatusMap.containsKey(status)) {
                    statusSet.add(status);
                }
            }
            param.setMan480statusNames(statusSet.toArray(new String[statusSet.size()]));
            param.setMan480impCount(csvImp.getCount());
        }

    }

    /**
     * <br>[機  能] グループ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Man440ParamModel
     * @throws SQLException SQL実行時例外
     * @return グループ名
     */
    private String __getGroupName(Man440ParamModel paramMdl) throws SQLException {
        GroupDao grpDao = new GroupDao(con__);
        CmnGroupmModel grpMdl = grpDao.getGroup(paramMdl.getMan440GrpSid());
        if (grpMdl != null) {
            return grpMdl.getGrpName();
        }
        return null;
    }
    /**
     * <br>[機  能] プロジェクトラベル一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @return プロジェクトラベル一覧
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ArrayList<LabelValueBean> __getProjectLabelList()
            throws SQLException {

        ArrayList<LabelValueBean> projectLabelList = new ArrayList<LabelValueBean>();

        List<PrjPrjdataModel> projectList = new ArrayList<PrjPrjdataModel>();

        PrjPrjdataDao prjDao = new PrjPrjdataDao(con__);
        projectList = prjDao.select();

        // プロジェクト一覧から通常プロジェクト名のみを抽出してリスト化
        for (PrjPrjdataModel mdl : projectList) {
            if (mdl.getPrjMyKbn() == GSConstProject.KBN_MY_PRJ_DEF) {
                LabelValueBean label = new LabelValueBean(
                        mdl.getPrjName(),
                        String.valueOf(mdl.getPrjSid()));
                projectLabelList.add(label);
            }
        }
        return projectLabelList;
    }
    /**
     *
     * <br>[機  能] 登録処理の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @param cntCon 採番コントローラー
     * @param tempDir テンポラリディレクトリ
     * @return 登録行数
     * @throws Exception 実行時例外
     */
    public long doInsert(Man480ParamModel param,
            MlCountMtController cntCon,
            String tempDir) throws Exception {
        int prjSid;
        int userSid = reqMdl__.getSmodel().getUsrsid();
        UDate now = new UDate();
        //入力されたTODO状態の取得
        CybTodoCsvImport csvImp = new CybTodoCsvImport(null,
                reqMdl__,
                con__, null, -1);
        Set<String> statusNameSet = csvImp.readStatusSet(tempDir);
        Map<String, Integer> statusNameSidMap = new HashMap<>();
        for (String name : statusNameSet) {
            statusNameSidMap.put(name, -1);
        }

        if (param.getMan480mode() == GSConst.CMDMODE_ADD) {
            //プロジェクトSID採番
            prjSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                                                           GSConstProject.SBNSID_SUB_PROJECT,
                                                           userSid);
            /** プロジェクト情報モデル*/
            PrjPrjdataModel ppMdl = __getPrjInsertMdl(prjSid, param, userSid,
                                                      now);
            /** プロジェクト付加情報モデル*/
            ProjectStatusModel prjStatus = new ProjectStatusModel();

            __initPrjInsertOption(prjSid, prjStatus, param, statusNameSidMap);

            /** プロジェクトメンバー更新Modelリスト*/
            List<PrjMembersModel> memberList =
                __getMemberUpdateList(prjSid, param, userSid, now);

            //プロジェクト情報を登録する
            ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);
            projectDao.insertProject(ppMdl, prjStatus, memberList);

        } else {
            prjSid = param.getMan480selectProject();
            /** プロジェクト情報モデル*/
            PrjPrjdataModel ppMdl = __getPrjUpdateMdl(prjSid);

            /** プロジェクト付加情報モデル*/
            ProjectStatusModel prjStatus = new ProjectStatusModel();

            __initPrjUpdateOption(prjSid, prjStatus, param, statusNameSidMap);

            List<PrjTodostatusModel> todoStatusList = prjStatus.getTodoStatusList();
            HashMap<String, String> updateStatus = prjStatus.getUpdateStatus();

            //プロジェクト情報を更新する
            ProjectUpdateDao prjUpDao = new ProjectUpdateDao(con__);
            //TODO情報のTODO状態を更新する
            prjUpDao.updateStatus(updateStatus, ppMdl);
            //TODO変更履歴のTODO状態を更新する
            prjUpDao.updateTodoHisStatus(updateStatus, ppMdl);
            //TODO状態を登録・更新する
            prjUpDao.updateTodoStatus(ppMdl, todoStatusList);
            //TODO状態がマスタに存在しない場合、TODO情報のTODO状態を0%に更新する
            prjUpDao.updateStatusNotExists(ppMdl.getPrjEuid(),
                    ppMdl.getPrjEdate(), ppMdl.getPrjSid());
            //TODO状態がマスタに存在しない場合、TODO変更履歴のTODO状態を0%に更新する
            prjUpDao.updateTodoHisStatusNotExists(
                    ppMdl.getPrjEuid(), ppMdl.getPrjEdate(), ppMdl.getPrjSid());
        }

        csvImp = new CybTodoCsvImport(null,
                reqMdl__,
                con__,
                statusNameSidMap,
                prjSid
                );
        return csvImp.importCsv(tempDir);
    }
    /**
     *
     * <br>[機  能] 更新用 プロジェクト付加情報を生成
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param prjStatusMdl プロジェクト付加情報設定先 実行後割り当てられた状態情報が設定
     * @param param パラメータ
     * @param statusNameSidMap TODO状態名SIDマップ 実行後割り当てられたSIDが設定される
     * @throws Exception 実行時例外
     */
    private void __initPrjUpdateOption(int prjSid,
            ProjectStatusModel prjStatusMdl,
            Man480ParamModel param,
            Map<String, Integer> statusNameSidMap) throws Exception {

        //TODO状態を取得
        PrjTodostatusDao pts = new PrjTodostatusDao(con__);
        List<PrjTodostatusModel> todoStatusList = pts.selectProjects(prjSid);

        Set<String> removeSet = new HashSet<String>();
        for (PrjTodostatusModel mdl : todoStatusList) {
            removeSet.add(mdl.getPtsName());
            if (statusNameSidMap.containsKey(mdl.getPtsName())) {
                //既存TODO状態のSIDを設定
                statusNameSidMap.put(mdl.getPtsName(), mdl.getPtsSid());
            }
        }
        //入力されたTODO状態を進捗順に整理
        TreeMap<Integer, List<PrjTodostatusModel>> addMap = new TreeMap<>();
        int idx = 0;
        for (String statusName : statusNameSidMap.keySet()) {
            //入力されたTODO状態とTODO状態との重複を除去
            if (removeSet.contains(statusName)) {
                continue;
            }
            int rate = NullDefault.getInt(
                    param.getMan480statusValueMap().get(
                            String.valueOf(idx)), 0);
            PrjTodostatusModel ptsMdl = new PrjTodostatusModel();
            //進捗と名称のみ設定し、SIDと並び順は後の走査時に設定する
            ptsMdl.setPtsName(statusName);
            ptsMdl.setPtsRate(rate);
            List<PrjTodostatusModel> rateGrp = new ArrayList<>();
            if (addMap.containsKey(rate)) {
                rateGrp = addMap.get(rate);
            }
            rateGrp.add(ptsMdl);
            addMap.put(rate, rateGrp);
            idx++;
        }


        //先頭の状態情報を設定(0%)
        PrjTodostatusModel firstPts = todoStatusList.get(0);
        firstPts.setPtsSort(1);
        firstPts.setPtsName("未着手");

        //最後尾の状態情報を設定(100%)
        PrjTodostatusModel maxPts = todoStatusList.get(todoStatusList.size() - 1);
        int maxSort = statusNameSidMap.size() + todoStatusList.size();
        maxPts.setPtsSort(maxSort);
        maxPts.setPtsName("完了");

        //sidの最大値を取得
        int maxSid = maxPts.getPtsSid();
        for (PrjTodostatusModel svmdl : todoStatusList) {
            if (svmdl.getPtsSid() > maxSid) {
                maxSid = svmdl.getPtsSid();
            }
        }
        maxSid++;
        int sort = GSConstProject.STATUS_0;

        List<Entry<Integer, List<PrjTodostatusModel>>> addList
           = new ArrayList<>(addMap.entrySet());
        ListIterator<PrjTodostatusModel> lit
           = todoStatusList.listIterator();
        ListIterator<Entry<Integer, List<PrjTodostatusModel>>> lit2
           = addList.listIterator();

        PrjTodostatusModel mdl = lit.next();
        Entry<Integer, List<PrjTodostatusModel>> entry;
        if (lit2.hasNext()) {
            entry = lit2.next();
        } else {
            entry = null;
        }
        while (mdl != null) {
            //追加するTodo状態の進捗がmdlより小さいのでmdlの前に挿入
            if (entry != null
                    && mdl.getPtsRate() > entry.getKey()) {
                ListIterator<PrjTodostatusModel> addLit
                  = entry.getValue().listIterator();
                while (addLit.hasNext()) {
                    //追加するTODO状態にSIDと並び順を設定
                    PrjTodostatusModel addPts = addLit.next();
                    addPts.setPtsSid(maxSid);
                    addPts.setPtsRate(entry.getKey());
                    addPts.setPtsSort(sort);
                    lit.add(addPts);
                    statusNameSidMap.put(addPts.getPtsName(), maxSid);


                    maxSid++;
                    sort++;
                }

                if (lit2.hasNext()) {
                    entry = lit2.next();
                } else {
                    entry = null;
                }
                continue;
            }

            //mdlの進捗の方が大きいのでmdlの並び順を決定
            mdl.setPtsSort(sort);
            sort++;

            if (lit.hasNext()) {
                mdl = lit.next();
            } else {
                mdl = null;
            }
        }

        //TODO状態をset
        prjStatusMdl.setTodoStatusList(todoStatusList);
    }
    /**
     *
     * <br>[機  能] 更新対象 プロジェクト情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @return プロジェクト情報
     * @throws SQLException SQL実行時例外
     */
    private PrjPrjdataModel __getPrjUpdateMdl(
            int prjSid) throws SQLException {
        PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
        PrjPrjdataModel ret = ppDao.getProjectData(prjSid);

        return ret;
    }
    /**
     *
     * <br>[機  能] 更新用のPrjPrjdataModelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param param パラメータ
     * @param userSid sessionUsrSid
     * @param now 現在時間
     * @return PrjPrjdataModel
     */
    private PrjPrjdataModel __getPrjInsertMdl(int prjSid,
            Man480ParamModel param, int userSid, UDate now
            ) {
        UDate startDate = PrjCommonBiz.createUDate(
                NullDefault.getInt(now.getStrYear(), -1),
                NullDefault.getInt(now.getStrMonth(), -1),
                NullDefault.getInt(now.getStrDay(), -1));
        UDate andDate = PrjCommonBiz.createUDate(
                NullDefault.getInt(now.getStrYear(), -1),
                NullDefault.getInt(now.getStrMonth(), -1),
                NullDefault.getInt(now.getStrDay(), -1));

        PrjPrjdataModel ppMdl = new PrjPrjdataModel();
        ppMdl.setPrjSid(prjSid);
        ppMdl.setPrjMyKbn(GSConstProject.KBN_MY_PRJ_DEF);
        ppMdl.setPrjId(param.getMan480projectID());
        ppMdl.setPrjName(param.getMan480projectName());
        ppMdl.setPrjNameShort(param.getMan480projectShortName());
        ppMdl.setPrjYosan(0);
        ppMdl.setPrjKoukaiKbn(GSConstProject.KBN_KOUKAI_DISABLED);
        ppMdl.setPrjDateStart(startDate);
        ppMdl.setPrjDateEnd(andDate);
        ppMdl.setPrjStatusSid(GSConstProject.STATUS_0);
        ppMdl.setPrjTarget("");
        ppMdl.setPrjContent("");
        ppMdl.setPrjEdit(GSConstProject.TODO_EDIT_KENGEN_MEM);
        ppMdl.setPrjMailKbn(GSConstProject.TODO_MAIL_FREE);
        ppMdl.setPrjAuid(userSid);
        ppMdl.setPrjAdate(now);
        ppMdl.setPrjEuid(userSid);
        ppMdl.setPrjEdate(now);


        Long binSid = new Long(0);
        ppMdl.setBinSid(binSid);
        return ppMdl;
    }
    /**
     *
     * <br>[機  能] メンバーリストを登録グループから生成
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param param パラメータ
     * @param userSid ユーザSID
     * @param now 現在時間
     * @return メンバーリスト
     * @throws SQLException 実行時例外
     */
    private List<PrjMembersModel> __getMemberUpdateList(int prjSid,
            Man480ParamModel param, int userSid, UDate now) throws SQLException {
        List<PrjMembersModel> memberList = new ArrayList<PrjMembersModel>();
        //所属ユーザ取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> userList
            = userBiz.getBelongUserList(con__, param.getMan440GrpSid(), null);

        if (userList != null && userList.size() > 0) {
            for (CmnUsrmInfModel member : userList) {
                int adminKbn = GSConstProject.KBN_POWER_NORMAL;
                PrjMembersModel memberMdl = new PrjMembersModel();
                memberMdl.setPrjSid(prjSid);
                memberMdl.setUsrSid(member.getUsrSid());
                memberMdl.setPrmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                memberMdl.setPrmAdminKbn(adminKbn);
                memberMdl.setPrmAuid(userSid);
                memberMdl.setPrmAdate(now);
                memberMdl.setPrmEuid(userSid);
                memberMdl.setPrmEdate(now);
                memberMdl.setPrmMemKey("");
                memberList.add(memberMdl);
            }
        }
        return memberList;
    }
    /**
     *
     * <br>[機  能] プロジェクト付加情報の初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param prjStatus プロジェクト付加情報設定先 実行後割り当てられた状態情報が設定
     * @param param パラメータ
     * @param statusNameSidMap TODO状態名SIDマップ 実行後割り当てられたSIDが設定される
     * @throws Exception 実行時例外
     */
    private void __initPrjInsertOption(int prjSid, ProjectStatusModel prjStatus,
            Man480ParamModel param, Map<String, Integer> statusNameSidMap) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //予定
        String textYotei = gsMsg.getMessage("project.prj010.8");
        //完了
        String textCmoplete = gsMsg.getMessage("cmn.complete");

        //プロジェクト状態
        List<PrjPrjstatusModel> prjStatusList = new ArrayList<PrjPrjstatusModel>();
        PrjPrjstatusModel ppsMdl = new PrjPrjstatusModel();
        ppsMdl.setPrsSid(GSConstProject.STATUS_0);
        ppsMdl.setPrsSort(1);
        ppsMdl.setPrsName(textYotei);
        ppsMdl.setPrsRate(GSConstProject.RATE_MIN);
        prjStatusList.add(ppsMdl);

        ppsMdl = new PrjPrjstatusModel();
        ppsMdl.setPrsSid(GSConstProject.STATUS_100);
        ppsMdl.setPrsSort(2);
        ppsMdl.setPrsName(textCmoplete);
        ppsMdl.setPrsRate(GSConstProject.RATE_MAX);
        prjStatusList.add(ppsMdl);

        //TODO状態
        List<PrjTodostatusModel> todoStatusList = new ArrayList<PrjTodostatusModel>();
        int sort = 1;
        int maxSid = GSConstProject.STATUS_100 + 1;

        PrjTodostatusModel ptsMdl = new PrjTodostatusModel();
        ptsMdl.setPtsSid(GSConstProject.STATUS_0);
        ptsMdl.setPtsSort(sort);
        ptsMdl.setPtsName("未着手");
        ptsMdl.setPtsRate(GSConstProject.RATE_MIN);
        todoStatusList.add(ptsMdl);
        sort++;

        //TODO状態は進捗順に並ぶ必要がある
        TreeMap<Integer, List<PrjTodostatusModel>> addMap = new TreeMap<>();
        int idx = 0;
        for (String statusName : statusNameSidMap.keySet()) {
            int rate = NullDefault.getInt(
                    param.getMan480statusValueMap().get(
                            String.valueOf(idx)), 0);
            ptsMdl = new PrjTodostatusModel();
            //進捗と名称のみ設定し、SIDと並び順は後の走査時に設定する
            ptsMdl.setPtsName(statusName);
            ptsMdl.setPtsRate(rate);
            List<PrjTodostatusModel> rateGrp = new ArrayList<>();
            if (addMap.containsKey(rate)) {
                rateGrp = addMap.get(rate);
            }
            rateGrp.add(ptsMdl);
            addMap.put(rate, rateGrp);
            idx++;
        }
        for (Entry<Integer, List<PrjTodostatusModel>> entry : addMap.entrySet()) {
            List<PrjTodostatusModel> rateGrp = entry.getValue();
            for (PrjTodostatusModel mdl : rateGrp) {
                ptsMdl = mdl;
                ptsMdl.setPtsSid(maxSid);
                ptsMdl.setPtsSort(sort);
                todoStatusList.add(ptsMdl);
                statusNameSidMap.put(ptsMdl.getPtsName(), maxSid);
                maxSid++;
                sort++;
            }
        }
        ptsMdl = new PrjTodostatusModel();
        ptsMdl.setPtsSid(GSConstProject.STATUS_100);
        ptsMdl.setPtsSort(sort);
        ptsMdl.setPtsName(textCmoplete);
        ptsMdl.setPtsRate(GSConstProject.RATE_MAX);
        todoStatusList.add(ptsMdl);
        //TODO状態をset
        prjStatus.setTodoStatusList(todoStatusList);

        //プロジェクト状態をset
        prjStatus.setPrjStatusList(prjStatusList);
    }

    /**
     * <br>[機  能] プロジェクト名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sid SID
     * @param con コネクション
     * @return プロジェクト名
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public String getProjectName(int sid, Connection con)
            throws SQLException {

        PrjPrjdataDao prjDao = new PrjPrjdataDao(con);
        PrjPrjdataModel mdl = prjDao.getProjectData(sid);
        return mdl.getPrjName();
    }
}
