<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/nippou/ntp120">
<input type="hidden" name="CMD" value="">
<html:hidden property="dspMod" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--BODY -->

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.14" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
  <div class="display_inline w100 bor1 bgC_body">
    <div class="w20 m0 p0">
      <table class="w100 h100">
        <tr class="ntp_sideMenu txt_l borC_light" onclick="buttonPush2('nippou')">
          <td class="ntp_menuIcon w5">
            <img class="header_pluginImg-classic ml5" src="../nippou/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
            <img class="header_pluginImg ml5" src="../nippou/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="cmn.shortmail" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush2('anken');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.11" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush2('target');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_target_25.png" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_target_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.12" /></span>
          </td>
        </tr>

        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('bunseki');">
          <td class="ntp_menuIcon  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_bunseki.gif" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_bunseki_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.13" /></span>
          </td>
        </tr>

        <tr class="ntp_sideMenu-select bgC_Body txt_l borC_body borBottomC_light borTopC_light">
          <td class="ntp_menuIcon-select  w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_mente.gif" alt="<gsmsg:write key="main.login.history" />">
            <img class="btn_originalImg-display ml5" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="main.login.history" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.14" /></span>
          </td>
        </tr>
        <tr class="h100 ntp_sideMenuArea bor_r1 borC_light">
          <td class="nippou_menuArea h100">&nbsp;</td>
          <td class="nippou_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <table class="table-top w70 mrl_auto border_none">
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp130');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_shohin2.gif" />
            <img class="btn_originalImg-display ml5" src="../common/images/original/icon_contena_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.58" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120ShohinCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120ShohinDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp140');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_gyoushu.gif" />
            <img class="btn_originalImg-display ml5" src="../common/images/original/icon_gyousyu_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.61" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120GyoushuCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120GyoushuDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp150');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_process.gif" />
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_process_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.62" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120ProcessCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120ProcessDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp170');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_bunrui.gif" />
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_bunrui_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.3" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120KtBunruiCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120KtBunruiDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp180');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_houhou.gif" />
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_houhou_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.121" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120KtHouhouCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120KtHouhouDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp190');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_contact.gif" />
            <img class="btn_originalImg-display ml5" src="../common/images/original/icon_call_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.65" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120ContactCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120ContactDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
        <tr class="cursor_p bgC_body" onClick="return buttonPush2('ntp230');">
          <td class="border_right_none border_left_none">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_target.png" />
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_target_32.png" />
          </td>
          <td class="w45 txt_l txt_b cl_linkDef border_right_none border_left_none">
            <gsmsg:write key="ntp.12" />
          </td>
          <td class="w25 txt_l fw_b txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120TargetCnt" /><gsmsg:write key="cmn.number" />
          </td>
          <td class="w30 txt_r txt_b border_right_none border_left_none">
            <bean:write name="ntp120Form" property="ntp120TargetDay" /> <gsmsg:write key="cmn.update" />
          </td>
        </tr>
        <tr>
          <td class="border_none bgC_body" colspan="4">
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
</html:form>
</td></tr></table>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>