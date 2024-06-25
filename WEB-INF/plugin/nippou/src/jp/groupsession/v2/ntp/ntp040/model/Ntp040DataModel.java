package jp.groupsession.v2.ntp.ntp040.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.ntp.ntp040.Ntp040Param;

/**
 * <br>[機  能] 日報データを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040DataModel extends Ntp040Param {
    /** 日報SID  */
    private int ntp040NtpSid__;
    /** 日報日付  */
    private String ntp040NtpDate__ = null;
    /** ユーザ名  */
    private String ntp040NtpUsrName__ = null;
    /** 登録者 Sid  */
    private int ntp040NtpUsiId__;
    /** 登録者 性  */
    private String ntp040NtpUsiSei__ = null;
    /** 登録者 名  */
    private String ntp040NtpUsiMei__ = null;
    /** 登録者 姓 名  */
    private String ntp040NtpAddUsrName__ = null;
    /** 登録者 区分  */
    private int ntp040AddUsrJkbn__;
    /** 登録者 ログイン停止フラグ  */
    private int ntp040AddUsrUkoFlg__;
    /** 添付ファイルリスト */
    private ArrayList<CmnBinfModel> ntp040FileList__ = null;
    /** コメントリスト */
    private ArrayList<Ntp040CommentModel> ntp040CommentList__ = null;

    /** 開始時  */
    private String ntp040DspFrHour__ = null;
    /** 開始分  */
    private String ntp040DspFrMinute__ = null;
    /** 終了時  */
    private String ntp040DspToHour__ = null;
    /** 終了分  */
    private String ntp040DspToMinute__ = null;
    /** 内容(表示用)  */
    private String ntp040DspValueStr__ = null;
    /** 次のアクション 日付  */
    private String ntp040NtpActionDate__ = null;
    /** 次のアクション(表示用)  */
    private String ntp040DspActionStr__ = null;
    /** 活動分類(表示用)  */
    private String ntp040DspKtbunrui__ = null;
    /** 活動方法(表示用)  */
    private String ntp040DspKthouhou__ = null;
    /** 見込み度(表示用)  */
    private String ntp040DspMikomido__ = null;

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
    private int ntp040SelectFlg__ = 0;

    /** いいね数  */
    private int ntp040GoodCnt__ = 0;
    /** いいねフラグ 0:いいねしていない  1:いいねしている  */
    private int ntp040GoodFlg__ = 0;
    /** 案件非公開フラグ*/
    private boolean ankenViewable__ = true;

    /**
     * <p>ntp040NtpSid を取得します。
     * @return ntp040NtpSid
     */
    public int getNtp040NtpSid() {
        return ntp040NtpSid__;
    }
    /**
     * <p>ntp040NtpSid をセットします。
     * @param ntp040NtpSid ntp040NtpSid
     */
    public void setNtp040NtpSid(int ntp040NtpSid) {
        ntp040NtpSid__ = ntp040NtpSid;
    }
    /**
     * <p>ntp040FileList を取得します。
     * @return ntp040FileList
     */
    public ArrayList<CmnBinfModel> getNtp040FileList() {
        return ntp040FileList__;
    }
    /**
     * <p>ntp040FileList をセットします。
     * @param ntp040FileList ntp040FileList
     */
    public void setNtp040FileList(ArrayList<CmnBinfModel> ntp040FileList) {
        ntp040FileList__ = ntp040FileList;
    }
    /**
     * <p>ntp040NtpUsiSei を取得します。
     * @return ntp040NtpUsiSei
     */
    public String getNtp040NtpUsiSei() {
        return ntp040NtpUsiSei__;
    }
    /**
     * <p>ntp040NtpUsiSei をセットします。
     * @param ntp040NtpUsiSei ntp040NtpUsiSei
     */
    public void setNtp040NtpUsiSei(String ntp040NtpUsiSei) {
        ntp040NtpUsiSei__ = ntp040NtpUsiSei;
    }
    /**
     * <p>ntp040NtpUsiMei を取得します。
     * @return ntp040NtpUsiMei
     */
    public String getNtp040NtpUsiMei() {
        return ntp040NtpUsiMei__;
    }
    /**
     * <p>ntp040NtpUsiMei をセットします。
     * @param ntp040NtpUsiMei ntp040NtpUsiMei
     */
    public void setNtp040NtpUsiMei(String ntp040NtpUsiMei) {
        ntp040NtpUsiMei__ = ntp040NtpUsiMei;
    }
    /**
     * <p>ntp040NtpUsiId を取得します。
     * @return ntp040NtpUsiId
     */
    public int getNtp040NtpUsiId() {
        return ntp040NtpUsiId__;
    }
    /**
     * <p>ntp040NtpUsiId をセットします。
     * @param ntp040NtpUsiId ntp040NtpUsiId
     */
    public void setNtp040NtpUsiId(int ntp040NtpUsiId) {
        ntp040NtpUsiId__ = ntp040NtpUsiId;
    }
    /**
     * <p>ntp040NtpAddUsrName を取得します。
     * @return ntp040NtpAddUsrName
     */
    public String getNtp040NtpAddUsrName() {
        return ntp040NtpAddUsrName__;
    }
    /**
     * <p>ntp040NtpAddUsrName をセットします。
     * @param ntp040NtpAddUsrName ntp040NtpAddUsrName
     */
    public void setNtp040NtpAddUsrName(String ntp040NtpAddUsrName) {
        ntp040NtpAddUsrName__ = ntp040NtpAddUsrName;
    }
    /**
     * <p>ntp040NtpUsrName を取得します。
     * @return ntp040NtpUsrName
     */
    public String getNtp040NtpUsrName() {
        return ntp040NtpUsrName__;
    }
    /**
     * <p>ntp040NtpUsrName をセットします。
     * @param ntp040NtpUsrName ntp040NtpUsrName
     */
    public void setNtp040NtpUsrName(String ntp040NtpUsrName) {
        ntp040NtpUsrName__ = ntp040NtpUsrName;
    }
    /**
     * <p>ntp040AddUsrJkbn を取得します。
     * @return ntp040AddUsrJkbn
     */
    public int getNtp040AddUsrJkbn() {
        return ntp040AddUsrJkbn__;
    }
    /**
     * <p>ntp040AddUsrJkbn をセットします。
     * @param ntp040AddUsrJkbn ntp040AddUsrJkbn
     */
    public void setNtp040AddUsrJkbn(int ntp040AddUsrJkbn) {
        ntp040AddUsrJkbn__ = ntp040AddUsrJkbn;
    }
    /**
     * <p>ntp040NtpDate を取得します。
     * @return ntp040NtpDate
     */
    public String getNtp040NtpDate() {
        return ntp040NtpDate__;
    }
    /**
     * <p>ntp040NtpDate をセットします。
     * @param ntp040NtpDate ntp040NtpDate
     */
    public void setNtp040NtpDate(String ntp040NtpDate) {
        ntp040NtpDate__ = ntp040NtpDate;
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
     * <p>ntp040SelectFlg を取得します。
     * @return ntp040SelectFlg
     */
    public int getNtp040SelectFlg() {
        return ntp040SelectFlg__;
    }
    /**
     * <p>ntp040SelectFlg をセットします。
     * @param ntp040SelectFlg ntp040SelectFlg
     */
    public void setNtp040SelectFlg(int ntp040SelectFlg) {
        ntp040SelectFlg__ = ntp040SelectFlg;
    }
    /**
     * <p>ntp040DspFrHour を取得します。
     * @return ntp040DspFrHour
     */
    public String getNtp040DspFrHour() {
        return ntp040DspFrHour__;
    }
    /**
     * <p>ntp040DspFrHour をセットします。
     * @param ntp040DspFrHour ntp040DspFrHour
     */
    public void setNtp040DspFrHour(String ntp040DspFrHour) {
        ntp040DspFrHour__ = ntp040DspFrHour;
    }
    /**
     * <p>ntp040DspFrMinute を取得します。
     * @return ntp040DspFrMinute
     */
    public String getNtp040DspFrMinute() {
        return ntp040DspFrMinute__;
    }
    /**
     * <p>ntp040DspFrMinute をセットします。
     * @param ntp040DspFrMinute ntp040DspFrMinute
     */
    public void setNtp040DspFrMinute(String ntp040DspFrMinute) {
        ntp040DspFrMinute__ = ntp040DspFrMinute;
    }
    /**
     * <p>ntp040DspValueStr を取得します。
     * @return ntp040DspValueStr
     */
    public String getNtp040DspValueStr() {
        return ntp040DspValueStr__;
    }
    /**
     * <p>ntp040DspValueStr をセットします。
     * @param ntp040DspValueStr ntp040DspValueStr
     */
    public void setNtp040DspValueStr(String ntp040DspValueStr) {
        ntp040DspValueStr__ = ntp040DspValueStr;
    }
    /**
     * <p>ntp040DspKtbunrui を取得します。
     * @return ntp040DspKtbunrui
     */
    public String getNtp040DspKtbunrui() {
        return ntp040DspKtbunrui__;
    }
    /**
     * <p>ntp040DspKtbunrui をセットします。
     * @param ntp040DspKtbunrui ntp040DspKtbunrui
     */
    public void setNtp040DspKtbunrui(String ntp040DspKtbunrui) {
        ntp040DspKtbunrui__ = ntp040DspKtbunrui;
    }
    /**
     * <p>ntp040DspKthouhou を取得します。
     * @return ntp040DspKthouhou
     */
    public String getNtp040DspKthouhou() {
        return ntp040DspKthouhou__;
    }
    /**
     * <p>ntp040DspKthouhou をセットします。
     * @param ntp040DspKthouhou ntp040DspKthouhou
     */
    public void setNtp040DspKthouhou(String ntp040DspKthouhou) {
        ntp040DspKthouhou__ = ntp040DspKthouhou;
    }
    /**
     * <p>ntp040DspMikomido を取得します。
     * @return ntp040DspMikomido
     */
    public String getNtp040DspMikomido() {
        return ntp040DspMikomido__;
    }
    /**
     * <p>ntp040DspMikomido をセットします。
     * @param ntp040DspMikomido ntp040DspMikomido
     */
    public void setNtp040DspMikomido(String ntp040DspMikomido) {
        ntp040DspMikomido__ = ntp040DspMikomido;
    }
    /**
     * <p>ntp040DspToHour を取得します。
     * @return ntp040DspToHour
     */
    public String getNtp040DspToHour() {
        return ntp040DspToHour__;
    }
    /**
     * <p>ntp040DspToHour をセットします。
     * @param ntp040DspToHour ntp040DspToHour
     */
    public void setNtp040DspToHour(String ntp040DspToHour) {
        ntp040DspToHour__ = ntp040DspToHour;
    }
    /**
     * <p>ntp040DspToMinute を取得します。
     * @return ntp040DspToMinute
     */
    public String getNtp040DspToMinute() {
        return ntp040DspToMinute__;
    }
    /**
     * <p>ntp040DspToMinute をセットします。
     * @param ntp040DspToMinute ntp040DspToMinute
     */
    public void setNtp040DspToMinute(String ntp040DspToMinute) {
        ntp040DspToMinute__ = ntp040DspToMinute;
    }
    /**
     * <p>ntp040CommentList を取得します。
     * @return ntp040CommentList
     */
    public ArrayList<Ntp040CommentModel> getNtp040CommentList() {
        return ntp040CommentList__;
    }
    /**
     * <p>ntp040CommentList をセットします。
     * @param ntp040CommentList ntp040CommentList
     */
    public void setNtp040CommentList(ArrayList<Ntp040CommentModel> ntp040CommentList) {
        ntp040CommentList__ = ntp040CommentList;
    }
    /**
     * <p>ntp040GoodCnt を取得します。
     * @return ntp040GoodCnt
     */
    public int getNtp040GoodCnt() {
        return ntp040GoodCnt__;
    }
    /**
     * <p>ntp040GoodCnt をセットします。
     * @param ntp040GoodCnt ntp040GoodCnt
     */
    public void setNtp040GoodCnt(int ntp040GoodCnt) {
        ntp040GoodCnt__ = ntp040GoodCnt;
    }
    /**
     * <p>ntp040GoodFlg を取得します。
     * @return ntp040GoodFlg
     */
    public int getNtp040GoodFlg() {
        return ntp040GoodFlg__;
    }
    /**
     * <p>ntp040GoodFlg をセットします。
     * @param ntp040GoodFlg ntp040GoodFlg
     */
    public void setNtp040GoodFlg(int ntp040GoodFlg) {
        ntp040GoodFlg__ = ntp040GoodFlg;
    }
    /**
     * <p>ntp040NtpActionDate を取得します。
     * @return ntp040NtpActionDate
     */
    public String getNtp040NtpActionDate() {
        return ntp040NtpActionDate__;
    }
    /**
     * <p>ntp040NtpActionDate をセットします。
     * @param ntp040NtpActionDate ntp040NtpActionDate
     */
    public void setNtp040NtpActionDate(String ntp040NtpActionDate) {
        ntp040NtpActionDate__ = ntp040NtpActionDate;
    }
    /**
     * <p>ntp040DspActionStr を取得します。
     * @return ntp040DspActionStr
     */
    public String getNtp040DspActionStr() {
        return ntp040DspActionStr__;
    }
    /**
     * <p>ntp040DspActionStr をセットします。
     * @param ntp040DspActionStr ntp040DspActionStr
     */
    public void setNtp040DspActionStr(String ntp040DspActionStr) {
        ntp040DspActionStr__ = ntp040DspActionStr;
    }
    /**
     * <p>ntp040AddUsrUkoFlg を取得します。
     * @return ntp040AddUsrUkoFlg
     */
    public int getNtp040AddUsrUkoFlg() {
        return ntp040AddUsrUkoFlg__;
    }
    /**
     * <p>ntp040AddUsrUkoFlg をセットします。
     * @param ntp040AddUsrUkoFlg ntp040AddUsrUkoFlg
     */
    public void setNtp040AddUsrUkoFlg(int ntp040AddUsrUkoFlg) {
        ntp040AddUsrUkoFlg__ = ntp040AddUsrUkoFlg;
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
