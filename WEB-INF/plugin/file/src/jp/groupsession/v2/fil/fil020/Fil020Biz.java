package jp.groupsession.v2.fil.fil020;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetBinDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileShortcutConfDao;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.fil020.dao.Fil020Dao;
import jp.groupsession.v2.fil.fil020.model.FileAccessUserModel;
import jp.groupsession.v2.fil.fil020.model.FileHistoryModel;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileCallConfModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;
import jp.groupsession.v2.fil.model.FileShortcutConfModel;
import jp.groupsession.v2.fil.model.FileUconfModel;


/**
 * キャビネット詳細画面で使用するビジネスロジッククラス
 * @author JTS
 */
public class Fil020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil020Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil020Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Fil020ParamModel
     * @param buMdl セッションユーザモデル
     * @param appRootPath アプリケーションルートパス
     * @param con コネクション
     * @return true:取得成功 false:取得失敗
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス例外
     * @throws IOException ファイルアクセス例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public boolean setInitData(Fil020ParamModel paramMdl, BaseUserModel buMdl,
            String appRootPath, Connection con)
    throws SQLException, IOException, IOToolsException, TempFileException {

        boolean success = false;

        //個人設定を取得
        FileUconfDao udao = new FileUconfDao(con);
        FileUconfModel uConfMdl = udao.select(buMdl.getUsrsid());
        int confline = uConfMdl.getFucRirekiCnt();

        log__.debug("キャビネット詳細表示");

       //表示項目設定
        int cabSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        FileCabinetDao cabDao = new FileCabinetDao(con);
        FileCabinetModel cabModel = cabDao.select(cabSid);

        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl__, con);
        FileAconfModel aconfMdl = cmnBiz.getFileAconfModel();

        if (cabModel != null) {
            // 個人キャビネットの場合はユーザ名を設定
            if (cabModel.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                // ユーザ名取得
                String userName = cmnBiz.getUserName(cabModel.getFcbOwnerSid());

                // キャビネット名を更新
                if (userName != null && !userName.isEmpty()) {
                    cabModel.setFcbName(userName);
                }

                cabModel.setFileAconf(aconfMdl);
                paramMdl.setCabinetKbn(GSConstFile.CABINET_KBN_PRIVATE);
            } else if (cabModel.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
                paramMdl.setCabinetKbn(GSConstFile.CABINET_KBN_ERRL);
            }

            paramMdl.setFil020CabinetName(cabModel.getFcbName());
            paramMdl.setFil020DspCapaKbn(String.valueOf(cabModel.getFcbCapaKbn()));
            paramMdl.setFil020DspCapaSize(String.valueOf(cabModel.getFcbCapaSize()));
            paramMdl.setFil020DspCapaWarn(String.valueOf(cabModel.getFcbCapaWarn()));
            paramMdl.setFil020VerKbn(String.valueOf(cabModel.getFcbVerKbn()));
            paramMdl.setFil020VerAllKbn(String.valueOf(cabModel.getFcbVerallKbn()));
            paramMdl.setFil020DspBiko(
                    StringUtilHtml.transToHTmlPlusAmparsant(cabModel.getFcbBiko()));
            paramMdl.setFil020binSid(cabModel.getFcbMark());
            paramMdl.setDetailCabinet(String.valueOf(cabModel.getFcbPersonalFlg()));
            paramMdl.setFil020ErrlKbn(cabModel.getFcbErrl());
            paramMdl.setFil020ErrlAutoKbn(cabModel.getFcbSortFolder());
            paramMdl.setFil020ErrlAutoFolder1(cabModel.getFcbSortFolder1());
            paramMdl.setFil020ErrlAutoFolder2(cabModel.getFcbSortFolder2());
            paramMdl.setFil020ErrlAutoFolder3(cabModel.getFcbSortFolder3());

            //添付ファイルのラベルを設定する。
            paramMdl.setFil020FileLabelList(getCabinetTempFiles(cabModel.getFcbSid(), con));

            if (isShortCutSetting(cabModel.getFcbSid(), buMdl.getUsrsid(), con)) {
                paramMdl.setFil020ShortCutKbn(String.valueOf(GSConstFile.SHORTCUT_ON));
            } else {
                paramMdl.setFil020ShortCutKbn(String.valueOf(GSConstFile.SHORTCUT_OFF));
            }
            if (isCallSetting(cabModel.getFcbSid(), buMdl.getUsrsid(), con)) {
                paramMdl.setFil020CallKbn(String.valueOf(GSConstFile.CALL_ON));
            } else {
                paramMdl.setFil020CallKbn(String.valueOf(GSConstFile.CALL_OFF));
            }

            //表示モード
            if (NullDefault.getString(
                    paramMdl.getFil020DspMode(), GSConstFile.DSP_MODE_HIST).equals(
                            GSConstFile.DSP_MODE_HIST)) {
                //履歴取得
                __setUpdateReki(paramMdl, confline, con);
            } else {
                //アクセス制御取得
                __setAccessList(paramMdl, confline, con);
            }
            //編集権限有無を取得
            if (cmnBiz.isEditCabinetUser(cabModel.getFcbSid())) {
                paramMdl.setFil020WriteFlg(String.valueOf(GSConstFile.EDIT_AUTH_OK));
            } else {
                paramMdl.setFil020WriteFlg(String.valueOf(GSConstFile.EDIT_AUTH_NG));
            }

            success = true;
        }

        //管理者設定バージョン管理区分を設定する。
        paramMdl.setAdmVerKbn(aconfMdl.getFacVerKbn());

        return success;
    }
    /**
     * キャビネットSIDを指定しROOTディレクトリのショートカット設定を取得する
     * 未設定の場合はnullを返します
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return FileShortcutConfModel
     * @throws SQLException SQL実行時例外
     */
    public FileShortcutConfModel getShortCutSetting(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        FileShortcutConfModel ret = null;
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        if (dirMdl != null) {
            FileShortcutConfDao dao = new FileShortcutConfDao(con);
            ret = dao.select(dirMdl.getFdrSid(), usrSid);
        }
        return ret;
    }
    /**
     * キャビネットSIDを指定しROOTディレクトリのショートカット設定を取得する
     * 未設定の場合はnullを返します
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return FileShortcutConfModel
     * @throws SQLException SQL実行時例外
     */
    public FileCallConfModel getCallSetting(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        FileCallConfModel ret = null;
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        if (dirMdl != null) {
            FileCallConfDao dao = new FileCallConfDao(con);
            ret = dao.select(dirMdl.getFdrSid(), usrSid);
        }
        return ret;
    }
    /**
     * キャビネットSIDを指定しROOTディレクトリのショートカット設定がされているか判定する
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return FileShortcutConfModel
     * @throws SQLException SQL実行時例外
     */
    public boolean isShortCutSetting(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        boolean ret = false;
        FileShortcutConfModel mdl = getShortCutSetting(fcbSid, usrSid, con);
        if (mdl != null) {
            ret = true;
        }
        return ret;
    }
    /**
     * キャビネットSIDを指定しROOTディレクトリの更新通知設定がされているか判定する
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return FileShortcutConfModel
     * @throws SQLException SQL実行時例外
     */
    public boolean isCallSetting(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        boolean ret = false;
        FileCallConfModel mdl = getCallSetting(fcbSid, usrSid, con);
        if (mdl != null) {
            ret = true;
        }
        return ret;
    }

    /**
     * ショートカットを登録する
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertShortCut(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        FileShortcutConfDao dao = new FileShortcutConfDao(con);
        FileShortcutConfModel mdl = dao.select(dirMdl.getFdrSid(), usrSid);
        if (mdl == null) {
            mdl = new FileShortcutConfModel();
            mdl.setFdrSid(dirMdl.getFdrSid());
            mdl.setUsrSid(usrSid);
            mdl.setFscAdate(new UDate());
            dao.insert(mdl);
        }
    }
    /**
     * ショートカットを削除する
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteShortCut(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        int ret = 0;
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        FileShortcutConfDao caDao = new FileShortcutConfDao(con);
        ret = caDao.delete(dirMdl.getFdrSid(), usrSid);
        return ret;

    }

    /**
     * <br>[機  能] 更新通知情報の設定・削除を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param callLevelKbn レベル区分
     * @throws SQLException SQL実行例外
     */
    public void updateCall(int fcbSid, int usrSid, Connection con, int callLevelKbn)
    throws SQLException {


        if (callLevelKbn > GSConstFile.CALL_LEVEL_OFF) {
            //子フォルダを一括更新
            updateCallChild(fcbSid, usrSid, con, GSConstFile.CALL_ON);
        } else {
            //カレントディレクトリのみ更新
            insertCallConf(fcbSid, usrSid, con);
        }

    }
    /**
     * 更新通知設定を登録する
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertCallConf(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        FileCallConfDao caDao = new FileCallConfDao(con);
        FileCallConfModel mdl = caDao.select(dirMdl.getFdrSid(), usrSid);
        if (mdl == null) {
            mdl = new FileCallConfModel();
            mdl.setFdrSid(dirMdl.getFdrSid());
            mdl.setUsrSid(usrSid);
            caDao.insert(mdl);
        }
    }

    /**
     * <br>[機  能] 更新通知情報の設定・削除を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param callKbn 通知区分
     * @throws SQLException SQL実行例外
     */
    public void updateCallChild(int fcbSid, int usrSid, Connection con, int callKbn)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FilTreeBiz treeBiz = new FilTreeBiz(con);
        FileCallConfDao callConfDao = new FileCallConfDao(con);
        FileCallDataDao callDataDao = new FileCallDataDao(con);
        FileCallConfModel callConfModel = null;

        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        if (dirMdl == null) {
            return;
        }

        //子階層のデータ取得
        FilChildTreeModel childMdl = treeBiz.getChildOfTarget(dirMdl);
        ArrayList<FileDirectoryModel> listOfDir = childMdl.getListOfDir();
        listOfDir.add(dirMdl);
        //子階層の更新通知設定を更新
        if (listOfDir != null && listOfDir.size() > 0) {

            if (callKbn == GSConstFile.CALL_ON) {

                callConfDao.delete(listOfDir, usrSid);
                for (FileDirectoryModel model : listOfDir) {
                    //更新通知設定を登録する。
                    callConfModel = new FileCallConfModel();
                    callConfModel.setFdrSid(model.getFdrSid());
                    callConfModel.setUsrSid(usrSid);
                    callConfDao.insert(callConfModel);
                }

            } else if (callKbn == GSConstFile.CALL_OFF) {
                callConfDao.delete(listOfDir, usrSid);
                callDataDao.delete(listOfDir, usrSid);
            }
        }
    }

    /**
     * 更新通知設定を削除する
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteCallConfOne(int fcbSid, int usrSid, Connection con)
    throws SQLException {
        //
        int ret = 0;
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = dirDao.getRootDirectory(fcbSid);
        FileCallConfDao caDao = new FileCallConfDao(con);
        ret = caDao.delete(dirMdl.getFdrSid(), usrSid);
        return ret;

    }

    /**
     * <br>[機  能] 更新通知情報の設定・削除を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param callLevelKbn 通知区分
     * @throws SQLException SQL実行例外
     */
    public void deleteCallConf(int fcbSid, int usrSid, Connection con, int callLevelKbn)
    throws SQLException {

        if (callLevelKbn > GSConstFile.CALL_LEVEL_OFF) {
            //子フォルダを一括更新
            updateCallChild(fcbSid, usrSid, con, GSConstFile.CALL_OFF);
        } else {
            //カレントディレクトリのみ更新
            deleteCallConfOne(fcbSid, usrSid, con);
        }

    }
    /**
     * キャビネットの添付ファイル一覧を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param con コネクション
     * @return List キャビネットの添付ファイル一覧
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<CmnBinfModel> getCabinetTempFiles(int fcbSid, Connection con)
    throws SQLException, TempFileException {

        FileCabinetBinDao cabinetBinDao = new FileCabinetBinDao(con);
        CmnBinfDao cmnDao = new CmnBinfDao(con);
        CommonBiz cmnBiz = new CommonBiz();
        //ファイル管理の添付ファイル情報を取得する
        String[] binSids = cabinetBinDao.getBinList(fcbSid);
        if (binSids == null || binSids.length < 1) {
            return null;
        }
        //添付ファイル情報を取得する
        List<CmnBinfModel> cmBinList = cmnDao.select(binSids);
        List<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();

        for (CmnBinfModel model : cmBinList) {
            if (model.getBinJkbn() == GSConst.JTKBN_DELETE) {
                continue;
            }
            long size = model.getBinFileSize();
            String strSize = cmnBiz.getByteSizeString(size);
            model.setBinFileSizeDsp(strSize);
            ret.add(model);
        }
        return ret;
    }

    /**
     * <br>[機  能] 更新履歴一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil020ParamModel
     * @param limit 個人設定明細行数
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setUpdateReki(Fil020ParamModel paramMdl, int limit, Connection con)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        int orderKey = NullDefault.getInt(paramMdl.getFil020OrderKey(), 0);
        int sortKey = NullDefault.getInt(paramMdl.getFil020SortKey(), 0);

        //ルートディレクトリSIDを取得する。
        int fcbid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), 0);
        FileDirectoryModel rootDirMdl = dirDao.getRootDirectory(fcbid);
        if (rootDirMdl == null) {
            return;
        }

        //キャビネットSID
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con);
        int dirSid = rootDirMdl.getFdrSid();
        int fcbSid = filBiz.getCabinetSid(dirSid);

        //特権ユーザ判定
        boolean superUser = filBiz.isEditCabinetUser(fcbSid);
        //ユーザSID
        int usrSid = superUser ? -1 : reqMdl__.getSmodel().getUsrsid();

        Fil020Dao dao = new Fil020Dao(con);
        int maxCount = dao.getRekiCount(dirSid, usrSid);

        //現在ページ、スタート行
        int nowPage = paramMdl.getFil020Slt_page1();
        int offset = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setFil020Slt_page1(nowPage);
        paramMdl.setFil020Slt_page2(nowPage);
        paramMdl.setFil020PageLabel(PageUtil.createPageOptions(maxCount, limit));
        //更新履歴一覧を取得する。
        ArrayList<FileHistoryModel> rekiList = dao.getRekiListAll(
                dirSid, usrSid, orderKey, sortKey, offset, limit);

        int cabSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        //ファイル編集権限を設定
        paramMdl.setFil020FileWriteFlg(String.valueOf(GSConstFile.EDIT_AUTH_NG));
        if (filBiz.isEditCabinetUser(cabSid)) {
            paramMdl.setFil020FileWriteFlg(String.valueOf(GSConstFile.EDIT_AUTH_OK));
        }

        paramMdl.setHistoryList(rekiList);

        //復旧ボタン表示判定
        if (!superUser) {
            paramMdl.setFil020RepairDspFlg(GSConstFile.DSP_KBN_OFF);
            for (FileHistoryModel rekiMdl : rekiList) {
                if (rekiMdl.isRepairBtnDspFlg()) {
                    paramMdl.setFil020RepairDspFlg(GSConstFile.DSP_KBN_ON);
                    break;
                }
            }
        }
    }
    /**
     * <br>[機  能] アクセス制御一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil020ParamModel
     * @param limit 個人設定明細行数
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setAccessList(Fil020ParamModel paramMdl, int limit, Connection con)
    throws SQLException {

        int orderKey = NullDefault.getInt(paramMdl.getFil020OrderKey(), 1);
        int sortKey = NullDefault.getInt(paramMdl.getFil020SortKey(), 1);
        int fcbSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), 0);

        FileCabinetDao fcbDao = new FileCabinetDao(con);
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);

        //個人キャビネットの場合、いずれかのサブディレクトリに対してアクセス権限があるユーザも表示
        int personalFlg = fcbMdl.getFcbPersonalFlg();
        ArrayList<Integer> dirAccessList = null;
        if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
            //__getAuthUsrListToSubDir();
            Fil020Dao filDao = new Fil020Dao(con);
            dirAccessList = filDao.getDirectoryAccessList(fcbMdl);
        }

        Fil020Dao dao = new Fil020Dao(con);
        int maxCount = dao.getAccessListCount(fcbMdl, dirAccessList);

//      現在ページ、スタート行
        int nowPage = paramMdl.getFil020Slt_page1();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setFil020Slt_page1(nowPage);
        paramMdl.setFil020Slt_page2(nowPage);
        paramMdl.setFil020PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //アクセス制御一覧を取得する。
        ArrayList<FileAccessUserModel> accessList = dao.getAccessList(
                fcbMdl, sortKey, orderKey, offset, limit, dirAccessList);

        paramMdl.setAccessList(accessList);
    }

    /**
     * <br>[機  能] ファイルの復旧を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl Fil020ParamModel
     * @param buMdl ユーザモデル
     * @param cntCon 採番コントロール
     * @param appRootPath APP ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール使用可能フラグ
     * @throws Exception 実行例外
     */
    public void updateRepair(
            RequestModel reqMdl,
            Fil020ParamModel paramMdl,
            BaseUserModel buMdl,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg)
    throws Exception {

        int dirSid = NullDefault.getInt(paramMdl.getFil020SltDirSid(), -1);
        int version = NullDefault.getInt(paramMdl.getFil020SltDirVer(), -1);
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
        }

        //復旧
        FileDirectoryModel bean = new FileDirectoryModel();
        bean.setFdrSid(dirSid);
        bean.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        bean.setFdrEuid(sessionUsrSid);
        bean.setFdrEdate(now);
        dirDao.updateJtkbn(bean);

        //ファイル情報
        FileFileBinModel fileBinModel = fileBinDao.select(dirSid, version);

        if (fileBinModel != null) {

            Long newBinSid = filBiz.copyFile(
                    appRootPath, fileBinModel.getBinSid(),
                    cntCon);

            fileBinModel.setBinSid(newBinSid);
            fileBinModel.setFdrVersion(nextVersion);
            fileBinModel.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
            fileBinDao.insert(fileBinModel);
        }


        //更新履歴情報
        FileFileRekiModel fileRekiModel = fileRekiDao.select(dirSid, version);
        if (fileRekiModel != null) {
            fileRekiModel.setFdrVersion(nextVersion);
            fileRekiModel.setFfrKbn(GSConstFile.REKI_KBN_REPAIR);
            fileRekiModel.setFfrEuid(sessionUsrSid);
            fileRekiModel.setFfrEdate(now);
            fileRekiDao.insert(fileRekiModel);
        }

        //バージョン管理外のファイルを削除する。
        __deleteOldVersion(dirSid, nextVersion - 1, sessionUsrSid, con__);

        //ディレクトリアクセス設定を更新
        dirDao.updateAccessSid(dirSid);

        //更新通知設定
        filBiz.updateCall(
                dirSid, cntCon, appRootPath, pconfig, smailPluginUseFlg, sessionUsrSid);

    }

    /**
     * <br>[機  能] バージョン管理外のファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param newVersion 最新バージョン
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __deleteOldVersion(int dirSid, int newVersion, int sessionUsrSid, Connection con)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

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
        __deleteOldFile(dirSid, delVersion, sessionUsrSid, con);

    }

    /**
     * <br>[機  能] バージョン管理外のファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param delVersion 削除基準バージョン
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __deleteOldFile(int dirSid, int delVersion, int sessionUsrSid, Connection con)
    throws SQLException {

        FileFileBinDao fileDao = new FileFileBinDao(con);
        CmnBinfDao cbDao = new CmnBinfDao(con);
        CmnBinfModel cmnBinModel = new CmnBinfModel();
        UDate now = new UDate();

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
}