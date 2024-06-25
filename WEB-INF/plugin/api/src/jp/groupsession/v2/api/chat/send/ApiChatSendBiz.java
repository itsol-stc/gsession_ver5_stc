package jp.groupsession.v2.api.chat.send;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtUsedDataBiz;
import jp.groupsession.v2.cht.biz.ChtUserPairCreateBiz;
import jp.groupsession.v2.cht.dao.ChtGroupDataDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtGroupViewDao;
import jp.groupsession.v2.cht.dao.ChtUserDataDao;
import jp.groupsession.v2.cht.dao.ChtUserViewDao;
import jp.groupsession.v2.cht.model.ChtGroupDataModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cht.model.ChtGroupViewModel;
import jp.groupsession.v2.cht.model.ChtUserDataModel;
import jp.groupsession.v2.cht.model.ChtUserViewModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] チャット投稿APIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiChatSendBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiChatSendBiz.class);

    /** ユーザSID */
    private int usrSid__ = -1;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param usrSid 設定する usrSid
     * */
    public ApiChatSendBiz(int usrSid, Connection con, RequestModel reqMdl) {
        usrSid__ = usrSid;
        con__ = con;
        reqMdl__ = reqMdl;
    }


    /**
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * @param usrSid 設定する usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>Set Connection
     * @param usrSid ユーザSID
     */
    public ApiChatSendBiz(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <br>[機  能] メッセージを投稿する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form フォーム
     * @param cntCon 採番用コネクション
     * @throws Exception 例外
     */
    public void sendMessage(
        ApiChatSendForm form,
        MlCountMtController cntCon)
        throws Exception {
        log__.debug("DBに登録");
        int sendKbn = NullDefault.getInt(form.getSendKbn(), -1);

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        String content = form.getBody();
        if (sendKbn == GSConstChat.CHAT_KBN_USER) {
            /*ユーザ間チャット*/
            int selectId = NullDefault.getInt(form.getSelectId(), -1);
            __insertUserData(sessionUsrSid, selectId, -1, form, cntCon);
        } else if (sendKbn == GSConstChat.CHAT_KBN_GROUP && content != null) {
            /*グループチャット*/
            __insertGroupData(sessionUsrSid, -1, form, cntCon);
        }
    }

    /**
     * <br>[機  能] 添付ファイルを投稿する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form フォーム
     * @param con コネクション
     * @param cntCon 採番用コネクション
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws Exception 例外
     */
    public void sendTempFile(
        ApiChatSendForm form,
        Connection con,
        MlCountMtController cntCon,
        String appRootPath,
        String tempDir,
        PluginConfig pluginConfig,
        RequestModel reqMdl)
        throws Exception {
        log__.debug("DBに登録");
        int sendKbn = NullDefault.getInt(form.getSendKbn(), -1);
        int selectId = NullDefault.getInt(form.getSelectId(), -1);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        UDate now = new UDate();
        String content = form.getBody();
        // 添付
        CommonBiz biz = new CommonBiz();
        List < String > binList
            = biz.insertBinInfo(con__, tempDir, appRootPath, cntCon, sessionUsrSid, now);
        long binSid = NullDefault.getLong(binList.get(0), -1);
        if (sendKbn == GSConstChat.CHAT_KBN_USER) {
            /*ユーザ間チャット*/
            __insertUserData(sessionUsrSid, selectId, binSid, form, cntCon);
        } else if (sendKbn == GSConstChat.CHAT_KBN_GROUP && content != null) {
            /*グループチャット*/
            __insertGroupData(sessionUsrSid, binSid, form, cntCon);
        }
    }

    /**
     * <br>[機  能] ペアチャット情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param selectId 選択SID
     * @param binSid バイナリSID
     * @param form フォーム
     * @param cntCon 採番用コネクション
     * @throws Exception 実行例外
     */
    private void __insertUserData(
            int usrSid,
            int selectId,
            long binSid,
            ApiChatSendForm form,
            MlCountMtController cntCon) throws Exception {
        int nPair = -1;
        /*ユーザ間チャット*/
        ChtUserPairCreateBiz cupBiz = new ChtUserPairCreateBiz();
        nPair = cupBiz.getCupSidAutoCreate(reqMdl__, con__, usrSid, selectId);

        //ユーザCHT_USER_DATAにデータを登録する
        ChtUserDataDao cudDao = new ChtUserDataDao(con__);
        ChtUserDataModel cudMdl = __createSendUserData(form, cntCon, binSid);
        cudMdl.setCupSid(nPair);
        cudDao.insert(cudMdl);

        //ユーザCHT_USER_VIEWにdeleteinsertする
        ChtUserViewDao cuvDao = new ChtUserViewDao(con__);
        cuvDao.delete(nPair, usrSid);

        ChtUserViewModel cuvMdl = new ChtUserViewModel();
        cuvMdl.setCupSid(nPair);
        cuvMdl.setCuvUid(usrSid);
        cuvMdl.setCudSid(cudMdl.getCudSid());
        cuvMdl.setCuvViewcnt(cudDao.countTarget(nPair, cudMdl.getCudSid()));
        cuvDao.insert(cuvMdl);

        //チャット投稿情報のデータ使用量を登録
        ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con__);
        usedDataBiz.insertChtDataSize((long) cudMdl.getCudSid(), ChtUsedDataBiz.TYPE_USER, true);
    }

    /**
     * <br>[機  能] グループチャット情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param binSid バイナリSID
     * @param form フォーム
     * @param cntCon 採番用コネクション
     * @throws Exception 実行例外
     */
    private void __insertGroupData(
            int usrSid,
            long binSid,
            ApiChatSendForm form,
            MlCountMtController cntCon) throws Exception {
        //グループIDからSID取得
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel infMdl = infDao.selectWhereCgiId(form.getSelectId());
        int grpSid = infMdl.getCgiSid();
        //CHT_GROUP_DATAにデータを登録する
        ChtGroupDataDao grpDataDao = new ChtGroupDataDao(con__);
        ChtGroupDataModel grpDataMdl = __createSendGroupData(form, cntCon, binSid, grpSid);
        grpDataDao.insert(grpDataMdl);
        //CHT_GROUP_VIEWのデータをdeleteinsertする
        ChtGroupViewDao cgvDao = new ChtGroupViewDao(con__);
        cgvDao.delete(grpSid, usrSid);
        ChtGroupViewModel cgvMdl = new ChtGroupViewModel();
        cgvMdl.setCgiSid(grpSid);
        cgvMdl.setCgvUid(usrSid);
        cgvMdl.setCgdSid(grpDataMdl.getCgdSid());
        cgvMdl.setCgvViewcnt(grpDataDao.countTarget(grpSid, grpDataMdl.getCgdSid()));
        cgvDao.insert(cgvMdl);


        //チャット投稿情報のデータ使用量を登録
        ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con__);
        usedDataBiz.insertChtDataSize((long) grpDataMdl.getCgdSid(), ChtUsedDataBiz.TYPE_GROUP, true);
    }


    /**
     * <br>[機  能] グループ投稿情報の作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム情報
     * @param cntCon コントローラ
     * @param binSid バイナリSID
     * @param cgiSid groupSid
     * @throws Exception 実行例外
     * @return grpDataMdl チャットグループ投稿情報
     */
    private ChtGroupDataModel __createSendGroupData(ApiChatSendForm form,
            MlCountMtController cntCon, long binSid, int cgiSid)

    throws Exception {

        ChtGroupDataModel grpDataMdl = new ChtGroupDataModel();
        UDate now = new UDate();
        int userSid = reqMdl__.getSmodel().getUsrsid();
        long newId = cntCon.getSaibanNumber("chat", "grpMessageSid", userSid);
        grpDataMdl.setCgdAdate(now);
        grpDataMdl.setCgdAuid(userSid);
        grpDataMdl.setCgdEdate(now);
        grpDataMdl.setCgdEuid(userSid);
        grpDataMdl.setBinSid(binSid);
        grpDataMdl.setCgdSendUid(userSid);
        grpDataMdl.setCgdSid(newId);
        grpDataMdl.setCgdStateKbn(GSConstChat.TOUKOU_STATUS_NORMAL);
        if (binSid == -1) {
            grpDataMdl.setCgdText(form.getBody());
        } else {
            grpDataMdl.setCgdText(null);
        }
        grpDataMdl.setCgiSid(cgiSid);
        return grpDataMdl;
    }

    /**
     * <br>[機  能] ユーザ投稿情報の作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form form
     * @param cntCon コントローラ
     * @param binSid バイナリSID
     * @throws Exception 実行例外
     * @return grpDataMdl チャットグループ投稿情報
     */
    private ChtUserDataModel __createSendUserData(ApiChatSendForm form,
            MlCountMtController cntCon, long binSid)

    throws Exception {

        ChtUserDataModel usrDataMdl = new ChtUserDataModel();
        UDate now = new UDate();
        int userSid = reqMdl__.getSmodel().getUsrsid();
        long newId = cntCon.getSaibanNumber("chat", "usrMessageSid", userSid);
        usrDataMdl.setCudSid(newId);
        usrDataMdl.setCupSid(0);
        if (binSid == -1) {
            usrDataMdl.setCudText(form.getBody());
        } else {
            usrDataMdl.setCudText(null);
        }
        usrDataMdl.setBinSid(binSid);
        usrDataMdl.setCudSendUid(userSid);
        usrDataMdl.setCudStateKbn(GSConstChat.TOUKOU_STATUS_NORMAL);
        usrDataMdl.setCudAdate(now);
        usrDataMdl.setCudAuid(userSid);
        usrDataMdl.setCudEdate(now);
        usrDataMdl.setCudEuid(userSid);
        return usrDataMdl;
    }

    /**
     * <br>[機  能] 全ての添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param form フォーム
     * @throws IOToolsException IOエラー
     * @throws Exception エラー
     */
    public void setTempFileAll(String tempDir, ApiChatSendForm form)
    throws IOToolsException, Exception {

        setTempFile(tempDir, form.getTmpFile(), 1);

    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param formFile ファイルデータ
     * @param fileNum ファイル番号
     * @throws IOToolsException IOエラー
     * @throws Exception エラー
     */
    public void setTempFile(String tempDir, FormFile formFile, int fileNum)
    throws IOToolsException, Exception {

        if (formFile == null
                || formFile.getFileName() == null
                || formFile.getFileName().equals("")) {
            return;
        }
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        //添付ファイル名
        String fileName = (new File(formFile.getFileName())).getName();
        long fileSize = formFile.getFileSize();
        //添付ファイル(本体)のパスを取得
        File saveFilePath = CommonBiz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイルアップロード
        TempFileUtil.upload(formFile, tempDir, saveFilePath.getName());

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(fileName);
        fileMdl.setSaveFileName(saveFilePath.getName());
        fileMdl.setAtattiSize(fileSize);

        String[] fileVal = fileMdl.getSaveFileName().split(GSConstCommon.ENDSTR_SAVEFILE);
        log__.debug("*** セーブファイル = " + fileVal[0]);
        fileMdl.setSplitObjName(fileVal[0]);

        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);

    }


}