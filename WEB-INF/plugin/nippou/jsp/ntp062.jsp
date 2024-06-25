<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<%
   String maxLengthSyosai = String.valueOf(1000);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp061.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="showLengthId($('#inputstr')[0], <%= maxLengthSyosai %>, 'inputlength');" onunload="calWindowClose();companyWindowClose();">

<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE_TANITU); %>
<logic:equal name="ntp062Form" property="ntp061PopKbn" value="0">
  <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
</logic:equal>
<html:form action="/nippou/ntp062">
<div id="ntp061CompanyIdArea">
  <html:hidden property="ntp061CompanySid" />
</div>
<div id="ntp061CompanyBaseIdArea">
  <html:hidden property="ntp061CompanyBaseSid" />
</div>
<input type="hidden" name="CMD" value="">
<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp" />
<logic:notEmpty name="ntp062Form" property="ntp060Mikomi" scope="request">
  <logic:iterate id="mikomi" name="ntp062Form" property="ntp060Mikomi" scope="request">
    <input type="hidden" name="ntp060Mikomi" value="<bean:write name="mikomi"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ntp062Form" property="ntp060Syodan" scope="request">
  <logic:iterate id="syodan" name="ntp062Form" property="ntp060Syodan" scope="request">
    <input type="hidden" name="ntp060Syodan" value="<bean:write name="syodan"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="ntp060ProcMode" />
<html:hidden property="ntp060NanSid" />
<html:hidden property="ntp061ReturnPage" />
<html:hidden property="ntp062InitFlg" />
<html:hidden property="ntp061TourokuName" />
<html:hidden property="ntp061TourokuUsrUkoFlg" />
<html:hidden property="ntp061PopKbn" />
<html:hidden property="ntp061AddCompFlg" />
<html:hidden property="ntp061Date" />
<html:hidden property="ntp061AnkenSid" />
<html:hidden property="ntp061SvCompanySid" />
<html:hidden property="ntp061SvCompanyCode" />
<html:hidden property="ntp061SvCompanyName" />
<html:hidden property="ntp061SvCompanyBaseSid" />
<html:hidden property="ntp061SvCompanyBaseName" />

<html:hidden property="ntp200NanSid" />
<html:hidden property="ntp200ProcMode" />
<html:hidden property="ntp200InitFlg" />
<html:hidden property="ntp200parentPageId" />
<html:hidden property="ntp200RowNumber" />

<logic:notEmpty name="ntp062Form" property="ntp061ChkShohinSidList" scope="request">
  <logic:iterate id="item" name="ntp062Form" property="ntp061ChkShohinSidList" scope="request">
    <input type="hidden" name="ntp061ChkShohinSidList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>


<!--　BODY -->


