<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<% String maxLengthSyosai = String.valueOf(1000); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="showLengthId($('#inputstr')[0], <%= maxLengthSyosai %>, 'inputlength');">
<input type="hidden" name="helpPrm" value="<bean:write name="ntp061knForm" property="ntp060ProcMode" />">
<logic:equal name="ntp061knForm" property="ntp061PopKbn" value="0">
 <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
</logic:equal>

<html:form action="/nippou/ntp061kn">
<div id="ntp061CompanyIdArea"><html:hidden property="ntp061CompanySid" /></div>
<div id="ntp061CompanyBaseIdArea"><html:hidden property="ntp061CompanyBaseSid" /></div>
<input type="hidden" name="CMD" value="">
<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp" />

<logic:notEmpty name="ntp061knForm" property="ntp060Mikomi" scope="request">
<logic:iterate id="mikomi" name="ntp061knForm" property="ntp060Mikomi" scope="request">
  <input type="hidden" name="ntp060Mikomi" value="<bean:write name="mikomi"/>">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="ntp061knForm" property="ntp060Syodan" scope="request">
<logic:iterate id="syodan" name="ntp061knForm" property="ntp060Syodan" scope="request">
  <input type="hidden" name="ntp060Syodan" value="<bean:write name="syodan"/>">
</logic:iterate>
</logic:notEmpty>

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
<html:hidden property="ntp061NanCode" />
<html:hidden property="ntp061NanName" />
<html:hidden property="ntp061NanSyosai" />
<html:hidden property="ntp061NanKinJutyu" />
<html:hidden property="ntp061NanKinMitumori" />
<html:hidden property="ntp061MitumoriYear" />
<html:hidden property="ntp061MitumoriMonth" />
<html:hidden property="ntp061MitumoriDay" />
<html:hidden property="ntp061JutyuYear" />
<html:hidden property="ntp061JutyuMonth" />
<html:hidden property="ntp061JutyuDay" />
<html:hidden property="ntp061NanMikomi" />
<html:hidden property="ntp061NgySid" />
<html:hidden property="ntp061NgpSid" />
<html:hidden property="ntp061NanSyodan" />
<html:hidden property="ntp061NcnSid" />
<html:hidden property="ntp061NanState" />
<html:hidden property="ntp061NanPermitView" />
<html:hidden property="ntp061NanPermitEdit" />
<html:hidden property="ntp200NanSid" />
<html:hidden property="ntp200ProcMode" />
<html:hidden property="ntp200InitFlg" />
<html:hidden property="ntp200parentPageId" />
<html:hidden property="ntp200RowNumber" />

<logic:notEmpty name="ntp061knForm" property="ntp061ChkShohinSidList" scope="request">
<logic:iterate id="item" name="ntp061knForm" property="ntp061ChkShohinSidList" scope="request">
  <input type="hidden" name="ntp061ChkShohinSidList" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>

<%--　BODY --%>
<logic:notEmpty name="ntp061knForm" property="sv_users" scope="request">
<logic:iterate id="ulBean" name="ntp061knForm" property="sv_users" scope="request">
  <input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>


<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.11" /><logic:notEqual name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_DSP) %>"><logic:notEqual name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>"><gsmsg:write key="cmn.entry" /></logic:notEqual><logic:equal name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>"><gsmsg:write key="cmn.edit" /></logic:equal></logic:notEqual><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <logic:equal name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_DSP) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061kn');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>

        <logic:notEqual name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_DSP) %>">
        <logic:equal name="ntp061knForm" property="ntp061PopKbn" value="0">
          <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('addOk');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061kn');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>

        <logic:notEqual name="ntp061knForm" property="ntp061PopKbn" value="0">
          <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('addOkPop');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061kn');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>
        </logic:notEqual>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">

