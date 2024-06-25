package jp.groupsession.v2.cir.cir040;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir010.Cir010ParamModel;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.ui.parts.account.AccountSelector;

/**
 * <br>[機  能] 回覧板 新規作成画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir040ParamModel extends Cir010ParamModel {

    /** 初期表示フラグ */
    private int cir040InitFlg__ = 0;

    //入力項目
    /** タイトル */
    private String cir040title__ = null;
    /** 発信部署 */
    private String cir040groupSid__ = null;
    /** 内容 */
    private String cir040value__ = null;
    /** 現在選択中の回覧先(一覧表示に使用する値) */
    private String[] cir040userSid__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] cir040selectFiles__ = null;
    /** 公開／非公開 */
    private int cir040show__ = 0;
    /** メモ欄修正区分 */
    private int cir040memoKbn__ = 0;
    /** メモ欄修正期限(期間指定) */
    private int cir040memoPeriod__ = 0;
    /** メモ欄修正期限(日付指定・年) */
    private int cir040memoPeriodYear__ = -1;
    /** メモ欄修正期限(日付指定・月) */
    private int cir040memoPeriodMonth__ = -1;
    /** メモ欄修正期限(日付指定・日) */
    private int cir040memoPeriodDay__ = -1;
    /** メモ欄修正期限(日付指定・年月日) */
    private String cir040memoPeriodDate__ = null;

    //表示項目
    /** ファイルコンボ */
    private List<LabelValueBean> cir040FileLabelList__ = null;
    /** 添付ファイルSID */
    private String[] cir040FileSids__ = null;
    /** 回覧先リスト */
    private List<CirAccountModel> cir040MemberList__ = null;
    /** 回覧先選択UI */
    private AccountSelector cir040MemberSelector__ = null;

    //非表示項目
    /** プラグインID */
    private String cir040pluginId__ = GSConstCircular.PLUGIN_ID_CIRCULAR;

    /** WEBメール 連携判定 */
    private int cir040webmail__ = 0;
    /** WEBメール 対象メール */
    private long cir040webmailId__ = 0;

    /** 送信時アカウントSID選択値*/
    private int cir040AccountSid__ = 0;


    /**
     * <p>cir040pluginId を取得します。
     * @return cir040pluginId
     */
    public String getCir040pluginId() {
        return cir040pluginId__;
    }

    /**
     * <p>cir040pluginId をセットします。
     * @param cir040pluginId cir040pluginId
     */
    public void setCir040pluginId(String cir040pluginId) {
        cir040pluginId__ = cir040pluginId;
    }

    /**
     * <p>cir040FileLabelList を取得します。
     * @return cir040FileLabelList
     */
    public List<LabelValueBean> getCir040FileLabelList() {
        return cir040FileLabelList__;
    }

    /**
     * <p>cir040FileLabelList をセットします。
     * @param cir040FileLabelList cir040FileLabelList
     */
    public void setCir040FileLabelList(List<LabelValueBean> cir040FileLabelList) {
        cir040FileLabelList__ = cir040FileLabelList;
    }

    /**
     * <p>cir040title を取得します。
     * @return cir040title
     */
    public String getCir040title() {
        return cir040title__;
    }

    /**
     * <p>cir040title をセットします。
     * @param cir040title cir040title
     */
    public void setCir040title(String cir040title) {
        cir040title__ = cir040title;
    }

    /**
     * <p>cir040value を取得します。
     * @return cir040value
     */
    public String getCir040value() {
        return cir040value__;
    }

    /**
     * <p>cir040value をセットします。
     * @param cir040value cir040value
     */
    public void setCir040value(String cir040value) {
        cir040value__ = cir040value;
    }

    /**
     * <p>cir040groupSid を取得します。
     * @return cir040groupSid
     */
    public String getCir040groupSid() {
        return cir040groupSid__;
    }

    /**
     * <p>cir040groupSid をセットします。
     * @param cir040groupSid cir040groupSid
     */
    public void setCir040groupSid(String cir040groupSid) {
        cir040groupSid__ = cir040groupSid;
    }

    /**
     * <p>cir040selectFiles を取得します。
     * @return cir040selectFiles
     */
    public String[] getCir040selectFiles() {
        return cir040selectFiles__;
    }

    /**
     * <p>cir040selectFiles をセットします。
     * @param cir040selectFiles cir040selectFiles
     */
    public void setCir040selectFiles(String[] cir040selectFiles) {
        cir040selectFiles__ = cir040selectFiles;
    }

    /**
     * <p>cir040show を取得します。
     * @return cir040show
     */
    public int getCir040show() {
        return cir040show__;
    }

    /**
     * <p>cir040show をセットします。
     * @param cir040show cir040show
     */
    public void setCir040show(int cir040show) {
        cir040show__ = cir040show;
    }

    /**
     * <p>cir040FileSids を取得します。
     * @return cir040FileSids
     */
    public String[] getCir040FileSids() {
        return cir040FileSids__;
    }

    /**
     * <p>cir040FileSids をセットします。
     * @param cir040FileSids cir040FileSids
     */
    public void setCir040FileSids(String[] cir040FileSids) {
        cir040FileSids__ = cir040FileSids;
    }

    /**
     * @return cir040memoKbn
     */
    public int getCir040memoKbn() {
        return cir040memoKbn__;
    }

    /**
     * @param cir040memoKbn セットする cir040memoKbn
     */
    public void setCir040memoKbn(int cir040memoKbn) {
        cir040memoKbn__ = cir040memoKbn;
    }

    /**
     * @return cir040memoPeriod
     */
    public int getCir040memoPeriod() {
        return cir040memoPeriod__;
    }

    /**
     * @param cir040memoPeriod セットする cir040memoPeriod
     */
    public void setCir040memoPeriod(int cir040memoPeriod) {
        cir040memoPeriod__ = cir040memoPeriod;
    }

    /**
     * @return cir040memoPeriodYear
     */
    public int getCir040memoPeriodYear() {
        return cir040memoPeriodYear__;
    }

    /**
     * @param cir040memoPeriodYear セットする cir040memoPeriodYear
     */
    public void setCir040memoPeriodYear(int cir040memoPeriodYear) {
        cir040memoPeriodYear__ = cir040memoPeriodYear;
    }

    /**
     * @return cir040memoPeriodMonth
     */
    public int getCir040memoPeriodMonth() {
        return cir040memoPeriodMonth__;
    }

    /**
     * @param cir040memoPeriodMonth セットする cir040memoPeriodMonth
     */
    public void setCir040memoPeriodMonth(int cir040memoPeriodMonth) {
        cir040memoPeriodMonth__ = cir040memoPeriodMonth;
    }

    /**
     * @return cir040memoPeriodDay
     */
    public int getCir040memoPeriodDay() {
        return cir040memoPeriodDay__;
    }

    /**
     * @param cir040memoPeriodDay セットする cir040memoPeriodDay
     */
    public void setCir040memoPeriodDay(int cir040memoPeriodDay) {
        cir040memoPeriodDay__ = cir040memoPeriodDay;
    }

    /**
     * <p>cir040memoPeriodDate を取得します。
     * @return cir040memoPeriodDate
     * @see jp.groupsession.v2.cir.cir040.Cir040Form#cir040memoPeriodDate__
     */
    public String getCir040memoPeriodDate() {
        return cir040memoPeriodDate__;
    }

    /**
     * <p>cir040memoPeriodDate をセットします。
     * @param cir040memoPeriodDate cir040memoPeriodDate
     * @see jp.groupsession.v2.cir.cir040.Cir040Form#cir040memoPeriodDate__
     */
    public void setCir040memoPeriodDate(String cir040memoPeriodDate) {
        cir040memoPeriodDate__ = cir040memoPeriodDate;
    }

    /**
     * <p>cir040webmail を取得します。
     * @return cir040webmail
     */
    public int getCir040webmail() {
        return cir040webmail__;
    }

    /**
     * <p>cir040webmail をセットします。
     * @param cir040webmail cir040webmail
     */
    public void setCir040webmail(int cir040webmail) {
        cir040webmail__ = cir040webmail;
    }

    /**
     * <p>cir040webmailId を取得します。
     * @return cir040webmailId
     */
    public long getCir040webmailId() {
        return cir040webmailId__;
    }

    /**
     * <p>cir040webmailId をセットします。
     * @param cir040webmailId cir040webmailId
     */
    public void setCir040webmailId(long cir040webmailId) {
        cir040webmailId__ = cir040webmailId;
    }

    /**
     * <p>cir040MemberList を取得します。
     * @return cir040MemberList
     */
    public List<CirAccountModel> getCir040MemberList() {
        return cir040MemberList__;
    }

    /**
     * <p>cir040MemberList をセットします。
     * @param cir040MemberList cir040MemberList
     */
    public void setCir040MemberList(List<CirAccountModel> cir040MemberList) {
        cir040MemberList__ = cir040MemberList;
    }

    /**
     * <p>cir040userSid を取得します。
     * @return cir040userSid
     */
    public String[] getCir040userSid() {
        return cir040userSid__;
    }

    /**
     * <p>cir040userSid をセットします。
     * @param cir040userSid cir040userSid
     */
    public void setCir040userSid(String[] cir040userSid) {
        cir040userSid__ = cir040userSid;
    }

    /**
     * <p>cir040InitFlg を取得します。
     * @return cir040InitFlg
     */
    public int getCir040InitFlg() {
        return cir040InitFlg__;
    }

    /**
     * <p>cir040InitFlg をセットします。
     * @param cir040InitFlg cir040InitFlg
     */
    public void setCir040InitFlg(int cir040InitFlg) {
        cir040InitFlg__ = cir040InitFlg;
    }

    /**
     * <p>cir040AccountSid を取得します。
     * @return cir040AccountSid
     * @see jp.groupsession.v2.cir.cir040.Cir040ParamModel#cir040AccountSid__
     */
    public int getCir040AccountSid() {
        return cir040AccountSid__;
    }

    /**
     * <p>cir040AccountSid をセットします。
     * @param cir040AccountSid cir040AccountSid
     * @see jp.groupsession.v2.cir.cir040.Cir040ParamModel#cir040AccountSid__
     */
    public void setCir040AccountSid(int cir040AccountSid) {
        cir040AccountSid__ = cir040AccountSid;
    }

    /**
     * <p>cir040MemberSelector を取得します。
     * @return cir040MemberSelector
     * @see jp.groupsession.v2.cir.cir040.Cir040ParamModel#cir040MemberSelector__
     */
    public AccountSelector getCir040MemberSelector() {
        return cir040MemberSelector__;
    }

    /**
     * <p>cir040MemberSelector をセットします。
     * @param cir040MemberSelector cir040MemberSelector
     * @see jp.groupsession.v2.cir.cir040.Cir040ParamModel#cir040MemberSelector__
     */
    public void setCir040MemberSelector(AccountSelector cir040MemberSelector) {
        cir040MemberSelector__ = cir040MemberSelector;
    }
}