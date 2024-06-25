<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../chat/js/chtmain.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/chat/cht020">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<%-- BODY --%>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cht.01"/>
    </li>
    <li>
      <div>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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
      <dt onClick="return buttonPush('cht040');">
        <span class="settingList_title"><gsmsg:write key="cht.cht020.01"/></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="cht.cht020.02" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('cht100');">
        <span class="settingList_title"><gsmsg:write key="cht.cht020.03"/></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="cht.cht020.04" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('cht140');">
        <span class="settingList_title"><gsmsg:write key="cht.cht020.05"/></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="cht.cht020.06" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('cht080');">
        <span class="settingList_title"><gsmsg:write key="cht.cht020.08"/></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="cht.cht020.09"/></div>
      </dd>
    </dl>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>