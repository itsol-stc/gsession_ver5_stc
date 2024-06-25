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
  <title>GROUPSESSION<gsmsg:write key="wml.wml100.02" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../smail/js/sml270.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body>

<html:form action="/smail/sml270">

<input type="hidden" name="CMD" value="">

<html:hidden property="smlCmdMode" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlAccountSid" />
<html:hidden property="backScreen" />

<logic:notEmpty name="sml270Form" property="accountList" scope="request">
  <logic:iterate id="sort" name="sml270Form" property="accountList" scope="request">
    <input type="hidden" name="sml270sortLabel" value="<bean:write name="sort" property="acValue" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <li>
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.preferences2" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.100" />
        </li>
        <li>
          <div>
            <logic:equal name="sml270Form" property="sml270MakeAcntHnt" value="0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="accountConf(0, 0);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('psnTool');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="txt_l">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upFilterData');">
        <gsmsg:write key="cmn.up" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downFilterData');">
        <gsmsg:write key="cmn.down" />
      </button>
    </div>

    <table class="table-top table-fixed w100">
      <tr>
        <th class="wp25 table_title-color txt_c">
          &nbsp;
        </th>
        <th class="w25 table_title-color txt_c">
          <gsmsg:write key="wml.96" />
        </th>
        <th class="table_title-color txt_c">
          <gsmsg:write key="cmn.memo" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="cmn.edit" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="cmn.label" />
        </th>
        <th class="wp80 table_title-color txt_c">
          <gsmsg:write key="wml.248" />
        </th>
      </tr>
      <logic:iterate id="acuntData" name="sml270Form" property="accountList">
        <bean:define id="acValue" name="acuntData" property="acValue" />
        <bean:define id="mukoUserClass"  >&nbsp;</bean:define>
        <logic:equal name="acuntData" property="usrUkoFlg" value="1">
          <bean:define id="mukoUserClass"  >mukoUser</bean:define>
        </logic:equal>
        <tr>
          <td class="txt_c no_w cursor_p" onClick="this.firstChild.checked=true"><html:radio property="sml270sortAccount" value="<%= String.valueOf(acValue) %>" /></td>
          <td class="w25"><span class="<%=mukoUserClass %>"><bean:write name="acuntData" property="accountName" /></span></td>
          <td class=""><bean:write name="acuntData" property="accountBiko" filter="false" /></td>
          <td class=" txt_c plr0">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onClick="return accountEdit(1, <bean:write name="acuntData" property="accountSid" />);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.edit" />
            </button>
          </td>
          <td class="txt_c plr0">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.label" />" onClick="confLabel(<bean:write name="acuntData" property="accountSid" />);">
              <gsmsg:write key="cmn.label" />
            </button>
          </td>
          <td class="txt_c plr0">
            <button type="button" class="baseBtn pr5 pl5" value="<gsmsg:write key="wml.248" />" onClick="confFilter(<bean:write name="acuntData" property="accountSid" />);">
              <gsmsg:write key="wml.248" />
            </button>
          </td>
        </tr>
      </logic:iterate>
    </table>
    <div class="txt_r footerBtn_block">
      <logic:equal name="sml270Form" property="sml270MakeAcntHnt" value="0">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="accountConf(0, 0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('psnTool');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>