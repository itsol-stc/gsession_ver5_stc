<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailySchedule.tld" prefix="dailySchedule" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%-- キーワード検索区分 --%>
<%
  String keyWordAnd    = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.KEY_WORD_KBN_AND);
  String keyWordOr     = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.KEY_WORD_KBN_OR);
%>
<%-- 検索対象 --%>
<%
  String targetTitle   = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SEARCH_TARGET_TITLE);
  String targetHonbun  = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SEARCH_TARGET_HONBUN);
%>
<%-- 定数値 --%>
<%
  int editConfOwn          = jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_OWN;
  int editConfGroup        = jp.groupsession.v2.cmn.GSConstSchedule.EDIT_CONF_GROUP;
  int dspPublic            = jp.groupsession.v2.cmn.GSConstSchedule.DSP_PUBLIC;
  int dspNotPublic         = jp.groupsession.v2.cmn.GSConstSchedule.DSP_NOT_PUBLIC;
  int dspYoteiari          = jp.groupsession.v2.cmn.GSConstSchedule.DSP_YOTEIARI;
  int dspBelongGroup       = jp.groupsession.v2.cmn.GSConstSchedule.DSP_BELONG_GROUP;
  int dspUserGroup       = jp.groupsession.v2.cmn.GSConstSchedule.DSP_USRGRP;
%>
<%-- 管理者区分 --%>
<%
  String adminUsr   = String.valueOf(jp.groupsession.v2.cmn.GSConst.USER_ADMIN);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch100.5" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>

<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch100.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body>
<html:form action="/schedule/sch100">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="sch100SltStartYearFr" value="">
<input type="hidden" name="sch100SltStartMonthFr" value="">
<input type="hidden" name="sch100SltStartDayFr" value="">
<input type="hidden" name="sch100SltStartYearTo" value="">
<input type="hidden" name="sch100SltStartMonthTo" value="">
<input type="hidden" name="sch100SltStartDayTo" value="">
<input type="hidden" name="sch100SltEndYearFr" value="">
<input type="hidden" name="sch100SltEndMonthFr" value="">
<input type="hidden" name="sch100SltEndDayFr" value="">
<input type="hidden" name="sch100SltEndYearTo" value="">
<input type="hidden" name="sch100SltEndMonthTo" value="">
<input type="hidden" name="sch100SltEndDayTo" value="">

<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="sch010DspDate" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="sch010SelectUsrKbn" />
<html:hidden property="sch010DspGpSid" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch010CrangeKbn" />

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

<html:hidden property="sch100SvOrderKey1" />
<html:hidden property="sch100SvSortKey1" />
<html:hidden property="sch100SvOrderKey2" />
<html:hidden property="sch100SvSortKey2" />

