<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@ page import="jp.groupsession.v2.enq.enq010.Enq010Const"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>


<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="enq.enqmain.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enqmain.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<body>
<html:form action="/enquete/enqmain">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="enq110DspMode" value="0">
<input type="hidden" name="enq110BackTo" value="1">
<html:hidden property="ansEnqSid" />
<html:hidden property="enq010initFlg" />

<bean:define id="enqSortKey" name="enqmainForm" property="enq010sortKey" type="java.lang.Integer" />
<bean:define id="enqOrder" name="enqmainForm" property="enq010order" type="java.lang.Integer" />
<% String sortSign = ""; %>
<% String nextOrder = ""; %>
<logic:notEmpty name="enqmainForm" property="enq010EnqueteList">
  <table class="table-top table_col-even w100 mb0">
    <tr>
      <th class="txt_l table_title-color" colspan="4">
        <img class="mainPlugin_icon" class="mainPlugin_icon" src="../enquete/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="enq.plugin" />">
        <a class="main_pluginTitle" href="../enquete/enq010.do">
          <gsmsg:write key="enq.plugin" />
        </a>
      </th>
    </tr>
    <tr>
      <th class="w10 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="enq.24" /></th>
      <th class="w25 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="enq.25" /></th>
      <th class="w40 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="cmn.title" /></th>
      <th class="w25 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="enq.19" /></th>
    </tr>
    <logic:iterate id="enqData" name="enqmainForm" property="enq010EnqueteList" indexId="idx" type="jp.groupsession.v2.enq.enq010.Enq010EnqueteModel">
      <tr class="js_listHover" id="<bean:write name="enqData" property="enqSid" />">
        <td class="txt_c js_listEnqClick cursor_p no_w">
          <bean:define id="enqPriority" name="enqData" property="priority" type="java.lang.Integer" />
          <% if (enqPriority.intValue() == GSConstEnquete.JUUYOU_0) { %>
          <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
          <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
          <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
          <span class="original-display">
            <i class="icon-star importance-blue"></i>
            <i class="icon-star_line"></i>
            <i class="icon-star_line"></i>
          </span>
          <% } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_1) { %>
          <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
          <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
          <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
          <span class="original-display">
            <i class="icon-star importance-yellow"></i>
            <i class="icon-star importance-yellow"></i>
            <i class="icon-star_line"></i>
          </span>
          <% } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_2) { %>
          <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
          <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
          <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
          <span class="original-display">
            <i class="icon-star importance-red"></i>
            <i class="icon-star importance-red"></i>
            <i class="icon-star importance-red"></i>
          </span>
          <% } %>
        </td>
        <td class="js_listEnqClick cursor_p">
          <bean:define id="sdFlg" name="enqData" property="senderDelFlg" type="java.lang.Boolean" />
          <span class="<% if (sdFlg) { %> text_deluser_enq<% } else if (enqData.getSenderUkoFlg() == 1) { %> mukoUser<% } %>">
            <bean:write name="enqData" property="sender" />
          </span>
        </td>
        <td class="js_listEnqClick cursor_p">
          <logic:notEmpty name="enqData" property="typeName">
            <span class="baseLabel">
              <bean:write name="enqData" property="typeName" />
            </span>
          </logic:notEmpty>
          <logic:equal name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
            <span class="cl_linkVisit">
              <bean:write name="enqData" property="title" />
            </span>
          </logic:equal>
          <logic:notEqual name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
            <logic:equal name="enqData" property="canAnsFlg" value="<%= String.valueOf(Enq010Const.PUBLIC_ANSFLG_NG) %>">
              <bean:write name="enqData" property="title" />
            </logic:equal>
            <logic:notEqual name="enqData" property="canAnsFlg" value="<%= String.valueOf(Enq010Const.PUBLIC_ANSFLG_NG) %>">
              <span class="cl_linkDef">
                <bean:write name="enqData" property="title" />
              </span>
            </logic:notEqual>
          </logic:notEqual>
        </td>
        <td class="txt_c js_listEnqClick cursor_p">
          <bean:write name="enqData" property="ansLimitDate" />
        </td>
      </tr>
    </logic:iterate>
  </table>
</logic:notEmpty>
</html:form>

</body>
</html:html>