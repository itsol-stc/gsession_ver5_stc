<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.prj.GSConstProject" %>

<!DOCTYPE html>

<%
      try{
%>

<%-- CMD定数 --%>
<%
    String okClick          = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_OK_CLICK;
    String deleteClick      = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_DEL_CLICK;
    String backClick        = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_BACK_CLICK;
    String memberAdd        = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_MEMBER_ADD_CLICK;
    String memberRemove     = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_MEMBER_REMOVE_CLICK;
    String attachRemove     = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_ATTACH_REMOVE_CLICK;
    String backRedraw       = jp.groupsession.v2.prj.prj050.Prj050Action.CMD_BACK_REDRAW;
    String entryAdd         = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_ADD;
    String entryEdit        = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_EDIT;
%>
<gsmsg:define id="textKbnAdmin" msgkey="project.src.55" />
<gsmsg:define id="textKbnParticipation" msgkey="project.src.56" />
<%

    String low              = String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_LOW);
    String middle           = String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE);
    String high             = String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH);

    String id_low           = "juyou" + low;
    String id_middle        = "juyou" + middle;
    String id_high          = "juyou" + high;

    String kbn_all          = String.valueOf(jp.groupsession.v2.prj.GSConstProject.PRJ_KBN_ADMIN);
    String kbn_sanka        = String.valueOf(jp.groupsession.v2.prj.GSConstProject.PRJ_KBN_PARTICIPATION);
    String kbn_all_str      = textKbnAdmin;
    String kbn_sanka_str    = textKbnParticipation;

    String pluginId         = jp.groupsession.v2.prj.GSConstProject.PLUGIN_ID_PROJECT;

    String maxLengthContent = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MAX_LENGTH_CONTENT);
    String maxLengthReason  = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MAX_LENGTH_STATUS_REASON);

%>

<bean:define id="prjStsMdl" name="prj050Form" property="prjStatusMdl" />

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../project/js/project.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../project/js/prj050.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<logic:equal name="prj050Form" property="prj050elementKbn" value="1">
  <body onunload="windowClose();" onload="showLengthId($('#inputstr2')[0], <%= maxLengthContent %>, 'inputlength2');showLengthId($('#inputstr3')[0], <%= maxLengthReason %>, 'inputlength3');">
</logic:equal>
<logic:equal name="prj050Form" property="prj050elementKbn" value="0">
  <body onunload="windowClose();" onload="showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');">
</logic:equal>

