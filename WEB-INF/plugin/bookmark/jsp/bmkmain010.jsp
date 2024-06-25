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
<link rel=stylesheet href="../bookmark/css/bookmark.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bookmark/js/bmkmain010.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>
<html:form action="/bookmark/bmkmain010">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="bmkBackMainFlg" value="1">
<%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp" %>
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<div id="tooltips_bmk">
  <logic:equal name="bmkmain010Form" property="dspFlg" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.DSP_NO) %>">
    <!--非表示-->
  </logic:equal>
  <!-- 個人ブックマーク -->
  <logic:notEmpty name="bmkmain010Form" property="bmkMain010List">
    <logic:notEqual name="bmkmain010Form" property="dspFlg" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.DSP_NO) %>">
      <table class="table-top main_oddcol_table w100 mb0">
        <tr>
          <th class="table_title-color  txt_l border_right_none" colspan="2">
            <img class="mainPlugin_icon" src="../bookmark/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="bmk.43" />">
            <a href="<bean:write name="bmkmain010Form" property="bmkTopUrl" />">
              <gsmsg:write key="bmk.43" />
            </a>
            <span class="flo_r">
            <%if (originalTheme) { %>
              <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="return buttonPushSetting();">
                <gsmsg:write key="cmn.setting" />
              </button>
            <% } else { %>
              <input type="button" onclick="return buttonPushSetting();" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
            <% } %>
            </span>
          </th>
        </tr>
        <logic:iterate id="bmkMdl" name="bmkmain010Form" property="bmkMain010List" indexId="idx">
          <tr>
            <td colspan="2">
              <a title="" href="<bean:write name="bmkMdl" property="bmkUrl" />" target="_blank">
                <bean:write name="bmkMdl" property="bmkTitle" />
                <span class="tooltips"><bean:write name="bmkMdl" property="labelName"/><br><bean:write name="bmkMdl" property="bmkCmt"/></span>
              </a>
              <bean:define id="bmkMain010UsrCnt" name="bmkMdl" property="userCount" type="java.lang.Integer" />
              <a href="#!" onclick="return selPerCount('<bean:write name="bmkMdl" property="bmuSid" />');" class="main_bmkCount">
                <gsmsg:write key="bmk.23" arg0="<%= String.valueOf(bmkMain010UsrCnt.intValue()) %>" />
              </a>
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