package jp.groupsession.v2.man.man430kn;

import jp.groupsession.v2.man.man430.Man430ParamModel;

/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430knParamModel extends Man430ParamModel {

    /** ページの表示を許可するドメイン */
    private String[] man430knPermittedDomains__ = null;


    /**
     * <p> man430knPermittedDomainsを取得します。
     * @return man430knPermittedDomains
     * */
    public String[] getMan430knPermittedDomains() {
        return man430knPermittedDomains__;
    }
    /**
     * <p> man430knPermittedDomainsをセットします。
     * @param man430knPermittedDomains man430knPermittedDomains
     * */
    public void setMan430knPermittedDomains(
            String[] man430knPermittedDomains) {
        man430knPermittedDomains__ = man430knPermittedDomains;
    }
}
