<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<% int tCmdAdd = jp.groupsession.v2.man.GSConstPortal.CMD_MODE_ADD; %>
<% int tCmdEdit = jp.groupsession.v2.man.GSConstPortal.CMD_MODE_EDIT; %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../portal/js/ptl090.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="ptl.9" /></title>
</head>

<body>
<html:form action="portal/ptl090">
<input type="hidden" name="CMD" value="">
<html:hidden property="ptl090svCategory" />
<html:hidden property="ptlPortletSid" />
<html:hidden property="ptlCmdMode" />

<logic:notEmpty name="ptl090Form" property="ptl090portletlist" scope="request">
  <logic:iterate id="sort" name="ptl090Form" property="ptl090portletlist" scope="request">
    <input type="hidden" name="arrayPtl090sortPortlet" value="<bean:write name="sort" property="strPltSort" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- body -->
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
      <gsmsg:write key="ptl.9" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="ptl.2" />" class="baseBtn" onClick="buttonPush('portalManager');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_portal.png" alt="<gsmsg:write key="ptl.2" />">
          <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.2" />">
          <gsmsg:write key="ptl.2" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="editPortlet('ptl090addPortlet', -1, '<%= tCmdAdd %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('confMenu');">
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
    <gsmsg:write key="ptl.ptl090.1" />
  </div>

  <table class="table-left">
    <tr>
      <th class="w15 txt_c">
        <gsmsg:write key="cmn.category.select" />
      </th>
      <td>
        <html:select property="ptl090category" onchange="buttonPush('init');">
          <html:optionsCollection property="ptl090CategoryList" value="value" label="label" />
        </html:select>
      </td>
      <td class="wp150 txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.categorylist" />" onclick="buttonPush('ptl090categoryList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_list.png" alt="<gsmsg:write key="cmn.categorylist" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.categorylist" />">
          <gsmsg:write key="cmn.categorylist" />
        </button>
      </td>
      <td class="wp150 txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng060.02" />" onclick="addCategory('ptl090addCategory', '0');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add_folder.png" alt="<gsmsg:write key="rng.rng060.02" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add_folder.png" alt="<gsmsg:write key="rng.rng060.02" />">
          <gsmsg:write key="rng.rng060.02" />
        </button>
      </td>
    </tr>
  </table>
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('sortUp');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('sortDown');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="w5"></th>
      <th class="w35">
        <gsmsg:write key="cmn.category.belong" />
      </th>
      <th class="w60">
        <gsmsg:write key="ptl.3" />
      </th>
    </tr>
    <logic:iterate id="portletMdl" name="ptl090Form" property="ptl090portletlist" indexId="idx">
      <bean:define id="pltSid" name="portletMdl" property="pltSid" type="java.lang.Integer" />
      <tr class="js_listHover" data-sid="<bean:write name="portletMdl" property="pltSid" />" data-edit="<%= tCmdEdit %>">
        <td class="w5 txt_c js_sord_radio cursor_p">
          <html:radio property="ptl090sortPortlet" value="<%= String.valueOf(pltSid) %>" />
        </td>
        <td class="txt_l js_listClick cursor_p">
          <bean:write name="portletMdl" property="plcName" />
        </td>
        <td class="txt_l js_listClick cursor_p cl_linkDef">
          <bean:write name="portletMdl" property="pltName" />
        </td>
      </tr>
    </logic:iterate>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="ptl.2" />" class="baseBtn" onClick="buttonPush('portalManager');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_portal.png" alt="<gsmsg:write key="ptl.2" />">
      <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.2" />">
      <gsmsg:write key="ptl.2" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="editPortlet('ptl090addPortlet', -1, '<%= tCmdAdd %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('confMenu');">
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