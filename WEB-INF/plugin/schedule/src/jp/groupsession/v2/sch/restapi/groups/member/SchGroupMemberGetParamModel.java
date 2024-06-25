package jp.groupsession.v2.sch.restapi.groups.member;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz;
import jp.groupsession.v2.sch.restapi.groups.EnumGroupType;
import jp.groupsession.v2.struts.msg.GsMessage;

@ParamModel
public class SchGroupMemberGetParamModel {

    /** グループ区分*/
    @NotBlank
    @Selectable({
        EnumGroupType.GROUP_VALUE_STRING,
        EnumGroupType.MYGROUP_VALUE_STRING,
        EnumGroupType.SCHEDULELIST_VALUE_STRING})
    private String groupType__;
    
    /** グループID*/
    @NotBlank
    private String groupId__;
    
    /** グループSID*/
    private Integer groupSid__;

    /** limit */
    @MinValue(1)
    @MaxValue(100)
    private Integer limit__ = 50;

    /** offset */
    @MinValue(0)
    private Integer offset__ = 0;

    /**
     * <p>groupType を取得します。
     * @return groupType
     * @see groupType__
     */
    public String getGroupType() {
        return groupType__;
    }

    /**
     * <p>groupType をセットします。
     * @param groupType groupType
     * @see groupType__
     */
    public void setGroupType(String groupType) {
        groupType__ = groupType;
    }

    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see groupId__
     */
    public String getGroupId() {
        return groupId__;
    }

    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see groupId__
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }

    /**
     * <p>groupSid を取得します。
     * @return groupSid
     * @see groupSid__
     */
    public Integer getGroupSid() {
        return groupSid__;
    }

    /**
     * <p>groupSid をセットします。
     * @param groupSid groupSid
     * @see groupSid__
     */
    public void setGroupSid(Integer groupSid) {
        groupSid__ = groupSid;
    }

    public Integer getLimit() {
        return limit__;
    }

    public void setLimit(Integer limit) {
        limit__ = limit;
    }

    public Integer getOffset() {
        return offset__;
    }

    public void setOffset(Integer offset) {
        offset__ = offset;
    }

    @Validator
    public void validate(RestApiContext ctx) throws SQLException {

        SchGroupMemberGetExeCash cash = SchGroupMemberGetExeCash.getInstance();
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());

        //グループ区分毎の値初期化
        if (!Objects.equals(getGroupType(), EnumGroupType.GROUP_VALUE_STRING)) {
            if (!NumberUtils.isNumber(String.valueOf(getGroupId()))) {
                RestApiValidateException ve =
                        new RestApiValidateException(
                                EnumError.PARAM_FORMAT,
                                "error.input.number.text", "groupSid");
                ve.setParamName("groupSid");
                throw ve;
            }

            setGroupSid(Integer.parseInt(getGroupId()));
            setGroupId(null);
        }
        SchUserGroupSelectInitBiz selectInitBiz = new SchUserGroupSelectInitBiz(
                ctx.getRequestModel(),
                ctx.getCon(),
                null,
                null,
                null);
        selectInitBiz.initGroup();

        //グループ区分毎の入力チェック
        if (Objects.equals(getGroupType(), EnumGroupType.GROUP_VALUE_STRING)) {
            CmnGroupmModel mdl = cash.getRequestGroup(ctx, getGroupId());
            if (mdl == null) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "search.data.notfound",
                        gsMsg.getMessage("cmn.group"))
                        .setParamName("targetId");
            }


            Set<Integer> accessGroupSet = new HashSet<>();
            accessGroupSet.addAll(
                    selectInitBiz.getBaseGrpLabelList().stream()
                        .filter(lbl -> NumberUtils.isNumber(
                                lbl.getValue().substring(0, 1)
                                    )
                                )
                        .map(lbl -> Integer.parseInt(
                                        lbl.getValue()
                                    )
                                )
                        .collect(Collectors.toSet())
                    );
            if (!accessGroupSet.contains(mdl.getGrpSid())
                    ) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "cant.execute.out.crange",
                        gsMsg.getMessage("cmn.group"),
                        gsMsg.getMessage("cmn.access")
                        )
                        .setParamName("targetId");

            }

        }
        if (Objects.equals(getGroupType(), EnumGroupType.MYGROUP_VALUE_STRING)) {

            Set<Integer> accessGroupSet = new HashSet<>();
            accessGroupSet.addAll(
                    selectInitBiz.getBaseGrpLabelList().stream()
                        .filter(lbl ->
                            lbl.getValue().startsWith(GSConstSchedule.MY_GROUP_STRING)
                                )
                        .map(lbl ->
                            Integer.parseInt(
                                        lbl.getValue().substring(
                                                GSConstSchedule.MY_GROUP_STRING.length()
                                              )
                                    )
                                )
                        .collect(Collectors.toSet())
                    );
            if (!accessGroupSet.contains(getGroupSid())
                    ) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.mygroup"));

            }

        }
        if (Objects.equals(getGroupType(), EnumGroupType.SCHEDULELIST_VALUE_STRING)) {
            Set<Integer> accessGroupSet = new HashSet<>();
            accessGroupSet.addAll(
                    selectInitBiz.getBaseGrpLabelList().stream()
                        .filter(lbl ->
                            lbl.getValue().startsWith(GSConstSchedule.DSP_LIST_STRING)
                                )
                        .map(lbl ->
                            Integer.parseInt(
                                        lbl.getValue().substring(
                                                GSConstSchedule.DSP_LIST_STRING.length()
                                              )
                                    )
                                )
                        .collect(Collectors.toSet())
                    );
            if (!accessGroupSet.contains(getGroupSid())
                    ) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel())
                        .getMessage("schedule.sch260.04"));
            }

        }

    }


}
