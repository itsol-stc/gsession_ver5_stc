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
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.108" /> [<gsmsg:write key="schedule.1" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../schedule/js/sch110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body>
<html:form action="/schedule/sch110kn">

<input type="hidden" name="CMD" value="">

<html:hidden property="sch110SltGroup" />
<html:hidden property="sch110SltUser" />

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
<logic:notEmpty name="sch110knForm" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch110knForm" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch110knForm" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch110knForm" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch110knForm" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch110knForm" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch110knForm" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch110knForm" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch110knForm" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch110knForm" property="sch100CsvOutField" scope="request">
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
     <gsmsg:write key="cmn.import" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.run" />" onClick="buttonPush('doImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
        <gsmsg:write key="cmn.run" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_import_input');">
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
  <div class="txt_l fw_b">
    <span class="cl_fontWarn"><gsmsg:write key="cmn.capture.file.sure" /></span>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w25 txt_l">
        <gsmsg:write key="cmn.registerd" />
      </th>
      <td class="w75">
        <bean:write name="sch110knForm" property="impTargetName" />
      </td>
    </tr>
    <tr>
      <th class="w25 txt_l">
        <gsmsg:write key="cmn.capture.file.name" />
      </th>
      <td class="w75">
        <a href="../schedule/sch110kn.do?CMD=downLoad">
          <bean:write name="sch110knForm" property="sch110knFileName" />
        </a>
      </td>
    </tr>
    <tr>
      <th class="w20 txt_l">
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td class="w80">
        <bean:write name="sch110knForm" property="impDataCnt" /><gsmsg:write key="cmn.number" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.run" />" onClick="buttonPush('doImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.run" />">
        <gsmsg:write key="cmn.run" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_import_input');">
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