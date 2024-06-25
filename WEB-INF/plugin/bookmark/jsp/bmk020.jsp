<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="bmk.bmk020.02" /></title>
<link rel=stylesheet href="../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>

  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

  <html:form action="/bookmark/bmk020">

    <input type="hidden" name="CMD" value="">
    <%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp"%>

    <html:hidden property="bmk070ReturnPage" />
    <html:hidden property="bmk070Page" />
    <html:hidden property="bmk070PageTop" />
    <html:hidden property="bmk070PageBottom" />
    <html:hidden property="bmk070OrderKey" />
    <html:hidden property="bmk070SortKey" />
    <html:hidden property="bmk030InitFlg" />

    <html:hidden property="bmk080Page" />
    <html:hidden property="bmk080PageTop" />
    <html:hidden property="bmk080PageBottom" />

    <logic:notEmpty name="bmk020Form" property="bmk010delInfSid" scope="request">
      <logic:iterate id="item" name="bmk020Form" property="bmk010delInfSid" scope="request">
        <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="bmk150PageNum" />

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bookmark/images/classic/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />">
          <img class="header_pluginImg" src="../bookmark/images/original/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />"
        </li>
        <li>
          <gsmsg:write key="bmk.43" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="bmk.bmk020.01" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('bmk020pushOk');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk020pushBack');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <!-- メッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left" border="0" cellpadding="5">
        <tr>
          <th class="w10 no_w">
            <span>URL</span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="txt_l w90">
            <html:text maxlength="1000" property="bmk020url" styleClass="wp500" />
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('bmk020pushOk');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk020pushBack');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>

    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>