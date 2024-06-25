<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%-- プラグインポートレット種別「ファイル管理-ファイル一覧」フォルダ情報選択画面 --%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../file/js/filptl060.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="fil.ptl050.1" /></title>
</head>

<!-- BODY -->
<body onload="closeWindow();">
<html:form action="/file/filptl060">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="filptl050selectFcbSid" />
<html:hidden property="filptl050selectDirSid" />
<html:hidden property="filptl060selectFlg" />

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
    <span class="flo_r">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('filptl060back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </span>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.21" />
      </th>
      <td class="w80">
        <logic:notEmpty name="filptl060Form" property="filptl050PluginPortletList">
          <html:select property="ptl080PluginPortlet" onchange="buttonPush('filchangeCombo');">
            <html:optionsCollection property="filptl050PluginPortletList" value="value" label="label"/>
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <table class="table-top w100">
    <tr>
      <th class="no_w table_title-color">
        <gsmsg:write key="cmn.folder" />
      </th>
    </tr>
    <tr>
      <th class="txt_l bgC_header2">
        <img class="btn_classicImg-display" src="../file/images/classic/icon_cabinet.gif">
        <img class="btn_originalImg-display" src="../file/images/original/icon_cabinet_32.png">
        <logic:notEmpty name="filptl060Form" property="filptl060DirectoryPathList" scope="request">
          <logic:iterate id="pathBean" name="filptl060Form" property="filptl060DirectoryPathList" scope="request" indexId="idx">
            <bean:size id="listSize" name="filptl060Form" property="filptl060DirectoryPathList" />
            <a href="javascript:(fileLinkClick('detailDir', <bean:write name="pathBean" property="fdrSid"/>, <bean:write name="idx"/>, <bean:write name="listSize"/>))" class="cl_linkDef"><bean:write name="pathBean" property="fdrName"/>/</a>
          </logic:iterate>
        </logic:notEmpty>
      </th>
    </tr>
    <logic:notEmpty name="filptl060Form" property="filptl060DirectoryList">
      <logic:iterate id="directoryModel" name="filptl060Form" property="filptl060DirectoryList" indexId="idx">
        <logic:equal name="directoryModel" property="fdrKbn" value="0">
          <tr class="js_listHover" data-fcbsid="<bean:write name="directoryModel" property="fcbSid" />" data-dirsid="<bean:write name="directoryModel" property="fdrSid" />">
            <td class="js_listClick cursor_p">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_box.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_folder_box.png">
              <span class="cl_linkDef">
                <bean:write name="directoryModel" property="fdrName" />
              </span>
            </td>
          </tr>
        </logic:equal>
        <logic:notEqual name="directoryModel" property="fdrKbn" value="0">
          <tr>
            <td>
              <img class="btn_classicImg-display" src="../file/images/classic/icon_file.gif">
              <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png">
              <bean:write name="directoryModel" property="fdrName" />
            </td>
          </tr>
        </logic:notEqual>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
</body>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:html>