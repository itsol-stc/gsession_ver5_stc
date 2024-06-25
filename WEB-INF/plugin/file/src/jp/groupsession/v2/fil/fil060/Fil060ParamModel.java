package jp.groupsession.v2.fil.fil060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil050.Fil050ParamModel;
import jp.groupsession.v2.fil.fil060.ui.Fil060AuthSelector;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileParentAccessDspModel;

/**
 * <br>[機  能] フォルダ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil060ParamModel extends Fil050ParamModel {

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
    private Fil060AuthSelector fil060PublicAcUserUI__ = null;
    /** アクセス権限 個人キャビネット用UI */
    private Fil060AuthSelector fil060PrivateAcUserUI__ = null;

    /** 親ディレクトリ　追加・変更・削除ユーザリスト */
    private ArrayList<FileParentAccessDspModel> fil060ParentEditList__ = null;
    /** 親ディレクトリ　閲覧ユーザリスト */
    private ArrayList<FileParentAccessDspModel> fil060ParentReadList__ = null;

    /** 親ディレクトリ　アクセス権限全件表示フラグ */
    private int fil060ParentAccessAllDspFlg__ = 0;
    /** 親ディレクトリ　ゼロユーザフラグ */
    private String fil060ParentZeroUser__ = "0";


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
     * @see jp.groupsession.v2.fil.fil060.Fil060ParamModel#fil060PublicAcUserUI__
     */
    public Fil060AuthSelector getFil060PublicAcUserUI() {
        return fil060PublicAcUserUI__;
    }

    /**
     * <p>fil060PublicAcUserUI をセットします。
     * @param fil060PublicAcUserUI fil060PublicAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060ParamModel#fil060PublicAcUserUI__
     */
    public void setFil060PublicAcUserUI(Fil060AuthSelector fil060PublicAcUserUI) {
        fil060PublicAcUserUI__ = fil060PublicAcUserUI;
    }

    /**
     * <p>fil060PrivateAcUserUI を取得します。
     * @return fil060PrivateAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060ParamModel#fil060PrivateAcUserUI__
     */
    public Fil060AuthSelector getFil060PrivateAcUserUI() {
        return fil060PrivateAcUserUI__;
    }

    /**
     * <p>fil060PrivateAcUserUI をセットします。
     * @param fil060PrivateAcUserUI fil060PrivateAcUserUI
     * @see jp.groupsession.v2.fil.fil060.Fil060ParamModel#fil060PrivateAcUserUI__
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