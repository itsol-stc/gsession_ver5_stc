<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%
   String maxLengthSyosai = String.valueOf(1000);
%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp061.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp061_message.jsp" />

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="showLengthId($('#inputstr')[0], <%= maxLengthSyosai %>, 'inputlength');" onunload="calWindowClose();companyWindowClose();">

<input type="hidden" name="helpPrm" value="<bean:write name="ntp061Form" property="ntp060ProcMode" />">

<logic:equal name="ntp061Form" property="ntp061PopKbn" value="0">
  <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
</logic:equal>

<html:form action="/nippou/ntp061">

<div id="ntp061CompanyIdArea">
<html:hidden property="ntp061CompanySid" />
</div>

<div id="ntp061CompanyBaseIdArea">
<html:hidden property="ntp061CompanyBaseSid" />
</div>

<input type="hidden" name="CMD" value="">

<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp" />

<logic:notEmpty name="ntp061Form" property="ntp060Mikomi" scope="request">
  <logic:iterate id="mikomi" name="ntp061Form" property="ntp060Mikomi" scope="request">
    <input type="hidden" name="ntp060Mikomi" value="<bean:write name="mikomi"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ntp061Form" property="ntp060Syodan" scope="request">
  <logic:iterate id="syodan" name="ntp061Form" property="ntp060Syodan" scope="request">
    <input type="hidden" name="ntp060Syodan" value="<bean:write name="syodan"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="yearRangeMinFr" value="5">
<input type="hidden" name="yearRangeMaxFr" value="5">
<input type="hidden" name="yearRangeMinTo" value="5">
<input type="hidden" name="yearRangeMaxTo" value="5">
<html:hidden property="ntp060ProcMode" />
<html:hidden property="ntp060NanSid" />
<html:hidden property="ntp061ReturnPage" />
<html:hidden property="ntp061InitFlg" />
<html:hidden property="ntp061TourokuName" />
<html:hidden property="ntp061TourokuUsrUkoFlg" />
<html:hidden property="ntp061PopKbn" />
<html:hidden property="ntp061AddCompFlg" />
<html:hidden property="ntp061Date" />
<html:hidden property="ntp061AnkenSid" />
<html:hidden property="ntp061SvCompanySid" />
<html:hidden property="ntp061SvCompanyCode" />
<html:hidden property="ntp061SvCompanyName" />
<html:hidden property="ntp061SvCompanyBaseSid" />
<html:hidden property="ntp061SvCompanyBaseName" />

<html:hidden property="ntp200NanSid" />
<html:hidden property="ntp200ProcMode" />
<html:hidden property="ntp200InitFlg" />
<html:hidden property="ntp200parentPageId" />
<html:hidden property="ntp200RowNumber" />

<logic:notEmpty name="ntp061Form" property="ntp061ChkShohinSidList" scope="request">
  <logic:iterate id="item" name="ntp061Form" property="ntp061ChkShohinSidList" scope="request">
    <input type="hidden" name="ntp061ChkShohinSidList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>


<!--　BODY -->
<%-------------------------------- 案件登録画面 --------------------------------%>

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <logic:notEqual name="ntp061Form" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
      <li class="pageTitle_subFont">
       <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.entry" />
      </li>
    </logic:notEqual>
    <logic:equal name="ntp061Form" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
      <li class="pageTitle_subFont">
       <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.edit" />
      </li>
    </logic:equal>
    <li>
      <logic:equal name="ntp061Form" property="ntp061PopKbn" value="0">
        <logic:equal name="ntp061Form" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.change" />" onclick="return buttonPush('okNtp061');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
            <gsmsg:write key="cmn.change" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonCopy('061_copy', 'add');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
            <gsmsg:write key="cmn.register.copy2" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush('del');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <logic:notEqual name="ntp061Form" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="return buttonPush('okNtp061')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <gsmsg:write key="cmn.entry" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:equal>
      <logic:notEqual name="ntp061Form" property="ntp061PopKbn" value="0">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="return buttonPush('okNtp061pop')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <gsmsg:write key="cmn.entry" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp200');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:notEqual>
    </li>
  </ul>
