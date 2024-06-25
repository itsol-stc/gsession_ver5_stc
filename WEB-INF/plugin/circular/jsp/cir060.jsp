<%@page import="jp.groupsession.v2.cir.model.CirAccountModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/circular/" prefix="circular" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%-- 回覧板種別 --%>
<%
String jusin = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_JUSIN);
String sosin = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_SOUSIN);
String gomi  = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_GOMI);
%>

<%-- キーワード区分 --%>
<%
String keyWordAnd = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.KEY_WORD_KBN_AND);
String keyWordOr  = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.KEY_WORD_KBN_OR);
%>

<%-- オーダー区分 --%>
<%
String orderAsc  = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC);
String orderDesc = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC);
%>

<%-- ソート区分 --%>
<%
String sortTitle  = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SORT_TITLE);
String sortDate  = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SORT_DATE);
String sortUser  = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SORT_USER);
%>

<%-- 検索対象 --%>
<%
String targetTitle   = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SEARCH_TARGET_TITLE);
String targetHonbun  = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SEARCH_TARGET_BODY);
%>
<% String unopen = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_UNOPEN); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../circular/js/cir060.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../circular/css/circular.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<logic:notEmpty name="cir060Form" property="cir010AccountTheme" scope="request">
  <bean:define id="selectThemePath" name="cir060Form" property="cir010AccountTheme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="cir060Form" property="cir010AccountTheme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>
<title>GROUPSESSION <gsmsg:write key="cir.cir060.1" /></title>
</head>

<body onload="syubetsuChange();">

<html:form action="/circular/cir060">

<input type="hidden" name="CMD">
<input type="hidden" name="cir010selectInfSid">
<input type="hidden" name="cir010sojuKbn">
<input type="hidden" name="cir060dspId" value="cir060">
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="cir060searchFlg" />
<html:hidden property="cir010svSearchWord" />
<html:hidden property="cir060svSyubetsu" />
<html:hidden property="cir060svGroupSid" />
<html:hidden property="cir060svUserSid" />
<html:hidden property="cir060svWordKbn" />
<html:hidden property="cir060svSort1" />
<html:hidden property="cir060svOrder1" />
<html:hidden property="cir060svSort2" />
<html:hidden property="cir060svOrder2" />

<%--
<logic:notEmpty name="cir060Form" property="cir060selUserSid" scope="request">
  <logic:iterate id="item" name="cir060Form" property="cir060selUserSid" scope="request">
    <input type="hidden" name="cir060selUserSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>
--%>

