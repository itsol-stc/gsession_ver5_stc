<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
int dspOn = jp.groupsession.v2.cmn.GSConstReserve.RSV_OVERTIME_DSP_ON;
%>
<%
int dspOff = jp.groupsession.v2.cmn.GSConstReserve.RSV_OVERTIME_DSP_OFF;
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <gsmsg:write key="reserve.rsv240kn.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv240kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv240knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv240knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsv240InitFlg" />
<html:hidden property="rsv240AllCheck" />
<html:hidden property="rsv240overTimeDspKbn" />

<logic:notEmpty name="rsv240knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv240knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv240knForm" property="rsv240RsgSids" scope="request">
  <logic:iterate id="grpSid" name="rsv240knForm" property="rsv240RsgSids" scope="request">
    <input type="hidden" name="rsv240RsgSids" value="<bean:write name="grpSid"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="reserve.rsv240kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('rsv240knkakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv240knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">

  <logic:empty name="rsv240knForm" property="rsv240knGrpList" scope="request">
    <div class="txt_l">
      <gsmsg:write key="reserve.rsv240kn.2" />
    </div>
  </logic:empty>

  <logic:notEmpty name="rsv240knForm" property="rsv240knGrpList" scope="request">

    <div class="txt_l cl_fontWarn mb5">
      <logic:equal name="rsv240knForm" property="rsv240overTimeDspKbn" value="<%= String.valueOf(dspOn) %>">
        <gsmsg:write key="reserve.show.time.passed.ok" />
      </logic:equal>
      <logic:equal name="rsv240knForm" property="rsv240overTimeDspKbn" value="<%= String.valueOf(dspOff) %>">
        <gsmsg:write key="reserve.show.time.passed.no" />
      </logic:equal>
    </div>

    <table class="table-top mt0">
      <tr>
        <th>
          <gsmsg:write key="cmn.group.name" />
        </th>
      </tr>
      <bean:define id="mod" value="0" />
      <logic:iterate id="grpMdl" name="rsv240knForm" property="rsv240knGrpList" scope="request" indexId="idx">
      <tr>
        <td>
          <bean:write name="grpMdl" property="rsgName" />
        </td>
      </tr>
      </logic:iterate>
    </table>
  </logic:notEmpty>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('rsv240knkakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv240knback');">
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