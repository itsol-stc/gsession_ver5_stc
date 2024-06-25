package jp.groupsession.v2.ntp.ntp062;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp061.Ntp061Form;
import jp.groupsession.v2.ntp.ui.parts.anken.permit.AnkenPermitSelector;
import jp.groupsession.v2.ntp.ui.parts.anken.tanto.AnkenTantoSelector;

/**
 * <br>[機  能] 日報 案件インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp062Form extends Ntp061Form implements ISelectorUseForm {
    /** 添付ファイル(コンボで選択中) */
    private String[] ntp062SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> ntp062FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;
    /** 初期化フラグ */
    private int ntp062InitFlg__ = 0;

    /** 権限区分 閲覧・編集権限 UI*/
    private AnkenPermitSelector ntp062NanPermitUI__ = 
            AnkenPermitSelector.builder()
                .chainLabel(new GsMessageReq("cmn.setting.permissions", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainGroupSelectionParamName("ntp061NanPermitGroup")
                .chainSelect(
                        Select.builder()
                            .chainLabel(new GsMessageReq("address.61", null))
                            .chainParameterName(
                                    "ntp061NanPermitUserView")
                            )
                .chainSelect(
                            Select.builder()
                            .chainLabel(new GsMessageReq("cmn.edit.permissions", null))
                            .chainParameterName(
                                    "ntp061NanPermitUserEdit")
                            )
                .build();
    /** 権限区分 編集権限 UI*/
    private AnkenPermitSelector ntp062NanPermitEditUI__ = 
            AnkenPermitSelector.builder()
                .chainLabel(new GsMessageReq("cmn.setting.permissions", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainGroupSelectionParamName("ntp061NanPermitGroup")
                .chainSelect(
                            Select.builder()
                            .chainLabel(new GsMessageReq("cmn.edit.permissions", null))
                            .chainParameterName(
                                    "ntp061NanPermitEditUser")
                            )
                .build();
    /** 担当者 UI */
    private AnkenTantoSelector ntp062TantoListUI__ =
            AnkenTantoSelector.builder()
                .chainLabel(new GsMessageReq("cmn.staff", null))
                .chainType(EnumSelectType.USER)
                .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "sv_users")
                    )
                .chainGroupSelectionParamName("ntp061GroupSid")
                .build();

    /** 権限区分 選択ユーザ・グループ(画面表示用) */
    private UserGroupSelectModel ntp062NanPermitList__ = new UserGroupSelectModel();

    /**
     * <p>ntp062SelectFiles を取得します。
     * @return ntp062SelectFiles
     */
    public String[] getNtp062SelectFiles() {
        return ntp062SelectFiles__;
    }
    /**
     * <p>ntp062SelectFiles をセットします。
     * @param ntp062SelectFiles ntp062SelectFiles
     */
    public void setNtp062SelectFiles(String[] ntp062SelectFiles) {
        ntp062SelectFiles__ = ntp062SelectFiles;
    }
    /**
     * <p>ntp062FileLabelList を取得します。
     * @return ntp062FileLabelList
     */
    public ArrayList<LabelValueBean> getNtp062FileLabelList() {
        return ntp062FileLabelList__;
    }
    /**
     * <p>ntp062FileLabelList をセットします。
     * @param ntp062FileLabelList ntp062FileLabelList
     */
    public void setNtp062FileLabelList(ArrayList<LabelValueBean> ntp062FileLabelList) {
        ntp062FileLabelList__ = ntp062FileLabelList;
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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
                                       String tempDir,
                                       Connection con)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

//        //取り込み対象チェック
//        if (ntp110SltUser__.equals(GSConstNippou.USER_NOT_SELECT)) {
//            ActionMessage msg = new ActionMessage("error.select.required.text", "ユーザ");
//            StrutsUtil.addMessage(errors, msg, "ntp110SltUser__.error.select.cmn.object");
//        }

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";

        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        GSConstNippou.TEXT_SELECT_FILE);
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
                            GSConstNippou.TEXT_SELECT_FILE);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                GSConstNippou.TEXT_CSV_FILE);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }

            String fullPath = tempDir + saveFileName;
            Ntp062ImportCheck csvCheck = new Ntp062ImportCheck(errors, con, reqMdl);
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
     * <p>ntp062InitFlg を取得します。
     * @return ntp062InitFlg
     */
    public int getNtp062InitFlg() {
        return ntp062InitFlg__;
    }
    /**
     * <p>ntp062InitFlg をセットします。
     * @param ntp062InitFlg ntp062InitFlg
     */
    public void setNtp062InitFlg(int ntp062InitFlg) {
        ntp062InitFlg__ = ntp062InitFlg;
    }
    /**
     * <p>ntp062NanPermitList を取得します。
     * @return ntp062NanPermitList
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062NanPermitList__
     */
    public UserGroupSelectModel getNtp062NanPermitList() {
        return ntp062NanPermitList__;
    }
    /**
     * <p>ntp062NanPermitList をセットします。
     * @param ntp062NanPermitList ntp062NanPermitList
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062NanPermitList__
     */
    public void setNtp062NanPermitList(UserGroupSelectModel ntp062NanPermitList) {
        ntp062NanPermitList__ = ntp062NanPermitList;
    }
    /**
     * <p>ntp062NanPermitUI を取得します。
     * @return ntp062NanPermitUI
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062NanPermitUI__
     */
    public AnkenPermitSelector getNtp062NanPermitUI() {
        return ntp062NanPermitUI__;
    }
    /**
     * <p>ntp062NanPermitUI をセットします。
     * @param ntp062NanPermitUI ntp062NanPermitUI
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062NanPermitUI__
     */
    public void setNtp062NanPermitUI(AnkenPermitSelector ntp062NanPermitUI) {
        ntp062NanPermitUI__ = ntp062NanPermitUI;
    }
    /**
     * <p>ntp062NanPermitEditUI を取得します。
     * @return ntp062NanPermitEditUI
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062NanPermitEditUI__
     */
    public AnkenPermitSelector getNtp062NanPermitEditUI() {
        return ntp062NanPermitEditUI__;
    }
    /**
     * <p>ntp062NanPermitEditUI をセットします。
     * @param ntp062NanPermitEditUI ntp062NanPermitEditUI
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062NanPermitEditUI__
     */
    public void setNtp062NanPermitEditUI(
            AnkenPermitSelector ntp062NanPermitEditUI) {
        ntp062NanPermitEditUI__ = ntp062NanPermitEditUI;
    }
    /**
     * <p>ntp062TantoListUI を取得します。
     * @return ntp062TantoListUI
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062TantoListUI__
     */
    public AnkenTantoSelector getNtp062TantoListUI() {
        return ntp062TantoListUI__;
    }
    /**
     * <p>ntp062TantoListUI をセットします。
     * @param ntp062TantoListUI ntp062TantoListUI
     * @see jp.groupsession.v2.ntp.ntp062.Ntp062Form#ntp062TantoListUI__
     */
    public void setNtp062TantoListUI(AnkenTantoSelector ntp062TantoListUI) {
        ntp062TantoListUI__ = ntp062TantoListUI;
    }
}