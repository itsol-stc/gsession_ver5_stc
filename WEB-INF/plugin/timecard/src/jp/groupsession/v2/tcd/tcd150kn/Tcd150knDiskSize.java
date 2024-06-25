package jp.groupsession.v2.tcd.tcd150kn;

import java.io.File;
import java.sql.Connection;
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
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.dao.TcdDatausedSumDao;

/**
 * <br>[機  能] タイムカードインポートCSVファイルの取り込み時にディスク容量計算を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150knDiskSize extends AbstractCsvRecordReader {

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    
    /** 更新日付リスト*/
    private ArrayList<UDate> importDate__ = new ArrayList<UDate>();
    
    /** 対象ユーザ*/
    private int targetUserSid__;

    /**
     * <p>targetUserSid を取得します。
     * @return targetUserSid
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#targetUserSid__
     */
    public int getTargetUserSid() {
        return targetUserSid__;
    }
    /**
     * <p>targetUserSid をセットします。
     * @param targetUserSid targetUserSid
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#targetUserSid__
     */
    public void setTargetUserSid(int targetUserSid) {
        targetUserSid__ = targetUserSid;
    }
    /**
     * <p>importDate を取得します。
     * @return importDate
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#importDate
     */
    public ArrayList<UDate> getImportDate() {
        return importDate__;
    }
    /**
     * <p>importDate をセットします。
     * @param importDate importDate
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#importDate
     */
    public void setImportDate(ArrayList<UDate> importDate) {
        this.importDate__ = importDate;
    }
    /**
     * <p>con を取得します。
     * @return con
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#con__
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knDiskSize#reqMdl__
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
     * @param targetUser インポート対象ユーザ
     */
    public Tcd150knDiskSize(Connection con,
                         RequestModel reqMdl,
                         int targetUser) {
        setCon(con);
        setReqMdl(reqMdl);
        setTargetUserSid(targetUser);;
    }

    /**
     * <br>[機　能] ディスクサイズ計算（削除）を行う
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     * @return num 取り込み件数
     */
    protected ArrayList<UDate> _deleteDiskSize(String tmpFileDir) throws Exception {

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
        
        TcdDatausedSumDao tdsDao = new TcdDatausedSumDao(getCon());
        tdsDao.insertDelDiff(importDate__, targetUserSid__);

        return importDate__;
    }
    
    /**
     * <br>[機  能] ディスクサイズ計算(追加)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws Exception
     */
    protected void _insertDiskSize() throws Exception {
        
        TcdDatausedSumDao tdsDao = new TcdDatausedSumDao(getCon());
        tdsDao.insertAddDiff(importDate__, targetUserSid__);
        
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
        UDate date = null;
        
        
        if (num > 1) {
            try {
                int j = 0;
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
                        importDate__.add(date);
                    }
                }
                
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
