<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

    <!-- コンテンツ -->
    <div class="help-right">
      <!-- プラグイン情報 -->
      <div class="help-content-tr">
        <div class="help-content-tl">
          <div class="help-content-center" align="center">
            <table>
              <tr>
                <td class="help-content-tl-td">
                  <span class="help-header-text2"><gsmsg:write key="cmn.plugin.info" /></span>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>

    <div class="help-content-mr"><div class="help-content-ml pad">




<logic:notEmpty name="hlp000topForm" property="menuInfoList">
        <table class="help-content-m-table" summary="<gsmsg:write key="main.src.man250.16" /><gsmsg:write key="cmn.list" />" cellpadding="0" cellspacing="0">
<logic:iterate id="menuInfo" name="hlp000topForm" property="menuInfoList" indexId="idx">
  <% int index = idx.intValue(); %>

  <bean:define id="pluginId" name="menuInfo" property="pluginId" />
  <bean:define id="name" name="menuInfo" property="name" type="java.lang.String"/>
  <bean:define id="topUrl" name="menuInfo" property="url" />
  <bean:define id="imagesUrl" name="menuInfo" property="imagesUrl" />
  <bean:define id="description" name="menuInfo" property="description" />
  <% int strLen = name.length(); %>
  <% if (strLen == 7) { %>
  <%    name = name.substring(0, 4) + "<br/>" + name.substring(4); %>
  <% } else if (strLen >= 8) { %>
  <%    strLen = 8; %>
  <%    name = name.substring(0, 5) + "<br/>" + name.substring(5); %>
  <% } %>

<% if ((index % 2) == 0) { %>
<%     if (index != 0) { %>
        </tr>
<%     } %>
        <tr>
<% } %>
        <th>
          <a onClick="chengeHeaderText('[ <%= name %> ]'); linkClick('<%= topUrl %>');">
            <div class="menu_bg_help menu_text_help">
              <% if (strLen == 7) { %>
              <div style="float:left;"><img src="../../help/images/spacer.gif" width="3px" height="1px" alt=""></div>
              <% } %>
              <div style="float:left;"><img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img_help" height="25" width="25" src="<%= imagesUrl %>/menu_icon_single.gif" border="0" /></div>
              <% if (strLen == 7) { %>
              <div style="float:left;"><img src="../../help/images/spacer.gif" width="5px" height="1px" alt=""></div><div style="float:left;margin-top:3px"><div style="float:left;" class="menu_text_position<%= strLen %>_help"><b><%= name %></b></div>
              <% } else { %>
              <div style="float:left;margin-top:5px;" class="menu_text_position<%= strLen %>"><b><%= name %></b></div>
              <% } %>
            </div>
          </a>
        </th>
        <td>
<%= description %>
        </td>

</logic:iterate>
        </tr>
        </table>
</logic:notEmpty>

        <br>
        </div></div>
    <div class="help-content-br"><div class="help-content-bl"><img src="images/spacer.gif" width="1" height="15" alt=""></div></div>

    <div class="help-align-right"><a href="#top"><gsmsg:write key="cmn.top.back" /></a></div>
    </div>

