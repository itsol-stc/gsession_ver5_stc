package jp.groupsession.v2.sch.sch085;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スケジュール 管理者設定 メンバー表示順設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch085Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch085Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch085Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch085ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Sch085ParamModel paramMdl,
                  Connection con) throws SQLException {
        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel aconf = biz.getAdmConfModel(con);
        SchPriConfModel pconf = new SchPriConfModel();

        int sortKbn = aconf.getSadSortKbn();
        paramMdl.setSch085MemDspKbn(sortKbn);

        //管理者権限設定
        if (sortKbn == GSConstSchedule.MEM_DSP_ADM) {
            //ソート1
            paramMdl.setSch085AdSortKey1(aconf.getSadSortKey1());
            paramMdl.setSch085AdSortOrder1(aconf.getSadSortOrder1());

            //ソート2
            paramMdl.setSch085AdSortKey2(aconf.getSadSortKey2());
            paramMdl.setSch085AdSortOrder2(aconf.getSadSortOrder2());

        //各ユーザ権限設定
        } else {
            pconf = biz.getSchPriConfModel(con, 0);
            //ソート1
            paramMdl.setSch085AdSortKey1(pconf.getSccSortKey1());
            paramMdl.setSch085AdSortOrder1(pconf.getSccSortOrder1());

            //ソート2
            paramMdl.setSch085AdSortKey2(pconf.getSccSortKey2());
            paramMdl.setSch085AdSortOrder2(pconf.getSccSortOrder2());
        }

        //デフォルト表示グループ
        paramMdl.setSch085DefGroup(aconf.getSadDspGroup());
    }

    /**
     * <br>[機  能] 設定された管理者設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch085ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setAdmconfSetting(Sch085ParamModel paramMdl,
                                                int sessionUserSid, Connection con)
    throws SQLException {

        SchAdmConfDao sacDao = new SchAdmConfDao(con);
        SchPriConfDao spcDao = new SchPriConfDao(con);

        SchCommonBiz biz = new SchCommonBiz(reqMdl__);

        SchPriConfModel usrDefo = new SchPriConfModel();

        //ユーザ権限で設定
        if (paramMdl.getSch085MemDspKbn() == GSConstSchedule.MEM_DSP_USR) {
            usrDefo = __getDefaulUsrConfModel(0, con, paramMdl, sessionUserSid);
        }

        //DBより設定情報を取得。なければデフォルト値とする。
        SchAdmConfModel aconf = biz.getAdmConfModel(con);
        UDate now = new UDate();

        //管理者が設定
        //ソート1
        aconf.setSadSortKey1(paramMdl.getSch085AdSortKey1());
        aconf.setSadSortOrder1(paramMdl.getSch085AdSortOrder1());
        //ソート2
        aconf.setSadSortKey2(paramMdl.getSch085AdSortKey2());
        aconf.setSadSortOrder2(paramMdl.getSch085AdSortOrder2());

        //ソート編集権限
        aconf.setSadSortKbn(paramMdl.getSch085MemDspKbn());

        //デフォルト表示グループ
        aconf.setSadDspGroup(paramMdl.getSch085DefGroup());

        aconf.setSadEuid(sessionUserSid);
        aconf.setSadEdate(now);

        boolean commitFlg = false;
        try {

            //各ユーザのデフォルトとなる設定値を登録する
            if (paramMdl.getSch085MemDspKbn() == GSConstSchedule.MEM_DSP_USR) {
                int count = spcDao.updateMemDspUsrNotExe(usrDefo);
                if (count <= 0) {
                    usrDefo.setSccAuid(sessionUserSid);
                    usrDefo.setSccAdate(now);
                    spcDao.insert(usrDefo);
                }
            }

            int count2 = sacDao.updateMemDsp(aconf);
            if (count2 <= 0) {
                aconf.setSadAuid(sessionUserSid);
                aconf.setSadAdate(now);
                sacDao.insert(aconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }

    /**
     * <br>[機  能] 各ユーザが指定する選択した場合のモデルを返します。
     * <br>[解  説] 新規ユーザ作成時などに適用されます。
     * <br>[備  考]
     * @param usrSid 変更するユーザSID
     * @param con DBコネクション
     * @param paramMdl Sch085ParamModel
     * @param sessionSid セッションSID
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    private SchPriConfModel __getDefaulUsrConfModel(
            int usrSid,
            Connection con,
            Sch085ParamModel paramMdl,
            int sessionSid)
    throws SQLException {

        SchPriConfModel confBean = new SchPriConfModel();

        //ユーザSID
        confBean.setUsrSid(usrSid);
        //開始時間 9時で作成
        UDate frDate = new UDate();
        frDate.setHour(GSConstSchedule.DF_FROM_HOUR);
        frDate.setMinute(GSConstSchedule.DF_FROM_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        confBean.setSccFrDate(frDate);
        //終了時間 18時で作成
        UDate toDate = new UDate();
        toDate.setHour(GSConstSchedule.DF_TO_HOUR);
        toDate.setMinute(GSConstSchedule.DF_TO_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        confBean.setSccToDate(toDate);
        //デフォルト表示グループ
        confBean.setSccDspGroup(0);
        //一覧表示件数
        confBean.setSccDspList(GSConstSchedule.DEFAULT_LIST_CNT);
        //自動リロード
        confBean.setSccReload(GSConstSchedule.AUTO_RELOAD_10MIN);
        //表示開始曜日
        confBean.setSccIniWeek(GSConstSchedule.CHANGE_WEEK_SUN);

        confBean.setSccEuid(sessionSid);
        confBean.setSccEdate(new UDate());

        //初期値 公開フラグ
        confBean.setSccIniPublic(GSConstSchedule.DSP_PUBLIC);
        //初期値 編集権限
        confBean.setSccIniEdit(GSConstSchedule.EDIT_CONF_NONE);
        //初期値 タイトルカラー
        confBean.setSccIniFcolor(GSConstSchedule.DF_BG_COLOR);
        //初期値 開始時刻 9時
        confBean.setSccIniFrDate(frDate);
        //初期値 終了時刻 18時
        confBean.setSccIniToDate(toDate);

        //ソート1
        confBean.setSccSortKey1(paramMdl.getSch085AdSortKey1());
        confBean.setSccSortOrder1(paramMdl.getSch085AdSortOrder1());
        //ソート2
        confBean.setSccSortKey2(paramMdl.getSch085AdSortKey2());
        confBean.setSccSortOrder2(paramMdl.getSch085AdSortOrder2());
        //一覧表示件数
        confBean.setSccDspList(GSConstSchedule.DEFAULT_LIST_CNT);
        //マイグループ
        confBean.setSccDspMygroup(0);
        //メンバー表示順編集
        confBean.setSccSortEdit(GSConstSchedule.MEM_EDIT_NOT_EXECUTE);
        return confBean;
    }
}
