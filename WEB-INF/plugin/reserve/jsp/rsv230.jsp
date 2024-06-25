<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <gsmsg:write key="cmn.default.setting" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>  
<script type="text/javascript" src="../reserve/js/rsv230.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv230">
<input type="hidden" name="CMD" value="">
<logic:equal name="rsv230Form" property="rsv230PeriodFlg" value="true">
  <input type="hidden" name="hourDivision" value="<bean:write name="rsv230Form" property="rsv230HourDiv" />" />
  <input type="hidden" name="rsv230DefFrH" value="">
  <input type="hidden" name="rsv230DefFrM" value="">
  <input type="hidden" name="rsv230DefToH" value="">
  <input type="hidden" name="rsv230DefToM" value="">
</logic:equal>

<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />

<html:hidden property="rsv230SvPeriodFlg" />
<html:hidden property="rsv230SvEditFlg" />
<html:hidden property="rsv230SvPublicFlg" />

<html:hidden property="rsv230initFlg" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv230Form" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv230Form" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv230Form" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv230Form" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:equal name="rsv230Form" property="rsv230EditFlg" value="false">
<html:hidden property="rsv230Edit" />
</logic:equal>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParams();buttonPush_rsv230('rsv230ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv230back');">
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
  <html:errors />
</logic:messagesPresent>
<table class="table-left">
    <%-- 期間 --%>
    <logic:equal name="rsv230Form" property="rsv230PeriodFlg" value="true">
    <tr>
      <th class="w25" id="periodFlg">
        <gsmsg:write key="cmn.period" />
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.starttime" />：
        <div class="verAlignMid">
          <span class="clockpicker_fr pos_rel display_flex input-group">
            <html:text name="rsv230Form" property="rsv230DefFrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <br>
        <gsmsg:write key="cmn.endtime" />：
        <div class="mt5 verAlignMid">
          <span class="clockpicker_fr pos_rel display_flex input-group">
            <html:text name="rsv230Form" property="rsv230DefToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
    </logic:equal>
<%-- 編集権限 --%>
    <logic:equal name="rsv230Form" property="rsv230EditFlg" value="true">
    <tr>
      <th class="w25" id="editFlg">
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio name="rsv230Form" property="rsv230Edit" styleId="rsv230Edit0" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_NONE)%>" />
        <label for="rsv230Edit0" class=""><gsmsg:write key="cmn.nolimit" /></label>
        <html:radio name="rsv230Form" styleClass="ml10" property="rsv230Edit" styleId="rsv230Edit1" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>" />
        <label for="rsv230Edit1" class=""><gsmsg:write key="cmn.only.principal.or.registant" /></label>
        <html:radio name="rsv230Form" styleClass="ml10" property="rsv230Edit" styleId="rsv230Edit2" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>" />
        <label for="rsv230Edit2"><gsmsg:write key="cmn.only.affiliation.group.membership" /></label>
       </div>
      </td>
    </tr>
   </logic:equal>
<%-- 公開区分 --%>
    <logic:equal name="rsv230Form" property="rsv230PublicFlg" value="true">
    <tr>
      <th class="w25" id="publicFlg">
        <gsmsg:write key="cmn.public.kbn" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio name="rsv230Form" styleClass="js_public" property="rsv230Public" styleId="rsv230Public0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL) %>" />
        <label for="rsv230Public0" class="mr10"><gsmsg:write key="cmn.public" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv230Form" styleClass="js_public" property="rsv230Public" styleId="rsv230Public1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS) %>" />
        <label for="rsv230Public1" class="mr10"><gsmsg:write key="reserve.175" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv230Form" styleClass="js_public" property="rsv230Public" styleId="rsv230Public4" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE) %>" />
        <label for="rsv230Public4" class="mr10"><gsmsg:write key="reserve.189" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv230Form" styleClass="js_public" property="rsv230Public" styleId="rsv230Public2" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP) %>" />
        <label for="rsv230Public2" class="mr10"><gsmsg:write key="reserve.176" /></label>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv230Form" styleClass="js_public" property="rsv230Public" styleId="rsv230Public3" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP) %>" />
        <label for="rsv230Public3"><gsmsg:write key="reserve.187" /></label>
      </div>
      <div class="settingForm_separator w100 js_selectUsrArea">
        <ui:usrgrpselector name="rsv230Form" property="rsv230PubUsrGrpUI" styleClass="hp215" />
      </div>
      </td>
    </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParams();buttonPush_rsv230('rsv230ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv230back');">
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