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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../portal/js/ptl140.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="ptl.2" /></title>
</head>

<!-- body -->
<body>
<html:form action="/portal/ptl140">
<input type="hidden" name="CMD" value="init">
<logic:notEmpty name="ptl140Form" property="ptl140portalList" scope="request">
  <logic:iterate id="sort" name="ptl140Form" property="ptl140portalList" scope="request">
    <input type="hidden" name="arrayPtl140sortPortal" value="<bean:write name="sort" property="strPtsSort" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div  class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />">
      <img class="header_pluginImg" src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.2" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('menuBack');">
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
    <html:errors/>
  </logic:messagesPresent>
  <logic:equal name="ptl140Form" property="ptlDefPow" value="1" >
    <table class="table-left w100">
      <tr>
        <th class="w20 no_w txt_c">
          <gsmsg:write key="ptl.18" />
        </th>
        <td class="w80">
          <div class="verAlignMid">
            <html:radio name="ptl140Form" styleClass="mr5" property="ptlType" value="0" styleId="ptlInitType0" onclick="buttonPush('changeKbn');" />
            <label for="ptlInitType0"><gsmsg:write key="ptl.19" /></label>
            <html:radio name="ptl140Form" styleClass="mr5 ml10" property="ptlType" value="1" styleId="ptlInitType1" onclick="buttonPush('changeKbn');" />
            <label for="ptlInitType1"><gsmsg:write key="ptl.20" /></label>
          </div>
        </td>
      </tr>
    </table>
  </logic:equal>
  <logic:equal name="ptl140Form" property="ptlSortPow" value="1" >
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('sortUp');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('sortDown');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="w5"></th>
      <th class="w35">
        <gsmsg:write key="ptl.4" />
      </th>
      <th class="w60">
        <gsmsg:write key="cmn.memo" />
      </th>
    </tr>
    <logic:iterate id="portalMdl" name="ptl140Form" property="ptl140portalList" indexId="idx">
      <bean:define id="ptlSid" name="portalMdl" property="ptlSid" type="java.lang.Integer" />
      <tr class="js_listHover js_listClick cursor_p">
        <td class="txt_c js_sord_radio">
          <html:radio property="ptl140sortPortal" value="<%= String.valueOf(ptlSid) %>" />
        </td>
        <td>
          <bean:write name="portalMdl" property="ptlName" />
        </td>
        <td>
          <bean:write name="portalMdl" property="ptlDescription" filter="false" />
        </td>
      </tr>
    </logic:iterate>
  </table>
  </logic:equal>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('menuBack');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>