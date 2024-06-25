package jp.groupsession.v2.api.smail.send;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailSendModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;

/**
 * <br>[機  能] ショートメールの送信を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlSendBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlSendBiz.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "smlsend";

    /** ユーザSID */
    private int usrSid__ = -1;

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
    public ApiSmlSendBiz(int usrSid) {
        usrSid__ = usrSid;
    }


    /**
     * <br>[機  能] 作成されたメールデータを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form フォーム
     * @param con コネクション
     * @param ctrl 採番用コネクション
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @return 送信結果オブジェクト
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public SmailSendModel insertMailData(
        ApiSmlSendForm form,
        Connection con,
        MlCountMtController ctrl,
        String appRootPath,
        String tempDir,
        PluginConfig pluginConfig,
        RequestModel reqMdl)
        throws
        ClassNotFoundException,
        IllegalAccessException,
        InstantiationException,
        SQLException,
        IOToolsException,
        IOException,
        TempFileException {
        SmailSendModel sendMdl = new SmailSendModel();
        log__.debug("DBに登録");

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = null;
        sacMdl = sacDao.selectFromUsrSid(usrSid__);
        sendMdl.setSacMdl(sacMdl);
        if (sacMdl != null) {
            UDate now = new UDate();
            SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
            SmlAdminModel adminConf = smlCmnBiz.getSmailAdminConf(usrSid__, con);
            //添付ファイルを登録
            CommonBiz biz = new CommonBiz();
            List<String> binList =
                biz.insertBinInfo(con, tempDir, appRootPath, ctrl, usrSid__, now);

            //SID採番
            int mailSid =
                (int) ctrl.getSaibanNumber(
                        GSConstSmail.SAIBAN_SML_SID,
                        GSConstSmail.SAIBAN_SUB_MAIL_SID,
                        usrSid__);
            //メールサイズ取得
            Long titile_byte = 0L;
            Long body_byte = 0L;
            Long file_byte = 0L;

            try {
                if (form.getTitle().getBytes("UTF-8").length != 0) {
                    titile_byte = Long.valueOf(
                            form.getTitle().getBytes("UTF-8").length);
                }
            } catch (UnsupportedEncodingException e) {
                log__.error("文字のバイト数取得に失敗");
                titile_byte = Long.valueOf(
                        form.getTitle().getBytes().length);
            }

            try {
                if (form.getBody().getBytes("UTF-8").length != 0) {
                    body_byte = Long.valueOf(
                            form.getBody().getBytes("UTF-8").length);
                }
            } catch (UnsupportedEncodingException e) {
                log__.error("文字のバイト数取得に失敗");
                body_byte = Long.valueOf(
                        form.getBody().getBytes().length);
            }

            file_byte = biz.getTempFileSize(tempDir);

            int mark = 0;
            if (GSValidateUtil.isNumber(form.getMark())) {
                mark = Integer.valueOf(form.getMark());
            }

            //送信テーブルにデータ作成
            SmlSmeisModel sparam = new SmlSmeisModel();
            sparam.setSacSid(sacMdl.getSacSid());
            sparam.setSmsSid(mailSid);
            sparam.setSmsSdate(now);
            sparam.setSmsTitle(form.getTitle());
            sparam.setSmsMark(mark);
            sparam.setSmsBody(form.getBody());
            sparam.setSmsBodyPlain(form.getBody());
            long addSize = titile_byte + body_byte + file_byte;
            sparam.setSmsSize(addSize);
            sparam.setSmsType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
            sparam.setSmsJkbn(GSConst.JTKBN_TOROKU);
            sparam.setSmsAuid(usrSid__);
            sparam.setSmsAdate(now);
            sparam.setSmsEuid(usrSid__);
            sparam.setSmsEdate(now);
            SmlSmeisDao sdao = new SmlSmeisDao(con);
            sdao.insert(sparam);

            SmlJmeisDao jdao = new SmlJmeisDao(con);

//            //ショートメールリスナー取得
//            ISMailListener[] lis = SMailListenerUtil.getSMailListeners(pluginConfig);


            //受信テーブルにデータ作成
            String usrSids = form.getUsrSid();

            String[] userSid = usrSids.split(",");
            String[] accountSidAtesaki = smlCmnBiz.getAccountSidFromUsr(
                                                    con, userSid);

            ArrayList<String[]> accountSidList = new ArrayList<String[]>();
            ArrayList<Integer> sendKbnList = new ArrayList<Integer>();
            accountSidList.add(accountSidAtesaki);

            sendKbnList.add(GSConstSmail.SML_SEND_KBN_ATESAKI);

            //受信メール登録前に送信メールの集計データを登録する
            String[] cntAccountSid = null;
            ArrayList<String> cntAllAccountSidList = new ArrayList<String>();
            int cntAtesaki = 0;
            int cntCc = 0;
            int cntBcc = 0;
            for (int n = 0; n < accountSidList.size(); n++) {
                cntAccountSid = accountSidList.get(n);
                for (int i = 0; i < cntAccountSid.length; i++) {
                    if (cntAllAccountSidList.contains(cntAccountSid[i])) {
                        continue;
                    }
                    cntAllAccountSidList.add(cntAccountSid[i]);
                    if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_ATESAKI) {
                        cntAtesaki++;
                    } else if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_CC) {
                        cntCc++;
                    } else if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_BCC) {
                        cntBcc++;
                    }
                }
            }

            SmlCommonBiz smlBiz = new SmlCommonBiz();
            smlBiz.regSmeisLogCnt(con, sacMdl.getSacSid(), cntAtesaki, cntCc, cntBcc, now);

            String[] accountSid = null;
            List<Integer> sendAccountList = new ArrayList<Integer>();
            ArrayList<String> allAccountSidList = new ArrayList<String>();
            ArrayList<SmlJmeisModel> jparamList = new ArrayList<SmlJmeisModel>();
            for (int n = 0; n < accountSidList.size(); n++) {

                accountSid = accountSidList.get(n);

                for (int i = 0; i < accountSid.length; i++) {
                    if (allAccountSidList.contains(accountSid[i])) {
                        //一度送信したユーザを除く
                        continue;
                    }
                    allAccountSidList.add(accountSid[i]);

                    SmlJmeisModel jparam = new SmlJmeisModel();
                    jparam.setSacSid(Integer.parseInt(accountSid[i]));
                    jparam.setSmjSid(mailSid);

                    smlCmnBiz.getSmailAccountForward(
                            Integer.parseInt(accountSid[i]), -1, con);

                    jparam.setSmjOpkbn(GSConstSmail.OPKBN_UNOPENED);
                    jparam.setSmjFwkbn(GSConstSmail.FWKBN_NO);
                    jparam.setSmjOpdate(null);
                    jparam.setSmjJkbn(GSConst.JTKBN_TOROKU);
                    jparam.setSmjSendkbn(sendKbnList.get(n));
                    jparam.setSmjAuid(usrSid__);
                    jparam.setSmjAdate(now);
                    jparam.setSmjEuid(usrSid__);
                    jparam.setSmjEdate(now);
                    jparamList.add(jparam);

                }
            }

            //メール送信、それに伴う処理でデッドロックが発生しないよう
            //SIDの降順に並び替え実行
            jparamList = smlCmnBiz.setOrderBySidDescMdl(jparamList);
            for (SmlJmeisModel jparam:jparamList) {
                //受信メール登録
                jdao.insert(jparam);
                sendAccountList.add(jparam.getSacSid());
                //アカウントディスク使用量の再計算を行う
                smlBiz.updateAccountDiskSize(con, jparam.getSacSid(), addSize);
                //受信メールの集計データを登録する
                smlBiz.regJmeisLogCnt(
                        con, jparam.getSacSid(), jparam.getSmjSendkbn(), now);
            }
            sendMdl.setSmjSid(mailSid);
            sendMdl.setAccountSidList(sendAccountList);
    
            //転送設定を取得し必要に応じてE-mailにて転送
            List<TempFileModel> fileList = biz.getTempFiles(tempDir);
            SmailDao smaildao = new SmailDao(con);
            ArrayList<SmailDetailModel> sdList =
                smaildao.selectSmeisDetailFromSid(sparam.getSmsSid());
            int fwkbn = 0;
            SmlJmeisModel jparam = null;

            //デッドロックが発生しないようアカウントSIDを降順に統一
            allAccountSidList = smlCmnBiz.setOrderBySidDescStr(allAccountSidList);
            for (int i = 0; i < allAccountSidList.size(); i++) {

                fwkbn = smlCmnBiz.sendSmailForward(
                        sparam,
                        sdList,
                        Integer.parseInt(allAccountSidList.get(i)),
                        fileList,
                        adminConf,
                        pluginConfig,
                        con);

                if (fwkbn == GSConstSmail.ERROR_KBN) {
                    continue;

                }

                if (fwkbn > 0) {
                    jparam = new SmlJmeisModel();
                    jparam.setSacSid(Integer.parseInt(allAccountSidList.get(i)));
                    jparam.setSmjSid(mailSid);
                    jparam.setSmjFwkbn(GSConstSmail.FWKBN_OK);
                    jdao.updateFwkbn(jparam);
                }

            }

            SmlBinDao sbinDao = new SmlBinDao(con);

            //ショートメール送付情報を登録
            sbinDao.insertSmlBin(sparam, binList);

            //ディスク容量を更新
            smlCmnBiz.updateAccountDiskSize(con, sacMdl.getSacSid(), addSize);
            //ショートメール情報のデータ使用量を登録
            SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
            usedDataBiz.insertSendDataSize(mailSid, true);

            //テンポラリディレクトリの削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(reqMdl,
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);
            log__.debug("テンポラリディレクトリのファイル削除");

        }
        return sendMdl;        
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

    /**
     * <br>[機  能] 全ての添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param form フォーム
     * @throws IOToolsException IOエラー
     * @throws Exception エラー
     */
    public void setTempFileAll(String tempDir, ApiSmlSendForm form)
    throws IOToolsException, Exception {

        setTempFile(tempDir, form.getTmpFile1(), 1);
        setTempFile(tempDir, form.getTmpFile2(), 2);
        setTempFile(tempDir, form.getTmpFile3(), 3);
        setTempFile(tempDir, form.getTmpFile4(), 4);
        setTempFile(tempDir, form.getTmpFile5(), 5);
        setTempFile(tempDir, form.getTmpFile6(), 6);
        setTempFile(tempDir, form.getTmpFile7(), 7);
        setTempFile(tempDir, form.getTmpFile8(), 8);
        setTempFile(tempDir, form.getTmpFile9(), 9);
        setTempFile(tempDir, form.getTmpFile10(), 10);

    }

}