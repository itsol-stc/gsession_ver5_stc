<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp130.js?aa<%= GSConst.VERSION_PARAM %>"></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp130">

<input type="hidden" name="CMD" value="">

<!-- ˆÄŒ“o˜^‰æ–Ê‚©‚ç‘JˆÚ -->
<logic:equal name="ntp130Form" property="ntp130ReturnPage" value="ntp061">
  <%@ include file="/WEB-INF/plugin/nippou/jsp/ntp061_hiddenParams.jsp" %>
  <logic:notEmpty name="ntp130Form" property="ntp130SvChkShohinSidList" scope="request">
    <logic:iterate id="item" name="ntp130Form" property="ntp130SvChkShohinSidList" scope="request">
      <input type="hidden" name="ntp130SvChkShohinSidList" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>
  <logic:notEmpty name="ntp130Form" property="ntp130SelectedSid" scope="request">
    <logic:iterate id="item" name="ntp130Form" property="ntp130SelectedSid" scope="request">
      <input type="hidden" name="ntp130ChkShohinSidList" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <%@ include file="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp" %>
  <logic:notEmpty name="ntp130Form" property="ntp060Mikomi" scope="request">
    <logic:iterate id="mikomi" name="ntp130Form" property="ntp060Mikomi" scope="request">
      <input type="hidden" name="ntp060Mikomi" value="<bean:write name="mikomi"/>">
    </logic:iterate>
  </logic:notEmpty>
  <logic:notEmpty name="ntp130Form" property="ntp060Syodan" scope="request">
    <logic:iterate id="syodan" name="ntp130Form" property="ntp060Syodan" scope="request">
      <input type="hidden" name="ntp060Syodan" value="<bean:write name="syodan"/>">
    </logic:iterate>
  </logic:notEmpty>
</logic:equal>

<html:hidden property="ntp130NhnSid" />
<html:hidden property="ntp130ProcMode" />
<html:hidden property="ntp130ReturnPage" />
<html:hidden property="ntp130DspMode" />
<html:hidden property="ntp130InitFlg" />
<html:hidden property="ntp130DspKbn" />
<html:hidden property="ntp130EditSid" />

<!--@BODY -->

<logic:equal name="ntp130Form" property="ntp130DspKbn" value="0">
  <%@ include file="/WEB-INF/plugin/nippou/jsp/ntp130_shohin.jsp" %>
</logic:equal>

<logic:equal name="ntp130Form" property="ntp130DspKbn" value="1">
  <%@ include file="/WEB-INF/plugin/nippou/jsp/ntp130_category.jsp" %>
</logic:equal>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>