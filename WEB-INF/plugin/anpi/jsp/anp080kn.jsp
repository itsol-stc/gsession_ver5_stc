<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page import="jp.groupsession.v2.anp.GSConstAnpi"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp080kn">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp080BackScreen" />
    <html:hidden property="anp080BaseUrl" />
    <html:hidden property="anp080SendMail" />
    <html:hidden property="anp080SendHost" />
    <html:hidden property="anp080SendPort" />
    <html:hidden property="anp080SendEncrypt" />
    <html:hidden property="anp080SmtpAuth" />
    <html:hidden property="anp080SendUser" />
    <html:hidden property="anp080SendPass" />
    <html:hidden property="anp080UrlSetKbn" />
    <html:hidden property="anp080SvBaseUrlAuto" />
    <html:hidden property="anp080provider" />
    <html:hidden property="anp080authMethod" />
    <html:hidden property="anp080cotSid" />
    <html:hidden property="anp080oauthCompFlg" />

    <logic:notEmpty name="anp080knForm" property="anp080AdmUserList" scope="request">
      <logic:iterate id="ulBean" name="anp080knForm" property="anp080AdmUserList" scope="request">
        <input type="hidden" name="anp080AdmUserList" value="<bean:write name="ulBean" />">
      </logic:iterate>
    </logic:notEmpty>

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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp080kn.01" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="anp.anp080kn.02" />" onClick="buttonPush('anp080knconTest');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_connect.png" alt="<gsmsg:write key="anp.anp080kn.02" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_connect.png" alt="<gsmsg:write key="anp.anp080kn.02" />">
              <gsmsg:write key="anp.anp080kn.02" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('anp080knexcute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <gsmsg:write key="cmn.final" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp080knback');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">
      <div class="txt_l">
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>
      </div>
      <table class="table-left">
        <!-- 返信基本URL -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.anp080.02" />
          </th>
          <td class="w75">
            <logic:equal name="anp080knForm" property="anp080UrlSetKbn" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.URL_SETTING_AUTO) %>">
              <bean:write name="anp080knForm" property="anp080SvBaseUrlAuto" />
            </logic:equal>
            <logic:equal name="anp080knForm" property="anp080UrlSetKbn" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.URL_SETTING_MANUAL) %>">
              <bean:write name="anp080knForm" property="anp080BaseUrl" />
            </logic:equal>
          </td>
        </tr>
        <!-- 認証形式 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.auth.method" />
          </th>
          <td class="w75">
            <logic:equal name="anp080knForm" property="anp080authMethod" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) %>">
              <gsmsg:write key="cmn.auth.basic" />
            </logic:equal>
            <logic:equal name="anp080knForm" property="anp080authMethod" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) %>">
              <gsmsg:write key="cmn.auth.oauth" />
            </logic:equal>
          </td>
        </tr>
        <!-- 送信メールアドレス -->
        <tr>
          <th>
            <gsmsg:write key="anp.anp080.06" />
          </th>
          <td>
            <bean:write name="anp080knForm" property="anp080SendMail" />
          </td>
        </tr>
        <logic:equal name="anp080knForm" property="anp080authMethod" value="<%= String.valueOf(GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) %>">
        <!-- メール送信サーバ -->
        <tr>
          <th>
            <gsmsg:write key="anp.smtp.server" />
          </th>
          <td>
            <bean:write name="anp080knForm" property="anp080SendHost" />
            &nbsp;
            <gsmsg:write key="cmn.port.number" />
            :
            <bean:write name="anp080knForm" property="anp080SendPort" />
            <br>
            <gsmsg:write key="cmn.ango" />
            :
            <bean:write name="anp080knForm" property="anp080knSendEncrypt" />
          </td>
        </tr>
        <!-- SMTP認証ON/OFF -->
        <tr>
          <th>
            <gsmsg:write key="anp.anp080.08" />
          </th>
          <td>
            <logic:equal name="anp080knForm" property="anp080SmtpAuth" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SMTP_AUTH_YES) %>">
              <gsmsg:write key="anp.anp080.09" />
            </logic:equal>
            <logic:equal name="anp080knForm" property="anp080SmtpAuth" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SMTP_AUTH_NOT) %>">
              <gsmsg:write key="anp.anp080.10" />
            </logic:equal>
          </td>
        </tr>
        <!-- メール送信サーバ ユーザ名 -->
        <tr>
          <th>
            <gsmsg:write key="anp.anp080.11" />
          </th>
          <td>
            <bean:write name="anp080knForm" property="anp080SendUser" />
          </td>
        </tr>
        <!-- メール送信サーバ パスワード -->
        <tr>
          <th>
            <gsmsg:write key="anp.anp080.12" />
          </th>
          <td>
            <bean:write name="anp080knForm" property="anp080knDspSendPass" />
          </td>
        </tr>
        </logic:equal>
        <logic:notEqual name="anp080knForm" property="anp080authMethod" value="<%= String.valueOf(GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) %>">
        <!-- プロバイダ -->
        <tr>
          <th>
            <gsmsg:write key="cmn.auth.provider" />
          </th>
          <td>
            <bean:write name="anp080knForm" property="anp080knDspProvider" /><br>
            <gsmsg:write key="cmn.auth.status"/>:
            <logic:equal name="anp080knForm" property="anp080oauthCompFlg" value="<%= String.valueOf(GSConstAnpi.AUTH_DONE) %>">
              <gsmsg:write key="cmn.auth.already"/>
            </logic:equal>
            <logic:notEqual name="anp080knForm" property="anp080oauthCompFlg" value="<%= String.valueOf(GSConstAnpi.AUTH_DONE) %>">
              <gsmsg:write key="cmn.auth.yet"/>
            </logic:notEqual>
          </td>
        </tr>
        </logic:notEqual>
        <!-- 安否確認管理者 -->
        <tr>
          <th>
            <gsmsg:write key="anp.anp080.13" />
          </th>
          <td>
            <logic:notEmpty name="anp080knForm" property="anp080AdmUserLabel">
              <logic:iterate id="urBean" name="anp080knForm" property="anp080AdmUserLabel" indexId="idx">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal value="1" name="urBean" property="usrUkoFlg">
                  <bean:define id="mukoUserClass" value="mukoUser" />
                </logic:equal>
                <span class="<%=mukoUserClass%>">
                  <bean:write name="urBean" property="label" />
                </span>
                <br>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="anp.anp080kn.02" />" onClick="buttonPush('anp080knconTest');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_connect.png" alt="<gsmsg:write key="anp.anp080kn.02" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_connect.png" alt="<gsmsg:write key="anp.anp080kn.02" />">
          <gsmsg:write key="anp.anp080kn.02" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('anp080knexcute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp080knback');">
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