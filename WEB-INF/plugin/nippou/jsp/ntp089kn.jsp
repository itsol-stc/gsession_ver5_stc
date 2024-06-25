<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<bean:define id="ntpEditMode" name="ntp089knForm" property="ntp088editMode" type="java.lang.Integer" />

<%
  int editMode = ntpEditMode.intValue();
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="schedule.sch240.01" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/nippou/ntp089kn">
<input type="hidden" name="CMD" value="">

<ntp:conf_hidden name="ntp089knForm"/>

<html:hidden property="ntp089initFlg" />
<html:hidden property="ntp089name" />
<html:hidden property="ntp089biko" />
<html:hidden property="ntp089position" />
<html:hidden property="ntp089positionAuth" />
<html:hidden property="ntp089accessGroup" />

<html:hidden property="ntp088keyword" />
<html:hidden property="ntp088pageTop" />
<html:hidden property="ntp088pageBottom" />
<html:hidden property="ntp088pageDspFlg" />
<html:hidden property="ntp088svKeyword" />
<html:hidden property="ntp088sortKey" />
<html:hidden property="ntp088order" />
<html:hidden property="ntp088editData" />
<html:hidden property="ntp088searchFlg" />
<html:hidden property="ntp088editMode" />

<logic:equal name="ntp089knForm" property="ntp088editMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="ntp089knForm" property="ntp088editMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>

<logic:notEmpty name="ntp089knForm" property="ntp089subject">
  <logic:iterate id="subject" name="ntp089knForm" property="ntp089subject">
    <input type="hidden" name="ntp089subject" value="<bean:write name="subject"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="ntp089knForm" property="ntp089editUser">
  <logic:iterate id="editUser" name="ntp089knForm" property="ntp089editUser">
    <input type="hidden" name="ntp089editUser" value="<bean:write name="editUser"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="ntp089knForm" property="ntp089accessUser">
  <logic:iterate id="accessUser" name="ntp089knForm" property="ntp089accessUser">
    <input type="hidden" name="ntp089accessUser" value="<bean:write name="accessUser"/>">
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
    <% if (editMode == 0) { %>
        <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="schedule.sch240kn.01" />
        </li>
        <% } else if (editMode == 1) { %>
        <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="schedule.sch240kn.02" />
        </li>
        <% } %>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doRegister');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp089knback');">
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

  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch240.04" />
      </th>
      <td class="w80">
        <bean:write name="ntp089knForm" property="ntp089name" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="schedule.sch240.05" />
      </th>
      <td>
        <logic:notEmpty name="ntp089knForm" property="ntp089knSubjectList">
          <logic:iterate id="subject" name="ntp089knForm" property="ntp089knSubjectList" scope="request">
            <logic:equal name="subject" property="usrUkoFlg" value="1">
              <span class="mukoUser"><bean:write name="subject" property="label" /><br></span>
            </logic:equal>
            <logic:notEqual name="subject" property="usrUkoFlg" value="1">
              <span><bean:write name="subject" property="label" /><br></span>
            </logic:notEqual>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.reading.permit" />
      </th>
      <td>
        <logic:greaterThan name="ntp089knForm" property="ntp089position" value="0">
          <gsmsg:write key="cmn.post" /><gsmsg:write key="cmn.colon" /><bean:write name="ntp089knForm" property="ntp089knpositionName" />
          <div class="settingForm_separator">
        </logic:greaterThan>

        <logic:equal name="ntp089knForm" property="ntp089positionAuth" value="0">
          <div>
            <logic:notEmpty name="ntp089knForm" property="ntp089knAccessList">
              <logic:iterate id="accessUser" name="ntp089knForm" property="ntp089knAccessList" scope="request">
                <logic:equal name="accessUser" property="usrUkoFlg" value="1">
                  <span class="mukoUser"><bean:write name="accessUser" property="label" /><br></span>
                </logic:equal>
                <logic:notEqual name="accessUser" property="usrUkoFlg" value="1">
                  <bean:write name="accessUser" property="label" /><br>
                </logic:notEqual>
              </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="ntp089knForm" property="ntp089knAccessList">&nbsp;</logic:empty>
          </logic:equal>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <bean:write name="ntp089knForm" property="ntp089knBiko" filter="false" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doRegister');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp089knback');">
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