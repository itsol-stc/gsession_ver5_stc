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
<title>GROUPSESSION <gsmsg:write key="cmn.category.entry.kn" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>
<body class="body_03">
  <html:form action="/chat/cht150kn">
    <logic:equal name="cht150knForm" property="cht140ProcMode" value="0">
      <input type="hidden" name="helpPrm" value="0">
    </logic:equal>
    <logic:notEqual name="cht150knForm" property="cht140ProcMode" value="0">
      <input type="hidden" name="helpPrm" value="1">
    </logic:notEqual>

    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="cht010SelectPartner" />
    <html:hidden property="cht010SelectKbn" />
    <html:hidden property="cht140CategoryLink" />
    <html:hidden property="cht140ProcMode" />
    <html:hidden property="cht150InitFlg" />
    <html:hidden property="cht150CategorySid" />
    <html:hidden property="cht150CategoryName" />
    <logic:notEmpty name="cht150knForm" property="cht150ChtGroupSid">
      <logic:iterate id="grpSid" name="cht150Form" property="cht150ChtGroupSid">
        <input type="hidden" name="cht150ChtGroupSid" value="<bean:write name="grpSid" />">
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
      <logic:equal name="cht150Form" property="cht140ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_ADD)%>">
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cmn.category.entry.kn" />
    </li>
    </logic:equal>
    <logic:equal name="cht150Form" property="cht140ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_EDIT)%>">
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="rng.04" /><gsmsg:write key="cmn.check" />
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
        <gsmsg:write key="cmn.category.name" />
      </th>
      <td class="w75">
        <bean:write name="cht150Form" property="cht150CategoryName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cht.cht150.01" />
      </th>
      <td>
        <logic:notEmpty name="cht150knForm" property="cht150knGrpNameList">
          <logic:iterate id="grpName" name="cht150knForm" property="cht150knGrpNameList">
            <bean:write name="grpName" /><br>
          </logic:iterate>
        </logic:notEmpty>
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