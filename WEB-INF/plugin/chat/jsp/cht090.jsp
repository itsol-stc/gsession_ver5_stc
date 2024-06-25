<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%-- 定数値 --%>
<bean:define id="chtEditMode" name="cht090Form" property="cht080editMode" type="java.lang.Integer" />

<%
  int editMode = chtEditMode.intValue();
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.cht090.01" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>

</head>

<body onunload="windowClose();">

<html:form action="/chat/cht090">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="cht080keyword" />
<html:hidden property="cht080pageTop" />
<html:hidden property="cht080pageBottom" />
<html:hidden property="cht080pageDspFlg" />
<html:hidden property="cht080svKeyword" />
<html:hidden property="cht080sortKey" />
<html:hidden property="cht080order" />
<html:hidden property="cht080editData" />
<html:hidden property="cht080searchFlg" />
<html:hidden property="cht080editMode" />

<html:hidden property="cht090initFlg" />

<logic:equal name="cht090Form" property="cht080editMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="cht090Form" property="cht080editMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><% if (editMode == 0) { %><gsmsg:write key="cht.cht090.01" /><% } else if (editMode == 1) { %><gsmsg:write key="cht.cht090.02" /><% } %>
    </li>
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
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cht090back');">
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
      <th class="w20 no_w">
        <gsmsg:write key="cht.cht090.03" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="cht090Form" property="cht090name" maxlength="50" styleClass="wp230"/>
      </td>
    </tr>
    <!-- 制限対象 -->
    <tr>
      <th class="no_w">
        <gsmsg:write key="cht.cht090.04" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <ui:usrgrpselector name="cht090Form" property="cht090subjectUI" styleClass="hp215" />
      </td>
    </tr>
    <!-- アクセス許可 -->
    <tr>
      <th class="no_w">
        <gsmsg:write key="cht.cht090.06" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <div class="hp25 verAlignMid">
          <span class="verAlignMid">
            <span class="fw_b"><gsmsg:write key="cmn.post" /></span>：
            <html:select property="cht090position" styleClass="wp300">
              <logic:notEmpty name="cht090Form" property="cht090positionCombo">
                <html:optionsCollection name="cht090Form" property="cht090positionCombo" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </span>
        </div>
        <div class="settingForm_separator">
          <ui:usrgrpselector name="cht090Form" property="cht090accessUserUI" styleClass="hp215" />
        </div>
      </td>
    </tr>
    <!-- 備考 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <html:textarea name="cht090Form" property="cht090biko"  rows="5" styleClass="wp600" />
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cht090back');">
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
