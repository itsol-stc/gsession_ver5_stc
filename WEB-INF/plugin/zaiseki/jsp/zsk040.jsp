<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

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
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script language="JavaScript" src="../zaiseki/js/zsk040.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
</head>

<body>
<html:form action="/zaiseki/zsk040">
<input type="hidden" name="CMD">
<html:hidden name="zsk040Form" property="backScreen" />
<html:hidden name="zsk040Form" property="selectZifSid" />
<html:hidden name="zsk040Form" property="uioStatus" />
<html:hidden name="zsk040Form" property="uioStatusBiko" />
<html:hidden name="zsk040Form" property="sortKey" />
<html:hidden name="zsk040Form" property="orderKey" />

<html:hidden name="zsk040Form" property="zsk040initFlg" />

<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_GAZOU); %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="zsk.26" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('zsk040kn');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk030');">
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
  <div class="txt_l">
    <gsmsg:write key="zsk.zsk040.01" />
  </div>

  <table class="table-left w100">
    <tr>
      <th class="w25">
        <gsmsg:write key="zsk.08" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="zsk040Form" property="zsk040name" styleClass="wp320" maxlength="20" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.sort" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="zsk040Form" property="zasekiSortNum" styleClass="wp60" maxlength="3"/>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="zsk.27" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <attachmentFile:filearea
        mode="<%= tempMode %>"
        pluginId="<%=GSConst.PLUGINID_ZSK %>"
        tempDirId="zsk040" />
        <div>
          <gsmsg:write key="zsk.42" />
        </div>
        <div>
          <gsmsg:write key="zsk.41" />
        </div>
        <div>
          <gsmsg:write key="zsk.43" />
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('zsk040kn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk030');">
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
