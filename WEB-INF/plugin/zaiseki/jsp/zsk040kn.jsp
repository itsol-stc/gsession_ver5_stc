<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zsk040kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
</head>

<body>

<html:form action="/zaiseki/zsk040kn">
<input type="hidden" name="CMD">
<html:hidden name="zsk040Form" property="backScreen" />
<html:hidden name="zsk040Form" property="selectZifSid" />
<html:hidden name="zsk040Form" property="uioStatus" />
<html:hidden name="zsk040Form" property="uioStatusBiko" />
<html:hidden name="zsk040Form" property="sortKey" />
<html:hidden name="zsk040Form" property="orderKey" />

<html:hidden name="zsk040knForm" property="zsk040knTmpFileId" />
<html:hidden name="zsk040knForm" property="zsk040name" />
<html:hidden name="zsk040knForm" property="zasekiSortNum" />
<html:hidden name="zsk040knForm" property="zsk040initFlg" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="zsk.zsk040kn.01" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('cmn999');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk040');">
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
    <tr>
      <th class="w25">
        <gsmsg:write key="zsk.08" />
      </th>
      <td class="w75">
        <bean:write name="zsk040knForm" property="zsk040name" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.sort" />
      </th>
      <td>
        <bean:write name="zsk040knForm" property="zasekiSortNum" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="zsk.27" />
      </th>
      <td>
        <logic:empty name="zsk040knForm" property="zsk040FileLabelList" scope="request">&nbsp;</logic:empty>
        <logic:notEmpty name="zsk040knForm" property="zsk040FileLabelList" scope="request">
          <logic:iterate id="fileMdl" name="zsk040knForm" property="zsk040FileLabelList" scope="request">
            <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file" />">
            <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
            <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="value" />');">
            <bean:write name="fileMdl" property="label" />
            </a>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('cmn999');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk040');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
<IFRAME type="hidden" src="../common/html/damy.html" class="display_n" name="navframe"></IFRAME>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>