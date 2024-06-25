<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.cht100.01" /></title>

<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../chat/js/cht100.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../chat/js/chtmain.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>

</head>

<body onunload="windowClose();">

<html:form action="/chat/cht100">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="cgiSid" />
<html:hidden property="cht100InitFlg" />
<html:hidden property="cht100ProcMode" />
<html:hidden property="cht100SortKey" />
<html:hidden property="cht100OrderKey" />
<html:hidden property="cht100SearchFlg" />
<input type="hidden" name="cht100CreateYearFr" value="" />
<input type="hidden" name="cht100CreateMonthFr" value="" />
<input type="hidden" name="cht100CreateDayFr" value="" />
<input type="hidden" name="cht100CreateYearTo" value="" />
<input type="hidden" name="cht100CreateMonthTo" value="" />
<input type="hidden" name="cht100CreateDayTo" value="" />
<input type="hidden" name="cht100UpdateYearFr" value="" />
<input type="hidden" name="cht100UpdateMonthFr" value="" />
<input type="hidden" name="cht100UpdateDayFr" value="" />
<input type="hidden" name="cht100UpdateYearTo" value="" />
<input type="hidden" name="cht100UpdateMonthTo" value="" />
<input type="hidden" name="cht100UpdateDayTo" value="" />
<html:hidden property="svCht100Keyword" />
<html:hidden property="svCht100AndOr" />
<html:hidden property="svCht100GroupId" />
<html:hidden property="svCht100GroupName" />
<html:hidden property="svCht100GroupInfo" />
<html:hidden property="svCht100Category" />
<html:hidden property="svCht100StatusKbn" />
<html:hidden property="svCht100SelectGroup" />
<html:hidden property="svCht100SelectUser" />
<html:hidden property="svCht100AdminMember" />
<html:hidden property="svCht100GeneralMember" />
<html:hidden property="svCht100CreateYearFr" />
<html:hidden property="svCht100CreateMonthFr" />
<html:hidden property="svCht100CreateDayFr" />
<html:hidden property="svCht100CreateYearTo" />
<html:hidden property="svCht100CreateMonthTo" />
<html:hidden property="svCht100CreateDayTo" />
<html:hidden property="svCht100CreateDateFr" />
<html:hidden property="svCht100CreateDateTo" />
<html:hidden property="svCht100UpdateYearFr" />
<html:hidden property="svCht100UpdateMonthFr" />
<html:hidden property="svCht100UpdateDayFr" />
<html:hidden property="svCht100UpdateYearTo" />
<html:hidden property="svCht100UpdateMonthTo" />
<html:hidden property="svCht100UpdateDayTo" />
<html:hidden property="svCht100UpdateDateFr" />
<html:hidden property="svCht100UpdateDateTo" />
<input type="hidden" name="yearRangeMinFr" value="<bean:write name="cht100Form" property="cht100OldYear" />" />
<input type="hidden" name="yearRangeMaxFr" value="0" />
<input type="hidden" name="yearRangeMinTo" value="<bean:write name="cht100Form" property="cht100OldYear" />" />
<input type="hidden" name="yearRangeMaxTo" value="0" />

