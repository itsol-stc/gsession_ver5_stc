<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.sml.sml360.Sml360Form" %>
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

<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml360.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body onload="changeFilterInput();">

<html:form action="/smail/sml360">


<input type="hidden" id="CMD" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlCmdMode" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlFilterCmdMode" />
<html:hidden property="smlEditFilterId" />
<html:hidden property="sml360initFlg" />
<html:hidden property="dspCount" />
<html:hidden property="sml350SortRadio" />

<html:hidden property="sml360viewMailList" />
<html:hidden property="sml360mailListSortKey" />
<html:hidden property="sml360mailListOrder" />
<html:hidden property="sml360svFilterType" />
<html:hidden property="sml360svCondition1" />
<html:hidden property="sml360svConditionType1" />
<html:hidden property="sml360svConditionExs1" />
<html:hidden property="sml360svConditionText1" />
<html:hidden property="sml360svCondition2" />
<html:hidden property="sml360svConditionType2" />
<html:hidden property="sml360svConditionExs2" />
<html:hidden property="sml360svConditionText2" />
<html:hidden property="sml360svCondition3" />
<html:hidden property="sml360svConditionType3" />
<html:hidden property="sml360svConditionExs3" />
<html:hidden property="sml360svConditionText3" />
<html:hidden property="sml360svCondition4" />
<html:hidden property="sml360svConditionType4" />
<html:hidden property="sml360svConditionExs4" />
<html:hidden property="sml360svConditionText4" />
<html:hidden property="sml360svCondition5" />
<html:hidden property="sml360svConditionType5" />
<html:hidden property="sml360svConditionExs5" />
<html:hidden property="sml360svConditionText5" />
<html:hidden property="sml360svTempFile" />


