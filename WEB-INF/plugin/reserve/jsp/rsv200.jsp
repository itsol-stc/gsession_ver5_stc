<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
String maxLengthBiko = String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.MAX_LENGTH_BIKO);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.61" /> ]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../reserve/js/rsv200.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/sisetuPopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? 500"> </script>

<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onload="showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');" onunload="windowClose();">
  <html:form action="/reserve/rsv200">
    <input type="hidden" name="CMD" value="ikkatu_settei_kakunin">

    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050SortRadio" />
    <html:hidden property="rsv080EditGrpSid" />
    <html:hidden property="rsv080SortRadio" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv200Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv200Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>


    <logic:notEmpty name="rsv200Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv200Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

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
          <gsmsg:write key="reserve.61" />
        </li>
        <li>
          <div>
            <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_settei');">
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

      <!-- 上のテーブル -->

      <table class="table-left" >
        <colgroup class="w20"></colgroup>
        <colgroup class="w80"></colgroup>
        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName4">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop4" name="rsv200Form" property="rsv200Prop4Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop4">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName4" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop4Inp">
              <html:text name="rsv200Form" property="rsv200Prop4Value" maxlength="20" styleClass="w200" />
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName5">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop5" name="rsv200Form" property="rsv200Prop5Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop5">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName5" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop5Inp">
              <html:text name="rsv200Form" property="rsv200Prop5Value" maxlength="17" styleClass="wp150" />
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName1">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop1" name="rsv200Form" property="rsv200Prop1Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop1">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName1" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop1Inp">
              <html:text name="rsv200Form" property="rsv200Prop1Value" styleClass="wp150" maxlength="10" />
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName2">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop2" name="rsv200Form" property="rsv200Prop2Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop2">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName2" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop2Inp">
              <span class="verAlignMid">
                <html:radio styleId="2ka" name="rsv200Form" property="rsv200Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>" />
                <label for="2ka">
                  <gsmsg:write key="cmn.accepted" />
                </label>
                <html:radio styleClass="ml10" styleId="2huka" name="rsv200Form" property="rsv200Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>" />
                <label for="2huka">
                  <gsmsg:write key="cmn.not" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName3">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop3" name="rsv200Form" property="rsv200Prop3Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop3">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName3" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop3Inp">
              <span class="verAlignMid">
                <html:radio styleId="3ka" name="rsv200Form" property="rsv200Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>" />
                <label for="3ka">
                  <gsmsg:write key="cmn.accepted" />
                </label>
                <html:radio styleClass="ml10" styleId="3huka" name="rsv200Form" property="rsv200Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>" />
                <label for="3huka">
                  <gsmsg:write key="cmn.not" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName7">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop7" name="rsv200Form" property="rsv200Prop7Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop7">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName7" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop7Inp">
              <span class="verAlignMid">
                <html:radio styleId="7ka" name="rsv200Form" property="rsv200Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>" />
                <label for="7ka">
                  <gsmsg:write key="cmn.accepted" />
                </label>
                <html:radio styleClass="ml10" styleId="7huka" name="rsv200Form" property="rsv200Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>" />
                <label for="7huka">
                  <gsmsg:write key="cmn.not" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv200Form" property="rsv200PropHeaderName6">
          <tr>
            <th class="no_w js_rsv200Prop_th cursor_p" >
              <span class="verAlignMid">
                <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Prop6" name="rsv200Form" property="rsv200Prop6Check" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
                <label for="rsv200Prop6">
                  <span>
                    <bean:write name="rsv200Form" property="rsv200PropHeaderName6" />
                  </span>
                </label>
              </span>
            </th>
            <td class="txt_l js_rsv200Prop_td" id="rsv200Prop6Inp">
              <html:text name="rsv200Form" property="rsv200Prop6Value" styleClass="wp50" maxlength="4" />
              &nbsp;
              <gsmsg:write key="cmn.days.after" />
            </td>
          </tr>
        </logic:notEmpty>

        <tr>
          <th class="no_w js_rsv200Prop_th cursor_p" >
            <span class="verAlignMid">
              <html:checkbox styleClass="js_rsv200Prop_check" styleId="rsv200Biko" name="rsv200Form" property="rsv200BikoCheck" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_CHECK_YES)%>"/>
              <label for="rsv200Biko">
                <span>
                  <gsmsg:write key="cmn.memo" />
                </span>
              </label>
            </span>
          </th>
          <td class="txt_l js_rsv200Prop_td" id="rsv200BikoInp">
            <textarea name="rsv200Biko" class="wp400" rows="6" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength');" id="inputstr"><bean:write name="rsv200Form" property="rsv200Biko" /></textarea>
            <br>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength" class="formCounter">0</span>
            <span class="formCounter_max">/<%=maxLengthBiko%><gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
      </table>

      <!-- 下のテーブル -->

      <table class="table-top">
        <tr>
          <th class="txt_c cursor_p js_tableTopCheck js_tableTopCheck-header">
            <input type="checkbox" name="allCheck" onClick="changeChk();">
          </th>
          <th class="w100">
            <span>
              <gsmsg:write key="reserve.121" />
            </span>
          </th>
        </tr>

        <bean:define id="mod" value="0" />
        <logic:notEmpty name="rsv200Form" property="rsv200SisetuList" scope="request">
          <logic:iterate id="sisetu" name="rsv200Form" property="rsv200SisetuList" scope="request" indexId="idx">
            <tr class="js_listHover cursor_p" id="<bean:write name="sisetu" property="rsdSid" />">
              <td class="txt_c js_tableTopCheck">
                <html:multibox name="rsv200Form" property="rsv200TargetSisetu">
                  <bean:write name="sisetu" property="rsdSid" />
                </html:multibox>
              </td>
              <td class="js_listClick txt_l">
                <span class="cl_linkDef"><bean:write name="sisetu" property="rsdName" /></span>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>

      <div class="footerBtn_block">
        <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_settei');">
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