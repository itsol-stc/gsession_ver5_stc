<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>

<tr>
  <th class="no_w" colspan="2">
    <span>
      <gsmsg:write key="schedule.3" />
    </span>
  </th>
  <td class="txt_l">
    <div>
      <span class="verAlignMid">
        <html:radio property="rsv110SchKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_SCHKBN_USER)%>" styleId="rsvSchKbn0" onclick="rsvSchChange();" />
        <label for="rsvSchKbn0">
          <span>
            <gsmsg:write key="cmn.user" />
          </span>
        </label>
        <html:radio property="rsv110SchKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_SCHKBN_GROUP)%>" styleClass="ml10" styleId="rsvSchKbn1" onclick="rsvSchChange();" />
        <label for="rsvSchKbn1">
          <span>
            <gsmsg:write key="cmn.group" />
          </span>
        </label>
      </span>
    </div>

    <span id="rsvSchGroup">
      <div>
        <span>
          <gsmsg:write key="reserve.167" />
        </span>
      </div>
      <html:select property="rsv110SchGroupSid" styleId="rsvSchGrpSid" styleClass="wp150">
        <logic:notEmpty name="rsv110Form" property="rsv110SchGroupLabel" scope="request">
          <logic:iterate id="exSchGpBean" name="rsv110Form" property="rsv110SchGroupLabel" scope="request">
            <%
            boolean schGpDisabled = false;
            %>
            <logic:equal name="exSchGpBean" property="viewKbn" value="false">
              <%
              schGpDisabled = true;
              %>
            </logic:equal>
            <bean:define id="gpValue" name="exSchGpBean" property="value" type="java.lang.String" />
            <logic:equal name="exSchGpBean" property="styleClass" value="0">
              <html:option value="<%=gpValue%>" disabled="<%=schGpDisabled%>">
                <bean:write name="exSchGpBean" property="label" />
              </html:option>
            </logic:equal>
            <logic:notEqual name="exSchGpBean" property="styleClass" value="0">
              <html:option value="<%=gpValue%>" disabled="<%=schGpDisabled%>">
                <bean:write name="exSchGpBean" property="label" />
              </html:option>
            </logic:notEqual>

          </logic:iterate>
        </logic:notEmpty>
      </html:select>

      <button type="button" onclick="setDateParam();openGroupWindow_Disabled(this.form.rsv110SchGroupSid, 'rsv110SchGroupSid', '0', '', 1, '', 'rsvSchNotAccessGroup', 1)" class="iconBtn-border" value="&nbsp;&nbsp;" id="rsvSchGrpBtn1">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
        <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
      </button>
    </span>

    <div id="rsvSchUser">
      <div>
        <span>
          <gsmsg:write key="reserve.166" />
        </span>
      </div>

      <ui:usrgrpselector name="rsv110Form" property="rsv110SchUserUI" styleClass="hp215" />
    </div>
  </td>
</tr>