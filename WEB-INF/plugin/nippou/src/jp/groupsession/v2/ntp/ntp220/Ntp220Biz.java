package jp.groupsession.v2.ntp.ntp220;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220AnkenModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220AnkenSearchModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220ContentDataModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220MenuParam;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220SearchMenuModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220ShohinModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220StateModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220TotalModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] 日報分析画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Ntp220Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp220Biz.class);

    /** コネクション */
    protected Connection con__ = null;
    /** メニュー表示件数 */
    private static final int MENU_CONTENT_COUNT = 5;
    /** 案件情報表示件数 */
    private static final int ANKEN_DATA_COUNT = 5;
    /** 検索結果表示件数 */
    private static final int SEARCH_CONTENT_COUNT = 10;
    /** 検索結果表示件数 */
    private static final int CONTENT_MONEY_LIST_COUNT = 5;

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp220Biz(Connection con,
            RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp220ParamModel
     * @param buMdl セッションユーザモデル
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    public void setInitData(
            Ntp220ParamModel paramMdl,
            BaseUserModel buMdl,
            Connection con) throws Exception {

        paramMdl.setDspMod(GSConstNippou.DSP_MOD_BUNSEKI);
        //グループ設定
        boolean admFlg = __setGrpCombo(paramMdl, buMdl, con);

        if (paramMdl.getNtp220mode() == 1) {
            if (!StringUtil.isNullZeroStringSpace(paramMdl.getNtp220SelectUsrSid())) {
                paramMdl.setNtp220SelectUsrSid(paramMdl.getNtp220SelectUsrSid());
            } else {
                paramMdl.setNtp220SelectUsrSid(String.valueOf(buMdl.getUsrsid()));
            }

            //ユーザ設定
            __setUsrCombo(paramMdl, con, buMdl, admFlg);

            paramMdl.setNtp220DspUsrName(buMdl.getUsisei() + " " + buMdl.getUsimei());

        } else {
            paramMdl.setNtp220SelectUsrSid("");
        }

        //選択日付設定
        UDate defToDate = new UDate();
        defToDate.setDay(defToDate.getMaxDayOfMonth());
        paramMdl.setNtp220DateToStr(NullDefault.getString(
                paramMdl.getNtp220DateToStr(), defToDate.getDateString("/")));
        UDate defFrDate = defToDate.cloneUDate();
        if (!StringUtil.isNullZeroString(paramMdl.getNtp220DateToStr())) {
            defFrDate = getDateFromString(paramMdl.getNtp220DateToStr());
        }
        defFrDate.addMonth(-5);
        defFrDate.setDay(1);
        paramMdl.setNtp220DateFrStr(NullDefault.getString(
                paramMdl.getNtp220DateFrStr(), defFrDate.getDateString("/")));

        //業種一覧を取得
        NtpCommonBiz cBiz = new NtpCommonBiz(con, reqMdl__);
        paramMdl.setNtp220GyoushuList(cBiz.getGyomuList(con));

        if (paramMdl.getNtp220GyoushuList() != null && !paramMdl.getNtp220GyoushuList().isEmpty()) {
            paramMdl.setNtp220GyoushuSid(
                    Integer.valueOf(paramMdl.getNtp220GyoushuList().get(0).getValue()));
        }

        //カテゴリリスト取得
        paramMdl.setNtp220CategoryList(cBiz.getCategoryLavel());
    }


    /**
     * ユーザコンボ設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp220ParamModel
     * @param con コネクション
     * @param umodel ユーザ基本情報モデル
     * @param admFlg 管理者権限

     * @throws Exception 実行例外
     * @return userSid ユーザSID
     */
    private int __setUsrCombo(Ntp220ParamModel paramMdl,
            Connection con,
            BaseUserModel umodel,
            boolean admFlg) throws Exception {


        int userSid = Integer.valueOf(paramMdl.getNtp220SelectUsrSid());
        int grpSid = Integer.valueOf(paramMdl.getNtp220GroupSid());

        if (admFlg) {
            //ユーザコンボ
            List<UsrLabelValueBean> userLabel = getUserLabelList(
                    con, grpSid, umodel.getUsrsid());
            paramMdl.setNtp220UserLabel(userLabel);

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
                    userSid = Integer.parseInt(userLabel.get(0).getValue());
                }
            } else {
                //ユーザが存在しない場合
                userSid = -1;
            }
        }

        return userSid;
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

        //閲覧許可のないユーザを取得
        NippouDao ntpDao = new NippouDao(con);
        List<Integer> notAccessList = ntpDao.getNotAccessUserList(groupSid, userSid);

        labelList = usrBiz.getNormalUserLabelList(con, groupSid, null, false, gsMsg);

        for (int i = 0; i < labelList.size(); i++) {
            if (notAccessList.contains(Integer.parseInt(labelList.get(i).getValue()))) {
                labelList.remove(i);
                i--;
            }
        }

        return labelList;
    }

    /**
     * グループコンボ設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp220ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws Exception 実行例外
     * @return admFlg 管理者権限
     */
    private boolean __setGrpCombo(Ntp220ParamModel paramMdl,
            BaseUserModel umodel,
            Connection con) throws Exception {

        GroupBiz grpBiz = new GroupBiz();
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl__);

        //管理者判定
        boolean admFlg = false;
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (cmnBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            //すべてのグループを取得
            if (paramMdl.getNtp220mode() == 0) {
                paramMdl.setNtp220GroupLavel(
                        ntpBiz.getGroupLabelForNippou4(umodel.getUsrsid(), con, true));
            } else {
                paramMdl.setNtp220GroupLavel(getGroupLabelList(con, umodel.getUsrsid()));
            }
            admFlg = true;
        } else {

            List<NtpLabelValueModel> ntpLabel = new ArrayList<NtpLabelValueModel>();
            NtpLabelValueModel labelMdl = null;

            //自分の所属するデフォルトグループを取得
            CmnGroupmModel bGrpMdl = new CmnGroupmModel();
            int belongDefGrpSid = grpBiz.getDefaultGroupSid(umodel.getUsrsid(), con);
            GroupDao dao = new GroupDao(con);
            bGrpMdl = dao.getGroup(belongDefGrpSid);
            labelMdl = new NtpLabelValueModel(bGrpMdl.getGrpName(),
                    String.valueOf(bGrpMdl.getGrpSid()), null);
            ntpLabel.add(labelMdl);


            //自分が所属しているグループと管理者になっているグループを取得
            List<LabelValueBean> grpLabel = null;
            grpLabel = grpBiz.getAdminGroupLabelList(umodel.getUsrsid(), con, false, gsMsg);
            if (!grpLabel.isEmpty()) {

                for (LabelValueBean bean : grpLabel) {
                    if (!bean.getValue().equals(String.valueOf(bGrpMdl.getGrpSid()))) {
                        labelMdl = new NtpLabelValueModel(bean.getLabel(), bean.getValue(), null);
                        ntpLabel.add(labelMdl);
                    }
                }
                if (!ntpLabel.isEmpty()) {
                    admFlg = true;
                }
            }
            paramMdl.setNtp220GroupLavel(ntpLabel);
        }
        GroupBiz gbiz = new GroupBiz();
        String dfGpSidStr = String.valueOf(gbiz.getDefaultGroupSid(umodel.getUsrsid(), con));

        paramMdl.setNtp220GroupSid(NullDefault.getString(paramMdl.getNtp220GroupSid(), dfGpSidStr));

        if (paramMdl.getNtp220mode() == 1) {
            if (paramMdl.getNtp220GroupSid().equals(String.valueOf(-1))) {
                paramMdl.setNtp220GroupSid(dfGpSidStr);
            }
        }

        return admFlg;
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
     * <br>[機  能] 「/」で区切られた日付文字列からUDateを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列
     * @return date 日付
     */
    public UDate getDateFromString(String dateStr) {
        UDate date = new UDate();
        if (!StringUtil.isNullZeroStringSpace(dateStr)) {
            date.setDate(
                    Integer.parseInt(dateStr.substring(0, 4)),
                    Integer.parseInt(dateStr.substring(5, 7)),
                    Integer.parseInt(dateStr.substring(8, 10)));
        }
        return date;
    }


    /**
     * <br>[機  能] メニューの表示項目を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param contentName 項目名
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param offset オフセット
     * @param alreadySelStr 選択済み
     * @param gyoushuSid 業種SID
     * @param shohinCategory カテゴリ
     * @throws Exception 実行例外
     * @return json データ
     */
    public JSONArray getMenuContent(
            String selGrpSid,
            String selUsrSid,
            String contentName,
            UDate frdate,
            UDate todate,
            int offset,
            String alreadySelStr,
            int gyoushuSid,
            int shohinCategory)
                    throws Exception {
        JSONArray jsonArray = null;
        List<Ntp220MenuParam> paramList = null;
        ArrayList<String> alreadySelList = null;
        alreadySelList = __createAlreadySelList(alreadySelStr);

        if (contentName.equals(GSConstNippou.STR_KIGYOU)) {
            //企業
            paramList = __getMenuKigyou(
                    selGrpSid,
                    selUsrSid,
                    MENU_CONTENT_COUNT,
                    offset,
                    frdate,
                    todate,
                    "",
                    alreadySelList,
                    shohinCategory);
        } else if (contentName.equals(GSConstNippou.STR_PROCESS)) {
            //プロセス
            paramList = __getMenuProcess(
                    selGrpSid,
                    selUsrSid,
                    MENU_CONTENT_COUNT,
                    offset,
                    frdate,
                    todate,
                    alreadySelList,
                    gyoushuSid,
                    shohinCategory);
        } else if (contentName.equals(GSConstNippou.STR_MIKOMIDO)) {
            //見込み度
            paramList = __getMenuMikomido(
                    selGrpSid,
                    selUsrSid,
                    MENU_CONTENT_COUNT,
                    offset,
                    frdate,
                    todate,
                    alreadySelList,
                    shohinCategory);
        } else if (contentName.equals(GSConstNippou.STR_KOKYAKUGENSEN)) {
            //顧客源泉
            paramList = __getMenuKokyakugensen(
                    selGrpSid,
                    selUsrSid,
                    MENU_CONTENT_COUNT,
                    offset,
                    frdate,
                    todate,
                    "",
                    alreadySelList,
                    shohinCategory);
        } else if (contentName.equals(GSConstNippou.STR_KADOU)) {
            //稼働時間
            paramList = __getMenuKadou();
        }

        if (paramList != null && !paramList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(paramList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] メニューに表示する企業を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択
     * @param selUsrSid 選択ユーザSID
     * @param limit リミット
     * @param offset オフセット
     * @param searchWord 検索キーワード
     * @param alreadySelList 選択済み
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private List<Ntp220MenuParam> __getMenuKigyou(
            String selGrpSid,
            String selUsrSid,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            String searchWord,
            ArrayList<String> alreadySelList,
            int shohinCategory)
                    throws SQLException {
        List<Ntp220MenuParam> paramList = null;
        Ntp220Dao dao = new Ntp220Dao(con__);
        paramList = dao.getAnkenKigyouList(
                __getGrpUsrAnken(selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                limit,
                offset,
                frdate,
                todate,
                searchWord,
                alreadySelList);
        return paramList;
    }

    /**
     * <br>[機  能] メニューに表示する顧客源泉を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択
     * @param selUsrSid 選択ユーザSID
     * @param limit リミット
     * @param offset オフセット
     * @param searchWord 検索キーワード
     * @param alreadySelList 選択済み
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private List<Ntp220MenuParam> __getMenuKokyakugensen(
            String selGrpSid,
            String selUsrSid,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            String searchWord,
            ArrayList<String> alreadySelList,
            int shohinCategory)
                    throws SQLException {
        List<Ntp220MenuParam> paramList = null;
        Ntp220Dao dao = new Ntp220Dao(con__);
        paramList = dao.getAnkenKokyakugensenList(
                __getGrpUsrAnken(selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                limit,
                offset,
                frdate,
                todate,
                searchWord,
                alreadySelList);
        return paramList;
    }

    /**
     * <br>[機  能] メニューに表示する稼働時間の分析項目を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private List<Ntp220MenuParam> __getMenuKadou()
            throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        List<Ntp220MenuParam> paramList = null;
        paramList = new ArrayList<Ntp220MenuParam>();
        Ntp220MenuParam ntp220menu = new Ntp220MenuParam();
        ntp220menu.setContentSid1(0);
        ntp220menu.setContentName1(gsMsg.getMessage("ntp.11"));
        paramList.add(ntp220menu);
        ntp220menu = new Ntp220MenuParam();
        ntp220menu.setContentSid1(1);
        ntp220menu.setContentName1(
                gsMsg.getMessage("ntp.15") + "・" + gsMsg.getMessage("ntp.16"));
        paramList.add(ntp220menu);
        ntp220menu = new Ntp220MenuParam();
        ntp220menu.setContentSid1(2);
        ntp220menu.setContentName1(gsMsg.getMessage("ntp.3"));
        paramList.add(ntp220menu);
        ntp220menu = new Ntp220MenuParam();
        ntp220menu.setContentSid1(3);
        ntp220menu.setContentName1(gsMsg.getMessage("ntp.121"));
        paramList.add(ntp220menu);
        return paramList;
    }

    /**
     * <br>[機  能] メニューに表示する見込み度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択
     * @param selUsrSid 選択ユーザSID
     * @param limit リミット
     * @param offset オフセット
     * @param alreadySelList 選択済み
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private List<Ntp220MenuParam> __getMenuMikomido(String selGrpSid,
            String selUsrSid,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            ArrayList<String> alreadySelList,
            int shohinCategory)
                    throws SQLException {
        List<Ntp220MenuParam> paramList = null;
        Ntp220Dao dao = new Ntp220Dao(con__);
        paramList = dao.getAnkenMikomidoList(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                limit,
                offset,
                frdate,
                todate,
                alreadySelList);
        return paramList;
    }

    /**
     * <br>[機  能] メニューに表示する業種を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択
     * @param selUsrSid 選択ユーザSID
     * @param limit リミット
     * @param offset オフセット
     * @param alreadySelList 選択済み
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param gyoushuSid 業種SID
     * @param shohinCategory 商品カテゴリ
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private List<Ntp220MenuParam> __getMenuProcess(
            String selGrpSid,
            String selUsrSid,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            ArrayList<String> alreadySelList,
            int gyoushuSid,
            int shohinCategory)
                    throws SQLException {
        List<Ntp220MenuParam> paramList = null;
        Ntp220Dao dao = new Ntp220Dao(con__);
        paramList = dao.getAnkenProcessList(
                __getGrpUsrAnken(selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                limit,
                offset,
                frdate,
                todate,
                alreadySelList,
                gyoushuSid);
        return paramList;
    }

    /**
     * <br>[機  能] 案件に登録されている企業数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param searchWord 検索キーワード
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private int __getMenuKigyouCnt(String selGrpSid,
            String selUsrSid,
            String searchWord,
            UDate frdate,
            UDate todate,
            int shohinCategory) throws SQLException {
        int count = 0;
        Ntp220Dao dao = new Ntp220Dao(con__);
        count = dao.getAnkenKigyouListCount(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                searchWord, frdate, todate);
        return count;
    }

    /**
     * <br>[機  能] 案件に登録されている顧客源泉数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param searchWord 検索キーワード
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return paramList メニュー項目リスト
     * @throws SQLException sql実行時例外
     */
    private int __getMenuKokyakugensenCnt(String selGrpSid,
            String selUsrSid,
            String searchWord,
            UDate frdate,
            UDate todate,
            int shohinCategory) throws SQLException {
        int count = 0;
        Ntp220Dao dao = new Ntp220Dao(con__);
        count = dao.getAnkenKokyakugensenListCount(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                searchWord, frdate, todate);
        return count;
    }

    /**
     * <br>[機  能] メニュー項目の検索結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param contentSid 項目SID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum ページ番号
     * @param shohinCategory 商品カテゴリ
     * @param searchWord 検索キーワード
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONObject getJsonMenuContentList(
            String selGrpSid,
            String selUsrSid,
            String contentSid,
            UDate frdate,
            UDate todate,
            int pageNum,
            String searchWord,
            int shohinCategory) throws Exception {

        JSONObject jsonData = new JSONObject();
        List<Ntp220MenuParam> paramList = null;
        int dataCnt = 0;
        int maxPageSize = 0;

        if (pageNum == 0) {
            pageNum = 1;
        }

        if (contentSid.equals(GSConstNippou.STR_KIGYOU)) {
            //企業
            dataCnt = __getMenuKigyouCnt(
                    selGrpSid, selUsrSid, searchWord, frdate, todate, shohinCategory);
            maxPageSize = PageUtil.getPageCount(dataCnt, SEARCH_CONTENT_COUNT);
            pageNum = __getPagenum(maxPageSize, pageNum);
            paramList = __getMenuKigyou(selGrpSid,
                    selUsrSid,
                    SEARCH_CONTENT_COUNT,
                    pageNum,
                    frdate,
                    todate,
                    searchWord,
                    new ArrayList<String>(),
                    shohinCategory);
        } else if (contentSid.equals(GSConstNippou.STR_GYOUSHU)) {
            //業種
        } else if (contentSid.equals(GSConstNippou.STR_PROCESS)) {
            //プロセス
        } else if (contentSid.equals(GSConstNippou.STR_MIKOMIDO)) {
            //見込み度
        } else if (contentSid.equals(GSConstNippou.STR_KOKYAKUGENSEN)) {
            //顧客源泉
            dataCnt = __getMenuKokyakugensenCnt(
                    selGrpSid, selUsrSid, searchWord, frdate, todate, shohinCategory);
            maxPageSize = PageUtil.getPageCount(dataCnt, SEARCH_CONTENT_COUNT);
            pageNum = __getPagenum(maxPageSize, pageNum);
            paramList = __getMenuKokyakugensen(selGrpSid,
                    selUsrSid,
                    SEARCH_CONTENT_COUNT,
                    pageNum,
                    frdate,
                    todate,
                    searchWord,
                    new ArrayList<String>(),
                    shohinCategory);
        }

        if (paramList != null && !paramList.isEmpty()) {

            //Jsonデータ成形
            if (pageNum > maxPageSize) {
                pageNum = maxPageSize;
            }

            Ntp220SearchMenuModel ntp220SearchMenuMdl = new Ntp220SearchMenuModel();
            ntp220SearchMenuMdl.setMaxPageSize(maxPageSize);
            ntp220SearchMenuMdl.setPageNum(pageNum);
            ntp220SearchMenuMdl.setMenuParamList(paramList);

            jsonData = JSONObject.fromObject(ntp220SearchMenuMdl);
        }
        return jsonData;
    }

    /**
     * <br>[機  能] ページ数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param maxPageSize 最大ページ
     * @param pageNum ページ番号
     * @return pageNum ページ番号
     * @throws SQLException sql実行時例外
     */
    private int __getPagenum(int maxPageSize, int pageNum) throws SQLException {
        if (maxPageSize < pageNum) {
            pageNum = maxPageSize;
        }
        return pageNum;
    }

    /**
     * <br>[機  能] 選択済みのコンテンツをリストに格納
     * <br>[解  説]
     * <br>[備  考]
     * @param alreadySelStr 選択済み
     * @return alreadySelList 選択済みリスト
     */
    private ArrayList<String> __createAlreadySelList(String alreadySelStr) {
        ArrayList<String> alreadySelList = null;

        if (!StringUtil.isNullZeroStringSpace(alreadySelStr)) {
            alreadySelList = new ArrayList<String>();
            alreadySelList = StringUtil.split(",", alreadySelStr);
        }

        return alreadySelList;
    }


    /**
     * <br>[機  能] 項目のデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param contentSid 項目SID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param gyoushuSid 業種SID
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONObject getContentData(
            String selGrpSid,
            String selUsrSid,
            String contentSid,
            int state,
            int ankenState,
            int pageNum,
            UDate frdate,
            UDate todate,
            int gyoushuSid,
            int shohinCategory) throws Exception {

        JSONObject jsonData = new JSONObject();
        ArrayList<Object> contDataList = null;
        Ntp220ContentDataModel dataMdl = new Ntp220ContentDataModel();

        if (contentSid.equals(GSConstNippou.STR_KIGYOU)) {
            //企業
            contDataList = __getAllCompanySales(selGrpSid,
                    selUsrSid,
                    state,
                    ankenState,
                    pageNum,
                    frdate,
                    todate,
                    shohinCategory);
        } else if (contentSid.equals(GSConstNippou.STR_GYOUSHU)) {
            //業種
        } else if (contentSid.equals(GSConstNippou.STR_PROCESS)) {
            //プロセス
            contDataList = __getAllProcessSales(selGrpSid,
                    selUsrSid,
                    state,
                    ankenState,
                    pageNum,
                    frdate,
                    todate,
                    gyoushuSid,
                    shohinCategory);
        } else if (contentSid.equals(GSConstNippou.STR_MIKOMIDO)) {
            //見込み度
            contDataList = __getAllMikomidoSales(selGrpSid,
                    selUsrSid,
                    state,
                    ankenState,
                    pageNum,
                    frdate,
                    todate,
                    shohinCategory);
        } else if (contentSid.equals(GSConstNippou.STR_KOKYAKUGENSEN)) {
            //顧客源泉
            contDataList = __getAllKokyakugensenSales(selGrpSid,
                    selUsrSid,
                    state,
                    ankenState,
                    pageNum,
                    frdate,
                    todate,
                    shohinCategory);
        } else if (contentSid.equals(GSConstNippou.STR_KADOU)) {
            //稼働時間
            contDataList = __getAllKadouAnken(selGrpSid,
                    selUsrSid,
                    state,
                    ankenState,
                    pageNum,
                    frdate,
                    todate,
                    shohinCategory);
        }

        if (contDataList != null && !contDataList.isEmpty()) {
            dataMdl.setContentDataList(contDataList);
            jsonData = JSONObject.fromObject(dataMdl);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 全企業の売上を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    private ArrayList<Object> __getAllCompanySales(
            String selGrpSid,
            String selUsrSid,
            int state,
            int ankenState,
            int pageNum,
            UDate frdate,
            UDate todate,
            int shohinCategory)
                    throws SQLException {

        ArrayList<Object> cmpSalesList = new ArrayList<Object>();
        Ntp220Dao dao = new Ntp220Dao(con__);

        cmpSalesList = dao.getAllCompanySales(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                state,
                ankenState,
                pageNum,
                CONTENT_MONEY_LIST_COUNT,
                frdate,
                todate);

        return cmpSalesList;
    }

    /**
     * <br>[機  能] 全企業の売上を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param gyoushuSid 業種SID
     * @param shohinCategory 商品カテゴリ
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    private ArrayList<Object> __getAllProcessSales(
            String selGrpSid,
            String selUsrSid,
            int state,
            int ankenState,
            int pageNum,
            UDate frdate,
            UDate todate,
            int gyoushuSid,
            int shohinCategory)
                    throws SQLException {

        ArrayList<Object> cmpSalesList = new ArrayList<Object>();
        Ntp220Dao dao = new Ntp220Dao(con__);

        cmpSalesList = dao.getAllProcessSales(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                state,
                ankenState,
                pageNum,
                CONTENT_MONEY_LIST_COUNT,
                frdate,
                todate,
                gyoushuSid);

        return cmpSalesList;
    }

    /**
     * <br>[機  能] 全企業の売上を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    private ArrayList<Object> __getAllKokyakugensenSales(
            String selGrpSid,
            String selUsrSid,
            int state,
            int ankenState,
            int pageNum,
            UDate frdate,
            UDate todate,
            int shohinCategory)
                    throws SQLException {

        ArrayList<Object> cmpSalesList = new ArrayList<Object>();
        Ntp220Dao dao = new Ntp220Dao(con__);

        cmpSalesList = dao.getAllKokyakugensenSales(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                state,
                ankenState,
                pageNum,
                CONTENT_MONEY_LIST_COUNT,
                frdate,
                todate);

        return cmpSalesList;
    }

    /**
     * <br>[機  能] 全稼働時間の案件を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    private ArrayList<Object> __getAllKadouAnken(
            String selGrpSid,
            String selUsrSid,
            int state,
            int ankenState,
            int pageNum,
            UDate frdate,
            UDate todate,
            int shohinCategory)
                    throws SQLException {

        ArrayList<Object> cmpSalesList = new ArrayList<Object>();

        return cmpSalesList;
    }

    /**
     * <br>[機  能] 全見込み度の売上を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    private ArrayList<Object> __getAllMikomidoSales(
            String selGrpSid,
            String selUsrSid,
            int state,
            int ankenState,
            int pageNum,
            UDate frdate,
            UDate todate,
            int shohinCategory)
                    throws SQLException {

        ArrayList<Object> cmpSalesList = new ArrayList<Object>();
        Ntp220Dao dao = new Ntp220Dao(con__);

        cmpSalesList = dao.getAllMikomidoSales(__getGrpUsrAnken(
                selGrpSid, selUsrSid, frdate, todate, shohinCategory),
                state,
                ankenState,
                pageNum,
                CONTENT_MONEY_LIST_COUNT,
                frdate,
                todate);

        return cmpSalesList;
    }


    /**
     * <br>[機  能] 案件の状態の件数データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param childVal 選択条件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONObject getStateData(
            String grpSid,
            String selUsrSid,
            int state,
            String childVal,
            UDate frdate,
            UDate todate,
            int shohinCategory) throws Exception {

        JSONObject jsonData = new JSONObject();
        Ntp220StateModel stateMdl = null;
        Ntp220AnkenSearchModel searchMdl = null;

        searchMdl = __getAnkenSearchModel(
                grpSid, selUsrSid, childVal, frdate, todate, -1, shohinCategory);

        Ntp220Dao dao = new Ntp220Dao(con__);
        stateMdl = dao.getAnkenState(state, searchMdl, frdate, todate);

        if (stateMdl != null) {
            jsonData = JSONObject.fromObject(stateMdl);
        }
        return jsonData;
    }

    /**
     * <br>[機  能] 日報から案件と活動時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouAnkenData(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        //稼働日を取得
        HashMap<String, UDate> kadouDayMap = __getDateList(frdate.cloneUDate(),
                todate.cloneUDate());
        Long totalKadouDays = (long) 0;
        if (kadouDayMap != null && kadouDayMap.size() > 0) {
            totalKadouDays = (long) kadouDayMap.size();
        }

        JSONArray jsonData = new JSONArray();
        List<Ntp220TotalModel> ankenTotalMdlList = null;

        Ntp220Dao dao = new Ntp220Dao(con__);

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        ankenTotalMdlList = dao.getKadouAnkenData(
                usrSids, frdate, todate, pageNum, ANKEN_DATA_COUNT, reqMdl__);

        if (ankenTotalMdlList != null && !ankenTotalMdlList.isEmpty()
                && ankenTotalMdlList.size() > 0) {
            ankenTotalMdlList.get(0).setTotalKadouDays(totalKadouDays);
            jsonData = JSONArray.fromObject(ankenTotalMdlList);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 日報から案件データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouAnkenDetail(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        JSONArray jsonArray = new JSONArray();
        ArrayList<Ntp220AnkenModel> ankenList = null;
        List<Ntp220ShohinModel > shohinList = null;

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        Ntp220Dao dao = new Ntp220Dao(con__);


        ankenList = dao.getKadouAnkenDataDetail(usrSids,
                frdate,
                todate,
                pageNum,
                ANKEN_DATA_COUNT, reqMdl__);

        if (ankenList != null && !ankenList.isEmpty()) {

            for (Ntp220AnkenModel mdl : ankenList) {
                //商品情報取得
                if (mdl.getNahSid() == -1) {
                    shohinList = dao.getAnkenShohin(mdl.getNanSid());
                    mdl.setShohinList(shohinList);
                } else {
                    shohinList = dao.getAnkenShohinHistory(mdl.getNahSid());
                    mdl.setShohinList(shohinList);
                }

                //担当者
                List<CmnUsrmInfModel> usrInfList = null;
                Ntp220Dao memDao = new Ntp220Dao(con__);
                CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con__);

                if (mdl.getNahSid() == -1) {
                    usrInfList = usrInfDao.getUsersDataList(
                            memDao.selectMember(mdl.getNanSid()));
                } else {
                    usrInfList = usrInfDao.getUsersDataList(
                            memDao.selectMemberFromHistory(mdl.getNahSid()));
                }

                if (usrInfList != null && !usrInfList.isEmpty()) {
                    for (CmnUsrmInfModel usrInfMdl : usrInfList) {
                        usrInfMdl.setUsiAdate(null);
                        usrInfMdl.setUsiBdate(null);
                        usrInfMdl.setUsiEdate(null);
                        usrInfMdl.setUsiLtlgin(null);
                        usrInfMdl.setUsiEntranceDate(null);
                    }
                    mdl.setTantoUsrInfList(usrInfList);
                }
            }

            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(ankenList);
        }

        return jsonArray;
    }




    /**
     * <br>[機  能] 日報から企業・顧客データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouKigyouDetail(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        JSONArray jsonArray = new JSONArray();
        List<Ntp220TotalModel> totalList = null;

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        Ntp220Dao dao = new Ntp220Dao(con__);


        totalList = dao.getKadouKigyouData(usrSids,
                frdate,
                todate,
                pageNum,
                ANKEN_DATA_COUNT,
                1);

        if (totalList != null && !totalList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(totalList);
        }

        return jsonArray;
    }


    /**
     * <br>[機  能] 日報から企業と活動時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouKigyouData(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        //稼働日を取得
        HashMap<String, UDate> kadouDayMap = __getDateList(frdate.cloneUDate(),
                todate.cloneUDate());
        Long totalKadouDays = (long) 0;
        if (kadouDayMap != null && kadouDayMap.size() > 0) {
            totalKadouDays = (long) kadouDayMap.size();
        }

        JSONArray jsonData = new JSONArray();
        List<Ntp220TotalModel> ankenTotalMdlList = null;

        Ntp220Dao dao = new Ntp220Dao(con__);

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        ankenTotalMdlList = dao.getKadouKigyouData(
                usrSids, frdate, todate, pageNum, ANKEN_DATA_COUNT, 0);

        if (ankenTotalMdlList != null && !ankenTotalMdlList.isEmpty()
                && ankenTotalMdlList.size() > 0) {
            ankenTotalMdlList.get(0).setTotalKadouDays(totalKadouDays);
            jsonData = JSONArray.fromObject(ankenTotalMdlList);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 日報から活動分類と活動時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouKbunruiData(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        //稼働日を取得
        HashMap<String, UDate> kadouDayMap = __getDateList(frdate.cloneUDate(),
                todate.cloneUDate());
        Long totalKadouDays = (long) 0;
        if (kadouDayMap != null && kadouDayMap.size() > 0) {
            totalKadouDays = (long) kadouDayMap.size();
        }

        JSONArray jsonData = new JSONArray();
        List<Ntp220TotalModel> ankenTotalMdlList = null;

        Ntp220Dao dao = new Ntp220Dao(con__);

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        ankenTotalMdlList = dao.getKadouKbunruiData(
                usrSids, frdate, todate, pageNum, ANKEN_DATA_COUNT, 0);

        if (ankenTotalMdlList != null && !ankenTotalMdlList.isEmpty()
                && ankenTotalMdlList.size() > 0) {
            ankenTotalMdlList.get(0).setTotalKadouDays(totalKadouDays);
            jsonData = JSONArray.fromObject(ankenTotalMdlList);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 日報から活動分類データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouKbunruiDetail(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        JSONArray jsonArray = new JSONArray();
        List<Ntp220TotalModel> totalList = null;

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        Ntp220Dao dao = new Ntp220Dao(con__);


        totalList = dao.getKadouKbunruiData(usrSids,
                frdate,
                todate,
                pageNum,
                ANKEN_DATA_COUNT,
                1);

        if (totalList != null && !totalList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(totalList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] 日報から活動方法と活動時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouKhouhouData(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        //稼働日を取得
        HashMap<String, UDate> kadouDayMap = __getDateList(frdate.cloneUDate(),
                todate.cloneUDate());
        Long totalKadouDays = (long) 0;
        if (kadouDayMap != null && kadouDayMap.size() > 0) {
            totalKadouDays = (long) kadouDayMap.size();
        }

        JSONArray jsonData = new JSONArray();
        List<Ntp220TotalModel> ankenTotalMdlList = null;

        Ntp220Dao dao = new Ntp220Dao(con__);

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        ankenTotalMdlList = dao.getKadouKhouhouData(
                usrSids, frdate, todate, pageNum, ANKEN_DATA_COUNT, 0);

        if (ankenTotalMdlList != null && !ankenTotalMdlList.isEmpty()
                && ankenTotalMdlList.size() > 0) {
            ankenTotalMdlList.get(0).setTotalKadouDays(totalKadouDays);
            jsonData = JSONArray.fromObject(ankenTotalMdlList);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 日報から活動分類データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouKhouhouDetail(
            String grpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        JSONArray jsonArray = new JSONArray();
        List<Ntp220TotalModel> totalList = null;

        List<Integer> usrSids = __getGrpUsrSid(grpSid, selUsrSid, frdate, todate);

        Ntp220Dao dao = new Ntp220Dao(con__);


        totalList = dao.getKadouKhouhouData(usrSids,
                frdate,
                todate,
                pageNum,
                ANKEN_DATA_COUNT,
                1);

        if (totalList != null && !totalList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(totalList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] 日報から指定した項目の活動時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param childVal 選択条件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouContentData(
            String grpSid,
            String selUsrSid,
            String childVal,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        JSONArray jsonData = new JSONArray();
        String contentKbn = "0";
        ArrayList<String> spStr = null;

        spStr = StringUtil.split(GSConstNippou.STR_KADOU + "_", childVal);

        if (spStr != null && !spStr.isEmpty() && spStr.size() >= 1) {
            contentKbn = spStr.get(1);
        }

        if (contentKbn.equals("0")) {
            jsonData = getKadouAnkenData(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        } else if (contentKbn.equals("1")) {
            jsonData = getKadouKigyouData(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);

        } else if (contentKbn.equals("2")) {
            jsonData = getKadouKbunruiData(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        } else if (contentKbn.equals("3")) {
            jsonData = getKadouKhouhouData(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        } else {
            jsonData = getKadouAnkenData(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 日報から指定した項目の詳細を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param childVal 選択条件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getKadouDetailData(
            String grpSid,
            String selUsrSid,
            String childVal,
            UDate frdate,
            UDate todate,
            int pageNum) throws Exception {

        JSONArray jsonData = new JSONArray();
        String contentKbn = "0";
        ArrayList<String> spStr = null;

        spStr = StringUtil.split(GSConstNippou.STR_KADOU + "_", childVal);

        if (spStr != null && !spStr.isEmpty() && spStr.size() >= 1) {
            contentKbn = spStr.get(1);
        }

        if (contentKbn.equals("0")) {
            jsonData = getKadouAnkenDetail(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        } else if (contentKbn.equals("1")) {
            jsonData = getKadouKigyouDetail(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);

        } else if (contentKbn.equals("2")) {
            jsonData = getKadouKbunruiDetail(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        } else if (contentKbn.equals("3")) {
            jsonData = getKadouKhouhouDetail(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        } else {
            jsonData = getKadouAnkenDetail(
                    grpSid,
                    selUsrSid,
                    frdate,
                    todate,
                    pageNum);
        }

        return jsonData;
    }

    /**
     * <br>[機  能] 案件の状態の件数データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param contentSid 項目SID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param childVal 選択条件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param gyoushuSid 業種SID
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONArray getContentStateData(
            String grpSid,
            String selUsrSid,
            String contentSid,
            int state,
            int ankenState,
            String childVal,
            UDate frdate,
            UDate todate,
            int gyoushuSid,
            int shohinCategory) throws Exception {

        JSONArray jsonData = new JSONArray();
        List<Ntp220StateModel> stateMdlList = null;
        Ntp220AnkenSearchModel searchMdl = null;

        searchMdl = __getAnkenSearchModel(
                grpSid, selUsrSid, childVal, frdate, todate, -1, shohinCategory);

        Ntp220Dao dao = new Ntp220Dao(con__);



        if (contentSid.equals(GSConstNippou.STR_PROCESS)) {
            //プロセス
            stateMdlList = dao.getProcessAnkenState(state,
                    ankenState,
                    searchMdl,
                    frdate,
                    todate,
                    gyoushuSid);
        } else if (contentSid.equals(GSConstNippou.STR_MIKOMIDO)) {
            //見込み度
            stateMdlList = dao.getMikomidoAnkenState(state,
                    ankenState,
                    searchMdl,
                    frdate,
                    todate);
        } else if (contentSid.equals(GSConstNippou.STR_KOKYAKUGENSEN)) {
            //顧客源泉
            stateMdlList = dao.getKokyakuAnkenState(state,
                    ankenState,
                    searchMdl,
                    frdate,
                    todate);
        }

        if (stateMdlList != null && !stateMdlList.isEmpty()) {
            jsonData = JSONArray.fromObject(stateMdlList);
        }
        return jsonData;
    }

    /**
     * <br>[機  能] 案件データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param pageNum 表示ページ
     * @param childVal 選択条件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param gyoushuSid 業種SID
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData json案件リスト
     */
    public JSONArray getAnkenData(
            String selGrpSid,
            String selUsrSid,
            int ankenState,
            int state,
            int pageNum,
            String childVal,
            UDate frdate,
            UDate todate,
            int gyoushuSid,
            int shohinCategory) throws Exception {

        JSONArray jsonArray = new JSONArray();
        ArrayList<Ntp220AnkenModel> ankenList = null;
        List<Ntp220ShohinModel > shohinList = null;
        Ntp220AnkenSearchModel searchMdl = null;

        searchMdl = __getAnkenSearchModel(
                selGrpSid, selUsrSid, childVal, frdate, todate, gyoushuSid, shohinCategory);

        Ntp220Dao dao = new Ntp220Dao(con__);
        ankenList = dao.getAnkenData(ANKEN_DATA_COUNT,
                pageNum,
                state,
                ankenState,
                pageNum,
                searchMdl,
                frdate,
                todate);

        if (ankenList != null && !ankenList.isEmpty()) {


            for (Ntp220AnkenModel mdl : ankenList) {
                //商品情報取得
                if (mdl.getNahSid() == -1) {
                    shohinList = dao.getAnkenShohin(mdl.getNanSid());
                    mdl.setShohinList(shohinList);
                } else {
                    shohinList = dao.getAnkenShohinHistory(mdl.getNahSid());
                    mdl.setShohinList(shohinList);
                }

                //担当者
                List<CmnUsrmInfModel> usrInfList = null;
                Ntp220Dao memDao = new Ntp220Dao(con__);
                CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con__);

                if (mdl.getNahSid() == -1) {
                    usrInfList = usrInfDao.getUsersDataList(
                            memDao.selectMember(mdl.getNanSid()));
                } else {
                    usrInfList = usrInfDao.getUsersDataList(
                            memDao.selectMemberFromHistory(mdl.getNahSid()));
                }

                if (usrInfList != null && !usrInfList.isEmpty()) {
                    for (CmnUsrmInfModel usrInfMdl : usrInfList) {
                        usrInfMdl.setUsiAdate(null);
                        usrInfMdl.setUsiBdate(null);
                        usrInfMdl.setUsiEdate(null);
                        usrInfMdl.setUsiLtlgin(null);
                        usrInfMdl.setUsiEntranceDate(null);
                    }
                    mdl.setTantoUsrInfList(usrInfList);
                }
            }

            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(ankenList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] 商品データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param childVal 選択条件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData json案件リスト
     */
    public JSONArray getShohinData(
            String grpSid,
            String selUsrSid,
            int ankenState,
            int state,
            String childVal,
            UDate frdate,
            UDate todate,
            int shohinCategory) throws Exception {

        JSONArray jsonArray = new JSONArray();
        List<Ntp220ShohinModel > shohinList = null;
        Ntp220AnkenSearchModel searchMdl = null;

        searchMdl = __getAnkenSearchModel(
                grpSid, selUsrSid, childVal, frdate, todate, -1, shohinCategory);

        Ntp220Dao dao = new Ntp220Dao(con__);
        shohinList = dao.getAnkenShohinData(
                state,
                ankenState,
                searchMdl,
                frdate,
                todate);

        if (shohinList != null && !shohinList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(shohinList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] 期間案件データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param defGraphMode 0:金額グラフ 1:商談結果グラフ
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData json案件リスト
     */
    public JSONArray getPeriodAnkenData(
            String selGrpSid,
            String selUsrSid,
            int ankenState,
            int state,
            UDate frdate,
            UDate todate,
            String defGraphMode,
            int shohinCategory) throws Exception {

        JSONArray jsonArray = new JSONArray();
        ArrayList<Ntp220TotalModel> totalMdlList = new ArrayList<Ntp220TotalModel>();
        Ntp220TotalModel totalMdl = null;
        Ntp220AnkenSearchModel searchMdl = null;
        Ntp220StateModel stateMdl = null;
        Ntp220Dao dao = new Ntp220Dao(con__);

        frdate.setZeroHhMmSs();
        todate.setMaxHhMmSs();

        //指定期間の月数を取得
        UDate newFrDate = frdate.cloneUDate();
        newFrDate.setDay(1);
        newFrDate.setZeroHhMmSs();
        UDate newToDate = todate.cloneUDate();
        newToDate.setDay(todate.getMaxDayOfMonth());
        newToDate.setMaxHhMmSs();
        UDate searchFrDate = new UDate();
        UDate searchPeriodFrDate = new UDate();
        UDate searchToDate = new UDate();

        UDate periodDate = newFrDate.cloneUDate();

        int monthCnt = UDateUtil.diffMonth(newFrDate, newToDate);

        //月毎にデータを集計
        for (int i = 0; i <= monthCnt; i++) {

            totalMdl = new Ntp220TotalModel();

            //検索開始日付
            searchFrDate = frdate.cloneUDate();

            if (i == 0) {
                searchPeriodFrDate = frdate.cloneUDate();
            } else {
                searchPeriodFrDate = periodDate.cloneUDate();
                searchPeriodFrDate.setDay(1);
            }

            //検索終了日付
            if (i == monthCnt) {
                searchToDate = todate.cloneUDate();
            } else {
                searchToDate = periodDate.cloneUDate();
                searchToDate.setDay(periodDate.getMaxDayOfMonth());
            }

            searchMdl = __getAnkenSearchModel(
                    selGrpSid, selUsrSid, "",
                    searchFrDate, searchToDate, -1, shohinCategory);


            if (defGraphMode.equals("0")) {
                totalMdl = dao.getPeriodAnkenData(state,
                        ankenState,
                        searchMdl,
                        searchPeriodFrDate,
                        searchToDate);
            } else {
                stateMdl = dao.getAnkenState(state,
                        searchMdl,
                        searchFrDate,
                        searchToDate);
            }


            if (stateMdl != null) {
                totalMdl.setSumJutyuAnken(stateMdl.getJutyuCnt());
                totalMdl.setSumSityuAnken(stateMdl.getSityuCnt());
                totalMdl.setSumShodanAnken(stateMdl.getSyodanCnt());
            }

            totalMdl.setDateStr(periodDate.getYear() + "/" + periodDate.getMonth());

            periodDate.addMonth(1);
            totalMdlList.add(totalMdl);
        }

        if (totalMdlList != null && !totalMdlList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(totalMdlList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] 期間稼働時間データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @throws Exception SQL実行時例外
     * @return jsonData json案件リスト
     */
    public JSONArray getPeriodKadouData(
            String selGrpSid,
            String selUsrSid,
            UDate frdate,
            UDate todate)
                    throws Exception {

        JSONArray jsonArray = new JSONArray();
        ArrayList<Ntp220TotalModel> totalMdlList = new ArrayList<Ntp220TotalModel>();
        Ntp220TotalModel totalMdl = null;
        Ntp220Dao dao = new Ntp220Dao(con__);

        frdate.setZeroHhMmSs();
        todate.setMaxHhMmSs();

        //稼働日を取得
        HashMap<String, UDate> kadouDayMap = __getDateList(frdate.cloneUDate(),
                todate.cloneUDate());
        Long totalKadouDays = (long) 0;
        if (kadouDayMap != null && kadouDayMap.size() > 0) {
            totalKadouDays = (long) kadouDayMap.size();
        }

        //指定期間の月数を取得
        UDate newFrDate = frdate.cloneUDate();
        newFrDate.setDay(1);
        newFrDate.setZeroHhMmSs();
        UDate newToDate = todate.cloneUDate();
        newToDate.setDay(todate.getMaxDayOfMonth());
        newToDate.setMaxHhMmSs();
        UDate searchPeriodFrDate = new UDate();
        UDate searchToDate = new UDate();

        UDate periodDate = newFrDate.cloneUDate();

        int monthCnt = UDateUtil.diffMonth(newFrDate, newToDate);

        List<Integer> usrSids = __getGrpUsrSid(selGrpSid, selUsrSid, frdate, todate);

        long sumKadouTimeMins = 0;

        //月毎にデータを集計
        for (int i = 0; i <= monthCnt; i++) {

            totalMdl = new Ntp220TotalModel();

            if (i == 0) {
                searchPeriodFrDate = frdate.cloneUDate();
            } else {
                searchPeriodFrDate = periodDate.cloneUDate();
                searchPeriodFrDate.setDay(1);
            }

            //検索終了日付
            if (i == monthCnt) {
                searchToDate = todate.cloneUDate();
            } else {
                searchToDate = periodDate.cloneUDate();
                searchToDate.setDay(periodDate.getMaxDayOfMonth());
            }


            totalMdl = dao.getPeriodKadouData(
                    usrSids,
                    searchPeriodFrDate,
                    searchToDate);


            totalMdl.setDateStr(periodDate.getYear() + "/" + periodDate.getMonth());
            totalMdl.setTotalKadouDays(totalKadouDays);

            sumKadouTimeMins += totalMdl.getSumKadouTimeMins();

            periodDate.addMonth(1);
            totalMdlList.add(totalMdl);
        }

        //稼働時間合計、全稼働時間合計を追加
        ArrayList<Ntp220TotalModel> compTotalMdlList = new ArrayList<Ntp220TotalModel>();
        float sumKadouTime = 0f;
        float totalKadouTime = Ntp220Biz.convMinToHour(sumKadouTimeMins);
        for (Ntp220TotalModel mdl : totalMdlList) {
            sumKadouTime = Ntp220Biz.convMinToHour(mdl.getSumKadouTimeMins());
            mdl.setSumKadouTime(sumKadouTime);
            mdl.setTotalKadouTime(totalKadouTime);
            compTotalMdlList.add(mdl);
        }

        if (totalMdlList != null && !totalMdlList.isEmpty()) {
            jsonArray = new JSONArray();
            jsonArray = JSONArray.fromObject(totalMdlList);
        }

        return jsonArray;
    }

    /**
     * <br>[機  能] 案件検索用モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param selGrpSid 選択グループSID
     * @param selUsrSid 選択ユーザSID
     * @param paramStr パラメータ
     * @param frDate 選択開始日付
     * @param toDate 選択終了日付
     * @param gyoushuSid 業種SID
     * @param shohinCategory 商品カテゴリ
     * @throws Exception SQL実行時例外
     * @return jsonData json案件リスト
     */
    private Ntp220AnkenSearchModel __getAnkenSearchModel(String selGrpSid,
            String selUsrSid,
            String paramStr,
            UDate frDate,
            UDate toDate,
            int gyoushuSid,
            int shohinCategory
            )throws Exception {

        Ntp220AnkenSearchModel searchMdl = null;
        if (!StringUtil.isNullZeroStringSpace(paramStr)) {
            ArrayList<String> paramList = StringUtil.split("_", paramStr);
            if (paramList != null && !paramList.isEmpty()) {
                String p1 = "";
                String p2 = "";
                String p3 = "";
                for (int i = 0; i < paramList.size(); i++) {
                    if (i == 0) {
                        p1 = paramList.get(i);
                    } else if (i == 1) {
                        p2 = paramList.get(i);
                    } else if (i == 2) {
                        p3 = paramList.get(i);
                    }
                }

                if (!StringUtil.isNullZeroStringSpace(p1)) {
                    searchMdl = new Ntp220AnkenSearchModel();
                    searchMdl.setSearchContent(p1);

                    if (p1.equals(GSConstNippou.STR_KIGYOU)) {
                        //企業選択状態
                        if (!StringUtil.isNullZeroStringSpace(p2)) {
                            searchMdl.setAcoSid(Integer.valueOf(p2));
                            if (!StringUtil.isNullZeroStringSpace(p3)) {
                                searchMdl.setAbaSid(Integer.valueOf(p3));
                            }
                        }

                    } else if (p1.equals(GSConstNippou.STR_GYOUSHU)) {
                        //業種選択状態

                    } else if (p1.equals(GSConstNippou.STR_PROCESS)) {
                        //プロセス選択状態
                        if (!StringUtil.isNullZeroStringSpace(p2)) {
                            searchMdl.setNgpSid(Integer.valueOf(p2));
                        }
                    } else if (p1.equals(GSConstNippou.STR_MIKOMIDO)) {
                        //見込み度選択状態
                        if (!StringUtil.isNullZeroStringSpace(p2)) {
                            searchMdl.setNanMikomiSearchVal(Integer.valueOf(p2));
                        }
                    } else if (p1.equals(GSConstNippou.STR_KOKYAKUGENSEN)) {
                        //顧客源泉選択状態
                        if (!StringUtil.isNullZeroStringSpace(p2)) {
                            searchMdl.setNcnSid(Integer.valueOf(p2));
                        }
                    }
                }
            }
        }


        //選択グループ所属ユーザ取得
        if (searchMdl == null) {
            searchMdl = new Ntp220AnkenSearchModel();
            searchMdl.setSearchPramFlg(false);
        } else {
            searchMdl.setSearchPramFlg(true);
        }
        List<Integer> ankenSids = null;
        ankenSids = __getGrpUsrAnken(selGrpSid, selUsrSid, frDate, toDate, shohinCategory);
        if (ankenSids != null && !ankenSids.isEmpty()) {
            searchMdl.setAnkenSidList(ankenSids);
        }

        //業種SID
        searchMdl.setGyoushuSid(gyoushuSid);

        //商品カテゴリ
        searchMdl.setShohinCatSid(shohinCategory);

        return searchMdl;
    }

    /**
     * <br>[機  能] 選択グループ・ユーザの案件が存在するか
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param selUsrSid ユーザSID
     * @param frDate 選択開始日付
     * @param toDate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @throws SQLException SQL実行時例外
     * @return usrSids usrSids
     */
    public boolean existGrpUsrAnken(String grpSid,
            String selUsrSid,
            UDate frDate,
            UDate toDate,
            int shohinCategory)
                    throws SQLException {

        List<Integer> ankenSids = __getGrpUsrAnken(grpSid,
                selUsrSid,
                frDate,
                toDate,
                shohinCategory);
        boolean ankenFlg = false;

        if (grpSid.equals("-1") || (ankenSids != null && !ankenSids.isEmpty())) {
            ankenFlg = true;
        }

        return ankenFlg;
    }

    /**
     * <br>[機  能] 選択グループ・ユーザの案件を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param usrSid グループSID
     * @param frDate 選択開始日付
     * @param toDate 選択終了日付
     * @param shohinCategory 商品カテゴリ
     * @throws SQLException SQL実行時例外
     * @return ankenSids ankenSids
     */
    private List<Integer> __getGrpUsrAnken(
            String grpSid,
            String usrSid,
            UDate frDate,
            UDate toDate,
            int shohinCategory)
                    throws SQLException {

        Ntp220Dao dao = new Ntp220Dao(con__);
        List<Integer> ankenSids = new ArrayList<Integer>();
        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con__,
                        reqMdl__.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        if (StringUtil.isNullZeroString(usrSid)) {
            //選択グループ所属ユーザ取得
            if (!StringUtil.isNullZeroStringSpace(grpSid)
                    && ValidateUtil.isNumber(grpSid)) {
                List<Integer> usrSids = null;

                usrSids = dao.getUsrSidBelongGrpHistory(
                        Integer.valueOf(grpSid), frDate, toDate);

                if (usrSids != null && !usrSids.isEmpty()) {
                    ankenSids = dao.getUsersAnkenSids(usrSids,
                            shohinCategory,
                            sessionUsrSid,
                            isAdmin);
                }
            }
        } else {
            if (ValidateUtil.isNumber(usrSid)) {
                List<Integer> usrSids = new ArrayList<Integer>();
                usrSids.add(Integer.valueOf(usrSid));
                ankenSids = dao.getUsersAnkenSids(usrSids,
                        shohinCategory,
                        sessionUsrSid,
                        isAdmin);

                if (ankenSids == null || ankenSids.isEmpty()) {
                    ankenSids = new ArrayList<Integer>();
                    ankenSids.add(-1);
                }
            }
        }

        return ankenSids;
    }

    /**
     * <br>[機  能] ユーザのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param usrSid グループSID
     * @param frDate 選択開始日付
     * @param toDate 選択終了日付
     * @throws SQLException SQL実行時例外
     * @return ankenSids ankenSids
     */
    private List<Integer> __getGrpUsrSid(String grpSid,
            String usrSid,
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        Ntp220Dao dao = new Ntp220Dao(con__);
        List<Integer> usrSids = new ArrayList<Integer>();

        if (StringUtil.isNullZeroString(usrSid)) {
            //選択グループ所属ユーザ取得
            if (!StringUtil.isNullZeroStringSpace(grpSid)
                    && ValidateUtil.isNumber(grpSid)) {
                usrSids = dao.getUsrSidBelongGrpHistory(
                        Integer.valueOf(grpSid), frDate, toDate);
            }
        } else {
            if (ValidateUtil.isNumber(usrSid)) {
                usrSids.add(Integer.valueOf(usrSid));
            }
        }

        return usrSids;
    }

    /**
     * <br>[機  能] 日付指定条件に該当する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param frDate 選択開始日付
     * @param toDate 選択終了日付
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    private HashMap<String, UDate> __getDateList(UDate frDate,
            UDate toDate)
                    throws SQLException {


        //営業日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con__);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(frDate, toDate);
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel acMdl = tcBiz.getTcdAdmConfModel(reqMdl__.getSmodel().getUsrsid(), con__);

        HashMap<String, UDate> dateMap = new HashMap<String, UDate>();
        UDate setDate = null;
        log__.debug("frDate.compareDateYMD(toDate)=>" + frDate.compareDateYMD(toDate));
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {

            setDate = __getConvertDate(frDate, holMap, acMdl);
            if (setDate != null) {
                dateMap.put(setDate.getDateString(), setDate);
                log__.debug("登録日付==>" + setDate.getDateString());
            }

            frDate.addDay(1);
        }

        return dateMap;
    }

    /**
     * <br>[機  能] 営業日判定を行い非営業日の場合、振替設定によって日付をコンバートします。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @return UDate コンバート結果
     */
    private UDate __getConvertDate(
            UDate date,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl) {

        UDate ret = date.cloneUDate();

        //休日として扱う曜日か判定
        boolean fin = true;
        while (fin) {
            if (__isMatchWeek(ret.getWeek(), acMdl)
                    || holMap.containsKey(ret.getDateString())) {
                log__.debug("休日として認識==>" + ret.getDateString());
                return null;
            } else {
                fin = false;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週
     * @param acMdl 管理設定
     * @return true:休日曜日　false:休日曜日以外
     */
    private boolean __isMatchWeek(int week, TcdAdmConfModel acMdl) {
        switch (week) {
        case UDate.SUNDAY:
            if (acMdl.getTacHolSun() == 0) {
                return false;
            }
            break;
        case UDate.MONDAY:
            if (acMdl.getTacHolMon() == 0) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            if (acMdl.getTacHolTue() == 0) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            if (acMdl.getTacHolWed() == 0) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            if (acMdl.getTacHolThu() == 0) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            if (acMdl.getTacHolFri() == 0) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            if (acMdl.getTacHolSat() == 0) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 分単位を小数点以下第1位までの時間単位に変換します。
     * <br>[解  説]
     * <br>[備  考]
     *@param mins 分
     *@return 小数点以下第1位までの時間
     */
    public static float convMinToHour(long mins) {
        BigDecimal bdMins = new BigDecimal(mins);
        BigDecimal hours = bdMins.divide(
                new BigDecimal(60), 1, BigDecimal.ROUND_HALF_UP);

        return hours.floatValue();
    }
}