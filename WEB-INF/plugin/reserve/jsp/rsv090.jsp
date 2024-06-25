<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve" %>

<%
String maxLengthBiko = String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.MAX_LENGTH_BIKO);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_SINKI)%>"> [ <gsmsg:write key="reserve.rsv090.1" /> ]</logic:equal> <logic:equal name="rsv090Form"
    property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">[ <gsmsg:write key="reserve.rsv090.2" /> ]</logic:equal>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/rsv090.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onload="initDisplay();showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');" class="display_n">
  <html:form action="/reserve/rsv090">
    <input type="hidden" name="CMD" value="sisetu_toroku_kakunin">

    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050SortRadio" />
    <html:hidden property="rsv080EditGrpSid" />
    <html:hidden property="rsv080SortRadio" />
    <html:hidden property="rsv090InitFlg" />
    <html:hidden property="rsv090ProcMode" />
    <html:hidden property="rsv090EditGrpSid" />
    <html:hidden property="rsv090EditSisetuSid" />
    <html:hidden property="rsv090sisGrpApprFlg" />
    <input type="hidden" name="attachmentTempDirPlus1" value="upSisetuImg" />
    <input type="hidden" name="attachmentTempDirPlus2" value="upPlaceImg" />
    <input type="hidden" name="rsv090BinSid" value="" />
    

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv090Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv090Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv090Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv090Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <input type="hidden" name="helpPrm" value="<bean:write name="rsv090Form" property="rsv090ProcMode" />">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_SINKI)%>">
            <gsmsg:write key="reserve.rsv090.1" />
          </logic:equal>
          <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
            <gsmsg:write key="reserve.rsv090.2" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>

            <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="buttonPush('sisetu_sakuzyo_kakunin');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>

            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_zyoho_settei');">
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


      <table class="table-left table-fixed" >
        <tr>
          <th class="wp120 ">
            <span>
              <gsmsg:write key="cmn.facility.group" />
            </span>
          </th>
          <td class="txt_l ">
            <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_SINKI)%>">
              <span>
                <bean:write name="rsv090Form" property="rsv090GrpName" />
              </span>
            </logic:equal>
            <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
              <html:select property="rsv090SelectRsvGrp" styleClass="select01" onchange="changeGroupCombo('chGrpComb');">
                <html:optionsCollection name="rsv090Form" property="rsv090RsvGrpLabelList" value="value" label="label" />
              </html:select>
            </logic:equal>
          </td>
          <th class="wp180 txt_c " rowspan="2">
            <gsmsg:write key="reserve.view.sisetu.dsp.kbn" />
          </th>
        </tr>
        <tr>
          <th class=" ">
            <span>
              <gsmsg:write key="reserve.47" />
            </span>
          </th>
          <td class=" txt_l">
            <span>
              <bean:write name="rsv090Form" property="rsv090SisetuKbnName" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="">
            <span>
              <gsmsg:write key="reserve.55" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="">
            <html:text styleClass="wp200" name="rsv090Form" property="rsv090SisetuId" maxlength="15" />
          </td>
          <td class=" txt_l no_w">
            <span class="verAlignMid">
              <html:radio styleId="sisetuIdDsp" name="rsv090Form" property="rsv090SisetuIdDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
              <label for="sisetuIdDsp">
                <gsmsg:write key="cmn.display.ok" />
              </label>
              <html:radio styleClass="ml10" styleId="sisetuIdDspNot" name="rsv090Form" property="rsv090SisetuIdDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
              <label for="sisetuIdDspNot">
                <gsmsg:write key="cmn.dont.show" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="">
            <span>
              <gsmsg:write key="cmn.facility.name" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="txt_l ">
            <html:text name="rsv090Form" property="rsv090SisetuName" styleClass="wp400" maxlength="100" />
          </td>
          <td class="txt_l "></td>
        </tr>

        <tr>
          <th class="">
            <span>
              <gsmsg:write key="cmn.asset.register.num" />
            </span>
          </th>
          <td class="txt_l">
            <html:text name="rsv090Form" property="rsv090SisanKanri" styleClass="wp200" maxlength="20" />
          </td>
          <td class=" txt_l no_w">
            <span class="verAlignMid">
              <html:radio styleId="sisanKanriDsp" name="rsv090Form" property="rsv090SisanKanriDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
              <label for="sisanKanriDsp">
                <gsmsg:write key="cmn.display.ok" />
              </label>
              <html:radio styleClass="ml10" styleId="sisanKanriDspNot" name="rsv090Form" property="rsv090SisanKanriDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
              <label for="sisanKanriDspNot">
                <gsmsg:write key="cmn.dont.show" />
              </label>
            </span>
          </td>
        </tr>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName4">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName4" />
              </span>
            </th>
            <td class="txt_l">
              <html:text name="rsv090Form" property="rsv090Prop4Value" maxlength="20" styleClass="wp200" />
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop4ValueDsp" name="rsv090Form" property="rsv090Prop4ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop4ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop4ValueDspNot" name="rsv090Form" property="rsv090Prop4ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop4ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName5">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName5" />
              </span>
            </th>
            <td class=" txt_l">
              <html:text name="rsv090Form" property="rsv090Prop5Value" maxlength="17" styleClass="wp200s" />
            </td>
            <td class=" txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop5ValueDsp" name="rsv090Form" property="rsv090Prop5ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop5ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop5ValueDspNot" name="rsv090Form" property="rsv090Prop5ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop5ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName1">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName1" />
              </span>
            </th>
            <td class="txt_l">
              <html:text name="rsv090Form" property="rsv090Prop1Value" styleClass="wp100" maxlength="10" />
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop1ValueDsp" name="rsv090Form" property="rsv090Prop1ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop1ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop1ValueDspNot" name="rsv090Form" property="rsv090Prop1ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop1ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName2">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName2" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
                <html:radio styleId="2ka" name="rsv090Form" property="rsv090Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>" />
                <label for="2ka">
                  <gsmsg:write key="cmn.accepted" />
                </label>
                <html:radio styleClass="ml10" styleId="2huka" name="rsv090Form" property="rsv090Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>" />
                <label for="2huka">
                  <gsmsg:write key="cmn.not" />
                </label>
              </span>
            </td>
            <td class=" txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop2ValueDsp" name="rsv090Form" property="rsv090Prop2ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop2ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop2ValueDspNot" name="rsv090Form" property="rsv090Prop2ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop2ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName3">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName3" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
                <html:radio styleId="3ka" name="rsv090Form" property="rsv090Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>" />
                <label for="3ka">
                  <gsmsg:write key="cmn.accepted" />
                </label>
                <html:radio styleClass="ml10" styleId="3huka" name="rsv090Form" property="rsv090Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>" />
                <label for="3huka">
                  <gsmsg:write key="cmn.not" />
                </label>
              </span>
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop3ValueDsp" name="rsv090Form" property="rsv090Prop3ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop3ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop3ValueDspNot" name="rsv090Form" property="rsv090Prop3ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop3ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName7">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName7" />
              </span>
            </th>
            <td class="txt_l">
              <span class="verAlignMid">
                <html:radio styleId="7ka" name="rsv090Form" property="rsv090Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>" />
                <label for="7ka">
                  <gsmsg:write key="cmn.accepted" />
                </label>
                <html:radio styleClass="ml10" styleId="7huka" name="rsv090Form" property="rsv090Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>" />
                <label for="7huka">
                  <gsmsg:write key="cmn.not" />
                </label>
              </span>
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop7ValueDsp" name="rsv090Form" property="rsv090Prop7ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop7ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop7ValueDspNot" name="rsv090Form" property="rsv090Prop7ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop7ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:equal name="rsv090Form" property="rsv090apprKbnInput" value="true">
          <tr>
            <th class="">
              <span>
                <gsmsg:write key="reserve.appr.set.title" />
              </span>
            </th>
            <td class="txt_l">
              <logic:equal name="rsv090Form" property="rsv090sisGrpAdmFlg" value="false">
                <span>
                  <gsmsg:write key="reserve.rsv090.4" />
                </span>
              </logic:equal>
              <logic:notEqual name="rsv090Form" property="rsv090sisGrpAdmFlg" value="false">
                <logic:equal name="rsv090Form" property="rsv090sisGrpApprFlg" value="true">
                  <span>
                    <gsmsg:write key="reserve.appr.set.kbn1" />
                </logic:equal>
                <logic:notEqual name="rsv090Form" property="rsv090sisGrpApprFlg" value="true">
                  <span class="verAlignMid">
                    <html:radio styleId="apprKbn1" name="rsv090Form" property="rsv090apprKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSD_APPR_KBN_NOSET)%>" />
                    <label for="apprKbn1">
                      <gsmsg:write key="reserve.appr.set.kbn2" />
                    </label>
                    <html:radio styleClass="ml10" styleId="apprKbn2" name="rsv090Form" property="rsv090apprKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSD_APPR_KBN_APPR)%>" />
                    <label for="apprKbn2">
                      <gsmsg:write key="reserve.appr.set.kbn1" />
                    </label>
                  </span>
                </logic:notEqual>
              </logic:notEqual>
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="apprKbnDsp" name="rsv090Form" property="rsv090apprKbnDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="apprKbnDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="apprKbnDspNot" name="rsv090Form" property="rsv090apprKbnDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="apprKbnDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:notEmpty name="rsv090Form" property="rsv090PropHeaderName6">
          <tr>
            <th class="">
              <span>
                <bean:write name="rsv090Form" property="rsv090PropHeaderName6" />
              </span>
            </th>
            <td class="txt_l">
              <html:text name="rsv090Form" property="rsv090Prop6Value" styleClass="wp50" maxlength="4" />
              &nbsp;
              <span>
                <gsmsg:write key="cmn.days.after" />
              </span>
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="prop6ValueDsp" name="rsv090Form" property="rsv090Prop6ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="prop6ValueDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="prop6ValueDspNot" name="rsv090Form" property="rsv090Prop6ValueDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="prop6ValueDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <tr>
          <th class="">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="txt_l">
            <textarea name="rsv090Biko" class="wp400" rows="6" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength');" id="inputstr"><bean:write name="rsv090Form" property="rsv090Biko" /></textarea>
            <br>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength" class="formCounter">0</span>
            <span class="formCounter_max">/<%=maxLengthBiko%>
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
          <td class="txt_l no_w">
            <span class="verAlignMid">
              <html:radio styleId="bikoDsp" name="rsv090Form" property="rsv090BikoDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
              <label for="bikoDsp">
                <gsmsg:write key="cmn.display.ok" />
              </label>
              <html:radio styleClass="ml10" styleId="bikoDspNot" name="rsv090Form" property="rsv090BikoDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
              <label for="bikoDspNot">
                <gsmsg:write key="cmn.dont.show" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="">
            <span>
              <gsmsg:write key="reserve.59" />
            </span>
          </th>
          <td class="txt_l mb0">

            <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_RETO_RSV090) %>"
              pluginId="<%=GSConstReserve.PLUGIN_ID_RESERVE %>"
              tempDirId="rsv090"
              tempDirPlus="<%=GSConstReserve.TEMP_IMG_SISETU_UPLOAD%>"
              fileList="false"
              formId="1" />

            <logic:notEmpty name="rsv090Form" property="rsv090SisetuFileLabelList" scope="request">
              <%
              String blockMargin = "mt5";
              %>

              <%
              int count = 0;
              %>
              <logic:iterate id="fileMdl" name="rsv090Form" property="rsv090SisetuFileLabelList" scope="request">
                <%
                if (count > 0) {
                	blockMargin = "mt20";
                }
                %>
                <div class="<%= blockMargin %> js_sisetuImage">
                  <img src="../reserve/rsv090.do?CMD=getImageFileSisetu&rsv090BinSid=<bean:write name="fileMdl" property="value" />" alt="<gsmsg:write key="reserve.17" />" class="wp130 mr5">
                  <br>
                  <a href="#!" onclick="attachmentFileDownload(<bean:write name="fileMdl" property="value" />, 1);">
                    <span class="textLink"><bean:write name="fileMdl" property="label" /></span>
                  </a>
                  <img class="ml5 cursor_p btn_originalImg-display" src="../common/images/original/icon_delete.png" onclick="attachmentDeleteFile(<bean:write name="fileMdl" property="value" />, 1);buttonPush('');" draggable="false">
                </div>
                <%
                count++;
                %>
              </logic:iterate>
            </logic:notEmpty>

          </td>
          <td class="txt_l no_w">
            <logic:notEmpty name="rsv090Form" property="rsv090SisetuFileLabelList" scope="request">
              <html:select property="rsv090SisetuImgDefoValue" styleClass="wp150">
                <html:optionsCollection name="rsv090Form" property="rsv090SisetuFileLabelList" value="value" label="label" />
              </html:select>
              <br>
              <span class="verAlignMid">
                <html:radio styleId="sisetuImgDefoDsp" name="rsv090Form" property="rsv090SisetuImgDefoDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="sisetuImgDefoDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="sisetuImgDefoDspNot" name="rsv090Form" property="rsv090SisetuImgDefoDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="sisetuImgDefoDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </logic:notEmpty>
          </td>
        </tr>

        <logic:notEmpty name="rsv090Form" property="place">
          <tr>
            <th class="">
              <span>
                <gsmsg:write key="reserve.location.comments" />
              </span>
            </th>
            <td class="">
              <html:text name="rsv090Form" property="rsv090PlaceComment" styleClass="wp200" maxlength="50" />
            </td>
            <td class="txt_l no_w">
              <span class="verAlignMid">
                <html:radio styleId="placeCommentDsp" name="rsv090Form" property="rsv090PlaceCommentDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_ON)%>" />
                <label for="placeCommentDsp">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio styleClass="ml10" styleId="placeCommentDspNot" name="rsv090Form" property="rsv090PlaceCommentDspKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.SISETU_IMG_OFF)%>" />
                <label for="placeCommentDspNot">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <tr>
          <th class="">
            <span>
              <gsmsg:write key="reserve.60" />
            </span>
          </th>
          <td class="txt_l mb0">

            <div class="mb5">
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_RETO_RSV090) %>"
                pluginId="<%=GSConstReserve.PLUGIN_ID_RESERVE %>"
                tempDirId="rsv090"
                tempDirPlus="<%=GSConstReserve.TEMP_IMG_PLACE_UPLOAD%>"
                fileList="false"
                formId="2" />
            </div>
            <logic:notEmpty name="rsv090Form" property="place">

              <%
              String commentDsp = "commentDsp";
              %>
              <%
              String commentDspNot = "commentDspNot";
              %>
              <%
              String commentDspVal = "";
              %>
              <%
              String commentDspNotVal = "";
              %>
              <%
              String blockMargin = "mt5";
              %>

              <%
              int count = 0;
              %>

              <logic:iterate id="place" name="rsv090Form" property="place" scope="request">
                <%
                commentDspVal = commentDsp + count;
                %>
                <%
                commentDspNotVal = commentDspNot + count;
                %>
                <%
                if (count > 0) {
                	blockMargin = "mt20";
                }
                %>
                <div class="<%= blockMargin %> js_basyoImage">
                  <img src="../reserve/rsv090.do?CMD=getImageFilePlace&rsv090BinSid=<bean:write name="place" property="rsv090PlaceFileValue" />" alt="<gsmsg:write key="reserve.66" />" border="1" class="wp130 mr5">
                  <html:hidden name="place" property="rsv090PlaceFileValue" indexed="true" />
                  <div>
                    <html:hidden name="place" property="rsv090PlaceFileLabel" indexed="true" />
                    <a href="#!" onclick="attachmentFileDownload(<bean:write name="place" property="rsv090PlaceFileValue" />, 2);">
                      <span class="textLink"><bean:write name="place" property="rsv090PlaceFileLabel" /></span>
                    </a>
                    <img class="ml5 cursor_p btn_originalImg-display" src="../common/images/original/icon_delete.png" onclick="basyoImageDelete(<bean:write name="place" property="rsv090PlaceFileValue" />);" draggable="false">
                    <div class="mt5"><gsmsg:write key="cmn.form.label" /><gsmsg:write key="cmn.colon" /><html:text name="place" property="rsv090PlaceFileComment" indexed="true" styleClass="wp300" maxlength="50" /></div>
                  </div>
                </div>
                <%
                count++;
                %>
              </logic:iterate>
            </logic:notEmpty>
          </td>
          <td class="txt_l">
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="rsv090Form" property="rsv090ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="buttonPush('sisetu_sakuzyo_kakunin');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_zyoho_settei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>



    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>