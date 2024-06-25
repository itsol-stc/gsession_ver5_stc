package jp.groupsession.v2.fil.fil060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.h2.util.StringUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil050.Fil050Form;
import jp.groupsession.v2.fil.fil060.ui.Fil060AuthSelector;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileParentAccessDspModel;
import jp.groupsession.v2.fil.util.FilValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil060Form extends Fil050Form implements ISelectorUseForm {

    /** 登録変更モード */
    private int fil060CmdMode__ = 0;
    /** プラグインID */
    private String fil060PluginId__ = null;
    /** フォルダ名 */
    private String fil060DirName__ = null;
    /** 備考 */
    private String fil060Biko__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] fil060TempFiles__ = null;
    /** 更新者ID */
    private String fil060EditId__ = null;
    /** グループ一覧 */
    private List<LabelValueBean> fil060groupList__ = null;

    /** 親アクセス権限全表示*/
    private String fil060ParentAccessAll__ = "0";
    /** アクセス制御　有無*/
    private String fil060AccessKbn__ = "0";
    /** 含まれるサブフォルダ・ファイルにも適応*/
    private String file060AdaptIncFile__ = "0";

    /** アクセス候補 追加・変更・削除グループ */
    private String fil060AcEditSltGroup__ = null;
    /** セーブ フルアクセスユーザ */
    private String[] fil060SvAcFull__ = null;

    /** アクセス候補 閲覧グループ */
    private String fil060AcReadSltGroup__ = null;
    /** セーブ 閲覧アクセスユーザ */
    private String[] fil060SvAcRead__ = null;

    /** アクセス権限 共通キャビネット用UI */
    private Fil060AuthSelector fil060PublicAcUserUI__ =
            Fil060AuthSelector.builder()
            .chainLabel(new GsMessageReq("cmn.access.auth", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.add.edit.delete", null))
                    .chainParameterName(
                            "fil060SvAcFull")
                    )
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.reading", null))
                    .chainParameterName(
                            "fil060SvAcRead")
                    )
            .chainGroupSelectionParamName("fil060AcEditSltGroup")
            .build();
    /** アクセス権限 個人キャビネット用UI */
    private Fil060AuthSelector fil060PrivateAcUserUI__ =
            Fil060AuthSelector.builder()
            .chainLabel(new GsMessageReq("cmn.access.auth", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.reading", null))
                    .chainParameterName(
                            "fil060SvAcRead")
                    )
            .chainGroupSelectionParamName("fil060AcReadSltGroup")
            .build();

    /** 親ディレクトリ　追加・変更・削除ユーザリスト */
    private ArrayList<FileParentAccessDspModel> fil060ParentEditList__ = null;
    /** 親ディレクトリ　閲覧ユーザリスト */
    private ArrayList<FileParentAccessDspModel> fil060ParentReadList__ = null;

    /** 親ディレクトリ　アクセス権限全件表示フラグ */
    private int fil060ParentAccessAllDspFlg__ = 0;
    /** 親ディレクトリ　ゼロユーザフラグ */
    private String fil060ParentZeroUser__ = "0";


    /**
     * <br>[機  能] 入力チェックを行う（フォルダ登録時）
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors fil060validateCheck(
            Connection con, RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //ゼロユーザチェック
        if (fil060ParentZeroUser__.equals("1")) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = new ActionMessage(
                    "error.input.format.file",
                    gsMsg.getMessage("cmn.folder"),
                    gsMsg.getMessage("fil.102"));
            StrutsUtil.addMessage(errors, msg, "error.input.format.file");
            return errors;
        }

        // 更新者チェック
        boolean isUpUserCheck = true;
        int userSid = -1;
        if (StringUtils.isNullOrEmpty(fil060EditId__)) {
            // ユーザSID or グループSID が未入力
            isUpUserCheck = false;
        } else {
            if (reqMdl != null && reqMdl.getSession() != null) {
                BaseUserModel usModel = reqMdl.getSmodel();
                userSid = usModel.getUsrsid(); //セッションユーザSID
            }

            // ユーザが使用可能かチェック
            if (userSid < 0) {
                isUpUserCheck = false;
            } else if (fil060EditId__.startsWith("G")) {
                UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);

                // グループSIDをチェック
                isUpUserCheck = false;
                ArrayList<GroupModel> gpList = gpDao.selectGroupNmListOrderbyClass(userSid);
                if (gpList != null && !gpList.isEmpty()) {
                    int groupSid = NullDefault.getInt(fil060EditId__.substring("G".length()), -1);
                    for (GroupModel gpMdl : gpList) {
                        if (gpMdl.getGroupSid() == groupSid) {
                            isUpUserCheck = true; // 使用可能なグループ
                            break;
                        }
                    }
                }
            } else {
                // ユーザSIDをチェック
                if (NullDefault.getInt(fil060EditId__, -1) != userSid) {
                    isUpUserCheck = false;
                }
            }
        }
        // 不正なユーザSIDorグループSIDを指定されている場合
        if (!isUpUserCheck) {
            // 再描画の際に不正なユーザ名が表示されないようにセッションユーザSIDを入力する
            fil060EditId__ = String.valueOf(userSid);

            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = new ActionMessage(
                    "error.select3.required.text",
                    gsMsg.getMessage("cmn.update.user"));
            StrutsUtil.addMessage(errors, msg, "error.select3.required.text");
        }

        //ディレクトリ階層チェック
        FileDirectoryDao dao = new FileDirectoryDao(con);
        FileDirectoryModel mdl = dao.getNewDirectory(
                NullDefault.getInt(getFil050ParentDirSid(), -1));
        if (mdl != null) {
            if (mdl.getFdrLevel() >= GSConstFile.DIRECTORY_LEVEL_10) {
                msg = new ActionMessage(
                        "error.over.level.create.dir", GSConstFile.DIRECTORY_LEVEL_10);
                StrutsUtil.addMessage(errors, msg, "error.over.level.create.dir");
                return errors;
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textFolderName = gsMsg.getMessage("fil.21");
        String textBiko = gsMsg.getMessage("cmn.memo");

        //フォルダ名
        GSValidateFile.validateTextBoxInput(errors,
                                         fil060DirName__,
                                         textFolderName,
                                         GSConstFile.MAX_LENGTH_FOLDER_NAME,
                                         true);

        //アクセス権限 制限する場合は必須
        if (fil060AccessKbn__.equals(String.valueOf(GSConstFile.ACCESS_KBN_ON))) {
            //フルアクセスユーザを選択チェック
            if ((fil060SvAcFull__ == null || fil060SvAcFull__.length < 1)
             && (fil060SvAcRead__ == null || fil060SvAcRead__.length < 1)) {
                String textAc = gsMsg.getMessage("fil.102");
                msg =
                    new ActionMessage(
                        "error.select.required.text", textAc);
                StrutsUtil.addMessage(errors, msg, "fil060SvAcFull");
            }
            List<String> svAcList = new ArrayList<String>();
            if (fil060SvAcFull__ != null) {
                svAcList.addAll(Arrays.asList(fil060SvAcFull__));
            }
            if (fil060SvAcRead__ != null) {
                svAcList.addAll(Arrays.asList(fil060SvAcRead__));
            }
            String[] svAc = svAcList.toArray(new String[svAcList.size()]);

            //選択ユーザ権限チェック(追加・変更・削除・閲覧)
            FilValidateUtil.validateSltDaccess(errors,
                                               reqMdl,
                                               con,
                                               Integer.parseInt(GSConstFile.ACCESS_KBN_READ),
                                               svAc,
                                               NullDefault.getInt(getFil050ParentDirSid(), -1),
                                               gsMsg);

        }

        //備考
        GSValidateFile.validateTextarea(errors,
                                         fil060Biko__,
                                         textBiko,
                                         GSConstFile.MAX_LENGTH_FOLDER_BIKO,
                                         false);
        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う(削除ボタンクリック時)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors checkDelFolder(Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String fieldFix = "fil060DirName.";

        int dirSid = NullDefault.getInt(getFil050DirSid(), -1);
        //削除対象ディレクトリの下の階層の有無チェック
        FileDirectoryDao dao = new FileDirectoryDao(con);
        List<FileDirectoryModel> childList = dao.getChildDirectory(dirSid);

        if (!(childList == null || childList.size() < 1)) {
            msg = new ActionMessage("error.directory.not.empty");
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
        }

        return errors;
    }

    /**
     * <p>fil060Biko を取得します。
     * @return fil060Biko
     */
    public String getFil060Biko() {
        return fil060Biko__;
    }
    /**
     * <p>fil060Biko をセットします。
     * @param fil060Biko fil060Biko
     */
    public void setFil060Biko(String fil060Biko) {
        fil060Biko__ = fil060Biko;
    }
    /**
     * <p>fil060DirName を取得します。
     * @return fil060DirName
     */
    public String getFil060DirName() {
        return fil060DirName__;
    }
    /**
     * <p>fil060DirName をセットします。
     * @param fil060DirName fil060DirName
     */
    public void setFil060DirName(String fil060DirName) {
        fil060DirName__ = fil060DirName;
    }
    /**
     * <p>fil060TempFiles を取得します。
     * @return fil060TempFiles
     */
    public String[] getFil060TempFiles() {
        return fil060TempFiles__;
    }
    /**
     * <p>fil060TempFiles をセットします。
     * @param fil060TempFiles fil060TempFiles
     */
    public void setFil060TempFiles(String[] fil060TempFiles) {
        fil060TempFiles__ = fil060TempFiles;
    }
    /**
     * <p>fil060CmdMode を取得します。
     * @return fil060CmdMode
     */
    public int getFil060CmdMode() {
        return fil060CmdMode__;
    }
    /**
     * <p>fil060CmdMode をセットします。
     * @param fil060CmdMode fil060CmdMode
     */
    public void setFil060CmdMode(int fil060CmdMode) {
        fil060CmdMode__ = fil060CmdMode;
    }
    /**
     * <p>fil060PluginId を取得します。
     * @return fil060PluginId
     */
    public String getFil060PluginId() {
        return fil060PluginId__;
    }
    /**
     * <p>fil060PluginId をセットします。
     * @param fil060PluginId fil060PluginId
     */
    public void setFil060PluginId(String fil060PluginId) {
        fil060PluginId__ = fil060PluginId;
    }

    /**
     * <p>fil060EditId を取得します。
     * @return fil060EditId
     */
    public String getFil060EditId() {
        return fil060EditId__;
    }

    /**
     * <p>fil060EditId をセットします。
     * @param fil060EditId fil060EditId
     */
    public void setFil060EditId(String fil060EditId) {
        fil060EditId__ = fil060EditId;
    }

    /**
     * <p>fil060groupList を取得します。
     * @return fil060groupList
     */
    public List<LabelValueBean> getFil060groupList() {
        return fil060groupList__;
    }

    /**
     * <p>fil060groupList をセットします。
     * @param fil060groupList fil060groupList
     */
    public void setFil060groupList(List<LabelValueBean> fil060groupList) {
        fil060groupList__ = fil060groupList;
    }

    /**
     * <p>fil060ParentAccessAll を取得します。
     * @return fil060ParentAccessAll
     */
    public String getFil060ParentAccessAll() {
        return fil060ParentAccessAll__;
    }

    /**
     * <p>fil060ParentAccessAll をセットします。
     * @param fil060ParentAccessAll fil060ParentAccessAll
     */
    public void setFil060ParentAccessAll(String fil060ParentAccessAll) {
        this.fil060ParentAccessAll__ = fil060ParentAccessAll;
    }

    /**
     * <p>fil060AccessKbn を取得します。
     * @return fil060AccessKbn
     */
    public String getFil060AccessKbn() {
        return fil060AccessKbn__;
    }

    /**
     * <p>fil060AccessKbn をセットします。
     * @param fil060AccessKbn fil060AccessKbn
     */
    public void setFil060AccessKbn(String fil060AccessKbn) {
        this.fil060AccessKbn__ = fil060AccessKbn;
    }

    /**
     * <p>file060AdaptIncFile を取得します。
     * @return file060AdaptIncFile
     */
    public String getFile060AdaptIncFile() {
        return file060AdaptIncFile__;
    }

    /**
     * <p>file060AdaptIncFile をセットします。
     * @param file060AdaptIncFile file060AdaptIncFile
     */
    public void setFile060AdaptIncFile(String file060AdaptIncFile) {
        this.file060AdaptIncFile__ = file060AdaptIncFile;
    }

    /**
     * <p>fil060AcEditSltGroup を取得します。
     * @return fil060AcEditSltGroup
     */
    public String getFil060AcEditSltGroup() {
        return fil060AcEditSltGroup__;
    }

    /**
     * <p>fil060AcEditSltGroup をセットします。
     * @param fil060AcEditSltGroup fil060AcEditSltGroup
     */
    public void setFil060AcEditSltGroup(String fil060AcEditSltGroup) {
        this.fil060AcEditSltGroup__ = fil060AcEditSltGroup;
    }

    /**
     * <p>fil060AcReadSltGroup を取得します。
     * @return fil060AcReadSltGroup
     */
    public String getFil060AcReadSltGroup() {
        return fil060AcReadSltGroup__;
    }

    /**
     * <p>fil060AcReadSltGroup をセットします。
     * @param fil060AcReadSltGroup fil060AcReadSltGroup
     */
    public void setFil060AcReadSltGroup(String fil060AcReadSltGroup) {
        this.fil060AcReadSltGroup__ = fil060AcReadSltGroup;
    }

    /**
     * <p>fil060SvAcFull を取得します。
     * @return fil060SvAcFull
     */
    public String[] getFil060SvAcFull() {
        return fil060SvAcFull__;
    }

    /**
     * <p>fil060SvAcFull をセットします。
     * @param fil060SvAcFull fil060SvAcFull
     */
    public void setFil060SvAcFull(String[] fil060SvAcFull) {
        this.fil060SvAcFull__ = fil060SvAcFull;
    }

    /**
     * <p>fil060SvAcRead を取得します。
     * @return fil060SvAcRead
     */
    public String[] getFil060SvAcRead() {
        return fil060SvAcRead__;
    }

    /**
     * <p>fil060SvAcRead をセットします。
     * @param fil060SvAcRead fil060SvAcRead
     */
    public void setFil060SvAcRead(String[] fil060SvAcRead) {
        this.fil060SvAcRead__ = fil060SvAcRead;
    }

    /**
     * <p>fil060PublicAcUserUI を取得します。
     * @return fil060PublicAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060Form#fil060PublicAcUserUI__
     */
    public Fil060AuthSelector getFil060PublicAcUserUI() {
        return fil060PublicAcUserUI__;
    }

    /**
     * <p>fil060PublicAcUserUI をセットします。
     * @param fil060PublicAcUserUI fil060PublicAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060Form#fil060PublicAcUserUI__
     */
    public void setFil060PublicAcUserUI(Fil060AuthSelector fil060PublicAcUserUI) {
        fil060PublicAcUserUI__ = fil060PublicAcUserUI;
    }

    /**
     * <p>fil060PrivateAcUserUI を取得します。
     * @return fil060PrivateAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060Form#fil060PrivateAcUserUI__
     */
    public Fil060AuthSelector getFil060PrivateAcUserUI() {
        return fil060PrivateAcUserUI__;
    }

    /**
     * <p>fil060PrivateAcUserUI をセットします。
     * @param fil060PrivateAcUserUI fil060PrivateAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060Form#fil060PrivateAcUserUI__
     */
    public void setFil060PrivateAcUserUI(Fil060AuthSelector fil060PrivateAcUserUI) {
        fil060PrivateAcUserUI__ = fil060PrivateAcUserUI;
    }

    /**
     * <p>fil060ParentEditList を取得します。
     * @return fil060ParentEditList
     */
    public ArrayList<FileParentAccessDspModel> getFil060ParentEditList() {
        return fil060ParentEditList__;
    }

    /**
     * <p>fil060ParentEditList をセットします。
     * @param fil060ParentEditList fil060ParentEditList
     */
    public void setFil060ParentEditList(ArrayList<FileParentAccessDspModel> fil060ParentEditList) {
        this.fil060ParentEditList__ = fil060ParentEditList;
    }

    /**
     * <p>fil060ParentReadList を取得します。
     * @return fil060ParentReadList
     */
    public ArrayList<FileParentAccessDspModel> getFil060ParentReadList() {
        return fil060ParentReadList__;
    }

    /**
     * <p>fil060ParentReadList をセットします。
     * @param fil060ParentReadList fil060ParentReadList
     */
    public void setFil060ParentReadList(ArrayList<FileParentAccessDspModel> fil060ParentReadList) {
        this.fil060ParentReadList__ = fil060ParentReadList;
    }

    /**
     * <p>fil060ParentAccessAllDspFlg を取得します。
     * @return fil060ParentAccessAllDspFlg
     */
    public int getFil060ParentAccessAllDspFlg() {
        return fil060ParentAccessAllDspFlg__;
    }

    /**
     * <p>fil060ParentAccessAllDspFlg をセットします。
     * @param fil060ParentAccessAllDspFlg fil060ParentAccessAllDspFlg
     */
    public void setFil060ParentAccessAllDspFlg(int fil060ParentAccessAllDspFlg) {
        this.fil060ParentAccessAllDspFlg__ = fil060ParentAccessAllDspFlg;
    }

    /**
     * <p>fil060ParentZeroUser を取得します。
     * @return fil060ParentZeroUser
     */
    public String getFil060ParentZeroUser() {
        return fil060ParentZeroUser__;
    }

    /**
     * <p>fil060ParentZeroUser をセットします。
     * @param fil060ParentZeroUser fil060ParentZeroUser
     */
    public void setFil060ParentZeroUser(String fil060ParentZeroUser) {
        this.fil060ParentZeroUser__ = fil060ParentZeroUser;
    }

}