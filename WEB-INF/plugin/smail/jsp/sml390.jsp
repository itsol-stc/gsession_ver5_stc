<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui/" prefix="ui" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>



<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION
<logic:equal name="sml390Form" property="sml380EditBan" value="0"><gsmsg:write key="sml.sml390.01" /></logic:equal>
<logic:notEqual name="sml390Form" property="sml380EditBan" value="0"><gsmsg:write key="sml.sml390.02" /></logic:notEqual>
</title>
</head>

<body>
<html:form action="/smail/sml390">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="sml380EditBan" />
<html:hidden property="sml390initFlg" />

<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />


<logic:notEmpty name="sml390Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml390Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml380keyword" />
<html:hidden property="sml380svKeyword" />
<html:hidden property="sml380sortKey" />
<html:hidden property="sml380order" />
<html:hidden property="sml380searchFlg" />
<html:hidden property="sml380pageTop" />

<logic:equal name="sml390Form" property="sml380EditBan" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="sml390Form" property="sml380EditBan" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont verAlignMid">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span>
          <logic:equal name="sml390Form" property="sml380EditBan" value="0">
            <gsmsg:write key="sml.sml390.01" />
          </logic:equal>
          <logic:notEqual name="sml390Form" property="sml380EditBan" value="0">
            <gsmsg:write key="sml.sml390.02" />
          </logic:notEqual>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="OK" onClick="buttonPush('ok');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:notEqual name="sml390Form" property="sml380EditBan" value="0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:notEqual>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>

    <table class="table-left w100">
      <tr>
        <th class="w25">
          <gsmsg:write key="sml.sml380.03" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">
          <html:text styleClass="wp350" maxlength="50" name="sml390Form" property="sml390sbcName" />
        </td>
      </tr>
      <tr>
        <%--対象送信先 --%>
        <th class="w25">
          <gsmsg:write key="sml.sml380.04" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">
          <ui:usrgrpselector name="sml390Form" property="sml390sbdTargetSelector" styleClass="hp250" />
          <ui:multiselector name="sml390Form" property="sml390sbdTargetAccSelector" styleClass="hp250" />
        </td>
      </tr>
      <tr>
        <%--送信許可 --%>
        <th class="w25">
          <gsmsg:write key="sml.sml390.03" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="w75">
          <div class="verAlignMid">
            <gsmsg:write key="cmn.post" />:
            <html:select name="sml390Form" property="sml390post" styleClass="wp150">
              <logic:notEmpty name="sml390Form" property="postCombo">
                <html:optionsCollection name="sml390Form" property="postCombo" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </div>
          <ui:usrgrpselector name="sml390Form" property="sml390sbpTargetSelector" styleClass="hp250" />
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.memo" />
        </th>
        <td class="w75">
          <html:textarea name="sml390Form" property="sml390biko"  rows="5" styleClass="wp600" />
        </td>
      </tr>
    </table>
    <div class="txt_r footerBtn_block">
      <button type="button" class="baseBtn" value="OK" onClick="buttonPush('ok');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:notEqual name="sml390Form" property="sml380EditBan" value="0">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:notEqual>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>


</body>
</html:html>