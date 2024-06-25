package jp.groupsession.v2.api.smail.mail;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.smail.mail.send.ApiSmlMailSendForm;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメールの送信を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailBiz.class);

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
     * <br>[機  能] 応答結果ドキュメントを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param ret 処理結果
     * @return 応答結果Document
     */
    public Document resultDocument(boolean ret) {
        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (ret) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        return doc;
    }

    /**
     * <br>[機  能] 空文字列の除外
     * <br>[解  説]
     * <br>[備  考]
     * @param sendList    チェックするアカウントSID配列
     * @param existSidMap 存在するアカウントSIDマップ
     * @return 空文字列を除外した配列
     */
    public String[] convertSacSidList(List<Integer> sendList,
                                      HashMap<Integer, Integer> existSidMap) {
        if (sendList != null && sendList.size() > 0) {
            List<String> convertList = new ArrayList<String>();
            for (Integer key : sendList) {
                Integer val = null;
                if (existSidMap != null && existSidMap.containsKey(key)) {
                    val = existSidMap.get(key);
                }
                if (val != null && val >= 0) {
                    convertList.add(val.toString());
                } else {
                    convertList.add(GSConstSmail.SML_ACCOUNT_STR + key);
                }
            }
            return convertList.toArray(new String[convertList.size()]);
        }
        return null;
    }

    /**
     * <br>[機  能] アカウント使用可能判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  userSid ユーザSID
     * @param  sacSid  アカウントSID
     * @return errors  エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheckSmlAccount(Connection con, GsMessage gsMsg,
                                                int userSid, int sacSid)
        throws SQLException {

        ActionErrors errors = new ActionErrors();

        SmailDao smlDao = new SmailDao(con);

        // 選択されているアカウントが使用可能かを判定する
        if (userSid <= 0 || sacSid <= 0 || !smlDao.canUseCheckAccount(userSid, sacSid)) {
            // アカウントがない場合
            ActionMessage msg = new ActionMessage("search.data.notfound",
                    gsMsg.getMessage("wml.102"));
            StrutsUtil.addMessage(errors, msg, "account");
        }
        return errors;
    }

    /**
     * <br>[機  能] アカウント使用可能判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  errors  エラー
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  form    フォーム
     * @throws SQLException SQL実行時例外
     */
    public void validateFormSmail(ActionErrors errors, Connection con,
                                  GsMessage gsMsg, ApiSmlMailSendForm form)
        throws SQLException {
        // 参照メール使用チェック(新規作成以外の場合)
        if (form.getProcMode() != null
         && (form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
         ||  form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)
         ||  form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)
         ||  form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)
         ||  form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_COPY))) {
            SmailDao smlDao = new SmailDao(con);
            int smlSid = form.getSmlSid();
            int sacSid = form.getSacSid();
            if (smlSid < 0) {
                // メールSIDが未指定
                ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                        gsMsg.getMessage(GSConstApi.TEXT_SML_SID));
                StrutsUtil.addMessage(errors, msg, "smlSid");
            } else if (!smlDao.isViewSmail(sacSid, smlSid)) {
                // メール閲覧権限が無い為、アクセスエラー
                ActionMessage msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cmn.shortmail"));
                StrutsUtil.addMessage(errors, msg, "smlNotFound");
            } else if (form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
                SmlWmeisDao swDao = new SmlWmeisDao(con);
                SmlWmeisModel mdl = swDao.select(sacSid, smlSid);
                if (mdl == null) {
                    ActionMessage msg = new ActionMessage("search.data.notfound",
                            gsMsg.getMessage("cmn.shortmail"));
                    StrutsUtil.addMessage(errors, msg, "smlNotFound");
                }
                
            }
        }
    }

    /**
     * <br>[機  能] アカウント使用可能判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  errors  エラー
     * @param  con     コネクション
     * @param  gsMsg   GsMessage
     * @param  sacSid  アカウントSID
     * @param  smlSid  メールSID
     * @throws SQLException SQL実行時例外
     */
    public void validateReadSmail(ActionErrors errors, Connection con,
                                  GsMessage gsMsg, int sacSid, int smlSid)
        throws SQLException {

        SmailDao smlDao = new SmailDao(con);
        if (smlSid < 0) {
            // メールSIDが未指定
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                    gsMsg.getMessage(GSConstApi.TEXT_SML_SID));
            StrutsUtil.addMessage(errors, msg, "smlSid");
        } else if (!smlDao.isViewSmail(sacSid, smlSid)) {
            // メール閲覧権限が無い為、アクセスエラー
            ActionMessage msg = new ActionMessage("search.data.notfound",
                    gsMsg.getMessage("cmn.shortmail"));
            StrutsUtil.addMessage(errors, msg, "smlNotFound");
        }
    }

    /**
     * <br>[機  能] 添付ファイル入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  errors   エラー
     * @param  con      コネクション
     * @param  gsMsg    GsMessage
     * @param  file     ファイル
     * @throws SQLException SQL実行時例外
     */
    public void validateTempFile(ActionErrors errors, Connection con,
                                 GsMessage gsMsg, FormFile file)
            throws SQLException {
        ActionMessage msg = null;

        String fileName = null;
        int    fileSize = 0;
        if (file != null) {
            fileName = file.getFileName();
            fileSize = file.getFileSize();
        }

        if (fileName == null || fileName.length() == 0 || fileSize == 0) {
            //指定されたファイルが存在しない場合はエラーメッセージを表示
            msg = new ActionMessage("error.input.notfound.file");
            //errors.add("error.input.notfound.file", msg);
            StrutsUtil.addMessage(errors, msg, "error.input.notfound.file");
        } else if (fileName.length() > GSConst.MAX_LENGTH_FILE) {
            //ファイル名
            String textFileName = gsMsg.getMessage("cmn.file.name");

            //ファイル名桁数チェック
            msg = new ActionMessage(
                    "error.input.length.text", textFileName, GSConst.MAX_LENGTH_FILE);
            //errors.add("error.input.length.text", msg);
            StrutsUtil.addMessage(errors, msg, "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(fileName)) {
            //ファイル名 使用文字チェック
            String textFileName = gsMsg.getMessage("cmn.file.name");  //ファイル名

            //利用不可能な文字を入力した場合
            String nstr =
                GSValidateUtil.getNotGsJapaneaseString(
                        fileName);
            msg =
                new ActionMessage("error.input.njapan.text",
                        textFileName,
                        nstr);
            //errors.add("error.file.name.char", msg);
            StrutsUtil.addMessage(errors, msg, "error.file.name.char");
        } else {
            int maxSize = 0;
            CmnFileConfDao cfcDao = new CmnFileConfDao(con);

            //添付ファイル最大容量取得
            CmnFileConfModel cfcMdl = cfcDao.select();
            maxSize = cfcMdl.getFicMaxSize() * 1048576;
            if (fileSize > maxSize) {
                //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
                msg = new ActionMessage("error.input.capacity.over", cfcMdl.getFicMaxSize() + "MB");
                //errors.add("cmn110file.error.input.capacity.over", msg);
                StrutsUtil.addMessage(errors, msg, "cmn110file.error.input.capacity.over");
            }
        }
    }

    /**
     * <br>[機  能] 添付ファイルをサーバーにアップする
     * <br>[解  説]
     * <br>[備  考]
     * @param errors     エラー
     * @param con        コネクション
     * @param gsMsg      GsMessage
     * @param req        リクエスト
     * @param form       フォーム
     * @param domain     ドメイン
     * @param appRootDir ルートパス
     * @param tempDir    テンポラリフォルダ
     * @throws Exception 実行例外
     */
    public void uploadTmpFile(ActionErrors errors, Connection con,
                              GsMessage gsMsg, HttpServletRequest req,
                              ApiSmlMailSendForm form, String domain,
                              String  appRootDir, String tempDir)
            throws Exception {

        int fileNum = 1;

        // 既存の添付ファイルがある場合、テンポラリディレクトリにファイルコピー
        // (※既存の添付ファイルは、草稿・転送・複写の際のみ使用できる)
        if (form.getBinSids() != null && form.getBinSids().length > 0) {
            List<Long> tmpList = null;
            if (form.getSmlSid() > 0
            && (form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)
            ||  form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)
            ||  form.getProcMode().equals(GSConstSmail.MSG_CREATE_MODE_COPY))) {
                // メールSIDから添付ファイル バイナリSID一覧取得
                SmlBinDao sbinDao = new SmlBinDao(con);
                String[] smlSidList = new String[1];
                smlSidList[0] = String.valueOf(form.getSmlSid());
                tmpList = sbinDao.selectBinSidList(smlSidList);
            }

            List<String> binSidList = new ArrayList<String>();
            if (tmpList != null && tmpList.size() > 0) {
                // 保存対象外(パラメータによる指定がない)の添付ファイルを除外
                for (long binSid : form.getBinSids()) {
                    Long key = Long.valueOf(binSid);
                    if (tmpList.contains(key)) {
                        binSidList.add(key.toString());
                    }
                }
            }

            if (binSidList.size() > 0) {
                CommonBiz cmnBiz = new CommonBiz();

                String[] binSids = new String[binSidList.size()];
                for (int i = 0; i < binSidList.size(); i++) {
                    binSids[i] = binSidList.get(i);
                }
                //添付ファイル情報を取得する
                String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
                List<CmnBinfModel> cmList = cmnBiz.getBinInfo(con, binSids,
                                                                   domain);
                for (CmnBinfModel mdl : cmList) {
                    if (mdl.getBinJkbn() != GSConst.JTKBN_DELETE) {
                        // 添付ファイルをテンポラリディレクトリにコピーする。
                        cmnBiz.saveTempFile(dateStr, mdl, appRootDir, tempDir, fileNum);
                        fileNum++;
                    }
                }
            }
        }

        // 新規の添付ファイルをテンポラリディレクトリに保存する。
        FormFile[] tmpFiles = form.getTmpFiles();
        if (tmpFiles != null && tmpFiles.length > 0) {
            for (FormFile file : tmpFiles) {
                if (file != null && file.getFileName() != null
                        && file.getFileName().length() > 0) {
                   // 一旦、一時フォルダに保存
                   this.validateTempFile(errors, con, gsMsg, file);
                   if (!errors.isEmpty()) {
                       return;
                   }
                   this.setTempFile(tempDir, file, fileNum);
                   fileNum++;
                }
            }
        }
    }
}
