package jp.groupsession.v2.cir;

import java.sql.Connection;

import jp.groupsession.v2.cir.util.CirConfigBundle;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.IGsListener;

/**
 * <br>[機  能] Servlet init() 又はdestroy()実行時に実行されるリスナーを実装
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirGsListnerImpl implements IGsListener {

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.IGsListener#gsInit(
     * jp.groupsession.v2.cmn.GSContext, java.sql.Connection, java.lang.String)
     */
    @Override
    public void gsInit(GSContext gscontext, Connection con, String domain)
            throws Exception {
        Object tmp = gscontext.get(GSContext.APP_ROOT_PATH);
        //設定ファイルの読み込み
        if (tmp instanceof String) {
            String path = (String) tmp;
            CirConfigBundle.readConfig(path);
        }
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.IGsListener#gsDestroy(
     * jp.groupsession.v2.cmn.GSContext, java.sql.Connection, java.lang.String)
     */
    @Override
    public void gsDestroy(GSContext gscontext, Connection con, String domain)
            throws Exception {

    }

}
