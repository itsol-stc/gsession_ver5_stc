<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<% String maxLengthContent = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_VALUE); %>
<% String maxLengthBiko = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_BIKO); %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<logic:equal name="ntp040Form" property="cmd" value="add">
<title>GROUPSESSION <gsmsg:write key="ntp.177" /></title>
</logic:equal>
<logic:equal name="ntp040Form" property="cmd" value="edit">
<title>GROUPSESSION <gsmsg:write key="ntp.178" /></title>
</logic:equal>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/selectionSearchText.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/reservepopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/htmlEscape.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>

<% String selectionEvent = ""; %>
<% boolean selectionFlg = false; %>
<% String valueFocusEvent = ""; %>
<% String bikoFocusEvent = ""; %>

<logic:equal name="ntp040Form" property="searchPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
  <% selectionFlg = true; %>
</logic:equal>
<% String closeScript = "windowClose();"; %>
<logic:equal name="ntp040Form" property="addressPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp040.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<script>
  $(function(){
      $('.js_ntp_labelArea').inFieldLabels();
      $('.fix_content').exFixed(); // for IE6
  });
</script>
<% closeScript += "companyWindowClose();"; %>
</logic:equal>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03" onload="addOpen();" onunload="<%= closeScript %>">

<html:form action="/nippou/ntp040">

<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp"/>

<input type="hidden" name="yearRangeMinFr" value="5">
<input type="hidden" name="yearRangeMaxFr" value="5">
<input type="hidden" name="yearRangeMinTo" value="5">
<input type="hidden" name="yearRangeMaxTo" value="5">
<html:hidden property="cmd" />
<html:hidden property="dspMod" />
<html:hidden property="listMod" />
<html:hidden property="ntp040InitFlg" />
<html:hidden property="ntp040CopyFlg" />
<html:hidden property="ntp040ScrollFlg" />
<html:hidden property="ntp040BgcolorInit" />
<html:hidden property="ntp040DspMoveFlg" />
<html:hidden property="ntp040AnkenUse" />
<html:hidden property="ntp040CompanyUse" />
<html:hidden property="ntp040AnkenCompanyUse" />
<html:hidden property="ntp040KtBriHhuUse" />
<html:hidden property="ntp040MikomidoUse" />
<html:hidden property="ntp040TmpFileUse" />
<html:hidden property="ntp040NextActionUse" />
<html:hidden property="ntp040AdrHistoryPageNum" />
<html:hidden property="ntp040AnkenHistoryPageNum" />
<html:hidden property="ntp040DefaultValue" />
<html:hidden property="ntp040DefaultValue2" />
<html:hidden property="ntp040MikomidoFlg" />
<html:hidden property="ntp040InitYear" />
<html:hidden property="ntp040InitMonth" />
<html:hidden property="ntp040InitDay" />
<html:hidden property="ntp040InitDate" />
<html:hidden property="ntp040schUrl" />
<html:hidden property="ntp040UsrName" />
<html:hidden property="ntp040UsrBinSid" />
<html:hidden property="ntp040UsrPctKbn" />
<html:hidden property="ntp040UsrUkoFlg" />
<html:hidden property="ntp040PrevNtpDate" />
<html:hidden property="ntp040TodayNtpDate" />
<html:hidden property="ntp040NextNtpDate" />
<html:hidden property="scheduleUseOk" />
<html:hidden property="projectUseOk" />
<html:hidden property="ntp010DspDate" />
<html:hidden property="ntp010SelectUsrSid" />
<html:hidden property="ntp010SelectUsrAreaSid" />
<html:hidden property="ntp010SelectUsrKbn" />
<html:hidden property="ntp010SelectDate" />
<html:hidden property="ntp010NipSid" />
<html:hidden property="ntp010DspGpSid" />
<html:hidden property="ntp010searchWord" />
<html:hidden property="ntp020SelectUsrSid" />
<html:hidden property="ntp030SelectUsrSid" />

<logic:notEmpty name="ntp040Form"  property="ntp040FileList">
<logic:iterate id="tempmdl" name="ntp040Form"  property="ntp040FileList">
<html:hidden name="tempmdl" property="binFileName" />
</logic:iterate>
</logic:notEmpty>

