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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../ipkanri/js/ipkanri.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="ipk.ipk100kn.1" /></title>
</head>
<body onload="scr(<bean:write name="ipk100knForm" property="ipk100knScroll" />);">
<html:form action="/ipkanri/ipk100kn">

<html:hidden property="CMD" />
<html:hidden property="backScreen" />
<html:hidden property="editMode" />
<html:hidden property="specKbn" />
<html:hidden property="ismSid" />
<html:hidden property="ipk100name" />
<html:hidden property="ipk100specLevel" />
<html:hidden property="ipk100biko" />
<html:hidden property="ipk100scroll" />
<html:hidden property="existFlg" />
<html:hidden property="ipk100svSpecLevel" />
<html:hidden property="ipk100helpMode" />
<input type="hidden" name="helpPrm" value="<bean:write name='ipk100knForm' property='ipk100helpMode' />">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.ipkanri" /></span><gsmsg:write key="ipk.10" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush2('ipk100knTouroku');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk100knReturn');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">

  <div class="txt_l cl_fontWarn">
    <logic:equal name="ipk100knForm" property="editMode" value="1">
    <gsmsg:write key="ipk.ipk100kn.2" />
    </logic:equal>
    <logic:equal name="ipk100knForm" property="editMode" value="2">
    <gsmsg:write key="ipk.ipk100kn.3" />
    </logic:equal>
  </div>


  <table class="table-left">

        <tr>
          <th class="txt_l bgC_header1 table_title-color" colspan="2">
            <logic:equal name="ipk100Form" property="specKbn" value="1">
              <gsmsg:write key="ipk.ipk100.4" />
            </logic:equal>
            <logic:equal name="ipk100Form" property="specKbn" value="2">
              <gsmsg:write key="ipk.ipk100.2" />
            </logic:equal>
            <logic:equal name="ipk100Form" property="specKbn" value="3">
              <gsmsg:write key="ipk.ipk100.3" />
            </logic:equal>
          </th>
        </tr>

        <tr>
      <th class="w25">
        <gsmsg:write key="cmn.name4" />
      </th>
      <td class="w75">
        <bean:write name="ipk100knForm" property="ipk100knName" filter="false"/>
      </td>
    </tr>

    <tr>
      <th>
        <gsmsg:write key="cmn.sort" />
      </th>
      <td>

      <div class="scroll" id="Layer1">
        <table class="table-top">
        <logic:notEmpty name="ipk100knForm" property="ipk100specMstModelList">
        <logic:equal name="ipk100knForm" property="ipk100specLevel" value="0">
        <tr>
        <td><span class="fw_b"><gsmsg:write key="ipk.ipk100kn.4" /></span></td>
        </tr>
        </logic:equal>

        <logic:iterate id="specModel" name="ipk100knForm" property="ipk100specMstModelList">
        <bean:define id="radioValue" name="specModel" property="ismSid" type="java.lang.Integer" />
        <tr>
        <logic:equal name="ipk100knForm" property="ipk100specLevel" value="<%= String.valueOf(radioValue.intValue()) %>">
        <td>
        </logic:equal>
        <logic:notEqual name="ipk100knForm" property="ipk100specLevel" value="<%= String.valueOf(radioValue.intValue()) %>">
        <td>
        </logic:notEqual>
        <span><bean:write name="specModel" property="ipk100name" filter="false"/></span>
        </td>
        </tr>
        <logic:equal name="ipk100knForm" property="ipk100specLevel" value="<%= String.valueOf(radioValue.intValue()) %>">
        <tr><td><span class="fw_b"><gsmsg:write key="ipk.ipk100kn.4" /></span></td></tr>
        </logic:equal>

        </logic:iterate>
        </logic:notEmpty>
        </table>
      </div>

      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <bean:write name="ipk100knForm" property="ipk100knBiko" filter="false" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush2('ipk100knTouroku');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk100knReturn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>