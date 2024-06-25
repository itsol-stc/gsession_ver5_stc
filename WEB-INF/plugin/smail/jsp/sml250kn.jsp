<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.sml.sml250kn.Sml250knForm" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.CMDMODE_EDIT);
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="wml.wml040kn.05" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml250.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script>
function connectionEnd(){
  document.getElementById("connection").style.display="none";
  document.getElementById("connectionEnd").style.display="block";
}

function connectionTest(){
  document.getElementById("connection").style.display="block";
  document.getElementById("connectionEnd").style.display="none";
  setTimeout (connectionEnd, 2000);
}
</script>
</head>

<body>

<html:form styleId="sml250kn" action="/smail/sml250kn">

<logic:notEqual name="sml250knForm" property="smlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="sml250knForm" property="smlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="0">
 </logic:equal>
</logic:notEqual>

<logic:equal name="sml250knForm" property="smlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="sml250knForm" property="smlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="1">
 </logic:equal>
</logic:equal>

<logic:notEqual name="sml250knForm" property="smlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="sml250knForm" property="smlCmdMode" value="<%= cmdModeEdit %>">
  <input type="hidden" name="helpPrm" value="2">
 </logic:equal>
</logic:notEqual>

<logic:equal name="sml250knForm" property="smlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="sml250knForm" property="smlCmdMode" value="<%= cmdModeEdit %>">
  <input type="hidden" name="helpPrm" value="3">
 </logic:equal>
</logic:equal>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlCmdMode" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlAccountSid" />
<html:hidden property="sml240keyword" />
<html:hidden property="sml240group" />
<html:hidden property="sml240user" />

<html:hidden property="sml250initFlg" />
<html:hidden property="sml250AccountKbn" />
<html:hidden property="sml250DefActUsrSid" />
<html:hidden property="sml250elementKbn" />
<html:hidden property="sml250name" />
<html:hidden property="sml250biko" />

<html:hidden property="sml250JdelKbn" />
<html:hidden property="sml250JYear" />
<html:hidden property="sml250JMonth" />
<html:hidden property="sml250SdelKbn" />
<html:hidden property="sml250SYear" />
<html:hidden property="sml250SMonth" />
<html:hidden property="sml250WdelKbn" />
<html:hidden property="sml250WYear" />
<html:hidden property="sml250WMonth" />
<html:hidden property="sml250DdelKbn" />
<html:hidden property="sml250DYear" />
<html:hidden property="sml250DMonth" />
<html:hidden property="sml250autoDelKbn" />
<html:hidden property="sml250tensoSetKbn" />
<html:hidden property="sml250tensoKbn" />
<html:hidden property="sml250SelTab" />

<logic:notEmpty name="sml250knForm" property="sml250userKbnUser">
<logic:iterate id="permitId" name="sml250knForm" property="sml250userKbnUser">
  <input type="hidden" name="sml250userKbnUser" value="<bean:write name="permitId" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="sml250theme" />
<html:hidden property="sml250quotes" />
<html:hidden property="sml250sendType" />

<html:hidden property="sml100sortAccount" />
<html:hidden property="sml240keyword" />
<html:hidden property="sml240group" />
<html:hidden property="sml240user" />
<html:hidden property="sml240svKeyword" />
<html:hidden property="sml240svGroup" />
<html:hidden property="sml240svUser" />
<html:hidden property="sml240sortKey" />
<html:hidden property="sml240order" />
<html:hidden property="sml240searchFlg" />

<logic:notEmpty name="sml250knForm" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml250knForm" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml250ObjKbn" />
<html:hidden property="sml250PassKbn" />
<html:hidden property="sml250groupSid" />
<html:hidden property="sml250MailFw" />
<html:hidden property="sml250MailDf" />
<html:hidden property="sml250MailDfSelected" />
<html:hidden property="sml250SmailOp" />
<html:hidden property="sml250ZaisekiPlugin" />

<logic:equal name="sml250knForm" property="sml250ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
<html:hidden property="sml250HuriwakeKbn" />
<html:hidden property="sml250Zmail1Selected" />
<html:hidden property="sml250Zmail1" />
<html:hidden property="sml250Zmail2Selected" />
<html:hidden property="sml250Zmail2" />
<html:hidden property="sml250Zmail3Selected" />
<html:hidden property="sml250Zmail3" />
</logic:equal>

<logic:notEmpty name="sml250knForm" property="sml250userSid" scope="request">
  <logic:iterate id="users" name="sml250knForm" property="sml250userSid" indexId="idx" scope="request">
    <bean:define id="userSid" name="users" type="java.lang.String" />
    <html:hidden property="sml250userSid" value="<%= userSid %>" />
  </logic:iterate>
