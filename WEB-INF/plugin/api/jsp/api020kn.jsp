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

</head>

<body class="body_03">
<html:form action="/api/api020kn">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="api.1" /></span><gsmsg:write key="api.api020.1" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('api020knKakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('api020knBack');">
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
        <div class="mr10">
          <html:hidden name="api020knForm" property="api020useToken"/>
          <logic:equal name="api020knForm" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>">
            <gsmsg:write key="api.api020.8" />
          </logic:equal>
          <logic:equal name="api020knForm" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>">
            <gsmsg:write key="api.api020.9" />
          </logic:equal>
          <logic:equal name="api020knForm" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>">
            <gsmsg:write key="api.api020.10" />
          </logic:equal>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="api020knForm" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="ml10  <bean:write name="nonDsp"/>">
          <html:hidden name="api020knForm" property="api020tokenIpArea"/>
          <bean:write name="api020knForm" property="api020tokenIpAreaDsp"  filter="false"/>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:equal name="api020knForm" property="api020useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:equal>
        <div class="mt10 <bean:write name="nonDsp"/>">
           <html:hidden name="api020knForm" property="api020tokenLimit"/>
           <gsmsg:write key="api.api020.11"/>:<bean:write name="api020knForm" property="api020tokenLimitDsp" />
        </div>
        <html:hidden name="api020knForm" property="api020autoDel"/>
      </td>
    </tr>
    <%-- ベーシック認証 --%>
    <tr>
      <th class="w25">
        <gsmsg:write key="api.api020.12" />
      </th>
      <td class="w75">
        <div class="mr10">
          <html:hidden name="api020knForm" property="api020useBasic"/>
          <logic:equal name="api020knForm" property="api020useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>">
            <gsmsg:write key="api.api020.8" />
          </logic:equal>
          <logic:equal name="api020knForm" property="api020useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>">
            <gsmsg:write key="api.api020.9" />
          </logic:equal>
          <logic:equal name="api020knForm" property="api020useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>">
            <gsmsg:write key="api.api020.10" />
          </logic:equal>
        </div>
        <bean:define id="nonDisp" value="" />
        <logic:notEqual name="api020knForm" property="api020useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="ml10 <bean:write name="nonDsp"/>">
          <html:hidden name="api020knForm" property="api020basicIpArea" />
          <bean:write name="api020knForm" property="api020basicIpAreaDsp" filter="false"/>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('api020knKakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('api020knBack');">
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