<%-------------------------------- 案件登録画面 --------------------------------%>
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
     <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.import" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('062_import');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <gsmsg:write key="cmn.import" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backNtp062');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left w100">
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="main.src.34" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <span class="fs_13">
          *<a href="../nippou/ntp062.do?CMD=ntp062_sample"><gsmsg:write key="reserve.111" /></a>
        </span>
        <attachmentFile:filearea
        mode="<%= tempMode %>"
        pluginId="<%= jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_ID_NIPPOU %>"
        tempDirId="ntp062" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.staff" />
      </th>
      <td class="w80">
        <ui:usrgrpselector name="ntp062Form" property="ntp062TantoListUI" styleClass="hp215" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.58" />
      </th>
      <td class="w80">
        <div>
          <button type="button" class="baseBtn js_itmAddBtn" value="<gsmsg:write key="cmn.add" />">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delShohin');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </div>
        <div>
          <html:select name="ntp062Form" property="ntp061SelectShohin" size="5" multiple="true" styleClass="wp300 hp100">
            <logic:notEmpty name="ntp062Form" property="ntp061ShohinList">
              <html:optionsCollection name="ntp062Form" property="ntp061ShohinList" value="value" label="label" />
            </logic:notEmpty>
            <option value="-1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
          </html:select>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20" rowspan="2">
        <gsmsg:write key="cmn.setting.permissions" />
      </th>
      <td class="w80">
        <div class="w100">
          <div class="verAlignMid">
            <logic:equal name="ntp062Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
              <span class="js_permissionViewLabel"><gsmsg:write key="address.61" />:</span>
            </logic:equal>
            <logic:notEqual name="ntp062Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
              <span class="js_permissionViewLabel display_n"><gsmsg:write key="address.61" />:</span>
            </logic:notEqual>
            <%--制限なし --%>
            <html:radio name="ntp062Form" property="ntp061NanPermitView" styleId="ntp061NanPermitView0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>" />
            <label for="ntp061NanPermitView0"><gsmsg:write key="cmn.nolimit" /></label>
            <%--担当者のみ --%>
            <html:radio name="ntp062Form" property="ntp061NanPermitView" styleId="ntp061NanPermitView1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>" />
            <label for="ntp061NanPermitView1"><gsmsg:write key="address.62" /></label>
            <%--ユーザグループ指定 --%>
            <html:radio name="ntp062Form" property="ntp061NanPermitView" styleId="ntp061NanPermitView2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>" />
            <label for="ntp061NanPermitView2"><gsmsg:write key="ntp.ntp061.1" /></label>
          </div>
        </div>
        <div id="nanPermitSelect">
          <ui:usrgrpselector name="ntp062Form" property="ntp062NanPermitUI" styleClass="hp300" />
       </div>
      </td>
    </tr>
    <logic:equal name="ntp062Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
      <tr class="js_permissionEditRadio" >
    </logic:equal>
    <logic:notEqual name="ntp062Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
      <tr class="js_permissionEditRadio display_n">
    </logic:notEqual>
      <td class="w80">
        <div class="w100">
          <div class="verAlignMid">
            <gsmsg:write key="cmn.edit.permissions" />:
            <%--制限なし --%>
            <html:radio name="ntp062Form" property="ntp061NanPermitEdit" styleId="ntp061NanPermitEdit0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>" />
            <label for="ntp061NanPermitEdit0"><gsmsg:write key="cmn.nolimit" /></label>
            <%--担当者のみ --%>
            <html:radio name="ntp062Form" property="ntp061NanPermitEdit" styleId="ntp061NanPermitEdit1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>" />
            <label for="ntp061NanPermitEdit1"><gsmsg:write key="address.62" /></label>
            <%--ユーザグループ指定 --%>
            <html:radio name="ntp062Form" property="ntp061NanPermitEdit" styleId="ntp061NanPermitEdit2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>" />
            <label for="ntp061NanPermitEdit2"><gsmsg:write key="ntp.ntp061.1" /></label>
          </div>
          <div id="nanPermitEditSelect">
            <ui:usrgrpselector name="ntp062Form" property="ntp062NanPermitEditUI" styleClass="hp215" />
          </div>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('062_import');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backNtp062');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<logic:equal name="ntp062Form" property="ntp061PopKbn" value="0">
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</logic:equal>

<%-------------------------------- 商品選択 --------------------------------%>
<logic:equal name="ntp062Form" property="ntp061ItmKbn" value="0">
  <div class="js_itmArea ntp_itmArea display_n">
</logic:equal>
<logic:notEqual name="ntp062Form" property="ntp061ItmKbn" value="0">
  <div class="js_itmArea ntp_itmArea">
</logic:notEqual>
<logic:equal name="ntp062Form" property="ntp061PopKbn" value="0">
  <div class="ntp_itmSelArea txt_c bgC_body">
</logic:equal>
<logic:notEqual name="ntp062Form" property="ntp061PopKbn" value="0">
  <div class="ntp_itmSelArea txt_c bgC_body">
