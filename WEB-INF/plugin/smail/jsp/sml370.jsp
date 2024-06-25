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
<title>GROUPSESSION <gsmsg:write key="cmn.shortmail" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
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

<script src='../smail/js/sml370.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../common/js/jquery-ui-1.8.16/development-bundle/themes/base/jquery.ui.all.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/smail/sml370">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="sml370InitFlg" />
<html:hidden property="sml370NowPage"/>
<html:hidden property="sml370GraphItem"/>
<html:hidden property="sml370GsAdminFlg"/>
<html:hidden property="sml370CtrlFlgWml"/>
<html:hidden property="sml370CtrlFlgCir"/>
<html:hidden property="sml370CtrlFlgFil"/>
<html:hidden property="sml370CtrlFlgBbs"/>
<html:hidden property="logCountBack"/>

<html:hidden property="jsonDateData"/>
<html:hidden property="jsonJmailData"/>
<html:hidden property="jsonSmailData"/>

<logic:notEqual name="sml370Form" property="sml370DateUnit" value="2">
  <html:hidden property="sml370DateMonthlyFrYear"/>
  <html:hidden property="sml370DateMonthlyFrMonth"/>
  <html:hidden property="sml370DateMonthlyToYear"/>
  <html:hidden property="sml370DateMonthlyToMonth"/>
</logic:notEqual>

