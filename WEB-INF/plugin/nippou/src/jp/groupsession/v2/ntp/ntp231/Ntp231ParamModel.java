package jp.groupsession.v2.ntp.ntp231;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.ntp230.Ntp230ParamModel;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] 日報 目標登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp231ParamModel extends Ntp230ParamModel {

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ntp231InitFlg__ = String.valueOf(GSConstNippou.INIT_FLG);
    /** 初期表示フラグ */
    private int ntp231initDspFlg__ = 0;
    /** 処理モード */
    private String ntp231ProcMode__ = GSConstNippou.CMD_ADD;
    /** 目標SID */
    private int ntp231NtgSid__ = -1;
    /** 目標名 */
    private String ntp231TargetName__ = null;
    /** 単位 */
    private String ntp231TargetUnit__ = null;
    /** 初期値 */
    private String ntp231TargetDef__ = "0";
    /** 内容 */
    private String ntp231TargetDetail__ = null;
    /** テンプレートリスト */
    private List<NtpTemplateModel> ntp231TemplateList__ = null;
    /** 表示目標 */
    private String[] ntp231DspTemplate__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        //目標名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_TARGET_NAME,
                ntp231TargetName__,
               "ntp231TargetName",
                GSConstNippou.MAX_LENGTH_TARGET_NAME,
                true);


        //目標 単位入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_TARGET_UNIT,
                ntp231TargetUnit__,
               "ntp231TargetUnit",
                GSConstNippou.MAX_LENGTH_TARGET_UNIT,
                true);

        //目標 初期値入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_TARGET_DEF,
                ntp231TargetDef__,
               "ntp231TargetDef",
                GSConstNippou.MAX_LENGTH_TARGET_DEF,
                true);

        //目標 内容入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_TARGET_DETAIL,
                ntp231TargetDetail__,
               "ntp231TargetDetail",
                GSConstNippou.MAX_LENGTH_TARGET_DETAIL,
                false);

        return errors;
    }

    /**
     * <p>ntp231TargetName を取得します。
     * @return ntp231TargetName
     */
    public String getNtp231TargetName() {
        return ntp231TargetName__;
    }

    /**
     * <p>ntp231TargetName をセットします。
     * @param ntp231TargetName ntp231TargetName
     */
    public void setNtp231TargetName(String ntp231TargetName) {
        ntp231TargetName__ = ntp231TargetName;
    }

    /**
     * <p>ntp231TargetUnit を取得します。
     * @return ntp231TargetUnit
     */
    public String getNtp231TargetUnit() {
        return ntp231TargetUnit__;
    }

    /**
     * <p>ntp231TargetUnit をセットします。
     * @param ntp231TargetUnit ntp231TargetUnit
     */
    public void setNtp231TargetUnit(String ntp231TargetUnit) {
        ntp231TargetUnit__ = ntp231TargetUnit;
    }

    /**
     * <p>ntp231TargetDetail を取得します。
     * @return ntp231TargetDetail
     */
    public String getNtp231TargetDetail() {
        return ntp231TargetDetail__;
    }

    /**
     * <p>ntp231TargetDetail をセットします。
     * @param ntp231TargetDetail ntp231TargetDetail
     */
    public void setNtp231TargetDetail(String ntp231TargetDetail) {
        ntp231TargetDetail__ = ntp231TargetDetail;
    }

    /**
     * <p>ntp231ProcMode を取得します。
     * @return ntp231ProcMode
     */
    public String getNtp231ProcMode() {
        return ntp231ProcMode__;
    }

    /**
     * <p>ntp231ProcMode をセットします。
     * @param ntp231ProcMode ntp231ProcMode
     */
    public void setNtp231ProcMode(String ntp231ProcMode) {
        ntp231ProcMode__ = ntp231ProcMode;
    }

    /**
     * <p>ntp231NtgSid を取得します。
     * @return ntp231NtgSid
     */
    public int getNtp231NtgSid() {
        return ntp231NtgSid__;
    }

    /**
     * <p>ntp231NtgSid をセットします。
     * @param ntp231NtgSid ntp231NtgSid
     */
    public void setNtp231NtgSid(int ntp231NtgSid) {
        ntp231NtgSid__ = ntp231NtgSid;
    }

    /**
     * <p>ntp231TargetDef を取得します。
     * @return ntp231TargetDef
     */
    public String getNtp231TargetDef() {
        return ntp231TargetDef__;
    }

    /**
     * <p>ntp231TargetDef をセットします。
     * @param ntp231TargetDef ntp231TargetDef
     */
    public void setNtp231TargetDef(String ntp231TargetDef) {
        ntp231TargetDef__ = ntp231TargetDef;
    }

    /**
     * <p>ntp231TemplateList を取得します。
     * @return ntp231TemplateList
     */
    public List<NtpTemplateModel> getNtp231TemplateList() {
        return ntp231TemplateList__;
    }

    /**
     * <p>ntp231TemplateList をセットします。
     * @param ntp231TemplateList ntp231TemplateList
     */
    public void setNtp231TemplateList(List<NtpTemplateModel> ntp231TemplateList) {
        ntp231TemplateList__ = ntp231TemplateList;
    }

    /**
     * <p>ntp231DspTemplate を取得します。
     * @return ntp231DspTemplate
     */
    public String[] getNtp231DspTemplate() {
        return ntp231DspTemplate__;
    }

    /**
     * <p>ntp231DspTemplate をセットします。
     * @param ntp231DspTemplate ntp231DspTemplate
     */
    public void setNtp231DspTemplate(String[] ntp231DspTemplate) {
        ntp231DspTemplate__ = ntp231DspTemplate;
    }

    /**
     * <p>ntp231initDspFlg を取得します。
     * @return ntp231initDspFlg
     */
    public int getNtp231initDspFlg() {
        return ntp231initDspFlg__;
    }

    /**
     * <p>ntp231initDspFlg をセットします。
     * @param ntp231initDspFlg ntp231initDspFlg
     */
    public void setNtp231initDspFlg(int ntp231initDspFlg) {
        ntp231initDspFlg__ = ntp231initDspFlg;
    }

    /**
     * <p>ntp231InitFlg を取得します。
     * @return ntp231InitFlg
     */
    public String getNtp231InitFlg() {
        return ntp231InitFlg__;
    }

    /**
     * <p>ntp231InitFlg をセットします。
     * @param ntp231InitFlg ntp231InitFlg
     */
    public void setNtp231InitFlg(String ntp231InitFlg) {
        ntp231InitFlg__ = ntp231InitFlg;
    }

}