<logic:notEmpty name="sch100Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch100Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch100Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch100Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sch100PageNum" />
<input type="hidden" name="listMod" value="5">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
      <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
    </li>
    <li>
      <gsmsg:write key="schedule.108" />
    </li>
    <li class="pageTitle_subFont">
     <gsmsg:write key="cmn.list" />
    </li>
    <li>
      <logic:notEmpty name="sch100Form" property="sch100ScheduleList">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.pdf" />" onClick="buttonPush('pdf');">
          <img class="btn_classicImg-display"  src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <gsmsg:write key="cmn.pdf" />
        </button>
      </logic:notEmpty>
      <logic:equal name="sch100Form" property="adminKbn" value="1">
        <button type="button" class="baseBtn"  value="<gsmsg:write key="cmn.import" />" onClick="setDateParam();buttonPush('import');">
          <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />" class="btn_classicImg-display">
          <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />" class="btn_originalImg-display">
          <gsmsg:write key="cmn.import" />
        </button>
      </logic:equal>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="verAlignMid w100">
    <span class="verAlignMid wp200"></span>
    <span class="mrl_auto"></span>
    <bean:define id="uMdl" name="sch100Form" property="sch100UsrMdl" />
    <button type="button" class="baseBtn" value="<gsmsg:write key="schedule.19" />" onClick="moveKojinSchedule();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_week_kojin.png" alt="<gsmsg:write key="schedule.19" />">
      <gsmsg:write key="schedule.19" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.days2" />" onClick="moveSchedule('day');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_clock.png" alt="<gsmsg:write key="cmn.days2" />">
      <gsmsg:write key="cmn.days2" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.weeks" />" onClick="buttonPush('week');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_week.png" alt="<gsmsg:write key="cmn.weeks" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_week.png" alt="<gsmsg:write key="cmn.weeks" />">
      <gsmsg:write key="cmn.weeks" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.between.mon" />" onClick="moveSchedule('month', <bean:write name="uMdl" property="usrSid" />, <bean:write name="uMdl" property="usrKbn" />);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="<gsmsg:write key="cmn.monthly" />">
      <gsmsg:write key="cmn.monthly" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.list" />" onClick="buttonPush('reload');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_list.png" alt="<gsmsg:write key="cmn.list" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
      <gsmsg:write key="cmn.list" />
    </button>
    <span class="mrl_auto"></span>
    <span class="verAlignMid wp200"></span>
  </div>
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <!-- 検索 -->
  <table class="table-left w100">
    <tr>
      <td colspan="4" class="w100 table_title-color">
        <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="mb5 table_header_icon_search classic-display">
        <span class="table_title-color"><gsmsg:write key="cmn.search" /></span>
      </td>
    </tr>
    <!-- グループ・ユーザ -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="schedule.sch100.4" />
      </th>
      <td class="w85" colspan="3">
        <html:select property="sch100SltGroup" styleClass="select01" onchange="changeGroupCombo();">
          <logic:notEmpty name="sch100Form" property="sch100GroupLabel" scope="request">
            <logic:iterate id="gpBean" name="sch100Form" property="sch100GroupLabel" scope="request">
              <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
              <logic:equal name="gpBean" property="styleClass" value="0">
                <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
              </logic:equal>
              <logic:equal name="gpBean" property="styleClass" value="1">
                <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
              </logic:equal>
              <logic:equal name="gpBean" property="styleClass" value="2">
                <html:option styleClass="select_myschedule-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
              </logic:equal>
            </logic:iterate>
          </logic:notEmpty>
        </html:select>
        <logic:equal name="sch100Form" property="sch010CrangeKbn" value="0">
          <button class="iconBtn-border " type="button" id="sch100GroupBtn" value="&nbsp;&nbsp;" onclick="openSch100Group(this.form.sch100SltGroup, 'sch100SltGroup', '1');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
        </logic:equal>
        <%-- 検索対象ユーザの設定 --%>
        <html:select property="sch100SltUser" styleClass="select01">
          <logic:iterate id="nowSelectUserLabel" name="sch100Form" property="sch100UserLabel">
            <%-- 初期検索対象ユーザ設定 --%>
            <logic:notEmpty name="sch100Form" property="sch100SltUser">
              <%-- 初期検索対象ユーザ設定のためのデータを取得  --%>
              <bean:define id="selectUser" name="sch100Form" property="sch100SltUser"/>
              <bean:define id="nowSelectUser" name="nowSelectUserLabel" property="value"/>
              <%-- 検索対象ユーザの初期設定(無効) --%>
              <logic:equal name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                <% if (selectUser.equals(String.valueOf(nowSelectUser))) { %>
                  <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption" selected>
                <% } else { %>
                  <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption">
                <% } %>
                <bean:write name="nowSelectUserLabel" property="label"/>
                </option>
              </logic:equal>
              <%-- 検索対象ユーザの初期設定(有効) --%>
              <logic:notEqual name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                <% if (selectUser.equals(String.valueOf(nowSelectUser))) { %>
                  <option value='<bean:write name="nowSelectUserLabel" property="value"/>'  selected>
                <% } else { %>
                  <option value='<bean:write name="nowSelectUserLabel" property="value"/>'>
                <% } %>
                <bean:write name="nowSelectUserLabel" property="label"/></option>
              </logic:notEqual>
            </logic:notEmpty>
            <logic:empty name="sch100Form" property="sch100SltUser">
              <logic:equal name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption">
              </logic:equal>
              <logic:notEqual name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                <option value='<bean:write name="nowSelectUserLabel" property="value"/>'>
              </logic:notEqual>
              <bean:write name="nowSelectUserLabel" property="label"/></option>
            </logic:empty>
          </logic:iterate>
        </html:select>
      </td>
    </tr>
    <!-- 開始日 -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="schedule.sch100.10" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid w100">
          <html:text name="sch100Form" property="sch100StartFrDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatesf" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <button type="button" class="webIconBtn ml5" name="applDateFrBtn" onClick="return moveDay($('#selDatesf')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#selDatesf')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a href="#" class="fw_b todayBtn original-display" onClick="return moveDay($('#selDatesf')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="return moveDay($('#selDatesf')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
          <span class="ml10 mr10"><gsmsg:write key="tcd.153" /></span>
          <html:text name="sch100Form" property="sch100StartToDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatest" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
          <button type="button" class="webIconBtn ml5" name="applDateFrBtn" onClick="return moveDay($('#selDatest')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#selDatest')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a href="#" class="fw_b todayBtn original-display" onClick="return moveDay($('#selDatest')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="return moveDay($('#selDatest')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
        </div>
      </td>
    </tr>
    <!-- 終了日 -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="schedule.sch100.15" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid w100">
          <html:text name="sch100Form" property="sch100EndFrDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDateef" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#selDateef')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#selDateef')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a href="#" class="fw_b todayBtn original-display" onClick="return moveDay($('#selDateef')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="return moveDay($('#selDateef')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
          <span class="ml10 mr10"><gsmsg:write key="tcd.153" /></span>
          <html:text name="sch100Form" property="sch100EndToDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateet" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
          <button type="button" class="webIconBtn ml5" name="applDateFrBtn" onClick="return moveDay($('#selDateet')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#selDateet')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a href="#" class="fw_b todayBtn original-display" onClick="return moveDay($('#selDateet')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="return moveDay($('#selDateet')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
        </div>
      </td>
    </tr>
    <!-- キーワード -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w35">
        <html:text name="sch100Form" maxlength="50" property="sch010searchWord" styleClass="wp320" /><br>
        <div class="txt_l verAlignMid">
          <html:radio name="sch100Form" property="sch100KeyWordkbn" value="<%= keyWordAnd %>" styleId="keyKbn_01" />
          <label for="keyKbn_01"><gsmsg:write key="cmn.contains.all.and" /></label>
          <html:radio name="sch100Form" property="sch100KeyWordkbn" value="<%= keyWordOr %>" styleId="keyKbn_02" styleClass="ml10"/>
          <label for="keyKbn_02"><gsmsg:write key="cmn.orcondition" /></label>
        </div>
      </td>
      <!-- 検索対象 -->
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.search2" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
        <html:multibox styleId="search_scope_01" name="sch100Form" property="sch100SearchTarget" value="<%= targetTitle %>" />
        <label for="search_scope_01"><gsmsg:write key="cmn.subject" /></label>
        <html:multibox styleId="search_scope_02" styleClass="ml10" name="sch100Form" property="sch100SearchTarget" value="<%= targetHonbun %>" />
        <label for="search_scope_02"><gsmsg:write key="cmn.body" /></label>
        </div>
      </td>
    </tr>
    <!-- タイトル色 -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="schedule.10" />
      </th>
      <td class="w85" colspan="3">
        <bean:define id="colorMsg1" value=""/>
        <bean:define id="colorMsg2" value=""/>
        <bean:define id="colorMsg3" value=""/>
        <bean:define id="colorMsg4" value=""/>
        <bean:define id="colorMsg5" value=""/>
        <bean:define id="colorMsg6" value=""/>
        <bean:define id="colorMsg7" value=""/>
        <bean:define id="colorMsg8" value=""/>
        <bean:define id="colorMsg9" value=""/>
        <bean:define id="colorMsg10" value=""/>
        <logic:notEmpty name="sch100Form" property="sch100ColorMsgList">
          <div class="verAlignMid">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
              <html:multibox name="sch100Form" styleId="bg_color1" property="sch100Bgcolor" value="1" />
            </div>
            <label for="bg_color1" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[0]"/></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleRed">
              <html:multibox name="sch100Form" styleId="bg_color2" property="sch100Bgcolor" value="2" />
            </div>
            <label for="bg_color2" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[1]"/></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
              <html:multibox name="sch100Form" styleId="bg_color3" property="sch100Bgcolor" value="3" />
            </div>
            <label for="bg_color3" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[2]"/></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
              <html:multibox name="sch100Form" styleId="bg_color4" property="sch100Bgcolor" value="4" />
            </div>
            <label for="bg_color4" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[3]"/></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
              <html:multibox name="sch100Form" styleId="bg_color5" property="sch100Bgcolor" value="5" />
            </div>
            <label for="bg_color5" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[4]"/></label>
          </div>
          <logic:equal name="sch100Form" property="sch100colorKbn" value="1">
            <br>
            <div class="verAlignMid">
              <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
                <html:multibox name="sch100Form" styleId="bg_color6" property="sch100Bgcolor" value="6" />
              </div>
              <label for="bg_color6" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[5]"/></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleWine">
                <html:multibox name="sch100Form" styleId="bg_color7" property="sch100Bgcolor" value="7" />
              </div>
              <label for="bg_color7" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[6]"/></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleCien">
                <html:multibox name="sch100Form" styleId="bg_color8" property="sch100Bgcolor" value="8" />
              </div>
              <label for="bg_color8" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[7]"/></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleGray">
                <html:multibox name="sch100Form" styleId="bg_color9" property="sch100Bgcolor" value="9" />
              </div>
              <label for="bg_color9" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[8]"/></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
                <html:multibox name="sch100Form" styleId="bg_color10" property="sch100Bgcolor" value="10" />
              </div>
              <label for="bg_color10" class="ml5"><bean:write name="sch100Form" property="sch100ColorMsgList[9]"/></label>
            </div>
          </logic:equal>
        </logic:notEmpty>

        <logic:empty name="sch100Form" property="sch100ColorMsgList">
          <div class="verAlignMid">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlue">
              <html:multibox name="sch100Form" styleId="bg_color1" property="sch100Bgcolor" value="1" />
            </div>
            <label for="bg_color1" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleRed">
              <html:multibox name="sch100Form" styleId="bg_color2" property="sch100Bgcolor" value="2" />
            </div>
            <label for="bg_color2" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleGreen">
              <html:multibox name="sch100Form" styleId="bg_color3" property="sch100Bgcolor" value="3" />
            </div>
            <label for="bg_color3" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleYellow">
              <html:multibox name="sch100Form" styleId="bg_color4" property="sch100Bgcolor" value="4" />
            </div>
            <label for="bg_color4" class="ml5"></label>
          </div>
          <div class="verAlignMid ml5">
            <div class="cal_titlecolor-block bgc_fontSchTitleBlack">
              <html:multibox name="sch100Form" styleId="bg_color5" property="sch100Bgcolor" value="5" />
            </div>
            <label for="bg_color5" class="ml5"></label>
          </div>
          <logic:equal name="sch100Form" property="sch100colorKbn" value="1">
            <br>
            <div class="verAlignMid">
              <div class="cal_titlecolor-block bgc_fontSchTitleNavy">
                <html:multibox name="sch100Form" styleId="bg_color6" property="sch100Bgcolor" value="6" />
              </div>
              <label for="bg_color6" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleWine">
                <html:multibox name="sch100Form" styleId="bg_color7" property="sch100Bgcolor" value="7" />
              </div>
              <label for="bg_color7" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleCien">
                <html:multibox name="sch100Form" styleId="bg_color8" property="sch100Bgcolor" value="8" />
              </div>
              <label for="bg_color8" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleGray">
                <html:multibox name="sch100Form" styleId="bg_color9" property="sch100Bgcolor" value="9" />
              </div>
              <label for="bg_color9" class="ml5"></label>
            </div>
            <div class="verAlignMid ml5">
              <div class="cal_titlecolor-block bgc_fontSchTitleMarine">
                <html:multibox name="sch100Form" styleId="bg_color10" property="sch100Bgcolor" value="10" />
              </div>
              <label for="bg_color10" class="ml5"></label>
            </div>
          </logic:equal>
        </logic:empty>
       </td>
    </tr>
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.sort.order" />
      </th>
      <td class="w85" colspan="3">
        <div class="display_inline">
          <gsmsg:write key="cmn.first.key" />&nbsp;
          <html:select property="sch100SortKey1" >
            <html:optionsCollection name="sch100Form" property="sortKeyList" value="value" label="label" />
          </html:select>
        </div>
        <div class="display_inline txt_b">
         <span class="verAlignMid mr20">
            <html:radio name="sch100Form" property="sch100OrderKey1" styleId="sort1_up" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ORDER_KEY_ASC) %>" />
            <label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio styleClass="ml10" name="sch100Form" property="sch100OrderKey1" styleId="sort1_dw" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ORDER_KEY_DESC) %>" />
            <label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
          </span>
        </div>
        <div class="display_inline">
          <gsmsg:write key="cmn.second.key" />&nbsp;
          <html:select property="sch100SortKey2" >
            <html:optionsCollection name="sch100Form" property="sortKeyList" value="value" label="label" />
          </html:select>
        </div>
        <div class="display_inline txt_b">
         <span class="verAlignMid mr20">
            <html:radio name="sch100Form" property="sch100OrderKey2" styleId="sort2_up" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ORDER_KEY_ASC) %>" />
            <label for="sort2_up"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio styleClass="ml10" name="sch100Form" property="sch100OrderKey2" styleId="sort2_dw" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ORDER_KEY_DESC) %>" />
            <label for="sort2_dw"><gsmsg:write key="cmn.order.desc" /></label>
          </span>
        </div>
      </td>
    </tr>
  </table>
  <div>
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('sch100search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <logic:notEmpty name="sch100Form" property="sch100ScheduleList">
    <table class="table-left w100">
      <th class="w15">
        <gsmsg:write key="reserve.output.item" />
      </th>
      <td class="w70 border_right_none">
        <div class="verAlignMid no_w">
          <logic:equal name="sch100Form" property="adminKbn" value="<%= adminUsr %>">
            <html:multibox styleId="loginId" name="sch100Form" property="sch100CsvOutField" value="1" />
            <label for="loginId" ><gsmsg:write key="cmn.user.id" /></label>
            <html:multibox styleId="groupId" name="sch100Form" property="sch100CsvOutField" value="2" styleClass="ml10"/>
            <label for="groupId" ><gsmsg:write key="cmn.group.id" /></label>
            <span class="mr10"></span>
          </logic:equal>
          <html:multibox styleId="uname" name="sch100Form" property="sch100CsvOutField" value="3" />
          <label for="uname" ><gsmsg:write key="cmn.name" /></label>
          <html:multibox styleId="frDate" name="sch100Form" property="sch100CsvOutField" value="4" styleClass="ml10"/>
          <label for="frDate" ><gsmsg:write key="cmn.startdate" /></label>
          <html:multibox styleId="frTime" name="sch100Form" property="sch100CsvOutField" value="5" styleClass="ml10"/>
          <label for="frTime" ><gsmsg:write key="cmn.starttime" /></label>
          <html:multibox styleId="toDate" name="sch100Form" property="sch100CsvOutField" value="6" styleClass="ml10"/>
          <label for="toDate" ><gsmsg:write key="cmn.end.date" /></label>
          <html:multibox styleId="toTime" name="sch100Form" property="sch100CsvOutField" value="7" styleClass="ml10"/>
          <label for="toTime" ><gsmsg:write key="cmn.endtime" /></label>
          <html:multibox styleId="title" name="sch100Form" property="sch100CsvOutField" value="8" styleClass="ml10"/>
          <label for="title" ><gsmsg:write key="cmn.title" /></label>
        </div><br>
        <div class="verAlignMid no_w">
          <html:multibox styleId="titleColor" name="sch100Form" property="sch100CsvOutField" value="9" />
          <label for="titleColor" ><gsmsg:write key="schedule.10" /></label>
          <html:multibox styleId="value" name="sch100Form" property="sch100CsvOutField" value="10" styleClass="ml10"/>
          <label for="value" ><gsmsg:write key="cmn.content" /></label>
          <html:multibox styleId="biko" name="sch100Form" property="sch100CsvOutField" value="11" styleClass="ml10"/>
          <label for="biko" ><gsmsg:write key="cmn.memo" /></label>
          <html:multibox styleId="editPerm" name="sch100Form" property="sch100CsvOutField" value="12" styleClass="ml10"/>
          <label for="editPerm" ><gsmsg:write key="cmn.edit.permissions" /></label>
          <html:multibox styleId="open" name="sch100Form" property="sch100CsvOutField" value="13" styleClass="ml10"/>
          <label for="open" ><gsmsg:write key="cmn.public.kbn" /></label>
          <html:multibox styleId="timeSeg" name="sch100Form" property="sch100CsvOutField" value="14" styleClass="ml10"/>
          <label for="timeSeg" ><gsmsg:write key="schedule.timed.segments" /></label>
          <html:multibox styleId="adName" name="sch100Form" property="sch100CsvOutField" value="15" styleClass="ml10"/>
          <label for="adName" ><gsmsg:write key="schedule.132" /></label>
          <html:multibox styleId="edName" name="sch100Form" property="sch100CsvOutField" value="16" styleClass="ml10"/>
          <label for="edName" ><gsmsg:write key="schedule.133" /></label>
        </div>
      </td>
      <td class="w15 txt_c">
        <button type="button" class="baseBtn"  value="<gsmsg:write key="cmn.export" />" onClick="buttonPush('sch100export');">
          <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
          <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
          <gsmsg:write key="cmn.export" />
        </button>
      </td>
    </table>

    <div class="w100 mt10 display_flex">
    <div class="txt_l w50">
      <button type="button" class="baseBtn mb0" value="<gsmsg:write key="cmn.list" />" onClick="setDateParam();buttonPush('pushDel');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </div>
    <bean:size id="count1" name="sch100Form" property="sch100PageLabel" scope="request" />
    <!-- ページング -->
    <logic:greaterThan name="count1" value="1">
      <div class="txt_r w50">
        <logic:notEmpty name="sch100Form" property="sch100PageLabel">
          <span class="paging">
             <button type="button" class="webIconBtn" onClick="return buttonPush('arrorw_left');">
               <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
               <i class="icon-paging_left"></i>
             </button>
              <html:select property="sch100Slt_page1" onchange="changePage1();" styleClass="paging_combo">
                <html:optionsCollection name="sch100Form" property="sch100PageLabel" value="value" label="label" />
              </html:select>
             <button type="button" class="webIconBtn" onClick="return buttonPush('arrorw_right');">
               <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
               <i class="icon-paging_right"></i>
             </button>
          </span>
        </logic:notEmpty>
      </div>
    </logic:greaterThan>
    </div>

    <table class="table-top w100">
      <tr>
        <th class="table_title-color w3 js_tableTopCheck js_tableTopCheck-header cursor_p" onChange="chgCheckAll('sch100AllCheck', 'sch100SelectScdSid');" >
          <input type="checkbox" name="sch100AllCheck" value="1" >
        </th>
        <th class="table_title-color w15 cursor_p">
          <a href="#!" onclick="clickSortTitle('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SORT_KEY_NAME) %>')">
            <gsmsg:write key="cmn.name4" />
            <logic:equal name="sch100Form" property="sch100SortKey1" value="1">
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="0">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span>
                <span class="original-display"><i class="icon-sort_up"></i></span>
              </logic:equal>
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="1">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span>
                <span class="original-display"><i class="icon-sort_down"></i></span>
              </logic:equal>
            </logic:equal>
          </a>
        </th>
        <th class="table_title-color w15 cursor_p">
          <a href="#!" onclick="clickSortTitle('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SORT_KEY_FRDATE) %>')">
            <gsmsg:write key="schedule.sch100.11" />
            <logic:equal name="sch100Form" property="sch100SortKey1" value="2">
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="0">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span>
                <span class="original-display"><i class="icon-sort_up"></i></span>
              </logic:equal>
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="1">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span>
                <span class="original-display"><i class="icon-sort_down"></i></span>
              </logic:equal>
            </logic:equal>
          </a>
        </th>
        <th class="table_title-color w15 cursor_p">
          <a href="#!" onclick="clickSortTitle('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SORT_KEY_TODATE) %>')">
            <gsmsg:write key="schedule.sch100.16" />
            <logic:equal name="sch100Form" property="sch100SortKey1" value="3">
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="0">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span>
                <span class="original-display"><i class="icon-sort_up"></i></span>
              </logic:equal>
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="1">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span>
                <span class="original-display"><i class="icon-sort_down"></i></span>
              </logic:equal>
            </logic:equal>
          </a>
        </th>
        <th class="table_title-color cursor_p">
          <a href="#!" onclick="clickSortTitle('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SORT_KEY_TITLE) %>')">
            <gsmsg:write key="schedule.sch100.7" />
            <logic:equal name="sch100Form" property="sch100SortKey1" value="4">
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="0">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span>
                <span class="original-display"><i class="icon-sort_up"></i></span>
              </logic:equal>
              <logic:equal name="sch100Form" property="sch100OrderKey1" value="1">
                <span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span>
                <span class="original-display"><i class="icon-sort_down"></i></span>
              </logic:equal>
            </logic:equal>
          </a>
        </th>
      </tr>
      <logic:iterate id="schMdl" name="sch100Form" property="sch100ScheduleList" indexId="idx">
        <bean:define id="u_public" name="schMdl" property="public"  type="java.lang.Integer" />
        <bean:define id="u_grpEdKbn" name="schMdl" property="grpEdKbn"  type="java.lang.Integer" />
        <bean:define id="u_kjnEdKbn" name="schMdl" property="kjnEdKbn"  type="java.lang.Integer" />
        <%
           int publicType = ((Integer)pageContext.getAttribute("u_public",PageContext.PAGE_SCOPE));
           int grpEdKbn = ((Integer)pageContext.getAttribute("u_grpEdKbn",PageContext.PAGE_SCOPE));
           int kjnEdKbn = ((Integer)pageContext.getAttribute("u_kjnEdKbn",PageContext.PAGE_SCOPE));
           String trClickClass = "";
           String tdClickClass = "";
           boolean canEditFlg = false;
        %>
        <%
          if ((publicType == dspPublic || publicType == dspBelongGroup || publicType == dspUserGroup) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) {
              trClickClass = "cursor_p js_listClick";
              tdClickClass = " js_click";
              canEditFlg = true;
          }
        %>
        <tr class="js_listHover <%= trClickClass %>">
          <input type="hidden" name="scrData_cmd" value="edit">
          <input type="hidden" name="scrData_sch010DspDate" value="<bean:write name="sch100Form" property="sch010DspDate" />">
          <input type="hidden" name="scrData_schSid" value="<bean:write name="schMdl" property="schSid" />">
          <input type="hidden" name="scrData_userSid" value="<bean:write name="schMdl" property="userSid" />">
          <input type="hidden" name="scrData_userKbn" value="<bean:write name="schMdl" property="userKbn" />">
          <td class="txt_c js_tableTopCheck">
            <% if (canEditFlg) { %>
            <html:multibox name="sch100Form" property="sch100SelectScdSid" onclick="">
              <bean:write name="schMdl" property="schSid" />
            </html:multibox>
            <% } %>
          </td>
          <td class="txt_l<%= tdClickClass %>">
            <logic:equal name="schMdl" property="usrUkoFlg" value="1">
              <span class="mukoUserOption"><bean:write name="schMdl" property="userName" /></span>
            </logic:equal>
            <logic:notEqual name="schMdl" property="usrUkoFlg" value="1">
              <bean:write name="schMdl" property="userName" />
            </logic:notEqual>
          </td>
          <td class="txt_c<%= tdClickClass %>">
            <bean:write name="schMdl" property="fromDateStr" />
          </td>
          <td class="txt_c<%= tdClickClass %>">
            <bean:write name="schMdl" property="toDateStr" />
          </td>
          <td class="txt_l<%= tdClickClass %>">
            <%
            if ((publicType == dspPublic || publicType == dspBelongGroup || publicType == dspUserGroup) || (kjnEdKbn == editConfOwn || grpEdKbn == editConfGroup)) {
            %>
              <!--タイトルカラー設定-->
              <bean:define id="colorType" value=""/>
              <logic:equal name="schMdl" property="bgColor" value="1">
                <bean:define id="colorType" value="cl_fontSchTitleBlue"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="2">
                <bean:define id="colorType" value="cl_fontSchTitleRed"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="3">
                <bean:define id="colorType" value="cl_fontSchTitleGreen"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="4">
                <bean:define id="colorType" value="cl_fontSchTitleYellow"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="5">
                <bean:define id="colorType" value="cl_fontSchTitleBlack"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="6">
                <bean:define id="colorType" value="cl_fontSchTitleNavy"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="7">
                <bean:define id="colorType" value="cl_fontSchTitleWine"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="8">
                <bean:define id="colorType" value="cl_fontSchTitleCien"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="9">
                <bean:define id="colorType" value="cl_fontSchTitleGray"/>
              </logic:equal>
              <logic:equal name="schMdl" property="bgColor" value="10">
                <bean:define id="colorType" value="cl_fontSchTitleMarine"/>
              </logic:equal>
              <span class="<%=colorType%> fs_13 verAlignMid">
                <logic:equal name="schMdl" property="publicIconFlg" value="true">
                  <span class="mr5">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                  </span>
                </logic:equal>
                <bean:write name="schMdl" property="title" /></span><br>
              <bean:write name="schMdl" property="valueStr" filter="false"/>
            <% } else {%>
              <span class="fs_13"><bean:write name="schMdl" property="title" /></span>
            <% } %>
          </td>
        </tr>
      </logic:iterate>
    </table>
    <!-- ページング -->
    <logic:greaterThan name="count1" value="1">
      <div class="txt_r mt10">
        <logic:notEmpty name="sch100Form" property="sch100PageLabel">
          <span class="paging">
             <button type="button" class="webIconBtn" onClick="return buttonPush('arrorw_left');">
               <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
               <i class="icon-paging_left"></i>
             </button>
              <html:select property="sch100Slt_page2" onchange="changePage2();" styleClass="paging_combo">
                <html:optionsCollection name="sch100Form" property="sch100PageLabel" value="value" label="label" />
              </html:select>
             <button type="button" class="webIconBtn" onClick="return buttonPush('arrorw_right');">
               <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
               <i class="icon-paging_right"></i>
             </button>
          </span>
        </logic:notEmpty>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>