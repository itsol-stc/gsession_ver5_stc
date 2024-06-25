<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cir.GSConstCircular"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="wml.wml160.05" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/circular/cir170">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cir150keyword" />
<html:hidden property="cir150group" />
<html:hidden property="cir150user" />
<html:hidden property="cir150pageTop" />
<html:hidden property="cir150pageBottom" />
<html:hidden property="cir150svKeyword" />
<html:hidden property="cir150svGroup" />
<html:hidden property="cir150svUser" />
<html:hidden property="cir150sortKey" />
<html:hidden property="cir150order" />
<html:hidden property="cir150searchFlg" />

<html:hidden property="cir170InitFlg" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountSid" />
<html:hidden property="cirAccountMode" />

<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

 <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.wml160.05" />
        </li>
        <!-- ボタン -->
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('importAccountConfirm');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <!-- コンテンツ -->
      <table class="table-left w100">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.capture.file" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <span class="fs_13">
              <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
              <% String csvFileMsg = "<a href=\"../circular/cir170.do?CMD=import_account&sample=1\">" + gsMsg.getMessage(request, "wml.wml160.04") + "</a>"; %>
              <span>*<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg %>" /></span>
            </span>
            <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
              pluginId="<%= GSConstCircular.PLUGIN_ID_CIRCULAR %>"
              tempDirId="cir170" />
          </td>
        </tr>
      </table>
      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('importAccountConfirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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