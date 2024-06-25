package jp.groupsession.v2.cir.cir170kn;

import java.util.List;

import jp.groupsession.v2.cir.cir170.Cir170ParamModel;


/**
 * <br>[機  能] 回覧板 アカウントインポート確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir170knParamModel extends Cir170ParamModel {
    /** 取込ファイル名 */
    private String cir170knFileName__ = null;
    /** 使用者リスト */
    private List<Cir170knUseUsrModel> cir170knUseUserList__ = null;

    /**
     * <p>cir170knUseUserList を取得します。
     * @return cir170knUseUserList
     */
    public List<Cir170knUseUsrModel> getCir170knUseUserList() {
        return cir170knUseUserList__;
    }
    /**
     * <p>cir170knUseUserList をセットします。
     * @param cir170knUseUserList cir170knUseUserList
     */
    public void setCir170knUseUserList(List<Cir170knUseUsrModel> cir170knUseUserList) {
        cir170knUseUserList__ = cir170knUseUserList;
    }
    /**
     * <p>cir170knFileName を取得します。
     * @return cir170knFileName
     */
    public String getCir170knFileName() {
        return cir170knFileName__;
    }
    /**
     * <p>cir170knFileName をセットします。
     * @param cir170knFileName cir170knFileName
     */
    public void setCir170knFileName(String cir170knFileName) {
        cir170knFileName__ = cir170knFileName;
    }
}