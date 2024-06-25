package jp.groupsession.v2.rss;

import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import org.xml.sax.InputSource;

/**
 * <br>[機  能] RssFeedのテストクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssFeedTest {

    /** フィードのURL */
    private static final String[] FEED_URLS__ = {
        "http://rss.cnn.com/rss/edition.rss",
        "https://news.yahoo.co.jp/rss/topics/sports.xml",
      };

    /**
     * <br>[機  能] メインメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {

        try {
            // フィードの内容、フィードに含まれる記事エントリの内容を出力する
            SyndFeed feed = null;
            for (String url : FEED_URLS__) {
                // フィードの取得
                feed = new SyndFeedInput().build(new InputSource(url));
                System.out.format("フィードタイトル:[%s] 著者:[%s]\n",
                    feed.getTitle(),
                      feed.getUri());

                for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
                    System.out.format("\t更新時刻:[%s] URL:[%s] 記事タイトル:[%s]\n",
                        entry.getPublishedDate(),
                        entry.getLink(),
                        entry.getTitle());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
