<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/schedule/" prefix="schedule" %>
<!DOCTYPE html>

<%-- 定数値 --%>
<%
  int editConfOwn          = jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_OWN;
  int editConfGroup        = jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_GROUP;
  int dspPublic            = jp.groupsession.v2.cmn.GSConstSchedule.DSP_PUBLIC;
  int dspNotPublic         = jp.groupsession.v2.cmn.GSConstSchedule.DSP_NOT_PUBLIC;
  int dspYoteiari          = jp.groupsession.v2.cmn.GSConstSchedule.DSP_YOTEIARI;
  int dspBelongGroup       = jp.groupsession.v2.cmn.GSConstSchedule.DSP_BELONG_GROUP;
  int dspUserGroup       = jp.groupsession.v2.cmn.GSConstSchedule.DSP_USRGRP;
  String project           = jp.groupsession.v2.cmn.GSConstCommon.PLUGIN_ID_PROJECT;
  String nippou            = jp.groupsession.v2.cmn.GSConstCommon.PLUGIN_ID_NIPPOU;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch020.1" /></title>
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

<script src="../schedule/js/sch020.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="sch020Form" property="sch020Reload" value="0">
    var reloadinterval = <bean:write name="sch020Form" property="sch020Reload" />;
    var reloadEvt = setTimeout("buttonPush('reload', true)",reloadinterval);
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

<% long schTipCnt = 0; %>
<body onload="window_create();visibleChange();" onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);" class="visibility-hidden">
<html:form action="/schedule/sch020" styleClass="js_schForm">
<input type="hidden" name="CMD" value="">
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
<html:hidden property="sch010SelectUsrKbn" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="sch020Reload" />

<logic:notEmpty name="sch020Form" property="schNotAccessGroupList" scope="request">
  <logic:iterate id="notAccessGroup" name="sch020Form" property="schNotAccessGroupList" scope="request">
    <input type="hidden" name="schNotAccessGroup" value="<bean:write name="notAccessGroup"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="schIkkatsuFlg" value="0">
<input type="hidden" name="schIkkatsuRemoveKey" value="">
<html:hidden property="schIkkatsuViewMode" />
<html:hidden property="schIkkatsuKakuninViewMode" />
<span id="sch020_saveIkkatsuKeyArea" class="display_none">
<logic:notEmpty name="sch020Form" property="schIkkatuTorokuSaveKey" scope="request">
  <logic:iterate id="ikkatsuSaveKey" name="sch020Form" property="schIkkatuTorokuSaveKey" scope="request">
    <input type="hidden" name="schIkkatuTorokuKey" value="<bean:write name="ikkatsuSaveKey"/>" id="sch020IkkatsuSaveKey_<bean:write name="ikkatsuSaveKey"/>">
  </logic:iterate>
</logic:notEmpty>
</span>

