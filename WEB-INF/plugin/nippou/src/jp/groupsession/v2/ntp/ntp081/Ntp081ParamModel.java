package jp.groupsession.v2.ntp.ntp081;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp080.Ntp080ParamModel;
import jp.groupsession.v2.ntp.util.NtpValidateUtil;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] 日報 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp081ParamModel extends Ntp080ParamModel {

    /** 共有範囲 */
    private int ntp081KyoyuFlg__ = -1;
    /** 時間単位 */
    private int ntp081HourDivision__ = -1;
    /** タイトルカラーコメント１（青） */
    private String ntp081ColCmt1__ = null;
    /** タイトルカラーコメント２（赤） */
    private String ntp081ColCmt2__ = null;
    /** タイトルカラーコメント３（緑） */
    private String ntp081ColCmt3__ = null;
    /** タイトルカラーコメント４（黄） */
    private String ntp081ColCmt4__ = null;
    /** タイトルカラーコメント５（黒） */
    private String ntp081ColCmt5__ = null;

    /** 見込み度コメント1(10) */
    private String ntp081MikomidoCmt1__ = null;
    /** 見込み度コメント2(30) */
    private String ntp081MikomidoCmt2__ = null;
    /** 見込み度コメント3(50) */
    private String ntp081MikomidoCmt3__ = null;
    /** 見込み度コメント4(70) */
    private String ntp081MikomidoCmt4__ = null;
    /** 見込み度コメント5(100) */
    private String ntp081MikomidoCmt5__ = null;

    /** 日報確認者設定 */
    private int ntp081KakuteiFlg__ = -1;

    /**
     * <p>ntp081KyoyuFlg を取得します。
     * @return ntp081KyoyuFlg
     */
    public int getNtp081KyoyuFlg() {
        return ntp081KyoyuFlg__;
    }

    /**
     * <p>ntp081KyoyuFlg をセットします。
     * @param ntp081KyoyuFlg ntp081KyoyuFlg
     */
    public void setNtp081KyoyuFlg(int ntp081KyoyuFlg) {
        ntp081KyoyuFlg__ = ntp081KyoyuFlg;
    }

    /**
     * <p>ntp081HourDivision を取得します。
     * @return ntp081HourDivision
     */
    public int getNtp081HourDivision() {
        return ntp081HourDivision__;
    }

    /**
     * <p>ntp081HourDivision をセットします。
     * @param ntp081HourDivision ntp081HourDivision
     */
    public void setNtp081HourDivision(int ntp081HourDivision) {
        ntp081HourDivision__ = ntp081HourDivision;
    }

    /**
     * <p>ntp081ColCmt1 を取得します。
     * @return ntp081ColCmt1
     */
    public String getNtp081ColCmt1() {
        return ntp081ColCmt1__;
    }

    /**
     * <p>ntp081ColCmt1 をセットします。
     * @param ntp081ColCmt1 ntp081ColCmt1
     */
    public void setNtp081ColCmt1(String ntp081ColCmt1) {
        ntp081ColCmt1__ = ntp081ColCmt1;
    }

    /**
     * <p>ntp081ColCmt2 を取得します。
     * @return ntp081ColCmt2
     */
    public String getNtp081ColCmt2() {
        return ntp081ColCmt2__;
    }

    /**
     * <p>ntp081ColCmt2 をセットします。
     * @param ntp081ColCmt2 ntp081ColCmt2
     */
    public void setNtp081ColCmt2(String ntp081ColCmt2) {
        ntp081ColCmt2__ = ntp081ColCmt2;
    }

    /**
     * <p>ntp081ColCmt3 を取得します。
     * @return ntp081ColCmt3
     */
    public String getNtp081ColCmt3() {
        return ntp081ColCmt3__;
    }

    /**
     * <p>ntp081ColCmt3 をセットします。
     * @param ntp081ColCmt3 ntp081ColCmt3
     */
    public void setNtp081ColCmt3(String ntp081ColCmt3) {
        ntp081ColCmt3__ = ntp081ColCmt3;
    }

    /**
     * <p>ntp081ColCmt4 を取得します。
     * @return ntp081ColCmt4
     */
    public String getNtp081ColCmt4() {
        return ntp081ColCmt4__;
    }

    /**
     * <p>ntp081ColCmt4 をセットします。
     * @param ntp081ColCmt4 ntp081ColCmt4
     */
    public void setNtp081ColCmt4(String ntp081ColCmt4) {
        ntp081ColCmt4__ = ntp081ColCmt4;
    }

    /**
     * <p>ntp081ColCmt5 を取得します。
     * @return ntp081ColCmt5
     */
    public String getNtp081ColCmt5() {
        return ntp081ColCmt5__;
    }

    /**
     * <p>ntp081ColCmt5 をセットします。
     * @param ntp081ColCmt5 ntp081ColCmt5
     */
    public void setNtp081ColCmt5(String ntp081ColCmt5) {
        ntp081ColCmt5__ = ntp081ColCmt5;
    }

    /**
     * <p>ntp081KakuteiFlg を取得します。
     * @return ntp081KakuteiFlg
     */
    public int getNtp081KakuteiFlg() {
        return ntp081KakuteiFlg__;
    }

    /**
     * <p>ntp081KakuteiFlg をセットします。
     * @param ntp081KakuteiFlg ntp081KakuteiFlg
     */
    public void setNtp081KakuteiFlg(int ntp081KakuteiFlg) {
        ntp081KakuteiFlg__ = ntp081KakuteiFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return エラー
     */
    public ActionErrors validateNtp081() {
        ActionErrors errors = new ActionErrors();

        //カラーコメント青
        NtpValidateUtil.validateTextField(errors,
                                          ntp081ColCmt1__,
                                          "ntp081ColCmt1",
                                          GSConstNippou.TEXT_COLOR_COMMENT + "青",
                                          30,
                                          false);
        //カラーコメント赤
        NtpValidateUtil.validateTextField(errors,
                                          ntp081ColCmt2__,
                                          "ntp081ColCmt2",
                                          GSConstNippou.TEXT_COLOR_COMMENT + "赤",
                                          30,
                                          false);
        //カラーコメント緑
        NtpValidateUtil.validateTextField(errors,
                                          ntp081ColCmt3__,
                                          "ntp081ColCmt3",
                                          GSConstNippou.TEXT_COLOR_COMMENT + "緑",
                                          30,
                                          false);
        //カラーコメント橙
        NtpValidateUtil.validateTextField(errors,
                                          ntp081ColCmt4__,
                                          "ntp081ColCmt4",
                                          GSConstNippou.TEXT_COLOR_COMMENT + "橙",
                                          30,
                                          false);
        //カラーコメント黒
        NtpValidateUtil.validateTextField(errors,
                                          ntp081ColCmt5__,
                                          "ntp081ColCmt5",
                                          GSConstNippou.TEXT_COLOR_COMMENT + "黒",
                                          30,
                                          false);


        //見込み度 10%
        NtpValidateUtil.validateTextField(errors,
                                          ntp081MikomidoCmt1__,
                                          "ntp081Mikomido1",
                                          "見込み度 10%",
                                          GSConstNippou.MAX_LENGTH_MIKOMIDO,
                                          false);
        //見込み度 30%
        NtpValidateUtil.validateTextField(errors,
                                          ntp081MikomidoCmt2__,
                                          "ntp081MikomidoCmt2",
                                          "見込み度 30%",
                                          GSConstNippou.MAX_LENGTH_MIKOMIDO,
                                          false);
        //見込み度 50%
        NtpValidateUtil.validateTextField(errors,
                                          ntp081MikomidoCmt3__,
                                          "ntp081MikomidoCmt3",
                                          "見込み度 50%",
                                          GSConstNippou.MAX_LENGTH_MIKOMIDO,
                                          false);
        //見込み度 70%
        NtpValidateUtil.validateTextField(errors,
                                          ntp081MikomidoCmt4__,
                                          "ntp081MikomidoCmt4",
                                          "見込み度 70%",
                                          GSConstNippou.MAX_LENGTH_MIKOMIDO,
                                          false);
        //見込み度 100%
        NtpValidateUtil.validateTextField(errors,
                                          ntp081MikomidoCmt5__,
                                          "ntp081MikomidoCmt5",
                                          "見込み度 100%",
                                          GSConstNippou.MAX_LENGTH_MIKOMIDO,
                                          false);


        return errors;
    }

    /**
     * <p>ntp081MikomidoCmt1 を取得します。
     * @return ntp081MikomidoCmt1
     */
    public String getNtp081MikomidoCmt1() {
        return ntp081MikomidoCmt1__;
    }

    /**
     * <p>ntp081MikomidoCmt1 をセットします。
     * @param ntp081MikomidoCmt1 ntp081MikomidoCmt1
     */
    public void setNtp081MikomidoCmt1(String ntp081MikomidoCmt1) {
        ntp081MikomidoCmt1__ = ntp081MikomidoCmt1;
    }

    /**
     * <p>ntp081MikomidoCmt2 を取得します。
     * @return ntp081MikomidoCmt2
     */
    public String getNtp081MikomidoCmt2() {
        return ntp081MikomidoCmt2__;
    }

    /**
     * <p>ntp081MikomidoCmt2 をセットします。
     * @param ntp081MikomidoCmt2 ntp081MikomidoCmt2
     */
    public void setNtp081MikomidoCmt2(String ntp081MikomidoCmt2) {
        ntp081MikomidoCmt2__ = ntp081MikomidoCmt2;
    }

    /**
     * <p>ntp081MikomidoCmt3 を取得します。
     * @return ntp081MikomidoCmt3
     */
    public String getNtp081MikomidoCmt3() {
        return ntp081MikomidoCmt3__;
    }

    /**
     * <p>ntp081MikomidoCmt3 をセットします。
     * @param ntp081MikomidoCmt3 ntp081MikomidoCmt3
     */
    public void setNtp081MikomidoCmt3(String ntp081MikomidoCmt3) {
        ntp081MikomidoCmt3__ = ntp081MikomidoCmt3;
    }

    /**
     * <p>ntp081MikomidoCmt4 を取得します。
     * @return ntp081MikomidoCmt4
     */
    public String getNtp081MikomidoCmt4() {
        return ntp081MikomidoCmt4__;
    }

    /**
     * <p>ntp081MikomidoCmt4 をセットします。
     * @param ntp081MikomidoCmt4 ntp081MikomidoCmt4
     */
    public void setNtp081MikomidoCmt4(String ntp081MikomidoCmt4) {
        ntp081MikomidoCmt4__ = ntp081MikomidoCmt4;
    }

    /**
     * <p>ntp081MikomidoCmt5 を取得します。
     * @return ntp081MikomidoCmt5
     */
    public String getNtp081MikomidoCmt5() {
        return ntp081MikomidoCmt5__;
    }

    /**
     * <p>ntp081MikomidoCmt5 をセットします。
     * @param ntp081MikomidoCmt5 ntp081MikomidoCmt5
     */
    public void setNtp081MikomidoCmt5(String ntp081MikomidoCmt5) {
        ntp081MikomidoCmt5__ = ntp081MikomidoCmt5;
    }
}