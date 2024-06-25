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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cir.5" /> <gsmsg:write key="cmn.manual.delete2" /><gsmsg:write key="cmn.check" /></title>
</head>

<body>
<html:form action="/circular/cir120kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />

<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="cir120JdelKbn" />
<html:hidden property="cir120JYear" />
<html:hidden property="cir120JMonth" />
<html:hidden property="cir120SdelKbn" />
<html:hidden property="cir120SYear" />
<html:hidden property="cir120SMonth" />
<html:hidden property="cir120DdelKbn" />
<html:hidden property="cir120DYear" />
<html:hidden property="cir120DMonth" />
<html:hidden property="cir120SelKbn" />
<html:hidden property="cir120AccountName" />
<html:hidden property="cir120AccountSid" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.manual.delete2" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_syudo_input');">
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
        <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
        <logic:iterate id="accountMdl" name="cir120knForm" property="cir120knAccountList">
          <div><bean:write name="accountMdl" property="accountName"/></div>
        </logic:iterate>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.25" />
      </th>
      <td>
        <logic:equal name="cir120knForm" property="cir120JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
        </logic:equal>

        <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>

        <logic:equal name="cir120knForm" property="cir120JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="cir120Year1" name="cir120knForm" property="cir120JYear" type="java.lang.String" />
          <bean:define id="cir120Month1" name="cir120knForm" property="cir120JMonth" type="java.lang.String" />
          <% String kikanStr1 = "<strong>" + gsMsg.getMessage(request, "cmn.year", cir120Year1) + " " + gsMsg.getMessage(request, "cmn.months", cir120Month1) + "</strong>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr1 %>" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.26" />
      </th>
      <td>
        <logic:equal name="cir120knForm" property="cir120SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
        </logic:equal>
        <logic:equal name="cir120knForm" property="cir120SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="cir120Year2" name="cir120knForm" property="cir120SYear" type="java.lang.String" />
          <bean:define id="cir120Month2" name="cir120knForm" property="cir120SMonth" type="java.lang.String" />
          <% String kikanStr2 = "<strong>" + gsMsg.getMessage(request, "cmn.year", cir120Year2) + " " + gsMsg.getMessage(request, "cmn.months", cir120Month2) + "</strong>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr2 %>" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.27" />
      </th>
      <td>
        <logic:equal name="cir120knForm" property="cir120DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
        </logic:equal>
        <logic:equal name="cir120knForm" property="cir120DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="cir120Year3" name="cir120knForm" property="cir120DYear" type="java.lang.String" />
          <bean:define id="cir120Month3" name="cir120knForm" property="cir120DMonth" type="java.lang.String" />
          <% String kikanStr3 = "<strong>" + gsMsg.getMessage(request, "cmn.year", cir120Year3) + " " + gsMsg.getMessage(request, "cmn.months", cir120Month3) + "</strong>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr3 %>" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('delete');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_syudo_input');">
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