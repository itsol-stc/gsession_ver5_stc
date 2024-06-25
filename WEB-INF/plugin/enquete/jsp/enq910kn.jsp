<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Script-Type" content="text/javascript">
<META http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION <gsmsg:write key="enq.plugin" /> <gsmsg:write key="enq.enq900.01" /></title>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/enquete/enq910kn">
<!-- BODY -->
<input type="hidden" name="CMD">
<html:hidden property="cmd" />
<html:hidden property="backScreen" />
<html:hidden property="enq910initFlg" />
<html:hidden property="enq910TaisyoKbn" />

<!-- 検索条件hidden -->
<%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp" %>

<logic:notEmpty name="enq910knForm" property="enq010priority">
<logic:iterate id="svPriority" name="enq910knForm" property="enq010priority">
  <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq910knForm" property="enq010status">
<logic:iterate id="svStatus" name="enq910knForm" property="enq010status">
  <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq910knForm" property="enq010svPriority">
<logic:iterate id="svPriority" name="enq910knForm" property="enq010svPriority">
  <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq910knForm" property="enq010svStatus">
<logic:iterate id="svStatus" name="enq910knForm" property="enq010svStatus">
  <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq910knForm" property="enq010statusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq910knForm" property="enq010statusAnsOver">
  <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq910knForm" property="enq010svStatusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq910knForm" property="enq010svStatusAnsOver">
  <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="enq910knForm" property="enq910MakeSenderList" scope="request">
    <logic:iterate id="ulBean" name="enq910knForm" property="enq910MakeSenderList" scope="request">
    <input type="hidden" name="enq910MakeSenderList" value="<bean:write name="ulBean" />">
    </logic:iterate>
</logic:notEmpty>

<!-- header -->
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="cmn.preferences.kn" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('enq910kncommit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq910knback');">
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
  <!-- 対象者区分 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="enq.enq910.01"/>
      </th>
      <td class="w75">
        <logic:equal name="enq910knForm" property="enq910knTaisyoKbn" value="<%= String.valueOf(GSConstEnquete.CONF_TAISYO_LIMIT) %>" >
          <gsmsg:write key="enq.enq910.02" />
        </logic:equal>
        <logic:notEqual name="enq910knForm" property="enq910knTaisyoKbn" value="<%= String.valueOf(GSConstEnquete.CONF_TAISYO_LIMIT) %>" >
          <gsmsg:write key="enq.enq910.03" />
        </logic:notEqual>
      </td>
    </tr>
    <logic:equal name="enq910knForm" property="enq910knTaisyoKbn" value="<%= String.valueOf(GSConstEnquete.CONF_TAISYO_LIMIT) %>" >
    <!-- アンケート発信対象者 -->
    <tr>
      <th>
        <gsmsg:write key="enq.70"/>
      </th>
      <td>
        <logic:notEmpty name="enq910knForm" property="enq910knAddSenderLabel" scope="request">
        <logic:iterate id="labelValueBean" name="enq910knForm" property="enq910knAddSenderLabel" scope="request" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
        <span class="<% if (labelValueBean.getUsrUkoFlg() == 1) { %>mukoUser<% } %>"><bean:write name="labelValueBean" property="label" /></span><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('enq910kncommit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq910knback');">
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