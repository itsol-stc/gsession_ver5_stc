<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<bean:define id="chtEditMode" name="cht090knForm" property="cht080editMode" type="java.lang.Integer" />

<%
  int editMode = chtEditMode.intValue();
%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cht.cht090.01" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>

</head>

<html:form action="/chat/cht090kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="cht090initFlg" />
<html:hidden property="cht090name" />
<html:hidden property="cht090biko" />
<html:hidden property="cht090position" />
<html:hidden property="cht090positionAuth" />
<html:hidden property="cht090accessGroup" />

<html:hidden property="cht080keyword" />
<html:hidden property="cht080pageTop" />
<html:hidden property="cht080pageBottom" />
<html:hidden property="cht080pageDspFlg" />
<html:hidden property="cht080svKeyword" />
<html:hidden property="cht080sortKey" />
<html:hidden property="cht080order" />
<html:hidden property="cht080editData" />
<html:hidden property="cht080searchFlg" />
<html:hidden property="cht080editMode" />

<logic:equal name="cht090knForm" property="cht080editMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="cht090knForm" property="cht080editMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>

<logic:notEmpty name="cht090knForm" property="cht090subject">
  <logic:iterate id="subject" name="cht090knForm" property="cht090subject">
    <input type="hidden" name="cht090subject" value="<bean:write name="subject"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="cht090knForm" property="cht090accessUser">
  <logic:iterate id="accessUser" name="cht090knForm" property="cht090accessUser">
    <input type="hidden" name="cht090accessUser" value="<bean:write name="accessUser"/>">
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
    <% if (editMode == 0) { %>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht090kn.02" />
      </li>
    <% } else if (editMode == 1) { %>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht090kn.03" />
      </li>
    <% } %>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doRegister');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cht090knback');">
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
      <th class="w20">
        <gsmsg:write key="cht.cht090.03" />
      </th>
      <td class="w80">
        <bean:write name="cht090knForm" property="cht090name" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cht.cht090.04" />
      </th>
      <td>
        <logic:notEmpty name="cht090knForm" property="cht090knsubjectList">

          <logic:iterate id="subject" name="cht090knForm" property="cht090knsubjectList" scope="request">

            <logic:equal name="subject" property="usrUkoFlg" value="1">
              <span class="mukoUser"><bean:write name="subject" property="label" /><br></span>
            </logic:equal>
            <logic:notEqual name="subject" property="usrUkoFlg" value="1">
              <bean:write name="subject" property="label" /><br>
            </logic:notEqual>

          </logic:iterate>

        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.post" />
      </th>
      <td>
        <bean:write name="cht090knForm" property="cht090knpositionName" />
        <logic:greaterThan name="cht090knForm" property="cht090position" value="0">
        <br>
        <logic:equal name="cht090knForm" property="cht090positionAuth" value="0">
          <gsmsg:write key="cmn.reading.permit" />
        </logic:equal>
      </td>
      </logic:greaterThan>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cht.cht090kn.01" />
      </th>
      <td>
        <table class="table-top w50">
          <tr>
            <th>
              <gsmsg:write key="cht.cht090.07" />
            </th>
          </tr>
          <tr>
            <td>
              <logic:notEmpty name="cht090knForm" property="cht090knaccessList">
              <logic:iterate id="accessUser" name="cht090knForm" property="cht090knaccessList" scope="request">
                <logic:equal name="accessUser" property="usrUkoFlg" value="1">
                  <span class="mukoUser"><bean:write name="accessUser" property="label" /><br></span>
                </logic:equal>
                <logic:notEqual name="accessUser" property="usrUkoFlg" value="1">
                  <bean:write name="accessUser" property="label" /><br>
                </logic:notEqual>

              </logic:iterate>
              </logic:notEmpty>
              <logic:empty name="cht090knForm" property="cht090knaccessList">&nbsp;</logic:empty>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <bean:write name="cht090knForm" property="cht090knBiko" filter="false" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doRegister');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cht090knback');">
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