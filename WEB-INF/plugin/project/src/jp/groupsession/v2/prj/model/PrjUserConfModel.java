package jp.groupsession.v2.prj.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <p>PRJ_USER_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjUserConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** PUC_PRJ_CNT mapping */
    private int pucPrjCnt__;
    /** PUC_TODO_CNT mapping */
    private int pucTodoCnt__;
    /** PUC_AUID mapping */
    private int pucAuid__;
    /** PUC_ADATE mapping */
    private UDate pucAdate__;
    /** PUC_EUID mapping */
    private int pucEuid__;
    /** PUC_EDATE mapping */
    private UDate pucEdate__;

    /** PUC_TODO_DATE mapping */
    private int pucTodoDate__;
    /** PUC_TODO_PROJECT mappign */
    private int pucTodoProject__;
    /** PUC_TODO_STATUS mapping */
    private int pucTodoStatus__;
    /** PUC_PRJ_PROJECT mapping */
    private int pucPrjProject__;

    /** PUC_MAIN_DATE mapping */
    private int pucMainDate__;
    /** PUC_MAIN_STATUS mapping */
    private int pucMainStatus__;
    /** PUC_MAIN_MENBER mapping */
    private int pucMainMember__;

    /** PUC_DEF_DSP mapping */
    private int pucDefDsp__;

    /** PUC_SCH_KBN mapping */
    private int pucSchKbn__;

    /** PUC_TODO_DSP mapping */
    private int pucTodoDsp__;

    /**
     * <p>pucSchKbn を取得します。
     * @return pucSchKbn
     */
    public int getPucSchKbn() {
        return pucSchKbn__;
    }

    /**
     * <p>pucSchKbn をセットします。
     * @param pucSchKbn pucSchKbn
     */
    public void setPucSchKbn(int pucSchKbn) {
        pucSchKbn__ = pucSchKbn;
    }

    /**
     * <p>pucDefDsp を取得します。
     * @return pucDefDsp
     */
    public int getPucDefDsp() {
        return pucDefDsp__;
    }

    /**
     * <p>pucDefDsp をセットします。
     * @param pucDefDsp pucDefDsp
     */
    public void setPucDefDsp(int pucDefDsp) {
        pucDefDsp__ = pucDefDsp;
    }

    /**
     * <p>Default Constructor
     */
    public PrjUserConfModel() {
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
     * <p>get PUC_PRJ_CNT value
     * @return PUC_PRJ_CNT value
     */
    public int getPucPrjCnt() {
        return pucPrjCnt__;
    }

    /**
     * <p>set PUC_PRJ_CNT value
     * @param pucPrjCnt PUC_PRJ_CNT value
     */
    public void setPucPrjCnt(int pucPrjCnt) {
        pucPrjCnt__ = pucPrjCnt;
    }

    /**
     * <p>get PUC_TODO_CNT value
     * @return PUC_TODO_CNT value
     */
    public int getPucTodoCnt() {
        return pucTodoCnt__;
    }

    /**
     * <p>set PUC_TODO_CNT value
     * @param pucTodoCnt PUC_TODO_CNT value
     */
    public void setPucTodoCnt(int pucTodoCnt) {
        pucTodoCnt__ = pucTodoCnt;
    }

    /**
     * <p>get PUC_AUID value
     * @return PUC_AUID value
     */
    public int getPucAuid() {
        return pucAuid__;
    }

    /**
     * <p>set PUC_AUID value
     * @param pucAuid PUC_AUID value
     */
    public void setPucAuid(int pucAuid) {
        pucAuid__ = pucAuid;
    }

    /**
     * <p>get PUC_ADATE value
     * @return PUC_ADATE value
     */
    public UDate getPucAdate() {
        return pucAdate__;
    }

    /**
     * <p>set PUC_ADATE value
     * @param pucAdate PUC_ADATE value
     */
    public void setPucAdate(UDate pucAdate) {
        pucAdate__ = pucAdate;
    }

    /**
     * <p>get PUC_EUID value
     * @return PUC_EUID value
     */
    public int getPucEuid() {
        return pucEuid__;
    }

    /**
     * <p>set PUC_EUID value
     * @param pucEuid PUC_EUID value
     */
    public void setPucEuid(int pucEuid) {
        pucEuid__ = pucEuid;
    }

    /**
     * <p>get PUC_EDATE value
     * @return PUC_EDATE value
     */
    public UDate getPucEdate() {
        return pucEdate__;
    }

    /**
     * <p>set PUC_EDATE value
     * @param pucEdate PUC_EDATE value
     */
    public void setPucEdate(UDate pucEdate) {
        pucEdate__ = pucEdate;
    }

    /**
     * <p>pucTodoDate を取得します。
     * @return pucTodoDate
     */
    public int getPucTodoDate() {
        return pucTodoDate__;
    }

    /**
     * <p>pucTodoDate をセットします。
     * @param pucTodoDate pucTodoDate
     */
    public void setPucTodoDate(int pucTodoDate) {
        pucTodoDate__ = pucTodoDate;
    }

    /**
     * <p>pucTodoProject を取得します。
     * @return pucTodoProject
     */
    public int getPucTodoProject() {
        return pucTodoProject__;
    }

    /**
     * <p>pucTodoProject をセットします。
     * @param pucTodoProject pucTodoProject
     */
    public void setPucTodoProject(int pucTodoProject) {
        pucTodoProject__ = pucTodoProject;
    }

    /**
     * <p>pucTodoStatus を取得します。
     * @return pucTodoStatus
     */
    public int getPucTodoStatus() {
        return pucTodoStatus__;
    }

    /**
     * <p>pucTodoStatus をセットします。
     * @param pucTodoStatus pucTodoStatus
     */
    public void setPucTodoStatus(int pucTodoStatus) {
        pucTodoStatus__ = pucTodoStatus;
    }

    /**
     * <p>pucPrjProject を取得します。
     * @return pucPrjProject
     */
    public int getPucPrjProject() {
        return pucPrjProject__;
    }

    /**
     * <p>pucPrjProject をセットします。
     * @param pucPrjProject pucPrjProject
     */
    public void setPucPrjProject(int pucPrjProject) {
        pucPrjProject__ = pucPrjProject;
    }

    /**
     * <p>pucMainDate を取得します。
     * @return pucMainDate
     */
    public int getPucMainDate() {
        return pucMainDate__;
    }

    /**
     * <p>pucMainDate をセットします。
     * @param pucMainDate pucMainDate
     */
    public void setPucMainDate(int pucMainDate) {
        pucMainDate__ = pucMainDate;
    }

    /**
     * <p>pucMainStatus を取得します。
     * @return pucMainStatus
     */
    public int getPucMainStatus() {
        return pucMainStatus__;
    }

    /**
     * <p>pucMainStatus をセットします。
     * @param pucMainStatus pucMainStatus
     */
    public void setPucMainStatus(int pucMainStatus) {
        pucMainStatus__ = pucMainStatus;
    }

    /**
     * <p>pucMainMember を取得します。
     * @return pucMainMember
     */
    public int getPucMainMember() {
        return pucMainMember__;
    }

    /**
     * <p>pucMainMember をセットします。
     * @param pucMainMember pucMainMember
     */
    public void setPucMainMember(int pucMainMember) {
        pucMainMember__ = pucMainMember;
    }

    /**
     * <p>pucTodoDsp を取得します。
     * @return pucTodoDsp
     */
    public int getPucTodoDsp() {
        return pucTodoDsp__;
    }

    /**
     * <p>pucTodoDsp をセットします。
     * @param pucTodoDsp pucTodoDsp
     */
    public void setPucTodoDsp(int pucTodoDsp) {
        pucTodoDsp__ = pucTodoDsp;
    }
}