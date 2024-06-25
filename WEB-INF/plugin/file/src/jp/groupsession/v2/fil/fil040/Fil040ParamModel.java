package jp.groupsession.v2.fil.fil040;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil030.Fil030ParamModel;
import jp.groupsession.v2.fil.fil090.Fil090Biz;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] フォルダ情報一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil040ParamModel extends Fil030ParamModel {


    /** 全て選択・解除用 */
    private String fil040SelectDelAll__ = null;
    /** 削除用チェックボックス */
    private String[] fil040SelectDel__;
    /** 移動先ディレクトリ */
    private String moveToDir__ = null;
    /** Unlock用ディレクトリSID */
    private String fil040SelectUnlock__ = null;
    /** Unlock用ディレクトリバージョン */
    private String fil040SelectUnlockVer__ = null;
    /** ソートキー */
    private int fil040SortKey__ = GSConstFile.SORT_NAME;
    /** オーダーキー */
    private int fil040OrderKey__ = GSConst.ORDER_KEY_ASC;
    /** 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット */
    private int fil040PersonalFlg__ = Integer.parseInt(GSConstFile.DSP_CABINET_PUBLIC);
    /** 個人キャビネット所有ユーザSID */
    private int fil040PersonalCabOwnerSid__ = 0;
    /** 個人キャビネット所有ユーザ名 */
    private String fil040PersonalCabOwnerName__ = null;
    /** サイドメニュー 表示フラグ */
    private int fil040SideMenuFlg__ = GSConst.DSP_OK;

    //表示用
    /** キャビネット名称 */
    private String fil040CabinetName__ = null;
    /** ディレクトリパス情報(タイトル表示用) */
    private ArrayList<FileDirectoryModel> fil040DirectoryPathList__ = null;

    /** カレントディレクトリ情報一覧 */
    private ArrayList<FileDirectoryDspModel> fil040DirectoryList__ = null;
    /** ロック解除権限有無 */
    private String fil040UnLockAuth__ = GSConstFile.UNLOCK_AUTH_OFF;
    /** 追加/削除ボタン有無 */
    private String fil040DspAddBtn__ = GSConstFile.DSP_KBN_OFF;
    /** フォルダ追加ボタン有無 */
    private String fil040DspFolderAddBtn__ = GSConstFile.DSP_KBN_OFF;
    /** URL文字列 */
    private String fil040UrlString__;

    /** キャビネットコンボ選択値 */
    private String fil040SelectCabinet__ = null;
    /** キャビネットコンボ */
    private List<LabelValueBean> fil040CabinetList__ = null;

    /** 一括移動区分 */
    private int fil090SelectPluralKbn__ = Fil090Biz.MOVE_PLURAL_NO;

    /** 全て選択・解除用有無 */
    private String fil040DspSelectDelAll__ = "0";

    /** ファイル削除時 操作コメント */
    private String fil040OpeComment__ = null; 

    /**
     * <p>fil040DspFolderAddBtn を取得します。
     * @return fil040DspFolderAddBtn
     */
    public String getFil040DspFolderAddBtn() {
        return fil040DspFolderAddBtn__;
    }
    /**
     * <p>fil040DspFolderAddBtn をセットします。
     * @param fil040DspFolderAddBtn fil040DspFolderAddBtn
     */
    public void setFil040DspFolderAddBtn(String fil040DspFolderAddBtn) {
        fil040DspFolderAddBtn__ = fil040DspFolderAddBtn;
    }
    /**
     * @return fil040DspAddBtn
     */
    public String getFil040DspAddBtn() {
        return fil040DspAddBtn__;
    }
    /**
     * @param fil040DspAddBtn 設定する fil040DspAddBtn
     */
    public void setFil040DspAddBtn(String fil040DspAddBtn) {
        fil040DspAddBtn__ = fil040DspAddBtn;
    }
    /**
     * @return moveToDir
     */
    public String getMoveToDir() {
        return moveToDir__;
    }
    /**
     * @param moveToDir 設定する moveToDir
     */
    public void setMoveToDir(String moveToDir) {
        moveToDir__ = moveToDir;
    }
    /**
     * @return fil040CabinetName
     */
    public String getFil040CabinetName() {
        return fil040CabinetName__;
    }
    /**
     * @param fil040CabinetName 設定する fil040CabinetName
     */
    public void setFil040CabinetName(String fil040CabinetName) {
        fil040CabinetName__ = fil040CabinetName;
    }
    /**
     * @return fil040DirectoryPathList
     */
    public ArrayList<FileDirectoryModel> getFil040DirectoryPathList() {
        return fil040DirectoryPathList__;
    }
    /**
     * @param fil040DirectoryPathList 設定する fil040DirectoryPathList
     */
    public void setFil040DirectoryPathList(
            ArrayList<FileDirectoryModel> fil040DirectoryPathList) {
        fil040DirectoryPathList__ = fil040DirectoryPathList;
    }
    /**
     * @return fil040OrderKey
     */
    public int getFil040OrderKey() {
        return fil040OrderKey__;
    }

    /**
     * <p> fil040PersonalFlgを取得します。
     * @return fil040PersonalFlg
     * */
    public int getFil040PersonalFlg() {
        return fil040PersonalFlg__;
    }
    /**
     * <p> fil040PersonalFlgをセットします。
     * @param fil040PersonalFlg fil040PersonalFlg
     * */
    public void setFil040PersonalFlg(int fil040PersonalFlg) {
        fil040PersonalFlg__ = fil040PersonalFlg;
    }

    /**
     * <p> fil040PersonalCabOwnerSidを取得します。
     * @return fil040PersonalCabOwnerSid
     * */
    public int getFil040PersonalCabOwnerSid() {
        return fil040PersonalCabOwnerSid__;
    }
    /**
     * <p> fil040PersonalCabOwnerSidをセットします。
     * @param fil040PersonalCabOwnerSid fil040PersonalCabOwnerSid
     * */
    public void setFil040PersonalCabOwnerSid(int fil040PersonalCabOwnerSid) {
        fil040PersonalCabOwnerSid__ = fil040PersonalCabOwnerSid;
    }

    /**
     * <p> fil040PersonalCabOwnerNameを取得します。
     * @return fil040PersonalCabOwnerName
     * */
    public String getFil040PersonalCabOwnerName() {
        return fil040PersonalCabOwnerName__;
    }
    /**
     * <p> fil040PersonalCabOwnerNameをセットします。
     * @param fil040PersonalCabOwnerName fil040PersonalCabOwnerName
     * */
    public void setFil040PersonalCabOwnerName(String fil040PersonalCabOwnerName) {
        fil040PersonalCabOwnerName__ = fil040PersonalCabOwnerName;
    }

    /**
     * <p>fil040SideMenuFlg を取得します。
     * @return fil040SideMenuFlg
     * @see jp.groupsession.v2.fil.fil040.Fil040Form#fil040SideMenuFlg__
     */
    public int getFil040SideMenuFlg() {
        return fil040SideMenuFlg__;
    }
    /**
     * <p>fil040SideMenuFlg をセットします。
     * @param fil040SideMenuFlg fil040SideMenuFlg
     * @see jp.groupsession.v2.fil.fil040.Fil040Form#fil040SideMenuFlg__
     */
    public void setFil040SideMenuFlg(int fil040SideMenuFlg) {
        fil040SideMenuFlg__ = fil040SideMenuFlg;
    }

    /**
     * @param fil040OrderKey 設定する fil040OrderKey
     */
    public void setFil040OrderKey(int fil040OrderKey) {
        fil040OrderKey__ = fil040OrderKey;
    }
    /**
     * @return fil040SortKey
     */
    public int getFil040SortKey() {
        return fil040SortKey__;
    }
    /**
     * @param fil040SortKey 設定する fil040SortKey
     */
    public void setFil040SortKey(int fil040SortKey) {
        fil040SortKey__ = fil040SortKey;
    }
    /**
     * @return fil040UrlString
     */
    public String getFil040UrlString() {
        return fil040UrlString__;
    }
    /**
     * @param fil040UrlString 設定する fil040UrlString
     */
    public void setFil040UrlString(String fil040UrlString) {
        fil040UrlString__ = fil040UrlString;
    }
    /**
     * @return fil040DirectoryList
     */
    public ArrayList<FileDirectoryDspModel> getFil040DirectoryList() {
        return fil040DirectoryList__;
    }
    /**
     * @param fil040DirectoryList 設定する fil040DirectoryList
     */
    public void setFil040DirectoryList(
            ArrayList<FileDirectoryDspModel> fil040DirectoryList) {
        fil040DirectoryList__ = fil040DirectoryList;
    }
    /**
     * @return fil040UnLockAuth
     */
    public String getFil040UnLockAuth() {
        return fil040UnLockAuth__;
    }
    /**
     * @param fil040UnLockAuth 設定する fil040UnLockAuth
     */
    public void setFil040UnLockAuth(String fil040UnLockAuth) {
        fil040UnLockAuth__ = fil040UnLockAuth;
    }
    /**
     * @return fil040SelectDel
     */
    public String[] getFil040SelectDel() {
        return fil040SelectDel__;
    }
    /**
     * @param fil040SelectDel 設定する fil040SelectDel
     */
    public void setFil040SelectDel(String[] fil040SelectDel) {
        fil040SelectDel__ = fil040SelectDel;
    }
    /**
     * <p>fil040SelectDelAll を取得します。
     * @return fil040SelectDelAll
     */
    public String getFil040SelectDelAll() {
        return fil040SelectDelAll__;
    }
    /**
     * <p>fil040SelectDelAll をセットします。
     * @param fil040SelectDelAll fil040SelectDelAll
     */
    public void setFil040SelectDelAll(String fil040SelectDelAll) {
        fil040SelectDelAll__ = fil040SelectDelAll;
    }

    /**
     * <p>fil040SelectUnlock を取得します。
     * @return fil040SelectUnlock
     */
    public String getFil040SelectUnlock() {
        return fil040SelectUnlock__;
    }
    /**
     * <p>fil040SelectUnlock をセットします。
     * @param fil040SelectUnlock fil040SelectUnlock
     */
    public void setFil040SelectUnlock(String fil040SelectUnlock) {
        fil040SelectUnlock__ = fil040SelectUnlock;
    }

    /**
     * <p>fil040SelectUnlockVer を取得します。
     * @return fil040SelectUnlockVer
     */
    public String getFil040SelectUnlockVer() {
        return fil040SelectUnlockVer__;
    }
    /**
     * <p>fil040SelectUnlockVer をセットします。
     * @param fil040SelectUnlockVer fil040SelectUnlockVer
     */
    public void setFil040SelectUnlockVer(String fil040SelectUnlockVer) {
        fil040SelectUnlockVer__ = fil040SelectUnlockVer;
    }
    /**
     * <p>fil040SelectCabinet を取得します。
     * @return fil040SelectCabinet
     */
    public String getFil040SelectCabinet() {
        return fil040SelectCabinet__;
    }
    /**
     * <p>fil040SelectCabinet をセットします。
     * @param fil040SelectCabinet fil040SelectCabinet
     */
    public void setFil040SelectCabinet(String fil040SelectCabinet) {
        fil040SelectCabinet__ = fil040SelectCabinet;
    }
    /**
     * <p>fil040CabinetList を取得します。
     * @return fil040CabinetList
     */
    public List<LabelValueBean> getFil040CabinetList() {
        return fil040CabinetList__;
    }
    /**
     * <p>fil040CabinetList をセットします。
     * @param fil040CabinetList fil040CabinetList
     */
    public void setFil040CabinetList(List<LabelValueBean> fil040CabinetList) {
        fil040CabinetList__ = fil040CabinetList;
    }
    /**
     * <p>fil090SelectPluralKbn を取得します。
     * @return fil090SelectPluralKbn
     */
    public int getFil090SelectPluralKbn() {
        return fil090SelectPluralKbn__;
    }

    /**
     * <p>fil090SelectPluralKbn をセットします。
     * @param fil090SelectPluralKbn fil090SelectPluralKbn
     */
    public void setFil090SelectPluralKbn(int fil090SelectPluralKbn) {
        fil090SelectPluralKbn__ = fil090SelectPluralKbn;
    }
    /**
     * <p>fil040DspSelectDelAll を取得します。
     * @return fil040DspSelectDelAll
     */
    public String getFil040DspSelectDelAll() {
        return fil040DspSelectDelAll__;
    }
    /**
     * <p>fil040DspSelectDelAll をセットします。
     * @param fil040DspSelectDelAll fil040DspSelectDelAll
     */
    public void setFil040DspSelectDelAll(String fil040DspSelectDelAll) {
        this.fil040DspSelectDelAll__ = fil040DspSelectDelAll;
    }
    /**
     * <p>fil040OpeComment を取得します。
     * @return fil040OpeComment
     * @see jp.groupsession.v2.fil.fil040.Fil040ParamModel#fil040OpeComment__
     */
    public String getFil040OpeComment() {
        return fil040OpeComment__;
    }
    /**
     * <p>fil040OpeComment をセットします。
     * @param fil040OpeComment fil040OpeComment
     * @see jp.groupsession.v2.fil.fil040.Fil040ParamModel#fil040OpeComment__
     */
    public void setFil040OpeComment(String fil040OpeComment) {
        fil040OpeComment__ = fil040OpeComment;
    }
}