<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cir.5" /> <gsmsg:write key="cmn.default.setting" /><gsmsg:write key="cmn.check" /></title>
</head>

<body>
<html:form action="/circular/cir130kn">
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

<html:hidden property="cir130memoKbn" />
<html:hidden property="cir130memoPeriod" />
<html:hidden property="cir130show" />
<html:hidden property="cir130SelKbn" />
<html:hidden property="cir130AccountName" />
<html:hidden property="cir130AccountSid" />



<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cmn.default.setting" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('backToKtool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_init_change');">
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
        <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
      <logic:iterate id="accountMdl" name="cir130knForm" property="cir130knAccountList">
        <bean:write name="accountMdl" property="accountName"/>
      </logic:iterate>
      </td>
    </tr>
    <tr>
      <th rowspan="2" id="cirEditArea1">
        <gsmsg:write key="cir.cir040.2" />
      </th>
    </tr>
    <logic:equal name="cir130knForm" property="cir130memoKbn" value="0">
    <!-- メモ欄修正区分 -->
    <tr>
      <td>
      <gsmsg:write key="cir.cir040.3" />&nbsp;&nbsp;
      <gsmsg:write key="cmn.not" />
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="cir130knForm" property="cir130memoKbn" value="1">
    <!-- メモ欄修正区分 -->
    <tr>
    <td>
    <gsmsg:write key="cir.cir040.3" />&nbsp;&nbsp;
      <gsmsg:write key="cmn.accepted" />
      <!-- メモ欄修正期限設定 -->
      <br>
      <gsmsg:write key="cir.54" />&nbsp;&nbsp;

      <logic:equal name="cir130knForm" property="cir130memoPeriod" value="1">
          <span class="text_base"><gsmsg:write key="cmn.today" /></span>
      </logic:equal>
      <logic:equal name="cir130knForm" property="cir130memoPeriod" value="0">
          <span class="text_base">1<gsmsg:write key="cmn.weeks" /></span>
      </logic:equal>
      <logic:equal name="cir130knForm" property="cir130memoPeriod" value="2">
          <span class="text_base">2<gsmsg:write key="cmn.weeks" /></span>
      </logic:equal>
      <logic:equal name="cir130knForm" property="cir130memoPeriod" value="3">
          <span class="text_base"><gsmsg:write key="cmn.months" arg0="1" /></span>
      </logic:equal>
    </td>
    </tr>
    </logic:equal>
    <!-- 回覧先確認編集権限区分 -->
    <tr>
      <th id="cirEditArea1">
        <gsmsg:write key="cir.cir030.3" />
      </th>
      <!-- 回覧先確認状況区分 -->
      <td>
        <logic:equal name="cir130knForm" property="cir130show" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC) %>">
          <gsmsg:write key="cmn.public" />
        </logic:equal>
        <logic:equal name="cir130knForm" property="cir130show" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PRIVATE) %>">
          <gsmsg:write key="cmn.private" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('backToKtool');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
     <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_init_change');">
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