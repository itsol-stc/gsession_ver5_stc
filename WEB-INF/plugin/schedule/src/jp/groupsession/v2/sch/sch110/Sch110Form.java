package jp.groupsession.v2.sch.sch110;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.sch100.Sch100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュールインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch110Form extends Sch100Form {

    /** 添付ファイル(コンボで選択中) */
    private String[] sch110SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> sch110FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;

    /** グループ */
    private String sch110SltGroup__ = null;
    /** ユーザ */
    private String sch110SltUser__ = null;

    /**
     * <p>sch110SltGroup を取得します。
     * @return sch110SltGroup
     */
    public String getSch110SltGroup() {
        return sch110SltGroup__;
    }
    /**
     * <p>sch110SltGroup をセットします。
     * @param sch110SltGroup sch110SltGroup
     */
    public void setSch110SltGroup(String sch110SltGroup) {
        sch110SltGroup__ = sch110SltGroup;
    }
    /**
     * <p>sch110SltUser を取得します。
     * @return sch110SltUser
     */
    public String getSch110SltUser() {
        return sch110SltUser__;
    }
    /**
     * <p>sch110SltUser をセットします。
     * @param sch110SltUser sch110SltUser
     */
    public void setSch110SltUser(String sch110SltUser) {
        sch110SltUser__ = sch110SltUser;
    }
    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }
    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }
    /**
     * <p>sch110FileLabelList を取得します。
     * @return sch110FileLabelList
     */
    public ArrayList<LabelValueBean> getSch110FileLabelList() {
        return sch110FileLabelList__;
    }
    /**
     * <p>sch110FileLabelList をセットします。
     * @param sch110FileLabelList sch110FileLabelList
     */
    public void setSch110FileLabelList(ArrayList<LabelValueBean> sch110FileLabelList) {
        sch110FileLabelList__ = sch110FileLabelList;
    }
    /**
     * <p>sch110SelectFiles を取得します。
     * @return sch110SelectFiles
     */
    public String[] getSch110SelectFiles() {
        return sch110SelectFiles__;
    }
    /**
     * <p>sch110SelectFiles をセットします。
     * @param sch110SelectFiles sch110SelectFiles
     */
    public void setSch110SelectFiles(String[] sch110SelectFiles) {
        sch110SelectFiles__ = sch110SelectFiles;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map,
                                       RequestModel reqMdl,
                                       String tempDir,
                                       Connection con)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

        //取り込み対象チェック
        String dspGpSidStr = NullDefault.getString(sch110SltGroup__, "");
        //マイグループ
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textSelectMygroup = gsMsg.getMessage("schedule.src.25");
        //CSV形式のファイル
        String textCsvFileFormat = gsMsg.getMessage("cmn.csv.file.format");
        //登録対象
        String textImportTarget = gsMsg.getMessage("cmn.registerd");
        if (SchCommonBiz.isMyGroupSid(dspGpSidStr)
                && sch110SltUser__.equals(GSConstSchedule.USER_NOT_SELECT)) {

            ActionMessage msg =
                new ActionMessage(
                        "error.select.cmn.object",
                        textSelectMygroup,
                        textImportTarget);
            StrutsUtil.addMessage(errors, msg, "sch110SltGroup.error.select.cmn.object");
        }

        //特例アクセスチェック
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        SchDao schDao = new SchDao(con);
        if (sch110SltUser__.equals(GSConstSchedule.USER_NOT_SELECT)) {
            //グループチェック
            if (!SchCommonBiz.isMyGroupSid(dspGpSidStr)) {
                int selectGrpSid = Integer.parseInt(dspGpSidStr);
                if (!schDao.canRegistGroupSchedule(selectGrpSid, sessionUserSid, true)) {
                    CmnGroupmDao grpDao = new CmnGroupmDao(con);
                    CmnGroupmModel grpMdl = grpDao.select(selectGrpSid);
                    String groupName = "";
                    if (grpMdl != null && grpMdl.getGrpJkbn() == GSConst.JTKBN_TOROKU) {
                        groupName = grpMdl.getGrpName();
                        groupName = StringUtilHtml.transToHTml(groupName);
                    }
                    ActionMessage msg =
                            new ActionMessage(
                                    "error.cant.entry.grpschedule",
                                    groupName);
                    StrutsUtil.addMessage(errors, msg,
                                                "sch110SltGroup.error.cant.entry.grpschedule");
                    return errors;
                }
            }
        } else {
            //ユーザチェック
            int selectUserSid = Integer.parseInt(sch110SltUser__);
            if (!schDao.canRegistUserSchedule(selectUserSid, sessionUserSid, true)) {
                CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                CmnUsrmInfModel usrMdl = usrDao.select(selectUserSid);
                String userName = "";
                if (usrMdl != null) {
                    userName = usrMdl.getUsiSei() + " " + usrMdl.getUsiMei();
                    userName = StringUtilHtml.transToHTml(userName);
                }
                ActionMessage msg =
                        new ActionMessage(
                                "error.cant.entry.userschedule",
                                userName);
                StrutsUtil.addMessage(errors, msg,
                                            "sch110SltUser.error.cant.entry.userschedule");
                return errors;
            }
        }

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

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

            boolean repeatCheckFlg = isRepeatCheck(con, reqMdl);
            int userSid = NullDefault.getInt(sch110SltUser__, -1);

            String fullPath = tempDir + saveFileName;
            Sch110ImportCheck csvCheck = new Sch110ImportCheck(
                    errors, con, reqMdl, repeatCheckFlg, userSid);
            //CSVチェック
            if (errors.isEmpty() && csvCheck.isCsvDataOk(fullPath)) {
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
     * <br>[機  能] 重複登録チェックを行うか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param reqMdl RequestModel
     * @return 重複登録チェックフラグ
     * @throws SQLException 実行例外
     */
    public boolean isRepeatCheck(Connection con,
                                 RequestModel reqMdl) throws SQLException {
        boolean checkFlg = false;
        int userSid = NullDefault.getInt(sch110SltUser__, -1);

        if (userSid < 1) {
            return checkFlg;
        }

        //個人設定を取得する。
        SchPriConfDao priConfDao = new SchPriConfDao(con);
        SchCommonBiz schBiz = new SchCommonBiz(reqMdl);
        SchPriConfModel priModel = priConfDao.select(userSid);
        SchRepeatKbnModel repertMdl
            = schBiz.getRepertKbn(con, priModel, userSid);

//        if (priModel == null || priModel.getSccRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_NG) {
        if (repertMdl.getRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_NG) {
            return checkFlg;
        } else {
            checkFlg = true;
        }

        return checkFlg;
    }

}