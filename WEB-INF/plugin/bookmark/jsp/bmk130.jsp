<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="bmk.43" /> <gsmsg:write key="cmn.number.display.settings" /></title>
</head>

<body>
<html:form action="/bookmark/bmk130">
<input type="hidden" name="CMD" value="">
<html:hidden name="bmk130Form" property="backScreen" />
<html:hidden name="bmk130Form" property="bmkBackMainFlg" />

<logic:notEmpty name="bmk130Form" property="bmk010delInfSid" scope="request">
<logic:iterate id="item" name="bmk130Form" property="bmk010delInfSid" scope="request">
  <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
</logic:iterate>
</logic:notEmpty>


<%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp" %>

<logic:notEqual name="bmk130Form" property="bmk130MyKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_YES)%>">
<logic:notEmpty name="bmk130Form" property="bmk130ViewBmkList">
  <logic:iterate id="ViewBmk" name="bmk130Form" property="bmk130ViewBmkList">
    <input type="hidden" name="bmk130ViewBmkList" value="<bean:write name="ViewBmk" />">
  </logic:iterate>
</logic:notEmpty>
</logic:notEqual>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="bmk.43" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
       <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('bmk130commit');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
         <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk130back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.number.display" />
      </th>
      <td class="w75">
        <gsmsg:write key="bmk.42" /><br>
        <html:select name="bmk130Form" property="bmk130DspCount">
          <html:optionsCollection name="bmk130Form" property="bmk130DspCountList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <span>
          <gsmsg:write key="cmn.setting.main.view" />
        </span>
      </th>

      <td class="txt_l w100">
        <gsmsg:write key="bmk.bmk120.01" />
        <div class="mt5">
            <span class="verAlignMid">
                <gsmsg:write key="bmk.30" />
                :&nbsp;
                <html:radio name="bmk130Form" property="bmk130MyKbn" styleId="bmk130MyKbn1" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_YES)%>" onclick="buttonPush('redraw');" />
                <label for="bmk130MyKbn1">
                    <gsmsg:write key="cmn.display.ok" />
                </label>
                <html:radio name="bmk130Form" property="bmk130MyKbn" styleClass="ml10" styleId="bmk130MyKbn0" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_NO)%>" onclick="buttonPush('redraw');" />
                <label for="bmk130MyKbn0">
                    <gsmsg:write key="cmn.dont.show" />
                </label>
            </span>
            <logic:equal name="bmk130Form" property="bmk130MyKbn" value="1">
              <ui:sortingselector name="bmk130Form" property="bmk130ViewBmkListUI" styleClass="hp215" onchange="buttonPush('changeViewBmk');" />
            </logic:equal>
        </div>
        <div class="settingForm_separator">
            <span class="verAlignMid">
            <gsmsg:write key="bmk.24" />
            :&nbsp;
            <html:radio name="bmk130Form" property="bmk130NewKbn" styleId="bmk130NewKbn1" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_YES)%>" onclick="" />
            <label for="bmk130NewKbn1">
                <gsmsg:write key="cmn.display.ok" />
            </label>
            <html:radio name="bmk130Form" property="bmk130NewKbn" styleClass="ml10" styleId="bmk130NewKbn0" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_NO)%>" onclick="" />
            <label for="bmk130NewKbn0">
                <gsmsg:write key="cmn.dont.show" />
            </label>
            </span>
        </div>
        <div class="settingForm_separator">
            <gsmsg:write key="bmk.bmk140.04" />
            :
            <html:select property="bmk130newCnt">
            <html:optionsCollection name="bmk130Form" property="bmk130newCntLabel" value="value" label="label" />
            </html:select>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.setting" />" onClick="return buttonPush('bmk130commit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
       <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk130back');">
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