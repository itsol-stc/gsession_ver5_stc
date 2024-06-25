<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp140.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp140">

<input type="hidden" name="CMD" value="">

<html:hidden property="ntp140NgySid" />
<html:hidden property="ntp140ProcMode" />

<logic:notEmpty name="ntp140Form" property="ntp140GyomuList" scope="request">
  <logic:iterate id="sort" name="ntp140Form" property="ntp140GyomuList" scope="request">
    <input type="hidden" name="ntp140sortLabel" value="<bean:write name="sort" property="gymValue" />">
  </logic:iterate>
</logic:notEmpty>
<!-- BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.61" /><gsmsg:write key="cmn.list" />
    </li>
    <li>
      <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="return buttonSubmit('add','-1');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp140');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush2('upGyomuData');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush2('downGyomuData');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="w5">
      </th>
      <th class="w15 no_w">
        <gsmsg:write key="ntp.123" />
      </th>
      <th class="w80">
        <gsmsg:write key="ntp.124" />
      </th>
    </tr>
    <logic:notEmpty name="ntp140Form" property="ntp140GyomuList">
      <logic:iterate id="gyomuMdl" name="ntp140Form" property="ntp140GyomuList" indexId="idx">
        <bean:define id="radiovalue" name="gyomuMdl" property="gymValue" />
        <tr class="js_listHover cursor_p" data-sid="<bean:write name="gyomuMdl" property="gymSid" />">
          <td class="w5 txt_c js_sord_radio">
            <html:radio property="ntp140sortGyomu" value="<%= String.valueOf(radiovalue) %>"/>
          </td>
          <td class="w15 js_listClick cl_linkDef">
            <bean:write name="gyomuMdl" property="gymCode" />
          </td>
          <td class="w80 js_listClick cl_linkDef">
            <bean:write name="gyomuMdl" property="gymName" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="return buttonSubmit('add','-1');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp140');">
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