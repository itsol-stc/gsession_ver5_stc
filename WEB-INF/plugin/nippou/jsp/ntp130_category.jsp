<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<input type="hidden" name="helpPrm" value="category">

<html:hidden property="ntp130ShohinPageNum" />
<html:hidden property="ntp130SelCategorySid" />

<logic:notEmpty name="ntp130Form" property="ntp130CatList" scope="request">
  <logic:iterate id="sort" name="ntp130Form" property="ntp130CatList" scope="request">
    <input type="hidden" name="ntp130KeyList" value="<bean:write name="sort" property="nscValue" />">
  </logic:iterate>
</logic:notEmpty>

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
      <gsmsg:write key="ntp.59" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonSubmitCatagory('addCategory', 'add' , '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp130');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table class="wp250 mb10 sideTop-font">
    <tr>
      <td class="w50 bor1 txt_c cursor_p bgC_lightGray searchMenu_top searchMenu_top" onclick="changeMode(0);">
        <gsmsg:write key="ntp.58" /><gsmsg:write key="cmn.list" />
      </td>
      <td class="w50 bor1 txt_c bgC_body searchMenu_title-select">
        <gsmsg:write key="ntp.59" />
      </td>
    </tr>
  </table>
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('ntp130up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('ntp130down');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th ckass="w5">
      </th>
      <th class="w35">
        <gsmsg:write key="cmn.category.name" />
      </th>
      <th class="w50">
        <gsmsg:write key="cmn.memo" />
      </th>
      <th class="w10">
        <gsmsg:write key="ntp.58" />
      </th>
    </tr>
    <logic:notEmpty name="ntp130Form" property="ntp130CatList">
      <logic:iterate id="catMdl" name="ntp130Form" property="ntp130CatList" indexId="idx">
      <bean:define id="nscValue" name="catMdl" property="nscValue" />
      <tr class="js_listHover">
        <td class="w5 txt_c js_sord_radio cursor_p">
          <html:radio property="ntp130SortRadio" value="<%= String.valueOf(nscValue) %>" />
        </td>
        <logic:equal name="catMdl" property="nscSid" value="1">
          <td class="w35">
            <bean:write name="catMdl" property="nscName" />
          </td>
        </logic:equal>
        <logic:notEqual name="catMdl" property="nscSid" value="1">
          <td class="w35 cl_linkDef cursor_p" onclick="return buttonSubmitCatagory('categoryEdit', 'edit', '<bean:write name="catMdl" property="nscSid" />')">
            <bean:write name="catMdl" property="nscName" />
          </td>
        </logic:notEqual>
        <logic:equal name="catMdl" property="nscSid" value="1">
          <td class="w50">
            <bean:write name="catMdl" property="nscBiko" filter="false" />
          </td>
        </logic:equal>
        <logic:notEqual name="catMdl" property="nscSid" value="1">
          <td class="w50 cursor_p" onclick="return buttonSubmitCatagory('categoryEdit', 'edit', '<bean:write name="catMdl" property="nscSid" />')">
            <bean:write name="catMdl" property="nscBiko" filter="false" />
          </td>
        </logic:notEqual>
        </td>
        <td class="w10 no_w txt_c">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonCatagoryAdd('add','<bean:write name="catMdl" property="nscSid" />');">
            <gsmsg:write key="cmn.add" />
          </button>
          <button type="button" class="baseBtn js_shohinListBtn" value="<gsmsg:write key="cmn.back" />" id="<bean:write name="catMdl" property="nscSid" />">
            <gsmsg:write key="cmn.list" />
          </button>
        </td>
      </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonSubmitCatagory('addCategory', 'add' , '-1');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp130');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
<div id="shohinPop" title="<gsmsg:write key="ntp.58" />" style="display:none;">
  <p>
    <div id="tmpShohinArea">
    </div>
  </p>
</div>