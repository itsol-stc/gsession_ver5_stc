package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_ACCOUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirAccountModel implements Serializable {

    /** CAC_SID mapping */
    private int cacSid__;
    /** CAC_TYPE mapping */
    private int cacType__;
    /** USR_SID mapping */
    private int usrSid__;
    /** CAC_NAME mapping */
    private String cacName__;
    /** CAC_BIKO mapping */
    private String cacBiko__;
    /** CAC_JKBN mapping */
    private int cacJkbn__;
    /** CAC_THEME mapping */
    private int cacTheme__;
    /** CAC_SML_NTF mapping */
    private int cacSmlNtf__;
    /** CAC_MEMO_KBN mapping */
    private int cacMemoKbn__;
    /** CAC_MEMO_DAY mapping */
    private int cacMemoDay__;
    /** CAC_KOU_KBN mapping */
    private int cacKouKbn__;
    /** CAC_INIT_KBN mapping */
    private int cacInitKbn__;
    /** ログイン有効フラグ */
    private int usrUkoFlg__ = 0;
    /** CAC_SML_MEMO mapping */
    private int cacSmlMemo__ = 0;
    /** CAC_SML_EDIT mapping */
    private int cacSmlEdit__ = 0;


    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public int getCacSid() {
        return cacSid__;
    }
    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }
    /**
     * <p>cacType を取得します。
     * @return cacType
     */
    public int getCacType() {
        return cacType__;
    }
    /**
     * <p>cacType をセットします。
     * @param cacType cacType
     */
    public void setCacType(int cacType) {
        cacType__ = cacType;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>cacName を取得します。
     * @return cacName
     */
    public String getCacName() {
        return cacName__;
    }
    /**
     * <p>cacName をセットします。
     * @param cacName cacName
     */
    public void setCacName(String cacName) {
        cacName__ = cacName;
    }
    /**
     * <p>cacBiko を取得します。
     * @return cacBiko
     */
    public String getCacBiko() {
        return cacBiko__;
    }
    /**
     * <p>cacBiko をセットします。
     * @param cacBiko cacBiko
     */
    public void setCacBiko(String cacBiko) {
        cacBiko__ = cacBiko;
    }
    /**
     * <p>cacJkbn を取得します。
     * @return cacJkbn
     */
    public int getCacJkbn() {
        return cacJkbn__;
    }
    /**
     * <p>cacJkbn をセットします。
     * @param cacJkbn cacJkbn
     */
    public void setCacJkbn(int cacJkbn) {
        cacJkbn__ = cacJkbn;
    }
    /**
     * <p>cacTheme を取得します。
     * @return cacTheme
     */
    public int getCacTheme() {
        return cacTheme__;
    }
    /**
     * <p>cacTheme をセットします。
     * @param cacTheme cacTheme
     */
    public void setCacTheme(int cacTheme) {
        cacTheme__ = cacTheme;
    }
    /**
     * <p>cacSmlNtf を取得します。
     * @return cacSmlNtf
     */
    public int getCacSmlNtf() {
        return cacSmlNtf__;
    }
    /**
     * <p>cacSmlNtf をセットします。
     * @param cacSmlNtf cacSmlNtf
     */
    public void setCacSmlNtf(int cacSmlNtf) {
        cacSmlNtf__ = cacSmlNtf;
    }
    /**
     * <p>cacMemoKbn を取得します。
     * @return cacMemoKbn
     */
    public int getCacMemoKbn() {
        return cacMemoKbn__;
    }
    /**
     * <p>cacMemoKbn をセットします。
     * @param cacMemoKbn cacMemoKbn
     */
    public void setCacMemoKbn(int cacMemoKbn) {
        cacMemoKbn__ = cacMemoKbn;
    }
    /**
     * <p>cacMemoDay を取得します。
     * @return cacMemoDay
     */
    public int getCacMemoDay() {
        return cacMemoDay__;
    }
    /**
     * <p>cacMemoDay をセットします。
     * @param cacMemoDay cacMemoDay
     */
    public void setCacMemoDay(int cacMemoDay) {
        cacMemoDay__ = cacMemoDay;
    }
    /**
     * <p>cacKouKbn を取得します。
     * @return cacKouKbn
     */
    public int getCacKouKbn() {
        return cacKouKbn__;
    }
    /**
     * <p>cacKouKbn をセットします。
     * @param cacKouKbn cacKouKbn
     */
    public void setCacKouKbn(int cacKouKbn) {
        cacKouKbn__ = cacKouKbn;
    }
    /**
     * <p>cacInitKbn を取得します。
     * @return cacInitKbn
     */
    public int getCacInitKbn() {
        return cacInitKbn__;
    }
    /**
     * <p>cacInitKbn をセットします。
     * @param cacInitKbn cacInitKbn
     */
    public void setCacInitKbn(int cacInitKbn) {
        cacInitKbn__ = cacInitKbn;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
    /**
     * <p>cacSmlMemo を取得します。
     * @return cacSmlMemo
     * @see jp.groupsession.v2.cir.model.CirAccountModel#cacSmlMemo__
     */
    public int getCacSmlMemo() {
        return cacSmlMemo__;
    }
    /**
     * <p>cacSmlMemo をセットします。
     * @param cacSmlMemo cacSmlMemo
     * @see jp.groupsession.v2.cir.model.CirAccountModel#cacSmlMemo__
     */
    public void setCacSmlMemo(int cacSmlMemo) {
        cacSmlMemo__ = cacSmlMemo;
    }
    /**
     * <p>cacSmlEdit を取得します。
     * @return cacSmlEdit
     * @see jp.groupsession.v2.cir.model.CirAccountModel#cacSmlEdit__
     */
    public int getCacSmlEdit() {
        return cacSmlEdit__;
    }
    /**
     * <p>cacSmlEdit をセットします。
     * @param cacSmlEdit cacSmlEdit
     * @see jp.groupsession.v2.cir.model.CirAccountModel#cacSmlEdit__
     */
    public void setCacSmlEdit(int cacSmlEdit) {
        cacSmlEdit__ = cacSmlEdit;
    }

}
