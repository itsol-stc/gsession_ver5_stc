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
<title>GROUPSESSION <gsmsg:write key="cmn.project" /> <gsmsg:write key="project.11" /><gsmsg:write key="cmn.check" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/project/prj190kn">
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
<html:hidden property="prj190TodoDay" />
<html:hidden property="prj190TodoPrj" />
<html:hidden property="prj190TodoSts" />
<html:hidden property="prj190Project" />
<html:hidden property="prj190DefDsp" />
<html:hidden property="prj190mainDspDate" />
<html:hidden property="prj190mainDspStatus" />
<html:hidden property="prj190mainDspMember" />
<html:hidden property="prj190todoInputKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><gsmsg:write key="cmn.default.setting" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('update');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backPrj190');">
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
          <gsmsg:write key="cmn.initial.display" />&nbsp;:&nbsp;<bean:write name="prj190knForm" property="prj190knDefDsp" />
        </div>
      </div>
      <div class="settingForm_separator">
        <gsmsg:write key="project.prj220.1" />[<gsmsg:write key="project.prj010.2" />]
        <table class="w100">
          <tr class="border_none">
            <td class="txt_r border_none w25"><gsmsg:write key="cmn.date2" />：</td>
            <td class="border_none">
              <bean:write name="prj190knForm" property="prj190knTodoDateStr" />
            </td>
          </tr>
          <tr class="border_top_none border_bottom_none">
            <td class="txt_r border_none"><gsmsg:write key="cmn.project" />：</td>
            <td class="border_none">
              <bean:write name="prj190knForm" property="prj190knTodoProjectStr" />
            </td>
          </tr>
          <tr class="border_none">
          <td class="txt_r border_none"><gsmsg:write key="cmn.status" />：</td>
          <td class="border_none">
              <bean:write name="prj190knForm" property="prj190knTodoStatusStr" />
          </td>
          </tr>
        </table>

      </div>
      <div class="settingForm_separator">
        <gsmsg:write key="cmn.project" />[<gsmsg:write key="project.prj010.2" />]
        <table class="w100">
          <tr class="border_none">
            <td class="txt_r border_none w25"><gsmsg:write key="cmn.project" />：</td>

            <td class="border_none">
              <bean:write name="prj190knForm" property="prj190knProjectStr" />
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
          <th class="txt_r border_none w25">
              <gsmsg:write key="project.100" />：
          </th>
          <td class="border_none">
            <bean:write name="prj190knForm" property="prj190knMainDspDateStr" />
          </td>
        </tr>
        <tr class="border_none">
          <th class="txt_r border_none">
            <gsmsg:write key="cmn.status" />：
          </th>
          <td class="border_none">
            <bean:write name="prj190knForm" property="prj190knMainDspStatusStr" />
          </td>
        </tr>
        <tr class="border_none">
          <th class="txt_r border_none">
            <gsmsg:write key="cmn.member" />：
          </th>
          <td class="border_none">
            <bean:write name="prj190knForm" property="prj190knMainDspMemberStr" />
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
      <span class="verAlignMid">
        <logic:equal name="prj190knForm" property="prj190todoInputKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO_EASY) %>"><gsmsg:write key="project.prj050.2" /></logic:equal>
        <logic:equal name="prj190knForm" property="prj190todoInputKbn" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.DSP_TODO_DETAIL) %>"><gsmsg:write key="project.prj050.1" /></logic:equal>
      </span>
    </td>
  </tr>
</table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('update');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backPrj190');">
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