<logic:notEmpty name="cir060Form" property="cir060svSelUserSid" scope="request">
  <logic:iterate id="item" name="cir060Form" property="cir060svSelUserSid" scope="request">
    <input type="hidden" name="cir060svSelUserSid" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cir060Form" property="cir060svSearchTarget" scope="request">
  <logic:iterate id="item" name="cir060Form" property="cir060svSearchTarget" scope="request">
    <input type="hidden" name="cir060svSearchTarget" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

 <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../circular/images/classic/header_circular.png" alt="<gsmsg:write key="cir.5" />">
          <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_circular_50.png" alt="<gsmsg:write key="cir.5" />">
        </li>
        <li>
          <gsmsg:write key="cir.5" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cir.cir060.1" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backList');">
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


  <table class="table-left">

 <tr>
    <th colspan="4" class="w100 bgC_header1 table_title-color">
        <img class="header_pluginImg-classic table_header_icon_search" src="../common/images/classic/icon_search.png" class="img_bottom" alt="<gsmsg:write key="cmn.advanced.search" />">
        <span class="table_title-color"><gsmsg:write key="cmn.advanced.search" /></span>
    </th>
    </tr>

    <tr>
    <th class="w10 no_w txt_c"><gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" /></th>
    <td class="w90" colspan="3">
      <bean:define id="mukoUserClass" value=""/>
      <logic:equal name="cir060Form" property="cirViewAccountUko" value="1"><bean:define id="mukoUserClass" >mukoUser-color</bean:define></logic:equal>
      <span class="<%=mukoUserClass%>"><bean:write name="cir060Form" property="cirViewAccountName" /></span>
    </td>
    </tr>

    <tr>
    <th class="w10 no_w txt_c"><gsmsg:write key="cir.cir060.2" /></th>
    <td class="w90" colspan="3">
      <span class="verAlignMid txt_m">
        <html:radio property="cir060syubetsu" styleId="radio_jushin" value="<%= jusin %>" onclick="syubetsuChange();" /><label for="radio_jushin"><gsmsg:write key="cmn.receive2" /></label>
        <html:radio property="cir060syubetsu" styleClass="ml10" styleId="radio_soushin" value="<%= sosin %>" onclick="syubetsuChange();" /><label for="radio_soushin"><gsmsg:write key="cmn.sent2" /></label>
        <html:radio property="cir060syubetsu" styleClass="ml10" styleId="radio_gomi" value="<%= gomi %>" onclick="syubetsuChange();" /><label for="radio_gomi"><gsmsg:write key="cmn.trash" /></label>
      </span>
    </td>
    </tr>

    <tr>
    <th class="w10 no_w txt_c"><gsmsg:write key="cir.2" /></th>
    <td class="w50">

            <div class="verAlignMid">
              <html:select styleClass="wp200" name="cir060Form" property="cir060groupSid" onchange="hassinChange('searchAgain');">

       <logic:notEmpty name="cir060Form" property="groupLabel">
          <logic:iterate id="gpBean" name="cir060Form" property="groupLabel" scope="request">
          <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
          <logic:equal name="gpBean" property="styleClass" value="0">
            <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
          </logic:equal>
          <logic:equal name="gpBean" property="styleClass" value="1">
            <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
          </logic:equal>
          <logic:equal name="gpBean" property="styleClass" value="2">
            <html:option styleClass="select_daihyo-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
          </logic:equal>
          </logic:iterate>
        </logic:notEmpty>

              </html:select>
              <button class="iconBtn-border ml5" type="button" id="cir060GroupBtn" value="" onClick="openGroupWindowForCircular(this.form.cir060groupSid, 'cir060groupSid', '0', 'searchAgain', 0);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </div>
            <span class="fs_13">
              <gsmsg:write key="cmn.user" />
            </span>

      <html:select property="cir060userSid" styleClass="wp200">
          <logic:notEmpty name="cir060Form" property="userLabel" >
          <logic:iterate id="usrList" name="cir060Form" property="userLabel" >
            <bean:define id="mukoUserClass" value=""/>
            <logic:equal name="usrList" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUserOption"/></logic:equal>
            <bean:define id="usrValue"  >-1</bean:define>
            <logic:notEmpty name="usrList" property="cacSid">
              <bean:define id="usrValue" ><bean:write name="usrList" property="cacSid"/></bean:define>
              <bean:define id="usrLabel" ><bean:write name="usrList" property="cacName" /></bean:define>
            </logic:notEmpty>
            <logic:empty name="usrList" property="cacSid">
              <logic:notEmpty name="usrList" property="usrSid">
                <bean:define id="usrValue" ><bean:write name="usrList" property="usrSid"/></bean:define>
              </logic:notEmpty>
              <bean:define id="usrLabel" ><bean:write name="usrList" property="usiSei" /> <bean:write name="usrList" property="usiMei" /></bean:define>
            </logic:empty>
            <html:option styleClass="<%=mukoUserClass %>" value="<%=usrValue %>"><bean:write name="usrLabel" /></html:option>
          </logic:iterate>
          </logic:notEmpty>


      </html:select>
    </td>

    <th class="w10 no_w txt_c"><gsmsg:write key="cir.20" /></th>
    <td class="w30">
      <button type="button" id="<gsmsg:write key="cmn.from" />" class="cir_send_sel_btn baseBtn" value="<gsmsg:write key="cmn.select" />" name="selectBtn" /><gsmsg:write key="cmn.select" />
        <span class="js_mailSendSelBtn_data"
               data-displayname="<gsmsg:write key="cir.20" />"
               data-addarea="#atesaki_to_area"
               data-inputname="cir060selUserSid"
             />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" name="clearBtn" onClick="clearUserList();"><gsmsg:write key="cmn.clear" /></button>
      <span id="atesaki_to_area">
      <logic:notEmpty name="cir060Form" property="memberList" scope="request">
      <logic:iterate id="memMdl" name="cir060Form" property="memberList" scope="request" type="CirAccountModel">

        <div class="atesaki_to_user" id="0">
          <circular:selectedAccountList memMdl="<%=memMdl%>" inputName="cir060selUserSid" />
        </div>
      </logic:iterate>
      </logic:notEmpty>
      </span>
    </td>
    </tr>

    <tr>
    <th class="no_w txt_c"><gsmsg:write key="cmn.keyword" /></th>
    <td>
      <html:text property="cir010searchWord" styleClass="wp250" maxlength="100"/>
      <div>
        <span class="verAlignMid txt_m">
          <html:radio property="cir060wordKbn" value="<%= keyWordAnd %>" styleId="keyKbn_01" /><label for="keyKbn_01"><gsmsg:write key="cmn.contains.all" />(AND)</label>
          <html:radio property="cir060wordKbn" value="<%= keyWordOr %>" styleClass="ml10" styleId="keyKbn_02" /><label for="keyKbn_02"><gsmsg:write key="cmn.containing.either" />(OR)</label>
        </span>
      </div>
    </td>

    <th class="no_w txt_c"><gsmsg:write key="cmn.search2" /></th>
    <td>
      <span class="verAlignMid">
        <html:multibox styleId="search_scope_01" property="cir060searchTarget" value="<%= targetTitle %>" /><label for="search_scope_01"><gsmsg:write key="cmn.title" /></label>
        <html:multibox styleClass="ml10" styleId="search_scope_02" property="cir060searchTarget" value="<%= targetHonbun %>" /><label for="search_scope_02"><gsmsg:write key="cmn.content" /></label>
      </span>
    </td>
    </tr>

    <tr>
    <th class="no_w txt_c"><gsmsg:write key="cmn.sort.order" /></th>
    <td colspan="3">
    <span><gsmsg:write key="cmn.first.key" /></span>
      <html:select property="cir060sort1" styleClass="select04">
        <html:optionsCollection name="cir060Form" property="sortLabel" value="value" label="label" />
      </html:select>
      <span class="verAlignMid txt_m mr10">
        <html:radio property="cir060order1" value="<%= orderAsc %>" styleId="sort1_up" /><label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
        <html:radio property="cir060order1" value="<%= orderDesc %>"  styleClass="ml10" styleId="sort1_dw" /><label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
      </span>
    <span><gsmsg:write key="cmn.second.key" /></span>
      <html:select property="cir060sort2" styleClass="select04">
        <html:optionsCollection name="cir060Form" property="sortLabel" value="value" label="label" />
      </html:select>
      <span class="verAlignMid txt_m">
        <html:radio property="cir060order2" value="<%= orderAsc %>" styleId="sort2_up" /><label for="sort2_up"><gsmsg:write key="cmn.order.asc" /></label>
        <html:radio property="cir060order2" value="<%= orderDesc %>" styleClass="ml10" styleId="sort2_dw" /><label for="sort2_dw"><gsmsg:write key="cmn.order.desc" /></label>
      </span>
    </td>
    </tr>
    </table>

    <div class="txt_c">
      <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onclick="buttonPush('searchCir');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <gsmsg:write key="cmn.search" />
      </button>
    </div>

