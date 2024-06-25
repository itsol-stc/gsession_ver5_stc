<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<% String maxLengthSubject = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SUBJECT); %>
<% String maxLengthText1   = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_TEXT1); %>
<% String maxLengthText2   = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_TEXT2); %>
<% String maxLengthMail    = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_MAIL); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.anp060.01" /></title>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp060.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../anpi/css/anpi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body onload="setfocus();showLengthId($('#inputstr1')[0],<%=maxLengthText1 %>,'inputlength1');showLengthId($('#inputstr2')[0],<%=maxLengthText2 %>,'inputlength2');">
  <html:form action="/anpi/anp060">

    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anpiSid" />
    <html:hidden property="anp010SelectGroupSid" />
    <html:hidden property="anp010NowPage" />
    <html:hidden property="anp010SortKeyIndex" />
    <html:hidden property="anp010OrderKey" />

    <html:hidden property="anp060ProcMode" />
    <html:hidden property="anp060ScrollFlg" />
    <html:hidden property="anp060CopyAnpiSid" />

    <html:hidden property="anp130SelectAphSid" />
    <html:hidden property="anp130allCheck" />
    <html:hidden property="anp130NowPage" />
    <html:hidden property="anp130DspPage1" />
    <html:hidden property="anp130DspPage2" />
    <html:hidden property="anp140NowPage" />
    <html:hidden property="anp140DspPage1" />
    <html:hidden property="anp140DspPage2" />
    <html:hidden property="anp140ScrollFlg" />

    <logic:notEmpty name="anp060Form" property="anp130DelSidList" scope="request">
      <logic:iterate id="del" name="anp060Form" property="anp130DelSidList" scope="request">
        <input type="hidden" name="anp130DelSidList" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>

    <!-- ヘッダー -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../anpi/images/classic/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
          <img class="header_pluginImg" src="../anpi/images/original/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
        </li>
        <li>
          <gsmsg:write key="anp.plugin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="anp.anp060.02" />
        </li>
        <!-- ボタン -->
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp060haisin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp060back');">
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

      <!-- 訓練モード バー -->
      <div class="anp_kunren js_knren_top">
        <span>
          <gsmsg:write key="anp.knmode" />
        </span>
      </div>
      <table class="table-left w100">
        <!-- 訓練フラグ -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.knmode" />
          </th>
          <td class="w75">
            <span class="verAlignMid">
              <html:checkbox name="anp060Form" property="anp010KnrenFlg" value="1" styleId="knrenFlg" onclick="setKnrenMode();" />
              <label for="knrenFlg">
                <gsmsg:write key="anp.anp060.03" />
              </label>
            </span>
          </td>
        </tr>
        <!-- メイン表示 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.main.view" />
          </th>
          <td class="w75 ">
            <span class="verAlignMid">
              <html:radio name="anp060Form" property="anp060main" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.APH_VIEW_MAIN_ALL) %>" styleId="anpMain0" />
              <label for="anpMain0">
                <gsmsg:write key="cmn.alluser" />
              </label>
              <html:radio name="anp060Form" styleClass="ml10" property="anp060main" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.APH_VIEW_MAIN_SENDTO) %>" styleId="anpMain1" />
              <label for="anpMain1">
                <gsmsg:write key="anp.anp060.11" />
              </label>
            </span>
          </td>
        </tr>
        <!-- 定型メッセージ選択 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.anp060.04" />
          </th>
          <td class="w75">
            <html:select property="anp060SelectMtempSid" styleId="mtempSid">
              <option value="-1"></option>
              <logic:notEmpty name="anp060Form" property="anp060MtempLabel">
                <html:optionsCollection name="anp060Form" property="anp060MtempLabel" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp060selectTemp');">
              <gsmsg:write key="cmn.select" />
            </button>
          </td>
        </tr>
        <!-- 件名 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.subject" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <span id="js_lmtinput">
              【
              <gsmsg:write key="anp.knmode" />
              】
            </span>
            <html:text name="anp060Form" property="anp060Subject" maxlength="<%=maxLengthSubject %>" styleClass="wp200" styleId="subject" />
          </td>
        </tr>
        <!-- 本文 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.body" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <span id="js_lmtinput2">
              <div>
                <gsmsg:write key="anp.anp060.12" />
              </div>
              <div>
                <gsmsg:write key="cmn.comments" />
                <gsmsg:write key="anp.knmode" />
              </div>
              <div>
                <gsmsg:write key="anp.anp060.05" />
              </div>
              <div>
                <gsmsg:write key="anp.anp060.12" />
              </div>
            </span>
            <% String onkey1 = "showLengthStr(value," + maxLengthText1 + ",'inputlength1');"; %>
            <div class="mt10">
              <html:textarea styleId="inputstr1" name="anp060Form" property="anp060Text1" rows="10" styleClass="wp600" onkeyup="<%= onkey1 %>" />
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
            <div class="m20">
              <bean:write name="anp060Form" property="anp060MessageBody" filter="false" />
            </div>
            <% String onkey2 = "showLengthStr(value," + maxLengthText2 + ",'inputlength2');"; %>
            <div>
              <html:textarea styleId="inputstr2" name="anp060Form" property="anp060Text2" rows="8" styleClass="wp600" onkeyup="<%= onkey2 %>" />
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
        <!-- 送信先 -->
        <th class="w25">
          <gsmsg:write key="anp.send.dest" />
          <span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments" />
          </span>
        </th>
        <td class="w75">
          <ui:usrgrpselector name="anp060Form" property="anp060SenderListUI" styleClass="hp215" />
        </td>
        <!-- テスト送信 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.anp060.06" />
          </th>
          <td class="w75">
            <html:text name="anp060Form" property="anp060TestAdr" maxlength="<%=maxLengthMail %>" styleClass="wp400" />
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.sent" />" onClick="buttonPush('anp060sendTest');">
              <gsmsg:write key="cmn.sent" />
            </button>
          </td>
        </tr>
        <!-- 配信者 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.sender" />
          </th>
          <td class="w75">
            <bean:write name="anp060Form" property="anp060RegistName" />
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp060haisin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp060back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <!-- フッター -->
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
