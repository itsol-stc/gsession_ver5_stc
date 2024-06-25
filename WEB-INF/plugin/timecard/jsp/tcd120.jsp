<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-timeZoneChart.tld" prefix="timeZoneChart" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd120.js?<%= GSConst.VERSION_PARAM %>1"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.tcd030.07" /></title>
</head>

<body>
<html:form action="/timecard/tcd120">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<logic:notEmpty name="tcd120Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd120Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="timezoneCmdMode" />
<html:hidden property="timezoneSid" />



<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd030.07" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn ml8" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="tcd120Data();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd120_back');">
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upTcdzoneData');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downTcdzoneData');">
      <gsmsg:write key="cmn.down" />
    </button>
    <span class="fw_b ml10"></span>
  </div>


  <table class="table-top mt0">
    <tr>
      <th class="w5 no_w">&nbsp;</th>
      <th class="w60 no_w"><gsmsg:write key="tcd.tcd120.01" /></th>
      <th class="w25 no_w"><gsmsg:write key="tcd.tcd120.02" /></th>
      <th class="w10 no_w"><gsmsg:write key="tcd.tcd120.03" /></th>
    </tr>
    <logic:notEmpty name="tcd120Form" property="tcd120TimezoneList" scope="request">
      <logic:iterate id="tcdzoneData" name="tcd120Form" property="tcd120TimezoneList" indexId="idx">
        <bean:define id="tcdzoneValue" name="tcdzoneData" property="ttiSid" />
        <% String tcdzoneCheckId = "chktcdzone" + String.valueOf(idx.intValue()); %>
        <tr class="js_listHover cursor_p" id="<%= String.valueOf(tcdzoneValue) %>">
          <td class="txt_c js_tableTopCheck cursor_p no_w js_sort_radio">
            <html:radio property="tcd120SortRadio" value="<%= String.valueOf(tcdzoneValue) %>" styleId="<%= tcdzoneCheckId %>" />
          </td>
          <td class="js_listClick cursor_p">
            <bean:write name="tcdzoneData" property="ttiName" />
          </td>
          <td class="js_listClick cursor_p">
            <bean:write name="tcdzoneData" property="ttiRyaku" />
          </td>
          <td class="txt_c js_listClick cursor_p">
            <logic:equal name="tcdzoneData" property="ttiUse" value="1">
              <gsmsg:write key="tcd.199" />
            </logic:equal>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>

  </table>

</div>

<div class="footerBtn_block txt_r w80 mrl_auto">
  <button type="button" class="baseBtn ml8" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('delete');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
    <gsmsg:write key="cmn.delete" />
  </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="tcd120Data();">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
    <gsmsg:write key="cmn.add" />
  </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd120_back');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <gsmsg:write key="cmn.back" />
  </button>
</div>


</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>