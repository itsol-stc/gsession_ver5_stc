<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.model.FileDspModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/common/" prefix="common" %>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>

<!DOCTYPE html>
<html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.fil010.8" /></title></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil300.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/components/currency-input/index.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../file/css/file.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onunload="closePreviewWindow();">
  <bean:define id="sepKey" name="fil300Form" property="sepKey" type="String"/>

  <html:form action="/file/fil300" styleId="js_filForm">
  <div class="pos_rel">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
    
    <% String pgid = String.valueOf(0); %>
    <logic:equal name="fil300Form" property="fil300insertMode" value="<%= String.valueOf(GSConstFile.MODE_MULTI) %>">
        <% pgid = String.valueOf(1); %>
    </logic:equal>
    <input type="hidden" name="helpPrm" value="<%= pgid %>">
    
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="escapeCmd" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="fil300InitFlg" />
    <html:hidden property="fil300BeforeDspFlg" />
    <html:hidden property="sepKey" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil300SelectDir" />
    <input type="hidden" name="fil300SelectFile" value="" />
    <html:hidden property="fil010DspCabinetKbn" />
    <html:hidden name="fil300Form" property="fil300insertMode" />
    <bean:define id="cabDirId" value=""/>
    <bean:define id="cabDirName" value=""/>
    <bean:define id="cabFilName" value=""/>
    <input type="hidden" name="fil300MoveToDir" value="" />

    <logic:equal name="fil300Form" property="fil300insertMode" value="<%= String.valueOf(GSConstFile.MODE_MULTI) %>">
      <html:hidden property="fil300SelectCabinet" />
    </logic:equal>

    <logic:notEmpty name="fil300Form" property="fil300BeforeInsertFile">
      <logic:iterate id="sid" name="fil300Form" property="fil300BeforeInsertFile">
        <input type="hidden" name="fil300BeforeInsertFile" value="<bean:write name="sid" />">
      </logic:iterate>
    </logic:notEmpty>

    <span class="js_treeSavePath">
    </span>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li><gsmsg:write key="cmn.filekanri" /></li>
        <li class="pageTitle_subFont"><gsmsg:write key="fil.fil010.8" /></li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil300back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div id="upperArea" class="w100">
    <bean:define id="modeKbn" name="fil300Form" property="fil300insertMode" />
    <% String singleClass = "tabHeader_tab-off js_tabHeader";
       String multipleClass = "tabHeader_tab-on";
       if (String.valueOf(modeKbn).equals(String.valueOf(GSConstFile.MODE_SINGLE))) {
         singleClass="tabHeader_tab-on";
         multipleClass = "tabHeader_tab-off js_tabHeader";
       }
    %>
    <ul id="normal_tab" class="tabHeader mb10 w100">
      <li id="single" class="pr10 pl10 bor_b1 <%= singleClass %>">
        <gsmsg:write key="fil.fil300.2" />
      </li>
      <li id="multiple" class="pr10 pl10 bor_b1 <%= multipleClass %>">
        <gsmsg:write key="fil.fil300.3" />
      </li>

      <li id="purge" class="js_tabHeader pr10 pl10 bor_b1 tabHeader_tab-off js_tabHeader">
        <gsmsg:write key="man.purge" />
      </li>
    </ul>
    <div class="js_errorArea">
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
    </div>

  <logic:equal name="modeKbn" value="<%= String.valueOf(GSConstFile.MODE_SINGLE) %>">
    <logic:equal name="fil300Form" property="fil300BeforeDspFlg" value="<%= String.valueOf(GSConstFile.BEFORE_DSP_FIL080) %>">
      <gsmsg:write key="fil.182" /><br>
      <gsmsg:write key="fil.183" />
    </logic:equal>

    </div>
    <div class="wrapper_2column">
    <div class="wp350 js_yuko">
    <table class="table-top js_dirTable">
      <tbody>
      <tr>
        <th class="w100 txt_l">
          <gsmsg:write key="cmn.select.file" />
        </th>
      </tr>
      <tr>
        <td>
          <div id="sidetreecontrol">
            <a href="#!">
              <gsmsg:write key="cmn.all.close" />
            </a>
            |
            <a href="#!">
              <gsmsg:write key="cmn.all.open" />
            </a>
          </div>
          <div>
            <div class="p5">
              <html:select name="fil300Form" property="fil300SelectCabinet" onchange="buttonPush('changeCabinet');" styleClass="js_selectCabinetCombo wp250">
                <html:optionsCollection name="fil300Form" property="fil300CabinetList" value="value" label="label" />
              </html:select>
            </div>
          </div>
          <div  class="hp250 ofx_a ofy_a no_w pl5 wp340 js_treeSideList">
            <ul id="tree" class="w100 test">
            </ul>
            <div class="js_tree_loader">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
              <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
              <gsmsg:write key="cmn.loading"/>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    </div>

    <div class="js_inputArea-noSelect file_tradeMain ml5 txt_c txt_m mt50">
      <span class="fs_20 fw_b cl_fontWeek"><gsmsg:write key="fil.fil300.9" /></span>
    </div>

    <div class="js_inputArea file_tradeMain ml5 display_none">
      <span class="cl_fontWarn js_errors">
      </span>

      <div class="mb0 mt10 table_title-color p5 hp40 js_inputArea bor_l1 bor_r1 bor_t1 display_none">
        <span class="side_headerTitle fs_14 fw_bold pl0">
          <gsmsg:write key="fil.fil300.4" />
        </span>
      </div>
      <table class="table-left mt0 w100">
        <tbody>
        <tr>
          <th class="w20 no_w">
            <gsmsg:write key="cmn.file.name" /><span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments"/></span>
          </th>
          <td class="w80">
            <input type="text" name="fil300FileName" value="" class="wp400" maxlength="250"><span class="ml5 js_fileExt"></span>
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <gsmsg:write key="fil.fil080.8" /><span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments"/></span>
          </th>
          <td class="w80">
            <span class="pos_rel display_flex mr5">
              <html:text name="fil300Form" property="fil300TradeDate" maxlength="10" styleClass="wp90 easyRegister-text datepicker js_frDatePicker" styleId="easyFrDate"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart" id="iconKikanStart"></span>
            </span>
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <gsmsg:write key="fil.fil030.18" /><span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments"/></span>
          </th>
          <td class="w80">
            <input type="text" name="fil300TradeTarget" value="" class="wp400 js_tradeTarget" maxlength="50">
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <gsmsg:write key="fil.fil080.5" /><span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments"/></span>
          </th>
          <td class="w80">
            <span class="verAlignMid">
              <label class="verAlignMid">
                <input type="checkbox" name="fil300TradeMoneyKbn" value="<%= String.valueOf(GSConstFile.MONEY_KBN_OFF) %>" onclick="tradeMoneyNoKbn();">
                <gsmsg:write key="fil.157" />
              </label>
              <currency-input name="fil300TradeMoney" class="ml10 wp180"></currency-input>
              <logic:notEmpty name="fil300Form" property="fil300GaikaList">
                <html:select name="fil300Form" property="fil300TradeMoneyGaika" styleClass="ml5">
                  <html:optionsCollection name="fil300Form" property="fil300GaikaList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </span>
          </td>
        </tr>
        <tr class="js_savePath display_none">
          <th class="w20 no_w">
            <gsmsg:write key="fil.186" />
          </th>
          <td class="w80">
            <span class="js_savePath-str"></span>
          </td>
        </tr>
        <tr class="js_savePath-tree display_none">
          <th class="w20 no_w">
            <gsmsg:write key="fil.186" /><span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments"/></span>
          </th>
          <td class="w80">
            <div class="mw95 bor1 mrl_auto mt5">
              <div id="sidetreecontrol2">
                <a href="#!">
                  <gsmsg:write key="cmn.all.close" />
                </a>
                |
                <a href="#!">
                  <gsmsg:write key="cmn.all.open" />
                </a>
              </div>
              <div  class="hp250 ofx_a ofy_a no_w pl5 js_treeSaveSelectList">
                <ul id="tree2" class="w100">
                </ul>
                <div class="js_tree_loader">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                  <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
                  <gsmsg:write key="cmn.loading"/>
                </div>
              </div>
            </div>
            <div class="w95 mrl_auto mt5">
              <gsmsg:write key="fil.186" />：<span class="js_cabName"></span>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="txt_c">
        <button type="button" class="baseBtn js_addBtn" value="<gsmsg:write key="cmn.entry" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <gsmsg:write key="cmn.entry" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="fil300deletePop();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.cancel" />" onclick="fil300HideInputArea();">
          <gsmsg:write key="cmn.cancel" />
        </button>
      </div>
    </div>

    </div>
  </logic:equal>

  <logic:equal name="modeKbn" value="<%= String.valueOf(GSConstFile.MODE_MULTI) %>">
    <logic:equal name="fil300Form" property="fil300BeforeDspFlg" value="<%= String.valueOf(GSConstFile.BEFORE_DSP_FIL080) %>">
      <gsmsg:write key="fil.182" /><br>
      <gsmsg:write key="fil.183" />
    </logic:equal>
    </div>

    <div class="wrapper_2column">
    <div class="wp350">
    <table class="table-top js_dirTable">
      <tbody>
      <tr>
        <th class="w100 txt_l">
          <gsmsg:write key="fil.fil300.5" />
        </th>
      </tr>
      <tr>
        <td>
          <div id="sidetreecontrol">
            <a href="#!">
              <gsmsg:write key="cmn.all.close" />
            </a>
            |
            <a href="#!">
              <gsmsg:write key="cmn.all.open" />
            </a>
          </div>
          <div  class="hp250 ofx_a ofy_a no_w pl5 wp340 js_treeSideList">
            <ul id="tree" class="w100">
            </ul>
            <div class="js_tree_loader">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
              <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
              <gsmsg:write key="cmn.loading"/>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    </div>

    <div class="js_importArea-noSelect file_tradeMain ml5 txt_c txt_m mt50">
      <span class="fs_20 fw_b cl_fontWeek"><gsmsg:write key="fil.fil300.5" /></span>
    </div>
    <div class="js_importArea-select file_tradeMain display_none">
    <span class="cl_fontWarn js_errors fw_bold ml5">
    </span>
    <div class="js_importArea w100 pl5">
    <table class="table-left w100 table-fixed">
      <tbody>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="fil.158" />
            <br>
            <gsmsg:write key="cmn.download" />
          </th>
          <td class="w75">
            <gsmsg:write key="fil.159" /><br>
            <span class="js_download-normal">
              <a href="#!" class="ml20 js_downloadBtn" onclick="buttonPush('fil300download')">
                <gsmsg:write key="cmn.download" />
              </a>
            </span>
            <span class="js_download-nosave display_none">
              <a href="#!" class="ml20 js_downloadBtn" onclick="buttonPush('fil300downloadNoSavePath')">
                <gsmsg:write key="cmn.download" />
              </a>
            </span>
          </td>
        </tr>
        <!-- 取引情報入力欄 -->
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="fil.160" />
          </th>
          <td class="w75">
            <gsmsg:write key="fil.161" /><br>
            <a href="#!" class="ml20 js_help">
              <gsmsg:write key="cmn.help" />:<gsmsg:write key="fil.163" />
            </a>
            <!-- 取引情報入力 ファイル一覧 -->
            <div class="bor1 w100 mt10 border_bottom_none bgC_header2">
              <div class="verAlignMid w100 p3">
                <div class="wp70"><gsmsg:write key="fil.fil300.10" /></div>
                <div><gsmsg:write key="fil.fil300.4" /></div>
              </div>
            </div>
            <div class="bor1 w100 hp250 ofx_a ofy_a">

            <div class="js_multiFileListArea">
            </div>

            </div>
            <div class="txt_r w100 js_kakuninPaging">
              <span class="verAlignMid">
              <button type="button" class="webIconBtn" onClick="fil300PrevPage();">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <select class="js_kakuninPage" name="fil300SelectPage" onchange="fil300FileTreeClick();">
                <opition></opition>
              </select>
              <button type="button" class="webIconBtn" onClick="fil300NextPage();">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
              </span>
            </div>

            <div class="mrl_auto w95 mb10">
              ※<gsmsg:write key="fil.162" />
            </div>
          </td>
        </tr>

        <!-- 取込み用ファイル添付欄 -->
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="fil.158" />
            <br>
            <gsmsg:write key="fil.164" />
          </th>
          <td class="w75">
            <gsmsg:write key="fil.165" /><br>
            <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
              pluginId="<%= GSConstFile.PLUGIN_ID_FILE %>"
              tempDirId="fil300"
              preview="false" />
          </td>
        </tr>
      </tbody>
    </table>
    <div class="txt_c mt20">
      <button type="button" class="baseBtn js_impBtn" value="<gsmsg:write key="cmn.import" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <gsmsg:write key="fil.173" />
      </button>
    </div>
    </div>

    </div>
    </div>
  </logic:equal>

  <!-- ローディング -->
  <div class="js_loading bgC_body h100 w100 pos_abs top0 display_n">
  </div>

  <!-- ヘルプダイアログ -->
  <div id="helpPop" class="display_n">
    <table>
      <tr>
        <td class="p10">・</td>
        <td><gsmsg:write key="fil.181" /></td>
      </tr>
      <tr>
        <td class="p10">・</td>
        <td><gsmsg:write key="fil.171" /></td>
      </tr>
      <tr>
        <td class="p10">・</td>
        <td><gsmsg:write key="fil.172" /></td>
      </tr>
    </table>
  </div>
  <!-- 一括登録確認ダイアログ -->
  <div id="impKakuninPop" class="display_n">
    <div class="js_kakuninMessage">
      <gsmsg:write key="fil.177" />
    </div>

    <div class="js_errorNum">

    </div>
    <div class="paging js_importPaging">
      <button type="button" class="webIconBtn" onClick="fil300PrevPageImp();">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <select name="fil300ImpPage" onchange="fil300ImportCheck();" class="paging_combo js_impCombo-opt">
      </select>
      <button type="button" class="webIconBtn" onClick="fil300NextPageImp();">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>

    <table class="table-top js_kakuninTable ofx_a ofy_a">
      <tr>
        <th class="w10"></th>
        <th class="w30"><gsmsg:write key="cmn.file.name"/></th>
        <th class="w20"><gsmsg:write key="fil.fil080.8"/></th>
        <th class="w20"><gsmsg:write key="fil.fil030.18"/></th>
        <th class="w20"><gsmsg:write key="fil.fil080.5"/></th>
      </tr>
      <tr class="js_addTr"></tr>
    </table>
  </div>
  <!-- 削除確認ダイアログ -->
  <div id="deletePop" class="display_n">
    <table class="w100 hp75">
      <tr>
        <td class="w10">
          <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
          <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
        </td>
        <td class="txt_l w90 pl10 fs_14">
          <gsmsg:write key="fil.188" />
          <span class="js_delMessage">
          </span>
        </td>
      </tr>
    </table>
  </div>

  <common:toast toastId="finish">
    <span class="js_message"></span>
  </common:toast>
  </div>
  </html:form>

  <span class="js_treeText">
    <logic:notEmpty name="fil300Form" property="treeFormLv0">
      <logic:iterate id="lv0" name="fil300Form" property="treeFormLv0" type="String">
        <logic:empty name="cabDirId">
         <% String[] sp = lv0.split(sepKey);
         %>
         <bean:define id="cabDirId" value="<%=sp[0] %>"/>
         <bean:define id="cabDirName" value="<%=sp[2] %>"/>
        </logic:empty>
        <span class="display_none" name="treeFormLv0" data-treevalue="<bean:write name="lv0" />"></span>
      </logic:iterate>
    </logic:notEmpty>

    <filekanri:fileTreeParams screenId="fil300" maxLv="11" />
  </span>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html>