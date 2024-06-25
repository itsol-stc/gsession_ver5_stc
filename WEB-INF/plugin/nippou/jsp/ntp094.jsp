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

<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>
<html:form action="/nippou/ntp094">

<input type="hidden" name="CMD" value="">
<ntp:conf_hidden name="ntp094Form"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ntp094kakunin');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp094back');">
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
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td class="w75">
        <!-- 件数 -->
        <html:select property="ntp094DefLine">
          <html:optionsCollection name="ntp094Form" property="ntp094LineLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.110" />
      </th>
      <td>
        <gsmsg:write key="ntp.111" />
        <div class="mt5">
          <span class="verAlignMid">
            <html:radio name="ntp094Form" styleId="ntp094Position_01" property="ntp094Position" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.DAY_POSITION_LEFT) %>" /><label for="ntp094Position_01"><gsmsg:write key="ntp.112" /></label>
            <html:radio name="ntp094Form" styleClass="ml10" styleId="ntp094Position_02" property="ntp094Position" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.DAY_POSITION_RIGHT) %>" /><label for="ntp094Position_02"><gsmsg:write key="ntp.113" /></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.sortby.members" />
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.plg.set.order.grpmember" /><br>
        <div class="verAlignMid">
        <gsmsg:write key="cmn.first.key" />：
        <!-- キー1 -->
        <html:select property="ntp094SortKey1">
        <html:optionsCollection name="ntp094Form" property="ntp094SortKeyLabel" value="value" label="label" />
        </html:select>
        <span class="verAlignMid">
          <html:radio name="ntp094Form" styleClass="ml10" property="ntp094SortOrder1" styleId="ntp094SortOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="ntp094SortOrder10"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="ntp094Form" styleClass="ml10" property="ntp094SortOrder1" styleId="ntp094SortOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="ntp094SortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
        </div>
        <br>
      <div class="mt5 verAlignMid">
        <gsmsg:write key="cmn.second.key" />：
        <!-- キー2 -->
        <html:select property="ntp094SortKey2">
        <html:optionsCollection name="ntp094Form" property="ntp094SortKeyLabel" value="value" label="label" />
        </html:select>
        <span class="verAlignMid">
          <html:radio name="ntp094Form" styleClass="ml10" property="ntp094SortOrder2" styleId="ntp094SortOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="ntp094SortOrder20"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="ntp094Form" styleClass="ml10" property="ntp094SortOrder2" styleId="ntp094SortOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="ntp094SortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="schedule.sch093.2" />
      </th>
      <td>
        <!-- グループ -->
        <html:select name="ntp094Form" property="ntp094DefGroup">

        <logic:notEmpty name="ntp094Form" property="ntp094GroupLabel" scope="request">
        <logic:iterate id="gpBean" name="ntp094Form" property="ntp094GroupLabel" scope="request">

        <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
        <logic:equal name="gpBean" property="styleClass" value="0">
        <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
        </logic:equal>

        <logic:notEqual name="gpBean" property="styleClass" value="0">
        <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
        </logic:notEqual>

        </logic:iterate>
        </logic:notEmpty>

        <!--html:optionsCollection name="ntp094Form" property="ntp093GroupLabel" label="value" label="label" /-->
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="ntp.115" />
      </th>
      <td class="w75">
        <gsmsg:write key="ntp.116" />
        <div class="mt5">
          <span class="verAlignMid">
            <html:radio name="ntp094Form" styleId="ntp094SchKbn_01" property="ntp094SchKbn" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SCH_DSP_YES) %>" /><label for="ntp094SchKbn_01"><gsmsg:write key="reserve.show.ok" /></label>
            <html:radio name="ntp094Form" styleClass="ml10" styleId="ntp094SchKbn_02" property="ntp094SchKbn" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SCH_DSP_NO) %>" /><label for="ntp094SchKbn_02"><gsmsg:write key="fil.65" /></label>
          </span>
        <div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ntp094kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp094back');">
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