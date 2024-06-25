<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%-- プラグインポートレット「ファイル管理-ファイル一覧」キャビネット選択画面 --%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../file/js/filptl050.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="fil.ptl050.1" /></title>
</head>

<html:form action="/file/filptl050">
<!-- BODY -->
<body>

<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="filptl050selectFcbSid" />
<html:hidden property="filptl050selectDirSid" />

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png">
      <img class="header_pluginImg" src="../file/images/original/header_file.png">
    </li>
    <li>
      <gsmsg:write key="cmn.filekanri" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="fil.ptl050.1" />
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
<div class="wrapper w90 mrl_auto">
  <div class="txt_l">
    <gsmsg:write key="fil.ptl050.2" />
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.21" />
      </th>
      <td class="w80">
        <logic:notEmpty name="filptl050Form" property="filptl050PluginPortletList">
          <html:select property="ptl080PluginPortlet" onchange="buttonPush('filchangeCombo');">
            <html:optionsCollection property="filptl050PluginPortletList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <table class="table-top w100">
    <tr>
      <th class="no_w table_title-color">
        <gsmsg:write key="fil.23" />
      </th>
    </tr>
    <logic:notEmpty name="filptl050Form" property="filptlCabinetInfoList">
      <logic:iterate id="cabinetModel" name="filptl050Form" property="filptlCabinetInfoList" indexId="idx">
        <tr class="js_listHover" data-fcbsid="<bean:write name="cabinetModel" property="fcbSid" />" data-dirsid="<bean:write name="cabinetModel" property="rootDirSid" />">
          <td class="js_listClick cursor_p">
            <span class="cl_linkDef">
              <bean:write name="cabinetModel" property="fcbName" />
            </span>
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