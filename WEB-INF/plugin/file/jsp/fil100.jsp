<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.model.FileDspModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@ page import="jp.groupsession.v2.fil.fil100.Fil100Form"%>
<!DOCTYPE html>
<%-- CMD定数 --%>
<%
  String searchClick = jp.groupsession.v2.fil.fil100.Fil100Action.CMD_SEARCH;
%>

<%-- 定数 --%>
<%
  String keyWordAnd = String.valueOf(jp.groupsession.v2.fil.GSConstFile.KEY_WORD_KBN_AND);
  String keyWordOr = String.valueOf(jp.groupsession.v2.fil.GSConstFile.KEY_WORD_KBN_OR);
  String targetFolder = String.valueOf(jp.groupsession.v2.fil.GSConstFile.GET_TARGET_FOLDER);
  String targetFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.GET_TARGET_FILE);
  String targetDeleted = String.valueOf(jp.groupsession.v2.fil.GSConstFile.GET_TARGET_DELETED);
  String targetName = String.valueOf(jp.groupsession.v2.fil.GSConstFile.KEYWORD_TARGET_NAME);
  String targetBiko = String.valueOf(jp.groupsession.v2.fil.GSConstFile.KEYWORD_TARGET_BIKO);
  String targetText = String.valueOf(jp.groupsession.v2.fil.GSConstFile.KEYWORD_TARGET_TEXT);
  String dirFolder = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DIRECTORY_FOLDER);
  String dirFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DIRECTORY_FILE);
  String callOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CALL_ON);
  String cabinetKbnErrl = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_KBN_ERRL);
  String pgid = String.valueOf(0);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.60" /></title>
<theme:css filename="theme.css" />

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>

<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/search.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil100.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/components/currency-input/index.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<logic:equal name="fil100Form" property="fil100WarnDspFlg" value="1">
  <body onload="filedateNoKbn();tradeDateNoKbn();displayMoneyTo();tradeMoneyInit();showWarnDialog('<bean:write name="fil100Form" property="fil100ResultCount" />');">
</logic:equal>
<logic:notEqual name="fil100Form" property="fil100WarnDspFlg" value="1">
  <body onload="filedateNoKbn();tradeDateNoKbn();displayMoneyTo();tradeMoneyInit();">
</logic:notEqual>

