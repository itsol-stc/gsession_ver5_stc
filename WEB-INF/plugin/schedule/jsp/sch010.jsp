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
  String project           = jp.groupsession.v2.cmn.GSConstCommon.PLUGIN_ID_PROJECT;
  String nippou            = jp.groupsession.v2.cmn.GSConstCommon.PLUGIN_ID_NIPPOU;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch010.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>

<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>

<!-- Clock script -->
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<!--  日本語化 -->
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../schedule/js/sch010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
  <!--
    //自動リロード
    <logic:notEqual name="sch010Form" property="sch010Reload" value="0">
      var reloadinterval = <bean:write name="sch010Form" property="sch010Reload" />;
      var reloadEvt = setTimeout("buttonPush('reload', true)",reloadinterval);
    </logic:notEqual>
  -->
</script>
<script type="text/javascript">
  function getSelVal(className){
    return false;
  }
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
<body onload="window_create();visibleChange();" onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);" class="js_memForm visibility-hidden">
<html:form action="/schedule/sch010" styleClass="js_schForm">
<input type="hidden" name="schIkkatsuFlg " value="1">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="dspMod" value="1">
<input type="hidden" name="sch010SelectUsrSid" value="-1">
<input type="hidden" name="sch010SelectUsrKbn" value="0">
<input type="hidden" name="selectDate" value="">
<input type="hidden" name="selectUser" value="">
<input type="hidden" name="selectKbn" value="">

<html:hidden property="cmd" />
<html:hidden property="sch010DspDate" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010CrangeKbn" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch010Reload" />

<logic:notEmpty name="sch010Form" property="schNotAccessGroupList" scope="request">
  <logic:iterate id="notAccessGroup" name="sch010Form" property="schNotAccessGroupList" scope="request">
    <input type="hidden" name="schNotAccessGroup" value="<bean:write name="notAccessGroup"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="schIkkatsuFlg" value="0">
<input type="hidden" name="schIkkatsuRemoveKey" value="">
<html:hidden property="schIkkatsuViewMode" />
<html:hidden property="schIkkatsuKakuninViewMode" />
<logic:notEmpty name="sch010Form" property="schIkkatuTorokuSaveKey" scope="request">
  <logic:iterate id="ikkatsuSaveKey" name="sch010Form" property="schIkkatuTorokuSaveKey" scope="request">
    <input type="hidden" name="schIkkatuTorokuKey" value="<bean:write name="ikkatsuSaveKey"/>" id="sch010IkkatsuSaveKey_<bean:write name="ikkatsuSaveKey"/>" class="js_saveIkkatsuKey">
  </logic:iterate>
