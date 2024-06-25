<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String cabUse      = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_PRIVATE_USE);
   String cabNotUse   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_PRIVATE_NOT_USE);
   String cabAuthAll  = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_AUTH_ALL);
   String cabAuthUser = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_AUTH_USER);
   String capaKbnOff  = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CAPA_KBN_OFF);
   String capaKbnOn   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CAPA_KBN_ON);
   String verKbnOn    = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_ON);
   String verKbnOff   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_OFF);
%>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.52" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/file/fil290kn">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="fileSid" value="">
<html:hidden property="cmnMode" />
<html:hidden property="admVerKbn" />
<html:hidden property="backDsp" />
<html:hidden property="backScreen" />
<html:hidden property="filSearchWd" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="fil040SelectDel" />
<html:hidden property="fil010SelectDelLink" />

<html:hidden property="fil290CabinetUseKbn" />
<html:hidden property="fil290CabinetAuthKbn" />
<html:hidden property="fil290CapaKbn" />
<html:hidden property="fil290CapaSize" />
<html:hidden property="fil290CapaWarn" />
<html:hidden property="fil290VerKbn" />
<html:hidden property="fil290VerVisible" />
<html:hidden property="fil290initFlg" />

<logic:notEmpty name="fil290knForm" property="fil290CabinetSv">
<logic:iterate id="afid" name="fil290knForm" property="fil290CabinetSv">
  <input type="hidden" name="fil290CabinetSv" value="<bean:write name="afid" />">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.151" /></span>
    </li>
    <li>
      <div>
     <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil290knok');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
       <gsmsg:write key="cmn.final" />
     </button>
     <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil290knback');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
       <gsmsg:write key="cmn.back" />
     </button>
      </div>
    </li>
  </ul>
</div>

<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

<div class="wrapper w80 mrl_auto">
  <table class="table-left">
  <!-- 個人キャビネット使用  -->
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="fil.fil290.1" />
      </th>
      <td class="w80">
        <logic:equal name="fil290knForm" property="fil290CabinetUseKbn" value="<%= cabUse %>">
          <gsmsg:write key="fil.fil290.2" />
        </logic:equal>
        <logic:equal name="fil290knForm" property="fil290CabinetUseKbn" value="<%= cabNotUse %>">
          <gsmsg:write key="fil.fil290.3" />
        </logic:equal>
      </td>
    </tr>
    <!-- キャビネット使用許可  -->
    <logic:equal name="fil290knForm" property="fil290CabinetUseKbn" value="<%= cabUse %>">
    <tr>
      <th class="no_w">
        <gsmsg:write key="fil.fil290.5" />
      </th>
      <td>
        <logic:equal name="fil290knForm" property="fil290CabinetAuthKbn" value="<%= cabAuthAll %>">
          <gsmsg:write key="fil.fil290.6" />
        </logic:equal>
        <logic:equal name="fil290knForm" property="fil290CabinetAuthKbn" value="<%= cabAuthUser %>">
          <gsmsg:write key="fil.fil290kn.1" /><br>
            <logic:notEmpty name="fil290knForm" property="fil290knCabinetAuthLabel">
              <logic:iterate id="user" name="fil290knForm" property="fil290knCabinetAuthLabel" type="UsrLabelValueBean" >
                <br>・<bean:write name="user" property="label"/>
              </logic:iterate>
            </logic:notEmpty>
        </logic:equal>
      </td>
    </tr>
    <!-- 容量制限設定  -->
    <tr>
      <th>
        <gsmsg:write key="fil.3" />
      </th>
      <td>
        <logic:equal name="fil290knForm" property="fil290CapaKbn" value="<%= capaKbnOff %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="fil290knForm" property="fil290CapaKbn" value="<%= capaKbnOn %>">
          <gsmsg:write key="fil.4" />：<bean:write name="fil290knForm" property="fil290CapaSize" />
          <gsmsg:write key="wml.wml040.07" />
          <logic:notEqual name="fil290knForm" property="fil290CapaWarn" value="0">
            <br><gsmsg:write key="fil.fil030kn.1" />：<bean:write name="fil290knForm" property="fil290CapaWarn" />%
          </logic:notEqual>
        </logic:equal>
      </td>
    </tr>
    <!-- バージョン管理  -->
    <logic:equal name="fil290knForm" property="fil290VerVisible" value="<%= verKbnOn %>" >
    <tr>
      <th>
        <gsmsg:write key="fil.5" />
      </th>
      <td>
        <gsmsg:write key="fil.fil030.3" />：<bean:write name="fil290knForm" property="fil290VerKbn" />
      </td>
    </tr>
     </logic:equal>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil290knok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil290knback');">
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