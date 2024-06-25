<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html;" charset="UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.plugin" /> <gsmsg:write key="cmn.display.settings" /><gsmsg:write key="cmn.check" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp040kn">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp040UserSid" />
    <html:hidden property="anp040MainDispFlg" />
    <html:hidden property="anp040SelectDispCnt" />
    <html:hidden property="anp040SelectGroupSid" />
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.preferences2" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="cmn.display.settings" /><gsmsg:write key="cmn.check" />
        </li>
        <li>
          <!-- ボタン -->
          <div>
            <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('anp040knexcute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <gsmsg:write key="cmn.final" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp040knback');">
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
          <html:errors />
        </logic:messagesPresent>
      </div>
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.main.view2" />
          </th>
          <td class="w75">
            <logic:equal name="anp040knForm" property="anp040MainDispFlg" value="1">
              <gsmsg:write key="cmn.display.ok" />
            </logic:equal>
            <logic:notEqual name="anp040knForm" property="anp040MainDispFlg" value="1">
              <gsmsg:write key="cmn.dont.show" />
            </logic:notEqual>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="anp.number.display" />
          </th>
          <td>
            <bean:write name="anp040Form" property="anp040SelectDispCnt" />
            <gsmsg:write key="cmn.number" />
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="anp.anp040.02" />
          </th>
          <td>
            <bean:write name="anp040knForm" property="anp040knDispGrpNm" />
          </td>
        </tr>
      </table>
      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('anp040knexcute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp040knback');">
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