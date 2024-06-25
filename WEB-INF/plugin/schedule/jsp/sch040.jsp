<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<% String maxLengthContent = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_VALUE); %>
<% String maxLengthBiko = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_BIKO); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
  <logic:equal name="sch040Form" property="cmd" value="add">
    <title>GROUPSESSION <gsmsg:write key="schedule.3" /></title>
  </logic:equal>
  <logic:equal name="sch040Form" property="cmd" value="edit">
    <title>GROUPSESSION <gsmsg:write key="schedule.9" /></title>
  </logic:equal>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <gsjsmsg:js filename="gsjsmsg.js"/>
  <!-- Clock script -->
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <!--  日本語化 -->
  <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
  <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
  <script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <%-- <script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script> --%>
  <script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/selectionSearchText.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/textarea_autoresize.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../schedule/js/sch040.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/reservepopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script type="text/javascript">
  <!--
    var msglist_sch040 = (function() {
      //使用するメッセージキーの配列を作成
      var ret = new Array();
      ret['schedule.js.sch040.5'] = '<gsmsg:write key="schedule.js.sch040.5"/>';
      ret['cmn.ok'] = '<gsmsg:write key="cmn.ok"/>';
      ret['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
      return ret;
    })();
    -->
  </script>
  <% String selectionEvent = ""; %>
  <% boolean selectionFlg = false; %>

  <% String closeScript = "calWindowClose();windowClose();"; %>
  <logic:equal name="sch040Form" property="addressPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
    <script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
    <% closeScript += "companyWindowClose();"; %>
  </logic:equal>

  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>_1' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<logic:notEmpty name="sch040Form" property="sch040SelectUsrLabel" scope="request">
  <logic:equal name="sch040Form" property="cmd" value="edit">
    <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
      <body onload="changeEnablePublic();setToDay();setBetweenFromToDayCount();setTimeStatus();showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');showLengthId($('#inputstr2')[0], <%= maxLengthBiko %>, 'inputlength2');<%= selectionEvent %>" onunload="setDisabled();<%= closeScript %>">
    </logic:notEqual>
    <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
      <body onload="changeEnablePublic();" onunload="setDisabled();<%= closeScript %>">
    </logic:equal>
  </logic:equal>
  <logic:equal name="sch040Form" property="cmd" value="add">
    <body onload="changeEnablePublic();setToDay();setBetweenFromToDayCount();setTimeStatus();showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');showLengthId($('#inputstr2')[0], <%= maxLengthBiko %>, 'inputlength2');<%= selectionEvent %>" onunload="<%= closeScript %>">
  </logic:equal>
</logic:notEmpty>
<logic:empty name="sch040Form" property="sch040SelectUsrLabel" scope="request">
  <body onload="changeEnablePublic();setToDay();setBetweenFromToDayCount();setTimeStatus();showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');showLengthId($('#inputstr2')[0], <%= maxLengthBiko %>, 'inputlength2');<%= selectionEvent %>" onunload="<%= closeScript %>">
</logic:empty>

<html:form action="/schedule/sch040">

<logic:equal name="sch040Form" property="cmd" value="add">
  <input type="hidden" name="CMD" value="040_ok">
</logic:equal>
<logic:equal name="sch040Form" property="cmd" value="edit">
  <input type="hidden" name="CMD" value="040_ok">
</logic:equal>

<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="listMod" />
<html:hidden property="sch040InitFlg" />
<html:hidden property="sch040CopyFlg" />
<html:hidden property="sch040ScrollFlg" />
<html:hidden property="sch040CrangeKbn" />
<html:hidden property="sch040EditDspMode" />
<html:hidden property="sch040EditMailSendKbn" />
<html:hidden property="sch010DspDate" />
<html:hidden property="schIkkatsuViewMode" />
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
<html:hidden property="schWeekDate" />
<html:hidden property="schDailyDate" />
<html:hidden property="sch100SelectUsrSid" />
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

<logic:notEmpty name="sch040Form" property="sch100SelectScdSid" scope="request">
  <logic:iterate id="selectScdSid" name="sch040Form" property="sch100SelectScdSid" scope="request">
    <input type="hidden" name="sch100SelectScdSid" value="<bean:write name="selectScdSid"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sch041InitFlg"/>
<html:hidden property="sch041ExtSid" />
<html:hidden property="sch041ExtKbn" />
<html:hidden property="sch041Week" />
<html:hidden property="sch041Day" />
<html:hidden property="sch041DayOfYearly" />
<html:hidden property="sch041MonthOfYearly" />
<html:hidden property="sch041DayOfMonth" />
<html:hidden property="sch041ConfKbn" />
<html:hidden property="sch041WeekOrDay" />
<html:hidden property="sch041TranKbn" />
<html:hidden property="sch041FrYear" />
<html:hidden property="sch041FrMonth" />
<html:hidden property="sch041FrDay" />
<html:hidden property="sch041ToYear" />
<html:hidden property="sch041ToMonth" />
<html:hidden property="sch041ToDay" />
<html:hidden property="sch041FrHour" />
<html:hidden property="sch041FrMin" />
<html:hidden property="sch041ToHour" />
<html:hidden property="sch041ToMin" />
<html:hidden property="sch041FrDate"/>
<html:hidden property="sch041ToDate"/>
<html:hidden property="sch041FrTime"/>
<html:hidden property="sch041ToTime"/>
<html:hidden property="sch041Bgcolor" />
<html:hidden property="sch041Title" />
<html:hidden property="sch041Value" />
<html:hidden property="sch041Biko" />
<html:hidden property="sch041Public" />
<html:hidden property="sch041Edit" />
<html:hidden property="sch041BatchRef" />
<html:hidden property="sch041GroupSid" />
<html:hidden property="sch041ReserveGroupSid" />
<html:hidden property="sch041TimeKbn" />
<html:hidden property="sch041ReminderTime" />
<html:hidden property="sch041TargetGroup" />
<html:hidden property="sch040AmFrHour"/>
<html:hidden property="sch040AmFrMin"/>
<html:hidden property="sch040AmToHour"/>
<html:hidden property="sch040AmToMin"/>
<html:hidden property="sch040PmFrHour"/>
<html:hidden property="sch040PmFrMin"/>
<html:hidden property="sch040PmToHour"/>
<html:hidden property="sch040PmToMin"/>
<html:hidden property="sch040AllDayFrHour"/>
<html:hidden property="sch040AllDayFrMin"/>
<html:hidden property="sch040AllDayToHour"/>
<html:hidden property="sch040AllDayToMin"/>
<html:hidden property="sch040FrYear" />
<html:hidden property="sch040FrMonth" />
<html:hidden property="sch040FrDay" />
<html:hidden property="sch040ToYear" />
<html:hidden property="sch040ToMonth" />
<html:hidden property="sch040ToDay" />
<html:hidden property="sch040FrHour" />
<html:hidden property="sch040FrMin" />
<html:hidden property="sch040ToHour" />
<html:hidden property="sch040ToMin" />
<html:hidden property="sch040BinSid" />
<html:hidden property="hourDivision"/>
<html:hidden property="schIkkatsuFlg"/>

<input type="hidden" name="sch040delCompanyId" value="">
<input type="hidden" name="sch040delCompanyBaseId" value="">
<html:hidden property="sch041contact" />
<div id="sch040CompanyIdArea">
  <logic:notEmpty name="sch040Form" property="sch040CompanySid">
    <logic:iterate id="coId" name="sch040Form" property="sch040CompanySid">
      <input type="hidden" name="sch040CompanySid" value="<bean:write name="coId"/>">
    </logic:iterate>
  </logic:notEmpty>
</div>
<div id="sch040CompanyBaseIdArea">
  <logic:notEmpty name="sch040Form" property="sch040CompanyBaseSid">
    <logic:iterate id="coId" name="sch040Form" property="sch040CompanyBaseSid">
      <input type="hidden" name="sch040CompanyBaseSid" value="<bean:write name="coId"/>">
    </logic:iterate>
  </logic:notEmpty>
</div>
<div id="sch040AddressIdArea">
  <logic:notEmpty name="sch040Form" property="sch040AddressId">
    <logic:iterate id="addressId" name="sch040Form" property="sch040AddressId">
      <input type="hidden" name="sch040AddressId" value="<bean:write name="addressId"/>">
    </logic:iterate>
  </logic:notEmpty>
</div>
<logic:notEmpty name="sch040Form" property="sch041CompanySid">
  <logic:iterate id="coId" name="sch040Form" property="sch041CompanySid">
    <input type="hidden" name="sch041CompanySid" value="<bean:write name="coId"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch041CompanyBaseSid">
  <logic:iterate id="coId" name="sch040Form" property="sch041CompanyBaseSid">
    <input type="hidden" name="sch041CompanyBaseSid" value="<bean:write name="coId"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch041AddressId">
  <logic:iterate id="addressId" name="sch040Form" property="sch041AddressId">
    <input type="hidden" name="sch041AddressId" value="<bean:write name="addressId"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="reservePluginKbn" />
<html:hidden property="addressPluginKbn" />
<logic:notEmpty name="sch040Form" property="sch041Dweek" scope="request">
  <logic:iterate id="selectWeek" name="sch040Form" property="sch041Dweek" scope="request">
    <input type="hidden" name="sch041Dweek" value="<bean:write name="selectWeek" />">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch040Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch040Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp"/>
<logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="1">
    <input type="hidden" name="helpPrm" value="<bean:write name="sch040Form" property="sch010SelectUsrKbn" /><bean:write name="sch040Form" property="cmd" />">
</logic:equal>
<logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="0">
    <logic:equal name="sch040Form" property="sch040EditDspMode" value="0">
        <input type="hidden" name="helpPrm" value="<bean:write name="sch040Form" property="sch010SelectUsrKbn" /><bean:write name="sch040Form" property="cmd" />">
    </logic:equal>
    <logic:equal name="sch040Form" property="sch040EditDspMode" value="1">
        <input type="hidden" name="helpPrm" value="2<bean:write name="sch040Form" property="cmd" />">
    </logic:equal>
    <logic:equal name="sch040Form" property="sch040EditDspMode" value="2">
        <input type="hidden" name="helpPrm" value="3<bean:write name="sch040Form" property="cmd" />">
    </logic:equal>
</logic:equal>
<input type="hidden" name="itizi_coment" value="">
<logic:notEmpty name="sch040Form" property="sch041SvUsers" scope="request">
  <logic:iterate id="eulBean" name="sch040Form" property="sch041SvUsers" scope="request">
    <input type="hidden" name="sch041SvUsers" value="<bean:write name="eulBean" />">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch041DisplayTarget" scope="request">
  <logic:iterate id="dtuBean" name="sch040Form" property="sch041DisplayTarget" scope="request">
    <input type="hidden" name="sch041DisplayTarget" value="<bean:write name="dtuBean" />">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch041SvReserve" scope="request">
  <logic:iterate id="exresBean" name="sch040Form" property="sch041SvReserve" scope="request">
    <input type="hidden" name="sch041SvReserve" value="<bean:write name="exresBean" />">
  </logic:iterate>
</logic:notEmpty>
<logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="1">
  <html:hidden property="sch040GroupSid" />
</logic:equal>
<logic:notEmpty name="sch040Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch040Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch040Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch040Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch040Form" property="schIkkatuTorokuKey" scope="request">
  <logic:iterate id="ikkatsuKey" name="sch040Form" property="schIkkatuTorokuKey" scope="request">
    <input type="hidden" name="schIkkatuTorokuKey" value="<bean:write name="ikkatsuKey"/>">
  </logic:iterate>
</logic:notEmpty>

<bean:define id="tdColor" value="" />
<% String[] tdColors = new String[] {"bgC_tableCell", "bgC_other1"}; %>
<logic:notEqual name="sch040Form" property="sch010SelectUsrKbn" value="0">
  <% tdColor = tdColors[1]; %>
</logic:notEqual>
<logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="0">
  <% tdColor = tdColors[0]; %>
</logic:equal>

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
    <logic:equal name="sch040Form" property="cmd" value="add">
      <li class="pageTitle_subFont">
       <gsmsg:write key="cmn.entry" />
      </li>
    </logic:equal>
    <logic:equal name="sch040Form" property="cmd" value="edit">
      <li class="pageTitle_subFont">
       <gsmsg:write key="cmn.change" />
      </li>
    </logic:equal>
    <li>
      <logic:equal name="sch040Form" property="cmd" value="add">
        <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.entry" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <gsmsg:write key="cmn.entry" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040_back', '<bean:write name="sch040Form" property="cmd" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:equal>
      <logic:equal name="sch040Form" property="cmd" value="edit">
        <button type="button" class="baseBtn js_editBtn" value="<gsmsg:write key="cmn.change" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
          <gsmsg:write key="cmn.change" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonPush('040_copy', 'add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
          <gsmsg:write key="cmn.register.copy2" />
        </button>
        <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('040_del', '<bean:write name="sch040Form" property="cmd" />');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:notEqual>
        <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <logic:equal name="sch040Form" property="sch040AttendDelFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_SCH_DEL_YES) %>">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('040_del', '<bean:write name="sch040Form" property="cmd" />');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040_back', '<bean:write name="sch040Form" property="sch040BtnCmd" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:equal>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
  <div class="txt_l">
    <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.for.repert" />" onClick="buttonPush('040_extend', '<bean:write name="sch040Form" property="cmd" />');">
        <gsmsg:write key="cmn.for.repert" />
      </button>
    </logic:notEqual>
    <logic:equal name="sch040Form" property="cmd" value="edit">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.pdf" />" onClick="buttonPush2('pdf');">
        <img class="btn_classicImg-display"  src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
        <gsmsg:write key="cmn.pdf" />
      </button>
    </logic:equal>
  </div>
  </logic:notEqual>

  <%-- スケジュールが繰り返し登録の場合警告表示  --%>
  <logic:equal name="sch040Form" property="sch040ExTextDspFlg" value="true">
    <div class="txt_l">
      <gsmsg:write key="schedule.149" />
    </div>
  </logic:equal>
  <%-- 出欠確認  --%>
  <logic:equal name="sch040Form" property="sch010SelectUsrKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.USER_KBN_USER) %>">
    <logic:equal name="sch040Form" property="cmd" value="edit">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_NORMAL) %>">
        <table class="table-left w100">
          <tr>
            <th class="w20 txt_l">
              <gsmsg:write key="schedule.sch040.3" />
            </th>
            <td class="w80 <%= tdColor %>">
              <%--  出欠確認：本人 --%>
              <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
                <div class="pt5 verAlignMid">
                  <html:radio name="sch040Form" property="sch040AttendKbn" styleId="sch040AttendKbn0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_KBN_NO) %>" />
                  <label for="sch040AttendKbn0"><gsmsg:write key="cmn.notcheck" /></label>
                  <html:radio name="sch040Form" property="sch040AttendKbn" styleId="sch040AttendKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_KBN_YES) %>" />
                  <label for="sch040AttendKbn1"><gsmsg:write key="cmn.check.2" /></label>
                </div>
              </logic:notEqual>
              <%--  出欠確認：回答者 --%>
              <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
                <div class="pt5 verAlignMid">
                  <html:radio name="sch040Form" property="sch040AttendAnsKbn" styleId="sch040AttendAnsKbn0" value="0" />
                  <label for="sch040AttendAnsKbn0"><gsmsg:write key="schedule.sch040.4" /></label>
                  <html:radio name="sch040Form" property="sch040AttendAnsKbn" styleId="sch040AttendAnsKbn1" styleClass="ml10" value="1" />
                  <label for="sch040AttendAnsKbn1"><gsmsg:write key="schedule.sch040.5" /></label>
                  <html:radio name="sch040Form" property="sch040AttendAnsKbn" styleId="sch040AttendAnsKbn2" styleClass="ml10" value="2" />
                  <label for="sch040AttendAnsKbn2"><gsmsg:write key="schedule.sch040.6" /></label>
                </div>
                <html:hidden property="sch040AttendKbn"/>
              </logic:equal>
              <!-- コメント表示 -->
              <div class="verAlignMid js_attend_comment ml20 pt5 txt_t">
                <gsmsg:write key="cmn.comment" />：
              </div>
              <div class="verAlignMid pt5 txt_t js_attend_comment attendConfirm_overflow">
                <span id="sch040AttendAnsComment"><bean:write name="sch040Form" property="sch040AttendAnsComment" /></span>
              </div>
              <div class="verAlignMid pt5 txt_t js_attend_comment" >
                <button type="button" value="<gsmsg:write key="cmn.edit" />" class="baseBtn mt0" id="btn_dsp_attend_comment">
                  <gsmsg:write key="cmn.edit" />
                </button>
              </div>
              <!-- コメント編集 -->
              <div class="display_none verAlignMid pt5 ml20 txt_t js_attend_comment_edit" >
                <gsmsg:write key="cmn.comment" />：
              </div>
              <div class="display_none js_attend_comment_edit pt5 txt_t">
                <div>
                  <html:text name="sch040Form"  property="sch040AttendAnsComment" styleClass="wp250" maxlength="50" />
                  <div class="mt5 ">
                    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn"  id="btn_kakutei_attend_comment">
                      <gsmsg:write key="cmn.final" />
                    </button>
                    <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn ml5" id="btn_cancel_attend_comment">
                      <gsmsg:write key="cmn.cancel" />
                    </button>
                  </div>
                </div>
              </div>
              <!-- 回答者一覧 -->
              <div class="mt5">
                <table class="table-top w100">
                  <tr>
                    <th class="w20">
                      <gsmsg:write key="schedule.sch040.7" />
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
                  <logic:notEmpty name="sch040Form" property="sch040AttendAnsList">
                    <logic:iterate id="attendMdl" name="sch040Form" property="sch040AttendAnsList" indexId="idx">
                      <tr>
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
                        <td class="txt_c">
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
                        <td class="txt_l">
                          <bean:write name="attendMdl" property="attendAnsComment" />
                        </td>
                      </tr>
                    </logic:iterate>
                  </logic:notEmpty>
                </table>
                <logic:equal name="sch040Form" property="sch040AttendLinkFlg" value="1">
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
  </logic:equal>
  <table class="table-left w100">
    <logic:equal name="sch040Form" property="schIkkatsuFlg" value="0">
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.4" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:notEqual name="sch040Form" property="sch010SelectUsrKbn" value="0">
          <span class="flo_l">
            <img class="classic-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
            <img class="original-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
          </span>
        </logic:notEqual>
        <logic:equal name="sch040Form" property="sch040UsrUkoFlg" value="1">
          <span class="mukoUserOption"><bean:write name="sch040Form" property="sch040UsrName" /></span>
       </logic:equal>
        <logic:notEqual name="sch040Form" property="sch040UsrUkoFlg" value="1">
          <bean:write name="sch040Form" property="sch040UsrName" />
        </logic:notEqual>
      </td>
    </tr>
    </logic:equal>

    <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="0">
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="schedule.sch040.13" />
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:notEmpty name="sch040Form" property="sch040IkkatsuDspList">
          <logic:iterate id="dspMdl" name="sch040Form" property="sch040IkkatsuDspList">
            <div class="mb5 w100">
              <div class="pl5 bgC_header2">
                <bean:write name="dspMdl" property="dayStr" />
              </div>
            </div>
            <logic:notEmpty name="dspMdl" property="targetName">
              <logic:iterate id="target" name="dspMdl" property="targetName">
                <div class="pl10">
                  <bean:write name="target" property="label" />
                </div>
              </logic:iterate>
            </logic:notEmpty>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    </logic:notEqual>

    <!-- 開　始・終了-->
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.period" />
        <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </logic:notEqual>
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <bean:write name="sch040Form" property="sch040DspFromDate" />
          <html:hidden property="sch040FrYear" />
          <html:hidden property="sch040FrMonth" />
          <html:hidden property="sch040FrDay" />
          <html:hidden property="sch040FrHour" />
          <html:hidden property="sch040FrMin" />
          <html:hidden property="sch040TimeKbn"/>
        </logic:equal>

        <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <div class="verAlignMid">
            <span class="mr10"><gsmsg:write key="cmn.start" /></span>
            <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
            <span class="pos_rel display_flex mr5">
              <html:text name="sch040Form" property="sch040FrDate" maxlength="10" styleClass="wp90 easyRegister-text datepicker " styleId="easyFrDate"/>
              <span class="picker-acs cursor_pointer icon-date input-group-addon" id="iconKikanStart"></span>
            </span>
            </logic:notEqual>
            <label class="clockpicker_fr pos_rel display_flex input-group">
              <html:text name="sch040Form" property="sch040FrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60 "/>
              <span class="picker-acs cursor_pointer icon-clock input-group-addon"></span>
            </label>
            <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
            <button type="button" class="webIconBtn ml5" onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span class="original-display">
              <a class="fw_b todayBtn " onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            </logic:notEqual>
          </div>
          <br>
          <div class="verAlignMid mt5">
            <span class="mr10"><gsmsg:write key="cmn.end" /></span>
            <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
            <span class="pos_rel display_flex mr5">
              <html:text name="sch040Form" property="sch040ToDate" maxlength="10" styleClass="wp90 easyRegister-text datepicker " styleId="easyToDate"/>
              <span class="picker-acs cursor_pointer icon-date input-group-addon" id="iconKikanFinish"></span>
            </span>
            </logic:notEqual>
            <label class="input-group clockpicker_to pos_rel display_flex">
              <html:text name="sch040Form" property="sch040ToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60 "/>
              <span class="picker-acs cursor_pointer icon-clock input-group-addon"></span>
            </label>
            <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
            <button type="button" class="webIconBtn ml5" onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span class="original-display">
              <a class="fw_b todayBtn " onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            </logic:notEqual>
          </div>
          <br>
          <div class="verAlignMid mt5">
            <span class="js_time_master">
              <a href="#!" onclick="setAmTime();"><gsmsg:write key="cmn.am" />
                <span class="tooltips">
                  <bean:write name="sch040Form" property="sch040AmFrHour" format="00" />:<bean:write name="sch040Form" property="sch040AmFrMin" format="00" />～<bean:write name="sch040Form" property="sch040AmToHour" format="00" />:<bean:write name="sch040Form" property="sch040AmToMin" format="00" />
                </span>
              </a>
            </span>
            <span class="js_time_master">
              &nbsp;&nbsp;
              <a href="#!" onclick="setPmTime();"><gsmsg:write key="cmn.pm" />
                <span class="tooltips">
                  <bean:write name="sch040Form" property="sch040PmFrHour" format="00" />:<bean:write name="sch040Form" property="sch040PmFrMin" format="00" />～<bean:write name="sch040Form" property="sch040PmToHour" format="00" />:<bean:write name="sch040Form" property="sch040PmToMin" format="00" />
                </span>
                </a>
            </span>
            <span class="js_time_master">
              &nbsp;&nbsp;
              <a href="#!" onclick="setAllTime();"><gsmsg:write key="cmn.allday" />
                <span class="tooltips">
                  <bean:write name="sch040Form" property="sch040AllDayFrHour" format="00" />:<bean:write name="sch040Form" property="sch040AllDayFrMin" format="00" />～<bean:write name="sch040Form" property="sch040AllDayToHour" format="00" />:<bean:write name="sch040Form" property="sch040AllDayToMin" format="00" />
                </span>
              </a>
            </span>
            <html:checkbox name="sch040Form" property="sch040TimeKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.TIME_NOT_EXIST) %>" styleId="num_seigyo" onclick="return changeTimeStatus();" styleClass="ml20" />
            <label for="num_seigyo"><gsmsg:write key="schedule.7" /></label>

            <span id="betWeenDays" class="ml10"></span>
          </div>
        </logic:notEqual>
      </td>
    </tr>
    <!-- 会社・担当者 -->
    <logic:equal name="sch040Form" property="addressPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <tr>
          <th class="w20 txt_l">
            <gsmsg:write key="schedule.14" />
          </th>
          <td class="w80 <%= tdColor %>">
            <logic:empty name="sch040Form" property="sch040CompanyList">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return openCompanyWindow('sch040');">
                <img class="btn_classicImg-display wp18hp20" src="../schedule/images/classic/icon_address.gif" alt="<gsmsg:write key="addressbook" />">
                <img class="btn_originalImg-display wp18hp20" src="../schedule/images/original/icon_address.png" alt="<gsmsg:write key="addressbook" />">
                <gsmsg:write key="addressbook" />
              </button>
            </logic:empty>
            <logic:notEmpty name="sch040Form" property="sch040CompanyList">
              <div class="verAlignMid">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return openCompanyWindow('sch040');">
                  <img class="btn_classicImg-display wp18hp20" src="../schedule/images/classic/icon_address.gif" alt="<gsmsg:write key="addressbook" />">
                  <img class="btn_originalImg-display wp18hp20" src="../schedule/images/original/icon_address.png" alt="<gsmsg:write key="addressbook" />">
                  <gsmsg:write key="addressbook" />
                </button>
                <logic:notEmpty name="sch040Form" property="sch040AddressId">
                  <html:checkbox name="sch040Form" property="sch040contact" value="1" styleId="contactCheck" styleClass="ml10"/>
                  <label for="contactCheck"><gsmsg:write key="schedule.23" /></label>
                </logic:notEmpty>
              </div>
              <logic:notEmpty name="sch040Form" property="sch040CompanyList">
                <logic:iterate id="companyData" name="sch040Form" property="sch040CompanyList">
                  <ul class="w90 m5 p5">
                    <li class="txt_m verAlignMid w3">
                      <a href="#" onClick="deleteCompany(<bean:write name="companyData" property="companySid" />, <bean:write name="companyData" property="companyBaseSid" />);">
                        <img src="../common/images/classic/icon_delete_2.gif" class="classic-display img-18"/>
                        <img src="../common/images/original/icon_delete.png" class="original-display"/>
                      </a>
                    </li>
                    <li class="txt_m display_inline-block">
                      <div class="txt_m">
                        <img src="../common/images/classic/icon_company.png" class="classic-display"/>
                        <img src="../common/images/original/icon_company.png" class="original-display"/>
                        <logic:equal name="companyData" property="companySid" value="0">
                          <bean:write name="companyData" property="companyName" />
                        </logic:equal>
                        <logic:notEqual name="companyData" property="companySid" value="0">
                          <a href="#!" onclick="return openSubWindow('../address/adr250.do?adr250AcoSid=<bean:write name="companyData" property="companySid" />')">
                            <bean:write name="companyData" property="companyName" />
                          </a>
                        </logic:notEqual>
                      </div>
                      <logic:notEmpty name="companyData" property="addressDataList">
                        <logic:iterate id="addressData" name="companyData" property="addressDataList">
                          <div class="txt_m">
                            <img src="../common/images/classic/icon_user.png" class="classic-display img-18"/>
                            <img src="../common/images/original/icon_user.png" class="original-display"/>
                            <bean:write name="addressData" property="adrName" />
                          </div>
                        </logic:iterate>
                      </logic:notEmpty>
                    </li>
                  </ul>
                </logic:iterate>
              </logic:notEmpty>
            </logic:notEmpty>
          </td>
        </tr>
      </logic:notEqual>
    </logic:equal>
    <!-- タイトル -->
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.title" />
        <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </logic:notEqual>
      </th>
      <td class="w80 <%= tdColor %>">
        <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <% if (selectionFlg) { %>
            <html:text name="sch040Form" maxlength="50" property="sch040Title" styleClass="wp400" styleId="selectionSearchArea" />
          <% } else { %>
            <html:text name="sch040Form" maxlength="50" property="sch040Title" styleClass="wp400" styleId="selectionSearchArea" />
          <% } %>
        </logic:notEqual>
        <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
          <bean:write name="sch040Form" property="sch040Title" />
          <html:hidden property="sch040Title" />
        </logic:equal>
      </td>
    </tr>
    <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch040_sub1.jsp" >
      <jsp:param value="<%= tdColor %>" name="tdColor"/>
    </jsp:include>
    <%-- グループスケジュール 非表示部分(同時登録部分) START --%>
    <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch040_atOnce.jsp">
    <jsp:param value="<%= tdColor %>" name="tdColor"/>
    </jsp:include>
    <%-- グループスケジュール 非表示部分(同時登録部分) END --%>
    <%-- 施設予約使用　有無判定 --%>
    <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch040_rsv.jsp">
      <jsp:param value="<%= tdColor %>" name="tdColor"/>
    </jsp:include>
    <%-- 施設予約使用　有無判定 END --%>
    <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
    <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
      <tr>
        <th class="w20 txt_l">
          <gsmsg:write key="schedule.18" />
        </th>
        <td class="w80 <%= tdColor %>">
          <gsmsg:write key="cmn.comments" /><gsmsg:write key="schedule.35" />
          <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.17" />" onClick="openScheduleReserveWindowSch040('<bean:write name="sch040Form" property="sch040ReserveGroupSid" />','<bean:write name="sch040Form" property="sch010SelectDate" />');">
            <gsmsg:write key="schedule.17" />
          </button>
        </td>
      </tr>
    </logic:notEqual>
    </logic:notEqual>
    <logic:notEqual name="sch040Form" property="reservePluginKbn" value="0">
      <html:hidden property="sch040ReserveGroupSid" />
    </logic:notEqual>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="w80 <%= tdColor %>">
        <div class="display_inline">
          <logic:notEqual name="sch040Form" property="sch040AddUsrJkbn" value="9">
            <logic:equal name="sch040Form" property="sch040AddUsrUkoFlg" value="1">
              <span class="mukoUserOption">
                <bean:write name="sch040Form" property="sch040AddUsrName" />
              </span>
            </logic:equal>
            <logic:notEqual name="sch040Form" property="sch040AddUsrUkoFlg" value="1">
              <bean:write name="sch040Form" property="sch040AddUsrName" />
            </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="sch040Form" property="sch040AddUsrJkbn" value="9">
            <del>
              <bean:write name="sch040Form" property="sch040AddUsrName" />
            </del>
          </logic:equal>
        </div>
        <div class="ml20 display_inline">
          <bean:write name="sch040Form" property="sch040AddDate" />
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <logic:equal name="sch040Form" property="cmd" value="add">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onClick="buttonPush('040_ok', '<bean:write name="sch040Form" property="cmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <gsmsg:write key="cmn.entry" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040_back', '<bean:write name="sch040Form" property="cmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:equal name="sch040Form" property="cmd" value="edit">
      <button type="button" class="baseBtn js_editBtn" value="<gsmsg:write key="cmn.change" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
        <gsmsg:write key="cmn.change" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonPush('040_copy', 'add');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
        <gsmsg:write key="cmn.register.copy2" />
      </button>
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('040_del', '<bean:write name="sch040Form" property="cmd" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <logic:equal name="sch040Form" property="sch040AttendDelFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_SCH_DEL_YES) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('040_del', '<bean:write name="sch040Form" property="cmd" />');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040_back', '<bean:write name="sch040Form" property="sch040BtnCmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
  </div>
</div>
</html:form>
<!-- 確認ダイアログ -->
<div id="schEditMail" title="<gsmsg:write key="schedule.sch040.3" /><gsmsg:write key="cmn.update" />" class="display_n">
 <ul class="mt20 p0">
   <li class="display_inline-block" >
     <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.info">
     <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.info">
   </li>
   <li class="display_inline-block txt_t pt5">
     <div>
       <gsmsg:write key="schedule.sch040.10" />
     </div>
     <div>
       <div class="verAlignMid">
         <input type="checkbox" id="sch040EditMailSendKbn" value="1" />
         <label for="sch040EditMailSendKbn">
           <span class="cl_fontWarn"><gsmsg:write key="schedule.sch040.11" /></span>
         </label>
       </div>
     </div>
   </li>
 </ul>
</div>
<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp"/>
</body>
</html:html>
