<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
int dspOn = jp.groupsession.v2.cmn.GSConstReserve.RSV_OVERTIME_DSP_ON;
%>
<%
int dspOff = jp.groupsession.v2.cmn.GSConstReserve.RSV_OVERTIME_DSP_OFF;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.setting.main.view" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../reserve/js/rsv240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>

<link rel=stylesheet href='../reserve/css/reserve.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body class="body_03" onload="lmtEnableDisable();">
  <html:form action="/reserve/rsv240">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv240Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv240Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="rsv240InitFlg" />

    <logic:notEmpty name="rsv240Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv240Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.preferences2" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.setting.main.view" />
        </li>
        <li>
          <div>
            <button type="button" value=" <gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('rsv240ok');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv240back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <div class="txt_l mb5">
        <span>
          <gsmsg:write key="reserve.rsv240.2" />
        </span>
      </div>

      <div id="lmtinput">
        <span class="verAlignMid mb5">
          <span class="cl_fontWarn"><gsmsg:write key="reserve.show.time.passed" /></span>
          <html:radio styleClass="ml5" name="rsv240Form" property="rsv240overTimeDspKbn" value="<%= String.valueOf(dspOn) %>" styleId="rsv240overTimeDspKbn0" />
          <label for="rsv240overTimeDspKbn0">
              <gsmsg:write key="reserve.show.ok" />
          </label>
          <html:radio styleClass="ml10" name="rsv240Form" property="rsv240overTimeDspKbn" value="<%= String.valueOf(dspOff) %>" styleId="rsv240overTimeDspKbn1" />
          <label for="rsv240overTimeDspKbn1">
              <gsmsg:write key="reserve.show.no" />
          </label>
        </span>
      </div>

      <table class="table-top mt0" >
        <tr>
          <th class="no_w js_tableTopCheck-header js_tableTopCheck cursor_p">
            <html:checkbox name="rsv240Form" property="rsv240AllCheck" value="1" onclick="changeChk();lmtEnableDisable();" />
          </th>
          <th class="txt_c w100">
            <span>
              <gsmsg:write key="cmn.group.name" />
            </span>
          </th>
        </tr>

        <bean:define id="mod" value="0" />
        <logic:notEmpty name="rsv240Form" property="rsv240DspList" scope="request">
          <logic:iterate id="grpMdl" name="rsv240Form" property="rsv240DspList" scope="request" indexId="idx">
            <tr class="js_listHover cursor_p">
              <bean:define id="index" value="<%= String.valueOf(((Integer) idx).intValue()) %>" />
              <% String label_id = "label_" + idx.toString();  %>
              <td class="no_w js_radio " >
                <html:multibox name="rsv240Form" property="rsv240RsgSids"  styleId="<%= label_id.toString() %>"  styleClass="js_check">
                  <bean:write name="grpMdl" property="rsgSid" />
                </html:multibox>
              </td>
              <td class="txt_l js_radio">
                  <bean:write name="grpMdl" property="rsgName" />
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
      <div class="footerBtn_block">
        <button type="button" value=" <gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('rsv240ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv240back');">
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