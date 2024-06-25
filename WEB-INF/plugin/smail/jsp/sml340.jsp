<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.sml.sml340.Sml340Form" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="wml.wml140.04" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<gsjsmsg:js filename="gsjsmsg.js"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml340.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>

</head>

<body onload="changeFilterInput();">

<html:form action="/smail/sml340">


<input type="hidden" id="CMD" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="smlAccountSid" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlFilterCmdMode" />
<html:hidden property="smlEditFilterId" />
<html:hidden property="sml340initFlg" />
<html:hidden property="dspCount" />
<html:hidden property="sml330SortRadio" />

<html:hidden property="sml340viewMailList" />
<html:hidden property="sml340mailListSortKey" />
<html:hidden property="sml340mailListOrder" />
<html:hidden property="sml340svFilterType" />
<html:hidden property="sml340svCondition1" />
<html:hidden property="sml340svConditionType1" />
<html:hidden property="sml340svConditionExs1" />
<html:hidden property="sml340svConditionText1" />
<html:hidden property="sml340svCondition2" />
<html:hidden property="sml340svConditionType2" />
<html:hidden property="sml340svConditionExs2" />
<html:hidden property="sml340svConditionText2" />
<html:hidden property="sml340svCondition3" />
<html:hidden property="sml340svConditionType3" />
<html:hidden property="sml340svConditionExs3" />
<html:hidden property="sml340svConditionText3" />
<html:hidden property="sml340svCondition4" />
<html:hidden property="sml340svConditionType4" />
<html:hidden property="sml340svConditionExs4" />
<html:hidden property="sml340svConditionText4" />
<html:hidden property="sml340svCondition5" />
<html:hidden property="sml340svConditionType5" />
<html:hidden property="sml340svConditionExs5" />
<html:hidden property="sml340svConditionText5" />
<html:hidden property="sml340svTempFile" />

<html:hidden property="sml240keyword" />
<html:hidden property="sml240group" />
<html:hidden property="sml240user" />
<html:hidden property="sml240svKeyword" />
<html:hidden property="sml240svGroup" />
<html:hidden property="sml240svUser" />
<html:hidden property="sml240sortKey" />
<html:hidden property="sml240order" />
<html:hidden property="sml240searchFlg" />

