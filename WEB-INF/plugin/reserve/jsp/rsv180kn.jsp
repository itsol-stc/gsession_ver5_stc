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
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [<gsmsg:write key="reserve.rsv180kn.1" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv180kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv180kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="rsv180Selectgroup" />
<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvBackToAdminSetting" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv050SortRadio" />
<html:hidden property="rsv080EditGrpSid" />
<html:hidden property="rsv080EditSisetuSid" />
<html:hidden property="rsv080SortRadio" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv180knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv180knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv180knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv180knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
      <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
    </li>
    <li><gsmsg:write key="cmn.reserve" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="reserve.rsv180kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_import_input');">
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
<div class="txt_l cl_fontWarn"><gsmsg:write key="cmn.capture.file.sure" /></div>

<table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.facility.group" />
      </th>
      <td class="w80">
        <bean:write name="rsv180knForm" property="rsv080RsgName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.47" />
      </th>
      <td>
        <bean:write name="rsv180knForm" property="rsv080RskName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.file.name" />
      </th>
      <td>
        <a href="../reserve/rsv180kn.do?CMD=downLoad">
          <bean:write name="rsv180knForm" property="rsv180knFileName" />
        </a>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.user.name" />
      </th>
      <td>
        <bean:write name="rsv180knForm" property="rsv180knUserName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <bean:write name="rsv180knForm" property="impDataCnt" /><gsmsg:write key="cmn.number" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImport');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_import_input');">
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