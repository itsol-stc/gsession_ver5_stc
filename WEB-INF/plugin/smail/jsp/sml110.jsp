<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body onload="initEnableDisable();lmtEnableDisable();">
<html:form action="/smail/sml110">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="sml110cotSid" />
<html:hidden property="sml110InitFlg" />
<html:hidden property="sml110oauthCompFlg" />
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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="sml.20" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('sml110kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sml110back');">
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
        <th class="w25">
          <gsmsg:write key="sml.80" />
        </th>
        <td class="w75">
          <gsmsg:write key="sml.sml110.11" />
          <div class="mt10 verAlignMid w100">
            <html:radio name="sml110Form" property="sml110MailForward" styleId="sml110MailForward0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>" onclick="changeEnableDisable();"/><label for="sml110MailForward0"><gsmsg:write key="sml.78" /></label>
            <html:radio name="sml110Form" property="sml110MailForward" styleClass="ml10" styleId="sml110MailForward1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_OK) %>" onclick="changeEnableDisable();"/><label for="sml110MailForward1"><gsmsg:write key="sml.79" /></label>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <bean:define id="dispUseSMTP" value="" />
          <logic:equal name="sml110Form" property="sml110MailForward" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NG) %>">
            <bean:define id="dispUseSMTP" value="display_n" />
          </logic:equal>
          <gsmsg:write key="sml.sml110.06" /><span class="cl_fontWarn js_markHissuSmtp <%=dispUseSMTP%>"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">

          <gsmsg:write key="sml.sml110.18" />
          <table class="w100 mt10 table-noBorder">
            <bean:size id="providerListSize" name="sml110Form" property="sml110providerList"/>
            <logic:notEqual name="providerListSize" value="1">
            <tr>
              <!-- 認証方法 -->
              <td class="wp 180 fw_b txt_r">
                <gsmsg:write key="cmn.auth.method" />
              </td>
              <td class="">
                <div class="mt10 verAlignMid">
                  <html:radio name="sml110Form" property="sml110authMethod" styleId="sml110AuthKbn0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) %>" onclick="changeAuthMethod();"/><label for="sml110AuthKbn0"><gsmsg:write key="cmn.auth.basic" /></label>
                  <html:radio name="sml110Form" property="sml110authMethod" styleClass="ml10" styleId="sml110AuthKbn1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) %>" onclick="changeAuthMethod();"/><label for="sml110AuthKbn1"><gsmsg:write key="cmn.auth.oauth" /></label>
                </div>
              </td>
            </tr>
            </logic:notEqual>
            <!-- SMTPサーバURL -->
            <tr class="js_BaseAuth">
              <td class="wp180 fw_b txt_r">
                <gsmsg:write key="sml.sml110.07" />
              </td>
              <td class="">
                <html:text name="sml110Form" maxlength="200" property="sml110SmtpUrl" styleClass="wp350" />
              </td>
            </tr>
            <!-- SMTPサーバポート番号 -->
            <tr class="js_BaseAuth">
              <td class="wp180 fw_b txt_r">
                <gsmsg:write key="sml.sml110.08" />
              </td>
              <td class="">
                <html:text name="sml110Form" maxlength="5" property="sml110SmtpPort" styleClass="wp80" /><span class="ml20 cl_fontWarn fs_13"><gsmsg:write key="sml.sml110.05" />
              </td>
            </tr>
            <!-- 暗号化 -->
            <tr class="js_BaseAuth">
              <td class="wp180 fw_b txt_r">
                <gsmsg:write key="cmn.ango" />
              </td>
              <td class="">
                <html:select name="sml110Form" property="sml110SmtpEncrypt" styleClass="hp25">
                  <html:optionsCollection name="sml110Form" property="sml110AngoProtocolCombo" value="value" label="label" />
                </html:select>
                <span class="display_inline"><gsmsg:write key="cmn.check.portnumber" /></span>
              </td>
            </tr>
            <!-- 転送Fromアドレス -->
            <tr>
              <td class="wp180 fw_b txt_r">
                <gsmsg:write key="sml.sml110.17" />
              </td>
              <td class="">
                <html:text name="sml110Form" maxlength="256" property="sml110FromAddress" styleClass="wp350" />
              </td>
            </tr>
            <!-- プロバイダ -->
            <tr class="js_OAuth display_none">
              <td class="wp180 fw_b txt_r">
                <gsmsg:write key="cmn.auth.provider" />
              </td>
              <td>
                <html:select name="sml110Form" property="sml110provider">
                  <html:optionsCollection name="sml110Form" property="sml110providerList" value="value" label="label" />
                </html:select>
                <span class="verAlignMid">
                  <button type="button" class="js_authBtn baseBtn mr20" onclick="doOAuth('sml110provider', 'sml110FromAddress', 'sml110cotSid');"><gsmsg:write key="cmn.auth" /></button>
                  <span class="ml10">
                    <gsmsg:write key="cmn.auth.status"/>:
                    <bean:define id="oauthFlg" name="sml110Form" property="sml110oauthCompFlg" type="java.lang.Integer"/>
                    <span class="js_oauthDone"><gsmsg:write key="cmn.auth.already"/></span>
                    <span class="cl_fontWarn js_oauthYet"><gsmsg:write key="cmn.auth.yet"/></span>
                  </span>
                </span>
              </td>
            </tr>
          </table>
          <div class="settingForm_separator js_BaseAuth">
            <div class="fs_13"><gsmsg:write key="sml.sml110.12" /></div>
            <div class="cl_fontWarn"><gsmsg:write key="sml.sml110.02" /></div>
            <table class="w100 mt10 table-noBorder">
              <tr>
                <td class="wp180 fw_b txt_r">
                  <gsmsg:write key="sml.sml110.22" />
                </td>
                <td class="">
                  <html:text name="sml110Form" maxlength="100" property="sml110SmtpUser" styleClass="wp300" />
                </td>
              </tr>
              <tr>
                <td class="wp180 fw_b txt_r">
                  <gsmsg:write key="sml.sml110.21" />
                </td>
                <td class="">
                  <html:password name="sml110Form" maxlength="100" property="sml110SmtpPass" styleClass="wp300" />
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="sml.sml110.20" />
        </th>
        <td class="w75">
          <div class="fs_13"><gsmsg:write key="sml.sml110.10" /></div>
          <div class="fs_13 cl_fontWarn"><gsmsg:write key="sml.sml110.04" /><br><gsmsg:write key="sml.sml110.01" /></div>
          <div class="mt10 verAlignMid">
            <html:radio name="sml110Form" property="sml110FwLmtKbn" styleId="sml110FwLmtKbn1" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_LIMIT) %>" onclick="lmtEnableDisable();"/><label for="sml110FwLmtKbn1"><gsmsg:write key="cmn.do.limit" /></label>
            <html:radio name="sml110Form" property="sml110FwLmtKbn" styleClass="ml10" styleId="sml110FwLmtKbn0" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAIL_FORWARD_NO_LIMIT) %>" onclick="lmtEnableDisable();"/><label for="sml110FwLmtKbn0"><gsmsg:write key="cmn.not.limit" /></label>
          </div>
          <div id="lmtinput" class="mt10">
            <div class="fs_13">
              <span clsas=""><gsmsg:write key="sml.sml110.19" /></span>
              <span class="cl_fontWarn"><gsmsg:write key="sml.sml110.03" /></span>
            </div>
            <div class="mt10">
              <html:textarea name="sml110Form" property="sml110FwlmtTextArea" styleClass="wp400" rows="6"></html:textarea>
            </div>
            <div class="">
              <button type="button" class="baseBtn" value="<gsmsg:write key="sml.sml110.23" />" onClick="buttonPush('sml110lmtCheck');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_check.png" alt="<gsmsg:write key="sml.sml110.23" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_checked.png" alt="<gsmsg:write key="sml.sml110.23" />">
                <gsmsg:write key="sml.sml110.23" />
              </button>
            </div>

            <logic:notEmpty name="sml110Form" property="sml110FwCheckList">
              <div class="pt10"><gsmsg:write key="sml.sml110.14" /></div>
            </logic:notEmpty>

            <logic:equal name="sml110Form" property="sml110CheckFlg" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.FW_CHECK_ON) %>">
              <div class="pt10"><gsmsg:write key="sml.sml110.24" /></div>
            </logic:equal>

            <logic:notEmpty name="sml110Form" property="sml110FwCheckList">
              <table class="table-top w100 mt0">
                <tr>
                  <th class="w40 table_title-color">
                    <gsmsg:write key="cmn.user.name" />
                  </th>
                  <th class="w60 table_title-color">
                    <gsmsg:write key="sml.sml110.25" />
                  </th>
                </tr>
                <logic:iterate id="lmtCheckList" name="sml110Form" property="sml110FwCheckList" indexId="idx">
                  <tr>
                    <td class="w40">
                      <bean:write name="lmtCheckList" property="usrNameSei" /><span class="ml10"><bean:write name="lmtCheckList" property="usrNameMei" /></span>
                    </td>
                    <td class="w60">
                      <logic:equal name="lmtCheckList" property="fwKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.FW_CHECK_MODE_DF) %>">
                        <bean:write name="lmtCheckList" property="fwAddDf" />
                      </logic:equal>
                      <logic:equal name="lmtCheckList" property="fwKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.FW_CHECK_MODE_ZAISEKI) %>">
                        <bean:write name="lmtCheckList" property="fwAdd1" />
                      </logic:equal>
                      <logic:equal name="lmtCheckList" property="fwKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.FW_CHECK_MODE_HUZAI) %>">
                        <bean:write name="lmtCheckList" property="fwAdd2" />
                      </logic:equal>
                      <logic:equal name="lmtCheckList" property="fwKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.FW_CHECK_MODE_OTHER) %>">
                        <bean:write name="lmtCheckList" property="fwAdd3" />
                      </logic:equal>
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:notEmpty>
          </div>
        </td>
      </tr>
    </table>
    <div class="footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('sml110kakunin');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sml110back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>