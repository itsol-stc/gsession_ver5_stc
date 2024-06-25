package jp.groupsession.v2.rsv.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisMainDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvMainGrpModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvSisMainModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100searchModel;

/**
 * <br>[機  能] 施設予約 予約状況一覧(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvMainBiz.class);

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req リクエスト
     * @return sessionUsrSid セッションユーザSID
     */
    private int __getSessionUserSid(HttpServletRequest req) {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }

    /**
     * <br>[機  能] 初期表示データをセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(RsvMainForm form,
                             HttpServletRequest req,
                             Connection con,
                             RequestModel reqMdl)
        throws SQLException {

        log__.debug("初期表示データセット");

        int sessionUsrSid = __getSessionUserSid(req);
        //管理施設グループを取得
//        RsvSisAdmDao admDao = new RsvSisAdmDao(con);
//        ArrayList<RsvSisAdmModel> grpList = admDao.select(sessionUsrSid);

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //システム管理者フラグ
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(
                con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        int rsgSid = -1;
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con);
        RsvSisYrkDao rsvDao = new RsvSisYrkDao(con);
        Rsv100searchModel searchMdl = new Rsv100searchModel();
        //検索条件設定
        UDate dspDate = new UDate();
        dspDate.setDate(NullDefault.getString(form.getDspDate(), dspDate.getDateString()));

        UDate toDate = dspDate.cloneUDate();
        toDate.setMaxHhMmSs();
        searchMdl.setRsvTo(toDate);
        searchMdl.setRsvGrp2(0);
        searchMdl.setRsvKeyWord(null);
        searchMdl.setRsvMaxPage(Integer.MAX_VALUE);
        searchMdl.setRsvOrderKey(GSConst.ORDER_KEY_ASC);
        searchMdl.setRsvSelectedKey1(3);
        searchMdl.setRsvSelectedKey1Sort(GSConst.ORDER_KEY_ASC);
        searchMdl.setRsvSelectedKey2(2);
        searchMdl.setRsvSelectedKey2Sort(GSConst.ORDER_KEY_ASC);
        searchMdl.setRsvSearchCondition(0);
        //格納オブジェクト生成
        ArrayList<RsvMainGrpModel> reservList = new ArrayList<RsvMainGrpModel>();
        RsvMainGrpModel mainMdl = null;
        ArrayList<Rsv100SisYrkModel> resultList = null;
        RsvSisGrpModel grpMdl = null;
        String rsgName = "";

        //個人設定より表示する施設グループを取得
        RsvSisMainDao mainDao = new RsvSisMainDao(con);
        List<RsvSisMainModel> mainModelList = mainDao.getCanReadConf(sessionUsrSid);

        for (RsvSisMainModel admMdl : mainModelList) {
            mainMdl = new RsvMainGrpModel();
            rsgSid = admMdl.getRsgSid();
            grpMdl = grpDao.select(rsgSid);
            if (grpMdl == null) {
                continue;
            }

            //予約時間経過表示区分を検索条件に反映させる
            UDate frDate = dspDate.cloneUDate();
            int frHour = frDate.getIntHour();
            int frMinute = frDate.getIntMinute();

            frDate.setZeroHhMmSs();

            if (admMdl.getRsmDspKbn() == GSConstReserve.RSV_OVERTIME_DSP_OFF) {
                frDate.setHour(frHour);
                frDate.setMinute(frMinute);
            }

            log__.debug("*****開始時刻：" + frDate);
            searchMdl.setRsvFrom(frDate);

            rsgName = grpMdl.getRsgName();
            searchMdl.setRsvGrp1(rsgSid);

            resultList = rsvDao.getYrkReferenceList(searchMdl, adminUser, reqMdl);
            log__.debug("予約件数==>" + resultList.size());
            if (resultList.size() > 0) {
                //施設グループ毎の予約状況を格納
                mainMdl.setRsgName(rsgName);
                mainMdl.setSisetuList(resultList);
                reservList.add(mainMdl);
            }
        }

//        for (RsvSisAdmModel admMdl : grpList) {
//            mainMdl = new RsvMainGrpModel();
//            rsgSid = admMdl.getRsgSid();
//            grpMdl = grpDao.select(rsgSid);
//            rsgName = grpMdl.getRsgName();
//            searchMdl.setRsvGrp1(rsgSid);
//            resultList = rsvDao.getYrkReferenceList(searchMdl);
//            log__.debug("予約件数==>" + resultList.size());
//            if (resultList.size() > 0) {
//                //施設グループ毎の予約状況を格納
//                mainMdl.setRsgName(rsgName);
//                mainMdl.setSisetuList(resultList);
//                reservList.add(mainMdl);
//            }
//        }
       /************************************************************************
        *
        * 取得・生成した値をフォームにセットする
        *
        ************************************************************************/
        form.setDspDate(dspDate.getDateString());
        form.setReservList(reservList);
    }
}