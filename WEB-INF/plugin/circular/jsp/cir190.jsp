<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cir.GSConstCircular" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cir.5" /> <gsmsg:write key="wml.wml020.07" /></title>
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

<html:form action="/circular/cir190">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>
<html:hidden property="cir190initFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.preferences" />
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
        <gsmsg:write key="wml.101" /></span>
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio name="cir190Form" property="cir190acntMakeKbn" value="<%= String.valueOf(GSConstCircular.KANRI_USER_ONLY) %>" styleId="acntMake2" /><label for="acntMake2"><gsmsg:write key="wml.70" /></label>
          <html:radio name="cir190Form" property="cir190acntMakeKbn" value="<%= String.valueOf(GSConstCircular.KANRI_USER_NO) %>" styleId="acntMake1" styleClass="ml10"/><label for="acntMake1"><gsmsg:write key="wml.31" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.automatic.delete.categories" /></span>
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="cir190Form" property="cir190autoDelKbn" value="<%= String.valueOf(GSConstCircular.AUTO_DEL_ADM) %>" styleId="autoDel1" /><label for="autoDel1"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio styleClass="ml10" name="cir190Form" property="cir190autoDelKbn" value="<%= String.valueOf(GSConstCircular.AUTO_DEL_ACCOUNT) %>" styleId="autoDel2" /><label for="autoDel2"><gsmsg:write key="cmn.set.eachaccount" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.employer" /></span>
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="cir190Form" property="cir190acntUser" value="<%= String.valueOf(GSConstCircular.CIN_ACNT_USER_ONLY) %>" styleId="acntUser1" /><label for="acntUser1"><gsmsg:write key="cmn.only.admin.setting" /></label>
          <html:radio styleClass="ml10" name="cir190Form" property="cir190acntUser" value="<%= String.valueOf(GSConstCircular.CIN_ACNT_USER_NO) %>" styleId="acntUser2" /><label for="acntUser2"><gsmsg:write key="wml.31" /></label>
        </span>
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