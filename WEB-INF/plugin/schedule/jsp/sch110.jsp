<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstSchedule" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.108" /> [<gsmsg:write key="schedule.31" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body onunload="windowClose();">
<html:form action="/schedule/sch110">

<input type="hidden" name="CMD" value="">
<html:hidden property="dspMod" />
<html:hidden property="listMod" />
<html:hidden property="sch010DspDate" />
<html:hidden property="changeDateFlg" />
<html:hidden property="iniDsp" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="sch010SelectUsrKbn" />
<html:hidden property="sch010SelectDate" />
<html:hidden property="sch010SchSid" />
<html:hidden property="sch010DspGpSid" />
<html:hidden property="sch010searchWord" />
<html:hidden property="sch020SelectUsrSid" />
<html:hidden property="sch030FromHour" />

<html:hidden property="sch100PageNum" />
<html:hidden property="sch100Slt_page1" />
<html:hidden property="sch100Slt_page2" />
<html:hidden property="sch100OrderKey1" />
<html:hidden property="sch100SortKey1" />
<html:hidden property="sch100OrderKey2" />
<html:hidden property="sch100SortKey2" />

<html:hidden property="sch100SvSltGroup" />
<html:hidden property="sch100SvSltUser" />
<html:hidden property="sch100SvSltStartYearFr" />
<html:hidden property="sch100SvSltStartMonthFr" />
<html:hidden property="sch100SvSltStartDayFr" />
<html:hidden property="sch100SvSltStartYearTo" />
<html:hidden property="sch100SvSltStartMonthTo" />
<html:hidden property="sch100SvSltStartDayTo" />
<html:hidden property="sch100SvSltEndYearFr" />
<html:hidden property="sch100SvSltEndMonthFr" />
<html:hidden property="sch100SvSltEndDayFr" />
<html:hidden property="sch100SvSltEndYearTo" />
<html:hidden property="sch100SvSltEndMonthTo" />
<html:hidden property="sch100SvSltEndDayTo" />
<html:hidden property="sch100SvKeyWordkbn" />
<html:hidden property="sch100SvKeyValue" />
<html:hidden property="sch100SvOrderKey1" />
<html:hidden property="sch100SvSortKey1" />
<html:hidden property="sch100SvOrderKey2" />
<html:hidden property="sch100SvSortKey2" />

<html:hidden property="sch100SltGroup" />
<html:hidden property="sch100SltUser" />
<html:hidden property="sch100SltStartYearFr" />
<html:hidden property="sch100SltStartMonthFr" />
<html:hidden property="sch100SltStartDayFr" />
<html:hidden property="sch100SltStartYearTo" />
<html:hidden property="sch100SltStartMonthTo" />
<html:hidden property="sch100SltStartDayTo" />
<html:hidden property="sch100SltEndYearFr" />
<html:hidden property="sch100SltEndMonthFr" />
<html:hidden property="sch100SltEndDayFr" />
<html:hidden property="sch100SltEndYearTo" />
<html:hidden property="sch100SltEndMonthTo" />
<html:hidden property="sch100SltEndDayTo" />
<html:hidden property="sch100KeyWordkbn" />
<logic:notEmpty name="sch110Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch110Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch110Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch110Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch110Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch110Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch110Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch110Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch110Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch110Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../schedule/images/classic/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
      <img class="header_pluginImg" src="../schedule/images/original/header_schedule.png" alt="<gsmsg:write key="schedule.108" />">
    </li>
    <li>
      <gsmsg:write key="schedule.108" />
    </li>
    <li class="pageTitle_subFont">
     <gsmsg:write key="cmn.import" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('110_import');">
        <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />" class="btn_classicImg-display">
        <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />" class="btn_originalImg-display">
        <gsmsg:write key="cmn.import" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left w100">
    <tr>
      <th class="w25 txt_l">
        <gsmsg:write key="cmn.registerd" />
      </th>
      <td class="w75">
        <html:select property="sch110SltGroup" styleClass="wp200" onchange="changeGroupCombo();">
          <logic:notEmpty name="sch110Form" property="sch100GroupLabel" scope="request">
            <logic:iterate id="gpBean" name="sch110Form" property="sch100GroupLabel" scope="request">
              <% boolean gpDisabled = false; %>
              <logic:equal name="gpBean" property="viewKbn" value="false">
                <% gpDisabled = true; %>
              </logic:equal>
              <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
              <logic:equal name="gpBean" property="styleClass" value="0">
                <html:option value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
              </logic:equal>
              <logic:notEqual name="gpBean" property="styleClass" value="0">
                <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>" disabled="<%= gpDisabled %>"><bean:write name="gpBean" property="label" /></html:option>
              </logic:notEqual>
            </logic:iterate>
          </logic:notEmpty>
        </html:select>
        <button class="iconBtn-border " type="button" id="sch100GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.sch110SltGroup, 'sch110SltGroup', '1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>
        <html:select property="sch110SltUser" styleClass="wp200">
          <logic:notEmpty name="sch110Form" property="sch100UserLabel">
            <logic:iterate id="accountMdl" name="sch110Form" property="sch100UserLabel">
              <bean:define id="accoutVal" name="accountMdl" property="value" />
              <bean:define id="optClass" value="" />
              <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
                <bean:define id="optClass" value="mukoUserOption" />
              </logic:equal>
              <option value="<%= String.valueOf(accoutVal) %>" class="<bean:write name="optClass" />"  <logic:equal name="sch110Form" property="sch110SltUser" value="<%= String.valueOf(accoutVal) %>">selected</logic:equal>><bean:write name="accountMdl" property="label" /></option>
            </logic:iterate>
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25 txt_l">
        <gsmsg:write key="cmn.capture.file" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <span class="fs_13">
          <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
          <% String csvFileMsg = "<a href=\"../schedule/sch110.do?CMD=sch110_sample\">" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>"; %>
          *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg %>" />
        </span>
        <attachmentFile:filearea
        mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
        pluginId="<%= GSConstSchedule.PLUGIN_ID_SCHEDULE %>"
        tempDirId="sch110" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('110_import');">
      <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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