<!-- BODY -->

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="cmn.statistical.info" />
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
<div class="wrapper mrl_auto ">
  <div class="display_flex bor1 bgC_body bgI_none">
    <div class=" ">
      <table class="wp180 h100 ">
        <%-- ログイン履歴 --%>
        <logic:equal name="sml370Form" property="sml370GsAdminFlg" value="true">
          <tr class="" onClick="buttonPush('statsMain');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
                <img class="btn_classicImg-display mr5" src="../main/images/classic/icon_menu_login_history.gif" alt="<gsmsg:write key="main.login.history" />">
                <img class="btn_originalImg-display mr5" src="../main/images/original/icon_login_history.png" alt="<gsmsg:write key="main.login.history" />">
                <span class="timeline_menu"><gsmsg:write key="main.login.history" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <%-- WEBメール --%>
        <logic:equal name="sml370Form" property="sml370CtrlFlgWml" value="true">
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
        <tr class="txt_l">
          <td class="p0 bor_t1 bor_b1 ">
            <div class="pl5 toukei_plugin-select bgC_body bgI_none fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../smail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
              <img class="original-display mr5" src="../smail/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.shortmail" />">
              <span class="timeline_menu "><gsmsg:write key="cmn.shortmail" /></span>
            </div>
          </td>
        </tr>
        <%-- 回覧板 --%>
        <logic:equal name="sml370Form" property="sml370CtrlFlgCir" value="true">
        <tr class="" onClick="buttonPush('circular');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../circular/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cir.5" />">
              <img class="original-display mr5" src="../circular/images/original/menu_icon_single.png" alt="<gsmsg:write key="cir.5" />">
              <span class="timeline_menu"><gsmsg:write key="cir.5" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <%-- ファイル管理 --%>
        <logic:equal name="sml370Form" property="sml370CtrlFlgFil" value="true">
        <tr class="" onClick="buttonPush('file');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />">
              <img class="original-display mr5" src="../file/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.filekanri" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.filekanri" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- 掲示板 --%>
        <logic:equal name="sml370Form" property="sml370CtrlFlgBbs" value="true">
        <tr class="" onClick="buttonPush('bulletin');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../bulletin/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
              <img class="original-display mr5" src="../bulletin/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.bulletin" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.bulletin" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- チャット --%>
        <logic:equal name="sml370Form" property="sml370CtrlFlgCht" value="true">
        <tr class="" onClick="buttonPush('chat');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none " >
              <img class="classic-display mr5" src="../chat/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cht.01" />">
              <img class="original-display mr5" src="../chat/images/original/menu_icon_single.png" alt="<gsmsg:write key="cht.01" />">
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
    <div class="m5 w100 ">
      <div class="display_flex">
        <%-- 月単位表示年月指定 --%>
        <div class="fw_b mr5 txt_l no_w">
          <logic:equal name="sml370Form" property="sml370DateUnit" value="2">
            <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
            <html:select name="sml370Form" property="sml370DateMonthlyFrYear" onchange="changeYearMonthCombo('from');">
              <html:optionsCollection name="sml370Form" property="sml370DspYearLabel" value="value" label="label" />
            </html:select>
            <html:select name="sml370Form" property="sml370DateMonthlyFrMonth" onchange="changeYearMonthCombo('from');">
              <html:optionsCollection name="sml370Form" property="sml370DspMonthLabel" value="value" label="label" />
            </html:select>
            <gsmsg:write key="tcd.153" />
            <html:select name="sml370Form" property="sml370DateMonthlyToYear" onchange="changeYearMonthCombo('to');">
              <html:optionsCollection name="sml370Form" property="sml370DspYearLabel" value="value" label="label" />
            </html:select>
            <html:select name="sml370Form" property="sml370DateMonthlyToMonth" onchange="changeYearMonthCombo('to');">
              <html:optionsCollection name="sml370Form" property="sml370DspMonthLabel" value="value" label="label" />
            </html:select>
          </logic:equal>

        <%-- 週単位表示 --%>
          <logic:equal name="sml370Form" property="sml370DateUnit" value="1">
            <div class="verAlignMid">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="sml370Form" property="sml370DateWeeklyFrStr" readonly="true" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <span class="ml5"><gsmsg:write key="tcd.153" /></span>
              <html:text name="sml370Form" property="sml370DateWeeklyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </div>
          </logic:equal>

        <%-- 日付範囲指定 --%>
          <logic:equal name="sml370Form" property="sml370DateUnit" value="0">
            <div class="verAlignMid">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="sml370Form" property="sml370DateDailyFrStr" readonly="true" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <span class="ml5"><gsmsg:write key="tcd.153" /></span>
              <html:text name="sml370Form" property="sml370DateDailyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
            </div>
          </logic:equal>
        </div>
        <div class="ml_auto">
        &nbsp;
        </div>
        <div class="mr10 w20 txt_r">
          <div class="  txt_l">
            <span class="fw_b"><gsmsg:write key="cmn.number.display" /><gsmsg:write key="wml.215" /></span>
            <html:select name="sml370Form" property="sml370DspNum" styleClass="wp50 mr5" onchange="changeDspNumCombo();">
              <logic:notEmpty name="sml370Form" property="sml370DspNumLabel">
                <html:optionsCollection name="sml370Form" property="sml370DspNumLabel" value="value" label="label" />
              </logic:notEmpty>
            </html:select><!--
         --><div class="verAlignMid txt_m">
              <html:checkbox name="sml370Form" property="sml370SysMailKbn" value="1" styleId="sysMailFlg" styleClass="" onclick="clickSysMailKbn();" />
              <label for="sysMailFlg" ><gsmsg:write key="sml.sml370.3" /></label>
            </div>
            </div>
        </div>
        <div class="">
          <div class="verAlignMid">
            <html:radio name="sml370Form" property="sml370DateUnit" value="2" styleId="radio_month" onclick="buttonPush('dateUnitChange');"/>
            <label for="radio_month" class="no_w mr10"><gsmsg:write key="cmn.monthly.3" /></label>
            <html:radio name="sml370Form" property="sml370DateUnit" value="1" styleId="radio_week" onclick="buttonPush('dateUnitChange');"/>
            <label for="radio_week" class="no_w mr10"><gsmsg:write key="cmn.weekly3" /></label>
            <html:radio name="sml370Form" property="sml370DateUnit" value="0" styleId="radio_day" onclick="buttonPush('dateUnitChange');"/>
            <label for="radio_day" class="no_w"><gsmsg:write key="cmn.daily" /></label>
          </div>
        </div>
      </div>
      <table class="w100 mt10">
        <tr>
          <td class="wp180 bor1 txt_t p0 toukei_pluginArea" id="sel_menu_wrapper">
            <logic:equal name="sml370Form" property="sml370GraphItem" value="sml_graph_jmail">
              <bean:define id="jmeilSelFlg" value="toukei_option-select" />
              <bean:define id="smeilSelFlg" value="" />
            </logic:equal>
            <logic:equal name="sml370Form" property="sml370GraphItem" value="sml_graph_smail">
              <bean:define id="jmeilSelFlg" value="" />
              <bean:define id="smeilSelFlg" value="toukei_option-select" />
            </logic:equal>
            <table class="w100 h100">
              <tr>
                <td class="toukei_option <bean:write name="jmeilSelFlg"/> txt_l" id="sml_graph_jmail" onclick="changeDspItem('sml_graph_jmail');">
                  <span><gsmsg:write key="sml.sml370.1" /></span>
                </td>
              </tr>
              <tr>
                <td class="toukei_option <bean:write name="smeilSelFlg"/> txt_l" id="sml_graph_smail" onclick="changeDspItem('sml_graph_smail');">
                  <span><gsmsg:write key="sml.sml370.2" /></span>
                </td>
              </tr>
            </table>
          </td>
          <td class="wp5 borC_light toukei_optionClose" id="menu_length_area">
          </td>
          <td class="pl5">
