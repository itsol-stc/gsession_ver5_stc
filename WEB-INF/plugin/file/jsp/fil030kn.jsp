<%@page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<% String sinki = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_NEW); %>
<% String hensin = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_HENSIN); %>
<% String zenhen = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_ZENHENSIN); %>
<% String tenso = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_TENSO); %>
<% String soko = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_SOKO); %>
<% String nikkan = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_SC_NIKKAN); %>
<% String syukan = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_SC_SYUKAN); %>
<% String zaiseki = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_ZAISEKI); %>
<% String main = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_MAIN); %>
<% String toroku = String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU); %>
<% String delete = String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE); %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.52" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/fil030kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/file/fil030kn">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="fileSid" value="">
<html:hidden property="cmnMode" />
<html:hidden property="fil010DspCabinetKbn" />
<html:hidden property="backDsp" />
<html:hidden property="backScreen" />
<html:hidden property="filSearchWd" />
<html:hidden property="admVerKbn" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="fil030SelectCabinet" />
<html:hidden property="fil030CabinetName" />
<html:hidden property="fil030AccessKbn" />
<html:hidden property="fil030CapaKbn" />
<html:hidden property="fil030CapaSize" />
<html:hidden property="fil030CapaWarn" />
<html:hidden property="fil030VerKbn" />
<html:hidden property="fil030VerAllKbn" />
<html:hidden property="fil030Biko" />
<html:hidden property="fil030ImageName" />
<html:hidden property="fil030ImageSaveName" />
<html:hidden property="fil030InitFlg" />
<html:hidden property="fil030PersonalFlg" />
<html:hidden property="file030AdaptIncFile" />
<html:hidden property="file030ErrlAutoKbn" />
<html:hidden property="file030ErrlAutoFolder1" />
<html:hidden property="file030ErrlAutoFolder2" />
<html:hidden property="file030ErrlAutoFolder3" />
<html:hidden property="ownerSid" />

<html:hidden property="fil220cabinetKbn" />
<html:hidden property="fil280svKeyword" />
<html:hidden property="fil280svGroup" />
<html:hidden property="fil280svUser" />

<logic:notEmpty name="fil030knForm" property="fil220sltCheck" scope="request">
<logic:iterate id="select" name="fil030knForm" property="fil220sltCheck" scope="request">
  <input type="hidden" name="fil220sltCheck" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil030knForm" property="fil030SvAcFull">
<logic:iterate id="afid" name="fil030knForm" property="fil030SvAcFull">
  <input type="hidden" name="fil030SvAcFull" value="<bean:write name="afid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil030knForm" property="fil030SvAcRead">
<logic:iterate id="arid" name="fil030knForm" property="fil030SvAcRead">
  <input type="hidden" name="fil030SvAcRead" value="<bean:write name="arid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil030knForm" property="fil030SvAdm">
<logic:iterate id="admid" name="fil030knForm" property="fil030SvAdm">
  <input type="hidden" name="fil030SvAdm" value="<bean:write name="admid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil030knForm" property="fil010SelectDelLink" scope="request">
<logic:iterate id="item" name="fil030knForm" property="fil010SelectDelLink" scope="request">
  <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil030knForm" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil030knForm" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="helpPrm" value="<bean:write name="fil030knForm" property="cmnMode" /><bean:write name="fil030knForm" property="fil010DspCabinetKbn" />">

<% int dspMode = 0; %>
<logic:equal name="fil030knForm" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
  <% dspMode = Integer.parseInt(jp.groupsession.v2.fil.GSConstFile.DSP_CABINET_PRIVATE); %>
</logic:equal>
<logic:equal name="fil030knForm" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
  <% dspMode = Integer.parseInt(jp.groupsession.v2.fil.GSConstFile.DSP_CABINET_ERRL); %>
</logic:equal>

