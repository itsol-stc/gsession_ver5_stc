package jp.groupsession.v2.api.ntp.nippou.edit;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.api.ntp.nippou.edit.model.ApiNippouEditModel;
import jp.groupsession.v2.api.ntp.nippou.edit.model.ApiNippouEditTemplateModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.biz.NtpUsedDataBiz;
import jp.groupsession.v2.ntp.dao.NtpBinDao;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.dao.NtpPriTargetDao;
import jp.groupsession.v2.ntp.model.NtpBinModel;
import jp.groupsession.v2.ntp.model.NtpCommentModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.ntp040.Ntp040Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] WEBAPI 日報編集ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouEditBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiNippouEditBiz.class);
    /** DBコネクション */
    private  Connection con__ = null;
    /** リクエスト */
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** ファイルサイズ 1MB */
    private static final int FILE_SIZE_1MB = 1048576;

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public ApiNippouEditBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl リクエストモデル
     * @param cntCon MlCountMtController
     */
    public ApiNippouEditBiz(Connection con, RequestModel reqMdl,
                           MlCountMtController cntCon) {
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }

    /**
     * <br>[機  能] 日報を新規登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param param 日報データパラメータ
     * @param userSid 登録者SID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param tempDir テンポラリディレクリ
     * @param reqMdl リクエストモデル
     * @throws Exception SQL実行時例外
     * @return 日報Sid
     */
    public int insertNippouDate(
            ApiNippouEditModel param,
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            String tempDir,
            RequestModel reqMdl) throws Exception {

        NtpDataModel ntpMdl = null;
        NtpCommonBiz cmnBiz = new NtpCommonBiz(con__, reqMdl__);

        //登録モデルを作成
        ntpMdl = new NtpDataModel();
        UDate frDate = new UDate();
        UDate toDate = new UDate();
        UDate now = new UDate();

        int frYear = param.getNtpYear();
        int frMonth = param.getNtpMonth();
        int frDay = param.getNtpDay();


        int frHour = GSConstNippou.DAY_START_HOUR;
        int frMin = GSConstNippou.DAY_START_MINUTES;
        int toHour = GSConstNippou.DAY_END_HOUR;
        int toMin = GSConstNippou.DAY_END_MINUTES;

        frHour = param.getFrHour();
        frMin = param.getFrMin();
        toHour = param.getToHour();
        toMin = param.getToMin();



        frDate.setDate(frYear, frMonth, frDay);
        frDate.setZeroHhMmSs();
        ntpMdl.setNipDate(frDate.cloneUDate());

//        boolean frExit = false;
        if (frHour != -1 && frMin != -1) {
            frDate.setHour(frHour);
            frDate.setMinute(frMin);
            frDate.setSecond(GSConstNippou.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
//            frExit = true;
        }

        toDate.setDate(frYear, frMonth, frDay);
        if (toHour != -1 && toMin != -1) {
            toDate.setHour(toHour);
            toDate.setMinute(toMin);
            toDate.setSecond(GSConstNippou.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
//            frExit = true;
        }

        //時間
        ntpMdl.setNipFrTime(frDate);
        ntpMdl.setNipToTime(toDate);

        //案件
        ntpMdl.setNanSid(param.getAnkenSid());

        //会社SID
        ntpMdl.setAcoSid(param.getCompanySid());

        //会社拠点SID
        if (param.getCompanyBaseSid() != -1) {
            ntpMdl.setAbaSid(param.getCompanyBaseSid());
        }

        //活動分類
        ntpMdl.setMkbSid(param.getKtbunruiSid());

        //活動方法
        ntpMdl.setMkhSid(param.getKthouhouSid());

        //見込み度
        ntpMdl.setNipMikomi(param.getMikomido());

        ntpMdl.setNipTitleClo(param.getBgcolor());
        ntpMdl.setNipTitle(param.getTitle());
        ntpMdl.setNipDetail(param.getValueStr());
        ntpMdl.setNipSyokan("");
        ntpMdl.setNipPublic(GSConstNippou.DSP_PUBLIC);

        ntpMdl.setNipAuid(userSid);
        ntpMdl.setNipAdate(now);
        ntpMdl.setNipEuid(userSid);
        ntpMdl.setNipEdate(now);
        //編集区分
        ntpMdl.setNipEdit(GSConstNippou.EDIT_CONF_NONE);
//        //拡張登録SID

        int ntpSid = -1;

        //添付ファイルを登録
        CommonBiz biz = new CommonBiz();
        List<String> binList =
            biz.insertBinInfo(con__, tempDir, appRootPath, cntCon__, userSid, now);
        //SID採番
        ntpSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_NIPPOU, userSid);
        ntpMdl.setNipSid(ntpSid);
        ntpMdl.setUsrSid(param.getSelectUsrSid());
//        ntpMdl.setScdUsrKbn(Integer.parseInt(form.getNtp010SelectUsrKbn()));


        NtpDataDao ntpDao = new NtpDataDao(con__);

        //登録
        ntpDao.insert(ntpMdl);

        NtpBinDao sbinDao = new NtpBinDao(con__);
        //日報添付情報を登録
        sbinDao.insertNtpBin(ntpMdl, binList);

        //日報情報のデータ使用量を登録
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpDataSize(ntpSid, true);

        //URL取得
        String url = __createNippouUrlDefo(GSConstNippou.CMD_EDIT,
                                         String.valueOf(ntpSid),
                                         String.valueOf(userSid),
                                         ntpMdl);

        //ショートメール通知
        cmnBiz.sendSmail(
                con__, cntCon__, ntpMdl, appRootPath, plconf, smailPluginUseFlg, reqMdl, url);

        return ntpSid;
    }

    /**
     * <br>[機  能] 日報を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param param 日報データパラメータ
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションRoot
     * @param iniEdi 編集権限フラグ
     * @param tempDir テンポラリディレクリ
     * @return 日報SID
     * @throws Exception SQL実行時例外
     */
    public int updateNippouDate(ApiNippouEditModel param,
            int userSid,
            String appRootPath,
            int iniEdi,
            String tempDir) throws Exception {

        //管理者設定を取得
        CommonBiz cmnBiz = new CommonBiz();

        int ntpSid = param.getNipSid();

        //日報情報のデータ使用量を登録(変更前のデータ使用量を減算)
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpDataSize(ntpSid, false);

        NtpDataModel ntpMdl = new NtpDataModel();
        UDate now = new UDate();
        UDate frDate = new UDate();
        frDate.setDate(param.getNtpYear(), param.getNtpMonth(), param.getNtpDay()
                );
        frDate.setZeroHhMmSs();
        ntpMdl.setNipDate(frDate.cloneUDate());

        int frHour = GSConstNippou.DAY_START_HOUR;
        int frMin = GSConstNippou.DAY_START_MINUTES;
        int toHour = GSConstNippou.DAY_END_HOUR;
        int toMin = GSConstNippou.DAY_END_MINUTES;
            frHour = param.getFrHour();
            frMin = param.getFrMin();
            toHour = param.getToHour();
            toMin = param.getToMin();

        if (frHour != -1 && frMin != -1) {
            frDate.setHour(frHour);
            frDate.setMinute(frMin);
            frDate.setSecond(GSConstNippou.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
        }

        UDate toDate = new UDate();
        toDate.setDate(param.getNtpYear(),
                       param.getNtpMonth(),
                       param.getNtpDay());

        if (toHour != -1 && toMin != -1) {
            toDate.setHour(toHour);
            toDate.setMinute(toMin);
            toDate.setSecond(GSConstNippou.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);
        }

        ntpMdl.setNipSid(ntpSid);

        //案件
        ntpMdl.setNanSid(param.getAnkenSid());

        //会社SID
        ntpMdl.setAcoSid(param.getCompanySid());

        //会社拠点SID
        if (param.getCompanyBaseSid() != -1) {
            ntpMdl.setAbaSid(param.getCompanyBaseSid());
        }

        //活動分類
        ntpMdl.setMkbSid(param.getKtbunruiSid());

        //活動方法
        ntpMdl.setMkhSid(param.getKthouhouSid());

        //見込み度
        ntpMdl.setNipMikomi(param.getMikomido());

        ntpMdl.setNipFrTime(frDate);
        ntpMdl.setNipToTime(toDate);
        ntpMdl.setNipTitleClo(param.getBgcolor());
        ntpMdl.setNipTitle(param.getTitle());
        ntpMdl.setNipDetail(param.getValueStr());
        ntpMdl.setNipSyokan("");
        ntpMdl.setNipPublic(iniEdi);

        ntpMdl.setNipAuid(userSid);
        ntpMdl.setNipAdate(now);
        ntpMdl.setNipEuid(userSid);
        ntpMdl.setNipEdate(now);

        //編集区分
        ntpMdl.setNipEdit(iniEdi);

        NtpDataDao ntpDao = new NtpDataDao(con__);
        ntpMdl.setUsrSid(param.getSelectUsrSid());

        //登録
        ntpDao.update(ntpMdl);

        //バイナリ情報を登録
        List<String> binList =
            cmnBiz.insertBinInfo(con__, tempDir, appRootPath, cntCon__, userSid, now);

        NtpBinDao sbinDao = new NtpBinDao(con__);
        //日報添付情報を登録
        sbinDao.deleteNtpBin(Integer.valueOf(ntpSid));
        sbinDao.insertNtpBin(ntpMdl, binList);

        //日報情報のデータ使用量を登録
        usedDataBiz.insertNtpDataSize(ntpSid, true);

        return ntpSid;
    }

    /**
     * <br>[機  能] 日報情報を削除(物理削除)します
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param con コネクション
     * @return 削除レコード件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteNippou(int nipSid, Connection con) throws SQLException {

        //日報情報のデータ使用量を登録(変更前のデータ使用量を減算)
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpDataSize(nipSid, false);

        int cnt = 0;
        NtpDataDao scdDao = new NtpDataDao(con);
        cnt = scdDao.delete(nipSid);


        return cnt;
    }
    /**
     * <br>[機  能] 日報登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd 処理モード
     * @param ntpSid 日報SID
     * @param usrSid ユーザーSID
     * @param ntpMdl 日報情報
     * @return 日報登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createNippouUrlDefo(String cmd,
                                          String ntpSid, String usrSid,
                                          NtpDataModel ntpMdl)
    throws UnsupportedEncodingException {
        //スレッドのURLを作成

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl += "/" + GSConstNippou.PLUGIN_ID_NIPPOU;
            paramUrl += "/ntp040.do";
            paramUrl += "?ntp010SelectDate=" + UDateUtil.getYYMD(ntpMdl.getNipDate());
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&ntp010NipSid=" + ntpMdl.getNipSid();
            paramUrl += "&ntp010SelectUsrSid=" + ntpMdl.getUsrSid();
            paramUrl += "&ntp010SelectUsrKbn=" + "0";
            paramUrl += "&ntp010DspDate=" + UDateUtil.getYYMD(ntpMdl.getNipDate());
            paramUrl += "&dspMod=" + "1";
            paramUrl += "&ntp010DspGpSid=" + "0";

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }
    /**
     * 添付フォルダIDを採番
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付フォルダID
     */
    public int createTempId(String tempDir) {
        int tempId = 1;
        String dir = tempDir + "row" + tempId + File.separator;
        File tempFile = new File(dir);
        while (tempFile.exists()) {
            tempId++;
            dir = tempDir + "row" + tempId + File.separator;
            tempFile = new File(dir);
        }
        return tempId;

    }
    /**
     * 添付ファイル編集初期化
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param tempId 添付フォルダID
     * @param nipSid 日報SID
     * @param appRootPath アプリケーションのルートパス
     * @param domain ドメイン
     * @param delBinSids 削除するファイルのbinSid
     * @param tempDir テンポラリディレクトリ
     * @return 添付フォルダID
     * @throws Exception 例外
     */
    public int initEditTempFile(
             int tempId,
             int nipSid,
             String appRootPath,
             String[] delBinSids,
             String domain,
             String tempDir) throws Exception {
        boolean createDir = false;

        if (tempId <= 0) {
            tempId = createTempId(tempDir);
            createDir = true;
        }
        String tempDir__ = tempDir + "row" + tempId + File.separator;
        ArrayList<String> delBinArray = new ArrayList<String>(delBinSids.length);
        for (String string__ : delBinSids) {
            delBinArray.add(string__);
        }

        //既存の日報編集の場合は添付ディレクトリにファイルを展開しておく
        if (nipSid > 0 && createDir) {
            //添付ファイル情報
            NtpBinDao binDao = new NtpBinDao(con__);
            List<NtpBinModel> binList = binDao.getBinList(nipSid);
            List<NtpBinModel> newBinList = new ArrayList<NtpBinModel>();
            for (NtpBinModel ntpBinModel__ : binList) {
                if (!delBinArray.contains(Long.toString(ntpBinModel__.getBinSid()))) {
                    newBinList.add(ntpBinModel__);
                }
            }

            //添付ファイルがあるなるならばテンポラリにコピー
            if (!newBinList.isEmpty()) {
                Ntp040Biz biz = new Ntp040Biz(con__, reqMdl__);
                biz.tempFileCopy(newBinList, appRootPath, tempDir__, con__, domain);

            }

        }
        return tempId;

    }
    /**
     * 添付ファイルを添付フォルダに保存
     * <br>[機  能] 添付ファイルを追加し、保存した添付フォルダIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param tempId 添付フォルダID(1以下の場合、新たに採番される)
     * @param formFile 添付ファイル
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 例外
     */
    public void insertNippouTempFile(int tempId,
            FormFile formFile,
            String tempDir) throws Exception {
        //テンポラリディレクトリパスを取得
        String dir = tempDir + "row" + tempId + File.separator;
        //テンポラリディレクトリにあるファイル名称を取得

        //現在日付の文字列(YYYYMMDD)を取得
        String dateStr = (new UDate()).getDateString();

        //ファイルの連番を取得する
        int fileNum = 1;
            //ファイル数 = 無制限の場合はTEMPディレクトリ内のファイル数から
            //連番を取得する
            fileNum = Cmn110Biz.getFileNumber(dir, dateStr);
            fileNum++;

        //添付ファイル名
        String fileName = (new File(formFile.getFileName())).getName();
        long fileSize = formFile.getFileSize();
        //添付ファイル(本体)のパスを取得
        File saveFilePath = Cmn110Biz.getSaveFilePath(dir, dateStr, fileNum);

        //添付ファイルアップロード
        TempFileUtil.upload(formFile, dir, saveFilePath.getName());

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(dir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(fileName);
        fileMdl.setSaveFileName(saveFilePath.getName());
        fileMdl.setUpdateKbn(0);

        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);


        log__.debug(">>>サイズ :" + fileSize);

    }
    /**エラーコード 日報がない*/
    public static final int ECODE_NIPPOU_NONE = 101;
    /**エラーコード 日報閲覧権限がない*/
    public static final int ECODE_DISABLE_VIEW = 102;
    /**エラーコード 日報編集権限がない*/
    public static final int ECODE_DISABLE_EDIT = 103;
    /**
    *
    * <br>[機  能] 日報の編集権限チェック
    * <br>[解  説]
    * <br>[備  考]
     * @param nipSid 日報SID
     * @throws SQLException SQL実行時例外
     * @return エラーコード
    * @throws SQLException SQL実行時例外
    */
   public int validateNippouAccsess(int nipSid) throws SQLException {
       return validateNippouAccsess(nipSid, false);
   }
    /**
     *
     * <br>[機  能] 日報の編集権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param onlyAccess 編集権限を確認しないかどうか
     * @throws SQLException SQL実行時例外
     * @return エラーコード
     */
    public int validateNippouAccsess(int nipSid, boolean onlyAccess) throws SQLException {
        BaseUserModel usModel = reqMdl__.getSmodel();
        //日報取得
        NtpDataDao dao = new NtpDataDao(con__);
        NtpDataModel data = dao.select(nipSid);
        if (data == null) {
            return ECODE_NIPPOU_NONE;
        }
        int sessionUsrSid = usModel.getUsrsid();
        int selUsrSid = data.getUsrSid();
        //日報閲覧権限チェック
        NtpCommonBiz ncbiz = new NtpCommonBiz(con__, reqMdl__);
        if (!ncbiz.isCanInspection(sessionUsrSid, selUsrSid, con__)) {
            return ECODE_DISABLE_VIEW;
        }
        if (onlyAccess) {
            return 0;
        }
        //編集権限チェック
        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        if (selUsrSid != sessionUsrSid
                && !commonBiz.isPluginAdmin(con__, usModel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            return ECODE_DISABLE_EDIT;
        }

        return 0;

    }
    /**
     * <br>[機  能] 添付ファイル入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fileName ファイル名
     * @param fileSize ファイルサイズ
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateTempFile(String fileName, int fileSize) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (fileName.length() > GSConst.MAX_LENGTH_FILE) {
            //ファイル名
            String textFileName = gsMsg.getMessage("cmn.file.name");

            //ファイル名桁数チェック
            msg = new ActionMessage(
                    "error.input.length.text", textFileName, GSConst.MAX_LENGTH_FILE);
            errors.add("error.input.length.text", msg);
            return errors;
        }

        //ファイル名 使用文字チェック
        if (!GSValidateUtil.isGsJapaneaseString(fileName)) {
            //ファイル名
            String textFileName = gsMsg.getMessage("cmn.file.name");

            //利用不可能な文字を入力した場合
            String nstr =
                GSValidateUtil.getNotGsJapaneaseString(
                        fileName);
            msg =
                new ActionMessage("error.input.njapan.text",
                        textFileName,
                        nstr);
            errors.add("error.file.name.char", msg);
        }

        int maxSize = 0;
        CmnFileConfDao cfcDao = new CmnFileConfDao(con__);
        //添付ファイル最大容量取得

        CmnFileConfModel cfcMdl = cfcDao.select();
        maxSize = cfcMdl.getFicMaxSize() * FILE_SIZE_1MB;
        if (fileSize > maxSize) {
            //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
            msg = new ActionMessage("error.input.capacity.over", cfcMdl.getFicMaxSize() + "MB");
            errors.add("cmn110file.error.input.capacity.over", msg);
            return errors;
        }

        return errors;

    }
    /**
     * <br>[機  能] コメントを登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpSid 日報SID
     * @param commentStr コメント
     * @param userSid 登録者SID
     * @param appPath アプリケーションパス
     * @param pluginConfig プラグインコンフィグ
     * @param reqMdl リクエストモデル
     * @throws Exception SQL実行時例外
     * @return コメントSID
     */
    public int insertComment(
                            int ntpSid,
                            String commentStr,
                            int userSid,
                            String appPath,
                            PluginConfig pluginConfig,
                            RequestModel reqMdl) throws Exception {

        NtpCommentModel npcMdl = new NtpCommentModel();
        NtpCommonBiz cmnBiz = new  NtpCommonBiz(con__, reqMdl__);
        UDate now = new UDate();

        //SID採番
        int npcSid = (int) cntCon__.getSaibanNumber(
                GSConstNippou.SBNSID_NIPPOU_COMMENT,
                GSConstNippou.SBNSID_SUB_NIPPOU_COMMENT, userSid);

        npcMdl.setNpcSid(npcSid);
        npcMdl.setNipSid(ntpSid);
        npcMdl.setUsrSid(userSid);
        npcMdl.setNpcComment(commentStr);
        npcMdl.setNpcViewKbn(0);
        npcMdl.setNpcEdate(now);
        npcMdl.setNpcEuid(userSid);
        npcMdl.setNpcAdate(now);
        npcMdl.setNpcAuid(userSid);

        NtpCommentDao npcDao = new NtpCommentDao(con__);

        //登録
        npcDao.insert(npcMdl);

        //日報コメント情報のデータ使用量を登録
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpCommentSize(npcSid, true);

        CommonBiz commonBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        if (commonBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pluginConfig)) {

            //日報データを取得
            NtpDataDao ntpDao = new NtpDataDao(con__);
            NtpDataModel ntpMdl = null;

            ntpMdl = ntpDao.select(Integer.valueOf(ntpSid));

            if (ntpMdl != null) {
                String url = __createNippouUrlDefo(
                        "edit", String.valueOf(ntpSid), String.valueOf(userSid), ntpMdl);
                cmnBiz.sendPlgSmail(
                        con__, cntCon__, ntpMdl, npcMdl, appPath, pluginConfig, url, reqMdl);
            }
        }

        return npcSid;
    }
    /**
     * <br>[機  能]テンプレートデータ取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param usrSid usrSid
     * @param ntpDate 日付
     * @return 日報テンプレート
     * @throws Exception 例外
     */
    public ApiNippouEditTemplateModel userTemplate(
            Connection con, int usrSid, UDate ntpDate) throws Exception {
        ApiNippouEditTemplateModel ret = new ApiNippouEditTemplateModel();

        //管理者設定を取得
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);

        //ユーザテンプレートデータ取得
        NtpTemplateModel tmpMdl = new NtpTemplateModel();
        tmpMdl = biz.getUsrTemplate(con, usrSid);

        List<NtpPriTargetModel> priTrgList = null;
        priTrgList = new ArrayList<NtpPriTargetModel>();
        ret.setNtgList(priTrgList);

        if (tmpMdl != null) {

            //項目設定
            ret.setTemplate(tmpMdl);
            //ユーザ適用目標取得
            List<NtpTargetModel> trgList = null;
            trgList = biz.getUsrTmpTarget(
                    con, tmpMdl.getNttSid(), usrSid);

            //表示ユーザの表示月の目標取得
            if (trgList != null && !trgList.isEmpty()) {

                NtpPriTargetModel priTrgMdl = null;
                NtpPriTargetDao priTrgDao = new NtpPriTargetDao(con);


                for (NtpTargetModel trgMdl : trgList) {

                    if (trgMdl != null) {

                        //ユーザデータ取得
                        priTrgMdl = priTrgDao.select(
                                                    trgMdl.getNtgSid(),
                                                    usrSid,
                                                    ntpDate.getYear(),
                                                    ntpDate.getMonth());

                        if (priTrgMdl == null) {
                            //データがない場合はデフォルト値を設定
                            priTrgMdl = new NtpPriTargetModel();

                            //目標SID
                            priTrgMdl.setNtgSid(trgMdl.getNtgSid());
                            //usrSid
                            priTrgMdl.setUsrSid(usrSid);
                            //デフォルト値
                            priTrgMdl.setNpgTarget(trgMdl.getNtgDef());
                            //実績
                            priTrgMdl.setNpgRecord(Long.valueOf(0));
                        }

                        //名前
                        priTrgMdl.setNpgTargetName(trgMdl.getNtgName());
                        //単位
                        priTrgMdl.setNpgTargetUnit(trgMdl.getNtgUnit());
                        //年
                        priTrgMdl.setNpgYear(ntpDate.getYear());
                        //月
                        priTrgMdl.setNpgMonth(ntpDate.getMonth());


                        priTrgList.add(priTrgMdl);
                    }
                }

            }
        }
        return ret;
    }
}
