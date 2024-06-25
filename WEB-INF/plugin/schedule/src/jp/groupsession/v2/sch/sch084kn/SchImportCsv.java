package jp.groupsession.v2.sch.sch084kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchRemindConfWriter;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;

/**
 * <br>[機  能] スケジュール 管理者設定 スケジュールインポート確認画面 CSVファイル取り込み処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class SchImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchImportCsv.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;

    /*

    /**
     * <p>con__ を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con__ をセットします。
     * @param con con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.sch.sch084kn.SchImportCsv#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.sch.sch084kn.SchImportCsv#reqMdl__
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>userSid__ を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid__ をセットします。
     * @param userSid userSid__
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>now__ を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now__ をセットします。
     * @param now now__
     */
    public void setNow(UDate now) {
        now__ = now;
    }
    /**
     * <p>cntCon__ を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * <p>cntCon__ をセットします。
     * @param cntCon cntCon__
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param cntCon 採番用コネクション
     * @param reqMdl リクエストモデル
     */
    public SchImportCsv(Connection con,
                         int userSid,
                         UDate now,
                         MlCountMtController cntCon,
                         RequestModel reqMdl) {
        setCon(con);
        setUserSid(userSid);
        setNow(now);
        setCntCon(cntCon);
        setReqMdl(reqMdl);
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     * @return long 取り込み件数
     */
    public long importCsv(String tmpFileDir) throws Exception {

        //テンポラリディレクトリにあるファイル名称を取得
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
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");
        SchDataModel impMdl = null;
        SchCommonBiz schBiz = new SchCommonBiz(con__);

        if (num > 1) {

            try {
                //スケジュールSID採番
                int scdSid = -1;
                scdSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                        SaibanModel.SBNSID_SUB_SCH, userSid__);

                impMdl = new SchDataModel();
                impMdl.setScdSid(scdSid);
                impMdl.setScdGrpSid(GSConstSchedule.DF_SCHGP_ID);
                impMdl.setSceSid(GSConstSchedule.DF_SCHGP_ID);
                impMdl.setScdRsSid(GSConstSchedule.DF_SCHGP_ID);

                impMdl.setScdAuid(getUserSid());
                impMdl.setScdAdate(getNow());
                impMdl.setScdEuid(getUserSid());
                impMdl.setScdEdate(getNow());
                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //ログインID
                    if (j == 1) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            //ユーザSIDを取得
                            CmnUsrmDao dao = new CmnUsrmDao(con__);
                            impMdl.setScdUsrSid(dao.selectLoginId(buff));
                            impMdl.setScdUsrKbn(GSConstSchedule.USER_KBN_USER);
                        }
                    }

                    //グループID
                    if (j == 2) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            //グループSIDを取得
                            CmnGroupmDao dao = new CmnGroupmDao(con__);
                            CmnGroupmModel model = dao.getGroupInf(buff);
                            impMdl.setScdUsrSid(model.getGrpSid());
                            impMdl.setScdUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                        }
                    }

                    //開始日付
                    if (j == 3) {
                        UDate frDate = new UDate();
                        ArrayList<String> list = StringUtil.split("/", buff);

                        int frYear = 0;
                        int frMonth = 0;
                        int frDay = 0;

                        frYear = new Integer(((String) list.get(0))).intValue();
                        frMonth = new Integer(((String) list.get(1))).intValue();
                        frDay = new Integer(((String) list.get(2))).intValue();

                        frDate.setDate(StringUtil.getStrYyyyMmDd(frYear, frMonth, frDay));
                        frDate.setZeroHhMmSs();
                        impMdl.setScdFrDate(frDate);
                    }
                    //開始時刻
                    if (j == 4) {
                        UDate frDate = null;
                        int hour = 0;
                        int minute = 0;
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ArrayList<String> list = StringUtil.split(":", buff);

                            hour = new Integer(((String) list.get(0))).intValue();
                            minute = new Integer(((String) list.get(1))).intValue();
                            frDate = impMdl.getScdFrDate();
                            frDate.setZeroHhMmSs();
                            frDate.setHour(hour);
                            frDate.setMinute(minute);
                            impMdl.setScdFrDate(frDate);
                        }
                    }
                    //終了日付
                    if (j == 5) {

                        UDate toDate = new UDate();
                        ArrayList<String> list = StringUtil.split("/", buff);

                        int toYear = 0;
                        int toMonth = 0;
                        int toDay = 0;

                        toYear = new Integer(((String) list.get(0))).intValue();
                        toMonth = new Integer(((String) list.get(1))).intValue();
                        toDay = new Integer(((String) list.get(2))).intValue();

                        toDate.setDate(StringUtil.getStrYyyyMmDd(toYear, toMonth, toDay));
                        toDate.setMaxHhMmSs();
                        impMdl.setScdToDate(toDate);
                    }
                    //終了時刻
                    if (j == 6) {
                        UDate toDate = null;
                        int hour = 0;
                        int minute = 0;
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ArrayList<String> list = StringUtil.split(":", buff);

                            hour = new Integer(((String) list.get(0))).intValue();
                            minute = new Integer(((String) list.get(1))).intValue();
                            toDate = impMdl.getScdToDate();
                            toDate.setZeroHhMmSs();
                            toDate.setHour(hour);
                            toDate.setMinute(minute);
                            impMdl.setScdToDate(toDate);
                            impMdl.setScdDaily(GSConstSchedule.TIME_EXIST);
                        } else {
                            impMdl.setScdDaily(GSConstSchedule.TIME_NOT_EXIST);
                        }
                    }

                    //タイトル
                    if (j == 7) {
                        impMdl.setScdTitle(buff);
                    }
                    //タイトル色
                    if (j == 8) {
                        impMdl.setScdBgcolor(new Integer(buff).intValue());
                    }
                    //内容
                    if (j == 9) {
                        impMdl.setScdValue(buff);
                    }
                    //備考
                    if (j == 10) {
                        impMdl.setScdBiko(buff);
                    }
                    //編集権限
                    if (j == 11) {
                        impMdl.setScdEdit(new Integer(buff).intValue());
                    }
                    //公開区分
                    if (j == 12) {
                        impMdl.setScdPublic(new Integer(buff).intValue());
                    }
                }
                if (impMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                    SchPriConfModel spcMdl = schBiz.getSchPriConfModel(
                            con__, impMdl.getScdUsrSid()
                            );
                    SchRemindConfWriter.builder()
                    .setDefConf(SchPriPushModel.getInstance(spcMdl))
                    .setOldConf(null)
                    .setRemindMode(SchEnumRemindMode.OTHERS)
                    .setTimeKbn(impMdl.getScdDaily())
                    .setPushUseable(
                            PushServiceOperator.getInstance(con__, reqMdl__.getDomain()).isUseable()
                            )
                    .build().write(impMdl);

                    //通知予定リストの登録
                    schBiz.insertPushInfUser(impMdl);
                } else if (impMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                    SchRemindConfWriter.builder()
                    .setRemindMode(SchEnumRemindMode.GROUP)
                    .setTargetGrp(GSConstSchedule.REMINDER_USE_YES)
                    .setTimeKbn(impMdl.getScdDaily())
                    .build().write(impMdl);

                    //通知予定リストの登録
                    Map<Integer, SchPriPushModel> pushMap =
                            schBiz.getGroupPriConf(impMdl.getScdUsrSid());
                    schBiz.insertPushInfGroup(
                            impMdl.getScdSid(), pushMap, impMdl.getScdFrDate().cloneUDate(),
                            GSConstSchedule.REMINDER_USE_YES);
                }

                SchDataDao dao = new SchDataDao(getCon());
                dao.insert(impMdl);

            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }

        }
    }
}