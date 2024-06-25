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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="tcd.50" /> <gsmsg:write key="cmn.admin.setting" />[<gsmsg:write key="tcd.48" />]</title>
</head>
<body class="body_03">
<html:form action="/timecard/tcd030" >

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="tcdBackScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<logic:notEmpty name="tcd030Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd030Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="tcd.50" />
    </li>
    <li>
      <div>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back')">
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
    <logic:equal name="tcd030Form" property="menuLevel" value="2">
      <dl>
        <dt onClick="return buttonPush('base_conf');">
          <span class="settingList_title"><gsmsg:write key="cmn.preferences" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.02" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('mng');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd010.13" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.04" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('timezone');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd030.07" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.08" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('outputKinmu');">
          <span class="settingList_title"><gsmsg:write key="tcd.55" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.06" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('userTimezone');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd030.09" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.10" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('yuukyuList');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd030.13" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.14" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('holidayKbn');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd030.11" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.12" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('formatInf');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd030.15" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.16" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('yukyuKeikoku');">
          <span class="settingList_title"><gsmsg:write key="tcd.tcd030.17" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.18" /></div>
        </dd>
      </dl>
      <dl>
        <dt onClick="return buttonPush('editAuth');">
          <span class="settingList_title"><gsmsg:write key="tcd.06" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="tcd.tcd030.05" /></div>
        </dd>
      </dl>
    </logic:equal>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>