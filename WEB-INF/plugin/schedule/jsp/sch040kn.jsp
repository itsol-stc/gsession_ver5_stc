<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-bean.tld" prefix="bean2" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch040kn.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch040kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/schedule/sch040kn">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="helpPrm" value="<bean:write name="sch040knForm" property="sch010SelectUsrKbn" />">
<html:hidden property="cmd" />
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
<html:hidden property="sch020SelectUsrSid" />
<html:hidden property="sch030FromHour" />
<html:hidden property="sch040Bgcolor" />
<html:hidden property="sch040Title" />
<html:hidden property="sch040Value" />
<html:hidden property="sch040Biko" />
<html:hidden property="sch040Public" />
<html:hidden property="sch040FrYear" />
<html:hidden property="sch040FrMonth" />
<html:hidden property="sch040FrDay" />
<html:hidden property="sch040FrHour" />
<html:hidden property="sch040FrMin" />
<html:hidden property="sch040ToYear" />
<html:hidden property="sch040ToMonth" />
<html:hidden property="sch040ToDay" />
<html:hidden property="sch040ToHour" />
<html:hidden property="sch040ToMin" />
<html:hidden property="sch040GroupSid" />
<html:hidden property="sch040Edit" />
<html:hidden property="sch040TimeKbn" />

<html:hidden property="schWeekDate" />
<html:hidden property="schDailyDate" />
<html:hidden property="sch100PageNum" />
<html:hidden property="sch100Slt_page1" />
<html:hidden property="sch100Slt_page2" />
<html:hidden property="sch100OrderKey1" />
<html:hidden property="sch100SortKey1" />
<html:hidden property="sch100OrderKey2" />
<html:hidden property="sch100SortKey2" />
<html:hidden property="sch100SelectUsrSid" />
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
<html:hidden property="sch010searchWord" />
<input type="hidden" name="sch040knBinSid" />

<logic:notEmpty name="sch040knForm" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch040knForm" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040knForm" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch040knForm" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch040knForm" property="sv_users" scope="request">
<logic:iterate id="ulBean" name="sch040knForm" property="sv_users" scope="request">
<input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch040knForm" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch040knForm" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040knForm" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch040knForm" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch040knForm" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch040knForm" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch040knForm" property="sch100SelectScdSid" scope="request">
  <logic:iterate id="selectScdSid" name="sch040knForm" property="sch100SelectScdSid" scope="request">
    <input type="hidden" name="sch100SelectScdSid" value="<bean:write name="selectScdSid"/>">
  </logic:iterate>
</logic:notEmpty>


