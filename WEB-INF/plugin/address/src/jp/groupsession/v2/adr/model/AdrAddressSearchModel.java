package jp.groupsession.v2.adr.model;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス情報検索時の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrAddressSearchModel extends AbstractModel {

    /** 並び順 */
    private int orderKey__ = GSConst.ORDER_KEY_ASC;

    /** カナ順取得開始位置 */
    private String kanaStartOffset__ = null;

    /** 検索キーワード */
    private String keyword__ = null;
    /** ラベルSID */
    private int[] label__ = null;

    /** 氏名カナ 先頭1文字 */
    private String unameKnHead__ = null;
    /** 役職SID */
    private int position__ = -1;

    /** ユーザID */
    private String userId__ = null;
    /** グループID */
    private String groupId__ = null;

    /** 企業コード */
    private String coCode__ = null;
    /** 会社拠点SID */
    private Integer companyBaseSid__ = null;
    /** 会社名カナ 先頭1文字 */
    private String cnameKnHead__ = null;

    /** 業種SID */
    private int atiSid__ = -1;
    /** 都道府県SID */
    private int tdfk__ = -1;

    /** 検索開始位置 */
    private int offset__ = 0;
    /** 取得最大件数 */
    private int limit__ = 0;

    public int getOrderKey() {
        return orderKey__;
    }
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }
    public String getKanaStartOffset() {
        return kanaStartOffset__;
    }
    public void setKanaStartOffset(String kanaStartOffset) {
        kanaStartOffset__ = kanaStartOffset;
    }
    public String getKeyword() {
        return keyword__;
    }
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    public int[] getLabel() {
        return label__;
    }
    public void setLabel(int[] label) {
        label__ = label;
    }
    public String getUnameKnHead() {
        return unameKnHead__;
    }
    public void setUnameKnHead(String unameKnHead) {
        unameKnHead__ = unameKnHead;
    }
    public int getPosition() {
        return position__;
    }
    public void setPosition(int position) {
        position__ = position;
    }
    public String getUserId() {
        return userId__;
    }
    public void setUserId(String userId) {
        userId__ = userId;
    }
    public String getGroupId() {
        return groupId__;
    }
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    public String getCoCode() {
        return coCode__;
    }
    public void setCoCode(String coCode) {
        coCode__ = coCode;
    }
    public Integer getCompanyBaseSid() {
        return companyBaseSid__;
    }
    public void setCompanyBaseSid(Integer companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
    }
    public String getCnameKnHead() {
        return cnameKnHead__;
    }
    public void setCnameKnHead(String cnameKnHead) {
        cnameKnHead__ = cnameKnHead;
    }
    public int getAtiSid() {
        return atiSid__;
    }
    public void setAtiSid(int atiSid) {
        atiSid__ = atiSid;
    }
    public int getTdfk() {
        return tdfk__;
    }
    public void setTdfk(int tdfk) {
        tdfk__ = tdfk;
    }
    public int getOffset() {
        return offset__;
    }
    public void setOffset(int offset) {
        offset__ = offset;
    }
    public int getLimit() {
        return limit__;
    }
    public void setLimit(int limit) {
        limit__ = limit;
    }

}