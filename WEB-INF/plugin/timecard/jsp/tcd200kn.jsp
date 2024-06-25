<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstTimecard" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.52" /></title>

</head>

<body>
<html:form action="/timecard/tcd200kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="tcdBackScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<html:hidden property="tcdDspFrom" />

<input type="hidden" name="tcd200Group" value="<bean:write name="tcd200knForm" property="tcd200Group" />" >
<input type="hidden" name="tcd200Name" value="<bean:write name="tcd200knForm" property="tcd200Name" />" >
<input type="hidden" name="tcd200Nendo" value="<bean:write name="tcd200knForm" property="tcd200Nendo" />" >
<input type="hidden" name="tcd200YukyuDays" value="<bean:write name="tcd200knForm" property="tcd200YukyuDays" />">
<input type="hidden" name="tcd200initFlg" value="<bean:write name="tcd200knForm" property="tcd200initFlg" />">
<input type="hidden" name="tcd190nendo" value="<bean:write name="tcd200knForm" property="tcd190nendo" />">
<input type="hidden" name="tcd190group" value="<bean:write name="tcd200knForm" property="tcd190group" />">
<input type="hidden" name="tcd190sortKey" value="<bean:write name="tcd200knForm" property="tcd190sortKey" />">
<input type="hidden" name="tcd190order" value="<bean:write name="tcd200knForm" property="tcd190order" />">
<input type="hidden" name="tcd190page" value="<bean:write name="tcd200knForm" property="tcd190page" />">
<input type="hidden" name="tcd200mode" value="<bean:write name="tcd200knForm" property="tcd200mode" />">
<input type="hidden" name="tcd200editUser" value="<bean:write name="tcd200knForm" property="tcd200editUser" />">
<input type="hidden" name="tcd200editNendo" value="<bean:write name="tcd200knForm" property="tcd200editNendo" />">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto w80">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><logic:equal name="tcd200knForm" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_INSERT) %>"><gsmsg:write key="tcd.tcd200.01" /><gsmsg:write key="cmn.check" /></logic:equal><logic:equal name="tcd200knForm" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_UPDATE) %>"><gsmsg:write key="tcd.tcd200.02" /><gsmsg:write key="cmn.check" /></logic:equal></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd200knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto w80">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <logic:messagesNotPresent message="false">
    <logic:equal name="tcd200knForm" property="tcd200mode" value="<%= String.valueOf(GSConstTimecard.YUKYU_MODE_UPDATE) %>">
      <div class="txt_l mt20"><gsmsg:write key="tcd.tcd200kn.01" /></div>
    </logic:equal>
  </logic:messagesNotPresent>

  <!-- 情報欄 -->
  <table class="table-left w100 mt0">
    <tr>
      <th class="table-_title-color w25"><gsmsg:write key="cmn.user" /></th>
      <td class="w75">
      <bean:define id="mukouserClass" value="" />
      <logic:equal name="tcd200knForm" property="tcd200editUserUkoFlg" value="<%= String.valueOf(GSConst.YUKOMUKO_MUKO) %>"><bean:define id="mukouserClass" value="mukoUser" /></logic:equal>
      <span class="<%= mukouserClass %>">
        <bean:write name="tcd200knForm" property="tcd200editUserName" />
      </span>
      </td>
    </tr>
    <tr>
      <th class="table-_title-color w25"><gsmsg:write key="tcd.209" /></th>
      <td class="w75">
        <bean:write name="tcd200knForm" property="tcd200Nendo" />
        <gsmsg:write key="tcd.209" />
      </td>
    </tr>
    <tr>
      <th class="table-_title-color w25"><gsmsg:write key="tcd.210" /></th>
      <td class="w75">
        <bean:write name="tcd200knForm" property="tcd200YukyuDays" />
        <gsmsg:write key="cmn.day"/>
      </td>
    </tr>
  </table>
  
  <!-- footer -->
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd200knback');">
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
