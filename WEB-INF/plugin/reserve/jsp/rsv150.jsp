<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
int dspNG = jp.groupsession.v2.cmn.GSConstReserve.KOJN_SETTEI_DSP_NO;
%>
<%
int dspOK = jp.groupsession.v2.cmn.GSConstReserve.KOJN_SETTEI_DSP_OK;
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" />[<gsmsg:write key="cmn.reserve" /> <gsmsg:write key="cmn.display.settings" />]</title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../reserve/js/rsv150.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<bod>
<html:form action="/reserve/rsv150">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="rsvBackPgId" />
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedGrpSid" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsv150initDspFlg" />
<html:hidden property="rsv150DateKbn" />

<%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp" %>

<logic:notEmpty name="rsv150Form" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv150Form" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv150Form" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv150Form" property="rsvIkkatuTorokuKey" scope="request">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.reserve" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('hyozi_settei_kakunin');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_kojn_menu');">
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
      <div class="verAlignMid">
       <html:radio name="rsv150Form" styleId="sch096Dsp_02" property="rsv150DefDsp" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.DSP_DAY)%>" />
       <label for="sch096Dsp_02" class="mr10"><gsmsg:write key="cmn.days2" /></label>
       <html:radio name="rsv150Form" styleClass="" styleId="sch096Dsp_01" property="rsv150DefDsp" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.DSP_WEEK)%>" />
       <label for="sch096Dsp_01"><gsmsg:write key="cmn.weeks" /></label>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.99" />
      </th>
      <td>
        <gsmsg:write key="reserve.rsv150.1" />
        <div class="mt5">
        <html:select property="rsv150SelectedGrpSid" styleClass="select01">
        <html:optionsCollection name="rsv150Form" property="rsv150sisetuLabelList" value="value" label="label" />
        </html:select>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.100" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <gsmsg:write key="reserve.rsv150.2" /><br>
        <span class="verAlignMid">
          <html:checkbox name="rsv150Form" property="rsv150DispItem1" value="<%=String.valueOf(dspOK)%>" styleId="rsv150DispItem1" />
          <label for="rsv150DispItem1"><gsmsg:write key="reserve.72" /></label>
        </span><br>
        <span class="verAlignMid">
          <html:checkbox name="rsv150Form" property="rsv150DispItem2" value="<%=String.valueOf(dspOK)%>" styleId="rsv150DispItem2" />
          <label for="rsv150DispItem2"><gsmsg:write key="reserve.137" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.auto.reload.time" />
      </th>
      <td>
        <html:select name="rsv150Form" property="rsv150ReloadTime">
        <html:optionsCollection name="rsv150Form" property="rsv150TimeLabelList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="reserve.102" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="rsv150Form" property="rsv150ImgDspKbn" styleId="rsv150ImgDspKbn0" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
          <label for="rsv150ImgDspKbn0" class="mr10"><gsmsg:write key="cmn.display.ok" /></label>
          <html:radio name="rsv150Form" styleClass="" property="rsv150ImgDspKbn" styleId="rsv150ImgDspKbn1" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
          <label for="rsv150ImgDspKbn1"><gsmsg:write key="cmn.dont.show" /></label>
        </div>
      </td>
    </tr>
    <logic:equal name="rsv150Form" property="rsv150DateKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.AUTH_ALL_USER)%>">
    <tr>
      <th>
       <gsmsg:write key="cmn.show.timezone.days.setting" />
      </th>
      <td>
      <gsmsg:write key="reserve.rsv160.1" />
      <div class="mt5">
      <gsmsg:write key="cmn.starttime" />：
      <html:select property="rsv150SelectedFromSid">
        <html:optionsCollection name="rsv150Form" property="rsv150ourLabelList" value="value" label="label" />
      </html:select>
      </div>
      <div class="mt5">
      <gsmsg:write key="cmn.endtime" />：
      <html:select property="rsv150SelectedToSid">
        <html:optionsCollection name="rsv150Form" property="rsv150ourLabelList" value="value" label="label" />
      </html:select>
      </div>
      </td>
    </tr>
    </logic:equal>
    <tr>
      <th>
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td>
        <gsmsg:write key="reserve.rsv170.3" />
        <div>
        <html:select name="rsv150Form" property="rsv150ViewCnt">
          <html:optionsCollection name="rsv150Form" property="rsv150DspCntList" value="value" label="label" />
        </html:select>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('hyozi_settei_kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_kojn_menu');">
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