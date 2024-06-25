package jp.groupsession.v2.ip.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] IP管理 ネットワーク情報の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkSearchModel extends AbstractModel {

    /** ネットワークSID */
    private int netSid__;
    /** 非公開ネットワークSIDリスト */
    private ArrayList <Integer> notDspNetSidList__;
    /** ネットワークアドレス */
    private String netad__;
    /** サブネットマスク */
    private String subnet__;
    /** IPアドレスSID */
    private int iadSid__;
    /** IPアドレス */
    private String ipad__;
    /** マシン名 */
    private String iadName__;
    /** 使用状況 */
    private String useKbn__;
    /** 管理者SID */
    private int admUsrSid__;
    /** 管理者グループSID */
    private int admGrpSid__;
    /** 管理者リスト */
    private ArrayList <IpkAddAdmModel> iadAdmList__;
    /** コメント */
    private String iadMsg__;
    /** オーダーキー1 */
    private int orderKey1__;
    /** ソートキー1 */
    private int sortKey1__;
    /** オーダーキー2 */
    private int orderKey2__;
    /** ソートキー2 */
    private int sortKey2__;
    /** オフセット */
    private int offset__;
    /** 1ページ表示件数 */
    private int limit__;
    /** キーワードリスト */
    private String[] keywordList__;
    /** キーワード検索条件 */
    private String keywordKbn__;
    /** キーワード検索対象 */
    private String[] searchTarget__;
    /** CPU名 */
    private String cpuName__;
    /** メモリ名 */
    private String memoryName__;
    /** HD名 */
    private String hdName__;
    /** CPUSID */
    private int iadCpu__;
    /** メモリSID */
    private int iadMemory__;
    /** HDSID */
    private int iadHd__;

    /**
     * <p>iadCpu を取得します。
     * @return iadCpu
     */
    public int getIadCpu() {
        return iadCpu__;
    }
    /**
     * <p>iadCpu をセットします。
     * @param iadCpu iadCpu
     */
    public void setIadCpu(int iadCpu) {
        iadCpu__ = iadCpu;
    }
    /**
     * <p>iadHd を取得します。
     * @return iadHd
     */
    public int getIadHd() {
        return iadHd__;
    }
    /**
     * <p>iadHd をセットします。
     * @param iadHd iadHd
     */
    public void setIadHd(int iadHd) {
        iadHd__ = iadHd;
    }
    /**
     * <p>iadMemory を取得します。
     * @return iadMemory
     */
    public int getIadMemory() {
        return iadMemory__;
    }
    /**
     * <p>iadMemory をセットします。
     * @param iadMemory iadMemory
     */
    public void setIadMemory(int iadMemory) {
        iadMemory__ = iadMemory;
    }
    /**
     * <p>orderKey1 を取得します。
     * @return orderKey1
     */
    public int getOrderKey1() {
        return orderKey1__;
    }
    /**
     * <p>orderKey1 をセットします。
     * @param orderKey1 orderKey1
     */
    public void setOrderKey1(int orderKey1) {
        orderKey1__ = orderKey1;
    }
    /**
     * <p>orderKey2 を取得します。
     * @return orderKey2
     */
    public int getOrderKey2() {
        return orderKey2__;
    }
    /**
     * <p>orderKey2 をセットします。
     * @param orderKey2 orderKey2
     */
    public void setOrderKey2(int orderKey2) {
        orderKey2__ = orderKey2;
    }
    /**
     * <p>sortKey1 を取得します。
     * @return sortKey1
     */
    public int getSortKey1() {
        return sortKey1__;
    }
    /**
     * <p>sortKey1 をセットします。
     * @param sortKey1 sortKey1
     */
    public void setSortKey1(int sortKey1) {
        sortKey1__ = sortKey1;
    }
    /**
     * <p>sortKey2 を取得します。
     * @return sortKey2
     */
    public int getSortKey2() {
        return sortKey2__;
    }
    /**
     * <p>sortKey2 をセットします。
     * @param sortKey2 sortKey2
     */
    public void setSortKey2(int sortKey2) {
        sortKey2__ = sortKey2;
    }
    /**
     * <p>iadAdmList を取得します。
     * @return iadAdmList
     */
    public ArrayList<IpkAddAdmModel> getIadAdmList() {
        return iadAdmList__;
    }
    /**
     * <p>iadAdmList をセットします。
     * @param iadAdmList iadAdmList
     */
    public void setIadAdmList(ArrayList<IpkAddAdmModel> iadAdmList) {
        iadAdmList__ = iadAdmList;
    }
    /**
     * <p>iadMsg を取得します。
     * @return iadMsg
     */
    public String getIadMsg() {
        return iadMsg__;
    }
    /**
     * <p>iadMsg をセットします。
     * @param iadMsg iadMsg
     */
    public void setIadMsg(String iadMsg) {
        iadMsg__ = iadMsg;
    }
    /**
     * <p>iadName を取得します。
     * @return iadName
     */
    public String getIadName() {
        return iadName__;
    }
    /**
     * <p>iadName をセットします。
     * @param iadName iadName
     */
    public void setIadName(String iadName) {
        iadName__ = iadName;
    }

    /**
     * <p>netad を取得します。
     * @return netad
     */
    public String getNetad() {
        return netad__;
    }
    /**
     * <p>netad をセットします。
     * @param netad netad
     */
    public void setNetad(String netad) {
        netad__ = netad;
    }
    /**
     * <p>subnet を取得します。
     * @return subnet
     */
    public String getSubnet() {
        return subnet__;
    }
    /**
     * <p>subnet をセットします。
     * @param subnet subnet
     */
    public void setSubnet(String subnet) {
        subnet__ = subnet;
    }

    /**
     * <p>admUsrSid を取得します。
     * @return admUsrSid
     */
    public int getAdmUsrSid() {
        return admUsrSid__;
    }
    /**
     * <p>admUsrSid をセットします。
     * @param admUsrSid admUsrSid
     */
    public void setAdmUsrSid(int admUsrSid) {
        admUsrSid__ = admUsrSid;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public int getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(int netSid) {
        netSid__ = netSid;
    }
    /**
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
    /**
     * <p>offset を取得します。
     * @return offset
     */
    public int getOffset() {
        return offset__;
    }
    /**
     * <p>offset をセットします。
     * @param offset offset
     */
    public void setOffset(int offset) {
        offset__ = offset;
    }
    /**
     * <p>ipad を取得します。
     * @return ipad
     */
    public String getIpad() {
        return ipad__;
    }
    /**
     * <p>ipad をセットします。
     * @param ipad ipad
     */
    public void setIpad(String ipad) {
        ipad__ = ipad;
    }
    /**
     * <p>useKbn を取得します。
     * @return useKbn
     */
    public String getUseKbn() {
        return useKbn__;
    }
    /**
     * <p>useKbn をセットします。
     * @param useKbn useKbn
     */
    public void setUseKbn(String useKbn) {
        useKbn__ = useKbn;
    }
    /**
     * <p>keywordKbn を取得します。
     * @return keywordKbn
     */
    public String getKeywordKbn() {
        return keywordKbn__;
    }
    /**
     * <p>keywordKbn をセットします。
     * @param keywordKbn keywordKbn
     */
    public void setKeywordKbn(String keywordKbn) {
        keywordKbn__ = keywordKbn;
    }
    /**
     * <p>searchTarget を取得します。
     * @return searchTarget
     */
    public String[] getSearchTarget() {
        return searchTarget__;
    }
    /**
     * <p>searchTarget をセットします。
     * @param searchTarget searchTarget
     */
    public void setSearchTarget(String[] searchTarget) {
        searchTarget__ = searchTarget;
    }
    /**
     * <p>keywordList を取得します。
     * @return keywordList
     */
    public String[] getKeywordList() {
        return keywordList__;
    }
    /**
     * <p>keywordList をセットします。
     * @param keywordList keywordList
     */
    public void setKeywordList(String[] keywordList) {
        keywordList__ = keywordList;
    }
    /**
     * <p>cpuName を取得します。
     * @return cpuName
     */
    public String getCpuName() {
        return cpuName__;
    }
    /**
     * <p>cpuName をセットします。
     * @param cpuName cpuName
     */
    public void setCpuName(String cpuName) {
        cpuName__ = cpuName;
    }
    /**
     * <p>hdName を取得します。
     * @return hdName
     */
    public String getHdName() {
        return hdName__;
    }
    /**
     * <p>hdName をセットします。
     * @param hdName hdName
     */
    public void setHdName(String hdName) {
        hdName__ = hdName;
    }
    /**
     * <p>memoryName を取得します。
     * @return memoryName
     */
    public String getMemoryName() {
        return memoryName__;
    }
    /**
     * <p>memoryName をセットします。
     * @param memoryName memoryName
     */
    public void setMemoryName(String memoryName) {
        memoryName__ = memoryName;
    }
    /**
     * <p>iadSid を取得します。
     * @return iadSid
     */
    public int getIadSid() {
        return iadSid__;
    }
    /**
     * <p>iadSid をセットします。
     * @param iadSid iadSid
     */
    public void setIadSid(int iadSid) {
        iadSid__ = iadSid;
    }
    /**
     * <p>admGrpSid を取得します。
     * @return admGrpSid
     */
    public int getAdmGrpSid() {
        return admGrpSid__;
    }
    /**
     * <p>admGrpSid をセットします。
     * @param admGrpSid admGrpSid
     */
    public void setAdmGrpSid(int admGrpSid) {
        admGrpSid__ = admGrpSid;
    }
    /**
     * <p>notDspNetSidList を取得します。
     * @return notDspNetSidList
     */
    public ArrayList<Integer> getNotDspNetSidList() {
        return notDspNetSidList__;
    }
    /**
     * <p>notDspNetSidList をセットします。
     * @param notDspNetSidList notDspNetSidList
     */
    public void setNotDspNetSidList(ArrayList<Integer> notDspNetSidList) {
        notDspNetSidList__ = notDspNetSidList;
    }

}