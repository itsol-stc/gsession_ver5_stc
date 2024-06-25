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
<meta name="format-detection" content="telephone=no">

<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../schedule/js/sch085.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="lmtEnableDisable();">
<html:form action="/schedule/sch085">

<input type="hidden" name="CMD" value="">

<html:hidden property="backScreen" />
<html:hidden property="dspMod" />
<html:hidden property="listMod" />
<html:hidden property="sch010DspDate" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="sch010SelectUsrKbn" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch010DspGpSid" />
<html:hidden property="sch010searchWord" />
<html:hidden property="sch020SelectUsrSid" />
<html:hidden property="sch030FromHour" />

<html:hidden property="sch100PageNum" />
<html:hidden property="sch100Slt_page1" />
<html:hidden property="sch100Slt_page2" />
<html:hidden property="sch100OrderKey1" />
<html:hidden property="sch100SortKey1" />
<html:hidden property="sch100OrderKey2" />
<html:hidden property="sch100SortKey2" />

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

<html:hidden property="sch100SltGroup" />
<html:hidden property="sch100SltUser" />
<html:hidden property="sch100SltStartYearFr" />
<html:hidden property="sch100SltStartMonthFr" />
<html:hidden property="sch100SltStartDayFr" />
<html:hidden property="sch100SltStartYearTo" />
<html:hidden property="sch100SltStartMonthTo" />
<html:hidden property="sch100SltStartDayTo" />
<html:hidden property="sch100SltEndYearFr" />
<html:hidden property="sch100SltEndMonthFr" />
<html:hidden property="sch100SltEndDayFr" />
<html:hidden property="sch100SltEndYearTo" />
<html:hidden property="sch100SltEndMonthTo" />
<html:hidden property="sch100SltEndDayTo" />
<html:hidden property="sch100KeyWordkbn" />

<logic:notEmpty name="sch085Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch085Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch085Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch085Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch085Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch085Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch085Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch085Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch085Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch085Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch085kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch085back');">
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
        <gsmsg:write key="cmn.sortby.members" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio name="sch085Form" styleId="sch085MemDspKbn_01" property="sch085MemDspKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MEM_DSP_ADM) %>" onclick="lmtEnableDisable();" />
          <label for="sch085MemDspKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch085Form" styleId="sch085MemDspKbn_02" styleClass="ml10" property="sch085MemDspKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MEM_DSP_USR) %>" onclick="lmtEnableDisable();" />
          <label for="sch085MemDspKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div id="lmtinput">
          <gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.user.defaultset" />
        </div>
        <div class="mt5">
          <gsmsg:write key="cmn.first.key" />：
          <!-- キー1 -->
          <html:select property="sch085AdSortKey1">
            <html:optionsCollection name="sch085Form" property="sch085SortKeyLabel" value="value" label="label" />
          </html:select>
          <span class="ml10 verAlignMid">
            <html:radio name="sch085Form" property="sch085AdSortOrder1" styleId="sch085AdSortOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
            <label for="sch085AdSortOrder10"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio name="sch085Form" property="sch085AdSortOrder1" styleId="sch085AdSortOrder11" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
            <label for="sch085AdSortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
          </span>
        </div>
        <div class="mt5">
          <gsmsg:write key="cmn.second.key" />：
          <!-- キー2 -->
          <html:select property="sch085AdSortKey2">
            <html:optionsCollection name="sch085Form" property="sch085SortKeyLabel" value="value" label="label" />
          </html:select>
          <span class="ml10 verAlignMid">
            <html:radio name="sch085Form" property="sch085AdSortOrder2" styleId="sch085AdSortOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
            <label for="sch085AdSortOrder20"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio name="sch085Form" property="sch085AdSortOrder2" styleId="sch085AdSortOrder21" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
            <label for="sch085AdSortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="schedule.sch093.2" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch085Form" styleId="sch085DspGrp_01" property="sch085DefGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) %>" />
          <label for="sch085DspGrp_01"><gsmsg:write key="schedule.sch085.2" /></label>
          <html:radio name="sch085Form" styleId="sch085DspGrp_02" property="sch085DefGroup" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SAD_DSP_GROUP_USER) %>" />
          <label for="sch085DspGrp_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('sch085kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch085back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>