</logic:notEmpty>

<bean:define id="acctMode" name="sml250knForm" property="smlAccountMode" type="java.lang.Integer" />
<bean:define id="sCmdMode" name="sml250knForm" property="smlCmdMode" type="java.lang.Integer" />
<% int accountMode = acctMode.intValue(); %>
<% int cmdMode = sCmdMode.intValue(); %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <% if (accountMode == 2 && cmdMode == 0) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.admin.setting" /></li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml040kn.05" />
      </li>
    <% } else if (accountMode == 2 && cmdMode == 1) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.admin.setting" /></li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.97" />
      </li>
    <% } else if (accountMode != 2 && cmdMode == 0) { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.preferences2" /></li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml040kn.05" />
      </li>
    <% } else if (accountMode != 2 && cmdMode == 1) { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.preferences2" /></li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml040kn.05" />
      </li>
    <% } %>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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
  <!-- タブヘッダ -->
    <ul id="normal_tab" class="tabHeader w100">
      <li id="normal" class="js_accTabHeader_tab tabHeader_tab-on border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.preferences" />
      </li>
      <logic:notEqual name="sml250Form" property="sml250autoDelKbn" value="0">
        <li id="auto_del" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
          <gsmsg:write key="cmn.autodelete" />
        </li>
      </logic:notEqual>
      <logic:equal name="sml250Form" property="smlAccountMode" value="2">
        <logic:equal name="sml250Form" property="sml250tensoKbn" value="1">
          <li id="tenso" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
            <gsmsg:write key="sml.80" />
          </li>
        </logic:equal>
      </logic:equal>
      <li id="other" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.other" />
      </li>
    </ul>

    <ul id="send_tab" class="tab_list display_none">
      <li id="normal" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.preferences" />
      </li>
      <logic:notEqual name="sml250Form" property="sml250autoDelKbn" value="0">
        <li id="auto_del" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
          <gsmsg:write key="cmn.autodelete" />
        </li>
      </logic:notEqual>
      <logic:equal name="sml250Form" property="smlAccountMode" value="2">
        <logic:equal name="sml250Form" property="sml250tensoKbn" value="1">
          <li id="tenso" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
            <gsmsg:write key="sml.80" />
          </li>
        </logic:equal>
      </logic:equal>
      <li id="other" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.other" />
      </li>
    </ul>

    <ul id="auto_del_tab" class="tab_list display_none">
      <li id="normal" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.preferences" />
      </li>
      <logic:notEqual name="sml250Form" property="sml250autoDelKbn" value="0">
        <li id="auto_del" class="js_accTabHeader_tab tabHeader_tab-on border_bottom_none pr10 pl10">
          <gsmsg:write key="cmn.autodelete" />
        </li>
      </logic:notEqual>
      <logic:equal name="sml250Form" property="smlAccountMode" value="2">
        <logic:equal name="sml250Form" property="sml250tensoKbn" value="1">
          <li id="tenso" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
            <gsmsg:write key="sml.80" />
          </li>
        </logic:equal>
      </logic:equal>
      <li id="other" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.other" />
      </li>
    </ul>

    <ul id="other_tab" class="tab_list display_none">
      <li id="normal" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.preferences" />
      </li>
      <logic:notEqual name="sml250Form" property="sml250autoDelKbn" value="0">
        <li id="auto_del" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
          <gsmsg:write key="cmn.autodelete" />
        </li>
      </logic:notEqual>
      <logic:equal name="sml250Form" property="smlAccountMode" value="2">
        <logic:equal name="sml250Form" property="sml250tensoKbn" value="1">
          <li id="tenso" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
            <gsmsg:write key="sml.80" />
          </li>
        </logic:equal>
      </logic:equal>
      <li id="other" class="js_accTabHeader_tab tabHeader_tab-on border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.other" />
      </li>
    </ul>

    <ul id="tenso_tab" class="tab_list display_none">
      <li id="normal" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.preferences" />
      </li>
      <logic:notEqual name="sml250Form" property="sml250autoDelKbn" value="0">
        <li id="auto_del" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
          <gsmsg:write key="cmn.autodelete" />
        </li>
      </logic:notEqual>
      <logic:equal name="sml250Form" property="smlAccountMode" value="2">
        <logic:equal name="sml250Form" property="sml250tensoKbn" value="1">
          <li id="tenso" class="js_accTabHeader_tab tabHeader_tab-on border_bottom_none pr10 pl10">
            <gsmsg:write key="sml.80" />
          </li>
        </logic:equal>
      </logic:equal>
      <li id="other" class="js_accTabHeader_tab tabHeader_tab-off border_bottom_none pr10 pl10">
        <gsmsg:write key="cmn.other" />
      </li>
    </ul>


  <!-- タブコンテンツ -->
  <!-- 基本設定 -->
  <table id="normal_table" class="table-left w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.96" />
      </th>
      <td class="w75">
        <bean:write name="sml250knForm" property="sml250name" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <bean:write name="sml250knForm" property="sml250knBiko" filter="false" />
      </td>
    </tr>
    <logic:equal name="sml250knForm" property="sml250acntUserFlg" value="true">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.employer" />
        </th>
        <td class="w75">
          <logic:notEmpty name="sml250knForm" property="userKbnUserSelectCombo">
            <logic:iterate id="userKbnLabel" name="sml250knForm" property="userKbnUserSelectCombo">
              <bean:define id="usrLabelClass" value="" />
              <logic:equal value="1" name="userKbnLabel" property="usrUkoFlg">
                <bean:define id="usrLabelClass" value="mukoUser" />
              </logic:equal>
              <div>
                <span class="<bean:write name="usrLabelClass"/>" ><bean:write name="userKbnLabel" property="label" /></span>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:equal>
  </table>

  <!-- 転送設定 -->
  <table id="tenso_table" class="table-left w100 display_none mt0">
    <!-- メール転送一括設定 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="sml.sml100.03" />
      </th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250tensoSetKbn" value="0">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:notEqual name="sml250knForm" property="sml250tensoSetKbn" value="0">
          <gsmsg:write key="cmn.setting.do" />
        </logic:notEqual>
      </td>
    </tr>
    <!-- 対象 -->
    <tr class="js_sml_tenso_set display_none">
      <th class="w25">
        <gsmsg:write key="cmn.target" />
      </th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250ObjKbn" value="0">
          <div>
            <span class="cl_fontWarn"><gsmsg:write key="sml.sml180kn.14" /></span>
            <br>
            <gsmsg:write key="wml.231" /><gsmsg:write key="cmn.alluser" />
            <br>
            (<bean:write name="sml250knForm" property="sml250knUsrCnt" />&nbsp;<gsmsg:write key="cmn.user" />)
          </div>
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250ObjKbn" value="1">
          <logic:notEmpty name="sml250knForm" property="sml250knUsrOkLabelList">
            <div>
              <span class="cl_fontWarn fs_13"><gsmsg:write key="sml.sml180kn.14" /></span>
              <br>
              <logic:iterate id="okUser" name="sml250knForm" property="sml250knUsrOkLabelList">
                <bean:define id="usrLabelClass" value="" />
                <logic:equal value="1" name="okUser" property="usrUkoFlg">
                  <bean:define id="usrLabelClass" value="mukoUser" />
                </logic:equal>
                <span class="<bean:write name="usrLabelClass" />"><bean:write name="okUser"  property="label"/></span>
                <br>
              </logic:iterate>
              (<bean:write name="sml250knForm" property="sml250knUsrCnt" />&nbsp;<gsmsg:write key="cmn.user" />)
            </div>
          </logic:notEmpty>
        </logic:equal>
        <logic:notEmpty name="sml250knForm" property="sml250knUsrNgLabelList">
          <div class="mt20">
            <span class="cl_fontWarn"><gsmsg:write key="sml.sml180kn.17" /></span>
            <br>
            <logic:iterate id="ngUser" name="sml250knForm" property="sml250knUsrNgLabelList">
              <bean:define id="usrLabelClass" value="" />
              <logic:equal value="1" name="ngUser" property="usrUkoFlg">
                <bean:define id="usrLabelClass" value="mukoUser" />
              </logic:equal>
              <span class="<bean:write name="usrLabelClass" />"><bean:write name="ngUser" property="label"/></span><br>
            </logic:iterate>
            <gsmsg:write key="sml.sml180kn.06" /><bean:write name="sml250knForm" property="sml250knUsrCntNg" />&nbsp;<gsmsg:write key="cmn.user" />)
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <!-- 転送設定 -->
    <tr class="js_sml_tenso_set display_none">
      <th class="w25">
        <gsmsg:write key="sml.80" />
      </th>
      <td class="w25">
        <logic:equal name="sml250knForm" property="sml250MailFw" value="0">
          <div>
            <gsmsg:write key="sml.sml180kn.04" />
          </div>
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250MailFw" value="1">
          <div>
            <gsmsg:write key="sml.sml180kn.05" />
          </div>
          <div class="">
            <logic:equal name="sml250knForm" property="sml250SmailOp" value="0">
              <gsmsg:write key="sml.sml180kn.02" />
            </logic:equal>
            <logic:equal name="sml250knForm" property="sml250SmailOp" value="1">
              <gsmsg:write key="sml.sml180kn.03" />
            </logic:equal>
          </div>
          <div class="mt10"></div>
          <bean:define id="dispSingleMailTo" value="" />
          <bean:define id="dispZaisekiMailTo" value="display_none" />
          <bean:define id="dispFuzaiMailTo" value="display_none" />
          <bean:define id="dispOtherMailTo" value="display_none" />
          <logic:equal name="sml250knForm" property="sml250ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
            <logic:equal name="sml250knForm" property="sml250HuriwakeKbn" value="1">
              <bean:define id="dispSingleMailTo" value="display_none" />
              <bean:define id="dispZaisekiMailTo" value="" />
              <bean:define id="dispFuzaiMailTo" value="" />
              <bean:define id="dispOtherMailTo" value="" />
            </logic:equal>
            <logic:equal name="sml250knForm" property="sml250HuriwakeKbn" value="2">
              <bean:define id="dispSingleMailTo" value="display_none" />
              <bean:define id="dispFuzaiMailTo" value="" />
            </logic:equal>

            <div class="js_zaisekiMailTo mb5 <bean:write name="dispZaisekiMailTo" />">
              <div class="fw_b">
                <gsmsg:write key="sml.44" />
              </div>
              <logic:equal name="sml250knForm" property="sml250Zmail1Selected" value="0"><bean:write name="sml250knForm" property="sml250Zmail1" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail1Selected" value="1"><gsmsg:write key="cmn.mailaddress1" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail1Selected" value="2"><gsmsg:write key="cmn.mailaddress2" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail1Selected" value="3"><gsmsg:write key="cmn.mailaddress3" /></logic:equal>
            </div>
            <div class="js_fuzaiMailTo <bean:write name="dispFuzaiMailTo" />">
              <div class="fw_b">
                <gsmsg:write key="sml.89" />
              </div>
              <logic:equal name="sml250knForm" property="sml250Zmail2Selected" value="0"><bean:write name="sml250knForm" property="sml250Zmail2" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail2Selected" value="1"><gsmsg:write key="cmn.mailaddress1" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail2Selected" value="2"><gsmsg:write key="cmn.mailaddress2" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail2Selected" value="3"><gsmsg:write key="cmn.mailaddress3" /></logic:equal>
            </div>
            <div class="js_otherMailTo mt5 <bean:write name="dispOtherMailTo" />">
              <div class="fw_b">
                <gsmsg:write key="sml.12" />
              </div>
              <logic:equal name="sml250knForm" property="sml250Zmail3Selected" value="0"><bean:write name="sml250knForm" property="sml250Zmail3" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail3Selected" value="1"><gsmsg:write key="cmn.mailaddress1" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail3Selected" value="2"><gsmsg:write key="cmn.mailaddress2" /></logic:equal>
              <logic:equal name="sml250knForm" property="sml250Zmail3Selected" value="3"><gsmsg:write key="cmn.mailaddress3" /></logic:equal>
            </div>
          </logic:equal>
          <div class="js_mailDfSelect <bean:write name="dispSingleMailTo" />">
            <div class="fw_b">
              <gsmsg:write key="sml.81" />
            </div>
            <logic:equal name="sml250knForm" property="sml250MailDfSelected" value="0">
              <bean:write name="sml250knForm" property="sml250MailDf" />
            </logic:equal>
            <logic:equal name="sml250knForm" property="sml250MailDfSelected" value="1">
              <gsmsg:write key="cmn.mailaddress1" />
            </logic:equal>
            <logic:equal name="sml250knForm" property="sml250MailDfSelected" value="2">
              <gsmsg:write key="cmn.mailaddress2" />
            </logic:equal>
            <logic:equal name="sml250knForm" property="sml250MailDfSelected" value="3">
              <gsmsg:write key="cmn.mailaddress3" />
            </logic:equal>
          </div>

        </logic:equal>
      </td>
    </tr>
  </table>

  <!-- 自動削除 -->
  <table id="auto_del_table" class="table-left w100 display_none mt0">
    <tr>
      <th class="w25"><gsmsg:write key="sml.50" /></th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="jyear" name="sml250knForm" property="sml250JYear" type="java.lang.String" />
          <bean:define id="jmonth" name="sml250knForm" property="sml250JMonth" type="java.lang.String" />
          <span><gsmsg:write key="cmn.year" arg0="<%= jyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= jmonth %>" /></span> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="sml.52" /></th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="syear" name="sml250knForm" property="sml250SYear" type="java.lang.String" />
          <bean:define id="smonth" name="sml250knForm" property="sml250SMonth" type="java.lang.String" />
          <span><gsmsg:write key="cmn.year" arg0="<%= syear %>" /> <gsmsg:write key="cmn.months" arg0="<%= smonth %>" /></span> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="sml.51" /></th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="wyear" name="sml250knForm" property="sml250WYear" type="java.lang.String" />
          <bean:define id="wmonth" name="sml250knForm" property="sml250WMonth" type="java.lang.String" />
          <span><gsmsg:write key="cmn.year" arg0="<%= wyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= wmonth %>" /></span> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="sml.49" /></th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="dyear" name="sml250knForm" property="sml250DYear" type="java.lang.String" />
          <bean:define id="dmonth" name="sml250knForm" property="sml250DMonth" type="java.lang.String" />
          <span><gsmsg:write key="cmn.year" arg0="<%= dyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" /></span> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
  </table>

  <!-- その他 -->
  <table id="other_table" class="table-left w100 display_none mt0">
    <tr><%-- 自動TO --%>
      <th class="w25"><gsmsg:write key="wml.52" /></th>
      <td class="w75">
        <logic:notEmpty  name="sml250knForm" property="sml250AutoDestToLabelList">
          <logic:iterate id="user" name="sml250knForm" property="sml250AutoDestToLabelList" type="UsrLabelValueBean">
            <input type="hidden" name="sml250AutoDestToUsrSid" value="<bean:write name="user" property="value" />">
            <span class="<%= user.getCSSClassNameNormal() %>"><bean:write name="user" property="label" /></span></br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr><%-- 自動Cc --%>
      <th class="w25"><gsmsg:write key="wml.53" /></th>
      <td class="w75">
        <logic:notEmpty  name="sml250knForm" property="sml250AutoDestCcLabelList">
          <logic:iterate id="user" name="sml250knForm" property="sml250AutoDestCcLabelList"  type="UsrLabelValueBean">
            <input type="hidden" name="sml250AutoDestCcUsrSid" value="<bean:write name="user" property="value" />">
            <span class="<%= user.getCSSClassNameNormal() %>"><bean:write name="user" property="label" /></span></br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr><%-- 自動Bcc --%>
      <th class="w25"><gsmsg:write key="wml.54" /></th>
      <td class="w75">
        <logic:notEmpty  name="sml250knForm" property="sml250AutoDestBccLabelList">
          <logic:iterate id="user" name="sml250knForm" property="sml250AutoDestBccLabelList"  type="UsrLabelValueBean">
            <input type="hidden" name="sml250AutoDestBccUsrSid" value="<bean:write name="user" property="value" />">
            <span class="<%= user.getCSSClassNameNormal() %>"><bean:write name="user" property="label" /></span></br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="cmn.format." /></th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250sendType" value="0">
          <gsmsg:write key="cmn.standard" />
        </logic:equal>
        <logic:notEqual name="sml250knForm" property="sml250sendType" value="0">
          <gsmsg:write key="wml.110" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="cmn.theme" /></th>
      <td class="w75">
        <logic:equal name="sml250knForm" property="sml250theme" value="0">
          <bean:write name="sml250knForm" property="sml250knTheme" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="1">
          <gsmsg:write key="cmn.default" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="2">
          <gsmsg:write key="cmn.theme.gray" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="3">
          <gsmsg:write key="cmn.theme.green" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="4">
          <gsmsg:write key="cmn.theme.red" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="5">
          <gsmsg:write key="cmn.theme.pink" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="6">
          <gsmsg:write key="cmn.theme.yellow" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="7">
          <gsmsg:write key="cmn.theme.original" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="8">
          <gsmsg:write key="cmn.theme.sougen" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="9">
          <gsmsg:write key="cmn.theme.sunset" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="10">
          <gsmsg:write key="cmn.theme.sakura" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="11">
          <gsmsg:write key="cmn.theme.sky" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="12">
          <gsmsg:write key="cmn.theme.city" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="13">
          <gsmsg:write key="cmn.theme.dark" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="14">
          <gsmsg:write key="cmn.theme.mokume" />
        </logic:equal>
        <logic:equal name="sml250knForm" property="sml250theme" value="15">
          <gsmsg:write key="cmn.theme.darkblue" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="cmn.quotes" /></th>
      <td class="w75">
        <bean:write name="sml250knForm" property="sml250knQuotes" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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