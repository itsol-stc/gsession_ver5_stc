<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<%@ page import="jp.groupsession.v2.rsv.rsv310.Rsv310Form"%>
<%
  String maxLengthNaiyo = String.valueOf(GSConstReserve.MAX_LENGTH_NAIYO);
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>"> [ <gsmsg:write key="reserve.19" /> ]</logic:equal> <logic:equal name="rsv110Form" property="rsv110ProcMode"
    value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
    <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">[ <gsmsg:write key="reserve.rsv110.1" /> ]</logic:notEqual>
    <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">[ <gsmsg:write key="reserve.rsv110.2" /> ]</logic:equal>
  </logic:equal> <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">[ <gsmsg:write key="reserve.rsv110.1" /> ]</logic:equal>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../reserve/js/rsv110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/rsvschedule.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/reservepopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/textarea_autoresize.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
</head>

<%
  boolean editSchFlg = false;
%>
<logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
  <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
    <logic:equal name="rsv110Form" property="rsv110ExistSchDateFlg" value="true">
      <logic:greaterThan name="rsv110Form" property="rsv110ScdRsSid" value="0">
        <%
          editSchFlg = true;
        %>
      </logic:greaterThan>
    </logic:equal>
  </logic:equal>
</logic:equal>

<%
  String showScript = "";
%>
<logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
  <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
    <%
      showScript = "showLengthId($(\'#inputstr\')[0], " + maxLengthNaiyo
                + ", \'inputlength\');rsvSchChange();";
    %>
  </logic:notEqual>
</logic:equal>

