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
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="zsk.zsk020.03" /></title>
</head>

<body>
<html:form action="/zaiseki/zsk020">
<input type="hidden" name="CMD">
<html:hidden name="zsk020Form" property="backScreen" />
<html:hidden name="zsk020Form" property="selectZifSid" />
<html:hidden name="zsk020Form" property="uioStatus" />
<html:hidden name="zsk020Form" property="uioStatusBiko" />
<html:hidden name="zsk020Form" property="sortKey" />
<html:hidden name="zsk020Form" property="orderKey" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--　BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.zaiseki.management" />
    </li>
    <li>
      <div>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk010');">
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
      <dt onClick="buttonPush('zsk030');">
        <span class="settingList_title"><gsmsg:write key="zsk.26" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="zsk.zsk020.02" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('zsk110');">
        <span class="settingList_title"><gsmsg:write key="cmn.preferences" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="zsk.zsk020.01" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('zsk140');">
        <span class="settingList_title"><gsmsg:write key="cmn.default.setting" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="zsk.zsk020.04" /></div>
      </dd>
    </dl>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>