<span class="js_sch_top_frame topFrame-fixed">
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
        <gsmsg:write key="cmn.monthly" />
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
      <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.19" />" onClick="moveKojinSchedule();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
        <gsmsg:write key="schedule.19" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.days2" />" onClick="buttonPush('day');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
        <gsmsg:write key="cmn.days2" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('week');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
        <gsmsg:write key="cmn.weeks" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.monthly" />" onClick="buttonPush('reload', false);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
        <gsmsg:write key="cmn.monthly" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.list" />" onClick="moveListSchedule('list', '<bean:write name="sch020Form" property="sch010SelectUsrSid" />', <bean:write name="sch020Form" property="sch010SelectUsrKbn" />);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
        <gsmsg:write key="cmn.list" />
      </button>
      <span class="mrl_auto"></span>
      <span class="verAlignMid wp200">
        <span class="mrl_auto"></span>
        <button type="button" class="webIconBtn" onClick="buttonPush('move_lm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left "></i>
        </button>
        <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="buttonPush('today');">
          <gsmsg:write key="cmn.thismonth" />
        </button>
        <span>
          <a href="#" class="fw_b todayBtn original-display" onClick="buttonPush('today');">
            <gsmsg:write key="cmn.thismonth" />
          </a>
        </span>
        <button type="button" class="webIconBtn" onClick="buttonPush('move_rm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right "></i>
        </button>
        <button type="button" class="iconBtn-border" value="Cal" onClick="resetCmd();wrtCalendarByBtn(this.form.sch010DspDate, 'sch020Btn');" id="sch020CalBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
          <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
        </button>
      </span>
    </div>

    <table class="table-top cal_table2 w100 mb0">
      <tr>
        <th class="topFrame_btnGroup">
          <span class="verAlignMid">
            <span class="fs_18 mr10">
              <bean:write name="sch020Form" property="sch020StrDspDate" scope="request" />
            </span>

            <html:select property="sch010DspGpSid" styleClass="ml5 wp250" onchange="changeGroupCombo();">
              <logic:notEmpty name="sch020Form" property="sch010GpLabelList" scope="request">
                <logic:iterate id="gpBean" name="sch020Form" property="sch010GpLabelList" scope="request">
                  <% boolean gpDisabled = false; %>
                  <logic:equal name="gpBean" property="viewKbn" value="false">
                    <% gpDisabled = true; %>
                  </logic:equal>
                  <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                    <html:option value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:equal name="gpBean" property="styleClass" value="1">
                    <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:equal name="gpBean" property="styleClass" value="2">
                    <html:option styleClass="select_myschedule-bgc" value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>

            <logic:equal name="sch020Form" property="sch010CrangeKbn" value="0">
              <button type="button" class="iconBtn-border ml5" value="Cal" onClick="openGroupWindow(this.form.sch010DspGpSid, 'sch010DspGpSid', '1', '', 0, '', 'schNotAccessGroup');" id="sch010GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              </button>
            </logic:equal>

            <%-- 月間スケジュールの表示ユーザ設定 --%>
            <html:select property="sch020SelectUsrSid" styleClass="ml5 wp200" onchange="changeUserCombo();">
              <%-- 月間スケジュールを開いたユーザを取得 --%>
              <bean:define name="sch020Form" property="sch020SelectUsrSid" id="selectUsr"/>
              <logic:iterate name="sch020Form" property="sch020UsrLabelList" id="schList">
                <bean:define name="schList" property="value" id="openUsr"/>
                <logic:equal name="schList" property="usrUkoFlg" value="1">
                  <%-- スケジュール初期表示ユーザ設定 --%>
                  <% if (selectUsr.equals(openUsr)) {%>
                    <option value='<bean:write name="schList" property="value"/>' class="mukoUserOption" selected>
                  <% } else { %>
                    <option value='<bean:write name="schList" property="value"/>' class="mukoUserOption">
                  <% } %>
                </logic:equal>
                <logic:notEqual name="schList" property="usrUkoFlg" value="1">
                  <% if (selectUsr.equals(openUsr)) {%>
                    <option value='<bean:write name="schList" property="value"/>' selected>
                  <% } else { %>
                    <option value='<bean:write name="schList" property="value"/>'>
                  <% } %>
                </logic:notEqual>
                <bean:write name="schList" property="label"/>
              </option>
              </logic:iterate>
            </html:select>
          </span>
        </th>
        <th colspan="3" class="w40 txt_r border_left_none">
          <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.170" />" onClick="sch020IkkatuSelect();" id="sch020IkkatuSelectBtn">
            <gsmsg:write key="schedule.170" />
          </button>
          <span id="sch020IkkatuButtonArea" class="display_none">
            <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.171" />" onClick="sch020IkkatuEntry();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="schedule.171" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="schedule.171" />">
              <gsmsg:write key="schedule.171" />
            </button>
            <button type="button" class="baseBtn js_ikkatuDsp" value="<gsmsg:write key="schedule.172" />" onClick="sch020IkkatuDsp()">
              <gsmsg:write key="schedule.172" />
            </button>
            <button type="button" class="baseBtn js_ikkatuDspEnd display_none" value="<gsmsg:write key="schedule.175" />" onClick="sch020IkkatuDsp()">
              <gsmsg:write key="schedule.175" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.end" />" onClick="sch020IkkatuEnd();">
              <gsmsg:write key="schedule.174" />
            </button>
          </span>

          <html:text property="sch010searchWord" styleClass="wp150 js_schSearch" maxlength="50" />
          <button type="button" class="baseBtn js_schSearch" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </th>
      </tr>
    </table>
  </div>