<!--                   グラフ -->
            <div id="smlCntGraph" class="hp260 w100 mb10"></div>
<!--                   ページング -->
            <bean:size id="pageCount2" name="sml370Form" property="sml370PageLabel" scope="request" />
            <logic:greaterThan name="pageCount2" value="1">
              <div class="txt_r paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <logic:notEmpty name="sml370Form" property="sml370PageLabel" scope="request">
                <html:select property="sml370DspPage2" onchange="changePage(this);" styleClass="paging_combo">
                  <html:optionsCollection name="sml370Form" property="sml370PageLabel" value="value" label="label" />
                </html:select>
                </logic:notEmpty>
                <button type="button" class="webIconBtn" onClick="buttonPush('pageNext');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
<!--                   集計一覧 -->
            <table class="table-top m0 table-fixed w100" id="anken_val_table">

              <tr class="" id="anken_val_title">
                <th class="w40"><b><gsmsg:write key="cmn.date3" /></b></th>
                <th class="w30"><b><gsmsg:write key="sml.sml370.1" /></b></th>
                <th class="w30"><b><gsmsg:write key="sml.sml370.2" /></b></th>
              </tr>
              <logic:notEmpty name="sml370Form" property="sml370LogCountList">
              <logic:iterate name="sml370Form" property="sml370LogCountList" id="dayData">
              <tr class="">
              <td class="txt_c"><bean:write name="dayData" property="dspDate" /></td>
              <td class="txt_r"><bean:write name="dayData" property="strJmailNum" /></td>
              <td class="txt_r"><bean:write name="dayData" property="strSmailNum" /></td>
              </tr>
              </logic:iterate>

              <tr class="">
              <td class="txt_c"><b><gsmsg:write key="cmn.average" /></b></td>
              <td class="txt_r"><b><bean:write name="sml370Form" property="sml370AveJmailNum" /></b></td>
              <td class="txt_r"><b><bean:write name="sml370Form" property="sml370AveSmailNum" /></b></td>
              </tr>
              <tr class="">
              <td class="txt_c"><b><gsmsg:write key="cmn.sum" /></b></td>
              <td class="txt_r"><b><bean:write name="sml370Form" property="sml370SumJmailNum" /></b></td>
              <td class="txt_r"><b><bean:write name="sml370Form" property="sml370SumSmailNum" /></b></td>
              </tr>
              </logic:notEmpty>


            </table>
<!--                   ページング -->
            <bean:size id="pageCount2" name="sml370Form" property="sml370PageLabel" scope="request" />
            <logic:greaterThan name="pageCount2" value="1">
            <div class="txt_r paging">
              <button type="button" class="webIconBtn" class="baseBtn" href="#!" onClick="buttonPush('pageLast');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <logic:notEmpty name="sml370Form" property="sml370PageLabel" scope="request">
              <html:select property="sml370DspPage2" onchange="changePage(this);" styleClass="paging_combo">
                <html:optionsCollection name="sml370Form" property="sml370PageLabel" value="value" label="label" />
              </html:select>
              </logic:notEmpty>
              <button type="button" class="webIconBtn" onClick="buttonPush('pageNext');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
            </logic:greaterThan>


          </td>
        </tr>
      </table>
    </div>
  </div>
</div>


<span id="tooltip_area"></span>



</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>