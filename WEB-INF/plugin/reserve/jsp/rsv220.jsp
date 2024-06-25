<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <gsmsg:write key="cmn.preferences" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../reserve/js/rsv220.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv220">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="hourDivision" value="<bean:write name="rsv220Form" property="rsv220HourDiv" />" />
<input type="hidden" name="rsv220AmFrHour" value="">
<input type="hidden" name="rsv220AmFrMin" value="">
<input type="hidden" name=rsv220AmToHour value="">
<input type="hidden" name="rsv220AmToMin" value="">
<input type="hidden" name=rsv220PmFrHour value="">
<input type="hidden" name="rsv220PmFrMin" value="">
<input type="hidden" name=rsv220PmToHour value="">
<input type="hidden" name="rsv220PmToMin" value="">
<input type="hidden" name=rsv220AllDayFrHour value="">
<input type="hidden" name="rsv220AllDayFrMin" value="">
<input type="hidden" name=rsv220AllDayToHour value="">
<input type="hidden" name="rsv220AllDayToMin" value="">
<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv220AmTime" />
<html:hidden property="rsv220PmTime" />
<html:hidden property="rsv220AllTime" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv220Form" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv220Form" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv220Form" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv220Form" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParams();buttonPush('rsv220ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv220back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>

</div>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="reserve.123" />
      </th>
      <td class="w75">
      <gsmsg:write key="reserve.rsv220.2" /><br>
      <div class="verAlignMid">
        <html:radio name="rsv220Form" property="rsv220HourDiv" styleId="rsv220HourDiv0" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.HOUR_DIVISION5)%>" />
        <label for="rsv220HourDiv0" ><gsmsg:write key="reserve.rsv220.3" /></label>
        <html:radio name="rsv220Form" styleClass="ml10" property="rsv220HourDiv" styleId="rsv220HourDiv1" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.DF_HOUR_DIVISION)%>" />
        <label for="rsv220HourDiv1" class=""><gsmsg:write key="reserve.rsv220.4" /></label>
        <html:radio name="rsv220Form" styleClass="ml10" property="rsv220HourDiv" styleId="rsv220HourDiv2" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.HOUR_DIVISION15)%>" />
        <label for="rsv220HourDiv2"><gsmsg:write key="reserve.rsv220.5" /></label>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.time.master" />
      </th>
      <td>
        <gsmsg:write key="reserve.rsv220.6" />
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.am" />：
          <span class="clockpicker_fr ml5 mr5 pos_rel display_flex input-group">
            <html:text name="rsv220Form" property="rsv220AmFrTime" styleId="fr_clock1" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock1" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          ～
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="rsv220Form" property="rsv220AmToTime" styleId="to_clock1" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock1" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.pm" />：
          <span class="clockpicker_fr ml5 mr5 pos_rel display_flex input-group">
            <html:text name="rsv220Form" property="rsv220PmFrTime" styleId="fr_clock2" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock2" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          ～
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="rsv220Form" property="rsv220PmToTime" styleId="to_clock2" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock2" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.allday" />：
          <span class="clockpicker_fr ml5 mr5 pos_rel display_flex input-group">
            <html:text name="rsv220Form" property="rsv220AllDayFrTime" styleId="fr_clock3" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock3" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          ～
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="rsv220Form" property="rsv220AllDayToTime" styleId="to_clock3" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock3" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParams();buttonPush('rsv220ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv220back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>