<input type="hidden" name="ntp040delCompanyId" value="">
<input type="hidden" name="ntp040delCompanyBaseId" value="">
<input type="hidden" name="hourDivision" value="<bean:write name="ntp040Form" property="ntp040HourDivision" />">

<html:hidden property="addressPluginKbn" />
<html:hidden property="searchPluginKbn" />

<ntp:search_hidden name="ntp040Form"/>

<bean:write name="ntp040Form" property="ntp040100SvSearchTarget" filter="false"/>
<bean:write name="ntp040Form" property="ntp040100SearchTarget" filter="false" />
<bean:write name="ntp040Form" property="ntp040100SvBgcolor" filter="false" />
<bean:write name="ntp040Form" property="ntp040100Bgcolor" filter="false" />
<bean:write name="ntp040Form" property="ntp040100SvMikomido" filter="false" />
<bean:write name="ntp040Form" property="ntp040100Mikomido" filter="false" />

<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp"/>

<input type="hidden" name="helpPrm" value="<bean:write name="ntp040Form" property="ntp010SelectUsrKbn" /><bean:write name="ntp040Form" property="cmd" />">

<!--　BODY -->
<bean:write name="ntp040Form" property="ntp040YearLavelStr" filter="false" />
<bean:write name="ntp040Form" property="ntp040MonthLavelStr" filter="false" />
<bean:write name="ntp040Form" property="ntp040DayLavelStr" filter="false" />
<bean:write name="ntp040Form" property="ntp040HourLavelStr" filter="false" />
<bean:write name="ntp040Form" property="ntp040MinuteLavelStr" filter="false" />
<bean:write name="ntp040Form" property="ntp040KtbunruiLavelStr" filter="false" />
<bean:write name="ntp040Form" property="ntp040KthouhouLavelStr" filter="false" />

<input type="hidden" id="frhourhide" value="<bean:write name="ntp040Form" property="ntp040DefFrHour" />">
<input type="hidden" id="frminhide" value="<bean:write name="ntp040Form" property="ntp040DefFrMin" />">
<input type="hidden" id="tohourhide" value="<bean:write name="ntp040Form" property="ntp040DefToHour" />">
<input type="hidden" id="tominhide" value="<bean:write name="ntp040Form" property="ntp040DefToMin" />">
<input type="hidden" id="ktbunruihide" value="<bean:write name="ntp040Form" property="ntp040Ktbunrui" />">
<input type="hidden" id="kthouhouhide" value="<bean:write name="ntp040Form" property="ntp040Kthouhou" />">

<input type="hidden" id="attachmentFileMultiMode" value="multi">

<bean:define id="frhourval" name="ntp040Form" property="ntp040FrHour" type="java.lang.String"/>
<bean:define id="frminval" name="ntp040Form" property="ntp040FrMin" type="java.lang.String"/>
<bean:define id="tohourval" name="ntp040Form" property="ntp040ToHour" type="java.lang.String"/>
<bean:define id="tominval" name="ntp040Form" property="ntp040ToMin" type="java.lang.String"/>

