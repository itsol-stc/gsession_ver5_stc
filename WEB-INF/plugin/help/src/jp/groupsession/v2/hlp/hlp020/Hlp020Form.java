package jp.groupsession.v2.hlp.hlp020;

import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.lucene.LuceneSearchResultRowModel;
import jp.groupsession.v2.hlp.hlp002.Hlp002Form;

/**
 * <br>[機  能] ヘルプ 検索結果画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp020Form extends Hlp002Form {

    /** 画面表示用 検索ワード */
    private String hlp020dspWord__ = "";

    /** 画面表示用 検索結果件数 */
    private String hlp020dspCount__ = "";

    /** 画面表示用 現在表示件数範囲(1-10, 11-20...) */
    private String hlp020dspPage__ = "";

    /** 表示するページ */
    private int hlp020DispPage__ = 1;

    /** 最大ページ数 */
    private int hlp020PageCount__ = 1;

    /** ページリスト(検索結果ページングに使用) */
    private ArrayList<String> hlp020pageList__ = null;

    /** 検索結果表示用List */
    private List<LuceneSearchResultRowModel> hlp020resultList__ = null;

    /** 検索実行フラグ */
    private int hlp020searchFlg__ = 0;
    /**
     * <p>hlp020DispPage を取得します。
     * @return hlp020DispPage
     */
    public int getHlp020DispPage() {
        return hlp020DispPage__;
    }
    /**
     * <p>hlp020DispPage をセットします。
     * @param hlp020DispPage hlp020DispPage
     */
    public void setHlp020DispPage(int hlp020DispPage) {
        hlp020DispPage__ = hlp020DispPage;
    }
    /**
     * <p>hlp020dspCount を取得します。
     * @return hlp020dspCount
     */
    public String getHlp020dspCount() {
        return hlp020dspCount__;
    }
    /**
     * <p>hlp020dspCount をセットします。
     * @param hlp020dspCount hlp020dspCount
     */
    public void setHlp020dspCount(String hlp020dspCount) {
        hlp020dspCount__ = hlp020dspCount;
    }
    /**
     * <p>hlp020dspPage を取得します。
     * @return hlp020dspPage
     */
    public String getHlp020dspPage() {
        return hlp020dspPage__;
    }
    /**
     * <p>hlp020dspPage をセットします。
     * @param hlp020dspPage hlp020dspPage
     */
    public void setHlp020dspPage(String hlp020dspPage) {
        hlp020dspPage__ = hlp020dspPage;
    }
    /**
     * <p>hlp020dspWord を取得します。
     * @return hlp020dspWord
     */
    public String getHlp020dspWord() {
        return hlp020dspWord__;
    }
    /**
     * <p>hlp020dspWord をセットします。
     * @param hlp020dspWord hlp020dspWord
     */
    public void setHlp020dspWord(String hlp020dspWord) {
        hlp020dspWord__ = hlp020dspWord;
    }
    /**
     * <p>hlp020PageCount を取得します。
     * @return hlp020PageCount
     */
    public int getHlp020PageCount() {
        return hlp020PageCount__;
    }
    /**
     * <p>hlp020PageCount をセットします。
     * @param hlp020PageCount hlp020PageCount
     */
    public void setHlp020PageCount(int hlp020PageCount) {
        hlp020PageCount__ = hlp020PageCount;
    }
    /**
     * <p>hlp020resultList を取得します。
     * @return hlp020resultList
     */
    public List<LuceneSearchResultRowModel> getHlp020resultList() {
        return hlp020resultList__;
    }
    /**
     * <p>hlp020resultList をセットします。
     * @param hlp020resultList hlp020resultList
     */
    public void setHlp020resultList(List<LuceneSearchResultRowModel> hlp020resultList) {
        hlp020resultList__ = hlp020resultList;
    }
    /**
     * <p>hlp020pageList を取得します。
     * @return hlp020pageList
     */
    public ArrayList<String> getHlp020pageList() {
        return hlp020pageList__;
    }
    /**
     * <p>hlp020pageList をセットします。
     * @param hlp020pageList hlp020pageList
     */
    public void setHlp020pageList(ArrayList<String> hlp020pageList) {
        hlp020pageList__ = hlp020pageList;
    }
    /**
     * <p>hlp020searchFlg を取得します。
     * @return hlp020searchFlg
     */
    public int getHlp020searchFlg() {
        return hlp020searchFlg__;
    }
    /**
     * <p>hlp020searchFlg をセットします。
     * @param hlp020searchFlg hlp020searchFlg
     */
    public void setHlp020searchFlg(int hlp020searchFlg) {
        hlp020searchFlg__ = hlp020searchFlg;
    }
}
