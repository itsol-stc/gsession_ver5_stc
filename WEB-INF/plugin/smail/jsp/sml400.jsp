<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="sml.sml400.01" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../smail/js/sml400.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="setShowHide();">
<html:form action="/smail/sml400">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlViewAccount" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />

<logic:notEmpty name="sml400Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml400Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToMenu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td class="w75">
        <gsmsg:write key="sml.sml040.02" /><br>
        <div class="verAlignMid">
          <html:radio name="sml400Form" styleId="sml400MaxDspStype_01" property="sml400MaxDspStype" value="1" onclick="changeStypeMaxDsp();" />
          <label for="sml400MaxDspStype_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sml400Form" styleId="sml400MaxDspStype_02" styleClass="ml10" property="sml400MaxDspStype" value="0" onclick="changeStypeMaxDsp();" />
          <label for="sml400MaxDspStype_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </div>
        <div id="smlMaxDspArea" class="settingForm_separator">
          <html:select name="sml400Form" property="sml400MaxDsp">
          <html:optionsCollection name="sml400Form" property="sml400MaxDspList" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.auto.reload.time" />
      </th>
      <td>
       <gsmsg:write key="sml.sml040.01" /><br>
         <div class="verAlignMid">
           <html:radio name="sml400Form" styleId="sml400ReloadTimeStype_01" property="sml400ReloadTimeStype" value="1" onclick="changeStypeReloadTime();" />
           <label for="sml400ReloadTimeStype_01"><gsmsg:write key="cmn.set.the.admin" /></label>
           <html:radio name="sml400Form" styleId="sml400ReloadTimeStype_02" styleClass="ml10" property="sml400ReloadTimeStype" value="0" onclick="changeStypeReloadTime();" />
           <label for="sml400ReloadTimeStype_02"><gsmsg:write key="cmn.set.eachuser" /></label>
          </div>
          <div id="smlReloadTimeArea" class="settingForm_separator">
            <html:select name="sml400Form" property="sml400ReloadTime">
            <html:optionsCollection name="sml400Form" property="sml400ReloadTimeList" value="value" label="label" />
            </html:select>
          </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.sml040.05" />
      </th>
      <td>
        <gsmsg:write key="sml.sml040.06" /><br>
          <div class="verAlignMid mt5">
            <html:radio name="sml400Form" styleId="sml400PhotoDspStype_01" property="sml400PhotoDspStype" value="1" onclick="changeStypePhotoDsp();" />
            <label for="sml400PhotoDspStype_01"><gsmsg:write key="cmn.set.the.admin" /></label>
            <html:radio name="sml400Form" styleId="sml400PhotoDspStype_02" styleClass="ml10" property="sml400PhotoDspStype" value="0" onclick="changeStypePhotoDsp();" />
            <label for="sml400PhotoDspStype_02"><gsmsg:write key="cmn.set.eachuser" /></label>
          </div>
          <div id="smlPhotoDspArea" class="settingForm_separator">
          <span class="verAlignMid">
            <html:radio name="sml400Form" property="sml400PhotoDsp" value="0" styleId="sml400PhotoDsp_01">
            <label for="sml400PhotoDsp_01"><gsmsg:write key="cmn.show" /></label></html:radio>
            <html:radio name="sml400Form" property="sml400PhotoDsp" styleClass="ml10" value="1" styleId="sml400PhotoDsp_02">
            <label for="sml400PhotoDsp_02"><gsmsg:write key="cmn.hide" /></label></html:radio>
          </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.sml040.07" />
      </th>
      <td>
        <gsmsg:write key="sml.sml040.08" /><br>
          <div class="verAlignMid mt5">
            <html:radio name="sml400Form" styleId="sml400AttachImgDspStype_01" property="sml400AttachImgDspStype" value="1" onclick="changeStypeAttachImgDsp();" />
            <label for="sml400AttachImgDspStype_01"><gsmsg:write key="cmn.set.the.admin" /></label>
            <html:radio name="sml400Form" styleId="sml400AttachImgDspStype_02" styleClass="ml10" property="sml400AttachImgDspStype" value="0" onclick="changeStypeAttachImgDsp();" />
            <label for="sml400AttachImgDspStype_02"><gsmsg:write key="cmn.set.eachuser" /></label>
          </div>
          <div id="smlAttachImgDspArea" class="settingForm_separator ">
            <div class="verAlignMid">
              <html:radio name="sml400Form"  property="sml400AttachImgDsp" value="0" styleId="sml400AttachImgDsp_01"></html:radio>
              <label for="sml400AttachImgDsp_01" class="mr10"><gsmsg:write key="cmn.show" /></label>
              <html:radio name="sml400Form" property="sml400AttachImgDsp" value="1" styleId="sml400AttachImgDsp_02"></html:radio>
              <label for="sml400AttachImgDsp_02"><gsmsg:write key="cmn.hide" /></label>
            </div>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToMenu');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>