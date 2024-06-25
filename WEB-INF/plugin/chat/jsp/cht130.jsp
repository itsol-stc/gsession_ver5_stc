<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.01" /> <gsmsg:write key="cht.cht030.03"/></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../chat/js/cht130.js?<%= GSConst.VERSION_PARAM %>'></script>
</head>

<body>
  <html:form action="/chat/cht130">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="cht010SelectPartner" />
    <html:hidden property="cht010SelectKbn" />
    <html:hidden property="cht130InitFlg" />
    <html:hidden property="cht130SelectSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht030.03"/>
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backConfMenu');">
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
<!-- デフォルト表示 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.default" /><gsmsg:write key="cmn.show" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="cht130Form" property="cht130DefFlg" styleId="cht130DefFlg_01" value="<%=String.valueOf(GSConstChat.CHAT_DSP_DEFAULT)%>" onchange="changeCheckBox();" /><label for="cht130DefFlg_01"><gsmsg:write key="cht.cht130.01" /></label>
          <html:radio name="cht130Form" styleClass="ml10" property="cht130DefFlg" styleId="cht130DefFlg_02" value="<%=String.valueOf(GSConstChat.CHAT_DSP_USER)%>" onchange="changeCheckBox();"/><label for="cht130DefFlg_02"><gsmsg:write key="cmn.user" /></label>
          <html:radio name="cht130Form" styleClass="ml10" property="cht130DefFlg" styleId="cht130DefFlg_03" value="<%=String.valueOf(GSConstChat.CHAT_DSP_CHATGROUP)%>" onchange="changeCheckBox();"/><label for="cht130DefFlg_03"><gsmsg:write key="cht.01" /><gsmsg:write key="cmn.group" /></label>
        </div>
        <logic:equal name="cht130Form" property="cht130DefFlg" value="<%= String.valueOf(GSConstChat.CHAT_DSP_DEFAULT) %>">
          <html:hidden property="cht130GrpGroup" />
          <html:hidden property="cht130User" />
          <html:hidden property="cht130UsrGroup" />
        </logic:equal>
        <logic:equal name="cht130Form" property="cht130DefFlg" value="<%= String.valueOf(GSConstChat.CHAT_DSP_USER) %>">
        <div class="mt10">
          <gsmsg:write key="cmn.group" />
          <html:select property="cht130UsrGroup" styleClass="wp300 ml5" styleId="cht130UsrGroup" onchange="changeGroupCombo();">
            <html:optionsCollection name="cht130Form" property="cht130GroupList" value="value" label="label" />
          </html:select>
          <br>
          <gsmsg:write key="cmn.user" />
          <html:select property="cht130User" styleId="cht130User" styleClass="wp300 ml20 mt5">
            <logic:iterate id="user" name="cht130Form" property="cht130UserList">
              <bean:define id="userValue" name="user" property="value" />
              <bean:define id="mukoUserClass" value="" />
              <logic:equal name="user" property="usrUkoFlg" value="1">
                <bean:define id="mukoUserClass" value="mukoUserOption" />
              </logic:equal>
              <html:option styleClass="<%=mukoUserClass %>" value="<%=String.valueOf(userValue) %>">
                <bean:write name="user" property="label" />
              </html:option>
            </logic:iterate>
          </html:select>
        </div>
        <html:hidden property="cht130GrpGroup" />
        </logic:equal>
        <logic:equal name="cht130Form" property="cht130DefFlg" value="<%= String.valueOf(GSConstChat.CHAT_DSP_CHATGROUP) %>">
        <div class="mt10">
          <gsmsg:write key="cht.01" /><gsmsg:write key="cmn.group" />
          <html:select property="cht130GrpGroup" styleClass="select01" styleId="cht130GrpGroup">
          <html:optionsCollection name="cht130Form" property="cht130ChtGroupList" value="value" label="label" />
          </html:select>
        </div>
        <html:hidden property="cht130User" />
        <html:hidden property="cht130UsrGroup" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backConfMenu');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>
</html:html>