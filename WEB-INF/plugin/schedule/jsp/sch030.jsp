<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailySchedule.tld" prefix="dailySchedule" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/schedule/" prefix="schedule" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch030.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../schedule/js/sch030.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="sch030Form" property="sch030Reload" value="0">
    var reloadinterval = <bean:write name="sch030Form" property="sch030Reload" />;
    var reloadEvt  = setTimeout("buttonPush('reload', true)",reloadinterval);
  </logic:notEqual>
-->
</script>
<jsp:include page="/WEB-INF/plugin/schedule/jsp/sch010_message.jsp" />

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body onload="window_create();visibleChange();" onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);" class="m0 visibility-hidden">
<html:form action="/schedule/sch030" styleClass="js_schForm">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="sch010SelectUsrSid" value="-1">
<input type="hidden" name="sch010SelectUsrKbn" value="0">
<input type="hidden" name="selectDate" value="">
<input type="hidden" name="selectUser" value="">
<input type="hidden" name="selectKbn" value="">

<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="sch010DspDate" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010CrangeKbn" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch030FromHour" />
<html:hidden property="sch030Reload" />

<bean:define id="fromHour" name="sch030Form" property="sch030FromHour" scope="request"/>
<bean:define id="toHour" name="sch030Form" property="sch030ToHour" scope="request"/>
<bean:define id="totalCols" name="sch030Form" property="sch030TotalCols" scope="request"/>
<bean:define id="adminKbn" name="sch030Form" property="adminKbn" scope="request"/>
<bean:define id="memoriCount" name="sch030Form" property="sch030MemoriCount" scope="request"/>

<logic:notEmpty name="sch030Form" property="schNotAccessGroupList" scope="request">
  <logic:iterate id="notAccessGroup" name="sch030Form" property="schNotAccessGroupList" scope="request">
    <input type="hidden" name="schNotAccessGroup" value="<bean:write name="notAccessGroup"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="schIkkatsuFlg" value="0">
<input type="hidden" name="schIkkatsuRemoveKey" value="">
<html:hidden property="schIkkatsuViewMode" />
<html:hidden property="schIkkatsuKakuninViewMode" />
<logic:notEmpty name="sch030Form" property="schIkkatuTorokuSaveKey" scope="request">
  <logic:iterate id="ikkatsuSaveKey" name="sch030Form" property="schIkkatuTorokuSaveKey" scope="request">
    <input type="hidden" name="schIkkatuTorokuKey" value="<bean:write name="ikkatsuSaveKey"/>" id="sch030IkkatsuSaveKey_<bean:write name="ikkatsuSaveKey"/>" class="js_saveIkkatsuKey">
  </logic:iterate>
</logic:notEmpty>

