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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <title>GROUPSESSION <gsmsg:write key="tcd.146" /></title>
</head>

<body>
<html:form action="/timecard/tcd070">
<logic:notEmpty name="tcd070Form" property="editTimezoneSid">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEmpty>
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<logic:notEmpty name="tcd070Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd070Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>
<html:hidden property="addTimezoneKbn" />
<html:hidden property="editTimezoneSid" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd030.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="OK" onClick="buttonPush('tcd070ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:notEmpty name="tcd070Form" property="editTimezoneSid">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('tcd070delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:notEmpty>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd070back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd070.03" />
      </th>
      <td class="w75">
        <html:select property="tcd070ZoneKbn">
          <html:optionsCollection name="tcd070Form" property="tcd070ZoneLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd070.01" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
        <gsmsg:write key="cmn.start" /><gsmsg:write key="wml.215" />
        <!-- 時 -->
        <html:select property="tcd070FrH" styleClass="ml5 mr5">
          <html:optionsCollection name="tcd070Form" property="tcd070HourLabel" value="value" label="label" />
        </html:select>
        <gsmsg:write key="cmn.hour.input" />
        <!-- 分 -->
        <html:select property="tcd070FrM" styleClass="ml5 mr5">
          <html:optionsCollection name="tcd070Form" property="tcd070MinuteLabel" value="value" label="label" />
        </html:select>
        <gsmsg:write key="cmn.minute.input" />
        </span>

        <span class="verAlignMid ml10">
        <gsmsg:write key="cmn.end" /><gsmsg:write key="wml.215" />
        <!-- 時 -->
        <html:select property="tcd070ToH" styleClass="ml5 mr5">
          <html:optionsCollection name="tcd070Form" property="tcd070HourLabel" value="value" label="label" />
        </html:select>
        <gsmsg:write key="cmn.hour.input" />
        <!-- 分 -->
        <html:select property="tcd070ToM" styleClass="ml5 mr5">
          <html:optionsCollection name="tcd070Form" property="tcd070MinuteLabel" value="value" label="label" />
        </html:select>
        <gsmsg:write key="cmn.minute.input" />
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd070ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:notEmpty name="tcd070Form" property="editTimezoneSid">
      <button type="button" class="baseBtn js_btn_dell_n1" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('tcd070delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="..//common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:notEmpty>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd070back');">
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