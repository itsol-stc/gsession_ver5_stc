<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstSchedule" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.108" /> [<gsmsg:write key="schedule.sch091.1" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../schedule/js/sch086.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body onload="changeEnableDisableTime(); changeEnableDisable(); changeEnableDisablePublic(); changeEnableDisableSame();">
<html:form action="/schedule/sch086">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="hourDivision" value="<bean:write name="sch086Form" property="sch086HourDiv" />" />
<input type="hidden" name="sch086DefFrH" value="">
<input type="hidden" name="sch086DefFrM" value="">
<input type="hidden" name="sch086DefToH" value="">
<input type="hidden" name="sch086DefToM" value="">

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
<logic:notEmpty name="sch086Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch086Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch086Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch086Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch086Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch086Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch086Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch086Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch086Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch086Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sch086init" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParams();buttonPush('sch086Ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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
      <gsmsg:write key="cmn.time" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio name="sch086Form" styleId="sch086TimeType_01" property="sch086TimeType" value="<%= String.valueOf(GSConstSchedule.SAD_INIEDIT_STYPE_ADM) %>" onclick="changeEnableDisableTime();" />
          <label for="sch086TimeType_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch086Form" styleId="sch086TimeType_02" styleClass="ml10" property="sch086TimeType" value="<%= String.valueOf(GSConstSchedule.SAD_INIEDIT_STYPE_USER) %>" onclick="changeEnableDisableTime();" />
          <label for="sch086TimeType_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div class="settingForm_separator" id="schTimeArea">
          <!-- 開始時間 -->
          <gsmsg:write key="cmn.starttime" />：
          <div class="verAlignMid">
            <span class="clockpicker_fr pos_rel display_flex input-group">
              <html:text name="sch086Form" property="sch086DefFrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
              <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
            </span>
          </div>
          <br>
          <!-- 終了時間 -->
          <gsmsg:write key="cmn.endtime" />：
          <div class="mt5 verAlignMid">
            <span class="clockpicker_fr pos_rel display_flex input-group">
              <html:text name="sch086Form" property="sch086DefToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
              <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
            </span>
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th>
       <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch086Form" styleId="sch086EditType_01" property="sch086EditType" value="<%= String.valueOf(GSConstSchedule.SAD_INIEDIT_STYPE_ADM) %>" onclick="changeEnableDisable();" />
          <label for="sch086EditType_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch086Form" styleId="sch086EditType_02" styleClass="ml10" property="sch086EditType" value="<%= String.valueOf(GSConstSchedule.SAD_INIEDIT_STYPE_USER) %>" onclick="changeEnableDisable();" />
          <label for="sch086EditType_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div class="settingForm_separator" id="schEditArea">
          <span class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Edit" styleId="sch086Edit0" value="<%= String.valueOf(GSConstSchedule.EDIT_CONF_NONE) %>" />
            <label for="sch086Edit0"><gsmsg:write key="cmn.nolimit" /></label>
            <html:radio name="sch086Form" property="sch086Edit" styleId="sch086Edit1" styleClass="ml10" value="<%= String.valueOf(GSConstSchedule.EDIT_CONF_OWN) %>" />
            <label for="sch086Edit1"><gsmsg:write key="cmn.only.principal.or.registant" /></label>
            <html:radio name="sch086Form" property="sch086Edit" styleId="sch086Edit2" styleClass="ml10" value="<%= String.valueOf(GSConstSchedule.EDIT_CONF_GROUP) %>" />
            <label for="sch086Edit2"><gsmsg:write key="cmn.only.affiliation.group.membership" /></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="cmn.public" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch086Form" styleId="sch086PublicType_01" property="sch086PublicType" value="<%= String.valueOf(GSConstSchedule.SAD_INIPUBLIC_STYPE_ADM) %>" onclick="changeEnableDisablePublic();" />
          <label for="sch086PublicType_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch086Form" styleId="sch086PublicType_02" styleClass="ml10" property="sch086PublicType" value="<%= String.valueOf(GSConstSchedule.SAD_INIPUBLIC_STYPE_USER) %>" onclick="changeEnableDisablePublic();" />
          <label for="sch086PublicType_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div class="settingForm_separator" id="schPublicArea">
          <div class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Public" styleId="sch086public0" styleClass="js_public" value="<%= String.valueOf(GSConstSchedule.DSP_PUBLIC) %>" />
            <label for="sch086public0" class="mr10"><gsmsg:write key="cmn.public" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Public" styleId="sch086public1" styleClass="js_public" value="<%= String.valueOf(GSConstSchedule.DSP_NOT_PUBLIC) %>" />
            <label for="sch086public1" class="mr10"><gsmsg:write key="cmn.private" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Public" styleId="sch086public2" styleClass="js_public" value="<%= String.valueOf(GSConstSchedule.DSP_YOTEIARI) %>" />
            <label for="sch086public2" class="mr10"><gsmsg:write key="schedule.5" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Public" styleId="sch086public5" styleClass="js_public" value="<%= String.valueOf(GSConstSchedule.DSP_TITLE) %>" />
            <label for="sch086public5" class="mr10"><gsmsg:write key="schedule.38" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Public" styleId="sch086public3" styleClass="js_public" value="<%= String.valueOf(GSConstSchedule.DSP_BELONG_GROUP) %>" />
            <label for="sch086public3" class="mr10"><gsmsg:write key="schedule.28" /></label>
          </div><!--
       --><div class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Public" styleId="sch086public4" styleClass="js_public" value="<%=String.valueOf(GSConstSchedule.DSP_USRGRP)%>" />
            <label for="sch086public4"><gsmsg:write key="schedule.37" /></label>
          </div>
          <div class="settingForm_separator w100 js_selectUsrArea">
            <ui:multiselector name="sch086Form" property="sch086PubUsrGrpSidUI" styleClass="hp200" />
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th>
      <gsmsg:write key="schedule.32" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="sch086Form" styleId="sch086SameType_01" property="sch086SameType" value="<%= String.valueOf(GSConstSchedule.SAD_INISAME_STYPE_ADM) %>" onclick="changeEnableDisableSame();" />
          <label for="sch086SameType_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch086Form" styleId="sch086SameType_02" property="sch086SameType" styleClass="ml10" value="<%= String.valueOf(GSConstSchedule.SAD_INISAME_STYPE_USER) %>" onclick="changeEnableDisableSame();" />
          <label for="sch086SameType_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div class="settingForm_separator" id="schSameArea">
          <span class="verAlignMid">
            <html:radio name="sch086Form" property="sch086Same" styleId="sch086Same0" value="<%= String.valueOf(GSConstSchedule.SAME_EDIT_ON) %>" />
            <label for="sch086Same0"><gsmsg:write key="schedule.34" /></label>
            <html:radio name="sch086Form" property="sch086Same" styleId="sch086Same1" styleClass="ml10" value="<%= String.valueOf(GSConstSchedule.SAME_EDIT_OFF) %>" />
            <label for="sch086Same1"><gsmsg:write key="schedule.33" /></label>
          </span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParams();buttonPush('sch086Ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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