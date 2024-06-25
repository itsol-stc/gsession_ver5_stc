<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../nippou/js/ntp060.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/nippou/ntp060">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="ntp060FrYear" value="">
<input type="hidden" name="ntp060FrMonth" value="">
<input type="hidden" name="ntp060FrDay" value="">
<input type="hidden" name="ntp060ToYear" value="">
<input type="hidden" name="ntp060ToMonth" value="">
<input type="hidden" name="ntp060ToDay" value="">
<input type="hidden" name="yearRangeMinFr" value="5">
<input type="hidden" name="yearRangeMaxFr" value="5">
<input type="hidden" name="yearRangeMinTo" value="5">
<input type="hidden" name="yearRangeMaxTo" value="5">

<html:hidden property="ntp060NanSid" />
<html:hidden property="ntp060InitFlg" />
<html:hidden property="paramAnkenSid" />
<html:hidden property="dspMod" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--Å@BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
  <div class="display_inline w100 bor1 bgC_body">
    <div class="w20 m0 p0 ">
      <table class="w100 h100">
        <tr class="ntp_sideMenu txt_l borC_light" onclick="buttonPush('nippou')">
          <td class="ntp_menuIcon w5">
            <img class="header_pluginImg-classic ml5" src="../nippou/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
            <img class="header_pluginImg ml5" src="../nippou/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="cmn.shortmail" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu-select bgC_Body txt_l borC_body borBottomC_light borTopC_light">
          <td class="ntp_menuIcon-select  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.11" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('target');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_target_25.png" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_target_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.12" /></span>
          </td>
        </tr>

        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('bunseki');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_bunseki.gif" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_bunseki_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.13" /></span>
          </td>
        </tr>

        <logic:equal name="ntp060Form" property="adminKbn" value="1">
          <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('masta');">
            <td class="ntp_menuIcon w5">
              <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_mente.gif" alt="<gsmsg:write key="main.login.history" />">
              <img class="btn_originalImg-display ml5" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="main.login.history" />">
            </td>
            <td class=" fs_16 no_w w95 lh_normal">
              <span class="timeline_menu ml5"><gsmsg:write key="ntp.14" /></span>
            </td>
          </tr>
        </logic:equal>
        <tr class="h100 ntp_sideMenuArea bor_r1 borC_light">
          <td class="nippou_menuArea h100">&nbsp;</td>
          <td class="nippou_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <table class="w100">
        <tr>
          <td class="w55 txt_l txt_t">

          <div class="txt_r">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="setParams();buttonPush('import');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('export');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <gsmsg:write key="cmn.export" />
            </button>
            <button type="button" class="baseBtn" onClick="setParams();return buttonSubmit('add','-1');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.new.registration" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.new.registration" />">
              <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.new.registration" />
            </button>
          </div>
          <logic:messagesPresent message="false">
            <html:errors/>
          </logic:messagesPresent>
          <div class="verAlignMid mt10 w100">
            <html:select name="ntp060Form" property="ntp060SortKey1" onchange="return buttonPush('search');">
              <logic:notEmpty name="ntp060Form" property="ntp060SortList">
                <html:optionsCollection name="ntp060Form" property="ntp060SortList" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
            <html:radio name="ntp060Form" property="ntp060OrderKey1" styleClass="ml10" styleId="ntp060OrderKey11" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" onclick="return buttonPush('search');" />
            <label for="ntp060OrderKey11"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio name="ntp060Form" property="ntp060OrderKey1" styleClass="ml10" styleId="ntp060OrderKey12" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" onclick="return buttonPush('search');" />
            <label for="ntp060OrderKey12"><gsmsg:write key="cmn.order.desc" /></label>
          <logic:notEmpty name="ntp060Form" property="ntp060AnkenList">
            <bean:size id="count1" name="ntp060Form" property="ntp060PageCmbList" scope="request" />
            <logic:greaterThan name="count1" value="1">
              <div class="paging ml_auto">
                <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="cmn.previous.page" />
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="ntp060PageTop" onchange="changePage(0);" styleClass="paging_combo">
                  <html:optionsCollection name="ntp060Form" property="ntp060PageCmbList" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="project.48" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </logic:notEmpty>
          </div>
          <logic:notEmpty name="ntp060Form" property="ntp060AnkenList">
            <logic:iterate id="ankenMdl" name="ntp060Form" property="ntp060AnkenList" indexId="idx">
            <bean:define id="mod" value="0" />
            <logic:equal name="mod" value="<%= String.valueOf(idx.intValue() % 2) %>">
              <bean:define id="tblColor" value="" />
            </logic:equal>
            <logic:notEqual name="mod" value="<%= String.valueOf(idx.intValue() % 2) %>">
              <bean:define id="tblColor" value="bgC_tableCellEvn" />
            </logic:notEqual>
              <table class="w100 bor1 mt10 <bean:write name="tblColor" />">
                <tr>
                  <td class="w95" colspan="3">
                    <div class="txt_l ml5">
                      <bean:write name="ankenMdl" property="nanCode" />
                      <span class="flo_r">
                        <bean:write name="ankenMdl" property="ntp060Edate" />&nbsp;<gsmsg:write key="cmn.update" />
                      </span>
                    </div>
                  </td>
                  <td class="w5 txt_m cursor_p txt_c js_ankenLinkTag bor_l1" id="<bean:write name="ankenMdl" property="nanSid" />" rowspan="3">
                    <img class="btn_classicImg-display" src="../nippou/images/classic/icon_detail.gif">
                    <img class="btn_originalImg-display" src="../nippou/images/original/icon_detail.png">
                  </td>
                </tr>
                <tr>
                  <td class="w10 txt_c txt_t" rowspan="2">
                    <% String parcent_class= "bgC_ntpParcent cl_white"; %>
                    <logic:equal name="ankenMdl" property="nanMikomi" value="4">
                      <% parcent_class= "bgC_ntpParcentComp cl_white"; %>
                    </logic:equal>
                    <div class="ntp_ankenMikomiStatus bor1 mr10 ml10 cursor_p <%= parcent_class %>" onclick="return buttonSubmit('edit','<bean:write name="ankenMdl" property="nanSid" />')">
                       <logic:equal name="ankenMdl" property="nanMikomi" value="0">10%</logic:equal>
                       <logic:equal name="ankenMdl" property="nanMikomi" value="1">30%</logic:equal>
                       <logic:equal name="ankenMdl" property="nanMikomi" value="2">50%</logic:equal>
                       <logic:equal name="ankenMdl" property="nanMikomi" value="3">70%</logic:equal>
                       <logic:equal name="ankenMdl" property="nanMikomi" value="4">100%</logic:equal>
                    </div>
                  </td>
                  <td class="w80 bor_b1">
                    <div>
                      <span class="fs_18 cl_linkDef cursor_p" onclick="return buttonSubmit('edit','<bean:write name="ankenMdl" property="nanSid" />')"><bean:write name="ankenMdl" property="nanName" /></span>
                      <span class="flo_r cursor_p" onClick="return ntpSearch(<bean:write name="ankenMdl" property="nanSid" />);">
                        <img class="btn_classicImg-display" src="../nippou/images/classic/icon_nippou_search.gif">
                        <img class="btn_originalImg-display" src="../nippou/images/original/menu_icon_single.png">
                      </span>
                    </div>
                  </td>
                  <td class="w5" rowspan="2">
                  </td>
                </tr>
                <tr>
                  <td class="w80 pt5">
                    <logic:notEmpty  name="ankenMdl" property="ntp060CompanyName">
                      <div class="verAlignMid">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
                        <span class="ml5"><bean:write name="ankenMdl" property="ntp060CompanyName" />
                        <logic:notEmpty  name="ankenMdl" property="ntp060BaseName">
                          <br>
                          <bean:write name="ankenMdl" property="ntp060BaseName" />
                        </logic:notEmpty>
                        </span>
                      </div>
                    </logic:notEmpty>
                    <logic:notEmpty  name="ankenMdl" property="ntp060ShohinList">
                      <div>
                        <logic:iterate id="shMdl" name="ankenMdl" property="ntp060ShohinList">
                          <wbr>
                          <span class="mr5">
                            <img class="btn_classicImg-display" src="../nippou/images/classic/icon_shohin.gif">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_contena.png">
                            <bean:write name="shMdl" property="nhnName" />
                          </span>
                        </logic:iterate>
                      </div>
                    </logic:notEmpty>
                    <div id="tooltip_area"></div>
                    <div>
                      <div class="verAlignMid">
                      <span class="js_mitumoriKinStrArea">
                        <img class="btn_classicImg-display" src="../nippou/images/classic/icon_money.gif">
                        <span class="btn_originalImg-display">\</span>
                        <gsmsg:write key="ntp.53" />ÅF<bean:write name="ankenMdl" property="ntp060KinMitumori" />
                      </span>
                      <span class="display_n"><gsmsg:write key="ntp.55" />:<bean:write name="ankenMdl" property="ntp060MitumoriDate" /></span>
                      </div>
                    </div>
                    <div>
                      <div class="verAlignMid">
                      <span class="js_jutyuKinStrArea">
                        <img class="btn_classicImg-display" src="../nippou/images/classic/icon_money.gif">
                        <span class="btn_originalImg-display">\</span>
                        <gsmsg:write key="ntp.54" />ÅF<bean:write name="ankenMdl" property="ntp060KinJutyu" />
                      </span>
                      <span class="display_n"><gsmsg:write key="ntp.56" />:<bean:write name="ankenMdl" property="ntp060JutyuDate" /></span>
                      </div>
                    </div>
                  </td>
                </tr>
              </table>
            </logic:iterate>
          </logic:notEmpty>
          <logic:notEmpty name="ntp060Form" property="ntp060AnkenList">
            <bean:size id="count1" name="ntp060Form" property="ntp060PageCmbList" scope="request" />
            <logic:greaterThan name="count1" value="1">
              <div class="paging mt10">
                <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="cmn.previous.page" />
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="ntp060PageBottom" onchange="changePage(1);" styleClass="paging_combo">
                  <html:optionsCollection name="ntp060Form" property="ntp060PageCmbList" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="project.48" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </logic:notEmpty>

          </td>
          <td class="w45 pl5 txt_t">

          <table class="table-left w100 mt0">
            <tr>
              <th class="txt_l bgC_header1 w50 border_right_none cl_fontOutline" colspan="2">
                <div class="fw_b verAlignMid">
                  <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
                </div>
              </th>
              <th class="txt_r bgC_header1 w50 border_left_none cl_fontOutline">
                <div class="txt_r">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="return buttonPush('search');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                    <gsmsg:write key="cmn.search" />
                  </button>
                </div>
              </th>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.29" />
              </th>
              <td class="w70 bor_t1" colspan="2">
                <html:text name="ntp060Form" property="ntp060NanCode" maxlength="8" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.57" />
              </th>
              <td class="w70" colspan="2">
                <html:text name="ntp060Form" property="ntp060NanName" maxlength="50" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="address.7" />
              </th>
              <td class="w70" colspan="2">
                <html:text name="ntp060Form" property="ntp060AcoCode" maxlength="8" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="cmn.company.name" />
              </th>
              <td class="w70" colspan="2">
                <html:text name="ntp060Form" property="ntp060AcoName" maxlength="50" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="address.9" />
              </th>
              <td class="w70" colspan="2">
                <html:text name="ntp060Form" property="ntp060AcoNameKana" maxlength="100" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.150" />
              </th>
              <td class="w70" colspan="2">
                <html:text name="ntp060Form" property="ntp060AbaName" maxlength="50" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.59" />
              </th>
              <td class="w70" colspan="2">
                <html:select name="ntp060Form" property="ntp060CatSid" styleClass="w100">
                  <logic:notEmpty name="ntp060Form" property="ntp060CategoryList">
                    <html:optionsCollection name="ntp060Form" property="ntp060CategoryList" value="value" label="label" />
                  </logic:notEmpty>
                </html:select>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.58" />
              </th>
              <td class="w70" colspan="2">
                <html:text name="ntp060Form" property="ntp060ShohinName" maxlength="50" styleClass="w100" />
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.11" /><gsmsg:write key="bmk.15" />
              </th>
              <td class="w70 pt0 pb0" colspan="2">
                <div class="lh200">
                  <span class="no_w mr5 verAlignMid">
                    <html:text name="ntp060Form" property="ntp060FrDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </span><!--
               --><span class="fw_b mr5">Å`</span><!--
               --><span class="no_w verAlignMid">
                    <html:text name="ntp060Form" property="ntp060ToDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </span>
                </div>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.61" />
              </th>
              <td class="w70" colspan="2">
                <html:select name="ntp060Form" property="ntp060NgySid" styleClass="select01" onchange="return buttonPush('changeGyomu');" style="width: 150px;">
                  <logic:notEmpty name="ntp060Form" property="ntp060GyomuList">
                    <html:optionsCollection name="ntp060Form" property="ntp060GyomuList" value="value" label="label" />
                  </logic:notEmpty>
                </html:select>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.62" />
              </th>
              <td class="w70" colspan="2">
                <html:select name="ntp060Form" property="ntp060NgpSid" styleClass="select01" style="width: 150px;">
                  <logic:notEmpty name="ntp060Form" property="ntp060ProcessList">
                    <html:optionsCollection name="ntp060Form" property="ntp060ProcessList" value="value" label="label" />
                  </logic:notEmpty>
                </html:select>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.63" />
              </th>
              <td class="w70" colspan="2">
                <div class="verAlignMid">
                  <html:multibox name="ntp060Form" property="ntp060Mikomi" styleId="ntp060mikomido0" value="0" />
                  <label for="ntp060mikomido0">10%</label>
                  <html:multibox name="ntp060Form" property="ntp060Mikomi" styleId="ntp060mikomido1" value="1" styleClass="ml10" />
                  <label for="ntp060mikomido1">30%</label>
                  <html:multibox name="ntp060Form" property="ntp060Mikomi" styleId="ntp060mikomido2" value="2" styleClass="ml10" />
                  <label for="ntp060mikomido2">50%</label>
                </div>
                <div class="verAlignMid">
                  <html:multibox name="ntp060Form" property="ntp060Mikomi" styleId="ntp060mikomido3" value="3" />
                  <label for="ntp060mikomido3">70%</label>
                  <html:multibox name="ntp060Form" property="ntp060Mikomi" styleId="ntp060mikomido4" value="4" styleClass="ml10" />
                  <label for="ntp060mikomido4">100%</label>
                </div>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.53" />
              </th>
              <td class="w70" colspan="2">
                <div>
                  <html:text name="ntp060Form" property="ntp060NanKinMitumori" maxlength="9" styleClass="w80" /><span class="ml5"><gsmsg:write key="project.103" /></span>
                </div>
                <div class="verAlignMid">
                  <html:radio name="ntp060Form" property="ntp060NanKinMitumoriKbn" styleId="ntp060NanKinMitumoriKbn1" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp060.Ntp060Biz.PRICE_MORE) %>" />
                  <label for="ntp060NanKinMitumoriKbn1"><gsmsg:write key="ntp.66" /></label>
                  <html:radio name="ntp060Form" property="ntp060NanKinMitumoriKbn" styleId="ntp060NanKinMitumoriKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp060.Ntp060Biz.PRICE_LESS) %>" />
                  <label for="ntp060NanKinMitumoriKbn2"><gsmsg:write key="ntp.67" /></label>
                </div>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.54" />
              </th>
              <td class="w70" colspan="2">
                <div>
                  <html:text name="ntp060Form" property="ntp060NanKinJutyu" maxlength="9" styleClass="w80" /><span class="ml5"><gsmsg:write key="project.103" /></span>
                </div>
                <div class="verAlignMid">
                  <html:radio name="ntp060Form" property="ntp060NanKinJutyuKbn" styleId="ntp060NanKinJutyuKbn1" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp060.Ntp060Biz.PRICE_MORE) %>" />
                  <label for="ntp060NanKinJutyuKbn1"><gsmsg:write key="ntp.66" /></label>
                  <html:radio name="ntp060Form" property="ntp060NanKinJutyuKbn" styleId="ntp060NanKinJutyuKbn2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.ntp060.Ntp060Biz.PRICE_LESS) %>" />
                  <label for="ntp060NanKinJutyuKbn2"><gsmsg:write key="ntp.67" /></label>
                </div>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.64" />
              </th>
              <td class="w70" colspan="2">
                <div class="verAlignMid">
                  <html:multibox name="ntp060Form" property="ntp060Syodan" styleId="ntp060syodan0" value="0" />
                  <label for="ntp060syodan0"><gsmsg:write key="ntp.68" /></label>
                  <html:multibox name="ntp060Form" property="ntp060Syodan" styleClass="ml10" styleId="ntp060syodan1" value="1" />
                  <label for="ntp060syodan1"><gsmsg:write key="ntp.69" /></label>
                  <html:multibox name="ntp060Form" property="ntp060Syodan" styleClass="ml10" styleId="ntp060syodan2" value="2" />
                  <label for="ntp060syodan2"><gsmsg:write key="ntp.7" /></label>
                </div>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.65" />
              </th>
              <td class="w70" colspan="2">
                <logic:notEmpty name="ntp060Form" property="ntp060ContactList">
                  <div class="verAlignMid">
                    <logic:iterate id="ncnMdl" name="ntp060Form" property="ntp060ContactList" indexId="ncnidx">
                      <bean:define  id="ncnVal" name="ncnMdl" property="value" />
                      <% String idFor = "ntp060NcnSid" + String.valueOf(ncnidx); %>
                      <html:radio name="ntp060Form" property="ntp060NcnSid" styleId="<%= idFor %>"  value="<%= String.valueOf(ncnVal) %>" />
                      <label for="<%= idFor %>" class="mr10"><bean:write  name="ncnMdl" property="label" /></label>
                      <% if (ncnidx != 0 && ncnidx % 2 == 0) { %></div><div class="verAlignMid"><% } %>
                    </logic:iterate>
                  </div>
                </logic:notEmpty>
              </td>
            </tr>
            <tr>
              <th class="w30 no_w fs_12 txt_c">
                <gsmsg:write key="ntp.71" />
              </th>
              <td class="w70" colspan="2">
                <logic:notEmpty name="ntp060Form" property="ntp060StateList">
                  <div class="verAlignMid">
                    <logic:iterate id="stateMdl" name="ntp060Form" property="ntp060StateList" indexId="stateidx">
                      <bean:define  id="ntp060StateVal" name="stateMdl" property="value" />
                      <% String idForState = "ntp060State" + String.valueOf(stateidx); %>
                      <html:radio name="ntp060Form" property="ntp060State" styleId="<%= idForState %>"  value="<%= String.valueOf(ntp060StateVal) %>" />
                      <label for="<%= idForState %>" class="mr10"><bean:write  name="stateMdl" property="label" /></label></wbr>
                    </logic:iterate>
                  </div>
                </logic:notEmpty>
              </td>
            </tr>
          </table>

          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>