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
<title>GROUPSESSION <gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.list" />(<gsmsg:write key="cmn.days2" />)</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.waypoints.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.tooltip.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../nippou/js/ntp030.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/htmlEscape.js?<%= GSConst.VERSION_PARAM %>"></script>
<script>
  $(function(){
      $('.js_ntp_labelArea').inFieldLabels();
  });
</script>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body onunload="windowClose();calWindowClose();">
<html:form action="/nippou/ntp030">
<input type="hidden" name="CMD" value="">
<html:hidden property="cmd" />
<input type="hidden" name="dspMod" value="3" />
<html:hidden property="ntp010SelectUsrSid" />
<html:hidden property="ntp010SelectUsrKbn" />
<html:hidden property="ntp010SelectDate" />
<html:hidden property="ntp030SessionSid" />
<html:hidden property="ntp030SelectUsrSid" />
<html:hidden property="ntp030Offset" />
<html:hidden property="ntp010NipSid" />
<html:hidden property="ntp030LabelDate" />
<html:hidden property="ntp010DspDate" />
<html:hidden property="ntp010HistoryAnkenSid" />
<html:hidden property="ntp010HistoryCompSid" />
<html:hidden property="ntp010HistoryCompBaseSid" />
<html:hidden property="ntp010SessionUsrId" />
<html:hidden property="ntp010CrangeKbn" />

<%-- セッションユーザ情報 --%>
<bean:define id="susrInfMdl" name="ntp030Form" property="ntp030UsrInfMdl" />
<input type="hidden" name="sUsrBinSid" value="<bean:write name="susrInfMdl" property="binSid" />" />
<input type="hidden" name="sUsrPictKf" value="<bean:write name="susrInfMdl" property="usiPictKf" />" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div id="pageTopArea"></div>
  <div id="time_line_fix" class="w100 timeLine_jump">
    <div class="w20 bgC_lightGray borC_light timeLine_jumpContent">
      <span class="time_line_fix_top cursor_p cl_linkDef" id="scTop"><gsmsg:write key="tcd.tcd040.22" /><gsmsg:write key="ntp.19" /></span>
      <select id="select_fix_date" class="ml10" onchange="selConbChange();"></select>
    </div>
  </div>


