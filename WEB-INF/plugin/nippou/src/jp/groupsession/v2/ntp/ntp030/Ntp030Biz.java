package jp.groupsession.v2.ntp.ntp030;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.biz.NtpUsedDataBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NippouSearchDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpBinDao;
import jp.groupsession.v2.ntp.dao.NtpColMsgDao;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NippouListSearchModel;
import jp.groupsession.v2.ntp.model.NippouSearchModel;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpBinModel;
import jp.groupsession.v2.ntp.model.NtpCommentModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpGoodModel;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030CommentModel;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030DataModel;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030DspCommentModel;
import jp.groupsession.v2.ntp.ntp030.model.Ntp030UsrLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] 日報 日間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp030Biz.class);
    /** 表示件数 */
    private static int dspCnt__ = 5;
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** 活動分類格納MAP */
    public Map<Integer, String> ktBunruiMap__ = null;
    /** 活動方法格納MAP */
    public Map<Integer, String> ktHouhouMap__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Ntp030Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     * @param cntCon MlCountMtController
     */
    public Ntp030Biz(Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon) {
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }
    /**
     * <br>[機  能] 初期表示画面情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param pconfig プラグインコンフィグ
     * @param con コネクション
     * @return Ntp030ParamModel アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Ntp030ParamModel getInitData(Ntp030ParamModel paramMdl,
                                 PluginConfig pconfig, Connection con)
    throws SQLException {


        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        Ntp010Biz ntp010biz = new Ntp010Biz(con__, reqMdl__);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        paramMdl.setNtp030SessionSid(String.valueOf(sessionUsrSid));

        //個人設定を取得
        NtpPriConfModel confMdl = ntp010biz.getPrivateConf(sessionUsrSid, con);
        //管理者設定を取得
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpAdmConfModel adminConf = biz.getAdminConfiModel(con);

        //リクエストパラメータを取得
        //表示開始日
        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010DspDate())) {
            paramMdl.setNtp010DspDate(new UDate().getDateString());
        }
        String strDspDate = NullDefault.getString(
                paramMdl.getNtp010DspDate(), new UDate().getDateString());
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //年月日初期選択値
        UDate uDate = new UDate();

        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010SelectDate())) {
            paramMdl.setNtp010SelectDate(uDate.getDateString());
        }

        uDate.setDate(
                NullDefault.getString(
                        paramMdl.getNtp010SelectDate(), uDate.getDateString()));

        //活動分類取得
        paramMdl.setNtp030KtbunruiLavel(getKtbunruiLabelList(con));

        //活動方法取得
        paramMdl.setNtp030KthouhouLavel(getKthouhouLabelList(con));

        //オフセット初期化
        paramMdl.setNtp030Offset(1);


        String ntpSid = null;
        if (paramMdl.getNtp010SelectUsrSid() == null
                || StringUtil.isNullZeroStringSpace(paramMdl.getNtp010SelectUsrSid())) {
            paramMdl.setNtp010SelectUsrSid(String.valueOf(sessionUsrSid));
        }
        String uid = paramMdl.getNtp010SelectUsrSid();

        //グループコンボ生成
        NtpCommonBiz scBiz = new NtpCommonBiz(con__, reqMdl__);
        //グループ設定
        List<NtpLabelValueModel> groupLabel = scBiz.getGroupLabelForNippou(
                sessionUsrSid, con, false);
        paramMdl.setNtp010GpLabelList(groupLabel);

        //デフォルト表示グループ
        String dfGpSidStr = biz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);
        int dspGpSid = 0;
        boolean myGroupFlg = false;
        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getNtp010DspGpSid(), dfGpSidStr);
        dspGpSidStr = scBiz.getEnableSelectGroup(groupLabel, dspGpSidStr, dfGpSidStr);

        if (NtpCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = NtpCommonBiz.getDspGroupSid(dspGpSidStr);
            myGroupFlg = true;
            paramMdl.setNtp010DspGpSid(dspGpSidStr);
        } else {
            dspGpSid = NullDefault.getInt(paramMdl.getNtp010DspGpSid(), dfGpSid);
            paramMdl.setNtp010DspGpSid(String.valueOf(dspGpSid));
        }

        //ユーザコンボ生成
        List<Ntp030UsrLabelModel> userLabel = __getUserLabelList(
                con, sessionUsrSid, dspGpSid, myGroupFlg);
        paramMdl.setNtp030UsrLabelList(userLabel);


        //最新の報告日付のデータを30件取得
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //選択された日付の日報データをすべて取得する

        //日報データ取得
        ArrayList<NippouSearchModel> ntpMdlList = new ArrayList<NippouSearchModel>();
        ntpMdlList = getNtpData(paramMdl, sessionUsrSid, adminConf, con, myGroupFlg);


        //日報データリスト
        ArrayList<Ntp030DataModel> dataList = new ArrayList<Ntp030DataModel>();

        if (!ntpMdlList.isEmpty()) {
            /**管理者権限*/
            CommonBiz commonBiz = new CommonBiz();
            boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU);
            /**取得ユーザ情報SIDセット*/
            Set<Integer> usrSidSet = new HashSet<>();
            /**取得日報SIDセット*/
            Set<Integer> nipSidSet = new HashSet<>();
            /**取得案件SIDセット*/
            Set<Integer> nanSidSet = new HashSet<>();
            /**取得会社SIDセット*/
            Set<Integer> acoSidSet = new HashSet<>();
            /**取得拠点SIDセット*/
            Set<Integer> abaSidSet = new HashSet<>();
            /**日報編集権限マップ*/
            Map<Integer, Integer> eauthMap = new HashMap<>();

            for (NippouSearchModel ntpMdl : ntpMdlList) {
                if (ntpMdl == null) {
                    //編集対象が無い場合
                    break;
                }
                nipSidSet.add(ntpMdl.getNipSid());
                usrSidSet.add(ntpMdl.getUsrSid());
                usrSidSet.add(ntpMdl.getNipAuid());
                nanSidSet.add(ntpMdl.getNanSid());
                acoSidSet.add(ntpMdl.getAcoSid());
                abaSidSet.add(ntpMdl.getAbaSid());
                eauthMap.put(ntpMdl.getNipSid(),
                        isAddEditOk(sessionUsrSid, isAdmin, ntpMdl.getUsrSid()));
            }
            UserSearchDao uDao = new UserSearchDao(con);

            /**取得ユーザ情報Map*/
            HashMap<Integer, UserSearchModel> usrMap = new HashMap<>();
            List<UserSearchModel> usrList = uDao.getUsersInfoJtkb(new ArrayList<>(usrSidSet),
                    GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);
            for (UserSearchModel user : usrList) {
                usrMap.put(user.getUsrSid(), user);
            }
            String ntp030UsrName =
                    getUsrName(Integer.parseInt(uid), Integer.parseInt("0"), con);

            /**案件情報Map*/
            Map<Integer, NtpAnkenModel> ankenMap =  _getAnkenMap(con, nanSidSet);
            /**会社情報Map*/
            Map<Integer, AdrCompanyModel> acoMap =  _getAcoMap(con, acoSidSet);
            /**会社拠点情報Map*/
            Map<Integer, AdrCompanyBaseModel> abaMap =  _getAbaMap(con, abaSidSet);

            NtpBinDao binDao = new NtpBinDao(con);
            /**添付情報Map*/
            Map<Integer, List<CmnBinfModel>> filMap = binDao.getFileMap(nipSidSet);

            Ntp030Dao ntpDao = new Ntp030Dao(con);
            /**コメント情報Map*/
            Map<Integer, List<Ntp030CommentModel>> cmmMap = ntpDao.getNpcMap(
                    reqMdl__, nipSidSet, eauthMap, usrMap);

            NtpGoodDao gDao = new NtpGoodDao(con__);
            /**いいね情報Map*/
            HashMap<Integer, List<NtpGoodModel>> goodMap = gDao.getIineMap(nipSidSet);



            //データセット
            for (NippouSearchModel ntpMdl : ntpMdlList) {

                if (ntpMdl == null) {
                    //編集対象が無い場合
                    return paramMdl;
                }

                Ntp030DataModel dataMdl = new Ntp030DataModel();

                if (String.valueOf(ntpMdl.getNipSid()).equals(ntpSid)) {
                    dataMdl.setNtp030SelectFlg(1);
                }

                dataMdl.setNtp030NtpSid(ntpMdl.getNipSid());


                CmnUsrmInfModel uMdl = null;
                //登録者
                uMdl = usrMap.get(ntpMdl.getNipAuid());
                if (uMdl != null) {
                    ntpMdl.setNipAuidSei(uMdl.getUsiSei());
                    dataMdl.setNtp030NtpUsiSei(uMdl.getUsiSei());
                    ntpMdl.setNipAuidMei(uMdl.getUsiMei());
                    dataMdl.setNtp030NtpUsiSei(uMdl.getUsiMei());
                    ntpMdl.setNipAuidJkbn(uMdl.getUsrJkbn());
                    dataMdl.setNtp030NtpUsiId(uMdl.getUsrSid());
                }

                //ユーザ情報
                CmnUsrmInfModel userMdl = null;
                userMdl = usrMap.get(ntpMdl.getUsrSid());
                dataMdl.setNtp030UsrInfMdl(userMdl);

                paramMdl.setNtp030UsrName(userMdl.getUsiSei() + " " + userMdl.getUsiMei());
                dataMdl.setNtp030NtpUsrName(ntp030UsrName);
                dataMdl.setNtp030UsrSid(String.valueOf(ntpMdl.getUsrSid()));

                paramMdl.setNtp030AddUsrName(ntpMdl.getNipAuidSei() + " " + ntpMdl.getNipAuidMei());
                dataMdl.setNtp030NtpAddUsrName(
                        ntpMdl.getNipUsrSei() + " " + ntpMdl.getNipUsrMei());


                paramMdl.setNtp030AddUsrJkbn(String.valueOf(ntpMdl.getNipAuidJkbn()));
                dataMdl.setNtp030AddUsrJkbn(ntpMdl.getNipAuidJkbn());
                //登録日時
                String textAddDate = gsMsg.getMessage("schedule.src.84");
                paramMdl.setNtp030AddDate(
                        textAddDate + " : "
                        + UDateUtil.getSlashYYMD(ntpMdl.getNipAdate())
                        + " "
                        + UDateUtil.getSeparateHM(ntpMdl.getNipAdate()));

                //報告日付
                dataMdl.setNtp030NtpDate(UDateUtil.getYymdJ(ntpMdl.getNipDate(), reqMdl__));
                dataMdl.setNtp030LabelDate(UDateUtil.getSlashMD(ntpMdl.getNipDate()));

                UDate frDate = ntpMdl.getNipFrTime();
                UDate toDate = ntpMdl.getNipToTime();
                //開始年月日
                paramMdl.setNtp030FrYear(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030FrYear(),
                                String.valueOf(frDate.getYear())));
                paramMdl.setNtp030FrMonth(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030FrMonth(),
                                String.valueOf(frDate.getMonth())));
                paramMdl.setNtp030FrDay(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030FrDay(),
                                String.valueOf(frDate.getIntDay())));

                dataMdl.setNtpYear(frDate.getYear());
                dataMdl.setNtpMonth(frDate.getMonth());
                dataMdl.setNtpDay(frDate.getIntDay());

                //活動分類
                paramMdl.setNtp030Ktbunrui(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030Ktbunrui(),
                                String.valueOf(ntpMdl.getMkbSid())));

                dataMdl.setKtbunruiSid(ntpMdl.getMkbSid());
                dataMdl.setNtp030DspKtbunrui(getKtbunrui(ntpMdl.getMkbSid()));

                //活動方法
                paramMdl.setNtp030Kthouhou(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030Kthouhou(),
                        String.valueOf(ntpMdl.getMkhSid())));
                dataMdl.setKthouhouSid(ntpMdl.getMkhSid());
                dataMdl.setNtp030DspKthouhou(getKthouhou(ntpMdl.getMkhSid()));

                //見込み度
                paramMdl.setNtp030Mikomido(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030Mikomido(),
                        String.valueOf(ntpMdl.getNipMikomi())));
                dataMdl.setMikomido(ntpMdl.getNipMikomi());
                dataMdl.setNtp030DspMikomido(getMikomido(ntpMdl.getNipMikomi()));

                paramMdl.setNtp030FrHour(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030FrHour(),
                                GSConstNippou.TIME_NOT_SELECT));

                if (frDate.equalsDate(ntpMdl.getNipDate())) {
                    dataMdl.setFrHour(frDate.getIntHour());
                    dataMdl.setNtp030DspFrHour(
                            StringUtil.toDecFormat(frDate.getIntHour(), "00"));
                } else {
                    dataMdl.setFrHour(frDate.getIntHour() + 24);
                    dataMdl.setNtp030DspFrHour(
                            StringUtil.toDecFormat(frDate.getIntHour() + 24, "00"));
                }

                paramMdl.setNtp030FrMin(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030FrMin(),
                                GSConstNippou.TIME_NOT_SELECT));
                dataMdl.setFrMin(frDate.getIntMinute());
                dataMdl.setNtp030DspFrMinute(
                        StringUtil.toDecFormat(frDate.getIntMinute(), "00"));

                paramMdl.setNtp030ToHour(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030ToHour(),
                                GSConstNippou.TIME_NOT_SELECT));

                if (frDate.equalsDate(toDate) && ntpMdl.getNipDate().equalsDate(frDate)) {
                    dataMdl.setToHour(toDate.getIntHour());
                    dataMdl.setNtp030DspToHour(
                            StringUtil.toDecFormat(toDate.getIntHour(), "00"));
                } else {
                    dataMdl.setToHour(toDate.getIntHour() + 24);
                    dataMdl.setNtp030DspToHour(
                            StringUtil.toDecFormat(toDate.getIntHour() + 24, "00"));
                }

                paramMdl.setNtp030ToMin(
                        NullDefault.getStringZeroLength(paramMdl.getNtp030ToMin(),
                                GSConstNippou.TIME_NOT_SELECT));
                dataMdl.setToMin(toDate.getIntMinute());
                dataMdl.setNtp030DspToMinute(
                        StringUtil.toDecFormat(toDate.getIntMinute(), "00"));

                //背景
                int iniBgcolor = GSConstNippou.DF_BG_COLOR;
                if (ntpMdl.getNipTitleClo() > GSConstNippou.DF_BG_COLOR) {
                    iniBgcolor = ntpMdl.getNipTitleClo();
                }
                paramMdl.setNtp030Bgcolor(
                        NullDefault.getString(
                                paramMdl.getNtp030Bgcolor(), String.valueOf(iniBgcolor)));
                dataMdl.setBgcolor(iniBgcolor);

                //タイトル
                paramMdl.setNtp030Title(
                        NullDefault.getString(paramMdl.getNtp030Title(), ntpMdl.getNipTitle()));
                dataMdl.setTitle(
                        StringUtilHtml.transToHTmlForTextArea(
                                ntpMdl.getNipTitle()));

                //詳細
                paramMdl.setNtp030Value(
                        NullDefault.getString(
                                paramMdl.getNtp030Value(), ntpMdl.getNipDetail()));
                dataMdl.setValueStr(ntpMdl.getNipDetail());

                dataMdl.setNtp030DspValueStr(
                             StringUtil.transToLink(
                             StringUtilHtml.returntoBR(StringUtilHtml.transToHTmlForTextArea(
                             ntpMdl.getNipDetail())),
                             StringUtil.OTHER_WIN, true));

                //次のアクション区分
                paramMdl.setNtp030ActDateKbn(ntpMdl.getNipActDateKbn());
                dataMdl.setActDateKbn(ntpMdl.getNipActDateKbn());

                if (ntpMdl.getNipActDateKbn() != 0) {
                    if (ntpMdl.getNipActionDate() != null) {
                        UDate ntpActionDate = ntpMdl.getNipActionDate();

                        //次のアクション日
                        paramMdl.setNtp030NxtActYear(
                                NullDefault.getStringZeroLength(paramMdl.getNtp030NxtActYear(),
                                        String.valueOf(ntpActionDate.getYear())));
                        paramMdl.setNtp030NxtActMonth(
                                NullDefault.getStringZeroLength(paramMdl.getNtp030NxtActMonth(),
                                        String.valueOf(ntpActionDate.getMonth())));
                        paramMdl.setNtp030NxtActDay(
                                NullDefault.getStringZeroLength(paramMdl.getNtp030NxtActDay(),
                                        String.valueOf(ntpActionDate.getIntDay())));

                        dataMdl.setNtpActionYear(ntpActionDate.getYear());
                        dataMdl.setNtpActionMonth(ntpActionDate.getMonth());
                        dataMdl.setNtpActionDay(ntpActionDate.getIntDay());
                    }
                }

                //次のアクション
                paramMdl.setNtp030NextAction(
                        NullDefault.getString(
                                paramMdl.getNtp030NextAction(), ntpMdl.getNipAction()));
                dataMdl.setActionStr(ntpMdl.getNipAction());
                String dspActionStr =
                    StringUtilHtml.transToHTmlForTextArea(ntpMdl.getNipAction());
                dataMdl.setNtp030DspActionStr(StringUtilHtml.returntoBR(dspActionStr));

                //所感
                paramMdl.setNtp030Biko(
                        NullDefault.getString(paramMdl.getNtp030Biko(), ntpMdl.getNipSyokan()));

                //公開
                paramMdl.setNtp030Public(
                        NullDefault.getString(paramMdl.getNtp030Public(),
                                String.valueOf(ntpMdl.getNipPublic())));
                //編集権限
                paramMdl.setNtp030Edit(
                        NullDefault.getString(paramMdl.getNtp030Edit(),
                                String.valueOf(ntpMdl.getNipEdit())));

                //拡張SID存在フラグ
                boolean textDspFlg = false;
                paramMdl.setNtp030ExTextDspFlg(textDspFlg);

                //案件情報取得
                NtpAnkenModel ankenModel = ankenMap.get(ntpMdl.getNanSid());
                if (ankenModel != null) {
                    dataMdl.setAnkenSid(ntpMdl.getNanSid());
                    dataMdl.setAnkenCode(ankenModel.getNanCode());
                    dataMdl.setAnkenName(ankenModel.getNanName());
                }
                dataMdl.setAnkenViewable(ntpMdl.isAnkenViewable());

                //会社情報
                int acoSid = ntpMdl.getAcoSid();
                int abaSid = ntpMdl.getAbaSid();

                if (acoMap.containsKey(acoSid)) {
                    AdrCompanyModel companyModel = acoMap.get(acoSid);
                    dataMdl.setCompanySid(acoSid);
                    dataMdl.setCompanyCode(companyModel.getAcoCode());
                    dataMdl.setCompanyName(companyModel.getAcoName());
                }

                //会社拠点情報
                if (abaMap.containsKey(abaSid)) {
                    AdrCompanyBaseModel companyBaseMdl = abaMap.get(abaSid);
                    dataMdl.setCompanyBaseSid(abaSid);
                    dataMdl.setCompanyBaseName(companyBaseMdl.getAbaName());
                }

                //添付ファイル情報取得
                if (filMap.containsKey(ntpMdl.getNipSid())) {
                    ArrayList<CmnBinfModel> retBin =
                            new ArrayList<>(filMap.get(ntpMdl.getNipSid()));
                    paramMdl.setNtp030FileList(retBin);
                    dataMdl.setNtp030FileList(retBin);
                }
                //コメント取得
                if (cmmMap.containsKey(ntpMdl.getNipSid())) {
                    ArrayList<Ntp030CommentModel> npcList
                                        = new ArrayList<>(cmmMap.get(ntpMdl.getNipSid()));
                    if (!npcList.isEmpty()) {
                        dataMdl.setNtp030CommentList(npcList);
                    }
                }
                //編集可能区分取得
                dataMdl.setNtp030AuthEditKbn(
                        isAddEditOk(sessionUsrSid, isAdmin, ntpMdl.getUsrSid()));

                //いいね件数取得
                int goodFlg = 0;
                if (goodMap.containsKey(ntpMdl.getNipSid())) {
                    ArrayList<NtpGoodModel> gList = new ArrayList<NtpGoodModel>(
                            goodMap.get(ntpMdl.getNipSid()));
                    dataMdl.setNtp030GoodCnt(gList.size());
                    //セッションユーザがいいねしているか
                    for (NtpGoodModel gMdl : gList) {
                        if (gMdl.getUsrSid() == sessionUsrSid) {
                            goodFlg = 1;
                        }
                    }
                }
                dataMdl.setNtp030GoodFlg(goodFlg);
                paramMdl.setNtp030DataFlg(true);

                dataList.add(dataMdl);
            }

            paramMdl.setNtp030DataModelList(dataList);

            //登録・編集権限を取得
            paramMdl.setAuthAddEditKbn(
                    ntp010biz.isAddEditOk(Integer.valueOf(uid), con));
        } else {
            paramMdl.setNtp030DataFlg(false);
        }

        //共通項目
        //カラーコメント
        NtpColMsgDao msgDao = new NtpColMsgDao(con);
        ArrayList<String> msgList = msgDao.selectMsg();
        paramMdl.setNtp030ColorMsgList(msgList);
        //年コンボを作成
        paramMdl.setNtp030YearLavel(getYearLavel(dspDate.getYear()));
        //月コンボを作成
        paramMdl.setNtp030MonthLavel(getMonthLavel());
        //日コンボを作成
        paramMdl.setNtp030DayLavel(getDayLavel());
        //時コンボを作成
        paramMdl.setNtp030HourLavel(getHourLavel());
        //分コンボを作成
        paramMdl.setNtp030MinuteLavel(getMinuteLavel(con));

        //ソートコンボを生成
        paramMdl.setNtp030SortLabel(getSortLavel());

        paramMdl.setNtp030FrYear(
                NullDefault.getString(paramMdl.getNtp030FrYear(),
                        String.valueOf(uDate.getYear())));
        paramMdl.setNtp030FrMonth(
                NullDefault.getString(paramMdl.getNtp030FrMonth(),
                        String.valueOf(uDate.getMonth())));
        paramMdl.setNtp030FrDay(
                NullDefault.getString(paramMdl.getNtp030FrDay(),
                        String.valueOf(uDate.getIntDay())));
        paramMdl.setNtp030ToYear(
                NullDefault.getString(paramMdl.getNtp030ToYear(),
                        String.valueOf(uDate.getYear())));
        paramMdl.setNtp030ToMonth(
                NullDefault.getString(paramMdl.getNtp030ToMonth(),
                        String.valueOf(uDate.getMonth())));
        paramMdl.setNtp030ToDay(
                NullDefault.getString(paramMdl.getNtp030ToDay(),
                        String.valueOf(uDate.getIntDay())));
        //時間
        UDate frDate = null;
        UDate toDate = null;
        if (confMdl != null) {
            frDate = confMdl.getNprIniFrDate();
            toDate = confMdl.getNprIniToDate();
        } else {
            frDate = new UDate();
            toDate = new UDate();
            frDate.setHour(GSConstNippou.DF_FROM_HOUR);
            frDate.setMinute(GSConstNippou.DF_FROM_MINUTES);
            toDate.setHour(GSConstNippou.DF_TO_HOUR);
            toDate.setMinute(GSConstNippou.DF_TO_MINUTES);
        }
        paramMdl.setNtp030FrHour(
                NullDefault.getStringZeroLength(paramMdl.getNtp030FrHour(),
                String.valueOf(frDate.getIntHour())));
        paramMdl.setNtp030FrMin(
                NullDefault.getStringZeroLength(paramMdl.getNtp030FrMin(),
                        String.valueOf(frDate.getIntMinute())));
        paramMdl.setNtp030ToHour(
                NullDefault.getStringZeroLength(paramMdl.getNtp030ToHour(),
                        String.valueOf(toDate.getIntHour())));
        paramMdl.setNtp030ToMin(
                NullDefault.getStringZeroLength(paramMdl.getNtp030ToMin(),
                        String.valueOf(toDate.getIntMinute())));
        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(
                con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU);
        if (adminUser) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }
        //初期表示完了
        paramMdl.setNtp030InitFlg(String.valueOf(GSConstNippou.NOT_INIT_FLG));

        //ユーザ情報取得
        CmnUsrmInfModel model = new CmnUsrmInfModel();
        model = getUsrInfo(String.valueOf(sessionUsrSid));
        if (model != null) {
            paramMdl.setNtp030UsrInfMdl(model);
        }

        //ラベル最終表示日時
        if (!dataList.isEmpty()) {
            paramMdl.setNtp030LabelDate(dataList.get(dataList.size() - 1).getNtp030NtpDate());
        }

        paramMdl.setNtp010SessionUsrId(String.valueOf(sessionUsrSid));

        NtpCommonBiz ntBiz = new NtpCommonBiz(con__, reqMdl__);

        //案件履歴を取得
        paramMdl.setAnkenHistoryList(ntBiz.getAnkenHistoryList(con, sessionUsrSid));

        //企業・顧客履歴取得
        paramMdl.setCompanyHistoryList(ntBiz.getCompanyHistoryList(con, sessionUsrSid));

        //共有範囲を取得
        NippouDao ntpDao = new NippouDao(con);
        paramMdl.setNtp010CrangeKbn(ntpDao.getNadCrange());


        return paramMdl;
    }
    /**
    *
    * <br>[機  能] 会社マップ取得
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param sids acoSID
    * @return Map
    * @throws SQLException SQL実行時例外
    */
    private Map<Integer, AdrCompanyBaseModel> _getAbaMap(Connection con,
            Collection<Integer> sids) throws SQLException {
        HashMap<Integer, AdrCompanyBaseModel> ret = new HashMap<>();
        AdrCompanyBaseDao dao = new AdrCompanyBaseDao(con);

        List<AdrCompanyBaseModel> list = dao.select(sids);
        for (AdrCompanyBaseModel mdl : list) {
            ret.put(mdl.getAbaSid(), mdl);
        }
        return ret;
    }
    /**
    *
    * <br>[機  能] 会社マップ取得
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param sids acoSID
    * @return Map
    * @throws SQLException SQL実行時例外
    */
    private Map<Integer, AdrCompanyModel> _getAcoMap(Connection con,
            Collection<Integer> sids) throws SQLException {
        HashMap<Integer, AdrCompanyModel> ret = new HashMap<>();
        AdrCompanyDao dao = new AdrCompanyDao(con);

        List<AdrCompanyModel> list = dao.select(sids);
        for (AdrCompanyModel mdl : list) {
            ret.put(mdl.getAcoSid(), mdl);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 案件マップ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param nanSidSet 案件SID
     * @return Map
     * @throws SQLException SQL実行時例外
     */
    private Map<Integer, NtpAnkenModel> _getAnkenMap(Connection con,
            Collection<Integer> nanSidSet) throws SQLException {
        HashMap<Integer, NtpAnkenModel> ret = new HashMap<>();
        //案件情報
        NtpAnkenDao ankenDao = new NtpAnkenDao(con);
        List<NtpAnkenModel> list = ankenDao.select(nanSidSet);
        for (NtpAnkenModel nanMdl : list) {
            ret.put(nanMdl.getNanSid(), nanMdl);
        }
       return ret;
    }
    /**
     * <br>日報の登録・編集権限があるか判定する
     * @param sessionUsrSid セッションユーザ
     * @param isAdmin 管理者権限
     * @param dspUsrSid 画面表示ユーザ
     * @return int 0:権限あり　1:権限無し
     * @throws SQLException SQL実行時例外
     */
    public int isAddEditOk(
            int sessionUsrSid,
            boolean isAdmin,
            int dspUsrSid) throws SQLException {

        //管理者権限の有無
        if (isAdmin) {
            return GSConstNippou.AUTH_ADD_EDIT;
        }
        //画面表示ユーザが自分か
        if (dspUsrSid == sessionUsrSid) {
            return GSConstNippou.AUTH_ADD_EDIT;
        }

        return GSConstNippou.AUTH_NOT_ADD_EDIT;
    }
    /**
     * <br>[機  能] DBから案件情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param nanSid 案件SID
     * @param dataMdl 日報データ
     * @throws SQLException SQL実行時例外
     */
    public void _getAnkenData(Connection con,
                              int nanSid,
                              Ntp030DataModel dataMdl) throws SQLException {

        //案件情報
        NtpAnkenDao ankenDao = new NtpAnkenDao(con);
        NtpAnkenModel ankenModel = ankenDao.select(nanSid);

        if (ankenModel != null) {
            dataMdl.setAnkenSid(nanSid);
            dataMdl.setAnkenCode(ankenModel.getNanCode());
            dataMdl.setAnkenName(ankenModel.getNanName());
        }

    }

    /**
     * <br>[機  能] DBから会社情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param acoSid 会社SID
     * @param abaSid 拠点SID
     * @param dataMdl 日報データ
     * @throws SQLException SQL実行時例外
     */
    public void _readCompanyData(Connection con,
                                 int acoSid,
                                 int abaSid,
                                 Ntp030DataModel dataMdl) throws SQLException {

        //会社情報
        AdrCompanyDao companyDao = new AdrCompanyDao(con);
        AdrCompanyModel companyModel = companyDao.select(acoSid);

        if (companyModel != null) {
            dataMdl.setCompanySid(acoSid);
            dataMdl.setCompanyCode(companyModel.getAcoCode());
            dataMdl.setCompanyName(companyModel.getAcoName());
        }

        //会社拠点情報
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        AdrCompanyBaseModel companyBaseMdl = new AdrCompanyBaseModel();
        companyBaseMdl = companyBaseDao.select(abaSid);
        if (companyBaseMdl != null) {
            dataMdl.setCompanyBaseSid(abaSid);
            dataMdl.setCompanyBaseName(companyBaseMdl.getAbaName());
        }
    }

    /**
     * <br>[機  能] ユーザSIDとユーザ区分からユーザ氏名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param con コネクション
     * @return String ユーザ氏名
     * @throws SQLException SQL実行時例外
     */
    public String getUsrName(int usrSid, int usrKbn, Connection con)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ret = "";
        if (usrKbn == GSConstNippou.USER_KBN_GROUP) {
            //グループ
            String textGroup = gsMsg.getMessage("cmn.group");
            if (usrSid == GSConstNippou.NIPPOU_GROUP) {
                ret = textGroup;
            } else {
                GroupDao grpDao = new GroupDao(con);
                ret = grpDao.getGroup(usrSid).getGrpName();
            }

        } else {
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmInfModel uMdl = uDao.getUserInfoJtkb(usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            if (uMdl != null) {
                ret = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザSIDと報告日付から日報情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param sessionUsrSid ユーザSID
     * @param adminConf 管理者設定
     * @param con コネクション
     * @param myGroupFlg マイグループフラグ
     * @return NippouSearchModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<NippouSearchModel> getNtpData(
            Ntp030ParamModel paramMdl,
            int sessionUsrSid,
            NtpAdmConfModel adminConf,
            Connection con,
            boolean myGroupFlg)
    throws SQLException {

        ArrayList<NippouSearchModel> ntpMdlList = new ArrayList<NippouSearchModel>();
        NippouListSearchModel ntpSearchMdl = setNippouListSearchModel(
                paramMdl, con, sessionUsrSid);
        List<Integer> notAccessUserList = getNotAccesUserList(paramMdl.getNtp010DspGpSid(),
                sessionUsrSid, con, myGroupFlg);

        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl__);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl__,
                ntpCmnBiz.isNippouAdmin(con, reqMdl__));

        try {
            NippouSearchDao ntpSeDao = new NippouSearchDao(con);
            ntpMdlList =
                ntpSeDao.getNippouDataAll(
                        ntpSearchMdl, sessionUsrSid,
                        paramMdl.getNtp030Sort(), notAccessUserList, napMdl);

        } catch (SQLException e) {
            log__.error("日報情報の取得に失敗" + e);
            throw e;
        }

        return ntpMdlList;
    }

    /**
     * <br>[機  能] タイムラインに追加する日報情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @return NippouSearchModel
     * @throws SQLException SQL実行時例外
     */
    public JSONArray getTimeLineData(
            Ntp030ParamModel paramMdl,
            int sessionUsrSid,
            Connection con) throws SQLException {
        ArrayList<NippouSearchModel> ntpMdlList = new ArrayList<NippouSearchModel>();
        NippouListSearchModel ntpSearchMdl = setNippouListSearchModel(
                paramMdl, con, sessionUsrSid);
        List<Integer> notAccessUserList = getNotAccesUserList(paramMdl.getNtp010DspGpSid(),
                sessionUsrSid, con, ntpSearchMdl.isMyGrpFlg());

        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl__);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl__,
                ntpCmnBiz.isNippouAdmin(con, reqMdl__));

        JSONArray jsonData = new JSONArray();
        try {
            NippouSearchDao ntpSearchDao = new NippouSearchDao(con);
            ntpMdlList =
              ntpSearchDao.getNippouDataAll(
                      ntpSearchMdl, sessionUsrSid, paramMdl.getNtp030Sort(),
                      notAccessUserList, napMdl);

            //日報データリスト
            ArrayList<Ntp030DataModel> dataList = new ArrayList<Ntp030DataModel>();

            if (!ntpMdlList.isEmpty()) {
                /**管理者権限*/
                CommonBiz commonBiz = new CommonBiz();
                boolean isAdmin = commonBiz.isPluginAdmin(
                        con, reqMdl__.getSmodel(), GSConstNippou.PLUGIN_ID_NIPPOU);
                /**取得ユーザ情報SIDセット*/
                Set<Integer> usrSidSet = new HashSet<>();
                /**取得日報SIDセット*/
                Set<Integer> nipSidSet = new HashSet<>();
                /**取得案件SIDセット*/
                Set<Integer> nanSidSet = new HashSet<>();
                /**取得会社SIDセット*/
                Set<Integer> acoSidSet = new HashSet<>();
                /**取得拠点SIDセット*/
                Set<Integer> abaSidSet = new HashSet<>();
                /**日報編集権限マップ*/
                Map<Integer, Integer> eauthMap = new HashMap<>();

                for (NippouSearchModel ntpMdl : ntpMdlList) {
                    if (ntpMdl == null) {
                        //編集対象が無い場合
                        break;
                    }
                    nipSidSet.add(ntpMdl.getNipSid());
                    usrSidSet.add(ntpMdl.getUsrSid());
                    usrSidSet.add(ntpMdl.getNipAuid());
                    nanSidSet.add(ntpMdl.getNanSid());
                    acoSidSet.add(ntpMdl.getAcoSid());
                    abaSidSet.add(ntpMdl.getAbaSid());
                    eauthMap.put(ntpMdl.getNipSid(),
                            isAddEditOk(sessionUsrSid, isAdmin, ntpMdl.getUsrSid()));
                }
                UserSearchDao uDao = new UserSearchDao(con);

                /**取得ユーザ情報Map*/
                HashMap<Integer, UserSearchModel> usrMap = new HashMap<>();
                List<UserSearchModel> usrList = uDao.getUsersInfoJtkb(new ArrayList<>(usrSidSet),
                        GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);
                for (UserSearchModel user : usrList) {
                    usrMap.put(user.getUsrSid(), user);
                }

                /**案件情報Map*/
                Map<Integer, NtpAnkenModel> ankenMap =  _getAnkenMap(con, nanSidSet);
                /**会社情報Map*/
                Map<Integer, AdrCompanyModel> acoMap =  _getAcoMap(con, acoSidSet);
                /**会社拠点情報Map*/
                Map<Integer, AdrCompanyBaseModel> abaMap =  _getAbaMap(con, abaSidSet);

                NtpBinDao binDao = new NtpBinDao(con);
                /**添付情報Map*/
                Map<Integer, List<CmnBinfModel>> filMap = binDao.getFileMap(nipSidSet);

                Ntp030Dao ntpDao = new Ntp030Dao(con);
                /**コメント情報Map*/
                Map<Integer, List<Ntp030CommentModel>> cmmMap = ntpDao.getNpcMap(
                        reqMdl__, nipSidSet, eauthMap, usrMap);

                NtpGoodDao gDao = new NtpGoodDao(con__);
                /**いいね情報Map*/
                HashMap<Integer, List<NtpGoodModel>> goodMap = gDao.getIineMap(nipSidSet);
                for (Entry<Integer, UserSearchModel> entry : usrMap.entrySet()) {
                    UserSearchModel user = entry.getValue();
                    //JSON形式に変換する為、UDATEフィールドをなくす
                    user.setUsiAdate(null);
                    user.setUsiBdate(null);
                    user.setUsiEdate(null);
                    user.setUsiLtlgin(null);
                    user.setUsiEntranceDate(null);
                }

                //データセット
                for (NippouSearchModel ntpMdl : ntpMdlList) {

                    if (ntpMdl != null) {

                        //日報データリスト
                        Ntp030DataModel dataMdl = new Ntp030DataModel();

                        dataMdl.setNtp030NtpSid(ntpMdl.getNipSid());

                        CmnUsrmInfModel uMdl = null;
                        //登録者
                        uMdl = usrMap.get(ntpMdl.getNipAuid());
                        if (uMdl != null) {
                            ntpMdl.setNipAuidSei(uMdl.getUsiSei());
                            dataMdl.setNtp030NtpUsiSei(uMdl.getUsiSei());
                            ntpMdl.setNipAuidMei(uMdl.getUsiMei());
                            dataMdl.setNtp030NtpUsiSei(uMdl.getUsiMei());
                            ntpMdl.setNipAuidJkbn(uMdl.getUsrJkbn());
                            dataMdl.setNtp030NtpUsiId(uMdl.getUsrSid());
                        }


                        //報告日付
                        dataMdl.setNtp030NtpDate(UDateUtil.getYymdJ(ntpMdl.getNipDate(), reqMdl__));
                        dataMdl.setNtp030LabelDate(UDateUtil.getSlashMD(ntpMdl.getNipDate()));

                        //ユーザ情報
                        CmnUsrmInfModel userMdl = null;
                        userMdl = usrMap.get(ntpMdl.getUsrSid());
                        dataMdl.setNtp030UsrInfMdl(userMdl);

                        dataMdl.setNtp030NtpUsrName(
                                userMdl.getUsiSei() + " " + userMdl.getUsiMei());
                        dataMdl.setNtp030UsrSid(String.valueOf(ntpMdl.getUsrSid()));

                        paramMdl.setNtp030AddUsrName(
                                ntpMdl.getNipAuidSei() + " " + ntpMdl.getNipAuidMei());
                        dataMdl.setNtp030NtpAddUsrName(
                                ntpMdl.getNipUsrSei() + " " + ntpMdl.getNipUsrMei());
                        dataMdl.setNtp030AddUsrJkbn(ntpMdl.getNipAuidJkbn());

                        UDate frDate = ntpMdl.getNipFrTime();
                        UDate toDate = ntpMdl.getNipToTime();
                        //開始年月日
                        dataMdl.setNtpYear(frDate.getYear());
                        dataMdl.setNtpMonth(frDate.getMonth());
                        dataMdl.setNtpDay(frDate.getIntDay());

                        //活動分類
                        getKtbunruiLabelList(con);
                        dataMdl.setKtbunruiSid(ntpMdl.getMkbSid());
                        dataMdl.setNtp030DspKtbunrui(getKtbunrui(ntpMdl.getMkbSid()));

                        //活動方法
                        getKthouhouLabelList(con);
                        dataMdl.setKthouhouSid(ntpMdl.getMkhSid());
                        dataMdl.setNtp030DspKthouhou(getKthouhou(ntpMdl.getMkhSid()));

                        //見込み度
                        dataMdl.setMikomido(ntpMdl.getNipMikomi());
                        dataMdl.setNtp030DspMikomido(getMikomido(ntpMdl.getNipMikomi()));

                        //時間指定
                        dataMdl.setFrHour(frDate.getIntHour());
                        dataMdl.setNtp030DspFrHour(
                                StringUtil.toDecFormat(frDate.getIntHour(), "00"));

                        dataMdl.setFrMin(frDate.getIntMinute());
                        dataMdl.setNtp030DspFrMinute(
                                StringUtil.toDecFormat(frDate.getIntMinute(), "00"));

                        dataMdl.setToHour(toDate.getIntHour());
                        dataMdl.setNtp030DspToHour(
                                StringUtil.toDecFormat(toDate.getIntHour(), "00"));

                        dataMdl.setToMin(toDate.getIntMinute());
                        dataMdl.setNtp030DspToMinute(
                                StringUtil.toDecFormat(toDate.getIntMinute(), "00"));

                        //背景
                        int iniBgcolor = GSConstNippou.DF_BG_COLOR;
                        if (ntpMdl.getNipTitleClo() > GSConstNippou.DF_BG_COLOR) {
                            iniBgcolor = ntpMdl.getNipTitleClo();
                        }
                        dataMdl.setBgcolor(iniBgcolor);

                        //タイトル
                        dataMdl.setTitle(StringUtilHtml.transToHTmlForTextArea(
                                ntpMdl.getNipTitle()));

                        //詳細
                        String dspValueStr
                             = StringUtilHtml.transToHTmlForTextArea(ntpMdl.getNipDetail());
                        dataMdl.setNtp030DspValueStr(StringUtil.transToLink(
                                                     StringUtilHtml.returntoBR(dspValueStr),
                                                     StringUtil.OTHER_WIN, true));



                        //次のアクション日付
                        dataMdl.setActDateKbn(ntpMdl.getNipActDateKbn());
                        if (ntpMdl.getNipActDateKbn() != 0) {
                            UDate ntpActDate = ntpMdl.getNipActionDate();
                            dataMdl.setNtpActionYear(ntpActDate.getYear());
                            dataMdl.setNtpActionMonth(ntpActDate.getMonth());
                            dataMdl.setNtpActionDay(ntpActDate.getIntDay());
                        }

                        //次のアクション
                        dataMdl.setActionStr(ntpMdl.getNipAction());
                        String dspActionStr
                            = StringUtilHtml.transToHTmlForTextArea(ntpMdl.getNipAction());
                        dataMdl.setNtp030DspActionStr(StringUtilHtml.returntoBR(dspActionStr));


                        //案件情報取得
                        NtpAnkenModel ankenModel = ankenMap.get(ntpMdl.getNanSid());
                        if (ankenModel != null) {
                            dataMdl.setAnkenSid(ntpMdl.getNanSid());
                            dataMdl.setAnkenCode(ankenModel.getNanCode());
                            dataMdl.setAnkenName(ankenModel.getNanName());
                        }
                        dataMdl.setAnkenViewable(ntpMdl.isAnkenViewable());

                        //会社情報
                        int acoSid = ntpMdl.getAcoSid();
                        int abaSid = ntpMdl.getAbaSid();

                        if (acoMap.containsKey(acoSid)) {
                            AdrCompanyModel companyModel = acoMap.get(acoSid);
                            dataMdl.setCompanySid(acoSid);
                            dataMdl.setCompanyCode(companyModel.getAcoCode());
                            dataMdl.setCompanyName(companyModel.getAcoName());
                        }

                        //会社拠点情報
                        if (abaMap.containsKey(abaSid)) {
                            AdrCompanyBaseModel companyBaseMdl = abaMap.get(abaSid);
                            dataMdl.setCompanyBaseSid(abaSid);
                            dataMdl.setCompanyBaseName(companyBaseMdl.getAbaName());
                        }
                        //添付ファイル情報取得
                        if (filMap.containsKey(ntpMdl.getNipSid())) {
                            ArrayList<CmnBinfModel> retBin =
                                    new ArrayList<>(filMap.get(ntpMdl.getNipSid()));
                            paramMdl.setNtp030FileList(retBin);
                            dataMdl.setNtp030FileList(retBin);
                        }

                        //コメント取得
                        if (cmmMap.containsKey(ntpMdl.getNipSid())) {
                            ArrayList<Ntp030CommentModel> npcList
                                                = new ArrayList<>(cmmMap.get(ntpMdl.getNipSid()));
                            if (!npcList.isEmpty()) {
                                for (Ntp030CommentModel ntp030Mdl : npcList) {
                                    ntp030Mdl.getNtp030CommentMdl().setNpcAdate(null);
                                    ntp030Mdl.getNtp030CommentMdl().setNpcEdate(null);

                                }
                                dataMdl.setNtp030CommentList(npcList);
                            }
                        }
                        //編集可能区分取得
                        dataMdl.setNtp030AuthEditKbn(
                                isAddEditOk(sessionUsrSid, isAdmin, ntpMdl.getUsrSid()));

                        //いいね件数取得
                        int goodFlg = 0;
                        if (goodMap.containsKey(ntpMdl.getNipSid())) {
                            ArrayList<NtpGoodModel> gList = new ArrayList<NtpGoodModel>(
                                    goodMap.get(ntpMdl.getNipSid()));
                            dataMdl.setNtp030GoodCnt(gList.size());
                            //セッションユーザがいいねしているか
                            for (NtpGoodModel gMdl : gList) {
                                if (gMdl.getUsrSid() == sessionUsrSid) {
                                    goodFlg = 1;
                                }
                            }
                        }
                        dataMdl.setNtp030GoodFlg(goodFlg);

                        dataList.add(dataMdl);
                    }

                }

            }

            jsonData = JSONArray.fromObject(dataList);

        } catch (SQLException e) {
            log__.error("日報情報の取得に失敗" + e);
           throw e;
        }

        return jsonData;
    }

    /**
     * フォーム情報から検索モデルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return NippouListSearchModel 検索条件モデル
     */
    public NippouListSearchModel setNippouListSearchModel(
            Ntp030ParamModel paramMdl, Connection con, int sessionUsrSid) throws SQLException {


        NippouListSearchModel searchMdl = new NippouListSearchModel();
        int userSid = NullDefault.getInt(paramMdl.getNtp030SelectUsrSid(), -1);
        searchMdl.setUsrSid(userSid);
        if (paramMdl.getNtp030Offset() > 1) {
            searchMdl.setNtpOffset(paramMdl.getNtp030Offset());
        }
        searchMdl.setNtpLimit(dspCnt__);

        //データが存在しない場合、グループが削除されていた場合はデフォルト所属グループを返す
        NtpCommonBiz scBiz = new NtpCommonBiz(con__, reqMdl__);
        //デフォルト表示グループ
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);

        int gpSid = 0;
        boolean myGrpFlg = false;

        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getNtp010DspGpSid(), dfGpSidStr);

        if (NtpCommonBiz.isMyGroupSid(dspGpSidStr)) {
            gpSid = NtpCommonBiz.getDspGroupSid(dspGpSidStr);
            //マイグループSIDをセット
            searchMdl.setNtpSltGroupSid(dspGpSidStr.substring(1));
            myGrpFlg = true;
        } else {
            gpSid = NullDefault.getInt(paramMdl.getNtp010DspGpSid(), dfGpSid);
            //通常グループSIDをセット
            searchMdl.setNtpSltGroupSid(String.valueOf(gpSid));
        }
        searchMdl.setMyGrpFlg(myGrpFlg);

        return searchMdl;
    }

    /**
     * <br>[機  能] 活動分類リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getKtbunruiLabelList(Connection con)
    throws SQLException {

        NtpKtbunruiDao bunruiDao = new NtpKtbunruiDao(con);
        List<NtpKtbunruiModel> ret = null;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        Map<Integer, String> ktMap = new HashMap<Integer, String>();

        ret = bunruiDao.select();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //未設定
        String textNoSet = gsMsg.getMessage("cmn.notset");
        labelList.add(
                new LabelValueBean(textNoSet, "-1"));

        for (NtpKtbunruiModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getNkbName(),
                            String.valueOf(mdl.getNkbSid())));

            //活動分類をMAPに格納
            ktMap.put(mdl.getNkbSid(), mdl.getNkbName());
        }
        ktBunruiMap__ = ktMap;

        return labelList;
    }

    /**
     * <br>[機  能] 活動分類を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ktSid 活動分類SID
     * @return 活動分類
     */
    public String getKtbunrui(int ktSid) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ktBunrui = gsMsg.getMessage("cmn.notset");

        if (ktBunruiMap__ != null && !ktBunruiMap__.isEmpty()) {
            String bunrui = null;
            bunrui = (String) ktBunruiMap__.get(ktSid);
            if (bunrui != null) {
                ktBunrui = bunrui;
            }
        }
        return ktBunrui;
    }

    /**
     * <br>[機  能] 活動方法リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getKthouhouLabelList(Connection con)
    throws SQLException {

        NtpKthouhouDao houhouDao = new NtpKthouhouDao(con);
        List<NtpKthouhouModel> ret = null;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        Map<Integer, String> ktMap = new HashMap<Integer, String>();

        ret = houhouDao.select();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //未設定
        String textNoSet = gsMsg.getMessage("cmn.notset");
        labelList.add(
                new LabelValueBean(textNoSet, "-1"));

        for (NtpKthouhouModel mdl : ret) {
            labelList.add(
                    new LabelValueBean(mdl.getNkhName(),
                            String.valueOf(mdl.getNkhSid())));

            //活動方法をMAPに格納
            ktMap.put(mdl.getNkhSid(), mdl.getNkhName());
        }
        ktHouhouMap__ = ktMap;

        return labelList;
    }

    /**
     * <br>[機  能] 活動方法を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ktSid 活動方法SID
     * @return 活動方法
     */
    public String getKthouhou(int ktSid) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ktHouhou = gsMsg.getMessage("cmn.notset");

        if (ktHouhouMap__ != null && !ktHouhouMap__.isEmpty()) {
            String houhou = null;
            houhou = ktHouhouMap__.get(ktSid);
            if (houhou != null) {
                ktHouhou = houhou;
            }
        }
        return ktHouhou;
    }

    /**
     * <br>[機  能] 見込み度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mikomi 見込み度
     * @return 活動方法
     */
    public String getMikomido(int mikomi) {

        String mikomido = "10";

        switch (mikomi) {
        case 0:
            mikomido = "10";
            break;
        case 1:
            mikomido = "30";
            break;
        case 2:
            mikomido = "50";
            break;
        case 3:
            mikomido = "70";
            break;
        case 4:
            mikomido = "100";
            break;
        default:
            mikomido = "10";
        }
        return mikomido;
    }

    /**
     * <br>[機  能] 表示開始日から年コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param year 基準年
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    public ArrayList<LabelValueBean> getYearLavel(int year) {
        year--;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstNippou.YEAR_LIST_CNT; i++) {
            labelList.add(
                    new LabelValueBean(
                  gsMsg.getMessage("cmn.year",
                          new String[] {String.valueOf(year)}), String.valueOf(year)));
            year++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public ArrayList<LabelValueBean> getMonthLavel() {
        int month = 1;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 月 **/
        String strMonth = gsMsg.getMessage("cmn.month");
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + strMonth, String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 日コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayLavel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 月 **/
        String strDay = gsMsg.getMessage("cmn.day");
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + strDay, String.valueOf(day)));
            day++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 時コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  時コンボ
     */
    public ArrayList<LabelValueBean> getHourLavel() {
        int hour = 0;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        ArrayList<String> hourList = new ArrayList<String>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //未設定
        String textNoSet = gsMsg.getMessage("cmn.notset");
        labelList.add(
                new LabelValueBean(textNoSet, "-1"));
        for (int i = 0; i < 24; i++) {
            labelList.add(
                    new LabelValueBean(String.valueOf(hour), String.valueOf(hour)));
            hourList.add(String.valueOf(hour));
            hour++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 分コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList (in LabelValueBean)  分コンボ
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getMinuteLavel(Connection con) throws SQLException {
        NtpCommonBiz cmnBiz = new NtpCommonBiz(con__, reqMdl__);
        int hourDivCount = cmnBiz.getDayNippouHourMemoriCount(con);
        int min = 0;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //未設定
        String textNoSet = gsMsg.getMessage("cmn.notset");
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(
                new LabelValueBean(textNoSet, "-1"));
        int hourMemCount = 60 / hourDivCount;
        for (int i = 0; i < hourDivCount; i++) {
            labelList.add(
                    new LabelValueBean(
                            StringUtil.toDecFormat(min, "00"), String.valueOf(min)));
            min = min + hourMemCount;
        }
        return labelList;
    }

    /**
     * <br>[機  能] ソートコンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  ソートコンボ
     */
    public ArrayList<LabelValueBean> getSortLavel() {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        labelList.add(
                new LabelValueBean("登録日時▲ 更新日時▲", "0"));
        labelList.add(
                new LabelValueBean("登録日時▲ 更新日時▼", "1"));
        labelList.add(
                new LabelValueBean("登録日時▼ 更新日時▲", "2"));
        labelList.add(
                new LabelValueBean("登録日時▼ 更新日時▼", "3"));

        return labelList;
    }

    /**
     * <br>[機  能] 日報を削除(物理削除)します
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param con コネクション
     * @return 削除レコード件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteNippou(int nipSid, Connection con) throws SQLException {

        //日報情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpDataSize(nipSid, false);

        int cnt = 0;
        NtpDataDao ntpDao = new NtpDataDao(con);
        cnt = ntpDao.delete(nipSid);
        return cnt;
    }

    /**
     * <br>[機  能] json形式で日報データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param ntpSid 日報SID
     * @return jsonデータ
     * @throws SQLException SQL実行例外
     */
    public JSONObject getNtpJsonData(Connection con,
                                     String ntpSid) throws SQLException {

        NippouSearchDao dao = new NippouSearchDao(con);

        JSONObject jsonObject = null;

        //日報データ取得
        NippouSearchModel ntpMdl = null;
        ntpMdl = dao.getNippouData(Integer.parseInt(ntpSid));

        if (ntpMdl != null) {

            //日報データリスト
            Ntp030DataModel dataMdl = new Ntp030DataModel();

            if (String.valueOf(ntpMdl.getNipSid()).equals(ntpSid)) {
                dataMdl.setNtp030SelectFlg(1);
            }

            dataMdl.setNtp030NtpSid(ntpMdl.getNipSid());

            CmnUsrmInfModel uMdl = null;

            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            //登録者
            uMdl = uDao.getUserInfoJtkb(ntpMdl.getNipAuid(), -1);
            if (uMdl != null) {
                ntpMdl.setNipAuidSei(uMdl.getUsiSei());
                ntpMdl.setNipAuidMei(uMdl.getUsiMei());
                ntpMdl.setNipAuidJkbn(cuDao.getUserJkbn(ntpMdl.getNipAuid()));
            }
            dataMdl.setNtp030NtpUsiSei(uMdl.getUsiSei());
            dataMdl.setNtp030NtpUsiSei(uMdl.getUsiMei());
            dataMdl.setNtp030NtpUsiId(uMdl.getUsrSid());

            //登録日時
            dataMdl.setNtp030NtpDate(UDateUtil.getYymdJ(ntpMdl.getNipDate(), reqMdl__));

            UDate frDate = ntpMdl.getNipFrTime();
            UDate toDate = ntpMdl.getNipToTime();
            //開始年月日
            dataMdl.setNtpYear(frDate.getYear());
            dataMdl.setNtpMonth(frDate.getMonth());
            dataMdl.setNtpDay(frDate.getIntDay());

            //活動分類
            getKtbunruiLabelList(con);
            dataMdl.setKtbunruiSid(ntpMdl.getMkbSid());
            dataMdl.setNtp030DspKtbunrui(getKtbunrui(ntpMdl.getMkbSid()));

            //活動方法
            getKthouhouLabelList(con);
            dataMdl.setKthouhouSid(ntpMdl.getMkhSid());
            dataMdl.setNtp030DspKthouhou(getKthouhou(ntpMdl.getMkhSid()));

            //見込み度
            dataMdl.setMikomido(ntpMdl.getNipMikomi());
            dataMdl.setNtp030DspMikomido(getMikomido(ntpMdl.getNipMikomi()));

            //時間指定
            dataMdl.setFrHour(frDate.getIntHour());
            dataMdl.setNtp030DspFrHour(
                    StringUtil.toDecFormat(frDate.getIntHour(), "00"));

            dataMdl.setFrMin(frDate.getIntMinute());
            dataMdl.setNtp030DspFrMinute(
                    StringUtil.toDecFormat(frDate.getIntMinute(), "00"));

            dataMdl.setToHour(toDate.getIntHour());
            dataMdl.setNtp030DspToHour(
                    StringUtil.toDecFormat(toDate.getIntHour(), "00"));

            dataMdl.setToMin(toDate.getIntMinute());
            dataMdl.setNtp030DspToMinute(
                    StringUtil.toDecFormat(toDate.getIntMinute(), "00"));

            //背景
            int iniBgcolor = GSConstNippou.DF_BG_COLOR;
            if (ntpMdl.getNipTitleClo() > GSConstNippou.DF_BG_COLOR) {
                iniBgcolor = ntpMdl.getNipTitleClo();
            }
            dataMdl.setBgcolor(iniBgcolor);

            //タイトル
            dataMdl.setTitle(ntpMdl.getNipTitle());

            //詳細
            dataMdl.setValueStr(ntpMdl.getNipDetail());
            dataMdl.setNtp030DspValueStr(StringUtil.transToLink(
                    StringUtilHtml.returntoBR(
                    StringUtilHtml.transToHTmlForTextArea(
                    ntpMdl.getNipDetail())),
                    StringUtil.OTHER_WIN, true));

            //次のアクション日付
            dataMdl.setActDateKbn(ntpMdl.getNipActDateKbn());
            if (ntpMdl.getNipActDateKbn() != 0) {
                UDate ntpActDate = ntpMdl.getNipActionDate();
                dataMdl.setNtpActionYear(ntpActDate.getYear());
                dataMdl.setNtpActionMonth(ntpActDate.getMonth());
                dataMdl.setNtpActionDay(ntpActDate.getIntDay());
            }

            //次のアクション
            dataMdl.setActionStr(ntpMdl.getNipAction());
            String dspActionStr = StringUtilHtml.transToHTmlForTextArea(ntpMdl.getNipAction());
            dataMdl.setNtp030DspActionStr(StringUtilHtml.returntoBR(dspActionStr));

            //案件情報取得
            _getAnkenData(con, ntpMdl.getNanSid(), dataMdl);
            dataMdl.setAnkenViewable(ntpMdl.isAnkenViewable());

            //会社情報、アドレス帳情報を設定
            _readCompanyData(con, ntpMdl.getAcoSid(), ntpMdl.getAbaSid(), dataMdl);

            //添付ファイル情報取得
            NtpBinDao binDao = new NtpBinDao(con);
            ArrayList<CmnBinfModel> retBin = binDao.getFileList(ntpMdl.getNipSid());
            dataMdl.setNtp030FileList(retBin);

            //jsonデータ形成
            jsonObject = JSONObject.fromObject(dataMdl);
        }
        return jsonObject;
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリにコピーする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param binList 添付ファイルリスト
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void tempFileCopy(List<NtpBinModel> binList,
                                 String appRootPath,
                                 String tempDir,
                                 Connection con,
                                 String domain)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        int i = 1;
        for (NtpBinModel retBinMdl : binList) {
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, retBinMdl.getBinSid(), domain);
            if (binMdl != null) {

                //添付ファイルをテンポラリディレクトリにコピーする。
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, i);
                i++;
            }
        }
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得(json形式)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @throws IOToolsException
     * @throws IOToolsException ファイルアクセス時例外
     * @return jsonTempStr
     */
    public String setTempFiles(String tempDir, Connection con)
        throws IOToolsException {

        String jsonTempStr = null;
        JSONArray jsonTempArray = null;
        CommonBiz commonBiz = new CommonBiz();

        List<LabelValueBean> fileLabels = commonBiz.getTempFileLabelList(tempDir);
        if (!fileLabels.isEmpty()) {
            jsonTempArray = JSONArray.fromObject(fileLabels);
            jsonTempStr = jsonTempArray.toString();
        }

        return jsonTempStr;
    }

    /**
     * <br>[機  能] ユーザ情報取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @return jsonTempStr
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel getUsrInfo(String userSid)
                                     throws SQLException {

        //インスタンス生成
        CmnUsrmInfDao dao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel model = new CmnUsrmInfModel();

        //ユーザSIDをセット
        model.setUsrSid(
                Integer.parseInt(
                        NullDefault.getString(userSid, "-1")));

        model = dao.select(model);

        return model;
    }

    /**
     * <br>[機  能] コメントを登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl paramMdl
     * @param ntpSid 日報SID
     * @param commentStr コメント
     * @param userSid 登録者SID
     * @param appPath アプリケーションパス
     * @param pluginConfig プラグインコンフィグ
     * @param reqMdl リクエストモデル
     * @throws Exception SQL実行時例外
     */
    public void insertComment(
                            Ntp030ParamModel paramMdl,
                            int ntpSid,
                            String commentStr,
                            int userSid,
                            String appPath,
                            PluginConfig pluginConfig,
                            RequestModel reqMdl) throws Exception {

        NtpCommentModel npcMdl = new NtpCommentModel();
        NtpCommonBiz cmnBiz = new  NtpCommonBiz(con__, reqMdl__);
        UDate now = new UDate();

        //SID採番
        int npcSid = (int) cntCon__.getSaibanNumber(
                GSConstNippou.SBNSID_NIPPOU_COMMENT,
                GSConstNippou.SBNSID_SUB_NIPPOU_COMMENT, userSid);

        npcMdl.setNpcSid(npcSid);
        npcMdl.setNipSid(ntpSid);
        npcMdl.setUsrSid(userSid);
        npcMdl.setNpcComment(commentStr);
        npcMdl.setNpcViewKbn(0);
        npcMdl.setNpcEdate(now);
        npcMdl.setNpcEuid(userSid);
        npcMdl.setNpcAdate(now);
        npcMdl.setNpcAuid(userSid);

        NtpCommentDao npcDao = new NtpCommentDao(con__);

        //登録
        npcDao.insert(npcMdl);

        //ショートメール通知
        if (paramMdl.getSmailUseOk() == GSConstNippou.PLUGIN_NOT_USE) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }

        //日報データを取得
        NtpDataDao ntpDao = new NtpDataDao(con__);
        NtpDataModel ntpMdl = null;


        //日報コメント情報のデータ使用量を登録
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpCommentSize(npcSid, true);

        ntpMdl = ntpDao.select(Integer.valueOf(ntpSid));

        if (ntpMdl != null) {
            String url = __createNippouUrlDefo(
                    "edit", String.valueOf(ntpSid), String.valueOf(userSid), ntpMdl);
            cmnBiz.sendPlgSmail(
               con__, cntCon__, ntpMdl, npcMdl, appPath, pluginConfig, url, reqMdl);
        }
    }

    /**
     * <br>[機  能] コメントを削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param npcSid コメントSID
     * @throws Exception SQL実行時例外
     */
    public void deleteComment(int npcSid) throws Exception {

        //日報コメント情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpCommentSize(npcSid, false);

        //コメント削除
        NtpCommentDao npcDao = new NtpCommentDao(con__);
        npcDao.delete(npcSid);
    }

    /**
     * <br>[機  能] コメントを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpSid 日報SID
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getComment(
                            int ntpSid) throws Exception {
        Ntp030Dao cmtDao = new Ntp030Dao(con__);
        ArrayList<Ntp030DspCommentModel> ntpCmtList = null;
        ntpCmtList = cmtDao.getDspNpcList(reqMdl__, ntpSid);
        JSONArray jsonData = new JSONArray();
        jsonData = JSONArray.fromObject(ntpCmtList);
        return jsonData;
    }

    /**
     * <br>スケジュールに対して編集権限があるか判定する
     * @param scdSid スケジュールSID

     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditOk(
            int scdSid,
            Connection con) throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU);
        //管理者権限の有無
        if (isAdmin) {
            return true;
        }
        //上記以外は修正不可
        return false;
    }

    /**
     * <br>[機  能] 日報登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd 処理モード
     * @param ntpSid 日報SID
     * @param usrSid ユーザーSID
     * @param ntpMdl 日報情報
     * @return 日報登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createNippouUrlDefo(String cmd,
                                          String ntpSid, String usrSid,
                                          NtpDataModel ntpMdl)
    throws UnsupportedEncodingException {


        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl += "/nippou/ntp040" + ".do";
            paramUrl += "?ntp010SelectDate=" + UDateUtil.getYYMD(ntpMdl.getNipDate());
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&ntp010NipSid=" + ntpMdl.getNipSid();
            paramUrl += "&ntp010SelectUsrSid=" + ntpMdl.getUsrSid();
            paramUrl += "&ntp010SelectUsrKbn=" + "0";
            paramUrl += "&ntp010DspDate=" + UDateUtil.getYYMD(ntpMdl.getNipDate());
            paramUrl += "&dspMod=" + "1";
            paramUrl += "&ntp010DspGpSid=" + "0";


            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }
    }


    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param groupSid グループSID
     * @param myGroupFlg マイグループ選択

     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private List<Ntp030UsrLabelModel> __getUserLabelList(Connection con, int userSid,
            int groupSid, boolean myGroupFlg) throws SQLException {

        List<LabelValueBean> labelList = null;
        ArrayList<Ntp030UsrLabelModel> ntp030labelList = new ArrayList<Ntp030UsrLabelModel>();

        NtpCommonBiz ntpBiz = new NtpCommonBiz(con__, reqMdl__);
        List<Integer> notAccessUserList = getNotAccesUserList(
                String.valueOf(groupSid),
                userSid, con, myGroupFlg);

        if (myGroupFlg) {
            labelList = ntpBiz.getMyGroupUserLabelList(con, userSid, groupSid, null);
        } else {
            labelList = ntpBiz.getNormalUserLabelList(con, groupSid, null, false);
        }

        //特例アクセスで閲覧不可なユーザを削除
        ArrayList<LabelValueBean> delLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < labelList.size(); i++) {
            for (int notDspUsr:notAccessUserList) {
                LabelValueBean dspUsr = labelList.get(i);
                if (dspUsr.getValue().equals(String.valueOf(notDspUsr))) {
                    delLabel.add(dspUsr);
                }
            }
        }
        for (LabelValueBean del:delLabel) {
            labelList.remove(del);
        }

        CmnUsrmInfModel infMdl = null;
        Ntp030UsrLabelModel ntp030UsrLvMdl = null;
        List<String> usrSidList = new ArrayList<>();

        for (LabelValueBean lvb : labelList) {
            int usrSid = NullDefault.getInt(lvb.getValue(), -1);
            if (usrSid >= 0) {
                usrSidList.add(String.valueOf(usrSid));
            }
        }
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        List<CmnUsrmInfModel> ulist = usiDao.getUsersInfList(
                usrSidList.toArray(new String[usrSidList.size()]));
        Map<Integer, CmnUsrmInfModel> uMap = new HashMap<>();
        for (CmnUsrmInfModel usrModel : ulist) {
            uMap.put(usrModel.getUsrSid(), usrModel);
        }

        //ユーザ情報を取得
        for (LabelValueBean lvb : labelList) {
            ntp030UsrLvMdl = new Ntp030UsrLabelModel();
            int usrSid = NullDefault.getInt(lvb.getValue(), -1);
            if (uMap.containsKey(usrSid)) {
                infMdl = uMap.get(usrSid);
                ntp030UsrLvMdl.setValue(lvb.getValue());
                ntp030UsrLvMdl.setLabel(lvb.getLabel());
                ntp030UsrLvMdl.setUsrInfMdl(infMdl);
                ntp030labelList.add(ntp030UsrLvMdl);
            }
        }

        return ntp030labelList;
    }

    /**
     * <br>[機  能] いいねを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ntp030ParamModel
     * @param ntpSid 日報SID
     * @param usrSid ユーザSID
     * @param appPath アプリケーションパス
     * @param pluginConfig プラグインコンフィグ
     * @param reqMdl リクエストモデル
     * @throws Exception SQL実行時例外
     */
    public void insertGood(Ntp030ParamModel paramMdl,
                           int ntpSid,
                           int usrSid,
                           String appPath,
                           PluginConfig pluginConfig,
                           RequestModel reqMdl) throws Exception {


        NtpGoodDao gDao = new NtpGoodDao(con__);

        //いいね登録
        gDao.insert(ntpSid, usrSid);

        //ショートメール通知
        if (paramMdl.getSmailUseOk() == GSConstNippou.PLUGIN_NOT_USE) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }

        //日報データを取得
        NtpDataDao ntpDao = new NtpDataDao(con__);
        NtpDataModel ntpMdl = null;

        ntpMdl = ntpDao.select(Integer.valueOf(ntpSid));
        NtpCommonBiz cmnBiz = new  NtpCommonBiz(con__, reqMdl__);
        if (ntpMdl != null) {
            String url = __createNippouUrlDefo(
                    "edit", String.valueOf(ntpSid), String.valueOf(ntpMdl.getUsrSid()), ntpMdl);
            cmnBiz.sendGoodPlgSmail(
                 con__, cntCon__, ntpMdl, usrSid, appPath, pluginConfig, url);
        }

    }

    /**
     * <br>[機  能] いいねを取り消します。
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpSid 日報SID
     * @param usrSid ユーザSID
     * @throws Exception SQL実行時例外
     */
    public void deleteGood(int ntpSid, int usrSid) throws Exception {

        NtpGoodDao gDao = new NtpGoodDao(con__);

        //いいね削除
        gDao.delete(ntpSid, usrSid);
    }

    /**
     * <br>[機  能] いいねしているユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpSid 日報SID
     * @param usrSid ユーザSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<Ntp030GoodMdl> getGoodAddUserList(
            int ntpSid, int usrSid) throws SQLException {

        List<CmnUsrmInfModel> goodUsrList = new ArrayList<CmnUsrmInfModel>();
        List<Ntp030GoodMdl> ntp030GoodList = new ArrayList<Ntp030GoodMdl>();
        Ntp030GoodMdl ntp030GoodMdl = null;
        NtpGoodDao gDao = new NtpGoodDao(con__);
        List<NtpGoodModel> ngList = gDao.select(ntpSid);
        if (!ngList.isEmpty()) {
            ArrayList<String> usidList = new ArrayList<String>();
            for (NtpGoodModel ngMdl : ngList) {
                usidList.add(String.valueOf(ngMdl.getUsrSid()));
            }
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con__);
            goodUsrList =
                cuiDao.getUsersInfList((String[]) usidList.toArray(new String[usidList.size()]));

            if (!goodUsrList.isEmpty()) {
                for (int i = 0; i < goodUsrList.size(); i++) {
                    goodUsrList.get(i).setUsiAdate(null);
                    goodUsrList.get(i).setUsiBdate(null);
                    goodUsrList.get(i).setUsiEdate(null);
                    goodUsrList.get(i).setUsiLtlgin(null);
                    goodUsrList.get(i).setUsiEntranceDate(null);
                    ntp030GoodMdl = new Ntp030GoodMdl();
                    ntp030GoodMdl.setUsrMdl(goodUsrList.get(i));
                    if (goodUsrList.get(i).getUsrSid() == usrSid) {
                        ntp030GoodMdl.setGoodDelFlg(1);
                    }
                    ntp030GoodList.add(ntp030GoodMdl);
                }
            }
        }
        return ntp030GoodList;
    }


    /**
     *
     * <br>[機  能]特例アクセスの閲覧不可ユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSidStr グループSID
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param myGrpFlg マイグループフラグ
     * @throws SQLException SQL実行時例外
     * @return 特例アクセス不可ユーザ
     */
    private List<Integer> getNotAccesUserList(String gpSidStr,
            int sessionUsrSid, Connection con,
            boolean myGrpFlg) throws SQLException {
        //データが存在しない場合、グループが削除されていた場合はデフォルト所属グループを返す
        NtpCommonBiz nBiz = new NtpCommonBiz(con__, reqMdl__);
        //デフォルト表示グループ
        String dfGpSidStr = nBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);
        //表示グループを設定
        int gpSid = 0;
        if (myGrpFlg) {
            gpSid = NtpCommonBiz.getDspGroupSid(gpSidStr);
            if (gpSid == 0) {
                gpSid = dfGpSid;
            }
        } else {
            gpSid = NullDefault.getInt(gpSidStr, dfGpSid);
        }

        //スケジュール個人設定で取得した表示順を取得する。
        NtpPriConfModel pconf = nBiz.getNtpPriConfModel(con, sessionUsrSid);
        int sortKey1 = pconf.getNprSortKey1();
        int orderKey1 = pconf.getNprSortOrder1();
        int sortKey2 = pconf.getNprSortKey2();
        int orderKey2 = pconf.getNprSortOrder2();


        //除外するユーザSIDを設定
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(new Integer(GSConstUser.SID_ADMIN));
        usrSids.add(new Integer(GSConstUser.SID_SYSTEM_MAIL));
        usrSids.add(sessionUsrSid);

        ArrayList<UserSearchModel> belongList = null;
        List<Integer> notAccessUserList = new ArrayList<Integer>();
        UserSearchDao usDao = new UserSearchDao(con);
        NippouDao ntpDao = new NippouDao(con);
        if (myGrpFlg) {
            //マイグループの所属ユーザを取得
            belongList = usDao.getMyGroupBelongUserInfoJtkb(gpSid, sessionUsrSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            //特例アクセス不可ユーザをすべて取得
            List<Integer> allNotAccessUserList
            = ntpDao.getSpAccessUserList(-1, sessionUsrSid, false, -1);
            //特定グループだけのアクセス不可ユーザを取得
            for (UserSearchModel belong:belongList) {
                if (allNotAccessUserList.indexOf(belong.getUsrSid()) > -1) {
                    notAccessUserList.add(belong.getUsrSid());
                }
            }

        } else {
            //グループの所属ユーザを取得
            belongList = usDao.getBelongUserInfoJtkb(
                    gpSid, usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = ntpDao.getSpAccessUserList(gpSid, sessionUsrSid, false, -1);
        }

        return notAccessUserList;
    }

}