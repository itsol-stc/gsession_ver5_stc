<%@page import="jp.groupsession.v2.cmn.model.SchEnumRemindMode"%>
<%@page import="jp.groupsession.v2.cmn.GSConstSchedule"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<!DOCTYPE html>

<% String maxLengthContent = String.valueOf(GSConstSchedule.MAX_LENGTH_VALUE); %>
<% String maxLengthBiko = String.valueOf(GSConstSchedule.MAX_LENGTH_BIKO); %>
<% String tdColor = request.getParameter("tdColor");%>
<% String valueFocusEvent = ""; %>
<% String bikoFocusEvent = ""; %>

<tr>
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="schedule.10" />
  </th>
  <td class="w80 <%= tdColor %>">

    <bean:define id="colorMsg1" value=""/>
    <bean:define id="colorMsg2" value=""/>
    <bean:define id="colorMsg3" value=""/>
    <bean:define id="colorMsg4" value=""/>
    <bean:define id="colorMsg5" value=""/>
    <bean:define id="colorMsg6" value=""/>
    <bean:define id="colorMsg7" value=""/>
    <bean:define id="colorMsg8" value=""/>
    <bean:define id="colorMsg9" value=""/>
    <bean:define id="colorMsg10" value=""/>
    <logic:iterate id="msgStr" name="sch041Form" property="sch040ColorMsgList" indexId="msgId" type="java.lang.String">
      <bean:define id="msgName" value="<%=\"colorMsg\" + String.valueOf(msgId + 1) %>"/>
      <% pageContext.setAttribute(msgName, msgStr); %>
    </logic:iterate>
    <div class="verAlignMid mr5">
      <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
        <html:radio name="sch041Form" property="sch041Bgcolor" value="1" styleId="bg_color1" />
      </div>
      <label for="bg_color1" class="ml5"><bean:write name="colorMsg1" /></label>
    </div>
    <div class="verAlignMid mr5">
      <div class="cal_titlecolor-block bgc_fontSchTitleRed">
        <html:radio name="sch041Form" property="sch041Bgcolor" value="2" styleId="bg_color2"/>
      </div>
      <label for="bg_color2" class="ml5"><bean:write name="colorMsg2" /></label>
    </div>
    <div class="verAlignMid mr5">
      <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
        <html:radio name="sch041Form" property="sch041Bgcolor" value="3" styleId="bg_color3"/>
      </div>
      <label for="bg_color3" class="ml5"><bean:write name="colorMsg3" /></label>
    </div>
    <div class="verAlignMid mr5">
      <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
        <html:radio name="sch041Form" property="sch041Bgcolor" value="4" styleId="bg_color4"/>
      </div>
      <label for="bg_color4" class="ml5"><bean:write name="colorMsg4" /></label>
    </div>
    <div class="verAlignMid">
      <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
        <html:radio name="sch041Form" property="sch041Bgcolor" value="5" styleId="bg_color5"/>
      </div>
      <label for="bg_color5" class="ml5"><bean:write name="colorMsg5" /></label>
    </div>
    <logic:equal name="sch041Form" property="sch041colorKbn" value="1">
      <br>
      <div class="verAlignMid mr5">
        <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
          <html:radio name="sch041Form" property="sch041Bgcolor" value="6" styleId="bg_color6"/>
        </div>
        <label for="bg_color6" class="ml5"><bean:write name="colorMsg6" /></label>
      </div>
      <div class="verAlignMid mr5">
        <div class="cal_titlecolor-block bgc_fontSchTitleWine">
          <html:radio name="sch041Form" property="sch041Bgcolor" value="7" styleId="bg_color7"/>
        </div>
        <label for="bg_color7" class="ml5"><bean:write name="colorMsg7" /></label>
      </div>
      <div class="verAlignMid mr5">
        <div class="cal_titlecolor-block bgc_fontSchTitleCien">
          <html:radio name="sch041Form" property="sch041Bgcolor" value="8" styleId="bg_color8"/>
        </div>
        <label for="bg_color8" class="ml5"><bean:write name="colorMsg8" /></label>
      </div>
      <div class="verAlignMid mr5">
        <div class="cal_titlecolor-block bgc_fontSchTitleGray">
          <html:radio name="sch041Form" property="sch041Bgcolor" value="9" styleId="bg_color9"/>
        </div>
        <label for="bg_color9" class="ml5"><bean:write name="colorMsg9" /></label>
      </div>
      <div class="verAlignMid">
        <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
          <html:radio name="sch041Form" property="sch041Bgcolor" value="10" styleId="bg_color10"/>
        </div>
        <label for="bg_color10" class="ml5"><bean:write name="colorMsg10" /></label>
      </div>
    </logic:equal>
  </td>
</tr>

<tr>
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="cmn.content" />
  </th>
  <%-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 --%>
  <td class="w80 <%= tdColor %>">
    <textarea name="sch041Value"  rows="10" class="wp400" onkeyup="showLengthStr(value, <%= maxLengthContent %>, 'inputlength');" id="inputstr" <%= valueFocusEvent %>>
<bean:write name="sch041Form" property="sch041Value" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
  </td>
