<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
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
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../anpi/js/anp130.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp130">
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp130SelectAphSid" />
    <html:hidden property="anp130NowPage" />
    <html:hidden property="anp130DspPage1" />
    <html:hidden property="anp130DspPage2" />
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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp070.08" />
        </li>
        <li>
          <!-- ボタン -->
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('anp130delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp130back');">
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

      <!-- ページング -->
      <bean:size id="pageCount" name="anp130Form" property="anp130PageLabel" scope="request" />
      <logic:greaterThan name="pageCount" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('anp130pageLast');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="anp130DspPage1" onchange="changePage(this);">
            <html:optionsCollection name="anp130Form" property="anp130PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('anp130pageNext');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <!-- コンテンツ -->
      <table class="table-top w100">
        <tr>
          <th class="txt_c js_tableTopCheck js_tableTopCheck-header no_w cursor_p">
            <html:checkbox name="anp130Form" property="anp130allCheck" value="1" onclick="changeCheckList();" />
          </th>
          <th class="txt_c w35 no_w">
            <gsmsg:write key="cmn.subject" />
          </th>
          <th class="txt_c w10 no_w">
            <gsmsg:write key="anp.knmode" />
          </th>
          <th class="txt_c w15 no_w">
            <gsmsg:write key="anp.sender" />
          </th>
          <th class="txt_c no_w">
            <gsmsg:write key="anp.date.send" />
          </th>
          <th class="txt_c no_w">
            <gsmsg:write key="anp.date.finish" />
          </th>
        </tr>
        <logic:notEmpty name="anp130Form" property="anp130HaisinList">
          <logic:iterate id="haisin" name="anp130Form" property="anp130HaisinList" indexId="idx">
            <bean:define id="backclass" value="td_line_color" />
            <bean:define id="backclass_no_edit" value="td_line_no_edit_color" />
            <bean:define id="backpat" value="<%=String.valueOf((idx.intValue() % 2) + 1)%>" />
            <bean:define id="back" value="<%=String.valueOf(backclass) + String.valueOf(backpat)%>" />
            <bean:define id="back_no_edit" value="<%=String.valueOf(backclass_no_edit) + String.valueOf(backpat)%>" />
            <tr class="js_listHover cursor_p" id="<bean:write name="haisin" property="anpiSid" />">
              <!-- 削除チェック -->
              <td class="txt_c js_tableTopCheck">
                <html:multibox name="anp130Form" property="anp130DelSidList">
                  <bean:write name="haisin" property="anpiSid" />
                </html:multibox>
              </td>
              <!-- 件名 -->
              <td class="txt_l js_listClick cl_linkDef">
                <bean:write name="haisin" property="subject" />
              </td>
              <!-- 訓練モード -->
              <td class="txt_c js_listClick">
                <logic:equal name="haisin" property="knrenFlg" value="1">
                  <gsmsg:write key="cmn.circle" />
                </logic:equal>
              </td>
              <!-- 氏名 -->
              <td class="txt_l js_listClick">
                <logic:equal name="haisin" property="jyotaiKbn" value="<%=String.valueOf(GSConst.JTKBN_TOROKU)%>">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal value="1" name="haisin" property="usrUkoFlg">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>">
                    <bean:write name="haisin" property="name" />
                  </span>
                </logic:equal>
                <logic:notEqual name="haisin" property="jyotaiKbn" value="<%=String.valueOf(GSConst.JTKBN_TOROKU)%>">
                  <del>
                    <bean:write name="haisin" property="name" />
                  </del>
                </logic:notEqual>
              </td>
              <!-- 配信日時 -->
              <td class="no_w txt_c js_listClick">
                <bean:write name="haisin" property="haisinDate" />
              </td>
              <!-- 完了日時 -->
              <td class="no_w txt_c js_listClick">
                <bean:write name="haisin" property="kanryoDate" />
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>

      <!-- ページング -->
      <bean:size id="pageCount" name="anp130Form" property="anp130PageLabel" scope="request" />
      <logic:greaterThan name="pageCount" value="1">
        <div class="paging mb10">
          <button type="button" class="webIconBtn" onClick="buttonPush('anp130pageLast');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="anp130DspPage1" onchange="changePage(this);">
            <html:optionsCollection name="anp130Form" property="anp130PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('anp130pageNext');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('anp130delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp130back');">
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