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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zskptl020.js?<%= GSConst.VERSION_PARAM %>"></script>

<title>GROUPSESSION <gsmsg:write key="zsk.ptl020.1" /></title>
</head>

<!-- BODY -->
<body onload="closeWindow();">

<html:form action="/zaiseki/zskptl020">

<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />

<html:hidden property="zskptl020selectGrpSid" />
<html:hidden property="zskptl020selectFlg" />


<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../zaiseki/images/classic/header_zaiseki.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
      <img class="header_pluginImg" src="../zaiseki/images/original/header_zaiseki.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
    </li>
    <li>
      <gsmsg:write key="cmn.zaiseki.management" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="zsk.ptl020.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <div class="mt10 txt_l">
    <gsmsg:write key="zsk.ptl020.2" />
  </div>
  <table class="table-left">
    <tr>
      <th class="w30">
        <gsmsg:write key="ptl.21" />
      </th>
      <td class="w70">
        <logic:notEmpty name="zskptl020Form" property="zskPtl020PluginPortletList">
          <html:select property="ptl080PluginPortlet" onchange="buttonPush('zskChangeCombo');">
            <html:optionsCollection property="zskPtl020PluginPortletList" value="value" label="label" />
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
    <logic:notEmpty name="zskptl020Form" property="zskptl020dspList">
      <logic:iterate id="grpModel" name="zskptl020Form" property="zskptl020dspList" indexId="idx">
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