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
<html:form action="/timecard/tcd090">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<logic:notEmpty name="tcd090Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd090Form" property="selectDay" scope="request">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.06" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd090_submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd090_back');">
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
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.20" />
      </th>
      <td class="w75">
        <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd090.02" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd090LockStrike_0" name="tcd090Form" property="tcd090LockStrike" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>" /><label for="tcd090LockStrike_0"><gsmsg:write key="tcd.tcd090.03" /></label>
        <html:radio styleId="tcd090LockStrike_1" styleClass="ml10" name="tcd090Form" property="tcd090LockStrike" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>" /><label for="tcd090LockStrike_1"><gsmsg:write key="tcd.tcd090.07" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.29" />
      </th>
      <td class="w75">
        <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd090.04" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd090LockFlg_0" name="tcd090Form" property="tcd090LockFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>" /><label for="tcd090LockFlg_0"><gsmsg:write key="tcd.tcd090.03" /></label>
        <html:radio styleId="tcd090LockFlg_1" styleClass="ml10" name="tcd090Form" property="tcd090LockFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>" /><label for="tcd090LockFlg_1"><gsmsg:write key="tcd.tcd090.07" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.25" />
      </th>
      <td class="w75">
        <span  class="verAlignMid">
        <html:checkbox styleId="tcd090LockBiko" name="tcd090Form" property="tcd090LockBiko" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.BIKO_NECESSARY_FLG) %>"  />
        <label for="tcd090LockBiko"><gsmsg:write key="tcd.tcd090.01" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.17" />
      </th>
      <td class="w75">
        <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd090.05" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd090LockLate_0" name="tcd090Form" property="tcd090LockLate" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>" /><label for="tcd090LockLate_0"><gsmsg:write key="tcd.tcd090.03" /></label>
        <html:radio styleId="tcd090LockLate_1" styleClass="ml10" name="tcd090Form" property="tcd090LockLate" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>" /><label for="tcd090LockLate_1"><gsmsg:write key="tcd.tcd090.07" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.39" />
      </th>
      <td class="w75">
        <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd090.06" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd090LockHoliday_0" name="tcd090Form" property="tcd090LockHoliday" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>" /><label for="tcd090LockHoliday_0"><gsmsg:write key="tcd.tcd090.03" /></label>
        <html:radio styleId="tcd090LockHoliday_1" styleClass="ml10" name="tcd090Form" property="tcd090LockHoliday" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>" /><label for="tcd090LockHoliday_1"><gsmsg:write key="tcd.tcd090.07" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.185" />
      </th>
      <td class="w75">
        <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd090.08" /></div>
        <span  class="verAlignMid">
        <html:radio styleId="tcd090LockTimezone_0" name="tcd090Form" property="tcd090LockTimezone" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.UNLOCK_FLG) %>" /><label for="tcd090LockTimezone_0"><gsmsg:write key="tcd.tcd090.03" /></label>
        <html:radio styleId="tcd090LockTimezone_1" styleClass="ml10" name="tcd090Form" property="tcd090LockTimezone" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.LOCK_FLG) %>" /><label for="tcd090LockTimezone_1"><gsmsg:write key="tcd.tcd090.07" /></label>
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd090_submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd090_back');">
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