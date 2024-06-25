<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@ page import="jp.groupsession.v2.enq.enq010.Enq010Const"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../enquete/js/enquete.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../enquete/js/enq970.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script type="text/javascript" src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../smail/css/smail.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
</head>

<body class="body_03">
  <html:form action="/enquete/enq970">

    <input type="hidden" name="CMD">
    <html:hidden property="cmd" />
    <html:hidden property="backScreen" />
    <input type="hidden" name="enq970BackPage" value="1">

    <!-- 検索条件hidden -->
    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp"%>
    
    <input type="hidden" name="enq970makeDateFromYear" value="">
    <input type="hidden" name="enq970makeDateFromMonth" value="">
    <input type="hidden" name="enq970makeDateFromDay" value="">
    <input type="hidden" name="enq970makeDateToYear" value="">
    <input type="hidden" name="enq970makeDateToMonth" value="">
    <input type="hidden" name="enq970makeDateToDay" value="">
    <input type="hidden" name="enq970ansDateFromYear" value="">
    <input type="hidden" name="enq970ansDateFromMonth" value="">
    <input type="hidden" name="enq970ansDateFromDay" value="">
    <input type="hidden" name="enq970ansDateToYear" value="">
    <input type="hidden" name="enq970ansDateToMonth" value="">
    <input type="hidden" name="enq970ansDateToDay" value="">
    <input type="hidden" name="enq970resPubDateFromYear" value="">
    <input type="hidden" name="enq970resPubDateFromMonth" value="">
    <input type="hidden" name="enq970resPubDateFromDay" value="">
    <input type="hidden" name="enq970resPubDateToYear" value="">
    <input type="hidden" name="enq970resPubDateToMonth" value="">
    <input type="hidden" name="enq970resPubDateToDay" value="">
    <input type="hidden" name="yearRangeMinFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMaxFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMinTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMaxTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">

    <html:hidden property="enq970initFlg" />
    <html:hidden property="enq970searchDetailFlg" />
    <html:hidden property="enq970sortKey" />
    <html:hidden property="enq970order" />
    <html:hidden property="enq970svType" />
    <html:hidden property="enq970svKeyword" />
    <html:hidden property="enq970svKeywordType" />
    <html:hidden property="enq970svSendGroup" />
    <html:hidden property="enq970svSendUser" />
    <html:hidden property="enq970svSendInput" />
    <html:hidden property="enq970svSendInputText" />
    <html:hidden property="enq970svMakeDateKbn" />
    <html:hidden property="enq970svMakeDateFromYear" />
    <html:hidden property="enq970svMakeDateFromMonth" />
    <html:hidden property="enq970svMakeDateFromDay" />
    <html:hidden property="enq970svMakeDateToYear" />
    <html:hidden property="enq970svMakeDateToMonth" />
    <html:hidden property="enq970svMakeDateToDay" />
    <html:hidden property="enq970svPubDateKbn" />
    <html:hidden property="enq970svPubDateFromYear" />
    <html:hidden property="enq970svPubDateFromMonth" />
    <html:hidden property="enq970svPubDateFromDay" />
    <html:hidden property="enq970svPubDateToYear" />
    <html:hidden property="enq970svPubDateToMonth" />
    <html:hidden property="enq970svPubDateToDay" />
    <html:hidden property="enq970svAnsDateKbn" />
    <html:hidden property="enq970svAnsDateFromYear" />
    <html:hidden property="enq970svAnsDateFromMonth" />
    <html:hidden property="enq970svAnsDateFromDay" />
    <html:hidden property="enq970svAnsDateToYear" />
    <html:hidden property="enq970svAnsDateToMonth" />
    <html:hidden property="enq970svAnsDateToDay" />
    <html:hidden property="enq970svAnony" />
    <html:hidden property="ansEnqSid" />

    <logic:notEmpty name="enq970Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq970Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq970Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq970Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq970Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq970Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq970Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq970Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq970Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq970Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq970Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq970Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq970Form" property="enq970svPriority">
      <logic:iterate id="svPriority" name="enq970Form" property="enq970svPriority">
        <input type="hidden" name="enq970svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq970Form" property="enq970svPriority">
      <logic:iterate id="svStatus" name="enq970Form" property="enq970svStatus">
        <input type="hidden" name="enq970svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="cmd" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="helpPrm" value="0">

    <div class="kanriPageTitle w85 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="enq.enq970.01" />
        </li>
        <li>
          <div>
            <logic:notEmpty name="enq970Form" property="enq010EnqueteList">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('enq970delEnquete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:notEmpty>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.advanced.search" />" onClick="enq970changeSearch();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <gsmsg:write key="cmn.advanced.search" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq970back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <div id="enq970searchDetailArea">
        <!-- 検索条件 -->
        <table cellpadding="5" class="table-left">

          <!-- 種類 -->
          <tr>
            <th class="w10 txt_c no_w">
              <gsmsg:write key="cmn.type2" />
            </th>
            <td class="no_w">
              <span>
                <html:select property="enq970type">
                  <logic:notEmpty name="enq970Form" property="enqTypeList">
                    <html:optionsCollection name="enq970Form" property="enqTypeList" value="value" label="label" />
                  </logic:notEmpty>
                </html:select>
              </span>
            </td>
          </tr>

          <!-- キーワード -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="cmn.keyword" />
            </th>
            <td>
              <span>
                <html:text styleClass="wp300" name="enq970Form" property="enq970keyword" maxlength="100" size="40" />
                <span class="verAlignMid txt_m">
                  <html:radio name="enq970Form" property="enq970keywordType" value="0" styleId="keyKbn_01" />
                  <label for="keyKbn_01">
                    <gsmsg:write key="cmn.contains.all.and" />
                  </label>
                  <html:radio name="enq970Form" property="enq970keywordType" value="1" styleClass="ml10" styleId="keyKbn_02" />
                  <label for="keyKbn_02">
                    <gsmsg:write key="cmn.orcondition" />
                  </label>
                </span>
            </td>
          </tr>

          <!-- 発信者 -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="cir.2" />
            </th>
            <td class="no_w">
              <span>
                <span>
                  <gsmsg:write key="cmn.group" />
                </span>
                <html:select property="enq970sendGroup" styleClass="wp250" onchange="buttonPush('init');">
                  <logic:notEmpty name="enq970Form" property="enqSendGroupList">
                    <html:optionsCollection name="enq970Form" property="enqSendGroupList" value="value" label="label" />
                  </logic:notEmpty>
                </html:select>
                <span class="ml5">
                  <gsmsg:write key="cmn.user" />
                </span>
                <html:select property="enq970sendUser">
                  <logic:notEmpty name="enq970Form" property="enqSendUserList">
                    <logic:iterate id="user" name="enq970Form" property="enqSendUserList" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                      <bean:define id="mukoUserClass" value="" />
                      <logic:equal value="1" name="user" property="usrUkoFlg">
                        <bean:define id="mukoUserClass" value="mukoUserOption" />
                      </logic:equal>
                      <html:option value="<%=user.getValue()%>" styleClass="<%=mukoUserClass%>">
                        <bean:write name="user" property="label" />
                      </html:option>
                    </logic:iterate>
                  </logic:notEmpty>
                </html:select>
              </span>
              <div class="textfield mt10 mb0">
                <label for="field_id">
                  <span class="pl5 cl_fontMiddle">
                    <gsmsg:write key="cmn.search.item2" />
                  </span>
                </label>
                <html:text name="enq970Form" property="enq970sendInputText" maxlength="20" styleClass="text_base wp300" styleId="field_id" />
              </div>
            </td>
          </tr>

          <!-- 作成日 -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="man.creation.date" />
            </th>
            <td class="no_w hp40">
            <span class="verAlignMid">
              <span class="verAlignMid txt_m">
                <html:radio name="enq970Form" property="enq970makeDateKbn" value="0" styleId="enq970makeDateFromKbn0" onclick="enq970chkSrhDate(1);" />
                <label for="enq970makeDateFromKbn0">
                  <gsmsg:write key="cmn.not.specified" />
                </label>
                <html:radio name="enq970Form" property="enq970makeDateKbn" value="1" styleClass="ml10" styleId="enq970makeDateFromKbn1" onclick="enq970chkSrhDate(1);" />
                <label for="enq970makeDateFromKbn1">
                  <gsmsg:write key="wml.wml010.12" />
                </label>
              </span>

              <span id="enq970makeDateArea" class="verAlignMid">
                <html:text name="enq970Form" property="enq970makeDateFrom" maxlength="10" styleClass="txt_c ml10 wp95 datepicker js_frDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <span class="ml5 mr5">～</span>
                <html:text name="enq970Form" property="enq970makeDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </span>
              </span>
            </td>
          </tr>

          <%--         <!-- 公開期間 -->
        <tr>
        <td width="10%" align="center" class="td_gray text_header" nowrap><gsmsg:write key="cmn.open.period" /></td>
        <td class="no_w">
        <span>
        <html:radio name="enq970Form" property="enq970pubDateKbn" value="0" styleId="enq970pubDateKbn0" onclick="enq970chkSrhDate(2);" /><label for="enq970pubDateKbn0"><gsmsg:write key="cmn.not.specified" /></label>
        <html:radio name="enq970Form" property="enq970pubDateKbn" value="1" styleId="enq970pubDateKbn1" onclick="enq970chkSrhDate(2);" /><label for="enq970pubDateKbn1"><gsmsg:write key="wml.wml010.12" /></label>
        </span>

        <span id="enq970pubDateArea">
        <html:select property="enq970pubDateFromYear" styleId="enq970pubDateFromYear" styleClass="txt_m">
        <html:optionsCollection name="enq970Form" property="yearCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq970pubDateFromMonth" styleId="enq970pubDateFromMonth" styleClass="txt_m">
        <html:optionsCollection name="enq970Form" property="monthCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq970pubDateFromDay" styleId="enq970pubDateFromDay" styleClass="txt_m">
        <html:optionsCollection name="enq970Form" property="dayCombo" value="value" label="label" />
        </html:select>
        <input type="button" value="Cal" name="pubDateFromCalendarBtn" onclick="wrtCalendar(this.form.enq970pubDateFromDay, this.form.enq970pubDateFromMonth, this.form.enq970pubDateFromYear);" class="calendar_btn">
            ～

        <html:select property="enq970pubDateToYear" styleId="enq970pubDateToYear" styleClass="txt_m">
        <html:optionsCollection name="enq970Form" property="yearCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq970pubDateToMonth" styleId="enq970pubDateToMonth" styleClass="txt_m">
        <html:optionsCollection name="enq970Form" property="monthCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq970pubDateToDay" styleId="enq970pubDateToDay" styleClass="txt_m">
        <html:optionsCollection name="enq970Form" property="dayCombo" value="value" label="label" />
        </html:select>
        <input type="button" value="Cal" name="pubDateToCalendarBtn" onclick="wrtCalendar(this.form.enq970pubDateToDay, this.form.enq970pubDateToMonth, this.form.enq970pubDateToYear);" class="calendar_btn">
        </span>
        </td>
        </tr>
 --%>
          <!-- 回答期限 -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="enq.19" />
            </th>
            <td class="no_w hp40">
            <span class="verAlignMid">
              <span class="verAlignMid txt_m">
                <html:radio name="enq970Form" property="enq970ansDateKbn" value="0" styleId="enq970ansDateKbn0" onclick="enq970chkSrhDate(3);" />
                <label for="enq970ansDateKbn0">
                  <gsmsg:write key="cmn.not.specified" />
                </label>
                <html:radio name="enq970Form" property="enq970ansDateKbn" value="1" styleClass="ml10" styleId="enq970ansDateKbn1" onclick="enq970chkSrhDate(3);" />
                <label for="enq970ansDateKbn1">
                  <gsmsg:write key="wml.wml010.12" />
                </label>
              </span>

              <span id="enq970ansDateArea" class="verAlignMid">
                <html:text name="enq970Form" property="enq970ansDateFrom" maxlength="10" styleClass="txt_c ml10 wp95 datepicker js_frDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <span class="ml5 mr5">～</span>
                <html:text name="enq970Form" property="enq970ansDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </span>
              </span>
            </td>
          </tr>
          <!-- 結果公開期間 -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="enq.enq210.11" />
            </th>
            <td class="no_w hp40">
            <span class="verAlignMid">
              <span class="verAlignMid txt_m">
                <html:radio name="enq970Form" property="enq970resPubDateKbn" value="0" styleId="enq970resPubDateKbn0" onclick="enq970chkSrhDate(4);" />
                <label for="enq970resPubDateKbn0">
                  <gsmsg:write key="cmn.not.specified" />
                </label>
                <html:radio name="enq970Form" property="enq970resPubDateKbn" value="1" styleClass="ml10" styleId="enq970resPubDateKbn1" onclick="enq970chkSrhDate(4);" />
                <label for="enq970resPubDateKbn1">
                  <gsmsg:write key="wml.wml010.12" />
                </label>
              </span>

              <span id="enq970resPubDateArea" class="verAlignMid">
                <html:text name="enq970Form" property="enq970resPubDateFrom" maxlength="10" styleClass="txt_c ml10 wp95 datepicker js_frDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                 <span class="ml5 mr5">～</span>
                <html:text name="enq970Form" property="enq970resPubDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </span>
              </span>
            </td>
          </tr>

          <!-- 重要度 -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="project.prj050.4" />
            </th>
            <td class="no_w hp40">
              <span class="verAlignMid">
              <html:multibox name="enq970Form" property="enq970priority" styleId="search_juuyou_01">
                <%=String.valueOf(GSConstEnquete.JUUYOU_0)%>
              </html:multibox>
              <label class="mr10" for="search_juuyou_01">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" class="star4" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star4" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star4" alt="<gsmsg:write key="project.58" />">
                <span class="original-display">
                  <i class="icon-star importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
              </label>
              <html:multibox name="enq970Form" property="enq970priority" styleId="search_juuyou_02">
                <%=String.valueOf(GSConstEnquete.JUUYOU_1)%>
              </html:multibox>
              <label class="mr10" for="search_juuyou_02">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <span class="original-display">
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star_line"></i>
                  </span>
              </label>
              <html:multibox name="enq970Form" property="enq970priority" styleId="search_juuyou_03">
                <%=String.valueOf(GSConstEnquete.JUUYOU_2)%>
              </html:multibox>
              <label for="search_juuyou_03">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star3" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star3" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star3" alt="<gsmsg:write key="project.60" />">
                <span class="original-display">
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                </span>
              </label>
              </span>
            </td>
          </tr>

          <!-- 状態 -->
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="anp.state" />
            </th>
            <td class="no_w">
              <span class="verAlignMid">
                <html:multibox name="enq970Form" property="enq970status" styleId="search_joutai_send_01">
                  <%=String.valueOf(Enq010Const.STATUS_NOTPUB)%>
                </html:multibox>
                <label for="search_joutai_send_01">
                  <gsmsg:write key="enq.15" />
                </label>
                <html:multibox name="enq970Form" property="enq970status" styleClass="ml10" styleId="search_joutai_send_02">
                  <%=String.valueOf(Enq010Const.STATUS_PUB)%>
                </html:multibox>
                <label for="search_joutai_send_02">
                  <gsmsg:write key="enq.77" />
                </label>
                <html:multibox name="enq970Form" property="enq970status" styleClass="ml10" styleId="search_joutai_send_03">
                  <%=String.valueOf(Enq010Const.STATUS_ANSEXIT)%>
                </html:multibox>
                <label for="search_joutai_send_03">
                  <gsmsg:write key="enq.16" />
                </label>
                <html:multibox name="enq970Form" property="enq970status" styleClass="ml10" styleId="search_joutai_send_04">
                  <%=String.valueOf(Enq010Const.STATUS_PUBEXIT)%>
                </html:multibox>
                <label for="search_joutai_send_04">
                  <gsmsg:write key="enq.17" />
                </label>
              </span>
            </td>
          </tr>

        </table>
        <div class="txt_c">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('enq970Search');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>
      </div>

      <bean:define id="enqSortKey" name="enq970Form" property="enq970sortKey" type="java.lang.Integer" />
      <bean:define id="enqOrder" name="enq970Form" property="enq970order" type="java.lang.Integer" />
      <%
        String sortSign = "";
      %>
      <%
        String nextOrder = "";
      %>
      <%
        int titleSortKey = Enq010Const.SORTKEY_OPEN;
      %>

      <logic:notEmpty name="enq970Form" property="pageList">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="enq970pageTop" onchange="enq970changePage('0');">
            <html:optionsCollection name="enq970Form" property="pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>

      <table class="table-top" cellpadding="3" cellspacing="0">
        <tr>
          <th class="w5 no_w js_tableTopCheck js_tableTopCheck-header cursor_p">
            <input type="checkbox" name="enq970allCheck" value="1" onclick="chgCheckAll('enq970allCheck', 'enq970selectEnqSid');">
          </th>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="cmn.status" />
            </span>
          </th>

          <%
              jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
                  String[] widthList = { "w0", "w15", "w30", "w15", "w15", "w15" };
                  String[] titleList = { gsMsg.getMessage(request, "enq.24"),
                      gsMsg.getMessage(request, "enq.25"), gsMsg.getMessage(request, "cmn.title"),
                      gsMsg.getMessage(request, "enq.53"), gsMsg.getMessage(request, "enq.19"),
                      gsMsg.getMessage(request, "enq.enq210.11") };
                  int[] sortKeyList = { Enq010Const.SORTKEY_PRIORITY, Enq010Const.SORTKEY_SENDER,
                      Enq010Const.SORTKEY_TITLE, Enq010Const.SORTKEY_OPEN, Enq010Const.SORTKEY_ANSLIMIT,
                      Enq010Const.SORTKEY_ANS_OPEN };
                  for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
                    if (enqSortKey.intValue() == sortKeyList[titleIdx]) {
                      if (enqOrder.intValue() == 1) {
                        sortSign = "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";
                        nextOrder = "0";
                      } else {
                        sortSign = "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";
                        nextOrder = "1";
                      }
                    } else {
                      nextOrder = "0";
                      sortSign = "";
                    }
            %>
          <th class="<%=widthList[titleIdx]%> no_w">
            <a href="#" onClick="enq970ClickTitle(<%=String.valueOf(sortKeyList[titleIdx])%>, <%=nextOrder%>);" ><%=titleList[titleIdx]%><%=sortSign%></a>
            <%
                if (titleIdx == 1 && enqSortKey.intValue() == Enq010Const.SORTKEY_MAKEDATE) {
                        if (enqOrder.intValue() == 1) {
                          sortSign = "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";
                          nextOrder = "0";
                        } else {
                          sortSign = "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";
                          nextOrder = "1";
                        }
              %>
            <div><a href="#" onClick="enq970ClickTitle(<%=String.valueOf(Enq010Const.SORTKEY_MAKEDATE)%>, <%=nextOrder%>);" >
              <gsmsg:write key="man.creation.date" /><%=sortSign%></a></div>
            <%
                } else if (titleIdx == 1) {
                        {
                          nextOrder = "0";
                          sortSign = "";
                        }
              %>
            <div><a href="#" onClick="enq970ClickTitle(<%=String.valueOf(Enq010Const.SORTKEY_MAKEDATE)%>, <%=nextOrder%>);" >
              <gsmsg:write key="man.creation.date" /><%=sortSign%></a></div>
            <%
                } else if (titleIdx == 4) {
              %>
            <div>
              <gsmsg:write key="cmn.number.of.candidates" />
            </div>
            <%
                }
              %>
          </th>
          <%
              }
            %>

        </tr>
        <logic:notEmpty name="enq970Form" property="enq010EnqueteList">
          <logic:iterate id="enqData" name="enq970Form" property="enq010EnqueteList" indexId="idx" type="jp.groupsession.v2.enq.enq010.Enq010EnqueteModel">
            <tr class=" cursor_p" id="<bean:write name="enqData"  property="enqSid" />">
              <td class="no_w txt_c js_tableTopCheck" nowrap="">
                <html:multibox name="enq970Form" property="enq970selectEnqSid">
                  <bean:write name="enqData" property="enqSid" />
                </html:multibox>
              </td>
              <td class="no_w txt_c ">
                <logic:equal name="enqData" property="publicKbn" value="<%=String.valueOf(Enq010Const.PUBLIC_YES)%>">
                  <span>
                    <gsmsg:write key="enq.77" />
                  </span>
                </logic:equal>
                <logic:equal name="enqData" property="publicKbn" value="<%=String.valueOf(Enq010Const.PUBLIC_END)%>">
                  <span>
                    <gsmsg:write key="enq.17" />
                  </span>
                </logic:equal>
                <logic:equal name="enqData" property="publicKbn" value="<%=String.valueOf(Enq010Const.PUBLIC_NO)%>">
                  <span>
                    <gsmsg:write key="enq.15" />
                  </span>
                </logic:equal>
                <logic:equal name="enqData" property="publicKbn" value="<%=String.valueOf(Enq010Const.PUBLIC_ANSED)%>">
                  <span>
                    <gsmsg:write key="enq.16" />
                  </span>
                </logic:equal>
              </td>
              <td class="no_w txt_c ">
                <bean:define id="enqPriority" name="enqData" property="priority" type="java.lang.Integer" />
                <%
                    if (enqPriority.intValue() == GSConstEnquete.JUUYOU_0) {
                  %>
                <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star4" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <span class="original-display">
                  <i class="icon-star  importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
                <%
                    } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_1) {
                  %>
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star4" alt="<gsmsg:write key="project.59" />">
                <span class="original-display">
                  <i class="icon-star  importance-yellow"></i>
                  <i class="icon-star  importance-yellow"></i>
                  <i class="icon-star_line"></i>
                </span>
                <%
                    } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_2) {
                  %>
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star3" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star3" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star3" alt="<gsmsg:write key="project.60" />">
                <span class="original-display">
                  <i class="icon-star  importance-red"></i>
                  <i class="icon-star  importance-red"></i>
                  <i class="icon-star  importance-red"></i>
                </span>
                <%
                    }
                  %>
              </td>
              <td class="txt_l txt_m ">
                <bean:define id="sdFlg" name="enqData" property="senderDelFlg" type="java.lang.Boolean" />
                <span class="<%if (sdFlg) {%> text_deluser_enq<%} else if (enqData.getSenderUkoFlg() == 1) {%> mukoUser<%}%>">
                  <bean:write name="enqData" property="sender" />
                </span>
                <br>
                <span class="no_w">
                  <bean:write name="enqData" property="makeDate" />
                </span>
              </td>
              <td class="txt_l txt_m ">
                <logic:notEmpty name="enqData" property="typeName">
                  <span class="baseLabel">
                    <bean:write name="enqData" property="typeName" />
                  </span>
                </logic:notEmpty>
                <span class="cl_linkDef">
                  <a href="#" onClick="setParams();enq970viewDetail(<bean:write name="enqData" property="enqSid" />);" ><bean:write name="enqData" property="title" /></a>
                </span>
              </td>
              <td class="no_w txt_c ">
                <span>
                  <bean:write name="enqData" property="pubStartDateStr" filter='false' />
                </span>
              </td>
              <td class="no_w txt_c ">
                <span>
                  <bean:write name="enqData" property="ansLimitDate" />
                  <br>
                  <a href="#" onClick="setParams();enq970EnqResult(<bean:write name="enqData" property="enqSid" />);" ><bean:write name="enqData" property="subjects" /></a>
                  <gsmsg:write key="cmn.persons" />
                </span>
              </td>
              <td class="no_w txt_c ">
                <span>
                  <logic:equal name="enqData" property="ansOpen" value="<%=String.valueOf(GSConstEnquete.KOUKAI_ON)%>">
                    <bean:write name="enqData" property="ansPubStartDate" />
                    <logic:empty name="enqData" property="pubEndDateStr">
                      &nbsp;～<br>
                      <gsmsg:write key="main.man200.9" />
                    </logic:empty>
                    <logic:notEmpty name="enqData" property="pubEndDateStr">
                      &nbsp;～<br>
                      <bean:write name="enqData" property="pubEndDateStr" />
                    </logic:notEmpty>
                  </logic:equal>
                  <logic:notEqual name="enqData" property="ansOpen" value="<%=String.valueOf(GSConstEnquete.KOUKAI_ON)%>">
                    <gsmsg:write key="cmn.private" />
                  </logic:notEqual>
                </span>
              </td>
            </tr>

          </logic:iterate>
        </logic:notEmpty>
      </table>
      <logic:notEmpty name="enq970Form" property="pageList">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="enq970pageBottom" onchange="enq970changePage('1');">
            <html:optionsCollection name="enq970Form" property="pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>


    </div>

    <span id="tooltip_area"></span>

  </html:form>

  <div id="smlPop" title="" class="display_n">
    <div id="smlCreateArea" class="w100 h100"></div>
  </div>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>