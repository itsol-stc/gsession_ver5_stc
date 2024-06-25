package jp.groupsession.v2.enq.enq210kn;

import java.io.Serializable;

/**
 * <p>アンケート編集確認画面 回答情報削除確認ダイアログ
 * @author JTS
 */
public class Enq210knEnqDataModel implements Serializable {

    /** 回答情報存在フラグ  0:存在しない  1:存在する */
    private int enqAnswerFlg__ = 0;

    /** 設問情報変更フラグ 0:変更なし  1:変更あり*/
    private int enqQchangeFlg__ = 0;

    /** 設問添付変更フラグ 0:変更なし  1:変更あり*/
    private int enqtempChangeFlg__ = 0;

    /**
     * <p>enqAnswerFlg を取得します。
     * @return enqAnswerFlg
     */
    public int getEnqAnswerFlg() {
        return enqAnswerFlg__;
    }

    /**
     * <p>enqAnswerFlg をセットします。
     * @param enqAnswerFlg enqAnswerFlg
     */
    public void setEnqAnswerFlg(int enqAnswerFlg) {
        enqAnswerFlg__ = enqAnswerFlg;
    }

    /**
     * <p>enqQchangeFlg を取得します。
     * @return enqQchangeFlg
     */
    public int getEnqQchangeFlg() {
        return enqQchangeFlg__;
    }

    /**
     * <p>enqQchangeFlg をセットします。
     * @param enqQchangeFlg enqQchangeFlg
     */
    public void setEnqQchangeFlg(int enqQchangeFlg) {
        enqQchangeFlg__ = enqQchangeFlg;
    }
    /**
     * <p>enqtempchangeFlg を取得します。
     * @return enqtempchangeFlg
     */
    public int getEnqtempChangeFlg() {
        return enqtempChangeFlg__;
    }
    /**
     * <p>enqtempchangeFlg をセットします。
     * @param enqtempChangeFlg enqtempChangeFlg
     */
    public void setEnqtempChangeFlg(int enqtempChangeFlg) {
        enqtempChangeFlg__ = enqtempChangeFlg;
    }

}
