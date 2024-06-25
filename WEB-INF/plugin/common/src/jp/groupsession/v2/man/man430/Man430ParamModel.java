package jp.groupsession.v2.man.man430;


import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430ParamModel extends AbstractParamModel {

    /** オープンリダイレクト制限の有無 */
    private int man430ExtPageDspKbn__ = GSConstMain.DSP_EXT_PAGE_NO_LIMIT;
    /** オープンリダイレクト先として許可するドメイン テキストエリア */
    private String man430ExtDomainArea__ = null;
    /** 初期表示フラグ  */
    private int man430InitFlg__ = GSConstMain.DSP_FIRST;


    /**
     * <p>man430ExtPageDspKbnを取得します。
     * @return man430ExtPageDspKbn
     * */
    public int getMan430ExtPageDspKbn() {
        return man430ExtPageDspKbn__;
    }
    /**
     * <p>man430ExtPageDspKbnをセットします。
     * @param man430ExtPageDspKbn man430ExtPageDspKbn
     * */
    public void setMan430ExtPageDspKbn(int man430ExtPageDspKbn) {
        man430ExtPageDspKbn__ = man430ExtPageDspKbn;
    }

    /**
     * <p>man430ExtDomainAreaを取得します。
     * @return man430ExtDomainArea
     * */
    public String getMan430ExtDomainArea() {
        return man430ExtDomainArea__;
    }
    /**
     * <p>man430ExtDomainAreaをセットします。
     * @param man430ExtDomainArea man430ExtDomainArea
     * */
    public void setMan430ExtDomainArea(String man430ExtDomainArea) {
        man430ExtDomainArea__ = man430ExtDomainArea;
    }

    /**
     * <p>man430InitFlgを取得します。
     * @return man430InitFlg
     * */
    public int getMan430InitFlg() {
        return man430InitFlg__;
    }
    /**
     * <p>man430InitFlgをセットします。
     * @param man430InitFlg man430InitFlg
     * */
    public void setMan430InitFlg(int man430InitFlg) {
        man430InitFlg__ = man430InitFlg;
    }

}
