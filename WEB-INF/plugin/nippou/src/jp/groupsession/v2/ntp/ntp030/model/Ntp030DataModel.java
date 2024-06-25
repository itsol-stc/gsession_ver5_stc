package jp.groupsession.v2.ntp.ntp030.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.ntp030.Ntp030Param;

/**
 * <br>[機  能] 日報データを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030DataModel extends Ntp030Param {
    /** 日報SID  */
    private int ntp030NtpSid__;
    /** 日報日付  */
    private String ntp030NtpDate__ = null;
    /** ラベル日付(タイムライン表示用)  */
    private String ntp030LabelDate__ = null;
    /** ユーザ名  */
    private String ntp030NtpUsrName__ = null;
    /** ユーザSID  */
    private String ntp030UsrSid__ = null;
    /** ユーザ情報  */
    private CmnUsrmInfModel ntp030UsrInfMdl__ = null;
    /** 登録者 Sid  */
    private int ntp030NtpUsiId__;
    /** 登録者 性  */
    private String ntp030NtpUsiSei__ = null;
    /** 登録者 名  */
    private String ntp030NtpUsiMei__ = null;
    /** 登録者 姓 名  */
    private String ntp030NtpAddUsrName__ = null;
    /** 登録者 区分  */
    private int ntp030AddUsrJkbn__;
    /** 登録者 ログイン停止フラグ  */
    private int ntp030AddUsrUkoFlg__;
    /** 添付ファイルリスト */
    private ArrayList<CmnBinfModel> ntp030FileList__ = null;
    /** コメントリスト */
    private ArrayList<Ntp030CommentModel> ntp030CommentList__ = null;

    /** 開始時  */
    private String ntp030DspFrHour__ = null;
    /** 開始分  */
    private String ntp030DspFrMinute__ = null;
    /** 終了時  */
    private String ntp030DspToHour__ = null;
    /** 終了分  */
    private String ntp030DspToMinute__ = null;
    /** 内容(表示用)  */
    private String ntp030DspValueStr__ = null;
    /** 次のアクション 日付  */
    private String ntp030NtpActionDate__ = null;
    /** 次のアクション(表示用)  */
    private String ntp030DspActionStr__ = null;
    /** 活動分類(表示用)  */
    private String ntp030DspKtbunrui__ = null;
    /** 活動方法(表示用)  */
    private String ntp030DspKthouhou__ = null;
    /** 見込み度(表示用)  */
    private String ntp030DspMikomido__ = null;

    /** 案件コード  */
    private String ankenCode__ = null;
    /** 案件名  */
    private String ankenName__ = null;

    /** 企業コード  */
    private String companyCode__ = null;
    /** 企業名  */
    private String companyName__ = null;
    /** 拠点名  */
    private String companyBaseName__ = null;

    /** 初期選択フラグ  */
    private int ntp030SelectFlg__ = 0;
    /** 編集可能区分 0:編集可 1:編集不可  */
    private int ntp030AuthEditKbn__ = 0;

    /** いいね数  */
    private int ntp030GoodCnt__ = 0;
    /** いいねフラグ 0:いいねしていない  1:いいねしている  */
    private int ntp030GoodFlg__ = 0;

    /** 確認フラグ 0:未確認  1:確認  */
    private int ntp030ChkKbn__ = 0;
    /** 案件非公開フラグ*/
    private boolean ankenViewable__ = true;

    /**
     * <p>ntp030NtpSid を取得します。
     * @return ntp030NtpSid
     */
    public int getNtp030NtpSid() {
        return ntp030NtpSid__;
    }
    /**
     * <p>ntp030NtpSid をセットします。
     * @param ntp030NtpSid ntp030NtpSid
     */
    public void setNtp030NtpSid(int ntp030NtpSid) {
        ntp030NtpSid__ = ntp030NtpSid;
    }
    /**
     * <p>ntp030FileList を取得します。
     * @return ntp030FileList
     */
    public ArrayList<CmnBinfModel> getNtp030FileList() {
        return ntp030FileList__;
    }
    /**
     * <p>ntp030FileList をセットします。
     * @param ntp030FileList ntp030FileList
     */
    public void setNtp030FileList(ArrayList<CmnBinfModel> ntp030FileList) {
        ntp030FileList__ = ntp030FileList;
    }
    /**
     * <p>ntp030NtpUsiSei を取得します。
     * @return ntp030NtpUsiSei
     */
    public String getNtp030NtpUsiSei() {
        return ntp030NtpUsiSei__;
    }
    /**
     * <p>ntp030NtpUsiSei をセットします。
     * @param ntp030NtpUsiSei ntp030NtpUsiSei
     */
    public void setNtp030NtpUsiSei(String ntp030NtpUsiSei) {
        ntp030NtpUsiSei__ = ntp030NtpUsiSei;
    }
    /**
     * <p>ntp030NtpUsiMei を取得します。
     * @return ntp030NtpUsiMei
     */
    public String getNtp030NtpUsiMei() {
        return ntp030NtpUsiMei__;
    }
    /**
     * <p>ntp030NtpUsiMei をセットします。
     * @param ntp030NtpUsiMei ntp030NtpUsiMei
     */
    public void setNtp030NtpUsiMei(String ntp030NtpUsiMei) {
        ntp030NtpUsiMei__ = ntp030NtpUsiMei;
    }
    /**
     * <p>ntp030NtpUsiId を取得します。
     * @return ntp030NtpUsiId
     */
    public int getNtp030NtpUsiId() {
        return ntp030NtpUsiId__;
    }
    /**
     * <p>ntp030NtpUsiId をセットします。
     * @param ntp030NtpUsiId ntp030NtpUsiId
     */
    public void setNtp030NtpUsiId(int ntp030NtpUsiId) {
        ntp030NtpUsiId__ = ntp030NtpUsiId;
    }
    /**
     * <p>ntp030NtpAddUsrName を取得します。
     * @return ntp030NtpAddUsrName
     */
    public String getNtp030NtpAddUsrName() {
        return ntp030NtpAddUsrName__;
    }
    /**
     * <p>ntp030NtpAddUsrName をセットします。
     * @param ntp030NtpAddUsrName ntp030NtpAddUsrName
     */
    public void setNtp030NtpAddUsrName(String ntp030NtpAddUsrName) {
        ntp030NtpAddUsrName__ = ntp030NtpAddUsrName;
    }
    /**
     * <p>ntp030NtpUsrName を取得します。
     * @return ntp030NtpUsrName
     */
    public String getNtp030NtpUsrName() {
        return ntp030NtpUsrName__;
    }
    /**
     * <p>ntp030NtpUsrName をセットします。
     * @param ntp030NtpUsrName ntp030NtpUsrName
     */
    public void setNtp030NtpUsrName(String ntp030NtpUsrName) {
        ntp030NtpUsrName__ = ntp030NtpUsrName;
    }
    /**
     * <p>ntp030AddUsrJkbn を取得します。
     * @return ntp030AddUsrJkbn
     */
    public int getNtp030AddUsrJkbn() {
        return ntp030AddUsrJkbn__;
    }
    /**
     * <p>ntp030AddUsrJkbn をセットします。
     * @param ntp030AddUsrJkbn ntp030AddUsrJkbn
     */
    public void setNtp030AddUsrJkbn(int ntp030AddUsrJkbn) {
        ntp030AddUsrJkbn__ = ntp030AddUsrJkbn;
    }
    /**
     * <p>ntp030NtpDate を取得します。
     * @return ntp030NtpDate
     */
    public String getNtp030NtpDate() {
        return ntp030NtpDate__;
    }
    /**
     * <p>ntp030NtpDate をセットします。
     * @param ntp030NtpDate ntp030NtpDate
     */
    public void setNtp030NtpDate(String ntp030NtpDate) {
        ntp030NtpDate__ = ntp030NtpDate;
    }
    /**
     * <p>ankenCode を取得します。
     * @return ankenCode
     */
    public String getAnkenCode() {
        return ankenCode__;
    }
    /**
     * <p>ankenCode をセットします。
     * @param ankenCode ankenCode
     */
    public void setAnkenCode(String ankenCode) {
        ankenCode__ = ankenCode;
    }
    /**
     * <p>ankenName を取得します。
     * @return ankenName
     */
    public String getAnkenName() {
        return ankenName__;
    }
    /**
     * <p>ankenName をセットします。
     * @param ankenName ankenName
     */
    public void setAnkenName(String ankenName) {
        ankenName__ = ankenName;
    }
    /**
     * <p>companyCode を取得します。
     * @return companyCode
     */
    public String getCompanyCode() {
        return companyCode__;
    }
    /**
     * <p>companyCode をセットします。
     * @param companyCode companyCode
     */
    public void setCompanyCode(String companyCode) {
        companyCode__ = companyCode;
    }
    /**
     * <p>companyName を取得します。
     * @return companyName
     */
    public String getCompanyName() {
        return companyName__;
    }
    /**
     * <p>companyName をセットします。
     * @param companyName companyName
     */
    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }
    /**
     * <p>companyBaseName を取得します。
     * @return companyBaseName
     */
    public String getCompanyBaseName() {
        return companyBaseName__;
    }
    /**
     * <p>companyBaseName をセットします。
     * @param companyBaseName companyBaseName
     */
    public void setCompanyBaseName(String companyBaseName) {
        companyBaseName__ = companyBaseName;
    }
    /**
     * <p>ntp030SelectFlg を取得します。
     * @return ntp030SelectFlg
     */
    public int getNtp030SelectFlg() {
        return ntp030SelectFlg__;
    }
    /**
     * <p>ntp030SelectFlg をセットします。
     * @param ntp030SelectFlg ntp030SelectFlg
     */
    public void setNtp030SelectFlg(int ntp030SelectFlg) {
        ntp030SelectFlg__ = ntp030SelectFlg;
    }
    /**
     * <p>ntp030DspFrHour を取得します。
     * @return ntp030DspFrHour
     */
    public String getNtp030DspFrHour() {
        return ntp030DspFrHour__;
    }
    /**
     * <p>ntp030DspFrHour をセットします。
     * @param ntp030DspFrHour ntp030DspFrHour
     */
    public void setNtp030DspFrHour(String ntp030DspFrHour) {
        ntp030DspFrHour__ = ntp030DspFrHour;
    }
    /**
     * <p>ntp030DspFrMinute を取得します。
     * @return ntp030DspFrMinute
     */
    public String getNtp030DspFrMinute() {
        return ntp030DspFrMinute__;
    }
    /**
     * <p>ntp030DspFrMinute をセットします。
     * @param ntp030DspFrMinute ntp030DspFrMinute
     */
    public void setNtp030DspFrMinute(String ntp030DspFrMinute) {
        ntp030DspFrMinute__ = ntp030DspFrMinute;
    }
    /**
     * <p>ntp030DspValueStr を取得します。
     * @return ntp030DspValueStr
     */
    public String getNtp030DspValueStr() {
        return ntp030DspValueStr__;
    }
    /**
     * <p>ntp030DspValueStr をセットします。
     * @param ntp030DspValueStr ntp030DspValueStr
     */
    public void setNtp030DspValueStr(String ntp030DspValueStr) {
        ntp030DspValueStr__ = ntp030DspValueStr;
    }
    /**
     * <p>ntp030DspKtbunrui を取得します。
     * @return ntp030DspKtbunrui
     */
    public String getNtp030DspKtbunrui() {
        return ntp030DspKtbunrui__;
    }
    /**
     * <p>ntp030DspKtbunrui をセットします。
     * @param ntp030DspKtbunrui ntp030DspKtbunrui
     */
    public void setNtp030DspKtbunrui(String ntp030DspKtbunrui) {
        ntp030DspKtbunrui__ = ntp030DspKtbunrui;
    }
    /**
     * <p>ntp030DspKthouhou を取得します。
     * @return ntp030DspKthouhou
     */
    public String getNtp030DspKthouhou() {
        return ntp030DspKthouhou__;
    }
    /**
     * <p>ntp030DspKthouhou をセットします。
     * @param ntp030DspKthouhou ntp030DspKthouhou
     */
    public void setNtp030DspKthouhou(String ntp030DspKthouhou) {
        ntp030DspKthouhou__ = ntp030DspKthouhou;
    }
    /**
     * <p>ntp030DspMikomido を取得します。
     * @return ntp030DspMikomido
     */
    public String getNtp030DspMikomido() {
        return ntp030DspMikomido__;
    }
    /**
     * <p>ntp030DspMikomido をセットします。
     * @param ntp030DspMikomido ntp030DspMikomido
     */
    public void setNtp030DspMikomido(String ntp030DspMikomido) {
        ntp030DspMikomido__ = ntp030DspMikomido;
    }
    /**
     * <p>ntp030DspToHour を取得します。
     * @return ntp030DspToHour
     */
    public String getNtp030DspToHour() {
        return ntp030DspToHour__;
    }
    /**
     * <p>ntp030DspToHour をセットします。
     * @param ntp030DspToHour ntp030DspToHour
     */
    public void setNtp030DspToHour(String ntp030DspToHour) {
        ntp030DspToHour__ = ntp030DspToHour;
    }
    /**
     * <p>ntp030DspToMinute を取得します。
     * @return ntp030DspToMinute
     */
    public String getNtp030DspToMinute() {
        return ntp030DspToMinute__;
    }
    /**
     * <p>ntp030DspToMinute をセットします。
     * @param ntp030DspToMinute ntp030DspToMinute
     */
    public void setNtp030DspToMinute(String ntp030DspToMinute) {
        ntp030DspToMinute__ = ntp030DspToMinute;
    }
    /**
     * <p>ntp030CommentList を取得します。
     * @return ntp030CommentList
     */
    public ArrayList<Ntp030CommentModel> getNtp030CommentList() {
        return ntp030CommentList__;
    }
    /**
     * <p>ntp030CommentList をセットします。
     * @param ntp030CommentList ntp030CommentList
     */
    public void setNtp030CommentList(ArrayList<Ntp030CommentModel> ntp030CommentList) {
        ntp030CommentList__ = ntp030CommentList;
    }
    /**
     * <p>ntp030UsrSid を取得します。
     * @return ntp030UsrSid
     */
    public String getNtp030UsrSid() {
        return ntp030UsrSid__;
    }
    /**
     * <p>ntp030UsrSid をセットします。
     * @param ntp030UsrSid ntp030UsrSid
     */
    public void setNtp030UsrSid(String ntp030UsrSid) {
        ntp030UsrSid__ = ntp030UsrSid;
    }
    /**
     * <p>ntp030UsrInfMdl を取得します。
     * @return ntp030UsrInfMdl
     */
    public CmnUsrmInfModel getNtp030UsrInfMdl() {
        return ntp030UsrInfMdl__;
    }
    /**
     * <p>ntp030UsrInfMdl をセットします。
     * @param ntp030UsrInfMdl ntp030UsrInfMdl
     */
    public void setNtp030UsrInfMdl(CmnUsrmInfModel ntp030UsrInfMdl) {
        ntp030UsrInfMdl__ = ntp030UsrInfMdl;
    }
    /**
     * <p>ntp030LabelDate を取得します。
     * @return ntp030LabelDate
     */
    public String getNtp030LabelDate() {
        return ntp030LabelDate__;
    }
    /**
     * <p>ntp030LabelDate をセットします。
     * @param ntp030LabelDate ntp030LabelDate
     */
    public void setNtp030LabelDate(String ntp030LabelDate) {
        ntp030LabelDate__ = ntp030LabelDate;
    }
    /**
     * <p>ntp030AuthEditKbn を取得します。
     * @return ntp030AuthEditKbn
     */
    public int getNtp030AuthEditKbn() {
        return ntp030AuthEditKbn__;
    }
    /**
     * <p>ntp030AuthEditKbn をセットします。
     * @param ntp030AuthEditKbn ntp030AuthEditKbn
     */
    public void setNtp030AuthEditKbn(int ntp030AuthEditKbn) {
        ntp030AuthEditKbn__ = ntp030AuthEditKbn;
    }
    /**
     * <p>ntp030GoodCnt を取得します。
     * @return ntp030GoodCnt
     */
    public int getNtp030GoodCnt() {
        return ntp030GoodCnt__;
    }
    /**
     * <p>ntp030GoodCnt をセットします。
     * @param ntp030GoodCnt ntp030GoodCnt
     */
    public void setNtp030GoodCnt(int ntp030GoodCnt) {
        ntp030GoodCnt__ = ntp030GoodCnt;
    }
    /**
     * <p>ntp030GoodFlg を取得します。
     * @return ntp030GoodFlg
     */
    public int getNtp030GoodFlg() {
        return ntp030GoodFlg__;
    }
    /**
     * <p>ntp030GoodFlg をセットします。
     * @param ntp030GoodFlg ntp030GoodFlg
     */
    public void setNtp030GoodFlg(int ntp030GoodFlg) {
        ntp030GoodFlg__ = ntp030GoodFlg;
    }
    /**
     * <p>ntp030DspActionStr を取得します。
     * @return ntp030DspActionStr
     */
    public String getNtp030DspActionStr() {
        return ntp030DspActionStr__;
    }
    /**
     * <p>ntp030DspActionStr をセットします。
     * @param ntp030DspActionStr ntp030DspActionStr
     */
    public void setNtp030DspActionStr(String ntp030DspActionStr) {
        ntp030DspActionStr__ = ntp030DspActionStr;
    }
    /**
     * <p>ntp030NtpActionDate を取得します。
     * @return ntp030NtpActionDate
     */
    public String getNtp030NtpActionDate() {
        return ntp030NtpActionDate__;
    }
    /**
     * <p>ntp030NtpActionDate をセットします。
     * @param ntp030NtpActionDate ntp030NtpActionDate
     */
    public void setNtp030NtpActionDate(String ntp030NtpActionDate) {
        ntp030NtpActionDate__ = ntp030NtpActionDate;
    }
    /**
     * <p>ntp030ChkKbn を取得します。
     * @return ntp030ChkKbn
     */
    public int getNtp030ChkKbn() {
        return ntp030ChkKbn__;
    }
    /**
     * <p>ntp030ChkKbn をセットします。
     * @param ntp030ChkKbn ntp030ChkKbn
     */
    public void setNtp030ChkKbn(int ntp030ChkKbn) {
        ntp030ChkKbn__ = ntp030ChkKbn;
    }
    /**
     * <p>ntp030AddUsrUkoFlg を取得します。
     * @return ntp030AddUsrUkoFlg
     */
    public int getNtp030AddUsrUkoFlg() {
        return ntp030AddUsrUkoFlg__;
    }
    /**
     * <p>ntp030AddUsrUkoFlg をセットします。
     * @param ntp030AddUsrUkoFlg ntp030AddUsrUkoFlg
     */
    public void setNtp030AddUsrUkoFlg(int ntp030AddUsrUkoFlg) {
        ntp030AddUsrUkoFlg__ = ntp030AddUsrUkoFlg;
    }
    /**
     * <p>ankenViewable を取得します。
     * @return ankenViewable
     */
    public boolean isAnkenViewable() {
        return ankenViewable__;
    }
    /**
     * <p>ankenViewable をセットします。
     * @param ankenViewable ankenViewable
     */
    public void setAnkenViewable(boolean ankenViewable) {
        ankenViewable__ = ankenViewable;
    }
}
