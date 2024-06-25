package jp.groupsession.v2.enq.biz;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.EnqueteDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.enq.EnqTypeListComparatorImpl;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.dao.EnqAdmConfDao;
import jp.groupsession.v2.enq.dao.EnqAnsMainDao;
import jp.groupsession.v2.enq.dao.EnqAnsSubDao;
import jp.groupsession.v2.enq.dao.EnqCrtUserDao;
import jp.groupsession.v2.enq.dao.EnqDescBinDao;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqPriConfDao;
import jp.groupsession.v2.enq.dao.EnqQueMainDao;
import jp.groupsession.v2.enq.dao.EnqQueSubDao;
import jp.groupsession.v2.enq.dao.EnqSubjectDao;
import jp.groupsession.v2.enq.dao.EnqueteDao;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;
import jp.groupsession.v2.enq.model.EnqAnsMainModel;
import jp.groupsession.v2.enq.model.EnqDescBinModel;
import jp.groupsession.v2.enq.model.EnqMainModel;
import jp.groupsession.v2.enq.model.EnqPriConfModel;
import jp.groupsession.v2.enq.model.EnqTypeListModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケートの共通ビジネスクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqCommonBiz.class);
    /** コネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public EnqCommonBiz() {
    }

    /**
     * <p>コンストラクタ
     * @param con コネクション
     */
    public EnqCommonBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] GS管理者、アンケート管理者ならばtrue、管理者でなければfalseを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return boolean 管理者権限が有り:true, 無し:false
     * @throws SQLException SQL実行時例外
     */
    public static boolean isGsEnqAdmin(RequestModel reqMdl, Connection con) throws SQLException {

        log__.debug("セッションユーザの管理者権限取得処理");

        // セッションユーザ情報を取得する
        BaseUserModel usModel = reqMdl.getSmodel();

        // GS管理者かどうか判別
        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.isPluginAdmin(con, usModel, GSConstEnquete.PLUGIN_ID_ENQUETE);
    }

    /**
     * <br>[機  能] アンケート回答可能フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param enqSid アンケートSID
     * @return 1:回答済、0:未回答、
     *        -1:回答期間切れ、 -9:回答権限無し
     * @throws Exception 実行例外
     */
    public static int canAnsEnquete(RequestModel reqMdl, Connection con, long enqSid)
            throws Exception {

        log__.debug("アンケート回答可能権限取得処理");

        int ret = -9;
        try {
            // セッションユーザ情報を取得する
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            // アンケートに回答可能かどうか判別
            EnqAnsMainModel mdl = new EnqAnsMainModel();
            EnqAnsMainDao dao = new EnqAnsMainDao(con);
            mdl = dao.select(enqSid, sessionUsrSid);

            if (mdl != null) {

                UDate now = new UDate();
                now.setZeroHhMmSs();
                EnqMainDao emDao = new EnqMainDao(con);
                EnqMainModel enqMdl = emDao.select(enqSid);
                int diff = now.compareDateYMD(enqMdl.getEmnResEnd());
                if (mdl.getEamStsKbn() == GSConstEnquete.EAM_STS_KBN_NO && diff < 0) {
                    ret = -1;
                } else {
                    ret = mdl.getEamStsKbn();
                }
            }

        } catch (Exception e) {
            log__.error("アンケート回答_基本の取得に失敗しました。" + e);
            throw e;
        }
        return ret;
    }

    /**
     * <br>[機  能] アンケート編集可能フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param enqSid アンケートSID
     * @return true:編集可能、false:編集不可
     * @throws Exception 実行例外
     */
    public boolean canEditEnquete(RequestModel reqMdl, Connection con, long enqSid)
            throws Exception {

        log__.debug("アンケート編集可能フラグ取得処理");

        boolean ret = false;
        try {
            // セッションユーザ情報を取得する
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            // アンケートに編集可能かどうか判別
            EnqMainModel mdl = new EnqMainModel();
            EnqMainDao dao = new EnqMainDao(con);
            int count = dao.countEditEnq(enqSid, sessionUsrSid);
            if (mdl != null && count > 0) {
                ret = true;
            }

        } catch (Exception e) {
            log__.error("アンケート基本情報の取得に失敗しました。" + e);
            throw e;
        }
        return ret;
    }

    /**
     * <br>[機  能] アンケートが、該当するデータ区分として存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param enqSid アンケートSID
     * @param dataKbn アンケートのデータ区分
     * @return true:編集可能、false:編集不可
     * @throws Exception 実行例外
     */
    public boolean checkExistEnquete(RequestModel reqMdl, Connection con, long enqSid, int dataKbn)
            throws Exception {

        log__.debug("アンケートが、該当するデータ区分として存在するかチェック");

        boolean ret = false;
        try {
            // アンケートに編集可能かどうか判別
            EnqMainModel mdl = new EnqMainModel();
            EnqMainDao dao = new EnqMainDao(con);
            int count = dao.countExistEnq(enqSid, dataKbn);
            if (mdl != null && count > 0) {
                ret = true;
            }

        } catch (Exception e) {
            log__.error("アンケート基本情報の取得に失敗しました。" + e);
            throw e;
        }
        return ret;
    }

    /**
     * <br>[機  能] アンケートの結果公開を閲覧できるかどうかチェックします
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param enqSid アンケートSID
     * @return true:結果の閲覧可能、false:結果の閲覧不可
     * @throws Exception 実行例外
     */
    public boolean canViewResultEnquete(RequestModel reqMdl, Connection con, long enqSid)
            throws Exception {

        log__.debug("アンケートの結果公開閲覧フラグ取得処理");
        boolean ret = false;

        // セッションユーザ情報取得
        int usrSid = reqMdl.getSmodel().getUsrsid();

        try {
            // アンケート基本情報を取得
            EnqMainModel mainMdl = new EnqMainModel();
            EnqMainDao mainDao = new EnqMainDao(con);
            mainMdl = mainDao.select(enqSid);

            // 発信者
            if (usrSid == mainMdl.getEmnEuid()) {
                return true;
            }

            //管理者
            if (isGsEnqAdmin(reqMdl, con)) {
                return true;
            }

            //アンケート回答可能チェック
            if (canAnsEnquete(reqMdl, con, enqSid) == -9) {
                return false;
            }

            //結果公開アンケートチェック
            if (mainMdl.getEmnAnsOpen() != GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {
                return false;
            }

            //結果公開期限チェック
            if (mainMdl.getEmnOpenEndKbn() == GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED) {
                //結果公開開始日 <= 現在日時 <= 公開終了日
                if (UDateUtil.isRange(mainMdl.getEmnAnsPubStr(), mainMdl.getEmnOpenEnd())) {
                    return true;
                }
            } else {
                //結果公開開始日 <= 現在日時
                if (mainMdl.getEmnAnsPubStr().compareDateYMD(new UDate()) != UDate.SMALL) {
                    return true;
                }
            }

        } catch (Exception e) {
            log__.error("アンケートの結果公開閲覧フラグの取得に失敗しました。" + e);
            throw e;
        }
        return ret;
    }

    /**
     * <br>[機  能] 個人設定のアクセス許可権限を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param dspId 画面ID
     * @return true:アクセス可能、false:アクセス不可
     * @throws SQLException SQL実行例外
     * @throws Exception 実行例外
     */
    public boolean checkPriPerm(RequestModel reqMdl, Connection con, int dspId)
        throws SQLException, Exception {

        log__.debug("個人設定 アクセス許可権限の取得処理");

        boolean ret = false;
        EnqAdmConfModel aconf = new EnqAdmConfModel();

        // セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 管理者設定より、アクセス許可権限を取得
        aconf = getAdmConfData(con, sessionUsrSid);
        ret = getPriPermFlg(aconf, dspId);

        return ret;
    }

    /**
     * <br>[機  能] 管理者設定と画面IDから、アクセス許可権限を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param aconf 管理者設定モデル
     * @param dspId 画面ID
     * @return true:アクセス可能、false:アクセス不可
     * @throws Exception 実行例外
     */
    public boolean getPriPermFlg(EnqAdmConfModel aconf, int dspId) throws Exception {

        log__.debug("管理者設定と画面IDから、アクセス許可権限を取得");

        boolean ret = false;
        EnqAdmConfModel wk = aconf;

        switch (dspId) {

            // 個人設定 表示設定
            case GSConstEnquete.DSP_ID_810:
                if (wk.getEacListCntUse() == GSConstEnquete.CONF_LIST_USE_EACH) {
                    ret = true;
                    }
                break;

            // 個人設定 メイン表示設定
            case GSConstEnquete.DSP_ID_820:
                if (wk.getEacMainDspUse() == GSConstEnquete.CONF_MAIN_DSP_USE_EACH) {
                    ret = true;
                    }
                break;

            // 個人設定 メイン
            default:
                if (wk.getEacListCntUse() == GSConstEnquete.CONF_LIST_USE_EACH
                 || wk.getEacMainDspUse() == GSConstEnquete.CONF_MAIN_DSP_USE_EACH) {
                    ret = true;
                    }
                break;
        }

        return ret;
    }

    /**
     * <br>[機  能] 一覧表示最大件数取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid セッションユーザSID
     * @return 最大表示件数
     * @throws SQLException SQL実行例外
     */
    public int getMaxListCnt(Connection con, int usrSid) throws SQLException {

        log__.debug("一覧表示最大件数取得処理");
        int listCnt = 10;

        EnqAdmConfModel aconf = getAdmConfData(con, usrSid);
        if (aconf.getEacListCntUse() == GSConstEnquete.CONF_LIST_USE_LIMIT) {
            listCnt = aconf.getEacListCnt();
        } else {
            EnqPriConfModel pconf = getPriConfData(con, usrSid);
            listCnt = pconf.getEpcListCnt();
        }

        return listCnt;
    }

    /**
     * <br>[機  能] アンケートの管理者設定を取得します
     * <br>[解  説]
     * <br>[備  考] 管理者設定が存在しない場合、初期値で管理者設定を登録し、その初期値を返します。
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return アンケート管理者設定のEnqAdmConfModel
     * @throws SQLException 実行時例外
     */
    public EnqAdmConfModel getAdmConfData(Connection con, int usrSid)
        throws SQLException {

        log__.debug("アンケートの管理者設定取得処理");

        EnqAdmConfDao adao = new EnqAdmConfDao(con);
        EnqAdmConfModel aconf = adao.select();

        if (aconf == null) {
            aconf = getNewEnqAdmConfModel(con, usrSid);
        }

        return aconf;
    }

    /**
     * <br>[機  能] アンケートの個人設定を取得します。
     * <br>[解  説]
     * <br>[備  考] 個人設定が存在しない場合、管理者設定の設定値で個人設定を登録し、返します。
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return アンケート個人設定のEnqPriConfModel
     * @throws SQLException 実行時例外
     */
    public EnqPriConfModel getPriConfData(Connection con, int usrSid) throws SQLException {

        log__.debug("アンケートの個人設定取得処理");

        EnqPriConfDao pdao = new EnqPriConfDao(con);
        EnqPriConfModel pconf = pdao.select(usrSid);

        if (pconf == null) {
            pconf = getNewEnqPriConfModel(con, usrSid);
        }

        return pconf;
    }

    /**
     * <br>[機  能] 管理者設定のEnqAdmConfModelを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return アンケート管理者設定EnqAdmConfModel
     * @throws SQLException 実行時例外
     */
    public EnqAdmConfModel getNewEnqAdmConfModel(Connection con, int usrSid)
        throws SQLException {

        log__.debug("アンケートの管理者設定登録処理");

        EnqAdmConfDao adao = new EnqAdmConfDao(con);
        boolean commitFlg = false;
        boolean svAutoCommit = con.getAutoCommit();

        // 管理者設定の初期値を設定
        EnqAdmConfModel aconf = getDefaultAdmConfModel(usrSid);
        // 登録処理
        try {
            if (svAutoCommit) {
                con.setAutoCommit(false);
            }
            adao.insert(aconf);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("管理者設定の登録に失敗しました。" + e);
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
            if (svAutoCommit) {
                con.setAutoCommit(true);
            }
        }

        return aconf;
    }

    /**
     * <br>[機  能] 個人設定のEnqPriConfModelを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return アンケート個人設定EnqPriConfModel
     * @throws SQLException 実行時例外
     */
    public EnqPriConfModel getNewEnqPriConfModel(Connection con, int usrSid) throws SQLException {

        log__.debug("アンケートの個人設定登録処理");

        EnqPriConfDao pdao = new EnqPriConfDao(con);
        boolean commitFlg = false;
        boolean svAutoCommit = con.getAutoCommit();

        // 個人設定の初期値を設定
        EnqPriConfModel pconf = getDefaultPriConfModel(con, usrSid);

        try {
            if (svAutoCommit) {
                con.setAutoCommit(false);
            }
            pdao.insert(pconf);

            if (svAutoCommit) {
                con.commit();
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("個人設定の登録に失敗しました。" + e);
        } finally {
            if (svAutoCommit && !commitFlg) {
                con.rollback();
            }
            if (svAutoCommit) {
                con.setAutoCommit(true);
            }
        }

        return pconf;
    }

    /**
     * <br>[機  能] アンケート管理者設定のデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから管理者設定が取得できない場合に使用して下さい。
     * @param usrSid ユーザSID
     * @return アンケート管理者設定EnqAdmConfModel
     * @throws SQLException 実行時例外
     */
    public EnqAdmConfModel getDefaultAdmConfModel(int usrSid)
        throws SQLException {

        log__.debug("アンケート管理者設定のデフォルト値取得処理");

        EnqAdmConfModel aconfBean = new EnqAdmConfModel();
        UDate now = new UDate();

        // 作成可能対象者区分
        aconfBean.setEacKbnTaisyo(GSConstEnquete.CONF_TAISYO_ALL);
        // メイン表示フラグ区分
        aconfBean.setEacMainDspUse(GSConstEnquete.CONF_MAIN_DSP_USE_LIMIT);
        // メイン表示フラグ
        aconfBean.setEacMainDsp(GSConstEnquete.CONF_MAIN_DSP_ON);
        // 一覧表示件数区分
        aconfBean.setEacListCntUse(GSConstEnquete.CONF_LIST_USE_LIMIT);
        // 一覧表示件数
        aconfBean.setEacListCnt(GSConstEnquete.CONF_LIST_CNT[2]);
        // 登録者ID
        aconfBean.setEacAuid(usrSid);
        // 登録日時
        aconfBean.setEacAdate(now);
        // 更新者ID
        aconfBean.setEacEuid(usrSid);
        // 更新日時
        aconfBean.setEacEdate(now);

        return aconfBean;
    }

    /**
     * <br>[機  能] アンケート個人設定のデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定が取得できない場合に使用して下さい。初期値には、管理者設定の設定値を使用しています。
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return アンケート管理者設定EnqAdmConfModel
     * @throws SQLException 実行時例外
     */
    public EnqPriConfModel getDefaultPriConfModel(Connection con, int usrSid) throws SQLException {

        log__.debug("アンケート個人設定のデフォルト値取得処理");

        // 管理者設定の設定値を取得
        EnqAdmConfModel aconf = getAdmConfData(con, usrSid);

        EnqPriConfModel pconfBean = new EnqPriConfModel();
        UDate now = new UDate();

        // ユーザSID
        pconfBean.setUsrSid(usrSid);
        // メイン表示フラグ
        pconfBean.setEpcMainDsp(aconf.getEacMainDsp());
        // 一覧表示件数
        pconfBean.setEpcListCnt(aconf.getEacListCnt());
        // 登録者ID
        pconfBean.setEpcAuid(usrSid);
        // 登録日時
        pconfBean.setEpcAdate(now);
        // 更新者ID
        pconfBean.setEpcEuid(usrSid);
        // 更新日時
        pconfBean.setEpcEdate(now);

        return pconfBean;
    }



    /**
     * <br>[機  能] 発信フォルダ削除処理
     * <br>[解  説]
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @param now 現在日
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteSendEnq(Connection con, UDate date, UDate now, int usrSid)
        throws SQLException {

        log__.debug("発信フォルダ削除処理");

        //アンケート情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        EnqMainDao mainDao = new EnqMainDao(con);
        EnqUsedDataBiz usedDataBiz = new EnqUsedDataBiz(con);
        usedDataBiz.insertEnqDataSize(
                mainDao.getDeleteSendEnqSid(date), false);

        // 回答サブ
        delEnqAnsSub(con, date);
        // 回答基本
        delEnqAnsMain(con, date);
        // 設問サブ
        delEnqQueSub(con, date, GSConstEnquete.DATA_KBN_SEND);
        // 設問基本
        delEnqQueMain(con, date, now, usrSid, GSConstEnquete.DATA_KBN_SEND);
        // アンケート対象者
        delEnqSubject(con, date, now, usrSid, GSConstEnquete.DATA_KBN_SEND);
        // アンケート基本
        delEnqMain(con, date, now, usrSid, GSConstEnquete.DATA_KBN_SEND);
    }

    /**
     * <br>[機  能] 草稿フォルダ削除処理
     * <br>[解  説]
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @param now 現在日
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteDraftEnq(Connection con, UDate date, UDate now, int usrSid)
        throws SQLException {

        log__.debug("草稿フォルダ削除処理");

        //アンケート情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        EnqMainDao mainDao = new EnqMainDao(con);
        EnqUsedDataBiz usedDataBiz = new EnqUsedDataBiz(con);
        usedDataBiz.insertEnqDataSize(
                mainDao.getDeleteDraftEnqSid(date), false);

        // 設問サブ
        delEnqQueSub(con, date, GSConstEnquete.DATA_KBN_DRAFT);
        // 設問基本
        delEnqQueMain(con, date, now, usrSid, GSConstEnquete.DATA_KBN_DRAFT);
        // アンケート対象者
        delEnqSubject(con, date, now, usrSid, GSConstEnquete.DATA_KBN_DRAFT);
        // アンケート基本
        delEnqMain(con, date, now, usrSid, GSConstEnquete.DATA_KBN_DRAFT);
    }

    /**
     * <br>[機  能] 回答_基本情報テーブルの削除処理
     * <br>[解  説]
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delEnqAnsMain(Connection con, UDate date) throws SQLException {

        log__.debug("回答_基本情報削除処理");

        EnqAnsMainDao dao = new EnqAnsMainDao(con);
        int cnt = dao.deleteSendEnq(date);

        return cnt;
    }

    /**
     * <br>[機  能] 回答_サブ情報テーブルの削除処理
     * <br>[解  説]
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delEnqAnsSub(Connection con, UDate date) throws SQLException {

        log__.debug("回答_サブ情報削除処理");

        EnqAnsSubDao dao = new EnqAnsSubDao(con);
        int cnt = dao.deleteSendEnq(date);

        return cnt;
    }

    /**
     * <br>[機  能] 設問_基本情報テーブルの削除処理
     * <br>[解  説] バイナリ情報の論理削除後に、設問_基本情報の物理削除を行う。
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @param now 現在日
     * @param usrSid セッションユーザSID
     * @param dataKbn データ区分
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delEnqQueMain(Connection con, UDate date, UDate now, int usrSid, int dataKbn)
        throws SQLException {

        log__.debug("設問_基本情報削除処理");

        EnqQueMainDao dao = new EnqQueMainDao(con);
        int cnt = 0;

        if (dataKbn == GSConstEnquete.DATA_KBN_SEND) {
            dao.updateJkbnFromSendEnq(date, now, usrSid);
            cnt = dao.deleteSendEnq(date);
        } else if (dataKbn == GSConstEnquete.DATA_KBN_DRAFT) {
            dao.updateJkbnFromDraftEnq(date, now, usrSid);
            cnt = dao.deleteDraftEnq(date);
        }

        return cnt;
    }

    /**
     * <br>[機  能] 設問_サブ情報テーブルの削除処理
     * <br>[解  説]
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @param dataKbn データ区分
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delEnqQueSub(Connection con, UDate date, int dataKbn) throws SQLException {

        log__.debug("設問_サブ情報削除処理");

        EnqQueSubDao dao = new EnqQueSubDao(con);
        int cnt = 0;

        if (dataKbn == GSConstEnquete.DATA_KBN_SEND) {
            cnt = dao.deleteSendEnq(date);
        } else if (dataKbn == GSConstEnquete.DATA_KBN_DRAFT) {
            cnt = dao.deleteDraftEnq(date);
        }

        return cnt;
    }

    /**
     * <br>[機  能] アンケート_対象者テーブルの削除処理
     * <br>[解  説]
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @param now 現在日
     * @param usrSid セッションユーザSID
     * @param dataKbn データ区分
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delEnqSubject(Connection con, UDate date, UDate now, int usrSid, int dataKbn)
        throws SQLException {

        log__.debug("アンケート_基本情報削除処理");

        EnqSubjectDao dao = new EnqSubjectDao(con);
        int cnt = 0;

        if (dataKbn == GSConstEnquete.DATA_KBN_SEND) {
            cnt = dao.deleteSendEnq(date);
        } else if (dataKbn == GSConstEnquete.DATA_KBN_DRAFT) {
            cnt = dao.deleteDraftEnq(date);
        }

        return cnt;
    }

    /**
     * <br>[機  能] アンケート_基本情報テーブルの削除処理
     * <br>[解  説] バイナリ情報の論理削除後に、アンケート_基本情報の物理削除を行う
     * <br>[備  考] 手動or自動削除で使用する
     * @param con コネクション
     * @param date 削除する年月期間の閾値
     * @param now 現在日
     * @param usrSid セッションユーザSID
     * @param dataKbn データ区分
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delEnqMain(Connection con, UDate date, UDate now, int usrSid, int dataKbn)
        throws SQLException {

        log__.debug("アンケート_基本情報削除処理");

        EnqMainDao dao = new EnqMainDao(con);
        int cnt = 0;

        if (dataKbn == GSConstEnquete.DATA_KBN_SEND) {
            dao.updateJkbnFromSendEnq(date, now, usrSid);
            cnt = dao.deleteSendEnq(date);
        } else if (dataKbn == GSConstEnquete.DATA_KBN_DRAFT) {
            dao.updateJkbnFromDraftEnq(date, now, usrSid);
            cnt = dao.deleteDraftEnq(date);
        }

        return cnt;
    }

    /**
     * <br>[機  能] アンケート全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param pluginName プラグイン名
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value ログの内容
     */
    public void outPutLog(ActionMapping map, RequestModel reqMdl, String pluginName,
                          String opCode, String level, String value) {
        outPutLog(map, reqMdl, pluginName, getPgName(map.getType(), reqMdl), opCode, level, value,
                null, -1, -1);
    }
    /**
     * <br>[機  能] アンケート全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param pluginName プラグイン名
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value ログの内容
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     * @param enqSid アンケートSID
     */
    public void outPutLog(ActionMapping map, RequestModel reqMdl, String pluginName,
                String opCode, String level, String value, String fileId, int logFlg, long enqSid) {
        outPutLog(map, reqMdl, pluginName, getPgName(map.getType(), reqMdl), opCode, level, value,
                fileId, logFlg, enqSid);
    }

    /**
     * <br>[機  能] アンケート全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param pluginName プラグイン名
     * @param pgName プログラム名
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value ログの内容
     * @param fileId 添付ファイルID
     * @param logFlg ログ出力判別フラグ
     * @param enqSid アンケートSID
     */
    public void outPutLog(ActionMapping map, RequestModel reqMdl, String pluginName, String pgName,
                          String opCode, String level, String value,
                          String fileId, int logFlg, long enqSid) {

        log__.debug("アンケートプラグイン共通のオペレーション出力処理");

        // セッション情報の取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // オペレーションログモデルのセット
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(new UDate());
        logMdl.setUsrSid(sessionUsrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstEnquete.PLUGIN_ID_ENQUETE);
        logMdl.setLogPluginName(pluginName);
        logMdl.setLogPgId(StringUtil.trimRengeString(map.getType(), 100));
        logMdl.setLogPgName(pgName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstEnquete.ENQ_LOG_FLG_DOWNLOAD) {
            logMdl.setLogCode("binSid：" + fileId);
        } else if (logFlg == GSConstEnquete.ENQ_LOG_FLG_PDF) {
            logMdl.setLogCode("PDF出力 enqSid：" + enqSid);
        }

        // オペレーションログの登録
        LoggingBiz logBiz = new LoggingBiz(con__);
        logBiz.outPutLog(logMdl, reqMdl.getDomain());
    }

    /**
     * <br>[機  能] アクションクラス名から、プログラム名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param actName 完全修飾されたアクションクラス名
     * @param reqMdl リクエストモデル
     * @return プログラム名称
     */
    public String getPgName(String actName, RequestModel reqMdl) {

        log__.debug("プログラム名称取得処理");

        String pgName = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        // アクションクラス名のチェック
        if (actName == null || actName.length() < 1) {
            return actName;
        }

        // プログラム名の取得
        if (actName.equals("jp.groupsession.v2.enq.enq010.Enq010Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq010");

        } else if (actName.equals("jp.groupsession.v2.enq.enq110.Enq110Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq110");

        } else if (actName.equals("jp.groupsession.v2.enq.enq110.Enq110Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq110");

        } else if (actName.equals("jp.groupsession.v2.enq.enq110kn.Enq110knAction")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq110");

        } else if (actName.equals("jp.groupsession.v2.enq.enq210.Enq210Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq210");

        } else if (actName.equals("jp.groupsession.v2.enq.enq210kn.Enq210knAction")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq210");

        } else if (actName.equals("jp.groupsession.v2.enq.enq230.Enq230Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq230");

        } else if (actName.equals("jp.groupsession.v2.enq.enq310.Enq310Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq310");

        } else if (actName.equals("jp.groupsession.v2.enq.enq810.Enq810Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq810");

        } else if (actName.equals("jp.groupsession.v2.enq.enq820.Enq820Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq820");

        } else if (actName.equals("jp.groupsession.v2.enq.enq910kn.Enq910knAction")) {
            pgName = gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("cmn.preferences.kn");

        } else if (actName.equals("jp.groupsession.v2.enq.enq920kn.Enq920knAction")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq920");

        } else if (actName.equals("jp.groupsession.v2.enq.enq930.Enq930Action")) {
            pgName = gsMsg.getMessage("cmn.admin.setting") + " " + gsMsg.getMessage(
                    "cmn.display.settings");

        } else if (actName.equals("jp.groupsession.v2.enq.enq950kn.Enq950knAction")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq950");

        } else if (actName.equals("jp.groupsession.v2.enq.enq960kn.Enq960knAction")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq960");

        } else if (actName.equals("jp.groupsession.v2.enq.enq970.Enq970Action")) {
            pgName = gsMsg.getMessage("enq.logmsg.enq970");
        }

        return pgName;
    }

    /**
     * <br>[機  能] アンケート種類一覧を表示順でソートする
     * <br>[解  説]
     * <br>[備  考]
     * @param argList ソート前のアンケート種類一覧
     * @return ソート後のアンケート種類一覧
     */
    public ArrayList<EnqTypeListModel> getSortTypeList(ArrayList<EnqTypeListModel> argList) {

        log__.debug("アンケート種類一覧を「表示順」でソートする処理");

        // ソート前のアンケート種類一覧チェック
        if (argList == null) {
            return argList;
        }

        // 削除された種類名一覧のインデックスを除外する
        ArrayList<EnqTypeListModel> list = new ArrayList<EnqTypeListModel>();
        for (EnqTypeListModel bean : argList) {
            if (bean.getEtpName() != null) {
                list.add(bean);
            }
        }

        // 表示順でソートする
        Collections.sort(list, new EnqTypeListComparatorImpl());

        return list;
    }

    /**
     * <br>[機  能] 一覧表示件数のリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 一覧表示件数リスト
     */
    public ArrayList<LabelValueBean> getListCntLabel() {

        log__.debug("一覧表示件数のリストを取得する処理");

        ArrayList<LabelValueBean> cntLabel = new ArrayList<LabelValueBean>();
        LabelValueBean labelBean = null;

        for (int cnt : GSConstEnquete.CONF_LIST_CNT) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(String.valueOf(cnt));
            labelBean.setValue(String.valueOf(cnt));
            cntLabel.add(labelBean);
        }

        return cntLabel;
    }

    /**
     * <br>[機  能] 初回表示フォルダのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 初回表示フォルダリスト
     */
    public ArrayList<LabelValueBean> getListFolderLabel() {

        log__.debug("初回表示フォルダのリストを取得する処理");

        ArrayList<LabelValueBean> folderLabel = new ArrayList<LabelValueBean>();
        LabelValueBean labelBean = null;

        for (int nIdx = 0; nIdx < GSConstEnquete.CONF_LIST_FOLDER.length; nIdx++) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(GSConstEnquete.CONF_LIST_FOLDER[nIdx]);
            labelBean.setValue(String.valueOf(GSConstEnquete.CONF_LIST_FOLDER_NO[nIdx]));
            folderLabel.add(labelBean);
        }

        return folderLabel;
    }

    /**
     * <br>[機  能] 自動・手動削除の年リストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return 年リスト
     */
    public static ArrayList<LabelValueBean> getYearLabel(RequestModel reqMdl) {

        log__.debug("自動・手動削除の年リスト取得処理");

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // 年リストを生成する
        for (int year : GSConst.DEL_YEAR_DATE) {
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(year)}),
                    Integer.toString(year)));
        }

        return yearLabel;
    }

    /**
     * <br>[機  能] 自動・手動削除の月リストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return 月リスト
     */
    public static ArrayList<LabelValueBean> getMonthLabel(RequestModel reqMdl) {

        log__.debug("自動・手動削除の月リスト取得処理");

        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // 月リストを生成する
        for (int month : GSConst.DEL_MONTH_DATE) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                    Integer.toString(month)));
        }

        return monthLabel;
    }

    /**
     * <br>[機  能] 年コンボ取得処理
     * <br>[解  説]
     * <br>[備  考] 現在日を元に、frYear～toYearまでの年コンボを取得します。
     * @param reqMdl リクエストモデル
     * @return 年コンボ
     */
    public ArrayList<LabelValueBean> getYearLabels(RequestModel reqMdl) {

        UDate now = new UDate();
        int frYear = now.getYear() - GSConstEnquete.YEARCOMBO_RANGE;
        int toYear = now.getYear() + GSConstEnquete.YEARCOMBO_RANGE;
        return getYearLabels(reqMdl, frYear, toYear);
    }

    /**
     * <br>[機  能] 年コンボ取得処理
     * <br>[解  説]
     * <br>[備  考] frYear～toYearまでの年コンボを取得します。
     * @param reqMdl リクエストモデル
     * @param frYear 開始年
     * @param toYear 終了年
     * @return 年コンボ
     */
    public ArrayList<LabelValueBean> getYearLabels(RequestModel reqMdl, int frYear, int toYear) {

        log__.debug("年コンボ取得処理");

        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = frYear; i <= toYear; i++) {
            String strYear = String.valueOf(i);
            labelList.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {strYear}), strYear));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 月コンボ取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return 月コンボ
     */
    public ArrayList<LabelValueBean> getMonthLabels(RequestModel reqMdl) {

        log__.debug("月コンボ取得処理");

        GsMessage gsMsg = new GsMessage(reqMdl);
        String strMonth = gsMsg.getMessage("cmn.month");
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 1; i <= 12; i++) {
            labelList.add(new LabelValueBean(i + strMonth, String.valueOf(i)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 日コンボ取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return 日コンボ
     */
    public ArrayList<LabelValueBean> getDayLabels(RequestModel reqMdl) {

        log__.debug("日コンボ取得処理");

        GsMessage gsMsg = new GsMessage(reqMdl);
        String strDay = gsMsg.getMessage("cmn.day");
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 1; i <= 31; i++) {
            labelList.add(
                    new LabelValueBean(i + strDay, String.valueOf(i)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 添付ファイルを参照可能かチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param enqSid アンケートSID
     * @param binSid バイナリSID
     * @return true:参照可能、false:参照不可
     * @throws SQLException SQL実行例外
     */
    public boolean canUseTempFile(Connection con, long enqSid, long binSid) throws SQLException {

        return canUseTempFile(con, enqSid, binSid, -1);
    }

    /**
     * <br>[機  能] 添付ファイルを参照可能かチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param enqSid アンケートSID
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @return true:参照可能、false:参照不可
     * @throws SQLException SQL実行例外
     */
    public boolean canUseTempFile(Connection con, long enqSid, long binSid, int usrSid)
    throws SQLException {

        log__.debug("添付ファイルを参照可能かチェックする処理");

        boolean ret = false;
        int count = 0;
        EnqAnsMainDao dao = new EnqAnsMainDao(con);

        count = dao.countUseTempFromAns(enqSid, binSid, usrSid);
        if (count > 0) {
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] アンケート情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @return 削除したアンケートの名称
     * @throws SQLException SQL実行例外
     */
    public String deleteEnquete(long emnSid, int userSid, Connection con)
    throws SQLException {

        UDate now = new UDate();

        //アンケート情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        EnqUsedDataBiz usedDataBiz = new EnqUsedDataBiz(con);
        usedDataBiz.insertEnqDataSize(Arrays.asList(emnSid), false);

        //アンケート_基本情報を削除
        EnqMainDao enqMainDao = new EnqMainDao(con);
        EnqMainModel enqMainMdl = enqMainDao.select(emnSid);
        if (enqMainMdl == null) {
            return null;
        }

        String enqName = enqMainMdl.getEmnTitle();
        enqMainDao.removeBinData(emnSid, userSid, now);
        enqMainDao.delete(emnSid);

        //アンケート_対象者を削除
        EnqSubjectDao enqSubjectDao = new EnqSubjectDao(con);
        enqSubjectDao.delete(emnSid);

        //設問情報を削除
        deleteEnqQuestion(emnSid, userSid, con);

        //アンケート_説明添付情報を削除
        deleteEnqDescBin(emnSid, userSid, con);

        return enqName;
    }

    /**
     * <br>[機  能] アンケート 設問情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void deleteEnqQuestion(long emnSid, int userSid, Connection con)
    throws SQLException {
        deleteEnqQuestion(emnSid, userSid, con, true);
    }

    /**
     * <br>[機  能] アンケート 設問情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @param delAns true: 回答情報を削除する false: 回答情報を削除しない
     * @throws SQLException SQL実行例外
     */
    public void deleteEnqQuestion(long emnSid, int userSid, Connection con, boolean delAns)
    throws SQLException {

        UDate now = new UDate();

        //設問_基本情報を削除
        EnqQueMainDao queMainDao = new EnqQueMainDao(con);
        queMainDao.removeBinData(emnSid, userSid, now);
        queMainDao.delete(emnSid);

        //設問_サブ情報を削除
        EnqQueSubDao queSubDao = new EnqQueSubDao(con);
        queSubDao.delete(emnSid);

        if (delAns) {
            //回答_基本情報を削除
            EnqAnsMainDao ansMainDao = new EnqAnsMainDao(con);
            ansMainDao.delete(emnSid);

            //回答_サブ情報を削除
            EnqAnsSubDao ansSubDao = new EnqAnsSubDao(con);
            ansSubDao.delete(emnSid);
        }
    }

    /**
     * <br>[機  能] アンケート 説明添付情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void deleteEnqDescBin(long emnSid, int userSid, Connection con)
    throws SQLException {


        //説明内のバイナリSID一覧取得
        EnqDescBinDao descBinDao = new EnqDescBinDao(con);
        List<EnqDescBinModel> descBinList = descBinDao.selectDescBinList(emnSid);

        if (descBinList != null && !descBinList.isEmpty()) {
            //バイナリ情報を論理削除
            List<Long> binSidList
                = descBinList.stream()
                .map(s -> s.getBinSid())
                .collect(Collectors.toList());
            CmnBinfDao binDao = new CmnBinfDao(con);
            CmnBinfModel cbMdl = new CmnBinfModel();
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(userSid);
            cbMdl.setBinUpdate(new UDate());
            binDao.updateJKbn(cbMdl, binSidList);
        }

        //説明内のファイル情報を削除
        descBinDao.deleteDescBin(emnSid);
    }

    /**
     * <br>[機  能] 指定したユーザがアンケート作成可能者かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return true: 作成可能 false: 作成不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isEnqCrtUser(Connection con, RequestModel reqMdl)
    throws SQLException {
        if (isGsEnqAdmin(reqMdl, con)) {
            return true;
        }

        int userSid = reqMdl.getSmodel().getUsrsid();
        EnqAdmConfModel admConf = getAdmConfData(con, userSid);

        if (admConf.getEacKbnTaisyo() == GSConstEnquete.CONF_TAISYO_ALL) {
            return true;
        }

        EnqCrtUserDao crtUserDao = new EnqCrtUserDao(con);
        return crtUserDao.existUser(userSid);
    }

    /**
     * <br>[機  能] 指定した数値をカンマフォーマットして返す(int型)
     * <br>[解  説]
     * <br>[備  考]
     * @param value 数値
     * @return カンマフォーマットした数値
     */
    public static String toCommaFormat(int value) {
        return StringUtil.toCommaFormat(String.valueOf(value));
    }

    /**
     * <br>[機  能] 指定した数値をカンマフォーマットして返す(Long型)
     * <br>[解  説]
     * <br>[備  考]
     * @param value 数値
     * @return カンマフォーマットした数値
     */
    public static String toCommaFormat(long value) {
        return StringUtil.toCommaFormat(String.valueOf(value));
    }

    /**
     * <br>[機  能] 現在日より指定年月分進めた日付を取得する。
     * <br>[解  説]
     * <br>[備  考] 現在日より過去の日付を取得するときは、引数をマイナスで指定すること。
     * @param year 年
     * @param month 月
     * @param date UDateのインスタンス
     * @return 閾値の日付
     */
    public UDate getThresholdUDate(int year, int month, UDate date) {

        UDate wk = date;
        wk.addYear(year);
        wk.addMonth(month);
        wk.setMaxHhMmSs();

        return wk;
    }

    /**
     * <br>[機  能] 年月日で区切ったyyyy年mm月dd日を取得する
     * <br>[解  説]
     * <br>[備  考] 英語対応した年月日を出力する（mm dd, yyyy）
     * @param reqMdl リクエストモデル
     * @param date 日付
     * @return yyyy年mm月dd日
     */
    public String getStrDate(RequestModel reqMdl, UDate date) {

        if (date == null) {
            return "";
            }
        return getStrDate(reqMdl, date.getStrYear(), date.getStrMonth(), date.getStrDay());
    }


    /**
     * <br>[機  能] 年月日で区切ったyyyy年mm月dd日を取得する
     * <br>[解  説]
     * <br>[備  考] 英語対応した年月日を出力する（mm dd, yyyy）
     * @param reqMdl リクエストモデル
     * @param argYear 年
     * @param argMonth 月
     * @param argDay 日
     * @return yyyy年mm月dd日
     */
    public String getStrDate(RequestModel reqMdl, int argYear, int argMonth, int argDay) {

        String year = String.format("%04d", argYear);
        String month = String.format("%02d", argMonth);
        String day = String.format("%02d", argDay);

        return getStrDate(reqMdl, year, month, day);
    }

    /**
     * <br>[機  能] 年月日で区切ったyyyy年mm月dd日を取得する
     * <br>[解  説]
     * <br>[備  考] 英語対応した年月日を出力する（mm dd, yyyy）
     * @param reqMdl リクエストモデル
     * @param year 年
     * @param month 月
     * @param day 日
     * @return yyyy年mm月dd日
     */
    public String getStrDate(RequestModel reqMdl, String year, String month, String day) {

        String ret = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (StringUtil.isNullZeroString(year) || StringUtil.isNullZeroString(month)
         || StringUtil.isNullZeroString(day)) {
            ret = "";
        } else {
            String[] date = new String[]{year, month, day};
            ret = gsMsg.getMessage("cmn.date4", date);
        }

        return ret;
    }

    /**
     * <br>[機  能] アンケート 通知メール情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param emnSid アンケートSID
     * @param sendKbn 送信区分 (全対象者 or 未回答者のみ)
     * @return アンケートタイトル
     * @throws Exception 実行例外
     */
    public String sendSmailInfo(
            RequestModel reqMdl,
            Connection con,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pluginConfig,
            int userSid,
            long emnSid,
            int sendKbn
            ) throws Exception {

        EnqueteDao enqDao = new EnqueteDao(con);
        EnqueteDataModel enqData = enqDao.getEnqueteData(emnSid, sendKbn);

        //宛先
        List<Integer> toList = new ArrayList<Integer>();
        List<String> askList = enqData.getAnswerList();
        if (askList != null && !askList.isEmpty()) {
            for (String askSid : askList) {
                toList.add(new Integer(askSid));
            }
        }

        //件名
        String enqTitle = enqData.getTitle();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String title = "[GS " + gsMsg.getMessage("enq.plugin") + "] " + enqTitle;

        //本文
        String rc = "\r\n";
        StringBuilder sb = new StringBuilder("");
        sb.append("Group Session " + gsMsg.getMessage("enq.enq010.02"));
        sb.append(rc + rc);
        sb.append(gsMsg.getMessage("cmn.type2") + ": "
                    + NullDefault.getString(enqData.getTypeName(), ""));
        sb.append(rc);
        sb.append(gsMsg.getMessage("cmn.title") + ": " + enqData.getTitle());
        sb.append(rc);
        sb.append(gsMsg.getMessage("enq.25") + ": " + enqData.getSender());
        sb.append(rc + rc);
        sb.append("URL: " + __createEnqueteUrl(reqMdl, emnSid));
        sb.append(rc + rc);
        sb.append(gsMsg.getMessage("enq.19") + ": "
                + UDateUtil.getSlashYYMD(enqData.getAnsLimitDate()));
        sb.append(rc);
        sb.append(gsMsg.getMessage("enq.20") + ": "
                + UDateUtil.getSlashYYMD(enqData.getPubStartDate())
                + " - "
                + UDateUtil.getSlashYYMD(enqData.getPubEndDate())
                );
        sb.append(rc);

        String body = sb.toString();

        //ショートメール送信用モデルを作成する。
        //
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO, BCC
        smlModel.setSendToUsrSidArray(toList);
        //タイトル
        smlModel.setSendTitle(title);
        //本文
        smlModel.setSendBody(body);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl);
        sender.execute();

        return enqTitle;
    }

    /**
     * <br>[機  能] アンケート 通知メールに記載するURLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param emnSid アンケートSID
     * @return URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createEnqueteUrl(RequestModel reqMdl, long emnSid)
    throws UnsupportedEncodingException {

        String enqUrl = null;
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        //スレッドのURLを作成
        try {

            String paramUrl =  "/" + urlBiz.getContextPath(reqMdl);

            paramUrl += "/enquete/enq110" + ".do";
            paramUrl += "?ansEnqSid=" + emnSid;
            paramUrl += "&cmd=enqSmail";
            enqUrl = urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

        return enqUrl;
    }

    /**
    *
    * <br>[機  能] 設問情報数値入力情報を加工する
    * <br>[解  説] 例：1.00000を1, 1.50000を1.5にする
    * <br>[備  考]
    * @param num 数値入力情報
    * @return String
    */
   public String processNumber(String num) {

       Pattern pFirst = Pattern.compile("0{5}$");
       Matcher mFirst = pFirst.matcher(num);
       Pattern pSecond = Pattern.compile("0{4}$");
       Matcher mSecond = pSecond.matcher(num);
       Pattern pThird = Pattern.compile("0{3}$");
       Matcher mThird = pThird.matcher(num);
       Pattern pFourth = Pattern.compile("0{2}$");
       Matcher mFourth = pFourth.matcher(num);
       Pattern pFifth = Pattern.compile("0$");
       Matcher mFifth = pFifth.matcher(num);

       if (num.indexOf(".") == -1) {
           return num;
       }
       if (mFirst.find()) {
           //小数点以下5桁すべてが0の場合は小数点以下が表示されないよう加工
           //例：1.00000→1
           //小数点を非表示にする
           int index = num.indexOf(".");
           return num.substring(0, index);
       }
       if (mSecond.find()) {
           //小数点以下5桁目が0の場合は小数点以下5桁目が表示されないよう加工
           //例：1.10000→1.1
           int index = num.indexOf(".") + 2;
           return num.substring(0, index);
       }
       if (mThird.find()) {
           //小数点以下5桁目が0の場合は小数点以下5桁目が表示されないよう加工
           //例：1.11000→1.11
           int index = num.indexOf(".") + 3;
           return num.substring(0, index);
       }
       if (mFourth.find()) {
           //小数点以下5桁目が0の場合は小数点以下5桁目が表示されないよう加工
           //例：1.11110→1.1111
           int index = num.indexOf(".") + 4;
           return num.substring(0, index);
       }
       if (mFifth.find()) {
           //小数点以下5桁目が0の場合は小数点以下5桁目が表示されないよう加工
           //例：1.11110→1.1111
           int index = num.indexOf(".") + 5;
           return num.substring(0, index);
       }
       return num;
   }
}
