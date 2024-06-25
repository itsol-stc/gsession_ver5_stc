<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch060.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/schedule/sch092">
<input type="hidden" name="CMD" value="">

<html:hidden property="backScreen" />
<html:hidden property="dspMod" />
<html:hidden property="listMod" />
<html:hidden property="sch010DspDate" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="sch010SelectUsrKbn" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch010DspGpSid" />
<html:hidden property="sch010searchWord" />
<html:hidden property="sch020SelectUsrSid" />
<html:hidden property="sch030FromHour" />

<html:hidden property="sch100PageNum" />
<html:hidden property="sch100Slt_page1" />
<html:hidden property="sch100Slt_page2" />
<html:hidden property="sch100OrderKey1" />
<html:hidden property="sch100SortKey1" />
<html:hidden property="sch100OrderKey2" />
<html:hidden property="sch100SortKey2" />

<html:hidden property="sch100SvSltGroup" />
<html:hidden property="sch100SvSltUser" />
<html:hidden property="sch100SvSltStartYearFr" />
<html:hidden property="sch100SvSltStartMonthFr" />
<html:hidden property="sch100SvSltStartDayFr" />
<html:hidden property="sch100SvSltStartYearTo" />
<html:hidden property="sch100SvSltStartMonthTo" />
<html:hidden property="sch100SvSltStartDayTo" />
<html:hidden property="sch100SvSltEndYearFr" />
<html:hidden property="sch100SvSltEndMonthFr" />
<html:hidden property="sch100SvSltEndDayFr" />
<html:hidden property="sch100SvSltEndYearTo" />
<html:hidden property="sch100SvSltEndMonthTo" />
<html:hidden property="sch100SvSltEndDayTo" />
<html:hidden property="sch100SvKeyWordkbn" />
<html:hidden property="sch100SvKeyValue" />
<html:hidden property="sch100SvOrderKey1" />
<html:hidden property="sch100SvSortKey1" />
<html:hidden property="sch100SvOrderKey2" />
<html:hidden property="sch100SvSortKey2" />

<html:hidden property="sch100SltGroup" />
<html:hidden property="sch100SltUser" />
<html:hidden property="sch100SltStartYearFr" />
<html:hidden property="sch100SltStartMonthFr" />
<html:hidden property="sch100SltStartDayFr" />
<html:hidden property="sch100SltStartYearTo" />
<html:hidden property="sch100SltStartMonthTo" />
<html:hidden property="sch100SltStartDayTo" />
<html:hidden property="sch100SltEndYearFr" />
<html:hidden property="sch100SltEndMonthFr" />
<html:hidden property="sch100SltEndDayFr" />
<html:hidden property="sch100SltEndYearTo" />
<html:hidden property="sch100SltEndMonthTo" />
<html:hidden property="sch100SltEndDayTo" />
<html:hidden property="sch100KeyWordkbn" />
<logic:notEmpty name="sch092Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch092Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch092Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch092Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch092Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch092Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch092Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch092Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch092Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch092Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch092kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch092back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
    <tr>
      <th class="w25">
      <gsmsg:write key="cmn.show.timezone.days" />
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.starttime" />：
        <!-- 時 -->
        <html:select property="sch092FrH">
        <html:optionsCollection name="sch092Form" property="sch092HourLabel" value="value" label="label" />
        </html:select>
        <div class="mt5">
        <gsmsg:write key="cmn.endtime" />：
        <!-- 時 -->
        <html:select property="sch092ToH">
        <html:optionsCollection name="sch092Form" property="sch092HourLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>























  <logic:equal name="sch092Form" property="sch092MemDspConfFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MEM_DSP_USR) %>">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.sortby.members" />
      </th>
      <td class="w75">
        <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.plg.set.order.grpmember" /></span><br>
        <div class="mt10 verAlignMid">
        <gsmsg:write key="cmn.first.key" />：
        <!-- キー1 -->
        <html:select property="sch092SortKey1">
        <html:optionsCollection name="sch092Form" property="sch092SortKeyLabel" value="value" label="label" />
        </html:select>
          <html:radio name="sch092Form" property="sch092SortOrder1" styleId="sch092SortOrder10" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
          <label for="sch092SortOrder10"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="sch092Form" property="sch092SortOrder1" styleId="sch092SortOrder11"  styleClass="ml5" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
          <label for="sch092SortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
        <br>
        <div class="mt5 verAlignMid">
        <gsmsg:write key="cmn.second.key" />：
        <!-- キー2 -->
        <html:select property="sch092SortKey2">
        <html:optionsCollection name="sch092Form" property="sch092SortKeyLabel" value="value" label="label" />
        </html:select>
        <html:radio name="sch092Form" property="sch092SortOrder2" styleId="sch092SortOrder20" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
        <label for="sch092SortOrder20"><gsmsg:write key="cmn.order.asc" /></label>
        <html:radio name="sch092Form" property="sch092SortOrder2" styleId="sch092SortOrder21" styleClass="ml5" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
        <label for="sch092SortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
    </tr>
    </logic:equal>
    <logic:notEqual name="sch092Form" property="sch092DefGroupFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) %>">
    <tr>
      <th>
      <gsmsg:write key="schedule.sch093.2" />
      </th>
      <td>
        <!-- グループ -->
        <html:select name="sch092Form" property="sch092DefGroup">

        <logic:notEmpty name="sch092Form" property="sch092GroupLabel" scope="request">
        <logic:iterate id="gpBean" name="sch092Form" property="sch092GroupLabel" scope="request">

        <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
        <bean:define id="gpStyle" name="gpBean" property="styleClass" type="java.lang.String" />
        <% String gpClass = "";
           if (gpStyle.equals("1")) {
               gpClass = "select_mygroup-bgc";
           } else if (gpStyle.equals("2")) {
               gpClass = "select_myschedule-bgc";
           }
        %>
        <html:option styleClass="<%= gpClass %>" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>

        </logic:iterate>
        </logic:notEmpty>

        </html:select>
        <button class="iconBtn-border" type="button" id="sch092GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.sch092DefGroup, 'sch092DefGroup', '1', '', '1');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
        <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>
      </td>
    </tr>
    </logic:notEqual>


























    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.src.83" />
      </th>
      <td class="w75">
      <span class="verAlignMid">
        <html:radio name="sch092Form" styleId="sch092Dsp_02" property="sch092DefDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.GROUP_SCH_SHOW) %>" />
        <label for="sch092Dsp_02"><gsmsg:write key="cmn.display.ok" /></label>
        <html:radio name="sch092Form" styleId="sch092Dsp_01" property="sch092DefDsp" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.GROUP_SCH_NOT_SHOW) %>" />
        <label for="sch092Dsp_01"><gsmsg:write key="cmn.dont.show" /></label>
      </span>
      </td>
    </tr>



























    <tr>
      <th class="w25">
      <gsmsg:write key="cmn.number.display" />
      </th>
      <td class="w75">
      <html:select property="sch092DefLine">
      <html:optionsCollection name="sch092Form" property="sch092LineLabel" value="value" label="label" />
      </html:select>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="cmn.set.auto.reflesh" />
      </th>
      <td>
      <html:select name="sch092Form" property="sch092ReloadTime">
      <html:optionsCollection name="sch092Form" property="sch092TimeLabelList" value="value" label="label" />
      </html:select>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="schedule.sch094.2" />
      </th>
      <td>
      <html:select name="sch092Form" property="sch092SelWeek">
      <html:optionsCollection name="sch092Form" property="sch092WeekList" value="value" label="label" />
      </html:select>
      </td>
    </tr>





















  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch092kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch092back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>