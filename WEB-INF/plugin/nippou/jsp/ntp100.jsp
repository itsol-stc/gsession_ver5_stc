<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.usr.UserUtil" %>
<%-- キーワード検索区分 --%>
<%
  String keyWordAnd    = String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.KEY_WORD_KBN_AND);
  String keyWordOr     = String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.KEY_WORD_KBN_OR);
%>
<%-- 検索対象 --%>
<%
  String targetTitle   = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SEARCH_TARGET_TITLE);
  String targetHonbun  = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SEARCH_TARGET_HONBUN);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /> [<gsmsg:write key="cmn.search" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp100.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp200.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="setToUser();" onunload="windowClose();">
<html:form action="/nippou/ntp100">

<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp" %>

<input type="hidden" name="listMod" value="5">
<input type="hidden" name="ntp100SltYearFr" value="">
<input type="hidden" name="ntp100SltMonthFr" value="">
<input type="hidden" name="ntp100SltDayFr" value="">
<input type="hidden" name="ntp100SltYearTo" value="">
<input type="hidden" name="ntp100SltMonthTo" value="">
<input type="hidden" name="ntp100SltDayTo" value="">
<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="ntp010DspDate" />
<html:hidden property="ntp010SelectUsrSid" />
<html:hidden property="ntp010SelectUsrKbn" />
<html:hidden property="ntp010SelectDate" />
<html:hidden property="ntp010NipSid" />
<html:hidden property="ntp100SvAnkenSid" />
<html:hidden property="ntp100SvCompanySid" />
<html:hidden property="ntp100SvCompanyBaseSid" />
<html:hidden property="ntp100SvKtbunrui" />
<html:hidden property="ntp100SvKthouhou" />
<html:hidden property="ntp100SvSltGroup" />
<html:hidden property="ntp100SvSltUser" />
<html:hidden property="ntp100SvSltYearFr" />
<html:hidden property="ntp100SvSltMonthFr" />
<html:hidden property="ntp100SvSltDayFr" />
<html:hidden property="ntp100SvSltYearTo" />
<html:hidden property="ntp100SvSltMonthTo" />
<html:hidden property="ntp100SvSltDayTo" />
<html:hidden property="ntp100SvKeyWordkbn" />
<html:hidden property="ntp100SvKeyValue" />
<html:hidden property="ntp100SvOrderKey1" />
<html:hidden property="ntp100SvSortKey1" />
<html:hidden property="ntp100SvOrderKey2" />
<html:hidden property="ntp100SvSortKey2" />
<html:hidden property="ntp100SelectNtpAreaSid" />
<html:hidden property="ntp010CrangeKbn" />

<logic:notEmpty name="ntp100Form" property="ntp100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="ntp100Form" property="ntp100SvSearchTarget" scope="request">
    <input type="hidden" name="ntp100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ntp100Form" property="ntp100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="ntp100Form" property="ntp100SvBgcolor" scope="request">
    <input type="hidden" name="ntp100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ntp100Form" property="ntp100SvMikomido" scope="request">
  <logic:iterate id="svMikomido" name="ntp100Form" property="ntp100SvMikomido" scope="request">
    <input type="hidden" name="ntp100SvMikomido" value="<bean:write name="svMikomido"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="ntp100PageNum" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

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
      <gsmsg:write key="cmn.search" />
    </li>
    <li>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto">
  <div class="display_inline w100 bor1 bgC_body">
    <div class="w20 m0 p0 ">
      <table class="w100 h100">
        <tr class="ntp_sideMenu-select bgC_Body txt_l border_top_none borC_body borBottomC_light">
          <td class="ntp_menuIcon-select w5">
            <img class="header_pluginImg-classic ml5" src="../nippou/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="ntp.1" />">
            <img class="header_pluginImg ml5" src="../nippou/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="ntp.1" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('anken');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="ntp.11" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken_32.png" alt="<gsmsg:write key="ntp.11" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.11" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('target');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_target_25.png" alt="<gsmsg:write key="ntp.12" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_target_32.png" alt="<gsmsg:write key="ntp.12" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.12" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('bunseki');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_bunseki.gif" alt="<gsmsg:write key="ntp.13" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_bunseki_32.png" alt="<gsmsg:write key="ntp.13" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.13" /></span>
          </td>
        </tr>
        <logic:equal name="ntp100Form" property="adminKbn" value="1">
          <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('masta');">
            <td class="ntp_menuIcon  w5">
              <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_mente.gif" alt="<gsmsg:write key="ntp.14" />">
              <img class="btn_originalImg-display ml5" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="ntp.14" />">
            </td>
            <td class=" fs_16 no_w w95 lh_normal">
              <span class="timeline_menu ml5"><gsmsg:write key="ntp.14" /></span>
            </td>
          </tr>
        </logic:equal>
        <tr class="h100 ntp_sideMenuArea bor_r1 borC_light">
          <td class="nippou_menuArea">&nbsp;</td>
          <td class="nippou_menuArea">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.timeline" />" onClick="buttonPush('day');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.timeline" />">
          <gsmsg:write key="cmn.timeline" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('week');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
          <gsmsg:write key="cmn.weeks" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.monthly" />" onClick="buttonPush('month');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
          <gsmsg:write key="cmn.monthly" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>
      <logic:messagesPresent message="false">
        <html:errors/>
      </logic:messagesPresent>
      <table class="table-left w100">
        <tr>
          <th class="bgC_header1" colspan="4">
            &nbsp;
          </th>
        </tr>
        <tr>
          <th class="w15 txt_c">
            <gsmsg:write key="schedule.sch100.4" />
          </th>
          <td class="w85" colspan="3">
            <html:select property="ntp100SltGroup" styleClass="wp200" onchange="changeGroupCombo();">
              <logic:notEmpty name="ntp100Form" property="ntp100GroupLabel" scope="request">
                <logic:iterate id="gpBean" name="ntp100Form" property="ntp100GroupLabel" scope="request">
                  <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                    <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:notEqual name="gpBean" property="styleClass" value="0">
                    <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:notEqual>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>
            <logic:notEqual name="ntp100Form" property="ntp010CrangeKbn" value="1">
              <button type="button" class="iconBtn-border" value="Cal" onclick="openNtp100Group(this.form.ntp100SltGroup, 'ntp100SltGroup', '1');" id="ntp100GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </logic:notEqual>
            <html:select property="ntp100SltUser" styleClass="wp200">
              <logic:iterate id="user" name="ntp100Form" property="ntp100UserLabel" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                <html:option value="<%=user.getValue() %>" styleClass="<%=user.getCSSClassNameOption() %>"><bean:write name="user" property="label" /></html:option>
              </logic:iterate>
            </html:select>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="ntp.163" />
          </th>
          <td class="w80" colspan="3">
            <div class="verAlignMid">
            <html:text name="ntp100Form" property="ntp100SltDateFr" maxlength="10" styleId="frDate" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#frDate')[0], 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#frDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span>
              <a class="fw_b todayBtn original-display" onClick="return moveDay($('#frDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn mr5" onClick="return moveDay($('#frDate')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <span class="mr5">～</span>
            <html:text name="ntp100Form" property="ntp100SltDateTo" maxlength="10" styleId="toDate" styleClass="txt_c wp95 ml5 datepicker js_toDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#toDate')[0], 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#toDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span>
              <a class="fw_b todayBtn original-display" onClick="return moveDay($('#toDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveDay($('#toDate')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="ntp.11" />
          </th>
          <td class="w35 txt_t">
            <button type="button" class="baseBtn" onclick="return openAnkenWindow('ntp100','')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />">
              <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
            </button>
            <logic:notEmpty name="ntp100Form" property="ntp100NtpAnkenModel" scope="request">
              <bean:define id="ankenMdl" name="ntp100Form" property="ntp100NtpAnkenModel" />
              <div id="ntp100AnkenIdArea">
                <input name="ntp100AnkenSid" value="<bean:write name="ankenMdl" property="nanSid" />" type="hidden">
              </div>
              <div id="ntp100AnkenCodeArea">
                <gsmsg:write key="ntp.29" />：<bean:write name="ankenMdl" property="nanCode" />
              </div>
              <div id="ntp100AnkenNameArea">
                <a id="<bean:write name="ankenMdl" property="nanSid" />" class="cl_linkDef js_anken_click">
                  <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken.png">
                  <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                  <bean:write name="ankenMdl" property="nanName" />
                </a>
                <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif" onclick="delAnken('ntp100','');">
                <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png" onclick="delAnken('ntp100','');">
              </div>
            </logic:notEmpty>
            <logic:empty name="ntp100Form" property="ntp100NtpAnkenModel" scope="request">
              <div id="ntp100AnkenIdArea"></div>
              <div id="ntp100AnkenCodeArea"></div>
              <div id="ntp100AnkenNameArea"></div>
            </logic:empty>
          </td>
          <th class="w15 txt_c">
            <gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" />
          </th>
          <td class="w30 txt_t">
            <button type="button" class="baseBtn" onclick="return openCompanyWindow2('ntp100','')">
              <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif" alt="<gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png" alt="<gsmsg:write key="cmn.search" />">
              <gsmsg:write key="addressbook" />
            </button>
            <logic:notEmpty name="ntp100Form" property="ntp100AdrCompanyMdl" scope="request">
              <bean:define id="companyMdl" name="ntp100Form" property="ntp100AdrCompanyMdl" />
              <div id="ntp100CompanyIdArea">
                <input name="ntp100CompanySid" value="<bean:write name="companyMdl" property="acoSid" />" type="hidden">
              </div>
              <logic:notEmpty name="ntp100Form" property="ntp100AdrCompanyBaseMdl" scope="request">
                <bean:define id="companyBaseMdl" name="ntp100Form" property="ntp100AdrCompanyBaseMdl" />
                <div id="ntp100CompanyBaseIdArea">
                  <input name="ntp100CompanyBaseSid" value="<bean:write name="companyBaseMdl" property="abaSid" />" type="hidden">
                </div>
              </logic:notEmpty>
              <div id="ntp100CompanyCodeArea">
                <gsmsg:write key="address.7" />：<bean:write name="companyMdl" property="acoCode" />
              </div>
              <logic:notEmpty name="ntp100Form" property="ntp100AdrCompanyBaseMdl" scope="request">
                <div id="ntp100CompNameArea">
                  <a id="102" class=" cl_linkDef js_compClick">
                    <img class="btn_classicImg-display wp18" src="../common/images/classic/icon_company.png">
                    <img class="btn_originalImg-display wp18" src="../common/images/original/icon_company.png">
                    <bean:write name="companyMdl" property="acoName" /><span class="ml5"><bean:write name="companyBaseMdl" property="abaName" /></span>
                  </a>
                  <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif" onclick="delCompany('ntp100','');">
                  <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png" onclick="delCompany('ntp100','');">
                </div>
              </logic:notEmpty>
              <logic:empty name="ntp100Form" property="ntp100AdrCompanyBaseMdl" scope="request">
                <div id="ntp100CompanyBaseIdArea"></div>
                <div id="ntp100CompNameArea">
                  <a id="102" class=" cl_linkDef js_compClick">
                    <img class="btn_classicImg-display wp18" src="../common/images/classic/icon_company.png">
                    <img class="btn_originalImg-display wp18" src="../common/images/original/icon_company.png">
                    <bean:write name="companyMdl" property="acoName" />
                  </a>
                  <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif" onclick="delCompany('ntp100','');">
                  <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png" onclick="delCompany('ntp100','');">
                </div>
              </logic:empty>
              <div id="ntp100AddressIdArea"></div>
              <div id="ntp100AddressNameArea"></div>
            </logic:notEmpty>
            <logic:empty name="ntp100Form" property="ntp100AdrCompanyMdl" scope="request">
              <div id="ntp100CompanyIdArea"></div>
              <div id="ntp100CompanyBaseIdArea"></div>
              <div id="ntp100CompanyCodeArea"></div>
              <div id="ntp100CompNameArea"></div>
              <div id="ntp100AddressIdArea"></div>
              <div id="ntp100AddressNameArea"></div>
            </logic:empty>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="ntp.3" />
          </th>
          <td class="w80" colspan="3">
            <logic:notEmpty name="ntp100Form" property="ntp100KtbunruiLavel">
              <html:select property="ntp100Ktbunrui">
                <html:optionsCollection name="ntp100Form" property="ntp100KtbunruiLavel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="ntp100Form" property="ntp100KthouhouLavel">
              <html:select property="ntp100Kthouhou">
                <html:optionsCollection name="ntp100Form" property="ntp100KthouhouLavel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="ntp.32" />
          </th>
          <td class="w80" colspan="3">
            <div class="verAlignMid">
              <html:multibox name="ntp100Form" property="ntp100Mikomido" styleId="ntp100Mikomido0" value="0" />
              <label for="ntp100Mikomido0">10%</label>
              <html:multibox name="ntp100Form" property="ntp100Mikomido" styleId="ntp100Mikomido1" styleClass="ml10" value="1" />
              <label for="ntp100Mikomido1">30%</label>
              <html:multibox name="ntp100Form" property="ntp100Mikomido" styleId="ntp100Mikomido2" styleClass="ml10" value="2" />
              <label for="ntp100Mikomido2">50%</label>
              <html:multibox name="ntp100Form" property="ntp100Mikomido" styleId="ntp100Mikomido3" styleClass="ml10" value="3" />
              <label for="ntp100Mikomido3">70%</label>
              <html:multibox name="ntp100Form" property="ntp100Mikomido" styleId="ntp100Mikomido4" styleClass="ml10" value="4" />
              <label for="ntp100Mikomido4">100%</label>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="cmn.keyword" />
          </th>
          <td class="w35">
            <div>
              <html:text name="ntp100Form" maxlength="50" property="ntp010searchWord" styleClass="w100" />
            </div>
            <div>
              <div class="verAlignMid no_w">
                <html:radio name="ntp100Form" property="ntp100KeyWordkbn" value="<%= keyWordAnd %>" styleId="keyKbn_01" />
                <label for="keyKbn_01"><gsmsg:write key="cmn.contains.all.and" /></label>
                <html:radio name="ntp100Form" property="ntp100KeyWordkbn" value="<%= keyWordOr %>" styleId="keyKbn_02" styleClass="ml10" />
                <label for="keyKbn_02"><gsmsg:write key="cmn.orcondition" /></label>
              </div>
            </div>
          </td>
          <th class="w15 txt_c">
            <gsmsg:write key="cmn.search2" />
          </th>
          <td class="w30">
            <div class="verAlignMid">
              <html:multibox styleId="search_scope_01" name="ntp100Form" property="ntp100SearchTarget" value="<%= targetTitle %>" />
              <label for="search_scope_01"><gsmsg:write key="cmn.title" /></label>
              <html:multibox styleId="search_scope_02" name="ntp100Form" property="ntp100SearchTarget" value="<%= targetHonbun %>" styleClass="ml10" />
              <label for="search_scope_02"><gsmsg:write key="cmn.content" /></label>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="schedule.10" />
          </th>
          <td class="w80" colspan="3">
            <span class="ntp_titlecolor-block bgc_fontSchTitleBlue">
              <html:multibox name="ntp100Form" property="ntp100Bgcolor" styleId="ntp100Bgcolor0" value="1" />
            </span>
            <span class="ntp_titlecolor-block bgc_fontSchTitleRed ml10">
              <html:multibox name="ntp100Form" property="ntp100Bgcolor" styleId="ntp100Bgcolor1" value="2" />
            </span>
            <span class="ntp_titlecolor-block bgc_fontSchTitleGreen ml10">
              <html:multibox name="ntp100Form" property="ntp100Bgcolor" styleId="ntp100Bgcolor2" value="3" />
            </span>
            <span class="ntp_titlecolor-block bgc_fontSchTitleYellow ml10">
              <html:multibox name="ntp100Form" property="ntp100Bgcolor" styleId="ntp100Bgcolor3" value="4" />
            </span>
            <span class="ntp_titlecolor-block bgc_fontSchTitleBlack ml10">
              <html:multibox name="ntp100Form" property="ntp100Bgcolor" styleId="ntp100Bgcolor4" value="5" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="cmn.sort.order" />
          </th>
          <td class="w80" colspan="3">
            <div class="verAlignMid">
              <gsmsg:write key="cmn.first.key" />
              <html:select property="ntp100SortKey1" styleClass="ml5">
                <html:optionsCollection name="ntp100Form" property="sortKeyList" value="value" label="label" />
              </html:select>
              <html:radio name="ntp100Form" property="ntp100OrderKey1" styleId="sort1_up" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
              <label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
              <html:radio name="ntp100Form" property="ntp100OrderKey1" styleId="sort1_dw" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
              <label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
              <span class="ml20"><gsmsg:write key="cmn.second.key" /></span>
              <html:select property="ntp100SortKey2" styleClass="ml5">
                <html:optionsCollection name="ntp100Form" property="sortKeyList" value="value" label="label" />
              </html:select>
              <html:radio name="ntp100Form" property="ntp100OrderKey2" styleId="sort2_up" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_ASC) %>" />
              <label for="sort2_up"><gsmsg:write key="cmn.order.asc" /></label>
              <html:radio name="ntp100Form" property="ntp100OrderKey2" styleId="sort2_dw" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.ORDER_KEY_DESC) %>" />
              <label for="sort2_dw"><gsmsg:write key="cmn.order.desc" /></label>
            </div>
          </td>
        </tr>
      </table>
      <div class="mt10 txt_c w100">
        <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('ntp100search');">
          <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
          <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>
      <logic:notEmpty name="ntp100Form" property="ntp100NippouList">
        <table class="table-left w100">
          <tr>
            <th class="w10 txt_c no_w">
              <gsmsg:write key="reserve.output.item" />
            </th>
            <td class="w70 border_right_none">
              <logic:equal name="ntp100Form" property="adminKbn" value="1">
                <span class="verAlignMid no_w">
                  <html:multibox styleId="loginId" name="ntp100Form" property="ntp100CsvOutField" value="1" />
                  <label for="loginId" class="mr10"><gsmsg:write key="cmn.user.id" /></label>
                </span>
              </logic:equal>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="userName" name="ntp100Form" property="ntp100CsvOutField" value="2" />
                <label for="userName" class="mr10"><gsmsg:write key="cmn.user.name" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="ntpDate" name="ntp100Form" property="ntp100CsvOutField" value="3" />
                <label for="ntpDate" class="mr10"><gsmsg:write key="ntp.35" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="frTime" name="ntp100Form" property="ntp100CsvOutField" value="4" />
                <label for="frTime" class="mr10"><gsmsg:write key="cmn.starttime" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="toTime" name="ntp100Form" property="ntp100CsvOutField" value="5" />
                <label for="toTime" class="mr10"><gsmsg:write key="cmn.endtime" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="ankenCode" name="ntp100Form" property="ntp100CsvOutField" value="6" />
                <label for="ankenCode" class="mr10"><gsmsg:write key="ntp.29" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="cmpCode" name="ntp100Form" property="ntp100CsvOutField" value="7" />
                <label for="cmpCode" class="mr10"><gsmsg:write key="address.7" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="title" name="ntp100Form" property="ntp100CsvOutField" value="8" />
                <label for="title" class="mr10"><gsmsg:write key="cmn.title" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="titleColor" name="ntp100Form" property="ntp100CsvOutField" value="9" />
                <label for="titleColor" class="mr10">タイトル色</label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="kbunruicode" name="ntp100Form" property="ntp100CsvOutField" value="10" />
                <label for="kbunruicode" class="mr10"><gsmsg:write key="ntp.117" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="khouhoucode" name="ntp100Form" property="ntp100CsvOutField" value="11" />
                <label for="khouhoucode" class="mr10"><gsmsg:write key="ntp.118" /></label>
              </span>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="value" name="ntp100Form" property="ntp100CsvOutField" value="12" />
                <label for="value" class="mr10"><gsmsg:write key="cmn.content" /></label>
              <wbr>
              <span class="verAlignMid no_w">
                <html:multibox styleId="mikomido" name="ntp100Form" property="ntp100CsvOutField" value="13" />
                <label for="mikomido"><gsmsg:write key="ntp.32" /></label>
            </td>
            <td class="w10 no_w border_left_none">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('ntp100export');">
                <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
                <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
                <gsmsg:write key="cmn.export" />
              </button>
            </td>
          </tr>
        </table>
        <bean:size id="count1" name="ntp100Form" property="ntp100PageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_left');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left"></i>
            </button>
            <logic:notEmpty name="ntp100Form" property="ntp100PageLabel">
              <html:select property="ntp100Slt_page1" onchange="changePage1();" styleClass="paging_combo">
                <html:optionsCollection name="ntp100Form" property="ntp100PageLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:empty name="ntp100Form" property="ntp100PageLabel">
              <html:select property="ntp100Slt_page1" styleClass="paging_combo">
               <option value="1" class="text_i">1 / 1</option>
              </html:select>
            </logic:empty>
            <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_right');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>
      <logic:notEmpty name="ntp100Form" property="ntp100NippouList">
        <table class="table-top w100">
          <tr>
            <th class="w15 cursor_p table_header-evt">
              <div onclick="clickSortTitle('1')">
                <gsmsg:write key="cmn.name4" />
              </div>
            </th>
            <th class="w15 cursor_p table_header-evt">
              <div onclick="clickSortTitle('2')">
                <gsmsg:write key="ntp.35" />
              </div>
            </th>
            <th class="w15">
              <gsmsg:write key="ntp.11" />
            </th>
            <th class="w55 cursor_p table_header-evt">
              <div onclick="clickSortTitle('4')">
                <gsmsg:write key="cmn.title" />/<gsmsg:write key="cmn.content" />
              </div>
            </th>
          </tr>
          <logic:iterate id="ntpMdl" name="ntp100Form" property="ntp100NippouList" indexId="idx" type="jp.groupsession.v2.ntp.ntp010.SimpleNippouModel">
            <tr class="cursor_p js_listHover" id="ntpArea_<bean:write name="ntpMdl" property="ntpSid" />" onClick="editNippou('edit', <bean:write name="ntpMdl" property="ntpDspDateStr" />, <bean:write name="ntpMdl" property="ntpSid" />, <bean:write name="ntpMdl" property="userSid" />, 0);">
              <td class="w15">
                <span class="<%=UserUtil.getCSSClassNameNormal(ntpMdl.getUserUkoFlg()) %>"><bean:write name="ntpMdl" property="userName" /></span>
              </td>
              <td class="w15 txt_c no_w">
                <div>
                  <bean:write name="ntpMdl" property="ntpDateStr" />
                </div>
                <div>
                  <bean:write name="ntpMdl" property="fromTimeStr" />～<bean:write name="ntpMdl" property="toTiemStr" />
                </div>
              </td>
              <td class="w15 txt_c">
                <bean:write name="ntpMdl" property="ankenName" />
              </td>
              <td class="w55">
                <bean:define id="chKbn" name="ntpMdl" property="ntp_chkKbn" type="java.lang.Integer" />
                <% String chkClass = "ntp_checkContent ntp_midokuContent"; %>
                <% if (chKbn == 1) { %>
                <%    chkClass = ""; %>
                <% } %>
                <div class="<%= chkClass %>">
                  <div>
                  <!-- コメントアイコン表示  -->
                  <logic:notEqual name="ntpMdl" property="ntp_cmtCnt" value="0">
                    <span class="mr5">
                      <img class="btn_classicImg-display" src="../nippou/images/classic/icon_comment.gif" alt="<gsmsg:write key="cmn.preferences2" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_comment.png" alt="<gsmsg:write key="cmn.preferences2" />">
                      <bean:write name="ntpMdl" property="ntp_cmtCnt" />
                    </span>
                  </logic:notEqual>
                  <%-- いいねアイコン表示  --%>
                  <logic:notEqual name="ntpMdl" property="ntp_goodCnt" value="0">
                    <img class="btn_classicImg-display" src="../nippou/images/classic/bg_good_2.gif" alt="<gsmsg:write key="cmn.preferences2" />">
                    <img class="btn_originalImg-display" src="../nippou/images/original/icon_good.png" alt="<gsmsg:write key="cmn.preferences2" />">
                    <bean:write name="ntpMdl" property="ntp_goodCnt" />
                  </logic:notEqual>
                  </div>
                  <!--タイトルカラー設定-->
                  <logic:equal name="ntpMdl" property="titleColor" value="1">
                  <div class="cl_fontSchTitleBlue">
                  </logic:equal>
                  <logic:equal name="ntpMdl" property="titleColor" value="2">
                  <div class="cl_fontSchTitleRed">
                  </logic:equal>
                  <logic:equal name="ntpMdl" property="titleColor" value="3">
                  <div class="cl_fontSchTitleGreen">
                  </logic:equal>
                  <logic:equal name="ntpMdl" property="titleColor" value="4">
                  <div class="cl_fontSchTitleYellow">
                  </logic:equal>
                  <logic:equal name="ntpMdl" property="titleColor" value="5">
                  <div class="cl_fontSchTitleBlack">
                  </logic:equal>
                    <bean:write name="ntpMdl" property="title" />
                  </div>
                  <div>
                    <bean:write name="ntpMdl" property="detail" filter="false"/>
                  </div>
                </div>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:notEmpty>
      <logic:notEmpty name="ntp100Form" property="ntp100NippouList">
        <bean:size id="count1" name="ntp100Form" property="ntp100PageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_left');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left"></i>
            </button>
            <logic:notEmpty name="ntp100Form" property="ntp100PageLabel">
              <html:select property="ntp100Slt_page2" onchange="changePage2();" styleClass="paging_combo">
                <html:optionsCollection name="ntp100Form" property="ntp100PageLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:empty name="ntp100Form" property="ntp100PageLabel">
              <html:select property="ntp100Slt_page1" styleClass="paging_combo">
               <option value="1" class="text_i">1 / 1</option>
              </html:select>
            </logic:empty>
            <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_right');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>