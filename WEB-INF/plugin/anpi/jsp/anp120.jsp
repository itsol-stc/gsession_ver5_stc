<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<% String maxLengthMail = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_MAIL); %>
<% String maxLengthTel = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_TEL); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />

</head>
<body>
  <html:form action="/anpi/anp120">
    <!-- BODY -->
    <input type="hidden" name="CMD">

    <html:hidden property="backScreen" />
    <html:hidden property="anp110SelectUserSid" />
    <html:hidden property="anp110SelectUserNm" />
    <html:hidden property="anp110SortKeyIndex" />
    <html:hidden property="anp110OrderKey" />
    <html:hidden property="anp110NowPage" />
    <html:hidden property="anp110DspPage1" />
    <html:hidden property="anp110DspPage2" />

    <html:hidden property="anp110SelectGroupSid" />
    <html:hidden property="anp110SelectMailFilter" />
    <html:hidden property="anp110SelectTellFilter" />
    <!-- ヘッダー -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp120.01" />
        </li>
        <!-- ボタン -->
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp120excute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp120back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <!-- コンテンツ -->
      <table class="table-left w100">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.name2" />
          </th>
          <td class="w75">
            <bean:write name="anp120Form" property="anp110SelectUserNm" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.mailaddress" />
          </th>
          <td class="w75">
            <html:text name="anp120Form" property="anp120MailAdr" maxlength="<%= maxLengthMail %>" styleClass="wp400" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.tel" />
          </th>
          <td class="w75">
            <html:text name="anp120Form" property="anp120TelNo" maxlength="<%= maxLengthTel %>" styleClass="wp200" />
          </td>
        </tr>
      </table>
      <!-- ボタン -->
      <div class="footerBtn_block">
        <div>
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp120excute');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp120back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>