<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="w80">
        <bean:define id="userUkoFlg" name="ntp061knForm" property="ntp061TourokuUsrUkoFlg" type="Integer"/>
        <span class="<%=UserUtil.getCSSClassNameNormal(userUkoFlg)%>"><bean:write name="ntp061knForm" property="ntp061TourokuName" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.72" />
      </th>
      <td>
        <bean:write name="ntp061knForm" property="ntp061Date" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.57" />
      </th>
      <td>
        <bean:write name="ntp061knForm" property="ntp061NanName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.73" />
      </th>
      <td>
        <bean:write name="ntp061knForm" property="ntp061knNanSyosai" filter="false" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.staff" />
      </th>
      <td>
        <logic:notEmpty name="ntp061knForm" property="ntp061knTantoList" scope="request">
          <logic:iterate id="urBean" name="ntp061knForm" property="ntp061knTantoList" scope="request" type="CmnUsrmInfModel">
            <span class="<%=UserUtil.getCSSClassNameNormal(urBean.getUsrUkoFlg())%>"><bean:write name="urBean" property="usiSei" scope="page"/>　<bean:write name="urBean" property="usiMei" scope="page"/></span><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.15" />（<gsmsg:write key="ntp.16" />）
      </th>
      <td>
        <gsmsg:write key="address.7" /><gsmsg:write key="cmn.colon" /><logic:notEmpty name="ntp061knForm" property="ntp061AcoCode"><bean:write name="ntp061knForm" property="ntp061AcoCode" /></logic:notEmpty>
        <logic:notEmpty name="ntp061knForm" property="ntp061CompanyName">
          <bean:write name="ntp061knForm" property="ntp061CompanyName" />&nbsp;&nbsp;&nbsp;<bean:write name="ntp061knForm" property="ntp061CompanyBaseName" />
        </logic:notEmpty>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.58" />
      </th>
      <td>
        <logic:notEmpty name="ntp061knForm" property="ntp061ShohinList">
          <logic:iterate id="shnMdl" name="ntp061knForm" property="ntp061ShohinList">
            <bean:write name="shnMdl" property="label" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.61" />&nbsp;／&nbsp;<gsmsg:write key="ntp.62" />
      </th>
      <td>
        <logic:notEmpty name="ntp061knForm" property="ntp060GyomuList">
        <bean:define id="ngyId" name="ntp061knForm" property="ntp061NgySid" />
        <logic:iterate id="gyoushuMdl" name="ntp061knForm" property="ntp060GyomuList">
          <logic:equal name="gyoushuMdl" property="value" value="<%= String.valueOf(ngyId) %>"><bean:write name="gyoushuMdl" property="label" /></logic:equal>
        </logic:iterate>
      </logic:notEmpty>／
      <logic:notEmpty name="ntp061knForm" property="ntp060ProcessList">
        <bean:define id="ngpId" name="ntp061knForm" property="ntp061NgpSid" />
        <logic:iterate id="processMdl" name="ntp061knForm" property="ntp060ProcessList">
          <logic:equal name="processMdl" property="value" value="<%= String.valueOf(ngpId) %>"><bean:write name="processMdl" property="label" /></logic:equal>
        </logic:iterate>
      </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.63" />
      </th>
      <td>
        <logic:equal name="ntp061knForm" property="ntp061NanMikomi" value="0">10%</logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanMikomi" value="1">30%</logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanMikomi" value="2">50%</logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanMikomi" value="3">70%</logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanMikomi" value="4">100%</logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.53" />
      </th>
      <td>
        <bean:write name="ntp061knForm" property="ntp061NanKinMitumori" /><gsmsg:write key="project.103" />&nbsp;&nbsp;&nbsp;&nbsp;
        <gsmsg:write key="ntp.55" /><gsmsg:write key="cmn.colon" />
        <logic:notEmpty name="ntp061knForm" property="ntp061YearLabel">
          <bean:define id="mYearId" name="ntp061knForm" property="ntp061MitumoriYear" />
          <logic:iterate id="mYearMdl" name="ntp061knForm" property="ntp061YearLabel">
            <logic:equal name="mYearMdl" property="value" value="<%= String.valueOf(mYearId) %>"><bean:write name="mYearMdl" property="label" /></logic:equal>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="ntp061knForm" property="ntp061MonthLabel">
          <bean:define id="mMonthId" name="ntp061knForm" property="ntp061MitumoriMonth" />
          <logic:iterate id="mMonthMdl" name="ntp061knForm" property="ntp061MonthLabel">
            <logic:equal name="mMonthMdl" property="value" value="<%= String.valueOf(mMonthId) %>"><bean:write name="mMonthMdl" property="label" /></logic:equal>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="ntp061knForm" property="ntp061DayLabel">
          <bean:define id="mDayId" name="ntp061knForm" property="ntp061MitumoriDay" />
          <logic:iterate id="mDayMdl" name="ntp061knForm" property="ntp061DayLabel">
            <logic:equal name="mDayMdl" property="value" value="<%= String.valueOf(mDayId) %>"><bean:write name="mDayMdl" property="label" /></logic:equal>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.54" />
      </th>
      <td>
        <bean:write name="ntp061knForm" property="ntp061NanKinJutyu" /><gsmsg:write key="project.103" />&nbsp;&nbsp;&nbsp;&nbsp;
        <gsmsg:write key="ntp.56" /><gsmsg:write key="cmn.colon" />
        <logic:notEmpty name="ntp061knForm" property="ntp061YearLabel">
          <bean:define id="jYearId" name="ntp061knForm" property="ntp061JutyuYear" />
          <logic:iterate id="jYearMdl" name="ntp061knForm" property="ntp061YearLabel">
            <logic:equal name="jYearMdl" property="value" value="<%= String.valueOf(jYearId) %>"><bean:write name="jYearMdl" property="label" /></logic:equal>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="ntp061knForm" property="ntp061MonthLabel">
          <bean:define id="jMonthId" name="ntp061knForm" property="ntp061JutyuMonth" />
          <logic:iterate id="jMonthMdl" name="ntp061knForm" property="ntp061MonthLabel">
            <logic:equal name="jMonthMdl" property="value" value="<%= String.valueOf(jMonthId) %>"><bean:write name="jMonthMdl" property="label" /></logic:equal>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="ntp061knForm" property="ntp061DayLabel">
          <bean:define id="jDayId" name="ntp061knForm" property="ntp061JutyuDay" />
          <logic:iterate id="jDayMdl" name="ntp061knForm" property="ntp061DayLabel">
            <logic:equal name="jDayMdl" property="value" value="<%= String.valueOf(jDayId) %>"><bean:write name="jDayMdl" property="label" /></logic:equal>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.64" />
      </th>
      <td>
        <logic:equal name="ntp061knForm" property="ntp061NanSyodan" value="0"><gsmsg:write key="ntp.68" /></logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanSyodan" value="1"><gsmsg:write key="ntp.69" /></logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanSyodan" value="2"><gsmsg:write key="ntp.7" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.65" />
      </th>
      <td>
        <%-- <gsmsg:write key="ntp.65" /> --%>
        <logic:notEmpty name="ntp061knForm" property="ntp060ContactList">
        <logic:iterate id="ncnMdl" name="ntp061knForm" property="ntp060ContactList" indexId="ncnidx">
         <bean:define  id="ncnVal" name="ncnMdl" property="value" />
        <logic:equal name="ntp061knForm" property="ntp061NcnSid" value="<%= String.valueOf(ncnVal) %>"><bean:write  name="ncnMdl" property="label" /></logic:equal>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.71" />
      </th>
      <td>
        <logic:equal name="ntp061knForm" property="ntp061NanState" value="0"><gsmsg:write key="ntp.74" /></logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanState" value="1"><gsmsg:write key="ntp.75" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.setting.permissions"  />
      </th>
      <td>
        <logic:equal name="ntp061knForm" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
          <%--制限なし --%>
          <span id="permissionViewLabel"><gsmsg:write key="address.61" /><gsmsg:write key="cmn.colon" /></span>
          <gsmsg:write key="cmn.nolimit" /><br>
          <gsmsg:write key="cmn.edit.permissions" /><gsmsg:write key="cmn.colon" />
          <logic:equal name="ntp061knForm" property="ntp061NanPermitEdit" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
            <gsmsg:write key="cmn.nolimit" /><br>
          </logic:equal>
          <logic:equal name="ntp061knForm" property="ntp061NanPermitEdit" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>">
          <%--担当者のみ --%>
            <gsmsg:write key="address.62" />
          </logic:equal>
          <%--ユーザグループ指定 --%>
          <logic:equal name="ntp061knForm" property="ntp061NanPermitEdit" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>">
            <gsmsg:write key="ntp.ntp061.1" /></br>
            <logic:iterate id="user" name="ntp061knForm" property="ntp061NanPermitUsrGrp.selectedList(edit)" type="UsrLabelValueBean">
              <html:hidden property="ntp061NanPermitUsrGrp.selected(edit)" value="<%=user.getValue() %>" />
              <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
            </logic:iterate>
          </logic:equal>
        </logic:equal>
        <logic:equal name="ntp061knForm" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>">
        <%--担当者のみ --%>
          <gsmsg:write key="address.62" /></br>
        </logic:equal>
        <%--ユーザグループ指定 --%>
        <logic:equal name="ntp061knForm" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>">
          <gsmsg:write key="ntp.ntp061.1" /></br><br>
          <bean:write name="ntp061knForm" property="ntp061NanPermitUsrGrp.selectedListName(view)"/><gsmsg:write key="cmn.colon" /><br />
          <logic:iterate id="user" name="ntp061knForm" property="ntp061NanPermitUsrGrp.selectedList(view)" type="UsrLabelValueBean">
            <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
            <html:hidden property="ntp061NanPermitUsrGrp.selected(view)" value="<%=user.getValue() %>" />
          </logic:iterate>
          </br>
          <bean:write name="ntp061knForm" property="ntp061NanPermitUsrGrp.selectedListName(edit)"/><gsmsg:write key="cmn.colon" /><br />
          <logic:iterate id="user" name="ntp061knForm" property="ntp061NanPermitUsrGrp.selectedList(edit)" type="UsrLabelValueBean">
            <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
            <html:hidden property="ntp061NanPermitUsrGrp.selected(edit)" value="<%=user.getValue() %>" />
          </logic:iterate>
        </logic:equal>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
  <logic:equal name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_DSP) %>">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061kn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </logic:equal>

  <logic:notEqual name="ntp061knForm" property="ntp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_DSP) %>">
  <logic:equal name="ntp061knForm" property="ntp061PopKbn" value="0">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('addOk');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061kn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
   </logic:equal>

   <logic:notEqual name="ntp061knForm" property="ntp061PopKbn" value="0">
     <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('addOkPop');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
       <gsmsg:write key="cmn.final" />
     </button>
     <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backNtp061kn');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
       <gsmsg:write key="cmn.back" />
     </button>
   </logic:notEqual>
   </logic:notEqual>

  </div>
</div>

<logic:equal name="ntp061knForm" property="ntp061PopKbn" value="0">
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</logic:equal>

<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp061_dialog.jsp">
  <jsp:param value="ntp061knForm" name="thisFormName"/>
 </jsp:include>
</html:form>

</body>
</html:html>