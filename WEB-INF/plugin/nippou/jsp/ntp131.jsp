<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String maxLengthHosoku = String.valueOf(1000);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp131.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="showLengthId($('inputstr'), <%= maxLengthHosoku %>, 'inputlength');">
<input type="hidden" name="helpPrm" value="<bean:write name="ntp131Form" property="ntp130ProcMode" />">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp131">

<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/nippou/jsp/ntp130_hiddenParams.jsp" %>
<html:hidden property="ntp130DspKbn" />
<html:hidden property="ntp130EditSid" />


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.58" /><gsmsg:write key="cmn.entry" />
    </li>
    <li>
      <div>
      <logic:equal name="ntp131Form" property="ntp130ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
        <button type="button" value="<gsmsg:write key="cmn.register.copy" />" class="baseBtn" onClick="buttonCopy('131_copy', 'add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
          <gsmsg:write key="cmn.register.copy2" />
        </button>
      </logic:equal>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush2('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
      <logic:equal name="ntp131Form" property="ntp130ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush2('del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
         <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp131');">
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
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w75">
        <html:select name="ntp131Form" property="ntp130SelCategorySid" styleClass="wp200">
        <logic:notEmpty name="ntp131Form" property="ntp130CategoryList">
        <html:optionsCollection name="ntp131Form" property="ntp130CategoryList" value="value" label="label" />
        </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.122" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="ntp131Form" property="ntp131ShohinCode" maxlength="13" styleClass="wp130" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.154" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="ntp131Form" property="ntp131ShohinName" maxlength="50" styleClass="wp500" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.76" />
      </th>
      <td>
        <html:text name="ntp131Form" property="ntp131PriceSale" maxlength="9" styleClass="wp100" />&nbsp;<gsmsg:write key="project.103" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.77" />
      </th>
      <td>
        <html:text name="ntp131Form" property="ntp131PriceCost" maxlength="9" styleClass="wp100" />&nbsp;<gsmsg:write key="project.103" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <textarea name="ntp131Hosoku" rows="10" class="wp500" onkeyup="showLengthStr(value, <%= maxLengthHosoku %>, 'inputlength');" id="inputstr"><bean:write name="ntp131Form" property="ntp131Hosoku" /></textarea>
        <br>
        <div class="fs_12"><gsmsg:write key="wml.js.15" /><span id="inputlength">0</span>&nbsp;/&nbsp;<%= maxLengthHosoku %>&nbsp;<gsmsg:write key="cmn.character" /></div>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
  <logic:equal name="ntp131Form" property="ntp130ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
    <button type="button" value="<gsmsg:write key="cmn.register.copy" />" class="baseBtn" onClick="buttonCopy('131_copy', 'add');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
      <gsmsg:write key="cmn.register.copy2" />
    </button>
  </logic:equal>
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush2('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
  <logic:equal name="ntp131Form" property="ntp130ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush2('del');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp131');">
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