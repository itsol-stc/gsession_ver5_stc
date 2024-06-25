<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<!DOCTYPE html>

<% String maxLengthContent = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_VALUE); %>
<% String maxLengthBiko = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_BIKO); %>

<html:html>
  <head>
    <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
    <meta name="format-detection" content="telephone=no">
    <title>GROUPSESSION <gsmsg:write key="schedule.sch041.1" /></title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <!-- Clock script -->
    <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
    <!--  日本語化 -->
    <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
    <link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
    <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
    <script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/selectionSearchText.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../schedule/js/sch041.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/reservepopup.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

    <% String selectionEvent = ""; %>
    <% boolean selectionFlg = false; %>

    <% String closeScript = "calWindowClose();windowClose();"; %>
    <logic:equal name="sch041Form" property="addressPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
    <script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
    <% closeScript += "companyWindowClose();"; %>
    </logic:equal>

    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <style>
      .clockpicker-popover > .arrow {
        display: none;
      }
      .clockpicker-popover {
        margin-top: 0px!important;
      }
    </style>

  </head>

  <body onunload="<%= closeScript %>" onload="changeEnablePublic();setMonthly();setDisabled();setTimeStatus();changeWeekCombo(); showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');showLengthId($('#inputstr2')[0], <%= maxLengthBiko %>, 'inputlength2');<%= selectionEvent %>">

    <html:form action="/schedule/sch041">
      <html:hidden property="cmd" />
      <html:hidden property="dspMod" />
      <html:hidden property="listMod" />
      <html:hidden property="sch010DspDate" />
      <html:hidden property="changeDateFlg" />
      <html:hidden property="iniDsp" />
      <html:hidden property="sch010SelectUsrSid" />
      <html:hidden property="sch010SelectUsrKbn" />
      <html:hidden property="sch010SelectDate" />
      <html:hidden property="sch010SchSid" />
      <html:hidden property="sch010DspGpSid" />
      <html:hidden property="sch020SelectUsrSid" />
      <html:hidden property="sch030FromHour" />
      <html:hidden property="schWeekDate" />
      <html:hidden property="schDailyDate" />
      <html:hidden property="sch100PageNum" />
      <html:hidden property="sch100Slt_page1" />
      <html:hidden property="sch100Slt_page2" />
      <html:hidden property="sch100OrderKey1" />
      <html:hidden property="sch100SortKey1" />
      <html:hidden property="sch100OrderKey2" />
      <html:hidden property="sch100SortKey2" />
      <html:hidden property="sch100SvOrderKey1" />
      <html:hidden property="sch100SvSortKey1" />
      <html:hidden property="sch100SvOrderKey2" />
      <html:hidden property="sch100SvSortKey2" />
      <html:hidden property="sch100SelectUsrSid" />
      <html:hidden property="sch100SvSltGroup" />
      <html:hidden property="sch100SvSltUser" />
      <html:hidden property="sch100SvSltStartYearFr" />
      <html:hidden property="sch100SvSltStartMonthFr" />
      <html:hidden property="sch100SvSltStartDayFr" />
      <html:hidden property="sch100SvSltStartYearTo" />
      <html:hidden property="sch100SvSltStartMonthTo" />
      <html:hidden property="sch100SvSltStartDayTo" />
      <html:hidden property="sch100SvSltEndYearFr" />
      <html:hidden property="sch100SvSltEndMonthFr" />
      <html:hidden property="sch100SvSltEndDayFr" />
      <html:hidden property="sch100SvSltEndYearTo" />
      <html:hidden property="sch100SvSltEndMonthTo" />
      <html:hidden property="sch100SvSltEndDayTo" />
      <html:hidden property="sch100SvKeyWordkbn" />
      <html:hidden property="sch100SvKeyValue" />
      <html:hidden property="sch100SltGroup" />
      <html:hidden property="sch100SltUser" />
      <html:hidden property="sch100SltStartYearFr" />
      <html:hidden property="sch100SltStartMonthFr" />
      <html:hidden property="sch100SltStartDayFr" />
      <html:hidden property="sch100SltStartYearTo" />
      <html:hidden property="sch100SltStartMonthTo" />
      <html:hidden property="sch100SltStartDayTo" />
      <html:hidden property="sch100SltEndYearFr" />
      <html:hidden property="sch100SltEndMonthFr" />
      <html:hidden property="sch100SltEndDayFr" />
      <html:hidden property="sch100SltEndYearTo" />
      <html:hidden property="sch100SltEndMonthTo" />
      <html:hidden property="sch100SltEndDayTo" />
      <html:hidden property="sch100KeyWordkbn" />

      <logic:notEmpty name="sch041Form" property="sch100SelectScdSid" scope="request">
        <logic:iterate id="selectScdSid" name="sch041Form" property="sch100SelectScdSid" scope="request">
          <input type="hidden" name="sch100SelectScdSid" value="<bean:write name="selectScdSid"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="sch010searchWord" />


      <logic:notEmpty name="sch041Form" property="sch100Bgcolor" scope="request">
        <logic:iterate id="bgcolor" name="sch041Form" property="sch100Bgcolor" scope="request">
          <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="sch100SvSearchTarget" scope="request">
        <logic:iterate id="svTarget" name="sch041Form" property="sch100SvSearchTarget" scope="request">
          <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="sch100SearchTarget" scope="request">
        <logic:iterate id="target" name="sch041Form" property="sch100SearchTarget" scope="request">
          <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="sch100CsvOutField" scope="request">
        <logic:iterate id="csvOutField" name="sch041Form" property="sch100CsvOutField" scope="request">
          <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="sch040Bgcolor" />
      <html:hidden property="sch040Title" />
      <html:hidden property="sch040Value" />
      <html:hidden property="sch040Biko" />
      <html:hidden property="sch040Public" />
      <html:hidden property="sch040FrYear" />
      <html:hidden property="sch040FrMonth" />
      <html:hidden property="sch040FrDay" />
      <html:hidden property="sch040FrHour" />
      <html:hidden property="sch040FrMin" />
      <html:hidden property="sch040ToYear" />
      <html:hidden property="sch040ToMonth" />
      <html:hidden property="sch040ToDay" />
      <html:hidden property="sch040ToHour" />
      <html:hidden property="sch040ToMin" />
      <html:hidden property="sch040FrDate"/>
      <html:hidden property="sch040ToDate"/>
      <html:hidden property="sch040FrTime"/>
      <html:hidden property="sch040ToTime"/>
      <html:hidden property="sch040Edit" />
      <html:hidden property="sch040TimeKbn" />
      <html:hidden property="sch040InitFlg" />
      <html:hidden property="sch040ScrollFlg" />
      <html:hidden property="sch040CrangeKbn" />
      <html:hidden property="sch041ExtSid" />
      <html:hidden property="reservePluginKbn" />
      <html:hidden property="addressPluginKbn" />
      <html:hidden property="searchPluginKbn" />

      <html:hidden property="sch040ReminderTime" />
      <html:hidden property="sch040TargetGroup" />

      <input type="hidden" name="sch041delCompanyId" value="">
      <input type="hidden" name="sch041delCompanyBaseId" value="">
      <html:hidden property="sch040contact" />

      <logic:equal name="sch041Form" property="sch010SelectUsrKbn" value="1">
          <html:hidden property="sch040GroupSid" />
      </logic:equal>

      <logic:notEmpty name="sch041Form" property="sch040CompanySid">
        <logic:iterate id="coId" name="sch041Form" property="sch040CompanySid">
          <input type="hidden" name="sch040CompanySid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>
      </div>

      <logic:notEmpty name="sch041Form" property="sch040CompanyBaseSid">
        <logic:iterate id="coId" name="sch041Form" property="sch040CompanyBaseSid">
          <input type="hidden" name="sch040CompanyBaseSid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="sch040AddressId">
        <logic:iterate id="addressId" name="sch041Form" property="sch040AddressId">
          <input type="hidden" name="sch040AddressId" value="<bean:write name="addressId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="sch040DisplayTarget" scope="request">
        <logic:iterate id="dtuBean" name="sch041Form" property="sch040DisplayTarget" scope="request">
          <input type="hidden" name="sch040DisplayTarget" value="<bean:write name="dtuBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <div id="sch041CompanyIdArea">
        <logic:notEmpty name="sch041Form" property="sch041CompanySid">
          <logic:iterate id="coId" name="sch041Form" property="sch041CompanySid">
            <input type="hidden" name="sch041CompanySid" value="<bean:write name="coId"/>">
          </logic:iterate>
        </logic:notEmpty>
      </div>

      <div id="sch041CompanyBaseIdArea">
        <logic:notEmpty name="sch041Form" property="sch041CompanyBaseSid">
          <logic:iterate id="coId" name="sch041Form" property="sch041CompanyBaseSid">
            <input type="hidden" name="sch041CompanyBaseSid" value="<bean:write name="coId"/>">
          </logic:iterate>
        </logic:notEmpty>
      </div>

      <div id="sch041AddressIdArea">
        <logic:notEmpty name="sch041Form" property="sch041AddressId">
          <logic:iterate id="addressId" name="sch041Form" property="sch041AddressId">
            <input type="hidden" name="sch041AddressId" value="<bean:write name="addressId"/>">
          </logic:iterate>
        </logic:notEmpty>
      </div>

      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <input type="hidden" name="helpPrm" value="<bean:write name="sch041Form" property="sch010SelectUsrKbn" /><bean:write name="sch041Form" property="cmd" />">

      <logic:notEmpty name="sch041Form" property="sv_users" scope="request">
        <logic:iterate id="ulBean" name="sch041Form" property="sv_users" scope="request">
          <input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="svReserveUsers" scope="request">
        <logic:iterate id="resBean" name="sch041Form" property="svReserveUsers" scope="request">
          <input type="hidden" name="svReserveUsers" value="<bean:write name="resBean" />">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="sch041Form" property="sch100SvBgcolor" scope="request">
        <logic:iterate id="svBgcolor" name="sch041Form" property="sch100SvBgcolor" scope="request">
          <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="sch041Form" property="sch100Bgcolor" scope="request">
        <logic:iterate id="bgcolor" name="sch041Form" property="sch100Bgcolor" scope="request">
          <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="sch040AmFrHour"/>
      <html:hidden property="sch040AmFrMin"/>
      <html:hidden property="sch040AmToHour"/>
      <html:hidden property="sch040AmToMin"/>
      <html:hidden property="sch040PmFrHour"/>
      <html:hidden property="sch040PmFrMin"/>
      <html:hidden property="sch040PmToHour"/>
      <html:hidden property="sch040PmToMin"/>
      <html:hidden property="sch040AllDayFrHour"/>
      <html:hidden property="sch040AllDayFrMin"/>
      <html:hidden property="sch040AllDayToHour"/>
      <html:hidden property="sch040AllDayToMin"/>
      <html:hidden property="sch041FrYear" styleId="selYear"/>
      <html:hidden property="sch041FrMonth" styleId="selMonth"/>
      <html:hidden property="sch041FrDay" styleId="selDay"/>
      <html:hidden property="sch041ToYear" styleId="seleYear"/>
      <html:hidden property="sch041ToMonth" styleId="seleMonth"/>
      <html:hidden property="sch041ToDay" styleId="seleDay"/>
      <html:hidden property="sch041FrHour"/>
      <html:hidden property="sch041FrMin"/>
      <html:hidden property="sch041ToHour"/>
      <html:hidden property="sch041ToMin"/>
      <html:hidden property="sch041BinSid" />
      <html:hidden property="hourDivision"/>

      <html:hidden property="sch041InitFlg"/>

      <div class="pageTitle w80 mrl_auto">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
            <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
          </li>
          <li>
            <gsmsg:write key="schedule.108" />
          </li>
          <li class="pageTitle_subFont">
            <gsmsg:write key="cmn.for.repert" />
          </li>
          <li>
            <div>
              <logic:equal name="sch041Form" property="cmd" value="add">
                <input type="hidden" name="CMD" value="041_ok">
                <button class="baseBtn" value="<gsmsg:write key="cmn.entry.2" />">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                  <gsmsg:write key="cmn.entry" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041_back', '<bean:write name="sch041Form" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>

              <logic:equal name="sch041Form" property="cmd" value="edit">
                <input type="hidden" name="CMD" value="041_ok">
                <button id="editbtn" class="baseBtn" value="<gsmsg:write key="schedule.43" />">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
                  <gsmsg:write key="cmn.change" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonPush('041_copy', 'add');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                  <gsmsg:write key="cmn.register.copy2" />
                </button>
                <logic:notEqual name="sch041Form" property="sch041ExtSid" value="0">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete2" />" onClick="buttonPush('041_del', 'del');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                </logic:notEqual>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041_back', '<bean:write name="sch041Form" property="sch040BtnCmd" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper w80 mrl_auto">
        <div class="verAlignMid w100">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.general.regist" />" onClick="buttonPush('041_default', '<bean:write name="sch041Form" property="sch040BtnCmd" />');">
            <gsmsg:write key="cmn.general.regist" />
          </button>
          <div class="mrl_auto"></div>
        </div>
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>

        <bean:define id="tdColor" value="" />
        <logic:notEqual name="sch041Form" property="sch010SelectUsrKbn" value="0">
          <bean:define id="tdColor" value="bgC_other1" />
        </logic:notEqual>
        <logic:equal name="sch041Form" property="sch010SelectUsrKbn" value="0">
          <bean:define id="tdColor" value="bgC_tableCell" />
        </logic:equal>

        <table class="table-left w100">
          <!-- 名前 -->
          <tr>
            <th class="w20 txt_l" colspan="2"><gsmsg:write key="schedule.4" /></th>
            <td class="w80 <%= tdColor %>">
              <logic:notEqual name="sch041Form" property="sch010SelectUsrKbn" value="0">
                <span class="flo_l">
                  <img class="classic-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
                  <img class="original-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.group" />">
                </span>
              </logic:notEqual>
              <logic:equal name="sch041Form" property="sch040UsrUkoFlg" value="1">
                <span class=" mukoUser">
                  <bean:write name="sch041Form" property="sch040UsrName" />
                </span>
              </logic:equal>
              <logic:notEqual name="sch041Form" property="sch040UsrUkoFlg" value="1">
                <bean:write name="sch041Form" property="sch040UsrName" />
              </logic:notEqual>
            </td>
          </tr>

          <!-- 拡張設定 -->
          <tr>
            <th class="w20 txt_l" colspan="2"><gsmsg:write key="cmn.advanced.settings" /></th>
            <td class="w80 <%= tdColor %> txt_t">
              <div class="display_inline txt_t">
                <div class="verAlignMid">
                  <html:radio name="sch041Form" property="sch041ExtKbn" value="1" styleId="sch041ExtKbn0" onclick="setDisabled();" />
                  <label for="sch041ExtKbn0"><gsmsg:write key="cmn.everyday" /></label>
                </div>
                <div class="verAlignMid ml10" >
                  <html:radio name="sch041Form" property="sch041ExtKbn" value="2" styleId="sch041ExtKbn1" onclick="setDisabled();" />
                  <label for="sch041ExtKbn1"><gsmsg:write key="cmn.weekly2" /></label>
                </div>
                <div class="verAlignMid ml10" >
                  <html:radio name="sch041Form" property="sch041ExtKbn" value="5" styleId="sch041ExtKbn4" onclick="setDisabled();" />
                  <label for="sch041ExtKbn4"><gsmsg:write key="cmn.biweekly" /></label>
                </div>
                <div class="verAlignMid ml10" >
                  <html:radio name="sch041Form" property="sch041ExtKbn" value="3" styleId="sch041ExtKbn2" onclick="setDisabled();" />
                  <label for="sch041ExtKbn2"><gsmsg:write key="cmn.monthly.2" /></label>
                </div>
                <div class="verAlignMid ml10" >
                  <html:radio name="sch041Form" property="sch041ExtKbn" value="4" styleId="sch041ExtKbn3" onclick="setDisabled();" />
                  <label for="sch041ExtKbn3"><gsmsg:write key="cmn.yearly" /></label>
                </div>
              </div>
              <div class="js_adv-monthly mt10 ml10 display_n">
                <div class="verAlignMid">
                  <html:radio name="sch041Form" property="sch041WeekOrDay" value="0" styleId="sch041MonKbn0" onclick="setMonthly();" />
                  <label for="sch041MonKbn0"><gsmsg:write key="schedule.sch041.11" /></label>
                </div>
                <div class="verAlignMid ml10">
                  <html:radio name="sch041Form" property="sch041WeekOrDay" value="1" styleId="sch041MonKbn1" onclick="setMonthly();" />
                  <label for="sch041MonKbn1"><gsmsg:write key="schedule.sch041.12" /></label>
                </div>
                <div class="js_weekArea">
                  <div class="verAlignMid">
                    <html:select property="sch041Week">
                      <html:optionsCollection name="sch041Form" property="sch041WeekLabel" value="value" label="label" />
                    </html:select>
                  </div>
                </div>
                <div class="js_dayArea">
                  <div class="verAlignMid">
                    <html:select property="sch041Day">
                      <html:optionsCollection name="sch041Form" property="sch041ExDayLabel" value="value" label="label" />
                    </html:select>
                    <html:select property="sch041ConfKbn" styleClass="ml5" onchange="changeconfKbnCombo();">
                      <html:optionsCollection name="sch041Form" property="sch041ConfKbnLabel" value="value" label="label" />
                    </html:select>
                    <span class="ml5 js_periodArea">
                      <gsmsg:write key="cmn.of" />
                      <html:select property="sch041DayOfMonth" styleClass="ml10" styleId="selDayOfMonth">
                        <html:optionsCollection name="sch041Form" property="sch041DayLabel" value="value" label="label" />
                      </html:select>
                    </span>
                  </div>
                </div>
              </div>
              <div class="js_adv-weekday ml10 mt5 display_n">
                <div>
                  <div class="bor_l1 bor_t1 display_inline">
                    <div class="wp30hp30 bgC_calSunday verAlignMid bor_r1">
                      <span class="cl_fontSunday mrl_auto">
                        <gsmsg:write key="cmn.sunday" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <gsmsg:write key="cmn.Monday" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <gsmsg:write key="cmn.tuesday" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <gsmsg:write key="cmn.wednesday" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <gsmsg:write key="cmn.thursday" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <gsmsg:write key="cmn.friday" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_calSaturday verAlignMid bor_r1">
                      <span class="cl_fontSaturday mrl_auto">
                        <gsmsg:write key="cmn.saturday" />
                      </span>
                    </div>
                  </div>
                </div>
                <div>
                  <div class="bor_l1 bor_t1 bor_b1  display_inline">
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="cl_fontSunday mrl_auto">
                        <html:multibox property="sch041Dweek" value="1" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <html:multibox property="sch041Dweek" value="2" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <html:multibox property="sch041Dweek" value="3" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <html:multibox property="sch041Dweek" value="4" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <html:multibox property="sch041Dweek" value="5" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="mrl_auto">
                        <html:multibox property="sch041Dweek" value="6" />
                      </span>
                    </div>
                    <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                      <span class="cl_fontSaturday mrl_auto">
                        <html:multibox property="sch041Dweek" value="7" />
                      </span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="js_adv-date mt5 ml10 display_n">
                <html:select property="sch041MonthOfYearly" styleId="selMonthOfYearly">
                   <html:optionsCollection name="sch041Form" property="sch040MonthLabel" value="value" label="label" />
                </html:select>
                <html:select property="sch041DayOfYearly" styleId="selDayOfYearly">
                   <html:optionsCollection name="sch041Form" property="sch041ExDayOfYearlyLabel" value="value" label="label" />
                </html:select>
              </div>
              <div class="mt10 ml10">
                <span class="cl_fontWarn fs_13"><gsmsg:write key="schedule.sch041.2" /></span><br>
                <div class="verAlignMid" id="selTranKbn9">
                  <html:radio name="sch041Form" property="sch041TranKbn" styleId="sch041TranKbn9" value="9" />
                  <label for="sch041TranKbn9"><gsmsg:write key="cmn.dont.entry" /></label>
                </div><br>
                <div class="verAlignMid" id="selTranKbn1">
                  <html:radio name="sch041Form" property="sch041TranKbn" styleId="sch041TranKbn1" value="0" />
                  <label for="sch041TranKbn1"><gsmsg:write key="schedule.144" /></label>
                </div>
                <span id="selTranKbnArea">
                <br>
                <div class="verAlignMid" id="selTranKbn2">
                  <html:radio name="sch041Form" property="sch041TranKbn" styleId="sch041TranKbn2" value="1" />
                  <label for="sch041TranKbn2"><gsmsg:write key="cmn.change.before.businessday" /></label>
                </div><br>
                <div class="verAlignMid" id="selTranKbn3">
                  <html:radio name="sch041Form" property="sch041TranKbn" styleId="sch041TranKbn3" value="2" />
                  <label for="sch041TranKbn3"><gsmsg:write key="cmn.change.next.businessday" /></label>
                </div>
                </span>
              </div>
              <div>
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.holiday.based.timecard" />
              </div>
            </td>
          </tr>
          <!-- 設定期間 開始-->
          <tr>
            <th class="txt_l" rowspan="2">
              <gsmsg:write key="schedule.sch041.8" />
            </th>
            <th >
              <gsmsg:write key="cmn.start" />
            </th>
            <td class="w80 txt_l <%= tdColor %>">
              <div class="verAlignMid">
                <span class="pos_rel display_flex">
                  <html:text name="sch041Form" property="sch041FrDate" maxlength="10" styleClass="wp90 h100 easyRegister-text datepicker" styleId="easyFrDate"/>
                  <span class="picker-acs cursor_pointer icon-date input-group-addon" id="iconKikanStart"></span>
                </span>

                <button type="button" class="webIconBtn ml5" onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 1);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 2);">
                  <gsmsg:write key="cmn.today" />
                </button>
                <span class="original-display">
                  <a class="fw_b todayBtn " onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 2);">
                    <gsmsg:write key="cmn.today" />
                  </a>
                </span>
                <button type="button" class="webIconBtn" onClick="return moveFromDay($('#selYear')[0], $('#selMonth')[0], $('#selDay')[0], 3);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
              </div>
            </td>
          </tr>
          <!-- 設定期間 終了-->
          <tr>
            <th class="txt_l">
              <gsmsg:write key="main.src.man250.30" />
            </th>
            <td class="w80 txt_l <%= tdColor %>">
              <div class="verAlignMid">
                <span class="pos_rel display_flex">
                  <html:text name="sch041Form" property="sch041ToDate" maxlength="10" styleClass="wp90 h100 easyRegister-text datepicker" styleId="easyToDate"/>
                  <span class="picker-acs cursor_pointer icon-date input-group-addon" id="iconKikanFinish"></span>
                </span>

                <button type="button" class="webIconBtn ml5" onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 1);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 2);">
                  <gsmsg:write key="cmn.today" />
                </button>
                <span class="original-display">
                  <a class="fw_b todayBtn " onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 2);">
                    <gsmsg:write key="cmn.today" />
                  </a>
                </span>
                <button type="button" class="webIconBtn" onClick="return moveToDay($('#seleYear')[0], $('#seleMonth')[0], $('#seleDay')[0], 3);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
              </div>
            </td>
          </tr>
          <!-- 時間-->

          <tr>
            <th class="w20 txt_l" colspan="2"><gsmsg:write key="cmn.time" /></th>
            <td class="w80 <%= tdColor %>">
              <div class="mb5 txt_l verAlignMid">
                <html:checkbox name="sch041Form" property="sch041TimeKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.TIME_NOT_EXIST) %>" styleId="num_seigyo" onclick="return changeTimeStatus();" />
                <label for="num_seigyo"><gsmsg:write key="schedule.7" /></label>

                <span class="js_time_master ml5">
                  <a href="#!" onclick="setAmTime();">
                    <gsmsg:write key="cmn.am" />
                    <span class="tooltips">
                      <bean:write name="sch041Form" property="sch040AmFrHour" format="00" />:<bean:write name="sch041Form" property="sch040AmFrMin" format="00" />～<bean:write name="sch041Form" property="sch040AmToHour" format="00" />:<bean:write name="sch041Form" property="sch040AmToMin" format="00" />
                    </span>
                  </a>
                </span>
                <span class="js_time_master ml5">
                  <a href="#!" onclick="setPmTime();">
                    <gsmsg:write key="cmn.pm" />
                    <span class="tooltips">
                      <bean:write name="sch041Form" property="sch040PmFrHour" format="00" />:<bean:write name="sch041Form" property="sch040PmFrMin" format="00" />～<bean:write name="sch041Form" property="sch040PmToHour" format="00" />:<bean:write name="sch041Form" property="sch040PmToMin" format="00" />
                    </span>
                  </a>
                </span>
                <span class="js_time_master ml5">
                 <a href="#!" onclick="setAllTime();">
                   <gsmsg:write key="cmn.allday" />
                   <span class="tooltips">
                     <bean:write name="sch041Form" property="sch040AllDayFrHour" format="00" />:<bean:write name="sch041Form" property="sch040AllDayFrMin" format="00" />～<bean:write name="sch041Form" property="sch040AllDayToHour" format="00" />:<bean:write name="sch041Form" property="sch040AllDayToMin" format="00" />
                   </span>
                 </a>
                </span>
              </div><br>
              <div class="verAlignMid">
                <label class="clockpicker_fr pos_rel display_flex input-group">
                  <html:text name="sch041Form" property="sch041FrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60 "/>
                  <span class="picker-acs cursor_pointer icon-clock input-group-addon"></span>
                </label>
                <span class="ml5 mr5"><gsmsg:write key="tcd.153" /></span>
                <label class="input-group clockpicker_to ml5 pos_rel display_flex">
                  <html:text name="sch041Form" property="sch041ToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60 "/>
                  <span class="picker-acs cursor_pointer icon-clock input-group-addon"></span>
                </label>
              </div>
            </td>
          </tr>

          <!-- 会社・担当者 -->
          <logic:equal name="sch041Form" property="addressPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
            <tr>
              <th class="w20 txt_l" colspan="2"><gsmsg:write key="schedule.14" /></th>
              <td class="w80 <%= tdColor %>">
                <div class="verAlignMid">
                  <button type="button" class="baseBtn ml0" value="<gsmsg:write key="addressbook" />" onClick="return openCompanyWindow('sch041');">
                    <img class="btn_classicImg-display wp18hp20" src="../schedule/images/classic/icon_address.gif" alt="<gsmsg:write key="addressbook" />">
                    <img class="btn_originalImg-display wp18hp20" src="../schedule/images/original/icon_address.png" alt="<gsmsg:write key="addressbook" />">
                    <gsmsg:write key="addressbook" />
                  </button>
                  <logic:notEmpty name="sch041Form" property="sch041CompanyList">
                    <logic:notEmpty name="sch041Form" property="sch041AddressId">
                      <html:checkbox name="sch041Form" property="sch041contact" value="1" styleId="contactCheck" styleClass="ml10"/>
                      <label for="contactCheck"><gsmsg:write key="schedule.23" /></label>
                    </logic:notEmpty>
                  </logic:notEmpty>
                </div>

                <logic:notEmpty name="sch041Form" property="sch041CompanyList">
                  <logic:iterate id="companyData" name="sch041Form" property="sch041CompanyList">
                    <ul class="m5 p5">
                      <li class="txt_m verAlignMid w3">
                        <a href="#" onClick="deleteCompany(<bean:write name="companyData" property="companySid" />, <bean:write name="companyData" property="companyBaseSid" />);">
                          <img src="../common/images/classic/icon_delete_2.gif" class="classic-display img-18"/>
                          <img src="../common/images/original/icon_delete.png" class="original-display"/>
                        </a>
                      </li>
                      <li class="txt_m display_inline-block">
                        <div class="txt_m">
                          <img src="../common/images/classic/icon_company.png" class="classic-display"/>
                          <img src="../common/images/original/icon_company.png" class="original-display"/>
                          <logic:equal name="companyData" property="companySid" value="0">
                            <bean:write name="companyData" property="companyName" />
                          </logic:equal>
                          <logic:notEqual name="companyData" property="companySid" value="0">
                            <a href="#!" onclick="return openSubWindow('../address/adr250.do?adr250AcoSid=<bean:write name="companyData" property="companySid" />')">
                              <bean:write name="companyData" property="companyName" />
                            </a>
                          </logic:notEqual>
                        </div>
                        <logic:notEmpty name="companyData" property="addressDataList">
                          <logic:iterate id="addressData" name="companyData" property="addressDataList">
                            <div class="txt_m">
                              <img src="../common/images/classic/icon_user.png" class="classic-display img-18"/>
                              <img src="../common/images/original/icon_user.png" class="original-display"/>
                              <bean:write name="addressData" property="adrName" />
                            </div>
                          </logic:iterate>
                        </logic:notEmpty>
                      </li>
                    </ul>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </tr>
          </logic:equal>

          <!-- タイトル -->
          <tr>
            <th class="w20 txt_l" colspan="2">
              <gsmsg:write key="cmn.title" />
              <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
            </th>
            <td class="w80 <%= tdColor %>">
              <html:text name="sch041Form" maxlength="50" property="sch041Title" styleClass="wp400" styleId="selectionSearchArea" />
            </td>
          </tr>

          <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch041_sub1.jsp">
            <jsp:param value="<%= tdColor %>" name="tdColor" />
          </jsp:include>


          <%-- グループスケジュール 非表示部分 START --%>
          <logic:notEqual name="sch041Form" property="sch010SelectUsrKbn" value="1">
            <html:hidden property="sch040BatchRef" value="1" />

            <!-- 同時登録 -->
            <tr>
              <th class="w20 txt_l" colspan="2"><gsmsg:write key="schedule.117" /></th>
              <td class="w80 <%= tdColor %>">
                <div class="cl_fontWarn  fs_13 pos_abs">[<gsmsg:write key="schedule.29" />]</div>
                <ui:usrgrpselector name="sch041Form" property="sch041SameUserUI" styleClass="hp200" />
              </td>
            </tr>
          </logic:notEqual>
          <%-- グループスケジュール 非表示部分 END --%>

          <logic:equal name="sch041Form" property="sch010SelectUsrKbn" value="1">
            <html:hidden property="sch040ReserveGroupSid" value="-1" />
          </logic:equal>

          <%-- 施設予約 --%>
          <logic:equal name="sch041Form" property="reservePluginKbn" value="0">
            <tr>
              <th class="w20 txt_l" colspan="2"><gsmsg:write key="cmn.reserve" /></th>
              <td class="w80 <%= tdColor %>">
                <div class="cl_fontWarn fs_13  pos_abs ">[<gsmsg:write key="schedule.26" />]</div>
                <ui:multiselector name="sch041Form" property="sch041SameReserveUI" styleClass="hp200"  />
              </td>
            </tr>
          </logic:equal>

          <%-- 空き状況確認 --%>
          <tr>
            <th class="w20 txt_l" colspan="2"><gsmsg:write key="schedule.18" /></th>
            <td class="w80 <%= tdColor %>">
              <gsmsg:write key="cmn.comments" /><gsmsg:write key="schedule.35" />
              <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.17" />" onClick="openScheduleReserveWindowSch040('<bean:write name="sch041Form" property="sch041ReserveGroupSid" />','<bean:write name="sch041Form" property="sch010SelectDate" />');">
                <gsmsg:write key="schedule.17" />
              </button>
           </td>
          </tr>

          <logic:notEqual name="sch041Form" property="reservePluginKbn" value="0">
            <html:hidden property="sch041ReserveGroupSid" />
          </logic:notEqual>
          <%-- 登録者 --%>
          <tr>
          <th class="w20 txt_l" colspan="2"><gsmsg:write key="cmn.registant" /></th>
          <td class="w80 <%= tdColor %>">
            <div class="display_inline">
              <logic:notEqual name="sch041Form" property="sch040AddUsrJkbn" value="9">
                <logic:equal name="sch041Form" property="sch040AddUsrUkoFlg" value="1">
                  <span class="mukoUser"><bean:write name="sch041Form" property="sch040AddUsrName" /></span>
                </logic:equal>
                <logic:notEqual name="sch041Form" property="sch040AddUsrUkoFlg" value="1">
                  <span><bean:write name="sch041Form" property="sch040AddUsrName" /></span>
                </logic:notEqual>
              </logic:notEqual>
              <logic:equal name="sch041Form" property="sch040AddUsrJkbn" value="9">
                <del>
                  <bean:write name="sch041Form" property="sch040AddUsrName" />
                </del>
              </logic:equal>
            </div>
            <div class="ml20 display_inline">
              <bean:write name="sch041Form" property="sch040AddDate" />
            </span>
          </td>
        </tr>
        </table>
        <ul class="w100 verAlignMid">
          <li class="mrl_auto">
          </li>
          <li class="footerBtn_block">
            <logic:equal name="sch041Form" property="cmd" value="add">
              <button class="baseBtn" value="<gsmsg:write key="cmn.entry.2" />">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <gsmsg:write key="cmn.entry" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041_back', '<bean:write name="sch041Form" property="sch040BtnCmd" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:equal>

            <logic:equal name="sch041Form" property="cmd" value="edit">
              <button id="editbtn" class="baseBtn" value="<gsmsg:write key="schedule.43" />">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
                <gsmsg:write key="cmn.change" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonPush('041_copy', 'add');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                <gsmsg:write key="cmn.register.copy2" />
              </button>
              <logic:notEqual name="sch041Form" property="sch041ExtSid" value="0">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete2" />" onClick="buttonPush('041_del', 'del');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:notEqual>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back2" />" onClick="buttonPush('041_back', '<bean:write name="sch041Form" property="sch040BtnCmd" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:equal>
          </li>
        </ul>
      </div>
    </html:form>

    <% if (selectionFlg) { %>
      <span id="tooltip_search" class="tooltip_search"></span>
      <span id="damy"></span>
    <% } %>
    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
  </body>
</html:html>
