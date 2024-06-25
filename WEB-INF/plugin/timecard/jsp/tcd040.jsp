<%@page import="jp.groupsession.v2.tcd.model.TcdManagerModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.tcd.model.TcdTotalValueModel" %>
<%@ page import="jp.groupsession.v2.tcd.model.TcdHolidayInfModel" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd040.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.49" /></title>

</head>

<body>
<html:form action="/timecard/tcd040">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<logic:notEmpty name="tcd040Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd040Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="tcdBackScreen" />
<html:hidden property="tcd040SearchFlg" />
<html:hidden property="tcd040SltYearSv" />
<html:hidden property="tcd040SltMonthSv" />
<html:hidden property="tcd040SltYearToSv" />
<html:hidden property="tcd040SltMonthToSv" />
<html:hidden property="tcd040SltGroupSv" />
<html:hidden property="tcd040ZangyoCkSv" />
<html:hidden property="tcd040SinyaCkSv" />
<html:hidden property="tcd040KyujituCkSv" />
<html:hidden property="tcd040ChikokuCkSv" />
<html:hidden property="tcd040SoutaiCkSv" />
<logic:notEmpty name="tcd040Form" property="tcd040HolidayCkSv" scope="request">
<logic:iterate id="holidayKbn" name="tcd040Form" property="tcd040HolidayCk" scope="request">
  <input type="hidden" name="tcd040HolidayCkSv" value="<bean:write name="holidayKbn" />">
</logic:iterate>
</logic:notEmpty>
<html:hidden property="tcd040SortKey" />
<html:hidden property="tcd040OrderKey" />
<html:hidden property="tcd040SortKeyHoliday" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd010.13" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd040_back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
</div>

