<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
  String sts_sonota      = String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC);
  String sts_zaiseki     = String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN);
  String sts_huzai       = String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE);
%>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../zaiseki/js/zskmain.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>
<html:form action="/zaiseki/zskmain">
<input type="hidden" name="CMD" value="zskEdit">
<input type="hidden" name="SONOTA" value="<%= sts_sonota %>">
<input type="hidden" name="ZAISEKI" value="<%= sts_zaiseki %>">
<input type="hidden" name="HUZAI" value="<%= sts_huzai %>">
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<table class="table-top w100 mb0">
  <tr>
    <%if (originalTheme) { %>
    <th class="txt_l table_title-color w100">
      <a href="<bean:write name="zskmainForm" property="zskTopUrl" />">
        <gsmsg:write key="zsk.zskmain.01" />
      </a>
      <span class="flo_r">
        <logic:equal name="zskmainForm" property="zskUioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
          <span class="main_zskLadio-zaiseki bor1 borC_light"><gsmsg:write key="cmn.zaiseki2" /></span>
        </logic:equal>
        <logic:equal name="zskmainForm" property="zskUioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
          <span class="main_zskLadio-huzai bor1 borC_light"><gsmsg:write key="cmn.absence2" /></span>
        </logic:equal>
        <logic:equal name="zskmainForm" property="zskUioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
          <span class="main_zskLadio-sonota bor1 borC_light"><gsmsg:write key="cmn.other" /></span>
        </logic:equal>
      </span>
    </th>
    <% } else { %>
    <th class="txt_l table_title-color w100">
      <img class="mainPlugin_icon" src="../zaiseki/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="zsk.zskmain.01" />">
      <a href="<bean:write name="zskmainForm" property="zskTopUrl" />">
        <gsmsg:write key="zsk.zskmain.01" />
      </a>
    </th>
    <% } %>
  </tr>
  <tr class="no_w">
    <logic:equal name="zskmainForm" property="zskUioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
      <bean:define id="zskColor" value="bgC_zaiseki" />
    </logic:equal>
    <logic:equal name="zskmainForm" property="zskUioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
      <bean:define id="zskColor" value="bgC_huzai" />
    </logic:equal>
    <logic:equal name="zskmainForm" property="zskUioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
      <bean:define id="zskColor" value="bgC_zskOther" />
    </logic:equal>
    <%if (originalTheme) { %>
      <td class="txt_l w100">
    <% } else { %>
      <td class="txt_l w100 <bean:write name="zskColor" />">
    <% } %>
      <div class="w100 mt5">
        <html:radio name="zskmainForm" property="zskUioStatus" styleId="sts_zaiseki" value="<%= sts_zaiseki %>"/>
        <span class="cal_colHeader-zaiseki main_zskLabel status_frame-base borC_deep"><label for="sts_zaiseki"><gsmsg:write key="cmn.zaiseki" /></label></span>
        <html:radio name="zskmainForm" property="zskUioStatus" styleId="sts_huzai" styleClass="ml5" value="<%= sts_huzai %>"/>
        <span class="cal_colHeader-huzai main_zskLabel status_frame-base borC_deep"><label for="sts_huzai"><gsmsg:write key="cmn.absence" /></label></span>
        <html:radio name="zskmainForm" property="zskUioStatus" styleId="sts_sonota" styleClass="ml5" value="<%= sts_sonota %>"/>
        <span class="cal_colHeader-sonota main_zskLabel status_frame-base borC_deep"><label for="sts_sonota"><gsmsg:write key="cmn.other" /></label></span>
      </div>
      <div class="verAlignMid mt5">
        <html:text name="zskmainForm" property="zskUioBiko"  maxlength="30" styleClass="wp200 ml0" />
        <button class="baseBtn ml10" type="button" onClick="updateZsk('zskEdit');">
          <gsmsg:write key="cmn.change" />
        </button>
      </div>
    </td>
  </tr>
</table>

</html:form>

</body>
</html:html>