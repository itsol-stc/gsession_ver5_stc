<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl160.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<title>GROUPSESSION <gsmsg:write key="cmn.cmn110.1" /></title>
</head>

<body onload="checkStatus();">

<div id="FreezePane">
<div id="InnerFreezePane"> </div>


<html:form action="portal/ptl160" enctype="multipart/form-data">

<input type="hidden" name="CMD">

<html:hidden property="ptlCmdMode" />
<html:hidden property="ptlPortletSid" />
<html:hidden property="ptlPortletImageSid" />

<html:hidden property="ptl160tempName" />
<html:hidden property="ptl160tempSaveName" />
<html:hidden property="ptl160Decision" />
<html:hidden property="splitStr" />

<!--BODY -->

<div id="FreezePane" class="ofx_h">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <div class="tempFileSelect_title bgC_header2">
    <logic:notEqual name="ptl160Form" property="strMaxSize" value="0">
      <gsmsg:write key="cmn.cmn110.2" />
    </logic:notEqual>
    <logic:equal name="ptl160Form" property="strMaxSize" value="0">
      <gsmsg:write key="cmn.cmn110.3" />
    </logic:equal>
  </div>
  <logic:notEqual name="ptl160Form" property="strMaxSize" value="0">
    <div class="mt5 w100 verAlignMid">
      <html:file property="ptl160file" styleClass="w80" size="25" maxlength="50" />
      <span class="w20 txt_r">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.attached" />" onClick="ptlImageEntry();freezeScreenThis('<gsmsg:write key="cmn.cmn110.4" />');">
          <img class="header_pluginImg-classic" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
          <img class="header_pluginImg" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
          <gsmsg:write key="cmn.attached" />
        </button>
      </span>
    </div>
    <div class="textError fs_12">
      <bean:define id="filMaxSize" name="ptl160Form" property="strMaxSize" type="java.lang.String" />
      <gsmsg:write key="cmn.cmn110.5" arg0="<%= filMaxSize %>" />
    </div>
  </logic:notEqual>
  <div class="mt5 txt_c">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.cmn110.7" />" onClick="ptlImagePopupClose();">
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.cmn110.7" />">
      <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.cmn110.7" />">
      <gsmsg:write key="cmn.cmn110.7" />
    </button>
  </div>
</div>
</html:form>
</div>
</body>
</html:html>