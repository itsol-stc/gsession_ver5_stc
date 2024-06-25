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
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl060.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<title>GROUPSESSION <gsmsg:write key="ptl.1" /> <gsmsg:write key="ptl.ptl060.1" /></title>
</head>

<!-- body -->
<body onload="initChgArea();">
<html:form action="/portal/ptl060">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl030sortPortal" />
<html:hidden property="ptl060init" />
<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="ptl.1" /></span><gsmsg:write key="ptl.ptl060.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush2('ptl060edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ptl060back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <div class="verAlignMid flo_l">
    <html:checkbox styleId="portalTopId" name="ptl060Form" property="ptl060area1" value="1" onclick="chgArea('portalTopId', 'Top');" />
    <label for="portalTopId"><gsmsg:write key="ptl.ptl060.2" /></label>
    <html:checkbox styleClass="ml10" styleId="portalLeftId" name="ptl060Form" property="ptl060area3" value="1" onclick="chgArea('portalLeftId', 'Left');" />
    <label for="portalLeftId"><gsmsg:write key="cmn.left" /></label>
    <html:checkbox styleClass="ml10" styleId="portalCenterId" name="ptl060Form" property="ptl060area4" value="1" onclick="chgArea('portalCenterId', 'Center');" />
    <label for="portalCenterId"><gsmsg:write key="ptl.ptl060.3" /></label>
    <html:checkbox styleClass="ml10" styleId="portalRightId" name="ptl060Form" property="ptl060area5" value="1" onclick="chgArea('portalRightId', 'Right');" />
    <label for="portalRightId"><gsmsg:write key="cmn.right" /></label>
    <html:checkbox styleClass="ml10" styleId="portalBottomId" name="ptl060Form" property="ptl060area2" value="1" onclick="chgArea('portalBottomId', 'Bottom');" />
    <label for="portalBottomId"><gsmsg:write key="ptl.ptl060.4" /></label>
  </div>
  <table class="w100">
    <tr>
      <td class="w100 bor3 hp90 p0" id="mainScreenListTop">
      </td>
    </tr>
    <tr>
      <td class="pt10" id="portal_space_Top">
      </td>
    </tr>
    <tr>
      <td>
        <table class="w100">
          <tr>
            <td id="mainScreenListLeft" class="hp150 bor3">
            </td>
            <td class="pl10" id="left_space">
            </td>
            <td id="mainScreenListCenter" class="hp150 bor3">
            </td>
            <td class="pl10" id="right_space">
            </td>
            <td id="mainScreenListRight" class="hp150 bor3">
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td class="pt10" id="portal_space_Bottom">
      </td>
    </tr>
    <tr>
      <td class="w100 bor3 hp90 p0" id="mainScreenListBottom">
      </td>
    </tr>
  </table>
</div>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>

</html:html>