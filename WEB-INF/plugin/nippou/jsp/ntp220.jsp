<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<!--[if IE]><script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js"></script><![endif]-->
<script src="../common/js/jplot/jquery.jqplot.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.barRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.pieRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.highlighter.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.categoryAxisRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.canvasAxisTickRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.canvasTextRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.pointLabels.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.cursor.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../nippou/js/ntp220.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/htmlEscape.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href="../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../common/js/jquery-ui-1.8.16/development-bundle/themes/base/jquery.ui.all.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/nippou/ntp220">

<input type="hidden" name="CMD" value="">
<html:hidden property="dspMod" />
<html:hidden property="ntp220mode" />

<html:hidden property="ntp220State" />
<html:hidden property="ntp220AnkenState" />

<html:hidden property="ntp220NowSelParentId" />
<html:hidden property="ntp220NowSelChildId" />

<!--@BODY -->
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
      <gsmsg:write key="ntp.13" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
  <div class="display_inline w100 bor1 bgC_body">
    <div class="w20 m0 p0">
      <table class="w100 h100">
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush2('nippou');">
          <td class="ntp_menuIcon w5">
            <img class="header_pluginImg-classic ml5" src="../nippou/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="ntp.1" />">
            <img class="header_pluginImg ml5" src="../nippou/images/original/menu_icon_single_32.png" alt="<gsmsg:write key="ntp.1" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush2('anken');">
          <td class="ntp_menuIcon w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_anken_25.png" alt="<gsmsg:write key="ntp.11" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_anken_32.png" alt="<gsmsg:write key="ntp.11" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.11" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('target');">
          <td class="ntp_menuIcon w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_target_25.png" alt="<gsmsg:write key="ntp.12" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_target_32.png" alt="<gsmsg:write key="ntp.12" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.12" /></span>
          </td>
        </tr>
        <tr class="ntp_sideMenu-select bgC_Body txt_l borC_body borBottomC_light borTopC_light">
          <td class="ntp_menuIcon-select w5">
            <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_bunseki.gif" alt="<gsmsg:write key="ntp.13" />">
            <img class="btn_originalImg-display ml5" src="../nippou/images/original/icon_bunseki_32.png" alt="<gsmsg:write key="ntp.13" />">
          </td>
          <td class=" fs_16 no_w w95 lh_normal">
            <span class="timeline_menu ml5"><gsmsg:write key="ntp.13" /></span>
          </td>
        </tr>
        <logic:equal name="ntp220Form" property="adminKbn" value="1">
          <tr class="ntp_sideMenu txt_l borC_light" onClick="buttonPush('masta');">
            <td class="ntp_menuIcon w5">
              <img class="btn_classicImg-display ml5" src="../nippou/images/classic/icon_menu_mente.gif" alt="<gsmsg:write key="ntp.14" />">
              <img class="btn_originalImg-display ml5" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="ntp.14" />">
            </td>
            <td class=" fs_16 no_w w95 lh_normal">
              <span class="timeline_menu ml5"><gsmsg:write key="ntp.14" /></span>
            </td>
          </tr>
        </logic:equal>
        <tr class="h100 ntp_sideMenuArea bor_r1 borC_light">
          <td class="nippou_menuArea h100">&nbsp;</td>
          <td class="nippou_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
        </tr>
      </table>
    </div>
    <div class="ntp_mainContent bgC_body w80">
      <div class="w100 no_w">
        <logic:equal name="ntp220Form" property="ntp220mode" value="0">
        <%@ include file="/WEB-INF/plugin/nippou/jsp/ntp220_shukei.jsp" %>
        </logic:equal>
        <logic:equal name="ntp220Form" property="ntp220mode" value="1">
        <%@ include file="/WEB-INF/plugin/nippou/jsp/ntp220_kojin.jsp" %>
        </logic:equal>
      </div>
    </div>
  </div>
</div>

<span id="tooltip_area"></span>

<div id="searchBtnResultPop" style="height:550px;overflow-y:hidden;display:none;" title="<gsmsg:write key="bbs.bbs041.2" />">
  <p>
    <div id="searchBtnResultArea" style="height:450px;">
    </div>
  </p>
</div>

<input type="hidden" name="ntp220pageNum" value="1" />

</html:form>
</td>
</tr>
</table>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>