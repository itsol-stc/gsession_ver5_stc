<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- ナビゲーション -->
<logic:notEmpty name="hlp000Form" property="menuInfoList">

<div align="center">

  <table style="width:100px;padding:0px;">
    <tr>
      <td>
        <ul>

          <logic:iterate id="menuInfo" name="hlp000Form" property="menuInfoList">
            <bean:define id="pluginId" name="menuInfo" property="pluginId" />
            <bean:define id="name" name="menuInfo" property="name" type="java.lang.String"/>
            <bean:define id="topUrl" name="menuInfo" property="url" />
            <bean:define id="imagesUrl" name="menuInfo" property="imagesUrl" />
            <% int strLen = name.length(); %>
            <% if (strLen == 7) { %>
            <%    name = name.substring(0, 4) + "<br/>" + name.substring(4); %>
            <% } else if (strLen >= 8) { %>
            <%    strLen = 8; %>
            <%    name = name.substring(0, 5) + "<br/>" + name.substring(5); %>
            <% } %>

            <li class="help-li">
              <a onClick="chengeHeaderText('[ <%= name %> ]'); linkClick('<%= topUrl %>');">
                <div class="menu_bg_help menu_text_help">
                  <table class="hp32">
                    <tr>
                      <td><img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img_help pb5" height="25" width="25" src="<%= imagesUrl %>/classic/menu_icon_single.gif" border="0" /></td>
                      <% if (strLen == 7) { %>
                      <td><table><tr><td class="str_7width"></td><td class="pb5 menu_text menu_text_position<%= strLen %>_help"><b><%= name %></b></td></tr></table></td>
                      <% } else { %>
                      <td class="pb5 menu_text menu_text_position<%= strLen %>"><b><%= name %></b></td>
                      <% } %>
                    </tr>
                  </table>
                </div>
              </a>
            </li>

          </logic:iterate>

        </ul>
      </td>
    </tr>
  </table>

</div>

</logic:notEmpty>