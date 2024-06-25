package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>NTP_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpDataModel  extends AbstractModel {

    /** NIP_SID mapping */
    private int nipSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** NIP_DATE mapping */
    private UDate nipDate__;
    /** NIP_FR_TIME mapping */
    private UDate nipFrTime__;
    /** NIP_TO_TIME mapping */
    private UDate nipToTime__;
    /** NIP_KADO_HH mapping */
    private int nipKadoHh__;
    /** NIP_KADO_MM mapping */
    private int nipKadoMm__;
    /** NIP_MGY_SID mapping */
    private int nipMgySid__;
    /** NAN_SID mapping */
    private int nanSid__;
    /** ACO_SID mapping */
    private int acoSid__;
    /** ABA_SID mapping */
    private int abaSid__;
    /** NIP_TITLE mapping */
    private String nipTitle__;
    /** NIP_TITLE_CLO mapping */
    private int nipTitleClo__;
    /** MPR_SID mapping */
    private int mprSid__;
    /** MKB_SID mapping */
    private int mkbSid__;
    /** MKH_SID mapping */
    private int mkhSid__;
    /** NIP_TIEUP_SID mapping */
    private int nipTieupSid__;
    /** NIP_KEIZOKU mapping */
    private int nipKeizoku__;
    /** NIP_ACTEND mapping */
    private UDate nipActend__;
    /** NIP_DETAIL mapping */
    private String nipDetail__;
    /** NIP_ACTION_DATE mapping */
    private UDate nipActionDate__;
    /** NIP_ACTION mapping */
    private String nipAction__;
    /** NIP_ACTDATE_KBN mapping */
    private int nipActDateKbn__;
    /** NIP_ASSIGN mapping */
    private String nipAssign__;
    /** NIP_KINGAKU mapping */
    private int nipKingaku__;
    /** NIP_MIKOMI mapping */
    private int nipMikomi__;
    /** NIP_SYOKAN mapping */
    private String nipSyokan__;
    /** NIP_PUBLIC mapping */
    private int nipPublic__;
    /** NIP_EDIT mapping */
    private int nipEdit__;
    /** NEX_SID mapping */
    private int nexSid__;
    /** NIP_AUID mapping */
    private int nipAuid__;
    /** NIP_ADATE mapping */
    private UDate nipAdate__;
    /** NIP_EUID mapping */
    private int nipEuid__;
    /** NIP_EDATE mapping */
    private UDate nipEdate__;

    /** 被登録者名　月間で使用 */
    private String ntpUserName__;
    /**案件非公開か*/
    private boolean ankenViewable__ = true;

    /**
     * <p>Default Constructor
     */
    public NtpDataModel() {
    }

    /**
     * <p>get NIP_SID value
     * @return NIP_SID value
     */
    public int getNipSid() {
        return nipSid__;
    }

    /**
     * <p>set NIP_SID value
     * @param nipSid NIP_SID value
     */
    public void setNipSid(int nipSid) {
        nipSid__ = nipSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get NIP_DATE value
     * @return NIP_DATE value
     */
    public UDate getNipDate() {
        return nipDate__;
    }

    /**
     * <p>set NIP_DATE value
     * @param nipDate NIP_DATE value
     */
    public void setNipDate(UDate nipDate) {
        nipDate__ = nipDate;
    }

    /**
     * <p>get NIP_FR_TIME value
     * @return NIP_FR_TIME value
     */
    public UDate getNipFrTime() {
        return nipFrTime__;
    }

    /**
     * <p>set NIP_FR_TIME value
     * @param nipFrTime NIP_FR_TIME value
     */
    public void setNipFrTime(UDate nipFrTime) {
        nipFrTime__ = nipFrTime;
    }

    /**
     * <p>get NIP_TO_TIME value
     * @return NIP_TO_TIME value
     */
    public UDate getNipToTime() {
        return nipToTime__;
    }

    /**
     * <p>set NIP_TO_TIME value
     * @param nipToTime NIP_TO_TIME value
     */
    public void setNipToTime(UDate nipToTime) {
        nipToTime__ = nipToTime;
    }

    /**
     * <p>get NIP_KADO_HH value
     * @return NIP_KADO_HH value
     */
    public int getNipKadoHh() {
        return nipKadoHh__;
    }

    /**
     * <p>set NIP_KADO_HH value
     * @param nipKadoHh NIP_KADO_HH value
     */
    public void setNipKadoHh(int nipKadoHh) {
        nipKadoHh__ = nipKadoHh;
    }

    /**
     * <p>get NIP_KADO_MM value
     * @return NIP_KADO_MM value
     */
    public int getNipKadoMm() {
        return nipKadoMm__;
    }

    /**
     * <p>set NIP_KADO_MM value
     * @param nipKadoMm NIP_KADO_MM value
     */
    public void setNipKadoMm(int nipKadoMm) {
        nipKadoMm__ = nipKadoMm;
    }

    /**
     * <p>get NIP_MGY_SID value
     * @return NIP_MGY_SID value
     */
    public int getNipMgySid() {
        return nipMgySid__;
    }

    /**
     * <p>set NIP_MGY_SID value
     * @param nipMgySid NIP_MGY_SID value
     */
    public void setNipMgySid(int nipMgySid) {
        nipMgySid__ = nipMgySid;
    }

    /**
     * <p>get NAN_SID value
     * @return NAN_SID value
     */
    public int getNanSid() {
        return nanSid__;
    }

    /**
     * <p>set NAN_SID value
     * @param nanSid NAN_SID value
     */
    public void setNanSid(int nanSid) {
        nanSid__ = nanSid;
    }

    /**
     * <p>get ACO_SID value
     * @return ACO_SID value
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>set ACO_SID value
     * @param acoSid ACO_SID value
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>get ABA_SID value
     * @return ABA_SID value
     */
    public int getAbaSid() {
        return abaSid__;
    }

    /**
     * <p>set ABA_SID value
     * @param abaSid ABA_SID value
     */
    public void setAbaSid(int abaSid) {
        abaSid__ = abaSid;
    }

    /**
     * <p>get NIP_TITLE value
     * @return NIP_TITLE value
     */
    public String getNipTitle() {
        return nipTitle__;
    }

    /**
     * <p>set NIP_TITLE value
     * @param nipTitle NIP_TITLE value
     */
    public void setNipTitle(String nipTitle) {
        nipTitle__ = nipTitle;
    }

    /**
     * <p>get NIP_TITLE_CLO value
     * @return NIP_TITLE_CLO value
     */
    public int getNipTitleClo() {
        return nipTitleClo__;
    }

    /**
     * <p>set NIP_TITLE_CLO value
     * @param nipTitleClo NIP_TITLE_CLO value
     */
    public void setNipTitleClo(int nipTitleClo) {
        nipTitleClo__ = nipTitleClo;
    }

    /**
     * <p>get MPR_SID value
     * @return MPR_SID value
     */
    public int getMprSid() {
        return mprSid__;
    }

    /**
     * <p>set MPR_SID value
     * @param mprSid MPR_SID value
     */
    public void setMprSid(int mprSid) {
        mprSid__ = mprSid;
    }

    /**
     * <p>get MKB_SID value
     * @return MKB_SID value
     */
    public int getMkbSid() {
        return mkbSid__;
    }

    /**
     * <p>set MKB_SID value
     * @param mkbSid MKB_SID value
     */
    public void setMkbSid(int mkbSid) {
        mkbSid__ = mkbSid;
    }

    /**
     * <p>get MKH_SID value
     * @return MKH_SID value
     */
    public int getMkhSid() {
        return mkhSid__;
    }

    /**
     * <p>set MKH_SID value
     * @param mkhSid MKH_SID value
     */
    public void setMkhSid(int mkhSid) {
        mkhSid__ = mkhSid;
    }

    /**
     * <p>get NIP_TIEUP_SID value
     * @return NIP_TIEUP_SID value
     */
    public int getNipTieupSid() {
        return nipTieupSid__;
    }

    /**
     * <p>set NIP_TIEUP_SID value
     * @param nipTieupSid NIP_TIEUP_SID value
     */
    public void setNipTieupSid(int nipTieupSid) {
        nipTieupSid__ = nipTieupSid;
    }

    /**
     * <p>get NIP_KEIZOKU value
     * @return NIP_KEIZOKU value
     */
    public int getNipKeizoku() {
        return nipKeizoku__;
    }

    /**
     * <p>set NIP_KEIZOKU value
     * @param nipKeizoku NIP_KEIZOKU value
     */
    public void setNipKeizoku(int nipKeizoku) {
        nipKeizoku__ = nipKeizoku;
    }

    /**
     * <p>get NIP_ACTEND value
     * @return NIP_ACTEND value
     */
    public UDate getNipActend() {
        return nipActend__;
    }

    /**
     * <p>set NIP_ACTEND value
     * @param nipActend NIP_ACTEND value
     */
    public void setNipActend(UDate nipActend) {
        nipActend__ = nipActend;
    }

    /**
     * <p>get NIP_DETAIL value
     * @return NIP_DETAIL value
     */
    public String getNipDetail() {
        return nipDetail__;
    }

    /**
     * <p>set NIP_DETAIL value
     * @param nipDetail NIP_DETAIL value
     */
    public void setNipDetail(String nipDetail) {
        nipDetail__ = nipDetail;
    }

    /**
     * <p>get NIP_ASSIGN value
     * @return NIP_ASSIGN value
     */
    public String getNipAssign() {
        return nipAssign__;
    }

    /**
     * <p>set NIP_ASSIGN value
     * @param nipAssign NIP_ASSIGN value
     */
    public void setNipAssign(String nipAssign) {
        nipAssign__ = nipAssign;
    }

    /**
     * <p>get NIP_KINGAKU value
     * @return NIP_KINGAKU value
     */
    public int getNipKingaku() {
        return nipKingaku__;
    }

    /**
     * <p>set NIP_KINGAKU value
     * @param nipKingaku NIP_KINGAKU value
     */
    public void setNipKingaku(int nipKingaku) {
        nipKingaku__ = nipKingaku;
    }

    /**
     * <p>get NIP_MIKOMI value
     * @return NIP_MIKOMI value
     */
    public int getNipMikomi() {
        return nipMikomi__;
    }

    /**
     * <p>set NIP_MIKOMI value
     * @param nipMikomi NIP_MIKOMI value
     */
    public void setNipMikomi(int nipMikomi) {
        nipMikomi__ = nipMikomi;
    }

    /**
     * <p>get NIP_SYOKAN value
     * @return NIP_SYOKAN value
     */
    public String getNipSyokan() {
        return nipSyokan__;
    }

    /**
     * <p>set NIP_SYOKAN value
     * @param nipSyokan NIP_SYOKAN value
     */
    public void setNipSyokan(String nipSyokan) {
        nipSyokan__ = nipSyokan;
    }

    /**
     * <p>get NIP_PUBLIC value
     * @return NIP_PUBLIC value
     */
    public int getNipPublic() {
        return nipPublic__;
    }

    /**
     * <p>set NIP_PUBLIC value
     * @param nipPublic NIP_PUBLIC value
     */
    public void setNipPublic(int nipPublic) {
        nipPublic__ = nipPublic;
    }

    /**
     * <p>get NIP_EDIT value
     * @return NIP_EDIT value
     */
    public int getNipEdit() {
        return nipEdit__;
    }

    /**
     * <p>set NIP_EDIT value
     * @param nipEdit NIP_EDIT value
     */
    public void setNipEdit(int nipEdit) {
        nipEdit__ = nipEdit;
    }

    /**
     * <p>get NEX_SID value
     * @return NEX_SID value
     */
    public int getNexSid() {
        return nexSid__;
    }

    /**
     * <p>set NEX_SID value
     * @param nexSid NEX_SID value
     */
    public void setNexSid(int nexSid) {
        nexSid__ = nexSid;
    }

    /**
     * <p>get NIP_AUID value
     * @return NIP_AUID value
     */
    public int getNipAuid() {
        return nipAuid__;
    }

    /**
     * <p>set NIP_AUID value
     * @param nipAuid NIP_AUID value
     */
    public void setNipAuid(int nipAuid) {
        nipAuid__ = nipAuid;
    }

    /**
     * <p>get NIP_ADATE value
     * @return NIP_ADATE value
     */
    public UDate getNipAdate() {
        return nipAdate__;
    }

    /**
     * <p>set NIP_ADATE value
     * @param nipAdate NIP_ADATE value
     */
    public void setNipAdate(UDate nipAdate) {
        nipAdate__ = nipAdate;
    }

    /**
     * <p>get NIP_EUID value
     * @return NIP_EUID value
     */
    public int getNipEuid() {
        return nipEuid__;
    }

    /**
     * <p>set NIP_EUID value
     * @param nipEuid NIP_EUID value
     */
    public void setNipEuid(int nipEuid) {
        nipEuid__ = nipEuid;
    }

    /**
     * <p>get NIP_EDATE value
     * @return NIP_EDATE value
     */
    public UDate getNipEdate() {
        return nipEdate__;
    }

    /**
     * <p>set NIP_EDATE value
     * @param nipEdate NIP_EDATE value
     */
    public void setNipEdate(UDate nipEdate) {
        nipEdate__ = nipEdate;
    }

    /**
     * <p>get ntpUserName value
     * @return ntpUserName value
     */
    public String getntpUserName() {
        return ntpUserName__;
    }

    /**
     * <p>set ntpUserName value
     * @param ntpusername NtpUserName value
     */
    public void setNtpUserName(String ntpusername) {
        ntpUserName__ = ntpusername;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nipSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipFrTime__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipToTime__, ""));
        buf.append(",");
        buf.append(nipKadoHh__);
        buf.append(",");
        buf.append(nipKadoMm__);
        buf.append(",");
        buf.append(nipMgySid__);
        buf.append(",");
        buf.append(nanSid__);
        buf.append(",");
        buf.append(acoSid__);
        buf.append(",");
        buf.append(abaSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipTitle__, ""));
        buf.append(",");
        buf.append(nipTitleClo__);
        buf.append(",");
        buf.append(mprSid__);
        buf.append(",");
        buf.append(mkbSid__);
        buf.append(",");
        buf.append(mkhSid__);
        buf.append(",");
        buf.append(nipTieupSid__);
        buf.append(",");
        buf.append(nipKeizoku__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipActend__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipDetail__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipAssign__, ""));
        buf.append(",");
        buf.append(nipKingaku__);
        buf.append(",");
        buf.append(nipMikomi__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipSyokan__, ""));
        buf.append(",");
        buf.append(nipPublic__);
        buf.append(",");
        buf.append(nipEdit__);
        buf.append(",");
        buf.append(nexSid__);
        buf.append(",");
        buf.append(nipAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipAdate__, ""));
        buf.append(",");
        buf.append(nipEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nipEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nipActionDate を取得します。
     * @return nipActionDate
     */
    public UDate getNipActionDate() {
        return nipActionDate__;
    }

    /**
     * <p>nipActionDate をセットします。
     * @param nipActionDate nipActionDate
     */
    public void setNipActionDate(UDate nipActionDate) {
        nipActionDate__ = nipActionDate;
    }

    /**
     * <p>nipAction を取得します。
     * @return nipAction
     */
    public String getNipAction() {
        return nipAction__;
    }

    /**
     * <p>nipAction をセットします。
     * @param nipAction nipAction
     */
    public void setNipAction(String nipAction) {
        nipAction__ = nipAction;
    }

    /**
     * <p>nipActDateKbn を取得します。
     * @return nipActDateKbn
     */
    public int getNipActDateKbn() {
        return nipActDateKbn__;
    }

    /**
     * <p>nipActDateKbn をセットします。
     * @param nipActDateKbn nipActDateKbn
     */
    public void setNipActDateKbn(int nipActDateKbn) {
        nipActDateKbn__ = nipActDateKbn;
    }
    /**
    *
    * <br>[機  能] 案件が公開されていない場合に詳細情報を秘匿します。
    * <br>[解  説]
    * <br>[備  考]
    * @param gsMsg GSメッセージクラス
    */
   public void resetNoPermitAnkenView(GsMessage gsMsg) {
       if (!ankenViewable__) {
           setNipTitle(gsMsg.getMessage("ntp.ntp220.1"));
           setNanSid(-1);
           setNipMgySid(-1);
           setAcoSid(-1);
           setAbaSid(-1);
           setNipTitleClo(GSConstNippou.BGCOLOR_BLUE);
           setMprSid(-1);
           setMkbSid(-1);
           setMkhSid(-1);
           setNipMikomi(0);
           setNipSyokan("");
           setNipDetail("");
           setNipAction("");
           setNipActDateKbn(0);
       }
   }

    /**
     * <p>ankenViewable を取得します。
     * @return ankenViewable
     */
    public boolean isAnkenViewable() {
        return ankenViewable__;
    }

    /**
     * <p>ankenViewable をセットします。
     * @param ankenViewable ankenViewable
     */
    public void setAnkenViewable(boolean ankenViewable) {
        ankenViewable__ = ankenViewable;
    }
}
