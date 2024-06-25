<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%-- CMD定数 --%>
<%
    String backClick       = jp.groupsession.v2.prj.prj140kn.Prj140knAction.CMD_BACK_CLICK;
    String tmpBackClick    = jp.groupsession.v2.prj.prj140kn.Prj140knAction.CMD_BACK;
    String tmpSelectClick  = jp.groupsession.v2.prj.prj140kn.Prj140knAction.CMD_SELECT_CLICK;

%>

<%-- 定数値 --%>
<%  String mode_kojin      = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_KOJIN);
    String mode_kyoyu      = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_KYOYU);
    String mode_select     = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_SELECT);
    String enabled         = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_KOUKAI_ENABLED);
    String disabled        = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_KOUKAI_DISABLED);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../project/js/prj140kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/project/prj140kn">
<input type="hidden" name="helpPrm" value="<bean:write name="prj140knForm" property="prj140cmdMode" />">
<input type="hidden" name="CMD" id="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="prj010cmdMode" />
<html:hidden property="prj010sort" />
<html:hidden property="prj010order" />
<html:hidden property="prj010page1" />
<html:hidden property="prj010page2" />
<html:hidden property="prj010Init" />
<html:hidden property="selectingProject" />
<html:hidden property="selectingTodoDay" />
<html:hidden property="selectingTodoPrj" />
<html:hidden property="selectingTodoSts" />
<html:hidden property="prjTmpMode" />
<html:hidden property="prtSid" />
<html:hidden property="prj140prtTmpName" />
<html:hidden property="prj140prtId" />
<html:hidden property="prj140prtName" />
<html:hidden property="prj140prtNameS" />
<html:hidden property="prj140yosan" />
<html:hidden property="prj140koukai" />
<html:hidden property="prj140startYear" />
<html:hidden property="prj140startMonth" />
<html:hidden property="prj140startDay" />
<html:hidden property="prj140endYear" />
<html:hidden property="prj140endMonth" />
<html:hidden property="prj140endDay" />
<html:hidden property="prj140status" />
<html:hidden property="prj140mokuhyou" />
<html:hidden property="prj140naiyou" />
<html:hidden property="prj140group" />
<html:hidden property="prj140kengen" />
<html:hidden property="prj140smailKbn" />

<logic:notEmpty name="prj140knForm" property="prj140syozokuMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj140syozokuMember" scope="request">
    <input type="hidden" name="prj140syozokuMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj140user" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj140user" scope="request">
    <input type="hidden" name="prj140user" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj140hdnMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj140hdnMember" scope="request">
    <input type="hidden" name="prj140hdnMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj140adminMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj140adminMember" scope="request">
    <input type="hidden" name="prj140adminMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj140prjMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj140prjMember" scope="request">
    <input type="hidden" name="prj140prjMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj140hdnAdmin" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj140hdnAdmin" scope="request">
    <input type="hidden" name="prj140hdnAdmin" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

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

<logic:notEmpty name="prj140knForm" property="prj040scMemberSid" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj040scMemberSid" scope="request">
    <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj040svScMemberSid" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj040svScMemberSid" scope="request">
    <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="prj030scrId" />
<html:hidden property="prj030prjSid" />
<html:hidden property="prj030sort" />
<html:hidden property="prj030order" />
<html:hidden property="prj030page1" />
<html:hidden property="prj030page2" />
<html:hidden property="prj030Init" />
<html:hidden property="selectingDate" />
<html:hidden property="selectingStatus" />
<html:hidden property="selectingCategory" />
<html:hidden property="selectingMember" />

<logic:notEmpty name="prj140knForm" property="prj030sendMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj030sendMember" scope="request">
    <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="prj020scrId" />
<html:hidden property="prj020cmdMode" />
<html:hidden property="prj020prjSid" />
<html:hidden property="prj020prjId" />
<html:hidden property="prj020prjName" />
<html:hidden property="prj020prjNameS" />
<html:hidden property="prj020yosan" />
<html:hidden property="prj020koukai" />
<html:hidden property="prj020startYear" />
<html:hidden property="prj020startMonth" />
<html:hidden property="prj020startDay" />
<html:hidden property="prj020endYear" />
<html:hidden property="prj020endMonth" />
<html:hidden property="prj020endDay" />
<html:hidden property="prj020status" />
<html:hidden property="prj020mokuhyou" />
<html:hidden property="prj020naiyou" />
<html:hidden property="prj020group" />
<html:hidden property="prj020kengen" />
<html:hidden property="prj020prjMyKbn" />
<html:hidden property="prj020smailKbn" />
<html:hidden property="prj020IcoName" />
<html:hidden property="prj020IcoSaveName" />
<html:hidden property="prj020initFlg" />

<logic:notEmpty name="prj140knForm" property="prj020syozokuMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj020syozokuMember" scope="request">
    <input type="hidden" name="prj020syozokuMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj020user" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj020user" scope="request">
    <input type="hidden" name="prj020user" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj020adminMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj020adminMember" scope="request">
    <input type="hidden" name="prj020adminMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj020prjMember" scope="request">
  <logic:iterate id="item" name="prj140knForm" property="prj020prjMember" scope="request">
    <input type="hidden" name="prj020prjMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<div id="hiddenList">

  <logic:notEmpty name="prj140knForm" property="prj020hdnMember" scope="request">
    <logic:iterate id="item" name="prj140knForm" property="prj020hdnMember" scope="request">
      <input type="hidden" name="prj020hdnMember" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj140knForm" property="prj020hdnAdmin" scope="request">
    <logic:iterate id="item" name="prj140knForm" property="prj020hdnAdmin" scope="request">
      <input type="hidden" name="prj020hdnAdmin" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

