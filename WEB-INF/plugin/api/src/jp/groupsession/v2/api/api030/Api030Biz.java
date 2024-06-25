package jp.groupsession.v2.api.api030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.api.biz.ApiTokenBiz;
import jp.groupsession.v2.api.dao.ApiTokenDao;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] トークン管理画面 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api030Biz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** 検索ソートキー ユーザ*/
    public static final int TOKEN_SORTKEY_USER = 0;
    /** 検索ソートキー クライアント*/
    public static final int TOKEN_SORTKEY_CLIENT = 1;
    /** 検索ソートキー 発行日*/
    public static final int TOKEN_SORTKEY_ADATE = 2;
    /** 検索ソートキー 有効期限*/
    public static final int TOKEN_SORTKEY_LDATE = 3;
    /** 検索対象 無効化を除く 無効 */
    public static final int SEARCH_TARGET_DISABLED_OFF = 0;
    /** 検索対象 無効化を除く 有効 */
    public static final int SEARCH_TARGET_DISABLED_ON = 1;
    /** 1ページ 件数*/
    public static final int MAX_ROWCOUNT = 30;


    /**
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Api030Biz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 表示前処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doDsp(Api030ParamModel param) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        Api030Dao api030dao = new Api030Dao(con__);
        int count = api030dao.selectCount(param);
        //現在ページ、スタート行
        int nowPage = param.getApi030page();
        int offset = PageUtil.getRowNumber(nowPage, MAX_ROWCOUNT);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(count, MAX_ROWCOUNT);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, MAX_ROWCOUNT);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        //ページング
        param.setApi030page(nowPage);
        param.setApi030pageSel(nowPage);
        param.setApi030PageLabel(PageUtil.createPageOptions(count, MAX_ROWCOUNT));

        //一覧を取得
        ArrayList<Api030TokenModel> dspList = api030dao.select(param);
        param.setApi030DspList(dspList);

        //無効化選択反映
        String[] delSel = param.getApi030delMulti();
        if (delSel != null && delSel.length > 0) {
            List<String> delSelList =
                    new ArrayList<>(
                            Arrays.asList(delSel));
            for (Api030TokenModel model : dspList) {
                model.setMukoCheck(
                        delSelList.contains(model.getAptToken()));
            }
        }

        //検索項目表示
        //グループコンボを取得
        GroupBiz cmnBiz = new GroupBiz();
        param.setApi030groupLabel(cmnBiz.getGroupCombLabelList(con__, true, gsMsg));
        param.setApi030tokenGroupLabel(cmnBiz.getGroupCombLabelList(con__, false, gsMsg));

        //グループに所属するユーザコンボを取得
        UserBiz uBiz = new UserBiz();
        param.setApi030usrLabel(uBiz.getUserLabelListNoSysUser(
                con__, gsMsg, param.getApi030group()));

        int grpSid = Integer.parseInt(param.getApi030tokenGroupLabel().get(0).getValue());
        List<UsrLabelValueBean> usrList = uBiz.getUserLabelListNoSysUser(con__, gsMsg, grpSid);
        usrList.remove(0);
        param.setApi030tokenUserLabel(usrList);


        //ソートコンボを取得
        List<LabelValueBean> sortKeyList = new ArrayList<>();
        sortKeyList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.user"),
                        String.valueOf(TOKEN_SORTKEY_USER)
                        )
                );
        sortKeyList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.client"),
                        String.valueOf(TOKEN_SORTKEY_CLIENT)
                        )
                );
        sortKeyList.add(
                new LabelValueBean(
                        gsMsg.getMessage("api.api030.4"),
                        String.valueOf(TOKEN_SORTKEY_ADATE)
                        )
                );
        sortKeyList.add(
                new LabelValueBean(
                        gsMsg.getMessage("api.api030.5"),
                        String.valueOf(TOKEN_SORTKEY_LDATE)
                        )
                );
        param.setSortLabel(sortKeyList);

    }
    /**
     *
     * <br>[機  能] 検索項目をパラメータに保管する
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     */
    public void doSaveSerch(Api030ParamModel param) {
        param.setApi030cliantCRSv(param.getApi030cliantCR());
        param.setApi030cliantAppSv(param.getApi030cliantApp());
        param.setApi030cliantOtherSv(param.getApi030cliantOther());
        param.setApi030groupSv(param.getApi030group());
        param.setApi030userSv(param.getApi030user());
        param.setApi030sortKeySv(param.getApi030sortKey());
        param.setApi030orderKeySv(param.getApi030orderKey());
        param.setApi030targetDisabledSv(param.getApi030targetDisabled());
        param.setApi030page(1);
    }
    /**
     *
     * <br>[機  能] 無効化処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doInvalidToken(Api030ParamModel param) throws SQLException {
        List<String> mukoList = new ArrayList<>();
        if (StringUtil.isNullZeroString(param.getApi030delete())) {
            if (param.getApi030delMulti() != null) {
                mukoList.addAll(
                        Arrays.asList(param.getApi030delMulti())
                        );
            }
        } else {
            mukoList.add(param.getApi030delete());
        }
        ApiTokenDao dao = new ApiTokenDao(con__);
        dao.doMukou(mukoList);

    }
    /**
     *
     * <br>[機  能] ログ本文を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return ログ
     */
    public String outputLogMessage(Api030ParamModel param) {
        int cnt = 0;
        if (StringUtil.isNullZeroString(param.getApi030delete())) {
            if (param.getApi030delMulti() != null) {
                cnt = param.getApi030delMulti().length;
            }
        } else {
            cnt = 1;
        }
        GsMessage gsMsg = new GsMessage(reqMdl__);
        return String.valueOf(cnt) + gsMsg.getMessage("cmn.number");
    }
    /**
     *
     * <br>[機  能] 初期化処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     */
    public void doInit(Api030ParamModel param) {
        param.setApi030cliantCR(UserAgent.CLIENT_TYPE_CROSSRIDE);
        param.setApi030cliantApp(UserAgent.CLIENT_TYPE_GSMOBILE);
        param.setApi030cliantOther(UserAgent.CLIENT_TYPE_OTHER);
        param.setApi030group(-1);
        param.setApi030user(-1);

        param.setApi030sortKey(TOKEN_SORTKEY_ADATE);
        param.setApi030orderKey(GSConst.ORDER_KEY_ASC);
        param.setApi030targetDisabled(SEARCH_TARGET_DISABLED_ON);

        doSaveSerch(param);
    }

    /**
     * <br>[機  能] ユーザリスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param param Api030ParamModel
     * @throws SQLException
     */
    public void getUserLabelList(Api030ParamModel param)
            throws SQLException {

        int grpSid = param.getApi030createTokenGroup();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        UserBiz uBiz = new UserBiz();
        List<UsrLabelValueBean> usrList = uBiz.getUserLabelListNoSysUser(con__, gsMsg, grpSid);
        usrList.remove(0);
        param.setApi030tokenUserLabel(usrList);

        return;
    }

    /**
     * <br>[機  能] トークン発行
     * <br>[解  説]
     * <br>[備  考]
     * @param param Api030ParamModel
     * @param req HttpServletRequest
     * @return token文字列
     * @throws SQLException
     */
    public String getToken(Api030ParamModel param, HttpServletRequest req)
            throws SQLException {

        ApiConfBiz confBiz = new ApiConfBiz();
        ApiTokenBiz tokenBiz = new ApiTokenBiz(con__, reqMdl__);

        CmnUsrmDao cuDao = new CmnUsrmDao(con__);
        CmnUsrmModel cuMdl = cuDao.select(param.getApi030createTokenUser());
        BaseUserModel buMdl = new BaseUserModel();
        buMdl.setLgid(cuMdl.getUsrLgid());
        buMdl.setUsrsid(cuMdl.getUsrSid());

        ApiTokenModel tokenMdl = tokenBiz.saveToken(req, buMdl, confBiz.getConf(con__));

        return tokenMdl.getAptToken();
    }
}
