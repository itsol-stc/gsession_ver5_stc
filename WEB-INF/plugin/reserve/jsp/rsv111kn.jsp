<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
  <title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.rsv111kn.1" /> ]</title>
</logic:equal>
<logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
  <title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.rsv111kn.2" /> ]</title>
</logic:equal>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv111.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../reserve/css/reserve.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body onload="showOrHide();">
<html:form action="/reserve/rsv111kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsvPrintUseKbn" />
<html:hidden property="rsv110SisetuKbn" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv111knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv111knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="rsv110ProcMode" />
<html:hidden property="rsv110InitFlg" />
<html:hidden property="rsv110RsySid" />
<html:hidden property="rsv110RsdSid" />
<html:hidden property="rsv110SinkiDefaultDate" />
<html:hidden property="rsv110ScdRsSid" />
<html:hidden property="rsv110Mokuteki" />
<html:hidden property="rsv110SelectedYearFr" />
<html:hidden property="rsv110SelectedMonthFr" />
<html:hidden property="rsv110SelectedDayFr" />
<html:hidden property="rsv110SelectedHourFr" />
<html:hidden property="rsv110SelectedMinuteFr" />
<html:hidden property="rsv110SelectedYearTo" />
<html:hidden property="rsv110SelectedMonthTo" />
<html:hidden property="rsv110SelectedDayTo" />
<html:hidden property="rsv110SelectedHourTo" />
<html:hidden property="rsv110SelectedMinuteTo" />
<html:hidden property="rsv110Naiyo" />
<html:hidden property="rsv110RsyEdit" />
<html:hidden property="rsv110EditAuth" />
<html:hidden property="rsv110Public" />
<html:hidden property="rsv110rejectDel" />
<html:hidden property="rsv110ApprBtnFlg" />
<html:hidden property="rsv111InitFlg" />
<html:hidden property="rsv111RsrRsid" />
<html:hidden property="rsv111RsrMok" />
<html:hidden property="rsv111RsrKbn" />
<html:hidden property="rsv111RsrWeek" />
<html:hidden property="rsv111RsrDay" />
<html:hidden property="rsv111RsrDayOfYearly" />
<html:hidden property="rsv111RsrMonthOfYearly" />
<html:hidden property="rsv111RsrDweek1" />
<html:hidden property="rsv111RsrDweek2" />
<html:hidden property="rsv111RsrDweek3" />
<html:hidden property="rsv111RsrDweek4" />
<html:hidden property="rsv111RsrDweek5" />
<html:hidden property="rsv111RsrDweek6" />
<html:hidden property="rsv111RsrDweek7" />
<html:hidden property="rsv111RsrTranKbn" />
<html:hidden property="rsv111RsrDateYearFr" />
<html:hidden property="rsv111RsrDateMonthFr" />
<html:hidden property="rsv111RsrDateDayFr" />
<html:hidden property="rsv111RsrDateYearTo" />
<html:hidden property="rsv111RsrDateMonthTo" />
<html:hidden property="rsv111RsrDateDayTo" />
<html:hidden property="rsv111RsrTimeHourFr" />
<html:hidden property="rsv111RsrTimeMinuteFr" />
<html:hidden property="rsv111RsrTimeHourTo" />
<html:hidden property="rsv111RsrTimeMinuteTo" />
<html:hidden property="rsv111RsrBiko" />
<html:hidden property="rsv111RsrEdit" />
<html:hidden property="rsv111RsrPublic" />
<html:hidden property="rsv111ScdReflection" />
<html:hidden property="rsv111DeleteFlg" />
<html:hidden property="rsv111HeaderDspFlg" />
<html:hidden property="rsv111ExistSchDateFlg" />

