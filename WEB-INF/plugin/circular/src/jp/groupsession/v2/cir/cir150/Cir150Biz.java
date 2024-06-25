package jp.groupsession.v2.cir.cir150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 回覧板 アカウントマネージャー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir150Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Cir150ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {
        //1ページ最大30件
        int limit = GSConstCircular.LIMIT_DSP_ACCOUNT;

        if (paramMdl.getCir150popKbn() == 1) {
            //ポップアップ時は10件
            limit = 10;
        }

        //検索結果を取得する
        Cir150SearchModel searchMdl = new Cir150SearchModel();
        searchMdl.setKeyword(paramMdl.getCir150svKeyword());
        searchMdl.setGrpSid(paramMdl.getCir150svGroup());
        searchMdl.setUserSid(paramMdl.getCir150svUser());
        searchMdl.setSortKey(paramMdl.getCir150sortKey());
        searchMdl.setOrder(paramMdl.getCir150order());
        searchMdl.setMaxCount(limit);
        long maxCount = 0;

        //件数カウント
        Cir150Dao dao = new Cir150Dao(con);
        maxCount = dao.recordCount(searchMdl);
        log__.debug("表示件数 = " + maxCount);

        //現在ページ（ページコンボのvalueを設定）
        int nowPage = paramMdl.getCir150pageTop();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        if (maxPageNum > 1) {
            paramMdl.setCir150pageDspFlg(true);
        }
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        searchMdl.setPage(start);

        //ページング
        paramMdl.setCir150pageTop(nowPage);
        paramMdl.setCir150pageBottom(nowPage);
        paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));

        List<Cir150AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);
        paramMdl.setAccountList(accountList);

        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }
        paramMdl.setGroupCombo(groupCombo);

        //ユーザコンボを設定
        List<UsrLabelValueBean> userCombo = new ArrayList<UsrLabelValueBean>();

        UserBiz userBiz = new UserBiz();
        userCombo.addAll(userBiz.getUserLabelListNoSysUser(con, gsMsg, paramMdl.getCir150group()));
        paramMdl.setUserCombo(userCombo);
    }
    /**
     * <br>[機  能] メッセージに表示するアカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSidList アカウントSID
     * @return アカウント名
     * @throws SQLException SQL実行時例外
     */
    public String getMsgAccountTitle(Connection con, String[] accountSidList)
    throws SQLException {

        Cir150Dao wml030Dao = new Cir150Dao(con);
        List<String> titleList = wml030Dao.getAccountNameList(accountSidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx), ""));
        }

        return msgTitle;
    }

    /**
     * <br>[機  能] アカウントの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccount(Connection con, Cir150ParamModel paramMdl, int userSid)
    throws SQLException {

        log__.info("アカウント削除開始");

        boolean commit = false;
        try {
            CirAccountDao accountDao = new CirAccountDao(con);
            accountDao.updateJkbn(
                    paramMdl.getCir150selectAcount(), GSConstCircular.CAC_JKBN_DELETE);
            // ラベル情報削除
            CirLabelDao labelDao = new CirLabelDao(con);
            labelDao.deleteAccountLabel(paramMdl.getCir150selectAcount());
            // 回覧板送信ラベル削除
            CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
            labelInfDao.deleteAccountLabel(paramMdl.getCir150selectAcount());
            // 回覧板受信ラベル削除
            CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
            labelViewDao.deleteAccountLabel(paramMdl.getCir150selectAcount());
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.info("アカウントの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        log__.info("アカウント削除終了");
    }

    /**
     * <br>[機  能] アカウント削除可能チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return jsonData JSONObject
     */
    public ActionErrors checkCanDelAct(Cir150ParamModel paramMdl,
                             RequestModel reqMdl,
                             Connection con)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        CirAccountDao sad = new CirAccountDao(con);
        CirAccountModel actMdl = null;

        for (String sid : paramMdl.getCir150selectAcount()) {

            if (ValidateUtil.isNumber(sid)) {
                actMdl = sad.select(Integer.valueOf(sid));

                if (actMdl != null && actMdl.getUsrSid() > 0) {
                    ActionMessage msg = new ActionMessage(
                            "error.common.no.delete",
                            StringUtilHtml.transToHTmlPlusAmparsant(actMdl.getCacName()));

                    StrutsUtil.addMessage(errors, msg, String.valueOf(actMdl.getCacSid()));
                }
            }

        }
        return errors;
    }

    /**
     * <br>[機  能] ログ用メッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sidList SIDリスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return jsonData JSONObject
     */
    public String getLogMessage(String[] sidList,
                             Connection con)
        throws SQLException {

        String msg = "";
        CirAccountDao accountDao = new CirAccountDao(con);
        List<CirAccountModel> accountList = accountDao.getAccountList(
                sidList);
        boolean multiFlg = false;
        for (CirAccountModel mdl : accountList) {
            if (multiFlg) {
                msg += "\r\n";
            } else {
                multiFlg = true;
            }
            msg += mdl.getCacName();
        }

        return msg;
    }
}
