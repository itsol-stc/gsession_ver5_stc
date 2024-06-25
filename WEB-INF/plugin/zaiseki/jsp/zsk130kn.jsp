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
<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/zaiseki/zsk130kn">
<input type="hidden" name="CMD">
<html:hidden name="zsk130knForm" property="backScreen" />
<html:hidden name="zsk130knForm" property="selectZifSid" />
<html:hidden name="zsk130knForm" property="uioStatus" />
<html:hidden name="zsk130knForm" property="uioStatusBiko" />
<html:hidden name="zsk130knForm" property="sortKey" />
<html:hidden name="zsk130knForm" property="orderKey" />
<html:hidden name="zsk130knForm" property="zsk130SortKey1" />
<html:hidden name="zsk130knForm" property="zsk130SortKey2" />
<html:hidden name="zsk130knForm" property="zsk130SortOrder1" />
<html:hidden name="zsk130knForm" property="zsk130SortOrder2" />
<html:hidden name="zsk130knForm" property="zsk130mainDspGpSid" />
<html:hidden name="zsk130knForm" property="zsk130Mode" />
<html:hidden name="zsk130knForm" property="zsk130mainDspSortKey1" />
<html:hidden name="zsk130knForm" property="zsk130mainDspSortOrder1" />
<html:hidden name="zsk130knForm" property="zsk130mainDspSortKey2" />
<html:hidden name="zsk130knForm" property="zsk130mainDspSortOrder2" />
<html:hidden name="zsk130knForm" property="zsk130mainDspSchViewDf" />
<html:hidden name="zsk130knForm" property="zsk130mainDspFlg" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="cmn.display.settings" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('updateZsk130');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backZsk130');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<table class="table-left">
   <logic:equal name="zsk130knForm" property="zsk130SortConfAble" value="true">
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.sort" />
      </th>
      <td class="w70">
      <gsmsg:write key="cmn.first.key" /><gsmsg:write key="wml.215" />
      <!-- キー1 -->
      <bean:write name="zsk130knForm" property="zsk130knSortKey1Name" />&nbsp;
      <logic:equal name="zsk130knForm" property="zsk130SortOrder1" value="0"><gsmsg:write key="cmn.order.asc" /></logic:equal>
      <logic:equal name="zsk130knForm" property="zsk130SortOrder1" value="1"><gsmsg:write key="cmn.order.desc" /></logic:equal>

      <br>

      <gsmsg:write key="cmn.second.key" /><gsmsg:write key="wml.215" />
      <!-- キー2 -->
      <bean:write name="zsk130knForm" property="zsk130knSortKey2Name" />&nbsp;
      <logic:equal name="zsk130knForm" property="zsk130SortOrder2" value="0"><gsmsg:write key="cmn.order.asc" /></logic:equal>
      <logic:equal name="zsk130knForm" property="zsk130SortOrder2" value="1"><gsmsg:write key="cmn.order.desc" /></logic:equal>
      </td>
    </tr>
  </logic:equal>
    <tr>
      <th class="w30">
        <gsmsg:write key="zsk.36" />
      </th>
      <td>
        <div class="">
          <gsmsg:write key="cmn.main.view2" />
          :
          <logic:equal name="zsk130knForm" property="zsk130mainDspFlg" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAINGRP_DSP) %>"><gsmsg:write key="cmn.display.ok" /></logic:equal>
          <logic:equal name="zsk130knForm" property="zsk130mainDspFlg" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAINGRP_NOT_DSP) %>"><gsmsg:write key="cmn.dont.show" /></logic:equal>
        </div>
        <logic:equal name="zsk130knForm" property="zsk130mainDspFlg" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAINGRP_DSP) %>">
          <div class="settingForm_separator">
            <div>
              <gsmsg:write key="cmn.show.group" /> : <bean:write name="zsk130knForm" property="zsk130mainDspGrpName" />
              <div class="mt5">
              <gsmsg:write key="cmn.first.key" /><gsmsg:write key="wml.215" />
              <!-- キー1 -->
              <bean:write name="zsk130knForm" property="zsk130mainDspSortKey1Name" />&nbsp;
              <logic:equal name="zsk130knForm" property="zsk130mainDspSortOrder1" value="0"><gsmsg:write key="cmn.order.asc" /></logic:equal>
              <logic:equal name="zsk130knForm" property="zsk130mainDspSortOrder1" value="1"><gsmsg:write key="cmn.order.desc" /></logic:equal>
              <br>
              <gsmsg:write key="cmn.second.key" /><gsmsg:write key="wml.215" />
              <!-- キー2 -->
              <bean:write name="zsk130knForm" property="zsk130mainDspSortKey2Name" />&nbsp;
              <logic:equal name="zsk130knForm" property="zsk130mainDspSortOrder2" value="0"><gsmsg:write key="cmn.order.asc" /></logic:equal>
              <logic:equal name="zsk130knForm" property="zsk130mainDspSortOrder2" value="1"><gsmsg:write key="cmn.order.desc" /></logic:equal>
            </div>
          </div>
          <div class="settingForm_separator">
            <gsmsg:write key="zsk.zskmaingrep.07" /> :
            <logic:equal name="zsk130knForm" property="zsk130mainDspSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_DSP) %>"><gsmsg:write key="cmn.display.ok" /></logic:equal>
            <logic:equal name="zsk130knForm" property="zsk130mainDspSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_NOT_DSP) %>"><gsmsg:write key="cmn.dont.show" /></logic:equal>
          </div>
        </logic:equal>

      </td>
    </tr>

  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('updateZsk130');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backZsk130');">
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