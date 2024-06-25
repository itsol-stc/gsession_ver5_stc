package jp.groupsession.v2.hlp.hlp010;

import jp.groupsession.v2.hlp.hlp001.Hlp001ParamModel;

/**
 * <br>[機  能] ヘルプ メイン画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp010ParamModel extends Hlp001ParamModel {
    /** 表示ヘルプURL */
    private String hlp010HelpUrl__ = null;

    /**
     * <p>hlp010HelpUrl を取得します。
     * @return hlp010HelpUrl
     */
    public String getHlp010HelpUrl() {
        return hlp010HelpUrl__;
    }

    /**
     * <p>hlp010HelpUrl をセットします。
     * @param hlp010HelpUrl hlp010HelpUrl
     */
    public void setHlp010HelpUrl(String hlp010HelpUrl) {
        hlp010HelpUrl__ = hlp010HelpUrl;
    }
}