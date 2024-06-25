<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch200.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../schedule/fullcalendar/fullcalendar.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../schedule/js/sch200.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="sch200Form" property="sch200Reload" value="0">
    var reloadinterval = <bean:write name="sch200Form" property="sch200Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
  </logic:notEqual>
-->
</script>

<script type='text/javascript'>
  $(document).ready(function() {
    $.post(
        '../schedule/sch200.do',
        {
           "CMD":"getSchData",
           "sch010DspDate":"<bean:write name="sch200Form" property="sch010DspDate" />",
           "sch200FrDate":"<bean:write name="sch200Form" property="sch200FrDate" />",
           "sch200ToDate":"<bean:write name="sch200Form" property="sch200ToDate" />",
           "sch100SelectUsrSid":"<bean:write name="sch200Form" property="sch100SelectUsrSid" />",
           "sch010SelectUsrKbn":"<bean:write name="sch200Form" property="sch010SelectUsrKbn" />"
        },
        function(data){
          //イベントオブジェクト描画後コールバック
          function drawUp () {

          }
          var jsonObject = eval('(' + data + ')');
          jsonObject.eventAfterRender = drawUp;
          $('#calendar').fullCalendar(jsonObject);
          $('#calendar').fullCalendar( 'gotoDate',
                  <bean:write name="sch200Form" property="sch200Year" />,
                  <bean:write name="sch200Form" property="sch200Month" />,
                  <bean:write name="sch200Form" property="sch200Day" /> );
          $('#calendar').fullCalendar('option', 'aspectRatio', 2.15);

          //GS描画設定用classを追加
          //layout.cssで.ui-widget-headerで背景、罫線なしでimportantが設定されているため
          //themeで定義された色(背景、罫線色)を追加で設定し
          //ui-widget-headerのclass設定を除去する必要がある
          //この画面においてはui-widget-headerをjavascriptなどから参照していないのでなくなっても動作に影響がない

          //ヘッダーに背景色
          $('#calendar .fc-agenda-days .ui-widget-header:not(.fc-sat):not(.fc-sun)').addClass('bgC_header2');
          $('#calendar .fc-agenda-divider').addClass('bgC_header2');

          //ヘッダー文字サイズ
          $('#calendar').addClass('fs_13');


          //土曜日表示
          $('#calendar .fc-sat:not(.ui-widget-content.ui-state-highlight)').addClass('bgC_calSaturday');
          $('#calendar .fc-sat').addClass('cl_fontSaturday');
          //日曜日表示
          $('#calendar .fc-sun:not(.ui-widget-content.ui-state-highlight)').addClass('bgC_calSunday');
          $('#calendar .fc-sun').addClass('cl_fontSunday');
          $("td[class*='fc-sat']").removeClass('bgC_calSaturday');
          $("td[class*='fc-sun']").removeClass('bgC_calSunday');
          $("td[class*='fc-sat']").addClass('bgC_tableCell_Saturday');
          $("td[class*='fc-sun']").addClass('bgC_tableCell_Sunday');


          //罫線表示
          $('.fc-border-separate th, .fc-border-separate td').addClass('bor_l1');
          $('.fc-border-separate tr.fc-last th, .fc-border-separate tr.fc-last td').addClass('bor_b1');
          $('.fc-border-separate th.fc-last, .fc-border-separate td.fc-last').addClass('bor_r1');
          $('#calendar .fc-agenda-divider').addClass('bor1');
          $('.fc-agenda-slots tr:not(.fc-minor):not(:first-child) th').addClass('bor_t1');

          //当日表示
          $('#calendar .ui-widget-content.ui-state-highlight').addClass('bgC_tableCell');

          //六曜表示
          for (var i = 0; i < 7; i++) {
              $("th[class*=fc-col" + i + "]").addClass(jsonObject["rokuyouList"][i]);
              $("th[class*=fc-col" + i + "]").addClass("kojinHeader_rokuyou");
          }

          if ($('#calendar .ui-widget-content.ui-state-highlight').length > 0) {

            var week = $('#calendar .ui-widget-content.ui-state-highlight')[0].className.split(" ")[0];

          $("th[class*='" + week + "']").removeClass("bgC_header2");
          $("th[class*='" + week + "']").removeClass("bgC_calSaturday");
          $("th[class*='" + week + "']").removeClass("bgC_calSunday");
          $("th[class*='" + week + "']").addClass("bgC_select");

          }

          $('#calendar .ui-widget-header').removeClass('ui-widget-header');

          window_create();
        }
    );
  });
