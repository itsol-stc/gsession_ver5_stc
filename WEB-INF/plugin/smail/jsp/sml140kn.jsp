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

<title>GROUPSESSION <gsmsg:write key="sml.10" /></title>
</head>

<body>
<html:form action="/smail/sml140kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />

<logic:notEmpty name="sml140knForm" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml140knForm" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml140JdelKbn" />
<html:hidden property="sml140JYear" />
<html:hidden property="sml140JMonth" />
<html:hidden property="sml140SdelKbn" />
<html:hidden property="sml140SYear" />
<html:hidden property="sml140SMonth" />
<html:hidden property="sml140WdelKbn" />
<html:hidden property="sml140WYear" />
<html:hidden property="sml140WMonth" />
<html:hidden property="sml140DdelKbn" />
<html:hidden property="sml140DYear" />
<html:hidden property="sml140DMonth" />
<html:hidden property="sml140SelKbn" />
<html:hidden property="sml140AccountName" />
<html:hidden property="sml140AccountSid" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="sml.10" />
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
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<div class="wrapper w80 mrl_auto">
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
        <logic:iterate id="accountMdl" name="sml140knForm" property="sml140knAccountList">
        <div><bean:write name="accountMdl" property="accountName"/></div>
        </logic:iterate>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.57" />
      </th>
      <td>
        <logic:equal name="sml140knForm" property="sml140JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
          </logic:equal>
          <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
          <logic:equal name="sml140knForm" property="sml140JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="sml140Year1" name="sml140knForm" property="sml140JYear" type="java.lang.String" />
          <bean:define id="sml140Month1" name="sml140knForm" property="sml140JMonth" type="java.lang.String" />
          <% String kikanStr1 = "<span class=\"fw_b\">" + gsMsg.getMessage(request, "cmn.year", sml140Year1) + " " + gsMsg.getMessage(request, "cmn.months", sml140Month1) + "</span>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr1 %>" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.59" />
      </th>
      <td>
        <logic:equal name="sml140knForm" property="sml140SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
          </logic:equal>
          <logic:equal name="sml140knForm" property="sml140SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="sml140Year2" name="sml140knForm" property="sml140SYear" type="java.lang.String" />
          <bean:define id="sml140Month2" name="sml140knForm" property="sml140SMonth" type="java.lang.String" />
          <% String kikanStr2 = "<span class=\"fw_b\">" + gsMsg.getMessage(request, "cmn.year", sml140Year2) + " " + gsMsg.getMessage(request, "cmn.months", sml140Month2) + "</span>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr2 %>" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.58" />
      </th>
      <td>
        <logic:equal name="sml140knForm" property="sml140WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
          </logic:equal>
          <logic:equal name="sml140knForm" property="sml140WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="sml140Year3" name="sml140knForm" property="sml140WYear" type="java.lang.String" />
          <bean:define id="sml140Month3" name="sml140knForm" property="sml140WMonth" type="java.lang.String" />
          <% String kikanStr3 = "<span class=\"fw_b\">" + gsMsg.getMessage(request, "cmn.year", sml140Year3) + " " + gsMsg.getMessage(request, "cmn.months", sml140Month3) + "</span>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr3 %>" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.56" />
      </th>
      <td>
        <logic:equal name="sml140knForm" property="sml140DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
          </logic:equal>
          <logic:equal name="sml140knForm" property="sml140DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="sml140Year4" name="sml140knForm" property="sml140DYear" type="java.lang.String" />
          <bean:define id="sml140Month4" name="sml140knForm" property="sml140DMonth" type="java.lang.String" />
          <% String kikanStr4 = "<span class=\"fw_b\">" + gsMsg.getMessage(request, "cmn.year", sml140Year4) + " " + gsMsg.getMessage(request, "cmn.months", sml140Month4) + "</span>"; %>
          <gsmsg:write key="cmn.del.data.older.than" arg0="<%= kikanStr4 %>" />
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