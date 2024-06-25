<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../circular/js/cir140.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="cir.5" /> <gsmsg:write key="cmn.default.setting" /></title>
</head>

<body onload="lmtEnableDisable();">
<html:form action="/circular/cir140">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />

<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('init_change_kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td class="w75">
      <!-- 編集権限 -->
        <div class="verAlignMid">
          <html:radio name="cir140Form" styleId="cir140EditType_01" property="cir140KenKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIEDIT_STYPE_ADM) %>" onclick="lmtEnableDisable();" /><label for="cir140EditType_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="cir140Form" styleClass="ml10" styleId="cir140EditType_02" property="cir140KenKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIEDIT_STYPE_USER) %>" onclick="lmtEnableDisable();" /><label for="cir140EditType_02"><gsmsg:write key="cmn.set.eachaccount" /></label>
        </div>
        <span id="lmtinput"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cir.cir040.2" />
      </th>
      <td>

      <logic:equal name="cir140Form" property="cir140memoKbn" value="0">
      <div class="verAlignMid">
        <html:hidden property="cir140memoPeriod" />
        <!-- メモ欄修正区分 -->
        <gsmsg:write key="cir.cir040.3" />
        <html:radio property="cir140memoKbn" styleId="memoNg" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_NO) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoNg"><gsmsg:write key="cmn.not" /></label>
        <html:radio property="cir140memoKbn" styleId="memoOk" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_YES) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoOk"><gsmsg:write key="cmn.accepted" /></label>
      </div>
      </logic:equal>

      <logic:equal name="cir140Form" property="cir140memoKbn" value="1">
        <div class="verAlignMid">
          <!-- メモ欄修正区分 -->
          <gsmsg:write key="cir.cir040.3" />
          <html:radio property="cir140memoKbn" styleId="memoNg" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_NO) %>" onclick="buttonPush('memoKbnChange');" styleClass="ml10" /><label for="memoNg" class="mr5"><gsmsg:write key="cmn.not" /></label>
          <html:radio styleClass="ml10" property="cir140memoKbn" styleId="memoOk" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_YES) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoOk"><gsmsg:write key="cmn.accepted" /></label>
       </div>
       <div>
         <!-- メモ欄修正期限設定 -->
         <div class="verAlignMid">
           <gsmsg:write key="cir.54" />
           <html:radio property="cir140memoPeriod" styleId="today" value="1" styleClass="ml10" /><label for="today" class="mr5"><gsmsg:write key="cmn.today" /></label>
           <html:radio styleClass="ml10" property="cir140memoPeriod" styleId="1weeks" value="0" /><label for="1weeks" class="mr5">1<gsmsg:write key="cmn.weeks" /></label>
           <html:radio styleClass="ml10" property="cir140memoPeriod" styleId="2weeks" value="2" /><label for="2weeks" class="mr5">2<gsmsg:write key="cmn.weeks" /></label>
           <html:radio styleClass="ml10" property="cir140memoPeriod" styleId="months" value="3" /><label for="months" ><gsmsg:write key="cmn.months" arg0="1" /></label>
         </div>
       </div>
      </logic:equal>


       </td>
    </tr>
    <!-- 回覧先確認状況区分 -->
    <tr>
      <th>
        <gsmsg:write key="cir.cir030.3" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir140Form" property="cir140show" styleId="showPub" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC) %>" /><label for="showPub"><gsmsg:write key="cmn.public" /></label>
          <html:radio name="cir140Form" styleClass="ml10" property="cir140show" styleId="showPri" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PRIVATE) %>" /><label for="showPri"><gsmsg:write key="cmn.private" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('init_change_kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>