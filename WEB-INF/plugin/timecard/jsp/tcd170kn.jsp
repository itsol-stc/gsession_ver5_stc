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
<html:form action="/timecard/tcd170kn">
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
<html:hidden property="tcd170HolidayName" />
<html:hidden property="tcd170HoliTotalKbn" />
<html:hidden property="tcd170Limit" />
<html:hidden property="tcd170HoliContentKbn" />


<logic:notEmpty name="tcd170knForm" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd170knForm" property="selectDay" scope="request">
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
      <logic:equal name="tcd170knForm" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_ADD) %>">
        <gsmsg:write key="tcd.tcd170kn.01" />
      </logic:equal>
      <logic:equal name="tcd170knForm" property="tcd170Mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
        <gsmsg:write key="tcd.tcd170kn.01" />
      </logic:equal>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('kakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd170knBack')">
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
        <gsmsg:write key="tcd.tcd160.02" />
      </th>
      <td width="80%" class="td_sub_detail" colspan="3">
        <bean:write name="tcd170knForm" property="tcd170HolidayName"/>
      </td>
    </tr>
    <!-- 休日集計区分 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd170.01" />
      </th>
      <td>
        <span  class="verAlignMid">
          <logic:equal name="tcd170knForm" property="tcd170HoliTotalKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_UNSELECT) %>">
            <gsmsg:write key="cmn.without.specifying" />
          </logic:equal>
          <logic:equal name="tcd170knForm" property="tcd170HoliTotalKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_YUUKYUU) %>">
            <gsmsg:write key="tcd.03" />
          </logic:equal>
          <logic:equal name="tcd170knForm" property="tcd170HoliTotalKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOL_KBN_KEKKIN) %>">
            <gsmsg:write key="tcd.34" />
          </logic:equal>
        </span>
      </td>
    </tr>

    <!-- 使用制限 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd170.03" />
      </th>
      <td>
        <span  class="verAlignMid">
          <logic:equal name="tcd170knForm" property="tcd170Limit" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.USE_KBN_OK) %>">
            <gsmsg:write key="tcd.tcd120.03" />
          </logic:equal>
          <logic:equal name="tcd170knForm" property="tcd170Limit" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.USE_KBN_NG) %>">
            <gsmsg:write key="tcd.tcd060.10" />
          </logic:equal>
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
          <logic:equal name="tcd170knForm" property="tcd170HoliContentKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOLIDAY_CONTENT_KBN_OK) %>">
            <gsmsg:write key="tcd.tcd120.03" />
          </logic:equal>
          <logic:equal name="tcd170knForm" property="tcd170HoliContentKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOLIDAY_CONTENT_KBN_NG) %>">
            <gsmsg:write key="tcd.tcd060.10" />
          </logic:equal>
        </span>
      </td>
    </tr>
  </table>
</div>

<div class="footerBtn_block txt_r w80 mrl_auto">
  <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('kakutei');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
    <gsmsg:write key="cmn.ok" />
  </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd170knBack')">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <gsmsg:write key="cmn.back" />
  </button>
</div>


</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>