<div class="wrapper_2column mrl_auto">
  <div class="w65">

    <table class="table-top">
      <tr>
      <th colspan="3">
        <div class="component_bothEnd">
          <gsmsg:write key="tcd.tcd040.04" />
          <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('tcd040_search');">
            <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
            <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>
      </th>
      </tr>
      <tr>
      <td colspan="3">
        <html:select name="tcd040Form" property="tcd040SltYear" >
          <html:optionsCollection name="tcd040Form" property="tcd040YearLabelList" />
         </html:select>
        <html:select name="tcd040Form" property="tcd040SltMonth" styleClass="mr5">
          <html:optionsCollection name="tcd040Form" property="tcd040MonthLabelList" />
        </html:select>
        <gsmsg:write key="tcd.153" />
        <html:select name="tcd040Form" property="tcd040SltYearTo" styleClass="ml5">
          <html:optionsCollection name="tcd040Form" property="tcd040YearLabelList" />
        </html:select>
        <html:select name="tcd040Form" property="tcd040SltMonthTo" >
          <html:optionsCollection name="tcd040Form" property="tcd040MonthLabelList" />
        </html:select>
        <div class="mt15">
          <span class="verAlignMid">
            <gsmsg:write key="cmn.show.group" />
            <html:select name="tcd040Form" property="tcd040SltGroup" styleClass="ml5 mr5 wp250">
              <html:optionsCollection name="tcd040Form" property="tcd040GpLabelList" />
            </html:select>
            <button type="button" class="iconBtn-border mr10" class="ml5" id="rng130GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.tcd040SltGroup, 'tcd040SltGroup', '0', '', 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </span>
        </div>

        <div class="mt15">
          <span class="verAlignMid">
            <html:checkbox name="tcd040Form" property="tcd040avgInZero" value="1" styleId="avgInZero" />
            <label for="avgInZero" class="lh_normal"><gsmsg:write key="tcd.tcd040.16" /></label>
          </span>
        </div>

        <div class="w100 settingForm_separator">
            <div class="border_none w100">
              <div class="verAlignMid w30" >
                <html:multibox styleId="tcd040ZangyoCk" property="tcd040ZangyoCk" value="1"/><label for="tcd040ZangyoCk" class="lh_normal"><gsmsg:write key="tcd.tcd040.10" /></label>
              </div>
              <div class="verAlignMid w30" >
                <html:multibox styleId="tcd040SinyaCk" property="tcd040SinyaCk" value="1"/><label for="tcd040SinyaCk" class="lh_normal"><gsmsg:write key="tcd.tcd040.08" /></label>
              </div>
              <div class="verAlignMid w30" >
                <html:multibox styleId="tcd040KyujituCk" property="tcd040KyujituCk" value="1"/><label for="tcd040KyujituCk" class="lh_normal"><gsmsg:write key="tcd.tcd040.15" /></label>
              </div>

              <div class="verAlignMid w30" >
                <html:multibox styleId="tcd040ChikokuCk" property="tcd040ChikokuCk" value="1"/><label for="tcd040ChikokuCk" class="lh_normal"><gsmsg:write key="tcd.tcd040.05" /></label>
              </div>
              <div class="verAlignMid w30" >
                <html:multibox styleId="tcd040SoutaiCk" property="tcd040SoutaiCk" value="1"/><label for="tcd040SoutaiCk" class="lh_normal"><gsmsg:write key="tcd.tcd040.07" /></label>
              </div>

              <logic:notEmpty name="tcd040Form" property="tcd040holMaltiList" scope="request">
                <logic:iterate id="maltiList" name="tcd040Form" property="tcd040holMaltiList" scope="request" indexId="idx">
                  <bean:define id="tcdHolValue" name="maltiList" property="thiSid" />
                  <% String holidayCheckId = "chkholiday" + String.valueOf(idx.intValue()); %>
                  <div class="verAlignMid w30" >
                    <html:multibox styleId="<%= holidayCheckId %>" property="tcd040HolidayCk" value="<%= String.valueOf(tcdHolValue) %>" />
                    <label for="<%= holidayCheckId %>" class="lh_normal"><bean:write name="maltiList" property="thiName"/><gsmsg:write key="tcd.tcd040.24" /></label>
                  </div>
                </logic:iterate>
              </logic:notEmpty>
        </div>
      </div>
    </table>
  </div>

  <div class="w34">
    <table class="table-top ml10">
      <tr>
      <th>
        <div class=" component_bothEnd">
          <gsmsg:write key="tcd.tcd040.09" />
          <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.export" />" onClick="return buttonPush('tcd040_export');">
            <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
            <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
            <gsmsg:write key="cmn.export" />
          </button>
        </div>
      </th>
      </tr>
      <tr>
      <td>
        <ul>
          <logic:notEmpty name="tcd040Form" property="tcd040SearchList" scope="request">
            <logic:iterate id="searchList" name="tcd040Form" property="tcd040SearchList" scope="request">
              <li class="list_st"><bean:write name="searchList" /></li>
            </logic:iterate>
          </logic:notEmpty>
        </ul>
      </td>
      </tr>
    </table>
  </div>
</div>

