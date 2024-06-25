<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<input type="hidden" name="helpPrm" value="shohin">


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
      <gsmsg:write key="ntp.58" /><gsmsg:write key="cmn.list" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush2('import');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <gsmsg:write key="cmn.import" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush2('export');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
        <gsmsg:write key="cmn.export" />
      </button>
      <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="return buttonSubmit('add','-1');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp130');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table class="wp250 mb10 sideTop-font">
    <tr>
      <td class="w50 bor1 bgC_body txt_c searchMenu_title-select">
        <gsmsg:write key="ntp.58" /><gsmsg:write key="cmn.list" />
      </td>
      <td class="w50 cursor_p bgC_lightGray bor1 txt_c searchMenu_top searchMenu_top" onclick="changeMode(1);">
        <gsmsg:write key="ntp.59" />
      </td>
    </tr>
  </table>
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left w100">
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w85" colspan="3">
        <html:select name="ntp130Form" property="ntp130CatSid" styleClass="wp200">
          <logic:notEmpty name="ntp130Form" property="ntp130CategoryList">
            <html:optionsCollection name="ntp130Form" property="ntp130CategoryList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.122" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="ntp130Form" property="ntp130NhnCode" maxlength="13" styleClass="wp150" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.154" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="ntp130Form" property="ntp130NhnName" maxlength="50" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.76" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:text name="ntp130Form" property="ntp130NhnPriceSale" maxlength="9" styleClass="wp100" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <html:radio name="ntp130Form" property="ntp130NhnPriceSaleKbn" styleId="ntp130NhnPriceSaleKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_MORE) %>" />
          <label for="ntp130NhnPriceSaleKbn1"><gsmsg:write key="ntp.66" /></label>
          <html:radio name="ntp130Form" property="ntp130NhnPriceSaleKbn" styleId="ntp130NhnPriceSaleKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_LESS) %>" />
          <label for="ntp130NhnPriceSaleKbn2"><gsmsg:write key="ntp.67" /></label>
        </div>
      </td>
      <th class="w15 txt_c">
        <gsmsg:write key="ntp.77" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:text name="ntp130Form" property="ntp130NhnPriceCost" maxlength="9" styleClass="wp100" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <html:radio name="ntp130Form" property="ntp130NhnPriceCostKbn" styleId="ntp130NhnPriceCostKbn1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_MORE) %>" />
          <label for="ntp130NhnPriceCostKbn1"><gsmsg:write key="ntp.66" /></label>
          <html:radio name="ntp130Form" property="ntp130NhnPriceCostKbn" styleId="ntp130NhnPriceCostKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp130.Ntp130Biz.PRICE_LESS) %>" />
          <label for="ntp130NhnPriceCostKbn2"><gsmsg:write key="ntp.67" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.sortkey" />1
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:select name="ntp130Form" property="ntp130SortKey1" styleClass="wp100">
            <logic:notEmpty name="ntp130Form" property="ntp130SortList">
              <html:optionsCollection name="ntp130Form" property="ntp130SortList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <html:radio name="ntp130Form" property="ntp130OrderKey1" styleId="ntp130OrderKey11" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
          <label for="ntp130OrderKey11"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="ntp130Form" property="ntp130OrderKey1" styleId="ntp130OrderKey12" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
          <label for="ntp130OrderKey12"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.sortkey" />2
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:select name="ntp130Form" property="ntp130SortKey2" styleClass="wp100">
            <logic:notEmpty name="ntp130Form" property="ntp130SortList">
              <html:optionsCollection name="ntp130Form" property="ntp130SortList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <html:radio name="ntp130Form" property="ntp130OrderKey2" styleId="ntp130OrderKey21" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
          <label for="ntp130OrderKey21"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="ntp130Form" property="ntp130OrderKey2" styleId="ntp130OrderKey22" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
          <label for="ntp130OrderKey22"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="mt10 txt_c w100 mb20">
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onclick="return buttonPush2('search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <logic:notEmpty name="ntp130Form" property="ntp130ShohinList">
    <bean:size id="count1" name="ntp130Form" property="ntp130PageCmbList" scope="request" />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush2('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp130PageTop" onchange="changePage(0);" styleClass="paging_combo">
          <html:optionsCollection name="ntp130Form" property="ntp130PageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="buttonPush2('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
  <table class="table-top w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.category" /><br><gsmsg:write key="ntp.122" />
      </th>
      <th class="w30">
        <gsmsg:write key="ntp.154" />
      </th>
      <th class="w10 no_w">
        <gsmsg:write key="ntp.76" />
      </th>
      <th class="w10 no_w">
        <gsmsg:write key="ntp.77" />
      </th>
      <th class="w15 no_w">
        <gsmsg:write key="bmk.15" />
      </th>
      <th class="w15 no_w">
        <gsmsg:write key="ntp.155" />
      </th>
    </tr>
    <logic:notEmpty name="ntp130Form" property="ntp130ShohinList">
      <logic:iterate id="syohinMdl" name="ntp130Form" property="ntp130ShohinList" indexId="idx">
        <tr class="js_listHover cursor_p" onclick="return buttonSubmit('edit','<bean:write name="syohinMdl" property="nhnSid" />');">
          <td class="w20">
            <div>
              <span class="baseLabel ml0">
                <bean:write name="syohinMdl" property="nscName" />
              </span>
            </div>
            <div>
              <span class="cl_linkDef"><bean:write name="syohinMdl" property="nhnCode" /></span>
            </div>
          </td>
          <td class="w30">
            <span class="cl_linkDef"><bean:write name="syohinMdl" property="nhnName" /></span>
          </td>
          <td class="w10 txt_r">
            \<bean:write name="syohinMdl" property="ntp130PriceSale" />
          </td>
          <td class="w10 txt_r">
            \<bean:write name="syohinMdl" property="ntp130PriceCost" />
          </td>
          <td class="w15 txt_c">
            <bean:write name="syohinMdl" property="ntp130ADate" />
          </td>
          <td class="w15 txt_c">
            <bean:write name="syohinMdl" property="ntp130EDate" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <logic:notEmpty name="ntp130Form" property="ntp130ShohinList">
    <bean:size id="count1" name="ntp130Form" property="ntp130PageCmbList" scope="request" />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush2('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp130PageBottom" styleClass="paging_combo" onchange="changePage(1);">
          <html:optionsCollection name="ntp130Form" property="ntp130PageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="buttonPush2('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
</div>