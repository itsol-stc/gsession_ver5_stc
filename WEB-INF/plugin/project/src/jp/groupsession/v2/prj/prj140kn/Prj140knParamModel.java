package jp.groupsession.v2.prj.prj140kn;

import jp.groupsession.v2.prj.prj140.Prj140ParamModel;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj140knParamModel extends Prj140ParamModel {

    /** 予算(表示用) */
    private String yosan__;
    /** 公開・非公開(表示用) */
    private String koukai__;
    /** 開始(表示用) */
    private String startDate__;
    /** 終了(表示用) */
    private String endDate__;
    /** 状態(表示用) */
    private String status__;
    /** 目標・目的(表示用) */
    private String mokuhyou__;
    /** 内容(表示用) */
    private String naiyou__;
    /** 編集権限(表示用) */
    private String kengen__;
    /** ショートメール通知先 */
    private String smailKbn__;

    /**
     * <p>smailKbn を取得します。
     * @return smailKbn
     */
    public String getSmailKbn() {
        return smailKbn__;
    }
    /**
     * <p>smailKbn をセットします。
     * @param smailKbn smailKbn
     */
    public void setSmailKbn(String smailKbn) {
        smailKbn__ = smailKbn;
    }
    /**
     * <p>yosan を取得します。
     * @return yosan
     */
    public String getYosan() {
        return yosan__;
    }
    /**
     * <p>yosan をセットします。
     * @param yosan yosan
     */
    public void setYosan(String yosan) {
        yosan__ = yosan;
    }
    /**
     * <p>koukai を取得します。
     * @return koukai
     */
    public String getKoukai() {
        return koukai__;
    }
    /**
     * <p>koukai をセットします。
     * @param koukai koukai
     */
    public void setKoukai(String koukai) {
        koukai__ = koukai;
    }
    /**
     * <p>startDate を取得します。
     * @return startDate
     */
    public String getStartDate() {
        return startDate__;
    }
    /**
     * <p>startDate をセットします。
     * @param startDate startDate
     */
    public void setStartDate(String startDate) {
        startDate__ = startDate;
    }
    /**
     * <p>endDate を取得します。
     * @return endDate
     */
    public String getEndDate() {
        return endDate__;
    }
    /**
     * <p>endDate をセットします。
     * @param endDate endDate
     */
    public void setEndDate(String endDate) {
        endDate__ = endDate;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public String getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(String status) {
        status__ = status;
    }
    /**
     * <p>mokuhyou を取得します。
     * @return mokuhyou
     */
    public String getMokuhyou() {
        return mokuhyou__;
    }
    /**
     * <p>mokuhyou をセットします。
     * @param mokuhyou mokuhyou
     */
    public void setMokuhyou(String mokuhyou) {
        mokuhyou__ = mokuhyou;
    }
    /**
     * <p>naiyou を取得します。
     * @return naiyou
     */
    public String getNaiyou() {
        return naiyou__;
    }
    /**
     * <p>naiyou をセットします。
     * @param naiyou naiyou
     */
    public void setNaiyou(String naiyou) {
        naiyou__ = naiyou;
    }
    /**
     * <p>kengen を取得します。
     * @return kengen
     */
    public String getKengen() {
        return kengen__;
    }
    /**
     * <p>kengen をセットします。
     * @param kengen kengen
     */
    public void setKengen(String kengen) {
        kengen__ = kengen;
    }
}