<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.mem.GSConstMemo"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/common/" prefix="common" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<!DOCTYPE html>
<html:html>
<head>
  <meta charset="UTF-8">
  <meta  http-equiv="content-language" content="ja">
  <title>GroupSession <gsmsg:write key="memo.01" /></title>
  <link rel="stylesheet" href="../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <link rel=stylesheet href="../common/js/jquery-ui-1.8.16/development-bundle/themes/base/jquery.ui.datepicker.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../memo/css/memo.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <gsjsmsg:js filename="gsjsmsg.js"/>
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/toastDisplay.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../memo/js/mem010.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>
<body class="select_none">
  <html:form action="/memo/mem010.do" styleId="js_memForm">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="mem010DisplayMode" value="<bean:write name="mem010Form" property="mem010DisplayMode" />">
    <input type="hidden" name="mem010SvSearchNaiyo" value="<bean:write name="mem010Form" property="mem010SvSearchNaiyo" />">
    <input type="hidden" name="mem010SvSearchDateFr" value="<bean:write name="mem010Form" property="mem010SvSearchDateFr" />">
    <input type="hidden" name="mem010SvSearchDateTo" value="<bean:write name="mem010Form" property="mem010SvSearchDateTo" />">
    <input type="hidden" name="mem010SvSearchLabel" value="<bean:write name="mem010Form" property="mem010SvSearchLabel" />">
    <input type="hidden" name="mem010SvSearchTenpu" value="<%= GSConstMemo.TENPU_KBN_NONE %>">
    <input type="hidden" name="mem010SvSort" value="<bean:write name="mem010Form" property="mem010SvSort" />">
    <input type="hidden" name="mem010TargetMemoSid" value="<bean:write name="mem010Form" property="mem010TargetMemoSid" />">

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../memo/images/classic/menu_icon_single_32.png" alt="<gsmsg:write key="memo.01" />">
          <img class="header_pluginImg" src="../memo/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="memo.01" />">
        </li>
        <li><gsmsg:write key="memo.01" /></li>
        <li>
          <button type="button" class="baseBtn display_none js_display-compact" onclick="changeDisplay(0);saveDisplay(0);">
            <img class="btn_originalImg-display" src="../memo/images/original/icon_display_change.png" alt="<gsmsg:write key="memo.mem010.30"/>">
            <img class="btn_classicImg-display" src="../memo/images/original/icon_display_change.png" alt="<gsmsg:write key="memo.mem010.30"/>">
            <gsmsg:write key="memo.mem010.30"/>
          </button>
          <button type="button" class="baseBtn js_display-normal" onclick="changeDisplay(1);saveDisplay(1);">
            <img class="btn_originalImg-display" src="../memo/images/original/icon_display_change.png" alt="<gsmsg:write key="memo.mem010.31"/>">
            <img class="btn_classicImg-display" src="../memo/images/original/icon_display_change.png" alt="<gsmsg:write key="memo.mem010.31"/>">
            <gsmsg:write key="memo.mem010.31"/>
          </button>
          <button type="button" class="baseBtn js_closeBtn" onclick="closeThisWindow();">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close"/>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close"/>">
            <gsmsg:write key="cmn.close"/>
          </button>
        </li>
      </ul>
    </div>

    <div id="errorMessageArea"></div>
    <div class="display_none js_searchArea" id="top_detailSearch">
      <table class="table-left mt10 w100 js_searchTerms">
        <tbody>
          <tr class="w100">
            <th class="no_w w10"><gsmsg:write key="cmn.content"/></th>
            <td class="w90" colspan="3"><input type="text" name="mem010SearchNaiyo" class="w98"/></td>
          </tr>
          <tr>
            <th class="no_w w10"><gsmsg:write key="memo.mem010.27"/></th>
            <td class="w45">
              <span class="display_flex">
                <gsmsg:write key="memo.mem010.28"/>
                <html:text name="mem010Form" property="mem010SearchDateFr" maxlength="10" styleClass="txt_c wp85 datepicker js_frDatePicker"/>
                <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <gsmsg:write key="memo.mem010.29"/>
                <html:text name="mem010Form" property="mem010SearchDateTo" maxlength="10" styleClass="txt_c wp85 datepicker js_toDatePicker"/>
                <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </span>
            </td>
            <th class="no_w w10"><gsmsg:write key="cmn.attached"/></th>
            <td class="w35">
              <label class="verAlignMid">
                <input type="radio" name="mem010SearchTenpu" value="<%= GSConstMemo.TENPU_KBN_NONE %>" checked onclick="changeTenpuChecked(0)"><gsmsg:write key="cmn.specified.no" />
              </label>
              <label class="verAlignMid">
                <input type="radio" name="mem010SearchTenpu" value="<%= GSConstMemo.TENPU_KBN_YES %>" onclick="changeTenpuChecked(<%= GSConstMemo.TENPU_KBN_YES %>)"><gsmsg:write key="cmn.exist" />
              </label>
              <label class="verAlignMid">
                <input type="radio" name="mem010SearchTenpu" value="<%= GSConstMemo.TENPU_KBN_NO %>" onclick="changeTenpuChecked(<%= GSConstMemo.TENPU_KBN_NO %>)"><gsmsg:write key="cmn.no3" />
              </label>
            </td>
          </tr>
          <tr>
            <th class="no_w w10"><gsmsg:write key="cmn.label"/></th>
            <td class="w45">
              <select class="w100" name="mem010SearchLabel">
                <option value="-1"></option>
                <logic:notEmpty name="mem010Form" property="labelList">
                  <logic:iterate id="emp" name="mem010Form" property="labelList">
                    <option value="<bean:write name="emp" property="mmlSid" />">
                      <bean:write name="emp" property="mmlName"/>
                    </option>
                  </logic:iterate>
                </logic:notEmpty>
              </select>
            </td>
            <th class="no_w w10"><gsmsg:write key="memo.mem010.43"/></th>
            <td class="w35">
              <div class="verAlignMid">
                <gsmsg:write key="memo.mem010.32"/>
                <label class="verAlignMid ml5">
                  <input type="radio" name="mem010Sort" value="<%= GSConstMemo.ORDER_DESC %>" checked onclick="changeSortChecked(<%= GSConstMemo.INDEX_DESC %>)"><gsmsg:write key="cmn.order.desc"/>
                </label>
                <label class="verAlignMid ml5">
                  <input type="radio" name="mem010Sort" value="<%= GSConstMemo.ORDER_ASC %>" onclick="changeSortChecked(<%= GSConstMemo.INDEX_ASC %>)"><gsmsg:write key="cmn.order.asc"/>
                </label>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="txt_c mb20 js_searchBtn">
        <button type="button" class="baseBtn" id="head_menu_search_btn">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search"/>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search"/>">
          <gsmsg:write key="cmn.search"/>
        </button>
        <button type="button" class="baseBtn" id="searchCancel" onClick="viewSearchDetail();">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.cancel"/>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.cancel"/>">
          <gsmsg:write key="cmn.cancel"/>
        </button>
      </div>
    </div>

    <div class="wrapper_2column">
      <div class="side_multileft-memo js_multileft-memo bgC_none display_none">
        <div class="caption js_caption bor_t1 bor_l1 bor_r1 bor_b1 bgC_header1">
          <ul>
            <li>
              <button type="button" class="baseBtn" onclick="viewSearchDetail();">
                <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search"/>">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search"/>">
                <gsmsg:write key="cmn.advanced.search"/>
              </button>
              <button type="button" class="baseBtn" id="delete_btn">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="memo.mem010.33"/>">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="memo.mem010.33"/>">
                <gsmsg:write key="memo.mem010.33"/>
              </button>
            </li>
          </ul>
        </div>
        <div class="ofy_a hp422 js_memo">
          <table class="table-top m0 js_memolist">
            <tbody>
              <logic:notEmpty name="mem010Form" property="memList">
                <logic:iterate id="emp" name="mem010Form" property="memList">
                  <tr class="js_listHover">
                    <td class="txt_c js_tableCheck cursor_p wp20 h100">
                      <input type="checkbox" class="js_cbx list_checkbox">
                    </td>
                    <td class="cursor_p js_tableCheck js_memo-title pos_rel">
                      <input type="hidden" name="memSid" value="<bean:write name="emp" property="memSid"/>">
                      <div class="display_flex">
                        <div class="cal_content js_cal_content fs_12">
                          <bean:write name="emp" property="mmdContent"/>
                        </div>
                        <div class="cal_time p0 fs_11 mt2 memo_edate js_edate wp65 txt_r hp12">
                          <bean:write name="emp" property="mmdEdate"/>
                        </div>
                      </div>
                      <logic:equal name="emp" property="memBin" value="1">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached"/>">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached"/>">
                      </logic:equal>
                      <logic:notEmpty name="emp" property="mmlName">
                        <span class="baseLabel m0 list_label-padding">
                          <bean:write name="emp" property="mmlName"/>
                        </span>
                      </logic:notEmpty>
                      <div onclick="deleteMemoPop(event, <bean:write name="emp" property="memSid"/>);" class="verAlignMid txt_c mt2 js_memo-delete memo_hover_color delete_button-pos pos_abs cl_linkDef display_none">
                        <img class="original-display" src="../common/images/original/icon_delete.png" alt="trash">
                        <img class="classic-display " src="../common/images/classic/icon_delete.png" alt="trash" >
                      </div>                        
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </tbody>
          </table>
        </div>
      </div>

      <div class="js_block-right w100">
        <div class="txt_r mb10">
          <button type="button" class="baseBtn js_button1" id="memo_insert_btn">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <gsmsg:write key="cmn.entry" />
          </button>

          <button type="button" class="baseBtn display_none js_button2" id="update_btn">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change"/>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="cmn.change"/>">
            <gsmsg:write key="cmn.change"/>
          </button>
          <button type="button" class="baseBtn js_button1" id="clear_btn">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.clear"/>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.clear"/>">
            <gsmsg:write key="cmn.clear"/>
          </button>

          <button type="button" class="baseBtn display_none js_button2" id="clear_btn">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="memo.mem010.34"/>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="memo.mem010.34"/>">
            <gsmsg:write key="memo.mem010.34"/>
          </button>
        </div>

        <textarea class="hp300 w100 memo_textarea js_textarea" placeholder="1行目は件名にも使用されます。"></textarea>
        <div class="bgC_header2 memo_count js_memoCount txt_r bor_l1 bor_r1 bor_b1">
          <gsmsg:write key="cmn.current.characters"/>
          <span class="js_textCount mr5">0</span>/<%= GSConstMemo.MAX_CONTENT_LENGTH %>
          <gsmsg:write key="cmn.character"/>
        </div>
        <table class="table-left memo_option">
          <tr>
            <th class="w30 no_w">
              <gsmsg:write key="cmn.attached" />
              <div class="flo_r">
                <img onclick="attachmentLoadFile();" class="btn_originalImg-display js_attacheBtn" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <img onclick="attachmentLoadFile();" class="btn_classicImg-display js_attacheBtn" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <input type="file" id="attachmentAreaBtn" class="display_none" onchange="attachFileSelect(this, );" multiple="">
              </div>
            </th>
            <td class="w70">
              <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_MEMO) %>"
              pluginId="<%=GSConstMemo.PLUGIN_ID_MEMO %>"
              tempDirId="<%= GSConstMemo.DIRID_MEM010 %>"
              tempBtn="false" />
            </td>
          </tr>

          <tr>
            <th class="w30 no_w">
              <gsmsg:write key="cmn.label" />
              <div class="flo_r">
                <img onclick="labelAddPop();" class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <img onclick="labelAddPop();" class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              </div>
            </th>
            <td class="w70">
              <div class="js_showLabel"></div>
            </td>
          </tr>

          <tr class="w30 no_w">
            <th>
              <gsmsg:write key="cmn.period2" />
            </th>
            <td class="w70">
              <span class="display_flex">
                <html:text name="mem010Form" property="mem010AtdelDate" maxlength="10" styleClass="txt_c wp85 datepicker js_frDatePicker"/>
                <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <gsmsg:write key="memo.mem010.36" />
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="display_none">
      <div id="labelAddPop" title="<gsmsg:write key="memo.mem010.40"/>">
        <table class="w100 h100">
          <tr>
            <td class="w20">
              <span class="verAlignMid">
                <input type="radio" name="mem010addLabelT" value="<%= GSConstMemo.MODE_SELECT %>" id="addLabelType0" checked>
                <label class="fw_b fs_13" for="addLabelType0"><gsmsg:write key="memo.mem010.37" /></label>
              </span>
            </td>
            <td class="w80">
              <select id="label_dialog_sel" class="wp180">
                <logic:notEmpty name="mem010Form" property="labelList">
                  <logic:iterate id="emp" name="mem010Form" property="labelList">
                    <option value="<bean:write name="emp" property="mmlSid" />">
                      <bean:write name="emp" property="mmlName" />
                    </option>
                  </logic:iterate>
                </logic:notEmpty>
              </select>
            </td>
          </tr>
          <tr>
            <td class="w30">
              <span class="verAlignMid">
                <input type="radio" name="mem010addLabelT" value="<%= GSConstMemo.MODE_ADD %>" id="addLabelType1">
                <label class="fw_b fs_13" for="addLabelType1"><gsmsg:write key="memo.mem010.38" /></label>
              </span>
            </td>
            <td class="w70">
              <input type="text" id="label_dialog_new" class="w100" maxlength="20" disabled>
              <span id="addLabelParam"></span>
            </td>
          </tr>
        </table>
      </div>

      <div id="delMemoMsgPop" title="">
        <ul class="p0 verAlignMid w100 pt10">
          <li class="">
            <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
            <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
          </li>
          <li class="pl10 dialog_msgbody word_b-all">
            <span id="delMemoMsgArea"><gsmsg:write key="memo.mem010.41" /></span>
          </li>
        </ul>
      </div>
      
      <div id="loading_pop" title="">
        <table class="w100 h100">
          <tr>
            <td class="txt_m txt_c">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
              <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </html:form>
</body>
</html:html>