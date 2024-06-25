<%@page import="jp.groupsession.v2.rsv.rsv010.Rsv010Form"%>
<%@page import="jp.groupsession.v2.zsk.GSConstZaiseki"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleReadOnly.tld" prefix="dailySchedule"%>
<%@ taglib uri="/WEB-INF/ctag-dailyReserveReadOnly.tld" prefix="dailyReserve"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/reserve" prefix="reserve"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="schedule.sch120.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../reserve/js/rsv310.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="rsv310Form" property="rsv310Reload" value="0">
  var reloadinterval = <bean:write name="rsv310Form" property="rsv310Reload" />;
  setTimeout("buttonPush('reload')", reloadinterval);
  </logic:notEqual>
  -->
</script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

</head>

<body onload="window_create();" onunload="windowClose();calWindowClose();" onkeydown="return keyPress(event.keyCode);" class="m0">
  <html:form action="/reserve/rsv310">
    <input type="hidden" name="CMD" value="">

    <html:hidden property="cmd" />
    <html:hidden property="rsv310InitFlg" />

    <html:hidden property="popDspMode" />
    <input type="hidden" name="rsvSelectedSisetuSid" value="<bean:write name='rsv310Form' property='rsvSelectedSisetuSid'/>">


    <html:hidden property="rsv310DspDate" />

    <html:hidden property="rsv310MoveMode" />
    <html:hidden property="rsv310FromHour" />

    <logic:notEmpty name="rsv310Form" property="sv_users" scope="request">
      <logic:iterate id="svuBean" name="rsv310Form" property="sv_users" scope="request">
        <input type="hidden" name="sv_users" value="<bean:write name="svuBean" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv310Form" property="users_l" scope="request">
      <logic:iterate id="ulBean" name="rsv310Form" property="users_l" scope="request">
        <input type="hidden" name="users_l" value="<bean:write name="ulBean" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv310Form" property="rsv111SvUsers" scope="request">
      <logic:iterate id="rsuBean" name="rsv310Form" property="rsv111SvUsers" scope="request">
        <input type="hidden" name="rsv111SvUsers" value="<bean:write name="rsuBean" />">
      </logic:iterate>
    </logic:notEmpty>

    <input type="hidden" name="rsv110SchKbn" value="<bean:write name='rsv310Form' property='rsv110SchKbn'/>">
    <input type="hidden" name="rsv110SchGroupSid" value="<bean:write name='rsv310Form' property='rsv110SchGroupSid'/>">
    <input type="hidden" name="rsv111SchKbn" value="<bean:write name='rsv310Form' property='rsv111SchKbn'/>">
    <input type="hidden" name="rsv111SchGroupSid" value="<bean:write name='rsv310Form' property='rsv111SchGroupSid'/>">
    <input type="hidden" name="rsv210SchKbn" value="<bean:write name='rsv310Form' property='rsv210SchKbn'/>">
    <input type="hidden" name="rsv210SchGroupSid" value="<bean:write name='rsv310Form' property='rsv210SchGroupSid'/>">

    <logic:notEmpty name="rsv310Form" property="svReserveUsers" scope="request">
      <logic:iterate id="svrBean" name="rsv310Form" property="svReserveUsers" scope="request">
        <input type="hidden" name="svReserveUsers" value="<bean:write name="svrBean" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv310Form" property="reserve_l" scope="request">
      <logic:iterate id="rlBean" name="rsv310Form" property="reserve_l" scope="request">
        <input type="hidden" name="reserve_l" value="<bean:write name="rlBean" />">
      </logic:iterate>
    </logic:notEmpty>


    <logic:notEmpty name="rsv310Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv310Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>


    <div class="js_rsv_top_frame topFrame-fixed">
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
            <gsmsg:write key="schedule.sch120.4" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('reload')">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
                <gsmsg:write key="cmn.reload" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.close();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                <gsmsg:write key="cmn.close" />
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper">
        <bean:define id="thisForm" name="rsv310Form" type="Rsv010Form"></bean:define>
        <reserve:rsvlistHeadPane thisForm="<%=thisForm%>" useListChangeBtn="false" >
          <jsp:attribute name="selectSisetu">
            <logic:equal name="rsv310Form" property="zaisekiUseOk" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PLUGIN_USE)%>">
              <div class="verAlignMid">
                <span class="cal_colHeader-zaiseki borC_light bor1 p5 mr5">
                  <gsmsg:write key="cmn.zaiseki" />
                </span>
                <span class="cal_colHeader-huzai borC_light bor1 p5 mr5">
                  <gsmsg:write key="cmn.absence" />
                </span>
                <span class="cal_colHeader-sonota borC_light bor1 p5">
                  <gsmsg:write key="cmn.other" />
                </span>
              </div>
            </logic:equal>
          </jsp:attribute>
          <jsp:attribute name="moveDays">
            <span class="mrl_auto"></span>
            <button type="button" class="webIconBtn" onClick="buttonPush('move_ld');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="buttonPush('today');">
              <gsmsg:write key="cmn.today" />
            </button>
            <span class="original-display">
              <a href="#!" class="fw_b todayBtn " onClick="buttonPush('today');">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="buttonPush('move_rd');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <button type="button" class="iconBtn-border" value="Cal" onClick="wrtCalendarByBtn(this.form.rsv310DspDate, 'rsv310')" id="rsv310">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
              <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
            </button>
          </jsp:attribute>
          <jsp:attribute name="searchPane">
            <table class="table-top cal_table2 w100 mb0">
              <tr>
                <td class="w100 table_title-color txt_l">
                  <span><bean:write name="rsv310Form" property="rsv310StrDate" scope="request" /></span>
                  <span><bean:write name="rsv310Form" property="rsvDispRokuyou" scope="request" /></span>
                </td>
              </tr>
            </table>
          </jsp:attribute>

        </reserve:rsvlistHeadPane>
      </div>
    </div>
    <reserve:rsvlistBodyPane thisForm="<%=thisForm %>">
      <reserve:rsvlistBody_rsv310 />
    </reserve:rsvlistBodyPane>



    <table class="w100">
      <tr>
      <td class="wrapper_space">
        <div class="wrapper">

        </div>
      </td>
      </tr>
    </table>
  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>