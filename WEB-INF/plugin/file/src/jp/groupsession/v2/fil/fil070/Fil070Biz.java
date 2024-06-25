package jp.groupsession.v2.fil.fil070;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.dao.FileDAccessUserDao;
import jp.groupsession.v2.fil.dao.FileDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileShortcutConfDao;
import jp.groupsession.v2.fil.fil070.dao.Fil070Dao;
import jp.groupsession.v2.fil.fil070.model.Fil070Model;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDAccessUserModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinDspModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;
import jp.groupsession.v2.fil.model.FileModel;
import jp.groupsession.v2.fil.model.FileShortcutConfModel;
import jp.groupsession.v2.fil.model.FileUconfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル詳細画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil070Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil070Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param buMdl ユーザモデル
     * @return true:取得成功 false:取得失敗
     * @throws SQLException SQL実行例外
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public boolean setInitData(
            Fil070ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException, UnsupportedEncodingException {

        log__.debug("fil070Biz Start");

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);

        //個人設定が無い場合は初期値で登録する。
        filBiz.getUserConf(buMdl.getUsrsid());

        //管理設定バージョン管理区分の設定
        paramMdl.setAdmVerKbn(filBiz.getVerKbnAdmin());

        //キャビネットデータを取得する。
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileCabinetModel cabModel = null;
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
        if (dirModel != null) {
            //paramMdl.setFil050ParentDirSid(String.valueOf(dirModel.getFdrSid()));
            FileCabinetDao cabDao = new FileCabinetDao(con__);
            cabModel = cabDao.select(dirModel.getFcbSid());
            if (cabModel != null) {
                paramMdl.setFil040PersonalFlg(cabModel.getFcbPersonalFlg());
                if (cabModel.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                    String userName = filBiz.getUserName(cabModel.getFcbOwnerSid());
                    FileAconfModel aconf = filBiz.getFileAconfModel();
                    cabModel.setFcbName(userName);
                    cabModel.setFileAconf(aconf);
                    paramMdl.setFil040PersonalCabOwnerSid(cabModel.getFcbOwnerSid());
                    paramMdl.setFil040PersonalCabOwnerName(userName);

                    paramMdl.setCabinetKbn(GSConstFile.CABINET_KBN_PRIVATE);
                } else if (cabModel.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
                    paramMdl.setCabinetKbn(GSConstFile.CABINET_KBN_ERRL);
                }
                paramMdl.setLogicalDelKbn(
                        (cabModel.getFcbJkbn() == GSConstFile.JTKBN_DELETE
                            || dirModel.getFdrJtkbn() == GSConstFile.JTKBN_DELETE)
                        );
                //ディレクトリが未選択の場合、ディレクトリSIDを設定
                if (NullDefault.getInt(paramMdl.getFil010SelectDirSid(), -1) < 0) {
                    paramMdl.setFil010SelectDirSid(String.valueOf(dirSid));
                }

                //キャビネットが未選択の場合、キャビネットSIDを設定
                if (NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1) < 0) {
                    paramMdl.setFil010SelectCabinet(String.valueOf(dirModel.getFcbSid()));
                }


            }
        }

        //DBより初期値を設定する。
        if (!__setData(paramMdl, buMdl, cabModel)) {
            return false;
        }

        if (NullDefault.getString(
                paramMdl.getFil070DspMode(), GSConstFile.DSP_MODE_HIST).equals(
                        GSConstFile.DSP_MODE_HIST)) {
            //更新履歴一覧を設定する。
            __setUpdateReki(paramMdl, buMdl);
        } else {
            //アクセス制御取得
            __setAccessList(paramMdl, buMdl);
        }

        //ファイル詳細のURLを作成
        paramMdl.setFileUrl(__createFileUrl(dirSid));

        //ファイル編集権限を設定
        paramMdl.setFil070EditAuthKbn(GSConstFile.DSP_KBN_OFF);
        if (filBiz.isEditCabinetUser(cabModel, dirSid)) {
            paramMdl.setFil070EditAuthKbn(GSConstFile.DSP_KBN_ON);
            int lockKbn = __isLockFile(dirSid, buMdl.getUsrsid(), paramMdl);
            paramMdl.setFil070FileLockKbn(String.valueOf(lockKbn));
        }

        return true;
    }

    /**
     * <br>[機  能] DB登録値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param buMdl ユーザモデル
     * @param cabModel キャビネットモデル
     * @return true:取得成功 false:取得失敗
     * @throws SQLException SQL実行例外
     */
    private boolean __setData(Fil070ParamModel paramMdl, BaseUserModel buMdl,
            FileCabinetModel cabModel)
    throws SQLException {

        boolean success = false;
        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), 0);

        FileDao fileDao = new FileDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        //ファイル情報を取得する。
        FileModel fileModel = fileDao.getFileInf(dirSid, buMdl.getUsrsid(), true);
        if (fileModel != null) {
            //フォルダパス
            paramMdl.setFil070FolderPath(
                    filBiz.getDirctoryPath(
                            fileModel.getFdrParentSid(),
                            true
                            ));
            //フォルダパスに対応するSIDを保管
            paramMdl.setFil070ParentDirSid(String.valueOf(fileModel.getFdrParentSid()));
            //ファイルサイズ
            paramMdl.setFil070FileSize(__transToKb(fileModel.getFflFileSize()));
            //バージョン
            if (paramMdl.getAdmVerKbn() == GSConstFile.VERSION_KBN_ON) {
                __setVersion(paramMdl, cabModel, fileModel.getFdrVerKbn());
            }
            //備考
            paramMdl.setFil070Biko(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(fileModel.getFdrBiko()), ""));
            //添付ファイル情報を設定する
            __setFileList(paramMdl, buMdl, fileModel.getFdrVersion());
            //ショートカット
            if (fileModel.getShortcutCount() > 0) {
                //登録済み
                paramMdl.setFil070ShortcutKbn(String.valueOf(GSConstFile.SHORTCUT_ON));
            } else {
                //未登録
                paramMdl.setFil070ShortcutKbn(String.valueOf(GSConstFile.SHORTCUT_OFF));
            }
            paramMdl.setFil070Jtkbn(fileModel.getFdrJtkbn());
            success = true;
        }
        return success;
    }

    /**
     * <br>[機  能] ファイルサイズの単位をＢからＫＢに変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fileSize ファイルサイズ
     * @return fileSize ファイルサイズ（表示用）
     */
    private String __transToKb(int fileSize) {
        if (fileSize == 0) {
            return "0KB";
        }
        BigDecimal sizeB = new BigDecimal(fileSize);

        String sizeKB = StringUtil.toCommaFromBigDecimal(
                sizeB.divide(GSConstFile.KB_TO_MB, 1, RoundingMode.HALF_UP));

        return sizeKB + "KB";
    }

    /**
     * <br>[機  能] バージョン管理区分を表示用に変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param verKbn バージョン管理区分
     * @return version バージョン（表示用）
     */
    private String __transVersionToDsp(int verKbn) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (verKbn < 1) {
            return gsMsg.getMessage("fil.22");
        }

        return gsMsg.getMessage("fil.generations", new String[] {String.valueOf(verKbn)});
    }

    /**
     * <br>[機  能] 更新履歴一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行例外
     */
    private void __setUpdateReki(
            Fil070ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        int orderKey = NullDefault.getInt(paramMdl.getFil070OrderKey(), 0);
        int sortKey = NullDefault.getInt(paramMdl.getFil070SortKey(), 0);
        int fdrSid = NullDefault.getInt(paramMdl.getFil070DirSid(), 0);
        Fil070Dao fil070Dao = new Fil070Dao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        //件数カウント
        long maxCount = fil070Dao.countRekiList(fdrSid);
        log__.debug("件数 = " + maxCount);

        //ファイル管理個人設定を取得する。
        FileUconfModel model = filBiz.getUserConf(buMdl.getUsrsid());

        //履歴表示件数
        int limit = model.getFucRirekiCnt();
        //現在ページ
        int nowPage = paramMdl.getFil070PageNum1();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }

        //ページコンボを設定する。
        paramMdl.setFil070PageNum1(nowPage);
        paramMdl.setFil070PageNum2(nowPage);
        paramMdl.setFil070PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return;
        }

        //最新バージョンを取得する。
        int newVersion = filBiz.getNewVersion(fdrSid);

        //更新履歴一覧を取得する。
        List<Fil070Model> rekiList = fil070Dao.getRekiList(
                fdrSid, orderKey, sortKey, start, limit, newVersion);

        paramMdl.setFil070RekiList(rekiList);

    }

    /**
     * <br>[機  能] アクセス制御一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param buMdl セッションユーザモデル
     * @throws SQLException 実行例外
     */
    private void __setAccessList(Fil070ParamModel paramMdl, BaseUserModel buMdl)
            throws SQLException {

        int orderKey = NullDefault.getInt(paramMdl.getFil070OrderKey(), 0);
        int sortKey = NullDefault.getInt(paramMdl.getFil070SortKey(), 0);
        int fdrSid = NullDefault.getInt(paramMdl.getFil070DirSid(), 0);
        FileDAccessUserDao dao = new FileDAccessUserDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        FileCabinetModel fcbMdl = filBiz.getCabinetModel(fdrSid, true);

        //件数カウント
        long maxCount = dao.getAccessListCount(fcbMdl, fdrSid, null);
        log__.debug("件数 = " + maxCount);

        //ファイル管理個人設定を取得する。
        FileUconfModel model = filBiz.getUserConf(buMdl.getUsrsid());

        //アクセス制御表示件数
        int limit = model.getFucRirekiCnt();
        //現在ページ
        int nowPage = paramMdl.getFil070PageNum1();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }

        //ページコンボを設定する。
        paramMdl.setFil070PageNum1(nowPage);
        paramMdl.setFil070PageNum2(nowPage);
        paramMdl.setFil070PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return;
        }

        ArrayList<FileDAccessUserModel> accessList = dao.getAccessList(
                fcbMdl, fdrSid, sortKey, orderKey, start, limit, null);
        paramMdl.setFil070AccessList(accessList);

    }

    /**
     * <br>[機  能] ショートカット情報の設定・削除を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param shortcutKbn ショートカット区分
     * @param buMdl ユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void updateShortcut(Fil070ParamModel paramMdl, int shortcutKbn, BaseUserModel buMdl)
    throws SQLException {

        FileShortcutConfDao shortcutDao = new FileShortcutConfDao(con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), 0);

        int sessionUsrSid = buMdl.getUsrsid();

        if (shortcutKbn == GSConstFile.SHORTCUT_ON) {

            //ショートカット設定を登録する。
            UDate now = new UDate();
            FileShortcutConfModel scModel = new FileShortcutConfModel();
            scModel.setFdrSid(dirSid);
            scModel.setUsrSid(sessionUsrSid);
            scModel.setFscAdate(now);
            shortcutDao.insert(scModel);

        } else if (shortcutKbn == GSConstFile.SHORTCUT_OFF)  {

            //ショートカット情報を削除する。
            shortcutDao.delete(dirSid, sessionUsrSid);
        }

    }

    /**
     * <br>[機  能] 更新履歴情報の削除を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param buMdl BaseUserModel
     * @throws SQLException SQL実行例外
     */
    public void deleteCallData(Fil070ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        FileCallDataDao callDataDao = new FileCallDataDao(con__);
        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), 0);

        //削除処理
        boolean commitFlg = false;
        try {
            callDataDao.delete(dirSid, buMdl.getUsrsid());
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }

    }

    /**
     * <br>[機  能] ファイルの復旧を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl Fil070ParamModel
     * @param buMdl ユーザモデル
     * @param appRoot アプリケーションルートパス
     * @param cntCon MlCountMtController
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @throws Exception SQL実行例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException ファイル操作時例外
     */
    public void updateRepair(
            RequestModel reqMdl, Fil070ParamModel paramMdl,
            BaseUserModel buMdl, String appRoot,
            MlCountMtController cntCon, PluginConfig pconfig, boolean smailPluginUseFlg)
    throws Exception, IOToolsException, IOException {

        int dirSid = NullDefault.getInt(paramMdl.getFil070SltDirSid(), -1);
        int version = NullDefault.getInt(paramMdl.getFil070SltDirVer(), -1);
        int sessionUsrSid = buMdl.getUsrsid();
        UDate now = new UDate();

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileRekiDao fileRekiDao = new FileFileRekiDao(con__);

        //最新バージョン + 1を取得
        int nextVersion = filBiz.getNextVersion(dirSid);

        //ディレクトリ情報
        FileDirectoryModel dirModel = dirDao.select(dirSid, version);
        if (dirModel != null) {
            dirModel.setFdrVersion(nextVersion);
            dirModel.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
            dirModel.setFdrAdate(now);
            dirModel.setFdrAuid(sessionUsrSid);
            dirModel.setFdrEdate(now);
            dirModel.setFdrEuid(sessionUsrSid);
            dirDao.insert(dirModel);
        } else {
            dirModel = new FileDirectoryModel();
        }

        FileDirectoryModel bean = new FileDirectoryModel();
        bean.setFdrSid(dirSid);
        bean.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        bean.setFdrEuid(sessionUsrSid);
        bean.setFdrEgid(dirModel.getFdrEgid());
        bean.setFdrEdate(now);
        dirDao.updateJtkbn(bean);

        //ファイル情報
        FileFileBinModel fileBinModel = fileBinDao.select(dirSid, version);

        if (fileBinModel != null) {

            Long newBinSid = filBiz.copyFile(
                    appRoot, fileBinModel.getBinSid(), cntCon);

            fileBinModel.setBinSid(newBinSid);
            fileBinModel.setFdrVersion(nextVersion);
            fileBinModel.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
            fileBinDao.insert(fileBinModel);
        }


        //更新履歴情報
        FileFileRekiModel fileRekiModel = fileRekiDao.select(dirSid, version);
        if (fileRekiModel != null) {
            fileRekiModel.setFdrVersion(nextVersion);
            fileRekiModel.setFfrEuid(sessionUsrSid);
            fileRekiModel.setFfrEdate(now);
            fileRekiDao.insert(fileRekiModel);
        }

        //バージョン管理外のファイルを削除する。
        __deleteOldVersion(paramMdl, nextVersion - 1, sessionUsrSid);

        //ディレクトリアクセス設定を更新
        dirDao.updateAccessSid(dirSid);

        //更新通知設定
        filBiz.updateCall(dirSid, cntCon, appRoot, pconfig, smailPluginUseFlg,
                        sessionUsrSid, true);
    }

    /**
     * <br>[機  能] ファイル詳細URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dirSid ディレクトリSID
     * @return ファイルURL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createFileUrl(int dirSid)
    throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl +=  "/" + GSConstFile.PLUGIN_ID_FILE;
            paramUrl += "/fil070.do";
            paramUrl += "?fil070DirSid=" + dirSid;

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    /**
     * <br>[機  能] バージョン管理の表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param cabModel キャビネット情報
     * @param verKbn バージョン管理区分
     * @throws SQLException SQL実行時例外
     */
    private void __setVersion(Fil070ParamModel paramMdl, FileCabinetModel cabModel, int verKbn)
    throws SQLException {
        if (cabModel != null && cabModel.getFcbVerallKbn() == GSConstFile.VERSION_ALL_KBN_ON) {
            paramMdl.setFil070VerKbn(__transVersionToDsp(cabModel.getFcbVerKbn()));
            return;
        }

        paramMdl.setFil070VerKbn(__transVersionToDsp(verKbn));

    }

    /**
     * <br>[機  能] ファイル情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param buMdl ユーザモデル
     * @param version バージョン
     * @throws SQLException SQL実行例外
     */
    private void __setFileList(Fil070ParamModel paramMdl, BaseUserModel buMdl, int version)
    throws SQLException {

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), 0);

        //添付ファイル情報を取得する
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        List<CmnBinfModel> cmBinList = fileBinDao.getBinList(dirSid, version);

        if (cmBinList == null || cmBinList.size() < 1) {
            return;
        }

        List<LabelValueBean> fileLabel = new ArrayList<LabelValueBean>();
        for (CmnBinfModel cbModel : cmBinList) {
            fileLabel.add(new LabelValueBean(
                    cbModel.getBinFileName(), String.valueOf(cbModel.getBinSid())));
        }
        //添付ファイルのラベルを設定する。
        paramMdl.setFil070FileLabelList(fileLabel);

    }

    /**
     * <br>[機  能] バージョン管理外のファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param newVersion 最新バージョン
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __deleteOldVersion(Fil070ParamModel paramMdl, int newVersion, int sessionUsrSid)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);


        FileCabinetModel cabMdl = filBiz.getCabinetModel(dirSid, true);
        if (cabMdl == null) {
            //削除データ無し
            return;
        }
        if (cabMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
            //※電帳法キャビネットは全世代管理の為、削除しない。
            //削除データ無し
            return;
        }

        //バージョン管理区分を取得する。
        int verKbn = filBiz.getVerKbn(cabMdl.getFcbSid(), dirSid, true);
        int delVersion = newVersion - verKbn + 1;
        if (delVersion < 1) {
            //削除データ無し
            return;
        }

        //管理しないディレクトリ情報を削除する。
        dirDao.deleteOldVersion(dirSid, delVersion);

        //ファイル情報を削除する。
        __deleteOldFile(paramMdl, delVersion, sessionUsrSid);

    }

    /**
     * <br>[機  能] バージョン管理外のファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil070ParamModel
     * @param delVersion 削除基準バージョン
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __deleteOldFile(Fil070ParamModel paramMdl, int delVersion, int sessionUsrSid)
    throws SQLException {

        FileFileBinDao fileDao = new FileFileBinDao(con__);
        CmnBinfDao cbDao = new CmnBinfDao(con__);
        CmnBinfModel cmnBinModel = new CmnBinfModel();
        UDate now = new UDate();
        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);

        //削除対象ファイルリスト
        List<FileFileBinModel> delList = fileDao.getOldVersion(dirSid, delVersion);

        if (delList != null && delList.size() > 0) {
            List<Long> binSidList = new ArrayList<Long>();
            for (FileFileBinModel binModel : delList) {
                binSidList.add(binModel.getBinSid());
            }
            cmnBinModel.setBinJkbn(GSConst.JTKBN_DELETE);
            cmnBinModel.setBinUpuser(sessionUsrSid);
            cmnBinModel.setBinUpdate(now);

            //バイナリー情報を論理削除する
            cbDao.updateJKbn(cmnBinModel, binSidList);
        }

        //ファイル情報を削除する。
        fileDao.deleteOldVersion(dirSid, delVersion);
    }

    /**
     * <br>[機  能] ファイルがロックされていないかを判定する。
     * <br>[解  説] ファイルロックされている場合編集者を設定する。
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param usrSid ユーザSID
     * @param paramMdl Fil070ParamModel
     * @return ロック区分　0:アンロック　1:ロック
     * @throws SQLException SQL実行例外
     */
    private int __isLockFile(
            int dirSid, int usrSid, Fil070ParamModel paramMdl) throws SQLException {

        int lockKbn = GSConstFile.LOCK_KBN_OFF;
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        //管理者設定ロック有効無効を判定する。
        int lockKbnAdmin = filBiz.getLockKbnAdmin();
        if (lockKbnAdmin == GSConstFile.LOCK_KBN_OFF) {
            return lockKbn;
        }

        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileBinDspModel fileBinDspModel = fileBinDao.getNewFileUsrName(dirSid);

        if (fileBinDspModel == null) {
            return lockKbn;
        }

        if (fileBinDspModel.getFflLockKbn() == GSConstFile.LOCK_KBN_ON
                && fileBinDspModel.getFflLockUser() == usrSid) {
            //編集ユーザがログインユーザとなった場合
            return GSConstFile.LOCK_KBN_OFF;
        }
        paramMdl.setFil070FileLockUser(
                StringUtilHtml.transToHTmlPlusAmparsant(fileBinDspModel.getFdrLockUsrName()));
        return fileBinDspModel.getFflLockKbn();
    }

    /**
     * <br>[機  能] ファイル編集可能か判定する。
     * <br>[解  説] ファイルロックを行う。すでにロックされている場合には、ユーザチェックを行う。
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @return lock true:編集可能 false:編集不可能
     * @throws SQLException SQL実行例外
     */
    public boolean isFileLock(int dirSid, int usrSid, Connection con)
    throws SQLException {

        FileFileBinDao fileBinDao = new FileFileBinDao(con);
        FileFileBinModel fileBinModel = fileBinDao.getNewFile(dirSid);

        if (fileBinModel == null) {
            return false;
        }

        if (fileBinModel.getFflLockKbn() == GSConstFile.LOCK_KBN_ON
                && fileBinModel.getFflLockUser() != usrSid) {
            //編集ユーザがログインユーザと異なった場合
                return false;
        }
        return true;
    }
}