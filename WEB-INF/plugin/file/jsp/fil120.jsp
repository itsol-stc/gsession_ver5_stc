<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<% String okiniDspOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAIN_OKINI_DSP_OFF);
   String okiniDspOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAIN_OKINI_DSP_ON);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" />　<gsmsg:write key="cmn.display.settings" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/file/fil120">
<input type="hidden" name="CMD" value="">

<html:hidden property="backDsp" />
<html:hidden property="backScreen" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="filSearchWd" />
<html:hidden property="backMainFlg" />

<logic:notEmpty name="fil120Form" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil120Form" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil120Form" property="fil010SelectDelLink" scope="request">
  <logic:iterate id="del" name="fil120Form" property="fil010SelectDelLink" scope="request">
    <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('fil120ok');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil120back');">
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
      <th class="w30">
        <gsmsg:write key="cmn.main.view2" /><span class="ml10"><gsmsg:write key="fil.2" /></span>
      </th>
      <td class="w70">
        <span class="verAlignMid">
          <html:radio name="fil120Form" styleId="okini_01" property="fil120MainSort" value="<%= okiniDspOff %>" /><label for="okini_01"><gsmsg:write key="cmn.dont.show" /></label>
          <html:radio styleClass="ml10" name="fil120Form" styleId="okini_02" property="fil120MainSort" value="<%= okiniDspOn %>" /><label for="okini_02"><gsmsg:write key="cmn.display.ok" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.main.view2" /><span class="ml10"><gsmsg:write key="fil.1" /></span>
      </th>
      <td>
        <logic:notEmpty name="fil120Form" property="fil120CallLblList">
        <html:select property="fil120MainCall">
        <html:optionsCollection name="fil120Form" property="fil120CallLblList" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.26" />
      </th>
      <td>
        <gsmsg:write key="fil.fil120.1" />
        <div class="mt5">
         <logic:notEmpty name="fil120Form" property="fil120RirekiCntLblList">
          <html:select property="fil120RirekiCnt">
          <html:optionsCollection name="fil120Form" property="fil120RirekiCntLblList" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="fil.24" />
      </th>
      <td>
        <logic:notEmpty name="fil120Form" property="fil120CallLblList">
        <html:select property="fil120Call">
        <html:optionsCollection name="fil120Form" property="fil120CallLblList" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('fil120ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil120back');">
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