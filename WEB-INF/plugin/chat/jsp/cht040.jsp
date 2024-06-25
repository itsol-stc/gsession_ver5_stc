<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.01" /> <gsmsg:write key="cht.cht020.01" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../chat/js/cht040.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../chat/js/chtmain.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/usrgrpselect.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>

<script type="text/javascript">
<!--
	var msglist_cht040 = (function() {
		//使用するメッセージキーの配列を作成
		var ret = new Array();
		ret['cht.cht040.useLimit'] = '<gsmsg:write key="main.can.use.user"/>';
		ret['cht.cht040.useAccept'] = '<gsmsg:write key="man.restricted.use.user"/>';
		return ret;
	})();
-->
</script>
</head>
<body onload="initLoad();">
  <html:form action="/chat/cht040">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="cht040InitFlg" />
    <html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht020.01" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
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
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
    <%-- ユーザ間チャットの使用 --%>
    <tr>
      <th class="w25 no_w">
      <gsmsg:write key="cht.cht040.01" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio name="cht040Form" property="cht040BetweenUsers" styleId="cht040BetweenUsers_01" styleClass="" value="<%=String.valueOf(GSConstChat.LIMIT_BETWEEN_USERS)%>" />
        <label for="cht040BetweenUsers_01" class="mr10"><gsmsg:write key="cmn.do.limit" /></label>
        <html:radio name="cht040Form" property="cht040BetweenUsers" styleId="cht040BetweenUsers_02" styleClass="" value="<%=String.valueOf(GSConstChat.PERMIT_BETWEEN_USERS)%>" />
        <label for="cht040BetweenUsers_02"><gsmsg:write key="cmn.not.limit" /></label>
      </div>
      </td>
    </tr>
    <%-- グループの作成 --%>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cht.cht040.02" />
      </th>
      <td>
      <div class="verAlignMid">
        <html:radio name="cht040Form" property="cht040CreateGroup" styleId="cht040CreateGroup_01" value="<%=String.valueOf(GSConstChat.LIMIT_CREATE_GROUP)%>"
                              onclick="dspChangeSelect();" />
        <label for="cht040CreateGroup_01" class="mr10"><gsmsg:write key="cmn.do.limit" /></label>
        <html:radio name="cht040Form" property="cht040CreateGroup" styleId="cht040CreateGroup_02" value="<%=String.valueOf(GSConstChat.PERMIT_CREATE_GROUP)%>"
                              onclick="dspChangeSelect();" />
        <label for="cht040CreateGroup_02"><gsmsg:write key="cmn.not.limit" /></label>
      </div>

 <%-- グループ作成制限対象 --%>
      <div id="js_selectUser" class="mt10">
        <gsmsg:write key="cmn.limit.target" /><br>
        <div class="verAlignMid">
          <html:radio name="cht040Form" property="cht040HowToLimit" styleId="cht040HowToLimit_01" value="<%=String.valueOf(GSConstChat.TARGET_LIMIT)%>" />
          <label for="cht040HowToLimit_01" class="mr10"><gsmsg:write key="cmn.access.permit" /></label>
          <html:radio name="cht040Form" property="cht040HowToLimit" styleId="cht040HowToLimit_02" value="<%=String.valueOf(GSConstChat.TARGET_PERMIT)%>" />
         <label for="cht040HowToLimit_02" class="mr10"><gsmsg:write key="cmn.access.limit" /></label>
        </div>

        <ui:multiselector name="cht040Form" property="cht040TargetUserUI" styleClass="hp215" />
      </div>
      </td>
    </tr>
    <%-- 既読の表示 --%>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cht.cht040.03" />
      </th>
      <td>
        <div class="verAlignMid">
        <html:radio name="cht040Form" property="cht040AlreadyRead" styleId="cht040AlreadyRead_01"  value="<%=String.valueOf(GSConstChat.KIDOKU_DISP) %>" />
        <label for="cht040AlreadyRead_01" class="mr10"><gsmsg:write key="cmn.display.ok" /></label>
        <html:radio name="cht040Form" property="cht040AlreadyRead" styleId="cht040AlreadyRead_02"  value="<%=String.valueOf(GSConstChat.KIDOKU_NOT_DISP) %>" />
        <label for="cht040AlreadyRead_02"><gsmsg:write key="cmn.dont.show" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToAdmin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>
</html:html>