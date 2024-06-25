<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cir.cir160kn.Cir160knForm" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CMDMODE_EDIT);
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="wml.wml040kn.05" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../circular/js/cir160.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form styleId="cir160kn" action="/circular/cir160kn">

<logic:notEqual name="cir160knForm" property="cir010adminUser" value="true">
  <input type="hidden" name="helpPrm" value="5">
</logic:notEqual>

<logic:equal name="cir160knForm" property="cir010adminUser" value="true">
  <input type="hidden" name="helpPrm" value="">
</logic:equal>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cirCmdMode" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />

<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="cir010adminUser" />
<html:hidden property="cir150keyword" />
<html:hidden property="cir150group" />
<html:hidden property="cir150user" />

<html:hidden property="cir160initFlg" />
<html:hidden property="cir160AccountKbn" />
<html:hidden property="cir160DefActUsrSid" />
<html:hidden property="cir160elementKbn" />
<html:hidden property="cir160name" />
<html:hidden property="cir160biko" />

<html:hidden property="cir160JdelKbn" />
<html:hidden property="cir160JYear" />
<html:hidden property="cir160JMonth" />
<html:hidden property="cir160SdelKbn" />
<html:hidden property="cir160SYear" />
<html:hidden property="cir160SMonth" />
<html:hidden property="cir160DdelKbn" />
<html:hidden property="cir160DYear" />
<html:hidden property="cir160DMonth" />
<html:hidden property="cir160autoDelKbn" />
<html:hidden property="cir160cirInitKbn" />
<html:hidden property="cir160SelTab" />
<html:hidden property="cir160theme" />
<html:hidden property="cir160smlNtf" />
<html:hidden property="cir160smlMemo" />
<html:hidden property="cir160smlEdt" />

<html:hidden property="cir160memoKbn" />
<html:hidden property="cir160memoPeriod" />
<html:hidden property="cir160show" />
<html:hidden property="cir160SmlNtfKbn" />



<logic:notEmpty name="cir160knForm" property="cir160userKbnUser">
<logic:iterate id="permitId" name="cir160knForm" property="cir160userKbnUser">
  <input type="hidden" name="cir160userKbnUser" value="<bean:write name="permitId" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="cir150keyword" />
<html:hidden property="cir150group" />
<html:hidden property="cir150user" />
<html:hidden property="cir150svKeyword" />
<html:hidden property="cir150svGroup" />
<html:hidden property="cir150svUser" />
<html:hidden property="cir150sortKey" />
<html:hidden property="cir150order" />
<html:hidden property="cir150searchFlg" />


<bean:define id="acctMode" name="cir160knForm" property="cirAccountMode" type="java.lang.Integer" />
<bean:define id="sCmdMode" name="cir160knForm" property="cirCmdMode" type="java.lang.Integer" />
<bean:define id="adminflg" name="cir160Form" property="cir010adminUser" type="java.lang.Boolean" />
<% int accountMode = acctMode.intValue(); %>
<% int cmdMode = sCmdMode.intValue(); %>
<% boolean adminFlg = adminflg; %>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>




<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <% if (adminflg) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.admin.setting" /></li>
    <% } else { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.preferences2" /></li>
    <% }%>
    <li class="pageTitle_subFont">
      <% if (cmdMode == 0) { %>
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.wml040kn.05" />
      <% } else { %>
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.97" />
      <% }%>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
