<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../nippou/js/ntp231.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<% String maxLengthContent = String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.MAX_LENGTH_VALUE); %>

<body onload="showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp231">
<input type="hidden" name="helpPrm" value="<bean:write name="ntp231Form" property="ntp230ProcMode" />">
<input type="hidden" name="CMD" value="">
<html:hidden property="ntp230NtgSid" />
<html:hidden property="ntp230ProcMode" />
<html:hidden property="ntp231initDspFlg" />
<html:hidden property="ntp231InitFlg" />

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.entry" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush2('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
      <logic:equal name="ntp231Form" property="ntp230ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush2('del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp231');">
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
        <gsmsg:write key="ntp.101" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="ntp231Form" property="ntp231TargetName" maxlength="50" styleClass="wp300" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.102" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="ntp231Form" property="ntp231TargetUnit" maxlength="15" styleClass="wp100" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.10" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="ntp231Form" property="ntp231TargetDef" styleClass="wp120 txt_r mr5" maxlength="15" />/<gsmsg:write key="cmn.month" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.content" />
      </th>
      <td>
        <% String onKeyUp = "showLengthStr(value, " + String.valueOf(maxLengthContent) + ", 'inputlength');"; %>
        <html:textarea name="ntp231Form" property="ntp231TargetDetail" styleClass="w100" rows="10" onkeyup="<%= onKeyUp %>" styleId="inputstr" />
        <br>
        <div class="fs_12"><gsmsg:write key="cmn.current.characters" />:<span id="inputlength">0</span>&nbsp;/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.template" />
      </th>
      <td>
        <gsmsg:write key="cmn.comments" /><gsmsg:write key="ntp.135" /><br>
        <button type="button" class="baseBtn" value="<gsmsg:write key="ntp.136" />" id="templatePopBtn">
          <gsmsg:write key="ntp.136" />
        </button>
          <div id="templateArea">
            <div>
              <logic:notEmpty name="ntp231Form" property="ntp231DspTemplate" scope="request">
                <logic:iterate id="nttSid" name="ntp231Form" property="ntp231DspTemplate" scope="request">
                  <input type="hidden" name="ntp231DspTemplate" value="<bean:write name="nttSid" />" />
                </logic:iterate>
              </logic:notEmpty>
            </div>
          </div>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush2('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
  <logic:equal name="ntp231Form" property="ntp230ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush2('del');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp231');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>



<div class="display_n" id="templatePop" title="<gsmsg:write key="ntp.136" />" >
  <p>
    <div class="hp300">
    <table class="table-top w100">
      <tr>
        <!-- チェックボックス -->
        <th class="w5 txt_c">
         <%--
           <input type="checkbox" name="cmn220AllCheckBottom" value="1" onClick="chgCheckAll('cmn220AllCheckBottom', 'cmn220userSidBottom');chgCheckAllChange2('cmn220AllCheckBottom', 'cmn220userSidBottom');">
        --%>
        </th>
        <!-- テンプレート名 -->
        <th class="w95">
          <gsmsg:write key="ntp.92" />
        </th>
      </tr>

      <tbody>
        <logic:notEmpty name="ntp231Form" property="ntp231TemplateList">
          <logic:iterate id="tmpMdl" name="ntp231Form" property="ntp231TemplateList">
            <bean:define id="nttsid" name="tmpMdl" property="nttSid" />
            <tr class="cursor_p js_listHover" id="tr_<%= nttsid.toString() %>" onclick="clickTemplateName(3, <%= nttsid.toString() %>);">

              <!-- チェックボックス -->
              <td class="w5 txt_c">
                <%-- html:multibox name="ntp087Form" property="ntp087DspTarget" value="<%= ntgsid.toString() %>" onclick="clickMulti();"/ --%>
                <input type="checkbox" name="poptemplate" value="<%= nttsid.toString() %>" onclick="clickMulti();">
              </td>

              <!-- テンプレート名 -->
              <td class="w95" id="td_name_<%= nttsid.toString() %>"><bean:write name="tmpMdl" property="nttName" /></td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </tbody>
    </table>

    <logic:empty name="ntp231Form" property="ntp231TemplateList">
      <span class="h100 w100 fw_b txt_c" style="padding-top:220px;"><gsmsg:write key="ntp.137" /></span>
    </logic:empty>
    </div>
  </p>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>