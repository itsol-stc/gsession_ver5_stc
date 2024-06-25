package jp.groupsession.v2.cir.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cmn.GSConstCommon;
/**
 *
 * <br>[機  能] テンポラリディレクトリ内の変更判定ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirFileEditCheckBiz {

    /**
    *
    * <br>[機  能] ファイル変更チェック用目録ファイルの作成を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param tempDir テンポラリディレクトリ
    * @throws IOException ファイルアクセス実行時例外
    * @throws IOToolsException ファイルアクセス実行時例外
    */
   public void createMokulokuFile(String tempDir) throws  IOException, IOToolsException {
       IOTools.isFileCheck(tempDir, GSConstCircular.CIR_EDITCHK_FILENAME, true);
       File mokulokuFile = new File(
               tempDir + File.separator + GSConstCircular.CIR_EDITCHK_FILENAME);

       Enumeration<File> fileEnum =  IOTools.getFiles(tempDir);

       FileOutputStream fos = new FileOutputStream(mokulokuFile);
       OutputStreamWriter osw = new OutputStreamWriter(fos, Encoding.UTF_8);
       try {
           osw.append("FILE_NAME,ADD_TIME\r\n");

           while (fileEnum.hasMoreElements()) {
               File tempFile = fileEnum.nextElement();
               //ファイル名を取得
               String fileName = tempFile.getName();
               if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                   continue;
               }
               //ファイル名,タイムスタンプを出力
               osw.append(fileName);
               osw.append(",");
               osw.append(String.valueOf(tempFile.lastModified()));
               osw.append("\r\n");
               osw.flush();
           }


       } finally {
           if (osw != null) {
              osw.close();
           }
           if (fos != null) {
               fos.close();
           }
       }

   }
   /**
    *
    * <br>[機  能] 目録ファイルと現在のファイルを比較しファイルの変更を検知する
    * <br>[解  説]
    * <br>[備  考]
    * @param tempDir テンポラリディレクトリ
    * @return true:ファイル変更有 false：ファイル変更なし
    * @throws IOToolsException ファイルアクセス実行時例外
    * @throws IOException ファイルアクセス実行時例外
    */
   public boolean isFileEdit(String tempDir) throws IOToolsException, IOException {
       if (!IOTools.isFileCheck(tempDir, GSConstCircular.CIR_EDITCHK_FILENAME, false)) {
           return true;
       }
       File mokulokuFile = new File(
               tempDir + File.separator + GSConstCircular.CIR_EDITCHK_FILENAME);

       FileInputStream fStream = null;
       InputStreamReader isReader = null;
       BufferedReader bufferedreader = null;

       String nText = null;

       HashMap<String, String> mokurokuMap = new HashMap<String, String>();
       HashMap<String, String> nowFileMap = new HashMap<String, String>();

       //現在のファイル情報収集
       Enumeration<File> fileEnum =  IOTools.getFiles(tempDir);

       while (fileEnum.hasMoreElements()) {
           File tempFile = fileEnum.nextElement();
           //ファイル名を取得
           String fileName = tempFile.getName();
           if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
               continue;
           }
           //ファイル名,タイムスタンプをマップ
           nowFileMap.put(fileName, String.valueOf(tempFile.lastModified()));
       }


       //目録ファイル情報収集
       //行数
       long num = 0;
       try {
           //CSVファイルの読み込み
           fStream = new FileInputStream(mokulokuFile);
           isReader = new InputStreamReader(fStream, Encoding.UTF_8);
           bufferedreader = new BufferedReader(isReader);
           char[] cbuf = new char[1024];
           while (true) {
               int rcnt = bufferedreader.read(cbuf, 0, 1024);
               if (rcnt == -1) {
                   break;
               }
               String tmp = NullDefault.getString(nText, "").concat(__getStringFmChar(cbuf, rcnt));

               int idxCrlf = 0;
               while (idxCrlf != -1) {
                   idxCrlf = tmp.indexOf("\r\n");
                   if (idxCrlf == -1) {
                       nText = tmp;
                       break;
                   }
                   String line = tmp.substring(0, idxCrlf);
                   tmp = tmp.substring(idxCrlf + 2, tmp.length());
                   //
                   num++;
                   if (num != 1) {
                       __readMokurokuLine(num, line, mokurokuMap);
                   }
                   //余りの文字列処理
                   if (tmp.length() == idxCrlf + 2) {
                       nText = null;
                   } else {
                       nText = tmp;
                   }
               }
               cbuf = new char[1024];

           }
           //最終行で、改行コードがないものへの対応
           if (nText != null && nText.length() > 0) {
               num++;
               __readMokurokuLine(num, nText, mokurokuMap);
           }
       } finally {
           if (fStream != null) {
               fStream.close();
           }
           if (isReader != null) {
               isReader.close();
           }
           if (bufferedreader != null) {
               bufferedreader.close();
           }
       }

       //比較解析
       if (mokurokuMap.size() != nowFileMap.size()) {
           return true;
       }
       for (String name : nowFileMap.keySet()) {
           String time1 = nowFileMap.get(name);
           String time2 = mokurokuMap.get(name);
           if (!time1.equals(time2)) {
               return true;
           }
       }
       return false;
   }
   /**
    *
    * <br>[機  能] 目録の1行の文字情報をマップに設定
    * <br>[解  説]
    * <br>[備  考]
    * @param num 行数
    * @param nText 読み込んだ情報
    * @param mokurokuMap 設定対象Map
    */
   private void __readMokurokuLine(long num, String nText,
        HashMap<String, String> mokurokuMap) {
       int spp = nText.indexOf(",");
       if (spp > 0 && spp < nText.length()) {
           String name = nText.substring(0, spp);
           String time = nText.substring(spp + 1);
           mokurokuMap.put(name, time);
       }

   }
/**
    * [機 能] バッファから取得したchar配列から必要な長さの文字列を生成する<br>
    * [解 説] <br>
    * [備 考] <br>
    * @param cbuf バッファから読み込んだchar[]
    * @param len 生成する長さ
    * @return 生成したString
    */
   private String __getStringFmChar(char[] cbuf, int len) {
       //
       StringBuilder sb = new StringBuilder();
       sb.append(cbuf, 0, len);
       return sb.toString();
   }


}
