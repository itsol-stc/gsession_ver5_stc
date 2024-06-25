<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<% String holNone = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_UNSELECT); %>

<% String chkNone = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.CHK_KBN_UNSELECT); %>
<% String chikoku = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.CHK_KBN_SELECT); %>
<% String souNone = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SOU_KBN_UNSELECT); %>
<% String soutai = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SOU_KBN_SELECT); %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd020.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.tcd020.07" /></title>
</head>

<body onload="init();">

<html:form action="/timecard/tcd020" onsubmit="return false" >
<input type="hidden" name="CMD" value="">
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<html:hidden property="editDay" />

<logic:notEmpty name="tcd020Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd020Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="usrKbn" />
<html:hidden property="tcd020LockFlg" />
<html:hidden property="tcd020LockStrike" />
<html:hidden property="tcd020LockBiko" />
<html:hidden property="tcd020LockLate" />
<html:hidden property="tcd020LockHoliday" />
<html:hidden property="pluralFlg" />
<html:hidden property="tcd020HolidayContentKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../timecard/images/classic/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
      <img class="header_pluginImg" src="../timecard/images/original/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
    </li>
    <li><gsmsg:write key="tcd.50" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.edit" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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
        <gsmsg:write key="cmn.name4" />
      </th>
      <td class="w75">
        <bean:write name="tcd020Form" property="tcd020Name"/>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w75">
        <html:hidden name="tcd020Form" property="tcd020Date" write="true" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.184" />
      </th>
      <td class="w75">
        <logic:equal name="tcd020Form" property="tcd020LockTimezone" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>">
          <logic:notEmpty name="tcd020Form" property="tcd020NotUseTimeozoneName">
            <div class="cl_fontWarn">
              <bean:write name="tcd020Form" property="tcd020NotUseTimeozoneName"/>
            </div>
          </logic:notEmpty>
          <html:select name="tcd020Form" property="tcd020Timezone">
            <html:optionsCollection name="tcd020Form" property="tcd020TimezoneList" label="label" value="value" />
          </html:select>
        </logic:equal>
        <logic:equal name="tcd020Form" property="tcd020LockTimezone" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>">
          <bean:write name="tcd020Form" property="tcd020TimezoneStr" />
        </logic:equal>
      </td>
    </tr>

    <logic:equal name="tcd020Form" property="pluralFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.PLURAL_FLG_SINGLE) %>">
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.21" />
      </th>
      <td class="w75">
        <logic:equal name="tcd020Form" property="tcd020LockStrike" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>">
          <html:select name="tcd020Form" property="tcd020StrikeInHour">
            <html:optionsCollection name="tcd020Form" property="tcd020HourLabel" label="label" value="value" />
          </html:select>
          <gsmsg:write key="cmn.hour.input" />
          <html:select name="tcd020Form" property="tcd020StrikeInMinute">
            <html:optionsCollection name="tcd020Form" property="tcd020StrikeMinuteLabel" />
          </html:select>
          <gsmsg:write key="cmn.minute.input" />
          <button type="button" class="baseBtn mr5" value="<gsmsg:write key="cmn.clear" />" onClick="clearValue('tcd020StrikeInHour', 'tcd020StrikeInMinute')">
            <gsmsg:write key="cmn.clear" />
          </button>
          <gsmsg:write key="tcd.153" />
          <html:select name="tcd020Form" property="tcd020StrikeOutHour" styleClass="ml5">
            <html:optionsCollection name="tcd020Form" property="tcd020HourLabel" />
          </html:select>
          <gsmsg:write key="cmn.hour.input" />
          <html:select name="tcd020Form" property="tcd020StrikeOutMinute">
            <html:optionsCollection name="tcd020Form" property="tcd020StrikeMinuteLabel" />
          </html:select>
          <gsmsg:write key="cmn.minute.input" />
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" onClick="clearValue('tcd020StrikeOutHour', 'tcd020StrikeOutMinute')">
            <gsmsg:write key="cmn.clear" />
          </button>
        </logic:equal>

        <logic:equal name="tcd020Form" property="tcd020LockStrike" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>">
          <bean:write name="tcd020Form" property="tcd020StrikeTimeStr" />
        </logic:equal>
      </td>
    </tr>
    </logic:equal>

    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.28" />
      </th>
      <td class="w75">
        <logic:equal name="tcd020Form" property="tcd020LockFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>">
          <html:select name="tcd020Form" property="tcd020InHour">
            <html:optionsCollection name="tcd020Form" property="tcd020HourLabel" label="label" value="value" />
          </html:select>
          <gsmsg:write key="cmn.hour.input" />
          <html:select name="tcd020Form" property="tcd020InMinute">
            <html:optionsCollection name="tcd020Form" property="tcd020MinuteLabel" />
          </html:select>
          <gsmsg:write key="cmn.minute.input" />
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" onClick="clearValue('tcd020InHour', 'tcd020InMinute')">
            <gsmsg:write key="cmn.clear" />
          </button>
        </logic:equal>

        <logic:equal name="tcd020Form" property="tcd020LockFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>">
          <bean:write name="tcd020Form" property="tcd020InTimeStr" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.24" />
      </th>
      <td class="w75">
        <logic:equal name="tcd020Form" property="tcd020LockFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>">
          <html:select name="tcd020Form" property="tcd020OutHour" >
            <html:optionsCollection name="tcd020Form" property="tcd020HourLabel" />
          </html:select>
          <gsmsg:write key="cmn.hour.input" />
          <html:select name="tcd020Form" property="tcd020OutMinute">
            <html:optionsCollection name="tcd020Form" property="tcd020MinuteLabel" />
          </html:select>
          <gsmsg:write key="cmn.minute.input" />
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" onClick="clearValue('tcd020OutHour', 'tcd020OutMinute')">
            <gsmsg:write key="cmn.clear" />
          </button>
        </logic:equal>

        <logic:equal name="tcd020Form" property="tcd020LockFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>">
          <bean:write name="tcd020Form" property="tcd020OutTimeStr" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <html:text name="tcd020Form" property="tcd020Biko" styleClass="wp600" maxlength="100" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd020.03" />
      </th>
      <td class="w75">
      <logic:equal name="tcd020Form" property="tcd020LockLate" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>">
        <gsmsg:write key="tcd.tcd020.02" /><gsmsg:write key="wml.215" />
        <logic:equal name="tcd020Form" property="tcd020ChkKbn" value="<%= chkNone %>"><gsmsg:write key="cmn.without.specifying" /></logic:equal>
        <logic:equal name="tcd020Form" property="tcd020ChkKbn" value="<%= chikoku %>"><gsmsg:write key="tcd.18" /></logic:equal>
        <br>
        <gsmsg:write key="tcd.tcd020.04" /><gsmsg:write key="wml.215" />
        <logic:equal name="tcd020Form" property="tcd020SouKbn" value="<%= souNone %>"><gsmsg:write key="cmn.without.specifying" /></logic:equal>
        <logic:equal name="tcd020Form" property="tcd020SouKbn" value="<%= soutai %>"><gsmsg:write key="tcd.22" /></logic:equal>
        </logic:equal>
        <logic:equal name="tcd020Form" property="tcd020LockLate" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>">
        <span class="verAlignMid">
        <html:radio styleId="tcd020ChkKbn_0" name="tcd020Form" property="tcd020ChkKbn" value="<%= chkNone %>"/><label for="tcd020ChkKbn_0"><gsmsg:write key="cmn.without.specifying" /></label>
        </span>
        <span class="verAlignMid ml10">
        <html:radio styleId="tcd020ChkKbn_1" name="tcd020Form" property="tcd020ChkKbn" value="<%= chikoku %>"/><label for="tcd020ChkKbn_1"><gsmsg:write key="tcd.18" /></label>
        </span>
        <br>
        <span class="verAlignMid">
        <html:radio styleId="tcd020SouKbn_0" name="tcd020Form" property="tcd020SouKbn" value="<%= souNone %>"/><label for="tcd020SouKbn_0"><gsmsg:write key="cmn.without.specifying" /></label>
        </span>
        <span class="verAlignMid ml10">
        <html:radio styleId="tcd020SouKbn_1" name="tcd020Form" property="tcd020SouKbn" value="<%= soutai %>"/><label for="tcd020SouKbn_1"><gsmsg:write key="tcd.22" /></label>
        </span>
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.40" />
      </th>
      <td class="w75">
      <logic:equal name="tcd020Form" property="tcd020LockHoliday" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>">
        <div class="cl_fontWarn"><gsmsg:write key="tcd.tcd020.08" /></div>
        <logic:notEmpty name="tcd020Form" property="tcd020NotUseHolKbnName">
          <div class="cl_fontWarn">
            <bean:write name="tcd020Form" property="tcd020NotUseHolKbnName"/>
          </div>
        </logic:notEmpty>
        <span class="verAlignMid">
          <html:select name="tcd020Form" property="tcd020HolKbn" onchange="holComboChange()">
            <html:optionsCollection name="tcd020Form" property="tcd020HolKbnList" />
          </html:select>
        </span>
        <span class="ml10">
          <gsmsg:write key="tcd.tcd020.06" /><gsmsg:write key="wml.215" />
          <html:text name="tcd020Form" property="tcd020HolDays" styleClass="wp55" maxlength="7" />
        </span>
        <div class="mt5" id="js_holValue">
          <gsmsg:write key="tcd.tcd170.08" /><gsmsg:write key="wml.215" />
          <html:text name="tcd020Form" property="tcd020HolValue" styleClass="wp200" maxlength="10" />
        </div>
      </logic:equal>
      <logic:equal name="tcd020Form" property="tcd020LockHoliday" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>">
        <html:hidden property="tcd020HolKbn" />
        <span class="fw_b"><gsmsg:write key="tcd.40" /><gsmsg:write key="cmn.colon" /></span>
        <logic:equal name="tcd020Form" property="tcd020HolKbn" value="0"><gsmsg:write key="cmn.without.specifying" /></logic:equal>
        <logic:notEqual name="tcd020Form" property="tcd020HolKbn" value="0">
          <bean:write name="tcd020Form" property="tcd020HolKbnName" />
          <span class="ml20"><gsmsg:write key="tcd.tcd020.06" /><gsmsg:write key="wml.215" />
            <bean:write name="tcd020Form" property="tcd020HolDays" />
          </span>
        </logic:notEqual>
        <logic:notEmpty name="tcd020Form" property="tcd020HolValue">
          <div>
            <gsmsg:write key="tcd.tcd170.08" /><gsmsg:write key="wml.215" />
            <bean:write name="tcd020Form" property="tcd020HolValue" />
          </div>
        </logic:notEmpty>
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.update.user" />
      </th>
      <td class="w75">
        <bean:write name="tcd020Form" property="tcd020UpdateUserName" />
      </td>
    </tr>

    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.update.day.hour" />
      </th>
      <td class="w75">
        <bean:write name="tcd020Form" property="tcd020UpdateDate" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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