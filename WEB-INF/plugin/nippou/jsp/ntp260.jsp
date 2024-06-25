<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String maxLengthSyosai = String.valueOf(1000);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp260.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/nippou/ntp260">

<input type="hidden" name="CMD" value="">
<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.147" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
    <tr>
      <th class="w40">
        <gsmsg:write key="ntp.92" />
      </th>
      <td class="w60">
        <bean:write name="ntp260Form" property="ntp260TemplateName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.148" />
      </th>
      <td>
        <logic:notEmpty name="ntp260Form" property="ntp260ItemList">
          <logic:iterate id="itmMdl" name="ntp260Form" property="ntp260ItemList">
            <bean:write name="itmMdl" /></br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.12" />
      </th>
      <td>
        <logic:notEmpty name="ntp260Form" property="ntp260TargetList">
          <logic:iterate id="trgMdl" name="ntp260Form" property="ntp260TargetList">
            <bean:write name="trgMdl" property="ntgName" /></br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.149" />
      </th>
      <td>
        <logic:notEmpty name="ntp260Form" property="ntp260GpUsrList">
          <logic:iterate id="memMdl" name="ntp260Form" property="ntp260GpUsrList" type="jp.groupsession.v2.ntp.model.NtpLabelValueModel">
            <span class="<%=memMdl.getCSSClassNameNormal()%>"><bean:write name="memMdl" property="label" /></span></br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th><gsmsg:write key="cmn.content" /></th>
      <td>
        <bean:write name="ntp260Form" property="ntp260TemplateDetail" filter="false" />
      </td>
    </tr>
  </table>

  <div class="txt_c">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>

</html:form>


</body>
</html:html>