<%-- 検索結果 --%>
<div class="mt20" style="overflow:hidden; overflow-x:auto;">

  <table class="w100 table-top">
    <tr class="no_w">
      <%-- 氏名 --%>
  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w25" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.name" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w25" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.name" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>">
      <th class="table_title-color w25" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.name" /></a></th>
  </logic:notEqual>

      <%-- 社員/職員番号 --%>
  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>">
      <th class="table_title-color w5" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.employee.staff.number" /></a></th>
  </logic:notEqual>
      <%-- 稼動 --%>
      <th class="table_title-color w10" colspan="2"><gsmsg:write key="tcd.tcd010.16" /></th>
      <%-- 残業 --%>
      <th class="table_title-color w10" colspan="2"><gsmsg:write key="tcd.tcd010.09" /></th>
      <%-- 深夜 --%>
      <th class="table_title-color w10" colspan="2"><gsmsg:write key="tcd.tcd010.06" /></th>
      <%-- 休出 --%>
      <th class="table_title-color w10" colspan="2"><gsmsg:write key="tcd.tcd010.14" /></th>
      <%-- 遅刻 --%>
  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_CHIKOKU) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5 no_w" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_CHIKOKU) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="tcd.18" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5 no_w" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_CHIKOKU) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.18" /><br><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_CHIKOKU) %>">
      <th class="table_title-color w5 no_w" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_CHIKOKU) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.18" /></a></th>
  </logic:notEqual>
      <%-- 早退 --%>
  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SOUTAI) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5 no_w" rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SOUTAI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="tcd.22" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5 " rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SOUTAI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.22" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SOUTAI) %>">
      <th class="table_title-color w5 " rowspan="2"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SOUTAI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.22" /></a></th>
  </logic:notEqual>

  <logic:notEmpty name="tcd040Form" property="tcd040holMaltiList" scope="request">
    <logic:iterate id="maltiList" name="tcd040Form" property="tcd040holMaltiList" scope="request" indexId="idx">
      <bean:define id="tcdHolValue" name="maltiList" property="thiSid" />
      <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_HOLIDAY) %>">
        <logic:equal name="tcd040Form" property="tcd040SortKeyHoliday" value="<%= String.valueOf(tcdHolValue) %>">
          <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
          <th class="table_title-color w5 " rowspan="2">
            <a href="#!" onclick="return onHolidayLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_HOLIDAY) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>', '<%= String.valueOf(tcdHolValue) %>')"><bean:write name="maltiList" property="thiName"/>
            <span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
            </a>
          </th>
          </logic:equal>
          <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
            <th class="table_title-color w5 mwp5" rowspan="2">
              <a href="#!" onclick="return onHolidayLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_HOLIDAY) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>', '<%= String.valueOf(tcdHolValue) %>')"><bean:write name="maltiList" property="thiName"/>
              <span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
              </a>
            </th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="tcd040Form" property="tcd040SortKeyHoliday" value="<%= String.valueOf(tcdHolValue) %>">
          <th class="table_title-color w5 mwp5" rowspan="2">
            <a href="#!" onclick="return onHolidayLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_HOLIDAY) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>', '<%= String.valueOf(tcdHolValue) %>')">
              <bean:write name="maltiList" property="thiName"/>
            </a>
          </th>
        </logic:notEqual>
      </logic:equal>
      <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_HOLIDAY) %>">
        <th class="table_title-color w5 mwp5" rowspan="2">
          <a href="#!" onclick="return onHolidayLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_HOLIDAY) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>', '<%= String.valueOf(tcdHolValue) %>')">
            <bean:write name="maltiList" property="thiName"/>
          </a>
        </th>
      </logic:notEqual>
    </logic:iterate>
  </logic:notEmpty>

    </tr>

    <tr class="no_w">

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADODAYS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADODAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADODAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADODAYS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADODAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /></a></th>
  </logic:notEqual>

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADOHOURS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADOHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADOHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADOHOURS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KADOHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /></a></th>
  </logic:notEqual>

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANDAYS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
  <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANDAYS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /></a></th>
  </logic:notEqual>

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANHOURS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANHOURS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_ZANHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /></a></th>
  </logic:notEqual>

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYADAYS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYADAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYADAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYADAYS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYADAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /></a></th>
  </logic:notEqual>

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYAHOURS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYAHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
  <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYAHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYAHOURS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SINYAHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /></a></th>
  </logic:notEqual>
<%-- 休出 --%>
  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEDAYS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEDAYS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.number.days" /></a></th>
  </logic:notEqual>

  <logic:equal name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEHOURS) %>">
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
    </logic:equal>
    <logic:equal name="tcd040Form" property="tcd040OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
    </logic:equal>
  </logic:equal>
  <logic:notEqual name="tcd040Form" property="tcd040SortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEHOURS) %>">
      <th class="table_title-color w5"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_KYUDEHOURS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.time" /></a></th>
  </logic:notEqual>
    </tr>

