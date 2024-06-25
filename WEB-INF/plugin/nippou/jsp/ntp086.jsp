<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.ntp.GSConstNippou" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>

<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../nippou/js/ntp086.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/htmlEscape.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>
<html:form action="/nippou/ntp086">
<input type="hidden" name="CMD" value="">
<html:hidden property="ntp086NttSid"/>
<html:hidden property="ntp086pageNum"/>

<ntp:conf_hidden name="ntp086Form"/>

<logic:notEmpty name="ntp086Form" property="ntp086TemplateList" scope="request">
  <logic:iterate id="sort" name="ntp086Form" property="ntp086TemplateList" scope="request">
    <input type="hidden" name="ntp086sortLabel" value="<bean:write name="sort" property="templateValue" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="cmn.template" /><gsmsg:write key="cmn.list" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('ntp086add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp086back');">
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
    <button type="button" value="<gsmsg:write key="cmn.up" />" class="baseBtn" name="btn_upper" onClick="buttonPush2('upTemplateData');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.down" />" class="baseBtn" name="btn_downer" onClick="buttonPush2('downTemplateData');">
      <gsmsg:write key="cmn.down" />
    </button><br>
    <span class="cl_fontWarn"><gsmsg:write key="ntp.91" /></span>
  </div>

  <table class="table-top">
  <tr>
    <th class="w5">

    </th>
    <th class="w80">
      <gsmsg:write key="ntp.92" />
    </th>
    <th class="w15">
      <gsmsg:write key="ntp.93" />
    </th>
  </tr>
   <logic:notEmpty name="ntp086Form" property="ntp086TemplateList">
   <logic:iterate id="templateMdl" name="ntp086Form" property="ntp086TemplateList" indexId="idx">
   <bean:define id="radiovalue" name="templateMdl" property="templateValue" />
  <tr class="js_listHover cursor_p">
    <!-- ラジオボタン -->
    <td class="js_sord_radio">
      <div class="txt_c">
      <html:radio property="ntp086sortTemplate" value="<%= String.valueOf(radiovalue) %>"/>
      </div>
    </td>
    <!-- テンプレート名 -->
    <td onclick="return buttonSubmit('edit','<bean:write name="templateMdl" property="templateSid" />') ">
      <span class="cl_linkDef"><bean:write name="templateMdl" property="templateName" /></span>
    </td>
    <td class="txt_c">
    <button id="<bean:write name="templateMdl" property="templateSid" />" class="baseBtn js_apcUserBtn" type="button" value="<gsmsg:write key="ntp.94" />" >
      <gsmsg:write key="ntp.94" />
    </button>
    </td>
  </tr>
</logic:iterate>
</logic:notEmpty>
</table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('ntp086add');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp086back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

<div id="apcUserPop" title="<gsmsg:write key="ntp.93" />" class"display_none">
  <p>
    <div id="tmpUsrArea">
    </div>
  </p>
</div>

</html:form>
</body>
</html:html>