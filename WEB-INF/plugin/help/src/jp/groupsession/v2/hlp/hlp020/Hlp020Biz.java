package jp.groupsession.v2.hlp.hlp020;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.lucene.LuceneSearchResultModel;
import jp.co.sjts.util.lucene.LuceneSearchResultRowModel;
import jp.co.sjts.util.lucene.LuceneUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] ヘルプ 検索結果画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp020Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Hlp020Biz.class);

    /** コネクション */
    protected Connection con_ = null;
    /** 最大表示件数 */
    private static final int MAX_SEARCH_CNT = 10;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Hlp020Biz(Connection con) {
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param rpath ルートパス
     * @param word 検索ワード
     * @return nothing 検索結果はありますか？ true: 無し　false: 有り
     * @throws Exception 実行時例外
     */
    public boolean getInitData(
            Hlp020ParamModel paramMdl, String rpath, String word) throws Exception {
        log__.debug("ヘルプ検索結果画面表示情報取得開始");

        //検索実行
        return __searchHelp(paramMdl, rpath, word);
    }

    /**
     * <br>[機  能] 検索実行処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param rpath パス
     * @param word 検索ワード
     * @return nothing 検索結果はありますか？ true: 無し　false: 有り
     * @throws Exception 実行時例外
     */
    private boolean __searchHelp(
            Hlp020ParamModel paramMdl, String rpath, String word) throws Exception {
        boolean nothing = false;
        LuceneUtil lUtil = new LuceneUtil();

        //検索結果取得
        String escapeWord = __htmlEscape(word);
        LuceneSearchResultModel resultModel = lUtil.getSearchHits(rpath, escapeWord);
        //検索結果が無い場合、検索結果無しフラグを立てる
        if (resultModel.getResultList() == null || resultModel.getResultList().isEmpty()) {
            log__.debug("検索結果がありません : " + nothing);
            nothing = true;
            return nothing;
        }
        log__.debug("検索結果件数 = " + resultModel.getHitCount());

        //ヒット件数を取得
        int hits = resultModel.getHitCount();

        //ヒット件数を表示用にカンマフォーマットします
        String counts = String.valueOf(hits);
        counts = StringUtil.toCommaFormat(counts);

        //start 1-10,start 11-20,start 21-30...
        log__.debug("paramMdl.getHlp020DispPage() = " + paramMdl.getHlp020DispPage());
        int start = ((paramMdl.getHlp020DispPage() - 1) * MAX_SEARCH_CNT) + 1;
        log__.debug("start = " + start);
        //1-10 end,11-20 end,21-30 end...
        int end = paramMdl.getHlp020DispPage() * MAX_SEARCH_CNT;
        log__.debug("start = " + end);
        List<LuceneSearchResultRowModel> lsrrMdlList = new ArrayList<LuceneSearchResultRowModel>();
        //表示する検索結果の判定に用いるカウント
        int iresult = 1;
        //11-15件表示、の15件の部分
        int endcount = 0;
        //iがstart以上end以下の場合のみ、検索結果を格納します
        for (LuceneSearchResultRowModel lsrrMdl : resultModel.getResultList()) {
            if (iresult >= start && iresult <= end) {
                lsrrMdlList.add(lsrrMdl);
                if (endcount == 0) {
                    endcount += iresult - 1;
                }
                endcount++;
            }
            iresult++;
        }

        //最大ページ数を算出
        int maxPage = hits / MAX_SEARCH_CNT;
        if ((hits % MAX_SEARCH_CNT) > 0) {
            maxPage++;
        }
        log__.debug("maxPage = " + maxPage);
        //ページ数リストを作成
        ArrayList<String> pageList = new ArrayList<String>();
        for (int i = 1; i <= maxPage; i++) {
            pageList.add(String.valueOf(i));
        }

        //現在表示中のページテキスト作成(1-10, 11-20...)
        String dspPage = String.valueOf(start) + "-" + String.valueOf(endcount);
        log__.debug("dspPage : " + dspPage);

        //作成した値をアクションフォームにセット
        paramMdl.setHlp020resultList(lsrrMdlList);
        paramMdl.setHlp020dspCount(counts);
        paramMdl.setHlp020dspWord(word);
        paramMdl.setHlp020PageCount(maxPage);
        paramMdl.setHlp020pageList(pageList);
        paramMdl.setHlp020dspPage(dspPage);

        return nothing;
    }

    /**
     * <br>[機  能] 文字列中の「+ - && || ! ( ) { } [ ] ^ " ~ * ? : \」を\エスケープする
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @return エスケープ後の文字列
     */
    private String __htmlEscape(String str) {
        String escStr = "";
        if (str == null) {
            return escStr;
        }
        String escape = "\\";

        if (!str.equals("")) {
            char[] target = str.toCharArray();
            for (int i = 0; i < target.length; i++) {
                char c = target[i];
                if ('+' == c  || '-' == c
                 || '&' == c  || '|' == c
                 || '!' == c  || '(' == c
                 || ')' == c  || '[' == c
                 || ']' == c  || '{' == c
                 || '}' == c  || '~' == c
                 || '^' == c  || '*' == c
                 || '?' == c  || ':' == c
                 || '\\' == c || '\"' == c) {
                    escStr += escape + c;
                } else {
                    escStr += c;
                }
            }
        }
        return escStr;
    }
}
