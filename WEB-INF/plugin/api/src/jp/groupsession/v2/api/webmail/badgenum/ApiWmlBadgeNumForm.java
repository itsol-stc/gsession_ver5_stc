package jp.groupsession.v2.api.webmail.badgenum;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] WEBメールのバッジ表示数を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-badgenum",
plugin = "webmail", name = "バッジ表示数(未読件数)取得",
url = "/api/webmail/badgenum.do", reqtype = "GET")
public class ApiWmlBadgeNumForm extends AbstractApiForm {

    /** バッジ表示タイプ メールボックス(ディレクトリ) */
    public static final int BADGE_TYPE_MAILBOX_ = 0;
    /** バッジ表示タイプ ラベル */
    public static final int BADGE_TYPE_LABEL_   = 1;

    /** アカウントSID*/
    @ApiParam(name = "wacSid", viewName = "アカウントSID")
    private int wacSid__  = -1;

    /** 取得タイプ*/
    private int badgeType__ = -1; // -1:全件 / 0:メールボックス / 1:ラベル

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
     * <p>badgeType を取得します。
     * @return badgeType
     */
    public int getBadgeType() {
        return badgeType__;
    }
    /**
     * <p>badgeType をセットします。
     * @param badgeType 取得データタイプ
     */
    public void setBadgeType(int badgeType) {
        badgeType__ = badgeType;
    }
}