<!--　BODY -->
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
      <gsmsg:write key="cmn.timeline" />
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
    <div class="w20 m0 p0">
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
        <logic:equal name="ntp030Form" property="adminKbn" value="1">
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
            <logic:notEmpty name="ntp030Form" property="ankenHistoryList">
              <table class="w100 table-top">
                <tr>
                  <th class="txt_l">
                    <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_adr_history.gif" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />">
                    <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_adr_history.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />">
                    <gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.17" />
                  </th>
                </tr>
                <logic:iterate id="ankenMdl" name="ntp030Form" property="ankenHistoryList">
                  <tr class="js_listHover cursor_p bgC_tableCell">
                    <td id="<bean:write name="ankenMdl" property="nanSid" />" class="js_ankenListClick">
                     <bean:write name="ankenMdl" property="nanName" />
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:notEmpty>
            <logic:notEmpty name="ntp030Form" property="companyHistoryList">
              <table class="w100 table-top">
                <tr>
                  <th class="txt_l">
                    <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_cmp_history.gif" alt="<gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />">
                    <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_cmp_history.png" alt="<gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />">
                    <gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /><gsmsg:write key="ntp.17" />
                  </th>
                </tr>
                <logic:iterate id="companyMdl" name="ntp030Form" property="companyHistoryList">
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
          <td class="nippou_menuArea">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <div class="verAlignMid w100">
        <span class="wp100"></span>
        <span class="mrl_auto"></span>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.timeline" />" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <gsmsg:write key="cmn.timeline" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('week');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <gsmsg:write key="cmn.weeks" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.monthly" />" onClick="moveMonthNippou('month', '', '');">
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
        <span class="wp200">
        </span>
      </div>
      <logic:messagesPresent message="false">
        <html:errors/>
      </logic:messagesPresent>
      <div class="w100 display_inline">
        <div class="w70 ml5 timeLine">
          <div class="js_timeLine">
          <div class="txt_r">
            <span class="flo_l">
            <html:select property="ntp030Sort" onchange="changeGroupCombo();">
              <logic:notEmpty name="ntp030Form" property="ntp030SortLabel" scope="request">
                <logic:iterate id="labelBean" name="ntp030Form" property="ntp030SortLabel" scope="request">
                  <bean:define id="labelValue" name="labelBean" property="value" type="java.lang.String" />
                  <html:option value="<%= labelValue %>"><bean:write name="labelBean" property="label" /></html:option>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>
            </span>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="return addNippou('add', <bean:write name="ntp030Form" property="ntp010SelectDate" />, <bean:write name="ntp030Form" property="ntp030SessionSid" />, 0);">
              <img class="btn_classicImg-display" src="../nippou/images/classic/icon_add_nippou.gif" alt="<gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.new.registration" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.new.registration" />">
              <gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.new.registration" />
            </button>
          </div>
          <logic:notEmpty name="ntp030Form" property="ntp030DataModelList">
            <% String beforeLabel = ""; %>
            <% String positionLabel = ""; %>
            <% String beforePositionLabel = ""; %>
            <% String dayLineSpace = ""; %>
            <logic:iterate id="dataMdl" name="ntp030Form" property="ntp030DataModelList"  indexId="idx">
              <bean:define id="labelDate" name="dataMdl" property="ntp030NtpDate" type="java.lang.String" />
              <% positionLabel = labelDate.substring(0,8); %>

              <% if (!labelDate.equals(beforeLabel)) { %>
                <div class="w100 js_dataDateArea <%= dayLineSpace %>" id="<%= labelDate %>">
                  <p class="ntp_dayLine cl_fontWeek fw_b">
                    <bean:write name="dataMdl" property="ntp030LabelDate" />
                  </p>
                </div>
              <% } %>
              <% if (!positionLabel.equals(beforePositionLabel)) { %>
                <span id="position_<%= positionLabel %>"></span>
              <% } %>
              <% dayLineSpace = "mt50"; %>

              <table class="table-top w100">
                <tr>
                  <th class="border_right_none txt_t bgC_header2 w5">
                    <bean:define id="ntpUsrInfMdl" name="dataMdl" property="ntp030UsrInfMdl" />
                    <logic:equal name="ntpUsrInfMdl" property="binSid" value="0">
                      <logic:notEqual name="ntpUsrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                        <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                        <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                      </logic:notEqual>
                      <logic:equal name="ntpUsrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                        <div align="center" class="hikokai_photo-m">
                          <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                        </div>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="ntpUsrInfMdl" property="binSid" value="0">
                      <logic:equal name="ntpUsrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                        <div align="center" class="hikokai_photo-m">
                          <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                        </div>
                      </logic:equal>
                      <logic:notEqual name="ntpUsrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                        <img class="wp50" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="ntpUsrInfMdl" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                      </logic:notEqual>
                    </logic:notEqual>
                  </th>
                  <th class="w95 txt_l txt_t border_left_none bgC_header2 cl_fontBody">
                    <div class="fs_12 lh_normal">
                      <bean:write name="dataMdl" property="ntp030NtpDate" />
                      <bean:write name="dataMdl" property="ntp030DspFrHour" /><gsmsg:write key="cmn.hour.input" />
                      <bean:write name="dataMdl" property="ntp030DspFrMinute"/><gsmsg:write key="cmn.minute.input" />
                      ～
                      <bean:write name="dataMdl" property="ntp030DspToHour"/><gsmsg:write key="cmn.hour.input" />
                      <bean:write name="dataMdl" property="ntp030DspToMinute"/><gsmsg:write key="cmn.minute.input" />
                    </div>
                    <div class="fs_12 lh_normal">
                      <bean:define id="mukoUserClass" value=""/>
                      <logic:equal value="1" name="ntpUsrInfMdl" property="usrUkoFlg">
                        <bean:define id="mukoUserClass" value="mukoUser"/>
                      </logic:equal>
                      <span class="<%= mukoUserClass %>">
                        <bean:write name="ntpUsrInfMdl" property="usiSei" />&nbsp;&nbsp;<bean:write name="ntpUsrInfMdl" property="usiMei" />
                      </span>
                    </div>
                    <div class="cursor_p cl_linkDef fs_16 mt5 mb5" onClick="return editNippou('edit', <bean:write name="dataMdl" property="ntp030NtpSid" />, <bean:write name="dataMdl" property="ntp030UsrSid" />);">
                      <bean:write name="dataMdl" property="title" filter="false"/>
                    </div>
                    <div>
                      <logic:notEmpty name="dataMdl" property="ankenName">
                        <a id="<bean:write name="dataMdl" property="ankenSid" />" class="cl_linkDef anken_click mr20">
                          <logic:notEmpty name="dataMdl" property="ankenSid">
                            <logic:notEqual name="dataMdl" property="ankenSid" value="0">
                              <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="ntp.11" />">
                              <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken.png" alt="<gsmsg:write key="ntp.11" />">
                              <bean:write name="dataMdl" property="ankenName" />
                            </logic:notEqual>
                          </logic:notEmpty>
                        </a>
                      </logic:notEmpty>
                      <logic:notEmpty name="dataMdl" property="companyName">
                        <a id="<bean:write name="dataMdl" property="companySid" />" class="cl_linkDef comp_click comp_name_link_<bean:write name="dataMdl" property="ntp030NtpSid" />">
                          <span class="comp_name_<bean:write name="dataMdl" property="ntp030NtpSid" />">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png" alt="<gsmsg:write key="address.adr010.2" />">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.adr010.2" />">
                            <bean:write name="dataMdl" property="companyName" />
                            <logic:notEmpty name="dataMdl" property="companySid">
                              <bean:write name="dataMdl" property="companyBaseName" />
                            </logic:notEmpty>
                          </span>
                        </a>
                      </logic:notEmpty>
                    </div>
                    <div class="fs_12 lh_normal">
                      <logic:notEmpty name="dataMdl" property="ntp030DspKtbunrui">
                        <logic:notEqual name="dataMdl" property="ktbunruiSid" value="-1">
                          <span class="mr20">
                            <gsmsg:write key="ntp.3" />&nbsp;:&nbsp;<bean:write name="dataMdl" property="ntp030DspKtbunrui" />
                          </span>
                        </logic:notEqual>
                      </logic:notEmpty>
                      <logic:notEmpty name="dataMdl" property="ntp030DspKthouhou">
                        <logic:notEqual name="dataMdl" property="kthouhouSid" value="-1">
                          <gsmsg:write key="ntp.121" />&nbsp;:&nbsp;<bean:write name="dataMdl" property="ntp030DspKthouhou" />
                        </logic:notEqual>
                      </logic:notEmpty>
                    </div>
                  </th>
                </tr>
                <tr class="bgC_lightGray">
                  <td class="border_right_none w5">
                  </td>
                  <td class="border_left_none w95">
                    <div class="naiyouArea<bean:write name="dataMdl" property="ntp030NtpSid" />">
                      <span class="dsp_naiyou_<bean:write name="dataMdl" property="ntp030NtpSid" />"><bean:write name="dataMdl" property="ntp030DspValueStr" filter="false"/></span>
                    </div>
                    <div class="dspTmpFileArea_<bean:write name="dataMdl" property="ntp030NtpSid" />">
                      <logic:notEmpty name="dataMdl" property="ntp030FileList">
                        <logic:iterate id="tempMdl" name="dataMdl" property="ntp030FileList">
                          <div class="cl_linkDef">
                            <span class="cursor_p" onclick="return fileLinkClick(<bean:write name="dataMdl" property="ntp030NtpSid" />,<bean:write name="tempMdl" property="binSid"/>)">
                              <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png">
                              <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png">
                              <bean:write name="tempMdl" property="binFileName"/><bean:write name="tempMdl" property="binFileSizeDsp" />
                            </span>
                          </div>
                        </logic:iterate>
                      </logic:notEmpty>
                    </div>
                    <div class="mt10">
                      <span class="js_goodBtnArea_<bean:write name="dataMdl" property="ntp030NtpSid" />">
                        <logic:equal name="dataMdl" property="ntp030GoodFlg" value="0">
                          <button type="button" id="<bean:write name="dataMdl" property="ntp030NtpSid" />" class="baseBtn ntp_goodButton js_goodLink" value="<gsmsg:write key="ntp.22" />!">
                            <gsmsg:write key="ntp.22" />!
                          </button>
                        </logic:equal>
                        <logic:notEqual name="dataMdl" property="ntp030GoodFlg" value="0">
                          <span class="fs_12 fw_b"><gsmsg:write key="ntp.ntp030.1" /></span>
                        </logic:notEqual>
                      </span>
                      <span class="ml5 js_textGood cursor_p" id="<bean:write name="dataMdl" property="ntp030NtpSid" />" data-count="<bean:write name="dataMdl" property="ntp030GoodCnt" />">
                        <img class="btn_classicImg-display" src="../nippou/images/classic/bg_good_2.gif">
                        <img class="btn_originalImg-display" src="../nippou/images/original/icon_good.png">
                        <span class="js_goodCount_<bean:write name="dataMdl" property="ntp030NtpSid" />"><bean:write name="dataMdl" property="ntp030GoodCnt" /></span>
                      </span>
                    </div>
                  </td>
                </tr>
                <tr class="bgC_body">
                  <td class="border_right_none w5">
                  </td>
                  <td class="border_left_none w95">
                    <logic:equal name="dataMdl" property="ankenViewable" value="true">
                      <logic:empty name="dataMdl" property="ntp030CommentList">
                        <div id="ntp030DspComment_<bean:write name="dataMdl" property="ntp030NtpSid" />">
                        </div>
                      </logic:empty>
                      <logic:notEmpty name="dataMdl" property="ntp030CommentList">
                        <div id="ntp030DspComment_<bean:write name="dataMdl" property="ntp030NtpSid" />">
                          <span class="commentDspArea<bean:write name="dataMdl" property="ntp030NtpSid" />">
                            <logic:iterate id="npcMdl" name="dataMdl" property="ntp030CommentList">
                              <bean:define id="usrInfMdl" name="npcMdl" property="ntp030UsrInfMdl"/>
                              <bean:define id="ntpCmtMdl" name="npcMdl" property="ntp030CommentMdl"/>
                              <table class="table-noBorder js_commentArea_<bean:write name="ntpCmtMdl" property="npcSid" />">
                                <tr>
                                  <td class="txt_t">
                                    <logic:equal name="usrInfMdl" property="binSid" value="0">
                                      <logic:notEqual name="usrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                        <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp25" alt="<gsmsg:write key="cmn.photo" />" >
                                        <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp25" alt="<gsmsg:write key="cmn.photo" />" >
                                      </logic:notEqual>
                                      <logic:equal name="usrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                        <div class="hikokai_photo-s txt_c">
                                          <span class="cl_fontWarn">非公</span>
                                        </div>
                                      </logic:equal>
                                    </logic:equal>
                                    <logic:notEqual name="usrInfMdl" property="binSid" value="0">
                                      <logic:equal name="usrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                        <div class="hikokai_photo-s txt_c">
                                          <span class="cl_fontWarn">非公</span>
                                        </div>
                                      </logic:equal>
                                      <logic:notEqual name="usrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                        <img class="wp25" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="usrInfMdl" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                                      </logic:notEqual>
                                    </logic:notEqual>
                                  </td>
                                  <td class="w100">
                                    <div>
                                      <bean:define id="mukoUserClass" value=""/>
                                      <logic:equal value="1" name="usrInfMdl" property="usrUkoFlg">
                                        <bean:define id="mukoUserClass" value="mukoUser"/>
                                      </logic:equal>
                                      <span class="<%= mukoUserClass %> fw_b mr20"><bean:write name="usrInfMdl" property="usiSei"/>&nbsp;<bean:write name="usrInfMdl" property="usiMei"/></span>
                                      <bean:write name="npcMdl" property="ntp030CommentDate" filter="false"/>
                                      <logic:equal name="npcMdl" property="ntp030CommentDelFlg" value="1">
                                        <span class="commentDel cursor_p" id="<bean:write name="ntpCmtMdl" property="npcSid" />">
                                          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                                          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                                        </span>
                                      </logic:equal>
                                    </div>
                                    <div>
                                      <bean:write name="ntpCmtMdl" property="npcComment" filter="false"/>
                                    </div>
                                  </td>
                                </tr>
                              </table>
                            </logic:iterate>
                          </span>
                        </div>
                      </logic:notEmpty>
                    </logic:equal>
                    <div>
                      <logic:equal name="dataMdl" property="ankenViewable" value="true">
                        <table class="table-noBorder w100">
                          <tr>
                            <td class="w5">
                              <logic:equal name="susrInfMdl" property="binSid" value="0">
                                <logic:notEqual name="susrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                  <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp25" alt="<gsmsg:write key="cmn.photo" />" >
                                  <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp25" alt="<gsmsg:write key="cmn.photo" />" >
                                </logic:notEqual>
                                <logic:equal name="susrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                  <div align="center" class="hikokai_photo-s">
                                    <span class="cl_fontWarn">非公</span>
                                  </div>
                                </logic:equal>
                              </logic:equal>
                              <logic:notEqual name="susrInfMdl" property="binSid" value="0">
                                <logic:equal name="susrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                  <div align="center" class="hikokai_photo-s">
                                    <span class="cl_fontWarn">非公</span>
                                  </div>
                                </logic:equal>
                                <logic:notEqual name="susrInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                                  <img class="wp25" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="susrInfMdl" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                                </logic:notEqual>
                              </logic:notEqual>
                            </td>
                            <td class="w90">
                              <div class="textfield verAlignMid w100">
                                <label class="js_ntp_labelArea ntp_labelArea cl_fontWeek" for="field_id<bean:write name="dataMdl" property="ntp030NtpSid" />"><gsmsg:write key="ntp.36" /></label>
                                <textarea name="ntp030Comment_<bean:write name="dataMdl" property="ntp030NtpSid" />" rows="1" class="w100 js_commentTextArea" id="field_id<bean:write name="dataMdl" property="ntp030NtpSid" />"></textarea>
                              </div>
                            </td>
                            <td class="no_w w5">
                              <button type="button" class="baseBtn js_commentBtn" value="<gsmsg:write key="bbs.16" />" id="<bean:write name="dataMdl" property="ntp030NtpSid" />">
                                <gsmsg:write key="bbs.16" />
                              </button>
                            </td>
                          </tr>
                        </table>
                      </logic:equal>
                    </div>
                  </td>
                </tr>
              </table>
              <bean:define id="beforeLabelDate" name="dataMdl" property="ntp030NtpDate" type="java.lang.String" />
              <% beforeLabel= beforeLabelDate; %>
              <% beforePositionLabel = beforeLabelDate.substring(0,8); %>
            </logic:iterate>
            <span id="pageBottomArea"></span>
            <span id="pageBottom"></span>
          </logic:notEmpty>
          </div>
          <div>
            &nbsp;
          </div>


        </div>
        <div class="w30 ml10">
          <table class="table-top w100 mt0">
            <tr>
              <th class="txt_l" colspan="2">
                <div class="fw_b">
                  <gsmsg:write key="cmn.show.group" />

                </div>
                <div class="mt5">
                  <html:select property="ntp010DspGpSid" styleClass="w80" onchange="change030GroupCombo();">
                    <logic:notEmpty name="ntp030Form" property="ntp010GpLabelList" scope="request">
                      <logic:iterate id="gpBean" name="ntp030Form" property="ntp010GpLabelList" scope="request">
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
                  <logic:notEqual name="ntp030Form" property="ntp010CrangeKbn" value="1">
                    <button type="button" class="iconBtn-border ml5" onclick="openGroupWindow(this.form.ntp010DspGpSid, 'ntp010DspGpSid', '1');" id="ntp010GroupBtn">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" />
                      <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" />
                    </button>
                  </logic:notEqual>
                </div>
                  <logic:greaterThan name="ntp030Form" property="ntp030SelectUsrSid" value="0">
                    <button type="button" class="baseBtn js_selGroupBtn ml0" value="<gsmsg:write key="cmn.import" />">
                      <gsmsg:write key="ntp.2" />
                    </button>
                  </logic:greaterThan>
              </th>
            </tr>
            <logic:notEmpty name="ntp030Form" property="ntp030UsrLabelList" scope="request">
              <logic:iterate id="usrBean" name="ntp030Form" property="ntp030UsrLabelList" scope="request">
                <bean:define id="beanUsrSid" name="usrBean" property="value" type="java.lang.String" />
                <bean:define id="usrLvInfMdl" name="usrBean" property="usrInfMdl" />
                <% String bgClassName = ""; %>
                <logic:equal name="ntp030Form" property="ntp030SelectUsrSid" value="<%= beanUsrSid %>">
                  <% bgClassName = "bgC_select"; %>
                </logic:equal>
                <logic:notEqual name="ntp030Form" property="ntp030SelectUsrSid" value="<%= beanUsrSid %>">
                  <% bgClassName = "js_listHover bgC_body"; %>
                </logic:notEqual>
                <tr class="<%= bgClassName %> cursor_p">
                  <td class="border_right_none">
                    <a href="#!" onclick="return openUserInfoWindow(<bean:write name="usrBean" property="value" />);">
                      <logic:equal name="usrLvInfMdl" property="binSid" value="0">
                        <logic:notEqual name="usrLvInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                          <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp25" alt="<gsmsg:write key="cmn.photo" />" >
                          <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp25" alt="<gsmsg:write key="cmn.photo" />" >
                        </logic:notEqual>
                        <logic:equal name="usrLvInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                          <div align="center" class="hikokai_photo-s">
                            <span class="cl_fontWarn"><gsmsg:write key="cmn.private.photo" /></span>
                          </div>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="usrLvInfMdl" property="binSid" value="0">
                        <logic:equal name="usrLvInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                          <div align="center" class="hikokai_photo-s">
                            <span class="cl_fontWarn"><gsmsg:write key="cmn.private.photo" /></span>
                          </div>
                        </logic:equal>
                        <logic:notEqual name="usrLvInfMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                          <img class="wp25" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="usrLvInfMdl" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                        </logic:notEqual>
                      </logic:notEqual>
                    </a>
                  </td>
                  <td id="<bean:write name="usrBean" property="value" />" class="w100 js_ntpUsrLink border_left_none">
                    <bean:define id="mukoUserClass" value=""/>
                    <logic:equal value="1" name="usrLvInfMdl" property="usrUkoFlg"><bean:define id="mukoUserClass" value="mukoUser"/></logic:equal>
                    <span class="cl_linkDef <%= mukoUserClass %>"><bean:write name="usrBean" property="label" /></span>
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>


