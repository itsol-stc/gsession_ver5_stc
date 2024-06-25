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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd220.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="tcd.57" /></title>
</head>
<body>
<html:form action="/timecard/tcd220">
<input type="hidden" name="CMD" value="">
<html:hidden property="tcdDspFrom" />
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<input type="hidden" name="tcd220Row1" value="">
<input type="hidden" name="tcd220Row2" value="">
<input type="hidden" name="tcd220Row3" value="">
<input type="hidden" name="tcd220Row4" value="">
<input type="hidden" name="tcd220Row5" value="">
<input type="hidden" name="tcd220Row6" value="">
<input type="hidden" name="tcd220Row7" value="">
<input type="hidden" name="tcd220Row8" value="">
<input type="hidden" name="tcd220Row9" value="">
<input type="hidden" name="tcd220Row10" value="">
<input type="hidden" name="tcd220Row11" value="">
<input type="hidden" name="tcd220Row12" value="">
<input type="hidden" name="tcd220Row13" value="">
<input type="hidden" name="tcd220Row14" value="">
<input type="hidden" name="tcd220Row15" value="">

<input type="hidden" name="tcd220DispMonth1" value="">
<input type="hidden" name="tcd220DispMonth2" value="">
<input type="hidden" name="tcd220DispMonth3" value="">
<input type="hidden" name="tcd220DispMonth4" value="">
<input type="hidden" name="tcd220DispMonth5" value="">
<input type="hidden" name="tcd220DispMonth6" value="">
<input type="hidden" name="tcd220DispMonth7" value="">
<input type="hidden" name="tcd220DispMonth8" value="">
<input type="hidden" name="tcd220DispMonth9" value="">
<input type="hidden" name="tcd220DispMonth10" value="">
<input type="hidden" name="tcd220DispMonth11" value="">
<input type="hidden" name="tcd220DispMonth12" value="">
<input type="hidden" name="tcd220DispMonth13" value="">
<input type="hidden" name="tcd220DispMonth14" value="">
<input type="hidden" name="tcd220DispMonth15" value="">

<input type="hidden" name="tcd220Message1" value="">
<input type="hidden" name="tcd220Message2" value="">
<input type="hidden" name="tcd220Message3" value="">
<input type="hidden" name="tcd220Message4" value="">
<input type="hidden" name="tcd220Message5" value="">
<input type="hidden" name="tcd220Message6" value="">
<input type="hidden" name="tcd220Message7" value="">
<input type="hidden" name="tcd220Message8" value="">
<input type="hidden" name="tcd220Message9" value="">
<input type="hidden" name="tcd220Message10" value="">
<input type="hidden" name="tcd220Message11" value="">
<input type="hidden" name="tcd220Message12" value="">
<input type="hidden" name="tcd220Message13" value="">
<input type="hidden" name="tcd220Message14" value="">
<input type="hidden" name="tcd220Message15" value="">

