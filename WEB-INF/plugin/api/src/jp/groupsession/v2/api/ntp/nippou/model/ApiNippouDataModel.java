package jp.groupsession.v2.api.ntp.nippou.model;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CommentModel;
/**
 * <br>[機  能] WEBAPI 日報データ詳細モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouDataModel extends NtpDataModel {
    /** ユーザ情報 */
    private CmnUsrmInfModel usrMdl__ = null;
    /** 登録ユーザ名 */
    private String addUsrName__ = "";
    /** 登録ユーザ名 */
    private int addUsrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /**  案件名 */
    private String nanName__ = "";
    /**  案件コード */
    private String nanCode__ = "";
    /**  会社名 */
    private String acoName__ = "";
    /**  会社コード */
    private String acoCode__ = "";
    /**  拠点名 */
    private String abaName__ = "";
    /** 活動分類名 */
    private String ktBunrui__;
    /** 活動方法名 */
    private String ktHouhou__;
    /** いいねカウント */
    private int iineCount__;
    /** いいねしているか */
    private int iineFlg__;
    /**
     * <p>iineFlg を取得します。
     * @return iineFlg
     */
    public int getIineFlg() {
        return iineFlg__;
    }
    /**
     * <p>iineFlg をセットします。
     * @param iineFlg iineFlg
     */
    public void setIineFlg(int iineFlg) {
        iineFlg__ = iineFlg;
    }
    /** 添付データ配列 */
    private List<CmnBinfModel> clips__ = new ArrayList<CmnBinfModel>();
    /** コメント配列 */
    private List<Ntp040CommentModel> comments__ = new ArrayList<Ntp040CommentModel>();
    /**
     * <p>usrMdl を取得します。
     * @return usrMdl
     */
    public CmnUsrmInfModel getUsrMdl() {
        return usrMdl__;
    }
    /**
     * <p>usrMdl をセットします。
     * @param usrMdl usrMdl
     */
    public void setUsrMdl(CmnUsrmInfModel usrMdl) {
        this.usrMdl__ = usrMdl;
    }
    /**
     * <p>nanName を取得します。
     * @return nanName
     */
    public String getNanName() {
        return nanName__;
    }
    /**
     * <p>nanName をセットします。
     * @param nanName nanName
     */
    public void setNanName(String nanName) {
        this.nanName__ = nanName;
    }
    /**
     * <p>acoName を取得します。
     * @return acoName
     */
    public String getAcoName() {
        return acoName__;
    }
    /**
     * <p>acoName をセットします。
     * @param acoName acoName
     */
    public void setAcoName(String acoName) {
        this.acoName__ = acoName;
    }
    /**
     * <p>abaName を取得します。
     * @return abaName
     */
    public String getAbaName() {
        return abaName__;
    }
    /**
     * <p>abaName をセットします。
     * @param abaName abaName
     */
    public void setAbaName(String abaName) {
        abaName__ = abaName;
    }
    /**
     * <p>nanCode を取得します。
     * @return nanCode
     */
    public String getNanCode() {
        return nanCode__;
    }
    /**
     * <p>nanCode をセットします。
     * @param nanCode nanCode
     */
    public void setNanCode(String nanCode) {
        nanCode__ = nanCode;
    }
    /**
     * <p>acoCode を取得します。
     * @return acoCode
     */
    public String getAcoCode() {
        return acoCode__;
    }
    /**
     * <p>acoCode をセットします。
     * @param acoCode acoCode
     */
    public void setAcoCode(String acoCode) {
        acoCode__ = acoCode;
    }
    /**
     * <p>ktBunrui を取得します。
     * @return ktBunrui
     */
    public String getKtBunrui() {
        return ktBunrui__;
    }
    /**
     * <p>ktBunrui をセットします。
     * @param ktBunrui ktBunrui
     */
    public void setKtBunrui(String ktBunrui) {
        ktBunrui__ = ktBunrui;
    }
    /**
     * <p>ktHouhou を取得します。
     * @return ktHouhou
     */
    public String getKtHouhou() {
        return ktHouhou__;
    }
    /**
     * <p>ktHouhou をセットします。
     * @param ktHouhou ktHouhou
     */
    public void setKtHouhou(String ktHouhou) {
        ktHouhou__ = ktHouhou;
    }
    /**
     * <p>iineCount を取得します。
     * @return iineCount
     */
    public int getIineCount() {
        return iineCount__;
    }
    /**
     * <p>iineCount をセットします。
     * @param iineCount iineCount
     */
    public void setIineCount(int iineCount) {
        iineCount__ = iineCount;
    }
    /**
     * <p>addUsrName を取得します。
     * @return addUsrName
     */
    public String getAddUsrName() {
        return addUsrName__;
    }
    /**
     * <p>addUsrName をセットします。
     * @param addUsrName addUsrName
     */
    public void setAddUsrName(String addUsrName) {
        addUsrName__ = addUsrName;
    }
    /**
     * <p>addUsrUkoFlg を取得します。
     * @return addUsrUkoFlg
     */
    public int getAddUsrUkoFlg() {
        return addUsrUkoFlg__;
    }
    /**
     * <p>addUsrUkoFlg をセットします。
     * @param addUsrUkoFlg addUsrUkoFlg
     */
    public void setAddUsrUkoFlg(int addUsrUkoFlg) {
        addUsrUkoFlg__ = addUsrUkoFlg;
    }
    /**
     * <p>clips を取得します。
     * @return clips
     */
    public List<CmnBinfModel> getClips() {
        return clips__;
    }
    /**
     * <p>clips をセットします。
     * @param clips clips
     */
    public void setClips(List<CmnBinfModel> clips) {
        clips__ = clips;
    }
    /**
     * <p>comments を取得します。
     * @return comments
     */
    public List<Ntp040CommentModel> getComments() {
        return comments__;
    }
    /**
     * <p>comments をセットします。
     * @param comments comments
     */
    public void setComments(List<Ntp040CommentModel> comments) {
        comments__ = comments;
    }


}
