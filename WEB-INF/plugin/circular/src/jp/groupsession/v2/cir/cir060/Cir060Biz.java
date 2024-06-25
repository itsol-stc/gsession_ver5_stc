package jp.groupsession.v2.cir.cir060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirSearchModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.model.CircularUsrModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 回覧板 詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir060Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cir060ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;

        //アカウントを取得
        if (paramMdl.getCirViewAccount() <= 0) {
            //デフォルトのアカウントを取得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        } else {
            //選択されたアカウントを取得
            cacMdl = cacDao.select(paramMdl.getCirViewAccount());
        }

        if (cacMdl != null) {
            //アカウント
            paramMdl.setCirViewAccount(cacMdl.getCacSid());
            //アカウント名
            paramMdl.setCirViewAccountName(cacMdl.getCacName());

            if (cacMdl.getCacTheme() !=  GSConstCircular.CAC_THEME_NOSET) {
                CmnThemeDao dao = new CmnThemeDao(con);
                CmnThemeModel model = dao.select(cacMdl.getCacTheme());
            if (model.getCtmPath() != null) {
                //アカウントテーマ
                paramMdl.setCir010AccountTheme(model.getCtmPath());
                }
            }

            paramMdl.setCirViewAccountUko(cacMdl.getUsrUkoFlg());

        }

        //コンボの値セット
        __setCombo(paramMdl, con, reqMdl);

        //回覧先名称
        if (paramMdl.getCir060selUserSid() != null
                && paramMdl.getCir060selUserSid().length > 0) {
            CirCommonBiz cirBiz = new CirCommonBiz();
            paramMdl.setMemberList(cirBiz.getAccountList(con, paramMdl.getCir060selUserSid()));
        }

        //WEB検索のキーワードとHTML表示用のキーワードを設定する。
        String key = paramMdl.getCir010svSearchWord();
        String webSearchWord = "";
        String htmlSearchWord = "";
        if (!StringUtil.isNullZeroStringSpace(key)) {
            webSearchWord = StringUtil.toSingleCortationEscape(key);
            htmlSearchWord = StringUtilHtml.transToHTmlPlusAmparsant(key);
        }
        paramMdl.setCir060WebSearchWord(webSearchWord);
        paramMdl.setCir060HtmlSearchWord(htmlSearchWord);

    }

    /**
     * <br>[機  能] 検索結果を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors setSearchData(Cir060ParamModel paramMdl, Connection con, int userSid,
                                    RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();

        //検索フラグがtrue(検索OK)の場合のみ、検索を行う
        if (!paramMdl.isCir060searchFlg()) {
            return errors;
        }

        //回覧板種別(セーブ)
        int svSyubetsu = paramMdl.getCir060svSyubetsu();

        //回覧板個人設定から最大表示件数を取得する
        CirCommonBiz ccBiz = new CirCommonBiz();
        int limit = ccBiz.getCountLimit(userSid, con);

        //検索用Modelを作成
        CirSearchModel bean = __getSearchModel(
                paramMdl, paramMdl.getCirViewAccount(), limit, userSid, con);

        //回覧板リスト
        List < CircularDspModel > cList = null;

        if (svSyubetsu == Integer.parseInt(GSConstCircular.MODE_JUSIN)) {
            //受信
            cList = __getJusinList(paramMdl, con, bean);

        } else if (svSyubetsu == Integer.parseInt(GSConstCircular.MODE_SOUSIN)) {
            //送信済
            cList = __getSousinList(paramMdl, con, bean);

        } else if (svSyubetsu == Integer.parseInt(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            cList = __getGomiList(paramMdl, con, bean);
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textCir = gsMsg.getMessage("cir.5");


        //該当データなしの場合エラーメッセージを表示
        if (cList.size() < 1) {
            ActionMessage msg = new ActionMessage(
                    "search.data.notfound", textCir);
            StrutsUtil.addMessage(errors, msg, "cir");
            return errors;
        }


        //表示用に加工
        for (int i = 0; i < cList.size(); i++) {
            CircularDspModel clMdl = cList.get(i);
            String strAdate =
                UDateUtil.getSlashYYMD(clMdl.getCifAdate())
                + "  "
                + UDateUtil.getSeparateHMS(clMdl.getCifAdate());
            clMdl.setDspCifAdate(strAdate);
        }

        //一覧をフォームへセット
        paramMdl.setCircularList(cList);
        return errors;
    }

    /**
     * <br>[機  能] 回覧情報(受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param bean 検索用Model
     * @return List in CircularDspModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getJusinList(
        Cir060ParamModel paramMdl,
        Connection con,
        CirSearchModel bean) throws SQLException {

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getJusinCount(bean);
        log__.debug("件数 = " + maxCount);

        int limit = bean.getLimit();
        //現在ページ
        int nowPage = paramMdl.getCir060pageNum1();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setCir060pageNum1(nowPage);
        paramMdl.setCir060pageNum2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getJusinList(bean);
    }

    /**
     * <br>[機  能] 回覧情報(送信済み)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param bean 検索用Model
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getSousinList(
        Cir060ParamModel paramMdl,
        Connection con,
        CirSearchModel bean) throws SQLException {

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getSousinCount(bean);
        log__.debug("件数 = " + maxCount);

        int limit = bean.getLimit();
        //現在ページ
        int nowPage = paramMdl.getCir060pageNum1();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setCir060pageNum1(nowPage);
        paramMdl.setCir060pageNum2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getSousinList(bean);
    }

    /**
     * <br>[機  能] 回覧情報(ゴミ箱)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param bean 検索用Model
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getGomiList(
        Cir060ParamModel paramMdl,
        Connection con,
        CirSearchModel bean) throws SQLException {

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getGomiCount(bean);
        log__.debug("件数 = " + maxCount);

        int limit = bean.getLimit();
        //現在ページ
        int nowPage = paramMdl.getCir060pageNum1();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setCir060pageNum1(nowPage);
        paramMdl.setCir060pageNum2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getGomiList(bean);
    }

    /**
     * <br>[機  能] 検索用Modelを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cacSid アカウントSID
     * @param limit 最大表示件数
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @return CirSearchModel
     * @throws SQLException sql実行時例外
     */
    private CirSearchModel __getSearchModel(
            Cir060ParamModel paramMdl,
            int cacSid,
            int limit,
            int userSid,
            Connection con) throws SQLException {

        //セーブ 検索キーワード
        String searchWord = NullDefault.getString(paramMdl.getCir010svSearchWord(), "");
        //セーブ 検索対象
        String[] targets = paramMdl.getCir060svSearchTarget();
        boolean targetTitle = false;
        boolean targetBody = false;
        if (targets != null && targets.length > 0) {
            for (String target : targets) {
                if (String.valueOf(GSConstCircular.SEARCH_TARGET_TITLE).equals(target)) {
                    targetTitle = true;
                }
                if (String.valueOf(GSConstCircular.SEARCH_TARGET_BODY).equals(target)) {
                    targetBody = true;
                }
            }
        }

        CommonBiz cBiz = new CommonBiz();
        CirCommonBiz cirBiz = new CirCommonBiz();



        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);
        bean.setLimit(limit);
        bean.setOrderKey(paramMdl.getCir060svOrder1());
        bean.setSortKey(paramMdl.getCir060svSort1());
        bean.setOrderKey2(paramMdl.getCir060svOrder2());
        bean.setSortKey2(paramMdl.getCir060svSort2());
        bean.setGroupSid(paramMdl.getCir060svGroupSid());
        bean.setSessionUserSid(userSid);
        bean.setKeyWord(cBiz.setKeyword(searchWord));
        bean.setWordKbn(paramMdl.getCir060svWordKbn());
        bean.setTargetTitle(targetTitle);
        bean.setTargetBody(targetBody);

        //発信者アカウントSID取得
        int hasshinSid = -1;
        if (paramMdl.getCir060svUserSid() > 0) {

            String selUsrSid = String.valueOf(paramMdl.getCir060svUserSid());

            if (isCirAccount(paramMdl.getCir060svGroupSid())) {
                selUsrSid = GSConstCircular.CIR_ACCOUNT_STR + selUsrSid;
            }

            String[] accountSids = null;
            accountSids =
                cirBiz.getAccountSidFromUsr(
                        con, new String[] {selUsrSid});
            if (accountSids != null && accountSids.length > 0) {
                if (GSValidateUtil.isNumber(accountSids[0])) {
                    hasshinSid = Integer.parseInt(accountSids[0]);
                }
            }
            bean.setHassinSid(hasshinSid);
        }

        //指定ユーザ
        if (paramMdl.getCir060svSelUserSid() != null
                && paramMdl.getCir060svSelUserSid().length > 0) {
            bean.setKairansakiSid(
                    cirBiz.getAccountSidFromUsr(con, paramMdl.getCir060svSelUserSid()));
        }

        return bean;
    }

    /**
     * <br>[機  能] コンボの値セット
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    private void __setCombo(Cir060ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        String groupSid = paramMdl.getCir060groupSid();

        if (StringUtil.isNullZeroString(groupSid)) {
            GroupBiz grpBiz = new GroupBiz();
            groupSid = String.valueOf(grpBiz.getDefaultGroupSid(
                                     reqMdl.getSmodel().getUsrsid(), con));
            paramMdl.setCir060groupSid(groupSid);
        }

        //発信者グループを取得
        GroupBiz gpBiz = new GroupBiz();

        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(reqMdl.getSmodel().getUsrsid());

        List<CmnLabelValueModel> dspGrpList = new ArrayList<CmnLabelValueModel>();

        //選択して下さい
        dspGrpList.add(
                new CmnLabelValueModel(
                 gsMsg.getMessage("cmn.select.plz"), String.valueOf(GSConstCommon.NUM_INIT), "0"));

        //代表アカウント
        dspGrpList.add(
                new CmnLabelValueModel("代表アカウント", GSConstCircular.CIR_ACCOUNT_STR, "2"));

        //マイグループリストをセット
        for (CmnMyGroupModel cmgMdl : cmgList) {
            dspGrpList.add(
                    new CmnLabelValueModel(
                            cmgMdl.getMgpName(), "M" + String.valueOf(cmgMdl.getMgpSid()), "1"));
        }


        List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con, false, gsMsg);
        for (LabelValueBean bean : grpLabelList) {
            dspGrpList.add(
                    new CmnLabelValueModel(bean.getLabel(), bean.getValue(), "0"));

        }

        paramMdl.setGroupLabel(dspGrpList);

        //発信者を取得
//        UserBiz uBiz = new UserBiz();
//        paramMdl.setUserLabel(uBiz.getUserLabelList(con, gsMsg, paramMdl.getCir060groupSid()));
        List<CircularUsrModel> cirUsrList = new ArrayList<CircularUsrModel>();

        CircularUsrModel topMdl = new CircularUsrModel();
        topMdl.setUsiSei(gsMsg.getMessage("cmn.select.plz"));
        topMdl.setUsrSid(GSConstCommon.NUM_INIT);
        cirUsrList.add(topMdl);

        if (isCirAccount(groupSid)) {
            //代表アカウントを取得
            CircularDao cirDao = new CircularDao(con);
            cirUsrList.addAll(cirDao.selectCirAccount());

        } else {

            //グループSIDから所属ユーザ一覧を作成
            int grpSid = getDspGroupSid(groupSid);
            ArrayList<Integer> users = new ArrayList<Integer>();

            if (isMyGroupSid(groupSid)) {
                //マイグループから作成
                CmnMyGroupMsDao mgmsDao = new CmnMyGroupMsDao(con);
                users = mgmsDao.selectMyGroupUsers(
                                      reqMdl.getSmodel().getUsrsid(), grpSid);

            } else {
                //通常グループから作成
                CmnBelongmDao cmnbDao = new CmnBelongmDao(con);
                users = cmnbDao.selectBelongUserSid(getDspGroupSid(groupSid));
            }

            //回覧板プラグインを使用していないユーザを除外する。
            CommonBiz cmnBiz = new CommonBiz();
            ArrayList<Integer> usrList
                           = (ArrayList<Integer>) cmnBiz.getCanUseCircularUser(con, users);

            //システムメールとGS管理者を除外する
            ArrayList<Integer> usrDspList = new ArrayList<Integer>();
            for (Integer usid : usrList) {
                if (usid != GSConstUser.SID_ADMIN && usid != GSConstUser.SID_SYSTEM_MAIL) {
                    usrDspList.add(usid);
                }
            }
            //ユーザ情報を取得
            List<CmnUsrmInfModel> uList = null;
            if (users != null && users.size() > 0) {
                //ユーザ情報一覧を作成
                UserBiz usrBiz = new UserBiz();
                uList = usrBiz.getUserList(con, usrDspList);

                try {

                    if (uList != null && !uList.isEmpty()) {
                        for (CmnUsrmInfModel usrMdl : uList) {
                            CircularUsrModel cirUsrMdl = new CircularUsrModel();
                            BeanUtils.copyProperties(cirUsrMdl, usrMdl);
                            cirUsrList.add(cirUsrMdl);
                        }
                    }

                } catch (Exception e) {
                    log__.error("ユーザリスト(選択用)の作成に失敗");
                }
            }
        }

        paramMdl.setUserLabel(cirUsrList);

        String textSortTitle = gsMsg.getMessage("cmn.title");
        String textSortUser = gsMsg.getMessage("cir.2");
        String textSortDate = gsMsg.getMessage("cmn.date");

        //ソートキーを取得
        List < LabelValueBean > sortList = new ArrayList<LabelValueBean>();
        sortList.add(new LabelValueBean(
                textSortTitle, String.valueOf(GSConstCircular.SORT_TITLE)));
        sortList.add(new LabelValueBean(
                textSortUser, String.valueOf(GSConstCircular.SORT_USER)));
        sortList.add(new LabelValueBean(
                textSortDate, String.valueOf(GSConstCircular.SORT_DATE)));
        paramMdl.setSortLabel(sortList);
    }

    /**
     * パラメータ.グループコンボ値が代表アカウントかを判定する
     * <br>[機  能]先頭文字に"sac"が有る場合は代表アカウント
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isCirAccount(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstCircular.CIR_ACCOUNT_STR);

        // 先頭文字に"cac"が有る場合は代表アカウント
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * パラメータ.グループコンボ値からグループSID又はマイグループSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (gpSid == null) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     * パラメータ.グループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("M");

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }
}
