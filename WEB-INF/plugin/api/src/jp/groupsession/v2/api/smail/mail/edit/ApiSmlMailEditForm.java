package jp.groupsession.v2.api.smail.mail.edit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメールを更新するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-mail/edit",
plugin = "smail", name = "ショートメール状態変更",
url = "/api/smail/mail/edit.do", reqtype = "POST")
public class ApiSmlMailEditForm extends AbstractApiForm {

    /** アカウントSID*/
    @ApiParam(name = "sacSid", viewName = "アカウントSID")
    private int sacSid__  = -1;

    /** メール種別(受信/送信/草稿/ゴミ箱)*/
    @ApiParam(name = "smlKbn", viewName = "メール種別")
    private int smlKbn__  = -1;

    /** 実行コマンド */
    @ApiParam(name = "smlCmd", viewName = "実行コマンドID")
    private int smlCmd__  = -1;

    /** メールSID一覧(受信) */
    @ApiParam(name = "smjSids", viewName = "受信メールSID一覧", required = false, confRequired = true)
    private int[] smjSids__;

    /** メールSID一覧(送信) */
    @ApiParam(name = "smsSids", viewName = "送信メールSID一覧", required = false, confRequired = true)
    private int[] smsSids__;

    /** メールSID一覧(草稿) */
    @ApiParam(name = "smwSids", viewName = "草稿メールSID一覧", required = false, confRequired = true)
    private int[] smwSids__;

    /** ラベルSID(ラベル追加/削除のみ)*/
    private int slbSid__ = -1;

    /** メールSID一覧(実行処理用) */
    private String[] smlSids__;

