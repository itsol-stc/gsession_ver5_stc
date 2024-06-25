package jp.groupsession.v2.fil;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ファイル管理で使用するフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractFileForm extends AbstractGsForm {

    /** ダウンロード対象 添付ファイルSID */
    private String fileSid__ = null;
    /** 全文検索使用区分 */
    private int fileSearchFlg__ = 0;

    /**
     * <p>fileSid を取得します。
     * @return fileSid
     */
    public String getFileSid() {
        return fileSid__;
    }

    /**
     * <p>fileSid をセットします。
     * @param fileSid fileSid
     */
    public void setFileSid(String fileSid) {
        fileSid__ = fileSid;
    }

    /**
     * <p>fileSearchFlg を取得します。
     * @return fileSid
     */
    public int getFileSearchFlg() {
        return fileSearchFlg__;
    }

    /**
     * <p>fileSid をセットします。
     * @param fileSearchFlg fileSearchFlg
     */
    public void setFileSearchFlg(int fileSearchFlg) {
        fileSearchFlg__ = fileSearchFlg;
    }
}
