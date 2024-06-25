<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.132" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../file/js/fil310.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/file/fil310">
<input type="hidden" name="CMD" value="">
<html:hidden property="backDsp" />
<html:hidden property="backScreen" />
<html:hidden property="filSearchWd" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="fil040SelectDel" />
<html:hidden property="fil010SelectDelLink" />
<html:hidden name="fil310Form" property="filDelGaikaId" />

<logic:iterate id="sort" name="fil310Form" property="gaikaList" scope="request">
  <input type="hidden" name="fil310SortGaika" value="<bean:write name="sort" property="gaikaValue" />">
</logic:iterate>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.132" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('fil310add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="deleteGaika();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil310back');">
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('fil310Up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('fil310Down');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>

  <table class="table-top mt0">
    <tr>
      <th class="w0 no_w"></th>
      <th class="w100 no_w"><gsmsg:write key="fil.fil310.1" /></th>
    </tr>

  <logic:iterate id="gaikaData" name="fil310Form" property="gaikaList" indexId="idx">
    <bean:define id="gaikaValue" name="gaikaData" property="gaikaValue" />
    <% String gaikaCheckId = "chkGaika" + String.valueOf(idx.intValue()); %>

    <tr class="js_gaika cursor_p">
      <td class="txt_c js_tableTopClick cursor_p no_w">
        <html:radio property="fil310SortRadio" value="<%= String.valueOf(gaikaValue) %>" styleId="<%= gaikaCheckId %>" />
        <html:hidden name="gaikaData" property="fmmSid" />
      </td>
      <td>
        <bean:write name="gaikaData" property="fmmName" />
      </td>
    </tr>
  </logic:iterate>
  </table>

</div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>