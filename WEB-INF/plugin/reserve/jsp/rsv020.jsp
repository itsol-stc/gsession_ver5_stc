<%@page import="jp.groupsession.v2.rsv.rsv010.Rsv010Form"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/reserve" prefix="reserve"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" />[<gsmsg:write key="cmn.days2" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js" ></script>
<script src="../reserve/js/rsv020.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/sisetuPopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
<!--
  <logic:notEqual name="rsv020Form" property="rsv020Reload" value="0">
    var reloadinterval = <bean:write name="rsv020Form" property="rsv020Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
  </logic:notEqual>
-->
</script>

<link rel=stylesheet href='../reserve/css/reserve.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" href="../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

</head>

<body onload="init();window_create();" onunload="windowClose();calWindowClose();" class="m0 hyde">
  <html:form action="/reserve/rsv020">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsv020FromHour" />
    <html:hidden property="rsv020ToHour" />
    <html:hidden property="rsv020ColSpan" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsvSelectedYoyakuSid" />
    <html:hidden property="rsvSelectedDate" />
    <html:hidden property="rsv020ClearTargetKey" />
    <html:hidden property="rsv010LearnMoreFlg" />
    <html:hidden property="rsv010InitFlg" />
    <html:hidden property="rsv010SiborikomiFlg" />
    <html:hidden property="rsv010svSisetuKeyword" />
    <html:hidden property="rsv010svKeyWordkbn" />
    <html:hidden property="rsv010svSisetuKeywordSisan" />
    <html:hidden property="rsv010svSisetuKeywordSisetu" />
    <html:hidden property="rsv010svSisetuKeywordBiko" />
    <html:hidden property="rsv010svSisetuKeywordNo" />
    <html:hidden property="rsv010svSisetuKeywordIsbn" />
    <html:hidden property="rsv010svSisetuFree" />
    <html:hidden property="rsv010svSisetuFreeFromY" />
    <html:hidden property="rsv010svSisetuFreeFromMo" />
    <html:hidden property="rsv010svSisetuFreeFromD" />
    <html:hidden property="rsv010svSisetuFreeFromH" />
    <html:hidden property="rsv010svSisetuFreeFromMi" />
    <html:hidden property="rsv010svSisetuFreeToY" />
    <html:hidden property="rsv010svSisetuFreeToMo" />
    <html:hidden property="rsv010svSisetuFreeToD" />
    <html:hidden property="rsv010svSisetuFreeToH" />
    <html:hidden property="rsv010svSisetuFreeToMi" />
    <html:hidden property="rsv010svSisetuKbn" />
    <html:hidden property="rsv010svGrpNarrowDown" />
    <html:hidden property="rsv010svSisetuSmoky" />
    <html:hidden property="rsv010svSisetuChere" />
    <html:hidden property="rsv010svSisetuTakeout" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHiddenParam.jsp"%>

    <logic:notEmpty name="rsv020Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv020Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv020Form" property="rsvSelectedIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv020Form" property="rsvSelectedIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <span class="js_rsv_top_frame topFrame-fixed">
      <div class="pageTitle">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
            <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          </li>
          <li>
            <gsmsg:write key="cmn.reserve" />
          </li>
          <li class="pageTitle_subFont">
            <gsmsg:write key="cmn.days2" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.reload" />" class="baseBtn" onClick="buttonPush('reload');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                <gsmsg:write key="cmn.reload" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf_nik');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
                <gsmsg:write key="cmn.pdf" />
              </button>
              <button type="button" name="btn_sisetu_settei" class="baseBtn" value="<gsmsg:write key="reserve.3" />" onclick="buttonPush('ikkatu_yoyaku');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_syorui_2.png" alt="<gsmsg:write key="reserve.3" />">
                <img class="btn_originalImg-display" src="../reserve/images/original/icon_ikatu.png" alt="<gsmsg:write key="reserve.3" />">
                <gsmsg:write key="reserve.3" />
              </button>
              <logic:equal name="rsv020Form" property="rsvGroupEditFlg" value="true">
                <button type="button" name="btn_sisetu_settei" class="baseBtn" value="<gsmsg:write key="reserve.settings" />" onclick="buttonPush('sisetu_settei');">
                  <img class="btn_classicImg-display" src="../reserve/images/classic/icon_resv_settei.png" alt="<gsmsg:write key="reserve.settings" />">
                  <img class="btn_originalImg-display" src="../reserve/images/original/icon_resv_settei.png" alt="<gsmsg:write key="reserve.settings" />">
                  <gsmsg:write key="reserve.settings" />
                </button>
              </logic:equal>
            </div>
          </li>
        </ul>
      </div>

      <div class="wrapper">

        <bean:define id="thisForm" name="rsv020Form" type="Rsv010Form"></bean:define>
        <reserve:rsvlistHeadPane thisForm="<%=thisForm%>" rsv020BtnEv="buttonPush('nikkan');" rsv010BtnEv="buttonPush('syukan');" rsv100BtnEv="moveItiran('riyo_zyokyo_syokai');" useRsvFilter="true">
          <jsp:attribute name="moveDays">
            <div class="ml_auto"></div>
            <button type="button" class="webIconBtn" value="&nbsp;" onclick="buttonPush('zenzitu_ido');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="buttonPush('kyo');">
              <gsmsg:write key="cmn.today" />
            </button>
            <span>
              <a href="#!" class="fw_b todayBtn original-display" onClick="buttonPush('kyo');">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" value="&nbsp;" onclick="buttonPush('yokuzitu_ido')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <button type="button" value="Cal" onclick="resetCmd();wrtCalendarByBtn(this.form.rsvDspFrom, 'rsv010Btn')" class="iconBtn-border" , id="rsv010Btn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
              <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
            </button>
          </jsp:attribute>
        </reserve:rsvlistHeadPane>
      </div>
    </span>
    <reserve:rsvlistBodyPane thisForm="<%=thisForm %>">
      <reserve:rsvlistBody_rsv020 />
    </reserve:rsvlistBodyPane>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>