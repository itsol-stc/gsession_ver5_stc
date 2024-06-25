package jp.groupsession.v2.prj.prj160kn;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート確認画面 取り込みファイルから取得したTODO情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class TodoCsvImportModel extends AbstractModel {

    /** タイトル */
    private String title__ = null;
    /** カテゴリ */
    private String category__ = null;
    /** 予定 開始 */
    private String yoteiFr__ = null;
    /** 予定 終了 */
    private String yoteiTo__ = null;
    /** 予定 工数 */
    private String yoteiKosu__ = null;
    /** 実績 開始 */
    private String zissekiFr__ = null;
    /** 実績 終了 */
    private String zissekiTo__ = null;
    /** 実績 工数 */
    private String zissekiKosu__ = null;
    /** 内容 */
    private String naiyo__ = null;
    /** 担当 */
    private ArrayList<String> tanto__ = null;
    /** 重要度 */
    private String priority__ = null;
    /** 警告 */
    private String caution__ = null;
    /** 状態 */
    private String status__ = null;
    /** 状態変更理由 */
    private String history__ = null;

    /**
     * <p>category を取得します。
     * @return category
     */
    public String getCategory() {
        return category__;
    }
    /**
     * <p>category をセットします。
     * @param category category
     */
    public void setCategory(String category) {
        category__ = category;
    }
    /**
     * <p>caution を取得します。
     * @return caution
     */
    public String getCaution() {
        return caution__;
    }
    /**
     * <p>caution をセットします。
     * @param caution caution
     */
    public void setCaution(String caution) {
        caution__ = caution;
    }
    /**
     * <p>history を取得します。
     * @return history
     */
    public String getHistory() {
        return history__;
    }
    /**
     * <p>history をセットします。
     * @param history history
     */
    public void setHistory(String history) {
        history__ = history;
    }
    /**
     * <p>naiyo を取得します。
     * @return naiyo
     */
    public String getNaiyo() {
        return naiyo__;
    }
    /**
     * <p>naiyo をセットします。
     * @param naiyo naiyo
     */
    public void setNaiyo(String naiyo) {
        naiyo__ = naiyo;
    }
    /**
     * <p>priority を取得します。
     * @return priority
     */
    public String getPriority() {
        return priority__;
    }
    /**
     * <p>priority をセットします。
     * @param priority priority
     */
    public void setPriority(String priority) {
        priority__ = priority;
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
     * <p>tanto を取得します。
     * @return tanto
     */
    public ArrayList<String> getTanto() {
        return tanto__;
    }
    /**
     * <p>tanto をセットします。
     * @param tanto tanto
     */
    public void setTanto(ArrayList<String> tanto) {
        tanto__ = tanto;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>yoteiFr を取得します。
     * @return yoteiFr
     */
    public String getYoteiFr() {
        return yoteiFr__;
    }
    /**
     * <p>yoteiFr をセットします。
     * @param yoteiFr yoteiFr
     */
    public void setYoteiFr(String yoteiFr) {
        yoteiFr__ = yoteiFr;
    }
    /**
     * <p>yoteiKosu を取得します。
     * @return yoteiKosu
     */
    public String getYoteiKosu() {
        return yoteiKosu__;
    }
    /**
     * <p>yoteiKosu をセットします。
     * @param yoteiKosu yoteiKosu
     */
    public void setYoteiKosu(String yoteiKosu) {
        yoteiKosu__ = yoteiKosu;
    }
    /**
     * <p>yoteiTo を取得します。
     * @return yoteiTo
     */
    public String getYoteiTo() {
        return yoteiTo__;
    }
    /**
     * <p>yoteiTo をセットします。
     * @param yoteiTo yoteiTo
     */
    public void setYoteiTo(String yoteiTo) {
        yoteiTo__ = yoteiTo;
    }
    /**
     * <p>zissekiFr を取得します。
     * @return zissekiFr
     */
    public String getZissekiFr() {
        return zissekiFr__;
    }
    /**
     * <p>zissekiFr をセットします。
     * @param zissekiFr zissekiFr
     */
    public void setZissekiFr(String zissekiFr) {
        zissekiFr__ = zissekiFr;
    }
    /**
     * <p>zissekiKosu を取得します。
     * @return zissekiKosu
     */
    public String getZissekiKosu() {
        return zissekiKosu__;
    }
    /**
     * <p>zissekiKosu をセットします。
     * @param zissekiKosu zissekiKosu
     */
    public void setZissekiKosu(String zissekiKosu) {
        zissekiKosu__ = zissekiKosu;
    }
    /**
     * <p>zissekiTo を取得します。
     * @return zissekiTo
     */
    public String getZissekiTo() {
        return zissekiTo__;
    }
    /**
     * <p>zissekiTo をセットします。
     * @param zissekiTo zissekiTo
     */
    public void setZissekiTo(String zissekiTo) {
        zissekiTo__ = zissekiTo;
    }
}