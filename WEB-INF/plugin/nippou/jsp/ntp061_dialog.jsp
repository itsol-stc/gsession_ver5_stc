<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
    String thisFormName = request.getParameter("thisFormName");
    pageContext.setAttribute("thisForm", request.getAttribute(request.getParameter("thisFormName")));
%>

<%-------------------------------- アドレス帳 --------------------------------%>
<logic:equal name="thisForm" property="ntp061AdrKbn" value="0">
  <div class="js_adrArea ntp_itmArea display_n">
</logic:equal>
<logic:notEqual name="thisForm" property="ntp061AdrKbn" value="0">
  <div class="js_adrArea ntp_itmArea">
</logic:notEqual>

<link rel=stylesheet href="../address/css/address.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<logic:equal name="thisForm" property="ntp061PopKbn" value="0">
  <div class="ntp_itmSelArea txt_c bgC_body">
</logic:equal>
<logic:notEqual name="thisForm" property="ntp061PopKbn" value="0">
  <div class="ntp_itmSelArea txt_c bgC_body">
</logic:notEqual>

<html:hidden property="ntp061AdrIndex" />
<html:hidden property="ntp061AdrStr" />
<html:hidden property="ntp061Adrpage" />
<html:hidden property="ntp061AdrparentPageId" />
<html:hidden property="ntp061AdrCompanySid" />
<html:hidden property="ntp061AdrCompanyBaseSid" />
<html:hidden property="ntp061AdrCompanyCode" />
<html:hidden property="ntp061AdrCompanyName" />
<html:hidden property="ntp061Adrmode" />
<html:hidden property="ntp061AdrProAddFlg" />
<html:hidden property="ntp061AdrProAddErrFlg" />
<html:hidden property="ntp061AdrRowNumber" />
<html:hidden property="ntp061AdrPrsMode" />
<html:hidden property="ntp061svAdrCode" />
<html:hidden property="ntp061svAdrCoName" />
<html:hidden property="ntp061svAdrCoNameKn" />
<html:hidden property="ntp061svAdrCoBaseName" />
<html:hidden property="ntp061svAdrAtiSid" />
<html:hidden property="ntp061svAdrTdfk" />
<html:hidden property="ntp061svAdrBiko" />
<html:hidden property="ntp061SearchMode" />
<input type="hidden" name="ntp061Adrtanto" value="0">

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.select.company" />
    </li>
    <li></li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
<bean:define id="procMode" name="thisForm" property="ntp061SearchMode" />
<% int sMode = ((Integer) procMode).intValue(); %>
<% if (sMode == 0) { %>
    <ul class="tabHeader w100">
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1" onclick="javascript:change061Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_50) %>');">
        <gsmsg:write key="address.adr100.1" />
      </li>
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2" onclick="javascript:change061Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) %>');">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space border_bottom_none"></li>
    </ul>
    <div id="tab1_block" class="bgC_lightGray bor1 p10">
      <table class="w100 bgC_body">
        <tr>
          <logic:equal name="thisForm" property="ntp061AdrIndex" value="-1">
            <td class="bor1 txt_c bgC_gray w10">
              <span class="cl_fontOutline"><gsmsg:write key="cmn.all" /></span>
            </td>
          </logic:equal>
          <logic:notEqual name="thisForm" property="ntp061AdrIndex" value="-1">
            <td class="bor1 txt_c w10 td-hoverChange cursor_p cl_linkDef" onClick="return selectLine('-1');">
              <gsmsg:write key="cmn.all" />
            </td>
          </logic:notEqual>
          <logic:notEmpty name="thisForm" property="ntp061AdrIndexList">
            <logic:iterate id="lineLabel" name="thisForm" property="ntp061AdrIndexList">
              <logic:equal name="lineLabel" property="value" value="0">
                <td class="bor1 txt_c w7 td-hoverChange cursor_p cl_linkDef" onClick="return selectLine('<bean:write name="lineLabel" property="label" />');">
                  <bean:write name="lineLabel" property="label" />
                </td>
              </logic:equal>
              <logic:equal name="lineLabel" property="value" value="1">
                <td class="bor1 txt_c w7">
                  <bean:write name="lineLabel" property="label" />
                </td>
              </logic:equal>
              <logic:equal name="lineLabel" property="value" value="2">
                <td class="bor1 txt_c bgC_gray w7">
                  <span class="cl_fontOutline"><bean:write name="lineLabel" property="label" /></span>
                </td>
              </logic:equal>
            </logic:iterate>
          </logic:notEmpty>
          <logic:equal name="thisForm" property="ntp061AdrIndex" value="99">
            <td class="no_w bor1 txt_c bgC_gray w20">
              <span class="cl_fontOutline"><gsmsg:write key="address.adr240.1" /></span>
            </td>
          </logic:equal>
          <logic:notEqual name="thisForm" property="ntp061AdrIndex" value="99">
            <td class="no_w bor1 txt_c w20 td-hoverChange cursor_p cl_linkDef" onClick="return selectLine('99');">
              <gsmsg:write key="address.adr240.1" />
            </td>
          </logic:notEqual>
        </tr>
      </table>
      <table class="w100 mt5 bgC_body">
        <tr>
          <logic:equal name="thisForm" property="ntp061AdrStr" value="-1">
            <td class="bor1 txt_c bgC_gray w25">
              <span class="cl_fontOutline"><gsmsg:write key="cmn.all" /></span>
            </td>
          </logic:equal>
          <logic:notEqual name="thisForm" property="ntp061AdrStr" value="-1">
            <td class="bor1 txt_c w25 td-hoverChange cursor_p cl_linkDef" onClick="return selectStr('-1');">
              <gsmsg:write key="cmn.all" />
            </td>
          </logic:notEqual>
          <logic:notEmpty name="thisForm" property="ntp061AdrStrList">
            <logic:iterate id="strLabel" name="thisForm" property="ntp061AdrStrList">
              <logic:equal name="strLabel" property="value" value="0">
                <td class="bor1 txt_c w15 td-hoverChange cursor_p cl_linkDef" onClick="return selectStr('<bean:write name="strLabel" property="label" />');">
                  <bean:write name="strLabel" property="label" />
                </td>
              </logic:equal>
              <logic:equal name="strLabel" property="value" value="1">
                <td class="bor1 txt_c w15">
                  <bean:write name="strLabel" property="label" />
                </td>
              </logic:equal>
              <logic:equal name="strLabel" property="value" value="2">
                <td class="bor1 txt_c bgC_gray w15">
                  <span class="cl_fontOutline"><bean:write name="strLabel" property="label" /></span>
                </td>
              </logic:equal>
            </logic:iterate>
          </logic:notEmpty>
        </tr>
      </table>
    </div>
