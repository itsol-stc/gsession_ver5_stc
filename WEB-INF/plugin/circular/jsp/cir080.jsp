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
<script src="../circular/js/cir080.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/default.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<title>GROUPSESSION <gsmsg:write key="cir.22" /></title>
</head>

<body onload="changeEnableDisable();">
<html:form action="/circular/cir080">
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

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<table align="center"  cellpadding="5" cellspacing="0" border="0" width="100%">
<tr>
<td width="100%" align="center">
  <table cellpadding="0" cellspacing="0" border="0" width="70%">
  <tr>
  <td>
    <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
    <td width="0%"><img src="../common/images/header_pset_01.gif" border="0" alt=""></td>
    <td width="0%" class="header_ktool_bg_text2" align="left" valign="middle" nowrap><span class="settei_ttl"><gsmsg:write key="cmn.preferences2" /></span></td>
    <td width="100%" class="header_ktool_bg_text">[ <gsmsg:write key="cir.22" /> ]</td>
    <td width="0%"><img src="../common/images/header_ktool_05.gif" border="0" alt=""></td>
    </tr>
    </table>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
    <td width="50%"></td>
    <td width="0%"><img src="../common/images/header_glay_1.gif" border="0" alt=""></td>
    <td class="header_glay_bg" width="50%">
      <input type="button" value="OK" class="btn_ok1" onClick="submitStyleChange();buttonPush('ok');">
      <input type="button" value="<gsmsg:write key="cmn.back.preferences" />" class="btn_back_n3" onClick="buttonPush('backToList');">
    </td>
    <td width="0%"><img src="../common/images/header_glay_3.gif" border="0" alt=""></td>
    </tr>
    </table>
    <logic:messagesPresent message="false">
      <table width="100%">
      <tr>
        <td align="left"><span class="TXT02"><html:errors/></span></td>
      </tr>
      </table>
    </logic:messagesPresent>
    <img src="../common/images/spacer.gif" width="1px" height="10px" border="0">

    <table width="100%" class="tl0" border="0" cellpadding="5">
    <tr>
    <td class="table_bg_A5B4E1" width="0%" nowrap><span class="text_bb1"><gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.25" /></span></td>
    <td align="left" class="td_wt" width="100%">

      <span class="text_base">

        <html:radio name="cir080Form" property="cir080JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir080JdelKbn_0" onclick="setDispState(this.form.cir080JdelKbn, this.form.cir080JYear, this.form.cir080JMonth)" /><label for="cir080JdelKbn_0"><gsmsg:write key="cmn.noset" /></label>&nbsp;
        <html:radio name="cir080Form" property="cir080JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir080JdelKbn_1" onclick="setDispState(this.form.cir080JdelKbn, this.form.cir080JYear, this.form.cir080JMonth)" /><label for="cir080JdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>&nbsp;

        <gsmsg:write key="cmn.after.data.head" />
        <logic:notEmpty name="cir080Form" property="cir080YearLabelList">
          <html:select property="cir080JYear" styleClass="select01" style="width: 100px;">
            <html:optionsCollection name="cir080Form" property="cir080YearLabelList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

        <logic:notEmpty name="cir080Form" property="cir080MonthLabelList">
          <html:select property="cir080JMonth" styleClass="select01" style="width: 100px;">
            <html:optionsCollection name="cir080Form" property="cir080MonthLabelList" value="value" label="label" />
          </html:select>
           <gsmsg:write key="cmn.after.data" />
        </logic:notEmpty>

      </span>

    </td>
    </tr>
    <tr>
    <td class="table_bg_A5B4E1" width="0%" nowrap><span class="text_bb1"><gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.26" /></span></td>
    <td align="left" class="td_wt" width="100%">

      <span class="text_base">

        <html:radio name="cir080Form" property="cir080SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir080SdelKbn_0" onclick="setDispState(this.form.cir080SdelKbn, this.form.cir080SYear, this.form.cir080SMonth)" /><label for="cir080SdelKbn_0"><gsmsg:write key="cmn.noset" /></label>&nbsp;
        <html:radio name="cir080Form" property="cir080SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir080SdelKbn_1" onclick="setDispState(this.form.cir080SdelKbn, this.form.cir080SYear, this.form.cir080SMonth)" /><label for="cir080SdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>&nbsp;

        <gsmsg:write key="cmn.after.data.head" />
        <logic:notEmpty name="cir080Form" property="cir080YearLabelList">
          <html:select property="cir080SYear" styleClass="select01" style="width: 100px;">
            <html:optionsCollection name="cir080Form" property="cir080YearLabelList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

        <logic:notEmpty name="cir080Form" property="cir080MonthLabelList">
          <html:select property="cir080SMonth" styleClass="select01" style="width: 100px;">
            <html:optionsCollection name="cir080Form" property="cir080MonthLabelList" value="value" label="label" />
          </html:select>
           <gsmsg:write key="cmn.after.data" />
        </logic:notEmpty>

      </span>

    </td>
    </tr>
    <tr>
    <td class="table_bg_A5B4E1" width="0%" nowrap><span class="text_bb1"><gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.27" /></span></td>
    <td align="left" class="td_wt" width="100%">

      <span class="text_base">

        <html:radio name="cir080Form" property="cir080DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir080DdelKbn_0" onclick="setDispState(this.form.cir080DdelKbn, this.form.cir080DYear, this.form.cir080DMonth)" /><label for="cir080DdelKbn_0"><gsmsg:write key="cmn.noset" /></label>&nbsp;
        <html:radio name="cir080Form" property="cir080DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir080DdelKbn_1" onclick="setDispState(this.form.cir080DdelKbn, this.form.cir080DYear, this.form.cir080DMonth)" /><label for="cir080DdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>&nbsp;

        <gsmsg:write key="cmn.after.data.head" />
        <logic:notEmpty name="cir080Form" property="cir080YearLabelList">
          <html:select property="cir080DYear" styleClass="select01" style="width: 100px;">
            <html:optionsCollection name="cir080Form" property="cir080YearLabelList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

        <logic:notEmpty name="cir080Form" property="cir080MonthLabelList">
          <html:select property="cir080DMonth" styleClass="select01" style="width: 100px;">
            <html:optionsCollection name="cir080Form" property="cir080MonthLabelList" value="value" label="label" />
          </html:select>
           <gsmsg:write key="cmn.after.data" />
        </logic:notEmpty>

      </span>

    </td>
    </tr>
    </table>

    <img src="../common/images/spacer.gif" width="1px" height="10px" border="0">

    <table cellpadding="0" cellpadding="5" border="0" width="100%">
    <tr>
    <td align="right">
      <input type="button" value="OK" class="btn_ok1" onClick="submitStyleChange();buttonPush('ok');">
      <input type="button" value="<gsmsg:write key="cmn.back.preferences" />" class="btn_back_n3" onClick="buttonPush('backToList');">
    </td>
    </tr>
    </table>

  </td>
  </tr>
  </table>
</td>
</tr>
</table>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>