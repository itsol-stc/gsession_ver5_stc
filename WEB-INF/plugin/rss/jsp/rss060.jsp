<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="rss.3" /> <gsmsg:write key="cmn.setting.main.view2" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/search.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.sortable.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../rss/js/rss060.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../rss/css/rss.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>

<html:form action="/rss/rss060">
  <input type="hidden" name="CMD" value="search">
  <html:hidden property="backScreen" />
  <input type="hidden" name="rssSid" value="">
  <input type="hidden" name="rssTitle" value="">
  <input type="hidden" name="rssCmdMode" value="0">

  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

  <div class="kanriPageTitle w80 mrl_auto">
    <ul>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li>
        <gsmsg:write key="cmn.preferences2" />
      </li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin">
          <gsmsg:write key="rss.3" />
        </span>
        <gsmsg:write key="cmn.setting.main.view" />
      </li>
      <li>
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backpconf');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </li>
    </ul>
  </div>
  <div class="wrapper w80 mrl_auto">
    <div class="txt_l"><span class="cl_fontWarn"><gsmsg:write key="rss.rss060.1" /></span></div>

    <table class="w100">
      <tr>
        <td class="column txt_t w50" id="rssListLeft" >

          <!-- 左コンテンツを表示 -->
          <logic:iterate id="flist" name="rss060Form" property="rss060Flist" indexId="idx1">
            <logic:equal name="flist" property="feedPosition" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_POSITIONFLG_LEFT)%>">
              <!-- RSS取得結果  -->
              <div class="portlet mr5" id="<bean:write name="flist" property="feedSid" />">
                <table class="table-top" cellpadding="0" cellspacing="0" border="0">
                  <tr>
                    <th class="txt_l table_title-color">
                      <img class="classic-display" src="../rss/images/classic/menu_icon_single.gif">
                        <logic:equal name='flist' property='furl' value=''>
                        <a href="#!">
                        </logic:equal>
                        <logic:notEqual name='flist' property='furl' value=''>
                        <a href="<bean:write name='flist' property='furl' />" target="_blank">  
                        </logic:notEqual>
                          <bean:write name="flist" property="ftitle" />
                        </a>
                    </th>
                  </tr>
                  <tr>
                    <td colspan="2" class="rsswin_body">
                      <logic:empty name="flist" property="feedList">
                        <div class="">
                          <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                            <bean:write name="flist" property="feedUrl" />
                          </a>
                        </div>
                      </logic:empty>
                      <logic:notEmpty name="flist" property="feedList">
                        <bean:define id="feedList" name="flist" property="feedList" type="java.util.List" />
                        <%
                          if (feedList.size() == 0) {
                        %>
                        <div>
                          <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                            <bean:write name="flist" property="feedUrl" />
                          </a>
                        </div>

                        <%
                          } else {
                        %>

                        <logic:iterate id="feed1" name="flist" property="feedList" indexId="idx2">
                          <div id="<bean:write name="idx1" />_<bean:write name="idx2" />">
                            <div class="fs_14 txt_b">
                              <!-- 要約表示切替 -->
                              <span onClick="dispDescription('ds<bean:write name="idx1" />_<bean:write name="idx2" />');">
                                <img class="classic-display wp15" src="../common/images/classic/icon_arrow_d.png">
                                <i class="fs_13 icon-down cursor_p btn_originalImg-display"></i>
                              </span>
                              <!-- 記事 -->
                              <a href="<bean:write name="feed1" property="link" />" target="_blank">
                                <bean:write name="feed1" property="title" />
                              </a>
                              <!-- RSS記事タイトル検索 -->
                              <a href="#" onClick="rssSearch('<bean:write name="feed1" property="title" />');">
                                <img class="classic-display" src="../common/images/classic/icon_search_web.gif" border="0" alt="<gsmsg:write key="cmn.search" />">
                                <img class="original-display" src="../common/images/original/icon_search_web.png" border="0" alt="<gsmsg:write key="cmn.search" />">
                              </a>
                            </div>
                          </div>
                          <div class="display_none">
                            <bean:write name="feed1" property="author" />
                          </div>
                          <!-- Description -->
                          <div id="ds<bean:write name="idx1" />_<bean:write name="idx2" />" class="display_n">
                            <div>
                              <bean:write name="feed1" property="publishedDate" />
                            </div>
                            <logic:empty name="feed1" property="description">&nbsp;</logic:empty>
                            <logic:notEmpty name="feed1" property="description">
                              <bean:define id="description" name="feed1" property="description" />
                              <bean:write name="description" property="value" filter="false" />
                            </logic:notEmpty>
                          </div>
                        </logic:iterate>
                        <%
                          }
                        %>
                      </logic:notEmpty>
                    </td>
                  </tr>
                </table>
              </div>
            </logic:equal>
          </logic:iterate>
        </td>
        <td class="column txt_t w50" id="rssListRight">
          <!-- 右コンテンツを表示 -->
          <logic:iterate id="flist" name="rss060Form" property="rss060Flist" indexId="idx1">
            <logic:equal name="flist" property="feedPosition" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_POSITIONFLG_RIGHT)%>">
              <!-- RSS取得結果  -->
              <div class="portlet" id="<bean:write name="flist" property="feedSid" />">
                <table class="table-top" cellpadding="0" cellspacing="0" border="0" id="rssListRight_<bean:write name="flist" property="feedSid" />">
                  <tr>
                    <th class="txt_l table_title-color">
                      <img class="classic-display" src="../rss/images/classic/menu_icon_single.gif">
                      <span>
                        <logic:equal name='flist' property='furl' value=''>
                        <a href="#!">
                        </logic:equal>
                        <logic:notEqual name='flist' property='furl' value=''>
                        <a href="<bean:write name='flist' property='furl' />" target="_blank">  
                        </logic:notEqual>
                          <bean:write name="flist" property="ftitle" />
                        </a>
                      </span>
                    </th>
                  </tr>
                  <tr>
                    <td colspan="2" class="rsswin_body">
                      <logic:empty name="flist" property="feedList">
                        <div>
                          <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                            <bean:write name="flist" property="feedUrl" />
                          </a>
                        </div>
                      </logic:empty>
                      <logic:notEmpty name="flist" property="feedList">
                        <bean:define id="feedList" name="flist" property="feedList" type="java.util.List" />
                        <%
                          if (feedList.size() == 0) {
                        %>
                        <div>
                          <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                            <bean:write name="flist" property="feedUrl" />
                          </a>
                        </div>

                        <%
                          } else {
                        %>

                        <logic:iterate id="feed1" name="flist" property="feedList" indexId="idx2">
                          <div id="<bean:write name="idx1" />_<bean:write name="idx2" />">
                            <div>
                              <!-- 要約表示切替 -->
                              <span onClick="dispDescription('ds<bean:write name="idx1" />_<bean:write name="idx2" />');">
                                <img class="classic-display wp15" src="../common/images/classic/icon_arrow_d.png">
                                <i class="fs_13 icon-down cursor_p btn_originalImg-display"></i>
                              </span>
                              <!-- 記事 -->
                              <a href="<bean:write name="feed1" property="link" />" target="_blank">
                                <bean:write name="feed1" property="title" />
                              </a>
                              <!-- RSS記事タイトル検索 -->
                              <a href="#!" onClick="rssSearch('<bean:write name="feed1" property="title" />');">
                                <img class="classic-display" src="../common/images/classic/icon_search_web.gif" border="0" alt="<gsmsg:write key="cmn.search" />">
                                <img class="original-display" src="../common/images/original/icon_search_web.png" border="0" alt="<gsmsg:write key="cmn.search" />">
                              </a>
                            </div>
                          </div>
                          <div class="display_none">
                            <bean:write name="feed1" property="author" />
                          </div>
                          <!-- Description -->
                          <div id="ds<bean:write name="idx1" />_<bean:write name="idx2" />" class="display_n">
                            <div>
                              <bean:write name="feed1" property="publishedDate" />
                            </div>
                            <logic:empty name="feed1" property="description">&nbsp;</logic:empty>
                            <logic:notEmpty name="feed1" property="description">
                              <bean:define id="description" name="feed1" property="description" />
                              <bean:write name="description" property="value" filter="false" />
                            </logic:notEmpty>
                          </div>
                        </logic:iterate>
                        <% } %>
                      </logic:notEmpty>
                    </td>
                  </tr>
                </table>
              </div>
            </logic:equal>
          </logic:iterate>
        </td>
      </tr>
    </table>
  </div>

</html:form>
<br class="clear">
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>