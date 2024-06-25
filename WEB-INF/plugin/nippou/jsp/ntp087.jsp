<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../nippou/js/ntp087.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jquery.scrollTable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<%
   String maxLengthContent = String.valueOf(1000);
%>

<body onload="showLengthId($('#inputstr')[0], <%= maxLengthContent %>, 'inputlength');" onunload="windowClose();">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp087">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="helpPrm" value="<bean:write name="ntp087Form" property="ntp087ProcMode" />">
<html:hidden property="ntp086NttSid"/>
<html:hidden property="ntp087initDspFlg" />
<html:hidden property="ntp087InitFlg" />
<html:hidden property="ntp087ProcMode" />
<ntp:conf_hidden name="ntp087Form"/>

<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
     <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="cmn.template" /><gsmsg:write key="cmn.entry" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush2('ntp087ok');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:equal name="ntp087Form" property="ntp087ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush2('del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp087');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.92" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="ntp087Form" property="ntp087TemplateName" maxlength="50" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="reserve.100" />
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="ntp.95" />
        </div>
        <div class="verAlignMid">
          <html:multibox name="ntp087Form" property="ntp087DspItem" styleId="ntp087DspItem0" value="0" />
          <label for="ntp087DspItem0"><gsmsg:write key="ntp.11" /></label>
          <html:multibox name="ntp087Form" property="ntp087DspItem" styleId="ntp087DspItem1" styleClass="ml10" value="1" />
          <label for="ntp087DspItem1"><gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /></label>
          <html:multibox name="ntp087Form" property="ntp087DspItem" styleId="ntp087DspItem2" styleClass="ml10" value="2" />
          <label for="ntp087DspItem2"><gsmsg:write key="ntp.3" />/<gsmsg:write key="ntp.31" /></label>
          <html:multibox name="ntp087Form" property="ntp087DspItem" styleId="ntp087DspItem3" styleClass="ml10" value="3" />
          <label for="ntp087DspItem3"><gsmsg:write key="ntp.32" /></label>
          <html:multibox name="ntp087Form" property="ntp087DspItem" styleId="ntp087DspItem5" styleClass="ml10" value="5" />
          <label for="ntp087DspItem5"><gsmsg:write key="ntp.96" /></label>
          <html:multibox name="ntp087Form" property="ntp087DspItem" styleId="ntp087DspItem4" styleClass="ml10" value="4" />
          <label for="ntp087DspItem4">添付ファイル</span></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.138" />
      </th>
      <td class="w80">
        <button type="button" class="baseBtn js_targetPopBtn" value="<gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.select" />">
          <img class="btn_classicImg-display" src="../nippou/images/classic/icon_target_18.png" alt="<gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.select" />">
          <img class="btn_originalImg-display" src="../nippou/images/original/icon_target_18.png" alt="<gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.select" />">
          <gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.select" />
        </button>
        <div id="targetArea">
          <logic:notEmpty name="ntp087Form" property="ntp087DspTarget" scope="request">
            <logic:iterate id="trgSid" name="ntp087Form" property="ntp087DspTarget" scope="request">
              <input type="hidden" name="ntp087DspTarget" value="<bean:write name="trgSid" />" />
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch100.4" />
      </th>
      <td class="w80">
        <ui:usrgrpselector name="ntp087Form" property="ntp087memberSidUI" styleClass="hp215" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.content" />(<gsmsg:write key="ntp.10" />)
      </th>
      <td class="w80">
        <% String onKeyUp = "showLengthStr(value, " + String.valueOf(maxLengthContent) + ", 'inputlength');"; %>
        <html:textarea name="ntp087Form" property="ntp087Detail" styleClass="w100" rows="10" onkeyup="<%= onKeyUp %>" styleId="inputstr" />
        <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush2('ntp087ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="ntp087Form" property="ntp087ProcMode" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.CMD_EDIT) %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush2('del');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush2('backNtp087');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<div id="targetPop" title="<gsmsg:write key="ntp.12" /><gsmsg:write key="cmn.select" />" style="display:none;">
  <div class="hp260 ofy_s bor1 borC_light">
  <table class="table-top w100 m0 border_none">
    <tr>
      <th class="w5 border_left_none">
      </th>
      <th class="w80 no_w">
        <gsmsg:write key="ntp.101" />
      </th>
      <th class="w15 border_right_none">
        <gsmsg:write key="ntp.102" />
      </th>
    </tr>
    <logic:notEmpty name="ntp087Form" property="ntp087TargetList">
      <logic:iterate id="trgMdl" name="ntp087Form" property="ntp087TargetList">
        <bean:define id="ntgsid" name="trgMdl" property="ntgSid" />
        <tr id="tr_<%= ntgsid.toString() %>" class="js_listHover cursor_p" onclick="clickTargetName(3, <%= ntgsid.toString() %>);">
          <td class="w5 border_left_none">
            <div class="txt_c">
              <input type="checkbox" id="poptarget_<%= ntgsid.toString() %>" name="poptarget" value="<%= ntgsid.toString() %>" onclick="clickMulti();">
            </div>
          </td>
          <td class="w80 js_tdName_<%= ntgsid.toString() %>">
            <bean:write name="trgMdl" property="ntgName" />
          </td>
          <td class="w15 txt_r border_left_none border_right_none">
            <bean:write name="trgMdl" property="ntgUnit" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
    <logic:empty name="ntp087Form" property="ntp087TargetList">
      <tr>
        <td class="txt_c no_w border_left_none" colspan="3">
          <span class="cl_fontWarn fw_b"><gsmsg:write key="ntp.103" /></span>
        </td>
      </tr>
    </logic:empty>
  </table>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>