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
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enq230.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />


<title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
</head>

<body>
  <html:form action="/enquete/enq230">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="editEnqSid" value="">
    <input type="hidden" name="enqEditMode" value="">
    <input type="hidden" name="enq210editMode" value="2">

    <html:hidden property="cmd" />
    <html:hidden property="enq230initFlg" />
    <html:hidden property="enq230svType" />
    <html:hidden property="enq230svKeyword" />
    <html:hidden property="enq230svKeywordType" />
    <html:hidden property="enq230svAnony" />
    <logic:notEmpty name="enq230Form" property="enq230svPriority">
      <logic:iterate id="svPriority" name="enq230Form" property="enq230svPriority">
        <input type="hidden" name="enq230svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq230Form" property="enq230svStatus">
      <logic:iterate id="svStatus" name="enq230Form" property="enq230svStatus">
        <input type="hidden" name="enq230svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>

    <!-- 検索条件hidden -->
    <html:hidden property="enq010folder" />
    <html:hidden property="enq010subFolder" />
    <html:hidden property="enq010initFlg" />
    <html:hidden property="enq010searchDetailFlg" />
    <html:hidden property="enq010type" />
    <html:hidden property="enq010keyword" />
    <html:hidden property="enq010keywordType" />
    <html:hidden property="enq010keywordSimple" />
    <html:hidden property="enq010sendGroup" />
    <html:hidden property="enq010sendUser" />
    <html:hidden property="enq010sendInput" />
    <html:hidden property="enq010sendInputText" />
    <html:hidden property="enq010makeDateKbn" />
    <html:hidden property="enq010makeDateFromYear" />
    <html:hidden property="enq010makeDateFromMonth" />
    <html:hidden property="enq010makeDateFromDay" />
    <html:hidden property="enq010makeDateToYear" />
    <html:hidden property="enq010makeDateToMonth" />
    <html:hidden property="enq010makeDateToDay" />
    <html:hidden property="enq010resPubDateKbn" />
    <html:hidden property="enq010resPubDateFromYear" />
    <html:hidden property="enq010resPubDateFromMonth" />
    <html:hidden property="enq010resPubDateFromDay" />
    <html:hidden property="enq010resPubDateToYear" />
    <html:hidden property="enq010resPubDateToMonth" />
    <html:hidden property="enq010resPubDateToDay" />
    <html:hidden property="enq010pubDateKbn" />
    <html:hidden property="enq010pubDateFromYear" />
    <html:hidden property="enq010pubDateFromMonth" />
    <html:hidden property="enq010pubDateFromDay" />
    <html:hidden property="enq010pubDateToYear" />
    <html:hidden property="enq010pubDateToMonth" />
    <html:hidden property="enq010pubDateToDay" />
    <html:hidden property="enq010ansDateKbn" />
    <html:hidden property="enq010ansDateFromYear" />
    <html:hidden property="enq010ansDateFromMonth" />
    <html:hidden property="enq010ansDateFromDay" />
    <html:hidden property="enq010ansDateToYear" />
    <html:hidden property="enq010ansDateToMonth" />
    <html:hidden property="enq010ansDateToDay" />
    <html:hidden property="enq010statusAnsOverSimple" />
    <html:hidden property="enq010anony" />
    <html:hidden property="enq010svType" />
    <html:hidden property="enq010svKeyword" />
    <html:hidden property="enq010svKeywordSimple" />
    <html:hidden property="enq010svKeywordType" />
    <html:hidden property="enq010svSendGroup" />
    <html:hidden property="enq010svSendUser" />
    <html:hidden property="enq010svSendInput" />
    <html:hidden property="enq010svSendInputText" />
    <html:hidden property="enq010svMakeDateKbn" />
    <html:hidden property="enq010svMakeDateFromYear" />
    <html:hidden property="enq010svMakeDateFromMonth" />
    <html:hidden property="enq010svMakeDateFromDay" />
    <html:hidden property="enq010svMakeDateToYear" />
    <html:hidden property="enq010svMakeDateToMonth" />
    <html:hidden property="enq010svMakeDateToDay" />
    <html:hidden property="enq010svResPubDateKbn" />
    <html:hidden property="enq010svResPubDateFromYear" />
    <html:hidden property="enq010svResPubDateFromMonth" />
    <html:hidden property="enq010svResPubDateFromDay" />
    <html:hidden property="enq010svResPubDateToYear" />
    <html:hidden property="enq010svResPubDateToMonth" />
    <html:hidden property="enq010svResPubDateToDay" />
    <html:hidden property="enq010svPubDateKbn" />
    <html:hidden property="enq010svPubDateFromYear" />
    <html:hidden property="enq010svPubDateFromMonth" />
    <html:hidden property="enq010svPubDateFromDay" />
    <html:hidden property="enq010svPubDateToYear" />
    <html:hidden property="enq010svPubDateToMonth" />
    <html:hidden property="enq010svPubDateToDay" />
    <html:hidden property="enq010svAnsDateKbn" />
    <html:hidden property="enq010svAnsDateFromYear" />
    <html:hidden property="enq010svAnsDateFromMonth" />
    <html:hidden property="enq010svAnsDateFromDay" />
    <html:hidden property="enq010svAnsDateToYear" />
    <html:hidden property="enq010svAnsDateToMonth" />
    <html:hidden property="enq010svAnsDateToDay" />
    <html:hidden property="enq010svAnony" />
    <html:hidden property="enq010svStatusAnsOverSimple" />
    <html:hidden property="enq010pageTop" />
    <html:hidden property="enq010pageBottom" />
    <html:hidden property="enq010sortKey" />
    <html:hidden property="enq010order" />

    <logic:notEmpty name="enq230Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq230Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq230Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq230Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq230Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq230Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq230Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq230Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq230Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq230Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq230Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq230Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <bean:define id="openFolder" name="enq230Form" property="enq010folder" type="java.lang.Integer" />
    <bean:define id="openSubFolder" name="enq230Form" property="enq010subFolder" type="java.lang.Integer" />


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
          <img class="header_pluginImg" src="../enquete/images/original/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
        </li>
        <li>
          <gsmsg:write key="enq.plugin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.shared.template" />
        </li>
        <li>
          <div>
            <button type="button" value='<gsmsg:write key="cmn.add" />' class="baseBtn" onclick="buttonPush('enq230add');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" value='<gsmsg:write key="cmn.delete" />' class="baseBtn" onclick="buttonPush('enq230delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq230back');">
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
        <html:errors />
      </logic:messagesPresent>


      <!-- 検索条件 -->
      <table class="w100 table-left" cellpadding="5" id="enq010searchDetailArea">

        <!-- 種類 -->
        <tr>
          <th class="txt_c no_w">
            <gsmsg:write key="cmn.type2" />
          </th>
          <td class="no_w">
            <span>
              <html:select property="enq230type" styleClass="txt_m">
                <logic:notEmpty name="enq230Form" property="enq230TypeList">
                  <html:optionsCollection name="enq230Form" property="enq230TypeList" value="value" label="label" />
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
          <td class="no_w">
            <span class="verAlignMid">
              <html:text name="enq230Form" styleClass="wp300" property="enq230keyword" maxlength="100" size="40" />
              <html:radio name="enq230Form" property="enq230keywordType" styleClass="ml10" value="0" styleId="keyKbn_01" />
              <label for="keyKbn_01">
                <gsmsg:write key="cmn.contains.all.and" />
              </label>
              <html:radio name="enq230Form" property="enq230keywordType" styleClass="ml10" value="1" styleId="keyKbn_02" />
              <label for="keyKbn_02">
                <gsmsg:write key="cmn.orcondition" />
              </label>
            </span>
          </td>
        </tr>

        <!-- 重要度 -->
        <tr>
          <th class="txt_c no_w">
            <gsmsg:write key="enq.24" />
          </th>
          <td class="no_w">
            <span class="verAlignMid">
              <html:multibox name="enq230Form" property="enq230priority" styleId="search_juuyou_01">
                <%= String.valueOf(GSConstEnquete.JUUYOU_0) %>
              </html:multibox>
              <label for="search_juuyou_01">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <span class="original-display">
                  <i class="icon-star importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
              </label>
            </span>
            <span class="verAlignMid ml10">
              <html:multibox name="enq230Form" property="enq230priority" styleId="search_juuyou_02">
                <%= String.valueOf(GSConstEnquete.JUUYOU_1) %>
              </html:multibox>
              <label for="search_juuyou_02">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <span class="original-display">
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star_line"></i>
                </span>
              </label>
            </span>
            <span class="verAlignMid ml10">
              <html:multibox name="enq230Form" property="enq230priority" styleId="search_juuyou_03">
                <%= String.valueOf(GSConstEnquete.JUUYOU_2) %>
              </html:multibox>
              <label for="search_juuyou_03">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <span class="original-display">
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                </span>
              </label>
            </span>
          </td>
        </tr>

        <!-- 匿名 -->
        <tr>
          <th class="txt_c no_w">
            <gsmsg:write key="cmn.anonymity" />
          </th>
          <td class="no_w">
            <span class="verAlignMid">
              <html:radio name="enq230Form" property="enq230anony" value="0" styleId="search_anony_00" />
              <label for="search_anony_00">
                <gsmsg:write key="cmn.all" />
              </label>
              <html:radio name="enq230Form" property="enq230anony" value="1" styleClass="ml10" styleId="search_anony_01" />
              <label for="search_anony_01">
                <gsmsg:write key="enq.62" />
              </label>
              <html:radio name="enq230Form" property="enq230anony" value="2" styleClass="ml10" styleId="search_anony_02" />
              <label for="search_anony_02">
                <gsmsg:write key="enq.63" />
              </label>
            </span>
          </td>
        </tr>

      </table>

      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('enq230search');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>

      <!-- ページング -->
      <logic:notEmpty name="enq230Form" property="enq230pageList">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrow_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="enq230pageTop" onchange="changePage(0);">
            <html:optionsCollection name="enq230Form" property="enq230pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrow_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>

      <!-- 一覧 -->
      <table class="table-top" cellpadding="5" cellspacing="0">
        <tr>
          <th class="txt_c no_w js_tableTopCheck js_tableTopCheck-header cursor_p">
            <input type="checkbox" name="enq230allCheck" value="1" onclick="chgCheckAll('enq230allCheck', 'enq230selectEnqSid');">
          </th>
          <th class="w10">
            <span>
              <gsmsg:write key="enq.24" />
            </span>
          </th>
          <th class="w70">
            <span>
              <gsmsg:write key="cmn.title" />
            </span>
          </th>
          <th class="w10">
            <span>
              <gsmsg:write key="cmn.anonymity" />
            </span>
          </th>
          <th class="w10">
            <span>&nbsp;</span>
          </th>
        </tr>

        <logic:notEmpty name="enq230Form" property="enq230EnqueteList">
          <logic:iterate id="enqData" name="enq230Form" property="enq230EnqueteList" indexId="idx">

            <tr class="js_listHover cursor_p" id="<bean:write name="enqData" property="enqSid" />">
              <td class="txt_c txt_m no_w js_tableTopCheck">
                <html:multibox name="enq230Form" property="enq230selectEnqSid">
                  <bean:write name="enqData" property="enqSid" />
                </html:multibox>
              </td>
              <td class='txt_c txt_m js_listClick no_w'>
                <bean:define id="enqPriority" name="enqData" property="priority" type="java.lang.Integer" />
                <% if (enqPriority.intValue() == GSConstEnquete.JUUYOU_0) { %>
                <label for="search_juuyou_01">
                  <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                  <span class="original-display">
                    <i class="icon-star importance-blue"></i>
                    <i class="icon-star_line"></i>
                    <i class="icon-star_line"></i>
                  </span>
                  <% } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_1) { %>
                  <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />"> <img
                    class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                  <span class="original-display">
                    <i class="icon-star importance-yellow"></i>
                    <i class="icon-star importance-yellow"></i>
                    <i class="icon-star_line"></i>
                  </span>
                  <% } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_2) { %>
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />"> <img
                    class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                  <span class="original-display">
                    <i class="icon-star importance-red"></i>
                    <i class="icon-star importance-red"></i>
                    <i class="icon-star importance-red"></i>
                  </span>
                  <% } %>

              </td>
              <td class='txt_l txt_m js_listClick'>
                <logic:notEmpty name="enqData" property="typeName">
                  <span class="baseLabel">
                    <bean:write name="enqData" property="typeName" />
                  </span>
                </logic:notEmpty>
                <span>
                  <bean:write name="enqData" property="title" />
                </span>
              </td>
              <td class='txt_l txt_m js_listClick no_w'>
                <logic:equal name="enqData" property="annoy" value="<%= String.valueOf(GSConstEnquete.ANONYMUS_ON) %>">
                  <span>
                    <gsmsg:write key="enq.06" />
                  </span>
                </logic:equal>
              </td>
              <td class='no_w txt_c txt_m js_listClick w15'>
                <button type="button" onclick="editEnquete(<bean:write name="enqData" property="enqSid" />);" class="baseBtn" value="<gsmsg:write key="cmn.edit" />">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                  <gsmsg:write key="cmn.edit" />
                </button>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>

      <logic:notEmpty name="enq230Form" property="enq230pageList">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrow_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="enq230pageBottom" onchange="changePage(1);">
            <html:optionsCollection name="enq230Form" property="enq230pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrow_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>

      <div class="footerBtn_block mt10">
        <button type="button" value='<gsmsg:write key="cmn.add" />' class="baseBtn" onclick="buttonPush('enq230add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value='<gsmsg:write key="cmn.delete" />' class="baseBtn" onclick="buttonPush('enq230delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq230back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>