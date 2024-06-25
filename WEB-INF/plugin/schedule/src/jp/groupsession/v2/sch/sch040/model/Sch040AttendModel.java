package jp.groupsession.v2.sch.sch040.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュールで使用する出欠確認回答結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040AttendModel extends AbstractModel {

    /** 回答日付 */
    private String attendAnsDate__ = null;
    /** 回答ユーザSID */
    private int attendAnsUsrSid__ = 0;
    /** 回答ユーザ名 */
    private String attendAnsUsrName__ = null;
    /** 回答ユーザ有効フラグ */
    private int attendAnsUsrUkoFlg__ = 0;
    /** 回答結果 */
    private int attendAnsKbn__ = 0;
    /** 回答コメント*/
    private String attendAnsComment__ = null;

    /**
     * <p>attendAnsDate を取得します。
     * @return attendAnsDate
     */
    public String getAttendAnsDate() {
        return attendAnsDate__;
    }
    /**
     * <p>attendAnsDate をセットします。
     * @param attendAnsDate attendAnsDate
     */
    public void setAttendAnsDate(String attendAnsDate) {
        attendAnsDate__ = attendAnsDate;
    }
    /**
     * <p>attendAnsUsrSid を取得します。
     * @return attendAnsUsrSid
     */
    public int getAttendAnsUsrSid() {
        return attendAnsUsrSid__;
    }
    /**
     * <p>attendAnsUsrSid をセットします。
     * @param attendAnsUsrSid attendAnsUsrSid
     */
    public void setAttendAnsUsrSid(int attendAnsUsrSid) {
        attendAnsUsrSid__ = attendAnsUsrSid;
    }
    /**
     * <p>attendAnsUsrName を取得します。
     * @return attendAnsUsrName
     */
    public String getAttendAnsUsrName() {
        return attendAnsUsrName__;
    }
    /**
     * <p>attendAnsUsrName をセットします。
     * @param attendAnsUsrName attendAnsUsrName
     */
    public void setAttendAnsUsrName(String attendAnsUsrName) {
        attendAnsUsrName__ = attendAnsUsrName;
    }
    /**
     * <p>attendAnsUsrUkoFlg を取得します。
     * @return attendAnsUsrUkoFlg
     */
    public int getAttendAnsUsrUkoFlg() {
        return attendAnsUsrUkoFlg__;
    }
    /**
     * <p>attendAnsUsrUkoFlg をセットします。
     * @param attendAnsUsrUkoFlg attendAnsUsrUkoFlg
     */
    public void setAttendAnsUsrUkoFlg(int attendAnsUsrUkoFlg) {
        attendAnsUsrUkoFlg__ = attendAnsUsrUkoFlg;
    }
    /**
     * <p>attendAnsKbn を取得します。
     * @return attendAnsKbn
     */
    public int getAttendAnsKbn() {
        return attendAnsKbn__;
    }
    /**
     * <p>attendAnsKbn をセットします。
     * @param attendAnsKbn attendAnsKbn
     */
    public void setAttendAnsKbn(int attendAnsKbn) {
        attendAnsKbn__ = attendAnsKbn;
    }
    /**
     * <p>attendAnsComment を取得します。
     * @return attendAnsComment
     * @see jp.groupsession.v2.sch.sch040.model.Sch040AttendModel#attendAnsComment__
     */
    public String getAttendAnsComment() {
        return attendAnsComment__;
    }
    /**
     * <p>attendAnsComment をセットします。
     * @param attendAnsComment attendAnsComment
     * @see jp.groupsession.v2.sch.sch040.model.Sch040AttendModel#attendAnsComment__
     */
    public void setAttendAnsComment(String attendAnsComment) {
        attendAnsComment__ = attendAnsComment;
    }

}
