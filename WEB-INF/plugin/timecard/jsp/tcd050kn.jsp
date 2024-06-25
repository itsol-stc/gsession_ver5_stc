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
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.tcd050.04" /></title>
</head>

<body>
<html:form action="/timecard/tcd050kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<logic:notEmpty name="tcd050knForm" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd050knForm" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="tcd050initFlg" />
<html:hidden property="tcd050BetweenSlt" />
<html:hidden property="tcd050SinsuSlt" />
<html:hidden property="tcd050LimitDaySlt" />
<html:hidden property="tcd050DspHolidayYear" />
<html:hidden property="tcd050DefTimezone" />
<html:hidden property="tcd050knDefTimezoneName" />
<logic:notEmpty name="tcd050knForm" property="tcd050SelectWeek" scope="request">
<logic:iterate id="selectWeek" name="tcd050knForm" property="tcd050SelectWeek" scope="request">
  <input type="hidden" name="tcd050SelectWeek" value="<bean:write name="selectWeek" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="tcd050knForm" property="tcd050SelectHoliDay" scope="request">
<logic:iterate id="selectHol" name="tcd050knForm" property="tcd050SelectHoliDay" scope="request">
  <input type="hidden" name="tcd050SelectHoliDay" value="<bean:write name="selectHol" />">
</logic:iterate>
</logic:notEmpty>
<logic:empty name="tcd050knForm" property="tcd050SelectHoliDay" scope="request">
<input type="hidden" name="tcd050SelectHoliDay" value="">
</logic:empty>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="cmn.preferences.kn" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('tcd050kn_submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd050kn_back');">
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

  <div class="cl_fontWarn txt_l"><gsmsg:write key="tcd.45" /></div>
  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.11" />
      </th>
      <td class="w75">
        <span class="cl_fontWarn"><bean:write name="tcd050knForm" property="tcd050BetweenSlt" /><gsmsg:write key="cmn.minute" /></span>
        <gsmsg:write key="tcd.tcd050.05" />
          <gsmsg:write key="wml.215" />
        <gsmsg:write key="tcd.tcd050.06" />
        <span class="cl_fontWarn">
        <bean:write name="tcd050knForm" property="tcd050SinsuSlt" /><gsmsg:write key="tcd.tcd050kn.03" /></span>
        <div class="mt10">
          <gsmsg:write key="tcd.54" /><br>
          <gsmsg:write key="tcd.53" /><br>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd050.02" />
      </th>
      <td class="w75">
        <span class="cl_fontWarn">
        <logic:equal name="tcd050knForm" property="tcd050LimitDaySlt" value="99">
          <gsmsg:write key="tcd.tcd050kn.01" />
        </logic:equal>
        <logic:notEqual name="tcd050knForm" property="tcd050LimitDaySlt" value="99">
          <bean:write name="tcd050knForm" property="tcd050LimitDaySlt" /><gsmsg:write key="cmn.day" />
        </logic:notEqual>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.187" />
      </th>
      <td class="w75">
        <bean:write name="tcd050knForm" property="tcd050knDefTimezoneName" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.37" />
      </th>
      <td class="w75">
        <gsmsg:write key="tcd.tcd050kn.07" />
        <table class="table-left wp200 mt0">
          <tr class="txt_c">
            <td class="txt_c bgC_calSunday"><gsmsg:write key="cmn.day" /></td>
            <td class="txt_c"><gsmsg:write key="cmn.Monday" /></td>
            <td class="txt_c"><gsmsg:write key="cmn.tuesday" /></td>
            <td class="txt_c"><gsmsg:write key="cmn.wednesday" /></td>
            <td class="txt_c"><gsmsg:write key="cmn.thursday" /></td>
            <td class="txt_c"><gsmsg:write key="cmn.friday" /></td>
            <td class="txt_c bgC_calSaturday"><gsmsg:write key="cmn.saturday" /></td>
          </tr>
          <tr>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekSun" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekMon" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekTue" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekWed" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekThu" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekFri" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
            <td class="txt_c txt_m">
              <logic:equal name="tcd050knForm" property="weekSat" value="1">
              <img src="../timecard/images/classic/icon_locked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="tcd.tcd050kn.06" />"  class="btn_originalImg-display">
              </logic:equal>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd050kn.04" />
      </th>
      <td class="w75">
        <gsmsg:write key="tcd.tcd050kn.08" />
        <div class="txt_r w70">
          <bean:define id="yr" name="tcd050knForm" property="tcd050DspHolidayYear" type="java.lang.String" />
          <span class="fw_b"><gsmsg:write key="cmn.year" arg0="<%= yr %>" /></span>
        </div>
        <table class="w70 table-top mt0">
          <tr>
            <th class="w30"><gsmsg:write key="cmn.date2" /></th>
            <th class="w70"><gsmsg:write key="cmn.holiday.name" /></th>
          </tr>
          <logic:notEmpty name="tcd050knForm" property="tcd050HolidayInfoList" scope="request">

          <logic:iterate id="holMdl" name="tcd050knForm" property="tcd050HolidayInfoList" scope="request" indexId="idx">
            <tr>
              <td class="txt_c"><bean:write name="holMdl" property="viewDate" /></td>
              <td class="txt_c"><bean:write name="holMdl" property="holName" /></td>
            </tr>
          </logic:iterate>
          </logic:notEmpty>
        </table>

      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('tcd050kn_submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd050kn_back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>