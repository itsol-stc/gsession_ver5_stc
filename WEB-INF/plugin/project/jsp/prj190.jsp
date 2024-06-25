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
<title>GROUPSESSION <gsmsg:write key="project.107" /> <gsmsg:write key="project.11" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/project/prj190">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="prj010cmdMode" />
<html:hidden property="prj010sort" />
<html:hidden property="prj010order" />
<html:hidden property="prj010page1" />
<html:hidden property="prj010page2" />
<html:hidden property="prj010Init" />
<html:hidden property="selectingProject" />
<html:hidden property="selectingTodoDay" />
<html:hidden property="selectingTodoPrj" />
<html:hidden property="selectingTodoSts" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('edit');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backKmenu');">
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
    <th class="w30">
      <gsmsg:write key="project.11" />
    </th>
    <td class="w70">
      <div>
        <div class="verAlignMid">
          <gsmsg:write key="cmn.initial.display" />&nbsp;:&nbsp;
          <html:radio name="prj190Form" styleId="prj190Dsp_01" property="prj190DefDsp" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO) %>" /><label for="prj190Dsp_01">TODO</label>
          <html:radio name="prj190Form" styleClass="ml10" styleId="prj190Dsp_02" property="prj190DefDsp" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_PROJECT) %>" /><label for="prj190Dsp_02"><gsmsg:write key="cmn.project" /></label>
        </div>
      </div>
      <div class="settingForm_separator">
        <gsmsg:write key="project.prj220.1" />[<gsmsg:write key="project.prj010.2" />]
        <table class="w100">
          <tr class="border_none">
            <td class="txt_r border_none w25"><gsmsg:write key="cmn.date2" />：
            <td class="border_none">
              <logic:notEmpty name="prj190Form" property="prj190TodoDayList">
                <html:select styleClass="wp300" name="prj190Form" property="prj190TodoDay">
                  <html:optionsCollection name="prj190Form" property="prj190TodoDayList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </td>
          </tr>
          <tr class="border_top_none border_bottom_none">
            <td class="txt_r border_none"><gsmsg:write key="cmn.project" />：
            <td class="border_none">
              <logic:notEmpty name="prj190Form" property="prj190TodoProjectList">
                <html:select styleClass="wp300" name="prj190Form" property="prj190TodoPrj" onchange="return buttonPush('changeList');">
                  <html:optionsCollection name="prj190Form" property="prj190TodoProjectList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </td>
          </tr>
          <tr class="border_none">
          <td class="txt_r border_none"><gsmsg:write key="cmn.status" />：
          <td class="border_none">
            <logic:notEmpty name="prj190Form" property="prj190TodoStsList">
              <html:select styleClass="wp300" name="prj190Form" property="prj190TodoSts">
                <html:optionsCollection name="prj190Form" property="prj190TodoStsList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
          </tr>
        </table>

      </div>
      <div class="settingForm_separator">
        <gsmsg:write key="cmn.project" />[<gsmsg:write key="project.prj010.2" />]
        <table  class="w100">
          <tr class="border_none">
            <td class="txt_r border_none  w25"><gsmsg:write key="cmn.project" />：
            <td class="border_none">
              <logic:notEmpty name="prj190Form" property="prj190ProjectList">
                <html:select name="prj190Form" property="prj190Project">
                  <html:optionsCollection name="prj190Form" property="prj190ProjectList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
  <tr>
    <th>
      <gsmsg:write key="project.12" />
    </th>
    <td>
      <table class="w100">
        <tr class="border_none">
          <th class="txt_r border_none  w25">
              <gsmsg:write key="project.100" />：
          </th>
          <td class="border_none">
            <logic:notEmpty name="prj190Form" property="prj190mainDspDateList">
              <html:select name="prj190Form" property="prj190mainDspDate" styleClass="select01">
                <html:optionsCollection name="prj190Form" property="prj190mainDspDateList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr class="border_none">
          <th class="txt_r border_none">
            <gsmsg:write key="cmn.status" />：
          </th>
          <td class="border_none">
            <logic:notEmpty name="prj190Form" property="prj190mainDspStatusList">
              <html:select name="prj190Form" property="prj190mainDspStatus" styleClass="select01">
                <html:optionsCollection name="prj190Form" property="prj190mainDspStatusList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr class="border_none">
          <th class="txt_r border_none">
            <gsmsg:write key="cmn.member" />：
          </th>
          <td class="border_none">
            <logic:notEmpty name="prj190Form" property="prj190mainDspMemberList">
              <html:select name="prj190Form" property="prj190mainDspMember" styleClass="select01">
                <html:optionsCollection name="prj190Form" property="prj190mainDspMemberList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <th>
      <gsmsg:write key="project.prj080.9" />
    </th>
    <td>
      <gsmsg:write key="project.prj080.10" /><br/>
      <span class="verAlignMid">
        <html:radio name="prj190Form" styleId="prj190todoInputKbn_01" property="prj190todoInputKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO_EASY) %>" /><label for="prj190todoInputKbn_01"><span class="text_bb1"><gsmsg:write key="project.prj050.2" /></span></label>
        <html:radio name="prj190Form" styleClass="ml10" styleId="prj190todoInputKbn_02" property="prj190todoInputKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO_DETAIL) %>" /><label for="prj190todoInputKbn_02"><span class="text_bb1"><gsmsg:write key="project.prj050.1" /></span></label>
      </span>

    </td>
  </tr>
</table>
<div class="footerBtn_block">
  <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('edit');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
    <gsmsg:write key="cmn.ok" />
  </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backKmenu');">
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