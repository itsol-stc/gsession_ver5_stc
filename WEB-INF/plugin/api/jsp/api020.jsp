<%@page import="jp.groupsession.v2.cmn.GSConstApi"%>
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
<title>GROUPSESSION <gsmsg:write key="api.1" />&nbsp;<gsmsg:write key="api.api020.1" /><gsmsg:write key="cmn.check" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../api/js/api020.js?<%= GSConst.VERSION_PARAM %>"></script>

</head>

<body class="body_03">
<html:form action="/api/api020">
<input type="hidden" name="CMD" value="dsp">
<html:hidden property="backScreen" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--　BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="api.1" /></span><gsmsg:write key="api.api020.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('api020Ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('api020Back');">
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
    <html:errors />
  </logic:messagesPresent>
  <table class="table-left">
    <%-- トークン認証 --%>
    <tr>
      <th class="w25">
        <gsmsg:write key="api.api020.3" />
      </th>
      <td class="w75">
        <div><gsmsg:write key="api.api020.4"/></div>
        <div><gsmsg:write key="api.api020.5"/></div>
        <div class="cl_fontWarn"><gsmsg:write key="api.api020.6"/></div>
        <div class="mb10"><gsmsg:write key="api.api020.7"/></div>
        <div class=" verAlignMid w100">
          <logic:equal name="api020Form" property="api020useOtp" value="true">
            <html:radio name="api020Form" property="api020useToken" styleId="api020useToken_0" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>" onchange="buttonPush('dsp');" disabled="true"/>
            <label class="mr10 mukoUser" for="api020useToken_0"><gsmsg:write key="api.api020.8" /></label>
          </logic:equal>
          <logic:notEqual name="api020Form" property="api020useOtp" value="true">
            <html:radio name="api020Form" property="api020useToken" styleId="api020useToken_0" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>" onchange="buttonPush('dsp');"/>
            <label class="mr10" for="api020useToken_0"><gsmsg:write key="api.api020.8" /></label>
          </logic:notEqual>
          <html:radio name="api020Form" property="api020useToken" styleId="api020useToken_1" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>"  onchange="buttonPush('dsp');"/>
          <label class="mr10" for="api020useToken_1"><gsmsg:write key="api.api020.9" /></label>
          <html:radio name="api020Form" property="api020useToken" styleId="api020useToken_2" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>"  onchange="buttonPush('dsp');"/>
          <label class="mr10" for="api020useToken_2"><gsmsg:write key="api.api020.10" /></label>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="api020Form" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="mt10 <bean:write name="nonDsp"/>">
          <html:textarea name="api020Form" property="api020tokenIpArea" styleClass="wp500" rows="6"/>
          <div class="cl_fontWarn"><gsmsg:write key="main.man430.7" /></div>
          <div class="cl_fontWarn"><gsmsg:write key="main.man430.8" /></div>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:equal name="api020Form" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:equal>
        <div class="mt10 <bean:write name="nonDsp"/>">
           <gsmsg:write key="api.api020.11"/>:
           <html:select name="api020Form" property="api020tokenLimit" styleClass="js_LimitChange">
              <html:optionsCollection name="api020Form" property="api020tokenLimitOption" />
           </html:select>
           <div class="mt5 display_n js_limitFree">
             <gsmsg:write key="api.api020.17" />
           </div>
        </div>
      </td>
    <%-- ベーシック認証 --%>
    <tr>
      <th class="w25">
        <gsmsg:write key="api.api020.12" />
      </th>
      <td class="w75">
        <div><gsmsg:write key="api.api020.13"/></div>
        <div><gsmsg:write key="api.api020.14"/></div>
        <div class="mb10"><gsmsg:write key="api.api020.15"/></div>
        <div class=" verAlignMid w100">
          <html:radio name="api020Form" property="api020useBasic" styleId="api020useBasic_0" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>" onchange="buttonPush('dsp');"/>
          <label class="mr10" for="api020useBasic_0"><gsmsg:write key="api.api020.8" /></label>
          <logic:equal name="api020Form" property="api020useOtp" value="true">
            <html:radio name="api020Form" property="api020useBasic" styleId="api020useBasic_1" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>"  onchange="buttonPush('dsp');" disabled="true"/>
            <label class="mr10 mukoUser" for="api020useBasic_1"><gsmsg:write key="api.api020.9" /></label>
          </logic:equal>
          <logic:notEqual name="api020Form" property="api020useOtp" value="true">
            <html:radio name="api020Form" property="api020useBasic" styleId="api020useBasic_1" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>"  onchange="buttonPush('dsp');"/>
            <label class="mr10" for="api020useBasic_1"><gsmsg:write key="api.api020.9" /></label>
          </logic:notEqual>
          <html:radio name="api020Form" property="api020useBasic" styleId="api020useBasic_2" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>"  onchange="buttonPush('dsp');"/>
          <label class="mr10" for="api020useBasic_2"><gsmsg:write key="api.api020.10" /></label>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="api020Form" property="api020useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>

        <div class="mt10 <bean:write name="nonDsp"/>">
          <html:textarea name="api020Form" property="api020basicIpArea" styleClass="wp500" rows="6"/>
          <div class="cl_fontWarn"><gsmsg:write key="main.man430.7" /></div>
          <div class="cl_fontWarn"><gsmsg:write key="main.man430.8" /></div>
        </div>
      </td>
    </tr>

  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('api020Ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('api020Back');">
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