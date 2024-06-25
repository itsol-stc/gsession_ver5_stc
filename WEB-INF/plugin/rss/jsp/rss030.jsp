<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
  String show = String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_MAIN_VIEWFLG_SHOW);
  String notshow = String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_MAIN_VIEWFLG_NOTSHOW);
  String id_show = "show" + show;
  String id_notshow = "show" + notshow;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="rss.rss030.1" /></title>
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

<logic:equal name="rss030Form" property="rssCmdMode" value="1">
  <!-- body class="body_03" onload="initDispDescription(<bean:write name='rss030Form' property='rssAuth' />);" -->
  <body class="body_03">
</logic:equal>
<logic:notEqual name="rss030Form" property="rssCmdMode" value="1">
  <body class="body_03">
</logic:notEqual>
<html:form action="/rss/rss030">

  <input type="hidden" name="CMD" value="">
  <html:hidden name="rss030Form" property="rssCmdMode" />
  <html:hidden name="rss030Form" property="rssSid" />
  <html:hidden name="rss030Form" property="rssBeforeFeedUrl" />

  <logic:notEqual name="rss030Form" property="rssCmdMode" value="1">
    <html:hidden name="rss030Form" property="rssAuth" />
    <html:hidden name="rss030Form" property="rssAuthId" />
    <html:hidden name="rss030Form" property="rssAuthPswd" />
  </logic:notEqual>


  <input type="hidden" name="helpPrm" value="<bean:write name='rss030Form' property='helpMode' />">

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
        <gsmsg:write key="rss.3" />
        -
        <gsmsg:write key="rss.rss030.5" />
      </li>
      <li>
        <div>
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('rssConfirm');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <logic:equal name="rss030Form" property="rssCmdMode" value="1">
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delRss');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backRssList');">
              </td>
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:equal>
          <logic:notEqual name="rss030Form" property="rssCmdMode" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backFeedUrl');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:notEqual>
        </div>
      </li>
    </ul>
  </div>
  <div class="wrapper w80 mrl_auto">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <table class="table-left" border="0" cellpadding="5">
      <tr>
        <th class="no_w">
          <span>
            <gsmsg:write key="rss.14" />
          </span>
          <span class="cl_fontWarn">※</span>
        </th>
        <td class="txt_l">
          <html:text name="rss030Form" property="rssTitle" styleClass="wp500" maxlength="50" />
        </td>
      </tr>

      <tr>
        <th class="no_w">
          <span>
            <gsmsg:write key="rss.feedurl" />
          </span>
          <span class="cl_fontWarn">※</span>
        </th>
        <td class="txt_l">
          <html:text name="rss030Form" property="rssFeedUrl" styleClass="wp500" maxlength="2000" />
        </td>
      </tr>

      <tr>
        <th class="no_w">
          <span>
            <gsmsg:write key="rss.16" />
          </span>
          <span class="cl_fontWarn">※</span>
        </th>
        <td class="txt_l">
          <html:text name="rss030Form" property="rssUrl" styleClass="wp500" maxlength="2000" />
        </td>
      </tr>

      <tr>
        <th class="no_w">
          <span>
            <gsmsg:write key="cmn.number.display" />
          </span>
        </th>
        <td class="txt_l">
          <html:select property="rss030ViewCnt">
            <html:optionsCollection name="rss030Form" property="viewCntList" value="value" label="label" />
          </html:select>
        </td>
      </tr>

      <tr>
        <th class="no_w">
          <span>
            <gsmsg:write key="cmn.main.view" />
          </span>
        </th>
        <td class="txt_l">
          <span class="verAlignMid">
          <html:radio property="rss030mainView" styleId="<%=id_show%>" value="<%=show%>" />
            <label for="<%=id_show%>">
              <gsmsg:write key="cmn.show" />
            </label>
          <html:radio property="rss030mainView" styleClass="ml10" styleId="<%=id_notshow%>" value="<%=notshow%>" />
            <label for="<%=id_notshow%>">
              <gsmsg:write key="cmn.hide" />
            </label>
          </span>
        </td>
      </tr>
    </table>

    <div class="footerBtn_block mt5">
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('rssConfirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="rss030Form" property="rssCmdMode" value="1">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delRss');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backRssList');">
            </td>
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
        <logic:notEqual name="rss030Form" property="rssCmdMode" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backFeedUrl');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>
      </div>
    </div>
  </div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>