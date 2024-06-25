<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
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
<title>GROUPSESSION <gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.list" />(<gsmsg:write key="cmn.weeks" />)</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../nippou/js/ntp010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript">
</script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<% long ntpTipCnt = 0; %>
<body onload="setToUser();" onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);">
<html:form action="/nippou/ntp010">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="dspMod" value="1">
<input type="hidden" name="ntp010SelectUsrSid" value="-1">
<input type="hidden" name="ntp010SelectUsrKbn" value="0">
<html:hidden property="cmd" />
<html:hidden property="ntp010DspDate" />
<html:hidden property="ntp010SelectDate" />
<html:hidden property="ntp010NipSid" />
<html:hidden property="ntp010SelectUsrAreaSid" />
<html:hidden property="ntp010HistoryAnkenSid" />
<html:hidden property="ntp010HistoryCompSid" />
<html:hidden property="ntp010HistoryCompBaseSid" />
<html:hidden property="ntp010SessionUsrId" />
<html:hidden property="ntp010CrangeKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.weeks" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('import');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <gsmsg:write key="cmn.import" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
  <div class="display_inline w100 bor1 bgC_body">
    <div class="w20 m0 p0 ">
      <table class="w100 h100">
        <tr class="ntp_sideMenu-select bgC_Body txt_l border_top_none borC_body borBottomC_light">
          <td class="ntp_menuIcon-select w5">
            <img class="header_pluginImg-classic ml5" src="../nippou/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="ntp.1" />">
            <img class="header_pluginImg ml5" src="../nippou/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="ntp.1" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('anken');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="ntp.11" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken_32.png" alt="<gsmsg:write key="ntp.11" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.11" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('target');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_target_25.png" alt="<gsmsg:write key="ntp.12" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_target_32.png" alt="<gsmsg:write key="ntp.12" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal ">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.12" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('bunseki');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_bunseki.gif" alt="<gsmsg:write key="ntp.13" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_bunseki_32.png" alt="<gsmsg:write key="ntp.13" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.13" /></span>
          </td>
        </tr>
        <logic:equal name="ntp010Form" property="adminKbn" value="1">
          <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('masta');">
            <td class="ntp_menuIcon  w5">
              <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_mente.gif" alt="<gsmsg:write key="ntp.14" />">
              <img class="btn_originalImg-display ml5" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="ntp.14" />">
            </td>
            <td class=" fs_16 no_w w95 lh_normal">
              <span class="timeline_menu ml5"><gsmsg:write key="ntp.14" /></span>
            </td>
          </tr>
        </logic:equal>
        <tr>
          <td colspan="2" class="p5 nippou_menuArea bor_r1 borC_light">
            <logic:notEmpty name="ntp010Form" property="ankenHistoryList">
              <table class="w100 table-top">
                <tr>
                  <th class="txt_l">
                    <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_adr_history.gif" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />">
                    <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_adr_history.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />">
                    <gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />
                  </th>
                </tr>
                <logic:iterate id="ankenMdl" name="ntp010Form" property="ankenHistoryList">
                  <tr class="js_listHover cursor_p bgC_tableCell">
                    <td id="<bean:write name="ankenMdl" property="nanSid" />" class="js_ankenListClick">
                     <bean:write name="ankenMdl" property="nanName" />
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:notEmpty>
            <logic:notEmpty name="ntp010Form" property="companyHistoryList">
              <table class="w100 table-top">
                <tr>
                  <th class="txt_l">
                    <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_cmp_history.gif" alt="<gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />">
                    <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_cmp_history.png" alt="<gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />">
                    <gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />
                  </th>
                </tr>
                <logic:iterate id="companyMdl" name="ntp010Form" property="companyHistoryList">
                  <tr class="js_listHover cursor_p">
                    <td class="js_companyListClick bgC_tableCell" id="<bean:write name="companyMdl" property="companySid" />">
                      <span id="<bean:write name="companyMdl" property="companyBaseSid" />">
                        <bean:write name="companyMdl" property="companyName" /><wbr>
                        <logic:notEmpty name="companyMdl" property="companyBaseName">
                          <bean:write name="companyMdl" property="companyBaseName" />
                        </logic:notEmpty>
                      </span>
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:notEmpty>
          </td>
        </tr>
        <tr class="h100 ntp_sideMenuArea bor_r1 borC_light">
          <td class="nippou_menuArea h100">&nbsp;</td>
          <td class="nippou_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <div class="verAlignMid w100">
        <bean:size id="topSize" name="ntp010Form" property="ntp010TopList" scope="request" />
        <logic:equal name="topSize" value="2">
          <logic:iterate id="weekBean" name="ntp010Form" property="ntp010TopList" scope="request" offset="1"/>
        </logic:equal>
        <logic:notEqual name="topSize" value="2">
          <logic:iterate id="weekBean" name="ntp010Form" property="ntp010TopList" scope="request" offset="0"/>
        </logic:notEqual>
        <span class="wp100"></span>
        <span class="mrl_auto"></span>
        <bean:define id="usrBean" name="weekBean" property="ntp010UsrMdl"/>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.timeline" />" onClick="moveDailyNippou('day', <bean:write name="ntp010Form" property="ntp010DspDate" />, <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <gsmsg:write key="cmn.timeline" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <gsmsg:write key="cmn.weeks" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.monthly" />" onClick="moveMonthNippou('month', <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <gsmsg:write key="cmn.monthly" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="moveListNippou('list', <bean:write name="usrBean" property="usrSid" />, <bean:write name="usrBean" property="usrKbn" />);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
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
          <button type="button" class="iconBtn-border" value="Cal" onClick="wrtCalendarByBtn(this.form.ntp010DspDate, 'ntp010CalBtn')" id="ntp010CalBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
            <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
          </button>
        </span>
      </div>
      <logic:messagesPresent message="false">
        <html:errors/>
      </logic:messagesPresent>
      <table class="table-top cal_table2 w100 mb0">
        <tr>
          <th colspan="5" class="topFrame_btnGroup table_title-color">
            <span class="verAlignMid">
            <span class="fs_18 mr10"><bean:write name="ntp010Form" property="ntp010StrDspDate" /></span>
            <gsmsg:write key="cmn.show.group" />
            <html:select property="ntp010DspGpSid" styleClass="ml5 wp300" onchange="changeGroupCombo();">
              <logic:notEmpty name="ntp010Form" property="ntp010GpLabelList" scope="request">
                <logic:iterate id="gpBean" name="ntp010Form" property="ntp010GpLabelList" scope="request">
                  <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                    <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:notEqual name="gpBean" property="styleClass" value="0">
                    <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:notEqual>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>
            <logic:notEqual name="ntp010Form" property="ntp010CrangeKbn" value="1">
              <button type="button" class="iconBtn-border ml5" value="Cal" onClick="openGroupWindow(this.form.ntp010DspGpSid, 'ntp010DspGpSid', '1');" id="ntp010GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              </button>
            </logic:notEqual>
            </span>
          </th>
          <th colspan="3" class="w40 txt_r border_left_none table_title-color">
            <html:text property="ntp010searchWord" styleClass="wp150" maxlength="50" />
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');" id="ntp010GroupBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <gsmsg:write key="cmn.search" />
            </button>
          </th>
        </tr>
      </table>
      <div class="ofy_a hp500">
        <table class="table-top cal_table w100 border_top_none m0">
          <tr class="calendar_weekHeader">
            <td class="cal_header fw_b w16 border_top_none">
              <gsmsg:write key="cmn.name" />
            </td>
            <logic:notEmpty name="ntp010Form" property="ntp010CalendarList" scope="request">
              <logic:iterate id="calBean" name="ntp010Form" property="ntp010CalendarList" scope="request">
                <bean:define id="tdColor" value="" />
                <% String[] tdColors = new String[] {"cal_header", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"};
                   String clFont = "";
                %>
                <logic:equal name="calBean" property="todayKbn" value="1">
                  <% tdColor = tdColors[1]; %>
                </logic:equal>
                <logic:notEqual name="calBean" property="todayKbn" value="1">
                 <logic:equal name="calBean" property="holidayKbn" value="1">
                   <% tdColor = tdColors[0];
                      clFont = "cl_fontSunday";
                   %>
                 </logic:equal>
                <logic:notEqual name="calBean" property="holidayKbn" value="1">
                  <logic:equal name="calBean" property="weekKbn" value="1">
                  <% tdColor = tdColors[3];
                     clFont = "cl_fontSunday";
                  %>
                  </logic:equal>
                  <logic:equal name="calBean" property="weekKbn" value="7">
                    <% tdColor = tdColors[2];
                       clFont = "cl_fontSaturday";
                    %>
                  </logic:equal>
                  <logic:notEqual name="calBean" property="weekKbn" value="1">
                    <logic:notEqual name="calBean" property="weekKbn" value="7">
                       <% tdColor = tdColors[0]; %>
                    </logic:notEqual>
                  </logic:notEqual>
                </logic:notEqual>
                </logic:notEqual>
                <bean:define id="rkyKbn" name="calBean" property="rokuyoKbn" />
                <%
                  String rkyName = "";
                  if (rkyKbn.equals(GSConst.RKY_KBN_SENSHOU)) {
                      rkyName = "senshou";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_TOMOBIKI)) {
                      rkyName = "tomobiki";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_SENBU)) {
                      rkyName = "senbu";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_BUTSUMETSU)) {
                      rkyName = "butsumetsu";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_TAIAN)) {
                      rkyName = "taian";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_SHAKKOU)) {
                      rkyName = "shakkou";
                  }
                %>
                <td class="no_w <%= tdColor %> w12 border_top_none">
                  <div class="calendar_dayHeader <%= rkyName %>">
                    <span class="fw_b <%= clFont %>">
                      <bean:write name="calBean" property="dspDayString" />
                    </span>
                  </div>
                </td>
              </logic:iterate>
            </logic:notEmpty>
          </tr>
          <logic:notEmpty name="ntp010Form" property="ntp010TopList" scope="request">
            <logic:iterate id="weekMdl" name="ntp010Form" property="ntp010TopList" scope="request">
              <tr class="txt_l txt_t">
                <bean:define id="usrMdl" name="weekMdl" property="ntp010UsrMdl"/>
                <logic:notEqual name="usrMdl" property="usrKbn" value="1">
                  <logic:equal name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
                    <logic:equal name="usrMdl" property="zaisekiKbn" value="1">
                      <td class="cal_colHeader-zaiseki w16">
                    </logic:equal>
                    <logic:equal name="usrMdl" property="zaisekiKbn" value="2">
                      <td class="cal_colHeader-huzai w16">
                    </logic:equal>
                    <logic:equal name="usrMdl" property="zaisekiKbn" value="0">
                      <td class="cal_colHeader-sonota w16">
                    </logic:equal>
                  </logic:equal>
                  <logic:notEqual name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
                    <td class="bgC_tableCell w16">
                  </logic:notEqual>
                  <%-- ユーザ・グループ名 --%>
                  <a href="#!" onClick="openUserInfoWindow(<bean:write name="usrMdl" property="usrSid" />);">
                    <span class="fs_14"><bean:write name="usrMdl" property="usrName" /></span>
                  </a>
                  <span class="cl_fontNtpTitleBlack fs_13"><bean:write name="usrMdl" property="zaisekiMsg" /></span><br>
                  <a class="fs_12" href="#" onClick="moveMonthNippou('month', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />);">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
                    <gsmsg:write key="cmn.monthly" />
                  </a><br>
                  <a class="fs_12" href="#" onClick="moveListNippou('list', <bean:write name="usrMdl" property="usrSid" />, <bean:write name="usrMdl" property="usrKbn" />);">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
                    <gsmsg:write key="cmn.list" />
                  </a><br>
                  <logic:equal name="ntp010Form" property="smailUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
                    <logic:equal name="usrMdl" property="smlAble" value="1">
                      <a href="#" class="js_smlSendLink fs_12" id="<bean:write name="usrMdl" property="usrSid" />">
                        <img class="btn_classicImg-display wp16hp18" src="../common/images/classic/icon_smail.gif" alt="<gsmsg:write key="cmn.shortmail" />">
                        <img class="btn_originalImg-display wp16hp18" src="../schedule/images/original/plugin_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
                        <gsmsg:write key="cmn.shortmail" />
                      </a><br>
                    </logic:equal>
                  </logic:equal>
                  <logic:equal name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
                    <div class="no_w">
                      <!-- 在席 -->
                      <% String zaisekiClass = ""; %>
                      <% String zaisekiSelected = ""; %>
                      <% String fuzaiSelected = ""; %>
                      <% String sonotaSelected = ""; %>
                      <logic:equal name="usrMdl" property="zaisekiKbn" value="1">
                        <% zaisekiClass = "zaiseki_select-zaiseki"; %>
                        <% zaisekiSelected = "selected"; %>
                      </logic:equal>
                      <!-- 不在 -->
                      <logic:equal name="usrMdl" property="zaisekiKbn" value="2">
                        <% zaisekiClass = "zaiseki_select-huzai"; %>
                        <% fuzaiSelected = "selected"; %>
                      </logic:equal>
                      <!-- その他 -->
                      <logic:equal name="usrMdl" property="zaisekiKbn" value="0">
                        <% zaisekiClass = "zaiseki_select-sonota"; %>
                        <% sonotaSelected = "selected"; %>
                      </logic:equal>
                      <select class="wp65 mt5 <%= zaisekiClass %>" onchange="setZaisekiStatus(this, <bean:write name="usrMdl" property="usrSid" />);">
                        <option value="ntp010Zaiseki" <%= zaisekiSelected %>><gsmsg:write key="cmn.zaiseki" /></option>
                        <option value="ntp010Fuzai" <%= fuzaiSelected %>><gsmsg:write key="cmn.absence" /></option>
                        <option value="ntp010Sonota" <%= sonotaSelected %>><gsmsg:write key="cmn.other" /></option>
                      </select>
                    </div>
                  </logic:equal>
                  </td>
                  <%-- 日報情報 --%>
                  <logic:notEmpty name="weekMdl" property="ntp010NtpList">
                    <logic:iterate id="dayMdl" name="weekMdl" property="ntp010NtpList">
                      <logic:equal name="dayMdl" property="weekKbn" value="1">
                        <td class="w12 bgC_tableCell_Sunday txt_t">
                      </logic:equal>
                      <logic:equal name="dayMdl" property="weekKbn" value="7">
                        <td class="w12 bgC_tableCell_Saturday txt_t">
                      </logic:equal>
                      <logic:notEqual name="dayMdl" property="weekKbn" value="1">
                        <logic:notEqual name="dayMdl" property="weekKbn" value="7">
                          <td class="w12 bgC_tableCell txt_t">
                        </logic:notEqual>
                      </logic:notEqual>
                      <a href="#" onClick="return addNippou('add', <bean:write name="dayMdl" property="ntpDate" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
                        <img class="btn_classicImg-display eventImg" src="../nippou/images/classic/icon_add_nippou.gif" alt="<gsmsg:write key="cmn.add" />">
                        <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      </a>
                      <logic:notEmpty name="dayMdl" property="holidayName">
                        <span class="ml5 flo_r fs_10 cl_fontWarn lh100 mt5"><bean:write name="dayMdl" property="holidayName" /></span>
                      </logic:notEmpty>
                      <logic:notEmpty name="dayMdl" property="ntpDataList">
                        <logic:iterate id="ntpMdl" name="dayMdl" property="ntpDataList">
                          <bean:define id="u_usrsid" name="dayMdl" property="usrSid" type="java.lang.Integer" />
                          <bean:define id="u_ntpsid" name="ntpMdl" property="ntpSid" type="java.lang.Integer" />
                          <bean:define id="u_date" name="dayMdl" property="ntpDate"  type="java.lang.String" />
                          <logic:empty name="ntpMdl" property="detail">
                            <a href="#" id="tooltips_ntp<%= String.valueOf(ntpTipCnt++) %>" onClick="editNippou('edit', <bean:write name="dayMdl" property="ntpDate" />, <bean:write name="ntpMdl" property="ntpSid" />, <bean:write name="dayMdl" property="usrSid" />, 0);">
                              <span class="tooltips">
                                <bean:write name="ntpMdl" property="title" />
                              </span>
                          </logic:empty>
                          <logic:notEmpty name="ntpMdl" property="detail">
                            <bean:define id="ntdetailu" name="ntpMdl" property="detail" />
                            <a href="#" id="tooltips_ntp<%= String.valueOf(ntpTipCnt++) %>" onClick="editNippou('edit', <bean:write name="dayMdl" property="ntpDate" />, <bean:write name="ntpMdl" property="ntpSid" />, <bean:write name="dayMdl" property="usrSid" />, 0);">

                              <bean:define id="ntdetailu" name="ntpMdl" property="detail" />
                              <%
                                String tmpText = (String)pageContext.getAttribute("ntdetailu",PageContext.PAGE_SCOPE);
                                String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTmlPlusAmparsant(tmpText);
                              %>
                              <span class="tooltips">
                                <gsmsg:write key="cmn.content" />:<%= tmpText2 %>
                              </span>
                          </logic:notEmpty>

                          <div class="mt5 cl_linkDef">
                            <%-- コメントアイコン表示  --%>
                            <logic:notEqual name="ntpMdl" property="ntp_cmtCnt" value="0">
                              <span class="mr5">
                                <img class="btn_classicImg-display" src="../nippou/images/classic/icon_comment.gif" alt="<gsmsg:write key="cmn.preferences2" />">
                                <img class="btn_originalImg-display" src="../common/images/original/icon_comment.png" alt="<gsmsg:write key="cmn.preferences2" />">
                                <bean:write name="ntpMdl" property="ntp_cmtCnt" />
                              </span>
                            </logic:notEqual>
                            <%-- いいねアイコン表示  --%>
                            <logic:notEqual name="ntpMdl" property="ntp_goodCnt" value="0">
                              <img class="btn_classicImg-display" src="../nippou/images/classic/bg_good_2.gif" alt="<gsmsg:write key="cmn.preferences2" />">
                              <img class="btn_originalImg-display" src="../nippou/images/original/icon_good.png" alt="<gsmsg:write key="cmn.preferences2" />">
                              <bean:write name="ntpMdl" property="ntp_goodCnt" />
                            </logic:notEqual>
                          </div>

                          <%--タイトルカラー設定--%>
                          <logic:equal name="ntpMdl" property="titleColor" value="0">
                            <div class="cl_fontNtpTitleBlue opacity6-hover fs_13">
                          </logic:equal>
                          <logic:equal name="ntpMdl" property="titleColor" value="1">
                            <div class="cl_fontNtpTitleBlue opacity6-hover fs_13">
                          </logic:equal>
                          <logic:equal name="ntpMdl" property="titleColor" value="2">
                            <div class="cl_fontNtpTitleRed opacity6-hover fs_13">
                          </logic:equal>
                          <logic:equal name="ntpMdl" property="titleColor" value="3">
                            <div class="cl_fontNtpTitleGreen opacity6-hover fs_13">
                          </logic:equal>
                          <logic:equal name="ntpMdl" property="titleColor" value="4">
                            <div class="cl_fontNtpTitleYellow opacity6-hover fs_13">
                          </logic:equal>
                          <logic:equal name="ntpMdl" property="titleColor" value="5">
                            <div class="cl_fontNtpTitleBlack opacity6-hover fs_13">
                          </logic:equal>
                          <logic:notEmpty name="ntpMdl" property="time">
                            <div class="lh100">
                              <span class="cal_time no_w">
                                <bean:write name="ntpMdl" property="time" />
                              </span>
                            </div>
                          </logic:notEmpty>
                          <div class="cal_content">
                            <bean:write name="ntpMdl" property="title" />
                          </div>
                          </div>
                          </a>
                        </logic:iterate>
                      </logic:notEmpty>
                      </td>
                    </logic:iterate>
                  </logic:notEmpty>
                </logic:notEqual>
              </tr>
              <tr>
                <th colspan="8" class="table_title-color txt_l">
                  <span class="fs_18">
                    <gsmsg:write key="schedule.74" />
                  </span>
                </th>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
          <tr class="calendar_weekHeader">
            <td class="cal_header fw_b w16 border_top_none">
              <gsmsg:write key="cmn.name" />
            </td>
            <logic:notEmpty name="ntp010Form" property="ntp010CalendarList" scope="request">
              <logic:iterate id="calBean" name="ntp010Form" property="ntp010CalendarList" scope="request">
                <bean:define id="tdColor" value="" />
                <% String[] tdColors = new String[] {"cal_header", "bgC_select","bgC_calSaturday" ,"bgC_calSunday"};
                   String clFont = "";
                %>
                <logic:equal name="calBean" property="todayKbn" value="1">
                  <% tdColor = tdColors[1]; %>
                </logic:equal>
                <logic:notEqual name="calBean" property="todayKbn" value="1">
                 <logic:equal name="calBean" property="holidayKbn" value="1">
                   <% tdColor = tdColors[0];
                      clFont = "cl_fontSunday";
                   %>
                 </logic:equal>
                <logic:notEqual name="calBean" property="holidayKbn" value="1">
                  <logic:equal name="calBean" property="weekKbn" value="1">
                  <% tdColor = tdColors[3];
                     clFont = "cl_fontSunday";
                  %>
                  </logic:equal>
                  <logic:equal name="calBean" property="weekKbn" value="7">
                    <% tdColor = tdColors[2];
                       clFont = "cl_fontSaturday";
                    %>
                  </logic:equal>
                  <logic:notEqual name="calBean" property="weekKbn" value="1">
                    <logic:notEqual name="calBean" property="weekKbn" value="7">
                       <% tdColor = tdColors[0]; %>
                    </logic:notEqual>
                  </logic:notEqual>
                </logic:notEqual>
                </logic:notEqual>

                <bean:define id="rkyKbn" name="calBean" property="rokuyoKbn" />
                <%
                  String rkyName = "";
                  if (rkyKbn.equals(GSConst.RKY_KBN_SENSHOU)) {
                      rkyName = "senshou";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_TOMOBIKI)) {
                      rkyName = "tomobiki";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_SENBU)) {
                      rkyName = "senbu";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_BUTSUMETSU)) {
                      rkyName = "butsumetsu";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_TAIAN)) {
                      rkyName = "taian";
                  } else if (rkyKbn.equals(GSConst.RKY_KBN_SHAKKOU)) {
                      rkyName = "shakkou";
                  }
                %>
                <td class="no_w <%= tdColor %> w12 border_top_none">
                  <div class="calendar_dayHeader <%= rkyName %>">
                    <span class="fw_b <%= clFont %>">
                      <bean:write name="calBean" property="dspDayString" />
                    </span>
                  </div>
                </td>
              </logic:iterate>
            </logic:notEmpty>
          </tr>
          <%-- グループメンバー日報表示 --%>
          <%-- グループメンバー --%>
          <logic:notEmpty name="ntp010Form" property="ntp010BottomList" scope="request">
            <jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp010_list.jsp" />
          </logic:notEmpty>
        </table>
      </div>
      <logic:equal name="ntp010Form" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_USE) %>">
        <div class="flo_l verAlignMid mt10">
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
    </div>
  </div>
</div>

</html:form>

<div id="smlPop" title="" style="display:none">
  <div id="smlCreateArea" class="w100 h100"></div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>