package jp.groupsession.v2.fil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.fil.dao.FileAccessConfDao;
import jp.groupsession.v2.fil.dao.FileCabinetAdminDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDatausedSumDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileDownloadLogDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileFileTextDao;
import jp.groupsession.v2.fil.dao.FileLogCountSumDao;
import jp.groupsession.v2.fil.dao.FileShortcutConfDao;
import jp.groupsession.v2.fil.dao.FileUploadLogDao;
import jp.groupsession.v2.fil.extractor.FileTextExtractor;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDatausedSumModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileTextModel;
import jp.groupsession.v2.fil.model.FileLogCountSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] ファイル管理についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(FileBatchListenerImpl.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public FileBatchListenerImpl() {
        super();
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        log__.info("ファイル管理バッチ処理開始");
        String pluginName = "ファイル管理";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        UDate date = new UDate();
        date.setZeroHhMmSs();

        RequestModel reqMdl = new RequestModel();
        reqMdl.setDomain(param.getDomain());
        reqMdl.setLocale(Locale.JAPANESE);

        try {
            //
            FilCommonBiz cBiz = new FilCommonBiz(reqMdl, con);
            FileAconfModel admMdl = cBiz.getFileAconfModel();
            int saveDays = admMdl.getFacSaveDays();

            date.addDay(saveDays * -1);
            log__.debug("削除基準日==>" + date.getDateString());

            //保存期間を過ぎた状態区分=削除のディレクトリを物理削除します
            FileCabinetDao cabDao = new FileCabinetDao(con);

            // 個人キャビネットを使用しない＋削除日付オーバー → 全ての個人キャビネットを削除
            if (admMdl.getFacPersonalKbn() == GSConstFile.CABINET_PRIVATE_NOT_USE
             && admMdl.getFacPersonalEdate() != null
             && admMdl.getFacPersonalEdate().compareDateYMD(date) != UDate.SMALL) {
                // 個人キャビネット一覧取得
                List<FileCabinetModel> cabList =
                        cabDao.selectPersonalCabinet(GSConstFile.CABINET_KBN_PRIVATE);
                if (cabList.size() > 0) {
                    // ルートパス取得
                    GSContext gscontext = GroupSession.getContext();
                    Object tmp = gscontext.get(GSContext.APP_ROOT_PATH);
                    String appRootPath = "";
                    if (tmp != null && (tmp instanceof String)) {
                        appRootPath = (String) tmp;
                    }

                    // 個人キャビネットの論理削除を実行
                    for (FileCabinetModel mdl : cabList) {
                        cBiz.deleteCabinet(mdl.getFcbSid(), appRootPath);
                    }
                }
            }

            //論理削除されたファイルの実体を物理削除 ※対象:共有/個人キャビネット
            __deleteFile(date, con);
            
            //論理削除されたフォルダの実体を物理削除(ファイルが存在しない場合) ※対象:共有/個人キャビネット
            __deleteFolder(con);
            
            //論理削除されたファイルの実体を物理削除(10年経過時) ※対象:電帳法キャビネット -> 最終更新から10年経過した論理削除済みファイル
            date = new UDate();
            date.addYear(-10);
            __deleteFileErrl(date, con);
            
            //論理削除されたフォルダの実体を物理削除 ※対象:電帳法キャビネット -> 最終更新から10年経過した論理削除済みファイル
            __deleteFolderErrl(con);

            //論理削除されたキャビネットに紐づくディレクトリ情報を削除
            __deleteCabinet(GSConstFile.SELECT_CABINET_PUBLIC, con);

            //論理削除された電帳法キャビネットを削除
            //※紐づくファイルが存在しないキャビネットを対象とする
            __deleteCabinet(GSConstFile.SELECT_CABINET_ERRL, con);

            //未登録の統計情報_集計結果を登録
            __entryLogSum(con);

            //ファイル管理 統計情報集計データ削除
            __deleteLog(con);
            con.commit();
            log__.info("使用データサイズの再集計 開始");

            //使用データサイズの再集計
            FileDatausedSumDao dataUsedDao = new FileDatausedSumDao(con);
            dataUsedDao.delete();

            long fileCabinetSize = 0;
            FileCabinetDao cabinetDao = new FileCabinetDao(con);
            List<FileCabinetModel> cabinetList = cabinetDao.select();
            for (FileCabinetModel cabMdl : cabinetList) {
                fileCabinetSize += cabinetDao.getCabinetUsedSize(cabMdl.getFcbSid()).longValue();
            }

            FileDatausedSumModel sumMdl = new FileDatausedSumModel();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            sumMdl.setFileCabinetSize(fileCabinetSize);
            dataUsedDao.insert(sumMdl);


            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getFileCabinetSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstFile.PLUGIN_ID_FILE, usedDisk);

            con.commit();
            log__.info("使用データサイズの再集計 終了");


            commitFlg = true;
            log__.debug("ファイル管理バッチ処理終了");
        } catch (Exception e) {
            log__.error("ファイル管理バッチ処理失敗", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } finally {
            if (commitFlg) {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            } else {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }
    }
    
    /**
     * <br>[機  能] ファイル情報を物理削除する(個人・共有キャビネット)
     * <br>[解  説]
     * <br>[備  考]
     * @param date 比較用日付
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteFile(
            UDate date, Connection con) throws SQLException, IOToolsException {
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        ArrayList<FileDirectoryModel> dirList = dirDao.getDeletedFile(date);
        __deleteDirectoryData(dirList, con);
    }
    
    /**
     * <br>[機  能] フォルダ情報を物理削除する(個人・共有キャビネット)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteFolder(Connection con) throws SQLException, IOToolsException {
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        ArrayList<FileDirectoryModel> dirList = null;
        int level = GSConstFile.DIRECTORY_LEVEL_10;
        for (; level >= GSConstFile.DIRECTORY_LEVEL_1; level--) {
            dirList = dirDao.getDeletedFolder(level);
            __deleteDirectoryData(dirList, con);
        }
    }
    
    /**
     * <br>[機  能] ファイル情報を物理削除する(電帳法キャビネット)
     * <br>[解  説]
     * <br>[備  考]
     * @param date 比較用日付
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteFileErrl(
            UDate date, Connection con) throws SQLException, IOToolsException {
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        ArrayList<FileDirectoryModel> dirList = dirDao.getDeletedFileErrl(date);
        __deleteDirectoryData(dirList, con);
    }
    
    /**
     * <br>[機  能] フォルダ情報を物理削除する(電帳法キャビネット)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteFolderErrl(Connection con) throws SQLException, IOToolsException {
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        //電帳法キャビネット内の論理削除済みフォルダの削除
        ArrayList<FileDirectoryModel> dirList = null;
        int level = GSConstFile.DIRECTORY_LEVEL_10;
        for (; level >= GSConstFile.DIRECTORY_LEVEL_1; level--) {
            dirList = dirDao.getDeletedFolderErrl(level);
            __deleteDirectoryData(dirList, con);
        }
    }
    
    /**
     * <br>[機  能] ディレクトリ情報を物理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param errlKbn 電帳法区分 0:共有/個人キャビネット, 1:電帳法キャビネット
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteCabinet(
            int errlKbn, Connection con) throws SQLException, IOToolsException {
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileCabinetDao cabDao = new FileCabinetDao(con);
        FileCabinetAdminDao fcaDao = new FileCabinetAdminDao(con);
        FileAccessConfDao facDao = new FileAccessConfDao(con);
        
        Map<Integer, Long> cabMap = cabDao.selectLogicalDelCabinet(errlKbn);
        ArrayList<FileDirectoryModel> dirList = null;
        
        List<Long> cabBinsSids = new ArrayList<Long>(cabMap.values());
        __deleteCmnBinf(cabBinsSids, con);
        
        List<Integer> cabSids = new ArrayList<Integer>(cabMap.keySet());
        for (int cabSid : cabSids) {
            dirList = dirDao.getDirectory(cabSid);
            __deleteDirectoryData(dirList, con);
        }
        
        ArrayList<Integer> exeList = new ArrayList<Integer>();
        //100件ずつ実行
        Iterator<Integer> itr = cabSids.iterator();
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < GSConstFile.FIL_BATCH_DELETE_COUNT && itr.hasNext()) {
                continue;
            }
            
            cabDao.delete(exeList);
            fcaDao.delete(exeList);
            facDao.delete(exeList);
            if (errlKbn == GSConstFile.SELECT_CABINET_ERRL) {
                FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
                feaDao.deleteCabinet(exeList);
            }
            exeList.clear();
        }
    }
    
    /**
     * <br>[機  能] ディレクトリ情報を物理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirList 削除対象ディレクトリ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteDirectoryData(ArrayList<FileDirectoryModel> dirList,
            Connection con) throws SQLException, IOToolsException {
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileFileBinDao ffbDao = new FileFileBinDao(con);
        FileDAccessConfDao fdaDao = new FileDAccessConfDao(con);
        FileShortcutConfDao fscDao = new FileShortcutConfDao(con);
        FileFileRekiDao ffrDao = new FileFileRekiDao(con);
        FileCallConfDao fccDao = new FileCallConfDao(con);
        FileCallDataDao fcdDao = new FileCallDataDao(con);
        FileFileTextDao fftDao = new FileFileTextDao(con);
        
        ArrayList<FileDirectoryModel> exeList = new ArrayList<>();
        //100件ずつ実行
        Iterator<FileDirectoryModel> itr = dirList.iterator();
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < GSConstFile.FIL_BATCH_DELETE_COUNT && itr.hasNext()) {
                continue;
            }
            List<Integer> sidList = exeList.stream()
                    .map(mdl -> mdl.getFdrSid())
                    .collect(Collectors.toList());
            
            __deleteCmnFile(exeList, con);
            dirDao.deleteDir(exeList);
            ffbDao.deleteDir(exeList);
            fscDao.deleteDir(exeList);
            fftDao.deleteDir(exeList);
            fdaDao.delete(sidList);
            ffrDao.deleteDir(exeList);
            fccDao.deleteDir(exeList);
            fcdDao.deleteDir(exeList);
            exeList.clear();
        }
        
    }

    /**
     * <br>[機  能] 共通ファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrList ディレクトリ一覧
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteCmnFile(ArrayList<FileDirectoryModel> fdrList, Connection con)
    throws SQLException, IOToolsException {

        FileFileBinDao fileDao = new FileFileBinDao(con);

        //添付ファイルリストを取得
        ArrayList<FileFileBinModel> binList = fileDao.getFileListAllVersion(fdrList);
        if (binList == null || binList.isEmpty()) {
            return;
        }
        
        List<Long> binSids = binList.stream()
                .map(mdl -> mdl.getBinSid())
                .collect(Collectors.toList());
        
        ArrayList<Long> exeList = new ArrayList<Long>();
        //100件ずつ実行
        Iterator<Long> itr = binSids.iterator();
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < GSConstFile.FIL_BATCH_DELETE_COUNT && itr.hasNext()) {
                continue;
            }
            __deleteCmnBinf(exeList, con);
            
            exeList.clear();
        }
    }
    
    /**
     * <br>[機  能] CMN_BINFから対象データを論理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSidList ファイルSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    private void __deleteCmnBinf(
            List<Long> binSidList,
            Connection con) throws SQLException, IOToolsException {

        CmnBinfDao cbDao = new CmnBinfDao(con);
        
        UDate now = new UDate();
        CmnBinfModel cmnBinModel = new CmnBinfModel();
        cmnBinModel.setBinJkbn(GSConst.JTKBN_DELETE);
        cmnBinModel.setBinUpuser(GSConst.SYSTEM_USER_ADMIN);
        cmnBinModel.setBinUpdate(now);

        //バイナリー情報を論理削除する
        cbDao.updateJKbn(cmnBinModel, binSidList);
    }

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception {
    }

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {
        log__.info("ファイル管理5分バッチ処理開始");

        //アプリケーションのルートパスを取得
        String rootPath = "";
        GSContext gscontext = param.getGsContext();
        Object pathObj = gscontext.get(GSContext.APP_ROOT_PATH);
        if (pathObj != null) {
            rootPath = (String) pathObj;
        }

        //全文検索使用時
        if (FilCommonBiz.isUseAllTextSearch(rootPath)) {
            con.setAutoCommit(false);
            String domain = param.getDomain();

            //まだテキストの読込みが完了していないファイル一覧を取得する
            //※通常ファイル
            FileFileTextDao textDao = new FileFileTextDao(con);
            List<FileFileTextModel> list
                = textDao.getNoReadFileList(GSConstFile.JTKBN_NORMAL);
            if (list.size() == 0) {
                log__.info("通常ファイルのテキスト情報内容読込み対象なし");
            } else {
                __entryFileTextList(con, rootPath, list, domain);
            }

            //------ 削除済みファイルのテキスト情報登録 ------

            //登録1:
            //「登録済みテキスト情報の"バージョン"が最新バージョンと異なる」場合
            //テキスト情報のバージョンを更新する
            //「テキスト情報が登録される前に」削除された場合を考慮し、
            //最新バージョンではなく「テキスト情報のバージョン+1」を設定する
            textDao.updateVersionForOldDeleteFile();
            con.commit();

            //登録2:
            //まだテキストの読込みが完了していないファイル一覧を取得する
            //※削除済みファイルを対象とする
            //※登録1完了時点では更新途中(バージョン < 最新バージョン)のファイルも対象
            List<FileFileTextModel> delFileList
                    = textDao.getNoReadFileList(GSConstFile.JTKBN_DELETE);
            if (delFileList.size() == 0) {
                log__.info("削除済みファイルのテキスト情報内容読込み対象なし");
            } else {
                __entryFileTextList(con, rootPath, delFileList, domain);
            }

            //------ 削除済みファイルのテキスト情報登録 ------
        }

        log__.info("ファイル管理5分バッチ処理終了");
    }

    /**
     * ファイルテキスト情報データの削除
     * @param con DBコネクション
     * @param rootPath アプリケーションルートパス
     * @param fileTextList ファイルテキスト情報
     * @param domain ドメイン文字列
     * @throws SQLException SQL実行例外
     * @throws Exception 例外発生
     */
    private void __entryFileTextList(Connection con, String rootPath,
                                    List<FileFileTextModel> fileTextList,
                                    String domain)
    throws SQLException, Exception {

        try {
            //ファイル内容を読み込んで、更新
            int num = 1;

            for (FileFileTextModel bean : fileTextList) {
                //削除
                __deleteFileText(con, bean.getFdrSid());
                //追加
                String tempSubId = StringUtil.toDecFormat(num, "0000000000");
                __insertFileText(domain, con, rootPath, bean, tempSubId);
                num++;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //テンポラリディレクトリのファイルを削除する（※消しそこない防止）
            __deleteM5BatchTempDir(domain);
        }
    }

    /**
     * ファイルテキスト情報データの削除
     * @param con DBコネクション
     * @param fdrSid フォルダSID
     * @throws SQLException SQL実行例外
     */
    private void __deleteFileText(Connection con, int fdrSid) throws SQLException {

        try {
            FileFileTextDao textDao = new FileFileTextDao(con);
            textDao.delete(fdrSid);
            con.commit();
        } catch (SQLException e) {
            JDBCUtil.rollback(con);
            throw e;
        }
    }

    /**
     * ファイルテキスト情報データの追加
     * @param domain ドメイン
     * @param con DBコネクション
     * @param appRoot アプリケーションのルートパス
     * @param textMdl ファイル内容モデル
     * @param tempSubId サブテンポラリディレクトリID
     * @throws Exception 実行例外
     */
    private void __insertFileText(String domain,
                                Connection con,
                                String appRoot, FileFileTextModel textMdl,
                                String tempSubId)
                        throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        FileFileBinDao fileBinDao = new FileFileBinDao(con);
        FilProcessExtractorCallback reg = new FilProcessExtractorCallback(con, textMdl);

        try {
            //バイナリファイル取得
            Long binSid = fileBinDao.getCmnBinSid(textMdl.getFdrSid(), textMdl.getFdrVersion());
            if (binSid == 0) {
                return;
            }

            // 添付ファイル情報を取得する。
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid, domain);
            log__.debug("読込ファイル名->" + cbMdl.getBinFileName());


            String tempDir = __getM5BatchTempDir(domain, tempSubId);
            //添付ファイルをテンポラリディレクトリにコピーする。
            String tempfile = cmnBiz.saveTempFile(cbMdl, appRoot, tempDir);
            if (tempfile == null) {
                return;
            }

            // ファイル中身を抽出
            FileTextExtractor extractor = new FileTextExtractor();
            extractor.setCallback(reg);
            extractor.setMaxLength(500000);
            extractor.read(tempfile);

            // コミット
            con.commit();

        } catch (SQLException e) {
            // エラー
            throw e;
        } catch (Exception e) {
            // エラー
            reg.onError(e);
            log__.warn("ファイル読込みエラー", e);
        } finally {

            //テンポラリディレクトリを削除する
            __deleteM5BatchTempDir(domain, tempSubId);
        }
    }

    /**
     * <br>[機  能] 未登録の統計情報_集計結果を登録
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @exception SQLException SQL実行時例外
     */
    private void __entryLogSum(Connection con) throws SQLException {
        //統計情報の集計を行う
        FileLogCountSumDao logSumDao = new FileLogCountSumDao(con);
        int[] flsKbnList = {GSConstFile.FLS_KBN_DOWNLOAD,
                                GSConstFile.FLS_KBN_UPLOAD
        };
        UDate today = new UDate();
        for (int flsKbn : flsKbnList) {
            List<FileLogCountSumModel> logSumList
                = logSumDao.getNonRegisteredList(flsKbn, today);
            if (logSumList != null && !logSumList.isEmpty()) {
                for (FileLogCountSumModel logSumMdl : logSumList) {
                    if (logSumDao.update(logSumMdl) == 0) {
                        logSumDao.insert(logSumMdl);
                    }
                }
            }
        }
    }

    /**
     * <br>[機  能] 統計情報の集計データを削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @exception SQLException SQL実行時例外
     */
    private void __deleteLog(Connection con) throws SQLException {
        log__.debug("ファイル管理 統計情報集計データ削除処理開始");
        UDate today = new UDate();
        FileDownloadLogDao fdlDao = new FileDownloadLogDao(con);
        int filDlLogCount = fdlDao.delete(today);
        log__.debug("ファイル管理ダウンロード 統計情報集計データ" + filDlLogCount + "件を削除");
        FileUploadLogDao fulDao = new FileUploadLogDao(con);
        int filUlLogCount = fulDao.delete(today);
        log__.debug("ファイル管理アップロード 統計情報集計データ" + filUlLogCount + "件を削除");
    }

    /**
     * <br>[機  能] 5分毎バッチ処理で使用するテンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param subId サブディレクトリID
     * @return テンポラリディレクトリパス
     */
    private String __getM5BatchTempDir(String domain, String... subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getBatchTempPath(domain,
                                            "batch5Min",
                                            GSConstFile.PLUGIN_ID_FILE,
                                            subId);
        tempDir = IOTools.replaceFileSep(tempDir);
        return tempDir;
    }

    /**
     * <br>[機  能] 5分毎バッチ処理で使用するテンポラリディレクトリパスを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param subId サブディレクトリID
     */
    private void __deleteM5BatchTempDir(String domain, String... subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteBatchTempPath(domain,
                                        "batch5Min",
                                        GSConstFile.PLUGIN_ID_FILE,
                                        subId);
    }
}