<logic:notEmpty name="tcd040Form" property="tcd040ResultList" scope="request">
  <logic:iterate id="mngMdl" name="tcd040Form" property="tcd040ResultList" scope="request" indexId="idx" type="TcdManagerModel">
    <!--基準-->
    <logic:equal name="idx" value="0">
    <tr class="bgC_lightGray fw_b">
      <td class="bgC_lightGray fs_13"><gsmsg:write key="tcd.tcd040.03" /></td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r"><bean:write name="mngMdl" property="kadoBaseDaysStr" /></td>
      <td class="bgC_lightGray fs_13 txt_r"><bean:write name="mngMdl" property="kadoBaseHoursStr" /></td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
      <logic:notEmpty name="tcd040Form" property="tcd040holMaltiList" scope="request">
        <logic:iterate id="maltiList" name="tcd040Form" property="tcd040holMaltiList" scope="request" indexId="idx">
          <td class="bgC_lightGray fs_13 txt_r">&nbsp;</td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>
    </logic:equal>

    <tr>
      <td class="no_w">
        <span class="normal_link <%=UserUtil.getCSSClassNameNormal(mngMdl.getUsrUkoFlg())%>"><bean:write name="mngMdl" property="userName" /></span>
      </td>
      <td class="txt_l txt_m no_w">
        <span class="normal_link <%=UserUtil.getCSSClassNameNormal(mngMdl.getUsrUkoFlg())%>"><bean:write name="mngMdl" property="userSyainNo" /></span>
      </td>
      <td class="txt_r"><bean:write name="mngMdl" property="kadoDaysStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="kadoHoursStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="zangyoDaysStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="zangyoHoursStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="sinyaDaysStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="sinyaHoursStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="kyusyutuDays" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="kyusyutuHours" /></td>

      <td class="txt_r"><bean:write name="mngMdl" property="chikokuTimesStr" /></td>
      <td class="txt_r"><bean:write name="mngMdl" property="soutaiTimesStr" /></td>

      <logic:notEmpty name="tcd040Form" property="tcd040holMaltiList" scope="request">
        <bean:define id="usrTcdMdl" name="mngMdl" type="TcdTotalValueModel" />
        <logic:iterate id="holMdl" name="tcd040Form" property="tcd040holMaltiList" scope="request" indexId="idx" type="TcdHolidayInfModel">
          <td class="txt_r"><%= usrTcdMdl.getHolDaysStr(holMdl.getThiSid()) %></td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>
  </logic:iterate>

    <tr class="fw_b">
      <td class="bgC_lightGray no_w fs_13"><gsmsg:write key="tcd.tcd040.02" /></td>
      <td class="bgC_lightGray txt_r fs_13">&nbsp;</td>
      <bean:define id="averageData" name="tcd040Form" property="averageData" />
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="kadoDays" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="kadoHours" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="zangyoDays" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="zangyoHours" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="sinyaDays" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="sinyaHours" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="kyusyutuDays" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="kyusyutuHours" /></td>

      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="chikokuTimes" /></td>
      <td class="bgC_lightGray txt_r fs_13"><bean:write name="averageData" property="soutaiTimes" /></td>
      
      <logic:notEmpty name="tcd040Form" property="tcd040holMaltiList" scope="request">
        <bean:define id="averageMdl" name="tcd040Form" property="averageData" type="TcdTotalValueModel" />
        <logic:iterate id="holMdl" name="tcd040Form" property="tcd040holMaltiList" scope="request" indexId="idx" type="TcdHolidayInfModel">
          <td class="bgC_lightGray txt_r fs_13"><%= averageMdl.getHolDaysStr(holMdl.getThiSid()) %></td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>

    <tr class="bgC_lightGray fw_b">
      <td class="no_w fs_13"><gsmsg:write key="tcd.tcd040.11" /></td>
      <td class="txt_r fs_13">&nbsp;</td>
      <bean:define id="totalData" name="tcd040Form" property="totalData" />
      <td class="txt_r fs_13"><bean:write name="totalData" property="kadoDays" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="kadoHours" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="zangyoDays" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="zangyoHours" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="sinyaDays" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="sinyaHours" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="kyusyutuDays" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="kyusyutuHours" /></td>

      <td class="txt_r fs_13"><bean:write name="totalData" property="chikokuTimes" /></td>
      <td class="txt_r fs_13"><bean:write name="totalData" property="soutaiTimes" /></td>
      
      <logic:notEmpty name="tcd040Form" property="tcd040holMaltiList" scope="request">
        <bean:define id="totalMdl" name="tcd040Form" property="totalData" type="TcdTotalValueModel" />
        <logic:iterate id="holMdl" name="tcd040Form" property="tcd040holMaltiList" scope="request" indexId="idx" type="TcdHolidayInfModel">
          <td class="txt_r"><%= totalMdl.getHolDaysStr(holMdl.getThiSid()) %></td>
        </logic:iterate>
      </logic:notEmpty>
    </tr>

  </logic:notEmpty>
  </table>

</div>


</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>
