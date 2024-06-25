package jp.groupsession.v2.ptl.ptl160;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.dao.PtlPortletImageDao;
import jp.groupsession.v2.ptl.model.PtlPortletImageModel;

/**
 * <br>[機  能] ポータル ポートレット画像選択ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl160Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl160Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @param con Connection
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル操作時例外
     */
    public void setInitData(Ptl160Form form, Connection con)
        throws SQLException, IOToolsException {

        //添付ファイル最大容量取得
        CmnFileConfDao cfcDao = new CmnFileConfDao(con);
        CmnFileConfModel cfcMdl = cfcDao.select();
        form.setStrMaxSize(String.valueOf(cfcMdl.getFicMaxSize()));
    }

    /**
     * <br>[機  能] 画像情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param form フォーム
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void entryImageData(Connection con, Ptl160Form form, String tempDir,
            String appRoot, int usrSid, MlCountMtController cntCon)
    throws SQLException, TempFileException {
        log__.debug("START");

        int ptlCmd = form.getPtlCmdMode();
        int pltSid = form.getPtlPortletSid();
        String imageName = NullDefault.getString(form.getPtl160tempName(), "");
        PtlPortletImageDao imageDao = new PtlPortletImageDao(con);
        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        long binSid = 0;

        String filePath = tempDir + imageName;


        if (ptlCmd == GSConstPortal.CMD_MODE_EDIT) {
            //更新

            long pliSid = form.getPtlPortletImageSid();

            //ポートレット画像情報を取得する
            PtlPortletImageModel ptlImageModel = imageDao.select(pltSid, pliSid);
            if (ptlImageModel == null) {
                return;
            }
            binSid = ptlImageModel.getBinSid();

            //バイナリー情報の論理削除を行う
            CmnBinfDao binDao = new CmnBinfDao(con);
            CmnBinfModel cbMdl = new CmnBinfModel();
            List<Long> binSidList = new ArrayList<Long>();
            binSidList.add(binSid);
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(usrSid);
            cbMdl.setBinUpdate(now);
            binDao.updateJKbn(cbMdl, binSidList);

            //バイナリー情報の新規登録を行う
            binSid = cmnBiz.insertBinInfo(
                        con, appRoot, cntCon, usrSid, now, filePath, imageName);

            //ポートレット_画像の編集を行う
            PtlPortletImageModel imageMdl = new PtlPortletImageModel();
            imageMdl.setPltSid(pltSid);
            imageMdl.setPliSid(pliSid);
            imageMdl.setBinSid(binSid);
            imageMdl.setPliName(imageName);
            imageDao.update(imageMdl);

        } else {
            //登録

            //バイナリー情報の新規登録を行う
            binSid = cmnBiz.insertBinInfo(
                        con, appRoot, cntCon, usrSid, now, filePath, imageName);

            //ポートレット画像SID採番
            int pliSid = (int) cntCon.getSaibanNumber(GSConstPortal.SBNSID_SUB_PORTAL,
                                                    GSConstPortal.SBNSID_SUB_PORTLET_IMAGE,
                                                    usrSid);

            //ポートレット_画像の新規登録を行う
            PtlPortletImageModel imageModel = new PtlPortletImageModel();
            imageModel.setPltSid(pltSid);
            imageModel.setPliSid(pliSid);
            imageModel.setBinSid(binSid);
            imageModel.setPliName(imageName);
            imageDao.insert(imageModel);
        }

        log__.debug("END");
    }

    /**
     * <br>[機  能] 添付ファイル(本体)のパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @return 添付ファイル(本体)のパス
     */
    public static File getSaveFilePath(String tempDir, String dateStr, int fileNum) {

        return __getFilePath(tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_SAVEFILE);
    }

    /**
     * <br>[機  能] オブジェクトファイルのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @return 添付ファイル(本体)のパス
     */
    public static File getObjFilePath(String tempDir, String dateStr, int fileNum) {

        return __getFilePath(tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_OBJFILE);
    }

    /**
     * <br>[機  能] 添付ファイルの連番を取得する
     * <br>[解  説] テンポラリディレクトリパス以下に存在する
     * <br>         名前が「日付文字列 + xxx + "file"」のファイルの数を返す
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列
     * @return テンポラリファイルの連番
     */
    public static int getFileNumber(String tempDir, String dateStr) {
        Ptl160FilenameFilter filter =
            new Ptl160FilenameFilter(dateStr, GSConstCommon.ENDSTR_SAVEFILE);

        File tempDirPath = new File(tempDir);
        File[] fileList = tempDirPath.listFiles(filter);
        if (fileList == null) {
            return 0;
        }

        int fileNum = 0;
        int tailLen = GSConstCommon.ENDSTR_SAVEFILE.length();
        for (File fileName : fileList) {
            String num = fileName.getName();
            num = num.substring(dateStr.length(), num.length() - tailLen);
            if (fileNum < Integer.parseInt(num)) {
                fileNum = Integer.parseInt(num);
            }
        }

        return fileNum;
    }

    /**
     * <br>[機  能] ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリファイル
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @param endStr 接尾文字列("file" or "obj")
     * @return ファイルパス
     */
    private static File __getFilePath(String tempDir, String dateStr, int fileNum, String endStr) {

        StringBuilder filePath = new StringBuilder("");
        filePath.append(tempDir);
        filePath.append(dateStr);
        filePath.append(StringUtil.toDecFormat(fileNum, "000"));
        filePath.append(endStr);

        return new File(filePath.toString());
    }
    /**
     * <br>[機  能] ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリファイル
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @param endStr 接尾文字列("file" or "obj")
     * @return ファイルパス
     */
    public static String getFilePath(String tempDir, String dateStr, int fileNum, String endStr) {

        StringBuilder filePath = new StringBuilder("");
        filePath.append(tempDir);
        filePath.append(dateStr);
        filePath.append(StringUtil.toDecFormat(fileNum, "000"));
        filePath.append(endStr);

        return filePath.toString();
    }
}