</div>

<logic:notEmpty name="prj140knForm" property="prj150AddressIdSv" scope="request">
  <logic:iterate id="addressId" name="prj140knForm" property="prj150AddressIdSv" scope="request">
    <input type="hidden" name="prj150AddressIdSv" value="<bean:write name="addressId"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj150CompanySid" scope="request">
  <logic:iterate id="coId" name="prj140knForm" property="prj150CompanySid" scope="request">
    <input type="hidden" name="prj150CompanySid" value="<bean:write name="coId"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj140knForm" property="prj150CompanyBaseSid" scope="request">
  <logic:iterate id="coId" name="prj140knForm" property="prj150CompanyBaseSid" scope="request">
    <input type="hidden" name="prj150CompanyBaseSid" value="<bean:write name="coId"/>">
  </logic:iterate>
</logic:notEmpty>

<bean:define id="prjStsMdl" name="prj140knForm" property="prjStatusTmpMdl" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<logic:equal name="prj140knForm" property="prjTmpMode" value="<%= mode_kyoyu %>">
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span>
      <logic:lessEqual name="prj140knForm" property="prtSid" value="0">
        <gsmsg:write key="cmn.entry.shared.template.kn" />
      </logic:lessEqual>

      <logic:greaterThan name="prj140knForm" property="prtSid" value="0">
        <gsmsg:write key="project.prj150.2" />
      </logic:greaterThan>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
</logic:equal>

<logic:equal name="prj140knForm" property="prjTmpMode" value="<%= mode_kojin %>">
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span>
      <logic:lessEqual name="prj140knForm" property="prtSid" value="0">
        <gsmsg:write key="cmn.entry.personal.template.kn" />
      </logic:lessEqual>

      <logic:greaterThan name="prj140knForm" property="prtSid" value="0">
        <gsmsg:write key="project.prj150.4" />
      </logic:greaterThan>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
</logic:equal>

<logic:equal name="prj140knForm" property="prjTmpMode" value="<%= mode_select %>">
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
      <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
    </li>
    <li><gsmsg:write key="cmn.project" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="select.template" />
    </li>
    <li>
     <div>
       <button type="button" value="<gsmsg:write key="cmn.select" />" class="baseBtn" onClick="copyTmpData('<%= tmpSelectClick %>');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.select" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.select" />">
         <gsmsg:write key="cmn.select" />
       </button>
       <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= tmpBackClick %>');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
         <gsmsg:write key="cmn.back" />
       </button>
      </div>
    </li>
  </ul>
</div>
</logic:equal>

<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="project.prj140.6" />
      </th>
      <td class="w75">
        <bean:write name="prj140knForm" property="prj140prtTmpName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.31" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="prj140prtId" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.40" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="prj140prtName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.41" />
      </th>
      <td>
       <bean:write name="prj140knForm" property="prj140prtNameS" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.10" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="yosan" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.19" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="koukai" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.start" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="startDate" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.end" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="endDate" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.status" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="status" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.21" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="mokuhyou" filter="false" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.content2" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="naiyou" filter="false" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.squad" />
      </th>
      <td>
        <logic:notEmpty name="prj140knForm" property="syozokuMemberLabel">
        <logic:iterate id="member" name="prj140knForm" property="syozokuMemberLabel" type="UsrLabelValueBean">
          <span class="<%=member.getCSSClassNameNormal()%>"><bean:write name="member" property="label" /></span><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.src.32" />
      </th>
      <td>
        <logic:notEmpty name="prj140knForm" property="adminMemberLabel">
        <logic:iterate id="member" name="prj140knForm" property="adminMemberLabel" type="UsrLabelValueBean">
          <span class="<%=member.getCSSClassNameNormal()%>"><bean:write name="member" property="label" /></span><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>

  <div class="txt_l mt20"><gsmsg:write key="project.prj150.5" /></div>
    <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.label" />
      </th>
      <td class="w75">
        <logic:notEmpty name="prjStsMdl" property="todoCateList">
        <logic:iterate id="todoCate" name="prjStsMdl" property="todoCateList">
          <bean:write name="todoCate" property="pctName" /><br>
        </logic:iterate>
      </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.status" />
      </th>
      <td>
        <logic:notEmpty name="prjStsMdl" property="todoStatusList">
        <logic:iterate id="todoStatus" name="prjStsMdl" property="todoStatusList">
          <bean:write name="todoStatus" property="pstRate" />%パーセント(<bean:write name="todoStatus" property="pstName" />)<br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="kengen" />
      </td>
    </tr>
    <logic:equal name="prj140knForm" property="useSmail" value="true">
    <tr>
      <th>
        <gsmsg:write key="project.2" />
      </th>
      <td>
        <bean:write name="prj140knForm" property="smailKbn" />
      </td>
    </tr>
    </logic:equal>
  </table>

  <div class="footerBtn_block">
    <logic:equal name="prj140knForm" property="prjTmpMode" value="<%= mode_select %>">
    <button type="button" value="<gsmsg:write key="cmn.select" />" class="baseBtn" onClick="copyTmpData('<%= tmpSelectClick %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.select" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.select" />">
      <gsmsg:write key="cmn.select" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= tmpBackClick %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
    </logic:equal>

    <logic:notEqual name="prj140knForm" property="prjTmpMode" value="<%= mode_select %>">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
    </logic:notEqual>
  </div>
</div>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>

</div>

</body>
</html:html>