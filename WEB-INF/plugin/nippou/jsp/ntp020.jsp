<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.list" />(<gsmsg:write key="cmn.monthly" />)</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../nippou/js/ntp020.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="ntp020Form" property="ntp020Reload" value="0">
    var reloadinterval = <bean:write name="ntp020Form" property="ntp020Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
  </logic:notEqual>
-->
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
<body onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);">
<html:form action="/nippou/ntp020">
<input type="hidden" name="CMD" value="">
<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="ntp010DspDate" />
<html:hidden property="ntp010SelectDate" />
<html:hidden property="ntp010NipSid" />
<html:hidden property="ntp010HistoryAnkenSid" />
<html:hidden property="ntp010HistoryCompSid" />
<html:hidden property="ntp010HistoryCompBaseSid" />
<html:hidden property="ntp010SessionUsrId" />
<html:hidden property="ntp010CrangeKbn" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->
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
      <gsmsg:write key="cmn.monthly" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('import');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
      </div>
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
          <td class=" fs_16 no_w w95 lh_normal">
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
        <logic:equal name="ntp020Form" property="adminKbn" value="1">
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
            <logic:notEmpty name="ntp020Form" property="ankenHistoryList">
              <table class="w100 table-top">
                <tr>
                  <th class="txt_l">
                    <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_adr_history.gif" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />">
                    <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_adr_history.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />">
                    <gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />
                  </th>
                </tr>
                <logic:iterate id="ankenMdl" name="ntp020Form" property="ankenHistoryList">
                  <tr class="js_listHover cursor_p bgC_tableCell">
                    <td id="<bean:write name="ankenMdl" property="nanSid" />" class="js_ankenListClick">
                     <bean:write name="ankenMdl" property="nanName" />
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:notEmpty>
            <logic:notEmpty name="ntp020Form" property="companyHistoryList">
              <table class="w100 table-top">
                <tr>
                  <th class="txt_l">
                    <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_cmp_history.gif" alt="<gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />">
                    <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_cmp_history.png" alt="<gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />">
                    <gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />
                  </th>
                </tr>
                <logic:iterate id="companyMdl" name="ntp020Form" property="companyHistoryList">
                  <tr class="js_listHover cursor_p bgC_tableCell">
                    <td class="js_companyListClick" id="<bean:write name="companyMdl" property="companySid" />">
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
          <td class="nippou_menuArea">&nbsp;</td>
          <td class="nippou_menuArea bor_r1 borC_light">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <div class="verAlignMid w100">
        <span class="wp100"></span>
        <span class="mrl_auto"></span>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.timeline" />" onClick="buttonPush('day');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <gsmsg:write key="cmn.timeline" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('week');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <gsmsg:write key="cmn.weeks" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.monthly" />" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <gsmsg:write key="cmn.monthly" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('list');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
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
          <button type="button" class="iconBtn-border" value="Cal" onClick="wrtCalendarByBtn(this.form.ntp010DspDate, 'ntp020CalBtn')" id="ntp020CalBtn">
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
          <th colspan="7" class="table_title-color">
            <span class="verAlignMid flo_l">
              <span class="fs_18 mr10"><bean:write name="ntp020Form" property="ntp020StrDspDate" scope="request" /></span>
              <gsmsg:write key="cmn.show.group" />
              <html:select property="ntp010DspGpSid" styleClass="ml5 wp250" onchange="changeGroupCombo();">
                <logic:notEmpty name="ntp020Form" property="ntp010GpLabelList" scope="request">
                  <logic:iterate id="gpBean" name="ntp020Form" property="ntp010GpLabelList" scope="request">
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
              <logic:notEqual name="ntp020Form" property="ntp010CrangeKbn" value="1">
                <button type="button" class="iconBtn-border ml5" value="Cal" onClick="openGroupWindow(this.form.ntp010DspGpSid, 'ntp010DspGpSid', '1');" id="ntp010GroupBtn">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                </button>
              </logic:notEqual>
              <html:select property="ntp020SelectUsrSid" styleClass="wp200 ml5 flo_r" onchange="changeUserCombo();">
                <logic:iterate id="user" name="ntp020Form" property="ntp020UsrLabelList" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                  <bean:define id="mukoUserClass" value=""/>
                  <logic:equal value="1" name="user" property="usrUkoFlg"><bean:define id="mukoUserClass" value="mukoUserOption"/></logic:equal>
                  <html:option value="<%= user.getValue() %>" styleClass="<%= mukoUserClass %>"><bean:write name="user" property="label"/></html:option>
                </logic:iterate>
              </html:select>
            </span>
            <logic:equal name="ntp020Form" property="ntp020SelectUsrSid" value="-1">
              <input type="hidden" name="ntp010SelectUsrSid" value="<bean:write name="ntp020Form" property="ntp010DspGpSid" />">
              <input type="hidden" name="ntp010SelectUsrKbn" value="1">
            </logic:equal>
            <logic:equal name="ntp020Form" property="ntp020SelectUsrSid" value="-2">
              <input type="hidden" name="ntp010SelectUsrSid" value="<bean:write name="ntp020Form" property="ntp010DspGpSid" />">
              <input type="hidden" name="ntp010SelectUsrKbn" value="1">
            </logic:equal>
            <logic:notEqual name="ntp020Form" property="ntp020SelectUsrSid" value="-1">
              <logic:notEqual name="ntp020Form" property="ntp020SelectUsrSid" value="-2">
                <input type="hidden" name="ntp010SelectUsrSid" value="<bean:write name="ntp020Form" property="ntp020SelectUsrSid" />">
                <input type="hidden" name="ntp010SelectUsrKbn" value="0">
              </logic:notEqual>
            </logic:notEqual>
          </th>
        </tr>
        <tr class="calendar_weekHeader">
          <td class="no_w w12 cal_header bgC_calSunday cl_fontSunday">
            <span class="fw_b">
              <gsmsg:write key="cmn.sunday" />
            </span>
          </td>
          <td class="no_w w12 cal_header">
            <span class="fw_b">
              <gsmsg:write key="cmn.Monday" />
            </span>
          </td>
          <td class="no_w w12 cal_header">
            <span class="fw_b">
              <gsmsg:write key="cmn.tuesday" />
            </span>
          </td>
          <td class="no_w w12 cal_header">
            <span class="fw_b">
              <gsmsg:write key="cmn.wednesday" />
            </span>
          </td>
          <td class="no_w w12 cal_header">
            <span class="fw_b">
              <gsmsg:write key="cmn.thursday" />
            </span>
          </td>
          <td class="no_w w12 cal_header">
            <span class="fw_b">
              <gsmsg:write key="cmn.friday" />
            </span>
          </td>
          <td class="no_w w12 cal_header bgC_calSaturday cl_fontSaturday">
            <span class="fw_b">
              <gsmsg:write key="cmn.saturday" />
            </span>
          </td>
        </tr>
        <logic:notEmpty name="ntp020Form" property="ntp020NippouList" scope="request">
          <logic:iterate id="monthMdl" name="ntp020Form" property="ntp020NippouList" scope="request">
            <bean:define id="usrMdl" name="monthMdl" property="ntp020UsrMdl"/>
            <logic:notEmpty name="monthMdl" property="ntp020NtpList">
              <logic:iterate id="dayMdl" name="monthMdl" property="ntp020NtpList">
                <logic:equal name="dayMdl" property="weekKbn" value="1">
                  <tr>
                    <logic:equal name="dayMdl" property="todayKbn" value="1">
                      <td class="bgC_select txt_t hp100">
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                      <td class="bgC_tableCell_Sunday txt_t hp100">
                    </logic:notEqual>
                </logic:equal>
                <logic:equal name="dayMdl" property="weekKbn" value="7">
                  <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                    <td class="bgC_tableCell_Saturday txt_t hp100">
                  </logic:notEqual>
                  <logic:equal name="dayMdl" property="todayKbn" value="1">
                    <td class="bgC_select txt_t hp100">
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="dayMdl" property="weekKbn" value="1">
                  <logic:notEqual name="dayMdl" property="weekKbn" value="7">
                    <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                      <td class="bgC_tableCell txt_t hp100">
                    </logic:notEqual>
                    <logic:equal name="dayMdl" property="todayKbn" value="1">
                      <td class="bgC_select txt_t hp100">
                    </logic:equal>
                  </logic:notEqual>
                </logic:notEqual>
                <div>
                <logic:equal name="ntp020Form" property="authAddEditKbn" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.AUTH_ADD_EDIT) %>">
                  <a href="#" onClick="addNippou('add', <bean:write name="dayMdl" property="ntpDate" />, <bean:write name="dayMdl" property="usrSid" />, <bean:write name="dayMdl" property="usrKbn" />);">
                    <img class="btn_classicImg-display eventImg" src="../nippou/images/classic/icon_add_nippou.gif" alt="<gsmsg:write key="cmn.add" />">
                    <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  </a>
                </logic:equal>
                <span class="flo_r">
                  <logic:notEmpty name="dayMdl" property="dspRokuyo">
                    <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="fw_b fs_11 cl_fontBody"><bean:write name="dayMdl" property="dspRokuyo" /></span>
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="opacity6 fs_11 cl_fontBody"><bean:write name="dayMdl" property="dspRokuyo" /></span>
                    </logic:notEqual>
                  </logic:notEmpty>
                  <% String fontCol = "cl_fontBody"; %>
                  <logic:notEmpty name="dayMdl" property="holidayName">
                    <% fontCol = "cl_fontSunday"; %>
                  </logic:notEmpty>
                  <logic:equal name="dayMdl" property="todayKbn" value="1">
                    <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="<%= fontCol %> td_u fw_b fs_17 wp25 txt_c flo_r">
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="<%= fontCol %> td_u opacity6 fs_17 wp25 txt_c flo_r">
                    </logic:notEqual>
                  </logic:equal>
                  <logic:notEqual name="dayMdl" property="todayKbn" value="1">
                    <logic:equal name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="<%= fontCol %> fw_b fs_17 wp25 txt_c flo_r">
                    </logic:equal>
                    <logic:notEqual name="dayMdl" property="thisMonthKbn" value="1">
                      <span class="<%= fontCol %> fw_n opacity6 fs_17 wp25 txt_c flo_r">
                    </logic:notEqual>
                  </logic:notEqual>
                  <bean:write name="dayMdl" property="dspDay" />
                  </span>
                </span>
                </div>
                <%--休日名称--%>
                <logic:notEmpty name="dayMdl" property="holidayName">
                  <div class="txt_r lh100 clear_b">
                    <span class="txt_l display_inline-block cl_fontSunday fs_10 lh100 mt5">
                      <bean:write name="dayMdl" property="holidayName" />
                    </span>
                  </div>
                </logic:notEmpty>

                <logic:notEmpty name="dayMdl" property="ntpDataList">
                  <logic:iterate id="ntpMdl" name="dayMdl" property="ntpDataList">
                    <bean:define id="u_usrsid" name="dayMdl" property="usrSid" type="java.lang.Integer" />
                    <bean:define id="u_ntpsid" name="ntpMdl" property="ntpSid" type="java.lang.Integer" />
                    <bean:define id="u_date" name="dayMdl" property="ntpDate"  type="java.lang.String" />
                    <bean:define id="chkKbn" name="ntpMdl" property="ntp_chkKbn" type="java.lang.Integer" />
                    <% String chkClass = "ntp_checkContent bgC_lightGray"; %>
                    <% if (chkKbn == 1) { %>
                    <%    chkClass = ""; %>
                    <% } %>
                    <div class="<%= chkClass %>">
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
                        <span class="cal_time">
                          <bean:write name="ntpMdl" property="time" />
                        </span>
                      </div>
                    </logic:notEmpty>
                    <div class="cal_content">
                      <bean:write name="ntpMdl" property="title" />
                    </div>
                    </div>
                    </a>
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
                </td>
                <logic:equal name="dayMdl" property="weekKbn" value="7">
                  </tr>
                </logic:equal>
              </logic:iterate>
            </logic:notEmpty>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </div>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>