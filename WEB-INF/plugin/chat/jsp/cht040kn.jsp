<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.01" /> <gsmsg:write key="cht.cht020.01" /><gsmsg:write key="cmn.check" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
  <html:form action="/chat/cht040kn">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="cht010SelectPartner" />
    <html:hidden property="cht010SelectKbn" />
    <html:hidden property="cht040InitFlg" />
    <html:hidden property="cht040BetweenUsers" />
    <html:hidden property="cht040CreateGroup" />
    <html:hidden property="cht040AlreadyRead" />
    <html:hidden property="cht040HowToLimit" />
    <html:hidden property="cht040TargetUserGroup" />

    <logic:notEmpty name="cht040knForm" property="cht040TargetUserList">
      <logic:iterate id="targetUser" name="cht040knForm" property="cht040TargetUserList">
        <input type="hidden" name="cht040TargetUserList" value="<bean:write name="targetUser"/>">
      </logic:iterate>
    </logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
 <div class="kanriPageTitle w80 mrl_auto">
   <ul>
     <li>
       <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
       <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
     </li>
     <li><gsmsg:write key="cmn.admin.setting" /></li>
     <li class="pageTitle_subFont">
       <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht020.01" /><gsmsg:write key="cmn.check" />
     </li>
     <li>
       <div>
         <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('kakutei');">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
           <gsmsg:write key="cmn.final" />
         </button>
         <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
           <gsmsg:write key="cmn.back" />
         </button>
       </div>
     </li>
   </ul>
  </div>
  <div class="wrapper w80 mrl_auto">
    <table class="table-left">
      <%-- ユーザ間チャットの使用 --%>
      <tr>
        <th class="w25 no_w">
          <gsmsg:write key="cht.cht040.01" />
        </th>
        <td class="w75">
          <logic:equal name="cht040knForm" property="cht040BetweenUsers" value="<%=String.valueOf(GSConstChat.LIMIT_BETWEEN_USERS)%>">
            <gsmsg:write key="cmn.do.limit" />
          </logic:equal>
          <logic:equal name="cht040knForm" property="cht040BetweenUsers" value="<%=String.valueOf(GSConstChat.PERMIT_BETWEEN_USERS)%>">
            <gsmsg:write key="cmn.not.limit" />
          </logic:equal>
        </td>
      </tr>
      <%-- グループの作成 --%>
      <tr>
      <th class="no_w">
        <gsmsg:write key="cht.cht040.02" />
      </th>
      <td>
        <logic:equal name="cht040knForm" property="cht040CreateGroup" value="<%=String.valueOf(GSConstChat.LIMIT_CREATE_GROUP)%>">
          <gsmsg:write key="cmn.do.limit" />
        </logic:equal>
        <logic:equal name="cht040knForm" property="cht040CreateGroup" value="<%=String.valueOf(GSConstChat.PERMIT_CREATE_GROUP)%>">
          <gsmsg:write key="cmn.not.limit" />
        </logic:equal>
        <%--グループ作成権限 --%>
        <logic:equal name="cht040knForm" property="cht040CreateGroup" value="<%=String.valueOf(GSConstChat.LIMIT_CREATE_GROUP)%>">
          <div class="mt10">
            <span class="fw_b"><gsmsg:write key="cmn.howto.limit" /></span><br>
            <logic:equal name="cht040knForm" property="cht040HowToLimit" value="<%=String.valueOf(GSConstChat.TARGET_LIMIT)%>">
              <gsmsg:write key="cmn.access.permit" /><br>
            </logic:equal>
            <logic:equal name="cht040knForm" property="cht040HowToLimit" value="<%=String.valueOf(GSConstChat.TARGET_PERMIT)%>">
              <gsmsg:write key="cmn.access.limit" /><br>
            </logic:equal>
          </div>
        <div>
          <br>
          <span class="fw_b"><gsmsg:write key="cmn.limit.target" /></span><br>
          <logic:iterate id="user" name="cht040knForm" property="cht040knTargetUser.selectedList(limitList)" type="UsrLabelValueBean">
          <span class="<%=user.getCSSClassNameNormal()%>"><bean:write name="user" property="label" /></span><br>
        </logic:iterate>
        </div>
        </logic:equal>
      </td>
    </tr>
    <%-- 既読の表示 --%>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cht.cht040.03" />
      </th>
      <td>
        <logic:equal name="cht040knForm" property="cht040AlreadyRead" value="<%=String.valueOf(GSConstChat.KIDOKU_DISP) %>">
        <gsmsg:write key="cmn.display.ok" />
        </logic:equal>
        <logic:equal name="cht040knForm" property="cht040AlreadyRead" value="<%=String.valueOf(GSConstChat.KIDOKU_NOT_DISP) %>">
        <gsmsg:write key="cmn.dont.show" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>

</html:html>