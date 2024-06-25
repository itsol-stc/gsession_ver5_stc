package jp.groupsession.v2.fil.fil210kn;

import java.util.List;

import jp.groupsession.v2.fil.fil210.Fil210ParamModel;

/**
 * <br>[機  能] 管理者設定 基本設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil210knParamModel extends Fil210ParamModel {

    /** ユーザ名リスト */
    private List<String> fil210knUserNameList__ = null;
    /** グループ名リスト */
    private List<String> fil210knGroupNameList__ = null;
    /** ファイルサイズ制限(表示用) */
    private String fil210knFileSize__ = null;
    /** ファイル保存期間（表示用） */
    private String fil210knSaveDays__ = null;

    /**
     * <p>fil210knGroupNameList を取得します。
     * @return fil210knGroupNameList
     */
    public List<String> getFil210knGroupNameList() {
        return fil210knGroupNameList__;
    }

    /**
     * <p>fil210knGroupNameList をセットします。
     * @param fil210knGroupNameList fil210knGroupNameList
     */
    public void setFil210knGroupNameList(List<String> fil210knGroupNameList) {
        fil210knGroupNameList__ = fil210knGroupNameList;
    }

    /**
     * <p>fil210knUserNameList を取得します。
     * @return fil210knUserNameList
     */
    public List<String> getFil210knUserNameList() {
        return fil210knUserNameList__;
    }

    /**
     * <p>fil210knUserNameList をセットします。
     * @param fil210knUserNameList fil210knUserNameList
     */
    public void setFil210knUserNameList(List<String> fil210knUserNameList) {
        fil210knUserNameList__ = fil210knUserNameList;
    }

    /**
     * <p>fil210knFileSize を取得します。
     * @return fil210knFileSize
     */
    public String getFil210knFileSize() {
        return fil210knFileSize__;
    }

    /**
     * <p>fil210knFileSize をセットします。
     * @param fil210knFileSize fil210knFileSize
     */
    public void setFil210knFileSize(String fil210knFileSize) {
        fil210knFileSize__ = fil210knFileSize;
    }

    /**
     * <p>fil210knSaveDays を取得します。
     * @return fil210knSaveDays
     */
    public String getFil210knSaveDays() {
        return fil210knSaveDays__;
    }

    /**
     * <p>fil210knSaveDays をセットします。
     * @param fil210knSaveDays fil210knSaveDays
     */
    public void setFil210knSaveDays(String fil210knSaveDays) {
        fil210knSaveDays__ = fil210knSaveDays;
    }
}
