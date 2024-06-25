package jp.groupsession.v2.ntp.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.model.NtpContactModel;
import jp.groupsession.v2.ntp.model.NtpProcessModel;
import jp.groupsession.v2.ntp.ntp150.Ntp150ProcessDao;

/**
 * <br>[機  能] インポートサンプルExcelの出力を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpSampleAnkenExcelWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpSampleAnkenExcelWriter.class);

    /** BOOK */
    private static Workbook workbook = null;
    /** Sheet */
    private static Sheet sheet = null;

    /** フォーマットファイル名 */
    private static String formatFile__ = null;
    /** 出力ファイル名 */
    private static String outputFile__ = null;

    /** リクエスト情報  */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] インポートサンプルExcelのダウンロードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param data インポートサンプルExcel出力用データ
     * @param reqMdl リクエスト情報
     * @throws Exception インポートサンプルExcelのダウンロードに失敗
     */
    public synchronized void downloadExcel(HttpServletRequest req, HttpServletResponse res,
                                              Connection con, NtpSampleExcelModel data,
                                              RequestModel reqMdl)
    throws Exception {

        formatFile__ = data.getFormatFile();
        outputFile__ = data.getOutPutFile();
        reqMdl__ = reqMdl;

        File outFilePath = new File(getOutputFilePath(data));

        try {
            __createExcel(con, data);
            TempFileUtil.downloadAtachment(req, res, outFilePath, outFilePath.getName(),
                                           Encoding.UTF_8);

        } catch (Exception e) {
            log__.error("インポートサンプルExcelのダウンロードに失敗", e);
            throw e;
        } finally {
            //出力先ディレクトリを削除
            deleteOutputDir();
        }

    }

    /**
     * <br>[機  能] Excelファイルの生成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param data インポートサンプルExcel出力用データ
     * @throws Exception インポートサンプルExcelファイルの生成に失敗
     */
    private synchronized void __createExcel(Connection con, NtpSampleExcelModel data)
    throws Exception {
        log__.debug("*** Excelファイル生成start ***");

        FileInputStream in = null;
        FileOutputStream ostream = null;

        try {

            //Excelテンプレートパス取得
            String path = __getTemplateFilePath(data.getAppRootPath());
            log__.debug(">>>path :" + path);
            in = new FileInputStream(path);
            workbook = WorkbookFactory.create(in);

            log__.debug("企業コード一覧作成");
            //企業コードシート取得
            sheet = workbook.getSheetAt(1);

            AdrCompanyDao adrDao = new AdrCompanyDao(con);
            List<AdrCompanyModel> adrList = adrDao.select();
            Iterator<AdrCompanyModel> it = adrList.iterator();
            AdrCompanyModel adrData = null;

            if (adrList != null && !adrList.isEmpty()) {
                for (int i = 1; i <= adrList.size(); i++) {

                    if (it.hasNext()) {
                        adrData = (AdrCompanyModel) it.next();
                    } else {
                        adrData = new AdrCompanyModel();
                    }


                    Row row = __getRow(i);
                    //企業名
                    Cell clKigyouName = __getCell(row, 0);
                    if (!StringUtil.isNullZeroStringSpace(adrData.getAcoName())) {
                        __setCellString(clKigyouName, adrData.getAcoName());
                    } else {
                        __setCellString(clKigyouName, "");
                    }

                    //企業コード
                    Cell clCode = __getCell(row, 1);
                    if (!StringUtil.isNullZeroStringSpace(adrData.getAcoCode())) {
                        __setCellString(clCode, adrData.getAcoCode());
                    } else {
                        __setCellString(clCode, "");
                    }
                }

                //Excelファイルを生成する
                File file = new File(getOutputFilePath(data));
                IOTools.isDirCheck(file.getParent(), true);
                ostream = new FileOutputStream(file);
                workbook.write(ostream);
            }


            //業種・プロセスコードシート
            sheet = workbook.getSheetAt(2);
            Ntp150ProcessDao processDao = new Ntp150ProcessDao(con);
            List<NtpProcessModel> processList = processDao.select();
            Iterator<NtpProcessModel> itprocess = processList.iterator();
            NtpProcessModel processData = null;


            itprocess = processList.iterator();
            processData = null;

            if (processList != null && !processList.isEmpty()) {
                for (int i = 1; i <= processList.size(); i++) {

                    if (itprocess.hasNext()) {
                        processData = (NtpProcessModel) itprocess.next();
                    } else {
                        processData = new NtpProcessModel();
                    }


                    Row row = __getRow(i);
                    //業務名
                    Cell clGyoumuName = __getCell(row, 0);
                    if (!StringUtil.isNullZeroStringSpace(processData.getNgyName())) {
                        __setCellString(clGyoumuName, processData.getNgyName());
                    } else {
                        __setCellString(clGyoumuName, "");
                    }

                    //業務コード
                    Cell ngyCode = __getCell(row, 1);
                    if (!StringUtil.isNullZeroStringSpace(processData.getNgyCode())) {
                        __setCellString(ngyCode, processData.getNgyCode());
                    } else {
                        __setCellString(ngyCode, "");
                    }

                    //プロセス名
                    Cell clProcessName = __getCell(row, 2);
                    if (!StringUtil.isNullZeroStringSpace(processData.getNgpName())) {
                        __setCellString(clProcessName, processData.getNgpName());
                    } else {
                        __setCellString(clProcessName, "");
                    }

                    //プロセスコード
                    Cell ngpCode = __getCell(row, 3);
                    if (!StringUtil.isNullZeroStringSpace(processData.getNgpCode())) {
                        __setCellString(ngpCode, processData.getNgpCode());
                    } else {
                        __setCellString(ngpCode, "");
                    }
                }


                //顧客源泉コードシート
                sheet = workbook.getSheetAt(3);
                NtpContactDao contactDao = new NtpContactDao(con);
                List<NtpContactModel> contactList = contactDao.select();
                Iterator<NtpContactModel> itcontact = contactList.iterator();
                NtpContactModel contactData = null;

                itcontact = contactList.iterator();
                contactData = null;

                if (contactList != null && !contactList.isEmpty()) {
                    for (int i = 1; i <= contactList.size(); i++) {

                        if (itcontact.hasNext()) {
                            contactData = (NtpContactModel) itcontact.next();
                        } else {
                            contactData = new NtpContactModel();
                        }


                        Row row = __getRow(i);
                        //顧客源泉名
                        Cell clContactName = __getCell(row, 0);
                        if (!StringUtil.isNullZeroStringSpace(contactData.getNcnName())) {
                            __setCellString(clContactName, contactData.getNcnName());
                        } else {
                            __setCellString(clContactName, "");
                        }

                        //顧客源泉コード
                        Cell contactCode = __getCell(row, 1);
                        if (!StringUtil.isNullZeroStringSpace(contactData.getNcnCode())) {
                            __setCellString(contactCode, contactData.getNcnCode());
                        } else {
                            __setCellString(contactCode, "");
                        }
                    }
                }


                //Excelファイルを生成する
                File file = new File(getOutputFilePath(data));
                IOTools.isDirCheck(file.getParent(), true);
                ostream = new FileOutputStream(file);
                workbook.write(ostream);
            }



        } catch (IOException e) {
            throw new Exception("インポートサンプルExcelファイルの生成に失敗", e);
        } finally {
            try {
                in.close();
            } catch (Exception e) { }

            try {
                ostream.close();
            } catch (Exception e) { }
        }
        log__.debug("end");
    }

    /**
     * <br>[機  能] 出力先ディレクトリのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 出力先ディレクトリのパス
     */
    public String getOutputDir() {
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz();
        return ntpCmnBiz.getTempDir(reqMdl__, "kei100p");
    }

    /**
     * <br>[機  能] 出力先ディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void deleteOutputDir() {
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz();
        ntpCmnBiz.deleteTempDir(reqMdl__, "kei100p");
    }

    /**
     * <br>[機  能] 出力ファイルのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param data 供給計画実績対比一覧Excel出力用データ
     * @return 出力ファイルのパス
     */
    public String getOutputFilePath(NtpSampleExcelModel data) {
        String path = IOTools.setEndPathChar(getOutputDir()) + outputFile__;
        path = IOTools.replaceFileSep(path);

        return path;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから供給計画実績対比一覧Excelテンプレートパスを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private static String __getTemplateFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = appRootPath + "nippou/doc/";
        ret += formatFile__;

        return IOTools.replaceFileSep(ret);
    }

