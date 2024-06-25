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
<bean:define id="ntpEditMode" name="ntp089Form" property="ntp088editMode" type="java.lang.Integer" />

<%
  int editMode = ntpEditMode.intValue();
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="schedule.sch240.01" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
</head>


<html:form action="/nippou/ntp089">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="CMD" value="">

<ntp:conf_hidden name="ntp089Form"/>

<html:hidden property="ntp088keyword" />
<html:hidden property="ntp088pageTop" />
<html:hidden property="ntp088pageBottom" />
<html:hidden property="ntp088pageDspFlg" />
<html:hidden property="ntp088svKeyword" />
<html:hidden property="ntp088sortKey" />
<html:hidden property="ntp088order" />
<html:hidden property="ntp088editData" />
<html:hidden property="ntp088searchFlg" />
<html:hidden property="ntp088editMode" />

<html:hidden property="ntp089initFlg" />

<logic:equal name="ntp089Form" property="ntp088editMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="ntp089Form" property="ntp088editMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>

<logic:notEmpty name="ntp089Form" property="ntp089editUser">
  <logic:iterate id="editUser" name="ntp089Form" property="ntp089editUser">
    <input type="hidden" name="ntp089editUser" value="<bean:write name="editUser"/>">
  </logic:iterate>
</logic:notEmpty>

<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <% if (editMode == 0) { %>
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="schedule.sch240.01" />
      <% } else if (editMode == 1) { %>
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="schedule.sch240.02" />
      <% } %>
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:equal name="ntp089Form" property="ntp088editMode" value="1">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccess');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp089back');">
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
        <gsmsg:write key="schedule.sch240.04" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="ntp089Form" property="ntp089name" maxlength="50" styleClass="w100"/>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch240.05" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <ui:usrgrpselector name="ntp089Form" property="ntp089subjectUI" styleClass="hp215" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.reading.permit" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="cmn.post" />:
          <html:select property="ntp089position">
            <logic:notEmpty name="ntp089Form" property="ntp089positionCombo">
              <html:optionsCollection name="ntp089Form" property="ntp089positionCombo" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
        </div>
        <div class="settingForm_separator">
          <ui:usrgrpselector name="ntp089Form" property="ntp089accessUserUI" styleClass="hp215" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w80">
        <html:textarea name="ntp089Form" property="ntp089biko"  rows="10" styleClass="w100" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="ntp089Form" property="ntp088editMode" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccess');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp089back');">
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