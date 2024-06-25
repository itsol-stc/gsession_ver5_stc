<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String delOptFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DELETE_OPTION_FILE);
   String delOptFolderFile = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DELETE_OPTION_FOLDER_FILE);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.fil250kn.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../file/js/dtree.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/file/fil250kn">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->

<input type="hidden" name="CMD" value="">

<html:hidden property="selectDir" />
<html:hidden property="sepKey" />
<html:hidden property="fil250SltCabinetSid" />
<html:hidden property="fil250DirSid" />
<html:hidden property="fil250cabinetKbn" />

<logic:notEmpty name="fil250knForm" property="fil250SvCallUser">
<logic:iterate id="calUser" name="fil250knForm" property="fil250SvCallUser">
  <input type="hidden" name="fil250SvCallUser" value="<bean:write name="calUser" />">
</logic:iterate>
</logic:notEmpty>


<html:hidden property="backDsp" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="filSearchWd" />
<html:hidden property="backScreen" />

<logic:notEmpty name="fil250knForm" property="fil040SelectDel" scope="request">
  <logic:iterate id="del" name="fil250knForm" property="fil040SelectDel" scope="request">
    <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv1" scope="request">
  <logic:iterate id="lv1" name="fil250knForm" property="treeFormLv1" scope="request">
    <input type="hidden" name="treeFormLv1" value="<bean:write name="lv1"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv2" scope="request">
  <logic:iterate id="lv2" name="fil250knForm" property="treeFormLv2" scope="request">
    <input type="hidden" name="treeFormLv2" value="<bean:write name="lv2"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv3" scope="request">
  <logic:iterate id="lv3" name="fil250knForm" property="treeFormLv3" scope="request">
    <input type="hidden" name="treeFormLv3" value="<bean:write name="lv3"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv4" scope="request">
  <logic:iterate id="lv4" name="fil250knForm" property="treeFormLv4" scope="request">
    <input type="hidden" name="treeFormLv4" value="<bean:write name="lv4"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv5" scope="request">
  <logic:iterate id="lv5" name="fil250knForm" property="treeFormLv5" scope="request">
    <input type="hidden" name="treeFormLv5" value="<bean:write name="lv5"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv6" scope="request">
  <logic:iterate id="lv6" name="fil250knForm" property="treeFormLv6" scope="request">
    <input type="hidden" name="treeFormLv6" value="<bean:write name="lv6"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv7" scope="request">
  <logic:iterate id="lv7" name="fil250knForm" property="treeFormLv7" scope="request">
    <input type="hidden" name="treeFormLv7" value="<bean:write name="lv7"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv8" scope="request">
  <logic:iterate id="lv8" name="fil250knForm" property="treeFormLv8" scope="request">
    <input type="hidden" name="treeFormLv8" value="<bean:write name="lv8"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv9" scope="request">
  <logic:iterate id="lv9" name="fil250knForm" property="treeFormLv9" scope="request">
    <input type="hidden" name="treeFormLv9" value="<bean:write name="lv9"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="treeFormLv10" scope="request">
  <logic:iterate id="lv10" name="fil250knForm" property="treeFormLv10" scope="request">
    <input type="hidden" name="treeFormLv10" value="<bean:write name="lv10"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="fil250knForm" property="fil010SelectDelLink" scope="request">
  <logic:iterate id="del" name="fil250knForm" property="fil010SelectDelLink" scope="request">
    <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>


<!-- HEADER -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.fil250kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil250knok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil250knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors />
  </logic:messagesPresent>
  <div class="txt_l fw_b cl_fontWarn">
    <gsmsg:write key="cmn.comments" /><gsmsg:write key="fil.fil250kn.2" />
  </div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.folder" />
      </th>
      <td class="w80">
      <div id="moveDirName"><bean:write name="fil250knForm" property="fil250DirPath" /></div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="fil.125" />
      </th>
      <td>
        <logic:notEmpty name="fil250knForm" property="fil250knCallUserList">
        <logic:iterate id="callUser" name="fil250knForm" property="fil250knCallUserList" type="UsrLabelValueBean">
        <span class="<%=callUser.getCSSClassNameNormal()%>"><bean:write name="callUser" property="label" /></span><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('fil250knok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil250knback');">
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