<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [<gsmsg:write key="reserve.rsv260.1" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../reserve/js/rsv260.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body class="body_03" onload="bodyLoad();" onunload="windowClose();">
  <html:form action="/reserve/rsv260">
    <input type="hidden" name="CMD" value="sisetu_toroku_kakunin">
    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050SortRadio" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv260Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv260Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv260Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv260Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <input type="hidden" name="helpPrm" value="">

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
          <gsmsg:write key="reserve.rsv260.1" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('move_import');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_group_settei');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left" >
        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.47" />
            </span>
          </th>
          <td class="txt_l">
            <html:select property="rsv260SelectedSisetuKbn" styleClass="select01" onchange="changeShisetsuCombo();">
              <html:optionsCollection name="rsv260Form" property="rsv260SisetuLabelList" value="value" label="label" />
            </html:select>
          </td>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="reserve.110" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <span class="fs_13">
              <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
              <%
              String csvFileMsg = "";
              %>
              <logic:equal name="rsv260Form" property="rsv260RskSid" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSK_KBN_HEYA)%>">
                <%
                csvFileMsg = "<a href=\"../reserve/rsv260.do?CMD=rsv260_sample&sample=1&kbn=1\">【"
                            + gsMsg.getMessage(request, "reserve.112") + "】" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>";
                %>
                *<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" />
              </logic:equal>
              <logic:equal name="rsv260Form" property="rsv260RskSid" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSK_KBN_BUPPIN)%>">
                <%
                csvFileMsg = "<a href=\"../reserve/rsv260.do?CMD=rsv260_sample&sample=1&kbn=2\">【"
                            + gsMsg.getMessage(request, "reserve.113") + "】" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>";
                %>
                *<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" />
              </logic:equal>
              <logic:equal name="rsv260Form" property="rsv260RskSid" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSK_KBN_CAR)%>">
                <%
                csvFileMsg = "<a href=\"../reserve/rsv260.do?CMD=rsv260_sample&sample=1&kbn=3\">【"
                            + gsMsg.getMessage(request, "reserve.114") + "】" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>";
                %>
                *<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" />
              </logic:equal>
              <logic:equal name="rsv260Form" property="rsv260RskSid" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSK_KBN_BOOK)%>">
                <%
                csvFileMsg = "<a href=\"../reserve/rsv260.do?CMD=rsv260_sample&sample=1&kbn=4\">【"
                            + gsMsg.getMessage(request, "reserve.115") + "】" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>";
                %>
                *<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" />
              </logic:equal>
              <logic:equal name="rsv260Form" property="rsv260RskSid" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSK_KBN_OTHER)%>">
                <%
                csvFileMsg = "<a href=\"../reserve/rsv260.do?CMD=rsv260_sample&sample=1&kbn=5\">【"
                            + gsMsg.getMessage(request, "cmn.other") + "】" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>";
                %>
                *<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" />
              </logic:equal>
            </span>
            <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
            pluginId="<%= GSConstReserve.PLUGIN_ID_RESERVE %>"
            tempDirId="rsv260" />
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.group.admin" />
            </span>
          </th>
          <td class="w75">
            <div>
              <div>
                <gsmsg:write key="reserve.49" />
              </div>
              <bean:define id="admOk" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_ADM_KBN_OK)%>" />
              <bean:define id="admNo" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_ADM_KBN_NO)%>" />
              <span class="verAlignMid mr10">
                <html:radio name="rsv260Form" styleId="doSet" property="rsv260GrpAdmKbn" value="<%=admOk%>" onclick="admChange(0);" />
                  <label for="doSet">
                    <gsmsg:write key="reserve.50" />
                  </label>
              </span><!--
           --><span class="verAlignMid">
                <html:radio name="rsv260Form" styleId="notSet" property="rsv260GrpAdmKbn" value="<%=admNo%>" onclick="admChange(1);" />
                <label for="notSet">
                  <gsmsg:write key="reserve.51" />
                </label>
              </span>
            </div>

            <ui:usrgrpselector name="rsv260Form" property="saveUserUI" styleClass="hp215" />
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.overwrite" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:checkbox name="rsv260Form" property="rsv260updateFlg" value="1" styleId="updateFlg" />
              <label for="updateFlg" class="text_base">
                <gsmsg:write key="reserve.125" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.126" />
            </span>
          </th>
          <td class="txt_l">
            <span class="verAlignMid">
              <html:checkbox name="rsv260Form" property="rsv260createGrpFlg" value="1" styleId="createGrpFlg" />
              <label for="createGrpFlg" class="text_base">
                <gsmsg:write key="reserve.127" />
              </label>
            </span>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('move_import');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_group_settei');">
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