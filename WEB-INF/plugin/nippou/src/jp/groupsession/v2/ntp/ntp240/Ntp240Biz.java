package jp.groupsession.v2.ntp.ntp240;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NtpPriTargetDao;
import jp.groupsession.v2.ntp.dao.NtpTargetDao;
import jp.groupsession.v2.ntp.dao.NtpTmpMemberDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTmpMemberModel;
import jp.groupsession.v2.ntp.ntp230.Ntp230Dao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 目標設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp240Biz {

    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp240Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp240ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws Exception 実行エラー
     */
    public void setInitData(Ntp240ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws Exception {

        paramMdl.setDspMod(GSConstNippou.DSP_MOD_TARGET);

        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID
        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp240SelectUsrSid())) {
            paramMdl.setNtp240SelectUsrSid(String.valueOf(sessionUsrSid));
        }

        //グループ設定
        boolean admFlg = __setGrpCombo(paramMdl, umodel, con, sessionUsrSid);

        //ユーザ設定
        int userSid = __setUsrCombo(paramMdl, con, umodel, sessionUsrSid, admFlg);

        //期間コンボ設定
        __setDateCombo(paramMdl);

        //選択したユーザの日報テンプレートを取得
        __getUsrTemplate(paramMdl, con, userSid);

        //表示ユーザの目標取得
        if (paramMdl.getNtp240TargetList() != null && !paramMdl.getNtp240TargetList().isEmpty()) {

            List<Ntp240DspTargetModel> dspTrgList = new ArrayList<Ntp240DspTargetModel>();
            Ntp240DspTargetModel dspTrgMdl = null;
            List<NtpPriTargetModel> priTrgList = null;
            NtpPriTargetModel priTrgMdl = null;
            NtpPriTargetDao priTrgDao = new NtpPriTargetDao(con);

            //画面表示年月
            int dspYear = Integer.valueOf(paramMdl.getNtp240Year());
            int dspMonth = Integer.valueOf(paramMdl.getNtp240Month());

            UDate dspDate = new UDate();
            dspDate.setYear(dspYear);
            dspDate.setMonth(dspMonth + 1);
            dspDate.setZeroDdHhMmSs();

            for (int i = 0; i < GSConstNippou.DSP_MONTH_CNT; i++) {

                priTrgList = new ArrayList<NtpPriTargetModel>();
                dspTrgMdl = new Ntp240DspTargetModel();

                for (NtpTargetModel trgMdl : paramMdl.getNtp240TargetList()) {

                    if (trgMdl != null) {

                        //ユーザデータ取得
                        priTrgMdl = priTrgDao.select(
                                trgMdl.getNtgSid(), userSid, dspDate.getYear(), dspDate.getMonth());

                        if (priTrgMdl == null) {

                            //データがない場合は一番最近のデータの値を設定
                            priTrgMdl = priTrgDao.getLatelyData(trgMdl.getNtgSid(), userSid);

                            if (priTrgMdl == null) {
                                //データがない場合はデフォルト値を設定
                                priTrgMdl = new NtpPriTargetModel();
                                //目標SID
                                priTrgMdl.setNtgSid(trgMdl.getNtgSid());
                                //デフォルト値
                                priTrgMdl.setNpgTarget(trgMdl.getNtgDef());
                                //実績
                                priTrgMdl.setNpgRecord(new Long(0));
                            } else {
                                //実績
                                priTrgMdl.setNpgRecord(new Long(0));
                            }
                        }

                        //名前
                        priTrgMdl.setNpgTargetName(trgMdl.getNtgName());
                        //単位
                        priTrgMdl.setNpgTargetUnit(trgMdl.getNtgUnit());

                        if (priTrgMdl.getNpgRecord() >= priTrgMdl.getNpgTarget()) {
                            //目標を達成している場合
                            priTrgMdl.setNpgTargetKbn(1);
                        }

                        //達成率を取得
                        double ratio = 0;
                        double unratio = 0;
                        Long npgTarget = new Long(1);
                        if (priTrgMdl.getNpgTarget() != null && priTrgMdl.getNpgTarget() > 0) {
                            npgTarget = priTrgMdl.getNpgTarget();
                        }
                        ratio = ((double) priTrgMdl.getNpgRecord() / (double) npgTarget) * 100;
                        BigDecimal bi = new BigDecimal(String.valueOf(ratio));
                        ratio = bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                        String ratioStr = "";

                        if (String.valueOf(ratio).length() > 5) {
                            ratioStr = "99999";
                        } else {
                            ratioStr = String.valueOf(ratio);
                        }

                        priTrgMdl.setNpgTargetRatioStr(String.valueOf(ratioStr));

                        int intRatio = (int) ratio;
                        if (intRatio >= 100) {
                            intRatio = 100;
                        }
                        unratio = 100 - intRatio;

                        if (intRatio < 100) {
                            unratio = unratio - 0.1;
                        }

                        priTrgMdl.setNpgTargetRatio(intRatio);
                        priTrgMdl.setNpgTargetUnRatio(unratio);

                        priTrgList.add(priTrgMdl);
                    }
                }
                //ユーザSID
                dspTrgMdl.setUsrSid(userSid);
                //年
                dspTrgMdl.setYear(dspDate.getYear());
                //月
                dspTrgMdl.setMonth(dspDate.getMonth());

                dspTrgMdl.setNtgList(priTrgList);
                dspTrgList.add(dspTrgMdl);
                //一月進める
                dspDate.addMonth(-1);
            }
            paramMdl.setNtp240DspTargetList(dspTrgList);
        }
    }

    /**
     * グループコンボ設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp240Form

     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws Exception 実行例外
     * @return admFlg 管理者権限
     */
    private boolean __setGrpCombo(Ntp240ParamModel paramMdl,

                                BaseUserModel umodel,
                                Connection con,
                                int sessionUsrSid) throws Exception {

        GroupBiz grpBiz = new GroupBiz();

        //管理者判定
        boolean admFlg = false;
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (cmnBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            //すべてのグループを取得
            paramMdl.setNtp240GpLabelList(getGroupLabelList(con, sessionUsrSid));
            admFlg = true;
        } else {
            //自分が管理者になっているグループを取得
            List<LabelValueBean> grpLabel = null;
            grpLabel = grpBiz.getAdminGroupLabelList(sessionUsrSid, con, false, gsMsg);
            if (!grpLabel.isEmpty()) {
                List<NtpLabelValueModel> ntpLabel = new ArrayList<NtpLabelValueModel>();
                NtpLabelValueModel labelMdl = null;
                for (LabelValueBean bean : grpLabel) {
                    labelMdl = new NtpLabelValueModel(bean.getLabel(), bean.getValue(), null);
                    ntpLabel.add(labelMdl);
                }
                if (!ntpLabel.isEmpty()) {
                    paramMdl.setNtp240GpLabelList(ntpLabel);
                    admFlg = true;
                }
            }
        }

        return admFlg;
    }

    /**
     * ユーザコンボ設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp240Form
     * @param con コネクション
     * @param umodel ユーザ基本情報モデル
     * @param sessionUsrSid セッションユーザSID
     * @param admFlg 管理者権限
     * @throws Exception 実行例外
     * @return userSid ユーザSID
     */
    private int __setUsrCombo(Ntp240ParamModel paramMdl,
                                Connection con,
                                BaseUserModel umodel,
                                int sessionUsrSid,
                                boolean admFlg) throws Exception {

        GroupBiz gbiz = new GroupBiz();
        String dfGpSidStr = String.valueOf(gbiz.getDefaultGroupSid(sessionUsrSid, con));
        String dspGpSidStr = NullDefault.getString(paramMdl.getNtp240DspGpSid(), dfGpSidStr);
        paramMdl.setNtp240DspGpSid(dspGpSidStr);
        int dspGpSid = Integer.valueOf(dspGpSidStr);
        int userSid = Integer.valueOf(paramMdl.getNtp240SelectUsrSid());

        if (admFlg) {

            List<Integer> notAccessUserList = null;
            NippouDao ntpDao = new NippouDao(con);
            notAccessUserList = ntpDao.getSpAccessUserList(dspGpSid, sessionUsrSid, false, -1);

            //ユーザコンボ
            List<UsrLabelValueBean> userLabel = getUserLabelList(con, dspGpSid, sessionUsrSid);

            //特例アクセス不可ユーザを取得し、表示から除外する
            ArrayList<UsrLabelValueBean> delLabelList = new ArrayList<UsrLabelValueBean>();
            for (int notAccessSid:notAccessUserList) {
                for (UsrLabelValueBean oneUser:userLabel) {
                    if (oneUser.getValue().equals(String.valueOf(notAccessSid))) {
                        delLabelList.add(oneUser);
                    }
                }
            }
            for (UsrLabelValueBean delLabel:delLabelList) {
                userLabel.remove(delLabel);
            }

            paramMdl.setNtp240UserLabel(userLabel);

            boolean userExist = false;
            if (userLabel != null && !userLabel.isEmpty()) {
                for (LabelValueBean lvb : userLabel) {
                    String lvbVal = lvb.getValue();
                    if (Integer.parseInt(lvbVal) == userSid) {
                        userExist = true;
                        break;
                    }
                }
                if (!userExist) {
                    if (!NtpCommonBiz.isMyGroupSid(paramMdl.getNtp010DspGpSid())) {
                        userSid = Integer.parseInt(userLabel.get(0).getValue());
                    }
                }
            } else {
                //ユーザが存在しない場合
                userSid = -1;
            }
        }

        //ユーザ名設定
        paramMdl.setNtp240UserName(umodel.getUsiseimei());
        paramMdl.setNtp240SelectUsrSid(String.valueOf(userSid));

        return userSid;
    }

    /**
     * ユーザテンプレート取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp240Form
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    private void __getUsrTemplate(Ntp240ParamModel paramMdl,
                                Connection con,
                                int userSid) throws Exception {

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
                            paramMdl.setNtp240Template(String.valueOf(nsmMdl.getNtmTmpSid()));
                            break;
                        }
                    }
                }
            }

            if (selTmpSid != -1) {
                //テンプレートに設定されている目標を取得
                NtpTmpTargetDao nptDao = new NtpTmpTargetDao(con);
                ArrayList<Integer> nptSidList = null;
                nptSidList = nptDao.getNtgSids(selTmpSid);
                if (!nptSidList.isEmpty()) {
                    //目標情報を取得する
                    Ntp230Dao targetDao = new Ntp230Dao(con);
                    List<NtpTargetModel> targetList = null;
                    targetList = targetDao.getTargetList(nptSidList);
                    if (!targetList.isEmpty()) {
                        paramMdl.setNtp240TargetList(targetList);
                    }
                }
            }
        }
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する
     * <br>[解  説] 管理者設定の共有範囲が「ユーザ全員で共有」の場合有効な全てのグループを取得する。
     * <br>「所属グループ内のみ共有可」の場合、ユーザが所属するグループのみを返す。
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getGroupLabelList(Connection con,
            int usrSid) throws SQLException {

        List < NtpLabelValueModel > labelList = null;

        NtpCommonBiz scBiz = new NtpCommonBiz(con__, reqMdl__);
        labelList = scBiz.getGroupLabelForNippou2(
                usrSid, con, false);

        return labelList;
    }

    /**
     * 期間コンボを設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp240Form
     */
    private void __setDateCombo(Ntp240ParamModel paramMdl) {

        //基準日
        String strDspDate = new UDate().getDateString();
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //年月日コンボを設定
        paramMdl.setNtp240YearLabel(__getYearLabelList(paramMdl));   //年
        paramMdl.setNtp240MonthLabel(__getMonthLabelList()); //月

        //期間設定
        paramMdl.setNtp240Year(
                NullDefault.getString(paramMdl.getNtp240Year(), dspDate.getStrYear()));
        paramMdl.setNtp240Month(
                NullDefault.getString(paramMdl.getNtp240Month(),
                        String.valueOf(dspDate.getMonth())));
    }

    /**
     * 年コンボを作成する

     * @param paramMdl Ntp240Form
     * @return List (in LabelValueBean)  コンボ
     */
    private List <LabelValueBean> __getYearLabelList(
                                            Ntp240ParamModel paramMdl) {

        int sysYear = (new UDate()).getYear();

        if (!StringUtil.isNullZeroStringSpace(paramMdl.getNtp240Year())) {
            sysYear = Integer.valueOf(paramMdl.getNtp240Year());
        }

        int start = sysYear - 5;
        int end = sysYear + 5;
        GsMessage gsMsg = new GsMessage();
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        for (int value = start; value <= end; value++) {
            labelList.add(
              new LabelValueBean(
                 gsMsg.getMessage(
                         "cmn.year", new String[] {String.valueOf(value)}),
                          String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * 月コンボを作成する

     * @return List (in LabelValueBean)  コンボ
     */
    private List <LabelValueBean> __getMonthLabelList() {

        int start = 1;
        int end = 12;
        GsMessage gsMsg = new GsMessage();
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                            value + gsMsg.getMessage("cmn.month"), String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSid セッションユーザSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserLabelList(Connection con,
        int groupSid, int userSid) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List < UsrLabelValueBean > labelList = null;
        UserBiz usrBiz = new UserBiz();
        labelList = usrBiz.getNormalUserLabelList(con, groupSid, null, false, gsMsg);
        return labelList;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param targetVal 目標
     * @param recordVal 実績
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(String targetVal,
                                      String recordVal)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        GSValidateNippou.validateCmnFieldTextNumber(
                errors,
                GSConstNippou.TEXT_MOKUHYOU,
                targetVal,
               "targetVal",
                GSConstNippou.MAX_LENGTH_RECORD,
                true);

        GSValidateNippou.validateCmnFieldTextNumber(
                errors,
                GSConstNippou.TEXT_JISSEKI,
                recordVal,
               "recordVal",
                GSConstNippou.MAX_LENGTH_RECORD,
                true);

        return errors;
    }

    /**
     * <br>[機  能] 目標を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param year 年
     * @param month 月
     * @param usrSid ユーザ
     * @param ntgSid セッションユーザSID
     * @param recordVal 実績値
     * @param targetVal 目標値
     * @param umodel ユーザモデル
     * @throws SQLException SQL実行時例外
     */
    public void setTarget(Connection con,
                                 int year,
                                 int month,
                                 int usrSid,
                                 int ntgSid,
                                 Long recordVal,
                                 Long targetVal,
                                 BaseUserModel umodel) throws SQLException {


        NtpPriTargetDao dao = new NtpPriTargetDao(con);

        NtpPriTargetModel ptgMdl = new NtpPriTargetModel();

        ptgMdl.setNtgSid(ntgSid);
        ptgMdl.setUsrSid(usrSid);
        ptgMdl.setNpgRecord(recordVal);
        ptgMdl.setNpgTarget(targetVal);
        ptgMdl.setNpgYear(year);
        ptgMdl.setNpgMonth(month);

        UDate date = new UDate();
        UDate ptgDate = date.cloneUDate();

        ptgDate.setYear(year);
        ptgDate.setMonth(month + 1);
        ptgDate.setZeroDdHhMmSs();
        ptgMdl.setNpgDate(ptgDate);

        ptgMdl.setNpgEdate(date);
        ptgMdl.setNpgEuid(umodel.getUsrsid());


        int count = dao.update(ptgMdl);
        if (count <= 0) {

            ptgMdl.setNpgAdate(date);
            ptgMdl.setNpgAuid(umodel.getUsrsid());

            //レコードがない場合は作成
            dao.insert(ptgMdl);
        }
    }

    /**
     * <br>[機  能] 個人の目標データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param year 年
     * @param month 月
     * @param usrSid ユーザ
     * @param ntgSid セッションユーザSID
     * @return JSONObject 目標データ
     * @throws SQLException SQL実行時例外
     */
    public JSONObject getPriTargetMdl(Connection con,
                                 int year,
                                 int month,
                                 int usrSid,
                                 int ntgSid) throws SQLException {

        JSONObject json = new JSONObject();
        NtpPriTargetDao dao = new NtpPriTargetDao(con);
        NtpPriTargetModel ptgMdl = null;
        ptgMdl = dao.select(ntgSid, usrSid, year, month);

        //実績、目標格納マップ
        Map<String, String> ntgDataMap = new HashMap<String, String>();

        if (ptgMdl != null) {
            ntgDataMap.put("npgRecord", String.valueOf(ptgMdl.getNpgRecord()));
            ntgDataMap.put("npgTarget", String.valueOf(ptgMdl.getNpgTarget()));

            if (ptgMdl.getNpgRecord() >= ptgMdl.getNpgTarget()) {
                //目標を達成している場合
                ntgDataMap.put("npgTargetKbn", String.valueOf(1));
            } else {
                ntgDataMap.put("npgTargetKbn", String.valueOf(0));
            }


        } else {

            //データがない場合は一番最近のデータの値を設定
            ptgMdl = dao.getLatelyData(ntgSid, usrSid);

            if (ptgMdl == null) {
                //デフォルト値を取得
                NtpTargetDao trgDao = new NtpTargetDao(con);
                NtpTargetModel ntgMdl = null;
                ntgMdl = trgDao.select(ntgSid);
                if (ntgMdl != null) {
                    ptgMdl = new NtpPriTargetModel();
                    //目標SID
                    ptgMdl.setNtgSid(ntgMdl.getNtgSid());
                    //デフォルト値
                    ptgMdl.setNpgTarget(ntgMdl.getNtgDef());
                    //実績
                    ptgMdl.setNpgRecord(new Long(0));
                }
            } else {
                //実績
                ptgMdl.setNpgRecord(new Long(0));
            }

            if (ptgMdl.getNpgRecord() >= ptgMdl.getNpgTarget()) {
                //目標を達成している場合
                ntgDataMap.put("npgTargetKbn", String.valueOf(1));
            } else {
                ntgDataMap.put("npgTargetKbn", String.valueOf(0));
            }

            ntgDataMap.put("npgRecord", String.valueOf(0));
            ntgDataMap.put("npgTarget", String.valueOf(ptgMdl.getNpgTarget()));

        }

        //達成率を取得
        double ratio = 0;
        double unratio = 0;
        Long npgTarget = new Long(1);
        if (ptgMdl.getNpgTarget() != null && ptgMdl.getNpgTarget() > 0) {
            npgTarget = ptgMdl.getNpgTarget();
        }
        ratio = ((double) ptgMdl.getNpgRecord() / (double) npgTarget) * 100;
        BigDecimal bi = new BigDecimal(String.valueOf(ratio));
        ratio = bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        ptgMdl.setNpgTargetRatioStr(String.valueOf(ratio));

        String ratioStr = "";
        if (String.valueOf(ratio).length() > 5) {
            ratioStr = "99999";
        } else {
            ratioStr = String.valueOf(ratio);
        }

        int intRatio = (int) ratio;
        if (intRatio >= 100) {
            intRatio = 100;
        }
        unratio = 100 - intRatio;

        if (intRatio < 100) {
            unratio = unratio - 0.1;
        }

        ptgMdl.setNpgTargetRatio(intRatio);
        ptgMdl.setNpgTargetUnRatio(unratio);

        ntgDataMap.put("npgTargetRatioStr", ratioStr);
        ntgDataMap.put("npgTargetRatio", String.valueOf(ptgMdl.getNpgTargetRatio()));
        ntgDataMap.put("npgTargetUnRatio", String.valueOf(ptgMdl.getNpgTargetUnRatio()));

        json = JSONObject.fromObject(ntgDataMap);

        return json;
    }

    /**
     * <br>[機  能] 個人の年度目標データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param year 年
     * @param month 年
     * @param usrSid ユーザ
     * @param ntgSid セッションユーザSID
     * @return JSONObject 目標データ
     * @throws SQLException SQL実行時例外
     */
    public JSONObject getYearData(Connection con,
                                 int year,
                                 int month,
                                 int usrSid,
                                 int ntgSid) throws SQLException {

        JSONObject json = null;

        //年度を取得
        NippouDao ntpDao = new NippouDao(con);
        int kishuMonth = 0;
        int thisNendo = 0;
        kishuMonth = ntpDao.getKishuMonth();
        if (kishuMonth == 0) {
            kishuMonth = Integer.valueOf(GSConstCommon.DEFAULT_KISYU);
        }
        thisNendo = __getNend(year, month, kishuMonth);

        //目標のデフォルト値を取得
        NtpTargetModel ntgMdl = null;
        List<NtpPriTargetModel> ntgList = new ArrayList<NtpPriTargetModel>();
        NtpTargetDao trgDao = new NtpTargetDao(con);
        ntgMdl = trgDao.select(ntgSid);
        int selYear = thisNendo;
        int selmonth = kishuMonth;
        double ratio = 0;
        boolean yearFlg = false;
        double allTarget = 0;
        double allRecord = 0;

        if (ntgMdl != null) {
            for (int i = kishuMonth; i < kishuMonth + 12; i++) {

                //年間の目標データを取得
                if (i > 12) {
                    selmonth = i - 12;

                    if (!yearFlg) {
                        //年を進める
                        selYear = selYear + 1;
                        yearFlg = true;
                    }
                } else {
                    selmonth = i;
                }

                NtpPriTargetModel ptgMdl = null;
                NtpPriTargetDao dao = new NtpPriTargetDao(con);
                ptgMdl = dao.select(ntgSid, usrSid, selYear, selmonth);

                if (ptgMdl == null) {

                    //データがない場合は一番最近のデータの値を設定
                    ptgMdl = dao.getLatelyData(ntgSid, usrSid);

                    if (ptgMdl != null) {
                        ntgMdl.setNtgDef(ptgMdl.getNpgTarget());
                    }

                    //デフォルト値を設定
                    ptgMdl = new NtpPriTargetModel();
                    ptgMdl.setNpgRecord(new Long(0));
                    ptgMdl.setNpgTarget(ntgMdl.getNtgDef());
                    ptgMdl.setNpgMonth(selmonth);
                    ptgMdl.setNpgYear(selYear);
                    ptgMdl.setNpgTargetRatio(0);
                } else {
                    ptgMdl.setNpgDate(null);
                    ptgMdl.setNpgAdate(null);
                    ptgMdl.setNpgEdate(null);

                    //達成率を取得
                    Long npgTarget = new Long(1);
                    if (ptgMdl.getNpgTarget() != null && ptgMdl.getNpgTarget() > 0) {
                        npgTarget = ptgMdl.getNpgTarget();
                    }
                    ratio = ((double) ptgMdl.getNpgRecord() / (double) npgTarget) * 100;
                    BigDecimal bi = new BigDecimal(String.valueOf(ratio));
                    ptgMdl.setNpgTargetRatio(
                            bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
                }

                allTarget += ptgMdl.getNpgTarget();
                allRecord += ptgMdl.getNpgRecord();

                if (ptgMdl.getNpgRecord() >= ptgMdl.getNpgTarget()) {
                    //目標を達成している場合
                    ptgMdl.setNpgTargetKbn(1);
                }

                ntgList.add(ptgMdl);
            }
        }

        if (!ntgList.isEmpty()) {

            //表示用データ生成
            Ntp240DspTargetModel dspMdl = new Ntp240DspTargetModel();

            //年度
            dspMdl.setYear(thisNendo);

            //期首月
            dspMdl.setMonth(kishuMonth);

            //目標SID
            dspMdl.setTargetSid(ntgSid);

            //目標名
            dspMdl.setTargetName(StringUtilHtml.transToHTml(ntgMdl.getNtgName()));

            //単位
            dspMdl.setTargetUnit(ntgMdl.getNtgUnit());

            //ユーザSID
            dspMdl.setUsrSid(usrSid);

            //ユーザ名＋ユーザ無効フラグ
            CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usrMdl = usrDao.selectUserNameAndJtkbn(usrSid);
            if (usrMdl != null) {
                dspMdl.setUsrName(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
                dspMdl.setUsrUkoFlg(usrMdl.getUsrUkoFlg());
            }

            //年度データ
            dspMdl.setNtgList(ntgList);

            //年度平均達成率
            if (allTarget == 0) {
                allTarget = 1;
            }
            double allRatio = 0;
            allRatio = (allRecord / allTarget) * 100;
            BigDecimal bi = new BigDecimal(String.valueOf(allRatio));
            dspMdl.setTargetRatio(
                    bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

            //jsonデータ生成
            json = new JSONObject();
            json = JSONObject.fromObject(dspMdl);
        }

        return json;
    }

    /**
     * <br>[機  能] 指定した年、月の年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param kishuMonth 期首月
     *
     * @return nendYear
     */
    private int __getNend(int year, int month, int kishuMonth) {
        int nendYear = 0;

        if (month < kishuMonth) {
            nendYear = year - 1;
        } else {
            nendYear = year;
        }
        return nendYear;
    }
}