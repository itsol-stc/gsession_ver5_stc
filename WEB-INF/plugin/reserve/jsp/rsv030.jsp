<%@page import="jp.groupsession.v2.cmn.GSConstReserve"%>
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
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" />[<gsmsg:write key="cmn.monthly" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../reserve/js/rsv030.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/sisetuPopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript">
<!--
  <logic:notEqual name="rsv030Form" property="rsv030Reload" value="0">
    var reloadinterval = <bean:write name="rsv030Form" property="rsv030Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
  </logic:notEqual>
-->
</script>

<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

</head>


<body onload="init();window_create();" onunload="windowClose();calWindowClose();" class="m0 hyde">
  <div id="brank_flash" class="bgC_Body"></div>
  <html:form action="/reserve/rsv030">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedYoyakuSid" />
    <html:hidden property="rsvSelectedDate" />
    <html:hidden property="rsv030ClearTargetKey" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv030Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv030Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv030Form" property="rsvSelectedIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv030Form" property="rsvSelectedIkkatuTorokuKey" scope="request">
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
            <gsmsg:write key="cmn.monthly" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.reload" />" class="baseBtn" onClick="buttonPush('reload');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                <gsmsg:write key="cmn.reload" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf_gek');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
                <gsmsg:write key="cmn.pdf" />
              </button>
              <button type="button" name="btn_sisetu_settei" class="baseBtn" value="<gsmsg:write key="reserve.3" />" onclick="buttonPush('ikkatu_yoyaku');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_syorui_2.png" alt="<gsmsg:write key="reserve.3" />">
                <img class="btn_originalImg-display" src="../reserve/images/original/icon_ikatu.png" alt="<gsmsg:write key="reserve.3" />">
                <gsmsg:write key="reserve.3" />
              </button>
              <logic:equal name="rsv030Form" property="rsvGroupEditFlg" value="true">
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

      <bean:define id="thisForm" name="rsv030Form" type="Rsv010Form"></bean:define>
      <div class="wrapper">
        <reserve:rsvlistHeadPane thisForm="<%=thisForm%>" rsv020BtnEv="buttonPush('nikkan');" rsv010BtnEv="buttonPush('syukan');" rsv100BtnEv="moveItiran('riyo_zyokyo_syokai');" useRsvFilter="false">
          <jsp:attribute name="selectSisetu">
            <div class="display_flex txt_l w100">
              <div class="txt_m no_w rsvListHead_nogrow rsvListHead_noshrink ">
                <div class="verAlignMid hp30">
                  <gsmsg:write key="cmn.group" />：
                </div><br />
                <div class="verAlignMid hp30">
                  <gsmsg:write key="cmn.facility" />:
                </div>
              </div>
              <div class="txt_m wp300">
                <div class="verAlignMid hp30">
                  <html:select property="rsvSelectedGrpSid" styleClass="w100" onchange="changeGroupCombo();">
                    <html:optionsCollection name="rsv030Form" property="rsvGrpLabelList" value="value" label="label" />
                  </html:select>
                </div><br />
                <div class="verAlignMid hp30">
                  <html:select property="rsvSelectedSisetuSid" styleClass="" onchange="changeGroupCombo();">
                    <html:optionsCollection name="rsv030Form" property="rsvSisetuLabelList" value="value" label="label" />
                  </html:select>
                </div>
              </div>
            </div>
          </jsp:attribute>

          <jsp:attribute name="moveDays">
            <span class="mrl_auto"></span>
            <button type="button" class="webIconBtn" value="&nbsp;" onclick="buttonPush('zengetu_ido');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth" />" onClick="buttonPush('kongetu');">
              <gsmsg:write key="cmn.thismonth" />
            </button>
            <span>
              <a href="#!" class="fw_b todayBtn original-display" onClick="buttonPush('kongetu');">
                <gsmsg:write key="cmn.thismonth" />
              </a>
            </span>
            <button type="button" class="webIconBtn" value="&nbsp;" onclick="buttonPush('yokutuki_ido')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <button type="button" value="Cal" onclick="resetCmd();wrtCalendar(this.form.rsvDspFrom)" class="iconBtn-border" , id="rsv010Btn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
              <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
            </button>
          </jsp:attribute>
        </reserve:rsvlistHeadPane>
      </div>
    </span>

    <reserve:rsvlistBodyPane thisForm="<%=thisForm %>">
      <reserve:rsvlistBody_rsv030  />
    </reserve:rsvlistBodyPane>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
    </html:form>
</body>
</html:html>