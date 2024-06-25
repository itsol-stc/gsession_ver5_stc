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

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd130.js?<%= GSConst.VERSION_PARAM %>"></script>


  <title>GROUPSESSION <gsmsg:write key="tcd.tcd130.01" /></title>
</head>

<body>
<html:form action="/timecard/tcd130">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />

<logic:notEmpty name="tcd130Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd130Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="tcd130SearchFlg" />
<html:hidden property="tcd130SvGroupSid" />
<html:hidden property="tcd130SvUserSid" />
<html:hidden property="tcd130SvTimezoneSid" />
<html:hidden property="tcd130SvDefaultTimezoneSid" />
<html:hidden property="tcdSelectedUser" />
<html:hidden property="timezoneSid" />


<logic:notEmpty name="tcd130Form" property="tcdSvSelectUserlist">
  <logic:iterate id="chks" name="tcd130Form" property="tcdSvSelectUserlist" scope="request">
    <bean:define id="chkSid" name="chks" type="java.lang.String" />
    <html:hidden property="tcdSelectUserlist" value="<%= chkSid %>" />
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd130.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="tcd.186" />" onClick="multiEditTimeZone()">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="tcd.186" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="tcd.186" />">
          <gsmsg:write key="tcd.186" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd130back');">
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
    <tr>
      <th>
        <gsmsg:write key="tcd.189" />
      </th>
      <td width="80%" class="td_sub_detail" colspan="3">
        <html:select property="tcd130GroupSid" styleClass="select01" styleId="tcd130GroupSid" onchange="changeGroupCombo();">
          <html:optionsCollection name="tcd130Form" property="tcd130GroupList" value="value" label="label" />
        </html:select>
        <button class="iconBtn-border mr10" type="button"  onclick="openGroupWindow(this.form.tcd130GroupSid, 'tcd130GroupSid', '0')" value="&nbsp;&nbsp;" id="tcd010GroupBtn" >
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>
        <html:select property="tcd130UserSid"  styleId="tcd130UserSid" styleClass="select01">
        <logic:iterate id="user" name="tcd130Form" property="tcd130UserList"  >
           <bean:define id="userValue" name="user" property="value" />
           <bean:define id="mukouserClass" value="" />
           <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukouserClass" value="mukoUser" /></logic:equal>
           <html:option styleClass="<%=mukouserClass %>" value="<%=String.valueOf(userValue) %>"><bean:write name="user" property="label" /></html:option>
       </logic:iterate>
       </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="tcd.184" />
      </th>
      <td>
        <html:select property="tcd130TimezoneSid" styleClass="wp200">
          <html:optionsCollection name="tcd130Form" property="tcd130TimezoneNameList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="tcd.187" />
      </th>
      <td>
        <html:select property="tcd130DefaultTimezoneSid" styleClass="wp200">
          <html:optionsCollection name="tcd130Form" property="tcd130TimezoneNameList" value="value" label="label" />
        </html:select>

      </td>
    </tr>
  </table>

  <div class="txt_c mb20">
    <button type="button" class="baseBtn" id="head_menu_search_btn2" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('search');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>

  <logic:notEmpty name="tcd130Form" property="tcd130PageList">
    <div class="paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  property="tcd130pageTop" onchange="changePage(0);">
        <html:optionsCollection name="tcd130Form" property="tcd130PageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>

  <table class="table-top mt0">
    <tr>
      <th class="w5 no_w js_tableTopCheck js_tableTopCheck-header"><input type="checkbox" name="allChk" onclick="changeChk();"/></th>
      <th class="w25 no_w"><gsmsg:write key="cmn.user.name" /></th>
      <th class="w35 no_w"><gsmsg:write key="tcd.190" /></th>
      <th class="w35 no_w"><gsmsg:write key="tcd.187" /></th>
    </tr>
    <logic:notEmpty name="tcd130Form" property="tcd130SearchList">

      <logic:iterate id="searchData" name="tcd130Form" property="tcd130SearchList" indexId="idx" scope="request">
        <tr class="js_listHover cursor_p" id="<bean:write name="searchData" property="usrSid" />">
          <td class="txt_c txt_c js_tableTopCheck cursor_p">
             <html:multibox name="tcd130Form" property="tcdSelectUserlist">
               <bean:write name="searchData" property="usrSid" />
             </html:multibox>
          </td>
          <td class="txt_l js_listClick">
            <bean:define id="mukouserClass" value="" />
            <logic:equal name="searchData" property="userUkoFLg" value="1">
              <bean:define id="mukouserClass" value="mukoUser" />
            </logic:equal>
            <span class="<%= mukouserClass %> text_link"><bean:write name="searchData" property="userName" /></span>
          </td>
          <td class="txt_l js_listClick">
            <logic:notEmpty name="searchData" property="timeZoneNames">
              <logic:iterate id="timeZoneData" name="searchData" property="timeZoneNames" indexId="idy" >
                <bean:define id="mukoTcd" value="" />
                <logic:equal name="timeZoneData" property="timeZoneUkoFlg" value="0">
                  <bean:define id="mukoTcd" value="mukoUser" />
                </logic:equal>
                <div class="<%= mukoTcd %>"><bean:write name="timeZoneData" property="timeZoneName" /></div>
              </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="searchData" property="timeZoneNames">
              <bean:write name="searchData" property="timeZoneName" />
            </logic:empty>
          </td>
          <td class="js_listClick">
            <bean:write name="searchData" property="defaultTimeZoneName" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>

  <logic:notEmpty name="tcd130Form" property="tcd130PageList">
    <div class="paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  property="tcd130pageBottom" onchange="changePage(1);">
        <html:optionsCollection name="tcd130Form" property="tcd130PageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>

</div>

<div class="footerBtn_block txt_r w80 mrl_auto">
  <button type="button" class="baseBtn" value="<gsmsg:write key="tcd.186" />" onClick="multiEditTimeZone()">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="tcd.186" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="tcd.186" />">
    <gsmsg:write key="tcd.186" />
  </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd130back');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <gsmsg:write key="cmn.back" />
  </button>
</div>


</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>