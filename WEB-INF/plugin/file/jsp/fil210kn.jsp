<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String crtKbnAdmin = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_ADMIN);
   String crtKbnGroup = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_GROUP);
   String crtKbnUser = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_USER);
   String crtKbnNo = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CREATE_CABINET_PERMIT_NO);
   String lockKbnOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.LOCK_KBN_OFF);
   String lockKbnOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.LOCK_KBN_ON);
   String verKbnOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_OFF);
   String verKbnOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_ON);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="cmn.preferences.kn" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/file/fil210kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="backDsp" />
<html:hidden property="fil210CrtKbn" />
<html:hidden property="fil210SltGroup" />
<html:hidden property="fil210AllSelect" />
<html:hidden property="fil210FileSize" />
<html:hidden property="fil210SaveDays" />
<html:hidden property="fil210LockKbn" />
<html:hidden property="fil210VerKbn" />
<html:hidden property="filSearchWd" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />

<logic:notEmpty name="fil210knForm" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil210knForm" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil210knForm" property="fil210SvUsers">
<logic:iterate id="param" name="fil210knForm" property="fil210SvUsers">
  <input type="hidden" name="fil210SvUsers" value="<bean:write name="param" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil210knForm" property="fil210SvGroups">
<logic:iterate id="param" name="fil210knForm" property="fil210SvGroups">
  <input type="hidden" name="fil210SvGroups" value="<bean:write name="param" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil210knForm" property="fil010SelectDelLink" scope="request">
  <logic:iterate id="del" name="fil210knForm" property="fil010SelectDelLink" scope="request">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="cmn.preferences.kn" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil210knok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil210knback');">
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
      <th class="w20 no_w">
        <gsmsg:write key="fil.28" />
      </th>
      <td class="w80">
        <logic:equal name="fil210knForm" property="fil210CrtKbn" value="<%= crtKbnAdmin %>">
        <gsmsg:write key="fil.29" />
        </logic:equal>

      <logic:equal name="fil210knForm" property="fil210CrtKbn" value="<%= crtKbnGroup %>">
        <gsmsg:write key="fil.fil210kn.1" /><br>
          <logic:notEmpty name="fil210knForm" property="fil210knGroupNameList">
          <logic:iterate id="groupName" name="fil210knForm" property="fil210knGroupNameList">
          <gsmsg:write key="wml.231" /><bean:write name="groupName" /><br>
        </logic:iterate>
        </logic:notEmpty>
      </logic:equal>

      <logic:equal name="fil210knForm" property="fil210CrtKbn" value="<%= crtKbnUser %>">
        <gsmsg:write key="fil.fil210kn.2" /><br>
          <logic:notEmpty name="fil210knForm" property="fil210knUserNameList">
          <logic:iterate id="userName" name="fil210knForm" property="fil210knUserNameList">
          <gsmsg:write key="wml.231" /><bean:write name="userName" /><br>
        </logic:iterate>
        </logic:notEmpty>
      </logic:equal>

      <logic:equal name="fil210knForm" property="fil210CrtKbn" value="<%= crtKbnNo %>">
        <gsmsg:write key="fil.30" />
      </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.31" />
      </th>
      <td>
        <bean:write name="fil210knForm" property="fil210knFileSize" /><br>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.fil210.3" />
      </th>
      <td>
        <bean:write name="fil210knForm" property="fil210knSaveDays" /><br>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.34" />
      </th>
      <td>
        <logic:equal name="fil210knForm" property="fil210LockKbn" value="<%= lockKbnOff %>"><gsmsg:write key="fil.107" /></logic:equal>
        <logic:equal name="fil210knForm" property="fil210LockKbn" value="<%= lockKbnOn %>"><gsmsg:write key="fil.108" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.69" />
      </th>
      <td>
        <logic:equal name="fil210knForm" property="fil210VerKbn" value="<%= verKbnOff %>"><gsmsg:write key="fil.107" /></logic:equal>
        <logic:equal name="fil210knForm" property="fil210VerKbn" value="<%= verKbnOn %>"><gsmsg:write key="fil.108" /></logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil210knok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil210knback');">
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