<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="bmk.43" /> <gsmsg:write key="cmn.setting.permissions" /></title>
</head>

<body>
<html:form action="/bookmark/bmk110">

<input type="hidden" name="CMD" value="">
<html:hidden name="bmk110Form" property="backScreen" />
<%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp" %>

<html:hidden name="bmk110Form" property="bmk110initFlg" />

<logic:notEqual name="bmk110Form" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER) %>">
  <html:hidden name="bmk110Form" property="bmk110GroupSid" />
  <logic:notEmpty name="bmk110Form" property="bmk110UserSid">
  <logic:iterate id="usid" name="bmk110Form" property="bmk110UserSid">
    <input type="hidden" name="bmk110UserSid" value="<bean:write name="usid" />">
  </logic:iterate>
  </logic:notEmpty>
</logic:notEqual>

<logic:notEqual name="bmk110Form" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP) %>">
  <logic:notEmpty name="bmk110Form" property="bmk110GrpSid">
  <logic:iterate id="gsid" name="bmk110Form" property="bmk110GrpSid">
    <input type="hidden" name="bmk110GrpSid" value="<bean:write name="gsid" />">
  </logic:iterate>
  </logic:notEmpty>
</logic:notEqual>

<logic:notEmpty name="bmk110Form" property="bmk010delInfSid" scope="request">
<logic:iterate id="item" name="bmk110Form" property="bmk010delInfSid" scope="request">
  <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="bmk.43" /></span><gsmsg:write key="cmn.setting.permissions" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('bmk110kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk110back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">

<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

  <table class="table-left">
  <!-- 共有ブックマーク編集権限 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="bmk.34" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio name="bmk110Form" styleId="bmk110PubEditKbn_01" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ADMIN) %>" onclick="buttonPush('chgPubEditKbn');" /><label for="bmk110PubEditKbn_01"><gsmsg:write key="cmn.only.admin.editable" /></label>
        <html:radio name="bmk110Form" styleClass="ml10" styleId="bmk110PubEditKbn_02" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP) %>" onclick="buttonPush('chgPubEditKbn');" /><label for="bmk110PubEditKbn_02"><gsmsg:write key="group.designation" /></label>
        <html:radio name="bmk110Form" styleClass="ml10" styleId="bmk110PubEditKbn_03" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER) %>" onclick="buttonPush('chgPubEditKbn');" /><label for="bmk110PubEditKbn_03"><gsmsg:write key="cmn.user.specified" /></label>
        <html:radio name="bmk110Form" styleClass="ml10" styleId="bmk110PubEditKbn_04" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ALL) %>" onclick="buttonPush('chgPubEditKbn');" /><label for="bmk110PubEditKbn_04"><gsmsg:write key="bmk.33" /></label>
      </div>
      <div class="mt5">
      <logic:equal name="bmk110Form" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ADMIN) %>">
        <div><gsmsg:write key="bmk.bmk110.02" /></div>
      </logic:equal>
      <logic:equal name="bmk110Form" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ALL) %>">
        <div><gsmsg:write key="bmk.bmk110.01" /></div>
      </logic:equal>

      <!-- 共有ブックマーク編集権限：グループ指定 -->
      <logic:equal name="bmk110Form" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP) %>">
        <ui:usrgrpselector name="bmk110Form" property="bmk110GrpSidUI" styleClass="hp215" />
      </logic:equal>

      <!-- 共有ブックマーク編集権限：ユーザ指定 -->
      <logic:equal name="bmk110Form" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER) %>">
        <ui:usrgrpselector name="bmk110Form" property="bmk110UserSidUI" styleClass="hp215" />
      </logic:equal>
      </div>
      </td>
    </tr>
    <!-- グループブックマーク編集権限 -->
    <tr>
      <th>
        <gsmsg:write key="bmk.51" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="bmk110Form" styleId="bmk110GrpEditKbn_01" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_ADMIN) %>" onclick="buttonPush('chgGrpEditKbn');" /><label for="bmk110GrpEditKbn_01"><gsmsg:write key="cmn.only.admin.editable" /></label>
          <html:radio name="bmk110Form" styleClass="ml10" styleId="bmk110GrpEditKbn_02" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_GROUP) %>" onclick="buttonPush('chgGrpEditKbn');" /><label for="bmk110GrpEditKbn_02"><gsmsg:write key="bmk.48" /></label>
          <html:radio name="bmk110Form" styleClass="ml10" styleId="bmk110GrpEditKbn_03" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_ALL) %>" onclick="buttonPush('chgGrpEditKbn');" /><label for="bmk110GrpEditKbn_03"><gsmsg:write key="bmk.33" /></label>
        </div>
        <div class="mt5">
          <logic:equal name="bmk110Form" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_ADMIN) %>"><gsmsg:write key="bmk.bmk110.03" /></logic:equal>
          <logic:equal name="bmk110Form" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_GROUP) %>"><gsmsg:write key="bmk.bmk110.04" /></logic:equal>
          <logic:equal name="bmk110Form" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_ALL) %>"><gsmsg:write key="bmk.19" /></logic:equal>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('bmk110kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk110back');">
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