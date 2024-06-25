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
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp150.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp150">

<input type="hidden" name="CMD" value="">
<html:hidden property="ntp150NgpSid" />
<html:hidden property="ntp150NgySid" />
<html:hidden property="ntp150ProcMode" />

<logic:notEmpty name="ntp150Form" property="ntp150ProcessList" scope="request">
  <logic:iterate id="sort" name="ntp150Form" property="ntp150ProcessList" scope="request">
    <input type="hidden" name="ntp150SortLabel" value="<bean:write name="sort" property="ngpValue" />">
  </logic:iterate>
</logic:notEmpty>


<!--　BODY -->
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
      <gsmsg:write key="ntp.62" /><gsmsg:write key="cmn.list" />
    </li>
    <li>
      <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="return buttonSubmit('add','-1','-1');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp150');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <html:select name="ntp150Form" property="ntp150DispNgySid" onchange="changeCmbo('selectChange');">
      <logic:notEmpty name="ntp150Form" property="ntp150GyomuList">
        <html:optionsCollection name="ntp150Form" property="ntp150GyomuList" value="value" label="label" />
      </logic:notEmpty>
    </html:select>
  </div>
  <logic:notEqual name="ntp150Form" property="ntp150DispNgySid" value="all">
    <div class="txt_l mt10">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush2('upProcessData');">
        <gsmsg:write key="cmn.up" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush2('downProcessData');">
        <gsmsg:write key="cmn.down" />
      </button>
    </div>
  </logic:notEqual>
  <!-- 全件表示処理 -->
  <logic:equal name="ntp150Form" property="ntp150DispNgySid" value="all">
    <table class="table-top w100">
      <tr>
        <th class="w20">
          <gsmsg:write key="ntp.125" />
        </th>
        <th class="w20 no_w">
          <gsmsg:write key="ntp.126" />
        </th>
        <th class="w60">
          <gsmsg:write key="ntp.127" />
        </th>
      </tr>
      <logic:notEmpty name="ntp150Form" property="ntp150ProcessList">
        <logic:iterate id="processMdl" name="ntp150Form" property="ntp150ProcessList" indexId="idx">
          <tr class="js_listHover cursor_p" data-ngpsid="<bean:write name="processMdl" property="ngpSid" />" data-ngysid="<bean:write name="processMdl" property="ngySid" />">
            <td class="w20 js_listClick">
              <bean:write name="processMdl" property="ngyName" />
            </td>
            <td class="w20 cl_linkDef js_listClick">
              <bean:write name="processMdl" property="ngpCode" />
            </td>
            <td class="w60 cl_linkDef js_listClick">
              <bean:write name="processMdl" property="ngpName" />
            </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>
  </logic:equal>
  <!-- 各業務ごとの表示処理 -->
  <logic:notEqual name="ntp150Form" property="ntp150DispNgySid" value="all">
    <table class="table-top w100">
      <tr>
        <th class="w5">
        </th>
        <th class="w20">
          <gsmsg:write key="ntp.125" />
        </th>
        <th class="w20 no_w">
          <gsmsg:write key="ntp.126" />
        </th>
        <th class="w55">
          <gsmsg:write key="ntp.127" />
        </th>
      </tr>
      <logic:notEmpty name="ntp150Form" property="ntp150ProcessList">
        <logic:iterate id="processMdl" name="ntp150Form" property="ntp150ProcessList" indexId="idx">
          <bean:define id="radiovalue" name="processMdl" property="ngpValue" />
          <tr class="js_listHover cursor_p" data-ngpsid="<bean:write name="processMdl" property="ngpSid" />" data-ngysid="<bean:write name="processMdl" property="ngySid" />">
            <td class="w5 txt_c js_sord_radio">
              <html:radio name="ntp150Form" property="ntp150SortProcess" value="<%= String.valueOf(radiovalue) %>" />
            </td>
            <td class="w20 js_listClick">
              <bean:write name="processMdl" property="ngyName" />
            </td>
            <td class="w20 cl_linkDef js_listClick">
              <bean:write name="processMdl" property="ngpCode" />
            </td>
            <td class="w55 cl_linkDef js_listClick">
              <bean:write name="processMdl" property="ngpName" />
            </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>
  </logic:notEqual>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="return buttonSubmit('add','-1','-1');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp150');">
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