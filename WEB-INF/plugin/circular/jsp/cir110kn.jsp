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
<html:form action="/circular/cir110kn">
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

<html:hidden property="cir110DelKbn" />
<html:hidden property="cir110JdelKbn" />
<html:hidden property="cir110JYear" />
<html:hidden property="cir110JMonth" />
<html:hidden property="cir110SdelKbn" />
<html:hidden property="cir110SYear" />
<html:hidden property="cir110SMonth" />
<html:hidden property="cir110DdelKbn" />
<html:hidden property="cir110DYear" />
<html:hidden property="cir110DMonth" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
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
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.25" />
      </th>
      <td class="w75">
        <logic:equal name="cir110knForm" property="cir110JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="cir110knForm" property="cir110JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="jyear" name="cir110knForm" property="cir110JYear" type="java.lang.String" />
          <bean:define id="jmonth" name="cir110knForm" property="cir110JMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= jyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= jmonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.26" />
      </th>
      <td>
        <logic:equal name="cir110knForm" property="cir110SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="cir110knForm" property="cir110SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="syear" name="cir110knForm" property="cir110SYear" type="java.lang.String" />
          <bean:define id="smonth" name="cir110knForm" property="cir110SMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= syear %>" /> <gsmsg:write key="cmn.months" arg0="<%= smonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.27" />
      </th>
      <td>
        <logic:equal name="cir110knForm" property="cir110DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="cir110knForm" property="cir110DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="dyear" name="cir110knForm" property="cir110DYear" type="java.lang.String" />
          <bean:define id="dmonth" name="cir110knForm" property="cir110DMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= dyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" /> <gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
     <%--
    <tr>
      <th>
        <gsmsg:write key="cmn.automatic.delete.categories" />
      </th>
      <td>
        <logic:equal name="cir110knForm" property="cir110DelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_DEL_KBN_ADM_SETTING) %>"><gsmsg:write key="cmn.set.the.admin" /></logic:equal>
        <logic:equal name="cir110knForm" property="cir110DelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_DEL_KBN_USER_SETTING) %>"><gsmsg:write key="cmn.set.eachuser" /></logic:equal>
      </td>
     </tr>
     --%>
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