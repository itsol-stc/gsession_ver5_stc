package jp.groupsession.v2.ptl.ptl030;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ptl.ptl020.Ptl020Form;
import jp.groupsession.v2.ptl.ptl030.model.Ptl030Model;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] ポータル ポータル管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl030Form extends Ptl020Form {

    /** 表示順ラジオボタン */
    private int ptl030sortPortal__ = -1;

    /** ポータル一覧 */
    private List<Ptl030Model> ptl030portalList__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckSort(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String textPortal = gsMsg.getMessage(req, "ptl.1");

        //ポータル未選択
        if (ptl030sortPortal__ < 0) {
            msg = new ActionMessage("error.select.required.text", textPortal);
            StrutsUtil.addMessage(errors, msg, "ptl030sortPortal");
        }

        return errors;
    }

    /**
     * @return ptl030sortPortal
     */
    public int getPtl030sortPortal() {
        return ptl030sortPortal__;
    }
    /**
     * @param ptl030sortPortal セットする ptl030sortPortal
     */
    public void setPtl030sortPortal(int ptl030sortPortal) {
        ptl030sortPortal__ = ptl030sortPortal;
    }
    /**
     * <p>ptl030portalList を取得します。
     * @return ptl030portalList
     */
    public List<Ptl030Model> getPtl030portalList() {
        return ptl030portalList__;
    }
    /**
     * <p>ptl030portalList をセットします。
     * @param ptl030portalList ptl030portalList
     */
    public void setPtl030portalList(List<Ptl030Model> ptl030portalList) {
        ptl030portalList__ = ptl030portalList;
    }
}
