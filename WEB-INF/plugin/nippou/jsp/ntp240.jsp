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

<title>GROUPSESSION <gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.setting" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>"></script>
<!--[if IE]><script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js"></script><![endif]-->
<script src="../common/js/jplot/jquery.jqplot.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.barRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.highlighter.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp240.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>
<html:form action="/nippou/ntp240">

<input type="hidden" name="CMD" value="">
<html:hidden property="cmd" />
<html:hidden property="dspMod" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--　BODY -->
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
      <gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.setting" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
  <div class="display_inline w100 bor1 bgC_body">
    <div class="w20 m0 p0 ">
      <table class="w100 h100">
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush2('nippou');">
          <td class="ntp_menuIcon w5">
            <img class="header_pluginImg-classic ml5" src="../nippou/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="ntp.1" />">
            <img class="header_pluginImg ml5" src="../nippou/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="ntp.1" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush2('anken');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="ntp.11" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken_32.png" alt="<gsmsg:write key="ntp.11" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.11" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu-select bgC_Body txt_l borC_body borC_body borBottomC_light borTopC_light">
          <td class="ntp_menuIcon-select  w5">
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
        <logic:equal name="ntp240Form" property="adminKbn" value="1">
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
          <td class="nippou_menuArea h100">&nbsp;</td>
          <td class="nippou_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <div class="w100 no_w">
        <logic:notEmpty name="ntp240Form" property="ntp240DspTargetList" scope="request">
          <logic:iterate id="trgDspMdl" name="ntp240Form" property="ntp240DspTargetList" scope="request" indexId="idx">
            <% if (idx.intValue() == 0) { %>
              <div class="w100 verAlignMid">
                <html:select property="ntp240Year" styleId="selYearsf" onchange="changeCmb();">
                  <html:optionsCollection name="ntp240Form" property="ntp240YearLabel" value="value" label="label" />
                </html:select>
                <html:select property="ntp240Month" styleId="selMonthsf" onchange="changeCmb();" styleClass="ml5">
                  <html:optionsCollection name="ntp240Form" property="ntp240MonthLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn ml10" onclick="prevMonth();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth" />" onclick="thisMonth();">
                  <gsmsg:write key="cmn.thismonth" />
                </button>
                <span>
                  <a href="#" class="fw_b todayBtn original-display" onclick="thisMonth();">
                    <gsmsg:write key="cmn.thismonth" />
                  </a>
                </span>
                <button type="button" class="webIconBtn" onclick="nextMonth();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
                <logic:notEmpty name="ntp240Form" property="ntp240GpLabelList" scope="request">
                  <html:select property="ntp240DspGpSid" onchange="changeGroupCombo();" styleClass="ml20 wp250">
                    <logic:iterate id="gpBean" name="ntp240Form" property="ntp240GpLabelList" scope="request">
                      <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                      <logic:equal name="gpBean" property="styleClass" value="0">
                        <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                      </logic:equal>
                      <logic:notEqual name="gpBean" property="styleClass" value="0">
                        <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                      </logic:notEqual>
                    </logic:iterate>
                  </html:select>
                  <button type="button" class="iconBtn-border ml5" value="Cal" onclick="openGroupWindow(this.form.ntp240DspGpSid, 'ntp240DspGpSid', '0');" id="ntp240GroupBtn">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                  </button>
                </logic:notEmpty>
                <logic:empty name="ntp240Form" property="ntp240GpLabelList" scope="request">
                  <span class="fw_b"><bean:write name="ntp240Form" property="ntp240UserName" /></span>
                </logic:empty>
                <logic:notEmpty name="ntp240Form" property="ntp240UserLabel" scope="request">
                  <html:select property="ntp240SelectUsrSid" onchange="changeCmb();" styleClass="ml5 wp200">
                     <logic:iterate id="user" name="ntp240Form" property="ntp240UserLabel" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                      <html:option styleClass="<%= user.getCSSClassNameOption() %>" value="<%= user.getValue() %>"><bean:write name="user" property="label" /></html:option>
                     </logic:iterate>
                  </html:select>
                </logic:notEmpty>
              </div>
            <% } else { %>
              <div class="ml5 txt_l w100 txt_b mt20"><bean:write name="trgDspMdl" property="year" />年<bean:write name="trgDspMdl" property="month" />月</div>
            <% } %>
            <table class="table-top w100">
              <tr>
                <th class="w25">
                  <gsmsg:write key="ntp.12" />
                </th>
                <th class="w75" colspan="2">
                  <bean:write name="trgDspMdl" property="year" />年<bean:write name="trgDspMdl" property="month" />月&nbsp;<gsmsg:write key="ntp.139" />
                </th>
              </tr>
              <logic:notEmpty name="trgDspMdl" property="ntgList">
                <logic:iterate id="trgUsrMdl" name="trgDspMdl" property="ntgList" indexId="idx2">
                  <% String recordColor = ""; %>
                  <% String barGraphClass = ""; %>

                  <logic:equal name="trgUsrMdl" property="npgTargetKbn" value="0">
                  <% barGraphClass = "bgC_ntpBarParcent"; %>
                  </logic:equal>

                  <logic:equal name="trgUsrMdl" property="npgTargetKbn" value="1">
                  <% barGraphClass = "bgC_ntpBarParcentComp"; %>
                  <% recordColor = "cl_fontWarn"; %>
                  </logic:equal>
                  <tr id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                    <td class="txt_l w25 cursor_p js_targetPopLink cl_linkDef">
                      <bean:write name="trgUsrMdl" property="npgTargetName" />
                    </td>
                    <td class="no_w border_right_none">
                      <div class="display_inline wp300 bor1 borC_light pos_rel">
                        <div class="cl_white pos_abs w100 txt_c">
                          <span id="ratioStr_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />"><bean:write name="trgUsrMdl" property="npgTargetRatioStr" />%</span>
                        </div>
                        <div id="barTargetRatio_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />" style="width:<bean:write name="trgUsrMdl" property="npgTargetRatio" />%;" class="<%= barGraphClass %>">
                          &nbsp;
                        </div>
                        <div id="barTargetUnRatio_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />" style="width:<bean:write name="trgUsrMdl" property="npgTargetUnRatio" />%;" class="bgC_darkGray">
                          &nbsp;
                        </div>
                      </div>
                    </td>
                    <td class="w40 border_left_none txt_r">
                      <div class="js_target_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                        <span class="fw_b <%= recordColor %>" id="spanRecord_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                          <bean:write name="trgUsrMdl" property="npgRecord" />
                        </span>
                        /
                        <span id="spanTarget_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                          <bean:write name="trgUsrMdl" property="npgTarget" />
                        </span>
                        <bean:write name="trgUsrMdl" property="npgTargetUnit" />
                        <button type="button" class="baseBtn js_targetSetteiBtn" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                          <gsmsg:write key="cmn.change" />
                        </button>
                      </div>
                      <div class="display_n js_editTarget_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                        <input class="wp60" name="npgRecord" maxlength="15" value="<bean:write name="trgUsrMdl" property="npgRecord" />" id="trgRecord_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />" type="text">
                        /
                        <input class="wp60" name="npgTarget" maxlength="15" value="<bean:write name="trgUsrMdl" property="npgTarget" />" id="trgTarget_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />" type="text">
                        <bean:write name="trgUsrMdl" property="npgTargetUnit" />
                        <button type="button" class="baseBtn js_targetKakuteiBtn" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                          <gsmsg:write key="cmn.change" />
                        </button>
                        <button type="button" class="baseBtn js_targetCanselBtn" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="ntp240Form" property="ntp240SelectUsrSid" />_<bean:write name="trgUsrMdl" property="ntgSid" />">
                          <gsmsg:write key="cmn.cancel" />
                        </button>
                      </div>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </table>
          </logic:iterate>
        </logic:notEmpty>
        <logic:empty name="ntp240Form" property="ntp240DspTargetList" scope="request">
          <div class="w100 verAlignMid">
            <html:select property="ntp240Year" styleId="selYearsf" onchange="changeCmb();">
              <html:optionsCollection name="ntp240Form" property="ntp240YearLabel" value="value" label="label" />
            </html:select>
            <html:select property="ntp240Month" styleId="selMonthsf" onchange="changeCmb();" styleClass="ml5">
              <html:optionsCollection name="ntp240Form" property="ntp240MonthLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onclick="prevMonth();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth" />" onclick="thisMonth();">
              <gsmsg:write key="cmn.thismonth" />
            </button>
            <span>
              <a href="#" class="fw_b todayBtn original-display" onclick="thisMonth();">
                <gsmsg:write key="cmn.thismonth" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onclick="nextMonth();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <logic:notEmpty name="ntp240Form" property="ntp240GpLabelList" scope="request">
              <html:select property="ntp240DspGpSid" onchange="changeGroupCombo();" styleClass="ml20 wp250">
                <logic:iterate id="gpBean" name="ntp240Form" property="ntp240GpLabelList" scope="request">
                  <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                    <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:notEqual name="gpBean" property="styleClass" value="0">
                    <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:notEqual>
                </logic:iterate>
              </html:select>
              <button type="button" class="iconBtn-border ml5" value="Cal" onclick="openGroupWindow(this.form.ntp240DspGpSid, 'ntp240DspGpSid', '0');" id="ntp240GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </logic:notEmpty>
            <logic:empty name="ntp240Form" property="ntp240GpLabelList" scope="request">
              <span class="fw_b"><bean:write name="ntp240Form" property="ntp240UserName" /></span>
            </logic:empty>
            <logic:notEmpty name="ntp240Form" property="ntp240UserLabel" scope="request">
              <html:select property="ntp240SelectUsrSid" onchange="changeCmb();" styleClass="ml5 wp200">
                 <logic:iterate id="user" name="ntp240Form" property="ntp240UserLabel" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                  <html:option styleClass="<%= user.getCSSClassNameOption() %>" value="<%= user.getValue() %>"><bean:write name="user" property="label" /></html:option>
                 </logic:iterate>
              </html:select>
            </logic:notEmpty>
          </div>
          <div class="txt_c mt10">
            <gsmsg:write key="ntp.140" />
          </div>
        </logic:empty>
      </div>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>



