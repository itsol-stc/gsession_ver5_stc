package jp.groupsession.v2.prj.prj160kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjStatusHistoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodomemberModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート確認画面 TODOインポートを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TodoCsvImport extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TodoCsvImport.class);
    /** コネクション */
    private Connection con__ = null;
    /** 採番コネクション */
    private MlCountMtController cntCon__ = null;
    /** プロジェクトSID */
    private int prjSid__ = -1;
    /** マイプロジェクト区分 */
    private int prjMyKbn__;
    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 現在日時 */
    private UDate now__ = null;
    /** TODOカテゴリマスタマッピング */
    private HashMap<String, PrjTodocategoryModel> categoryMap__;
    /** TODO状態マスタマッピング */
    private HashMap<String, PrjTodostatusModel> statusMap__;
    /** プロジェクトメンバーマッピング */
    private HashMap<String, PrjMembersModel> memberMap__;
    /** 全て未入力の場合は文字列 */
    private static final String NO_INPUT_STRING = ",,,,,,,,,,,,,";

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>cntCon を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * <p>cntCon をセットします。
     * @param cntCon cntCon
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }
    /**
     * <p>prjSid を取得します。
     * @return prjSid
     */
    public int getPrjSid() {
        return prjSid__;
    }
    /**
     * <p>prjSid をセットします。
     * @param prjSid prjSid
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>now を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now をセットします。
     * @param now now
     */
    public void setNow(UDate now) {
        now__ = now;
    }
    /**
     * <p>categoryMap を取得します。
     * @return categoryMap
     */
    public HashMap<String, PrjTodocategoryModel> getCategoryMap() {
        return categoryMap__;
    }
    /**
     * <p>categoryMap をセットします。
     * @param categoryMap categoryMap
     */
    public void setCategoryMap(HashMap<String, PrjTodocategoryModel> categoryMap) {
        categoryMap__ = categoryMap;
    }
    /**
     * <p>memberMap を取得します。
     * @return memberMap
     */
    public HashMap<String, PrjMembersModel> getMemberMap() {
        return memberMap__;
    }
    /**
     * <p>memberMap をセットします。
     * @param memberMap memberMap
     */
    public void setMemberMap(HashMap<String, PrjMembersModel> memberMap) {
        memberMap__ = memberMap;
    }
    /**
     * <p>statusMap を取得します。
     * @return statusMap
     */
    public HashMap<String, PrjTodostatusModel> getStatusMap() {
        return statusMap__;
    }
    /**
     * <p>statusMap をセットします。
     * @param statusMap statusMap
     */
    public void setStatusMap(HashMap<String, PrjTodostatusModel> statusMap) {
        statusMap__ = statusMap;
    }
    /** リクエスト */
    protected HttpServletRequest req__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param cntCon 採番用コネクション
     * @param model TodoCsvImportConstModel
     * @param req リクエスト
     */
    public TodoCsvImport(Connection con,
                          MlCountMtController cntCon,
                          TodoCsvImportConstModel model,
                          HttpServletRequest req) {

        setCon(con);
        setCntCon(cntCon);
        setPrjSid(model.getPrjSid());
        setPrjMyKbn(model.getPrjMyKbn());
        setUserSid(model.getUsrSid());
        setCategoryMap(model.getCategoryMap());
        setStatusMap(model.getStatusMap());
        setMemberMap(model.getMemberMap());
        req__ = req;
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     * @return num 取り込み件数
     */
    public long importCsv(String tmpFileDir) throws Exception {

        //現在日時設定
        setNow(new UDate());

        //テンポラリディレクトリにあるファイル情報を取得
        String saveFileName = "";
        List<String> fileList = IOTools.getFileNames(tmpFileDir);

        for (int i = 0; i < fileList.size(); i++) {

            //ファイル名を取得
            String fileName = fileList.get(i);
            if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                continue;
            }

            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tmpFileDir, fileName);
            Object fObj = objFile.load();
            if (fObj == null) {
                continue;
            }

            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            saveFileName = fMdl.getSaveFileName();
        }

        String csvFile = tmpFileDir + saveFileName;

        //ファイル取込
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        return num;
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader
     * @see #processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //1行目はヘッダなのでバイパス
        if (num > 1) {

            try {

                //未入力列
                if (lineStr.equals(NO_INPUT_STRING)) {
                    return;
                }

                int i = 0;

                //CSVインポートモデル
                TodoCsvImportModel mdl = new TodoCsvImportModel();

                while (stringTokenizer.hasMoreTokens()) {

                    i++;
                    buff = stringTokenizer.nextToken();

                    //タイトル
                    if (i == 1) {
                        String title = "";
                        //改行コードを空文字に変換
                        title = StringUtil.replaceReturnCode(NullDefault.getString(buff, ""),
                                                             "");
                        mdl.setTitle(title);
                    }

                    //カテゴリ
                    if (i == 2) {
                        mdl.setCategory(NullDefault.getString(buff, ""));
                    }

                    //予定 開始
                    if (i == 3) {
                        mdl.setYoteiFr(NullDefault.getString(buff, ""));
                    }

                    //予定 終了
                    if (i == 4) {
                        mdl.setYoteiTo(NullDefault.getString(buff, ""));
                    }

                    //予定 工数
                    if (i == 5) {
                        mdl.setYoteiKosu(NullDefault.getString(buff, ""));
                    }

                    //実績 開始
                    if (i == 6) {
                        mdl.setZissekiFr(NullDefault.getString(buff, ""));
                    }

                    //実績 終了
                    if (i == 7) {
                        mdl.setZissekiTo(NullDefault.getString(buff, ""));
                    }

                    //実績 工数
                    if (i == 8) {
                        mdl.setZissekiKosu(NullDefault.getString(buff, ""));
                    }

                    //内容
                    if (i == 9) {
                        mdl.setNaiyo(NullDefault.getString(buff, ""));
                    }

                    //担当
                    if (i == 10) {
                        ArrayList<String> memberList = new ArrayList<String>();
                        if (!StringUtil.isNullZeroString(buff)) {

                            String[] splitMember = buff.split("\n");

                            for (String member : splitMember) {
                                memberList.add(member);
                            }
                        }

                        mdl.setTanto(memberList);
                    }

                    //重要度
                    if (i == 11) {
                        mdl.setPriority(NullDefault.getString(buff, ""));
                    }

                    //警告開始
                    if (i == 12) {
                        mdl.setCaution(NullDefault.getString(buff, ""));
                    }

                    //状態
                    if (i == 13) {
                        mdl.setStatus(NullDefault.getString(buff, ""));
                    }

                    //状態変更理由
                    if (i == 14) {
                        mdl.setHistory(NullDefault.getString(buff, ""));
                    }
                }

                //TODOSID採番
                int todoSid =
                    (int) cntCon__.getSaibanNumber(
                            GSConstProject.SBNSID_PROJECT,
                            GSConstProject.SBNSID_SUB_TODO,
                            userSid__);

                //管理番号を採番
                ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);
                int kanriNo =
                    (int) cntCon__.getSaibanNumber(
                            GSConstProject.SBNSID_PROJECT,
                            prjSid__ + GSConstProject.SBNSID_SUB_KANRI,
                            userSid__);

                //変更履歴SIDを採番
                int hisSid =
                    (int) cntCon__.getSaibanNumber(
                            GSConstProject.SBNSID_PROJECT,
                            todoSid + GSConstProject.SBNSID_SUB_HISTORY,
                            userSid__);

                //TODO情報
                PrjTododataModel todoMdl =
                    __getPrjTododataModel(mdl, todoSid, kanriNo, hisSid);

                //TODOメンバー
                List<PrjTodomemberModel> memberList =
                    __getPrjTodomemberList(mdl, todoSid);

                //TODO変更履歴
                PrjStatusHistoryModel historyMdl =
                    __getPrjStatusHistoryModel(mdl, todoSid, hisSid);

                //アップデート
                projectDao.insertTodo(todoMdl, memberList, historyMdl, null);

            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }
        }
    }

    /**
     * <br>[機  能] 更新用のPrjTododataModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean CSVデータモデル
     * @param todoSid TODOSID
     * @param kanriNo 管理番号
     * @param hisSid 変更履歴SID
     * @return PrjTododataModel 更新用のPrjTododataModel
     */
    private PrjTododataModel __getPrjTododataModel(TodoCsvImportModel bean,
                                                    int todoSid,
                                                    int kanriNo,
                                                    int hisSid) {

        //開始予定年月日
        UDate yoteiFrUd = null;
        if (!StringUtil.isNullZeroString(bean.getYoteiFr())) {
            String yoteiFrStr = bean.getYoteiFr();
            yoteiFrUd = new UDate();
            yoteiFrUd.setDate(
                Integer.parseInt(yoteiFrStr.substring(0, 4)),
                Integer.parseInt(yoteiFrStr.substring(5, yoteiFrStr.lastIndexOf("/"))),
                Integer.parseInt(yoteiFrStr.substring(yoteiFrStr.lastIndexOf("/") + 1)));
        }

        //期限年月日
        UDate yoteiToUd = null;
        if (!StringUtil.isNullZeroString(bean.getYoteiTo())) {
            String yoteiToStr = bean.getYoteiTo();
            yoteiToUd = new UDate();
            yoteiToUd.setDate(
                Integer.parseInt(yoteiToStr.substring(0, 4)),
                Integer.parseInt(yoteiToStr.substring(5, yoteiToStr.lastIndexOf("/"))),
                Integer.parseInt(yoteiToStr.substring(yoteiToStr.lastIndexOf("/") + 1)));
        }

        //開始実績年月日
        UDate zissekiFrUd = null;
        if (!StringUtil.isNullZeroString(bean.getZissekiFr())) {
            String zissekiFrStr = bean.getZissekiFr();
            zissekiFrUd = new UDate();
            zissekiFrUd.setDate(
                Integer.parseInt(zissekiFrStr.substring(0, 4)),
                Integer.parseInt(zissekiFrStr.substring(5, zissekiFrStr.lastIndexOf("/"))),
                Integer.parseInt(zissekiFrStr.substring(zissekiFrStr.lastIndexOf("/") + 1)));
        }

        //終了実績年月日
        UDate zissekiToUd = null;
        if (!StringUtil.isNullZeroString(bean.getZissekiTo())) {
            String zissekiToStr = bean.getZissekiTo();
            zissekiToUd = new UDate();
            zissekiToUd.setDate(
                Integer.parseInt(zissekiToStr.substring(0, 4)),
                Integer.parseInt(zissekiToStr.substring(5, zissekiToStr.lastIndexOf("/"))),
                Integer.parseInt(zissekiToStr.substring(zissekiToStr.lastIndexOf("/") + 1)));
        }


        PrjTododataModel ptMdl = new PrjTododataModel();
        ptMdl.setPrjSid(getPrjSid());
        ptMdl.setPtdSid(todoSid);
        ptMdl.setPtdNo(kanriNo);

        int category = -1;
        String categoryStr = bean.getCategory();

        if (!StringUtil.isNullZeroString(categoryStr)) {
            PrjTodocategoryModel categoryMdl =
                categoryMap__.get(categoryStr);

            if (categoryMdl != null) {
                category = categoryMdl.getPtcCategorySid();
            }
        }
        ptMdl.setPtdCategory(category);

        ptMdl.setPtdTitle(bean.getTitle());
        ptMdl.setPtdDatePlan(yoteiFrUd);
        ptMdl.setPrjDateLimit(yoteiToUd);
        ptMdl.setPtdDateStart(zissekiFrUd);
        ptMdl.setPtdDateEnd(zissekiToUd);

        ptMdl.setPtdPlanKosu(
                NullDefault.getBigDecimal(
                        bean.getYoteiKosu(), null));
        ptMdl.setPtdResultsKosu(
                NullDefault.getBigDecimal(
                        bean.getZissekiKosu(), null));

        int alarmKbn = GSConstProject.KEIKOKU_NO;
        String alarmStr = bean.getCaution();
        GsMessage gsMsg = new GsMessage();
        //1ヶ月前
        String textKeikokuNameBef30 = gsMsg.getMessage(req__, "project.src.8");
        //10日前
        String textKeikokuNameBef10 = gsMsg.getMessage(req__, "project.src.6");
        //5日前
        String textKeikokuNameBef5 = gsMsg.getMessage(req__, "project.src.9");
        //3日前
        String textKeikokuNameBef3 = gsMsg.getMessage(req__, "project.src.7");
        //1日前
        String textKeikokuNameBef1 = gsMsg.getMessage(req__, "project.src.5");

        if (!StringUtil.isNullZeroString(alarmStr)) {
            if (alarmStr.equals(textKeikokuNameBef1)) {
                alarmKbn = GSConstProject.KEIKOKU_BEF1;
            } else if (alarmStr.equals(textKeikokuNameBef3)) {
                alarmKbn = GSConstProject.KEIKOKU_BEF3;
            } else if (alarmStr.equals(textKeikokuNameBef5)) {
                alarmKbn = GSConstProject.KEIKOKU_BEF5;
            } else if (alarmStr.equals(textKeikokuNameBef10)) {
                alarmKbn = GSConstProject.KEIKOKU_BEF10;
            } else if (alarmStr.equals(textKeikokuNameBef30)) {
                alarmKbn = GSConstProject.KEIKOKU_BEF30;
            }
        }
        ptMdl.setPtdAlarmKbn(alarmKbn);

        //低
        String textLow = gsMsg.getMessage(req__, "project.58");
        //高
        String textHigh = gsMsg.getMessage(req__, "project.60");

        int priority = GSConstProject.WEIGHT_KBN_MIDDLE;
        String priorityStr = bean.getPriority();
        if (!StringUtil.isNullZeroString(priorityStr)) {
            if (priorityStr.equals(textLow)) {
                priority = GSConstProject.WEIGHT_KBN_LOW;
            } else if (priorityStr.equals(textHigh)) {
                priority = GSConstProject.WEIGHT_KBN_HIGH;
            }
        }
        ptMdl.setPtdImportance(priority);

        ptMdl.setPshSid(hisSid);

        int rate = GSConstProject.STATUS_0;
        String rateStr = bean.getStatus();
        if (!StringUtil.isNullZeroString(rateStr)) {
            PrjTodostatusModel statusMdl =
                statusMap__.get(rateStr);
            if (statusMdl != null) {
                rate = statusMdl.getPtsSid();
            }
        }

        ptMdl.setPtsSid(rate);
        ptMdl.setPtdContent(bean.getNaiyo());

        int usrSid = getUserSid();
        UDate now = getNow();

        ptMdl.setPtdAuid(usrSid);
        ptMdl.setPtdAdate(now);
        ptMdl.setPtdEuid(usrSid);
        ptMdl.setPtdEdate(now);

        return ptMdl;
    }

    /**
     * <br>[機  能] 更新用のPrjTodomemberModelのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean CSVデータモデル
     * @param todoSid TODOSID
     * @return List in PrjTodomemberModel
     */
    private List<PrjTodomemberModel> __getPrjTodomemberList(TodoCsvImportModel bean,
                                                             int todoSid) {

        int prjSid = getPrjSid();
        int usrSid = getUserSid();
        UDate now = getNow();
        List<PrjTodomemberModel> memberList = new ArrayList<PrjTodomemberModel>();

        PrjTodomemberModel memberMdl = null;
        ArrayList<String> member = bean.getTanto();

        if (prjMyKbn__ == GSConstProject.KBN_MY_PRJ_MY) {
            memberMdl = new PrjTodomemberModel();
            memberMdl.setPrjSid(prjSid);
            memberMdl.setPtdSid(todoSid);
            memberMdl.setUsrSid(usrSid);
            memberMdl.setPtmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
            memberMdl.setPtmAuid(usrSid);
            memberMdl.setPtmAdate(now);
            memberMdl.setPtmEuid(usrSid);
            memberMdl.setPtmEdate(now);
            memberList.add(memberMdl);
        } else if (!member.isEmpty()) {
            ArrayList<String> addList = new ArrayList<String>();
            for (String tanto : member) {
                if (memberMap__.containsKey(tanto)) {
                    if (addList.contains(tanto)) {
                        continue;
                    }
                    addList.add(tanto);
                    PrjMembersModel mapMdl = memberMap__.get(tanto);
                    memberMdl = new PrjTodomemberModel();
                    memberMdl.setPrjSid(prjSid);
                    memberMdl.setPtdSid(todoSid);
                    memberMdl.setUsrSid(mapMdl.getUsrSid());
                    memberMdl.setPtmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                    memberMdl.setPtmAuid(usrSid);
                    memberMdl.setPtmAdate(now);
                    memberMdl.setPtmEuid(usrSid);
                    memberMdl.setPtmEdate(now);
                    memberList.add(memberMdl);
                }
            }
        }

        return memberList;
    }

    /**
     * <br>[機  能] 更新用のPrjStatusHistoryModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean CSVモデル
     * @param todoSid TODOSID
     * @param hisSid 変更履歴SID
     * @return PrjStatusHistoryModel
     */
    private PrjStatusHistoryModel __getPrjStatusHistoryModel(TodoCsvImportModel bean,
                                                              int todoSid,
                                                              int hisSid) {

        int prjSid = getPrjSid();
        int usrSid = getUserSid();
        UDate now = getNow();

        PrjStatusHistoryModel pshMdl = new PrjStatusHistoryModel();
        pshMdl.setPrjSid(prjSid);
        pshMdl.setPtdSid(todoSid);
        pshMdl.setPshSid(hisSid);

        int rate = GSConstProject.STATUS_0;
        String rateStr = bean.getStatus();
        if (!StringUtil.isNullZeroString(rateStr)) {
            PrjTodostatusModel statusMdl =
                statusMap__.get(rateStr);
            if (statusMdl != null) {
                rate = statusMdl.getPtsSid();
            }
        }
        pshMdl.setPtsSid(rate);

        pshMdl.setPshReason(bean.getHistory());
        pshMdl.setPshAuid(usrSid);
        pshMdl.setPshAdate(now);
        pshMdl.setPshEuid(usrSid);
        pshMdl.setPshEdate(now);

        return pshMdl;
    }
    /**
     * <p>prjMyKbn を取得します。
     * @return prjMyKbn
     */
    public int getPrjMyKbn() {
        return prjMyKbn__;
    }
    /**
     * <p>prjMyKbn をセットします。
     * @param prjMyKbn prjMyKbn
     */
    public void setPrjMyKbn(int prjMyKbn) {
        prjMyKbn__ = prjMyKbn;
    }
}