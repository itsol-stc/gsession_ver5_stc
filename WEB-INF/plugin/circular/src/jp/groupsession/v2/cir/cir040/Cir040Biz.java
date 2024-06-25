package jp.groupsession.v2.cir.cir040;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.biz.CirFileEditCheckBiz;
import jp.groupsession.v2.cir.biz.CirNotifyBiz;
import jp.groupsession.v2.cir.biz.CirUsedDataBiz;
import jp.groupsession.v2.cir.cir180.Cir180Dao;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirBinDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.AccountDataModel;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirBinModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.WmlMailDataModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 新規作成画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir040Biz.class);
    /** 定数 メモ欄修正期限 1週間 */
    private static final int PERIOD_ONEWEEK = 0;
    /** 定数 メモ欄修正期限 2週間 */
    private static final int PERIOD_TWOWEEK = 2;
    /** 定数 メモ欄修正期限 1カ月 */
    private static final int PERIOD_ONEMONTH = 3;

    /**
     * <br>[機  能] テンポラリディレクトリのファイル削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param dirId テンポラリディレクトリのディレクトリＩＤ
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void doDeleteFile(RequestModel reqMdl, String dirId) throws IOToolsException {

        //テンポラリディレクトリのファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(reqMdl, GSConstCircular.PLUGIN_ID_CIRCULAR,
                dirId);
        log__.debug("テンポラリディレクトリのファイル削除");
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setInitData(
        Cir040ParamModel paramMdl,
        Connection con,
        String tempDir,
        RequestModel reqMdl) throws Exception {

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;

        cacMdl = cacDao.select(paramMdl.getCir040AccountSid());

        if (cacMdl != null) {
            //アカウント
            paramMdl.setCir040AccountSid(cacMdl.getCacSid());
            //アカウント名
            paramMdl.setCirViewAccountName(cacMdl.getCacName());

            if (cacMdl.getCacTheme() !=  GSConstCircular.CAC_THEME_NOSET) {
                CmnThemeDao dao = new CmnThemeDao(con);
                CmnThemeModel model = dao.select(cacMdl.getCacTheme());
            if (model.getCtmPath() != null) {
                //アカウントテーマ
                paramMdl.setCir010AccountTheme(model.getCtmPath());
                }
            }

            paramMdl.setCirViewAccountUko(cacMdl.getUsrUkoFlg());
        }

        //使用可能なアカウントの一覧を取得する
        Cir180Dao dao = new Cir180Dao(con);
        List<AccountDataModel> accountList = dao.getAccountList(reqMdl.getSmodel().getUsrsid());
        paramMdl.setCir010AccountList(accountList);

        CommonBiz commonBiz = new CommonBiz();
        paramMdl.setCir040FileLabelList(commonBiz.getTempFileLabelList(tempDir));

        /** 回覧先をセット ******************************************************/
        if (paramMdl.getCir040userSid() != null
                && paramMdl.getCir040userSid().length > 0) {
            CirCommonBiz cirBiz = new CirCommonBiz();
            paramMdl.setCir040MemberList(cirBiz.getAccountList(con, paramMdl.getCir040userSid()));

        }

        /** 日付をセット ********************************************************/
        int initYear = paramMdl.getCir040memoPeriodYear();
        int initMonth = paramMdl.getCir040memoPeriodMonth();
        int initDay = paramMdl.getCir040memoPeriodDay();
        if (initYear == -1 && initMonth == -1 && initDay == -1) {
            setInitDateData(paramMdl);
        }
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        
        if (paramMdl.getCir040memoPeriodDate() == null) {
            dateBiz.setDateParam(paramMdl, "cir040memoPeriodDate", "cir040memoPeriodYear",
                    "cir040memoPeriodMonth", "cir040memoPeriodDay", null);
        }
    }

    /**
     * <br>[機  能] 初回表示のラジオボタンの値をセットする。
     * <br>[解  説] 初回表示用のラジオの値をDBから取得し、セットをする。
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitRadioData(Cir040ParamModel paramMdl, Connection con, int userSid)
            throws SQLException {
        //管理者設定の有無
        CirInitDao initDao = new CirInitDao(con);
        //管理者設定モデル
        CirInitModel initMdl = new CirInitModel();

        //管理者設定の有無チェック 0:無し
        if (initDao.getCount() != 0) {
            initMdl = initDao.select();
        } else if (initDao.getCount() == 0) {
            initMdl = getDefaultData();
        }

        //CirUserDao userDao = new CirUserDao(con);
        //CirUserModel userModel = userDao.getCirUserInfo(userSid);

        if (initMdl.getCinInitSetKen() == GSConstCircular.CIR_INIEDIT_STYPE_ADM) {
            //メモ欄の編集権限
            paramMdl.setCir040memoKbn(initMdl.getCinMemoKbn());
            paramMdl.setCir040memoPeriod(initMdl.getCinMemoDay());
            //回覧板公開区分
            paramMdl.setCir040show(initMdl.getCinKouKbn());
        } else if (initMdl.getCinInitSetKen() == GSConstCircular.CIR_INIEDIT_STYPE_USER) {

            CirAccountDao cacDao = new CirAccountDao(con);
            CirAccountModel cacMdl = new CirAccountModel();
            cacMdl = cacDao.select(paramMdl.getCir040AccountSid());

            //初期設定区分
            if (cacMdl.getCacInitKbn() == GSConstCircular.CIR_INIT_KBN_NOSET) {
                paramMdl.setCir040memoKbn(initMdl.getCinMemoKbn());
                paramMdl.setCir040memoPeriod(initMdl.getCinMemoDay());
                paramMdl.setCir040show(initMdl.getCinKouKbn());
            } else if (cacMdl.getCacInitKbn() == GSConstCircular.CIR_INIT_KBN_SET) {
                paramMdl.setCir040memoKbn(cacMdl.getCacMemoKbn());
                paramMdl.setCir040memoPeriod(cacMdl.getCacMemoDay());
                paramMdl.setCir040show(cacMdl.getCacKouKbn());
            }
        }

    }

    /**
     * <br>[機  能] 初期値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setInitDateData(Cir040ParamModel paramMdl) throws Exception {

        UDate now = new UDate();

        //期限の初期値
        int kigenKbn = paramMdl.getCir040memoPeriod();
        switch (kigenKbn) {
        case GSConstCircular.CIR_INIT_MEMO_ONEWEEK :
            now.addDay(7);
            break;

        case GSConstCircular.CIR_INIT_MEMO_TWOWEEKS :
            now.addDay(14);
            break;

        case GSConstCircular.CIR_INIT_MEMO_ONEMONTH :
            now.addMonth(1);
            break;

        default:
            break;
        }

        paramMdl.setCir040memoPeriodYear(now.getYear());
        paramMdl.setCir040memoPeriodMonth(now.getMonth());
        paramMdl.setCir040memoPeriodDay(now.getIntDay());
    }

    /**
     * <br>[機  能] メモ欄修正期限ラジオクリック時に、日付に値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setDateData(Cir040ParamModel paramMdl) throws Exception {

        UDate now = new UDate();
        int period = paramMdl.getCir040memoPeriod();

        //ラジオの値によって日付を設定
        if (period == PERIOD_ONEWEEK) {
            //1週間なので7日
            now.addDay(7);
        } else if (period == PERIOD_TWOWEEK) {
            //2週間なので14日
            now.addDay(14);
        } else if (period == PERIOD_ONEMONTH) {
            now.addMonth(1);
        }

        /** 日付の値をフォームにセット *************************************************/
        paramMdl.setCir040memoPeriodYear(now.getYear());
        paramMdl.setCir040memoPeriodMonth(now.getMonth());
        paramMdl.setCir040memoPeriodDay(now.getIntDay());
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        dateBiz.setDateParam(paramMdl, "cir040memoPeriodDate", "cir040memoPeriodYear",
                "cir040memoPeriodMonth", "cir040memoPeriodDay", null);
    }

    /**
     * <br>[機  能]デフォルト値を設定する。
     * <br>[解  説]初期値が設定されてない場合にデフォルト値を設定する。
     * <br>[備  考]
     * @return initMdl 初期値モデル(管理者)
     */
    private CirInitModel getDefaultData() {
        CirInitModel initMdl = new CirInitModel();
        initMdl.setCinInitSetKen(GSConstCircular.CIR_INIEDIT_STYPE_USER);
        initMdl.setCinMemoKbn(GSConstCircular.CIR_INIT_MEMO_CHANGE_NO);
        initMdl.setCinMemoDay(GSConstCircular.CIR_INIT_MEMO_ONEWEEK);
        initMdl.setCinKouKbn(GSConstCircular.CIR_INIT_SAKI_PUBLIC);

        return initMdl;
    }

    /**
     * <br>[機  能] 回覧板 新規作成画面のフォームへ回覧板情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws TempFileException 回覧板 添付ファイル情報の取得に失敗
     * @throws IOException 回覧板 添付ファイル情報の取得に失敗
     * @throws IOToolsException 回覧板 添付ファイル情報の取得に失敗
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public void copyCirData(Cir040ParamModel paramMdl, Connection con,
                            String appRootPath, String tempDir,
                            RequestModel reqMdl)
    throws SQLException, TempFileException, IOException, IOToolsException,
    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //回覧板SID
        int cifSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //回覧板情報を取得する
        CirInfDao infDao = new CirInfDao(con);
        CirInfModel cdMdl = infDao.getCirInfo(cifSid);

        //タイトル
        paramMdl.setCir040title(cdMdl.getCifTitle());
        //発信部署
        paramMdl.setCir040groupSid(Integer.toString(cdMdl.getGrpSid()));
        //内容
        paramMdl.setCir040value(cdMdl.getCifValue());
        //公開／非公開
        paramMdl.setCir040show(cdMdl.getCifShow());
        //メモ欄修正区分
        paramMdl.setCir040memoKbn(cdMdl.getCifMemoFlg());

        UDate memoDate = cdMdl.getCifMemoDate();
        if (memoDate != null) {
            //メモ欄修正期限(日付指定・年)
            paramMdl.setCir040memoPeriodYear(memoDate.getYear());
            //メモ欄修正期限(日付指定・月)
            paramMdl.setCir040memoPeriodMonth(memoDate.getMonth());
            //メモ欄修正期限(日付指定・日)
            paramMdl.setCir040memoPeriodDay(memoDate.getIntDay());
            
            DateTimePickerBiz dateBiz = new DateTimePickerBiz();
            dateBiz.setDateParam(paramMdl, "cir040memoPeriodDate", "cir040memoPeriodYear",
                    "cir040memoPeriodMonth", "cir040memoPeriodDay", null);
        }

        //添付ファイル情報
        CirBinDao cirBinDao = new CirBinDao(con);
        List<CirBinModel> cirBinList = cirBinDao.getBinList(new String[]{Integer.toString(cifSid)});
        if (cirBinList != null && !cirBinList.isEmpty()) {
            CommonBiz cmnBiz = new CommonBiz();
            UDate now = new UDate();
            String dateStr = now.getDateString();

            int fileIdx = 1;
            for (CirBinModel cirBinData : cirBinList) {
                CmnBinfModel binMdl
                    = cmnBiz.getBinInfo(con, cirBinData.getBinSid(), reqMdl.getDomain());
                if (binMdl != null) {
                    //添付ファイルをテンポラリディレクトリにコピーする。
                    cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, fileIdx++);
                }
            }
        }
        //テンポラリディレクトリにあるファイル目録の作成
        CirFileEditCheckBiz fileChkBiz = new CirFileEditCheckBiz();
        fileChkBiz.createMokulokuFile(tempDir);

        //回覧先
        CirViewDao cirViewDao = new CirViewDao(con);
        List<String> viewUserList;
        if (paramMdl.getCirEntryMode() == GSConstCircular.CIR_ENTRYMODE_COPY) {
            viewUserList = cirViewDao.getCirViewUserStr(cifSid);
        } else {
            viewUserList = new ArrayList<String>();
            List<Integer> intSidList = cirViewDao.getCirViewUser(cifSid);
            for (Integer intSid : intSidList) {
                viewUserList.add(String.valueOf(intSid));
            }
        }
        if (viewUserList != null && !viewUserList.isEmpty()) {
            String[] cmn120UserSid = new String[viewUserList.size()];

            CirAccountDao cacDao = new CirAccountDao(con);
            List<CirAccountModel> accountMdlList = null;
            CirAccountModel accountMdl = null;
            accountMdlList = cacDao.getAccountList(
                    (String[]) viewUserList.toArray(new String[viewUserList.size()]));

            for (int idx = 0; idx < accountMdlList.size(); idx++) {
                accountMdl = accountMdlList.get(idx);
                if (accountMdl.getUsrSid() > 0) {
                    cmn120UserSid[idx] = String.valueOf(accountMdl.getUsrSid());
                } else {
                    cmn120UserSid[idx] = GSConstCircular.CIR_ACCOUNT_STR
                    + accountMdl.getCacSid();
                }

            }
            paramMdl.setCir040userSid(cmn120UserSid);

        }

        if (paramMdl.getCirEntryMode() == GSConstCircular.CIR_ENTRYMODE_COPY) {
            //回覧板情報の複写完了後、登録モードを「新規作成」に変更する
            paramMdl.setCirEntryMode(GSConstCircular.CIR_ENTRYMODE_NEW);
        }
    }

    /**
     * <br>[機  能] WEBメール メール情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setWebmailData(
        Cir040ParamModel paramMdl,
        Connection con,
        String appRootPath,
        String tempDir,
        RequestModel reqMdl) throws Exception {

        long mailNum = paramMdl.getCir040webmailId();
        WmlDao wmlDao = new WmlDao(con);
        WmlMailDataModel mailData = wmlDao.getMailData(mailNum, reqMdl.getDomain());
        paramMdl.setCir040title(mailData.getSubject());

        GsMessage gsMsg = new GsMessage(reqMdl);
        String body = gsMsg.getMessage("cmn.subject") + ": "
                            + NullDefault.getString(mailData.getSubject(), "")
                            + "\r\n";
        body += gsMsg.getMessage("cmn.sender") + ": "
                    + NullDefault.getString(mailData.getFromAddress(), "")
                    + "\r\n";

        body += gsMsg.getMessage("cmn.send.date") + ": ";
        UDate sendDate = mailData.getSendDate();
        if (sendDate != null) {
            body += UDateUtil.getSlashYYMD(sendDate)
                    + " "
                    + UDateUtil.getSeparateHMS(sendDate);
        }
        body += "\r\n";

        body += gsMsg.getMessage("cmn.from") + ": "
                    + __createAddressStr(mailData.getToList())
                    + "\r\n";
        body += "CC: " + __createAddressStr(mailData.getCcList()) + "\r\n";
        body += "\r\n" + mailData.getBody();

        paramMdl.setCir040value(body);

        if (mailData.getTempFileList() != null) {
            UDate now = new UDate();
            String dateStr = now.getDateString();
            CommonBiz cmnBiz = new CommonBiz();
            int fileNum = 1;
            for (WmlTempfileModel fileMdl : mailData.getTempFileList()) {
                cmnBiz.saveTempFileForWebmail(dateStr, fileMdl, appRootPath, tempDir, fileNum);
                fileNum++;
            }
        }
    }
    /**
     * <br>[機  能] メールアドレス一覧から本文設定用メールアドレス文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param addressList メールアドレス一覧
     * @return 本文設定用メールアドレス文字列
     */
    private String __createAddressStr(List<String> addressList) {
        String address = "";
        if (addressList != null && !addressList.isEmpty()) {
            address = addressList.get(0);
            for (int adrIdx = 1; adrIdx < addressList.size(); adrIdx++) {
                address += "," + addressList.get(adrIdx);
            }
        }
        return address;
    }
    
    /**
     * <br>[機  能] 回覧板の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param dirId ディレクトリID
     * @return int 回覧板SID
     * @throws Exception 実行時例外
     */
    public int doInsert(
            Cir040ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            int userSid,
            String tempDir,
            String appRootPath,
            String dirId) throws Exception {

        int cirSid = 0;

        boolean commit = false;
        try {
            con.setAutoCommit(false);

            //システム日付
            UDate now = new UDate();

            /** 添付ファイルを登録 **********************************************/
            //テンポラリディレクトリパスにある添付ファイルを全て登録
            CommonBiz biz = new CommonBiz();
            List < String > binList =
                biz.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);


            /** 回覧板情報を登録 **********************************************/
            //回覧板SID採番
            cirSid = (int) cntCon.getSaibanNumber(GSConstCircular.SBNSID_CIRCULAR,
                                                       GSConstCircular.SBNSID_SUB_CIRCULAR,
                                                       userSid);

            log__.debug("shoukbn ===> " + paramMdl.getCir040show());

            /** メモ欄修正期限を設定 *******************************************/
            UDate memoLimit = new UDate();
            int year = paramMdl.getCir040memoPeriodYear();
            int month = paramMdl.getCir040memoPeriodMonth();
            int day = paramMdl.getCir040memoPeriodDay();

            if (year == -1 && month == -1 && day == -1) {
                memoLimit = null;
            } else {
                memoLimit.setDate(year, month, day);
            }

            CirInfModel ciMdl = new CirInfModel();
            ciMdl.setCifSid(cirSid);
            ciMdl.setCifTitle(NullDefault.getString(paramMdl.getCir040title(), ""));
            ciMdl.setGrpSid(NullDefault.getInt(paramMdl.getCir040groupSid(), -1));
            ciMdl.setCifValue(NullDefault.getString(paramMdl.getCir040value(), ""));
            ciMdl.setCifAuid(paramMdl.getCir040AccountSid());
            ciMdl.setCifAdate(now);
            ciMdl.setCifEuid(paramMdl.getCir040AccountSid());
            ciMdl.setCifEdate(now);
            ciMdl.setCifJkbn(GSConstCircular.DSPKBN_DSP_OK);
            ciMdl.setCifEkbn(GSConstCircular.CIR_NO_EDIT);
            ciMdl.setCifShow(paramMdl.getCir040show());
            ciMdl.setCifMemoFlg(paramMdl.getCir040memoKbn());
            ciMdl.setCifMemoDate(memoLimit);
            ciMdl.setCifEditDate(now);

            //回覧板情報を登録する
            CircularDao cDao = new CircularDao(con);
            cDao.insertCir(
               ciMdl, binList, paramMdl.getCir040userSid());

            con.commit();

            if (paramMdl.getCir040userSid() != null
                    && paramMdl.getCir040userSid().length > 0) {
                CirCommonBiz cirBiz = new CirCommonBiz();
                paramMdl.setCir040MemberList(
                        cirBiz.getAccountList(con, paramMdl.getCir040userSid()));
            }
            /** 新回覧先*/
            List<Integer> newAccSidList = new ArrayList<>();
            for (CirAccountModel accMdl : paramMdl.getCir040MemberList()) {
                newAccSidList.add(accMdl.getCacSid());
            }

            CirNotifyBiz notifyBiz = new CirNotifyBiz(con, reqMdl);
            //追加送信者へ受信ショートメール通知
            notifyBiz.doNotifiesJusin(cntCon, newAccSidList, ciMdl, appRootPath);


            commit = true;
        } catch (SQLException e) {
            log__.warn("回覧板登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } catch (TempFileException e) {
            log__.warn("回覧板添付ファイル登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //テンポラリディレクトリのファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(reqMdl, GSConstCircular.PLUGIN_ID_CIRCULAR, dirId);
        log__.debug("テンポラリディレクトリのファイル削除");

        return cirSid;
    }

    /**
     * <br>[機  能] 回覧板の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param userSid ログインユーザSID
     * @param tempDir テンポラリディレクトリパス
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションのルートパス
     * @param tempMdl テンポラリディレクトリ情報
     * @return int 回覧板SID
     * @throws Exception 実行時例外
     */
    public int doEdit(
            Cir040ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            int userSid,
            String tempDir,
            MlCountMtController cntCon,
            String appRootPath,
            GSTemporaryPathModel tempMdl) throws Exception {

        int cirSid = 0;
        boolean commit = false;
        try {

            if (ValidateUtil.isNumber(paramMdl.getCirEditInfSid())) {
                cirSid = Integer.valueOf(paramMdl.getCirEditInfSid());


                //新規追加宛先取得
                CirViewDao cirViewDao = new CirViewDao(con);

                if (paramMdl.getCir040userSid() != null
                        && paramMdl.getCir040userSid().length > 0) {
                    CirCommonBiz cirBiz = new CirCommonBiz();
                    paramMdl.setCir040MemberList(
                            cirBiz.getAccountList(con, paramMdl.getCir040userSid()));
                }

                /** 回覧板情報モデル */
                //回覧板情報を取得する
                CirInfDao infDao = new CirInfDao(con);
                CirInfModel oldMdl = infDao.getCirInfo(cirSid);

                /** 旧回覧先*/
                List<String> accSidStrList = cirViewDao.getCirViewUserStr(cirSid);

                List<Integer> loadAccSidList = new ArrayList<>();
                for (String sidStr : accSidStrList) {
                    loadAccSidList.add(NullDefault.getInt(sidStr, 0));
                }
                /** 新回覧先*/
                List<Integer> newAccSidList = new ArrayList<>();
                for (CirAccountModel accMdl : paramMdl.getCir040MemberList()) {
                    newAccSidList.add(accMdl.getCacSid());
                }
                /** 追加回覧先*/
                @SuppressWarnings("unchecked")
                List<Integer> addAcc = ListUtils.subtract(newAccSidList, loadAccSidList);
                /** 削除回覧先*/
                @SuppressWarnings("unchecked")
                List<Integer> rmAcc = ListUtils.subtract(loadAccSidList, newAccSidList);
                /** 既存回覧先*/
                @SuppressWarnings("unchecked")
                List<Integer> edAcc = ListUtils.subtract(newAccSidList, addAcc);

                /** システム日付*/
                UDate now = new UDate();

                /** 回覧板情報モデル */
                CirInfModel ciMdl = new CirInfModel();
                ciMdl.setCifSid(cirSid);
                ciMdl.setCifTitle(NullDefault.getString(paramMdl.getCir040title(), ""));
                ciMdl.setGrpSid(NullDefault.getInt(paramMdl.getCir040groupSid(), -1));
                ciMdl.setCifValue(NullDefault.getString(paramMdl.getCir040value(), ""));
                ciMdl.setCifAuid(paramMdl.getCir040AccountSid());
                ciMdl.setCifAdate(now);
                ciMdl.setCifEuid(paramMdl.getCir040AccountSid());
                ciMdl.setCifEdate(now);
                ciMdl.setCifJkbn(GSConstCircular.DSPKBN_DSP_OK);
                ciMdl.setCifShow(paramMdl.getCir040show());
                ciMdl.setCifMemoFlg(paramMdl.getCir040memoKbn());
                // メモ欄修正期限を設定
                UDate memoLimit = new UDate();
                int year = paramMdl.getCir040memoPeriodYear();
                int month = paramMdl.getCir040memoPeriodMonth();
                int day = paramMdl.getCir040memoPeriodDay();

                if (year == -1 && month == -1 && day == -1) {
                    memoLimit = null;
                } else {
                    memoLimit.setDate(year, month, day);
                }
                ciMdl.setCifMemoDate(memoLimit);
                con.setAutoCommit(false);

                /** 内容編集判定*/
                boolean edit = __isEdited(oldMdl, ciMdl, tempDir);

                if (edit) {
                    ciMdl.setCifEkbn(GSConstCircular.CIR_EDIT);
                    ciMdl.setCifEditDate(now);
                } else {
                    ciMdl.setCifEkbn(oldMdl.getCifEkbn());
                    ciMdl.setCifEditDate(oldMdl.getCifEditDate());
                }


                //回覧板情報のデータ使用量を登録(変更対象のデータ使用量を減算)
                CirUsedDataBiz usedDataBiz = new CirUsedDataBiz(con);
                usedDataBiz.insertCirDataSize(ciMdl.getCifSid(), false);

                CircularDao cDao = new CircularDao(con);

                /** 添付ファイルを登録 **********************************************/
                CirBinDao bdao = new CirBinDao(con);
                List < CirBinModel > cbList
                           = bdao.getBinList(new String[] {String.valueOf(cirSid)});
                List<Long> cbSidList = new ArrayList<Long>();
                for (CirBinModel cbMdl : cbList) {
                    cbSidList.add(cbMdl.getBinSid());
                }
                //バイナリ情報を論理削除
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
                cbMdl.setBinUpuser(userSid);
                cbMdl.setBinUpdate(now);
                cDao.updateJKbn(cbMdl, cbSidList);

                //回覧板添付情報(CIR_BIN)を物理削除
                bdao.deleteCriBin(new String[] {paramMdl.getCirEditInfSid()});

                //テンポラリディレクトリパスにある添付ファイルを全て登録
                CommonBiz biz = new CommonBiz();
                List < String > binList =
                    biz.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);


                log__.debug("shoukbn ===> " + paramMdl.getCir040show());



                //回覧板情報を登録する
                cDao.updateCir(
                   ciMdl, binList, addAcc, rmAcc);

                //回覧板情報のデータ使用量を登録
                usedDataBiz.insertCirDataSize(ciMdl.getCifSid(), true);

                con.commit();

                CirNotifyBiz notifyBiz = new CirNotifyBiz(con, reqMdl);
                //追加送信者へ受信ショートメール通知
                notifyBiz.doNotifiesJusin(cntCon, addAcc, ciMdl, appRootPath);

                if (edit) {

                    //既存送信者へ編集ショートメール通知
                    notifyBiz.doNotifiesEdit(cntCon, edAcc, ciMdl, appRootPath);

                }
                con.commit();
                commit = true;
            }
            //テンポラリディレクトリのファイルを削除する
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(tempMdl);
            log__.debug("テンポラリディレクトリのファイル削除");
        } catch (SQLException e) {
            log__.warn("回覧板登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } catch (TempFileException e) {
            log__.warn("回覧板添付ファイル登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }


        return cirSid;
    }

    /**
     *
     * <br>[機  能] 回覧板編集判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param oldMdl 旧登録情報
     * @param ciMdl 新登録情報
     * @param tempDir テンポラリディレクトリパス
     * @return true : 変更有 false : 変更なし
     * @throws IOToolsException ファイルアクセス実行時例外
     * @throws IOException ファイルアクセス実行時例外
     */
    private boolean __isEdited(CirInfModel oldMdl, CirInfModel ciMdl,
            String tempDir) throws IOToolsException, IOException {
        //タイトル
        if (!ObjectUtils.equals(oldMdl.getCifTitle(), ciMdl.getCifTitle())) {
            return true;
        }
        //内容
        if (!ObjectUtils.equals(oldMdl.getCifValue(), ciMdl.getCifValue())) {
            return true;
        }
        //メモ欄の修正
        if (oldMdl.getCifMemoFlg() != ciMdl.getCifMemoFlg()) {
            return true;
        } else if (oldMdl.getCifMemoFlg() == GSConstCircular.CIR_INIT_MEMO_CHANGE_YES) {
            //修正期限
            UDate d1 = oldMdl.getCifMemoDate();
            UDate d2 = ciMdl.getCifMemoDate();
            if (d1 == null || d2 == null) {
                return true;
            }
            d1.setZeroHhMmSs();
            d2.setZeroHhMmSs();
            if (!d1.equalsTimeStamp(d2)) {
                return true;
            }
        }
        //回覧先確認状況
        if (oldMdl.getCifShow() != ciMdl.getCifShow()) {
            return true;
        }
        //添付ファイル
        CirFileEditCheckBiz fchkBiz = new CirFileEditCheckBiz();
        if (fchkBiz.isFileEdit(tempDir)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 添付ファイル情報をセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     */
    @SuppressWarnings("unchecked")
    public void setTempFiles(Cir040ParamModel paramMdl, String tempDir)
        throws IOToolsException {

        /** 画面に表示するファイルのリストを作成、セット **********************/
        CommonBiz commonBiz = new CommonBiz();
        List<LabelValueBean> sortList = commonBiz.getTempFileLabelList(tempDir);
        Collections.sort(sortList);
        paramMdl.setCir040FileLabelList(sortList);
    }
    /**
     *
     * <br>[機  能] オペレーションログ内容を出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return オペレーションログ内容
     * @throws SQLException SQL実行時例外
     */
    public String getOperationLogForCommit(
            Cir040ParamModel paramMdl, Connection con, RequestModel reqMdl) throws SQLException {
        StringBuilder sb = new StringBuilder();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //送信者
        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;

        cacMdl = cacDao.select(paramMdl.getCir040AccountSid());

        if (cacMdl != null) {
            sb.append(gsMsg.getMessage("cir.2")).append(":").append(cacMdl.getCacName());
        }
        sb.append(" \r\n");
        //タイトル
        sb.append(gsMsg.getMessage("cmn.title")).append(":").append(paramMdl.getCir040title());
        sb.append(" \r\n");
        //回覧先
        sb.append(gsMsg.getMessage("cir.20")).append(":");
        CirCommonBiz cirBiz = new CirCommonBiz();
        List<CirAccountModel> accList = cirBiz.getAccountList(con, paramMdl.getCir040userSid());
        boolean first = true;
        for (CirAccountModel acc : accList) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(acc.getCacName());
        }
        return sb.toString();
    }
}
