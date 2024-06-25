<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<% String tdColor = request.getParameter("tdColor");%>
<logic:equal name="sch040Form" property="reservePluginKbn" value="0">
  <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="cmn.reserve" />
    </th>
    <td class="w80 <%= tdColor %> ">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <div class="verAlignMid pos_abs js_rsvEditBatchRef">
          <span class="cl_fontWarn fs_13">[<gsmsg:write key="schedule.26" />]</span>
          <logic:equal name="sch040Form" property="cmd" value="edit">
            <html:radio name="sch040Form" property="sch040ResBatchRef" styleId="sch040ResBatchRef0" value="1" onchange="setDisabled();" styleClass="ml20"/>
            <label for="sch040ResBatchRef0"><gsmsg:write key="schedule.25" /></label>
            <html:radio name="sch040Form" property="sch040ResBatchRef" styleId="sch040ResBatchRef1" value="0" onchange="setDisabled();" styleClass="ml10"/>
            <label for="sch040ResBatchRef1"><gsmsg:write key="schedule.24" /></label>
          </logic:equal>
        </div>
        <ui:multiselector name="sch040Form" property="sch040SameReserveUI" styleClass="hp200"  />
      </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <logic:notEmpty name="sch040Form" property="sch040ReserveSelectLabel" scope="request">
          <logic:iterate id="ressBean" name="sch040Form" property="sch040ReserveSelectLabel" scope="request">
             <bean:write name="ressBean" property="rsdName" scope="page"/>
             <br>
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>
    </td>
  </tr>
  </logic:notEqual>
</logic:equal>