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
<jsp:include page="/WEB-INF/plugin/smail/jsp/sml010_message.jsp" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../smail/js/sml170.js?<%= GSConst.VERSION_PARAM %>"></script>
<jsp:include page="/WEB-INF/plugin/smail/jsp/smlaccountsel.jsp" />
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION <gsmsg:write key="sml.20" /></title>
</head>

<body onload="changeEnableDisable();">
<html:form action="/smail/sml170">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlAccountSid" />
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml170AccountSid" />
<html:hidden property="sml170InitFlg" />
<html:hidden property="sml170AccountName" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.preferences2" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="sml.80" />
        </li>
        <li>
          <div>
            <button type="button" id="settingBtn" class="baseBtn" value="<gsmsg:write key="cmn.setting" />">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
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

    <table class="table-left table-fixed w100">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
        </th>
        <td class="w75">
          <div class="verAlignMid">
            <html:radio name="sml170Form" property="sml170SelKbn" styleClass="accountSelKbn" styleId="sml170SelKbn_0"  value="0"/><label for="sml170SelKbn_0"><gsmsg:write key="wml.wml010.12" /></label>
            <html:radio styleClass="ml10 accountSelKbn" name="sml170Form" property="sml170SelKbn"  styleId="sml170SelKbn_1"  value="1"/><label for="sml170SelKbn_1"><gsmsg:write key="cmn.all" /></label>
          </div>
          <div id="accountSelArea">
            <span id="selAccountNameArea">
              <bean:write name="sml170Form" property="sml170AccountName" />
            </span>
            <button type="button" id="accountSelBtn" class="baseBtn ml20" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
              <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
            </button>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="sml.80" />
        </th>
        <td class="w75">
          <div class="">
            <gsmsg:write key="sml.05" />
          </div>
          <div ass="mt5">
            <div class="verAlignMid">
              <html:radio name="sml170Form" property="sml170MailFw" styleId="sml170MailFw0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>" onclick="changeEnableDisable();"/><label for="sml170MailFw0"><gsmsg:write key="sml.78" /></label>
              <html:radio styleClass="ml10" name="sml170Form" property="sml170MailFw" styleId="sml170MailFw1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_OK) %>" onclick="changeEnableDisable();"/><label for="sml170MailFw1"><gsmsg:write key="sml.79" /></label>
            </div>
          </div>
          <bean:define id="dispTenso" value="" />
          <logic:equal  name="sml170Form" property="sml170MailFw" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>">
            <bean:define id="dispTenso" value="display_none" />
          </logic:equal>
          <div class="js_tensoConf <bean:write name="dispTenso" />">
            <div class="mt5">
              <div>
                <gsmsg:write key="sml.77" />
              </div>
              <div class="verAlignMid">
                <html:radio name="sml170Form" property="sml170SmailOp" styleId="sml170SmailOp0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_UNOPENED) %>"/><label for="sml170SmailOp0"><gsmsg:write key="sml.29" /></label>
                <html:radio styleClass="ml10" name="sml170Form" property="sml170SmailOp" styleId="sml170SmailOp1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_OPENED) %>"/><label for="sml170SmailOp1"><gsmsg:write key="cmn.mark.read" /></label>
              </div>
            </div>
            <logic:equal name="sml170Form" property="sml170ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
              <div class="mt10">
                <div class="">
                  <gsmsg:write key="sml.sml180.03" />
                </div>
                <div class="verAlignMid mt5">
                  <html:radio name="sml170Form" property="sml170HuriwakeKbn" styleId="sml170HuriwakeKbn0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>" onclick="changeEnableDisable();"/><label for="sml170HuriwakeKbn0"><gsmsg:write key="sml.42" /></label>
                  <html:radio styleClass="ml10" name="sml170Form" property="sml170HuriwakeKbn" styleId="sml170HuriwakeKbn1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_OK) %>" onclick="changeEnableDisable();"/><label for="sml170HuriwakeKbn1"><gsmsg:write key="sml.43" /></label></span>
                  <html:radio styleClass="ml10" name="sml170Form" property="sml170HuriwakeKbn" styleId="sml170HuriwakeKbn2" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_FUZAI_OK) %>" onclick="changeEnableDisable();"/><label for="sml170HuriwakeKbn2"><gsmsg:write key="sml.168" /></label></span>
                </div>
              </div>
              <div class="mt5">
                <div class="fw_b js_send_default">
                  <gsmsg:write key="sml.81" />
                </div>
                <div class="js_send_default verAlignMid w100">
                  <html:select name="sml170Form" property="sml170MailDfSelected" styleClass="wp200 mr5" onchange="changeEnableDisable();">
                    <html:optionsCollection name="sml170Form" property="sml170MailList" value="value" label="label" />
                  </html:select>
                  <html:text name="sml170Form" size="50" maxlength="50" property="sml170MailDf" styleClass="w100" />
                </div>
                <div class="fw_b js_send_kobetu">
                  <gsmsg:write key="sml.44" />
                </div>
                <div class="js_send_kobetu mb10 verAlignMid w100">
                  <html:select name="sml170Form" property="sml170Zmail1Selected" styleClass="wp200 mr5" onchange="changeEnableDisable();">
                    <html:optionsCollection name="sml170Form" property="sml170MailList" value="value" label="label" />
                  </html:select>
                  <html:text name="sml170Form" size="50" maxlength="50" property="sml170Zmail1"  styleClass="w100" />
                </div>
                <div class="fw_b js_send_kobetu js_send_fuzai">
                  <gsmsg:write key="sml.89" />
                </div>
                <div class="js_send_kobetu js_send_fuzai verAlignMid w100">
                  <html:select name="sml170Form" property="sml170Zmail2Selected" styleClass="wp200 mr5" onchange="changeEnableDisable();">
                    <html:optionsCollection name="sml170Form" property="sml170MailList" value="value" label="label" />
                  </html:select>
                  <html:text name="sml170Form" size="50" maxlength="50" property="sml170Zmail2" styleClass="w100" />
                </div>
                <div class="fw_b js_send_kobetu mt10">
                  <gsmsg:write key="sml.12" />
                </div>
                <div class="js_send_kobetu verAlignMid w100">
                  <html:select name="sml170Form" property="sml170Zmail3Selected" styleClass="wp200 mr5" onchange="changeEnableDisable();">
                    <html:optionsCollection name="sml170Form" property="sml170MailList" value="value" label="label" />
                  </html:select>
                  <html:text name="sml170Form" size="50" maxlength="50" property="sml170Zmail3"  styleClass="w100" />
                </div>
              </div>
            </logic:equal>
            <logic:notEqual name="sml170Form" property="sml170ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
              <div class="mt5 fw_b">
                <gsmsg:write key="sml.81" />
              </div>
              <div class="verAlignMid w100">
                <html:select name="sml170Form" property="sml170MailDfSelected" styleClass="wp200 mr5" onchange="changeEnableDisable();">
                  <html:optionsCollection name="sml170Form" property="sml170MailList" value="value" label="label" />
                </html:select>
                <html:text name="sml170Form" size="50" maxlength="50" property="sml170MailDf" styleClass="w100" />
              </div>
              <html:hidden property="sml170HuriwakeKbn"/>
              <html:hidden property="sml170Zmail1Selected" />
              <html:hidden property="sml170Zmail1" />
              <html:hidden property="sml170Zmail2Selected" />
              <html:hidden property="sml170Zmail2" />
              <html:hidden property="sml170Zmail3Selected" />
              <html:hidden property="sml170Zmail3" />
            </logic:notEqual>
          </div>
        </td>
      </tr>
    </table>
    <div class="footerBtn_block">
      <button type="button" id="settingBtn" class="baseBtn" value="<gsmsg:write key="cmn.setting" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
<%-- ダイアログ --%>
<div class="display_none">
  <div id="accountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" >
    <input type="hidden" id="selAccountElm" value="sml170AccountSid" />
    <input type="hidden" id="selAccountSubmit" value="true" />
    <input type="hidden" id="resetParam" value="sml170InitFlg" />
    <input type="hidden" id="sml240user" value="<bean:write name="sml170Form" property="smlViewUser" />" />

    <div class="hp450 w100 ofy_a">
      <table class="w100 h100">
        <tr>
          <td id="accountListArea"  class="txt_t"></td>
        </tr>
      </table>
    </div>
  </div>


  <div id="setKakuninPop" title="">
    <ul class="mt20 p0">
      <li class="display_inline" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="display_inline txt_t pt20 pl10">
         <gsmsg:write key="sml.170" />
      </li>
    </ul>
    <div id="accountKakuninListArea" class="pl15 pt10 hp100 wp300 ofy_s"></div>
  </div>
</div>
</body>
</html:html>