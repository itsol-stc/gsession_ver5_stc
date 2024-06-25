package jp.groupsession.v2.ntp.ntp084kn;

import jp.groupsession.v2.ntp.ntp084.Ntp084ParamModel;

/**
 * <br>[機  能] 日報 管理者設定 インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp084knParamModel extends Ntp084ParamModel {

    /** 取込みファイル名称 */
    private String ntp084knFileName__ = null;

    /**
     * <p>ntp084knFileName を取得します。
     * @return ntp084knFileName
     */
    public String getNtp084knFileName() {
        return ntp084knFileName__;
    }

    /**
     * <p>ntp084knFileName をセットします。
     * @param ntp084knFileName ntp084knFileName
     */
    public void setNtp084knFileName(String ntp084knFileName) {
        ntp084knFileName__ = ntp084knFileName;
    }

}