<span class="js_sch_top_frame topFrame-fixed m0">
  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
  <div class="pageTitle">
    <ul>
      <li>
        <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
        <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
      </li>
      <li>
        <gsmsg:write key="schedule.108" />
      </li>
      <li class="pageTitle_subFont">
        <gsmsg:write key="cmn.days2" />
      </li>
      <li>
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onClick="buttonPush('reload', false);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
            <gsmsg:write key="cmn.reload" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.pdf" />" onClick="buttonPush('pdf');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
            <gsmsg:write key="cmn.pdf" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('import');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <gsmsg:write key="cmn.import" />
          </button>
        </div>
      </li>
    </ul>
  </div>

  <div class="wrapper js_easyDispArea">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>

    <div class="verAlignMid w100">
      <span class="wp200"></span>
      <span class="mrl_auto"></span>
    <logic:notEmpty name="sch030Form" property="sch010TopList">
      <bean:size id="topSize" name="sch030Form" property="sch010TopList" scope="request" />
      <logic:equal name="topSize" value="2">
        <logic:iterate id="weekBean" name="sch030Form" property="sch010TopList" scope="request" offset="1"/>
      </logic:equal>
      <logic:notEqual name="topSize" value="2">
        <logic:iterate id="weekBean" name="sch030Form" property="sch010TopList" scope="request" offset="0"/>
      </logic:notEqual>
      <bean:define id="usrBean" name="weekBean" property="sch010UsrMdl" />
    </logic:notEmpty>
    <logic:empty name="sch030Form" property="sch010TopList">
      <bean:define id="usrBean" name="sch030Form" property="sch010UserData" />
    </logic:empty>
      <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.19" />" onClick="moveKojinSchedule('kojin', <bean:write name="sch030Form" property="sch010DspDate" />);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
        <gsmsg:write key="schedule.19" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.days2" />" onClick="buttonPush('reload', false);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
        <gsmsg:write key="cmn.days2" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('week');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
        <gsmsg:write key="cmn.weeks" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.monthly" />" onClick="moveSchedule('month');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
        <gsmsg:write key="cmn.monthly" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.list" />" onClick="moveSchedule('list');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
        <gsmsg:write key="cmn.list" />
      </button>

      <span class="mrl_auto"></span>
      <span class="verAlignMid wp200">
        <span class="mrl_auto"></span>
        <button type="button" class="webIconBtn" onClick="buttonPush('move_ld');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left "></i>
        </button>
        <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="buttonPush('today');">
          <gsmsg:write key="cmn.today" />
        </button>
        <span>
          <a href="#" class="fw_b todayBtn original-display" onClick="buttonPush('today');">
            <gsmsg:write key="cmn.today" />
          </a>
        </span>
        <button type="button" class="webIconBtn" onClick="buttonPush('move_rd');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right "></i>
        </button>
        <button type="button" class="iconBtn-border" value="Cal" onClick="resetCmd();sch030OpenCalendar();wrtCalendarByBtn(this.form.sch010DspDate, 'sch030CalBtn');" id="sch030CalBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
          <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
        </button>
      </span>
    </div>

    <table class="table-top cal_table2 w100 mb0">
      <tr>
        <th class="topFrame_btnGroup">
          <span class="verAlignMid">
            <span class="fs_18 mr5">
              <bean:write name="sch030Form" property="sch030StrDate" scope="request" />
            </span>
            <span class="mr10">
              <bean:write name="sch030Form" property="sch030StrRokuyou" scope="request" />
            </span>

            <html:select property="sch010DspGpSid" styleClass="ml5 wp300" onchange="changeGroupCombo();">
              <logic:notEmpty name="sch030Form" property="sch010GpLabelList" scope="request">
                <logic:iterate id="gpBean" name="sch030Form" property="sch010GpLabelList" scope="request">
                  <% boolean gpDisabled = false; %>
                  <logic:equal name="gpBean" property="viewKbn" value="false">
                    <% gpDisabled = true; %>
                  </logic:equal>
                  <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                    <html:option value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:notEqual name="gpBean" property="styleClass" value="0">
                    <% String grpOptionClass = "select_mygroup-bgc"; %>
                    <logic:equal name="gpBean" property="styleClass" value="2"><% grpOptionClass = "select_myschedule-bgc"; %></logic:equal>
                    <html:option styleClass="<%= grpOptionClass %>" value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:notEqual>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>

            <logic:equal name="sch030Form" property="sch010CrangeKbn" value="0">
              <button type="button" class="iconBtn-border ml5" value="Cal" onClick="openGroupWindow(this.form.sch010DspGpSid, 'sch010DspGpSid', '1', '', 0, '', 'schNotAccessGroup');" id="sch030GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              </button>
            </logic:equal>

          </span>
        </th>
        <th colspan="3" class="w40 txt_r border_left_none">
          <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.170" />" onClick="sch030IkkatuSelect();" id="sch030IkkatuSelectBtn">
            <gsmsg:write key="schedule.170" />
          </button>
          <span id="sch030IkkatuButtonArea" class="display_none">
            <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.171" />" onClick="sch030IkkatuEntry();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="schedule.171" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="schedule.171" />">
              <gsmsg:write key="schedule.171" />
            </button>
            <button type="button" class="baseBtn js_ikkatuDsp" value="<gsmsg:write key="schedule.172" />" onClick="sch030IkkatuDsp()">
              <gsmsg:write key="schedule.172" />
            </button>
            <button type="button" class="baseBtn js_ikkatuDspEnd display_none" value="<gsmsg:write key="schedule.175" />" onClick="sch030IkkatuDsp()">
              <gsmsg:write key="schedule.175" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.174" />" onClick="sch030IkkatuEnd();">
              <gsmsg:write key="schedule.174" />
            </button>
          </span>

          <html:text property="sch010searchWord" styleClass="wp150 js_schSearch" maxlength="50" />
          <button type="button" class="baseBtn js_schSearch" value="<gsmsg:write key="cmn.search" />" onClick="moveSchedule('search');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </th>
      </tr>
    </table>
  </div>
</span>

<table class="w100">
  <tr>
    <td class="wrapper_space">
<div class="wrapper">
  <table class="table-top js_sch_under_frame border_top_none">

