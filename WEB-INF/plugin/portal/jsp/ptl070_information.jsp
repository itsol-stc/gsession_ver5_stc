<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>

<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<!--<gsmsg:write key="cmn.information" />-->
<logic:empty name="ptl070Form" property="infoMsgs">
  <table class="table-top w100">
    <tr>
      <th class="table_title-color txt_l border_right_none no_w">
        <img src="../main/images/classic/menu_icon_single_info.gif" class="mainPlugin_icon" alt="<gsmsg:write key="cmn.information" />">
        <a class="main_pluginTitle" href="#!" onClick="javascript:buttonPush2('infoSetting');"><gsmsg:write key="main.4" /></a>
      </th>
      <th class="table_title-color txt_r border_left_none">
        <%if (originalTheme) { %>
          <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="buttonPush2('infoSetting');">
            <gsmsg:write key="cmn.setting" />
          </button>
        <% } else { %>
            <input type="button" onclick="buttonPush2('infoSetting');" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
        <% } %>
      </th>
    </tr>
  </table>
</logic:empty>
<logic:notEmpty name="ptl070Form" property="infoMsgs">
  <% String tableOddCol = ""; %>
  <%if (originalTheme) { %>
    <% tableOddCol = "main_oddcol_table"; %>
  <% } %>
  <table class="table-top w100">
    <tr>
      <th class="table_title-color txt_l border_right_none no_w">
        <img src="../main/images/classic/menu_icon_single_info.gif" class="mainPlugin_icon" alt="<gsmsg:write key="cmn.information" />">
        <a class="main_pluginTitle" href="#!" onClick="javascript:buttonPush2('infoSetting');"><gsmsg:write key="cmn.information" /></a>
      </th>
      <th class="table_title-color txt_r border_left_none">
        <%if (originalTheme) { %>
          <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="buttonPush2('infoSetting');">
            <gsmsg:write key="cmn.setting" />
          </button>
        <% } else { %>
          <input type="button" onclick="buttonPush2('infoSetting');" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
        <% } %>
      </th>
    </tr>
    <logic:iterate id="msg" name="ptl070Form" property="infoMsgs" scope="request" indexId="idx">
      <logic:equal name="msg" property="popupDsp" value="false">
        <tr class="js_listHover cursor_p" data-url="<bean:write name="msg" property="linkUrl" />">
          <td colspan="2" class="bgC_tableCell js_listInfoClick">
            <%if (originalTheme) { %>
              <logic:notEmpty name="msg" property="pluginName">
                <span class="info_pluginName"><bean:write name="msg" property="pluginName" /></span>
              </logic:notEmpty>
              <span class="cl_linkDef fw_b"><bean:write name="msg" property="originalMessage"/></span>
            <%} else { %>
              <logic:notEmpty name="msg" property="icon">
                <img src="<bean:write name="msg" property="icon" />" alt="<bean:write name="msg" property="message" />">
              </logic:notEmpty>
              <span class="cl_fontWarn fw_b"><bean:write name="msg" property="message"/></span>
            <%} %>
          </td>
        </tr>
      </logic:equal>
      <logic:notEqual name="msg" property="popupDsp" value="false">
        <tr class="js_listHover cursor_p" onclick="<bean:write name="msg" property="linkUrl" />">
          <td colspan="2" class="bgC_tableCell">
            <%if (originalTheme) { %>
              <logic:notEmpty name="msg" property="pluginName">
                <span class="info_pluginName"><bean:write name="msg" property="pluginName" /></span>
              </logic:notEmpty>
              <span class="cl_linkDef fw_b"><bean:write name="msg" property="originalMessage"/></span>
            <%} else { %>
              <logic:notEmpty name="msg" property="icon">
                <img src="<bean:write name="msg" property="icon" />" alt="<bean:write name="msg" property="message" />">
              </logic:notEmpty>
              <span class="cl_fontWarn fw_b"><bean:write name="msg" property="message"/></span>
            <%} %>
          </td>
        </tr>
      </logic:notEqual>
    </logic:iterate>
  </table>
</logic:notEmpty>