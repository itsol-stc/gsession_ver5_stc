package jp.groupsession.v2.anp.anp020;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpJdataModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 安否状況入力画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp020Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp020Model パラメータモデル
     * @param con DBコネクション
     * @param accessUsrSid アクセスユーザSID
     * @param isAdmin 管理者権限があるかどうか
     * @return 進行中の安否確認がある場合、true
     * @throws Exception 実行例外
     */
    public boolean setInitData(Anp020ParamModel anp020Model, Connection con,
                                int accessUsrSid, boolean isAdmin)
                throws Exception {

        //ユーザ情報を取得
        int usrSid = Integer.valueOf(anp020Model.getUserSid());
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel udata = usiDao.getUsersInf(usrSid);
        if (udata == null) {
            log__.debug("ユーザ情報が取得できませんでした。usrSid = " + usrSid);
            return false;
        }

        //安否確認配信データ取得
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hdata = hDao.select(Integer.valueOf(anp020Model.getAnpiSid()));

        //安否状況データを取得
        AnpJdataModel jdata = __getAnpiJokyoData(anp020Model, con, hdata);
        if (jdata == null) {
            return false;
        }

        //フォーム入力値セット
        anp020Model.setAnp020EmployeeNo(udata.getUsiSyainNo());
        anp020Model.setAnp020Name(NullDefault.getString(udata.getUsiSei(), "") + " "
                         + NullDefault.getString(udata.getUsiMei(), ""));
        anp020Model.setAnp020Kana(NullDefault.getString(udata.getUsiSeiKn(), "") + " "
                         + NullDefault.getString(udata.getUsiMeiKn(), ""));
        anp020Model.setAnp020PhotoFileSid(udata.getBinSid());
        anp020Model.setAnp020PhotoDspFlg(udata.getUsiPictKf());
        anp020Model.setAnp020HaisinDate(__getDspDate(jdata.getApdHdate()));
        anp020Model.setAnp020ReplyDate(__getDspDate(jdata.getApdRdate()));
        anp020Model.setAnp020usrJkbn(udata.getUsrJkbn());
        anp020Model.setAnp020usrUkoFlg(udata.getUsrUkoFlg());

        //訓練モードフラグ
        if (hdata != null) {
            anp020Model.setAnp010KnrenFlg(hdata.getAphKnrenFlg());
        }

        //緊急連絡先は、本人か管理者のみ参照可能にする
        int urgentflg = 0;
        if (!NullDefault.getString(anp020Model.getRmode(), "").equals(GSConstAnpi.REMOTE_MODE)) {
            if (accessUsrSid == usrSid || isAdmin) {
                //個人設定データを取得
                AnpiCommonBiz anpBiz = new AnpiCommonBiz();
                AnpPriConfModel priConf = anpBiz.getAnpPriConfModel(con, usrSid);
                anp020Model.setAnp020UrgentMail(priConf.getAppMailadr());
                anp020Model.setAnp020UrgentTelNo(priConf.getAppTelno());
                urgentflg = 1;
            }
        }
        anp020Model.setAnp020UrgentDspFlg(String.valueOf(urgentflg));

        return true;
    }

    /**
     * <br>[機  能] 安否状況データを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp020Model パラメータモデル
     * @param con DBコネクション
     * @return 進行中の安否確認がある場合、true
     * @throws Exception 実行例外
     */
    public boolean setAnpiData(Anp020ParamModel anp020Model, Connection con)
                throws Exception {

        //安否確認配信データ取得
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hdata = hDao.select(Integer.valueOf(anp020Model.getAnpiSid()));

        //安否状況データを取得
        AnpJdataModel jdata = __getAnpiJokyoData(anp020Model, con, hdata);
        if (jdata == null) {
            return false;
        }

        //フォーム入力値セット
        anp020Model.setAnp020JokyoFlg(String.valueOf(jdata.getApdJokyoFlg()));
        anp020Model.setAnp020PlaceFlg(String.valueOf(jdata.getApdPlaceFlg()));
        anp020Model.setAnp020SyusyaFlg(String.valueOf(jdata.getApdSyusyaFlg()));
        anp020Model.setAnp020Comment(jdata.getApdComment());

        return true;
    }

    /**
     * <br>[機  能] ユーザアクセス日時の更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param anp020Model パラメータモデル
     * @param con DBコネクション
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAccess(Anp020ParamModel anp020Model, Connection con, int usrSid)
                        throws SQLException {

        boolean commitFlg = false;
        UDate now = new UDate();

        try {
            con.setAutoCommit(false);

            AnpJdataModel bean = new AnpJdataModel();
            bean.setAphSid(Integer.valueOf(anp020Model.getAnpiSid()));
            bean.setUsrSid(Integer.valueOf(anp020Model.getUserSid()));
            bean.setApdCdate(now);
            bean.setApdEuid(usrSid);
            bean.setApdEdate(now);

            AnpJdataDao dao = new AnpJdataDao(con);
            dao.updateUserAccess(bean);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 安否状況データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp020Model パラメータモデル
     * @param con DBコネクション
     * @param hdata 安否確認配信データモデル
     * @return 進行中の安否確認がある場合、true
     * @throws Exception 実行例外
     */
    private AnpJdataModel __getAnpiJokyoData(
            Anp020ParamModel anp020Model, Connection con, AnpHdataModel hdata)
                throws Exception {

        int anpSid = Integer.valueOf(anp020Model.getAnpiSid());
        int usrSid = Integer.valueOf(anp020Model.getUserSid());

        //配信データを確認
        if (hdata == null) {
            log__.debug("安否配信データが取得できませんでした。aphSid = " + anpSid);
            return null;
        }
        if (hdata.getAphEndFlg() == GSConstAnpi.ANPI_END_FLG) {
            log__.debug("指定された安否配信データは完了しています。aphSid = " + anpSid);
            return null;
        }

        //安否状況データを取得
        AnpJdataDao jDao = new AnpJdataDao(con);
        AnpJdataModel jdata = jDao.selectRegisteredUser(anpSid, usrSid);
        if (jdata == null) {
            log__.debug("安否状況データが取得できませんでした。aphSid = " + anpSid);
            return null;
        }

        return jdata;
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