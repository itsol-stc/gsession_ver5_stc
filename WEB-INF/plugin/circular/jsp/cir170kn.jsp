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
<title>GROUPSESSION <gsmsg:write key="cir.5" />　<gsmsg:write key="wml.wml160kn.03" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/circular/cir170kn">


<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cir150keyword" />
<html:hidden property="cir150group" />
<html:hidden property="cir150user" />
<html:hidden property="cir150pageTop" />
<html:hidden property="cir150pageBottom" />
<html:hidden property="cir150svKeyword" />
<html:hidden property="cir150svGroup" />
<html:hidden property="cir150svUser" />
<html:hidden property="cir150sortKey" />
<html:hidden property="cir150order" />
<html:hidden property="cir150searchFlg" />

<html:hidden property="cir170InitFlg" />

<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.wml160kn.03" />
    </li>
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

<logic:messagesPresent message="false">
  <html:errors />
</logic:messagesPresent>

<div class="txt_l">
<gsmsg:write key="main.man028kn.3" />
</div>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.capture.file.name" />
      </th>
      <td class="w75">
        <bean:write name="cir170knForm" property="cir170knFileName" />
      </td>
    </tr>
  </table>

  <table class="table-top">
  <tr>
    <th class="w50">
      <gsmsg:write key="wml.96" />
    </th>
    <th class="w50">
      <gsmsg:write key="cmn.employer" />
    </th>
  </tr>
  <logic:iterate id="useUserData" name="cir170knForm" property="cir170knUseUserList">
  <tr>
    <td>
      <bean:write name="useUserData" property="accountName" />
    </td>
    <td>
    <logic:notEmpty name="useUserData" property="groupNameList">
        <logic:iterate id="useGroupName" name="useUserData" property="groupNameList">
          <div><bean:write name="useGroupName" property="grpName" /></div>
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="useUserData" property="userNameList">
        <logic:iterate id="useUserName" name="useUserData" property="userNameList">
          <div><bean:write name="useUserName" property="usiSei" />  <bean:write name="useUserName" property="usiMei" /></div>
        </logic:iterate>
      </logic:notEmpty>
    </td>
  </tr>
  </logic:iterate>
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