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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/schptl020.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="sch.ptl020.1" /></title>
</head>
<!-- BODY -->
<body onload="closeWindow();">
<html:form action="/schedule/schptl020">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="schptl020selectGrpSid" />
<html:hidden property="schptl020selectFlg" />


<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
      <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />"></li></li>
    <li>
      <gsmsg:write key="schedule.108" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="sch.ptl020.1" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w90 mrl_auto">
  <div class="mt10 txt_l">
    <gsmsg:write key="sch.ptl020.2" />
  </div>
  <table class="table-left">
    <tr>
      <th class="w30">
        <gsmsg:write key="ptl.21" />
      </td>
      <td class="w70">
        <logic:notEmpty name="schptl020Form" property="schPtl020PluginPortletList">
          <html:select property="ptl080PluginPortlet" onchange="buttonPush('schChangeCombo');">
            <html:optionsCollection property="schPtl020PluginPortletList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <table class="table-top">
    <tr>
      <th class="no_w table_title-color">
        <gsmsg:write key="cmn.group" />
      </th>
    </tr>
    <logic:notEmpty name="schptl020Form" property="schptl020dspList">
      <logic:iterate id="grpModel" name="schptl020Form" property="schptl020dspList" indexId="idx">
        <tr class="js_listHover cursor_p" id="<bean:write name="grpModel" property="groupSid" />">
          <td class="js_listClick cl_linkDef">
            <bean:write name="grpModel" property="groupName" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
</body>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:html>