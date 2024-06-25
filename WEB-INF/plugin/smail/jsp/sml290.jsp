<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="wml.wml110.01" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml290.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body>

<html:form action="/smail/sml290">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden name="sml290Form" property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlLabelCmdMode" />
<html:hidden property="smlEditLabelId" />
<html:hidden property="dspCount" />
<html:hidden property="sml240keyword" />
<html:hidden property="sml240group" />
<html:hidden property="sml240user" />
<html:hidden property="sml240svKeyword" />
<html:hidden property="sml240svGroup" />
<html:hidden property="sml240svUser" />
<html:hidden property="sml240sortKey" />
<html:hidden property="sml240order" />
<html:hidden property="sml240searchFlg" />


<logic:notEmpty name="sml290Form" property="lbnList" scope="request">
  <logic:iterate id="sort" name="sml290Form" property="lbnList" scope="request">
    <input type="hidden" name="sml290sortLabel" value="<bean:write name="sort" property="lbValue" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml110.01" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addLabel();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sml240Back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>

    <table class="table-left w100">
      <tr>
        <th class="w25 table_title-color">
          <gsmsg:write key="wml.28" />
        </th>
        <td class="w75">
          <bean:write name="sml290Form" property="sml290accountName" />
        </td>
      </tr>
    </table>

    <div class="txt_l">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upLabelData');">
        <gsmsg:write key="cmn.up" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downLabelData');">
        <gsmsg:write key="cmn.down" />
      </button>
    </div>

    <table class="table-top table-fixed w100">
      <tr>
        <th class="wp25 table_title-color txt_c">
          &nbsp;
        </th>
        <th class="w80 table_title-color txt_c">
          <gsmsg:write key="wml.74" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="cmn.fixed" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="cmn.delete" />
        </th>
      </tr>
      <logic:notEqual name="sml290Form" property="smlAccountSid" value="-1">
        <logic:iterate id="lbnData" name="sml290Form" property="lbnList" indexId="idx">
          <bean:define id="lbValue" name="lbnData" property="lbValue" />
          <% String labelCheckId = "chkLabel" + String.valueOf(idx.intValue()); %>
          <tr>
            <td class="txt_c cursor_p" onClick="this.firstChild.checked=true;"><html:radio property="sml290SortRadio" value="<%= String.valueOf(lbValue) %>" styleId="<%= labelCheckId %>" /></td>
            <td class="txt_l">
            <bean:write name="lbnData" property="labelName" />
            </td>
            <td class="txt_c plr0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.fixed" />" onClick="editLabel('<bean:write name="lbnData" property="labelSid" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
                <gsmsg:write key="cmn.fixed" />
              </button>
            </td>
            <td class="txt_c plr0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="deleteLabel('<bean:write name="lbnData" property="labelSid" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </td>
          </tr>
        </logic:iterate>
      </logic:notEqual>
    </table>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>