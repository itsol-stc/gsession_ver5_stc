<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
<title>GROUPSESSION <gsmsg:write key="cir.5" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<!--[if IE]><script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js"></script><![endif]-->
<script src="../common/js/jplot/jquery.jqplot.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.barRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.pieRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.highlighter.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.categoryAxisRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.canvasAxisTickRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jplot/jqplot.canvasTextRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jplot/jqplot.pointLabels.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.cursor.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src='../common/js/jquery-ui-1.8.16/ui/jquery-ui-1.8.16.custom.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/jquery.ui.datepicker.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>

<script src='../circular/js/cir210.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href="../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../common/js/jquery-ui-1.8.16/development-bundle/themes/base/jquery.ui.all.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../circular/css/circular.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/circular/cir210">

<input type="hidden" name="CMD" value="">
<html:hidden property="cir210NowPage"/>
<html:hidden property="cir210GraphItem"/>
<html:hidden property="cir210GsAdminFlg"/>
<html:hidden property="cir210CtrlFlgWml"/>
<html:hidden property="cir210CtrlFlgSml"/>
<html:hidden property="cir210CtrlFlgFil"/>
<html:hidden property="cir210CtrlFlgBbs"/>
<html:hidden property="cir210CtrlFlgCht"/>
<html:hidden property="logCountBack"/>
<html:hidden property="backScreen" />

<html:hidden property="cirAccountSid" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="jsonDateData"/>
<html:hidden property="jsonJcirData"/>
<html:hidden property="jsonScirData"/>

<logic:notEqual name="cir210Form" property="cir210DateUnit" value="2">
  <html:hidden property="cir210DateMonthlyFrYear"/>
  <html:hidden property="cir210DateMonthlyFrMonth"/>
  <html:hidden property="cir210DateMonthlyToYear"/>
  <html:hidden property="cir210DateMonthlyToMonth"/>
</logic:notEqual>