<html:hidden property="rsv110Busyo" />
<html:hidden property="rsv110UseName" />
<html:hidden property="rsv110UseNum" />
<html:hidden property="rsv110UseKbn" />
<html:hidden property="rsv110Contact" />
<html:hidden property="rsv110Guide" />
<html:hidden property="rsv110ParkNum" />
<html:hidden property="rsv110PrintKbn"/>
<html:hidden property="rsv110Dest" />
<html:hidden property="rsv111Busyo" />
<html:hidden property="rsv111UseName" />
<html:hidden property="rsv111UseNum" />
<html:hidden property="rsv111UseKbn" />
<html:hidden property="rsv111Contact" />
<html:hidden property="rsv111Guide" />
<html:hidden property="rsv111ParkNum" />
<html:hidden property="rsv111PrintKbn"/>
<html:hidden property="rsv111Dest" />

<logic:notEmpty name="rsv111knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv111knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsv110GroupSid" />
<logic:notEmpty name="rsv111knForm" property="sv_users" scope="request">
  <logic:iterate id="ulBean" name="rsv111knForm" property="sv_users" scope="request">
    <input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsv111GroupSid" />
<logic:notEmpty name="rsv111knForm" property="rsv111SvUsers" scope="request">
  <logic:iterate id="ulExBean" name="rsv111knForm" property="rsv111SvUsers" scope="request">
    <input type="hidden" name="rsv111SvUsers" value="<bean:write name="ulExBean" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv111knForm" property="rsv110PubUsrGrpSid">
  <logic:iterate id="rsv110UsrGrp" name="rsv111knForm" property="rsv110PubUsrGrpSid">
    <input type="hidden" name="rsv110PubUsrGrpSid" value="<bean:write name="rsv110UsrGrp" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv111knForm" property="rsv111PubUsrGrpSid">
  <logic:iterate id="rsv111UsrGrp" name="rsv111knForm" property="rsv111PubUsrGrpSid">
    <input type="hidden" name="rsv111PubUsrGrpSid" value="<bean:write name="rsv111UsrGrp" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsv110SchKbn" />
<html:hidden property="rsv110SchGroupSid" />
<html:hidden property="rsv111SchKbn" />
<html:hidden property="rsv111SchGroupSid" />

<bean:define id="rsvSisKbn" name="rsv111knForm" property="rsv110SisetuKbn" type="java.lang.Integer" />
<% int sisKbn = rsvSisKbn; %>

<input type="hidden" name="helpPrm" value="<bean:write name ="rsv111knForm" property="rsv110SisetuKbn"/>_<bean:write name="rsv111knForm" property="rsv110ProcMode" />" />

<logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
  <input type="hidden" name="helpPrm" value="del">