</logic:notEmpty>

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
        <gsmsg:write key="cmn.weeks" />
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

  <div class="wrapper">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
    <div class="verAlignMid w100">
      <span class="wp200"></span>
      <span class="mrl_auto"></span>
      <logic:notEmpty name="sch010Form" property="sch010TopList">
        <bean:size id="topSize" name="sch010Form" property="sch010TopList" scope="request" />
        <logic:equal name="topSize" value="2">
          <logic:iterate id="weekBean" name="sch010Form" property="sch010TopList" scope="request" offset="1"/>
        </logic:equal>
        <logic:notEqual name="topSize" value="2">
          <logic:iterate id="weekBean" name="sch010Form" property="sch010TopList" scope="request" offset="0"/>
        </logic:notEqual>
        <bean:define id="usrBean" name="weekBean" property="sch010UsrMdl"/>
      </logic:notEmpty>
      <logic:empty name="sch010Form" property="sch010TopList">
        <bean:define id="usrBean" name="sch010Form" property="sch010UserData" />
      </logic:empty>
      <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.19" />" onClick="moveKojinSchedule('kojin', <bean:write name="sch010Form" property="sch010DspDate" />);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
        <gsmsg:write key="schedule.19" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.days2" />" onClick="moveDailySchedule('day', <bean:write name="sch010Form" property="sch010DspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
        <gsmsg:write key="cmn.days2" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('reload', false);">
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
        <button type="button" class="webIconBtn" onClick="buttonPush('move_lw');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_2_l.png">
          <i class="icon-paging_week_left "></i>
        </button>
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
        <button type="button" class="webIconBtn" onClick="buttonPush('move_rw');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_2_r.png">
          <i class="icon-paging_week_right "></i>
        </button>
        <button type="button" class="iconBtn-border" value="Cal" onClick="resetCmd();sch010OpenCalendar();wrtCalendarByBtn(this.form.sch010DspDate, 'sch010CalBtn');" id="sch010CalBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
          <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
        </button>
      </span>
    </div>

    <table class="table-top cal_table2 w100 mb0">
      <tr>
        <th colspan="5" class="topFrame_btnGroup table_title-color">
          <span class="verAlignMid">
          <span class="fs_18 mr10"><bean:write name="sch010Form" property="sch010StrDspDate" /></span>
          <html:select property="sch010DspGpSid" styleClass="ml5 wp300" onchange="changeGroupCombo();getSelVal('select01');">
            <logic:notEmpty name="sch010Form" property="sch010GpLabelList" scope="request">
              <logic:iterate id="gpBean" name="sch010Form" property="sch010GpLabelList" scope="request">
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
          <logic:equal name="sch010Form" property="sch010CrangeKbn" value="0">
            <button type="button" class="iconBtn-border ml5" value="Cal" onClick="openGroupWindow(this.form.sch010DspGpSid, 'sch010DspGpSid', '1', '', 0, '', 'schNotAccessGroup');" id="sch010GroupBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
            </button>
          </logic:equal>
          </span>
        </th>
        <th colspan="3" class="w40 txt_r border_left_none table_title-color no_w">
          <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.170" />" onClick="sch010IkkatuSelect();" id="sch010IkkatuSelectBtn">
            <gsmsg:write key="schedule.170" />
          </button>
          <span id="sch010IkkatuButtonArea" class="display_none">
            <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.171" />" onClick="sch010IkkatuEntry();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="schedule.171" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="schedule.171" />">
              <gsmsg:write key="schedule.171" />
            </button>
            <button type="button" class="baseBtn js_ikkatuDsp" value="<gsmsg:write key="schedule.172" />" onClick="sch010IkkatuDsp()">
              <gsmsg:write key="schedule.172" />
            </button>
            <button type="button" class="baseBtn js_ikkatuDspEnd display_none" value="<gsmsg:write key="schedule.175" />" onClick="sch010IkkatuDsp()">
              <gsmsg:write key="schedule.175" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.end" />" onClick="sch010IkkatuEnd();">
              <gsmsg:write key="schedule.174" />
            </button>
          </span>

          <html:text property="sch010searchWord" styleClass="wp150 js_schSearch" maxlength="50" />
          <button type="button" class="baseBtn js_schSearch" value="<gsmsg:write key="cmn.search" />" onClick="moveSchedule('search');" id="sch010GroupBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </th>
      </tr>
    </table>
  </div>
</span>

