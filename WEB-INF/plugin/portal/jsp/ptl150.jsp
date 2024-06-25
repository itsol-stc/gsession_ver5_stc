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
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../portal/js/ptl150.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="cmn.default.setting" /></title>
</head>

<!-- body -->
<body onload="changeInitArea();">
<html:form action="/portal/ptl150">
<input type="hidden" name="CMD" value="init">
<html:hidden name="ptl150Form" property="ptl150init" />

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
      <gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('doKakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('confMenu');">
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
      <th class="w30 no_w">
        <gsmsg:write key="ptl.18" />
      </th>
      <td class="w70">
        <div class="verAlignMid">
          <html:radio name="ptl150Form" styleClass="mr5" property="ptl150ptlInitKbn" value="0" styleId="ptlInit0" onclick="changeInitArea();" />
          <label for="ptlInit0"><gsmsg:write key="ptl.14" /></label>
          <html:radio name="ptl150Form" styleClass="mr5 ml10" property="ptl150ptlInitKbn" value="1" styleId="ptlInit1" onclick="changeInitArea();" />
          <label for="ptlInit1"><gsmsg:write key="cmn.nolimit" /></label>
        </div>
        <div>
          <gsmsg:write key="ptl.ptl150.1" />
        </div>
      </td>
    </tr>
    <tr id="ptlInitTypeArea0">
      <th class="w30 no_w">
        <gsmsg:write key="ptl.18" /> <gsmsg:write key="ptl.ptl150.3" />
      </th>
      <td class="w70" id="ptlInitTypeArea1">
        <div class="verAlignMid">
          <html:radio name="ptl150Form" styleClass="mr5" property="ptl150ptlInitType" value="0" styleId="ptlInitType0" />
          <label for="ptlInitType0"><gsmsg:write key="ptl.19" /></label>
          <html:radio name="ptl150Form" styleClass="mr5 ml10" property="ptl150ptlInitType" value="1" styleId="ptlInitType1" />
          <label for="ptlInitType1"><gsmsg:write key="ptl.20" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('doKakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('confMenu');">
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