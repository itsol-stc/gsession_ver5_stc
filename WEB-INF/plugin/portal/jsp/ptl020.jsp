<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="ptl.13" /></title>
</head>

<body>
<html:form action="/portal/ptl020">
<input type="hidden" name="CMD" value="init">
<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--BODY -->
<div  class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.1" />
    </li>
    <li>
      <button value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backList')">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="settingList">
    <dl>
      <dt onClick="buttonPush2('ptlManager');">
        <span class="settingList_title">
          <gsmsg:write key="ptl.2" />
        </span>
      </dt>
      <dd>
        <div class="settingList_text">
          <gsmsg:write key="ptl.ptl020.1" />
        </div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush2('pletManager');">
        <span class="settingList_title">
          <gsmsg:write key="ptl.9" />
        </span>
      </dt>
      <dd>
        <div class="settingList_text">
          <gsmsg:write key="ptl.ptl020.2" />
        </div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush2('pletInitValue');">
        <span class="settingList_title">
          <gsmsg:write key="cmn.default.setting" />
        </span>
      </dt>
      <dd>
        <div class="settingList_text">
          <gsmsg:write key="ptl.ptl020.3" />
        </div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush2('powManager');">
        <span class="settingList_title">
          <gsmsg:write key="cmn.setting.permissions" />
        </span>
      </dt>
      <dd>
        <div class="settingList_text">
          <gsmsg:write key="ptl.ptl020.4" />
        </div>
      </dd>
    </dl>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>