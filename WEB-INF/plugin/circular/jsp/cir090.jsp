<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../circular/js/cir090.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../circular/js/ciraccountsel.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cir.21" /></title>
</head>

<body onload="changeEnableDisable();">
<html:form action="/circular/cir090">
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
<html:hidden property="cir090AccountSid" />
<html:hidden property="cir090AccountName" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.manual.delete2" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="submitStyleChange();buttonPush('syudo_del_kakunin');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKjnTool');">
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
    <th class="w25"><gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" /></th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="cir090Form" property="cir090SelKbn" styleClass="js_accountSelKbn" styleId="cir090SelKbn_0" value="0"/><label for="cir090SelKbn_0"><gsmsg:write key="wml.wml010.12" /></label>
          <html:radio name="cir090Form" property="cir090SelKbn" styleClass="js_accountSelKbn ml10" styleId="cir090SelKbn_1" value="1"/><label for="cir090SelKbn_1"><gsmsg:write key="cmn.all" /></label>
        </div>
        <div id="accountSelArea" class="account_name_area"><span id="selAccountNameArea"><bean:write name="cir090Form" property="cir090AccountName" /></span>
          <button id="accountSelBtn" name="btn_add" class="baseBtn" type="button" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
          <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
          </button>
        </div>
    </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.25" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir090Form" property="cir090JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir090JdelKbn_0" onclick="setDispState(this.form.cir090JdelKbn, this.form.cir090JYear, this.form.cir090JMonth)" /><label for="cir090JdelKbn_0"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="cir090Form" styleClass="ml10" property="cir090JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir090JdelKbn_1" onclick="setDispState(this.form.cir090JdelKbn, this.form.cir090JYear, this.form.cir090JMonth)" /><label for="cir090JdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>

          <gsmsg:write key="cmn.after.data.head" />

          <logic:notEmpty name="cir090Form" property="cir090YearLabelList">
            <html:select property="cir090JYear" styleClass="wp100 mr5 ml10">
            <html:optionsCollection name="cir090Form" property="cir090YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="cir090Form" property="cir090MonthLabelList">
            <html:select property="cir090JMonth" styleClass="wp100 mr5">
            <html:optionsCollection name="cir090Form" property="cir090MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.26" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir090Form" property="cir090SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir090SdelKbn_0" onclick="setDispState(this.form.cir090SdelKbn, this.form.cir090SYear, this.form.cir090SMonth)" /><label for="cir090SdelKbn_0"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="cir090Form" styleClass="ml10" property="cir090SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir090SdelKbn_1" onclick="setDispState(this.form.cir090SdelKbn, this.form.cir090SYear, this.form.cir090SMonth)" /><label for="cir090SdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>

          <gsmsg:write key="cmn.after.data.head" />
          <logic:notEmpty name="cir090Form" property="cir090YearLabelList">
            <html:select property="cir090SYear" styleClass="wp100 mr5 ml10">
            <html:optionsCollection name="cir090Form" property="cir090YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="cir090Form" property="cir090MonthLabelList">
            <html:select property="cir090SMonth" styleClass="wp100 mr5">
            <html:optionsCollection name="cir090Form" property="cir090MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.27" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir090Form" property="cir090DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir090DdelKbn_0" onclick="setDispState(this.form.cir090DdelKbn, this.form.cir090DYear, this.form.cir090DMonth)" /><label for="cir090DdelKbn_0"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="cir090Form" styleClass="ml10" property="cir090DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir090DdelKbn_1" onclick="setDispState(this.form.cir090DdelKbn, this.form.cir090DYear, this.form.cir090DMonth)" /><label for="cir090DdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>

          <gsmsg:write key="cmn.after.data.head" />

          <logic:notEmpty name="cir090Form" property="cir090YearLabelList">
            <html:select property="cir090DYear" styleClass="wp100 mr5 ml10">
            <html:optionsCollection name="cir090Form" property="cir090YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="cir090Form" property="cir090MonthLabelList">
            <html:select property="cir090DMonth" styleClass="wp100 mr5">
            <html:optionsCollection name="cir090Form" property="cir090MonthLabelList" value="value" label="label" />
            </html:select>
           <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="submitStyleChange();buttonPush('syudo_del_kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKjnTool');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

<div id="accountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" class="display_n">
  <input type="hidden" id="selAccountElm" value="cir090AccountSid" />
  <input type="hidden" id="selAccountSubmit" value="true" />
  <input type="hidden" id="cir150user" value="<bean:write name="cir090Form" property="cirViewUser" />" />
  <div class="ofy_a hp450">
  <table class="w100 h100">
    <tr>
      <td id="accountListArea" class="txt_t"></td>
    </tr>
  </table>
  </div>
</div>

</html:form>
</body>
</html:html>