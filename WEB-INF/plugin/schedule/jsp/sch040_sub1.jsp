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

<%
String maxLengthContent = String.valueOf(GSConstSchedule.MAX_LENGTH_VALUE);
%>
<%
String maxLengthBiko = String.valueOf(GSConstSchedule.MAX_LENGTH_BIKO);
%>
<%
String tdColor = request.getParameter("tdColor");
%>
<%
String valueFocusEvent = "";
%>
<%
String bikoFocusEvent = "";
%>
  <!-- タイトル色 -->
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="schedule.10" />
    </th>
    <td class="w80 <%=tdColor%>">
    <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
      <logic:notEmpty name="sch040Form" property="sch040ColorMsgList">
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="1" styleId="bg_color1" />
          </div>
          <label for="bg_color1" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[0]"/></label>
        </div>
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleRed">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="2" styleId="bg_color2"/>
          </div>
          <label for="bg_color2" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[1]"/></label>
        </div>
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="3" styleId="bg_color3"/>
          </div>
          <label for="bg_color3" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[2]"/></label>
        </div>
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="4" styleId="bg_color4"/>
          </div>
          <label for="bg_color4" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[3]"/></label>
        </div>
        <div class="verAlignMid">
          <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="5" styleId="bg_color5"/>
          </div>
          <label for="bg_color5" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[4]"/></label>
        </div>
        <logic:equal name="sch040Form" property="sch040colorKbn" value="1">
          <br>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="6" styleId="bg_color6"/>
            </div>
            <label for="bg_color6" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[5]"/></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleWine">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="7" styleId="bg_color7"/>
            </div>
            <label for="bg_color7" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[6]"/></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleCien">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="8" styleId="bg_color8"/>
            </div>
            <label for="bg_color8" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[7]"/></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleGray">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="9" styleId="bg_color9"/>
            </div>
            <label for="bg_color9" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[8]"/></label>
          </div>
          <div class="verAlignMid">
            <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="10" styleId="bg_color10"/>
            </div>
            <label for="bg_color10" class="ml5"><bean:write name="sch040Form" property="sch040ColorMsgList[9]"/></label>
          </div>
        </logic:equal>

      </logic:notEmpty>

      <logic:empty name="sch040Form" property="sch040ColorMsgList">
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="1" styleId="bg_color1" />
          </div>
          <label for="bg_color1" class="ml5"></label>
        </div>
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleRed">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="2" styleId="bg_color2"/>
          </div>
          <label for="bg_color2" class="ml5"></label>
        </div>
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="3" styleId="bg_color3"/>
          </div>
          <label for="bg_color3" class="ml5"></label>
        </div>
        <div class="verAlignMid mr5">
          <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="4" styleId="bg_color4"/>
          </div>
          <label for="bg_color4" class="ml5"></label>
        </div>
        <div class="verAlignMid">
          <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
            <html:radio name="sch040Form" property="sch040Bgcolor" value="5" styleId="bg_color5"/>
          </div>
          <label for="bg_color5" class="ml5"></label>
        </div>
        <logic:equal name="sch040Form" property="sch040colorKbn" value="1">
          <br>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="6" styleId="bg_color6"/>
            </div>
            <label for="bg_color6" class="ml5"></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleWine">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="7" styleId="bg_color7"/>
            </div>
            <label for="bg_color7" class="ml5"></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleCien">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="8" styleId="bg_color8"/>
            </div>
            <label for="bg_color8" class="ml5"></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleGray">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="9" styleId="bg_color9"/>
            </div>
            <label for="bg_color9" class="ml5"></label>
          </div>
          <div class="verAlignMid">
            <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
              <html:radio name="sch040Form" property="sch040Bgcolor" value="10" styleId="bg_color10"/>
            </div>
            <label for="bg_color10" class="ml5"></label>
          </div>
        </logic:equal>
      </logic:empty>
    </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="1">
           <span class="bgc_fontSchTitleBlue"><span class="pl15"></span></span><bean:write name="colorMsg1" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="2">
           <span class="bgc_fontSchTitleRed"><span class="pl15"></span></span><bean:write name="colorMsg2" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="3">
           <span class="bgc_fontSchTitleGreen"><span class="pl15"></span></span><bean:write name="colorMsg3" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="4">
           <span class="bgc_fontSchTitleYellow"><span class="pl15"></span></span><bean:write name="colorMsg4" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="5">
           <span class="bgc_fontSchTitleBlack"><span class="pl15"></span></span><bean:write name="colorMsg5" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="6">
           <span class="bgc_fontSchTitleNavy"><span class="pl15"></span></span><bean:write name="colorMsg6" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="7">
           <span class="bgc_fontSchTitleWine"><span class="pl15"></span></span><bean:write name="colorMsg7" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="8">
           <span class="bgc_fontSchTitleCien"><span class="pl15"></span></span><bean:write name="colorMsg8" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="9">
           <span class="bgc_fontSchTitleGray"><span class="pl15"></span></span><bean:write name="colorMsg9" />
        </logic:equal>
        <logic:equal name="sch040Form" property="sch040Bgcolor" value="10">
           <span class="bgc_fontSchTitleMarine"><span class="pl15"></span></span><bean:write name="colorMsg10" />
        </logic:equal>
        <html:hidden property="sch040Bgcolor" />
        <input type="hidden" id="sch040AnsPublic" value="<bean:write name="sch040Form" property="sch040Public" />">
      </logic:equal>
    </td>
  </tr>
  <!-- 内容 -->
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="cmn.content" /><a id="naiyou" name="naiyou"></a>
    </th>
    <td class="w80  <%=tdColor%>">
    <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
      <%-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 --%>
      <textarea name="sch040Value" cols="50" rows="5" class="wp400" onkeyup="showLengthStr(value, <%=maxLengthContent%>, 'inputlength');" id="inputstr" <%=valueFocusEvent%>><%--
      --%><bean:write name="sch040Form" property="sch040Value" /></textarea>
      <br>
      <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%=maxLengthContent%>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </logic:notEqual>
    <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
      <bean:write name="sch040Form" property="sch040DspValue" filter="false"/>
      <html:hidden property="sch040Value" />
    </logic:equal>
    </td>
  </tr>
  <!-- 添付 -->
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="cmn.attached" />
    </th>
    <td class="w80 <%= tdColor %> js_tenpu">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <attachmentFile:filearea
        mode="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE) %>"
        pluginId="<%= GSConstSchedule.PLUGIN_ID_SCHEDULE %>"
        tempDirId="sch040" />
      </logic:notEqual>

      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <logic:notEmpty name="sch040Form" property="fileLabelAttend">
          <logic:iterate id="bean" name="sch040Form" property="fileLabelAttend">
          <div>
            <input type="hidden" class="js_tempValue" value="<bean:write name="bean" property="binSid" />">
            <a href="#!" class="js_tempDownLoad"><span class="textLink"><bean:write name="bean" property="binFileName" /></span></a>
          </div>
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>
    </td>
  </tr>
  <!-- 備考 -->
  <tr>
    <th class="w20 txt_l"><gsmsg:write key="cmn.memo" /></td>
    <td class="w80 <%=tdColor%>">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
        <%-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 --%>
        <textarea name="sch040Biko" cols="50" rows="3" class="wp400" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength2');" id="inputstr2" <%=bikoFocusEvent%>><%--
         --%><bean:write name="sch040Form" property="sch040Biko" /></textarea>
        <br>
        <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%=maxLengthBiko%>&nbsp;<gsmsg:write key="cmn.character" /></span>
      </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
        <bean:write name="sch040Form" property="sch040DspBiko" filter="false"/>
        <html:hidden property="sch040Biko" />
      </logic:equal>
    </td>
  </tr>
  <!-- 編集権限 -->
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="cmn.edit.permissions" />
    </th>
    <td class="w80 <%=tdColor%>">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
        <div id="editRadioArea" class="verAlignMid">
          <html:radio name="sch040Form" property="sch040Edit" styleId="sch040Edit0" value="0" />
          <label for="sch040Edit0"><gsmsg:write key="cmn.nolimit" /></label>
          <html:radio name="sch040Form" property="sch040Edit" styleId="sch040Edit1" value="1" styleClass="ml10"/>
          <label for="sch040Edit1"><gsmsg:write key="cmn.only.principal.or.registant" /></label>
          <html:radio name="sch040Form" property="sch040Edit" styleId="sch040Edit2" value="2" styleClass="ml10"/>
          <label for="sch040Edit2"><gsmsg:write key="cmn.only.affiliation.group.membership" /></label>
        </div>
        <div id="editOnlyArea">
          <gsmsg:write key="cmn.only.principal.or.registant" />
        </div>
      </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%=String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER)%>">
         <logic:equal name="sch040Form" property="sch040Edit" value="0">
             <gsmsg:write key="cmn.nolimit" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Edit" value="1">
             <gsmsg:write key="cmn.only.principal.or.registant" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Edit" value="2">
             <gsmsg:write key="cmn.only.affiliation.group.membership" />
         </logic:equal>
         <html:hidden property="sch040Edit"/>
      </logic:equal>
    </td>
  </tr>
  <!-- リマインダー通知 -->
  <logic:equal name="sch040Form" property="sch040ReminderEditMode.ableSelReminder" value="true">
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="cmn.reminder" />
    </th>
    <td class="w80 <%=tdColor%>">
      <logic:notEqual name="sch040Form" property="sch040ReminderEditMode" value="<%=SchEnumRemindMode.GROUP.toString()%>">
        <div>
          <html:select name="sch040Form" property="sch040ReminderTime" styleClass="js_reminder">
            <html:optionsCollection name="sch040Form" property="reminderTimeList" value="value" label="label" />
          </html:select>
        </div>
      </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040ReminderEditMode" value="<%=SchEnumRemindMode.GROUP.GROUP.toString()%>">
        <div class="verAlignMid">
          <html:radio name="sch040Form" property="sch040TargetGroup" value="<%= String.valueOf(GSConstSchedule.REMINDER_USE_YES) %>" styleId="schGroup1"/>
          <label for="schGroup1"><gsmsg:write key="cmn.notify" /></label>
          <html:radio name="sch040Form" property="sch040TargetGroup" value="<%= String.valueOf(GSConstSchedule.REMINDER_USE_NO) %>" styleId="schGroup2" styleClass="ml10" />
          <label for="schGroup2"><gsmsg:write key="cmn.dont.notify" /></label>
        </div>
        <div class="cl_fontWarn">
          [<gsmsg:write key="schedule.sch040.12" />]
        </div>
      </logic:equal>
    </td>
  </tr>
  </logic:equal>

  <!-- 公開 -->
  <tr>
    <th class="w20 txt_l">
      <gsmsg:write key="schedule.21" />
    </th>
    <td class="w80 <%= tdColor %>">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <div class="verAlignMid">
          <html:radio name="sch040Form" property="sch040Public" styleId="sch040Public0" value="0" />
          <label for="sch040Public0" class="mr10"><gsmsg:write key="cmn.public" /></label>
        </div><!--
     --><div class="verAlignMid">
          <html:radio name="sch040Form" property="sch040Public" styleId="sch040Public1" value="1"/>
          <label for="sch040Public1" class="mr10"><gsmsg:write key="cmn.private" /></label>
        </div><!--
     --><logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="0"><!--
       --><div class="verAlignMid">
            <html:radio name="sch040Form" property="sch040Public" styleId="sch040Public2" value="2"/>
            <label for="sch040Public2" class="mr10"><gsmsg:write key="schedule.5" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch040Form" property="sch040Public" styleId="sch040Public5" value="5"/>
            <label for="sch040Public5" class="mr10"><gsmsg:write key="schedule.38" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch040Form" property="sch040Public" styleId="sch040Public3" value="3"/>
            <label for="sch040Public3" class="mr10"><gsmsg:write key="schedule.28" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch040Form" property="sch040Public" styleId="sch040Public4" value="4"/>
            <label for="sch040Public4"><gsmsg:write key="schedule.37" /></label>
          </div>
        </logic:equal>
      </logic:notEqual>
      <a id="add_user" name="add_user"></a>

      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
         <logic:equal name="sch040Form" property="sch040Public" value="0">
           <gsmsg:write key="cmn.public" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Public" value="1">
           <gsmsg:write key="cmn.private" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Public" value="2">
           <gsmsg:write key="schedule.5" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Public" value="3">
           <gsmsg:write key="schedule.28" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Public" value="4">
           <gsmsg:write key="schedule.37" />
         </logic:equal>
         <logic:equal name="sch040Form" property="sch040Public" value="5">
           <gsmsg:write key="schedule.38" />
         </logic:equal>
         <html:hidden property="sch040Public" />
      </logic:equal>
    </td>
  </tr>
  <!-- 公開対象 -->
  <tr class="js_displayTarget">
    <th class="w20 txt_l">
      <gsmsg:write key="schedule.36" />
    </th>
    <td class="w80 <%= tdColor %>">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <ui:multiselector name="sch040Form" property="sch040DisplayTargetUI" styleClass="hp200" />
      </logic:notEqual>

      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <logic:notEmpty name="sch040Form" property="sch040DisplayTargetList">
          <logic:iterate id="user" name="sch040Form" property="sch040DisplayTargetList" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
            <bean:write name="user" property="label" />
            <br>
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>
    </td>
  </tr>

  <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
  <logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="<%= String.valueOf(GSConstSchedule.USER_KBN_USER) %>">
    <logic:equal name="sch040Form" property="cmd" value="add">
     <!-- 出欠確認 -->
     <tr>
       <th class="w20 txt_l">
         <gsmsg:write key="schedule.sch040.3" />
       </th>
       <td class="w80 <%= tdColor %>">
       <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
         <div class="verAlignMid">
           <html:radio name="sch040Form" property="sch040AttendKbn" styleId="sch040AttendKbn0" value="<%= String.valueOf(GSConstSchedule.ATTEND_KBN_NO) %>" />
           <label for="sch040AttendKbn0"><gsmsg:write key="cmn.notcheck" /></label>
           <html:radio name="sch040Form" property="sch040AttendKbn" styleId="sch040AttendKbn1" value="<%= String.valueOf(GSConstSchedule.ATTEND_KBN_YES) %>" styleClass="ml10"/>
           <label for="sch040AttendKbn1"><gsmsg:write key="cmn.check.2" /></label>
         </div>
       </logic:notEqual>
       </td>
     </tr>
    </logic:equal>
    <logic:equal name="sch040Form" property="cmd" value="edit">
       <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_NORMAL) %>">
         <!-- 出欠確認 -->
         <tr>
           <th class="w20 txt_l">
            <gsmsg:write key="schedule.sch040.3" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
           </th>
           <td class="w80 <%= tdColor %>">
             <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
               <div class="verAlignMid">
                 <html:radio name="sch040Form" property="sch040AttendKbn" styleId="sch040AttendKbn0" value="<%= String.valueOf(GSConstSchedule.ATTEND_KBN_NO) %>" />
                 <label for="sch040AttendKbn0"><gsmsg:write key="cmn.notcheck" /></label>
                 <html:radio name="sch040Form" property="sch040AttendKbn" styleId="sch040AttendKbn1" value="<%= String.valueOf(GSConstSchedule.ATTEND_KBN_YES) %>" styleClass="ml10"/>
                 <label for="sch040AttendKbn1"><gsmsg:write key="cmn.check.2" /></label>
               </div>
             </logic:notEqual>
           </td>
         </tr>
       </logic:equal>
    </logic:equal>
  </logic:equal>
  </logic:notEqual>