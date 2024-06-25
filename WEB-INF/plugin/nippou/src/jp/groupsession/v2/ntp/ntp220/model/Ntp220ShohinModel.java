package jp.groupsession.v2.ntp.ntp220.model;

import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <p>NTP_SHOHIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Ntp220ShohinModel extends NtpShohinModel  {
    /** 件数 */
    private int nshCnt__ = 0;

    /**
     * <p>nshCnt を取得します。
     * @return nshCnt
     */
    public int getNshCnt() {
        return nshCnt__;
    }

    /**
     * <p>nshCnt をセットします。
     * @param nshCnt nshCnt
     */
    public void setNshCnt(int nshCnt) {
        nshCnt__ = nshCnt;
    }
}
