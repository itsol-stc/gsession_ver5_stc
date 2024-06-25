<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>
<body>
  <html:form action="/chat/cht110kn" styleId = "form">
    <logic:equal name="cht110knForm" property="cht100ProcMode" value="0">
      <input type="hidden" name="helpPrm" value="0">
    </logic:equal>
    <logic:notEqual name="cht110knForm" property="cht100ProcMode" value="0">
      <input type="hidden" name="helpPrm" value="1">
    </logic:notEqual>

    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="cht010SelectPartner" />
    <html:hidden property="cht010SelectKbn" />
    <html:hidden property="cgiSid" />

    <html:hidden property="cht110groupId" />
    <html:hidden property="cht110groupName" />
    <html:hidden property="cht110category" />
    <html:hidden property="cht110biko" />
    <html:hidden property="cht110compFlg" />
    <html:hidden property="cht110InitFlg" />

    <html:hidden property="cht100InitFlg" />
    <html:hidden property="cht100ProcMode" />
    <html:hidden property="cht100SortKey" />
    <html:hidden property="cht100OrderKey" />
    <html:hidden property="cht100PageTop" />
    <html:hidden property="cht100PageBottom" />
    <html:hidden property="cht100SearchFlg" />

    <html:hidden property="cht100SearchFlg" />
    <html:hidden property="cht100Keyword" />
    <html:hidden property="cht100AndOr" />
    <html:hidden property="cht100GroupId" />
    <html:hidden property="cht100GroupName" />
    <html:hidden property="cht100GroupInfo" />
    <html:hidden property="cht100Category" />
    <html:hidden property="cht100StatusKbn" />
    <html:hidden property="cht100SelectGroup" />
    <html:hidden property="cht100SelectUser" />
    <html:hidden property="cht100AdminMember" />
    <html:hidden property="cht100GeneralMember" />
    <html:hidden property="cht100CreateYearFr" />
    <html:hidden property="cht100CreateMonthFr" />
    <html:hidden property="cht100CreateDayFr" />
    <html:hidden property="cht100CreateYearTo" />
    <html:hidden property="cht100CreateMonthTo" />
    <html:hidden property="cht100CreateDayTo" />
    <html:hidden property="cht100UpdateYearFr" />
    <html:hidden property="cht100UpdateMonthFr" />
    <html:hidden property="cht100UpdateDayFr" />
    <html:hidden property="cht100UpdateYearTo" />
    <html:hidden property="cht100UpdateMonthTo" />
    <html:hidden property="cht100UpdateDayTo" />

    <html:hidden property="svCht100Keyword" />
    <html:hidden property="svCht100AndOr" />
    <html:hidden property="svCht100GroupId" />
    <html:hidden property="svCht100GroupName" />
    <html:hidden property="svCht100GroupInfo" />
    <html:hidden property="svCht100Category" />
    <html:hidden property="svCht100StatusKbn" />
    <html:hidden property="svCht100SelectGroup" />
    <html:hidden property="svCht100SelectUser" />
    <html:hidden property="svCht100AdminMember" />
    <html:hidden property="svCht100GeneralMember" />
    <html:hidden property="svCht100CreateYearFr" />
    <html:hidden property="svCht100CreateMonthFr" />
    <html:hidden property="svCht100CreateDayFr" />
    <html:hidden property="svCht100CreateYearTo" />
    <html:hidden property="svCht100CreateMonthTo" />
    <html:hidden property="svCht100CreateDayTo" />
    <html:hidden property="svCht100UpdateYearFr" />
    <html:hidden property="svCht100UpdateMonthFr" />
    <html:hidden property="svCht100UpdateDayFr" />
    <html:hidden property="svCht100UpdateYearTo" />
    <html:hidden property="svCht100UpdateMonthTo" />
    <html:hidden property="svCht100UpdateDayTo" />
    <html:hidden property="cht110createDate" />
    <html:hidden property="cht110updateDate" />

<logic:notEmpty name="cht110knForm" property="cht110memberAdmin">
  <logic:iterate id="usid" name="cht110knForm" property="cht110memberAdmin">
    <input type="hidden" name="cht110memberAdmin" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cht110knForm" property="cht110memberNormal">
  <logic:iterate id="usid" name="cht110knForm" property="cht110memberNormal">
    <input type="hidden" name="cht110memberNormal" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cht110knForm" property="oldMemberSids">
  <logic:iterate id="member" name="cht110knForm" property="oldMemberSids">
    <input type="hidden" name="oldMemberSids" value="<bean:write name="member" />">
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
    <logic:equal name="cht110knForm" property="cht100ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_ADD)%>">
      <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht110kn.01" />
      </li>
    </logic:equal>
    <logic:equal name="cht110knForm" property="cht100ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_EDIT)%>">
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht110kn.02" />
      </li>
    </logic:equal>
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
    <tr>
      <th class="w25">
        <gsmsg:write key="cht.01" /><gsmsg:write key="main.src.man220.6" />
      </th>
      <td class="w75">
        <bean:write name="cht110knForm" property="cht110groupId" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.category" />
      </th>
      <td>
        <bean:write name="cht110knForm" property="cht110knCategoryName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.group.name" />
      </th>
      <td>
        <bean:write name="cht110knForm" property="cht110groupName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.member" />
      </th>
      <td>
        <logic:notEmpty name="cht110knForm" property="cht110knAdminMemberList">
          <span><gsmsg:write key="cht.03" /></span>
          <br>
          <logic:iterate id="user" name="cht110knForm" property="cht110knAdminMemberList" type="UsrLabelValueBean">
            ・<bean:write name="user" property="label" />
            <br>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="cht110knForm" property="cht110knGeneralMemberList">
          <br>
          <span><gsmsg:write key="cht.04" /></span>
          <br>
          <logic:iterate id="user" name="cht110knForm" property="cht110knGeneralMemberList" type="UsrLabelValueBean">
            ・<bean:write name="user" property="label" />
            <br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <bean:write name="cht110knForm" property="cht110knBiko" filter="false"/>
      </td>
    </tr>
    <logic:equal name="cht110knForm" property="cht100ProcMode" value="<%= String.valueOf(GSConstChat.CHAT_MODE_EDIT) %>">
    <tr>
      <th>
        <gsmsg:write key="anp.state" /><gsmsg:write key="tcd.tcd070.03" />
      </th>
      <td>
        <logic:equal name="cht110knForm" property="cht110compFlg" value="<%= String.valueOf(GSConstChat.CHAT_ARCHIVE_MODE) %>">
          <gsmsg:write key="cht.cht110.01" />
          <br>
        </logic:equal>
        <logic:equal name="cht110knForm" property="cht110compFlg" value="<%= String.valueOf(GSConstChat.CHAT_ARCHIVE_NOT_MODE) %>">
          <gsmsg:write key="cht.cht110.02" />
          <br>
        </logic:equal>
      </td>
    </tr>
    </logic:equal>
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

