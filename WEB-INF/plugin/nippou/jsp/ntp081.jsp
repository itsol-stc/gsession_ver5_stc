<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /><gsmsg:write key="anp.anp080.01" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<% String maxLengthMikomido = String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.MAX_LENGTH_MIKOMIDO); %>

<body onload="showLengthId($('#inputstr0')[0], <%= maxLengthMikomido %>, 'inputlength0');showLengthId($('#inputstr1')[0], <%= maxLengthMikomido %>, 'inputlength1');showLengthId($('#inputstr2')[0], <%= maxLengthMikomido %>, 'inputlength2');showLengthId($('#inputstr3')[0], <%= maxLengthMikomido %>, 'inputlength3');showLengthId($('#inputstr4')[0], <%= maxLengthMikomido %>, 'inputlength4');">



<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp081">

<input type="hidden" name="CMD" value="">
<ntp:conf_hidden name="ntp081Form"/>
<!--　BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="anp.anp080.01" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('ntp081Ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('ntp081Back');">
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
  <!-- 共有範囲 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.123" />
      </th>
      <td class="w75">
        <gsmsg:write key="ntp.81" />
        <div class="mt5">
          <span class="verAlignMid">
            <html:radio name="ntp081Form" property="ntp081KyoyuFlg" styleId="ntp081KyoyuFlg0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CRANGE_SHARE_ALL) %>" /><label for="ntp081KyoyuFlg0"><gsmsg:write key="schedule.125" /></label>
            <html:radio styleClass="ml10" name="ntp081Form" property="ntp081KyoyuFlg" styleId="ntp081KyoyuFlg1" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CRANGE_SHARE_GROUP) %>" /><label for="ntp081KyoyuFlg1"><gsmsg:write key="schedule.src.2" /></label>
          </span>
        </div>
      </td>
    </tr>
  <!-- 時間単位 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.time" /><gsmsg:write key="ntp.102" />
      </th>
      <td>
        <gsmsg:write key="ntp.82" />
        <div class="mt5">
          <span class="verAlignMid">
            <html:radio name="ntp081Form" property="ntp081HourDivision" styleId="ntp081HourDivision05" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.HOUR_DIVISION5) %>" /><label for="ntp081HourDivision05">５<gsmsg:write key="cmn.minute" /></label>
            <html:radio styleClass="ml10" name="ntp081Form" property="ntp081HourDivision" styleId="ntp081HourDivision10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.DF_HOUR_DIVISION) %>" /><label for="ntp081HourDivision10">１０<gsmsg:write key="cmn.minute" /></label>
            <html:radio styleClass="ml10" name="ntp081Form" property="ntp081HourDivision" styleId="ntp081HourDivision15" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.HOUR_DIVISION15) %>" /><label for="ntp081HourDivision15">１５<gsmsg:write key="cmn.minute" /></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.title" /><gsmsg:write key="schedule.src.16" />
      </th>
      <td>
      <span class="text_base"><gsmsg:write key="ntp.83" /></span><br>
      <span class="bgc_fontSchTitleBlue">　　</span>&nbsp;<html:text maxlength="25" property="ntp081ColCmt1" styleClass="wp180 mt5" /><br>
      <span class="bgc_fontSchTitleRed">　　</span>&nbsp;<html:text maxlength="25" property="ntp081ColCmt2" styleClass="wp180 mt5" /><br>
      <span class="bgc_fontSchTitleGreen">　　</span>&nbsp;<html:text maxlength="25" property="ntp081ColCmt3" styleClass="wp180 mt5" /><br>
      <span class="bgc_fontSchTitleYellow">　　</span>&nbsp;<html:text maxlength="25" property="ntp081ColCmt4" styleClass="wp180 mt5" /><br>
      <span class="bgc_fontSchTitleBlack">　　</span>&nbsp;<html:text maxlength="25" property="ntp081ColCmt5" styleClass="wp180 mt5" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.84" />
      </th>
      <td>
        <gsmsg:write key="ntp.85" />
    <div class="mt10">
    ■10%<br>
    <textarea name="ntp081MikomidoCmt1" rows="3" class="wp350" onkeyup="showLengthStr(value, <%= maxLengthMikomido %>, 'inputlength0');" id="inputstr0"><bean:write name="ntp081Form" property="ntp081MikomidoCmt1" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength0" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthMikomido %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    <br>
    <div class="mt10">
    ■30%<br>
    <textarea name="ntp081MikomidoCmt2" rows="3" class="wp350" onkeyup="showLengthStr(value, <%= maxLengthMikomido %>, 'inputlength1');" id="inputstr1"><bean:write name="ntp081Form"  property="ntp081MikomidoCmt2" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength1" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthMikomido %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </div>
    <div class="mt10">
    ■50%<br>
    <textarea name="ntp081MikomidoCmt3" rows="3" class="wp350" onkeyup="showLengthStr(value, <%= maxLengthMikomido %>, 'inputlength2');" id="inputstr2"><bean:write name="ntp081Form"  property="ntp081MikomidoCmt3" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthMikomido %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </div>
    <div class="mt10">
    ■70%<br>
    <textarea name="ntp081MikomidoCmt4" rows="3" class="wp350" onkeyup="showLengthStr(value, <%= maxLengthMikomido %>, 'inputlength3');" id="inputstr3"><bean:write name="ntp081Form" property="ntp081MikomidoCmt4" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength3" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthMikomido %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </div>
    <div class="mt10">
    ■100%<br>
    <textarea name="ntp081MikomidoCmt5" rows="3" class="wp350" onkeyup="showLengthStr(value, <%= maxLengthMikomido %>, 'inputlength4');" id="inputstr4"><bean:write name="ntp081Form" property="ntp081MikomidoCmt5" /></textarea>
    <br>
    <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength4" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthMikomido %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('ntp081Ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('ntp081Back');">
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