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
<title>GROUPSESSION <gsmsg:write key="bmk.bmk070.07" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bookmark/js/bmk070.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/common.css?500' type='text/css'>
<link rel=stylesheet href='../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/bookmark/bmk070">
    <input type="hidden" name="CMD" value="">

    <html:hidden property="procMode" />
    <html:hidden property="editBmuSid" />
    <html:hidden property="editBmkSid" />
    <html:hidden property="returnPage" />

    <html:hidden property="bmk010mode" />

    <span id="userBookmark">
      <html:hidden property="bmk010groupSid" />
      <html:hidden property="bmk010orderKey" />
      <html:hidden property="bmk010sortKey" />
      <html:hidden property="bmk010searchLabel" />
      <logic:notEmpty name="bmk070Form" property="bmk010delInfSid" scope="request">
        <logic:iterate id="item" name="bmk070Form" property="bmk010delInfSid" scope="request">
          <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>
    </span>

    <html:hidden property="bmk010userSid" />
    <html:hidden property="bmk010page" />
    <html:hidden property="bmk070SortKey" />
    <html:hidden property="bmk070OrderKey" />
    <html:hidden property="bmk070ReturnPage" />
    <html:hidden property="bmk080Page" />
    <html:hidden property="bmk080PageTop" />
    <html:hidden property="bmk080PageBottom" />

    <html:hidden property="bmk150PageNum" />
    <html:hidden property="bmk070ToBmk150DspFlg" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bookmark/images/classic/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />">
          <img class="header_pluginImg" src="../bookmark/images/original/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />"
        </li>
        <li>
          <gsmsg:write key="bmk.43" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="bmk.bmk070.07" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('bmk070back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <logic:notEmpty name="bmk070Form" property="bmk070ResultUrl">
        <table class="bgC_lightGray w100 mb5" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <th>
              <bean:define id="resultUrl" name="bmk070Form" property="bmk070ResultUrl" />
              <table class="comment w100" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td class="w95 bor1 pl5 pr5">
                    <a href="<bean:write name="resultUrl" property="bmuUrl" />" target="_blank"><bean:write name="resultUrl" property="bmuTitle" /></a>
                    <br>
                    <div nowrap>
                       <logic:notEmpty name="resultUrl" property="bmkUrlDspList">
                         <span class="fs_13">
                          <logic:iterate id="urlDsp" name="resultUrl" property="bmkUrlDspList" indexId="idx3">
                            <%
                              if (idx3 > 0) {
                            %>
                            <br>
                            <%
                              }
                            %>
                            <bean:write name="urlDsp" />
                          </logic:iterate>
                         </span>
                        </logic:notEmpty>
                    </div>
                  </td>
                  <bean:define id="userCnt" name="resultUrl" property="userCount" type="java.lang.Integer" />
                  <td class="no_w txt_c bor1 p5">
                    <gsmsg:write key="bmk.bmk070.05" />
                    <gsmsg:write key="bmk.23" arg0="<%=String.valueOf(userCnt.intValue())%>" />
                  </td>
                  <logic:equal name="bmk070Form" property="bmk070NotViewBmk" value="0">
                    <td class="no_w txt_c bor1 p5">
                      <div class="verAlignMid">
                      <gsmsg:write key="bmk.bmk070.01" />
                      <logic:equal name="resultUrl" property="scoreAvg" value="1">
                        <img class="classic-display" src="../bookmark/images/classic/icon_star1.gif" class="img_bottom" alt="<gsmsg:write key="bmk.11" />">
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star_line"></i>
                        <i class="original-display icon-star_line"></i>
                        <i class="original-display icon-star_line"></i>
                        <i class="original-display icon-star_line"></i>
                        <i class="original-display"></i>
                      </logic:equal>
                      <logic:equal name="resultUrl" property="scoreAvg" value="2">
                        <img class="classic-display" src="../bookmark/images/classic/icon_star2.gif" class="img_bottom" alt="<gsmsg:write key="bmk.10" />">
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star_line"></i>
                        <i class="original-display icon-star_line_line"></i>
                        <i class="original-display icon-star_line"></i>
                      </logic:equal>
                      <logic:equal name="resultUrl" property="scoreAvg" value="3">
                        <img class="classic-display" src="../bookmark/images/classic/icon_star3.gif" class="img_bottom" alt="<gsmsg:write key="bmk.09" />">
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star_line"></i>
                        <i class="original-display icon-star_line"></i>
                      </logic:equal>
                      <logic:equal name="resultUrl" property="scoreAvg" value="4">
                        <img class="classic-display" src="../bookmark/images/classic/icon_star4.gif" class="img_bottom" alt="<gsmsg:write key="bmk.08" />">
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star_line"></i>
                      </logic:equal>
                      <logic:equal name="resultUrl" property="scoreAvg" value="5">
                        <img class="classic-display" src="../bookmark/images/classic/icon_star5.gif" class="img_bottom" alt="<gsmsg:write key="bmk.07" />">
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                        <i class="original-display icon-star importance-red"></i>
                      </logic:equal>
                      </div>
                    </td>
                  </logic:equal>

                  <td class="txt_c w5 bor1">
                    <logic:equal name="bmk070Form" property="editBmkSid" value="-1">
                      <logic:empty name="bmk070Form" property="bmk070ReturnPage">
                        <button type="button" name="add" class="iconBtn-border" value="<gsmsg:write key="cmn.add" />" onClick="return bmkAdd(<bean:write name="resultUrl" property="bmuSid" />, 0);">
                          <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                        </button>
                      </logic:empty>
                      <logic:equal name="bmk070Form" property="bmk070ReturnPage" value="bmk070">
                        <button type="button" name="add" class="iconBtn-border" value="<gsmsg:write key="cmn.add" />" onClick="return bmkAdd(<bean:write name="resultUrl" property="bmuSid" />, 0);">
                          <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                        </button>
                      </logic:equal>
                      <logic:equal name="bmk070Form" property="bmk070ReturnPage" value="bmk150">
                        <button type="button" name="add" class="iconBtn-border" value="<gsmsg:write key="cmn.add" />" onClick="return bmkAdd(<bean:write name="resultUrl" property="bmuSid" />, 1);">
                          <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                        </button>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="bmk070Form" property="editBmkSid" value="-1">
                      <logic:empty name="bmk070Form" property="bmk070ReturnPage">
                        <button type="button" name="add" class="iconBtn-border" value="" onClick="return bmkEdit(<bean:write name="bmk070Form" property="editBmkSid" />, 0);">
                          <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.add" />">
                        </button>
                      </logic:empty>
                      <logic:equal name="bmk070Form" property="bmk070ReturnPage" value="bmk070">
                        <button type="button" name="add" class="iconBtn-border" value="" onClick="return bmkEdit(<bean:write name="bmk070Form" property="editBmkSid" />, 0);">
                          <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.add" />">
                        </button>
                      </logic:equal>
                      <logic:equal name="bmk070Form" property="bmk070ReturnPage" value="bmk150">
                        <button type="button" name="add" class="iconBtn-border" value="" onClick="return bmkEdit(<bean:write name="bmk070Form" property="editBmkSid" />, 1);">
                          <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.add" />">
                        </button>
                      </logic:equal>
                    </logic:notEqual>
                  </td>
                </tr>
              </table>
            </th>
          </tr>
        </table>
      </logic:notEmpty>

      <logic:notEmpty name="bmk070Form" property="bmk070ResultList">
        <bean:size id="count1" name="bmk070Form" property="bmk070PageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="bmk070PageTop" onchange="changePage(0);">
              <html:optionsCollection name="bmk070Form" property="bmk070PageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <table class="w100 m0">
        <tr>
          <th class="no_w wp180 txt_l bgC_lightGray bor1 border_bottom_none p5 fs_14">
            <span>
              <gsmsg:write key="cmn.contributor" />
            </span>
          </th>
          <th class="txt_l bgC_lightGray bor1 border_bottom_none p5 fs_14">
            <div class="component_bothEnd">
              <div class="flo_l">
                <gsmsg:write key="cmn.content" />
              </div>
              <div class="flo_r fs_14 verAlignMid">

                <bean:define id="sortKey" name="bmk070Form" property="bmk070SortKey" type="java.lang.Integer" />
                <bean:define id="orderKey" name="bmk070Form" property="bmk070OrderKey" type="java.lang.Integer" />

                <!-- 登録順 -->
                <%
                  if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE
                          && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC) {
                %>
               <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC%>);" class="verAlignMid cl_fontBody">
                 <span class="verAlignMid"><gsmsg:write key="bmk.17" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span>
               </a>

                <%
                  } else if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE
                          && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC) {
                %>
               <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);" class="verAlignMid cl_fontBody">
                <span class="verAlignMid"><gsmsg:write key="bmk.17" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span>
               </a>

                <%
                  } else {
                %>
                <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);">
                  <gsmsg:write key="bmk.17" />
                </a>
                <%
                  }
                %>／
                <!-- 評価順 -->
                <%
                  if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE
                          && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC) {
                %>
                <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC%>);" class="verAlignMid cl_fontBody">
                  <span class="verAlignMid"><gsmsg:write key="bmk.06" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span>
                </a>

                <%
                  } else if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE
                          && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC) {
                %>
                <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);" class="verAlignMid cl_fontBody">
                   <span class="verAlignMid"><gsmsg:write key="bmk.06" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span>
                </a>
                <%
                  } else {
                %>
                <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);">
                  <gsmsg:write key="bmk.06" />
                </a>
                <%
                  }
                %>／
                <!-- 投稿者順 -->
                <%
                  if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_AUID
                          && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC) {
                %>
               <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_AUID%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC%>);" class="verAlignMid cl_fontBody">
                 <span class="verAlignMid"><gsmsg:write key="cmn.contributor.order" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span>
               </a>

                <%
                  } else if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_AUID
                          && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC) {
                %>
                <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_AUID%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);" class="verAlignMid cl_fontBody">
                  <span class="verAlignMid"><gsmsg:write key="cmn.contributor.order" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span>
                </a>
                <%
                  } else {
                %>
                <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_AUID%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);">
                  <gsmsg:write key="cmn.contributor.order" />
                </a>
                <%
                  }
                %>
              </div>
            </div>
          </th>
        </tr>
      </table>

      <table class="w100">
        <logic:empty name="bmk070Form" property="bmk070ResultList">
          <tr class="bor_t1 mb20"><td><div class="mb20"></div></td></tr>
        </logic:empty>
        <!-- コメント・評価リスト一覧 -->
        <logic:notEmpty name="bmk070Form" property="bmk070ResultList">
          <logic:iterate id="resultMdl" name="bmk070Form" property="bmk070ResultList" indexId="idx">
            <bean:define id="tdColor" value="" />
            <%
              String[] tdColors = new String[] { "bgC_tableCell", "bgC_tableCellEvn" };
            %>
            <%
              tdColor = tdColors[(idx.intValue() % 2)];
            %>
            <tr class="txt_t mb20 bor1">
              <td class="<%=tdColor%> p5 no_w wp180 bor_t1 bor_l1 bor_b1">
                <table class="w100 txt_c">
                  <tr>
                    <bean:define id="mukoUserClass" value="" />
                    <logic:equal name="resultMdl" property="usrUkoFlg" value="1">
                      <bean:define id="mukoUserClass" value="mukoUser" />
                    </logic:equal>
                    <td class="txt_t txt_c bor1">
                      <a href="#!" onClick="openUserInfoWindow(<bean:write name="resultMdl" property="usrSid" />);">
                        <span class="<%=mukoUserClass%>">
                          <bean:write name="resultMdl" property="usrName" />
                        </span>
                      </a>
                    </td>
                  </tr>
                  <tr>
                    <logic:equal name="resultMdl" property="userPictKf" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE)%>">
                      <td class="txt_c txt_b no_w bor1 p5">
                      <div class="userImg-hikokai wp130 hp150 mrl_auto">
                        <span class="cl_fontWarn fs_24 fw_b hikokai_lh">
                          <gsmsg:write key="cmn.private" />
                        </span>
                      </div>
                      </td>
                    </logic:equal>

                    <logic:notEqual name="resultMdl" property="userPictKf" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE)%>">
                      <logic:notEmpty name="resultMdl" property="photoFileName">
                        <td class="bor1 p5">
                          <a href="#!" onClick="openUserInfoWindow(<bean:write name="resultMdl" property="usrSid" />);">
                            <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="resultMdl" property="photoFileSid" />" name="userPhoto" alt="<gsmsg:write key="cmn.photo" />" border="0" width="130" onload="initImageView130('userPhoto');">
                          </a>
                        </td>
                      </logic:notEmpty>
                      <logic:empty name="resultMdl" property="photoFileName">
                        <td class="bor1 p5">
                          <a href="#!" onClick="openUserInfoWindow(<bean:write name="resultMdl" property="usrSid" />);">
                            <img class="classic-display" src="../common/images/classic/icon_photo.gif" name="userImage" alt="<gsmsg:write key="cmn.photo" />" />
                            <img class="original-display" src="../common/images/classic/icon_photo.gif" name="userImage" alt="<gsmsg:write key="cmn.photo" />" />
                          </a>
                        </td>
                      </logic:empty>
                    </logic:notEqual>
                  </tr>

                  <tr>
                    <td class="txt_c p0 pt5">
                      <button type="button" value="<gsmsg:write key="bmk.30" />" class="baseBtn" onClick="return buttonPushUser(<bean:write name="resultMdl" property="usrSid" />);">
                        <gsmsg:write key="bmk.30" />
                      </button>
                    </td>
                  </tr>
                </table>
              </td>
              <td class="<%=tdColor%> txt_t txt_l bor1 p5">
                <logic:equal name="resultMdl" property="bmkScore" value="1">
                  <img class="classic-display" src="../bookmark/images/classic/icon_star1.gif" class="img_bottom" alt="<gsmsg:write key="bmk.11" />">
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display"></i>
                  <br>
                  <br>
                </logic:equal>
                <logic:equal name="resultMdl" property="bmkScore" value="2">
                  <img class="classic-display" src="../bookmark/images/classic/icon_star2.gif" class="img_bottom" alt="<gsmsg:write key="bmk.10" />">
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display icon-star_line_line"></i>
                  <i class="original-display icon-star_line"></i>
                  <br>
                  <br>
                </logic:equal>
                <logic:equal name="resultMdl" property="bmkScore" value="3">
                  <img class="classic-display" src="../bookmark/images/classic/icon_star3.gif" class="img_bottom" alt="<gsmsg:write key="bmk.09" />">
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star_line"></i>
                  <i class="original-display icon-star_line"></i>
                  <br>
                  <br>
                </logic:equal>
                <logic:equal name="resultMdl" property="bmkScore" value="4">
                  <img class="classic-display" src="../bookmark/images/classic/icon_star4.gif" class="img_bottom" alt="<gsmsg:write key="bmk.08" />">
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star_line"></i>
                  <br>
                  <br>
                </logic:equal>
                <logic:equal name="resultMdl" property="bmkScore" value="5">
                  <img class="classic-display" src="../bookmark/images/classic/icon_star5.gif" class="img_bottom" alt="<gsmsg:write key="bmk.07" />">
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <i class="original-display icon-star importance-red"></i>
                  <br>
                  <br>
                </logic:equal>
                <!-- コメント -->
                <span>
                  <bean:write name="resultMdl" property="bmkCmt" filter="false" />
                </span>
                <p class="bookmark_tag">
                  <!-- ラベル -->
                  <span>
                    <gsmsg:write key="cmn.label" />
                    <gsmsg:write key="wml.215" />
                    <bean:write name="resultMdl" property="labelName" />
                  </span>
                  <br> <br>
                  <!-- 登録日 -->
                  <span>
                    <gsmsg:write key="bmk.15" />
                    <gsmsg:write key="wml.215" />
                    <bean:write name="resultMdl" property="strBmkAdate" />
                  </span>
                </p>
              </td>
            </tr>
            <tr>
              <td class="">&nbsp;</td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>

      <logic:notEmpty name="bmk070Form" property="bmk070ResultList">
        <bean:size id="count2" name="bmk070Form" property="bmk070PageLabelList" scope="request" />
        <logic:greaterThan name="count2" value="1">
          <div class="paging mb5">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="bmk070PageBottom" onchange="changePage(1);">
              <html:optionsCollection name="bmk070Form" property="bmk070PageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('bmk070back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>