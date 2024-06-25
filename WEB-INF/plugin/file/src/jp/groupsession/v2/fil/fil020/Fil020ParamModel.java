package jp.groupsession.v2.fil.fil020;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil010.Fil010ParamModel;
import jp.groupsession.v2.fil.fil020.model.FileAccessUserModel;
import jp.groupsession.v2.fil.fil020.model.FileHistoryModel;

/**
 * <br>[機  能] キャビネット詳細画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil020ParamModel extends Fil010ParamModel {

    /** ソートする項目 */
    private String fil020SortKey__ = "1";
    /** 昇順・降順 */
    private String fil020OrderKey__ = String.valueOf(GSConst.ORDER_KEY_DESC);
    /** 編集権限 有無 */
    private String fil020WriteFlg__ = String.valueOf(GSConstFile.EDIT_AUTH_NG);
    /** ファイル編集権限 有無 */
    private String fil020FileWriteFlg__ = String.valueOf(GSConstFile.EDIT_AUTH_NG);
    /** 復旧表示区分 */
    private String fil020RepairDspFlg__ = GSConstFile.DSP_KBN_ON;

    /** 表示切替用 更新履歴・アクセス制御*/
    private String fil020DspMode__ = GSConstFile.DSP_MODE_HIST;

    /** キャビネット名*/
    private String fil020CabinetName__;
    /** 表示用 キャビネットの容量制限*/
    private String fil020DspCapaKbn__;
    /** 表示用 キャビネットの容量上限*/
    private String fil020DspCapaSize__;
    /** 表示用 キャビネットの容量警告*/
    private String fil020DspCapaWarn__;
    /** 表示用 キャビネットのバージョン世代数*/
    private String fil020VerKbn__ = null;
    /** バージョン管理 キャビネットで世代を統一*/
    private String fil020VerAllKbn__ = null;
    /** 表示用 キャビネットの備考*/
    private String fil020DspBiko__;
    /** 表示用 キャビネットのショートカット登録区分*/
    private String fil020ShortCutKbn__;
    /** 表示用 キャビネットの更新通知登録区分*/
    private String fil020CallKbn__;
    /** 更新通知 一括反映区分 */
    private int fil020CallLevelKbn__ = GSConstFile.CALL_LEVEL_OFF;

    /** 履歴　選択したディレクトリSID */
    private String fil020SltDirSid__ = null;
    /** 履歴　選択したディレクトリバージョン */
    private String fil020SltDirVer__ = null;
    /** 更新履歴一覧 */
    private ArrayList<FileHistoryModel> historyList__;
    /** アクセス制御一覧 */
    private ArrayList<FileAccessUserModel> accessList__;
    /** 添付ファイル */
    private List<CmnBinfModel> fil020FileLabelList__ = null;

    /** ページ1 */
    private int fil020Slt_page1__ = 1;
    /** ページ2 */
    private int fil020Slt_page2__ = 1;
    /** ページラベル */
    private ArrayList <LabelValueBean> fil020PageLabel__;

    /** アイコンファイルSID */
    private Long fil020binSid__ = Long.valueOf(0);

    /** 電子帳簿保存法 適用区分*/
    private int fil020ErrlKbn__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 使用区分*/
    private int fil020ErrlAutoKbn__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第1フォルダ*/
    private int fil020ErrlAutoFolder1__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第2フォルダ*/
    private int fil020ErrlAutoFolder2__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第3フォルダ*/
    private int fil020ErrlAutoFolder3__ = -1;

    /**
     * @return fil020PageLabel
     */
    public ArrayList<LabelValueBean> getFil020PageLabel() {
        return fil020PageLabel__;
    }
    /**
     * @param fil020PageLabel 設定する fil020PageLabel
     */
    public void setFil020PageLabel(ArrayList<LabelValueBean> fil020PageLabel) {
        fil020PageLabel__ = fil020PageLabel;
    }

    /**
     * @return fil020Slt_page1
     */
    public int getFil020Slt_page1() {
        return fil020Slt_page1__;
    }
    /**
     * @param fil020Sltpage1 設定する fil020Sltpage1
     */
    public void setFil020Slt_page1(int fil020Sltpage1) {
        fil020Slt_page1__ = fil020Sltpage1;
    }
    /**
     * @return fil020Slt_page2
     */
    public int getFil020Slt_page2() {
        return fil020Slt_page2__;
    }
    /**
     * @param fil020Sltpage2 設定する fil020Sltpage2
     */
    public void setFil020Slt_page2(int fil020Sltpage2) {
        fil020Slt_page2__ = fil020Sltpage2;
    }
    /**
     * @return historyList
     */
    public ArrayList<FileHistoryModel> getHistoryList() {
        return historyList__;
    }
    /**
     * @param historyList 設定する historyList
     */
    public void setHistoryList(ArrayList<FileHistoryModel> historyList) {
        historyList__ = historyList;
    }
    /**
     * @return accessList
     */
    public ArrayList<FileAccessUserModel> getAccessList() {
        return accessList__;
    }
    /**
     * @param accessList 設定する accessList
     */
    public void setAccessList(ArrayList<FileAccessUserModel> accessList) {
        accessList__ = accessList;
    }
    /**
     * @return fil020CabinetName
     */
    public String getFil020CabinetName() {
        return fil020CabinetName__;
    }
    /**
     * @param fil020CabinetName 設定する fil020CabinetName
     */
    public void setFil020CabinetName(String fil020CabinetName) {
        fil020CabinetName__ = fil020CabinetName;
    }
    /**
     * @return fil020CallKbn
     */
    public String getFil020CallKbn() {
        return fil020CallKbn__;
    }
    /**
     * @param fil020CallKbn 設定する fil020CallKbn
     */
    public void setFil020CallKbn(String fil020CallKbn) {
        fil020CallKbn__ = fil020CallKbn;
    }
    /**
     * @return fil020DspBiko
     */
    public String getFil020DspBiko() {
        return fil020DspBiko__;
    }
    /**
     * @param fil020DspBiko 設定する fil020DspBiko
     */
    public void setFil020DspBiko(String fil020DspBiko) {
        fil020DspBiko__ = fil020DspBiko;
    }
    /**
     * @return fil020DspCapaKbn
     */
    public String getFil020DspCapaKbn() {
        return fil020DspCapaKbn__;
    }
    /**
     * @param fil020DspCapaKbn 設定する fil020DspCapaKbn
     */
    public void setFil020DspCapaKbn(String fil020DspCapaKbn) {
        fil020DspCapaKbn__ = fil020DspCapaKbn;
    }
    /**
     * @return fil020DspCapaSize
     */
    public String getFil020DspCapaSize() {
        return fil020DspCapaSize__;
    }
    /**
     * @param fil020DspCapaSize 設定する fil020DspCapaSize
     */
    public void setFil020DspCapaSize(String fil020DspCapaSize) {
        fil020DspCapaSize__ = fil020DspCapaSize;
    }
    /**
     * @return fil020DspCapaWarn
     */
    public String getFil020DspCapaWarn() {
        return fil020DspCapaWarn__;
    }
    /**
     * @param fil020DspCapaWarn 設定する fil020DspCapaWarn
     */
    public void setFil020DspCapaWarn(String fil020DspCapaWarn) {
        fil020DspCapaWarn__ = fil020DspCapaWarn;
    }
    /**
     * @return fil020DspMode
     */
    public String getFil020DspMode() {
        return fil020DspMode__;
    }
    /**
     * @param fil020DspMode 設定する fil020DspMode
     */
    public void setFil020DspMode(String fil020DspMode) {
        fil020DspMode__ = fil020DspMode;
    }

    /**
     * <p>fil020FileLabelList を取得します。
     * @return fil020FileLabelList
     */
    public List<CmnBinfModel> getFil020FileLabelList() {
        return fil020FileLabelList__;
    }
    /**
     * <p>fil020FileLabelList をセットします。
     * @param fil020FileLabelList fil020FileLabelList
     */
    public void setFil020FileLabelList(List<CmnBinfModel> fil020FileLabelList) {
        fil020FileLabelList__ = fil020FileLabelList;
    }
    /**
     * @return fil020OrderKey
     */
    public String getFil020OrderKey() {
        return fil020OrderKey__;
    }
    /**
     * @param fil020OrderKey 設定する fil020OrderKey
     */
    public void setFil020OrderKey(String fil020OrderKey) {
        fil020OrderKey__ = fil020OrderKey;
    }
    /**
     * @return fil020ShortCutKbn
     */
    public String getFil020ShortCutKbn() {
        return fil020ShortCutKbn__;
    }
    /**
     * @param fil020ShortCutKbn 設定する fil020ShortCutKbn
     */
    public void setFil020ShortCutKbn(String fil020ShortCutKbn) {
        fil020ShortCutKbn__ = fil020ShortCutKbn;
    }
    /**
     * @return fil020SltDirSid
     */
    public String getFil020SltDirSid() {
        return fil020SltDirSid__;
    }
    /**
     * @param fil020SltDirSid 設定する fil020SltDirSid
     */
    public void setFil020SltDirSid(String fil020SltDirSid) {
        fil020SltDirSid__ = fil020SltDirSid;
    }
    /**
     * @return fil020SltDirVer
     */
    public String getFil020SltDirVer() {
        return fil020SltDirVer__;
    }
    /**
     * @param fil020SltDirVer 設定する fil020SltDirVer
     */
    public void setFil020SltDirVer(String fil020SltDirVer) {
        fil020SltDirVer__ = fil020SltDirVer;
    }
    /**
     * @return fil020SortKey
     */
    public String getFil020SortKey() {
        return fil020SortKey__;
    }
    /**
     * @param fil020SortKey 設定する fil020SortKey
     */
    public void setFil020SortKey(String fil020SortKey) {
        fil020SortKey__ = fil020SortKey;
    }
    /**
     * @return fil020VerAllKbn
     */
    public String getFil020VerAllKbn() {
        return fil020VerAllKbn__;
    }
    /**
     * @param fil020VerAllKbn 設定する fil020VerAllKbn
     */
    public void setFil020VerAllKbn(String fil020VerAllKbn) {
        fil020VerAllKbn__ = fil020VerAllKbn;
    }
    /**
     * @return fil020VerKbn
     */
    public String getFil020VerKbn() {
        return fil020VerKbn__;
    }
    /**
     * @param fil020VerKbn 設定する fil020VerKbn
     */
    public void setFil020VerKbn(String fil020VerKbn) {
        fil020VerKbn__ = fil020VerKbn;
    }
    /**
     * @return fil020WriteFlg
     */
    public String getFil020WriteFlg() {
        return fil020WriteFlg__;
    }
    /**
     * @param fil020WriteFlg 設定する fil020WriteFlg
     */
    public void setFil020WriteFlg(String fil020WriteFlg) {
        fil020WriteFlg__ = fil020WriteFlg;
    }
    /**
     * <p>fil020FileWriteFlg を取得します。
     * @return fil020FileWriteFlg
     */
    public String getFil020FileWriteFlg() {
        return fil020FileWriteFlg__;
    }
    /**
     * <p>fil020FileWriteFlg をセットします。
     * @param fil020FileWriteFlg fil020FileWriteFlg
     */
    public void setFil020FileWriteFlg(String fil020FileWriteFlg) {
        fil020FileWriteFlg__ = fil020FileWriteFlg;
    }
    /**
     * <p>fil020RepairDspFlg を取得します。
     * @return fil020RepairDspFlg
     */
    public String getFil020RepairDspFlg() {
        return fil020RepairDspFlg__;
    }
    /**
     * <p>fil020RepairDspFlg をセットします。
     * @param fil020RepairDspFlg fil020RepairDspFlg
     */
    public void setFil020RepairDspFlg(String fil020RepairDspFlg) {
        this.fil020RepairDspFlg__ = fil020RepairDspFlg;
    }
    /**
     * <p>fil020binSid を取得します。
     * @return fil020binSid
     */
    public Long getFil020binSid() {
        return fil020binSid__;
    }
    /**
     * <p>fil020binSid をセットします。
     * @param fil020binSid fil020binSid
     */
    public void setFil020binSid(Long fil020binSid) {
        fil020binSid__ = fil020binSid;
    }
    /**
     * <p>fil020CallLevelKbn を取得します。
     * @return fil020CallLevelKbn
     */
    public int getFil020CallLevelKbn() {
        return fil020CallLevelKbn__;
    }
    /**
     * <p>fil020CallLevelKbn をセットします。
     * @param fil020CallLevelKbn fil020CallLevelKbn
     */
    public void setFil020CallLevelKbn(int fil020CallLevelKbn) {
        fil020CallLevelKbn__ = fil020CallLevelKbn;
    }
    /**
     * <p>fil020ErrlKbn を取得します。
     * @return fil020ErrlKbn
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlKbn__
     */
    public int getFil020ErrlKbn() {
        return fil020ErrlKbn__;
    }
    /**
     * <p>fil020ErrlKbn をセットします。
     * @param fil020ErrlKbn fil020ErrlKbn
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlKbn__
     */
    public void setFil020ErrlKbn(int fil020ErrlKbn) {
        fil020ErrlKbn__ = fil020ErrlKbn;
    }
    /**
     * <p>fil020ErrlAutoKbn を取得します。
     * @return fil020ErrlAutoKbn
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoKbn__
     */
    public int getFil020ErrlAutoKbn() {
        return fil020ErrlAutoKbn__;
    }
    /**
     * <p>fil020ErrlAutoKbn をセットします。
     * @param fil020ErrlAutoKbn fil020ErrlAutoKbn
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoKbn__
     */
    public void setFil020ErrlAutoKbn(int fil020ErrlAutoKbn) {
        fil020ErrlAutoKbn__ = fil020ErrlAutoKbn;
    }
    /**
     * <p>fil020ErrlAutoFolder1 を取得します。
     * @return fil020ErrlAutoFolder1
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoFolder1__
     */
    public int getFil020ErrlAutoFolder1() {
        return fil020ErrlAutoFolder1__;
    }
    /**
     * <p>fil020ErrlAutoFolder1 をセットします。
     * @param fil020ErrlAutoFolder1 fil020ErrlAutoFolder1
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoFolder1__
     */
    public void setFil020ErrlAutoFolder1(int fil020ErrlAutoFolder1) {
        fil020ErrlAutoFolder1__ = fil020ErrlAutoFolder1;
    }
    /**
     * <p>fil020ErrlAutoFolder2 を取得します。
     * @return fil020ErrlAutoFolder2
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoFolder2__
     */
    public int getFil020ErrlAutoFolder2() {
        return fil020ErrlAutoFolder2__;
    }
    /**
     * <p>fil020ErrlAutoFolder2 をセットします。
     * @param fil020ErrlAutoFolder2 fil020ErrlAutoFolder2
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoFolder2__
     */
    public void setFil020ErrlAutoFolder2(int fil020ErrlAutoFolder2) {
        fil020ErrlAutoFolder2__ = fil020ErrlAutoFolder2;
    }
    /**
     * <p>fil020ErrlAutoFolder3 を取得します。
     * @return fil020ErrlAutoFolder3
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoFolder3__
     */
    public int getFil020ErrlAutoFolder3() {
        return fil020ErrlAutoFolder3__;
    }
    /**
     * <p>fil020ErrlAutoFolder3 をセットします。
     * @param fil020ErrlAutoFolder3 fil020ErrlAutoFolder3
     * @see jp.groupsession.v2.fil.fil020.Fil020Form#fil020ErrlAutoFolder3__
     */
    public void setFil020ErrlAutoFolder3(int fil020ErrlAutoFolder3) {
        fil020ErrlAutoFolder3__ = fil020ErrlAutoFolder3;
    }
}