<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.preview" /></title>

<bean:define id="fileExtension" name="cmn380Form" property="cmn380PreviewFileExtension" type="java.lang.String" />
<%
  boolean pdfFlg = false, imgFlg = false;
  if (fileExtension != null) {
    fileExtension = fileExtension.toLowerCase();

    pdfFlg = fileExtension.equals("pdf");
    imgFlg = fileExtension.equals("jpg")
          || fileExtension.equals("jpeg")
          || fileExtension.equals("png");
  }
%>

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.12.1/jquery-ui.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn380.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<% if (imgFlg) { %>
<script src="../common/js/viewerjs/viewer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/js/viewerjs/viewer.min.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<% } %>

<theme:css filename="theme.css"/>

</head>

<% if (pdfFlg) { %>
<body class="m0 p0">
  <iframe class="w100 h100 pos_abs" src="../common/js/pdfjs-3.10.111/web/viewer.html?file=<bean:write name="cmn380Form" property="cmn380PreviewPdfURL" />"></iframe>
<% } else if (imgFlg) { %>
<body onload="cmn380LoadFile();">
  <div style="width: 880px; height: 880px;">
    <img id="imgPreview" class="pt5 cursor_p display_none" src="<bean:write name="cmn380Form" property="cmn380PreviewURL" />" />
  </div>
<% } %>
</body>
</html:html>