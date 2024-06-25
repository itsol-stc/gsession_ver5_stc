<%@page import="jp.groupsession.v2.cmn.GSConstTimecard"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.tcd.model.TcdTotalValueModel" %>
<%@ page import="jp.groupsession.v2.tcd.model.TcdHolidayInfModel" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd010.js?<%= GSConst.VERSION_PARAM %>"></script>
  <gsjsmsg:js filename="gsjsmsg.js"/>
  <title>GROUPSESSION <gsmsg:write key="tcd.tcd010.17" /></title>
</head>

<body>

<html:form action="/timecard/tcd010">
<html:hidden property="tcdBackScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="editDay" />
<html:hidden property="dakokuStrSetFlg" value="0"/>
<html:hidden property="dakokuEndSetFlg" value="0"/>
<input type="hidden" name="CMD" value="init">

<logic:equal name="tcd010Form" property="usrKbn" value="0">
<html:hidden property="sltGroupSid" />
<html:hidden property="usrSid" />
</logic:equal>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../timecard/images/classic/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
      <img class="header_pluginImg" src="../timecard/images/original/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
    </li>
    <li><gsmsg:write key="tcd.50" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="tcd.tcd010.18" />
    </li>
    <li>
      <div>
        <logic:notEqual name="tcd010Form" property="usrKbn" value="0">
          <button type="button" class="baseBtn" value="<gsmsg:write key="tcd.tcd010.13" />" onClick="buttonPush('mng');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_syorui.gif" alt="<gsmsg:write key="tcd.tcd010.13" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png" alt="<gsmsg:write key="tcd.tcd010.13" />">
            <gsmsg:write key="tcd.tcd010.13" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="tcd.tcd030.13" />" onClick="buttonPush('yuukyuList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_flag.png" alt="<gsmsg:write key="tcd.tcd030.13" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_flag.png" alt="<gsmsg:write key="tcd.tcd030.13" />">
            <gsmsg:write key="tcd.tcd030.13" />
          </button>
        </logic:notEqual>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper">
  <div class="wrapper_2column">
    <div class="side-left wp320 border_none mr20 side_left-basis bgc_transparent">
      <logic:notEmpty name="tcd010Form" property="warnMessage">
        <div class="txt_l cl_fontWarn borColor-Warn bor1 plr3 hp60 warn-area bgC_body">
          <span class="warn-message"><bean:write name="tcd010Form" property="warnMessage"/></span>
        </div>
      </logic:notEmpty>
      <logic:empty name="tcd010Form" property="warnMessage">
        <div class="hp60 border_none">
          <bean:write name="tcd010Form" property="warnMessage"/>
        </div>
      </logic:empty>

      <logic:notEmpty name="tcd010Form" property="tcd010UsrLabelList">
        <!--勤怠集計-->
          <table class="table-top bgC_tableCell">
            <tr>
              <th class="table_title-color fs_18 txt_c" colspan="6"><gsmsg:write key="tcd.tcd010.13" /></th>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b" colspan="2" rowspan="2">&nbsp;</td>
              <td class="bgC_lightGray txt_c fw_b" colspan="2"><bean:write name="tcd010Form" property="lastMonthString" /><gsmsg:write key="cmn.month" /></td>
              <td class="bgC_lightGray txt_c fw_b" colspan="2"><bean:write name="tcd010Form" property="thisMonthString" /><gsmsg:write key="cmn.month" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b no_w">&nbsp;&nbsp;<gsmsg:write key="cmn.performance" />&nbsp;&nbsp;</td>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.tcd010.15" /></td>
              <td class="bgC_lightGray txt_c fw_b no_w">&nbsp;&nbsp;<gsmsg:write key="cmn.performance" />&nbsp;&nbsp;</td>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.tcd010.15" /></td>
            </tr>
            <bean:define id="lastMdl" name="tcd010Form" property="lastMonthMdl" type="TcdTotalValueModel"/>
            <bean:define id="thisMdl" name="tcd010Form" property="thisMonthMdl" type="TcdTotalValueModel"/>
            <bean:define id="totalYearMdl" name="tcd010Form" property="totalYearMdl" type="TcdTotalValueModel"/>
            <tr>
              <td rowspan="2" class="bgC_lightGray txt_c fw_b"><gsmsg:write key="tcd.tcd010.16" /></td>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.working.day" /></td>
              <td class="bgC_tableCell txt_r w10"><bean:write name="lastMdl" property="kadoDaysStr" /></td>
              <td class="bgC_tableCell txt_r w10"><bean:write name="lastMdl" property="kadoBaseDaysStr" /></td>
              <td class="bgC_tableCell txt_r w10"><bean:write name="thisMdl" property="kadoDaysStr" /></td>
              <td class="bgC_tableCell txt_r w10"><bean:write name="thisMdl" property="kadoBaseDaysStr" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.running.time" /></td>
              <td class="bgC_tableCell txt_r"><bean:write name="lastMdl" property="kadoHoursStr" /></td>
              <td class="bgC_tableCell txt_r"><bean:write name="lastMdl" property="kadoBaseHoursStr" /></td>
              <td class="bgC_tableCell txt_r"><bean:write name="thisMdl" property="kadoHoursStr" /></td>
              <td class="bgC_tableCell txt_r"><bean:write name="thisMdl" property="kadoBaseHoursStr" /></td>
            </tr>
            <tr>
              <td rowspan="2" class="bgC_lightGray txt_c fw_b no_w w15"><gsmsg:write key="tcd.tcd010.09" /></td>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.working.day" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="zangyoDaysStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="zangyoDaysStr" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.running.time" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="zangyoHoursStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="zangyoHoursStr" /></td>
            </tr>
            <tr>
              <td rowspan="2" class="bgC_lightGray txt_c fw_b no_w w15"><gsmsg:write key="tcd.tcd010.06" /></td>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.working.day" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="sinyaDaysStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="sinyaDaysStr" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.running.time" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="sinyaHoursStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="sinyaHoursStr" /></td>
            </tr>
            <tr>
              <td rowspan="2" class="bgC_lightGray txt_c fw_b no_w w15"><gsmsg:write key="tcd.tcd010.14" /></td>
              <td class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.working.day" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="kyusyutuDaysStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="kyusyutuDaysStr" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray no_w txt_c fw_b"><gsmsg:write key="tcd.running.time" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="kyusyutuHoursStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="kyusyutuHoursStr" /></td>
            </tr>
            <tr>
              <td colspan="2" class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.18" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="chikokuTimesStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="chikokuTimesStr" /></td>
            </tr>
            <tr>
              <td colspan="2" class="bgC_lightGray txt_c fw_b no_w"><gsmsg:write key="tcd.22" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="lastMdl" property="soutaiTimesStr" /></td>
              <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="thisMdl" property="soutaiTimesStr" /></td>
            </tr>
            <logic notEmpty name="tcd010Form" property="holList">
              <logic:iterate id="holData" name="tcd010Form" property="holList" type="TcdHolidayInfModel">
                <tr>
                  <td colspan="2" class="bgC_lightGray txt_c fw_b"><bean:write name="holData" property="thiName" /></td>
                  <td class="bgC_tableCell txt_r" colspan="2">
                    <%= lastMdl.getHolDaysStr(holData.getThiSid()) %>
                  </td>
                  <td class="bgC_tableCell txt_r" colspan="2">
                    <%= thisMdl.getHolDaysStr(holData.getThiSid()) %>
                  </td>
                </tr>
              </logic:iterate>
            </logic_notEmpty>
          </table>

          <!--月別集計-->
          <table class="table-top w100">
            <tr>
              <th class="table_title-color fs_18 txt_c" colspan="7"><gsmsg:write key="tcd.tcd010.11" /></th>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b w25"></td>
              <td class="bgC_lightGray txt_c fw_b w25" colspan="2"><gsmsg:write key="tcd.tcd010.16" /></td>
              <td class="bgC_lightGray txt_c fw_b w25" colspan="2"><gsmsg:write key="tcd.tcd010.09" /></td>
              <td class="bgC_lightGray txt_c fw_b w25" colspan="2"><gsmsg:write key="tcd.tcd010.05" /></td>
            </tr>
            <logic:notEmpty name="tcd010Form" property="monthTtlList" scope="request">
              <logic:iterate id="monthTtlMdl" name="tcd010Form" property="monthTtlList" scope="request" indexId="cnt">
                <tr>
                  <td class="bgC_lightGray txt_c fw_b"><bean:write name="monthTtlMdl" property="kadoMonth" /><gsmsg:write key="cmn.month" /></td>
                  <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="monthTtlMdl" property="kadoHoursStr" /></td>
                  <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="monthTtlMdl" property="zangyoHours" /></td>
                  <td class="bgC_tableCell txt_r" colspan="2"><bean:write name="monthTtlMdl" property="sinyaHours" /></td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
          </table>

          <!--年度集計-->
          <table class="table-top w100">
            <tr>
              <th class="table_title-color w20 txt_c no_w" colspan="3">
                <div class="txt_l fs_18 flo_l">
                  <gsmsg:write key="tcd.tcd010.03" />
                </div>
                <div class="txt_r flo_r">
                  <bean:define id="yr" name="tcd010Form" property="nendYear" type="java.lang.String" />
                  <bean:define id="yr2" name="tcd010Form" property="endYear" type="java.lang.String" />
                  <gsmsg:write key="cmn.year" arg0="<%= yr %>" />&nbsp;<bean:write name="tcd010Form" property="kishuMonth" /><gsmsg:write key="cmn.month" />&nbsp;<gsmsg:write key="tcd.153" />&nbsp;<gsmsg:write key="cmn.year" arg0="<%= yr2 %>" />&nbsp;<bean:write name="tcd010Form" property="kimatuMonth" /><gsmsg:write key="cmn.month" /></span>
                </div>
              </th>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b w30 no_w" colspan="2"><gsmsg:write key="tcd.18" /></td>
              <td class="bgC_tableCell txt_r w70"><bean:write name="totalYearMdl" property="chikokuTimesStr" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b" colspan="2"><gsmsg:write key="tcd.22" /></td>
              <td class="bgC_tableCell txt_r"><bean:write name="totalYearMdl" property="soutaiTimesStr" /></td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b" colspan="2"><gsmsg:write key="tcd.34" /></td>
              <td class="txt_r">
                <bean:write name="totalYearMdl" property="kekkinDaysStr" />
              </td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b w10" rowspan="2"><gsmsg:write key="tcd.03" /></td>
              <td class="bgC_lightGray txt_c fw_b w20"><gsmsg:write key="tcd.tcd010.20" /></td>
              <td class="bgC_tableCell txt_r">
                <bean:write name="totalYearMdl" property="yuukyuDaysStr" />
              </td>
            </tr>
            <tr>
              <td class="bgC_lightGray txt_c fw_b w20"><gsmsg:write key="tcd.tcd010.21" /></td>
              <td class="bgC_tableCell txt_r">
                <bean:write name="tcd010Form" property="tcd010YukyuZan" />
              </td>
            </tr>
          </table>
      </logic:notEmpty>
    </div>

    <div class="main timcard_list_right">
      <div>
        <logic:messagesPresent message="false">
          <html:errors/>
        </logic:messagesPresent>
      </div>
      <div class="txt_l hp60">
        <logic:notEqual name="tcd010Form" property="usrKbn" value="0">
          <div class="txt_l">
            <gsmsg:write key="cmn.show.group" />
            <html:select property="sltGroupSid" onchange="changeGroupCombo();" styleClass="wp200">
              <html:optionsCollection name="tcd010Form" property="tcd010GpLabelList" value="value" label="label" />
            </html:select>
            <button class="iconBtn-border mr10" type="button"  onclick="openGroupWindow(this.form.sltGroupSid, 'sltGroupSid', '0')" value="&nbsp;&nbsp;" id="tcd010GroupBtn" >
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
            <html:select property="usrSid" onchange="changeUserCombo();" styleClass="wp200">
              <logic:iterate id="user" name="tcd010Form" property="tcd010UsrLabelList" type="UsrLabelValueBean">
                <html:option value="<%=user.getValue() %>" styleClass="<%=user.getCSSClassNameOption() %>"><bean:write name="user" property="label"/></html:option>
              </logic:iterate>
            </html:select>
          </div>
        </logic:notEqual>

        <logic:notEmpty name="tcd010Form" property="tcd010UsrLabelList">
          <div class="w100 mt10">
            <div class="txt_l no_w flo_l mr10">
                <logic:equal name="tcd010Form" property="tcd010LockFlg" value="0">
                  <logic:equal name="tcd010Form" property="tcd010FailFlg" value="0">
                    <button type="button" class="baseBtn" onClick="multiEditButton();" value="<gsmsg:write key="cmn.multiple.edit" />">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.multiple.edit" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.multiple.edit" />">
                      <gsmsg:write key="cmn.multiple.edit" />
                    </button>
                    <button type="button" class="baseBtn" onClick="buttonPush('del');"  value="<gsmsg:write key="cmn.delete" />">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                      <gsmsg:write key="cmn.delete" />
                    </button>
                  </logic:equal>
                </logic:equal>
                <logic:equal name="tcd010Form" property="tcd010FailFlg" value="0">
                  <logic:equal name="tcd010Form" property="kinmuOut" value="0">
                    <button tsype="button" class="baseBtn" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('xls');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                      <gsmsg:write key="tcd.tcd010.12" />
                    </button>
                  </logic:equal>
                  <logic:equal name="tcd010Form" property="kinmuOut" value="1">
                    <button tsype="button" class="baseBtn" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('pdf');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                      <gsmsg:write key="tcd.tcd010.12" />
                    </button>
                  </logic:equal>
                  <button tsype="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onClick="buttonPush('csv');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                    <gsmsg:write key="cmn.export" />
                  </button>
                  <logic:equal name="tcd010Form" property="csvImportFlg" value="0">
                    <button type="button" class="baseBtn" onClick="buttonPush('imp');" value="<gsmsg:write key="cmn.import" />">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                      <gsmsg:write key="cmn.import" />
                    </button>
                  </logic:equal>
                </logic:equal>
              </div>

              <div class="txt_r no_w flo_r">
                <span class="verAlignMid">
                  <button type="button" class="webIconBtn" onClick="buttonPush('move_last');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="tcd.tcd010.04" />">
                    <i class="icon-paging_left"></i>
                  </button>
                  <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth3" />" onClick="buttonPush('move_now');">
                    <gsmsg:write key="cmn.thismonth3" />
                  </button>
                  <span>
                    <a href="#" class="fw_b todayBtn original-display" onClick="buttonPush('move_now');">
                      <gsmsg:write key="cmn.thismonth3" />
                    </a>
                  </span>
                  <button type="button" class="webIconBtn" onClick="buttonPush('move_next');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.change.nextmonth" />">
                    <i class="icon-paging_right"></i>
                  </button>
                  <button type="button" value="Cal" onclick="wrtCalendarByBtn(this.form.tcdDspFrom, 'tcd010CalBtn')" id="tcd010CalBtn" class="iconBtn-border ml5">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png">
                  </button>
                </span>
              </div>
          </div>
        </logic:notEmpty>
      </div>


       <div>
         <!--一覧表-->
         <table class="table-top bgC_tableCell">
           <tr>
             <th colspan="9" class="table_title-color fs_18 txt_l">
               <bean:define id="yr2" name="tcd010Form" property="year" type="java.lang.String" />
               <gsmsg:write key="cmn.year" arg0="<%= yr2 %>" /><bean:write name="tcd010Form" property="month" /><gsmsg:write key="cmn.month" />
             </th>
           </tr>
           <% String[] tdCols = new String[] {"1", "2"}; %>
           <% String tddayCols = ""; %>
           <% String tdbikoCols = ""; %>
           <% tdbikoCols = tdCols[0]; %>
           <logic:equal name="tcd010Form" property="tcd010FailFlg" value="0">
             <% tddayCols = tdCols[0]; %>
           </logic:equal>
           <logic:equal name="tcd010Form" property="tcd010FailFlg" value="1">
             <% tddayCols = tdCols[1]; %>
           </logic:equal>
           <bean:define id="dayCols" value="<%= tddayCols %>" />
           <bean:define id="bikoCols" value="<%= tdbikoCols %>" />
           <tr>
             <logic:equal name="dayCols" value="1" scope="page">
               <td colspan="1" class="bgC_lightGray wp25 txt_c js_tableTopCheck js_tableTopCheck-header cursor_p"><input type="checkbox" name="allChk" onClick="changeChk();"></td>
               <td colspan="1" class="bgC_lightGray wp70 txt_c fw_b"><gsmsg:write key="cmn.date2" /></td>
             </logic:equal>
             <logic:equal name="dayCols" value="2" scope="page">
               <td class="bgC_lightGray fw_b wp70" colspan="2"><gsmsg:write key="cmn.date2" /></td>
             </logic:equal>
             <td class="bgC_lightGray wp70 txt_c fw_b no_w"><gsmsg:write key="tcd.21" /><br><gsmsg:write key="tcd.tcd010.08" /></td>
             <td class="bgC_lightGray wp100 txt_c fw_b no_w"><gsmsg:write key="tcd.28" /></td>
             <td class="bgC_lightGray wp100 txt_c fw_b no_w"><gsmsg:write key="tcd.24" /></td>
             <td class="bgC_lightGray wp70 txt_c fw_b no_w"><gsmsg:write key="tcd.184" /></td>
             <td class="bgC_lightGray txt_c fw_b no_w" colspan="3"><gsmsg:write key="cmn.memo" /></td>
           </tr>
           <logic:notEmpty name="tcd010Form" property="tcd010List" scope="request">
             <logic:iterate id="tdMdl" name="tcd010Form" property="tcd010List" scope="request" indexId="cnt">
               <bean:define id="tdColor" value="" />
               <bean:define id="week" name="tdMdl" property="tcd010Week" type="java.lang.Integer" />
               <% String[] tdColors = new String[] {"bgC_calSunday", "bgC_tableCell", "bgC_tableCell", "bgC_tableCell", "bgC_tableCell", "bgC_tableCell", "bgC_calSaturday"}; %>
               <% tdColor = tdColors[(week.intValue()-1)]; %>
               <tr>
                 <logic:equal name="dayCols" value="1" scope="page">
                   <td class="<%= tdColor %> txt_c js_tableTopCheck cursor_p">
                     <bean:define id="day" name="tdMdl" property="tcd010Day" type="java.lang.Integer" />
                     <html:multibox property="selectDay" value="<%= Integer.toString(day.intValue()) %>"/>
                   </td>
                 </logic:equal>
                 <!--休日以外-->
                 <logic:equal name="tdMdl" property="holKbn" value="0">
                   <logic:equal name="tdMdl" property="tcd010Week" value="1">
                     <td class="<%= tdColor %> no_w fs_13 txt_c" colspan="<%= dayCols %>">
                       <span class="cl_fontSunday fw_b"><bean:write name="tdMdl" property="tcd010Day" /><gsmsg:write key="cmn.day" />(<bean:write name="tdMdl" property="tcd010WeekStr" />) </span>
                     </td>
                   </logic:equal>
                   <logic:equal name="tdMdl" property="tcd010Week" value="7">
                     <td class="<%= tdColor %> no_w fs_13 txt_c" colspan="<%= dayCols %>">
                       <span class="cl_fontSaturday fw_b"><bean:write name="tdMdl" property="tcd010Day" /><gsmsg:write key="cmn.day" />(<bean:write name="tdMdl" property="tcd010WeekStr" />) </span>
                     </td>
                   </logic:equal>
                   <logic:notEqual name="tdMdl" property="tcd010Week" value="1">
                     <logic:notEqual name="tdMdl" property="tcd010Week" value="7">
                       <td class="no_w <%= tdColor %> fs_13 txt_c" colspan="<%= dayCols %>">
                         <span class="fw_b"><bean:write name="tdMdl" property="tcd010Day" /><gsmsg:write key="cmn.day" />(<bean:write name="tdMdl" property="tcd010WeekStr" />) </span>
                       </td>
                     </logic:notEqual>
                   </logic:notEqual>
                 </logic:equal>
                 <!--休日-->
                 <logic:notEqual name="tdMdl" property="holKbn" value="0">
                   <td class="<%= tdColor %> no_w fs_13 txt_c" colspan="<%= dayCols %>">
                     <span class="cl_fontSunday fw_b"><bean:write name="tdMdl" property="tcd010Day" /><gsmsg:write key="cmn.day" />(<bean:write name="tdMdl" property="tcd010WeekStr" />) </span>
                   </td>
                 </logic:notEqual>
                 <td class="<%= tdColor %> txt_c fs_13"><bean:write name="tdMdl" property="tcd010StrikeShigyouStr"/>
                   <br>
                   <logic:empty name="tdMdl" property="tcd010StrikeSyugyouStr">&nbsp;</logic:empty>
                   <logic:notEmpty name="tdMdl" property="tcd010StrikeSyugyouStr"><bean:write name="tdMdl" property="tcd010StrikeSyugyouStr" /></logic:notEmpty>
                 </td>
                 <td class="<%= tdColor %> txt_c no_w">
                   <bean:write name="tdMdl" property="tcd010ShigyouStr" />
                   <logic:equal name="tcd010Form" property="myselfFlg" value="true">
                     <logic:equal name="tdMdl" property="dakokuBtnStrKbn" value="1">
                       <button type="button" class="baseBtn" onClick="editStrDakokuButton('<bean:write name="tdMdl" property="tcd010Day" />');" value="<gsmsg:write key="tcd.tcdmain.06" />">
                         <gsmsg:write key="tcd.tcdmain.06" />
                       </button>
                     </logic:equal>
                   </logic:equal>
                 </td>
                 <td class="<%= tdColor %> txt_c no_w">
                   <bean:write name="tdMdl" property="tcd010SyugyouStr" />
                   <logic:equal name="tcd010Form" property="myselfFlg" value="true">
                     <logic:equal name="tdMdl" property="dakokuBtnEndKbn" value="1">
                       <button type="button" class="baseBtn" onClick="editEndDakokuButton('<bean:write name="tdMdl" property="tcd010Day" />');" value="<gsmsg:write key="tcd.tcdmain.05" />">
                         <gsmsg:write key="tcd.tcdmain.05" />
                       </button>
                     </logic:equal>
                   </logic:equal>
                 </td>
                 <td class="<%= tdColor %>">
                   <bean:write name="tdMdl" property="tcd010TimezoneName" />
                 </td>
                 <td class="<%= tdColor %> txt_l wp75 holidayName">
                   <bean:write name="tdMdl" property="tcd010Kyujitsu" />
                 </td>
                 <td class="<%= tdColor %> txt_l" colspan="<%= bikoCols %>">
                   <logic:notEmpty name="tdMdl" property="holName">
                     <span class="cl_fontSunday fs_13"><bean:write name="tdMdl" property="holName" /></span>
                   </logic:notEmpty>
                   <bean:write name="tdMdl" property="tcd010Bikou" />
                 </td>
                 <td class="<%= tdColor %> txt_c no_w wp60">
                   <logic:equal name="tcd010Form" property="tcd010FailFlg" value="0">
                     <button type="button" class="baseBtn" name="btnChange" value="<gsmsg:write key="cmn.change" />" onClick="editButton('<bean:write name="tdMdl" property="tcd010Day" />');">
                       <span class="fs_13"><gsmsg:write key="cmn.change" /></span>
                     </button>
                   </logic:equal>
                   <logic:notEqual name="tcd010Form" property="tcd010FailFlg" value="0">
                     <logic:equal name="tdMdl" property="failFlg" value="1">
                       <button type="button" class="baseBtn" name="btnChange" value="<gsmsg:write key="cmn.change" />" onClick="editButton('<bean:write name="tdMdl" property="tcd010Day" />');">
                         <span class="fs_13"><gsmsg:write key="cmn.change" /></span>
                       </button>
                     </logic:equal>
                   </logic:notEqual>
                 </td>
               </tr>
             </logic:iterate>
           </logic:notEmpty>
         </table>
         <!--一覧表ここまで-->

          <logic:notEmpty name="tcd010Form" property="tcd010UsrLabelList">
            <div class="mt10 w100">
              <div class="txt_r no_w flo_r">
                  <div class="verAlignMid">
                    <button type="button" class="webIconBtn" onClick="buttonPush('move_last');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="tcd.tcd010.04" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth3" />" onClick="buttonPush('move_now');">
                      <gsmsg:write key="cmn.thismonth3" />
                    </button>
                    <span>
                      <a href="#" class="fw_b todayBtn original-display" onClick="buttonPush('move_now');">
                        <gsmsg:write key="cmn.thismonth3" />
                      </a>
                    </span>
                    <button type="button" class="webIconBtn" onClick="buttonPush('move_next');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.change.nextmonth" />">
                      <i class="icon-paging_right"></i>
                    </button>
                    <button type="button" value="Cal" onclick="wrtCalendarByBtn(this.form.tcdDspFrom, 'tcd010CalBtn')" id="tcd010CalBtn" class="iconBtn-border ml5">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png">
                    </button>
                  </div>
                </div>
              <div class="txt_l no_w flo_l mr10">
                  <logic:equal name="tcd010Form" property="tcd010LockFlg" value="0">
                    <logic:equal name="tcd010Form" property="tcd010FailFlg" value="0">
                      <button type="button" class="baseBtn" onClick="multiEditButton();" value="<gsmsg:write key="cmn.multiple.edit" />">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.multiple.edit" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.multiple.edit" />">
                        <gsmsg:write key="cmn.multiple.edit" />
                      </button>
                      <button type="button" class="baseBtn" onClick="buttonPush('del');"  value="<gsmsg:write key="cmn.delete" />">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                        <gsmsg:write key="cmn.delete" />
                      </button>
                    </logic:equal>
                  </logic:equal>
                  <logic:equal name="tcd010Form" property="tcd010FailFlg" value="0">
                    <logic:equal name="tcd010Form" property="kinmuOut" value="0">
                      <button tsype="button" class="baseBtn" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('xls');">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                        <gsmsg:write key="tcd.tcd010.12" />
                      </button>
                    </logic:equal>
                    <logic:equal name="tcd010Form" property="kinmuOut" value="1">
                      <button tsype="button" class="baseBtn" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('pdf');">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
                        <gsmsg:write key="tcd.tcd010.12" />
                      </button>
                    </logic:equal>
                    <button tsype="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onClick="buttonPush('csv');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                      <gsmsg:write key="cmn.export" />
                    </button>
                    <logic:equal name="tcd010Form" property="csvImportFlg" value="0">
                      <button type="button" class="baseBtn" onClick="buttonPush('imp');" value="<gsmsg:write key="cmn.import" />">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                        <gsmsg:write key="cmn.import" />
                      </button>
                    </logic:equal>
                  </logic:equal>
                </div>
            </div>
          </logic:notEmpty>
        </div>
      </div>
    </div>
  </html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>