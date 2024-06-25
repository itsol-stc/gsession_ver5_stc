package jp.groupsession.v2.hlp.hlp002;

import jp.groupsession.v2.hlp.hlp001.Hlp001Form;


/**
 * <br>[機  能] ヘルプ ヘッダーのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp002Form extends Hlp001Form {

    /** 検索テキスト */
    private String hlp002SearchText__ = null;

    /**
     * <p>hlp002SearchText を取得します。
     * @return hlp002SearchText
     */
    public String getHlp002SearchText() {
        return hlp002SearchText__;
    }

    /**
     * <p>hlp002SearchText をセットします。
     * @param hlp002SearchText hlp002SearchText
     */
    public void setHlp002SearchText(String hlp002SearchText) {
        hlp002SearchText__ = hlp002SearchText;
    }


}
