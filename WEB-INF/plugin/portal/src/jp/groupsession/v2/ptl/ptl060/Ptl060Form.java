package jp.groupsession.v2.ptl.ptl060;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.ptl040.Ptl040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


/**
 * <br>[機  能] ポータル レイアウト設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl060Form extends Ptl040Form {

    /** 初期表示フラグ */
    private int ptl060init__ = 0;
    /** レイアウト 上 */
    private String ptl060area1__ = null;
    /** レイアウト 下 */
    private String ptl060area2__ = null;
    /** レイアウト 左 */
    private String ptl060area3__ = null;
    /** レイアウト 中 */
    private String ptl060area4__ = null;
    /** レイアウト 右 */
    private String ptl060area5__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textLayout = gsMsg.getMessage("ptl.5");

        //レイアウト全非表示
        if (NullDefault.getInt(ptl060area1__, 0) == 0
                && NullDefault.getInt(ptl060area2__, 0) == 0
                && NullDefault.getInt(ptl060area3__, 0) == 0
                && NullDefault.getInt(ptl060area4__, 0) == 0
                && NullDefault.getInt(ptl060area5__, 0) == 0) {

            msg = new ActionMessage("error.select.required.text", textLayout);
            StrutsUtil.addMessage(errors, msg, "ptl030sortPortal");

        }

        return errors;
    }

    /**
     * <p>ptl060init を取得します。
     * @return ptl060init
     */
    public int getPtl060init() {
        return ptl060init__;
    }
    /**
     * <p>ptl060init をセットします。
     * @param ptl060init ptl060init
     */
    public void setPtl060init(int ptl060init) {
        ptl060init__ = ptl060init;
    }
    /**
     * <p>ptl060area1 を取得します。
     * @return ptl060area1
     */
    public String getPtl060area1() {
        return ptl060area1__;
    }
    /**
     * <p>ptl060area1 をセットします。
     * @param ptl060area1 ptl060area1
     */
    public void setPtl060area1(String ptl060area1) {
        ptl060area1__ = ptl060area1;
    }
    /**
     * <p>ptl060area2 を取得します。
     * @return ptl060area2
     */
    public String getPtl060area2() {
        return ptl060area2__;
    }
    /**
     * <p>ptl060area2 をセットします。
     * @param ptl060area2 ptl060area2
     */
    public void setPtl060area2(String ptl060area2) {
        ptl060area2__ = ptl060area2;
    }
    /**
     * <p>ptl060area3 を取得します。
     * @return ptl060area3
     */
    public String getPtl060area3() {
        return ptl060area3__;
    }
    /**
     * <p>ptl060area3 をセットします。
     * @param ptl060area3 ptl060area3
     */
    public void setPtl060area3(String ptl060area3) {
        ptl060area3__ = ptl060area3;
    }
    /**
     * <p>ptl060area4 を取得します。
     * @return ptl060area4
     */
    public String getPtl060area4() {
        return ptl060area4__;
    }
    /**
     * <p>ptl060area4 をセットします。
     * @param ptl060area4 ptl060area4
     */
    public void setPtl060area4(String ptl060area4) {
        ptl060area4__ = ptl060area4;
    }
    /**
     * <p>ptl060area5 を取得します。
     * @return ptl060area5
     */
    public String getPtl060area5() {
        return ptl060area5__;
    }
    /**
     * <p>ptl060area5 をセットします。
     * @param ptl060area5 ptl060area5
     */
    public void setPtl060area5(String ptl060area5) {
        ptl060area5__ = ptl060area5;
    }
}
