package jp.groupsession.v2.api.api030;

import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] トークン管理 検索結果モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api030TokenModel extends ApiTokenModel {
    /** ユーザ情報*/
    private UsrLabelValueBean user__;
    /** 無効化選択済み*/
    private boolean mukoCheck__;
    /** 有効期限 年*/
    private int yukoYear__;
    
    /**
     * <p>user を取得します。
     * @return user
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#user__
     */
    public UsrLabelValueBean getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#user__
     */
    public void setUser(UsrLabelValueBean user) {
        user__ = user;
    }
    /**
     * <p>adateDsp を取得します。
     * @return adateDsp
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#adateDsp__
     */
    public String getAdateDsp() {
        String strSdate =
                UDateUtil.getSlashYYMD(getAptAdate())
                + "  "
                + UDateUtil.getSeparateHMS(getAptAdate());

        return strSdate;
    }
    /**
     * <p>ldateDsp を取得します。
     * @return ldateDsp
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#ldateDsp__
     */
    public String getLdateDsp() {
        String strSdate =
                UDateUtil.getSlashYYMD(getAptLimitDate())
                + "  "
                + UDateUtil.getSeparateHMS(getAptLimitDate());

        return strSdate;
    }
    /**
     * <p>mukoCheck を取得します。
     * @return mukoCheck
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#mukoCheck__
     */
    public boolean isMukoCheck() {
        return mukoCheck__;
    }
    /**
     * <p>mukoCheck をセットします。
     * @param mukoCheck mukoCheck
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#mukoCheck__
     */
    public void setMukoCheck(boolean mukoCheck) {
        mukoCheck__ = mukoCheck;
    }
    /**
     * <p>yukoYear を取得します。
     * @return yukoYear
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#yukoYear__
     */
    public int getYukoYear() {
        return yukoYear__;
    }
    /**
     * <p>yukoYear をセットします。
     * @param yukoYear yukoYear
     * @see jp.groupsession.v2.api.api030.Api030TokenModel#yukoYear__
     */
    public void setYukoYear(int yukoYear) {
        yukoYear__ = yukoYear;
    }

}