<div class="wrapper js_easyDispArea">
  <table class="table-top cal_table w100 js_sch_under_frame border_top_none">
  <logic:notEmpty name="sch010Form" property="sch010TopList" scope="request">
    <tr class="calendar_weekHeader">
      <td class="cal_header fw_b w16 border_top_none">
        <gsmsg:write key="cmn.name" />
      </td>
      <logic:notEmpty name="sch010Form" property="sch010CalendarList" scope="request">
        <logic:iterate id="calBean" name="sch010Form" property="sch010CalendarList" scope="request">
          <bean:define id="tdColor" value="" />
          <% String[] tdColors = new String[] {"cal_header", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"}; %>
          <% String rkyClass = ""; %>
          <bean:define id="rkyKbn" name="calBean" property="rokuyou" />
          <%
            if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SENSHOU))) {
                rkyClass = "header_rokuyou-senshou";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_TOMOBIKI))) {
                rkyClass = "header_rokuyou-tomobiki";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SENBU))) {
                rkyClass = "header_rokuyou-senbu";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_BUTSUMETSU))) {
                rkyClass = "header_rokuyou-butsumetsu";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_TAIAN))) {
                rkyClass = "header_rokuyou-taian";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SHAKKOU))) {
                rkyClass = "header_rokuyou-shakkou";
            }
          %>
          <logic:equal name="calBean" property="todayKbn" value="1">
            <% tdColor = tdColors[1]; %>
          </logic:equal>
          <logic:notEqual name="calBean" property="todayKbn" value="1">
             <logic:equal name="calBean" property="holidayKbn" value="1">
                <% tdColor = tdColors[0]; %>
              </logic:equal>
          <logic:notEqual name="calBean" property="holidayKbn" value="1">
            <logic:equal name="calBean" property="weekKbn" value="1">
            <% tdColor = tdColors[3]; %>
            </logic:equal>
            <logic:equal name="calBean" property="weekKbn" value="7">
              <% tdColor = tdColors[2]; %>
            </logic:equal>
            <logic:notEqual name="calBean" property="weekKbn" value="1">
              <logic:notEqual name="calBean" property="weekKbn" value="7">
            <% tdColor = tdColors[0]; %>
              </logic:notEqual>
            </logic:notEqual>
          </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="calBean" property="holidayKbn" value="1">
            <td class="no_w <%= tdColor %> w12 border_top_none">
              <div class="calendar_dayHeader">
                <div class="<%= rkyClass %>">
                  <a href="#" class="cl_fontSunday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                    <span class="fw_b schedule_headerDay">
                      <bean:write name="calBean" property="dspDayString" />
                    </span>
                  </a>
                </div>
              </div>
            </td>
          </logic:equal>
          <logic:notEqual name="calBean" property="holidayKbn" value="1">
            <logic:equal name="calBean" property="weekKbn" value="1">
              <td class="no_w <%= tdColor %> w12 border_top_none">
                <div class="calendar_dayHeader">
                  <div class="<%= rkyClass %>">
                    <a href="#" class="cl_fontSunday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                      <span class="fw_b schedule_headerDay">
                        <bean:write name="calBean" property="dspDayString" />
                      </span>
                    </a>
                  </div>
                </div>
              </td>
            </logic:equal>
            <logic:equal name="calBean" property="weekKbn" value="7">
              <td class="no_w <%= tdColor %> w12 border_top_none">
                <div class="calendar_dayHeader">
                  <div class="<%= rkyClass %>">
                    <a href="#" class="cl_fontSaturday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                      <span class="fw_b schedule_headerDay">
                        <bean:write name="calBean" property="dspDayString" />
                      </span>
                    </a>
                  </div>
                </div>
              </td>
            </logic:equal>
            <logic:notEqual name="calBean" property="weekKbn" value="1">
              <logic:notEqual name="calBean" property="weekKbn" value="7">
                <td class="no_w <%= tdColor %> w12 border_top_none">
                <div class="calendar_dayHeader">
                  <div class="<%= rkyClass %>">
                    <a href="#" class="cl_fontBody" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                      <span class="fw_b schedule_headerDay">
                        <bean:write name="calBean" property="dspDayString" />
                      </span>
                    </a>
                  </div>
                </div>
                </td>
              </logic:notEqual>
            </logic:notEqual>
          </logic:notEqual>
        </logic:iterate>
      </logic:notEmpty>
    </tr>
    </logic:notEmpty>

    <% String grpHeight = "hp100"; %>
    <logic:notEqual name="sch010Form" property="smailUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
      <% grpHeight = "hp60"; %>
    </logic:notEqual>

    <!-- グループ,本人 -->
    <logic:notEmpty name="sch010Form" property="sch010TopList" scope="request">
      <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch010_mySelf.jsp" >
        <jsp:param value="<%= editConfOwn %>" name="editConfOwn"/>
        <jsp:param value="<%= editConfGroup %>" name="editConfGroup"/>
        <jsp:param value="<%= dspPublic %>" name="dspPublic"/>
        <jsp:param value="<%= project %>" name="project"/>
        <jsp:param value="<%= nippou %>" name="nippou"/>
        <jsp:param value="<%= grpHeight %>" name="grpHeight"/>
        <jsp:param value="<%= schTipCnt %>" name="schTipCnt"/>
      </jsp:include>
    </logic:notEmpty>

    <logic:notEmpty name="sch010Form" property="sch010TopList" scope="request">
    <tr>
      <th colspan="8" class="table_title-color txt_l">
        <span class="fs_18">
          <gsmsg:write key="schedule.74" />
        </span>
      </th>
    </tr>
    </logic:notEmpty>

    <tr class="calendar_weekHeader">
      <td class="cal_header fw_b w16"><gsmsg:write key="cmn.name" /></td>
      <logic:notEmpty name="sch010Form" property="sch010CalendarList" scope="request">
        <logic:iterate id="calBean" name="sch010Form" property="sch010CalendarList" scope="request">
          <bean:define id="tdColor" value="" />
          <% String[] tdColors = new String[] {"cal_header", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"}; %>
          <% String rkyClass = ""; %>
          <bean:define id="rkyKbn" name="calBean" property="rokuyou" />
          <%
            if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SENSHOU))) {
                rkyClass = "header_rokuyou-senshou";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_TOMOBIKI))) {
                rkyClass = "header_rokuyou-tomobiki";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SENBU))) {
                rkyClass = "header_rokuyou-senbu";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_BUTSUMETSU))) {
                rkyClass = "header_rokuyou-butsumetsu";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_TAIAN))) {
                rkyClass = "header_rokuyou-taian";
            } else if (rkyKbn.equals(String.valueOf(GSConst.RKY_KBN_SHAKKOU))) {
                rkyClass = "header_rokuyou-shakkou";
            }
          %>
          <logic:equal name="calBean" property="todayKbn" value="1">
            <% tdColor = tdColors[1]; %>
          </logic:equal>
          <logic:notEqual name="calBean" property="todayKbn" value="1">
              <logic:equal name="calBean" property="holidayKbn" value="1">
                <% tdColor = tdColors[0]; %>
              </logic:equal>
           <logic:notEqual name="calBean" property="holidayKbn" value="1">
           <logic:equal name="calBean" property="weekKbn" value="1">
           <% tdColor = tdColors[3]; %>
           </logic:equal>
           <logic:equal name="calBean" property="weekKbn" value="7">
             <% tdColor = tdColors[2]; %>
           </logic:equal>
           <logic:notEqual name="calBean" property="weekKbn" value="1">
             <logic:notEqual name="calBean" property="weekKbn" value="7">
           <% tdColor = tdColors[0]; %>
             </logic:notEqual>
           </logic:notEqual>
          </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="calBean" property="holidayKbn" value="1">
            <td class="no_w <%= tdColor %> w12">
              <span class="<%= rkyClass %>">
              <a href="#" class="schedule_headerDay cl_fontSunday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                <span class="fw_b">
                  <bean:write name="calBean" property="dspDayString" />
                </span>
              </a>
              </span>
            </td>
          </logic:equal>
          <logic:notEqual name="calBean" property="holidayKbn" value="1">
            <logic:equal name="calBean" property="weekKbn" value="1">
              <td class="no_w <%= tdColor %> w12">
                <span class="<%= rkyClass %>">
                <a href="#" class="schedule_headerDay cl_fontSunday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                  <span class="fw_b">
                    <bean:write name="calBean" property="dspDayString" />
                  </span>
                </a>
                </span>
              </td>
            </logic:equal>
            <logic:equal name="calBean" property="weekKbn" value="7">
              <td class="no_w <%= tdColor %> w12">
                <span class="<%= rkyClass %>">
                <a href="#" class="schedule_headerDay cl_fontSaturday" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                  <span class="fw_b">
                    <bean:write name="calBean" property="dspDayString" />
                  </span>
                </a>
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="calBean" property="weekKbn" value="1">
              <logic:notEqual name="calBean" property="weekKbn" value="7">
                <td class="no_w <%= tdColor %> w12">
                  <span class="<%= rkyClass %>">
                  <a href="#" class="schedule_headerDay cl_fontBody" onClick="moveDailySchedule('day', <bean:write name="calBean" property="dspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
                    <span class="fw_b">
                      <bean:write name="calBean" property="dspDayString" />
                    </span>
                  </a>
                  </span>
                </td>
              </logic:notEqual>
            </logic:notEqual>
          </logic:notEqual>
        </logic:iterate>
      </logic:notEmpty>
    </tr>

    <!-- グループメンバー -->
    <logic:notEmpty name="sch010Form" property="sch010BottomList" scope="request">
      <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch010_gpMember.jsp">
        <jsp:param value="<%= editConfOwn %>" name="editConfOwn"/>
        <jsp:param value="<%= editConfGroup %>" name="editConfGroup"/>
        <jsp:param value="<%= dspPublic %>" name="dspPublic"/>
        <jsp:param value="<%= dspBelongGroup %>" name="dspBelongGroup"/>
        <jsp:param value="<%= project %>" name="project"/>
        <jsp:param value="<%= nippou %>" name="nippou"/>
        <jsp:param value="<%= grpHeight %>" name="grpHeight"/>
        <jsp:param value="<%= schTipCnt %>" name="schTipCnt"/>
      </jsp:include>
    </logic:notEmpty>

  </table>