<input type="hidden" name="sml340actionSendValueDelIdx" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml140.04" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('filterList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>

    <table class="table-left w100">
      <tr>
        <th class="w25">
          <gsmsg:write key="wml.102" />
        </th>
        <td class="w75">
          <bean:write name="sml340Form" property="sml330accountName" />
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="wml.84" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">
          <html:text name="sml340Form" property="sml340FilterName" maxlength="100" styleClass="wp400" />
        </td>
      </tr>
    </table>

    <div class="mt20 w100 verAlignMid ">
      <gsmsg:write key="wml.40" />
      <span class="verAlignMid ml20"><html:radio name="sml340Form" property="sml340filterType" value="0" styleId="filtype1" /><label for="filtype1"><gsmsg:write key="wml.wml140.01" /></label></span>
      <span class="verAlignMid ml10"><html:radio name="sml340Form" property="sml340filterType" value="1" styleId="filtype2" /><label for="filtype2"><gsmsg:write key="wml.containing.either" /></label></span>
    </div>

    <table class="table-left w100 mt5">
      <tr>
        <th class="w25"><gsmsg:write key="cmn.conditions" /></th>
        <td class="w75">
          <%
            pageContext.setAttribute("conditions", new String[] {"1","2","3","4","5"});
          %>
          <logic:iterate id="condition" name="conditions" indexId="idx">
            <div class="verAlignMid">
              <html:checkbox name="sml340Form" property="<%=\"sml340condition\" + condition%>" value="<%=String.valueOf(condition) %>" onclick="changeFilterInput();" />
              <logic:notEmpty name="sml340Form" property="conditionList1">
                <html:select property="<%=\"sml340conditionType\" + condition%>" size="1" styleClass="ml5">
                  <html:optionsCollection name="sml340Form" property="conditionList1" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <logic:notEmpty name="sml340Form" property="conditionList2">
                <html:select property="<%=\"sml340conditionExs\" + condition%>" size="1" styleClass="ml5">
                  <html:optionsCollection name="sml340Form" property="conditionList2" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <html:text property="<%=\"sml340conditionText\" + condition%>" maxlength="256" styleClass="wp250 ml5" />
            </div>
            <div class="settingForm_separator w100"></div>
          </logic:iterate>
          <div class="verAlignMid">
            <html:checkbox name="sml340Form" property="sml340tempFile" value="1" styleId="sml340tempFile" /><label for="sml340tempFile"><gsmsg:write key="wml.14" /></label>
          </div>
        </td>
      </tr>
    </table>

    <div class="txt_l mt20">
      <gsmsg:write key="wml.56" />
    </div>

    <table class="table-left w100 mt5">
      <tr>
        <th class="w5 txt_c  js_tableTopCheck cursor_p">
          <html:checkbox name="sml340Form" property="sml340actionLabel" value="1" />
        </th>
        <td class="w95">
          <gsmsg:write key="wml.75" />
          <logic:notEmpty name="sml340Form" property="conditionList2">
            <html:select property="sml340actionLabelValue" size="1">
              <html:optionsCollection name="sml340Form" property="lbList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
        </td>
      </tr>
      <tr>
        <th class="w5 txt_c  js_tableTopCheck cursor_p">
          <html:checkbox name="sml340Form" property="sml340actionRead" value="1" />
        </th>
        <td class="w95">
          <gsmsg:write key="cmn.mark.read" />
        </td>
      </tr>
      <tr>
        <th class="w5 txt_c  js_tableTopCheck cursor_p">
          <html:checkbox name="sml340Form" property="sml340actionDust" value="1" />
        </th>
        <td class="w95">
          <gsmsg:write key="wml.91" />
        </td>
      </tr>
    </table>
    <logic:notEmpty name="sml340Form" property="sml340mailList">
      <div class="mt10 mb10 verAlignMid w100">
        <html:checkbox styleId="sml340doFilter" name="sml340Form" property="sml340doFilter" value="<%= String.valueOf(Sml340Form.SML340_DOFILTER_YES) %>" /><label for="sml340doFilter"><gsmsg:write key="wml.85" /></label>
        <div class="mrl_auto"></div>
      </div>
    </logic:notEmpty>
    <div class="display_inline mb10 flo_l">
       <button type="button" class="baseBtn" value="<gsmsg:write key="wml.wml140.03" />" onClick="buttonPush('filterSearch');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="wml.wml140.03" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="wml.wml140.03" />">
          <gsmsg:write key="wml.wml140.03" />
       </button>
    </div>
    <logic:notEmpty name="sml340Form" property="sml340mailList">
      <logic:notEmpty name="sml340Form" property="sml340mailListPageCombo">
        <div class="paging flo_r mt5">
          <button type="button" class="webIconBtn ml_auto" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" styleId="sml340mailListPageTop" name="sml340Form" property="sml340mailListPageTop" onchange="changePage(0);">
            <html:optionsCollection name="sml340Form" property="sml340mailListPageCombo" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>
      <table class="table-top w100">
        <tr>
          <th class="txt_c table_title-color w5">&nbsp;</th>
          <bean:define id="sml340SortKey" name="sml340Form" property="sml340mailListSortKey" type="java.lang.Integer" />
          <bean:define id="sml340Order" name="sml340Form" property="sml340mailListOrder" type="java.lang.Integer" />
          <% String[] widthList = new String[] {"wp30", "wp40", "wp25"};
             jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
             String from = gsMsg.getMessage(request, "cmn.sendfrom");
             String subject = gsMsg.getMessage(request, "cmn.subject");
             String date = gsMsg.getMessage(request, "cmn.date");
             String down = "<span class=\"classic-display\">▼</span><i class=\"original-display icon-sort_down\"></i>";
             String up   = "<span class=\"classic-display\">▲</span><i class=\"original-display icon-sort_up\"></i>";
                                           %>
          <% String[] titleList = new String[] {from, subject, date}; %>
          <% int[] sortKeyList = new int[] {Sml340Form.SML340_SKEY_FROM, Sml340Form.SML340_SKEY_SUBJECT, Sml340Form.SML340_SKEY_DATE}; %>
          <% for (int i = 0; i < widthList.length; i++) { %>
          <%   String title = titleList[i]; %>
          <%   int order = Sml340Form.SML340_ORDER_ASC; %>
          <%   if (sortKeyList[i] == sml340SortKey.intValue()) { %>
          <%     if (sml340Order.intValue() == Sml340Form.SML340_ORDER_DESC) { %>
          <%       title = title + " " + up; %>
          <%     } else { %>
          <%       title = title + " " + down; %>
          <%       order = Sml340Form.SML340_ORDER_DESC; %>
          <%     } %>
          <%   } %>
          <th class="txt_c table_title-color cursor_p <%= widthList[i] %>"><a href="#" onClick="return sml340Sort(<%= String.valueOf(sortKeyList[i]) %>, <%= String.valueOf(order) %>)"><%= title %></a></th>
          <% } %>
        </tr>
        <logic:iterate id="mailData" name="sml340Form" property="sml340mailList" indexId="idx">
          <tr>
            <td class="txt_c w5">
              <logic:notEqual name="mailData" property="binCnt" value="0">
                <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png">
                <img class="original-display" src="../common/images/original/icon_attach.png">
              </logic:notEqual>
            </td>
            <td class="w30">
              <logic:equal name="mailData" property="accountJkbn" value="0">
                <logic:equal name="mailData" property="usrSid" value="0">
                  <bean:write name="mailData" property="accountName" />  
                </logic:equal>
                <logic:notEqual name="mailData" property="usrSid" value="0">
                  <bean:write name="mailData" property="usiSei" /> <bean:write name="mailData" property="usiMei" />
                </logic:notEqual>
              </logic:equal>
              <logic:notEqual name="mailData" property="accountJkbn" value="0">
                <logic:equal name="mailData" property="usrSid" value="0">
                  <del><bean:write name="mailData" property="accountName" /></del>  
                </logic:equal>
                <logic:notEqual name="mailData" property="usrSid" value="0">
                  <del><bean:write name="mailData" property="usiSei" /> <bean:write name="mailData" property="usiMei" /></del>
                </logic:notEqual>
              </logic:notEqual>
            </td>
            <td class="w40"><bean:write name="mailData" property="smsTitle" /></td>
            <td class="w25"><bean:write name="mailData" property="strDate" /></td>
          </tr>
        </logic:iterate>
      </table>
      <div class="paging w100 pb20">
        <div class="mrl_auto"></div>
        <logic:notEmpty name="sml340Form" property="sml340mailListPageCombo">
          <button type="button" class="webIconBtn " onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" styleId="sml340mailListPageBottom" name="sml340Form" property="sml340mailListPageBottom" onchange="changePage(1);">
            <html:optionsCollection name="sml340Form" property="sml340mailListPageCombo" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </logic:notEmpty>
      </div>
    </logic:notEmpty>
    <div class="footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('filterList');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>

  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>