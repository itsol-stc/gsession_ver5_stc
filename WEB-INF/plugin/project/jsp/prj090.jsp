<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%-- CMD定数 --%>
<%  String back          = jp.groupsession.v2.prj.prj090.Prj090Action.CMD_BACK;
    String edit          = jp.groupsession.v2.prj.prj090.Prj090Action.CMD_EDIT;  %>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.project" /> <gsmsg:write key="cmn.number.display.settings" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/project/prj090">
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

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('<%= edit %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
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
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="project.prj090.1" />
      </th>
      <td class="w75">
        <div><gsmsg:write key="project.prj090.2" /></div>
        <html:select name="prj090Form" property="prj090prjViewCnt">
        <html:optionsCollection name="prj090Form" property="cntList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="project.prj090.3" />
      </th>
      <td>
        <div><gsmsg:write key="project.prj090.4" /></div>
        <html:select name="prj090Form" property="prj090todoViewCnt">
        <html:optionsCollection name="prj090Form" property="cntList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.108" /><gsmsg:write key="cmn.display.settings" />
      </th>
      <td class="w75">
        <gsmsg:write key="project.prj080.8" /><br>
        <div class="verAlignMid">
          <html:radio name="prj090Form" styleId="prj090SchDsp_01" property="prj090SchDsp" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO_SHOW) %>" /><label for="prj090SchDsp_01"><gsmsg:write key="cmn.display.ok" /></label>
          <html:radio name="prj090Form" styleClass="ml10" styleId="prj090SchDsp_02" property="prj090SchDsp" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO_NOT_SHOW) %>" /><label for="prj090SchDsp_02"><gsmsg:write key="cmn.dont.show" /></label>
        </div>
      </td>
    </tr>

  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('<%= edit %>');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
       <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= back %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>