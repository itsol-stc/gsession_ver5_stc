<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cir.GSConstCircular" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Script-Type" content="text/javascript">
<META http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION<gsmsg:write key="cir.5"/> <gsmsg:write key="cir.cir220kn.1"/></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/circular/cir220kn">
<!-- BODY -->
<input type="hidden" name="CMD">
<html:hidden property="backScreen" />
<html:hidden property="cir220initFlg" />
<html:hidden property="cir220TaisyoKbn" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="backScreen" />

<logic:notEmpty name="cir220knForm" property="cir220MakeSenderList" scope="request">
    <logic:iterate id="ulBean" name="cir220knForm" property="cir220MakeSenderList" scope="request">
    <input type="hidden" name="cir220MakeSenderList" value="<bean:write name="ulBean" />">
    </logic:iterate>
</logic:notEmpty>

<!-- header -->
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5"/></span><gsmsg:write key="cir.cir220kn.1"/>
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('cir220kncommit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cir220knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
  <!-- 対象者区分 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cir.cir220.3"/>
      </th>
      <td class="w75">
        <logic:equal name="cir220knForm" property="cir220knTaisyoKbn" value="<%= String.valueOf(GSConstCircular.CAF_AREST_KBN_SELECT) %>" >
          <gsmsg:write key="wml.32" />
        </logic:equal>
        <logic:notEqual name="cir220knForm" property="cir220knTaisyoKbn" value="<%= String.valueOf(GSConstCircular.CAF_AREST_KBN_SELECT) %>" >
          <gsmsg:write key="wml.31" />
        </logic:notEqual>
      </td>
    </tr>
    <logic:equal name="cir220knForm" property="cir220knTaisyoKbn" value="<%= String.valueOf(GSConstCircular.CAF_AREST_KBN_SELECT) %>" >
    <tr>
      <th>
        <gsmsg:write key="cir.cir220.6"/>
      </th>
      <td>
        <logic:notEmpty name="cir220knForm" property="cir220knAddSenderLabel" scope="request">
        <logic:iterate id="labelValueBean" name="cir220knForm" property="cir220knAddSenderLabel" scope="request">
          <div><bean:write name="labelValueBean" property="label" /></div>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('cir220kncommit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cir220knback');">
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