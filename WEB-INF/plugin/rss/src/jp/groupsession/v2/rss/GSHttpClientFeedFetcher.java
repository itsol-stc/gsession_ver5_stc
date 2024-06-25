package jp.groupsession.v2.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.http.HttpRequestModel;
import jp.groupsession.v2.cmn.http.HttpResponseModel;

/**
 * <br>[機  能] HttpClientFeedFetcherではProxy設定済みのHttpClientが使用できないため、それを可能としたクラス。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSHttpClientFeedFetcher {

    /** HttpOperation */
    private HttpOperation httpOperation__ = null;

    /**
     * @param feedUrl フィードURL
     * @throws IllegalArgumentException IllegalArgumentException
     * @throws IOException HTTPリクエスト送信に失敗
     * @throws InterruptedException HTTPリクエスト送信に失敗
     * @throws FeedException FeedException
     * @throws FetcherException FetcherException
     * @return SyndFeed
     */
    public SyndFeed retrieveFeed(URL feedUrl)
    throws IllegalArgumentException, IOException, InterruptedException, FeedException {
        if (feedUrl == null) {
            throw new IllegalArgumentException("null is not a valid URL");
        }

        String urlStr = feedUrl.toString();
 
        HttpRequestModel httpReqMdl = new HttpRequestModel();
        httpReqMdl.setRedirectFlg(true);
        HttpResponseModel httpResMdl = httpOperation__.sendRequest(urlStr, httpReqMdl, HttpOperation.METHOD_GET);

        InputStream stream = null;
        if (httpResMdl.getHeader("Content-Encoding") != null
        && ("gzip".equalsIgnoreCase(httpResMdl.getHeader("Content-Encoding").get(0)))) {
            stream = new GZIPInputStream(httpResMdl.getBodyStream());
        } else {
            stream = httpResMdl.getBodyStream();
        }

        try {

            XmlReader reader = null;
            if (httpResMdl.getHeader("Content-Type") != null) {
                reader = new XmlReader(stream, httpResMdl.getHeader("Content-Type").get(0),
                                    true);
            } else {
                reader = new XmlReader(stream, true);
            }
    
            return new SyndFeedInput().build(reader);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public HttpOperation getHttpOperation() {
        return httpOperation__;
    }

    public void setHttpOperation(HttpOperation httpOperation) {
        httpOperation__ = httpOperation;
    }
}
