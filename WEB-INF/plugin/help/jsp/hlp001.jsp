<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="cmn.help" /></title>
<theme:css filename="theme.css"/>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src='../common/js/jquery-1.5.2.min.js'></script>


<link rel=stylesheet href='../help/css/default.css' type='text/css'>
</head>
<frameset rows="70,*" cols="1" frameborder="no">
  <frame src="../help/hlp002.do?menuPage=<bean:write name="hlp001Form" property="menuPage" />&pluginid=<bean:write name="hlp001Form" property="pluginid" />&pgid=<bean:write name="hlp001Form" property="pgid" />" name="menu" scrolling="no" noresize="true">
  <frame src="<bean:write name="hlp001Form" property="url" />?pluginid=<bean:write name="hlp001Form" property="pluginid" />&pgid=<bean:write name="hlp001Form" property="pgid" />" name="body" scrolling="yes">
</frameset>
<noframes>
<gsmsg:write key="cmn.cmn002.1" />
</noframes>
</html:html>