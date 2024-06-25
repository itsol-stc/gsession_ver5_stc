<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
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
  <title>GROUPSESSION <gsmsg:write key="tcd.56" /></title>
</head>
<body>
<html:form action="/timecard/tcd210kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="tcdBackScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<input type="hidden" name="tcd190nendo" value="<bean:write name="tcd210knForm" property="tcd190nendo" />">
<input type="hidden" name="tcd190group" value="<bean:write name="tcd210knForm" property="tcd190group" />">
<input type="hidden" name="tcd190sortKey" value="<bean:write name="tcd210knForm" property="tcd190sortKey" />">
<input type="hidden" name="tcd190order" value="<bean:write name="tcd210knForm" property="tcd190order" />">
<input type="hidden" name="tcd190page" value="<bean:write name="tcd210knForm" property="tcd190page" />">
<html:hidden property="tcdDspFrom" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto w80">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.210" /><gsmsg:write key="cmn.import" /><gsmsg:write key="cmn.check" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn" onClick="buttonPush('tcd210knCommit');" value="<gsmsg:write key="cmn.final" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" onClick="buttonPush('tcd210knBack');" value="<gsmsg:write key="cmn.back" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto w80">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <logic:messagesNotPresent message="false">
    <div class="txt_l mt20"><gsmsg:write key="tcd.tcd210kn.01" /></div>
  </logic:messagesNotPresent>
  
  <table class="table-left mt0">
    <tr>
      <th class="w25 txt_l"><gsmsg:write key="cmn.capture.file.name" /></th>
      <td class="w75">
        <bean:write name="tcd210knForm" property="tcd210knFileName" />
      </td>
    </tr>
  </table>
  
  <table class="mt20 table-top">
    <tr>
      <th class="table_title-color w15"><gsmsg:write key="tcd.209" /></th>
      <th class="table_title-color w60"><gsmsg:write key="cmn.user.name" /></th>
      <th class="table_title-color w25"><gsmsg:write key="tcd.tcd210kn.02" /></th>
    </tr>
    <logic:notEmpty name="tcd210knForm" property="dspList">
      <logic:iterate id="dspMdl" name="tcd210knForm" property="dspList">
        <tr>
          <td><bean:write name="dspMdl" property="nendo" /></td>
          <td><bean:write name="dspMdl" property="userName" /></td>
          <td><bean:write name="dspMdl" property="yukyuDays" /><gsmsg:write key="cmn.day" /></td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" onClick="buttonPush('tcd210knCommit');" value="<gsmsg:write key="cmn.final" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" onClick="buttonPush('tcd210knBack');" value="<gsmsg:write key="cmn.back" />">
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
