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
  <title>GROUPSESSION <gsmsg:write key="tcd.tcd080.13" /></title>
</head>

<body class="body_03">
<html:form action="/timecard/tcd080">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<logic:notEmpty name="tcd080Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd080Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd080ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd080back');">
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

  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.time" />
      </th>
      <td class="w75">

        <div>
          <gsmsg:write key="cmn.starttime" /><gsmsg:write key="wml.215" />
          <!-- 時 -->
          <html:select property="tcd080DefFrH">
            <html:optionsCollection name="tcd080Form" property="tcd080HourLabel" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.hour.input" />
          <!-- 分 -->
          <html:select property="tcd080DefFrM">
            <html:optionsCollection name="tcd080Form" property="tcd080MinuteLabel" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.minute.input" />
        </div>
        <div class="mt5">
          <gsmsg:write key="cmn.endtime" /><gsmsg:write key="wml.215" />
          <!-- 時 -->
          <html:select property="tcd080DefToH">
            <html:optionsCollection name="tcd080Form" property="tcd080HourLabel" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.hour.input" />
          <!-- 分 -->
          <html:select property="tcd080DefToM">
            <html:optionsCollection name="tcd080Form" property="tcd080MinuteLabel" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.minute.input" />
        </div>

      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd080.11" />
      </th>
      <td class="w75">
        <div class="mb10"><gsmsg:write key="tcd.tcd080.06" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd080mainDsp_0" name="tcd080Form" property="tcd080mainDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" /><label for="tcd080mainDsp_0"><gsmsg:write key="cmn.display.ok" /></label>
        <html:radio styleId="tcd080mainDsp_1" styleClass="ml10" name="tcd080Form" property="tcd080mainDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_NOT_DSP) %>" /><label for="tcd080mainDsp_1"><gsmsg:write key="cmn.dont.show" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.zaiseki.management" />
      </th>
      <td class="w75">
        <div class="mb10"><gsmsg:write key="tcd.tcd080.12" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd080zaisekiSts_0" name="tcd080Form" property="tcd080zaisekiSts" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ZAISEKI_OFF) %>" /><label for="tcd080zaisekiSts_0"><gsmsg:write key="tcd.tcd080.02" /></label>
        <html:radio styleId="tcd080zaisekiSts_1" styleClass="ml10" name="tcd080Form" property="tcd080zaisekiSts" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ZAISEKI_ON) %>" /><label for="tcd080zaisekiSts_1"><gsmsg:write key="tcd.tcd080.01" /></label>
        </span>
      </td>
    </tr>
    <logic:equal name="tcd080Form" property="tcd080KinpDispFlg" value="0">
      <tr>
        <th class="w25">
          <gsmsg:write key="tcd.tcd080.08" />
        </th>
        <td class="w75">
          <div class="mb10"><gsmsg:write key="tcd.tcd080.09" /></div>
          <span  class="verAlignMid">
          <html:radio styleId="tcd080kinmuOutput_0" name="tcd080Form" property="tcd080kinmuOutput" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.KINMU_EXCEL) %>" /><label for="tcd080kinmuOutput_0"><gsmsg:write key="tcd.tcd080.15" /></label>
          <html:radio styleId="tcd080kinmuOutput_1" styleClass="ml10" name="tcd080Form" property="tcd080kinmuOutput" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.KINMU_PDF) %>" /><label for="tcd080kinmuOutput_1"><gsmsg:write key="tcd.tcd080.14" /></label>
          </span>
        </td>
      </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd080ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd080back');">
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