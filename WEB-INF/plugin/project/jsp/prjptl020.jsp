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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../project/js/prjptl020.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="cmn.project" /></title>
</head>

<!-- BODY -->
<body onload="closeWindow();">

<html:form action="/project/prjptl020">

<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />

<html:hidden property="prjptl020selectFlg" />
<html:hidden property="prjptl020selectPrjSid" />

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
      <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
    </li>
    <li>
      <gsmsg:write key="cmn.project" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="project.prj010.5" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <div class="txt_l">
    <gsmsg:write key="prj.ptl020.2" />
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.21" />
      </th>
      <td class="w80">
        <logic:notEmpty name="prjptl020Form" property="portletTypeCombo">
          <html:select property="ptl080PluginPortlet" onchange="buttonPush('prjChangeCombo');">
            <html:optionsCollection property="portletTypeCombo" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>

  <% boolean existPageCombo = false; %>
  <logic:notEmpty name="prjptl020Form" property="pageCombo">
    <bean:size id="count1" name="prjptl020Form" property="pageCombo" scope="request" />
    <logic:greaterThan name="count1" value="1">
      <% existPageCombo = true; %>
    </logic:greaterThan>
  </logic:notEmpty>
  <% if (existPageCombo) { %>
  <div class="paging">
    <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select name="prjptl020Form" property="prjptl020pageTop" onchange="buttonPush('init');">
      <html:optionsCollection name="prjptl020Form" property="pageCombo" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  <% } %>
  <table class="table-top w100">
    <tr>
      <th class="no_w table_title-color">
        <gsmsg:write key="cmn.project" />
      </th>
    </tr>
    <logic:notEmpty name="prjptl020Form" property="prjptl020dspList">
      <logic:iterate id="prjdataModel" name="prjptl020Form" property="prjptl020dspList" indexId="idx">
        <tr class="js_listHover" data-sid="<bean:write name="prjdataModel" property="prjSid" />">
          <td class="js_listClick cursor_p">
            <span class="cl_linkDef">
              <bean:write name="prjdataModel" property="prjName" />
            </span>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <% if (existPageCombo) { %>
  <div class="paging">
    <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select name="prjptl020Form" property="prjptl020pageBottom" onchange="changePageBottom();">
      <html:optionsCollection name="prjptl020Form" property="pageCombo" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  <% } %>
</div>

</body>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:html>