<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%-- 評価画像定義 --%>
<%
  java.util.HashMap imgMap = new java.util.HashMap();
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
  String rank1 = gsMsg.getMessage(request, "bmk.11");
  String rank2 = gsMsg.getMessage(request, "bmk.10");
  String rank3 = gsMsg.getMessage(request, "bmk.09");
  String rank4 = gsMsg.getMessage(request, "bmk.08");
  String rank5 = gsMsg.getMessage(request, "bmk.07");

  imgMap.put("0", "&nbsp;");
  imgMap.put("1",
      "<img class=\"classic-display\" src=\"../bookmark/images/classic/icon_star1.gif\" alt=\"" + rank1
          + "\" class=\"img_bottom\">" + "<i class=\"original-display importance-red icon-star\"></i>"
          + "<i class=\"original-display icon-star_line \"></i>"
          + "<i class=\"original-display icon-star_line \"></i>"
          + "<i class=\"original-display icon-star_line \"></i>"
          + "<i class=\"original-display icon-star_line \"></i>");
  imgMap.put("2",
      "<img class=\"classic-display\" src=\"../bookmark/images/classic/icon_star2.gif\" alt=\"" + rank2
          + "\" class=\"img_bottom\">" + "<i class=\"original-display importance-red icon-star\"></i>"
          + "<i class=\"original-display importance-red icon-star\"></i>"
          + "<i class=\"original-display icon-star_line \"></i>"
          + "<i class=\"original-display icon-star_line \"></i>"
          + "<i class=\"original-display icon-star_line \"></i>");
  imgMap.put("3", "<img class=\"classic-display\" src=\"../bookmark/images/classic/icon_star3.gif\" alt=\""
      + rank3 + "\" class=\"img_bottom\">" + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display importance-red icon-star\"></i>" + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display icon-star_line \"></i>"
      + "<i class=\"original-display icon-star_line \"></i>");
  imgMap.put("4", "<img class=\"classic-display\" src=\"../bookmark/images/classic/icon_star4.gif\" alt=\""
      + rank4 + "\" class=\"img_bottom\">" + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display importance-red icon-star\"></i>" + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display icon-star_line\"></i>");
  imgMap.put("5", "<img class=\"classic-display\" src=\"../bookmark/images/classic/icon_star5.gif\" alt=\""
      + rank5 + "\" class=\"img_bottom\">" + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display importance-red icon-star\"></i>" + "<i class=\"original-display importance-red icon-star\"></i>"
      + "<i class=\"original-display importance-red icon-star\"></i>" + "<i class=\"original-display importance-red icon-star\"></i>");
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="bmk.bmk010.11" /></title>
<link rel=stylesheet href="../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/search.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bookmark/js/bmk010.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bookmark/js/bmkcommon.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>

  <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />

  <html:form action="/bookmark/bmk010">

    <input type="hidden" name="CMD" value="">

    <html:hidden property="procMode" />
    <html:hidden property="editBmuSid" />
    <html:hidden property="editBmkSid" />
    <html:hidden property="returnPage" value="bmk010" />

    <html:hidden property="bmk010mode" />
    <html:hidden property="bmk010orderKey" />
    <html:hidden property="bmk010sortKey" />
    <html:hidden property="bmk010searchLabel" />
    <html:hidden property="bmk010page" />
    <input type="hidden" name="helpPrm" value="<bean:write name="bmk010Form" property="bmk010mode" />">

    <bean:define id="bmkMode" name="bmk010Form" property="bmk010mode" type="java.lang.Integer" />
    <%
      if (bmkMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU) {
    %>
    <html:hidden property="bmk010groupSid" />
    <html:hidden property="bmk010userSid" />
    <%
      } else if (bmkMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP) {
    %>
    <html:hidden property="bmk010userSid" />
    <%
      }
    %>

    <logic:notEmpty name="bmk010Form" property="bmk010SelectedDelSid" scope="request">
      <logic:iterate id="item" name="bmk010Form" property="bmk010SelectedDelSid" scope="request">
        <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>



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
          <bean:define id="bmkMode" name="bmk010Form" property="bmk010mode" type="java.lang.Integer" />
          <%
            if (bmkMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN) {
          %>
          <gsmsg:write key="bmk.30" />
          <%
            } else if (bmkMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP) {
          %>
          <gsmsg:write key="bmk.51" />
          <%
            } else if (bmkMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU) {
          %>
          <gsmsg:write key="bmk.34" />
          <%
            }
          %>
        </li>
        <li>
        </li>
      </ul>

    </div>
    <div class="wrapper_2column">
      <div class="side_multi-left bgC_none">

        <!-- ラベル一覧 -->
        <table class="table-top m0">
          <tbody>
            <tr>
              <th class="txt_l">
                <div class="component_bothEnd">
                  <div>
                    <gsmsg:write key="cmn.label" />
                  </div>

                <logic:equal name="bmk010Form" property="bmk010editPow" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.POW_YES)%>'>
                  <div class="side_confGear">
                      <button type="button" onClick="return buttonPush('labelList');" class="baseBtn classic-display" value="<gsmsg:write key="cmn.setting" />">
                        <gsmsg:write key="cmn.setting" />
                      </button>
                      <i class="icon-setting cursor_p fs_18 original-display" onClick="return buttonPush('labelList');"></i>
                  </div>
                </logic:equal>
                </div>
              </th>
            </tr>

            <logic:notEmpty name="bmk010Form" property="bmk010LabelList">
              <logic:iterate id="labelMdl" name="bmk010Form" property="bmk010LabelList" indexId="idx">
                <tr>
                  <td>
                    <div class="component_bothEnd">
                      <div>
                        <a href="#!" onclick="return selectLabel('<bean:write name="labelMdl" property="blbSid" />');">
                          <span class="textLink">
                            <bean:write name="labelMdl" property="blbName" />
                            &nbsp;(<bean:write name="labelMdl" property="bmkLabelCount" />)
                          </span>
                        </a>
                      </div>
                      <div>
                        <logic:notEqual name="bmk010Form" property="bmk010searchUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_NOT_USE)%>">
                          <logic:notEqual name="labelMdl" property="blbSid" value="-1">
                            <a href="#!" onClick="webSearch('<bean:write name="labelMdl" property="blbNameWebSearchWord" />');">
                              <img class="classic-display" src="../common/images/classic/icon_search_web.gif" alt="" border="0">
                              <img class="original-display" src="../common/images/original/icon_search_web.png" alt="" border="0">
                            </a>
                            <a href="#!" onClick="webSearch('<bean:write name="labelMdl" property="blbNameWebSearchWord" />');">
                              <span class="text_search_web">
                                <gsmsg:write key="cmn.search" />
                              </span>
                            </a>
                          </logic:notEqual>
                        </logic:notEqual>
                      </div>
                    </div>
                  </td>
                </tr>

              </logic:iterate>
            </logic:notEmpty>

          </tbody>
        </table>

        <!-- 新着ブックマーク -->
        <logic:notEmpty name="bmk010Form" property="bmk010NewList">
          <br>

          <table class="table-top">
            <tr>
              <th>
                <div class="component_bothEnd">
                  <div>
                    <gsmsg:write key="bmk.24" />
                  </div>

                <logic:equal name="bmk010Form" property="bmk010editPow" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.POW_YES)%>'>
                  <div>
                    <button type="button" class=baseBtn value="<gsmsg:write key="cmn.list" />" onclick="buttonPush('newBookmark');">
                      <img class="classic-display" src="../common/images/classic/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
                      <img class="original-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
                      <gsmsg:write key="cmn.list" />
                    </button>
                  </div>
                </logic:equal>
              </th>
            </tr>

            <logic:iterate id="newMdl" name="bmk010Form" property="bmk010NewList" indexId="idx">
              <tr>
                <td>
                <table class="w100 table-noBorder p0">
                  <tr>
                    <td class="w90">
                    <!-- タイトル -->
                    <a class="mr5" href="<bean:write name="newMdl" property="bmuUrl" />" target="_blank">
                      <bean:write name="newMdl" property="bmuTitle" />
                    </a>
                    <!-- 人数 -->
                    <div class="no_w display_inline-block">
                      <bean:define id="perCnt1" name="newMdl" property="bmkPerCount" type="java.lang.Integer" />
                      <a href="#!" onclick="return selPerCount('<bean:write name="newMdl" property="bmuSid" />');" class="bmkCount">
                        <gsmsg:write key="bmk.23" arg0="<%=String.valueOf(perCnt1.intValue())%>" />
                      </a>
                    </div>
                    </td>
                    <td class="w10">
                    <!-- 追加ボタン -->
                    <logic:equal name="newMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                      <button type="button" name="<gsmsg:write key="cmn.add" />" class="iconBtn-border" value="<gsmsg:write key="cmn.add" />" onclick="return buttonPushAdd('<bean:write name="newMdl" property="bmuSid" />');">
                        <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                        <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      </button>
                    </logic:equal>
                    </td>
                  </tr>
                </table>
                </td>
              </tr>
            </logic:iterate>

          </table>
        </logic:notEmpty>

        <!-- ランキング -->
        <logic:notEmpty name="bmk010Form" property="bmk010RankingList">
          <br>

          <table class="table-top">
            <tr>
              <th class="txt_l w100" colspan="2">
                <span>
                  <gsmsg:write key="bmk.bmk010.01" />
                </span>
              </th>
            </tr>

            <logic:iterate id="rankMdl" name="bmk010Form" property="bmk010RankingList" indexId="idx">
              <tr>
                <!-- 追加ボタンありのときのtd -->
                <logic:equal name="rankMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                  <td>
                </logic:equal>
                <!-- 追加ボタンなしのときのtd -->
                <logic:notEqual name="rankMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                  <td colspan="2">
                </logic:notEqual>
                <table class="w100 table-noBorder p0">
                  <tr>
                    <td class="w90">
                    <!-- タイトル・人数 -->
                    <a class="mr5" href="<bean:write name="rankMdl" property="bmuUrl" />" target="_blank">
                      <bean:write name="rankMdl" property="bmuTitle" />
                    </a>
                    <bean:define id="perCnt2" name="rankMdl" property="bmkPerCount" type="java.lang.Integer" />
                    <div class="no_w display_inline-block">
                      <a href="#!" onclick="return selPerCount('<bean:write name="rankMdl" property="bmuSid" />');" class="bmkCount no_w">
                        <gsmsg:write key="bmk.23" arg0="<%=String.valueOf(perCnt2.intValue())%>" />
                      </a>
                    </div>
                    </td>
                    <td class="w10">
                    <!-- 追加ボタン -->
                    <logic:equal name="rankMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                      <button type="button" name="add" class="iconBtn-border" value="<gsmsg:write key="cmn.add" />" onclick="return buttonPushAdd('<bean:write name="rankMdl" property="bmuSid" />');">
                        <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                        <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      </button>
                    </logic:equal>
                    </td>
                  </tr>
                </table>
                </td>
              </tr>
            </logic:iterate>

          </table>
        </logic:notEmpty>
      </div>


      <div class="main">
        <!-- メッセージ -->
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>

        <div class="component_bothEnd">
          <div>
            <!-- グループコンボ -->
            <logic:notEqual name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>'>
              <logic:notEmpty name="bmk010Form" property="bmk010groupCmbList">
                <html:select name="bmk010Form" property="bmk010groupSid" onchange="buttonPush('grpChange');" styleClass="wp200">
                  <html:optionsCollection name="bmk010Form" property="bmk010groupCmbList" value="value" label="label" />
                </html:select>
                <button type="button" onclick="openGroupWindow(this.form.bmk010groupSid, 'bmk010groupSid', '0')" class="iconBtn-border" value="&nbsp;&nbsp;" id="bmk010GroupBtn">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                </button>
              </logic:notEmpty>
            </logic:notEqual>
            <!-- ユーザコンボ -->
            <logic:equal name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>'>
              <logic:notEmpty name="bmk010Form" property="bmk010userCmbList">
                <html:select name="bmk010Form" property="bmk010userSid" onchange="buttonPush('usrChange');" styleClass="wp150">
                  <logic:iterate id="user" name="bmk010Form" property="bmk010userCmbList">
                    <bean:define id="userValue" name="user" property="value" />
                    <bean:define id="mukoUserClass" value="" />
                    <logic:equal name="user" property="usrUkoFlg" value="1">
                      <bean:define id="mukoUserClass" value="mukoUserOption" />
                    </logic:equal>
                    <html:option styleClass="<%=mukoUserClass%>" value="<%=String.valueOf(userValue)%>">
                      <bean:write name="user" property="label" />
                    </html:option>
                  </logic:iterate>
                </html:select>
              </logic:notEmpty>
            </logic:equal>
            <!-- グループ編集権限ボタン -->
            <logic:equal name="bmk010Form" property="bmk010viewGroupBtn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.POW_YES)%>'>
              <button type="button" name="btn_group" class="baseBtn" value="<gsmsg:write key="cmn.setting.permissions" />" onClick="return buttonPush('groupMenu');">
                <gsmsg:write key="cmn.setting.permissions" />
              </button>
            </logic:equal>
          </div>
          <div>
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.new.registration" />" onClick="return buttonPushAdd(-1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
              <gsmsg:write key="cmn.new.registration" />
            </button>
            <button type="button" name="btn_del" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" name="btn_lank" class="baseBtn" value="<gsmsg:write key="cmn.ranking" />" onClick="return buttonPush('rankingList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_rank.png" alt="<gsmsg:write key="cmn.ranking" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_ranking.png" alt="<gsmsg:write key="cmn.ranking" />">
              <gsmsg:write key="cmn.ranking" />
            </button>
          </div>
        </div>
        <div class="mt10">

          <!-- 個人タブ -->
          <logic:equal name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>'>
            <ul class="tabHeader w100 hp30">
              <li class="tabHeader_tab-on bgC_lightGray mwp100 pt5 pb5 js_tab border_bottom_none txt_c pt5 pb5 bgI_none">
                <gsmsg:write key="bmk.bmk010.07" />
              </li>
              <li class="tabHeader_tab-off mwp100 pt5 pb5 js_tab txt_c" onclick="changeBmkKbn('<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>');">
                <gsmsg:write key="cmn.group" />
              </li>
              <li class="tabHeader_tab-off mwp100 pt5 pb5 js_tab txt_c" onclick="changeBmkKbn('<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>');">
                <gsmsg:write key="bmk.bmk010.09" />
              </li>
              <li class="tabHeader_space">
                <logic:notEmpty name="bmk010Form" property="bmk010pageCmbList">
                  <div class="paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select styleClass="paging_combo" property="bmk010pageTop" onchange="changePage('bmk010pageTop');">
                      <html:optionsCollection name="bmk010Form" property="bmk010pageCmbList" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </div>
                </logic:notEmpty>
              </li>
            </ul>
          </logic:equal>

          <!-- グループタブ -->
          <logic:equal name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>'>

            <ul class="tabHeader w100 hp30">
              <li class="tabHeader_tab-off mwp100 pt5 pb5 js_tab txt_c" onclick="changeBmkKbn('<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>');">
                <gsmsg:write key="bmk.bmk010.07" />
              </li>
              <li class="tabHeader_tab-on mwp100 bgC_lightGray pt5 pb5 js_tab border_bottom_none txt_c bgI_none">
                <gsmsg:write key="cmn.group" />
              </li>
              <li class="tabHeader_tab-off mwp100 pt5 pb5 js_tab txt_c" onclick="changeBmkKbn('<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>');">
                <gsmsg:write key="bmk.bmk010.09" />
              </li>
              <li class="tabHeader_space">
                <logic:notEmpty name="bmk010Form" property="bmk010pageCmbList">
                  <div class="paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select styleClass="paging_combo" property="bmk010pageTop" onchange="changePage('bmk010pageTop');">
                      <html:optionsCollection name="bmk010Form" property="bmk010pageCmbList" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </div>
                </logic:notEmpty>
              </li>
            </ul>
          </logic:equal>
          <!-- 共有タブ -->
          <logic:equal name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>'>


            <ul class="tabHeader w100 hp30">
              <li class="tabHeader_tab-off mwp100 pt5 pb5 js_tab txt_c" onclick="changeBmkKbn('<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>');">
                <gsmsg:write key="bmk.bmk010.07" />
              </li>
              <li class="tabHeader_tab-off mwp100 pt5 pb5 js_tab txt_c" onclick="changeBmkKbn('<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>');">
                <gsmsg:write key="cmn.group" />
              </li>
              <li class="tabHeader_tab-on mwp100 bgC_lightGray pt5 pb5 js_tab border_bottom_none txt_c bgI_none">
                <gsmsg:write key="bmk.bmk010.09" />
              </li>
              <li class="tabHeader_space">
                <logic:notEmpty name="bmk010Form" property="bmk010pageCmbList">
                  <div class="paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select styleClass="paging_combo" property="bmk010pageTop" onchange="changePage('bmk010pageTop');">
                      <html:optionsCollection name="bmk010Form" property="bmk010pageCmbList" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </div>
                </logic:notEmpty>
              </li>
            </ul>
          </logic:equal>
        </div>

        <div class="bgC_lightGray bor_r1 bor_l1 bor_b1 mb10 txt_r pt5 pb5">
          <!-- 条件 -->
          <logic:equal name="bmk010Form" property="bmk010searchLabel" value='<%=String.valueOf(jp.groupsession.v2.bmk.bmk010.Bmk010Biz.INIT_VALUE)%>'>
            <span class="flo_l fw_b ml5">
              <span class="verAlignMid"><gsmsg:write key="cmn.showing.all" /></span>
            </span>
          </logic:equal>
          <logic:notEqual name="bmk010Form" property="bmk010searchLabel" value='<%=String.valueOf(jp.groupsession.v2.bmk.bmk010.Bmk010Biz.INIT_VALUE)%>'>
            <span class="flo_l">
              <gsmsg:write key="cmn.label" />
              <gsmsg:write key="wml.215" />
              <bean:write name="bmk010Form" property="bmk010searchLabelName" />
              &nbsp;
            </span>
            <div class="flo_l fs_13">
              ／<a href="#!" onClick="return selectLabel(<%=jp.groupsession.v2.bmk.bmk010.Bmk010Biz.INIT_VALUE%>);"><gsmsg:write key="bmk.bmk010.06" /></a>
            </div>
          </logic:notEqual>
          <span class="verAlignMid mr5 fs_14">
            <!-- ソート順 -->
            <bean:define id="sortKey" name="bmk010Form" property="bmk010sortKey" type="java.lang.Integer" />
            <bean:define id="orderKey" name="bmk010Form" property="bmk010orderKey" type="java.lang.Integer" />

            <!-- 登録順 -->
            <%
              if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE
                      && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC) {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);" class="cl_fontBody verAlignMid">
              <span class="verAlignMid"><gsmsg:write key="bmk.17" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span>
            </a>
            <%
              } else if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE
                      && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC) {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC%>);" class="cl_fontBody verAlignMid">
              <span class="verAlignMid"><gsmsg:write key="bmk.17" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span>
            </a>

            <%
              } else {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_ADATE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);">
              <gsmsg:write key="bmk.17" />
            </a>
            <%
              }
            %>
            ／
            <!-- 評価順 -->
            <%
              if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE
                      && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC) {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);" class="cl_fontBody verAlignMid">
              <span class="verAlignMid"><gsmsg:write key="bmk.06" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span>
            </a>

            <%
              } else if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE
                      && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC) {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC%>);" class="cl_fontBody verAlignMid">
              <span class="verAlignMid"><gsmsg:write key="bmk.06" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span>
            </a>

            <%
              } else {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_SCORE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);">
              <gsmsg:write key="bmk.06" />
            </a>
            <%
              }
            %>
            ／
            <!-- タイトル順 -->
            <%
              if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_TITLE
                      && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC) {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_TITLE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);" class="cl_fontBody verAlignMid">
              <span class="verAlignMid"><gsmsg:write key="bmk.bmk010.12" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span>
            </a>

            <%
              } else if (sortKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_TITLE
                      && orderKey.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC) {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_TITLE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_DESC%>);" class="cl_fontBody verAlignMid">
              <span class="verAlignMid"><gsmsg:write key="bmk.bmk010.12" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span>
            </a>

            <%
              } else {
            %>
            <a href="#!" onClick="return sortChange(<%=jp.groupsession.v2.bmk.GSConstBookmark.SORTKEY_TITLE%>, <%=jp.groupsession.v2.bmk.GSConstBookmark.ORDERKEY_ASC%>);">
              <gsmsg:write key="bmk.bmk010.12" />
            </a>
            <%
              }  
            %>
        </span>
        </div>
          <!-- ブックマーク一覧 -->
          <logic:notEmpty name="bmk010Form" property="bmk010BookmarkList">
            <logic:iterate id="bmkMdl" name="bmk010Form" property="bmk010BookmarkList" indexId="idx">
              <table class="w100 bmkDspCmt">
                <tr>
                  <th>
                    <table class="table-left bor1 w100">
                      <tr>

                        <!-- 評価 -->
                        <th class="txt_c bor1 fs_13">
                          <div class="p5 no_w">
                          <bean:define id="imgScore">
                            <bean:write name="bmkMdl" property="bmkScore" />
                          </bean:define>
                          <%
                            java.lang.String key = "0";
                                    if (imgMap.containsKey(imgScore)) {
                                      key = imgScore;
                                    }
                          %>
                          <%=(java.lang.String) imgMap.get(key)%>
                          </div>
                        </th>
                        <!-- タイトル -->
                        <td class="w85 pt5 pb5">
                          <div class="ml5 word_b-all">
                            <a href="<bean:write name="bmkMdl" property="bmuUrl" />" target="_blank">
                              <span class="fs_17"><bean:write name="bmkMdl" property="bmkTitle" /></span>
                            </a>
                            <!-- 登録人数 -->
                            <div class="no_w display_inline-block">
                              <a href="#!" onclick="return selPerCount('<bean:write name="bmkMdl" property="bmuSid" />');" class="bmkCount no_w">
                                <bean:define id="bmkPerCountStr" name="bmkMdl" property="bmkPerCount" type="java.lang.Integer" />
                                <gsmsg:write key="bmk.22" arg0="<%=String.valueOf(bmkPerCountStr.intValue())%>" />
                              </a>
                            </div>
                            <br>
                            <!-- ＵＲＬ -->
                              <logic:notEmpty name="bmkMdl" property="bmuUrlDspList">
                                <span class="fs_13">
                                  <logic:iterate id="urlDsp" name="bmkMdl" property="bmuUrlDspList">
                                    <bean:write name="urlDsp" />
                                    <br>
                                  </logic:iterate>
                                </span>
                              </logic:notEmpty>
                          </div>

                          <div id="comment<bean:write name='idx' />"  class="txt_comment p5 mt10 mb5">
                            <!-- コメント -->
                            <logic:notEmpty name="bmkMdl" property="bmkCmt">
                              <span id="commentNum<bean:write name='idx' />" class="fs_13">
                                <bean:write name="bmkMdl" property="bmkCmt" filter="false" />
                              </span>
                            </logic:notEmpty>
                          </div>
                          <div id="js_commentWidth<bean:write name='idx' />"  class="display_n no_w p5 mt10 mb5">
                            <logic:notEmpty name="bmkMdl" property="bmkCmt">
                              <span class="fs_13">
                                <bean:write name="bmkMdl" property="bmkCmt" filter="false" />
                              </span>
                            </logic:notEmpty>
                          </div>

                        <!-- 表示／隠す -->
                        <div class="txt_l pl5 mb20">
                          <span class="fs_12 no_w">
                           <a href="#!" onclick="hide_comment(<bean:write name='idx' />);">
                             <span id="switchComment<bean:write name='idx' />"><gsmsg:write key="bmk.bmk010.03" /></span>
                           </a>
                         </span>
                        </div>

                        <!-- ラベル -->
                        <p class="ml5 fs_13">
                          <gsmsg:write key="cmn.label" />
                          <gsmsg:write key="wml.215" />
                          <logic:notEmpty name="bmkMdl" property="bmkLabelList">
                            <logic:iterate id="labelMdl" name="bmkMdl" property="bmkLabelList">
                              <a href="#!" onclick="return selectLabel('<bean:write name="labelMdl" property="blbSid" />');">
                                <bean:write name="labelMdl" property="blbName" />
                              </a>
                            </logic:iterate>
                          </logic:notEmpty>

                          <!-- 登録日 -->
                          <br>
                          <span class="date">
                            <gsmsg:write key="bmk.15" />
                            <gsmsg:write key="wml.215" />
                            <bean:write name="bmkMdl" property="bmkAdateDsp" />
                          </span>

                          <!-- 公開区分 -->
                          <logic:equal name="bmkMdl" property="bmkPublic" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_NO)%>'>
                            <logic:equal name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>'>
                              <span class="secret">
                                <gsmsg:write key="cmn.private" />
                              </span>
                            </logic:equal>
                            <logic:equal name="bmk010Form" property="bmk010mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>'>
                              <span class="secret">
                                <gsmsg:write key="bmk.25" />
                              </span>
                            </logic:equal>
                          </logic:equal>
                        </p>

                        </td>
                        <!-- 追加ボタン -->
                        <logic:equal name="bmkMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                          <td class="txt_c txt_m w5 bor1">
                            <button type="button" name="add" class="iconBtn-border" value="<gsmsg:write key="cmn.add" />" onClick="return buttonPushAdd(<bean:write name="bmkMdl" property="bmuSid" />);">
                              <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                              <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                            </button>
                          </td>
                        </logic:equal>
                        <!-- 変更ボタン -->
                        <logic:equal name="bmk010Form" property="bmk010editPow" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.POW_YES)%>'>
                          <td class="txt_c txt_m bor1 w5">
                            <button type="button" name="add" class="iconBtn-border" value="" onClick="return buttonPushEdit(<bean:write name="bmkMdl" property="bmkSid" />);">
                              <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.add" />">
                              <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.add" />">
                            </button>
                          </td>
                        </logic:equal>
                        <!-- 削除チェックボックス -->
                        <logic:equal name="bmk010Form" property="bmk010editPow" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.POW_YES)%>'>
                          <td class="w5 txt_c txt_m js_tableTopCheck cursor_p">
                            <html:multibox name="bmk010Form" property="bmk010delInfSid">
                              <bean:write name="bmkMdl" property="bmkSid" />
                            </html:multibox>
                          </td>
                        </logic:equal>
                      </tr>
                    </table>
                  </th>
                </tr>

              </table>
            </logic:iterate>
          </logic:notEmpty>

          <!-- ページング -->
          <logic:notEmpty name="bmk010Form" property="bmk010pageCmbList">

            <div class="paging">
              <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo" property="bmk010pageBottom" onchange="changePage('bmk010pageBottom');">
                <html:optionsCollection name="bmk010Form" property="bmk010pageCmbList" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:notEmpty>
      </div>
      </div>
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
  </html:form>
</body>
</html:html>