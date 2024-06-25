<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
  String maxLengthBiko = String.valueOf(jp.groupsession.v2.ip.IpkConst.MAX_LENGTH_SPECM_BIKO);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../ipkanri/js/ipk100.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>

<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="ipk.ipk100.1" /></title>
</head>

<body onload="scr(<bean:write name='ipk100Form' property='ipk100scroll' />);showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');">
  <html:form action="/ipkanri/ipk100">
    <html:hidden property="CMD" />
    <html:hidden property="backScreen" />
    <html:hidden property="editMode" />
    <html:hidden property="specKbn" />
    <html:hidden property="ismSid" />
    <html:hidden property="ipk100scroll" />
    <html:hidden property="existFlg" />
    <html:hidden property="ipk100svSpecLevel" />
    <html:hidden property="ipk100helpMode" />
    <input type="hidden" name="helpPrm" value="<bean:write name='ipk100Form' property='ipk100helpMode' />">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.ipkanri" /></span><gsmsg:write key="ipk.10" />
        </li>
        <li>
          <div>
            <button type="button" name="<gsmsg:write key="cmn.ok" />" value="OK" onClick="ipk100ok();" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:equal name="ipk100Form" property="editMode" value="2">
              <button type="button" name="delete" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush2('ipk100Delete');" class="baseBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk100Return');">
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

      <table class="table-left" cellpadding="5" cellspacing="0" border="0">
        <tr>
          <th class="txt_l bgC_header1 table_title-color" colspan="2">
              <logic:equal name="ipk100Form" property="specKbn" value="1">
                <gsmsg:write key="ipk.ipk100.4" />
              </logic:equal>
              <logic:equal name="ipk100Form" property="specKbn" value="2">
                <gsmsg:write key="ipk.ipk100.2" />
              </logic:equal>
              <logic:equal name="ipk100Form" property="specKbn" value="3">
                <gsmsg:write key="ipk.ipk100.3" />
              </logic:equal>
          </th>
        </tr>

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.name4" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="w80 txt_l">
            <input type="text" name="ipk100name" maxlength="50" class="wp400" value="<bean:write name='ipk100Form' property='ipk100name' />">
          </td>
        </tr>

         <logic:empty name="ipk100Form" property="ipk100specMstModelList">
         <span id="Layer1"></span>
         </logic:empty>

        <logic:notEmpty name="ipk100Form" property="ipk100specMstModelList">
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.sort" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td>
            <div class="scroll" id="Layer1">
              <table class="table-top">
                <logic:notEmpty name="ipk100Form" property="ipk100specMstModelList">
                  <tr>
                    <logic:equal name="ipk100Form" property="ipk100specLevel" value="0">
                      <td class="w10 hp35 txt_c js_tableTopCheck cursor_p" id="0">
                        <input type="radio" name="ipk100specLevel" value="0" checked>
                    </logic:equal>

                    <logic:notEqual name="ipk100Form" property="ipk100specLevel" value="0">
                      <td class="w10 hp35 txt_c js_tableTopCheck cursor_p" id="0">
                        <input type="radio" name="ipk100specLevel" value="0">
                    </logic:notEqual>
                    </td>
                    <td class="sonyu fw_b" id="disp0"></td>
                  </tr>

                  <logic:iterate id="specModel" name="ipk100Form" property="ipk100specMstModelList" indexId="idx">
                    <bean:define id="radioValue" name="specModel" property="ismSid" type="java.lang.Integer" />
                    <tr>
                      <td class="w10 hp35"></td>
                      <td>
                        <bean:write name="specModel" property="ipk100name" filter="false" />
                      </td>
                    </tr>

                    <tr>
                      <logic:equal name="ipk100Form" property="ipk100specLevel" value="<%=String.valueOf(radioValue.intValue())%>">
                        <td class="w10 txt_c no_w hp35 js_tableTopCheck cursor_p" id="<bean:write name='specModel' property='ismSid' />">
                          <input type="radio" name="ipk100specLevel" value="<%=String.valueOf(radioValue.intValue())%>" checked>
                      </logic:equal>

                      <logic:notEqual name="ipk100Form" property="ipk100specLevel" value="<%=String.valueOf(radioValue.intValue())%>">
                        <td class="w10 txt_c no_w hp35 js_tableTopCheck cursor_p" id="<bean:write name='specModel' property='ismSid' />">
                          <input type="radio" name="ipk100specLevel" value="<%=String.valueOf(radioValue.intValue())%>">
                      </logic:notEqual>
                      </td>
                      <td class="sonyu fw_b" id="disp<bean:write name='specModel' property='ismSid' />"></td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
              </table>
            </div>
          </td>
        </tr>
        </logic:notEmpty>

        <tr>
          <th>
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="txt_l">
            <textarea name="ipk100biko" class="wp400" rows="5" class="textarea" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength');" id="inputstr"><bean:write name="ipk100Form" property="ipk100biko" /></textarea>
            <br>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength" class="formCounter">0</span>/<span class="formCounter_max"><%= maxLengthBiko %>
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block mt5">
        <button type="button" name="<gsmsg:write key="cmn.ok" />" value="OK" onClick="ipk100ok();" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="ipk100Form" property="editMode" value="2">
          <button type="button" name="delete" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush2('ipk100Delete');" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk100Return');">
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