<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<% int tCmdAdd = jp.groupsession.v2.man.GSConstPortal.CMD_MODE_ADD; %>
<% int tCmdEdit = jp.groupsession.v2.man.GSConstPortal.CMD_MODE_EDIT; %>


<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../portal/js/ptl110.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="ptl.ptl110.1" /></title>
</head>

<!-- body -->
<body>
<html:form action="/portal/ptl110">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortletSid" />
<html:hidden property="ptlCmdMode" />

<html:hidden property="ptl090category" />
<html:hidden property="ptl090svCategory" />

<input type="hidden" name="ptlPlcBack" value="">
<input type="hidden" name="ptlPtlCategorytSid" value="">

<logic:notEmpty name="ptl110Form" property="ptl110categoryList" scope="request">
  <logic:iterate id="sort" name="ptl110Form" property="ptl110categoryList" scope="request">
    <input type="hidden" name="ptl110categorySort" value="<bean:write name="sort" property="strPlcSort" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- body -->
<div  class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.categorylist" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addEditCategoryWithFlg('0', '<%= tCmdAdd %>', 'ptl110add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush2('ptl110back');">
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
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush2('sortUp');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush2('sortDown');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="w5"></th>
      <th class="w95">
        <gsmsg:write key="cmn.category.name" />
      </th>
    </tr>
    <logic:iterate id="categoryMdl" name="ptl110Form" property="ptl110categoryList" indexId="idx">
      <bean:define id="plcSid" name="categoryMdl" property="plcSid" type="java.lang.Integer" />
      <% String strPlcSid = String.valueOf(plcSid); %>
      <tr class="js_listHover cursor_p" data-sid="<%= strPlcSid %>" data-edit="<%= tCmdEdit %>">
        <td class="txt_c js_sord_radio">
          <html:radio property="ptl110sortPltCategory" value="<%= strPlcSid %>" />
        </td>
        <td class="txt_l js_listClick cl_linkDef">
          <bean:write name="categoryMdl" property="plcName" />
        </td>
      </tr>
    </logic:iterate>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addEditCategoryWithFlg('0', '<%= tCmdAdd %>', 'ptl110add');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush2('ptl110back');">
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