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
<script src="../portal/js/ptl081.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="ptl.ptl081.1" /></title>
</head>

<!-- BODY -->
<body onload="closeWindow();">

<html:form action="/portal/ptl081">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl030sortPortal" />
<html:hidden property="ptl081selectPortletSid" />
<html:hidden property="ptl081selectFlg" />

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_main.png">
      <img class="header_pluginImg" src="../main/images/original/header_main.png">
    </li>
    <li>
      <gsmsg:write key="cmn.main" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.ptl081.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w100">
  <div class="txt_l">
    <gsmsg:write key="ptl.ptl081.2" />
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.category.select" />
      </th>
      <td class="w80">
        <logic:notEmpty name="ptl081Form" property="ptl081CategoryList">
          <html:select property="ptl081Category" onchange="buttonPush('init');">
            <html:optionsCollection property="ptl081CategoryList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <table class="table-top w100">
    <tr>
      <th class="no_w w30">
        <gsmsg:write key="cmn.category.belong" />
      </th>
      <th class="no_w w70">
        <gsmsg:write key="ptl.3" />
      </th>
    </tr>
    <logic:notEmpty name="ptl081Form" property="ptl081dspList">
      <logic:iterate id="dspModel" name="ptl081Form" property="ptl081dspList" indexId="idx">
        <bean:define id="description" name="dspModel" property="pltDescription" />
        <%
          String tmpText = (String)pageContext.getAttribute("description", PageContext.PAGE_SCOPE);
          String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
        %>
        <tr class="js_listHover" data-ptlsid="<bean:write name="dspModel" property="pltSid" />">
          <td class="js_listClick cursor_p">
            <bean:write name="dspModel" property="plcName" />
          </td>
          <td class="js_listClick cursor_p js_content">
            <logic:notEmpty name="dspModel" property="pltDescription">
            <span class="js_nlTooltips">
              <gsmsg:write key="ptl.8" />:<%= tmpText2 %>
            </span>
              <span class="cl_linkDef">
                <bean:write name="dspModel" property="pltName" />
              </span>
            </logic:notEmpty>
            <logic:empty name="dspModel" property="pltDescription">
              <span class="cl_linkDef">
                <bean:write name="dspModel" property="pltName" />
              </span>
            </logic:empty>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
</body>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:html>