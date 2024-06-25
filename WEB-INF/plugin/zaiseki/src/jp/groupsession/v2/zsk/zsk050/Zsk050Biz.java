package jp.groupsession.v2.zsk.zsk050;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZskUsedDataBiz;
import jp.groupsession.v2.zsk.dao.WkZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.model.WkZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiInfoModel;
import jp.groupsession.v2.zsk.model.ZaiInfoPlusModel;

/**
 * <br>[機  能] 在席管理 座席表編集画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk050Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public Zsk050Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Zsk050ParamModel
     * @param con コネクション
     * @param tempPath 添付用ディレクトリパス
     * @param appRoot アプリケーションRoot
     * @param domain ドメイン
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイル書込みエラー
     * @throws IOToolsException ファイル書込みエラー
     * @throws FileNotFoundException ファイル存在チェックエラー
     * @throws UnsupportedEncodingException エンコードエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Zsk050ParamModel getInitData(
            Zsk050ParamModel paramMdl,
            Connection con,
            String appRoot,
            String tempPath,
            String domain)
    throws
    IOException,
    IOToolsException,
    FileNotFoundException,
    UnsupportedEncodingException,
    SQLException,
    TempFileException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        String sessionId = session.getId();
        int dspZifSid = -1;
        dspZifSid = NullDefault.getInt(
                    paramMdl.getEditZifSid(), -1);

        log__.debug("編集する座席表SID=>" + dspZifSid);
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        ZaiInfoPlusModel infoPlus = infoDao.getZaiInfoPlusModel(dspZifSid);
        paramMdl.setZasekiMapName(
                NullDefault.getString(paramMdl.getZasekiMapName(), infoPlus.getZifName()));
        paramMdl.setZasekiSortNum(
                NullDefault.getString(
                        paramMdl.getZasekiSortNum(),
                        String.valueOf(infoPlus.getZifSort())));

        //初期表示時のみDBから項目情報を取得しオブジェクトファイルとして保存する
        ArrayList<String> keyList = null;
        ArrayList<ZaiIndexModel> indexList = null;
        if (NullDefault.getString(
                paramMdl.getInitFlg(),
                GSConstZaiseki.INIT_FLG_ON).equals(GSConstZaiseki.INIT_FLG_ON)) {
            //座席表画像をテンポラリへ保存
            __tempFileCopy(infoPlus, appRoot, tempPath, con, domain);
            //座席表画像を表示用テンポラリへ保存
            saveImageFile(paramMdl, tempPath);
            //エレメント詳細情報をワークテーブルへ生成
            ZaiIndexDao indexDao = new ZaiIndexDao(con);
            indexList = indexDao.select(dspZifSid);
            //hidden エレメントKEY
            keyList = getElementList(paramMdl, dspZifSid, indexList, con);
            //ワークテーブルを生成
            __insertWkElementData(sessionId, dspZifSid, indexList, keyList, con);
        } else {
            //ワークテーブルから取得
            WkZaiIndexDao wkIndexDao = new WkZaiIndexDao(con);
            indexList = wkIndexDao.getZaiIndexModelList(sessionId, dspZifSid);
            //hidden エレメントKEY
            keyList = getElementList(paramMdl, dspZifSid, indexList, con);
            paramMdl.setImageFileName(getDspImageFileName(tempPath));
        }



        paramMdl.setElementKeyList(keyList);
        //グループコンボ
        paramMdl.setGroupLabelList(__getGroupLabel(con));
        //グループ所属ユーザ
        paramMdl.setBelongUserList(__getBelongUserList(paramMdl, con));
        //施設グループコンボ
        paramMdl.setRsvGroupLabelList(__getRsvGroupLabel(con));
        //施設グループ所属施設
        paramMdl.setBelongRsvList(__getRsvList(paramMdl, con));
        //初期表示フラグ
        paramMdl.setInitFlg(GSConstZaiseki.INIT_FLG_OFF);

        //添付ファイル一覧を設定
        CommonBiz cBiz = new CommonBiz();
        paramMdl.setZsk050FileLabelList(cBiz.getTempFileLabelList(tempPath));

        return paramMdl;
    }

    /**
     * グループコンボを生成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return ArrayList
     */
    private ArrayList<LabelValueBean> __getGroupLabel(Connection con) throws SQLException {
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        LabelValueBean label = null;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.select.plz");

        label = new LabelValueBean();
        label.setLabel(msg);
        label.setValue("-1");
        ret.add(label);

//        ArrayList<GroupModel> gpList = null;
//        GroupDao dao = new GroupDao(con);
//        gpList = dao.getGroupTree();
        GroupBiz groupBiz = new GroupBiz();
        ArrayList<GroupModel> gpList = groupBiz.getGroupCombList(con);
        for (GroupModel model : gpList) {
            label = new LabelValueBean();
            label.setLabel(model.getGroupName());
            label.setValue(String.valueOf(model.getGroupSid()));
            ret.add(label);
        }
        return ret;
    }

    /**
     * グループに所属するユーザリストを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk050ParamModel
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UserSearchModel> __getBelongUserList(
            Zsk050ParamModel paramMdl, Connection con) throws SQLException {

        ArrayList<UserSearchModel> ret = null;
        int gpSid = NullDefault.getInt(paramMdl.getSelectGroup(), -1);

        UserSearchDao usDao = new UserSearchDao(con);
        ret = usDao.getBelongUserInfoJtkb(gpSid,
                null, GSConstUser.USER_SORT_NAME, GSConst.ORDER_KEY_ASC, 0, 0);
        return ret;
    }

    /**
     * 施設グループに所属する施設リストを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外]
     * @return ArrayList 施設リスト
     */
    private ArrayList<LabelValueBean> __getRsvGroupLabel(Connection con)
    throws SQLException {
        log__.debug("施設グループコンボリストを取得");

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> ret = dao.selectAllGroupData();
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.select.plz");

        String lavelStr = "";
        lavelStr = msg;

        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisGrpModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsgName(),
                            String.valueOf(mdl.getRsgSid())));
        }
        return labelList;
    }
    /**
     * 施設グループに所属する施設リストを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk050ParamModel
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisDataModel> __getRsvList(Zsk050ParamModel paramMdl, Connection con)
    throws SQLException {
        RsvSisDataDao dataDao = new RsvSisDataDao(con);
        ArrayList<RsvSisDataModel> ret =
            dataDao.selectGrpSisetuList(NullDefault.getInt(paramMdl.getSelectRsvGroup(), -1));
        return ret;
    }

    /**
     * <br>[機  能]エレメント詳細情報をワークテーブルへ保存します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionId セッションID
     * @param zaiSid 座席表SID
     * @param indexList 座標リスト
     * @param keyList Keyリスト
     * @param con コネクション
     * @throws SQLException オブジェクトファイル生成に失敗
     */
    private void __insertWkElementData(
            String sessionId,
            int zaiSid,
            ArrayList<ZaiIndexModel> indexList,
            ArrayList<String> keyList,
            Connection con) throws SQLException {

        WkZaiIndexModel wkModel = null;
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            WkZaiIndexDao wkDao = new WkZaiIndexDao(con);
            //一時ファイルを削除
            wkDao.deleteSessionWk(sessionId);
            //一時ファイルを作成
            ZaiIndexModel bean = null;
            for (int i = 0; i < indexList.size(); i++) {
                bean = indexList.get(i);
                wkModel = new WkZaiIndexModel();
                wkModel.setWziSessionSid(sessionId);
                wkModel.setWziSid(zaiSid);
                wkModel.setWziKey(keyList.get(i));
                wkModel.setWziLinkkbn(bean.getZinLinkkbn());
                wkModel.setWziLinksid(bean.getZinLinksid());
                wkModel.setWziName(bean.getZinName());
                wkModel.setWziMsg(bean.getZinMsg());
                wkModel.setWziOtherValue(bean.getZinOtherValue());
                wkModel.setWziXindex(bean.getZinXindex());
                wkModel.setWziYindex(bean.getZinYindex());
                wkDao.insert(wkModel);
            }
            commitFlg = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }
    /**
     * <br>[機  能]一時保存データから指定エレメントKEYを削除
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionId セッションID
     * @param zaiSid 座席表SID
     * @param key Key
     * @param con コネクション
     * @throws SQLException SQL実行時の例外
     */
    public void deleteWkElementData(
            String sessionId,
            int zaiSid,
            String key,
            Connection con) throws SQLException {

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            WkZaiIndexDao wkDao = new WkZaiIndexDao(con);
            //一時保存データから指定KEYを削除
            wkDao.deleteSessionWk(sessionId, zaiSid, key);
            commitFlg = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }

    /** 座席表添付画像を表示用ディレクトリへ保存します。
    * <br>[機  能]
    * <br>[解  説]JSPから参照される画像はこの画像になります。
    * <br>[備  考]
    * @param paramMdl Zsk050ParamModel
    * @param tempDir 添付ディレクトリ
    * @throws IOException 添付ファイル生成に失敗
    * @throws IOToolsException 添付ファイル生成に失敗
    */
   public void saveImageFile(
           Zsk050ParamModel paramMdl,
           String tempDir)
   throws IOException, IOToolsException {

       //
       String imgDir = getDspImageFilePath(tempDir);
       imgDir = IOTools.replaceFileSep(imgDir.toString());

       CommonBiz cBiz = new CommonBiz();
       //添付ファイル一覧を取得
       List<TempFileModel> fileList = cBiz.getTempFiles(tempDir);
       TempFileModel tempMdl = null;
       if (fileList != null && fileList.size() > 0) {
           tempMdl = fileList.get(0);
           String ext = StringUtil.getExtension(tempMdl.getFileName());
           String fileName = GSConstZaiseki.DSP_IMAGE_DIR;
           if (ext != null) {
               fileName = fileName + ext;
           }

           File file = tempMdl.getFile();
           //ファイルの有効性チェック(ない場合に作成)
           IOTools.deleteInDir(imgDir);
           IOTools.isFileCheck(imgDir, fileName, true);
           File destFile = new File(imgDir + fileName);
           IOTools.copyBinFile(file, destFile);
           paramMdl.setImageFileName(fileName);
       }

   }

   /**
    * 添付ディレクトリを元に表示する座席表画像のPATHを取得する
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param tempDir 添付ディレクトリ
    * @return String 座席表画像のPATH
    */
   public String getDspImageFilePath(String tempDir) {
       if (tempDir == null) {
           return null;
       }
       String ret = tempDir;
       ret = ret.concat(GSConstZaiseki.DSP_IMAGE_DIR);
       ret = ret.concat("/");

       return ret;
   }
   /**
    * 表示する座席表画像の名前を取得する
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param tempDir 添付ディレクトリ
    * @return String 座席表画像のファイル名
    */
   public String getDspImageFileName(String tempDir) {
       String ret = null;
       String imgDir = getDspImageFilePath(tempDir);
       imgDir = IOTools.replaceFileSep(imgDir.toString());
       List<String> fileNames = IOTools.getFileNames(imgDir);
       if (fileNames != null && fileNames.size() > 0) {
           ret = fileNames.get(0);
       }
       return ret;
   }

   /**
    * TEMPディレクトリ中の座席表画像の名前を取得する
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param tempDir 添付ディレクトリ
    * @return String 座席表画像のファイル名
    * @throws IOException 添付ファイル参照時エラー
    * @throws IOToolsException 添付ファイル参照時エラー
    */
   public String getTempImageFileName(String tempDir)
   throws IOException, IOToolsException {
       String ret = null;

       CommonBiz cBiz = new CommonBiz();
       //添付ファイル一覧を取得
       List<TempFileModel> fileList = cBiz.getTempFiles(tempDir);
       TempFileModel tempMdl = null;
       if (fileList != null && fileList.size() > 0) {
           tempMdl = fileList.get(0);
           ret = tempMdl.getFileName();
       }

       return ret;
   }

   /**
    * <br>[機  能] 添付ファイルをテンポラリディレクトリにコピーする
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param zaiMdl 在席管理情報
    * @param appRootPath アプリケーションルート
    * @param tempDir テンポラリディレクトリ
    * @param con コネクション
    * @param domain ドメイン
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイルアクセス時例外
    * @throws IOException 入出力時例外
    * @throws TempFileException 添付ファイルUtil内での例外
    */
   private void __tempFileCopy(ZaiInfoPlusModel zaiMdl,
                                String appRootPath,
                                String tempDir,
                                Connection con,
                                String domain)
       throws SQLException, IOToolsException, IOException, TempFileException {

       CommonBiz biz = new CommonBiz();
       CmnBinfModel binMdl = biz.getBinInfo(con, zaiMdl.getBinSid(), domain);

       if (binMdl != null) {

//         現在日付の文字列(YYYYMMDD)を取得
           String dateStr = (new UDate()).getDateString();

           biz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, 1);

       }
   }
    /**
     * セッションIDとエレメントKEYを指定しワークテーブルの内容を更新します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionId セッションID
     * @param zifSid 座席表SID
     * @param elKey エレメントKEY
     * @param bean 更新内容
     * @param con コネクション
     * @return int 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateWkElementData(
            String sessionId,
            int zifSid,
            String elKey,
            WkZaiIndexModel bean,
            Connection con) throws SQLException {

        int cnt = 0;
        WkZaiIndexDao wkDao = new WkZaiIndexDao(con);
        cnt = wkDao.updateWkZaisekiIndex(sessionId, zifSid, elKey, bean);
        return cnt;
    }

    /**
     * ワークテーブルへ項目を追加します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 更新内容
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertWkElementData(
            WkZaiIndexModel bean,
            Connection con) throws SQLException {

        WkZaiIndexDao wkDao = new WkZaiIndexDao(con);
        wkDao.insert(bean);
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
            Zsk050ParamModel paramMdl,
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
        for (int cnt = 0; cnt < indexList.size(); cnt++) {
            buf = new StringBuilder();
            buf.append(GSConstZaiseki.ELEMENT_KEY);
            buf.append(GSConstZaiseki.ELEMENT_SEPARATOR);
            buf.append(cnt);
            ret.add(buf.toString());
        }

        return ret;

    }

    /**
     * 座席表SIDを指定し座席表情報、座席表項目情報を削除する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param zifSid 座席表SID
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル削除に失敗
     * @return int 削除件数
     */
    public int deleteZaiInfoData(int zifSid, int userSid, Connection con)
    throws SQLException, IOToolsException {
        int ret = 0;

        //座席表情報のデータ使用量を登録(変更前のデータ使用量を減算)
        ZskUsedDataBiz usedDataBiz = new ZskUsedDataBiz(con);
        usedDataBiz.insertZasekiSize(zifSid, false);

        //バイナリー情報論理削除
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        ZaiInfoPlusModel plusMdl = infoDao.getZaiInfoPlusModel(zifSid);
        Long binSid = plusMdl.getBinSid();
        CmnBinfDao binDao = new CmnBinfDao(con);
        CmnBinfModel binMdl = new CmnBinfModel();
        binMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        binMdl.setBinUpuser(userSid);
        binMdl.setBinUpdate(new UDate());
        ArrayList<Long> binSids = new ArrayList<Long>();
        binSids.add(new Long(binSid));
        binDao.updateJKbn(binMdl, binSids);
        //在席情報削除
        int cnt1 = infoDao.delete(zifSid);
        ZaiIndexDao idxDao = new ZaiIndexDao(con);
        //座席表項目情報削除
        int cnt2 = idxDao.delete(zifSid);
        ret = cnt1 + cnt2;

        return ret;
    }
    /**
     * <br>[機  能] ログ用メッセージ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param zifSid 座席表SID
     * @param con コネクション
     * @return ログメッセージ
     * @throws IOToolsException ファイルアクセス時例外
     * @throws SQLException SQLエラー
     */
    public String getLogMessage(int zifSid, Connection con)
            throws IOToolsException, SQLException {

        ZaiInfoDao dao = new ZaiInfoDao(con);
        ZaiInfoModel mdl = dao.select(zifSid);
        return mdl.getZifName();

    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンポラリディレクトリパス
     */
    public String getTempDir() {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl__,
                                                GSConstZaiseki.PLUGIN_ID_ZAISEKI,
                                                "zsk050");
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void deleteTempDir() {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl__,
                                    GSConstZaiseki.PLUGIN_ID_ZAISEKI,
                                    "zsk050");
    }
}
