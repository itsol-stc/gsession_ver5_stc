<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.prj.GSConstProject" %>


<%-- 定数 --%>
<%
    String downLoad  = jp.groupsession.v2.prj.prj160.Prj160Action.CMD_CSV_DOWNLOAD;
    String importCsv = jp.groupsession.v2.prj.prj160.Prj160Action.CMD_IMP_CLICK;
    String back      = jp.groupsession.v2.prj.prj160.Prj160Action.CMD_BACK_CLICK;
%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<link rel=stylesheet href="../project/css/project.css?<%= GSConst.VERSION_PARAM %>" type="text/css">

<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../project/js/prj160.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onunload="windowClose();">

<html:form action="/project/prj160">
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

<logic:notEmpty name="prj160Form" property="prj040scMemberSid" scope="request">
  <logic:iterate id="item" name="prj160Form" property="prj040scMemberSid" scope="request">
    <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj160Form" property="prj040svScMemberSid" scope="request">
  <logic:iterate id="item" name="prj160Form" property="prj040svScMemberSid" scope="request">
    <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="prj160Form" property="prj030sendMember" scope="request">
  <logic:iterate id="item" name="prj160Form" property="prj030sendMember" scope="request">
    <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="prj030scrId" />
<html:hidden property="prj030prjSid" />
<html:hidden property="prj030sort" />
<html:hidden property="prj030order" />
<html:hidden property="prj030page1" />
<html:hidden property="prj030page2" />
<html:hidden property="prj030Init" />
<html:hidden property="prj060todoSid" />
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
      <gsmsg:write key="project.prj160.1" />
    </li>
    <li>
      <div>
       <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onclick="buttonPush('<%= importCsv %>');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
       <gsmsg:write key="cmn.import" />
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

  <logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>

  <table class="table-left">
    <tr>
    <th class="txt_l w25">
  <gsmsg:write key="cmn.capture.file" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
</th>
<td class="w75">
  <span class="fs_13">
     <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
     <% String csvFileMsg = "<a href=\"../project/prj160.do?CMD=" + downLoad + "&sample=1\">" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>"; %>
     *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg %>" />
  </span>
  <attachmentFile:filearea
  mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
  pluginId="<%=GSConstProject.PLUGIN_ID_PROJECT%>"
  tempDirId="prj160" />
</td>
    </tr>
    <tr>
    <th><span><gsmsg:write key="project.4" /></span></th>
    <td>
      <span class="verAlignMid">
        <html:radio styleId="lvl1" name="prj160Form" property="prj160ImportMeans" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.PRJ_IMP_MEANS_ADD) %>" /><label for="lvl1"><gsmsg:write key="project.prj160.5" /></label>
        <html:radio styleClass="ml10" styleId="lvl2" name="prj160Form" property="prj160ImportMeans" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.PRJ_IMP_MEANS_RESET) %>" /><label for="lvl2"><gsmsg:write key="project.prj160.6" /></label>
      </span>
    </td>
    </tr>

    <tr>
    <th><span><gsmsg:write key="cmn.project" /></span></th>
    <td>
      <html:select property="prj160PrjSid" styleClass="wp250" onchange="buttonPush('projectChange');">
        <html:optionsCollection name="prj160Form" property="prj160ProjectLabel" value="value" label="label" />
      </html:select>
    </td>
    </tr>

    <tr>
    <th><span><gsmsg:write key="cmn.label" /></span></th>
    <td>
      <logic:notEmpty name="prj160Form" property="prj160CategoryList">
        <div>
        <logic:iterate id="category" name="prj160Form" property="prj160CategoryList" scope="request" indexId="idx">
          <div><bean:write name="category" property="ptcName" /></div>
        </logic:iterate>
        </div>
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th><span><gsmsg:write key="cmn.status" /></span></th>
    <td>
      <logic:notEmpty name="prj160Form" property="prj160StatusList">
        <div>
        <logic:iterate id="status" name="prj160Form" property="prj160StatusList" scope="request" indexId="idx">
          <div><bean:write name="status" property="ptsRate" />%（<bean:write name="status" property="ptsName" />）</div>
        </logic:iterate>
        </div>
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th><span><gsmsg:write key="project.src.29" /></span></th>
    <td class="txt_l">

      <table class="table-top">
      <tr>
      <th class="txt_r w5 no_w"><span>Ｎｏ</span></th>
      <th class="txt_c w40"><span><gsmsg:write key="cmn.name" /></span></th>
      <th class="txt_c w55 no_w"><span><gsmsg:write key="project.3" /></span></th>
      </tr>

      <logic:notEmpty name="prj160Form" property="prj160MemberList">
        <bean:define id="mod" value="0" />
        <logic:iterate name="prj160Form" property="prj160MemberList" scope="request" id="member" indexId="index">


          <tr class="js_listHover js_listClick cursor_p" id="<bean:write name="member" property="userSid" />">
          <td class="txt_r">
            <span><%= String.valueOf(index.intValue() + 1) %></span>
          </td>
          <td class="txt_l">
            <span class="cl_linkDef"><bean:write name="member" property="sei" />&nbsp;&nbsp;<bean:write name="member" property="mei" /></span>
          </td>
          <td class="txt_l">
            <span><bean:write name="member" property="memberKey" /></span>
          </td>
          </tr>

        </logic:iterate>
      </logic:notEmpty>

      </table>
</td>
</tr>
</table>

      <div class="footerBtn_block txt_r">
       <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onclick="buttonPush('<%= importCsv %>');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
       <gsmsg:write key="cmn.import" />
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