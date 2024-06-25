<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<% String maxLengthTitle   = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SUBJECT); %>
<% String maxLengthSubject = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SUBJECT); %>
<% String maxLengthText1   = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_TEXT1); %>
<% String maxLengthText2   = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_TEXT2); %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body onload="showLengthId($('#inputstr1')[0],<%=maxLengthText1 %>,'inputlength1');showLengthId($('#inputstr2')[0],<%=maxLengthText2 %>,'inputlength2');">
  <html:form action="/anpi/anp100">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp090SelectSid" />
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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp100.01" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp100excute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:notEmpty name="anp100Form" property="anp090SelectSid">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('anp100delete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:notEmpty>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp100back');">
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

      <table class="table-left w100">
        <tr>
          <!-- テンプレート名 -->
          <th class="w25"><gsmsg:write key="anp.anp100.02" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
          <td class="w75">
            <html:text name="anp100Form" property="anp100Title" maxlength="<%=maxLengthTitle %>" styleClass="wp400" styleId="title" />
          </td>
        </tr>
        <tr>
          <!-- 件名 -->
          <th class="w25"><gsmsg:write key="cmn.subject" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
          <td class="w75">
            <html:text name="anp100Form" property="anp100Subject" maxlength="<%=maxLengthSubject %>" styleClass="wp400" styleId="subject" />
          </td>
        </tr>
        <tr>
          <!-- 本文１ -->
          <th class="w25"><gsmsg:write key="anp.body1" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
          <td class="w75">
            <% String onkey1 = "showLengthStr(value," + maxLengthText1 + ",'inputlength1');"; %>
            <div>
              <html:textarea styleId="inputstr1" name="anp100Form" property="anp100Text1" rows="10" styleClass="wp600" onkeyup="<%= onkey1 %>" />
            </div>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />
              :
            </span>
            <span id="inputlength1" class="formCounter">0</span>
            <span class="formCounter">
              /&nbsp;<%=maxLengthText1 %>&nbsp;
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
        <tr>
          <!-- 本文２ -->
          <th class="w25">
            <gsmsg:write key="anp.body2" />
          </th>
          <td class="w75">
            <% String onkey2 = "showLengthStr(value," + maxLengthText2 + ",'inputlength2');"; %>
            <div>
              <html:textarea styleId="inputstr2" name="anp100Form" property="anp100Text2" rows="8" styleClass="wp600" onkeyup="<%= onkey2 %>" />
            </div>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength2" class="formCounter">0</span>
            <span class="formCounter">/&nbsp;<%=maxLengthText2 %>&nbsp;
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp100excute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:notEmpty name="anp100Form" property="anp090SelectSid">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('anp100delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:notEmpty>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp100back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>