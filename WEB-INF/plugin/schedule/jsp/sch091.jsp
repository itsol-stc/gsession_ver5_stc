<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../schedule/js/sch060.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch091.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body>
<html:form action="/schedule/sch091">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="hourDivision" value="<bean:write name="sch091Form" property="sch091HourDiv" />" />

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
<html:hidden property="sch091ReminderDspFlg" />

<logic:notEmpty name="sch091Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch091Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch091Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch091Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch091Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch091Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch091Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch091Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch091Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch091Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:equal name="sch091Form" property="sch091EditFlg" value="false">
  <html:hidden property="sch091Edit" />
</logic:equal>
<html:hidden property="sch091initFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch091kakunin');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch091back');">
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
  <logic:equal name="sch091Form" property="sch091TimeFlg" value="true">
    <tr>
      <th class="w25">
      <gsmsg:write key="cmn.time" />
      </th>
      <td class="w75">
        <!-- 開始時間 -->
        <gsmsg:write key="cmn.starttime" />：
        <div class="verAlignMid">
          <span class="clockpicker_fr pos_rel display_flex input-group">
            <html:text name="sch091Form" property="sch091DefFrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <!-- 終了時間 -->
        <gsmsg:write key="cmn.endtime" />：
        <div class="mt5 verAlignMid">
          <span class="clockpicker_fr pos_rel display_flex input-group">
            <html:text name="sch091Form" property="sch091DefToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
  </logic:equal>
    <tr>
      <th>
        <gsmsg:write key="schedule.10" />
      </th>
      <td>
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
        <logic:notEmpty name="sch091Form" property="sch091ColorMsgList">

          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
              <html:radio name="sch091Form" property="sch091Fcolor" value="1" styleId="bg_color1" />
            </div>
            <label for="bg_color1" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[0]"/></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleRed">
              <html:radio name="sch091Form" property="sch091Fcolor" value="2" styleId="bg_color2"/>
            </div>
            <label for="bg_color2" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[1]"/></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
              <html:radio name="sch091Form" property="sch091Fcolor" value="3" styleId="bg_color3"/>
            </div>
            <label for="bg_color3" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[2]"/></label>
          </div>
          <div class="verAlignMid mr5">
            <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
              <html:radio name="sch091Form" property="sch091Fcolor" value="4" styleId="bg_color4"/>
            </div>
            <label for="bg_color4" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[3]"/></label>
          </div>
          <div class="verAlignMid">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
              <html:radio name="sch091Form" property="sch091Fcolor" value="5" styleId="bg_color5"/>
            </div>
            <label for="bg_color5" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[4]"/></label>
          </div>
          <logic:equal name="sch091Form" property="sch091colorKbn" value="1">
            <br>
            <div class="verAlignMid mr5">
              <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
                <html:radio name="sch091Form" property="sch091Fcolor" value="6" styleId="bg_color6"/>
              </div>
              <label for="bg_color6" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[5]"/></label>
            </div>
            <div class="verAlignMid mr5">
              <div class="cal_titlecolor-block bgc_fontSchTitleWine">
                <html:radio name="sch091Form" property="sch091Fcolor" value="7" styleId="bg_color7"/>
              </div>
              <label for="bg_color7" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[6]"/></label>
            </div>
            <div class="verAlignMid mr5">
              <div class="cal_titlecolor-block bgc_fontSchTitleCien">
                <html:radio name="sch091Form" property="sch091Fcolor" value="8" styleId="bg_color8"/>
              </div>
              <label for="bg_color8" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[7]"/></label>
            </div>
            <div class="verAlignMid mr5">
              <div class="cal_titlecolor-block bgc_fontSchTitleGray">
                <html:radio name="sch091Form" property="sch091Fcolor" value="9" styleId="bg_color9"/>
              </div>
              <label for="bg_color9" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[8]"/></label>
            </div>
            <div class="verAlignMid">
              <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
                <html:radio name="sch091Form" property="sch091Fcolor" value="10" styleId="bg_color10"/>
              </div>
              <label for="bg_color10" class="ml5"><bean:write name="sch091Form" property="sch091ColorMsgList[9]"/></label>
            </div>
          </logic:equal>
        </logic:notEmpty >
        <logic:empty name="sch091Form" property="sch091ColorMsgList">
          <div class="verAlignMid">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
              <html:radio name="sch091Form" property="sch091Fcolor" value="1" styleId="bg_color1" />
            </div>
            <label for="bg_color1" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleRed">
              <html:radio name="sch091Form" property="sch091Fcolor" value="2" styleId="bg_color2"/>
            </div>
            <label for="bg_color2" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
              <html:radio name="sch091Form" property="sch091Fcolor" value="3" styleId="bg_color3"/>
            </div>
            <label for="bg_color3" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
              <html:radio name="sch091Form" property="sch091Fcolor" value="4" styleId="bg_color4"/>
            </div>
            <label for="bg_color4" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
              <html:radio name="sch091Form" property="sch091Fcolor" value="5" styleId="bg_color5"/>
            </div>
            <label for="bg_color5" class="ml5"></label>
          </div>
          <logic:equal name="sch091Form" property="sch091colorKbn" value="1">
            <br>
            <div class="verAlignMid">
              <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
                <html:radio name="sch091Form" property="sch091Fcolor" value="6" styleId="bg_color6"/>
              </div>
              <label for="bg_color6" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleWine">
                <html:radio name="sch091Form" property="sch091Fcolor" value="7" styleId="bg_color7"/>
              </div>
              <label for="bg_color7" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleCien">
                <html:radio name="sch091Form" property="sch091Fcolor" value="8" styleId="bg_color8"/>
              </div>
              <label for="bg_color8" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleGray">
                <html:radio name="sch091Form" property="sch091Fcolor" value="9" styleId="bg_color9"/>
              </div>
              <label for="bg_color9" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
                <html:radio name="sch091Form" property="sch091Fcolor" value="10" styleId="bg_color10"/>
              </div>
              <label for="bg_color10" class="ml5"></label>
            </div>
          </logic:equal>
        </logic:empty>
      </td>
    </tr>
    <logic:equal name="sch091Form" property="sch091EditFlg" value="true">
    <tr>
      <th>
      <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch091Form" property="sch091Edit" styleId="sch091Edit0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_NONE) %>" />
          <label for="sch091Edit0"><gsmsg:write key="cmn.nolimit" /></label>
          <html:radio name="sch091Form" property="sch091Edit" styleId="sch091Edit1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_OWN) %>" />
          <label for="sch091Edit1"><gsmsg:write key="cmn.only.principal.or.registant" /></label>
          <html:radio name="sch091Form" property="sch091Edit" styleId="sch091Edit2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_GROUP) %>" />
          <label for="sch091Edit2"><gsmsg:write key="cmn.only.affiliation.group.membership" /></label>
        </span>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="sch091Form" property="sch091ReminderDspFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.REMINDER_USE_YES) %>">
    <tr>
      <th><gsmsg:write key="cmn.reminder" /></th>
      <td>
        <div>
          <html:select name="sch091Form" property="sch091ReminderTime" styleClass="js_reminder">
            <html:optionsCollection name="sch091Form" property="reminderTimeList" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>
    </logic:equal>
    <logic:notEqual name="sch091Form" property="sch091ReminderDspFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.REMINDER_USE_YES) %>">
      <html:hidden name="sch091Form" property="sch091ReminderTime" />
    </logic:notEqual>
    <logic:equal name="sch091Form" property="sch091PublicFlg" value="true">
    <tr>
      <th>
        <gsmsg:write key="cmn.public" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sch091Form" property="sch091PubFlg" styleId="sch091PubFlg0" styleClass="js_public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_PUBLIC) %>" />
          <label for="sch091PubFlg0" class="mr10"><gsmsg:write key="cmn.public" /></label>
        </div><!--
     --><div class="verAlignMid">
          <html:radio name="sch091Form" property="sch091PubFlg" styleId="sch091PubFlg1" styleClass="js_public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_NOT_PUBLIC) %>" />
          <label for="sch091PubFlg1" class="mr10"><gsmsg:write key="cmn.private" /></label>
        </div><!--
     --><div class="verAlignMid">
          <html:radio name="sch091Form" property="sch091PubFlg" styleId="sch091PubFlg2" styleClass="js_public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_YOTEIARI) %>" />
          <label for="sch091PubFlg2" class="mr10"><gsmsg:write key="schedule.5" /></label>
        </div><!--
     --><div class="verAlignMid">
          <html:radio name="sch091Form" property="sch091PubFlg" styleId="sch091PubFlg5" styleClass="js_public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_TITLE) %>" />
          <label for="sch091PubFlg5" class="mr10"><gsmsg:write key="schedule.38" /></label>
        </div><!--
     --><div class="verAlignMid">
          <html:radio name="sch091Form" property="sch091PubFlg" styleId="sch091PubFlg3" styleClass="js_public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_BELONG_GROUP) %>" />
          <label for="sch091PubFlg3" class="mr10"><gsmsg:write key="schedule.28" /></label>
        </div><!--
     --><div class="verAlignMid">
          <html:radio name="sch091Form" property="sch091PubFlg" styleId="sch091PubFlg4" styleClass="js_public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_USRGRP) %>" />
          <label for="sch091PubFlg4"><gsmsg:write key="schedule.37" /></label>
        </div>
        <div class="settingForm_separator w100 js_selectUsrArea">
          <ui:multiselector name="sch091Form" property="sch091PubUsrGrpSidUI" styleClass="hp200" />
        </div>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="sch091Form" property="sch091SameFlg" value="true">
    <tr>
      <th>
      <gsmsg:write key="schedule.32" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch091Form" property="sch091Same" styleId="sch091Same0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAME_EDIT_ON) %>" />
          <label for="sch091Same0"><gsmsg:write key="schedule.34" /></label>
          <html:radio name="sch091Form" property="sch091Same" styleId="sch091Same1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAME_EDIT_OFF) %>" />
          <label for="sch091Same1"><gsmsg:write key="schedule.33" /></label>
        </span>
      </td>
    </tr>
    </logic:equal>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.initial.display" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio name="sch091Form" styleId="sch091Dsp_04" property="sch091DefDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_DAY) %>" />
          <label for="sch091Dsp_04"><gsmsg:write key="cmn.days2" /></label>
          <html:radio name="sch091Form" styleId="sch091Dsp_03" styleClass="ml10" property="sch091DefDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_WEEK) %>" />
          <label for="sch091Dsp_03"><gsmsg:write key="cmn.weeks" /></label>
          <html:radio name="sch091Form" styleId="sch091Dsp_02" styleClass="ml10" property="sch091DefDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_MONTH) %>" />
          <label for="sch091Dsp_02"><gsmsg:write key="cmn.monthly" /></label>
          <html:radio name="sch091Form" styleId="sch091Dsp_01" styleClass="ml10" property="sch091DefDsp" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_PRI_WEEK) %>" />
          <label for="sch091Dsp_01"><gsmsg:write key="schedule.19" /></label>
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch091kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch091back');">
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