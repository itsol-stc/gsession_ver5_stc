package jp.groupsession.v2.ptl.ptl100kn;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.HtmlBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.dao.PtlPortletImageDao;
import jp.groupsession.v2.ptl.dao.PtlPortletSortDao;
import jp.groupsession.v2.ptl.model.PtlPortletImageModel;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.ptl100.Ptl100Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル ポートレット登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl100knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl100knBiz.class);
    /** Connection */
    private Connection con__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     */
    Ptl100knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行時例外
     */
    public void initDsp(
            Ptl100knParamModel paramMdl,
            RequestModel reqMdl,
            String appRootPath) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.notset");

        //カテゴリ名を設定
        PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con__);
        paramMdl.setPtl100knCategoryName(NullDefault.getString(
                dao.select(paramMdl.getPtl100category()).getPlcName(), msg));

        if (paramMdl.getPtl100contentType() == Ptl100knForm.PTL100_CONTENTTYPE_URL) {
            paramMdl.setPtl100knContentUrl(__createContentUrl(paramMdl));
        }

        //説明を表示用に整形
        paramMdl.setPtl100knDescription(
            StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getPtl100description()));

        if (!StringUtil.isNullZeroString(paramMdl.getPtl100content())) {
            //javascriptの場合(chromeキャッシュ対策)
            String content = paramMdl.getPtl100content();

            //URLが含まれていた場合(chromeキャッシュ対策)
            content = __addUrlPrm(content);
            paramMdl.setPtl100knContent(content);
        }

        if (!StringUtil.isNullZeroString(paramMdl.getPtl100contentHtml())) {
            //javascriptの場合(chromeキャッシュ対策)
            String content = paramMdl.getPtl100contentHtml();

            //HTML入力の場合、GS予約語が含まれていた場合、置換する
            if (paramMdl.getPtl100contentType() == Ptl100knForm.PTL100_CONTENTTYPE_HTML) {
                CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con__);
                CmnUsrmInfModel usrInfMdl = usrInfDao.select(reqMdl.getSmodel().getUsrsid());
                CommonBiz cmnBiz = new CommonBiz();
                content = cmnBiz.replaceGSReservedWordNoTime(
                        reqMdl, con__, appRootPath, content, usrInfMdl, true);

            }

            //URLが含まれていた場合(chromeキャッシュ対策)
            content = __addUrlPrm(content);
            paramMdl.setPtl100knContentHtml(content);
        }
    }

    /**
     * <br>[機  能] タグのURLにパラメータを付与します。
     * <br>[解  説]
     * <br>[備  考] タグからURLが入る属性を見つけ、パラメータを付与します。
     * @param content 本文
     * @return タグのURLにパラメータが付与された本文
     */
    private String __addUrlPrm(String content) {

        //タグとURLが入る属性のハッシュマップ
        HashMap<String, String> tagAttrMap = __getUriTagAttrMap();

        for (String tagName : tagAttrMap.keySet()) {

            //タグの正規表現
            String regexTag = "<" + tagName + " [^>]*>";

            //属性名
            String attrName = tagAttrMap.get(tagName);

            //属性の正規表現
            String regexAttr = " " + attrName + " *= *(\"[^>]*\"|'[^>]*')";

            //タグのパターン
            Pattern ptTag = Pattern.compile(regexTag, Pattern.CASE_INSENSITIVE);
            //属性のパターン
            Pattern ptAttr = Pattern.compile(regexAttr, Pattern.CASE_INSENSITIVE);

            //URLにパラメータを付与します
            content = __addTagAttrPrm(content, ptTag, ptAttr);
        }

        return content;
    }

    /**
     * <br>[機  能] 指定されたタグの属性にあるURLにパラメータを付与します。
     * <br>[解  説]
     * <br>[備  考]
     * @param content 本文
     * @param ptTag タグパターン
     * @param ptAttr 属性パターン
     * @return content タグのURLにパラメータが付与された本文
     */
    private String __addTagAttrPrm(String content, Pattern ptTag, Pattern ptAttr) {

        //StringBuffer 本文再構成用
        StringBuffer sb;

        int findIdx = 0;
        while (true) {
            //本文からタグを検索する
            Matcher mTag = ptTag.matcher(content);
            boolean findResultTag = mTag.find(findIdx);

            if (!findResultTag) {
                //タグがなければ処理終了
                break;
            }

            //タグ文字列を取得
            String tagStr = mTag.group();

            //タグから対象の属性を検索する
            Matcher mAttr = ptAttr.matcher(tagStr);
            boolean findResultAttr = mAttr.find();

            if (!findResultAttr) {
                //属性がなければ次のタグへ
                sb = new StringBuffer();
                sb.append(content.substring(0, mTag.start()));
                sb.append(tagStr);
                findIdx = sb.toString().length();
                continue;
            }

            //属性文字列にパラメータを付与
            String convString = Matcher.quoteReplacement(mAttr.group());
            String attrStr = StringUtil.getLinkAddPrm(convString);

            //タグに属性の変更を反映
            tagStr = mAttr.replaceFirst(attrStr);

            //タグを本文に反映
            sb = new StringBuffer();
            sb.append(content.substring(0, mTag.start()));
            sb.append(tagStr);
            findIdx = sb.toString().length();
            sb.append(content.substring(mTag.end(), content.length()));
            content = sb.toString();
        }

        return content;
    }

    /**
     * <br>[機  能] タグとURLが入る属性のHashMapを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @return タグとURLが入る属性のHashMap
     */
    private static HashMap<String, String> __getUriTagAttrMap() {

        //<タグ, 属性>のハッシュマップ
        HashMap<String, String> tagAttrMap = new HashMap<String, String>();

        tagAttrMap.put("a", "href");
        tagAttrMap.put("area", "href");
        tagAttrMap.put("link", "href");
        tagAttrMap.put("applet", "codebase");
        tagAttrMap.put("base", "href");
        tagAttrMap.put("blockquote", "cite");
        tagAttrMap.put("q", "cite");
        tagAttrMap.put("body", "background");
        tagAttrMap.put("del", "cite");
        tagAttrMap.put("ins", "cite");
        tagAttrMap.put("form", "action");
        tagAttrMap.put("frame", "longdesc");
        tagAttrMap.put("frame", "src");
        tagAttrMap.put("iframe", "longdesc");
        tagAttrMap.put("iframe", "src");
        tagAttrMap.put("head", "profile");
        tagAttrMap.put("img", "longdesc");
        tagAttrMap.put("img", "src");
        tagAttrMap.put("img", "usemap");
        tagAttrMap.put("input", "usemap");
        tagAttrMap.put("input", "src");
        tagAttrMap.put("object", "usemap");
        tagAttrMap.put("object", "classid");
        tagAttrMap.put("object", "codebase");
        tagAttrMap.put("object", "data");
        tagAttrMap.put("script", "src");

        return tagAttrMap;
    }

    /**
     * <br>[機  能] ポートレットの登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param controller MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @param mode 処理モード
     * @throws Exception 実行時例外
     */
    public void registPtl(
            Ptl100knParamModel paramMdl,
            MlCountMtController controller,
            String appRootPath,
            RequestModel reqMdl,
            int mode) throws Exception {

        //ポートレットモデルを作成
        PtlPortletModel pltMdl
            = __createPltModel(paramMdl, controller, reqMdl.getSmodel().getUsrsid(), mode);

        //更新前のポートレット_画像情報を削除する
        if (mode == GSConstPortal.CMD_MODE_EDIT) {
            PtlCommonBiz ptlCmnBiz = new PtlCommonBiz();
            ptlCmnBiz.deletePortletImage(pltMdl.getPltSid(), pltMdl.getPltEuid(), con__);
        }

        if (pltMdl.getPltContentType() == Ptl100knForm.PTL100_CONTENTTYPE_INPUT) {
            //「文章を入力」の場合、内容欄に挿入されたファイル情報を登録する
            String content = pltMdl.getPltContent();

            content = __insertPortletContentImageData(
                            pltMdl.getPltSid(),
                            content,
                            controller,
                            appRootPath,
                            reqMdl);

            pltMdl.setPltContent(content);

        } else if (pltMdl.getPltContentType() == Ptl100knForm.PTL100_CONTENTTYPE_HTML) {
            //「HTMLを入力」の場合、画像登録により登録された画像ファイル情報を登録する
            String content = pltMdl.getPltContent();

            content = __insertPortletImageData(
                            pltMdl.getPltSid(),
                            content,
                            controller,
                            appRootPath,
                            reqMdl);

            pltMdl.setPltContent(content);
        }

        //ポートレットを登録or更新
        if (mode == GSConstPortal.CMD_MODE_ADD) {
            //登録
            __insert(pltMdl);
            log__.debug("// ポートレットを登録しました。");
        }

        if (mode == GSConstPortal.CMD_MODE_EDIT) {
            //更新
            __update(pltMdl, paramMdl);
            log__.debug("// ポートレットを更新しました。");
        }

    }

    /**
     * <br>[機  能] ポートレットモデルを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param controller MlCountMtController
     * @param userSid ログインユーザSID
     * @param mode 処理モード
     * @return PtlPortletModel
     * @throws Exception Exception
     */
    private PtlPortletModel __createPltModel(
            Ptl100knParamModel paramMdl,
            MlCountMtController controller,
            int userSid,
            int mode) throws Exception {

        log__.debug("ポートレットモデルを作成します。");
        PtlPortletModel mdl = new PtlPortletModel();

        int pltSid;

        //もし処理モードが追加なら、
        if (paramMdl.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
            log__.debug("//採番マスタからポートレットSIDを取得。");
            pltSid = (int) controller.getSaibanNumber(GSConstPortal.PLUGIN_ID,
                                                      GSConstPortal.SBNSID_SUB_PORTLET,
                                                      userSid);
        } else {
            log__.debug("//現在選択されているポートレットのSIDを取得。");
            pltSid = paramMdl.getPtlPortletSid();
        }

        UDate now = new UDate();


        mdl.setPltSid(pltSid);
        mdl.setPltName(NullDefault.getString(paramMdl.getPtl100name(), ""));

        if (paramMdl.getPtl100contentType() == Ptl100knForm.PTL100_CONTENTTYPE_URL) {
            mdl.setPltContent(__createContentUrl(paramMdl));
            mdl.setPltContentType(Ptl100knForm.PTL100_CONTENTTYPE_URL);
            mdl.setPltContentUrl(paramMdl.getPtl100contentUrl());
        } else if (paramMdl.getPtl100contentType() == Ptl100knForm.PTL100_CONTENTTYPE_INPUT) {
            mdl.setPltContent(paramMdl.getPtl100content());
            mdl.setPltContentType(Ptl100knForm.PTL100_CONTENTTYPE_INPUT);
        } else {
            mdl.setPltContent(paramMdl.getPtl100contentHtml());
            mdl.setPltContentType(Ptl100knForm.PTL100_CONTENTTYPE_HTML);
        }
        mdl.setPltDescription(paramMdl.getPtl100description());
        mdl.setPlcSid(paramMdl.getPtl100category());
        mdl.setPltBorder(paramMdl.getPtl100border());
        mdl.setPltAuid(userSid);
        mdl.setPltAdate(now);
        mdl.setPltEuid(userSid);
        mdl.setPltEdate(now);
        return mdl;
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param model モデル
     * @throws SQLException SQL実行時例外
     */
    private void __insert(PtlPortletModel model)
    throws SQLException {

        //ポートレット情報と並び順登録
        PtlPortletDao dao = new PtlPortletDao(con__);
        PtlPortletSortDao sortDao = new PtlPortletSortDao(con__);
        dao.insert(model);
        sortDao.insertMaxSort(model.getPltSid());

    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考] カテゴリ間の移動は更新処理でしか起こり得ないので
     *              元の所属カテゴリSIDと編集後の所属カテゴリSIDを比
     *              べて、カテゴリ間の移動があるか判定している。<br>
     *              カテゴリ間の移動がない場合は並び順の更新はしない。
     * @param model モデル
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    private void __update(PtlPortletModel model, Ptl100knParamModel paramMdl)
    throws SQLException {

        PtlPortletDao dao = new PtlPortletDao(con__);
        PtlPortletSortDao sortDao = new PtlPortletSortDao(con__);

        int motoCatSid = dao.select(paramMdl.getPtlPortletSid()).getPlcSid();
        int updateCatSid = paramMdl.getPtl100category();

        //ポートレット情報登録
        dao.update(model);

        //カテゴリ間の移動がある場合に、並び順更新
        if (motoCatSid != updateCatSid) {
            //並び順削除後追加
            sortDao.delete(model.getPltSid());
            sortDao.insertMaxSort(model.getPltSid());
        }

    }

    /**
     * <br>[機  能] 内容 URLのHTMLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return HTML
     */
    private String __createContentUrl(Ptl100knParamModel paramMdl) {

        String content = "<iframe border=\"0\" frameborder=\"0\" scrolling=\"yes\"";
        content += " width=\"100%\" height=\"600\"";
        content += " src=\"" + paramMdl.getPtl100contentUrl() + "\"></iframe>";

        return content;
    }

    /**
     * <br>[機  能] ポートレット 画像情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param bodyValue 内容
     * @param ctrl MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエストモデル
     * @return 変換後の内容
     * @throws IOException ファイル情報読み込み時にエラー
     * @throws SQLException SQL実行時例外
     * @throws TempFileException ファイル情報登録時にエラー発生
     * @throws TransformerException 内容解析時にエラー発生
     * @throws SAXException 内容解析時にエラー発生
     * @throws ParserConfigurationException 内容解析時にエラー発生
     * @throws Exception 例外発生
     */
    private String __insertPortletImageData(
            int pltSid, String bodyValue,
            MlCountMtController ctrl,
            String appRootPath,
            RequestModel reqMdl)
                    throws IOException,
                            SQLException,
                            Exception {

        //imgタグに指定された編集用パラメータを除去する
        if (!StringUtil.isNullZeroString(bodyValue)) {
            bodyValue = bodyValue.replaceAll(
                    Pattern.quote("<img src=\"../pltimage/ptl990.do"
                                    + "?ptlPortletSid=$PORTLET_SID"),
                    "<img src=\"../pltimage/ptl990.do?ptlPortletSid=" + pltSid);
        }

        //添付情報を取得し、バイナリー情報に登録
        PtlPortletImageDao pltImageDao = new PtlPortletImageDao(con__);
        PtlPortletImageModel pltImgMdl = new PtlPortletImageModel();
        pltImgMdl.setPltSid(pltSid);

        UDate now = new UDate();
        CommonBiz cmnBiz = new CommonBiz();
        Ptl100Biz biz100 = new Ptl100Biz();
        String imgTempDir = biz100.getPortletImgTempDir(reqMdl);
        List<File> imgDirList = IOTools.getDirs(imgTempDir);
        for (File imgDirPath : imgDirList) {
            List <String> bodyBinSid = cmnBiz.insertBinInfo(
                    con__, imgDirPath.getPath(),
                    appRootPath, ctrl, 
                    reqMdl.getSmodel().getUsrsid(), now);

            //ポートレット 画像情報の登録
            if (bodyBinSid != null && bodyBinSid.size() > 0) {
                pltImgMdl.setPliSid(Long.parseLong(imgDirPath.getName()));
                pltImgMdl.setBinSid(Long.parseLong(bodyBinSid.get(0)));

                List<TempFileModel> tempMdlList = cmnBiz.getTempFiles(imgDirPath.getPath());
                pltImgMdl.setPliName(tempMdlList.get(0).getFileName());

                pltImageDao.insert(pltImgMdl);
            }
        }

        return bodyValue;
    }

    /**
     * <br>[機  能] ポートレット 内容欄に挿入された画像情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param bodyValue 内容
     * @param ctrl MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエストモデル
     * @return 変換後の内容
     * @throws IOException ファイル情報読み込み時にエラー
     * @throws SQLException SQL実行時例外
     * @throws TempFileException ファイル情報登録時にエラー発生
     * @throws TransformerException 内容解析時にエラー発生
     * @throws SAXException 内容解析時にエラー発生
     * @throws ParserConfigurationException 内容解析時にエラー発生
     * @throws Exception 例外発生
     */
    private String __insertPortletContentImageData(
            int pltSid, String bodyValue,
            MlCountMtController ctrl,
            String appRootPath,
            RequestModel reqMdl)
                    throws IOException,
                            SQLException,
                            Exception {
        String ret = "";
        String startKey = "ptl100.do?CMD=getBodyFile";

        if (bodyValue == null
            || bodyValue.length() < 1
            || bodyValue.indexOf(startKey) < 0) {
            return bodyValue;
        }

        bodyValue = StringUtilHtml.replaceString(bodyValue, "&", "&amp;");
        bodyValue = StringUtilHtml.replaceString(bodyValue, "&amp;amp;", "&amp;");
        bodyValue = StringUtilHtml.replaceString(bodyValue, "&amp;lt;", "&lt;");
        bodyValue = StringUtilHtml.replaceString(bodyValue, "&amp;gt;", "&gt;");
        bodyValue = StringUtilHtml.replaceString(bodyValue, "&amp;quot;", "&quot;");
        bodyValue = StringUtilHtml.replaceString(bodyValue, "&amp;nbsp;", "&nbsp;");

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html[");

        //Entityを設定
        HtmlBiz htmlBiz = new HtmlBiz();
        sb.append(htmlBiz.getHtmlEntity());

        sb.append("]>");

        String bodyHeader = "<root>";
        String bodyFooter = "</root>";
        if (!StringUtil.isNullZeroString(bodyValue)) {
            StringBuffer bodySb = new StringBuffer();
            for (int index = 0; index < bodyValue.length(); index++) {
                char c = bodyValue.charAt(index);
                if ((c >= 0x00 && c <= 0x08)
                        || (c >= 0x0B && c <= 0x0C)
                        || (c >= 0x0E && c <= 0x1F)) {
                    
                    bodySb.append("");
                } else {
                    bodySb.append(c);
                }
            }
            bodyValue = bodySb.toString();
        }

        sb.append(bodyHeader);
        sb.append(bodyValue);
        sb.append(bodyFooter);
        bodyValue = sb.toString();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        ByteArrayInputStream baiStream = null;
        StringWriter strWriter = null;
        ArrayList<String> bodyFileDirList = new ArrayList<String>();
        ArrayList<String> tempDirList = new ArrayList<String>();
        Ptl100Biz biz100 = new Ptl100Biz();

        try {
            baiStream = new ByteArrayInputStream(bodyValue.getBytes("UTF-8"));
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(baiStream);

            //<img>タグのsrcを取得
            NodeList imgNodeList = doc.getElementsByTagName("img");

            if (imgNodeList != null && imgNodeList.getLength() > 0) {
                PtlPortletImageDao pltImageDao = new PtlPortletImageDao(con__);
                PtlPortletImageModel pltImgMdl = new PtlPortletImageModel();
                pltImgMdl.setPltSid(pltSid);

                List <String> bodyBinSidList = new ArrayList<String>();
                String bodyFileTempDir = null;
                CommonBiz cmnBiz = new CommonBiz();
                UDate now = new UDate();
                for (int i = 0; i < imgNodeList.getLength(); ++i) {
                    Node imgNode = imgNodeList.item(i);
                    if (imgNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    NamedNodeMap imgNodeAttrMap = imgNode.getAttributes();
                    Node srcAttr = imgNodeAttrMap.getNamedItem("src");
                    if (srcAttr == null) {
                        continue;
                    }
                    String srcStr = srcAttr.getNodeValue();
                    if (srcStr == null || srcStr.length() < 1) {
                        continue;
                    }
                    if (!srcStr.startsWith(startKey)) {
                        continue;
                    }
                    int idxOfIdStart = srcStr.lastIndexOf("=");
                    String idStr = srcStr.substring(idxOfIdStart + 1);
                    if (idStr.indexOf("&nowTime") > 0) {
                        idStr = idStr.substring(0, idStr.indexOf("&nowTime"));
                    }
                    //添付情報を取得し、バイナリー情報に登録
                    bodyFileTempDir = biz100.getPortletBodyTempDir(reqMdl, idStr);
                    bodyFileDirList.add(idStr);
                    tempDirList.add(bodyFileTempDir);
                    List <String> bodyBinSid = cmnBiz.insertBinInfo(
                            con__, bodyFileTempDir, appRootPath, ctrl, 
                            reqMdl.getSmodel().getUsrsid(), now);

                    if (bodyBinSid != null && bodyBinSid.size() > 0) {
                        bodyBinSidList.add(bodyBinSid.get(0));
                        srcAttr.setNodeValue("../pltimage/ptl990.do"
                                + "?ptlPortletSid=" + String.valueOf(pltSid)
                                + "&imgId=" + idStr);
                    }

                    //ポートレット 画像情報の登録
                    if (bodyBinSid != null && bodyBinSid.size() > 0) {
                        pltImgMdl.setPliSid(Long.parseLong(idStr));
                        pltImgMdl.setBinSid(Long.parseLong(bodyBinSid.get(0)));

                        List<TempFileModel> tempMdlList = cmnBiz.getTempFiles(bodyFileTempDir);
                        pltImgMdl.setPliName(tempMdlList.get(0).getFileName());

                        pltImageDao.insert(pltImgMdl);
                    }
                }

            }
            //解析・変更した文を実行結果として返す
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            strWriter = new StringWriter();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(strWriter));
            String strDoc = strWriter.toString();

            int valueStart = strDoc.indexOf(bodyHeader);
            int valueEnd = strDoc.lastIndexOf(bodyFooter);
            strDoc = strDoc.substring(valueStart + bodyHeader.length(), valueEnd);

            ret = strDoc;

        } catch (Exception e) {
            log__.error("HTMLの読み取りに失敗しました。", e);
            throw e;
        } finally {
            if (strWriter != null) {
                strWriter.close();
            }
            if (baiStream != null) {
                baiStream.close();
            }
        }

        return ret;
    }

}
