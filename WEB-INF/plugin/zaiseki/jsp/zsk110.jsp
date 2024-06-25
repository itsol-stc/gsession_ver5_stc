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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zsk110.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /> <gsmsg:write key="zsk.11" /></title>
</head>

<body onload="enableDisable();">
<html:form action="/zaiseki/zsk110">
<input type="hidden" name="CMD" value="">
<html:hidden name="zsk110Form" property="backScreen" />
<html:hidden name="zsk110Form" property="selectZifSid" />
<html:hidden name="zsk110Form" property="uioStatus" />
<html:hidden name="zsk110Form" property="uioStatusBiko" />
<html:hidden name="zsk110Form" property="sortKey" />
<html:hidden name="zsk110Form" property="orderKey" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('zsk110Ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk110back');">
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
        <gsmsg:write key="zsk.11" />
      </th>
      <td class="w75">
        <gsmsg:write key="zsk.zsk110.01" /><br>
      <div class="verAlignMid">
        <html:radio name="zsk110Form" property="zsk110UpdateKbn" styleId="zsk110UpdateKbn0" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.FIXED_UPDATE_ON) %>" onchange="enableDisable();" /><label for="zsk110UpdateKbn0"><gsmsg:write key="cmn.setting.do" /></label>
        <html:radio name="zsk110Form" styleClass="ml10" property="zsk110UpdateKbn" styleId="zsk110UpdateKbn1" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.FIXED_UPDATE_OFF) %>" onchange="enableDisable();" /><label for="zsk110UpdateKbn1"><gsmsg:write key="cmn.noset" /></label>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.starttime" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
      <html:select name="zsk110Form" property="zsk110StartTime">
        <logic:notEmpty name="zsk110Form" property="zsk110TimeList">
        <html:optionsCollection name="zsk110Form" property="zsk110TimeList" value="value" label="label" />
        </logic:notEmpty>
      </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="zsk.20" />
      </th>
      <td>
      <gsmsg:write key="zsk.zsk110.02" /><br>
      <div class="verAlignMid">
        <html:radio name="zsk110Form" property="zsk110Status" styleId="zsk110Status1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>" />
        <span class="zsk_label-zaiseki status_frame-base borC_light m0"><label for="zsk110Status1"><gsmsg:write key="cmn.zaiseki" /></label></span>
        <html:radio name="zsk110Form" styleClass="ml10" property="zsk110Status" styleId="zsk110Status2" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>" />
        <span class="zsk_label-huzai status_frame-base borC_light m0"><label for="zsk110Status2"><gsmsg:write key="cmn.absence" /></label></span>
        <html:radio name="zsk110Form" styleClass="ml10" property="zsk110Status" styleId="zsk110Status0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>" />
        <span class="zsk_label-sonota status_frame-base borC_light m0"><label for="zsk110Status0"><gsmsg:write key="cmn.other" /></label></span>
      </div>
      </td>
    </tr>
   <tr>
      <th>
        <gsmsg:write key="zsk.23" />
      </th>
      <td>
        <gsmsg:write key="zsk.zsk110.03" /><br>
        <html:text name="zsk110Form" property="zsk110Msg" maxlength="30" styleClass="wp150"/>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('zsk110Ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk110back');">
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