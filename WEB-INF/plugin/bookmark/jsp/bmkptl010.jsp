<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="bmk.43" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bookmark/js/bmkptl010.js?<%= GSConst.VERSION_PARAM %>"></script>

<bean:define id="grpId" name="bmkptl010Form" property="bmkGrpSid" type="java.lang.Integer" />
<% String bmkFormId = "group" + String.valueOf(grpId.intValue()); %>

<bean:define id="pageNum" name="bmkptl010Form" property="bmkptl010page" type="java.lang.Integer" />
<% String bmkPage = String.valueOf(pageNum.intValue()); %>
</head>

<body>
<html:form action="/bookmark/bmkptl010" styleId="<%= bmkFormId %>">
<input type="hidden" name="CMD" value="">
<html:hidden property="bmkptl010ItemId" />
<html:hidden property="bmkGrpSid" />

<div id="tooltips_bmk">
<logic:notEmpty name="bmkptl010Form" property="bmkPtl010List">
<logic:notEqual name="bmkptl010Form" property="dspFlg" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.DSP_NO) %>">
<table class="table-top table_col-even w100 mb0">
  <tr>
    <th class="table_title-color txt_l" colspan=3>
      <img class="mainPlugin_icon" src="../bookmark/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
      <a href="<bean:write name="bmkptl010Form" property="bmkTopUrl" />">
        <gsmsg:write key="bmk.51" /><span class="pl5">[ <bean:write name="bmkptl010Form" property="groupName" /> ]</span>
      </a>
      <bean:size id="count1" name="bmkptl010Form" property="bmkptl010pageCmb" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging flo_r">
          <button type="button" class="webIconBtn" onClick="bmkptlClickArrow('prevPage', '<%= bmkFormId %>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left ptl_moveIcon"></i>
          </button>
          <select size="1" name="bmkptl010page" onchange="bmkptlChangePage('<%= bmkFormId %>');" class="paging_combo">
            <logic:iterate id="labelBean" name="bmkptl010Form" property="bmkptl010pageCmb" scope="request">
              <logic:equal name="labelBean" property="value" value="<%= bmkPage %>">
                <option value="<bean:write name="labelBean" property="value" />" selected="selected"><bean:write name="labelBean" property="label" /></option>
              </logic:equal>
              <logic:notEqual name="labelBean" property="value" value="<%= bmkPage %>">
                <option value="<bean:write name="labelBean" property="value" />"><bean:write name="labelBean" property="label" /></option>
              </logic:notEqual>
            </logic:iterate>
          </select>
          <button type="button" class="webIconBtn" onClick="bmkptlClickArrow('nextPage', '<%= bmkFormId %>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right ptl_moveIcon"></i>
          </button>
        </div>
      </logic:greaterThan>
    </th>
  </tr>
  <logic:iterate id="bmkMdl" name="bmkptl010Form" property="bmkPtl010List" indexId="idx">
    <tr class="js_listHover" data-url="<bean:write name="bmkMdl" property="bmuUrl" />">
      <td class="js_lisBmkPtlClick cursor_p" colspan="2">
        <span class="cl_linkDef"><bean:write name="bmkMdl" property="bmkTitle" /></span>
      </td>
    </tr>
  </logic:iterate>
</table>
</logic:notEqual>
</logic:notEmpty>
</div>
</html:form>
</body>
</html:html>