<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp090.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp090">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp090SelectSid" />
    <logic:notEmpty name="anp090Form" property="anp090TempList" scope="request">
      <logic:iterate id="sort" name="anp090Form" property="anp090TempList" scope="request">
        <input type="hidden" name="anp090SortLabel" value="<bean:write name="sort" property="templateValue" />">
      </logic:iterate>
    </logic:notEmpty>
    <!-- ヘッダー -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp090.01" />
        </li>
        <!-- ボタン -->
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="editDetail('');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp090back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <!-- 上・下ボタン -->
      <div class="txt_l">
        <div class="cl_fontWarn">
          <gsmsg:write key="anp.anp090.02" />
        </div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upTemplateData');">
          <gsmsg:write key="cmn.up" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downTemplateData');">
          <gsmsg:write key="cmn.down" />
        </button>
      </div>
      <!-- コンテンツ -->
      <table class="table-top w100">
        <tr>
          <th class="w5 table_title-color txt_c"></th>
          <th class="w95 table_title-color txt_c">
            <gsmsg:write key="anp.anp090.03" />
          </th>
        </tr>
        <logic:notEmpty name="anp090Form" property="anp090TempList">
          <logic:iterate id="templateMdl" name="anp090Form" property="anp090TempList" indexId="idx">
            <bean:define id="radiovalue" name="templateMdl" property="templateValue" />
            <bean:define id="backclass" value="td_line_color" />
            <bean:define id="backclass_no_edit" value="td_line_no_edit_color" />
            <bean:define id="backpat" value="<%= String.valueOf((idx.intValue() % 2) + 1) %>" />
            <bean:define id="back" value="<%= String.valueOf(backclass) + String.valueOf(backpat) %>" />
            <bean:define id="back_no_edit" value="<%= String.valueOf(backclass_no_edit) + String.valueOf(backpat) %>" />
            <tr class="js_listHover cursor_p" id="<bean:write name="templateMdl" property="templateSid" />">
              <!-- ラジオボタン -->
              <td class="w5 txt_c js_tableTopCheck">
                <html:radio property="anp090SortTemplate" value="<%= String.valueOf(radiovalue) %>" />
              </td>
              <!-- テンプレート名 -->
              <td class="txt_l js_listClick cl_linkDef">
                <bean:write name="templateMdl" property="templateName" />
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="editDetail('');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp090back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>