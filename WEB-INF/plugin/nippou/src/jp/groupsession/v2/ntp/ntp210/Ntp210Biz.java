package jp.groupsession.v2.ntp.ntp210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 案件情報ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp210Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp210Biz(Connection con,
                     RequestModel reqMdl) {
        reqMdl__ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp210ParamModel
     * @param userMdl セッションユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp210ParamModel paramMdl,
            BaseUserModel userMdl,
            Connection con) throws SQLException {

        //変更処理
        Ntp210AnkenDao ankenDao = new Ntp210AnkenDao(con);
        Ntp210AnkenModel ankenModel = null;


        if (paramMdl.getNtp210HisFlg() == 0) {
            ankenModel = ankenDao.select(paramMdl.getNtp210NanSid(), reqMdl__);
        } else {
            ankenModel = ankenDao.selectFromHistory(paramMdl.getNtp210NahSid(), reqMdl__);
        }

        if (ankenModel != null) {
            paramMdl.setNtp210Date(ankenModel.getNanDate().getStrYear() + "年"
                    + ankenModel.getNanDate().getMonth() + "月"
                    + ankenModel.getNanDate().getIntDay() + "日");
             paramMdl.setNtp210TourokuName(ankenModel.getNtp210TourokuName());
             paramMdl.setNtp210NanCode(ankenModel.getNanCode());
             paramMdl.setNtp210NanName(ankenModel.getNanName());
             paramMdl.setNtp210NanSyosai(
                     StringUtilHtml.transToHTmlPlusAmparsant(ankenModel.getNanDetial()));
             paramMdl.setNtp210AcoCode(ankenModel.getNtp210CompanyCode());
             paramMdl.setNtp210CompanyName(ankenModel.getNtp210CompanyName());
             paramMdl.setNtp210CompanyBaseName(ankenModel.getNtp210BaseName());
             paramMdl.setNtp210CompanySid(Integer.toString(ankenModel.getAcoSid()));
             paramMdl.setNtp210CompanyBaseSid(Integer.toString(ankenModel.getAbaSid()));
             paramMdl.setNtp210NgySid(ankenModel.getNtp210NgySid());
             paramMdl.setNtp210NgpSid(ankenModel.getNgpSid());
             paramMdl.setNtp210NanMikomi(ankenModel.getNanMikomi());
             paramMdl.setNtp210NanKinMitumori(Integer.toString(ankenModel.getNanKinMitumori()));
             paramMdl.setNtp210NanKinJutyu(Integer.toString(ankenModel.getNanKinJutyu()));
             paramMdl.setNtp210NanSyodan(ankenModel.getNanSyodan());
             paramMdl.setNtp210NanState(ankenModel.getNanState());
             paramMdl.setNtp210NcnSid(ankenModel.getNcnSid());

             NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);

             if (paramMdl.getNtp210HisFlg() == 0) {
                 paramMdl.setNtp210ChkShohinSidList(
                         anShohinDao.select(paramMdl.getNtp210NanSid()));
             } else {
                 paramMdl.setNtp210ChkShohinSidList(
                         anShohinDao.selectFromHistory(paramMdl.getNtp210NahSid()));
             }

             //選択された商品情報の設定
             _setShohinData(con, paramMdl);

             //選択された会社情報の設定
             _setCompanyData(con, paramMdl);

             setDspData(paramMdl, con);
        }
    }

    /**
     * <br>[機  能] 画面表示データの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp210ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setDspData(Ntp210ParamModel paramMdl, Connection con) throws SQLException {
        NtpCommonBiz cBiz = new NtpCommonBiz(con, reqMdl__);
        //業務リスト取得
        paramMdl.setNtp060GyomuList(cBiz.getGyomuList(con, ""));
        for (LabelValueBean gyoBean : paramMdl.getNtp060GyomuList()) {
            if (Integer.parseInt(gyoBean.getValue()) == paramMdl.getNtp210NgySid()) {
                paramMdl.setNtp210GyoushuName(gyoBean.getLabel());
            }
        }

        String mes = "";
        if (paramMdl.getNtp210NgySid() > 0) {
            mes = "選択してください";
        }
        //プロセスリスト取得
        paramMdl.setNtp060ProcessList(cBiz.getProcessList(con, mes, paramMdl.getNtp210NgySid()));
        for (LabelValueBean prcBean : paramMdl.getNtp060ProcessList()) {
            if (Integer.parseInt(prcBean.getValue()) == paramMdl.getNtp210NgpSid()) {
                paramMdl.setNtp210ProcessName(prcBean.getLabel());
            }
        }

        //コンタクトリスト取得
        paramMdl.setNtp060ContactList(cBiz.getContactList(con, ""));
        for (LabelValueBean cntBean : paramMdl.getNtp060ContactList()) {
            if (Integer.parseInt(cntBean.getValue()) == paramMdl.getNtp210NcnSid()) {
                paramMdl.setNtp210ContactName(cntBean.getLabel());
            }
        }
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
     * @param paramMdl Ntp210ParamModel
     * @throws SQLException SQL実行時例外
     */
    protected void _setShohinData(Connection con, Ntp210ParamModel paramMdl) throws SQLException {
        if (paramMdl.getNtp210ChkShohinSidList().length > 0) {
            NtpShohinDao shohinDao = new NtpShohinDao(con);
            List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
            ArrayList<NtpShohinModel> shohinList = new ArrayList<NtpShohinModel>();

            shohinList = (ArrayList<NtpShohinModel>)
                shohinDao.select(paramMdl.getNtp210ChkShohinSidList());

            for (NtpShohinModel mdl : shohinList) {
                labelList.add(
                        new LabelValueBean(mdl.getNhnName(), String.valueOf(mdl.getNhnSid())));
            }
            paramMdl.setNtp210ShohinList(labelList);
        }
    }

    /**
     * <br>[機  能] 選択された会社情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp210ParamModel
     * @throws SQLException SQL実行時例外
     */
    protected void _setCompanyData(Connection con, Ntp210ParamModel paramMdl)
    throws SQLException {
        if (!StringUtil.isNullZeroString(paramMdl.getNtp210CompanySid())) {


            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyModel companyMdl
                = companyDao.select(Integer.parseInt(paramMdl.getNtp210CompanySid()));

            if (companyMdl != null) {
                paramMdl.setNtp210AcoCode(companyMdl.getAcoCode());
                paramMdl.setNtp210CompanyName(companyMdl.getAcoName());

                if (!StringUtil.isNullZeroString(paramMdl.getNtp210CompanyBaseSid())) {
                    AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
                    AdrCompanyBaseModel companyBaseMdl
                        = companyBaseDao.select(
                                Integer.parseInt(paramMdl.getNtp210CompanyBaseSid()));
                    if (companyBaseMdl != null) {
                        String companyBaseName = companyBaseMdl.getAbaName();
                        String companyBaseType
                            = AddressBiz.getCompanyBaseTypeName(
                                    companyBaseMdl.getAbaType(), reqMdl__);
                        if (!StringUtil.isNullZeroString(companyBaseType)) {
                            companyBaseName = companyBaseType + " ： " + companyBaseName;
                        }
                        paramMdl.setNtp210CompanyBaseName(companyBaseName);
                    }
                }
            }
        }
    }
}