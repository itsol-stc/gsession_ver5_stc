<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>

<gsjsmsg:js filename="gsjsmsg.js" />

</head>

<body class="body_03" >

<html:form action="/chat/cht110">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

<logic:equal name="cht110Form" property="cht100ProcMode" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:notEqual name="cht110Form" property="cht100ProcMode" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:notEqual>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="cgiSid" />
<html:hidden property="cht100InitFlg" />
<html:hidden property="cht100ProcMode" />
<html:hidden property="cht100SortKey" />
<html:hidden property="cht100OrderKey" />
<html:hidden property="cht100PageTop" />
<html:hidden property="cht100PageBottom" />

<html:hidden property="cht100SearchFlg" />
<html:hidden property="cht100Keyword" />
<html:hidden property="cht100AndOr" />
<html:hidden property="cht100GroupId" />
<html:hidden property="cht100GroupName" />
<html:hidden property="cht100GroupInfo" />
<html:hidden property="cht100Category" />
<html:hidden property="cht100StatusKbn" />
<html:hidden property="cht100SelectGroup" />
<html:hidden property="cht100SelectUser" />
<html:hidden property="cht100AdminMember" />
<html:hidden property="cht100GeneralMember" />
<html:hidden property="cht100CreateYearFr" />
<html:hidden property="cht100CreateMonthFr" />
<html:hidden property="cht100CreateDayFr" />
<html:hidden property="cht100CreateYearTo" />
<html:hidden property="cht100CreateMonthTo" />
<html:hidden property="cht100CreateDayTo" />
<html:hidden property="cht100UpdateYearFr" />
<html:hidden property="cht100UpdateMonthFr" />
<html:hidden property="cht100UpdateDayFr" />
<html:hidden property="cht100UpdateYearTo" />
<html:hidden property="cht100UpdateMonthTo" />
<html:hidden property="cht100UpdateDayTo" />

<html:hidden property="svCht100Keyword" />
<html:hidden property="svCht100AndOr" />
<html:hidden property="svCht100GroupId" />
<html:hidden property="svCht100GroupName" />
<html:hidden property="svCht100GroupInfo" />
<html:hidden property="svCht100Category" />
<html:hidden property="svCht100StatusKbn" />
<html:hidden property="svCht100SelectGroup" />
<html:hidden property="svCht100SelectUser" />
<html:hidden property="svCht100AdminMember" />
<html:hidden property="svCht100GeneralMember" />
<html:hidden property="svCht100CreateYearFr" />
<html:hidden property="svCht100CreateMonthFr" />
<html:hidden property="svCht100CreateDayFr" />
<html:hidden property="svCht100CreateYearTo" />
<html:hidden property="svCht100CreateMonthTo" />
<html:hidden property="svCht100CreateDayTo" />
<html:hidden property="svCht100UpdateYearFr" />
<html:hidden property="svCht100UpdateMonthFr" />
<html:hidden property="svCht100UpdateDayFr" />
<html:hidden property="svCht100UpdateYearTo" />
<html:hidden property="svCht100UpdateMonthTo" />
<html:hidden property="svCht100UpdateDayTo" />
<html:hidden property="cht110createDate" />
<html:hidden property="cht110updateDate" />
<html:hidden property="cht110InitFlg" />

<logic:notEmpty name="cht110Form" property="oldMemberSids">
  <logic:iterate id="member" name="cht110Form" property="oldMemberSids">
    <input type="hidden" name="oldMemberSids" value="<bean:write name="member" />">
  </logic:iterate>
</logic:notEmpty>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>

    <logic:equal name="cht110Form" property="cht100ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_ADD)%>">
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht110.04" />
      </li>
    </logic:equal>
    <logic:equal name="cht110Form" property="cht100ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_EDIT)%>">
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="user.133" />
      </li>
    </logic:equal>

    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="cht110Form" property="cht100ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_EDIT)%>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
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
    <!-- チャットグループID -->
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cht.01" /><gsmsg:write key="main.src.man220.6" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="cht110Form" property="cht110groupId" maxlength="20" styleClass="wp250" />
      </td>
    </tr>
    <!-- カテゴリ -->
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.category" /></span>
      </th>
      <td class="w75">
        <logic:notEmpty name="cht110Form" property="cht110CategoryList">
          <html:select property="cht110category" styleId="category" styleClass="mwp150">
            <html:optionsCollection name="cht110Form" property="cht110CategoryList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <!-- チャットグループ名 -->
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cht.01" /><gsmsg:write key="cmn.group.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="cht110Form" property="cht110groupName" maxlength="100" styleClass="wp300" />
      </td>
    </tr>
    <!-- メンバー -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.member" />
      </th>
      <td class="w75">
        <ui:usrgrpselector name="cht110Form" property="cht110memberUI" styleClass="hp300" />
      </td>
    </tr>
    <!-- 備考 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <textarea name="cht110biko" property="" maxlength="500" rows="5" class="wp600" id="chtBiko"><bean:write name="cht110Form" property="cht110biko" /></textarea>
      </td>
    </tr>
    <logic:equal name="cht110Form" property="cht100ProcMode" value="<%= String.valueOf(GSConstChat.CHAT_MODE_EDIT) %>">
      <!-- 状態区分 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="anp.state" /><gsmsg:write key="tcd.tcd070.03" />
        </th>
        <td class="w75">
          <div class="verAlignMid">
            <html:checkbox name="cht110Form" property="cht110compFlg" value="<%= String.valueOf(GSConstChat.CHAT_ARCHIVE_MODE) %>" styleId="cht110compFlg" />
            <label for="cht110compFlg"><gsmsg:write key="cht.cht110.01" /></label>
          </div>
        </td>
      </tr>
      <!-- 作成日時 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="rng.37" />
        </th>
        <td class="w75">
          <bean:write name="cht110Form" property="cht110createDate" />
        </td>
      </tr>
      <!-- 最終投稿日時 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="bbs.bbsMain.2" />
        </th>
        <td class="w75">
          <bean:write name="cht110Form" property="cht110updateDate" />
        </td>
      </tr>
    </logic:equal>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="cht110Form" property="cht100ProcMode" value="<%=String.valueOf(GSConstChat.CHAT_MODE_EDIT)%>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>

</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</html:form>
</body>
</html:html>
