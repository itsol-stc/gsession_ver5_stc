<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.CMDMODE_EDIT);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<title>GROUPSESSION <gsmsg:write key="schedule.sch240kn.01" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>
<body>
<html:form action="/schedule/sch240kn">
<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/schedule/jsp/sch080_hiddenParams.jsp" %>

<logic:notEmpty name="sch240knForm" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch240knForm" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240knForm" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch240knForm" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240knForm" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch240knForm" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240knForm" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch240knForm" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240knForm" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch240knForm" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="sch230keyword" />
<html:hidden property="sch230pageTop" />
<html:hidden property="sch230pageBottom" />
<html:hidden property="sch230pageDspFlg" />
<html:hidden property="sch230svKeyword" />
<html:hidden property="sch230sortKey" />
<html:hidden property="sch230order" />
<html:hidden property="sch230editData" />
<html:hidden property="sch230searchFlg" />
<html:hidden property="sch230editMode" />
<html:hidden property="sch240initFlg" />
<html:hidden property="sch240name" />
<html:hidden property="sch240biko" />
<html:hidden property="sch240position" />
<html:hidden property="sch240positionAuth" />
<html:hidden property="sch240accessGroup" />

<logic:equal name="sch240knForm" property="sch230editMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="sch240knForm" property="sch230editMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>
<logic:notEmpty name="sch240knForm" property="sch240subject">
  <logic:iterate id="subject" name="sch240knForm" property="sch240subject">
    <input type="hidden" name="sch240subject" value="<bean:write name="subject"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240knForm" property="sch240editUser">
  <logic:iterate id="editUser" name="sch240knForm" property="sch240editUser">
    <input type="hidden" name="sch240editUser" value="<bean:write name="editUser"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch240knForm" property="sch240accessUser">
  <logic:iterate id="accessUser" name="sch240knForm" property="sch240accessUser">
    <input type="hidden" name="sch240accessUser" value="<bean:write name="accessUser"/>">
  </logic:iterate>
</logic:notEmpty>
<bean:define id="schEditMode" name="sch240knForm" property="sch230editMode" type="java.lang.Integer" />
<%
  int editMode = schEditMode.intValue();
%>
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
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="schedule.sch240kn.01" />
      </li>
    <% } else if (editMode == 1) { %>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="schedule.sch240kn.02" />
      </li>
    <% } %>
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
  <div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.sch240.04" />
      </th>
      <td class="w75">
        <bean:write name="sch240knForm" property="sch240name" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.sch240.05" />
      </th>
      <td class="w75">
        <logic:notEmpty name="sch240knForm" property="sch240subjectSelectCombo">
          <logic:iterate id="subject" name="sch240knForm" property="sch240subjectSelectCombo" scope="request">
            <logic:equal name="subject" property="usrUkoFlg" value="1">
              <span class="mukoUser"><bean:write name="subject" property="label" /><br></span>
            </logic:equal>
            <logic:notEqual name="subject" property="usrUkoFlg" value="1">
              <span><bean:write name="subject" property="label" /><br></span>
            </logic:notEqual>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.sch240.07" />
      </th>
      <td class="w75">
        <div>
          <div class="fw_b">
            <gsmsg:write key="cmn.post" />
          </div>
          <div class="ml10">
            <bean:write name="sch240knForm" property="sch240knpositionName" />
          </div>
          <logic:greaterThan name="sch240knForm" property="sch240position" value="0">
            <span class="ml10"></span>
            <logic:equal name="sch240knForm" property="sch240positionAuth" value="0">
              <gsmsg:write key="cmn.add.edit.delete" />
            </logic:equal>
            <logic:equal name="sch240knForm" property="sch240positionAuth" value="1">
              <gsmsg:write key="cmn.reading" />
            </logic:equal>
          </logic:greaterThan>
        </div>

        <div class="fw_b mt5">
          <gsmsg:write key="cmn.add.edit.delete" />
        </div>
        <logic:notEmpty name="sch240knForm" property="sch240editUserSelectCombo">
          <logic:iterate id="editUser" name="sch240knForm" property="sch240editUserSelectCombo" scope="request">
            <div class="ml10">
              <logic:equal name="editUser" property="usrUkoFlg" value="1">
                <span class="mukoUser"><bean:write name="editUser" property="label" /><br></span>
              </logic:equal>
              <logic:notEqual name="editUser" property="usrUkoFlg" value="1">
                <bean:write name="editUser" property="label" /><br>
              </logic:notEqual>
            </div>
          </logic:iterate>
        </logic:notEmpty>
        <logic:empty name="sch240knForm" property="sch240editUserSelectCombo">
          <div>
            &nbsp;
          </div>
        </logic:empty>

        <div class="fw_b mt5">
          <gsmsg:write key="cmn.reading" />
        </div>
        <logic:notEmpty name="sch240knForm" property="sch240accessSelectCombo">
          <logic:iterate id="accessUser" name="sch240knForm" property="sch240accessSelectCombo" scope="request">
            <div class="ml10">
              <logic:equal name="accessUser" property="usrUkoFlg" value="1">
                <span class="mukoUser"><bean:write name="accessUser" property="label" /><br></span>
              </logic:equal>
              <logic:notEqual name="accessUser" property="usrUkoFlg" value="1">
                <bean:write name="accessUser" property="label" /><br>
              </logic:notEqual>
            </div>
          </logic:iterate>
        </logic:notEmpty>
        <logic:empty name="sch240knForm" property="sch240accessSelectCombo">
          <div>
            &nbsp;
          </div>
        </logic:empty>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <bean:write name="sch240knForm" property="sch240knBiko" filter="false" />
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