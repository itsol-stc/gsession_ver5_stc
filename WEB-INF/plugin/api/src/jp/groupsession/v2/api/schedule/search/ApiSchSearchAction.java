package jp.groupsession.v2.api.schedule.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.sch.sch100.ScheduleListSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール検索WEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchSearchAction extends AbstractApiSchAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSchSearchAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        ApiSchSearchForm asForm = (ApiSchSearchForm) form;
        //入力チェック
        ActionErrors errors = asForm.validateSchSearch(con, reqMdl, gsMsg);
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }

        //スケジュール検索条件モデルを作成
        ScheduleListSearchModel searchModel = __createScheduleListSearchModel(umodel, asForm);

        if (searchModel.getSchSltUserSid() != -1
                && NullDefault.getInt(asForm.getGrpShowKbn(), 0) == 1) {
            searchModel.setSchSltUserBelongGrpFlg(true);
        }

        //スケジュール情報を取得
        ScheduleSearchDao schSearchDao = new ScheduleSearchDao(con);

        //カウント
        SqlBuffer sqlCount =
                schSearchDao.makeCountSelectSql(searchModel, umodel.getUsrsid(), false, true);
        int count = schSearchDao.getScheduleCount(sqlCount);
        //開始位置 + 取得件数
        if (count < (searchModel.getSchLimit() + searchModel.getSchOffset() - 1)) {
            searchModel.setSchLimit(count);
        }
        log__.debug("スケジュールカウント = " + count);

        //開始位置
        int start = __getStartPosition(asForm);

        int sessionUsrSid = umodel.getUsrsid();

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, sessionUsrSid);

        CommonBiz cmnBiz = new CommonBiz();
        //施設予約の管理者
        boolean rsvAdmin = cmnBiz.isPluginAdmin(con, umodel, GSConstSchedule.PLUGIN_ID_RESERVE);

        //管理者設定を取得
        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = schCmnBiz.getAdmConfModel(con);

        ApiSchSearchBiz searchBiz = new ApiSchSearchBiz(con, reqMdl);
        SqlBuffer sqlSchedule =
                schSearchDao.makeCountSelectSql(searchModel, sessionUsrSid, true, false);

        ArrayList<SchDataModel> schDataList = schSearchDao.getScheduleList(sqlSchedule, true, true);

        log__.debug("DAO取得件数 = " + schDataList.size());
        boolean sameInputFlg = (NullDefault.getInt(asForm.getSameInputFlg(), 0) == 1);

        //XMLデータ作成
        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Attribute atCount = new Attribute("TotalCount", Integer.toString(count));
        resultSet.setAttribute(atCount);
        Attribute atStart = new Attribute("Start", Integer.toString(start));
        resultSet.setAttribute(atStart);
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUserList
            = schDao.getNotAccessUserList(sessionUsrSid);
        List<Integer> notRegistUserList
            = schDao.getNotRegistUserList(sessionUsrSid);

        List<ApiSchSearchModel> dspMdlList = new ArrayList<ApiSchSearchModel>();
        List<Integer> userSidList       = new ArrayList<Integer>();
        List<Integer> groupSidList      = new ArrayList<Integer>();
        List<Integer> schduleSidList    = new ArrayList<Integer>();
        List<Integer> sameScdGrpSidList = new ArrayList<Integer>();
        Map<Integer, List<Integer>> removeUsrsMap = new HashMap<Integer, List<Integer>>();

        //指定公開スケジュールのスケジュールSIDを取得する
        List<Integer> checkScdSidList =
                schDataList.stream()
                .map(s -> s.getScdSid())
                .collect(Collectors.toList());
        SchDataPubDao schPubDao = new SchDataPubDao(con);
        List<Integer> pubScdSidList
            = schPubDao.getUserPubScdSidList(sessionUsrSid, checkScdSidList);
        
        for (SchDataModel scData : schDataList) {
            Integer scdSid = Integer.valueOf(scData.getScdSid());

            ApiSchSearchModel dspMdl = searchBiz.getDspScheduleMdl(scData,
                    pubScdSidList,
                    adminConf,
                    (NullDefault.getInt(asForm.getEscapeFlg(), 1) == 1));
            if (dspMdl == null) {
                continue;
            }

            // 登録ユーザ
            Integer scdAuid = Integer.valueOf(scData.getScdAuid());
            if (!userSidList.contains(scdAuid)) {
                userSidList.add(scdAuid);
            }
            Integer scdUsrSid = Integer.valueOf(scData.getScdUsrSid());
            if (scData.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                // 対象ユーザ
                if (!userSidList.contains(scdUsrSid)) {
                    userSidList.add(scdUsrSid);
                }
            } else {
                // 対象グループ
                if (!groupSidList.contains(scdUsrSid)) {
                    groupSidList.add(scdUsrSid);
                }
            }

            if (!dspMdl.isSeacret()) {
                // スケジュール一覧取得(施設予約、会社・担当者、編集権限の取得の為)
                schduleSidList.add(scdSid);

                if (sameInputFlg && scData.getScdGrpSid() >= 0) {
                    // 同時登録
                    Integer scdGrpSid = Integer.valueOf(scData.getScdGrpSid());
                    sameScdGrpSidList.add(scdGrpSid);
                    List<Integer> removeUsrsList = new ArrayList<Integer>(notAccessUserList);
                    removeUsrsList.add(scdUsrSid);
                    removeUsrsMap.put(scdGrpSid, removeUsrsList);
                }
            }
            dspMdlList.add(dspMdl);
        }

        // --------------------------------------------------------
        //   データ一括取得
        // --------------------------------------------------------
        //登録ユーザ or 対象ユーザ(ユーザ)
        Map<Integer, CmnUsrmInfModel> userMap = new HashMap<Integer, CmnUsrmInfModel>();
        if (userSidList.size() > 0) {
            String[] userIds = new String[userSidList.size()];
            for (int i = 0; i < userSidList.size(); i++) {
                userIds[i] = String.valueOf(userSidList.get(i));
            }
            CmnUsrmInfDao uDao = new CmnUsrmInfDao(con);
            List<CmnUsrmInfModel> uMdlList = uDao.getUsersDataList(userIds);
            if (uMdlList != null) {
                for (CmnUsrmInfModel uMdl : uMdlList) {
                    userMap.put(uMdl.getUsrSid(), uMdl);
                }
            }
        }

        //対象グループ
        Map<Integer, String> groupMap = new HashMap<Integer, String>();
        if (groupSidList.size() > 0) {
            int[] groupIds = new int[groupSidList.size()];
            for (int i = 0; i < groupSidList.size(); i++) {
                groupIds[i] = groupSidList.get(i);
            }
            GroupDao gDao = new GroupDao(con);
            List<CmnGroupmModel> gMdlList = gDao.getGroups(groupIds);
            if (gMdlList != null) {
                for (CmnGroupmModel gMdl : gMdlList) {
                    groupMap.put(gMdl.getGrpSid(), gMdl.getGrpName());
                }
            }
        }

        //添付ファイル情報
        SchBinDao schBinDao = new SchBinDao(con);
        Map<Integer, List<CmnBinfModel>> binMap = schBinDao.getBinInfoMap(schduleSidList);

        // 会社・担当者(アドレス)
        Map<Integer, Map<String, Sch040CompanyModel>> scdCompanysMap = null;
        if (pconfig.getPlugin("address") != null) {
            scdCompanysMap = searchBiz.getCompanyMapList(schduleSidList, sessionUsrSid);
        }

        // 施設予約
        Map<Integer, List<RsvSisDataModel>> reserveListMap = null;
        if (pconfig.getPlugin("reserve") != null && sameInputFlg) {
            reserveListMap
                = searchBiz.getSelectResList(con, schduleSidList, sessionUsrSid, rsvAdmin);
        }

        // 同時登録スケジュール
        Map<Integer, ArrayList<SchDataModel>> sameInputScheduleMap =
                schSearchDao.getSameScheduleMap(sameScdGrpSidList, removeUsrsMap);

        //編集権限(権限のあるスケジュールSID一覧取得) → 共通プログラムの為、一括取得できない
        Sch010Biz sch010Biz = new Sch010Biz(reqMdl);
        List<Integer> ableEditList = new ArrayList<Integer>();
        for (Integer scdSid : schduleSidList) {
            boolean ableEdit = sch010Biz.isEditOk(scdSid.intValue(), reqMdl, con, false);
            if (ableEdit) {
                ableEditList.add(scdSid);
            }
        }
        // --------------------------------------------------------

        // スケジュール取得結果を応答データへ
        for (ApiSchSearchModel dspMdl : dspMdlList) {
            Integer scdSid    = Integer.valueOf(dspMdl.getScdSid());
            Integer scdGrpSid = Integer.valueOf(dspMdl.getScdGrpSid());

            //登録者
            CmnUsrmInfModel uMdl = userMap.get(dspMdl.getScdAuid());
            if (uMdl != null) {
                dspMdl.setScdAuidSei(uMdl.getUsiSei());
                dspMdl.setScdAuidMei(uMdl.getUsiMei());
                dspMdl.setScdAuidJkbn(uMdl.getUsrJkbn());
                dspMdl.setRegistUsrUkoFlg(uMdl.getUsrUkoFlg());
            }
            //対象ユーザ
            if (dspMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                uMdl = userMap.get(dspMdl.getScdUsrSid());
                if (uMdl != null) {
                    dspMdl.setScdUsrSei(uMdl.getUsiSei());
                    dspMdl.setScdUsrMei(uMdl.getUsiMei());
                    dspMdl.setScdUsrJkbn(uMdl.getUsrJkbn());
                    dspMdl.setScdUsrUkoFlg(uMdl.getUsrUkoFlg());
                }
            } else {
                dspMdl.setScdUsrSei(groupMap.get(dspMdl.getScdUsrSid()));
                dspMdl.setScdUsrMei("");
            }


            //関連アドレス情報
            Map<String, Sch040CompanyModel> scdCompanyMap = null;
            if (scdCompanysMap != null && scdCompanysMap.containsKey(scdSid)) {
                scdCompanyMap = scdCompanysMap.get(scdSid);
            } else {
                scdCompanyMap = new HashMap<String, Sch040CompanyModel>();
            }

            //施設予約リスト
            List<RsvSisDataModel> selectResList = null;
            if (reserveListMap != null && reserveListMap.containsKey(scdSid)) {
                selectResList = reserveListMap.get(scdSid);
            } else {
                selectResList = new ArrayList<RsvSisDataModel>();
            }

            //同時登録スケジュールリスト
            List<SchDataModel> sameInputSchedules = null;
            if (sameInputScheduleMap != null && sameInputScheduleMap.containsKey(scdGrpSid)) {
                sameInputSchedules = sameInputScheduleMap.get(scdGrpSid);
            } else {
                sameInputSchedules = new ArrayList<SchDataModel>();
            }

            //編集権限
            boolean ableEdit = (ableEditList != null && ableEditList.contains(scdSid));

            Element result = __createSchElement(dspMdl,
                    binMap,
                    scdCompanyMap,
                    selectResList,
                    sameInputSchedules,
                    notRegistUserList,
                    ableEdit,
                    reqMdl);
            resultSet.addContent(result);
        }


        return doc;
    }
    /**
     *
     * <br>[機  能] エレメント生成
     * <br>[解  説]
     * <br>[備  考]
     * @param scData スケジュールモデル
     * @param binMap スケジュール添付ファイル情報
     * @param scdCompanyMap 関連アドレス情報 アドレス帳使用不可時はnullをセット
     * @param selectResList 施設予約リスト 施設予約使用不可時はnullをセット
     * @param sameInputSchedules 同時登録スケジュールリスト
     * @param notEditUserSids 同時登録ユーザで編集権限がないユーザSID一覧
     * @param ableEdit 編集権限
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     * @return XMLエレメント
     */
    private Element __createSchElement(ApiSchSearchModel scData,
            Map<Integer, List<CmnBinfModel>> binMap,
            Map<String, Sch040CompanyModel> scdCompanyMap,
            List<RsvSisDataModel> selectResList,
            List<SchDataModel> sameInputSchedules,
            List<Integer> notEditUserSids,
            boolean ableEdit,
            RequestModel reqMdl
            ) throws SQLException {
        //Result
        Element result = new Element("Result");

        //Schsid スケジュールSID
        result.addContent(_createElement("Schsid", scData.getScdSid()));
        //SchKf スケジュール公開フラグ
        result.addContent(_createElement("SchKf", scData.getScdPublic()));
        //SchKf スケジュール編集区分
        result.addContent(_createElement("SchEf", scData.getScdEdit()));

        //Title
        result.addContent(_createElement("Title", scData.getScdTitle()));
        //Naiyo
        result.addContent(_createElement("Naiyo", scData.getScdValue()));
        //Biko
        result.addContent(_createElement("Biko", scData.getScdBiko()));
        //StartDateTime
        Element startDateTime = new Element("StartDateTime");
        startDateTime.addContent(ApiDataTypeUtil.getDateTime(scData.getScdFrDate()));
        result.addContent(startDateTime);
        //EndDateTime
        Element endDateTime = new Element("EndDateTime");
        endDateTime.addContent(ApiDataTypeUtil.getDateTime(scData.getScdToDate()));
        result.addContent(endDateTime);
        //TimeKbn
        result.addContent(_createElement("TimeKbn", scData.getScdDaily()));
        //UserKbn
        result.addContent(_createElement("UserKbn", scData.getScdUsrKbn()));
        //UserSid
        result.addContent(_createElement("UserSid", scData.getScdUsrSid()));
        //UserName
        result.addContent(_createElement("UserName",
                scData.getScdUsrSei() + " " + scData.getScdUsrMei()));
        //UserUkoFlg
        result.addContent(_createElement("UserUkoFlg", scData.getScdUsrUkoFlg()));
        //ColorKbn
        result.addContent(_createElement("ColorKbn", scData.getScdBgcolor()));

        if (!scData.isSeacret()) {
            //AttendKbn
            result.addContent(_createElement("AttendKbn", scData.getScdAttendKbn()));
            //AttendAns
            result.addContent(_createElement("AttendAns", scData.getScdAttendAns()));
            //AttendAuKbn
            result.addContent(_createElement("AttendAuKbn", scData.getScdAttendAuKbn()));
        }
        //TargetGrp
        result.addContent(_createElement("TargetGrp", scData.getScdTargetGrp()));
        //Reminder
        result.addContent(_createElement("Reminder", scData.getScdReminder()));

        if (ableEdit) {
            result.addContent(_createElement("AbleEdit", "1"));
        } else {
            result.addContent(_createElement("AbleEdit", "0"));
        }


        //登録者情報
        if (!scData.isSeacret()) {
            result.addContent(_createElement("AddUserName",
                    scData.getScdAuidSei() + " " + scData.getScdAuidMei()));
            result.addContent(_createElement("AddUserJkbn", scData.getScdAuidJkbn()));
            result.addContent(_createElement("AddUserUkoFlg", scData.getRegistUsrUkoFlg()));
            result.addContent(_createElement("ADateTime",
                    ApiDataTypeUtil.getDateTime(scData.getScdAdate())));
            result.addContent(_createElement("EDateTime",
                    ApiDataTypeUtil.getDateTime(scData.getScdEdate())));
        }

        //同時登録ユーザ
        if (sameInputSchedules != null) {
            Element sameScheduleUserSet = new Element("SameScheduleUserSet");
            result.addContent(sameScheduleUserSet);
            int sameInputScheduleCount = 0;
            if (sameInputSchedules.size() > 0) {
                for (SchDataModel sameScData : sameInputSchedules) {
                    if (sameScData.getScdSid() == scData.getScdSid()) {
                        continue;
                    }

                    int userSid  = sameScData.getScdUsrSid();
                    int editFlag = (notEditUserSids.contains(userSid) ? 0 : 1);

                    Element user = new Element("User");
                    sameScheduleUserSet.addContent(user);
                    user.addContent(_createElement("Name", sameScData.getScdUserName()));
                    user.addContent(_createElement("UsrSid", userSid));
                    user.addContent(_createElement("UsrUkoFlg", sameScData.getScdUserUkoFlg()));
                    user.addContent(_createElement("isEdit", editFlag));
                    user.addContent(_createElement("AttendAns", sameScData.getScdAttendAns()));
                    user.addContent(_createElement("AttendAuKbn", sameScData.getScdAttendAuKbn()));
                    if (sameScData.getScdAttendKbn() == GSConstSchedule.ATTEND_KBN_YES
                     && sameScData.getScdAttendAns() != GSConstSchedule.ATTEND_ANS_NONE) {
                        user.addContent(_createElement("AttendAnsDateTime",
                                ApiDataTypeUtil.getDateTime(scData.getScdEdate())));
                    } else {
                        user.addContent(_createElement("AttendAnsDateTime",
                                "-"));
                    }
                    user.addContent(_createElement("TargetGrp", sameScData.getScdTargetGrp()));
                    user.addContent(_createElement("Reminder", sameScData.getScdReminder()));
                    sameInputScheduleCount++;
                }
            }
            sameScheduleUserSet.setAttribute("Count",
                    String.valueOf(sameInputScheduleCount));
        }

        // 添付ファイル一覧
        Element tmpFileSet = new Element("TmpFileSet");
        result.addContent(tmpFileSet);
        Integer tmpFileCnt = 0;
        if (binMap.containsKey(scData.getScdSid())) {
            List<CmnBinfModel>  binList = binMap.get(scData.getScdSid());
            for (CmnBinfModel mdl : binList) {
                Element binEle = new Element("tmpFile");
                binEle.addContent(_createElement("binSid",  mdl.getBinSid()));
                binEle.addContent(_createElement("fileName", mdl.getBinFileName()));
                binEle.addContent(_createElement("fileSize", mdl.getBinFileSize()));
                binEle.addContent(_createElement("filePath", mdl.getBinFilePath()));
                tmpFileSet.addContent(binEle);
            }
            tmpFileCnt = binList.size();
        }
        tmpFileSet.setAttribute("Count", Integer.toString(tmpFileCnt));

        //施設予約使用有無
        if (selectResList != null) {
            Element reserveSet = new Element("ReserveSet");
            result.addContent(reserveSet);
            reserveSet.setAttribute("Count", String.valueOf(selectResList.size()));
            for (RsvSisDataModel rsvSisDataModel : selectResList) {
                Element reserve = new Element("Reserve");
                reserveSet.addContent(reserve);

                reserve.addContent(_createElement("RsdSid", rsvSisDataModel.getRsdSid()));
                reserve.addContent(_createElement("Name", rsvSisDataModel.getRsdName()));

            }
        }
        //アドレス帳使用有無
        if (scdCompanyMap != null) {
            Element companySet = new Element("CompanySet");
            result.addContent(companySet);
            Set<String> comKeys = scdCompanyMap.keySet();
            int comKeyCount = 0;
            if (comKeys != null) {
                for (String key : comKeys) {
                    Element company = new Element("Company");
                    companySet.addContent(company);

                    Sch040CompanyModel comModel = scdCompanyMap.get(key);

                    company.addContent(_createElement("AcoSid", comModel.getCompanySid()));
                    company.addContent(_createElement("AbaSid", comModel.getCompanyBaseSid()));
                    company.addContent(_createElement("Name", comModel.getCompanyName()));

                    List<Sch040AddressModel> adressList = comModel.getAddressDataList();
                    Element adressSet = new Element("AdressSet");
                    company.addContent(adressSet);
                    int adressCount = 0;
                    if (adressList != null) {
                        for (Sch040AddressModel sch040AddressModel : adressList) {
                            Element adress = new Element("Adress");
                            adressSet.addContent(adress);

                            adress.addContent(_createElement("AdrSid",
                                    sch040AddressModel.getAdrSid()));
                            adress.addContent(_createElement("Name",
                                    sch040AddressModel.getAdrName()));
                            adressCount++;
                        }
                    }
                    adressSet.setAttribute("Count", String.valueOf(adressCount));
                    comKeyCount++;
                }
            }
            companySet.setAttribute("Count", String.valueOf(comKeyCount));
        }
        return result;
    }



    /**
     * フォーム情報から検索モデルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param umodel ユーザ基本情報
     * @param form フォーム
     * @return ScheduleListSearchModel 検索条件モデル
     */
    private ScheduleListSearchModel __createScheduleListSearchModel(
            BaseUserModel umodel, ApiSchSearchForm form) {

        ScheduleListSearchModel searchMdl = new ScheduleListSearchModel();
        //ユーザ(検索を実行する本人)
        int sessionUsrSid = umodel.getUsrsid();
        searchMdl.setSchUsrSid(sessionUsrSid);
        searchMdl.setSessionUserSid(sessionUsrSid);

        //検索target grop or person
        String target = form.getTarget();
        int userKbn = GSConstSchedule.USER_KBN_USER;
        if (target != null && target.toLowerCase().equals("group")) {
            userKbn = GSConstSchedule.USER_KBN_GROUP;
        }
        searchMdl.setSchUsrKbn(userKbn);

        //公開区分
        //タイトル・内容についての検索条件がある場合は公開スケジュールを対象に検索
        if (StringUtil.isNullZeroString(form.getKeyWord())) {
            searchMdl.setSchPublic(-1);
        } else {
            searchMdl.setSchPublic(GSConstSchedule.DSP_PUBLIC);
        }
        searchMdl.setSchOrder(0);

        //ソート1 デフォルト開始日
        int sortKey1 = NullDefault.getInt(form.getSort1(), GSConstSchedule.SORT_KEY_FRDATE);
        searchMdl.setSchSortKey(sortKey1);
        //オーダーキー1
        int order1 = NullDefault.getInt(form.getSort1(), GSConstSchedule.ORDER_KEY_ASC);
        searchMdl.setSchOrder(order1);
        //ソート2 デフォルト開始日
        int sortKey2 = NullDefault.getInt(form.getSort2(), GSConstSchedule.SORT_KEY_TODATE);
        searchMdl.setSchSortKey2(sortKey2);
        //オーダーキー2
        int order2 = NullDefault.getInt(form.getSort2(), GSConstSchedule.ORDER_KEY_ASC);
        searchMdl.setSchOrder2(order2);

        //取得開始位置
        searchMdl.setSchOffset(__getStartPosition(form));
        //取得件数
        int results = __getResults(form);
        searchMdl.setSchLimit(results);

        //グループSID
        int sltGsid = NullDefault.getInt(form.getGsid(), -1);
        searchMdl.setSchSltGroupSid(Integer.toString(sltGsid));
        searchMdl.setMyGrpFlg(
                SchCommonBiz.isMyGroupSid(NullDefault.getString(form.getGsid(), "")));
        //ユーザSID
        if (userKbn == GSConstSchedule.USER_KBN_USER) {
            int sltUsid = NullDefault.getInt(form.getUsid(), sessionUsrSid);
            if (sltUsid == 0) {
                sltUsid = umodel.getUsrsid();
            }

            searchMdl.setSchSltUserSid(sltUsid);
        } else {
            searchMdl.setSchSltUserSid(-2);
        }

        //開始日From
        String strStartFrom = form.getStartFrom();
        UDate date1 = null;
        if (StringUtil.isNullZeroString(strStartFrom)) {
            //未入力の場合現在日
            date1 = new UDate();
            date1.resetTime();
        } else {
            date1 = __createUDate(strStartFrom);
        }
        //開始日To
        String strStartTo = form.getStartTo();
        UDate date2 = null;
        if (StringUtil.isNullZeroString(strStartTo)) {
            //未入力の場合現在日 + 1年
            date2 = new UDate();
            date2.resetTime();
            date2.addYear(1);
        } else {
            date2 = __createUDate(strStartTo);
        }
        //終了日From
        String strEndFrom = form.getEndFrom();
        UDate date3 = null;
        if (StringUtil.isNullZeroString(strEndFrom)) {
            //未入力の場合現在日
            date3 = new UDate();
            date3.resetTime();
        } else {
            date3 = __createUDate(strEndFrom);
        }
        //終了日To
        String strEndTo = form.getEndTo();
        UDate date4 = null;
        if (StringUtil.isNullZeroString(strEndTo)) {
            //未入力の場合現在日 + 1年
            date4 = new UDate();
            date4.resetTime();
            date4.addYear(1);
        } else {
            date4 = __createUDate(strEndTo);
        }
        searchMdl.setSchStartDateFr(date1);
        searchMdl.setSchStartDateTo(date2);
        searchMdl.setSchEndDateFr(date3);
        searchMdl.setSchEndDateTo(date4);

        //キーワード区分
        String strKeyWordKbn = NullDefault.getStringZeroLength(form.getKeyWordKbn(), "0");
        if (strKeyWordKbn.equals("0")) {
            //and
            searchMdl.setKeyWordkbn(0);
        } else {
            //or
            searchMdl.setKeyWordkbn(1);
        }

        //キーワード
        String keyWord = NullDefault.getString(form.getKeyWord(), "");
        CommonBiz cBiz = new CommonBiz();
        searchMdl.setSchKeyValue(cBiz.setKeyword(keyWord));

        //検索対象 タイトル
        String strTargetTitle = NullDefault.getStringZeroLength(form.getKeytitle(), "0");
        if (strTargetTitle.equals("0")) {
            searchMdl.setTargetTitle(true);
        }
        //検索対象 本文
        String strTargetBody = NullDefault.getStringZeroLength(form.getKeybody(), "0");
        if (strTargetBody.equals("0")) {
            searchMdl.setTargetValue(true);
        }

        return searchMdl;
    }

    /**
     * <br>[機  能] 取得開始位置を取得する。
     * <br>[解  説]
     * <br>[備  考] 未入力の場合、0以下の場合は1をセットする。
     * @param form フォーム
     * @return 取得開始位置
     */
    private int __getStartPosition(ApiSchSearchForm form) {
        int position = NullDefault.getInt(form.getStart(), 1);
        if (position < 1) {
            position = 1;
        }
        return position;
    }

    /**
     * <br>[機  能] 表示件数を取得する。
     * <br>[解  説]
     * <br>[備  考] 未入力の場合、0以下の場合は50をセットする。
     * @param form フォーム
     * @return 表示件数
     */
    private int __getResults(ApiSchSearchForm form) {
        int results = NullDefault.getInt(form.getResults(), 50);
        if (results < 1) {
            results = 1;
        }
        return results;
    }

    /**
     * <br>[機  能] 指定された日時文字列(yyyy/mm/dd hh:mm)からUDateを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付文字列
     * @return UDate
     */
    private UDate __createUDate(String date) {
        UDate dateTime = UDate.getInstanceStr(date.substring(0, 10));
//        dateTime.setZeroHhMmSs();
//        dateTime.setHour(Integer.parseInt(date.substring(11, 13)));
//        dateTime.setMinute(Integer.parseInt(date.substring(14)));

        return dateTime;
    }


}