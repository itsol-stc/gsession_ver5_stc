package jp.groupsession.v2.ntp.ntp010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.ntp.GSConstNippou;

/**
 * <br>[機  能] ユーザ毎の日報情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp010WeekOfModel extends AbstractModel {

    /** ユーザ情報 */
    private Ntp010UsrModel ntp010UsrMdl__ = null;
    /** スケジュール情報リスト */
    private ArrayList < Ntp010DayOfModel > ntp010NtpList__ = null;

    //その他プラグインの利用可能状況
    /** 在席管理プラグイン利用可:0・不可:1*/
    private int zaisekiUseOk__ = GSConstNippou.PLUGIN_USE;
    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConstNippou.PLUGIN_USE;

    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }
    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }
    /**
     * <p>zaisekiUseOk を取得します。
     * @return zaisekiUseOk
     */
    public int getZaisekiUseOk() {
        return zaisekiUseOk__;
    }
    /**
     * <p>zaisekiUseOk をセットします。
     * @param zaisekiUseOk zaisekiUseOk
     */
    public void setZaisekiUseOk(int zaisekiUseOk) {
        zaisekiUseOk__ = zaisekiUseOk;
    }
    /**
     * @return ntp010NtpList を戻します。
     */
    public ArrayList < Ntp010DayOfModel > getNtp010NtpList() {
        return ntp010NtpList__;
    }
    /**
     * @param ntp010NtpList 設定する ntp010NtpList。
     */
    public void setNtp010NtpList(ArrayList < Ntp010DayOfModel > ntp010NtpList) {
        ntp010NtpList__ = ntp010NtpList;
    }
    /**
     * @return ntp010UsrMdl を戻します。
     */
    public Ntp010UsrModel getNtp010UsrMdl() {
        return ntp010UsrMdl__;
    }
    /**
     * @param ntp010UsrMdl 設定する ntp010UsrMdl。
     */
    public void setNtp010UsrMdl(Ntp010UsrModel ntp010UsrMdl) {
        ntp010UsrMdl__ = ntp010UsrMdl;
    }

}
