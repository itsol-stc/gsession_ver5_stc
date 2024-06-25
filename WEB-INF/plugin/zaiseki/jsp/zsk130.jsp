<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../zaiseki/js/zsk130.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /> <gsmsg:write key="zsk.28" /></title>
</head>

<body>
<html:form action="/zaiseki/zsk130">
<input type="hidden" name="CMD" value="">
<html:hidden name="zsk130Form" property="backScreen" />
<html:hidden name="zsk130Form" property="selectZifSid" />
<html:hidden name="zsk130Form" property="uioStatus" />
<html:hidden name="zsk130Form" property="uioStatusBiko" />
<html:hidden name="zsk130Form" property="sortKey" />
<html:hidden name="zsk130Form" property="orderKey" />
<html:hidden name="zsk130Form" property="zsk130Mode" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('zsk130kakunin');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk130back');">
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
  <logic:equal name="zsk130Form" property="zsk130SortConfAble" value="true">
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.sort" />
      </th>
      <td class="w70">
        <gsmsg:write key="zsk.zsk130.01" /><br>
        <div class="verAlignMid">
          <gsmsg:write key="cmn.first.key" /><gsmsg:write key="wml.215" />
          <!-- キー1 -->
          <html:select property="zsk130SortKey1" styleClass="mr5">
          <html:optionsCollection name="zsk130Form" property="zsk130SortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="zsk130Form" styleClass="ml10" property="zsk130SortOrder1" styleId="zsk130SortOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="zsk130SortOrder10"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="zsk130Form" styleClass="ml10" property="zsk130SortOrder1" styleId="zsk130SortOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="zsk130SortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
       </div>
        <br>
        <div class="verAlignMid">
          <gsmsg:write key="cmn.second.key" /><gsmsg:write key="wml.215" />
          <!-- キー2 -->
          <html:select property="zsk130SortKey2" styleClass="mr5 mt5">
          <html:optionsCollection name="zsk130Form" property="zsk130SortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="zsk130Form" styleClass="ml10" property="zsk130SortOrder2" styleId="zsk130SortOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="zsk130SortOrder20"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="zsk130Form" styleClass="ml10" property="zsk130SortOrder2" styleId="zsk130SortOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="zsk130SortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
    </tr>
  </logic:equal>
    <tr>
      <th class="w30">
        <gsmsg:write key="zsk.36" />
      </th>
      <td>
        <gsmsg:write key="zsk.zsk100.02" />
        <div class="mt5">
          <div class="verAlignMid">
              <html:radio name="zsk130Form" property="zsk130mainDspFlg" styleId="zsk130mainDspFlg0" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAINGRP_DSP) %>" onclick="enableDisable();" /><label for="zsk130mainDspFlg0"><gsmsg:write key="cmn.display.ok" /></label>
              <html:radio name="zsk130Form" styleClass="ml10" property="zsk130mainDspFlg" styleId="zsk130mainDspFlg1" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAINGRP_NOT_DSP) %>" onclick="enableDisable();" /><label for="zsk130mainDspFlg1"><gsmsg:write key="cmn.dont.show" /></label>
          </div>
        </div>
        <div class="settingForm_separator">
          <div>
            <div class="verAlignMid">
              <gsmsg:write key="cmn.show.group" />&nbsp;:&nbsp;
              <html:select property="zsk130mainDspGpSid" styleClass="select01">
                  <logic:notEmpty name="zsk130Form" property="zsk130mainDspGpLabelList" scope="request">
                  <logic:iterate id="gpBean" name="zsk130Form" property="zsk130mainDspGpLabelList" scope="request">
                  <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                      <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>

                  <logic:notEqual name="gpBean" property="styleClass" value="0">
                      <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:notEqual>
                  </logic:iterate>
                  </logic:notEmpty>
              </html:select>
              <button class="iconBtn-border ml5" type="button" id="zsk130GroupBtn" value="" onClick="openGroupWindow(this.form.zsk130mainDspGpSid, 'zsk130mainDspGpSid', '1', '', 1)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </div>
          </div>
          <div class="mt5">
            <gsmsg:write key="zsk.zsk100.03" /><br>
            <div class="verAlignMid">
              <gsmsg:write key="cmn.first.key" /><gsmsg:write key="wml.215" />
              <!-- キー1 -->
              <html:select property="zsk130mainDspSortKey1" styleClass="mr10">
              <html:optionsCollection name="zsk130Form" property="zsk130mainDspSortKeyLabel" value="value" label="label" />
              </html:select>
              <html:radio name="zsk130Form" property="zsk130mainDspSortOrder1" styleId="zsk130mainDspSortOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="zsk130mainDspSortOrder10"><gsmsg:write key="cmn.order.asc" /></label>
              <html:radio name="zsk130Form" styleClass="ml10" property="zsk130mainDspSortOrder1" styleId="zsk130mainDspSortOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="zsk130mainDspSortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
            </div><br>
            <div class="verAlignMid">
              <gsmsg:write key="cmn.second.key" /><gsmsg:write key="wml.215" />
              <!-- キー2 -->
              <html:select property="zsk130mainDspSortKey2" styleClass="mr10 mt5">
                  <html:optionsCollection name="zsk130Form" property="zsk130mainDspSortKeyLabel" value="value" label="label" />
              </html:select>
              <html:radio name="zsk130Form" property="zsk130mainDspSortOrder2" styleId="zsk130mainDspSortOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="zsk130mainDspSortOrder20"><gsmsg:write key="cmn.order.asc" /></label>
              <html:radio name="zsk130Form" styleClass="ml10" property="zsk130mainDspSortOrder2" styleId="zsk130mainDspSortOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="zsk130mainDspSortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
            </div>
          </div>
        </div>
        <div class="settingForm_separator">
          <gsmsg:write key="zsk.zsk100.01" /><br>
          <div class="verAlignMid">
            <gsmsg:write key="zsk.zskmaingrep.07" />&nbsp;:&nbsp;
            <html:radio name="zsk130Form" property="zsk130mainDspSchViewDf" styleId="zsk130mainDspSchViewDf1" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_DSP) %>" /><label for="zsk130mainDspSchViewDf1"><gsmsg:write key="cmn.display.ok" /></label>
            <html:radio styleClass="ml10" name="zsk130Form" property="zsk130mainDspSchViewDf" styleId="zsk130mainDspSchViewDf2" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_NOT_DSP) %>" /><label for="zsk130mainDspSchViewDf2"><gsmsg:write key="cmn.dont.show" /></label>
          </div>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('zsk130kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('zsk130back');">
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