<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConstRss" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%-- ソートキー --%>
<%
  String sortName          =  String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_SORT_NAME);
  String sortUserCount     =  String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_SORT_USER_COUNT);
  String sortLastUpdate    =  String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_SORT_LAST_UPDATE);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="rss.3" /><gsmsg:write key="rss.rss080.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../rss/js/rss080.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../rss/js/rssMemPopUp.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../rss/css/rss.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/rss/rss080">
<input type="hidden" name="CMD">
<html:hidden property="rss080orderKey" />
<html:hidden property="rss080sortKey" />
<html:hidden property="rssSid" />
<html:hidden property="rssTitle" />
<html:hidden property="backScreen" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rss.3" /></span><gsmsg:write key="rss.rss070.1" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAconf');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:notEmpty name="rss080Form" property="resultList">
        <bean:size id="count1" name="rss080Form" property="pageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">

          <div class="paging txt_r">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="rss080page1" onchange="changePage(0);">
              <html:optionsCollection name="rss080Form" property="pageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <bean:define id="sortKey" name="rss080Form" property="rss080sortKey" />
      <bean:define id="orderKey" name="rss080Form" property="rss080orderKey" />
      <% int iSortKey = ((Integer) sortKey).intValue();%>
      <% int iOrderKey = ((Integer) orderKey).intValue();%>
      <% int[] sortKeyList = GSConstRss.LIST_SORT_KEY_ALL; %>
      <% String[] title_width = new String[] { "w50", "w10", "w20"};%>
      <gsmsg:define id="rssName" msgkey="rss.14" />
      <gsmsg:define id="users" msgkey="rss.25" />
      <gsmsg:define id="lastUpdate" msgkey="cmn.last.modified" />
      <% String[] titleList = new String[] {rssName, users, lastUpdate};  %>

      <table class="table-top" cellpadding="0" cellspacing="0">
        <tr>

          <% int order_asc = GSConstRss.ORDER_KEY_ASC; %>
          <% int order_desc = GSConstRss.ORDER_KEY_DESC; %>
          <% for (int i = 0; i < sortKeyList.length; i++) {   %>
          <%   String title = titleList[i];                   %>
          <%   String skey = String.valueOf(sortKeyList[i]);  %>
          <%   String order = String.valueOf(order_asc);      %>
          <%   if (iSortKey == sortKeyList[i]) {              %>
          <%     if (iOrderKey == order_desc) {               %>
          <%       title = title + "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";%>
          <%     } else {                                     %>
          <%       title = title + "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";%>
          <%       order = String.valueOf(order_desc);        %>
          <%     }                                            %>
          <%   }                                              %>
          <th class="<%= title_width[i] %>">
            <%   if (iSortKey > 0) { %>
            <a href="#" onClick="return sort(<%= skey %>, <%= order %>);">
              <%   } %>
              <span>
                <%= title %>
              </span>
              <%
                if (iSortKey > 0) {
              %>
            </a>
            <%
              }
            %>
          </th>
          <%
            }
          %>
          <th class="w5"></th>

          <logic:notEmpty name="rss080Form" property="resultList">
            <logic:iterate name="rss080Form" property="resultList" scope="request" id="rssData" indexId="index">
              <tr>
                <td class="txt_l">
                  <span class="cl_linkDef">
                    <logic:equal name='rssData' property='rsdUrl' value=''>
                    <a href="#!">
                    </logic:equal>
                    <logic:notEqual name='rssData' property='rsdUrl' value=''>
                    <a href="<bean:write name='rssData' property='rsdUrl' />" target="_blank">
                    </logic:notEqual>
                      <bean:write name="rssData" property="rsdTitle" />
                    </a>
                  </span>
                  <br>
                  <span class="rss_description">
                    <bean:write name="rssData" property="rsdUrlFeed" />
                  </span>
                </td>
                <td class="txt_l">
                  <span>
                    <a href="javascript:openUsrWindow(<bean:write name="rssData" property="rssSid" />);">
                      <bean:write name="rssData" property="userCount" />users
                    </a>
                  </span>
                </td>

                <td class="txt_l">
                  <bean:write name="rssData" property="dspFeedUpdateTime" />
                </td>

                <td class="txt_c w5 no_w">
                  <button type="button" value="<gsmsg:write key="cmn.delete" />" onClick="return rssDel('<bean:write name="rssData" property="rssSid" />', '<bean:write name="rssData" property="rsdTitle" />');" class="baseBtn">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </tr>
      </table>

      <logic:notEmpty name="rss080Form" property="resultList">
        <bean:size id="count1" name="rss080Form" property="pageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging txt_r">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="rss080page2" onchange="changePage(1);">
              <html:optionsCollection name="rss080Form" property="pageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <div class="footerBtn_block mt10">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAconf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

</html:form>

<br class="clear">
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>