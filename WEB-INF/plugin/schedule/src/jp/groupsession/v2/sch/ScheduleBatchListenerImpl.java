package jp.groupsession.v2.sch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.base.CmnUsrThemeDao;
import jp.groupsession.v2.cmn.model.PushRequestModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.cmn.websocket.IWebSocketSender;
import jp.groupsession.v2.cmn.websocket.WebSocketSender;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDatausedSumDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.dao.SchExdataDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDatausedSumModel;
import jp.groupsession.v2.sch.model.SchPushListModel;
import jp.groupsession.v2.sch.model.SchPushRequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ScheduleBatchListenerImpl.class);

    /** 前回5分バッチ実行時間*/
    private static ConcurrentHashMap<String, UDate> last5mBatchDate__ =
            new ConcurrentHashMap<>();

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public ScheduleBatchListenerImpl() {
        super();
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        log__.debug("スケジュールバッチ処理開始");
        String pluginName = "スケジュール";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            SchCommonBiz biz = new SchCommonBiz();
            SchAdmConfModel conf = biz.getAdmConfModel(con);
            if (conf.getSadAtdelFlg() == GSConstSchedule.AUTO_DELETE_ON) {
                //自動削除実行

                //基準日を作成
                UDate bdate = new UDate();
                int year = conf.getSadAtdelY();
                int month = conf.getSadAtdelM();
                bdate.addYear(-year);
                bdate.addMonth(-month);
                //削除実行
                biz.deleteOldSchedule(con, bdate);

                //スケジュール拡張情報を削除する。
                biz.deleteSchNoData(con);
            }
            con.commit();

            SchPushListDao pushDao = new SchPushListDao(con);
            UDate last5mBatchDate = last5mBatchDate__.get(param.getDomain());
            if (last5mBatchDate == null) {
                last5mBatchDate = new UDate();
                last5mBatchDate__.putIfAbsent(param.getDomain(), last5mBatchDate);
            }
            //古いスケジュール通知予定リストを削除する。
            pushDao.delete(last5mBatchDate);

            //通知予定リストを追加する。
            UDate frDate = last5mBatchDate;
            UDate toDate = new UDate();
            toDate.addDay(8);

            List<SchPushListModel> pushList = pushDao.createPushListIfNeed(frDate, toDate);
            for (SchPushListModel push : pushList) {
                if (last5mBatchDate.compare(
                        last5mBatchDate,
                        push.getSplReminder()
                        ) == UDate.SMALL) {
                    continue;
                }
                pushDao.insert(push);
            }

            con.commit();

            log__.info("使用データサイズの再集計 開始");

            //使用データサイズの再集計
            SchDatausedSumModel sumMdl = new SchDatausedSumModel();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            long schDataSize = 0;
            SchDataDao schDao = new SchDataDao(con);
            schDataSize += schDao.getTotalDataSize();

            SchBinDao schBinDao = new SchBinDao(con);
            schDataSize += schBinDao.getTotalFileSize();

            SchExdataDao schExDao = new SchExdataDao(con);
            schDataSize += schExDao.getTotalDataSize();

            SchExdataBinDao schExBinDao = new SchExdataBinDao(con);
            schDataSize += schExBinDao.getTotalFileSize();

            sumMdl.setSchDataSize(schDataSize);
            SchDatausedSumDao dataUsedDao = new SchDatausedSumDao(con);
            dataUsedDao.delete();
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getSchDataSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstSchedule.PLUGIN_ID_SCHEDULE, usedDisk);

            con.commit();
            log__.info("使用データサイズの再集計 終了");
            commitFlg = true;


            log__.debug("スケジュールバッチ処理終了");
        } catch (Exception e) {
            log__.error("スケジュールバッチ処理失敗", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } finally {
            if (commitFlg) {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            } else {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }
    }

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception {
    }

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {
        SchPushListDao pushDao = new SchPushListDao(con);
        UDate last5mBatchDate = last5mBatchDate__.get(param.getDomain());
        if (last5mBatchDate == null) {
            last5mBatchDate = new UDate();
            last5mBatchDate.addMinute(-5);
        }

        UDate frDate = last5mBatchDate;
        UDate toDate = new UDate();
        UDate batchTime = new UDate();
        batchTime.setSecond(0);
        batchTime.setMilliSecond(0);

        RequestModel reqMdl = new RequestModel();
        reqMdl.setDomain(param.getDomain());
        reqMdl.setLocale(Locale.JAPANESE);

        Map<SchPushListModel, SchDataModel> pushListMap =
                pushDao.getPushListMap(frDate, toDate);
        Map<Integer, String> imageMap = __getImageMap(con, pushListMap);

        //WEBブラウザへ通知
        for (Entry<SchPushListModel, SchDataModel> push
            : pushListMap.entrySet()) {
            // gsドメイン
            String domain = param.getDomain();
            String imagePath = imageMap.get(push.getKey().getUsrSid());
            String content = __createRimindTimeText(push.getKey().getSplReminderKbn());
            String message = __createPcPushMessage(
                push.getValue(), content, reqMdl, imagePath, batchTime);
            IWebSocketSender sender =  WebSocketSender.getInstance();
            sender.sendText(domain, new int[] {push.getKey().getUsrSid()}, message);
        }

        //アプリへ通知
        __appPushMessage(pushListMap, con, param.getDomain(), batchTime);

        last5mBatchDate__.put(param.getDomain(), toDate);

    }
    /**
     *
     * <br>[機  能] アプリ通知用
     * <br>[解  説]
     * <br>[備  考]
     * @param pushListMap 通知用マップ
     * @param con DBコネクション
     * @param domain ドメイン
     * @param now 現在時
     * @throws SQLException
     */
    private void __appPushMessage(Map<SchPushListModel, SchDataModel> pushListMap,
            Connection con, String domain, UDate now) throws SQLException {
        IPushServiceOperator psOpe = PushServiceOperator.getInstance(con, domain);
        if (psOpe.isUseable()) {
            RequestModel reqMdl = new RequestModel();
            reqMdl.setDomain(domain);
            reqMdl.setLocale(Locale.JAPANESE);
            GsMessage gsMsg = new GsMessage(reqMdl);
            final Map<SchPushListModel, SchDataModel> psPushListMap = pushListMap;
            StringBuilder logSb = new StringBuilder();
            if (psOpe.isUseable()) {
                ArrayList<Integer> usrSidList = new ArrayList<Integer>();
                HashMap<Integer, Integer> usrSidCnt = new HashMap<Integer, Integer>();
                HashMap<Integer, SchDataModel> usrSchMainData = new HashMap<Integer, SchDataModel>();
                HashMap<Integer, Integer> reminderTimeMap = new HashMap<Integer, Integer>();
                for (Entry<SchPushListModel, SchDataModel> push : psPushListMap.entrySet()) {
                    int usrSid = push.getKey().getUsrSid();
                    if (usrSidList.contains(usrSid)) {
                        int cnt = usrSidCnt.get(usrSid);
                        usrSidCnt.put(usrSid, cnt + 1);
                        UDate minDate = usrSchMainData.get(usrSid).getScdFrDate();
                        UDate hikakuDate = push.getValue().getScdFrDate();
                        if (minDate.compare(minDate, hikakuDate) == UDate.SMALL) {
                            usrSchMainData.put(usrSid, push.getValue());
                            reminderTimeMap.put(usrSid, push.getKey().getSplReminderKbn());
                        } else if (minDate.compare(minDate, hikakuDate) == UDate.EQUAL) {
                            if (usrSchMainData.get(usrSid).getScdSid()
                                    > push.getValue().getScdSid()) {
                                usrSchMainData.put(usrSid, push.getValue());
                                reminderTimeMap.put(usrSid, push.getKey().getSplReminderKbn());
                            }
                        }
                    } else {
                        usrSidList.add(usrSid);
                        usrSidCnt.put(usrSid, 1);
                        usrSchMainData.put(usrSid, push.getValue());
                        reminderTimeMap.put(usrSid, push.getKey().getSplReminderKbn());
                    }
                }
                
                ArrayList<PushRequestModel> pushMdlList = new ArrayList<PushRequestModel>();

                for (int usrSid : usrSidList) {
                    SchPushRequestModel pushMdl = new SchPushRequestModel();
                    //通知情報を設定
                    pushMdl.setPushTitle(gsMsg.getMessage("schedule.push.title"));
                    StringBuilder pushText = new StringBuilder();
                    SchDataModel scdMdl = usrSchMainData.get(usrSid);
                
                    String startTimeStr = __createRimindTimeText(reminderTimeMap.get(usrSid));
                   
                    pushText.append(scdMdl.getScdTitle().toString());
                    pushText.append("　");
                    pushText.append(startTimeStr);
                    if (usrSidCnt.get(usrSid) > 1) {
                        int cnt = usrSidCnt.get(usrSid) - 1;
                        pushText.append("\n");
                        pushText.append(
                        gsMsg.getMessage("schedule.push.body.etc",
                        new String[] { String.valueOf(cnt)})
                        );
                    }
                    pushMdl.setPushMessage(pushText.toString());
                    Map<String, String> pushParamMap = new HashMap<>();
                    pushParamMap.put("cmd", "detail");
                    pushParamMap.put("plugin", GSConst.PLUGINID_SCH);
                    pushMdl.setPushParam(pushParamMap);
                    pushMdl.setPushUser(usrSid);
                    pushMdl.setAppId(GSConst.APP_GS_MOBILE);
                    pushMdlList.add(pushMdl);

                    log__.info("通知対象ユーザSID：" + usrSid);
                    logSb.append("スケジュールSID：");
                    logSb.append(scdMdl.getScdSid());
                    logSb.append(" タイトル：");
                    logSb.append(scdMdl.getScdTitle());
                    logSb.append(" 開始日時：");
                    logSb.append(scdMdl.getScdFrDate());
                    logSb.append(" リマインダー区分：");
                    logSb.append(scdMdl.getScdReminder());
                    logSb.append(" 最終更新者：");
                    logSb.append(scdMdl.getScdEuid());
                    logSb.append(" 最終更新日時：");
                    logSb.append(scdMdl.getScdEdate().getTimeStamp2());
                    log__.info(logSb.toString());
                    logSb.delete(0, logSb.length());
                    log__.info("通知内容：" + pushText.toString());
                }
                
                psOpe.sendMessage(con, reqMdl, pushMdlList, GSConst.PLUGINID_SCH);
            }
        }
    }

    /**
     *
     * <br>[機  能] 通知に使用するプラグイン画像の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param data 通知スケジュールデータ
     * @param reqMdl リクエストモデル
     * @return 通知メッセージbody
     * @throws SQLException
     */
    private Map<Integer, String> __getImageMap(
            Connection con, 
            Map<SchPushListModel, SchDataModel> pushListMap) throws SQLException {
        CmnUsrThemeDao cutDao = new CmnUsrThemeDao(con);
        Map<Integer, String> ret = new HashMap<Integer, String>();
        
        List<SchPushListModel> pushList = new ArrayList<>(pushListMap.keySet());
        List<Integer> usrSidList = pushList.stream().map(SchPushListModel::getUsrSid).collect(Collectors.toList());
        Map<Integer, Integer> pathMap = cutDao.select(usrSidList);

        int themeSid;
        String setImage = null;
        for (Entry<Integer, Integer> entry : pathMap.entrySet()) {
            themeSid = entry.getValue();
            if (themeSid >= GSConstCommon.THEME_DEFAULT && themeSid <= GSConstCommon.THEME_YELLOW) {
                setImage = "../schedule/images/classic/icon_notice.png";
            } else {
                setImage = "../schedule/images/original/icon_notice.png";
            }
            ret.put(entry.getKey(), setImage);
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] WEBソケット用通知メッセージjsonの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param data 通知スケジュールデータ
     * @param reqMdl リクエストモデル
     * @param imagePath スケジュール画像パス
     * @param now 実行日時
     * @return 通知メッセージbody
     */
    private String __createPcPushMessage(SchDataModel data, String content, RequestModel reqMdl, String imagePath, UDate now) {
        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("plugin", "schedule");
        jsonData.element("type", "remainder");
        String title = StringUtilHtml.transToHTmlPlusAmparsant(data.getScdTitle());
        jsonData.element("title", title);
        //本文の設定
        jsonData.element("content", content);
        
        //画像の設定
        jsonData.element("image", imagePath);

        String paramUrl = "";
        paramUrl +=  "../" + GSConstSchedule.PLUGIN_ID_SCHEDULE;

        paramUrl += "/sch040.do";
        paramUrl += "?sch010SelectDate=" + data.getScdFrDate().getDateString();
        paramUrl += "&cmd=" + GSConstSchedule.CMD_EDIT;
        paramUrl += "&sch010SchSid=" + data.getScdSid();
        paramUrl += "&sch010SelectUsrSid=" + data.getScdUsrSid();
        paramUrl += "&sch010SelectUsrKbn=" + data.getScdUsrKbn();
        paramUrl += "&sch010DspDate=" + data.getScdFrDate().getDateString();
        paramUrl += "&dspMod=" + GSConstSchedule.DSP_MOD_MAIN;

        jsonData.element("url", paramUrl);


        return jsonData.toString();
    }
    /**
     * 実行時間とスケジュール開始時間の差からリマインダ時間文字列を生成する
     * @param reminderKbn リマインダー区分
     * @return リマインダ時間文字列
     */
    private String __createRimindTimeText(int reminderKbn) {
        String ret = "";
        GsMessage gsMsg = new GsMessage(Locale.JAPANESE);
        if (reminderKbn == GSConstSchedule.REMINDER_TIME_ZERO) {
            ret = gsMsg.getMessage("schedule.push.body.start");
        } else {
            String timeStr = gsMsg.getMessage("schedule.reminder.timesel." + reminderKbn);
            ret = gsMsg.getMessage("schedule.push.body", new String[] {timeStr});
        }
        return ret;
    }    
}


