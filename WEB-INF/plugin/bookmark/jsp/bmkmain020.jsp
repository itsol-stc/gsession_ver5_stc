<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="bmk.24" />(<gsmsg:write key="cmn.main" />)</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../bookmark/js/bmkmain020.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<body>
<html:form action="/bookmark/bmkmain020">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="bmkBackMainFlg" value="1">
<%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp" %>
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<logic:equal name="bmkmain020Form" property="bmkmain020dspFlg" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.DSP_YES) %>">
  <logic:notEmpty name="bmkmain020Form" property="bmkmain020NewList">
    <table class="table-top main_oddcol_table w100 mb0">
      <tr>
        <th class="table_title-color txt_l" colspan="2">
          <img class="mainPlugin_icon" src="../bookmark/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="bmk.24" />">
          <a href="<bean:write name="bmkmain020Form" property="bmkTopUrl" />">
            <gsmsg:write key="bmk.24" />
          </a>
          <span class="flo_r">
          <%if (originalTheme) { %>
            <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="return buttonPushSetting();">
              <gsmsg:write key="cmn.setting" />
            </button>
          <% } else { %>
              <input type="button" onclick="return bmkmain020buttonPush('bmkmain020settei');" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
          <% } %>
          </span>
        </th>
      </tr>
      <logic:iterate id="newMdl" name="bmkmain020Form" property="bmkmain020NewList" indexId="idx">
        <tr>
          <!-- 追加ボタンありのときのtd -->
          <logic:equal name="newMdl" property="bmkMyKbn" value='<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO) %>'>
            <td class="border_right_none w95">
          </logic:equal>
          <!-- 追加ボタンなしのときのtd -->
          <logic:notEqual name="newMdl" property="bmkMyKbn" value='<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO) %>'>
            <td colspan="2" class="w100">
          </logic:notEqual>
          <!-- タイトル -->
          <a href="<bean:write name="newMdl" property="bmuUrl" />" target="_blank">
            <bean:write name="newMdl" property="viewBmuTitle" filter="false" />
<%--
            <logic:notEmpty name="newMdl" property="bmkTitleDspList">
              <logic:iterate id="titleDsp" name="newMdl" property="bmkTitleDspList" indexId="idx2">
                <% if (idx2 > 0) { %> <br> <% } %>
                <bean:write name="titleDsp" />
              </logic:iterate>
            </logic:notEmpty>
--%>
          </a>
          <!-- 人数 -->
          <bean:define id="bmkMain020UsrCnt" name="newMdl" property="bmkPerCount" type="java.lang.Integer" />
          <a href="#!" class="main_bmkCount" onclick="return bmkmain020selPerCount('<bean:write name="newMdl" property="bmuSid" />');">
            <gsmsg:write key="bmk.23" arg0="<%= String.valueOf(bmkMain020UsrCnt.intValue()) %>" />
          </a>
          </td>
          <!-- 追加ボタン -->
          <logic:equal name="newMdl" property="bmkMyKbn" value='<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO) %>'>
            <td class="txt_r w5 border_left_none">
              <button class="iconBtn-border" type="button" value="<gsmsg:write key="cmn.add" />" onClick="return bmkmain020buttonPushAdd('<bean:write name="newMdl" property="bmuSid" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png">
              </button>
            </td>
          </logic:equal>
        </tr>
      </logic:iterate>
      <tr class="js_listHover">
        <td class="js_listBmkListClick cursor_p txt_r bgC_tableCell p0 pr5" colspan="2">
          <span class="cl_linkDef"><gsmsg:write key="cmn.list" /></span>
        </td>
      </tr>
    </table>
  </logic:notEmpty>
</logic:equal>
</html:form>
</body>
</html:html>