<input type="hidden" name="tcd220Days1" value="">
<input type="hidden" name="tcd220Days2" value="">
<input type="hidden" name="tcd220Days3" value="">
<input type="hidden" name="tcd220Days4" value="">
<input type="hidden" name="tcd220Days5" value="">
<input type="hidden" name="tcd220Days6" value="">
<input type="hidden" name="tcd220Days7" value="">
<input type="hidden" name="tcd220Days8" value="">
<input type="hidden" name="tcd220Days9" value="">
<input type="hidden" name="tcd220Days10" value="">
<input type="hidden" name="tcd220Days11" value="">
<input type="hidden" name="tcd220Days12" value="">
<input type="hidden" name="tcd220Days13" value="">
<input type="hidden" name="tcd220Days14" value="">
<input type="hidden" name="tcd220Days15" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto w80">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.213" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok" value="<gsmsg:write key="cmn.ok" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" onClick="buttonPush('tcd220back');" value="<gsmsg:write key="cmn.back" />">
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
  <div class="txt_r">
    <button type="button" class="baseBtn" onClick="addNewRow();" value="<gsmsg:write key="cmn.add" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
  </div>
  <table class="table-top mt5" id="yukyuTable">
    <tr>
      <th class="w20"><gsmsg:write key="tcd.tcd220.03" /></th>
      <th class="w20"><gsmsg:write key="tcd.137" /></th>
      <th class="w55"><gsmsg:write key="cmn.warning" /><gsmsg:write key="cmn.message" /></th>
      <th class="w5"></th>
    </tr>

    <bean:define id="line2" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message2" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth2" value="">
        <logic:equal name="tcd220Form" property="tcd220Days2" value="">
          <bean:define id="line2" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>

    <bean:define id="line3" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message3" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth3" value="">
        <logic:equal name="tcd220Form" property="tcd220Days3" value="">
          <bean:define id="line3" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line4" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message4" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth4" value="">
        <logic:equal name="tcd220Form" property="tcd220Days4" value="">
          <bean:define id="line4" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line5" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message5" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth5" value="">
        <logic:equal name="tcd220Form" property="tcd220Days5" value="">
          <bean:define id="line5" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line6" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message6" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth6" value="">
        <logic:equal name="tcd220Form" property="tcd220Days6" value="">
          <bean:define id="line6" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line7" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message7" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth7" value="">
        <logic:equal name="tcd220Form" property="tcd220Days7" value="">
          <bean:define id="line7" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line8" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message8" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth8" value="">
        <logic:equal name="tcd220Form" property="tcd220Days8" value="">
          <bean:define id="line8" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line9" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message9" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth9" value="">
        <logic:equal name="tcd220Form" property="tcd220Days9" value="">
          <bean:define id="line9" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line10" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message10" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth10" value="">
        <logic:equal name="tcd220Form" property="tcd220Days10" value="">
          <bean:define id="line10" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line11" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message11" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth11" value="">
        <logic:equal name="tcd220Form" property="tcd220Days11" value="">
          <bean:define id="line11" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line12" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message12" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth12" value="">
        <logic:equal name="tcd220Form" property="tcd220Days12" value="">
          <bean:define id="line12" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line13" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message13" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth13" value="">
        <logic:equal name="tcd220Form" property="tcd220Days13" value="">
          <bean:define id="line13" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line14" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message14" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth14" value="">
        <logic:equal name="tcd220Form" property="tcd220Days14" value="">
          <bean:define id="line14" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>


    <bean:define id="line15" value="1"/>
    <logic:equal name="tcd220Form" property="tcd220Message15" value="">
      <logic:equal name="tcd220Form" property="tcd220DispMonth15" value="">
        <logic:equal name="tcd220Form" property="tcd220Days15" value="">
          <bean:define id="line15" value="0"/>
        </logic:equal>
      </logic:equal>
    </logic:equal>

    <tr class="js_line">
      <td class="txt_c">
        <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row1"/></span>
        <html:select  styleClass="js_month" name="tcd220Form" property="tcd220DispMonth1">
          <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
        </html:select>
        <gsmsg:write key="cmn.month" />
      </td>
      <td class="txt_c">
        <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days1" />">
        <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
      </td>
      <td>
        <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message1" />">
      </td>
      <td class="txt_c">
        <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      </td>
    </tr>

    <logic:equal name="line2" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row2"/></span>
          <html:select  styleClass="js_month" name="tcd220Form" property="tcd220DispMonth2">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days2" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message2" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line3" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row3"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth3">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days3" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message3" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line4" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row4"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth4">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days4" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message4" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line5" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row5"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth5">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days5" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message5" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line6" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row6"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth6">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days6" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message6" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line7" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row7"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth7">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days7" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message7" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line8" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row8"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth8">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days8" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message8" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line9" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row9"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth9">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days9" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message9" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line10" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row10"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth10">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days10" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message10" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line11" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row11"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth11">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days11" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message11" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line12" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row12"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth12">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days12" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message12" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line13" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <input type="hidden" name="tcd220Row13" class="js_num" value="<bean:write name="tcd220Form" property="tcd220Row13" />">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row13"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth13">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days13" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message13" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line14" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <input type="hidden" name="tcd220Row14" class="js_num" value="<bean:write name="tcd220Form" property="tcd220Row14" />">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row14"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth14">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days14" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message14" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>

    <logic:equal name="line15" value="1">
      <tr class="js_line">
        <td class="txt_c">
          <input type="hidden" name="tcd220Row15" class="js_num" value="<bean:write name="tcd220Form" property="tcd220Row15" />">
          <span class="js_num display_none"><bean:write name="tcd220Form" property="tcd220Row15"/></span>
          <html:select styleClass="js_month" name="tcd220Form" property="tcd220DispMonth15">
            <html:optionsCollection name="tcd220Form" property="tcd220MonthLabelList" />
          </html:select>
          <gsmsg:write key="cmn.month" />
        </td>
        <td class="txt_c">
          <input type="text" maxlength="3" class="w30 js_days" value="<bean:write name="tcd220Form" property="tcd220Days15" />">
          <gsmsg:write key="cmn.day" /><gsmsg:write key="cmn.comp.lo" />
        </td>
        <td>
          <input type="text" class="w100 js_message" maxlength="50" value="<bean:write name="tcd220Form" property="tcd220Message15" />">
        </td>
        <td class="txt_c">
          <img class="btn_classicImg-display js_del_btn" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_del_btn" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        </td>
      </tr>
    </logic:equal>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok" value="<gsmsg:write key="cmn.ok" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" onClick="buttonPush('tcd220back');" value="<gsmsg:write key="cmn.back" />">
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
