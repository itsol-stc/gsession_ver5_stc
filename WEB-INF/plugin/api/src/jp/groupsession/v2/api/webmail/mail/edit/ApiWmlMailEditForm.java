package jp.groupsession.v2.api.webmail.mail.edit;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] WEBメールを更新するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-mail/edit",
plugin = "webmail", name = "メール状態変更",
url = "/api/webmail/mail/edit.do", reqtype = "POST")
public class ApiWmlMailEditForm extends AbstractApiForm {

    /** アカウントSID*/
    @ApiParam(name = "wacSid", viewName = "アカウントSID")
    private int wacSid__  = -1;

    /** 実行コマンド */
    @ApiParam(name = "wmlCmd", viewName = "実行コマンド")
    private int wmlCmd__  = -1;

    /** メールSID一覧 */
    @ApiParam(name = "wmlSids", viewName = "メッセージ番号一覧")
    private long[] wmlSids__;

    /** ラベルSID(ラベル追加/削除のみ)*/
    private int wlbSid__ = -1;

    /** 移動先ディレクトリSID(移動/削除のみ)*/
    private long wdrSid__ = -1;

    /**
     * <p>wlbSid を取得します。
     * @return wlbSid
     */
    public int getWlbSid() {
        return wlbSid__;
    }
    /**
     * <p>wlbSid をセットします。
     * @param wlbSid ラベルSID
     */
    public void setWlbSid(int wlbSid) {
        wlbSid__ = wlbSid;
    }

    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid アカウントSID
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>wmlCmd を取得します。
     * @return wmlCmd   0:既読 / 1:未読
     *                 10:ラベル追加 / 11:ラベル削除
     *                 20:元に戻す / 21:削除 / 22:完全削除
     */
    public int getWmlCmd() {
        return wmlCmd__;
    }
    /**
     * <p>wmlCmd をセットします。
     * @param wmlCmd 実行コマンド
     */
    public void setWmlCmd(int wmlCmd) {
        wmlCmd__ = wmlCmd;
    }

    /**
     * <p>wmlSids を取得します。
     * @return wmlSids
     */
    public long[] getWmlSids() {
        return wmlSids__;
    }
    /**
     * <p>wmlSids をセットします。
     * @param wmlSids WEBメールSID一覧
     */
    public void setWmlSids(long[] wmlSids) {
        wmlSids__ = wmlSids;
    }

    /**
     * <p>wdrSid を取得します。
     * @return wdrSid
     */
    public long getWdrSid() {
        return wdrSid__;
    }
    /**
     * <p>wdrSid をセットします。
     * @param wdrSid ディレクトリSID
     */
    public void setWdrSid(long wdrSid) {
        wdrSid__ = wdrSid;
    }
}
