<%@page import="jp.groupsession.v2.cmn.login.UserAgent"%>
<%@page import="jp.groupsession.v2.cmn.GSConstApi"%>
<%@page import="jp.groupsession.v2.api.api030.Api030Biz"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>



<html:html>
<head>

<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">

<title>GROUPSESSION <gsmsg:write key="main.man002.53" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<gsjsmsg:js filename="gsjsmsg.js"/>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script type="text/javascript" src="../common/js/check.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script type="text/javascript" src="../api/js/api030.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>

<script type="text/javascript">
<!--
var msglist_api030 = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['api.api030.11'] = '<gsmsg:write key="api.api030.11"/>';
  return ret;
})();
-->
</script>
</head>

<body class="body_03">
<html:form action="/api/api030">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="api030page" />

<html:hidden property="api030cliantCRSv" />
<html:hidden property="api030cliantAppSv" />
<html:hidden property="api030cliantOtherSv" />
<html:hidden property="api030userSv" />
<html:hidden property="api030groupSv" />
<html:hidden property="api030sortKeySv" />
<html:hidden property="api030orderKeySv" />
<html:hidden property="api030targetDisabledSv" />
<html:hidden property="api030delete" value=""/>
<html:hidden property="api030cmnCallFlg" />



<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->
<bean:define id="heightControl" value=""/>
<logic:notEqual name="api030Form" property="api030cmnCallFlg" value="0">
  <bean:define id="heightControl" value="hp_auto"/>
</logic:notEqual>

