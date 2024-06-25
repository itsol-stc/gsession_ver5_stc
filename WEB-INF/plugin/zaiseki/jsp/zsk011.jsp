<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%

    jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
    String sts_sonota      = String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC);
    String sts_zaiseki     = String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN);
    String sts_huzai       = String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE);
    String sts_sonota_str  = gsMsg.getMessage(request, "cmn.other");
    String sts_zaiseki_str = gsMsg.getMessage(request, "cmn.zaiseki");
    String sts_huzai_str   = gsMsg.getMessage(request, "cmn.absence");

%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="zsk.zsk011.03" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../zaiseki/js/zsk011.js?<%= GSConst.VERSION_PARAM %>"></script>

<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
</head>

<body class="ofx_h">
<html:form action="/zaiseki/zsk011">

<input type="hidden" name="CMD" value="ok">
<html:hidden property="closeFlg" />
<html:hidden property="uioUpdateUsrSid" />
<html:hidden property="uioUpdateStatus" />

<div class="pageTitle mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../zaiseki/images/classic/header_zaiseki.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
      <img class="header_pluginImg" src="../zaiseki/images/original/header_zaiseki.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
    </li>
    <li><gsmsg:write key="cmn.zaiseki.management" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <table class="table-left">
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.name2" />
      </th>
    <bean:define id="ukoFlg" name="zsk011Form" property="zsk011UpdateUsrUkoFlg" type="Integer"/>
      <td class="w70">
        <span class="<%=UserUtil.getCSSClassNameNormal(ukoFlg)%>"><bean:write name="zsk011Form" property="zsk011UpdateUserName" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="zsk.20" />
      </th>
      <td>
        <div class="verAlignMid">
        <html:radio property="zsk011Status" styleId="sts_zaiseki" value="<%= sts_zaiseki %>"/>
        <span class="zsk_label-zaiseki status_frame-base borC_light"><label for="sts_zaiseki"><%= sts_zaiseki_str %></label></span>
        <html:radio property="zsk011Status" styleId="sts_huzai" styleClass="ml5" value="<%= sts_huzai %>"/>
        <span class="zsk_label-huzai status_frame-base borC_light"><label for="sts_huzai"><%= sts_huzai_str %></label></span>
        <html:radio property="zsk011Status" styleId="sts_sonota" styleClass="ml5" value="<%= sts_sonota %>"/>
        <span class="zsk_label-sonota status_frame-base borC_light"><label for="sts_sonota"><%= sts_sonota_str %></label></span>
        </div>
      </td>
    </tr>
     <tr>
      <th>
        <gsmsg:write key="zsk.23" />
      </th>
      <td>
        <html:text maxlength="30" property="zsk011Comment" styleClass="wp250" />
      </td>
    </tr>
  </table>

  <div>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onClick="checkStatus();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.entry" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.entry" />">
      <gsmsg:write key="cmn.entry" />
    </button>
  </div>
</div>

</html:form>

</body>
</html:html>