    /** メールSID一覧(ラベル削除用) */
    private String[] smlNums__;

    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }
    /**
     * <p>sacSid をセットします。
     * @param sacSid アカウントSID
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * @return smlKbn
     */
    public int getSmlKbn() {
        return smlKbn__;
    }
    /**
     * @param smlKbn メール区分
     */
    public void setSmlKbn(int smlKbn) {
        smlKbn__ = smlKbn;
    }

    /**
     * <p>smlCmd を取得します。
     * @return smlCmd   0:既読 / 1:未読
     *                 10:ラベル追加 / 11:ラベル削除
     *                 20:元に戻す / 21:削除 / 22:完全削除
     */
    public int getSmlCmd() {
        return smlCmd__;
    }
    /**
     * <p>smlCmd をセットします。
     * @param smlCmd 実行コマンド
     */
    public void setSmlCmd(int smlCmd) {
        smlCmd__ = smlCmd;
    }

    /**
     * <p>smjSids を取得します。
     * @return smjSids
     */
    public int[] getSmjSids() {
        return smjSids__;
    }
    /**
     * <p>smjSids をセットします。
     * @param smjSids 受信ショートメールSID一覧
     */
    public void setSmjSids(int[] smjSids) {
        smjSids__ = smjSids;
    }

    /**
     * <p>smsSids を取得します。
     * @return smsSids
     */
    public int[] getSmsSids() {
        return smsSids__;
    }
    /**
     * <p>smsSids をセットします。
     * @param smsSids 送信ショートメールSID一覧
     */
    public void setSmsSids(int[] smsSids) {
        smsSids__ = smsSids;
    }

    /**
     * <p>smwSids を取得します。
     * @return smwSids
     */
    public int[] getSmwSids() {
        return smwSids__;
    }
    /**
     * <p>smwSids をセットします。
     * @param smwSids 草稿ショートメールSID一覧
     */
    public void setSmwSids(int[] smwSids) {
        smwSids__ = smwSids;
    }

    /**
     * <p>slbSid を取得します。
     * @return slbSid
     */
    public int getSlbSid() {
        return slbSid__;
    }
    /**
     * <p>slbSid をセットします。
     * @param slbSid ラベルSID
     */
    public void setSlbSid(int slbSid) {
        slbSid__ = slbSid;
    }

    /**
     * <p>smlSids を取得します。
     * @return smlSids
     */
    public String[] getSmlSids() {
        return smlSids__;
    }
    /**
     * <p>smlSids をセットします。
     * @param smlSids ショートメールSID一覧(実行処理用)
     */
    private void setSmlSids(String[] smlSids) {
        smlSids__ = smlSids;
    }

    /**
     * <p>smlNums を取得します。
     * @return smlNums
     */
    public String[] getSmlNums() {
        return smlNums__;
    }
    /**
     * <p>smlNums をセットします。
     * @param smlNums ショートメールSID一覧(ラベル削除用)
     */
    private void setSmlNums(String[] smlNums) {
        smlNums__ = smlNums;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors エラー
     * @param con    コネクション
     * @param gsMsg  GsMessage
     * @throws SQLException SQL実行時例外
     */
    public void validateCheckSmlSend(ActionErrors errors, Connection con, GsMessage gsMsg)
        throws SQLException {

        String procMode = String.valueOf(this.getSmlKbn());
        if (this.getSmlKbn() < 0
        || (!procMode.contains(GSConstSmail.TAB_DSP_MODE_JUSIN)
        &&  !procMode.contains(GSConstSmail.TAB_DSP_MODE_SOSIN)
        &&  !procMode.contains(GSConstSmail.TAB_DSP_MODE_SOKO)
        &&  !procMode.contains(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
        &&  !procMode.contains(GSConstSmail.TAB_DSP_MODE_LABEL))) {
            // モードが不正
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage("cmn.mode"));
            StrutsUtil.addMessage(errors, msg, "smlEditMode");
            return;
        } else if (slbSid__ < 0 && (smlCmd__ == 10 || smlCmd__ == 11)) {
            // ラベル追加・削除の場合のみ、ラベルSID必須
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage("cmn.label"));
            StrutsUtil.addMessage(errors, msg, "smlEditLabel");
            return;
        }

        // 指定モードで使用できるメールSID一覧をセット(モードがゴミ箱・ラベルは全て使用可)
        List<String> sidList = new ArrayList<String>(); // セミコロンなし
        List<String> numList = new ArrayList<String>(); // セミコロン付き
        // 受信メール
        if (procMode.contains(GSConstSmail.TAB_DSP_MODE_JUSIN)
        ||  procMode.contains(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
        ||  procMode.contains(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            int[] smjSids = this.getSmjSids();
            if (smjSids != null && smjSids.length > 0) {
                for (int smlSid : smjSids) {
                    sidList.add(String.format("%s%d",  GSConstSmail.TAB_DSP_MODE_JUSIN, smlSid));
                    numList.add(String.format("%s:%d", GSConstSmail.TAB_DSP_MODE_JUSIN, smlSid));
                }
            }
        }
        // 送信メール
        if (procMode.contains(GSConstSmail.TAB_DSP_MODE_SOSIN)
        ||  procMode.contains(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
        ||  procMode.contains(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            int[] smsSids = this.getSmsSids();
            if (smsSids != null && smsSids.length > 0) {
                for (int smlSid : smsSids__) {
                    sidList.add(String.format("%s%d",  GSConstSmail.TAB_DSP_MODE_SOSIN, smlSid));
                    numList.add(String.format("%s:%d", GSConstSmail.TAB_DSP_MODE_SOSIN, smlSid));
                }
            }
        }
        // 草稿メール
        if (procMode.contains(GSConstSmail.TAB_DSP_MODE_SOKO)
        ||  procMode.contains(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
        ||  procMode.contains(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            int[] smwSids = this.getSmwSids();
            if (smwSids != null && smwSids.length > 0) {
                for (int smlSid : smwSids) {
                    sidList.add(String.format("%s%d",  GSConstSmail.TAB_DSP_MODE_SOKO, smlSid));
                    numList.add(String.format("%s:%d", GSConstSmail.TAB_DSP_MODE_SOKO, smlSid));
                }
            }
        }

        // 該当するメールSIDがない場合、エラー
        if (sidList.size() == 0) {
            // メール閲覧権限が無い為、アクセスエラー
            ActionMessage msg = new ActionMessage("search.data.notfound",
                    gsMsg.getMessage("cmn.shortmail"));
            StrutsUtil.addMessage(errors, msg, "smlNotFound");
            return;
        }

        String[] smlSids = new String[sidList.size()];
        for (int i = 0; i < sidList.size(); i++) {
            smlSids[i] = sidList.get(i);
        }
        this.setSmlSids(smlSids);

        String[] smlNums = new String[numList.size()];
        for (int i = 0; i < numList.size(); i++) {
            smlNums[i] = numList.get(i);
        }
        this.setSmlNums(smlNums);
    }
}
