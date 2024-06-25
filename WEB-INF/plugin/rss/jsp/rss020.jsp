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
<title>GROUPSESSION <gsmsg:write key="rss.rss020.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../rss/js/rss020.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<link rel=stylesheet href='../rss/css/rss.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<!-- body class="body_03" onload="initDispDescription(<bean:write name='rss020Form' property='rssAuth' />);" -->
<body>

  <html:form action="/rss/rss020">

    <input type="hidden" name="CMD" value="">
    <html:hidden name="rss020Form" property="rssCmdMode" />
    <html:hidden name="rss020Form" property="rssSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
            <img class="header_pluginImg-classic" src="../rss/images/classic/header_rss.png" alt=" <gsmsg:write key="rss.3" />">
          <img class="header_pluginImg" src="../rss/images/original/menu_icon_single.png" alt=" <gsmsg:write key="rss.3" />">
        </li>
        <li>
          <gsmsg:write key="rss.3" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="rss.3" />-<gsmsg:write key="rss.rss020.1" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('moveRssData');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rssList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="rss.feedurl" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w100">
            <html:text name="rss020Form" property="rssFeedUrl" styleClass="wp500" maxlength="2000" />
          </td>
        </tr>
      </table>

      <div class="footerBtn_block mt5">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('moveRssData');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rssList');">
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