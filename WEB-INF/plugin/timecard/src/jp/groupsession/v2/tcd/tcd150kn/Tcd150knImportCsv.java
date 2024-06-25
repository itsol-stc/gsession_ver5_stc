package jp.groupsession.v2.tcd.tcd150kn;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;
import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;

/**
 * <br>[機  能] タイムカードインポートCSVファイルの取り込み処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150knImportCsv extends AbstractCsvRecordReader {

    /** コネクション */
    private Connection con__ = null;

    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 対象ユーザSID */
    private int targetSid__ = -1;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>con を取得します。
     * @return con
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#con__
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#userSid__
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#userSid__
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>now を取得します。
     * @return now
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#now__
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now をセットします。
     * @param now now
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#now__
     */
    public void setNow(UDate now) {
        now__ = now;
    }
    /**
     * <p>targetSid を取得します。
     * @return targetSid
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#targetSid__
     */
    public int getTargetSid() {
        return targetSid__;
    }
    /**
     * <p>targetSid をセットします。
     * @param targetSid targetSid
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#targetSid__
     */
    public void setTargetSid(int targetSid) {
        targetSid__ = targetSid;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knImportCsv#reqMdl__
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param targetSid 登録対象のユーザSID
     */
    public Tcd150knImportCsv(Connection con,
                         RequestModel reqMdl,
                         int userSid,
                         UDate now,
                         int targetSid) {
        setCon(con);
        setReqMdl(reqMdl);
        setUserSid(userSid);
        setNow(now);
        setTargetSid(targetSid);
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
    protected long _importCsv(String tmpFileDir) throws Exception {

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
        TcdTcdataModel tcdMdl = null;
        GsMessage gsMsg = new GsMessage(getReqMdl());
        UDate date = null;
        if (num > 1) {

            try {
                tcdMdl = new TcdTcdataModel();
                tcdMdl.setTcdAdate(getNow());
                tcdMdl.setTcdAuid(getUserSid());
                tcdMdl.setTcdEdate(getNow());
                tcdMdl.setTcdEuid(getUserSid());
                tcdMdl.setTcdLockFlg(GSConstTimecard.UNLOCK_FLG);
                tcdMdl.setTcdStatus(GSConstTimecard.TCD_STATUS_EDIT);
                tcdMdl.setUsrSid(getTargetSid());
                int j = 0;

                //休暇理由が設定されていない又は、休日集計区分がその他かを示すフラグ
                boolean holOtherFlg = true;
                //休暇理由がnullかを示すフラグ
                boolean holNullFlg = true;

                TcdTimezonePriDao ttpDao = new TcdTimezonePriDao(con__);
                TcdTimezonePriModel ttpMdl = ttpDao.getDefault(getTargetSid());
                ArrayList<TcdTimezonePriModel> timezoneList = ttpDao.selectCanUse(getTargetSid());
                TimecardBiz tcdBiz = new TimecardBiz(reqMdl__);
                TcdAdmConfModel admConfModel =
                        tcdBiz.getTcdAdmConfModel(reqMdl__.getSmodel().getUsrsid(), con__);

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //日付
                    if (j == 3) {
                        ArrayList<String> list = StringUtil.split("/", buff);

                        int frYear = 0;
                        int frMonth = 0;
                        int frDay = 0;

                        frYear = Integer.parseInt(list.get(0));
                        frMonth = Integer.parseInt(list.get(1));
                        frDay = Integer.parseInt(list.get(2));
                        String yyyyMmDd = StringUtil.getStrYyyyMmDd(frYear, frMonth, frDay);
                        date = UDate.getInstanceStr(yyyyMmDd);
                        date.setZeroHhMmSs();
                        tcdMdl.setTcdDate(date);
                    }

                    //時間帯
                    if (j == 4) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            if (timezoneList.size() == 0) {
                                tcdMdl.setTtiSid(admConfModel.getTacDefTimezone());    
                            } else {
                                if (ttpMdl != null) {
                                    tcdMdl.setTtiSid(ttpMdl.getTtiSid());    
                                }
                                for (TcdTimezonePriModel timezone : timezoneList) {
                                    if (timezone.getTtiRyaku().equals(buff)) {
                                        tcdMdl.setTtiSid(timezone.getTtiSid());
                                        break;
                                    }
                                }    
                            }
                        } else {
                            if (ttpMdl != null) {
                                tcdMdl.setTtiSid(ttpMdl.getTtiSid());
                            } else {
                                tcdMdl.setTtiSid(admConfModel.getTacDefTimezone());
                            }
                        }
                    }

                    //打刻始業時刻
                    if (j == 5) {
                        int hour = 0;
                        int minute = 0;
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ArrayList<String> list = StringUtil.split(":", buff);

                            hour = Integer.parseInt(list.get(0));
                            minute = Integer.parseInt(list.get(1));
                            UDate strInDate = date.cloneUDate();
                            strInDate.setZeroHhMmSs();
                            strInDate.setHour(hour);
                            strInDate.setMinute(minute);
                            Time time = null;
                            time = new Time(strInDate.getTime());
                            tcdMdl.setTcdStrikeIntime(time);
                        }
                    }
                    //打刻終業時刻
                    if (j == 6) {
                        int hour = 0;
                        int minute = 0;
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ArrayList<String> list = StringUtil.split(":", buff);

                            hour = Integer.parseInt(list.get(0));
                            minute = Integer.parseInt(list.get(1));
                            UDate strOutDate = date.cloneUDate();
                            strOutDate.setZeroHhMmSs();
                            strOutDate.setHour(hour);
                            strOutDate.setMinute(minute);
                            Time time = null;
                            time = new Time(strOutDate.getTime());
                            tcdMdl.setTcdStrikeOuttime(time);
                        }
                    }
                    //始業時刻
                    if (j == 7) {
                        int hour = 0;
                        int minute = 0;
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ArrayList<String> list = StringUtil.split(":", buff);

                            hour = Integer.parseInt(list.get(0));
                            minute = Integer.parseInt(list.get(1));
                            UDate inDate = date.cloneUDate();
                            inDate.setZeroHhMmSs();
                            inDate.setHour(hour);
                            inDate.setMinute(minute);
                            Time time = null;
                            time = new Time(inDate.getTime());
                            tcdMdl.setTcdIntime(time);
                        }
                    }
                    //終業時刻
                    if (j == 8) {
                        int hour = 0;
                        int minute = 0;
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ArrayList<String> list = StringUtil.split(":", buff);

                            hour = Integer.parseInt(list.get(0));
                            minute = Integer.parseInt(list.get(1));
                            UDate outDate = date.cloneUDate();
                            outDate.setZeroHhMmSs();
                            outDate.setHour(hour);
                            outDate.setMinute(minute);
                            Time time = null;
                            time = new Time(outDate.getTime());
                            tcdMdl.setTcdOuttime(time);
                        }
                    }
                    //備考
                    if (j == 9) {
                        tcdMdl.setTcdBiko(buff);
                    }
                    //遅刻
                    if (j == 10) {
                        String tikoku = gsMsg.getMessage("tcd.18");
                        int chkKbn;
                        if (buff.equals(tikoku)) {
                            chkKbn = GSConstTimecard.CHK_KBN_SELECT;
                        } else {
                            chkKbn = GSConstTimecard.CHK_KBN_UNSELECT;
                        }
                        tcdMdl.setTcdChkkbn(chkKbn);
                    }
                    //早退
                    if (j == 11) {
                        String soutai = gsMsg.getMessage("tcd.22");
                        int stiKbn;
                        if (buff.equals(soutai)) {
                            stiKbn = GSConstTimecard.SOU_KBN_SELECT;
                        } else {
                            stiKbn = GSConstTimecard.SOU_KBN_UNSELECT;
                        }
                        tcdMdl.setTcdSoukbn(stiKbn);
                    }
                    //休暇理由
                    if (j == 12) {
                        if (!StringUtil.isNullZeroString(buff)) {

                            //TTI_SIDの設定
                            TcdHolidayInfDao dao = new TcdHolidayInfDao(getCon());
                            TcdHolidayInfModel thiMdl = dao.getHolidayInf(buff);
                            tcdMdl.setThiSid(thiMdl.getThiSid());

                            //フラグを設定する
                            if (thiMdl == null
                                    || thiMdl.getThiHoltotalKbn()
                                    == GSConstTimecard.HOLKBN_WEEKDAY
                                    || thiMdl.getThiHoltotalKbn()
                                    != GSConstTimecard.HOLKBN_HOLDAY) {
                                holOtherFlg = false;
                            }
                            holNullFlg = false;
                        }
                    }
                    //その他休暇内容
                    if (j == 13) {
                        String holOther;
                        if (holOtherFlg) {
                            holOther = null;
                        } else {
                            holOther = buff;
                        }
                        tcdMdl.setTcdHolother(holOther);
                    }
                    //休暇日数
                    if (j == 14) {
                        BigDecimal holDays;
                        if (holNullFlg) {
                            holDays = BigDecimal.ZERO;
                        } else {
                            holDays = new BigDecimal(buff);
                        }
                        tcdMdl.setTcdHolcnt(holDays);
                    }
                }
                //データの更新，登録
                TimecardDao dao = new TimecardDao(con__);
                int updCnt;
                updCnt = dao.updateLock(tcdMdl);
                if (updCnt == 0) {
                    dao.insert(tcdMdl);
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
