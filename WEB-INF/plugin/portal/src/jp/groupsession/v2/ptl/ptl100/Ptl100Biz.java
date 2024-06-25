package jp.groupsession.v2.ptl.ptl100;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.dao.PtlPortletImageDao;
import jp.groupsession.v2.ptl.dao.PtlPortletSortDao;
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;
import jp.groupsession.v2.ptl.model.PtlPortletImageModel;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル ポートレット登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl100Biz {

    /** 画面ID */
    private static final String SCR_ID__ = "ptl100";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl100Biz.class);

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @throws Exception 例外
     */
    public void setInitData(Ptl100ParamModel paramMdl,
                            Connection con,
                            String appRootPath,
                            RequestModel reqMdl) throws Exception {

        if (paramMdl.getPtl100init() != 1) {
            log__.debug("初期表示");

            //テンポラリディレクトリ生成
            deletePortletTempDir(reqMdl);
            IOTools.isDirCheck(getPortletBodyTempDir(reqMdl), true);
            IOTools.isDirCheck(getPortletImgTempDir(reqMdl), true);

            //編集の場合、データ取得
            if (paramMdl.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
                int pltSid = paramMdl.getPtlPortletSid();
                PtlPortletDao dao = new PtlPortletDao(con);
                PtlPortletModel model = dao.select(pltSid);
                paramMdl.setPtl100name(model.getPltName());
                paramMdl.setPtl100contentType(model.getPltContentType());

                String content = model.getPltContent();
                if (model.getPltContentType() == Ptl100Form.PTL100_CONTENTTYPE_URL) {
                    paramMdl.setPtl100content(model.getPltContent());
                } else {
                    //ポートレット画像情報をテンポラリディレクトリへコピー
                    PtlPortletImageDao pltImageDao = new PtlPortletImageDao(con);
                    List<PtlPortletImageModel> pltImgList
                        = pltImageDao.getImageList(pltSid);

                    __bodyTempFileCopy(pltImgList, appRootPath, con, reqMdl,
                                        model.getPltContentType());

                    if (model.getPltContentType() == Ptl100Form.PTL100_CONTENTTYPE_INPUT) {
                        //内容欄の画像ファイqwルリンクを編集用に変換する
                        content = __setPortletBinLink(pltImgList, pltSid, content);
                        paramMdl.setPtl100content(content);
                    } else if (model.getPltContentType() == Ptl100Form.PTL100_CONTENTTYPE_HTML) {
                        content = this.__setPortletImageLink(pltSid, content);
                        paramMdl.setPtl100contentHtml(content);
                    }

                }
                paramMdl.setPtl100border(model.getPltBorder());
                paramMdl.setPtl100category(model.getPlcSid());
                paramMdl.setPtl100contentUrl(model.getPltContentUrl());
                paramMdl.setPtl100description(model.getPltDescription());

            } else if (paramMdl.getPtl090svCategory() > 0) {
                paramMdl.setPtl100category(paramMdl.getPtl090svCategory());
            }

            paramMdl.setPtl100init(1);
        }

        //画像追加がある場合は内容の最後に追加する。
        String sltImage = paramMdl.getPtl100contentPlusImage();
        if (!StringUtil.isNullZeroString(sltImage)) {

            if (paramMdl.getPtl100contentType() == Ptl100Form.PTL100_CONTENTTYPE_HTML) {
                paramMdl.setPtl100contentHtml(paramMdl.getPtl100contentHtml() + sltImage);
            } else {
                paramMdl.setPtl100content(paramMdl.getPtl100content() + sltImage);
            }
            paramMdl.setPtl100contentPlusImage(null);
        }

        //カテゴリコンボ作成
        createCatComb(paramMdl, con, reqMdl);

        //内容 種別 = HTMLを入力 の場合、画像の一覧を取得する
        if (paramMdl.getPtl100contentType() == Ptl100Form.PTL100_CONTENTTYPE_HTML) {
            int portletSid = paramMdl.getPtlPortletSid();

            CommonBiz cmnBiz = new CommonBiz();
            List<PtlPortletImageModel> dspList = new ArrayList<PtlPortletImageModel>();
            String imgTempDir = getPortletImgTempDir(reqMdl);
            List<File> imgDirList = IOTools.getDirs(imgTempDir);
            for (File imgDirPath : imgDirList) {
                PtlPortletImageModel pltImgMdl = new PtlPortletImageModel();
                pltImgMdl.setPltSid(portletSid);
                pltImgMdl.setPliSid(Long.parseLong(imgDirPath.getName()));

                List<TempFileModel> tempMdlList = cmnBiz.getTempFiles(imgDirPath.getPath());
                pltImgMdl.setPliName(tempMdlList.get(0).getFileName());

                dspList.add(pltImgMdl);
            }
            paramMdl.setPtl100ImageList(dspList);
        }

    }

    /**
     * <br>[機  能] ポートレットカテゴリコンボボックスを作る
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void createCatComb(Ptl100ParamModel paramMdl, Connection con,
                              RequestModel reqMdl) throws SQLException {

        PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con);
        ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String misettei = gsMsg.getMessage("cmn.notset");

        List<PtlPortletCategoryModel> mdlList = dao.selectSortAll();
        //初期値 全て
        list.add(new LabelValueBean(misettei, "0"));
        for (PtlPortletCategoryModel model : mdlList) {
            String strName = model.getPlcName();
            String strSid = Integer.toString(model.getPlcSid());
            list.add(new LabelValueBean(strName, strSid));
        }
        paramMdl.setPtl100CategoryList(list);
    }

    /**
     * <br>[機  能] ポートレットの削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param pltSid ポートレットSID
     * @param con Connection
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 例外
     */
    public void deletePortlet(Ptl100ParamModel paramMdl,
                             int pltSid, Connection con,
                             int sessionUserSid) throws Exception {

        PtlPortletDao dao = new PtlPortletDao(con);
        PtlPortletSortDao sortDao = new PtlPortletSortDao(con);
        PtlPortalPositionDao positionDao = new PtlPortalPositionDao(con);

        //ソート順の更新を行う
        sortDao.delete(pltSid);
        //ポートレットの削除
        dao.delete(pltSid);
        //ポートレット位置情報の削除
        positionDao.deletePortlet(pltSid);

        //ポートレット画像の削除
        deletePortletImage(paramMdl, con, pltSid);
    }

    /**
     * <br>[機  能] ポートレットSIDからポートレット名を取得し、メッセージを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param ptlSid ポートレットSID
     * @param msgRes MessageResources
     * @param reqMdl リクエスト情報
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeletePltMsg(Connection con, int ptlSid,
                                  MessageResources msgRes, RequestModel reqMdl)
    throws SQLException {

        String msg = "";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String portlet = gsMsg.getMessage("ptl.3");

        //ポートレット名取得
        PtlPortletDao dao = new PtlPortletDao(con);
        PtlPortletModel mdl = dao.select(ptlSid);
        String catName = StringUtilHtml.transToHTmlPlusAmparsant(
                         NullDefault.getString(mdl.getPltName(), ""));

        msg = msgRes.getMessage("sakujo.kakunin.list", portlet, catName);

        return msg;
    }

    /**
     * <br>[機  能] ポートレット_画像情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deletePortletImage(Ptl100ParamModel paramMdl,
                            Connection con, int usrSid) throws SQLException {
        int pltSid = paramMdl.getPtlPortletSid();
        PtlPortletImageDao imageDao = new PtlPortletImageDao(con);
        imageDao.updateImgFlg(pltSid);
    }

    /**
     * <br>[機  能] 画像情報ファイル(保存先ディレクトリ)を削除する。
     * <br>[解  説] 「画像毎に」作成されたディレクトリを削除
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     */
    public void deleteContentImageFile(Ptl100ParamModel paramMdl, RequestModel reqMdl) {

        long imgSid = paramMdl.getPltPortletImageSid();
        String imgTempDir = getPortletImgTempDir(reqMdl, String.valueOf(imgSid));

        IOTools.deleteDir(imgTempDir);
    }

    /**
     * <br>[機  能] ポートレット 添付ファイル保存先ディレクトリの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deletePortletTempDir(RequestModel reqMdl) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(reqMdl,
                GSConstPortal.PLUGIN_ID, SCR_ID__);
    }

    /**
     * <br>[機  能] ポートレット 内容の挿入ファイル保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ポートレット 内容の挿入ファイル保存先ディレクトリパス
     */
    public String getPortletBodyTempDir(RequestModel reqMdl) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String saveDir = temp.getTempPath(reqMdl,
                GSConstPortal.PLUGIN_ID, SCR_ID__,
                GSConstCommon.EDITOR_BODY_FILE);

        return saveDir;
    }

    /**
     * <br>[機  能] ポートレット 内容の挿入ファイル保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param saveTempDirId ファイル別ディレクトリID
     * @return ポートレット 内容の挿入ファイル保存先ディレクトリパス
     */
    public String getPortletBodyTempDir(RequestModel reqMdl, String saveTempDirId) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String saveDir = temp.getTempPath(reqMdl,
                GSConstPortal.PLUGIN_ID, SCR_ID__,
                GSConstCommon.EDITOR_BODY_FILE,
                saveTempDirId);

        return saveDir;
    }

    /**
     * <br>[機  能] ポートレット 画像ファイル保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ポートレット 画像ファイル保存先ディレクトリパス
     */
    public String getPortletImgTempDir(RequestModel reqMdl) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String saveDir = temp.getTempPath(reqMdl,
                GSConstPortal.PLUGIN_ID, SCR_ID__,
                "imgFile");

        return saveDir;
    }

    /**
     * <br>[機  能] ポートレット 画像ファイル保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param saveTempDirId ファイル別ディレクトリID
     * @return ポートレット 画像ファイル保存先ディレクトリパス
     */
    public String getPortletImgTempDir(RequestModel reqMdl, String saveTempDirId) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String saveDir = temp.getTempPath(reqMdl,
                GSConstPortal.PLUGIN_ID, SCR_ID__,
                "imgFile",
                saveTempDirId);

        return saveDir;
    }

    /**
     * <br>[機  能] 内容に挿入された画像ファイルリンクを編集用に書き換える
     * <br>[解  説] 「文章を入力」用
     * <br>[備  考]
     * @param binList 添付ファイルリスト
     * @param pltSid ポートレットSID
     * @param content 内容
     * @return 編集用の内容
     */
    private String __setPortletBinLink(List<PtlPortletImageModel> binList,
                                        int pltSid, String content) {

        String linkStr = "src=\"../pltimage/ptl990.do"
                + "?ptlPortletSid=" + String.valueOf(pltSid);

        if (content != null && content.indexOf(linkStr) >= 0
            && binList != null && !binList.isEmpty()) {

            UDate now = new UDate();
            //添付ファイルがあった場合に、説明文のimgタグの書き換えが発生
            for (PtlPortletImageModel pltImgMdl : binList) {
                long pliSid = pltImgMdl.getPliSid();

                String beforeBody = linkStr + "&amp;imgId=";
                beforeBody += pliSid;
                String afterBody = "src=\"ptl100.do?CMD=getBodyFile&amp;ptl100TempSaveId=";
                afterBody += pliSid;
                afterBody += "&amp;nowTime";
                afterBody += now;

                content = content.replace(beforeBody, afterBody);
            }
        }

        return content;
    }

    /**
     * <br>[機  能] 内容に挿入された画像ファイルリンクを編集用に書き換える
     * <br>[解  説] 「HTMLを入力」用
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param content 内容
     * @return 編集用の内容
     */
    private String __setPortletImageLink(int pltSid, String content) {

        String linkStr = "<img src=\"../pltimage/ptl990.do"
                        + "?ptlPortletSid=";

        String beforeStr = linkStr + pltSid;
        String afterStr = linkStr + "$PORTLET_SID";

        while (content.indexOf(beforeStr) >= 0) {
            content = content.replace(beforeStr, afterStr);
        }

        return content;
    }

    /**
     * <br>[機  能] ポートレット画像情報をテンポラリディレクトリにコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param binList 添付ファイルリスト
     * @param appRootPath アプリケーションルート
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param contentType ポートレット種別
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __bodyTempFileCopy(List<PtlPortletImageModel> binList,
                                 String appRootPath,
                                 Connection con,
                                 RequestModel reqMdl,
                                 int contentType)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        int i = 1;

        String tempDir = "";
        if (contentType == Ptl100Form.PTL100_CONTENTTYPE_INPUT) {
            tempDir = getPortletBodyTempDir(reqMdl);
        } else if (contentType == Ptl100Form.PTL100_CONTENTTYPE_HTML) {
            tempDir = getPortletImgTempDir(reqMdl);
        }

        for (PtlPortletImageModel pltBinMdl : binList) {
            String dir = tempDir 
                    + File.separator 
                    + pltBinMdl.getPliSid()
                    + File.separator;
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, pltBinMdl.getBinSid(), reqMdl.getDomain());
            if (binMdl != null) {
                //添付ファイルをテンポラリディレクトリにコピーする。
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, dir, i);
                i++;
            }
        }
    }
}
