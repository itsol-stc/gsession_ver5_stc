<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<%
  String maxLengthMail = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_MAIL);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../anpi/js/anp150.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="anp.plugin" /> <gsmsg:write key="anp.anp150.01" /></title>
</head>
<body>
  <html:form action="/anpi/anp150">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic"> <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp150.01" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp150excute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp150back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <div class="txt_l">
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>
      </div>
      <table class="table-left">
        <tr>
          <th class="w20">
            <gsmsg:write key="cmn.target" />
          </th>
          <td class="w80">
            <div class="verAlignMid">
              <html:radio styleId="anp150Obj_0" name="anp150Form" property="anp150TargetKbn" value="<%=String.valueOf(jp.groupsession.v2.anp.anp150.Anp150Form.TAISYO_ALL)%>" onclick="changeUrlKbn();" />
              <label for="anp150Obj_0" class="mr10">
                <gsmsg:write key="cmn.alluser" />
              </label>
              <html:radio styleId="anp150Obj_1" name="anp150Form" property="anp150TargetKbn" value="<%=String.valueOf(jp.groupsession.v2.anp.anp150.Anp150Form.TAISYO_SELECT)%>" onclick="changeUrlKbn();" />
              <label for="anp150Obj_1">
                <gsmsg:write key="anp.anp150.02" />
              </label>
            </div>
            <br>
            <div class="verAlignMid">
              <html:checkbox name="anp150Form" property="anp150PassKbn" value="1" styleId="anp150PassKbn" />
              <label for="anp150PassKbn">
                <gsmsg:write key="sml.sml180.07" />
              </label>
            </div>

            <div id="js_selectTarget" class="pt10">
              <ui:usrgrpselector name="anp150Form" property="anp150TargetListUI" styleClass="hp215" />
            </div>
          </td>
        </tr>
        <!-- メール転送設定 -->
        <tr>
          <th class="w20">
            <gsmsg:write key="anp.anp150.03" />
          </th class="w80">
          <td>
            <gsmsg:write key="anp.anp150.04" />
            <div class="mt5">
              <gsmsg:write key="anp.anp150.05" />
            </div>
            <div class="verAlignMid">
              <logic:notEmpty name="anp150Form" property="anp150MailLabel">
                <html:select name="anp150Form" property="anp150SelectMail" onchange="changeEnableDisable();">
                  <html:optionsCollection name="anp150Form" property="anp150MailLabel" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
              <html:text name="anp150Form" maxlength="50" property="anp150OtherMail" styleClass="wp350 ml5" />
            </div>
          </td>
        </tr>
        <tr>
          <th class="w20">
            <gsmsg:write key="cmn.overwrite" />
          </th>
          <td class="w80">
            <span class="verAlignMid">
              <html:checkbox name="anp150Form" property="anp150UpdateFlg" value="1" styleId="updateKbn" />
              <label for="updateKbn">
                <gsmsg:write key="anp.anp150.06" />
              </label>
            </span>
          </td>
        </tr>
      </table>

      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp150excute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp150back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>
</html:html>