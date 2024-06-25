package jp.groupsession.v2.anp.anp160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpPriConfDao;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.usr040.ShainSearchModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メッセージ配信確認 送信者一覧(ポップアップ画面)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp160Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp160Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp160Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param anpSid 安否SID
     * @param grpSid グループSID
     * @param procMode プロセスモード 1:新規画面からの遷移
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Anp160ParamModel anp160Model,
            RequestModel reqMdl,
            Connection con,
            int anpSid,
            int grpSid,
            String procMode) throws SQLException {

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //配信一覧のページ内容をセット
        int limit = priConf.getAppListCount();
        int maxCount = 0;
        Anp160Dao dao = new Anp160Dao(con);
        UserSearchDao userDao = new UserSearchDao(con);
        if (procMode.equals(GSConstAnpi.MSG_HAISIN_MODE_NEW)
                || procMode.equals(GSConstAnpi.MSG_HAISIN_MODE_COPY)) {
            //新規送信時 グループ所属している全ユーザ対象
            maxCount = userDao.getBelongUserCount(grpSid, null);
        } else {
            //再送信時 送信データがある全ユーザ対象
            maxCount = dao.getReSendBelongUserListCount(anpSid, grpSid, procMode);
        }

        int nowPage = anp160Model.getAnp160NowPage();
        int start = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }
        anp160Model.setAnp160NowPage(nowPage);
        anp160Model.setAnp160DspPage1(nowPage);
        anp160Model.setAnp160DspPage2(nowPage);
        anp160Model.setAnp160PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //グループに所属しているユーザの情報を取得する
        ArrayList<CmnUsrmInfModel> belongList = null;
        if (procMode.equals(GSConstAnpi.MSG_HAISIN_MODE_NEW)
                || procMode.equals(GSConstAnpi.MSG_HAISIN_MODE_COPY)) {
            //新規送信時 グループ所属している全ユーザ対象
            belongList = userDao.getBelongUserSearchList(
                    grpSid, null, false, __getSearchModel(con), start, limit);
        } else {
            //再送信時 送信データがあるユーザ対象
            belongList = __getBelongAllUserList(con, anpSid, grpSid, start, limit, procMode);
        }

        List<Anp160DspModel> dispList = new ArrayList<Anp160DspModel>();
        Anp160DspModel dspMdl = null;
        AnpPriConfDao confDao = new AnpPriConfDao(con);
        AnpPriConfModel pMdl = null;
        //ユーザ情報からSIDのリストを生成
        for (CmnUsrmInfModel usrMdl : belongList) {
            dspMdl = new Anp160DspModel();
            pMdl = confDao.select(usrMdl.getUsrSid());
            dspMdl.setAnp160UsrSid(usrMdl.getUsrSid());
            dspMdl.setAnp160Name(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
            dspMdl.setAnp160usrUkoFlg(usrMdl.getUsrUkoFlg());
            if (pMdl != null) {
                if ((pMdl.getAppMailadr() != null) && (pMdl.getAppMailadr().length() != 0)) {
                    dspMdl.setAnp160MailFlg(Anp160ParamModel.MAIL_SET_YES);
                } else {
                    dspMdl.setAnp160MailFlg(Anp160ParamModel.MAIL_SET_NO);
                    anp160Model.setAnp160NosetMailFlg(Anp160ParamModel.MAIL_NOSET_USER_YES);
                }
            }
            dispList.add(dspMdl);
        }
        anp160Model.setAnp160DspSenderList(dispList);

        //グループ情報を取得
        CmnGroupmDao grpmDao = new CmnGroupmDao(con);
        CmnGroupmModel grpmMdl =  grpmDao.select(grpSid);
        anp160Model.setAnp160DispGrpName(grpmMdl.getGrpName());

    }


    /**
     * <br>[機  能] ソートモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 検索モデル
     * @throws SQLException 実行例外
     */
    private ShainSearchModel __getSearchModel(Connection con) throws SQLException {

        ShainSearchModel ret = new ShainSearchModel();

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        int sortKey1 = UserBiz.getSortKey(sortMdl.getCscUserSkey1());
        int order1 = sortMdl.getCscUserOrder1();
        int sortKey2 = UserBiz.getSortKey(sortMdl.getCscUserSkey2());
        int order2 = sortMdl.getCscUserOrder2();

        ret.setSortKey(sortKey1);
        ret.setSortOrder(order1);
        ret.setSortKey2(sortKey2);
        ret.setSortOrder2(order2);

        return ret;
    }


    /**
     * <br>[機  能] 再配信確認画面に表示するグループのユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param anpSid 安否SID
     * @param grpSid グループSID
     * @param start 開始位置
     * @param limit 表示件数
     * @param procMode プロセスモード
     * @throws SQLException SQL実行エラー
     * @return ユーザリスト
     */
    private ArrayList<CmnUsrmInfModel> __getBelongAllUserList(
            Connection con, int anpSid, int grpSid, int start, int limit, String procMode)
            throws SQLException {
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        int sortKey1 = UserBiz.getSortKey(sortMdl.getCscUserSkey1());
        int order1 = sortMdl.getCscUserOrder1();
        int sortKey2 = UserBiz.getSortKey(sortMdl.getCscUserSkey2());
        int order2 = sortMdl.getCscUserOrder2();

        Anp160Dao dao = new Anp160Dao(con);
        ret = dao.getReSendBelongUserList(
                anpSid, grpSid, sortKey1, order1, sortKey2, order2, start, limit, procMode);
        return ret;
    }
}