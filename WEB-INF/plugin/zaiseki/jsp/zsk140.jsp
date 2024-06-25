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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../zaiseki/js/zsk140.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /> <gsmsg:write key="zsk.74" /></title>
</head>

<body onload="sortEnableDisable();">
<html:form action="/zaiseki/zsk140">
<input type="hidden" name="CMD" value="">
<html:hidden name="zsk140Form" property="backScreen" />
<html:hidden name="zsk140Form" property="selectZifSid" />
<html:hidden name="zsk140Form" property="uioStatus" />
<html:hidden name="zsk140Form" property="uioStatusBiko" />
<html:hidden name="zsk140Form" property="sortKey" />
<html:hidden name="zsk140Form" property="orderKey" />

<html:hidden name="zsk140Form" property="zsk140initKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('zsk140kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk140back');">
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
        <gsmsg:write key="cmn.sort" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio name="zsk140Form" styleId="zsk140SortKbn_01" property="zsk140SortKbn" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.ADM_SORTKBN_ADM) %>" onclick="sortEnableDisable();" /><label for="zsk140SortKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
        <html:radio name="zsk140Form" styleClass="ml10" styleId="zsk140SortKbn_02" property="zsk140SortKbn" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.ADM_SORTKBN_PRI) %>" onclick="sortEnableDisable();" /><label for="zsk140SortKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
      </div>
      <br>
      <span id="lmtText"><gsmsg:write key="cmn.view.user.defaultset" /><br></span>
      <div class="verAlignMid">
        <gsmsg:write key="cmn.first.key" />：

        <!-- キー1 -->
        <html:select property="zsk140SortKey1" styleClass="mr10">
          <html:optionsCollection name="zsk140Form" property="zsk140SortKeyLabel" value="value" label="label" />
        </html:select>
        <html:radio name="zsk140Form" property="zsk140SortOrder1" styleId="zsk140SortOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="zsk140SortOrder10"><gsmsg:write key="cmn.order.asc" /></label>
        <html:radio name="zsk140Form" styleClass="ml10" property="zsk140SortOrder1" styleId="zsk140SortOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="zsk140SortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
      </div>
      <br>
      <div class="verAlignMid mt5">
        <gsmsg:write key="cmn.second.key" /><gsmsg:write key="wml.215" />
        <!-- キー2 -->
        <html:select property="zsk140SortKey2" styleClass="mr10">
          <html:optionsCollection name="zsk140Form" property="zsk140SortKeyLabel" value="value" label="label" />
        </html:select>
        <html:radio name="zsk140Form" property="zsk140SortOrder2" styleId="zsk140SortOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="zsk140SortOrder20"><gsmsg:write key="cmn.order.asc" /></label>
        <html:radio name="zsk140Form" styleClass="ml10" property="zsk140SortOrder2" styleId="zsk140SortOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="zsk140SortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
      </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('zsk140kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk140back');">
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