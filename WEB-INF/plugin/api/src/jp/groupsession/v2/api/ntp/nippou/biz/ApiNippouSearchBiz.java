package jp.groupsession.v2.api.ntp.nippou.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.api.ntp.nippou.model.ApiNippouDataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpBinDao;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpGoodModel;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;
import jp.groupsession.v2.ntp.ntp040.Ntp040Dao;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CommentModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
/**
 * <br>[機  能] WEBAPI 日報 日報検索ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouSearchBiz {
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * コンストラクタ
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public ApiNippouSearchBiz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 日報情報を収集
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpMdlList ベースモデルリスト
     * @return reports 日報一覧
     * @throws SQLException SQL実行時例外
     */

    public List<ApiNippouDataModel> getReports(List<NtpDataModel> ntpMdlList)
            throws SQLException {

        //日報データリスト
        ArrayList<ApiNippouDataModel> dataList = new ArrayList<ApiNippouDataModel>();

        if (!ntpMdlList.isEmpty()) {

            /**管理者権限*/
            CommonBiz commonBiz = new CommonBiz();
            boolean isAdmin = commonBiz.isPluginAdmin(
                    con__, reqMdl__.getSmodel(), GSConstNippou.PLUGIN_ID_NIPPOU);
            int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
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
            for (NtpDataModel ntpMdl : ntpMdlList) {
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
                        isAddEditOk(reqMdl__.getSmodel().getUsrsid(),
                                isAdmin, ntpMdl.getUsrSid()));
            }
            UserSearchDao uDao = new UserSearchDao(con__);
            /**取得ユーザ情報Map*/
            HashMap<Integer, UserSearchModel> usrMap = new HashMap<>();
            List<UserSearchModel> usrList = uDao.getUsersInfoJtkb(new ArrayList<>(usrSidSet),
                    GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);
            for (UserSearchModel user : usrList) {
                usrMap.put(user.getUsrSid(), user);
            }

            /**案件情報Map*/
            Map<Integer, NtpAnkenModel> ankenMap =  _getAnkenMap(con__, nanSidSet);
            /**会社情報Map*/
            Map<Integer, AdrCompanyModel> acoMap =  _getAcoMap(con__, acoSidSet);
            /**会社拠点情報Map*/
            Map<Integer, AdrCompanyBaseModel> abaMap =  _getAbaMap(con__, abaSidSet);

            NtpBinDao binDao = new NtpBinDao(con__);
            /**添付情報Map*/
            Map<Integer, List<CmnBinfModel>> filMap = binDao.getFileMap(nipSidSet);

            Ntp040Dao ntpDao = new Ntp040Dao(con__);
            /**コメント情報Map*/
            Map<Integer, List<Ntp040CommentModel>> cmmMap = ntpDao.getNpcMap(
                    reqMdl__, nipSidSet, eauthMap, usrMap);

            NtpGoodDao gDao = new NtpGoodDao(con__);
            /**いいね情報Map*/
            HashMap<Integer, List<NtpGoodModel>> goodMap = gDao.getIineMap(nipSidSet);

            //データセット
            for (NtpDataModel ntpMdl : ntpMdlList) {


                if (ntpMdl == null) {
                    //編集対象が無い場合
                    return dataList;
                }

                ApiNippouDataModel dataMdl = new ApiNippouDataModel();

                dataMdl.setNipSid(ntpMdl.getNipSid());

                CmnUsrmInfModel uMdl = null;
                Integer usid = null;

                //登録者
                dataMdl.setUsrSid(ntpMdl.getUsrSid());
                usid = Integer.valueOf(ntpMdl.getUsrSid());
                if (usrMap.containsKey(usid)) {
                    dataMdl.setUsrMdl(usrMap.get(usid));
                }

                //編集者
                dataMdl.setNipEuid(ntpMdl.getNipEuid());
                usid = Integer.valueOf(ntpMdl.getNipEuid());
                if (usrMap.containsKey(usid)) {
                    uMdl = usrMap.get(usid);
                    dataMdl.setAddUsrName(uMdl.getUsiSei() + "" + uMdl.getUsiMei());
                    dataMdl.setAddUsrUkoFlg(uMdl.getUsrUkoFlg());
                }

                //登録日時
                dataMdl.setNipDate(ntpMdl.getNipDate());

                UDate frDate = ntpMdl.getNipFrTime();
                UDate toDate = ntpMdl.getNipToTime();
                //開始年月日

                dataMdl.setNipFrTime(frDate);
                dataMdl.setNipToTime(toDate);


                //活動分類

                dataMdl.setMkbSid(ntpMdl.getMkbSid());
                dataMdl.setKtBunrui(__getKtbunrui(ntpMdl.getMkbSid()));


                //活動方法
                dataMdl.setMkhSid(ntpMdl.getMkhSid());
                dataMdl.setKtHouhou(__getKthouhou(ntpMdl.getMkhSid()));


                //見込み度
                dataMdl.setNipMikomi(ntpMdl.getNipMikomi());

                //背景
                int iniBgcolor = GSConstNippou.DF_BG_COLOR;
                if (ntpMdl.getNipTitleClo() > GSConstNippou.DF_BG_COLOR) {
                    iniBgcolor = ntpMdl.getNipTitleClo();
                }
                dataMdl.setNipTitleClo(iniBgcolor);

                //タイトル
                dataMdl.setNipTitle(ntpMdl.getNipTitle());

                //詳細
                dataMdl.setNipDetail(ntpMdl.getNipDetail());

                //所感
                //案件情報取得
                NtpAnkenModel ankenModel = ankenMap.get(ntpMdl.getNanSid());
                if (ankenModel != null) {
                    dataMdl.setNanSid(ntpMdl.getNanSid());
                    dataMdl.setNanCode(ankenModel.getNanCode());
                    dataMdl.setNanName(ankenModel.getNanName());
                }
                dataMdl.setAnkenViewable(ntpMdl.isAnkenViewable());

                //会社情報
                int acoSid = ntpMdl.getAcoSid();
                int abaSid = ntpMdl.getAbaSid();

                if (acoMap.containsKey(acoSid)) {
                    AdrCompanyModel companyModel = acoMap.get(acoSid);
                    dataMdl.setAcoSid(acoSid);
                    dataMdl.setAcoCode(companyModel.getAcoCode());
                    dataMdl.setAcoName(companyModel.getAcoName());
                }

                //会社拠点情報
                if (abaMap.containsKey(abaSid)) {
                    AdrCompanyBaseModel companyBaseMdl = abaMap.get(abaSid);
                    dataMdl.setAbaSid(abaSid);
                    dataMdl.setAbaName(companyBaseMdl.getAbaName());
                }
                //添付ファイル情報取得
                if (filMap.containsKey(ntpMdl.getNipSid())) {
                    ArrayList<CmnBinfModel> retBin =
                            new ArrayList<>(filMap.get(ntpMdl.getNipSid()));
                    dataMdl.setClips(retBin);
                }

                if (ntpMdl.isAnkenViewable()) {
                    //コメント取得
                    List<Ntp040CommentModel> npcList;
                    if (cmmMap.containsKey(ntpMdl.getNipSid())) {
                        npcList = cmmMap.get(ntpMdl.getNipSid());
                    } else {
                        npcList =  new ArrayList<Ntp040CommentModel>();
                    }
                    dataMdl.setComments(npcList);
                }
                //いいね件数取得
                ArrayList<NtpGoodModel> gList;
                if (goodMap.containsKey(ntpMdl.getNipSid())) {
                    gList = gDao.select(ntpMdl.getNipSid());
                } else {
                    gList = new ArrayList<NtpGoodModel>();
                }
                dataMdl.setIineCount(gList.size());

                //セッションユーザがいいねしているか
                int goodFlg = 0;
                for (NtpGoodModel gMdl : gList) {
                    if (gMdl.getUsrSid() == sessionUsrSid) {
                        goodFlg = 1;
                    }
                }
                dataMdl.setIineFlg(goodFlg);
                dataList.add(dataMdl);

            }

        }



        return dataList;
    }

    /**
     * <br>[機  能] DBから案件情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSid 案件SID
     * @param dataMdl 日報データ
     * @throws SQLException SQL実行時例外
     */
    public void _getAnkenData(
                              int nanSid,
                              ApiNippouDataModel dataMdl) throws SQLException {

        //案件情報
        NtpAnkenDao ankenDao = new NtpAnkenDao(con__);
        NtpAnkenModel ankenModel = ankenDao.select(nanSid);

        if (ankenModel != null) {
            dataMdl.setNanSid(nanSid);
            dataMdl.setNanCode(ankenModel.getNanCode());
            dataMdl.setNanName(ankenModel.getNanName());
        }
    }

    /**
     * <br>[機  能] DBから会社情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @param abaSid 拠点SID
     * @param dataMdl 日報データ
     * @throws SQLException SQL実行時例外
     */
    public void _readCompanyData(
                                 int acoSid,
                                 int abaSid,
                                 ApiNippouDataModel dataMdl) throws SQLException {

        //会社情報
        AdrCompanyDao companyDao = new AdrCompanyDao(con__);
        AdrCompanyModel companyModel = companyDao.select(acoSid);

        if (companyModel != null) {
            dataMdl.setAcoSid(acoSid);
            dataMdl.setAcoCode(companyModel.getAcoCode());
            dataMdl.setAcoName(companyModel.getAcoName());
        }

        //会社拠点情報
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con__);
        AdrCompanyBaseModel companyBaseMdl = new AdrCompanyBaseModel();
        companyBaseMdl = companyBaseDao.select(abaSid);
        if (companyBaseMdl != null) {
            dataMdl.setAbaSid(abaSid);
            dataMdl.setAbaName(companyBaseMdl.getAbaName());
        }
    }
    /**
     * <br>[機  能] 活動分類を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ktSid 活動分類SID
     * @return 活動分類
     * @throws SQLException SQL例外
     */
    public String __getKtbunrui(int ktSid) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ktBunrui = gsMsg.getMessage("cmn.notset");

        NtpKtbunruiDao ktBunruiDao = new NtpKtbunruiDao(con__);
        NtpKtbunruiModel bunruiModel = ktBunruiDao.select(ktSid);
        if (bunruiModel != null) {
            ktBunrui = bunruiModel.getNkbName();
        }
        return ktBunrui;
    }
    /**
     * <br>[機  能] 活動方法を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ktSid 活動方法SID
     * @return 活動方法
     * @throws SQLException SQL例外
     */
    public String __getKthouhou(int ktSid) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ktHouhou = gsMsg.getMessage("cmn.notset");


        NtpKthouhouDao houhouDao = new NtpKthouhouDao(con__);

        NtpKthouhouModel khmodel = houhouDao.select(ktSid);

        if (khmodel != null) {
            ktHouhou = khmodel.getNkhName();
        }
        return ktHouhou;
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
}
