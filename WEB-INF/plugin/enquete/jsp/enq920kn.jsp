<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>

<!DOCTYPE html>

<html:html lang="true">
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Script-Type" content="text/javascript">
<META http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION <gsmsg:write key="enq.plugin" /> <gsmsg:write key="enq.enq920kn.01"/></title>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/enquete/enq920kn">
<!-- BODY -->
<input type="hidden" name="CMD">
<html:hidden property="cmd" />
<html:hidden property="backScreen" />

<!-- 検索条件hidden -->
<%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp" %>

<logic:notEmpty name="enq920knForm" property="enq010priority">
<logic:iterate id="svPriority" name="enq920knForm" property="enq010priority">
  <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq920knForm" property="enq010status">
<logic:iterate id="svStatus" name="enq920knForm" property="enq010status">
  <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq920knForm" property="enq010svPriority">
<logic:iterate id="svPriority" name="enq920knForm" property="enq010svPriority">
  <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq920knForm" property="enq010svStatus">
<logic:iterate id="svStatus" name="enq920knForm" property="enq010svStatus">
  <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq920knForm" property="enq010statusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq920knForm" property="enq010statusAnsOver">
  <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq920knForm" property="enq010svStatusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq920knForm" property="enq010svStatusAnsOver">
  <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="enq920knForm" property="enq920TypeListToList">
  <logic:iterate id="typeData" name="enq920knForm" property="enq920TypeListToList" indexId="idx">
    <input type="hidden" name="enq920TypeList[<%= String.valueOf(idx.intValue()) %>].etpSid" value='<bean:write name="typeData" property="etpSid"/>' >
    <input type="hidden" name="enq920TypeList[<%= String.valueOf(idx.intValue()) %>].etpDspSeq" value='<bean:write name="typeData" property="etpDspSeq"/>' >
    <input type="hidden" name="enq920TypeList[<%= String.valueOf(idx.intValue()) %>].etpName" value='<bean:write name="typeData" property="etpName"/>' >
    <input type="hidden" name="enq920TypeList[<%= String.valueOf(idx.intValue()) %>].emnCnt" value='<bean:write name="typeData" property="emnCnt"/>' >
    <input type="hidden" name="enq920TypeList[<%= String.valueOf(idx.intValue()) %>].emnResEnd" value='<bean:write name="typeData" property="emnResEnd"/>' >
    <input type="hidden" name="enq920TypeList[<%= String.valueOf(idx.intValue()) %>].emnOpenEnd" value='<bean:write name="typeData" property="emnOpenEnd"/>' >
  </logic:iterate>
</logic:notEmpty>

<div id="saveTbl">
  <logic:notEmpty name="enq920knForm" property="enq920DelListToList">
    <logic:iterate id="delList" name="enq920knForm" property="enq920DelListToList" indexId="idx">
      <div class="save">
        <input type="hidden" name="enq920DelList[<%= String.valueOf(idx.intValue()) %>].etpSid" value='<bean:write name="delList" property="etpSid"/>'>
      </div>
    </logic:iterate>
  </logic:notEmpty>
</div>

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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="enq.enq920kn.01"/>
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('enq920kncommit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq920knback');">
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
    <tr>
      <th class="w25">
        <gsmsg:write key="enq.enq920.01"/>
      </th>
      <td class="w75">
        <logic:notEmpty name="enq920knForm" property="enq920knTypeList" scope="request">
        <logic:iterate id="list" name="enq920knForm" property="enq920knTypeList" scope="request">
          <bean:write name="list" property="etpName" /><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('enq920kncommit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq920knback');">
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