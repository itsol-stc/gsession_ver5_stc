<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.tcd.model.TcdTotalValueModel" %>
<%@ page import="jp.groupsession.v2.tcd.model.TcdHolidayInfModel" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstTimecard" %>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd200.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.52" /></title>

</head>

<body>
<html:form action="/timecard/tcd200">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="tcdBackScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<input type="hidden" name="tcd190nendo" value="<bean:write name="tcd200Form" property="tcd190nendo" />">
<input type="hidden" name="tcd190group" value="<bean:write name="tcd200Form" property="tcd190group" />">
<input type="hidden" name="tcd190sortKey" value="<bean:write name="tcd200Form" property="tcd190sortKey" />">
<input type="hidden" name="tcd190order" value="<bean:write name="tcd200Form" property="tcd190order" />">
<input type="hidden" name="tcd190page" value="<bean:write name="tcd200Form" property="tcd190page" />">
<input type="hidden" name="tcd200initFlg" value="<bean:write name="tcd200Form" property="tcd200initFlg" />">
<html:hidden property="tcdDspFrom" />

<logic:equal name="tcd200Form" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_INSERT) %>" >
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="tcd200Form" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_INSERT) %>" >
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto w80">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><logic:equal name="tcd200Form" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_INSERT) %>" ><gsmsg:write key="tcd.tcd200.01" /></logic:equal><logic:equal name="tcd200Form" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_UPDATE) %>"><gsmsg:write key="tcd.tcd200.02" /></logic:equal></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('tcd200confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:notEqual name="tcd200Form" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_INSERT) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('tcd200delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd200back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto w80">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left mt20 w100">
    <tr>
      <th class="table-_title-color w25"><gsmsg:write key="cmn.user" /></th>
      <td class="w75">
        <html:select property="tcd200Group" onchange="changeGroupCombo();">
          <html:optionsCollection property="tcdGroupList" value="value" label="label" />
        </html:select>
        <html:select property="tcd200Name" onchange="changeCombo();">
          <logic:iterate id="user" name="tcd200Form" property="tcdUserList" type="UsrLabelValueBean">
            <html:option value="<%=user.getValue() %>" styleClass="<%=user.getCSSClassNameOption() %>"><bean:write name="user" property="label"/></html:option>
          </logic:iterate>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="table-_title-color w25"><gsmsg:write key="tcd.209" /></th>
      <td class="w75">
        <html:select property="tcd200Nendo" onchange="changeCombo();">
          <html:optionsCollection property="tcdNendoList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="table-_title-color w25"><gsmsg:write key="tcd.210" /></th>
      <td class="w75">
        <input type="text" name="tcd200YukyuDays" class="wp100" value="<bean:write name="tcd200Form" property="tcd200YukyuDays" />" maxlength="7">
        <gsmsg:write key="cmn.day"/>
      </td>
    </tr>
  </table>
  
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('tcd200confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:notEqual name="tcd200Form" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_INSERT) %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('tcd200delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:notEqual>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd200back');">
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
