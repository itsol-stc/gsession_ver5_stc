package jp.groupsession.v2.rsv.rsv250kn;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約インポート確認画面 施設予約CSVファイルの取り込み処理を行います
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvImportCsv.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** ユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** RsvSisDataDaoインスタンス */
    private RsvSisDataDao rsdDao__ = null;
    /** RsvSisYrkDaoインスタンス */
    private RsvSisYrkDao rsyDao__ = null;
    /** RsvSisKyrkDao インスタンス */
    private RsvSisKyrkDao krykDao__ = null;
    /** 管理者設定の使用するプラグイン設定を反映したプラグイン情報 */
    private PluginConfig pconfigForMain__ = null;
    /** プラグイン情報 */
    private PluginConfig pconfig__ = null;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;

    /**
     * @return rsdDao
     */
    public RsvSisDataDao getRsdDao() {
        return rsdDao__;
    }
    /**
     * @param rsdDao 設定する rsdDao
     */
    public void setRsdDao(RsvSisDataDao rsdDao) {
        rsdDao__ = rsdDao;
    }
    /**
     * @return rsyDao
     */
    public RsvSisYrkDao getRsyDao() {
        return rsyDao__;
    }
    /**
     * @param rsyDao 設定する rsyDao
     */
    public void setRsyDao(RsvSisYrkDao rsyDao) {
        rsyDao__ = rsyDao;
    }
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
     * @param reqMdl リクエスト情報
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param cntCon 採番用コネクション
     * @param pconfigForMain プラグインコンフィグ
     * @param pconfig プラグインコンフィグ
     * @param appRootPath アプリケーションルートパス
     */
    public RsvImportCsv(Connection con,
                        RequestModel reqMdl,
                        int userSid,
                        UDate now,
                        MlCountMtController cntCon,
                        PluginConfig pconfigForMain,
                        PluginConfig pconfig,
                        String appRootPath) {

        setCon(con);
        setUserSid(userSid);
        setNow(now);
        setCntCon(cntCon);
        reqMdl__ = reqMdl;
        pconfigForMain__ = pconfigForMain;
        pconfig__ = pconfig;
        appRootPath__ = appRootPath;
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @return num 件数
     * @throws Exception 実行時例外
     */
    public long importCsv(String tmpFileDir) throws Exception {

        rsdDao__ = new RsvSisDataDao(getCon());
        rsyDao__ = new RsvSisYrkDao(getCon());
        krykDao__ = new RsvSisKyrkDao(getCon());

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
        RsvSisYrkModel yrkModel = new RsvSisYrkModel();
        RsvSisKyrkModel kyrkModel = new RsvSisKyrkModel();
        if (num > 1) {

            try {
                //予約SID採番
                int yoyakuSid = (int) cntCon__.getSaibanNumber(
                            GSConstReserve.SBNSID_RESERVE,
                            GSConstReserve.SBNSID_SUB_YOYAKU,
                            userSid__);
                yrkModel.setRsySid(yoyakuSid);
                yrkModel.setRsyAdate(now__);
                yrkModel.setRsyEdate(now__);
                yrkModel.setScdRsSid(-1);
                yrkModel.setRsrRsid(-1);

                kyrkModel.setRsySid(yoyakuSid);
                kyrkModel.setRkyAdate(now__);
                kyrkModel.setRkyEdate(now__);

                int j = 0;
                UDate frDate = new UDate();
                UDate toDate = new UDate();
                int rsdSid = -1;
                //施設区分
                int sisKbn = 0;
                //担当部署
                String busyo = null;
                //担当・使用者名
                String tName = null;
                //人数
                String tNum = null;
                //利用区分
                String useKbn = null;
                //連絡先
                String contact = null;
                //会議名案内
                String guide = null;
                //駐車場見込み台数
                String parkNum = null;
                //印刷区分
                String printKbn = null;
                //行き先
                String dest = null;


                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //施設ID
                    if (j == 1) {
                        rsdSid = rsdDao__.getRsdSid(buff);
                        yrkModel.setRsdSid(rsdSid);
                        //施設区分取得
                        sisKbn = rsdDao__.getSisKbn(buff);
                    }
                    //ユーザーID
                    if (j == 2) {
                        int usrSid = rsdDao__.getUserSid(buff);
                        yrkModel.setRsyEuid(usrSid);
                        yrkModel.setRsyAuid(usrSid);

                        kyrkModel.setRkyAuid(usrSid);
                        kyrkModel.setRkyEuid(usrSid);
                    }
                    //利用目的
                    if (j == 3) {
                        yrkModel.setRsyMok(buff);
                    }
                    //開始日付
                    if (j == 4) {

                        List<String> list = StringUtil.split("/", buff);

                        int frYear = 0;
                        int frMonth = 0;
                        int frDay = 0;

                        frYear = new Integer(((String) list.get(0)).trim()).intValue();
                        frMonth = new Integer(((String) list.get(1)).trim()).intValue();
                        frDay = new Integer(((String) list.get(2)).trim()).intValue();

                        frDate.setDate(StringUtil.getStrYyyyMmDd(frYear, frMonth, frDay));
                    }
                    //開始時刻
                    if (j == 5) {
                        List<String> list = StringUtil.split(":", buff);
                        int hour = 0;
                        int minute = 0;
                            hour = new Integer(((String) list.get(0)).trim()).intValue();
                            minute = new Integer(((String) list.get(1)).trim()).intValue();
                        frDate.setZeroHhMmSs();
                        frDate.setHour(hour);
                        frDate.setMinute(minute);
                        yrkModel.setRsyFrDate(frDate);
                    }
                    //終了日時
                    if (j == 6) {
                        List<String> list = StringUtil.split("/", buff);

                        int toYear = 0;
                        int toMonth = 0;
                        int toDay = 0;

                        toYear = new Integer(((String) list.get(0)).trim()).intValue();
                        toMonth = new Integer(((String) list.get(1)).trim()).intValue();
                        toDay = new Integer(((String) list.get(2)).trim()).intValue();

                        toDate.setDate(StringUtil.getStrYyyyMmDd(toYear, toMonth, toDay));
                    }
                    //終了時刻
                    if (j == 7) {
                        List<String> list = StringUtil.split(":", buff);
                        int hour = 0;
                        int minute = 0;
                            hour = new Integer(((String) list.get(0)).trim()).intValue();
                            minute = new Integer(((String) list.get(1)).trim()).intValue();
                        toDate.setZeroHhMmSs();
                        toDate.setHour(hour);
                        toDate.setMinute(minute);
                        yrkModel.setRsyToDate(toDate);
                   }

                    //備考
                    if (j == 8) {
                        yrkModel.setRsyBiko(buff);
                    }
                    //編集権限
                    if (j == 9) {
                        yrkModel.setRsyEdit(new Integer(buff).intValue());
                    }
                    //公開区分
                    if (j == 10) {
                        yrkModel.setRsyPublic(new Integer(buff).intValue());
                    }
                    //担当部署
                    if (j == 11) {
                        busyo = buff;
                    }
                    //担当・使用者名
                    if (j == 12) {
                        tName = buff;
                    }
                    //人数
                    if (j == 13) {
                        tNum = buff;
                    }
                    //利用区分
                    if (j == 14) {
                        useKbn = buff;
                    }
                    //連絡先
                    if (j == 15) {
                        contact = buff;
                    }
                    //会議名案内
                    if (j == 16) {
                        guide = buff;
                    }
                    //駐車場見込み台数
                    if (j == 17) {
                        parkNum = buff;
                    }
                    //印刷区分
                    if (j == 18) {
                        printKbn = buff;
                    }
                    //行き先
                    if (j == 19) {
                        dest = buff;
                    }
                }

                //承認状況
                rsvCmnBiz.setSisYrkApprData(con__, yrkModel.getRsdSid(), yrkModel, userSid__);

                rsyDao__.insertNewYoyaku(yrkModel);

                //施設予約区分別情報登録
                if (RsvCommonBiz.isRskKbnRegCheck(sisKbn)) {
                    kyrkModel.setRkyBusyo(busyo);
                    kyrkModel.setRkyName(tName);
                    kyrkModel.setRkyNum(tNum);
                    kyrkModel.setRkyContact(contact);

                    if (sisKbn == GSConstReserve.RSK_KBN_HEYA) {
                        kyrkModel.setRkyUseKbn(NullDefault.getInt(useKbn, 0));
                        kyrkModel.setRkyGuide(guide);
                        kyrkModel.setRkyParkNum(parkNum);
                    } else if (sisKbn == GSConstReserve.RSK_KBN_CAR) {
                        if (RsvCommonBiz.isUsePrintKbn(appRootPath__)) {
                            kyrkModel.setRkyPrintKbn(NullDefault.getInt(printKbn, 1));
                        } else {
                            kyrkModel.setRkyPrintKbn(GSConstReserve.RSY_PRINT_KBN_NO);
                        }
                        kyrkModel.setRkyDest(dest);
                    }

                    krykDao__.insert(kyrkModel);
                }


                //ショートメールで通知
                //選択した施設に承認設定がされている場合
                if (rsvCmnBiz.isApprSis(con__, rsdSid, userSid__)) {
                    CommonBiz cmnBiz = new CommonBiz();
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfigForMain__)) {
                        RsvRegSmailModel regMdl = new RsvRegSmailModel();
                        regMdl.setCon(con__);
                        regMdl.setReqMdl(reqMdl__);
                        regMdl.setRsySid(yoyakuSid);
                        regMdl.setRsdSid(rsdSid);
                        regMdl.setCntCon(cntCon__);
                        regMdl.setUserSid(userSid__);
                        regMdl.setAppRootPath(appRootPath__);
                        regMdl.setTempDir(null);
                        regMdl.setPluginConfig(pconfig__);

                        rsvCmnBiz.sendRegSmail(regMdl);
                    }
                }

            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }

        }
    }
}