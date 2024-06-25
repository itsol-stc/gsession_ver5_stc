package jp.groupsession.v2.prj.prj150;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.GSValidateProject;
import jp.groupsession.v2.prj.prj020.Prj020Form;
import jp.groupsession.v2.prj.prj150.model.Prj150CompanyModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトメンバー設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150Form extends Prj020Form {
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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     *@param req リクエスト
     * @return エラー
     */
    public ActionErrors validate150(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        int errorSize = 0;
        GsMessage gsMsg = new GsMessage();
        //他のメンバーの
        String textOtherMem = gsMsg.getMessage(req, "project.src.85");
        if (member__.isEmpty()) {
            return errors;
        }
        GSValidateProject gsValidatePrj = new GSValidateProject(req);
        for (int i = 0; i < member__.size(); i++) {

            boolean rowError = false;
            errorSize = errors.size();

            //チェックする行取得
            Prj150MemberForm inputForm = getMemberForm(i);
            String inputMemberKey = inputForm.getProjectMemberKey();
            //プロジェクトメンバーID
            String textProjectMemId = gsMsg.getMessage(req, "project.3");
            //プロジェクトメンバーIDが入力されている場合
            if (!StringUtil.isNullZeroString(inputMemberKey)) {

                //プロジェクトメンバーID
                gsValidatePrj.validateRowsTextBoxInput(
                        errors,
                        inputMemberKey,
                        textProjectMemId,
                        GSConstProject.MAX_LENGTH_PRJ_MEM_ID,
                        false,
                        inputForm.getRowNumber());

                if (errorSize != errors.size()) {
                    rowError = true;
                }
            }

            //プロジェクトメンバーIDが入力されている
            //and チェックする行にフォーマットのエラーが無い
            if (!StringUtil.isNullZeroString(inputMemberKey) && !rowError) {

                for (int j = 0; j < member__.size(); j++) {

                    if (j == i) {
                        continue;
                    }

                    //比較対象行取得
                    Prj150MemberForm comparisonForm = getMemberForm(j);
                    String comparisonMemberKey = comparisonForm.getProjectMemberKey();

                    //比較対象行のプロジェクトメンバーIDが入力されている
                    if (!StringUtil.isNullZeroString(comparisonMemberKey)) {
                        if (inputMemberKey.equals(comparisonMemberKey)) {

                            msg = new ActionMessage(
                                    "error.select.dup.list2",
                                    gsMsg.getMessage(req, "cmn.no.of",
                                            String.valueOf(inputForm.getRowNumber()),
                                            String.valueOf(textProjectMemId)),
                                            String.valueOf(textOtherMem + textProjectMemId)
                                    );
                            StrutsUtil.addMessage(
                                    errors,
                                    msg,
                                    inputForm.getRowNumber() + ".error.select.dup.list2");

                            break;
                        }
                    }
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除ボタン(内部)クリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     *@param req リクエスト
     * @return エラー
     */
    public ActionErrors validateDelete(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //プロジェクトメンバー
        String textPrjMem = gsMsg.getMessage(req, "project.src.29");
        if (getPrj150naibuSelectMemberSid() == null
        || getPrj150naibuSelectMemberSid().length < 0) {
            msg = new ActionMessage("error.select.required.text", textPrjMem);
            StrutsUtil.addMessage(errors, msg,
                                "prj150naibuSelectMemberSid.error.select.required.text");
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除ボタン(外部)クリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateDeleteGaibu(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //プロジェクトメンバー
        String textPrjMem = gsMsg.getMessage(req, "project.src.29");
        if (getPrj150gaibuSelectMemberSid() == null
        || getPrj150gaibuSelectMemberSid().length < 0) {
            msg = new ActionMessage("error.select.required.text", textPrjMem);
            StrutsUtil.addMessage(errors, msg,
                                "prj150gsibuSelectMemberSid.error.select.required.text");
        }

        return errors;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {

        super.setcmn999FormParam(cmn999Form);

        cmn999Form.addHiddenParam("prj010cmdMode", getPrj010cmdMode());
        cmn999Form.addHiddenParam("prj010page1", getPrj010page1());
        cmn999Form.addHiddenParam("prj010page2", getPrj010page2());
        cmn999Form.addHiddenParam("prj010sort", getPrj010sort());
        cmn999Form.addHiddenParam("prj010order", getPrj010order());
        cmn999Form.addHiddenParam("prj010Init", String.valueOf(isPrj010Init()));
        cmn999Form.addHiddenParam("selectingProject", getSelectingProject());
        cmn999Form.addHiddenParam("selectingTodoDay", getSelectingTodoDay());
        cmn999Form.addHiddenParam("selectingTodoPrj", getSelectingTodoPrj());
        cmn999Form.addHiddenParam("selectingTodoSts", getSelectingTodoSts());
        cmn999Form.addHiddenParam("prj040searchFlg", getPrj040searchFlg());
        cmn999Form.addHiddenParam("prj040scPrjId", getPrj040scPrjId());
        cmn999Form.addHiddenParam("prj040scStatusFrom", getPrj040scStatusFrom());
        cmn999Form.addHiddenParam("prj040scStatusTo", getPrj040scStatusTo());
        cmn999Form.addHiddenParam("prj040scPrjName", getPrj040scPrjName());
        cmn999Form.addHiddenParam("prj040scStartYearFrom", getPrj040scStartYearFrom());
        cmn999Form.addHiddenParam("prj040scStartMonthFrom", getPrj040scStartMonthFrom());
        cmn999Form.addHiddenParam("prj040scStartDayFrom", getPrj040scStartDayFrom());
        cmn999Form.addHiddenParam("prj040scStartYearTo", getPrj040scStartYearTo());
        cmn999Form.addHiddenParam("prj040scStartMonthTo", getPrj040scStartMonthTo());
        cmn999Form.addHiddenParam("prj040scStartDayTo", getPrj040scStartDayTo());
        cmn999Form.addHiddenParam("prj040scEndYearFrom", getPrj040scEndYearFrom());
        cmn999Form.addHiddenParam("prj040scEndMonthFrom", getPrj040scEndMonthFrom());
        cmn999Form.addHiddenParam("prj040scEndDayFrom", getPrj040scEndDayFrom());
        cmn999Form.addHiddenParam("prj040scEndYearTo", getPrj040scEndYearTo());
        cmn999Form.addHiddenParam("prj040scEndMonthTo", getPrj040scEndMonthTo());
        cmn999Form.addHiddenParam("prj040scEndDayTo", getPrj040scEndDayTo());
        cmn999Form.addHiddenParam("prj040svScPrjId", getPrj040svScPrjId());
        cmn999Form.addHiddenParam("prj040svScStatusFrom", getPrj040svScStatusFrom());
        cmn999Form.addHiddenParam("prj040svScStatusTo", getPrj040svScStatusTo());
        cmn999Form.addHiddenParam("prj040svScPrjName", getPrj040svScPrjName());
        cmn999Form.addHiddenParam("prj040svScStartYearFrom", getPrj040svScStartYearFrom());
        cmn999Form.addHiddenParam("prj040svScStartMonthFrom", getPrj040svScStartMonthFrom());
        cmn999Form.addHiddenParam("prj040svScStartDayFrom", getPrj040svScStartDayFrom());
        cmn999Form.addHiddenParam("prj040svScStartYearTo", getPrj040svScStartYearTo());
        cmn999Form.addHiddenParam("prj040svScStartMonthTo", getPrj040svScStartMonthTo());
        cmn999Form.addHiddenParam("prj040svScStartDayTo", getPrj040svScStartDayTo());
        cmn999Form.addHiddenParam("prj040svScEndYearFrom", getPrj040svScEndYearFrom());
        cmn999Form.addHiddenParam("prj040svScEndMonthFrom", getPrj040svScEndMonthFrom());
        cmn999Form.addHiddenParam("prj040svScEndDayFrom", getPrj040svScEndDayFrom());
        cmn999Form.addHiddenParam("prj040svScEndYearTo", getPrj040svScEndYearTo());
        cmn999Form.addHiddenParam("prj040svScEndMonthTo", getPrj040svScEndMonthTo());
        cmn999Form.addHiddenParam("prj040svScEndDayTo", getPrj040svScEndDayTo());
        cmn999Form.addHiddenParam("prj040scYosanFr", getPrj040scYosanFr());
        cmn999Form.addHiddenParam("prj040scYosanTo", getPrj040scYosanTo());
        cmn999Form.addHiddenParam("prj040svScYosanFr", getPrj040svScYosanFr());
        cmn999Form.addHiddenParam("prj040svScYosanTo", getPrj040svScYosanTo());
        cmn999Form.addHiddenParam("prj040page1", getPrj040page1());
        cmn999Form.addHiddenParam("prj040page2", getPrj040page2());
        cmn999Form.addHiddenParam("prj040sort", getPrj040sort());
        cmn999Form.addHiddenParam("prj040order", getPrj040order());
        cmn999Form.addHiddenParam("prj040scMemberSid", getPrj040scMemberSid());
        cmn999Form.addHiddenParam("prj040svScMemberSid", getPrj040svScMemberSid());
        cmn999Form.addHiddenParam("prj030scrId", getPrj030scrId());
        cmn999Form.addHiddenParam("prj030prjSid", getPrj030prjSid());
        cmn999Form.addHiddenParam("prj030sort", getPrj030sort());
        cmn999Form.addHiddenParam("prj030order", getPrj030order());
        cmn999Form.addHiddenParam("prj030page1", getPrj030page1());
        cmn999Form.addHiddenParam("prj030page2", getPrj030page2());
        cmn999Form.addHiddenParam("prj030Init", String.valueOf(isPrj030Init()));
        cmn999Form.addHiddenParam("selectingDate", getSelectingDate());
        cmn999Form.addHiddenParam("selectingStatus", getSelectingStatus());
        cmn999Form.addHiddenParam("selectingMember", getSelectingMember());
        cmn999Form.addHiddenParam("prj030sendMember", getPrj030sendMember());
        cmn999Form.addHiddenParam("prj020scrId", getPrj020scrId());
        cmn999Form.addHiddenParam("prj020cmdMode", getPrj020cmdMode());
        cmn999Form.addHiddenParam("prj020prjSid", getPrj020prjSid());
        cmn999Form.addHiddenParam("prj020prjId", getPrj020prjId());
        cmn999Form.addHiddenParam("prj020prjName", getPrj020prjName());
        cmn999Form.addHiddenParam("prj020prjNameS", getPrj020prjNameS());
        cmn999Form.addHiddenParam("prj020yosan", getPrj020yosan());
        cmn999Form.addHiddenParam("prj020koukai", getPrj020koukai());
        cmn999Form.addHiddenParam("prj020startYear", getPrj020startYear());
        cmn999Form.addHiddenParam("prj020startMonth", getPrj020startMonth());
        cmn999Form.addHiddenParam("prj020startDay", getPrj020startDay());
        cmn999Form.addHiddenParam("prj020endYear", getPrj020endYear());
        cmn999Form.addHiddenParam("prj020endMonth", getPrj020endMonth());
        cmn999Form.addHiddenParam("prj020endDay", getPrj020endDay());
        cmn999Form.addHiddenParam("prj020status", getPrj020status());
        cmn999Form.addHiddenParam("prj020mokuhyou", getPrj020mokuhyou());
        cmn999Form.addHiddenParam("prj020naiyou", getPrj020naiyou());
        cmn999Form.addHiddenParam("prj020group", getPrj020group());
        cmn999Form.addHiddenParam("prj020kengen", getPrj020kengen());
        cmn999Form.addHiddenParam("prj020prjMyKbn", getPrj020prjMyKbn());
        cmn999Form.addHiddenParam("prj020syozokuMember", getPrj020syozokuMember());
        cmn999Form.addHiddenParam("prj020user", getPrj020user());
        cmn999Form.addHiddenParam("prj020hdnMember", getPrj020hdnMember());
        cmn999Form.addHiddenParam("prj020adminMember", getPrj020adminMember());
        cmn999Form.addHiddenParam("prj020prjMember", getPrj020prjMember());
        cmn999Form.addHiddenParam("prj020hdnAdmin", getPrj020hdnAdmin());
        cmn999Form.addHiddenParam("prjTmpMode", getPrjTmpMode());
        cmn999Form.addHiddenParam("prtSid", getPrtSid());
        cmn999Form.addHiddenParam("prj140prtTmpName", getPrj140prtTmpName());
        cmn999Form.addHiddenParam("prj140prtId", getPrj140prtId());
        cmn999Form.addHiddenParam("prj140prtName", getPrj140prtName());
        cmn999Form.addHiddenParam("prj140prtNameS", getPrj140prtNameS());
        cmn999Form.addHiddenParam("prj140yosan", getPrj140yosan());
        cmn999Form.addHiddenParam("prj140koukai", getPrj140koukai());
        cmn999Form.addHiddenParam("prj140startYear", getPrj140startYear());
        cmn999Form.addHiddenParam("prj140startMonth", getPrj140startMonth());
        cmn999Form.addHiddenParam("prj140startDay", getPrj140startDay());
        cmn999Form.addHiddenParam("prj140endYear", getPrj140endYear());
        cmn999Form.addHiddenParam("prj140endMonth", getPrj140endMonth());
        cmn999Form.addHiddenParam("prj140endDay", getPrj140endDay());
        cmn999Form.addHiddenParam("prj140status", getPrj140status());
        cmn999Form.addHiddenParam("prj140mokuhyou", getPrj140mokuhyou());
        cmn999Form.addHiddenParam("prj140naiyou", getPrj140naiyou());
        cmn999Form.addHiddenParam("prj140group", getPrj140group());
        cmn999Form.addHiddenParam("prj140kengen", getPrj140kengen());
        cmn999Form.addHiddenParam("prj140smailKbn", getPrj140smailKbn());
        cmn999Form.addHiddenParam("prj140syozokuMember", getPrj140syozokuMember());
        cmn999Form.addHiddenParam("prj140user", getPrj140user());
        cmn999Form.addHiddenParam("prj140hdnMember", getPrj140hdnMember());
        cmn999Form.addHiddenParam("prj140adminMember", getPrj140adminMember());
        cmn999Form.addHiddenParam("prj140prjMember", getPrj140prjMember());
        cmn999Form.addHiddenParam("prj140hdnAdmin", getPrj140hdnAdmin());
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
     * @see jp.groupsession.v2.prj.prj150.Prj150Form#prj240userSid__
     */
    public String[] getPrj240userSid() {
        return prj240userSid__;
    }
    /**
     * <p>prj240userSid をセットします。
     * @param prj240userSid prj240userSid
     * @see jp.groupsession.v2.prj.prj150.Prj150Form#prj240userSid__
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