<div class="wrapper w80 mrl_auto">
  <ul class="tabHeader w100">
    <li class="tabHeader_tab-on wp100 js_tab border_bottom_none bgI_none" id="tab1">
      <gsmsg:write key="cmn.preferences" />
    </li>
    <logic:notEqual name="cir160knForm" property="cir160autoDelKbn" value="0">
      <li class="tabHeader_tab-off wp100 js_tab border_bottom_none" id="tab2">
        <gsmsg:write key="cmn.autodelete" />
      </li>
    </logic:notEqual>
    <logic:notEqual name="cir160knForm" property="cir160cirInitKbn" value="0">
      <li class="tabHeader_tab-off js_tab border_bottom_none" id="tab3">
        <gsmsg:write key="cir.23" />
      </li>
    </logic:notEqual>
    <logic:equal name="cir160knForm" property="canSmlUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE) %>">
      <logic:equal name="cir160knForm" property="cir160SmlNtfKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_USER) %>">
        <li class="tabHeader_tab-off js_tab border_bottom_none" id="tab4">
          <gsmsg:write key="shortmail.notification" />
        </li>
      </logic:equal>
    </logic:equal>
    <li class="tabHeader_tab-off wp100 js_tab border_bottom_none" id="tab5">
      <gsmsg:write key="cmn.other" />
    </li>
    <li class="tabHeader_space border_bottom_none"></li>
  </ul>

  <table id="tab1_table" class="table-left w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.96" />
      </th>
      <td class="w75">
        <bean:write name="cir160knForm" property="cir160name" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <bean:write name="cir160knForm" property="cir160knBiko" filter="false" />
      </td>
    </tr>
    <logic:equal name="cir160knForm" property="cir160acntUserFlg" value="true">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.employer" />
        </th>
        <td class="w75">
          <logic:notEmpty name="cir160knForm" property="userKbnUserSelectCombo">
            <ul class="list_st mb0">
              <logic:iterate id="userKbnLabel" name="cir160knForm" property="userKbnUserSelectCombo">
                <bean:define id="mukoUserClass" value=""/>
                <logic:equal name="userKbnLabel" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" >mukoUser</bean:define></logic:equal>
                <li class="list_st"><span class="<%=mukoUserClass%>"><bean:write name="userKbnLabel" property="label" /></span></li>
              </logic:iterate>
            </ul>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:equal>
  </table>
  <table id="tab2_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.25" />
      </th>
      <td class="w75">
        <logic:equal name="cir160knForm" property="cir160JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="jyear" name="cir160knForm" property="cir160JYear" type="java.lang.String" />
          <bean:define id="jmonth" name="cir160knForm" property="cir160JMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= jyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= jmonth %>" /><gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.26" />
      </th>
      <td class="w75">
        <logic:equal name="cir160knForm" property="cir160SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="syear" name="cir160knForm" property="cir160SYear" type="java.lang.String" />
          <bean:define id="smonth" name="cir160knForm" property="cir160SMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= syear %>" /> <gsmsg:write key="cmn.months" arg0="<%= smonth %>" /><gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.autodelete" /> <gsmsg:write key="cir.27" />
      </th>
      <td class="w75">
        <logic:equal name="cir160knForm" property="cir160DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>">
          <bean:define id="dyear" name="cir160knForm" property="cir160DYear" type="java.lang.String" />
          <bean:define id="dmonth" name="cir160knForm" property="cir160DMonth" type="java.lang.String" />
          <gsmsg:write key="cmn.year" arg0="<%= dyear %>" /> <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" /><gsmsg:write key="cmn.auto.del.data.older.than" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <table id="tab3_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cir.cir040.2" />
      </th>
      <td class="w75">
        <!-- メモ欄修正区分 -->
        <logic:equal name="cir160knForm" property="cir160memoKbn" value="0">
          <gsmsg:write key="cir.cir040.3" /><span class="ml5"><gsmsg:write key="cmn.not" /></span>
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160memoKbn" value="1">
          <!-- メモ欄修正区分 -->
          <div><gsmsg:write key="cir.cir040.3" /><span class="ml5"><gsmsg:write key="cmn.accepted" /></span></div>
          <!-- メモ欄修正期限設定 -->
          <span class="mt10"><gsmsg:write key="cir.54" /></span>
          <span>
            <logic:equal name="cir160knForm" property="cir160memoPeriod" value="1">
              <gsmsg:write key="cmn.today" />
            </logic:equal>
            <logic:equal name="cir160knForm" property="cir160memoPeriod" value="0">
              1<gsmsg:write key="cmn.weeks" />
            </logic:equal>
            <logic:equal name="cir160knForm" property="cir160memoPeriod" value="2">
              2<gsmsg:write key="cmn.weeks" />
            </logic:equal>
            <logic:equal name="cir160knForm" property="cir160memoPeriod" value="3">
              <gsmsg:write key="cmn.months" arg0="1" />
            </logic:equal>
        </span>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <!-- 回覧先確認編集権限区分 -->
      <th class="w25">
        <gsmsg:write key="cir.cir030.3" />
      </th>
      <td class="w75">
        <logic:equal name="cir160knForm" property="cir160show" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC) %>">
          <gsmsg:write key="cmn.public" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160show" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PRIVATE) %>">
          <gsmsg:write key="cmn.private" />
        </logic:equal>
      </td>
    </tr>
  </table>

  <logic:equal name="cir160knForm" property="canSmlUse" value="<%=String.valueOf(GSConst.PLUGIN_USE) %>">
    <logic:equal name="cir160knForm" property="cir160SmlNtfKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_USER) %>">
      <table id="tab4_table" class="table-left display_none w100 mt0">
        <tr>
          <th class="w25">
            <gsmsg:write key="cir.cir160.2" />
          </th>
          <td class="w75">
            <logic:equal name="cir160knForm" property="cir160smlNtf" value="0">
              <gsmsg:write key="cmn.notify" />
            </logic:equal>
            <logic:equal name="cir160knForm" property="cir160smlNtf" value="1">
              <gsmsg:write key="cmn.dont.notify" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cir.cir160.4" />
          </th>
          <td class="w75">
            <logic:equal name="cir160knForm" property="cir160smlMemo" value="0">
              <gsmsg:write key="cmn.notify" />
            </logic:equal>
            <logic:equal name="cir160knForm" property="cir160smlMemo" value="1">
              <gsmsg:write key="cmn.dont.notify" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cir.cir160.6" />
          </th>
          <td class="w75">
            <logic:equal name="cir160knForm" property="cir160smlEdt" value="0">
              <gsmsg:write key="cmn.notify" />
            </logic:equal>
            <logic:equal name="cir160knForm" property="cir160smlEdt" value="1">
              <gsmsg:write key="cmn.dont.notify" />
            </logic:equal>
          </td>
        </tr>
      </table>
    </logic:equal>
  </logic:equal>

  <table id="tab5_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.theme" />
      </th>
      <td class="w75">
        <logic:equal name="cir160knForm" property="cir160theme" value="0">
          <bean:write name="cir160knForm" property="cir160knTheme" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="1">
          <gsmsg:write key="cmn.default" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="2">
          <gsmsg:write key="cmn.theme.gray" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="3">
          <gsmsg:write key="cmn.theme.green" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="4">
          <gsmsg:write key="cmn.theme.red" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="5">
          <gsmsg:write key="cmn.theme.pink" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="6">
          <gsmsg:write key="cmn.theme.yellow" /><gsmsg:write key="cmn.theme.classic" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="7">
          <gsmsg:write key="cmn.theme.original" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="8">
          <gsmsg:write key="cmn.theme.sougen" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="9">
          <gsmsg:write key="cmn.theme.sunset" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="10">
          <gsmsg:write key="cmn.theme.sakura" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="11">
          <gsmsg:write key="cmn.theme.sky" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="12">
          <gsmsg:write key="cmn.theme.city" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="13">
          <gsmsg:write key="cmn.theme.dark" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="14">
          <gsmsg:write key="cmn.theme.mokume" />
        </logic:equal>
        <logic:equal name="cir160knForm" property="cir160theme" value="15">
          <gsmsg:write key="cmn.theme.darkblue" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>