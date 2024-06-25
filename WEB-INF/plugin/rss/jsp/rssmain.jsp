<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="rss.rssmain.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<logic:empty name="rssmainForm" property="sidUpdate">
<body>
</logic:empty>
<logic:notEmpty name="rssmainForm" property="sidUpdate">
<body onload="rssUpdate();">
</logic:notEmpty>

<html:form action="/rss/rssmain">

<table class="table-top w100 m0 border_none bgC_none">
  <tr class="bgC_none">
    <td class="main_rss-left bgC_none">
      <% String addClass1 = ""; %>
      <logic:iterate id="flist" name="rssmainForm" property="flist" indexId="idx1">
        <logic:equal name="flist" property="feedPosition" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_POSITIONFLG_LEFT) %>">
          <table class="table-top main_oddcol_table w100 mwp150 mb0 <%= addClass1 %>">
            <tr>
              <th class="txt_l table_title-color" colspan="2">
                <img class="mainPlugin_icon" src="../rss/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="bbs.bbsMain.5" />">
                <logic:equal name='flist' property='furl' value=''>
                <a href="#!">
                </logic:equal>
                <logic:notEqual name='flist' property='furl' value=''>
                <a class="main_pluginTitle" target="_blank" href="<bean:write name="flist" property="furl" />">  
                </logic:notEqual>
                  <bean:write name="flist" property="ftitle" />
                </a>
              </th>
            </tr>
            <tr class="cl_linkVisit-content">
              <td>
              <bean:define id="feedList" name="flist" property="feedList" type="java.util.List" />
              <% if (feedList == null || feedList.size() == 0) { %>
                <div>
                  <a href="<bean:write name="flist" property="feedUrl" />" target="_blank" class="td_u"><bean:write name="flist" property="feedUrl" /></a>
                </div>
              <% } else { %>
                <logic:iterate id="feed1" name="flist" property="feedList" indexId="idx2">
                  <div>
                    <a class="cl_fontBody" href="#!" onClick="dispDescription('ds<bean:write name="idx1" />_<bean:write name="idx2" />');">
                      <img class="classic-display main_rssArrowIcon" src="../common/images/classic/icon_arrow_d.png">
                      <i class="fs_13 icon-down cursor_p btn_originalImg-display"></i>
                    </a>
                    <a href="<bean:write name="feed1" property="link" />" target="_blank" class="td_u"><bean:write name="feed1" property="title" /></a>
                    <a href="#!" onClick="rssSearch('<bean:write name="feed1" property="title" />');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_search_web.gif" alt="<gsmsg:write key="cmn.search" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_search_web.png" alt="<gsmsg:write key="cmn.search" />">
                    </a>
                  </div>
                  <div class="display_n"><bean:write name="feed1" property="author" /></div>
                  <!-- Description -->
                  <div id="ds<bean:write name="idx1" />_<bean:write name="idx2" />" class="display_n fs_10">
                    <div><bean:write name="feed1" property="publishedDate" /></div>
                    <logic:empty name="feed1" property="description">&nbsp;</logic:empty>
                    <logic:notEmpty name="feed1" property="description">
                      <bean:define id="description" name="feed1" property="description" />
                      <bean:write name="description" property="value" filter="false" />
                    </logic:notEmpty>
                  </div>
                </logic:iterate>
              <% } %>
              </td>
            </tr>
          </table>
          <% addClass1 = "mt10 mb0"; %>
        </logic:equal>
      </logic:iterate>
    </td>
    <td class="main_rss-right bgC_none">
      <% String addClass2 = ""; %>
      <logic:iterate id="flist" name="rssmainForm" property="flist" indexId="idx1">
        <logic:equal name="flist" property="feedPosition" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_POSITIONFLG_RIGHT) %>">
          <table class="table-top main_oddcol_table w100 mwp150 mb0 <%= addClass2 %>">
            <tr>
              <th class="txt_l table_title-color" colspan="2">
                <img class="classic-display" src="../rss/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="bbs.bbsMain.5" />">
                <logic:equal name='flist' property='furl' value=''>
                <a href="#!">
                </logic:equal>
                <logic:notEqual name='flist' property='furl' value=''>
                <a class="main_pluginTitle" target="_blank" href="<bean:write name="flist" property="furl" />">  
                </logic:notEqual>
                  <bean:write name="flist" property="ftitle" />
                </a>
              </th>
            </tr>
            <tr class="cl_linkVisit-content">
              <td>
              <bean:define id="feedList" name="flist" property="feedList" type="java.util.List" />
              <% if (feedList == null || feedList.size() == 0) { %>
                <div>
                  <a href="<bean:write name="flist" property="feedUrl" />" target="_blank" class="td_u"><bean:write name="flist" property="feedUrl" /></a>
                </div>
              <% } else { %>
                <logic:iterate id="feed1" name="flist" property="feedList" indexId="idx2">
                  <div>
                    <a class="cl_fontBody" href="#!" onClick="dispDescription('ds<bean:write name="idx1" />_<bean:write name="idx2" />');">
                      <img class="classic-display main_rssArrowIcon" src="../common/images/classic/icon_arrow_d.png">
                      <i class="fs_13 icon-down cursor_p btn_originalImg-display"></i>
                    </a>
                    <a href="<bean:write name="feed1" property="link" />" target="_blank" class="td_u"><bean:write name="feed1" property="title" /></a>
                    <a href="#!" onClick="rssSearch('<bean:write name="feed1" property="title" />');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_search_web.gif" alt="<gsmsg:write key="cmn.search" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_search_web.png" alt="<gsmsg:write key="cmn.search" />">
                    </a>
                  </div>
                  <div class="display_n"><bean:write name="feed1" property="author" /></div>
                  <!-- Description -->
                  <div id="ds<bean:write name="idx1" />_<bean:write name="idx2" />" class="display_n">
                    <div ><bean:write name="feed1" property="publishedDate" /></div>
                    <logic:empty name="feed1" property="description">&nbsp;</logic:empty>
                    <logic:notEmpty name="feed1" property="description">
                      <bean:define id="description" name="feed1" property="description" />
                      <bean:write name="description" property="value" filter="false" />
                    </logic:notEmpty>
                  </div>
                </logic:iterate>
              <% } %>
              </td>
            </tr>
          </table>
          <% addClass2 = "mt10 mb0"; %>
        </logic:equal>
      </logic:iterate>
    </td>
  </tr>
</table>


</html:form>

</body>
</html:html>