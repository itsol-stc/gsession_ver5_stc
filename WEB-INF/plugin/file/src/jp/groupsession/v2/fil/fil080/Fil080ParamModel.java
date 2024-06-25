package jp.groupsession.v2.fil.fil080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil070.Fil070ParamModel;
import jp.groupsession.v2.fil.fil080.ui.Fil080AuthSelector;
import jp.groupsession.v2.fil.model.FileParentAccessDspModel;

/**
 * <br>[機  能] ファイル登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil080ParamModel extends Fil070ParamModel {

    /** プラグインID */
    private String fil080PluginId__ = null;
    /** フォルダパス */
    private String fil080DirPath__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] fil080TempFiles__ = null;
    /** ファイルコンボ */
    private List<LabelValueBean> fil080FileLabelList__ = null;
    /** 処理モード */
    private int fil080Mode__ = GSConstFile.MODE_ADD;
    /** バージョン管理区分  */
    private String fil080VerKbn__ = null;
    /** バージョンコンボ */
    private List<LabelValueBean> fil080VerKbnLabelList__ = null;
    /** バージョン統一 */
    private String fil080VerallKbn__ = null;

    /** 更新者ID */
    private String fil080EditId__ = null;
    /** グループ一覧 */
    private List<LabelValueBean> fil080groupList__ = null;

    /** 備考 */
    private String fil080Biko__ = null;
    /** 更新コメント */
    private String fil080UpCmt__ = null;
    /** ロック　ユーザ名 */
    private String fil080LockUsrName__ = null;
    /** 複数登録区分 */
    private String fil080PluralKbn__ = "1";
    /** 複数登録区分保持用 */
    private String fil080SvPluralKbn__ = "1";

    /** WEBメール 連携判定 */
    private int fil080webmail__ = 0;
    /** WEBメール 対象メール */
    private long fil080webmailId__ = 0;

    /** 親アクセス権限全表示*/
    private String fil080ParentAccessAll__ = "0";
    /** アクセス制御　有無*/
    private String fil080AccessKbn__ = "0";

    /** アクセス候補 追加・変更・削除グループ */
    private String fil080AcEditSltGroup__ = null;
    /** セーブ フルアクセスユーザ */
    private String[] fil080SvAcFull__ = null;
    /** アクセス候補　閲覧グループ */
    private String fil080AcReadSltGroup__ = null;
    /** セーブ 閲覧アクセスユーザ */
    private String[] fil080SvAcRead__ = null;

    /** アクセス権限 共通キャビネット用UI */
    private Fil080AuthSelector fil080PublicAcUserUI__ = null;
    /** アクセス権限 個人キャビネット用UI */
    private Fil080AuthSelector fil080PrivateAcUserUI__ = null;

    /** 親ディレクトリ　追加・変更・削除ユーザリスト */
    private ArrayList<FileParentAccessDspModel> fil080ParentEditList__ = null;
    /** 親ディレクトリ　閲覧ユーザリスト */
    private ArrayList<FileParentAccessDspModel> fil080ParentReadList__ = null;

    /** 親ディレクトリ　アクセス権限全件表示フラグ */
    private int fil080ParentAccessAllDspFlg__ = 0;
    /** 親ディレクトリ　ゼロユーザフラグ */
    private String fil080ParentZeroUser__ = "0";

    /** 初期表示フラグ*/
    private int fil080InitFlg__ = 0;
    /** 取引年月日*/
    private String fil080TradeDate__ = "";
    /** 取引先*/
    private String fil080TradeTarget__ = "";
    /** 取引金額区分 金額無し */
    private int fil080TradeMoneyKbn__ = GSConstFile.MONEY_KBN_OFF;
    /** 取引金額*/
    private String fil080TradeMoney__ = "";
    /** 取引金額 外貨*/
    private int fil080TradeMoneyType__ = 0;
    /** 取引金額 外貨リスト */
    private ArrayList<LabelValueBean> fil080TradeMoneyMasterList__ = null;

    /** 保存先自動振り分け */
    private int fil080FcbSortFolder__ = 0;
    /** 保存先自動振り分けフォルダ名 */
    private String fil080FcbSortFolderName__ = null;

    /** 登録済みファイル情報 */
    private List<CmnBinfModel> binList__ = null;

    /** キャビネットコンボ キャビネット種類 選択値 */
    private int fil080SelectCabinetKbn__ = 0;

    /** 編集対象ファイル名 */
    private String fil080EditFileName__ = null;
    /** ファイル削除時 操作コメント */
    private String fil080OpeComment__ = null; 

    /**
     * <br>[機  能] 移動先フォルダの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return true:エラーなし false:エラー有り
     * @throws NumberFormatException 実行例外
     * @throws SQLException 実行例外
     */
    public boolean checkSltMoveDirAccess(Connection con, RequestModel reqMdl)
            throws NumberFormatException, SQLException {

        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        int fcbSid = cmnBiz.getCabinetSid(NullDefault.getInt(getFil070ParentDirSid(), -1));
        return cmnBiz.isDirAccessAuthUser(fcbSid,
                                          NullDefault.getInt(getFil070ParentDirSid(), -1),
                                          Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
    }

    /**
     * <p>fil080SvPluralKbn を取得します。
     * @return fil080SvPluralKbn
     */
    public String getFil080SvPluralKbn() {
        return fil080SvPluralKbn__;
    }

    /**
     * <p>fil080SvPluralKbn をセットします。
     * @param fil080SvPluralKbn fil080SvPluralKbn
     */
    public void setFil080SvPluralKbn(String fil080SvPluralKbn) {
        fil080SvPluralKbn__ = fil080SvPluralKbn;
    }

    /**
     * <p>fil080PluralKbn を取得します。
     * @return fil080PluralKbn
     */
    public String getFil080PluralKbn() {
        return fil080PluralKbn__;
    }

    /**
     * <p>fil080PluralKbn をセットします。
     * @param fil080PluralKbn fil080PluralKbn
     */
    public void setFil080PluralKbn(String fil080PluralKbn) {
        fil080PluralKbn__ = fil080PluralKbn;
    }

    /**
     * <p>fil080Biko を取得します。
     * @return fil080Biko
     */
    public String getFil080Biko() {
        return fil080Biko__;
    }
    /**
     * <p>fil080Biko をセットします。
     * @param fil080Biko fil080Biko
     */
    public void setFil080Biko(String fil080Biko) {
        fil080Biko__ = fil080Biko;
    }
    /**
     * <p>fil080DirPath を取得します。
     * @return fil080DirPath
     */
    public String getFil080DirPath() {
        return fil080DirPath__;
    }
    /**
     * <p>fil080DirPath をセットします。
     * @param fil080DirPath fil080DirPath
     */
    public void setFil080DirPath(String fil080DirPath) {
        fil080DirPath__ = fil080DirPath;
    }
    /**
     * <p>fil080Mode を取得します。
     * @return fil080Mode
     */
    public int getFil080Mode() {
        return fil080Mode__;
    }
    /**
     * <p>fil080Mode をセットします。
     * @param fil080Mode fil080Mode
     */
    public void setFil080Mode(int fil080Mode) {
        fil080Mode__ = fil080Mode;
    }
    /**
     * <p>fil080PluginId を取得します。
     * @return fil080PluginId
     */
    public String getFil080PluginId() {
        return fil080PluginId__;
    }
    /**
     * <p>fil080PluginId をセットします。
     * @param fil080PluginId fil080PluginId
     */
    public void setFil080PluginId(String fil080PluginId) {
        fil080PluginId__ = fil080PluginId;
    }
    /**
     * <p>fil080VerKbn を取得します。
     * @return fil080VerKbn
     */
    public String getFil080VerKbn() {
        return fil080VerKbn__;
    }
    /**
     * <p>fil080VerKbn をセットします。
     * @param fil080VerKbn fil080VerKbn
     */
    public void setFil080VerKbn(String fil080VerKbn) {
        fil080VerKbn__ = fil080VerKbn;
    }
    /**
     * <p>fil080LockUsrName を取得します。
     * @return fil080LockUsrName
     */
    public String getFil080LockUsrName() {
        return fil080LockUsrName__;
    }
    /**
     * <p>fil080LockUsrName をセットします。
     * @param fil080LockUsrName fil080LockUsrName
     */
    public void setFil080LockUsrName(String fil080LockUsrName) {
        fil080LockUsrName__ = fil080LockUsrName;
    }
    /**
     * <p>fil080FileLabelList を取得します。
     * @return fil080FileLabelList
     */
    public List<LabelValueBean> getFil080FileLabelList() {
        return fil080FileLabelList__;
    }
    /**
     * <p>fil080FileLabelList をセットします。
     * @param fil080FileLabelList fil080FileLabelList
     */
    public void setFil080FileLabelList(List<LabelValueBean> fil080FileLabelList) {
        fil080FileLabelList__ = fil080FileLabelList;
    }

    /**
     * <p>fil080VerKbnLabelList を取得します。
     * @return fil080VerKbnLabelList
     */
    public List<LabelValueBean> getFil080VerKbnLabelList() {
        return fil080VerKbnLabelList__;
    }

    /**
     * <p>fil080VerKbnLabelList をセットします。
     * @param fil080VerKbnLabelList fil080VerKbnLabelList
     */
    public void setFil080VerKbnLabelList(List<LabelValueBean> fil080VerKbnLabelList) {
        fil080VerKbnLabelList__ = fil080VerKbnLabelList;
    }

    /**
     * <p>fil080TempFiles を取得します。
     * @return fil080TempFiles
     */
    public String[] getFil080TempFiles() {
        return fil080TempFiles__;
    }

    /**
     * <p>fil080TempFiles をセットします。
     * @param fil080TempFiles fil080TempFiles
     */
    public void setFil080TempFiles(String[] fil080TempFiles) {
        fil080TempFiles__ = fil080TempFiles;
    }

    /**
     * <p>fil080VerallKbn を取得します。
     * @return fil080VerallKbn
     */
    public String getFil080VerallKbn() {
        return fil080VerallKbn__;
    }

    /**
     * <p>fil080VerallKbn をセットします。
     * @param fil080VerallKbn fil080VerallKbn
     */
    public void setFil080VerallKbn(String fil080VerallKbn) {
        fil080VerallKbn__ = fil080VerallKbn;
    }

    /**
     * <p>fil080UpCmt を取得します。
     * @return fil080UpCmt
     */
    public String getFil080UpCmt() {
        return fil080UpCmt__;
    }

    /**
     * <p>fil080UpCmt をセットします。
     * @param fil080UpCmt fil080UpCmt
     */
    public void setFil080UpCmt(String fil080UpCmt) {
        fil080UpCmt__ = fil080UpCmt;
    }

    /**
     * <p>fil080webmail を取得します。
     * @return fil080webmail
     */
    public int getFil080webmail() {
        return fil080webmail__;
    }

    /**
     * <p>fil080webmail をセットします。
     * @param fil080webmail fil080webmail
     */
    public void setFil080webmail(int fil080webmail) {
        fil080webmail__ = fil080webmail;
    }

    /**
     * <p>fil080webmailId を取得します。
     * @return fil080webmailId
     */
    public long getFil080webmailId() {
        return fil080webmailId__;
    }

    /**
     * <p>fil080webmailId をセットします。
     * @param fil080webmailId fil080webmailId
     */
    public void setFil080webmailId(long fil080webmailId) {
        fil080webmailId__ = fil080webmailId;
    }

    /**
     * <p>fil080EditId を取得します。
     * @return fil080EditId
     */
    public String getFil080EditId() {
        return fil080EditId__;
    }

    /**
     * <p>fil080EditId をセットします。
     * @param fil080EditId fil080EditId
     */
    public void setFil080EditId(String fil080EditId) {
        fil080EditId__ = fil080EditId;
    }

    /**
     * <p>fil080groupList を取得します。
     * @return fil080groupList
     */
    public List<LabelValueBean> getFil080groupList() {
        return fil080groupList__;
    }

    /**
     * <p>fil080groupList をセットします。
     * @param fil080groupList fil080groupList
     */
    public void setFil080groupList(List<LabelValueBean> fil080groupList) {
        fil080groupList__ = fil080groupList;
    }

    /**
     * <p>fil080ParentAccessAll を取得します。
     * @return fil080ParentAccessAll
     */
    public String getFil080ParentAccessAll() {
        return fil080ParentAccessAll__;
    }

    /**
     * <p>fil080ParentAccessAll をセットします。
     * @param fil080ParentAccessAll fil080ParentAccessAll
     */
    public void setFil080ParentAccessAll(String fil080ParentAccessAll) {
        this.fil080ParentAccessAll__ = fil080ParentAccessAll;
    }

    /**
     * <p>fil080AccessKbn を取得します。
     * @return fil080AccessKbn
     */
    public String getFil080AccessKbn() {
        return fil080AccessKbn__;
    }

    /**
     * <p>fil080AccessKbn をセットします。
     * @param fil080AccessKbn fil080AccessKbn
     */
    public void setFil080AccessKbn(String fil080AccessKbn) {
        this.fil080AccessKbn__ = fil080AccessKbn;
    }

    /**
     * <p>fil080AcEditSltGroup を取得します。
     * @return fil080AcEditSltGroup
     */
    public String getFil080AcEditSltGroup() {
        return fil080AcEditSltGroup__;
    }

    /**
     * <p>fil080AcEditSltGroup をセットします。
     * @param fil080AcEditSltGroup fil080AcEditSltGroup
     */
    public void setFil080AcEditSltGroup(String fil080AcEditSltGroup) {
        this.fil080AcEditSltGroup__ = fil080AcEditSltGroup;
    }

    /**
     * <p>fil080AcReadSltGroup を取得します。
     * @return fil080AcReadSltGroup
     */
    public String getFil080AcReadSltGroup() {
        return fil080AcReadSltGroup__;
    }

    /**
     * <p>fil080AcReadSltGroup をセットします。
     * @param fil080AcReadSltGroup fil080AcReadSltGroup
     */
    public void setFil080AcReadSltGroup(String fil080AcReadSltGroup) {
        this.fil080AcReadSltGroup__ = fil080AcReadSltGroup;
    }

    /**
     * <p>fil080SvAcFull を取得します。
     * @return fil080SvAcFull
     */
    public String[] getFil080SvAcFull() {
        return fil080SvAcFull__;
    }

    /**
     * <p>fil080SvAcFull をセットします。
     * @param fil080SvAcFull fil080SvAcFull
     */
    public void setFil080SvAcFull(String[] fil080SvAcFull) {
        this.fil080SvAcFull__ = fil080SvAcFull;
    }

    /**
     * <p>fil080SvAcRead を取得します。
     * @return fil080SvAcRead
     */
    public String[] getFil080SvAcRead() {
        return fil080SvAcRead__;
    }

    /**
     * <p>fil080SvAcRead をセットします。
     * @param fil080SvAcRead fil080SvAcRead
     */
    public void setFil080SvAcRead(String[] fil080SvAcRead) {
        this.fil080SvAcRead__ = fil080SvAcRead;
    }

    /**
     * <p>fil080PublicAcUserUI を取得します。
     * @return fil080PublicAcUserUI
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080PublicAcUserUI__
     */
    public Fil080AuthSelector getFil080PublicAcUserUI() {
        return fil080PublicAcUserUI__;
    }

    /**
     * <p>fil080PublicAcUserUI をセットします。
     * @param fil080PublicAcUserUI fil080PublicAcUserUI
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080PublicAcUserUI__
     */
    public void setFil080PublicAcUserUI(Fil080AuthSelector fil080PublicAcUserUI) {
        fil080PublicAcUserUI__ = fil080PublicAcUserUI;
    }

    /**
     * <p>fil080PrivateAcUserUI を取得します。
     * @return fil080PrivateAcUserUI
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080PrivateAcUserUI__
     */
    public Fil080AuthSelector getFil080PrivateAcUserUI() {
        return fil080PrivateAcUserUI__;
    }

    /**
     * <p>fil080PrivateAcUserUI をセットします。
     * @param fil080PrivateAcUserUI fil080PrivateAcUserUI
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080PrivateAcUserUI__
     */
    public void setFil080PrivateAcUserUI(Fil080AuthSelector fil080PrivateAcUserUI) {
        fil080PrivateAcUserUI__ = fil080PrivateAcUserUI;
    }

    /**
     * <p>fil080ParentEditList を取得します。
     * @return fil080ParentEditList
     */
    public ArrayList<FileParentAccessDspModel> getFil080ParentEditList() {
        return fil080ParentEditList__;
    }

    /**
     * <p>fil080ParentEditList をセットします。
     * @param fil080ParentEditList fil080ParentEditList
     */
    public void setFil080ParentEditList(ArrayList<FileParentAccessDspModel> fil080ParentEditList) {
        this.fil080ParentEditList__ = fil080ParentEditList;
    }

    /**
     * <p>fil080ParentReadList を取得します。
     * @return fil080ParentReadList
     */
    public ArrayList<FileParentAccessDspModel> getFil080ParentReadList() {
        return fil080ParentReadList__;
    }

    /**
     * <p>fil080ParentReadList をセットします。
     * @param fil080ParentReadList fil080ParentReadList
     */
    public void setFil080ParentReadList(ArrayList<FileParentAccessDspModel> fil080ParentReadList) {
        this.fil080ParentReadList__ = fil080ParentReadList;
    }

    /**
     * <p>fil080ParentAccessAllDspFlg を取得します。
     * @return fil080ParentAccessAllDspFlg
     */
    public int getFil080ParentAccessAllDspFlg() {
        return fil080ParentAccessAllDspFlg__;
    }

    /**
     * <p>fil080ParentAccessAllDspFlg をセットします。
     * @param fil080ParentAccessAllDspFlg fil080ParentAccessAllDspFlg
     */
    public void setFil080ParentAccessAllDspFlg(int fil080ParentAccessAllDspFlg) {
        this.fil080ParentAccessAllDspFlg__ = fil080ParentAccessAllDspFlg;
    }

    /**
     * <p>fil080ParentZeroUser を取得します。
     * @return fil080ParentZeroUser
     */
    public String getFil080ParentZeroUser() {
        return fil080ParentZeroUser__;
    }

    /**
     * <p>fil080ParentZeroUser をセットします。
     * @param fil080ParentZeroUser fil080ParentZeroUser
     */
    public void setFil080ParentZeroUser(String fil080ParentZeroUser) {
        this.fil080ParentZeroUser__ = fil080ParentZeroUser;
    }

    /**
     * <p>fil080InitFlg を取得します。
     * @return fil080InitFlg
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080InitFlg__
     */
    public int getFil080InitFlg() {
        return fil080InitFlg__;
    }

    /**
     * <p>fil080InitFlg をセットします。
     * @param fil080InitFlg fil080InitFlg
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080InitFlg__
     */
    public void setFil080InitFlg(int fil080InitFlg) {
        fil080InitFlg__ = fil080InitFlg;
    }
    /**
     * <p>fil080TradeDate を取得します。
     * @return fil080TradeDate
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeDate__
     */
    public String getFil080TradeDate() {
        return fil080TradeDate__;
    }

    /**
     * <p>fil080TradeDate をセットします。
     * @param fil080TradeDate fil080TradeDate
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeDate__
     */
    public void setFil080TradeDate(String fil080TradeDate) {
        fil080TradeDate__ = fil080TradeDate;
    }

    /**
     * <p>fil080TradeTarget を取得します。
     * @return fil080TradeTarget
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeTarget__
     */
    public String getFil080TradeTarget() {
        return fil080TradeTarget__;
    }

    /**
     * <p>fil080TradeTarget をセットします。
     * @param fil080TradeTarget fil080TradeTarget
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeTarget__
     */
    public void setFil080TradeTarget(String fil080TradeTarget) {
        fil080TradeTarget__ = fil080TradeTarget;
    }

    /**
     * <p>fil080TradeMoneyKbn を取得します。
     * @return fil080TradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoneyKbn__
     */
    public int getFil080TradeMoneyKbn() {
        return fil080TradeMoneyKbn__;
    }

    /**
     * <p>fil080TradeMoneyKbn をセットします。
     * @param fil080TradeMoneyKbn fil080TradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoneyKbn__
     */
    public void setFil080TradeMoneyKbn(int fil080TradeMoneyKbn) {
        fil080TradeMoneyKbn__ = fil080TradeMoneyKbn;
    }

    /**
     * <p>fil080TradeMoney を取得します。
     * @return fil080TradeMoney
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoney__
     */
    public String getFil080TradeMoney() {
        return fil080TradeMoney__;
    }

    /**
     * <p>fil080TradeMoney をセットします。
     * @param fil080TradeMoney fil080TradeMoney
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoney__
     */
    public void setFil080TradeMoney(String fil080TradeMoney) {
        fil080TradeMoney__ = fil080TradeMoney;
    }

    /**
     * <p>fil080TradeMoneyType を取得します。
     * @return fil080TradeMoneyType
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoneyType__
     */
    public int getFil080TradeMoneyType() {
        return fil080TradeMoneyType__;
    }

    /**
     * <p>fil080TradeMoneyType をセットします。
     * @param fil080TradeMoneyType fil080TradeMoneyType
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoneyType__
     */
    public void setFil080TradeMoneyType(int fil080TradeMoneyType) {
        fil080TradeMoneyType__ = fil080TradeMoneyType;
    }

    /**
     * <p>fil080TradeMoneyMasterList を取得します。
     * @return fil080TradeMoneyMasterList
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoneyMasterList__
     */
    public ArrayList<LabelValueBean> getFil080TradeMoneyMasterList() {
        return fil080TradeMoneyMasterList__;
    }

    /**
     * <p>fil080TradeMoneyMasterList をセットします。
     * @param fil080TradeMoneyMasterList fil080TradeMoneyMasterList
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080TradeMoneyMasterList__
     */
    public void setFil080TradeMoneyMasterList(
            ArrayList<LabelValueBean> fil080TradeMoneyMasterList) {
        fil080TradeMoneyMasterList__ = fil080TradeMoneyMasterList;
    }
    /**
     * <p>fil080SelectCabinetKbn を取得します。
     * @return fil080SelectCabinetKbn
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080SelectCabinetKbn__
     */
    public int getFil080SelectCabinetKbn() {
        return fil080SelectCabinetKbn__;
    }

    /**
     * <p>fil080SelectCabinetKbn をセットします。
     * @param fil080SelectCabinetKbn fil080SelectCabinetKbn
     * @see jp.groupsession.v2.fil.fil080.Fil080Form#fil080SelectCabinetKbn__
     */
    public void setFil080SelectCabinetKbn(int fil080SelectCabinetKbn) {
        fil080SelectCabinetKbn__ = fil080SelectCabinetKbn;
    }

    /**
     * <p>fil080FcbSortFolder を取得します。
     * @return fil080FcbSortFolder
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080FcbSortFolder__
     */
    public int getFil080FcbSortFolder() {
        return fil080FcbSortFolder__;
    }

    /**
     * <p>fil080FcbSortFolder をセットします。
     * @param fil080FcbSortFolder fil080FcbSortFolder
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080FcbSortFolder__
     */
    public void setFil080FcbSortFolder(int fil080FcbSortFolder) {
        fil080FcbSortFolder__ = fil080FcbSortFolder;
    }

    /**
     * <p>fil080FcbSortFolderName を取得します。
     * @return fil080FcbSortFolderName
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080FcbSortFolderName__
     */
    public String getFil080FcbSortFolderName() {
        return fil080FcbSortFolderName__;
    }

    /**
     * <p>fil080FcbSortFolderName をセットします。
     * @param fil080FcbSortFolderName fil080FcbSortFolderName
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080FcbSortFolderName__
     */
    public void setFil080FcbSortFolderName(String fil080FcbSortFolderName) {
        fil080FcbSortFolderName__ = fil080FcbSortFolderName;
    }
    /**
     * <p>fil080EditFileName を取得します。
     * @return fil080EditFileName
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080EditFileName__
     */
    public String getFil080EditFileName() {
        return fil080EditFileName__;
    }

    /**
     * <p>fil080EditFileName をセットします。
     * @param fil080EditFileName fil080EditFileName
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080EditFileName__
     */
    public void setFil080EditFileName(String fil080EditFileName) {
        fil080EditFileName__ = fil080EditFileName;
    }

    /**
     * <p>fil080OpeComment を取得します。
     * @return fil080OpeComment
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080OpeComment__
     */
    public String getFil080OpeComment() {
        return fil080OpeComment__;
    }

    /**
     * <p>fil080OpeComment をセットします。
     * @param fil080OpeComment fil080OpeComment
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#fil080OpeComment__
     */
    public void setFil080OpeComment(String fil080OpeComment) {
        fil080OpeComment__ = fil080OpeComment;
    }

    /**
     * <p>binList を取得します。
     * @return binList
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#binList__
     */
    public List<CmnBinfModel> getBinList() {
        return binList__;
    }

    /**
     * <p>binList をセットします。
     * @param binList binList
     * @see jp.groupsession.v2.fil.fil080.Fil080ParamModel#binList__
     */
    public void setBinList(List<CmnBinfModel> binList) {
        binList__ = binList;
    }
}