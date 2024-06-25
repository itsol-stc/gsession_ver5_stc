<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.man.GSConstMain"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cht.01" /> <gsmsg:write key="cmn.statistical.info" /></title>

<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel=stylesheet href='../chat/css/chat.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.8.16/development-bundle/themes/base/jquery.ui.all.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/jquery.jqplot.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.barRenderer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.pieRenderer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.highlighter.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.categoryAxisRenderer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.canvasAxisTickRenderer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.canvasTextRenderer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.pointLabels.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jqplot_1_0_9/plugins/jqplot.cursor.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src='../chat/js/cht070.js?<%=GSConst.VERSION_PARAM%>'></script>

<script type="text/javascript">
<!--
  var msglist_cht070 = (function() {
    //使用するメッセージキーの配列を作成
    var ret = new Array();
    ret['message'] = '<gsmsg:write key="cht.cht070.01"/>';
    ret['send'] = '<gsmsg:write key="cht.cht070.02"/>';
    ret['user'] = '<gsmsg:write key="cmn.user"/>';
    ret['group'] = '<gsmsg:write key="cmn.group"/>';
    ret['sum'] = '<gsmsg:write key="ntp.172"/>';
    return ret;
  })();
  -->
</script>
</head>
<body>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

<html:form action="/chat/cht070">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="jsonDateData" />
<html:hidden property="jsonUserData" />
<html:hidden property="jsonGroupData" />
<html:hidden property="jsonSumData" />
<html:hidden property="jsonSendData" />
<html:hidden property="cht070NowPage" />
<html:hidden property="cht070GraphItem" />
<html:hidden property="cht070GsAdminFlg" />
<html:hidden property="cht070CtrlFlgWml" />
<html:hidden property="cht070CtrlFlgSml" />
<html:hidden property="cht070CtrlFlgCir" />
<html:hidden property="cht070CtrlFlgFil" />
<html:hidden property="cht070CtrlFlgBul" />
<html:hidden property="cht070InitFlg" />
<html:hidden property="logCountBack"/>
<logic:notEqual name="cht070Form" property="cht070DateUnit" value="2">
<html:hidden property="cht070DateMonthlyFrYear" />
<html:hidden property="cht070DateMonthlyFrMonth" />
<html:hidden property="cht070DateMonthlyToYear" />
<html:hidden property="cht070DateMonthlyToMonth" />
</logic:notEqual>

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cmn.statistical.info" />
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
        <logic:equal name="cht070Form" property="cht070GsAdminFlg" value="true">
        <tr class="" onClick="buttonPush('statsMain');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../main/images/classic/icon_menu_login_history.gif" alt="<gsmsg:write key="main.login.history" />">
              <img class="original-display mr5" src="../main/images/original/icon_login_history.png" alt="<gsmsg:write key="main.login.history" />">
              <span class="timeline_menu"><gsmsg:write key="main.login.history" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <%-- WEBメール --%>
        <logic:equal name="cht070Form" property="cht070CtrlFlgWml" value="true">
        <tr class="" onClick="buttonPush('webmail');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../webmail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="wml.wml010.25" />">
              <img class="original-display mr5" src="../webmail/images/original/menu_icon_single.png" alt="<gsmsg:write key="wml.wml010.25" />">
              <span class="timeline_menu"><gsmsg:write key="wml.wml010.25" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <%-- ショートメール --%>
        <logic:equal name="cht070Form" property="cht070CtrlFlgSml" value="true">
        <tr class="" onClick="buttonPush('smail');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../smail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
              <img class="original-display mr5" src="../smail/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.shortmail" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.shortmail" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <%-- 回覧板 --%>
        <logic:equal name="cht070Form" property="cht070CtrlFlgCir" value="true">
        <tr class="" onClick="buttonPush('circular');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../circular/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cir.5" />">
              <img class="original-display mr5" src="../circular/images/original/menu_icon_single.png" alt="<gsmsg:write key="cir.5" />">
              <span class="timeline_menu"><gsmsg:write key="cir.5" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- ファイル管理 --%>
        <logic:equal name="cht070Form" property="cht070CtrlFlgFil" value="true">
        <tr class="" onClick="buttonPush('file');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />">
              <img class="original-display mr5" src="../file/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.filekanri" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.filekanri" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- 掲示板 --%>
        <logic:equal name="cht070Form" property="cht070CtrlFlgBul" value="true">
        <tr class="" onClick="buttonPush('bulletin');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../bulletin/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
              <img class="original-display mr5" src="../bulletin/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.bulletin" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.bulletin" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- チャット --%>
        <tr class=""  >
          <td class="bor_t1 bor_b1 p0">
            <div class="pl5 toukei_plugin-select bgC_body bgI_none fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../chat/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cht.01" />">
              <img class="original-display mr5" src="../chat/images/original/menu_icon_single.png" alt="<gsmsg:write key="cht.01" />">
              <span class="timeline_menu"><gsmsg:write key="cht.01" /></span>
            </div>
          </td>
        </tr>
        <tr class=" ">
          <td class="h100 toukei_pluginArea bor_r1"></td>
        </tr>
      </table>
    </div>
    <div class="w100 p5">
      <div class="display_flex">
        <div class="fw_b mr5 txt_l no_w">
          <%-- 月単位表示年月指定 --%>
          <logic:equal name="cht070Form" property="cht070DateUnit" value="<%=String.valueOf(GSConstMain.STATS_DATE_UNIT_MONTH)%>">
            <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
            <html:select name="cht070Form" property="cht070DateMonthlyFrYear" onchange="changeYearMonthCombo('from');">
              <html:optionsCollection name="cht070Form" property="cht070DspYearLabel" value="value" label="label" />
            </html:select>
            <html:select name="cht070Form" property="cht070DateMonthlyFrMonth" onchange="changeYearMonthCombo('from');">
              <html:optionsCollection name="cht070Form" property="cht070DspMonthLabel" value="value" label="label" />
            </html:select>
            <gsmsg:write key="tcd.153" />
            <html:select name="cht070Form" property="cht070DateMonthlyToYear" onchange="changeYearMonthCombo('to');">
              <html:optionsCollection name="cht070Form" property="cht070DspYearLabel" value="value" label="label" />
            </html:select>
            <html:select name="cht070Form" property="cht070DateMonthlyToMonth" onchange="changeYearMonthCombo('to');">
              <html:optionsCollection name="cht070Form" property="cht070DspMonthLabel" value="value" label="label" />
            </html:select>
          </logic:equal>

          <%-- 週単位表示 --%>
          <logic:equal name="cht070Form" property="cht070DateUnit" value="<%=String.valueOf(GSConstMain.STATS_DATE_UNIT_WEEK)%>">
            <span class="display_flex">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="cht070Form" property="cht070DateWeeklyFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart mr5"></span>
              <gsmsg:write key="tcd.153" />
              <html:text name="cht070Form" property="cht070DateWeeklyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </span>
          </logic:equal>
          <%-- 日付範囲指定 --%>
          <logic:equal name="cht070Form" property="cht070DateUnit" value="<%=String.valueOf(GSConstMain.STATS_DATE_UNIT_DAY)%>">
            <span class="display_flex">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="cht070Form" property="cht070DateDailyFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart mr5"></span>
              <gsmsg:write key="tcd.153" />
              <html:text name="cht070Form" property="cht070DateDailyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </span>
          </logic:equal>
        </div>
        <div class="ml_auto">
        &nbsp;
        </div>
        <div class="mr10 txt_r">
          <div class="txt_l">
            <%-- 表示件数 --%>
            <span class="fw_b ">
              <gsmsg:write key="cmn.number.display" /><gsmsg:write key="wml.215" />
              <html:select name="cht070Form" property="cht070DspNum" styleClass="wp70" onchange="changeDspNumCombo();">
                <logic:notEmpty name="cht070Form" property="cht070DspNumLabel">
                  <html:optionsCollection name="cht070Form" property="cht070DspNumLabel" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
            </span>
            <%-- 表示項目 --%>
            <div class="verAlignMid" id="dspItem">
              <span class="fw_b no_w"><gsmsg:write key="reserve.100" /><gsmsg:write key="wml.215" /></span>
              <html:checkbox name="cht070Form" property="cht070DspUser"  styleId="cht070DspUser" styleClass="m0" onchange="chkChange();" value="<%=String.valueOf(GSConstChat.CHAT_LOG_DSPUSER_YES)%>"/>
              <label class="no_w mr10" for="cht070DspUser"><gsmsg:write key="cmn.user" /></label>
              <html:checkbox name="cht070Form" property="cht070DspGroup" styleId="cht070DspGroup" styleClass="m0" onchange="chkChange();" value="<%=String.valueOf(GSConstChat.CHAT_LOG_DSPGROUP_YES)%>" />
              <label class="no_w mr10" for="cht070DspGroup"><gsmsg:write key="cmn.group" /></label>
              <html:checkbox name="cht070Form" property="cht070DspSum"  styleId="cht070DspSum" styleClass="m0" onchange="chkChange();" value="<%=String.valueOf(GSConstChat.CHAT_LOG_DSPSUM_YES)%>"/>
              <label class="no_w" for="cht070DspSum"><gsmsg:write key="ntp.172" /></label>
            </div>
          </div>
        </div>
        <div class="">
          <div class="verAlignMid">
            <html:radio name="cht070Form" property="cht070DateUnit" styleId="radio_month" onclick="buttonPush('dateUnitChange');" value="<%=String.valueOf(GSConstMain.STATS_DATE_UNIT_MONTH)%>"/>
            <label for="radio_month" class="no_w mr10"><gsmsg:write key="cmn.monthly.3" /></label>
            <html:radio name="cht070Form" property="cht070DateUnit" styleId="radio_week" onclick="buttonPush('dateUnitChange');" value="<%=String.valueOf(GSConstMain.STATS_DATE_UNIT_WEEK)%>"/>
            <label for="radio_week" class="no_w mr10"><gsmsg:write key="cmn.weekly3" /></label>
            <html:radio name="cht070Form" property="cht070DateUnit" styleId="radio_day" onclick="buttonPush('dateUnitChange');" value="<%=String.valueOf(GSConstMain.STATS_DATE_UNIT_DAY)%>"/>
            <label for="radio_day" class="no_w"><gsmsg:write key="cmn.daily" /></label>
          </div>
        </div>
      </div>

      <table class="w100 mt10">
        <tr>
          <td class="wp180 bor1 txt_t p0 toukei_pluginArea" id="sel_menu_wrapper">
            <% String msgClass=" toukei_option-select", sendClass=""; %>
            <logic:equal name="cht070Form" property="cht070GraphItem" value="chat_graph_send">
            <% msgClass=""; sendClass=" toukei_option-select"; %>
            </logic:equal>
            <table class="w100 h100">
              <tr>
                <td class="toukei_option txt_l<%= msgClass %>" id="chat_graph_message" onclick="changeDspItem('chat_graph_message');">
                  <span><gsmsg:write key="cht.cht070.01" /></span>
                </td>
              </tr>
              <tr>
                <td class="toukei_option txt_l<%= sendClass %>" id="chat_graph_send" onclick="changeDspItem('chat_graph_send');">
                  <span><gsmsg:write key="cht.cht070.02" /></span>
                </td>
              </tr>
            </table>
          </td>
          <td class="wp5 toukei_optionClose borC_light" id="menu_length_area">
          </td>
          <td class="pl5">
            <table class="p0 m0 w100">
              <%-- グラフ --%>
              <tr>
                <td class=" pb10">
                  <div id="chat_Graph" class="hp260"></div>
                </td>
              </tr>

              <!-- ページング -->
              <tr>
                <td>
                  <bean:size id="pageCount2" name="cht070Form" property="cht070PageLabel" scope="request" />
                  <logic:greaterThan name="pageCount2" value="1">
                  <div class="txt_r paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <logic:notEmpty name="cht070Form" property="cht070PageLabel" scope="request">
                    <html:select property="cht070DspPage2" onchange="changePage(this);" styleClass="paging_combo">
                      <html:optionsCollection name="cht070Form" property="cht070PageLabel" value="value" label="label" />
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

              <%-- 集計一覧 --%>
              <tr id="anken_detail_area">
                <td class="w100">
                  <table class="table-top w100 m0" id="cht_val_table">

                    <tr class="" id="chat_val_title">
                      <th class="w30 fw_b no_w" rowspan="2"><gsmsg:write key="cmn.date3" /></th>
                      <th class="w40 fw_b no_w" colspan="3"><gsmsg:write key="cht.cht070.01" /></th>
                      <th class="w30 fw_b no_w" rowspan="2"><gsmsg:write key="cht.cht070.02" /></th>
                    </tr>
                    <tr class="" id="chat_val_title2">
                      <th class="fw_b no_w"><gsmsg:write key="cmn.user" /></th>
                      <th class="fw_b no_w"><gsmsg:write key="cmn.group" /></th>
                      <th class="fw_b no_w"><gsmsg:write key="ntp.172" /></th>
                    </tr>

                    <logic:notEmpty name="cht070Form" property="cht070LogCountList">
                      <logic:iterate name="cht070Form" property="cht070LogCountList" id="data">

                        <tr class="">
                          <td class="txt_c"><bean:write name="data" property="dspDate" /></td>
                          <td class="txt_r"><bean:write name="data" property="strUserNum" /></td>
                          <td class="txt_r"><bean:write name="data" property="strGroupNum" /></td>
                          <td class="txt_r"><bean:write name="data" property="strSumNum" /></td>
                          <td class="txt_r"><bean:write name="data" property="strSendNum" /></td>
                        </tr>
                      </logic:iterate>

                      <tr class="">
                        <td class="txt_c fw_b"><gsmsg:write key="cmn.sum" /></td>
                        <td class="txt_r fw_b"><bean:write name="cht070Form" property="cht070TotalMessageUser" /></td>
                        <td class="txt_r fw_b"><bean:write name="cht070Form" property="cht070TotalMessageGroup" /></td>
                        <td class="txt_r fw_b"><bean:write name="cht070Form" property="cht070TotalMessage" /></td>
                        <td class="txt_r fw_b"><bean:write name="cht070Form" property="cht070TotalSend" /></td>
                      </tr>
                    </logic:notEmpty>

                  </table>

                </td>
              </tr>

              <!-- ページング -->
              <tr>
                <td>
                  <bean:size id="pageCount2" name="cht070Form" property="cht070PageLabel" scope="request" />
                  <logic:greaterThan name="pageCount2" value="1">
                  <div class="txt_r paging">
                    <button type="button" class="webIconBtn" class="baseBtn" href="#!" onClick="buttonPush('pageLast');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <logic:notEmpty name="cht070Form" property="cht070PageLabel" scope="request">
                    <html:select property="cht070DspPage2" onchange="changePage(this);" styleClass="paging_combo">
                      <html:optionsCollection name="cht070Form" property="cht070PageLabel" value="value" label="label" />
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

          </td>
        </tr>
      </table>
    </div>
  </div>
</div>


<span id="tooltip_area"></span>

</html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>