</div>

<div class="wrapper w90 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left w100">
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="w80">
        <bean:define id="userUkoFlg" name="ntp061Form" property="ntp061TourokuUsrUkoFlg" type="Integer"/>
        <span class="<%=UserUtil.getCSSClassNameNormal(userUkoFlg)%>"><bean:write name="ntp061Form" property="ntp061TourokuName" /></span>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.72" />
      </th>
      <td class="w80">
        <bean:write name="ntp061Form" property="ntp061Date" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.29" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="ntp061Form" property="ntp061NanCode" maxlength="8" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.57" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="ntp061Form" property="ntp061NanName" maxlength="100" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.73" />
      </th>
      <td class="w80">
        <logic:equal name="ntp061Form" property="ntp061PopKbn" value="0">
          <textarea name="ntp061NanSyosai" rows="10" class="w100" onkeyup="showLengthStr(value, <%= maxLengthSyosai %>, 'inputlength');" id="inputstr"><bean:write name="ntp061Form" property="ntp061NanSyosai" /></textarea>
          <span class="formCounter"><gsmsg:write key="wml.js.15" /></span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthSyosai %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </logic:equal>
        <logic:notEqual name="ntp061Form" property="ntp061PopKbn" value="0">
          <textarea name="ntp061NanSyosai" rows="5" class="w100" onkeyup="showLengthStr(value, <%= maxLengthSyosai %>, 'inputlength');" id="inputstr"><bean:write name="ntp061Form" property="ntp061NanSyosai" /></textarea>
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="cmn.staff" />
      </th>
      <td class="w80">
        <ui:usrgrpselector name="ntp061Form" property="tantoListUI" styleClass="hp215" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.15" />（<gsmsg:write key="ntp.16" />）
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="address.7" />：<logic:notEmpty name="ntp061Form" property="ntp061AcoCode"><bean:write name="ntp061Form" property="ntp061AcoCode" /></logic:notEmpty>
        </div>
        <div>
          <logic:notEmpty name="ntp061Form" property="ntp061CompanyName">
            <bean:write name="ntp061Form" property="ntp061CompanyName" />&nbsp;&nbsp;&nbsp;<bean:write name="ntp061Form" property="ntp061CompanyBaseName" />
            <a href="#!" onclick="deleteDspCompany();" class="ml5 mr10">
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png">
            </a>
          </logic:notEmpty>
          <button type="button" class="baseBtn js_adrBtn">
            <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif">
            <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png">
            <gsmsg:write key="addressbook" />
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.58" />
      </th>
      <td class="w80">
        <div>
          <button type="button" class="baseBtn js_itmAddBtn" value="<gsmsg:write key="cmn.add" />">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delShohin');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </div>
        <div>
          <html:select name="ntp061Form" property="ntp061SelectShohin" size="5" multiple="true" styleClass="wp300 hp100">
            <logic:notEmpty name="ntp061Form" property="ntp061ShohinList">
              <html:optionsCollection name="ntp061Form" property="ntp061ShohinList" value="value" label="label" />
            </logic:notEmpty>
            <option value="-1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
          </html:select>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.61" />/<gsmsg:write key="ntp.62" />
      </th>
      <td class="w80">
        <!-- 業務コンボ -->
        <html:select name="ntp061Form" property="ntp061NgySid" styleClass="wp130" onchange="return buttonPush('changeGyomu');">
          <logic:notEmpty name="ntp061Form" property="ntp060GyomuList">
            <html:optionsCollection name="ntp061Form" property="ntp060GyomuList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
        <!-- プロセスコンボ -->
        <html:select name="ntp061Form" property="ntp061NgpSid" styleClass="wp130 ml10">
          <logic:notEmpty name="ntp061Form" property="ntp060ProcessList">
            <html:optionsCollection name="ntp061Form" property="ntp060ProcessList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.63" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ntp061Form" property="ntp061NanMikomi" styleId="ntp061NanMikomi0" value="0" /><label for="ntp061NanMikomi0">10%</label>
          <html:radio name="ntp061Form" property="ntp061NanMikomi" styleId="ntp061NanMikomi1" styleClass="ml10" value="1" /><label for="ntp061NanMikomi1">30%</label>
          <html:radio name="ntp061Form" property="ntp061NanMikomi" styleId="ntp061NanMikomi2" styleClass="ml10" value="2" /><label for="ntp061NanMikomi2">50%</label>
          <html:radio name="ntp061Form" property="ntp061NanMikomi" styleId="ntp061NanMikomi3" styleClass="ml10" value="3" /><label for="ntp061NanMikomi3">70%</label>
          <html:radio name="ntp061Form" property="ntp061NanMikomi" styleId="ntp061NanMikomi4" styleClass="ml10" value="4" /><label for="ntp061NanMikomi4">100%</label>
        </div>
        <div>
          <button type="button" class="baseBtn js_mikomidoBtn" value="<gsmsg:write key="tcd.tcd010.15" />">
            <gsmsg:write key="ntp.33" />
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.53" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:text name="ntp061Form" property="ntp061NanKinMitumori" maxlength="9" styleClass="wp120" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <span class="ml20"><gsmsg:write key="ntp.55" />:</span>
          <html:text name="ntp061Form" property="ntp061MitumoriDate" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker" styleId="mitumoriDate"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#mitumoriDate')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#mitumoriDate')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a class="fw_b todayBtn original-display" onClick="return moveDay($('#mitumoriDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="return moveDay($('#mitumoriDate')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.54" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:text name="ntp061Form" property="ntp061NanKinJutyu" maxlength="9" styleClass="wp120" /><span class="ml5"><gsmsg:write key="project.103" /></span>
          <span class="ml20"><gsmsg:write key="ntp.56" />:</span>
          <html:text name="ntp061Form" property="ntp061JutyuDate" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker" styleId="jutyuDate"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#jutyuDate')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#jutyuDate')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a class="fw_b todayBtn original-display" onClick="return moveDay($('#jutyuDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="return moveDay($('#jutyuDate')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.64" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ntp061Form" property="ntp061NanSyodan" styleId="ntp061NanSyodan0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_CHU) %>" />
          <label for="ntp061NanSyodan0"><gsmsg:write key="ntp.68" /></label>
          <html:radio name="ntp061Form" property="ntp061NanSyodan" styleId="ntp061NanSyodan1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_JYUCHU) %>" />
          <label for="ntp061NanSyodan1"><gsmsg:write key="ntp.69" /></label>
          <html:radio name="ntp061Form" property="ntp061NanSyodan" styleId="ntp061NanSyodan2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_SICHU) %>" />
          <label for="ntp061NanSyodan2"><gsmsg:write key="ntp.7" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.65" />
      </th>
      <td class="w80">
        <!-- 顧客源泉 -->
        <logic:notEmpty name="ntp061Form" property="ntp060ContactList">
          <logic:iterate id="ncnMdl" name="ntp061Form" property="ntp060ContactList" indexId="ncnidx">
            <bean:define  id="ncnVal" name="ncnMdl" property="value" />
            <% String idFor = "ntp061NcnSid" + String.valueOf(ncnidx); %>
            <span class="no_w verAlignMid">
            <html:radio name="ntp061Form" property="ntp061NcnSid" styleId="<%= idFor %>"  value="<%= String.valueOf(ncnVal) %>" />
            <span class="mr10"><label for="<%= idFor %>"><bean:write  name="ncnMdl" property="label" /></label></span>
            </span>
            <wbr>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w txt_l">
        <gsmsg:write key="ntp.71" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ntp061Form" property="ntp061NanState" styleId="ntp061NanState0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.STATE_UNCOMPLETE) %>" />
          <label for="ntp061NanState0"><gsmsg:write key="ntp.74" /></label>
          <html:radio name="ntp061Form" property="ntp061NanState" styleId="ntp061NanState1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.STATE_COMPLETE) %>" />
          <label for="ntp061NanState1"><gsmsg:write key="ntp.75" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <logic:equal name="ntp061Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
        <th class="w20 no_w txt_l" rowspan="2">
          <gsmsg:write key="cmn.setting.permissions" />
        </th>
      </logic:equal>
      <logic:notEqual name="ntp061Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
        <th class="w20 no_w txt_l" rowspan="2">
          <gsmsg:write key="cmn.setting.permissions"  />
        </th>
      </logic:notEqual>
      <td class="w80">
        <div class="w100">
          <div class="verAlignMid">
            <logic:equal name="ntp061Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
              <span class="js_permissionViewLabel"><gsmsg:write key="address.61" />:</span>
            </logic:equal>
            <logic:notEqual name="ntp061Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
              <span class="js_permissionViewLabel" class="display_n" ><gsmsg:write key="address.61" />:</span>
            </logic:notEqual>
            <%--制限なし --%>
            <html:radio name="ntp061Form" property="ntp061NanPermitView" styleId="ntp061NanPermitView0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>" />
            <label for="ntp061NanPermitView0"><gsmsg:write key="cmn.nolimit" /></label>
            <%--担当者のみ --%>
            <html:radio name="ntp061Form" property="ntp061NanPermitView" styleId="ntp061NanPermitView1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>" />
            <label for="ntp061NanPermitView1"><gsmsg:write key="address.62" /></label>
            <%--ユーザグループ指定 --%>
            <html:radio name="ntp061Form" property="ntp061NanPermitView" styleId="ntp061NanPermitView2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>" />
            <label for="ntp061NanPermitView2"><gsmsg:write key="ntp.ntp061.1" /></label>
          </div>
        </div>
        <div id="nanPermitSelect">
          <ui:usrgrpselector name="ntp061Form" property="ntp061NanPermitUI" styleClass="hp300" />
       </div>
      </td>
    </tr>

    <logic:equal name="ntp061Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
      <tr class="js_permissionEditRadio">
    </logic:equal>
    <logic:notEqual name="ntp061Form" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
      <tr class="js_permissionEditRadio display_n">
    </logic:notEqual>
      <td class="w80">
        <div class="w100">
          <div class="verAlignMid">
            <gsmsg:write key="cmn.edit.permissions" />:
            <%--制限なし --%>
            <html:radio name="ntp061Form" property="ntp061NanPermitEdit" styleId="ntp061NanPermitEdit0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>" />
            <label for="ntp061NanPermitEdit0"><gsmsg:write key="cmn.nolimit" /></label>
            <%--担当者のみ --%>
            <html:radio name="ntp061Form" property="ntp061NanPermitEdit" styleId="ntp061NanPermitEdit1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>" />
            <label for="ntp061NanPermitEdit1"><gsmsg:write key="address.62" /></label>
            <%--ユーザグループ指定 --%>
            <html:radio name="ntp061Form" property="ntp061NanPermitEdit" styleId="ntp061NanPermitEdit2" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>" />
            <label for="ntp061NanPermitEdit2"><gsmsg:write key="ntp.ntp061.1" /></label>
          </div>
        </div>
        <div id="nanPermitEditSelect">
          <ui:usrgrpselector name="ntp061Form" property="ntp061NanPermitEditUI" styleClass="hp215" />
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block mt20">
    <logic:equal name="ntp061Form" property="ntp061PopKbn" value="0">
      <logic:equal name="ntp061Form" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.change" />" onclick="return buttonPush('okNtp061');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
          <gsmsg:write key="cmn.change" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonCopy('061_copy', 'add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
          <gsmsg:write key="cmn.register.copy2" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush('del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <logic:notEqual name="ntp061Form" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="return buttonPush('okNtp061')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <gsmsg:write key="cmn.entry" />
        </button>
      </logic:notEqual>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:notEqual name="ntp061Form" property="ntp061PopKbn" value="0">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="return buttonPush('okNtp061pop')">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <gsmsg:write key="cmn.entry" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp200');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:notEqual>
  </div>
</div>

<logic:equal name="ntp061Form" property="ntp061PopKbn" value="0">
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</logic:equal>
<br/>
<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp061_dialog.jsp">
  <jsp:param value="ntp061Form" name="thisFormName"/>
 </jsp:include>

</html:form>

</body>
</html:html>