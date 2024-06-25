package jp.groupsession.v2.tcd.tcd210kn;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;
import jp.groupsession.v2.tcd.tcd210.Tcd210ImportCheck;

/**
 * <br>[機  能] タイムカード 有休日数CSVファイルの取り込み処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210knImportCsv extends AbstractCsvRecordReader {
    
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd210ImportCheck.class);
    
    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    
    /**
     * <p>con を取得します。
     * @return con
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knImportCsv#con__
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knImportCsv#con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knImportCsv#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }

    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knImportCsv#reqMdl__
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     */
    public Tcd210knImportCsv(Connection con, RequestModel reqMdl) {
        setCon(con);
        setReqMdl(reqMdl);
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
     * <br>[機  能] csvファイル一行の読み込みを行う
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
        TcdYukyudataModel tydMdl = new TcdYukyudataModel();
        
        if (num > 1) {
            try {
                int j = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();

                    //ユーザIDの読み込み
                    if (j == 1) {
                        //ユーザSIDの設定
                        CmnUsrmDao cuDao = new CmnUsrmDao(getCon());
                        int usrSid = cuDao.selectLoginId(buff);
                        tydMdl.setUsrSid(usrSid);
                    }

                    //登録年度の読み込み
                    if (j == 2) {
                        tydMdl.setTcyNendo(Integer.parseInt(buff));
                    }

                    //有休日数
                    if (j == 3) {
                        tydMdl.setTcyYukyu(new BigDecimal(buff));
                    }
                }
                TcdYukyudataDao tydDao = new TcdYukyudataDao(getCon());
                //モデルに値の設定
                int usrSid = getReqMdl().getSmodel().getUsrsid();
                UDate now = new UDate();
                tydMdl.setTcyAuid(usrSid);
                tydMdl.setTcyAdate(now);
                tydMdl.setTcyEuid(usrSid);
                tydMdl.setTcyEdate(now);
                
                //有休日数の更新
                if (tydDao.update(tydMdl) == 0) {
                    //有休日数の登録
                    tydDao.insert(tydMdl);
                }
                
            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }
        }
    }
}
