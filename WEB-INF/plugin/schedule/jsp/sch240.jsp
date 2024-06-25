<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%-- 定数値 --%>
<bean:define id="schEditMode" name="sch240Form" property="sch230editMode" type="java.lang.Integer" />
<%
  int editMode = schEditMode.intValue();
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch240.01" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../schedule/js/sch240.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>
<body>
<html:form action="/schedule/sch240">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/schedule/jsp/sch080_hiddenParams.jsp" %>
<logic:notEmpty name="sch240Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch240Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch240Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch240Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch240Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch240Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="sch230keyword" />
<html:hidden property="sch230pageTop" />
<html:hidden property="sch230pageBottom" />
<html:hidden property="sch230pageDspFlg" />
<html:hidden property="sch230svKeyword" />
<html:hidden property="sch230sortKey" />
<html:hidden property="sch230order" />
<html:hidden property="sch230editData" />
<html:hidden property="sch230searchFlg" />
<html:hidden property="sch230editMode" />
<html:hidden property="sch240initFlg" />
<logic:equal name="sch240Form" property="sch230editMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="sch240Form" property="sch230editMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <% if (editMode == 0) { %>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="schedule.sch240.01" />
      </li>
    <% } else if (editMode == 1) { %>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="schedule.sch240.02" />
      </li>
    <% } %>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
          <gsmsg:write key="cmn.ok" />
        </button>
        <% if (editMode == 1) { %>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccess');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
          </button>
         <% } %>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <!-- 特例アクセス名 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.sch240.04" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="sch240Form" property="sch240name" maxlength="50" styleClass="wp230"/>
      </td>
    </tr>
    <!-- 制限対象 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.sch240.05" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <ui:multiselector name="sch240Form" property="sch240subjectUI" styleClass="hp200" />
      </td>
    </tr>
    <!-- アクセス許可 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.sch240.07" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <div class="hp25 verAlignMid">
          <span class="verAlignMid">
            <span class="fw_b"><gsmsg:write key="cmn.post" /></span>：
            <html:select property="sch240position">
              <logic:notEmpty name="sch240Form" property="sch240positionCombo">
                <html:optionsCollection name="sch240Form" property="sch240positionCombo" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </span>
          <span class="ml20 verAlignMid" id="positionAuthArea">
            <html:radio property="sch240positionAuth" value="0" styleId="positionAuth0" />
            <label for="positionAuth0"><gsmsg:write key="cmn.add.edit.delete" /></label>
            <html:radio styleClass="ml10" property="sch240positionAuth" value="1" styleId="positionAuth1" />
            <label for="positionAuth1"><gsmsg:write key="cmn.reading" /></label>
          </span>
        </div>
        <div class="settingForm_separator">
          <ui:multiselector name="sch240Form" property="sch240accessUI" styleClass="hp400" />
        </div>
      </td>
    </tr>
    <!-- 備考 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <html:textarea name="sch240Form" property="sch240biko"  rows="5" styleClass="wp600" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
      <gsmsg:write key="cmn.ok" />
    </button>
    <% if (editMode == 1) { %>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccess');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
      </button>
     <% } %>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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