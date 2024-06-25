package jp.groupsession.v2.anp.anp080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpAdmConfDao;
import jp.groupsession.v2.anp.model.AnpAdmConfModel;
import jp.groupsession.v2.anp.model.AnpCmnConfModel;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.dao.base.CmnOauthTokenDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSPassword;


/**
 * <br>[機  能] 管理者設定・基本設定画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp080Biz.class);

    /**
     * <br>[機  能] 初期表示情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp080Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp080ParamModel anp080Model, RequestModel reqMdl, Connection con)
            throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //全て表示区分(コンボボックス)
        boolean allGroupUserFlg = false;

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //グループリストに初期表示するデフォルトグループを取得
        String dspGpSidStr = anp080Model.getAnp080SelectGroupSid();
        if (StringUtil.isNullZeroString(dspGpSidStr)) {
            dspGpSidStr = anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg);
        }
        anp080Model.setAnp080SelectGroupSid(dspGpSidStr);

        //暗号化プロトコルコンボを設定
        MailEncryptBiz protcolBiz = new MailEncryptBiz(reqMdl);
        anp080Model.setAnp080AngoProtocolCombo(
                protcolBiz.setDspProtocolLabels());
        
        //認証状態
        OAuthBiz oatBiz = new OAuthBiz();
        int cotSid = oatBiz.checkOAuthToken(
                con, anp080Model.getAnp080provider(), anp080Model.getAnp080SendMail(), true);
        if (cotSid > 0) {
            anp080Model.setAnp080oauthCompFlg(GSConstAnpi.AUTH_DONE);
        } else {
            anp080Model.setAnp080oauthCompFlg(GSConstAnpi.AUTH_YET);
        }

        //プロバイダリストの設定
        _setProviderCombo(con, anp080Model, reqMdl);

    }

    /**
     * <br>[機  能] 設定情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp080Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setConfData(Anp080ParamModel anp080Model, RequestModel reqMdl, Connection con)
            throws Exception {

        //Oauthトークン情報
        CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //共通設定情報のセット
        AnpCmnConfModel cmnConf = anpiBiz.getAnpCmnConfModel(con);
        anp080Model.setAnp080UrlSetKbn(cmnConf.getApcUrlKbn());
        anp080Model.setAnp080BaseUrl(cmnConf.getApcUrlBase());
        //基本URL
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        anp080Model.setAnp080SvBaseUrlAuto(urlBiz.getBaseUrl(reqMdl));

        anp080Model.setAnp080authMethod(cmnConf.getApcAuthType());
        anp080Model.setAnp080SendMail(cmnConf.getApcAddress());
        anp080Model.setAnp080SendHost(cmnConf.getApcSendHost());
        anp080Model.setAnp080SendPort(String.valueOf(cmnConf.getApcSendPort()));
        anp080Model.setAnp080SendEncrypt(cmnConf.getApcSendSsl());
        anp080Model.setAnp080SmtpAuth(String.valueOf(cmnConf.getApcSmtpAuth()));
        anp080Model.setAnp080SendUser(cmnConf.getApcSendUser());
        anp080Model.setAnp080SendPass(GSPassword.getDecryPassword(cmnConf.getApcSendPass()));

        //管理者情報のセット
        String[] admUsers = null;
        AnpAdmConfDao adao = new AnpAdmConfDao(con);
        List<AnpAdmConfModel> alist = adao.select();

        if (alist != null && !alist.isEmpty()) {
            List<String> admList = new ArrayList<String>();
            for (AnpAdmConfModel bean : alist) {
                if (bean.getUsrSid() != -1) {
                    admList.add(String.valueOf(bean.getUsrSid()));
                }

                if (bean.getGrpSid() != -1) {
                    admList.add(String.valueOf("G" + bean.getGrpSid()));
                }
            }
            admUsers = (String[]) admList.toArray(new String[admList.size()]);
        }
        anp080Model.setAnp080AdmUserList(admUsers);

        int cotSid = cmnConf.getCotSid();
        OauthDataModel odMdl = cotDao.getAuthData(cotSid);

        //プロバイダ
        anp080Model.setAnp080provider(odMdl.getCouSid());

        //認証SID
        anp080Model.setAnp080cotSid(odMdl.getCotSid());
    }

    /**
     * <br>[機  能] ユーザリストに選択されているユーザリストを追加して戻します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userList   元のユーザリスト
     * @param selectSids 選択ユーザリスト
     * @return 選択ユーザを追加したユーザリスト
     * @throws Exception 実行例外
     */
    public String[] getUserListAdd(String[] userList, String[] selectSids)
            throws Exception {
        if (selectSids == null) {
            return userList;
        }
        if (selectSids.length < 1) {
            return userList;
        }

        log__.debug("追加後ユーザを取得");

        //元のユーザリストから、戻り配列を作成
        List<String> newList = new ArrayList<String>();
        if (userList != null && userList.length > 0) {
            for (String sid: userList) {
                newList.add(sid);
            }
        }

        //選択されているユーザSIDを追加
        if (selectSids != null && selectSids.length > 0) {
            for (String sid: selectSids) {
                newList.add(sid);
            }
        }

        String[] ret = null;
        if (newList.size() > 0) {
            ret = (String[]) newList.toArray(new String[newList.size()]);
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザリストから選択されているユーザを削除して戻します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userList   元のユーザリスト
     * @param selectSids 選択ユーザリスト
     * @return 選択ユーザを削除したユーザリスト
     * @throws Exception 実行例外
     */
    public String[] getUserListDel(String[] userList, String[] selectSids)
            throws Exception {

        log__.debug("削除後ユーザを取得");

        if (userList == null || userList.length == 0) {
            return null;
        }

        //元のリストから選択されているユーザ以外を追加
        List<String> newList = new ArrayList<String>();
        String[] selects = new String[0];
        if (selectSids != null) {
            selects = Arrays.copyOf(selectSids, selectSids.length);
        }
        Arrays.sort(selects);

        for (String sid: userList) {
            if (Arrays.binarySearch(selects, sid) < 0) {
                newList.add(sid);
            }
        }

        String[] ret = null;
        if (newList.size() > 0) {
            ret = (String[]) newList.toArray(new String[newList.size()]);
        }
        return ret;
    }

    /**
     * <br>[機  能] プロバイダコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setProviderCombo(Connection con, Anp080ParamModel paramMdl,
            RequestModel reqMdl)
                    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> providerCombo = new ArrayList<LabelValueBean>();
        providerCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        CmnOauthDao oauthDao = new CmnOauthDao(con);
        List<CmnOauthModel> authDataList =  oauthDao.select();
        for (CmnOauthModel authData : authDataList) {
            providerCombo.add(
                    new LabelValueBean(
                            OAuthBiz.getProviderName(authData.getCouProvider(), reqMdl),
                            String.valueOf(authData.getCouSid())
                            ));
        }

        paramMdl.setAnp080providerList(providerCombo);
    }
}