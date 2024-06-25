package jp.groupsession.v2.enq.enq010;

import java.util.List;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.enq.model.EnqMenuListModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アンケート一覧画面のパラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq010ParamModel extends AbstractParamModel {
    /** コマンド */
    private String cmd__ = null;
    /** 編集モード */
    private int enqEditMode__ = 0;
    /** フォルダ */
    private int enq010folder__ = 0;
    /** サブフォルダ */
    private int enq010subFolder__ = 0;
    /** 未回答件数 */
    private int enq010UnansCount__ =  0;
    /** 未公開件数 */
    private int enq010notPublicCount__ =  0;
    /** 公開件数 */
    private int enq010publicCount__ =  0;
    /** 草稿件数 */
    private int enq010draftCount__ =  0;

    /** 管理者ユーザフラグ */
    private boolean enq010adminUser__ = false;
    /** 個人設定フラグ */
    private boolean enq010psnFlg__ = false;
    /** アンケート作成対象者フラグ */
    private boolean enq010crtUser__ = false;
    /** ショートメールプラグイン使用可能フラグ */
    private int enq010pluginSmailUse__ = GSConst.PLUGIN_NOT_USE;

    /** ショートメール送信対象アンケート */
    private long enq010smailEnquate__ = 0;
    /** ショートメール通知 回答済みユーザ通知フラグ */
    private int enq010ansedSendKbn__ = 0;

    /** 再描画表示フラグ */
    private int enq010initFlg__ = 0;
    /** 詳細検索フラグ */
    private int enq010searchDetailFlg__ = 0;
    /** 編集対象アンケートSID */
    private long editEnqSid__ = 0;
    /** 回答対象アンケートSID */
    private long ansEnqSid__ = 0;
    /** 選択したアンケートSID */
    private String[] enq010selectEnqSid__ = null;

    /** ソートキー */
    private int enq010sortKey__ = Enq010Const.SORTKEY_OPEN;
    /** 並び順 */
    private int enq010order__ = Enq010Const.ORDER_DESC;

    /** 種類 */
    private int enq010type__ = 0;
    /** キーワード(簡易検索) */
    private String enq010keywordSimple__ = null;
    /** キーワード */
    private String enq010keyword__ = null;
    /** キーワード 種別 */
    private int enq010keywordType__ = 0;
    /** 発信者 グループ */
    private int enq010sendGroup__ = 0;
    /** 発信者 ユーザ */
    private int enq010sendUser__ = 0;
    /** 発信者 入力 */
    private int enq010sendInput__ = 0;
    /** 発信者 テキスト */
    private String enq010sendInputText__ = null;
    /** 作成日 指定なし */
    private int enq010makeDateKbn__ = 0;
    /** 作成日 開始 年 */
    private int enq010makeDateFromYear__ = 0;
    /** 作成日 開始 月 */
    private int enq010makeDateFromMonth__ = 0;
    /** 作成日 開始 日 */
    private int enq010makeDateFromDay__ = 0;
    /** 作成日 終了 年 */
    private int enq010makeDateToYear__ = 0;
    /** 作成日 終了 月 */
    private int enq010makeDateToMonth__ = 0;
    /** 作成日 終了 日 */
    private int enq010makeDateToDay__ = 0;
    /** 作成日 開始 年月日 */
    private String enq010makeDateFrom__ = null;
    /** 作成日 終了 年月日 */
    private String enq010makeDateTo__ = null;
    /** 結果公開期間 指定なし */
    private int enq010resPubDateKbn__ = 0;
    /** 結果公開期間 開始 年 */
    private int enq010resPubDateFromYear__ = 0;
    /** 結果公開期間 開始 月 */
    private int enq010resPubDateFromMonth__ = 0;
    /** 結果公開期間 開始 日 */
    private int enq010resPubDateFromDay__ = 0;
    /** 結果公開期間 終了 年 */
    private int enq010resPubDateToYear__ = 0;
    /** 結果公開期間 終了 月 */
    private int enq010resPubDateToMonth__ = 0;
    /** 結果公開期間 終了 日 */
    private int enq010resPubDateToDay__ = 0;
    /** 結果公開期間 開始 年月日 */
    private String enq010resPubDateFrom__ = null;

    /** 結果公開期間 終了 年月日 */
    private String enq010resPubDateTo__ = null;
    /** 公開期間 指定なし */
    private int enq010pubDateKbn__ = 0;
    /** 公開期間 開始 年 */
    private int enq010pubDateFromYear__ = 0;
    /** 公開期間 開始 月 */
    private int enq010pubDateFromMonth__ = 0;
    /** 公開期間 開始 日 */
    private int enq010pubDateFromDay__ = 0;
    /** 公開期間 終了 年 */
    private int enq010pubDateToYear__ = 0;
    /** 公開期間 終了 月 */
    private int enq010pubDateToMonth__ = 0;
    /** 公開期間 終了 日 */
    private int enq010pubDateToDay__ = 0;
    /** 回答期限 指定なし */
    private int enq010ansDateKbn__ = 0;
    /** 回答期限 開始 年 */
    private int enq010ansDateFromYear__ = 0;
    /** 回答期限 開始 月 */
    private int enq010ansDateFromMonth__ = 0;
    /** 回答期限 開始 日 */
    private int enq010ansDateFromDay__ = 0;
    /** 回答期限 終了 年 */
    private int enq010ansDateToYear__ = 0;
    /** 回答期限 終了 月 */
    private int enq010ansDateToMonth__ = 0;
    /** 回答期限 終了 日 */
    private int enq010ansDateToDay__ = 0;
    /** 回答期限 開始 年月日 */
    private String enq010ansDateFrom__ = null;
    /** 回答期限 終了 年月日 */
    private String enq010ansDateTo__ = null;
    /** 重要度 */
    private int[] enq010priority__ = null;
    /** 状態 */
    private int[] enq010status__ = null;
    /** 状態 期限切れ*/
    private int[] enq010statusAnsOver__ = null;
    /** 状態 期限切れ 簡易表示*/
    private String enq010statusAnsOverSimple__ = null;
    /** 匿名 匿名 */
    private int enq010anony__ = 0;
    /** 種類(検索条件保持) */
    private int enq010svType__ = 0;
    /** キーワード(簡易検索)(検索条件保持) */
    private String enq010svKeywordSimple__ = null;
    /** キーワード(検索条件保持) */
    private String enq010svKeyword__ = null;
    /** キーワード 種別(検索条件保持) */
    private int enq010svKeywordType__ = 0;
    /** 発信者 グループ(検索条件保持) */
    private int enq010svSendGroup__ = 0;
    /** 発信者 ユーザ(検索条件保持) */
    private int enq010svSendUser__ = 0;
    /** 発信者 入力(検索条件保持) */
    private int enq010svSendInput__ = 0;
    /** 発信者 テキスト(検索条件保持) */
    private String enq010svSendInputText__ = null;
    /** 作成日 指定なし(検索条件保持) */
    private int enq010svMakeDateKbn__ = 0;
    /** 作成日 開始 年(検索条件保持) */
    private int enq010svMakeDateFromYear__ = 0;
    /** 作成日 開始 月(検索条件保持) */
    private int enq010svMakeDateFromMonth__ = 0;
    /** 作成日 開始 日(検索条件保持) */
    private int enq010svMakeDateFromDay__ = 0;
    /** 作成日 終了 年(検索条件保持) */
    private int enq010svMakeDateToYear__ = 0;
    /** 作成日 終了 月(検索条件保持) */
    private int enq010svMakeDateToMonth__ = 0;
    /** 作成日 終了 日(検索条件保持) */
    private int enq010svMakeDateToDay__ = 0;
    /** 作成日 開始 年月日(検索条件保持) */
    private String enq010svMakeDateFrom__ = null;
    /** 作成日 終了 年月日(検索条件保持) */
    private String enq010svMakeDateTo__ = null;
    /** 結果公開期間 指定なし(検索条件保持) */
    private int enq010svResPubDateKbn__ = 0;
    /** 結果公開期間 開始 年(検索条件保持) */
    private int enq010svResPubDateFromYear__ = 0;
    /** 結果公開期間 開始 月(検索条件保持) */
    private int enq010svResPubDateFromMonth__ = 0;
    /** 結果公開期間 開始 日(検索条件保持) */
    private int enq010svResPubDateFromDay__ = 0;
    /** 結果公開期間 終了 年(検索条件保持) */
    private int enq010svResPubDateToYear__ = 0;
    /** 結果公開期間 終了 月(検索条件保持) */
    private int enq010svResPubDateToMonth__ = 0;
    /** 結果公開期間 終了 日(検索条件保持) */
    private int enq010svResPubDateToDay__ = 0;
    /** 結果公開期間 開始 年月日(検索条件保持) */
    private String enq010svResPubDateFrom__ = null;
    /** 結果公開期間 終了 年月日(検索条件保持) */
    private String enq010svResPubDateTo__ = null;

    /** 公開期間 指定なし(検索条件保持) */
    private int enq010svPubDateKbn__ = 0;
    /** 公開期間 開始 年(検索条件保持) */
    private int enq010svPubDateFromYear__ = 0;
    /** 公開期間 開始 月(検索条件保持) */
    private int enq010svPubDateFromMonth__ = 0;
    /** 公開期間 開始 日(検索条件保持) */
    private int enq010svPubDateFromDay__ = 0;
    /** 公開期間 終了 年(検索条件保持) */
    private int enq010svPubDateToYear__ = 0;
    /** 公開期間 終了 月(検索条件保持) */
    private int enq010svPubDateToMonth__ = 0;
    /** 公開期間 終了 日(検索条件保持) */
    private int enq010svPubDateToDay__ = 0;
    /** 回答期限 指定なし(検索条件保持) */
    private int enq010svAnsDateKbn__ = 0;
    /** 回答期限 開始 年(検索条件保持) */
    private int enq010svAnsDateFromYear__ = 0;
    /** 回答期限 開始 月(検索条件保持) */
    private int enq010svAnsDateFromMonth__ = 0;
    /** 回答期限 開始 日(検索条件保持) */
    private int enq010svAnsDateFromDay__ = 0;
    /** 回答期限 終了 年(検索条件保持) */
    private int enq010svAnsDateToYear__ = 0;
    /** 回答期限 終了 月(検索条件保持) */
    private int enq010svAnsDateToMonth__ = 0;
    /** 回答期限 終了 日(検索条件保持) */
    private int enq010svAnsDateToDay__ = 0;
    /** 回答期限 開始 年月日(検索条件保持) */
    private String enq010svAnsDateFrom__ = null;
    /** 回答期限 終了 年月日(検索条件保持) */
    private String enq010svAnsDateTo__ = null;
    /** 重要度(検索条件保持) */
    private int[] enq010svPriority__ = null;
    /** 状態(検索条件保持) */
    private int[] enq010svStatus__ = null;
    /** 状態 期限切れ*/
    private int[] enq010svStatusAnsOver__ = null;
    /** 状態 期限切れ 簡易表示*/
    private String enq010svStatusAnsOverSimple__ = null;

    /** 匿名 匿名(検索条件保持) */
    private int enq010svAnony__ = 0;
    /** ページ(上段) */
    private int enq010pageTop__ = 0;
    /** ページ(下段) */
    private int enq010pageBottom__ = 0;
    /** ページリスト */
    private List<LabelValueBean> pageList__ = null;
    /** アンケート情報一覧 */
    private List<Enq010EnqueteModel> enq010EnqueteList__ = null;
    /** アンケート種類リスト */
    private List<LabelValueBean> enqTypeList__ = null;
    /** 発信者 グループリスト */
    private List<LabelValueBean> enqSendGroupList__ = null;
    /** 発信者 ユーザリスト */
    private List<UsrLabelValueBean> enqSendUserList__ = null;
    /** テンプレート一覧 */
    private List<EnqMenuListModel> enq010TemplateList__ = null;

    /**
     * <p>ansEnqSid を取得します。
     * @return ansEnqSid
     */
    public long getAnsEnqSid() {
        return ansEnqSid__;
    }

    /**
     * <p>ansEnqSid をセットします。
     * @param ansEnqSid ansEnqSid
     */
    public void setAnsEnqSid(long ansEnqSid) {
        ansEnqSid__ = ansEnqSid;
    }

    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }

    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }

    /**
     * <p>enqEditMode を取得します。
     * @return enqEditMode
     */
    public int getEnqEditMode() {
        return enqEditMode__;
    }

    /**
     * <p>enqEditMode をセットします。
     * @param enqEditMode enqEditMode
     */
    public void setEnqEditMode(int enqEditMode) {
        enqEditMode__ = enqEditMode;
    }

    /**
     * <p>enq010folder を取得します。
     * @return enq010folder
     */
    public int getEnq010folder() {
        return enq010folder__;
    }

    /**
     * <p>enq010folder をセットします。
     * @param enq010folder enq010folder
     */
    public void setEnq010folder(int enq010folder) {
        enq010folder__ = enq010folder;
    }

    /**
     * <p>enq010subFolder を取得します。
     * @return enq010subFolder
     */
    public int getEnq010subFolder() {
        return enq010subFolder__;
    }

    /**
     * <p>enq010subFolder をセットします。
     * @param enq010subFolder enq010subFolder
     */
    public void setEnq010subFolder(int enq010subFolder) {
        enq010subFolder__ = enq010subFolder;
    }

    /**
     * <p>enq010UnansCount を取得します。
     * @return enq010UnansCount
     */
    public int getEnq010UnansCount() {
        return enq010UnansCount__;
    }

    /**
     * <p>enq010UnansCount をセットします。
     * @param enq010UnansCount enq010UnansCount
     */
    public void setEnq010UnansCount(int enq010UnansCount) {
        enq010UnansCount__ = enq010UnansCount;
    }

    /**
     * <p>enq010notPublicCount を取得します。
     * @return enq010notPublicCount
     */
    public int getEnq010notPublicCount() {
        return enq010notPublicCount__;
    }

    /**
     * <p>enq010notPublicCount をセットします。
     * @param enq010notPublicCount enq010notPublicCount
     */
    public void setEnq010notPublicCount(int enq010notPublicCount) {
        enq010notPublicCount__ = enq010notPublicCount;
    }

    /**
     * <p>enq010publicCount を取得します。
     * @return enq010publicCount
     */
    public int getEnq010publicCount() {
        return enq010publicCount__;
    }

    /**
     * <p>enq010publicCount をセットします。
     * @param enq010publicCount enq010publicCount
     */
    public void setEnq010publicCount(int enq010publicCount) {
        enq010publicCount__ = enq010publicCount;
    }

    /**
     * <p>enq010draftCount を取得します。
     * @return enq010draftCount
     */
    public int getEnq010draftCount() {
        return enq010draftCount__;
    }

    /**
     * <p>enq010draftCount をセットします。
     * @param enq010draftCount enq010draftCount
     */
    public void setEnq010draftCount(int enq010draftCount) {
        enq010draftCount__ = enq010draftCount;
    }

    /**
     * <p>enq010adminUser を取得します。
     * @return enq010adminUser
     */
    public boolean isEnq010adminUser() {
        return enq010adminUser__;
    }

    /**
     * <p>enq010adminUser をセットします。
     * @param enq010adminUser enq010adminUser
     */
    public void setEnq010adminUser(boolean enq010adminUser) {
        enq010adminUser__ = enq010adminUser;
    }

    /**
     * <p>enq010psnFlg を取得します。
     * @return enq010psnFlg
     */
    public boolean isEnq010psnFlg() {
        return enq010psnFlg__;
    }

    /**
     * <p>enq010psnFlg をセットします。
     * @param enq010psnFlg enq010psnFlg
     */
    public void setEnq010psnFlg(boolean enq010psnFlg) {
        enq010psnFlg__ = enq010psnFlg;
    }

    /**
     * <p>enq010crtUser を取得します。
     * @return enq010crtUser
     */
    public boolean isEnq010crtUser() {
        return enq010crtUser__;
    }

    /**
     * <p>enq010crtUser をセットします。
     * @param enq010crtUser enq010crtUser
     */
    public void setEnq010crtUser(boolean enq010crtUser) {
        enq010crtUser__ = enq010crtUser;
    }

    /**
     * <p>enq010pluginSmailUse を取得します。
     * @return enq010pluginSmailUse
     */
    public int getEnq010pluginSmailUse() {
        return enq010pluginSmailUse__;
    }

    /**
     * <p>enq010pluginSmailUse をセットします。
     * @param enq010pluginSmailUse enq010pluginSmailUse
     */
    public void setEnq010pluginSmailUse(int enq010pluginSmailUse) {
        enq010pluginSmailUse__ = enq010pluginSmailUse;
    }

    /**
     * <p>enq010smailEnquate を取得します。
     * @return enq010smailEnquate
     */
    public long getEnq010smailEnquate() {
        return enq010smailEnquate__;
    }

    /**
     * <p>enq010smailEnquate をセットします。
     * @param enq010smailEnquate enq010smailEnquate
     */
    public void setEnq010smailEnquate(long enq010smailEnquate) {
        enq010smailEnquate__ = enq010smailEnquate;
    }

    /**
     * <p>enq010ansedSendKbn を取得します。
     * @return enq010ansedSendKbn
     */
    public int getEnq010ansedSendKbn() {
        return enq010ansedSendKbn__;
    }

    /**
     * <p>enq010ansedSendKbn をセットします。
     * @param enq010ansedSendKbn enq010ansedSendKbn
     */
    public void setEnq010ansedSendKbn(int enq010ansedSendKbn) {
        enq010ansedSendKbn__ = enq010ansedSendKbn;
    }

    /**
     * <p>enq010initFlg を取得します。
     * @return enq010initFlg
     */
    public int getEnq010initFlg() {
        return enq010initFlg__;
    }

    /**
     * <p>enq010initFlg をセットします。
     * @param enq010initFlg enq010initFlg
     */
    public void setEnq010initFlg(int enq010initFlg) {
        enq010initFlg__ = enq010initFlg;
    }

    /**
     * <p>enq010searchDetailFlg を取得します。
     * @return enq010searchDetailFlg
     */
    public int getEnq010searchDetailFlg() {
        return enq010searchDetailFlg__;
    }

    /**
     * <p>enq010searchDetailFlg をセットします。
     * @param enq010searchDetailFlg enq010searchDetailFlg
     */
    public void setEnq010searchDetailFlg(int enq010searchDetailFlg) {
        enq010searchDetailFlg__ = enq010searchDetailFlg;
    }

    /**
     * <p>editEnqSid を取得します。
     * @return editEnqSid
     */
    public long getEditEnqSid() {
        return editEnqSid__;
    }

    /**
     * <p>editEnqSid をセットします。
     * @param editEnqSid editEnqSid
     */
    public void setEditEnqSid(long editEnqSid) {
        editEnqSid__ = editEnqSid;
    }

    /**
     * <p>enq010selectEnqSid を取得します。
     * @return enq010selectEnqSid
     */
    public String[] getEnq010selectEnqSid() {
        return enq010selectEnqSid__;
    }

    /**
     * <p>enq010selectEnqSid をセットします。
     * @param enq010selectEnqSid enq010selectEnqSid
     */
    public void setEnq010selectEnqSid(String[] enq010selectEnqSid) {
        enq010selectEnqSid__ = enq010selectEnqSid;
    }

    /**
     * <p>enq010type を取得します。
     * @return enq010type
     */
    public int getEnq010type() {
        return enq010type__;
    }

    /**
     * <p>enq010type をセットします。
     * @param enq010type enq010type
     */
    public void setEnq010type(int enq010type) {
        enq010type__ = enq010type;
    }

    /**
     * <p>enq010keyword を取得します。
     * @return enq010keyword
     */
    public String getEnq010keyword() {
        return enq010keyword__;
    }

    /**
     * <p>enq010keyword をセットします。
     * @param enq010keyword enq010keyword
     */
    public void setEnq010keyword(String enq010keyword) {
        enq010keyword__ = enq010keyword;
    }

    /**
     * <p>enq010keywordSimple を取得します。
     * @return enq010keywordSimple
     */
    public String getEnq010keywordSimple() {
        return enq010keywordSimple__;
    }

    /**
     * <p>enq010keywordSimple をセットします。
     * @param enq010keywordSimple enq010keywordSimple
     */
    public void setEnq010keywordSimple(String enq010keywordSimple) {
        enq010keywordSimple__ = enq010keywordSimple;
    }

    /**
     * <p>enq010keywordType を取得します。
     * @return enq010keywordType
     */
    public int getEnq010keywordType() {
        return enq010keywordType__;
    }

    /**
     * <p>enq010keywordType をセットします。
     * @param enq010keywordType enq010keywordType
     */
    public void setEnq010keywordType(int enq010keywordType) {
        enq010keywordType__ = enq010keywordType;
    }

    /**
     * <p>enq010sendGroup を取得します。
     * @return enq010sendGroup
     */
    public int getEnq010sendGroup() {
        return enq010sendGroup__;
    }

    /**
     * <p>enq010sendGroup をセットします。
     * @param enq010sendGroup enq010sendGroup
     */
    public void setEnq010sendGroup(int enq010sendGroup) {
        enq010sendGroup__ = enq010sendGroup;
    }

    /**
     * <p>enq010sendUser を取得します。
     * @return enq010sendUser
     */
    public int getEnq010sendUser() {
        return enq010sendUser__;
    }

    /**
     * <p>enq010sendUser をセットします。
     * @param enq010sendUser enq010sendUser
     */
    public void setEnq010sendUser(int enq010sendUser) {
        enq010sendUser__ = enq010sendUser;
    }

    /**
     * <p>enq010sendInput を取得します。
     * @return enq010sendInput
     */
    public int getEnq010sendInput() {
        return enq010sendInput__;
    }

    /**
     * <p>enq010sendInput をセットします。
     * @param enq010sendInput enq010sendInput
     */
    public void setEnq010sendInput(int enq010sendInput) {
        enq010sendInput__ = enq010sendInput;
    }

    /**
     * <p>enq010sendInputText を取得します。
     * @return enq010sendInputText
     */
    public String getEnq010sendInputText() {
        return enq010sendInputText__;
    }

    /**
     * <p>enq010sendInputText をセットします。
     * @param enq010sendInputText enq010sendInputText
     */
    public void setEnq010sendInputText(String enq010sendInputText) {
        enq010sendInputText__ = enq010sendInputText;
    }

    /**
     * <p>enq010makeDateKbn を取得します。
     * @return enq010makeDateKbn
     */
    public int getEnq010makeDateKbn() {
        return enq010makeDateKbn__;
    }

    /**
     * <p>enq010makeDateKbn をセットします。
     * @param enq010makeDateKbn enq010makeDateKbn
     */
    public void setEnq010makeDateKbn(int enq010makeDateKbn) {
        enq010makeDateKbn__ = enq010makeDateKbn;
    }

    /**
     * <p>enq010makeDateFromYear を取得します。
     * @return enq010makeDateFromYear
     */
    public int getEnq010makeDateFromYear() {
        return enq010makeDateFromYear__;
    }

    /**
     * <p>enq010makeDateFromYear をセットします。
     * @param enq010makeDateFromYear enq010makeDateFromYear
     */
    public void setEnq010makeDateFromYear(int enq010makeDateFromYear) {
        enq010makeDateFromYear__ = enq010makeDateFromYear;
    }

    /**
     * <p>enq010makeDateFromMonth を取得します。
     * @return enq010makeDateFromMonth
     */
    public int getEnq010makeDateFromMonth() {
        return enq010makeDateFromMonth__;
    }

    /**
     * <p>enq010makeDateFromMonth をセットします。
     * @param enq010makeDateFromMonth enq010makeDateFromMonth
     */
    public void setEnq010makeDateFromMonth(int enq010makeDateFromMonth) {
        enq010makeDateFromMonth__ = enq010makeDateFromMonth;
    }

    /**
     * <p>enq010makeDateFromDay を取得します。
     * @return enq010makeDateFromDay
     */
    public int getEnq010makeDateFromDay() {
        return enq010makeDateFromDay__;
    }

    /**
     * <p>enq010makeDateFromDay をセットします。
     * @param enq010makeDateFromDay enq010makeDateFromDay
     */
    public void setEnq010makeDateFromDay(int enq010makeDateFromDay) {
        enq010makeDateFromDay__ = enq010makeDateFromDay;
    }

    /**
     * <p>enq010makeDateToYear を取得します。
     * @return enq010makeDateToYear
     */
    public int getEnq010makeDateToYear() {
        return enq010makeDateToYear__;
    }

    /**
     * <p>enq010makeDateToYear をセットします。
     * @param enq010makeDateToYear enq010makeDateToYear
     */
    public void setEnq010makeDateToYear(int enq010makeDateToYear) {
        enq010makeDateToYear__ = enq010makeDateToYear;
    }

    /**
     * <p>enq010makeDateToMonth を取得します。
     * @return enq010makeDateToMonth
     */
    public int getEnq010makeDateToMonth() {
        return enq010makeDateToMonth__;
    }

    /**
     * <p>enq010makeDateToMonth をセットします。
     * @param enq010makeDateToMonth enq010makeDateToMonth
     */
    public void setEnq010makeDateToMonth(int enq010makeDateToMonth) {
        enq010makeDateToMonth__ = enq010makeDateToMonth;
    }

    /**
     * <p>enq010makeDateToDay を取得します。
     * @return enq010makeDateToDay
     */
    public int getEnq010makeDateToDay() {
        return enq010makeDateToDay__;
    }

    /**
     * <p>enq010makeDateToDay をセットします。
     * @param enq010makeDateToDay enq010makeDateToDay
     */
    public void setEnq010makeDateToDay(int enq010makeDateToDay) {
        enq010makeDateToDay__ = enq010makeDateToDay;
    }
    
    /**
     * <p>enq010makeDateFrom を取得します。
     * @return enq010makeDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010makeDateFrom__
     */
    public String getEnq010makeDateFrom() {
        return enq010makeDateFrom__;
    }

    /**
     * <p>enq010makeDateFrom をセットします。
     * @param enq010makeDateFrom enq010makeDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010makeDateFrom__
     */
    public void setEnq010makeDateFrom(String enq010makeDateFrom) {
        enq010makeDateFrom__ = enq010makeDateFrom;
    }

    /**
     * <p>enq010makeDateTo を取得します。
     * @return enq010makeDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010makeDateTo__
     */
    public String getEnq010makeDateTo() {
        return enq010makeDateTo__;
    }

    /**
     * <p>enq010makeDateTo をセットします。
     * @param enq010makeDateTo enq010makeDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010makeDateTo__
     */
    public void setEnq010makeDateTo(String enq010makeDateTo) {
        enq010makeDateTo__ = enq010makeDateTo;
    }

    /**
     * <p>enq010pubDateKbn を取得します。
     * @return enq010pubDateKbn
     */
    public int getEnq010pubDateKbn() {
        return enq010pubDateKbn__;
    }

    /**
     * <p>enq010pubDateKbn をセットします。
     * @param enq010pubDateKbn enq010pubDateKbn
     */
    public void setEnq010pubDateKbn(int enq010pubDateKbn) {
        enq010pubDateKbn__ = enq010pubDateKbn;
    }

    /**
     * <p>enq010pubDateFromYear を取得します。
     * @return enq010pubDateFromYear
     */
    public int getEnq010pubDateFromYear() {
        return enq010pubDateFromYear__;
    }

    /**
     * <p>enq010pubDateFromYear をセットします。
     * @param enq010pubDateFromYear enq010pubDateFromYear
     */
    public void setEnq010pubDateFromYear(int enq010pubDateFromYear) {
        enq010pubDateFromYear__ = enq010pubDateFromYear;
    }

    /**
     * <p>enq010pubDateFromMonth を取得します。
     * @return enq010pubDateFromMonth
     */
    public int getEnq010pubDateFromMonth() {
        return enq010pubDateFromMonth__;
    }

    /**
     * <p>enq010pubDateFromMonth をセットします。
     * @param enq010pubDateFromMonth enq010pubDateFromMonth
     */
    public void setEnq010pubDateFromMonth(int enq010pubDateFromMonth) {
        enq010pubDateFromMonth__ = enq010pubDateFromMonth;
    }

    /**
     * <p>enq010pubDateFromDay を取得します。
     * @return enq010pubDateFromDay
     */
    public int getEnq010pubDateFromDay() {
        return enq010pubDateFromDay__;
    }

    /**
     * <p>enq010pubDateFromDay をセットします。
     * @param enq010pubDateFromDay enq010pubDateFromDay
     */
    public void setEnq010pubDateFromDay(int enq010pubDateFromDay) {
        enq010pubDateFromDay__ = enq010pubDateFromDay;
    }

    /**
     * <p>enq010pubDateToYear を取得します。
     * @return enq010pubDateToYear
     */
    public int getEnq010pubDateToYear() {
        return enq010pubDateToYear__;
    }

    /**
     * <p>enq010pubDateToYear をセットします。
     * @param enq010pubDateToYear enq010pubDateToYear
     */
    public void setEnq010pubDateToYear(int enq010pubDateToYear) {
        enq010pubDateToYear__ = enq010pubDateToYear;
    }

    /**
     * <p>enq010pubDateToMonth を取得します。
     * @return enq010pubDateToMonth
     */
    public int getEnq010pubDateToMonth() {
        return enq010pubDateToMonth__;
    }

    /**
     * <p>enq010pubDateToMonth をセットします。
     * @param enq010pubDateToMonth enq010pubDateToMonth
     */
    public void setEnq010pubDateToMonth(int enq010pubDateToMonth) {
        enq010pubDateToMonth__ = enq010pubDateToMonth;
    }

    /**
     * <p>enq010pubDateToDay を取得します。
     * @return enq010pubDateToDay
     */
    public int getEnq010pubDateToDay() {
        return enq010pubDateToDay__;
    }

    /**
     * <p>enq010pubDateToDay をセットします。
     * @param enq010pubDateToDay enq010pubDateToDay
     */
    public void setEnq010pubDateToDay(int enq010pubDateToDay) {
        enq010pubDateToDay__ = enq010pubDateToDay;
    }

    /**
     * <p>enq010ansDateKbn を取得します。
     * @return enq010ansDateKbn
     */
    public int getEnq010ansDateKbn() {
        return enq010ansDateKbn__;
    }

    /**
     * <p>enq010ansDateKbn をセットします。
     * @param enq010ansDateKbn enq010ansDateKbn
     */
    public void setEnq010ansDateKbn(int enq010ansDateKbn) {
        enq010ansDateKbn__ = enq010ansDateKbn;
    }

    /**
     * <p>enq010ansDateFromYear を取得します。
     * @return enq010ansDateFromYear
     */
    public int getEnq010ansDateFromYear() {
        return enq010ansDateFromYear__;
    }

    /**
     * <p>enq010ansDateFromYear をセットします。
     * @param enq010ansDateFromYear enq010ansDateFromYear
     */
    public void setEnq010ansDateFromYear(int enq010ansDateFromYear) {
        enq010ansDateFromYear__ = enq010ansDateFromYear;
    }

    /**
     * <p>enq010ansDateFromMonth を取得します。
     * @return enq010ansDateFromMonth
     */
    public int getEnq010ansDateFromMonth() {
        return enq010ansDateFromMonth__;
    }

    /**
     * <p>enq010ansDateFromMonth をセットします。
     * @param enq010ansDateFromMonth enq010ansDateFromMonth
     */
    public void setEnq010ansDateFromMonth(int enq010ansDateFromMonth) {
        enq010ansDateFromMonth__ = enq010ansDateFromMonth;
    }

    /**
     * <p>enq010ansDateFromDay を取得します。
     * @return enq010ansDateFromDay
     */
    public int getEnq010ansDateFromDay() {
        return enq010ansDateFromDay__;
    }

    /**
     * <p>enq010ansDateFromDay をセットします。
     * @param enq010ansDateFromDay enq010ansDateFromDay
     */
    public void setEnq010ansDateFromDay(int enq010ansDateFromDay) {
        enq010ansDateFromDay__ = enq010ansDateFromDay;
    }

    /**
     * <p>enq010ansDateToYear を取得します。
     * @return enq010ansDateToYear
     */
    public int getEnq010ansDateToYear() {
        return enq010ansDateToYear__;
    }

    /**
     * <p>enq010ansDateToYear をセットします。
     * @param enq010ansDateToYear enq010ansDateToYear
     */
    public void setEnq010ansDateToYear(int enq010ansDateToYear) {
        enq010ansDateToYear__ = enq010ansDateToYear;
    }

    /**
     * <p>enq010ansDateToMonth を取得します。
     * @return enq010ansDateToMonth
     */
    public int getEnq010ansDateToMonth() {
        return enq010ansDateToMonth__;
    }

    /**
     * <p>enq010ansDateToMonth をセットします。
     * @param enq010ansDateToMonth enq010ansDateToMonth
     */
    public void setEnq010ansDateToMonth(int enq010ansDateToMonth) {
        enq010ansDateToMonth__ = enq010ansDateToMonth;
    }

    /**
     * <p>enq010ansDateToDay を取得します。
     * @return enq010ansDateToDay
     */
    public int getEnq010ansDateToDay() {
        return enq010ansDateToDay__;
    }

    /**
     * <p>enq010ansDateToDay をセットします。
     * @param enq010ansDateToDay enq010ansDateToDay
     */
    public void setEnq010ansDateToDay(int enq010ansDateToDay) {
        enq010ansDateToDay__ = enq010ansDateToDay;
    }
    
    /**
     * <p>enq010ansDateFrom を取得します。
     * @return enq010ansDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010ansDateFrom__
     */
    public String getEnq010ansDateFrom() {
        return enq010ansDateFrom__;
    }

    /**
     * <p>enq010ansDateFrom をセットします。
     * @param enq010ansDateFrom enq010ansDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010ansDateFrom__
     */
    public void setEnq010ansDateFrom(String enq010ansDateFrom) {
        enq010ansDateFrom__ = enq010ansDateFrom;
    }

    /**
     * <p>enq010ansDateTo を取得します。
     * @return enq010ansDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010ansDateTo__
     */
    public String getEnq010ansDateTo() {
        return enq010ansDateTo__;
    }

    /**
     * <p>enq010ansDateTo をセットします。
     * @param enq010ansDateTo enq010ansDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010ansDateTo__
     */
    public void setEnq010ansDateTo(String enq010ansDateTo) {
        enq010ansDateTo__ = enq010ansDateTo;
    }

    /**
     * <p>enq010priority を取得します。
     * @return enq010priority
     */
    public int[] getEnq010priority() {
        return enq010priority__;
    }

    /**
     * <p>enq010priority をセットします。
     * @param enq010priority enq010priority
     */
    public void setEnq010priority(int[] enq010priority) {
        enq010priority__ = enq010priority;
    }

    /**
     * <p>enq010status を取得します。
     * @return enq010status
     */
    public int[] getEnq010status() {
        return enq010status__;
    }

    /**
     * <p>enq010status をセットします。
     * @param enq010status enq010status
     */
    public void setEnq010status(int[] enq010status) {
        enq010status__ = enq010status;
    }

    /**
     * <p>enq010anony を取得します。
     * @return enq010anony
     */
    public int getEnq010anony() {
        return enq010anony__;
    }

    /**
     * <p>enq010anony をセットします。
     * @param enq010anony enq010anony
     */
    public void setEnq010anony(int enq010anony) {
        enq010anony__ = enq010anony;
    }

    /**
     * <p>enq010svType を取得します。
     * @return enq010svType
     */
    public int getEnq010svType() {
        return enq010svType__;
    }

    /**
     * <p>enq010svType をセットします。
     * @param enq010svType enq010svType
     */
    public void setEnq010svType(int enq010svType) {
        enq010svType__ = enq010svType;
    }

    /**
     * <p>enq010svKeyword を取得します。
     * @return enq010svKeyword
     */
    public String getEnq010svKeyword() {
        return enq010svKeyword__;
    }

    /**
     * <p>enq010svKeyword をセットします。
     * @param enq010svKeyword enq010svKeyword
     */
    public void setEnq010svKeyword(String enq010svKeyword) {
        enq010svKeyword__ = enq010svKeyword;
    }

    /**
     * <p>enq010svKeywordSimple を取得します。
     * @return enq010svKeywordSimple
     */
    public String getEnq010svKeywordSimple() {
        return enq010svKeywordSimple__;
    }

    /**
     * <p>enq010svKeywordSimple をセットします。
     * @param enq010svKeywordSimple enq010svKeywordSimple
     */
    public void setEnq010svKeywordSimple(String enq010svKeywordSimple) {
        enq010svKeywordSimple__ = enq010svKeywordSimple;
    }

    /**
     * <p>enq010svKeywordType を取得します。
     * @return enq010svKeywordType
     */
    public int getEnq010svKeywordType() {
        return enq010svKeywordType__;
    }

    /**
     * <p>enq010svKeywordType をセットします。
     * @param enq010svKeywordType enq010svKeywordType
     */
    public void setEnq010svKeywordType(int enq010svKeywordType) {
        enq010svKeywordType__ = enq010svKeywordType;
    }

    /**
     * <p>enq010svSendGroup を取得します。
     * @return enq010svSendGroup
     */
    public int getEnq010svSendGroup() {
        return enq010svSendGroup__;
    }

    /**
     * <p>enq010svSendGroup をセットします。
     * @param enq010svSendGroup enq010svSendGroup
     */
    public void setEnq010svSendGroup(int enq010svSendGroup) {
        enq010svSendGroup__ = enq010svSendGroup;
    }

    /**
     * <p>enq010svSendUser を取得します。
     * @return enq010svSendUser
     */
    public int getEnq010svSendUser() {
        return enq010svSendUser__;
    }

    /**
     * <p>enq010svSendUser をセットします。
     * @param enq010svSendUser enq010svSendUser
     */
    public void setEnq010svSendUser(int enq010svSendUser) {
        enq010svSendUser__ = enq010svSendUser;
    }

    /**
     * <p>enq010svSendInput を取得します。
     * @return enq010svSendInput
     */
    public int getEnq010svSendInput() {
        return enq010svSendInput__;
    }

    /**
     * <p>enq010svSendInput をセットします。
     * @param enq010svSendInput enq010svSendInput
     */
    public void setEnq010svSendInput(int enq010svSendInput) {
        enq010svSendInput__ = enq010svSendInput;
    }

    /**
     * <p>enq010svSendInputText を取得します。
     * @return enq010svSendInputText
     */
    public String getEnq010svSendInputText() {
        return enq010svSendInputText__;
    }

    /**
     * <p>enq010svSendInputText をセットします。
     * @param enq010svSendInputText enq010svSendInputText
     */
    public void setEnq010svSendInputText(String enq010svSendInputText) {
        enq010svSendInputText__ = enq010svSendInputText;
    }

    /**
     * <p>enq010svMakeDateKbn を取得します。
     * @return enq010svMakeDateKbn
     */
    public int getEnq010svMakeDateKbn() {
        return enq010svMakeDateKbn__;
    }

    /**
     * <p>enq010svMakeDateKbn をセットします。
     * @param enq010svMakeDateKbn enq010svMakeDateKbn
     */
    public void setEnq010svMakeDateKbn(int enq010svMakeDateKbn) {
        enq010svMakeDateKbn__ = enq010svMakeDateKbn;
    }

    /**
     * <p>enq010svMakeDateFromYear を取得します。
     * @return enq010svMakeDateFromYear
     */
    public int getEnq010svMakeDateFromYear() {
        return enq010svMakeDateFromYear__;
    }

    /**
     * <p>enq010svMakeDateFromYear をセットします。
     * @param enq010svMakeDateFromYear enq010svMakeDateFromYear
     */
    public void setEnq010svMakeDateFromYear(int enq010svMakeDateFromYear) {
        enq010svMakeDateFromYear__ = enq010svMakeDateFromYear;
    }

    /**
     * <p>enq010svMakeDateFromMonth を取得します。
     * @return enq010svMakeDateFromMonth
     */
    public int getEnq010svMakeDateFromMonth() {
        return enq010svMakeDateFromMonth__;
    }

    /**
     * <p>enq010svMakeDateFromMonth をセットします。
     * @param enq010svMakeDateFromMonth enq010svMakeDateFromMonth
     */
    public void setEnq010svMakeDateFromMonth(int enq010svMakeDateFromMonth) {
        enq010svMakeDateFromMonth__ = enq010svMakeDateFromMonth;
    }

    /**
     * <p>enq010svMakeDateFromDay を取得します。
     * @return enq010svMakeDateFromDay
     */
    public int getEnq010svMakeDateFromDay() {
        return enq010svMakeDateFromDay__;
    }

    /**
     * <p>enq010svMakeDateFromDay をセットします。
     * @param enq010svMakeDateFromDay enq010svMakeDateFromDay
     */
    public void setEnq010svMakeDateFromDay(int enq010svMakeDateFromDay) {
        enq010svMakeDateFromDay__ = enq010svMakeDateFromDay;
    }

    /**
     * <p>enq010svMakeDateToYear を取得します。
     * @return enq010svMakeDateToYear
     */
    public int getEnq010svMakeDateToYear() {
        return enq010svMakeDateToYear__;
    }

    /**
     * <p>enq010svMakeDateToYear をセットします。
     * @param enq010svMakeDateToYear enq010svMakeDateToYear
     */
    public void setEnq010svMakeDateToYear(int enq010svMakeDateToYear) {
        enq010svMakeDateToYear__ = enq010svMakeDateToYear;
    }

    /**
     * <p>enq010svMakeDateToMonth を取得します。
     * @return enq010svMakeDateToMonth
     */
    public int getEnq010svMakeDateToMonth() {
        return enq010svMakeDateToMonth__;
    }

    /**
     * <p>enq010svMakeDateToMonth をセットします。
     * @param enq010svMakeDateToMonth enq010svMakeDateToMonth
     */
    public void setEnq010svMakeDateToMonth(int enq010svMakeDateToMonth) {
        enq010svMakeDateToMonth__ = enq010svMakeDateToMonth;
    }

    /**
     * <p>enq010svMakeDateToDay を取得します。
     * @return enq010svMakeDateToDay
     */
    public int getEnq010svMakeDateToDay() {
        return enq010svMakeDateToDay__;
    }

    /**
     * <p>enq010svMakeDateToDay をセットします。
     * @param enq010svMakeDateToDay enq010svMakeDateToDay
     */
    public void setEnq010svMakeDateToDay(int enq010svMakeDateToDay) {
        enq010svMakeDateToDay__ = enq010svMakeDateToDay;
    }
    
    /**
     * <p>enq010svMakeDateFrom を取得します。
     * @return enq010svMakeDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svMakeDateFrom__
     */
    public String getEnq010svMakeDateFrom() {
        return enq010svMakeDateFrom__;
    }

    /**
     * <p>enq010svMakeDateFrom をセットします。
     * @param enq010svMakeDateFrom enq010svMakeDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svMakeDateFrom__
     */
    public void setEnq010svMakeDateFrom(String enq010svMakeDateFrom) {
        enq010svMakeDateFrom__ = enq010svMakeDateFrom;
    }

    /**
     * <p>enq010svMakeDateTo を取得します。
     * @return enq010svMakeDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svMakeDateTo__
     */
    public String getEnq010svMakeDateTo() {
        return enq010svMakeDateTo__;
    }

    /**
     * <p>enq010svMakeDateTo をセットします。
     * @param enq010svMakeDateTo enq010svMakeDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svMakeDateTo__
     */
    public void setEnq010svMakeDateTo(String enq010svMakeDateTo) {
        enq010svMakeDateTo__ = enq010svMakeDateTo;
    }

    /**
     * <p>enq010svPubDateKbn を取得します。
     * @return enq010svPubDateKbn
     */
    public int getEnq010svPubDateKbn() {
        return enq010svPubDateKbn__;
    }

    /**
     * <p>enq010svPubDateKbn をセットします。
     * @param enq010svPubDateKbn enq010svPubDateKbn
     */
    public void setEnq010svPubDateKbn(int enq010svPubDateKbn) {
        enq010svPubDateKbn__ = enq010svPubDateKbn;
    }

    /**
     * <p>enq010svPubDateFromYear を取得します。
     * @return enq010svPubDateFromYear
     */
    public int getEnq010svPubDateFromYear() {
        return enq010svPubDateFromYear__;
    }

    /**
     * <p>enq010svPubDateFromYear をセットします。
     * @param enq010svPubDateFromYear enq010svPubDateFromYear
     */
    public void setEnq010svPubDateFromYear(int enq010svPubDateFromYear) {
        enq010svPubDateFromYear__ = enq010svPubDateFromYear;
    }

    /**
     * <p>enq010svPubDateFromMonth を取得します。
     * @return enq010svPubDateFromMonth
     */
    public int getEnq010svPubDateFromMonth() {
        return enq010svPubDateFromMonth__;
    }

    /**
     * <p>enq010svPubDateFromMonth をセットします。
     * @param enq010svPubDateFromMonth enq010svPubDateFromMonth
     */
    public void setEnq010svPubDateFromMonth(int enq010svPubDateFromMonth) {
        enq010svPubDateFromMonth__ = enq010svPubDateFromMonth;
    }

    /**
     * <p>enq010svPubDateFromDay を取得します。
     * @return enq010svPubDateFromDay
     */
    public int getEnq010svPubDateFromDay() {
        return enq010svPubDateFromDay__;
    }

    /**
     * <p>enq010svPubDateFromDay をセットします。
     * @param enq010svPubDateFromDay enq010svPubDateFromDay
     */
    public void setEnq010svPubDateFromDay(int enq010svPubDateFromDay) {
        enq010svPubDateFromDay__ = enq010svPubDateFromDay;
    }

    /**
     * <p>enq010svPubDateToYear を取得します。
     * @return enq010svPubDateToYear
     */
    public int getEnq010svPubDateToYear() {
        return enq010svPubDateToYear__;
    }

    /**
     * <p>enq010svPubDateToYear をセットします。
     * @param enq010svPubDateToYear enq010svPubDateToYear
     */
    public void setEnq010svPubDateToYear(int enq010svPubDateToYear) {
        enq010svPubDateToYear__ = enq010svPubDateToYear;
    }

    /**
     * <p>enq010svPubDateToMonth を取得します。
     * @return enq010svPubDateToMonth
     */
    public int getEnq010svPubDateToMonth() {
        return enq010svPubDateToMonth__;
    }

    /**
     * <p>enq010svPubDateToMonth をセットします。
     * @param enq010svPubDateToMonth enq010svPubDateToMonth
     */
    public void setEnq010svPubDateToMonth(int enq010svPubDateToMonth) {
        enq010svPubDateToMonth__ = enq010svPubDateToMonth;
    }

    /**
     * <p>enq010svPubDateToDay を取得します。
     * @return enq010svPubDateToDay
     */
    public int getEnq010svPubDateToDay() {
        return enq010svPubDateToDay__;
    }

    /**
     * <p>enq010svPubDateToDay をセットします。
     * @param enq010svPubDateToDay enq010svPubDateToDay
     */
    public void setEnq010svPubDateToDay(int enq010svPubDateToDay) {
        enq010svPubDateToDay__ = enq010svPubDateToDay;
    }

    /**
     * <p>enq010svAnsDateKbn を取得します。
     * @return enq010svAnsDateKbn
     */
    public int getEnq010svAnsDateKbn() {
        return enq010svAnsDateKbn__;
    }

    /**
     * <p>enq010svAnsDateKbn をセットします。
     * @param enq010svAnsDateKbn enq010svAnsDateKbn
     */
    public void setEnq010svAnsDateKbn(int enq010svAnsDateKbn) {
        enq010svAnsDateKbn__ = enq010svAnsDateKbn;
    }

    /**
     * <p>enq010svAnsDateFromYear を取得します。
     * @return enq010svAnsDateFromYear
     */
    public int getEnq010svAnsDateFromYear() {
        return enq010svAnsDateFromYear__;
    }

    /**
     * <p>enq010svAnsDateFromYear をセットします。
     * @param enq010svAnsDateFromYear enq010svAnsDateFromYear
     */
    public void setEnq010svAnsDateFromYear(int enq010svAnsDateFromYear) {
        enq010svAnsDateFromYear__ = enq010svAnsDateFromYear;
    }

    /**
     * <p>enq010svAnsDateFromMonth を取得します。
     * @return enq010svAnsDateFromMonth
     */
    public int getEnq010svAnsDateFromMonth() {
        return enq010svAnsDateFromMonth__;
    }

    /**
     * <p>enq010svAnsDateFromMonth をセットします。
     * @param enq010svAnsDateFromMonth enq010svAnsDateFromMonth
     */
    public void setEnq010svAnsDateFromMonth(int enq010svAnsDateFromMonth) {
        enq010svAnsDateFromMonth__ = enq010svAnsDateFromMonth;
    }

    /**
     * <p>enq010svAnsDateFromDay を取得します。
     * @return enq010svAnsDateFromDay
     */
    public int getEnq010svAnsDateFromDay() {
        return enq010svAnsDateFromDay__;
    }

    /**
     * <p>enq010svAnsDateFromDay をセットします。
     * @param enq010svAnsDateFromDay enq010svAnsDateFromDay
     */
    public void setEnq010svAnsDateFromDay(int enq010svAnsDateFromDay) {
        enq010svAnsDateFromDay__ = enq010svAnsDateFromDay;
    }

    /**
     * <p>enq010svAnsDateToYear を取得します。
     * @return enq010svAnsDateToYear
     */
    public int getEnq010svAnsDateToYear() {
        return enq010svAnsDateToYear__;
    }

    /**
     * <p>enq010svAnsDateToYear をセットします。
     * @param enq010svAnsDateToYear enq010svAnsDateToYear
     */
    public void setEnq010svAnsDateToYear(int enq010svAnsDateToYear) {
        enq010svAnsDateToYear__ = enq010svAnsDateToYear;
    }

    /**
     * <p>enq010svAnsDateToMonth を取得します。
     * @return enq010svAnsDateToMonth
     */
    public int getEnq010svAnsDateToMonth() {
        return enq010svAnsDateToMonth__;
    }

    /**
     * <p>enq010svAnsDateToMonth をセットします。
     * @param enq010svAnsDateToMonth enq010svAnsDateToMonth
     */
    public void setEnq010svAnsDateToMonth(int enq010svAnsDateToMonth) {
        enq010svAnsDateToMonth__ = enq010svAnsDateToMonth;
    }

    /**
     * <p>enq010svAnsDateToDay を取得します。
     * @return enq010svAnsDateToDay
     */
    public int getEnq010svAnsDateToDay() {
        return enq010svAnsDateToDay__;
    }

    /**
     * <p>enq010svAnsDateToDay をセットします。
     * @param enq010svAnsDateToDay enq010svAnsDateToDay
     */
    public void setEnq010svAnsDateToDay(int enq010svAnsDateToDay) {
        enq010svAnsDateToDay__ = enq010svAnsDateToDay;
    }

    /**
     * <p>enq010svAnsDateFrom を取得します。
     * @return enq010svAnsDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010svAnsDateFrom__
     */
    public String getEnq010svAnsDateFrom() {
        return enq010svAnsDateFrom__;
    }

    /**
     * <p>enq010svAnsDateFrom をセットします。
     * @param enq010svAnsDateFrom enq010svAnsDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010svAnsDateFrom__
     */
    public void setEnq010svAnsDateFrom(String enq010svAnsDateFrom) {
        enq010svAnsDateFrom__ = enq010svAnsDateFrom;
    }

    /**
     * <p>enq010svAnsDateTo を取得します。
     * @return enq010svAnsDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010svAnsDateTo__
     */
    public String getEnq010svAnsDateTo() {
        return enq010svAnsDateTo__;
    }

    /**
     * <p>enq010svAnsDateTo をセットします。
     * @param enq010svAnsDateTo enq010svAnsDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010svAnsDateTo__
     */
    public void setEnq010svAnsDateTo(String enq010svAnsDateTo) {
        enq010svAnsDateTo__ = enq010svAnsDateTo;
    }

    /**
     * <p>enq010svPriority を取得します。
     * @return enq010svPriority
     */
    public int[] getEnq010svPriority() {
        return enq010svPriority__;
    }

    /**
     * <p>enq010svPriority をセットします。
     * @param enq010svPriority enq010svPriority
     */
    public void setEnq010svPriority(int[] enq010svPriority) {
        enq010svPriority__ = enq010svPriority;
    }

    /**
     * <p>enq010svStatus を取得します。
     * @return enq010svStatus
     */
    public int[] getEnq010svStatus() {
        return enq010svStatus__;
    }

    /**
     * <p>enq010svStatus をセットします。
     * @param enq010svStatus enq010svStatus
     */
    public void setEnq010svStatus(int[] enq010svStatus) {
        enq010svStatus__ = enq010svStatus;
    }

    /**
     * <p>enq010svAnony を取得します。
     * @return enq010svAnony
     */
    public int getEnq010svAnony() {
        return enq010svAnony__;
    }

    /**
     * <p>enq010svAnony をセットします。
     * @param enq010svAnony enq010svAnony
     */
    public void setEnq010svAnony(int enq010svAnony) {
        enq010svAnony__ = enq010svAnony;
    }

    /**
     * <p>enq010pageTop を取得します。
     * @return enq010pageTop
     */
    public int getEnq010pageTop() {
        return enq010pageTop__;
    }

    /**
     * <p>enq010pageTop をセットします。
     * @param enq010pageTop enq010pageTop
     */
    public void setEnq010pageTop(int enq010pageTop) {
        enq010pageTop__ = enq010pageTop;
    }

    /**
     * <p>enq010pageBottom を取得します。
     * @return enq010pageBottom
     */
    public int getEnq010pageBottom() {
        return enq010pageBottom__;
    }

    /**
     * <p>enq010pageBottom をセットします。
     * @param enq010pageBottom enq010pageBottom
     */
    public void setEnq010pageBottom(int enq010pageBottom) {
        enq010pageBottom__ = enq010pageBottom;
    }

    /**
     * <p>enq010sortKey を取得します。
     * @return enq010sortKey
     */
    public int getEnq010sortKey() {
        return enq010sortKey__;
    }

    /**
     * <p>enq010sortKey をセットします。
     * @param enq010sortKey enq010sortKey
     */
    public void setEnq010sortKey(int enq010sortKey) {
        enq010sortKey__ = enq010sortKey;
    }

    /**
     * <p>enq010order を取得します。
     * @return enq010order
     */
    public int getEnq010order() {
        return enq010order__;
    }

    /**
     * <p>enq010order をセットします。
     * @param enq010order enq010order
     */
    public void setEnq010order(int enq010order) {
        enq010order__ = enq010order;
    }

    /**
     * <p>pageList を取得します。
     * @return pageList
     */
    public List<LabelValueBean> getPageList() {
        return pageList__;
    }

    /**
     * <p>pageList をセットします。
     * @param pageList pageList
     */
    public void setPageList(List<LabelValueBean> pageList) {
        pageList__ = pageList;
    }

    /**
     * <p>enq010EnqueteList を取得します。
     * @return enq010EnqueteList
     */
    public List<Enq010EnqueteModel> getEnq010EnqueteList() {
        return enq010EnqueteList__;
    }

    /**
     * <p>enq010EnqueteList をセットします。
     * @param enq010EnqueteList enq010EnqueteList
     */
    public void setEnq010EnqueteList(List<Enq010EnqueteModel> enq010EnqueteList) {
        enq010EnqueteList__ = enq010EnqueteList;
    }

    /**
     * <p>enqTypeList を取得します。
     * @return enqTypeList
     */
    public List<LabelValueBean> getEnqTypeList() {
        return enqTypeList__;
    }

    /**
     * <p>enqTypeList をセットします。
     * @param enqTypeList enqTypeList
     */
    public void setEnqTypeList(List<LabelValueBean> enqTypeList) {
        enqTypeList__ = enqTypeList;
    }

    /**
     * <p>enqSendGroupList を取得します。
     * @return enqSendGroupList
     */
    public List<LabelValueBean> getEnqSendGroupList() {
        return enqSendGroupList__;
    }

    /**
     * <p>enqSendGroupList をセットします。
     * @param enqSendGroupList enqSendGroupList
     */
    public void setEnqSendGroupList(List<LabelValueBean> enqSendGroupList) {
        enqSendGroupList__ = enqSendGroupList;
    }

    /**
     * <p>enqSendUserList を取得します。
     * @return enqSendUserList
     */
    public List<UsrLabelValueBean> getEnqSendUserList() {
        return enqSendUserList__;
    }

    /**
     * <p>enqSendUserList をセットします。
     * @param list enqSendUserList
     */
    public void setEnqSendUserList(List<UsrLabelValueBean> list) {
        enqSendUserList__ = list;
    }

    /**
     * <p>enq010TemplateList を取得します。
     * @return enq010TemplateList
     */
    public List<EnqMenuListModel> getEnq010TemplateList() {
        return enq010TemplateList__;
    }

    /**
     * <p>enq010TemplateList をセットします。
     * @param enq010TemplateList enq010TemplateList
     */
    public void setEnq010TemplateList(List<EnqMenuListModel> enq010TemplateList) {
        enq010TemplateList__ = enq010TemplateList;
    }

    /**
     * <p>enq010resPubDateKbn を取得します。
     * @return enq010resPubDateKbn
     */
    public int getEnq010resPubDateKbn() {
        return enq010resPubDateKbn__;
    }

    /**
     * <p>enq010resPubDateKbn をセットします。
     * @param enq010resPubDateKbn enq010resPubDateKbn
     */
    public void setEnq010resPubDateKbn(int enq010resPubDateKbn) {
        enq010resPubDateKbn__ = enq010resPubDateKbn;
    }

    /**
     * <p>enq010resPubDateFromYear を取得します。
     * @return enq010resPubDateFromYear
     */
    public int getEnq010resPubDateFromYear() {
        return enq010resPubDateFromYear__;
    }

    /**
     * <p>enq010resPubDateFromYear をセットします。
     * @param enq010resPubDateFromYear enq010resPubDateFromYear
     */
    public void setEnq010resPubDateFromYear(int enq010resPubDateFromYear) {
        enq010resPubDateFromYear__ = enq010resPubDateFromYear;
    }

    /**
     * <p>enq010resPubDateFromMonth を取得します。
     * @return enq010resPubDateFromMonth
     */
    public int getEnq010resPubDateFromMonth() {
        return enq010resPubDateFromMonth__;
    }

    /**
     * <p>enq010resPubDateFromMonth をセットします。
     * @param enq010resPubDateFromMonth enq010resPubDateFromMonth
     */
    public void setEnq010resPubDateFromMonth(int enq010resPubDateFromMonth) {
        enq010resPubDateFromMonth__ = enq010resPubDateFromMonth;
    }

    /**
     * <p>enq010resPubDateFromDay を取得します。
     * @return enq010resPubDateFromDay
     */
    public int getEnq010resPubDateFromDay() {
        return enq010resPubDateFromDay__;
    }

    /**
     * <p>enq010resPubDateFromDay をセットします。
     * @param enq010resPubDateFromDay enq010resPubDateFromDay
     */
    public void setEnq010resPubDateFromDay(int enq010resPubDateFromDay) {
        enq010resPubDateFromDay__ = enq010resPubDateFromDay;
    }

    /**
     * <p>enq010resPubDateToYear を取得します。
     * @return enq010resPubDateToYear
     */
    public int getEnq010resPubDateToYear() {
        return enq010resPubDateToYear__;
    }

    /**
     * <p>enq010resPubDateToYear をセットします。
     * @param enq010resPubDateToYear enq010resPubDateToYear
     */
    public void setEnq010resPubDateToYear(int enq010resPubDateToYear) {
        enq010resPubDateToYear__ = enq010resPubDateToYear;
    }

    /**
     * <p>enq010resPubDateToMonth を取得します。
     * @return enq010resPubDateToMonth
     */
    public int getEnq010resPubDateToMonth() {
        return enq010resPubDateToMonth__;
    }

    /**
     * <p>enq010resPubDateToMonth をセットします。
     * @param enq010resPubDateToMonth enq010resPubDateToMonth
     */
    public void setEnq010resPubDateToMonth(int enq010resPubDateToMonth) {
        enq010resPubDateToMonth__ = enq010resPubDateToMonth;
    }

    /**
     * <p>enq010resPubDateToDay を取得します。
     * @return enq010resPubDateToDay
     */
    public int getEnq010resPubDateToDay() {
        return enq010resPubDateToDay__;
    }

    /**
     * <p>enq010resPubDateToDay をセットします。
     * @param enq010resPubDateToDay enq010resPubDateToDay
     */
    public void setEnq010resPubDateToDay(int enq010resPubDateToDay) {
        enq010resPubDateToDay__ = enq010resPubDateToDay;
    }
    
    /**
     * <p>enq010resPubDateFrom を取得します。
     * @return enq010resPubDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010resPubDateFrom__
     */
    public String getEnq010resPubDateFrom() {
        return enq010resPubDateFrom__;
    }

    /**
     * <p>enq010resPubDateFrom をセットします。
     * @param enq010resPubDateFrom enq010resPubDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010resPubDateFrom__
     */
    public void setEnq010resPubDateFrom(String enq010resPubDateFrom) {
        enq010resPubDateFrom__ = enq010resPubDateFrom;
    }

    /**
     * <p>enq010resPubDateTo を取得します。
     * @return enq010resPubDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010resPubDateTo__
     */
    public String getEnq010resPubDateTo() {
        return enq010resPubDateTo__;
    }

    /**
     * <p>enq010resPubDateTo をセットします。
     * @param enq010resPubDateTo enq010resPubDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010ParamModel#enq010resPubDateTo__
     */
    public void setEnq010resPubDateTo(String enq010resPubDateTo) {
        enq010resPubDateTo__ = enq010resPubDateTo;
    }

    /**
     * <p>enq010svResPubDateKbn を取得します。
     * @return enq010svResPubDateKbn
     */
    public int getEnq010svResPubDateKbn() {
        return enq010svResPubDateKbn__;
    }

    /**
     * <p>enq010svResPubDateKbn をセットします。
     * @param enq010svResPubDateKbn enq010svResPubDateKbn
     */
    public void setEnq010svResPubDateKbn(int enq010svResPubDateKbn) {
        enq010svResPubDateKbn__ = enq010svResPubDateKbn;
    }

    /**
     * <p>enq010svResPubDateFromYear を取得します。
     * @return enq010svResPubDateFromYear
     */
    public int getEnq010svResPubDateFromYear() {
        return enq010svResPubDateFromYear__;
    }

    /**
     * <p>enq010svResPubDateFromYear をセットします。
     * @param enq010svResPubDateFromYear enq010svResPubDateFromYear
     */
    public void setEnq010svResPubDateFromYear(int enq010svResPubDateFromYear) {
        enq010svResPubDateFromYear__ = enq010svResPubDateFromYear;
    }

    /**
     * <p>enq010svResPubDateFromMonth を取得します。
     * @return enq010svResPubDateFromMonth
     */
    public int getEnq010svResPubDateFromMonth() {
        return enq010svResPubDateFromMonth__;
    }

    /**
     * <p>enq010svResPubDateFromMonth をセットします。
     * @param enq010svResPubDateFromMonth enq010svResPubDateFromMonth
     */
    public void setEnq010svResPubDateFromMonth(int enq010svResPubDateFromMonth) {
        enq010svResPubDateFromMonth__ = enq010svResPubDateFromMonth;
    }

    /**
     * <p>enq010svResPubDateFromDay を取得します。
     * @return enq010svResPubDateFromDay
     */
    public int getEnq010svResPubDateFromDay() {
        return enq010svResPubDateFromDay__;
    }

    /**
     * <p>enq010svResPubDateFromDay をセットします。
     * @param enq010svResPubDateFromDay enq010svResPubDateFromDay
     */
    public void setEnq010svResPubDateFromDay(int enq010svResPubDateFromDay) {
        enq010svResPubDateFromDay__ = enq010svResPubDateFromDay;
    }

    /**
     * <p>enq010svResPubDateToYear を取得します。
     * @return enq010svResPubDateToYear
     */
    public int getEnq010svResPubDateToYear() {
        return enq010svResPubDateToYear__;
    }

    /**
     * <p>enq010svResPubDateToYear をセットします。
     * @param enq010svResPubDateToYear enq010svResPubDateToYear
     */
    public void setEnq010svResPubDateToYear(int enq010svResPubDateToYear) {
        enq010svResPubDateToYear__ = enq010svResPubDateToYear;
    }

    /**
     * <p>enq010svResPubDateToMonth を取得します。
     * @return enq010svResPubDateToMonth
     */
    public int getEnq010svResPubDateToMonth() {
        return enq010svResPubDateToMonth__;
    }

    /**
     * <p>enq010svResPubDateToMonth をセットします。
     * @param enq010svResPubDateToMonth enq010svResPubDateToMonth
     */
    public void setEnq010svResPubDateToMonth(int enq010svResPubDateToMonth) {
        enq010svResPubDateToMonth__ = enq010svResPubDateToMonth;
    }

    /**
     * <p>enq010svResPubDateToDay を取得します。
     * @return enq010svResPubDateToDay
     */
    public int getEnq010svResPubDateToDay() {
        return enq010svResPubDateToDay__;
    }

    /**
     * <p>enq010svResPubDateToDay をセットします。
     * @param enq010svResPubDateToDay enq010svResPubDateToDay
     */
    public void setEnq010svResPubDateToDay(int enq010svResPubDateToDay) {
        enq010svResPubDateToDay__ = enq010svResPubDateToDay;
    }
    
    /**
     * <p>enq010svResPubDateFrom を取得します。
     * @return enq010svResPubDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svResPubDateFrom__
     */
    public String getEnq010svResPubDateFrom() {
        return enq010svResPubDateFrom__;
    }

    /**
     * <p>enq010svResPubDateFrom をセットします。
     * @param enq010svResPubDateFrom enq010svResPubDateFrom
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svResPubDateFrom__
     */
    public void setEnq010svResPubDateFrom(String enq010svResPubDateFrom) {
        enq010svResPubDateFrom__ = enq010svResPubDateFrom;
    }

    /**
     * <p>enq010svResPubDateTo を取得します。
     * @return enq010svResPubDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svResPubDateTo__
     */
    public String getEnq010svResPubDateTo() {
        return enq010svResPubDateTo__;
    }

    /**
     * <p>enq010svResPubDateTo をセットします。
     * @param enq010svResPubDateTo enq010svResPubDateTo
     * @see jp.groupsession.v2.enq.enq010.Enq010Form#enq010svResPubDateTo__
     */
    public void setEnq010svResPubDateTo(String enq010svResPubDateTo) {
        enq010svResPubDateTo__ = enq010svResPubDateTo;
    }
    
    /**
     * <p>enq010statusAnsOver を取得します。
     * @return enq010statusAnsOver
     */
    public int[] getEnq010statusAnsOver() {
        return enq010statusAnsOver__;
    }

    /**
     * <p>enq010statusAnsOver をセットします。
     * @param enq010statusAnsOver enq010statusAnsOver
     */
    public void setEnq010statusAnsOver(int[] enq010statusAnsOver) {
        enq010statusAnsOver__ = enq010statusAnsOver;
    }

    /**
     * <p>enq010statusAnsOverSimple を取得します。
     * @return enq010statusAnsOverSimple
     */
    public String getEnq010statusAnsOverSimple() {
        return enq010statusAnsOverSimple__;
    }

    /**
     * <p>enq010statusAnsOverSimple をセットします。
     * @param enq010statusAnsOverSimple enq010statusAnsOverSimple
     */
    public void setEnq010statusAnsOverSimple(String enq010statusAnsOverSimple) {
        enq010statusAnsOverSimple__ = enq010statusAnsOverSimple;
    }

    /**
     * <p>enq010svStatusAnsOver を取得します。
     * @return enq010svStatusAnsOver
     */
    public int[] getEnq010svStatusAnsOver() {
        return enq010svStatusAnsOver__;
    }

    /**
     * <p>enq010svStatusAnsOver をセットします。
     * @param enq010svStatusAnsOver enq010svStatusAnsOver
     */
    public void setEnq010svStatusAnsOver(int[] enq010svStatusAnsOver) {
        enq010svStatusAnsOver__ = enq010svStatusAnsOver;
    }

    /**
     * <p>enq010svStatusAnsOverSimple を取得します。
     * @return enq010svStatusAnsOverSimple
     */
    public String getEnq010svStatusAnsOverSimple() {
        return enq010svStatusAnsOverSimple__;
    }

    /**
     * <p>enq010svStatusAnsOverSimple をセットします。
     * @param enq010svStatusAnsOverSimple enq010svStatusAnsOverSimple
     */
    public void setEnq010svStatusAnsOverSimple(String enq010svStatusAnsOverSimple) {
        enq010svStatusAnsOverSimple__ = enq010svStatusAnsOverSimple;
    }
}
