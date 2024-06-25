package jp.groupsession.v2.tcd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;
import jp.groupsession.v2.tcd.model.TcdTotalValueModel;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] タイムカードに関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdMainInfoMessage.class);

    /** タイムカード一覧URL */
    public static final String TIMECARD_MAIN_URL = "../timecard/tcd010.do";

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] メインへは不正なデータの編集を促すメッセージを表示します。
     * <br>未開封のメッセージがない場合は表示しません。
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param gsMsg Gsメッセージ
     * @param reqMdl リクエストモデル
     * @return メッセージのリスト
     */
    public List<MainInfoMessageModel> getMessage(Map<String, Object> paramMap,
                   int usid, Connection con, GsMessage gsMsg, RequestModel reqMdl) {
        ArrayList<MainInfoMessageModel> msgList = null;

        StringBuilder linkUrl = new StringBuilder();
        linkUrl.append(TIMECARD_MAIN_URL);

        TimecardBiz tcBiz = new TimecardBiz();
        UDate failDate = null;
        String warnMessage = null;

        boolean commit = false;
        boolean beforeAutoCommit = true;
        try {

            beforeAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);

            //不正データ有無確認
            failDate = tcBiz.getFailDataYmd(usid, con);
            if (failDate != null) {
                String timecard = gsMsg.getMessage("tcd.104");
                String msg2 = gsMsg.getMessage("tcd.106");

                //表示する年月を取得
                linkUrl.append("?year=");
                linkUrl.append(failDate.getYear());
                linkUrl.append("&month=");
                linkUrl.append(failDate.getMonth());
                //メッセージを作成する。
                MainInfoMessageModel model = new MainInfoMessageModel();
                model.setPluginId(GSConst.PLUGINID_TIMECARD);
                model.setPluginName(gsMsg.getMessage("tcd.50"));
                model.setLinkUrl(linkUrl.toString());
                StringBuilder msgBuf = new StringBuilder();
                msgBuf.append(timecard);
                msgBuf.append(
                   gsMsg.getMessage("tcd.105", new String[] {UDateUtil.getSlashYYMD(failDate)}));
                msgBuf.append(msg2);
                model.setMessage(msgBuf.toString());
                model.setOriginalMessage(gsMsg.getMessage(
                        "tcd.105", new String[] {UDateUtil.getSlashYYMD(failDate)}) + msg2);
                CommonBiz cmnBiz = new CommonBiz();
                model.setIcon(cmnBiz.getPluginIconUrl(GSConstTimecard.PLUGIN_ID_TIMECARD,
                        reqMdl.getDomain()));

                msgList = new ArrayList<MainInfoMessageModel>();
                msgList.add(model);
                return msgList;
            }

            TimecardDao tcdDao = new TimecardDao(con);
            int kishuMonth = tcdDao.getKishuMonth();

            //有休警告有無確認
            UDate now = new UDate();
            UDate fdate = new UDate();
            UDate tdate = new UDate();

            //有休日数の取得
            TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(usid, con);
            int fromYear = now.getYear();
            int toYear = now.getYear();

            if (kishuMonth > 1) {
                int nowMonth = now.getMonth();
                if (nowMonth < kishuMonth) {
                    fromYear = now.getYear() - 1;
                } else {
                    toYear = now.getYear() + 1;
                }
            }
            fdate.setYear(fromYear);
            fdate.setMonth(kishuMonth);

            TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl);
            UDate date = new UDate();
            date.setMonth(kishuMonth);
            date.addMonth(11);
            date.setYear(toYear);
            calBiz.setTimeCardCal(fromYear, kishuMonth, admConf.getTacSimebi(), fdate);
            tdate = calBiz.setTimeCardCal(toYear, date.getMonth(), admConf.getTacSimebi(), date);
            TcdTotalValueModel yearMdl
            = calBiz.getTotalValueModel(usid, fdate, tdate,
                    usid, con, reqMdl);

            //警告メッセージの取得
            TimecardYukyuConfBiz yukyuBiz = new TimecardYukyuConfBiz();
            int nendo = yukyuBiz.getNendo(con, new UDate());
            TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
            TcdYukyudataModel tydMdl = tydDao.getYukyuData(usid, nendo);
            warnMessage = tcBiz.getYukyuWarnMessage(con, tydMdl, yearMdl.getYuukyuDays());

            //警告メッセージが有る場合
            if (warnMessage != null) {
                String timecard = gsMsg.getMessage("tcd.104");
                //メッセージを作成する。
                MainInfoMessageModel model = new MainInfoMessageModel();
                model.setPluginId(GSConst.PLUGINID_TIMECARD);
                model.setPluginName(gsMsg.getMessage("tcd.50"));
                model.setLinkUrl(linkUrl.toString());
                StringBuilder msgBuf = new StringBuilder();
                msgBuf.append(timecard);
                msgBuf.append(warnMessage);
                model.setMessage(msgBuf.toString());
                model.setOriginalMessage(warnMessage);
                CommonBiz cmnBiz = new CommonBiz();
                model.setIcon(cmnBiz.getPluginIconUrl(GSConstTimecard.PLUGIN_ID_TIMECARD,
                        reqMdl.getDomain()));

                msgList = new ArrayList<MainInfoMessageModel>();
                msgList.add(model);
                return msgList;
            }

            con.commit();
            commit = true;

        } catch (SQLException e) {
            log__.error("不正データチェックに失敗", e);
        } finally {
            try {
                if (!commit) {
                    con.rollback();
                }
                con.setAutoCommit(beforeAutoCommit);
            } catch (SQLException e) {
                log__.error("タイムカード インフォメーション用メッセージの後処理でエラー発生", e);
            }
        }
        return null;
    }
}
