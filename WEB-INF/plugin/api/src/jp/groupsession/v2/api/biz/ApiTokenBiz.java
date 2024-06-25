package jp.groupsession.v2.api.biz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.dao.ApiTokenDao;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.login.biz.ITokenAuthBiz;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能] Apiトークン関連 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiTokenBiz implements ITokenAuthBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiTokenBiz.class);

    /** コネクション */
    protected Connection con__ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;
    /**トークン用ロックオブジェクト*/
    private static final Object LOCK_TOKEN__ = new Object();

    /**
     * コンストラクタ
     */
    public ApiTokenBiz() {
        super();
    }


    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public ApiTokenBiz(Connection con, RequestModel reqMdl) {
        super();
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] リクエストからトークンを取り出す
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエストモデル
     * @return トークン
     */
    public String readAuthrizationToken(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth == null) {
            return null;
        }
        String realm = auth.substring(auth.lastIndexOf(' ') + 1);
        return realm;
    }
    @Override
    public String createToken(HttpServletRequest req, BaseUserModel smodel,
            IApiConfModel aconf) throws SQLException {
        return saveToken(req, smodel, aconf).getAptToken();
    }
    /**
     *
     * <br>[機  能] トークンの発行処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel ログインユーザ
     * @param aconf Api基本設定モデル
     * @return トークンモデル
     * @throws SQLException SQL実行時例外
     */
    public ApiTokenModel saveToken(HttpServletRequest req,
            BaseUserModel smodel, IApiConfModel aconf) throws SQLException {
        ApiTokenModel apt = null;
        synchronized (LOCK_TOKEN__) {
            Connection con = null;
            boolean succsess = false;
            try {
                con = GroupSession.getConnection(
                        reqMdl__.getDomain(),
                        1000);
                con.setAutoCommit(false);
                ApiTokenDao dao = new ApiTokenDao(con);
                int limitCnt = 3;
                String token = "";
                //トークン重複確認
                boolean exist = true;
                OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
                while (exist && limitCnt > 1) {
                    token = otpBiz.createToken(smodel.getLgid());
                    ApiTokenModel mdl = dao.select(token);
                    if (mdl == null) {
                        exist = false;
                    }
                    limitCnt--;
                }
                ApiConfBiz confBiz = new ApiConfBiz(aconf);

                UDate now = new UDate();
                UDate limit = confBiz.getLimitDate(now, con);

                apt = new ApiTokenModel();
                apt.setAptToken(token);
                apt.setUsrSid(smodel.getUsrsid());
                apt.setAptLimitDate(limit);
                UserAgent agent = new UserAgent(req);
                apt.setAptClient(agent.getClient());
                apt.setAptClientId(agent.getCuid());
                apt.setAptIp(CommonBiz.getRemoteAddr(req));
                apt.setAptAuid(reqMdl__.getSmodel().getUsrsid());
                apt.setAptAdate(now);
                apt.setAptEuid(reqMdl__.getSmodel().getUsrsid());
                apt.setAptEdate(now);
                dao.insert(apt);
                con.commit();
                succsess = true;
            } catch (SQLException sqle) {
                throw sqle;
            } catch (Exception e) {
                log__.error("データソース取得に失敗", e);
                throw new SQLException(e);
            } finally {
                if (con != null) {
                    if (!succsess) {
                        JDBCUtil.rollback(con);
                    }
                    JDBCUtil.closeConnection(con);
                }
                con = null;
            }
        }
        return apt;

    }
    @Override
    public LoginResultModel authToken(String token, Connection con) throws SQLException {
        LoginResultModel resultModel = new LoginResultModel();
        ApiTokenDao dao = new ApiTokenDao(con);
        ApiTokenModel mdl = dao.select(token);
        if (mdl == null || mdl.getAptJkbn() != 0) {
            resultModel.setErrCode(LoginResultModel.ECODE_TOKENNONE);
            resultModel.setErrors();
            return resultModel;
        }
        UDate now = new UDate();
        if (now.compare(now, mdl.getAptLimitDate()) == UDate.SMALL) {
            resultModel.setErrCode(LoginResultModel.ECODE_TOKENTIMEOVER);
            resultModel.setErrors();
            return resultModel;
        }
        AuthDao adao = new AuthDao(con);
        BaseUserModel smodel = null;
        try {
            smodel = adao.selectLogin(mdl.getUsrSid());
        } catch (SQLException e) {
            log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
            throw e;
        }
        //該当ユーザなし
        if (smodel == null) {
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            return resultModel;
        } else if (smodel.getUsrsid() == 1) {
            //システムメールユーザはログイン不可
            resultModel.setErrCode(LoginResultModel.ECODE_SYSMAIL_LOGIN);
            resultModel.setErrors();
            return resultModel;
        }
        if (smodel.getUsrsid() > GSConstUser.USER_RESERV_SID
                && smodel.getUsrUkoFlg() == GSConst.YUKOMUKO_MUKO) {
            //ログイン停止ユーザはログイン不可
            resultModel.setErrCode(LoginResultModel.ECODE_LOGINTEISI_USER);
            resultModel.setErrors();
            return resultModel;
        }
        //所属グループ未設定の場合、ログイン不可
        CmnBelongmDao belongDao = new CmnBelongmDao(con);
        if (belongDao.selectUserBelongGroupSid(smodel.getUsrsid()).isEmpty()) {
            resultModel.setErrCode(LoginResultModel.ECODE_BELONGGRP_NONE);

            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.auth.notset.belonggroup.api");
            StrutsUtil.addMessage(errors, msg, "error.auth.notset.belonggroup.api");
            resultModel.setErrors(errors);
    
            return resultModel;
        }
        //デフォルトグループ未設定の場合、ログイン不可
        GroupBiz grpBiz = new GroupBiz();
        if (grpBiz.getDefaultGroupId(smodel.getUsrsid(), con) == null) {
            resultModel.setErrCode(LoginResultModel.ECODE_DEFGRP_NONE);

            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.auth.notset.defgroup.api");
            StrutsUtil.addMessage(errors, msg, "error.auth.notset.defgroup.api");
            resultModel.setErrors(errors);
    
            return resultModel;
        }
        resultModel.setLoginUser(smodel);
        return resultModel;
    }
    /**
     * <p>con を取得します。
     * @return con
     * @see jp.groupsession.v2.api.biz.ApiTokenBiz#con__
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     * @see jp.groupsession.v2.api.biz.ApiTokenBiz#con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.api.biz.ApiTokenBiz#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }

    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.api.biz.ApiTokenBiz#reqMdl__
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    @Override
    public boolean isAbleTokenAuth(HttpServletRequest req, Connection con)
            throws SQLException {
        ApiConfBiz confBiz = new ApiConfBiz();
        return confBiz.isAbleTokenAuth(req, con);
    }

}
