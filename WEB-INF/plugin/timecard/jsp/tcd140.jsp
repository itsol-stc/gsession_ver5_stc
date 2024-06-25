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
  <script src="../timecard/js/tcd140.js?<%= GSConst.VERSION_PARAM %>"></script>


  <title>GROUPSESSION <gsmsg:write key="tcd.tcd140.05" /></title>
</head>

<body>
<html:form action="/timecard/tcd140">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<html:hidden property="tcd130SearchFlg" />
<html:hidden property="tcd130GroupSid" />
<html:hidden property="tcd130UserSid" />
<html:hidden property="tcd130TimezoneSid" />
<html:hidden property="tcd130DefaultTimezoneSid" />
<html:hidden property="tcd130SvGroupSid" />
<html:hidden property="tcd130SvUserSid" />
<html:hidden property="tcd130SvTimezoneSid" />
<html:hidden property="tcd130SvDefaultTimezoneSid" />
<html:hidden property="tcd140initFlg" />
<html:hidden property="timezoneSid" />


<div class="js_user_list">
  <logic:notEmpty name="tcd140Form" property="tcdSelectUserlist">
    <logic:iterate id="chks" name="tcd140Form" property="tcdSelectUserlist" scope="request">
      <bean:define id="chkSid" name="chks" type="java.lang.String" />
      <html:hidden property="tcdSelectUserlist" value="<%= chkSid %>" />
    </logic:iterate>
  </logic:notEmpty>
  <html:hidden property="tcdSelectedUser" />
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd140.05" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backTimeZoneInfList()">
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

  <table class="table-left">
    <!-- ユーザ -->
    <tr>
      <th>
        <gsmsg:write key="cmn.user.name" />
      </th>
      <td width="80%" class="td_sub_detail" colspan="3">
        <logic:notEmpty name="tcd140Form" property="tcd140SelectedUser" >
         <logic:iterate id="user" name="tcd140Form" property="tcd140SelectedUser" scope="request" indexId="cnt">
         <bean:define id="mukouserClass" value="" />
         <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukouserClass" value="mukoUser" /></logic:equal>
          <span class="<%=mukouserClass%>"><%if(cnt != 0){ %>, <%} %><bean:write name="user" property="usiSei" /> <bean:write name="user" property="usiMei" /></span>
        </logic:iterate>
      </logic:notEmpty>
      </td>
    </tr>
    <!-- 時間帯設定 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd140.02" />
      </th>
      <td>
        <logic:notEmpty name="tcd140Form" property="tcd140TimeZoneCheck" >
          <logic:iterate id="data" name="tcd140Form" property="tcd140TimeZoneCheck"  indexId="idx">
            <bean:define id="mukoClass" value="" />
            <logic:equal name="data" property="ttiUse" value="0">
              <bean:define id="mukoClass" value="mukoUser" />
            </logic:equal>
            <div>
              <label>
                <html:multibox name="tcd140Form" property="tcd140SelectedTimeZone" onchange="changeDefault()">
                  <bean:write name="data" property="ttiSid" />
                </html:multibox>
                <span class="<%= mukoClass %>">
                  <bean:write name="data" property="ttiName" />
                </span>
              </label>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <!-- デフォルト時間帯設定 -->
    <tr>
      <th>
        <gsmsg:write key="tcd.tcd140.04" />
      </th>
      <td>
        <logic:notEmpty name="tcd140Form" property="tcd140TimeZoneDefaultList" >
          <html:select name ="tcd140Form" property="tcd140DefaultTimeZone"  styleId="tcd140DefaultTimeZone" styleClass="select01" >
            <logic:iterate id="data" name="tcd140Form" property="tcd140TimeZoneDefaultList"  >
              <bean:define id="sidValue" name="data" property="ttiSid" />
              <html:option value="<%=String.valueOf(sidValue) %>"><bean:write name="data" property="ttiName" /></html:option>
            </logic:iterate>
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
</div>

<div class="footerBtn_block txt_r w80 mrl_auto">
   <button type="button" class="baseBtn js_btn_ok1" value="OK" onClick="buttonPush('ok');">
     <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
     <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
     <gsmsg:write key="cmn.ok" />
   </button>
   <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backTimeZoneInfList()">
     <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
     <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
     <gsmsg:write key="cmn.back" />
   </button>
</div>


</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>