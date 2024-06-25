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
<title>GROUPSESSION <gsmsg:write key="wml.wml110.01" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../circular/js/cir230.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/circular/cir230">

    <logic:notEqual name="cir230Form" property="cirAccountMode" value="2">
      <input type="hidden" name="helpPrm" value="0" />
    </logic:notEqual>

    <logic:equal name="cir230Form" property="cirAccountMode" value="2">
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

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <logic:equal name="cir230Form" property="cir230DspKbn" value="<%=String.valueOf(GSConstCircular.DSPKBN_ADMIN)%>">
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
            <gsmsg:write key="cmn.admin.setting" />
          </logic:equal>
          <logic:equal name="cir230Form" property="cir230DspKbn" value="<%=String.valueOf(GSConstCircular.DSPKBN_PREF)%>">
            <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
            <gsmsg:write key="cmn.preferences2" />
          </logic:equal>
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.wml110.01" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addLabel();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAccount');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
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
        <tr>
          <th class="w25">
            <gsmsg:write key="wml.28" />
          </th>
          <td class="w75">
            <bean:write name="cir230Form" property="cir230accountName" />
          </td>
        </tr>
      </table>

      <!-- 上・下ボタン -->
      <div class="txt_l">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upLabelData');">
          <gsmsg:write key="cmn.up" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downLabelData');">
          <gsmsg:write key="cmn.down" />
        </button>
      </div>
      <table class="table-top">
        <tr>
          <th class="txt_c">
          </th>
          <th class="w90">
            <gsmsg:write key="wml.74" />
          </th>
          <th class="w5">
            <gsmsg:write key="cmn.fixed" />
          </th>
          <th class="w5">
            <gsmsg:write key="cmn.delete" />
          </th>
        </tr>
        <logic:notEqual name="cir230Form" property="cirAccountSid" value="-1">
          <logic:iterate id="labelData" name="cir230Form" property="cir230LabelList" indexId="idx">
            <%
              String labelCheckId = "chkLabel" + String.valueOf(idx.intValue());
            %>
            <bean:define id="labelValue" name="labelData" property="labelSid" />
            <tr>
              <!-- ラジオボタン -->
              <td class="txt_c js_tableTopCheck cursor_p">
                <html:radio property="cir230SortRadio" value="<%=String.valueOf(labelValue)%>" styleId="<%=labelCheckId%>" />
              </td>
              <!-- ラベル -->
              <td>
                <bean:write name="labelData" property="labelName" />
              </td>
              <!-- 修正ボタン -->
              <td class="txt_c">
                <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.fixed" />" onClick="editLabel('<bean:write name="labelData" property="labelSid" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
                  <gsmsg:write key="cmn.fixed" />
                </button>
              </td>
              <!-- 削除ボタン -->
              <td class="txt_c">
                <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.delete" />" onClick="deleteLabel('<bean:write name="labelData" property="labelSid" />');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete"/>">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete"/>">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEqual>
      </table>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>