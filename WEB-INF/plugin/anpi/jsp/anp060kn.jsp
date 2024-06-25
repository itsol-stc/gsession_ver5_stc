<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!-- haisinflg 0:配信 2:再配信 4:参照 -->
<% Integer haisinflg = 0; %>
<% Integer senderCnt = 0; %>
<logic:equal name="anp060knForm" property="anp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MSG_HAISIN_MODE_MISAISOU) %>">
  <% haisinflg = 2; %>
</logic:equal>
<logic:equal name="anp060knForm" property="anp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MSG_HAISIN_MODE_ZENSAISOU) %>">
  <% haisinflg = 2; %>
</logic:equal>
<logic:equal name="anp060knForm" property="anp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MSG_HAISIN_MODE_INFOCONF) %>">
  <% haisinflg = 4; %>
</logic:equal>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.anp060.01" /></title>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp060kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../anpi/css/anpi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body onload="setfocus();" onunload="windowClose();">
  <html:form action="/anpi/anp060kn">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anpiSid" />
    <html:hidden property="anp010SelectGroupSid" />
    <html:hidden property="anp010NowPage" />
    <html:hidden property="anp010SortKeyIndex" />
    <html:hidden property="anp010OrderKey" />
    <html:hidden property="anp010KnrenFlg" />
    <html:hidden property="anp060ProcMode" />
    <html:hidden property="anp060main" />
    <html:hidden property="anp060Subject" />
    <html:hidden property="anp060Text1" />
    <html:hidden property="anp060Text2" />
    <html:hidden property="anp130SelectAphSid" />
    <html:hidden property="anp010SearchKbn" />
    <html:hidden property="anp010SearchSndKbn" />
    <html:hidden property="anp010SearchAnsKbn" />
    <html:hidden property="anp010SearchAnpKbn" />
    <html:hidden property="anp010SearchPlcKbn" />
    <html:hidden property="anp010SearchSyuKbn" />
    <html:hidden property="anp010SvSearchSndKbn" />
    <html:hidden property="anp010SvSearchAnsKbn" />
    <html:hidden property="anp010SvSearchAnpKbn" />
    <html:hidden property="anp010SvSearchPlcKbn" />
    <html:hidden property="anp010SvSearchSyuKbn" />
    <html:hidden property="anp060knNowPage" />
    <html:hidden property="anp060knDspPage1" />
    <html:hidden property="anp060knDspPage2" />
    <html:hidden property="anp060knScrollFlg" />
    <logic:notEmpty name="anp060knForm" property="anp060SenderList" scope="request">
      <logic:iterate id="ulBean" name="anp060knForm" property="anp060SenderList" scope="request">
        <input type="hidden" name="anp060SenderList" value="<bean:write name="ulBean" />">
        <% senderCnt++; %>
      </logic:iterate>
    </logic:notEmpty>
    <input type="hidden" name="helpPrm" value="<%= haisinflg %>" />
    <!-- ヘッダー -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../anpi/images/classic/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
          <img class="header_pluginImg" src="../anpi/images/original/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
        </li>
        <li>
          <gsmsg:write key="anp.plugin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="anp.anp060kn.01" />
        </li>
        <li>
          <div>
            <% if ((haisinflg == 0) || (haisinflg == 2 && senderCnt > 0)) { %>
            <button type="button" value="<gsmsg:write key="anp.send2"/>" class="baseBtn" onClick="buttonPush('anp060knhaisin');">
              <img class="btn_classicImg-display" src="../anpi/images/classic/icon_okmail.gif" alt="<gsmsg:write key="anp.send2"/>">
              <img class="btn_originalImg-display" src="../common/images/original/icon_mail2.png" alt="<gsmsg:write key="anp.send2"/>">
              <gsmsg:write key="anp.send2" />
            </button>
            <%
              }
            %>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp060knback');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <!-- メッセージ -->
      <div class="txt_l">
        <% if (haisinflg == 2 && senderCnt == 0) { %>
        <span class="cl_fontWarn">
          <gsmsg:write key="anp.anp060kn.02" />
        </span>
        <% } %>
        <bean:define id="settingCnt" name="anp060knForm" property="anp060knSetConCount" />
        <% if (((Integer) settingCnt).intValue() < senderCnt) { %>
        <span class="cl_fontWarn">
          <gsmsg:write key="anp.anp060kn.03" />
        </span>
        <% } %>
      </div>
      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <!-- 訓練モード バー -->
      <logic:equal name="anp060knForm" property="anp010KnrenFlg" value="1">
        <div class="anp_kunren">
          <gsmsg:write key="anp.knmode" />
        </div>
      </logic:equal>
      <table class="table-left w100">
        <% if (haisinflg == 2) { %>
        <tr>
          <!-- オプション -->
          <th class="w25">
            <gsmsg:write key="anp.anp060kn.04" />
          </th>
          <td class="w75">
            <div>
              <span class="verAlignMid">
                <html:radio styleId="reflg1" name="anp060knForm" property="anp060knProcMode" onclick="buttonPush('anp060knreload');" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MSG_HAISIN_MODE_MISAISOU) %>" />
                <label for="reflg1">
                  <gsmsg:write key="anp.anp060kn.05" />
                </label>
              </span>
              <span class="ml10 verAlignMid">
                <html:radio styleId="reflg2" name="anp060knForm" property="anp060knProcMode" onclick="buttonPush('anp060knreload');" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MSG_HAISIN_MODE_ZENSAISOU) %>" />
                <label for="reflg2">
                  <gsmsg:write key="anp.anp060kn.06" />
                </label>
              </span>
            </div>
            <gsmsg:write key="anp.anp060kn.07" />
          </td>
        </tr>
        <%} %>
        <!-- 訓練フラグ -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.knmode" />
          </th>
          <td class="w75">
            <logic:equal name="anp060knForm" property="anp010KnrenFlg" value="1">
              <gsmsg:write key="anp.knmode" />
            </logic:equal>
            <logic:notEqual name="anp060knForm" property="anp010KnrenFlg" value="1">
              <gsmsg:write key="anp.hmode" />
            </logic:notEqual>
          </td>
        </tr>
        <!-- メイン表示 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.main.view" />
          </th>
          <td class="w75">
            <logic:equal name="anp060knForm" property="anp060main" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.APH_VIEW_MAIN_SENDTO) %>">
              <gsmsg:write key="anp.anp060.11" />
            </logic:equal>
            <logic:notEqual name="anp060knForm" property="anp060main" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.APH_VIEW_MAIN_SENDTO) %>">
              <gsmsg:write key="cmn.alluser" />
            </logic:notEqual>
          </td>
        </tr>
        <!-- 件名 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.subject" />
          </th>
          <td class="w75">
            <bean:write name="anp060knForm" property="anp060knDispSubject" />
          </td>
        </tr>
        <!-- 本文 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.body" />
          </th>
          <td class="w75">
            <bean:write name="anp060knForm" property="anp060knDispMessageBody" filter="false" />
          </td>
        </tr>
        <logic:notEqual name="anp060knForm" property="anp060ProcMode" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MSG_HAISIN_MODE_INFOCONF) %>">
          <tr>
            <!-- 送信先 -->
            <th class="w25">
              <gsmsg:write key="anp.send.dest" />
            </th>
            <td class="w75">
              <logic:notEmpty name="anp060knForm" property="anp060knSenderList">
                <a id="js_label_sender" name="label_sender"></a>
                <!-- ページコンボ -->
                <bean:size id="pageCount" name="anp060knForm" property="anp060knPageLabel" scope="request" />
                <logic:greaterThan name="pageCount" value="1">
                  <div class="paging display_inline-block">
                    <button type="button" class="webIconBtn btn_originalImg-display p0" onClick="buttonPush('anp060knpageLast');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <button type="button" class="webIconBtn btn_classicImg-display ml0" onClick="buttonPush('anp060knpageLast');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select styleClass="paging_combo" property="anp060knDspPage2" onchange="changePage(this);">
                      <html:optionsCollection name="anp060knForm" property="anp060knPageLabel" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('anp060knpageNext');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </div>
                </logic:greaterThan>
              </logic:notEmpty>
              <!-- 送信者表示 -->
              <bean:define id="dispMessage" value="0" />
              <table>
                <logic:iterate id="urBean" name="anp060knForm" property="anp060knSenderList" indexId="idx">
                  <tr class="border_none">
                    <td class="border_none p0 no_w">
                      <bean:define id="mukoUserClass" value="" />
                      <logic:equal name="urBean" property="grpFlg" value="<%= String.valueOf(jp.groupsession.v2.anp.anp060kn.Anp060knForm.GROUP_SELECT_YES) %>">
                        <a href="#!" onClick="openGrpUsrMailWindow(<bean:write name="urBean" property="sid" />);">
                          <bean:write name="urBean" property="name" />
                        </a>
                      </logic:equal>
                      <logic:equal value="1" name="urBean" property="usrUkoFlg">
                        <bean:define id="mukoUserClass" value="mukoUser" />
                      </logic:equal>
                      <span class="<%= mukoUserClass %>">
                        <logic:notEqual name="urBean" property="grpFlg" value="<%= String.valueOf(jp.groupsession.v2.anp.anp060kn.Anp060knForm.GROUP_SELECT_YES) %>">
                          <bean:write name="urBean" property="name" />
                        </logic:notEqual>
                      </span>
                    </td>
                    <td class="border_none p0 pl5">
                      <logic:notEqual name="urBean" property="mailadr" value="<%= String.valueOf(jp.groupsession.v2.anp.anp060kn.Anp060knForm.MAIL_SET_YES) %>">
                        <img class="btn_classicImg-display" src="../anpi/images/classic/icon_nomail.gif" border="0" alt="!">
                        <img class="btn_originalImg-display" src="../anpi/images/original/icon_senderr.png" border="0" alt="!">
                      </logic:notEqual>
                    </td>
                    <% if (idx == 0 && ((Integer) settingCnt).intValue() < senderCnt) { %>
                    <bean:define id="dispMessage" value="1" />
                    <% } %>
                  </tr>
                </logic:iterate>
              </table>
              <logic:equal name="dispMessage" value="1">
                <div class="txt_r">
                  <span class="fs_13 btn_classicImg-display">
                    <img src="../anpi/images/classic/icon_nomail.gif" border="0" alt="!" class="txt_m">：
                    <gsmsg:write key="anp.anp060kn.08" />
                  </span>
                  <span class="fs_13 btn_originalImg-display">
                    <img src="../anpi/images/original/icon_senderr.png" border="0" alt="!" class="txt_m">：
                    <gsmsg:write key="anp.anp060kn.08" />
                  </span>
                </div>
              </logic:equal>
              <!-- ページコンボ -->
              <bean:size id="pageCount" name="anp060knForm" property="anp060knPageLabel" scope="request" />
              <logic:greaterThan name="pageCount" value="1">
                <div class="paging display_inline-block">
                  <button type="button" class="webIconBtn btn_originalImg-display p0" onClick="buttonPush('anp060knpageLast');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                    <i class="icon-paging_left"></i>
                  </button>
                  <button type="button" class="webIconBtn btn_classicImg-display ml0" onClick="buttonPush('anp060knpageLast');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                    <i class="icon-paging_left"></i>
                  </button>
                  <html:select styleClass="paging_combo" property="anp060knDspPage2" onchange="changePage(this);">
                    <html:optionsCollection name="anp060knForm" property="anp060knPageLabel" value="value" label="label" />
                  </html:select>
                  <button type="button" class="webIconBtn" onClick="buttonPush('anp060knpageNext');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                    <i class="icon-paging_right"></i>
                  </button>
                </div>
              </logic:greaterThan>
            </td>
          </tr>
          <tr>
            <!-- 配信者 -->
            <th class="w25">
              <gsmsg:write key="anp.sender" />
            </th>
            <td class="w75">
              <bean:write name="anp060knForm" property="anp060RegistName" />
            </td>
          </tr>
        </logic:notEqual>
      </table>
      <div class="footerBtn_block">
        <% if ((haisinflg == 0) || (haisinflg == 2 && senderCnt > 0)) { %>
        <button type="button" value="<gsmsg:write key="anp.send2"/>" class="baseBtn" onClick="buttonPush('anp060knhaisin');">
          <img class="btn_classicImg-display" src="../anpi/images/classic/icon_okmail.gif" alt="<gsmsg:write key="anp.send2"/>">
          <img class="btn_originalImg-display" src="../common/images/original/icon_mail2.png" alt="<gsmsg:write key="anp.send2"/>">
          <gsmsg:write key="anp.send2"/>
        </button>
        <% } %>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp060knback');">
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
