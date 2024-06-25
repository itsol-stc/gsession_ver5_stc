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
<title>GROUPSESSION <gsmsg:write key="cir.5" /> <gsmsg:write key="cmn.autodelete.setting" /><gsmsg:write key="cmn.check" /></title>
</head>

<body>
<html:form action="/circular/cir080kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="backScreen" />
<html:hidden property="cir080JdelKbn" />
<html:hidden property="cir080JYear" />
<html:hidden property="cir080JMonth" />
<html:hidden property="cir080SdelKbn" />
<html:hidden property="cir080SYear" />
<html:hidden property="cir080SMonth" />
<html:hidden property="cir080DdelKbn" />
<html:hidden property="cir080DYear" />
<html:hidden property="cir080DMonth" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.autodelete.setting" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('update');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="xxx">
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
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.25" />
      </th>
      <td class="w75">
      <logic:equal name="cir080knForm" property="cir080JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
        <gsmsg:write key="cmn.noset" />
      </logic:equal>
      <logic:equal name="cir080knForm" property="cir080JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
        <bean:define id="jmonth" name="cir080knForm" property="cir080JMonth" type="java.lang.String" />
        <bean:define id="yr1" name="cir080knForm" property="cir080JYear" type="java.lang.String" />
        <gsmsg:write key="cmn.year" arg0="<%= yr1 %>" /> <gsmsg:write key="cmn.months" arg0="<%= jmonth %>"/> <gsmsg:write key="cmn.auto.del.data.older.than" />
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.26" />
      </th>
      <td>
      <logic:equal name="cir080knForm" property="cir080SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
        <gsmsg:write key="cmn.noset" />
      </logic:equal>
      <logic:equal name="cir080knForm" property="cir080SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
        <bean:define id="smonth" name="cir080knForm" property="cir080SMonth" type="java.lang.String" />
        <bean:define id="yr2" name="cir080knForm" property="cir080SYear" type="java.lang.String" />
        <gsmsg:write key="cmn.year" arg0="<%= yr2 %>" /> <gsmsg:write key="cmn.months" arg0="<%= smonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.27" />
      </th>
      <td>
      <logic:equal name="cir080knForm" property="cir080DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
        <gsmsg:write key="cmn.noset" />
      </logic:equal>
      <logic:equal name="cir080knForm" property="cir080DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
        <bean:define id="dmonth" name="cir080knForm" property="cir080DMonth" type="java.lang.String" />
        <bean:define id="yr3" name="cir080knForm" property="cir080DYear" type="java.lang.String" />
        <gsmsg:write key="cmn.year" arg0="<%= yr3 %>" /> <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
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