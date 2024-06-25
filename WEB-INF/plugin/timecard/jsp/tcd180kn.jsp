<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-timeZoneChart.tld" prefix="timeZoneChart" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstTimecard" %>

<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <gsjsmsg:js filename="gsjsmsg.js"/>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'> 
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../timecard/js/tcd180.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.47" /></title>
</head>
<body>
<html:form action="/timecard/tcd180kn">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<html:hidden property="fileName"/>
<html:hidden property="tcd180Use"/>

<logic:notEmpty name="tcd180knForm" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd180knForm" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd180kn.01" /></li>
    <li>
      <button type="button" class="baseBtn" onClick="buttonPush('commit'); loadingData();" value="<gsmsg:write key="cmn.run" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
        <gsmsg:write key="cmn.run" />
      </button>
      <button type="button" class="baseBtn" onClick="buttonPush('tcd180knBack');" value="<gsmsg:write key="cmn.back" />">
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
    <logic:notEqual name="tcd180knForm" property="tcd180Use" value="0">
      <div class="txt_l fw_b">
        <span class="cl_fontWarn">
            <gsmsg:write key="tcd.tcd180.10" />
            <div>
              <gsmsg:write key="tcd.tcd180kn.02" />
            </div>
        </span>
      </div>
    </logic:notEqual>
  </logic:messagesNotPresent>
  <table class="table-left">
    <logic:equal name="tcd180knForm" property="tcd180Use" value="0">
      <tr>
        <th class="w25 txt_l"><gsmsg:write key="tcd.tcd180.02" /></th>
        <td class="w75"><gsmsg:write key="tcd.tcd180.11" /></td>
      </tr>
    </logic:equal>
    <logic:notEqual name="tcd180knForm" property="tcd180Use" value="0">
      <tr>
        <th class="w25 txt_l"><gsmsg:write key="tcd.tcd180.02" /></th>
        <td class="w75"><gsmsg:write key="tcd.tcd180.12" /></td>
      </tr>
      <tr>
        <th class="w25 txt_l"><gsmsg:write key="tcd.tcd180.04" /></th>
        <td class="w75">
          <a href="../timecard/tcd180kn.do?CMD=download"><bean:write name="tcd180knForm" property="fileName" /></a>
        </td>
      </tr>
    </logic:notEqual>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" onClick="buttonPush('commit'); loadingData();" value="<gsmsg:write key="cmn.run" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
      <gsmsg:write key="cmn.run" />
    </button>
    <button type="button" class="baseBtn" onClick="buttonPush('tcd180knBack');" value="<gsmsg:write key="cmn.back" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<div class="display_none">
  <div id="loading_pop" title="">
    <table class="w100 h100">
      <tr>
        <td class="txt_m txt_c">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
          <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
        </td>
      </tr>
    </table>
  </div>
</div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>