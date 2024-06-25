package jp.groupsession.v2.sch.restapi.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.Default;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sch.dao.SchMyviewlistDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] スケジュール検索機能パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SchEntitiesQueryParamModel {
    /** 取得件数                                         */
    @Default("50")
    private int limit__;
    /** 取得開始位置                                       */
    private int offset__ = 0;
    /** ソート1キー                                       */
    @Default("2")
    private EnumSortKey sortKeyType__;
    /** ソート1昇順降順                                         */
    @Selectable({"0", "1"})
    private int sortOrderFlg__;
    /** ソート2キー                                       */
    @Default("3")
    private EnumSortKey sortKey2Type__;
    /** ソート2昇順降順                                         */
    @Selectable({"0", "1"})
    private int sortOrder2Flg__;
    /** 検索タイプ                                        */
    @Default("0")
    private EnumScheduleSelectType selectType__;
    /** ユーザID                                        */
    @TextField
    @MaxLength(256)
    private String userId__;

    /** グループID                                       */
    @TextField
    @MaxLength(50)
    private String groupId__;
    /** マイグループSID                                        */
    private Integer myGroupSid__;
    /** スケジュールリストSID                                         */
    private Integer listSid__;
    /** 開始日FROM                                      */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate startFromDate__;
    {
        startFromDate__ = new UDate();
        startFromDate__.setZeroDdHhMmSs();
    }
    /** 開始日TO                                        */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate startToDate__;
    {
        startToDate__ = new UDate();
        startToDate__.addYear(1);
        startToDate__.setMaxHhMmSs();
    }
    /** 終了日FROM                                      */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate endFromDate__;
    {
        endFromDate__ = new UDate();
        endFromDate__.setZeroDdHhMmSs();
    }
    /** 終了日TO                                        */
    @UDateFormat(Format.DATE_SLUSH)
    private UDate endToDate__;
    {
        endToDate__ = new UDate();
        endToDate__.addYear(1);
        endToDate__.setMaxHhMmSs();
    }
    /** 同時登録取得フラグ                                        */
    @Selectable({"0", "1"})
    private int sameInputFlg__ = 0;
    /** キーワード                                        */
    @MaxLength(50)
    @TextField
    private String keywordText__;
    /** キーワード検索対象                                        */
    private EnumKeywordTarget keywordTargetType__;
    /** キーワード接続方法                                        */
    @Selectable({"0", "1"})
    private Integer keywordJunctionFlg__ = 0;
    /** 文字色*/
    @MaxLength(10)
    private int[] colorTypeArray__;

    /**
     * <p>limit を取得します。
     * @return limit
     * @see SchEntitiesQueryParamModel#limit__
     */
    public Integer getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     * @see SchEntitiesQueryParamModel#limit__
     */
    public void setLimit(Integer limit) {
        limit__ = limit;
    }
    /**
     * <p>offset を取得します。
     * @return offset
     * @see SchEntitiesQueryParamModel#offset__
     */
    public Integer getOffset() {
        return offset__;
    }
    /**
     * <p>offset をセットします。
     * @param offset offset
     * @see SchEntitiesQueryParamModel#offset__
     */
    public void setOffset(Integer offset) {
        offset__ = offset;
    }
    /**
     * <p>sortKeyType を取得します。
     * @return sortKeyType
     * @see SchEntitiesQueryParamModel#sortKeyType__
     */
    public EnumSortKey getSortKeyType() {
        return sortKeyType__;
    }
    /**
     * <p>sortKeyType をセットします。
     * @param sortKeyType sortKeyType
     * @see SchEntitiesQueryParamModel#sortKeyType__
     */
    public void setSortKeyType(EnumSortKey sortKeyType) {
        sortKeyType__ = sortKeyType;
    }
    /**
     * <p>sortOrderFlg を取得します。
     * @return sortOrderFlg
     * @see SchEntitiesQueryParamModel#sortOrderFlg__
     */
    public Integer getSortOrderFlg() {
        return sortOrderFlg__;
    }
    /**
     * <p>sortOrderFlg をセットします。
     * @param sortOrderFlg sortOrderFlg
     * @see SchEntitiesQueryParamModel#sortOrderFlg__
     */
    public void setSortOrderFlg(Integer sortOrderFlg) {
        sortOrderFlg__ = sortOrderFlg;
    }
    /**
     * <p>sortKey2Type を取得します。
     * @return sortKey2Type
     * @see SchEntitiesQueryParamModel#sortKey2Type__
     */
    public EnumSortKey getSortKey2Type() {
        return sortKey2Type__;
    }
    /**
     * <p>sortKey2Type をセットします。
     * @param sortKey2Type sortKey2Type
     * @see SchEntitiesQueryParamModel#sortKey2Type__
     */
    public void setSortKey2Type(EnumSortKey sortKey2Type) {
        sortKey2Type__ = sortKey2Type;
    }
    /**
     * <p>sortOrder2Flg を取得します。
     * @return sortOrder2Flg
     * @see SchEntitiesQueryParamModel#sortOrder2Flg__
     */
    public Integer getSortOrder2Flg() {
        return sortOrder2Flg__;
    }
    /**
     * <p>sortOrder2Flg をセットします。
     * @param sortOrder2Flg sortOrder2Flg
     * @see SchEntitiesQueryParamModel#sortOrder2Flg__
     */
    public void setSortOrder2Flg(Integer sortOrder2Flg) {
        sortOrder2Flg__ = sortOrder2Flg;
    }
    /**
     * <p>selectType を取得します。
     * @return selectType
     * @see SchEntitiesQueryParamModel#selectType__
     */
    public EnumScheduleSelectType getSelectType() {
        return selectType__;
    }
    /**
     * <p>selectType をセットします。
     * @param selectType selectType
     * @see SchEntitiesQueryParamModel#selectType__
     */
    public void setSelectType(EnumScheduleSelectType selectType) {
        selectType__ = selectType;
    }
    /**
     * <p>usrerId を取得します。
     * @return usrerId
     * @see SchEntitiesQueryParamModel#usrerId__
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>usrerId をセットします。
     * @param userId userId
     * @see SchEntitiesQueryParamModel#userId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see SchEntitiesQueryParamModel#groupId__
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see SchEntitiesQueryParamModel#groupId__
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    /**
     * <p>myGroupSid を取得します。
     * @return myGroupSid
     * @see SchEntitiesQueryParamModel#myGroupSid__
     */
    public Integer getMyGroupSid() {
        return myGroupSid__;
    }
    /**
     * <p>myGroupSid をセットします。
     * @param myGroupSid myGroupSid
     * @see SchEntitiesQueryParamModel#myGroupSid__
     */
    public void setMyGroupSid(Integer myGroupSid) {
        myGroupSid__ = myGroupSid;
    }
    /**
     * <p>listSid を取得します。
     * @return listSid
     * @see SchEntitiesQueryParamModel#listSid__
     */
    public Integer getListSid() {
        return listSid__;
    }
    /**
     * <p>listSid をセットします。
     * @param listSid listSid
     * @see SchEntitiesQueryParamModel#listSid__
     */
    public void setListSid(Integer listSid) {
        listSid__ = listSid;
    }
    /**
     * <p>startFromDate を取得します。
     * @return startFromDate
     * @see SchEntitiesQueryParamModel#startFromDate__
     */
    public UDate getStartFromDate() {
        return startFromDate__;
    }
    /**
     * <p>startFromDate をセットします。
     * @param startFromDate startFromDate
     * @see SchEntitiesQueryParamModel#startFromDate__
     */
    public void setStartFromDate(UDate startFromDate) {
        startFromDate__ = startFromDate;
    }
    /**
     * <p>startToDate を取得します。
     * @return startToDate
     * @see SchEntitiesQueryParamModel#startToDate__
     */
    public UDate getStartToDate() {
        return startToDate__;
    }
    /**
     * <p>startToDate をセットします。
     * @param startToDate startToDate
     * @see SchEntitiesQueryParamModel#startToDate__
     */
    public void setStartToDate(UDate startToDate) {
        startToDate__ = startToDate;
    }
    /**
     * <p>endFromDate を取得します。
     * @return endFromDate
     * @see SchEntitiesQueryParamModel#endFromDate__
     */
    public UDate getEndFromDate() {
        return endFromDate__;
    }
    /**
     * <p>endFromDate をセットします。
     * @param endFromDate endFromDate
     * @see SchEntitiesQueryParamModel#endFromDate__
     */
    public void setEndFromDate(UDate endFromDate) {
        endFromDate__ = endFromDate;
    }
    /**
     * <p>endToDate を取得します。
     * @return endToDate
     * @see SchEntitiesQueryParamModel#endToDate__
     */
    public UDate getEndToDate() {
        return endToDate__;
    }
    /**
     * <p>endToDate をセットします。
     * @param endToDate endToDate
     * @see SchEntitiesQueryParamModel#endToDate__
     */
    public void setEndToDate(UDate endToDate) {
        endToDate__ = endToDate;
    }
    /**
     * <p>sameInputFlg を取得します。
     * @return sameInputFlg
     * @see SchEntitiesQueryParamModel#sameInputFlg__
     */
    public int getSameInputFlg() {
        return sameInputFlg__;
    }
    /**
     * <p>sameInputFlg をセットします。
     * @param sameInputFlg sameInputFlg
     * @see SchEntitiesQueryParamModel#sameInputFlg__
     */
    public void setSameInputFlg(int sameInputFlg) {
        sameInputFlg__ = sameInputFlg;
    }
    /**
     * <p>keywordText を取得します。
     * @return keywordText
     * @see SchEntitiesQueryParamModel#keywordText__
     */
    public String getKeywordText() {
        return keywordText__;
    }
    /**
     * <p>keywordText をセットします。
     * @param keywordText keywordText
     * @see SchEntitiesQueryParamModel#keywordText__
     */
    public void setKeywordText(String keywordText) {
        keywordText__ = keywordText;
    }
    /**
     * <p>keywardTargetType を取得します。
     * @return keywardTargetType
     * @see SchEntitiesQueryParamModel#keywordTargetType__
     */
    public EnumKeywordTarget getKeywordTargetType() {
        return keywordTargetType__;
    }
    /**
     * <p>keywardTargetType をセットします。
     * @param keywardTargetType keywardTargetType
     * @see SchEntitiesQueryParamModel#keywordTargetType__
     */
    public void setKeywordTargetType(EnumKeywordTarget keywardTargetType) {
        keywordTargetType__ = keywardTargetType;
    }
    /**
     * <p>keywardJunctionFlg を取得します。
     * @return keywardJunctionFlg
     * @see SchEntitiesQueryParamModel#keywordJunctionFlg__
     */
    public Integer getKeywordJunctionFlg() {
        return keywordJunctionFlg__;
    }
    /**
     * <p>keywardJunctionFlg をセットします。
     * @param keywardJunctionFlg keywardJunctionFlg
     * @see SchEntitiesQueryParamModel#keywordJunctionFlg__
     */
    public void setKeywordJunctionFlg(Integer keywardJunctionFlg) {
        keywordJunctionFlg__ = keywardJunctionFlg;
    }
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();
        if (selectType__ == EnumScheduleSelectType.USER
                || selectType__ == EnumScheduleSelectType.USER_WITH_BELONGGROUP
                ) {
            if (StringUtil.isNullZeroString(userId__)) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.input.required.text",
                        "userId")
                    .setParamName("userId"));

            }
        }
        if (getUserId() != null) {
            AuthDao adao = new AuthDao(ctx.getCon());
            BaseUserModel smodel = null;
            try {
                smodel = adao.selectLoginNoPwd(getUserId(), null);
            } catch (SQLException e) {
                throw new RuntimeException("SQL実行エラー", e);
            }
            if (smodel == null) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.user")));
            }

        }
        if (selectType__ == EnumScheduleSelectType.GROUP
                || selectType__ == EnumScheduleSelectType.GROUP_WITH_MENBER
                ) {
            if (StringUtil.isNullZeroString(groupId__)) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.input.required.text",
                        "groupId")
                    .setParamName("groupId"));

            }

        }
        if (getGroupId() != null) {
            CmnGroupmModel grp = new CmnGroupmDao(ctx.getCon())
            .getGroupInf(getGroupId());
            if (grp == null) {
                throw new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.group"));
            }

        }
        if (selectType__ == EnumScheduleSelectType.MYGROUPMEMBER) {
            if (myGroupSid__ == null) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.input.required.text",
                        "myGroupSid")
                    .setParamName("myGroupSid"));

            }
        }
        if (myGroupSid__ != null) {
            CmnMyGroupDao mgDao = new CmnMyGroupDao(ctx.getCon());
            if (mgDao.selectShareMyGroup(myGroupSid__, ctx.getRequestUserSid()) == null) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.mygroup"))
                    .setParamName("myGroupSid"));

            }
        }

        if (selectType__ == EnumScheduleSelectType.SCHEDULELIST) {
            if (listSid__ == null) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_REQUIRED,
                        "error.input.required.text",
                        "listSid")
                    .setParamName("listSid"));

            }
        }
        if (listSid__ != null) {
            SchMyviewlistDao myViewListDao = new SchMyviewlistDao(ctx.getCon());
            if (myViewListDao.select(listSid__, ctx.getRequestUserSid()) == null) {
                valErr.add(new RestApiValidateException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel())
                        .getMessage("schedule.sch260.04"))
                    .setParamName("listSid"));
            }

        }
        //開始 大小
        if (startFromDate__ != null
                && startToDate__ != null
                && startFromDate__.compare(
                        startToDate__,
                        startFromDate__) == UDate.LARGE) {
            valErr.add(new RestApiValidateException(
                    EnumError.PARAM_COLLABORATION,
                    "error.input.date.over",
                    "startFromDate",
                    "startToDate")
                .setParamName("startFromDate",
                        "startToDate"));
        }
        //開始 大小
        if (endFromDate__ != null
                && endToDate__ != null
                && endFromDate__.compare(
                        endToDate__,
                        endFromDate__) == UDate.LARGE) {
            valErr.add(new RestApiValidateException(
                    EnumError.PARAM_COLLABORATION,
                    "error.input.date.over",
                    "endFromDate",
                    "endToDate")
                .setParamName("endFromDate",
                        "endToDate"));
        }
        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }

    }
    /**
     * <p>colorType を取得します。
     * @return colorType
     * @see SchEntitiesQueryParamModel#colorType__
     */
    public int[] getColorTypeArray() {
        return colorTypeArray__;
    }
    /**
     * <p>colorType をセットします。
     * @param colorType colorType
     * @see SchEntitiesQueryParamModel#colorType__
     */
    public void setColorTypeArray(int[] colorType) {
        colorTypeArray__ = colorType;
    }
}
