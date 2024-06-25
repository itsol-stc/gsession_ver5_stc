<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.cht140.01" /></title>

<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script language="JavaScript" src='../chat/js/cht140.js?<%=GSConst.VERSION_PARAM%>'></script>

</head>

<body>

<html:form action="/chat/cht140">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="cht140CategoryLink" />
<html:hidden property="cht140ProcMode" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht140.01" />
    </li>
    <li>
      <div>
       <button type="button" value="<gsmsg:write key="cht.cht100.02" />" class="baseBtn" onClick="addEditCategory('addEdit',-1,0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.category" /><gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToAdmin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>


<div class="wrapper w80 mrl_auto">
  <div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>


  <table class="table-top w100">
    <tr>
      <th class="table_title-color">
        <gsmsg:write key="cmn.category" />
      </th>
    </tr>

    <logic:iterate id="categoryData" name="cht140Form" property="cht140CategoryList">
      <tr class="js_listHover cursor_p" id="<bean:write name="categoryData" property="chcSid" />">
        <td class="js_list_Click cl_linkDef">
          <span class="cl_linkDef"><bean:write name="categoryData" property="chcName"/></span>
        </td>
      </tr>
    </logic:iterate>
  </table>

  <div class="footerBtn_block">
   <button type="button" value="<gsmsg:write key="cht.cht100.02" />" class="baseBtn" onClick="addEditCategory('addEdit',-1,0);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.category" /><gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToAdmin');">
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