<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" />[<gsmsg:write key="cmn.reserve" /> <gsmsg:write key="cmn.display.settings.kn" />]</title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../reserve/js/rsv150kn.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>
<html:form action="/reserve/rsv150kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv150SelectedGrpSid" />
<html:hidden property="rsv150initDspFlg" />
<html:hidden property="rsv150DispItem1" />
<html:hidden property="rsv150DispItem2" />
<html:hidden property="rsv150ReloadTime" />
<html:hidden property="rsv150ImgDspKbn" />

<html:hidden property="rsv150DateKbn" />
<html:hidden property="rsv150SelectedFromSid" />
<html:hidden property="rsv150SelectedToSid" />
<html:hidden property="rsv150ViewCnt" />

<html:hidden property="rsv150DefDsp" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv150knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv150knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv150knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv150knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.display.settings.kn" />
    </li>
    <li>
      <div>
       <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('hyozi_settei_kakutei');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
         <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_hyozi_settei_input');">
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
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.initial.display" />
      </th>
      <td class="w75">
        <bean:write name="rsv150knForm" property="rsv150knDefDsp"/>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.99" />
      </th>
      <td>
        <bean:write name="rsv150knForm" property="rsv150knDispGroup"/>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.100" />
      </th>
      <td>
        <gsmsg:write key="reserve.rsv150kn.2" />
        <logic:notEmpty name="rsv150knForm" property="rsv150knDispItem1">
          <div class="ml10"><bean:write name="rsv150knForm" property="rsv150knDispItem1"/></div>
        </logic:notEmpty>
        <logic:notEmpty name="rsv150knForm" property="rsv150knDispItem2">
          <div class="ml10"><bean:write name="rsv150knForm" property="rsv150knDispItem2"/></div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.auto.reload.time" />
      </th>
      <td>
        <bean:write name="rsv150knForm" property="rsv150knReloadTime"/>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.102" />
      </th>
      <td>
      <logic:equal name="rsv150knForm" property="rsv150ImgDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" ><gsmsg:write key="cmn.display.ok" /></logic:equal>
      <logic:equal name="rsv150knForm" property="rsv150ImgDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" ><gsmsg:write key="cmn.dont.show" /></logic:equal>
      </td>
    </tr>
    <logic:equal name="rsv150knForm" property="rsv150DateKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.AUTH_ALL_USER)%>">
    <tr>
      <th>
        <gsmsg:write key="cmn.show.timezone.days.setting" />
      </th>
      <td>
        <gsmsg:write key="cmn.starttime" />：<bean:write name="rsv150knForm" property="rsv150SelectedFromSid"/><gsmsg:write key="cmn.hour" /><br>
        <gsmsg:write key="cmn.endtime" />：<bean:write name="rsv150knForm" property="rsv150SelectedToSid"/><gsmsg:write key="cmn.hour" /><br>
      </td>
    </tr>
    </logic:equal>
    <tr>
      <th>
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td>
        <bean:write name="rsv150knForm" property="rsv150ViewCnt"/><gsmsg:write key="cmn.number" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('hyozi_settei_kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_hyozi_settei_input');">
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