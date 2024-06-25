<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve" %>

<%
String procMode       = String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT);
%>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="reserve.rsvmain.4" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../reserve/js/rsvmain.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<body>
<html:form action="/reserve/rsvmain">
<input type="hidden" name="CMD" value="">
<html:hidden property="dspDate" />
<html:hidden property="rsvmainSselectedYoyakuSid" />
<div id="tooltips_rsv">
<logic:notEmpty name="rsvmainForm" property="reservList" scope="request">
  <table class="table-top w100 mwp300 mb0">
    <tr>
      <th class="table_title-color txt_l border_right_none" colspan="5">
        <img src="../reserve/images/classic/menu_icon_single.gif" class="mainPlugin_icon" alt="<gsmsg:write key="cmn.project" />">
        <a href="<bean:write name="rsvmainForm" property="rsvTopUrl" />">
          <gsmsg:write key="reserve.rsvmain.1" />
        </a>
      </th>
    </tr>
    <tr>
      <th class="w20 p0 pl5 pr5 bgC_header2 cl_fontBody no_w" scope="col" nowrap>
        <gsmsg:write key="reserve.rsvmain.2" />
      </th>
      <th class="w35 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap>
        <gsmsg:write key="reserve.rsvmain.3" />
      </th>
      <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap>
        <gsmsg:write key="cmn.start" />
      </th>
      <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap>
        <gsmsg:write key="cmn.end" />
      </th>
      <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col" nowrap>
        <gsmsg:write key="reserve.73" />
      </th>
    </tr>
    <logic:iterate id="grpMdl" name="rsvmainForm" property="reservList" scope="request" indexId="idx">
      <logic:notEmpty name="grpMdl" property="sisetuList">
        <tr>
          <td class="p0 pl5 txt_l bgC_lightGray fw_b" colspan="5">
            <bean:write name="grpMdl" property="rsgName" />
          </td>
        </tr>
        <% String bgColor = ""; %>
        <logic:iterate id="rsvMdl" name="grpMdl" property="sisetuList" indexId="idy">
          <bean:define id="s_rsdSid" name="idy" type="java.lang.Integer" />
          <bean:define id="s_rsySid" name="rsvMdl" property="rsySisetuSid" type="java.lang.Integer" />

          <%
            if (bgColor != "bgC_tableCell") {
              bgColor = "bgC_tableCell";
            } else {
              bgColor = "bgC_tableCellEvn";
            }
          %>
          <logic:notEqual name="rsvMdl" property="public" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>">
            <logic:notEmpty name="rsvMdl" property="rsyBiko">
              <bean:define id="rsybico" name="rsvMdl" property="rsyBiko" />
              <%
                String tmpText = (String)pageContext.getAttribute("rsybico",PageContext.PAGE_SCOPE);
                String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
              %>
              <tr class="js_listHover js_mainTooltip  <%= bgColor %>" id="<bean:write name="rsvMdl" property="rsySisetuSid" />" data-tooltip="<gsmsg:write key="cmn.content" />:<%= tmpText2 %>">
            </logic:notEmpty>
            <logic:empty name="rsvMdl" property="rsyBiko">
              <tr class="js_listHover <%= bgColor %>" id="<bean:write name="rsvMdl" property="rsySisetuSid" />">
            </logic:empty>
              <td class="js_listRsvClick cursor_p">
                <bean:write name="rsvMdl" property="rsySisetu" />
              </td>
              <td class="js_listRsvClick cursor_p js_addTooltip">
                <logic:notEqual name="rsvMdl" property="public" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>">
                  <span class="cl_linkDef"><bean:write name="rsvMdl" property="rsyContent" /></span>
                </logic:notEqual>
                <logic:equal name="rsvMdl" property="public" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>">
                  <span class=""><bean:write name="rsvMdl" property="rsyContent" /></span>
                </logic:equal>
              </td>
              <td class="txt_c js_listRsvClick cursor_p">
               <bean:write name="rsvMdl" property="rsyFrom" />
              </td>
              <td class="txt_c js_listRsvClick cursor_p">
                <bean:write name="rsvMdl" property="rsyTo" />
              </td>
              <td class="js_listRsvClick cursor_p">
                <logic:equal name="rsvMdl" property="usrJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU) %>">
                  <bean:define id="mukoUserClass">textLink</bean:define>
                  <logic:equal name="rsvMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass">mukoUser</bean:define></logic:equal>
                  <input type="hidden" name="usrSid" value="<bean:write name="rsvMdl" property="rsyAuid"/>">
                  <span class="<%=mukoUserClass%> js_usrPop"><bean:write name="rsvMdl" property="rsySeiMei" /></span>
                </logic:equal>
                <logic:equal name="rsvMdl" property="usrJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                  <del><bean:write name="rsvMdl" property="rsySeiMei" /></del>
                </logic:equal>
              </td>
            </tr>
          </logic:notEqual>
          <logic:equal name="rsvMdl" property="public" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>">
            <tr class="<%= bgColor %>">
              <td>
                <bean:write name="rsvMdl" property="rsySisetu" />
              </td>
              <td class="js_addTooltip">
                <logic:notEqual name="rsvMdl" property="public" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>">
                  <span class="cl_linkDef"><bean:write name="rsvMdl" property="rsyContent" /></span>
                </logic:notEqual>
                <logic:equal name="rsvMdl" property="public" value="<%= String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS) %>">
                  <span class=""><bean:write name="rsvMdl" property="rsyContent" /></span>
                </logic:equal>
              </td>
              <td class="txt_c">
               <bean:write name="rsvMdl" property="rsyFrom" />
              </td>
              <td class="txt_c">
                <bean:write name="rsvMdl" property="rsyTo" />
              </td>
              <% String rsvUsrName = "js_usrPop cursor_p"; %>
              <logic:equal name="rsvMdl" property="usrJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                <% rsvUsrName = ""; %>
              </logic:equal>
              <td class="<%= rsvUsrName %>">
                <input type="hidden" name="usrSid" value="<bean:write name="rsvMdl" property="rsyAuid"/>">
                <logic:equal name="rsvMdl" property="usrJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU) %>">
                  <bean:define id="mukoUserClass">cl_linkDef</bean:define>
                  <logic:equal name="rsvMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass">mukoUser</bean:define></logic:equal>
                  <span class="<%=mukoUserClass%>"><bean:write name="rsvMdl" property="rsySeiMei" /></span>
                </logic:equal>
                <logic:equal name="rsvMdl" property="usrJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                  <del><bean:write name="rsvMdl" property="rsySeiMei" /></del>
                </logic:equal>
              </td>
            </tr>
          </logic:equal>
        </logic:iterate>
      </logic:notEmpty>
    </logic:iterate>
  </table>
</logic:notEmpty>
</div>
</html:form>
</body>
</html:html>