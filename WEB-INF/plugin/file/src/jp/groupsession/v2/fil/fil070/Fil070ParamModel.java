package jp.groupsession.v2.fil.fil070;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil060.Fil060ParamModel;
import jp.groupsession.v2.fil.fil070.model.Fil070Model;
import jp.groupsession.v2.fil.model.FileDAccessUserModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] ファイル詳細画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil070ParamModel extends Fil060ParamModel {

    /** ソートする項目 */
    private String fil070SortKey__ = "0";
    /** 昇順・降順 */
    private String fil070OrderKey__ = String.valueOf(GSConst.ORDER_KEY_DESC);
    /** GS管理者権限 有無 */
    private String fil070AdminFlg__ = String.valueOf(GSConst.USER_NOT_ADMIN);

    /** 表示切替用 更新履歴・アクセス制御*/
    private String fil070DspMode__ = GSConstFile.DSP_MODE_HIST;
    /** ディレクトリSID */
    private String fil070DirSid__ = null;
    /** 親ディレクトリSID */
    private String fil070ParentDirSid__ = null;
    /** フォルダパス */
    private String fil070FolderPath__ = null;
    /** 備考 */
    private String fil070Biko__ = null;
    /** ショートカット有無 */
    private String fil070ShortcutKbn__ = null;
    /** ショートカットパス */
    private String fil070ShortcutPath__ = null;
    /** ファイルサイズ */
    private String fil070FileSize__ = null;
    /** バージョン */
    private String fil070VerKbn__ = null;
    /** ファイル */
    private List<LabelValueBean> fil070FileLabelList__ = null;
    /** 履歴　選択したディレクトリSID */
    private String fil070SltDirSid__ = null;
    /** 履歴　選択したディレクトリバージョン */
    private String fil070SltDirVer__ = null;
    /** ログインユーザ編集権限区分 */
    private String fil070EditAuthKbn__ = null;

    /** ファイルロック区分 */
    private String fil070FileLockKbn__ = String.valueOf(GSConstFile.LOCK_KBN_OFF);
    /** ファイルロックユーザ */
    private String fil070FileLockUser__ = null;

    /** 更新履歴一覧 */
    private List<Fil070Model> fil070RekiList__ = null;
    /** アクセス制御一覧 */
    private List<FileDAccessUserModel> fil070AccessList__ = null;
    /** ページ1 */
    private int fil070PageNum1__ = 1;
    /** ページ2 */
    private int fil070PageNum2__ = 1;
    /** ページラベル */
    ArrayList < LabelValueBean > fil070PageLabel__;

    /** 削除区分 */
    private int fil070Jtkbn__ = 0;

    /** ファイル詳細URL */
    private String fileUrl__ = null;

    /** 表示ページ */
    private int fil240PageNum__ = 1;

    /**
     * <p>fil070Biko を取得します。
     * @return fil070Biko
     */
    public String getFil070Biko() {
        return fil070Biko__;
    }
    /**
     * <p>fil070Biko をセットします。
     * @param fil070Biko fil070Biko
     */
    public void setFil070Biko(String fil070Biko) {
        fil070Biko__ = fil070Biko;
    }
    /**
     * <p>fil070FolderPath を取得します。
     * @return fil070FolderPath
     */
    public String getFil070FolderPath() {
        return fil070FolderPath__;
    }
    /**
     * <p>fil070FolderPath をセットします。
     * @param fil070FolderPath fil070FolderPath
     */
    public void setFil070FolderPath(String fil070FolderPath) {
        fil070FolderPath__ = fil070FolderPath;
    }
    /**
     * <p>fil070AdminFlg を取得します。
     * @return fil070AdminFlg
     */
    public String getFil070AdminFlg() {
        return fil070AdminFlg__;
    }
    /**
     * <p>fil070AdminFlg をセットします。
     * @param fil070AdminFlg fil070AdminFlg
     */
    public void setFil070AdminFlg(String fil070AdminFlg) {
        fil070AdminFlg__ = fil070AdminFlg;
    }
    /**
     * <p>fil070DspMode を取得します。
     * @return fil070DspMode
     */
    public String getFil070DspMode() {
        return fil070DspMode__;
    }
    /**
     * <p>fil070DspMode をセットします。
     * @param fil070DspMode fil070DspMode
     */
    public void setFil070DspMode(String fil070DspMode) {
        this.fil070DspMode__ = fil070DspMode;
    }
    /**
     * <p>fil070OrderKey を取得します。
     * @return fil070OrderKey
     */
    public String getFil070OrderKey() {
        return fil070OrderKey__;
    }
    /**
     * <p>fil070OrderKey をセットします。
     * @param fil070OrderKey fil070OrderKey
     */
    public void setFil070OrderKey(String fil070OrderKey) {
        fil070OrderKey__ = fil070OrderKey;
    }
    /**
     * <p>fil070SortKey を取得します。
     * @return fil070SortKey
     */
    public String getFil070SortKey() {
        return fil070SortKey__;
    }
    /**
     * <p>fil070SortKey をセットします。
     * @param fil070SortKey fil070SortKey
     */
    public void setFil070SortKey(String fil070SortKey) {
        fil070SortKey__ = fil070SortKey;
    }

    /**
     * <p>fil070ShortcutPath を取得します。
     * @return fil070ShortcutPath
     */
    public String getFil070ShortcutPath() {
        return fil070ShortcutPath__;
    }
    /**
     * <p>fil070ShortcutPath をセットします。
     * @param fil070ShortcutPath fil070ShortcutPath
     */
    public void setFil070ShortcutPath(String fil070ShortcutPath) {
        fil070ShortcutPath__ = fil070ShortcutPath;
    }
    /**
     * <p>fil070ShortcutKbn を取得します。
     * @return fil070ShortcutKbn
     */
    public String getFil070ShortcutKbn() {
        return fil070ShortcutKbn__;
    }
    /**
     * <p>fil070ShortcutKbn をセットします。
     * @param fil070ShortcutKbn fil070ShortcutKbn
     */
    public void setFil070ShortcutKbn(String fil070ShortcutKbn) {
        fil070ShortcutKbn__ = fil070ShortcutKbn;
    }
    /**
     * <p>fil070RekiList を取得します。
     * @return fil070RekiList
     */
    public List<Fil070Model> getFil070RekiList() {
        return fil070RekiList__;
    }
    /**
     * <p>fil070RekiList をセットします。
     * @param fil070RekiList fil070RekiList
     */
    public void setFil070RekiList(List<Fil070Model> fil070RekiList) {
        fil070RekiList__ = fil070RekiList;
    }
    /**
     * <p>fil070AccessList を取得します。
     * @return fil070AccessList
     */
    public List<FileDAccessUserModel> getFil070AccessList() {
        return fil070AccessList__;
    }
    /**
     * <p>fil070AccessList をセットします。
     * @param fil070AccessList fil070AccessList
     */
    public void setFil070AccessList(List<FileDAccessUserModel> fil070AccessList) {
        this.fil070AccessList__ = fil070AccessList;
    }
    /**
     * <p>fil070SltDirSid を取得します。
     * @return fil070SltDirSid
     */
    public String getFil070SltDirSid() {
        return fil070SltDirSid__;
    }
    /**
     * <p>fil070SltDirSid をセットします。
     * @param fil070SltDirSid fil070SltDirSid
     */
    public void setFil070SltDirSid(String fil070SltDirSid) {
        fil070SltDirSid__ = fil070SltDirSid;
    }
    /**
     * <p>fil070SltDirVer を取得します。
     * @return fil070SltDirVer
     */
    public String getFil070SltDirVer() {
        return fil070SltDirVer__;
    }
    /**
     * <p>fil070SltDirVer をセットします。
     * @param fil070SltDirVer fil070SltDirVer
     */
    public void setFil070SltDirVer(String fil070SltDirVer) {
        fil070SltDirVer__ = fil070SltDirVer;
    }
    /**
     * <p>fil070FileLabelList を取得します。
     * @return fil070FileLabelList
     */
    public List<LabelValueBean> getFil070FileLabelList() {
        return fil070FileLabelList__;
    }
    /**
     * <p>fil070FileLabelList をセットします。
     * @param fil070FileLabelList fil070FileLabelList
     */
    public void setFil070FileLabelList(List<LabelValueBean> fil070FileLabelList) {
        fil070FileLabelList__ = fil070FileLabelList;
    }
    /**
     * <p>fil070DirSid を取得します。
     * @return fil070DirSid
     */
    public String getFil070DirSid() {
        return fil070DirSid__;
    }
    /**
     * <p>fil070DirSid をセットします。
     * @param fil070DirSid fil070DirSid
     */
    public void setFil070DirSid(String fil070DirSid) {
        fil070DirSid__ = fil070DirSid;
    }
    /**
     * <p>fil070ParentDirSid を取得します。
     * @return fil070ParentDirSid
     */
    public String getFil070ParentDirSid() {
        return fil070ParentDirSid__;
    }
    /**
     * <p>fil070ParentDirSid をセットします。
     * @param fil070ParentDirSid fil070ParentDirSid
     */
    public void setFil070ParentDirSid(String fil070ParentDirSid) {
        fil070ParentDirSid__ = fil070ParentDirSid;
    }
    /**
     * <p>fil070FileSize を取得します。
     * @return fil070FileSize
     */
    public String getFil070FileSize() {
        return fil070FileSize__;
    }
    /**
     * <p>fil070FileSize をセットします。
     * @param fil070FileSize fil070FileSize
     */
    public void setFil070FileSize(String fil070FileSize) {
        fil070FileSize__ = fil070FileSize;
    }
    /**
     * <p>fil070VerKbn を取得します。
     * @return fil070VerKbn
     */
    public String getFil070VerKbn() {
        return fil070VerKbn__;
    }
    /**
     * <p>fil070VerKbn をセットします。
     * @param fil070VerKbn fil070VerKbn
     */
    public void setFil070VerKbn(String fil070VerKbn) {
        fil070VerKbn__ = fil070VerKbn;
    }
    /**
     * <p>fileUrl を取得します。
     * @return fileUrl
     */
    public String getFileUrl() {
        return fileUrl__;
    }
    /**
     * <p>fileUrl をセットします。
     * @param fileUrl fileUrl
     */
    public void setFileUrl(String fileUrl) {
        fileUrl__ = fileUrl;
    }
    /**
     * <p>fil070PageLabel を取得します。
     * @return fil070PageLabel
     */
    public ArrayList<LabelValueBean> getFil070PageLabel() {
        return fil070PageLabel__;
    }
    /**
     * <p>fil070PageLabel をセットします。
     * @param fil070PageLabel fil070PageLabel
     */
    public void setFil070PageLabel(ArrayList<LabelValueBean> fil070PageLabel) {
        fil070PageLabel__ = fil070PageLabel;
    }
    /**
     * <p>fil070Jtkbn を取得します。
     * @return fil070Jtkbn
     * @see jp.groupsession.v2.fil.fil070.Fil070ParamModel#fil070Jtkbn__
     */
    public int getFil070Jtkbn() {
        return fil070Jtkbn__;
    }
    /**
     * <p>fil070Jtkbn をセットします。
     * @param fil070Jtkbn fil070Jtkbn
     * @see jp.groupsession.v2.fil.fil070.Fil070ParamModel#fil070Jtkbn__
     */
    public void setFil070Jtkbn(int fil070Jtkbn) {
        fil070Jtkbn__ = fil070Jtkbn;
    }
    /**
     * <p>fil070PageNum1 を取得します。
     * @return fil070PageNum1
     */
    public int getFil070PageNum1() {
        return fil070PageNum1__;
    }
    /**
     * <p>fil070PageNum1 をセットします。
     * @param fil070PageNum1 fil070PageNum1
     */
    public void setFil070PageNum1(int fil070PageNum1) {
        fil070PageNum1__ = fil070PageNum1;
    }
    /**
     * <p>fil070PageNum2 を取得します。
     * @return fil070PageNum2
     */
    public int getFil070PageNum2() {
        return fil070PageNum2__;
    }
    /**
     * <p>fil070PageNum2 をセットします。
     * @param fil070PageNum2 fil070PageNum2
     */
    public void setFil070PageNum2(int fil070PageNum2) {
        fil070PageNum2__ = fil070PageNum2;
    }
    /**
     * <p>fil070EditAuthKbn を取得します。
     * @return fil070EditAuthKbn
     */
    public String getFil070EditAuthKbn() {
        return fil070EditAuthKbn__;
    }
    /**
     * <p>fil070EditAuthKbn をセットします。
     * @param fil070EditAuthKbn fil070EditAuthKbn
     */
    public void setFil070EditAuthKbn(String fil070EditAuthKbn) {
        fil070EditAuthKbn__ = fil070EditAuthKbn;
    }
    /**
     * <p>fil070FileLockKbn を取得します。
     * @return fil070FileLockKbn
     */
    public String getFil070FileLockKbn() {
        return fil070FileLockKbn__;
    }
    /**
     * <p>fil070FileLockKbn をセットします。
     * @param fil070FileLockKbn fil070FileLockKbn
     */
    public void setFil070FileLockKbn(String fil070FileLockKbn) {
        fil070FileLockKbn__ = fil070FileLockKbn;
    }
    /**
     * <p>fil070FileLockUser を取得します。
     * @return fil070FileLockUser
     */
    public String getFil070FileLockUser() {
        return fil070FileLockUser__;
    }
    /**
     * <p>fil070FileLockUser をセットします。
     * @param fil070FileLockUser fil070FileLockUser
     */
    public void setFil070FileLockUser(String fil070FileLockUser) {
        fil070FileLockUser__ = fil070FileLockUser;
    }
    /**
     * <p>fil240PageNum を取得します。
     * @return fil240PageNum
     */
    public int getFil240PageNum() {
        return fil240PageNum__;
    }
    /**
     * <p>fil240PageNum をセットします。
     * @param fil240PageNum fil240PageNum
     */
    public void setFil240PageNum(int fil240PageNum) {
        fil240PageNum__ = fil240PageNum;
    }
}