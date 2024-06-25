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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../smail/js/sml040.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="cmn.display.settings" /></title>
</head>

<body onload="setShowHide()">
<html:form action="/smail/sml040">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml040MaxDspStype" />
<html:hidden property="sml040ReloadTimeStype" />
<html:hidden property="sml040PhotoDspStype" />
<html:hidden property="sml040AttachImgDspStype" />

<logic:notEmpty name="sml040Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml040Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.setting" />" onClick="buttonPush('edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
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
    <tr id="smlMaxDspArea">
      <th class="w25">
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td class="w75">
        <gsmsg:write key="sml.sml040.02" />
        <div class="mt5">
          <html:select name="sml040Form" property="sml040ViewCnt">
          <html:optionsCollection name="sml040Form" property="sml040DspCntList" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>
    <tr id="smlReloadTimeArea">
      <th>
        <gsmsg:write key="cmn.auto.reload.time" />
      </th>
      <td>
      <gsmsg:write key="sml.sml040.01" />
      <div class="mt5">
        <html:select name="sml040Form" property="sml040ReloadTime">
        <html:optionsCollection name="sml040Form" property="sml040TimeLabelList" value="value" label="label" />
        </html:select>
      </div>
      </td>
    </tr>
    <tr id="smlPhotoDspArea">
      <th>
        <gsmsg:write key="sml.sml040.05" />
      </th>
      <td>
        <gsmsg:write key="sml.sml040.06" /><br>
        <div class="mt5 verAlignMid">
          <html:radio name="sml040Form" property="sml040PhotoDsp" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_IMAGE_TEMP_DSP) %>" styleId="sml040PhotoDsp_01"><label for="sml040PhotoDsp_01" class="mr10"><gsmsg:write key="cmn.show" /></label></html:radio>
          <html:radio name="sml040Form" property="sml040PhotoDsp" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_IMAGE_TEMP_NOT_DSP) %>" styleId="sml040PhotoDsp_02"><label for="sml040PhotoDsp_02"><gsmsg:write key="cmn.hide" /></label></html:radio>
        </div>
      </td>
    </tr>
    <tr id="smlAttachImgDspArea">
      <th>
        <gsmsg:write key="sml.sml040.07" />
      </th>
      <td>
        <gsmsg:write key="sml.sml040.08" /><br>
      <div class="mt5 verAlignMid">
        <html:radio name="sml040Form" property="sml040ImageTempDsp" value="0" styleId="sml040ImageTempDsp_01"><label for="sml040ImageTempDsp_01" class="mr10"><gsmsg:write key="cmn.show" /></label></html:radio>
        <html:radio name="sml040Form" property="sml040ImageTempDsp" value="1" styleId="sml040ImageTempDsp_02"><label for="sml040ImageTempDsp_02"><gsmsg:write key="cmn.hide" /></label></html:radio>
      </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="sml.sml190.02" />
      </th>
      <td class="w75">
        <html:select name="sml040Form" property="sml040mainDsp">
        <html:optionsCollection name="sml040Form" property="sml040mainDspList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.sml190.03" />
      </th>
      <td>
        <div class="verAlignMid">
        <html:radio name="sml040Form" property="sml040kidokuKbn" styleId="sml040kidokuKbn1" value="1"><label for="sml040kidokuKbn1" class="mr10"><gsmsg:write key="cmn.show" /></label></html:radio>
        <html:radio name="sml040Form" property="sml040kidokuKbn" styleId="sml040kidokuKbn2" value="0"><label for="sml040kidokuKbn2"><gsmsg:write key="cmn.hide" /></label></html:radio>
      </td>
    </tr>
     <tr>
      <th>
        <gsmsg:write key="cmn.sort" />
      </th>
      <td>
        <gsmsg:write key="sml.sml190.04" /><br>
        <div class="verAlignMid">
        <html:radio name="sml040Form" property="sml040mainSort" styleId="sml040mainSort1" value="1"><label for="sml040mainSort1" class="mr10"><gsmsg:write key="cmn.order.asc" /></label></html:radio>
        <html:radio name="sml040Form" property="sml040mainSort" styleId="sml040mainSort2" value="0"><label for="sml040mainSort2"><gsmsg:write key="cmn.order.desc" /></label></html:radio>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.setting" />" onClick="buttonPush('edit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
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