<div class="kanriPageTitle w100 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
     <li class="pageTitle_subFont">
       <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht100.01" />
     </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="user.37" />" onClick="clickAddEdit('addEditGrp',-1,0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="user.37" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="user.37" />">
          <gsmsg:write key="user.37" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToAdmin');">
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
    <html:errors/>
  </logic:messagesPresent>

  <!-- 検索 -->
  <table class="table-left w100">
    <tr>
      <td colspan="4" class="w100 table_title-color">
        <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="mb5 table_header_icon_search classic-display">
        <span class="table_title-color"><gsmsg:write key="cmn.search" /></span>
      </td>
    </tr>

    <tr>
      <!-- キーワード -->
      <th class="w15 txt_c no_w">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w45">
        <html:text name="cht100Form" property="cht100Keyword" maxlength="100" styleClass="search_keyword_form_width" />
        <br>
        <div class="verAlignMid mt5">
          <html:radio name="cht100Form" property="cht100AndOr" styleId="cht100AndOr_01" value="<%=String.valueOf(GSConstChat.KEYWORDKBN_AND)%>" styleClass="radio_position"/>
          <label for="cht100AndOr_01" class="mr10"><gsmsg:write key="cmn.contains.all.and" /></label>
          <html:radio name="cht100Form" property="cht100AndOr" styleId="cht100AndOr_02" value="<%=String.valueOf(GSConstChat.KEYWORDKBN_OR)%>" styleClass="radio_position"/>
          <label for="cht100AndOr_02"><gsmsg:write key="cmn.orcondition" /></label>
        </div>
      </td>
      <!-- 検索条件 -->
      <th class="w15 txt_c no_w">
        <gsmsg:write key="cmn.search.criteria" />
      </th>
      <td class="w25">
        <div class="verAlignMid">
          <html:checkbox name="cht100Form" property="cht100GroupId" value="<%=String.valueOf(GSConstChat.SEARCH_GROUPID_IN)%>" styleId="cht100GroupId" />
          <label for="cht100GroupId" class="mr10"><gsmsg:write key="cmn.group.id" /></label>
        </div>
        <br>
        <div class="verAlignMid">
          <html:checkbox name="cht100Form" property="cht100GroupName" value="<%=String.valueOf(GSConstChat.SEARCH_GROUPNAME_IN)%>" styleId="cht100GroupName" />
          <label for="cht100GroupName" class="mr10"><gsmsg:write key="cmn.group.name" /></label>
        </div>
        <br>
        <div class="verAlignMid">
          <html:checkbox name="cht100Form" property="cht100GroupInfo" value="<%=String.valueOf(GSConstChat.SEARCH_GROUPINFO_IN)%>" styleId="cht100GroupInfo" />
          <label for="cht100GroupInfo"><gsmsg:write key="cmn.memo" /></label>
        </div>
      </td>
    </tr>

    <!-- カテゴリ -->
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w85" colspan="3">
        <logic:notEmpty name="cht100Form" property="cht100CategoryList">
          <html:select property="cht100Category" styleId="category" styleClass="mwp150">
            <html:optionsCollection name="cht100Form" property="cht100CategoryList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>

    <!-- 状態区分 -->
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="anp.state" /><gsmsg:write key="tcd.tcd070.03" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid">
          <html:radio name="cht100Form" property="cht100StatusKbn" styleId="cht100StatusKbn_01" value="<%=String.valueOf(GSConstChat.SEARCH_STATUSKBN_ALL)%>" />
          <label for="cht100StatusKbn_01" class="mr10"><gsmsg:write key="cmn.all" /></label>
          <html:radio name="cht100Form" property="cht100StatusKbn" styleId="cht100StatusKbn_02" value="<%=String.valueOf(GSConstChat.SEARCH_STATUSKBN_NOT_ARCHIVE)%>" />
          <label for="cht100StatusKbn_02" class="mr10"><gsmsg:write key="cht.cht100.03" /></label>
          <html:radio name="cht100Form" property="cht100StatusKbn" styleId="cht100StatusKbn_03" value="<%=String.valueOf(GSConstChat.SEARCH_STATUSKBN_ARCHIVE_ONLY)%>" />
          <label for="cht100StatusKbn_03"><gsmsg:write key="cht.cht100.04" /></label>
        </div>
      </td>
    </tr>

    <!-- メンバー -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.member" />
      </th>
      <td class="w85" colspan="3">
        <html:select property="cht100SelectGroup" styleClass="mwp150" styleId="cht100SelectGroup" onchange="changeCombo('init');">
          <html:optionsCollection name="cht100Form" property="cht100GroupList" value="value" label="label" />
        </html:select>
        <button class="iconBtn-border " type="button" id="cht100GroupBtn" value="&nbsp;&nbsp;" onclick="openGroupWindow(this.form.cht100SelectGroup, 'cht100SelectGroup', '0');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>

        <html:select property="cht100SelectUser" styleId="cht100SelectUser" styleClass="mwp150">
          <logic:iterate id="user" name="cht100Form" property="cht100UserList">
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

        <br>
        <div class="verAlignMid mt5">
          <html:checkbox name="cht100Form" property="cht100AdminMember" value="<%=String.valueOf(GSConstChat.SEARCH_GROUPADMIN_IN)%>" styleId="cht100AdminMember" />
          <label for="cht100AdminMember" class="mr10"><gsmsg:write key="cht.cht100.10" /></label>
          <html:checkbox name="cht100Form" property="cht100GeneralMember" value="<%=String.valueOf(GSConstChat.SEARCH_GENERALUSER_IN)%>" styleId="cht100GeneralMember" />
          <label for="cht100GeneralMember"><gsmsg:write key="cht.cht100.05" /></label>
        </div>
      </td>
    </tr>

    <!-- 作成日時 -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="rng.37" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid w100">
          <html:text name="cht100Form" property="cht100CreateDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <span class="ml5 mr5"><gsmsg:write key="tcd.153" /></span>
          <html:text name="cht100Form" property="cht100CreateDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
        </div>
      </td>
    </tr>

    <!-- 最終投稿日時 -->
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="bbs.bbsMain.2" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid w100">
          <html:text name="cht100Form" property="cht100UpdateDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <span class="ml5 mr5"><gsmsg:write key="tcd.153" /></span>
          <html:text name="cht100Form" property="cht100UpdateDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
        </div>
      </td>
    </tr>
  </table>

  <div>
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>

  <logic:notEmpty name="cht100Form" property="cht100GrpDataList">

    <!-- ページング -->
    <logic:notEmpty name="cht100Form" property="pageList">
    <div class="txt_r mt10">
      <span class="paging">
         <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
           <i class="icon-paging_left"></i>
         </button>
          <html:select property="cht100PageTop" onchange="selectPage(0);" styleClass="paging_combo">
            <html:optionsCollection name="cht100Form" property="pageList" value="value" label="label" />
          </html:select>
         <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
           <i class="icon-paging_right"></i>
         </button>
      </span>
    </div>
    </logic:notEmpty>

    <table class="table-top w100">
      <tr>
        <bean:define id="chtSortKey" name="cht100Form" property="cht100SortKey" type="java.lang.Integer" />
        <bean:define id="chtOrder" name="cht100Form" property="cht100OrderKey" type="java.lang.Integer" />
        <%
          jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
          String down = gsMsg.getMessage(request, "tcd.tcd040.23");
          String up = gsMsg.getMessage(request, "tcd.tcd040.22");
          String sortSign = up;
          String nextOrder = "";

          String[] widthList = { "w20", "w60", "w10", "w10" };
          String[] titleList = { gsMsg.getMessage(request, "main.src.man220.6"),
                                gsMsg.getMessage(request, "cmn.group.name"),
                                gsMsg.getMessage(request, "rng.37"),
                                gsMsg.getMessage(request, "bbs.bbsMain.2") };

          int[] sortKeyList = { 0, 1, 2, 3 };
          for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
              if (chtSortKey.intValue() == sortKeyList[titleIdx]) {
                  if (chtOrder.intValue() == 1) {
                      sortSign = down;
                      nextOrder = "0";
                  } else {
                      sortSign = up;
                      nextOrder = "1";
                  }
              } else {
                  nextOrder = "0";
                  sortSign = "";
              }
        %>
        <th class="<%=widthList[titleIdx]%> table_title-color cursor_p no_w">
          <a class="table_headerSort-top" href="#" onClick="chatGroupSort(<%= String.valueOf(sortKeyList[titleIdx])%>);">
            <%= titleList[titleIdx] %>
            <% if (sortSign.length() > 0) { %>
              <span class="classic-display"><%= sortSign %></span>
              <span class="original-display txt_m">
                <% if (sortSign.equals(down)) {%>
                  <i class="icon-sort_down"></i>
                <%} else if (sortSign.equals(up)) { %>
                  <i class="icon-sort_up"></i>
                <% } %>
              </span>
            <% } %>
          </a>
        </th>
        <% } %>
      </tr>

      <logic:iterate id="chtData" name="cht100Form" property="cht100GrpDataList" indexId="idx">
        <tr class="js_listHover cursor_p" id="<bean:write name="chtData" property="chatSid" />">
          <td class="txt_l js_list_Click">
            <bean:write name="chtData" property="chatId" />
          </td>
          <td class="txt_l js_list_Click cl_linkDef">
            <span class="cl_linkDef"><bean:write name="chtData" property="chatName"/></span>
          </td>
          <td class="txt_c js_list_Click">
            <bean:write name="chtData" property="strInsertDate" />
          </td>
          <td class="txt_c js_list_Click">
            <bean:write name="chtData" property="strLastSendDate" />
          </td>
        </tr>
      </logic:iterate>
    </table>

    <!-- ページング -->
    <logic:notEmpty name="cht100Form" property="pageList">
      <div class="txt_r mt10">
        <span class="paging">
          <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select property="cht100PageBottom" onchange="selectPage(1);" styleClass="paging_combo">
            <html:optionsCollection name="cht100Form" property="pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </span>
      </div>
    </logic:notEmpty>

  </logic:notEmpty>

</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</html:form>
</body>

</html:html>
