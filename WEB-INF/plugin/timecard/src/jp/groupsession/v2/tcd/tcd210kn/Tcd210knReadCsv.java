package jp.groupsession.v2.tcd.tcd210kn;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.tcd.tcd210.Tcd210ImportCheck;

/**
 * <br>[機  能] タイムカード 有休日数CSVファイルの読み取り処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210knReadCsv extends AbstractCsvRecordReader {
    
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd210ImportCheck.class);
    
    /** コネクション */
    private Connection con__ = null;
    
    /** 登録用モデルリスト */
    private List<Tcd210knInsertModel> insList__ = new ArrayList<Tcd210knInsertModel>();
    
    /** ファイル名 */
    private String fileName__ = null;
    
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
     * <p>insList を取得します。
     * @return insList
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knReadCsv#insList__
     */
    public List<Tcd210knInsertModel> getInsList() {
        return insList__;
    }

    /**
     * <p>insList をセットします。
     * @param insList insList
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knReadCsv#insList__
     */
    public void setInsMdl(List<Tcd210knInsertModel> insList) {
        insList__ = insList;
    }
    
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     */
    public Tcd210knReadCsv(Connection con) {
        setCon(con);
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
            setFileName(fMdl.getFileName());
        }
        String csvFile = tmpFileDir + saveFileName;

        //ファイル取込
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        return num;
    }
    
    /**
     * <br>[機  能] CSVファイルの内容から登録用モデルリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {
        
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");
        
        if (num > 1) {
            try {
                int j = 0;
                Tcd210knInsertModel dspMdl = new Tcd210knInsertModel();

                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();

                    //ユーザIDの読み込み
                    if (j == 1) {
                        //ユーザSIDの設定
                        CmnUsrmDao cuDao = new CmnUsrmDao(getCon());
                        int usrSid = cuDao.selectLoginId(buff);
                        dspMdl.setUserSid(usrSid);
                        //対象ユーザ名の設定
                        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(getCon());
                        CmnUsrmInfModel cuiMdl = cuiDao.getUsersInf(usrSid);
                        StringBuilder strBld = new StringBuilder();
                        strBld.append(cuiMdl.getUsiSei());
                        strBld.append(" ");
                        strBld.append(cuiMdl.getUsiMei());
                        dspMdl.setUserName(strBld.toString());
                    }

                    //登録年度の読み込み
                    if (j == 2) {
                        dspMdl.setNendo(Integer.parseInt(buff));
                    }

                    //有休日数
                    if (j == 3) {
                        BigDecimal yukyuDate = new BigDecimal(buff);
                        dspMdl.setYukyuDays(yukyuDate);
                    }
                }
                insList__.add(dspMdl);
            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }
        }
    }

    /**
     * <p>fileName を取得します。
     * @return fileName
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knReadCsv#fileName__
     */
    public String getFileName() {
        return fileName__;
    }

    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knReadCsv#fileName__
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
}
