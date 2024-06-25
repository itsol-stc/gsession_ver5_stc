<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String zskOn = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ZAISEKI_ON); %>
<% String zskOff = String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ZAISEKI_OFF); %>

<html:html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../timecard/js/tcdmain.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.50" /></title>
</head>

<body>
<html:form action="/timecard/tcdmain">
<input type="hidden" name="CMD" value="">
<logic:notEqual name="tcdmainForm" property="dspFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_NOT_DSP) %>">
<table class="table-top w100 mwp300 mb0">
  <tr>
    <th class="table_title-color w100 txt_l" colspan=2>
      <img class="mainPlugin_icon" src="../timecard/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="tcd.50" />">
      <a href="<bean:write name="tcdmainForm" property="tcdTopUrl" />">
        <gsmsg:write key="tcd.50" />
      </a>
    </th>
  </tr>
  <logic:equal name="tcdmainForm" property="tcdStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.STATUS_FREE) %>">
    <tr>
      <th class="w30 bgC_header2 cl_fontBody"><gsmsg:write key="tcd.tcdmain.03" /></th>
      <td class="w70 bgC_tableCell txt_c">
        <button type="button" class="baseBtn wp150 mt10 no_w" value="<gsmsg:write key="tcd.tcdmain.06" />" onClick="editTimecard('tcdEdit');">
          <gsmsg:write key="tcd.tcdmain.06" />
        </button>
        <br>
        <div class="txt_l mt0 wp150 verAlignMid no_w">
          <html:checkbox styleId="zsk_kbn" name="tcdmainForm" property="zaisekiSts" value="<%= zskOn %>" />
          <label for="zsk_kbn"><gsmsg:write key="tcd.tcdmain.04" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w30 bgC_header2 cl_fontBody"><gsmsg:write key="tcd.tcdmain.02" /></th>
      <td class="w70 bgC_tableCell">&nbsp;</td>
    </tr>
  </logic:equal>
  <logic:equal name="tcdmainForm" property="tcdStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.STATUS_FIN) %>">
    <tr>
      <th class="w30 bgC_header2 cl_fontBody"><gsmsg:write key="tcd.tcdmain.03" /></th>
      <td class="w70 bgC_tableCell txt_c fw_b"><bean:write name="tcdmainForm" property="tcdStartTime" /></td>
    </tr>
    <tr>
      <th class="w30 bgC_header2 cl_fontBody"><gsmsg:write key="tcd.tcdmain.02" /></th>
      <td class="w70 bgC_tableCell txt_c fw_b"><bean:write name="tcdmainForm" property="tcdStopTime" /></td>
    </tr>
  </logic:equal>
  <logic:equal name="tcdmainForm" property="tcdStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.STATUS_HAFE) %>">
    <tr>
      <th class="w30 bgC_header2 cl_fontBody"><gsmsg:write key="tcd.tcdmain.03" /></th>
      <td class="w70 bgC_tableCell txt_c fw_b"><bean:write name="tcdmainForm" property="tcdStartTime" /></td>
    </tr>
    <tr>
      <th class="w30 bgC_header2 cl_fontBody"><gsmsg:write key="tcd.tcdmain.02" /></th>
      <td class="w70 bgC_tableCell txt_c">
        <button type="button" class="baseBtn wp150 mt10 no_w" value="<gsmsg:write key="tcd.tcdmain.05" />" onClick="editTimecard('tcdEdit');">
          <gsmsg:write key="tcd.tcdmain.05" />
        </button>
        <br>
        <div class="txt_l mt0 wp150 verAlignMid no_w">
          <html:checkbox styleId="zsk_kbn" name="tcdmainForm" property="zaisekiSts" value="<%= zskOn %>" />
          <label for="zsk_kbn"><gsmsg:write key="tcd.tcdmain.01" /></label>
        </div>
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="tcdmainForm" property="tcdStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.STATUS_FAIL) %>">
    <tr>
      <td class="w100 bgC_tableCell txt_l fw_b"><gsmsg:write key="tcd.tcdmain.07" /></td>
    </tr>
  </logic:equal>
</table>
</logic:notEqual>
</html:form>
</body>
</html:html>