<body onunload="calWindowClose();windowClose();" onload="showOrHide();<%=showScript%><%if (editSchFlg) {%>rsvSchDisabled('rsv110SchUserUI');<%}%>">

  <html:form action="/reserve/rsv110">
    <input type="hidden" name="CMD" value="sisetu_yoyaku_kakunin">
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsvPrintUseKbn" />
    <html:hidden property="rsv110ProcMode" />
    <html:hidden property="rsv110InitFlg" />
    <html:hidden property="rsv110RsySid" />
    <html:hidden property="rsv110RsdSid" />
    <html:hidden property="rsv110SinkiDefaultDate" />
    <html:hidden property="rsv110ScdRsSid" />
    <html:hidden property="rsv110EditAuth" />
    <html:hidden property="rsv110ApprBtnFlg" />
    <html:hidden property="rsv110rejectDel" />
    <html:hidden property="rsv110SisetuKbn" />
    <html:hidden property="rsv110SelectedYearFr" />
    <html:hidden property="rsv110SelectedMonthFr" />
    <html:hidden property="rsv110SelectedDayFr" />
    <html:hidden property="rsv110SelectedYearTo" />
    <html:hidden property="rsv110SelectedMonthTo" />
    <html:hidden property="rsv110SelectedDayTo" />
    <html:hidden property="rsv110SelectedHourFr" />
    <html:hidden property="rsv110SelectedMinuteFr" />
    <html:hidden property="rsv110SelectedHourTo" />
    <html:hidden property="rsv110SelectedMinuteTo" />

    <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" />

    <logic:notEmpty name="rsv110Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv110Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv111InitFlg" />
    <html:hidden property="rsv111RsrRsid" />
    <html:hidden property="rsv111RsrKbn" />
    <html:hidden property="rsv111RsrDweek1" />
    <html:hidden property="rsv111RsrDweek2" />
    <html:hidden property="rsv111RsrDweek3" />
    <html:hidden property="rsv111RsrDweek4" />
    <html:hidden property="rsv111RsrDweek5" />
    <html:hidden property="rsv111RsrDweek6" />
    <html:hidden property="rsv111RsrDweek7" />
    <html:hidden property="rsv111RsrWeek" />
    <html:hidden property="rsv111RsrDay" />
    <html:hidden property="rsv111RsrDayOfYearly" />
    <html:hidden property="rsv111RsrMonthOfYearly" />
    <html:hidden property="rsv111RsrTranKbn" />
    <html:hidden property="rsv111RsrDateFr" />
    <html:hidden property="rsv111RsrDateTo" />
    <html:hidden property="rsv111RsrTimeFr" />
    <html:hidden property="rsv111RsrTimeTo" />
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
    <html:hidden property="rsv111RsrMok" />
    <html:hidden property="rsv111RsrBiko" />
    <html:hidden property="rsv111RsrEdit" />
    <html:hidden property="rsv111RsrPublic" />
    <html:hidden property="rsv111ScdReflection" />
    <html:hidden property="rsv111Busyo" />
    <html:hidden property="rsv111UseName" />
    <html:hidden property="rsv111UseNum" />
    <html:hidden property="rsv111UseKbn" />
    <html:hidden property="rsv111Contact" />
    <html:hidden property="rsv111Guide" />
    <html:hidden property="rsv111ParkNum" />
    <html:hidden property="rsv111PrintKbn" />
    <html:hidden property="rsv111Dest" />
    <html:hidden property="rsv110HeaderDspFlg" />
    <html:hidden property="rsv110ExistSchDateFlg" />

    <logic:notEmpty name="rsv110Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv110Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv111GroupSid" />
    <logic:notEmpty name="rsv110Form" property="rsv111SvUsers" scope="request">
      <logic:iterate id="ulExBean" name="rsv110Form" property="rsv111SvUsers" scope="request">
        <input type="hidden" name="rsv111SvUsers" value="<bean:write name="ulExBean" />">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv111SchKbn" />
    <html:hidden property="rsv111SchGroupSid" />

    <logic:notEmpty name="rsv110Form" property="rsv110SchNotAccessGroupList" scope="request">
      <logic:iterate id="notAccessGroup" name="rsv110Form" property="rsv110SchNotAccessGroupList">
        <input type="hidden" name="rsvSchNotAccessGroup" value="<bean:write name="notAccessGroup" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv110Form" property="rsv111PubUsrGrpSid">
      <logic:iterate id="rsv111UsrGrp" name="rsv110Form" property="rsv111PubUsrGrpSid">
        <input type="hidden" name="rsv111PubUsrGrpSid" value="<bean:write name="rsv111UsrGrp" />">
      </logic:iterate>
    </logic:notEmpty>

    <bean:define id="rsvSisKbn" name="rsv110Form" property="rsv110SisetuKbn" type="java.lang.Integer" />
    <%
      int sisKbn = rsvSisKbn;
      String rsvElementSizeClass = "w100";
    %>

    <input type="hidden" name="helpPrm" value="<bean:write name ="rsv110Form" property="rsv110SisetuKbn"/>_<bean:write name="rsv110Form" property="rsv110ProcMode" />" />
    <input type="hidden" name="yearRangeMinFr" value="1" />
    <input type="hidden" name="yearRangeMaxFr" value="3" />
    <input type="hidden" name="yearRangeMinTo" value="1" />
    <input type="hidden" name="yearRangeMaxTo" value="3" />
    <input type="hidden" name="hourDivision" value="<bean:write name="rsv110Form" property="racHourDiv" />" />


    <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
      <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
      <% rsvElementSizeClass = "w80"; %>
    </logic:notEqual>

    <div class="pageTitle <%= rsvElementSizeClass %> mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
            <gsmsg:write key="cmn.entry" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
              <gsmsg:write key="cmn.check" />
            </logic:notEqual>
            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <gsmsg:write key="cmn.change" />
            </logic:equal>
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
            <gsmsg:write key="cmn.check" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
            <gsmsg:write key="cmn.entry" />
          </logic:equal>
        </li>
        <li>
          <div>

            <bean:define id="strRsv110ProcMode" name="rsv110Form" property="rsv110ProcMode" type="java.lang.String" />
                 <%
                          if (strRsv110ProcMode != null
                                  && (strRsv110ProcMode.equals(GSConstReserve.PROC_MODE_EDIT)
                                      || strRsv110ProcMode.equals(GSConstReserve.PROC_MODE_POPUP))) {
                        %>
            <logic:equal name="rsv110Form" property="rsv110ApprBtnFlg" value="1">
              <button type="button" value="<gsmsg:write key="cmn.approval" />" id="syoninbtn" class="baseBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="cmn.approval" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="cmn.approval" />">
                <gsmsg:write key="cmn.approval" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.rejection" />" id="kyakkabtn" class="baseBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
                <gsmsg:write key="cmn.rejection" />
              </button>
              </td>
              </tr>
            </logic:equal>

            <logic:equal name="rsv110Form" property="rsv110ApprBtnFlg" value="2">
              <button type="button" value="<gsmsg:write key="reserve.appr.st1" />" id="waitbtn" class="baseBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_sashimodoshi.png" alt="<gsmsg:write key="reserve.appr.st1" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_sashimodoshi.png" alt="<gsmsg:write key="reserve.appr.st1" />">
                <gsmsg:write key="reserve.appr.st1" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.rejection" />" id="kyakkabtn" class="baseBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
                <gsmsg:write key="cmn.rejection" />
              </button>
            </logic:equal>



          <%
            }
          %>
            <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('back_to_menu');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:notEqual>
            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=GSConstReserve.PROC_MODE_POPUP%>">
                <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onclick="window.parent.callYokyakuWindowClose();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                  <gsmsg:write key="cmn.close" />
                </button>
              </logic:equal>
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=GSConstReserve.PROC_MODE_POPUP%>">

                <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
                  <button type="button" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onclick="buttonPush('sisetu_yoyaku_kakunin');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                    <gsmsg:write key="cmn.entry" />
                  </button>
                </logic:notEqual>

                <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
                  <button type="button" value="<gsmsg:write key="cmn.change" />" class="baseBtn" onClick="buttonPush('sisetu_yoyaku_kakunin');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
                    <gsmsg:write key="cmn.change" />
                  </button>
                </logic:equal>

                <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
                  <button type="button" value="<gsmsg:write key="cmn.register.copy2" />" class="baseBtn" onClick="setDateParam();buttonPush('copytouroku');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                    <gsmsg:write key="cmn.register.copy2" />
                  </button>
                </logic:equal>

                <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
                  <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('delete');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                </logic:equal>
                <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('back_to_menu');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:notEqual>
            </logic:equal>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper <%= rsvElementSizeClass %> mrl_auto">
      <div class="component_bothEnd">

        <div>
          <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
            <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
              <button type="button" value="<gsmsg:write key="cmn.for.repert" />" class="baseBtn" onclick="setDateParam();buttonPush('kurikaeshi');">
                <gsmsg:write key="cmn.for.repert" />
              </button>
            </logic:notEqual>
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <gsmsg:write key="cmn.pdf" />
            </button>
          </logic:equal>
        </div>

      </div>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <table class="table-left rsv110_table table-fixed table bgC_none">

        <colgroup class="w10" ></colgroup>
        <colgroup class="w10" ></colgroup>
        <colgroup class="w80 bgC_tableCell"></colgroup>

      <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv110_rsvInfo.jsp" />

        <%-- 登録者 --%>
        <tr>
          <th class="w20 no_w " colspan="2">
            <span>
              <gsmsg:write key="cmn.registant" />
            </span>
          </th>
          <td class="txt_l w80">
            <%-- 新規登録者 --%>
            <div class="w100 display_flex" >
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
                <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
                  <span class="verAlignMid no_w">
                    <gsmsg:write key="reserve.178" /><gsmsg:write key="cmn.colon"/>
                  </span><!--
               --></logic:notEqual><!--
           --></logic:notEqual><!--
           --><span class="verAlignMid no_w">
                <logic:equal name="rsv110Form" property="rsv110AddUsrJKbn" value="<%=String.valueOf(GSConst.JTKBN_TOROKU)%>">
                  <bean:define id="styleTorokusya" value="" />
                  <logic:equal name="rsv110Form" property="rsv110AddUsrUkoFlg" value="1">
                    <bean:define id="styleTorokusya" value="mukoUser" />
                  </logic:equal>
                  <logic:notEqual name="rsv110Form" property="rsv110Torokusya" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
                  <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
                  <a href="#!" onClick="return openUserInfoWindow(<bean:write name="rsv110Form" property="rsv110AuId" />);">
                  </logic:notEqual>
                  </logic:notEqual>
                    <span class="<%=styleTorokusya%> no_w"><bean:write name="rsv110Form" property="rsv110Torokusya" /></span>
                  <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
                  <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
                  </a>
                  </logic:notEqual>
                  </logic:notEqual>
                </logic:equal>
                <logic:equal name="rsv110Form" property="rsv110AddUsrJKbn" value="<%=String.valueOf(GSConst.JTKBN_DELETE)%>">
                  <del><bean:write name="rsv110Form" property="rsv110Torokusya" /></del>
                </logic:equal>
              </span><!--
           --><span class="verAlignMid no_w ml10">
                <bean:write name="rsv110Form" property="rsv110AddDate" />
              </span>
            </div>
            <%-- 最終更新者 --%>
            <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_SINKI)%>">
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_COPY_ADD)%>">
                <div class="w100 display_flex">
                  <span class="verAlignMid no_w">
                    <gsmsg:write key="reserve.179" /><gsmsg:write key="cmn.colon"/>
                  </span><!--
               --><span class="verAlignMid no_w">
                    <logic:equal name="rsv110Form" property="rsv110EditUsrJKbn" value="<%=String.valueOf(GSConst.JTKBN_TOROKU)%>">
                      <bean:define id="styleTorokusya" value="" />
                      <logic:equal name="rsv110Form" property="rsv110EditUsrUkoFlg" value="1">
                        <bean:define id="styleTorokusya" value="mukoUser" />
                      </logic:equal>
                      <a href="#!" onClick="return openUserInfoWindow(<bean:write name="rsv110Form" property="rsv110EuId" />);">
                        <span class="<%=styleTorokusya%> no_w"><bean:write name="rsv110Form" property="rsv110Koshinsya" /></span>
                      </a>
                    </logic:equal>
                    <logic:equal name="rsv110Form" property="rsv110EditUsrJKbn" value="<%=String.valueOf(GSConst.JTKBN_DELETE)%>">
                      <del><bean:write name="rsv110Form" property="rsv110Koshinsya" /></del>
                    </logic:equal>
                  </span><!--
               --><span class="verAlignMid no_w ml10">
                    <bean:write name="rsv110Form" property="rsv110EditDate" />
                  </span>
                </div>
              </logic:notEqual>
            </logic:notEqual>
          </td>
        </tr>

        <%-- 印刷 --%>
        <logic:equal name="rsv110Form" property="rsv110SisetuKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_CAR)%>">
          <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv110_print.jsp" />
        </logic:equal>

        <tr>
          <th colspan="2" class=" no_w">
            <span>
              <gsmsg:write key="reserve.72" />
            </span>
            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <span class="cl_fontWarn">
                  <gsmsg:write key="cmn.comments" />
                </span>
              </logic:notEqual>
            </logic:equal>
          </th>
          <td class="txt_l">
            <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
              <span>
                <bean:write name="rsv110Form" property="rsv110Mokuteki" />
              </span>
              <html:hidden name="rsv110Form" property="rsv110Mokuteki" />
            </logic:notEqual>

            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <html:text name="rsv110Form" property="rsv110Mokuteki" maxlength="50" styleClass="wp400" />
              </logic:notEqual>
              <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <span>
                  <bean:write name="rsv110Form" property="rsv110Mokuteki" />
                </span>
                <html:hidden name="rsv110Form" property="rsv110Mokuteki" />
              </logic:equal>
            </logic:equal>
          </td>
        </tr>

        <%-- 利用区分 --%>
        <logic:equal name="rsv110Form" property="rsv110SisetuKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_HEYA)%>">
          <tr>
            <th class=" w20 no_w" colspan="2">
              <span>
                <gsmsg:write key="reserve.use.kbn" />
              </span>
            </th>
            <td class="txt_l w80">
              <span>
                <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
                  <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_NOSET)%>">
                    <gsmsg:write key="reserve.use.kbn.noset" />
                  </logic:equal>
                  <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_KAIGI)%>">
                    <gsmsg:write key="reserve.use.kbn.meeting" />
                  </logic:equal>
                  <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_KENSYU)%>">
                    <gsmsg:write key="reserve.use.kbn.training" />
                  </logic:equal>
                  <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_OTHER)%>">
                    <gsmsg:write key="reserve.use.kbn.other" />
                  </logic:equal>
                </logic:notEqual>

                <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
                  <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                    <span class="verAlignMid">
                      <html:radio name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_NOSET)%>" styleId="rsyUkbnNoset">
                        <label for="rsyUkbnNoset" class="mr10">
                          <gsmsg:write key="reserve.use.kbn.noset" />
                        </label>
                      </html:radio>
                      <html:radio name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_KAIGI)%>" styleId="rsyUkbnKaigi">
                        <label for="rsyUkbnKaigi" class="mr10">
                          <gsmsg:write key="reserve.use.kbn.meeting" />
                        </label>
                      </html:radio>
                      <html:radio name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_KENSYU)%>" styleId="rsyUkbnKensyu">
                        <label for="rsyUkbnKensyu" class="mr10">
                          <gsmsg:write key="reserve.use.kbn.training" />
                        </label>
                      </html:radio>
                      <html:radio name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_OTHER)%>" styleId="rsyUkbnOther">
                        <label for="rsyUkbnOther">
                          <gsmsg:write key="reserve.use.kbn.other" />
                        </label>
                      </html:radio>
                    </span>
                  </logic:notEqual>
                  <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                    <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_NOSET)%>">
                      <gsmsg:write key="reserve.use.kbn.noset" />
                    </logic:equal>
                    <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_KAIGI)%>">
                      <gsmsg:write key="reserve.use.kbn.meeting" />
                    </logic:equal>
                    <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_KENSYU)%>">
                      <gsmsg:write key="reserve.use.kbn.training" />
                    </logic:equal>
                    <logic:equal name="rsv110Form" property="rsv110UseKbn" value="<%=String.valueOf(GSConstReserve.RSY_USE_KBN_OTHER)%>">
                      <gsmsg:write key="reserve.use.kbn.other" />
                    </logic:equal>
                  </logic:equal>
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:equal>

        <%
                          if (sisKbn == GSConstReserve.RSK_KBN_HEYA
                                  || sisKbn == GSConstReserve.RSK_KBN_CAR) {
                        %>
        <tr>
          <th class=" w20 no_w" colspan="2">
            <span>
              <gsmsg:write key="reserve.contact" />
            </span>
          </th>
          <td class="w80 txt_l">
            <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
              <span>
                <bean:write name="rsv110Form" property="rsv110Contact" />
              </span>
            </logic:notEqual>
            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <html:text name="rsv110Form" property="rsv110Contact" maxlength="20" styleClass="wp150" />
              </logic:notEqual>
              <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <span>
                  <bean:write name="rsv110Form" property="rsv110Contact" />
                </span>
              </logic:equal>
            </logic:equal>
          </td>
        </tr>
        <%
                          }
                        %>

        <logic:equal name="rsv110Form" property="rsv110SisetuKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_HEYA)%>">
          <tr>
            <th class=" w20 no_w" colspan="2">
              <span>
                <gsmsg:write key="reserve.guide" />
              </span>
            </th>
            <td class="w80 txt_l">
              <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
                <span>
                  <bean:write name="rsv110Form" property="rsv110Guide" />
                </span>
              </logic:notEqual>
              <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
                <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                  <html:text name="rsv110Form" property="rsv110Guide" styleClass="wp300" maxlength="50" />
                </logic:notEqual>
                <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                  <span>
                    <bean:write name="rsv110Form" property="rsv110Guide" />
                  </span>
                </logic:equal>
              </logic:equal>
            </td>
          </tr>

          <tr>
            <th class=" w20 no_w" colspan="2">
              <span>
                <gsmsg:write key="reserve.park.num" />
              </span>
            </th>
            <td class="w80 txt_l">
              <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
                <span>
                  <bean:write name="rsv110Form" property="rsv110ParkNum" />
                </span>
              </logic:notEqual>
              <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
                <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                  <html:text name="rsv110Form" property="rsv110ParkNum" maxlength="5" styleClass="txt_r wp150" />
                </logic:notEqual>
                <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                  <span>
                    <bean:write name="rsv110Form" property="rsv110ParkNum" />
                  </span>
                </logic:equal>
              </logic:equal>
            </td>
          </tr>

        </logic:equal>

        <logic:equal name="rsv110Form" property="rsv110SisetuKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_CAR)%>">
          <%-- 行先 --%>
          <tr>
            <th class=" w20 no_w" colspan="2">
              <span>
                <gsmsg:write key="reserve.dest" />
              </span>
            </th>
            <td class="w80 txt_l">
              <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
                <span>
                  <bean:write name="rsv110Form" property="rsv110Dest" />
                </span>
              </logic:notEqual>
              <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
                <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                  <html:text name="rsv110Form" property="rsv110Dest" maxlength="50" styleClass="wp350" />
                </logic:notEqual>
                <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                  <span>
                    <bean:write name="rsv110Form" property="rsv110Dest" />
                  </span>
                </logic:equal>
              </logic:equal>
            </td>
          </tr>
        </logic:equal>

        <html:hidden styleId="rsv110AmFrHour" property="rsv110AmFrHour" />
        <html:hidden styleId="rsv110AmFrMin" property="rsv110AmFrMin" />
        <html:hidden styleId="rsv110AmToHour" property="rsv110AmToHour" />
        <html:hidden styleId="rsv110AmToMin" property="rsv110AmToMin" />
        <html:hidden styleId="rsv110PmFrHour" property="rsv110PmFrHour" />
        <html:hidden styleId="rsv110PmFrMin" property="rsv110PmFrMin" />
        <html:hidden styleId="rsv110PmToHour" property="rsv110PmToHour" />
        <html:hidden styleId="rsv110PmToMin" property="rsv110PmToMin" />
        <html:hidden styleId="rsv110AllDayFrHour" property="rsv110AllDayFrHour" />
        <html:hidden styleId="rsv110AllDayFrMin" property="rsv110AllDayFrMin" />
        <html:hidden styleId="rsv110AllDayToHour" property="rsv110AllDayToHour" />
        <html:hidden styleId="rsv110AllDayToMin" property="rsv110AllDayToMin" />

        <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv110_sub1.jsp" />


        <%-- 担当部署/使用者名/人数 --%>
        <%
                          if (sisKbn == GSConstReserve.RSK_KBN_HEYA
                                  || sisKbn == GSConstReserve.RSK_KBN_CAR) {
                        %>
        <%
                          String headName = "";
                                jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(
                                    request);
                                String msgTanto = gsMsg.getMessage("reserve.use.name.1");
                                String msgUser = gsMsg.getMessage("reserve.use.name.2");
                        %>

        <logic:equal name="rsv110Form" property="rsv110SisetuKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_HEYA)%>">
          <%
                            headName = msgTanto;
                          %>
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110SisetuKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_CAR)%>">
          <%
                            headName = msgUser;
                          %>
        </logic:equal>

        <tr>
          <th colspan="2" class="w20">
            <span class="verAlignMid"><gsmsg:write key="reserve.busyo" />/</span><span class="verAlignMid"><%=headName%></span><span class="verAlignMid">/<gsmsg:write key="reserve.use.num" /></span>
          </th>
          <td class="w80 txt_l">
            <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">

              <span>
                <gsmsg:write key="reserve.busyo" /><gsmsg:write key="cmn.colon"/><bean:write name="rsv110Form" property="rsv110Busyo" />
                <br><%=headName%><gsmsg:write key="cmn.colon"/><bean:write name="rsv110Form" property="rsv110UseName" />
                <logic:notEmpty name="rsv110Form" property="rsv110UseNum">
                  <br>
                  <gsmsg:write key="reserve.rsv110.3" /><gsmsg:write key="cmn.colon"/><bean:write name="rsv110Form" property="rsv110UseNum" /><gsmsg:write key="cmn.persons" />
                </logic:notEmpty>
              </span>
            </logic:notEqual>

            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <div class="display_flex">
                  <div class="wp60  mr5">
                    <gsmsg:write key="reserve.busyo" />
                  </div>
                  <div class="">
                    <html:text name="rsv110Form" property="rsv110Busyo" maxlength="50" styleClass="wp150" />
                  </div>
                </div>
                <div class="display_flex mt5">
                  <div class="wp60  mr5">
                    <%=headName%>
                  </div>
                  <div class="">
                    <html:text name="rsv110Form" property="rsv110UseName" maxlength="62" styleClass="wp150" />
                  </div>
                </div>
                <div class="display_flex mt5">
                  <div class="wp60  mr5">
                    <gsmsg:write key="reserve.rsv110.3" />
                  </div>
                  <div class="display_inline">
                    <html:text name="rsv110Form" property="rsv110UseNum" maxlength="5" styleClass="txt_r wp60" /><gsmsg:write key="cmn.persons" />
                  </div>
                </div>
              </logic:notEqual>
              <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_POPUP)%>">
                <span>
                  <gsmsg:write key="reserve.busyo" /><gsmsg:write key="cmn.colon"/><bean:write name="rsv110Form" property="rsv110Busyo" />
                  <br><%=headName%><gsmsg:write key="cmn.colon"/>
                  <bean:write name="rsv110Form" property="rsv110UseName" />
                  <logic:notEmpty name="rsv110Form" property="rsv110UseNum">
                    <br><gsmsg:write key="reserve.rsv110.3" /><gsmsg:write key="cmn.colon"/>
                    <bean:write name="rsv110Form" property="rsv110UseNum" /><gsmsg:write key="cmn.persons" />
                  </logic:notEmpty>
                </span>
              </logic:equal>
            </logic:equal>
          </td>

        </tr>
        <%
          }
        %>

        <%
          if (editSchFlg) {
        %>
        <tr>
          <th class="no_w" colspan="2">
            <span>
              <gsmsg:write key="reserve.85" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:radio styleId="refOk" name="rsv110Form" property="rsv110ScdReflection" value="<%=String.valueOf(GSConstReserve.SCD_REFLECTION_OK)%>" onclick="rsvSchDisabled('rsv110SchUserUI');" />
              <label for="refOk" class="mr10">
                <gsmsg:write key="reserve.86" />
              </label>
              <html:radio styleId="refNo" name="rsv110Form" property="rsv110ScdReflection" value="<%=String.valueOf(GSConstReserve.SCD_REFLECTION_NO)%>" onclick="rsvSchDisabled('rsv110SchUserUI');" />
              <label for="refNo">
                <gsmsg:write key="reserve.87" />
              </label>
            </span>
          </td>
        </tr>
        <% } %>

        <%-- スケジュール同時登録 --%>
        <logic:equal name="rsv110Form" property="schedulePluginKbn" value="<%= String.valueOf(GSConst.PLUGIN_USE) %>">
          <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_POPUP) %>">
            <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
              <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv110_schedule.jsp" />
            </logic:equal>
          </logic:notEqual>
        </logic:equal>

        <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
          <tr>
            <th class="no_w" colspan="2">
              <span>
                <gsmsg:write key="schedule.18" />
              </span>
            </th>
            <td class="txt_l">
              <span>※
                <gsmsg:write key="schedule.35" />
              </span>
              <button type="button" value="<gsmsg:write key="schedule.17" />" class="baseBtn" onClick="setDateParam();openScheduleReserveWindowForReserve(<%=String.valueOf(Rsv310Form.POP_DSP_MODE_RSV110)%>);">
                <gsmsg:write key="schedule.17" />
              </button>
            </td>
          </tr>
        </logic:equal>
      </table>

      <div class="footerBtn_block">
      <bean:define id="strRsv110ProcMode" name="rsv110Form" property="rsv110ProcMode" type="java.lang.String" />
                 <%
                          if (strRsv110ProcMode != null
                                  && (strRsv110ProcMode.equals(GSConstReserve.PROC_MODE_EDIT)
                                      || strRsv110ProcMode.equals(GSConstReserve.PROC_MODE_POPUP))) {
                        %>
        <logic:equal name="rsv110Form" property="rsv110ApprBtnFlg" value="1">
          <button type="button" value="<gsmsg:write key="cmn.approval" />" id="syoninbtn" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="cmn.approval" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="cmn.approval" />">
            <gsmsg:write key="cmn.approval" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.rejection" />" id="kyakkabtn" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
            <gsmsg:write key="cmn.rejection" />
          </button>
        </logic:equal>

        <logic:equal name="rsv110Form" property="rsv110ApprBtnFlg" value="2">
          <button type="button" value="<gsmsg:write key="reserve.appr.st1" />" id="waitbtn" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_sashimodoshi.png" alt="<gsmsg:write key="reserve.appr.st1" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_sashimodoshi.png" alt="<gsmsg:write key="reserve.appr.st1" />">
            <gsmsg:write key="reserve.appr.st1" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.rejection" />" id="kyakkabtn" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="cmn.rejection" />">
            <gsmsg:write key="cmn.rejection" />
          </button>
        </logic:equal>

        <% } %>

      <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('back_to_menu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:notEqual>
      <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
        <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=GSConstReserve.PROC_MODE_POPUP%>">
          <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onclick="window.parent.callYokyakuWindowClose();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <gsmsg:write key="cmn.close" />
          </button>
        </logic:equal>
        <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=GSConstReserve.PROC_MODE_POPUP%>">

          <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <button type="button" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onclick="buttonPush('sisetu_yoyaku_kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              <gsmsg:write key="cmn.entry" />
            </button>
          </logic:notEqual>

          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <button type="button" value="<gsmsg:write key="cmn.change" />" class="baseBtn" onClick="buttonPush('sisetu_yoyaku_kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change"/>">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
              <gsmsg:write key="cmn.change" />
            </button>
          </logic:equal>

          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <button type="button" value="<gsmsg:write key="cmn.register.copy2" />" class="baseBtn" onClick="setDateParam();buttonPush('copytouroku');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
              <gsmsg:write key="cmn.register.copy2" />
            </button>
          </logic:equal>

          <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(GSConstReserve.PROC_MODE_EDIT)%>">
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('back_to_menu');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>
      </logic:equal>
    </div>
  </div>
  <bean:define id="reserveMokuteki" value="" />
  <logic:notEmpty name="rsv110Form" property="rsv110Mokuteki" >
    <bean:define id="reserveMokuteki" ><bean:write  name="rsv110Form" property="rsv110Mokuteki" /></bean:define>
  </logic:notEmpty>
  <div id="rsvApproval" title="<gsmsg:write key='reserve.rsv110.info.2'/>" class="display_n">
    <ul class="verAlignMid  mt20 p0">
     <li class="" >
       <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.info">
       <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.info">
     </li>
     <li class="txt_t pl10">
       <gsmsg:write key='reserve.rsv110.info.msg.2' arg0="<%= reserveMokuteki %>" />
     </li>
    </ul>
  </div>

  <div id="rsvcheck" title="<gsmsg:write key='reserve.rsv110.info'/>" class="display_n">
    <div class="verAlignMid mt20 p0">
      <img class="header_pluginImg-classic mr10" src="../main/images/classic/header_info.png" alt="cmn.info">
      <img class="header_pluginImg mr10" src="../common/images/original/icon_info_32.png" alt="cmn.info">

      <gsmsg:write key='reserve.rsv110.info.msg' arg0="<%=reserveMokuteki%>" />
    </div>
    <div class="ml40 mt5">
      <span class="verAlignMid">
        <input type="checkbox" id="kyakkaCheck" value="1" />
        <label for="kyakkaCheck" class="cl_fontWarn ml5">
          <gsmsg:write key="reserve.rsv110.appr.note1" />
        </label>
      </span>
    </div>
  </div>

   <div id="rsvWait" title="<gsmsg:write key='reserve.rsv110.info'/>" class="display_n">
    <ul class="verAlignMid  mt20 p0">
     <li class="" >
       <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.info">
       <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.info">
     </li>
     <li class=" txt_t pl10">
      <gsmsg:write key='reserve.rsv110.info.msg.3' arg0="<%= reserveMokuteki %>" />
     </li>
    </ul>
  </div>

    <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%= String.valueOf(GSConstReserve.PROC_MODE_POPUP) %>">
      <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
    </logic:notEqual>

  </html:form>
</body>
</html:html>
