<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.sml.sml360.Sml360Form" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="wml.wml140kn.06" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml360.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/smail/sml360kn">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="smlCmdMode" />
<html:hidden property="smlAccountSid" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlFilterCmdMode" />
<html:hidden property="smlEditFilterId" />
<html:hidden property="sml360initFlg" />
<html:hidden property="dspCount" />
<html:hidden property="sml350SortRadio" />

<html:hidden property="sml360initFlg" />
<html:hidden property="sml360FilterName" />
<html:hidden property="sml360filterType" />
<html:hidden property="sml360condition1" />
<html:hidden property="sml360conditionType1" />
<html:hidden property="sml360conditionExs1" />
<html:hidden property="sml360conditionText1" />
<html:hidden property="sml360condition2" />
<html:hidden property="sml360conditionType2" />
<html:hidden property="sml360conditionExs2" />
<html:hidden property="sml360conditionText2" />
<html:hidden property="sml360condition3" />
<html:hidden property="sml360conditionType3" />
<html:hidden property="sml360conditionExs3" />
<html:hidden property="sml360conditionText3" />
<html:hidden property="sml360condition4" />
<html:hidden property="sml360conditionType4" />
<html:hidden property="sml360conditionExs4" />
<html:hidden property="sml360conditionText4" />
<html:hidden property="sml360condition5" />
<html:hidden property="sml360conditionType5" />
<html:hidden property="sml360conditionExs5" />
<html:hidden property="sml360conditionText5" />

<html:hidden property="sml360actionLabel" />
<html:hidden property="sml360actionLabelValue" />
<html:hidden property="sml360actionRead" />
<html:hidden property="sml360actionDust" />




<html:hidden property="sml360tempFile" />
<html:hidden property="sml360doFilter" />

<html:hidden property="sml360viewMailList" />
<html:hidden property="sml360mailListSortKey" />
<html:hidden property="sml360mailListOrder" />
<html:hidden property="sml360svFilterType" />
<html:hidden property="sml360svCondition1" />
<html:hidden property="sml360svConditionType1" />
<html:hidden property="sml360svConditionExs1" />
<html:hidden property="sml360svConditionText1" />
<html:hidden property="sml360svCondition2" />
<html:hidden property="sml360svConditionType2" />
<html:hidden property="sml360svConditionExs2" />
<html:hidden property="sml360svConditionText2" />
<html:hidden property="sml360svCondition3" />
<html:hidden property="sml360svConditionType3" />
<html:hidden property="sml360svConditionExs3" />
<html:hidden property="sml360svConditionText3" />
<html:hidden property="sml360svCondition4" />
<html:hidden property="sml360svConditionType4" />
<html:hidden property="sml360svConditionExs4" />
<html:hidden property="sml360svConditionText4" />
<html:hidden property="sml360svCondition5" />
<html:hidden property="sml360svConditionType5" />
<html:hidden property="sml360svConditionExs5" />
<html:hidden property="sml360svConditionText5" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.wml140kn.06" />
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

<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
        <bean:write name="sml360knForm" property="sml350accountName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.84" />
      </th>
      <td>
        <bean:write name="sml360knForm" property="sml360FilterName" />
      </td>
    </tr>
  </table>

  <div class="txt_l mt20">
    <gsmsg:write key="wml.40" /><span class="ml10"><bean:write name="sml360knForm" property="sml360filterTypeView" /></span>
  </div>
  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.wml140kn.05" />
      </th>
      <td class="w75">
        <bean:write name="sml360knForm" property="sml360conditionType1View" />
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionExs1View" /></span>
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionText1" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.04" />
      </th>
      <td>
        <bean:write name="sml360knForm" property="sml360conditionType2View" />
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionExs2View" /></span>
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionText2" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.03" />
      </th>
      <td>
        <bean:write name="sml360knForm" property="sml360conditionType3View" />
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionExs3View" /></span>
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionText3" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.02" />
      </th>
      <td>
        <bean:write name="sml360knForm" property="sml360conditionType4View" />
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionExs4View" /></span>
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionText4" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.01" />
      </th>
      <td>
        <bean:write name="sml360knForm" property="sml360conditionType5View" />
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionExs5View" /></span>
        <span class="ml10"><bean:write name="sml360knForm" property="sml360conditionText5" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td>
        <logic:equal name="sml360knForm" property="sml360tempFile" value="1">
          <gsmsg:write key="wml.14" />
        </logic:equal>
      </td>
    </tr>
  </table>

  <div class="txt_l mt20">
    <gsmsg:write key="wml.56" />
  </div>
  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.run" />
      </th>
      <td class="w75">
        <logic:equal name="sml360knForm" property="sml360actionLabel" value="1">
          <div><gsmsg:write key="wml.75" /><span class="ml10"><bean:write name="sml360knForm" property="sml360LabelView" /></span></div>
        </logic:equal>
        <logic:equal name="sml360knForm" property="sml360actionRead" value="1">
          <div><gsmsg:write key="cmn.mark.read" /></div>
        </logic:equal>
        <logic:equal name="sml360knForm" property="sml360actionDust" value="1">
          <div><gsmsg:write key="wml.91" /></div>
        </logic:equal>
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