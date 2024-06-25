package jp.groupsession.v2.tcd.tcd180kn;

import jp.groupsession.v2.tcd.tcd180.Tcd180Form;

/**
 * <br>[機  能] タイムカード 勤務表フォーマット登録確認画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd180knForm extends Tcd180Form{

    /** 取込みファイル名 */
    private String fileName__ = null;

    /**
     * <p>fileName を取得します。
     * @return fileName
     * @see jp.groupsession.v2.tcd.tcd180kn.Tcd180knForm#fileName__
     */
    public String getFileName() {
        return fileName__;
    }

    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     * @see jp.groupsession.v2.tcd.tcd180kn.Tcd180knForm#fileName__
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
}
