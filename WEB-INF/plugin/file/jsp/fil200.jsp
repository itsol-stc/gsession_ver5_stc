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
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" />　<gsmsg:write key="cmn.admin.setting" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/file/fil200">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="backDsp" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="filSearchWd" />

<logic:notEmpty name="fil200Form" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil200Form" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil200Form" property="fil010SelectDelLink" scope="request">
  <logic:iterate id="del" name="fil200Form" property="fil010SelectDelLink" scope="request">
    <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
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
      <gsmsg:write key="cmn.filekanri" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil200back');">
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
      <dt onClick="buttonPush('fil200baseConf');">
        <span class="settingList_title"><gsmsg:write key="cmn.preferences" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.1" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('fil200cabinetConf');">
        <span class="settingList_title"><gsmsg:write key="fil.62" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.2" /></div>
      </dd>
    </dl>
    <logic:equal name="fil200Form" property="canUsePrivate" value="<%=String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_PRIVATE_USE) %>">
    <dl>
      <dt onClick="buttonPush('fil200myCabinetConf');">
        <span class="settingList_title"><gsmsg:write key="fil.143" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.2" /></div>
      </dd>
    </dl>
    </logic:equal>
    <dl>
      <dt onClick="buttonPush('fil200deleteFile');">
        <span class="settingList_title"><gsmsg:write key="fil.fil230.4" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.3" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('fil200call');">
        <span class="settingList_title"><gsmsg:write key="fil.124" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.4" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('fil200myCabinetBaseConf');">
        <span class="settingList_title original-display fs_13"><gsmsg:write key="cmn.individual" /><gsmsg:write key="fil.23" /><gsmsg:write key="fil.156" /></span>
        <span class="settingList_title classic-display"><gsmsg:write key="cmn.individual" /><gsmsg:write key="fil.23" /><gsmsg:write key="fil.156" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.6" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('fil200gaika');">
        <span class="settingList_title"><gsmsg:write key="fil.132" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="fil.fil200.7" /></div>
      </dd>
    </dl>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>