package jp.groupsession.v2.rsv.rsv250kn;

import jp.groupsession.v2.rsv.rsv250.Rsv250Form;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv250knForm extends Rsv250Form {

    /** 取込みファイル名称 */
    private String rsv250knFileName__ = null;

    /**
     * @return rsv250knFileName
     */
    public String getRsv250knFileName() {
        return rsv250knFileName__;
    }

    /**
     * @param rsv250knFileName 設定する rsv250knFileName
     */
    public void setRsv250knFileName(String rsv250knFileName) {
        rsv250knFileName__ = rsv250knFileName;
    }


}