<div class="pageTitle w85 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" alt="<gsmsg:write key="cmn.filekanri" />">
      <img class="header_pluginImg" src="../file/images/original/header_file.png" alt="<gsmsg:write key="cmn.filekanri" />">
    </li>
    <li><gsmsg:write key="cmn.filekanri" /></li>
    <bean:define id="pageTitle"><gsmsg:write key="fil.192" /></bean:define>
    <logic:notEqual name="fil030Form" property="cmnMode" value="<%= GSConstFile.CMN_MODE_ADD %>">
      <bean:define id="pageTitle"><gsmsg:write key="fil.52" /></bean:define>
    </logic:notEqual>
    <li class="pageTitle_subFont">
    <% if (dspMode == 0) { %>
      <gsmsg:write key="cmn.share"/><bean:write name="pageTitle" />
    <% } else if (dspMode == 2) { %>
      <gsmsg:write key="fil.errl"/><bean:write name="pageTitle" />
    <% } else { %>
      <gsmsg:write key="cmn.individual" /><bean:write name="pageTitle" />
    <% } %>
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil030knok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil030knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w85 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
    <tr>
      <th class="w25">
        <% if (dspMode != 1) { %>
          <gsmsg:write key="fil.13" />
        <% } else { %>
          <gsmsg:write key="fil.146" />
        <% } %>
      </th>
      <td class="w75">
        <bean:write name="fil030knForm" property="fil030CabinetName" />
      </td>
    </tr>
    <tr>
      <th>
        <% if (dspMode != 1) { %>
          <gsmsg:write key="fil.102" />
        <% } else { %>
          <gsmsg:write key="fil.153" />
        <% } %>
      </th>
      <logic:equal name="fil030knForm" property="fil030AccessKbn" value="0">
      <td>
        <% if (dspMode != 1) { %>
          <gsmsg:write key="cmn.not.limit" />
        <% } else { %>
          <gsmsg:write key="cmn.not.permit" />
        <% } %>
      </td>
      </logic:equal>
      <logic:notEqual name="fil030knForm" property="fil030AccessKbn" value="0">
      <td>


      <logic:notEmpty name="fil030knForm" property="fil030knAcFullList" scope="request">
      <div class="w40 bgC_header2 txt_c">
        <gsmsg:write key="cmn.add.edit.delete" />
      </div>
      <div class="w40 ml5">
      <logic:iterate id="fullBean" name="fil030knForm" property="fil030knAcFullList" scope="request" type="UsrLabelValueBean">
        <span class="<%=fullBean.getCSSClassNameNormal()%>"><bean:write name="fullBean" property="label" /></span><br>
      </logic:iterate>
      </div>
      </logic:notEmpty>

      <logic:notEmpty name="fil030knForm" property="fil030knAcReadList" scope="request">
      <div class="w40 mt10 bgC_header2 txt_c">
        <gsmsg:write key="cmn.reading" />
      </div>
      <div class="w40 ml5">
      <logic:iterate id="readBean" name="fil030knForm" property="fil030knAcReadList" scope="request" type="UsrLabelValueBean">
        <span class="<%=readBean.getCSSClassNameNormal()%>"><bean:write name="readBean" property="label" /></span><br>
      </logic:iterate>
      </div>
      </logic:notEmpty>


      <% if (dspMode != 1) { %>
        <br>
        <gsmsg:write key="fil.127" /><gsmsg:write key="cmn.colon" />
        <logic:notEqual name="fil030knForm" property="file030AdaptIncFile" value="1"><gsmsg:write key="fil.129" /></logic:notEqual>
        <logic:equal name="fil030knForm" property="file030AdaptIncFile" value="1"><gsmsg:write key="fil.128" /></logic:equal>
      <% } %>
      </td>
      </logic:notEqual>

    </tr>
    <% if (dspMode != 1) { %>
    <tr>
      <th>
        <gsmsg:write key="fil.14" />
      </th>
      <td>
      <logic:notEmpty name="fil030knForm" property="fil030knAdmList" scope="request">
      <logic:iterate id="admBean" name="fil030knForm" property="fil030knAdmList" scope="request" type="UsrLabelValueBean">
      <span class="<%= admBean.getCSSClassNameNormal() %>"><bean:write name="admBean" property="label" /></span><br>
      </logic:iterate>
      </logic:notEmpty>
      </td>
    </tr>
    <% } %>

    <% if (dspMode == 2) { %>
    <tr>
      <th>
        <gsmsg:write key="fil.19" />
      </th>
      <td>
        <logic:equal name="fil030knForm" property="file030ErrlAutoKbn" value="0">
          <gsmsg:write key="fil.fil290.3" />
        </logic:equal>
        <logic:equal name="fil030knForm" property="file030ErrlAutoKbn" value="1">
          <gsmsg:write key="fil.fil290.2" />
          <div class="mt10">
            <gsmsg:write key="fil.190" />
            <div class="ml20">
              <gsmsg:write key="fil.fil030.10" />
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder1" value="1">
                <span class="ml5"><gsmsg:write key="fil.fil030.16" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder1" value="2">
                <span class="ml5"><gsmsg:write key="fil.fil030.17" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder1" value="3">
                <span class="ml5"><gsmsg:write key="fil.fil030.18" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder1" value="4">
                <span class="ml5"><gsmsg:write key="user.35" /></span>
              </logic:equal>
            </div>
            <div class="ml20">
              <gsmsg:write key="fil.fil030.11" />
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder2" value="0">
                <span class="ml5"><gsmsg:write key="cmn.no" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder2" value="1">
                <span class="ml5"><gsmsg:write key="fil.fil030.16" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder2" value="2">
                <span class="ml5"><gsmsg:write key="fil.fil030.17" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder2" value="3">
                <span class="ml5"><gsmsg:write key="fil.fil030.18" /></span>
              </logic:equal>
              <logic:equal name="fil030knForm" property="file030ErrlAutoFolder2" value="4">
                <span class="ml5"><gsmsg:write key="user.35" /></span>
              </logic:equal>
            </div>
          </div>
          <logic:notEqual name="fil030knForm" property="file030ErrlAutoFolder2" value="0">
          <div class="ml20">
            <gsmsg:write key="fil.fil030.24" />
            <logic:equal name="fil030knForm" property="file030ErrlAutoFolder3" value="0">
              <span class="ml5"><gsmsg:write key="cmn.no" /></span>
            </logic:equal>
            <logic:equal name="fil030knForm" property="file030ErrlAutoFolder3" value="1">
              <span class="ml5"><gsmsg:write key="fil.fil030.16" /></span>
            </logic:equal>
            <logic:equal name="fil030knForm" property="file030ErrlAutoFolder3" value="2">
              <span class="ml5"><gsmsg:write key="fil.fil030.17" /></span>
            </logic:equal>
            <logic:equal name="fil030knForm" property="file030ErrlAutoFolder3" value="3">
              <span class="ml5"><gsmsg:write key="fil.fil030.18" /></span>
            </logic:equal>
            <logic:equal name="fil030knForm" property="file030ErrlAutoFolder3" value="4">
              <span class="ml5"><gsmsg:write key="user.35" /></span>
            </logic:equal>
          </div>
          </logic:notEqual>
        </div>
      </logic:equal>
      </td>
    </tr>
    <% } %>

    <tr>
      <th>
        <gsmsg:write key="fil.3" />
      </th>
      <td>
      <logic:equal name="fil030knForm" property="fil030CapaKbn" value="0">
        <gsmsg:write key="cmn.noset" />
      </logic:equal>

      <logic:notEqual name="fil030knForm" property="fil030CapaKbn" value="0">
        <gsmsg:write key="fil.4" /><gsmsg:write key="cmn.colon" /><bean:write name="fil030knForm" property="fil030knDspCapaSize" />
      <logic:notEqual name="fil030knForm" property="fil030CapaWarn" value="0"><br>
        <gsmsg:write key="fil.fil030kn.1" /><gsmsg:write key="cmn.colon" /><bean:write name="fil030knForm" property="fil030CapaWarn" />%
      </logic:notEqual>
      </logic:notEqual>
      </td>
    </tr>
    <logic:equal name="fil030knForm" property="admVerKbn" value="1" >
    <tr>
      <th>
        <gsmsg:write key="fil.5" />
      </th>
      <td>
        <% if (dspMode != 2) { %>
          <logic:equal name="fil030knForm" property="fil030VerAllKbn" value="1">
            <gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />：<bean:write name="fil030knForm" property="fil030VerKbn" /></span>
          </logic:equal>
          <logic:notEqual name="fil030knForm" property="fil030VerAllKbn" value="1">
            <gsmsg:write key="fil.fil030kn.5" />
          </logic:notEqual>
        <% } else { %>
          <gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />:<gsmsg:write key="fil.fil030.2" /></span>
        <% } %>
      </td>
    </tr>
    </logic:equal>
    <logic:notEqual name="fil030knForm" property="admVerKbn" value="1">
      <% if (dspMode != 2) { %>
        <tr>
          <th class="w20 no_w">
            <gsmsg:write key="fil.5" />
          </th>
     　   <td class="w80 txt_l"><gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />:<gsmsg:write key="fil.fil030.2" /></span></td>
        </tr>
      <% } %>
    </logic:notEqual>
    <logic:notEqual name="fil030knForm" property="cmnMode" value="2">
    <tr>
      <th>
        <gsmsg:write key="cmn.icon" />
      </th>
      <td>
        <logic:empty name="fil030knForm" property="fil030ImageName">
        <logic:equal name="fil030knForm" property="fil030PersonalFlg" value="<%= GSConstFile.DSP_CABINET_PUBLIC %>" >
          <img class="btn_classicImg-display wp30" src="../file/images/classic/icon_cabinet.gif" alt="<gsmsg:write key="cmn.icon" />">
          <img class="btn_originalImg-display wp30" src="../file/images/original/icon_cabinet_32.png" alt="<gsmsg:write key="cmn.icon" />">
        </logic:equal>
        <logic:equal name="fil030knForm" property="fil030PersonalFlg" value="<%= GSConstFile.DSP_CABINET_PRIVATE %>" >
          <img class="btn_classicImg-display wp30" src="../file/images/classic/30classic_icon_personal_cabinet.png" alt="<gsmsg:write key="cmn.icon" />">
          <img class="btn_originalImg-display wp30" src="../file/images/original/30original_icon_personal_cabinet.png" alt="<gsmsg:write key="cmn.icon" />">
        </logic:equal>
        </logic:empty>
        <logic:notEmpty name="fil030knForm" property="fil030ImageName">
        <logic:equal name="fil030knForm" property="cmnMode" value="0">
          <img class="wp30 bor1 borC_light" src="../file/fil030kn.do?CMD=getImageFile&fil010DspCabinetKbn=<bean:write name="fil030knForm" property="fil010DspCabinetKbn" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" onload="">
        </logic:equal>
        <logic:equal name="fil030knForm" property="cmnMode" value="1">
          <img class="wp30 bor1 borC_light" src="../file/fil030kn.do?CMD=getImageFile&cmnMode=<bean:write name="fil030knForm" property="cmnMode" />&fil030SelectCabinet=<bean:write name="fil030knForm" property="fil030SelectCabinet" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" onload="">
        </logic:equal>
        </logic:notEmpty>
      </td>
    </tr>
    </logic:notEqual>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <bean:write name="fil030knForm" property="fil030knDspBiko" filter="false"/>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil030knok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil030knback');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>