</span>

<div class="wrapper">
  <table class="table-top cal_table w100 js_sch_under_frame border_top_none">
    <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch020_week.jsp" />
    <%
    int count = 0;
    int rowCnt = 0;
    %>
    <logic:notEmpty name="sch020Form" property="sch020ScheduleList" scope="request">
      <logic:iterate id="monthMdl" name="sch020Form" property="sch020ScheduleList" scope="request">
        <bean:define id="periodSchList" name="monthMdl" property="sch020PeriodSchList" type="java.util.ArrayList" />
        <bean:define id="usrMdl" name="monthMdl" property="sch020UsrMdl"/>
        <logic:notEmpty name="monthMdl" property="sch020SchList">
          <logic:iterate id="dayMdl" name="monthMdl" property="sch020SchList" type="jp.groupsession.v2.sch.sch020.Sch020DayOfModel">
            <%
              String detailId = dayMdl.getSchDate() + "-";
              if (dayMdl.getUsrKbn() == 1) {
                detailId += "G";
              }
              detailId += dayMdl.getUsrSid();
            %>
            <% if (count == 0) { %>
              <tr class="cal_month_area">
            <% } %>
            <%--１週間--%>
            <%--背景--%>
            <logic:equal name="dayMdl" property="weekKbn" value="1">
              <logic:equal name="dayMdl" property="todayKbn" value="1">
                <td class="bgC_select txt_t hp100" id="sch020Cell_<%= detailId %>">
              </logic:equal>
              <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                <td class="txt_t bgC_tableCell_Sunday hp100" id="sch020Cell_<%= detailId %>">
                <input type="hidden" name="sch020CellClass" value="bgC_tableCell_Sunday" id="sch020BaseClass_<%= detailId %>">
              </logic:notEqual>
            </logic:equal>
            <logic:equal name="dayMdl" property="weekKbn" value="7">
              <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                <td class="txt_t bgC_tableCell_Saturday hp100" id="sch020Cell_<%= detailId %>">
                <input type="hidden" name="sch020CellClass" value="bgC_tableCell_Saturday" id="sch020BaseClass_<%= detailId %>">
              </logic:notEqual>
              <logic:equal name="dayMdl" property="todayKbn" value="1">
                <td class="bgC_select txt_t hp100" id="sch020Cell_<%= detailId %>">
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="dayMdl" property="weekKbn" value="1">
              <logic:notEqual name="dayMdl" property="weekKbn" value="7">
                <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                  <td class="bgC_tableCell txt_t hp100" id="sch020Cell_<%= detailId %>">
                </logic:notEqual>
                <logic:equal name="dayMdl" property="todayKbn" value="1">
                  <td class="bgC_select txt_t hp100" id="sch020Cell_<%= detailId %>">
                </logic:equal>
              </logic:notEqual>
            </logic:notEqual>
            <logic:equal name="sch020Form" property="sch020RegistFlg" value="true">
              <a class="js_schAddBtn" href="#" onClick="addSchedule('add', <bean:write name="dayMdl" property="schDate" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
                <img class="btn_classicImg-display eventImg" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              </a>
              <span class="js_easyRegister js_schAddBtn cursor_pointer">
                <img class="eventImg wp18 ml5" src="../common/images/original/bubble_pen_icon.png" alt="<gsmsg:write key="cmn.add" />">
                <div class="display_none js_schDate"><bean:write name="dayMdl" property="schDate" /></div>
                <div class="display_none js_schUsrSid"><bean:write name="dayMdl" property="usrSid" /></div>
                <div class="display_none js_schUsrKbn"><bean:write name="dayMdl" property="usrKbn" /></div>
              </span>
              <html:multibox name="sch020Form" property="schIkkatuTorokuKey" onclick="sch020IkkatsuCheck(this)" styleClass="js_schIkkatsuCheck display_none">
                <%= detailId %>
              </html:multibox>
            </logic:equal>
            <logic:notEqual name="dayMdl" property="holidayKbn" value="1">
              <span class="flo_r">
                <%--日付--%>
                <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                  <span class="fs_11 opacity6"><bean:write name="dayMdl" property="schRokuyou" /></span>
                </logic:notEqual>
                <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                  <span class="fw_b fs_11"><bean:write name="dayMdl" property="schRokuyou" /></span>
                </logic:equal>
                <a href="#" class="schedule_headerDay cl_fontBody wp25 txt_c" onClick="moveDailySchedule('day', <bean:write name="dayMdl" property="schDate" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
                  <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                    <logic:equal name="dayMdl" property="todayKbn" value="1">
                      <span class="cl_fontBody td_u fw_b fs_17 wp25 txt_c flo_r">
                        <bean:write name="dayMdl" property="dspDay" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                      <span class="cl_fontBody fw_b fs_17 wp25 txt_c flo_r">
                        <bean:write name="dayMdl" property="dspDay" />
                      </span>
                    </logic:notEqual>
                  </logic:equal>
                  <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                    <span class="cl_fontBody fw_n opacity6 fs_17 wp25 txt_c flo_r">
                      <bean:write name="dayMdl" property="dspDay" />
                    </span>
                  </logic:notEqual>
                  <%
                    count++;
                  %>
                </a>
              </span>
            </logic:notEqual>

            <logic:equal name="dayMdl" property="holidayKbn" value="1">
              <span class="flo_r">
                <%--日付--%>
                <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                  <span class="fw_b fs_11 opacity6"><bean:write name="dayMdl" property="schRokuyou" /></span>
                </logic:notEqual>
                <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                  <span class="fw_b fs_11"><bean:write name="dayMdl" property="schRokuyou" /></span>
                </logic:equal>
                <a href="#" class="schedule_headerDay cl_fontSunday wp25 txt_c" onClick="moveDailySchedule('day', <bean:write name="dayMdl" property="schDate" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
                  <logic:equal name="dayMdl" property="todayKbn" value="1">
                    <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="sc_thismonth_holiday_today fs_17 wp25 txt_c flo_r">
                        <bean:write name="dayMdl" property="dspDay" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="sc_month_holiday_today fs_17 wp25 txt_c flo_r">
                        <bean:write name="dayMdl" property="dspDay" />
                      </span>
                    </logic:notEqual>
                  </logic:equal>
                  <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                    <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="cl_fontSunday fw_b fs_17 wp25 txt_c flo_r">
                        <bean:write name="dayMdl" property="dspDay" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="cl_fontSunday fw_b opacity6 fs_17 wp25 txt_c flo_r">
                        <bean:write name="dayMdl" property="dspDay" />
                      </span>
                    </logic:notEqual>
                  </logic:notEqual>
                  <%
                    count++;
                  %>
                </a>
              </span>
            </logic:equal>
            <%--休日名称--%>
            <logic:notEmpty name="dayMdl" property="holidayName">
              <div class="txt_r lh100 clear_b">
                <span class="txt_l display_inline-block cl_fontSunday fs_11 lh100 mt5">
                  <bean:write name="dayMdl" property="holidayName" />
                </span>
              </div>
            </logic:notEmpty>
            <logic:notEmpty name="dayMdl" property="schDataList">
              <logic:iterate id="schMdl" name="dayMdl" property="schDataList">
                <logic:notEqual name="schMdl" property="timeKbn" value="1">
                  <bean:define id="u_usrsid" name="dayMdl" property="usrSid" type="java.lang.Integer" />
                  <bean:define id="u_schsid" name="schMdl" property="schSid" type="java.lang.Integer" />
                  <bean:define id="u_date" name="dayMdl" property="schDate"  type="java.lang.String" />
                  <bean:define id="u_public" name="schMdl" property="public"  type="java.lang.Integer" />
                  <bean:define id="u_grpEdKbn" name="schMdl" property="grpEdKbn"  type="java.lang.Integer" />
                  <bean:define id="u_kjnEdKbn" name="schMdl" property="kjnEdKbn"  type="java.lang.Integer" />
                  <%
                    int publicType = ((Integer)pageContext.getAttribute("u_public",PageContext.PAGE_SCOPE));
                    int grpEdKbn = ((Integer)pageContext.getAttribute("u_grpEdKbn",PageContext.PAGE_SCOPE));
                    int kjnEdKbn = ((Integer)pageContext.getAttribute("u_kjnEdKbn",PageContext.PAGE_SCOPE));
                  %>
                  <%--公開--%>
                  <% if ((publicType == dspPublic || publicType == dspBelongGroup || publicType == dspUserGroup) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) { %>
                    <logic:empty name="schMdl" property="valueStr">
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="schMdl" property="userSid" />, <bean:write name="schMdl" property="userKbn" />);">
                        <span class="tooltips">
                            <bean:write name="schMdl" property="title" />
                        </span>
                        <div class="cal_space">
                    </logic:empty>
                    <logic:notEmpty name="schMdl" property="valueStr">
                      <bean:define id="scnaiyou" name="schMdl" property="valueStr" />
                      <%
                        String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                        String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                      %>
                      <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="schMdl" property="schSid" />, <bean:write name="schMdl" property="userSid" />, <bean:write name="schMdl" property="userKbn" />);">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                        </span>
                        <div class="cal_space">
                    </logic:notEmpty>
                    <%--タイトルカラー設定--%>
                    <logic:equal name="schMdl" property="bgColor" value="0">
                      <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="1">
                      <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="2">
                      <div class="cl_fontSchTitleRed opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="3">
                      <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="4">
                      <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="5">
                      <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="6">
                      <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="7">
                      <div class="cl_fontSchTitleWine opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="8">
                      <div class="cl_fontSchTitleCien opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="9">
                      <div class="cl_fontSchTitleGray opacity6-hover fs_13">
                    </logic:equal>
                    <logic:equal name="schMdl" property="bgColor" value="10">
                      <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
                    </logic:equal>
                    <logic:notEqual name="schMdl" property="userKbn" value="1">
                      <logic:notEmpty name="schMdl" property="userName">
                        <logic:equal name="schMdl" property="usrUkoFlg" value="1">
                          <div class="cal_userName cl_fontBody">
                            <span class="mukoUser">
                              <bean:write name="schMdl" property="userName"/>
                            </span>
                          </div>
                        </logic:equal>
                        <logic:notEqual name="schMdl" property="usrUkoFlg" value="1">
                          <div class="cal_userName">
                            <span class="cl_fontBody">
                              <bean:write name="schMdl" property="userName"/>
                            </span>
                          </div>
                        </logic:notEqual>
                      </logic:notEmpty>
                      <logic:notEmpty name="schMdl" property="time">
                        <logic:notEmpty name="schMdl" property="userName">
                          <div class="cal_content-space cal_userNameTime-space">
                        </logic:notEmpty>
                        <logic:empty name="schMdl" property="userName">
                          <div class="cal_content-space">
                        </logic:empty>
                          <span class="cal_time">
                            <bean:write name="schMdl" property="time" />
                          </span>
                        </div>
                      </logic:notEmpty>
                      <logic:empty name="schMdl" property="time">
                        <logic:empty name="schMdl" property="userName">
                          <div class="mt10"></div>
                        </logic:empty>
                        <logic:notEmpty name="schMdl" property="userName">
                          <div class="cal_userName-space"></div>
                        </logic:notEmpty>
                      </logic:empty>
                    </logic:notEqual>
                    <logic:equal name="schMdl" property="userKbn" value="1">
                      <logic:notEmpty name="schMdl" property="time">
                        <div class="cal_content-space">
                          <span class="cal_time">
                            <span class="cal_label-g classic-display">G</span>
                            <span class="cal_label-g original-display"></span>
                            <bean:write name="schMdl" property="time" />
                          </span>
                        </div>
                      </logic:notEmpty>
                      <logic:empty name="schMdl" property="time">
                        <div class="cal_content-space">
                          <span class="cal_time">
                            <span class="cal_label-g classic-display">G</span>
                            <span class="cal_label-g original-display"></span>
                          </span>
                        </div>
                      </logic:empty>
                    </logic:equal>
                    <div class="cal_content verAlignMid">
                      <logic:equal name="schMdl" property="publicIconFlg" value="true">
                        <span class="mr5">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                        </span>
                      </logic:equal>
                      <bean:write name="schMdl" property="title" />
                    </div>
                    </div>
                    </div>
                    </a>
                  <%
                    } else {
                  %>
                    <%--非公開--%>
                    <div class="fs_13 cal_space">
                      <logic:notEmpty name="schMdl" property="userName">
                        <div class="cal_userName">
                          <span class="cl_fontBody">
                            <bean:write name="schMdl" property="userName" />
                          </span>
                        </div>
                      </logic:notEmpty>
                      <logic:notEmpty name="schMdl" property="time">
                        <logic:notEmpty name="schMdl" property="userName">
                          <div class="cal_content-space cal_userNameTime-space">
                        </logic:notEmpty>
                        <logic:empty name="schMdl" property="userName">
                          <div class="cal_content-space">
                        </logic:empty>
                          <span class="cal_time">
                            <bean:write name="schMdl" property="time" />
                          </span>
                        </div>
                      </logic:notEmpty>
                      <logic:notEqual name="schMdl" property="public" value="1">
                        <div class="cal_content">
                          <bean:write name="schMdl" property="title" />
                        </div>
                      </logic:notEqual>
                    </div>
                  <%
                    }
                  %>
                </logic:notEqual>
              </logic:iterate>
            </logic:notEmpty>
            </td>
            <% if (count >= 7) { %>
              </tr>
              <%--期間スケジュール--%>
              <logic:iterate id="periodList" name="periodSchList" indexId="idx">
                <% if (Integer.valueOf(idx) == rowCnt) { %>
                  <logic:notEmpty name="periodList" property="sch020NoTimeSchList">
                    <bean:define id="prList" name="periodList" property="sch020NoTimeSchList" type="java.util.ArrayList"/>
                    <% int size = prList.size(); %>
                    <logic:iterate id="prdList" name="periodList" property="sch020NoTimeSchList" indexId="idx2">
                      <logic:notEmpty name="prdList">
                        <tr>
                          <bean:define id="mPrList" name="prdList" type="java.util.ArrayList"/>
                          <% int size2 = mPrList.size(); %>
                          <logic:iterate id="uPeriodMdl" name="prdList" indexId="mPidx">
                            <logic:notEmpty name="uPeriodMdl" property="periodMdl">
                              <bean:define id="pMdl" name="uPeriodMdl" property="periodMdl"/>
                                <td class="cal_periodCell" colspan="<bean:write name="pMdl" property="schPeriodCnt" />">
                                  <bean:define id="p_schsid" name="uPeriodMdl" property="schSid" type="java.lang.Integer" />
                                  <bean:define id="p_public" name="uPeriodMdl" property="public"  type="java.lang.Integer" />
                                  <bean:define id="p_grEdKbn" name="uPeriodMdl" property="grpEdKbn"  type="java.lang.Integer" />
                                  <bean:define id="p_kjEdKbn" name="uPeriodMdl" property="kjnEdKbn"  type="java.lang.Integer" />
                                  <%
                                    int p_pblicType = ((Integer)pageContext.getAttribute("p_public",PageContext.PAGE_SCOPE));
                                    int p_grpEdKbn = ((Integer)pageContext.getAttribute("p_grEdKbn",PageContext.PAGE_SCOPE));
                                    int p_kjnEdKbn = ((Integer)pageContext.getAttribute("p_kjEdKbn",PageContext.PAGE_SCOPE));
                                  %>
                                  <%--公開--%>
                                  <% if ((p_pblicType == dspPublic || p_pblicType == dspBelongGroup || p_pblicType == dspUserGroup) || (p_kjEdKbn == editConfOwn || p_grEdKbn == editConfGroup)) { %>
                                    <%--スケジュールデータ--%>
                                    <logic:empty name="uPeriodMdl" property="schAppendUrl">
                                      <logic:empty name="uPeriodMdl" property="valueStr">
                                        <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="uPeriodMdl" property="userSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                                          <span class="tooltips">
                                            <bean:write name="uPeriodMdl" property="title" />
                                          </span>
                                      </logic:empty>
                                      <logic:notEmpty name="uPeriodMdl" property="valueStr">
                                        <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                                        <%
                                          String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                                          String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                                        %>
                                        <a href="#" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" onClick="editSchedule('edit', <bean:write name="dayMdl" property="schDate" />, <bean:write name="uPeriodMdl" property="schSid" />, <bean:write name="uPeriodMdl" property="userSid" />, <bean:write name="uPeriodMdl" property="userKbn" />);">
                                          <span class="tooltips">
                                            <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                                          </span>
                                      </logic:notEmpty>
                                    </logic:empty>
                                    <%--他プラグインデータ--%>
                                    <logic:notEmpty name="uPeriodMdl" property="schAppendUrl">
                                      <logic:empty name="uPeriodMdl" property="valueStr">
                                        <a id="tooltips_sch<%= String.valueOf(schTipCnt++) %>" href="<bean:write name="uPeriodMdl" property="schAppendUrl" />">
                                          <% boolean schFilter = true; %>
                                          <logic:equal name="uPeriodMdl" property="userKbn" value="<%= project %>">
                                            <% schFilter = false; %>
                                          </logic:equal>
                                          <span class="tooltips">
                                            <bean:write name="uPeriodMdl" property="title" filter="<%= schFilter %>" />
                                          </span>
                                      </logic:empty>
                                      <logic:notEmpty name="uPeriodMdl" property="valueStr">
                                        <bean:define id="scnaiyou" name="uPeriodMdl" property="valueStr" />
                                        <%
                                          String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                                          String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                                        %>
                                        <a href="<bean:write name="uPeriodMdl" property="schAppendUrl" />" id="tooltips_sch<%= String.valueOf(schTipCnt++) %>">
                                          <span class="tooltips">
                                            <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                                          </span
                                      </logic:notEmpty>
                                      <logic:equal name="uPeriodMdl" property="bgColor" value="0">
                                        <div class="cl_fontSchTitleBlue fs_13">
                                      </logic:equal>
                                    </logic:notEmpty>

                                    <% boolean schFilter = true; %>
                                    <%--タイトルカラー設定--%>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="0">
                                      <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="1">
                                      <div class="cl_fontSchTitleBlue opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="2">
                                      <div class="cl_fontSchTitleRed opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="3">
                                      <div class="cl_fontSchTitleGreen opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="4">
                                      <div class="cl_fontSchTitleYellow opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="5">
                                      <div class="cl_fontSchTitleBlack opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="6">
                                      <div class="cl_fontSchTitleNavy opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="7">
                                      <div class="cl_fontSchTitleWine opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="8">
                                      <div class="cl_fontSchTitleCien opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="9">
                                      <div class="cl_fontSchTitleGray opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:equal name="uPeriodMdl" property="bgColor" value="10">
                                      <div class="cl_fontSchTitleMarine opacity6-hover fs_13">
                                    </logic:equal>
                                    <logic:notEmpty name="uPeriodMdl" property="userName">
                                      <div class="cal_userName txt_c schMonth_userName-space">
                                        <span class="cl_fontBody">
                                          <bean:write name="uPeriodMdl" property="userName" />
                                        </span>
                                      </div>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="uPeriodMdl" property="time">
                                      <div class="cal_content-space">
                                        <span class="cal_time">
                                          <bean:write name="uPeriodMdl" property="time" />
                                        </span>
                                      </div>
                                    </logic:notEmpty>
                                    <div class="lh130 txt_c mt5 mb5">
                                      <logic:equal name="uPeriodMdl" property="publicIconFlg" value="true">
                                        <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                                        <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                                      </logic:equal>
                                      <logic:equal name="uPeriodMdl" property="userKbn" value="1">
                                        <span class="cal_label-g classic-display">G</span>
                                        <span class="cal_label-g original-display"></span>
                                      </logic:equal>
                                      <logic:equal name="uPeriodMdl" property="userKbn" value="<%= project %>">
                                        <span class="cal_label-todo">TODO</span>
                                        <% schFilter = false; %>
                                      </logic:equal>
                                      <logic:equal name="uPeriodMdl" property="userKbn" value="<%= nippou %>">
                                        <span class="cal_label-action">アクション</span>
                                      </logic:equal>
                                      <bean:write name="uPeriodMdl" property="title" filter="<%= schFilter %>"  />
                                    </div>
                                    </div>
                                    </a>
                                  <%
                                    } else {
                                  %>
                                    <%--非公開--%>
                                    <div class="fs_13 cal_space">
                                      <logic:notEmpty name="uPeriodMdl" property="time">
                                        <div class="cal_content-space">
                                          <span class="cal_time">
                                            <bean:write name="uPeriodMdl" property="time" />
                                          </span>
                                        </div>
                                      </logic:notEmpty>
                                      <logic:notEmpty name="uPeriodMdl" property="userName">
                                        <div class="cal_userName">
                                          <span class="cl_fontBody">
                                            <bean:write name="uPeriodMdl" property="userName" />
                                          </span>
                                        </div>
                                      </logic:notEmpty>
                                      <div class="lh130 txt_c mt5 mb5">
                                        <bean:write name="uPeriodMdl" property="title" />
                                      </div>
                                    </div>
                                  <% } %>
                                  </td>
                            </logic:notEmpty>
                            <logic:empty name="uPeriodMdl" property="periodMdl">
                              <td class="cal_periodCell-less"></td>
                            </logic:empty>
                          </logic:iterate>
                        </tr>
                      </logic:notEmpty>
                    </logic:iterate>
                  </logic:notEmpty>
                <% } %>
              </logic:iterate>
            <%
              count = 0;
              rowCnt++;
              }
            %>
          </logic:iterate>
        </logic:notEmpty>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
