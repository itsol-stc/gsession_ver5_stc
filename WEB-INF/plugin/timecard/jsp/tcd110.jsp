<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.55" /></title>
</head>

<body>
<html:form action="/timecard/tcd110">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<html:hidden property="tcd110initFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.55" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_outputBtnExcel" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('tcd110_submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
          <gsmsg:write key="tcd.tcd010.12" />
        </button>

        <button type="button" class="baseBtn js_outputBtnPdf" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('tcd110_submit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
          <gsmsg:write key="tcd.tcd010.12" />
        </button>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd110_back');">
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
      <th class="w25">
        <gsmsg:write key="tcd.tcd110.01" />
      </th>
      <td class="w75">
        <html:select name="tcd110Form" property="tcd110Year" >
          <html:optionsCollection name="tcd110Form" property="tcd110YearLabelList" />
        </html:select>
        <html:select name="tcd110Form" property="tcd110Month" >
          <html:optionsCollection name="tcd110Form" property="tcd110MonthLabelList" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd110.04" />
      </th>
      <td class="w75">
        <logic:equal name="tcd110Form" property="kinmuFormatExists" value="0">
          <span  class="verAlignMid">
            <html:radio styleId="tcd110OutputFileType_0" name="tcd110Form" property="tcd110OutputFileType" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.KINMU_EXCEL) %>" onclick="changeOutputType();" /><label for="tcd110OutputFileType_0"><gsmsg:write key="tcd.tcd080.15" /></label>
            <html:radio styleId="tcd110OutputFileType_1" styleClass="ml10" name="tcd110Form" property="tcd110OutputFileType" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.KINMU_PDF) %>" onclick="changeOutputType();" /><label for="tcd110OutputFileType_1"><gsmsg:write key="tcd.tcd080.14" /></label>
          </span>
        </logic:equal>
        <logic:equal name="tcd110Form" property="kinmuFormatExists" value="1">
          <span><gsmsg:write key="tcd.tcd080.15" /></span>
          <div class="cl_fontWarn"><gsmsg:write key="tcd.tcd110.05" /></div>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="tcd.tcd110.02" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <ui:usrgrpselector name="tcd110Form" property="tcd110targetUI" styleClass="hp215" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">

    <button type="button" class="baseBtn js_outputBtnExcel" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('tcd110_submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_excel.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
      <gsmsg:write key="tcd.tcd010.12" />
    </button>

    <button type="button" class="baseBtn js_outputBtnPdf" value="<gsmsg:write key="tcd.tcd010.12" />" onClick="buttonPush('tcd110_submit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="tcd.tcd010.12" />">
      <gsmsg:write key="tcd.tcd010.12" />
    </button>

    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd110_back');">
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
