package jp.groupsession.v2.rsv.rsv020;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvWeekModelBeforConv;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvHidDayModel;
import jp.groupsession.v2.rsv.model.RsvHidGroupModel;
import jp.groupsession.v2.rsv.model.RsvHidModel;
import jp.groupsession.v2.rsv.model.RsvHidSisetuModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.pdf.RsvNikPdfModel;
import jp.groupsession.v2.rsv.pdf.RsvNikPdfUtil;
import jp.groupsession.v2.rsv.rsv010.Rsv010Biz;
import jp.groupsession.v2.rsv.rsv010.Rsv010ParamModel;
import jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約一覧 日間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv020Biz extends Rsv010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv020Biz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv020Biz(RequestModel reqMdl, Connection con) {
        super(reqMdl, con);
    }

    /**
     * <br>[機  能] 施設予約情報一覧[日間]をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setYoyakuDay(Rsv020ParamModel paramMdl) throws SQLException {

        log__.debug("日間カレンダーと予約情報設定");
        RsvCommonBiz biz = new RsvCommonBiz();
        //表示開始日
        String strDspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());

        paramMdl.setRsvDspFrom(strDspDate);

        //一覧のヘッダに表示する年月を設定
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);
        paramMdl.setRsvDispYm(__getHeaderDate(dspDate));

        CommonBiz cmnBiz = new CommonBiz();
        //セッション情報を取得
        HttpSession session = reqMdl_.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();
        //一覧のヘッダに表示する六曜を設定
        if (cmnBiz.getRokuyoDspKbn(con_, sessionUsrSid)) {
            paramMdl.setRsvDispRokuyou(cmnBiz.getRokuyou(dspDate));
        }

        int hourFr = GSConstReserve.DEFAULT_START_HOUR;
        int hourTo = GSConstReserve.DEFAULT_END_HOUR;

        //管理者設定取得
        RsvAdmConfDao admconfDao = new RsvAdmConfDao(con_);
        RsvAdmConfModel admMdl = admconfDao.select();

        //日間表示時間帯区分 = 管理者強制 の場合、管理者設定の時間帯を使用する
        if (admMdl != null && admMdl.getRacDtmKbn() == GSConstReserve.RAC_DTMKBN_ADM) {
            hourFr = admMdl.getRacDtmFr();
            hourTo = admMdl.getRacDtmTo();

        } else {
            //個人設定取得
            RsvUserModel rsvUsr = _isRsvUser(reqMdl_, con_);
            if (rsvUsr != null) {
                //個人設定がされている場合は表示時間を取得
                hourFr = rsvUsr.getRsuDtmFr();
                hourTo = rsvUsr.getRsuDtmTo();
            } else if (admMdl != null) {
                //個人設定が存在しない場合は管理者設定の表示時間を取得
                hourFr = admMdl.getRacDtmFr();
                hourTo = admMdl.getRacDtmTo();
            }
        }

        paramMdl.setRsv020FromHour(String.valueOf(hourFr));
        paramMdl.setRsv020ToHour(String.valueOf(hourTo));
        int hourDivCount = __getHourDivCount();
        int colSpan =
            (hourTo - hourFr + 1) * hourDivCount + 2;
        paramMdl.setRsv020ColSpan(colSpan);

        //タイムチャート作成
        paramMdl.setRsv020TimeChartList(__getTimeChart(paramMdl, hourFr, hourTo));

        //施設毎の予約情報取得
        if (paramMdl.getRsv010SiborikomiFlg() == 0) {
            paramMdl.setRsv020DaylyList(
                    __getDaylyList(
                            paramMdl,
                            dspDate.cloneUDate(),
                            hourFr,
                            hourTo));
        } else {
            paramMdl.setRsv020DaylyList(
                    __getDaylySiborikomiList(
                            paramMdl,
                            dspDate.cloneUDate(),
                            hourFr,
                            hourTo));
        }

        //予約時間間隔取得
        paramMdl.setRsv020HourDivCount(hourDivCount);


    }

    /**
     * <br>[機  能] 自動リロード時間を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Rsv020ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void set020Reloadtime(Connection con, Rsv020ParamModel paramMdl, int sessionUsrSid)
    throws SQLException {

        paramMdl.setRsv020Reload(getReloadtime(con, sessionUsrSid));

    }

    /**
     * <br>[機  能] タイムチャートを作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @param hourFr 開始時間
     * @param hourTo 終了時間
     * @return timeList タイムチャート
     */
    private ArrayList<String> __getTimeChart(Rsv020ParamModel paramMdl,
                                              int hourFr,
                                              int hourTo) {

        ArrayList<String> timeList = new ArrayList<String>();
        for (int i = hourFr; i <= hourTo; i++) {
            timeList.add(String.valueOf(i));
        }
        return timeList;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @param dspDate 指定日付
     * @param hourFr 開始時間
     * @param hourTo 終了時間
     * @return ArrayList 予約リスト
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getDaylyList(Rsv020ParamModel paramMdl,
                                                      UDate dspDate,
                                                      int hourFr,
                                                      int hourTo)
        throws SQLException {

        BaseUserModel usModel = reqMdl_.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        CommonBiz cmnBiz = new CommonBiz();
        boolean admFlg = cmnBiz.isPluginAdmin(con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        //検索条件 開始時間
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(hourFr);
        fromDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        fromDate.setSecond(GSConstReserve.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了時間
        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(hourTo);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

        //予約情報取得
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con_);
        ArrayList<RsvSisetuModel> ret = new ArrayList<RsvSisetuModel>();

        //選択してくださいを選択時は施設を表示しない
        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_PLEASE_CHOICE) {
            return ret;
        }


        ArrayList<RsvWeekModelBeforConv> daylyList = null;

        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            //全ての場合

            //アクセス可能グループ一覧を取得する。
            List<RsvSisGrpModel> rsgMdlList = null;
            if (admFlg) {
                daylyList =
                    dao.getYrkList(paramMdl.getRsvSelectedGrpSid(), -1, fromDate, toDate);

            } else {
                rsgMdlList = rsgDao.getCanReadData(sessionUsrSid);

                List<Integer> canAccessGrp = new ArrayList<Integer>();
                if (rsgMdlList != null && rsgMdlList.size() > 0) {
                    for (RsvSisGrpModel model : rsgMdlList) {
                        canAccessGrp.add(model.getRsgSid());
                    }
                }

                //アクセス可能なグループが存在する場合のみ検索を行う
                if (!canAccessGrp.isEmpty()) {
                    daylyList = dao.getYrkList(canAccessGrp, -1, fromDate, toDate);
                } else {
                    daylyList = new ArrayList<RsvWeekModelBeforConv>();
                }
            }


        } else {
            //グループ指定の場合

            //施設グループ閲覧権限
            if (!_isReadRsvGrp(con_, reqMdl_, paramMdl.getRsvSelectedGrpSid())) {
                paramMdl.setRsvSelectedGrpSid(GSConstReserve.COMBO_PLEASE_CHOICE);
                return ret;
            }

            daylyList = dao.getYrkList(paramMdl.getRsvSelectedGrpSid(), -1, fromDate, toDate);
        }


        //検索結果を画面表示用に変換
        ret = __getConvDayLyList(paramMdl, daylyList,
                dspDate, fromDate, toDate, sessionUsrSid, admFlg);

        return ret;
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @param dspDate 指定日付
     * @param hourFr 開始時間
     * @param hourTo 終了時間
     * @return ArrayList 予約リスト
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getDaylySiborikomiList(Rsv020ParamModel paramMdl,
                                                      UDate dspDate,
                                                      int hourFr,
                                                      int hourTo)
        throws SQLException {

        BaseUserModel usModel = reqMdl_.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        CommonBiz cmnBiz = new CommonBiz();
        boolean admFlg = cmnBiz.isPluginAdmin(con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        //検索条件 開始時間
        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(hourFr);
        fromDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        fromDate.setSecond(GSConstReserve.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //検索条件 終了時間
        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(hourTo);
        toDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        toDate.setSecond(GSConstReserve.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);

        //予約情報取得
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con_);
        ArrayList<RsvSisetuModel> ret = new ArrayList<RsvSisetuModel>();

        //選択してくださいを選択時は施設を表示しない
        if (paramMdl.getRsv010grpNarrowDown() == GSConstReserve.COMBO_PLEASE_CHOICE) {
            return ret;
        }
        paramMdl.setRsv010SiborikomiFlg(1);
        ArrayList<RsvWeekModelBeforConv> daylyList = null;

        //検索モデル作成
        Rsv010SearchModel sMdl = setSearchModel(paramMdl);

        if (paramMdl.getRsv010grpNarrowDown() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            //全ての場合

            //アクセス可能グループ一覧を取得する。
            List<RsvSisGrpModel> rsgMdlList = null;
            if (admFlg) {
                //全グループ一覧を取得する。
                rsgMdlList = rsgDao.selectAllGroupData();

            } else {
              //アクセス可能グループ一覧を取得する。
                rsgMdlList = rsgDao.getCanReadData(sessionUsrSid);
            }
            List<Integer> canAccessGrp = new ArrayList<Integer>();
            if (rsgMdlList != null && rsgMdlList.size() > 0) {
                for (RsvSisGrpModel model : rsgMdlList) {
                    canAccessGrp.add(model.getRsgSid());
                }
            }

            //アクセス可能なグループが存在する場合のみ検索を行う
            if (!canAccessGrp.isEmpty()) {
                daylyList = dao.getYrkList(canAccessGrp, sMdl, fromDate, toDate);
            } else {
                daylyList = new ArrayList<RsvWeekModelBeforConv>();
            }
        } else {
            //グループ指定の場合

            //施設グループ閲覧権限
            if (!_isReadRsvGrp(con_, reqMdl_, paramMdl.getRsv010grpNarrowDown())) {
                paramMdl.setRsvSelectedGrpSid(GSConstReserve.COMBO_PLEASE_CHOICE);
                return ret;
            }

            daylyList = dao.getYrkList(sMdl, fromDate, toDate);
        }


        //検索結果を画面表示用に変換
        ret = __getConvDayLyList(paramMdl, daylyList,
                dspDate, fromDate, toDate, sessionUsrSid, admFlg);

        return ret;
    }

    /**
     * <br>[機  能] 検索モデル作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv010ParamModel
     * @return Rsv010SearchModel
     */
    private Rsv010SearchModel setSearchModel(Rsv010ParamModel paramMdl) {

        Rsv010SearchModel sMdl = new Rsv010SearchModel();
        /** キーワード*/
        sMdl.setSisetuKeyword(paramMdl.getRsv010svSisetuKeyword());
        /** キーワード区分*/
        sMdl.setKeyWordkbn(paramMdl.getRsv010svKeyWordkbn());
        /** キーワード対象（資産管理番号)*/
        sMdl.setSisetuKeywordSisan(paramMdl.getRsv010svSisetuKeywordSisan());
        /** キーワード対象（施設名)*/
        sMdl.setSisetuKeywordSisetu(paramMdl.getRsv010svSisetuKeywordSisetu());
        /** キーワード対象（備考)*/
        sMdl.setSisetuKeywordBiko(paramMdl.getRsv010svSisetuKeywordBiko());
        /** キーワード対象（ナンバー)*/
        sMdl.setSisetuKeywordNo(paramMdl.getRsv010svSisetuKeywordNo());
        /** キーワード対象（ISBN)*/
        sMdl.setSisetuKeywordIsbn(paramMdl.getRsv010svSisetuKeywordIsbn());
        /** 空き状況*/
        sMdl.setSisetuFree(paramMdl.getRsv010svSisetuFree());
        /** 空き範囲開始年*/
        sMdl.setSisetuFreeFromY(paramMdl.getRsv010svSisetuFreeFromY());
        /** 空き範囲開始月*/
        sMdl.setSisetuFreeFromMo(paramMdl.getRsv010svSisetuFreeFromMo());
        /** 空き範囲開始日*/
        sMdl.setSisetuFreeFromD(paramMdl.getRsv010svSisetuFreeFromD());
        /** 空き範囲開始時*/
        sMdl.setSisetuFreeFromH(paramMdl.getRsv010svSisetuFreeFromH());
        /** 空き範囲開始分*/
        sMdl.setSisetuFreeFromMi(paramMdl.getRsv010svSisetuFreeFromMi());
        /** 空き範囲終了年*/
        sMdl.setSisetuFreeToY(paramMdl.getRsv010svSisetuFreeToY());
        /** 空き範囲終了月*/
        sMdl.setSisetuFreeToMo(paramMdl.getRsv010svSisetuFreeToMo());
        /** 空き範囲終了日*/
        sMdl.setSisetuFreeToD(paramMdl.getRsv010svSisetuFreeToD());
        /** 空き範囲終了時*/
        sMdl.setSisetuFreeToH(paramMdl.getRsv010svSisetuFreeToH());
        /** 空き範囲終了分*/
        sMdl.setSisetuFreeToMi(paramMdl.getRsv010svSisetuFreeToMi());
        /** 施設区分*/
        sMdl.setSisetuKbn(paramMdl.getRsv010svSisetuKbn());
        /** グループ*/
        sMdl.setGrpNarrowDown(paramMdl.getRsv010svGrpNarrowDown());
        /** 喫煙*/
        sMdl.setSisetuSmoky(paramMdl.getRsv010svSisetuSmoky());
        /** 座席数*/
        sMdl.setSisetuChere(paramMdl.getRsv010svSisetuChere());
        /** 社外持ち出し*/
        sMdl.setSisetuTakeout(paramMdl.getRsv010svSisetuTakeout());
        return sMdl;
    }

    /**
     * <br>[機  能] 予約情報を画面表示用に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @param retList 検索結果リスト
     * @param dspDate 表示日付(開始)
     * @param fromDate 表示開始
     * @param toDate 表示終了
     * @param sessionUsrSid ユーザSID
     * @param admFlg 管理者フラグ
     * @return sisetuList 変換結果
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<RsvSisetuModel> __getConvDayLyList(Rsv020ParamModel paramMdl,
                                                          ArrayList<RsvWeekModelBeforConv> retList,
                                                          UDate dspDate,
                                                          UDate fromDate,
                                                          UDate toDate,
                                                          int sessionUsrSid,
                                                          boolean admFlg) throws SQLException {

        if (retList.isEmpty()) {
            return null;
        }

        int kjnSetteiKbn1 = -1;
        int kjnSetteiKbn2 = -1;

        //施設予約個人設定を取得
        RsvUserModel rsvUsrMdl = _isRsvUser(reqMdl_, con_);
        if (rsvUsrMdl != null) {
            //個人設定がされている場合は設定情報を取得
            kjnSetteiKbn1 = rsvUsrMdl.getRsuDit1();
            kjnSetteiKbn2 = rsvUsrMdl.getRsuDit2();
        } else {
            //個人設定が未設定の場合は全部表示する
            kjnSetteiKbn1 = GSConstReserve.KOJN_SETTEI_DSP_OK;
            kjnSetteiKbn2 = GSConstReserve.KOJN_SETTEI_DSP_OK;
        }

        int saveRsdSid = -1;
        ArrayList<RsvSisetuModel> sisetuList =
            new ArrayList<RsvSisetuModel>();
        ArrayList<String> dspArray = new ArrayList<String>();

        HashMap <Integer, Integer> map = new HashMap<Integer, Integer>();
        int authKbn = 0;

        //施設情報設定
        for (RsvWeekModelBeforConv sisetu : retList) {
            UDate date = dspDate.cloneUDate();
            int rsdSid = sisetu.getRsdSid();
            RsvSisetuModel sisetuMdl = new RsvSisetuModel();;

            if (rsdSid != saveRsdSid) {

                if (map.containsKey(sisetu.getRsgSid())) {
                    authKbn = map.get(sisetu.getRsgSid());
                } else {
                    boolean editOkFlg
                        = _isEditRsvGrp(con_, sisetu.getRsgSid(), sessionUsrSid, admFlg);
                    if (editOkFlg) {
                        map.put(sisetu.getRsgSid(), GSConstReserve.RSV_ACCESS_KBN_WRITE);
                        authKbn = GSConstReserve.RSV_ACCESS_KBN_WRITE;
                    } else {
                        map.put(sisetu.getRsgSid(), GSConstReserve.RSV_ACCESS_KBN_READ);
                        authKbn = GSConstReserve.RSV_ACCESS_KBN_READ;
                    }

                }

                //施設情報セット
                sisetuMdl.setRsgSid(sisetu.getRsgSid());
                sisetuMdl.setRsdSid(sisetu.getRsdSid());
                sisetuMdl.setRsdName(sisetu.getRsdName());
                sisetuMdl.setRsdInfoSisetuKbnSid(sisetu.getRsdInfoSisetuKbnSid());
                sisetuMdl.setRsdSisetuId(sisetu.getRsdInfoSisetuId());
                sisetuMdl.setRsdSisanKanri(sisetu.getRsdInfoSisanKanri());
                sisetuMdl.setRsdProp1Value(sisetu.getRsdInfoProp1Value());
                sisetuMdl.setRsdProp2Value(sisetu.getRsdInfoProp2Value());
                sisetuMdl.setRsdProp3Value(sisetu.getRsdInfoProp3Value());
                sisetuMdl.setRsdProp4Value(sisetu.getRsdInfoProp4Value());
                sisetuMdl.setRsdProp5Value(sisetu.getRsdInfoProp5Value());
                sisetuMdl.setRsdProp6Value(sisetu.getRsdInfoProp6Value());
                sisetuMdl.setRsdProp7Value(sisetu.getRsdInfoProp7Value());
                sisetuMdl.setRsdPlaceImgCom1(sisetu.getRsdInfoPlaceImgCom1());
                sisetuMdl.setRsdPlaceImgCom2(sisetu.getRsdInfoPlaceImgCom2());
                sisetuMdl.setRsdPlaceImgCom3(sisetu.getRsdInfoPlaceImgCom3());
                sisetuMdl.setRsdPlaceImgCom4(sisetu.getRsdInfoPlaceImgCom4());
                sisetuMdl.setRsdPlaceImgCom5(sisetu.getRsdInfoPlaceImgCom5());
                sisetuMdl.setRsdPlaceImgCom6(sisetu.getRsdInfoPlaceImgCom6());
                sisetuMdl.setRsdPlaceImgCom7(sisetu.getRsdInfoPlaceImgCom7());
                sisetuMdl.setRsdPlaceImgCom8(sisetu.getRsdInfoPlaceImgCom8());
                sisetuMdl.setRsdPlaceImgCom9(sisetu.getRsdInfoPlaceImgCom9());
                sisetuMdl.setRsdPlaceImgCom10(sisetu.getRsdInfoPlaceImgCom10());
                sisetuMdl.setRsdBiko(
                        StringUtilHtml.transToHTmlPlusAmparsant(sisetu.getRsdInfoBiko()));
                sisetuMdl.setRsdInfoPlaCom(sisetu.getRsdInfoPlaCom());

                //施設情報表示区分セット
                sisetuMdl.setRsdInfoSisetuIdDspKbn(sisetu.getRsdInfoSisetuIdDspKbn());
                sisetuMdl.setRsdInfoSisanKanriDspKbn(sisetu.getRsdInfoSisanKanriDspKbn());
                sisetuMdl.setRsdInfoProp1ValueDspKbn(sisetu.getRsdInfoProp1ValueDspKbn());
                sisetuMdl.setRsdInfoProp2ValueDspKbn(sisetu.getRsdInfoProp2ValueDspKbn());
                sisetuMdl.setRsdInfoProp3ValueDspKbn(sisetu.getRsdInfoProp3ValueDspKbn());
                sisetuMdl.setRsdInfoProp4ValueDspKbn(sisetu.getRsdInfoProp4ValueDspKbn());
                sisetuMdl.setRsdInfoProp5ValueDspKbn(sisetu.getRsdInfoProp5ValueDspKbn());
                sisetuMdl.setRsdInfoProp6ValueDspKbn(sisetu.getRsdInfoProp6ValueDspKbn());
                sisetuMdl.setRsdInfoProp7ValueDspKbn(sisetu.getRsdInfoProp7ValueDspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom1DspKbn(sisetu.getRsdInfoPlaceImgCom1DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom2DspKbn(sisetu.getRsdInfoPlaceImgCom2DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom3DspKbn(sisetu.getRsdInfoPlaceImgCom3DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom4DspKbn(sisetu.getRsdInfoPlaceImgCom4DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom5DspKbn(sisetu.getRsdInfoPlaceImgCom5DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom6DspKbn(sisetu.getRsdInfoPlaceImgCom6DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom7DspKbn(sisetu.getRsdInfoPlaceImgCom7DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom8DspKbn(sisetu.getRsdInfoPlaceImgCom8DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom9DspKbn(sisetu.getRsdInfoPlaceImgCom9DspKbn());
                sisetuMdl.setRsdInfoPlaceImgCom10DspKbn(sisetu.getRsdInfoPlaceImgCom10DspKbn());
                sisetuMdl.setRsdInfoBikoDspKbn(sisetu.getRsdInfoBikoDspKbn());
                sisetuMdl.setRsdInfoPlaComDspKbn(sisetu.getRsdInfoPlaComDspKbn());
                sisetuMdl.setRsdInfoSisetuImgDspKbn(sisetu.getRsdInfoSisetuImgDspKbn());

                //施設予約の承認
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                sisetuMdl.setRsdApprKbnFlg(rsvCmnBiz.isApprSis(con_, rsdSid, -1));
                sisetuMdl.setRsdApprDspFlg(
                        sisetu.getRsdApprDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON);

                //新規予約登録ボタン表示フラグ
                sisetuMdl.setRacAuth(authKbn);

                //施設情報ヘッダセット
                __setSisetuHeader(sisetuMdl, sisetu.getRsdInfoSisetuKbnSid());

                saveRsdSid = rsdSid;

                ArrayList<RsvYoyakuDayModel> yoyakuDayList =
                    new ArrayList<RsvYoyakuDayModel>();

                RsvYoyakuDayModel yoyakuDayMdl = new RsvYoyakuDayModel();
                yoyakuDayMdl.setYrkDateStr(date.getDateString());

                ArrayList<RsvYoyakuModel> yoyakuList =
                    new ArrayList<RsvYoyakuModel>();

                for (RsvWeekModelBeforConv yrk : retList) {

                    //予約開始日付無し = 外部結合にて施設の情報だけ取得したもの
                    if (saveRsdSid != yrk.getRsdSid()
                        || yrk.getRsyFrDate() == null) {
                        continue;
                    }

                    RsvYoyakuModel yrkDayMdl = new RsvYoyakuModel();
                    yrkDayMdl.setRsySid(yrk.getRsySid());
                    yrkDayMdl.setYrkRiyoDateStr(__getTimeString(yrk, fromDate, toDate));
                    yrkDayMdl.setRsyFrDate(yrk.getRsyFrDate());
                    yrkDayMdl.setRsyToDate(yrk.getRsyToDate());

                    if (kjnSetteiKbn2 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                        yrkDayMdl.setYrkName(yrk.getUsiSei() + "  " + yrk.getUsiMei());
                    }

                    //「予定あり」メッセージ
                    GsMessage gsMsg = new GsMessage(reqMdl_);
                    String textPlans = gsMsg.getMessage("reserve.177");

                    RsvCommonBiz rsvBiz = new RsvCommonBiz();
                    int publicFlg = rsvBiz.isCheckPubRange(
                            con_, yrk.getRsySid(), sessionUsrSid);

                    //公開区分別表示
                    if (publicFlg == GSConstReserve.POWRANGE_DEL) {
                        continue;
                    }
                    if (publicFlg == GSConstReserve.POWRANGE_IN) {
                        if (kjnSetteiKbn1 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                            yrkDayMdl.setRsyMok(yrk.getRsyMok());
                        }
                        yrkDayMdl.setPublic(GSConstReserve.PUBLIC_KBN_ALL);
                    } else {
                        if (kjnSetteiKbn1 == GSConstReserve.KOJN_SETTEI_DSP_OK) {
                            if (publicFlg == GSConstReserve.RANGE_OUT_TITLE) {
                                yrkDayMdl.setRsyMok(yrk.getRsyMok());
                            } else {
                                yrkDayMdl.setRsyMok(textPlans);
                            }
                        }
                        yrkDayMdl.setPublic(GSConstReserve.PUBLIC_KBN_PLANS);
                    }

                    yrkDayMdl.setRsyApprStatus(yrk.getRsyApprStatus());
                    yrkDayMdl.setRsyApprKbn(yrk.getRsyApprKbn());
                    yrkDayMdl.setRsyNaiyo(yrk.getRsyNaiyo());
                    yrkDayMdl.setUsrJKbn(yrk.getUsrJkbn());
                    yrkDayMdl.setUsrUkoFlg(yrk.getUsrUkoFlg());
                    yoyakuList.add(yrkDayMdl);
                }

                String ikkatuKey = date.getDateString() + "-" + String.valueOf(saveRsdSid);
                //一括登録用キー(yyyyMMdd-施設SID)
                yoyakuDayMdl.setIkkatuKey(ikkatuKey);
                dspArray.add(ikkatuKey);

                boolean checkedFlg = false;
                String[] hidKey = paramMdl.getRsvIkkatuTorokuKey();
                if (hidKey != null && hidKey.length > 0) {
                    for (String key : hidKey) {
                        if (ikkatuKey.equals(key)) {
                            checkedFlg = true;
                            break;
                        }
                    }
                }
                yoyakuDayMdl.setCheckedFlg(checkedFlg);

                yoyakuDayMdl.setYoyakuList(yoyakuList);
                yoyakuDayList.add(yoyakuDayMdl);

                RsvWeekModel weekMdl = new RsvWeekModel();
                weekMdl.setYoyakuDayList(yoyakuDayList);
                ArrayList<RsvWeekModel> weekList = new ArrayList<RsvWeekModel>();
                weekList.add(weekMdl);

                sisetuMdl.setRsvWeekList(weekList);
                sisetuList.add(sisetuMdl);
            }
        }

        if (paramMdl.isRsvIkkatuTorokuFlg()) {

            //選択された施設キーから画面に表示しきれていないキーを取得
            ArrayList<String> hiddArray =
                __getHiddenSelectedList(paramMdl.getRsvIkkatuTorokuKey(), dspArray);
            paramMdl.setRsvSelectedIkkatuTorokuKey(hiddArray);

            Collections.sort(hiddArray);
            String saveDay = null;
            String saveHiddDayKey = null;
            RsvSisDataDao dao = new RsvSisDataDao(con_);
            ArrayList<String> searchArray = new ArrayList<String>();
            ArrayList<RsvHidDayModel> hiddList = new ArrayList<RsvHidDayModel>();

            //画面に表示しきれていないキーがあれば処理
            if (!hiddArray.isEmpty()) {

                for (String hiddKey : hiddArray) {

                    //キーから日付部分を取得
                    String hiddDayKey = hiddKey.substring(0, hiddKey.indexOf("-"));

                    if (saveDay == null) {
                        saveDay = hiddDayKey;
                        saveHiddDayKey = hiddDayKey;
                    } else if (!saveDay.equals(hiddDayKey)) {

                        ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                        hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));

                        //配列とキーを初期化
                        searchArray = new ArrayList<String>();
                        saveDay = hiddDayKey;
                        saveHiddDayKey = hiddDayKey;
                    }

                    //キーの施設SID部分を追加
                    String hiddSidKey = hiddKey.substring(hiddKey.indexOf("-") + 1);
                    searchArray.add(hiddSidKey);
                }

                if (!searchArray.isEmpty()) {
                    //リスト末尾
                    ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                    hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));
                }
            }

            paramMdl.setRsvIkkatuTorokuHiddenList(hiddList);
        }

        return sisetuList;
    }

    /**
     * <br>[機  能] 施設区分に応じて施設のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuMdl 施設情報モデル
     * @param rskSid 施設区分SID
     */
    private void __setSisetuHeader(RsvSisetuModel sisetuMdl, int rskSid) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                sisetuMdl.setRsvPropHeaderName1(gsMsg.getMessage("reserve.128"));
                sisetuMdl.setRsvPropHeaderName2(gsMsg.getMessage("reserve.132"));
                sisetuMdl.setRsvPropHeaderName6(gsMsg.getMessage("reserve.135"));
                sisetuMdl.setRsvPropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                sisetuMdl.setRsvPropHeaderName1(gsMsg.getMessage("reserve.130"));
                sisetuMdl.setRsvPropHeaderName3(gsMsg.getMessage("reserve.133"));
                sisetuMdl.setRsvPropHeaderName6(gsMsg.getMessage("reserve.135"));
                sisetuMdl.setRsvPropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                sisetuMdl.setRsvPropHeaderName1(gsMsg.getMessage("reserve.129"));
                sisetuMdl.setRsvPropHeaderName2(gsMsg.getMessage("reserve.132"));
                sisetuMdl.setRsvPropHeaderName4(gsMsg.getMessage("reserve.134"));
                sisetuMdl.setRsvPropHeaderName6(gsMsg.getMessage("reserve.135"));
                sisetuMdl.setRsvPropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                sisetuMdl.setRsvPropHeaderName1(gsMsg.getMessage("reserve.131"));
                sisetuMdl.setRsvPropHeaderName3(gsMsg.getMessage("reserve.133"));
                sisetuMdl.setRsvPropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                sisetuMdl.setRsvPropHeaderName6(gsMsg.getMessage("reserve.135"));
                sisetuMdl.setRsvPropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                sisetuMdl.setRsvPropHeaderName6(gsMsg.getMessage("reserve.135"));
                sisetuMdl.setRsvPropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
    }

    /**
     * <br>[機  能] DB取得結果を画面表示用に変換する
     * <br>[解  説]
     * <br>
     * <br>   施設グループ    施設
     * <br>       A            1
     * <br>       A            2
     * <br>       A            3
     * <br>       B            4
     * <br>       B            5
     * <br>       B            6
     * <br>
     * <br>   DBから上記の形式で取得したリストを
     * <br>
     * <br>   施設グループ    施設
     * <br>       A            1 2 3
     * <br>       B            4 5 6
     * <br>
     * <br>   の形式へ変換する
     * <br>
     * <br>[備  考]
     *
     * @param hiddDayList DB取得結果リスト
     * @param saveDay キー
     * @param saveHiddDayKey 日付文字列yyyyMMdd
     * @return ret 変換後モデル
     */
    private RsvHidDayModel __getDaylySisetuList(ArrayList<RsvHidModel> hiddDayList,
                                                 String saveDay,
                                                 String saveHiddDayKey) {

        //画面表示用に変換
        int saveRsgSid = -1;
        String saveRsgName = null;
        ArrayList<RsvHidSisetuModel> hidSisetuList =
            new ArrayList<RsvHidSisetuModel>();
        ArrayList<RsvHidGroupModel> hidGroupList =
            new ArrayList<RsvHidGroupModel>();

        RsvHidDayModel day = new RsvHidDayModel();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        UDate udDay = new UDate();
        udDay.setDate(saveDay);
        day.setHidDayStr(
                gsMsg.getMessage("cmn.year", new String[] {udDay.getStrYear()})
                + udDay.getStrMonth()
                + gsMsg.getMessage("cmn.month")
                + udDay.getStrDay()
                + gsMsg.getMessage("cmn.day") + "（"
                + UDateUtil.getStrWeek(udDay.getWeek(), reqMdl_)
                + "）");

        for (RsvHidModel dbMdl : hiddDayList) {

            if (saveRsgSid == -1) {
                saveRsgSid = dbMdl.getRsgSid();
                saveRsgName = dbMdl.getRsgName();

                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
                continue;

            //同じグループに所属する施設をまとめる
            } else if (saveRsgSid == dbMdl.getRsgSid()) {

                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
                continue;

            //グループブレイク
            } else if (saveRsgSid != dbMdl.getRsgSid()) {

                RsvHidGroupModel group = new RsvHidGroupModel();
                group.setRsgName(saveRsgName);
                group.setSisetuList(hidSisetuList);
                hidGroupList.add(group);

                saveRsgSid = dbMdl.getRsgSid();
                saveRsgName = dbMdl.getRsgName();

                hidSisetuList = new ArrayList<RsvHidSisetuModel>();
                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
            }

            day.setGrpList(hidGroupList);
        }
        RsvHidGroupModel group = new RsvHidGroupModel();
        group.setRsgName(saveRsgName);
        group.setSisetuList(hidSisetuList);
        hidGroupList.add(group);
        day.setGrpList(hidGroupList);

        return day;
    }

    /**
     * <br>[機  能] チェック中のhiddenリストを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param checkAry 現在まで入力されているチェックボックスの値
     * @param viewList 一覧データ（最大20件まで）
     * @return hiddenリスト
     */
    private ArrayList<String> __getHiddenSelectedList(String[] checkAry,
                                                       ArrayList<String> viewList) {

        //ハッシュリストの作成
        HashSet<String> map = new HashSet<String>();

        if (viewList != null) {
            for (String viewKey : viewList) {
                map.add(viewKey);
            }
        }

        ArrayList<String> hiddenList = new ArrayList<String>();
        if (checkAry != null) {
            //入力値(hidden値)から表示データのパラメータを除外して登録
            for (int i = 0; i < checkAry.length; i++) {
                if (!map.contains(checkAry[i])) {
                    hiddenList.add(checkAry[i]);
                }
            }
        }

        return hiddenList;
    }

    /**
     * <br>[機  能] 予約時間表示を画面表示用に編集
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yrk 予約情報
     * @param dFrDate 表示開始日時
     * @param dToDate 表示終了日時
     * @return String 画面表示用時間
     */
    private String __getTimeString(RsvWeekModelBeforConv yrk, UDate dFrDate, UDate dToDate) {

        StringBuilder buf = new StringBuilder();
        UDate frDate = yrk.getRsyFrDate();
        UDate toDate = yrk.getRsyToDate();
        UDate cmpToDate = null;

        if (dToDate.getIntHour() == GSConstReserve.DAY_END_HOUR) {
            cmpToDate = dToDate.cloneUDate();
            cmpToDate.addDay(1);
            cmpToDate.setHour(GSConstReserve.DAY_START_HOUR);
            cmpToDate.setMinute(GSConstReserve.DAY_START_MINUTES);
            cmpToDate.setSecond(GSConstReserve.DAY_END_SECOND);
            cmpToDate.setMilliSecond(GSConstReserve.DAY_END_MILLISECOND);
        } else {
            cmpToDate = dToDate.cloneUDate();
        }

        boolean flg = false;

        //スケジュール開始日時が表示範囲か判定
        if (frDate.betweenYMDHM(dFrDate, dToDate)) {
            buf.append(frDate.getStrHour());
            buf.append(":");
            buf.append(frDate.getStrMinute());
            buf.append("-");
            flg = true;
        }
        //スケジュール終了日時が表示範囲か判定
        if (toDate.betweenYMDHM(dFrDate, cmpToDate)) {
            if (flg == false) {
                buf.append("-");
            }
            if (toDate.getIntHour() == GSConstReserve.DAY_START_HOUR
                    && toDate.getIntMinute() == GSConstReserve.DAY_START_MINUTES) {
                buf.append("24");
                buf.append(":");
                buf.append("00");
            } else {
                buf.append(toDate.getStrHour());
                buf.append(":");
                buf.append(toDate.getStrMinute());
            }
        } else {
            if (!flg) {
                buf.append("");
            }
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] 表示日付の移動を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @param moveDay 移動日数
     * @param today 今日へ移動
     */
    public void setMoveDspData(Rsv020ParamModel paramMdl, int moveDay, boolean today) {

        RsvCommonBiz biz = new RsvCommonBiz();
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = biz.getDspFrom(paramMdl.getRsvDspFrom());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        paramMdl.setRsvDspFrom(udate.getDateString());
        paramMdl.setRsv100selectedFromYear(udate.getYear());
        paramMdl.setRsv100selectedFromMonth(udate.getMonth());
        paramMdl.setRsv100selectedFromDay(udate.getIntDay());
    }

    /**
     * <br>[機  能] ヘッダー部分へ表示する日付文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 表示日付
     * @return String 日付文字列(YYYY年MM月DD日(W))
     */
    private String __getHeaderDate(UDate date) {

        String ret = "";
        if (date == null) {
            return ret;
        }
        GsMessage gsMsg = new GsMessage(reqMdl_);
        StringBuilder buf = new StringBuilder();
        buf.append(gsMsg.getMessage("cmn.year", new String[] {date.getStrYear()}));
        buf.append(date.getStrMonth());
        buf.append(gsMsg.getMessage("cmn.month"));
        buf.append(date.getStrDay());
        buf.append(gsMsg.getMessage("cmn.day"));
        buf.append("(");
        buf.append(__getStrWeek(date.getWeek()));
        buf.append(")");
        ret = buf.toString();
        return ret;
    }

    /**
     * <br>[機  能] UDateの曜日定数から曜日文字を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param week UDateの曜日定数
     * @return String 曜日
     */
    private String __getStrWeek(int week) {

        String str = "";
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (week) {
            case UDate.SUNDAY:
                str = gsMsg.getMessage("cmn.sunday");
                break;
            case UDate.MONDAY:
                str = gsMsg.getMessage("cmn.month");
                break;
            case UDate.TUESDAY:
                str = gsMsg.getMessage("cmn.tuesday");
                break;
            case UDate.WEDNESDAY:
                str = gsMsg.getMessage("cmn.wednesday");
                break;
            case UDate.THURSDAY:
                str = gsMsg.getMessage("cmn.thursday");
                break;
            case UDate.FRIDAY:
                str = gsMsg.getMessage("cmn.friday");
                break;
            case UDate.SATURDAY:
                str = gsMsg.getMessage("cmn.saturday");
                break;
            default:
                break;
        }
        return str;
    }

    /**
     * <br>[機  能] 予約時間間隔を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return hourDiv 時間間隔 区切り数
     * @throws SQLException SQL実行時例外
     */
    public int __getHourDivCount() throws SQLException {

        RsvAdmConfDao confDao = new RsvAdmConfDao(con_);
        RsvAdmConfModel confModel = confDao.select();
        int hourDiv = 0;
        if (confModel == null) {
            hourDiv = GSConstReserve.DF_HOUR_DIVISION;
        } else {
            hourDiv = confModel.getRacHourDiv();
        }
        return 60 / hourDiv;
    }

    /**
     * <br>[機  能] 施設画像データを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv020ParamModel
     * @param tempDir テンポラリディレクトリ
     * @param appRoot アプリケーションルートパス
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void get020SisetuImgData(
            Rsv020ParamModel paramMdl, String tempDir, String appRoot, int usrSid)
    throws SQLException, TempFileException, IOException, IOToolsException   {

        //施設予約個人設定で画像表示を許可しているか確認
        RsvUserDao ruDao = new RsvUserDao(con_);
        RsvUserModel ruMdl = ruDao.select(usrSid);

        if (ruMdl == null) {
            __setRsvBinData(paramMdl, tempDir, appRoot, usrSid);
        } else {
            if (ruMdl.getRsuImgDsp() == GSConstReserve.SISETU_IMG_ON) {
                __setRsvBinData(paramMdl, tempDir, appRoot, usrSid);
            }
        }
    }

    /**
     * <br>[機  能] 施設画像データを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv020ParamModel
     * @param tempDir テンポラリディレクトリ
     * @param appRoot アプリケーションルートパス
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    private void __setRsvBinData(
            Rsv020ParamModel paramMdl, String tempDir, String appRoot, int usrSid)
    throws SQLException, TempFileException, IOException, IOToolsException   {

        //施設データ取得
        RsvBinDao rbDao = new RsvBinDao(con_);
        CmnBinfModel dataRet = null;
        ArrayList<RsvSisetuModel> dspMdlList = new ArrayList<RsvSisetuModel>();

        for (RsvSisetuModel bean : paramMdl.getRsv020DaylyList()) {
            dataRet = rbDao.getWriteTmpFile(bean.getRsdSid());
            if (dataRet == null) {
                //施設画像が存在しない場合
                bean.setSisetuImgBinSid(Long.valueOf(0));
            } else {
                //週間・日間に表示する施設画像データをセット
                bean.setSisetuImgBinSid(dataRet.getBinSid());
            }
            dspMdlList.add(bean);
        }

        //表示用施設予約データ
        paramMdl.setRsv020DaylyList(dspMdlList);
    }

    /**
     * <br>[機  能] 施設予約[日間]をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv020ParamModel
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @return pdfModel SmlPdfModel
     * @throws IOException IO実行時例外
     * @throws SQLException
     * @throws NumberFormatException
     */
    public RsvNikPdfModel createRsvNikPdf(
            Rsv020ParamModel paramMdl,
            Connection con,
            String appRootPath,
            String outTempDir)
        throws IOException, NumberFormatException, SQLException {
        OutputStream oStream = null;
        BaseUserModel usModel = reqMdl_.getSmodel();
        CommonBiz commonBiz = new CommonBiz();


        //施設予約PDF出力用モデル
        RsvNikPdfModel pdfModel = new RsvNikPdfModel();
        //表示用施設予約データ
        pdfModel.setRsvNikDaylyList(paramMdl.getRsv020DaylyList());

        //年月
        pdfModel.setRsvNikDspDate(paramMdl.getRsvDspFrom());
        //ヘッダー表示用年月
        pdfModel.setRsvNikHeadDate(paramMdl.getRsvDispYm());
        pdfModel.setRsvNikFrom(Integer.valueOf(paramMdl.getRsv020FromHour()));
        pdfModel.setRsvNikTo(Integer.valueOf(paramMdl.getRsv020ToHour()));

        String headDate = paramMdl.getRsvDispYm();
        //セッションユーザSID
        int sessionUsrSid = usModel.getUsrsid();
        if (commonBiz.getRokuyoDspKbn(con, sessionUsrSid)) {
            UDate dspDate = new UDate();
            dspDate.setDate(Integer.parseInt(headDate.substring(0, 4)),
                            Integer.parseInt(headDate.substring(5, 7)),
                            Integer.parseInt(headDate.substring(8, 10)));
            pdfModel.setDspRokuyou(commonBiz.getRokuyou(dspDate));
        } else {
            pdfModel.setDspRokuyou("");
        }

        //colspan値
        pdfModel.setRsvNikColSpan(paramMdl.getRsv020ColSpan());
        //タイムチャート作成
        pdfModel.setRsvNikTimeChartList(paramMdl.getRsv020TimeChartList());
        //予約時間間隔取得
        pdfModel.setRsvNikMemoriCount(paramMdl.getRsv020HourDivCount());

        GsMessage msg = new GsMessage(reqMdl_);
        //表示グループ
        if (paramMdl.getRsvSelectedGrpSid() == GSConstReserve.COMBO_DEFAULT_VALUE) {
            pdfModel.setRsvNikdspGroup(msg.getMessage("cmn.all"));
        } else {
            for (LabelValueBean bean : paramMdl.getRsvGrpLabelList()) {
                if (Integer.valueOf(bean.getValue())
                        == Integer.valueOf(paramMdl.getRsvSelectedGrpSid())) {
                    pdfModel.setRsvNikdspGroup(bean.getLabel());
                    break;
                }
            }
        }

        //ダウンロード時のファイル名
        String outBookName = paramMdl.getRsvDispYm()
                + msg.getMessage("reserve.rsvmain.4")
                + "(" + msg.getMessage("cmn.days2") + ")";
        //エスケープ処理をしたファイル名
        String encOutBookName = __fileNameCheck(outBookName) + ".pdf";
        pdfModel.setRsvNikFileName(encOutBookName);

        //テンポラリディレクトリに保存時のファイル名
        String saveFileName = "rsvNik_" + paramMdl.getRsvDspFrom() + "_" + sessionUsrSid + ".pdf";
        pdfModel.setSaveNikFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RsvNikPdfUtil pdfUtil = new RsvNikPdfUtil(reqMdl_);
            pdfUtil.createRsvSyukanPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("施設予約[日間]PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("施設予約[日間]PDF出力を終了します。");

        return pdfModel;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String __fileNameCheck(String fileName) {
            String escName = fileName;

            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }
}