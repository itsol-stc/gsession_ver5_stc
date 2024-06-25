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
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.rsv200kn.1" /> ]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv200kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/reserve/rsv200kn">
<input type="hidden" name="CMD" value="">

<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvBackToAdminSetting" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv050SortRadio" />
<html:hidden property="rsv080EditGrpSid" />
<html:hidden property="rsv080SortRadio" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv200knForm" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv200knForm" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="rsv200Prop1Check" />
<html:hidden property="rsv200Prop2Check" />
<html:hidden property="rsv200Prop3Check" />
<html:hidden property="rsv200Prop4Check" />
<html:hidden property="rsv200Prop5Check" />
<html:hidden property="rsv200Prop6Check" />
<html:hidden property="rsv200Prop7Check" />
<html:hidden property="rsv200BikoCheck" />
<html:hidden property="rsv200Prop1Value" />
<html:hidden property="rsv200Prop2Value" />
<html:hidden property="rsv200Prop3Value" />
<html:hidden property="rsv200Prop4Value" />
<html:hidden property="rsv200Prop5Value" />
<html:hidden property="rsv200Prop6Value" />
<html:hidden property="rsv200Prop7Value" />
<html:hidden property="rsv200Biko" />

<logic:notEmpty name="rsv200knForm" property="rsv200TargetSisetu" scope="request">
  <logic:iterate id="targetSisetu" name="rsv200knForm" property="rsv200TargetSisetu" scope="request">
    <input type="hidden" name="rsv200TargetSisetu" value="<bean:write name="targetSisetu"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv200knForm" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv200knForm" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
      <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
    </li>
    <li><gsmsg:write key="cmn.reserve" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="reserve.rsv200kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('ikkatu_settei_kakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_ikkatu_settei');">
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

<div class="txt_l fw_b fs_16">
  <gsmsg:write key="reserve.rsv200kn.2" />
</div>
  <table class="table-left mt0">
    <colgroup class="w20"></colgroup>
    <colgroup class="w80"></colgroup>
    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName4">
      <logic:equal name="rsv200knForm" property="rsv200Prop4Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName4" />
          </th>
          <td >
            <bean:write name="rsv200knForm" property="rsv200Prop4Value" />
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName5">
      <logic:equal name="rsv200knForm" property="rsv200Prop5Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName5" />
          </th>
          <td >
            <bean:write name="rsv200knForm" property="rsv200Prop5Value" />
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName1">
      <logic:equal name="rsv200knForm" property="rsv200Prop1Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName1" />
          </th>
          <td >
            <bean:write name="rsv200knForm" property="rsv200Prop1Value" />
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName2">
      <logic:equal name="rsv200knForm" property="rsv200Prop2Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName2" />
          </th>
          <td >
            <logic:equal name="rsv200knForm" property="rsv200Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>"><gsmsg:write key="cmn.accepted" /></logic:equal>
            <logic:equal name="rsv200knForm" property="rsv200Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>"><gsmsg:write key="cmn.not" /></logic:equal>
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName3">
      <logic:equal name="rsv200knForm" property="rsv200Prop3Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName3" />
          </th>
          <td >
            <logic:equal name="rsv200knForm" property="rsv200Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>"><gsmsg:write key="cmn.accepted" /></logic:equal>
            <logic:equal name="rsv200knForm" property="rsv200Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>"><gsmsg:write key="cmn.not" /></logic:equal>
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName7">
      <logic:equal name="rsv200knForm" property="rsv200Prop7Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName7" />
          </th>
          <td >
            <logic:equal name="rsv200knForm" property="rsv200Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>"><gsmsg:write key="cmn.accepted" /></logic:equal>
            <logic:equal name="rsv200knForm" property="rsv200Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>"><gsmsg:write key="cmn.not" /></logic:equal>
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:notEmpty name="rsv200knForm" property="rsv200PropHeaderName6">
      <logic:equal name="rsv200knForm" property="rsv200Prop6Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
        <tr>
          <th >
            <bean:write name="rsv200knForm" property="rsv200PropHeaderName6" />
          </th>
          <td >
            <logic:notEmpty name="rsv200knForm" property="rsv200Prop6Value">
              <bean:write name="rsv200knForm" property="rsv200Prop6Value" /><gsmsg:write key="cmn.days.after" />
            </logic:notEmpty>
          </td>
        </tr>
      </logic:equal>
    </logic:notEmpty>

    <logic:equal name="rsv200knForm" property="rsv200BikoCheck" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>">
      <tr>
        <th >
          <gsmsg:write key="cmn.memo" />
        </th>
        <td >
          <bean:write name="rsv200knForm" property="rsv200knBiko" filter="false" />
        </td>
      </tr>
    </logic:equal>
  </table>

  <div class="txt_l fw_b fs_16 mt20">
    <gsmsg:write key="reserve.rsv200kn.3" />
  </div>

  <table class="table-top mt0">
    <tr>
      <th>
        <gsmsg:write key="reserve.121" />
      </th>
    </tr>
    <bean:define id="mod" value="0" />

    <logic:notEmpty name="rsv200knForm" property="rsv200SisetuList" scope="request">
      <logic:iterate id="sisetu" name="rsv200knForm" property="rsv200SisetuList" scope="request" indexId="idx">
        <tr>
          <td>
            <bean:write name="sisetu" property="rsdName" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('ikkatu_settei_kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_ikkatu_settei');">
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