<% } else if (sMode == 1) { %>
    <ul class="tabHeader w100">
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1" onclick="javascript:change061Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_50) %>');">
        <gsmsg:write key="address.adr100.1" />
      </li>
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2" onclick="javascript:change061Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) %>');">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space border_bottom_none"></li>
    </ul>
    <div id="tab2_block" class="bgC_lightGray bor1 p10">
      <table class="table-left mt0">
        <tr>
          <th class="w15">
            <gsmsg:write key="address.7" />
          </th>
          <td class="w35">
            <html:text property="ntp061code" maxlength="20" styleClass="wp250" />
          </td>
          <th class="w15">
            <gsmsg:write key="address.11" />
          </th>
          <td class="w35">
            <logic:notEmpty name="thisForm" property="atiCmbList">
              <html:select name="thisForm" property="ntp061atiSid">
                <html:optionsCollection name="thisForm" property="atiCmbList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="cmn.company.name" />
          </th>
          <td class="w35">
            <html:text property="ntp061coName" maxlength="50" styleClass="wp250" />
          </td>
          <th class="w15">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="w35">
            <logic:notEmpty name="thisForm" property="tdfkCmbList">
              <html:select name="thisForm" property="ntp061tdfk">
                <html:optionsCollection name="thisForm" property="tdfkCmbList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="address.9" />
          </th>
          <td class="w35">
            <html:text property="ntp061coNameKn" maxlength="100" styleClass="wp250" />
          </td>
          <th class="w15">
            <gsmsg:write key="cmn.memo" />
          </th>
          <td class="w30">
            <html:text property="ntp061biko" maxlength="1000" styleClass="wp250" />
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="address.10" />
          </th>
          <td colspan="3">
            <html:text property="ntp061coBaseName" maxlength="50" styleClass="wp250" />
          </td>
        </tr>
      </table>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>
    </div>
