<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../portal/js/ptl030.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="ptl.2" /></title>
</head>
<body>

<html:form action="/portal/ptl030">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--BODY -->
<div  class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.2" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="ptl.3" />" class="baseBtn" onClick="buttonPush2('portletManager');">
          <img class="btn_classicImg-display" src="../portal/images/classic/icon_portlet.png" alt="<gsmsg:write key="ptl.3" />">
          <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.3" />">
          <gsmsg:write key="ptl.3" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addPortal();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush2('confMenu');">
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
  <div class="txt_l">
    <gsmsg:write key="ptl.ptl030.1" />
  </div>
  <div class="txt_l mt5">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush2('ptl030up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush2('ptl030down');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="w5"></th>
      <th class="w35">
        <gsmsg:write key="ptl.4" />
      </th>
      <th class="w10">
        <gsmsg:write key="cmn.public" />
      </th>
      <th class="w50">
        <gsmsg:write key="cmn.memo" />
      </th>
    </tr>
    <logic:notEmpty name="ptl030Form" property="ptl030portalList">
      <logic:iterate id="ptlMdl" name="ptl030Form" property="ptl030portalList" indexId="idx">
        <% String trClass = ""; %>
        <% String tdClass = ""; %>
        <logic:notEqual name="ptlMdl" property="ptlSid" value="0">
          <% trClass = "js_listHover cursor_p"; %>
          <% tdClass = "js_listClick"; %>
        </logic:notEqual>
        <bean:define id="sortValue" name="ptlMdl" property="ptlSid" />
        <tr class="<%= trClass %>" id="<bean:write name="ptlMdl" property="ptlSid" />">
          <td class="w5 txt_c js_sord_radio cursor_p">
            <html:radio property="ptl030sortPortal" value="<%= String.valueOf(sortValue) %>" />
          </td>
          <logic:equal name="ptlMdl" property="ptlSid" value="0">
            <td class="<%= tdClass %>">
              <bean:write name="ptlMdl" property="ptlName" />
            </td>
          </logic:equal>
          <logic:notEqual name="ptlMdl" property="ptlSid" value="0">
            <td class="<%= tdClass %> cl_linkDef">
              <span class="cl_linkDef">
                <bean:write name="ptlMdl" property="ptlName" />
              </span>
            </td>
          </logic:notEqual>
          <td class="<%= tdClass %> txt_c">
            <logic:equal name="ptlMdl" property="ptlOpen" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_OPENKBN_OK) %>">
              <gsmsg:write key="cmn.show" />
            </logic:equal>
            <logic:equal name="ptlMdl" property="ptlOpen" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.PTL_OPENKBN_NG) %>">
              <gsmsg:write key="cmn.hide" />
            </logic:equal>
          </td>
          <td class="<%= tdClass %> txt_l">
            <bean:write name="ptlMdl" property="ptlDescriptionView" filter="false"/>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="ptl.3" />" class="baseBtn" onClick="buttonPush2('portletManager');">
      <img class="btn_classicImg-display" src="../portal/images/classic/icon_portlet.png" alt="<gsmsg:write key="ptl.3" />">
      <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.3" />">
      <gsmsg:write key="ptl.3" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addPortal();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush2('confMenu');">
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