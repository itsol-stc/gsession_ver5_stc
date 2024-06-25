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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="bmk.43" /> <gsmsg:write key="cmn.setting.permissions" /><gsmsg:write key="cmn.check" /></title>
</head>

<body>
<html:form action="/bookmark/bmk110kn">
<input type="hidden" name="CMD" value="">

<html:hidden name="bmk110knForm" property="backScreen" />
<html:hidden name="bmk110knForm" property="bmk110PubEditKbn" />
<html:hidden name="bmk110knForm" property="bmk110GrpEditKbn" />
<html:hidden name="bmk110knForm" property="bmk110initFlg" />
<html:hidden name="bmk110knForm" property="bmk110GroupSid" />

<logic:notEmpty name="bmk110knForm" property="bmk110UserSid">
<logic:iterate id="usid" name="bmk110knForm" property="bmk110UserSid">
  <input type="hidden" name="bmk110UserSid" value="<bean:write name="usid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="bmk110knForm" property="bmk110GrpSid">
<logic:iterate id="gsid" name="bmk110knForm" property="bmk110GrpSid">
  <input type="hidden" name="bmk110GrpSid" value="<bean:write name="gsid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="bmk110knForm" property="bmk010delInfSid" scope="request">
<logic:iterate id="item" name="bmk110knForm" property="bmk010delInfSid" scope="request">
  <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp" %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="bmk.43" /></span><gsmsg:write key="cmn.setting.permissions" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('bmk110knkakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk110knback');">
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
      <th class="w25">
        <gsmsg:write key="bmk.34" />
      </th>
      <td class="w75">
      <logic:equal name="bmk110knForm" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ADMIN) %>"><gsmsg:write key="cmn.only.admin.editable" /></logic:equal>
    <logic:equal name="bmk110knForm" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ALL) %>"><gsmsg:write key="bmk.33" /></logic:equal>

    <!-- 共有ブックマーク編集権限：グループ指定 -->
    <logic:equal name="bmk110knForm" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP) %>">
        <gsmsg:write key="group.designation" /><br>
        <logic:notEmpty name="bmk110knForm" property="bmk110knGroupList">
          <logic:iterate id="groupName" name="bmk110knForm" property="bmk110knGroupList">
          <span class="pl10"><bean:write name="groupName" /></span><br>
          </logic:iterate>
        </logic:notEmpty>
    </logic:equal>
    <!-- 共有ブックマーク編集権限：ユーザ指定 -->
    <logic:equal name="bmk110knForm" property="bmk110PubEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER) %>">
        <gsmsg:write key="cmn.user.specified" /><br>
        <logic:notEmpty name="bmk110knForm" property="bmk110knUserList">
          <logic:iterate id="user" name="bmk110knForm" property="bmk110knUserList">
              <bean:define id="mukoUserClass" value="" />
              <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
              <span class="<%=mukoUserClass%> %> pl10"><bean:write name="user" property="label" /></span><br>
          </logic:iterate>
        </logic:notEmpty>

    </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="bmk.51" />
      </th>
      <td>
      <logic:equal name="bmk110knForm" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_ADMIN) %>"><gsmsg:write key="cmn.only.admin.editable" /></logic:equal>
      <logic:equal name="bmk110knForm" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_GROUP) %>"><gsmsg:write key="bmk.48" /></logic:equal>
      <logic:equal name="bmk110knForm" property="bmk110GrpEditKbn" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.GROUP_EDIT_ALL) %>"><gsmsg:write key="bmk.33" /></logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('bmk110knkakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk110knback');">
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