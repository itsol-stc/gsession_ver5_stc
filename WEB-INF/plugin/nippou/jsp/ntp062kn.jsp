<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /> [<gsmsg:write key="ntp.1" /><gsmsg:write key="cmn.import" /><gsmsg:write key="cmn.check" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/nippou/ntp062kn">

<input type="hidden" name="CMD" value="">
<jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp060_hiddenParams.jsp" />
<html:hidden property="ntp062InitFlg" />

<logic:notEmpty name="ntp062knForm" property="ntp061ChkShohinSidList" scope="request">
<logic:iterate id="item" name="ntp062knForm" property="ntp061ChkShohinSidList" scope="request">
  <input type="hidden" name="ntp061ChkShohinSidList" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ntp062knForm" property="sv_users" scope="request">
<logic:iterate id="ulBean" name="ntp062knForm" property="sv_users" scope="request">
  <input type="hidden" name="sv_users" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>
<html:hidden property="ntp061NanPermitView" />
<html:hidden property="ntp061NanPermitEdit" />

<logic:notEmpty name="ntp062knForm" property="ntp061NanPermitUserView" scope="request">
<logic:iterate id="ulBean" name="ntp062knForm" property="ntp061NanPermitUserView" scope="request">
  <input type="hidden" name="ntp061NanPermitUserView" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="ntp062knForm" property="ntp061NanPermitUserEdit" scope="request">
<logic:iterate id="ulBean" name="ntp062knForm" property="ntp061NanPermitUserEdit" scope="request">
  <input type="hidden" name="ntp061NanPermitUserEdit" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="ntp062knForm" property="ntp061NanPermitEditUser" scope="request">
<logic:iterate id="ulBean" name="ntp062knForm" property="ntp061NanPermitEditUser" scope="request">
  <input type="hidden" name="ntp061NanPermitEditUser" value="<bean:write name="ulBean" />">
</logic:iterate>
</logic:notEmpty>



<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.import" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_import_input');">
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
  <gsmsg:write key="main.man028kn.3" />
</div>
<table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.capture.file.name" />
      </th>
      <td class="w80">
        <a href="../nippou/ntp062kn.do?CMD=downLoad">
          <bean:write name="ntp062knForm" property="ntp062knFileName" />
        </a>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <bean:write name="ntp062knForm" property="impDataCnt" /><gsmsg:write key="cmn.number" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.staff" />
      </th>
      <td>
        <logic:notEmpty name="ntp062knForm" property="ntp062knSelectUserList" scope="request">
          <logic:iterate id="urBean" name="ntp062knForm" property="ntp062knSelectUserList" scope="request">
            <bean:write name="urBean" property="usiSei" scope="page"/>　<bean:write name="urBean" property="usiMei" scope="page"/><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.58" />
      </th>
      <td>
        <logic:notEmpty name="ntp062knForm" property="ntp061ShohinList">
          <logic:iterate id="shohinMdl" name="ntp062knForm" property="ntp061ShohinList" scope="request">
            <bean:write name="shohinMdl" property="label" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <!-- 権限設定 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.setting.permissions"  />
      </th>
      <td>
      <logic:equal name="ntp062knForm" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
        <%--制限なし --%>
        <div class="mb5"><span id="permissionViewLabel"><gsmsg:write key="address.61" /><gsmsg:write key="cmn.colon" /></span>
        <gsmsg:write key="cmn.nolimit" /></div>
        <gsmsg:write key="cmn.edit.permissions" /><gsmsg:write key="cmn.colon" />
        <logic:equal name="ntp062knForm" property="ntp061NanPermitEdit" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_ALL) %>">
          <gsmsg:write key="cmn.nolimit" />
        </logic:equal>
        <logic:equal name="ntp062knForm" property="ntp061NanPermitEdit" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>">
        <%--担当者のみ --%>
          <gsmsg:write key="address.62" />
        </logic:equal>
        <%--ユーザグループ指定 --%>
        <logic:equal name="ntp062knForm" property="ntp061NanPermitEdit" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>">
          <gsmsg:write key="ntp.ntp061.1" /></br>
            <logic:iterate id="user" name="ntp062knForm" property="ntp062NanPermitList.selectedList(edit)" type="UsrLabelValueBean">
              <html:hidden property="ntp062NanPermitList.selected(edit)" value="<%=user.getValue() %>" />
              <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
            </logic:iterate>
          </logic:equal>
        </logic:equal>


        <logic:equal name="ntp062knForm" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_TANTO) %>">
        <%--担当者のみ --%>
          <gsmsg:write key="address.62" />
        </logic:equal>
       <%--ユーザグループ指定 --%>
        <logic:equal name="ntp062knForm" property="ntp061NanPermitView" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.NAP_KBN_USERGROUP) %>">
          <gsmsg:write key="ntp.ntp061.1" /></br>
          <div class="mt5"><bean:write name="ntp062knForm" property="ntp062NanPermitList.selectedListName(view)"/><gsmsg:write key="cmn.colon" /></div>
          <logic:iterate id="user" name="ntp062knForm" property="ntp062NanPermitList.selectedList(view)" type="UsrLabelValueBean">
            <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
            <html:hidden property="ntp062NanPermitList.selected(view)" value="<%=user.getValue() %>" />
          </logic:iterate>

          <div class="mt5"><bean:write name="ntp062knForm" property="ntp062NanPermitList.selectedListName(edit)"/><gsmsg:write key="cmn.colon" /></div>
          <logic:iterate id="user" name="ntp062knForm" property="ntp062NanPermitList.selectedList(edit)" type="UsrLabelValueBean">
            <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
            <html:hidden property="ntp062NanPermitList.selected(edit)" value="<%=user.getValue() %>" />
          </logic:iterate>
        </logic:equal>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImport');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_import_input');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>


<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />

</html:form>
</body>
</html:html>