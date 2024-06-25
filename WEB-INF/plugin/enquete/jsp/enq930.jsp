<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>

<!DOCTYPE html>

<html:html lang="true">
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Script-Type" content="text/javascript">
<META http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION<gsmsg:write key="enq.plugin" /> <gsmsg:write key="cmn.display.settings" /></title>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enq930.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="enq930Init(<bean:write name='enq930Form' property='enq930DspKbn' />, <bean:write name='enq930Form' property='enq930MainDspKbn' />, <bean:write name='enq930Form' property='enq930MainDsp' />);">
<html:form action="/enquete/enq930">
<!-- BODY -->
<input type="hidden" name="CMD">
<html:hidden property="cmd" />
<html:hidden property="backScreen" />

<!-- 検索条件hidden -->
<%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp" %>

<logic:notEmpty name="enq930Form" property="enq010priority">
<logic:iterate id="svPriority" name="enq930Form" property="enq010priority">
  <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq930Form" property="enq010status">
<logic:iterate id="svStatus" name="enq930Form" property="enq010status">
  <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq930Form" property="enq010svPriority">
<logic:iterate id="svPriority" name="enq930Form" property="enq010svPriority">
  <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq930Form" property="enq010svStatus">
<logic:iterate id="svStatus" name="enq930Form" property="enq010svStatus">
  <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq930Form" property="enq010statusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq930Form" property="enq010statusAnsOver">
  <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq930Form" property="enq010svStatusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq930Form" property="enq010svStatusAnsOver">
  <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>

<!-- header -->
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('enq930commit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq930back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table class="table-left">
  <!-- 表示区分 -->
    <tr>
      <th class="w25">
       <gsmsg:write key="enq.enq930.01"/>
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio styleId="dspKbn0" name="enq930Form" property="enq930DspKbn" value="<%= String.valueOf(GSConstEnquete.CONF_LIST_USE_LIMIT) %>" onclick="changeDspKbn(0);" /><label for="dspKbn0"><gsmsg:write key="cmn.set.the.admin"/></label>
          <html:radio styleId="dspKbn1" styleClass="ml10" name="enq930Form" property="enq930DspKbn" value="<%= String.valueOf(GSConstEnquete.CONF_LIST_USE_EACH) %>" onclick="changeDspKbn(1);" /><label for="dspKbn1"><gsmsg:write key="cmn.set.eachuser"/></label>
        </span>
        <div class="settingForm_separator dspArea">
          <div>
            <div class="verAlignMid">
              <gsmsg:write key="enq.enq930.02"/><span class="ml5 mr5">:</span>
              <logic:notEmpty name="enq930Form" property="enq930SelectCnt" scope="request">
                <html:select name="enq930Form" property="enq930SelectCnt">
                  <html:optionsCollection name="enq930Form" property="enq930CntListLabel" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <gsmsg:write key="cmn.number" />
            </div>
          </div>


          <div class="mt5">
            <div class="verAlignMid">
              <gsmsg:write key="enq.enq810.02"/><span class="ml5 mr5">:</span>
              <logic:notEmpty name="enq930Form" property="enq930SelectFolder" scope="request">
                <html:select name="enq930Form" property="enq930SelectFolder" onchange="selectchage();" styleId="selectbox">
                <html:optionsCollection name="enq930Form" property="enq930FolderListLabel" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <span id="canAnswer" class="verAlignMid txt_m">
                <html:checkbox name="enq930Form" property="enq930CanAnswer" styleId="enq930CanAnswer" onclick="check();" styleClass="ml5"/><label for="enq930CanAnswer"><gsmsg:write key="enq.enq010.06" /></label>
              </span>
            </div>
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="enq.enq940.02"/>
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio styleId="mainDspKbn0" name="enq930Form" property="enq930MainDspKbn" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_USE_LIMIT) %>" onclick="changeMainDspKbn(0);" /><label for="mainDspKbn0"><gsmsg:write key="cmn.set.the.admin"/></label>
          <html:radio styleId="mainDspKbn1" styleClass="ml10" name="enq930Form" property="enq930MainDspKbn" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_USE_EACH) %>" onclick="changeMainDspKbn(1);" /><label for="mainDspKbn1"><gsmsg:write key="cmn.set.eachuser"/></label>
        </div>
        <div class="settingForm_separator js_mainDspArea">
          <div class="verAlignMid">
            <html:radio styleId="display_ok" name="enq930Form" property="enq930MainDsp" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_ON) %>" onclick="changeMainFlg(1);" /><label for="display_ok"><gsmsg:write key="cmn.display.ok"/></label>
            <html:radio styleClass="ml10" styleId="display_show" name="enq930Form" property="enq930MainDsp" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_OFF) %>" onclick="changeMainFlg(0);" /><label for="display_show"><gsmsg:write key="cmn.dont.show"/></label>
          </div>
          <div class="settingForm_separator js_dspMainDetail">
            <div>
              <div class="verAlignMid">
                <gsmsg:write key="enq.enq940.03"/><span class="ml5 mr5">:</span>
                <html:select name="enq930Form" property="enq930DspCntMain">
                  <html:optionsCollection name="enq930Form" property="dspCntComb" value="value" label="label"/>
                </html:select>
              </div>
            </div>
            <div class="mt5">
              <div class="verAlignMid">
                <gsmsg:write key="enq.enq940.04"/><span class="ml5 mr5">:</span>
                <html:radio styleId="display_checked_ok" name="enq930Form" property="enq930DspChecked" value="<%= String.valueOf(GSConstEnquete.DSP_CHECKED_OK) %>" /><label for="display_checked_ok"><gsmsg:write key="cmn.display.ok"/></label>
                <html:radio styleClass="ml10" styleId="display_checked_none" name="enq930Form" property="enq930DspChecked" value="<%= String.valueOf(GSConstEnquete.DSP_CHECKED_NONE) %>" /><label for="display_checked_none"><gsmsg:write key="cmn.dont.show"/></label>
              </div>
            </div>
          </div>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('enq930commit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq930back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>