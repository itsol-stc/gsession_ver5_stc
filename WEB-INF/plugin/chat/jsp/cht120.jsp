<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.01" /> <gsmsg:write key="cht.cht030.01"/></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../chat/js/cht120.js?<%=GSConst.VERSION_PARAM%>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>

<body>
  <html:form action="/chat/cht120">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="cht010SelectPartner" />
    <html:hidden property="cht010SelectKbn" />
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cmn.preferences"/>
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backConfMenu');">
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
    <!-- 受信時プッシュ通知 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cht.cht120.02" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio name="cht120Form" property="cht120Push" styleId="cht120Push_01" value="<%=String.valueOf(GSConstChat.CHAT_PUSH_FLG_YES)%>" onclick="dspChangeSelect()" /><label for="cht120Push_01"><gsmsg:write key="cmn.notify" /></label>
          <html:radio name="cht120Form" styleClass="ml10" property="cht120Push" styleId="cht120Push_02" value="<%=String.valueOf(GSConstChat.CHAT_PUSH_FLG_NO)%>" onclick="dspChangeSelect()" /><label for="cht120Push_02"><gsmsg:write key="cmn.dont.notify" /></label>
        </span>
      </td>
    </tr>
    <!-- 通知表示時間 -->
    <tr id="js_dspTime">
      <th>
        <gsmsg:write key="cht.cht120.03" />
      </th>
      <td>
        <html:select property="cht120DspSecond">
        <html:optionsCollection name="cht120Form" property="cht120TimeList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backConfMenu');">
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