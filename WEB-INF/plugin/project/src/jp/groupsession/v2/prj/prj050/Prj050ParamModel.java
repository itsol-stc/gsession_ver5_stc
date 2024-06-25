package jp.groupsession.v2.prj.prj050;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.prj060.Prj060ParamModel;

/**
 * <br>[機  能] TODO登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj050ParamModel extends Prj060ParamModel {

    /** マイプロジェクト区分 */
    private int prj050prjMyKbn__;
    /** カテゴリ */
    private int prj050cate__ = GSConstCommon.NUM_INIT;
    /** タイトル */
    private String prj050title__;
    /** タイトル(簡易入力) */
    private String prj050titleEasy__;
    /** 担当者(担当者) */
    private String[] prj050tanto__;
    /** メンバー(担当者) */
    private String[] prj050member__;
    /** 担当者 */
    private String[] prj050hdnTanto__;
    /** 開始予定年 */
    private String prj050kaisiYoteiYear__;
    /** 開始予定月 */
    private String prj050kaisiYoteiMonth__;
    /** 開始予定日 */
    private String prj050kaisiYoteiDay__;
    /** 期限年 */
    private String prj050kigenYear__;
    /** 期限月 */
    private String prj050kigenMonth__;
    /** 期限日 */
    private String prj050kigenDay__;
    /** 予定工数 */
    private String prj050yoteiKosu__;
    /** 開始実績年 */
    private String prj050kaisiJissekiYear__;
    /** 開始実績月 */
    private String prj050kaisiJissekiMonth__;
    /** 開始実績日 */
    private String prj050kaisiJissekiDay__;
    /** 終了実績年 */
    private String prj050syuryoJissekiYear__;
    /** 終了実績月 */
    private String prj050syuryoJissekiMonth__;
    /** 終了実績日 */
    private String prj050syuryoJissekiDay__;
    /** 実績工数 */
    private String prj050jissekiKosu__;
    /** 警告開始 */
    private int prj050keikoku__;
    /** 重要度 */
    private int prj050juyou__ = GSConstProject.WEIGHT_KBN_MIDDLE;
    /** 重要度(簡易入力) */
    private int prj050juyouEasy__ = GSConstProject.WEIGHT_KBN_MIDDLE;
    /** 状態 */
    private int prj050status__ = GSConstProject.STATUS_0;
    /** 状態(簡易入力) */
    private int prj050statusEasy__ = GSConstProject.STATUS_0;
    /** 状態変更時コメント */
    private String prj050statusCmt__;
    /** 状態変更時コメント(簡易入力) */
    private String prj050statusCmtEasy__;
    /** 内容 */
    private String prj050naiyo__;
    /** 内容(簡易入力) */
    private String prj050naiyoEasy__;
    /** 添付 */
    private String[] prj050tenpu__;
    /** プロジェクト編集権限 */
    private int prj050Edit__;

    /** 予定 開始年月日 */
    private String prj050strPlanDate__;
    /** 予定 終了年月日 */
    private String prj050endPlanDate__;
    /** 実績 開始年月日 */
    private String prj050strResultDate__;
    /** 実績 終了年月日 */
    private String prj050endResultDate__;

    //表示項目
    /** ファイルラベル */
    private List<LabelValueBean> fileLabel__;
    /** カテゴリリスト */
    private ArrayList<PrjTodocategoryModel> prj050CategoryList__;
    /** 警告開始ラベル */
    private List<LabelValueBean> keikokuLabel__;

    /** ショートメール通知 */
    private int prj050MailSend__ = -1;
    /** プロジェクト TODOショートメール通知設定 */
    private int prj050smailKbn__;

    /** プロジェクトコンボ 表示項目選択 **/
    private int prj050PrjListKbn__ = GSConstProject.PRJ_KBN_PARTICIPATION;

    /** 入力画面モード */
    private int prj050elementKbn__ = GSConstProject.DSP_TODO_EASY;
    /** 表示画面区分 */
    private int prj050dspKbn__ = GSConstProject.DSP_FIRST;

    /** プロジェクト管理者 UI*/
    private Prj050TantoSelector prj050hdnTantoUI__ = null;
    /** グループSID 担当 */
    private int prj050groupSidTanto__ = Integer.parseInt(GSConstProject.GROUP_COMBO_VALUE_USER);
    /**
     * <p>prj050cate を取得します。
     * @return prj050cate
     */
    public int getPrj050cate() {
        return prj050cate__;
    }
    /**
     * <p>prj050cate をセットします。
     * @param prj050cate prj050cate
     */
    public void setPrj050cate(int prj050cate) {
        prj050cate__ = prj050cate;
    }
    /**
     * <p>prj050title を取得します。
     * @return prj050title
     */
    public String getPrj050title() {
        return prj050title__;
    }
    /**
     * <p>prj050title をセットします。
     * @param prj050title prj050title
     */
    public void setPrj050title(String prj050title) {
        prj050title__ = prj050title;
    }
    /**
     * <p>prj050tanto を取得します。
     * @return prj050tanto
     */
    public String[] getPrj050tanto() {
        return prj050tanto__;
    }
    /**
     * <p>prj050tanto をセットします。
     * @param prj050tanto prj050tanto
     */
    public void setPrj050tanto(String[] prj050tanto) {
        prj050tanto__ = prj050tanto;
    }
    /**
     * <p>prj050member を取得します。
     * @return prj050member
     */
    public String[] getPrj050member() {
        return prj050member__;
    }
    /**
     * <p>prj050member をセットします。
     * @param prj050member prj050member
     */
    public void setPrj050member(String[] prj050member) {
        prj050member__ = prj050member;
    }
    /**
     * <p>prj050hdnTanto を取得します。
     * @return prj050hdnTanto
     */
    public String[] getPrj050hdnTanto() {
        return prj050hdnTanto__;
    }
    /**
     * <p>prj050hdnTanto をセットします。
     * @param prj050hdnTanto prj050hdnTanto
     */
    public void setPrj050hdnTanto(String[] prj050hdnTanto) {
        prj050hdnTanto__ = prj050hdnTanto;
    }
    /**
     * <p>prj050kaisiYoteiYear を取得します。
     * @return prj050kaisiYoteiYear
     */
    public String getPrj050kaisiYoteiYear() {
        return prj050kaisiYoteiYear__;
    }
    /**
     * <p>prj050kaisiYoteiYear をセットします。
     * @param prj050kaisiYoteiYear prj050kaisiYoteiYear
     */
    public void setPrj050kaisiYoteiYear(String prj050kaisiYoteiYear) {
        prj050kaisiYoteiYear__ = prj050kaisiYoteiYear;
    }
    /**
     * <p>prj050kaisiYoteiMonth を取得します。
     * @return prj050kaisiYoteiMonth
     */
    public String getPrj050kaisiYoteiMonth() {
        return prj050kaisiYoteiMonth__;
    }
    /**
     * <p>prj050kaisiYoteiMonth をセットします。
     * @param prj050kaisiYoteiMonth prj050kaisiYoteiMonth
     */
    public void setPrj050kaisiYoteiMonth(String prj050kaisiYoteiMonth) {
        prj050kaisiYoteiMonth__ = prj050kaisiYoteiMonth;
    }
    /**
     * <p>prj050kaisiYoteiDay を取得します。
     * @return prj050kaisiYoteiDay
     */
    public String getPrj050kaisiYoteiDay() {
        return prj050kaisiYoteiDay__;
    }
    /**
     * <p>prj050kaisiYoteiDay をセットします。
     * @param prj050kaisiYoteiDay prj050kaisiYoteiDay
     */
    public void setPrj050kaisiYoteiDay(String prj050kaisiYoteiDay) {
        prj050kaisiYoteiDay__ = prj050kaisiYoteiDay;
    }
    /**
     * <p>prj050kigenYear を取得します。
     * @return prj050kigenYear
     */
    public String getPrj050kigenYear() {
        return prj050kigenYear__;
    }
    /**
     * <p>prj050kigenYear をセットします。
     * @param prj050kigenYear prj050kigenYear
     */
    public void setPrj050kigenYear(String prj050kigenYear) {
        prj050kigenYear__ = prj050kigenYear;
    }
    /**
     * <p>prj050kigenMonth を取得します。
     * @return prj050kigenMonth
     */
    public String getPrj050kigenMonth() {
        return prj050kigenMonth__;
    }
    /**
     * <p>prj050kigenMonth をセットします。
     * @param prj050kigenMonth prj050kigenMonth
     */
    public void setPrj050kigenMonth(String prj050kigenMonth) {
        prj050kigenMonth__ = prj050kigenMonth;
    }
    /**
     * <p>prj050kigenDay を取得します。
     * @return prj050kigenDay
     */
    public String getPrj050kigenDay() {
        return prj050kigenDay__;
    }
    /**
     * <p>prj050kigenDay をセットします。
     * @param prj050kigenDay prj050kigenDay
     */
    public void setPrj050kigenDay(String prj050kigenDay) {
        prj050kigenDay__ = prj050kigenDay;
    }
    /**
     * <p>prj050kaisiJissekiYear を取得します。
     * @return prj050kaisiJissekiYear
     */
    public String getPrj050kaisiJissekiYear() {
        return prj050kaisiJissekiYear__;
    }
    /**
     * <p>prj050kaisiJissekiYear をセットします。
     * @param prj050kaisiJissekiYear prj050kaisiJissekiYear
     */
    public void setPrj050kaisiJissekiYear(String prj050kaisiJissekiYear) {
        prj050kaisiJissekiYear__ = prj050kaisiJissekiYear;
    }
    /**
     * <p>prj050kaisiJissekiMonth を取得します。
     * @return prj050kaisiJissekiMonth
     */
    public String getPrj050kaisiJissekiMonth() {
        return prj050kaisiJissekiMonth__;
    }
    /**
     * <p>prj050kaisiJissekiMonth をセットします。
     * @param prj050kaisiJissekiMonth prj050kaisiJissekiMonth
     */
    public void setPrj050kaisiJissekiMonth(String prj050kaisiJissekiMonth) {
        prj050kaisiJissekiMonth__ = prj050kaisiJissekiMonth;
    }
    /**
     * <p>prj050kaisiJissekiDay を取得します。
     * @return prj050kaisiJissekiDay
     */
    public String getPrj050kaisiJissekiDay() {
        return prj050kaisiJissekiDay__;
    }
    /**
     * <p>prj050kaisiJissekiDay をセットします。
     * @param prj050kaisiJissekiDay prj050kaisiJissekiDay
     */
    public void setPrj050kaisiJissekiDay(String prj050kaisiJissekiDay) {
        prj050kaisiJissekiDay__ = prj050kaisiJissekiDay;
    }
    /**
     * <p>prj050syuryoJissekiYear を取得します。
     * @return prj050syuryoJissekiYear
     */
    public String getPrj050syuryoJissekiYear() {
        return prj050syuryoJissekiYear__;
    }
    /**
     * <p>prj050syuryoJissekiYear をセットします。
     * @param prj050syuryoJissekiYear prj050syuryoJissekiYear
     */
    public void setPrj050syuryoJissekiYear(String prj050syuryoJissekiYear) {
        prj050syuryoJissekiYear__ = prj050syuryoJissekiYear;
    }
    /**
     * <p>prj050syuryoJissekiMonth を取得します。
     * @return prj050syuryoJissekiMonth
     */
    public String getPrj050syuryoJissekiMonth() {
        return prj050syuryoJissekiMonth__;
    }
    /**
     * <p>prj050syuryoJissekiMonth をセットします。
     * @param prj050syuryoJissekiMonth prj050syuryoJissekiMonth
     */
    public void setPrj050syuryoJissekiMonth(String prj050syuryoJissekiMonth) {
        prj050syuryoJissekiMonth__ = prj050syuryoJissekiMonth;
    }
    /**
     * <p>prj050syuryoJissekiDay を取得します。
     * @return prj050syuryoJissekiDay
     */
    public String getPrj050syuryoJissekiDay() {
        return prj050syuryoJissekiDay__;
    }
    /**
     * <p>prj050syuryoJissekiDay をセットします。
     * @param prj050syuryoJissekiDay prj050syuryoJissekiDay
     */
    public void setPrj050syuryoJissekiDay(String prj050syuryoJissekiDay) {
        prj050syuryoJissekiDay__ = prj050syuryoJissekiDay;
    }
    /**
     * <p>prj050keikoku を取得します。
     * @return prj050keikoku
     */
    public int getPrj050keikoku() {
        return prj050keikoku__;
    }
    /**
     * <p>prj050keikoku をセットします。
     * @param prj050keikoku prj050keikoku
     */
    public void setPrj050keikoku(int prj050keikoku) {
        prj050keikoku__ = prj050keikoku;
    }
    /**
     * <p>prj050juyou を取得します。
     * @return prj050juyou
     */
    public int getPrj050juyou() {
        return prj050juyou__;
    }
    /**
     * <p>prj050juyou をセットします。
     * @param prj050juyou prj050juyou
     */
    public void setPrj050juyou(int prj050juyou) {
        prj050juyou__ = prj050juyou;
    }
    /**
     * <p>prj050status を取得します。
     * @return prj050status
     */
    public int getPrj050status() {
        return prj050status__;
    }
    /**
     * <p>prj050status をセットします。
     * @param prj050status prj050status
     */
    public void setPrj050status(int prj050status) {
        prj050status__ = prj050status;
    }
    /**
     * <p>prj050naiyo を取得します。
     * @return prj050naiyo
     */
    public String getPrj050naiyo() {
        return prj050naiyo__;
    }
    /**
     * <p>prj050naiyo をセットします。
     * @param prj050naiyo prj050naiyo
     */
    public void setPrj050naiyo(String prj050naiyo) {
        prj050naiyo__ = prj050naiyo;
    }
    /**
     * <p>prj050tenpu を取得します。
     * @return prj050tenpu
     */
    public String[] getPrj050tenpu() {
        return prj050tenpu__;
    }
    /**
     * <p>prj050tenpu をセットします。
     * @param prj050tenpu prj050tenpu
     */
    public void setPrj050tenpu(String[] prj050tenpu) {
        prj050tenpu__ = prj050tenpu;
    }
    /**
     * <p>fileLabel を取得します。
     * @return fileLabel
     */
    public List<LabelValueBean> getFileLabel() {
        return fileLabel__;
    }
    /**
     * <p>fileLabel をセットします。
     * @param fileLabel fileLabel
     */
    public void setFileLabel(List<LabelValueBean> fileLabel) {
        fileLabel__ = fileLabel;
    }
    /**
     * <p>keikokuLabel を取得します。
     * @return keikokuLabel
     */
    public List<LabelValueBean> getKeikokuLabel() {
        return keikokuLabel__;
    }
    /**
     * <p>keikokuLabel をセットします。
     * @param keikokuLabel keikokuLabel
     */
    public void setKeikokuLabel(List<LabelValueBean> keikokuLabel) {
        keikokuLabel__ = keikokuLabel;
    }

    /**
     * <p>prj050statusCmt を取得します。
     * @return prj050statusCmt
     */
    public String getPrj050statusCmt() {
        return prj050statusCmt__;
    }

    /**
     * <p>prj050statusCmt をセットします。
     * @param prj050statusCmt prj050statusCmt
     */
    public void setPrj050statusCmt(String prj050statusCmt) {
        prj050statusCmt__ = prj050statusCmt;
    }

    /**
     * <p>prj050yoteiKosu を取得します。
     * @return prj050yoteiKosu
     */
    public String getPrj050yoteiKosu() {
        return prj050yoteiKosu__;
    }

    /**
     * <p>prj050yoteiKosu をセットします。
     * @param prj050yoteiKosu prj050yoteiKosu
     */
    public void setPrj050yoteiKosu(String prj050yoteiKosu) {
        prj050yoteiKosu__ = prj050yoteiKosu;
    }

    /**
     * <p>prj050jissekiKosu を取得します。
     * @return prj050jissekiKosu
     */
    public String getPrj050jissekiKosu() {
        return prj050jissekiKosu__;
    }

    /**
     * <p>prj050jissekiKosu をセットします。
     * @param prj050jissekiKosu prj050jissekiKosu
     */
    public void setPrj050jissekiKosu(String prj050jissekiKosu) {
        prj050jissekiKosu__ = prj050jissekiKosu;
    }
    /**
     * <p>prj050CategoryList を取得します。
     * @return prj050CategoryList
     */
    public ArrayList<PrjTodocategoryModel> getPrj050CategoryList() {
        return prj050CategoryList__;
    }
    /**
     * <p>prj050CategoryList をセットします。
     * @param prj050CategoryList prj050CategoryList
     */
    public void setPrj050CategoryList(
            ArrayList<PrjTodocategoryModel> prj050CategoryList) {
        prj050CategoryList__ = prj050CategoryList;
    }
    /**
     * <p>prj050Edit を取得します。
     * @return prj050Edit
     */
    public int getPrj050Edit() {
        return prj050Edit__;
    }
    /**
     * <p>prj050Edit をセットします。
     * @param prj050Edit prj050Edit
     */
    public void setPrj050Edit(int prj050Edit) {
        prj050Edit__ = prj050Edit;
    }
    /**
     * <p>prj050MailSend を取得します。
     * @return prj050MailSend
     */
    public int getPrj050MailSend() {
        return prj050MailSend__;
    }
    /**
     * <p>prj050MailSend をセットします。
     * @param prj050MailSend prj050MailSend
     */
    public void setPrj050MailSend(int prj050MailSend) {
        prj050MailSend__ = prj050MailSend;
    }

    /**
     * <p>prj050prjMyKbn を取得します。
     * @return prj050prjMyKbn
     */
    public int getPrj050prjMyKbn() {
        return prj050prjMyKbn__;
    }

    /**
     * <p>prj050prjMyKbn をセットします。
     * @param prj050prjMyKbn prj050prjMyKbn
     */
    public void setPrj050prjMyKbn(int prj050prjMyKbn) {
        prj050prjMyKbn__ = prj050prjMyKbn;
    }
    /**
     * <p>prj050smailKbn を取得します。
     * @return prj050smailKbn
     */
    public int getPrj050smailKbn() {
        return prj050smailKbn__;
    }
    /**
     * <p>prj050smailKbn をセットします。
     * @param prj050smailKbn prj050smailKbn
     */
    public void setPrj050smailKbn(int prj050smailKbn) {
        prj050smailKbn__ = prj050smailKbn;
    }
    /**
     * <p>prj050PrjListKbn を取得します。
     * @return prj050PrjListKbn
     */
    public int getPrj050PrjListKbn() {
        return prj050PrjListKbn__;
    }
    /**
     * <p>prj050PrjListKbn をセットします。
     * @param prj050PrjListKbn prj050PrjListKbn
     */
    public void setPrj050PrjListKbn(int prj050PrjListKbn) {
        prj050PrjListKbn__ = prj050PrjListKbn;
    }
    /**
     * @return prj050elementKbn
     */
    public int getPrj050elementKbn() {
        return prj050elementKbn__;
    }

    /**
     * @param prj050elementKbn 設定する prj050elementKbn
     */
    public void setPrj050elementKbn(int prj050elementKbn) {
        prj050elementKbn__ = prj050elementKbn;
    }

    /**
     * @return prj050juyouEasy
     */
    public int getPrj050juyouEasy() {
        return prj050juyouEasy__;
    }

    /**
     * @param prj050juyouEasy 設定する prj050juyouEasy
     */
    public void setPrj050juyouEasy(int prj050juyouEasy) {
        prj050juyouEasy__ = prj050juyouEasy;
    }

    /**
     * @return prj050statusEasy
     */
    public int getPrj050statusEasy() {
        return prj050statusEasy__;
    }

    /**
     * @param prj050statusEasy 設定する prj050statusEasy
     */
    public void setPrj050statusEasy(int prj050statusEasy) {
        prj050statusEasy__ = prj050statusEasy;
    }

    /**
     * @return prj050titleEasy
     */
    public String getPrj050titleEasy() {
        return prj050titleEasy__;
    }

    /**
     * @param prj050titleEasy 設定する prj050titleEasy
     */
    public void setPrj050titleEasy(String prj050titleEasy) {
        prj050titleEasy__ = prj050titleEasy;
    }

    /**
     * @return prj050naiyoEasy
     */
    public String getPrj050naiyoEasy() {
        return prj050naiyoEasy__;
    }

    /**
     * @param prj050naiyoEasy 設定する prj050naiyoEasy
     */
    public void setPrj050naiyoEasy(String prj050naiyoEasy) {
        prj050naiyoEasy__ = prj050naiyoEasy;
    }

    /**
     * @return prj050statusCmtEasy
     */
    public String getPrj050statusCmtEasy() {
        return prj050statusCmtEasy__;
    }

    /**
     * @param prj050statusCmtEasy 設定する prj050statusCmtEasy
     */
    public void setPrj050statusCmtEasy(String prj050statusCmtEasy) {
        prj050statusCmtEasy__ = prj050statusCmtEasy;
    }
    /**
     * @return prj050dspKbn
     */
    public int getPrj050dspKbn() {
        return prj050dspKbn__;
    }

    /**
     * @param prj050dspKbn 設定する prj050dspKbn
     */
    public void setPrj050dspKbn(int prj050dspKbn) {
        prj050dspKbn__ = prj050dspKbn;
    }

    /**
     * <p>prj050hdnTantoUI を取得します。
     * @return prj050hdnTantoUI
     * @see jp.groupsession.v2.prj.prj050.Prj050ParamModel#prj050hdnTantoUI__
     */
    public Prj050TantoSelector getPrj050hdnTantoUI() {
        return prj050hdnTantoUI__;
    }
    /**
     * <p>prj050hdnTantoUI をセットします。
     * @param prj050hdnTantoUI prj050hdnTantoUI
     * @see jp.groupsession.v2.prj.prj050.Prj050ParamModel#prj050hdnTantoUI__
     */
    public void setPrj050hdnTantoUI(Prj050TantoSelector prj050hdnTantoUI) {
        prj050hdnTantoUI__ = prj050hdnTantoUI;
    }
    /**
     * <p>prj050groupSidTanto を取得します。
     * @return prj050groupSidTanto
     * @see jp.groupsession.v2.prj.prj050.Prj050ParamModel#prj050groupSidTanto__
     */
    public int getPrj050groupSidTanto() {
        return prj050groupSidTanto__;
    }
    /**
     * <p>prj050groupSidTanto をセットします。
     * @param prj050groupSidTanto prj050groupSidTanto
     * @see jp.groupsession.v2.prj.prj050.Prj050ParamModel#prj050groupSidTanto__
     */
    public void setPrj050groupSidTanto(int prj050groupSidTanto) {
        prj050groupSidTanto__ = prj050groupSidTanto;
    }

    /**
     * <p>prj050strPlanDate を取得します。
     * @return prj050strPlanDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050strPlanDate__
     */
    public String getPrj050strPlanDate() {
        return prj050strPlanDate__;
    }

    /**
     * <p>prj050strPlanDate をセットします。
     * @param prj050strPlanDate prj050strPlanDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050strPlanDate__
     */
    public void setPrj050strPlanDate(String prj050strPlanDate) {
        prj050strPlanDate__ = prj050strPlanDate;
    }

    /**
     * <p>prj050endPlanDate を取得します。
     * @return prj050endPlanDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050endPlanDate__
     */
    public String getPrj050endPlanDate() {
        return prj050endPlanDate__;
    }

    /**
     * <p>prj050endPlanDate をセットします。
     * @param prj050endPlanDate prj050endPlanDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050endPlanDate__
     */
    public void setPrj050endPlanDate(String prj050endPlanDate) {
        prj050endPlanDate__ = prj050endPlanDate;
    }

    /**
     * <p>prj050strResultDate を取得します。
     * @return prj050strResultDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050strResultDate__
     */
    public String getPrj050strResultDate() {
        return prj050strResultDate__;
    }

    /**
     * <p>prj050strResultDate をセットします。
     * @param prj050strResultDate prj050strResultDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050strResultDate__
     */
    public void setPrj050strResultDate(String prj050strResultDate) {
        prj050strResultDate__ = prj050strResultDate;
    }

    /**
     * <p>prj050endResultDate を取得します。
     * @return prj050endResultDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050endResultDate__
     */
    public String getPrj050endResultDate() {
        return prj050endResultDate__;
    }

    /**
     * <p>prj050endResultDate をセットします。
     * @param prj050endResultDate prj050endResultDate
     * @see jp.groupsession.v2.prj.prj050.Prj050Form#prj050endResultDate__
     */
    public void setPrj050endResultDate(String prj050endResultDate) {
        prj050endResultDate__ = prj050endResultDate;
    }
}