<div class="kanriPageTitle w100 <%=heightControl %>" >
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="api.1" /></span><gsmsg:write key="api.api030.1" />
    </li>
    <li>
      <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('api030Back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </logic:equal>
    </li>
  </ul>
</div>
<div class="wrapper display_tbl w100">
  <logic:messagesPresent message="false">
    <html:errors />
  </logic:messagesPresent>
  <div class="txt_l">
    <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
      <button type="button" name="btn_token" class="baseBtn ml_auto js_createToken mb5" value="<gsmsg:write key="api.api030.3" />">
        <gsmsg:write key="api.api030.10" />
      </button>
    </logic:equal>
    <logic:notEqual name="api030Form" property="api030cmnCallFlg" value="0">
      <button type="button" name="btn_token" class="baseBtn ml_auto js_createUseToken mb5" value="<gsmsg:write key="api.api030.3" />">
        <gsmsg:write key="api.api030.10" />
      </button>
    </logic:notEqual>
    <div id="tokenCreatePop" class="display_n bgC_body p10">
      <div class="w100">
        <logic:notEmpty name="api030Form" property="api030tokenGroupLabel">
          <html:select property="api030createTokenGroup" styleClass="wp200 mr5 js_groupChange">
            <html:optionsCollection name="api030Form" property="api030tokenGroupLabel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        <logic:notEmpty name="api030Form" property="api030tokenUserLabel">
          <html:select property="api030createTokenUser" styleClass="wp200 js_tokenUser">
            <html:optionsCollection name="api030Form" property="api030tokenUserLabel" value="value" label="label" styleClass="js_userListContet" />
          </html:select>
        </logic:notEmpty>
      </div>
      <div class="m5 txt_c">
        <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
          <button type="button" name="btn_token" class="baseBtn ml_auto js_createTokenPop" value="<gsmsg:write key="api.api030.10" />">
            <gsmsg:write key="api.api030.10" />
          </button>
        </logic:equal>
        <logic:notEqual name="api030Form" property="api030cmnCallFlg" value="0">
          <button type="button" name="btn_token" class="baseBtn ml_auto js_createUseTokenPop" value="<gsmsg:write key="api.api030.10" />">
            <gsmsg:write key="api.api030.10" />
          </button>
        </logic:notEqual>
      </div>
      <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
        <div class="txt_l">
          <div>
            <gsmsg:write key="cmn.token" />
          </div>
          <div class="verAlignMid">
            <input type="text" class="js_tokenDisp wp380" readonly="readonly"/>
            <div class="ml5">
              <img class="btn_classicImg-display img-18 cursor_p" src="../common/images/classic/icon_copy.png" alt="<gsmsg:write key="cmn.url" />" onClick="tokenCopy();">
              <img class="btn_originalImg-display img-18 cursor_p" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.url" />" onClick="tokenCopy();">
            </div>
          </div>
        </div>
        <div class="m5">
          <common:toast toastId="tokenParam">
            <gsmsg:write key="api.api030.11" />
          </common:toast>
        </div>
      </logic:equal>
    </div>
  </div>
  <div class="table_title-color bor1 border_bottom_none w100 hp25"></div>
  <table class="table-left mt0 mb10 w100 ">
    <tr>
      <%-- クライアント --%>
      <th ><gsmsg:write key="cmn.client" /></th>
      <td>
        <div class="verAlignMid mr10">
          <html:checkbox styleId="search_scope_01" property="api030cliantCR" value="<%=String.valueOf(UserAgent.CLIENT_TYPE_CROSSRIDE) %>" />
          <label for="search_scope_01"><gsmsg:write key="cmn.crossride" /></label>
        </div><!--
     --><div class="verAlignMid mr10">
          <html:checkbox styleId="search_scope_02" property="api030cliantApp" value="<%=String.valueOf(UserAgent.CLIENT_TYPE_GSMOBILE) %>" />
          <label for="search_scope_02"><gsmsg:write key="cmn.gsmobile" /></label>
        </div><!--
     --><div class="verAlignMid mr10">
          <html:checkbox styleId="search_scope_03" property="api030cliantOther" value="<%=String.valueOf(UserAgent.CLIENT_TYPE_OTHER) %>" />
          <label for="search_scope_03"><gsmsg:write key="cmn.other" /></label>
        </div>
      </td>
      <%-- ユーザ --%>
      <th ><gsmsg:write key="cmn.user" /></td>
      <td >
        <div class="verAlignMid w100">
          <logic:notEmpty name="api030Form" property="api030groupLabel">
            <html:select property="api030group" styleClass="wp200 mr5" onchange="buttonPush('dsp')">
              <html:optionsCollection name="api030Form" property="api030groupLabel" value="value" label="label" />
            </html:select>
            <button class="iconBtn-border mr5" type="button" id="man250GroupBtn" value="" onClick="openGroupWindowForApi030(this.form.api030group, 'api030group', '0', 'dsp')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </logic:notEmpty>
          <logic:notEmpty name="api030Form" property="api030usrLabel">
            <html:select property="api030user" styleClass="wp200">
              <logic:iterate id="user" name="api030Form" property="api030usrLabel">
                 <option class="<logic:equal name="user" property="usrUkoFlg" value="1">mukoUser-color</logic:equal>" value="<bean:write name="user" property="value"/>"><bean:write name="user" property="label"/></option>
              </logic:iterate>
            </html:select>
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <%-- ソート順 --%>
      <th><gsmsg:write key="cmn.sort.order" /></td>
      <td>
        <div class="verAlignMid w100">
          <div class="display_inline mr5">
            <gsmsg:write key="cmn.first.key" />
          </div>
          <div class="display_inline mr10">
            <html:select property="api030sortKey" styleClass="select01">
              <logic:notEmpty name="api030Form" property="sortLabel">
                <html:optionsCollection name="api030Form" property="sortLabel" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </div>
          <div class="verAlignMid  mr10">
            <html:radio name="api030Form" property="api030orderKey" styleId="sort1_up" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
            <label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
          </div>
          <div class="verAlignMid  mr10">
            <html:radio name="api030Form" property="api030orderKey" styleId="sort1_dw" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
            <label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
          </div>
        </div>
      </td>
      <%-- 検索対象 --%>
      <th><gsmsg:write key="cmn.search2" /></td>
      <td>
        <div class="verAlignMid">
          <html:checkbox name="api030Form" property="api030targetDisabled" value="<%=String.valueOf(Api030Biz.SEARCH_TARGET_DISABLED_ON)%>" styleId="targetDisabled" />
          <label for="targetDisabled"><gsmsg:write key="api.api030.12" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="display_flex mb10">
    <div class="wp200"></div>
    <button type="button" class="baseBtn mrl_auto" onClick="buttonPush('api030Search');" value="<gsmsg:write key="cmn.search" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <gsmsg:write key="cmn.search" />
    </button>
    <div class="wp200 display_inline">
      <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
      <logic:notEmpty name="api030Form" property="api030DspList">
        <button type="button" name="btn_del" class="baseBtn ml_auto" onClick="buttonPush('api030Muko');" value="<gsmsg:write key="api.api030.3" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete2" />">
          <gsmsg:write key="api.api030.3" />
        </button>
      </logic:notEmpty>
      </logic:equal>
    </div>
  </div>
  <logic:notEmpty name="api030Form" property="api030DspList">
    <bean:size id="count1" name="api030Form" property="api030PageLabel" scope="request" />
    <logic:greaterThan name="count1" value="1">

      <div class="paging w100">
        <button type="button" class="ml_auto webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="api030pageSel" onchange="changePage(this);" styleClass="paging_combo">
          <html:optionsCollection name="api030Form" property="api030PageLabel" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
    <table class="table-top table-fixed mt0 mb0">
      <tr>
        <%-- 検索項目ヘッダ --%>
        <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
          <th class="wp25 js_tableTopCheck js_tableTopCheck-header cursor_p" onChange="chgCheckAll('allCheck', 'api030delMulti'); return false;"><input class="js_token_check" type="checkbox" name="allCheck"></th>
        </logic:equal>
        <th class="w25 cursor_p" onClick="clickSortTitle(<%= String.valueOf(Api030Biz.TOKEN_SORTKEY_USER) %>); return false;"><a href="#!" ><span class="text_bb2"><gsmsg:write key="cmn.user" /></span></a></th>
        <th class="wp100 cursor_p"  onClick="clickSortTitle(<%= String.valueOf(Api030Biz.TOKEN_SORTKEY_CLIENT) %>); return false;"><a href="#!"><span class="text_bb2"><gsmsg:write key="cmn.client" /></span></a></th>
        <th class="wp100 cursor_p" onClick="clickSortTitle(<%= String.valueOf(Api030Biz.TOKEN_SORTKEY_ADATE) %>); return false;""><a href="#!"><span class="text_bb2"><gsmsg:write key="api.api030.4" /></span></a></th>
        <th class="wp100 cursor_p" onClick="clickSortTitle(<%= String.valueOf(Api030Biz.TOKEN_SORTKEY_LDATE) %>); return false;"><a href="#!" ><span class="text_bb2"><gsmsg:write key="api.api030.5" /></span></a></th>
        <th class="wp150"><gsmsg:write key="api.api030.6" /></th>
        <th class="w25"><gsmsg:write key="api.api030.7" /></th>
        <th class="wp200"><gsmsg:write key="cmn.token" /></th>
        <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
          <th class="wp80"></th>
        </logic:equal>
        <logic:notEqual name="api030Form" property="api030cmnCallFlg" value="0">
          <th class="wp100"></th>
        </logic:notEqual>
      </tr>
      <logic:iterate id="dspMdl" name="api030Form" property="api030DspList" indexId="idx">
        <tr>
          <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
          <td class="txt_c cursor_p  js_tableTopCheck">
             <logic:equal name="dspMdl" property="aptJkbn" value="0">
               <logic:equal name="dspMdl" property="mukoCheck" value="true">
                 <input type="checkbox" class="js_token_check" name="api030delMulti" value="<bean:write name="dspMdl" property="aptToken" />" checked="checked" />
               </logic:equal>
               <logic:notEqual name="dspMdl" property="mukoCheck" value="true">
                  <input type="checkbox" class="js_token_check" name="api030delMulti" value="<bean:write name="dspMdl" property="aptToken" />" />
               </logic:notEqual>
             </logic:equal>
          </td>
          </logic:equal>
          <td class="txt_l">
            <logic:notEmpty name="dspMdl" property="user" >
              <logic:equal name="dspMdl" property="user.jkbn" value="0">
                  <span class = "<logic:equal name="dspMdl" property="user.usrUkoFlg" value="1">mukoUser</logic:equal>">
                  <bean:write name="dspMdl" property="user.label" />
                  </span>
              </logic:equal>
              <logic:notEqual name="dspMdl" property="user.jkbn" value="0">
                  <del>
                  <bean:write name="dspMdl" property="user.label" />
                  </del>
              </logic:notEqual>
            </logic:notEmpty>
          </td>
          <td class="txt_l ">
            <logic:equal name="dspMdl" property="aptClient" value="<%=String.valueOf(UserAgent.CLIENT_TYPE_CROSSRIDE) %>">
              <gsmsg:write key="cmn.crossride" />
            </logic:equal>
            <logic:equal name="dspMdl" property="aptClient" value="<%=String.valueOf(UserAgent.CLIENT_TYPE_GSMOBILE) %>">
              <gsmsg:write key="cmn.gsmobile" />
            </logic:equal>
            <logic:equal name="dspMdl" property="aptClient" value="<%=String.valueOf(UserAgent.CLIENT_TYPE_OTHER) %>">
              <gsmsg:write key="cmn.other" />
            </logic:equal>
          </td>
          <td class="txt_c ">
            <bean:write name="dspMdl" property="adateDsp" />
          </td>
          <td class="txt_c ">
            <logic:greaterEqual name="dspMdl" property="yukoYear" value="9000">
              <gsmsg:write key="main.man200.9" />
            </logic:greaterEqual>
            <logic:lessThan name="dspMdl" property="yukoYear" value="9000">
              <bean:write name="dspMdl" property="ldateDsp" />
            </logic:lessThan>
          </td>
          <td class="txt_l ">
            <bean:write name="dspMdl" property="aptIp" />
          </td>
          <td class="txt_l ">
            <bean:write name="dspMdl" property="aptClientId" />
          </td>
          <td class="txt_l word_b-all">
            <bean:write name="dspMdl" property="aptToken" />
          </td>
          <td class="txt_c no_w plr0">
            <logic:equal name="dspMdl" property="aptJkbn" value="0">
              <logic:equal name="api030Form" property="api030cmnCallFlg" value="0">
                <button type="button" name="api030delete" class="baseBtn " onClick="buttonMuko('<bean:write name="dspMdl" property="aptToken" />');" value="<gsmsg:write key="api.api030.3" />">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete2" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete2" />">
                  <gsmsg:write key="cmn.invalid" />
                </button>
              </logic:equal>
              <logic:notEqual name="api030Form" property="api030cmnCallFlg" value="0">
                <button type="button" class="baseBtn " onClick="tokenUseList('<bean:write name="dspMdl" property="aptToken" />');" value="<gsmsg:write key="project.66" />">
                  <gsmsg:write key="api.api030.13" />
                </button>
                <div class="m5">
                  <common:toast toastId="tokenListParam">
                    <gsmsg:write key="api.api030.11" />
                  </common:toast>
                </div>
              </logic:notEqual>
            </logic:equal>
            <logic:notEqual name="dspMdl" property="aptJkbn" value="0">
              <gsmsg:write key="api.api030.9" />
            </logic:notEqual>
          </td>
        </tr>
      </logic:iterate>
    </table>
    <logic:greaterThan name="count1" value="1">
      <div class="paging w100">
        <button type="button" class="ml_auto webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="api030pageSel" onchange="changePage(this);" styleClass="paging_combo">
          <html:optionsCollection name="api030Form" property="api030PageLabel" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
</div>
</table>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>