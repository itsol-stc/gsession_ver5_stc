<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-bean.tld" prefix="bean2" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>



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
<script src="../common/js/selectionSearch.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch210.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body onload="hideTooltip();">


<html:form action="/schedule/sch210">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="helpPrm" value="<bean:write name="sch210Form" property="sch010SelectUsrKbn" />">
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
<input type="hidden" name="sch040knBinSid" />

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

<logic:notEmpty name="sch210Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch210Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch210Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch210Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch210Form" property="sv_users" scope="request">
<logic:iterate id="ulBean" name="sch210Form" property="sv_users" scope="request">
<input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch210Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch210Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch210Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch210Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch210Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch210Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
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
      <gsmsg:write key="cmn.check" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper">

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <bean:define id="tdColor" value="" />

  <logic:notEqual name="sch210Form" property="sch010SelectUsrKbn" value="0">
    <bean:define id="tdColor" value="bgC_other1" />
  </logic:notEqual>
  <logic:equal name="sch210Form" property="sch010SelectUsrKbn" value="0">
    <bean:define id="tdColor" value="bgC_tableCell" />
  </logic:equal>

  <table class="table-left w100">
    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.4" />
      </th>
      <td class="w70 <%= tdColor %> ">
        <logic:notEqual name="sch210Form" property="sch010SelectUsrKbn" value="0">
          <span class="flo_l">
            <img class="classic-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
            <img class="original-display" src="../common/images/original/icon_group.png"  alt="<gsmsg:write key="cmn.group" />">
          </span>
        </logic:notEqual>
        <bean:write name="sch210Form" property="sch040UsrName" />
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.16" />
      </th>
      <td class="w70 <%= tdColor %>">
        <bean:write name="sch210Form" property="sch040knFromDate" />
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.27" />
      </th>
      <td class="w70 <%= tdColor %>">
        <bean:write name="sch210Form" property="sch040knToDate" />
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.title" />
      </th>
      <td class="w70 <%= tdColor %>">
        <bean:write name="sch210Form" property="sch040Title" />
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.10" />
      </th>
      <td class="w70 <%= tdColor %>">
          <bean:define id="clName" value="Blue" />
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="1">
            <bean:define id="clName" value="Blue" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="2">
            <bean:define id="clName" value="Red" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="3">
            <bean:define id="clName" value="Green" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="4">
            <bean:define id="clName" value="Yellow" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="5">
            <bean:define id="clName" value="Black" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="6">
            <bean:define id="clName" value="Navy" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="7">
            <bean:define id="clName" value="Wine" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="8">
            <bean:define id="clName" value="Cien" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="9">
            <bean:define id="clName" value="Gray" />
          </logic:equal>
          <logic:equal name="sch210Form" property="sch040Bgcolor" value="10">
            <bean:define id="clName" value="Marine" />
          </logic:equal>
          <span class="bgc_fontSchTitle<%=clName%>">
            <span class="pl15"></span>
          </span>
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.content" />
      </th>
      <td class="w70 <%= tdColor %>">
        <bean2:writeText name="sch210Form" property="sch040Value" crlf="false" />
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w70 <%= tdColor %>">
        <bean2:writeText name="sch210Form" property="sch040Biko" crlf="false" />
      </td>
    </tr>

    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w70 <%= tdColor %>">
        <logic:notEmpty name="sch210Form" property="sch040knFileList">
          <logic:iterate id="fileMdl" name="sch210Form" property="sch040knFileList">
            <div>
              <a href="#!" onClick="return fileLinkClick(<bean:write name="fileMdl" property="binSid"/>);"><bean:write name="fileMdl" property="binFileName"/></a>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>

    <!-- リマインダー通知 -->
    <logic:equal name="sch210Form" property="sch040ReminderEditMode.ableSelReminder" value="true">
      <tr>
        <th class="w30 txt_l">
          <gsmsg:write key="cmn.reminder" />
        </th>
        <td class="w70 <%= tdColor %>">
          <logic:notEqual name="sch210Form" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_GROUP) %>">
            <div>
              <bean:write name="sch210Form" property="sch210ReminderTimeText" />
            </div>
          </logic:notEqual>

          <logic:equal name="sch210Form" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_GROUP) %>">
            <div>
              <logic:equal name="sch210Form" property="sch040TargetGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.REMINDER_USE_YES) %>">
                <gsmsg:write key="cmn.notify" />
              </logic:equal>
              <logic:equal name="sch210Form" property="sch040TargetGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.REMINDER_USE_NO) %>">
                <gsmsg:write key="cmn.dont.notify" />
              </logic:equal>
            </div>
          </logic:equal>
        </td>
      </tr>
    </logic:equal>


    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.21" />
      </th>
      <td class="w70 <%= tdColor %>">
        <logic:equal name="sch210Form" property="sch040Public" value="0">
        <gsmsg:write key="cmn.public" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Public" value="1">
        <gsmsg:write key="cmn.private" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Public" value="2">
        <gsmsg:write key="schedule.5" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Public" value="3">
        <gsmsg:write key="schedule.28" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Public" value="4">
        <gsmsg:write key="reserve.187" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Public" value="5">
        <gsmsg:write key="schedule.38" />
        </logic:equal>
      </td>
    </tr>
  <logic:equal name="sch210Form" property="sch040Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.DSP_USRGRP) %>">
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.36" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:notEmpty name="sch210Form" property="sch040knDspTarget">
          <logic:iterate id="usrMdl" name="sch210Form" property="sch040knDspTarget">
            <logic:equal value="1" name="usrMdl" property="usrUkoFlg">
              <span class="mukoUser fs_14"><bean:write name="usrMdl" property="label" /></span>
            </logic:equal>
            <logic:notEqual value="1" name="usrMdl" property="usrUkoFlg">
              <span class="fs_14"><bean:write name="usrMdl" property="label" /></span>
            </logic:notEqual>
            <br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </logic:equal>

    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.sch040kn.2" />
      </th>
      <td class="w70 <%= tdColor %>">
        <logic:equal name="sch210Form" property="sch040Edit" value="0">
        <gsmsg:write key="cmn.notset" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Edit" value="1">
        <gsmsg:write key="schedule.sch040kn.3" />
        </logic:equal>
        <logic:equal name="sch210Form" property="sch040Edit" value="2">
        <gsmsg:write key="cmn.affiliation.group" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="schedule.117" />
      </th>
      <td class="w70 <%= tdColor %>">
    <logic:notEmpty name="sch210Form" property="sch040AddedUsrLabel">
    <logic:iterate id="aurBean" name="sch210Form" property="sch040AddedUsrLabel" scope="request">
      <logic:equal name="aurBean" property="usrUkoFlg" value="1">
        <span class="mukoUserOption">
          <bean:write name="aurBean" property="usiSei" scope="page"/>　<bean:write name="aurBean" property="usiMei" scope="page"/>
        </span>
      </logic:equal>
      <logic:notEqual name="aurBean" property="usrUkoFlg" value="1">
        <bean:write name="aurBean" property="usiSei" scope="page"/>　<bean:write name="aurBean" property="usiMei" scope="page"/>
      </logic:notEqual>
    <br>
    </logic:iterate>
    </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="w70 <%= tdColor %>">
        <logic:notEqual name="sch210Form" property="sch040AddUsrJkbn" value="9">
        <bean:write name="sch210Form" property="sch040AddUsrName" />
        </logic:notEqual>
        <logic:equal name="sch210Form" property="sch040AddUsrJkbn" value="9">
        <del>
        <bean:write name="sch210Form" property="sch040AddUsrName" />
        </del>
        </logic:equal>
        <br><bean:write name="sch210Form" property="sch040AddDate"/></span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block mt10 mb20 flo_r">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>

</html:form>
<span id="tooltip_search" class="tooltip_search"></span>

</body>
</html:html>