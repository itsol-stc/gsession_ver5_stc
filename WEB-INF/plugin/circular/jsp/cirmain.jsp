<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="cir.cir130.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="../circular/js/cirmain.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>
<body>
<html:form action="/circular/cirmain">
<input type="hidden" name="CMD" value="">
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cir010selectInfSid" />
<html:hidden property="cir010cmdMode" />
<logic:notEmpty name="cirmainForm" property="cir010CircularList" scope="request">
<table class="table-top table_col-even w100 mb0">
  <tr>
    <th class="txt_l table_title-color" colspan="4">
      <img class="mainPlugin_icon" src="../circular/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cir.5" />">
      <a href="<bean:write name="cirmainForm" property="cirTopUrl" />">
        <gsmsg:write key="cir.5" />
      </a>
    </th>
  </tr>
  <tr>
    <th class="w20 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.account" />
    </th>
    <th class="w40 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.title" />
    </th>
    <th class="w25 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.date" />
    </th>
    <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.sender" />
    </th>
  </tr>
  <logic:iterate id="cirMdl" name="cirmainForm" property="cir010CircularList" scope="request" indexId="idx">
    <%
      String titleFont = "";
    %>
    <logic:notEqual name="cirMdl" property="cvwConf" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_UNOPEN)%>">
      <%
        titleFont = "cl_linkVisit";
      %>
    </logic:notEqual>
    <tr class="js_listHover" id="<bean:write name="cirMdl" property="cacSid" />,<bean:write name="cirMdl" property="cifSid" />">
      <!-- 受信者 -->
      <td class="js_listCirClick cursor_p">
        <bean:define id="mukoUserClass" value="" />
        <logic:equal name="cirMdl" property="viewUkoFlg" value="1">
          <bean:define id="mukoUserClass">mukoUser</bean:define>
        </logic:equal>
        <span class="sc_ttl_def <%=mukoUserClass%>">
          <bean:write name="cirMdl" property="posName" />
        </span>
      </td>
      <!-- タイトル -->
      <td class="js_listCirClick  cursor_p cl_linkDef">
        <span class="<%=String.valueOf(titleFont)%>">
          <bean:write name="cirMdl" property="cifTitle" />
        </span>
      </td>
      <!-- 日付 -->
      <td class="txt_c  cursor_p js_listCirClick">
        <bean:write name="cirMdl" property="dspCifAdate" />
      </td>
      <!-- 発信者 -->
      <td class="js_listCirClick  cursor_p">
        <logic:equal name="cirMdl" property="cacJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
          <bean:define id="mukoUserClass" value="" />
          <logic:equal name="cirMdl" property="usrUkoFlg" value="1">
            <bean:define id="mukoUserClass">mukoUser</bean:define>
          </logic:equal>
          <span class="sc_ttl_def  <%=mukoUserClass%>">
            <bean:write name="cirMdl" property="cacName" />
          </span>
        </logic:equal>
        <logic:notEqual name="cirMdl" property="cacJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
          <del>
            <span class="sc_ttl_def">
              <bean:write name="cirMdl" property="cacName" />
            </span>
          </del>
        </logic:notEqual>
      </td>
    </tr>
  </logic:iterate>
</table>
</logic:notEmpty>
</html:form>
</body>
</html:html>