</logic:equal>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
      <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
    </li>
    <li><gsmsg:write key="cmn.reserve" /></li>
    <li class="pageTitle_subFont">
      <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
        <gsmsg:write key="cmn.for.repert" /><gsmsg:write key="cmn.check" />
      </logic:equal>
      <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
        <gsmsg:write key="cmn.delete" /><gsmsg:write key="cmn.check" />
      </logic:equal>
    </li>
    <li>
      <div>
      <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
        <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('kurikaesi_delete_kakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('kurikaesi_toroku_kakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_kurikaeshi_inp');">
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

<table class="table-left rsv110_table table-fixed table bgC_none">
  <colgroup class="w20" ></colgroup>
  <colgroup class="w80 bgC_tableCell"></colgroup>
  <tr class="js_longHeader" >
    <th class="w25">
      <gsmsg:write key="cmn.facility.group" />
    </th>
    <td class="w75 txt_m">
      <span class="mt5 display_inline-block"><bean:write name="rsv111knForm" property="rsv110GrpName" /></span>
      <button type="button" value="<gsmsg:write key="cmn.detail" /><gsmsg:write key="cmn.hide" />" class="baseBtn flo_r" onClick="hideText();">
        <gsmsg:write key="cmn.hide" />
      </button>
    </td>
  </tr>
  <tr class="js_longHeader" >
    <th class="w20 no_w">
      <gsmsg:write key="reserve.47" />
    </th>
    <td class="w80">
      <bean:write name="rsv111knForm" property="rsv110SisetuKbnName" />
    </td>
  </tr>
  <tr class="js_longHeader" >
    <th class="no_w">
      <gsmsg:write key="cmn.facility.name" />
    </th>
    <td>
      <bean:write name="rsv111knForm" property="rsv110SisetuName" />
    </td>
  </tr>
  <tr class="js_longHeader" >
    <th class="no_w">
      <gsmsg:write key="cmn.asset.register.num" />
    </th>
    <td>
      <bean:write name="rsv111knForm" property="rsv110SisanKanri" />
    </td>
  </tr>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName4">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName4" />
    </th>
    <td>
      <bean:write name="rsv111knForm" property="rsv110Prop4Value" />
    </td>
  </tr>
  </logic:notEmpty>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName5">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName5" />
    </th>
    <td>
      <bean:write name="rsv111knForm" property="rsv110Prop5Value" />
    </td>
  </tr>
  </logic:notEmpty>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName1">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName1" />
    </th>
    <td>
      <bean:write name="rsv111knForm" property="rsv110Prop1Value" />
    </td>
  </tr>
  </logic:notEmpty>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName2">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName2" />
    </th>
    <td>
      <logic:equal name="rsv111knForm" property="rsv110Prop2Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_KA) %>"><gsmsg:write key="cmn.accepted" /></logic:equal>
      <logic:equal name="rsv111knForm" property="rsv110Prop2Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_HUKA) %>"><gsmsg:write key="cmn.not" /></logic:equal>
    </td>
  </tr>
  </logic:notEmpty>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName3">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName3" />
    </th>
    <td>
      <logic:equal name="rsv111knForm" property="rsv110Prop3Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_KA) %>"><gsmsg:write key="cmn.accepted" /></logic:equal>
      <logic:equal name="rsv111knForm" property="rsv110Prop3Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_HUKA) %>"><gsmsg:write key="cmn.not" /></logic:equal>
    </td>
  </tr>
  </logic:notEmpty>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName7">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName7" />
    </th>
    <td>
      <logic:equal name="rsv111knForm" property="rsv110Prop7Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_KA) %>"><gsmsg:write key="cmn.accepted" /></logic:equal>
      <logic:equal name="rsv111knForm" property="rsv110Prop7Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_HUKA) %>"><gsmsg:write key="cmn.not" /></logic:equal>
    </td>
  </tr>
  </logic:notEmpty>
  <logic:notEmpty name="rsv111knForm" property="rsv110PropHeaderName6">
  <tr class="js_longHeader" >
    <th class="no_w">
      <bean:write name="rsv111knForm" property="rsv110PropHeaderName6" />
    </th>
    <td>
      <logic:notEmpty name="rsv111knForm" property="rsv110Prop6Value"><bean:write name="rsv111knForm" property="rsv110Prop6Value" /><gsmsg:write key="cmn.days.after" /></logic:notEmpty>
    </td>
  </tr>
  </logic:notEmpty>
  <tr class="js_longHeader" >
    <th class="no_w">
      <gsmsg:write key="cmn.memo" />
    </th>
    <td>
      <bean:write name="rsv111knForm" property="rsv110Biko" filter="false" />
    </td>
  </tr>
  <tr class="js_shortHeader">
    <th class="w20">
      <gsmsg:write key="cmn.facility.name" />
    </th>
    <td class="w80">
      <span class="mt5 display_inline-block">
        <bean:write name="rsv111knForm" property="rsv110SisetuName" />
      </span>
        <button type="button" value="<gsmsg:write key="cmn.detail" /><gsmsg:write key="cmn.show" />" class="baseBtn flo_r" onClick="showText();">
          <gsmsg:write key="cmn.show" />
        </button>
      </div>
    </td>
  </tr>
  <tr><td class="border_right_none border_left_none bgC_none" colspan="2"></td></tr>

  <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
  <%-- 登録者 --%>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="">
        <%-- 登録者 --%>
        <div class="w100 display_flex" >
          <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
            <logic:notEqual name="rsv111knForm" property="rsv111RsrRsid" value="-1">
              <span class="verAlignMid no_w">
                <gsmsg:write key="reserve.178" /><gsmsg:write key="cmn.colon"/>
              </span><!--
         --></logic:notEqual><!--
       --></logic:equal><!--
       --><span class="verAlignMid no_w">
            <logic:equal name="rsv111knForm" property="rsv110AddUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
              <bean:define id="styleTorokusya" value="" />
              <logic:equal name="rsv111knForm" property="rsv110AddUsrUkoFlg" value="1">
                <bean:define id="styleTorokusya" value="mukoUser" />
              </logic:equal>
              <logic:notEqual name="rsv111knForm" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
              <logic:notEqual name="rsv111knForm" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
              <logic:notEqual name="rsv111knForm" property="rsv110AuId" value="0">
              <a href="#!" onClick="return openUserInfoWindow(<bean:write name="rsv111knForm" property="rsv110AuId" />);">
              </logic:notEqual>
              </logic:notEqual>
              </logic:notEqual>
                <span class="<%=styleTorokusya%> no_w"><bean:write name="rsv111knForm" property="rsv110Torokusya" /></span>
              <logic:notEqual name="rsv111knForm" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
              <logic:notEqual name="rsv111knForm" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
              <logic:notEqual name="rsv111knForm" property="rsv110Torokusya" value="0">
              </a>
              </logic:notEqual>
              </logic:notEqual>
              </logic:notEqual>
            </logic:equal>
            <logic:notEqual name="rsv111knForm" property="rsv110AddUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
              <del><bean:write name="rsv111knForm" property="rsv110Torokusya" /></del>
            </logic:notEqual>
          </span>
        </div>
        <%-- 最終更新者 --%>
        <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
          <logic:notEqual name="rsv111knForm" property="rsv111RsrRsid" value="-1">
            <div class="w100 display_flex">
              <span class="verAlignMid no_w">
                <gsmsg:write key="reserve.179" /><gsmsg:write key="cmn.colon"/>
              </span><!--
           --><span class="verAlignMid no_w">
                <logic:equal name="rsv111knForm" property="rsv110EditUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                  <bean:define id="styleTorokusya" value="" />
                  <logic:equal name="rsv111knForm" property="rsv110EditUsrUkoFlg" value="1">
                    <bean:define id="styleTorokusya" value="mukoUser" />
                  </logic:equal>
                  <a href="#!" onClick="return openUserInfoWindow(<bean:write name="rsv111knForm" property="rsv110EuId" />);">
                    <span class="<%=styleTorokusya%> no_w"><bean:write name="rsv111knForm" property="rsv110Koshinsya" /></span>
                  </a>
                </logic:equal>
                <logic:equal name="rsv111knForm" property="rsv110EditUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_DELETE) %>">
                  <del><bean:write name="rsv111knForm" property="rsv110Koshinsya" /></del>
                </logic:equal>
              </span>
            </div>
          </logic:notEqual>
        </logic:equal>
      </td>
    </tr>
    <%-- 更新者 --%>
    <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
      <logic:notEqual name="rsv111knForm" property="rsv111RsrRsid" value="-1">
        <tr>
          <th class="no_w">
            <gsmsg:write key="cmn.update.user" />
          </th>
          <td>
            <logic:equal name="rsv111knForm" property="rsv110EditUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
            <bean:define id="styleStr" value=""/>
            <logic:equal name="rsv111knForm" property="rsv110EditUsrUkoFlg" value="1">
              <% styleStr = "mukoUser"; %>
            </logic:equal>
              <span class="<%=styleStr %>" ><bean:write name="rsv111knForm" property="rsv110Koshinsya" /></span>
            </logic:equal>
            <logic:equal name="rsv111knForm" property="rsv110EditUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_DELETE) %>">
              <del><bean:write name="rsv111knForm" property="rsv110Koshinsya" /></del>
            </logic:equal>
          </td>
        </tr>
      </logic:notEqual>
    </logic:equal>

    <logic:equal name="rsv111knForm" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_CAR) %>">
      <logic:equal name="rsv111knForm" property="rsvPrintUseKbn" value="<%= String.valueOf(GSConstReserve.RSV_PRINT_USE_YES) %>">
        <tr>
          <th>
            <gsmsg:write key="reserve.print" />
          </th>
          <td>
            <logic:equal name="rsv111knForm" property="rsv111PrintKbn" value="1"><gsmsg:write key="reserve.print.yes" /></logic:equal>
            <logic:notEqual name="rsv111knForm" property="rsv111PrintKbn" value="1"><gsmsg:write key="reserve.print.no" /></logic:notEqual>
          </td>
        </tr>
      </logic:equal>
    </logic:equal>
    <tr>
      <th>
        <gsmsg:write key="reserve.72" />
      </th>
      <td>
        <bean:write name="rsv111knForm" property="rsv111RsrMok" />
      </td>
    </tr>
    <logic:equal name="rsv111knForm" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_HEYA) %>">
    <tr>
      <th>
        <gsmsg:write key="reserve.use.kbn" />
      </th>
      <td>
        <div class="w60">
          <logic:equal name="rsv111knForm" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_NOSET) %>"><gsmsg:write key="reserve.use.kbn.noset" /></label></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_KAIGI) %>"><gsmsg:write key="reserve.use.kbn.meeting" /></label></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_KENSYU) %>"><gsmsg:write key="reserve.use.kbn.training" /></label></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_OTHER) %>"><gsmsg:write key="reserve.use.kbn.other" /></label></logic:equal>
        </div>
      </td>
    </tr>
    </logic:equal>
    <% if (sisKbn ==GSConstReserve.RSK_KBN_HEYA
            || sisKbn == GSConstReserve.RSK_KBN_CAR) { %>
    <tr>
      <th class="no_w">
        <gsmsg:write key="reserve.contact" />
      </th>
      <td>
          <bean:write name="rsv111knForm" property="rsv111Contact"/>
      </td>
    </tr>
    <% } %>
    <logic:equal name="rsv111knForm" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_HEYA) %>">
    <tr>
      <th class="no_w">
        <gsmsg:write key="reserve.guide" />
      </th>
      <td>
          <bean:write name="rsv111knForm" property="rsv111Guide"/>
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="reserve.park.num" />
      </th>
      <td>
          <bean:write name="rsv111knForm" property="rsv111ParkNum"/>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="rsv111knForm" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_CAR) %>">
    <!-- 行先 -->
    <tr>
      <th class="no_w">
        <gsmsg:write key="reserve.dest" />
      </th>
      <td>
          <bean:write name="rsv111knForm" property="rsv111Dest"/>
      </td>
    </tr>
    </logic:equal>
    </logic:equal>
    <tr>
      <th class="no_w">
        <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
          <gsmsg:write key="reserve.rsv111kn.3" />
        </logic:equal>
        <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
          <gsmsg:write key="reserve.rsv111kn.4" />
        </logic:equal>
      </th>
      <td>
        <span class="cl_fontWarn">
          <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
            <gsmsg:write key="reserve.rsv111kn.5" />
          </logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
            <gsmsg:write key="reserve.rsv111kn.6" />
          </logic:equal>
        </span>
        <div class="display_flex">
          <div class="mr5">
            <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_SINKI) %>">
              <logic:iterate id="day" name="rsv111knForm" property="targetDay" scope="request" indexId="idx">
                <div><bean:write name="day" /></div>
              </logic:iterate>
            </logic:equal>
            <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
              <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
                <div>【<gsmsg:write key="reserve.rsv111kn.7" />】</div>
              </logic:equal>
              <logic:iterate id="day" name="rsv111knForm" property="oldDay" scope="request" indexId="idx">
                <div><bean:write name="day" /></div>
              </logic:iterate>
            </logic:equal>
          </div>
          <div class="verAlignMid mr5">
            <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_SINKI) %>">&nbsp;</logic:equal>
            <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
              <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
                <img class="classic-display" src="../common/images/classic/icon_arrow_east.gif" alt="→">
                <img class="original-display" src="../common/images/original/icon_arrow_east.png" alt="→">
              </logic:equal>
            </logic:equal>
          </div>
          <div>
            <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_SINKI) %>">&nbsp;</logic:equal>
            <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
              <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
                <div class="">【<gsmsg:write key="reserve.rsv111kn.8" />】</div>
                <logic:iterate id="newDay" name="rsv111knForm" property="targetDay" scope="request" indexId="idx">
                  <div><bean:write name="newDay" /></div>
                </logic:iterate>
              </logic:equal>
            </logic:equal>
          </div>
        </div>
      </td>
    </tr>
    <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
      <tr>
        <th class="no_w">
          <gsmsg:write key="cmn.period" />
        </th>
        <td>
          <bean:write name="rsv111knForm" property="yoyakuFrString" />&nbsp;<gsmsg:write key="tcd.153" />&nbsp;<bean:write name="rsv111knForm" property="yoyakuToString" />
        </td>
      </tr>
      <tr>
        <th class="no_w">
          <gsmsg:write key="cmn.time" />
        </th>
        <td>
          <bean:write name="rsv111knForm" property="yoyakuTimeFrString" />&nbsp;<gsmsg:write key="tcd.153" />&nbsp;<bean:write name="rsv111knForm" property="yoyakuTimeToString" />
        </td>
      </tr>
      <tr>
        <th class="no_w">
          <gsmsg:write key="cmn.content" />
        </th>
        <td>
          <bean:write name="rsv111knForm" property="rsv111knRsrBiko" filter="false" />
        </td>
      </tr>
      <tr>
        <th class="no_w">
          <gsmsg:write key="cmn.edit.permissions" />
        </th>
        <td>
          <logic:equal name="rsv111knForm" property="rsv111RsrEdit" value="<%= String.valueOf(GSConstReserve.EDIT_AUTH_NONE) %>"><gsmsg:write key="cmn.nolimit" /></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111RsrEdit" value="<%= String.valueOf(GSConstReserve.EDIT_AUTH_PER_AND_ADU) %>"><gsmsg:write key="cmn.only.principal.or.registant" /></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111RsrEdit" value="<%= String.valueOf(GSConstReserve.EDIT_AUTH_GRP_AND_ADU) %>"><gsmsg:write key="cmn.only.affiliation.group.membership" /></logic:equal>
        </td>
      </tr>
      <tr>
        <th class="no_w">
          <gsmsg:write key="cmn.public.kbn" />
        </th>
        <td>
          <logic:equal name="rsv111knForm" property="rsv111RsrPublic" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_ALL) %>"><gsmsg:write key="cmn.public" /></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111RsrPublic" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>"><gsmsg:write key="reserve.175" /></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111RsrPublic" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_GROUP) %>"><gsmsg:write key="reserve.176" /></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111RsrPublic" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_USRGRP) %>" ><gsmsg:write key="reserve.187" /></logic:equal>
          <logic:equal name="rsv111knForm" property="rsv111RsrPublic" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_TITLE) %>" ><gsmsg:write key="reserve.189" /></logic:equal>
        </td>
      </tr>
      <logic:equal name="rsv111knForm" property="rsv111RsrPublic" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_USRGRP) %>" >
      <tr>
        <th class="no_w">
          <gsmsg:write key="reserve.190" />
        </th>
        <td>
          <logic:notEmpty name="rsv111knForm" property="rsv111knPubUsrGrpList">
            <logic:iterate id="usrMdl" name="rsv111knForm" property="rsv111knPubUsrGrpList" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
              <span class="<%=usrMdl.getCSSClassNameNormal()%>"><bean:write name="usrMdl" property="label" /></span><br>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
      </logic:equal>
      
      <% if (sisKbn ==GSConstReserve.RSK_KBN_HEYA
              || sisKbn == GSConstReserve.RSK_KBN_CAR) { %>
      <% String headName=""; %>
      <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(request); %>
      <logic:equal name="rsv111knForm" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_HEYA) %>">
        <% headName=gsMsg.getMessage("reserve.use.name.1"); %>
      </logic:equal>
      <logic:equal name="rsv111knForm" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_CAR) %>">
        <% headName=gsMsg.getMessage("reserve.use.name.2"); %>
      </logic:equal>
      <tr>
        <th>
          <span class="verAlignMid"><gsmsg:write key="reserve.busyo" />/</span><span class="verAlignMid"><%=headName%></span><span class="verAlignMid">/<gsmsg:write key="reserve.use.num" /></span>
        </th>
        <td>
          <logic:notEmpty name="rsv111knForm" property="rsv111Busyo">
            <div class="">
              <gsmsg:write key="reserve.busyo" /><gsmsg:write key="cmn.colon" /><bean:write name="rsv111knForm" property="rsv111Busyo"/>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="rsv111knForm" property="rsv111UseName">
            <div class="">
              <%= headName %><gsmsg:write key="cmn.colon" /><bean:write name="rsv111knForm" property="rsv111UseName"/>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="rsv111knForm" property="rsv111UseNum">
            <div class="">
              <gsmsg:write key="reserve.rsv110.3" /><gsmsg:write key="cmn.colon" /><bean:write name="rsv111knForm" property="rsv111UseNum"/><gsmsg:write key="cmn.persons" />
            </div>
          </logic:notEmpty>
        </td>
      </tr>
      <% } %>
    </logic:equal>

    <logic:equal name="rsv111knForm" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
    <logic:equal name="rsv111knForm" property="rsv110EditAuth" value="true">
    <logic:equal name="rsv111knForm" property="rsv111ExistSchDateFlg" value="true">
    <logic:greaterThan name="rsv111knForm" property="rsv110ScdRsSid" value="0">
    <tr>
      <th class="no_w">
        <gsmsg:write key="reserve.85" />
      </th>
      <td>
        <logic:equal name="rsv111knForm" property="rsv111ScdReflection" value="<%= String.valueOf(GSConstReserve.SCD_REFLECTION_OK) %>" ><gsmsg:write key="reserve.86" /></logic:equal>
        <logic:equal name="rsv111knForm" property="rsv111ScdReflection" value="<%= String.valueOf(GSConstReserve.SCD_REFLECTION_NO) %>" ><gsmsg:write key="reserve.87" /></logic:equal>
      </td>
    </tr>
    </logic:greaterThan>
    </logic:equal>
    </logic:equal>
    </logic:equal>

    <logic:notEmpty name="rsv111knForm" property="rsvSchUserNameArray" scope="request">
    <tr>
      <th class="no_w">
        <gsmsg:write key="schedule.3" />
      </th>
      <td>
        <logic:iterate id="usrName" name="rsv111knForm" property="rsvSchUserNameArray" scope="request" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
          <span class="<%=usrName.getCSSClassNameNormal()%>">
            <bean:write name="usrName" property="label" /><br>
          </span>
        </logic:iterate>
      </td>
    </tr>
    </logic:notEmpty>
  </table>

  <div class="footerBtn_block">
  <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="true">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('kurikaesi_delete_kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </logic:equal>
  <logic:equal name="rsv111knForm" property="rsv111DeleteFlg" value="false">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('kurikaesi_toroku_kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
  </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_kurikaeshi_inp');">
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