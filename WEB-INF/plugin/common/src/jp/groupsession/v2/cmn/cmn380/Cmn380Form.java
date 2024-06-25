package jp.groupsession.v2.cmn.cmn380;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ファイルプレビュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn380Form extends AbstractGsForm {
    /** プレビュー対象ファイルのダウンロードURL */
    private String cmn380PreviewURL__ = null;
    /** プレビュー対象ファイルの拡張子 */
    private String cmn380PreviewFileExtension__ = null;
    /** プレビュー対象ファイルのダウンロードURL(PDF表示用) */
    private String cmn380PreviewPdfURL__ = null;
    /**
     * <p>cmn380PreviewURL を取得します。
     * @return cmn380PreviewURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewURL__
     */
    public String getCmn380PreviewURL() {
        return cmn380PreviewURL__;
    }
    /**
     * <p>cmn380PreviewURL をセットします。
     * @param cmn380PreviewURL cmn380PreviewURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewURL__
     */
    public void setCmn380PreviewURL(String cmn380PreviewURL) {
        cmn380PreviewURL__ = cmn380PreviewURL;
    }
    /**
     * <p>cmn380PreviewFileExtension を取得します。
     * @return cmn380PreviewFileExtension
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewFileExtension__
     */
    public String getCmn380PreviewFileExtension() {
        return cmn380PreviewFileExtension__;
    }
    /**
     * <p>cmn380PreviewFileExtension をセットします。
     * @param cmn380PreviewFileExtension cmn380PreviewFileExtension
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewFileExtension__
     */
    public void setCmn380PreviewFileExtension(String cmn380PreviewFileExtension) {
        cmn380PreviewFileExtension__ = cmn380PreviewFileExtension;
    }
    /**
     * <p>cmn380PreviewPdfURL を取得します。
     * @return cmn380PreviewPdfURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewPdfURL__
     */
    public String getCmn380PreviewPdfURL() {
        return cmn380PreviewPdfURL__;
    }
    /**
     * <p>cmn380PreviewPdfURL をセットします。
     * @param cmn380PreviewPdfURL cmn380PreviewPdfURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewPdfURL__
     */
    public void setCmn380PreviewPdfURL(String cmn380PreviewPdfURL) {
        cmn380PreviewPdfURL__ = cmn380PreviewPdfURL;
    }
}
