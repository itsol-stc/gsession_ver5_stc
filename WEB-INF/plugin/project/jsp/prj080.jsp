<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>


<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<%-- CMD定数 --%>
<%  String back          = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_BACK;
    String kensuEdit     = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_KENSU_EDIT;
    String tempKojin     = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_TMP_KOJIN;
    String dashBoard     = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_DASH_BOARD;
    String projectMain   = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_PRJ_MAIN;
    String projectSch    = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_PRJ_SCH;
    String todoSet       = jp.groupsession.v2.prj.prj080.Prj080Action.CMD_TODO_SET;
%>
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
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
</head>

<body onload="parent.menu.location='../common/cmn003.do';">

<html:form action="/project/prj080">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="prj010cmdMode" />
<html:hidden property="prj010sort" />
<html:hidden property="prj010order" />
<html:hidden property="prj010page1" />
<html:hidden property="prj010page2" />
<html:hidden property="prj010Init" />
<html:hidden property="selectingProject" />
<html:hidden property="selectingTodoDay" />
<html:hidden property="selectingTodoPrj" />
<html:hidden property="selectingTodoSts" />
<html:hidden property="prjTmpMode" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.project" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= back %>');">
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
      <dt onClick="return buttonPush('<%= tempKojin %>');">
        <span class="settingList_title"><gsmsg:write key="project.prj080.2" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="project.prj080.3" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('<%= kensuEdit %>');">
        <span class="settingList_title"><gsmsg:write key="cmn.display.settings" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="project.prj080.1" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('<%= dashBoard %>');">
        <span class="settingList_title"><gsmsg:write key="cmn.default.setting" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="project.prj080.5" /></div>
      </dd>
    </dl>

  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>