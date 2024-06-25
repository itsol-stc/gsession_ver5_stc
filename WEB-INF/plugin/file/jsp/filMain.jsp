<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn380.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/filMain.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>
<html:form action="/file/filMain">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="backDsp" value="">
<input type="hidden" name="backDspLow" value="">
<input type="hidden" name="backDspCall" value="">
<input type="hidden" name="backMainFlg" value="">
<input type="hidden" name="fileSid" value="">
<input type="hidden" name="fil010SelectCabinet" value="">
<input type="hidden" name="fil010SelectDirSid" value="">
<input type="hidden" name="fil050DirSid" value="">
<input type="hidden" name="fil070DirSid" value="">
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<logic:notEmpty name="filMainForm" property="shortcutList">
<% String spaceClass = ""; %>
<logic:notEmpty name="filMainForm" property="callList">
  <% spaceClass = "mb10"; %>
</logic:notEmpty>
<table class="table-top main_oddcol_table w100 mb0 <%= spaceClass %>">
  <tr>
    <th class="table_title-color txt_l" colspan=4>
      <img class="mainPlugin_icon" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />-<gsmsg:write key="fil.2" />">
      <a href="<bean:write name="filMainForm" property="filTopUrl" />">
        <gsmsg:write key="cmn.filekanri" />-<gsmsg:write key="fil.2" />
      </a>
    </th>
  </tr>
  <logic:iterate id="shortBean" name="filMainForm" property="shortcutList" scope="request" indexId="idx">
    <logic:equal name="shortBean" property="directoryKbn" value="0">
      <tr class="js_listHover" data-cabsid="<bean:write name="shortBean" property="cabinetSid" />" data-dirsid="<bean:write name="shortBean" property="directorySid" />">
        <td class="js_listFileScClick cursor_p w100">
          <img class="btn_classicImg-display" src="../file/images/classic/icon_shortcut_folder.gif" alt="<gsmsg:write key="cmn.folder" />">
          <img class="btn_originalImg-display" src="../file/images/original/icon_folder_shortcut.png" alt="<gsmsg:write key="cmn.folder" />">
          <span class="cl_linkDef"><bean:write name="shortBean" property="directoryFullPathName" /></span>
        </td>
      </tr>
    </logic:equal>
    <logic:equal name="shortBean" property="directoryKbn" value="1">
      <tr class="js_listHover" data-binsid="<bean:write name="shortBean" property="binSid" />">
        <td class="js_listFileDlClick cursor_p w100">
          <img class="btn_classicImg-display" src="../file/images/classic/icon_shortcut_file.png" alt="<gsmsg:write key="cmn.file" />">
          <img class="btn_originalImg-display" src="../file/images/original/icon_file_shortcut.png" alt="<gsmsg:write key="cmn.file" />">
          <span class="cl_linkDef"><bean:write name="shortBean" property="directoryFullPathName" /></span>
          <bean:define id="fileName" name="shortBean" property="directoryName" />
          <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
          <bean:define id="fileSid" name="shortBean" property="binSid" />
          <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
          <span class="ml5 cursor_p" onclick="openPreviewWindow('../file/filMain.do?CMD=fileDownloadInline&fileSid=<%= fileSid %>', '<%= extension %>');stopDownload(event);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
          </span>
          <% } %>
        </td>
      </tr>
    </logic:equal>
  </logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="filMainForm" property="callList">
<table class="table-top main_oddcol_table w100 mb0">
  <tr>
    <th class="table_title-color txt_l" colspan=4>
      <img class="mainPlugin_icon" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />-<gsmsg:write key="fil.1" />">
      <a href="<bean:write name="filMainForm" property="filTopUrl" />">
        <gsmsg:write key="cmn.filekanri" />-<gsmsg:write key="fil.1" />
      </a>
      <span class="flo_r">
      <%if (originalTheme) { %>
        <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="MoveToPconf();">
          <gsmsg:write key="cmn.setting" />
        </button>
      <% } else { %>
        <input type="button" onclick="MoveToPconf();" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
      <% } %>
      </span>
    </th>
  </tr>
  <logic:iterate id="callBean" name="filMainForm" property="callList" scope="request" indexId="idx">
    <logic:equal name="callBean" property="directoryKbn" value="0">
      <tr class="js_listHover" data-cabsid="<bean:write name="callBean" property="cabinetSid" />" data-dirsid="<bean:write name="callBean" property="directorySid" />">
        <td class="js_listFileUpdFolderClick cursor_p w10 border_right_none">
          <%if (originalTheme) { %>
            <span class="labelNew"><gsmsg:write key="bbs.bbsMain.6" /></span>
          <% } else { %>
            <img class="btn_classicImg-display" src="../file/images/classic/icon_new_folder.gif">
          <% } %>
        </td>
        <td class="js_listFileUpdFolderClick cursor_p txt_l border_left_none w100">
          <span class="cl_linkDef"><bean:write name="callBean" property="directoryFullPathName" /></span>
          <br>
          <gsmsg:write key="cmn.update.day.hour" />：<bean:write name="callBean" property="directoryUpdateStr" />
        </td>
      </tr>
    </logic:equal>
    <logic:notEqual name="callBean" property="directoryKbn" value="0">
      <tr class="js_listHover" data-cabsid="<bean:write name="callBean" property="cabinetSid" />" data-dirsid="<bean:write name="callBean" property="directorySid" />">
        <td class="js_listFileUpdFileClick cursor_p w10 border_right_none">
          <%if (originalTheme) { %>
            <span class="labelNew"><gsmsg:write key="bbs.bbsMain.6" /></span>
          <% } else { %>
            <img class="btn_classicImg-display" src="../file/images/classic/icon_new_file.gif" alt="">
          <% } %>
        </td>
        <td class="js_listFileUpdFileClick cursor_p txt_l border_left_none w100">
          <span class="cl_linkDef"><bean:write name="callBean" property="directoryFullPathName" /></span>
          <bean:define id="fileName" name="callBean" property="directoryName" />
          <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
          <bean:define id="fileSid" name="callBean" property="binSid" />
          <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
          <span class="ml5 cursor_p" onclick="openPreviewWindow('../file/filMain.do?CMD=fileDownloadInline&fileSid=<%= fileSid %>', '<%= extension %>');stopDownload(event);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
          </span>
          <% } %>
          <br>
          <gsmsg:write key="cmn.update.day.hour" />：<bean:write name="callBean" property="directoryUpdateStr" />
        </td>
      </tr>
    </logic:notEqual>
  </logic:iterate>
  <tr class="js_listHover">
    <td class="js_listFileListClick cursor_p txt_r bgC_tableCell p0 pr5" colspan="2">
      <span class="cl_linkDef"><gsmsg:write key="cmn.list" /></span>
    </td>
  </tr>
</table>

</logic:notEmpty>

</html:form>
</body>
</html:html>