<!-- BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
      <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
    </li>
    <li>
      <gsmsg:write key="schedule.108" />
    </li>
    <li class="pageTitle_subFont">
     <gsmsg:write key="cmn.check" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040kn_back', 'edit');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <bean:define id="tdColor" value="" />
  <% String[] tdColors = new String[] {"bgC_tableCell", "bgC_other1"}; %>
  <logic:notEqual name="sch040knForm" property="sch010SelectUsrKbn" value="0">
  <% tdColor = tdColors[1]; %>
  </logic:notEqual>
  <logic:equal name="sch040knForm" property="sch010SelectUsrKbn" value="0">
  <% tdColor = tdColors[0]; %>
  </logic:equal>
  <logic:equal name="sch040knForm" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_USER) %>">
    <logic:notEqual name="sch040knForm" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_NORMAL) %>">
      <table class="table-left w100">
        <tr>
          <th class="w20 txt_l">
            <gsmsg:write key="schedule.sch040.3" />
          </th>
          <td class="w80 <%= tdColor %>">
            <logic:notEqual name="sch040knForm" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
              <div class="pt5">
                <logic:equal name="sch040knForm" property="sch040AttendKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_KBN_NO) %>" >
                  <gsmsg:write key="cmn.notcheck" />
                </logic:equal>
                <logic:equal name="sch040knForm" property="sch040AttendKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_KBN_YES) %>" >
                  <gsmsg:write key="cmn.check.2" />
                </logic:equal>
              </div>
            </logic:notEqual>
            <logic:equal name="sch040knForm" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
              <div class="pt5">
                <logic:equal name="sch040knForm" property="sch040AttendAnsKbn" value="0" >
                  <gsmsg:write key="schedule.sch040.4" />
                </logic:equal>
                <logic:equal name="sch040knForm" property="sch040AttendAnsKbn" value="1" >
                  <gsmsg:write key="schedule.sch040.5" />
                </logic:equal>
                <logic:equal name="sch040knForm" property="sch040AttendAnsKbn" value="2" >
                  <gsmsg:write key="schedule.sch040.6" />
                </logic:equal>
              </div>
            </logic:equal>
            <div>
              <table class="table-top w100">
                <tr>
                  <th class="w20">
                    <gsmsg:write key="anp.date.ans" />
                  </th>
                  <th class="w15">
                    <gsmsg:write key="cmn.name" />
                  </th>
                  <th class="w15">
                    <gsmsg:write key="schedule.sch040.8" />
                  </th>
                  <th class="w50">
                    <gsmsg:write key="cmn.comment" />
                  </th>
                </tr>
                <logic:notEmpty name="sch040knForm" property="sch040AttendAnsList">
                  <logic:iterate id="attendMdl" name="sch040knForm" property="sch040AttendAnsList" indexId="idx">
                    <tr class=bg_w>
                      <td class="txt_c">
                        <logic:equal name="attendMdl" property="attendAnsKbn" value="0">
                          <gsmsg:write key="schedule.sch040.9" />
                        </logic:equal>
                        <logic:notEqual name="attendMdl" property="attendAnsKbn" value="0">
                          <bean:write name="attendMdl" property="attendAnsDate" />
                        </logic:notEqual>
                      </td>
                      <td>
                        <logic:equal name="attendMdl" property="attendAnsUsrUkoFlg" value="1">
                          <span class="mukoUserOption"><bean:write name="attendMdl" property="attendAnsUsrName" /></span>
                        </logic:equal>
                        <logic:notEqual name="attendMdl" property="attendAnsUsrUkoFlg" value="1">
                          <bean:write name="attendMdl" property="attendAnsUsrName" />
                        </logic:notEqual>
                      </td>
                      <td>
                        <logic:equal name="attendMdl" property="attendAnsKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_ANS_NONE) %>">
                          <gsmsg:write key="schedule.sch040.4" />
                        </logic:equal>
                        <logic:equal name="attendMdl" property="attendAnsKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_ANS_YES) %>">
                        <span class="cl_fontSafe"><gsmsg:write key="schedule.sch040.5" /></span>
                        </logic:equal>
                        <logic:equal name="attendMdl" property="attendAnsKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_ANS_NO) %>">
                        <span class="cl_fontWarn"><gsmsg:write key="schedule.sch040.6" /></span>
                        </logic:equal>
                      </td>
                      <td>
                        <bean:write name="attendMdl" property="attendAnsComment" />
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
              </table>
              <logic:equal name="sch040knForm" property="sch040AttendLinkFlg" value="1">
                <div>
                  <a href="#" id="all_disp"><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.display.ok" /></a>
                </div>
              </logic:equal>
            </div>
          </td>
        </tr>
      </table>
    </logic:notEqual>
  </logic:equal>
  <table class="table-left w100">
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.4" />
      </th>
      <td class="w80 <%= tdColor %>">
      <logic:notEqual name="sch040knForm" property="sch010SelectUsrKbn" value="0">
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
      <img class="header_pluginImg" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
      </logic:notEqual>
        <logic:equal name="sch040knForm" property="sch040UsrUkoFlg" value="1">
          <span class="mukoUser"><bean:write name="sch040knForm" property="sch040UsrName" /></span>
        </logic:equal>
        <logic:notEqual name="sch040knForm" property="sch040UsrUkoFlg" value="1">
          <bean:write name="sch040knForm" property="sch040UsrName" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.16" />
      </th>
      <td class="w80 <%= tdColor %>">
        <bean:write name="sch040knForm" property="sch040knFromDate" />
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.27" />
      </th>
      <td class="w80 <%= tdColor %>">
        <bean:write name="sch040knForm" property="sch040knToDate" />
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.title" />
      </th>
      <td class="w80 <%= tdColor %>">
        <bean:write name="sch040knForm" property="sch040Title" />
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.10" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="1">
          <span class="bgc_fontSchTitleBlue"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="2">
          <span class="bgc_fontSchTitleRed"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="3">
          <span class="bgc_fontSchTitleGreen"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="4">
          <span class="bgc_fontSchTitleYellow"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="5">
          <span class="bgc_fontSchTitleBlack"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="6">
          <span class="bgc_fontSchTitleNavy"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="7">
          <span class="bgc_fontSchTitleWine"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="8">
          <span class="bgc_fontSchTitleCien"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="9">
          <span class="bgc_fontSchTitleGray"><span class="pl15"></span></span>
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Bgcolor" value="10">
          <span class="bgc_fontSchTitleMarine"><span class="pl15"></span></span>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.content" />
      </th>
      <td class="w80 <%= tdColor %>">
        <bean2:writeText name="sch040knForm" property="sch040Value" crlf="false" />
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:notEmpty name="sch040knForm" property="sch040knFileList">
          <logic:iterate id="fileMdl" name="sch040knForm" property="sch040knFileList">
            <div>
              <a href="#!" onClick="return fileLinkClick(<bean:write name="fileMdl" property="binSid"/>);"><bean:write name="fileMdl" property="binFileName"/></a>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w80 <%= tdColor %>">
        <bean2:writeText name="sch040knForm" property="sch040Biko" crlf="false" />
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:equal name="sch040knForm" property="sch040Edit" value="0">
          <gsmsg:write key="cmn.notset" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Edit" value="1">
          <gsmsg:write key="schedule.sch040kn.3" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Edit" value="2">
          <gsmsg:write key="cmn.affiliation.group" />
        </logic:equal>
      </td>
    </tr>
    <logic:equal name="sch040knForm" property="sch040ReminderEditMode.ableSelReminder" value="true">
    <logic:equal name="sch040knForm" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_GROUP) %>">
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.reminder" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:equal name="sch040knForm" property="sch040TargetGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.REMINDER_USE_YES) %>">
          <gsmsg:write key="cmn.notify" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040TargetGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.REMINDER_USE_NO) %>">
          <gsmsg:write key="cmn.dont.notify" />
        </logic:equal>
      </td>
    </tr>
    </logic:equal>
    </logic:equal>
    <logic:equal name="sch040knForm" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_USER) %>">
    <logic:equal name="sch040knForm" property="sch040Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_USRGRP) %>">
    <!-- 公開 -->
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.21" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:equal name="sch040knForm" property="sch040Public" value="0">
          <gsmsg:write key="cmn.public" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Public" value="1">
          <gsmsg:write key="cmn.private" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Public" value="2">
          <gsmsg:write key="schedule.5" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Public" value="3">
          <gsmsg:write key="schedule.28" />
        </logic:equal>
        <logic:equal name="sch040knForm" property="sch040Public" value="4">
          <gsmsg:write key="schedule.37" />
        </logic:equal>
      </td>
    </tr>
    <!-- 公開対象 -->
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.36" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:notEmpty name="sch040knForm" property="sch040knDspTarget">
          <logic:iterate id="usrMdl" name="sch040knForm" property="sch040knDspTarget">
            <bean:write name="usrMdl" property="label" />
            <br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    
    </logic:equal>
    </logic:equal>
    <!-- 出欠確認 -->
    <logic:equal name="sch040knForm" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_USER) %>">
      <logic:equal name="sch040knForm" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_NORMAL) %>">
         <tr>
           <th class="w20 txt_l">
             <gsmsg:write key="schedule.sch040.3" />
           </th>
           <td class="w80 <%= tdColor %>">
             <gsmsg:write key="cmn.notcheck" />
           </td>
         </tr>
      </logic:equal>
    </logic:equal>
    <!-- 同時登録 -->
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.117" />
      </th>
      <td class="w80 <%= tdColor %>">
      <logic:notEmpty name="sch040knForm" property="sch040AddedUsrLabel">
        <logic:iterate id="aurBean" name="sch040knForm" property="sch040AddedUsrLabel" scope="request">
          <logic:equal name="aurBean" property="usrUkoFlg" value="1">
            <span class="mukoUserOption">
              <bean:write name="aurBean" property="usiSei" scope="page"/> <bean:write name="aurBean" property="usiMei" scope="page"/>
            </span>
          </logic:equal>
          <logic:notEqual name="aurBean" property="usrUkoFlg" value="1">
            <span>
              <bean:write name="aurBean" property="usiSei" scope="page"/> <bean:write name="aurBean" property="usiMei" scope="page"/>
            </span>
          </logic:notEqual>
          <br>
        </logic:iterate>
      </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="w80 <%= tdColor %>">
        <div class="display_inline">
          <logic:notEqual name="sch040knForm" property="sch040AddUsrJkbn" value="9">
            <logic:equal name="sch040knForm" property="sch040AddUsrUkoFlg" value="1">
              <span class="mukoUserOption"><bean:write name="sch040knForm" property="sch040AddUsrName" /></span>
            </logic:equal>
            <logic:notEqual name="sch040knForm" property="sch040AddUsrUkoFlg" value="1">
              <span><bean:write name="sch040knForm" property="sch040AddUsrName" /></span>
            </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="sch040knForm" property="sch040AddUsrJkbn" value="9">
            <del>
              <bean:write name="sch040knForm" property="sch040AddUsrName" />
            </del>
          </logic:equal>
        </div>
        <div class="ml20 display_inline">
          <bean:write name="sch040knForm" property="sch040AddDate" filter="false" />
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040kn_back', 'edit');">
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