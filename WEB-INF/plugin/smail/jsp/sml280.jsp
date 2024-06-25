<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.sml.GSConstSmail" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="wml.wml020.07" /></title>
  <meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/smail/sml280">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlViewAccount" />
<html:hidden property="sml280initFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
    <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.101" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio name="sml280Form" property="sml280acntMakeKbn" value="<%= String.valueOf(GSConstSmail.KANRI_USER_ONLY) %>" styleId="acntMake2" /><label for="acntMake2" class="mr10"><gsmsg:write key="wml.70" /></label>
        <html:radio name="sml280Form" property="sml280acntMakeKbn" value="<%= String.valueOf(GSConstSmail.KANRI_USER_NO) %>" styleId="acntMake1" /><label for="acntMake1"><gsmsg:write key="wml.31" /></label>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.automatic.delete.categories" />
      </th>
      <td>
      <div class="verAlignMid">
        <html:radio name="sml280Form" property="sml280autoDelKbn" value="<%= String.valueOf(GSConstSmail.AUTO_DEL_ADM) %>" styleId="autoDel1" /><label for="autoDel1" class="mr10"><gsmsg:write key="cmn.set.the.admin" /></label>
        <html:radio name="sml280Form" property="sml280autoDelKbn" value="<%= String.valueOf(GSConstSmail.AUTO_DEL_ACCOUNT) %>" styleId="autoDel2" /><label for="autoDel2" class="mr10"><gsmsg:write key="cmn.set.eachaccount" /></label>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.employer" />
      </th>
      <td>
      <div class="verAlignMid">
        <html:radio name="sml280Form" property="sml280acntUser" value="<%= String.valueOf(GSConstSmail.KANRI_ACCOUNT_USER_ONLY) %>" styleId="acntUser1" /><label for="acntUser1" class="mr10"><gsmsg:write key="cmn.only.admin.setting" /></label>
        <html:radio name="sml280Form" property="sml280acntUser" value="<%= String.valueOf(GSConstSmail.KANRI_ACCOUNT_USER_NO) %>" styleId="acntUser2" /><label for="acntUser2"><gsmsg:write key="wml.31" /></label>
      </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
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