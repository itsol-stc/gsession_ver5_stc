package jp.groupsession.v2.man.man450;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man440.AbstractCybCsvImport;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.GSValidateUserCsv;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.UserUtil;

/**
 * <br>[機  能] サイボウズLive メンバー名簿エクスポートファイルのチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybMemberCsvImport extends AbstractCybCsvImport {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CybMemberCsvImport.class);

    /** ログインID重複チェック用MAP */
    private HashMap<String, String> loginIdMap__;

    /** 仮パスワード */
    private String password__ = "";
    /** パスワード次回更新フラグ */
    private int passUpdateFlg__ = 0;

    // -------------------------------
    //  クラス内でのみ使用する変数
    // -------------------------------
    /** 次回パスワード更新区分*/
    private int uidPswdKbn__ = GSConstMain.PWC_UIDPSWDKBN_OFF;
    /** ユーザリスナー */
    private IUserGroupListener[] lis__ = null;
    /** 暗号化されたパスワード */
    private String encryPassword__ = "";

    /** CSV列定義    */
    public enum COLNO {
      /** 姓*/
      SEI,
      /** 名*/
      MEI,
      /** 姓カナ*/
      SEIKN,
      /** 名カナ*/
      MEIKN,
      /** メールアドレス*/
      MAIL
    }

    /** csv列定義実態 モードごとの列の違いが反映された配列*/
    private COLNO[] colArr__;

    /**
     * <br>[機 能] コンストラクタ
     * <br>[解 説]
     * <br>[備 考]
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param grpSid グループSID
     * @param password 仮パスワード
     * @param passUpdateFlg パスワード次回更新フラグ
     */
    public CybMemberCsvImport(ActionErrors error, RequestModel reqMdl,
            Connection con, int grpSid, String password, int passUpdateFlg) {
        super(error, reqMdl, con);

        setLoginIdMap(new HashMap<String, String>());
        setGrpSid(grpSid);
        setPassword(password);
        setPassUpdateFlg(passUpdateFlg);
        EnumSet<COLNO> eset = EnumSet.allOf(COLNO.class);
        colArr__ = eset.toArray(new COLNO[eset.size()]);
    }

    /**
     * <br>[機  能] インポート画面毎に設定するチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __validateImportData() throws Exception {
        //グループSID存在チェック
        if (this.getGrpSid() >= 0 && !GSValidateUser.existGroup(this.getGrpSid(), con__)) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            String eprefix = "group.";
            String group = gsMsg.getMessage("cmn.group"); //グループ
            ActionMessage msg = new ActionMessage("search.data.notfound", group);
            StrutsUtil.addMessage(errors__, msg, eprefix + "search.data.notfound");
        }

        // パスワードルール設定取得
        int coe = GSConstMain.PWC_COEKBN_OFF;
        int digit = GSConstMain.DEFAULT_DIGIT;
        uidPswdKbn__ = GSConstMain.PWC_UIDPSWDKBN_OFF;

        CmnPswdConfDao dao = new CmnPswdConfDao(con__);
        CmnPswdConfModel model = dao.select();

        if (model != null) {
            coe = model.getPwcCoe();
            digit = model.getPwcDigit();
            uidPswdKbn__ = model.getPwcUidPswd();
        }

        //パスワード入力値チェック
        __validatePassword(errors__, coe, digit, uidPswdKbn__, this.getPassword(), reqMdl__);
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @param pconfig プラグイン設定
     * @return 取得件数_
     * @throws  Exception   実行時例外
     */
    public long importCsv(String tmpFileDir, PluginConfig pconfig)
        throws Exception {

        // モード指定
        mode__ = MODE_IMPORT;

        //ユーザリスナー取得
        lis__ = UserUtil.getUserListeners(pconfig);

        //採番コントローラー取得
        cntCon__ = GroupSession.getResourceManager().getCountController(reqMdl__);

        // 更新日付(現在日時)取得
        sysUd__ = new UDate();

        // ログインユーザSID取得
        BaseUserModel buMdl = reqMdl__.getSmodel();
        if (buMdl != null) {
            sessionUser__ = buMdl.getUsrsid();
        }

        // 入力されたパスワードを暗号化
        if (this.getPassword() != null) {
            CommonBiz cmnBiz = new CommonBiz();
            String password = cmnBiz.getLoginInstance().formatPassword(this.getPassword());
            encryPassword__ = GSPassword.getEncryPassword(password); //暗号化されたパスワード
        }


        //テンポラリディレクトリにあるファイル名称を取得
        String csvFile = this.getCsvFilePath(tmpFileDir);

        //ファイル取込
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        if (num > 0) {
            num--; // ヘッダ行分を除外
        }

        //登録・登録予定内容を設定
        return num;
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return userInfList__
     * @throws  Exception   実行時例外
     */
    public String getCsvFilePath(String tmpFileDir)
        throws Exception {

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

        return tmpFileDir + saveFileName;
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        //処理モード
        if (mode__ == MODE_CHECK) {
            // データチェック
            __check(num, lineStr);
        } else if (mode__ == MODE_IMPORT) {
            // データインポート
            __import(num, lineStr);
        }
    }

    /**
     * <br>[機  能] csvファイル一行のチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __check(long num, String lineStr) throws Exception {
        GSValidateUserCsv gsValidateUserCsv = new GSValidateUserCsv(reqMdl__);
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage("cmn.csv.number.items");
        //行目
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});
        //行目の
        String textLine2 = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //メールアドレス
        String textMailAddress = gsMsg.getMessage("cmn.mailaddress");
        //パスワード
        String textPassWord = gsMsg.getMessage("user.117", new String[] {String.valueOf(num)});

        try {

            int j = 0;

            String buff = "";
            String eprefix = "inputFile.";
            int ecnt = errors__.size();

            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            log__.debug("項目数=" + stringTokenizer.length());
            COLNO[] colArr = colArr__;
            if (stringTokenizer.length() != colArr.length
                    && stringTokenizer.length() != colArr.length - 1) {
                ActionMessage msg =
                new ActionMessage(
                        "error.input.format.file",
                        textCaptureFile,
                        textCsvitems + "(" + textLine + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {

                while (stringTokenizer.hasMoreTokens()) {
                    buff = stringTokenizer.nextToken();
                    switch (colArr[j]) {
                        //姓
                        case SEI:
                            buff = _getUserNameFromBuffer(NullDefault.getString(buff, "")); // ユーザ名として取得
                            gsValidateUserCsv.validateCsvUserNameSei(
                                    errors__, buff, num, true);
                            break;
                        //名
                        case MEI:
                            buff = _getUserNameFromBuffer(NullDefault.getString(buff, "")); // ユーザ名として取得
                            gsValidateUserCsv.validateCsvUserNameMei(
                                    errors__, buff, num, true);
                            break;
                        //姓カナ
                        case SEIKN:
                            // ひらがなを全角カタカナへ変換する
                            buff = _getUserNameFromBuffer(NullDefault.getString(buff, "")); // ユーザ名として取得
                            buff = CybMemberCsvImport.hiraganaToKatakana(buff);
                            gsValidateUserCsv.validateCsvUserNameSeiKana(
                                    errors__, buff, num, true);
                            break;
                        //名カナ
                        case MEIKN:
                            // ひらがなを全角カタカナへ変換する
                            buff = _getUserNameFromBuffer(NullDefault.getString(buff, "")); // ユーザ名として取得
                            buff = CybMemberCsvImport.hiraganaToKatakana(buff);
                            gsValidateUserCsv.validateCsvUserNameMeiKana(
                                    errors__, buff, num, true);
                            break;
                        //メールアドレス(メールアドレス1 / ログインID)
                        case MAIL:
                            int uecnt = errors__.size();

                            // メールアドレスチェック
                            if (StringUtil.isNullZeroString(buff)) {
                                // メールアドレス未入力チェック
                                ActionMessage msg = new ActionMessage("error.input.required.text",
                                        textLine + textMailAddress);
                                StrutsUtil.addMessage(errors__, msg, eprefix
                                        + "error.input.required.text");
                            } else {
                                gsValidateUserCsv.validateCsvMail(errors__, buff, 1, num);
                            }

                            // ログインIDチェック(メールアドレスで既に問題が発生した場合はチェックしない)
                            if (uecnt == errors__.size()) {
                                // 重複チェックの為、読み込み済みログインID一覧をチェック
                                if (loginIdMap__.containsKey(buff)) {
                                    String dupIndex = (String) loginIdMap__.get(buff);
                                    //行目の
                                    String dupLine = gsMsg.getMessage("cmn.line2",
                                            new String[] {dupIndex});

                                    ActionMessage msg = new ActionMessage(
                                        "error.select.dup.list2",
                                        textLine2 + textUserId,
                                        dupLine + textUserId);
                                    StrutsUtil.addMessage(
                                        errors__,
                                        msg,
                                        "userid." + num + "error.select.dup.list2");
                                } else {
                                    // ログインIDの入力チェックを行う
                                    gsValidateUserCsv.validateCsvUserId(errors__, buff, num);

                                    if (uecnt == errors__.size()) {
                                        // 登録済みのログインIDとの重複チェック
                                        gsValidateUserCsv.validateCsvUserIdDouble(
                                                 errors__, -1, buff, num, con__);
                                    }

                                    // パスワードとユーザIDが重複しているかチェック
                                    if (uidPswdKbn__ == GSConstMain.PWC_UIDPSWDKBN_ON
                                     && this.getPassword().equals(buff)) {
                                        // ユーザIDと同じパスワードは許可しない
                                        ActionMessage msg = new ActionMessage(
                                                    "error.input.icchi.useridpassword2",
                                                    textLine + textPassWord);
                                        StrutsUtil.addMessage(errors__, msg, eprefix
                                                     + "error.input.icchi.useridpassword2");
                                    }

                                    loginIdMap__.put(buff, String.valueOf(num));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    j++;
                }
            }

            //エラー有り
            if (ecnt < errors__.size()) {
                //エラー存在フラグON
                setErrorFlg(true);
            } else {
                //明細データ以降
                if (num >= 2) {
                    //有効データ件数カウントアップ
                    int cnt = getCount();
                    cnt += 1;
                    setCount(cnt);
                }
            }

        } catch (Exception e) {
            log__.error("CSVファイル読込み時例外");
            throw e;
        }
    }

    /**
     * <br>[機  能] csvファイル一行のインポート処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __import(long num, String lineStr) throws Exception {
        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        CmnUsrmModel umodel = new CmnUsrmModel();
        CmnUsrmInfModel uifmodel = new CmnUsrmInfModel();
        CmnBelongmModel bmodel = new CmnBelongmModel();

        COLNO[] colArr = colArr__;

        // 行データ解析
        while (stringTokenizer.hasMoreTokens()) {

            COLNO col = colArr[j];
            buff = stringTokenizer.nextToken();

            __setUsrInfModel(col, uifmodel, buff);
            j++;
        }

        int usid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_USER,
                                              SaibanModel.SBNSID_SUB_USER,
                                              sessionUser__);

        //ユーザマスタ
        umodel.setUsrJkbn(GSConstUser.USER_JTKBN_ACTIVE);
        umodel.setUsrSid(usid);
        umodel.setUsrAuid(sessionUser__);
        umodel.setUsrAdate(sysUd__);
        umodel.setUsrEuid(sessionUser__);
        umodel.setUsrEdate(sysUd__);
        umodel.setUsrLgid(uifmodel.getUsiMail1()); // メールアドレスをユーザIDとして登録
        umodel.setUsrPswd(encryPassword__);
        umodel.setUsrPswdEdate(sysUd__);
        umodel.setUsrPswdKbn(this.getPassUpdateFlg());

        //ユーザ情報
        uifmodel.setUsrSid(usid);
        uifmodel.setUsiEuid(sessionUser__);
        uifmodel.setUsiEdate(sysUd__);
        uifmodel.setUsiLtlgin(sysUd__);
        uifmodel.setUsiAuid(sessionUser__);
        uifmodel.setUsiAdate(sysUd__);

        // グループメンバー情報
        bmodel.setGrpSid(this.getGrpSid());
        bmodel.setUsrSid(usid);
        bmodel.setBegAuid(sessionUser__);
        bmodel.setBegAdate(sysUd__);
        bmodel.setBegEuid(sessionUser__);
        bmodel.setBegEdate(sysUd__);
        bmodel.setBegDefgrp(CmnBelongmModel.DEFGRP_FLG_DEFAULT); // 強制的にデフォルトグループとして登録

        //登録処理 → あとで考える
        CmnUsrmDao udao = new CmnUsrmDao(con__);
        CmnBelongmDao bdao = new CmnBelongmDao(con__);
        CmnUsrmInfDao uifdao = new CmnUsrmInfDao(con__);

        udao.insert(umodel);
        uifdao.insert(uifmodel);
        bdao.insert(bmodel);

        // 新規登録なので各プラグインリスナーを呼び出し
        for (int i = 0; i < lis__.length; i++) {
            lis__[i].addUser(cntCon__, con__, usid, sessionUser__, reqMdl__);
        }
    }

    /**
     * <br>[機  能] ユーザ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param col 項目クラス
     * @param uifmodel CmnUsrmInfModel
     * @param buff 読込文字列
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード変換時例外
     */
    private void __setUsrInfModel(COLNO col,
            CmnUsrmInfModel uifmodel,
            String buff
            ) throws SQLException, EncryptionException {

        String prmStr;

        switch (col) {
        //姓
        case SEI:
            prmStr = _getUserNameFromBuffer(NullDefault.getString(buff, "")); // ユーザ名として取得
            uifmodel.setUsiSei(prmStr);
            break;
        //名
        case MEI:
            prmStr = _getUserNameFromBuffer(NullDefault.getString(buff, "")); // ユーザ名として取得
            uifmodel.setUsiMei(prmStr);
            break;
        //姓カナ
        case SEIKN:
            prmStr = _getUserNameFromBuffer(NullDefault.getString(buff, ""));
            if (prmStr.length() > 0) {
                // ひらがなを全角カタカナへ変換する
                prmStr = CybMemberCsvImport.hiraganaToKatakana(prmStr);
                uifmodel.setUsiSeiKn(prmStr);
                uifmodel.setUsiSini(StringUtilKana.getInitKanaChar(prmStr));
            } else {
                uifmodel.setUsiSeiKn("");
                uifmodel.setUsiSini("");
            }
            break;
        //名カナ
        case MEIKN:
            prmStr = _getUserNameFromBuffer(NullDefault.getString(buff, ""));
            if (prmStr.length() > 0) {
                // ひらがなを全角カタカナへ変換する
                prmStr = CybMemberCsvImport.hiraganaToKatakana(prmStr);
                uifmodel.setUsiMeiKn(prmStr);
            } else {
                uifmodel.setUsiMeiKn("");
            }
            break;
        //メールアドレス
        case MAIL:
            prmStr = NullDefault.getString(buff, "").trim();
            uifmodel.setUsiMail1(prmStr);
            break;
        default:
            break;
        }
    }

    /**
     * <p>password を取得します。
     * @return password
     */
    public String getPassword() {
        return password__;
    }

    /**
     * <p>password をセットします。
     * @param password password
     */
    public void setPassword(String password) {
        password__ = password;
    }

    /**
     * <p>passUpdateFlg を取得します。
     * @return passUpdateFlg
     */
    public int getPassUpdateFlg() {
        return passUpdateFlg__;
    }

    /**
     * <p>passUpdateFlg をセットします。
     * @param passUpdateFlg passUpdateFlg
     */
    public void setPassUpdateFlg(int passUpdateFlg) {
        passUpdateFlg__ = passUpdateFlg;
    }

    /**
     * <p>loginIdMap を取得します。
     * @return loginIdMap
     */
    public HashMap<String, String> getLoginIdMap() {
        return loginIdMap__;
    }

    /**
     * <p>loginIdMap をセットします。
     * @param loginIdMap loginIdMap
     */
    public void setLoginIdMap(HashMap<String, String> loginIdMap) {
        loginIdMap__ = loginIdMap;
    }

    /**
     * <br>[機  能] 全角ひらがなを全角カタカナに変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param s 変換する文字列(ひらがな)
     * @return  変換した文字列(全角カタカナ)
     * -----------------------------------------------------------
     * ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞ
     * ただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽ
     * まみむめもゃやゅゆょよらりるれろゎわゐゑをん
     *
     * ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾ
     * タダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポ
     * マミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶ
     * -----------------------------------------------------------
     */
    public static String hiraganaToKatakana(String s) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c >= 'ぁ' && c <= 'ん') { // 文字列中のひらがなの文字コードを検索
                sb.setCharAt(i, (char)(c - 'ぁ' + 'ァ')); // 文字コードを全角カタカナに変換
            }
        }
        return sb.toString();
    }

    /**
     * <p>パスワードの入力チェックを行う
     * @param errors ActionErrors
     * @param coe 英数混在区分
     * @param digit パスワード桁数
     * @param uidPswdKbn ユーザIDと同一パスワード設定区分
     * @param password パスワード
     * @param reqMdl RequestModel
     * @return ActionErrors
     * @throws Exception 実行例外
     */
    private void __validatePassword(ActionErrors errors,
                int coe, int digit, int uidPswdKbn,
                String password, RequestModel reqMdl) throws Exception {

        ActionMessage msg = null;
        String eprefix = "password.";
        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage("user.117");

        if (StringUtil.isNullZeroString(password)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", textPassWord);
            StrutsUtil.addMessage(errors__, msg, eprefix + "error.input.required.text");

        } else if (password.length() > GSConstUser.MAX_LENGTH_PASSWORD
                || password.length() < digit) {
            //MIN,MAX桁チェック
            msg = new ActionMessage("error.input.length2.text",
                    textPassWord, digit,
                    GSConstUser.MAX_LENGTH_PASSWORD);
            StrutsUtil.addMessage(errors__, msg, eprefix + "error.input.length2.text");

        } else if (!GSValidateUtil.isPasswordFormat(password)) {
            //パスワード使用文字チェック
            msg = new ActionMessage("error.input.format.newpassword", textPassWord);
            StrutsUtil.addMessage(errors__, msg,
                    eprefix + "error.input.format.newpassword" + textPassWord);

        } else if (!GSValidateUtil.isPasswordCombinationFormat(coe, password)) {
            //パスワード組合せフォーマットチェック
            if (coe == GSConstMain.PWC_COEKBN_ON_EN) {
                msg = new ActionMessage("error.input.format.newpassword2", textPassWord);
                StrutsUtil.addMessage(errors__, msg,
                        eprefix + "error.input.format.newpassword2" + textPassWord);
            } else if (coe == GSConstMain.PWC_COEKBN_ON_ENS) {
                msg = new ActionMessage("error.input.format.newpassword3", textPassWord);
                StrutsUtil.addMessage(errors__, msg,
                        eprefix + "error.input.format.newpassword3" + textPassWord);
            }
        }
    }
}
