<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>

<%
  String delOptFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DELETE_OPTION_FILE);
  String delOptFolderFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DELETE_OPTION_FOLDER_FILE);
  String maxLengthValue = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAX_LENGTH_FILE_UP_CMT);
%>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.fil230.4" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/fil230.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body onload="showLengthId($('#input_area_plain')[0], <%=maxLengthValue%>, 'inputlength');">

<html:form action="/file/fil230">
<input type="hidden" name="CMD" value="">
<html:hidden property="backDsp" />
<html:hidden property="backScreen" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="filSearchWd" />

<logic:notEmpty name="fil230Form" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil230Form" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil230Form" property="fil010SelectDelLink" scope="request">
  <logic:iterate id="del" name="fil230Form" property="fil010SelectDelLink" scope="request">
    <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<html:hidden property="backScreen" />
<html:hidden property="selectDir" />
<html:hidden property="sepKey" />
<bean:define id="cabDirId" value=""/>
<bean:define id="cabDirName" value=""/>
<bean:define id="cabDirNoLink" value="0"/>
<bean:define id="sepKey" name="fil230Form" property="sepKey" type="String"/>

<logic:notEmpty name="fil230Form" property="treeFormLv0" scope="request">
  <logic:iterate id="lv0" name="fil230Form" property="treeFormLv0" scope="request" type="String">
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


<html:hidden property="fil230DeleteDirSid" />
<html:hidden property="fil230RootDirSid" />
<html:hidden property="fil230RootDirName" />

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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.fil230.4" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil230delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil230back');">
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
              <gsmsg:write key="fil.36" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div id="moveDirName" class="word_b-all">
              <bean:write name="fil230Form" property="fil230DeleteDir" />
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
                <html:radio property="fil230cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_PRIVATE) %>" styleClass="js_cabinetKbn" />
                <gsmsg:write key="fil.142" />
              </label>
              <label class="verAlignMid mr10">
                <html:radio property="fil230cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_PUBLIC) %>" styleClass="js_cabinetKbn" />
                <gsmsg:write key="fil.141" />
              </label>
              <label class="verAlignMid">
                <html:radio property="fil230cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>" styleClass="js_cabinetKbn" />
                <gsmsg:write key="fil.147" />
              </label>
            </div>
            <logic:notEmpty name="fil230Form" property="fil230cabinetList">
              <html:select name="fil230Form" property="fil230SltCabinetSid" styleClass="wp300" onchange="buttonPush('changeCabinet');">
                <html:optionsCollection name="fil230Form" property="fil230cabinetList" value="value" label="label" />
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

              <logic:notEqual name="fil230Form" property="fil230SltCabinetSid" value="-1">
                <div>
                  <a href="#!" onClick="file230TreeClick('detailDir', '<bean:write name="cabDirId" />')"><img class="classic-display mr5" src="../common/images/classic/icon_folder.png" alt=""><img class="original-display mr5" src="../common/images/original/icon_folder_box.png"  alt=""><bean:write name="cabDirName"  filter="false"/></a>
                </div>
                <ul id="tree" class="w100">
                </ul>
                <div class="js_tree_loader">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                  <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
                  <gsmsg:write key="cmn.loading"/>
                </div>
              </logic:notEqual>
            </div>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="fil.35" />
            </span>
          </th>
          <td class="txt_l">

            <span class="verAlignMid">
            <html:radio property="fil230DeleteOpt" value="<%=delOptFile%>" styleId="delOpt_00" />
              <label for="delOpt_00">
                <gsmsg:write key="fil.99" />
              </label>
            </span>
            &nbsp;
            <span class="verAlignMid">
            <html:radio property="fil230DeleteOpt" value="<%= delOptFolderFile %>" styleId="delOpt_01" />
              <label for="delOpt_01">
                <gsmsg:write key="fil.37" />
              </label>
            </span>
            &nbsp; <br>
            <span class="cl_fontWarn">
              ※
              <logic:equal name="fil230Form" property="fil230cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
                <gsmsg:write key="fil.fil230.7" />
              </logic:equal>
              <logic:notEqual name="fil230Form" property="fil230cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
                <gsmsg:write key="fil.fil230.6" />
              </logic:notEqual>
            </span>
          </td>
        </tr>

        <logic:equal name="fil230Form" property="fil230cabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.11" />
            </span>
          </th>
          <td class="w80 txt_l">
            <textarea name="fil230Comment" class="w100" rows="5" id="input_area_plain" onkeyup="showLengthStr(value, <%=maxLengthValue%>, 'inputlength');"><bean:write name="fil230Form" property="fil230Comment" /></textarea>
            <div id="plain_text_count" class="fs_13">
              <gsmsg:write key="cmn.current.characters" />:<span class="formCounter" id="inputlength">0</span>&nbsp;/&nbsp;<%=maxLengthValue%>
              <gsmsg:write key="cmn.character" />
            </div>
          </td>
        </tr>
        </logic:equal>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil230delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil230back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

</html:form>

<filekanri:fileTreeParams screenId="fil230" />

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>