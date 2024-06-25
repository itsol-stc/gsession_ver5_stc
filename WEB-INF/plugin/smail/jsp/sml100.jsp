<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String hkbnCmn = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml100.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body>
<html:form action="/smail/sml100">

<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<input type="hidden" name="smlAccountMode" value="2">
<input type="hidden" name="sml050HinaKbn" value="" >

<logic:notEmpty name="sml100Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml100Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
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
    <gsmsg:write key="cmn.shortmail" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sml100back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="settingList">
    <dl>
      <dt onClick="return buttonPush('accountList');">
        <span class="settingList_title"><gsmsg:write key="wml.100" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.09" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('smlConfAccount');">
        <span class="settingList_title"><gsmsg:write key="cmn.preferences" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.10" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('fwconf');">
        <span class="settingList_title"><gsmsg:write key="sml.20" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="sml.sml100.01" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return hinaEdit(<%= hkbnCmn %>);">
        <span class="settingList_title"><gsmsg:write key="sml.sml100.04" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="sml.sml100.07" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('smlAdmDspConf');">
        <span class="settingList_title"><gsmsg:write key="cmn.display.settings" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="sml.sml120.07" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('smlBanDestination');">
        <span class="settingList_title"><gsmsg:write key="sml.188" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="sml.sml100.09" /></div>
      </dd>
    </dl>
  </div>
</div>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>