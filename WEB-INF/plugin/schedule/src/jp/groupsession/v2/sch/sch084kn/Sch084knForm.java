package jp.groupsession.v2.sch.sch084kn;

import jp.groupsession.v2.sch.sch084.Sch084Form;

/**
 * <br>[機  能] スケジュール 管理者設定 スケジュールインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch084knForm extends Sch084Form {

    /** 取込みファイル名称 */
    private String sch084knFileName__ = null;

    /**
     * <p>sch084knFileName を取得します。
     * @return sch084knFileName
     */
    public String getSch084knFileName() {
        return sch084knFileName__;
    }

    /**
     * <p>sch084knFileName をセットします。
     * @param sch084knFileName sch084knFileName
     */
    public void setSch084knFileName(String sch084knFileName) {
        sch084knFileName__ = sch084knFileName;
    }

}