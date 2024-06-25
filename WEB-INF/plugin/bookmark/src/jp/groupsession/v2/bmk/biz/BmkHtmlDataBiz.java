package jp.groupsession.v2.bmk.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.model.BmkHtmlDataModel;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnProxyAddressDao;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.http.HttpRequestModel;
import jp.groupsession.v2.cmn.http.HttpResponseModel;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSPassword;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * <br>[機  能] HTMLの情報を取得するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkHtmlDataBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkHtmlDataBiz.class);

    /** タイトル */
    private String title__ = "";
    /** コメント */
    private String coment__ = "";
    /** タイトル開始タグ取得フラグ */
    private boolean topFlgTitle__ = false;
    /** コメント開始タグ取得フラグ */
    private boolean topFlgComent__ = false;
    /** タイトル取得完了フラグ */
    private boolean titleOk__ = false;
    /** コメント取得完了フラグ */
    private boolean cmentOk__ = false;
    /** タイトル取得回数 */
    private int getTitleCount__ = 0;
    /** コメント */
    private String charset__ = "";

    /**
     * <br>[機  能] URLからタイトル取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param strUrl URL
     * @return BmkHtmlDataModel HTML情報
     * @throws Exception 実行例外
     * @throws SQLException SQL実行例外
     * @throws MalformedURLException 実行例外
     * @throws IOException 実行例外
     */
    public BmkHtmlDataModel getHtmlData(Connection con, String strUrl)
    throws Exception, SQLException, MalformedURLException, IOException {

        title__ = "";
        coment__ = "";
        topFlgTitle__ = false;
        topFlgComent__ = false;
        titleOk__ = false;
        cmentOk__ = false;
        getTitleCount__ = 0;
        charset__ = "";

        BmkHtmlDataModel model = new BmkHtmlDataModel();

        //「http://」,「https://」以外は処理しない
        String scheme = strUrl.substring(0, strUrl.indexOf("://"));
        if (!scheme.equals("http") && !scheme.equals("https")) {
            return model;
        }

        //タイトル取得(HttpURLConnection)
        if (title__.equals("")) {
            log__.debug("タイトル取得１");
            __getTitleFromUrlCon(con, strUrl);
            getTitleCount__++;
        }

        //タイトル取得(HttpMethod_parser)
        if (title__.equals("")) {
            log__.debug("タイトル取得２");
            __getTitleFromUrlPer(con, strUrl, 0);
            __getTitleFromUrlPer(con, strUrl, 1);
            getTitleCount__++;
        }

        //タイトル取得(HttpMethod_HTML解析)
        if (title__.equals("")) {
            log__.debug("タイトル取得３");
            __getTitleFromUrlMet(con, strUrl);
            getTitleCount__++;
        }

        //タイトル取得(HttpURLConnection)
        if (title__.equals("")) {
            log__.debug("タイトル取得４");
            __getTitleFromUrlCon(con, strUrl);
            getTitleCount__++;
        }

        model.setTitle(title__);
        model.setDescription(coment__);
        return model;
    }

    /**
     * <br>[機  能] 文字コード、タイトル・コメント取得(HttpMethod_parser)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param strUrl URL
     * @param kbn 取得区分 0:文字コード 1:タイトル・コメント
     * @throws Exception 実行例外
     * @throws SQLException SQL実行例外
     * @throws MalformedURLException 実行例外
     * @throws IOException 実行例外
     */
    private void __getTitleFromUrlPer(Connection con, String strUrl, int kbn)
    throws Exception, SQLException, MalformedURLException, IOException {

        HttpOperation httpOperation = __createHttpOperation(con);

        String url = strUrl;
        int statusCode = -1;
        HttpResponseModel httpResMdl = null;
        try {
            HttpRequestModel httpReqMdl = new HttpRequestModel();

            httpResMdl = httpOperation.sendRequest(url, httpReqMdl, HttpOperation.METHOD_GET);
            statusCode = httpResMdl.getStatusCode();
        } catch (InterruptedException e) {
            log__.debug("取得に失敗(InterruptedException)");
        } catch (IOException e) {
            log__.debug("取得に失敗(IOException)");
        }
        if (statusCode != 200) {
            //取得失敗
            log__.debug("取得失敗 終了");
            return;
        }

        InputStreamReader reader = null;
        try {
            if (kbn == 0) {
                reader = new InputStreamReader(
                                  httpResMdl.getBodyStream(), Encoding.JISAUTODETECT);
            } else if (kbn == 1) {
                log__.debug("charset__ ===> " + charset__);
                reader = new InputStreamReader(httpResMdl.getBodyStream(), charset__);
            }
        } catch (UnsupportedEncodingException e) {
            //ありえないので何もしない
        }

        if (kbn == 0) {
            //文字コード取得
            try {
                DOMParser parser = new DOMParser();
                parser.parse(new InputSource(reader));
                Document doc = parser.getDocument();
                Element el = doc.getElementById("");
                charset__ = __getCharset(el);
            } catch (Exception e) {
                log__.debug("文字コード取得に失敗");
            }
        } else if (kbn == 1) {
            //タイトル、コメント取得
            try {
                DOMParser parser = new DOMParser();
                parser.parse(new InputSource(reader));
                Document doc = parser.getDocument();
                Element el = doc.getElementById("");
                __getTextData(el);
            } catch (Exception e) {
                log__.debug("タイトル、コメント取得に失敗");
            }
        }
    }

    /**
     * <br>[機  能] 文字コード取得
     * <br>[解  説]
     * <br>[備  考]
     * @param el Element
     * @return 文字コード
     */
    private String __getCharset(Element el) {
        log__.debug("文字コード取得");

        String charset = Encoding.JISAUTODETECT;
        String content = "";

        //metaタグで分割する
        NodeList nlist = el.getElementsByTagName("meta");
        log__.debug("nlist.getLength() ===> " + nlist.getLength());
        for (int i = 0; i < nlist.getLength(); i++) {
            log__.debug("i ===> " + i);
            Node node = nlist.item(i);
            NamedNodeMap map = node.getAttributes();
            if (map.getNamedItem("http-equiv") == null
            && map.getNamedItem("charset") == null) {
                continue;
            }

            Node subNode = map.getNamedItem("http-equiv");
            log__.debug("subNode ===> " + subNode);
            if (!subNode.getTextContent().equals("content-type")
                    && !subNode.getTextContent().equals("Content-Type")
                    && !subNode.getTextContent().equals("CONTENT-TYPE")) {
                continue;
            }

            subNode = map.getNamedItem("content");
            if (subNode == null) {
                subNode = map.getNamedItem("charset");
            }
            content = subNode.getTextContent();
            break;
        }
        log__.debug(content);
        //選択する文字コード
        List<String> charList = __getCharset();

        //文字コード取得
        for (String charCode : charList) {
            if (content.indexOf(charCode) >= 0) {
                charset = charCode;
                break;
            }
        }
        log__.debug("charset ===> " + charset);
        return charset;
    }

    /**
     * <br>[機  能] タイトル、コメント取得
     * <br>[解  説]
     * <br>[備  考]
     * @param el Element
     */
    private void __getTextData(Element el) {
        log__.debug("タイトル、コメント取得");

        //titleタグで分割する
        NodeList nlist = el.getElementsByTagName("title");
        log__.debug("nlist.getLength() ===> " + nlist.getLength());
        if (nlist.getLength() > 0) {
            Node node = nlist.item(0);
            title__ = node.getTextContent();
        }

        //metaタグで分割する
        nlist = el.getElementsByTagName("meta");
        for (int i = 0; i < nlist.getLength(); i++) {
            Node node = nlist.item(i);

            NamedNodeMap map = node.getAttributes();
            if (map.getNamedItem("name") == null) {
                continue;
            }

            Node subNode = map.getNamedItem("name");
            log__.debug("subNode.getTextContent() ===> " + subNode.getTextContent());
            if (!subNode.getTextContent().equals("description")) {
                continue;
            }

            if (map.getNamedItem("content") == null) {
                continue;
            }

            subNode = map.getNamedItem("content");
            log__.debug("subNode.getTextContent() ===> " + subNode.getTextContent());

            coment__ = subNode.getTextContent();
        }
    }

    /**
     * <br>[機  能] タイトル取得(HttpMethod_HTML解析)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param strUrl URL
     * @throws Exception 実行例外
     * @throws SQLException SQL実行例外
     * @throws MalformedURLException 実行例外
     * @throws IOException 実行例外
     */
    private void __getTitleFromUrlMet(Connection con, String strUrl)
    throws Exception, SQLException, MalformedURLException, IOException {
        log__.debug("タイトル取得(HttpMethod)");

        HttpOperation httpOperation = __createHttpOperation(con);

        String url = strUrl;
        int statusCode = -1;
        HttpResponseModel httpResMdl = null;
        try {
            HttpRequestModel httpReqMdl = new HttpRequestModel();

            httpResMdl = httpOperation.sendRequest(url, httpReqMdl, HttpOperation.METHOD_GET);
            statusCode = httpResMdl.getStatusCode();
        } catch (InterruptedException e) {
            log__.warn("タイトル取得に失敗(InterruptedException)", e);
            return;
        } catch (SocketTimeoutException e) {
            log__.warn("タイトル取得に失敗(SocketTimeoutException)", e);
            return;
        } catch (IOException e) {
            log__.warn("タイトル取得に失敗(IOException)", e);
            return;
        }

        if (statusCode != 200) {
            //取得失敗
            log__.warn("タイトル取得に失敗(IOException)");
            return;
        }

        //選択する文字コード
        String charSet = null;
        List<String> charList = __getCharset();

        //HTMLソース取得
        String html = "";
        InputStreamReader reader = null;
        BufferedReader bufferReader = null;
        try {
            reader = new InputStreamReader(httpResMdl.getBodyStream(), "JISAutoDetect");
            bufferReader = new BufferedReader(reader);

            String line;
            while ((line = bufferReader.readLine()) != null) {
                html += line + "\r\n";
            }
        } catch (SocketTimeoutException e) {
            log__.warn("タイトル取得に失敗(SocketTimeoutException)", e);
            return;
        } catch (IOException e) {
            log__.warn("タイトル取得に失敗(IOException)", e);
            return;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        //"charset"の位置を取得
        int posCon = -1;
        if (html.indexOf("charset") >= 0) {
            posCon = html.indexOf("charset");
        } else if (html.indexOf("CHARSET") >= 0) {
            posCon = html.indexOf("CHARSET");
        }

        //">"の位置を取得
        int posKako = html.indexOf(">", posCon);
        log__.debug("'charset'の位置 ===> " + posCon);
        log__.debug("'>'の位置       ===> " + posKako);
        //文字コード取得
        for (String charCode : charList) {
            int posChar = html.indexOf(charCode, posCon);
            if (posChar >= 0 && posChar > posCon && posChar < posKako) {
                charSet = charCode;
                break;
            }
        }

        //文字コード判別不能のとき
        if (charSet == null) {
            charSet = "JISAutoDetect";
        }
        log__.debug("文字コード ===> " + charSet);

        reader = null;
        try {
            reader = new InputStreamReader(httpResMdl.getBodyStream(), charSet);
        } catch (UnsupportedEncodingException e) {
            //ありえないので何もしない
        } catch (IOException e) {
            log__.warn("検索結果ファイルの分析に失敗", e);
            return;
        }

        //HTML解析（タイトル、コメント取得）
        BufferedReader in = new BufferedReader(reader);
        __readHtml(in);
    }

    /**
     * <br>[機  能] タイトル取得(HttpURLConnection)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param strUrl URL
     * @throws Exception 実行例外
     * @throws SQLException SQL実行例外
     * @throws MalformedURLException 実行例外
     * @throws IOException 実行例外
     */
    private void __getTitleFromUrlCon(Connection con, String strUrl)
    throws Exception, SQLException, MalformedURLException, IOException {
        log__.debug("タイトル取得(HttpURLConnection)");

        String url = strUrl;
        String proxyHost = null;
        int proxyPort = 0;
        List<String> urlList = new ArrayList<String>();

        //プロキシの設定情報を取得
        CmnContmDao contDao = new CmnContmDao(con);
        CmnContmModel contModel = contDao.select();
        if (contModel.getCntPxyAdrkbn() == GSConstMain.PROXY_SERVER_ADRKBN_EXISTADDRESS) {
            //プロキシを使用しないアドレス
            CmnProxyAddressDao adrDao = new CmnProxyAddressDao(con);
            urlList = Arrays.asList(adrDao.getAddressList());
        }

        if (contModel != null && contModel.getCntPxyUse() == GSConstMain.PROXY_SERVER_USE) {
            boolean notUseProxy = false;
            for (String noUseUrl : urlList) {
                if (url.indexOf(noUseUrl) >= 0) {
                    notUseProxy = true;
                    break;
                }
            }
            if (!notUseProxy) {
                proxyHost = contModel.getCntPxyUrl();
                proxyPort = contModel.getCntPxyPort();
            }
        }

        URL urlObj;
        HttpURLConnection urlCon = null;
        BufferedReader urlIn = null;

        boolean closeflg = false;

        try {

            boolean proxyAuth = false;
            //URLオブジェクトの作成
            if (proxyHost == null) {
                urlObj = new URL(url);
            } else {
                urlObj = new URL("http", proxyHost, proxyPort, url);
                proxyAuth = contModel.getCntPxyAuth() == GSConstMain.PROXY_SERVER_USERAUTH_AUTH;
            }

            //URL接続
            urlCon = (HttpURLConnection) urlObj.openConnection();
            urlCon.setRequestMethod("GET");
            urlCon.setUseCaches(false);
            HttpURLConnection.setFollowRedirects(true);
            urlCon.setInstanceFollowRedirects(true);
            urlCon.setRequestProperty("User-Agent", "Android Application");
//            urlCon.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
            urlCon.setConnectTimeout(GSConstBookmark.TIMEOUT);
            urlCon.setReadTimeout(GSConstBookmark.TIMEOUT);

            //プロキシサーバ ユーザ認証のユーザ、パスワードを設定
            if (proxyAuth) {
                String authStr = contModel.getCntPxyAuthId()
                                + ":"
                                + GSPassword.getEncryPassword(contModel.getCntPxyAuthPass());
                authStr = new String(Base64.encodeBase64(authStr.getBytes()));
                urlCon.setRequestProperty("Proxy-Authorization",
                                        "Basic " + authStr);
            }

            urlCon.connect();

            //サイトの存在チェック(503エラー)
            if (urlCon.getResponseCode() == HttpURLConnection.HTTP_UNAVAILABLE) {
                return;
            }

            //選択する文字コード
            String charSet = null;
            List<String> charList = __getCharset();

            //文字コード取得 HTTPヘッダのContent-Typeのcharsetを確認
            String contType = urlCon.getContentType();
            log__.debug("Content-Type ===> " + contType);
            for (String charCode : charList) {
                if (contType.indexOf(charCode) >= 0) {
                    charSet = charCode;
                    break;
                }
            }

            //文字コード取得 判別不能
            if (charSet == null) {
                //初回のとき、文字コードが取得できない場合は処理終了
                if (getTitleCount__ == 0) {
                    return;
                }
                charSet = "JISAutoDetect";
            }
            log__.debug("文字コード ===> " + charSet);

            //HTML解析（タイトル、コメント取得）
            urlIn = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charSet));
            __readHtml(urlIn);

            closeflg = true;

        } catch (SocketTimeoutException e) {
            log__.warn("タイトル取得に失敗(SocketTimeoutException)", e);
            return;
        } catch (MalformedURLException ex) {
            log__.warn("タイトル取得に失敗(MalformedURLException)", ex);
            return;
        } catch (IOException ex) {
            log__.warn("タイトル取得に失敗(IOException)", ex);
            return;
        } catch (Exception ex) {
            log__.warn("タイトル取得に失敗(Exception)", ex);
            return;
        } finally {
            //URL切断
            if (closeflg) {
                urlIn.close();
                urlCon.disconnect();
            }
        }
    }

    /**
     * <br>[機  能] 文字コード一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList
     */
    private List<String> __getCharset() {

        List<String> ret = new ArrayList<String>();
        ret.add("EUC-JP");
        ret.add("euc-jp");
        ret.add("UTF-8");
        ret.add("utf-8");
        ret.add("UTF-16");
        ret.add("utf-16");
        ret.add("Shift_JIS");
        ret.add("shift_jis");
        ret.add("WINDOWS-31J");
        ret.add("windows-31j");
        ret.add("ISO-2022-JP");
        ret.add("iso-2022-jp");
        ret.add("ISO8859_1");
        ret.add("iso8859_1");
        ret.add("MS932");
        ret.add("ms932");

        return ret;
    }

    /**
     * <br>[機  能] HTML解析（タイトル、コメント取得）
     * <br>[解  説]
     * <br>[備  考]
     * @param in BufferedReader
     * @throws IOException 実行例外
     */
    private void __readHtml(BufferedReader in) throws IOException {
        log__.debug("HTML解析:タイトル、コメント取得");

        title__ = "";
        coment__ = "";
        topFlgTitle__ = false;
        topFlgComent__ = false;
        titleOk__ = false;
        cmentOk__ = false;

        String line;
        while ((line = in.readLine()) != null) {
            //タイトル取得
            if (!titleOk__) {
                //<title>タグと</title>タグの位置を取得
                int posTop = __getTitleTagPos(0, line);
                int posEnd = __getTitleTagPos(1, line);
                //タイトル取り出し
                __getTitleFromHtml(line, posTop, posEnd);
            }
            //コメント取得
            if (!cmentOk__) {
                //<meta name="description" content="の位置を取得
                int posTop = __getComentTagPos(0, line);
                int posEnd = __getComentTagPos(1, line);
                //コメント取り出し
                __getComentFromHtml(line, posTop, posEnd);
            }
            //タイトル・コメント取得で処理終了
            if (titleOk__ && cmentOk__) {
                return;
            }
        }
    }

    /**
     * <br>[機  能] TITLEタグの位置を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 0:開始タグの位置を取得 1:終了タグの位置を取得
     * @param line HTMLの文字列
     * @return 位置
     */
    private int __getTitleTagPos(int kbn, String line) {

        int posTop = line.indexOf("<title");
        if (posTop < 0) {
            posTop = line.indexOf("<TITLE");
        }
        if (posTop < 0) {
            posTop = line.indexOf("<Title");
        }

        int posEnd = line.indexOf("</title>");
        if (posEnd < 0) {
            posEnd = line.indexOf("</TITLE>");
        }
        if (posEnd < 0) {
            posEnd = line.indexOf("</Title>");
        }

        if (kbn == 0) {
            return posTop;
        } else if (kbn == 1) {
            return posEnd;
        }

        return -1;
    }

    /**
     * <br>[機  能] コメントタグの位置を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 0:開始タグの位置を取得 1:終了タグの位置を取得
     * @param line HTMLの文字列
     * @return 位置
     */
    private int __getComentTagPos(int kbn, String line) {

        int posTop = -1;
        int posEnd = -1;
        int posTop1 = -1;
        int posTop2 = -1;
        int posTop3 = -1;
        int posTop4 = -1;
        int posTop5 = -1;

        char c = '"';
        Character character = Character.valueOf(c);
        String str = character.toString();

        posTop1 = line.indexOf("<meta");
        if (posTop1 >= 0) {
            posTop2 = line.indexOf("name", posTop1);
        }
        if (posTop2 >= 0) {
            posTop3 = line.indexOf("description", posTop2);
        }
        if (posTop3 >= 0) {
            posTop4 = line.indexOf("content", posTop3);
        }
        if (posTop4 >= 0) {
            posTop5 = line.indexOf(str, posTop4);
        }

        if (posTop1 >= 0 && posTop2 >= 0 && posTop3 >= 0 && posTop4 >= 0 && posTop5 >= 0) {
            posTop = posTop5 + 1;
            posEnd = line.indexOf(str, posTop5 + 1);
        }
        if (kbn == 0) {
            return posTop;
        } else if (kbn == 1) {
            return posEnd;
        }

        return -1;
    }

    /**
     * <br>[機  能] タイトルの取り出し
     * <br>[解  説]
     * <br>[備  考]
     * @param posTop TITLEタグ開始の位置
     * @param posEnd TITLEタグ終了の位置
     * @param line HTMLの文字列
     */
    private void __getTitleFromHtml(String line, int posTop, int posEnd) {

        if (posTop >= 0 && posEnd >= 0) {
            title__ = line.substring(line.indexOf(">", posTop) + 1, posEnd);
            titleOk__ = true;
        } else if (posTop >= 0 && posEnd < 0) {
            title__ = line.substring(line.indexOf(">", posTop) + 1);
            topFlgTitle__ = true;
        } else if (posTop < 0 && posEnd >= 0) {
            title__ += line.substring(0, posEnd);
            titleOk__ = true;
        } else if (topFlgTitle__) {
            title__ += line;
        }
    }

    /**
     * <br>[機  能] コメントの取り出し
     * <br>[解  説]
     * <br>[備  考]
     * @param posTop コメントタグ開始の位置
     * @param posEnd コメントタグ終了の位置
     * @param line HTMLの文字列
     */
    private void __getComentFromHtml(String line, int posTop, int posEnd) {

        //<meta name="description" content="の位置を取得
        if (posTop >= 0 && posEnd >= 0) {
            coment__ = line.substring(posTop, posEnd);
            cmentOk__ = true;
        } else if (posTop >= 0 && posEnd < 0) {
            coment__ = line.substring(posTop);
            topFlgComent__ = true;
        } else if (posTop < 0 && posEnd >= 0) {
            coment__ += line.substring(0, posEnd);
            cmentOk__ = true;
        } else if (topFlgComent__) {
            coment__ += line;
        }
    }

    private HttpOperation __createHttpOperation(Connection con)
    throws SQLException, EncryptionException {
        int bmlTimeout = GSConstBookmark.TIMEOUT / 1000;
        return new HttpOperation(con, bmlTimeout);
    }
}