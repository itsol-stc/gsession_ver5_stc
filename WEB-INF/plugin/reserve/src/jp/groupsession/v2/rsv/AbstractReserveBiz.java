package jp.groupsession.v2.rsv;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisKbnDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.ReserveSmlModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvSisKbnModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約プラグインで共通使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractReserveBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public AbstractReserveBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public AbstractReserveBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] セッションユーザModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return セッションユーザModel
     */
    protected BaseUserModel _getSessionUserModel(RequestModel reqMdl) {
        return reqMdl.getSmodel();
    }

    /**
     * <br>[機  能] 管理者フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return true:管理者 false:非管理者
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isAdmin(RequestModel reqMdl, Connection con)
    throws SQLException {

        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        return adminUser;
    }

    /**
     * <br>[機  能] 施設グループの編集が可能か判定する
     * <br>[解  説]
     * <br>[備  考] 全ての施設グループを判定対象とする
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return true:編集可 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isAllGroupEditAuthority(RequestModel reqMdl,
                                                 Connection con)
        throws SQLException {

        log__.debug("施設グループの編集が可能か判定");

        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        //ユーザ区分 = 管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);
        if (adminUser) {
            return true;
        }

        /**
         * 管理者に設定されている施設グループの数と、
         * 「権限設定しない」となっている施設グループの数を取得し、
         * 権限の判定を行う
         */
        ReserveCommonModel rcMdl = __getGroupCnt(usModel.getUsrsid(), con);

        log__.debug("管理者に設定されている施設グループ数 = " + rcMdl.getGroupAdmCnt());
        log__.debug("権限設定しない施設グループ数 = " + rcMdl.getGroupAdmNotSetCnt());

        if (rcMdl.getGroupAdmCnt() > 0 || rcMdl.getGroupAdmNotSetCnt() > 0) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 施設グループの編集が可能か判定する
     * <br>[解  説]
     * <br>[備  考] 特定の施設グループを判定対象とする
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param mode 処理モード 0=新規 1=編集
     * @param grpSid 処理対象の施設グループSID
     * @return true:編集可 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isGroupEditAuthority(RequestModel reqMdl,
                                              Connection con,
                                              String mode,
                                              int grpSid)
        throws SQLException {

        log__.debug("施設グループの編集が可能か判定");

        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        //ユーザ区分 = 管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);
        if (adminUser) {
            return true;
        }

        //管理者ではないので新規追加処理はできない
        if (mode.equals(GSConstReserve.PROC_MODE_SINKI)) {
            return false;
        }

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpMdl = new RsvSisGrpModel();
        grpMdl.setRsgSid(grpSid);
        RsvSisGrpModel ret = dao.select(grpSid);

        if (ret == null) {
            return false;
        }

        //【権限設定をしない】に設定されている = 誰でも編集可
        if (ret.getRsgAdmKbn() == GSConstReserve.RSG_ADM_KBN_NO) {
            return true;
        }

        //対象の施設グループの管理者に設定されているかチェック
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl);
        int cnt = dao.getSelectGroupAdmCnt(grpSid, usrMdl.getUsrsid());

        if (cnt > 0) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 施設の編集が可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param grpSid 処理対象の施設グループSID
     * @return true:編集可 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isSisetuEditAuthority(RequestModel reqMdl,
                                               Connection con,
                                               int grpSid)
        throws SQLException {

        log__.debug("施設の編集が可能か判定");

        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        //ユーザ区分 = 管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        if (adminUser) {
            return true;
        }

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpMdl = new RsvSisGrpModel();
        grpMdl.setRsgSid(grpSid);
        RsvSisGrpModel ret = dao.select(grpSid);

        if (ret == null) {
            return false;
        }

        //【権限設定をしない】に設定されている = 誰でも編集可
        if (ret.getRsgAdmKbn() == GSConstReserve.RSG_ADM_KBN_NO) {
            return true;
        }

        //対象の施設グループの管理者に設定されているかチェック
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl);
        int cnt = dao.getSelectGroupAdmCnt(grpSid, usrMdl.getUsrsid());

        if (cnt > 0) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 施設グループの状態に応じ件数を取得する
     * <br>[解  説]
     * <br>[備  考] 全ての施設グループを対象とし、
     *              管理者の設定されている施設グループ数と
     *              権限設定をしない施設グループ数を取得する
     *
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @return ret 取得結果をセットしたModel
     * @throws SQLException SQL実行時例外
     */
    private ReserveCommonModel __getGroupCnt(int usrSid, Connection con)
        throws SQLException {

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ReserveCommonModel ret = dao.getGroupCnt(usrSid);

        return ret;
    }

    /**
     * <br>[機  能] 施設グループコンボリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param defFlg true:[全て] false:[選択してください]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ret 施設グループコンボリスト
     * @throws SQLException SQL実行時例外
     */
    protected ArrayList<LabelValueBean> _getGroupComboList(boolean defFlg,
            Connection con, RequestModel reqMdl)
        throws SQLException {
        log__.debug("施設グループコンボリストを取得");

        //システム管理者か
        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> ret = null;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (adminUser) {
            //全グループ取得
            ret = dao.selectAllGroupData();
        } else {
            //閲覧可能グループ取得
            ret = dao.getCanReadData(usModel.getUsrsid());
        }



        String lavelStr = "";
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (defFlg) {
            lavelStr = gsMsg.getMessage("cmn.all");
        } else {
            lavelStr = gsMsg.getMessage("cmn.select.plz");
        }

        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisGrpModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsgName(),
                            String.valueOf(mdl.getRsgSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 施設グループコンボリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ret 施設グループコンボリスト
     * @throws SQLException SQL実行時例外
     */
    protected ArrayList<LabelValueBean> _getGroupComboListDef(
            Connection con, RequestModel reqMdl)
        throws SQLException {
        log__.debug("施設グループコンボリストを取得");

        //システム管理者か
        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> ret = null;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (adminUser) {
            //全グループ取得
            ret = dao.selectAllGroupData();
        } else {
            //閲覧可能グループ取得
            ret = dao.getCanReadData(usModel.getUsrsid());
        }


        String lavelStrPlz = "";
        String lavelStrAll = "";
        GsMessage gsMsg = new GsMessage();


        lavelStrPlz = gsMsg.getMessage("cmn.select.plz");
        lavelStrAll = gsMsg.getMessage("cmn.all");

        labelList.add(
                new LabelValueBean(lavelStrPlz,
                        String.valueOf(GSConstReserve.COMBO_PLEASE_CHOICE)));

        labelList.add(
                new LabelValueBean(lavelStrAll,
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisGrpModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsgName(),
                            String.valueOf(mdl.getRsgSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 施設グループコンボリストを取得する
     * <br>[解  説] 閲覧可能な施設グループのみ取得
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ret 施設グループコンボリスト
     * @throws SQLException SQL実行時例外
     */
    protected ArrayList<LabelValueBean> _getGroupComboList2(Connection con,
            RequestModel reqMdl)
        throws SQLException {

        log__.debug("施設グループコンボリストを取得");



        BaseUserModel usModel = _getSessionUserModel(reqMdl);
        //ユーザ区分 = 管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);



        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> ret = null;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (adminUser) {
            //全グループ取得
            ret = dao.selectAllGroupData();
        } else {
            //閲覧可能グループ取得
            ret = dao.getCanReadData(usModel.getUsrsid());
        }

        String lavelStr = "";
        GsMessage gsMsg = new GsMessage(reqMdl);

        //選択してください
        lavelStr = gsMsg.getMessage("cmn.select.plz");
        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_PLEASE_CHOICE)));

        //全て
        lavelStr = gsMsg.getMessage("cmn.all");
        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisGrpModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsgName(),
                            String.valueOf(mdl.getRsgSid())));
        }

        return labelList;
    }
    /**
     * <br>[機  能] 施設グループコンボリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param defFlg true:[全て] false:[選択してください]
     * @param con コネクション
     * @param grpSid グループSID
     * @param reqMdl リクエスト情報
     * @return ret 施設グループコンボリスト
     * @throws SQLException SQL実行時例外
     */
    protected ArrayList<LabelValueBean> _getGroupSubComboList(
            boolean defFlg, Connection con, int grpSid, RequestModel reqMdl)
            throws SQLException {

        log__.debug("施設グループコンボリストを取得");

        RsvSisDataDao dao = new RsvSisDataDao(con);
        ArrayList<RsvSisDataModel> ret = dao.selectGrpSisetuList(grpSid);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        String lavelStr = "";
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (defFlg) {
            lavelStr = gsMsg.getMessage("cmn.all");
        } else {
            lavelStr = gsMsg.getMessage("cmn.select.plz");
        }

        labelList.add(
                new LabelValueBean(lavelStr,
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisDataModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsdName(),
                            String.valueOf(mdl.getRsdSid())));
        }

        return labelList;
    }
    /**
     * <br>[機  能] 施設コンボリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return ret 施設コンボリスト
     * @throws SQLException SQL実行時例外
     */
    protected ArrayList<LabelValueBean> _getSisetuComboList(int grpSid,
            Connection con, RequestModel reqMdl)
        throws SQLException {

        log__.debug("施設コンボリストを取得");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = usModel.getUsrsid(); //セッションユーザSID

        CommonBiz cmnBiz = new CommonBiz();
        RsvSisDataDao dao = new RsvSisDataDao(con);
        ArrayList<RsvSisDataModel> ret = null;
        if (cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE)) {
            //システム管理者ならば全施設を取得
            ret = dao.selectSisetuList(grpSid);
        } else {
            //閲覧権限のある施設を取得
            ret = dao.selectSisetuList(grpSid, usrSid);
        }

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.select.plz"),
                        String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));

        for (RsvSisDataModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRsdName(),
                            String.valueOf(mdl.getRsdSid())));
        }

        return labelList;
    }


    /**
     * <br>[機  能] 施設区分コンボリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @return ret 施設区分コンボリスト
     * @throws SQLException SQL実行時例外
     */
    protected ArrayList<LabelValueBean> _getGroupKbnComboList(Connection con)
        throws SQLException {

        log__.debug("施設区分コンボリストを取得");

        RsvSisKbnDao dao = new RsvSisKbnDao(con);
        ArrayList<RsvSisKbnModel> ret = dao.selectAllGrpKbn();
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        for (RsvSisKbnModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getRskName(),
                            String.valueOf(mdl.getRskSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 施設予約個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return ret 施設予約個人設定Model
     * @throws SQLException SQL実行時例外
     */
    protected RsvUserModel _isRsvUser(RequestModel reqMdl,
                                       Connection con)
        throws SQLException {

        log__.debug("施設予約個人設定を取得");

        BaseUserModel usModel = _getSessionUserModel(reqMdl);

        RsvUserDao dao = new RsvUserDao(con);
        RsvUserModel ret = dao.select(usModel.getUsrsid());

        return ret;
    }

    /**
     * <br>[機  能] 閲覧可能な施設グループか判定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param rsgSid 施設グループSID
     * @return ret 閲覧可能:true 閲覧不可:false
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isReadRsvGrp(Connection con,
            RequestModel reqMdl,
            int rsgSid)
        throws SQLException {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = usModel.getUsrsid(); //セッションユーザSID

        CommonBiz cmnBiz = new CommonBiz();
        if (cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE)) {
            return true;
        }

        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpModel = rsgDao.select(rsgSid);

        //制限方法
        int limitKbn = GSConstReserve.RSV_ACCESS_MODE_FREE;
        if (grpModel != null) {
            limitKbn = grpModel.getRsgAcsLimitKbn();
        }
        if (limitKbn == GSConstReserve.RSV_ACCESS_MODE_FREE) {
            return true;
        }

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> accessList = dao.getCanReadData(usrSid, rsgSid);
        if (accessList != null && accessList.size() > 0) {
            return true;
        }
        return false;

    }

    /**
     * <br>[機  能] 予約可能な施設グループか判定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param rsgSid 施設グループSID
     * @param sessionUsrSid セッションユーザSID
     * @param admFlg 管理者フラグ
     * @return ret 予約可能:true 予約不可:false
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isEditRsvGrp(Connection con,
            int rsgSid, int sessionUsrSid, boolean admFlg)
        throws SQLException {

        if (admFlg) {
            return true;
        }

        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpModel = rsgDao.select(rsgSid);

        //制限方法
        int limitKbn = GSConstReserve.RSV_ACCESS_MODE_FREE;
        if (grpModel != null) {
            limitKbn = grpModel.getRsgAcsLimitKbn();
        }
        if (limitKbn == GSConstReserve.RSV_ACCESS_MODE_FREE) {
            return true;
        }

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> accessList = dao.getCanEditData(sessionUsrSid, rsgSid);
        if (accessList != null && accessList.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * 施設予約全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value) {
        outPutLog(map, req, res, opCode, level, value, null);
    }

    /**
     * 施設予約全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logCode ログコード
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            String logCode) {

        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        GsMessage gsMsg = new GsMessage();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstReserve.PLUGIN_ID_RESERVE);
        logMdl.setLogPluginName(gsMsg.getMessage(req, "cmn.reserve"));
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), req));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (logCode != null) {
            logMdl.setLogCode(logCode);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     *
     * <br>[解  説] 登録に使用する側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で削除矢印ボタンをクリックした場合、
     *              登録に使用する側のコンボで選択中の値(deleteSelectSid)をメンバーリストから削除する。
     *
     * <br>[備  考] 登録に使用する側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param deleteSelectSid 登録に使用する側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 削除済みのメンバーリスト
     */
    public String[] getDeleteMember(String[] deleteSelectSid, String[] memberSid) {

        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }

        if (memberSid == null) {
            memberSid = new String[0];
        }

        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < memberSid.length; i++) {
            boolean setFlg = true;

            for (int j = 0; j < deleteSelectSid.length; j++) {
                if (memberSid[i].equals(deleteSelectSid[j])) {
                    setFlg = false;
                    break;
                }
            }

            if (setFlg) {
                list.add(memberSid[i]);
            }
        }

        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] ショートメールで更新通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param rsvModel 投稿内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @param userSid 登録ユーザSID
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Connection con,
        MlCountMtController cntCon,
        ReserveSmlModel rsvModel,
        String appRootPath,
        PluginConfig pluginConfig,
        RequestModel reqMdl,
        int userSid) throws Exception {

        //投稿者名
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = udao.select(rsvModel.getUserSid());
        String tname = umodel.getUsiSei() + " " + umodel.getUsiMei();

        GsMessage gsMsg = new GsMessage(reqMdl);


        String uDate = "";
        String frDate = "";
        String toDate = "";

        String bodyml = "";
        if (rsvModel.getEditModeFlg() == 0) {

            uDate = UDateUtil.getSlashYYMD(rsvModel.getRsvAdate());
            frDate = UDateUtil.getSlashYYMD(rsvModel.getRsvFrDate())
                   + " "
                   + UDateUtil.getSeparateHMS(rsvModel.getRsvFrDate());
            toDate = UDateUtil.getSlashYYMD(rsvModel.getRsvToDate())
                   + " "
                   + UDateUtil.getSeparateHMS(rsvModel.getRsvToDate());

            //本文
            String tmpPath = getSmlTemplateFilePath(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("RESERVE", rsvModel.getRsvMokuteki());
            map.put("NAIYOU", rsvModel.getRsvNaiyou());
            map.put("UNAME", tname);
            map.put("UDATE", uDate);
            map.put("FRDATE", frDate);
            map.put("TODATE", toDate);
            map.put("URL", rsvModel.getRsvUrl());
            bodyml = StringUtil.merge(tmpBody, map);

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {

                String textMessage = gsMsg.getMessage("cmn.mail.omit");

                bodyml = textMessage + "\r\n\r\n" + bodyml;

                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);

            }
        } else {

            uDate = UDateUtil.getSlashYYMD(rsvModel.getRsvAdate());
            frDate = UDateUtil.getSlashYYMD(rsvModel.getRsvFrDate());
            toDate = UDateUtil.getSlashYYMD(rsvModel.getRsvToDate());
            String fromTime = UDateUtil.getSeparateHMS(rsvModel.getRsvFrDate());
            String toTime = UDateUtil.getSeparateHMS(rsvModel.getRsvToDate());

            //本文
            String tmpPath = getSmlTemplateFilePathEditLoop(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("RESERVE", rsvModel.getRsvMokuteki());
            map.put("NAIYOU", rsvModel.getRsvNaiyou());
            map.put("UNAME", tname);
            map.put("UDATE", uDate);
            map.put("FRDATE", frDate);
            map.put("TODATE", toDate);
            map.put("FRTIME", fromTime);
            map.put("TOTIME", toTime);
            map.put("URL", rsvModel.getRsvUrl());
            bodyml = StringUtil.merge(tmpBody, map);

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {

                String textMessage = gsMsg.getMessage("cmn.mail.omit");

                bodyml = textMessage + "\r\n\r\n" + bodyml;

                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);

            }
        }

        List<Integer>  sidList = new ArrayList<Integer>();
        sidList.add(userSid);

        //ショートメール送信用モデルを作成する。
        //
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(sidList);

        //タイトル
        String title = gsMsg.getMessage("reserve.169");

        title = StringUtil.trimRengeString(title,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(title);

        //本文
        smlModel.setSendBody(bodyml);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl);
        sender.execute();
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/reserve/smail/koushin_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathEditLoop(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/reserve/smail/koushin_tsuuchi_loop.txt");
        return ret;
    }

    /**
     * <br>[機  能] 施設予約情報URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param extendSid 施設予約情報SID
     * @param procMode 処理モードSID
     * @param date 日付
     * @return スレッドURL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createReserveLoopUrl(RequestModel reqMdl,
                            int extendSid,
                            int procMode, String date)
    throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);
            paramUrl += "/" + GSConst.PLUGIN_ID_RESERVE;
            paramUrl += "/rsv111.do";

            paramUrl += "?rsv110ProcMode=" + procMode;
            paramUrl += "&rsv111RsrRsid=" + extendSid;
            paramUrl += "&rsvDspFrom=" + date;
            paramUrl += "&smailSeniFlg=" + true;

            return urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    /**
     * <br>[機  能] 施設区分別情報登録対象かチェックする
     * <br>[解  説] 施設区分が部屋または車かをチェックする
     * <br>[備  考]
     *
     * @param rskSid 施設区分
     * @return true:対象 false:対象外
     */
    protected boolean _isRskKbnRegCheck(int rskSid) {

        if (RsvCommonBiz.isRskKbnRegCheck(rskSid)) {
            return true;
        }
        return false;
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param req リクエスト
     * @return String
     */
    public String getPgName(String id, HttpServletRequest req) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        log__.info("プログラムID==>" + id);

        try {
            GsMessage gsMsg = new GsMessage();
            //施設予約(週間)
            if (id.equals("jp.groupsession.v2.rsv.rsv010.Rsv010Action")) {
                return gsMsg.getMessage(req, "cmn.reserve");
            }
            //施設予約(日間)
            if (id.equals("jp.groupsession.v2.rsv.rsv020.Rsv020Action")) {
                return gsMsg.getMessage(req, "cmn.reserve");
            }
            //施設グループ情報設定
            if (id.equals("jp.groupsession.v2.rsv.rsv050.Rsv050Action")) {
                return gsMsg.getMessage(req, "reserve.rsv050.1");
            }
            //施設グループ登録・編集
            if (id.equals("jp.groupsession.v2.rsv.rsv060.Rsv060Action")) {
                return gsMsg.getMessage(req, "reserve.rsv060.5");
            }
            //施設情報設定
            if (id.equals("jp.groupsession.v2.rsv.rsv080.Rsv080Action")) {
                return gsMsg.getMessage(req, "reserve.rsv080.1");
            }
            //施設利用状況照会
            if (id.equals("jp.groupsession.v2.rsv.rsv100.Rsv100Action")) {
                return gsMsg.getMessage(req, "reserve.rsv100.1");
            }
            //施設予約登録・編集
            if (id.equals("jp.groupsession.v2.rsv.rsv110.Rsv110Action")) {
                return gsMsg.getMessage(req, "reserve.153");
            }
            //施設予約繰り返し登録・編集確認
            if (id.equals("jp.groupsession.v2.rsv.rsv111kn.Rsv111knAction")) {
                return gsMsg.getMessage(req, "reserve.155");
            }
            //管理者設定 自動データ削除設定確認
            if (id.equals("jp.groupsession.v2.rsv.rsv120kn.Rsv120knAction")) {
                return gsMsg.getMessage(req, "cmn.admin.setting")
                        + " "
                        + gsMsg.getMessage(req, "reserve.rsv120kn.1");
            }
            //管理者設定 手動データ削除確認
            if (id.equals("jp.groupsession.v2.rsv.rsv130kn.Rsv130knAction")) {
                return gsMsg.getMessage(req, "cmn.admin.setting")
                        + " "
                        + gsMsg.getMessage(req, "cmn.manual.delete.kn");
            }
            //個人設定 表示設定確認
            if (id.equals("jp.groupsession.v2.rsv.rsv150kn.Rsv150knAction")) {
                return gsMsg.getMessage(req, "cmn.preferences2")
                        + " "
                        + gsMsg.getMessage(req, "cmn.display.settings.kn");
            }
            //施設インポート
            if (id.equals("jp.groupsession.v2.rsv.rsv180.Rsv180Action")) {
                return gsMsg.getMessage(req, "reserve.62");
            }
            //施設インポート確認
            if (id.equals("jp.groupsession.v2.rsv.rsv180kn.Rsv180knAction")) {
                return gsMsg.getMessage(req, "reserve.rsv180kn.1");
            }
            if (id.equals("jp.groupsession.v2.rsv.rsv200kn.Rsv200knAction")) {
                //施設予約一括設定確認
                return gsMsg.getMessage(req, "reserve.src.2");
            }
            //施設予約一括登録確認
            if (id.equals("jp.groupsession.v2.rsv.rsv210.Rsv210Action")) {
                return  gsMsg.getMessage(req, "reserve.rsv210.1");
            }
            //管理者設定 基本設定確認
            if (id.equals("jp.groupsession.v2.rsv.rsv220kn.Rsv220knAction")) {
                return gsMsg.getMessage(req, "cmn.admin.setting")
                        + " "
                        + gsMsg.getMessage(req, "cmn.preferences.kn");
            }
            //個人設定 初期値設定確認
            if (id.equals("jp.groupsession.v2.rsv.rsv230kn.Rsv230knAction")) {
                return gsMsg.getMessage(req, "cmn.preferences2")
                        + " "
                        + gsMsg.getMessage(req, "reserve.src.4");
            }
            //個人設定 メイン表示設定確認
            if (id.equals("jp.groupsession.v2.rsv.rsv240kn.Rsv240knAction")) {
                return gsMsg.getMessage(req, "cmn.preferences2")
                        + " "
                        + gsMsg.getMessage(req, "reserve.rsv240kn.1");
            }
            //個人設定 ショートメール通知設定
            if (id.equals("jp.groupsession.v2.rsv.rsv300.Rsv300Action")) {
                return gsMsg.getMessage(req, "cmn.preferences2")
                        + " "
                        + gsMsg.getMessage(req, "cmn.sml.notification.setting");
            }
            if (id.equals("jp.groupsession.v2.rsv.rsv250.Rsv250Action")) {
                //施設予約インポート
                return gsMsg.getMessage(req, "reserve.37");
            }
            if (id.equals("jp.groupsession.v2.rsv.rsv250kn.Rsv250knAction")) {
                //施設予約インポート確認
                return gsMsg.getMessage(req, "reserve.src.5");
            }
            //グループ・施設一括登録
            if (id.equals("jp.groupsession.v2.rsv.rsv260.Rsv260Action")) {
                return gsMsg.getMessage(req, "reserve.rsv260.1");
            }
            //グループ・施設一括登録確認
            if (id.equals("jp.groupsession.v2.rsv.rsv260kn.Rsv260knAction")) {
                return gsMsg.getMessage(req, "reserve.rsv260kn.1");
            }
            //グループ・施設一括出力
            if (id.equals("jp.groupsession.v2.rsv.rsv270.Rsv270Action")) {
                return gsMsg.getMessage(req, "reserve.output.group.facilities");
            }
            //施設予約 初期値設定確認
            if (id.equals("jp.groupsession.v2.rsv.rsv280kn.Rsv280knAction")) {
                return gsMsg.getMessage(req, "cmn.admin.setting") + " "
                        + gsMsg.getMessage(req, "cmn.default.setting")
                        + gsMsg.getMessage(req, "cmn.check");
            }
            //表示設定
            if (id.equals("jp.groupsession.v2.rsv.rsv290.Rsv290Action")) {
                return gsMsg.getMessage(req, "cmn.admin.setting") + " "
                    + gsMsg.getMessage(req, "cmn.display.settings");
            }
            //管理者設定 ショートメール通知設定
            if (id.equals("jp.groupsession.v2.rsv.rsv320.Rsv320Action")) {
                return gsMsg.getMessage(req, "cmn.admin.setting")
                        + " "
                        + gsMsg.getMessage(req, "cmn.sml.notification.setting");
            }
            //モバイル 施設予約登録編集画面
            if (id.equals("jp.groupsession.v3.mbh.rsv030.MbhRsv030Action")) {
                return gsMsg.getMessage(req, "main.man120.4")
                        + " "
                        + gsMsg.getMessage(req, "reserve.153");
            }

        } catch (Exception e) {
            log__.error("プログラム名称の取得に失敗しました。", e);
        }

      return ret;
    }
    /**
    *
    * <br>[機  能] 選択した値がグループコンボ上にない場合に有効な値を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param list ラベルリスト
    * @param nowSel 選択中ラベルID
    * @param defSel 初期ラベルID
    * @return 有効な選択値
    */
   public String getEnableSelectGroup(List<ExtendedLabelValueModel> list,
           String nowSel, String defSel) {
       boolean nowVar = false;
       boolean defVar = false;
       if (list == null || list.size() <= 0) {
           return "";
       }
       for (ExtendedLabelValueModel label : list) {
           if (label.getValue().equals(nowSel)) {
               nowVar = true;
               break;
           }
           if (label.getValue().equals(defSel)) {
               defVar = true;
           }
       }
       if (nowVar) {
           return nowSel;
       }
       if (defVar) {
           return defSel;
       }
       return list.get(0).getValue();
   }
}