<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <title>GROUPSESSION <gsmsg:write key="tcd.06" /></title>
</head>

<body>
<html:form action="/timecard/tcd090kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<logic:notEmpty name="tcd090knForm" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd090knForm" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="tcd090LockFlg" />
<html:hidden property="tcd090LockStrike" />
<html:hidden property="tcd090LockBiko" />
<html:hidden property="tcd090LockLate" />
<html:hidden property="tcd090LockHoliday" />
<html:hidden property="tcd090LockTimezone" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd090kn.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd090kn_submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd090kn_back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  </div>

  <div class="w100 txt_l cl_fontWarn">
   <gsmsg:write key="tcd.45" />
  </div>

  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.20" />
      </th>
      <td class="w75">
        <logic:equal name="tcd090knForm" property="tcd090LockStrike" value="0">
          <gsmsg:write key="tcd.tcd090.03" />
        </logic:equal>
        <logic:equal name="tcd090knForm" property="tcd090LockStrike" value="1">
          <gsmsg:write key="tcd.tcd090.07" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.29" />
      </th>
      <td class="w75">
        <logic:equal name="tcd090knForm" property="tcd090LockFlg" value="0">
          <gsmsg:write key="tcd.tcd090.03" />
        </logic:equal>
        <logic:equal name="tcd090knForm" property="tcd090LockFlg" value="1">
          <gsmsg:write key="tcd.tcd090.07" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.25" />
      </th>
      <td class="w75">
        <logic:equal name="tcd090knForm" property="tcd090LockBiko" value="0">
          <gsmsg:write key="tcd.tcd090kn.03" />
        </logic:equal>
        <logic:equal name="tcd090knForm" property="tcd090LockBiko" value="1">
          <gsmsg:write key="tcd.tcd090kn.02" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.17" />
      </th>
      <td class="w75">
        <logic:equal name="tcd090knForm" property="tcd090LockLate" value="0">
          <gsmsg:write key="tcd.tcd090.03" />
        </logic:equal>
        <logic:equal name="tcd090knForm" property="tcd090LockLate" value="1">
          <gsmsg:write key="tcd.tcd090.07" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.39" />
      </th>
      <td class="w75">
        <logic:equal name="tcd090knForm" property="tcd090LockHoliday" value="0">
          <gsmsg:write key="tcd.tcd090.03" />
        </logic:equal>
        <logic:equal name="tcd090knForm" property="tcd090LockHoliday" value="1">
          <gsmsg:write key="tcd.tcd090.07" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.185" />
      </th>
      <td class="w75">
        <logic:equal name="tcd090knForm" property="tcd090LockTimezone" value="0">
          <gsmsg:write key="tcd.tcd090.03" />
        </logic:equal>
        <logic:equal name="tcd090knForm" property="tcd090LockTimezone" value="1">
          <gsmsg:write key="tcd.tcd090.07" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd090kn_submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd090kn_back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>