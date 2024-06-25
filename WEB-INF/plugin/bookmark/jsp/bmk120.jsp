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
<title>GROUPSESSION <gsmsg:write key="bmk.43" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bookmark/js/bmk120.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/bookmark/bmk120">

<input type="hidden" name="CMD" value="">
<html:hidden name="bmk120Form" property="backScreen" />

<logic:notEmpty name="bmk120Form" property="bmk010delInfSid" scope="request">
<logic:iterate id="item" name="bmk120Form" property="bmk010delInfSid" scope="request">
  <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp" %>

<!--　BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="bmk.43" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('bmk120back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="settingList">
    <dl>
      <dt onClick="return buttonPush('countedit');">
        <span class="settingList_title"><gsmsg:write key="cmn.display.settings" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="bmk.41" /></div>
      </dd>
    </dl>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>