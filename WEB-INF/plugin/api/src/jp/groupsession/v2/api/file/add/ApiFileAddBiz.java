package jp.groupsession.v2.api.file.add;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;

/**
 * <br>[機  能] ファイルの登録を行うWEBAPIビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileAddBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileAddBiz.class);

    /** DBコネクション */
    private final Connection con__;
    /** リクエストモデル */
    private final RequestModel reqMdl__;
    /** ユーザSID */
    private final int usrSid__;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public ApiFileAddBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
        usrSid__ = reqMdl.getSmodel().getUsrsid();
    }

    /**
     * <br>[機  能] ファイルを登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ApiFileAddParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @throws Exception 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ディレクトリSID
     */
    public int registerData(
            ApiFileAddParamModel paramMdl,
            String tempDir,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg) throws Exception, TempFileException {

        CommonBiz cmnbiz = new CommonBiz();
        UDate now = new UDate();

        //テンポラリディレクトリパスにある添付ファイルを全て登録
        List<CmnBinfModel> binList = cmnbiz.insertSameBinInfoForFileKanri(
                con__, tempDir, appRootPath, cntCon, usrSid__, now);

        //新規登録
        return __insertFileInf(
                paramMdl,
                binList,
                cntCon,
                appRootPath,
                pconfig,
                smailPluginUseFlg);

    }

    /**
     * <br>[機  能] 新規登録の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ApiFileAddParamModel
     * @param binList バイナリリスト
     * @param cntCon MlCountMtController
     * @param appRoot ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @throws Exception 実行例外
     * @return ディレクトリSID
     */
    private int __insertFileInf(
            ApiFileAddParamModel paramMdl,
            List<CmnBinfModel> binList,
            MlCountMtController cntCon,
            String appRoot,
            PluginConfig pconfig,
            boolean smailPluginUseFlg)
    throws Exception {

        int dirSid = -1;
        if (binList == null) {
            return dirSid;
        }

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileRekiDao rekiDao = new FileFileRekiDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int version = 1;
        UDate now = new UDate();
        int parentDirSid = NullDefault.getInt(paramMdl.getFdrParentSid(), -1);
        int targetCabSid = -1;

        FileCabinetModel parentCabMdl = getTargetCabinet(paramMdl);
        if (parentCabMdl == null
                || parentCabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return -1;
        }
        targetCabSid = parentCabMdl.getFcbSid();
        UDate tradeDate =
                UDate.getInstanceStr(
                    NullDefault.getString(
                        paramMdl.getFdrErrlDate(),
                        now.getDateString()
                        )
                    );
        tradeDate.setZeroHhMmSs();
        String tradeTarget = paramMdl.getFdrErrlTarget();
        int level;
        if (parentCabMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
            //登録者のデフォルトグループ名を取得
            GroupDao grpDao = new GroupDao(con__);
            CmnGroupmModel grpMdl = grpDao.getDefaultGroup(usrSid__);
            String defGrpName = grpMdl.getGrpName();

            //自動振り分け機能がある場合の、親ディレクトリを取得
            FileDirectoryModel newDirMdl =
                    filBiz.getErrlDirModel(
                            targetCabSid,
                            now,
                            UDate.getInstanceStr(paramMdl.getFdrErrlDate()),
                            tradeTarget, cntCon, usrSid__,
                            defGrpName);
            parentDirSid = newDirMdl.getFdrSid();
            level = newDirMdl.getFdrLevel() + 1;
        } else {
            FileDirectoryModel parDirModel = dirDao.getNewDirectory(parentDirSid);
            level = parDirModel.getFdrLevel() + 1;
        }

        //ディレクトリ情報モデルを設定する。
        FileDirectoryModel dirModel = new FileDirectoryModel();
        dirModel.setFdrVersion(version);
        dirModel.setFcbSid(targetCabSid);
        dirModel.setFdrParentSid(parentDirSid);
        dirModel.setFdrKbn(GSConstFile.DIRECTORY_FILE);
        dirModel.setFdrVerKbn(GSConstFile.VERSION_KBN_OFF);
        dirModel.setFdrBiko("");
        dirModel.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        dirModel.setFdrAuid(usrSid__);
        dirModel.setFdrAdate(now);
        dirModel.setFdrEuid(usrSid__);
        dirModel.setFdrEdate(now);
        dirModel.setFdrLevel(level);

        if (parentCabMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
            //電帳法項目パラメータの設定
            dirModel.setFdrTradeDate(tradeDate);
            dirModel.setFdrTradeTarget(paramMdl.getFdrErrlTarget());
            if (StringUtil.isNullZeroString(paramMdl.getFdrErrlMoney())) {
                dirModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
            } else {
                dirModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);

                String moneyStr = paramMdl.getFdrErrlMoney().replaceAll(",", "");
                BigDecimal money = new BigDecimal(moneyStr);
                dirModel.setFdrTradeMoney(money);

                FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con__);
                FileMoneyMasterModel fmmMdl = fmmDao.select(paramMdl.getFdrErrlMoneyType());
                dirModel.setEmtSid(fmmMdl.getFmmSid());
            }

        }

        //ファイル情報モデルを設定する。
        FileFileBinModel fileBinModel = new FileFileBinModel();
        fileBinModel.setFdrVersion(version);
        fileBinModel.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
        fileBinModel.setFflLockUser(usrSid__);

        //ディレクトリ情報モデルを設定する。
        FileFileRekiModel rekiModel = new FileFileRekiModel();
        rekiModel.setFdrVersion(version);
        rekiModel.setFfrKbn(GSConstFile.REKI_KBN_NEW);
        rekiModel.setFfrEuid(usrSid__);
        rekiModel.setFfrEdate(now);
        rekiModel.setFfrParentSid(parentDirSid);
        if (parentCabMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
            //電帳法項目パラメータの設定
            rekiModel.setFdrTradeDate(dirModel.getFdrTradeDate());
            rekiModel.setFdrTradeTarget(dirModel.getFdrTradeTarget());
            rekiModel.setFdrTradeMoney(dirModel.getFdrTradeMoney());
            rekiModel.setFdrTradeMoneykbn(dirModel.getFdrTradeMoneykbn());
            rekiModel.setEmtSid(dirModel.getEmtSid());
        }

        for (CmnBinfModel binMdl : binList) {
            //ディレクトリSIDを採番する。
            dirSid = (int) cntCon.getSaibanNumber(
                    GSConstFile.PLUGIN_ID_FILE,
                    GSConstFile.SBNSID_SUB_DIRECTORY,
                    usrSid__);

            dirModel.setFdrSid(dirSid);
            dirModel.setFdrName(binMdl.getBinFileName());
            dirDao.insert(dirModel);

            fileBinModel.setFdrSid(dirSid);
            fileBinModel.setBinSid(binMdl.getBinSid());
            fileBinModel.setFflExt(binMdl.getBinFileExtension());
            fileBinModel.setFflFileSize(binMdl.getBinFileSize());
            fileBinDao.insert(fileBinModel);

            rekiModel.setFdrSid(dirSid);
            rekiModel.setFfrFname(binMdl.getBinFileName());
            rekiDao.insert(rekiModel);
            //更新通知を設定する。
            filBiz.updateCall(dirSid, cntCon, appRoot, pconfig, smailPluginUseFlg,
                            usrSid__);

            //集計データを登録する
            int fulUsrSid = 0;
                fulUsrSid = usrSid__;
            int fulGrpSid = -1;

            filBiz.regFileUploadLogCnt(
                    fulUsrSid, fulGrpSid, GSConstFile.LOG_COUNT_KBN_NEW,
                    targetCabSid, dirSid, now);
            //親ディレクトリ配下のアクセス制限を更新
            dirDao.updateAccessSid(dirSid);

        }
        return dirSid;
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param paramMdlFile ファイルデータ
     * @throws IOToolsException IOエラー
     * @throws Exception エラー
     */
    public void setTempFile(String tempDir, FormFile paramMdlFile)
                                               throws IOToolsException, Exception {

        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        int fileNum = 1;
        //添付ファイル名
        String fileName = (new File(paramMdlFile.getFileName())).getName();
        long fileSize = paramMdlFile.getFileSize();
        //添付ファイル(本体)のパスを取得
        File saveFilePath = CommonBiz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイルアップロード
        TempFileUtil.upload(paramMdlFile, tempDir, saveFilePath.getName());

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(fileName);
        fileMdl.setSaveFileName(saveFilePath.getName());
        fileMdl.setAtattiSize(fileSize);

        String[] fileVal = fileMdl.getSaveFileName().split(GSConstCommon.ENDSTR_SAVEFILE);
        log__.debug("*** セーブファイル = " + fileVal[0]);
        fileMdl.setSplitObjName(fileVal[0]);

        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);

    }

    /**
     *
     * <br>[機  能] フォームパラメータから対象キャビネットを取得する
     * <br>[解  説] フォームパラメータ「fcbSid」,「fdrParentSid」の組み合わせからキャビネットを返す
     * <br>[備  考]
     * @param paramMdl
     * @return キャビネットモデル
     * @throws SQLException SQL実行時例外
     */
    public FileCabinetModel getTargetCabinet(
            ApiFileAddParamModel paramMdl) throws SQLException {
        String fcbSid = paramMdl.getFcbSid();
        String fdrParentSid  = paramMdl.getFdrParentSid();

        FileCabinetModel fcbMdl = null;
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        //親ディレクトリが入力されている場合
        if (!StringUtil.isNullZeroString(fdrParentSid)) {

            fcbMdl = filCmnBiz.getCabinetModel(NullDefault.getInt(fdrParentSid, 0));

            //指定したディレクトリを格納しているキャビネットが削除 or 論理削除済みになっている
            if (fcbMdl == null) {
                return null;
            }
            //親ディレクトリが振り分け機能有効の電帳法キャビネットに対して通常登録を実行した
            if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON
                    && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                return null;
            }
            return fcbMdl;
        }
        //キャビネットSIDが入力されている場合
        if (!StringUtil.isNullZeroString(fcbSid)) {
            FileCabinetDao fcbDao = new FileCabinetDao(con__);
            fcbMdl = fcbDao.select(NullDefault.getInt(fcbSid, -1));

            //指定したディレクトリを格納しているキャビネットが削除 or 論理削除済みになっている
            if (fcbMdl == null
                    || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
                return null;
            }
            //fcbSidで指定したキャビネットが振り分け機能を有効にした電帳法キャビネットではない
            if (fcbMdl.getFcbSortFolder() != GSConstFile.SORT_FOLDER_USE) {
                return null;
            }
            return fcbMdl;
        }
        return fcbMdl;

    }


}