<html:form action="/file/fil100">

  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
  
  <logic:equal name="fil100Form" property="fil100SltCabinetKbn" value="<%= cabinetKbnErrl %>">
    <% pgid = String.valueOf(1); %>
  </logic:equal>
  <input type="hidden" name="helpPrm" value="<%= pgid %>">

  <input type="hidden" name="CMD" value="">
  <input type="hidden" name="yearRangeMinFr" value="10">
  <input type="hidden" name="yearRangeMaxFr" value="10">
  <input type="hidden" name="yearRangeMinTo" value="10">
  <input type="hidden" name="yearRangeMaxTo" value="10">
  <input type="hidden" name="fileSearchfromYear" value="">
  <input type="hidden" name="fileSearchfromMonth" value="">
  <input type="hidden" name="fileSearchfromDay" value="">
  <input type="hidden" name="fileSearchtoYear" value="">
  <input type="hidden" name="fileSearchtoMonth" value="">
  <input type="hidden" name="fileSearchtoDay" value="">

  <html:hidden name="fil100Form" property="searchFlg" />
  <html:hidden name="fil100Form" property="fil100SvSltCabinetSid" />
  <html:hidden name="fil100Form" property="fil100SvChkTrgFolder" />
  <html:hidden name="fil100Form" property="fil100SvChkTrgFile" />
  <html:hidden name="fil100Form" property="fil100SvSearchMode" />
  <html:hidden name="fil100Form" property="fil100SvChkWdTrgName" />
  <html:hidden name="fil100Form" property="fil100SvChkWdTrgBiko" />
  <html:hidden name="fil100Form" property="fil100SvChkWdTrgText" />
  <html:hidden name="fil100Form" property="fil100SvChkWdKeyWord" />
  <html:hidden name="fil100Form" property="fileSvSearchfromYear" />
  <html:hidden name="fil100Form" property="fileSvSearchfromMonth" />
  <html:hidden name="fil100Form" property="fileSvSearchfromDay" />
  <html:hidden name="fil100Form" property="fileSvSearchtoYear" />
  <html:hidden name="fil100Form" property="fileSvSearchtoMonth" />
  <html:hidden name="fil100Form" property="fileSvSearchtoDay" />
  <html:hidden name="fil100Form" property="fil100SvChkOnOff" />
  <html:hidden name="fil100Form" property="fil100SvSltCabinetKbn" />
  <html:hidden name="fil100Form" property="fil100SvChkTrgDeleted" />
  <html:hidden name="fil100Form" property="fil100SvChkTrgDeletedFolder" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeTarget" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeMoneyNoset" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeMoneyKbn" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeMoney" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeMoneyTo" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeMoneyType" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeMoneyJudge" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeDateFrom" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeDateTo" />
  <html:hidden name="fil100Form" property="fil100SvSearchTradeDateKbn" />
  <logic:notEqual name="fil100Form" property="fil100SltCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
    <html:hidden name="fil100Form" property="fil100ChkTrgDeleted" />
    <html:hidden name="fil100Form" property="fil100ChkTrgDeletedFolder" />
    <html:hidden name="fil100Form" property="fil100SearchTradeTarget" />
    <html:hidden name="fil100Form" property="fil100SearchTradeMoneyNoset" />
    <html:hidden name="fil100Form" property="fil100SearchTradeMoneyKbn" />
    <html:hidden name="fil100Form" property="fil100SearchTradeMoney" />
    <html:hidden name="fil100Form" property="fil100SearchTradeMoneyType" />
    <html:hidden name="fil100Form" property="fil100SearchTradeMoneyJudge" />
    <html:hidden name="fil100Form" property="fil100SearchTradeDateKbn" />
    <html:hidden name="fil100Form" property="fil100SearchTradeDateFrom" />
    <html:hidden name="fil100Form" property="fil100SearchTradeDateTo" />
  </logic:notEqual>
  <html:hidden name="fil100Form" property="fil100sortKey" />
  <html:hidden name="fil100Form" property="fil100orderKey" />
  <html:hidden name="fil100Form" property="binSid" />
  <html:hidden name="fil100Form" property="fil100ResultCount" />
  <html:hidden name="fil100Form" property="fil100InitFlg" />

  <html:hidden property="backDsp" />
  <html:hidden property="backDspLow" />

  <html:hidden property="fil040SortKey" />
  <html:hidden property="fil040OrderKey" />

  <html:hidden property="fil010SelectDirSid" />
  <html:hidden property="fil010SelectCabinet" />
  <html:hidden property="fil010DspCabinetKbn" />

  <input type="hidden" name="fil050DirSid" value="">
  <input type="hidden" name="fil070DirSid" value="">
  <input type="hidden" name="fil050ParentDirSid" value="">
  <input type="hidden" name="fil070ParentDirSid" value="">
  <input type="hidden" name="fil100WarnOk" value="">


  <div class="pageTitle">
    <ul>
      <li>
        <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
      </li>
      <li>
        <gsmsg:write key="cmn.filekanri" />
      </li>
      <li class="pageTitle_subFont">
        <gsmsg:write key="fil.60" />
      </li>
      <li>
        <div>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('fil100back')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </li>
    </ul>
  </div>
  <div class="wrapper">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <table class="table-left">
      <tr>
        <td colspan="4" class="w100 table_title-color">
          <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="mb5 classic-display">
          <span>
            <gsmsg:write key="cmn.advanced.search" />
          </span>
        </td>
      </tr>

      <tr>
        <th class="w10 no_w">
          <gsmsg:write key="fil.23" />
        </th>
        <td class="w40">
          <span class="verAlignMid mr10 ">
            <html:radio property="fil100SltCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_PRIVATE) %>" styleId="errlKbn_01" styleClass="js_cabinetKbn" />
            <label for="errlKbn_01">
              <gsmsg:write key="fil.142" />
            </label>
          </span>
          <span class="verAlignMid mr10">
            <html:radio property="fil100SltCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_PUBLIC) %>" styleId="errlKbn_02" styleClass="js_cabinetKbn" />
            <label for="errlKbn_02">
              <gsmsg:write key="fil.141" />
            </label>
          </span>
          <span class="verAlignMid">
            <html:radio styleId="errlKbn_03" property="fil100SltCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>" styleClass="js_cabinetKbn" />
            <label for="errlKbn_03">
              <gsmsg:write key="fil.147" />
            </label>
          </span>
          <br>
          <logic:notEmpty name="fil100Form" property="cabinetLabel">
            <% String selectCabinetSid = ""; %>
            <logic:notEmpty name="fil100Form" property="fil100SltCabinetSid">
              <bean:define id="paramCabinetSid" name="fil100Form" property="fil100SltCabinetSid" type="java.lang.String" />
              <% selectCabinetSid = paramCabinetSid; %>
            </logic:notEmpty>

            <html:select styleId="selectCabinetCombo" property="fil100SltCabinetSid" styleClass="wp300" onchange="fil100SelectCabinet();">
              <html:optionsCollection name="fil100Form" property="cabinetLabel" value="value" label="label" />

              <logic:notEmpty name="fil100Form" property="delCabinetLabel">
                <optgroup label="<gsmsg:write key="fil.189" />">
                <logic:iterate id="delCabinetMdl" name="fil100Form" property="delCabinetLabel">
                  <% String delSelected = ""; %>
                  <logic:equal name="delCabinetMdl" property="value" value="<%= selectCabinetSid %>">
                    <% delSelected = " selected"; %>
                  </logic:equal>
                  <option value="<bean:write name="delCabinetMdl" property="value" />" data-delkbn="true"<%= delSelected %>><bean:write name="delCabinetMdl" property="label" /></option>
                </logic:iterate>
                </optgroup>
              </logic:notEmpty>
            </html:select>
          </logic:notEmpty>
        </td>

        <th class="w10 no_w">
          <gsmsg:write key="cmn.target" />
        </th>
        <td class="w40">
          <span class="verAlignMid">
            <html:checkbox styleId="search_scope_01" name="fil100Form" property="fil100ChkTrgFolder" value="<%=targetFolder%>" />
            <label for="search_scope_01" class="mr10">
              <gsmsg:write key="cmn.folder" />
            </label>
          </span>
          <span class="verAlignMid">
            <html:checkbox styleId="search_scope_02" name="fil100Form" property="fil100ChkTrgFile" value="<%=targetFile%>" />
            <label for="search_scope_02" class="mr10">
              <gsmsg:write key="cmn.file" />
            </label>
          </span>

          <logic:equal name="fil100Form" property="fil100SltCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
          <logic:equal name="fil100Form" property="fil100adminUser" value="true">
            <span class="verAlignMid js_delTarget">
              <html:checkbox styleId="search_scope_06" name="fil100Form" property="fil100ChkTrgDeletedFolder" value="<%=targetDeleted%>" />
              <label for="search_scope_06" class="mr10">
                <gsmsg:write key="cmn.deleted.folder" />
              </label>
            </span>
            <span class="verAlignMid js_delTarget">
              <html:checkbox styleId="search_scope_05" name="fil100Form" property="fil100ChkTrgDeleted" value="<%=targetDeleted%>" />
              <label for="search_scope_05">
                <gsmsg:write key="cmn.deleted.file" />
              </label>
            </span>
            </logic:equal>
            </logic:equal>
          </span>
        </td>
      </tr>

      <tr>
        <th>
          <gsmsg:write key="cmn.keyword" />
        </th>
        <td>
          <html:text name="fil100Form" property="filSearchWd" styleClass="text_base wp300" maxlength="50" />
          <br>
          <span class="verAlignMid">
            <html:radio property="fil100SearchMode" value="<%=keyWordAnd%>" styleId="keyKbn_01" />
            <label for="keyKbn_01">
              <gsmsg:write key="cmn.contains.all" />(AND)
            </label>
            <html:radio styleClass="ml10" property="fil100SearchMode" value="<%=keyWordOr%>" styleId="keyKbn_02" />
            <label for="keyKbn_02">
              <gsmsg:write key="cmn.containing.either" />(OR)
            </label>
          </span>
        </td>
        <th class="w10 no_w">
          <gsmsg:write key="cmn.search2" />
        </th>
        <td class="w40">
          <span class="verAlignMid">
            <html:checkbox styleId="search_scope_03" name="fil100Form" property="fil100ChkWdTrgName" value="<%=targetName%>" />
            <label for="search_scope_03">
              <gsmsg:write key="fil.fil100.1" />
            </label>

            <logic:equal name="fil100Form" property="fileSearchFlg" value="1">
              <html:checkbox styleClass="ml10" styleId="search_scope_05" name="fil100Form" property="fil100ChkWdTrgText" value="<%=targetText%>" />
              <label for="search_scope_05">
                <gsmsg:write key="fil.fil100.2" />
              </label>
            </logic:equal>

            <html:checkbox styleClass="ml10" styleId="search_scope_04" name="fil100Form" property="fil100ChkWdTrgBiko" value="<%=targetBiko%>" />
            <label for="search_scope_04">
              <gsmsg:write key="cmn.memo" />
            </label>
          </span>
        </td>
      </tr>
      <logic:equal name="fil100Form" property="fil100SltCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
      <tr>
        <!-- 取引先 -->
        <th>
          <gsmsg:write key="fil.fil030.18" />
        </th>
        <td>
          <html:text name="fil100Form" property="fil100SearchTradeTarget" styleClass="text_base wp300" maxlength="50" />
        </td>
        <!-- 取引金額 -->
        <th class="w10 no_w">
          <gsmsg:write key="fil.fil080.5" />
        </th>
        <td class="w40">
          <span class="verAlignMid">
            <html:checkbox name="fil100Form" styleId="fileSearchMoneyNoset" property="fil100SearchTradeMoneyNoset" value="<%= String.valueOf(GSConstFile.SEARCH_NON) %>" onclick="tradeMoneyNoSet();" />
            <label for="fileSearchMoneyNoset" class="mr20">
              <gsmsg:write key="cmn.without.specifying" />
            </label>

            <html:radio styleId="search_tradeMoney0" name="fil100Form" property="fil100SearchTradeMoneyKbn" value="<%= String.valueOf(Fil100Form.TRADEMONEYKBN_WITHOUT) %>" onclick="tradeMoneyNoKbn();" />
            <label for="search_tradeMoney0" class="mr10">
              <gsmsg:write key="fil.157" />
            </label>
            <html:radio styleId="search_tradeMoney1" name="fil100Form" property="fil100SearchTradeMoneyKbn" value="<%= String.valueOf(Fil100Form.TRADEMONEYKBN_SET) %>" onclick="tradeMoneyNoKbn();" />
            <label for="search_tradeMoney1">
              <gsmsg:write key="fil.fil100.3" />
            </label>
          </span>
          <span id="search_tradeMoney_input">
          <br>
          <div class="verAlignMid">
            <bean:define id="tradeMoney" value="" type="String"/>
            <logic:notEmpty name="fil100Form" property="fil100SearchTradeMoney">
              <bean:define id="tradeMoney" name="fil100Form" property="fil100SearchTradeMoney" type="String"/>
            </logic:notEmpty>
            <currency-input name="fil100SearchTradeMoney" value="<%=tradeMoney %>" class="text_base wp180" >
            </currency-input>
            <logic:notEmpty name="fil100Form" property="moneyTypeLabel">
              <html:select property="fil100SearchTradeMoneyType" styleClass="ml5">
                <html:optionsCollection name="fil100Form" property="moneyTypeLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="fil100Form" property="moneyJudgeLabel">
              <html:select property="fil100SearchTradeMoneyJudge" styleClass="ml5 js_moneyJudge">
                <html:optionsCollection name="fil100Form" property="moneyJudgeLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>

            <bean:define id="tradeMoney" value="" type="String"/>
            <logic:notEmpty name="fil100Form" property="fil100SearchTradeMoneyTo">
              <bean:define id="tradeMoney" name="fil100Form" property="fil100SearchTradeMoneyTo" type="String"/>
            </logic:notEmpty>
            <currency-input name="fil100SearchTradeMoneyTo" value="<%=tradeMoney %>" class="text_base wp180 ml5 display_n" >
            </currency-input>
          </div>
          </span>
        </td>
      <!-- 取引年月日 -->
      <tr>
        <th class="txt_l">
          <span>
            <gsmsg:write key="fil.fil080.8" />
          </span>
        </th>
        <td colspan="3">
          <span class="verAlignMid">
            <html:checkbox styleId="search_tradeDate" name="fil100Form" property="fil100SearchTradeDateKbn" value="<%= String.valueOf(GSConstFile.SEARCH_NON) %>" onclick="tradeDateNoKbn();" />
            <label for="search_tradeDate">
              <gsmsg:write key="cmn.without.specifying" />
            </label>
            <html:text name="fil100Form" property="fil100SearchTradeDateFrom" maxlength="10" styleClass="txt_c wp95 ml10 datepicker js_frDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <span class="ml5">～</span>
            <html:text name="fil100Form" property="fil100SearchTradeDateTo" maxlength="10" styleClass="txt_c wp95 ml5 datepicker js_toDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          </span>
        </td>
      </tr>
      </logic:equal>

      <tr>
        <th class="txt_l">
          <span>
            <gsmsg:write key="cmn.update.day.hour" />
          </span>
        </th>
        <td colspan="3">
          <span class="verAlignMid">
            <html:checkbox name="fil100Form" styleId="fileSearchdateNoKbn" property="fil100ChkOnOff" value="1" onclick="filedateNoKbn();" />
            <label for="fileSearchdateNoKbn">
              <gsmsg:write key="cmn.without.specifying" />
            </label>

            <html:text name="fil100Form" property="fileSearchfromDate" maxlength="10" styleClass="txt_c wp95 ml10 datepicker js_frDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <span class="ml5">～</span>
            <html:text name="fil100Form" property="fileSearchtoDate" maxlength="10" styleClass="txt_c wp95 ml5 datepicker js_toDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          </span>
        </td>
      </tr>

    </table>

    <div class="txt_c">
      <button type="submit" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onclick="buttonPush('<%=searchClick%>');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </div>

    <logic:equal name="fil100Form" property="fil100pageDspFlg" value="true">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('fil100PagePreview');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="fil100pageNum1" onchange="changePage(1);" styleClass="paging_combo">
          <html:optionsCollection name="fil100Form" property="pageList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('fil100PageNext');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:equal>

    <logic:notEmpty name="fil100Form" property="resultList">
      <!-- 一覧 -->
      <bean:define id="sortKey" name="fil100Form" property="fil100sortKey" />
      <bean:define id="orderKey" name="fil100Form" property="fil100orderKey" />
      <bean:define id="cabinetKbn" name="fil100Form" property="fil100SltCabinetKbn" />

      <gsmsg:define id="namae" msgkey="cmn.name4" />
      <gsmsg:define id="tradeDate" msgkey="fil.fil080.8" />
      <gsmsg:define id="tradeTarget" msgkey="fil.fil030.18" />
      <gsmsg:define id="tradeMoney" msgkey="fil.fil080.5" />
      <gsmsg:define id="saizu" msgkey="cmn.size" />
      <gsmsg:define id="tuuti" msgkey="fil.1" />
      <gsmsg:define id="nitiji" msgkey="cmn.update.day.hour" />
      <gsmsg:define id="kousinsya" msgkey="cmn.update.user" />


      <%
        int iSortKey = ((Integer) sortKey).intValue();
        int iOrderKey = ((Integer) orderKey).intValue();
        int[] sortKeyList = new int[] { GSConstFile.SORT_NAME,
                  GSConstFile.SORT_SIZE,
                  GSConstFile.SORT_CALL,
                  GSConstFile.SORT_EDATE,
                  GSConstFile.SORT_EUSR};
        String[] title_width = new String[] { "40", "5", "5", "10", "15" };
        String[] titleList = new String[] { namae, saizu, tuuti, nitiji, kousinsya };
        String syousai_width = "2";

        if (String.valueOf(cabinetKbn).equals(String.valueOf(GSConstFile.CABINET_KBN_ERRL))) {
          titleList = new String[] { namae, tradeDate, tradeTarget, tradeMoney, saizu, tuuti, nitiji, kousinsya };
          title_width = new String[] { "20", "5", "15", "10", "5", "5", "10", "15" };
          sortKeyList = new int[] { GSConstFile.SORT_NAME,
                    GSConstFile.SORT_SIZE,
                    GSConstFile.SORT_CALL,
                    GSConstFile.SORT_EDATE,
                    GSConstFile.SORT_EUSR,
                  GSConstFile.SORT_TRADE_DATE,
                    GSConstFile.SORT_TRADE_TARGET,
                    GSConstFile.SORT_TRADE_MONEY};
          syousai_width = "6";
        }
      %>

      <table class="table-top" cellpadding="0" cellspacing="0">
        <tr>
          <%
            int order_asc = jp.groupsession.v2.fil.GSConstFile.ORDER_KEY_ASC;
            int order_desc = jp.groupsession.v2.fil.GSConstFile.ORDER_KEY_DESC;
            for (int i = 0; i < sortKeyList.length; i++) {
                String title = titleList[i];
                int skey =  GSConstFile.SORT_NAME ;
                if (title.equals(namae)) {
                  skey =  GSConstFile.SORT_NAME ;
                } else if (title.equals(saizu)) {
                  skey =  GSConstFile.SORT_SIZE ;
                } else if (title.equals(tuuti)) {
                  skey =  GSConstFile.SORT_CALL ;
                } else if (title.equals(nitiji)) {
                  skey =  GSConstFile.SORT_EDATE ;
                } else if (title.equals(kousinsya)) {
                  skey =  GSConstFile.SORT_EUSR ;
                } else if (title.equals(tradeDate)) {
                  skey =  GSConstFile.SORT_TRADE_DATE ;
                } else if (title.equals(tradeTarget)) {
                  skey =  GSConstFile.SORT_TRADE_TARGET ;
                } else if (title.equals(tradeMoney)) {
                  skey =  GSConstFile.SORT_TRADE_MONEY ;
                }

                String order = String.valueOf(order_asc);
                if (iSortKey == skey) {
                    if (iOrderKey == order_desc) {
                        title = title + "<span class=\"classic-display\">▼</span><i class=\"original-display icon-sort_down\"></i>";
                    } else {
                        title = title + "<span class=\"classic-display\">▲</span><i class=\"original-display icon-sort_up\"></i>";
                        order = String.valueOf(order_desc);
                    }
                }
          %>
          <th class="w<%=title_width[i]%> no_w cursor_p">
            <a href="#" onClick="return sort(<%=skey%>, <%=order%>);">
              <span><%=title%></span>
            </a>
          </th>
          <%
            }
          %>

          <th class="w<%= syousai_width %>">
            <span></span>
          </th>
        </tr>

        <logic:iterate id="detailMdl" name="fil100Form" property="resultList" indexId="idx" type="FileDspModel">

          <% String listType="file"; %>
          <bean:define id="fileName" name="detailMdl" property="fdrName" />
          <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
          <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFolder%>">
            <% listType="folder"; %>
          </logic:equal>

          <% String deleteResultClass = ""; boolean delListFlg = false; String delKbn = "0"; %>
          <logic:equal name="detailMdl" property="fdrJtkbn" value="<%= String.valueOf(GSConstFile.JTKBN_DELETE) %>">
            <% delListFlg = true; delKbn = "1"; %>
          </logic:equal>
          <logic:equal name="detailMdl" property="fcbJkbn" value="<%= String.valueOf(GSConstFile.JTKBN_DELETE) %>">
            <% delListFlg = true; delKbn = "1"; %>
          </logic:equal>
          <%
            if (delListFlg) {
              deleteResultClass = "deletedFile";
            }
          %>

          <tr class="js_listHover" data-fdrsid="<bean:write name="detailMdl" property="fdrSid" />" data-fcbSid="<bean:write name="detailMdl" property="fcbSid" />" data-parentsid="<bean:write name="detailMdl" property="fdrParentSid" />" data-binsid="<bean:write name="detailMdl" property="binSid" />" data-listtype="<%= listType %>" data-extension="<%= extension %>" data-delkbn="<%= delKbn %>">

            <!-- ディレクトリ名称 -->
            <td class="txt_l js_listClick cursor_p word_b-all">
              <div class="verAlignMid">
                <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFolder%>">
                  <div class="wp25">
                    <img class="classic-display" src="../common/images/classic/icon_folder_box.png" alt="">
                    <img class="original-display" src="../common/images/original/icon_folder_box.png" alt="">
                  </div>
                  <a href="#" class="cl_linkDeff <%= deleteResultClass %> js_resultName" onclick="MoveToFolderDetail('<bean:write name="detailMdl" property="fdrSid" />','<bean:write name="detailMdl" property="fcbSid" />','<bean:write name="detailMdl" property="fdrParentSid" />');">
                    <bean:write name="detailMdl" property="fdrName" />
                    <span class="tooltips display_none"><bean:write name="detailMdl" property="parentDirPath" /><bean:write name="detailMdl" property="fdrName" /></span>
                  </a>
                </logic:equal>
                <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFile%>">
                  <div class="wp25">
                    <img class="classic-display" src="../file/images/classic/icon_file.gif" alt="">
                    <img class="original-display" src="../common/images/original/icon_siryo.png" alt="">
                  </div>

                  <a href="#" class="cl_linkDef <%= deleteResultClass %> js_resultName" onclick="downLoad('fileNameClick', '<bean:write name="detailMdl" property="binSid" />');">
                    <bean:write name="detailMdl" property="fdrName" />
                    <span class="tooltips display_none"><bean:write name="detailMdl" property="parentDirPath" /><bean:write name="detailMdl" property="fdrName" /></span>
                  </a>
                  <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                    <span class="ml5">
                      <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                      <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                    </span>
                  <% } %>
                </logic:equal>
              </div>
            </td>

            <logic:equal name="cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
              <!-- 取引年月日 -->
              <td class="txt_c js_listClick cursor_p">
                <span>
                  <bean:write name="detailMdl" property="fdrTradeDate" />
                </span>
              </td>
              <!-- 取引先 -->
              <td class="txt_l js_listClick cursor_p word_b-all">
                <span>
                  <bean:write name="detailMdl" property="fdrTradeTarget" />
                </span>
              </td>
              <!-- 取引金額 -->
              <% String moneyClass = "txt_r"; %>
              <logic:equal name="detailMdl" property="fdrTradeMoney" value="-">
                <% moneyClass = "txt_c"; %>
              </logic:equal>
              <td class="<%= moneyClass %> js_listClick cursor_p no_w">
                <span>
                  <bean:write name="detailMdl" property="fdrTradeMoney" />
                  <bean:write name="detailMdl" property="fmmName" />
                </span>
              </td>
            </logic:equal>

            <!-- サイズ -->
            <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFolder%>">
              <td class="txt_r js_listClick cursor_p"></td>
            </logic:equal>
            <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFile%>">
              <td class="txt_r js_listClick cursor_p no_w">
                <span>
                  <bean:write name="detailMdl" property="fflFileSize" />
                </span>
              </td>
            </logic:equal>

            <!-- 更新通知 -->
            <logic:equal name="detailMdl" property="callOn" value="-1">
              <td class="txt_c js_listClick cursor_p"></td>
            </logic:equal>

            <logic:equal name="detailMdl" property="callOn" value="<%=callOn%>">
              <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFolder%>">
                <td class="txt_c js_listClick cursor_p">
                  <img class="classic-display" src="../file/images/classic/icon_call.gif">
                  <img class="original-display" src="../common/images/original/icon_bell.png">
                </td>
              </logic:equal>
            </logic:equal>

            <!-- 更新日時 -->
            <td class="txt_c js_listClick cursor_p">
              <span>
                <bean:write name="detailMdl" property="fdrEdate" />
              </span>
            </td>

            <!-- 更新者 -->
            <td class="txt_l js_listClick cursor_p">
              <logic:equal name="detailMdl" property="usrJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                <s><span>
                    <bean:write name="detailMdl" property="usrSei" />
                    &nbsp;
                    <bean:write name="detailMdl" property="usrMei" />
                  </span></s>
              </logic:equal>
              <logic:notEqual name="detailMdl" property="usrJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                <span class="<%=UserUtil.getCSSClassNameNormal(detailMdl.getUsrUkoFlg())%>">
                  <bean:write name="detailMdl" property="usrSei" />
                  &nbsp;
                  <bean:write name="detailMdl" property="usrMei" />
                </span>
              </logic:notEqual>
            </td>

            <!-- 詳細ボタン-->
            <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFolder%>">
              <td class="txt_c">
                <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.detail" />" onclick="MoveToFolderDetail('<bean:write name="detailMdl" property="fdrSid" />','<bean:write name="detailMdl" property="fcbSid" />','<bean:write name="detailMdl" property="fdrParentSid" />',<%= delKbn %>);">
                  <gsmsg:write key="cmn.detail" />
                </button>
              </td>
            </logic:equal>
            <logic:equal name="detailMdl" property="fdrKbn" value="<%=dirFile%>">
              <td class="txt_c">
                <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.detail" />" onclick="MoveToFileDetail('<bean:write name="detailMdl" property="fdrSid" />','<bean:write name="detailMdl" property="fcbSid" />','<bean:write name="detailMdl" property="fdrParentSid" />',<%= delKbn %>);">
                  <gsmsg:write key="cmn.detail" />
                </button>
              </td>
            </logic:equal>
          </tr>
        </logic:iterate>
      </table>
    </logic:notEmpty>


    <logic:notEqual name="fil100Form" property="fil100searchUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_NOT_USE)%>">
      <logic:notEmpty name="fil100Form" property="filSearchWd">
        <table class="w100" cellpadding="5" cellspacing="0">
          <tr>
            <td class="txt_l">
              <bean:define id="searchKeyword" name="fil100Form" property="fil100HtmlSearchWord" type="java.lang.String" />
              <a href="#!" onClick="webSearch('<bean:write name="fil100Form" property="fil100WebSearchWord" />');">
                <span id="webSearchArea">
                  <gsmsg:write key="cmn.websearch" arg0="<%=searchKeyword%>" />
                </span>
              </a>
            </td>
          </tr>
        </table>
      </logic:notEmpty>
    </logic:notEqual>

    <logic:equal name="fil100Form" property="fil100pageDspFlg" value="true">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('fil100PagePreview');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="fil100pageNum2" onchange="changePage(2);" styleClass="paging_combo">
          <html:optionsCollection name="fil100Form" property="pageList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('fil100PageNext');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:equal>

  </div>

  <div id="delMailMsgPop" title="" style="display: none">

    <table width="100%" height="100%">
      <tr>
        <td width="15%">
          <span class="ui-icon ui-icon-info"></span>
        </td>
        <td width="85%" valign="middle">
          <div style="vertical-align: middle; overflow: auto; width: 500px; height: 200px;">
            <table>
              <tr>
                <td id="delMailMsgArea" style="vertical-align: middle; width: 500px; height: 200px; font-weight: bold; font-size: 14px;"></td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
    </table>

  </div>

  <span id="tooltip_area"></span>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>