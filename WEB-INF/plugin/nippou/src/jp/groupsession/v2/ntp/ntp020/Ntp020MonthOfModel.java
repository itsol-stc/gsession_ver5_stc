package jp.groupsession.v2.ntp.ntp020;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010UsrModel;

/**
 * <br>[機  能] ユーザ毎の日報情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp020MonthOfModel extends AbstractModel {

    /** ユーザ情報 */
    private Ntp010UsrModel ntp020UsrMdl__ = null;
    /** 日報情報リスト */
    private List<Ntp020DayOfModel> ntp020NtpList__ = null;
    /**
     * <p>ntp020UsrMdl を取得します。
     * @return ntp020UsrMdl
     */
    public Ntp010UsrModel getNtp020UsrMdl() {
        return ntp020UsrMdl__;
    }
    /**
     * <p>ntp020UsrMdl をセットします。
     * @param ntp020UsrMdl ntp020UsrMdl
     */
    public void setNtp020UsrMdl(Ntp010UsrModel ntp020UsrMdl) {
        ntp020UsrMdl__ = ntp020UsrMdl;
    }
    /**
     * <p>ntp020NtpList を取得します。
     * @return ntp020NtpList
     */
    public List<Ntp020DayOfModel> getNtp020NtpList() {
        return ntp020NtpList__;
    }
    /**
     * <p>ntp020NtpList をセットします。
     * @param ntp020NtpList ntp020NtpList
     */
    public void setNtp020NtpList(List<Ntp020DayOfModel> ntp020NtpList) {
        ntp020NtpList__ = ntp020NtpList;
    }
}
