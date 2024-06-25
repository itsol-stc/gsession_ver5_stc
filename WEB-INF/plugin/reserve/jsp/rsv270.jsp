<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [<gsmsg:write key="reserve.rsv260.1" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv270.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/reserve/rsv270">
    <input type="hidden" name="CMD" value="sisetu_toroku_kakunin">
    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050SortRadio" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

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
          <gsmsg:write key="reserve.output.group.facilities" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.export" />" class="baseBtn" onClick="buttonPush('export');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt=" <gsmsg:write key="cmn.export" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt=" <gsmsg:write key="cmn.export" />">
              <gsmsg:write key="cmn.export" />
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
      <div class="txt_l">
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>
      </div>
      <div class="txt_l">
        <gsmsg:write key="reserve.select.category.export" />
      </div>
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="reserve.47" />
          </th>
          <td class="w75">
            <html:select property="rsv270SelectedSisetuKbn" styleClass="select01">
              <html:optionsCollection name="rsv270Form" property="rsv270SisetuLabelList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('export');">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt=" <gsmsg:write key="cmn.export" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt=" <gsmsg:write key="cmn.export" />">
           <gsmsg:write key="cmn.export" />
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