</script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<link rel=stylesheet href='../schedule/css/fullcalendar_jq_thme_reset.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/fullcalendar/fullcalendar.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/fullcalendar/fullcalendar.print.css?<%= GSConst.VERSION_PARAM %>' media='print' type='text/css'>

</head>
<body  onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);">
<html:form action="/schedule/sch200">
<input type="hidden" name="CMD" value="">
<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="sch010DspDate" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SelectUsrKbn" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch010CrangeKbn" />
<html:hidden property="sch200DayDelta" />
<html:hidden property="sch200MinuteDelta" />
<html:hidden property="sch200EventPosition" />
<html:hidden property="sch200BatchRef" />
<html:hidden property="sch200ResBatchRef" />
<html:hidden property="sch200Cancel" />
<html:hidden property="sch200FrDate" />
<html:hidden property="sch200ToDate" />

<html:hidden property="sch040FrHour" value="" />
<html:hidden property="sch040FrMin" value="" />
<html:hidden property="sch040ToYear" value="" />
<html:hidden property="sch040ToMonth" value="" />
<html:hidden property="sch040ToDay" value="" />
<html:hidden property="sch040ToHour" value="" />
<html:hidden property="sch040ToMin" value="" />
<html:hidden property="sch040TimeKbn" value="0" />

<logic:notEmpty name="sch200Form" property="schNotAccessGroupList" scope="request">
  <logic:iterate id="notAccessGroup" name="sch200Form" property="schNotAccessGroupList" scope="request">
    <input type="hidden" name="schNotAccessGroup" value="<bean:write name="notAccessGroup"/>">
  </logic:iterate>
</logic:notEmpty>

<!--　BODY -->


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
        <gsmsg:write key="schedule.19" />
      </li>
      <li>
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
        <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.19" />" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
          <gsmsg:write key="schedule.19" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.days" />" onClick="buttonPush('day');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
          <gsmsg:write key="cmn.days2" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weekly" />" onClick="buttonPush('week');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <gsmsg:write key="cmn.weeks" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.between.mon" />" onClick="moveMonthSchedule('month', <bean:write name="sch200Form" property="sch010SelectUsrSid" />, <bean:write name="sch200Form" property="sch010SelectUsrKbn" />);;">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <gsmsg:write key="cmn.monthly" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.listof" />" onClick="moveListSchedule('list', <bean:write name="sch200Form" property="sch010SelectUsrSid" />, <bean:write name="sch200Form" property="sch010SelectUsrKbn" />);">
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
          <button type="button" class="iconBtn-border" value="Cal" onClick="wrtCalendarByBtn(this.form.sch010DspDate, 'sch200Btn');" id="sch200CalBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
            <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
          </button>
        </span>
      </div>

      <table class="table-top cal_table2 w100 mb0">
      <tr>
        <th class="table_title-color" >
          <span class="verAlignMid w100">

            <span class="fs_18 mr10"><bean:write name="sch200Form" property="sch200StrDspDate" /></span>
            <html:select property="sch010DspGpSid" styleClass="ml5 wp250" onchange="changeGroupCombo('chgroup');">

              <logic:notEmpty name="sch200Form" property="sch010GpLabelList" scope="request">
              <logic:iterate id="gpBean" name="sch200Form" property="sch010GpLabelList" scope="request">

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
            <logic:equal name="sch200Form" property="sch010CrangeKbn" value="0">
              <button type="button" class="iconBtn-border ml5" onClick="openGroupWindow(this.form.sch010DspGpSid, 'sch010DspGpSid', '1', 'chgroup', 0, '', 'schNotAccessGroup');" id="sch010GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              </button>
            </logic:equal>

            <%-- 月間スケジュールの表示ユーザ設定 --%>

            <logic:notEmpty name="sch200Form" property="sch200UsrLabelList">

              <html:select property="sch100SelectUsrSid" styleClass="ml5 wp200" onchange="changeUserCombo();">
                <logic:iterate id="nowSelectUserLabel" name="sch200Form" property="sch200UsrLabelList">

                  <%-- 初期検索対象ユーザ設定のためのデータを取得  --%>
                  <bean:define id="selectUser" name="sch200Form" property="sch100SelectUsrSid"/>
                  <bean:define id="nowSelectUser" name="nowSelectUserLabel" property="value"/>

                  <%-- 検索対象ユーザの初期設定(無効) --%>
                  <logic:equal name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                    <% if (selectUser.equals(String.valueOf(nowSelectUser))) { %>
                      <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption" selected>
                    <% } else { %>
                      <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption">
                    <% } %>
                    <bean:write name="nowSelectUserLabel" property="label"/></option>
                  </logic:equal>

                  <%-- 検索対象ユーザの初期設定(有効) --%>
                  <logic:notEqual name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                    <% if (selectUser.equals(String.valueOf(nowSelectUser))) { %>
                      <option value='<bean:write name="nowSelectUserLabel" property="value"/>'  selected>
                    <% } else { %>
                      <option value='<bean:write name="nowSelectUserLabel" property="value"/>'>
                    <% } %>
                    <bean:write name="nowSelectUserLabel" property="label"/></option>
                  </logic:notEqual>

                </logic:iterate>
              </html:select>
            </logic:notEmpty>
            <div class="mrl_auto"></div>
            <html:text property="sch010searchWord" styleClass="wp150 js_schSearch" maxlength="50"  />
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <gsmsg:write key="cmn.search" />
            </button>
          </span>
        </th>
      </tr>
    </table>
  </div>
