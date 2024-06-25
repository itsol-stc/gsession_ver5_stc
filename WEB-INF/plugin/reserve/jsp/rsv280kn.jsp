<%@page import="jp.groupsession.v2.cmn.GSConstReserve"%>
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
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <gsmsg:write key="reserve.rsv280kn.1" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv280kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="rsv280PeriodKbn" />
<html:hidden property="rsv280DefFrH" />
<html:hidden property="rsv280DefFrM" />
<html:hidden property="rsv280DefToH" />
<html:hidden property="rsv280DefToM" />
<html:hidden property="rsv280EditKbn" />
<html:hidden property="rsv280Edit" />
<html:hidden property="rsv280PublicKbn" />
<html:hidden property="rsv280Public" />
<html:hidden property="rsv280initFlg" />
<logic:notEmpty name="rsv280knForm" property="rsv280PubUsrGrpSid">
  <logic:iterate id="usrGrp" name="rsv280knForm" property="rsv280PubUsrGrpSid">
    <input type="hidden" name="rsv280PubUsrGrpSid" value="<bean:write name="usrGrp" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv280knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv280knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv280knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv280knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.default.setting" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('rsv280knkakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv280knback');">
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
    <html:errors />
  </logic:messagesPresent>
  <table class="table-left">
<!-- 期間 -->
    <tr>
      <th class="w25" id="rsvPeriodArea1">
        <gsmsg:write key="cmn.period" />
      </th>
      <td class="w75">
        <logic:equal name="rsv280knForm" property="rsv280PeriodKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIPERIODKBN_ADM)%>"><gsmsg:write key="cmn.set.the.admin" /></logic:equal>
        <logic:equal name="rsv280knForm" property="rsv280PeriodKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIPERIODKBN_USER)%>"><gsmsg:write key="cmn.set.eachuser" /></logic:equal>
        <logic:equal name="rsv280knForm" property="rsv280PeriodKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIPERIODKBN_ADM)%>">
          <div class="settingForm_separator">
            <gsmsg:write key="cmn.starttime" />：<bean:write name="rsv280knForm" property="rsv280knDefFrHDsp" /><gsmsg:write key="cmn.hour.input" />
            <bean:write name="rsv280knForm" property="rsv280knDefFrMDsp" /><gsmsg:write key="cmn.minute.input" />
            <br>
            <gsmsg:write key="cmn.endtime" />：<bean:write name="rsv280knForm" property="rsv280knDefToHDsp" /><gsmsg:write key="cmn.hour.input" />
            <bean:write name="rsv280knForm" property="rsv280knDefToMDsp" /><gsmsg:write key="cmn.minute.input" />
          </div>
        </logic:equal>
      </td>
    </tr>
<!-- 編集権限 -->
    <tr>
      <th id="rsvEditArea1">
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td>
       <logic:equal name="rsv280knForm" property="rsv280EditKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIEDITKBN_ADM)%>"><span class="text_base6_2"><gsmsg:write key="cmn.set.the.admin" /></span></logic:equal>
       <logic:equal name="rsv280knForm" property="rsv280EditKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIEDITKBN_USER)%>"><span class="text_base6_2"><gsmsg:write key="cmn.set.eachuser" /></span></logic:equal>

        <logic:equal name="rsv280knForm" property="rsv280EditKbn" value="<%=String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.ADM_SORTKBN_ADM)%>">
        <div id="rsvEditArea2" class="settingForm_separator">
          <logic:equal name="rsv280knForm" property="rsv280Edit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_NONE)%>" ><gsmsg:write key="cmn.nolimit" /></logic:equal>
          <logic:equal name="rsv280knForm" property="rsv280Edit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>" ><gsmsg:write key="cmn.only.principal.or.registant" /></logic:equal>
          <logic:equal name="rsv280knForm" property="rsv280Edit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>" ><gsmsg:write key="cmn.only.affiliation.group.membership" /></logic:equal>
        </div>
        </logic:equal>
      </td>
    </tr>
      <tr>
      <th>
        <gsmsg:write key="cmn.public.kbn" />
      </th>
      <td>
        <logic:equal name="rsv280knForm" property="rsv280PublicKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIPUBLICKBN_ADM)%>"><span class="text_base6_2"><gsmsg:write key="cmn.set.the.admin" /></span></logic:equal>
        <logic:equal name="rsv280knForm" property="rsv280PublicKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RAC_INIPUBLICKBN_USER)%>"><span class="text_base6_2"><gsmsg:write key="cmn.set.eachuser" /></span></logic:equal>

        <logic:equal name="rsv280knForm" property="rsv280PublicKbn" value="<%=String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.ADM_SORTKBN_ADM)%>">
        <div id="rsvPublicArea2"">
          <div class="settingForm_separator">
            <logic:equal name="rsv280knForm" property="rsv280Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL)%>" ><gsmsg:write key="cmn.public" /></logic:equal>
            <logic:equal name="rsv280knForm" property="rsv280Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS)%>" ><gsmsg:write key="reserve.175" /></logic:equal>
            <logic:equal name="rsv280knForm" property="rsv280Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP)%>" ><gsmsg:write key="reserve.176" /></logic:equal>
            <logic:equal name="rsv280knForm" property="rsv280Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP)%>" ><gsmsg:write key="reserve.187" /></logic:equal>
            <logic:equal name="rsv280knForm" property="rsv280Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE)%>" ><gsmsg:write key="reserve.189" /></logic:equal>
          </div>
          <logic:equal name="rsv280knForm" property="rsv280Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP)%>" >
          <div class="settingForm_separator">
            <logic:notEmpty name="rsv280knForm" property="rsv280knPubUsrGrpList">
              <logic:iterate id="usrMdl" name="rsv280knForm" property="rsv280knPubUsrGrpList">
                <div><bean:write name="usrMdl" property="label" /><div>
              </logic:iterate>
            </logic:notEmpty>
          </div>
          </logic:equal>
        </div>
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('rsv280knkakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rsv280knback');">
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