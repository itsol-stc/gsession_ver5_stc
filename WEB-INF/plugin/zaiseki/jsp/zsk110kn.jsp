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
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /> <gsmsg:write key="zsk.zsk110kn.02" /></title>
</head>

<body>
<html:form action="/zaiseki/zsk110kn">
<input type="hidden" name="CMD" value="">
<html:hidden name="zsk110knForm" property="backScreen" />
<html:hidden name="zsk110knForm" property="selectZifSid" />
<html:hidden name="zsk110knForm" property="uioStatus" />
<html:hidden name="zsk110knForm" property="uioStatusBiko" />
<html:hidden name="zsk110knForm" property="sortKey" />
<html:hidden name="zsk110knForm" property="orderKey" />
<html:hidden name="zsk110knForm" property="zsk110UpdateKbn" />
<html:hidden name="zsk110knForm" property="zsk110StartTime" />
<html:hidden name="zsk110knForm" property="zsk110Status" />
<html:hidden name="zsk110knForm" property="zsk110Msg" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="cmn.preferences.kn" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('zsk110knOk');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk110knBack');">
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
       <logic:equal name="zsk110knForm" property="zsk110UpdateKbn" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.FIXED_UPDATE_ON) %>">
       <gsmsg:write key="cmn.setting.do" />
       </logic:equal>
       <logic:equal name="zsk110knForm" property="zsk110UpdateKbn" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.FIXED_UPDATE_OFF) %>">
       <gsmsg:write key="cmn.noset" />
       </logic:equal>
      </td>
    </tr>
    <logic:equal name="zsk110knForm" property="zsk110UpdateKbn" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.FIXED_UPDATE_ON) %>">
    <bean:define id="zskSTime" name="zsk110knForm" property="zsk110StartTime" type="java.lang.Integer" />
    <tr>
      <th>
        <gsmsg:write key="cmn.starttime" />
      </th>
      <td>
        <gsmsg:write key="cmn.hour.only" arg0="<%= String.valueOf(zskSTime.intValue()) %>"/>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="zsk.20" />
      </th>
      <td>

      <logic:equal name="zsk110knForm" property="zsk110Status" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
      <span class="zsk_label-zaiseki status_frame-base borC_light"><gsmsg:write key="cmn.zaiseki" /></span>
      </logic:equal>
      <logic:equal name="zsk110knForm" property="zsk110Status" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
      <span class="zsk_label-huzai status_frame-base borC_light"><gsmsg:write key="cmn.absence" /></span>
      </logic:equal>
      <logic:equal name="zsk110knForm" property="zsk110Status" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
      <span class="zsk_label-sonota status_frame-base borC_light"><gsmsg:write key="cmn.other" /></span>
      </logic:equal>

      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="zsk.23" />
      </th>
      <td>
        <bean:write name="zsk110knForm" property="zsk110Msg" />
      </td>
    </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('zsk110knOk');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk110knBack');">
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