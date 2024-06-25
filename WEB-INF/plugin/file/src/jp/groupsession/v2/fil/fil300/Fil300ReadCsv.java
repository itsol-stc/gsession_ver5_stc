package jp.groupsession.v2.fil.fil300;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理 取引情報CSVファイルの読み取り処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil300ReadCsv extends AbstractCsvRecordReader {

    /** 表示用リスト */
    private List<FileErrlAdddataDspModel> dspList__ = new ArrayList<FileErrlAdddataDspModel>();

    /**
     * <p>dspList を取得します。
     * @return dspList
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#dspList__
     */
    public List<FileErrlAdddataDspModel> getDspList() {
        return dspList__;
    }

    /**
     * <p>dspList をセットします。
     * @param dspList dspList
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#dspList__
     */
    public void setDspList(List<FileErrlAdddataDspModel> dspList) {
        dspList__ = dspList;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil300ReadCsv.class);

    /** コネクション */
    private Connection con__ = null;
    /** コネクション */
    private RequestModel reqMdl__ = null;
    /** メッセージ */
    private MessageResources msgRes__ = null;
    /** 表示ページ */
    private int page__ = 1;
    /** SID未入力件数*/
    private int invalidCount__ = 0;
    /** 仮登録情報 */
    private FileErrlAdddataModel feaMdl__ = null;
    /** 選択ディレクトリ */
    private int directorySid__ = 0;
    /** キャビネットSID */
    private int cabinetSid__ = 0;
    /** 追加済み確認用Set */
    private Set<Integer> addedSet__ = null;
    /** 登録を許可する仮登録ファイルID */
    private List<String> beforeInsertFileList__ = null;

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     * @return num 取り込み件数
     */
    protected long _importCsv(String tmpFileDir) throws Exception {

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
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        return num;
    }

    /**
     * <br>[機  能] CSVファイルの内容から登録用モデルリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //ページで指定された範囲のみ表示
        if (num > 1) {
            try {
                int j = 0;
                FileErrlAdddataDspModel dspMdl = new FileErrlAdddataDspModel();
                GsMessage gsMsg = new GsMessage(reqMdl__);
                boolean addFlg = true;
                FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
                FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
                String moneyStr = null;

                boolean moneyInputFlg = false;
                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();
                    if (dspMdl.getErrorFlg() == GSConstFile.READ_KBN_ERROR) {
                        break;
                    }

                    //No.の読み込み
                    if (j == 1) {
                        if (StringUtil.isNullZeroString(buff)) {
                            addFlg = false;
                            buff = "";
                        }

                        if (!ValidateUtil.isNumber(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("search.notfound.tdfkcode",
                                    gsMsg.getMessage("fil.fil300.10")));
                            dspMdl.setErrorText(sb.toString());

                            dspMdl.setDspId(buff);
                            continue;
                        }

                        int feaSid = Integer.parseInt(buff);
                        dspMdl.setFeaSid(feaSid);
                        if (!addedSet__.add(feaSid)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.select.dup.list",
                                    gsMsg.getMessage("cmn.id")));
                            dspMdl.setErrorText(sb.toString());
                        }

                        //登録を許可する仮登録ファイルのIDかを判定
                        if (!beforeInsertFileList__.isEmpty()) {
                            if (!beforeInsertFileList__.contains(String.valueOf(feaSid))) {
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(gsMsg.getMessage("fil.176"));
                                sb.append(msgRes__.getMessage("search.notfound.tdfkcode",
                                        gsMsg.getMessage("fil.fil300.10")));
                                dspMdl.setErrorText(sb.toString());

                                dspMdl.setDspId(buff);
                                continue;
                            }
                        }

                        feaMdl__ =
                                feaDao.getErrlAddDataList(
                                        List.of(feaSid),
                                        reqMdl__.getSmodel())
                                .stream()
                                .findAny()
                                .orElse(null);

                        if (feaMdl__ == null) {
                            //存在エラー
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("search.notfound.tdfkcode",
                                    gsMsg.getMessage("fil.fil300.10")));
                            dspMdl.setErrorText(sb.toString());
                        } else {
                            FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
                            List<Integer> dirList = fdrDao.getLowerDirSid(directorySid__);
                            FileDirectoryModel fdrMdl =
                                    fdrDao.getNewDirectory(feaMdl__.getFdrParentSid());

                            if (fdrMdl == null
                                        || fdrMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                                dspMdl.setParentExistFlg(false);
                            } else if (!dirList.contains(feaMdl__.getFdrParentSid())) {
                                //指定されたキャビネット, フォルダに紐づいていない
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(msgRes__.getMessage("error.import.errlid"));
                                dspMdl.setErrorText(sb.toString());
                            } else {
                                FileCabinetDao fcbDao = new FileCabinetDao(con__);
                                FileCabinetModel fcbMdl = fcbDao.select(fdrMdl.getFcbSid());

                                FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl__, con__);
                                int code = cmnBiz.checkPowEditDir(feaMdl__.getFdrParentSid(),
                                        GSConstFile.DIRECTORY_FOLDER, true);

                                if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NOT_USE
                                            && code != 0) {
                                    //保存先フォルダの編集権限がない
                                    dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(gsMsg.getMessage("fil.176"));
                                    sb.append(msgRes__.getMessage("error.errl.access"));
                                    dspMdl.setErrorText(sb.toString());
                                }
                            }
                        }

                        dspMdl.setDspId(buff);
                    }

                    //ファイル名
                    if (j == 2) {
                        if (StringUtil.isNullZeroString(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.required.text",
                                    gsMsg.getMessage("cmn.file.name")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (ValidateUtil.isSpaceOrKaigyou(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.spase.cl.only",
                                    gsMsg.getMessage("cmn.file.name")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (ValidateUtil.isSpaceStart(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.spase.start",
                                    gsMsg.getMessage("cmn.file.name")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (ValidateUtil.isTab(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.tab.text",
                                    gsMsg.getMessage("cmn.file.name")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (buff.length() > 250) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.length.text",
                                    gsMsg.getMessage("cmn.file.name"), 250));
                            dspMdl.setErrorText(sb.toString());
                        } else if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                            sb.append(msgRes__.getMessage("error.input.njapan.text",
                                    gsMsg.getMessage("cmn.file.name"), nstr));
                            dspMdl.setErrorText(sb.toString());
                        } else {
                            int parentSid = feaMdl__.getFdrParentSid();
                            FileDirectoryModel fdrMdl = new FileDirectoryModel();
                            fdrMdl.setFdrParentSid(parentSid);
                            FileCabinetDao fcbDao = new FileCabinetDao(con__);
                            FileCabinetModel fcbMdl = fcbDao.select(feaMdl__.getFcbSid());
                            List<FileDirectoryModel> parentsList =
                                    filBiz.getRootToCurrentDirctoryList(fcbMdl, fdrMdl);

                            StringBuilder sb = new StringBuilder();
                            if (!dspMdl.isParentExistFlg() && fcbMdl != null) {
                                sb.append(fcbMdl.getFcbName());
                                sb.append("/");
                                sb.append(gsMsg.getMessage("fil.179"));
                                sb.append("/");
                            }

                            StringBuilder sbPath = new StringBuilder();
                            for (int idx = 0; idx < parentsList.size() - 1; idx++) {
                                if (parentsList.get(idx).getFdrJtkbn()
                                        != GSConstFile.JTKBN_DELETE) {
                                    sbPath.append(parentsList.get(idx).getFdrName());
                                    sbPath.append("/");
                                } else {
                                    sbPath = new StringBuilder();
                                    break;
                                }
                            }

                            String ext = feaDao.select(
                                    Integer.parseInt(dspMdl.getDspId())).getFflExt();
                            //表示用
                            dspMdl.setFileName(sb.toString() + sbPath.toString() + buff + ext);
                            //登録用
                            dspMdl.setFdrName(buff + ext);
                        }
                    }

                    //取引年月日
                    if (j == 3) {
                        if (StringUtil.isNullZeroString(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.required.text",
                                    gsMsg.getMessage("fil.fil080.8")));
                            dspMdl.setErrorText(sb.toString());
                        } else {
                            if (!ValidateUtil.isSlashDateFormat(buff)) {
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(gsMsg.getMessage("fil.176"));
                                sb.append(msgRes__.getMessage("error.input.comp.text",
                                        gsMsg.getMessage("fil.fil080.8"),
                                        gsMsg.getMessage("cmn.format.date2")));
                                dspMdl.setErrorText(sb.toString());
                                continue;
                            }
                            String[] dateParam = buff.split("/");
                            String tradeDate = dateParam[0];
                            if (dateParam[1].length() == 1) {
                                tradeDate += ("/0" + dateParam[1]);
                            } else {
                                tradeDate += ("/" + dateParam[1]);
                            }
                            if (dateParam[2].length() == 1) {
                                tradeDate += ("/0" + dateParam[2]);
                            } else {
                                tradeDate += ("/" + dateParam[2]);
                            }
                            buff = tradeDate;
                            if (!ValidateUtil.isExistDateYyyyMMdd(buff.replaceAll("/", ""))) {
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(gsMsg.getMessage("fil.176"));
                                sb.append(msgRes__.getMessage("search.data.notfound",
                                        gsMsg.getMessage("fil.fil080.8")));
                                dspMdl.setErrorText(sb.toString());
                            } else {
                                UDate date = UDate.getInstanceStr(buff);
                                dspMdl.setTradeDate(
                                        gsMsg.getMessage("cmn.date4",
                                                new String[] {
                                                        date.getStrYear(),
                                                        date.getStrMonth(),
                                                        date.getStrDay()
                                                }));

                                dspMdl.setTradeDateInput(buff);
                            }
                        }
                    }

                    //取引先
                    if (j == 4) {
                        if (StringUtil.isNullZeroString(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.required.text",
                                    gsMsg.getMessage("fil.fil030.18")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (buff.length() > GSConstFile.MAX_LENGTH_TRADE_TARGET) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.length.text",
                                    gsMsg.getMessage("fil.fil030.18"),
                                    GSConstFile.MAX_LENGTH_TRADE_TARGET));
                            dspMdl.setErrorText(sb.toString());
                        } else if (ValidateUtil.isSpaceOrKaigyou(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.spase.cl.only",
                                    gsMsg.getMessage("fil.fil030.18")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (ValidateUtil.isSpaceStart(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.spase.start",
                                    gsMsg.getMessage("fil.fil030.18")));
                            dspMdl.setErrorText(sb.toString());
                        } else if (ValidateUtil.isTab(buff)) {
                            dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                            StringBuilder sb = new StringBuilder();
                            sb.append(gsMsg.getMessage("fil.176"));
                            sb.append(msgRes__.getMessage("error.input.tab.text",
                                    gsMsg.getMessage("fil.fil030.18")));
                            dspMdl.setErrorText(sb.toString());
                        } else {
                            dspMdl.setTradeTarget(buff);
                        }
                    }

                    //取引金額
                    if (j == 5) {
                        if (StringUtil.isNullZeroString(buff)) {
                            continue;
                        } else {
                            String money = buff;
                            String money_top = "";
                            String money_bottom = "";

                            //マイナス対応
                            if (money.substring(0, 1).equals("-")) {
                                money = money.substring(1);
                            }

                            if (money.indexOf(".") != -1) {
                                money_top = money.substring(
                                        0, money.indexOf(".")).replaceAll(",", "");
                                money_bottom = money.substring(money.indexOf(".") + 1);
                            } else {
                                money_top = money.replaceAll(",", "");
                            }
                            String targetName = gsMsg.getMessage("fil.fil080.5");

                            String errorText = null;
                            if (!ValidateUtil.isNumber(money_top)) {
                                //半角数字チェック
                                errorText = msgRes__.getMessage(
                                        "error.input.number.text", targetName);
                            } else if (money_top.length()
                                    > GSConstFile.MAX_LENGTH_TRADE_MONEY_TOP) {
                                //MAX桁チェック
                                errorText = msgRes__.getMessage(
                                        "error.input.length.text",
                                        (targetName + " " + gsMsg.getMessage("fil.fil300.7")),
                                        GSConstFile.MAX_LENGTH_TRADE_MONEY_TOP);
                            } else if (money_bottom != "") {
                                if (!ValidateUtil.isNumber(money_bottom)) {
                                    //半角数字チェック
                                    errorText = msgRes__.getMessage(
                                            "error.input.number.text", targetName);
                                } else if (money_bottom.length()
                                        > GSConstFile.MAX_LENGTH_TRADE_MONEY_BOTTOM) {
                                    //MAX桁チェック
                                    errorText = msgRes__.getMessage(
                                            "error.input.length.text",
                                            (targetName + " " + gsMsg.getMessage("fil.fil300.8")),
                                            GSConstFile.MAX_LENGTH_TRADE_MONEY_BOTTOM);
                                }
                            }
                            if (errorText != null) {
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(gsMsg.getMessage("fil.176"));
                                sb.append(errorText);
                                dspMdl.setErrorText(sb.toString());
                                continue;
                            }
                        }
                        moneyInputFlg = true;
                        moneyStr = StringUtil.toCommaUnderZeroTrim(buff.replaceAll(",", ""));
                        dspMdl.setTradeMoeyInput(buff);
                    }

                    //取引金額(外貨)
                    if (j == 6) {
                        if (moneyInputFlg) {
                            FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con__);
                            if (StringUtil.isNullZeroString(buff)) {
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(gsMsg.getMessage("fil.176"));
                                sb.append(msgRes__.getMessage("error.input.required.text",
                                        gsMsg.getMessage("fil.fil310.2")));
                                dspMdl.setErrorText(sb.toString());
                            } else if (!fmmDao.existGaikaName(buff)) {
                                dspMdl.setErrorFlg(GSConstFile.READ_KBN_ERROR);
                                StringBuilder sb = new StringBuilder();
                                sb.append(gsMsg.getMessage("fil.176"));
                                sb.append(msgRes__.getMessage("search.notfound.tdfkcode",
                                        gsMsg.getMessage("fil.fil310.2")));
                                dspMdl.setErrorText(sb.toString());
                            } else {
                                moneyStr = moneyStr + " " + buff;
                                dspMdl.setTradeMoey(moneyStr);
                                dspMdl.setTradeGaika(buff);
                            }
                            continue;
                        }
                        dspMdl.setTradeMoey("-");
                    }
                }

                //ファイル仮登録時のデフォルトグループを設定
                if (feaMdl__ != null) {
                    dspMdl.setFeaDefgrpName(feaMdl__.getFeaDefgrpName());
                }

                feaMdl__ = null;
                if (addFlg) {
                    dspList__.add(dspMdl);
                } else {
                    invalidCount__++;
                }
            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }
        }
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param addedSet 追加済み確認用セット
     * @param msgRes MessageResources
     * @param directorySid 選択した親ディレクトリ
     * @param beforeInsertFileList 登録を許可する仮登録ファイルID
     */
    public Fil300ReadCsv(Connection con, RequestModel reqMdl,
            MessageResources msgRes, int directorySid, Set<Integer> addedSet,
            List<String> beforeInsertFileList) {

        setCon(con);
        setReqMdl(reqMdl);
        setMsgRes(msgRes);
        directorySid__ = directorySid;
        addedSet__ = addedSet;
        beforeInsertFileList__ = beforeInsertFileList;
        if (beforeInsertFileList__ == null) {
            beforeInsertFileList__ = new ArrayList<String>(0);
        }
    }

    /**
     * <p>con を取得します。
     * @return con
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knImportCsv#con__
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knImportCsv#con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }

    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#reqMdl__
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <p>msgRes を取得します。
     * @return msgRes
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#msgRes__
     */
    public MessageResources getMsgRes() {
        return msgRes__;
    }

    /**
     * <p>msgRes をセットします。
     * @param msgRes msgRes
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#msgRes__
     */
    public void setMsgRes(MessageResources msgRes) {
        msgRes__ = msgRes;
    }

    /**
     * <p>page を取得します。
     * @return page
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#page__
     */
    public int getPage() {
        return page__;
    }

    /**
     * <p>page をセットします。
     * @param page page
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#page__
     */
    public void setPage(int page) {
        page__ = page;
    }

    /**
     * <p>invalidCount を取得します。
     * @return invalidCount
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#invalidCount__
     */
    public int getInvalidCount() {
        return invalidCount__;
    }

    /**
     * <p>invalidCount をセットします。
     * @param invalidCount invalidCount
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#invalidCount__
     */
    public void setInvalidCount(int invalidCount) {
        invalidCount__ = invalidCount;
    }

    /**
     * <p>cabinetSid を取得します。
     * @return cabinetSid
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#cabinetSid__
     */
    public int getCabinetSid() {
        return cabinetSid__;
    }

    /**
     * <p>cabinetSid をセットします。
     * @param cabinetSid cabinetSid
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#cabinetSid__
     */
    public void setCabinetSid(int cabinetSid) {
        cabinetSid__ = cabinetSid;
    }

    /**
     * <p>addedSet を取得します。
     * @return addedSet
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#addedSet__
     */
    public Set<Integer> getAddedSet() {
        return addedSet__;
    }

    /**
     * <p>addedSet をセットします。
     * @param addedSet addedSet
     * @see jp.groupsession.v2.fil.fil300.Fil300ReadCsv#addedSet__
     */
    public void setAddedSet(Set<Integer> addedSet) {
        addedSet__ = addedSet;
    }
}