</span>

<div class="wrapper js_sch_under_frame">
  <div id='calendar' class="bgC_tableCell"></div>
</div>

<div class="display_n">
  <div id="dialog" title="<gsmsg:write key="schedule.sch200.23" />" >
    <ul class="display_inline m0 mt20 p0">
      <li>
         <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.information2">
         <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.information2">
      </li>
      <li class="ml10 txt_l">
        <gsmsg:write key="schedule.sch200.13" /><br>
        <gsmsg:write key="schedule.sch200.16" />
      </li>
    </ul>
  </div>

  <div id="dialog2" title="<gsmsg:write key="schedule.sch200.24" />" >
    <ul class="display_inline m0 mt20 p0">
      <li>
         <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.information2">
         <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.information2">
      </li>
      <li class="ml10 txt_l">
        <gsmsg:write key="schedule.135" />
      </li>
    </ul>

  </div>

  <div id="dialog3" title="<gsmsg:write key="schedule.sch200.25" />" >
    <ul class="display_inline m0 mt20 p0">
      <li>
         <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.information2">
         <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.information2">
      </li>
      <li class="ml10 txt_l">
        <gsmsg:write key="schedule.134" />
      </li>
    </ul>
  </div>

  <div id="dialog4" title="<gsmsg:write key="schedule.sch200.26" />" >
    <ul class="display_inline m0 mt20 p0">
      <li>
         <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.information2">
         <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.information2">
      </li>
      <li class="ml10 txt_l">
        <gsmsg:write key="schedule.sch200.17" />
      </li>
    </ul>
  </div>

  <div id="dialog5" title="<gsmsg:write key="schedule.148" />" >
    <ul class="display_inline m0 mt20 p0">
      <li>
         <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.information2">
         <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.information2">
      </li>
      <li class="ml10 txt_l">
        <gsmsg:write key="schedule.sch200.19" />
      </li>
    </ul>
  </div>

  <div id="dialog6" title="<gsmsg:write key="cmn.warning" />" >
    <ul class="display_inline m0 mt20 p0">
      <li>
         <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.information2">
         <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.information2">
      </li>
      <li class="ml10 txt_l">
        <gsmsg:write key="schedule.sch200.21" />
      </li>
    </ul>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>