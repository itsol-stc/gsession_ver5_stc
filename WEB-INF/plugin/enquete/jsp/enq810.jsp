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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Script-Type" content="text/javascript">
<META http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences.menu" /></title>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enq810.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/enquete/enq810">
<!-- BODY -->
<input type="hidden" name="CMD">
<html:hidden property="cmd" />
<html:hidden property="backScreen" />

<!-- 検索条件hidden -->
<%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp" %>

<logic:notEmpty name="enq810Form" property="enq010priority">
<logic:iterate id="svPriority" name="enq810Form" property="enq010priority">
  <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq810Form" property="enq010status">
<logic:iterate id="svStatus" name="enq810Form" property="enq010status">
  <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq810Form" property="enq010svPriority">
<logic:iterate id="svPriority" name="enq810Form" property="enq010svPriority">
  <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq810Form" property="enq010svStatus">
<logic:iterate id="svStatus" name="enq810Form" property="enq010svStatus">
  <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq810Form" property="enq010statusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq810Form" property="enq010statusAnsOver">
  <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq810Form" property="enq010svStatusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq810Form" property="enq010svStatusAnsOver">
  <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>


<!-- header -->
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="enq.enq800.01"/>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('enq810commit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq810back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table cellpadding="10" cellspacing="0" border="0" class="w100 table-left">
    <logic:equal name="enq810Form" property="enq810DspUse" value="<%= String.valueOf(GSConstEnquete.CONF_LIST_USE_EACH) %>" >
      <!-- 一覧表示件数 -->
      <tr>
        <th class="w25">
          <span><gsmsg:write key="enq.enq810.01"/></span>
        </th>
        <td class="w75 txt_l">
          <span class="text_base">
            <logic:notEmpty name="enq810Form" property="enq810SelectCnt" scope="request">
              <html:select name="enq810Form" property="enq810SelectCnt">
                <html:optionsCollection name="enq810Form" property="enq810CntListLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </span>
          <span class="text_base"><gsmsg:write key="cmn.number" /></span>
        </td>
      </tr>

      <!-- 初期表示フォルダ選択 -->
      <tr>
        <th class="w25">
          <span><gsmsg:write key="enq.enq810.02"/></span>
        </th>
        <td class="w75 txt_l">
          <span>
            <logic:notEmpty name="enq810Form" property="enq810SelectFolder" scope="request">
              <html:select name="enq810Form" property="enq810SelectFolder" onchange="selectchage();" styleId="selectbox">
                <html:optionsCollection name="enq810Form" property="enq810FolderListLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </span>
          <span class="verAlignMid txt_m" id="canAnswer" >
            <html:checkbox name="enq810Form" property="enq810CanAnswer" styleId="enq810CanAnswer" onclick="check();"/><label for="enq810CanAnswer"><gsmsg:write key="enq.enq010.06" /></label>
          </span>
        </td>
      </tr>
    </logic:equal>
    <logic:equal name="enq810Form" property="enq810DspMainUse" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_USE_EACH) %>" >
      <!-- メイン表示フラグ -->
      <tr>
        <th class="w25">
          <span><gsmsg:write key="enq.enq820.01"/></span>
        </th>
        <td class="w75 txt_l">
          <span class="verAlignMid">
            <html:radio styleId="display_ok" name="enq810Form" property="enq810MainDsp" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_ON) %>" onclick="changeMainDspKbn(1);" />
            <label class="mr10" for="display_ok"><gsmsg:write key="cmn.display.ok"/></label>
            <html:radio styleId="display_show" name="enq810Form" property="enq810MainDsp" value="<%= String.valueOf(GSConstEnquete.CONF_MAIN_DSP_OFF) %>" onclick="changeMainDspKbn(0);" />
            <label for="display_show"><gsmsg:write key="cmn.dont.show"/></label>
          </span>
          <div class="js_dspMainDetail settingForm_separator">
            <div>
              <span class="verAlignMid">
                <%-- メイン表示件数 --%>
                <gsmsg:write key="enq.enq820.02"/>
                :&nbsp;
                <html:select name="enq810Form" property="enq810DspCntMain">
                    <html:optionsCollection name="enq810Form" property="dspCntComb" value="value" label="label"/>
                </html:select>
              </span>
            </div>
            <div>
              <span class="verAlignMid">
                <%-- 回答済みアンケートをメイン画面に表示するか --%>
                <gsmsg:write key="enq.enq820.03"/>
                :&nbsp;
                <html:radio styleId="display_checked_ok" name="enq810Form" property="enq810DspChecked" value="<%= String.valueOf(GSConstEnquete.DSP_CHECKED_OK) %>" />
                    <label class="mr10" for="display_checked_ok"><gsmsg:write key="cmn.display.ok"/></label>
                <html:radio styleId="display_checked_none" name="enq810Form" property="enq810DspChecked" value="<%= String.valueOf(GSConstEnquete.DSP_CHECKED_NONE) %>" />
                    <label for="display_checked_none"><gsmsg:write key="cmn.dont.show"/></label>
              </span>
            </div>
          </div>
        </td>
      </tr>
    </logic:equal>
  </table>

  <div class="footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('enq810commit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq810back');">
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