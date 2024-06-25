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
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting" />[<gsmsg:write key="cmn.reserve" /> <gsmsg:write key="cmn.manual.delete.kn" />]</title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../reserve/js/rsv130kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv130kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv130year" />
<html:hidden property="rsv130month" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv130knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv130knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv130knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv130knForm" property="rsvIkkatuTorokuKey" scope="request">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.manual.delete.kn" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('data_sakuzyo_kakutei');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_data_sakuzyo_input');">
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
        <gsmsg:write key="cmn.manual.delete2" />
      </th>
      <td class="w75">
        <bean:define id="month" name="rsv130knForm" property="rsv130month" type="java.lang.Integer" />
        <bean:define id="yr" name="rsv130knForm" property="rsv130year" type="java.lang.Integer" />
        <gsmsg:write key="cmn.year" arg0="<%= String.valueOf(yr) %>"/> <gsmsg:write key="cmn.months" arg0="<%= String.valueOf(month) %>" /> <gsmsg:write key="cmn.del.data.older.than2" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('data_sakuzyo_kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_data_sakuzyo_input');">
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