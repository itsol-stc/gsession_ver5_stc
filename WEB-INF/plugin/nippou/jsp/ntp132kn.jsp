<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<html:form action="/nippou/ntp132kn">

<input type="hidden" name="CMD" value="">
<html:hidden property="ntp132CatSid" />
<%@ include file="/WEB-INF/plugin/nippou/jsp/ntp130_hiddenParams.jsp" %>
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.58" /><gsmsg:write key="cmn.import" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush2('doImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('backNtp132kn');">
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
    <html:errors/>
  </logic:messagesPresent>
</div>
<div class="txt_l cl_fontWarn">
  <gsmsg:write key="cmn.capture.file.sure" />
</div>

  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.capture.file" />
      </th>
      <td class="w75">
        <bean:write name="ntp132knForm" property="ntp132knFileName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.category" />
      </th>
      <td>
        <bean:write name="ntp132knForm" property="ntp132knCategoryName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.164" />
      </th>
      <td>
        <logic:notEmpty name="ntp132knForm" property="ntp132knImpList" scope="request">
          <logic:iterate id="shohinModel" name="ntp132knForm" property="ntp132knImpList" scope="request">
            <bean:write name="shohinModel" property="nhnCode" />&nbsp;&nbsp;<bean:write name="shohinModel" property="nhnName" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <logic:notEmpty name="ntp132knForm" property="ntp132knImpList" scope="request">
          <bean:size id="count" name="ntp132knForm" property="ntp132knImpList" scope="request" />
          <bean:write name="count" /><gsmsg:write key="cmn.number" /><br>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
 
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush2('doImp');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('backNtp132kn');">
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