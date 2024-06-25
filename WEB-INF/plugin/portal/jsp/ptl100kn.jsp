<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../portal/js/ptl100kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/ptl100_layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/portal.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="ptl.ptl100kn.1" /></title>
</head>

<!-- body -->
<body onload="changeContentKn(0);">
<html:form action="/portal/ptl100kn">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortletSid" />
<html:hidden property="ptlCmdMode" />

<html:hidden property="ptl090category" />
<html:hidden property="ptl090svCategory" />
<html:hidden property="ptl090sortPortlet" />

<html:hidden property="ptl100contentType" />
<html:hidden property="ptl100contentHtml" />
<html:hidden property="ptl100contentUrl" />
<html:hidden property="ptl100content" />
<html:hidden property="ptl100border" />
<html:hidden property="ptl100name" />
<html:hidden property="ptl100description" />
<html:hidden property="ptl100category" />
<html:hidden property="ptl100init" />
<html:hidden property="ptl100knContentUrl" />

<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto pluginPortlet">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.ptl100kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('ptl100knOk');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ptl100knBack');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l pluginPortlet">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <table class="table-left w100 pluginPortlet">
    <tr class="pluginPortlet">
      <th class="w20 no_w">
        <gsmsg:write key="ptl.17" />
      </th>
      <td class="w80">
        <bean:write name="ptl100knForm" property="ptl100name" />
      </td>
    </tr>
    <tr class="pluginPortlet">
      <th class="w20 no_w">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w80">
        <bean:write name="ptl100knForm" property="ptl100knCategoryName" />
      </td>
    </tr>
    <tr class="pluginPortlet">
      <th class="w20 no_w">
        <gsmsg:write key="ptl.16" />
      </th>
      <td class="w80">
        <logic:equal name="ptl100knForm" property="ptl100border" value="0">
          <gsmsg:write key="address.adr010.contact.5" />
        </logic:equal>
        <logic:equal name="ptl100knForm" property="ptl100border" value="1">
          <gsmsg:write key="cmn.no" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w bor_b1 txt_l">
        <gsmsg:write key="cmn.content" />
      </th>
      <td class="w80 bor_b1 bor_l1 txt_l p5">
        <div class="verAlignMid">
          <input type="radio" id="borderKbn0" name="dspChange" checked="checked" class="mr5" onChange="changeContentKn(0)">
          <label for="borderKbn0"><gsmsg:write key="cmn.show" /></label>
          <input type="radio" id="borderKbn1" name="dspChange" class="mr5 ml10" onChange="changeContentKn(1)">
          <label for="borderKbn1" class="text_base">HTML</label>
        </div>
        <table class="table-top w100 pluginPortlet" id="contentSrcArea">
          <tr>
            <td class="bgC_tableCell">
              <logic:equal name="ptl100knForm" property="ptl100contentType" value="0">
                <bean:write name="ptl100knForm" property="ptl100content" />
              </logic:equal>
              <logic:equal name="ptl100knForm" property="ptl100contentType" value="1">
                <bean:write name="ptl100knForm" property="ptl100knContentUrl" />
              </logic:equal>
              <logic:equal name="ptl100knForm" property="ptl100contentType" value="2">
                <bean:write name="ptl100knForm" property="ptl100contentHtml" />
              </logic:equal>
            </td>
          </tr>
        </table>
        <table class="table-top w100" id="contentArea">
          <logic:equal name="ptl100knForm" property="ptl100border" value="0">
            <tr class="pluginPortlet">
              <th class="txt_l">
                <bean:write name="ptl100knForm" property="ptl100name" />
              </th>
            </tr>
          </logic:equal>
          <tr>
            <td class="bgC_tableCell tinymce-inherit">
              <logic:equal name="ptl100knForm" property="ptl100contentType" value="0">
                <htmlframe:write attrClass="w100">
                   <bean:write name="ptl100knForm" property="ptl100knContent" filter="false" />
                </htmlframe:write>
              </logic:equal>
              <logic:equal name="ptl100knForm" property="ptl100contentType" value="1">
                <bean:write name="ptl100knForm" property="ptl100knContentUrl" filter="false" />
              </logic:equal>
              <logic:equal name="ptl100knForm" property="ptl100contentType" value="2">
                <bean:write name="ptl100knForm" property="ptl100knContentHtml" filter="false" />
              </logic:equal>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="pluginPortlet">
      <th class="w20 no_w">
        <gsmsg:write key="ptl.8" />
      </th>
      <td class="w80">
        <bean:write name="ptl100knForm" property="ptl100knDescription" filter="false"/>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block pluginPortlet">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('ptl100knOk');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ptl100knBack');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>


</body>

</html:html>