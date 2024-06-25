package jp.groupsession.v2.rsv.rsv180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 施設インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv180Form extends Rsv080Form {

    /** 添付ファイル(コンボで選択中) */
    private String[] rsv180SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> rsv180FileLabelList__ = null;
    /** 選択グループ(CSVが格納される) */
    private String rsv180Selectgroup__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;
    /** 施設区分SID */
    private int rsv180RskSid__ = -1;

    /**
     * <p>rsv180FileLabelList__ を取得します。
     * @return rsv180FileLabelList
     */
    public ArrayList<LabelValueBean> getRsv180FileLabelList() {
        return rsv180FileLabelList__;
    }
    /**
     * <p>rsv180FileLabelList__ をセットします。
     * @param rsv180FileLabelList rsv180FileLabelList__
     */
    public void setRsv180FileLabelList(ArrayList<LabelValueBean> rsv180FileLabelList) {
        rsv180FileLabelList__ = rsv180FileLabelList;
    }
    /**
     * <p>rsv180SelectFiles__ を取得します。
     * @return rsv180SelectFiles
     */
    public String[] getRsv180SelectFiles() {
        return rsv180SelectFiles__;
    }
    /**
     * <p>rsv180SelectFiles__ をセットします。
     * @param rsv180SelectFiles rsv180SelectFiles__
     */
    public void setRsv180SelectFiles(String[] rsv180SelectFiles) {
        rsv180SelectFiles__ = rsv180SelectFiles;
    }
    /**
     * <p>rsv180Selectgroup__ を取得します。
     * @return rsv180Selectgroup
     */
    public String getRsv180Selectgroup() {
        return rsv180Selectgroup__;
    }
    /**
     * <p>rsv180Selectgroup__ をセットします。
     * @param rsv180Selectgroup rsv180Selectgroup__
     */
    public void setRsv180Selectgroup(String rsv180Selectgroup) {
        rsv180Selectgroup__ = rsv180Selectgroup;
    }
    /**
     * <p>impDataCnt__ を取得します。
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }
    /**
     * <p>impDataCnt__ をセットします。
     * @param impDataCnt impDataCnt__
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }
    /**
     * <p>rsv180RskSid__ を取得します。
     * @return rsv180RskSid
     */
    public int getRsv180RskSid() {
        return rsv180RskSid__;
    }
    /**
     * <p>rsv180RskSid__ をセットします。
     * @param rsv180RskSid rsv180RskSid__
     */
    public void setRsv180RskSid(int rsv180RskSid) {
        rsv180RskSid__ = rsv180RskSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map,
                                       HttpServletRequest req,
                                       String tempDir,
                                       Connection con)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";

        GsMessage gsMsg = new GsMessage();
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        gsMsg.getMessage(req, "cmn.capture.file"));
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
                            gsMsg.getMessage(req, "cmn.capture.file"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                gsMsg.getMessage(req, "cmn.csv.file.format"));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }

            int rskSid = -1;
            //取込み先グループの施設区分取得
            RsvSisGrpDao dao = new RsvSisGrpDao(con);
            RsvSisGrpModel ret = dao.select(getRsv080EditGrpSid());
            if (ret == null) {
                ActionMessage msg =
                    new ActionMessage("error.group.notfound");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.group.notfound");
                csvError = true;
            } else {
                rskSid = ret.getRskSid();
            }

            if (!csvError) {
                String fullPath = tempDir + saveFileName;
                RsvImportCheck csvCheck = new RsvImportCheck(errors, con, rskSid, req);
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
        }

        return errors;
    }
}