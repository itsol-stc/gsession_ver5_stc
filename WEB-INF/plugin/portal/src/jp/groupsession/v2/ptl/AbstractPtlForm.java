package jp.groupsession.v2.ptl;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ポータルで共通使用するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractPtlForm extends AbstractGsForm {
    /** ポータルSID */
    private int ptlPortalSid__ = -1;
    /** 処理モード */
    private int ptlCmdMode__ = 0;
    /** ポータルメニューからの戻り先画面 0:管理者メニュー, 1:メイン */
    private int ptlBackPage__ = GSConstPortal.BACKSCREEN_MAIN_ADMIN_MENU;
    /** メイン画面選択ポータルSID */
    private int ptlMainSid__ = -1;

    /**
     * <p>ptlPortalSid を取得します。
     * @return ptlPortalSid
     */
    public int getPtlPortalSid() {
        return ptlPortalSid__;
    }

    /**
     * <p>ptlPortalSid をセットします。
     * @param ptlPortalSid ptlPortalSid
     */
    public void setPtlPortalSid(int ptlPortalSid) {
        ptlPortalSid__ = ptlPortalSid;
    }
    /**
     * @return ptlCmdMode
     */
    public int getPtlCmdMode() {
        return ptlCmdMode__;
    }

    /**
     * @param ptlCmdMode セットする ptlCmdMode
     */
    public void setPtlCmdMode(int ptlCmdMode) {
        ptlCmdMode__ = ptlCmdMode;
    }

    /**
     * <p>ptlBackPage を取得します。
     * @return ptlBackPage
     */
    public int getPtlBackPage() {
        return ptlBackPage__;
    }

    /**
     * <p>ptlBackPage をセットします。
     * @param ptlBackPage ptlBackPage
     */
    public void setPtlBackPage(int ptlBackPage) {
        ptlBackPage__ = ptlBackPage;
    }

    /**
     * <p>ptlMainSid を取得します。
     * @return ptlMainSid
     */
    public int getPtlMainSid() {
        return ptlMainSid__;
    }

    /**
     * <p>ptlMainSid をセットします。
     * @param ptlMainSid ptlMainSid
     */
    public void setPtlMainSid(int ptlMainSid) {
        ptlMainSid__ = ptlMainSid;
    }

}
