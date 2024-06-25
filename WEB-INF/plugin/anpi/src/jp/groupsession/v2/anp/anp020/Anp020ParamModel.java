package jp.groupsession.v2.anp.anp020;

import jp.groupsession.v2.anp.anp010.Anp010ParamModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 安否状況入力画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp020ParamModel extends Anp010ParamModel {

    /** 起動モード */
    private String rmode__;
    /** 社員/職員番号 */
    private String anp020EmployeeNo__;
    /** 氏名 */
    private String anp020Name__;
    /** 氏名(カナ) */
    private String anp020Kana__;
    /** 配信日時 */
    private String anp020HaisinDate__;
    /** 返信日時 */
    private String anp020ReplyDate__;
    /** 安否状況 */
    private String anp020JokyoFlg__;
    /** 現在地 */
    private String anp020PlaceFlg__;
    /** 出社状況 */
    private String anp020SyusyaFlg__;
    /** コメント */
    private String anp020Comment__;

    /** 緊急連絡先・表示フラグ */
    private String anp020UrgentDspFlg__;
    /** 緊急連絡先・メールアドレス */
    private String anp020UrgentMail__;
    /** 緊急連絡先・電話番号 */
    private String anp020UrgentTelNo__;

    /** 写真 ファイルSid  */
    private long anp020PhotoFileSid__ = 0;
    /** 写真 ファイル有無 */
    private int anp020PhotoDspFlg__ = GSConstUser.INDIVIDUAL_INFO_CLOSE;
    /** ユーザ状態区分 */
    private int anp020usrJkbn__;
    /** ユーザログイン停止フラグ */
    private int anp020usrUkoFlg__ = 0;

    /**
     * <p>rmode を取得します。
     * @return rmode
     */
    public String getRmode() {
        return rmode__;
    }
    /**
     * <p>rmode をセットします。
     * @param rmode rmode
     */
    public void setRmode(String rmode) {
        rmode__ = rmode;
    }
    /**
     * <p>anp020EmployeeNo を取得します。
     * @return anp020EmployeeNo
     */
    public String getAnp020EmployeeNo() {
        return anp020EmployeeNo__;
    }
    /**
     * <p>anp020EmployeeNo をセットします。
     * @param anp020EmployeeNo anp020EmployeeNo
     */
    public void setAnp020EmployeeNo(String anp020EmployeeNo) {
        anp020EmployeeNo__ = anp020EmployeeNo;
    }
    /**
     * <p>anp020Name を取得します。
     * @return anp020Name
     */
    public String getAnp020Name() {
        return anp020Name__;
    }
    /**
     * <p>anp020Name をセットします。
     * @param anp020Name anp020Name
     */
    public void setAnp020Name(String anp020Name) {
        anp020Name__ = anp020Name;
    }
    /**
     * <p>anp020Kana を取得します。
     * @return anp020Kana
     */
    public String getAnp020Kana() {
        return anp020Kana__;
    }
    /**
     * <p>anp020Kana をセットします。
     * @param anp020Kana anp020Kana
     */
    public void setAnp020Kana(String anp020Kana) {
        anp020Kana__ = anp020Kana;
    }
    /**
     * <p>anp020HaisinDate を取得します。
     * @return anp020HaisinDate
     */
    public String getAnp020HaisinDate() {
        return anp020HaisinDate__;
    }
    /**
     * <p>anp020HaisinDate をセットします。
     * @param anp020HaisinDate anp020HaisinDate
     */
    public void setAnp020HaisinDate(String anp020HaisinDate) {
        anp020HaisinDate__ = anp020HaisinDate;
    }
    /**
     * <p>anp020ReplyDate を取得します。
     * @return anp020ReplyDate
     */
    public String getAnp020ReplyDate() {
        return anp020ReplyDate__;
    }
    /**
     * <p>anp020ReplyDate をセットします。
     * @param anp020ReplyDate anp020ReplyDate
     */
    public void setAnp020ReplyDate(String anp020ReplyDate) {
        anp020ReplyDate__ = anp020ReplyDate;
    }
    /**
     * <p>anp020JokyoFlg を取得します。
     * @return anp020JokyoFlg
     */
    public String getAnp020JokyoFlg() {
        return anp020JokyoFlg__;
    }
    /**
     * <p>anp020JokyoFlg をセットします。
     * @param anp020JokyoFlg anp020JokyoFlg
     */
    public void setAnp020JokyoFlg(String anp020JokyoFlg) {
        anp020JokyoFlg__ = anp020JokyoFlg;
    }
    /**
     * <p>anp020PlaceFlg を取得します。
     * @return anp020PlaceFlg
     */
    public String getAnp020PlaceFlg() {
        return anp020PlaceFlg__;
    }
    /**
     * <p>anp020PlaceFlg をセットします。
     * @param anp020PlaceFlg anp020PlaceFlg
     */
    public void setAnp020PlaceFlg(String anp020PlaceFlg) {
        anp020PlaceFlg__ = anp020PlaceFlg;
    }
    /**
     * <p>anp020SyusyaFlg を取得します。
     * @return anp020SyusyaFlg
     */
    public String getAnp020SyusyaFlg() {
        return anp020SyusyaFlg__;
    }
    /**
     * <p>anp020SyusyaFlg をセットします。
     * @param anp020SyusyaFlg anp020SyusyaFlg
     */
    public void setAnp020SyusyaFlg(String anp020SyusyaFlg) {
        anp020SyusyaFlg__ = anp020SyusyaFlg;
    }
    /**
     * <p>anp020Comment を取得します。
     * @return anp020Comment
     */
    public String getAnp020Comment() {
        return anp020Comment__;
    }
    /**
     * <p>anp020Comment をセットします。
     * @param anp020Comment anp020Comment
     */
    public void setAnp020Comment(String anp020Comment) {
        anp020Comment__ = anp020Comment;
    }
    /**
     * <p>anp020UrgentDspFlg を取得します。
     * @return anp020UrgentDspFlg
     */
    public String getAnp020UrgentDspFlg() {
        return anp020UrgentDspFlg__;
    }
    /**
     * <p>anp020UrgentDspFlg をセットします。
     * @param anp020UrgentDspFlg anp020UrgentDspFlg
     */
    public void setAnp020UrgentDspFlg(String anp020UrgentDspFlg) {
        anp020UrgentDspFlg__ = anp020UrgentDspFlg;
    }
    /**
     * <p>anp020UrgentMail を取得します。
     * @return anp020UrgentMail
     */
    public String getAnp020UrgentMail() {
        return anp020UrgentMail__;
    }
    /**
     * <p>anp020UrgentMail をセットします。
     * @param anp020UrgentMail anp020UrgentMail
     */
    public void setAnp020UrgentMail(String anp020UrgentMail) {
        anp020UrgentMail__ = anp020UrgentMail;
    }
    /**
     * <p>anp020UrgentTelNo を取得します。
     * @return anp020UrgentTelNo
     */
    public String getAnp020UrgentTelNo() {
        return anp020UrgentTelNo__;
    }
    /**
     * <p>anp020UrgentTelNo をセットします。
     * @param anp020UrgentTelNo anp020UrgentTelNo
     */
    public void setAnp020UrgentTelNo(String anp020UrgentTelNo) {
        anp020UrgentTelNo__ = anp020UrgentTelNo;
    }
    /**
     * <p>anp020PhotoFileSid を取得します。
     * @return anp020PhotoFileSid
     */
    public long getAnp020PhotoFileSid() {
        return anp020PhotoFileSid__;
    }
    /**
     * <p>anp020PhotoFileSid をセットします。
     * @param anp020PhotoFileSid anp020PhotoFileSid
     */
    public void setAnp020PhotoFileSid(long anp020PhotoFileSid) {
        anp020PhotoFileSid__ = anp020PhotoFileSid;
    }
    /**
     * <p>anp020PhotoDspFlg を取得します。
     * @return anp020PhotoDspFlg
     */
    public int getAnp020PhotoDspFlg() {
        return anp020PhotoDspFlg__;
    }
    /**
     * <p>anp020PhotoDspFlg をセットします。
     * @param anp020PhotoDspFlg anp020PhotoDspFlg
     */
    public void setAnp020PhotoDspFlg(int anp020PhotoDspFlg) {
        anp020PhotoDspFlg__ = anp020PhotoDspFlg;
    }
    /**
     * <p>anp020usrJkbn を取得します。
     * @return anp020usrJkbn
     */
    public int getAnp020usrJkbn() {
        return anp020usrJkbn__;
    }
    /**
     * <p>anp020usrJkbn をセットします。
     * @param anp020usrJkbn anp020usrJkbn
     */
    public void setAnp020usrJkbn(int anp020usrJkbn) {
        anp020usrJkbn__ = anp020usrJkbn;
    }
    /**
     * <p>anp020usrUkoFlg を取得します。
     * @return anp020usrUkoFlg
     */
    public int getAnp020usrUkoFlg() {
        return anp020usrUkoFlg__;
    }
    /**
     * <p>anp020usrUkoFlg をセットします。
     * @param anp020usrUkoFlg anp020usrUkoFlg
     */
    public void setAnp020usrUkoFlg(int anp020usrUkoFlg) {
        anp020usrUkoFlg__ = anp020usrUkoFlg;
    }


}