</html:form>
<iframe type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></iframe>

<script type="text/javascript">
$(function() {
  $('#ntpTooltipsDaily * a').tooltip();
});
</script>


<div id="goodUsrInfArea" class="goodAddUsrArea bgC_body borC_light">
  <table class="w100 hp250">
    <tr>
      <td class="w100 txt_r bgC_header2 hp30">
        <button id="goodAddUsrClose" type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.close" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </td>
    </tr>
    <tr>
      <td class="w100 hp220">
        <div id="goodUsrInfArea2" class="ofy_s hp220">
        </div>
      </td>
    </tr>
  </table>
</div>
<div id="dialogCommentDel" title="<gsmsg:write key="cmn.delete" /><gsmsg:write key="cmn.check" />" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.21" />
      </td>
    </tr>
  </table>
</div>

<div id="commentError" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.23" />
      </td>
    </tr>
  </table>
</div>

<div id="commentLengthError" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.24" />
      </td>
    </tr>
  </table>
</div>

<div id="goodError" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.25" />
      </td>
    </tr>
  </table>
</div>

<div id="goodDialog" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.26" />
      </td>
    </tr>
  </table>
</div>

<div id="goodDialogComp" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.27" />
      </td>
    </tr>
  </table>
</div>

<div id="goodZero" class="w100 display_n">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.28" />
      </td>
    </tr>
  </table>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>