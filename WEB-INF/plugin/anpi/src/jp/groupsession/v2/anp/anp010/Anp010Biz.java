package jp.groupsession.v2.anp.anp010;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp010.dao.Anp010Dao;
import jp.groupsession.v2.anp.anp010.model.Anp010SearchModel;
import jp.groupsession.v2.anp.anp010.model.Anp010SenderModel;
import jp.groupsession.v2.anp.dao.AnpAdmConfDao;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.dao.AnpiCommonDao;
import jp.groupsession.v2.anp.model.AnpAdmConfModel;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpJdataModel;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] 安否状況一覧画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp010Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp010Model アクションフォーム
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @return 進行中の安否確認がある場合、true
     * @throws Exception 実行例外
     */
    public boolean setInitData(Anp010ParamModel anp010Model, RequestModel reqMdl, Connection con)
                throws Exception {

        boolean exist = false;

        //管理者権限確認
        if (AnpiCommonBiz.isGsAnpiAdmin(reqMdl, con)) {
            anp010Model.setAnp010KanriFlg(1);
        }

        //配信中の安否データを取得
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hdata = hDao.selectInHaisin();
        String anpiSid = "";

        if (hdata != null) {
            anpiSid = String.valueOf(hdata.getAphSid());
            //※表示中の安否確認データと最新の安否データが違う場合、1ページ目を再表示
            if (!NullDefault.getString(anp010Model.getAnpiSid(), "").equals(anpiSid)) {
                anp010Model.setAnp010NowPage(1);
            }

            __setAnpiData(anp010Model, reqMdl, con, hdata.getAphSid());

            //訓練モードフラグの設定
            anp010Model.setAnp010KnrenFlg(hdata.getAphKnrenFlg());

            //配信先ユーザ全削除フラグ
            Anp010Dao dao = new Anp010Dao(con);
            int delFlg = dao.checkAllDellFlg(hdata.getAphSid());
            anp010Model.setAnp010AllDeleteFlg(delFlg);

            exist = true;
        } else {
            anp010Model.setAnp010KnrenFlg(GSConstAnpi.DSP_FLG_NOT);
        }

        //安否確認管理者一覧情報を設定する
        __setAdminUsrList(anp010Model, con);

        anp010Model.setAnpiSid(anpiSid);

        return exist;
    }

    /**
     * <br>[機  能] 確認完了時のメッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param anpiSid 安否確認SID
     * @return メッセージ文字列
     * @throws Exception 実行例外
     */
    public String getFinishMessage(RequestModel reqMdl, Connection con, String anpiSid)
                throws Exception {

        String msg = "";
        GsMessage gsmsg = new GsMessage(reqMdl);

        if (!ValidateUtil.isNumber(anpiSid)) {
            return msg;
        }

        AnpiCommonDao adao = new AnpiCommonDao(con, reqMdl);
        AnpStateModel state = adao.getStateInfo(Integer.valueOf(anpiSid));
        if (state != null) {
            gsmsg.getMessage("anp.reply.state");
            msg = gsmsg.getMessage("anp.date.send") + "："
            + NullDefault.getString(state.getHaisinDate(), "") + "<br>"
            + gsmsg.getMessage("anp.reply.state") + "："
            + NullDefault.getString(state.getReplyState(), "-");
        }

        return msg;
    }

    /**
     * <br>[機  能] 配信中データを完了にする。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return 配信中の安否確認タイトル
     */
    public String finishHaisin(RequestModel reqMdl, Connection con)
                throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        GsMessage gsMsg = new GsMessage(reqMdl);
        AnpHdataDao dao = new AnpHdataDao(con);
        AnpHdataModel anpMdl = dao.selectInHaisin();
        String ret;
        if (anpMdl.getAphKnrenFlg() == GSConstAnpi.KNREN_MODE_ON) {
            ret = "【 " + gsMsg.getMessage("anp.knmode") + " 】" + anpMdl.getAphSubject();
        } else {
            ret = anpMdl.getAphSubject();
        }
        dao.updateFinish(sessionUsrSid);

        return ret;
    }

    /**
     * <br>[機  能] 安否配信情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp010Model パラメータモデル
     * @param con DBコネクション
     * @param reqMdl リクエストモデル
     * @param anpiSid 安否確認SID
     * @throws Exception 実行例外
     */
    private void __setAnpiData(
            Anp010ParamModel anp010Model, RequestModel reqMdl, Connection con, int anpiSid)
                throws Exception {

        log__.debug("現在進行中の配信あり");
        //セッションユーザ情報
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        AnpiCommonDao anpidao = new AnpiCommonDao(con, reqMdl);
        Anp010Dao dao = new Anp010Dao(con);

        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //全て表示区分(コンボボックス)
        boolean allGroupUserFlg = true;

        //表示グループリストを取得
        List<AnpLabelValueModel> gpLabel =
                anpiBiz.getGroupLabel(reqMdl, con, sessionUsrSid, allGroupUserFlg);
        anp010Model.setAnp010GroupLabel(gpLabel);

        //グループリストに初期表示するデフォルトグループを取得
        String dspGpSidStr = anp010Model.getAnp010SelectGroupSid();
        if (StringUtil.isNullZeroString(dspGpSidStr)) {
            log__.debug("個人設定デフォルトグループ取得");
            dspGpSidStr = anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg);
        }

        dspGpSidStr = anpiBiz.getEnableSelectGroup(gpLabel, dspGpSidStr,
                anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg));

        anp010Model.setAnp010SelectGroupSid(dspGpSidStr);

        //現在の状況をセット
        AnpStateModel state = anpidao.getStateInfo(anpiSid);
        anp010Model.setAnp010State(state);

        //送信者一覧の取得条件をセット
        Anp010SearchModel joken = new Anp010SearchModel();
        joken.setAnpiSid(anpiSid);
        joken.setUsrSid(sessionUsrSid);
        joken.setGpSid(anp010Model.getAnp010SelectGroupSid());

        joken.setSearchKbn(anp010Model.getAnp010SearchKbn());
        joken.setSendKbn(anp010Model.getAnp010SvSearchSndKbn());
        joken.setAnswerKbn(anp010Model.getAnp010SvSearchAnsKbn());
        joken.setAnpKbn(anp010Model.getAnp010SvSearchAnpKbn());
        joken.setPlaceKbn(anp010Model.getAnp010SvSearchPlcKbn());
        joken.setSyusyaKbn(anp010Model.getAnp010SvSearchSyuKbn());

        //送信者一覧のページ内容をセット
        int limit = priConf.getAppListCount();
        int maxCount = dao.getListCount(joken);

        int nowPage = anp010Model.getAnp010NowPage();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }

        anp010Model.setAnp010NowPage(nowPage);
        anp010Model.setAnp010DspPage1(nowPage);
        anp010Model.setAnp010DspPage2(nowPage);
        anp010Model.setAnp010PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //送信者一覧をセット
        List<Anp010SenderModel> list = dao.getListInfo(
                                            joken,
                                            anp010Model.getAnp010SortKeyIndex(),
                                            anp010Model.getAnp010OrderKey(),
                                            start,
                                            limit);
        anp010Model.setAnp010List(list);


        //セッションユーザの安否確認状況をセット
        AnpJdataDao jDao = new AnpJdataDao(con);
        AnpJdataModel jMdl = jDao.select(anpiSid, sessionUsrSid);
        //安否確認 配信対象
        if (jMdl != null) {
            //セッションユーザ安否情報モデル
            Anp010SenderModel sessionUserAnpInfo = new Anp010SenderModel();
            sessionUserAnpInfo.setUsrSid(String.valueOf(sessionUsrSid));
            sessionUserAnpInfo.setJokyoflg(String.valueOf(jMdl.getApdJokyoFlg()));
            sessionUserAnpInfo.setPlaceflg(String.valueOf(jMdl.getApdPlaceFlg()));
            sessionUserAnpInfo.setSyusyaflg(String.valueOf(jMdl.getApdSyusyaFlg()));
            sessionUserAnpInfo.setReplyDate(__getDspDate(jMdl.getApdRdate()));
            sessionUserAnpInfo.setComment(
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(jMdl.getApdComment(), "")));
            anp010Model.setAnp010SessionUserInfo(sessionUserAnpInfo);

            //配信対象フラグのセット
            anp010Model.setAnp010SendFlg(true);

            //回答区分のセット
            if ((jMdl.getApdJokyoFlg() != GSConstAnpi.JOKYO_FLG_UNSET)
                    && (jMdl.getApdPlaceFlg() != GSConstAnpi.PLACE_FLG_UNSET)
                    && (jMdl.getApdSyusyaFlg() != GSConstAnpi.SYUSYA_FLG_UNSET)) {
                anp010Model.setAnp010AnsKbn(GSConstAnpi.ANP_ANS_YES);
            } else {
                anp010Model.setAnp010AnsKbn(GSConstAnpi.ANP_ANS_NO);
            }
        } else {
            //安否確認 配信対象外
            anp010Model.setAnp010SendFlg(false);
        }
    }

    /**
     * <br>[機  能] 安否確認管理者一覧情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp010Model パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setAdminUsrList(Anp010ParamModel anp010Model, Connection con)
            throws SQLException {
        AnpAdmConfDao adao = new AnpAdmConfDao(con);
        List<AnpAdmConfModel> alist = adao.select();
        List<UsrLabelValueBean> adminGrpList = new ArrayList<UsrLabelValueBean>();
        List<UsrLabelValueBean> adminUsrList = new ArrayList<UsrLabelValueBean>();
        if (alist != null && !alist.isEmpty()) {
            GroupDao gDao = new GroupDao(con);
            CmnUsrmInfDao uDao = new CmnUsrmInfDao(con);
            for (AnpAdmConfModel mdl : alist) {
                //グループ
                if (mdl.getGrpSid() != -1) {
                    CmnGroupmModel gMdl = gDao.getGroup(mdl.getGrpSid());
                    if (gMdl != null) {
                        adminGrpList.add(new UsrLabelValueBean(
                                NullDefault.getString(gMdl.getGrpName(), ""),
                                String.valueOf(mdl.getGrpSid())
                                ));
                    }
                }

                //ユーザ
                if (mdl.getUsrSid() != -1) {
                    CmnUsrmInfModel uMdl = uDao.selectUserNameAndJtkbn(mdl.getUsrSid());
                    if (uMdl.getUsrJkbn() == GSConstUser.USER_JTKBN_ACTIVE) {
                        adminUsrList.add(new UsrLabelValueBean(uMdl));
                    }
                }
            }
            List <UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
            ret.addAll(adminGrpList);
            ret.addAll(adminUsrList);
            anp010Model.setAnp010AdmUsrList(ret);
        }
    }


    /**
     * <br>[機  能] 表示する日付の書式を整えて戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param date 対象の日付
     * @return 表示日付
     * @throws SQLException SQL実行例外
     */
    private String __getDspDate(UDate date) throws SQLException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (date == null) {
            return "";
        }
        return dateformat.format(date.toJavaUtilDate());
    }

}