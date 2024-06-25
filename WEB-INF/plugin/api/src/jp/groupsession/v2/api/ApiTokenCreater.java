package jp.groupsession.v2.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.api.biz.ApiTokenBiz;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.mbh.interfaces.IApiTokenCreater;

/**
 * <br>[機  能] 中継サーバ間で行う処理用
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiTokenCreater implements IApiTokenCreater {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiTokenCreater.class);

    /**
     *
     * デフォルトコンストラクタ
     */
    private ApiTokenCreater() {

    }

    /**
     *
     * <br>[機  能] インスタンス取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param domain String
     * @return インスタンス
     */
    public static IApiTokenCreater getInstance(Connection con, String domain) {
        IApiTokenCreater instance = null;
       String clsName = "jp.groupsession.v3.api.ApiTokenCreater";

       if (!StringUtil.isNullZeroString(clsName)) {
           try {
               @SuppressWarnings("unchecked")
               Class<ApiTokenCreater> cls = (Class<ApiTokenCreater>) Class.forName(clsName);
               Method m = cls.getMethod("getInstance", Connection.class, String.class);
               instance = (IApiTokenCreater) m.invoke(cls, con, domain);
           } catch (IllegalAccessException  | ClassNotFoundException
                   | IllegalArgumentException | InvocationTargetException
                   | NoSuchMethodException | SecurityException e) {
           }
           if (instance != null) {
               return instance;
           }
       }
       instance = new ApiTokenCreater();
       return instance;
   }

    @Override
    public String doCreateToken(Connection con, 
            HttpServletRequest req, RequestModel reqMdl, int usrSid) {
        
        ApiConfBiz confBiz = new ApiConfBiz();
        ApiTokenBiz tokenBiz = new ApiTokenBiz(con, reqMdl);
        ApiTokenModel tokenMdl = new ApiTokenModel();
        try {
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            CmnUsrmModel cuMdl;
            cuMdl = cuDao.select(usrSid);
            BaseUserModel buMdl = new BaseUserModel();
            buMdl.setLgid(cuMdl.getUsrLgid());
            buMdl.setUsrsid(cuMdl.getUsrSid());
            tokenMdl = tokenBiz.saveToken(req, buMdl, confBiz.getConf(con));
        } catch (SQLException e) {
            log__.error("APIトークン発行に失敗" + e);
        }
        return tokenMdl.getAptToken();
    }
}
