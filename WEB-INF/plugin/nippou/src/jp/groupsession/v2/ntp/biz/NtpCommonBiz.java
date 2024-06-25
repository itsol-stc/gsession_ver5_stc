package jp.groupsession.v2.ntp.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupMsModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NtpAdmConfDao;
import jp.groupsession.v2.ntp.dao.NtpAnMemberDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenPermitDao;
import jp.groupsession.v2.ntp.dao.NtpBinDao;
import jp.groupsession.v2.ntp.dao.NtpCheckDao;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
import jp.groupsession.v2.ntp.dao.NtpPriConfDao;
import jp.groupsession.v2.ntp.dao.NtpShohinCategoryDao;
import jp.groupsession.v2.ntp.dao.NtpSmlMemberDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessPermitDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessTargetDao;
import jp.groupsession.v2.ntp.dao.NtpTemplateDao;
import jp.groupsession.v2.ntp.dao.NtpTmpMemberDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NippouExSearchModel;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpCommentModel;
import jp.groupsession.v2.ntp.model.NtpContactModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.model.NtpProcessModel;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.model.NtpTmpMemberModel;
import jp.groupsession.v2.ntp.ntp040.Ntp040Dao;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040AddressModel;
import jp.groupsession.v2.ntp.ntp140.Ntp140Dao;
import jp.groupsession.v2.ntp.ntp150.Ntp150ProcessDao;
import jp.groupsession.v2.ntp.ntp190.Ntp190Dao;
import jp.groupsession.v2.ntp.ntp230.Ntp230Dao;
import jp.groupsession.v2.ntp.ntp240.Ntp240Dao;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpCommonBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpCommonBiz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public NtpCommonBiz() {
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public NtpCommonBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 日報個人設定NtpPriConfModelを取得します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return 日報個人設定NtpPriConfModel
     * @throws SQLException SQL実行エラー
     */
    public NtpPriConfModel getNtpPriConfModel(Connection con, int usrSid) throws SQLException {
        //
        NtpPriConfDao dao = new NtpPriConfDao(con);
        NtpPriConfModel pconf = dao.select(usrSid);
        boolean commitFlg = false;
        if (pconf == null) {
            con.setAutoCommit(false);
            pconf = getDefaulPriConfiModel(usrSid, con);
            try {
                dao.insert(pconf);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("個人設定の取得に失敗しました。" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        log__.debug("pconf = " + pconf.toCsvString());
        return pconf;
    }

    /**
     * <br>[機  能] 日報個人設定のデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param usrSid ユーザSID
     * @param con DBコネクション
     * @return 日報個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public NtpPriConfModel getDefaulPriConfiModel(int usrSid, Connection con) throws SQLException {
        NtpPriConfModel confBean = new NtpPriConfModel();
        //ユーザSID
        confBean.setUsrSid(usrSid);
        //開始時間 9時で作成
        UDate frDate = new UDate();
        frDate.setHour(GSConstNippou.DF_FROM_HOUR);
        frDate.setMinute(GSConstNippou.DF_FROM_MINUTES);
        frDate.setSecond(GSConstNippou.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);

        //終了時間 18時で作成
        UDate toDate = new UDate();
        toDate.setHour(GSConstNippou.DF_TO_HOUR);
        toDate.setMinute(GSConstNippou.DF_TO_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);

        //デフォルト表示グループ
        GroupBiz gpBiz = new GroupBiz();
        int gsid = gpBiz.getDefaultGroupSid(usrSid, con);
        confBean.setNprDspGroup(gsid);
        //一覧表示件数
        confBean.setNprDspList(GSConstNippou.DEFAULT_LIST_CNT);
        //自動リロード
        confBean.setNprAutoReload(GSConstNippou.AUTO_RELOAD_10MIN);

        confBean.setNprAuid(usrSid);
        confBean.setNprAdate(new UDate());
        confBean.setNprEuid(usrSid);
        confBean.setNprEdate(new UDate());


        //初期値 タイトルカラー
        confBean.setNprIniFcolor(GSConstNippou.DF_BG_COLOR);
        //初期値 開始時刻 9時
        confBean.setNprIniFrDate(frDate);
        //初期値 終了時刻 18時
        confBean.setNprIniToDate(toDate);

        //ソート1
        confBean.setNprSortKey1(GSConstNippou.SORT_KEY_YKSK);
        confBean.setNprSortOrder1(GSConst.ORDER_KEY_ASC);
        //ソート2
        confBean.setNprSortKey2(GSConstNippou.SORT_KEY_NAME);
        confBean.setNprSortOrder2(GSConst.ORDER_KEY_ASC);
        //一覧表示件数
        confBean.setNprDspList(GSConstNippou.DEFAULT_LIST_CNT);
        //表示位置
        confBean.setNprDspPosition(GSConstNippou.DAY_POSITION_RIGHT);

        //日報通知
        confBean.setNprSmail(GSConstNippou.SML_NOTICE_YES);
        //コメント通知
        confBean.setNprCmtSmail(GSConstNippou.SML_NOTICE_YES);
        //いいね通知
        confBean.setNprGoodSmail(GSConstNippou.SML_NOTICE_YES);
        //次のアクションスケジュール表示
        confBean.setNprSchKbn(GSConstNippou.SCH_DSP_YES);

        return confBean;
    }

    /**
     * <br>[機  能] 日報管理者設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param con DBコネクション
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public NtpAdmConfModel getAdminConfiModel(Connection con) throws SQLException {
        //DBより現在の設定を取得する。
        NtpAdmConfDao dao = new NtpAdmConfDao(con);
        NtpAdmConfModel conf = dao.select();
        if (conf == null) {
            //データがない場合
            conf = new NtpAdmConfModel();
            //共有範囲
            conf.setNacCrange(GSConstNippou.CRANGE_SHARE_ALL);
            //自動削除
            conf.setNacAtdelFlg(GSConstNippou.AUTO_DELETE_OFF);
            conf.setNacAtdelY(-1);
            conf.setNacAtdelM(-1);
            //時間間隔
            conf.setNacHourDiv(GSConstNippou.DF_HOUR_DIVISION);

            //日報通知
            conf.setNacSmlKbn(GSConstNippou.SML_NOTICE_ADM);
            conf.setNacSmlNoticeKbn(GSConstNippou.SML_NOTICE_YES);
            conf.setNacSmlNoticeGrp(GSConstNippou.SML_NOTICE_GROUP);
            conf.setNacCsmlKbn(GSConstNippou.SML_NOTICE_ADM);
            conf.setNacCsmlNoticeKbn(GSConstNippou.SML_NOTICE_YES);
            conf.setNacGsmlKbn(GSConstNippou.SML_NOTICE_ADM);
            conf.setNacGsmlNoticeKbn(GSConstNippou.SML_NOTICE_YES);

            //登録者・更新者
            UDate now = new UDate();
            conf.setNacAuid(0);
            conf.setNacAdate(now);
            conf.setNacEuid(0);
            conf.setNacEdate(now);
        }

        log__.debug(conf.toCsvString());
        return conf;
    }

    /**
     * 日報で扱う時間間隔を取得する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 時間間隔
     * @throws SQLException SQL実行時例外
     */
    public int getHourDivision(Connection con) throws SQLException {
        int ret = GSConstNippou.DF_HOUR_DIVISION;
        NtpAdmConfModel conf = getAdminConfiModel(con);
        ret = conf.getNacHourDiv();
        return ret;
    }

    /**
     * 日報で扱う時間間隔から１時間を何分割するかを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 時間の何分割数
     * @throws SQLException SQL実行時例外
     */
    public int getDayNippouHourMemoriCount(Connection con)
    throws SQLException {
        int ret = GSConstNippou.HOUR_DIVISION_COUNT_10;
        int hourDiv = getHourDivision(con);
        switch (hourDiv) {
        case 5:
            ret = GSConstNippou.HOUR_DIVISION_COUNT_5;
            break;
        case 10:
            ret = GSConstNippou.HOUR_DIVISION_COUNT_10;
            break;
        case 15:
            ret = GSConstNippou.HOUR_DIVISION_COUNT_15;
            break;
        default:
            break;
        }
        return ret;
    }

    /**
     * 日間画面での1目盛の分を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 日間画面での1目盛の分
     * @throws SQLException SQL実行時例外
     */
    public int getDayNippouHourMemoriMin(Connection con)
    throws SQLException {
        int divMin = getDayNippouHourMemoriCount(con);
        return 60 / divMin;
    }

    /**
     * <br>[機  能] 基準日を指定し、基準日より古い日報データを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param bdate 基準日
     * @return 削除件数
     * @throws SQLException SQL実行エラー
     */
    public int deleteOldNippou(Connection con, UDate bdate) throws SQLException {

        NtpDataDao dao = new NtpDataDao(con);

        //削除する日報のSIDを取得
        ArrayList<String> sidList = new ArrayList<String>();
        sidList = dao.getDeleteNipSid(bdate);

        //日報データを物理削除
        int count = dao.deleteOldNippou(bdate);

        //日報に添付されているバイナリSID一覧取得
        NtpBinDao nbinDao = new NtpBinDao(con);
        List<Long> binSidList = nbinDao.selectBinSidList(sidList);

        //バイナリ情報を論理削除
        CmnBinfDao binDao = new CmnBinfDao(con);
        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(GSConstUser.SID_ADMIN);
        cbMdl.setBinUpdate(new UDate());
        binDao.updateJKbn(cbMdl, binSidList);

        //日報バイナリファイル情報を物理削除
        nbinDao.deleteNtpBin(sidList);

        return count;
    }


    /**
     * <br>[機  能] 日報Sidを指定し、日報データのバイナリファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param sidList 日報SID
     * @throws SQLException SQL実行エラー
     */
    public void deleteNippouFile(Connection con, List<String> sidList) throws SQLException {

        //日報に添付されているバイナリSID一覧取得
        NtpBinDao nbinDao = new NtpBinDao(con);
        List<Long> binSidList = nbinDao.selectBinSidList(sidList);

        //バイナリ情報を論理削除
        CmnBinfDao binDao = new CmnBinfDao(con);
        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(GSConstUser.SID_ADMIN);
        cbMdl.setBinUpdate(new UDate());
        binDao.updateJKbn(cbMdl, binSidList);

        //日報バイナリファイル情報を物理削除
        nbinDao.deleteNtpBin(sidList);

    }

    /**
     * <br>[機  能] 基準日を指定し、基準日より古い日報データ(コメント)を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param bdate 基準日
     * @return 削除件数
     * @throws SQLException SQL実行エラー
     */
    public int deleteOldNippouCmt(Connection con, UDate bdate) throws SQLException {
        //
        NtpCommentDao dao = new NtpCommentDao(con);
        int count = dao.deleteOldNippouCmt(bdate);
        return count;
    }

    /**
     * <br>[機  能] 基準日を指定し、基準日より古い日報データ(確認)を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param bdate 基準日
     * @return 削除件数
     * @throws SQLException SQL実行エラー
     */
    public int deleteOldNippouChk(Connection con, UDate bdate) throws SQLException {
        //
        NtpCheckDao dao = new NtpCheckDao(con);
        int count = dao.deleteOldNippouCheck(bdate);
        return count;
    }

    /**
     * <br>[機  能] 基準日を指定し、基準日より古い日報データ(いいね)を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param bdate 基準日
     * @return 削除件数
     * @throws SQLException SQL実行エラー
     */
    public int deleteOldNippouGood(Connection con, UDate bdate) throws SQLException {
        //
        NtpGoodDao dao = new NtpGoodDao(con);
        int count = dao.deleteOldNippouGood(bdate);
        return count;
    }

    /**
     * <br>[機  能] 日報情報のデフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しその表示グループを返す。
     * <br>グループが削除されていた場合は、デフォルトグループを返す。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public int getDispDefaultGroupSid(Connection con, int usrSid) throws SQLException {
        //
        NtpAdmConfModel aconf = getAdminConfiModel(con);
        NtpPriConfModel pconf = getNtpPriConfModel(con, usrSid);

        if (pconf == null) {
            pconf = getDefaulPriConfiModel(usrSid, con);
        }
        int gsid = 0;
        if (aconf.getNacCrange() == GSConstNippou.CRANGE_SHARE_GROUP) {
            gsid = pconf.getNprDspGroup();
        } else {
            if (pconf.getNprDspMygroup() != 0) {
                gsid = pconf.getNprDspMygroup();
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    GroupBiz gbiz = new GroupBiz();
                    gsid = gbiz.getDefaultGroupSid(usrSid, con);
                }
            } else {
                gsid = pconf.getNprDspGroup();
                //グループ存在チェック
                GroupDao gdao = new GroupDao(con);
                CmnGroupmModel group = gdao.getGroup(gsid);
                if (group == null) {
                    //個人設定未設定 or 不正データの場合、ユーザマネージャのデフォルトグループ
                    GroupBiz gbiz = new GroupBiz();
                    gsid = gbiz.getDefaultGroupSid(usrSid, con);
                } else {
                    if (GSConst.JTKBN_DELETE == group.getGrpJkbn()) {
                        //状態区分が削除の場合はデフォルトグループを返す
                        GroupBiz gbiz = new GroupBiz();
                        gsid = gbiz.getDefaultGroupSid(usrSid, con);
                    }
                }
            }
        }
        log__.debug("デフォルト表示グループID=>" + gsid);
        return gsid;
    }
    /**
     * <br>[機  能] 日報情報のデフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しその表示グループを返す。
     * <br>グループが削除されていた場合は、デフォルトグループを返す。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public String getDispDefaultGroupSidStr(Connection con, int usrSid) throws SQLException {
        //
        NtpAdmConfModel aconf = getAdminConfiModel(con);
        NtpPriConfModel pconf = getNtpPriConfModel(con, usrSid);

        if (pconf == null) {
            pconf = getDefaulPriConfiModel(usrSid, con);
        }
        String gsid = null;
        if (aconf.getNacCrange() == GSConstNippou.CRANGE_SHARE_GROUP) {
            gsid = String.valueOf(pconf.getNprDspGroup());
            if (pconf.getNprDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getNprDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                ArrayList<String> gsidList = new ArrayList<String>();
                gsidList.add(gsid);
                if (cmgDao.getMyGroupList(usrSid,
                        gsidList).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    GroupBiz gbiz = new GroupBiz();
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstNippou.MY_GROUP_STRING + gsid;
                }
            } else {
                gsid = String.valueOf(pconf.getNprDspGroup());
                //個人設定値が閲覧可能なグループで無い場合はデフォルトグループを表示
                if (!isDspOkGroup(pconf.getNprDspGroup(), usrSid, con)) {
                    GroupBiz gbiz = new GroupBiz();
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                }
            }
        } else {
            if (pconf.getNprDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getNprDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                ArrayList<String> gsidList = new ArrayList<String>();
                gsidList.add(gsid);
                if (cmgDao.getMyGroupList(usrSid,
                        gsidList).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    GroupBiz gbiz = new GroupBiz();
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstNippou.MY_GROUP_STRING + gsid;
                }
            } else {
                gsid = String.valueOf(pconf.getNprDspGroup());
                //グループ存在チェック
                GroupDao gdao = new GroupDao(con);
                CmnGroupmModel group = gdao.getGroup(Integer.parseInt(gsid));
                if (group == null) {
                    //個人設定未設定 or 不正データの場合、ユーザマネージャのデフォルトグループ
                    GroupBiz gbiz = new GroupBiz();
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    if (GSConst.JTKBN_DELETE == group.getGrpJkbn()) {
                        //状態区分が削除の場合はデフォルトグループを返す
                        GroupBiz gbiz = new GroupBiz();
                        gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                    }
                }
            }
        }

        return gsid;
    }
    /**
     * <br>[機  能] 共有範囲が所属グループのみの場合に指定したグループにユーザが所属しているかのチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param gpSid グループSID
     * @return gpFlg
     * @throws SQLException SQL実行エラー
     */
    public boolean checkGroupSidStr(
            Connection con, int usrSid, String gpSid) throws SQLException {
        boolean gpFlg = true;
        NtpAdmConfModel aconf = getAdminConfiModel(con);
        if (aconf.getNacCrange() == GSConstNippou.CRANGE_SHARE_GROUP) {
            GroupBiz gbiz = new GroupBiz();
            if (!gbiz.isBelongGroup(usrSid, Integer.valueOf(gpSid), con)) {
                gpFlg = false;
            }
        }
        return gpFlg;
    }
    /**
     * ユーザが閲覧可能なグループか判定する。
     * 所属グループ、グループ外ユーザとして設定されているグループは閲覧可能
     * @param gpSid グループSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return boolean true=可能 false=不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isDspOkGroup(int gpSid, int usrSid, Connection con) throws SQLException {

        //管理者設定
        NtpAdmConfModel admConf = getAdmConfModel(con);
        //グループリスト取得
        CmnGroupmDao grpDao = new CmnGroupmDao(con);
        List<CmnGroupmModel> gpList = grpDao.selectBelongGroupList(usrSid);

        if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_ALL) {
            //全員で共有
            gpList = grpDao.select();
        } else {
            //所属グループのみで共有
            NippouDao ntpDao = new NippouDao(con);
            List<CmnGroupmModel> mdlList = grpDao.select();
            //セッションユーザの所属グループを取得
            gpList = grpDao.selectBelongGroupList(usrSid);
            //特例アクセス設定によって閲覧を許可されたグループを取得
            List<Integer> belongGrpList = ntpDao.getGroupBelongSpAccessUser(usrSid);
            List<Integer> accessGrpList = ntpDao.getAccessGrpList(usrSid);
            //閲覧可能な全てのグループを取得
            for (CmnGroupmModel gModel : mdlList) {
                if (accessGrpList.contains(gModel.getGrpSid())
                        || belongGrpList.contains(gModel.getGrpSid())) {
                    gpList.add(gModel);
                }
            }
        }

        for (CmnGroupmModel gmdl : gpList) {
            if (gmdl.getGrpSid() == gpSid) {
                return true;
            }
        }
        return false;
    }
    /**
     * フォーム情報のグループコンボ値からグループSID又はマイグループSIDを取得する
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
     * フォーム情報のグループコンボ値がグループSIDかマイグループSIDかを判定する
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
        int index = gpSid.indexOf(GSConstNippou.MY_GROUP_STRING);

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * <br>[機  能] 日報拡張登録画面の初期値を取得
     * <br>[解  説] 画面表示用に使用します
     * <br>[備  考] 拡張SIDと登録・更新者、時間は設定されません。
     * @param usrSid ユーザSID
     * @param date 登録・編集日付
     * @param con DBコネクション
     * @return NippouExSearchModel 拡張情報Bean
     * @throws SQLException SQL実行エラー
     */
    public NippouExSearchModel getDispDefaultExtend(int usrSid, UDate date, Connection con)
    throws SQLException {
        //個人設定を取得
        NtpPriConfModel priConf = getNtpPriConfModel(con, usrSid);
        NippouExSearchModel extMdl = new NippouExSearchModel();

        extMdl.setNexKbn(GSConstNippou.EXTEND_KBN_DAY);
        extMdl.setNexTranKbn(GSConstNippou.FURIKAE_KBN_NONE);
        extMdl.setNexWeek(GSConstNippou.SETTING_NONE);
        extMdl.setNexDay(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek1(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek2(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek3(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek4(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek5(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek6(GSConstNippou.SETTING_NONE);
        extMdl.setNexDweek7(GSConstNippou.SETTING_NONE);
        extMdl.setNexDateFr(date);
        extMdl.setNexDateTo(date);
        extMdl.setNexTimeTo(priConf.getNprIniToDate());
        extMdl.setNexTitle("");
        extMdl.setNexTitleClo(priConf.getNprIniFcolor());
        extMdl.setNexDetail("");

        return extMdl;
    }

    /**
     * ユーザIDを指定しマイグループラベルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getMyGroupLabel(int userSid, Connection con)
    throws SQLException {
        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

        //マイグループリストをセット
        List<NtpLabelValueModel> cmgLabelList = new ArrayList<NtpLabelValueModel>();
        for (CmnMyGroupModel cmgMdl : cmgList) {

            cmgLabelList.add(
                    new NtpLabelValueModel(
                            cmgMdl.getMgpName(),
                            GSConstNippou.MY_GROUP_STRING
                            + String.valueOf(cmgMdl.getMgpSid()), "1")
                            );
        }
        return cmgLabelList;
    }
    /**
     * <br>[機  能] ショートメールで日報登録通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param ntpMdl 日報内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエストモデル
     * @param url 日報URL
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Connection con,
        MlCountMtController cntCon,
        NtpDataModel ntpMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        RequestModel reqMdl,
        String url) throws Exception {

        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = null;

        // 登録者
        int addUserSid = ntpMdl.getNipEuid();
        //送信先ユーザSIDリスト
        List<Integer> sidList = new ArrayList<Integer>();

        NtpAdmConfModel admConf = getAdmConfModel(con);

        //ユーザの所属グループを取得
        ArrayList<Integer> gpSids = new ArrayList<Integer>();
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        gpSids = bdao.selectUserBelongGroupSid(addUserSid);


        //除外するユーザ
        ArrayList<Integer> existSids = new ArrayList<Integer>();
        existSids.add(0);
        existSids.add(1);
        existSids.add(addUserSid);

        if (admConf.getNacSmlKbn() == GSConstNippou.SML_NOTICE_ADM) {
            if (admConf.getNacSmlNoticeKbn() == GSConstNippou.SML_NOTICE_YES) {


                if (admConf.getNacSmlNoticeGrp() == GSConstNippou.SML_NOTICE_GROUP) {

                    //所属グループユーザのSIDを取得
                    ArrayList<Integer> gpUsrSids = new ArrayList<Integer>();
                    UserSearchDao usDao = new UserSearchDao(con);
                    gpUsrSids = usDao.getBelongUserSids(gpSids, existSids);
                    if (!gpUsrSids.isEmpty()) {
                        sidList.addAll(gpUsrSids);
                    }

                } else {
                   //所属グループ管理者のSIDを取得
                    SltUserPerGroupDao tdao = new SltUserPerGroupDao(con);
                    sidList = tdao.selectAdminGroupUserSidList(gpSids, existSids);
                }
            }
        } else {
            boolean kyoyuFlg = false;

            if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_GROUP) {
                kyoyuFlg = true;
            }

            //個人設定のショートメール通知で自分を設定しているユーザを取得
            sidList = __getNtpSmlUsrList(
                    con, addUserSid, gpSids, existSids, kyoyuFlg);
        }

        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }
        //日報に紐づく案件の閲覧権限があるユーザで絞り込み
        sidList = getUserSidViewableAnken(con, ntpMdl.getNanSid(), sidList);
        //日報登録ユーザに閲覧権限があるユーザで絞り込み
        sidList = getUserSidViewableNippou(con, ntpMdl.getUsrSid(), sidList);

        try {

            //登録ユーザ名
            umodel = udao.select(addUserSid);
            String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            //タイトル
            String thtitle = ntpMdl.getNipTitle();
            //登録日時
            UDate aDate = ntpMdl.getNipEdate();
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = getSmlTemplateFilePath(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("UNAME", addUserName);
            map.put("TITLE", thtitle);
            map.put("URL", url);

            String bodyml = StringUtil.merge(tmpBody, map);

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            String title = "[GS 日報]" + addUserName + "さん が日報を登録しました。 "  + thtitle;

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
            SmlSender sender = new SmlSender(con,
                                             cntCon,
                                             smlModel,
                                             pluginConfig,
                                             appRootPath,
                                             reqMdl);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }

    /**
     * <br>[機  能] 日報の登録通知をするユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param grpSids 所属グループSID
     * @param existUsrSids 除外するユーザ
     * @param kyoyuFlg 共有フラグ false:全員 true:所属グループのみ
     * @return sidList ユーザSIDリスト
     * @throws Exception 実行例外
     */
    private List<Integer> __getNtpSmlUsrList(
                                    Connection con,
                                    int usrSid,
                                    ArrayList<Integer> grpSids,
                                    ArrayList<Integer> existUsrSids,
                                    boolean kyoyuFlg) throws Exception {

        ArrayList<Integer> usrSidList      = new ArrayList<Integer>();
//        ArrayList<Integer> myGrpSidList    = new ArrayList<Integer>();
//        ArrayList<Integer> myGrpUsrSidList = new ArrayList<Integer>();
        ArrayList<Integer> grpUsrSidList   = new ArrayList<Integer>();
        ArrayList<Integer> sidList         = new ArrayList<Integer>();
        ArrayList<Integer> sendSidList     = new ArrayList<Integer>();

        //ユーザとして自分を設定しているユーザを取得
        NtpSmlMemberDao nsmDao = new NtpSmlMemberDao(con);
        usrSidList = nsmDao.getSelUsrSid(usrSid, existUsrSids);
        existUsrSids.addAll(usrSidList);
        sidList.addAll(usrSidList);

        //自分の所属グループを設定しているユーザを取得
        grpUsrSidList = nsmDao.getSelGrpSid(grpSids, existUsrSids, false);
        existUsrSids.addAll(grpUsrSidList);
        sidList.addAll(grpUsrSidList);

        //自分の所属するマイグループを設定しているユーザを取得
//        CmnMyGroupMsDao myGrpDao = new CmnMyGroupMsDao(con);
//        myGrpSidList = myGrpDao.getMyGroupSid(usrSid);
//        if (!myGrpSidList.isEmpty()) {
//            myGrpUsrSidList = nsmDao.getSelGrpSid(myGrpSidList, existUsrSids, true);
//            sidList.addAll(myGrpUsrSidList);
//        }

        if (!sidList.isEmpty()) {
            if (kyoyuFlg) {
                //自分と同じグループに所属しているユーザのみ取得
                GroupBiz gbiz = new GroupBiz();
                for (int uSid : sidList) {
                    if (gbiz.isBothBelongGroup(usrSid, uSid, con)) {
                        sendSidList.add(uSid);
                    }
                }
            } else {
                sendSidList.addAll(sidList);
            }
        }
        return sendSidList;
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
                + "/WEB-INF/plugin/nippou/smail/touroku_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] 業務一覧リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sStr インデックスの最初のラベル名
     * @throws SQLException SQL実行エラー
     * @return 業務一覧リスト
     */
    public List<LabelValueBean> getGyomuList(Connection con, String sStr) throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(sStr, String.valueOf(-1)));

        // 業務一覧を取得
        Ntp140Dao gyomuDao = new Ntp140Dao(con);
        List<NtpGyomuModel> gyoumuList = gyomuDao.getGyoumuList();

        for (NtpGyomuModel mdl : gyoumuList) {
            labelList.add(
                    new LabelValueBean(mdl.getNgyName(), String.valueOf(mdl.getNgySid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 業務一覧リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return 業務一覧リスト
     */
    public List<LabelValueBean> getGyomuList(Connection con) throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        // 業務一覧を取得
        Ntp140Dao gyomuDao = new Ntp140Dao(con);
        List<NtpGyomuModel> gyoumuList = gyomuDao.getGyoumuList();

        for (NtpGyomuModel mdl : gyoumuList) {
            labelList.add(
                    new LabelValueBean(mdl.getNgyName(), String.valueOf(mdl.getNgySid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] プロセス一覧リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sStr インデックスの最初のラベル名
     * @param ngySid 業務SID
     * @throws SQLException SQL実行エラー
     * @return 業務一覧リスト
     */
    public List<LabelValueBean> getProcessList(Connection con, String sStr, int ngySid)
    throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(sStr, String.valueOf(-1)));

        // プロセス一覧を取得
        //NtpProcessDao processDao = new NtpProcessDao(con);
        Ntp150ProcessDao processDao = new Ntp150ProcessDao(con);
        List<NtpProcessModel> processList = processDao.select(ngySid);

        for (NtpProcessModel mdl : processList) {
            labelList.add(
                    new LabelValueBean(mdl.getNgpName(), String.valueOf(mdl.getNgpSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 顧客源泉一覧リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sStr インデックスの最初のラベル名
     * @throws SQLException SQL実行エラー
     * @return 業務一覧リスト
     */
    public List<LabelValueBean> getContactList(Connection con, String sStr) throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(sStr, String.valueOf(-1)));

        // コンタクト一覧を取得
        Ntp190Dao contactDao = new Ntp190Dao(con);
        List<NtpContactModel> contactMdl = contactDao.getKthouhouList();

        for (NtpContactModel mdl : contactMdl) {
            labelList.add(
                    new LabelValueBean(mdl.getNcnName(), String.valueOf(mdl.getNcnSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 状態リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 状態一覧リスト
     */
    public List<LabelValueBean> getStateList() {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        // 状態一覧を取得
        labelList.add(
                new LabelValueBean("すべて", String.valueOf(-1)));
        labelList.add(
                new LabelValueBean("進行中", String.valueOf(GSConstNippou.STATE_UNCOMPLETE)));
        labelList.add(
                new LabelValueBean("完了", String.valueOf(GSConstNippou.STATE_COMPLETE)));

        return labelList;
    }

    /**
     * <br>[機  能] 案件状態リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 案件状態一覧リスト
     */
    public List<LabelValueBean> getAnkenStateList() {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        // 状態一覧を取得
        labelList.add(
                new LabelValueBean("すべて", String.valueOf(-1)));
        labelList.add(
                new LabelValueBean("商談中", String.valueOf(GSConstNippou.SYODAN_CHU)));
        labelList.add(
                new LabelValueBean("受注", String.valueOf(GSConstNippou.SYODAN_JYUCHU)));
        labelList.add(
                new LabelValueBean("失注", String.valueOf(GSConstNippou.SYODAN_SICHU)));

        return labelList;
    }

    /**
     * <br>表示開始日から前後5年のコンボを生成します
     * @param year 基準年
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    public List<LabelValueBean> getYearLavel(int year) {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", ""));
        for (int i = year - 5; i <= year + 5; i++) {
            labelList.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.year",
                            new String[] {String.valueOf(i)}), String.valueOf(i)));
        }
        return labelList;
    }

    /**
     * <br>月コンボを生成します
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public List<LabelValueBean> getMonthLavel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //月
        String textMonth = gsMsg.getMessage("cmn.month");

        int month = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", ""));
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + textMonth, String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>日コンボを生成します
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public List<LabelValueBean> getDayLavel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //日
        String textDay = gsMsg.getMessage("cmn.day");

        int day = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", ""));
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + textDay, String.valueOf(day)));
            day++;
        }
        return labelList;
    }

    /**
     * <br>カテゴリコンボを生成します
     * @return ArrayList (in LabelValueBean)  カテゴリコンボコンボ
     * @throws SQLException sql 実行時例外
     */
    public List<LabelValueBean> getCategoryLavel() throws SQLException {
        ArrayList<LabelValueBean> ntpShohinCatList = new ArrayList<LabelValueBean>();
        NtpShohinCategoryDao catDao = new NtpShohinCategoryDao(con__);
        List<NtpShohinCategoryModel> catMdlList = catDao.select();
        ntpShohinCatList.add(new LabelValueBean("すべて", String.valueOf(-1)));
        for (NtpShohinCategoryModel mdl : catMdlList) {
            String catName = mdl.getNscName();
            String catSid = String.valueOf(mdl.getNscSid());
            ntpShohinCatList.add(new LabelValueBean(catName, catSid));
        }
        return ntpShohinCatList;
    }

    /**
     * <br>[機  能] ショートメールプラグインでコメント登録通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param ntpMdl コメント内容(ショートメール送信用)
     * @param cmtMdl コメントモデル
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param url 日報URL
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void sendPlgSmail(
        Connection con,
        MlCountMtController cntCon,
        NtpDataModel ntpMdl,
        NtpCommentModel cmtMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        String url,
        RequestModel reqMdl) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);


        if (ntpMdl != null) {
            CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel umodel = null;
            String userName = "";

            // 被登録者
            int userSid = ntpMdl.getUsrSid();
            // 登録者
            int addUserSid = cmtMdl.getUsrSid();
            umodel = udao.select(userSid);
            userName = umodel.getUsiSei() + " " + umodel.getUsiMei();

            //送信するユーザSIDリスト(仮)を作成(同じ日報にコメントしているユーザを取得)
            ArrayList<Integer> sidList = new ArrayList<Integer>();
            NtpCommentDao cmtDao = new NtpCommentDao(con);
            //コメントをしているユーザを取得(登録者以外)
            List<Integer> usrIds = new ArrayList<Integer>();
            usrIds.add(addUserSid);
            usrIds.add(userSid);
            sidList = cmtDao.getNpcUsrList(ntpMdl.getNipSid(), usrIds);
            if (addUserSid != userSid) {
                sidList.add(userSid);
            }

            //送信先(仮)がない場合は終了
            if (sidList.size() < 1) {
                return;
            }

            ArrayList<Integer> sendSidList = new ArrayList<Integer>();
            NtpPriConfModel priConf = null;
            //管理者設定、個人設定を反映させた送信先を作成
            NtpAdmConfModel admConf = getAdmConfModel(con);
            if (admConf.getNacCsmlKbn() == GSConstNippou.SML_NOTICE_ADM) {
                if (admConf.getNacCsmlNoticeKbn() == GSConstNippou.SML_NOTICE_YES) {
                    //管理者が設定する 通知する
                    sendSidList = sidList;
                } else {
                    //管理者が設定する 通知しない
                    return;
                }
            } else {
                //個人設定で通知するに設定しているユーザを取得
                for (int usid : sidList) {
                    priConf = getNtpPriConfModel(con, usid);
                    if (priConf.getNprCmtSmail() == GSConstNippou.SML_NOTICE_YES) {
                        sendSidList.add(usid);
                    }
                }
            }

            //送信先がない場合は終了
            if (sendSidList.size() < 1) {
                return;
            }

            try {

                //登録ユーザ名
                umodel = udao.select(addUserSid);
                String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
                //日報タイトル
                String thtitle = ntpMdl.getNipTitle();
                //登録日時
                UDate aDate = cmtMdl.getNpcAdate();
                String adate = UDateUtil.getSlashYYMD(aDate) + " "
                + UDateUtil.getSeparateHMS(aDate);

                //本文
                String tmpPath = getNtpCmtTemplateFilePathPlg(appRootPath); //テンプレートファイルパス取得
                String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
                Map<String, String> map = new HashMap<String, String>();
                map.put("ADATE", adate);
                map.put("UNAME", addUserName);
                map.put("NAME", userName);
                map.put("TITLE", thtitle);
                map.put("URL", url);

                String bodyml = StringUtil.merge(tmpBody, map);
                String omit = gsMsg.getMessage("cmn.mail.omit");

                if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                    bodyml = omit + "\r\n\r\n" + bodyml;
                    bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
                }

                //ショートメール送信用モデルを作成する。
                SmlSenderModel smlModel = new SmlSenderModel();
                //送信者(システムメールを指定)
                smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
                //TO
                smlModel.setSendToUsrSidArray(sendSidList);
                //タイトル
                //日報
                String textNippou = "日報";
                //登録通知
                String textAdd = "さん からコメントがありました。";
                String title =
                    "[GS " + textNippou + "] " + addUserName + textAdd + " " + thtitle;
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
                SmlSender sender = new SmlSender(
                        con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl);
                sender.execute();
            } catch (Exception e) {
                e.printStackTrace();
                log__.error("ショートメール送信に失敗しました。");
                throw e;
            }
        }
    }


    /**
     * <br>[機  能] ショートメールプラグインでいいね!通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param ntpMdl コメント内容(ショートメール送信用)
     * @param addUsrSid いいねユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param url 日報URL
     * @throws Exception 実行例外
     */
    public void sendGoodPlgSmail(
        Connection con,
        MlCountMtController cntCon,
        NtpDataModel ntpMdl,
        int addUsrSid,
        String appRootPath,
        PluginConfig pluginConfig,
        String url) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);


        if (ntpMdl != null) {
            CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel umodel = null;
            String userName = "";

            // 被登録者
            int userSid = ntpMdl.getUsrSid();
            // 登録者
            int addUserSid = addUsrSid;
            umodel = udao.select(userSid);
            userName = umodel.getUsiSei() + " " + umodel.getUsiMei();

            //送信するユーザSIDリスト(仮)を作成(同じ日報にコメントしているユーザを取得)
            ArrayList<Integer> sidList = new ArrayList<Integer>();
            if (addUserSid != userSid) {
                sidList.add(userSid);
            }

            //送信先(仮)がない場合は終了
            if (sidList.size() < 1) {
                return;
            }

            ArrayList<Integer> sendSidList = new ArrayList<Integer>();
            NtpPriConfModel priConf = null;
            //管理者設定、個人設定を反映させた送信先を作成
            NtpAdmConfModel admConf = getAdmConfModel(con);
            if (admConf.getNacGsmlKbn() == GSConstNippou.SML_NOTICE_ADM) {
                if (admConf.getNacGsmlNoticeKbn() == GSConstNippou.SML_NOTICE_YES) {
                    //管理者が設定する 通知する
                    sendSidList = sidList;
                } else {
                    //管理者が設定する 通知しない
                    return;
                }
            } else {
                //個人設定で通知するに設定しているユーザを取得
                for (int usid : sidList) {
                    priConf = getNtpPriConfModel(con, usid);
                    if (priConf.getNprGoodSmail() == GSConstNippou.SML_NOTICE_YES) {
                        sendSidList.add(usid);
                    }
                }
            }

            //送信先がない場合は終了
            if (sendSidList.size() < 1) {
                return;
            }

            try {

                //登録ユーザ名
                umodel = udao.select(addUserSid);
                String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
                //日報タイトル
                String thtitle = ntpMdl.getNipTitle();
                //登録日時
                UDate aDate = new UDate();
                String adate = UDateUtil.getSlashYYMD(aDate) + " "
                + UDateUtil.getSeparateHMS(aDate);

                //本文
                String tmpPath = getNtpGoodTemplateFilePathPlg(appRootPath); //テンプレートファイルパス取得
                String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
                Map<String, String> map = new HashMap<String, String>();
                map.put("ADATE", adate);
                map.put("UNAME", addUserName);
                map.put("NAME", userName);
                map.put("TITLE", thtitle);
                map.put("URL", url);

                String bodyml = StringUtil.merge(tmpBody, map);
                String omit = gsMsg.getMessage("cmn.mail.omit");

                if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                    bodyml = omit + "\r\n\r\n" + bodyml;
                    bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
                }

                //ショートメール送信用モデルを作成する。
                SmlSenderModel smlModel = new SmlSenderModel();
                //送信者(システムメールを指定)
                smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
                //TO
                smlModel.setSendToUsrSidArray(sendSidList);
                //タイトル
                //日報
                String textNippou = "日報";
                //登録通知
                String textAdd = "さん がいいね!しました。";
                String title =
                    "[GS " + textNippou + "] " + addUserName + textAdd + " " + thtitle;
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
                SmlSender sender = new SmlSender(
                        con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl__);
                sender.execute();
            } catch (Exception e) {
                e.printStackTrace();
                log__.error("ショートメール送信に失敗しました。");
                throw e;
            }
        }
    }


    /**
     * <br>[機  能] アプリケーションのルートパスからコメント通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getNtpCmtTemplateFilePathPlg(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/nippou/smail/comment_tsuuchi_plg.txt");
        return ret;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスからいいね通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getNtpGoodTemplateFilePathPlg(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/nippou/smail/good_tsuuchi_plg.txt");
        return ret;
    }

    /**
     * <br>[機  能] 各ユーザが重複登録設定を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditSmlKbn(Connection con) throws SQLException {
        return canEditSmlKbn(getAdmConfModel(con));
    }

    /**
     * <br>[機  能] 各ユーザがショートメール通知設定を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf スケジュール 管理者設定
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditSmlKbn(NtpAdmConfModel admConf) throws SQLException {
        boolean smlEditFlg = false;
        if (admConf.getNacSmlKbn() == GSConstNippou.SML_NOTICE_USR
                || admConf.getNacCsmlKbn() == GSConstNippou.SML_NOTICE_USR
                || admConf.getNacGsmlKbn() == GSConstNippou.SML_NOTICE_USR) {
            smlEditFlg = true;
        }
        return smlEditFlg;
    }

    /**
     * <br>[機  能] スケジュール管理者設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param con DBコネクション
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public NtpAdmConfModel getAdmConfModel(Connection con) throws SQLException {
        //DBより現在の設定を取得する。
        NtpAdmConfDao dao = new NtpAdmConfDao(con);
        NtpAdmConfModel conf = dao.select();
        if (conf == null) {
            //データがない場合
            conf = new NtpAdmConfModel();
            //共有範囲
            conf.setNacCrange(GSConstNippou.CRANGE_SHARE_ALL);
            //自動削除
            conf.setNacAtdelFlg(GSConstNippou.AUTO_DELETE_OFF);
            conf.setNacAtdelY(-1);
            conf.setNacAtdelM(-1);
            //時間間隔
            conf.setNacHourDiv(GSConstNippou.DF_HOUR_DIVISION);

            //ショートメール区分
            conf.setNacSmlKbn(GSConstNippou.SML_NOTICE_ADM);
            //ショートメール通知区分
            conf.setNacSmlNoticeKbn(GSConstNippou.SML_NOTICE_YES);
            //ショートメール通知先区分
            conf.setNacSmlNoticeGrp(GSConstNippou.SML_NOTICE_GROUP);

            //登録者・更新者
            UDate now = new UDate();
            conf.setNacAuid(0);
            conf.setNacAdate(now);
            conf.setNacEuid(0);
            conf.setNacEdate(now);

        }

        log__.debug(conf.toCsvString());
        return conf;
    }

    /**
     * 表示グループ用のグループリストを取得する(所属グループのみ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getGroupLabelForNippou(int usrSid,
            Connection con, boolean sentakuFlg) throws SQLException {
        //管理者設定
        NtpAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<NtpLabelValueModel> labelList = new ArrayList<NtpLabelValueModel>();

        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textHide = gsMsg.getMessage("cmn.specified.no");
        if (sentakuFlg) {
            labelList.add(new NtpLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        //グループリスト取得
        ArrayList<GroupModel> gpList = null;
        if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_ALL) {
            log__.debug("全員で共有するグループリストを取得");
            //全員で共有
            GroupBiz groupBiz = new GroupBiz();
            gpList = groupBiz.getGroupCombList(con);
        } else {
            //所属グループのみで共有
            log__.debug("所属グループのみで共有するグループリストを取得");
            UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
            gpList = gpDao.selectGroupNmListOrderbyClass(usrSid);

            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            GroupDao dao = new GroupDao(con);
            ArrayList<GroupModel> gpBaseList = dao.getGroupList(sortMdl);

            NippouDao ntpDao = new NippouDao(con);
            //ユーザが所属するグループを取得
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongGroupMdlList = belongDao.selectUserBelongGroupSid(usrSid);
            List<Integer> belongGrpList = new ArrayList<Integer>();
            List<Integer> accessGrpList = new ArrayList<Integer>();

            //閲覧可能ユーザが所属するグループを取得
            belongGrpList
                    = ntpDao.getGroupBelongSpAccessUser(usrSid);
            accessGrpList = ntpDao.getAccessGrpList(usrSid);

            gpList = new ArrayList<GroupModel>();
            for (GroupModel gpMdl : gpBaseList) {
                int grpSid = gpMdl.getGroupSid();
                if (belongGroupMdlList.contains(grpSid)
                || accessGrpList.contains(grpSid)
                || belongGrpList.contains(grpSid)) {
                    gpList.add(gpMdl);
                }
            }
        }

        for (GroupModel gmodel : gpList) {
            labelList.add(new NtpLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0"));
        }

            //マイグループを追加
            List<NtpLabelValueModel> mylabelList = getMyGroupLabel(usrSid, con);
            if (!sentakuFlg) {
                labelList.addAll(0, mylabelList);
            } else {
                labelList.addAll(1, mylabelList);
            }

        return labelList;
    }

    /**
     * 表示グループ用のグループリストを取得する(マイグループ以外の所属グループのみ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getGroupLabelForNippou2(int usrSid,
            Connection con, boolean sentakuFlg) throws SQLException {
        //管理者設定
        NtpAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<NtpLabelValueModel> labelList = new ArrayList<NtpLabelValueModel>();

        //グループリスト取得
        ArrayList<GroupModel> gpList = null;
        if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_ALL) {
            log__.debug("全員で共有するグループリストを取得");
            //全員で共有
            GroupBiz groupBiz = new GroupBiz();
            gpList = groupBiz.getGroupCombList(con);
        } else {
            //所属グループのみで共有
            log__.debug("所属グループのみで共有するグループリストを取得");
            UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
            gpList = gpDao.selectGroupNmListOrderbyClass(usrSid);


            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            GroupDao dao = new GroupDao(con);
            ArrayList<GroupModel> gpBaseList = dao.getGroupList(sortMdl);

            NippouDao ntpDao = new NippouDao(con);
            //ユーザが所属するグループを取得
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongGroupMdlList = belongDao.selectUserBelongGroupSid(usrSid);
            List<Integer> belongGrpList = new ArrayList<Integer>();
            List<Integer> accessGrpList = new ArrayList<Integer>();

            //閲覧可能ユーザが所属するグループを取得
            belongGrpList
                    = ntpDao.getGroupBelongSpAccessUser(usrSid);
            accessGrpList = ntpDao.getAccessGrpList(usrSid);

            gpList = new ArrayList<GroupModel>();
            for (GroupModel gpMdl : gpBaseList) {
                int grpSid = gpMdl.getGroupSid();
                if (belongGroupMdlList.contains(grpSid)
                || accessGrpList.contains(grpSid)
                || belongGrpList.contains(grpSid)) {
                    gpList.add(gpMdl);
                }
            }

        }

        for (GroupModel gmodel : gpList) {
            labelList.add(new NtpLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0"));
        }

        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textHide = gsMsg.getMessage("cmn.hide");
        if (sentakuFlg) {
            labelList.add(new NtpLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        return labelList;
    }

    /**
     * 表示グループ用のグループリストを取得する(マイグループ以外のすべてのグループ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getGroupLabelForNippou3(int usrSid,
            Connection con, boolean sentakuFlg) throws SQLException {
        //管理者設定
        //NtpAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<NtpLabelValueModel> labelList = new ArrayList<NtpLabelValueModel>();

        //グループリスト取得
        ArrayList<GroupModel> gpList = null;

        log__.debug("全員で共有するグループリストを取得");
        //全員で共有
        GroupBiz groupBiz = new GroupBiz();
        gpList = groupBiz.getGroupCombList(con);

        for (GroupModel gmodel : gpList) {
            labelList.add(new NtpLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0"));
        }

        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textHide = gsMsg.getMessage("cmn.hide");
        if (sentakuFlg) {
            labelList.add(new NtpLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        return labelList;
    }

    /**
     * 表示グループ用のグループリストを取得する(マイグループ以外のすべてのグループ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param allFlg true:「すべて」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getGroupLabelForNippou4(int usrSid,
            Connection con, boolean allFlg) throws SQLException {
        //管理者設定
        //NtpAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<NtpLabelValueModel> labelList = new ArrayList<NtpLabelValueModel>();

        //グループ
        String textHide = "すべて";
        if (allFlg) {
            labelList.add(new NtpLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        //グループリスト取得
        ArrayList<GroupModel> gpList = null;

        log__.debug("全員で共有するグループリストを取得");
        //全員で共有
        GroupBiz groupBiz = new GroupBiz();
        gpList = groupBiz.getGroupCombList(con);

        for (GroupModel gmodel : gpList) {
            labelList.add(new NtpLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0"));
        }



        return labelList;
    }

    /**
     * 指定ユーザの日報を閲覧できるかをチェックする
     * @param sessionUsrSid セッションユーザSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return 閲覧可能フラグ
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanInspection(int sessionUsrSid, int usrSid,
            Connection con) throws SQLException {
        boolean canInspection = true;

        //管理者設定
        NtpAdmConfModel admConf = getAdmConfModel(con);
        //特例アクセス設定によって閲覧を許可されているユーザを取得
        NippouDao ntpDao = new NippouDao(con);
        List<Integer> accessUsrList = ntpDao.getNotAccessUserList(sessionUsrSid);

        if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_GROUP) {
            //所属グループのみで共有
            GroupBiz gbiz = new GroupBiz();
            if (gbiz.isBothBelongGroup(sessionUsrSid, usrSid, con)
                    && !accessUsrList.contains(usrSid)) {
                canInspection = true;
            } else {
                canInspection = false;
            }
        } else if (admConf.getNacCrange() == GSConstNippou.CRANGE_SHARE_ALL) {
            //共有範囲設定なし
            if (!accessUsrList.contains(usrSid)) {
                canInspection = true;
            } else {
                canInspection = false;
            }

        }

        return canInspection;
    }
    /**
     *
     * <br>[機  能] 管理者権限を持つか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return 管理者権限を持つか
     * @throws SQLException SQL実行時例外
     */
    public boolean isNippouAdmin(Connection con, RequestModel reqMdl) throws SQLException {
        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();

        boolean isAdmin = commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU);
        return isAdmin;
    }
    /**
     * 表示グループ用のグループリストを取得する(所属グループのみ)
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「選択してください」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<NtpLabelValueModel> getBelongGroupLabelList(int usrSid,
            Connection con, boolean sentakuFlg) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<NtpLabelValueModel> labelList = new ArrayList<NtpLabelValueModel>();
        if (sentakuFlg) {
            labelList.add(new NtpLabelValueModel(textSelect, String.valueOf(-1), "0"));
        }

        //グループリスト取得
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> gpList = gpDao.selectGroupNmListOrderbyClass(usrSid);

        for (GroupModel gmodel : gpList) {
            labelList.add(new NtpLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0"));
        }
        return labelList;
    }

    /**
     * ユーザテンプレート取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     * @return List<NtpTargetModel> 目標リスト
     */
    public NtpTemplateModel getUsrTemplate(Connection con,
                                int userSid) throws Exception {

        NtpTemplateModel templateMdl = null;

        GroupBiz grpBiz = new GroupBiz();

        //選択したユーザの日報テンプレートを取得
        List<Integer> tmpSids = null;
        int selTmpSid = -1;
        Ntp240Dao dao = new Ntp240Dao(con);
        tmpSids = dao.getTemplateSids(userSid);
        if (!tmpSids.isEmpty() && userSid > 0) {
            //ユーザの所属グループを取得
            ArrayList<GroupModel> grpMdlList = null;
            List<Integer> grpSids = new ArrayList<Integer>();
            grpMdlList = grpBiz.getGroupList(con, userSid);
            if (!grpMdlList.isEmpty()) {
                for (GroupModel gpMdl : grpMdlList) {
                    grpSids.add(gpMdl.getGroupSid());
                }
            }

            boolean appFlg = false;

            for (int tmpSid : tmpSids) {

                if (appFlg) {
                    break;
                }

                //テンプレートメンバーを取得
                NtpTmpMemberDao npmDao = new NtpTmpMemberDao(con);
                ArrayList<NtpTmpMemberModel> npmList = null;
                npmList = npmDao.select(tmpSid);
                if (!npmList.isEmpty()) {

                    NtpTmpMemberModel nsmMdl = null;
                    for (int i = 0; i < npmList.size(); i++) {

                        nsmMdl = new NtpTmpMemberModel();
                        nsmMdl = npmList.get(i);

                        if (nsmMdl.getGrpSid() != -1) {
                            //ユーザの所属するグループが適用されているか
                            if (!grpSids.isEmpty()) {
                                if (grpSids.indexOf(nsmMdl.getGrpSid()) != -1) {
                                    appFlg = true;
                                }
                            }
                        } else if (nsmMdl.getUsrSid() != -1) {
                            //ユーザが適用されているか
                            if (userSid == nsmMdl.getUsrSid()) {
                                appFlg = true;
                            }
                        }

                        if (appFlg) {
                            selTmpSid = nsmMdl.getNtmTmpSid();
                            break;
                        }
                    }
                }
            }

            if (selTmpSid != -1) {
                //テンプレート情報を取得
                NtpTemplateDao templateDao = new NtpTemplateDao(con);
                templateMdl = templateDao.select(selTmpSid);
            }
        }
        return templateMdl;
    }


    /**
     * ユーザ適用目標取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tmpSid テンプレートSID
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     * @return List<NtpTargetModel> 目標リスト
     */
    public List<NtpTargetModel> getUsrTmpTarget(Connection con,
                                int tmpSid,
                                int userSid) throws Exception {

        List<NtpTargetModel> targetList = new ArrayList<NtpTargetModel>();

        if (tmpSid != -1) {
            //テンプレートに設定されている目標を取得
            NtpTmpTargetDao nptDao = new NtpTmpTargetDao(con);
            ArrayList<Integer> nptSidList = null;
            nptSidList = nptDao.getNtgSids(tmpSid);
            if (!nptSidList.isEmpty()) {
                //目標情報を取得する
                Ntp230Dao targetDao = new Ntp230Dao(con);
                targetList = targetDao.getTargetList(nptSidList);
            }
        }

        return targetList;
    }

    /**
     * <p>マイグループ又はグループに所属するユーザ情報一覧を取得する。
     * @param con コネクション
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sessionUsrSid セッションユーザSID
     * @param myGroupFlg マイグループ選択フラグ
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(Connection con, int gpSid,
            ArrayList<Integer> usrSids, int sessionUsrSid, boolean myGroupFlg) throws SQLException {

        UserBiz userBiz = new UserBiz();
        return userBiz.getBelongUserList(con, gpSid, usrSids, sessionUsrSid, myGroupFlg);
    }

    /**
     * <br>[機  能] 案件履歴取得
     * <br>[解  説] 最新の５件を取得
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid 選択ユーザSID
     * @return JSONObject 目標データ
     * @throws SQLException SQL実行時例外
     */
    public List<NtpAnkenModel> getAnkenHistoryList(
            Connection con, int usrSid) throws SQLException {

        Ntp040Dao dao = new Ntp040Dao(con);
        List<Integer> ankenSids = null;
        List<NtpAnkenModel> ankenList = null;
        NtpAnkenDao nanDao = new NtpAnkenDao(con);
        NtpAnkenModel nanMdl = null;
        ankenSids = dao.getNtpAnkenHistory(usrSid,
                isNippouAdmin(con, reqMdl__));
        if (ankenSids != null && !ankenSids.isEmpty()) {
            ankenList = new ArrayList<NtpAnkenModel>();
            for (int ankenSid : ankenSids) {
                nanMdl = nanDao.select(ankenSid);
                if (nanMdl != null) {
                    ankenList.add(nanMdl);
                }
            }
        }
        return ankenList;
    }

    /**
     * <br>[機  能] 会社情報履歴取得
     * <br>[解  説] 最新の5件を取得
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid 選択ユーザSID
     * @return JSONObject 目標データ
     * @throws SQLException SQL実行時例外
     */
    public List<Ntp040AddressModel> getCompanyHistoryList(
            Connection con, int usrSid) throws SQLException {

        Ntp040Dao dao = new Ntp040Dao(con);
        List<Ntp040AddressModel> adrList = null;
        List<Ntp040AddressModel> adrMapList = new ArrayList<Ntp040AddressModel>();
        List<Ntp040AddressModel> adrDataList = new ArrayList<Ntp040AddressModel>();
        Ntp040AddressModel adrMdl = null;


        adrList = dao.getNtpAdrHistory(usrSid);

        if (!adrList.isEmpty()) {

            Map<String, Ntp040AddressModel> adrMap
                        = new LinkedHashMap<String, Ntp040AddressModel>();

            for (Ntp040AddressModel mdl : adrList) {
                String key = mdl.getCompanySid() + "_" + mdl.getCompanyBaseSid();
                if (adrMap.get(key) == null) {
                    adrMap.put(key, mdl);
                }
            }

            Set<String> keySet = adrMap.keySet();
            Iterator<String> keyIte = keySet.iterator();
            while (keyIte.hasNext()) {

                adrMdl = new Ntp040AddressModel();
                String adrkey = (String) keyIte.next();
                adrMdl = adrMap.get(adrkey);

                //会社情報
                AdrCompanyDao companyDao = new AdrCompanyDao(con);
                AdrCompanyModel companyModel = companyDao.select(adrMdl.getCompanySid());

                if (companyModel != null) {
                    adrMdl.setCompanySid(companyModel.getAcoSid());
                    adrMdl.setCompanyCode(companyModel.getAcoCode());
                    adrMdl.setCompanyName(companyModel.getAcoName());
                }

                //会社拠点情報
                AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
                AdrCompanyBaseModel companyBaseMdl = new AdrCompanyBaseModel();
                companyBaseMdl = companyBaseDao.select(adrMdl.getCompanyBaseSid());
                if (companyBaseMdl != null) {
                    adrMdl.setCompanyBaseSid(companyBaseMdl.getAbaSid());
                    adrMdl.setCompanyBaseName(companyBaseMdl.getAbaName());
                }
                if (!StringUtil.isNullZeroStringSpace(adrMdl.getCompanyName())) {
                    adrMapList.add(adrMdl);
                }
            }

            if (!adrMapList.isEmpty()) {
                for (int i = 0; i < adrMapList.size(); i++) {
                    if (i >= 10) {
                        break;
                    }
                    adrDataList.add(adrMapList.get(i));
                }
            }
        }
        return adrDataList;
    }

    /**
     * 日報全般のログ出力を行う
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
        outPutLog(map, req, res, opCode, level, value, null, GSConstNippou.NTP_LOG_FLG_NONE);
    }

    /**
     * 日報全般のログ出力を行う（）
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            String fileId,
            int logFlg) {
        outPutLog(map, req, res, opCode, level, value, -1, fileId, logFlg);
    }

    /**
     * 日報全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param ntpSid 日報SID
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            int ntpSid,
            String fileId,
            int logFlg) {

        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }
        //メッセージ日報
        String nippou = "日報";

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstNippou.PLUGIN_ID_NIPPOU);
        logMdl.setLogPluginName(nippou);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstNippou.NTP_LOG_FLG_DOWNLOAD) {
            logMdl.setLogCode("binSid：" + fileId);
        } else if (logFlg == GSConstNippou.NTP_LOG_FLG_PDF) {
            logMdl.setLogCode("ntpSid：" + ntpSid);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * 日報全般のログ出力を行う(追加１)
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param ntpSid 日報SID
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value,
            int ntpSid,
            String fileId,
            int logFlg) {

        int usrSid = -1;
        if (reqMdl__.getSmodel() != null) {
            usrSid = reqMdl__.getSmodel().getUsrsid(); //セッションユーザSID
        }
        //メッセージ日報
        String nippou = "日報";

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstNippou.PLUGIN_ID_NIPPOU);
        logMdl.setLogPluginName(nippou);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstNippou.NTP_LOG_FLG_DOWNLOAD) {
            logMdl.setLogCode("binSid：" + fileId);
        } else if (logFlg == GSConstNippou.NTP_LOG_FLG_PDF) {
            logMdl.setLogCode("ntpSid：" + ntpSid);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        logBiz.outPutLog(logMdl, reqMdl__.getDomain());
    }

    /**
     * 日報全般のログ出力を行う（追加２）
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value,
            String fileId,
            int logFlg) {
        outPutLog(map, opCode, level, value, -1, fileId, logFlg);
    }

    /**
     * 日報全般のログ出力を行う（追加３）
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value) {
        outPutLog(map, opCode, level, value, null, GSConstNippou.NTP_LOG_FLG_NONE);
    }


    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (StringUtil.isNullZeroString(id)) {
            return ret;
        }

        //GsMessage gsMsg = new GsMessage(reqMdl__);
        log__.info("プログラムID==>" + id);

        if (id.equals("jp.groupsession.v2.ntp.ntp030.Ntp030Action")) {
            return "日報タイムライン";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp040.Ntp040Action")) {
            return "日報登録・変更";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp081.Ntp081Action")) {
            return "管理者設定 日報基本設定";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp082.Ntp082Action")) {
            return "管理者設定 自動データ削除設定";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp083.Ntp083Action")) {
            return "管理者設定 手動データ削除設定";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp084.Ntp084Action")) {
            return "管理者設定 インポート";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp084kn.Ntp084knAction")) {
            return "管理者設定 インポート確認";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp085.Ntp085Action")) {
            return "管理者設定 ショートメール通知設定";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp087.Ntp087Action")) {
            return "管理者設定 テンプレート登録・変更";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp091.Ntp091Action")) {
            return "個人設定 日報初期値設定";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp094.Ntp094Action")) {
            return "個人設定 表示設定";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp095.Ntp095Action")) {
            return "個人設定 ショートメール通知設定";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp100.Ntp100Action")) {
            return "日報 検索";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp110.Ntp110Action")) {
            return "日報インポート";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp110kn.Ntp110knAction")) {
            return "日報インポート確認";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp060.Ntp060Action")) {
            return "案件 検索";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp061.Ntp061Action")) {
            return "案件 登録・変更";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp062.Ntp062Action")) {
            return "案件インポート";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp062kn.Ntp062knAction")) {
            return "案件インポート確認";
        }

        if (id.equals("jp.groupsession.v2.ntp.ntp130.Ntp130Action")) {
            return "商品一覧";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp131.Ntp131Action")) {
            return "商品 登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp132.Ntp132Action")) {
            return "商品 インポート";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp132kn.Ntp132knAction")) {
            return "商品 インポート確認";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp133.Ntp133Action")) {
            return "商品カテゴリ 登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp141.Ntp141Action")) {
            return "業種 登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp151.Ntp151Action")) {
            return "プロセス　登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp171.Ntp171Action")) {
            return "活動分類　登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp181.Ntp181Action")) {
            return "活動方法　登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp191.Ntp191Action")) {
            return "顧客　登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp231.Ntp231Action")) {
            return "管理者　目標　登録・編集・削除";
        }
        if (id.equals("jp.groupsession.v2.ntp.ntp240.Ntp240Action")) {
            return "個人　目標　変更";
        }

        return ret;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、システムユーザ or 指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @param defFlg 1項目目に「選択してください」を設定するか true:設定する false:設定しない
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List <LabelValueBean> getNormalUserLabelList(Connection con,
                                                        int groupSid,
                                                        String[] userSidList,
                                                        boolean defFlg)
    throws SQLException {
        List<LabelValueBean> userLabelList = new ArrayList<LabelValueBean>();
        if (defFlg) {

            GsMessage gsMsg = new GsMessage(reqMdl__);
            //選択してください
            String textSelect = gsMsg.getMessage("cmn.select.plz");

            userLabelList.add(0, new LabelValueBean(textSelect, "-1"));
        }

        UserSearchDao usDao = new UserSearchDao(con);
        ArrayList<Integer> userList = new ArrayList<Integer>();
        if (userSidList != null) {
            for (String userSid : userSidList) {
                userList.add(new Integer(userSid));
            }
        }

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();


        //個人設定のユーザ表示順を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        NtpPriConfModel pconf = getNtpPriConfModel(con, sessionUsrSid);
        if (pconf != null) {
            sortMdl.setCscUserSkey1(pconf.getNprSortKey1());
            sortMdl.setCscUserOrder1(pconf.getNprSortOrder1());
            sortMdl.setCscUserSkey2(pconf.getNprSortKey2());
            sortMdl.setCscUserOrder2(pconf.getNprSortOrder2());

        }

        List<CmnUsrmInfModel> addUserList
        = usDao.getBelongUserList(groupSid, userList, false,
                                           sortMdl.getCscUserSkey1(),
                                sortMdl.getCscUserOrder1(),
                                            sortMdl.getCscUserSkey2(),
                                sortMdl.getCscUserOrder2());

        //自分がふくまれている場合は自分を先頭にする
        for (CmnUsrmInfModel usrMdl : addUserList) {
            if (usrMdl.getUsrSid() == sessionUsrSid) {
                userLabelList.add(
                        new LabelValueBean(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei(),
                                        String.valueOf(usrMdl.getUsrSid())));
            }

        }

        for (CmnUsrmInfModel usrMdl : addUserList) {
            if (usrMdl.getUsrSid() != sessionUsrSid) {
            userLabelList.add(
                    new LabelValueBean(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei(),
                                    String.valueOf(usrMdl.getUsrSid())));
            }
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] グループに所属するユーザのうち、指定されたユーザを除いた
     * <br>         表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param groupSid グループSID
     * @param userSidList ユーザSID一覧
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List < LabelValueBean > getMyGroupUserLabelList(
            Connection con, int userSid, int groupSid, String[] userSidList
            ) throws SQLException {

        List<LabelValueBean> userLabelList = new ArrayList<LabelValueBean>();

        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        List<CmnMyGroupMsModel> cmgmList = cmgmDao.getMyGroupMsInfo(userSid,
                groupSid,
                userSidList,
                false);

        String[] users = new String[cmgmList.size()];
        for (int i = 0; i < cmgmList.size(); i++) {
            CmnMyGroupMsModel cmgmMdl = cmgmList.get(i);
            users[i] = String.valueOf(cmgmMdl.getMgmSid());
        }

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        //個人設定のユーザ表示順を取得
        NtpPriConfModel pconf = getNtpPriConfModel(con, userSid);
        if (pconf != null) {
            sortMdl.setCscUserSkey1(pconf.getNprSortKey1());
            sortMdl.setCscUserOrder1(pconf.getNprSortOrder1());
            sortMdl.setCscUserSkey2(pconf.getNprSortKey2());
            sortMdl.setCscUserOrder2(pconf.getNprSortOrder2());

        }

        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
        List<CmnUsrmInfModel> cuiList = cuiDao.getUsersInfList(users, sortMdl);

        //自分が含まれている場合は自分を先頭に
        for (CmnUsrmInfModel cuiMdl : cuiList) {
            if (cuiMdl.getUsrSid() == userSid) {
                userLabelList.add(new LabelValueBean(cuiMdl.getUsiSei() + " " + cuiMdl.getUsiMei(),
                        String.valueOf(cuiMdl.getUsrSid())));
            }

        }

        for (CmnUsrmInfModel cuiMdl : cuiList) {
            if (cuiMdl.getUsrSid() != userSid) {
                userLabelList.add(new LabelValueBean(cuiMdl.getUsiSei() + cuiMdl.getUsiMei(),
                                    String.valueOf(cuiMdl.getUsrSid())));
            }
        }

        return userLabelList;
    }

    /**
     * <br>[機  能] コンボボックスソート設定のソートキーに対応するユーザソートキーを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmbSortKey コンボボックスソート設定のソートキー
     * @return ユーザソートキー
     */
    public static int getSortKey(int cmbSortKey) {
        int sortKey = -1;
        switch (cmbSortKey) {
            case GSConst.USERCMB_SKEY_NAME:
                sortKey = GSConstUser.USER_SORT_NAME;
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sortKey = GSConstUser.USER_SORT_SNO;
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sortKey = GSConstUser.USER_SORT_YKSK;
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sortKey = GSConstUser.USER_SORT_BDATE;
                break;
            case GSConstUser.USER_SORT_BDATE:
                sortKey = GSConstUser.USER_SORT_BDATE;
                break;
            default:
                break;
        }

        return sortKey;
    }

    /**
     * <br>[機  能] 添付ファイルがダウンロード出来るかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param nipSid 日報SID
     * @param sessionUsrSid ユーザSID
     * @param binSid バイナリSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLNtpTemp(Connection con, int nipSid, int sessionUsrSid, Long binSid)
            throws SQLException {

        NtpBinDao binDao = new NtpBinDao(con);
        NtpDataDao ntpDao = new NtpDataDao(con);
        NtpDataModel ntpMdl = ntpDao.select(nipSid);
        if (ntpMdl == null) {
            return false;
        }

        int ntpUsrSid = ntpMdl.getUsrSid();

        //指定ユーザの日報が閲覧可能 且つ バイナリSIDが日報のものである。
        if (isCanInspection(sessionUsrSid, ntpUsrSid, con)
                && binDao.isCheckNtpFile(nipSid, binSid)) {
            return true;
        }
        return false;
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
   public String getEnableSelectGroup(List<NtpLabelValueModel> list,
           String nowSel, String defSel) {
       boolean nowVar = false;
       boolean defVar = false;
       if (list == null || list.size() <= 0) {
           return "";
       }
       for (LabelValueBean label : list) {
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

   /**
    *
    * <br>[機  能]特例アクセスでグループの表示が行えるかを判定
    * <br>[解  説]
    * <br>[備  考]
    * @param sessionUsrSid セッションユーザSID
    * @param gpSid グループSID
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    * @return 表示可能なグループかどうか
    */
   public boolean checkSpAccessDsp(
           int sessionUsrSid, String gpSid, Connection con) throws SQLException {

       boolean gpFlg = false;
       int gpSidNum = Integer.valueOf(gpSid);

       NippouDao dao = new NippouDao(con);
       List<Integer> spGpList
       = dao.getSpAccessGrpList(sessionUsrSid, true, GSConst.SP_AUTH_VIEWONLY);
       //グループの閲覧が許可されているかを確認
       if (spGpList.contains(gpSidNum)) {
           gpFlg = true;
           return gpFlg;
       } else {
           //特例で閲覧が許可されているユーザの所属グループを取得
           List<Integer> spUsrGpList = dao.getGroupBelongSpAccessUser(sessionUsrSid);
           if (spUsrGpList.contains(gpSidNum)) {
               gpFlg = true;
               return gpFlg;
           }
       }

       return gpFlg;
   }
   /**
    * <br>[機  能] 指定の案件を閲覧可能なユーザで絞り込む
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param nanSid 案件SID
    * @param usrSidList ユーザSID
    * @return ユーザ一覧
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getUserSidViewableAnken(
           Connection con,
           int nanSid,
           List<Integer> usrSidList) throws SQLException {
       NtpAnkenDao ankenDao = new NtpAnkenDao(con);
       NtpAnkenModel ankenMdl = ankenDao.select(nanSid);
       if (ankenMdl == null
               || ankenMdl.getNanPermitView() == GSConstNippou.NAP_KBN_ALL) {
           return usrSidList;
       }
       HashSet<Integer> ableSids = new HashSet<Integer>();
       NippouDao ntpDao = new NippouDao(con);
       ableSids.addAll(ntpDao.getNippouAdminUsrSids());
       if (ankenMdl.getNanPermitView() != GSConstNippou.NAP_KBN_ALL) {
           NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con__);
           String[] tantoSids = anMemberDao.select(nanSid);
           for (String sidStr : tantoSids) {
               ableSids.add(Integer.valueOf(sidStr));
           }
       }
       if (ankenMdl.getNanPermitView() == GSConstNippou.NAP_KBN_USERGROUP) {
           NtpAnkenPermitDao anPermitDao = new NtpAnkenPermitDao(con__);
           ableSids.addAll(anPermitDao.selectUsrSids(nanSid, GSConst.SP_AUTH_VIEWONLY));
       }
       List<Integer> ret = new ArrayList<Integer>();
       for (Integer sid : ableSids) {
           if (usrSidList.contains(sid)) {
               ret.add(sid);
           }
       }
       return ret;
   }
   /**
    * <br>[機  能] 指定ユーザの日報を閲覧可能なユーザで絞り込む
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param targetSid 日報ユーザSID
    * @param usrSidList ユーザSID
    * @return ユーザ一覧
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getUserSidViewableNippou(
           Connection con,
           int targetSid,
           List<Integer> usrSidList) throws SQLException {
       NtpSpaccessTargetDao nstDao = new NtpSpaccessTargetDao(con);
       int nsaCnt = nstDao.countNSA(targetSid);
       if (nsaCnt == 0) {
           return usrSidList;
       }
       NtpSpaccessPermitDao nspDao = new NtpSpaccessPermitDao(con);
       HashSet<Integer> ableSids = new HashSet<Integer>();
       ableSids.addAll(nspDao.selectUsrSids(targetSid));
       List<Integer> ret = new ArrayList<Integer>();
       for (Integer sid : ableSids) {
           if (usrSidList.contains(sid)) {
               ret.add(sid);
           }
       }
       return ret;
   }

   /**
    * <br>[機  能] テンポラリディレクトリパスを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエスト情報
    * @param dirId ディレクトリID
    * @param subId サブディレクトリID
    * @return テンポラリディレクトリパス
    */
   public String getTempDir(RequestModel reqMdl, String dirId, String... subId) {
       GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
       String tempDir = tempPathUtil.getTempPath(reqMdl,
                                               GSConstNippou.PLUGIN_ID_NIPPOU,
                                               dirId,
                                               subId);
       return tempDir;
   }

   /**
    * <br>[機  能] テンポラリディレクトリを削除する
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエスト情報
    * @param dirId ディレクトリID
    * @param subId サブディレクトリID
    */
   public void deleteTempDir(RequestModel reqMdl, String dirId, String... subId) {
       GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
       tempPathUtil.deleteTempPath(reqMdl,
                                   GSConstNippou.PLUGIN_ID_NIPPOU,
                                   dirId,
                                   subId);
   }

   /**
    * <br>[機  能] テンポラリディレクトリを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエスト情報
    * @param dirId ディレクトリID
    * @param subId サブディレクトリID
    * @throws IOToolsException テンポラリディレクトリの作成に失敗
    */
   public void createTempDir(RequestModel reqMdl, String dirId, String... subId)
   throws IOToolsException {
       GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
       tempPathUtil.createTempDir(reqMdl,
                               GSConstNippou.PLUGIN_ID_NIPPOU,
                               dirId,
                               subId);
   }

   /**
    * <br>[機  能] テンポラリディレクトリの初期化を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエスト情報
    * @param dirId ディレクトリID
    * @throws IOToolsException テンポラリディレクトリの作成に失敗
    */
   public void clearTempDir(RequestModel reqMdl, String dirId)
   throws IOToolsException {
       //テンポラリディレクトリの削除後、再作成
       deleteTempDir(reqMdl, dirId);
       createTempDir(reqMdl, dirId);
   }

}