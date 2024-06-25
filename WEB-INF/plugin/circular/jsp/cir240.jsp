<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cir.GSConstCircular"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.entry.label" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../circular/css/circular.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/circular/cir240">

    <logic:notEqual name="cir240Form" property="cirAccountMode" value="2">
      <input type="hidden" name="helpPrm" value="0" />
    </logic:notEqual>

    <logic:equal name="cir240Form" property="cirAccountMode" value="2">
      <input type="hidden" name="helpPrm" value="1" />
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
    <html:hidden property="cir010SelectLabelSid" />

    <html:hidden property="cir010adminUser" />
    <html:hidden property="cir150keyword" />
    <html:hidden property="cir150group" />
    <html:hidden property="cir150user" />
    <html:hidden property="cir150svKeyword" />
    <html:hidden property="cir150svGroup" />
    <html:hidden property="cir150svUser" />
    <html:hidden property="cir150sortKey" />
    <html:hidden property="cir150order" />
    <html:hidden property="cir150searchFlg" />
    <html:hidden property="cir230DspKbn" />
    <html:hidden property="cir230LabelCmdMode" />
    <html:hidden property="cir230EditLabelId" />
    <html:hidden property="cir240initKbn" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <logic:equal name="cir240Form" property="cir230DspKbn" value="<%=String.valueOf(GSConstCircular.DSPKBN_ADMIN)%>">
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
            <gsmsg:write key="cmn.admin.setting" />
          </logic:equal>
          <logic:equal name="cir240Form" property="cir230DspKbn" value="<%=String.valueOf(GSConstCircular.DSPKBN_PREF)%>">
            <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
            <gsmsg:write key="cmn.preferences2" />
          </logic:equal>
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="cir240Form" property="cir230LabelCmdMode" value="<%=String.valueOf(GSConstCircular.CMDMODE_ADD)%>">
            <span class="pageTitle_subFont-plugin">
              <gsmsg:write key="cir.5" />
            </span>
            <gsmsg:write key="cmn.entry.label" />
          </logic:equal>
          <logic:equal name="cir240Form" property="cir230LabelCmdMode" value="<%=String.valueOf(GSConstCircular.CMDMODE_EDIT)%>">
            <span class="pageTitle_subFont-plugin">
              <gsmsg:write key="cir.5" />
            </span>
            <gsmsg:write key="cmn.edit.label" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backConfLabel');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <!-- コンテンツ部分 -->
    <div class="wrapper w80 mrl_auto">

      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <table class="table-left">
        <!-- アカウント -->
        <tr>
          <th class="w25">
            <gsmsg:write key="wml.102" />
          </th>
          <td class="w75">
            <bean:write name="cir240Form" property="cir230accountName" />
          </td>
        </tr>
        <!-- ラベル -->
        <tr>
          <th class="w25">
            <gsmsg:write key="wml.74" />
          </th>
          <td class="w75">
            <html:text name="cir240Form" property="cir240LabelName" maxlength="100"/>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backConfLabel');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>