//    /**
//     * <br>[機  能] セルにStringをセットする
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rowIdx 行数
//     * @param colIdx 列数
//     * @param str セットする文字列
//     * @return セル
//     */
//    private HSSFCell __setCellString(int rowIdx, int colIdx, String str) {
//        return __setCellString(__getCell(rowIdx, colIdx), str);
//    }

//    /**
//     * <br>[機  能] セルにStringをセットする
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rowIdx 行数
//     * @param colIdx 列数
//     * @param styleRowIdx 設定するスタイルの行数
//     * @param styleColIdx 設定するスタイルの列数
//     * @param str セットする文字列
//     * @return セル
//     * @throws Exception 文字列の設定に設定
//     */
//    private HSSFCell __setCellStringWithStyle(int rowIdx, int colIdx,
//                                            int styleRowIdx, int styleColIdx,
//                                            String str)
//    throws Exception {
//        HSSFCell cell = __createCellWithStyle(rowIdx, colIdx, styleRowIdx, styleColIdx);
//        return __setCellString(cell, str);
//    }
//
//    /**
//     * <br>[機  能] セルにStringをセットする
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rowIdx 行数
//     * @param colIdx 列数
//     * @param styleRowIdx 設定するスタイルの行数
//     * @param styleColIdx 設定するスタイルの列数
//     * @param str セットする文字列
//     * @return セル
//     * @throws Exception 文字列の設定に設定
//     */
//    private HSSFCell __setCellNumericWithStyle(int rowIdx, int colIdx,
//                                            int styleRowIdx, int styleColIdx,
//                                            String str)
//    throws Exception {
//        HSSFCell cell = __createCellWithStyle(rowIdx, colIdx, styleRowIdx, styleColIdx);
//        return __setCellNumeric(cell, str);
//    }

//    /**
//     * <br>[機  能] セルにStringをセットする
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param cell 対象のセル
//     * @param str セットする文字列
//     * @return セル
//     */
//    private HSSFCell __setCellString(HSSFCell cell, String str) {
//
//        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//        cell.setCellValue(str);
//
//        return cell;
//    }

    /**
     * <p>セルにStringをセットする
     * @param cell 対象のセル
     * @param str セットする文字列
     * @return セル
     */
    private Cell __setCellString(Cell cell, String str) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(str);
        return cell;
    }

    /**
     * <p>行を返す
     * @param index 行数
     * @return 行
     */
    private Row __getRow(int index) {
        Row row = sheet.getRow(index);
        if (row == null) {
            row = sheet.createRow(index);
        }
        return row;
    }

    /**
     * <p>セルを返す
     * @param row 行
     * @param index 取得するセルのインデックス
     * @return 取得したセル
     */
    private Cell __getCell(Row row, int index) {
        Cell cell = row.createCell(index);
        return cell;
    }
}