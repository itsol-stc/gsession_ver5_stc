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
<title>GROUPSESSION <gsmsg:write key="cir.5" /> <gsmsg:write key="cmn.sml.notification.setting.kn" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/circular/cir200kn">
<input type="hidden" name="CMD" value="">

<html:hidden property="backScreen" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>
<html:hidden property="cir200InitFlg" />

<html:hidden property="cir200SmlSendKbn" />
<html:hidden property="cir200SmlSend" />
<html:hidden property="cir200SmlMemo" />
<html:hidden property="cir200SmlEdt" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.sml.notification.setting" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
       <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('cir200knok');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cir200knback');">
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
        <gsmsg:write key="shortmail.notification" />
      </th>
      <td class="w75">
        <logic:equal name="cir200knForm" property="cir200SmlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_ADMIN) %>"><gsmsg:write key="cmn.set.the.admin" /></logic:equal>
        <logic:equal name="cir200knForm" property="cir200SmlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_USER) %>"><gsmsg:write key="cmn.set.eachaccount" /></logic:equal>
      </td>
    </tr>
<logic:equal name="cir200knForm" property="cir200SmlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_ADMIN) %>">
<% String tuuchi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_KBN_YES); %>
<% String notuuchi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_KBN_NO); %>
    <tr>
      <th>
        <gsmsg:write key="cir.cir160.2" />
      </th>
      <td>
      <logic:equal name="cir200knForm" property="cir200SmlSend" value="0">
        <gsmsg:write key="cmn.notify" />
      </logic:equal>
      <logic:equal name="cir200knForm" property="cir200SmlSend" value="1">
        <gsmsg:write key="cmn.dont.notify" />
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cir.cir160.4" />
      </th>
      <td>
        <logic:equal name="cir200knForm" property="cir200SmlMemo" value="0">
          <gsmsg:write key="cmn.notify" />
        </logic:equal>
        <logic:equal name="cir200knForm" property="cir200SmlMemo" value="1">
          <gsmsg:write key="cmn.dont.notify" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cir.cir160.6" />
      </th>
      <td>
        <logic:equal name="cir200knForm" property="cir200SmlEdt" value="0">
          <gsmsg:write key="cmn.notify" />
        </logic:equal>
        <logic:equal name="cir200knForm" property="cir200SmlEdt" value="1">
          <gsmsg:write key="cmn.dont.notify" />
        </logic:equal>
      </td>
    </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('cir200knok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cir200knback');">
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