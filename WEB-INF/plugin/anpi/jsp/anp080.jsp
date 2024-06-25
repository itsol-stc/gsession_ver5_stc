<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<%
  String maxLengthUrl = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_BASE_URL);
  String maxLengthMail = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_MAIL);
  String maxLengthHost = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SEND_HOST);
  String maxLengthPort = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SEND_PORT);
  String maxLengthUser = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SEND_USER);
  String maxLengthPass = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_SEND_PASSWORD);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../anpi/js/anp080.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp080">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp080BackScreen" />

    <html:hidden property="anp080SvBaseUrlAuto" />
    <html:hidden property="anp080cotSid" />
    <html:hidden property="anp080oauthCompFlg" />

    <!-- header -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w85 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp080.01" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp080excute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp080back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">

      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left w100">
        <!-- 返信基本URL -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.anp080.02" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <div id="js_baseUrlForm">
              <html:text styleId="baseUrl" name="anp080Form" property="anp080BaseUrl" maxlength="<%=maxLengthUrl%>" styleClass="wp400" />
            </div>
            <div id="js_baseUrlAuto">
              <bean:write name="anp080Form" property="anp080SvBaseUrlAuto" />
            </div>
            <div class="mt10 verAlignMid">
              <html:radio styleId="urlSetKbn1" name="anp080Form" property="anp080UrlSetKbn" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.URL_SETTING_AUTO)%>" onclick="changeUrlKbn();" />
              <label for="urlSetKbn1">
                <gsmsg:write key="anp.anp080.03" />
              </label>
              <span class="ml10 verAlignMid">
                <html:radio styleId="urlSetKbn2" name="anp080Form" property="anp080UrlSetKbn" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.URL_SETTING_MANUAL)%>" onclick="changeUrlKbn();" />
                <label for="urlSetKbn2">
                  <gsmsg:write key="anp.anp080.04" />
                </label>
              </span>
            </div>
            <div>
              <gsmsg:write key="anp.anp080.05" />
            </div>
          </td>
        </tr>
        <!-- 送信メールアドレス -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.anp080.06" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <html:text name="anp080Form" property="anp080SendMail" maxlength="<%=maxLengthMail%>" styleClass="wp400" />
          </td>
        </tr>
        <!-- 認証形式 -->
        <bean:size id="providerListSize" name="anp080Form" property="anp080providerList"/>
        <logic:notEqual name="providerListSize" value="1">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.auth.method" />
          </th>
          <td class=~w75">
            <div class="mt10 verAlignMid">
              <html:radio name="anp080Form" property="anp080authMethod" styleId="anp080AuthKbn0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) %>" onclick="changeAuthMethod();"/><label for="anp080AuthKbn0"><gsmsg:write key="cmn.auth.basic" /></label>
              <html:radio name="anp080Form" property="anp080authMethod" styleClass="ml10" styleId="anp080AuthKbn1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) %>" onclick="changeAuthMethod();"/><label for="anp080AuthKbn1"><gsmsg:write key="cmn.auth.oauth" /></label>
            </div>
          </td>
        </tr>
        </logic:notEqual>
        <!-- メール送信サーバ -->
        <tr class="js_BaseAuth">
          <th class="w25 ">
            <gsmsg:write key="anp.smtp.server" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <div>
              <html:text name="anp080Form" property="anp080SendHost" maxlength="<%=maxLengthHost%>" styleClass="wp300" />
              <gsmsg:write key="cmn.port.number" />
              <html:text name="anp080Form" property="anp080SendPort" maxlength="<%=maxLengthPort%>" styleClass="wp80" />
            </div>
            <div class="mt5">
              <gsmsg:write key="cmn.ango" />:
              <html:select name="anp080Form" property="anp080SendEncrypt">
                <html:optionsCollection name="anp080Form" property="anp080AngoProtocolCombo" value="value" label="label" />
              </html:select>
              &nbsp;
              <gsmsg:write key="cmn.check.portnumber" />
            </div>
          </td>
        </tr>
        <!-- プロバイダ -->
        <tr class="js_OAuth display_none">
          <th class="w25 ">
            <gsmsg:write key="cmn.auth.provider" />
          </th>
          <td>
            <html:select name="anp080Form" property="anp080provider">
              <html:optionsCollection name="anp080Form" property="anp080providerList" value="value" label="label" />
            </html:select>
            <span class="verAlignMid">
              <button type="button" class="js_authBtn baseBtn mr20" onclick="doOAuth('anp080provider', 'anp080SendMail', 'anp080cotSid');"><gsmsg:write key="cmn.auth" /></button>
              <span class="ml10">
                <gsmsg:write key="cmn.auth.status"/>:
                <span class="js_oauthDone"><gsmsg:write key="cmn.auth.already"/></span>
                <span class="cl_fontWarn js_oauthYet"><gsmsg:write key="cmn.auth.yet"/></span>
              </span>
            </span>
          </td>
        </tr>
        <!-- SMTP認証ON/OFF -->
        <tr class="js_BaseAuth">
          <th class="w25">
            <gsmsg:write key="anp.anp080.08" />
          </th>
          <td class="w75">
            <span class="verAlignMid">
              <html:radio styleId="smtpAuth1" name="anp080Form" property="anp080SmtpAuth" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SMTP_AUTH_YES)%>" onclick="changeSendServerAuth(1);" />
              <label for="smtpAuth1">
                <gsmsg:write key="anp.anp080.09" />
              </label>
            </span>
            <span class="ml10 verAlignMid">
              <html:radio styleId="smtpAuth2" name="anp080Form" property="anp080SmtpAuth" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SMTP_AUTH_NOT)%>" onclick="changeSendServerAuth(0);" />
              <label for="smtpAuth2">
                <gsmsg:write key="anp.anp080.10" />
              </label>
            </span>
          </td>
        </tr>
        <!-- メール送信サーバ ユーザ名 -->
        <tr class="js_BaseAuth">
          <th class="w25">
            <gsmsg:write key="anp.anp080.11" />
          </th>
          <td class="w75">
            <html:text styleId="js_sendUser" name="anp080Form" property="anp080SendUser" maxlength="<%=maxLengthUser%>" styleClass="wp300" />
          </td>
        </tr>
        <!-- メール送信サーバ パスワード -->
        <tr class="js_BaseAuth">
          <th class="w25">
            <gsmsg:write key="anp.anp080.12" />
          </th>
          <td class="w75">
            <html:password styleId="js_sendPassword" name="anp080Form" property="anp080SendPass" maxlength="<%=maxLengthPass%>" styleClass="wp300" />
          </td>
        </tr>
        <!-- 安否確認管理者 -->
        <th class="w25">
          <gsmsg:write key="anp.anp080.13" />
        </th>
        <td class="w75">
          <ui:usrgrpselector name="anp080Form" property="anp080AdmUserListUI" styleClass="hp215" />
        </td>
      </table>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp080excute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp080back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>