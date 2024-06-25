package jp.groupsession.v2.tcd.tcd180kn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 勤務表フォーマット登録確認画面で使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd180knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd180knBiz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd180knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param dirId テンポラリディレクトリID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 取込みファイル操作時例外
     */
    protected void _setInitData(
            Tcd180knParamModel paramMdl,
            Connection con,
            String dirId) throws SQLException, IOToolsException {

        if (paramMdl.getTcd180Use() != 0) {
            //テンポラリディレクトリパスを取得
            String tempDir = _getTempDir(dirId);

            //ファイル名の取得
            String fileName = __getFileName(tempDir);
            paramMdl.setFileName(fileName);
        }
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private String __getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId テンポラリディレクトリID
     * @return テンポラリディレクトリ
     */
    protected String _getTempDir(String dirId) {
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl__,
                GSConstTimecard.PLUGIN_ID_TIMECARD, dirId);

        return tempDir;
    }

    /**
     * <br>[機  能] 取込みファイル名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     * @return String 保存しているファイル名
     */
    protected String _getImportFileName(String tempDir)
        throws SQLException, IOToolsException {

        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                    continue;
                }
                ret = fileName.substring(0, 11);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] フォーマット情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param ctrl コントローラ
     * @throws SQLException
     * @throws IOException
     * @throws InvalidFormatException
     * @throws IOToolsException
     * @throws TempFileException
     * @throws Exception 実行例外
     */
    protected void _insert(Tcd180knParamModel paramMdl,
                                Connection con,
                                String appRoot,
                                String tempDir,
                                MlCountMtController ctrl)
                                        throws SQLException,
                                        InvalidFormatException,
                                        IOException,
                                        IOToolsException,
                                        TempFileException {

        log__.debug("START");
        if (paramMdl.getTcd180Use() == 0) {
            TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
            int usrSid = reqMdl__.getSmodel().getUsrsid();
            TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(usrSid, con);
            TcdAdmConfDao tacDao = new TcdAdmConfDao(con);
            UDate now = new UDate();
            admConf.setTacWorkreportKbn(paramMdl.getTcd180Use());
            admConf.setTacEuid(usrSid);
            admConf.setTacEdate(now);
            tacDao.updateWorkReport(admConf);
        } else {
            __formatInsert(paramMdl, con, appRoot, tempDir, ctrl);
        }
    }

    /**
     * <br>[機  能] 勤務表フォーマットの登録
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @param con
     * @param appRoot
     * @param tempDir
     * @param ctrl
     * @throws SQLException
     * @throws InvalidFormatException
     * @throws IOException
     * @throws IOToolsException
     * @throws TempFileException
     * @return updateFlg
     */
    private boolean __formatInsert(Tcd180knParamModel paramMdl, Connection con,
            String appRoot, String tempDir, MlCountMtController ctrl)
                    throws SQLException, InvalidFormatException,
                    IOException, IOToolsException, TempFileException {

        boolean updateFlg = false;
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);

        String fileName = _getImportFileName(tempDir) + GSConstCommon.ENDSTR_SAVEFILE;
        //アップロードしたフォーマットファイルの基準シートを差し替える
        __setStandardSeat(paramMdl, admConf, appRoot, tempDir, fileName);

        UDate now = new UDate();
        //フォーマットファイルの登録
        CommonBiz cmnBiz = new CommonBiz();
        String outTmpDir = tempDir + GSConstTimecard.TCD_TEMP_FORMAT_EDIT;

        List < String > binSid = cmnBiz.insertBinInfo(con, outTmpDir, appRoot,
                ctrl, sessionUsrSid, now);
        Long binarySid = Long.valueOf(0);
        if (binSid != null) {
            binarySid = Long.parseLong(binSid.get(0));
        }
        admConf.setTacWorkreportKbn(1);
        admConf.setTacWorkreportSid(binarySid);
        admConf.setTacEuid(sessionUsrSid);
        admConf.setTacEdate(now);
        TcdAdmConfDao tacDao = new TcdAdmConfDao(con);
        tacDao.updateWorkReport(admConf);
        return updateFlg;
    }

    /**
     * <br>[機  能] フォーマット情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param admConf タイムカード管理者設定情報
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param fileName ファイル名
     * @throws SQLException
     * @throws IOException
     * @throws InvalidFormatException
     * @throws IOToolsException
     * @throws Exception 実行例外
     */
    private void __setStandardSeat(Tcd180knParamModel paramMdl, 
            TcdAdmConfModel admConf, String appRoot,
            String tempDir, String fileName) 
                    throws InvalidFormatException, IOException, SQLException, IOToolsException {

        //アップロードされたファイルの基準シート削除する
        FileInputStream uploadIn = new FileInputStream(tempDir + fileName);
        Workbook uploadWb = WorkbookFactory.create(uploadIn);
        if (uploadWb.getNumberOfSheets() > 1 && uploadWb.getSheetAt(1) != null) {
            uploadWb.removeSheetAt(1);            
        }

        //想定の基準シートをアップロードされたファイルに追加する
        String standardPath = __getWorkTemplateFilePath(appRoot, admConf);
        FileInputStream in = new FileInputStream(standardPath);
        Workbook wb = WorkbookFactory.create(in);
        Sheet stSheet = wb.getSheetAt(1);

        //アップロードシートに出力
        String baseName = "Sheet2";
        String sheetName = "Sheet2";
        int numberSheet = 0;
        while (uploadWb.getSheet(sheetName) != null) {
            numberSheet++;
            sheetName = baseName + "(" + numberSheet + ")";
        }
        Sheet upSheet = uploadWb.createSheet(sheetName);
        uploadWb.setSheetOrder(sheetName, 1);
        for (int rowNum = 0; rowNum < 38; rowNum++) {
            Row stRow = __getRow(rowNum, stSheet);
            Row upRow = __getRow(rowNum, upSheet);

            for (int cellNum = 0; cellNum < 34; cellNum++) {
                Cell stCell = stRow.getCell(cellNum);
                Cell upCell = upRow.getCell(cellNum);
                if (upCell == null || upCell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    upCell = upRow.createCell(cellNum);
                }

                if (stCell != null) {
                    switch (stCell.getCellType()) {
                    //数値
                    case Cell.CELL_TYPE_NUMERIC:
                        Double numericValue = stCell.getNumericCellValue();
                        if (DateUtil.isCellDateFormatted(stCell)) {
                            Date date = DateUtil.getJavaDate(numericValue);
                            int hour = date.getHours();
                            int minute = date.getMinutes();
                            String minuteStr = String.valueOf(minute);
                            if (minuteStr.length() == 1) {
                                minuteStr = "0" + minuteStr;
                            }
                            String time = String.valueOf(hour) + ":" + minuteStr;
                            __setCellString(upCell, time);
                        } else {
                            __setCellNumeric(upCell, numericValue);
                        }
                        break;
                    //関数
                    case Cell.CELL_TYPE_FORMULA:
                        String formula = stCell.getCellFormula();
                        upCell.setCellFormula(formula);
                        break;
                    //文字列
                    case Cell.CELL_TYPE_STRING:
                        String stringValue = stCell.getStringCellValue();
                        if (stringValue == null) {
                            stringValue = "";
                        }
                        __setCellString(upCell, stringValue);
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        __setCellString(upCell, "");
                        break;
                    }
                    CellStyle stStyle = stCell.getCellStyle();
                    CellStyle upStyle = uploadWb.createCellStyle();
                    upStyle.cloneStyleFrom(stStyle);
                    upCell.setCellStyle(upStyle);
                } else {
                    __setCellString(upCell, "");
                }
            }

        }
        //ファイルをテンポラリディレクトリに出力
        String outPath = tempDir + "/" + GSConstTimecard.TCD_TEMP_FORMAT_EDIT + "/";
        IOTools.isDirCheck(tempDir, true);
        OutputStream oStream = new FileOutputStream(outPath + fileName);
        String dateStr = (new UDate()).getDateString();
        //オブジェクトファイルを取得
        File objFilePath = Cmn110Biz.getObjFilePath(outPath, dateStr, 0);
        File saveFilePath = Cmn110Biz.getSaveFilePath(outPath, dateStr, 0);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        String tempName = __getTempName(paramMdl, tempDir);
        paramMdl.setFileName(tempName);
        fileMdl.setFileName(tempName);
        fileMdl.setSaveFileName(saveFilePath.getName());
        fileMdl.setUpdateKbn(0);
        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);
        uploadWb.write(oStream);

    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 取込みファイル操作時例外
     * @return fileName
     */
    private String __getTempName(
            Tcd180knParamModel paramMdl,
            String tempDir) throws SQLException, IOToolsException {

        //テンポラリディレクトリパスを取得
        String dateStr = (new UDate()).getDateString();
        //オブジェクトファイルを取得
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, 0);

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

        //ファイル名の取得
        String fileName = fMdl.getFileName();
        return fileName;
    }


    /**
     * <p>行を返す
     * @param index 行数
     * @param sheet
     * @return 行
     */
    private Row __getRow(int index, Sheet sheet) {
        Row row = sheet.getRow(index);
        if (row == null) {
            row = sheet.createRow(index);
        }
        return row;
    }

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
     * <p>セルにStringをセットするNUMERIC
     * @param cell 対象のセル
     * @param i セットする数値
     * @return セル
     */
    private Cell __setCellNumeric(Cell cell, double i) {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(i);
        return cell;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから勤務表テンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @param admMdl 基本設定
     * @return テンプレートファイルのパス文字列
     */
    private static String __getWorkTemplateFilePath(String appRootPath, TcdAdmConfModel admMdl) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String filename = "workReportFormat.xlsx";
        if (admMdl.getTacKansan() == GSConstTimecard.TIMECARD_SINSU[1]) {
            //60進数
            filename = "workReportFormat60.xlsx";
        }
        String ret = IOTools.replaceFileSep(appRootPath
                + "WEB-INF/plugin/timecard/template/" + filename);
        return ret;
    }
}