<% }  %>
  <logic:equal name="thisForm" property="ntp061AdrProAddFlg" value="1">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </logic:equal>

  <logic:notEmpty name="thisForm" property="ntp061AdrCompanyList">
    <logic:notEmpty  name="thisForm" property="pageCmbList">
      <div class="paging mt5">
        <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="thisForm" property="ntp061AdrpageTop" onchange="buttonPush('changePageTop');">
          <html:optionsCollection name="thisForm" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>
    <logic:notEqual name="thisForm" property="ntp061Adrmode" value="0">
      <table class="table-top">
        <tr>
          <th>
            <gsmsg:write key="address.118" />
          </th>
        </tr>
        <logic:iterate id="companyModel" name="thisForm" property="ntp061AdrCompanyList" indexId="idx">
          <bean:define id="coData" name="companyModel" type="jp.groupsession.v2.adr.adr240.model.Adr240Model" />
          <% String companyId = String.valueOf(coData.getAcoSid()) + ":" + String.valueOf(coData.getAbaSid()); %>
          <% String companyId2 = String.valueOf(coData.getAcoSid()) + "_" + String.valueOf(coData.getAbaSid()); %>
          <tr class="js_listHover cursor_p">
            <td id="<%= companyId %>" onClick="return setParent('<%= companyId %>');" class="cl_linkDef">
              <input type="hidden" name="ntp061selectCompanyCode<%= companyId2 %>" value="<bean:write name="companyModel" property="acoCode" />">
              <input type="hidden" name="ntp061selectCompanyName<%= companyId %>" value="<bean:write name="companyModel" property="acoName" /> <bean:write name="companyModel" property="abaName" />">
              <bean:write name="companyModel" property="acoName" /> <bean:write name="companyModel" property="abaName" />
            </td>
          </tr>
        </logic:iterate>
      </table>
    </logic:notEqual>
    <logic:equal name="thisForm" property="ntp061Adrmode" value="0">
      <div id="tantoArea"></div>
    </logic:equal>
    <logic:notEmpty  name="thisForm" property="pageCmbList">
      <div class="paging mt5">
        <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="thisForm" property="ntp061AdrpageBottom" onchange="buttonPush('changePageBottom');">
          <html:optionsCollection name="thisForm" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>
  </logic:notEmpty>
  <div class="footerBtn_block mt10 mb10">
    <button type="button" class="baseBtn js_adrClose" value="<gsmsg:write key="cmn.close" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>
</div>
</div>

<%-------------------------------- 商品選択 --------------------------------%>
<logic:equal name="thisForm" property="ntp061ItmKbn" value="0">
  <div class="js_itmArea ntp_itmArea display_n">
</logic:equal>
<logic:notEqual name="thisForm" property="ntp061ItmKbn" value="0">
  <div class="js_itmArea ntp_itmArea">
</logic:notEqual>
<logic:equal name="thisForm" property="ntp061PopKbn" value="0">
  <div class="ntp_itmSelArea txt_c bgC_body">
</logic:equal>
<logic:notEqual name="thisForm" property="ntp061PopKbn" value="0">
  <div class="ntp_itmSelArea txt_c bgC_body">
</logic:notEqual>
<logic:notEmpty name="thisForm" property="ntp061ItmSvChkShohinSidList" >
  <logic:iterate id="item" name="thisForm" property="ntp061ItmSvChkShohinSidList" >
    <input type="hidden" name="ntp061ItmSvChkShohinSidList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="thisForm" property="ntp061ItmSelectedSid" >
  <logic:iterate id="item" name="thisForm" property="ntp061ItmSelectedSid" >
    <input type="hidden" name="ntp061ItmChkShohinSidList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="ntp061ItmNhnSid" />
<html:hidden property="ntp061ItmProcMode" />
<html:hidden property="ntp061ItmReturnPage" />
<html:hidden property="ntp061ItmDspMode" />
<html:hidden property="ntp061ItmInitFlg" />

