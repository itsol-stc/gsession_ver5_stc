<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-timeZoneChart.tld" prefix="timeZoneChart" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

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
  <title>GROUPSESSION <gsmsg:write key="tcd.211" /></title>
</head>
<body>
<html:form action="/timecard/tcd150kn">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="tcd150SltGroup" value="<bean:write name="tcd150knForm" property="tcd150SltGroup" />">
<input type="hidden" name="tcd150SltUser" value="<bean:write name="tcd150knForm" property="tcd150SltUser" />">
<input type="hidden" name="tcd150knSltUserName" value="<bean:write name="tcd150knForm" property="tcd150knSltUserName" />">
<input type="hidden" name="fileName" value="<bean:write name="tcd150knForm" property="fileName" />">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../timecard/images/classic/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
      <img class="header_pluginImg" src="../timecard/images/original/header_timecard.png" alt="<gsmsg:write key="tcd.50" />">
    </li>
    <li><gsmsg:write key="tcd.50" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="tcd.tcd150kn.01" />
    </li>
    <li>
      <button type="button" class="baseBtn" onClick="buttonPush('commit');" value="<gsmsg:write key="cmn.run" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
        <gsmsg:write key="cmn.run" />
      </button>
      <button type="button" class="baseBtn" onClick="buttonPush('back_to_import_input');" value="<gsmsg:write key="cmn.back" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <logic:messagesNotPresent message="false">
    <div class="txt_l fw_b">
      <span class="cl_fontWarn"><gsmsg:write key="cmn.capture.file.sure" /></span>
    </div>
  </logic:messagesNotPresent>
  <table class="table-left">
    <logic:notEqual name="tcd150knForm" property="usrKbn" value="0">
      <tr>
        <th class="w25 txt_l"><gsmsg:write key="cmn.registerd" /></th>
        <td class="w75">
          <logic:equal name="tcd150knForm" property="userUkoFLg" value="<%= String.valueOf(GSConst.YUKOMUKO_MUKO) %>">
            <span class="mukoUser"><bean:write name="tcd150knForm" property="tcd150knSltUserName" /></span>
          </logic:equal>
          <logic:notEqual name="tcd150knForm" property="userUkoFLg" value="<%= String.valueOf(GSConst.YUKOMUKO_MUKO) %>">
            <bean:write name="tcd150knForm" property="tcd150knSltUserName" />
          </logic:notEqual>
        </td>
      </tr>
    </logic:notEqual>
    <tr>
      <th class="w25 txt_l"><gsmsg:write key="cmn.capture.file" /></th>
      <td class="w75">
        <a href="../timecard/tcd150kn.do?CMD=download"><bean:write name="tcd150knForm" property="fileName" /></a>
      </td>
    </tr>
    <tr>
      <th class="w25 txt_l"><gsmsg:write key="cmn.capture.item.count" /></th>
      <td class="w75">
        <bean:write name="tcd150knForm" property="impDataCnt" /><gsmsg:write key="cmn.number" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" onClick="buttonPush('commit');" value="<gsmsg:write key="cmn.run" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
      <gsmsg:write key="cmn.run" />
    </button>
    <button type="button" class="baseBtn" onClick="buttonPush('back_to_import_input');" value="<gsmsg:write key="cmn.back" />">
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