<div id="targetPop" title="<gsmsg:write key="ntp.139" />" class="mt10" style="display:none;">
  <div class="display_inline w100">
    <div class="w30 txt_l fw_b fs_16">
      <span id="popTrgYear"></span>
      <gsmsg:write key="ntp.168" />
      <span id="popTrgUsr" class="ml20"></span>
    </div>
    <div class="w40 txt_c fw_b fs_16">
      <span id="popTrgTarget">
    </div>
    <div class="w30 txt_r">
      <div class="verAlignMid">
        <button type="button" class="webIconBtn" id="popPrevBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left "></i>
        </button>
        <button type="button" class="baseBtn todayBtn cl_fontBody classic-display" id="popThisYearBtn" value="<gsmsg:write key="cmn.thismonth" />" onclick="thisMonth();">
          <gsmsg:write key="ntp.169" />
        </button>
        <span>
          <a href="#" class="fw_b todayBtn cl_fontBody original-display" id="popThisYearBtn">
            <gsmsg:write key="ntp.169" />
          </a>
        </span>
        <button type="button" class="webIconBtn" id="popNextBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right "></i>
        </button>
        <input type="hidden" id="popHideYear" value="" />
        <input type="hidden" id="popHideMonth" value="" />
        <input type="hidden" id="popHideUsrSid" value="" />
        <input type="hidden" id="popHideNtgSid" value="" />
      </div>
    </div>
  </div>
  <div id="yearTargetArea" class="w100">
    <span id="yearTargetDataArea" class="ofx_s"></span>
  </div>
  <table style="border-collapse:collapse;" class="w100">
    <tr>
      <td align="center"><u><span class="fs_14"><gsmsg:write key="ntp.141" />：</span><span class="fs_16 fw_b" id="popTrgRatio"></span>%</u></td>
    </tr>
    <tr>
      <td class="txt_l pl0 pb0">
        <input type="button" class="graph-btn-active pl0 pb0 border_right_none" id="changeLineGraph" value="<gsmsg:write key="ntp.142" />"/><input type="button" class="graph-btn-unactive pl0 pb0" id="changeBarGraph" value="<gsmsg:write key="ntp.143" />"/>
      </td>
    </tr>
    <tr>
      <td align="center" class="graph_top">
        <span id="line_title" class="graph_title"><gsmsg:write key="ntp.142" />(%)/<gsmsg:write key="cmn.month" /></span>
        <span id="bar_title" class="graph_title"><gsmsg:write key="ntp.143" />(<span id="bar_unit"></span>)/<gsmsg:write key="cmn.month" /></span>
      </td>
    </tr>
    <tr>
      <td  class="graph_bottom" align="center" id="graph_area">
        <div id="linechart" class="ntp_graphArea"></div>
        <div id="barchart" class="ntp_graphArea"></div>
      </td>
   </tr>
  </table>
</div>

<div id="dialogEditOk" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="ntp.145" />
      </td>
    </tr>
  </table>
</div>

<div id="dialog_error" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14" id="error_msg">
      </td>
    </tr>
  </table>
</div>

</html:form>
</body>
</html:html>