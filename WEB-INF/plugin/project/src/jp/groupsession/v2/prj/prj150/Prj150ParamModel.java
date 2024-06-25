package jp.groupsession.v2.prj.prj150;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.prj020.Prj020ParamModel;
import jp.groupsession.v2.prj.prj150.model.Prj150CompanyModel;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトメンバー設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150ParamModel extends Prj020ParamModel {
    /** プロジェクトメンバーフォームリスト */
    private ArrayList<Prj150MemberForm> member__ = new ArrayList<Prj150MemberForm>();
    /** セパレートキー */
    private final String sepKey = GSConst.GSESSION2_ID;
    /** 所属メンバーsave */
    private String[] prj020hdnMemberSv__;
    /** 所属メンバーsave */
    private String[] prj140hdnMemberSv__;
    /** 処理モード */
    private String prj150cmdMode__ = GSConstProject.MODE_NAIBU;
    /** アドレス帳プラグイン使用有無 0=使用 1=未使用*/
    private int addressPluginKbn__;

    /** 内部メンバー初期表示フラグ */
    private int prj150naibuInitFlg__ = 0;
    /** 外部メンバー初期表示フラグ */
    private int prj150gaibuInitFlg__ = 0;
    /**
     * <p>prj150gaibuInitFlg を取得します。
     * @return prj150gaibuInitFlg
     */
    public int getPrj150gaibuInitFlg() {
        return prj150gaibuInitFlg__;
    }
    /**
     * <p>prj150gaibuInitFlg をセットします。
     * @param prj150gaibuInitFlg prj150gaibuInitFlg
     */
    public void setPrj150gaibuInitFlg(int prj150gaibuInitFlg) {
        prj150gaibuInitFlg__ = prj150gaibuInitFlg;
    }
    /** 内部メンバー 選択されたメンバーのユーザSID */
    private String[] prj150naibuSelectMemberSid__ = null;
    /** 追加したメンバーのユーザSID */
    private String[] prj240userSid__ = null;
    /** 外部メンバー 選択されたメンバーのID */
    private String[] prj150gaibuSelectMemberSid__ = null;

    /** チェックされている並び順(内部) */
    private String prj150SortRadio__  = null;
    /** チェックされている並び順 （外部）*/
    private String prj150SortGaibuRadio__  = null;

    //会社情報
    /** 会社情報一覧 */
    private List<Prj150CompanyModel> prj150CompanyList__ = null;

    /** 画面表示用 */
    private ArrayList<Prj150DspModel> prj150DspList__ = new ArrayList<Prj150DspModel>();

    /** 削除対象の会社ID */
    private String prj150delCompanyId__ = null;
    /** 削除対象の会社拠点ID */
    private String prj150delCompanyBaseId__ = null;
    /** ユーザを削除対象にするかのフラグ */
    private int prj150UsrDelFlg__ = 0;

    //プロジェクトメンバ設定外部キー項目
    /** 会社SID save */
    private String[] prj150CompanySidSv__ = null;
    /** 会社拠点SID save */
    private String[] prj150CompanyBaseSidSv__ = null;
    /** 担当者(アドレス情報) */
    private String[] prj150AddressId__ = null;
    /** アドレス情報削除フラグ */
    private int dspAddFlg__ = 0;

    /**
     * @return dspAddFlg
     */
    public int getDspAddFlg() {
        return dspAddFlg__;
    }
    /**
     * @param dspAddFlg 設定する dspAddFlg
     */
    public void setDspAddFlg(int dspAddFlg) {
        dspAddFlg__ = dspAddFlg;
    }
    /**
     * <p>prj020hdnMemberSv を取得します。
     * @return prj020hdnMemberSv
     */
    public String[] getPrj020hdnMemberSv() {
        return prj020hdnMemberSv__;
    }
    /**
     * <p>prj020hdnMemberSv をセットします。
     * @param prj020hdnMemberSv prj020hdnMemberSv
     */
    public void setPrj020hdnMemberSv(String[] prj020hdnMemberSv) {
        prj020hdnMemberSv__ = prj020hdnMemberSv;
    }
    /**
     * <p>prj140hdnMemberSv を取得します。
     * @return prj140hdnMemberSv
     */
    public String[] getPrj140hdnMemberSv() {
        return prj140hdnMemberSv__;
    }
    /**
     * <p>prj140hdnMemberSv をセットします。
     * @param prj140hdnMemberSv prj140hdnMemberSv
     */
    public void setPrj140hdnMemberSv(String[] prj140hdnMemberSv) {
        prj140hdnMemberSv__ = prj140hdnMemberSv;
    }
    /**
     * <p>sepKey を取得します。
     * @return sepKey
     */
    public String getSepKey() {
        return sepKey;
    }

    /**
     * <br>[機  能] フォームパラメータをコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setParam(Object form) {
        super.setParam(form);

        setMemberList(((Prj150Form) form).getMemberList());
        setDspList(((Prj150Form) form).getDspList());
    }
    /**
     * <br>[機  能] Modelの出力値をフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setFormData(Object form) {
        super.setFormData(form);

        ((Prj150Form) form).setMemberList(getMemberList());
        ((Prj150Form) form).setDspList(getDspList());
    }


    /**
     * @return member__ を戻す
     */
    public ArrayList<Prj150MemberForm> getMemberFormList() {
        return member__;
    }
    /**
     * @return prj150DspList__ を戻す
     */
    public ArrayList<Prj150DspModel> getDspList() {
        return prj150DspList__;
    }


    /**
     * @param member プロジェクトメンバーフォームリスト
     */
    public void setMemberFormList(ArrayList<Prj150MemberForm> member) {
        member__ = member;
    }
    /**
     * @param prj150DspList 表示モデル
     */
    public void setDspList(ArrayList<Prj150DspModel>  prj150DspList) {
        prj150DspList__ =  prj150DspList;
    }


    /**
     * @param iIndex インデックス番号
     * @return Prj150MemberForm を戻す
     */
    public Prj150MemberForm getMember(int iIndex) {
        while (member__.size() <= iIndex) {
            member__.add(new Prj150MemberForm());
        }
        return (Prj150MemberForm) member__.get(iIndex);
    }

    /**
     * @param iIndex インデックス番号
     * @return Prj150MemberForm を戻す
     */
    public Prj150DspModel getPrj150DspList(int iIndex) {
        while (prj150DspList__.size() <= iIndex) {
            prj150DspList__.add(new Prj150DspModel());
        }
        return (Prj150DspModel) prj150DspList__.get(iIndex);
    }


    /**
     * @return Prj150MemberForm[]
     */
    public Prj150MemberForm[] getMember() {
        return (Prj150MemberForm[]) member__.toArray(new Prj150MemberForm[0]);
    }
    /**
     * @return Prj150MemberForm[]
     */
    public Prj150DspModel[] getPrj150DspList() {
        return (Prj150DspModel[]) prj150DspList__.toArray(new Prj150DspModel[0]);
    }


    /**
     * @return 表の行数
     */
    public int getMemberFormSize() {
        return member__.size();
    }
    /**
     * @return 表の行数
     */
    public int getPrj150DspListSize() {
        return prj150DspList__.size();
    }


    /**
     * @param pos 行インデックス
     * @return Prj150MemberForm
     */
    protected Prj150MemberForm getMemberForm(int pos) {
        while (pos >= member__.size()) {
            member__.add(null);
        }
        Prj150MemberForm form = (Prj150MemberForm) member__.get(pos);
        if (form == null) {
            form = new Prj150MemberForm();
            member__.set(pos, form);
        }
        return form;
    }
    /**
     * @param pos 行インデックス
     * @return Prj150MemberForm
     */
    protected Prj150DspModel getPrj150DspModelList(int pos) {
        while (pos >= prj150DspList__.size()) {
            prj150DspList__.add(null);
        }
        Prj150DspModel form = (Prj150DspModel) prj150DspList__.get(pos);
        if (form == null) {
            form = new Prj150DspModel();
            prj150DspList__.set(pos, form);
        }
        return form;
    }

    /**
     * @param pos インデックス番号 pos
     * @param rowNum 設定する rowNum
     */
    public void setGaibuRowNumber(int pos, int rowNum) {
        getPrj150DspModelList(pos).setGaibuRowNumber(rowNum);
    }
    /**
     * @param pos インデックス番号 pos
     * @param adrSid 設定する usrSid
     */
    public void setAdrSid(int pos, int adrSid) {
        getPrj150DspModelList(pos).setAdrSid(adrSid);
    }
    /**
     * @param pos インデックス番号 pos
     * @param companySid 設定する companySid
     */
    public void setCompanySid(int pos, int companySid) {
        getPrj150DspModelList(pos).setCompanySid(companySid);
    }
    /**
     * @param pos インデックス番号 pos
     * @param companyBaseSid 設定する companyBaseSid
     */
    public void setCompanyBaseSid(int pos, int companyBaseSid) {
        getPrj150DspModelList(pos).setCompanyBaseSid(companyBaseSid);
    }
    /**
     * @param pos インデックス番号 pos
     * @param adrName 設定する adrName
     */
    public void setAdrName(int pos, String adrName) {
        getPrj150DspModelList(pos).setAdrName(adrName);
    }
    /**
     * @param pos インデックス番号 pos
     * @param adrMail 設定するadrMail
     */
    public void setAdrMail(int pos, String adrMail) {
        getPrj150DspModelList(pos).setAdrMail(adrMail);
    }
    /**
     * @param pos インデックス番号 pos
     * @param adrTel 設定する adrTel
     */
    public void setAdrTel(int pos, String adrTel) {
        getPrj150DspModelList(pos).setAdrTel(adrTel);
    }
    /**
     * @param pos インデックス番号 pos
     * @param companyName 設定するcompanyName
     */
    public void setCompanyName(int pos, String companyName) {
        getPrj150DspModelList(pos).setCompanyName(companyName);
    }
    /**
     * @param pos インデックス番号 pos
     * @param companyBaseName 設定する companyBaseName
     */
    public void setCompanyBaseName(int pos, String companyBaseName) {
        getPrj150DspModelList(pos).setCompanyBaseName(companyBaseName);
    }
    /**
     * @param pos インデックス番号 pos
     * @param sort 設定する sort
     */
    public void setGaibuSort(int pos, String sort) {
        getPrj150DspModelList(pos).setGaibuSort(sort);
    }


    /**
     * @param pos インデックス番号 pos
     * @param rowNum 設定する rowNum
     */
    public void setRowNumber(int pos, int rowNum) {
        getMemberForm(pos).setRowNumber(rowNum);
    }
    /**
     * @param pos インデックス番号 pos
     * @param usrSid 設定する usrSid
     */
    public void setUsrSid(int pos, int usrSid) {
        getMemberForm(pos).setUsrSid(usrSid);
    }
    /**
     * @param pos インデックス番号 pos
     * @param usrName 設定する usrName
     */
    public void setUsrName(int pos, String usrName) {
        getMemberForm(pos).setUsrName(usrName);
    }
    /**
     * @param pos インデックス番号 pos
     * @param projectMemberKey 設定する projectMemberKey
     */
    public void setProjectMemberKey(int pos, String projectMemberKey) {
        getMemberForm(pos).setProjectMemberKey(projectMemberKey);
    }
    /**
     * @param pos インデックス番号 pos
     * @param projectMemberKeySv 設定する projectMemberKeySv
     */
    public void setProjectMemberKeySv(int pos, String projectMemberKeySv) {
        getMemberForm(pos).setProjectMemberKeySv(projectMemberKeySv);
    }
    /**
     * @param pos インデックス番号 pos
     * @param sort 設定する sort
     */
    public void setSort(int pos, String sort) {
        getMemberForm(pos).setSort(sort);
    }


    /**
     * @return prj150cmdMode
     */
    public String getPrj150cmdMode() {
        return prj150cmdMode__;
    }
    /**
     * @param prj150cmdMode 設定する prj150cmdMode
     */
    public void setPrj150cmdMode(String prj150cmdMode) {
        prj150cmdMode__ = prj150cmdMode;
    }
    /**
     * @return addressPluginKbn
     */
    public int getAddressPluginKbn() {
        return addressPluginKbn__;
    }
    /**
     * @param addressPluginKbn 設定する addressPluginKbn
     */
    public void setAddressPluginKbn(int addressPluginKbn) {
        addressPluginKbn__ = addressPluginKbn;
    }
    /**
     * @return prj150CompanyList
     */
    public List<Prj150CompanyModel> getPrj150CompanyList() {
        return prj150CompanyList__;
    }
    /**
     * @param prj150CompanyList 設定する prj150CompanyList
     */
    public void setPrj150CompanyList(List<Prj150CompanyModel> prj150CompanyList) {
        prj150CompanyList__ = prj150CompanyList;
    }
    /**
     * @return prj150delCompanyBaseId
     */
    public String getPrj150delCompanyBaseId() {
        return prj150delCompanyBaseId__;
    }
    /**
     * @param prj150delCompanyBaseId 設定する prj150delCompanyBaseId
     */
    public void setPrj150delCompanyBaseId(String prj150delCompanyBaseId) {
        prj150delCompanyBaseId__ = prj150delCompanyBaseId;
    }
    /**
     * @return prj150delCompanyId
     */
    public String getPrj150delCompanyId() {
        return prj150delCompanyId__;
    }
    /**
     * @param prj150delCompanyId 設定する prj150delCompanyId
     */
    public void setPrj150delCompanyId(String prj150delCompanyId) {
        prj150delCompanyId__ = prj150delCompanyId;
    }
    /**
     * @return prj150UsrDelFlg
     */
    public int getPrj150UsrDelFlg() {
        return prj150UsrDelFlg__;
    }
    /**
     * @param prj150UsrDelFlg 設定する prj150UsrDelFlg
     */
    public void setPrj150UsrDelFlg(int prj150UsrDelFlg) {
        prj150UsrDelFlg__ = prj150UsrDelFlg;
    }
    /**
     * <p>prj150SortGaibuRadio を取得します。
     * @return prj150SortGaibuRadio
     */
    public String getPrj150SortGaibuRadio() {
        return prj150SortGaibuRadio__;
    }
    /**
     * <p>prj150SortGaibuRadio をセットします。
     * @param prj150SortGaibuRadio prj150SortGaibuRadio
     */
    public void setPrj150SortGaibuRadio(String prj150SortGaibuRadio) {
        prj150SortGaibuRadio__ = prj150SortGaibuRadio;
    }
    /**
     * @return prj150CompanyBaseSidSv
     */
    public String[] getPrj150CompanyBaseSidSv() {
        return prj150CompanyBaseSidSv__;
    }
    /**
     * @param prj150CompanyBaseSidSv 設定する prj150CompanyBaseSidSv
     */
    public void setPrj150CompanyBaseSidSv(String[] prj150CompanyBaseSidSv) {
        prj150CompanyBaseSidSv__ = prj150CompanyBaseSidSv;
    }
    /**
     * @return prj150CompanySidSv
     */
    public String[] getPrj150CompanySidSv() {
        return prj150CompanySidSv__;
    }
    /**
     * @param prj150CompanySidSv 設定する prj150CompanySidSv
     */
    public void setPrj150CompanySidSv(String[] prj150CompanySidSv) {
        prj150CompanySidSv__ = prj150CompanySidSv;
    }
    /**
     * @return prj150AddressId
     */
    public String[] getPrj150AddressId() {
        return prj150AddressId__;
    }
    /**
     * @param prj150AddressId 設定する prj150AddressId
     */
    public void setPrj150AddressId(String[] prj150AddressId) {
        prj150AddressId__ = prj150AddressId;
    }

    /**
     * <p>prj150naibuInitFlg を取得します。
     * @return prj150naibuInitFlg
     */
    public int getPrj150naibuInitFlg() {
        return prj150naibuInitFlg__;
    }
    /**
     * <p>prj150naibuInitFlg をセットします。
     * @param prj150naibuInitFlg prj150naibuInitFlg
     */
    public void setPrj150naibuInitFlg(int prj150naibuInitFlg) {
        prj150naibuInitFlg__ = prj150naibuInitFlg;
    }
    /**
     * <p>prj150naibuSelectMemberSid を取得します。
     * @return prj150naibuSelectMemberSid
     */
    public String[] getPrj150naibuSelectMemberSid() {
        return prj150naibuSelectMemberSid__;
    }
    /**
     * <p>prj150naibuSelectMemberSid をセットします。
     * @param prj150naibuSelectMemberSid prj150naibuSelectMemberSid
     */
    public void setPrj150naibuSelectMemberSid(String[] prj150naibuSelectMemberSid) {
        prj150naibuSelectMemberSid__ = prj150naibuSelectMemberSid;
    }
    /**
     * <p>prj150gaibuSelectMemberSid を取得します。
     * @return prj150gaibuSelectMemberSid
     */
    public String[] getPrj150gaibuSelectMemberSid() {
        return prj150gaibuSelectMemberSid__;
    }
    /**
     * <p>prj150gaibuSelectMemberSid をセットします。
     * @param prj150gaibuSelectMemberSid prj150gaibuSelectMemberSid
     */
    public void setPrj150gaibuSelectMemberSid(String[] prj150gaibuSelectMemberSid) {
        prj150gaibuSelectMemberSid__ = prj150gaibuSelectMemberSid;
    }
    /**
     * <p>prj240userSid を取得します。
     * @return prj240userSid
     * @see jp.groupsession.v2.prj.prj150.Prj150ParamModel#prj240userSid__
     */
    public String[] getPrj240userSid() {
        return prj240userSid__;
    }
    /**
     * <p>prj240userSid をセットします。
     * @param prj240userSid prj240userSid
     * @see jp.groupsession.v2.prj.prj150.Prj150ParamModel#prj240userSid__
     */
    public void setPrj240userSid(String[] prj240userSid) {
        prj240userSid__ = prj240userSid;
    }
    /**
     * <p>prj150SortRadio を取得します。
     * @return prj150SortRadio
     */
    public String getPrj150SortRadio() {
        return prj150SortRadio__;
    }
    /**
     * <p>prj150SortRadio をセットします。
     * @param prj150SortRadio prj150SortRadio
     */
    public void setPrj150SortRadio(String prj150SortRadio) {
        prj150SortRadio__ = prj150SortRadio;
    }
}