<%--　BODY --%>
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
        <html:select name="thisForm" property="ntp061CatSid" styleClass="wp200">
          <logic:notEmpty name="thisForm" property="ntp061CategoryList">
            <html:optionsCollection name="thisForm" property="ntp061CategoryList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.122" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="thisForm" property="ntp061ItmNhnCode" maxlength="13" styleClass="wp150" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.154" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="thisForm" property="ntp061ItmNhnName" maxlength="100" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.76" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:text name="thisForm" property="ntp061ItmNhnPriceSale" maxlength="9" styleClass="wp100" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <html:radio name="thisForm" property="ntp061ItmNhnPriceSaleKbn" styleId="ntp061ItmNhnPriceSaleKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_MORE) %>" />
          <label for="ntp061ItmNhnPriceSaleKbn1"><gsmsg:write key="ntp.66" /></label>
          <html:radio name="thisForm" property="ntp061ItmNhnPriceSaleKbn" styleId="ntp061ItmNhnPriceSaleKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_LESS) %>" />
          <label for="ntp061ItmNhnPriceSaleKbn2"><gsmsg:write key="ntp.67" /></label>
        </div>
      </td>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.77" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:text name="thisForm" property="ntp061ItmNhnPriceCost" maxlength="9" styleClass="wp100" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <html:radio name="thisForm" property="ntp061ItmNhnPriceCostKbn" styleId="ntp061ItmNhnPriceCostKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_MORE) %>" />
          <label for="ntp061ItmNhnPriceCostKbn1"><gsmsg:write key="ntp.66" /></label>
          <html:radio name="thisForm" property="ntp061ItmNhnPriceCostKbn" styleId="ntp061ItmNhnPriceCostKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_LESS) %>" />
          <label for="ntp061ItmNhnPriceCostKbn2"><gsmsg:write key="ntp.67" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <span class="text_bb2"><gsmsg:write key="cmn.sortkey" />1
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:select name="thisForm" property="ntp061ItmSortKey1" styleClass="wp100">
            <logic:notEmpty name="thisForm" property="ntp061ItmSortList">
              <html:optionsCollection name="thisForm" property="ntp061ItmSortList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <html:radio name="thisForm" property="ntp061ItmOrderKey1" styleId="ntp061ItmOrderKey11" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
          <label for="ntp061ItmOrderKey11"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="thisForm" property="ntp061ItmOrderKey1" styleId="ntp061ItmOrderKey12" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
          <label for="ntp061ItmOrderKey12"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
      <th class="w15 txt_c">
        <span class="text_bb2"><gsmsg:write key="cmn.sortkey" />2
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:select name="thisForm" property="ntp061ItmSortKey2" styleClass="wp100">
            <logic:notEmpty name="thisForm" property="ntp061ItmSortList">
              <html:optionsCollection name="thisForm" property="ntp061ItmSortList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <html:radio name="thisForm" property="ntp061ItmOrderKey2" styleId="ntp061ItmOrderKey21" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
          <label for="ntp061ItmOrderKey21"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="thisForm" property="ntp061ItmOrderKey2" styleId="ntp061ItmOrderKey22" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
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
  <logic:notEmpty name="thisForm" property="ntp061ItmShohinList">
    <bean:size id="count1" name="thisForm" property="ntp061ItmPageCmbList"  />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="itemSearchPush('itmprevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="cmn.previous.page" />
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp061ItmPageTop" onchange="itmChangePage(0);" styleClass="paging_combo">
          <html:optionsCollection name="thisForm" property="ntp061ItmPageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="itemSearchPush('itmnextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="project.48" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
  <table class="table-top w100">
    <tr>
      <logic:notEqual name="thisForm" property="ntp061ItmDspMode" value="search">
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
    <logic:notEmpty name="thisForm" property="ntp061ItmShohinList">
      <logic:iterate id="syohinMdl" name="thisForm" property="ntp061ItmShohinList" indexId="idx">
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
  <logic:notEmpty name="thisForm" property="ntp061ItmShohinList">
    <bean:size id="count1" name="thisForm" property="ntp061ItmPageCmbList"  />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="itemSearchPush('itmprevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="cmn.previous.page" />
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp061ItmPageBottom" onchange="itmChangePage(1);" styleClass="paging_combo">
          <html:optionsCollection name="thisForm" property="ntp061ItmPageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="itemSearchPush('itmnextPage');">
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
<%-------------------------------- アドレス帳ダイアログ内遷移時 --------------------------------%>
<logic:notEqual name="thisForm" property="ntp061AdrKbn" value="0">
<script type="text/javascript">
Glayer.show();
$(".adrselectbox").css('visibility','visible');
</script>
</logic:notEqual>

<%-------------------------------- 商品ダイアログ内遷移時 --------------------------------%>
<logic:notEqual name="thisForm" property="ntp061ItmKbn" value="0">
<script type="text/javascript">
Glayer.show();
$(".itmselectbox").css('visibility','visible');
</script>
</logic:notEqual>

<%-------------------------------- 登録確認ダイアログ --------------------------------%>
<logic:notEqual name="thisForm" property="ntp061PopKbn" value="0" >
<logic:notEqual name="thisForm" property="ntp061AddFlg" value="0" >
<div id="dialogAddOk" title="" style="display:none">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.78" />
      </td>
    </tr>
  </table>
</div>
<script type="text/javascript">
addOkOpen();
</script>
</logic:notEqual>
</logic:notEqual>


<%-------------------------------- 商品検索時エラーダイアログ --------------------------------%>
<div id="dialog_error" title="" style="display:none">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14 js_shohinErrorStr cl_fontWarn">
      </td>
    </tr>
  </table>
</div>

<div class="js_mikomidoPop" title="<gsmsg:write key="ntp.84" />" style="display:none;">
  <table class="table-left w100">
    <logic:notEmpty name="thisForm" property="ntp061MikomidoMsgList">
      <logic:iterate id="mmdMdl" name="thisForm" property="ntp061MikomidoMsgList">
        <tr>
          <th class="w20">
            <bean:write name="mmdMdl" property="nmmName" />
          </th>
          <td class="w80">
            <bean:write name="mmdMdl" property="nmmMsg" filter="false" />
          </td>
       </tr>
     </logic:iterate>
   </logic:notEmpty>
  </table>
</div>
