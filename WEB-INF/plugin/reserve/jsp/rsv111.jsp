
<%@page import="jp.groupsession.v2.rsv.rsv110.Rsv110Form"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/reserve" prefix="reserve"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<%@ page import="jp.groupsession.v2.rsv.rsv310.Rsv310Form"%>
<%@ page import="jp.groupsession.v2.usr.UserUtil"%>

<% String maxLengthNaiyo = String.valueOf(GSConstReserve.MAX_LENGTH_NAIYO); %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.rsv111.1" /> ]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv111.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsvschedule.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/reservepopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../common/js/textarea_autoresize.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<% boolean editSchFlg = false; %>
<logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
  <logic:equal name="rsv111Form" property="rsv110EditAuth" value="true">
    <logic:equal name="rsv111Form" property="rsv111ExistSchDateFlg" value="true">
      <logic:greaterThan name="rsv111Form" property="rsv110ScdRsSid" value="0">
        <% editSchFlg = true; %>
      </logic:greaterThan>
    </logic:equal>
  </logic:equal>
</logic:equal>


<body onload="showOrHide();setDisabled();changeWeekCombo();showLengthId($('#inputstr')[0], <%= maxLengthNaiyo %>, 'inputlength');rsvSchChange();<% if (editSchFlg) { %>rsvSchDisabled('rsv111SchUserUI');<% } %>" onunload="windowClose();">

  <html:form action="/reserve/rsv111">
    <input type="hidden" name="CMD" value="kurikaesi_toroku_kakunin">
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsvPrintUseKbn" />
    <html:hidden property="rsv110SisetuKbn" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv111Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv111Form" property="rsv100CsvOutField" scope="request">
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
    <html:hidden property="rsv110SelectedDateFr" />
    <html:hidden property="rsv110SelectedDateTo" />
    <html:hidden property="rsv110SelectedTimeFr" />
    <html:hidden property="rsv110SelectedTimeTo" />
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
    <html:hidden property="rsv110Public" />
    <html:hidden property="rsv110EditAuth" />
    <html:hidden property="rsv110rejectDel" />
    <html:hidden property="rsv110ApprBtnFlg" />
    <html:hidden property="rsv111InitFlg" />
    <html:hidden property="rsv111RsrRsid" />
    <html:hidden property="rsv111HeaderDspFlg" />
    <html:hidden property="rsv111ExistSchDateFlg" />
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

    <html:hidden property="rsv110Busyo" />
    <html:hidden property="rsv110UseName" />
    <html:hidden property="rsv110UseNum" />
    <html:hidden property="rsv110UseKbn" />
    <html:hidden property="rsv110Contact" />
    <html:hidden property="rsv110Guide" />
    <html:hidden property="rsv110ParkNum" />
    <html:hidden property="rsv110PrintKbn" />
    <html:hidden property="rsv110Dest" />


    <logic:notEmpty name="rsv111Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv111Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv110GroupSid" />
    <logic:notEmpty name="rsv111Form" property="sv_users" scope="request">
      <logic:iterate id="ulBean" name="rsv111Form" property="sv_users" scope="request">
        <input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv110SchKbn" />
    <html:hidden property="rsv110SchGroupSid" />

    <logic:notEmpty name="rsv111Form" property="rsv110SchNotAccessGroupList" scope="request">
      <logic:iterate id="notAccessGroup" name="rsv111Form" property="rsv110SchNotAccessGroupList">
        <input type="hidden" name="rsvSchNotAccessGroup" value="<bean:write name="notAccessGroup" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv111Form" property="rsv110PubUsrGrpSid">
      <logic:iterate id="rsv110UsrGrp" name="rsv111Form" property="rsv110PubUsrGrpSid">
        <input type="hidden" name="rsv110PubUsrGrpSid" value="<bean:write name="rsv110UsrGrp" />">
      </logic:iterate>
    </logic:notEmpty>

    <span id="rsv111SvUserArea" class="display_none"></span>
      <logic:equal name="rsv111Form" property="rsv111ScdReflection" value="1">
      <logic:notEmpty name="rsv111Form" property="rsv111SvUsers">
        <logic:iterate id="rsvSchUsers" name="rsv111Form" property="rsv111SvUsers">
          <input type="hidden" name="rsv111SvUsers" value="<bean:write name="rsvSchUsers" />">
        </logic:iterate>
        </logic:notEmpty>
      </logic:equal>
    </span>

    <bean:define id="rsvSisKbn" name="rsv111Form" property="rsv110SisetuKbn" type="java.lang.Integer" />
    <% int sisKbn = rsvSisKbn; %>

    <input type="hidden" name="helpPrm" value="<bean:write name ="rsv111Form" property="rsv110SisetuKbn"/>_<bean:write name="rsv111Form" property="rsv110ProcMode" />" />
    <input type="hidden" name="yearRangeMinFr" value="1" />
    <input type="hidden" name="yearRangeMaxFr" value="3" />
    <input type="hidden" name="yearRangeMinTo" value="1" />
    <input type="hidden" name="yearRangeMaxTo" value="3" />
    <input type="hidden" name="hourDivision" value="<bean:write name="rsv111Form" property="racHourDiv" />" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.for.repert" />
        </li>
        <li>
          <div>
            <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
              <button type="submit" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onclick="setDateParam();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
                <gsmsg:write key="cmn.change" />
              </button>
            </logic:equal>
            <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
              <button type="submit" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onclick="setDateParam();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <gsmsg:write key="cmn.entry" />
              </button>
            </logic:notEqual>
            <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
              <button type="button" value="<gsmsg:write key="cmn.register.copy" />" class="baseBtn" onClick="buttonPush('copytouroku');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                <gsmsg:write key="cmn.register.copy2" />
              </button>
            </logic:equal>
            <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <logic:notEqual name="rsv111Form" property="rsv111RsrRsid" value="-1">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('delete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:notEqual>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_menu');">
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
        <button type="button" value="<gsmsg:write key="cmn.general.regist" />" class="baseBtn" onclick="setDateParam();buttonPush('ippan');">
          <gsmsg:write key="cmn.general.regist" />
        </button>
      </div>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left rsv110_table table-fixed table bgC_none">
        <colgroup class="w15" ></colgroup>
        <colgroup class="w5" ></colgroup>
        <colgroup class="w80 bgC_tableCell"></colgroup>
        <tr class="js_longHeader">
          <th class="w20 no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.facility.group" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div class="component_bothEnd">
              <div>
                <span>
                  <bean:write name="rsv111Form" property="rsv110GrpName" />
                </span>
              </div>
              <div>
                <button type="button" value="<gsmsg:write key="cmn.hide" />" class="baseBtn" onClick="hideText();">
                  <gsmsg:write key="cmn.hide" />
                </button>
              </div>
            </div>
          </td>
        </tr>

        <tr  class="js_longHeader">
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="reserve.47" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv111Form" property="rsv110SisetuKbnName" />
            </span>
          </td>
        </tr>

        <tr class="js_longHeader">
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.facility.name" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv111Form" property="rsv110SisetuName" />
            </span>
          </td>
        </tr>

        <tr class="js_longHeader">
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.asset.register.num" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv111Form" property="rsv110SisanKanri" />
            </span>
          </td>
        </tr>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName4">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName4" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv111Form" property="rsv110Prop4Value" />
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName5">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName5" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv111Form" property="rsv110Prop5Value" />
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName1">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName1" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv111Form" property="rsv110Prop1Value" />
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName2">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName2" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv111Form" property="rsv110Prop2Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_KA) %>">
                  <gsmsg:write key="cmn.accepted" />
                </logic:equal>
                <logic:equal name="rsv111Form" property="rsv110Prop2Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_HUKA) %>">
                  <gsmsg:write key="cmn.not" />
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName3">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName3" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv111Form" property="rsv110Prop3Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_KA) %>">
                  <gsmsg:write key="cmn.accepted" />
                </logic:equal>
                <logic:equal name="rsv111Form" property="rsv110Prop3Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_HUKA) %>">
                  <gsmsg:write key="cmn.not" />
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName7">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName7" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv111Form" property="rsv110Prop7Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_KA) %>">
                  <gsmsg:write key="cmn.accepted" />
                </logic:equal>
                <logic:equal name="rsv111Form" property="rsv110Prop7Value" value="<%= String.valueOf(GSConstReserve.PROP_KBN_HUKA) %>">
                  <gsmsg:write key="cmn.not" />
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv111Form" property="rsv110PropHeaderName6">
          <tr class="js_longHeader">
            <th class="no_w" colspan="2">
              <span>
                <bean:write name="rsv111Form" property="rsv110PropHeaderName6" />
              </span>
            </th>
            <td class="txt_l">
              <logic:notEmpty name="rsv111Form" property="rsv110Prop6Value">
                <span>
                  <bean:write name="rsv111Form" property="rsv110Prop6Value" />
                  <gsmsg:write key="cmn.days.after" />
              </logic:notEmpty>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <tr class="js_longHeader">
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv111Form" property="rsv110Biko" filter="false" />
            </span>
          </td>
        </tr>
        <tr class="js_shortHeader">
          <th class="w20 no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.facility.name" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div class="component_bothEnd">
              <div>
                <span>
                  <bean:write name="rsv111Form" property="rsv110SisetuName" />
                </span>
              </div>
              <div>
                <button type="button" value="<gsmsg:write key="cmn.show" />" class="baseBtn" onClick="showText();">
                  <gsmsg:write key="cmn.show" />
                </button>
              </div>
            </div>
          </td>
        </tr>

        <tr><td class="border_right_none border_left_none bgC_none" colspan="3"></td></tr>
        <%-- 新規登録者 --%>
        <tr>
          <th class="w20 no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.registant" />
            </span>
          </th>
          <td class="w80 txt_l">
            <%-- 登録者 --%>
            <div class="w100 display_flex" >
              <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
                <logic:notEqual name="rsv111Form" property="rsv111RsrRsid" value="-1">
                  <span class="verAlignMid no_w">
                    <gsmsg:write key="reserve.178" /><gsmsg:write key="cmn.colon"/>
                  </span><!--
             --></logic:notEqual><!--
           --></logic:equal><!--
           --><span class="verAlignMid no_w">
                <logic:equal name="rsv111Form" property="rsv110AddUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                  <bean:define id="styleTorokusya" value="" />
                  <logic:equal name="rsv111Form" property="rsv110AddUsrUkoFlg" value="1">
                    <bean:define id="styleTorokusya" value="mukoUser" />
                  </logic:equal>
                  <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
                  <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
                  <logic:notEqual name="rsv111Form" property="rsv110AuId" value="0">
                  <a href="#!" onClick="return openUserInfoWindow(<bean:write name="rsv111Form" property="rsv110AuId" />);">
                  </logic:notEqual>
                  </logic:notEqual>
                  </logic:notEqual>
                    <span class="<%=styleTorokusya%> no_w"><bean:write name="rsv111Form" property="rsv110Torokusya" /></span>
                  <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
                  <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
                  <logic:notEqual name="rsv111Form" property="rsv110Torokusya" value="0">
                  </a>
                  </logic:notEqual>
                  </logic:notEqual>
                  </logic:notEqual>
                </logic:equal>
                <logic:notEqual name="rsv111Form" property="rsv110AddUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                  <del><bean:write name="rsv111Form" property="rsv110Torokusya" /></del>
                </logic:notEqual>
              </span><!--
           --><span class="verAlignMid no_w ml10">
                <bean:write name="rsv111Form" property="rsv110AddDate" />
              </span>
            </div>
            <%-- 最終更新者 --%>
            <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_EDIT) %>">
              <logic:notEqual name="rsv111Form" property="rsv111RsrRsid" value="-1">
                <div class="w100 display_flex">
                  <span class="verAlignMid no_w">
                    <gsmsg:write key="reserve.179" /><gsmsg:write key="cmn.colon"/>
                  </span><!--
               --><span class="verAlignMid no_w">
                    <logic:equal name="rsv111Form" property="rsv110EditUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                      <bean:define id="styleTorokusya" value="" />
                      <logic:equal name="rsv111Form" property="rsv110EditUsrUkoFlg" value="1">
                        <bean:define id="styleTorokusya" value="mukoUser" />
                      </logic:equal>
                      <a href="#!" onClick="return openUserInfoWindow(<bean:write name="rsv111Form" property="rsv110EuId" />);">
                        <span class="<%=styleTorokusya%> no_w"><bean:write name="rsv111Form" property="rsv110Koshinsya" /></span>
                      </a>
                    </logic:equal>
                    <logic:equal name="rsv111Form" property="rsv110EditUsrJKbn" value="<%= String.valueOf(GSConst.JTKBN_DELETE) %>">
                      <del><bean:write name="rsv111Form" property="rsv110Koshinsya" /></del>
                    </logic:equal>
                  </span><!--
               --><span class="verAlignMid no_w ml10">
                    <bean:write name="rsv111Form" property="rsv110EditDate" />
                  </span>
                </div>
              </logic:notEqual>
            </logic:equal>
          </td>
        </tr>

        <%-- 印刷 --%>
        <logic:equal name="rsv111Form" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_CAR) %>">
          <logic:equal name="rsv111Form" property="rsvPrintUseKbn" value="<%= String.valueOf(GSConstReserve.RSV_PRINT_USE_YES) %>">
            <tr>
              <th class="w20 no_w" colspan="2">
                <span><gsmsg:write key="reserve.print" /></span>
              </th>
              <td class="w80 txt_l">
                <span class="verAlignMid">
                  <html:checkbox name="rsv111Form" property="rsv111PrintKbn" value="1" styleId="print" />
                  <label for="print"><gsmsg:write key="reserve.print.yes" /></label>
                </span>
              </td>
            </tr>
          </logic:equal>
        </logic:equal>
        <!--    利用区分 -->
        <tr>
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="reserve.72" />
            </span>
            <logic:equal name="rsv111Form" property="rsv110EditAuth" value="true">
              <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_POPUP) %>">
                <span class="cl_fontWarn">
                  <gsmsg:write key="cmn.comments" />
                </span>
              </logic:notEqual>
            </logic:equal>
          </th>
          <td class="txt_l">
            <html:text name="rsv111Form" property="rsv111RsrMok" maxlength="50" styleClass="wp400" />
          </td>
        </tr>
        <logic:equal name="rsv111Form" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_HEYA) %>">
          <tr>
            <th colspan="2" class="w20 no_w">
              <span><gsmsg:write key="reserve.use.kbn" /></span>
            </th>
            <td class="w80 txt_l">
              <span class="verAlignMid">
                <html:radio name="rsv111Form" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_NOSET) %>" styleId="rsyUkbnNoset" >
                  <label for="rsyUkbnNoset" class="mr10"><gsmsg:write key="reserve.use.kbn.noset" /></label>
                </html:radio>
                <html:radio name="rsv111Form" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_KAIGI) %>" styleId="rsyUkbnKaigi" >
                  <label for="rsyUkbnKaigi" class="mr10"><gsmsg:write key="reserve.use.kbn.meeting" /></label>
                </html:radio>
                <html:radio name="rsv111Form" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_KENSYU) %>" styleId="rsyUkbnKensyu" >
                  <label for="rsyUkbnKensyu" class="mr10"><gsmsg:write key="reserve.use.kbn.training" /></label>
                </html:radio>
                <html:radio name="rsv111Form" property="rsv111UseKbn" value="<%= String.valueOf(GSConstReserve.RSY_USE_KBN_OTHER) %>" styleId="rsyUkbnOther">
                  <label for="rsyUkbnOther"><gsmsg:write key="reserve.use.kbn.other" /></label>
                </html:radio>
              </span>
            </td>
          </tr>
        </logic:equal>

        <% if (sisKbn ==GSConstReserve.RSK_KBN_HEYA
            || sisKbn == GSConstReserve.RSK_KBN_CAR) { %>
        <tr>
          <th colspan="2" class="w20 no_w">
            <span><!-- 連絡先--><gsmsg:write key="reserve.contact" /></span>
          </th>
          <td class="w80 txt_l">
            <html:text name="rsv111Form" property="rsv111Contact" maxlength="20" styleClass="wp150" />
          </td>
        </tr>
        <% } %>

        <logic:equal name="rsv111Form" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_HEYA) %>">
          <tr>
            <th colspan="2" class="w20 no_w">
              <span><!--  会議名案内 --><gsmsg:write key="reserve.guide" /></span>
            </th>
            <td class="w80 txt_l">
              <html:text name="rsv111Form" property="rsv111Guide" maxlength="50" styleClass="wp300" />
            </td>
          </tr>
          <tr>
            <th class="w20 no_w" colspan="2">
              <span><!--  駐車場見込み台数--><gsmsg:write key="reserve.park.num" /></span>
            </th>
            <td class="w80 txt_l">
              <html:text name="rsv111Form" property="rsv111ParkNum" maxlength="5" styleClass="wp150" />
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="rsv111Form" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_CAR) %>">
          <%-- 行先 --%>
          <tr>
            <th class="w20 no_w" colspan="2">
              <span><gsmsg:write key="reserve.dest" /></span>
            </th>
            <td class="w80 txt_l">
              <html:text name="rsv111Form" property="rsv111Dest" maxlength="50" styleClass="wp300" />
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.for.repert" />
            </span>
          </th>
          <td class="txt_l">

            <div class="display_inline txt_t">
              <div class="verAlignMid">
                <html:radio name="rsv111Form" property="rsv111RsrKbn" styleId="everyday" value="<%=String.valueOf(GSConstReserve.KAKUTYO_KBN_EVERY_DAY)%>" onclick="setDisabled();" />
                <label for="everyday"><gsmsg:write key="cmn.everyday" /></label>
              </div>
              <div class="verAlignMid ml10">
                <html:radio name="rsv111Form" property="rsv111RsrKbn" styleId="everyweek" value="<%=String.valueOf(GSConstReserve.KAKUTYO_KBN_EVERY_WEEK)%>" onclick="setDisabled();" />
                <label for="everyweek"><gsmsg:write key="cmn.weekly2" /></label>
              </div>
              <div class="verAlignMid ml10">
                <html:radio name="rsv111Form" property="rsv111RsrKbn" styleId="everymonth" value="<%= String.valueOf(GSConstReserve.KAKUTYO_KBN_EVERY_MONTH) %>" onclick="setDisabled();" />
                <label for="everymonth"><gsmsg:write key="cmn.monthly.2" /></label>
              </div>
              <div class="verAlignMid ml10">
                <html:radio name="rsv111Form" property="rsv111RsrKbn" value="<%=String.valueOf(GSConstReserve.KAKUTYO_KBN_EVERY_YEAR)%>" styleId="everyyear" onclick="setDisabled();" />
                <label for="everyyear"><gsmsg:write key="cmn.yearly" /></label>
              </div>
            </div>
            <div class="js_adv-monthly mt5 display_n">
              <div class="verAlignMid">
                <span><gsmsg:write key="cmn.week" />：</span>
                <html:select property="rsv111RsrWeek" onchange="changeWeekCombo();">
                  <html:optionsCollection name="rsv111Form" property="rsv111WeekList" value="value" label="label" />
                </html:select>
              </div>
              <div class="verAlignMid ml5">
                <span><gsmsg:write key="cmn.day" />：</span>
                <html:select property="rsv111RsrDay">
                  <html:optionsCollection name="rsv111Form" property="rsv111ExDayList" value="value" label="label" />
                </html:select>
              </div>
            </div>
            <div class="mt5 js_adv-weekday">
              <div>
                <div class="bor_l1 bor_t1 display_inline">
                  <div class="wp30hp30 bgC_calSunday verAlignMid bor_r1">
                    <span class="cl_fontSunday mrl_auto">
                      <gsmsg:write key="cmn.sunday" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                    <span class="mrl_auto">
                      <gsmsg:write key="cmn.Monday" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                    <span class="mrl_auto">
                      <gsmsg:write key="cmn.tuesday" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                    <span class="mrl_auto">
                      <gsmsg:write key="cmn.wednesday" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                    <span class="mrl_auto">
                      <gsmsg:write key="cmn.thursday" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                    <span class="mrl_auto">
                      <gsmsg:write key="cmn.friday" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_calSaturday verAlignMid bor_r1">
                    <span class="cl_fontSaturday mrl_auto">
                      <gsmsg:write key="cmn.saturday" />
                    </span>
                  </div>
                </div>
              </div>
              <div>
                <div class="bor_l1 bor_t1 bor_b1  display_inline">
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="cl_fontSunday mrl_auto">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek1" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="mrl_auto js_tableTopCheck">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek2" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="mrl_auto">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek3" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="mrl_auto">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek4" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="mrl_auto">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek5" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="mrl_auto">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek6" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                  <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck">
                    <span class="cl_fontSaturday mrl_auto">
                      <html:checkbox name="rsv111Form" property="rsv111RsrDweek7" value="<%=String.valueOf(GSConstReserve.COMBO_DEFAULT_ON_VALUE)%>" />
                    </span>
                  </div>
                </div>
              </div>
            </div>


            <div class="js_adv-date mt5 display_n">
              <html:select property="rsv111RsrMonthOfYearly" styleId="selMonthOfYearly">
                <html:optionsCollection name="rsv111Form" property="rsv110MonthComboList" value="value" label="label" />
              </html:select>
              <html:select property="rsv111RsrDayOfYearly" styleId="selDayOfYearly">
                <html:optionsCollection name="rsv111Form" property="rsv111ExDayOfYearlyList" value="value" label="label" />
              </html:select>
            </div>
            <div class="mt10 js_adv-everyday">
              <span class="cl_fontWarn fs_13">
                <gsmsg:write key="schedule.sch041.2" />
              </span>
              <br>
              <div class="verAlignMid">
                <html:radio name="rsv111Form" property="rsv111RsrTranKbn" styleId="tranKbn1" value="<%= String.valueOf(GSConstReserve.FURIKAE_NO) %>" />
                <label for="tranKbn1">
                  <gsmsg:write key="reserve.rsv111.9" />
                </label>
              </div>
              <br>
              <div class="verAlignMid">
                <html:radio name="rsv111Form" property="rsv111RsrTranKbn" styleId="tranKbn2" value="<%= String.valueOf(GSConstReserve.FURIKAE_MAE) %>" />
                <label for="tranKbn2">
                  <gsmsg:write key="cmn.change.before.businessday" />
                </label>
              </div>
              <br>
              <div class="verAlignMid">
                <html:radio name="rsv111Form" property="rsv111RsrTranKbn" styleId="tranKbn3" value="<%= String.valueOf(GSConstReserve.FURIKAE_ATO) %>" />
                <label for="tranKbn3">
                  <gsmsg:write key="cmn.change.next.businessday" />
                </label>
              </div>
              <br>
            </div>
            <div>
              <gsmsg:write key="cmn.comments" />
              <gsmsg:write key="cmn.holiday.based.timecard" />
            </div>


          </td>
        </tr>


        </td>
        </tr>

        <tr>
          <th rowspan="2" class="no_w">
            <span>
              <gsmsg:write key="cmn.period" />
            </span>
          </th>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.start" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:text name="rsv111Form" property="rsv111RsrDateFr" maxlength="10" styleClass="txt_c wp95 ml5 datepicker js_frDatePicker" styleId="selDatefr" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveFromDay($('#selDatefr')[0], 1)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onclick="return moveFromDay($('#selDatefr')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return moveFromDay($('#selDatefr')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveFromDay($('#selDatefr')[0], 3)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right"></i>
              </button>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.end" />
            </span>
          </th>
          <td class="txt_l">

            <span class="verAlignMid">
              <html:text name="rsv111Form" property="rsv111RsrDateTo" maxlength="10" styleClass="txt_c wp95 ml5 datepicker js_toDatePicker" styleId="selDateto" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveToDay($('#selDateto')[0], 1)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onclick="return moveToDay($('#selDateto')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return moveToDay($('#selDateto')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveToDay($('#selDateto')[0], 3)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right"></i>
              </button>
            </span>

          </td>
        </tr>

        <tr>
          <th colspan="2" class="no_w">
            <span>
              <gsmsg:write key="cmn.time" />
            </span>
          </th>
          <td align="left" class="td_wt">
            <div class="verAlignMid">
              <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
                <html:text name="rsv111Form" property="rsv111RsrTimeFr" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
                <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
              </span>
              <span class="ml5">～</span>
              <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
                <html:text name="rsv111Form" property="rsv111RsrTimeTo" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
                <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
              </span>
            </div>

            <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv111_sub.jsp" />

            <%-- 担当部署/使用者名/人数 --%>
            <% if (sisKbn ==GSConstReserve.RSK_KBN_HEYA
            || sisKbn == GSConstReserve.RSK_KBN_CAR) { %>
            <%
                 String headName = "";
                 jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(
                                    request);
                 String msgTanto = gsMsg.getMessage("reserve.use.name.1");
                 String msgUser = gsMsg.getMessage("reserve.use.name.2");
                        %>
            <logic:equal name="rsv111Form" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_HEYA) %>">
              <% headName=msgTanto; %>
            </logic:equal>
            <logic:equal name="rsv111Form" property="rsv110SisetuKbn" value="<%= String.valueOf(GSConstReserve.RSK_KBN_CAR) %>">
              <% headName=msgUser; %>
            </logic:equal>
        <tr>
          <th colspan="2" class="w20">
            <span class="verAlignMid"><gsmsg:write key="reserve.busyo" />/</span><span class="verAlignMid"><%=headName%></span><span class="verAlignMid">/<gsmsg:write key="reserve.use.num" /></span>
          </th>
          <td class="w80 txt_l">
            <div class="display_flex">
              <div class="wp60  mr5">
                <gsmsg:write key="reserve.busyo" />
              </div>
              <div class="">
                <html:text name="rsv111Form" property="rsv111Busyo" maxlength="50" styleClass="wp150" />
              </div>
            </div>
            <div class="display_flex mt5">
              <div class="wp60  mr5">
                <%=headName%>
              </div>
              <div class="">
                <html:text name="rsv111Form" property="rsv111UseName" maxlength="62" styleClass="wp150" />
              </div>
            </div>
            <div class="display_flex mt5">
              <div class="wp60  mr5">
                <gsmsg:write key="reserve.rsv110.3" />
              </div>
              <div class="display_inline">
                <html:text name="rsv111Form" property="rsv111UseNum" maxlength="5" styleClass="txt_r wp60" /><gsmsg:write key="cmn.persons" />
              </div>
            </div>
          </td>
        </tr>
        <%
          }
        %>

        <%
          if (editSchFlg) {
        %>
        <tr>
          <th colspan="2" class="no_w">
            <span>
              <gsmsg:write key="reserve.85" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:radio styleId="refOk" name="rsv111Form" property="rsv111ScdReflection" value="<%= String.valueOf(GSConstReserve.SCD_REFLECTION_OK) %>" onclick="rsvSchDisabled('rsv111SchUserUI');rsv111SaveSvUser();" />
              <label for="refOk" class="mr10"><gsmsg:write key="reserve.86" /></label>
              <html:radio styleId="refNo" name="rsv111Form" property="rsv111ScdReflection" value="<%= String.valueOf(GSConstReserve.SCD_REFLECTION_NO) %>" onclick="rsvSchDisabled('rsv111SchUserUI');rsv111SaveSvUser();" />
              <label for="refNo"><gsmsg:write key="reserve.87" /></label>
            </span>
          </td>
        </tr>
        <% } %>


        <logic:equal name="rsv111Form" property="schedulePluginKbn" value="<%=String.valueOf(GSConst.PLUGIN_USE)%>">

          <tr>
            <th class="no_w" colspan="2">
              <span>
                <gsmsg:write key="schedule.3" />
              </span>
            </th>
            <td class="txt_l">

              <div>
                <span class="verAlignMid">
                  <html:radio property="rsv111SchKbn" value="<%=String.valueOf(GSConstReserve.RSV_SCHKBN_USER)%>" styleId="rsvSchKbn0" onclick="rsvSchChange();" />
                  <label for="rsvSchKbn0" class="mr10"><gsmsg:write key="cmn.user" /></label>
                  <html:radio property="rsv111SchKbn" value="<%=String.valueOf(GSConstReserve.RSV_SCHKBN_GROUP)%>"  styleId="rsvSchKbn1" onclick="rsvSchChange();" />
                  <label for="rsvSchKbn1"><gsmsg:write key="cmn.group" /></label>
                </span>
              </div>

              <span id="rsvSchGroup">
                <div>
                  <span>
                    <gsmsg:write key="reserve.167" />
                  </span>
                </div>

                <html:select property="rsv111SchGroupSid" styleId="rsvSchGrpSid" styleClass="wp150">
                  <logic:notEmpty name="rsv111Form" property="rsv110SchGroupLabel" scope="request">

                    <logic:iterate id="exSchGpBean" name="rsv111Form" property="rsv110SchGroupLabel" scope="request">
                      <%
                        boolean schGpDisabled = false;
                      %>
                      <logic:equal name="exSchGpBean" property="viewKbn" value="false">
                        <%
                          schGpDisabled = true;
                        %>
                      </logic:equal>
                      <bean:define id="gpValue" name="exSchGpBean" property="value" type="java.lang.String" />
                      <logic:equal name="exSchGpBean" property="styleClass" value="0">
                        <html:option value="<%=gpValue%>" disabled="<%=schGpDisabled%>">
                          <bean:write name="exSchGpBean" property="label" />
                        </html:option>
                      </logic:equal>
                      <logic:notEqual name="exSchGpBean" property="styleClass" value="0">
                        <html:option value="<%=gpValue%>" disabled="<%=schGpDisabled%>">
                          <bean:write name="exSchGpBean" property="label" />
                        </html:option>
                      </logic:notEqual>

                    </logic:iterate>

                  </logic:notEmpty>
                </html:select>

                <button type="button" onclick="openGroupWindow_Disabled(this.form.rsv111SchGroupSid, 'rsv111SchGroupSid', '0', '', 1, '', 'rsvSchNotAccessGroup', 1)" class="iconBtn-border" value="&nbsp;&nbsp;" id="rsvSchGrpBtn1">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                </button>
              </span>

              <div id="rsvSchUser">
                <span>
                  <gsmsg:write key="reserve.166" />
                </span>

                <ui:usrgrpselector name="rsv111Form" property="rsv111SchUserUI" styleClass="hp215" />
              </div>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="schedule.18" />
            </span>
          </td>
          <td class="txt_l ">
            <span>※<gsmsg:write key="schedule.35" /></span>
            <button type="button" value="<gsmsg:write key="schedule.17" />" class="baseBtn" onClick="setDateParam();openScheduleReserveWindowForReserve(<%=String.valueOf(Rsv310Form.POP_DSP_MODE_RSV111)%>);">
              <gsmsg:write key="schedule.17" />
            </button>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
          <button type="submit" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onclick="setDateParam();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
            <gsmsg:write key="cmn.change" />
          </button>
        </logic:equal>
        <logic:notEqual name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
          <button type="submit" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onclick="setDateParam();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <gsmsg:write key="cmn.entry" />
          </button>
        </logic:notEqual>
        <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
          <button type="button" value="<gsmsg:write key="cmn.register.copy" />" class="baseBtn" onClick="buttonPush('copytouroku');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
            <gsmsg:write key="cmn.register.copy2" />
          </button>
        </logic:equal>
        <logic:equal name="rsv111Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_menu');">
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