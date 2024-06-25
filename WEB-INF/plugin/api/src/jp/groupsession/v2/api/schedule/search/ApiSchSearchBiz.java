package jp.groupsession.v2.api.schedule.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchCompanyModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] スケジュール検索API ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchSearchBiz {

    /** コネクション */
    public Connection con__ = null;
    /** リクエスト */
    public RequestModel reqMdl__ = null;

    /** キャッシュ セッションユーザ所属グループ*/
    List<Integer> belongGpSidList__ = new ArrayList<Integer>();
    /** 予定有り*/
    private String textYoteiari__;
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行時例外
     */
    public ApiSchSearchBiz(Connection con, RequestModel reqMdl) throws SQLException {
        con__ = con;
        reqMdl__ = reqMdl;
        //セッションユーザの所属グループを格納
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        belongGpSidList__ = bdao.selectUserBelongGroupSid(reqMdl.getSmodel().getUsrsid());
        GsMessage gsMsg = new GsMessage(reqMdl);
        //予定あり
        textYoteiari__ = gsMsg.getMessage("schedule.src.9");

    }
    /**
     *
     * <br>[機  能] 表示用スケジュールモデル作成
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールモデル
     * @param pubScdSidList 公開対象に指定されているスケジュールのSID一覧
     * @param adminConf 管理者権限
     * @param escapeFlg htmlエスケープフラグ
     * @return 表示用スケジュールモデル
     * @throws Exception
     */
    public ApiSchSearchModel getDspScheduleMdl(SchDataModel schMdl,
            List<Integer> pubScdSidList,
            SchAdmConfModel adminConf,
            boolean escapeFlg
            ) throws Exception {
        ApiSchSearchModel dspSchMdl = null;
        int usrSid = schMdl.getScdUsrSid();
        int usrKbn = schMdl.getScdUsrKbn();
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        dspSchMdl = new ApiSchSearchModel();
        //表示グループに所属しているか判定
        boolean grpBelongHnt = isGrpBelongHnt(schMdl,
                sessionUsrSid,
                belongGpSidList__);

        dspSchMdl.setScdSid(schMdl.getScdSid());
        dspSchMdl.setScdUsrSid(usrSid);
        dspSchMdl.setScdGrpSid(schMdl.getScdGrpSid());
        dspSchMdl.setScdUsrKbn(usrKbn);
        dspSchMdl.setScdFrDate(schMdl.getScdFrDate());
        dspSchMdl.setScdToDate(schMdl.getScdToDate());
        dspSchMdl.setScdDaily(schMdl.getScdDaily());
        dspSchMdl.setScdBgcolor(schMdl.getScdBgcolor());
        dspSchMdl.setScdTitle(schMdl.getScdTitle());
        int scdPub = schMdl.getScdPublic();
        if (scdPub == GSConstSchedule.DSP_TITLE
                || scdPub == GSConstSchedule.DSP_USRGRP) {
            scdPub = GSConstSchedule.DSP_YOTEIARI;
        }
        dspSchMdl.setScdPublic(scdPub);

        if (escapeFlg) {
            dspSchMdl.setScdValue(
                    StringUtilHtml.transToHTmlPlusAmparsant(schMdl.getScdValue()));
            dspSchMdl.setScdBiko(
                    StringUtilHtml.transToHTmlPlusAmparsant(schMdl.getScdBiko()));
        } else {
            dspSchMdl.setScdValue(schMdl.getScdValue());
            dspSchMdl.setScdBiko(schMdl.getScdBiko());
        }

        dspSchMdl.setSeacret(false);
        if (usrKbn == GSConstSchedule.USER_KBN_USER
                && usrSid == sessionUsrSid) {
            //本人
        } else if (usrKbn == GSConstSchedule.USER_KBN_USER
                && usrSid != sessionUsrSid) {

            SchDataPubDao sdpDao = new SchDataPubDao(con__);
            //他ユーザ
            if (schMdl.getScdAuid() == sessionUsrSid
             || schMdl.getScdEuid() == sessionUsrSid) {
                //登録者の場合は表示する
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI
                   || schMdl.getScdPublic() == GSConstSchedule.DSP_TITLE
                   || (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                   && !grpBelongHnt)
                   || (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP
                   && !sdpDao.select(schMdl.getScdSid(), sessionUsrSid))) {
                //予定あり
                dspSchMdl.setScdTitle(textYoteiari__);
                dspSchMdl.setScdValue("");
                dspSchMdl.setScdBiko("");
                dspSchMdl.setScdBgcolor(GSConstSchedule.BGCOLOR_BLACK);
                dspSchMdl.setSeacret(true);
            }

            if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {

                if (schMdl.getScdAuid() == sessionUsrSid
                        || schMdl.getScdEuid() == sessionUsrSid) {
                    //登録者の場合は表示する
                } else {
                    //非公開
                    dspSchMdl.setScdPublic(schMdl.getScdPublic());
                    return null;
                }
            }
        } else {
            //グループのスケジュール
            if (schMdl.getScdAuid() == sessionUsrSid
             || schMdl.getScdEuid() == sessionUsrSid) {
                //登録者の場合は表示する
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                    && !(grpBelongHnt)) {
                //非公開
                return null;

            }

        }
        if (usrKbn == GSConstSchedule.USER_KBN_USER) {
            dspSchMdl.setScdReminder(schMdl.getScdReminder());
            dspSchMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_NO);
        } else {
            dspSchMdl.setScdTargetGrp(schMdl.getScdTargetGrp());
            dspSchMdl.setScdReminder(schMdl.getScdReminder());
        }
        dspSchMdl.setScdAttendKbn(schMdl.getScdAttendKbn());
        dspSchMdl.setScdAttendAns(schMdl.getScdAttendAns());
        dspSchMdl.setScdAttendAuKbn(schMdl.getScdAttendAuKbn());
        dspSchMdl.setScdAuid(schMdl.getScdAuid());
        dspSchMdl.setScdAdate(schMdl.getScdAdate());
        dspSchMdl.setScdEuid(schMdl.getScdEuid());
        dspSchMdl.setScdEdate(schMdl.getScdEdate());
        dspSchMdl.setSceSid(schMdl.getSceSid());
        dspSchMdl.setScdRsSid(schMdl.getScdRsSid());
        dspSchMdl.setScdEdit(schMdl.getScdEdit());

        return dspSchMdl;
    }

    /**
    *
    * <br>[機  能]  関連会社情報、アドレス帳情報を取得
    * <br>[解  説]
    * <br>[備  考]
    * @param scdSid スケジュールSID
    * @param usrSid ユーザSID
    * @throws SQLException SQL実行例外
    * @return 会社情報Map
    */
   public Map<String, Sch040CompanyModel> getCompanyMap(
           int scdSid,
           int usrSid
           ) throws SQLException {
       Integer key = Integer.valueOf(scdSid);
       List<Integer> scdSids = new ArrayList<Integer>();
       scdSids.add(key);
       Map<Integer, Map<String, Sch040CompanyModel>> map = getCompanyMapList(scdSids, usrSid);
       return map.get(key);
   }

   /**
    *
    * <br>[機  能]  関連会社情報、アドレス帳情報を一括取得
    * <br>[解  説]
    * <br>[備  考]
    * @param scdSids スケジュールSID一覧
    * @param usrSid  ユーザSID
    * @throws SQLException SQL実行例外
    * @return 会社情報Map配列(キー:スケジュールSID / 値:会社情報Map)
    */
   public Map<Integer, Map<String, Sch040CompanyModel>> getCompanyMapList(
           List<Integer> scdSids,
           int usrSid
           ) throws SQLException {
       Connection con = con__;
       SchCompanyDao companyDao = new SchCompanyDao(con);
       List<SchCompanyModel> schCompanyList = companyDao.select(scdSids);
       Map<Integer, Map<String, Sch040CompanyModel>> ret
           = new HashMap<Integer, Map<String, Sch040CompanyModel>>();

       Map<String, Sch040CompanyModel> companyMap = new HashMap<String, Sch040CompanyModel>();

       Sch040CompanyModel noCompanyModel = new Sch040CompanyModel();
       GsMessage gsMsg = new GsMessage(reqMdl__);
       //会社登録無し
       String textCmpDataNone = gsMsg.getMessage("schedule.src.87");
       noCompanyModel.setCompanyName(textCmpDataNone);
       noCompanyModel.setCompanySid(0);
       noCompanyModel.setCompanyBaseSid(0);
       companyMap.put("0:0", noCompanyModel);

       List<Integer> acoSids = new ArrayList<Integer>();
       List<Integer> abaSids = new ArrayList<Integer>();

       // 必要な会社or拠点SIDを一覧へ追加
       for (SchCompanyModel company : schCompanyList) {
           Integer acoSid = Integer.valueOf(company.getAcoSid());
           Integer abaSid = Integer.valueOf(company.getAbaSid());
           if (acoSid > 0 && !acoSids.contains(acoSid)) {
               acoSids.add(acoSid);
           }
           if (abaSid > 0 && !abaSids.contains(abaSid)) {
               abaSids.add(abaSid);
           }
       }

       //アドレス情報を取得
       SchAddressDao addressDao = new SchAddressDao(con);
       List<SchAddressModel> addressList = addressDao.select(scdSids);
       Map<Integer, Sch040AddressModel> addressMap = new HashMap<Integer, Sch040AddressModel>();
       if (addressList != null && addressList.size() > 0) {
           String[] addressId = new String[addressList.size()];
           for (int index = 0; index < addressList.size(); index++) {
               addressId[index] = String.valueOf(addressList.get(index).getAdrSid());
           }
           List<Sch040AddressModel> exAddressList = __getAddressFromBaseMap(addressId, usrSid);
           if (exAddressList != null) {
               List<Integer> adrSidList = new ArrayList<Integer>();
               // アドレスに紐付く会社or拠点SIDを一覧へ追加
               for (Sch040AddressModel adrData : exAddressList) {
                   Integer acoSid = Integer.valueOf(adrData.getCompanySid());
                   Integer abaSid = Integer.valueOf(adrData.getCompanyBaseSid());
                   if (acoSid > 0 && !acoSids.contains(acoSid)) {
                       acoSids.add(acoSid);
                   }
                   if (abaSid > 0 && !abaSids.contains(abaSid)) {
                       abaSids.add(abaSid);
                   }
                   Integer adrSid = Integer.valueOf(adrData.getAdrSid());
                   addressMap.put(adrSid, adrData);
                   adrSidList.add(adrSid);
               }

               // アドレス一覧を並び替える(データ取得順)
               final List<Integer> sortList = new ArrayList<Integer>(adrSidList);
               Collections.sort(addressList, new Comparator<SchAddressModel>() {
                   public int compare(SchAddressModel o1, SchAddressModel o2) {
                       int sort1 = sortList.indexOf(Integer.valueOf(o1.getAdrSid()));
                       int sort2 = sortList.indexOf(Integer.valueOf(o2.getAdrSid()));
                       return (sort1 - sort2);
                   }
               });
           }
       }

       AdrCompanyDao     acoDao = new AdrCompanyDao(con);
       AdrCompanyBaseDao abaDao = new AdrCompanyBaseDao(con);
       Map<Integer, String> acoNameMap = acoDao.getCompanyNameMap(con, acoSids);
       Map<Integer, String> abaNameMap = abaDao.getCompanyBaseNameMap(con, abaSids);

       // スケジュールに使用される会社SID一覧から会社情報を取得
       for (SchCompanyModel company : schCompanyList) {
           Integer acoSid = Integer.valueOf(company.getAcoSid());
           Integer abaSid = Integer.valueOf(company.getAbaSid());

           if (acoSid == 0 && abaSid == 0) {
               continue; // [会社登録無し]の場合は除外
           }

           Integer scdSid = Integer.valueOf(company.getScdSid());
           String companyId = acoSid + ":" + abaSid;

           Sch040CompanyModel companyData = companyMap.get(companyId);
           if (companyData == null) {
               String acoName = acoNameMap.get(acoSid);
               if (acoName != null) {
                   companyData = new Sch040CompanyModel();

                   String companyName = acoName;
                   String abaName = abaNameMap.get(abaSid);
                   if (abaName != null) {
                       companyName += " " + abaName;
                   }

                   companyData.setCompanySid(acoSid);
                   companyData.setCompanyBaseSid(abaSid);
                   companyData.setCompanyName(companyName);
                   companyMap.put(companyId, companyData);
               }
           }

           if (companyData != null) {
               Map<String, Sch040CompanyModel> dataMap = ret.get(scdSid);
               if (dataMap == null) {
                   dataMap = new HashMap<String, Sch040CompanyModel>();
               }

               // 会社情報を複製してセット(アドレス一覧があるのでスケジュールごとにオブジェクトを分ける必要がある為)
               Sch040CompanyModel data = new Sch040CompanyModel();
               data.setCompanySid(companyData.getCompanySid());
               data.setCompanyBaseSid(companyData.getCompanyBaseSid());
               data.setCompanyName(companyData.getCompanyName());
               data.setAddressDataList(new ArrayList<Sch040AddressModel>());

               dataMap.put(companyId, data);
               ret.put(scdSid, dataMap);
           }
       }

       // スケジュールに使用されるアドレスSID一覧からアドレス情報を取得
       if (addressList != null) {
           for (SchAddressModel address : addressList) {
               Integer scdSid = Integer.valueOf(address.getScdSid());
               Sch040AddressModel adrData = addressMap.get(Integer.valueOf(address.getAdrSid()));
               if (adrData != null) {
                   String companyId = adrData.getCompanySid() + ":" + adrData.getCompanyBaseSid();

                   Map<String, Sch040CompanyModel> dataMap
                       = (ret.containsKey(scdSid)
                               ? ret.get(scdSid) : new HashMap<String, Sch040CompanyModel>());
                   Sch040CompanyModel data = dataMap.get(companyId);
                   if (data == null) {
                       Sch040CompanyModel companyData = companyMap.get(companyId);
                       if (companyData != null) {
                           // 会社情報を複製してセット(アドレス一覧があるのでスケジュールごとにオブジェクトを分ける必要がある為)
                           data = new Sch040CompanyModel();
                           data.setCompanySid(companyData.getCompanySid());
                           data.setCompanyBaseSid(companyData.getCompanyBaseSid());
                           data.setCompanyName(companyData.getCompanyName());
                           data.setAddressDataList(new ArrayList<Sch040AddressModel>());
                       }
                   }

                   if (data != null) {
                       data.getAddressDataList().add(adrData);
                       dataMap.put(companyId, data);
                       ret.put(scdSid, dataMap);
                   }
               }
           }
       }

       return ret;
   }

   /**
    *
    * <br>[機  能] アドレス情報取得
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList sid配列
    * @param usrSid セッションユーザSID
    * @return アドレス情報一覧
    * @throws SQLException SQL実行時例外
    */
   private List<Sch040AddressModel> __getAddressFromBaseMap(String[] sidList,
           int usrSid) throws SQLException {
       Sch040Dao sch040dao = new Sch040Dao(con__);

       List<Sch040AddressModel> exAddressList
       = sch040dao.getAddressList(con__, sidList, usrSid);
       return exAddressList;
   }

   /**
    * 関連済施設SIDリストを取得
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param scdSid スケジュールSID
    * @param userSid usrSid
    * @param rsvAdmin 施設予約管理者権限
    * @throws SQLException SQL実行例外
    * @return 施設リスト
    */
   public ArrayList<RsvSisDataModel> getSelectResList(
           Connection con, int scdSid, int userSid, boolean rsvAdmin) throws SQLException {
       ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
       RsvSisDataDao dataDao = new RsvSisDataDao(con);

       ArrayList<Integer> reservs = schRsvDao.getScheduleReserveData(scdSid);

       ArrayList<RsvSisDataModel> selectResList = null;

       if (reservs != null && reservs.size() > 0) {
           if (rsvAdmin) {
               //全施設
               selectResList =
                   dataDao.selectGrpSisetuList(reservs);
           } else {
               //閲覧権限のある施設
               selectResList =
                   dataDao.selectGrpSisetuCanReadList(reservs, userSid);
           }

       }
       return selectResList;
   }

   /**
    * 関連済施設SIDリストを取得
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param scdSids スケジュールSID一覧
    * @param userSid usrSid
    * @param rsvAdmin 施設予約管理者権限
    * @throws SQLException SQL実行例外
    * @return 施設リスト
    */
   public Map<Integer, List<RsvSisDataModel>> getSelectResList(
           Connection con, List<Integer> scdSids, int userSid, boolean rsvAdmin)
           throws SQLException {
       ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
       RsvSisDataDao dataDao = new RsvSisDataDao(con);

       Map<Integer, List<Integer>> schReservsMap = schRsvDao.getScheduleReserveDataMap(scdSids);

       Map<Integer, List<RsvSisDataModel>> ret = new HashMap<Integer, List<RsvSisDataModel>>();

       if (schReservsMap != null) {
           ArrayList<Integer> reservs = new ArrayList<Integer>();
           for (Integer scdSid : schReservsMap.keySet()) {
               for (Integer reservSid : schReservsMap.get(scdSid)) {
                   if (!reservs.contains(reservSid)) {
                       reservs.add(reservSid);
                   }
               }
           }

           ArrayList<RsvSisDataModel> selectResList = null;
           if (rsvAdmin) {
               //全施設
               selectResList = dataDao.selectGrpSisetuList(reservs);
           } else {
               //閲覧権限のある施設
               selectResList = dataDao.selectGrpSisetuCanReadList(reservs, userSid);
           }

           if (selectResList != null) {
               // 施設予約リスト作成(キー:施設予約SID / 値:施設予約データ)
               Map<Integer, RsvSisDataModel> reservMap = new HashMap<Integer, RsvSisDataModel>();
               List<Integer> rsvSidList = new ArrayList<Integer>();
               for (RsvSisDataModel mdl : selectResList) {
                   Integer reservSid = Integer.valueOf(mdl.getRsdSid());
                   reservMap.put(reservSid, mdl);
                   rsvSidList.add(reservSid);
               }

               // 並び順の指定(施設予約データ取得順)
               final List<Integer> sortList = new ArrayList<Integer>(rsvSidList);
               Comparator<RsvSisDataModel> comparator = new Comparator<RsvSisDataModel>() {
                   public int compare(RsvSisDataModel o1, RsvSisDataModel o2) {
                       int sort1 = sortList.indexOf(Integer.valueOf(o1.getRsdSid()));
                       int sort2 = sortList.indexOf(Integer.valueOf(o2.getRsdSid()));
                       return (sort1 - sort2);
                   }
               };

               // スケジュール毎に施設予約データ一覧を紐付け
               for (Integer scdSid : schReservsMap.keySet()) {
                   List<RsvSisDataModel> list = new ArrayList<RsvSisDataModel>();
                   List<Integer> schReservesList = schReservsMap.get(scdSid);
                   for (Integer reservSid : schReservesList) {
                       if (reservMap.containsKey(reservSid)) {
                           list.add(reservMap.get(reservSid));
                       }
                   }
                   Collections.sort(list, comparator); // 並び替え
                   ret.put(scdSid, list);
               }
           }
       }
       return ret;
   }


   /**
    * ユーザ氏名＋ユーザ無効フラグを取得する
    * <br>[機  能]ユーザ区分がグループの場合はグループ名を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param userSid ユーザSID
    * @param userKbn ユーザ区分
    * @param con コネクション
    * @return UsrLabelValueBean ユーザ情報(ユーザー名＋ユーザ無効フラグ)
    * @throws SQLException SQL実行時例外
    */
   public UsrLabelValueBean getUserNameAndUkoFlg(int userSid, int userKbn, Connection con)
   throws SQLException {

       UsrLabelValueBean ret = null;
       if (userKbn == GSConstSchedule.USER_KBN_USER) {
           CmnUsrmInfDao uDao = new CmnUsrmInfDao(con);
           CmnUsrmInfModel uMdl = uDao.selectUserNameAndJtkbn(userSid);
           if (uMdl != null) {
               ret = new UsrLabelValueBean(String.valueOf(userSid),
                       uMdl.getUsiSei() + " " + uMdl.getUsiMei(), uMdl.getUsrUkoFlg());
           }
       } else if (userKbn == GSConstSchedule.USER_KBN_GROUP) {
           GroupDao gDao = new GroupDao(con);
           CmnGroupmModel gMdl = gDao.getGroup(userSid);
           if (gMdl != null) {
               ret = new UsrLabelValueBean(String.valueOf(userSid), gMdl.getGrpName());
           }
       }

       return ret;
   }

   /**
    *
    * <br>[機  能] 表示グループに所属しているか判定する
    * <br>[解  説]
    * <br>[備  考]
    * @param schMdl スケジュールモデル
    * @param sessionUsrSid セッションユーザSID
    * @param belongGpSidList 所属グループ一覧
    * @return 表示ユーザと同じグループに所属するスケジュールならtrue
    * @throws SQLException SQL実行時例外
    */
   public boolean isGrpBelongHnt(SchDataModel schMdl,
           int sessionUsrSid,
           List<Integer> belongGpSidList
           ) throws SQLException {

       //表示グループに所属しているか判定
       GroupBiz gpBiz = new GroupBiz();

       boolean belongFlg = false;
       if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
           //ユーザスケジュールの場合は表示スケジュールユーザと同じグループに所属しているか判定
           ArrayList<Integer> belongSids = schMdl.getScdUserBlongGpList();
           if (belongSids != null && !belongSids.isEmpty()) {
               for (int gpSid : belongSids) {
                   if (belongGpSidList != null) {
                       int index = belongGpSidList.indexOf(gpSid);
                       if (index > -1) {
                           belongFlg = true;
                       }
                   }
               }
           }
       } else {
           if (belongGpSidList != null) {
               // セッションユーザの所属グループにスケジュールグループSIDがあるかチェック
               int index = belongGpSidList.indexOf(schMdl.getScdUsrSid());
               if (index > -1) {
                   belongFlg = true;
               }
           } else {
               // 所属グループ一覧がない場合(基本的に所属グループがあるはずなので、ここは実行されない)
               belongFlg = gpBiz.isBelongGroup(sessionUsrSid, schMdl.getScdUsrSid(), con__);
           }
       }
       return belongFlg;
   }
}
