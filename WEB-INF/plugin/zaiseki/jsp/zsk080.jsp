<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zsk080.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
</head>

<body>
<html:form action="/zaiseki/zsk080">
<input type="hidden" name="CMD">
<html:hidden name="zsk080Form" property="backScreen" />
<html:hidden name="zsk080Form" property="selectZifSid" />
<html:hidden name="zsk080Form" property="uioStatus" />
<html:hidden name="zsk080Form" property="uioStatusBiko" />
<html:hidden name="zsk080Form" property="sortKey" />
<html:hidden name="zsk080Form" property="orderKey" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('zsk080kn');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk070');">
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
<logic:empty name="zsk080Form" property="zasekiList" scope="request">
  <div class="txt_l">
    <gsmsg:write key="zsk.zsk080.01" />
  </div>
</logic:empty>


<logic:notEmpty name="zsk080Form" property="zasekiList" scope="request">
  <div class="txt_l">
   <gsmsg:write key="zsk.zsk080.02" />
  </div>
<table class="table-top">
    <tr>
      <th class="w80" colspan="2">
        <gsmsg:write key="zsk.29" />
      </th>
      <th>
        <gsmsg:write key="zsk.25" />
      </th>
    </tr>
    <logic:iterate id="zsk030Mdl" name="zsk080Form" property="zasekiList" scope="request" indexId="idx">
    <bean:define id="sid" name="zsk030Mdl" property="zifSid" />
    <tr class="js_listHover cursor_p">
      <td class="txt_c js_listClick js_radio">
        <html:radio idName="zsk030Mdl" property="dfZifSid" value="zifSid" />
      </td>
      <td class="w80 js_listClick">
        <bean:write name="zsk030Mdl" property="zifName" />
      </td>
      <td class="js_listClick txt_c">
        <bean:write name="zsk030Mdl" property="lastUpdateDate" />
      </td>
    </tr>
    </logic:iterate>
  </table>
</logic:notEmpty>


  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('zsk080kn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk070');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>