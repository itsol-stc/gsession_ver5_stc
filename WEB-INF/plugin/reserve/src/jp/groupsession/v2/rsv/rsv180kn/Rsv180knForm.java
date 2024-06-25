package jp.groupsession.v2.rsv.rsv180kn;

import jp.groupsession.v2.rsv.rsv180.Rsv180Form;

/**
 * <br>[機  能] 施設予約 施設インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv180knForm extends Rsv180Form {

    /** 取込みファイル名称 */
    private String rsv180knFileName__ = null;
    /** 取込みユーザ名称 */
    private String rsv180knUserName__ = null;

    /**
     * <p>rsv180knFileName__ を取得します。
     * @return rsv180knFileName
     */
    public String getRsv180knFileName() {
        return rsv180knFileName__;
    }

    /**
     * <p>rsv180knFileName__ をセットします。
     * @param rsv180knFileName rsv180knFileName__
     */
    public void setRsv180knFileName(String rsv180knFileName) {
        rsv180knFileName__ = rsv180knFileName;
    }
    /**
     * <p>rsv180knUserName__ を取得します。
     * @return rsv180knUserName
     */
    public String getRsv180knUserName() {
        return rsv180knUserName__;
    }

    /**
     * <p>rsv180knUserName__ をセットします。
     * @param rsv180knUserName rsv180knUserName__
     */
    public void setRsv180knUserName(String rsv180knUserName) {
        rsv180knUserName__ = rsv180knUserName;
    }

}