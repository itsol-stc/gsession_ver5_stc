<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<%-- 定数 --%>
<%
    String importCsv = jp.groupsession.v2.prj.prj160kn.Prj160knAction.CMD_CSV_IMPORT;
    String back      = jp.groupsession.v2.prj.prj160kn.Prj160knAction.CMD_BACK_CLICK;
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/project/prj160kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="prj010cmdMode" />
<html:hidden property="prj010page1" />
<html:hidden property="prj010page2" />
<html:hidden property="prj010sort" />
<html:hidden property="prj010order" />
<html:hidden property="prj010Init" />
<html:hidden property="selectingProject" />
<html:hidden property="selectingTodoDay" />
<html:hidden property="selectingTodoPrj" />
<html:hidden property="selectingTodoSts" />
<html:hidden property="prj040searchFlg" />
<html:hidden property="prj040scPrjId" />
<html:hidden property="prj040scStatusFrom" />
<html:hidden property="prj040scStatusTo" />
<html:hidden property="prj040scPrjName" />
<html:hidden property="prj040scStartYearFrom" />
<html:hidden property="prj040scStartMonthFrom" />
<html:hidden property="prj040scStartDayFrom" />
<html:hidden property="prj040scStartYearTo" />
<html:hidden property="prj040scStartMonthTo" />
<html:hidden property="prj040scStartDayTo" />
<html:hidden property="prj040scEndYearFrom" />
<html:hidden property="prj040scEndMonthFrom" />
<html:hidden property="prj040scEndDayFrom" />
<html:hidden property="prj040scEndYearTo" />
<html:hidden property="prj040scEndMonthTo" />
<html:hidden property="prj040scEndDayTo" />
<html:hidden property="prj040svScPrjId" />
<html:hidden property="prj040svScStatusFrom" />
<html:hidden property="prj040svScStatusTo" />
<html:hidden property="prj040svScPrjName" />
<html:hidden property="prj040svScStartYearFrom" />
<html:hidden property="prj040svScStartMonthFrom" />
<html:hidden property="prj040svScStartDayFrom" />
<html:hidden property="prj040svScStartYearTo" />
<html:hidden property="prj040svScStartMonthTo" />
<html:hidden property="prj040svScStartDayTo" />
<html:hidden property="prj040svScEndYearFrom" />
<html:hidden property="prj040svScEndMonthFrom" />
<html:hidden property="prj040svScEndDayFrom" />
<html:hidden property="prj040svScEndYearTo" />
<html:hidden property="prj040svScEndMonthTo" />
<html:hidden property="prj040svScEndDayTo" />
<html:hidden property="prj040page1" />
<html:hidden property="prj040page2" />
<html:hidden property="prj040sort" />
<html:hidden property="prj040order" />
<html:hidden property="prj040scYosanFr" />
<html:hidden property="prj040scYosanTo" />
<html:hidden property="prj040svScYosanFr" />
<html:hidden property="prj040svScYosanTo" />

<html:hidden property="prj160PrjMyKbn" />

<logic:notEmpty name="prj160knForm" property="prj040scMemberSid" scope="request">
  <logic:iterate id="item" name="prj160knForm" property="prj040scMemberSid" scope="request">
    <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj160knForm" property="prj040svScMemberSid" scope="request">
  <logic:iterate id="item" name="prj160knForm" property="prj040svScMemberSid" scope="request">
    <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj160knForm" property="prj030sendMember" scope="request">
  <logic:iterate id="item" name="prj160knForm" property="prj030sendMember" scope="request">
    <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="prj160ImportMeans" />
<html:hidden property="prj030scrId" />
<html:hidden property="prj030prjSid" />
<html:hidden property="prj030sort" />
<html:hidden property="prj030order" />
<html:hidden property="prj030page1" />
<html:hidden property="prj030page2" />
<html:hidden property="prj030Init" />
<html:hidden property="prj060todoSid" />
<html:hidden property="prj160PrjSid" />
<html:hidden property="selectingDate" />
<html:hidden property="selectingStatus" />
<html:hidden property="selectingCategory" />
<html:hidden property="selectingMember" />
<html:hidden property="selectDir" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
      <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
    </li>
    <li><gsmsg:write key="cmn.project" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="project.prj160.7" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.run" />" class="baseBtn" onClick="buttonPush('<%= importCsv %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
          <gsmsg:write key="cmn.run" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= back %>');">
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
        <gsmsg:write key="cmn.capture.file" />
      </th>
      <td class="w75">
        <bean:write name="prj160knForm" property="prj160knImportFileName" />
      </td>
    </tr>
    <gsmsg:define id="textAddImport" msgkey="project.prj160.5" />
    <gsmsg:define id="textResetImport" msgkey="project.prj160.6" />
    <tr>
      <th>
        <gsmsg:write key="project.4" />
      </th>
      <td>
        <logic:equal name="prj160knForm" property="prj160ImportMeans" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.PRJ_IMP_MEANS_ADD) %>">
          <%= textAddImport %>
        </logic:equal>
        <logic:equal name="prj160knForm" property="prj160ImportMeans" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.PRJ_IMP_MEANS_RESET) %>">
          <%= textResetImport %>
        </logic:equal>
      </td>
      <tr>
      <th>
        <gsmsg:write key="cmn.project" />
      </th>
      <td>
        <bean:write name="prj160knForm" property="prj160knTargetProjectName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.prj160kn.2" />
      </th>
      <td>
        <bean:write name="prj160knForm" property="prj160knImportUserName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <bean:write name="prj160knForm" property="prj160ImportCnt" /><gsmsg:write key="cmn.number" />
      </td>
    </tr>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.run" />" class="baseBtn" onClick="buttonPush('<%= importCsv %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
      <gsmsg:write key="cmn.run" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= back %>');">
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