<!-- BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <logic:equal name="ntp040Form" property="cmd" value="add">
      <li class="pageTitle_subFont">
       <gsmsg:write key="ntp.177" />
      </li>
    </logic:equal>
    <logic:equal name="ntp040Form" property="cmd" value="edit">
      <li class="pageTitle_subFont">
       <gsmsg:write key="ntp.178" />
      </li>
    </logic:equal>
    <li>
      <logic:equal name="ntp040Form" property="cmd" value="add">
        <input type="hidden" name="CMD" value="040_ok">
        <button type="button" class="baseBtn js_ntpAddBtn" value="<gsmsg:write key="cmn.entry" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <gsmsg:write key="cmn.entry" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040_back', '<bean:write name="ntp040Form" property="ntp040BtnCmd" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:equal>
      <logic:equal name="ntp040Form" property="cmd" value="edit">
        <input type="hidden" name="CMD" value="040_ok">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.pdf" />" onClick="buttonPush('pdf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <gsmsg:write key="cmn.pdf" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backButtonPush('040_back', '<bean:write name="ntp040Form" property="ntp040BtnCmd" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:equal>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <%-- スケジュールが繰り返し登録の場合警告表示  --%>
  <logic:equal name="ntp040Form" property="ntp040ExTextDspFlg" value="true">
    <div class="txt_l">
      <gsmsg:write key="schedule.149" />
    </div>
  </logic:equal>
  <logic:equal name="ntp040Form" property="cmd" value="add">
    <table class="table-left w100">
      <tr>
        <th class="w20">
          <gsmsg:write key="schedule.4" />
        </th>
        <td class="w80">
          <logic:notEqual name="ntp040Form" property="ntp010SelectUsrKbn" value="0">
            <span id="lt">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </span>
          </logic:notEqual>
          <bean:define id="mukoUserClass" value=""/>
          <logic:equal value="1" name="ntp040Form" property="ntp040UsrUkoFlg">
            <bean:define id="mukoUserClass" value="mukoUser"/>
          </logic:equal>
          <span class="<%= mukoUserClass %>"><bean:write name="ntp040Form" property="ntp040UsrName" /></span>
        </td>
      </tr>
      <tr>
        <th class="w20">
          <gsmsg:write key="ntp.35" />
        </th>
        <td class="w80">
          <div class="verAlignMid w100">
            <html:text name="ntp040Form" property="ntp040FrDate" maxlength="10" styleId="selDate" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#selDate')[0], 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#selDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span>
              <a class="fw_b todayBtn original-display" onClick="return moveDay($('#selDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveDay($('#selDate')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w20">
          <gsmsg:write key="cmn.registant" />
        </th>
        <td class="w80">
          <logic:notEqual name="ntp040Form" property="ntp040AddUsrJkbn" value="9">
            <bean:write name="ntp040Form" property="ntp040AddUsrName" />
          </logic:notEqual>
          <logic:equal name="ntp040Form" property="ntp040AddUsrJkbn" value="9">
            <del><bean:write name="ntp040Form" property="ntp040AddUsrName" /></del>
          </logic:equal>
        </td>
      </tr>
    </table>
  </logic:equal>

  <logic:notEmpty name="ntp040Form" property="ntp040DspTargetMdlList">
    <logic:iterate id="trgDspMdl" name="ntp040Form" property="ntp040DspTargetMdlList" indexId="trgListIdx">
      <logic:notEmpty name="trgDspMdl" property="ntgList">
        <input type="hidden" id="hideYear" value="<bean:write name="trgDspMdl" property="year" />" />
        <input type="hidden" id="hideMonth" value="<bean:write name="trgDspMdl" property="month" />" />
        <input type="hidden" id="hideUsrSid" value="<bean:write name="trgDspMdl" property="usrSid" />" />
        <input type="hidden" id="hideNttSid" value="<bean:write name="trgDspMdl" property="nttSid" />" />
        <div id="trgSetArea">
          <div id="trgDataSetArea">
            <logic:equal name="ntp040Form" property="cmd" value="add">
              <table class="table-top w100 border_none bgC_none">
                <bean:define id="trgList" name="trgDspMdl" property="ntgList" />
                <bean:size id="listSize" name="trgList" />
                <tr class="bgC_none">
                  <logic:iterate id="ntgMdl" name="trgDspMdl" property="ntgList" indexId="trgIdx">
                    <logic:notEmpty name="ntgMdl" property="npgTargetName">
                      <th class="w25 txt_c bor_t1">
                        <bean:write name="ntgMdl" property="npgTargetName" />
                      </th>
                    </logic:notEmpty>
                    <logic:empty name="ntgMdl" property="npgTargetName">
                      <td class="w25 border_none"></td>
                    </logic:empty>
                  </logic:iterate>
                </tr>
                <tr class="bgC_none">
                  <logic:iterate id="ntgMdl" name="trgDspMdl" property="ntgList" indexId="trgIdx">
                    <logic:notEmpty name="ntgMdl" property="npgTargetName">
                      <logic:notEmpty name="ntgMdl" property="npgTargetName">
                        <td class="w25 txt_c bgC_body js_tdTarget" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                          <div>
                            <input class="wp100" maxlength="15" value="<bean:write name="ntgMdl" property="npgRecord" />" id="val_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />" type="text">
                            <wbr>/<span id="valTrg_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />"><bean:write name="ntgMdl" property="npgTarget" /></span>
                            <wbr><bean:write name="ntgMdl" property="npgTargetUnit" />
                          </div>
                          <button type="button" class="baseBtn js_resetTrgBtn" value="<gsmsg:write key="cmn.reset" />" id="resetTrg_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                            <gsmsg:write key="cmn.reset" />
                          </button>
                        </td>
                      </logic:notEmpty>
                      <logic:empty name="ntgMdl" property="npgTargetName">
                        <td class="w25 border_none"></td>
                      </logic:empty>
                    </logic:notEmpty>
                  </logic:iterate>
                </tr>
              </table>
            </logic:equal>
            <logic:equal name="ntp040Form" property="cmd" value="edit">
              <table class="table-top w100 border_none bgC_none">
                <bean:define id="trgList" name="trgDspMdl" property="ntgList" />
                <bean:size id="listSize" name="trgList" />
                <tr class="bgC_none">
                  <logic:iterate id="ntgMdl" name="trgDspMdl" property="ntgList" indexId="trgIdx">
                    <logic:notEmpty name="ntgMdl" property="npgTargetName">
                      <th class="w25 txt_c bor_t1">
                        <bean:write name="ntgMdl" property="npgTargetName" />
                      </th>
                    </logic:notEmpty>
                    <logic:empty name="ntgMdl" property="npgTargetName">
                      <td class="w25 border_none"></td>
                    </logic:empty>
                  </logic:iterate>
                </tr>
                <tr class="bgC_none">
                  <logic:iterate id="ntgMdl" name="trgDspMdl" property="ntgList" indexId="trgIdx">
                    <logic:notEmpty name="ntgMdl" property="npgTargetName">
                      <% String recordColor = ""; %>
                      <logic:equal name="ntgMdl" property="npgTargetKbn" value="1">
                        <% recordColor = "cl_fontWarn"; %>
                      </logic:equal>
                      <td class="txt_c js_tdTarget bgC_body" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                        <span class="trgBtnArea_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                          <span class="<%= recordColor %>" id="recordAreaText_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                            <bean:write name="ntgMdl" property="npgRecord" />
                          </span>
                        </span>
                        <span class="display_n recordArea_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                          <input class="wp100" maxlength="15" value="<bean:write name="ntgMdl" property="npgRecord" />" id="trgRecord_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />" type="text">
                        </span>
                        /<span id="valTrg_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />"><bean:write name="ntgMdl" property="npgTarget" /></span>
                        <bean:write name="ntgMdl" property="npgTargetUnit" />
                        <logic:equal name="ntp040Form" property="ntp040TargetAdmKbn" value="0">
                          <div class="trgBtnArea_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                            <button type="button" class="baseBtn js_changeTrgBtn" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                              <gsmsg:write key="cmn.change" />
                            </button>
                          </div>
                          <div class="display_n recordArea_<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                            <button type="button" class="baseBtn js_targetJakuteiBtn" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                              <gsmsg:write key="cmn.final" />
                            </button>
                            <button type="button" class="baseBtn js_targetCanselBtn" id="<bean:write name="trgDspMdl" property="year" />_<bean:write name="trgDspMdl" property="month" />_<bean:write name="trgDspMdl" property="usrSid" />_<bean:write name="ntgMdl" property="ntgSid" />">
                              <gsmsg:write key="cmn.cancel" />
                            </button>
                          </div>
                        </logic:equal>
                      </td>
                    </logic:notEmpty>
                    <logic:empty name="ntgMdl" property="npgTargetName">
                      <td class="w25 border_none"></td>
                    </logic:empty>
                  </logic:iterate>
                </tr>
              </table>
            </logic:equal>
          </div>
        </div>
      </logic:notEmpty>
    </logic:iterate>
  </logic:notEmpty>
  <logic:equal name="ntp040Form" property="cmd" value="add">
    <jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp040_add.jsp">
    <jsp:param value="<%= frhourval %>" name="frhourval"/>
    <jsp:param value="<%= frminval %>" name="frminval"/>
    <jsp:param value="<%= tohourval %>" name="tohourval"/>
    <jsp:param value="<%= tominval %>" name="tominval"/>
    <jsp:param value="<%= valueFocusEvent %>" name="valueFocusEvent"/>
    <jsp:param value="<%= maxLengthContent %>" name="maxLengthContent"/>
    <jsp:param value="<%= maxLengthBiko %>" name="maxLengthBiko"/>
    </jsp:include>
  </logic:equal>
  <logic:equal name="ntp040Form" property="cmd" value="edit">
    <div class="bgC_header2 p5 w100 verAlignMid bor1">
      <span class="no_w fw_b <bean:write name="ntp040Form" property="ntp040DspDateKbn" />"><bean:write name="ntp040Form" property="ntp040FrYear" />年<bean:write name="ntp040Form" property="ntp040FrMonth" />月<bean:write name="ntp040Form" property="ntp040FrDay" />日(<bean:write name="ntp040Form" property="ntp040DspDateKbnStr" />)</span>
      <div class="w100 txt_r">
        <button type="button" class="baseBtn js_ntpPrevBtn" id="<bean:write name="ntp040Form" property="ntp040PrevNtpSid" />">
          <gsmsg:write key="cmn.previous.day" />
        </button>
        <button type="button" class="baseBtn js_ntpTodayBtn" id="<bean:write name="ntp040Form" property="ntp040TodayNtpSid" />">
          <gsmsg:write key="cmn.today" />
        </button>
        <button type="button" class="baseBtn js_ntpNextBtn" id="<bean:write name="ntp040Form" property="ntp040NextNtpSid" />">
          <gsmsg:write key="cmn.nextday" />
        </button>
      </div>
    </div>
    <jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp040_edit.jsp">
      <jsp:param value="<%= maxLengthContent %>" name="maxLengthContent"/>
      <jsp:param value="<%= maxLengthBiko %>" name="maxLengthBiko"/>
    </jsp:include>
  </logic:equal>
  <div class="js_nippouData">

  </div>

  <logic:equal name="ntp040Form" property="cmd" value="add">
    <div class="w100 txt_c">
      <button type="button" class="baseBtn js_newAddBtn" value="<gsmsg:write key="cmn.entry" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="ntp.38" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="ntp.38" />">
        <gsmsg:write key="ntp.38" />
      </button>
    </div>
  </logic:equal>

  <logic:equal name="ntp040Form" property="cmd" value="edit">
    <logic:equal name="ntp040Form" property="authAddEditKbn" value="0">
      <div class="w100 txt_c">
        <button type="button" class="baseBtn js_newAddBtnInEdit mb10" value="<gsmsg:write key="cmn.entry" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="ntp.38" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="ntp.38" />">
          <gsmsg:write key="ntp.38" />
        </button>
      </div>
    </logic:equal>
    <div class="bgC_header2 p5 w100 verAlignMid bor1">
      <span class="no_w fw_b <bean:write name="ntp040Form" property="ntp040DspDateKbn" />"><bean:write name="ntp040Form" property="ntp040FrYear" />年<bean:write name="ntp040Form" property="ntp040FrMonth" />月<bean:write name="ntp040Form" property="ntp040FrDay" />日(<bean:write name="ntp040Form" property="ntp040DspDateKbnStr" />)</span>
      <div class="w100 txt_r">
        <button type="button" class="baseBtn js_ntpPrevBtn" id="<bean:write name="ntp040Form" property="ntp040PrevNtpSid" />">
          <gsmsg:write key="cmn.previous.day" />
        </button>
        <button type="button" class="baseBtn js_ntpTodayBtn" id="<bean:write name="ntp040Form" property="ntp040TodayNtpSid" />">
          <gsmsg:write key="cmn.today" />
        </button>
        <button type="button" class="baseBtn js_ntpNextBtn" id="<bean:write name="ntp040Form" property="ntp040NextNtpSid" />">
          <gsmsg:write key="cmn.nextday" />
        </button>
      </div>
    </div>
  </logic:equal>
  <div class="footerBtn_block mt20">
    <logic:equal name="ntp040Form" property="cmd" value="add">
      <button type="button" class="baseBtn js_ntpAddBtn" value="<gsmsg:write key="cmn.entry" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <gsmsg:write key="cmn.entry" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('040_back', '<bean:write name="ntp040Form" property="ntp040BtnCmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:equal name="ntp040Form" property="cmd" value="edit">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.pdf" />" onClick="buttonPush('pdf');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
        <gsmsg:write key="cmn.pdf" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backButtonPush('040_back', '<bean:write name="ntp040Form" property="ntp040BtnCmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
  </div>
</div>

<div id="footdiv"></div>
<jsp:include page="ntp040_popupdivs.jsp" />
<div id="mikomidoPop" title="見込み度基準" style="display:none;">
  <div class="hp300 ofy_s">
  <table class="table-left w100">
    <logic:notEmpty name="ntp040Form" property="ntp040MikomidoMsgList">
      <logic:iterate id="mmdMdl" name="ntp040Form" property="ntp040MikomidoMsgList">
        <tr>
          <th class="w20">
            <bean:write name="mmdMdl" property="nmmName" />
          </th>
          <td class="w80">
            <bean:write name="mmdMdl" property="nmmMsg" filter="false" />
          </td>
       </tr>
     </logic:iterate>
   </logic:notEmpty>
  </table>
  </div>
</div>
<div id="schDataPop" title="スケジュールデータ" style="display:none;">
  <div class="fw_b mt10 <bean:write name="ntp040Form" property="ntp040DspDateKbn" />">
    <bean:write name="ntp040Form" property="ntp040FrYear" />年<bean:write name="ntp040Form" property="ntp040FrMonth" />月<bean:write name="ntp040Form" property="ntp040FrDay" />日(<bean:write name="ntp040Form" property="ntp040DspDateKbnStr" />)
  </div>
  <div class="hp250 ofy_s bor1 borC_light">
  <table class="table-top w100 m0 border_none">
    <thead>
      <tr>
        <th class="w5 border_left_none">
        </th>
        <th class="w15 no_w">
          <gsmsg:write key="cmn.time" />
        </th>
        <th class="w80" colspan="2">
          <gsmsg:write key="cmn.title" />
        </th>
      </tr>
    </thead>
    <tbody id="schDataTrArea"></tbody>
  </table>
  </div>
</div>
<div id="prjDataPop" title="プロジェクトデータ" style="display:none;">
  <div class="fw_b mt10 <bean:write name="ntp040Form" property="ntp040DspDateKbn" />">
    <bean:write name="ntp040Form" property="ntp040FrYear" />年<bean:write name="ntp040Form" property="ntp040FrMonth" />月<bean:write name="ntp040Form" property="ntp040FrDay" />日(<bean:write name="ntp040Form" property="ntp040DspDateKbnStr" />)
  </div>
  <div class="hp250 ofy_s bor1 borC_light">
    <table class="table-top w100 m0 border_none">
      <thead>
        <tr>
          <th class="w5 border_left_none">
          </th>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.start" />
          </th>
          <th class="w80" colspan="2">
            <gsmsg:write key="cmn.title" />
          </th>
        </tr>
      </thead>
      <tbody id="prjDataTrArea"></tbody>
    </table>
  </div>
</div>
<div id="contDataPop" title="コンタクト履歴データ" style="display:none;">
  <div class="fw_b mt10 <bean:write name="ntp040Form" property="ntp040DspDateKbn" />">
    <bean:write name="ntp040Form" property="ntp040FrYear" />年<bean:write name="ntp040Form" property="ntp040FrMonth" />月<bean:write name="ntp040Form" property="ntp040FrDay" />日(<bean:write name="ntp040Form" property="ntp040DspDateKbnStr" />)
  </div>
  <div class="hp250 ofy_s bor1 borC_light">
    <table class="table-top w100 m0 border_none">
      <thead>
        <tr>
          <th class="w5 border_left_none">
          </th>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.time" />
          </th>
          <th class="w80" colspan="2">
            <gsmsg:write key="cmn.title" />
          </th>
        </tr>
      </thead>
      <tbody id="contDataTrArea"></tbody>
    </table>
  </div>
</div>

</html:form>
<iframe type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></iframe>

<% if (selectionFlg) { %>
<span id="tooltip_search" class="tooltip_search"></span>
<span id="damy"></span>
<% } %>

<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp"/>

</body>
</html:html>