<html:form action="/project/prj050">
  <input type="hidden" name="helpPrm" value="<bean:write name="prj050Form" property="prj050cmdMode" />">

  <input type="hidden" name="CMD" value="okClick">
  <html:hidden property="prj010cmdMode" />
  <html:hidden property="prj010page1" />
  <html:hidden property="prj010page2" />
  <html:hidden property="prj010sort" />
  <html:hidden property="prj010order" />
  <html:hidden property="prj010Init" />
  <html:hidden property="selectingProject" />
  <html:hidden property="selectingTodoDay" />
  <html:hidden property="selectingTodoPrj" />
  <html:hidden property="selectingTodoSts" />

  <html:hidden property="prj050kaisiYoteiYear" />
  <html:hidden property="prj050kaisiYoteiMonth" />
  <html:hidden property="prj050kaisiYoteiDay" />
  <html:hidden property="prj050kigenYear" />
  <html:hidden property="prj050kigenMonth" />
  <html:hidden property="prj050kigenDay" />
  <html:hidden property="prj050kaisiJissekiYear" />
  <html:hidden property="prj050kaisiJissekiMonth" />
  <html:hidden property="prj050kaisiJissekiDay" />
  <html:hidden property="prj050syuryoJissekiYear" />
  <html:hidden property="prj050syuryoJissekiMonth" />
  <html:hidden property="prj050syuryoJissekiDay" />

  <html:hidden property="prj040searchFlg" />
  <html:hidden property="prj040scPrjId" />
  <html:hidden property="prj040scStatusFrom" />
  <html:hidden property="prj040scStatusTo" />
  <html:hidden property="prj040scPrjName" />
  <html:hidden property="prj040scStartYearFrom" />
  <html:hidden property="prj040scStartMonthFrom" />
  <html:hidden property="prj040scStartDayFrom" />
  <html:hidden property="prj040scStartYearTo" />
  <html:hidden property="prj040scStartMonthTo" />
  <html:hidden property="prj040scStartDayTo" />
  <html:hidden property="prj040scEndYearFrom" />
  <html:hidden property="prj040scEndMonthFrom" />
  <html:hidden property="prj040scEndDayFrom" />
  <html:hidden property="prj040scEndYearTo" />
  <html:hidden property="prj040scEndMonthTo" />
  <html:hidden property="prj040scEndDayTo" />
  <html:hidden property="prj040svScPrjId" />
  <html:hidden property="prj040svScStatusFrom" />
  <html:hidden property="prj040svScStatusTo" />
  <html:hidden property="prj040svScPrjName" />
  <html:hidden property="prj040svScStartYearFrom" />
  <html:hidden property="prj040svScStartMonthFrom" />
  <html:hidden property="prj040svScStartDayFrom" />
  <html:hidden property="prj040svScStartYearTo" />
  <html:hidden property="prj040svScStartMonthTo" />
  <html:hidden property="prj040svScStartDayTo" />
  <html:hidden property="prj040svScEndYearFrom" />
  <html:hidden property="prj040svScEndMonthFrom" />
  <html:hidden property="prj040svScEndDayFrom" />
  <html:hidden property="prj040svScEndYearTo" />
  <html:hidden property="prj040svScEndMonthTo" />
  <html:hidden property="prj040svScEndDayTo" />
  <html:hidden property="prj040page1" />
  <html:hidden property="prj040page2" />
  <html:hidden property="prj040sort" />
  <html:hidden property="prj040order" />
  <html:hidden property="prj040scYosanFr" />
  <html:hidden property="prj040scYosanTo" />
  <html:hidden property="prj040svScYosanFr" />
  <html:hidden property="prj040svScYosanTo" />

  <logic:notEmpty name="prj050Form" property="prj040scMemberSid" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj040scMemberSid" scope="request">
      <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj040svScMemberSid" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj040svScMemberSid" scope="request">
      <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <html:hidden property="prj030scrId" />
  <html:hidden property="prj030prjSid" />
  <html:hidden property="prj030sort" />
  <html:hidden property="prj030order" />
  <html:hidden property="prj030page1" />
  <html:hidden property="prj030page2" />
  <html:hidden property="prj030Init" />
  <html:hidden property="selectingDate" />
  <html:hidden property="selectingStatus" />
  <html:hidden property="selectingCategory" />
  <html:hidden property="selectingMember" />

  <logic:notEmpty name="prj050Form" property="prj030sendMember" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj030sendMember" scope="request">
      <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <html:hidden property="prj050elementKbn" />

  <html:hidden property="prj070scrId" />
  <html:hidden property="prj070scPrjSid" />
  <html:hidden property="prj070scCategorySid" />
  <html:hidden property="prj070scStatusFrom" />
  <html:hidden property="prj070scStatusTo" />
  <html:hidden property="prj070scKaisiYoteiYear" />
  <html:hidden property="prj070scKaisiYoteiMonth" />
  <html:hidden property="prj070scKaisiYoteiDay" />
  <html:hidden property="prj070scKigenYear" />
  <html:hidden property="prj070scKigenMonth" />
  <html:hidden property="prj070scKigenDay" />
  <html:hidden property="prj070scKaisiJissekiYear" />
  <html:hidden property="prj070scKaisiJissekiMonth" />
  <html:hidden property="prj070scKaisiJissekiDay" />
  <html:hidden property="prj070scSyuryoJissekiYear" />
  <html:hidden property="prj070scSyuryoJissekiMonth" />
  <html:hidden property="prj070scSyuryoJissekiDay" />
  <html:hidden property="prj070scTitle" />
  <html:hidden property="prj070KeyWordkbn" />
  <html:hidden property="prj070svScPrjSid" />
  <html:hidden property="prj070svScCategorySid" />
  <html:hidden property="prj070svScStatusFrom" />
  <html:hidden property="prj070svScStatusTo" />
  <html:hidden property="prj070svScKaisiYoteiYear" />
  <html:hidden property="prj070svScKaisiYoteiMonth" />
  <html:hidden property="prj070svScKaisiYoteiDay" />
  <html:hidden property="prj070svScKigenYear" />
  <html:hidden property="prj070svScKigenMonth" />
  <html:hidden property="prj070svScKigenDay" />
  <html:hidden property="prj070svScKaisiJissekiYear" />
  <html:hidden property="prj070svScKaisiJissekiMonth" />
  <html:hidden property="prj070svScKaisiJissekiDay" />
  <html:hidden property="prj070svScSyuryoJissekiYear" />
  <html:hidden property="prj070svScSyuryoJissekiMonth" />
  <html:hidden property="prj070svScSyuryoJissekiDay" />
  <html:hidden property="prj070svScTitle" />
  <html:hidden property="prj070SvKeyWordkbn" />
  <html:hidden property="prj070page1" />
  <html:hidden property="prj070page2" />
  <html:hidden property="prj070sort" />
  <html:hidden property="prj070order" />
  <html:hidden property="prj070searchFlg" />
  <html:hidden property="prj070InitFlg" />

  <logic:notEmpty name="prj050Form" property="prj070scTantou" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070scTantou" scope="request">
      <input type="hidden" name="prj070scTantou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070scTourokusya" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070scTourokusya" scope="request">
      <input type="hidden" name="prj070scTourokusya" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070svScTantou" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070svScTantou" scope="request">
      <input type="hidden" name="prj070svScTantou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070svScTourokusya" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070svScTourokusya" scope="request">
      <input type="hidden" name="prj070svScTourokusya" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070svScJuuyou" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070svScJuuyou" scope="request">
      <input type="hidden" name="prj070svScJuuyou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070scJuuyou" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070scJuuyou" scope="request">
      <input type="hidden" name="prj070scJuuyou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070SearchTarget" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070SearchTarget" scope="request">
      <input type="hidden" name="prj070SearchTarget" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj050Form" property="prj070SvSearchTarget" scope="request">
    <logic:iterate id="item" name="prj050Form" property="prj070SvSearchTarget" scope="request">
      <input type="hidden" name="prj070SvSearchTarget" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <html:hidden property="prj060scrId" />
  <html:hidden property="prj060prjSid" />
  <html:hidden property="prj060todoSid" />
  <html:hidden property="prj060comment" />
  <html:hidden property="prj060status" />
  <html:hidden property="prj060riyu" />

  <html:hidden property="prj060SelectYearFr" />
  <html:hidden property="prj060SelectMonthFr" />
  <html:hidden property="prj060SelectDayFr" />
  <html:hidden property="prj060SelectYearTo" />
  <html:hidden property="prj060SelectMonthTo" />
  <html:hidden property="prj060SelectDayTo" />
  <html:hidden property="prj060ResultKosu" />

  <html:hidden property="prj060scrId" />
  <html:hidden property="prj060prjSid" />
  <html:hidden property="prj060todoSid" />
  <html:hidden property="prj060schUrl" />

  <html:hidden property="prj050scrId" />
  <html:hidden property="prj050cmdMode" />
  <html:hidden property="prj050todoSid" />
  <html:hidden property="prj050prjMyKbn" />
  <html:hidden property="prj050dspKbn" />

  <!-- マイプロジェクト -->
  <logic:equal name="prj050Form" property="prj050prjMyKbn" value="1">
    <logic:notEmpty name="prj050Form" property="prj050hdnTanto" scope="request">
      <logic:iterate id="item" name="prj050Form" property="prj050hdnTanto" scope="request">
        <input type="hidden" name="prj050hdnTanto" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>
  </logic:equal>

  <!-- 通常プロジェクトかつ簡易入力 -->
  <logic:notEqual name="prj050Form" property="prj050prjMyKbn" value="1">
    <logic:notEqual name="prj050Form" property="prj050elementKbn" value="1">
      <logic:notEmpty name="prj050Form" property="prj050hdnTanto" scope="request">
        <logic:iterate id="item" name="prj050Form" property="prj050hdnTanto" scope="request">
          <input type="hidden" name="prj050hdnTanto" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>
    </logic:notEqual>
  </logic:notEqual>

  <html:hidden property="selectDir" />

  <logic:equal name="prj050Form" property="prj050elementKbn" value="0">
    <html:hidden property="prj050PrjListKbn" />
    <html:hidden property="prj050cate" />
    <html:hidden property="prj050kaisiYoteiYear" />
    <html:hidden property="prj050kaisiYoteiMonth" />
    <html:hidden property="prj050kaisiYoteiDay" />
    <html:hidden property="prj050yoteiKosu" />
    <html:hidden property="prj050kaisiJissekiYear" />
    <html:hidden property="prj050kaisiJissekiMonth" />
    <html:hidden property="prj050kaisiJissekiDay" />
    <html:hidden property="prj050syuryoJissekiYear" />
    <html:hidden property="prj050syuryoJissekiMonth" />
    <html:hidden property="prj050syuryoJissekiDay" />
    <html:hidden property="prj050strPlanDate" />
    <html:hidden property="prj050strResultDate" />
    <html:hidden property="prj050endResultDate" />
    <html:hidden property="prj050jissekiKosu" />
    <html:hidden property="prj050status" />
    <html:hidden property="prj050statusCmt" />
    <html:hidden property="prj050keikoku" />
    <html:hidden property="prj050MailSend" />
  </logic:equal>

  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

  <div class="pageTitle w90 mrl_auto">
    <ul>
      <li>
        <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
        <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
      </li>
      <li>
        <gsmsg:write key="cmn.project" />
      </li>
      <li class="pageTitle_subFont">
        <logic:equal name="prj050Form" property="prj050cmdMode" value="<%= entryAdd %>">
          <gsmsg:write key="project.32" />
        </logic:equal>
        <logic:equal name="prj050Form" property="prj050cmdMode" value="<%= entryEdit %>">
          <gsmsg:write key="project.56" />
        </logic:equal>
      </li>
      <li>
        <div>
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%= okClick %>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <logic:equal name="prj050Form" property="prj050cmdMode" value="<%= entryEdit %>">
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('<%= deleteClick %>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </li>
    </ul>
  </div>

  <div class="w90 wrapper mrl_auto">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <div class="mt15">
    <logic:equal name="prj050Form" property="prj050elementKbn" value="0">
      <table class="wp250 mb10 sideTop-font">
        <tr>
          <td class="w50 cursor_p bgC_lightGray bor1 txt_c searchMenu_top" onClick="setDateParam();buttonPush('toDetail');"><gsmsg:write key="project.prj050.1" /></td>
          <td class="w50 bor1 bgC_body txt_c searchMenu_title-select"><gsmsg:write key="project.prj050.2" /></td>
        </tr>
      </table>
    </logic:equal>
    <logic:equal name="prj050Form" property="prj050elementKbn" value="1">

      <table class="wp250 mb10 sideTop-font">
        <tr>
          <td class="w50 bor1 bgC_body txt_c searchMenu_title-select"><gsmsg:write key="project.prj050.1" /></td>
          <td class="w50 cursor_p bgC_lightGray bor1 txt_c searchMenu_top" onClick="setDateParam();buttonPush('toEasy');"><gsmsg:write key="project.prj050.2" /></td>
        </tr>
      </table>
    </logic:equal>
    </div>


    <!-- 簡易入力 -->
    <logic:equal name="prj050Form" property="prj050elementKbn" value="0">
      <html:hidden property="prj050statusCmtEasy" />
      <table class="table-left m0">
        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.project" />
            </span>
          </th>
          <td class="txt_l no_w">
            <html:select property="prj050prjSid" styleClass="wp250" onchange="setDateParam();buttonPush('changeProject');">
              <html:optionsCollection name="prj050Form" property="projectLabel" value="value" label="label" />
            </html:select>
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.title" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w75">
            <html:text property="prj050titleEasy" styleClass="wp500" maxlength="100" />
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="project.111" />
            </span>
          </th>
          <td class="txt_l w75">
            <span class="verAlignMid">
              <html:text name="prj050Form" property="prj050endPlanDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDateep"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selDateep')[0], 1, 2)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDateep')[0], 2, 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return moveDay($('#selDateep')[0], 2, 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDateep')[0], 3, 2)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
              <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="return clearDate($('#selDateep'))">
                <gsmsg:write key="cmn.clear" />
              </button>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="project.prj050.4" />
            </span>
          </th>

          <td class="txt_l w75">
          <span class="verAlignMid">
            <span class="verAlignMid">
              <html:radio property="prj050juyouEasy" styleId="<%= id_low %>" value="<%=low%>" />
              <label for="<%=id_low%>" onclick="return clickLabel(this);">
                <gsmsg:write key="project.58" />
                <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                <span class="original-display">
                <i class="icon-star importance-blue"></i>
                <i class="icon-star_line"></i>
                <i class="icon-star_line"></i>
              </label>
            </span>
            <span class="verAlignMid">
            <html:radio property="prj050juyouEasy" styleClass="ml10" styleId="<%=id_middle%>" value="<%=middle%>" />
              <label for="<%=id_middle%>" onclick="return clickLabel(this);">
                <gsmsg:write key="project.59" />
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.59" />">
                <span class="original-display">
                <i class="icon-star importance-yellow"></i>
                <i class="icon-star importance-yellow"></i>
                <i class="icon-star_line"></i>
                </span>
              </label>
            </span>

            <span class="verAlignMid">
            <html:radio property="prj050juyouEasy" styleClass="ml10" styleId="<%=id_high%>" value="<%=high%>" />
              <label for="<%=id_high%>" onclick="return clickLabel(this);">
                <gsmsg:write key="project.60" />
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                <span class="original-display">
                <i class="icon-star importance-red"></i>
                <i class="icon-star importance-red"></i>
                <i class="icon-star importance-red"></i>
                </span>
              </label>
              </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.content2" />
            </span>
          </th>
          <td class="txt_l">
            <textarea name="prj050naiyoEasy" rows="5" class="wp500" onkeyup="showLengthStr(value, <%= maxLengthContent %>, 'inputlength');" id="inputstr"><bean:write name="prj050Form" property="prj050naiyoEasy" /></textarea>
            <br>
            <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>
            <span class="formCounter_max"> / <%= maxLengthContent %>
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
      </table>
    </logic:equal>

    <!-- 詳細入力 -->
    <logic:equal name="prj050Form" property="prj050elementKbn" value="1">
      <table class="table-left m0">
        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.project" />
            </span>
          </th>
          <td class="txt_l no_w w75">
            <span class="verAlignMid">
            <html:select property="prj050prjSid" styleClass="wp250" onchange="setDateParam();buttonPush('changeProject');">
              <html:optionsCollection name="prj050Form" property="projectLabel" value="value" label="label" />
            </html:select>
            <logic:equal name="prj050Form" property="admin" value="true">
              <span class="ml5 verAlignMid">
                <html:radio property="prj050PrjListKbn" styleId="kbn_all" value="<%=kbn_all%>" onclick="setDateParam();return buttonPush('changeProject');" />
                <span>
                  <label for="kbn_all"><%=kbn_all_str%></label>
                </span>
                <html:radio property="prj050PrjListKbn" styleClass="ml10" styleId="kbn_sanka" value="<%=kbn_sanka%>" onclick="setDateParam();return buttonPush('changeProject');" />
                <span>
                  <label for="kbn_sanka"><%=kbn_sanka_str%></label>
                </span>
              </span>
            </logic:equal>
            </span>
          </td>
        </tr>

        <logic:equal name="prj050Form" property="prj050cmdMode" value="<%= entryEdit %>">
          <tr>
            <th class="w25 no_w" colspan="2">
              <span>
                <gsmsg:write key="project.prj050.5" />
              </span>
            </th>
            <td class="txt_l w75">
              <logic:notEmpty name="prj050Form" property="projectItem">
                <bean:define id="prjMdl" name="prj050Form" property="projectItem" />
                <span>
                  <bean:write name="prjMdl" property="strKanriNo" />
                </span>
              </logic:notEmpty>
            </td>
          </tr>

          <tr>
            <th class="no_w w25" colspan="2">
              <span>
                <gsmsg:write key="cmn.registant" />
              </span>
            </th>
            <td class="txt_l w75">
              <logic:notEmpty name="prj050Form" property="projectItem">
                <bean:define id="prjMdl" name="prj050Form" property="projectItem" />
                <span>
                  <bean:write name="prjMdl" property="addUserSei" />&nbsp;<bean:write name="prjMdl" property="addUserMei" />
                </span>
              </logic:notEmpty>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.title" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w75">
            <html:text property="prj050title" styleClass="wp600" maxlength="100" />
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.label" />
            </span>
          </th>
          <td class="txt_l w75">

            <logic:notEmpty name="prj050Form" property="prj050CategoryList">
              <logic:iterate id="category" name="prj050Form" property="prj050CategoryList" indexId="idx">

                <% if (idx.intValue() > 0 && idx.intValue() % 5 == 0) { %><br>
                <% } %>
                <bean:define id="categorySid" name="category" property="ptcCategorySid" />
                <bean:define id="categoryName" name="category" property="ptcName" />
                <bean:define id="idname" value="<%= String.valueOf(categorySid) %>" />
                <span class="verAlignMid">
                <html:radio property="prj050cate" styleId="<%= idname %>" value="<%= String.valueOf(categorySid) %>" />
                  <label class="mr10" for="<%= idname %>">
                    <bean:write name="categoryName" />
                  </label>
                </span>
              </logic:iterate>
            </logic:notEmpty>

          </td>
        </tr>

        <tr>
          <th class="no_w w10" rowspan="3">
            <span>
              <gsmsg:write key="project.prj010.8" />
            </span>
          </th>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="cmn.start" />
            </span>
          </th>
          <td class="txt_l w75">
            <span class="verAlignMid">
              <html:text name="prj050Form" property="prj050strPlanDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatesp"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selDatesp')[0], 1, 0)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDatesp')[0], 2, 0)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return moveDay($('#selDatesp')[0], 2, 0)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDatesp')[0], 3, 0)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
              <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="return clearDate($('#selDatesp'))">
                <gsmsg:write key="cmn.clear" />
              </button>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="cmn.end" />
            </span>
          </th>
          <td class="txt_l w75">
            <span class="verAlignMid">
              <html:text name="prj050Form" property="prj050endPlanDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateep"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selDateep')[0], 1, 0)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDateep')[0], 2, 0)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return moveDay($('#selDateep')[0], 2, 0)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDateep')[0], 3, 0)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
              <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="return clearDate($('#selDateep'))">
                <gsmsg:write key="cmn.clear" />
              </button>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="project.33" />
            </span>
          </th>
          <td class="txt_l">
            <html:text property="prj050yoteiKosu" styleClass="wp50" maxlength="5" />
          </td>
        </tr>

        <tr>
          <th class="no_w w10" rowspan="3">
            <span>
              <gsmsg:write key="cmn.performance" />
            </span>
          </th>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="cmn.start" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:text name="prj050Form" property="prj050strResultDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatesr"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>

              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="moveDay($('#selDatesr')[0], 1, 1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="moveDay($('#selDatesr')[0], 2, 1)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="moveDay($('#selDatesr')[0], 2, 1)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="moveDay($('#selDatesr')[0], 3, 1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
              <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="clearDate($('#selDatesr'))">
                <gsmsg:write key="cmn.clear" />
              </button>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="cmn.end" />
            </span>
          </th>
          <td class="txt_l w75">
            <span class="verAlignMid">
              <html:text name="prj050Form" property="prj050endResultDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateer"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="moveDay($('#selDateer')[0], 1, 1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="moveDay($('#selDateer')[0], 2, 1)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="moveDay($('#selDateer')[0], 2, 1)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="moveDay($('#selDateer')[0], 3, 1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
              <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="clearDate($('#selDateer'))">
                <gsmsg:write key="cmn.clear" />
              </button>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="project.33" />
            </span>
          </th>
          <td class="txt_l w75">
            <html:text property="prj050jissekiKosu" styleClass="wp60" maxlength="5" />
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.content2" />
            </span>
          </th>
          <td class="txt_l w75">
            <textarea name="prj050naiyo" class="wp500" rows="5" onkeyup="showLengthStr(value, <%=maxLengthContent%>, 'inputlength2');" id="inputstr2"><bean:write name="prj050Form" property="prj050naiyo" /></textarea>
            <br>
            <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>
            <span class="formCounter_max"> / <%=maxLengthContent%>
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>

        <logic:equal name="prj050Form" property="prj050prjMyKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF) %>">
          <tr>
            <th class="no_w w25" colspan="2">
              <span>
                <gsmsg:write key="project.113" />
              </span>
            </th>
            <td class="w75">
              <ui:multiselector name="prj050Form" property="prj050hdnTantoUI" styleClass="hp200" />
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="project.prj050.4" />
            </span>
          </th>
          <td class="txt_l w75">
          <span class="verAlignMid">
            <span class="verAlignMid">
              <html:radio property="prj050juyou" styleId="<%=id_low%>" value="<%=low%>" />
                <label class="mr10" for="<%=id_low%>" onclick="return clickLabel(this);">
                  <gsmsg:write key="project.58" />
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <i class="original-display icon-star importance-blue"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display icon-star_line"></i>
                </label>
            </span>

            <span class="verAlignMid">
              <html:radio property="prj050juyou" styleId="<%=id_middle%>" value="<%=middle%>" />
                <label class="mr10" for="<%=id_middle%>" onclick="return clickLabel(this);">
                  <gsmsg:write key="project.59" />
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.59" />">
                  <i class="original-display icon-star importance-yellow"></i>
                  <i class="original-display icon-star importance-yellow"></i>
                  <i class="original-display icon-star_line"></i>
                </label>
            </span>
            <span class="verAlignMid">
              <html:radio property="prj050juyou" styleId="<%=id_high%>" value="<%=high%>" />
                <label for="<%=id_high%>" onclick="return clickLabel(this);">
                  <gsmsg:write key="project.60" />
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                </label>
              </span>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="project.35" />
            </span>
          </th>
          <td class="txt_l w75">
            <html:select property="prj050keikoku">
              <html:optionsCollection name="prj050Form" property="keikokuLabel" value="value" label="label" />
            </html:select>
          </td>
        </tr>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="cmn.status" />
            </span>
          </th>
          <td class="txt_l w75">

            <logic:notEmpty name="prjStsMdl" property="todoStatusList">
              <logic:iterate id="prjStatus" name="prjStsMdl" property="todoStatusList">
                <bean:define id="idbase" value="prj050status" />
                <bean:define id="ptsSid" name="prjStatus" property="ptsSid" />
                <bean:define id="idname" value="<%= String.valueOf(idbase) + String.valueOf(ptsSid) %>" />
                <span class="verAlignMid">
                <html:radio property="prj050status" styleId="<%= idname %>" value="<%= String.valueOf(ptsSid) %>" />
                  <label class="mr10" for="<%= idname %>">
                    <bean:write name="prjStatus" property="ptsRate" />%(<bean:write name="prjStatus" property="ptsName" />)
                  </label>
                </span>

              </logic:iterate>
            </logic:notEmpty>

            <br> <br>
            <span>
              <gsmsg:write key="project.36" />：
            </span>
            <br>
            <textarea name="prj050statusCmt" rows="3" class="wp400" onkeyup="showLengthStr(value, <%= maxLengthReason %>, 'inputlength3');" id="inputstr3"><bean:write name="prj050Form" property="prj050statusCmt" /></textarea>
            <br>
            <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength3" class="formCounter">0</span>
            <span class="formCounter_max"> / <%= maxLengthReason %>
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
        <gsmsg:define id="textNo" msgkey="cmn.no3" />
        <gsmsg:define id="textAllMem" msgkey="project130" />
        <gsmsg:define id="textCmnStaff" msgkey="cmn.staff" />
        <gsmsg:define id="textProjectAdm" msgkey="project.src.32" />
        <gsmsg:define id="textProjectLeaderAndTanto" msgkey="project.src.64" />

        <logic:equal name="prj050Form" property="useSmail" value="true">
          <logic:equal name="prj050Form" property="prj050prjMyKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF) %>">
            <tr>
              <th class="no_w w25" colspan="2">
                <span>
                  <gsmsg:write key="shortmail.notification" />
                </span>
              </th>
              <td class="txt_l w75">
                <span class="verAlignMid">
                  <logic:equal name="prj050Form" property="prj050smailKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_FREE) %>">
                    <html:radio property="prj050MailSend" styleId="smail1" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.NOT_SEND) %>" />
                    <label class="mr10" for="smail1"><%= textNo %></label>
                    <html:radio property="prj050MailSend" styleId="smail3" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_TANTO) %>" />
                    <label class="mr10" for="smail3"><%= textCmnStaff %></label>
                    </logic:equal>
                    <html:radio property="prj050MailSend" styleId="smail4" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER) %>" />
                    <label class="mr10" for="smail4"><%= textProjectAdm %></label>
                    <html:radio property="prj050MailSend" styleId="smail5" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER_AND_TANTO) %>" />
                    <label class="mr10" for="smail5"><%= textProjectLeaderAndTanto %></label>
                    <html:radio property="prj050MailSend" styleId="smail2" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_ALL_MEMBER) %>" />
                    <label for="smail2"><%= textAllMem %></label>
                  </span>
                </td>
              </tr>
            </logic:equal>
          </logic:equal>

        <tr>
          <th class="no_w w25" colspan="2">
            <span>
              <gsmsg:write key="project.110" />
            </span>
          </th>

          <td class="w75">
            <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
            pluginId="<%= GSConstProject.PLUGIN_ID_PROJECT %>"
            tempDirId="prj050" />
          </td>
        </tr>
      </table>
    </logic:equal>

    <div class="footerBtn_block mt5">
      <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=okClick%>');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:equal name="prj050Form" property="prj050cmdMode" value="<%=entryEdit%>">
        <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('<%=deleteClick%>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=backClick%>');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</html:form>

</body>
</html:html>

<%
  } catch (Exception e) {
    e.printStackTrace();
  }
%>
