<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstTimecard" %>

<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd180.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.47" /></title>
</head>
<body>
<html:form action="/timecard/tcd180">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<html:hidden property="tcd180FileName"/>

<logic:notEmpty name="tcd180Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd180Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd180.01" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd180confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" onClick="buttonPush('tcd180back');" value="<gsmsg:write key="cmn.back" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <tr>
      <th class="w25 txt_l"><gsmsg:write key="tcd.tcd180.02" /></th>
      <td>
        <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd180.10" /></div>
        <span  class="verAlignMid">
          <html:radio styleId="tcd180Use_0" name="tcd180Form" property="tcd180Use" value="0" onclick="dispChg(0)" /><label for="tcd180Use_0"><gsmsg:write key="tcd.tcd180.11" /></label>
          <html:radio styleId="tcd180Use_1" name="tcd180Form" property="tcd180Use" value="1" onclick="dispChg(1)" styleClass="ml10"/><label for="tcd180Use_1"><gsmsg:write key="tcd.tcd180.12" /></label>
        </span>
      </td>
    </tr>
    <bean:define id="dispCls" value=""/>
    <logic:equal name="tcd180Form" property="tcd180Use" value="0">
      <bean:define id="dispCls" value="display_n"/>
    </logic:equal>
    <tr class="<%=dispCls%>" id="dispChgCompornent">
      <th class="w25 txt_l"><gsmsg:write key="tcd.tcd180.04" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td clas="w75">
        <div>
          <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
          <% String msg1 = "<a href=\"../timecard/tcd180.do?CMD=how_to_make_format\">" + gsMsg.getMessage(request, "tcd.tcd180.05") + "</a>"; %>
          <span class="fs_13">*<gsmsg:write key="tcd.tcd180.07" arg0="<%= msg1 %>" /></span>
        </div>
        <div>
          <span class="fs_13">*<a href="#" onClick="buttonPush('tcd180_example');"><gsmsg:write key="tcd.tcd180.06"/></a><gsmsg:write key="tcd.tcd180.08"/></span>
        </div>
        <attachmentFile:filearea
        mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
        pluginId="<%= GSConstTimecard.PLUGIN_ID_TIMECARD %>"
        tempDirId="tcd180" />
        <logic:equal name="tcd180Form" property="tcd180FormatExistFlg" value="1">
          <logic:equal name="tcd180Form" property="tcd180FormatDisp" value="1">
          <div>
            <span class="fs_13">
              <gsmsg:write key="tcd.tcd180.09" />
                <a href="#" onClick="buttonPush('tcd180_format');">
                  <bean:write name="tcd180Form" property="tcd180FileName" />
                </a>
            </span>
          </div>
          </logic:equal>
        </logic:equal>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('tcd180confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" onClick="buttonPush('tcd180back');" value="<gsmsg:write key="cmn.back" />">
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
