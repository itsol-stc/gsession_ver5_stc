<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
    String maxLengthDescription = String.valueOf(jp.groupsession.v2.man.GSConstPortal.MAX_LENGTH_PTL_DESCRIPTION);
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl050.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="ptl.ptl050.1" /></title>
</head>

<!-- body -->
<body onload="showOrHide();showLengthId($('#inputstr')[0], 1000, 'inputlength');">
<html:form action="/portal/ptl050">
<input type="hidden" name="CMD" value="init">

<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl030sortPortal" />
<html:hidden property="ptl050init" />
<html:hidden property="ptlCmdMode" />

<bean:define id="ptlSid" name="ptl050Form" property="ptlPortalSid" type="java.lang.Integer"/>
<% if (ptlSid > -1) {%>
<input type="hidden" name="helpPrm" value="1">
<% } else { %>
<input type="hidden" name="helpPrm" value="0">
<% } %>

<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.ptl050.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush2('ptl050ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:greaterThan name="ptl050Form" property="ptlPortalSid" value="0">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush2('ptl050delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:greaterThan>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ptl050back');">
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
  <table class="table-left w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="ptl.4" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="ptl050Form" property="ptl050name" size="40" maxlength="100" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.public.kbn" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ptl050Form" styleClass="mr5" styleId="ptl050open_0" property="ptl050openKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_OPENKBN_OK) %>" />
          <label for="ptl050open_0"><gsmsg:write key="cmn.show" /></label>
          <html:radio name="ptl050Form" styleClass="mr5 ml10" styleId="ptl050open_1" property="ptl050openKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_OPENKBN_NG) %>" />
          <label for="ptl050open_1"><gsmsg:write key="cmn.hide" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w80">
        <textarea name="ptl050description" class="w100" rows="10" onkeyup="showLengthStr(value, <%= maxLengthDescription %>, 'inputlength');" id="inputstr"><bean:write name="ptl050Form" property="ptl050description" /></textarea>
        <div class="txt_r">
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;/&nbsp;<span class="formCounter_max"><%= maxLengthDescription %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.access.auth" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ptl050Form" styleClass="mr5" onclick="showOrHide();" styleId="ptl050access_0" property="ptl050accessKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_ACCESS_OFF) %>" />
          <label for="ptl050access_0"><gsmsg:write key="cmn.not.limit" /></label>
          <html:radio name="ptl050Form" styleClass="mr5 ml10" onclick="showOrHide();" styleId="ptl050access_1" property="ptl050accessKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_ACCESS_ON) %>" />
          <label for="ptl050access_1"><gsmsg:write key="cmn.do.limit" /></label>
        </div>
        <span id="userCombo">
          <ui:usrgrpselector name="ptl050Form" property="ptl050memberSidUI" styleClass="hp215" />
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush2('ptl050ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:greaterThan name="ptl050Form" property="ptlPortalSid" value="0">
      <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush2('ptl050delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:greaterThan>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ptl050back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>


</body>

</html:html>