</div>

<logic:equal name="sch010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
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

<schedule:schEasyRegister name="sch010Form"/>
</html:form>

<div id="smlPop" class="js_smlPop p0" title="" class="display_n">
  <div id="js_smlCreateArea" class="w90 h90"></div>
</div>

<div class="txt_t txt_l wp300 pl5 ikkatsuFrame-fixed js_sch_ikkatshHid_frame display_none">
  <div class="bor1 pl5 pr5 pt5 bgC_tableCell js_ikkatsuArea">
  <logic:notEmpty name="sch010Form" property="schIkkatuTorokuHideList">

  <% String hideDate = ""; %>
  <logic:iterate id="schHidDateList" name="sch010Form" property="schIkkatuTorokuHideList">
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
        <img class="btn_classicImg-display mr5" src="../common/images/classic/header_group.png" alt="<gsmsg:write key="cmn.group" />">
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
      <button type="button" value="<gsmsg:write key="reserve.22" />" class="ml_auto classic-display mailMenu_button no_w" onclick="sch010IkkatsuRemove('<%= hidDataId %>');">
        <gsmsg:write key="reserve.22" />
      </button>
      <button type="button" value="<gsmsg:write key="reserve.22" />" class="original-display iconBtn-noBorder no_w" onclick="sch010IkkatsuRemove('<%= hidDataId %>');">
        <img src="../common/images/classic/icon_delete.png">
      </button>
      </div>
    </div>
    </logic:iterate>
  </logic:iterate>
  </logic:notEmpty>
  <logic:empty name="sch010Form" property="schIkkatuTorokuHideList">
    <div class="txt_c pb5"><gsmsg:write key="schedule.ikkatsu.1" /></div>
  </logic:empty>
  </div>
</div>

<script type="text/javascript">
<% for (long schIdCnt = 0; schIdCnt < schTipCnt; schIdCnt++) { }%>
</script>

<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</body>
</html:html>
