<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.fil040.FileDirectoryDspModel"%>
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
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="portal.portal" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmn380.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/filptl040.js?<%= GSConst.VERSION_PARAM %>"></script>

<bean:define id="filDirSid" name="filptl040Form" property="dspDirSid" type="java.lang.Integer" />
<bean:define id="cabinet" name="filptl040Form" property="dspFcbSid" type="java.lang.Integer" />

<% String filFormId = "filptl040Form" + String.valueOf(filDirSid.intValue()); %>
<% String filDspId = String.valueOf(filDirSid.intValue()); %>
<% String CabinetSid = String.valueOf(cabinet.intValue()); %>
</head>
<% java.util.List filTipList = new java.util.ArrayList(); %>
<body>
<html:form action="/file/filptl040" styleId="<%= filFormId %>">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="fileSid" value="">
<input type="hidden" name="backDsp" value="">
<input type="hidden" name="fil010SelectCabinet" value="">
<input type="hidden" name="fil010SelectDirSid" value="">
<html:hidden property="dspDirSid" />

<%--一覧--%>

<logic:notEmpty name="filptl040Form" property="directoryList">
  <table class="table-top table_col-even w100 mb0">
    <tr>
      <th class="table_title-color txt_l" colspan=3>
        <img class="mainPlugin_icon" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />">
        <a href="#!" onClick="MoveToDirectory('filptlSelectFolder','<%= CabinetSid %>', '<%= filDspId %>','<%= filFormId %>')">
         <gsmsg:write key="cmn.filekanri" /> [ <bean:write name="filptl040Form" property="filPtlDirName" /> ]
        </a>
      </th>
    </tr>
    <tr class="text_base2">
      <th class="w60 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap><gsmsg:write key="cmn.name4" /></th>
      <th class="w25 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap><gsmsg:write key="cmn.update.day.hour" /></th>
      <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap><gsmsg:write key="cmn.update.user" /></th>
    </tr>
    <logic:iterate id="filDirMdl" name="filptl040Form" property="directoryList" indexId="index">
      <%-- ディレクトリ区分=0：フォルダ --%>
      <logic:equal name="filDirMdl" property="fdrKbn" value="0">
        <tr class="js_listHover" data-fcbsid="<bean:write name="filDirMdl" property="fcbSid" />" data-fdrsid="<bean:write name="filDirMdl" property="fdrSid" />" data-formid="<%= filFormId %>">
          <%-- 名前 --%>
          <td class="txt_l js_listFil040FrdClick cursor_p">
            <span class="cl_linkDef verAlignMid">
              <span class="wp18">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_folder.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_folder_box.png">
              </span>
              <bean:write name="filDirMdl" property="fdrName" />
            </span>
          </td>
          <%-- 更新日時 --%>
          <td class="txt_c js_listFil040FrdClick cursor_p"><bean:write name="filDirMdl" property="edateString" /></td>
          <%-- 更新者 --%>
          <td class="txt_l js_listFil040FrdClick cursor_p">
            <logic:equal name="filDirMdl" property="upUsrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
              <s><bean:write name="filDirMdl" property="upUsrName" /></s>
            </logic:equal>
            <logic:notEqual name="filDirMdl" property="upUsrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
              <logic:equal name="filDirMdl" property="upUsrUkoFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.YUKOMUKO_MUKO) %>">
                <span class="mukoUser"><bean:write name="filDirMdl" property="upUsrName" /></span>
              </logic:equal>
              <logic:notEqual name="filDirMdl" property="upUsrUkoFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.YUKOMUKO_MUKO) %>">
                <bean:write name="filDirMdl" property="upUsrName" />
              </logic:notEqual>
            </logic:notEqual>
          </td>
        </tr>
      </logic:equal>
      <%-- ディレクトリ区分=1：ファイル --%>
      <logic:notEqual name="filDirMdl" property="fdrKbn" value="0">
        <bean:define id="fileName" name="filDirMdl" property="fdrName" />
        <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
        <tr class="js_listHover" data-fcbsid="<bean:write name="filDirMdl" property="fcbSid" />" data-sid="<bean:write name="filDirMdl" property="fileBinSid" />" data-formid="<%= filFormId %>" data-extension="<%= extension %>">
          <%-- 名前 --%>
          <td class="txt_l js_listFil040Click cursor_p">
            <span class="cl_linkDef">
              <img class="btn_classicImg-display" src="../file/images/classic/icon_file.gif">
              <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png">
              <bean:write name="filDirMdl" property="fdrName" />
              <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                <input type="hidden" name="binSid" value="<bean:write name="filDirMdl" property="fileBinSid" />" />
              <% } %>
            </span>
            <logic:equal name="filDirMdl" property="lockKbn" value="1">
              <logic:equal name="filptl040Form" property="admLockKbn" value="1">
                <br>
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png">
                <span class="cl_fontWarn fs_11"><gsmsg:write key="fil.fil040.5" />(<bean:write name="filDirMdl" property="lockUsrName" />)</span>
                <logic:notEmpty name="filDirMdl" property="lockDate">
                  <br><span class="fs_11"><bean:write name="filDirMdl" property="lockDate" />～</span>
                </logic:notEmpty>
              </logic:equal>
            </logic:equal>
          </td>
          <%-- 更新日時 --%>
          <td class="txt_c js_listFil040Click cursor_p"><bean:write name="filDirMdl" property="edateString" /></td>
          <%-- 更新者 --%>
          <td class="txt_l js_listFil040Click cursor_p">
            <logic:equal name="filDirMdl" property="upUsrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
              <s><bean:write name="filDirMdl" property="upUsrName" /></s>
            </logic:equal>
            <logic:notEqual name="filDirMdl" property="upUsrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
              <logic:equal name="filDirMdl" property="upUsrUkoFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.YUKOMUKO_MUKO) %>">
                <span class="mukoUser"><bean:write name="filDirMdl" property="upUsrName" /></span>
              </logic:equal>
              <logic:notEqual name="filDirMdl" property="upUsrUkoFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.YUKOMUKO_MUKO) %>">
                <bean:write name="filDirMdl" property="upUsrName" />
              </logic:notEqual>
            </logic:notEqual>
          </td>
        </tr>
      </logic:notEqual>
    </logic:iterate>
  </table>
</logic:notEmpty>

</html:form>


</body>
</html:html>