<logic:notEmpty name="sch030Form" property="sch010TopList" scope="request">
    <!-- タイトル行(氏名,新規,時間) -->
    <tr>
      <td  class="wp150 mwp150 cal_header border_top_none fw_b" rowspan="2">
        <gsmsg:write key="cmn.name" />
      </td>
      <td class="wp40 mwp40 cal_header border_top_none fw_b" rowspan="2">
        <gsmsg:write key="cmn.new" />
      </td>

      <logic:notEmpty name="sch030Form" property="sch030TimeChartList" scope="request">
        <logic:iterate id="strHour" name="sch030Form" property="sch030TimeChartList" scope="request">
          <td class="cal_header fw_b border_top_none" colspan="<bean:write name="memoriCount" scope="page" />">
            <bean:write name="strHour" scope="page" />
          </td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>

    <!-- 時間目盛 -->
    <tr class="sch_scale_area">
      <logic:notEmpty name="sch030Form" property="sch030TimeChartList" scope="request">
        <logic:iterate id="strHour" name="sch030Form" property="sch030TimeChartList" scope="request">
          <logic:equal name="memoriCount" value="4">
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
          </logic:equal>

          <logic:equal name="memoriCount" value="6">
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
          </logic:equal>

          <logic:equal name="memoriCount" value="12">
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
          </logic:equal>
        </logic:iterate>
      </logic:notEmpty>
    </tr>
    <!-- グループと本人 -->
    <logic:iterate id="weekMdl" name="sch030Form" property="sch010TopList" scope="request">
      <tr class="txt_l txt_t">
        <dailySchedule:dailywrite name="weekMdl" top="1" from="<%= fromHour.toString() %>" to="<%= toHour.toString() %>" admin="<%= adminKbn.toString() %>" memCnt="<%= memoriCount.toString() %>"/>
      </tr>
    </logic:iterate>
</logic:notEmpty>
    <!-- グループメンバー -->
    <!-- タイトル行(氏名,新規,時間) -->
    <tr class="cal_day_hd">
      <td  class="wp150 mwp150 cal_header fw_b" rowspan="2">
        <gsmsg:write key="cmn.name" />
      </td>
      <td class="wp40 mwp40 cal_header fw_b" rowspan="2">
        <gsmsg:write key="cmn.new" />
      </td>
      <!-- 時間目盛 -->
      <logic:notEmpty name="sch030Form" property="sch030TimeChartList" scope="request">
        <logic:iterate id="strHour" name="sch030Form" property="sch030TimeChartList" scope="request">
          <td class="cal_header fw_b" colspan="<bean:write name="memoriCount" scope="page" />">
            <bean:write name="strHour" scope="page" />
          </td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>
    <!-- 分目盛 -->
    <tr class="sch_scale_area">
      <logic:notEmpty name="sch030Form" property="sch030TimeChartList" scope="request">
        <logic:iterate id="strHour" name="sch030Form" property="sch030TimeChartList" scope="request">
          <logic:equal name="memoriCount" value="4">
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
          </logic:equal>
          <logic:equal name="memoriCount" value="6">
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
          </logic:equal>
          <logic:equal name="memoriCount" value="12">
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
            <td class="sch_timeScale"></td>
          </logic:equal>
        </logic:iterate>
      </logic:notEmpty>
    </tr>

    <!-- グループメンバー -->
    <logic:notEmpty name="sch030Form" property="sch010BottomList" scope="request">
      <logic:iterate id="gpWeekMdl" name="sch030Form" property="sch010BottomList" scope="request" indexId="cnt">
        <bean:define id="ret" value="<%= String.valueOf(cnt.intValue() % 5) %>" />
        <logic:notEqual name="cnt" value="0" scope="page">
          <logic:equal name="ret" value="0">
            <!-- タイトル行(氏名,新規,時間) -->
            <tr class="cal_day_hd">
              <td  class="wp120 cal_header fw_b" rowspan="2">
                <gsmsg:write key="cmn.name" />
              </td>
              <td class="wp40 cal_header fw_b" rowspan="2">
                <gsmsg:write key="cmn.new" />
              </td>
              <!-- 時間目盛 -->
              <logic:notEmpty name="sch030Form" property="sch030TimeChartList" scope="request">
                <logic:iterate id="strHour" name="sch030Form" property="sch030TimeChartList" scope="request">
                  <td class="cal_header fw_b" colspan="<bean:write name="memoriCount" scope="page" />">
                    <bean:write name="strHour" scope="page" />
                  </td>
                </logic:iterate>
              </logic:notEmpty>
            </tr>
            <!-- 分目盛 -->
            <tr class="sch_scale_area">
              <logic:notEmpty name="sch030Form" property="sch030TimeChartList" scope="request">
                <logic:iterate id="strHour" name="sch030Form" property="sch030TimeChartList" scope="request">
                  <logic:equal name="memoriCount" value="4">
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                  </logic:equal>
                  <logic:equal name="memoriCount" value="6">
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                  </logic:equal>
                  <logic:equal name="memoriCount" value="12">
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                    <td class="sch_timeScale"></td>
                  </logic:equal>
                </logic:iterate>
              </logic:notEmpty>
            </tr>
          </logic:equal>
        </logic:notEqual>
        <tr class="txt_l txt_t">
          <dailySchedule:dailywrite name="gpWeekMdl" top="0" from="<%= fromHour.toString() %>" to="<%= toHour.toString() %>" admin="<%= adminKbn.toString() %>" memCnt="<%= memoriCount.toString() %>" />
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
<logic:equal name="sch030Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
  <div class="txt_l verAlignMid">
    <span class="cal_colHeader-zaiseki status_frame-base borC_light" >
      <gsmsg:write key="cmn.zaiseki" />
    </span>
    <span class="cal_colHeader-huzai status_frame-base borC_light" >
      <gsmsg:write key="cmn.absence" />
    </span>
    <span class="cal_colHeader-sonota status_frame-base borC_light" >
      <gsmsg:write key="cmn.other" />
    </span>
  </div>
