package jp.groupsession.v2.ntp.ntp061;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr240.Adr240Const;
import jp.groupsession.v2.adr.adr240.dao.Adr240Dao;
import jp.groupsession.v2.adr.adr240.model.Adr240Model;
import jp.groupsession.v2.adr.adr240.model.Adr240SearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAnMemberDao;
import jp.groupsession.v2.ntp.dao.NtpAnMemberHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenPermitDao;
import jp.groupsession.v2.ntp.dao.NtpMikomidoMsgDao;
import jp.groupsession.v2.ntp.dao.NtpShohinCategoryDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpAnMemberHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnMemberModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinModel;
import jp.groupsession.v2.ntp.model.NtpAnkenHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpAnkenPermitModel;
import jp.groupsession.v2.ntp.model.NtpMikomidoMsgModel;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenDao;
import jp.groupsession.v2.ntp.ntp130.Ntp130SearchModel;
import jp.groupsession.v2.ntp.ntp130.Ntp130ShohinDao;
import jp.groupsession.v2.ntp.ntp130.Ntp130ShohinModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報 案件登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp061Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp061Biz.class);

    /** コネクション */
    private Connection con__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /** ユーザグループ選択 選択済みリストID 閲覧*/
    public static final String NAP_USRGRPBOXID_VIEW = "view";
    /** ユーザグループ選択 選択済みリストID 編集*/
    public static final String NAP_USRGRPBOXID_EDIT = "edit";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番用コネクション
     * @param reqMdl RequestModel
     */
    public Ntp061Biz(Connection con,
                     MlCountMtController cntCon,
                     RequestModel reqMdl) {
        con__ = con;
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] 編集可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSid 対象案件SID
     * @throws SQLException SQL実行時例外
     * @return 判定結果
     */
    public boolean isEditable(int nanSid) throws SQLException {
        Ntp060AnkenDao dao = new Ntp060AnkenDao(con__);
        List<Integer> nanSids = new ArrayList<Integer>();
        nanSids.add(nanSid);
        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin =
                commonBiz.isPluginAdmin(con__,
                        reqMdl__.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU);

        List<Integer> retSids = dao.getPermissionAnkenSids(nanSids,
                GSConst.SP_AUTH_EDIT,
                reqMdl__.getSmodel().getUsrsid(),
                isAdmin);
        return (retSids.size() > 0);
    }
    /**
    *
    * <br>[機  能] 編集可能か判定
    * <br>[解  説]
    * <br>[備  考]
    * @param nanSid 対象案件SID
    * @throws SQLException SQL実行時例外
    * @return 判定結果
    */
   public boolean isViewable(int nanSid) throws SQLException {
       Ntp060AnkenDao dao = new Ntp060AnkenDao(con__);
       List<Integer> nanSids = new ArrayList<Integer>();
       nanSids.add(nanSid);
       //管理者権限がある場合
       CommonBiz commonBiz = new CommonBiz();
       boolean isAdmin =
               commonBiz.isPluginAdmin(con__,
                       reqMdl__.getSmodel(),
                       GSConstNippou.PLUGIN_ID_NIPPOU);

       List<Integer> retSids = dao.getPermissionAnkenSids(nanSids,
               GSConst.SP_AUTH_VIEWONLY,
               reqMdl__.getSmodel().getUsrsid(),
               isAdmin);
       return (retSids.size() > 0);
   }    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp060ParamModel
     * @param userMdl セッションユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public void setInitData(
            Ntp061ParamModel paramMdl,
            BaseUserModel userMdl,
            Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (paramMdl.getNtp061ChkShohinSidList() != null) {
            log__.debug("*****選択商品数 = " + paramMdl.getNtp061ChkShohinSidList().length);
            for (String sid : paramMdl.getNtp061ChkShohinSidList()) {
                log__.debug("*****選択商品SID = " + sid);
            }
        }

        BaseUserModel usModel =
            (BaseUserModel) reqMdl__.getSession().getAttribute(GSConst.SESSION_KEY);


        if (paramMdl.getNtp061InitFlg() == 0) {
            if (paramMdl.getNtp060ProcMode().equals(GSConstNippou.CMD_EDIT)) {
                loadAnken(paramMdl);
            } else {
                //追加処理
                //登録者名の設定
                UserSearchDao uDao = new UserSearchDao(con);
                CmnUsrmInfModel uMdl
                    = uDao.getUserInfoJtkb(usModel.getUsrsid(), GSConstUser.USER_JTKBN_ACTIVE);
                paramMdl.setNtp061TourokuName(uMdl.getUsiSei() + " " + uMdl.getUsiMei());
                paramMdl.setNtp061TourokuUsrUkoFlg(uMdl.getUsrUkoFlg());
                //登録日付の設定（現在日）
                paramMdl.setNtp061Date(UDateUtil.getYymdJ(new UDate(), reqMdl__));

                //案件コードの初期値(Sidを取得)を取得
                if (paramMdl.getNtp061InitFlg() == 0) {
                    setNanCode(paramMdl, usModel);

                }
            }
        }

        if (paramMdl.getNtp061CopyFlg() != 0) {
            setNanCode(paramMdl, usModel);
            UDate kinDate = new UDate();
            //見積もり 再設定
            paramMdl.setNtp061MitumoriYear(kinDate.getStrYear());
            paramMdl.setNtp061MitumoriMonth(
                    Integer.toString(kinDate.getMonth()));
            paramMdl.setNtp061MitumoriDay(
                    Integer.toString(kinDate.getIntDay()));

            //受注再設定
            paramMdl.setNtp061JutyuYear(kinDate.getStrYear());
            paramMdl.setNtp061JutyuMonth(
                    Integer.toString(kinDate.getMonth()));
            paramMdl.setNtp061JutyuDay(
                    Integer.toString(kinDate.getIntDay()));

            //状態再設定
            paramMdl.setNtp061NanMikomi(0);
            paramMdl.setNtp061NanKinMitumori(String.valueOf(0));
            paramMdl.setNtp061NanKinJutyu(String.valueOf(0));
            paramMdl.setNtp061NanSyodan(0);
        }

        paramMdl.setNtp010SelectUsrSid(String.valueOf(usModel.getUsrsid()));

        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl__);

        //デフォルト表示グループ
        String dfGpSidStr = ntpBiz.getDispDefaultGroupSidStr(con, usModel.getUsrsid());

        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getNtp061GroupSid(), dfGpSidStr);
        paramMdl.setNtp061GroupSid(dspGpSidStr);

        //初期状態で自分を選択ユーザに追加
        if (paramMdl.getNtp061InitFlg() == 0
                && paramMdl.getNtp060ProcMode().equals(GSConstNippou.CMD_ADD)) {
            String[] defUser = {String.valueOf(usModel.getUsrsid())};
            paramMdl.setSv_users(defUser);
        }

        paramMdl.setNtp061InitFlg(1);

        //ポップアップ設定
        //if (paramMdl.getNtp061PopKbn() != 0) {

            /**------------ アドレス帳 ------------*/

            //インデックスを設定する。
            __setIndex(paramMdl);

            //業種を設定する
            __setGyoshuList(paramMdl, userMdl.getUsrsid());

            //都道府県を設定する
            __setTdfList(paramMdl, userMdl.getUsrsid());

            //一覧を設定する。
            if (paramMdl.getNtp061SearchMode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {
                __setDspList(paramMdl, userMdl.getUsrsid());
            } else {
                //詳細検索
                __setDspDetailList(paramMdl, userMdl.getUsrsid());
            }

            //画面モード設定する。
            paramMdl.setNtp061Adrmode(1);


            /**------------ 商品 ------------*/
            __doSearch(paramMdl, userMdl.getUsrsid());

          //カテゴリコンボ作成
            ArrayList<LabelValueBean> ntpShohinCatList = new ArrayList<LabelValueBean>();
            NtpShohinCategoryDao catDao = new NtpShohinCategoryDao(con);
            List<NtpShohinCategoryModel> catMdlList = catDao.select();
            ntpShohinCatList.add(new LabelValueBean("すべて", String.valueOf(-1)));
            for (NtpShohinCategoryModel mdl : catMdlList) {
                String catName = mdl.getNscName();
                String catSid = String.valueOf(mdl.getNscSid());
                ntpShohinCatList.add(new LabelValueBean(catName, catSid));
            }

            paramMdl.setNtp061CategoryList(ntpShohinCatList);
            paramMdl.setNtp061CatSid(paramMdl.getNtp061CatSid());

            //ソートキーコンボ設定
            paramMdl.setNtp061ItmSortList(__getSortList());

            ArrayList<Ntp130ShohinModel> sList = paramMdl.getNtp061ItmShohinList();
            //選択チェックボックスを設定
            String[] chkSid = paramMdl.getNtp061ItmChkShohinSidList();
            ArrayList<String> saveList = null;

            if (chkSid != null && sList != null) {

                saveList = new ArrayList<String>();

                for (int i = 0; i < chkSid.length; i++) {

                    String nhnSid = NullDefault.getString(chkSid[i], "");
                    boolean addFlg = true;
                    for (int j = 0; j < sList.size(); j++) {
                        NtpShohinModel sMdl = sList.get(j);
                        if (nhnSid.equals(String.valueOf(sMdl.getNhnSid()))) {
                            addFlg = false;
                            break;
                        }
                    }
                    if (addFlg) {
                        saveList.add(String.valueOf(chkSid[i]));
                    }
                }
            }
            if (saveList != null) {
                for (String sid : saveList) {
                log__.debug("*****セーブリスト:" + sid);
                }
            }
            //saveリスト(現在ページ以外でチェックされている値)
            paramMdl.setNtp061ItmSelectedSid(saveList);
        //}

        //選択された商品情報の設定
        _setShohinData(con, paramMdl);

        //選択された会社情報の設定
        _setCompanyData(con, paramMdl);

        //見積もり・受注日付設定
        __setDateCombo(paramMdl);

        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getNtp061MitumoriDate() == null) {
            dateBiz.setDateParam(paramMdl, "ntp061MitumoriDate",
                    "ntp061MitumoriYear", "ntp061MitumoriMonth", "ntp061MitumoriDay", null);
        }
        
        if (paramMdl.getNtp061JutyuDate() == null) {
            dateBiz.setDateParam(paramMdl, "ntp061JutyuDate",
                    "ntp061JutyuYear", "ntp061JutyuMonth", "ntp061JutyuDay", null);
        }

        //見込み度基準
        NtpMikomidoMsgDao nmmDao = new NtpMikomidoMsgDao(con);
        ArrayList<NtpMikomidoMsgModel> nmmList = nmmDao.select();

        if (!nmmList.isEmpty()) {
            for (NtpMikomidoMsgModel nmmMdl : nmmList) {
                if (!StringUtil.isNullZeroStringSpace(nmmMdl.getNmmMsg())) {
                    nmmMdl.setNmmMsg(StringUtilHtml.returntoBR(
                            StringUtilHtml.transToHTmlForTextArea(nmmMdl.getNmmMsg())));
                    paramMdl.setNtp061MikomidoFlg(1);
                }
            }
        }

        paramMdl.setNtp061MikomidoMsgList(nmmList);

        //権限設定から担当者を除外する
        String[] tantoList = paramMdl.getSv_users();
        if (tantoList != null && tantoList.length > 0) {
            List<String> permitListView = __createList(paramMdl.getNtp061NanPermitUserView());
            List<String> permitListEdit = __createList(paramMdl.getNtp061NanPermitUserEdit());
            List<String> permitEditList = __createList(paramMdl.getNtp061NanPermitEditUser());
            for (String tantoId : tantoList) {
                permitListView.remove(tantoId);
                permitListEdit.remove(tantoId);
                permitEditList.remove(tantoId);
            }
            paramMdl.setNtp061NanPermitUserView(__createArray(permitListView));
            paramMdl.setNtp061NanPermitUserEdit(__createArray(permitListEdit));
            paramMdl.setNtp061NanPermitEditUser(__createArray(permitEditList));
        }

        setDspData(paramMdl, con);
    }

    private List<String> __createList(String[] value) {
        List<String> valueList = new ArrayList<String>();
        if (value != null) {
            valueList.addAll(Arrays.asList(value));
        }
        return valueList;
    }
    private String[] __createArray(List<String> valueList) {
        return valueList.toArray(new String[valueList.size()]);
    }

    /**
     * <br>[機  能] 画面表示データの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp060ParamModel
     * @param usModel ユーザモデル
     * @throws SQLException SQL実行エラー
     */
    public void setNanCode(Ntp061ParamModel paramMdl, BaseUserModel usModel) throws SQLException {
        String ankenCode = String.valueOf(
                cntCon__.getSaibanNumberNotCommit(con__, GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN, usModel.getUsrsid()));
        paramMdl.setNtp061NanCode(ankenCode);
    }

    /**
     * <br>[機  能] 画面表示データの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp060ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setDspData(Ntp061ParamModel paramMdl, Connection con) throws SQLException {
        NtpCommonBiz cBiz = new NtpCommonBiz(con__, reqMdl__);
        //業務リスト取得
        paramMdl.setNtp060GyomuList(cBiz.getGyomuList(con, ""));
        String mes = "";
        if (paramMdl.getNtp061NgySid() > 0) {
            mes = "選択してください";
        }
        //プロセスリスト取得
        paramMdl.setNtp060ProcessList(cBiz.getProcessList(con, mes, paramMdl.getNtp061NgySid()));
        //顧客源泉リスト取得
        paramMdl.setNtp060ContactList(cBiz.getContactList(con, "指定なし"));
        UDate now = new UDate();

        //年リスト取得
        paramMdl.setNtp060YearList(cBiz.getYearLavel(now.getYear()));
        //月リスト取得
        paramMdl.setNtp060MonthList(cBiz.getMonthLavel());
        //日リスト取得
        paramMdl.setNtp060DayList(cBiz.getDayLavel());
    }

    /**
     * <br>[機  能] 選択された商品情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl フォーム
     * @throws SQLException SQL実行時例外
     */
    protected void _setShohinData(Connection con, Ntp061ParamModel paramMdl) throws SQLException {
        if (paramMdl.getNtp061ChkShohinSidList() != null) {
            NtpShohinDao shohinDao = new NtpShohinDao(con);
            List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
            ArrayList<NtpShohinModel> shohinList
                = (ArrayList<NtpShohinModel>)
                   shohinDao.select(paramMdl.getNtp061ChkShohinSidList());

            for (NtpShohinModel mdl : shohinList) {
                labelList.add(
                        new LabelValueBean(mdl.getNhnName(), String.valueOf(mdl.getNhnSid())));
            }
            paramMdl.setNtp061ShohinList(labelList);
        }
    }

    /**
     * <br>[機  能] 選択された会社情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl フォーム

     * @throws SQLException SQL実行時例外
     */
    protected void _setCompanyData(Connection con, Ntp061ParamModel paramMdl)
    throws SQLException {
        if (!StringUtil.isNullZeroString(paramMdl.getNtp061CompanySid())) {

            //遷移先判定（日報登録画面へ戻るか）
            boolean backNtpFlg = false;
            if (paramMdl.getNtp061AddCompFlg() == 2) {
                backNtpFlg = true;
                paramMdl.setNtp061SvCompanySid(paramMdl.getNtp061CompanySid());
            }

            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyModel companyMdl
                = companyDao.select(Integer.parseInt(paramMdl.getNtp061CompanySid()));

            if (companyMdl != null) {
                paramMdl.setNtp061AcoCode(companyMdl.getAcoCode());
                paramMdl.setNtp061CompanyName(companyMdl.getAcoName());
                if (backNtpFlg) {
                    paramMdl.setNtp061SvCompanyCode(companyMdl.getAcoCode());
                    paramMdl.setNtp061SvCompanyName(companyMdl.getAcoName());
                }

                if (!StringUtil.isNullZeroString(paramMdl.getNtp061CompanyBaseSid())) {
                    AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
                    AdrCompanyBaseModel companyBaseMdl
                        = companyBaseDao.select(Integer.parseInt(paramMdl.getNtp061CompanySid()),
                                Integer.parseInt(paramMdl.getNtp061CompanyBaseSid()));
                    if (companyBaseMdl != null) {
                        String companyBaseName = companyBaseMdl.getAbaName();
                        String companyBaseType
                            = AddressBiz.getCompanyBaseTypeName(
                                    companyBaseMdl.getAbaType(), reqMdl__);
                        if (!StringUtil.isNullZeroString(companyBaseType)) {
                            companyBaseName = companyBaseType + " ： " + companyBaseName;
                        }
                        paramMdl.setNtp061CompanyBaseName(companyBaseName);
                        if (backNtpFlg) {
                            paramMdl.setNtp061SvCompanyBaseSid(
                                    String.valueOf(companyBaseMdl.getAbaSid()));
                            paramMdl.setNtp061SvCompanyBaseName(companyBaseName);
                        }
                    }
                }
            }
        }
    }

    /**
     * 見積もり、受注の日付コンボを設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp061Form
     */
    private void __setDateCombo(Ntp061ParamModel paramMdl) {

        UDate dspDate = new UDate();

        //年月日コンボを設定
        paramMdl.setNtp061YearLabel(__getYearLabelList());   //年
        paramMdl.setNtp061MonthLabel(__getMonthLabelList()); //月
        paramMdl.setNtp061DayLabel(__getDayLabelList());     //日

        //期間設定
        paramMdl.setNtp061MitumoriYear(
                NullDefault.getString(paramMdl.getNtp061MitumoriYear(), dspDate.getStrYear()));
        paramMdl.setNtp061MitumoriMonth(
                NullDefault.getString(paramMdl.getNtp061MitumoriMonth(),
                        String.valueOf(dspDate.getMonth())));
        paramMdl.setNtp061MitumoriDay(
                NullDefault.getString(paramMdl.getNtp061MitumoriDay(),
                        String.valueOf(dspDate.getIntDay())));

        paramMdl.setNtp061JutyuYear(
                NullDefault.getString(paramMdl.getNtp061JutyuYear(), dspDate.getStrYear()));
        paramMdl.setNtp061JutyuMonth(
                NullDefault.getString(paramMdl.getNtp061JutyuMonth(),
                        String.valueOf(dspDate.getMonth())));
        paramMdl.setNtp061JutyuDay(
                NullDefault.getString(paramMdl.getNtp061JutyuDay(),
                        String.valueOf(dspDate.getIntDay())));
    }

    /**
     * 年コンボを作成する

     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getYearLabelList() {

        int sysYear = (new UDate()).getYear();
        int start = sysYear - 5;
        int end = sysYear + 5;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        for (int value = start; value <= end; value++) {
            labelList.add(
              new LabelValueBean(
                 gsMsg.getMessage(
                   "cmn.year", new String[] {String.valueOf(value)}), String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * 月コンボを作成する

     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getMonthLabelList() {

        int start = 1;
        int end = 12;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                            value + gsMsg.getMessage("cmn.month"), String.valueOf(value)));
        }
        return labelList;
    }
    /**
     * 日コンボを作成する

     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getDayLabelList() {

        int start = 1;
        int end = 31;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                             value + gsMsg.getMessage("cmn.day"), String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 業種コンボをセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp060ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
        private void __setGyoshuList(
                Ntp061ParamModel paramMdl, int sessionUsrSid) throws SQLException {
            AddressBiz adrBiz = new AddressBiz(reqMdl__);
            paramMdl.setAtiCmbList(adrBiz.getGyosyuLabelList(con__));
     }

    /**
     * <br>[機  能] 都道府県コンボをセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp060ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
        private void __setTdfList(
                Ntp061ParamModel paramMdl, int sessionUsrSid) throws SQLException {
            //都道府県コンボを設定
            CommonBiz cmnBiz = new CommonBiz();
            GsMessage gsMsg = new GsMessage(reqMdl__);
            paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con__, gsMsg));
     }

    /**
     * <br>[機  能] インデックスをセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp060ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setIndex(Ntp061ParamModel paramMdl) throws SQLException {

        Adr240Dao dao = new Adr240Dao(con__);
        List<String> comkanaList = dao.getCompanyInitialList();
        List<LabelValueBean> indexList = new ArrayList<LabelValueBean>();

        List<String[]> kanaList = new ArrayList<String[]>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //50音
        String[] kana_a_line = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                gsMsg.getMessage("cmn.kana.i"),
                                gsMsg.getMessage("cmn.kana.u"),
                                gsMsg.getMessage("cmn.kana.e"),
                                gsMsg.getMessage("cmn.kana.o")};
        String[] kana_ka_line = new String[] {gsMsg.getMessage("cmn.kana.ka"),
                                 gsMsg.getMessage("cmn.kana.ki"),
                                 gsMsg.getMessage("cmn.kana.ku"),
                                 gsMsg.getMessage("cmn.kana.ke"),
                                 gsMsg.getMessage("cmn.kana.ko")};
        String[] kana_sa_line = new String[] {gsMsg.getMessage("cmn.kana.sa"),
                                 gsMsg.getMessage("cmn.kana.shi"),
                                 gsMsg.getMessage("cmn.kana.su"),
                                 gsMsg.getMessage("cmn.kana.se"),
                                 gsMsg.getMessage("cmn.kana.so")};
        String[] kana_ta_line = new String[] {gsMsg.getMessage("cmn.kana.ta"),
                                 gsMsg.getMessage("cmn.kana.chi"),
                                 gsMsg.getMessage("cmn.kana.tsu"),
                                 gsMsg.getMessage("cmn.kana.te"),
                                 gsMsg.getMessage("cmn.kana.to")};
        String[] kana_na_line = new String[] {gsMsg.getMessage("cmn.kana.na"),
                                 gsMsg.getMessage("cmn.kana.ni"),
                                 gsMsg.getMessage("cmn.kana.nu"),
                                 gsMsg.getMessage("cmn.kana.ne"),
                                 gsMsg.getMessage("cmn.kana.no")};
        String[] kana_ha_line = new String[] {gsMsg.getMessage("cmn.kana.ha"),
                                 gsMsg.getMessage("cmn.kana.hi"),
                                 gsMsg.getMessage("cmn.kana.fu"),
                                 gsMsg.getMessage("cmn.kana.he"),
                                 gsMsg.getMessage("cmn.kana.ho")};
        String[] kana_ma_line = new String[] {gsMsg.getMessage("cmn.kana.ma"),
                                 gsMsg.getMessage("cmn.kana.mi"),
                                 gsMsg.getMessage("cmn.kana.mu"),
                                 gsMsg.getMessage("cmn.kana.me"),
                                 gsMsg.getMessage("cmn.kana.mo")};
        String[] kana_ya_line = new String[] {gsMsg.getMessage("cmn.kana.ya"),
                                 gsMsg.getMessage("cmn.kana.yu"),
                                 gsMsg.getMessage("cmn.kana.yo")};
        String[] kana_ra_line = new String[] {gsMsg.getMessage("cmn.kana.ra"),
                                 gsMsg.getMessage("cmn.kana.ri"),
                                 gsMsg.getMessage("cmn.kana.ru"),
                                 gsMsg.getMessage("cmn.kana.re"),
                                 gsMsg.getMessage("cmn.kana.ro")};
        String[] kana_wa_line = new String[] {gsMsg.getMessage("cmn.kana.wa"),
                                 gsMsg.getMessage("cmn.kana.wo"),
                                 gsMsg.getMessage("cmn.kana.n")};

        String[] kana_index = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                            gsMsg.getMessage("cmn.kana.ka"),
                                            gsMsg.getMessage("cmn.kana.sa"),
                                            gsMsg.getMessage("cmn.kana.ta"),
                                            gsMsg.getMessage("cmn.kana.na"),
                                            gsMsg.getMessage("cmn.kana.ha"),
                                            gsMsg.getMessage("cmn.kana.ma"),
                                            gsMsg.getMessage("cmn.kana.ya"),
                                            gsMsg.getMessage("cmn.kana.ra"),
                                            gsMsg.getMessage("cmn.kana.wa")};
        kanaList.add(kana_a_line);
        kanaList.add(kana_ka_line);
        kanaList.add(kana_sa_line);
        kanaList.add(kana_ta_line);
        kanaList.add(kana_na_line);
        kanaList.add(kana_ha_line);
        kanaList.add(kana_ma_line);
        kanaList.add(kana_ya_line);
        kanaList.add(kana_ra_line);
        kanaList.add(kana_wa_line);

        if (comkanaList != null && comkanaList.size() > 0) {

            for (String[] kanaLine : kanaList) {

                String kanaExist = Adr240Const.NOT_EXIST;
                int i = 0;
                String headKana = null;
                for (String kana : kanaLine) {

                    if (i == 0) {
                        headKana = kana;
                    }

                    if (kana.equals(paramMdl.getNtp061AdrIndex())) {
                        kanaExist = Adr240Const.INDEX_SELECT;
                        break;
                    }
                    if (comkanaList.contains(kana)) {
                        kanaExist = Adr240Const.EXIST;
                        break;
                    }
                    i++;
                }
                indexList.add(new LabelValueBean(headKana, kanaExist));
            }
        } else {
            //会社情報が一件も存在しない場合。
            for (String kana : kana_index) {
                indexList.add(new LabelValueBean(kana, Adr240Const.NOT_EXIST));
            }
        }

        paramMdl.setNtp061AdrIndexList(indexList);

        //インデックスの詳細を設定する。
        __setIndexDetail(paramMdl);
    }

    /**
     * <br>[機  能] インデックスの詳細をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp060ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setIndexDetail(Ntp061ParamModel paramMdl) throws SQLException {

        String index = paramMdl.getNtp061AdrIndex();
        if (index.equals(Adr240Const.KANA_ALL) || index.equals(Adr240Const.KANA_COMPANY)) {
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //50音
        String[] kana_a_line = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                gsMsg.getMessage("cmn.kana.i"),
                                gsMsg.getMessage("cmn.kana.u"),
                                gsMsg.getMessage("cmn.kana.e"),
                                gsMsg.getMessage("cmn.kana.o")};
        String[] kana_ka_line = new String[] {gsMsg.getMessage("cmn.kana.ka"),
                                 gsMsg.getMessage("cmn.kana.ki"),
                                 gsMsg.getMessage("cmn.kana.ku"),
                                 gsMsg.getMessage("cmn.kana.ke"),
                                 gsMsg.getMessage("cmn.kana.ko")};
        String[] kana_sa_line = new String[] {gsMsg.getMessage("cmn.kana.sa"),
                                 gsMsg.getMessage("cmn.kana.shi"),
                                 gsMsg.getMessage("cmn.kana.su"),
                                 gsMsg.getMessage("cmn.kana.se"),
                                 gsMsg.getMessage("cmn.kana.so")};
        String[] kana_ta_line = new String[] {gsMsg.getMessage("cmn.kana.ta"),
                                 gsMsg.getMessage("cmn.kana.chi"),
                                 gsMsg.getMessage("cmn.kana.tsu"),
                                 gsMsg.getMessage("cmn.kana.te"),
                                 gsMsg.getMessage("cmn.kana.to")};
        String[] kana_na_line = new String[] {gsMsg.getMessage("cmn.kana.na"),
                                 gsMsg.getMessage("cmn.kana.ni"),
                                 gsMsg.getMessage("cmn.kana.nu"),
                                 gsMsg.getMessage("cmn.kana.ne"),
                                 gsMsg.getMessage("cmn.kana.no")};
        String[] kana_ha_line = new String[] {gsMsg.getMessage("cmn.kana.ha"),
                                 gsMsg.getMessage("cmn.kana.hi"),
                                 gsMsg.getMessage("cmn.kana.fu"),
                                 gsMsg.getMessage("cmn.kana.he"),
                                 gsMsg.getMessage("cmn.kana.ho")};
        String[] kana_ma_line = new String[] {gsMsg.getMessage("cmn.kana.ma"),
                                 gsMsg.getMessage("cmn.kana.mi"),
                                 gsMsg.getMessage("cmn.kana.mu"),
                                 gsMsg.getMessage("cmn.kana.me"),
                                 gsMsg.getMessage("cmn.kana.mo")};
        String[] kana_ya_line = new String[] {gsMsg.getMessage("cmn.kana.ya"),
                                 gsMsg.getMessage("cmn.kana.yu"),
                                 gsMsg.getMessage("cmn.kana.yo")};
        String[] kana_ra_line = new String[] {gsMsg.getMessage("cmn.kana.ra"),
                                 gsMsg.getMessage("cmn.kana.ri"),
                                 gsMsg.getMessage("cmn.kana.ru"),
                                 gsMsg.getMessage("cmn.kana.re"),
                                 gsMsg.getMessage("cmn.kana.ro")};
        String[] kana_wa_line = new String[] {gsMsg.getMessage("cmn.kana.wa"),
                                 gsMsg.getMessage("cmn.kana.wo"),
                                 gsMsg.getMessage("cmn.kana.n")};

        HashMap<String, String[]> map = new HashMap<String, String[]>();
        map.put(gsMsg.getMessage("cmn.kana.a"), kana_a_line);
        map.put(gsMsg.getMessage("cmn.kana.ka"), kana_ka_line);
        map.put(gsMsg.getMessage("cmn.kana.sa"), kana_sa_line);
        map.put(gsMsg.getMessage("cmn.kana.ta"), kana_ta_line);
        map.put(gsMsg.getMessage("cmn.kana.na"), kana_na_line);
        map.put(gsMsg.getMessage("cmn.kana.ha"), kana_ha_line);
        map.put(gsMsg.getMessage("cmn.kana.ma"), kana_ma_line);
        map.put(gsMsg.getMessage("cmn.kana.ya"), kana_ya_line);
        map.put(gsMsg.getMessage("cmn.kana.ra"), kana_ra_line);
        map.put(gsMsg.getMessage("cmn.kana.wa"), kana_wa_line);

        String[] indexLine = map.get(index);

        Adr240Dao dao = new Adr240Dao(con__);
        List<String> comkanaList = dao.getCompanyInitialList(indexLine);

        List<LabelValueBean> indexList = new ArrayList<LabelValueBean>();

        if (comkanaList != null && comkanaList.size() > 0) {
            for (String kana : indexLine) {
                String exist = Adr240Const.NOT_EXIST;
                if (kana.equals(paramMdl.getNtp061AdrStr())) {
                    //選択中
                    exist = Adr240Const.INDEX_SELECT;
                } else if (comkanaList.contains(kana)) {
                    exist = Adr240Const.EXIST;
                }
                indexList.add(new LabelValueBean(kana, exist));
            }
        } else {
            for (String kana : comkanaList) {
                indexList.add(new LabelValueBean(kana, Adr240Const.NOT_EXIST));
            }
        }
        paramMdl.setNtp061AdrStrList(indexList);
    }

    /**
     * <br>[機  能] 会社一覧をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp060ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setDspList(Ntp061ParamModel paramMdl, int sessionUsrSid) throws SQLException {

        String index = paramMdl.getNtp061AdrIndex();
        String str = paramMdl.getNtp061AdrStr();

        String[] initials = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (index.equals(Adr240Const.KANA_ALL)) {
            //全て
            initials = null;
        } else if (index.equals(Adr240Const.KANA_COMPANY)) {
            //会社未選択
            Adr240Model model = new Adr240Model();
            model.setAcoSid(0);
            model.setAbaSid(0);
            model.setAcoName(gsMsg.getMessage("address.adr240.1"));
            model.setAbaName(null);
            List<Adr240Model> dspList = new ArrayList<Adr240Model>();
            dspList.add(model);
            paramMdl.setNtp061AdrCompanyList(dspList);
            return;

        } else if (str.equals(Adr240Const.KANA_ALL)) {
            //行全て

            //50音
            String[] kana_a_line = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                    gsMsg.getMessage("cmn.kana.i"),
                                    gsMsg.getMessage("cmn.kana.u"),
                                    gsMsg.getMessage("cmn.kana.e"),
                                    gsMsg.getMessage("cmn.kana.o")};
            String[] kana_ka_line = new String[] {gsMsg.getMessage("cmn.kana.ka"),
                                     gsMsg.getMessage("cmn.kana.ki"),
                                     gsMsg.getMessage("cmn.kana.ku"),
                                     gsMsg.getMessage("cmn.kana.ke"),
                                     gsMsg.getMessage("cmn.kana.ko")};
            String[] kana_sa_line = new String[] {gsMsg.getMessage("cmn.kana.sa"),
                                     gsMsg.getMessage("cmn.kana.shi"),
                                     gsMsg.getMessage("cmn.kana.su"),
                                     gsMsg.getMessage("cmn.kana.se"),
                                     gsMsg.getMessage("cmn.kana.so")};
            String[] kana_ta_line = new String[] {gsMsg.getMessage("cmn.kana.ta"),
                                     gsMsg.getMessage("cmn.kana.chi"),
                                     gsMsg.getMessage("cmn.kana.tsu"),
                                     gsMsg.getMessage("cmn.kana.te"),
                                     gsMsg.getMessage("cmn.kana.to")};
            String[] kana_na_line = new String[] {gsMsg.getMessage("cmn.kana.na"),
                                     gsMsg.getMessage("cmn.kana.ni"),
                                     gsMsg.getMessage("cmn.kana.nu"),
                                     gsMsg.getMessage("cmn.kana.ne"),
                                     gsMsg.getMessage("cmn.kana.no")};
            String[] kana_ha_line = new String[] {gsMsg.getMessage("cmn.kana.ha"),
                                     gsMsg.getMessage("cmn.kana.hi"),
                                     gsMsg.getMessage("cmn.kana.fu"),
                                     gsMsg.getMessage("cmn.kana.he"),
                                     gsMsg.getMessage("cmn.kana.ho")};
            String[] kana_ma_line = new String[] {gsMsg.getMessage("cmn.kana.ma"),
                                     gsMsg.getMessage("cmn.kana.mi"),
                                     gsMsg.getMessage("cmn.kana.mu"),
                                     gsMsg.getMessage("cmn.kana.me"),
                                     gsMsg.getMessage("cmn.kana.mo")};
            String[] kana_ya_line = new String[] {gsMsg.getMessage("cmn.kana.ya"),
                                     gsMsg.getMessage("cmn.kana.yu"),
                                     gsMsg.getMessage("cmn.kana.yo")};
            String[] kana_ra_line = new String[] {gsMsg.getMessage("cmn.kana.ra"),
                                     gsMsg.getMessage("cmn.kana.ri"),
                                     gsMsg.getMessage("cmn.kana.ru"),
                                     gsMsg.getMessage("cmn.kana.re"),
                                     gsMsg.getMessage("cmn.kana.ro")};
            String[] kana_wa_line = new String[] {gsMsg.getMessage("cmn.kana.wa"),
                                     gsMsg.getMessage("cmn.kana.wo"),
                                     gsMsg.getMessage("cmn.kana.n")};

            HashMap<String, String[]> map = new HashMap<String, String[]>();
            map.put(gsMsg.getMessage("cmn.kana.a"), kana_a_line);
            map.put(gsMsg.getMessage("cmn.kana.ka"), kana_ka_line);
            map.put(gsMsg.getMessage("cmn.kana.sa"), kana_sa_line);
            map.put(gsMsg.getMessage("cmn.kana.ta"), kana_ta_line);
            map.put(gsMsg.getMessage("cmn.kana.na"), kana_na_line);
            map.put(gsMsg.getMessage("cmn.kana.ha"), kana_ha_line);
            map.put(gsMsg.getMessage("cmn.kana.ma"), kana_ma_line);
            map.put(gsMsg.getMessage("cmn.kana.ya"), kana_ya_line);
            map.put(gsMsg.getMessage("cmn.kana.ra"), kana_ra_line);
            map.put(gsMsg.getMessage("cmn.kana.wa"), kana_wa_line);
            initials = map.get(index);

        } else {
            initials = new String[]{str};
        }

        Adr240Dao dao = new Adr240Dao(con__);
        //検索件数
        int searchCnt = dao.countCompanyList(initials);

        //最大件数
        int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;
        if (paramMdl.getNtp061AdrPrsMode() != 0) {
            maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;
        }


        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getNtp061Adrpage();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setNtp061Adrpage(page);
        paramMdl.setNtp061AdrpageTop(page);
        paramMdl.setNtp061AdrpageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
        }

        if (searchCnt < 1) {
            return;
        }
        //会社一覧を取得
        List<Adr240Model> dspList = dao.getCompanyList(initials, page, maxCnt);
        paramMdl.setNtp061AdrCompanyList(dspList);
    }

    /**
     * <br>[機  能] 会社一覧をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp060ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setDspDetailList(
            Ntp061ParamModel paramMdl, int sessionUsrSid) throws SQLException {

        Adr240SearchModel searchMdl = new Adr240SearchModel();

        //ソートキー
        searchMdl.setSortKey(paramMdl.getNtp061AdrSortKey());
        //オーダーキー
        searchMdl.setOrderKey(paramMdl.getNtp061AdrOrderKey());
        //企業コード
        searchMdl.setCoCode(paramMdl.getNtp061svAdrCode());
        //会社名
        searchMdl.setCoName(paramMdl.getNtp061svAdrCoName());
        //会社名カナ
        searchMdl.setCoNameKn(paramMdl.getNtp061svAdrCoNameKn());
        //支店・営業所名
        searchMdl.setCoBaseName(paramMdl.getNtp061svAdrCoBaseName());
        //業種
        searchMdl.setAtiSid(paramMdl.getNtp061svAdrAtiSid());
        //都道府県
        searchMdl.setTdfk(paramMdl.getNtp061svAdrTdfk());
        //備考
        searchMdl.setBiko(paramMdl.getNtp061svAdrBiko());

        Adr240Dao dao = new Adr240Dao(con__);
        //検索件数
        int searchCnt = dao.getSearchCount(searchMdl);

        //最大件数
        int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getNtp061Adrpage();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setNtp061Adrpage(page);
        paramMdl.setNtp061AdrpageTop(page);
        paramMdl.setNtp061AdrpageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
        }

        if (searchCnt < 1) {
            return;
        }
        //会社一覧を取得
        searchMdl.setPage(page);
        searchMdl.setMaxViewCount(maxCnt);
        List<Adr240Model> dspList = dao.getSearchResultList(searchMdl);
        paramMdl.setNtp061AdrCompanyList(dspList);
    }

    /**
     * <br>[機  能] 検索処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp060ParamModel
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行エラー
     */
    private void __doSearch(
            Ntp061ParamModel paramMdl,
            int sessionUserSid) throws SQLException {

        //検索モデルの設定
        Ntp130SearchModel searchMdl = new Ntp130SearchModel();
        searchMdl.setNscSid(paramMdl.getNtp061CatSid());
        searchMdl.setNhnCode(paramMdl.getNtp061ItmNhnCode());
        searchMdl.setNhnName(paramMdl.getNtp061ItmNhnName());

        String price = NullDefault.getStringZeroLength(paramMdl.getNtp061ItmNhnPriceSale(), "-1");
        searchMdl.setNhnPriceSale(Integer.parseInt(price.replaceAll(",", "")));
        searchMdl.setNhnPriceSaleKbn(paramMdl.getNtp061ItmNhnPriceSaleKbn());

        String cost = NullDefault.getStringZeroLength(paramMdl.getNtp061ItmNhnPriceCost(), "-1");
        searchMdl.setNhnPriceCost(Integer.parseInt(cost.replaceAll(",", "")));
        searchMdl.setNhnPriceCostKbn(paramMdl.getNtp061ItmNhnPriceCostKbn());

        searchMdl.setSortKey1(paramMdl.getNtp061ItmSortKey1());
        searchMdl.setSortKey2(paramMdl.getNtp061ItmSortKey2());
        searchMdl.setOrderKey1(paramMdl.getNtp061ItmOrderKey1());
        searchMdl.setOrderKey2(paramMdl.getNtp061ItmOrderKey2());

        int maxCnt = 5;
//        NtpCommonBiz biz = new NtpCommonBiz(con__, req__);
//        NtpPriConfModel pconf = biz.getNtpPriConfModel(con__, sessionUserSid);
//        if (pconf != null) {
//            maxCnt = pconf.getNprDspList();
//        }

        //最大件数
        Ntp130ShohinDao shohinDao = new Ntp130ShohinDao(con__, reqMdl__);
        int searchCnt = shohinDao.getShohinCount(searchMdl);

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getNtp061ItmPageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setNtp061ItmPageTop(page);
        paramMdl.setNtp061ItmPageBottom(page);
        paramMdl.setNtp061ItmPage((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setNtp061ItmPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));

        //検索モデルにて商品一覧の取得・設定
        paramMdl.setNtp061ItmShohinList(
                (ArrayList<Ntp130ShohinModel>) shohinDao.select(searchMdl, page, maxCnt));
    }

    /**
     * <br>[機  能] ソートキーリストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ソートキーリストリスト
     */
    private List<LabelValueBean> __getSortList() {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("", String.valueOf(-1)));

        for (int i = 0; i < 6; i++) {
            labelList.add(
                    new LabelValueBean(GSConstNippou.SORT_KEY_NHK_ALL_TEXT[i],
                        String.valueOf(GSConstNippou.SORT_KEY_NHK_ALL[i])));
        }
        return labelList;
    }
    /**
     * <br>[機  能] 案件情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnkenModel __createNtpAnken(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnkenModel mdl = new NtpAnkenModel();
        mdl.setNanAuid(usrSid);
        mdl.setNanAdate(nowDate);
        mdl.setNanEuid(usrSid);
        mdl.setNanEdate(nowDate);
        return mdl;
    }
    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinModel __createNtpAnShohin(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinModel mdl = new NtpAnShohinModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinHistoryModel __createNtpAnShohinHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinHistoryModel mdl = new NtpAnShohinHistoryModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 担当者情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnMemberModel __createNtpAnMember(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnMemberModel mdl = new NtpAnMemberModel();
        mdl.setNamAuid(usrSid);
        mdl.setNamAdate(nowDate);
        mdl.setNamEuid(usrSid);
        mdl.setNamEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 担当者履歴情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnMemberHistoryModel __createNtpAnMemberHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnMemberHistoryModel mdl = new NtpAnMemberHistoryModel();
        mdl.setNamAuid(usrSid);
        mdl.setNamAdate(nowDate);
        mdl.setNamEuid(usrSid);
        mdl.setNamEdate(nowDate);
        return mdl;
    }
    /**
     *
     * <br>[機  能] 案件登録ビジネスロジック
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param param パラメータモデル
     * @throws SQLException SQL実行時例外
     * @throws InvocationTargetException パラメータコピー時例外
     * @throws IllegalAccessException パラメータコピー時例外
     */
    public void doResistAnken(int usrSid, Ntp061ParamModel param)
            throws SQLException, IllegalAccessException, InvocationTargetException {
        NtpAnkenDao ankenDao = new NtpAnkenDao(con__);
        NtpAnkenModel ankenMdl = __createNtpAnken(usrSid);

        String mitumori = NullDefault.getStringZeroLength(
                param.getNtp061NanKinMitumori().replaceAll(",", ""), "0");
        String jutyu = NullDefault.getStringZeroLength(
                param.getNtp061NanKinJutyu().replaceAll(",", ""), "0");
        String acoSid
            = NullDefault.getStringZeroLength(param.getNtp061CompanySid(), "-1");
        String abaSid
            = NullDefault.getStringZeroLength(param.getNtp061CompanyBaseSid(), "-1");

        UDate date = new UDate();
        date.setZeroHhMmSs();

        ankenMdl.setNanDate(date);
        ankenMdl.setNanCode(param.getNtp061NanCode());
        ankenMdl.setNanName(param.getNtp061NanName());
        ankenMdl.setNanDetial(param.getNtp061NanSyosai());
        ankenMdl.setAcoSid(Integer.parseInt(acoSid));
        ankenMdl.setAbaSid(Integer.parseInt(abaSid));
        ankenMdl.setNgpSid(param.getNtp061NgpSid());
        ankenMdl.setNanMikomi(param.getNtp061NanMikomi());
        ankenMdl.setNanKinMitumori(Integer.parseInt(mitumori));
        ankenMdl.setNanKinJutyu(Integer.parseInt(jutyu));
        ankenMdl.setNanSyodan(param.getNtp061NanSyodan());
        ankenMdl.setNanState(param.getNtp061NanState());
        ankenMdl.setNanPermitView(param.getNtp061NanPermitView());
        if (param.getNtp061NanPermitView() == GSConstNippou.NAP_KBN_USERGROUP
                || param.getNtp061NanPermitView() == GSConstNippou.NAP_KBN_TANTO) {
            ankenMdl.setNanPermitEdit(param.getNtp061NanPermitView());
        } else {
            ankenMdl.setNanPermitEdit(param.getNtp061NanPermitEdit());
        }
        ankenMdl.setNcnSid(param.getNtp061NcnSid());

        UDate mitumoriDate = new UDate();
        mitumoriDate.setDate(
                Integer.parseInt(param.getNtp061MitumoriYear()),
                Integer.parseInt(param.getNtp061MitumoriMonth()),
                Integer.parseInt(param.getNtp061MitumoriDay()));
        mitumoriDate.setZeroHhMmSs();
        UDate jutyuDate = new UDate();
        jutyuDate.setDate(
                Integer.parseInt(param.getNtp061JutyuYear()),
                Integer.parseInt(param.getNtp061JutyuMonth()),
                Integer.parseInt(param.getNtp061JutyuDay()));
        jutyuDate.setZeroHhMmSs();
        ankenMdl.setNanMitumoriDate(mitumoriDate);
        ankenMdl.setNanJutyuDate(jutyuDate);


        int nanSid = -1;

        if (param.getNtp060ProcMode().equals(GSConstNippou.CMD_ADD)) {
            //追加モード

            //SID採番
            nanSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN, usrSid);
            ankenMdl.setNanSid(nanSid);
            ankenDao.insert(ankenMdl);
        } else {
            nanSid = param.getNtp060NanSid();
            //変更モード
            ankenMdl.setNanSid(nanSid);
            ankenDao.update(ankenMdl);

        }

        //案件商品情報の登録
        NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con__);
        if (param.getNtp060ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更モード
            anShohinDao.delete(nanSid);
        }

        NtpAnShohinModel anShohinMdl = null;
        if (param.getNtp061ChkShohinSidList() != null
            && param.getNtp061ChkShohinSidList().length > 0) {
            for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                anShohinMdl = __createNtpAnShohin(usrSid);
                anShohinMdl.setNhnSid(Integer.parseInt(shohinSid));
                anShohinMdl.setNanSid(nanSid);
                anShohinDao.insert(anShohinMdl);
            }
        }



        //担当者
        NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con__);
        if (param.getNtp060ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更モード
            anMemberDao.delete(nanSid);
        }
        NtpAnMemberModel anMemberMdl = null;
        String[] svUsers = param.getSv_users();
        if (svUsers != null) {
            for (int i = 0; i < svUsers.length; i++) {
                if (GSValidateUtil.isNumber(svUsers[i])) {
                    anMemberMdl = __createNtpAnMember(usrSid);
                    anMemberMdl.setUsrSid(Integer.parseInt(svUsers[i]));
                    anMemberMdl.setNanSid(nanSid);
                    anMemberDao.insert(anMemberMdl);
                }
            }
        }
        //案件権限の登録
        NtpAnkenPermitDao napDao = new NtpAnkenPermitDao(con__);
        if (param.getNtp060ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            napDao.delete(nanSid);
        }
        String[] selectedList = param.getNtp061NanPermitUserView();
        if (GSConstNippou.NAP_KBN_USERGROUP == ankenMdl.getNanPermitView()) {
            for (String selected : selectedList) {
                __insertPermitUsrGrp(nanSid, selected, GSConst.SP_AUTH_VIEWONLY);
            }
        }

        if (GSConstNippou.NAP_KBN_USERGROUP == ankenMdl.getNanPermitEdit()) {
            if (param.getNtp061NanPermitView() == GSConstNippou.NAP_KBN_USERGROUP) {
                //閲覧権限 = 担当者および指定ユーザ の場合
                selectedList = param.getNtp061NanPermitUserEdit();
            } else if (param.getNtp061NanPermitEdit() == GSConstNippou.NAP_KBN_USERGROUP) {
                //編集権限 = 担当者および指定ユーザ の場合
                selectedList = param.getNtp061NanPermitEditUser();
            }

            for (String selected : selectedList) {
                __insertPermitUsrGrp(nanSid, selected, GSConst.SP_AUTH_EDIT);
            }
        }


        NtpAnkenHistoryDao hisDao = new NtpAnkenHistoryDao(con__);
        NtpAnShohinHistoryDao shohinHisDao = new NtpAnShohinHistoryDao(con__);
        NtpAnMemberHistoryDao memberHisDao = new NtpAnMemberHistoryDao(con__);

        //今日の履歴があるかないか
        int nahSid = -1;
        nahSid = hisDao.checkData(nanSid, date);

        NtpAnkenHistoryModel hisMdl = new NtpAnkenHistoryModel();
        BeanUtils.copyProperties(hisMdl, ankenMdl);
        UDate nanMonth = new UDate();
        nanMonth.setYear(hisMdl.getNanDate().getYear());
        nanMonth.setMonth(hisMdl.getNanDate().getMonth());
        nanMonth.setDay(nanMonth.getMaxDayOfMonth());
        nanMonth.setZeroHhMmSs();
        hisMdl.setNanMonth(nanMonth);

        //履歴は見積もり金額、受注金額を0に設定
        hisMdl.setNanKinJutyu(0);
        hisMdl.setNanKinMitumori(0);

        if (nahSid == -1) {
            //履歴の新規登録

            //履歴SID採番
            nahSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
            hisMdl.setNahSid(nahSid);
            hisDao.insert(hisMdl);

            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (param.getNtp061ChkShohinSidList() != null
                        && param.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (param.getSv_users() != null
                        && param.getSv_users().length > 0) {
                    for (String uSid : param.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }
        } else {
            //履歴の更新
            hisMdl.setNahSid(nahSid);
            hisDao.update(hisMdl);
            shohinHisDao.delete(nahSid);
            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (param.getNtp061ChkShohinSidList() != null
                        && param.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            memberHisDao.delete(nahSid);
            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (param.getSv_users() != null
                        && param.getSv_users().length > 0) {
                    for (String uSid : param.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }
        }

        //見積もり日の履歴があるかないか
        nahSid = -1;
        nahSid = hisDao.checkData(nanSid, ankenMdl.getNanMitumoriDate());
        nanMonth = new UDate();
        nanMonth.setYear(ankenMdl.getNanMitumoriDate().getYear());
        nanMonth.setMonth(ankenMdl.getNanMitumoriDate().getMonth());
        nanMonth.setDay(nanMonth.getMaxDayOfMonth());
        nanMonth.setZeroHhMmSs();
        hisMdl.setNanMonth(nanMonth);
        ankenMdl.getNanMitumoriDate().setZeroHhMmSs();
        hisMdl.setNanDate(ankenMdl.getNanMitumoriDate());

        //見積もり日は案件状態を商談中に設定
        //hisMdl.setNanSyodan(0);

        if (nahSid == -1) {
            //履歴の新規登録

            //履歴SID採番
            nahSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
            hisMdl.setNahSid(nahSid);
            hisDao.insert(hisMdl);

            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (param.getNtp061ChkShohinSidList() != null
                        && param.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (param.getSv_users() != null
                        && param.getSv_users().length > 0) {
                    for (String uSid : param.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }
        } else {
            //履歴の更新
            hisMdl.setNahSid(nahSid);
            hisDao.update(hisMdl);
            shohinHisDao.delete(nahSid);
            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (param.getNtp061ChkShohinSidList() != null
                        && param.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            memberHisDao.delete(nahSid);
            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (param.getSv_users() != null
                        && param.getSv_users().length > 0) {
                    for (String uSid : param.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }
        }

        //受注日の履歴があるかないか
        nahSid = -1;
        nahSid = hisDao.checkData(nanSid, ankenMdl.getNanJutyuDate());
        nanMonth = new UDate();
        nanMonth.setYear(ankenMdl.getNanJutyuDate().getYear());
        nanMonth.setMonth(ankenMdl.getNanJutyuDate().getMonth());
        nanMonth.setDay(nanMonth.getMaxDayOfMonth());
        nanMonth.setZeroHhMmSs();
        hisMdl.setNanMonth(nanMonth);
        ankenMdl.getNanJutyuDate().setZeroHhMmSs();
        hisMdl.setNanDate(ankenMdl.getNanJutyuDate());

        //受注日は案件状態を受注に設定
        //hisMdl.setNanSyodan(1);

        if (nahSid == -1) {
            //履歴の新規登録

            //履歴SID採番
            nahSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
            hisMdl.setNahSid(nahSid);
            hisDao.insert(hisMdl);

            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (param.getNtp061ChkShohinSidList() != null
                        && param.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (param.getSv_users() != null
                        && param.getSv_users().length > 0) {
                    for (String uSid : param.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }
        } else {
            //履歴の更新
            hisMdl.setNahSid(nahSid);
            hisDao.update(hisMdl);
            shohinHisDao.delete(nahSid);
            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (param.getNtp061ChkShohinSidList() != null
                        && param.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            memberHisDao.delete(nahSid);
            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (param.getSv_users() != null
                        && param.getSv_users().length > 0) {
                    for (String uSid : param.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }
        }

    }

    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSid 案件SID
     * @param selected 選択済みID文字列（ユーザSID or G+グループSID）
     * @param kbn 権限区分 0:閲覧のみ 1:登録・編集
     * @throws SQLException SQL実行時例外
     */
    private void __insertPermitUsrGrp(int nanSid, String selected, int kbn) throws SQLException {
        if (selected == null) {
            return;
        }
        NtpAnkenPermitDao napDao = new NtpAnkenPermitDao(con__);
        NtpAnkenPermitModel model = new NtpAnkenPermitModel();
        model.setNanSid(nanSid);
        model.setNapKbn(kbn);
        int sid = NullDefault.getInt(selected, -1);
        if (selected.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
            //グループ
            sid = NullDefault.getInt(selected.substring(
                    UserGroupSelectBiz.GROUP_PREFIX.length()), -1);
            if (sid < 0) {
                return;
            }
            model.setGrpSid(sid);
        } else if (sid >= 0) {
            //ユーザ
            model.setUsrSid(sid);
        }
        napDao.insert(model);
    }

    /**
    *
    * <br>[機  能] 案件登録ビジネスロジック
    * <br>[解  説]
    * <br>[備  考]
    * @param usrSid セッションユーザSID
    * @param param パラメータモデル
    * @throws SQLException SQL実行時例外
    * @throws InvocationTargetException パラメータコピー時例外
    * @throws IllegalAccessException パラメータコピー時例外
    * @return 案件SID
    */
    public int doResistAnkenPop(int usrSid, Ntp061ParamModel param)
            throws SQLException, IllegalAccessException, InvocationTargetException {
        Connection con = con__;
        MlCountMtController cntCon = cntCon__;

        NtpAnkenDao ankenDao = new NtpAnkenDao(con);
        NtpAnkenModel ankenMdl = __createNtpAnken(usrSid);

        String mitumori = NullDefault.getStringZeroLength(
                param.getNtp061NanKinMitumori().replaceAll(",", ""), "0");
        String jutyu = NullDefault.getStringZeroLength(
                param.getNtp061NanKinJutyu().replaceAll(",", ""), "0");
        String acoSid
            = NullDefault.getStringZeroLength(param.getNtp061CompanySid(), "-1");
        String abaSid
            = NullDefault.getStringZeroLength(param.getNtp061CompanyBaseSid(), "-1");

        UDate date = new UDate();
        date.setZeroHhMmSs();

        ankenMdl.setNanDate(date);
        ankenMdl.setNanCode(param.getNtp061NanCode());
        ankenMdl.setNanName(param.getNtp061NanName());
        ankenMdl.setNanDetial(param.getNtp061NanSyosai());
        ankenMdl.setAcoSid(Integer.parseInt(acoSid));
        ankenMdl.setAbaSid(Integer.parseInt(abaSid));
        ankenMdl.setNgpSid(param.getNtp061NgpSid());
        ankenMdl.setNanMikomi(param.getNtp061NanMikomi());
        ankenMdl.setNanKinMitumori(Integer.parseInt(mitumori));
        ankenMdl.setNanKinJutyu(Integer.parseInt(jutyu));
        ankenMdl.setNanSyodan(param.getNtp061NanSyodan());
        ankenMdl.setNanState(param.getNtp061NanState());
        ankenMdl.setNanPermitView(param.getNtp061NanPermitView());
        ankenMdl.setNanPermitEdit(param.getNtp061NanPermitEdit());
        ankenMdl.setNcnSid(param.getNtp061NcnSid());

        UDate mitumoriDate = new UDate();
        mitumoriDate.setDate(
                Integer.parseInt(param.getNtp061MitumoriYear()),
                Integer.parseInt(param.getNtp061MitumoriMonth()),
                Integer.parseInt(param.getNtp061MitumoriDay()));
        mitumoriDate.setZeroHhMmSs();
        UDate jutyuDate = new UDate();
        jutyuDate.setDate(
                Integer.parseInt(param.getNtp061JutyuYear()),
                Integer.parseInt(param.getNtp061JutyuMonth()),
                Integer.parseInt(param.getNtp061JutyuDay()));
        jutyuDate.setZeroHhMmSs();
        ankenMdl.setNanMitumoriDate(mitumoriDate);
        ankenMdl.setNanJutyuDate(jutyuDate);

        int nanSid = -1;

        //SID採番
        nanSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
            GSConstNippou.SBNSID_SUB_ANKEN, usrSid);
        ankenMdl.setNanSid(nanSid);
        ankenDao.insert(ankenMdl);


        //案件商品情報の登録
        NtpAnShohinModel  anShohinMdl = null;
        NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);
        if (param.getNtp061ChkShohinSidList() != null
            && param.getNtp061ChkShohinSidList().length > 0) {
            for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                anShohinMdl = __createNtpAnShohin(usrSid);
                anShohinMdl.setNhnSid(Integer.parseInt(shohinSid));
                anShohinMdl.setNanSid(nanSid);
                anShohinDao.insert(anShohinMdl);
            }
        }

        //担当者
        NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con);
        NtpAnMemberModel anMemberMdl = null;
        String[] svUsers = param.getSv_users();
        if (svUsers != null) {
            for (int i = 0; i < svUsers.length; i++) {
                if (GSValidateUtil.isNumber(svUsers[i])) {
                    anMemberMdl = __createNtpAnMember(usrSid);
                    anMemberMdl.setUsrSid(Integer.parseInt(svUsers[i]));
                    anMemberMdl.setNanSid(nanSid);
                    anMemberDao.insert(anMemberMdl);
                }
            }
        }

        //案件権限の登録
        NtpAnkenPermitDao napDao = new NtpAnkenPermitDao(con__);
        if (param.getNtp060ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            napDao.delete(nanSid);
        }
        String[] selectedList = param.getNtp061NanPermitUserView();
        if (GSConstNippou.NAP_KBN_USERGROUP == param.getNtp061NanPermitView()) {
            for (String selected : selectedList) {
                __insertPermitUsrGrp(nanSid, selected, GSConst.SP_AUTH_VIEWONLY);
            }
        }
        if (GSConstNippou.NAP_KBN_USERGROUP == param.getNtp061NanPermitEdit()) {
            if (param.getNtp061NanPermitView() == GSConstNippou.NAP_KBN_USERGROUP) {
                //閲覧権限 = 担当者および指定ユーザ の場合
                selectedList = param.getNtp061NanPermitUserEdit();
            } else if (param.getNtp061NanPermitEdit() == GSConstNippou.NAP_KBN_USERGROUP) {
                //編集権限 = 担当者および指定ユーザ の場合
                selectedList = param.getNtp061NanPermitEditUser();
            }
            for (String selected : selectedList) {
                __insertPermitUsrGrp(nanSid, selected, GSConst.SP_AUTH_EDIT);
            }
        }


        //履歴の新規登録
        NtpAnkenHistoryDao hisDao = new NtpAnkenHistoryDao(con);
        NtpAnShohinHistoryDao shohinHisDao = new NtpAnShohinHistoryDao(con);
        NtpAnMemberHistoryDao memberHisDao = new NtpAnMemberHistoryDao(con);

        //履歴は見積もり金額、受注金額を0に設定
        NtpAnkenHistoryModel hisMdl = new NtpAnkenHistoryModel();
        BeanUtils.copyProperties(hisMdl, ankenMdl);
        UDate nanMonth = new UDate();
        nanMonth.setYear(hisMdl.getNanDate().getYear());
        nanMonth.setMonth(hisMdl.getNanDate().getMonth());
        nanMonth.setDay(nanMonth.getMaxDayOfMonth());
        nanMonth.setZeroHhMmSs();
        hisMdl.setNanMonth(nanMonth);

        //履歴は見積もり金額、受注金額を0に設定
        hisMdl.setNanKinJutyu(0);
        hisMdl.setNanKinMitumori(0);

        //履歴SID採番
        int nahSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
            GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
        hisMdl.setNahSid(nahSid);
        hisDao.insert(hisMdl);

        if (anShohinMdl != null) {
            NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
            if (param.getNtp061ChkShohinSidList() != null
                    && param.getNtp061ChkShohinSidList().length > 0) {
                for (String shohinSid : param.getNtp061ChkShohinSidList()) {
                    shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                    shohinHisMdl.setNahSid(nahSid);
                    shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                    shohinHisMdl.setNanSid(nanSid);
                    shohinHisDao.insert(shohinHisMdl);
                }
            }
        }

        if (anMemberMdl != null) {
            NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
            if (param.getSv_users() != null
                    && param.getSv_users().length > 0) {
                for (String uSid : param.getSv_users()) {
                    if (GSValidateUtil.isNumber(uSid)) {
                        memberHisMdl = __createNtpAnMemberHistory(usrSid);
                        memberHisMdl.setNahSid(nahSid);
                        memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                        memberHisMdl.setNanSid(nanSid);
                        memberHisDao.insert(memberHisMdl);
                    }
                }
            }
        }
        return nanSid;
    }
    /**
     *
     * <br>[機  能] 権限設定の読み込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSid 案件SID
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    private void __loadUserGroupSelected(int nanSid, Ntp061ParamModel paramMdl)
    throws SQLException {

        NtpAnkenPermitDao anPermitDao = new NtpAnkenPermitDao(con__);
        List<NtpAnkenPermitModel> pList = anPermitDao.select(nanSid, GSConst.SP_AUTH_VIEWONLY);
        List<String> viewList = new ArrayList<String>();
        List<String> editList = new ArrayList<String>();
        for (NtpAnkenPermitModel model : pList) {
            String strSid;
            if (model.getUsrSid() >= 0) {
                strSid = String.valueOf(model.getUsrSid());
            } else {
                strSid = UserGroupSelectBiz.GROUP_PREFIX + String.valueOf(model.getGrpSid());
            }
            if (model.getNapKbn() == GSConst.SP_AUTH_VIEWONLY) {
                viewList.add(strSid);
            } else if (model.getNapKbn() == GSConst.SP_AUTH_EDIT) {
                editList.add(strSid);
            }
        }

        
        paramMdl.setNtp061NanPermitUserView(viewList.toArray(new String[viewList.size()]));
        paramMdl.setNtp061NanPermitUserEdit(editList.toArray(new String[editList.size()]));
        paramMdl.setNtp061NanPermitEditUser(editList.toArray(new String[editList.size()]));
    }
    /**
     *
     * <br>[機  能] 既存案件情報の読み込み
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    public void loadAnken(Ntp061ParamModel paramMdl) throws SQLException {
        //変更処理
        Ntp061AnkenDao ankenDao = new Ntp061AnkenDao(con__);
        Ntp061AnkenModel ankenModel = ankenDao.select(
                                     paramMdl.getNtp060NanSid(), reqMdl__);
        paramMdl.setNtp061Date(ankenModel.getNanDate().getStrYear() + "年"
                           + ankenModel.getNanDate().getMonth() + "月"
                           + ankenModel.getNanDate().getIntDay() + "日");
        paramMdl.setNtp061TourokuName(ankenModel.getNtp061TourokuName().getLabel());
        paramMdl.setNtp061TourokuUsrUkoFlg(
                ankenModel.getNtp061TourokuName().getUsrUkoFlg());

        paramMdl.setNtp061MitumoriYear(ankenModel.getNanMitumoriDate().getStrYear());
        paramMdl.setNtp061MitumoriMonth(
                Integer.toString(ankenModel.getNanMitumoriDate().getMonth()));
        paramMdl.setNtp061MitumoriDay(
                Integer.toString(ankenModel.getNanMitumoriDate().getIntDay()));
        paramMdl.setNtp061JutyuYear(ankenModel.getNanJutyuDate().getStrYear());
        paramMdl.setNtp061JutyuMonth(
                Integer.toString(ankenModel.getNanJutyuDate().getMonth()));
        paramMdl.setNtp061JutyuDay(
                Integer.toString(ankenModel.getNanJutyuDate().getIntDay()));
        paramMdl.setNtp061NcnSid(ankenModel.getNcnSid());
        paramMdl.setNtp061NanCode(ankenModel.getNanCode());
        paramMdl.setNtp061NanName(ankenModel.getNanName());
        paramMdl.setNtp061NanSyosai(ankenModel.getNanDetial());
        paramMdl.setNtp061AcoCode(ankenModel.getNtp061CompanyCode());
        paramMdl.setNtp061CompanyName(ankenModel.getNtp061CompanyName());
        paramMdl.setNtp061CompanyBaseName(ankenModel.getNtp061BaseName());
        paramMdl.setNtp061CompanySid(Integer.toString(ankenModel.getAcoSid()));
        paramMdl.setNtp061CompanyBaseSid(Integer.toString(ankenModel.getAbaSid()));
        paramMdl.setNtp061NgySid(ankenModel.getNtp061NgySid());
        paramMdl.setNtp061NgpSid(ankenModel.getNgpSid());
        paramMdl.setNtp061NanMikomi(ankenModel.getNanMikomi());
        paramMdl.setNtp061NanKinMitumori(StringUtil.toCommaFromString(
                              Integer.toString(ankenModel.getNanKinMitumori())));
        paramMdl.setNtp061NanKinJutyu(StringUtil.toCommaFromString(
                              Integer.toString(ankenModel.getNanKinJutyu())));
        paramMdl.setNtp061NanSyodan(ankenModel.getNanSyodan());
        paramMdl.setNtp061NanState(ankenModel.getNanState());
        paramMdl.setNtp061NcnSid(ankenModel.getNcnSid());
        paramMdl.setNtp061NanPermitView(ankenModel.getNanPermitView());
        paramMdl.setNtp061NanPermitEdit(ankenModel.getNanPermitEdit());

        NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con__);
        paramMdl.setNtp061ChkShohinSidList(anShohinDao.select(paramMdl.getNtp060NanSid()));
        paramMdl.setNtp061ItmChkShohinSidList(paramMdl.getNtp061ChkShohinSidList());
        NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con__);
        paramMdl.setSv_users(anMemberDao.select(paramMdl.getNtp060NanSid()));
        __loadUserGroupSelected(paramMdl.getNtp060NanSid(), paramMdl);
    }
}
