package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <p>FILE_CABINET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCabinetModel implements Serializable {

    /** FCB_SID mapping */
    private int fcbSid__;
    /** FCB_NAME mapping */
    private String fcbName__;
    /** FCB_ACCESS_KBN mapping */
    private int fcbAccessKbn__;
    /** FCB_CAPA_KBN mapping */
    private int fcbCapaKbn__;
    /** FCB_CAPA_SIZE mapping */
    private int fcbCapaSize__;
    /** FCB_CAPA_WARN mapping */
    private int fcbCapaWarn__;
    /** FCB_VER_KBN mapping */
    private int fcbVerKbn__;
    /** FCB_VERALL_KBN mapping */
    private int fcbVerallKbn__;
    /** FCB_BIKO mapping */
    private String fcbBiko__;
    /** FCB_SORT mapping */
    private int fcbSort__;
    /** FCB_MARK mapping */
    private Long fcbMark__ = Long.valueOf(0);
    /** FCB_AUID mapping */
    private int fcbAuid__;
    /** FCB_ADATE mapping */
    private UDate fcbAdate__;
    /** FCB_EUID mapping */
    private int fcbEuid__;
    /** FCB_EDATE mapping */
    private UDate fcbEdate__;
    /** FCB_PERSONAL_FLG mapping */
    private int fcbPersonalFlg__;
    /** FCB_OWNER_SID mapping */
    private int fcbOwnerSid__;
    /** FCB_ERRL mapping */
    private int fcbErrl__;
    /** FCB_SORT_FOLDER mapping */
    private int fcbSortFolder__;
    /** FCB_SORT_FOLDER1 mapping */
    private int fcbSortFolder1__;
    /** FCB_SORT_FOLDER2 mapping */
    private int fcbSortFolder2__;
    /** FCB_SORT_FOLDER3 mapping */
    private int fcbSortFolder3__;
    /** FCB_JKBN mapping */
    private int fcbJkbn__;

    /**
     * <p>Default Constructor
     */
    public FileCabinetModel() {
    }

    /**
     * <p>get FCB_SID value
     * @return FCB_SID value
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>set FCB_SID value
     * @param fcbSid FCB_SID value
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>get FCB_NAME value
     * @return FCB_NAME value
     */
    public String getFcbName() {
        return fcbName__;
    }

    /**
     * <p>set FCB_NAME value
     * @param fcbName FCB_NAME value
     */
    public void setFcbName(String fcbName) {
        fcbName__ = fcbName;
    }

    /**
     * <p>get FCB_ACCESS_KBN value
     * @return FCB_ACCESS_KBN value
     */
    public int getFcbAccessKbn() {
        return fcbAccessKbn__;
    }

    /**
     * <p>set FCB_ACCESS_KBN value
     * @param fcbAccessKbn FCB_ACCESS_KBN value
     */
    public void setFcbAccessKbn(int fcbAccessKbn) {
        fcbAccessKbn__ = fcbAccessKbn;
    }

    /**
     * <p>get FCB_CAPA_KBN value
     * @return FCB_CAPA_KBN value
     */
    public int getFcbCapaKbn() {
        return fcbCapaKbn__;
    }

    /**
     * <p>set FCB_CAPA_KBN value
     * @param fcbCapaKbn FCB_CAPA_KBN value
     */
    public void setFcbCapaKbn(int fcbCapaKbn) {
        fcbCapaKbn__ = fcbCapaKbn;
    }

    /**
     * <p>get FCB_CAPA_SIZE value
     * @return FCB_CAPA_SIZE value
     */
    public int getFcbCapaSize() {
        return fcbCapaSize__;
    }

    /**
     * <p>set FCB_CAPA_SIZE value
     * @param fcbCapaSize FCB_CAPA_SIZE value
     */
    public void setFcbCapaSize(int fcbCapaSize) {
        fcbCapaSize__ = fcbCapaSize;
    }

    /**
     * <p>get FCB_CAPA_WARN value
     * @return FCB_CAPA_WARN value
     */
    public int getFcbCapaWarn() {
        return fcbCapaWarn__;
    }

    /**
     * <p>set FCB_CAPA_WARN value
     * @param fcbCapaWarn FCB_CAPA_WARN value
     */
    public void setFcbCapaWarn(int fcbCapaWarn) {
        fcbCapaWarn__ = fcbCapaWarn;
    }

    /**
     * <p>get FCB_VER_KBN value
     * @return FCB_VER_KBN value
     */
    public int getFcbVerKbn() {
        return fcbVerKbn__;
    }

    /**
     * <p>set FCB_VER_KBN value
     * @param fcbVerKbn FCB_VER_KBN value
     */
    public void setFcbVerKbn(int fcbVerKbn) {
        fcbVerKbn__ = fcbVerKbn;
    }

    /**
     * <p>get FCB_VERALL_KBN value
     * @return FCB_VERALL_KBN value
     */
    public int getFcbVerallKbn() {
        return fcbVerallKbn__;
    }

    /**
     * <p>set FCB_VERALL_KBN value
     * @param fcbVerallKbn FCB_VERALL_KBN value
     */
    public void setFcbVerallKbn(int fcbVerallKbn) {
        fcbVerallKbn__ = fcbVerallKbn;
    }

    /**
     * <p>get FCB_BIKO value
     * @return FCB_BIKO value
     */
    public String getFcbBiko() {
        return fcbBiko__;
    }

    /**
     * <p>set FCB_BIKO value
     * @param fcbBiko FCB_BIKO value
     */
    public void setFcbBiko(String fcbBiko) {
        fcbBiko__ = fcbBiko;
    }

    /**
     * <p>get FCB_SORT value
     * @return FCB_SORT value
     */
    public int getFcbSort() {
        return fcbSort__;
    }

    /**
     * <p>set FCB_SORT value
     * @param fcbSort FCB_SORT value
     */
    public void setFcbSort(int fcbSort) {
        fcbSort__ = fcbSort;
    }

    /**
     * <p>get FCB_AUID value
     * @return FCB_AUID value
     */
    public int getFcbAuid() {
        return fcbAuid__;
    }

    /**
     * <p>set FCB_AUID value
     * @param fcbAuid FCB_AUID value
     */
    public void setFcbAuid(int fcbAuid) {
        fcbAuid__ = fcbAuid;
    }

    /**
     * <p>get FCB_ADATE value
     * @return FCB_ADATE value
     */
    public UDate getFcbAdate() {
        return fcbAdate__;
    }

    /**
     * <p>set FCB_ADATE value
     * @param fcbAdate FCB_ADATE value
     */
    public void setFcbAdate(UDate fcbAdate) {
        fcbAdate__ = fcbAdate;
    }

    /**
     * <p>get FCB_EUID value
     * @return FCB_EUID value
     */
    public int getFcbEuid() {
        return fcbEuid__;
    }

    /**
     * <p>set FCB_EUID value
     * @param fcbEuid FCB_EUID value
     */
    public void setFcbEuid(int fcbEuid) {
        fcbEuid__ = fcbEuid;
    }

    /**
     * <p>get FCB_EDATE value
     * @return FCB_EDATE value
     */
    public UDate getFcbEdate() {
        return fcbEdate__;
    }

    /**
     * <p>set FCB_EDATE value
     * @param fcbEdate FCB_EDATE value
     */
    public void setFcbEdate(UDate fcbEdate) {
        fcbEdate__ = fcbEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(NullDefault.getString(fcbName__, ""));
        buf.append(",");
        buf.append(fcbAccessKbn__);
        buf.append(",");
        buf.append(fcbCapaKbn__);
        buf.append(",");
        buf.append(fcbCapaSize__);
        buf.append(",");
        buf.append(fcbCapaWarn__);
        buf.append(",");
        buf.append(fcbVerKbn__);
        buf.append(",");
        buf.append(fcbVerallKbn__);
        buf.append(",");
        buf.append(fcbBiko__);
        buf.append(",");
        buf.append(fcbSort__);
        buf.append(",");
        buf.append(fcbAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fcbAdate__, ""));
        buf.append(",");
        buf.append(fcbEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fcbEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>fcbMark を取得します。
     * @return fcbMark
     */
    public Long getFcbMark() {
        return fcbMark__;
    }

    /**
     * <p>fcbMark をセットします。
     * @param fcbMark fcbMark
     */
    public void setFcbMark(Long fcbMark) {
        fcbMark__ = fcbMark;
    }

    /**
     * <p> get FCB_PERSONAL_FLG value
     * @return FCB_PERSONAL_FLG value
     * */
    public int getFcbPersonalFlg() {
        return fcbPersonalFlg__;
    }
    /**
     * <p> set FCB_PERSONAL_FLG value
     * @param fcbPersonalFlg FCB_PERSONAL_FLG value
     * */
    public void setFcbPersonalFlg(int fcbPersonalFlg) {
        fcbPersonalFlg__ = fcbPersonalFlg;
    }

    /**
     * <p> get FCB_OWNER_SID value
     * @return FCB_OWNER_SID value
     * */
    public int getFcbOwnerSid() {
        return fcbOwnerSid__;
    }
    /**
     * <p> set FCB_OWNER_SID value
     * @param fcbOwnerSid FCB_OWNER_SID value
     * */
    public void setFcbOwnerSid(int fcbOwnerSid) {
        fcbOwnerSid__ = fcbOwnerSid;
    }

    /**
     * <p>get FCB_ERRL value
     * @return FCB_ERRL value
     */
    public int getFcbErrl() {
        return fcbErrl__;
    }

    /**
     * <p>set FCB_ERRL value
     * @param fcbErrl FCB_ERRL value
     */
    public void setFcbErrl(int fcbErrl) {
        fcbErrl__ = fcbErrl;
    }

    /**
     * <p>get FCB_SORT_FOLDER value
     * @return FCB_SORT_FOLDER value
     */
    public int getFcbSortFolder() {
        return fcbSortFolder__;
    }

    /**
     * <p>set FCB_SORT_FOLDER value
     * @param fcbSortFolder FCB_SORT_FOLDER value
     */
    public void setFcbSortFolder(int fcbSortFolder) {
        fcbSortFolder__ = fcbSortFolder;
    }

    /**
     * <p>get FCB_SORT_FOLDER1 value
     * @return FCB_SORT_FOLDER1 value
     */
    public int getFcbSortFolder1() {
        return fcbSortFolder1__;
    }

    /**
     * <p>set FCB_SORT_FOLDER1 value
     * @param fcbSortFolder1 FCB_SORT_FOLDER1 value
     */
    public void setFcbSortFolder1(int fcbSortFolder1) {
        fcbSortFolder1__ = fcbSortFolder1;
    }

    /**
     * <p>get FCB_SORT_FOLDER2 value
     * @return FCB_SORT_FOLDER2 value
     */
    public int getFcbSortFolder2() {
        return fcbSortFolder2__;
    }

    /**
     * <p>set FCB_SORT_FOLDER2 value
     * @param fcbSortFolder2 FCB_SORT_FOLDER2 value
     */
    public void setFcbSortFolder2(int fcbSortFolder2) {
        fcbSortFolder2__ = fcbSortFolder2;
    }

    /**
     * <p>get FCB_SORT_FOLDER3 value
     * @return FCB_SORT_FOLDER3 value
     */
    public int getFcbSortFolder3() {
        return fcbSortFolder3__;
    }

    /**
     * <p>set FCB_SORT_FOLDER3 value
     * @param fcbSortFolder3 FCB_SORT_FOLDER3 value
     */
    public void setFcbSortFolder3(int fcbSortFolder3) {
        fcbSortFolder3__ = fcbSortFolder3;
    }

    /**
     * <p>get FCB_JKBN value
     * @return FCB_JKBN value
     */
    public int getFcbJkbn() {
        return fcbJkbn__;
    }

    /**
     * <p>set FCB_JKBN value
     * @param fcbJkbn FCB_JKBN value
     */
    public void setFcbJkbn(int fcbJkbn) {
        fcbJkbn__ = fcbJkbn;
    }

    /**
     * <p>個人キャビネットの場合、管理者設定を反映
     * @param aconf 管理者設定情報
     */
    public void setFileAconf(FileAconfModel aconf) {
        // 個人キャビネットの管理者設定(共通設定)を更新
        if (aconf != null && fcbPersonalFlg__ == GSConstFile.CABINET_KBN_PRIVATE) {
            fcbCapaKbn__   = aconf.getFacPersonalCapa();
            fcbCapaSize__  = aconf.getFacPersonalSize();
            fcbCapaWarn__  = aconf.getFacPersonalWarn();
            fcbVerKbn__    = aconf.getFacPersonalVer();
            fcbVerallKbn__ = GSConstFile.VERSION_ALL_KBN_ON;
        }
    }
}
