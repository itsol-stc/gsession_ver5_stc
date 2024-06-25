package jp.groupsession.v2.prj.prj150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjAddressDao;
import jp.groupsession.v2.prj.dao.PrjCompanyDao;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.model.PrjAddressModel;
import jp.groupsession.v2.prj.model.PrjCompanyModel;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.prj150.model.Prj150CompanyModel;
import jp.groupsession.v2.prj.prj150.model.Prj150DBCompanyBaseModel;
import jp.groupsession.v2.prj.prj150.model.Prj150DBCompanyModel;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトメンバー設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj150Biz.class);
    /** 並び順分割文字列 */
    private static final String SORT_SEPARATE = "-";
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Prj150Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Prj150ParamModel paramMdl, PluginConfig pconfig)
        throws SQLException {
        String movedDspId = paramMdl.getMovedDspId();
        List<PrjMemberEditModel> usrModelList = new ArrayList<PrjMemberEditModel>();

        //登録画面からの遷移
        if (movedDspId.equals(GSConstProject.SCR_ID_PRJ020)) {

            log__.debug("プロジェクト登録画面からの遷移");

            usrModelList = __getUserModelList(paramMdl);

        //プロジェクト画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ030)) {

            log__.debug("プロジェクト画面からの遷移");

            //プロジェクトSID取得
            int prjSid = paramMdl.getPrj030prjSid();

            //プロジェクトメンバー取得
            PrjMembersDao pmDao = new PrjMembersDao(con__);
            if (paramMdl.getPrj150naibuInitFlg() == 1) {

                Vector<String> existUser = new Vector<String>();
                Prj150Dao dao = new Prj150Dao(con__);
                for (Prj150MemberForm memForm : paramMdl.getMemberFormList()) {
                    List<PrjMemberEditModel> viewMdlList
                        = dao.createNewMemberList(prjSid,
                                                new String[]{String.valueOf(memForm.getUsrSid())});
                    if (viewMdlList != null && !viewMdlList.isEmpty()) {
                        PrjMemberEditModel model = viewMdlList.get(0);
                        model.setMemberKey(memForm.getProjectMemberKey());
                        usrModelList.add(model);
                        existUser.add(String.valueOf(model.getUsrSid()));
                    }
                }
                if (paramMdl.getPrj240userSid() != null && paramMdl.getPrj240userSid().length > 0) {
                    List<String> newUserList = new ArrayList<String>();
                    for (String newUser : paramMdl.getPrj240userSid()) {
                        if (!existUser.contains(newUser)) {
                            newUserList.add(newUser);
                        }
                    }

                    if (!newUserList.isEmpty()) {
                        List<PrjMemberEditModel> newUsrModelList =
                            dao.createNewMemberList(prjSid,
                                                (String[]) newUserList.toArray(
                                                                new String[newUserList.size()]));
                        if (newUsrModelList != null && !newUsrModelList.isEmpty()) {
                            usrModelList.addAll(newUsrModelList);
                        }
                    }
                }

            } else {
                usrModelList = pmDao.getMemberList(prjSid);
                paramMdl.setPrj150naibuInitFlg(1);
            }

            //チェックされているラジオがNULLの場合、初期値設定
            if (StringUtil.isNullZeroString(paramMdl.getPrj150SortRadio())
            && usrModelList.size() > 0) {
                PrjMemberEditModel model = usrModelList.get(0);
                paramMdl.setPrj150SortRadio(
                        getRadioValueStr(model.getUsrSid(), 0));
            }

        //プロジェクトテンプレート登録画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ140)) {

            log__.debug("プロジェクトテンプレート画面からの遷移");

            usrModelList = __getUserTmpModelList(paramMdl);

        }

        //アドレス帳使用有無
        setAddressUse(paramMdl, pconfig);

        //取得した値をフォームにセット
        __setUserDataToForm(paramMdl, usrModelList);
    }

    /**
     * <br>[機  能] ユーザSID配列からユーザ情報Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @return ret ユーザ情報Model
     * @throws SQLException SQL実行時例外
     */
    private List<PrjMemberEditModel> __getUserModelList(
            Prj150ParamModel paramMdl) throws SQLException {

        String[] hdnMember = paramMdl.getPrj020hdnMember();
        String[] spUsrSidList = null;
        HashMap<String, String> spUsrKeyMap = null;

        if (hdnMember != null && hdnMember.length > 0) {

            int idx = 0;
            spUsrSidList = new String[hdnMember.length];
            spUsrKeyMap = new HashMap<String, String>();

            for (String hdn : hdnMember) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spUsrSidList[idx] = String.valueOf(splitStr[0]);

                if (splitStr.length > 1) {
                    spUsrKeyMap.put(spUsrSidList[idx], splitStr[1]);
                } else {
                    spUsrKeyMap.put(spUsrSidList[idx], "");
                }

                idx += 1;
            }
        }

        UserBiz userBiz = new UserBiz();
        List<PrjMemberEditModel> ret =
            userBiz.getUserModelList(con__, spUsrSidList, spUsrKeyMap);

        paramMdl.setPrj020hdnMemberSv(paramMdl.getPrj020hdnMember());

        return ret;
    }

    /**
     * <br>[機  能] ユーザSID配列からユーザ情報Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @return ret ユーザ情報Model
     * @throws SQLException SQL実行時例外
     */
    private List<PrjMemberEditModel> __getUserTmpModelList(
            Prj150ParamModel paramMdl) throws SQLException {

        String[] hdnMember = paramMdl.getPrj140hdnMember();
        String[] spUsrSidList = null;
        HashMap<String, String> spUsrKeyMap = null;

        if (hdnMember != null && hdnMember.length > 0) {

            int idx = 0;
            spUsrSidList = new String[hdnMember.length];
            spUsrKeyMap = new HashMap<String, String>();

            for (String hdn : hdnMember) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spUsrSidList[idx] = String.valueOf(splitStr[0]);

                if (splitStr.length > 1) {
                    spUsrKeyMap.put(spUsrSidList[idx], splitStr[1]);
                } else {
                    spUsrKeyMap.put(spUsrSidList[idx], "");
                }

                idx += 1;
            }
        }

        UserBiz userBiz = new UserBiz();
        List<PrjMemberEditModel> ret =
            userBiz.getUserModelList(con__, spUsrSidList, spUsrKeyMap);

        paramMdl.setPrj140hdnMemberSv(paramMdl.getPrj140hdnMember());

        return ret;
    }

    /**
     * <br>[機  能] 取得した値をフォームにセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @param usrModelList ユーザ情報モデルリスト
     */
    private void __setUserDataToForm(Prj150ParamModel paramMdl,
                                      List<PrjMemberEditModel> usrModelList) {

        int usrSid = 0;

        //ソートで選択されているユーザSIDを取得
        if (paramMdl.getPrj150SortRadio() != null
                && paramMdl.getPrj150SortRadio().length() != 0) {
            String[] radioValue = paramMdl.getPrj150SortRadio().split(SORT_SEPARATE);
            usrSid = Integer.valueOf(radioValue[0]);
        }

        int idx = 0;
        boolean redrawFlg = false;
        if (!paramMdl.getMemberFormList().isEmpty()) {
            redrawFlg = true;

        }

        int count = 0;
        if (!usrModelList.isEmpty()) {

            for (PrjMemberEditModel mdl : usrModelList) {
                Prj150MemberForm subForm = paramMdl.getMemberForm(idx);
                //行番号
                subForm.setRowNumber(idx + 1);
               //ユーザSID
                subForm.setUsrSid(mdl.getUsrSid());
                //ユーザ氏名
                subForm.setUsrName(
                        NullDefault.getString(mdl.getUsiSei(), "")
                        + "　"
                        + NullDefault.getString(mdl.getUsiMei(), ""));
                subForm.setUsrUkoFlg(mdl.getUsrUkoFlg());
                //ソート順
                subForm.setSort(
                        getRadioValueStr(mdl.getUsrSid(), count));

                //ソートで選択しているユーザがリストから削除されていない場合
                if (usrSid == mdl.getUsrSid()) {
                    paramMdl.setPrj150SortRadio(getRadioValueStr(usrSid, count));
                }

                if (!redrawFlg) {
                    //プロジェクトメンバーID
                    subForm.setProjectMemberKey(
                            NullDefault.getString(mdl.getMemberKey(), ""));
                    //プロジェクトメンバーID_save
                    subForm.setProjectMemberKeySv(
                            NullDefault.getString(mdl.getMemberKey(), ""));
                }

                idx += 1;
                count += 1;
            }
        }
    }

    /**
     * <br>[機  能] 入力内容をDBに更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateUserKeyToDB(Prj150ParamModel paramMdl, int usrSid)
        throws SQLException {

        PrjMembersDao memDao = new PrjMembersDao(con__);

        //削除するプロジェクトメンバーリストのうち、プロジェクト管理者を取得する
        List<PrjMembersModel> prjAdminList = memDao.getPrjKanriMembers(paramMdl.getPrj030prjSid());

        //削除
        memDao.delete(paramMdl.getPrj030prjSid());

        //新規登録 or 更新
        UDate now = new UDate();
        memDao.addUserKey(
                paramMdl.getMemberFormList(),
                paramMdl.getPrj030prjSid(),
                usrSid,
                now,
                prjAdminList);
    }

    /**
     * <br>[機  能] プロジェクトアドレス情報をDBに更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void addPrjAddDB(Prj150ParamModel paramMdl, int usrSid)
        throws SQLException {

        UDate now = new UDate();
        PrjAddressDao addDao = new PrjAddressDao(con__);
        addDao.deletePrjAdd(paramMdl.getPrj030prjSid());

        if (paramMdl.getDspList() != null && !paramMdl.getDspList().isEmpty()) {
            for (Prj150DspModel mdl : paramMdl.getDspList()) {
                PrjAddressModel addressModel = new PrjAddressModel();
                addressModel.setAdrSid(mdl.getAdrSid());
                addressModel.setPrjSid(paramMdl.getPrj030prjSid());
                addressModel.setPraAuid(usrSid);
                addressModel.setPraAdate(now);
                addressModel.setPraEuid(usrSid);
                addressModel.setPraEdate(now);
                addressModel.setPraSort(mdl.getGaibuRowNumber());
                addDao.insert(addressModel);
            }
        }
    }

    /**
     * <br>[機  能] プロジェクト会社情報をDBに更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void addPrjComDB(Prj150ParamModel paramMdl, int usrSid)
        throws SQLException {

        UDate now = new UDate();
        PrjCompanyDao comDao = new PrjCompanyDao(con__);
        comDao.deletePrjCom(paramMdl.getPrj030prjSid());

        String[] companyIdList = paramMdl.getPrj150CompanySid();
        String[] companyBaseIdList = paramMdl.getPrj150CompanyBaseSid();

        if (companyIdList != null) {
            for (int i = 0; i < companyIdList.length; i++) {
                String comSid = companyIdList[i];
                String comBaseSid = companyBaseIdList[i];
                int comCnt = comDao.getprjComCount(paramMdl.getPrj030prjSid(),
                        Integer.parseInt(comSid),
                        Integer.parseInt(comBaseSid));

                if (comCnt != 0) {
                    continue;
                }

                PrjCompanyModel companyModel = new PrjCompanyModel();

                companyModel.setAcoSid(Integer.parseInt(comSid));
                companyModel.setAbaSid(Integer.parseInt(comBaseSid));
                companyModel.setPrjSid(paramMdl.getPrj030prjSid());
                companyModel.setPrcAuid(usrSid);
                companyModel.setPrcAdate(now);
                companyModel.setPrcEuid(usrSid);
                companyModel.setPrcEdate(now);
                comDao.insert(companyModel);
            }
        }
    }

    /**
     * <br>[機  能] 会社情報、アドレス帳情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj150ParamModel
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setCompanyData(Prj150ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        String movedDspId = paramMdl.getMovedDspId();

        //登録画面からの遷移
        if (movedDspId.equals(GSConstProject.SCR_ID_PRJ020) && paramMdl.getDspAddFlg() == 0) {

            log__.debug("プロジェクト登録画面からの遷移");
            if (paramMdl.getPrj020AddMemAllDelFlg() != 1) {
                __getSvCompanyData(userSid, paramMdl);
            }

            paramMdl.setDspAddFlg(1);
            paramMdl.setPrj150AddressId(paramMdl.getPrj150AddressIdSv());

        //プロジェクト画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ030)) {
            log__.debug("プロジェクト画面からの遷移");
            if (paramMdl.getPrj150gaibuInitFlg() == 1) {
                //アドレス情報を取得
                Prj150Dao prj150Dao = new Prj150Dao(con__);
                ArrayList<Prj150DspModel> pri150DspList = new ArrayList<Prj150DspModel>();

                PrjAddressDao paDao = new PrjAddressDao(con__);

                List<PrjAddressModel> addDbSidList = new ArrayList<PrjAddressModel>();

                String[] addId = paramMdl.getPrj150AddressId();

                List<String> addSidList = new ArrayList<String>();
                List<String> viewAddSidList = new ArrayList<String>();
                List<String> newAddSidList = new ArrayList<String>();

                if (addId == null) {
                    addDbSidList = paDao.getAddSidList(paramMdl.getPrj030prjSid());
                    for (int m = 0; m < addDbSidList.size(); m++) {
                        addSidList.add(String.valueOf(addDbSidList.get(m).getAdrSid()));
                    }
                    paramMdl.setPrj150AddressId(addSidList.toArray(new String[addSidList.size()]));
                } else {

                    for (int i = 0; i < paramMdl.getPrj150AddressId().length; i++) {
                        addSidList.add(addId[i]);
                    }
                    if (paramMdl.getPrj150DspList() != null
                            && paramMdl.getPrj150DspList().length > 0) {
                        for (Prj150DspModel mdl : paramMdl.getPrj150DspList()) {
                            viewAddSidList.add(String.valueOf(mdl.getAdrSid()));
                            pri150DspList.add(mdl);
                        }
                    }
                    for (String addSid : addSidList) {
                        if (!viewAddSidList.contains(addSid)) {
                            //画面に現在表示されていない外部ユーザのSIDを取得し、新規リストに追加
                            newAddSidList.add(addSid);
                        }
                    }
                }

                List<Prj150DspModel> newAddressList
                            = prj150Dao.getAddressList(con__,
                                 newAddSidList.toArray(new String[newAddSidList.size()]),
                                 userSid, paramMdl.getPrj030prjSid());
                if (newAddressList == null || newAddressList.isEmpty()) {
                    newAddressList = prj150Dao.getAddressList(con__,
                                 newAddSidList.toArray(new String[newAddSidList.size()]), userSid);
                }
                int adrSid = 0;

                int idx = viewAddSidList.size();
                int count = viewAddSidList.size();
                if (newAddressList != null) {
                    for (Prj150DspModel adrData : newAddressList) {
                        Prj150DspModel addressData = new Prj150DspModel();
                        Prj150CompanyModel companyData = new Prj150CompanyModel();
                        addressData.setAdrName(adrData.getAdrName());
                        addressData.setAdrTel(adrData.getAdrTel());
                        addressData.setAdrMail(adrData.getAdrMail());
                        addressData.setAdrSid(adrData.getAdrSid());
                        //行番号
                        addressData.setGaibuRowNumber(idx + 1);
                        //ソート順
                        addressData.setGaibuSort(getRadioValueStr(adrData.getAdrSid(), count));

                        //ソートで選択しているユーザがリストから削除されていない場合
                        if (adrSid == adrData.getAdrSid()) {
                            paramMdl.setPrj150SortRadio(getRadioValueStr(adrSid, count));
                        }

                        companyData = createCompanyData(prj150Dao,
                                adrData.getCompanySid(),
                                adrData.getCompanyBaseSid());

                        if (companyData != null) {
                            addressData.setCompanyName(companyData.getCompanyName());
                            addressData.setCompanyBaseSid(companyData.getCompanyBaseSid());
                            addressData.setCompanySid(companyData.getCompanySid());
                        }

                        pri150DspList.add(addressData);
                        count += 1;
                        idx += 1;
                    }
                }
                paramMdl.setDspList(pri150DspList);
            } else {
                __getCompanyData(userSid, paramMdl);
                paramMdl.setPrj150gaibuInitFlg(1);
            }
        } else {
            __getCompanyData(userSid, paramMdl);
            paramMdl.setPrj150gaibuInitFlg(1);
        }
        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getPrj150SortGaibuRadio())
        && paramMdl.getDspList().size() > 0) {
            Prj150DspModel model = paramMdl.getDspList().get(0);
            paramMdl.setPrj150SortGaibuRadio(
                    getRadioValueStr(model.getAdrSid(), 0));
        }
    }

    /**
     * <br>[機  能] 会社情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dao150 プロジェクトメンバ登録画面DAO
     * @param acoSid 会社SID
     * @param abaSid 会社拠点SID
     * @return 会社情報
     * @throws SQLException SQL実行時例外
     */
    public Prj150CompanyModel createCompanyData(
            Prj150Dao dao150,
            int acoSid,
            int abaSid)
    throws SQLException {

        Prj150CompanyModel companyData = null;

        Prj150DBCompanyModel model = dao150.getCompanyData(acoSid);

        if (model != null) {
            companyData = new Prj150CompanyModel();

            String companyName = model.getAcoName();

            Prj150DBCompanyBaseModel baseModel = dao150.getCompanyBaseData(abaSid);
            if (baseModel != null) {
                companyName += " " + baseModel.getAbaName();
            }

            companyData.setCompanySid(acoSid);
            companyData.setCompanyBaseSid(abaSid);
            companyData.setCompanyName(companyName);
        }

        return companyData;
    }

    /**
     * <br>[機  能] 会社・アドレス情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param paramMdl Prj150ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __getCompanyData(
            int userSid,
            Prj150ParamModel paramMdl)
    throws SQLException {

        //アドレス情報を取得
        Prj150Dao prj150Dao = new Prj150Dao(con__);
        ArrayList<Prj150DspModel> pri150DspList = new ArrayList<Prj150DspModel>();

        PrjAddressDao paDao = new PrjAddressDao(con__);

        List<PrjAddressModel> addDbSidList = new ArrayList<PrjAddressModel>();

        String[] addId = paramMdl.getPrj150AddressId();

        List<String> addSidList = new ArrayList<String>();

        if (addId == null) {
            addDbSidList = paDao.getAddSidList(paramMdl.getPrj030prjSid());
            for (int m = 0; m < addDbSidList.size(); m++) {
                addSidList.add(String.valueOf(addDbSidList.get(m).getAdrSid()));
            }
            paramMdl.setPrj150AddressId(addSidList.toArray(new String[addSidList.size()]));
        } else {
            for (int i = 0; i < paramMdl.getPrj150AddressId().length; i++) {
                addSidList.add(addId[i]);
            }
            paramMdl.setPrj150AddressId(addSidList.toArray(new String[addSidList.size()]));
        }

        List<Prj150DspModel> addressList
                    = prj150Dao.getAddressList(con__,
                            paramMdl.getPrj150AddressId(), userSid, paramMdl.getPrj030prjSid());

        int adrSid = 0;

        //ソートで選択されているユーザSIDを取得
        if (paramMdl.getPrj150SortGaibuRadio() != null
                && paramMdl.getPrj150SortGaibuRadio().length() != 0) {
            String[] radioValue = paramMdl.getPrj150SortGaibuRadio().split(SORT_SEPARATE);
            adrSid = Integer.valueOf(radioValue[0]);
        }

        int idx = 0;
        int count = 0;
        if (addressList != null) {
            for (Prj150DspModel adrData : addressList) {
                Prj150DspModel addressData = new Prj150DspModel();
                Prj150CompanyModel companyData = new Prj150CompanyModel();
                addressData.setAdrName(adrData.getAdrName());
                addressData.setAdrTel(adrData.getAdrTel());
                addressData.setAdrMail(adrData.getAdrMail());
                addressData.setAdrSid(adrData.getAdrSid());
                //行番号
                addressData.setGaibuRowNumber(idx + 1);
                //ソート順
                addressData.setGaibuSort(getRadioValueStr(adrData.getAdrSid(), count));

                //ソートで選択しているユーザがリストから削除されていない場合
                if (adrSid == adrData.getAdrSid()) {
                    paramMdl.setPrj150SortRadio(getRadioValueStr(adrSid, count));
                }

                companyData = createCompanyData(prj150Dao,
                        adrData.getCompanySid(),
                        adrData.getCompanyBaseSid());

                if (companyData != null) {
                    addressData.setCompanyName(companyData.getCompanyName());
                    addressData.setCompanyBaseSid(companyData.getCompanyBaseSid());
                    addressData.setCompanySid(companyData.getCompanySid());
                }

                pri150DspList.add(addressData);
                count += 1;
                idx += 1;
            }
        }
        paramMdl.setDspList(pri150DspList);
    }

    /**
     * <br>[機  能] Saveパラメータを元に会社・アドレス情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param paramMdl Prj150ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __getSvCompanyData(
            int userSid,
            Prj150ParamModel paramMdl)
    throws SQLException {

        //アドレス情報を取得
        Prj150Dao prj150Dao = new Prj150Dao(con__);
        ArrayList<Prj150DspModel> pri150DspList = new ArrayList<Prj150DspModel>();

        PrjAddressDao paDao = new PrjAddressDao(con__);

        List<PrjAddressModel> addDbSidList = new ArrayList<PrjAddressModel>();

        String[] addId = paramMdl.getPrj150AddressIdSv();

        List<String> addSidList = new ArrayList<String>();
        if (addId == null) {
            addDbSidList = paDao.getAddSidList(paramMdl.getPrj030prjSid());
            for (int m = 0; m < addDbSidList.size(); m++) {
                addSidList.add(String.valueOf(addDbSidList.get(m).getAdrSid()));
            }
            paramMdl.setPrj150AddressIdSv(addSidList.toArray(new String[addSidList.size()]));
        } else {
            for (int i = 0; i < paramMdl.getPrj150AddressIdSv().length; i++) {
                addSidList.add(addId[i]);
            }
            paramMdl.setPrj150AddressIdSv(addSidList.toArray(new String[addSidList.size()]));
        }

        List<Prj150DspModel> addressList
                    = prj150Dao.getAddressList(con__, paramMdl.getPrj150AddressIdSv(),
                            userSid, paramMdl.getPrj030prjSid());
        int i = 1;
        if (addressList != null) {
            for (Prj150DspModel adrData : addressList) {
                Prj150DspModel addressData = new Prj150DspModel();
                Prj150CompanyModel companyData = new Prj150CompanyModel();
                addressData.setAdrName(adrData.getAdrName());
                addressData.setAdrTel(adrData.getAdrTel());
                addressData.setAdrMail(adrData.getAdrMail());
                addressData.setAdrSid(adrData.getAdrSid());
                addressData.setGaibuRowNumber(i);

                companyData = createCompanyData(prj150Dao,
                        adrData.getCompanySid(),
                        adrData.getCompanyBaseSid());

                if (companyData != null) {
                    addressData.setCompanyName(companyData.getCompanyName());
                    addressData.setCompanyBaseSid(companyData.getCompanyBaseSid());
                    addressData.setCompanySid(companyData.getCompanySid());
                }

                pri150DspList.add(addressData);
                i++;
            }
        }
        paramMdl.setDspList(pri150DspList);
    }

    /**
     * <br>[機  能] 会社情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Prj150ParamModel
     * @param userSid セッションユーザSID
     * @param delCompanyId 会社ID
     * @param delCompanyBaseId 会社拠点ID
     * @param userDelFlg ユーザ削除フラグ
     * @throws SQLException SQL実行時例外
     */
    public void deleteCompany(Connection con, Prj150ParamModel paramMdl, int userSid,
                            int delCompanyId, int delCompanyBaseId, int userDelFlg)
    throws SQLException {

        if (delCompanyId != -1 && delCompanyBaseId != -1) {

            List<Prj150DspModel> companyList = paramMdl.getDspList();
            if (companyList != null && !companyList.isEmpty()) {
                List<String> companyIdList = new ArrayList<String>();
                List<String> companyBaseIdList = new ArrayList<String>();
                List<String> addressIdList = new ArrayList<String>();

                for (Prj150DspModel companyData : companyList) {
                    if (companyData.getCompanySid() != delCompanyId
                    || companyData.getCompanyBaseSid() != delCompanyBaseId) {

                        companyIdList.add(String.valueOf(companyData.getCompanySid()));
                        companyBaseIdList.add(String.valueOf(companyData.getCompanyBaseSid()));
                    }
                    //ユーザを削除
                    if (userDelFlg != companyData.getAdrSid()) {
                        addressIdList.add(String.valueOf(companyData.getAdrSid()));
                    }
                }

                paramMdl.setPrj150CompanySid(
                        companyIdList.toArray(new String[companyIdList.size()]));
                paramMdl.setPrj150CompanyBaseSid(
                        companyBaseIdList.toArray(new String[companyBaseIdList.size()]));
                paramMdl.setPrj150AddressId(
                        addressIdList.toArray(new String[addressIdList.size()]));
                //外部メンバーを設定
                __getCompanyData(userSid, paramMdl);
            }
        }
    }
    /**
     * <br>[機  能] 選択したユーザを１つ上に上げる(内部)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @throws Exception 実行時例外
     */
    public void getSortUpUsrList(Prj150ParamModel paramMdl) throws Exception {

        List<Prj150MemberForm> memList = new ArrayList<Prj150MemberForm>();
        memList = paramMdl.getMemberFormList();

        ArrayList<Prj150MemberForm> dspMemList = new ArrayList<Prj150MemberForm>();

        boolean flg = false;
        Prj150MemberForm menMdl = null;

        //並び替えるユーザ
        String [] targetData = paramMdl.getPrj150SortRadio().split(SORT_SEPARATE);

        for (int i = 0; i < memList.size(); i++) {

            if (i == Integer.parseInt(targetData[1]) - 1) {
                //下にさがるユーザ
                flg = true;
                continue;
            }

            if (flg) {

                //上にあがるユーザ
                menMdl = memList.get(i);
                dspMemList.add(menMdl);

                //下にさがるユーザ
                menMdl = memList.get(i - 1);

                flg = false;

            } else {
                menMdl = memList.get(i);
            }

            dspMemList.add(menMdl);
            paramMdl.setMemberFormList(dspMemList);
        }
    }
    /**
     * <br>[機  能] 選択したユーザを１つ上に上げる(外部)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @throws Exception 実行時例外
     */
    public void getSortUpGaibuUsrList(Prj150ParamModel paramMdl) throws Exception {

        List<Prj150DspModel> memList = new ArrayList<Prj150DspModel>();
        memList = paramMdl.getDspList();

        ArrayList<Prj150DspModel> dspMemList = new ArrayList<Prj150DspModel>();

        boolean flg = false;
        Prj150DspModel menMdl = null;
        //並び替えるユーザ
        String [] targetData = paramMdl.getPrj150SortGaibuRadio().split(SORT_SEPARATE);

        for (int i = 0; i < memList.size(); i++) {

            if (i == Integer.parseInt(targetData[1]) - 1) {
                //下にさがるユーザ
                flg = true;
                continue;
            }

            if (flg) {

                //上にあがるユーザ
                menMdl = memList.get(i);
                menMdl.setGaibuRowNumber(menMdl.getGaibuRowNumber() - 1);
                menMdl.setGaibuSort(getRadioValueStr(menMdl.getAdrSid(),
                        menMdl.getGaibuRowNumber() - 1));
                paramMdl.setPrj150SortGaibuRadio(menMdl.getGaibuSort());
                dspMemList.add(menMdl);

                //下にさがるユーザ
                menMdl = memList.get(i - 1);
                menMdl.setGaibuRowNumber(menMdl.getGaibuRowNumber() + 1);
                menMdl.setGaibuSort(getRadioValueStr(menMdl.getAdrSid(),
                        menMdl.getGaibuRowNumber() - 1));

                flg = false;

            } else {
                menMdl = memList.get(i);
            }

            dspMemList.add(menMdl);
            paramMdl.setDspList(dspMemList);
        }
    }

    /**
     * <br>[機  能] 選択したユーザを１つ下に下げる（内部）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @throws Exception 実行時例外
     */
    public void getSortDownUsrList(Prj150ParamModel paramMdl) throws Exception {

        List<Prj150MemberForm> memList = new ArrayList<Prj150MemberForm>();
        memList = paramMdl.getMemberFormList();

        ArrayList<Prj150MemberForm> dspMemList = new ArrayList<Prj150MemberForm>();

        boolean flg = false;
        Prj150MemberForm menMdl = null;

        //並び替えるユーザ
        String [] targetData = paramMdl.getPrj150SortRadio().split(SORT_SEPARATE);

        for (int i = 0; i < memList.size(); i++) {

            if (i == Integer.parseInt(targetData[1])) {
                //下にさがるユーザ
                flg = true;
                continue;
            }

            if (flg) {

                //上にあがるユーザ
                menMdl = memList.get(i);
                dspMemList.add(menMdl);

                //下にさがるユーザ
                menMdl = memList.get(i - 1);
                flg = false;

            } else {
                menMdl = memList.get(i);
            }

            dspMemList.add(menMdl);
            paramMdl.setMemberFormList(dspMemList);
        }
    }
    /**
     * <br>[機  能] 選択したユーザを１つ下に下げる（外部）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj150ParamModel
     * @throws Exception 実行時例外
     */
    public void getSortDownGaibuUsrList(Prj150ParamModel paramMdl) throws Exception {

        List<Prj150DspModel> memList = new ArrayList<Prj150DspModel>();
        memList = paramMdl.getDspList();

        ArrayList<Prj150DspModel> dspMemList = new ArrayList<Prj150DspModel>();

        boolean flg = false;
        Prj150DspModel menMdl = null;

        //並び替えるユーザ
        String [] targetData = paramMdl.getPrj150SortGaibuRadio().split(SORT_SEPARATE);

        for (int i = 0; i < memList.size(); i++) {

            if (i == Integer.parseInt(targetData[1])) {
                //下にさがるユーザ
                flg = true;
                continue;
            }

            if (flg) {

                //上にあがるユーザ
                menMdl = memList.get(i);
                menMdl.setGaibuRowNumber(menMdl.getGaibuRowNumber() - 1);
                menMdl.setGaibuSort(getRadioValueStr(menMdl.getAdrSid(),
                        menMdl.getGaibuRowNumber() - 1));
                dspMemList.add(menMdl);

                //下にさがるユーザ
                menMdl = memList.get(i - 1);
                menMdl.setGaibuRowNumber(menMdl.getGaibuRowNumber() + 1);
                menMdl.setGaibuSort(getRadioValueStr(menMdl.getAdrSid(),
                        menMdl.getGaibuRowNumber() - 1));
                paramMdl.setPrj150SortGaibuRadio(menMdl.getGaibuSort());
                flg = false;

            } else {
                menMdl = memList.get(i);
            }

            dspMemList.add(menMdl);
            paramMdl.setDspList(dspMemList);
        }
    }
    /**
     * <br>[機  能] radioにセットする値(文字列)を取得する
     * <br>[解  説] 「業種SID-表示順-画面上の表示順」のフォーマットの文字列を返す
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param index 画面上の表示順
     * @return String radioにセットする値
     */
    public String getRadioValueStr(int usrSid, int index) {

        String sort = usrSid + SORT_SEPARATE
                    + index;
        return sort;
    }

    /**
     * <br>[機  能] アドレス帳プラグインの使用有無を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj150ParamModel
     * @param pconfig プラグインコンフィグ
     */
    public void setAddressUse(Prj150ParamModel paramMdl, PluginConfig pconfig) {
        if (pconfig.getPlugin("address") != null) {
            paramMdl.setAddressPluginKbn(GSConstProject.PLUGIN_USE);
            log__.debug("アドレス帳使用");
        } else {
            paramMdl.setAddressPluginKbn(GSConstProject.PLUGIN_NOT_USE);
            log__.debug("アドレス帳使用不可");
        }
    }

    /**
     * <br>[機  能] 対象のユーザ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList SIDリスト
     * @param kbn 内部or外部
     * @param reqMdl リクエストモデル
     * @return ユーザ名リスト
     * @throws SQLException SQLException
     */
    public List<String> getTargetName(Connection con, String[] sidList,
            String kbn, RequestModel reqMdl)
            throws SQLException {

        List<String> nameList = new ArrayList<String>();
        if (kbn.equals(GSConstProject.MODE_NAIBU)) {
            CmnUsrmDao cmnDao = new CmnUsrmDao(con);
            ArrayList<BaseUserModel> usrMdlList =  cmnDao.getSelectedUserList(sidList);
            for (BaseUserModel mdl : usrMdlList) {
                nameList.add(mdl.getUsisei() + " " + mdl.getUsimei());
            }
        } else if (kbn.equals(GSConstProject.MODE_GAIBU)) {
            Prj150Dao dao = new Prj150Dao(con);
            List<Prj150DspModel> mdlList = dao.getAddressList(con, sidList,
                    reqMdl.getSmodel().getUsrsid());
            for (Prj150DspModel mdl : mdlList) {
                nameList.add(mdl.getAdrName());
            }
        }
        return nameList;
    }
}