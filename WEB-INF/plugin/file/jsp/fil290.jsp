<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String cabUse      = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_PRIVATE_USE);
   String cabNotUse   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_PRIVATE_NOT_USE);
   String cabAuthAll  = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_AUTH_ALL);
   String cabAuthUser = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_AUTH_USER);
   String capaKbnOff  = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CAPA_KBN_OFF);
   String capaKbnOn   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CAPA_KBN_ON);
   String verKbnOn    = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_ON);
   String verKbnOff   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_KBN_OFF);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" />　<gsmsg:write key="cmn.preferences" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../file/js/fil290.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="showOrHide();">

<html:form action="/file/fil290">
<input type="hidden" name="CMD" value="">
<html:hidden property="backDsp" />
<html:hidden property="backScreen" />
<html:hidden property="filSearchWd" />
<html:hidden property="fil010SelectCabinet" />
<html:hidden property="fil010SelectDirSid" />
<html:hidden property="fil040SelectDel" />
<html:hidden property="fil010SelectDelLink" />

<html:hidden property="fil290initFlg" />
<html:hidden property="fil290VerVisible" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.150" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('fil290ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil290back');">
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
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="fil.fil290.1" />
      </th>
      <!-- 個人キャビネット使用  -->
      <td class="w80">
        <span class="verAlignMid">
          <html:radio name="fil290Form" styleId="verKbn_01" property="fil290CabinetUseKbn" value="<%= cabUse %>" onclick="showOrHide();" /><label for="verKbn_01"><gsmsg:write key="fil.fil290.2" /></label>
          <html:radio styleClass="ml10" name="fil290Form" styleId="verKbn_02" property="fil290CabinetUseKbn" value="<%= cabNotUse %>" onclick="showOrHide();" /><label for="verKbn_02"><gsmsg:write key="fil.fil290.3" /></label>
        </span>
        <div id="hide0">
          <!-- 個人キャビネット使用設定 使用しない -->
        </div>

        <div id="show0">
          <!-- 個人キャビネット使用設定 使用する -->
          <span class="cl_fontWarn"><gsmsg:write key="fil.fil290.4" /></span>
        </div>
      </td>
    </tr>
    <!-- キャビネット使用許可  -->
    <tr id="rowCabinetAuth">
      <th class="no_w">
        <gsmsg:write key="fil.fil290.5" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="fil290Form" property="fil290CabinetAuthKbn" styleId="okini0" value="<%= cabAuthAll %>" onclick="showOrHide();"/><label for="okini0"><gsmsg:write key="fil.fil290.6" /></label>
          <html:radio styleClass="ml10" name="fil290Form" property="fil290CabinetAuthKbn" styleId="okini1" value="<%= cabAuthUser %>" onclick="showOrHide();"/><label for="okini1"><gsmsg:write key="fil.fil290.7" /></label>
        </span>
        <div id="hide1">
          <!-- 「全ユーザに許可する」選択 → コンボボックス非表示 -->
        </div>

      <div id="show1">
        <!-- 「指定ユーザだけ許可する」選択 → コンボボックス表示  -->
        <ui:usrgrpselector name="fil290Form" property="fil290CabinetUserUI" styleClass="hp215" />
      </div>

      </td>
    </tr>
<!-- 容量制限設定  -->
    <tr id="rowCapaKbn">
      <th>
        <gsmsg:write key="fil.3" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="fil290Form" property="fil290CapaKbn" styleId="disksize0" value="<%= capaKbnOff %>" onclick="showOrHide();"/><label for="disksize0"><gsmsg:write key="cmn.noset" /></label>
          <html:radio styleClass="ml10" name="fil290Form" property="fil290CapaKbn" styleId="disksize1" value="<%= capaKbnOn %>" onclick="showOrHide();"/><label for="disksize1"><gsmsg:write key="cmn.setting.do" /></label>
        </span>

        <div id="hide2">
          <!-- 容量制限設定 制限しない選択 → 制限容量＋警告設定コンボ は非表示 -->
        </div>

        <div id="show2">
          <!-- 容量制限設定 制限する選択 → 制限容量＋警告設定コンボ を表示 -->
          <div>
            <div class="wp180 display_inline-block">
              <gsmsg:write key="fil.4" />：
            </div>
            <div class="display_inline-block">
              <html:text name="fil290Form" maxlength="8" property="fil290CapaSize" styleClass="wp100 mr5" /><gsmsg:write key="wml.wml040.07" />
            </div>
          </div>
          <div>
            <div class="wp180 display_inline-block">
             <gsmsg:write key="fil.fil030kn.1" />：
            </div>
            <div class="display_inline-block">
              <html:select name="fil290Form" property="fil290CapaWarn" styleClass="wp100 mt5" >
                <logic:notEmpty name="fil290Form" property="fil290CapaWarnLavel">
                  <html:optionsCollection name="fil290Form" property="fil290CapaWarnLavel" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
            </div>
          </div>
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.not.specified.nowarn" /></span>
        </div>
      </td>
    </tr>
    <!-- バージョン管理  -->
    <logic:equal name="fil290Form" property="fil290VerVisible" value="<%= verKbnOn %>" >
    <tr id="rowVerKbn">
      <th>
        <gsmsg:write key="fil.5" />
      </th>
      <td>
        <gsmsg:write key="fil.fil030.3" />：
        <html:select name="fil290Form" property="fil290VerKbn" styleClass="wp80" >
          <logic:notEmpty name="fil290Form" property="fil290VerKbnLavel">
            <html:optionsCollection name="fil290Form" property="fil290VerKbnLavel" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('fil290ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil290back');">
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