package jp.groupsession.v2.ntp.ntp132kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <br>[機  能] グループCSVファイル取り込み処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class ShohinCsvImport extends AbstractCsvRecordReader {

    /** 実行モード*/
    private int mode__;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    private HttpServletRequest req__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** セッションユーザ */
    private int sessionUser__;
    /** システム日付 */
    private UDate sysUd__;
    /** 取込みグループ情報リスト*/
    private ArrayList<NtpShohinModel> shohinList__ = null;
    /** カテゴリSID */
    private int nscSid__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param cntCon 採番コントローラ
     * @param userSid セッションユーザSID
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     * @param con コネクション
     * @param nscSid カテゴリSID
     */
    public ShohinCsvImport(HttpServletRequest req,
                          MlCountMtController cntCon,
                          int userSid,
                          int mode,
                          Connection con,
                          int nscSid) {

        setReq(req);
        setCntCon(cntCon);
        setSessionUser(userSid);
        setMode(mode);
        setCon(con);
        setSysUd(new UDate());
        setGrpmList(new ArrayList<NtpShohinModel>());
        nscSid__ = nscSid;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     */
    public ShohinCsvImport(HttpServletRequest req, int mode) {

        setReq(req);
        setMode(mode);
        setGrpmList(new ArrayList<NtpShohinModel>());
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return grpmList__ グループ情報リスト
     * @throws  Exception   実行時例外
     */
    public ArrayList<NtpShohinModel> importCsv(String tmpFileDir)
        throws Exception {

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

        //登録・登録予定内容を設定
        return shohinList__;
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

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        //処理モード
        if (mode__ == 0) {
            __import(num, lineStr);
        } else {
            //登録処理無し
            __getData(num, lineStr);
        }

    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __getData(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //商品情報
        NtpShohinModel shohinModel = new NtpShohinModel();
        shohinModel.setNscSid(nscSid__);
        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //商品コード
            if (j == 1) {
                shohinModel.setNhnCode(NullDefault.getString(buff, ""));
            }
            //商品名
            if (j == 2) {
                shohinModel.setNhnName(NullDefault.getString(buff, ""));
            }
            //販売価格
            if (j == 3) {
                shohinModel.setNhnPriceSale(
                        Integer.valueOf(NullDefault.getStringZeroLength(buff.trim(), "0")));
            }
            //原価価格
            if (j == 4) {
                shohinModel.setNhnPriceCost(
                        Integer.valueOf(NullDefault.getStringZeroLength(buff.trim(), "0")));
            }
          //補足事項
            if (j == 5) {
                shohinModel.setNhnHosoku(NullDefault.getString(buff, ""));
            }
        }
        shohinList__.add(shohinModel);
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __import(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        NtpShohinModel shohinModel = new NtpShohinModel();
        NtpShohinDao shohinDao = new NtpShohinDao(con__);
        boolean existFlg = false;

        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

          //商品コード
            if (j == 1) {
                existFlg = shohinDao.existShohin(0, buff);
                shohinModel.setNhnCode(NullDefault.getString(buff, ""));
            }
            //商品名
            if (j == 2) {
                shohinModel.setNhnName(NullDefault.getString(buff, ""));
            }
            //販売価格
            if (j == 3) {
                shohinModel.setNhnPriceSale(
                        Integer.valueOf(NullDefault.getStringZeroLength(buff.trim(), "0")));
            }
            //原価価格
            if (j == 4) {
                shohinModel.setNhnPriceCost(
                        Integer.valueOf(NullDefault.getStringZeroLength(buff.trim(), "0")));
            }
          //補足事項
            if (j == 5) {
                shohinModel.setNhnHosoku(NullDefault.getString(buff, ""));
            }

        }
        int gsid = 0;
        shohinModel.setNscSid(nscSid__);
        shohinModel.setNhnAuid(sessionUser__);
        shohinModel.setNhnAdate(sysUd__);
        shohinModel.setNhnEuid(sessionUser__);
        shohinModel.setNhnEdate(sysUd__);
        //1 == 1&&true
        if (existFlg) {
            NtpShohinModel shoModel = shohinDao.getShohinInf(shohinModel.getNhnCode());
            if (shoModel != null) {
                gsid = shoModel.getNhnSid();
                shohinModel.setNhnSid(gsid);
                //グループ更新処理
                shohinDao.update(shohinModel);
            }

        } else {
            gsid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_SHOHIN,
                    sessionUser__);
            shohinModel.setNhnSid(gsid);

            //グループ登録処理
            shohinDao.insert(shohinModel);
        }


        shohinList__.add(shohinModel);
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
     * <p>grpmList を取得します。
     * @return grpmList
     */
    public ArrayList<NtpShohinModel> getGrpmList() {
        return shohinList__;
    }

    /**
     * <p>grpmList をセットします。
     * @param grpmList grpmList
     */
    public void setGrpmList(ArrayList<NtpShohinModel> grpmList) {
        shohinList__ = grpmList;
    }

    /**
     * <p>req を取得します。
     * @return req
     */
    public HttpServletRequest getReq() {
        return req__;
    }

    /**
     * <p>req をセットします。
     * @param req req
     */
    public void setReq(HttpServletRequest req) {
        req__ = req;
    }

    /**
     * <p>sessionUser を取得します。
     * @return sessionUser
     */
    public int getSessionUser() {
        return sessionUser__;
    }

    /**
     * <p>sessionUser をセットします。
     * @param sessionUser sessionUser
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
    }

    /**
     * <p>sysUd を取得します。
     * @return sysUd
     */
    public UDate getSysUd() {
        return sysUd__;
    }

    /**
     * <p>sysUd をセットします。
     * @param sysUd sysUd
     */
    public void setSysUd(UDate sysUd) {
        sysUd__ = sysUd;
    }

    /**
     * <p>mode を取得します。
     * @return mode
     */
    public int getMode() {
        return mode__;
    }

    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(int mode) {
        mode__ = mode;
    }
}