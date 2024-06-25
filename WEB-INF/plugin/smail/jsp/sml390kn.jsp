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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION
<logic:equal name="sml390knForm" property="sml380EditBan" value="0"><gsmsg:write key="sml.sml390.01" /></logic:equal>
<logic:notEqual name="sml390knForm" property="sml380EditBan" value="0"><gsmsg:write key="sml.sml390.02" /></logic:notEqual>
</title>
</head>

<body>
<html:form action="/smail/sml390kn">
<input type="hidden" name="CMD" value="">

<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="sml380EditBan" />
<html:hidden property="sml390sbcName" />
<html:hidden property="sml390biko" />
<html:hidden property="sml390initFlg" />

<logic:notEmpty name="sml390knForm" property="sml390sbpTarget" scope="request">
  <logic:iterate id="sid" name="sml390knForm" property="sml390sbpTarget" scope="request">
    <input type="hidden" name="sml390sbpTarget" value="<bean:write name="sid"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="sml390post" />
<html:hidden property="sml390banGroup" />
<html:hidden property="sml390ableGroup" />

<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />


<logic:notEmpty name="sml390knForm" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml390knForm" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml380keyword" />
<html:hidden property="sml380svKeyword" />
<html:hidden property="sml380sortKey" />
<html:hidden property="sml380order" />
<html:hidden property="sml380searchFlg" />
<html:hidden property="sml380pageTop" />

<logic:equal name="sml390knForm" property="sml380EditBan" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="sml390knForm" property="sml380EditBan" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->

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
          <logic:equal name="sml390knForm" property="sml380EditBan" value="0">
            <gsmsg:write key="sml.sml390kn.01" />
          </logic:equal>
          <logic:notEqual name="sml390knForm" property="sml380EditBan" value="0">
            <gsmsg:write key="sml.sml390kn.02" />
          </logic:notEqual>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="cmn.final">
              <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="cmn.final">
              <gsmsg:write key="cmn.final" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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
          <gsmsg:write key="sml.sml380.03" />
        </th>
        <td class="w75">
          <bean:write name="sml390knForm" property="sml390sbcName" />
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="sml.sml380.04" />
        </th>
        <td class="w75">
          <div>
            <logic:iterate id="banAccountData" name="sml390knForm" property="sml390sbdTargetSelectCombo">
              <bean:define id="mukoUserClass">&nbsp;</bean:define>
              <logic:equal name="banAccountData" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass">mukoUser</bean:define></logic:equal>
              <div class="atesaki_to_area <bean:write name="mukoUserClass"/>">
                <input type="hidden" name="sml390sbdTarget" value="<bean:write name="banAccountData" property="value" />">
                <bean:write name="banAccountData" property="label" />
              </div>
            </logic:iterate>
          </div>
          <logic:notEmpty name="sml390knForm" property="sml390sbdTargetAccSelectCombo">
          <div class="settingForm_separator">
            <span class="fs_13"><gsmsg:write key="sml.189" /></span>
            <logic:iterate id="banAccountData" name="sml390knForm" property="sml390sbdTargetAccSelectCombo">
              <div class="atesaki_to_area">
                <input type="hidden" name="sml390sbdTargetAcc" value="<bean:write name="banAccountData" property="value" />">
                <bean:write name="banAccountData" property="label" />
              </div>
            </logic:iterate>
          </div>
          </logic:notEmpty>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="sml.sml390.03" />
        </th>
        <td class="w75">
          <logic:greaterEqual value="0" name="sml390knForm" property="sml390post">
            <div>
              <gsmsg:write key="cmn.post" />:<bean:write name="sml390knForm" property="sml390knDspPosition" />
            </div>
          </logic:greaterEqual>
          <div class="">
          <logic:iterate id="sml390sbpTarget" name="sml390knForm" property="sml390sbpTargetSelectCombo">
            <bean:define id="mukoUserClass">&nbsp;</bean:define>
            <logic:equal name="sml390sbpTarget" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass">mukoUser</bean:define></logic:equal>
            <span class="<bean:write name="mukoUserClass"/>"><bean:write name="sml390sbpTarget" property="label" /></span>
            <br>
          </logic:iterate>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.memo" />
        </th>
        <td class="w75">
          <bean:write name="sml390knForm" property="sml390knDspBiko" filter="false"/>
        </td>
      </tr>
    </table>
    <div class="txt_r footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="cmn.final">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="cmn.final">
        <gsmsg:write key="cmn.final" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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