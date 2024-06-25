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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="ptl.ptl050kn.1" /></title>
</head>

<!-- body -->
<body>

<html:form action="/portal/ptl050kn">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl030sortPortal" />

<html:hidden property="ptl050init" />
<html:hidden property="ptl050name" />
<html:hidden property="ptl050openKbn" />
<html:hidden property="ptl050description" />
<html:hidden property="ptl050accessKbn" />
<html:hidden property="ptl050accessKbnGroup" />

<logic:notEmpty name="ptl050knForm" property="ptl050memberSid">
<logic:iterate id="usid" name="ptl050knForm" property="ptl050memberSid">
  <input type="hidden" name="ptl050memberSid" value="<bean:write name="usid" />">
</logic:iterate>
</logic:notEmpty>
<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.ptl050kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush2('ptl050knOk');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ptl050knBack');">
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
  <table class="table-left w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="ptl.4" />
      </th>
      <td class="w80">
        <bean:write name="ptl050knForm" property="ptl050name" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.public.kbn" />
      </th>
      <td class="w80">
        <logic:equal name="ptl050knForm" property="ptl050openKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_OPENKBN_OK) %>">
          <gsmsg:write key="cmn.show" />
        </logic:equal>
        <logic:equal name="ptl050knForm" property="ptl050openKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_OPENKBN_NG) %>">
          <gsmsg:write key="cmn.hide" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w80">
        <bean:write name="ptl050knForm" property="ptl050knviewDescription" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.access.auth" />
      </th>
      <td class="w80">
        <logic:equal name="ptl050knForm" property="ptl050accessKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_ACCESS_OFF) %>">
          <gsmsg:write key="cmn.not.limit" />
        </logic:equal>
        <logic:equal name="ptl050knForm" property="ptl050accessKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_ACCESS_ON) %>">
          <gsmsg:write key="cmn.do.limit" />
        </logic:equal>
        <logic:equal name="ptl050knForm" property="ptl050accessKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_ACCESS_ON) %>">
          <div class="mt5 txt_l">
            <gsmsg:write key="cmn.reading" />
          </div>
          <div class="txt_l ml10">
            <logic:notEmpty name="ptl050knForm" property="ptl050knMemNameList">
              <logic:iterate id="memName" name="ptl050knForm" property="ptl050knMemNameList">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal value="1" name="memName" property="usrUkoFlg"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
                <span class="<%= mukoUserClass %>"><bean:write name="memName" property="label" /></span><br>
              </logic:iterate>
            </logic:notEmpty>
          </div>
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush2('ptl050knOk');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ptl050knBack');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>

</html:html>