</tr>
<!-- 添付 -->
<tr>
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="cmn.attached" />
  </th>
  <td class="w80 <%= tdColor %> js_tenpu">
    <attachmentFile:filearea
    mode="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE) %>"
    pluginId="<%= GSConstSchedule.PLUGIN_ID_SCHEDULE %>"
    tempDirId="sch041" />
  </td>
</tr>

<tr>
  <th class="w20 txt_l" colspan="2"><gsmsg:write key="cmn.memo" /></th>
  <%-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 --%>
  <td class="w80 <%= tdColor %>">
    <textarea name="sch041Biko" rows="5" class="wp400" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength2');" id="inputstr2" <%= bikoFocusEvent %>>
<bean:write name="sch041Form" property="sch041Biko" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthBiko %>&nbsp;<gsmsg:write key="cmn.character" /></span>
  </td>
</tr>

<tr>
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="cmn.edit.permissions" />
  </th>
  <td class="w80 <%= tdColor %> ">
    <div class="verAlignMid">
      <html:radio name="sch041Form" property="sch041Edit" styleId="sch040Edit0" value="0" />
      <label for="sch040Edit0">
        <gsmsg:write key="cmn.nolimit" />
      </label>

      <html:radio name="sch041Form" property="sch041Edit" styleId="sch040Edit1" value="1" styleClass="ml10" />
      <label for="sch040Edit1">
        <gsmsg:write key="cmn.only.principal.or.registant" />
      </label>

      <html:radio name="sch041Form" property="sch041Edit" styleId="sch040Edit2" value="2" styleClass="ml10" />
      <label for="sch040Edit2">
        <gsmsg:write key="cmn.only.affiliation.group.membership" />
      </label>
    </div>
  </td>
</tr>

<!-- リマインダー通知 -->
<logic:equal name="sch041Form" property="sch041ReminderEditMode.ableSelReminder" value="true">
<tr>
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="cmn.reminder" />
  </th>
  <td class="w80 <%= tdColor %>">
    <logic:notEqual name="sch041Form" property="sch041ReminderEditMode" value="<%=SchEnumRemindMode.GROUP.toString()%>">
      <div>
        <html:select name="sch041Form" property="sch041ReminderTime" styleClass="js_reminder">
          <html:optionsCollection name="sch041Form" property="reminderTimeList" value="value" label="label" />
        </html:select>
      </div>
    </logic:notEqual>
    <logic:equal name="sch041Form" property="sch041ReminderEditMode" value="<%=SchEnumRemindMode.GROUP.GROUP.toString()%>">
      <div class="verAlignMid">
        <html:radio name="sch041Form" property="sch041TargetGroup" value="<%= String.valueOf(GSConstSchedule.REMINDER_USE_YES) %>" styleId="schGroup1"/><label for="schGroup1"><gsmsg:write key="cmn.notify" /></label>
        <html:radio name="sch041Form" property="sch041TargetGroup" value="<%= String.valueOf(GSConstSchedule.REMINDER_USE_NO) %>" styleId="schGroup2" styleClass="ml10" /><label for="schGroup2"><gsmsg:write key="cmn.dont.notify" /></label>
      </div>
      <div>
        <span class="cl_fontWarn">
          [<gsmsg:write key="schedule.sch040.12" />]
        </span>
      </div>
    </logic:equal>
  </td>
</tr>
</logic:equal>

<!-- 公開 -->
<tr>
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="schedule.21" />
  </th>
  <td class="w80 <%= tdColor %>">
    <div class="verAlignMid">
      <html:radio name="sch041Form" property="sch041Public" styleId="sch041Public0" value="0" />
      <label for="sch041Public0" class="mr10"><gsmsg:write key="cmn.public" /></label>
    </div><!--
 --><div class="verAlignMid">
      <html:radio name="sch041Form" property="sch041Public" styleId="sch041Public1" value="1"/>
      <label for="sch041Public1" class="mr10"><gsmsg:write key="cmn.private" /></label>
    </div><!--
 --><logic:equal name="sch041Form" property="sch010SelectUsrKbn" value="0"><!--
   --><div class="verAlignMid">
        <html:radio name="sch041Form" property="sch041Public" styleId="sch041Public2" value="2"/>
        <label for="sch041Public2" class="mr10"><gsmsg:write key="schedule.5" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="sch041Form" property="sch041Public" styleId="sch041Public5" value="5"/>
        <label for="sch041Public5" class="mr10"><gsmsg:write key="schedule.38" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="sch041Form" property="sch041Public" styleId="sch041Public3" value="3"/>
        <label for="sch041Public3" class="mr10"><gsmsg:write key="schedule.28" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="sch041Form" property="sch041Public" styleId="sch041Public4" value="4"/>
        <label for="sch041Public4"><gsmsg:write key="schedule.37" /></label>
      </div>
  </logic:equal>
    <a id="add_user" name="add_user"></a>
  </td>
</tr>

<!-- 公開対象 -->
<tr class="js_displayTarget">
  <th class="w20 txt_l" colspan="2">
    <gsmsg:write key="schedule.36" />
  </th>
  <td class="w80 <%= tdColor %>">
    <ui:multiselector name="sch041Form" property="sch041DisplayTargetUI" styleClass="hp200" />


  </td>
</tr>