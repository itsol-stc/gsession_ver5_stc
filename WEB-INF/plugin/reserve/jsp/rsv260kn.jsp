<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [<gsmsg:write key="reserve.rsv260kn.1" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv260kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv260kn">
<input type="hidden" name="CMD" value="">

<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvBackToAdminSetting" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv050SortRadio" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv260knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv260knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv260knForm" property="saveUser" scope="request">
  <logic:iterate id="usrSid" name="rsv260knForm" property="saveUser" scope="request">
    <input type="hidden" name="saveUser" value="<bean:write name="usrSid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv260knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv260knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsv260SelectedSisetuKbn" />
<html:hidden property="rsv260SelectedGrpComboSid" />
<html:hidden property="rsv260GrpAdmKbn" />
<html:hidden property="rsv260updateFlg" />
<html:hidden property="rsv260createGrpFlg" />

<input type="hidden" name="helpPrm" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
      <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
    </li>
    <li><gsmsg:write key="cmn.reserve" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="reserve.rsv260kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_group_input');">
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
<div class="txt_l">
  <span class="cl_fontWarn"><gsmsg:write key="cmn.capture.file.sure" />
</div>

<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="reserve.47" />
      </th>
      <td class="w75">
        <bean:write name="rsv260knForm" property="rsv260knSelectedSisetuName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.110" />
      </th>
      <td>
        <a href="../reserve/rsv260kn.do?CMD=downLoad">
         <bean:write name="rsv260knForm" property="rsv260knFileName" />
       </a>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.group.admin" />
      </th>
      <td>
        <logic:equal name="rsv260knForm" property="rsv260GrpAdmKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_ADM_KBN_OK)%>"><gsmsg:write key="reserve.50" /></logic:equal>
        <logic:equal name="rsv260knForm" property="rsv260GrpAdmKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_ADM_KBN_NO)%>"><gsmsg:write key="reserve.51" /></logic:equal>
        <logic:notEmpty name="rsv260knForm" property="rsv260knKanriUser" scope="request">
        <br>
        <logic:iterate id="usr" name="rsv260knForm" property="rsv260knKanriUser" scope="request" indexId="idx" type="UsrLabelValueBean">
          <span class="<%=usr.getCSSClassNameNormal()%> ml10"><bean:write name="usr" property="label" /><br></span>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.overwrite" />
      </th>
      <td>
        <logic:equal name="rsv260knForm" property="rsv260updateFlg" value="1"><gsmsg:write key="reserve.125" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.126" />
      </th>
      <td>
        <logic:equal name="rsv260knForm" property="rsv260createGrpFlg" value="1"><gsmsg:write key="reserve.127" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <bean:write name="rsv260knForm" property="impDataCnt" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImport');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_group_input');">
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