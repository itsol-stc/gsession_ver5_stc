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
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch081.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body onload="changeEnableDisable();showOrHide();">
<html:form action="/schedule/sch081">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="hourDivision" value="<bean:write name="sch081Form" property="sch081HourDiv" />" />

<%@ include file="/WEB-INF/plugin/schedule/jsp/sch080_hiddenParams.jsp" %>

<logic:notEmpty name="sch081Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch081Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch081Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch081Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch081Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch081Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch081Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch081Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch081Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch081Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch081kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch081back');">
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
        <gsmsg:write key="schedule.123" />
      </th>
      <td class="w75">
        <gsmsg:write key="schedule.124" />
        <div>
        <div class="mt5 verAlignMid">
        <html:radio name="sch081Form" property="sch081Crange" styleId="sch081Crange0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CRANGE_SHARE_ALL) %>" /><label for="sch081Crange0"><gsmsg:write key="schedule.125" /></label>&nbsp;
        </div>
        <div class="verAlignMid">
        <html:radio name="sch081Form" property="sch081Crange" styleId="sch081Crange1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.CRANGE_SHARE_GROUP) %>" /><label for="sch081Crange1"><gsmsg:write key="schedule.src.2" /></label>&nbsp;
        </div>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.123" />
      </th>
      <td>
        <div>
          <gsmsg:write key="schedule.126" />
        </div>
        <div class="mt5 verAlignMid">
          <html:radio name="sch081Form" property="sch081HourDiv" styleId="sch081HourDiv0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.HOUR_DIVISION5) %>" />
          <label for="sch081HourDiv0"><gsmsg:write key="reserve.rsv220.3" /></label>
          <html:radio name="sch081Form" property="sch081HourDiv" styleId="sch081HourDiv1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DF_HOUR_DIVISION) %>" />
          <label for="sch081HourDiv1"><gsmsg:write key="reserve.rsv220.4" /></label>
          <html:radio name="sch081Form" property="sch081HourDiv" styleId="sch081HourDiv2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.HOUR_DIVISION15) %>" />
          <label for="sch081HourDiv2"><gsmsg:write key="reserve.rsv220.5" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.time.master" />
      </th>
      <td>
      <gsmsg:write key="schedule.sch081.1" /><br>
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.am" />：
          <span class="clockpicker_fr ml5 mr5 pos_rel display_flex input-group">
            <html:text name="sch081Form" property="sch081AmFrTime" styleId="fr_clock1" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock1" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          ～
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="sch081Form" property="sch081AmToTime" styleId="to_clock1" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock1" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.pm" />：
          <span class="clockpicker_fr ml5 mr5 pos_rel display_flex input-group">
            <html:text name="sch081Form" property="sch081PmFrTime" styleId="fr_clock2" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock2" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          ～
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="sch081Form" property="sch081PmToTime" styleId="to_clock2" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock2" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.allday" />：
          <span class="clockpicker_fr ml5 mr5 pos_rel display_flex input-group">
            <html:text name="sch081Form" property="sch081AllDayFrTime" styleId="fr_clock3" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock3" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          ～
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="sch081Form" property="sch081AllDayToTime" styleId="to_clock3" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock3" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          </div>
        </td>
      </tr>
      <tr>
        <th>
          <gsmsg:write key="schedule.128" />
        </th>
        <td>
          <gsmsg:write key="schedule.127" /><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleBlue"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment1" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleRed"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment2" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleGreen"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment3" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleYellow"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment4" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
           <span class="bgc_fontSchTitleBlack"><span class="pl15"></span></span>
           <html:text name="sch081Form" maxlength="10" property="sch081ColorComment5" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt10 verAlignMid">
          <html:radio name="sch081Form" property="sch081colorKbn" styleId="sch081colorKbn0" value="0" />
          <label for="sch081colorKbn0"><gsmsg:write key="schedule.168" /></label>
          <html:radio name="sch081Form" property="sch081colorKbn" styleId="sch081colorKbn1" styleClass="ml10" value="1" />
          <label for="sch081colorKbn1"><gsmsg:write key="schedule.169" /></label>
        </div>
        <div class="add_title_color display_none">
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleNavy"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment6" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleWine"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment7" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleCien"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment8" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleGray"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment9" styleClass="wp100 ml5" />
        </div><br>
        <div class="mt5 verAlignMid">
          <span class="bgc_fontSchTitleMarine"><span class="pl15"></span></span>
          <html:text name="sch081Form" maxlength="10" property="sch081ColorComment10" styleClass="wp100 ml5" />
        </div>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="schedule.148" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch081Form" styleId="sch081RepertType_01" property="sch081RepeatKbnType" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAD_REPEAT_STYPE_ADM) %>" onclick="changeEnableDisable();" />
          <label for="sch081RepertType_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch081Form" styleId="sch081RepertType_02" property="sch081RepeatKbnType" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAD_REPEAT_STYPE_USER) %>" onclick="changeEnableDisable();" />
          <label for="sch081RepertType_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div class="settingForm_separator">
          <div id="lmtinput"><gsmsg:write key="cmn.comments" />
            <gsmsg:write key="cmn.view.account.defaultset" />
          </div>
          <span class="verAlignMid">
              <html:radio onclick="showOrHide();" name="sch081Form" styleId="sch081Repeat_00" property="sch081RepeatKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_REPEAT_KBN_OK) %>" />
              <label for="sch081Repeat_00"><gsmsg:write key="cmn.accepted" /></label>
              <html:radio onclick="showOrHide();" name="sch081Form" styleId="sch081Repeat_01" styleClass="ml10"  property="sch081RepeatKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_REPEAT_KBN_NG) %>" />
              <label for="sch081Repeat_01"><gsmsg:write key="cmn.not" /></label>
              <html:radio onclick="showOrHide();" name="sch081Form" styleId="sch081Repeat_02" styleClass="ml10"  property="sch081RepeatKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_REPEAT_KBN_WARNING) %>" />
              <label for="sch081Repeat_02"><gsmsg:write key="schedule.sch097.1" /></label>
            </span>
        </div>
        <div class="mt5 verAlignMid" id="repertMyKbnArea">
          <html:checkbox name="sch081Form" property="sch081RepeatMyKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_REPEAT_MY_KBN_OK) %>" styleId="repeatMyKbn" />
          <label for="repeatMyKbn"><gsmsg:write key="schedule.sch097.2" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch081kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch081back');">
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