<logic:notEmpty name="cir060Form" property="circularList" scope="request">

    <bean:size id="count1" name="cir060Form" property="pageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
   <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="cir060pageNum1" onchange="changePage(1);">
      <html:optionsCollection name="cir060Form" property="pageLabel" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('next');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
    </logic:greaterThan>

    <table class="table-top">
    <logic:equal name="cir060Form" property="cir060svSyubetsu" value="<%= jusin %>">
      <%@ include file="/WEB-INF/plugin/circular/jsp/cir060_sub01.jsp" %>
    </logic:equal>

    <logic:equal name="cir060Form" property="cir060svSyubetsu" value="<%= sosin %>">
      <%@ include file="/WEB-INF/plugin/circular/jsp/cir060_sub02.jsp" %>
    </logic:equal>

    <logic:equal name="cir060Form" property="cir060svSyubetsu" value="<%= gomi %>">
      <%@ include file="/WEB-INF/plugin/circular/jsp/cir060_sub03.jsp" %>
    </logic:equal>
    </table>

    <logic:notEqual name="cir060Form" property="cir060searchUse" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_NOT_USE) %>">
    <logic:notEmpty name="cir060Form" property="cir060WebSearchWord">
    <table class="w100">
    <tr>
      <td class="txt_l">
      <bean:define id="searchKeyword" name="cir060Form" property="cir060HtmlSearchWord" type="java.lang.String" />
      <a href="#!" onClick="webSearch('<bean:write name="cir060Form" property="cir060WebSearchWord" />');">
        <span id="webSearchArea">
        <gsmsg:write key="cmn.websearch" arg0="<%= searchKeyword %>" />
        </span>
      </a>
      </td>
    </tr>
    </logic:notEmpty>
    </logic:notEqual>


    <bean:size id="count2" name="cir060Form" property="pageLabel" scope="request" />
    <logic:greaterThan name="count2" value="1">
   <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="cir060pageNum2" onchange="changePage(2);">
      <html:optionsCollection name="cir060Form" property="pageLabel" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('next');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
    </logic:greaterThan>

</logic:notEmpty>

<!-- ページコンテンツ end -->
  </td>
  </tr>

  </table>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
<circular:accountSelector name="cir060Form" property="cir060MemberSelector"/>


</body>
</html:html>
