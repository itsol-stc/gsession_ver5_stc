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
<html:form action="/circular/cir140kn">
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

<html:hidden property="cir140KenKbn" />
<html:hidden property="cir140memoKbn" />
<html:hidden property="cir140memoPeriod" />
<html:hidden property="cir140show" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
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
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td class="w75">
        <!-- メモ欄の修正権限区分 -->
        <logic:equal name="cir140knForm" property="cir140KenKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIEDIT_STYPE_ADM) %>">
          <gsmsg:write key="cmn.set.the.admin" />
        </logic:equal>
        <logic:equal name="cir140knForm" property="cir140KenKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIEDIT_STYPE_USER) %>">
          <gsmsg:write key="cmn.set.eachaccount" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cir.cir040.2" />
      </th>
      <td>

      <logic:equal name="cir140knForm" property="cir140memoKbn" value="0">
        <!-- メモ欄修正区分 -->
        <gsmsg:write key="cir.cir040.3" /><span class="ml10"><gsmsg:write key="cmn.not" /></span>
      </logic:equal>

      <logic:equal name="cir140knForm" property="cir140memoKbn" value="1">
        <!-- メモ欄修正区分 -->
        <gsmsg:write key="cir.cir040.3" /><span class="ml10"><gsmsg:write key="cmn.accepted" /></span>

        <!-- メモ欄修正期限設定 -->
        <div>
          <gsmsg:write key="cir.54" /><span class="ml10">
          <logic:equal name="cir140knForm" property="cir140memoPeriod" value="1">
            <gsmsg:write key="cmn.today" />
          </logic:equal>
          <logic:equal name="cir140knForm" property="cir140memoPeriod" value="0">
            1<gsmsg:write key="cmn.weeks" />
          </logic:equal>
          <logic:equal name="cir140knForm" property="cir140memoPeriod" value="2">
            2<gsmsg:write key="cmn.weeks" />
          </logic:equal>
          <logic:equal name="cir140knForm" property="cir140memoPeriod" value="3">
            <gsmsg:write key="cmn.months" arg0="1" />
          </logic:equal>
        </span>
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
        <logic:equal name="cir140knForm" property="cir140show" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC) %>">
          <gsmsg:write key="cmn.public" />
        </logic:equal>
        <logic:equal name="cir140knForm" property="cir140show" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PRIVATE) %>">
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