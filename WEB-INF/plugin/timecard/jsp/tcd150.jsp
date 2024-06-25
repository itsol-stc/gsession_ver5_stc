<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-timeZoneChart.tld" prefix="timeZoneChart" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstTimecard" %>

<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd150.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.211" /></title>
</head>
<body>
<html:form action="/timecard/tcd150">
<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../timecard/images/classic/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
      <img class="header_pluginImg" src="../timecard/images/original/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
    </li>
    <li><gsmsg:write key="tcd.50" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.import" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" onClick="buttonPush('import');" value="<gsmsg:write key="cmn.import" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" onClick="buttonPush('back');" value="<gsmsg:write key="cmn.back" />">
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
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <logic:notEqual name="tcd150Form" property="usrKbn" value="<%= String.valueOf(GSConstTimecard.USER_KBN_NORMAL) %>">
      <tr>
        <th class="w25 txt_l"><gsmsg:write key="cmn.registerd" /></th>
        <td class="w75">
          <html:select property="tcd150SltGroup" styleClass="wp200" onchange="changeGroupCombo();">
            <html:optionsCollection name="tcd150Form" property="tcd150GpLabelList" value="value" label="label" />
          </html:select>
          <button type="button" class="iconBtn-border ml5" value="&nbsp;&nbsp;" onclick="openGroupWindow(this.form.tcd150SltGroup, 'tcd150SltGroup', '1');" id="tcd150GroupBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
          </button>
          <html:select property="tcd150SltUser" styleClass="wp200">
            <logic:iterate id="user" name="tcd150Form" property="tcd150UsrLabelList" type="UsrLabelValueBean">
              <html:option value="<%=user.getValue() %>" styleClass="<%=user.getCSSClassNameOption() %>"><bean:write name="user" property="label"/></html:option>
            </logic:iterate>
          </html:select>
        </td>
      </tr>
    </logic:notEqual>
    <tr>
      <th class="w25 txt_l"><gsmsg:write key="cmn.capture.file" /></th>
      <td clas="w75">
        <span class="fs_13">
          <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
          <% String csvFileMsg = "<a href=\"../timecard/tcd150.do?CMD=tcd150_sample\">" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>"; %>
          *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg %>" />
        </span>
        <attachmentFile:filearea
        mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
        pluginId="<%= GSConstTimecard.PLUGIN_ID_TIMECARD %>"
        tempDirId="tcd150" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" onClick="buttonPush('import');" value="<gsmsg:write key="cmn.import" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" onClick="buttonPush('back');" value="<gsmsg:write key="cmn.back" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>
