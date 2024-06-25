<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/smail/" prefix="smail" %>
<%@ taglib tagdir="/WEB-INF/tags/ui/" prefix="ui" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.sml.sml250.Sml250Form" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.CMDMODE_EDIT);
%>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<jsp:include page="/WEB-INF/plugin/smail/jsp/smlatesakisel.jsp" />
<jsp:include page="/WEB-INF/plugin/smail/jsp/sml010_message.jsp" />
<script src="../smail/js/sml250.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION <gsmsg:write key="wml.wml040.05" /></title>
</head>

<body onunload="windowClose();">

<html:form styleId="sml250Form" action="/smail/sml250">

<logic:notEqual name="sml250Form" property="smlAccountMode" value="0">
<input type="hidden" name="helpPrm" value="" />
</logic:notEqual>

<logic:equal name="sml250Form" property="smlAccountMode" value="0">
<input type="hidden" name="helpPrm" value="3" />
</logic:equal>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="smlCmdMode" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlAccountSid" />
<html:hidden property="sml010adminUser" />
<html:hidden property="sml250initFlg" />
<html:hidden property="sml250AccountKbn" />
<html:hidden property="sml250DefActUsrSid" />
<html:hidden property="sml250elementKbn" />
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
<html:hidden property="sml250autoDelKbn" />
<html:hidden property="sml250tensoKbn" />
<html:hidden property="sml250SelTab" />

<logic:notEmpty name="sml250Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml250Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>


<logic:notEqual name="sml250Form" property="sml250ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
<html:hidden property="sml250HuriwakeKbn" />
<html:hidden property="sml250Zmail1Selected" />
<html:hidden property="sml250Zmail1" />
<html:hidden property="sml250Zmail2Selected" />
<html:hidden property="sml250Zmail2" />
<html:hidden property="sml250Zmail3Selected" />
<html:hidden property="sml250Zmail3" />
</logic:notEqual>
<html:hidden property="sml250ZaisekiPlugin" />


