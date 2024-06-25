package jp.groupsession.v2.zsk.zsk050kn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.dao.WkZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.model.ZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiInfoModel;
import jp.groupsession.v2.zsk.zsk050.Zsk050Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 在席管理 座席表編集確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk050knBiz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Zsk050knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Zsk050knParamModel
     * @param con コネクション
     * @param tempPath 添付用ディレクトリパス
     * @param appRoot アプリケーションRoot
     * @return Zsk050knForm アクションフォーム
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイル書込み時例外
     * @throws IOToolsException ファイル書込み時例外
     * @throws FileNotFoundException ファイル書込み時例外
     * @throws UnsupportedEncodingException エンコード例外
     */
    public Zsk050knParamModel getInitData(
            Zsk050knParamModel paramMdl,
            Connection con,
            String appRoot,
            String tempPath)
    throws
    IOException,
    IOToolsException,
    FileNotFoundException,
    UnsupportedEncodingException,
    SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        String sessionId = session.getId();
        int dspZifSid = -1;
        dspZifSid = NullDefault.getInt(
                    paramMdl.getEditZifSid(), -1);

        log__.debug("編集する座席表SID=>" + dspZifSid);
//        ZaiInfoDao infoDao = new ZaiInfoDao(con);
//        ZaiInfoPlusModel infoPlus = infoDao.getZaiInfoPlusModel(dspZifSid);
//        paramMdl.setZasekiMapName(NullDefault.getString(infoPlus.getZifName(), ""));

        Zsk050Biz biz = new Zsk050Biz(reqMdl__);
        paramMdl.setImageFileName(biz.getDspImageFileName(tempPath));
        //座席表画像をテンポラリへ保存
//        __saveImageFile(paramMdl, infoPlus, appRoot, tempPath);

        ArrayList<ZaiIndexModel> indexList = null;
        WkZaiIndexDao wkIndexDao = new WkZaiIndexDao(con);
        indexList = wkIndexDao.getZaiIndexModelList(sessionId, dspZifSid);

        //hidden エレメントKEY
        ArrayList<String> keyList = getElementList(paramMdl, dspZifSid, indexList, con);
        paramMdl.setElementKeyList(keyList);

        return paramMdl;
    }
    /**
     * <br>[機  能] 座席表情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk050knParamModel
     * @param con コネクション
     * @param tempDir TEMPディレクトリ
     * @param appRootPath アプリケーションRootパス
     * @param cntCon 採番コントロール
     * @throws SQLException SQL実行時例外
     * @throws IOException 実行時例外
     * @throws IOToolsException 実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void updateZaiInfo(
            Zsk050knParamModel paramMdl,
            Connection con,
            String tempDir,
            String appRootPath,
            MlCountMtController cntCon)
        throws SQLException, IOException, IOToolsException, TempFileException {
        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int userSid = usModel.getUsrsid();
        //バイナリー情報登録
        UDate now = new UDate();
        CommonBiz cmnBiz = new CommonBiz();
        List < String > binSid = cmnBiz.insertBinInfo(con, tempDir, appRootPath,
                                                    cntCon, userSid, now);
        Long binarySid = new Long(0);
        if (binSid != null && !binSid.isEmpty()) {
            binarySid = Long.parseLong(binSid.get(0));
        }

        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        //添付情報を論理削除する
        infoDao.updateBinInfo(paramMdl.getEditZifSid());

        //座席表情報更新
        ZaiInfoModel model = new ZaiInfoModel();
        model.setZifSid(NullDefault.getInt(paramMdl.getEditZifSid(), 0));
        model.setZifName(NullDefault.getString(paramMdl.getZasekiMapName(), ""));
        model.setZifSort(NullDefault.getInt(paramMdl.getZasekiSortNum(), 0));
        model.setBinSid(binarySid);
        model.setZifEuid(userSid);
        model.setZifEdate(now);
        infoDao.updateNameAndImage(model);
    }
    /**
     * <br>[機  能] 座席表インデックス情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk050knParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateZaiIndex(Zsk050knParamModel paramMdl, Connection con)
        throws SQLException {

        log__.debug("在席表更新");

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        String sessionId = session.getId();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int userSid = usModel.getUsrsid();

        //フォーム情報取得
        int dspZifSid = -1;
        dspZifSid = NullDefault.getInt(
                    paramMdl.getEditZifSid(), -1);

        ZaiIndexDao indexDao = new ZaiIndexDao(con);
        //登録済みIndexを削除
        indexDao.delete(dspZifSid);
        //ワークテーブルからIndex情報を登録
        indexDao.selectInsert(sessionId, dspZifSid, userSid);

    }

    /**
     * 座席表SIDを指定し表示エレメントのKEY配列を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param zifSid 座席表SID
     * @param con コネクション
     * @param indexList 座標リスト
     * @return ArrayList エレメントKEYリスト
     */
    public ArrayList<String> getElementList(
            Zsk050knParamModel paramMdl,
            int zifSid,
            ArrayList<ZaiIndexModel> indexList,
            Connection con) {

        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder buf = null;
        if (indexList.size() < 1) {
            return null;
        }

//        int cnt = 0;
//        for (ZaiIndexModel model : indexList) {
//            cnt++;
        for (int cnt = 1; cnt <= indexList.size(); cnt++) {
            buf = new StringBuilder();
            buf.append(GSConstZaiseki.ELEMENT_KEY);
            buf.append(GSConstZaiseki.ELEMENT_SEPARATOR);
            buf.append(cnt);
            ret.add(buf.toString());
        }

        return ret;

    }

//    /**
//     * 座席表情報から座席表画像をTEMPディレクトリへ保存します。
//     * <br>[機  能]
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param paramMdl フォーム
//     * @param model 座席表情報 + 画像情報
//     * @param appRoot アプリケーションルートパス
//     * @param tempDir 添付ディレクトリ
//     * @throws IOException 添付ファイル生成に失敗
//     * @throws IOToolsException 添付ファイル生成に失敗
//     */
//    private void __saveImageFile(
//            Zsk050knParamModel paramMdl,
//            ZaiInfoPlusModel model,
//            String appRoot,
//            String tempDir) throws IOException, IOToolsException {
//        if (model != null) {
//            String ext = StringUtil.getExtension(model.getZifFileName());
//            String fileName = String.valueOf(model.getZifSid()) +  ext;
//            String dbFilePath = model.getZifFilePath();
//
//            CommonBiz biz = new CommonBiz();
//            //添付ファイル保存用パス(フルパス)
//            String filePath = biz.getSaveFullPath(appRoot, dbFilePath);
//            //ファイルの有効性チェック(ない場合に作成)
//            IOTools.isFileCheck(tempDir, fileName, true);
//            //添付ファイルを保存
//            IOTools.copyBinFile(filePath, tempDir + fileName);
//            paramMdl.setImageFileName(fileName);
//        }
//    }

}