<schedule:schEasyRegister name="sch020Form"/>
</html:form>

<div class="txt_t txt_l wp300 pl5 ikkatsuFrame-fixed js_sch_ikkatshHid_frame display_none">
  <div class="bor1 pl5 pr5 pt5 bgC_tableCell js_ikkatsuArea">
  <logic:notEmpty name="sch020Form" property="schIkkatuTorokuHideList">

  <% String hideDate = ""; %>
  <logic:iterate id="schHidDateList" name="sch020Form" property="schIkkatuTorokuHideList">
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
      <button type="button" value="<gsmsg:write key="reserve.22" />" class="ml_auto classic-display mailMenu_button no_w" onclick="sch020IkkatsuRemove('<%= hidDataId %>');">
        <gsmsg:write key="reserve.22" />
      </button>
      <button type="button" value="<gsmsg:write key="reserve.22" />" class="original-display iconBtn-noBorder no_w" onclick="sch020IkkatsuRemove('<%= hidDataId %>');">
        <img src="../common/images/classic/icon_delete.png">
      </button>
      </div>
    </div>
    </logic:iterate>
  </logic:iterate>
  </logic:notEmpty>
  <logic:empty name="sch020Form" property="schIkkatuTorokuHideList">
    <div class="txt_c pb5"><gsmsg:write key="schedule.ikkatsu.1" /></div>
  </logic:empty>
  </div>
</div>

<script type="text/javascript">
<% for (long schIdCnt = 0; schIdCnt < schTipCnt; schIdCnt++) { %>
<% } %>
</script>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>