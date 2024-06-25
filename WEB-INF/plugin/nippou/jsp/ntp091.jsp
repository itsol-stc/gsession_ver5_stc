<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences.menu" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>  

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>
<html:form action="/nippou/ntp091">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="hourDivision" value="<bean:write name="ntp091Form" property="ntp091HourDiv" />" />

<ntp:conf_hidden name="ntp091Form"/>

<logic:notEmpty name="ntp091Form" property="ntp091userSid">
<logic:iterate id="usid" name="ntp091Form" property="ntp091userSid">
  <input type="hidden" name="ntp091userSid" value="<bean:write name="usid" />">
</logic:iterate>
</logic:notEmpty>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="ntp.10" /><gsmsg:write key="cmn.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ntp091kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp091back');">
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
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.time" />
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.starttime" />ÅF
        <div class="verAlignMid">
          <span class="clockpicker_fr pos_rel display_flex input-group">
            <html:text name="ntp091Form" property="ntp091DefFrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <gsmsg:write key="cmn.endtime" />ÅF
        <div class="mt5 verAlignMid">
          <span class="clockpicker_fr pos_rel display_flex input-group">
            <html:text name="ntp091Form" property="ntp091DefToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="schedule.10" />
      </th>
      <td>
        <bean:define id="colorMsg1" value=""/>
        <bean:define id="colorMsg2" value=""/>
        <bean:define id="colorMsg3" value=""/>
        <bean:define id="colorMsg4" value=""/>
        <bean:define id="colorMsg5" value=""/>
        <logic:iterate id="msgStr" name="ntp091Form" property="ntp091ColorMsgList" indexId="msgId" type="java.lang.String">
        <logic:equal name="msgId" value="0">
          <% colorMsg1 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="1">
          <% colorMsg2 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="2">
          <% colorMsg3 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="3">
          <% colorMsg4 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="4">
          <% colorMsg5 = msgStr; %>
        </logic:equal>
        </logic:iterate>
    <div class="verAlignMid mr5 mt5 mb5">
      <div class="bgc_fontSchTitleBlue titleColor_radioBack-size mr5">
        <html:radio name="ntp091Form" property="ntp091Fcolor" styleId="ntp091Fcolor1" value="1" />
      </div>
      <label for="ntp091Fcolor1" ><%= colorMsg1 %></label>
    </div>
    <div class="verAlignMid mr5 mt5 mb5">
      <div class="bgc_fontSchTitleRed titleColor_radioBack-size mr5">
        <html:radio name="ntp091Form" property="ntp091Fcolor" styleId="ntp091Fcolor2" value="2" />
      </div>
      <label for="ntp091Fcolor2" ><%= colorMsg2 %></label>
    </div>
    <div class="verAlignMid mr5 mt5 mb5">
      <div class="bgc_fontSchTitleGreen titleColor_radioBack-size mr5">
        <html:radio name="ntp091Form" property="ntp091Fcolor" styleId="ntp091Fcolor3" value="3" />
      </div>
      <label for="ntp091Fcolor3"><%= colorMsg3 %></label>
    </div>
    <div class="verAlignMid mr5 mt5 mb5">
      <div class="bgc_fontSchTitleYellow titleColor_radioBack-size mr5">
        <html:radio name="ntp091Form" property="ntp091Fcolor" styleId="ntp091Fcolor4" value="4" />
      </div>
      <label for="ntp091Fcolor4"><%= colorMsg4 %></label>
    </div>
    <div class="verAlignMid mt5 mb5">
      <div class="bgc_fontSchTitleBlack titleColor_radioBack-size mr5">
        <html:radio name="ntp091Form" property="ntp091Fcolor" styleId="ntp091Fcolor5" value="5" />
      </div>
      <label for="ntp091Fcolor5"><%= colorMsg5 %></label>
    </div>
    </td>
    </tr>

  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ntp091kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp091back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>