<input type="hidden" name="sml360actionSendValueDelIdx" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.preferences2" /></li>
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
          <bean:write name="sml360Form" property="sml350accountName" />
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="wml.84" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">
          <html:text name="sml360Form" property="sml360FilterName" maxlength="100" styleClass="wp400" />
        </td>
      </tr>
    </table>

    <div class="mt20 w100 verAlignMid ">
      <gsmsg:write key="wml.40" />
      <span class="verAlignMid ml20"><html:radio name="sml360Form" property="sml360filterType" value="0" styleId="filtype1" /><label for="filtype1"><gsmsg:write key="wml.wml140.01" /></label>
      <span class="verAlignMid ml10"><html:radio name="sml360Form" property="sml360filterType" value="1" styleId="filtype2" /><label for="filtype2"><gsmsg:write key="wml.containing.either" /></label></span>
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
              <html:checkbox name="sml360Form" property="<%=\"sml360condition\" + condition%>" value="<%=String.valueOf(condition) %>" onclick="changeFilterInput();" />
              <logic:notEmpty name="sml360Form" property="conditionList1">
                <html:select property="<%=\"sml360conditionType\" + condition%>" size="1" styleClass="ml5">
                  <html:optionsCollection name="sml360Form" property="conditionList1" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <logic:notEmpty name="sml360Form" property="conditionList2">
                <html:select property="<%=\"sml360conditionExs\" + condition%>" size="1" styleClass="ml5">
                  <html:optionsCollection name="sml360Form" property="conditionList2" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <html:text property="<%=\"sml360conditionText\" + condition%>" maxlength="256" styleClass="wp250 ml5" />
            </div>
            <div class="settingForm_separator w100"></div>
          </logic:iterate>
          <div class="verAlignMid">
            <html:checkbox name="sml360Form" property="sml360tempFile" value="1" styleId="sml360tempFile" /><label for="sml360tempFile"><gsmsg:write key="wml.14" /></label>
          </div>
        </td>
      </tr>
    </table>

    <div class="txt_l mt20">
      <gsmsg:write key="wml.56" />
    </div>

    <table class="table-left w100 mt5">
      <tr>
        <th class="w5 txt_c js_tableTopCheck cursor_p">
          <html:checkbox name="sml360Form" property="sml360actionLabel" value="1" />
        </th>
        <td class="w75">
          <gsmsg:write key="wml.75" />
          <logic:notEmpty name="sml360Form" property="conditionList2">
            <html:select property="sml360actionLabelValue" size="1">
              <html:optionsCollection name="sml360Form" property="lbList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
        </td>
      </tr>
      <tr>
        <th class="w5 txt_c js_tableTopCheck cursor_p">
          <html:checkbox name="sml360Form" property="sml360actionRead" value="1" />
        </th>
        <td class="w75">
          <gsmsg:write key="cmn.mark.read" />
        </td>
      </tr>
      <tr>
        <th class="w5 txt_c js_tableTopCheck cursor_p">
          <html:checkbox name="sml360Form" property="sml360actionDust" value="1" />
        </th>
        <td class="w75">
          <gsmsg:write key="wml.91" />
        </td>
      </tr>
    </table>
    <logic:notEmpty name="sml360Form" property="sml360mailList">
      <div class="mt10 mb10 verAlignMid w100">
        <html:checkbox styleId="sml360doFilter" name="sml360Form" property="sml360doFilter" value="<%= String.valueOf(Sml360Form.SML360_DOFILTER_YES) %>" /><label for="sml360doFilter"><gsmsg:write key="wml.85" /></label>
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

    <logic:notEmpty name="sml360Form" property="sml360mailList">
        <logic:notEmpty name="sml360Form" property="sml360mailListPageCombo">
          <div class="paging flo_r mt5">
            <button type="button" class="webIconBtn ml_auto" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" styleId="sml360mailListPageTop" name="sml360Form" property="sml360mailListPageTop" onchange="changePage(0);">
              <html:optionsCollection name="sml360Form" property="sml360mailListPageCombo" value="value" label="label" />
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
          <bean:define id="sml360SortKey" name="sml360Form" property="sml360mailListSortKey" type="java.lang.Integer" />
          <bean:define id="sml360Order" name="sml360Form" property="sml360mailListOrder" type="java.lang.Integer" />
          <% String[] widthList = new String[] {"wp30", "wp40", "wp25"};
             jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
             String from = gsMsg.getMessage(request, "cmn.sendfrom");
             String subject = gsMsg.getMessage(request, "cmn.subject");
             String date = gsMsg.getMessage(request, "cmn.date");
             String down = "<span class=\"classic-display\">▼</span><i class=\"original-display icon-sort_down\"></i>";
             String up   = "<span class=\"classic-display\">▲</span><i class=\"original-display icon-sort_up\"></i>";
                                           %>
          <% String[] titleList = new String[] {from, subject, date}; %>
          <% int[] sortKeyList = new int[] {Sml360Form.SML360_SKEY_FROM, Sml360Form.SML360_SKEY_SUBJECT, Sml360Form.SML360_SKEY_DATE}; %>
          <% for (int i = 0; i < widthList.length; i++) { %>
          <%   String title = titleList[i]; %>
          <%   int order = Sml360Form.SML360_ORDER_ASC; %>
          <%   if (sortKeyList[i] == sml360SortKey.intValue()) { %>
          <%     if (sml360Order.intValue() == Sml360Form.SML360_ORDER_DESC) { %>
          <%       title = title + " " + up; %>
          <%     } else { %>
          <%       title = title + " " + down; %>
          <%       order = Sml360Form.SML360_ORDER_DESC; %>
          <%     } %>
          <%   } %>
          <th class="txt_c cursor_p table_title-color <%= widthList[i] %>"><a href="#" onClick="return sml360Sort(<%= String.valueOf(sortKeyList[i]) %>, <%= String.valueOf(order) %>)"><%= title %></a></th>
          <% } %>
        </tr>
        <logic:iterate id="mailData" name="sml360Form" property="sml360mailList" indexId="idx">
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
        <logic:notEmpty name="sml360Form" property="sml360mailListPageCombo">
          <button type="button" class="webIconBtn ml_auto" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" styleId="sml360mailListPageBottom" name="sml360Form" property="sml360mailListPageBottom" onchange="changePage(1);">
            <html:optionsCollection name="sml360Form" property="sml360mailListPageCombo" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </logic:notEmpty>
      </div>
    </logic:notEmpty>
    <div class="footerBtn_block ">
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