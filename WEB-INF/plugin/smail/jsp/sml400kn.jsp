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
<title>GROUPSESSION <gsmsg:write key="sml.sml400kn.01" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../smail/js/sml400kn.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="setShowHide();">
<html:form action="/smail/sml400kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlViewAccount" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml400MaxDspStype" />
<html:hidden property="sml400ReloadTimeStype" />
<html:hidden property="sml400PhotoDspStype" />
<html:hidden property="sml400AttachImgDspStype" />
<html:hidden property="sml400MaxDsp" />
<html:hidden property="sml400ReloadTime" />
<html:hidden property="sml400PhotoDsp" />
<html:hidden property="sml400AttachImgDsp" />

<logic:notEmpty name="sml400knForm" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml400knForm" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="cmn.display.settings.kn" />
    </li>
    <li>
      <div>
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
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
<div class="wrapper w80 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td class="w75">
        <logic:equal name="sml400knForm" property="sml400MaxDspStype" value="1">
        <gsmsg:write key="cmn.set.the.admin" />
        </logic:equal>
        <logic:equal name="sml400knForm" property="sml400MaxDspStype" value="0">
        <gsmsg:write key="cmn.set.eachuser" />
        </logic:equal>
        <span id="smlMaxDspArea">
          <div class="settingForm_separator">
          <bean:write name="sml400knForm" property="sml400knMaxDsp" />
          </div>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.auto.reload.time" />
      </th>
      <td>
        <logic:equal name="sml400knForm" property="sml400ReloadTimeStype" value="1">
        <gsmsg:write key="cmn.set.the.admin" />
        </logic:equal>
        <logic:equal name="sml400knForm" property="sml400ReloadTimeStype" value="0">
        <gsmsg:write key="cmn.set.eachuser" />
        </logic:equal>
        <span id="smlReloadTimeArea">
          <div class="settingForm_separator">
          <bean:write name="sml400knForm" property="sml400knReloadTime" />
          </div>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.sml040.05" />
      </th>
      <td>
        <logic:equal name="sml400knForm" property="sml400PhotoDspStype" value="1">
        <gsmsg:write key="cmn.set.the.admin" />
        </logic:equal>
        <logic:equal name="sml400knForm" property="sml400PhotoDspStype" value="0">
        <gsmsg:write key="cmn.set.eachuser" />
        </logic:equal>
        <span id="smlPhotoDspArea">
          <div class="settingForm_separator">
          <bean:write name="sml400knForm" property="sml400knPhotoDsp" />
          </div>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.sml040.07" />
      </th>
      <td>
        <logic:equal name="sml400knForm" property="sml400AttachImgDspStype" value="1">
        <gsmsg:write key="cmn.set.the.admin" />
        </logic:equal>
        <logic:equal name="sml400knForm" property="sml400AttachImgDspStype" value="0">
        <gsmsg:write key="cmn.set.eachuser" />
        </logic:equal>
        <span id="smlAttachImgDspArea">
          <div class="settingForm_separator">
          <bean:write name="sml400knForm" property="sml400knAttachImgDsp" />
          </div>
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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