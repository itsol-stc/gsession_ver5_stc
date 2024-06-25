package jp.groupsession.v2.man.man480;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man440.AbstractCybCsvImport;
import jp.groupsession.v2.man.man440.CybCsvCommentModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjTodocommentDao;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjStatusHistoryModel;
import jp.groupsession.v2.prj.model.PrjTodocommentModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodomemberModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] CybozuLive Todoリストインポート
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybTodoCsvImport extends AbstractCybCsvImport {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CybTodoCsvImport.class);
    /** 実行モード CSVデータインポート */
    public static final int MODE_READ_STATUS = 2;
    /** 実行モード CSVユーザリスト作成 */
    public static final int MODE_READ_USER = 3;
    /** 状態読み込み用 */
    private Set<String> statusSet__;
    /** CSV行数*/
    private long count__;
    /** CSV列定義    */
    public enum COLNO {
        /** ID*/
        ID,
        /** タイトル*/
        TITLE,
        /** 本文*/
        BODY,
        /** 作成者*/
        CREATE_USER,
        /** 作成日時*/
        CREATE_DATE,
        /** 更新者*/
        UPDATE_USER,
        /** 更新日時*/
        UPDATE_DATE,
        /** ステータス*/
        STATUS,
        /** 優先度*/
        YUSENDO,
        /** 担当者*/
        USER,
        /** 期日*/
        LIMITDATE,
        /** コメント*/
        COMMENT
    }

    /** csv列定義実態 モードごとの列の違いが反映された配列*/
    private COLNO[] colArr__;

    /**ユーザ 名前参照Map*/
    private Map<String, Integer> userNameMap__ = new HashMap<>();
    /**Todo状態 名前参照Map*/
    private Map<String, Integer> todoStsNameMap__ = new HashMap<>();
    /** プロジェクトSid*/
    private int prjSid__ = -1;




    /**
     * <br>[機 能] コンストラクタ
     * <br>[解 説]
     * <br>[備 考]
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param todoStatusMap todo状態Map
     * @param prjSid プロジェクトSID
     */
    public CybTodoCsvImport(ActionErrors error, RequestModel reqMdl,
            Connection con, Map<String, Integer> todoStatusMap, int prjSid) {
        super(error, reqMdl, con);
        EnumSet<COLNO> eset = EnumSet.allOf(COLNO.class);
        colArr__ = eset.toArray(new COLNO[eset.size()]);
        prjSid__ = prjSid;
        if (todoStatusMap != null) {
            todoStsNameMap__ = new HashMap<>(todoStatusMap);
            todoStsNameMap__.put("未着手", GSConstProject.STATUS_0);
            todoStsNameMap__.put("完了", GSConstProject.STATUS_100);
        }
    }

    /**
     * <br>[機 能] CSVファイルからstatusを洗い出す
     * <br>[解 説]
     * <br>[備 考]
     * @param tmpFileDir 入力ファイルフォルダ
     * @return true:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
    public Set<String> readStatusSet(String tmpFileDir) throws Exception {

        // モード指定
        mode__ = MODE_READ_STATUS;
        //テンポラリディレクトリにあるファイル名称を取得
        String csvFile = getCsvFilePath(tmpFileDir);
        File file = new File(csvFile);

        // ファイル読込み
        statusSet__ = new HashSet<String>();
        readFile(file, Encoding.WINDOWS_31J);
        return statusSet__;
    }
    @Override
    protected void __validateImportData() throws Exception {
    }

    @Override
    protected void __check(long num, String lineStr) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage("cmn.csv.number.items");
        //行目
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});
        //行目の
        String textLine2 = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});


        int j = 0;

        String buff = "";
        String eprefix = "inputFile.";
        int ecnt = errors__.size();

        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        log__.debug("項目数=" + stringTokenizer.length());
        COLNO[] colArr = (COLNO[]) colArr__;
        if (stringTokenizer.length() != colArr.length) {
            ActionMessage msg =
            new ActionMessage(
                    "error.input.format.file",
                    textCaptureFile,
                    textCsvitems + "(" + textLine + ")");
            StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
        } else {
            String fieldFix = String.valueOf(num) + "title.";
            String fieldTitle;
            ActionMessage msg;
            while (stringTokenizer.hasMoreTokens()) {
                buff = stringTokenizer.nextToken();

                if (buff != null && buff.equals("\"")) {
                    buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
                }

                switch (colArr[j]) {
                /* ID*/
                case ID:
                    break;
                /* タイトル*/
                case TITLE:
                    fieldTitle = textLine2 + "タイトル";
                    fieldFix = String.valueOf(num) + "title.";
                    //未入力チェック
                    if (StringUtil.isNullZeroString(buff)) {
                        msg = new ActionMessage("error.input.required.text", fieldTitle);
                        StrutsUtil.addMessage(errors__, msg, fieldFix);
                        break;
                    }
                    //スペース(改行)のみチェック
                    if (ValidateUtil.isSpaceOrKaigyou(buff)) {
                        msg = new ActionMessage("error.input.spase.cl.only", fieldTitle);
                        StrutsUtil.addMessage(errors__, msg, fieldFix);
                    }
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseString(buff);
                        msg = new ActionMessage(
                                        "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                           msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                /* 本文*/
                case BODY:
                    fieldTitle = textLine2 + gsMsg.getMessage("cmn.body");
                    fieldFix = String.valueOf(num) + "body.";
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                        msg = new ActionMessage(
                                "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                   msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                /* 作成者*/
                case CREATE_USER:
                    fieldTitle = textLine2 + "作成者";
                    fieldFix = String.valueOf(num) + "createUser.";
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseString(buff);
                        msg = new ActionMessage(
                                        "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                           msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                /* 作成日時*/
                case CREATE_DATE:
                    fieldTitle = textLine2 + "作成日時";
                    fieldFix = String.valueOf(num) + "createDate.";
                    __isOkDateTime(errors__, buff, fieldTitle, fieldFix);
                    break;
                /* 更新者*/
                case UPDATE_USER:
                    fieldTitle = textLine2 + "更新者";
                    fieldFix = String.valueOf(num) + "updateUser.";
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseString(buff);
                        msg = new ActionMessage(
                                        "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                           msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                /* 更新日時*/
                case UPDATE_DATE:
                    fieldTitle = textLine2 + "更新日時";
                    fieldFix = String.valueOf(num) + "updateDate.";
                    __isOkDateTime(errors__, buff, fieldTitle, fieldFix);
                    break;
                /* ステータス*/
                case STATUS:
                    fieldTitle = textLine2 + "ステータス";
                    fieldFix = String.valueOf(num) + "status.";
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseString(buff);
                        msg = new ActionMessage(
                                        "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                           msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                /* 優先度*/
                case YUSENDO:
                    break;
                /* 担当者*/
                case USER:
                    fieldTitle = textLine2 + "担当者";
                    fieldFix = String.valueOf(num) + "tanto.";
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseString(buff);
                        msg = new ActionMessage(
                                        "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                           msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                /* 期日*/
                case LIMITDATE:
                    fieldTitle = textLine2 + "期日";
                    fieldFix = String.valueOf(num) + "limit.";
                    if (!StringUtil.isNullZeroString(buff)) {
                        __isOkDateTime(errors__, buff, fieldTitle, fieldFix);
                    }
                    break;
                /* コメント*/
                case COMMENT:
                    fieldTitle = textLine2 + "コメント";
                    fieldFix = String.valueOf(num) + "comment.";
                    //JIS第2水準チェック
                    if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {
                        //利用不可能な文字を入力した場合
                        String nstr =
                                GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                        msg = new ActionMessage(
                                "error.input.njapan.text", fieldTitle, nstr);
                        StrutsUtil.addMessage(errors__,
                                   msg, fieldFix + "error.input.njapan.text");
                    }
                    break;
                default:
                    break;
                }

                j++;
            }

        }

        //エラー有り
        if (ecnt < errors__.size()) {
            //エラー存在フラグON
            setErrorFlg(true);
        } else {
            //明細データ以降
            if (num >= 2) {
                //有効データ件数カウントアップ
                int cnt = getCount();
                cnt += 1;
                setCount(cnt);
            }
        }

    }
    @Override
    public long importCsv(String tmpFileDir) throws Exception {
        // モード指定
        mode__ = MODE_READ_USER;
        //テンポラリディレクトリにあるファイル名称を取得
        String csvFile = getCsvFilePath(tmpFileDir);
        File file = new File(csvFile);

        // 参照ユーザ名読込み
        userNameMap__ = new HashMap<>();
        readFile(file, Encoding.WINDOWS_31J);

        //参照ユーザ名からユーザ情報を取得
        Man480Dao man480Dao = new Man480Dao(con__);
        man480Dao.setUserSidToNameMap(userNameMap__);
        return super.importCsv(tmpFileDir);
    }
    @Override
    protected void __import(long num, String lineStr) throws Exception {
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        COLNO[] colArr = (COLNO[]) colArr__;
        String buff = "";
        int j = 0;

        /** Todoモデル*/
        PrjTododataModel todoMdl = __initTodo();
        /** 担当者参照ユーザ*/
        List<Integer> tanto = new ArrayList<>();
        /** コメント*/
        Map<Integer, CybCsvCommentModel> commMap = new HashMap<>();

        while (stringTokenizer.hasMoreTokens()) {
            buff = stringTokenizer.nextToken();

            if (buff != null && buff.equals("\"")) {
                buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
            }
            switch (colArr[j]) {
            /* ID*/
            case ID:
                break;
            /* タイトル*/
            case TITLE:
                todoMdl.setPtdTitle(__changeGSText(buff, GSConstProject.MAX_LENGTH_TODO_TITLE));
                break;
            /* 本文*/
            case BODY:
                todoMdl.setPtdContent(__changeGSTextArea(buff, GSConstProject.MAX_LENGTH_CONTENT));
                break;
            /* 作成者*/
            case CREATE_USER:
                todoMdl.setPtdAuid(__getUsrSid(buff, true));
                break;
            /* 作成日時*/
            case CREATE_DATE:
                todoMdl.setPtdAdate(__getDateFromStr(buff));
                break;
            /* 更新者*/
            case UPDATE_USER:
                todoMdl.setPtdEuid(__getUsrSid(buff, true));
                break;
            /* 更新日時*/
            case UPDATE_DATE:
                todoMdl.setPtdEdate(__getDateFromStr(buff));
                break;
            /* ステータス*/
            case STATUS:
                todoMdl.setPtsSid(__getTodoStatussSid(buff));
                break;
            /* 優先度*/
            case YUSENDO:
                todoMdl.setPtdImportance(__getPtdImportance(buff));
                break;
            /* 担当者*/
            case USER:
                if (buff.indexOf(",") > 0) {
                    String[] userNames = buff.split(",");
                    for (String name : userNames) {
                        int usrSid = __getUsrSid(name, false);
                        if (usrSid > 0) {
                            tanto.add(usrSid);
                        }
                    }
                } else if (!StringUtil.isNullZeroString(buff)) {
                    int usrSid = __getUsrSid(buff, false);
                    if (usrSid > 0) {
                        tanto.add(usrSid);
                    }
                }
                break;
            /* 期日*/
            case LIMITDATE:
                //終了予定年月日
                UDate syuryo = null;

                if (!StringUtil.isNullZeroString(buff)) {
                    syuryo = __getDateFromStr(buff);
                }
                if (syuryo != null) {
                    syuryo.setZeroHhMmSs();
                } else {
                    syuryo = PrjCommonBiz.createUDate(
                            -1,
                            -1,
                            -1);
                }
                todoMdl.setPrjDateLimit(syuryo);
                break;
            /* コメント*/
            case COMMENT:
                List<CybCsvCommentModel> commList = __getCommentFromStr(buff);
                ListIterator<CybCsvCommentModel> lit = commList.listIterator(commList.size());
                //コメントは投稿の降順に並ぶため逆順に走査
                int i = 1;
                while (lit.hasPrevious()) {
                    CybCsvCommentModel comm = lit.previous();
                    commMap.put(i, comm);
                    i++;
                }
                break;
            default:
                break;
            }

            j++;
        }
        __insertTodo(todoMdl, tanto, commMap);
    }
    /**
     *
     * <br>[機  能] Todo情報の登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param todoMdl todoモデル
     * @param tanto メンバーSIDリスト
     * @param commMap コメントマップ
     * @throws SQLException SQL実行時例外
     */
    private void __insertTodo(PrjTododataModel todoMdl, List<Integer> tanto,
            Map<Integer, CybCsvCommentModel> commMap) throws SQLException {


        //更新用のPrjTodomemberModelのリストを取得する
        List<PrjTodomemberModel> memberList =
            __getPrjTodomemberList(todoMdl, reqMdl__.getSmodel().getUsrsid(), tanto);

        //更新用のPrjStatusHistoryModelを取得する
        PrjStatusHistoryModel pshMdl =
            __getPrjStatusHistoryModel(todoMdl, reqMdl__.getSmodel().getUsrsid());


        //TODO情報を登録する
        ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);
        projectDao.insertTodo(todoMdl, memberList, pshMdl, null);

        //TODOコメント情報を登録する
        PrjTodocommentDao ptcDao = new PrjTodocommentDao(con__);
        for (Entry<Integer, CybCsvCommentModel> entry : commMap.entrySet()) {
            CybCsvCommentModel comment = entry.getValue();
            int userSid = __getUsrSid(comment.getName(), true);

            //コメントが多い場合は分割
            List<String> commStrList = new ArrayList<>();
            String commentBase = comment.getValue();
            for (int i = 0; i < commentBase.length();
                    i += GSConstProject.MAX_LENGTH_TODO_CMT) {
                int ed = i + GSConstProject.MAX_LENGTH_TODO_CMT;
                if (ed > commentBase.length()) {
                    ed = commentBase.length();
                }

                commStrList.add(
                        commentBase.substring(i,  ed));
            }

            for (String commStr : commStrList) {
                //TODOコメントSIDを採番
                int todoCmtSid = (int) cntCon__.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                                  todoMdl.getPtdSid() + GSConstProject.SBNSID_SUB_COMMENT,
                                  reqMdl__.getSmodel().getUsrsid());
                PrjTodocommentModel ptcMdl = new PrjTodocommentModel();
                ptcMdl.setPrjSid(todoMdl.getPrjSid());
                ptcMdl.setPtdSid(todoMdl.getPtdSid());
                ptcMdl.setPcmSid(todoCmtSid);
                ptcMdl.setPcmComment(commStr);
                ptcMdl.setPcmAuid(userSid);
                ptcMdl.setPcmAdate(comment.getDate());
                ptcMdl.setPcmEuid(userSid);
                ptcMdl.setPcmEdate(comment.getDate());

                ptcDao.insert(ptcMdl);

            }

        }

    }
    /**
     * <br>[機  能] 更新用のPrjStatusHistoryModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoMdl TODOモデル
     * @param userSid ログインユーザSID
     * @return PrjStatusHistoryModel
     */
    private PrjStatusHistoryModel __getPrjStatusHistoryModel(
            PrjTododataModel todoMdl,
            int userSid) {

        //プロジェクトSID
        int projectSid = todoMdl.getPrjSid();
        int todoSid = todoMdl.getPtdSid();

        UDate now = new UDate();

        PrjStatusHistoryModel pshMdl = new PrjStatusHistoryModel();
        pshMdl.setPrjSid(projectSid);
        pshMdl.setPtdSid(todoSid);
        pshMdl.setPshSid(todoMdl.getPshSid());

        pshMdl.setPtsSid(todoMdl.getPtsSid());
        pshMdl.setPshReason("");
        pshMdl.setPshAuid(todoMdl.getPtdEuid());
        pshMdl.setPshAdate(now);
        pshMdl.setPshEuid(todoMdl.getPtdEuid());
        pshMdl.setPshEdate(now);
        return pshMdl;
    }
    /**
     * <br>[機  能] 更新用のPrjTodomemberModelのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoMdl TODOモデル
     * @param userSid ログインユーザSID
     * @param tanto メンバーSIDリスト
     * @return List in PrjTodomemberModel
     */
    private List<PrjTodomemberModel> __getPrjTodomemberList(
        PrjTododataModel todoMdl,
        int userSid,
        List<Integer> tanto) {

        //プロジェクトSID
        int projectSid = todoMdl.getPrjSid();
        int todoSid = todoMdl.getPtdSid();

        List<PrjTodomemberModel> memberList = new ArrayList<PrjTodomemberModel>();
        PrjTodomemberModel memberMdl = null;
        UDate now = new UDate();
        if (tanto != null) {
            for (Integer sid : tanto) {
                memberMdl = new PrjTodomemberModel();
                memberMdl.setPrjSid(projectSid);
                memberMdl.setPtdSid(todoSid);
                memberMdl.setUsrSid(sid);
                memberMdl.setPtmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                memberMdl.setPtmAuid(userSid);
                memberMdl.setPtmAdate(now);
                memberMdl.setPtmEuid(userSid);
                memberMdl.setPtmEdate(now);
                memberList.add(memberMdl);
            }
        }
        return memberList;
    }

    /**
     *
     * <br>[機  能] 優先度文字列から 重要度の値を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param buff 優先度文字列
     * @return 重要度
     */
    private int __getPtdImportance(String buff) {
        if (buff.equals("S")) {
            return GSConstProject.WEIGHT_KBN_HIGH;
        }
        if (buff.equals("A")) {
            return GSConstProject.WEIGHT_KBN_HIGH;
        }
        if (buff.equals("B")) {
            return GSConstProject.WEIGHT_KBN_MIDDLE;
        }
        if (buff.equals("C")) {
            return GSConstProject.WEIGHT_KBN_LOW;
        }
        if (buff.equals("D")) {
            return GSConstProject.WEIGHT_KBN_LOW;
        }
        return GSConstProject.WEIGHT_KBN_LOW;
    }

    /**
     *
     * <br>[機  能] 状態名からSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param buff 状態名
     * @return sid
     */
    private int __getTodoStatussSid(String buff) {
        if (todoStsNameMap__.containsKey(buff)) {
            return todoStsNameMap__.get(buff);
        }
        return GSConstProject.STATUS_0;
    }

    /**
     *
     * <br>[機  能] ユーザ名からSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param name ユーザ名
     * @param defaultSysUser 存在しない場合システムユーザを使うか
     * @return sid
     */
    private int __getUsrSid(String name, boolean defaultSysUser) {
        if (userNameMap__.containsKey(name)) {
            int userSid = userNameMap__.get(name);
            if (userSid < 0 && defaultSysUser) {
                return 0;
            }
            return userSid;
        }
        if (defaultSysUser) {
            return 0;
        }
        return -1;
    }

    /**
     *
     * <br>[機  能] GS登録用文字列に変換する
     * <br>[解  説] 文字数制限によるカット、タブ文字の変換を行う。
     * <br>[備  考]
     * @param buff 読み込みデータ
     * @param maxLength 最大文字数
     * @return 変換後文字列
     */
    private String __changeGSText(String buff, int maxLength) {
        String ret = buff;
        //改行文字の置き換え
        ret = ret.replaceAll("\r\n", " ");
        //タブ文字の置き換え
        ret = ret.replaceAll("\t", " ");
        //開始空白のトリム
        ret = ret.trim();

        if (ret.length() > maxLength) {
            ret = ret.substring(0, maxLength);
        }
        return ret;
    }
    /**
    *
    * <br>[機  能] GS登録用文字列に変換する
    * <br>[解  説] 文字数制限によるカットを行う。
    * <br>[備  考]
    * @param buff 読み込みデータ
    * @param maxLength 最大文字数
    * @return 変換後文字列
    */
    private String __changeGSTextArea(String buff, int maxLength) {
        String ret = buff;
        if (ret.length() > maxLength) {
            ret = ret.substring(0, maxLength);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] Todo 初期化情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @return 初期化したTodo情報
     * @throws SQLException SQL実行時例外
     */
    private PrjTododataModel __initTodo() throws SQLException {
        //プロジェクトSID
        int projectSid = prjSid__;
        UDate kaisiYotei = new UDate();
        UDate kigen = new UDate();
        UDate kaisiJisseki = new UDate();
        UDate syuryoJisseki = new UDate();

        //開始予定年月日
        kaisiYotei = PrjCommonBiz.createUDate(
                -1,
                -1,
                -1);
        //期限年月日
        kigen = PrjCommonBiz.createUDate(
                -1,
                -1,
                -1);
        //開始実績年月日
        kaisiJisseki = PrjCommonBiz.createUDate(
                -1,
                -1,
                -1);
        //終了実績年月日
        syuryoJisseki = PrjCommonBiz.createUDate(
                -1,
                -1,
                -1);

        PrjTododataModel ptMdl = new PrjTododataModel();

        //TODOSID採番
        int todoSid = (int) cntCon__.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                                                       GSConstProject.SBNSID_SUB_TODO,
                                                       reqMdl__.getSmodel().getUsrsid());
        //管理番号を採番
        int kanriNo = (int) cntCon__.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                       projectSid + GSConstProject.SBNSID_SUB_KANRI,
                       reqMdl__.getSmodel().getUsrsid());

        //変更履歴SIDを採番
        int hisSid = (int) cntCon__.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                      todoSid + GSConstProject.SBNSID_SUB_HISTORY,
                      reqMdl__.getSmodel().getUsrsid());
        UDate now = new UDate();
        ptMdl.setPrjSid(projectSid);
        ptMdl.setPtdSid(todoSid);
        ptMdl.setPtdNo(kanriNo);
        ptMdl.setPtdCategory(-1);
        ptMdl.setPtdTitle("");
        ptMdl.setPtdDatePlan(kaisiYotei);
        ptMdl.setPrjDateLimit(kigen);
        ptMdl.setPtdDateStart(kaisiJisseki);
        ptMdl.setPtdDateEnd(syuryoJisseki);
        ptMdl.setPtdPlanKosu(null);
        ptMdl.setPtdResultsKosu(null);
        ptMdl.setPtdAlarmKbn(GSConstProject.KEIKOKU_NO);
        ptMdl.setPtdImportance(GSConstProject.WEIGHT_KBN_LOW);
        ptMdl.setPshSid(hisSid);
        ptMdl.setPtsSid(GSConstProject.STATUS_0);
        ptMdl.setPtdContent("");
        ptMdl.setPtdAuid(0);
        ptMdl.setPtdAdate(now);
        ptMdl.setPtdEuid(0);
        ptMdl.setPtdEdate(now);
        return ptMdl;
    }

    @Override
    protected void processedLine(long num, String lineStr) throws Exception {
        //明細データ以降
        if (num >= 2) {
            count__ = num - 1;
        }

        if (mode__ == MODE_READ_STATUS) {
            //ヘッダ文字列読み飛ばし
            if (num == 1) {
                return;
            }
            __readStatus(num, lineStr);
            return;
        }
        if (mode__ == MODE_READ_USER) {
            //ヘッダ文字列読み飛ばし
            if (num == 1) {
                return;
            }
            __readUser(num, lineStr);
            return;
        }

        super.processedLine(num, lineStr);
    }
    /**
     *
     * <br>[機  能] ユーザ名情報の読み込み
     * <br>[解  説]
     * <br>[備  考]
     * @param num 行数
     * @param lineStr 行の文字列
     */
    private void __readUser(long num, String lineStr) {
        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        COLNO[] colArr = colArr__;
        // 行データ解析
        while (stringTokenizer.hasMoreTokens()) {

            buff = stringTokenizer.nextToken();

            if (buff != null && buff.equals("\"")) {
                buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
            }
            switch (colArr[j]) {
            /* ID*/
            case ID:
                break;
            /* タイトル*/
            case TITLE:
                break;
            /* 本文*/
            case BODY:
                break;
            /* 作成者*/
            case CREATE_USER:
                __addMapName(buff);
                break;
            /* 作成日時*/
            case CREATE_DATE:
                break;
            /* 更新者*/
            case UPDATE_USER:
                __addMapName(buff);
                break;
            /* 更新日時*/
            case UPDATE_DATE:
                break;
            /* ステータス*/
            case STATUS:
                break;
            /* 優先度*/
            case YUSENDO:
                break;
            /* 担当者*/
            case USER:
                if (buff.indexOf(",") > 0) {
                    String[] userNames = buff.split(",");
                    for (String name : userNames) {
                        __addMapName(name);
                    }
                } else if (!StringUtil.isNullZeroString(buff)) {
                    __addMapName(buff);
                }
                break;
            /* 期日*/
            case LIMITDATE:
                break;
            /* コメント*/
            case COMMENT:
                List<CybCsvCommentModel> commList = __getCommentFromStr(buff);
                for (CybCsvCommentModel comm : commList) {
                    __addMapName(comm.getName());
                }
                break;
            default:
                break;
            }
            j++;
        }
    }
    /**
     *
     * <br>[機  能] 参照ユーザMapにユーザ名を追加
     * <br>[解  説]
     * <br>[備  考]
     * @param buff ユーザ名
     */
    private void __addMapName(String buff) {
        if (!userNameMap__.containsKey(buff)) {
            userNameMap__.put(buff,
                    -1);
        }
    }

    /**
     *
     * <br>[機  能] TODO状態情報の読み込み
     * <br>[解  説]
     * <br>[備  考]
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __readStatus(long num, String lineStr) {
        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        COLNO[] colArr = colArr__;
        // 行データ解析
        while (stringTokenizer.hasMoreTokens()) {

            COLNO col = colArr[j];
            buff = stringTokenizer.nextToken();

            if (buff != null && buff.equals("\"")) {
                buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
            }
            if (col.equals(COLNO.STATUS)
                    && !"未着手".equals(buff)
                    && !"完了".equals(buff)
                    ) {
                statusSet__.add(__changeGSText(buff, GSConstProject.MAX_LENGTH_STATUS_NAME));
                return;
            }
            j++;
        }

    }
    /**
     * <br>[機  能] 文字列から日時情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日時文字列(Y/M/D h:m)
     * @return 日時情報
     */
    protected UDate __getDateFromStr(String dateStr) {
        if (StringUtil.isNullZeroString(dateStr)) {
            return null;
        }
        String ymd;
        String hm = null;
        int spIdx = dateStr.indexOf(" ");
        if (spIdx > 0) {
            ymd = dateStr.substring(0, spIdx);
            hm = dateStr.substring(spIdx + 1);
        } else {
            ymd = dateStr;
        }

        try {
            ArrayList<String> list = StringUtil.split("/", ymd);
            int iBYear  = Integer.parseInt(list.get(0));
            int iBMonth = Integer.parseInt(list.get(1));
            int iBDay   = Integer.parseInt(list.get(2));

            UDate date = new UDate();
            date.setDate(iBYear, iBMonth, iBDay);
            date.setZeroHhMmSs();
            if (hm == null) {
                return date;
            }


            ArrayList<String> hmlist = StringUtil.split(":", hm);
            int iBHour  = Integer.parseInt(hmlist.get(0));
            int iBMinute = Integer.parseInt(hmlist.get(1));
            date.setHour(iBHour);
            date.setMinute(iBMinute);
            return date;
        } catch (NumberFormatException e) {
            log__.debug("年月日CSV入力エラー");
        }
        return null;
    }

    /**
     * <p>年月日 日時の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param dateStr 日付
     * @param targetNameJp チェック対象名(日本語)
     * @param targetField チェック対象名
     */
    protected void __isOkDateTime(ActionErrors errors, String dateStr,
                              String targetNameJp, String targetField) {

        ActionMessage msg;

        if (StringUtil.isNullZeroString(dateStr)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetNameJp);
            StrutsUtil.addMessage(errors, msg, targetField + "error.input.required.text");
            return;
        }

        String ymd;
        String hm = null;
        int spIdx = dateStr.indexOf(" ");
        if (spIdx > 0) {
            ymd = dateStr.substring(0, spIdx);
            hm = dateStr.substring(spIdx + 1);
        } else {
            ymd = dateStr;
        }

        try {
            //日時部判定
            ArrayList<String> list = StringUtil.split("/", ymd);
            if (list.size() != 3) {
                msg = new ActionMessage("error.input.format.text",
                        targetNameJp);
                StrutsUtil.addMessage(errors, msg, targetField
                        + "error.input.format.text");
                return;
            }
            int iBYear  = Integer.parseInt(list.get(0));
            int iBMonth = Integer.parseInt(list.get(1));
            int iBDay   = Integer.parseInt(list.get(2));

            //論理チェック
            UDate date = new UDate();
            date.setDate(iBYear, iBMonth, iBDay);
            date.setZeroHhMmSs();
            if (date.getYear() != iBYear
             || date.getMonth() != iBMonth
             || date.getIntDay() != iBDay) {
                msg = new ActionMessage("error.input.notfound.date", targetNameJp);
                StrutsUtil.addMessage(errors, msg, targetField
                        + "error.input.notfound.date");
                return;
            }
            if (hm == null) {
                return;
            }


            ArrayList<String> hmlist = StringUtil.split(":", hm);
            //hh:mm形式入力
            if (hmlist.size() != 2) {
                msg = new ActionMessage("error.input.format.text",
                        targetNameJp);
                StrutsUtil.addMessage(errors, msg, targetField
                        + "error.input.format.text");
                return;
            }

            int iBHour  = Integer.parseInt(hmlist.get(0));
            int iBMinute = Integer.parseInt(hmlist.get(1));
            date.setHour(iBHour);
            date.setMinute(iBMinute);
            if (date.getIntHour() != iBHour
                    || date.getIntMinute() != iBMinute
                ) {
                msg = new ActionMessage("error.input.notfound.date", targetNameJp);
                StrutsUtil.addMessage(errors, msg, targetField
                        + "error.input.notfound.date");
                return;
            }
        } catch (NumberFormatException e) {
            log__.debug("年月日CSV入力エラー");
            msg = new ActionMessage("error.input.notfound.date", targetNameJp);
            StrutsUtil.addMessage(errors, msg, targetField
                    + "error.input.notfound.date");
        }

        return;
    }
    @Override
    public int getCount() {
        return (int) count__;
    }

}
