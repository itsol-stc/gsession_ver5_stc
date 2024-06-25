<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>



<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="schedule.sch040.3" /></title>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>


</head>

<body>

<html:form action="/schedule/sch220">

<input type="hidden" name="CMD" value="">

<!--@BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
      <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
    <li>
      <gsmsg:write key="schedule.108" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="schedule.sch040.3" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper">

  <table class="table-top w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch040.7" />
      </th>
      <th class="w20">
        <gsmsg:write key="cmn.name" />
      </th>
      <th class="w10">
        <gsmsg:write key="schedule.sch040.8" />
      </th>
      <th class="w50">
        <gsmsg:write key="cmn.comment" />
      </th>
    </tr>
    <logic:notEmpty name="sch220Form" property="sch040AttendAnsList">
    <logic:iterate id="attendMdl" name="sch220Form" property="sch040AttendAnsList" indexId="idx">
      <tr>
      <td class="w20 txt_c">
      <logic:equal name="attendMdl" property="attendAnsKbn" value="0">
        <gsmsg:write key="schedule.sch040.9" />
      </logic:equal>
      <logic:notEqual name="attendMdl" property="attendAnsKbn" value="0">
        <bean:write name="attendMdl" property="attendAnsDate" />
      </logic:notEqual>
      </td>
      <td class="w20">
        <bean:write name="attendMdl" property="attendAnsUsrName" />
      </td>
      <td class="w10 txt_c">
        <logic:equal name="attendMdl" property="attendAnsKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_ANS_NONE) %>"><gsmsg:write key="schedule.sch040.4"/></logic:equal>
        <logic:equal name="attendMdl" property="attendAnsKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_ANS_YES) %>"><span class="cl_fontSafe"><gsmsg:write key="schedule.sch040.5"/></span></logic:equal>
        <logic:equal name="attendMdl" property="attendAnsKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.ATTEND_ANS_NO) %>"><span class="cl_fontWarn"><gsmsg:write key="schedule.sch040.6"/></span></logic:equal>
      </td>
      <td class="w50 txt_l" >
        <bean:write name="attendMdl" property="attendAnsComment" />
      </td>
      </tr>
    </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block mt10 mb20 flo_r">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>

</div>
</html:form>


</body>
</html:html>