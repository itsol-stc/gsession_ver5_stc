package jp.groupsession.v2.ntp.ntp110kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpUsedDataBiz;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;

/**
 * <br>[機  能] 日報インポート CSVファイルの取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp110ImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp110ImportCsv.class);
    /** コネクション */
    private Connection con__ = null;

    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** ユーザSID */
    private int id__ = -1;

    /**
     * <p>id を取得します。
     * @return id
     */
    public int getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(int id) {
        id__ = id;
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
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param cntCon 採番用コネクション
     * @param id 登録対象ID
     */
    public Ntp110ImportCsv(Connection con,
                         int userSid,
                         UDate now,
                         MlCountMtController cntCon,
                         int id) {
        setCon(con);
        setUserSid(userSid);
        setNow(now);
        setCntCon(cntCon);
        setId(id);
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     */
    public void importCsv(String tmpFileDir) throws Exception {

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
        readFile(new File(csvFile), Encoding.WINDOWS_31J);
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
        NtpDataModel impMdl = null;
        if (num > 1) {

            try {
                //日報SID採番
                int nipSid = -1;
                nipSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                        GSConstNippou.SBNSID_SUB_NIPPOU, userSid__);

                impMdl = new NtpDataModel();
                //初期値設定
                impMdl.setNipSid(nipSid);
                impMdl.setUsrSid(id__);
                impMdl.setNipDate(getNow());
                impMdl.setNipFrTime(getNow());
                impMdl.setNipToTime(getNow());
                impMdl.setNipKadoHh(0);
                impMdl.setNipKadoMm(0);
                impMdl.setNipMgySid(-1);
                impMdl.setNanSid(-1);
                impMdl.setAcoSid(-1);
                impMdl.setAbaSid(-1);
                impMdl.setNipTitle("");
                impMdl.setNipTitleClo(GSConstNippou.BGCOLOR_BLUE);
                impMdl.setMprSid(-1);
                impMdl.setMkbSid(-1);
                impMdl.setMkhSid(-1);
                impMdl.setNipTieupSid(-1);
                impMdl.setNipKeizoku(GSConstNippou.KEIZOKU_KBN_NO);
                impMdl.setNipActend(null);
                impMdl.setNipDetail("");
                impMdl.setNipAssign("");
                impMdl.setNipKingaku(0);
                impMdl.setNipMikomi(GSConstNippou.MIKOMI_10);
                impMdl.setNipSyokan("");
                impMdl.setNipPublic(GSConstNippou.DSP_PUBLIC);
                impMdl.setNipEdit(GSConstNippou.EDIT_CONF_NONE);
                impMdl.setNexSid(GSConstNippou.DF_SCHGP_ID);
                impMdl.setNipAuid(getUserSid());
                impMdl.setNipAdate(getNow());
                impMdl.setNipEuid(getUserSid());
                impMdl.setNipEdate(getNow());

                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //報告日
                    if (j == 1 && !StringUtil.isNullZeroStringSpace(buff)) {
                        UDate repDate = new UDate();
                        repDate.setDate(buff);
                        repDate.setZeroHhMmSs();
                        impMdl.setNipDate(repDate);
                    }
                    //開始時間
                    if (j == 2 && !StringUtil.isNullZeroStringSpace(buff)) {
                        UDate frDate = new UDate();
                        int hour = 0;
                        int minute = 0;
                        ArrayList<String> list = StringUtil.split(":", buff);
                        hour = new Integer(((String) list.get(0))).intValue();
                        minute = new Integer(((String) list.get(1))).intValue();
                        frDate = impMdl.getNipDate().cloneUDate();
                        frDate.setZeroHhMmSs();
                        frDate.setHour(hour);
                        frDate.setMinute(minute);
                        impMdl.setNipFrTime(frDate);
                    }
                    //終了時間
                    if (j == 3 && !StringUtil.isNullZeroStringSpace(buff)) {
                        UDate toDate = new UDate();
                        int hour = 0;
                        int minute = 0;
                        ArrayList<String> list = StringUtil.split(":", buff);
                        hour = new Integer(((String) list.get(0))).intValue();
                        minute = new Integer(((String) list.get(1))).intValue();
                        toDate = impMdl.getNipDate().cloneUDate();
                        toDate.setZeroHhMmSs();
                        toDate.setHour(hour);
                        toDate.setMinute(minute);
                        impMdl.setNipToTime(toDate);
                    }
                    //案件SID
                    if (j == 4 && !StringUtil.isNullZeroStringSpace(buff)) {
                        //案件コードから案件SIDを取得
                        NtpAnkenModel model = null;
                        NtpAnkenDao dao = new NtpAnkenDao(con__);
                        model = dao.select(buff);
                        if (model != null) {
                            impMdl.setNanSid(new Integer(model.getNanSid()).intValue());
                        }
                    }
                    //会社コード
                    if (j == 5 && !StringUtil.isNullZeroStringSpace(buff)) {
                        AdrCompanyModel model = null;
                        AdrCompanyDao dao = new AdrCompanyDao(con__);
                        model = dao.select(buff);
                        if (model != null) {
                            impMdl.setAcoSid(new Integer(model.getAcoSid()).intValue());
                        }
                    }
                    //タイトル
                    if (j == 6 && !StringUtil.isNullZeroStringSpace(buff)) {
                        impMdl.setNipTitle(buff);
                    }
                    //タイトル色
                    if (j == 7 && !StringUtil.isNullZeroStringSpace(buff)) {
                        impMdl.setNipTitleClo(new Integer(buff).intValue());
                    }
                    //活動分類コード
                    if (j == 8 && !StringUtil.isNullZeroStringSpace(buff)) {
                        NtpKtbunruiModel model = null;
                        NtpKtbunruiDao dao = new NtpKtbunruiDao(con__);
                        model = dao.select(buff);
                        if (model != null) {
                            impMdl.setMkbSid(new Integer(model.getNkbSid()).intValue());
                        }
                    }
                    //活動方法コード
                    if (j == 9 && !StringUtil.isNullZeroStringSpace(buff)) {
                        NtpKthouhouModel model = null;
                        NtpKthouhouDao dao = new NtpKthouhouDao(con__);
                        model = dao.select(buff);
                        if (model != null) {
                            impMdl.setMkhSid(new Integer(buff).intValue());
                        }
                    }
                    //内容
                    if (j == 10 && !StringUtil.isNullZeroStringSpace(buff)) {
                        impMdl.setNipDetail(buff);
                    }
                    //見込み度
                    if (j == 11 && !StringUtil.isNullZeroStringSpace(buff)) {
                        impMdl.setNipMikomi(new Integer(buff).intValue());
                    }

                }

                NtpDataDao dao = new NtpDataDao(getCon());
                dao.insert(impMdl);

                //日報情報のデータ使用量を登録
                NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
                usedDataBiz.insertNtpDataSize(nipSid, true);
            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }

        }
    }
}