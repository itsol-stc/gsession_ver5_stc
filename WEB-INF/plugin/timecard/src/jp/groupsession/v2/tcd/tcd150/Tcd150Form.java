package jp.groupsession.v2.tcd.tcd150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.tcd010.Tcd010Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] タイムカード CSVインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150Form extends Tcd010Form {
    
    /** 登録対象　グループ */
    private String tcd150SltGroup__ = null;
    /** 登録対象 ユーザ */
    private String tcd150SltUser__ = null;

    /** ファイルコンボ */
    private ArrayList<LabelValueBean> tcd150FileLabelList__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] tcd150SelectFiles__ = null;

    /** 取込みファイル件数 */
    private int impDataCnt__;

    /** グループコンボ */
    private List<LabelValueBean> tcd150GpLabelList__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> tcd150UsrLabelList__ = null;

    /**
     * <br>[機  能]登録権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @return boolean true登録権限あり false 登録権限なし
     * @throws SQLException
     */
    public boolean insertKengen(RequestModel reqMdl, Connection con)
            throws SQLException {

        BaseUserModel usModel = reqMdl.getSmodel();
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int userKbn = tcBiz.getUserKbn(con, usModel);
        boolean ret = false;
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(usModel.getUsrsid(), con);

        //ユーザ存在チェック
        if (userKbn == GSConstTimecard.USER_KBN_NORMAL) {
            //対象ユーザが入力されていて自身以外の場合エラー
            if (tcd150SltUser__ != null
                && !tcd150SltUser__.equals(String.valueOf(usModel.getUsrsid()))) {
                return false;
            }
            setTcd150SltUser(String.valueOf(usModel.getUsrsid()));
        }
        CmnUsrmDao cuDao = new CmnUsrmDao(con);
        List<Integer> userList = new ArrayList<Integer>();
        if (!StringUtil.isNullZeroString(tcd150SltUser__)) {
            userList.add(Integer.parseInt(tcd150SltUser__));
            if (cuDao.getCountActiveUser(userList) == 0) {
                return false;
            }
        } else {
            return false;
        }
        if (userKbn == GSConstTimecard.USER_KBN_ADMIN) {
            ret = true;
        } else if (userKbn == GSConstTimecard.USER_KBN_GRPADM) {
            List<LabelValueBean> groupLabel = tcBiz.getGroupLabelList(con,
                    usModel.getUsrsid(), userKbn, reqMdl);
            int dspGpSid = Integer.parseInt(tcd150SltGroup__);
            boolean adminUser = false;
            for (LabelValueBean grpLabel : groupLabel) {
                if (dspGpSid == Integer.parseInt(grpLabel.getValue())) {
                    adminUser = true;
                    break;
                }
            }
            if (adminUser) {
                GroupDao grpDao = new GroupDao(con);
                int usrSid = NullDefault.getInt(tcd150SltUser__, -1);
                if (grpDao.isBelong(usrSid, dspGpSid)) {
                    ret = true;
                }
            }
        } else {
            //基本設定を確認
            if (admConf.getTacLockFlg() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockStrike() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockLate() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockHoliday() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockTimezone() == GSConstTimecard.UNLOCK_FLG) {
                tcd150SltUser__ = String.valueOf(usModel.getUsrsid());
                ret = true;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] CSVファイルのフォーマットが正しいかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            RequestModel reqMdl,
            String tempDir,
            Connection con) throws Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        //CSV形式のファイル
        String textCsvFileFormat = gsMsg.getMessage("cmn.csv.file.format");
        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        textCaptureFile);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                saveFileName = fMdl.getSaveFileName();
                baseFileName = fMdl.getFileName();
            }

            boolean csvError = false;

            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage(
                            "error.input.notfound.file",
                            textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                textCsvFileFormat);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }

            String fullPath = tempDir + saveFileName;
            Tcd150ImportCheck csvCheck = new Tcd150ImportCheck(
                    errors, con, reqMdl, Integer.parseInt(tcd150SltUser__));

            //CSVチェック
            if (errors.isEmpty() && csvCheck._isCsvDataOk(fullPath)) {
                ActionMessage msg =
                    new ActionMessage("error.format.impfile");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.format.impfile");
                csvError = true;
            }

            //有効データ数
            setImpDataCnt(csvCheck.getCount());
            if (!csvError && getImpDataCnt() <= 0) {
                ActionMessage msg =
                    new ActionMessage("error.nodata.impfile");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.nodata.impfile");
            }
        }

        return errors;
    }

    /**
     * <p>tcd150SltGroup を取得します。
     * @return tcd150SltGroup
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150SltGroup__
     */
    public String getTcd150SltGroup() {
        return tcd150SltGroup__;
    }

    /**
     * <p>tcd150SltGroup をセットします。
     * @param tcd150SltGroup tcd150SltGroup
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150SltGroup__
     */
    public void setTcd150SltGroup(String tcd150SltGroup) {
        tcd150SltGroup__ = tcd150SltGroup;
    }

    /**
     * <p>tcd150SltUser を取得します。
     * @return tcd150SltUser
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150SltUser__
     */
    public String getTcd150SltUser() {
        return tcd150SltUser__;
    }

    /**
     * <p>tcd150SltUser をセットします。
     * @param tcd150SltUser tcd150SltUser
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150SltUser__
     */
    public void setTcd150SltUser(String tcd150SltUser) {
        tcd150SltUser__ = tcd150SltUser;
    }

    /**
     * <p>tcd150FileLabelList を取得します。
     * @return tcd150FileLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150FileLabelList__
     */
    public ArrayList<LabelValueBean> getTcd150FileLabelList() {
        return tcd150FileLabelList__;
    }

    /**
     * <p>tcd150FileLabelList をセットします。
     * @param tcd150FileLabelList tcd150FileLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150FileLabelList__
     */
    public void setTcd150FileLabelList(
            ArrayList<LabelValueBean> tcd150FileLabelList) {
        tcd150FileLabelList__ = tcd150FileLabelList;
    }

    /**
     * <p>tcd150SelectFiles を取得します。
     * @return tcd150SelectFiles
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150SelectFiles__
     */
    public String[] getTcd150SelectFiles() {
        return tcd150SelectFiles__;
    }

    /**
     * <p>tcd150SelectFiles をセットします。
     * @param tcd150SelectFiles tcd150SelectFiles
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150SelectFiles__
     */
    public void setTcd150SelectFiles(String[] tcd150SelectFiles) {
        tcd150SelectFiles__ = tcd150SelectFiles;
    }

    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#impDataCnt__
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }

    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#impDataCnt__
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }

    /**
     * <p>tcd150GpLabelList を取得します。
     * @return tcd150GpLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150GpLabelList__
     */
    public List<LabelValueBean> getTcd150GpLabelList() {
        return tcd150GpLabelList__;
    }

    /**
     * <p>tcd150GpLabelList をセットします。
     * @param tcd150GpLabelList tcd150GpLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150GpLabelList__
     */
    public void setTcd150GpLabelList(List<LabelValueBean> tcd150GpLabelList) {
        tcd150GpLabelList__ = tcd150GpLabelList;
    }

    /**
     * <p>tcd150UsrLabelList を取得します。
     * @return tcd150UsrLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150UsrLabelList__
     */
    public List<UsrLabelValueBean> getTcd150UsrLabelList() {
        return tcd150UsrLabelList__;
    }

    /**
     * <p>tcd150UsrLabelList をセットします。
     * @param tcd150UsrLabelList tcd150UsrLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150Form#tcd150UsrLabelList__
     */
    public void setTcd150UsrLabelList(List<UsrLabelValueBean> tcd150UsrLabelList) {
        tcd150UsrLabelList__ = tcd150UsrLabelList;
    }
}
