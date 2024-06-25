<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-timeZoneChart.tld" prefix="timeZoneChart" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.47" /></title>
</head>

<body>
<html:form action="/timecard/tcd170">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<html:hidden property="tcd170initFlg" />
<html:hidden property="tcd170Mode" />
<html:hidden property="tcd170EditSid" />
<logic:equal name="tcd170Form" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
  <input type="hidden" name="helpPrm" value="1">
</logic:equal>
<logic:notEqual name="tcd170Form" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
  <input type="hidden" name="helpPrm" value="0">
</logic:notEqual>

<logic:notEmpty name="tcd170Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd170Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span>
      <logic:equal name="tcd170Form" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_ADD) %>">
        <gsmsg:write key="tcd.tcd170.05" />
      </logic:equal>
      <logic:equal name="tcd170Form" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
        <gsmsg:write key="tcd.tcd170.06" />
      </logic:equal>
    </li>
    <li>
      <div>
        <logic:equal name="tcd170Form" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
          <logic:equal name="tcd170Form" property="tcd170useHolFlg" value="0">
            <button type="button" class="baseBtn ml8" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
        </logic:equal>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd170back')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">

<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>

  <table class="table-left">
    <!-- 休日区分名 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd160.02" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td width="80%" class="td_sub_detail" colspan="3">
        <html:text name="tcd170Form" property="tcd170HolidayName" styleClass="wp300" maxlength="10" />
      </td>
    </tr>
    <!-- 休日集計区分 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd170.01" />
      </th>
      <td>
        <div class="fs_13 mb5"><gsmsg:write key="cmn.comments" /><gsmsg:write key="tcd.tcd170.02" /></div>
        <span  class="verAlignMid">
          <html:radio styleId="tcd170HoliTotalKbn_2" name="tcd170Form" property="tcd170HoliTotalKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_UNSELECT) %>" /><label for="tcd170HoliTotalKbn_2"><gsmsg:write key="cmn.without.specifying" /></label>
          <html:radio styleId="tcd170HoliTotalKbn_1" styleClass="ml10" name="tcd170Form" property="tcd170HoliTotalKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_YUUKYUU) %>" /><label for="tcd170HoliTotalKbn_1"><gsmsg:write key="tcd.03" /></label>
          <html:radio styleId="tcd170HoliTotalKbn_0" styleClass="ml10" name="tcd170Form" property="tcd170HoliTotalKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_KEKKIN) %>" /><label for="tcd170HoliTotalKbn_0"><gsmsg:write key="tcd.34" /></label>
        </span>
      </td>
    </tr>
    <!-- 使用制限 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd170.03" />
      </th>
      <td>
        <div class="fs_13 mb5"><gsmsg:write key="cmn.comments" /><gsmsg:write key="tcd.tcd170.04" /></div>
        <span  class="verAlignMid">
          <html:radio styleId="tcd170Limit_0" name="tcd170Form" property="tcd170Limit" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.USE_KBN_OK) %>" /><label for="tcd170Limit_0"><gsmsg:write key="tcd.tcd120.03" /></label>
          <html:radio styleId="tcd170Limit_1" styleClass="ml10" name="tcd170Form" property="tcd170Limit" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.USE_KBN_NG) %>" /><label for="tcd170Limit_1"><gsmsg:write key="tcd.tcd060.10" /></label>
        </span>
      </td>
    </tr>
    <!-- 休日内容区分 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd170.07" />
      </th>
      <td>
        <span  class="verAlignMid">
          <html:radio styleId="tcd170HoliContentKbn_0" name="tcd170Form" property="tcd170HoliContentKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOLIDAY_CONTENT_KBN_OK) %>" /><label for="tcd170HoliContentKbn_0"><gsmsg:write key="tcd.tcd120.03" /></label>
          <html:radio styleId="tcd170HoliContentKbn_1" styleClass="ml10" name="tcd170Form" property="tcd170HoliContentKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOLIDAY_CONTENT_KBN_NG) %>" /><label for="tcd170HoliContentKbn_1"><gsmsg:write key="tcd.tcd060.10" /></label>
        </span>
      </td>
    </tr>
  </table>
</div>

<div class="footerBtn_block txt_r w80 mrl_auto">
  <logic:equal name="tcd170Form" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
    <logic:equal name="tcd170Form" property="tcd170useHolFlg" value="0">
      <button type="button" class="baseBtn ml8" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
  </logic:equal>
  <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd170back')">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <gsmsg:write key="cmn.back" />
  </button>
</div>


</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>