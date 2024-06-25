package jp.groupsession.v2.adr.restapi.entities.query;

import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.MaxArraySize;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;

/**
 * <br>[機  能] アドレス帳 アドレス情報一覧 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class AdrEntitiesQueryPostParamModel {
    
    /** 担当者検索タイプ 担当者検索を行わない */
    public static final int TANTOSEACHTYPE_NOTSEARCH = 0;
    /** 担当者検索タイプ ユーザID検索 */
    public static final int TANTOSEACHTYPE_USER = 1;
    /** 担当者検索タイプ グループID検索 */
    public static final int TANTOSEACHTYPE_GROUP = 2;

    /** 取得件数 */
    @MinValue(1)
    @MaxValue(50)
    private int limit__ = 50;
    /** 取得開始位置 */
    @MinValue(0)
    private int offset__ = 0;
    /** ソート昇順降順 */
    @Selectable({"0","1"})
    private int sortOrderFlg__ = GSConst.ORDER_KEY_ASC;
    /** カナ順取得開始位置 */
    @MaxLength(1)
    private String kanaStartOffsetText__ = null;

    /** キーワード */
    @TextField
    @MaxLength(50)
    private String keywordText__ = null;
    /** ラベルSID */
    @MaxArraySize(50)
    private int[] labelSidArray__ = null;
    /** 氏名先頭カナ1文字 */
    @TextField
    @MaxLength(1)
    private String seiKanaStartText__ = null;
    /** 役職SID */
    private int yakusyokuSid__ = -1;
    /** 担当者検索タイプ */
    @Selectable({"0", "1", "2"})
    private int tantoSearchType__ = 0;
    /** ユーザID(担当者) */
    @TextField
    @MaxLength(256)
    private String tantoUserId__ = null;
    /** グループID(担当者) */
    @TextField
    @MaxLength(50)
    private String tantoGroupId__ = null;
    /** 企業コード */
    @TextField
    @MaxLength(20)
    private String companyId__ = null;
    /** 会社拠点SID */
    private int baseSid__ = -1;
    /** 会社名先頭カナ1文字 */
    @TextField
    @MaxLength(1)
    private String companyNameKanaStartText__ = null;
    /** 業種SID */
    private int industrySid__ = -1;
    /** 都道府県SID */
    private int todofukenSid__ = -1;

    public int getLimit() {
        return limit__;
    }

    public void setLimit(int limit) {
        limit__ = limit;
    }

    public int getOffset() {
        return offset__;
    }

    public void setOffset(int offset) {
        offset__ = offset;
    }

    public int getSortOrderFlg() {
        return sortOrderFlg__;
    }

    public void setSortOrderFlg(int sortOrderFlg) {
        sortOrderFlg__ = sortOrderFlg;
    }

    public String getKanaStartOffsetText() {
        return kanaStartOffsetText__;
    }

    public void setKanaStartOffsetText(String kanaStartOffsetText) {
        kanaStartOffsetText__ = kanaStartOffsetText;
    }

    public String getKeywordText() {
        return keywordText__;
    }

    public void setKeywordText(String keywordText) {
        keywordText__ = keywordText;
    }

    public int[] getLabelSidArray() {
        return labelSidArray__;
    }

    public void setLabelSidArray(int[] labelSidArray) {
        labelSidArray__ = labelSidArray;
    }

    public String getSeiKanaStartText() {
        return seiKanaStartText__;
    }

    public void setSeiKanaStartText(String seiKanaStartText) {
        seiKanaStartText__ = seiKanaStartText;
    }

    public int getYakusyokuSid() {
        return yakusyokuSid__;
    }

    public void setYakusyokuSid(int yakusyokuSid) {
        yakusyokuSid__ = yakusyokuSid;
    }

    public int getTantoSearchType() {
        return tantoSearchType__;
    }

    public void setTantoSearchType(int tantoSearchType) {
        tantoSearchType__ = tantoSearchType;
    }

    public String getTantoUserId() {
        return tantoUserId__;
    }

    public void setTantoUserId(String tantoUserId) {
        tantoUserId__ = tantoUserId;
    }

    public String getTantoGroupId() {
        return tantoGroupId__;
    }

    public void setTantoGroupId(String tantoGroupId) {
        tantoGroupId__ = tantoGroupId;
    }

    public String getCompanyId() {
        return companyId__;
    }

    public void setCompanyId(String companyId) {
        companyId__ = companyId;
    }

    public int getBaseSid() {
        return baseSid__;
    }

    public void setBaseSid(int baseSid) {
        baseSid__ = baseSid;
    }

    public String getCompanyNameKanaStartText() {
        return companyNameKanaStartText__;
    }

    public void setCompanyNameKanaStartText(String companyNameKanaStartText) {
        companyNameKanaStartText__ = companyNameKanaStartText;
    }

    public int getIndustrySid() {
        return industrySid__;
    }

    public void setIndustrySid(int industrySid) {
        industrySid__ = industrySid;
    }

    public int getTodofukenSid() {
        return todofukenSid__;
    }

    public void setTodofukenSid(int todofukenSid) {
        todofukenSid__ = todofukenSid;
    }

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        //カナ順取得開始位置
        if (kanaStartOffsetText__ != null
        && !GSValidateUtil.isGsWideKana(kanaStartOffsetText__)) {
            //全角カナチェック
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text",
                    "kanaStartOffsetText"
                    )
                .setParamName("kanaStartOffsetText");
        }

        //担当者検索タイプ = ユーザID検索 の場合、ユーザID(担当者)の未入力チェック
        if (tantoSearchType__ == TANTOSEACHTYPE_USER
        && StringUtil.isNullZeroString(tantoUserId__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "tantoUserId"
                    )
                .setParamName("tantoUserId");
        }

        //担当者検索タイプ = グループID検索 の場合、グループID(担当者)の未入力チェック
        if (tantoSearchType__ == TANTOSEACHTYPE_GROUP
        && StringUtil.isNullZeroString(tantoGroupId__)) {
            throw new RestApiValidateException(
                    EnumError.PARAM_REQUIRED,
                    "error.input.required.text",
                    "tantoGroupId"
                    )
                .setParamName("tantoGroupId");
        }

        //氏名先頭カナ1文字
        if (seiKanaStartText__ != null
        && !GSValidateUtil.isGsWideKana(seiKanaStartText__)) {
            //全角カナチェック
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text",
                    "seiKanaStartText"
                    )
                .setParamName("seiKanaStartText");
        }

        //会社名先頭カナ1文字
        if (companyNameKanaStartText__ != null
        && !GSValidateUtil.isGsWideKana(companyNameKanaStartText__)) {
            //全角カナチェック
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text",
                    "companyNameKanaStartText"
                    )
                .setParamName("companyNameKanaStartText");
        }
    }
}
