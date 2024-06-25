<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String crtKbnAdmin = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_ADMIN);
   String crtKbnGroup = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_GROUP);
   String crtKbnUser = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_USER);
   String crtKbnNo = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_NO);
   String lockKbnOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.LOCK_KBN_OFF);
   String lockKbnOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.LOCK_KBN_ON);
   String verKbnOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_OFF);
   String verKbnOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_ON);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" />　<gsmsg:write key="cmn.preferences" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../file/js/fil210.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="viewchange();">

<html:form action="/file/fil210">
<input type="hidden" name="CMD" value="">
<html:hidden property="filSearchWd" />
<html:hidden property="backDsp" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />

<logic:notEmpty name="fil210Form" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil210Form" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil210Form" property="fil010SelectDelLink" scope="request">
  <logic:iterate id="del" name="fil210Form" property="fil010SelectDelLink" scope="request">
    <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="backScreen" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('fil210ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil210back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>


<div class="wrapper w80 mrl_auto">

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="fil.28" />
      </th>
      <td class="w80">
        <div id="editselect">
          <span class="verAlignMid">
            <html:radio name="fil210Form" styleId="view0" onclick="viewchange();" property="fil210CrtKbn" value="<%= crtKbnAdmin %>" /><label for="view0"><span class="text_base"><gsmsg:write key="fil.fil210.1" /></span></label>
            <html:radio styleClass="ml10" name="fil210Form" styleId="view1" onclick="viewchange();" property="fil210CrtKbn" value="<%= crtKbnGroup %>" /><label for="view1"><span class="text_base"><gsmsg:write key="group.designation" /></span></label>
            <html:radio styleClass="ml10" name="fil210Form" styleId="view2" onclick="viewchange();" property="fil210CrtKbn" value="<%= crtKbnUser %>" /><label for="view2"><span class="text_base"><gsmsg:write key="cmn.user.specified" /></span></label>
          </span>
        </div>

        <!-- 管理者のみ -->
        <div id="viewadmin" class="mt5">
          <gsmsg:write key="fil.29" />
        </div>

        <!-- グループ指定 -->
        <span id="viewgroup">
          <ui:usrgrpselector name="fil210Form" property="fil210GroupUI" styleClass="hp215" />
        </span>

        <span id="viewuser">
          <ui:usrgrpselector name="fil210Form" property="fil210UserUI" styleClass="hp215" />
        </span>

        <!-- 権限なし -->
        <div id="viewall">
          <gsmsg:write key="fil.30" />
        </div>

      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.31" />
      </th>
      <td>
        <gsmsg:write key="fil.32" /><br>
        <logic:notEmpty name="fil210Form" property="fil210FileSizeList">
        <html:select property="fil210FileSize">
        <html:optionsCollection name="fil210Form" property="fil210FileSizeList" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.fil210.3" />
      </th>
      <td>
        <gsmsg:write key="fil.33" /><br>
        <logic:notEmpty name="fil210Form" property="fil210SaveDaysList">
        <html:select property="fil210SaveDays">
          <html:optionsCollection name="fil210Form" property="fil210SaveDaysList" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
        <br>
        <gsmsg:write key="cmn.comments" /><gsmsg:write key="fil.fil210.5" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.34" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="fil210Form" styleId="lockKbn_01" property="fil210LockKbn" value="<%= lockKbnOff %>" /><label for="lockKbn_01"><gsmsg:write key="fil.107" /></label>
          <html:radio styleClass="ml10" name="fil210Form" styleId="lockKbn_02" property="fil210LockKbn" value="<%= lockKbnOn %>" /><label for="lockKbn_02"><gsmsg:write key="fil.108" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.69" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="fil210Form" styleId="verKbn_01" property="fil210VerKbn" value="<%= verKbnOff %>" /><label for="verKbn_01"><gsmsg:write key="fil.107" /></label>
          <html:radio styleClass="ml10" name="fil210Form" styleId="verKbn_02" property="fil210VerKbn" value="<%= verKbnOn %>" /><label for="verKbn_02"><gsmsg:write key="fil.108" /></label>
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('fil210ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil210back');">
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