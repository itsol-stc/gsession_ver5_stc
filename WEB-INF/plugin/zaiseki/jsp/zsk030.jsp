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
<script src="../zaiseki/js/zsk030.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
</head>

<body>
<html:form action="/zaiseki/zsk030">
<input type="hidden" name="CMD">
<html:hidden name="zsk030Form" property="editZifSid" />

<html:hidden name="zsk030Form" property="backScreen" />
<html:hidden name="zsk030Form" property="selectZifSid" />
<html:hidden name="zsk030Form" property="uioStatus" />
<html:hidden name="zsk030Form" property="uioStatusBiko" />
<html:hidden name="zsk030Form" property="sortKey" />
<html:hidden name="zsk030Form" property="orderKey" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--  body  -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="zsk.26" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="zsk.zsk030.02" />" class="baseBtn" onClick="buttonPush('zsk040');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="zsk.zsk030.02" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="zsk.zsk030.02" />">
          <gsmsg:write key="zsk.zsk030.02" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk020');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">

<logic:empty name="zsk030Form" property="zasekiList" scope="request">
  <div class="txt_l"><gsmsg:write key="zsk.zsk030.03" /></div>
</logic:empty>

<logic:notEmpty name="zsk030Form" property="zasekiList" scope="request">

  <div class="txt_l"><gsmsg:write key="zsk.zsk030.01" /></div>

  <table class="table-top">
    <tr>
      <th class="w80">
        <gsmsg:write key="zsk.29" />
      </th>
      <th class="w20">
        <gsmsg:write key="zsk.25" />
      </th>
    </tr>
  <logic:iterate id="zsk030Mdl" name="zsk030Form" property="zasekiList" scope="request" indexId="idx">
    <tr class="js_listHover cursor_p" id="<bean:write name="zsk030Mdl" property="zifSid" />">
      <td class="js_listClick cl_linkDef">
        <bean:write name="zsk030Mdl" property="zifName" />
      </td>
      <td class="js_listClick txt_c">
        <bean:write name="zsk030Mdl" property="lastUpdateDate" />
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
  </table>


  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="zsk.zsk030.02" />" class="baseBtn" onClick="buttonPush('zsk040');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="zsk.zsk030.02" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="zsk.zsk030.02" />">
      <gsmsg:write key="zsk.zsk030.02" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk020');">
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