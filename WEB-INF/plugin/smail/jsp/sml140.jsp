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
<jsp:include page="/WEB-INF/plugin/smail/jsp/sml010_message.jsp" />
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../smail/js/sml140.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<jsp:include page="/WEB-INF/plugin/smail/jsp/smlaccountsel.jsp" />
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="sml.09" /></title>
</head>

<body onload="changeEnableDisable();">
<html:form action="/smail/sml140">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml140AccountSid" />
<html:hidden property="sml140AccountName" />

<logic:notEmpty name="sml140Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml140Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
    <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="cmn.manual.delete2" />
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
      <th class="w25">
        <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="sml140Form" property="sml140SelKbn" styleClass="accountSelKbn" styleId="sml140SelKbn_0"  value="0"/><label for="sml140SelKbn_0" class="mr10"><gsmsg:write key="wml.wml010.12" /></label>
          <html:radio name="sml140Form" property="sml140SelKbn" styleClass="accountSelKbn" styleId="sml140SelKbn_1"  value="1"/><label for="sml140SelKbn_1"><gsmsg:write key="cmn.all" /></label>
        </div>
        <div id="accountSelArea" class="">
          <logic:notEmpty name="sml140Form" property="sml140AccountName">
            <span id="selAccountNameArea"><bean:write name="sml140Form" property="sml140AccountName" /></span>
          </logic:notEmpty>
          <button type="button" id="accountSelBtn" class="baseBtn ml20" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
            <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="sml.57" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml140Form" property="sml140JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml140JdelKbn_0" onclick="setDispState(this.form.sml140JdelKbn, this.form.sml140JYear, this.form.sml140JMonth)" /><label for="sml140JdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="sml140Form" property="sml140JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml140JdelKbn_1" onclick="setDispState(this.form.sml140JdelKbn, this.form.sml140JYear, this.form.sml140JMonth)" /><label for="sml140JdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>
        </div>
        <div class="">
          <logic:notEmpty name="sml140Form" property="sml140YearLabelList">
          <html:select property="sml140JYear" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140YearLabelList" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml140Form" property="sml140MonthLabelList">
          <html:select property="sml140JMonth" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140MonthLabelList" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="sml.59" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml140Form" property="sml140SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml140SdelKbn_0" onclick="setDispState(this.form.sml140SdelKbn, this.form.sml140SYear, this.form.sml140SMonth)" /><label for="sml140SdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label></label>
          <html:radio name="sml140Form" property="sml140SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml140SdelKbn_1" onclick="setDispState(this.form.sml140SdelKbn, this.form.sml140SYear, this.form.sml140SMonth)" /><label for="sml140SdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>
        </div>
        <div class="">
          <logic:notEmpty name="sml140Form" property="sml140YearLabelList">
          <html:select property="sml140SYear" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140YearLabelList" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml140Form" property="sml140MonthLabelList">
          <html:select property="sml140SMonth" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140MonthLabelList" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="sml.58" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml140Form" property="sml140WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml140WdelKbn_0" onclick="setDispState(this.form.sml140WdelKbn, this.form.sml140WYear, this.form.sml140WMonth)" /><label for="sml140WdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="sml140Form" property="sml140WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml140WdelKbn_1" onclick="setDispState(this.form.sml140WdelKbn, this.form.sml140WYear, this.form.sml140WMonth)" /><label for="sml140WdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>
        </div>
        <div class="">
          <logic:notEmpty name="sml140Form" property="sml140YearLabelList">
          <html:select property="sml140WYear" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140YearLabelList" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml140Form" property="sml140MonthLabelList">
          <html:select property="sml140WMonth" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140MonthLabelList" value="value" label="label" />
          </html:select>
          <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.56" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml140Form" property="sml140DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml140DdelKbn_0" onclick="setDispState(this.form.sml140DdelKbn, this.form.sml140DYear, this.form.sml140DMonth)" /><label for="sml140DdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="sml140Form" property="sml140DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml140DdelKbn_1" onclick="setDispState(this.form.sml140DdelKbn, this.form.sml140DYear, this.form.sml140DMonth)" /><label for="sml140DdelKbn_1"><gsmsg:write key="cmn.delete.manual" /></label>
        </div>
        <div class="">
          <logic:notEmpty name="sml140Form" property="sml140YearLabelList">
          <html:select property="sml140DYear" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140YearLabelList" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml140Form" property="sml140MonthLabelList">
          <html:select property="sml140DMonth" styleClass="wp100">
          <html:optionsCollection name="sml140Form" property="sml140MonthLabelList" value="value" label="label" />
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

</html:form>
<div class="display_none">
  <div id="accountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" >
    <input type="hidden" id="selAccountElm" value="sml140AccountSid" />
    <input type="hidden" id="selAccountSubmit" value="true" />
    <input type="hidden" id="sml240user" value="<bean:write name="sml140Form" property="smlViewUser" />" />

    <div class="hp450 w100 ofy_a">
      <table class="w100 h100">
        <tr>
          <td id="accountListArea"  class="txt_t"></td>
        </tr>
      </table>
    </div>
  </div>
</div>
</body>
</html:html>