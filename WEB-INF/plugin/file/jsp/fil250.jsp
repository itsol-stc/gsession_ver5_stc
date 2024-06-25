<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<!DOCTYPE html>
<%
  String delOptFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DELETE_OPTION_FILE);
  String delOptFolderFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DELETE_OPTION_FOLDER_FILE);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.124" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>

<script src="../file/js/dtree.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil250.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>


<body>

  <html:form action="/file/fil250">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="backScreen" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="filSearchWd" />

    <logic:notEmpty name="fil250Form" property="fil040SelectDel" scope="request">
      <logic:iterate id="del" name="fil250Form" property="fil040SelectDel" scope="request">
        <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="fil250Form" property="fil010SelectDelLink" scope="request">
      <logic:iterate id="del" name="fil250Form" property="fil010SelectDelLink" scope="request">
        <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->

    <html:hidden property="backScreen" />
    <html:hidden property="selectDir" />
    <html:hidden property="sepKey" />
    <bean:define id="cabDirId" value=""/>
    <bean:define id="cabDirName" value=""/>
    <bean:define id="cabDirNoLink" value="0"/>
    <bean:define id="sepKey" name="fil250Form" property="sepKey" type="String"/>

    <logic:notEmpty name="fil250Form" property="treeFormLv0" scope="request">
      <logic:iterate id="lv0" name="fil250Form" property="treeFormLv0" scope="request" type="String">
        <logic:empty name="cabDirId">
         <% String[] sp = lv0.split(sepKey);
         %>
         <bean:define id="cabDirId" value="<%=sp[0] %>"/>
         <bean:define id="cabDirName" value="<%=sp[2] %>"/>
        </logic:empty>
        <input type="hidden" name="treeFormLv0" value="<bean:write name="lv0"/>">
      </logic:iterate>
    </logic:notEmpty>

    <bean:parameter id="fileTree" name="fileTree" value=""/>
    <input type="hidden" name="fileTree" value="<bean:write name="fileTree" />">

    <html:hidden property="fil250DirSid" />
    <html:hidden property="fil250RootDirSid" />
    <html:hidden property="fil250RootDirName" />

    <bean:parameter id="fil250tree" name="fil250tree" value="" />
    <input type="hidden" name="fil250tree" value="<bean:write name="fil250tree" />">

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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.124" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onclick="buttonPush('fil250ok');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil250back');">
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


      <table class="table-left" border="0" cellpadding="5">
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.folder" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div id="moveDirName" class="word_b-all">
              <bean:write name="fil250Form" property="fil250DirPath" />
            </div>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.23" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="w80 txt_l">
            <div>
              <label class="verAlignMid mr10">
                <html:radio property="fil250cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_PUBLIC) %>" styleClass="js_cabinetKbn" />
                <gsmsg:write key="fil.141" />
              </label>
              <label class="verAlignMid">
                <html:radio property="fil250cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>" styleClass="js_cabinetKbn" />
                <gsmsg:write key="fil.147" />
              </label>
            </div>
            <logic:notEmpty name="fil250Form" property="fil250cabinetList">
              <html:select name="fil250Form" property="fil250SltCabinetSid" styleClass="wp300" onchange="buttonPush('changeCabinet');">
                <html:optionsCollection name="fil250Form" property="fil250cabinetList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.fil230.5" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div class="hp300 ofx_s ofy_s no_w">
              <logic:notEmpty name="fil250Form" property="fil250SltCabinetSid">
                <logic:notEqual name="fil250Form" property="fil250SltCabinetSid" value="-1">
                  <div>
                    <a href="#!" onClick="file250TreeClick('detailDir', '<bean:write name="cabDirId" />')"><img class="classic-display mr5" src="../common/images/classic/icon_folder.png" alt=""><img class="original-display mr5" src="../common/images/original/icon_folder_box.png"  alt=""><bean:write name="cabDirName"  filter="false"/></a>
                  </div>
                  <ul id="tree" class="w100">
                  </ul>
                  <div class="js_tree_loader">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                    <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
                    <gsmsg:write key="cmn.loading"/>
                  </div>
                </logic:notEqual>
              </logic:notEmpty>
            </div>
          </td>

          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="fil.125" />
              <span class="cl_fontWarn">※</span>
            </span>
          </th>

          <td class="txt_l">
            <div>
              <gsmsg:write key="fil.fil250.1" />
            </div>

            <ui:usrgrpselector name="fil250Form" property="fil250CallUserUI" styleClass="hp215" />
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onclick="buttonPush('fil250ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil250back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>

  <filekanri:fileTreeParams screenId="fil250" />

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>