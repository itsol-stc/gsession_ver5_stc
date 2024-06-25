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
  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.tcd050.04" /></title>
</head>

<body>
<html:form action="/timecard/tcd050">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<html:hidden property="tcd050initFlg" />

<logic:notEmpty name="tcd050Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd050Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="tcd050DspHolidayYear" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd050_submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd050_back');">
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
        <gsmsg:write key="tcd.11" />
      </th>
      <td class="w75">
        <html:select property="tcd050BetweenSlt">
          <html:optionsCollection name="tcd050Form" property="tcd050BetweenLabel" value="value" label="label" />
        </html:select>
        <gsmsg:write key="tcd.tcd050.05" />
        <gsmsg:write key="wml.215" />
        <gsmsg:write key="tcd.tcd050.06" />
        <html:select property="tcd050SinsuSlt">
          <html:optionsCollection name="tcd050Form" property="tcd050SinsuLabel" value="value" label="label" />
        </html:select>
        <div class="mt10">
          <gsmsg:write key="tcd.54" /><br>
          <gsmsg:write key="tcd.53" /> <br>
          <span class="cl_fontWarn"><gsmsg:write key="tcd.tcd050.10" /></span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd050.02" />
      </th>
      <td class="w75">
        <html:select property="tcd050LimitDaySlt">
          <html:optionsCollection name="tcd050Form" property="tcd050LimitDayLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.187" />
      </th>
      <td class="w75">
        <div><gsmsg:write key="tcd.tcd050.11" /></div>
        <html:select property="tcd050DefTimezone">
          <html:optionsCollection name="tcd050Form" property="tcd050TimezoneList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.37" />
      </th>
      <td class="w75">
        <span class="cl_fontWarn"><gsmsg:write key="tcd.tcd050.09" /></span>
        <table class="table-top wp200 mt0">
        <tr>
          <td class="txt_c bgC_calSunday"><gsmsg:write key="cmn.day" /></td>
          <td class="txt_c bgC_body"><gsmsg:write key="cmn.Monday" /></td>
          <td class="txt_c bgC_body"><gsmsg:write key="cmn.tuesday" /></td>
          <td class="txt_c bgC_body"><gsmsg:write key="cmn.wednesday" /></td>
          <td class="txt_c bgC_body"><gsmsg:write key="cmn.thursday" /></td>
          <td class="txt_c bgC_body"><gsmsg:write key="cmn.friday" /></td>
          <td class="txt_c bgC_calSaturday"><gsmsg:write key="cmn.saturday" /></td>
        </tr>

        <tr>
          <td class="txt_c js_tableTopCheck cursor_p"><html:multibox property="tcd050SelectWeek" value="1"/></td>
          <td class="txt_c js_tableTopCheck bgC_body cursor_p"><html:multibox property="tcd050SelectWeek" value="2"/></td>
          <td class="txt_c js_tableTopCheck bgC_body cursor_p"><html:multibox property="tcd050SelectWeek" value="3"/></td>
          <td class="txt_c js_tableTopCheck bgC_body cursor_p"><html:multibox property="tcd050SelectWeek" value="4"/></td>
          <td class="txt_c js_tableTopCheck bgC_body cursor_p"><html:multibox property="tcd050SelectWeek" value="5"/></td>
          <td class="txt_c js_tableTopCheck bgC_body cursor_p"><html:multibox property="tcd050SelectWeek" value="6"/></td>
          <td class="txt_c js_tableTopCheck cursor_p"><html:multibox property="tcd050SelectWeek" value="7"/></td>
        </tr>
        </table>
      </td>
    </tr>

    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd050kn.04" />
      </th>
      <td class="w75">
        <span class="cl_fontWarn"><gsmsg:write key="tcd.tcd050.08" /></span>

        <div class="txt_r fw_b w80 paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('moveLeft');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.changes.previous.year" />">
            <i class="icon-paging_left"></i>
          </button>

          <bean:define id="yr" name="tcd050Form" property="tcd050DspHolidayYear" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= yr %>" />

          <button type="button" class="webIconBtn" onClick="buttonPush('moveRight');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.change.nextyear" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>

        <table class="w80 table-top mt0">
          <tr>
            <th class="w10"></th>
            <th class="w30"><gsmsg:write key="cmn.date2" /></th>
            <th class="w60"><gsmsg:write key="cmn.holiday.name" /></th>
          </tr>

          <logic:notEmpty name="tcd050Form" property="tcd050HolidayInfoList" scope="request">

            <logic:iterate id="holMdl" name="tcd050Form" property="tcd050HolidayInfoList" scope="request" indexId="idx">
              <tr>
                <td class="txt_c js_tableTopCheck cursor_p">
                  <html:multibox name="tcd050Form" property="tcd050SelectHoliDay">
                    <bean:write name="holMdl" property="strDate" />
                  </html:multibox>
                </td>
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
    <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd050_submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd050_back');">
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