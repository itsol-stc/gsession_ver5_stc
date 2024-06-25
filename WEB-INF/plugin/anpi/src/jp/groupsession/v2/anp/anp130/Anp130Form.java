package jp.groupsession.v2.anp.anp130;

import java.util.List;

import jp.groupsession.v2.anp.anp070.Anp070Form;
import jp.groupsession.v2.anp.anp130.model.Anp130SenderModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 管理者設定・配信履歴画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp130Form extends Anp070Form {

    /** 配信履歴一覧 */
    private List<Anp130SenderModel> anp130HaisinList__ = null;
    /** 選択された配信データSID */
    private int anp130SelectAphSid__;

    /** 全選択削除チェックボックス */
    private int anp130allCheck__ = 0;
    /** 一覧削除チェックボックス */
    private String[] anp130DelSidList__;

    /** 現在ページ */
    private int anp130NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp130DspPage1__;
    /** 表示ページ（下） */
    private int anp130DspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp130PageLabel__;


    /**
     * <p>配信履歴一覧を取得します
     * @return anp130HaisinList
     */
    public List<Anp130SenderModel> getAnp130HaisinList() {
        return anp130HaisinList__;
    }

    /**
     * <p>配信履歴一覧を設定します
     * @param anp130HaisinList セットする anp130HaisinList
     */
    public void setAnp130HaisinList(List<Anp130SenderModel> anp130HaisinList) {
        anp130HaisinList__ = anp130HaisinList;
    }

    /**
     * <p>選択された配信データSIDを取得します
     * @return anp130SelectAphSid
     */
    public int getAnp130SelectAphSid() {
        return anp130SelectAphSid__;
    }

    /**
     * <p>選択された配信データSIDを設定します
     * @param anp130SelectAphSid セットする anp130SelectAphSid
     */
    public void setAnp130SelectAphSid(int anp130SelectAphSid) {
        anp130SelectAphSid__ = anp130SelectAphSid;
    }

    /**
     * <p>全選択削除チェックボックスを取得します
     * @return anp130allCheck
     */
    public int getAnp130allCheck() {
        return anp130allCheck__;
    }

    /**
     * <p>全選択削除チェックボックスを設定します
     * @param anp130allCheck セットする anp130allCheck
     */
    public void setAnp130allCheck(int anp130allCheck) {
        anp130allCheck__ = anp130allCheck;
    }

    /**
     * <p>一覧削除チェックSIDを取得します
     * @return anp130DelSid
     */
    public String[] getAnp130DelSidList() {
        return anp130DelSidList__;
    }

    /**
     * <p>一覧削除チェックSIDを設定します
     * @param anp130DelSid セットする anp130DelSid
     */
    public void setAnp130DelSidList(String[] anp130DelSid) {
        anp130DelSidList__ = anp130DelSid;
    }

    /**
     * <p>現在ページを取得します
     * @return anp130NowPage
     */
    public int getAnp130NowPage() {
        return anp130NowPage__;
    }

    /**
     * <p>現在ページを設定します
     * @param anp130NowPage セットする anp130NowPage
     */
    public void setAnp130NowPage(int anp130NowPage) {
        anp130NowPage__ = anp130NowPage;
    }

    /**
     * <p>表示ページ（上）を取得します
     * @return anp130DspPage1
     */
    public int getAnp130DspPage1() {
        return anp130DspPage1__;
    }

    /**
     * <p>表示ページ（上）を設定します
     * @param anp130DspPage1 セットする anp130DspPage1
     */
    public void setAnp130DspPage1(int anp130DspPage1) {
        anp130DspPage1__ = anp130DspPage1;
    }

    /**
     * <p>表示ページ（下）を取得します
     * @return anp130DspPage2
     */
    public int getAnp130DspPage2() {
        return anp130DspPage2__;
    }

    /**
     * <p>表示ページ（下）を設定します
     * @param anp130DspPage2 セットする anp130DspPage2
     */
    public void setAnp130DspPage2(int anp130DspPage2) {
        anp130DspPage2__ = anp130DspPage2;
    }

    /**
     * <p>ページラベルリストを取得します
     * @return anp130PageLabel
     */
    public List<LabelValueBean> getAnp130PageLabel() {
        return anp130PageLabel__;
    }

    /**
     * <p>ページラベルリストを設定します
     * @param anp130PageLabel セットする anp130PageLabel
     */
    public void setAnp130PageLabel(List<LabelValueBean> anp130PageLabel) {
        anp130PageLabel__ = anp130PageLabel;
    }

    /**
     * <p>
     * <br>[機  能] 選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ActionErrors
     */
    public ActionErrors validateCheckAnp130(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (anp130DelSidList__ == null || anp130DelSidList__.length == 0) {
            String msgCode = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgCode, gsMsg.getMessage("anp.anp130.03"));
            errors.add(msgCode, msg);
        }

        return errors;
    }

    /**
     * <br>[機  能] hidden項目を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm メッセージフォーム
     */
    public void setHiddenParamAnp130(Cmn999Form msgForm) {

        msgForm.addHiddenParam("anp130SelectAphSid", anp130SelectAphSid__);
        msgForm.addHiddenParam("anp130allCheck", anp130allCheck__);
        msgForm.addHiddenParam("anp130DelSidList", anp130DelSidList__);
        msgForm.addHiddenParam("anp130NowPage", anp130NowPage__);
        msgForm.addHiddenParam("anp130DspPage1", anp130DspPage1__);
        msgForm.addHiddenParam("anp130DspPage2", anp130DspPage2__);

    }
}