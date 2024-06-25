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
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <gsmsg:write key="cmn.preferences.kn" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv220kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />

<html:hidden property="rsv220AmFrHour" />
<html:hidden property="rsv220AmFrMin" />
<html:hidden property="rsv220AmToHour" />
<html:hidden property="rsv220AmToMin" />
<html:hidden property="rsv220PmFrHour" />
<html:hidden property="rsv220PmFrMin" />
<html:hidden property="rsv220PmToHour" />
<html:hidden property="rsv220PmToMin" />
<html:hidden property="rsv220AllDayFrHour" />
<html:hidden property="rsv220AllDayFrMin" />
<html:hidden property="rsv220AllDayToHour" />
<html:hidden property="rsv220AllDayToMin" />
<html:hidden property="rsv220AmTime" />
<html:hidden property="rsv220PmTime" />
<html:hidden property="rsv220AllTime" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv220knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv220knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="rsv220HourDiv" />

<logic:notEmpty name="rsv220Form" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv220Form" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.preferences.kn" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('rsv220knkakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv220knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="reserve.123" />
      </th>
      <td class="w75">
        <logic:equal name="rsv220Form" property="rsv220HourDiv" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.HOUR_DIVISION5)%>">
          <gsmsg:write key="reserve.rsv220.3" />
        </logic:equal>
        <logic:equal name="rsv220Form" property="rsv220HourDiv" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.DF_HOUR_DIVISION)%>">
          <gsmsg:write key="reserve.rsv220.4" />
        </logic:equal>
        <logic:equal name="rsv220Form" property="rsv220HourDiv" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.HOUR_DIVISION15)%>">
          <gsmsg:write key="reserve.rsv220.5" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.time.master" />
      </th>
      <td>
        <bean:write name="rsv220knForm" property="rsv220AmTime"/><br>
        <bean:write name="rsv220knForm" property="rsv220PmTime"/><br>
        <bean:write name="rsv220knForm" property="rsv220AllTime"/><br>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('rsv220knkakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv220knback');">
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
