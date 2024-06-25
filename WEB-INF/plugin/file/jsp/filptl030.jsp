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
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/filptl030.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>
<logic:notEmpty name="filptl030Form" property="cabinetList">
  <html:form action="/file/filptl030">
  <input type="hidden" name="CMD" value="">
  <input type="hidden" name="backDsp" value="">
  <input type="hidden" name="fil010SelectCabinet" value="">
  <input type="hidden" name="fil010SelectDirSid" value="">
  <table class="table-top table_col-even w100 mb0">
    <tr>
      <th class="table_title-color txt_l" colspan=2>
        <img class="mainPlugin_icon" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />-<gsmsg:write key="fil.1" />">
        <a href="../file/fil010.do"><gsmsg:write key="mainscreeninfo.file.filptl030" /></a>
      </th>
    </tr>
    <tr>
      <th class="bgC_header2 cl_fontBody no_w p0" colspan=2>
        <gsmsg:write key="fil.23" />
      </th>
    </tr>
    <logic:iterate id="cabBean" name="filptl030Form" property="cabinetList" indexId="index">
      <tr class="js_listHover cursor_p" data-cabsid="<bean:write name="cabBean" property="fcbSid" />" data-dirsid="<bean:write name="cabBean" property="rootDirSid" />">
        <td class="js_listFileClick p5 border_right_none">
          <logic:equal name="cabBean" property="fcbMark" value="0">
            <img class="btn_classicImg-display" src="../file/images/classic/icon_cabinet.gif">
            <img class="btn_originalImg-display" src="../file/images/original/icon_cabinet_32.png">
          </logic:equal>
          <logic:notEqual name="cabBean" property="fcbMark" value="0">
            <img name="iconImage" class="wp30hp30" src="../file/fil010.do?CMD=tempview&fil010SelectCabinet=<bean:write name="cabBean" property="fcbSid" />&fil010binSid=<bean:write name="cabBean" property="fcbMark" />" name="pictImage<bean:write name="cabBean" property="fcbMark" />" >
          </logic:notEqual>
        </td>
        <td class="js_listFileClick border_left_none w100">
          <span class="cl_linkDef"><bean:write name="cabBean" property="dspfcbName" filter="false" /></span>
          <logic:notEqual name="cabBean" property="accessIconKbn" value="1">
            <img class="btn_classicImg-display" src="../file/images/classic/18_icon_stop.png">
            <img class="btn_originalImg-display" src="../file/images/original/icon_stop.png">
          </logic:notEqual>
          <logic:equal name="cabBean" property="callIconKbn" value="1">
            <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif">
            <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png">
          </logic:equal>
          <logic:notEmpty name="cabBean" property="dspBikoString">
            <div class="fs_11">
              <bean:write name="cabBean" property="dspBikoString" filter="false"/>
            </div>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:iterate>
  </table>
  </html:form>
</logic:notEmpty>
</body>
</html:html>