<bean:define id="accountMode" name="sml250Form" property="smlAccountMode" type="java.lang.Integer" />
<bean:define id="cmdMode" name="sml250Form" property="smlCmdMode" type="java.lang.Integer" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

  <div class="kanriPageTitle w80 mrl_auto">
    <ul>
      <% if (accountMode == 2 && cmdMode == 0) { %>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml040.05" />
        </li>
      <% } else if (accountMode == 2 && cmdMode == 1) { %>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.98" />
        </li>
      <% } else if (accountMode != 2 && cmdMode == 0) { %>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.preferences2" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml040.05" />
        </li>
      <% } else if (accountMode != 2 && cmdMode == 1) { %>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.preferences2" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.98" />
        </li>
      <% } %>
      <li>
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <logic:equal name="sml250Form" property="smlCmdMode" value="1">
            <logic:equal name="sml250Form" property="sml250AccountKbn" value="1">
              <logic:equal name="sml250Form" property="sml250CanDelFlg" value="0">
                <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('deleteAccount');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:equal>
            </logic:equal>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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
    <!-- 基本設定 -->
    <table id="normal_table" class="table-left w100 mt0">
      <tr>
        <th class="w25">
          <div class="display_inline">
            <gsmsg:write key="wml.96" />
            <logic:equal value="0" name="cmdMode">
              <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
            </logic:equal>
          </div>
        </th>
        <td class="w75">
          <logic:equal name="sml250Form" property="sml250AccountKbn" value="1">
            <html:text name="sml250Form" property="sml250name" styleClass="wp250" maxlength="100" />
          </logic:equal>
          <logic:equal name="sml250Form" property="sml250AccountKbn" value="0">
            <html:hidden property="sml250name" />
            <bean:write name="sml250Form" property="sml250name" />
          </logic:equal>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.memo" />
        </th>
        <td class="w75">
          <html:textarea name="sml250Form" property="sml250biko" styleClass="wp400" rows="10" />
        </td>
      </tr>
      <logic:equal name="sml250Form" property="sml250acntUserFlg" value="true">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.employer" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">
          <ui:usrgrpselector name="sml250Form" property="sml250userKbnUserUI" styleClass="hp300" />


        </td>
      </tr>
      </logic:equal>
      <logic:notEqual name="sml250Form" property="sml250acntUserFlg" value="true">
        <logic:notEmpty name="sml250Form" property="sml250userKbnUser">
        <logic:iterate id="permitId" name="sml250Form" property="sml250userKbnUser">
          <input type="hidden" name="sml250userKbnUser" value="<bean:write name="permitId" />">
        </logic:iterate>
        </logic:notEmpty>
      </logic:notEqual>
    </table>

    <logic:equal name="sml250Form" property="smlAccountMode" value="2">
      <logic:equal name="sml250Form" property="sml250tensoKbn" value="1">
        <!-- 転送設定 -->
        <table id="tenso_table" class="table-left w100 display_none mt0">
          <tr>
            <th class="w25"><gsmsg:write key="sml.sml100.03" /></td>
            <td class="w75">
              <div class="verAlignMid">
                <html:radio styleId="sml250tensoSetKbn_0" name="sml250Form" property="sml250tensoSetKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NO_SET) %>" onclick="changeTensoArea();" /><label for="sml250tensoSetKbn_0"><gsmsg:write key="cmn.noset" /></label>
                <html:radio styleClass="ml10" styleId="sml250tensoSetKbn_1" name="sml250Form" property="sml250tensoSetKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_SET) %>" onclick="changeTensoArea();" /><label for="sml250tensoSetKbn_1"><gsmsg:write key="cmn.setting.do" /></label></span>
              </div>
            </td>
          </tr>
          <tr class="js_sml_tenso_set display_none">
            <th class="w25">
              <div class="display_inline">
                <gsmsg:write key="cmn.target" />
                <logic:equal name="sml250Form" property="sml250ObjKbn" value="1">
                  <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
                </logic:equal>
              </div>
            </td>
            <td class="w75">
              <div class="verAlignMid">
                <html:radio styleId="sml250Obj_0" name="sml250Form" property="sml250ObjKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.sml250.Sml250Biz.TAISYO_ALL) %>" onclick="changeTensoUsrKbn();" /><label for="sml250Obj_0"><gsmsg:write key="cmn.alluser" /></label>
                <html:radio styleClass="ml10" styleId="sml250Obj_1" name="sml250Form" property="sml250ObjKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.sml250.Sml250Biz.TAISYO_SELECT) %>" onclick="changeTensoUsrKbn();" /><label for="sml250Obj_1"><gsmsg:write key="cmn.named.user" /></label>
                <html:checkbox styleClass="ml20" name="sml250Form" property="sml250PassKbn" value="1" styleId="sml180PassKbn" /><label for="sml180PassKbn"><gsmsg:write key="sml.sml180.07" /></label>
              </div>
              <logic:equal name="sml250Form" property="sml250ObjKbn" value="1">
                <ui:multiselector name="sml250Form" property="sml250tensoTargetUI" styleClass="hp300" />
              </logic:equal>
            </td>
          </tr>
          <tr class="js_sml_tenso_set display_none">
            <th class="w25">
              <div class="display_inline">
                <gsmsg:write key="sml.80" />
                <bean:define id="hissuDsp" value="" />
                <logic:equal value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>" name="sml250Form" property="sml250MailFw" >
                  <bean:define id="hissuDsp" value="display_none" />
                </logic:equal>
                <span class="cl_fontWarn js_settingTenso_hissuMark <%=hissuDsp %>"><gsmsg:write key="cmn.comments" /></span>
              </div>
            </th>
            <td class="w75">
              <div class=""><gsmsg:write key="sml.05" /></div>
              <div class="mt5 verAlignMid">
                <html:radio name="sml250Form" property="sml250MailFw" styleId="sml250MailFw0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>" onclick="changeEnableDisableTenso();"/>
                <label for="sml250MailFw0" class="mr10"><gsmsg:write key="sml.78" /></label>
                <html:radio name="sml250Form" property="sml250MailFw" styleId="sml250MailFw1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_OK) %>" onclick="changeEnableDisableTenso();"/>
                <label for="sml250MailFw1"><gsmsg:write key="sml.79" /></label>
              </div>
              <bean:define id="dispTenso" value="" />
              <logic:equal name="sml250Form" property="sml250MailFw" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>">
                <bean:define id="dispTenso" value="display_none" />
              </logic:equal>
              <div class="js_tensoConf <bean:write name="dispTenso" />">
                <div class="mt5">
                  <div class="">
                    <gsmsg:write key="sml.77" />
                  </div>
                  <div class="verAlignMid w100">
                    <html:radio name="sml250Form" property="sml250SmailOp" styleId="sml180SmailOp0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_UNOPENED) %>"/>
                    <label for="sml180SmailOp0"><gsmsg:write key="sml.29" /></label>
                    <html:radio styleClass="ml10" name="sml250Form" property="sml250SmailOp" styleId="sml180SmailOp1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_OPENED) %>"/>
                    <label for="sml180SmailOp1"><gsmsg:write key="cmn.mark.read" /></label>
                  </div>
                </div>
                <bean:define id="dispSingleMailTo" value="" />
                <bean:define id="dispZaisekiMailTo" value="display_none" />
                <bean:define id="dispFuzaiMailTo" value="display_none" />
                <bean:define id="dispOtherMailTo" value="display_none" />

                <logic:notEqual name="sml250Form" property="sml250ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
                  <div class="mt10"></div>
                </logic:notEqual>

                <logic:equal name="sml250Form" property="sml250ZaisekiPlugin" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.PLUGIN_USE) %>">
                  <div class="mt10">
                    <div class=""><gsmsg:write key="sml.sml180.03" /></div>
                    <div class="mt5 verAlignMid">
                      <html:radio name="sml250Form" property="sml250HuriwakeKbn" styleId="sml250HuriwakeKbn0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>" onclick="changeEnableDisableTenso();"/><label for="sml250HuriwakeKbn0"><gsmsg:write key="sml.42" /></label>
                      <html:radio styleClass="ml10" name="sml250Form" property="sml250HuriwakeKbn" styleId="sml250HuriwakeKbn1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_OK) %>" onclick="changeEnableDisableTenso();"/><label for="sml250HuriwakeKbn1"><gsmsg:write key="sml.43" /></label>
                      <html:radio styleClass="ml10" name="sml250Form" property="sml250HuriwakeKbn" styleId="sml250HuriwakeKbn2" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_FUZAI_OK) %>" onclick="changeEnableDisableTenso();"/><label for="sml250HuriwakeKbn2"><gsmsg:write key="sml.168" /></label>
                    </div>
                  </div>
                  <logic:notEqual name="sml250Form" property="sml250HuriwakeKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>">
                    <bean:define id="dispSingleMailTo" value="display_none" />
                  </logic:notEqual>
                  <logic:equal name="sml250Form" property="sml250HuriwakeKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_OK) %>">
                    <bean:define id="dispZaisekiMailTo" value="" />
                    <bean:define id="dispFuzaiMailTo" value="" />
                    <bean:define id="dispOtherMailTo" value="" />
                  </logic:equal>
                  <logic:equal name="sml250Form" property="sml250HuriwakeKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_FUZAI_OK) %>">
                    <bean:define id="dispFuzaiMailTo" value="" />
                  </logic:equal>
                  <div class="mt5">
                  </div>
                  <div class="js_zaisekiMailTo mb5 <bean:write name="dispZaisekiMailTo" />">
                    <div class="fw_b">
                      <gsmsg:write key="sml.44" />
                    </div>
                    <div class="verAlignMid w100">
                      <html:select styleClass="txt_m mr5" name="sml250Form" property="sml250Zmail1Selected" onchange="changeEnableDisableTenso();">
                        <html:optionsCollection name="sml250Form" property="sml250MailList" value="value" label="label" />
                      </html:select>
                      <html:text name="sml250Form" maxlength="50" property="sml250Zmail1" styleClass="w100" />
                    </div>
                  </div>
                  <div class="js_fuzaiMailTo <bean:write name="dispFuzaiMailTo" />">
                    <div class="fw_b">
                      <gsmsg:write key="sml.89" />
                    </div>
                    <div class="verAlignMid w100">
                      <html:select styleClass="txt_m mr5" name="sml250Form" property="sml250Zmail2Selected" onchange="changeEnableDisableTenso();">
                        <html:optionsCollection name="sml250Form" property="sml250MailList" value="value" label="label" />
                      </html:select>
                      <html:text name="sml250Form" maxlength="50" property="sml250Zmail2" styleClass="w100" />
                    </div>
                  </div>
                  <div class="js_otherMailTo mt5 <bean:write name="dispOtherMailTo" />">
                    <div class="fw_b">
                      <gsmsg:write key="sml.12" />
                    </div>
                    <div class="verAlignMid w100">
                      <html:select styleClass="txt_m mr5" name="sml250Form" property="sml250Zmail3Selected" onchange="changeEnableDisableTenso();">
                        <html:optionsCollection name="sml250Form" property="sml250MailList" value="value" label="label" />
                      </html:select>
                      <html:text name="sml250Form" maxlength="50" property="sml250Zmail3" styleClass="w100" />
                    </div>
                  </div>
                </logic:equal>
                <div class="js_mailDfSelect <bean:write name="dispSingleMailTo" />">
                  <div class="fw_b">
                    <gsmsg:write key="sml.81" />
                  </div>
                  <div class="verAlignMid w100">
                    <html:select styleClass="txt_m mr5" name="sml250Form" property="sml250MailDfSelected" onchange="changeEnableDisableTenso();">
                      <html:optionsCollection name="sml250Form" property="sml250MailList" value="value" label="label" />
                    </html:select>
                    <html:text name="sml250Form" maxlength="50" property="sml250MailDf" styleClass="wp250" />
                  </div>
                </div>

              </div>
            </td>
          </tr>
        </table>
      </logic:equal>
    </logic:equal>

    <logic:notEqual name="sml250Form" property="sml250autoDelKbn" value="0">
      <table id="auto_del_table" class="table-left w100 display_none mt0">
        <tr>
          <th class="w25">
            <gsmsg:write key="sml.50" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="sml250Form" property="sml250JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml250JdelKbn_0" onclick="setDispState(this.form.sml250JdelKbn, this.form.sml250JYear, this.form.sml250JMonth)" />
              <label for="sml250JdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
              <html:radio styleClass="ml10"  name="sml250Form" property="sml250JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml250JdelKbn_1" onclick="setDispState(this.form.sml250JdelKbn, this.form.sml250JYear, this.form.sml250JMonth)" />
              <label for="sml250JdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
            </div>
            <div class="mt5"></div>
            <logic:notEmpty name="sml250Form" property="sml250YearLabelList">
              <html:select property="sml250JYear" styleClass="wp100">
                <html:optionsCollection name="sml250Form" property="sml250YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="sml250Form" property="sml250MonthLabelList">
              <html:select property="sml250JMonth" styleClass="wp100">
                <html:optionsCollection name="sml250Form" property="sml250MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="sml.52" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="sml250Form" property="sml250SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml250SdelKbn_0" onclick="setDispState(this.form.sml250SdelKbn, this.form.sml250SYear, this.form.sml250SMonth)" />
              <label for="sml250SdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
              <html:radio styleClass="ml10" name="sml250Form" property="sml250SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml250SdelKbn_1" onclick="setDispState(this.form.sml250SdelKbn, this.form.sml250SYear, this.form.sml250SMonth)" />
              <label for="sml250SdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
            </div>
            <div class="mt5"></div>
            <logic:notEmpty name="sml250Form" property="sml250YearLabelList">
              <html:select property="sml250SYear" styleClass="wp100">
                <html:optionsCollection name="sml250Form" property="sml250YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="sml250Form" property="sml250MonthLabelList">
              <html:select property="sml250SMonth" styleClass="wp100">
                <html:optionsCollection name="sml250Form" property="sml250MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="sml.51" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="sml250Form" property="sml250WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml250WdelKbn_0" onclick="setDispState(this.form.sml250WdelKbn, this.form.sml250WYear, this.form.sml250WMonth)" />
              <label for="sml250WdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
              <html:radio styleClass="ml10" name="sml250Form" property="sml250WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml250WdelKbn_1" onclick="setDispState(this.form.sml250WdelKbn, this.form.sml250WYear, this.form.sml250WMonth)" />
              <label for="sml250WdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
            </div>
            <div class="mt5">
              <logic:notEmpty name="sml250Form" property="sml250YearLabelList">
                <html:select property="sml250WYear" styleClass="wp100">
                  <html:optionsCollection name="sml250Form" property="sml250YearLabelList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <logic:notEmpty name="sml250Form" property="sml250MonthLabelList">
                <html:select property="sml250WMonth" styleClass="wp100">
                  <html:optionsCollection name="sml250Form" property="sml250MonthLabelList" value="value" label="label" />
                </html:select>
                <gsmsg:write key="cmn.after.data" />
              </logic:notEmpty>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="sml.49" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="sml250Form" property="sml250DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml250DdelKbn_0" onclick="setDispState(this.form.sml250DdelKbn, this.form.sml250DYear, this.form.sml250DMonth)" />
              <label for="sml250DdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
              <html:radio styleClass="ml10" name="sml250Form" property="sml250DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml250DdelKbn_1" onclick="setDispState(this.form.sml250DdelKbn, this.form.sml250DYear, this.form.sml250DMonth)" />
              <label for="sml250DdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
            </div>
            <div class="mt5">
              <logic:notEmpty name="sml250Form" property="sml250YearLabelList">
                <html:select property="sml250DYear" styleClass="wp100">
                  <html:optionsCollection name="sml250Form" property="sml250YearLabelList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <logic:notEmpty name="sml250Form" property="sml250MonthLabelList">
                <html:select property="sml250DMonth" styleClass="wp100">
                  <html:optionsCollection name="sml250Form" property="sml250MonthLabelList" value="value" label="label" />
                </html:select>
                <gsmsg:write key="cmn.after.data" />
              </logic:notEmpty>
            </div>
          </td>
        </tr>
      </table>
    </logic:notEqual>
    <!-- その他 -->
    <table id="other_table"  class="table-left w100 table-fixed display_none mt0">
      <tr><%-- 自動TO --%>
        <th class="w25"><gsmsg:write key="wml.52" /></th>
        <td class="w75 p0">
          <div class="verAlignMid w100 " >
            <button type="button" class="js_mailSendSelBtn iconBtn-border wp23hp23 ml5 mr5">
              <img class="btn_classicImg-display m0" src="../common/images/classic/icon_add.png" >
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" >
              <span class="js_mailSendSelBtn_data"
                     data-displayname="<gsmsg:write key="wml.52" />"
                     data-addarea="#atesaki_to_area"
                     data-inputname="sml250AutoDestToUsrSid"
                   />

            </button>
            <div class="w100 ">
              <%
                Sml250Form form = (Sml250Form)request.getAttribute("sml250Form");
                List toList = form.getSml250AutoDestToLabelList();
                int toSize = 0;
                if (toList != null && toList.size() > 0) {
                    toSize = toList.size();
                }
                pageContext.setAttribute("toSize", Integer.valueOf(toSize));
              %>
              <bean:define id="clsScr" value="" />
              <logic:greaterThan name="toSize" value="3">
                <bean:define id="clsScr" value="sendAtesakiList-scr" />
              </logic:greaterThan>
              <div id="atesaki_to_area" class="js_selectAtesakiArea  pt5 pr5 mb5 <%= clsScr%>">
                <logic:notEmpty  name="sml250Form" property="sml250AutoDestToLabelList">
                  <logic:iterate id="user" name="sml250Form" property="sml250AutoDestToLabelList" type="UsrLabelValueBean">
                    <smail:selectedAccountList user="<%=user %>" inputName="sml250AutoDestToUsrSid" />
                  </logic:iterate>
                </logic:notEmpty>
              </div>
              <a href="#!" class="js_atesakiAllDisp fs_12 "><%--
            --%><logic:greaterThan name="toSize" value="3"><%--
               --%><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.show" /><%--
             --%></logic:greaterThan><%--
           --%></a>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25"><gsmsg:write key="wml.53" /></th>
        <td class="w75">
          <div class="verAlignMid w100" >
            <button type="button" class="js_mailSendSelBtn iconBtn-border wp23hp23 mr5">
              <img class="btn_classicImg-display m0" src="../common/images/classic/icon_add.png" >
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" >
              <span class="js_mailSendSelBtn_data"
                data-displayname="<gsmsg:write key="wml.53" />"
                data-addarea="#atesaki_cc_area"
                data-inputname="sml250AutoDestCcUsrSid"/>
            </button>
            <div class="w100 ">
              <%
                List ccList = form.getSml250AutoDestCcLabelList();
                int ccSize = 0;
                if (ccList != null && ccList.size() > 0) {
                    ccSize = ccList.size();
                }
                pageContext.setAttribute("ccSize", Integer.valueOf(ccSize));
              %>

              <bean:define id="clsScr" value="" />
              <logic:greaterThan name="ccSize" value="3">
                <bean:define id="clsScr" value="sendAtesakiList-scr" />
              </logic:greaterThan>
              <div id="atesaki_cc_area" class="js_selectAtesakiArea <%= clsScr%> pt5 pr5 mb5 ">
                <logic:notEmpty  name="sml250Form" property="sml250AutoDestCcLabelList">
                  <logic:iterate id="user" name="sml250Form" property="sml250AutoDestCcLabelList" type="UsrLabelValueBean">
                    <smail:selectedAccountList user="<%=user %>" inputName="sml250AutoDestCcUsrSid"/>
                  </logic:iterate>
                </logic:notEmpty>
              </div>
              <a href="#!" class="js_atesakiAllDisp fs_12 "><%--
            --%><logic:greaterThan name="ccSize" value="3"><%--
               --%><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.show" /><%--
             --%></logic:greaterThan><%--
           --%></a>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25"><gsmsg:write key="wml.54" /></th>
        <td class="w75">
          <div class="verAlignMid w100" >
            <button type="button" class="js_mailSendSelBtn iconBtn-border wp23hp23 mr5">
              <img class="btn_classicImg-display m0" src="../common/images/classic/icon_add.png" >
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" >
              <span class="js_mailSendSelBtn_data"
                data-displayname="<gsmsg:write key="wml.54" />"
                data-addarea="#atesaki_bcc_area"
                data-inputname="sml250AutoDestBccUsrSid"/>
            </button>
            <div  class="w100 ">
              <%
                List bccList = form.getSml250AutoDestBccLabelList();
                int bccSize = 0;
                if (bccList != null && bccList.size() > 0) {
                    bccSize = bccList.size();
                }
                pageContext.setAttribute("bccSize", Integer.valueOf(bccSize));
              %>

              <bean:define id="clsScr" value="" />
              <logic:greaterThan name="bccSize" value="3">
                <bean:define id="clsScr" value="sendAtesakiList-scr" />
              </logic:greaterThan>
              <div id="atesaki_bcc_area" class="js_selectAtesakiArea <%= clsScr%> pr5 pt5 mb5">
                <logic:notEmpty  name="sml250Form" property="sml250AutoDestBccLabelList">
                  <logic:iterate id="user" name="sml250Form" property="sml250AutoDestBccLabelList"  type="UsrLabelValueBean">
                    <smail:selectedAccountList user="<%=user %>" inputName="sml250AutoDestBccUsrSid"/>
                  </logic:iterate>
                </logic:notEmpty>
              </div>
              <a href="#!" class="js_atesakiAllDisp fs_12 "><%--
            --%><logic:greaterThan name="bccSize" value="3"><%--
               --%><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.show" /><%--
             --%></logic:greaterThan><%--
           --%></a>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.format." />
        </th>
        <td class="w75">
          <div class="verAlignMid">
            <html:radio name="sml250Form" property="sml250sendType" styleId="sendType1" value="0" />
            <label for="sendType1"><gsmsg:write key="cmn.standard" /></label>
            <span class="ml10"></span>
            <html:radio name="sml250Form" property="sml250sendType" styleId="sendType2" value="1" />
            <label for="sendType2"><gsmsg:write key="wml.110" /></label>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.theme" />
        </th>
        <td class="w75">
          <html:select name="sml250Form" property="sml250theme">
            <logic:notEmpty name="sml250Form" property="sml250themeList">
              <html:optionsCollection name="sml250Form" property="sml250themeList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>

        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.quotes" />
        </th>
        <td class="w75">
          <html:select name="sml250Form" property="sml250quotes">
            <logic:notEmpty name="sml250Form" property="sml250quotesList">
              <html:optionsCollection name="sml250Form" property="sml250quotesList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
        </td>
      </tr>
    </table>
    <div class="footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:equal name="sml250Form" property="smlCmdMode" value="1">
        <logic:equal name="sml250Form" property="sml250AccountKbn" value="1">
          <logic:equal name="sml250Form" property="sml250CanDelFlg" value="0">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccount');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete2" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete2" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
        </logic:equal>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>


  </div>










<%-- ダイアログ --%>
<div class="display_none">
  <div id="messagePop" title="" >
    <ul class="mt20 p0">
      <li class="display_inline" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="display_inline txt_t pt5 pl10">
        <span id="messageArea"></span>
      </li>
    </ul>
  </div>

  <div id="clearTensoPop" title="" >
    <ul class="mt20 p0">
      <li class="display_inline" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="display_inline txt_t pt5 pl10">
        <gsmsg:write key="sml.169" />
      </li>
    </ul>
  </div>
</div>
</html:form>
<smail:accountSelector name="sml250Form" property="sml250AtesakiUI"/>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>




</body>
</html:html>