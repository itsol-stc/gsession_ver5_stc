package jp.groupsession.v2.ptl.ptl010;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.ptl.model.PtlParamModel;
import jp.groupsession.v2.ptl.ptl010.model.Ptl010Model;

/**
 * <br>[機  能] ポータルトップ画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl010ParamModel extends PtlParamModel {
    /** 表示しているポータルSID */
    private int dspPtlSid__ = 0;

    /** インフォーメーションのリスト */
    private ArrayList<MainInfoMessageModel> infoMsgs__ = null;
    /** インフォーメーション設定　表示・非表示 */
    public boolean infoConf__ = false;

    /** 自動リロード時間 */
    private int ptl010Reload__ = 600000;

    /** ポータル管理者フラグ */
    private boolean ptl010ptlAdminFlg__ = false;

    /** ポータルリスト */
    private List<PtlPortalModel> ptl010ptlList__ = null;

    /** ポータル表示リスト 上 */
    private List<Ptl010Model> ptl010topList__ = null;
    /** ポータル表示リスト 下 */
    private List<Ptl010Model> ptl010bottomList__ = null;
    /** ポータル表示リスト 左 */
    private List<Ptl010Model> ptl010leftList__ = null;
    /** ポータル表示リスト 中 */
    private List<Ptl010Model> ptl010centerList__ = null;
    /** ポータル表示リスト 右 */
    private List<Ptl010Model> ptl010rightList__ = null;
    /** ポータル表示リスト 全部 */
    private List<Ptl010Model> ptl010allList__ = null;

    /** レイアウト表示区分 上 */
    private int ptl010areaTop__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 下 */
    private int ptl010areaBottom__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 左 */
    private int ptl010areaLeft__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 中 */
    private int ptl010areaCenter__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 右 */
    private int ptl010areaRight__ = GSConstPortal.LAYOUT_VIEW_OFF;

    /** JavaScriptのパス */
    private List<String> ptl010scriptPathList__ = null;
    /** CSSのパス */
    private List<String> ptl010stylePath__ = null;

    //スケジュール用
    /** 処理モード */
    private String cmd__ = null;
    /** ユーザSID */
    private String sch010SelectUsrSid__;
    /** ユーザ区分 */
    private String sch010SelectUsrKbn__;
    /** 表示日付 */
    private String sch010DspDate__;

    /** 表示しているポータル名 */
    private String dspPtlName__ = "";

    /**
     * <p>ptl010topList を取得します。
     * @return ptl010topList
     */
    public List<Ptl010Model> getPtl010topList() {
        return ptl010topList__;
    }

    /**
     * <p>ptl010topList をセットします。
     * @param ptl010topList ptl010topList
     */
    public void setPtl010topList(List<Ptl010Model> ptl010topList) {
        ptl010topList__ = ptl010topList;
    }

    /**
     * <p>ptl010bottomList を取得します。
     * @return ptl010bottomList
     */
    public List<Ptl010Model> getPtl010bottomList() {
        return ptl010bottomList__;
    }

    /**
     * <p>ptl010bottomList をセットします。
     * @param ptl010bottomList ptl010bottomList
     */
    public void setPtl010bottomList(List<Ptl010Model> ptl010bottomList) {
        ptl010bottomList__ = ptl010bottomList;
    }

    /**
     * <p>ptl010leftList を取得します。
     * @return ptl010leftList
     */
    public List<Ptl010Model> getPtl010leftList() {
        return ptl010leftList__;
    }

    /**
     * <p>ptl010leftList をセットします。
     * @param ptl010leftList ptl010leftList
     */
    public void setPtl010leftList(List<Ptl010Model> ptl010leftList) {
        ptl010leftList__ = ptl010leftList;
    }

    /**
     * <p>ptl010centerList を取得します。
     * @return ptl010centerList
     */
    public List<Ptl010Model> getPtl010centerList() {
        return ptl010centerList__;
    }

    /**
     * <p>ptl010centerList をセットします。
     * @param ptl010centerList ptl010centerList
     */
    public void setPtl010centerList(List<Ptl010Model> ptl010centerList) {
        ptl010centerList__ = ptl010centerList;
    }

    /**
     * <p>ptl010rightList を取得します。
     * @return ptl010rightList
     */
    public List<Ptl010Model> getPtl010rightList() {
        return ptl010rightList__;
    }

    /**
     * <p>ptl010rightList をセットします。
     * @param ptl010rightList ptl010rightList
     */
    public void setPtl010rightList(List<Ptl010Model> ptl010rightList) {
        ptl010rightList__ = ptl010rightList;
    }

    /**
     * <p>ptl010areaTop を取得します。
     * @return ptl010areaTop
     */
    public int getPtl010areaTop() {
        return ptl010areaTop__;
    }

    /**
     * <p>ptl010areaTop をセットします。
     * @param ptl010areaTop ptl010areaTop
     */
    public void setPtl010areaTop(int ptl010areaTop) {
        ptl010areaTop__ = ptl010areaTop;
    }

    /**
     * <p>ptl010areaBottom を取得します。
     * @return ptl010areaBottom
     */
    public int getPtl010areaBottom() {
        return ptl010areaBottom__;
    }

    /**
     * <p>ptl010areaBottom をセットします。
     * @param ptl010areaBottom ptl010areaBottom
     */
    public void setPtl010areaBottom(int ptl010areaBottom) {
        ptl010areaBottom__ = ptl010areaBottom;
    }

    /**
     * <p>ptl010areaLeft を取得します。
     * @return ptl010areaLeft
     */
    public int getPtl010areaLeft() {
        return ptl010areaLeft__;
    }

    /**
     * <p>ptl010areaLeft をセットします。
     * @param ptl010areaLeft ptl010areaLeft
     */
    public void setPtl010areaLeft(int ptl010areaLeft) {
        ptl010areaLeft__ = ptl010areaLeft;
    }

    /**
     * <p>ptl010areaCenter を取得します。
     * @return ptl010areaCenter
     */
    public int getPtl010areaCenter() {
        return ptl010areaCenter__;
    }

    /**
     * <p>ptl010areaCenter をセットします。
     * @param ptl010areaCenter ptl010areaCenter
     */
    public void setPtl010areaCenter(int ptl010areaCenter) {
        ptl010areaCenter__ = ptl010areaCenter;
    }

    /**
     * <p>ptl010areaRight を取得します。
     * @return ptl010areaRight
     */
    public int getPtl010areaRight() {
        return ptl010areaRight__;
    }

    /**
     * <p>ptl010areaRight をセットします。
     * @param ptl010areaRight ptl010areaRight
     */
    public void setPtl010areaRight(int ptl010areaRight) {
        ptl010areaRight__ = ptl010areaRight;
    }

    /**
     * <p>ptl010scriptPathList を取得します。
     * @return ptl010scriptPathList
     */
    public List<String> getPtl010scriptPathList() {
        return ptl010scriptPathList__;
    }

    /**
     * <p>ptl010scriptPathList をセットします。
     * @param ptl010scriptPathList ptl010scriptPathList
     */
    public void setPtl010scriptPathList(List<String> ptl010scriptPathList) {
        ptl010scriptPathList__ = ptl010scriptPathList;
    }

    /**
     * <p>ptl010stylePath を取得します。
     * @return ptl010stylePath
     */
    public List<String> getPtl010stylePath() {
        return ptl010stylePath__;
    }

    /**
     * <p>ptl010stylePath をセットします。
     * @param ptl010stylePath ptl010stylePath
     */
    public void setPtl010stylePath(List<String> ptl010stylePath) {
        ptl010stylePath__ = ptl010stylePath;
    }

    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }

    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }

    /**
     * <p>sch010SelectUsrSid を取得します。
     * @return sch010SelectUsrSid
     */
    public String getSch010SelectUsrSid() {
        return sch010SelectUsrSid__;
    }

    /**
     * <p>sch010SelectUsrSid をセットします。
     * @param sch010SelectUsrSid sch010SelectUsrSid
     */
    public void setSch010SelectUsrSid(String sch010SelectUsrSid) {
        sch010SelectUsrSid__ = sch010SelectUsrSid;
    }

    /**
     * <p>sch010SelectUsrKbn を取得します。
     * @return sch010SelectUsrKbn
     */
    public String getSch010SelectUsrKbn() {
        return sch010SelectUsrKbn__;
    }

    /**
     * <p>sch010SelectUsrKbn をセットします。
     * @param sch010SelectUsrKbn sch010SelectUsrKbn
     */
    public void setSch010SelectUsrKbn(String sch010SelectUsrKbn) {
        sch010SelectUsrKbn__ = sch010SelectUsrKbn;
    }

    /**
     * <p>sch010DspDate を取得します。
     * @return sch010DspDate
     */
    public String getSch010DspDate() {
        return sch010DspDate__;
    }

    /**
     * <p>sch010DspDate をセットします。
     * @param sch010DspDate sch010DspDate
     */
    public void setSch010DspDate(String sch010DspDate) {
        sch010DspDate__ = sch010DspDate;
    }

    /**
     * <p>ptl010ptlList を取得します。
     * @return ptl010ptlList
     */
    public List<PtlPortalModel> getPtl010ptlList() {
        return ptl010ptlList__;
    }

    /**
     * <p>ptl010ptlList をセットします。
     * @param ptl010ptlList ptl010ptlList
     */
    public void setPtl010ptlList(List<PtlPortalModel> ptl010ptlList) {
        ptl010ptlList__ = ptl010ptlList;
    }

    /**
     * <p>dspPtlSid を取得します。
     * @return dspPtlSid
     */
    public int getDspPtlSid() {
        return dspPtlSid__;
    }

    /**
     * <p>dspPtlSid をセットします。
     * @param dspPtlSid dspPtlSid
     */
    public void setDspPtlSid(int dspPtlSid) {
        dspPtlSid__ = dspPtlSid;
    }

    /**
     * <p>infoMsgs を取得します。
     * @return infoMsgs
     */
    public ArrayList<MainInfoMessageModel> getInfoMsgs() {
        return infoMsgs__;
    }

    /**
     * <p>infoMsgs をセットします。
     * @param infoMsgs infoMsgs
     */
    public void setInfoMsgs(ArrayList<MainInfoMessageModel> infoMsgs) {
        infoMsgs__ = infoMsgs;
    }

    /**
     * <p>infoConf を取得します。
     * @return infoConf
     */
    public boolean isInfoConf() {
        return infoConf__;
    }

    /**
     * <p>infoConf をセットします。
     * @param infoConf infoConf
     */
    public void setInfoConf(boolean infoConf) {
        infoConf__ = infoConf;
    }

    /**
     * <p>ptl010Reload を取得します。
     * @return ptl010Reload
     */
    public int getPtl010Reload() {
        return ptl010Reload__;
    }

    /**
     * <p>ptl010Reload をセットします。
     * @param ptl010Reload ptl010Reload
     */
    public void setPtl010Reload(int ptl010Reload) {
        ptl010Reload__ = ptl010Reload;
    }

    /**
     * <p>ptl010ptlAdminFlg を取得します。
     * @return ptl010ptlAdminFlg
     */
    public boolean isPtl010ptlAdminFlg() {
        return ptl010ptlAdminFlg__;
    }

    /**
     * <p>ptl010ptlAdminFlg をセットします。
     * @param ptl010ptlAdminFlg ptl010ptlAdminFlg
     */
    public void setPtl010ptlAdminFlg(boolean ptl010ptlAdminFlg) {
        ptl010ptlAdminFlg__ = ptl010ptlAdminFlg;
    }

    /**
     * @return dspPtlName
     */
    public String getDspPtlName() {
        return dspPtlName__;
    }

    /**
     * @param dspPtlName セットする dspPtlName
     */
    public void setDspPtlName(String dspPtlName) {
        dspPtlName__ = dspPtlName;
    }

    /**
     * <p>ptl010allList を取得します。
     * @return ptl010allList
     */
    public List<Ptl010Model> getPtl010allList() {
        return ptl010allList__;
    }

    /**
     * <p>ptl010allList をセットします。
     * @param ptl010allList ptl010allList
     */
    public void setPtl010allList(List<Ptl010Model> ptl010allList) {
        ptl010allList__ = ptl010allList;
    }
}