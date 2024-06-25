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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="sml.08" /></title>
</head>

<body>
<html:form action="/smail/sml150kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />

<logic:notEmpty name="sml150knForm" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml150knForm" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="backScreen" />
<html:hidden property="sml150DelKbn" />
<html:hidden property="sml150JdelKbn" />
<html:hidden property="sml150JYear" />
<html:hidden property="sml150JMonth" />
<html:hidden property="sml150SdelKbn" />
<html:hidden property="sml150SYear" />
<html:hidden property="sml150SMonth" />
<html:hidden property="sml150WdelKbn" />
<html:hidden property="sml150WYear" />
<html:hidden property="sml150WMonth" />
<html:hidden property="sml150DdelKbn" />
<html:hidden property="sml150DYear" />
<html:hidden property="sml150DMonth" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><%--
      --%><gsmsg:write key="sml.08" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('update');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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
        <gsmsg:write key="sml.50" />
      </th>
      <td class="w75">
        <logic:equal name="sml150knForm" property="sml150JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml150knForm" property="sml150JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="jyear" name="sml150knForm" property="sml150JYear" type="java.lang.String" />
          <bean:define id="jmonth" name="sml150knForm" property="sml150JMonth" type="java.lang.String" />
            <gsmsg:write key="cmn.year" arg0="<%= jyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= jmonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.52" />
      </th>
      <td>
        <logic:equal name="sml150knForm" property="sml150SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml150knForm" property="sml150SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="syear" name="sml150knForm" property="sml150SYear" type="java.lang.String" />
          <bean:define id="smonth" name="sml150knForm" property="sml150SMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= syear %>" /> <gsmsg:write key="cmn.months" arg0="<%= smonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.51" />
      </th>
      <td>
        <logic:equal name="sml150knForm" property="sml150WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml150knForm" property="sml150WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="wyear" name="sml150knForm" property="sml150WYear" type="java.lang.String" />
          <bean:define id="wmonth" name="sml150knForm" property="sml150WMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= wyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= wmonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.49" />
      </th>
      <td>
        <logic:equal name="sml150knForm" property="sml150DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="sml150knForm" property="sml150DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>">
          <bean:define id="dyear" name="sml150knForm" property="sml150DYear" type="java.lang.String" />
          <bean:define id="dmonth" name="sml150knForm" property="sml150DMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= dyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('update');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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