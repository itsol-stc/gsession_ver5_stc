<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%-- 定数値 --%>
<bean:define id="schEditMode" name="sch260Form" property="sch250editMode" type="java.lang.Integer" />

<%
  int editMode = schEditMode.intValue();
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="schedule.sch260.01" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch260.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>


<html:form action="/schedule/sch260">
<input type="hidden" name="CMD" value="">
<html:hidden property="sch250editData" />
<html:hidden property="sch250editMode" />
<html:hidden property="sch260initFlg" />
<%@ include file="/WEB-INF/plugin/schedule/jsp/sch080_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<logic:equal name="sch260Form" property="sch250editMode" value="0">
  <input type="hidden" name="helpPrm" value="0add">
</logic:equal>
<logic:notEqual name="sch260Form" property="sch250editMode" value="0">
  <input type="hidden" name="helpPrm" value="1edit">
</logic:notEqual>

<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.preferences2" />
    </li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="schedule.sch260.01" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:equal name="sch260Form" property="sch250editMode" value="<%= String.valueOf(GSConst.CMDMODE_EDIT) %>" >
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('sch260delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch260back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.name5" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="sch260Form" property="sch260scheduleName" maxlength="50" styleClass="w100"/>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch260.03" />
      </th>
      <td class="w80">
        <ui:sortingselector name="sch260Form" property="sch260subjectUI" styleClass="hp200"
        onchange="" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w80">
        <html:textarea name="sch260Form" property="sch260biko"  rows="10" styleClass="w100" onkeyup="showLengthStr(value, 1000, 'inputlength');" styleId="inputstr" />
        <div>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter"><logic:notEmpty name="sch260Form" property="sch260biko"><bean:define id="biko" name="sch260Form" property="sch260biko" type="java.lang.String"/><%= biko.length() %></logic:notEmpty><logic:empty name="sch260Form" property="sch260biko">0</logic:empty></span>&nbsp;/&nbsp;<span class="formCounter_max">1000&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="sch260Form" property="sch250editMode" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('sch260delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch260back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>