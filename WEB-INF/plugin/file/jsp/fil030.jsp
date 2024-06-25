<%@page import="org.apache.lucene.search.FieldComparator.StringValComparator"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>

<%
  String maxLengthBiko = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAX_LENGTH_BIKO);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.51" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil030.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>


<logic:equal name="fil030Form" property="admVerKbn" value="1">
  <body onload="showOrHide();setVersionComboStatus();showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');">
</logic:equal>
<logic:notEqual name="fil030Form" property="admVerKbn" value="1">
  <body onload="showOrHide();showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');">
</logic:notEqual>

<html:form action="/file/fil030">
  <input type="hidden" name="CMD" value="">
  <html:hidden property="cmnMode" />
  <html:hidden property="fil010DspCabinetKbn" />
  <html:hidden property="backDsp" />
  <html:hidden property="backScreen" />
  <html:hidden property="filSearchWd" />
  <html:hidden property="admVerKbn" />
  <html:hidden property="fil010SelectCabinet" />
  <html:hidden property="fil030SelectCabinet" />
  <html:hidden property="fil010SelectDirSid" />
  <html:hidden property="fil030binSid" />
  <html:hidden property="fil030ImageName" />
  <html:hidden property="fil030ImageSaveName" />
  <html:hidden property="fil030InitFlg" />
  <html:hidden property="fil030PersonalFlg" />
  <html:hidden property="ownerSid" />
  <logic:equal name="fil030Form" property="cmnMode" value="2">
    <html:hidden name="fil030Form" property="fil030CabinetName" />
  </logic:equal>

  <html:hidden property="fil220cabinetKbn" />
  <html:hidden property="fil280svKeyword" />
  <html:hidden property="fil280svGroup" />
  <html:hidden property="fil280svUser" />

  <logic:notEmpty name="fil030Form" property="fil220sltCheck" scope="request">
    <logic:iterate id="select" name="fil030Form" property="fil220sltCheck" scope="request">
      <input type="hidden" name="fil220sltCheck" value="<bean:write name="select" />">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="fil030Form" property="fil010SelectDelLink" scope="request">
    <logic:iterate id="item" name="fil030Form" property="fil010SelectDelLink" scope="request">
      <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="fil030Form" property="fil040SelectDel" scope="request">
    <logic:iterate id="del" name="fil030Form" property="fil040SelectDel" scope="request">
      <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
    </logic:iterate>
  </logic:notEmpty>

  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
  <input type="hidden" name="helpPrm" value="<bean:write name="fil030Form" property="cmnMode" /><bean:write name="fil030Form" property="fil010DspCabinetKbn"/>">


  <div class="pageTitle w85 mrl_auto">
    <ul>
      <li>
        <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
      </li>
      <li>
        <gsmsg:write key="cmn.filekanri" />
      </li>
      <% int dspMode = 0; %>
      <logic:equal name="fil030Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
        <% dspMode = Integer.parseInt(jp.groupsession.v2.fil.GSConstFile.DSP_CABINET_PRIVATE); %>
      </logic:equal>
      <logic:equal name="fil030Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
        <% dspMode = Integer.parseInt(jp.groupsession.v2.fil.GSConstFile.DSP_CABINET_PUBLIC); %>
      </logic:equal>
      <logic:equal name="fil030Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
        <% dspMode = Integer.parseInt(jp.groupsession.v2.fil.GSConstFile.DSP_CABINET_ERRL); %>
      </logic:equal>

      <bean:define id="pageTitle"><gsmsg:write key="fil.191" /></bean:define>
      <logic:notEqual name="fil030Form" property="cmnMode" value="<%= GSConstFile.CMN_MODE_ADD %>">
        <bean:define id="pageTitle"><gsmsg:write key="fil.51" /></bean:define>
      </logic:notEqual>

      <li class="pageTitle_subFont">
        <% if (dspMode == 0) { %>
          <gsmsg:write key="cmn.share" /><bean:write name="pageTitle" />
        <% } else if (dspMode == 1) { %>
          <gsmsg:write key="cmn.individual" /><bean:write name="pageTitle" />
        <% } else if (dspMode == 2) { %>
          <gsmsg:write key="fil.errl" /><bean:write name="pageTitle" />
        <% } %>
      </li>
      <li>
        <div>
          <logic:notEqual name="fil030Form" property="cmnMode" value="1">
            <logic:notEqual name="fil030Form" property="cmnMode" value="2">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="buttonPush('fil030edit');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                <gsmsg:write key="cmn.entry" />
              </button>
            </logic:notEqual>
            <logic:equal name="fil030Form" property="cmnMode" value="2">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil030edit');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
            </logic:equal>
          </logic:notEqual>
          <logic:equal name="fil030Form" property="cmnMode" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil030edit');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.edit" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil030delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil030back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </li>
    </ul>
  </div>

  <% if (dspMode == 2) { %>
    <div class="w85 mrl_auto mb10">
      <span class="cl_fontWarn"><gsmsg:write key="fil.fil030.5" /></span>
    </div>
  <% } %>

  <div class="wrapper w85 mrl_auto">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <table class="table-left" cellpadding="5">
      <tr>
        <th class="no_w">
          <% if (dspMode != 1) { %>
            <span>
              <gsmsg:write key="fil.13" />
              <logic:notEqual name="fil030Form" property="cmnMode" value="2">
                <span class="cl_fontWarn">※</span>
              </logic:notEqual>
            </span>
          <% } else { %>
            <span>
              <gsmsg:write key="fil.146" />
            </span>
          <% } %>
        </th>
        <td class="txt_l">
          <logic:notEqual name="fil030Form" property="cmnMode" value="2">
            <% if (dspMode != 1) { %>
              <html:text name="fil030Form" maxlength="70" property="fil030CabinetName" styleClass="wp300" />
            <% } else { %>
              <html:hidden name="fil030Form" property="fil030CabinetName" />
              <bean:write name="fil030Form" property="fil030CabinetName" />
            <% } %>
          </logic:notEqual>
          <logic:equal name="fil030Form" property="cmnMode" value="2">
            <bean:write name="fil030Form" property="fil030CabinetName" />
          </logic:equal>
        </td>
      </tr>

      <% if (dspMode != 1) { %>
        <tr>
          <th class="txt_m txt_l no_w">
            <span>
              <gsmsg:write key="fil.102" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_m txt_l w100">
            <span class="verAlignMid">
              <html:radio name="fil030Form" property="fil030AccessKbn" styleId="okini0" value="0" onclick="showOrHide();" />
              <label for="okini0">
                <gsmsg:write key="cmn.not.limit" />
              </label>
            </span>
            <span class="verAlignMid ml5">
              <html:radio name="fil030Form" property="fil030AccessKbn" styleId="okini1" value="1" onclick="showOrHide();" />
              <label for="okini1">
                <gsmsg:write key="cmn.do.limit" />
              </label>
            </span>
            <logic:notEqual name="fil030Form" property="cmnMode" value="<%=GSConstFile.CMN_MODE_ADD%>">
              <span class="verAlignMid ml5">
                <html:checkbox name="fil030Form" property="file030AdaptIncFile" value="1" styleId="adaptIncFile" />
                <label for="adaptIncFile">
                  <gsmsg:write key="fil.127" />
                </label>
              </span>
            </logic:notEqual>
      <% } else { %>
        <tr>
          <th class="txt_m txt_l no_w">
            <span>
              <gsmsg:write key="fil.153" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_m txt_l w100">
            <span class="verAlignMid">
              <html:radio name="fil030Form" property="fil030AccessKbn" styleId="okini0" value="0" onclick="showOrHide();" />
              <label for="okini0">
                <gsmsg:write key="cmn.not.permit" />
              </label>
            </span>
            <span class="verAlignMid ml5">
              <html:radio name="fil030Form" property="fil030AccessKbn" styleId="okini1" value="1" onclick="showOrHide();" />
              <label for="okini1">
                <gsmsg:write key="cmn.permit" />
              </label>
            </span>
      <% } %>

      <div id="hide0"></div>
      <div id="show0">
        <% if (dspMode != 1) { %>
          <%-- アクセス権限 共有キャビネット --%>
          <ui:usrgrpselector name="fil030Form" property="fil030AccessUserUI" styleClass="hp300" />
        <% } else { %>
          <%-- 個人キャビネット --%>
          <ui:usrgrpselector name="fil030Form" property="fil030PrivateAccessUserUI" styleClass="hp215" />
        <% } %>
      </div>
      </td>
      </tr>

      <% if (dspMode != 1) { %>
        <tr>
          <th valign="middle" align="left" class="td_sub_title3" width="0%" nowrap>
            <span>
              <gsmsg:write key="fil.14" />
            </span>
          </th>
          <td class="txt_m txt_l w100">
            <div>
              <gsmsg:write key="fil.fil030.1" />
            </div>

            <ui:usrgrpselector name="fil030Form" property="fil030AdminUserUI" styleClass="hp215" />
          </td>
        </tr>
      <% } %>

      <%-- 保存先振り分け機能 --%>
      <% if (dspMode == 2) { %>
        <tr>
          <th class="w20 no_w">
            <gsmsg:write key="fil.19" />
            <span class="cl_fontWarn">※</span>
          </th>
          <td>
            <div>
              <gsmsg:write key="fil.fil030.9" />
            </div>
            <div>
              <span class="verAlignMid">
                <html:radio name="fil030Form" property="file030ErrlAutoKbn" styleId="errlAutoDisabled" value="0" onclick="changeErrlAutoDsp();" />
                <label for="errlAutoDisabled">
                  <gsmsg:write key="fil.fil290.3" />
                </label>
                <html:radio name="fil030Form" property="file030ErrlAutoKbn" styleId="errlAutoEnabled" styleClass="ml5" value="1" onclick="changeErrlAutoDsp();" />
                <label for="errlAutoEnabled">
                  <gsmsg:write key="fil.fil290.2" />
                </label>
              </span>
            </div>

            <div class="mt10 js_autoFolder">
              <gsmsg:write key="fil.190" />
            </div>
            <div class="mt5 ml20 js_autoFolder">
              <gsmsg:write key="fil.fil030.10" />
              <logic:notEmpty name="fil030Form" property="file030ErrlAutoFolderList1">
                <html:select property="file030ErrlAutoFolder1" onchange="changeErrlAutoList();">
                  <html:optionsCollection name="fil030Form" property="file030ErrlAutoFolderList1" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </div>
            <div class="mt5 ml20 js_autoFolder">
              <gsmsg:write key="fil.fil030.11" />
              <logic:notEmpty name="fil030Form" property="file030ErrlAutoFolderList2">
                <html:select property="file030ErrlAutoFolder2" onchange="changeErrlAutoList();">
                  <html:optionsCollection name="fil030Form" property="file030ErrlAutoFolderList2" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </div>
            <div class="mt5 ml20 js_autoFolder">
              <gsmsg:write key="fil.fil030.24" />
              <logic:notEmpty name="fil030Form" property="file030ErrlAutoFolderList3">
                <html:select property="file030ErrlAutoFolder3" onchange="changeErrlAutoList();">
                  <html:optionsCollection name="fil030Form" property="file030ErrlAutoFolderList3" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </div>
            <div class="mt10 js_autoFolder js_autoFolderText">
              <gsmsg:write key="fil.fil030.12" />/<span class="cl_fontWarn"><gsmsg:write key="fil.fil030.13" /></span>/<gsmsg:write key="fil.fil030.15" />
            </div>
          </td>
        </tr>

      <% } %>

      <%-- 容量設定 --%>
      <tr>
        <%-- 共有キャビネット --%>
        <% if (dspMode != 1) { %>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.3" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="w80 txt_l">
            <span class="verAlignMid">
              <html:radio name="fil030Form" property="fil030CapaKbn" styleId="disksize0" value="0" onclick="showOrHide();" />
              <label for="disksize0">
                <gsmsg:write key="cmn.noset" />
              </label>
            </span>
            <span class="verAlignMid">
              <html:radio name="fil030Form" property="fil030CapaKbn" styleId="disksize1" value="1" onclick="showOrHide();" />
              <label for="disksize1">
                <gsmsg:write key="cmn.setting.do" />
              </label>
            </span>
        <% } else { %>
          <%-- 個人キャビネット --%>
          <html:hidden property="fil030CapaKbn" />
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.3" />
            </span>
          </th>
          <td class="w80 txt_l">
            <logic:equal name="fil030Form" property="fil030CapaKbn" value="<%=String.valueOf(GSConstFile.CAPA_KBN_OFF)%>">
              <span>
                <gsmsg:write key="cmn.noset" />
              </span>
            </logic:equal>
        <% } %>
        <div id="hide1"></div>

        <div id="show1">
          <%-- 共有キャビネット --%>
          <% if (dspMode != 1) { %>
            <div>
              <span>
                <gsmsg:write key="fil.4" />
                ：
              </span>
              <html:text name="fil030Form" maxlength="8" property="fil030CapaSize" styleClass="wp70" />
              <span>MB</span>
            </div>
          <% } else { %>
            <%-- 個人キャビネット --%>
            <html:hidden property="fil030CapaSize" />
            <logic:equal name="fil030Form" property="fil030CapaKbn" value="<%=String.valueOf(GSConstFile.CAPA_KBN_ON)%>">
              <span>
                <gsmsg:write key="fil.4" />：
                <span class="fw_b">
                  <bean:write name="fil030Form" property="fil030CapaSize" />MB
                </span>
              </span>
              <br>
            </logic:equal>
          <% } %>

          <%-- 共有キャビネット --%>
          <% if (dspMode != 1) { %>
            <span>
              <gsmsg:write key="fil.fil030kn.1" />：
            </span>
            <html:select name="fil030Form" property="fil030CapaWarn" styleClass="wp90 mt5">
              <logic:notEmpty name="fil030Form" property="fil030CapaWarnLavel">
                <html:optionsCollection name="fil030Form" property="fil030CapaWarnLavel" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
            <br>
            <span class="cl_fontWarn">※
              <gsmsg:write key="cmn.not.specified.nowarn" />
            </span>
          <% } else { %>
            <%-- 個人キャビネット --%>
            <html:hidden property="fil030CapaWarn" />
            <logic:equal name="fil030Form" property="fil030CapaKbn" value="<%=String.valueOf(GSConstFile.CAPA_KBN_ON)%>">
              <span>
                <gsmsg:write key="fil.fil030kn.1" />：
                <span class="fw_b">
                  <bean:write name="fil030Form" property="fil030CapaWarn" />%
                </span>
              </span>
            </logic:equal>
          <% } %>
        </div>

        </td>
      </tr>

      <%-- バージョン管理 --%>
      <logic:equal name="fil030Form" property="admVerKbn" value="1">
        <%-- 共有キャビネット --%>
        <% if (dspMode != 1) { %>
          <tr>
            <th class="w20 no_w">
              <gsmsg:write key="fil.5" />
            </th>
            <% if (dspMode == 0) { %>
            <td class="w80 txt_l">
              <div class="verAlignMid">
              <span class="verAlignMid">
                <html:checkbox name="fil030Form" property="fil030VerAllKbn" value="1" styleId="select_version" onclick="return setVersionComboStatus();" />
                <label for="select_version">
                  <gsmsg:write key="fil.15" />
                </label>
              </span>
              <span>
                &nbsp;&nbsp;
                <gsmsg:write key="fil.fil030.3" />：
              </span>
              <html:select name="fil030Form" property="fil030VerKbn" styleClass="select01" style="width: 80px;">
                <logic:notEmpty name="fil030Form" property="fil030VerKbnLavel">
                  <html:optionsCollection name="fil030Form" property="fil030VerKbnLavel" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
              </div>
            </td>
            <% } else if (dspMode == 2) { %>
              <td class="w80 txt_l"><gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />:<gsmsg:write key="fil.fil030.2" /></span></td>
            <% } %>
          </tr>
        <% } else { %>
          <%-- 個人キャビネット --%>
          <html:hidden property="fil030VerKbn" />
          <html:hidden property="fil030VerAllKbn" />
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="fil.5" />
              </span>
            </th>
            <td class="w80 txt_l">
              <span>
                <gsmsg:write key="fil.15" />
                <span class="ml20">
                  <gsmsg:write key="fil.fil030.3" />：
                  <bean:write name="fil030Form" property="fil030VerKbn" />
                </span>
              </span>
            </td>
          </tr>
        <% } %>
      </logic:equal>
      <logic:notEqual name="fil030Form" property="admVerKbn" value="1">
        <%-- 電帳法キャビネット --%>
        <% if (dspMode == 2) { %>
          <tr>
            <th class="w20 no_w">
              <gsmsg:write key="fil.5" />
            </th>
            <td class="w80 txt_l"><gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />:<gsmsg:write key="fil.fil030.2" /></span></td>
          </tr>
        <% } %>
      </logic:notEqual>

      <logic:notEqual name="fil030Form" property="cmnMode" value="2">
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.icon" />
            </span>
          </th>
          <td class="w80 txt_l">
            <logic:empty name="fil030Form" property="fil030ImageName">
              <% if (dspMode != 1) { %>
                <img class="btn_classicImg-display wp30" src="../file/images/classic/icon_cabinet.gif">
                <img class="btn_originalImg-display wp30" src="../file/images/original/icon_cabinet_32.png">
              <% } else { %>
                <img class="btn_classicImg-display wp30" src="../file/images/classic/30classic_icon_personal_cabinet.png">
                <img class="btn_originalImg-display wp30" src="../file/images/original/30original_icon_personal_cabinet.png">
              <% } %>
            </logic:empty>
            <logic:notEmpty name="fil030Form" property="fil030ImageName">
              <logic:equal name="fil030Form" property="cmnMode" value="0">
                <img class="wp30" src="../file/fil030.do?CMD=getImageFile&fil010DspCabinetKbn=<bean:write name="fil030Form" property="fil010DspCabinetKbn" />" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />aa" border="1" onload="">
              </logic:equal>
              <logic:equal name="fil030Form" property="cmnMode" value="1">
                <img class="wp30" src="../file/fil030.do?CMD=getImageFile&cmnMode=<bean:write name="fil030Form" property="cmnMode" />&fil030SelectCabinet=<bean:write name="fil030Form" property="fil030SelectCabinet" />" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />bb" border="1" onload="">
              </logic:equal>
            </logic:notEmpty>
            <div>
              <gsmsg:write key="cmn.icon.size" />
            </div>
            <div>
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_TANITU_FIL030) %>"
                pluginId="<%= GSConstFile.PLUGIN_ID_FILE %>"
                tempDirId="fil030"
                delBtn="true"
                delBtnAction="buttonPush('fil030tempdeleteMark');"
                fileList="false" />
            </div>
          </td>
        </tr>
      </logic:notEqual>

      <tr>
        <th class="w20 no_w">
          <span>
            <gsmsg:write key="cmn.memo" />
          </span>
        </th>
        <td class="w80 txt_l">
          <textarea name="fil030Biko" rows="5" class="wp500" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength');" id="inputstr"><bean:write name="fil030Form" property="fil030Biko" /></textarea>
          <br>
          <span class="formCounter">
            <gsmsg:write key="cmn.current.characters" />:
          </span>
          <span id="inputlength" class="formCounter">0</span>
          <span class="formCounter_max">/<%= maxLengthBiko %><gsmsg:write key="cmn.character" />
          </span>
        </td>
      </tr>
    </table>

    <div class="footerBtn_block">
      <logic:notEqual name="fil030Form" property="cmnMode" value="1">
        <logic:notEqual name="fil030Form" property="cmnMode" value="2">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="buttonPush('fil030edit');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <gsmsg:write key="cmn.entry" />
          </button>
        </logic:notEqual>
        <logic:equal name="fil030Form" property="cmnMode" value="2">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil030edit');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
        </logic:equal>
      </logic:notEqual>
      <logic:equal name="fil030Form" property="cmnMode" value="1">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil030edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
          <gsmsg:write key="cmn.edit" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil030delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil030back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>