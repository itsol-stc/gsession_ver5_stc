<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.sml.sml340.Sml340Form" %>
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
<script src="../smail/js/sml340.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/smail/sml340kn">


<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="smlAccountSid" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlFilterCmdMode" />
<html:hidden property="smlEditFilterId" />
<html:hidden property="smlViewAccount" />
<html:hidden property="dspCount" />
<html:hidden property="sml330SortRadio" />

<html:hidden property="sml340initFlg" />
<html:hidden property="sml340FilterName" />
<html:hidden property="sml340filterType" />
<html:hidden property="sml340condition1" />
<html:hidden property="sml340conditionType1" />
<html:hidden property="sml340conditionExs1" />
<html:hidden property="sml340conditionText1" />
<html:hidden property="sml340condition2" />
<html:hidden property="sml340conditionType2" />
<html:hidden property="sml340conditionExs2" />
<html:hidden property="sml340conditionText2" />
<html:hidden property="sml340condition3" />
<html:hidden property="sml340conditionType3" />
<html:hidden property="sml340conditionExs3" />
<html:hidden property="sml340conditionText3" />
<html:hidden property="sml340condition4" />
<html:hidden property="sml340conditionType4" />
<html:hidden property="sml340conditionExs4" />
<html:hidden property="sml340conditionText4" />
<html:hidden property="sml340condition5" />
<html:hidden property="sml340conditionType5" />
<html:hidden property="sml340conditionExs5" />
<html:hidden property="sml340conditionText5" />

<html:hidden property="sml340actionLabel" />
<html:hidden property="sml340actionLabelValue" />
<html:hidden property="sml340actionRead" />
<html:hidden property="sml340actionDust" />

<html:hidden property="sml240keyword" />
<html:hidden property="sml240group" />
<html:hidden property="sml240user" />
<html:hidden property="sml240svKeyword" />
<html:hidden property="sml240svGroup" />
<html:hidden property="sml240svUser" />
<html:hidden property="sml240sortKey" />
<html:hidden property="sml240order" />
<html:hidden property="sml240searchFlg" />



<html:hidden property="sml340tempFile" />
<html:hidden property="sml340doFilter" />

<html:hidden property="sml340viewMailList" />
<html:hidden property="sml340mailListSortKey" />
<html:hidden property="sml340mailListOrder" />
<html:hidden property="sml340svFilterType" />
<html:hidden property="sml340svCondition1" />
<html:hidden property="sml340svConditionType1" />
<html:hidden property="sml340svConditionExs1" />
<html:hidden property="sml340svConditionText1" />
<html:hidden property="sml340svCondition2" />
<html:hidden property="sml340svConditionType2" />
<html:hidden property="sml340svConditionExs2" />
<html:hidden property="sml340svConditionText2" />
<html:hidden property="sml340svCondition3" />
<html:hidden property="sml340svConditionType3" />
<html:hidden property="sml340svConditionExs3" />
<html:hidden property="sml340svConditionText3" />
<html:hidden property="sml340svCondition4" />
<html:hidden property="sml340svConditionType4" />
<html:hidden property="sml340svConditionExs4" />
<html:hidden property="sml340svConditionText4" />
<html:hidden property="sml340svCondition5" />
<html:hidden property="sml340svConditionType5" />
<html:hidden property="sml340svConditionExs5" />
<html:hidden property="sml340svConditionText5" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>



<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
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
        <bean:write name="sml340knForm" property="sml330accountName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.84" />
      </th>
      <td>
        <bean:write name="sml340knForm" property="sml340FilterName" />
      </td>
    </tr>
  </table>

  <div class="txt_l mt20">
    <gsmsg:write key="wml.40" /><span class="ml10"><bean:write name="sml340knForm" property="sml340filterTypeView" /></span>
  </div>
  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.wml140kn.05" />
      </th>
      <td class="w75">
        <bean:write name="sml340knForm" property="sml340conditionType1View" />
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionExs1View" /></span>
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionText1" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.04" />
      </th>
      <td>
        <bean:write name="sml340knForm" property="sml340conditionType2View" />
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionExs2View" /></span>
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionText2" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.03" />
      </th>
      <td>
        <bean:write name="sml340knForm" property="sml340conditionType3View" />
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionExs3View" /></span>
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionText3" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.02" />
      </th>
      <td>
        <bean:write name="sml340knForm" property="sml340conditionType4View" />
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionExs4View" /></span>
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionText4" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml140kn.01" />
      </th>
      <td>
        <bean:write name="sml340knForm" property="sml340conditionType5View" />
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionExs5View" /></span>
        <span class="ml10"><bean:write name="sml340knForm" property="sml340conditionText5" /></span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td>
        <logic:equal name="sml340knForm" property="sml340tempFile" value="1">
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
        <logic:equal name="sml340knForm" property="sml340actionLabel" value="1">
        <div><gsmsg:write key="wml.75" /><span class="ml10"><bean:write name="sml340knForm" property="sml340LabelView" /></span></div>
        </logic:equal>
        <logic:equal name="sml340knForm" property="sml340actionRead" value="1">
        <div><gsmsg:write key="cmn.mark.read" /></div>
        </logic:equal>
        <logic:equal name="sml340knForm" property="sml340actionDust" value="1">
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