</logic:equal>
    </td>
  </tr>
</table>
<schedule:schEasyRegister name="sch030Form"/>
</html:form>

<div id="smlPop" class="js_smlPop" title="" class="display_n">
  <div id="js_smlCreateArea" width="100%" height="100%"></div>
</div>

<div class="txt_t txt_l wp300 pl5 ikkatsuFrame-fixed js_sch_ikkatshHid_frame display_none">
  <div class="bor1 pl5 pr5 pt5 bgC_tableCell js_ikkatsuArea">
  <logic:notEmpty name="sch030Form" property="schIkkatuTorokuHideList">

  <% String hideDate = ""; %>
  <logic:iterate id="schHidDateList" name="sch030Form" property="schIkkatuTorokuHideList">
    <logic:iterate id="schHidMdl" name="schHidDateList"  type="jp.groupsession.v2.sch.model.SchHidModel" indexId="hidListIdx">

    <% String hidDataId = schHidMdl.getId(); %>
    <% if (hidListIdx.intValue() == 0) { %>
    <div class="mb5 w100">
      <div class="pl5 bgC_header2">
        <bean:write name="schHidMdl" property="dateStr" />
      </div>
    </div>
    <% } else { %><br><% } %>

    <div class="pl10 pb5 verAlignMid">
      <% String sid = String.valueOf(schHidMdl.getSid()); %>
      <logic:equal name="schHidMdl" property="usrKbn" value="1">
        <img class="btn_classicImg-display mr5" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
        <img class="btn_originalImg-display mr5" src="../common/images/original/icon_group_25.png" alt="<gsmsg:write key="cmn.group" />">
        <bean:write name="schHidMdl" property="name" />
      </logic:equal>
      <logic:notEqual name="schHidMdl" property="usrKbn" value="1">
        <%
          String usrMukoClass = "";
          if (schHidMdl.getUsrUkoFlg() == jp.groupsession.v2.usr.GSConstUser.USER_SEARCH_USRUKOFLG_MUKO) {
          usrMukoClass = "mukoUser";
        }
        %>
        <a href="#!" class="<%= usrMukoClass %>" onClick="return openUserInfoWindow(<%= sid %>);">
        <% if (schHidMdl.getPhotoKbn() == jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) { %>
          <div class="hikokai_photo-s wp25 mr5 verAlignMid js_syain_sel_check_img">
            <span class="hikokai_text mrl_auto "><gsmsg:write key="cmn.private.photo" /></span>
          </div>
        <% } else if (schHidMdl.getPhotoKbn() == jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) { %>
          <logic:equal name="schHidMdl" property="photoSid" value="0">
            <img src="../common/images/original/photo.png" name="userImage" class="original-display wp25 mr5" alt="<gsmsg:write key="cmn.photo" />">
            <img src="../common/images/classic/icon_photo.gif" name="userImage" class="btn_classicImg-display wp25 mr5" alt="<gsmsg:write key="cmn.photo" />">
          </logic:equal>
          <logic:notEqual name="schHidMdl" property="photoSid" value="0">
            <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="schHidMdl" property="photoSid" />" name="userImage" class="wp25 mr5" alt="<gsmsg:write key="cmn.photo" />">
          </logic:notEqual>
        <% } %>
          <bean:write name="schHidMdl" property="name" />
        </a>
      </logic:notEqual>
      <div class="ml10">
      <button type="button" value="<gsmsg:write key="reserve.22" />" class="ml_auto classic-display mailMenu_button no_w" onclick="sch030IkkatsuRemove('<%= hidDataId %>');">
        <gsmsg:write key="reserve.22" />
      </button>
      <button type="button" value="<gsmsg:write key="reserve.22" />" class="original-display iconBtn-noBorder no_w" onclick="sch030IkkatsuRemove('<%= hidDataId %>');">
        <img src="../common/images/classic/icon_delete.png">
      </button>
      </div>
    </div>
    </logic:iterate>
  </logic:iterate>
  </logic:notEmpty>
  <logic:empty name="sch030Form" property="schIkkatuTorokuHideList">
    <div class="txt_c pb5"><gsmsg:write key="schedule.ikkatsu.1" /></div>
  </logic:empty>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>