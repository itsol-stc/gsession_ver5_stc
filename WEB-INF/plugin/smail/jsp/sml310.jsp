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
<title>GROUPSESSION <gsmsg:write key="wml.wml110.01" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml310.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<html:form action="/smail/sml310">


<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlCmdMode" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlLabelCmdMode" />
<html:hidden property="smlEditLabelId" />
<html:hidden property="dspCount" />

<logic:notEmpty name="sml310Form" property="lbnList" scope="request">
  <logic:iterate id="sort" name="sml310Form" property="lbnList" scope="request">
    <input type="hidden" name="sml310sortLabel" value="<bean:write name="sort" property="lbValue" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.preferences2" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml110.01" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addLabel();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('smlUserAccountList');">
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
        <th class="w25 ">
          <gsmsg:write key="wml.28" />
        </th>
        <td class="w75">
          <bean:write name="sml310Form" property="sml310accountName" />
        </td>
      </tr>
    </table>

    <div class="txt_l">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upLabelData');">
        <gsmsg:write key="cmn.up" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downLabelData');">
        <gsmsg:write key="cmn.down" />
      </button>
    </div>

    <table class="table-top table-fixed w100">
      <tr>
        <th class="wp25 table_title-color">
          &nbsp;
        </th>
        <th class="w65 table_title-color txt_c">
          <gsmsg:write key="wml.74" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="cmn.fixed" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="cmn.delete" />
        </th>
      </tr>
      <logic:notEqual name="sml310Form" property="smlAccountSid" value="-1">
        <logic:iterate id="lbnData" name="sml310Form" property="lbnList" indexId="idx">
          <bean:define id="lbValue" name="lbnData" property="lbValue" />
          <% String labelCheckId = "chkLabel" + String.valueOf(idx.intValue()); %>
          <tr>
            <td class="w5 txt_c cursor_p" onClick="this.firstChild.checked=true;"><html:radio property="sml310SortRadio" value="<%= String.valueOf(lbValue) %>" styleId="<%= labelCheckId %>" /></td>
            <td class="w65 txt_l" onClick="sml310CheckLabel('<%= labelCheckId %>');">
              <bean:write name="lbnData" property="labelName" />
            </td>
            <td class="w15 txt_c  no_w plr0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.fixed" />" onClick="editLabel('<bean:write name="lbnData" property="labelSid" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
                <gsmsg:write key="cmn.fixed" />
              </button>
            </td>
            <td class="w15 txt_c  no_w plr0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="deleteLabel('<bean:write name="lbnData" property="labelSid" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </td>
          </tr>
        </logic:iterate>
      </logic:notEqual>
    </table>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>