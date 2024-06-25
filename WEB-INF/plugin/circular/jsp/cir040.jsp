<%@page import="jp.groupsession.v2.cir.model.CirAccountModel"%>
<%@page import="java.util.List"%>
<%@page import="jp.groupsession.v2.cir.cir040.Cir040Form"%>
<%@page import="jp.groupsession.v2.cir.cir250.Cir250Form"%>
<%@page import="jp.groupsession.v2.cir.biz.CirCommonBiz"%>
<%@page import="jp.groupsession.v2.cir.GSConstCircular"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/circular/" prefix="circular" %>
<%@ taglib tagdir="/WEB-INF/tags/ui/" prefix="ui" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cir.GSConstCircular"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>

<% int maxLengthNaiyou = CirCommonBiz.getBodyLimitLength(); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../circular/js/cir040.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../circular/css/circular.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<logic:notEmpty name="cir040Form" property="cir010AccountTheme" scope="request">
  <bean:define id="selectThemePath" name="cir040Form" property="cir010AccountTheme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="cir040Form" property="cir010AccountTheme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>
<title>GROUPSESSION <gsmsg:write key="cir.19" /></title>
</head>


<% if (maxLengthNaiyou <= 0) {%>
<body onunload="windowClose();" onload="">
  <% } else { %>

<body onunload="windowClose();" onload="showLengthId($('#inputstr')[0], <%= String.valueOf(maxLengthNaiyou) %>, 'inputlength');">
  <% } %>
  <html:form action="/circular/cir040">

    <input type="hidden" name="CMD" value="ok">
    <bean:define id="mode" value="0" />
    <logic:equal name="cir040Form" property="cirEntryMode" value="2">
      <bean:define id="mode" value="2" />
    </logic:equal>
    <input type="hidden" name="helpPrm" value="<bean:write name="mode"/>">
    <html:hidden property="cirViewAccount" />
    <html:hidden property="cirAccountMode" />
    <html:hidden property="cirAccountSid" />
    <html:hidden property="cirEditInfSid" />
    <html:hidden property="cir010cmdMode" />
    <html:hidden property="cirEntryMode" />
    <html:hidden property="cir010orderKey" />
    <html:hidden property="cir010sortKey" />
    <html:hidden property="cir010pageNum1" />
    <html:hidden property="cir010pageNum2" />
    <html:hidden property="cir010SelectLabelSid" />
    <%-- <html:hidden property="cir040pluginId" /> --%>
    <html:hidden property="cir040InitFlg" />
    <html:hidden property="cir040memoPeriod" />
    <html:hidden property="cir040webmail" />
    <input type="hidden" name="yearRangeMinFr" value="0" />
    <input type="hidden" name="yearRangeMaxFr" value="1" />

    <% boolean callWebmail = true; %>
    <logic:notEqual name="cir040Form" property="cir040webmail" value="1">
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
      <% callWebmail = false; %>
    </logic:notEqual>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../circular/images/classic/header_circular.png" alt="<gsmsg:write key="cir.5" />"> <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_circular_50.png" alt="<gsmsg:write key="cir.5" />">
        </li>
        <li>
          <gsmsg:write key="cir.5" />
        </li>
        <li class="pageTitle_subFont">
          <logic:notEqual name="cir040Form" property="cirEntryMode" value="2">
            <gsmsg:write key="cmn.create.new" />
          </logic:notEqual>
          <logic:equal name="cir040Form" property="cirEntryMode" value="2">
            <gsmsg:write key="cmn.edit" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <% if (callWebmail) { %>
            <button type="button" name="btn_close" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.parent.webmailEntrySubWindowClose();">
             <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
             <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
              <gsmsg:write key="cmn.close" />
            </button>
            <% } else { %>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('cir040back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
            <% } %>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cir.2" />
            </span>
          </th>
          <bean:define id="mukoUserClass" value="" />
          <logic:equal name="cir040Form" property="cirViewAccountUko" value="1">
            <bean:define id="mukoUserClass" value="mukoUser" />
          </logic:equal>

          <td class="txt_l w75">
          <logic:equal name="cir040Form" property="cirEntryMode" value="<%=String.valueOf(GSConstCircular.CIR_ENTRYMODE_EDIT) %>">
            <html:hidden name="cir040Form" property="cir040AccountSid" />
            <span class="<%=mukoUserClass%>">
              <bean:write name="cir040Form" property="cirViewAccountName" />
            </span>
          </td>

          </logic:equal>
          <logic:notEqual name="cir040Form" property="cirEntryMode" value="<%=String.valueOf(GSConstCircular.CIR_ENTRYMODE_EDIT) %>">
            <html:select property="cir040AccountSid" styleId="account_comb_box" styleClass="wp250">
              <logic:notEmpty name="cir040Form" property="cir010AccountList">
                <logic:iterate id="accountMdl" name="cir040Form" property="cir010AccountList">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUserOption" />
                  </logic:equal>
                  <bean:define id="accoutVal" name="accountMdl" property="accountSid" type="java.lang.Integer" />
                  <html:option styleClass="<%= mukoUserClass %>" value="<%= String.valueOf(accoutVal) %>">
                    <bean:write name="accountMdl" property="accountName" />
                  </html:option>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>
          </logic:notEqual>
        </tr>

        <!-- 回覧先 -->
        <tr>
          <th class="w25">
            <span>
              <gsmsg:write key="cir.20" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w75">
            <table>

              <tr>
                <td class="border_none m0 p0">
                  <button type="button" id="<gsmsg:write key="cmn.from" />" value="<gsmsg:write key="sml.sml020.05" />" class="baseBtn js_cirSendUser no_w m0">
                    <img class="btn_classicImg-display img-18" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="cmn.from" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_user.png" alt="<gsmsg:write key="cmn.from" />">
                    <gsmsg:write key="sml.sml020.05" />
                    <span class="js_mailSendSelBtn_data"
                           data-displayname="<gsmsg:write key="cir.20" />"
                           data-addarea="#atesaki_to_area"
                           data-inputname="cir040userSid"
                         />

                  </button>
                </td>

                <td class="border_none pl10 txt_l w100">
                  <div class="w100 ">
                    <%
                      Cir040Form form = (Cir040Form)request.getAttribute("cir040Form");
                      List<CirAccountModel> toList = form.getCir040MemberList();
                      int toSize = 0;
                      if (toList != null && toList.size() > 0) {
                          toSize = toList.size();
                      }
                      pageContext.setAttribute("toSize", Integer.valueOf(toSize));
                    %>
                    <bean:define id="clsScr" value="" />
                    <logic:greaterThan name="toSize" value="10">
                      <bean:define id="clsScr" value="sendAtesakiList-scr" />
                    </logic:greaterThan>
                    <div id="atesaki_to_area" class="js_selectAtesakiArea  pt5 pr5 mb5 <%= clsScr%>">
                      <logic:notEmpty  name="cir040Form" property="cir040MemberList">
                        <logic:iterate id="user" name="cir040Form" property="cir040MemberList" type="CirAccountModel">
                          <circular:selectedAccountList memMdl="<%=user %>" inputName="cir040userSid" />
                        </logic:iterate>
                      </logic:notEmpty>
                    </div>
                    <a href="#!" class="js_atesakiAllDisp fs_12 "><%--
                  --%><logic:greaterThan name="toSize" value="10"><%--
                     --%><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.show" /><%--
                   --%></logic:greaterThan><%--
                 --%></a>
                  </div>
                </td>

              </tr>
            </table>
          </td>
        </tr>

        <!-- タイトル -->
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.title" /><span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w75">
            <html:text maxlength="70" property="cir040title" styleClass="wp600" />
          </td>
        </tr>

        <!-- 内容 -->
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.content" /><span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l w75">
            <textarea class="wp600" name="cir040value" rows="10" onkeyup="showLengthStr(value, <%= String.valueOf(maxLengthNaiyou) %>, 'inputlength');" id="inputstr"><bean:write name="cir040Form" property="cir040value" /></textarea>
            <% if (maxLengthNaiyou > 0) {%>
            <div class="formCounter flo_l m0">
              <gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span><span class="formCounter_max"> / <%= String.valueOf(maxLengthNaiyou) %>
              <gsmsg:write key="cmn.character" />
            </div>
            <% } %>

          </td>
        </tr>

        <logic:equal name="cir040Form" property="cir040memoKbn" value="0">
          <!-- メモ欄修正区分 -->
          <tr>
            <th class="w25 no_w">
              <gsmsg:write key="cir.cir040.2" />
            </th>
            <td class="no_w txt_l w75">
              <span class="verAlignMid">
              <span>
                <gsmsg:write key="cir.cir040.3" />
              </span>
              <html:radio value="0" property="cir040memoKbn" styleClass="ml10" styleId="memoNg" onclick="buttonPush('memoKbnChange');" />
              <label for="memoNg">
                <span>
                  <gsmsg:write key="cmn.not" />
                </span>
              </label>
              <html:radio value="1" property="cir040memoKbn" styleClass="ml10" styleId="memoOk" onclick="buttonPush('memoKbnChange');" />
              <label for="memoOk">
                <span>
                  <gsmsg:write key="cmn.accepted" />
                </span>
              </label>
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="cir040Form" property="cir040memoKbn" value="1">
          <!-- メモ欄修正区分 -->
          <tr>
            <th class="w25 no_w">
              <span>
                <gsmsg:write key="cir.cir040.2" />
              </span>
            </th>
            <td class="txt_l no_w w75">
              <span class="verAlignMid">
              <span>
                <gsmsg:write key="cir.cir040.3" />
              </span>
              <html:radio value="0" property="cir040memoKbn" styleClass="ml10" styleId="memoNg" onclick="buttonPush('memoKbnChange');" />
              <label for="memoNg">
                <span>
                  <gsmsg:write key="cmn.not" />
                </span>
              </label>
              <html:radio value="1" property="cir040memoKbn" styleClass="ml10" styleId="memoOk" onclick="buttonPush('memoKbnChange');" />
              <label for="memoOk">
                <span>
                  <gsmsg:write key="cmn.accepted" />
                </span>
              </label>
              </span>
              <!-- メモ欄修正期限設定 -->
              <div class="mt10">
                <gsmsg:write key="cir.54" />
              </div>
              <!-- 期間指定の場合 -->
              <div>
                <a href="#!" onClick="return clickPeriod('clickPeriod', '1');" class="mr10">
                  <span>
                    <gsmsg:write key="cmn.today" />
                  </span>
                </a>
                <a href="#!" onClick="return clickPeriod('clickPeriod', '0');" class="mr10">
                  <span>
                    1<gsmsg:write key="cmn.weeks" />
                  </span>
                </a>
                <a href="#!" onClick="return clickPeriod('clickPeriod', '2');" class="mr10">
                  <span>
                    2<gsmsg:write key="cmn.weeks" />
                  </span>
                </a>
                <a href="#!" onClick="return clickPeriod('clickPeriod', '3');" class="mr10">
                  <span>
                    <gsmsg:write key="cmn.months" arg0="1" />
                  </span>
                </a>
              </div>
              <!-- 日付指定の場合 -->
               <div class="verAlignMid">
              <html:text name="cir040Form" property="cir040memoPeriodDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selYear"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selYear')[0], 1)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onclick="return moveDay($('#selYear')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return moveDay($('#selYear')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selYear')[0], 3)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right"></i>
              </button>
              <span class="ml10 cl_fontWarn">
                <gsmsg:write key="cir.cir040.4" />
              </span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <!-- 添付 -->
        <th class="w25">
          <span>
            <gsmsg:write key="cmn.attached" />
          </span>
        </th>
        <td class="w75">
          <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
          pluginId="<%= GSConstCircular.PLUGIN_ID_CIRCULAR %>"
          tempDirId="cir040"
          tempDirPlus="<%= GSConstCircular.TEMP_DIR_NEW %>" />
        </td>

        <!-- 公開／非公開   -->
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cir.cir030.3" />
            </span>
          </td>
          <td class="txt_l w75">
            <span class="verAlignMid">
              <html:radio name="cir040Form" property="cir040show" styleId="kokai" value="0" />
              <label for="kokai">
                <span>
                  <gsmsg:write key="cmn.public" />
                </span>
              </label>
              <html:radio name="cir040Form" property="cir040show" styleClass="ml10" styleId="hikokai" value="1" />
              <label for="hikokai">
                <span>
                  <gsmsg:write key="cmn.private" />
                </span>
              </label>
            </span>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <%
          if (callWebmail) {
        %>
        <button type="button" name="btn_close" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.parent.webmailEntrySubWindowClose();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
        <%
          } else {
        %>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('cir040back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
        <%
          }
        %>
      </div>
    </div>

    <logic:notEqual name="cir040Form" property="cir040webmail" value="1">
      <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
    </logic:notEqual>

  </html:form>
  <circular:accountSelector name="cir040Form" property="cir040MemberSelector"/>

</body>
</html:html>