<div class="kanriPageTitle mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.statistical.info" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
  <div class="display_flex bor1 bgC_body bgI_none">
    <div class=" ">
      <table class="wp180 h100 ">

        <%-- ログイン履歴 --%>
        <logic:equal name="cir210Form" property="cir210GsAdminFlg" value="true">
        <tr class="" onClick="buttonPush('statsMain');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../main/images/classic/icon_menu_login_history.gif" alt="<gsmsg:write key="main.login.history" />">
              <img class="original-display mr5" src="../main/images/original/icon_login_history.png" alt="<gsmsg:write key="main.login.history" />">
              <span class="timeline_menu"><gsmsg:write key="main.login.history" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- WEBメール --%>
        <logic:equal name="cir210Form" property="cir210CtrlFlgWml" value="true">
        <tr class="" onClick="buttonPush('webmail');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../webmail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="wml.wml010.25" />">
              <img class="original-display mr5" src="../webmail/images/original/menu_icon_single.png" alt="<gsmsg:write key="wml.wml010.25" />">
              <span class="timeline_menu"><gsmsg:write key="wml.wml010.25" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- ショートメール --%>
        <logic:equal name="cir210Form" property="cir210CtrlFlgSml" value="true">
        <tr class="" onClick="buttonPush('smail');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../webmail/images/classic/smail_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
              <img class="original-display mr5" src="../smail/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.shortmail" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.shortmail" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- 回覧板 --%>
        <tr class="">
          <td class="p0 bor_t1 bor_b1 ">
            <div class="pl5 toukei_plugin-select bgC_body bgI_none fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../webmail/images/classic/circular_icon_single.gif" alt="<gsmsg:write key="cir.5" />">
              <img class="original-display mr5" src="../webmail/images/original/circular_icon_single.png" alt="<gsmsg:write key="cir.5" />">
              <span class="timeline_menu"><gsmsg:write key="cir.5" /></span>
            </div>
          </td>
        </tr>

        <%-- ファイル管理 --%>
        <logic:equal name="cir210Form" property="cir210CtrlFlgFil" value="true">
        <tr class="" onClick="buttonPush('file');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../webmail/images/classic/file_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />">
              <img class="original-display mr5" src="../webmail/images/original/file_icon_single.png" alt="<gsmsg:write key="cmn.filekanri" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.filekanri" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- 掲示板 --%>
        <logic:equal name="cir210Form" property="cir210CtrlFlgBbs" value="true">
        <tr class="" onClick="buttonPush('bulletin');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../webmail/images/classic/bulletin_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
              <img class="original-display mr5" src="../webmail/images/original/bulletin_icon_single.png" alt="<gsmsg:write key="cmn.bulletin" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.bulletin" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- チャット --%>
        <logic:equal name="cir210Form" property="cir210CtrlFlgCht" value="true">
        <tr class="" onClick="buttonPush('chat');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../webmail/images/classic/chat_icon_single.gif" alt="<gsmsg:write key="cht.01" />">
              <img class="original-display mr5" src="../webmail/images/original/chat_icon_single.png" alt="<gsmsg:write key="cht.01" />">
              <span class="timeline_menu"><gsmsg:write key="cht.01" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <tr class=" ">
          <td class="h100 toukei_pluginArea bor_r1"></td>
        </tr>
      </table>

    </div>
    <div class="w100 p5 ">
      <div class="display_flex">
        <%-- 日付指定 --%>
        <div class="fw_b mr5 txt_l no_w">
          <%-- 月単位表示年月指定 --%>
          <logic:equal name="cir210Form" property="cir210DateUnit" value="2">
            <gsmsg:write key="ntp.132" />
            <gsmsg:write key="wml.215" />
            <html:select name="cir210Form" property="cir210DateMonthlyFrYear" onchange="changeYearMonthCombo('from');">
              <html:optionsCollection name="cir210Form" property="cir210DspYearLabel" value="value" label="label" />
            </html:select>
            <html:select name="cir210Form" property="cir210DateMonthlyFrMonth" onchange="changeYearMonthCombo('from');">
              <html:optionsCollection name="cir210Form" property="cir210DspMonthLabel" value="value" label="label" />
            </html:select>
            <span>
              <gsmsg:write key="tcd.153" />
            </span>
            <html:select name="cir210Form" property="cir210DateMonthlyToYear" onchange="changeYearMonthCombo('to');">
              <html:optionsCollection name="cir210Form" property="cir210DspYearLabel" value="value" label="label" />
            </html:select>
            <html:select name="cir210Form" property="cir210DateMonthlyToMonth" onchange="changeYearMonthCombo('to');">
              <html:optionsCollection name="cir210Form" property="cir210DspMonthLabel" value="value" label="label" />
            </html:select>
          </logic:equal>

          <%-- 週単位表示 --%>
          <logic:equal name="cir210Form" property="cir210DateUnit" value="1">
            <div id="jquery-ui-datepicker-wrap" class="display_flex">
              <gsmsg:write key="ntp.132" />
              <gsmsg:write key="wml.215" />
              <html:text name="cir210Form" property="cir210DateWeeklyFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart mr5"></span>
              <gsmsg:write key="tcd.153" />
              <html:text name="cir210Form" property="cir210DateWeeklyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </div>
          </logic:equal>

          <%-- 日付範囲指定 --%>
          <logic:equal name="cir210Form" property="cir210DateUnit" value="0">
            <span id="jquery-ui-datepicker-wrap" class="display_flex">
              <gsmsg:write key="ntp.132" />
              <gsmsg:write key="wml.215" />
              <html:text name="cir210Form" property="cir210DateDailyFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart mr5"></span>
              <gsmsg:write key="tcd.153" />
              <html:text name="cir210Form" property="cir210DateDailyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </span>
          </logic:equal>
        </div>
        <div class="ml_auto">
        &nbsp;
        </div>
         <%-- 表示件数 --%>
        <div class="fw_b mr10 category_sel_area">
          <gsmsg:write key="cmn.number.display" />
          <gsmsg:write key="wml.215" />
          <html:select name="cir210Form" property="cir210DspNum" styleClass="wp55" onchange="changeDspNumCombo();">
            <logic:notEmpty name="cir210Form" property="cir210DspNumLabel">
              <html:optionsCollection name="cir210Form" property="cir210DspNumLabel" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
        </div>

        <%-- 月週日切り替えラジオ--%>
        <div class="category_sel_area">
          <span class="verAlignMid">
            <html:radio name="cir210Form" property="cir210DateUnit" value="2" styleId="radio_month" onclick="buttonPush('dateUnitChange');" />
            <label for="radio_month" class=" mr10">
              <gsmsg:write key="cmn.monthly.3" />
            </label>
            <html:radio name="cir210Form" property="cir210DateUnit" value="1" styleId="radio_week" onclick="buttonPush('dateUnitChange');" />
            <label for="radio_week" class=" mr10">
              <gsmsg:write key="cmn.weekly3" />
            </label>
            <html:radio name="cir210Form" property="cir210DateUnit" value="0" styleId="radio_day" onclick="buttonPush('dateUnitChange');" />
            <label for="radio_day" class=" m0">
              <gsmsg:write key="cmn.daily" />
            </label>
          </span>
        </div>
      </div>

      <%-- コンテンツ--%>
      <table class="w100 p0 mt10">
        <tr class="hp300">
          <%-- メニュー--%>
          <td id="sel_menu_wrapper" class="wp180 bor1 txt_t p0 toukei_pluginArea">
            <div class="m0 p0">
              <table class="wp180 mb5">
               <tr>
                  <td class="toukei_option txt_l" id="cir_graph_jcir" onclick="changeDspItem('cir_graph_jcir');">
                    <span><gsmsg:write key="cir.cir210.1" /></span>
                  </td>
                </tr>
                <tr>
                  <td class="toukei_option txt_l" id="cir_graph_scir" onclick="changeDspItem('cir_graph_scir');">
                    <span><gsmsg:write key="cir.cir210.2" /></span>
                  </td>
                </tr>
              </table>
            </div>
          </td>

          <%-- メニュー格納用縦線 --%>
          <td id="menu_length_area" class="toukei_optionClose borC_light wp5 p0 m0">&nbsp;</td>

          <%-- グラフ --%>
          <td class="txt_t m0 pl5 p0">
            <table class="w100 m0 p0">
              <%-- グラフ --%>
              <tr>
                <td class="pb10">
                  <div id="cirCntGraph" class="hp260"></div>
                </td>
              </tr>
              <!-- ページング -->
              <tr>
                <bean:size id="pageCount" name="cir210Form" property="cir210PageLabel" scope="request" />
                <logic:greaterThan name="pageCount" value="1">
                  <td class="paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select styleClass="paging_combo" property="cir210DspPage1" onchange="changePage(this);">
                      <html:optionsCollection name="cir210Form" property="cir210PageLabel" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('pageNext');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </td>
                </logic:greaterThan>
              </tr>
                <%-- 集計一覧 --%>
                <tr id="anken_detail_area">
                  <td class="w100">
                    <table class="w100">
                      <tr>
                        <td valign="top">
                          <table class="table-top m0" id="anken_val_table">

                            <tr class="anken_val_title" id="anken_val_title">
                              <th class="w40">
                                <gsmsg:write key="cmn.date3" />
                              </th>
                              <th class="w30">
                                <gsmsg:write key="cir.cir210.1" />
                              </th>
                              <th class="w30">
                                <gsmsg:write key="cir.cir210.2" />
                              </th>
                            </tr>
                            <logic:notEmpty name="cir210Form" property="cir210LogCountList">
                              <logic:iterate name="cir210Form" property="cir210LogCountList" id="dayData">
                                <tr class="anken_val">
                                  <td class="txt_c no_w">
                                    <bean:write name="dayData" property="dspDate" />
                                  </td>
                                  <td class="txt_r">
                                    <bean:write name="dayData" property="strJcirNum" />
                                  </td>
                                  <td class="txt_r">
                                    <bean:write name="dayData" property="strScirNum" />
                                  </td>
                                </tr>
                              </logic:iterate>
                              <tr class="anken_val">
                                <td class="txt_c no_w">
                                  <b><gsmsg:write key="cmn.average" /></b>
                                </td>
                                <td class="txt_r">
                                  <b><bean:write name="cir210Form" property="cir210AveJcirNum" /></b>
                                </td>
                                <td class="txt_r">
                                  <b><bean:write name="cir210Form" property="cir210AveScirNum" /></b>
                                </td>
                              </tr>
                              <tr class="anken_val">
                                <td class="txt_c no_w">
                                  <b><gsmsg:write key="cmn.sum" /></b>
                                </td>
                                <td class="txt_r">
                                  <b><bean:write name="cir210Form" property="cir210SumJcirNum" /></b>
                                </td>
                                <td class="txt_r">
                                  <b><bean:write name="cir210Form" property="cir210SumScirNum" /></b>
                                </td>
                              </tr>
                            </logic:notEmpty>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              <!-- ページング -->
              <tr>
                <bean:size id="pageCount2" name="cir210Form" property="cir210PageLabel" scope="request" />
                <logic:greaterThan name="pageCount2" value="1">
                  <td class="paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select styleClass="paging_combo" property="cir210DspPage2" onchange="changePage(this);">
                      <html:optionsCollection name="cir210Form" property="cir210PageLabel" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('pageNext');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </td>
                </logic:greaterThan>
              </tr>
            </table>
          </td>
        </tr>
      </table>

    </div>
  </div>
  <span id="tooltip_area"></span>
  <div id="searchBtnResultPop" class="hp550 display_none ofy_h" title="<gsmsg:write key="bbs.bbs041.2" />">
    <p>
      <div id="searchBtnResultArea" class="hp450">
      </div>
    </p>
  </div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>