</logic:notEqual>
<logic:notEmpty name="ntp062Form" property="ntp061ItmSvChkShohinSidList" >
  <logic:iterate id="item" name="thisForm" property="ntp061ItmSvChkShohinSidList" >
    <input type="hidden" name="ntp061ItmSvChkShohinSidList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="ntp062Form" property="ntp061ItmSelectedSid" >
  <logic:iterate id="item" name="thisForm" property="ntp061ItmSelectedSid" >
    <input type="hidden" name="ntp061ItmChkShohinSidList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="ntp061ItmNhnSid" />
<html:hidden property="ntp061ItmProcMode" />
<html:hidden property="ntp061ItmReturnPage" />
<html:hidden property="ntp061ItmDspMode" />
<html:hidden property="ntp061ItmInitFlg" />
<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
     <gsmsg:write key="ntp.58" /><gsmsg:write key="cmn.list" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <table class="table-left w100">
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w85" colspan="3">
        <html:select name="ntp062Form" property="ntp061CatSid" styleClass="wp200">
          <logic:notEmpty name="ntp062Form" property="ntp061CategoryList">
            <html:optionsCollection name="ntp062Form" property="ntp061CategoryList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.122" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="ntp062Form" property="ntp061ItmNhnCode" maxlength="13" styleClass="wp150" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.154" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="ntp062Form" property="ntp061ItmNhnName" maxlength="100" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.76" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:text name="ntp062Form" property="ntp061ItmNhnPriceSale" maxlength="12" styleClass="wp100" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <html:radio name="ntp062Form" property="ntp061ItmNhnPriceSaleKbn" styleId="ntp061ItmNhnPriceSaleKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_MORE) %>" />
          <label for="ntp061ItmNhnPriceSaleKbn1"><gsmsg:write key="ntp.66" /></label>
          <html:radio name="ntp062Form" property="ntp061ItmNhnPriceSaleKbn" styleId="ntp061ItmNhnPriceSaleKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_LESS) %>" />
          <label for="ntp061ItmNhnPriceSaleKbn2"><gsmsg:write key="ntp.67" /></label>
        </div>
      </td>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.77" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:text name="ntp062Form" property="ntp061ItmNhnPriceCost" maxlength="12" styleClass="wp100" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <html:radio name="ntp062Form" property="ntp061ItmNhnPriceCostKbn" styleId="ntp061ItmNhnPriceCostKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_MORE) %>" />
          <label for="ntp061ItmNhnPriceCostKbn1"><gsmsg:write key="ntp.66" /></label>
          <html:radio name="ntp062Form" property="ntp061ItmNhnPriceCostKbn" styleId="ntp061ItmNhnPriceCostKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_LESS) %>" />
          <label for="ntp061ItmNhnPriceCostKbn2"><gsmsg:write key="ntp.67" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.sortkey" />1
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:select name="ntp062Form" property="ntp061ItmSortKey1" styleClass="wp100">
            <logic:notEmpty name="ntp062Form" property="ntp061ItmSortList">
              <html:optionsCollection name="ntp062Form" property="ntp061ItmSortList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <html:radio name="ntp062Form" property="ntp061ItmOrderKey1" styleId="ntp061ItmOrderKey11" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
          <label for="ntp061ItmOrderKey11"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="ntp062Form" property="ntp061ItmOrderKey1" styleId="ntp061ItmOrderKey12" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
          <label for="ntp061ItmOrderKey12"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.sortkey" />2
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:select name="ntp062Form" property="ntp061ItmSortKey2" styleClass="wp100">
            <logic:notEmpty name="ntp062Form" property="ntp061ItmSortList">
              <html:optionsCollection name="ntp062Form" property="ntp061ItmSortList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <html:radio name="ntp062Form" property="ntp061ItmOrderKey2" styleId="ntp061ItmOrderKey21" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
          <label for="ntp061ItmOrderKey21"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="ntp062Form" property="ntp061ItmOrderKey2" styleId="ntp061ItmOrderKey22" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
          <label for="ntp061ItmOrderKey22"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="mt5 txt_c">
    <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.search" />" onClick="return itemSearchPush('itmsearch');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <logic:notEmpty name="ntp062Form" property="ntp061ItmShohinList">
    <bean:size id="count1" name="ntp062Form" property="ntp061ItmPageCmbList"  />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush('itmprevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="cmn.previous.page" />
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp061ItmPageTop" onchange="itmChangePage(0);" styleClass="paging_combo">
          <html:optionsCollection name="ntp062Form" property="ntp061ItmPageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="buttonPush('itmnextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="project.48" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
  <table class="table-top w100">
    <tr>
      <logic:notEqual name="ntp062Form" property="ntp061ItmDspMode" value="search">
        <th></th>
      </logic:notEqual>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.122" />
      </th>
      <th class="w25 txt_c">
        <gsmsg:write key="ntp.154" />
      </th>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.76" />
      </th>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.77" />
      </th>
      <th class="w15 txt_c">
        <gsmsg:write key="bmk.15" />
      </th>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.155" />
      </th>
    </tr>
    <logic:notEmpty name="ntp062Form" property="ntp061ItmShohinList">
      <logic:iterate id="syohinMdl" name="ntp062Form" property="ntp061ItmShohinList" indexId="idx">
        <bean:define id="sid" name="syohinMdl" property="nhnSid" type="java.lang.Integer" />
        <tr class="js_listHover cursor_p" id="tr_<%= Integer.toString(sid.intValue()) %>" onclick="clickShohinName('1', <%= Integer.toString(sid.intValue()) %>);">
          <td class="txt_c">
            <div class="verAlignMid">
              <html:multibox property="ntp061ItmChkShohinSidList" value="<%= Integer.toString(sid.intValue()) %>" styleId="1" onclick="clickMulti();"/>
            </div>
          </td>
          <td class="txt_l">
            <div>
              <span class="baseLabel ml0">
                <bean:write name="syohinMdl" property="nscName" />
              </span>
            </div>
            <bean:write name="syohinMdl" property="nhnCode" />
          </td>
          <td class="txt_l">
            <bean:write name="syohinMdl" property="nhnName" />
          </td>
          <td class="txt_r">
            \<bean:write name="syohinMdl" property="ntp130PriceSale" />
          </td>
          <td class="txt_r">
            \<bean:write name="syohinMdl" property="ntp130PriceCost" />
          </td>
          <td class="txt_c no_w">
            <bean:write name="syohinMdl" property="ntp130ADate" />
          </td>
          <td class="txt_c no_w">
            <bean:write name="syohinMdl" property="ntp130EDate" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <logic:notEmpty name="ntp062Form" property="ntp061ItmShohinList">
    <bean:size id="count1" name="ntp062Form" property="ntp061ItmPageCmbList"  />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush('itmprevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="cmn.previous.page" />
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp061ItmPageBottom" onchange="itmChangePage(1);" styleClass="paging_combo">
          <html:optionsCollection name="ntp062Form" property="ntp061ItmPageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="buttonPush('itmnextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="project.48" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
  <div class="txt_c mb10">
    <button type="button" class="baseBtn" onclick="return itemSelectPush('itmok');">
      <gsmsg:write key="cmn.select" />
    </button>
    <button type="button" class="baseBtn js_itmClose">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>

<%-------------------------------- 商品ダイアログ内遷移時 --------------------------------%>
<logic:notEqual name="ntp062Form" property="ntp061ItmKbn" value="0">
<script type="text/javascript">
Glayer.show();
$(".itmselectbox").css('visibility','visible');
</script>
</logic:notEqual>

<%-------------------------------- 商品検索時エラーダイアログ --------------------------------%>
<div id="dialog_error" title="" style="display:none">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../common/images/original/icon_info_32.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14 js_shohinErrorStr cl_fontWarn">
      </td>
    </tr>
  </table>
</div>

</html:form>
</body>
</html:html>