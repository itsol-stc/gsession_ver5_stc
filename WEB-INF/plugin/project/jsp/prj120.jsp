<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/readOnly.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../project/js/prj120.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body onload="freezeScreenParent('', false);parentReadOnly();">

<html:form action="/project/prj120">
<input type="hidden" name="CMD" value="">

<div class="txt_r">
  <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onClick="posClose();">
    <gsmsg:write key="cmn.cancel" />
  </button>
</div>
<div class="wrapper w100 mrl_auto">
  <table class="table-top">
    <tr>
      <th class="w35">
        <gsmsg:write key="project.31" />
      </th>
      <th class="w65">
        <gsmsg:write key="project.40" />
      </th>
    </tr>
    <logic:notEmpty name="prj120Form" property="projectList" scope="request">
    <logic:iterate id="prjMdl" name="prj120Form" property="projectList" scope="request" indexId="idx">
    <bean:define id="backclass" value="td_prj_list" />
    <bean:define id="backpat" value="<%= String.valueOf((idx.intValue() % 2) + 1) %>" />
    <bean:define id="back" value="<%= String.valueOf(backclass) + String.valueOf(backpat) %>" />
    <tr class="<%= String.valueOf(back) %> js_listHover js_listClick cursor_p" id="<bean:write name="prjMdl" property="projectSid" />">
      <td>
        <img class="classic-display" src="../project/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.project" />" name="pitctImage" class="wp20 mr5">
        <img class="original-display" src="../project/images/original/icon_project.png" alt="<gsmsg:write key="cmn.project" />" name="pitctImage" class="wp20 mr5">
        <bean:write name="prjMdl" property="projectId" />
      </td>
      <td>
        <img class="classic-display" src="../project/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.project" />" name="pitctImage" class="wp20 mr5">
        <img class="original-display" src="../project/images/original/icon_project.png" alt="<gsmsg:write key="cmn.project" />" name="pitctImage" class="wp20 mr5">
        <span class="cl_linkDef"><bean:write name="prjMdl" property="projectName" /></span>
      </td>
    </tr>
    </logic:iterate>
    </logic:notEmpty>
  </table>
</div>


</html:form>
</body>
</html:html>