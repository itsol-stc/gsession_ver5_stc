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
<title>GROUPSESSION <gsmsg:write key="rss.3" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/search.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.sortable.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../rss/js/rss010.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../rss/css/rss.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>

<html:form action="/rss/rss010">
  <input type="hidden" name="CMD" value="search">
  <input type="hidden" name="rssSid" value="">
  <input type="hidden" name="rssTitle" value="">
  <input type="hidden" name="rssCmdMode" value="0">

  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

  <div class="pageTitle">
    <ul>
      <li>
        <img class="header_pluginImg-classic" src="../rss/images/classic/header_rss.png" alt=" <gsmsg:write key="rss.3" />">
        <img class="header_pluginImg" src="../rss/images/original/menu_icon_single.png" alt=" <gsmsg:write key="rss.3" />">
      </li>
      <li>
        <gsmsg:write key="rss.3" />
        </span>
      </li>
      <li>
      </li>
    </ul>
  </div>
  <div class="wrapper">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
    <div class="txt_r">
       <button type="button" name="btn_feedadd" class="baseBtn" value="<gsmsg:write key="cmn.ranking" />" onClick="buttonPush('ranking');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_rank.png" alt="<gsmsg:write key="cmn.ranking" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_ranking.png" alt="<gsmsg:write key="cmn.ranking" />">
         <gsmsg:write key="cmn.ranking" />
       </button>
       <button type="button" name="btn_feedadd" class="baseBtn" value="<gsmsg:write key="rss.6" />" onClick="buttonPush('rssInput');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rss.6" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rss.6" />">
         <gsmsg:write key="rss.6" />
      </button>
    </div>

    <div class="wrapper_2column">

      <div class="w75">

        <table class="w100">
          <tr>
            <td class="txt_t w50 column" id="rssListLeft">

              <!-- 左コンテンツを表示 -->
              <logic:iterate id="flist" name="rss010Form" property="rss010Flist" indexId="idx1">
                <logic:equal name="flist" property="feedPosition" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_POSITIONFLG_LEFT)%>">

                  <!-- RSS取得結果  -->
                  <div class="portlet mr5" id="<bean:write name="flist" property="feedSid" />">
                    <table class="table-top" cellpadding="0" cellspacing="0" border="0">
                      <th class="table_title-color w80 txt_l border_right_none">
                        <span>
                          <img class="classic-display" src="../rss/images/classic/menu_icon_single.gif">
                          <span>
                            <logic:equal name='flist' property='furl' value=''>
                            <a href="#!">
                            </logic:equal>
                            <logic:notEqual name='flist' property='furl' value=''>
                            <a href="<bean:write name='flist' property='furl' />" target="_blank">  
                            </logic:notEqual>
                              <bean:write name='flist' property='ftitle' />
                            </a>
                          </span>
                        </span>
                      </th>
                      <th class="table_title-color w20 txt_r border_left_none">
                        <span class="txt_r">
                          <button type="button" value="<gsmsg:write key="cmn.edit" />" onClick="rssEdit(<bean:write name='flist' property='feedSid' />);" class="baseBtn mr5 no_w">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                            <gsmsg:write key="cmn.edit" />
                        </span>
                      </th>
                      <tr>
                        <td colspan="3">

                          <logic:empty name="flist" property="feedList">
                            <div>
                              <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                                <bean:write name="flist" property="feedUrl" />
                              </a>
                            </div>
                          </logic:empty>
                          <logic:notEmpty name="flist" property="feedList">
                            <bean:define id="feedList" name="flist" property="feedList" type="java.util.List" />
                            <% if (feedList.size() == 0) { %>
                            <div>
                              <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                                <bean:write name="flist" property="feedUrl" />
                              </a>
                            </div>

                            <% } else { %>

                            <logic:iterate id="feed1" name="flist" property="feedList" indexId="idx2">
                              <div id="<bean:write name='idx1' />_<bean:write name='idx2' />">
                                <div class="cl_linkVisit-content">
                                  <!-- 要約表示切替 -->
                                  <span onClick="dispDescription('ds<bean:write name="idx1" />_<bean:write name="idx2" />');">
                                    <img class="classic-display wp15" src="../common/images/classic/icon_arrow_d.png">
                                    <i class="fs_13 icon-down cursor_p btn_originalImg-display"></i>
                                  </span>
                                  <!-- 記事 -->
                                  <a href="<bean:write name='feed1' property='link' />" target="_blank">
                                    <bean:write name="feed1" property="title" />
                                  </a>
                                  <!-- RSS記事タイトル検索 -->
                                  <a href="#" onClick="rssSearch('<bean:write name="feed1" property="title" />');">
                                    <img class="classic-display" src="../common/images/classic/icon_search_web.gif" border="0" alt="<gsmsg:write key="cmn.search" />">
                                    <img class="original-display" src="../common/images/original/icon_search_web.png" border="0" alt="<gsmsg:write key="cmn.search" />">
                                  </a>
                                </div>
                              </div>

                              <div class="display_n">
                                <bean:write name="feed1" property="author" />
                              </div>

                              <!-- Description -->
                              <div id="ds<bean:write name='idx1' />_<bean:write name='idx2' />" class="display_n">
                                <div class="rss_date_text">
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

            <td class="w50 txt_t column" id="rssListRight">
              <!-- 右コンテンツを表示 -->
              <logic:iterate id="flist" name="rss010Form" property="rss010Flist" indexId="idx1">

                <logic:equal name="flist" property="feedPosition" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstRss.RSS_POSITIONFLG_RIGHT)%>">
                  <!-- RSS取得結果  -->
                  <div class="portlet mr5" id="<bean:write name='flist' property='feedSid' />">
                    <table class="table-top" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <th class="table_title-color w80 txt_l border_right_none">
                            <span>
                              <img class="classic-display" src="../rss/images/classic/menu_icon_single.gif">
                              <span>
                                <logic:equal name='flist' property='furl' value=''>
                                <a href="#!">
                                </logic:equal>
                                <logic:notEqual name='flist' property='furl' value=''>
                                <a href="<bean:write name='flist' property='furl' />" target="_blank">  
                                </logic:notEqual>
                                  <bean:write name='flist' property='ftitle' />
                                </a>
                              </span>
                            </span>
                        </th>
                         <th class="table_title-color w20 txt_r border_left_none">
                            <span class="txt_r border_left_none">
                              <button type="button" value="<gsmsg:write key="cmn.edit" />" onClick="rssEdit(<bean:write name='flist' property='feedSid' />);" class="baseBtn no_w">
                                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                                <gsmsg:write key="cmn.edit" />
                              </button>
                          </span>
                        </th>
                      </tr>
                      <tr>
                        <td colspan="3">
                          <logic:empty name="flist" property="feedList">
                            <div>
                              <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                                <bean:write name="flist" property="feedUrl" />
                              </a>
                            </div>
                          </logic:empty>
                          <logic:notEmpty name="flist" property="feedList">
                            <bean:define id="feedList" name="flist" property="feedList" type="java.util.List" />
                            <% if (feedList.size() == 0) { %>
                            <div>
                              <a href="<bean:write name="flist" property="feedUrl" />" target="_blank">
                                <bean:write name="flist" property="feedUrl" />
                              </a>
                            </div>

                            <% } else { %>

                            <logic:iterate id="feed1" name="flist" property="feedList" indexId="idx2">
                              <div id="<bean:write name='idx1' />_<bean:write name='idx2' />">
                                <div class="cl_linkVisit-content">
                                  <!-- 要約表示切替 -->
                                  <span onClick="dispDescription('ds<bean:write name="idx1" />_<bean:write name="idx2" />');">
                                    <img class="classic-display wp15" src="../common/images/classic/icon_arrow_d.png">
                                    <i class="fs_13 icon-down cursor_p btn_originalImg-display"></i>
                                  </span>
                                  </a>
                                  <!-- 記事 -->
                                  <a href="<bean:write name='feed1' property='link' />" target="_blank">
                                    <bean:write name="feed1" property="title" />
                                  </a>
                                  <!-- RSS記事タイトル検索 -->
                                  <a href="#!" onClick="rssSearch('<bean:write name="feed1" property="title" />');">
                                    <img class="classic-display" src="../common/images/classic/icon_search_web.gif" border="0" alt="<gsmsg:write key="cmn.search" />">
                                    <img class="original-display" src="../common/images/original/icon_search_web.png" border="0" alt="<gsmsg:write key="cmn.search" />">
                                  </a>
                                </div>
                              </div>

                              <div class="display_n">
                                <bean:write name="feed1" property="author" />
                              </div>

                              <!-- Description -->
                              <div id="ds<bean:write name='idx1' />_<bean:write name='idx2' />" class="display_n">
                                <div class="rss_date_text">
                                  <bean:write name="feed1" property="publishedDate" />
                                </div>
                                <logic:empty name="feed1" property="description">&nbsp;</logic:empty>
                                <logic:notEmpty name="feed1" property="description">
                                  <bean:define id="description" name="feed1" property="description" />
                                  <bean:write name="description" property="value" filter="false" />
                                </logic:notEmpty>
                              </div>
                            </logic:iterate>
                            <% }%>
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

      <div class="w25">

        <!-- 購読RSSの一覧 -->
        <logic:notEmpty name="rss010Form" property="rankingList">
          <!-- 登録ランキング -->
          <table class="table-top">
            <tr>
              <th class="txt_c table_title-color" colspan="3">
                <span>
                  <gsmsg:write key="cmn.entry.rankings" />
                </span>
              </th>
            </tr>

            <logic:iterate id="rankingMdl" name="rss010Form" property="rankingList" indexId="index">
              <tr>
                <td class="w15 border_none">
                  <bean:define id="rankNum" name="rankingMdl" property="ranking" type="java.lang.String" />
                  <span class="cl_ranking fw_b">
                    <gsmsg:write key="rss.rss010.3" arg0="<%=rankNum%>" />
                  </span>
                </td>
                <td class="txt_l w70 border_none">
                  <a href="<bean:write name='rankingMdl' property='rsdUrl' />" target="_blank">
                    <span>
                      <font size="-1"><bean:write name="rankingMdl" property="rsdTitle" /></font>
                    </span>
                  </a>
                  <br>
                  <span class="fs_12">
                    (<bean:write name="rankingMdl" property="userCount" />)
                  </span>
                </td>

                <td class="w30 border_none">
                  <logic:equal name="rankingMdl" property="koudokuCount" value="0">
                    <button type="button" value="&nbsp;" onClick="return rssAdd('<bean:write name="rankingMdl" property="rssSid" />', '<bean:write name="rankingMdl" property="rsdTitle" />');" class="iconBtn-border">
                      <img class="classic-display" src="../common/images/classic/icon_rss_2.png">
                      <img class="original-display" src="../common/images/original/icon_add.png">
                    </button>
                  </logic:equal>
                  <logic:greaterThan name="rankingMdl" property="koudokuCount" value="0">
                    <span class="cl_fontWarn no_w">
                      <gsmsg:write key="rss.1" />
                      <br>
                      <gsmsg:write key="cmn.pre" />
                    </span>
                  </logic:greaterThan>
                </td>
              </tr>
            </logic:iterate>
          </table>
          <br>
        </logic:notEmpty>

        <logic:notEmpty name="rss010Form" property="newRankingList">

          <!-- 新着RSS -->
          <table class="table-top">
            <tr>
              <th class="txt_c table_title-color" colspan="2">
                <span>
                  <gsmsg:write key="rss.9" />
                </span>
              </th>
            </tr>
            <logic:iterate id="newRankingMdl" name="rss010Form" property="newRankingList" indexId="index">
              <tr>
                <td class="txt_l w85 border_none">
                  <a href="<bean:write name='newRankingMdl' property='url' />" target="_blank">
                    <span>
                      <font size="-1"><bean:write name="newRankingMdl" property="title" /></font>
                    </span>
                  </a>
                </td>
                <td class="w15 border_none">
                  <logic:equal name="newRankingMdl" property="koudokuCount" value="0">
                    <button type="button" value="&nbsp;" onClick="return rssAdd('<bean:write name="newRankingMdl" property="rssSid" />', '<bean:write name="newRankingMdl" property="title" />');" class="iconBtn-border">
                      <img class="classic-display" src="../common/images/classic/icon_rss_2.png">
                      <img class="original-display" src="../common/images/original/icon_add.png">
                    </button>
                  </logic:equal>
                  <logic:greaterThan name="newRankingMdl" property="koudokuCount" value="0">
                    <span class="cl_fontWarn no_w">
                      <gsmsg:write key="rss.1" />
                      <br>
                      <gsmsg:write key="cmn.pre" />
                    </span>
                  </logic:greaterThan>
                </td>
              </tr>
            </logic:iterate>
          </table>
        </logic:notEmpty>
      </div>
    </div>
  </div>

  <!-- SUB MENU -->
  <div class="rss_submenu">
    <div id="kiji">
      <gsmsg:write key="rss.10" />
    </div>

  </div>
  <!-- rss_menu -->

</html:form>
<br class="clear">
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>