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
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script type="text/javascript" src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enq010.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/jquery.infieldlabel.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>

<link rel=stylesheet href='../smail/css/smail.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
</head>

<body onunload="callSmailWindowClose();">
  <html:form action="/enquete/enq010">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="enqEditMode" value="">
    <input type="hidden" name="editEnqSid" value="">
    <input type="hidden" name="ansEnqSid" value="">
    <input type="hidden" name="enq010smailEnquate" value="">
    <input type="hidden" name="enq010ansedSendKbn" value="">
    <input type="hidden" name="enq210initFlg" value="">
    <input type="hidden" name="enq210templateId" value="">

    <html:hidden property="enq010folder" />
    <html:hidden property="enq010subFolder" />
    <html:hidden property="enq010initFlg" />
    <html:hidden property="enq010searchDetailFlg" />
    <html:hidden property="enq010sortKey" />
    <html:hidden property="enq010order" />
    <html:hidden property="enq010svType" />
    <html:hidden property="enq010svKeywordSimple" />
    <html:hidden property="enq010svKeyword" />
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
    <html:hidden property="enq010makeDateFromYear" />
    <html:hidden property="enq010makeDateFromMonth" />
    <html:hidden property="enq010makeDateFromDay" />
    <html:hidden property="enq010makeDateToYear" />
    <html:hidden property="enq010makeDateToMonth" />
    <html:hidden property="enq010makeDateToDay" />
    <html:hidden property="enq010svResPubDateKbn" />
    <html:hidden property="enq010svResPubDateFromYear" />
    <html:hidden property="enq010svResPubDateFromMonth" />
    <html:hidden property="enq010svResPubDateFromDay" />
    <html:hidden property="enq010svResPubDateToYear" />
    <html:hidden property="enq010svResPubDateToMonth" />
    <html:hidden property="enq010svResPubDateToDay" />
    <html:hidden property="enq010resPubDateFromYear" />
    <html:hidden property="enq010resPubDateFromMonth" />
    <html:hidden property="enq010resPubDateFromDay" />
    <html:hidden property="enq010resPubDateToYear" />
    <html:hidden property="enq010resPubDateToMonth" />
    <html:hidden property="enq010resPubDateToDay" />
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
    <html:hidden property="enq010ansDateFromYear" />
    <html:hidden property="enq010ansDateFromMonth" />
    <html:hidden property="enq010ansDateFromDay" />
    <html:hidden property="enq010ansDateToYear" />
    <html:hidden property="enq010ansDateToMonth" />
    <html:hidden property="enq010ansDateToDay" />
    <html:hidden property="enq010svAnony" />
    <html:hidden property="enq010svStatusAnsOverSimple" />
    <input type="hidden" name="yearRangeMinFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMaxFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMinTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMaxTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />

    <logic:notEmpty name="enq010Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq010Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq010Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq010Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq010Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq010Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>


    <bean:define id="openFolder" name="enq010Form" property="enq010folder" type="java.lang.Integer" />
    <bean:define id="openSubFolder" name="enq010Form" property="enq010subFolder" type="java.lang.Integer" />
    <%
      boolean receiveFolder = openFolder.intValue() == Enq010Const.FOLDER_RECEIVE;
    %>
    <%
      boolean sendFolder = openFolder.intValue() == Enq010Const.FOLDER_SEND;
    %>
    <%
      boolean draftFolder = openFolder.intValue() == Enq010Const.FOLDER_DRAFT;
    %>
    <%
      boolean templateFolder = openFolder.intValue() == Enq010Const.FOLDER_TEMPLATE;
    %>

    <%
      if ((receiveFolder || sendFolder) && openSubFolder.intValue() > 0) {
    %>
    <input type="hidden" name="enq010status" value="<%=openSubFolder%>">
    <%
      }
    %>
    <bean:define id="openDetailSearch" name="enq010Form" property="enq010searchDetailFlg" type="java.lang.Integer" />

    <html:hidden property="cmd" />

    <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
    <input type="hidden" name="helpPrm" value='<%=openFolder%><%=openSubFolder%><bean:write name="enq010Form" property="enq010searchDetailFlg"/>'>


    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
          <img class="header_pluginImg" src="../enquete/images/original/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
        </li>
        <li>
          <gsmsg:write key="enq.plugin" />
        </li>
        <li class="pageTitle_subFont">
          <%
            if (openFolder.intValue() == Enq010Const.FOLDER_RECEIVE) {
          %>
          <gsmsg:write key="cmn.receive" />
          <%
            } else if (openFolder.intValue() == Enq010Const.FOLDER_SEND) {
          %>
          <gsmsg:write key="enq.13" />
          <%
            } else if (openFolder.intValue() == Enq010Const.FOLDER_DRAFT) {
          %>
          <gsmsg:write key="cmn.draft" />
          <%
            } else if (openFolder.intValue() == Enq010Const.FOLDER_TEMPLATE) {
          %>
          <gsmsg:write key="cmn.template" />
          <%
            }
          %>
        </li>
        <li>
        </li>
      </ul>
    </div>
    <div class="wrapper_2column">

      <div class="side-left fs_13">

        <!-- BODY -->
        <!-- アンケートフォルダ -->
        <div id="enq_folder_area" class="side_header cursor_p side_header-folding">
          <span class="side_headerTitle side_header-open fs_13">
            <span class="side_headerArrow"></span>
            <gsmsg:write key="enq.43" />
          </span>
        </div>

        <%
          String enq010leftMenuClass = "left_menu_child_content_area";
        %>
        <bean:define id="enq010crtUser" name="enq010Form" property="enq010crtUser" type="java.lang.Boolean" />
        <%
          boolean enqCrtUser = enq010crtUser.booleanValue();
        %>
        <%
          if (!enqCrtUser) {
                enq010leftMenuClass = "left_menu_child_content_area_once";
              }
        %>
        <div id="enq_folder_child_area" class="side_content p0 w100">
          <div class="content_div">
            <div class="side_folder-focus">
            <div class="side_folderImg side_folderImg-lineTop classic-display"></div>
            <div class="side_folderImg side_folderImg-jushin"></div>
            <div id="menu_jushin_txt" class="side-folderText folder_float" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_RECEIVE)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_NONE)%>);">
              <gsmsg:write key="cmn.receive" />
            </div>
            <div id="midoku_txt" class="midoku_txt"></div>
            </div>

            <div class="display_inline txt_b w100 side_folder-focus cursor_p" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_RECEIVE)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_UNANS)%>);">
              <div class="classic-display ml10">
                <img src="../common/images/classic/icon_left_line_pertical.png">
              </div>
              <div class="wp20 hp25 side_folderImg original-display"></div>
              <div class="side_folderImg side_folderImg-line m0 ml5"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1">
                <gsmsg:write key="anp.ans.notyet" /><logic:greaterThan name="enq010Form" property="enq010UnansCount" value="0"><span>(<bean:write name="enq010Form" property="enq010UnansCount" />)</span></logic:greaterThan>
              </div>
              <input type="hidden" name="left_menu_label_name" value="1">
            </div>

            <div class="display_inline txt_b w100 side_folder-focus cursor_p" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_RECEIVE)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_REPLIED)%>);">
              <div class="classic-display ml10">
                <img src="../common/images/classic/icon_left_line_pertical.png">
              </div>
              <div class="wp20 hp25 side_folderImg original-display"></div>
              <div class=" side_folderImg-lineBottom side_folderImg m0 ml5"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1">
                <gsmsg:write key="enq.14" />
              </div>
              <input type="hidden" name="left_menu_label_name" value="2">
            </div>
          </div>

          <!-- 発信 -->
          <div>
            <div class="side_folder-focus cursor_p">
              <div class=" side_folderImg-lineBottom side_folderImg classic-display"></div>
              <div class="side_folderImg side_folderImg-soshin"></div>
              <div class="side_folderImg-soshin"></div>
              <div class="side-folderText" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_SEND)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_NONE)%>);">
                <gsmsg:write key="enq.13" />
              </div>
            </div>

            <div class="side_folder-focus cursor_p">
              <div class="wp20 hp25 side_folderImg original-display"></div>
              <div class="wp25 hp25 side_folderImg classic-display"></div>
              <div class="side_folderImg side_folderImg-line m0 ml5"></div>
              <div class="side_folderImg-folder_null folder_clear_float"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_SEND)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_NOT_PUBLIC)%>);">
                <gsmsg:write key="enq.15" /><logic:greaterThan name="enq010Form" property="enq010notPublicCount" value="0"><span>(<bean:write name="enq010Form" property="enq010notPublicCount" />)</span></logic:greaterThan>
              </div>
              <input type="hidden" name="left_menu_label_name" value="<gsmsg:write key="enq.15" />">
            </div>


            <div class="side_folder-focus cursor_p">
              <div class="wp20 hp25 side_folderImg original-display"></div>
              <div class="wp25 hp25 side_folderImg classic-display"></div>
              <div class="side_folderImg side_folderImg-line m0 ml5"></div>
              <div class="side_folderImg-folder_null folder_clear_float"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_SEND)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_PUBLIC)%>);">
                <gsmsg:write key="enq.77" /><logic:greaterThan name="enq010Form" property="enq010publicCount" value="0"><span>(<bean:write name="enq010Form" property="enq010publicCount" />)</span></logic:greaterThan>
              </div>
              <input type="hidden" name="left_menu_label_name" value="<gsmsg:write key="enq.77" />">
            </div>

            <div class="side_folder-focus cursor_p">
              <div class="wp20 hp25 side_folderImg original-display"></div>
              <div class="wp25 hp25 side_folderImg classic-display"></div>
              <div class="side_folderImg side_folderImg-line m0 ml5"></div>
              <div class="side_folderImg-folder_null folder_clear_float"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_SEND)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_COMP_ANS)%>);">
                <gsmsg:write key="enq.16" />
              </div>
              <input type="hidden" name="left_menu_label_name" value="<gsmsg:write key="enq.16" />">
            </div>


            <div class="side_folder-focus cursor_p">
              <div class="wp20 hp25 side_folderImg original-display"></div>
              <div class="wp25 hp25 side_folderImg classic-display"></div>
              <div class="side_folderImg side_folderImg-line m0 ml5"></div>
              <div class="side_folderImg-folder_null folder_clear_float"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_SEND)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_COMP_PUB)%>);">
                <gsmsg:write key="enq.17" />
              </div>
              <input type="hidden" name="left_menu_label_name" value="<gsmsg:write key="enq.17" />">
            </div>

            <div class="side_folder-focus cursor_p hp25" onclick="changeFolder(<%=String.valueOf(Enq010Const.FOLDER_DRAFT)%>, <%=String.valueOf(Enq010Const.SUBFOLDER_NONE)%>);">
              <div class="verAlignMid">
              <div class="wp20 side_folderImg original-display"></div>
              <div class="wp25 side_folderImg classic-display"></div>
              <div class="side_folderImg side_folderImg-lineBottom m0 ml5"></div>
              <span>
                <img class="original-display mr5 pb5" src="../common/images/original/icon_folder_box.png" alt="">
              </span>
              <div class="side_folderImg side_folderImg-folder ml0 classic-display"></div>
              <div class="side_folderImg-folder_null folder_clear_float"></div>
              <div class="side-folderText mail_box_lable_txt folder_float changeLabelDir" id="1">
                <gsmsg:write key="cmn.draft" /><logic:greaterThan name="enq010Form" property="enq010draftCount" value="0">(<bean:write name="enq010Form" property="enq010draftCount" />)</span></logic:greaterThan>
            </div>
            </div>
            </div>
          </div>
        </div>
        <% if (enqCrtUser) { %>
        <!-- テンプレート -->

        <div id="enq_template_area" class="side_header cursor_p side_header-folding">
           <span class="side_headerTitle side_header-close fs_13 txt_m">
           <span class="side_headerArrow"></span>
           <gsmsg:write key="cmn.shared.template" />

           <span class="side_editBtn flo_r mr5 cl_fontMiddle classic-display side_confGear" onclick="setParams();buttonPush('editTemplate');"><gsmsg:write key="cmn.setting" /></span>

           <span class="side_editBtn flo_r mr5 fs_18 original-display side_confGear" onclick="setParams();buttonPush('editTemplate');">
            <i class="icon-setting"></i>
          </span>
        </div>

        <logic:notEmpty name="enq010Form" property="enq010TemplateList">
          <div id="enq_template_child_area" class="side_content p0 w100 display_n">
              <table class="w100">
                <logic:notEmpty name="enq010Form" property="enq010TemplateList">
                  <logic:iterate id="template" name="enq010Form" property="enq010TemplateList">
                    <tr id="<bean:write name="template" property="emnSid" />">
                      <td class="js_listClick pl10 pt5 cursor_p">
                        <a class="template_sel_txt" id='<bean:write name="template" property="emnSid" />' href="#" onclick='editTemplate(<bean:write name="template" property="emnSid" />);'>
                        <div class="dspTempName lh130"><bean:write name="template" property="viewEmnTitle" filter="false"/></div>
                        <span class="tooltips display_none"><gsmsg:write key="cmn.type2" />：<bean:write name="template" property="etpName" />
                          <gsmsg:write key="cmn.title" />：<bean:write name="template" property="emnTitle" />
                        </span>
                      </a>
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
              </table>
          </div>
        </logic:notEmpty>

        <logic:empty name="enq010Form" property="enq010TemplateList">
          <div class="left_menu_content_area menu_head_area_none"></div>
        </logic:empty>

        <%
          }
        %>

      </div>



      <!-- 右 -->
      <div class="main">

        <table class="w100 pageTitle_sub">
          <tr>
            <td class="w30 txt_l mt50 no_w">
              <% if (openFolder.intValue() == Enq010Const.FOLDER_RECEIVE) { %>
              <span class="sub_title">
                <gsmsg:write key="cmn.receive" />
              </span>
              <% if (openSubFolder.intValue() == Enq010Const.SUBFOLDER_UNANS) { %>
              <span class="sub_title">
                <gsmsg:write key="anp.ans.notyet" />
              </span>
              <% } else if (openSubFolder.intValue() == Enq010Const.SUBFOLDER_REPLIED) { %>
              <span class="sub_title">
                <gsmsg:write key="enq.14" />
              </span>
              <%  } %>
              <%  } else if (openFolder.intValue() == Enq010Const.FOLDER_SEND) { %>
              <span class="sub_title">
                <gsmsg:write key="enq.13" />
              </span>
              <% if (openSubFolder.intValue() == Enq010Const.SUBFOLDER_NOT_PUBLIC) { %>
              <span class="sub_title">
                <gsmsg:write key="enq.15" />
              </span>
              <% } else if (openSubFolder.intValue() == Enq010Const.SUBFOLDER_PUBLIC) { %>
              <span class="sub_title">
                <gsmsg:write key="enq.77" />
              </span>
              <%  } else if (openSubFolder.intValue() == Enq010Const.SUBFOLDER_COMP_ANS) { %>
              <span class="sub_title">
                <gsmsg:write key="enq.16" />
              </span>
              <% } else if (openSubFolder.intValue() == Enq010Const.SUBFOLDER_COMP_PUB) { %>
              <span class="sub_title">
                <gsmsg:write key="enq.17" />
              </span>
              <% } %>
              <% } else if (openFolder.intValue() == Enq010Const.FOLDER_DRAFT) {  %>
              <span class="sub_title">
                <gsmsg:write key="cmn.draft" />
              </span>
              <% } else if (openFolder.intValue() == Enq010Const.FOLDER_TEMPLATE) { %>
              <span class="sub_title">
                <gsmsg:write key="cmn.template" />
              </span>
              <% } %>
            </td>
            <td class="w70 mb10 txt_r no_w">
                <span class="verAlignMid">
              <% if (openDetailSearch.intValue() == Enq010Const.SEARCH_DETAIL_OFF) { %>
              <span id="simpleSearch" class="verAlignMid">
                <%
                  } else {
                %>
                <span id="simpleSearch" class="display_n">
                  <% } %>
                  <% if (openFolder.intValue() == Enq010Const.FOLDER_RECEIVE) { %>
                    <html:multibox name="enq010Form" property="enq010statusAnsOverSimple" styleId="search_simple_joutai_ansover" onclick="chkAnsOverSimple();">
                      <%=String.valueOf(Enq010Const.SEARCH_ANSFLGOK_ONLY)%>
                    </html:multibox>
                    <label class="mr5" for="search_simple_joutai_ansover">
                      <gsmsg:write key="enq.enq010.06" />
                    </label>
                  <% } %>
                  <html:text name="enq010Form" property="enq010keywordSimple" maxlength="100" styleClass="wp200 mb5 ml5 mr5" />
                  <button type="submit" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('enq010SearchSimple');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                    <gsmsg:write key="cmn.search" />
                  </button>
                </span>
                <div>
                <button type="button" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.advanced.search" />" onClick="enq010changeSearch();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
                  <gsmsg:write key="cmn.advanced.search" />
                </button>
                <%
                  if (enqCrtUser) {
                %>
                <button type="button" name="" class="baseBtn" value="<gsmsg:write key="cmn.add2" />" onclick="addEnquete();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add2" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.add2" />">
                  <gsmsg:write key="cmn.add2" />
                </button>
                <%
                  }
                %>
                <%
                  if (!receiveFolder) {
                %>
                <logic:notEmpty name="enq010Form" property="enq010EnqueteList">
                  <%
                    if (enqCrtUser) {
                  %>
                  <button type="button" name="" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('enq010delEnquete');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                  <%
                    }
                  %>
                </logic:notEmpty>
                <%
                  }
                %>
                </div>
                </span>
            </td>
          </tr>
        </table>

        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>

        <div>
          <!-- 検索条件 -->
          <%
            if (openDetailSearch != Enq010Const.SEARCH_DETAIL_OFF) {
          %>
          <table cellpadding="5" class="table-left w100" id="enq010searchDetailArea">
            <%
              } else {
            %>
            <table cellpadding="5" class="table-left w100 display_n" id="enq010searchDetailArea">
              <%
                }
              %>

              <!-- 種類 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="cmn.type2" />
                </th>
                <td class="no_w">
                  <span>
                    <html:select property="enq010type">
                      <logic:notEmpty name="enq010Form" property="enqTypeList">
                        <html:optionsCollection name="enq010Form" property="enqTypeList" value="value" label="label" />
                      </logic:notEmpty>
                    </html:select>
                  </span>
                </td>
              </tr>

              <!-- キーワード -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="cmn.keyword" />
                </th>
                <td class="no_w">
                  <span class="verAlignMid txt_m">
                    <html:text name="enq010Form" property="enq010keyword" maxlength="100" styleClass="wp300" />
                    <html:radio name="enq010Form" property="enq010keywordType" value="0" styleClass="ml10" styleId="keyKbn_01" />
                    <label for="keyKbn_01">
                      <gsmsg:write key="cmn.contains.all.and" />
                    </label>
                    &nbsp;
                    <html:radio name="enq010Form" property="enq010keywordType" value="1" styleId="keyKbn_02" />
                    <label for="keyKbn_02">
                      <gsmsg:write key="cmn.orcondition" />
                    </label>
                  </span>
                </td>
              </tr>

              <%
                if (!templateFolder) {
              %>
              <!-- 発信者 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="cir.2" />
                </th>
                <td class="no_w">
                  <span>
                    <span>
                      <gsmsg:write key="cmn.group" />
                    </span>
                    <html:select styleClass="wp150" property="enq010sendGroup" onchange="buttonPush('init');">
                      <logic:notEmpty name="enq010Form" property="enqSendGroupList">
                        <html:optionsCollection name="enq010Form" property="enqSendGroupList" value="value" label="label" />
                      </logic:notEmpty>
                    </html:select>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <span>
                      <gsmsg:write key="cmn.user" />
                    </span>
                    <html:select property="enq010sendUser">
                      <logic:notEmpty name="enq010Form" property="enqSendUserList">
                        <logic:iterate id="user" name="enq010Form" property="enqSendUserList" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
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
                    &nbsp;&nbsp;&nbsp;&nbsp;
                  </span>
                  <div class="textfield mt5 mb0">
                    <label for="enq010sendInputText">
                      <span class="cl_fontMiddle pl5">
                        <gsmsg:write key="cmn.search.item2" />
                      </span>
                    </label>
                    <html:text name="enq010Form" property="enq010sendInputText" maxlength="20" styleClass="text_base" styleId="enq010sendInputText" style="width:273px;" />
                  </div>
                </td>
              </tr>
              <%
                }
              %>

              <%
                if (sendFolder || draftFolder) {
              %>
              <!-- 作成日 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="man.creation.date" />
                </th>
                <td class="no_w">
                  <span class="verAlignMid">
                    <html:radio name="enq010Form" property="enq010makeDateKbn" value="0" styleId="enq010makeDateFromKbn0" onclick="enq010chkSrhDate(1);" />
                    <label for="enq010makeDateFromKbn0">
                      <gsmsg:write key="cmn.not.specified" />
                    </label>
                    <html:radio name="enq010Form" property="enq010makeDateKbn" value="1" styleClass="ml10" styleId="enq010makeDateFromKbn1" onclick="enq010chkSrhDate(1);" />
                    <label for="enq010makeDateFromKbn1">
                      <gsmsg:write key="wml.wml010.12" />
                    </label>
                  </span>

                  <div id="enq010makeDateArea" class="display_flex">
                    <html:text name="enq010Form" property="enq010makeDateFrom" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                    <span class="ml5 mr5">～</span>
                    <html:text name="enq010Form" property="enq010makeDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                    </div>
                </td>
              </tr>
              <%
                }
              %>

              <%
                if (!templateFolder) {
              %>

              <%--         <!-- 公開期間 -->
        <tr>
        <td width="10%" align="center" class="td_gray text_header" nowrap><gsmsg:write key="cmn.open.period" /></td>
        <td class="td_type20" nowrap>
        <span class="text_base2">
        <html:radio name="enq010Form" property="enq010pubDateKbn" value="0" styleId="enq010pubDateKbn0" onclick="enq010chkSrhDate(2);" /><label for="enq010pubDateKbn0"><gsmsg:write key="cmn.not.specified" /></label>
        <html:radio name="enq010Form" property="enq010pubDateKbn" value="1" styleId="enq010pubDateKbn1" onclick="enq010chkSrhDate(2);" /><label for="enq010pubDateKbn1"><gsmsg:write key="wml.wml010.12" /></label>
        </span>

        <span id="enq010pubDateArea">
        <html:select property="enq010pubDateFromYear" styleId="enq010pubDateFromYear">
        <html:optionsCollection name="enq010Form" property="yearCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq010pubDateFromMonth" styleId="enq010pubDateFromMonth">
        <html:optionsCollection name="enq010Form" property="monthCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq010pubDateFromDay" styleId="enq010pubDateFromDay">
        <html:optionsCollection name="enq010Form" property="dayCombo" value="value" label="label" />
        </html:select>
        <input type="button" value="Cal" name="pubDateFromCalendarBtn" onclick="wrtCalendar(this.form.enq010pubDateFromDay, this.form.enq010pubDateFromMonth, this.form.enq010pubDateFromYear);" class="baseBtn">
            ～

        <html:select property="enq010pubDateToYear" styleId="enq010pubDateToYear">
        <html:optionsCollection name="enq010Form" property="yearCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq010pubDateToMonth" styleId="enq010pubDateToMonth">
        <html:optionsCollection name="enq010Form" property="monthCombo" value="value" label="label" />
        </html:select>&nbsp;
        <html:select property="enq010pubDateToDay" styleId="enq010pubDateToDay">
        <html:optionsCollection name="enq010Form" property="dayCombo" value="value" label="label" />
        </html:select>
        <input type="button" value="Cal" name="pubDateToCalendarBtn" onclick="wrtCalendar(this.form.enq010pubDateToDay, this.form.enq010pubDateToMonth, this.form.enq010pubDateToYear);" class="baseBtn">
        </span>
        </td>
        </tr>
 --%>
              <!-- 回答期限 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="enq.19" />
                </th>
                <td class="no_w">

                  <span class="verAlignMid">
                    <html:radio name="enq010Form" property="enq010ansDateKbn" value="0" styleId="enq010ansDateKbn0" onclick="enq010chkSrhDate(3);" />
                    <label for="enq010ansDateKbn0">
                      <gsmsg:write key="cmn.not.specified" />
                    </label>
                    <html:radio name="enq010Form" property="enq010ansDateKbn" styleClass="ml10" value="1" styleId="enq010ansDateKbn1" onclick="enq010chkSrhDate(3);" />
                    <label class="mr10" for="enq010ansDateKbn1">
                      <gsmsg:write key="wml.wml010.12" />
                    </label>
                  </span>

                  <div id="enq010ansDateArea" class="display_flex">
                    <html:text name="enq010Form" property="enq010ansDateFrom" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                    <span class="ml5 mr5">～</span>
                    <html:text name="enq010Form" property="enq010ansDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </div>
                </td>
              </tr>
              <%
                if (!templateFolder) {
              %>
              <!-- 結果公開期間 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="enq.enq210.11" />
                </th>
                <td class="no_w">
                  <span class="verAlignMid">
                    <html:radio name="enq010Form" property="enq010resPubDateKbn" value="0" styleId="enq010resPubDateKbn0" onclick="enq010chkSrhDate(4);" />
                    <label for="enq010resPubDateKbn0">
                      <gsmsg:write key="cmn.not.specified" />
                    </label>
                    <html:radio name="enq010Form" property="enq010resPubDateKbn" value="1" styleClass="ml10" styleId="enq010resPubDateKbn1" onclick="enq010chkSrhDate(4);" />
                    <label for="enq010resPubDateKbn1">
                      <gsmsg:write key="wml.wml010.12" />
                    </label>
                  </span>

                  <div id="enq010resPubDateArea" class="display_flex">
                    <html:text name="enq010Form" property="enq010resPubDateFrom" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                    <span class="ml5 mr5">～</span>
                    <html:text name="enq010Form" property="enq010resPubDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </div>
                </td>
              </tr>
              <%
                }
              %>
              <%
                }
              %>

              <!-- 重要度 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="project.prj050.4" />
                </td>
                <td class="no_w">
                 <span class="verAlignMid">
                  <html:multibox name="enq010Form" property="enq010priority" styleId="search_juuyou_01">
                    <%=String.valueOf(GSConstEnquete.JUUYOU_0)%>
                  </html:multibox>
                  <label for="search_juuyou_01">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_blue.png" class="star4" border="0" alt="<gsmsg:write key="project.58" />">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_white.png" class="star4" border="0" alt="<gsmsg:write key="project.58" />">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_white.png" class="star4" border="0" alt="<gsmsg:write key="project.58" />">
                    <span class="original-display">
                      <i class="icon-star importance-blue"></i>
                      <i class="icon-star_line"></i>
                      <i class="icon-star_line"></i>
                     </span>
                  </label>
                  </span>
                   <span class="verAlignMid ml10">

                  <html:multibox name="enq010Form" property="enq010priority" styleId="search_juuyou_02">
                    <%=String.valueOf(GSConstEnquete.JUUYOU_1)%>
                  </html:multibox>
                  <label for="search_juuyou_02">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_gold.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_gold.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_white.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
                    <span class="original-display">
                      <i class="icon-star importance-yellow"></i>
                      <i class="icon-star importance-yellow"></i>
                      <i class="icon-star_line"></i>
                    </span>
                  </label>
                  </span>
                   <span class="verAlignMid ml10">

                  <html:multibox name="enq010Form" property="enq010priority" styleId="search_juuyou_03">
                    <%=String.valueOf(GSConstEnquete.JUUYOU_2)%>
                  </html:multibox>
                  <label for="search_juuyou_03">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_red.png" class="star3" border="0" alt="<gsmsg:write key="project.60" />">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_red.png" class="star3" border="0" alt="<gsmsg:write key="project.60" />">
                    <img class="classic-display mb5" src="../common/images/classic/icon_star_red.png" class="star3" border="0" alt="<gsmsg:write key="project.60" />">
                    <span class="original-display">
                      <i class="icon-star importance-red"></i>
                      <i class="icon-star importance-red"></i>
                      <i class="icon-star importance-red"></i>
                    </span>
                  </label>
                  </span>
                </td>
              </tr>

              <%
                if ((receiveFolder || sendFolder) && openSubFolder.intValue() <= 0) {
              %>
              <!-- 状態 -->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="anp.state" />
                </th>
                <td class="no_w">
                  <span class="verAlignMid">
                    <%
                      if (receiveFolder) {
                    %>
                    <html:multibox name="enq010Form" property="enq010status" styleId="search_joutai_receive_01">
                      <%=String.valueOf(Enq010Const.STATUS_NOTANS)%>
                    </html:multibox>
                    <label for="search_joutai_receive_01">
                      <gsmsg:write key="anp.ans.notyet" />
                    </label>
                    <html:multibox name="enq010Form" property="enq010status" styleClass="ml10" styleId="search_joutai_receive_02">
                      <%=String.valueOf(Enq010Const.STATUS_ANS)%>
                    </html:multibox>
                    <label for="search_joutai_receive_02">
                      <gsmsg:write key="enq.14" />
                    </label>
                    <%
                      } else if (sendFolder) {
                    %>
                    <html:multibox name="enq010Form" property="enq010status" styleId="search_joutai_send_01">
                      <%=String.valueOf(Enq010Const.STATUS_NOTPUB)%>
                    </html:multibox>
                    <label for="search_joutai_send_01">
                      <gsmsg:write key="enq.15" />
                    </label>
                    <html:multibox name="enq010Form" property="enq010status" styleClass="ml10" styleId="search_joutai_send_02">
                      <%=String.valueOf(Enq010Const.STATUS_PUB)%>
                    </html:multibox>
                    <label for="search_joutai_send_02">
                      <gsmsg:write key="enq.77" />
                    </label>
                    <html:multibox name="enq010Form" property="enq010status" styleClass="ml10" styleId="search_joutai_send_03">
                      <%=String.valueOf(Enq010Const.STATUS_ANSEXIT)%>
                    </html:multibox>
                    <label for="search_joutai_send_03">
                      <gsmsg:write key="enq.16" />
                    </label>
                    <html:multibox name="enq010Form" property="enq010status" styleClass="ml10"  styleId="search_joutai_send_04">
                      <%=String.valueOf(Enq010Const.STATUS_PUBEXIT)%>
                    </html:multibox>
                    <label for="search_joutai_send_04">
                      <gsmsg:write key="enq.17" />
                    </label>
                    <%
                      }
                    %>
                  </span>
                </td>
              </tr>
              <%
                }
              %>
              <%
                if (receiveFolder) {
              %>
              <!-- 回答 可/不可-->
              <tr>
                <th class="w10 txt_c no_w">
                  <gsmsg:write key="enq.enq010.09" />
                </th>
                <td class="no_w">
                  <span class="verAlignMid">
                    <html:multibox name="enq010Form" property="enq010statusAnsOver" styleId="search_joutai_receive_03">
                      <%=String.valueOf(Enq010Const.PUBLIC_ANSFLG_OK)%>
                    </html:multibox>
                    <label for="search_joutai_receive_03">
                      <gsmsg:write key="enq.enq010.07" />
                    </label>
                    <html:multibox name="enq010Form" property="enq010statusAnsOver" styleClass="ml10" styleId="search_joutai_receive_04">
                      <%=String.valueOf(Enq010Const.PUBLIC_ANSFLG_NG)%>
                    </html:multibox>
                    <label for="search_joutai_receive_04">
                      <gsmsg:write key="enq.enq010.08" />
                    </label>
                  </span>
                </td>
              </tr>
              <%
                }
              %>

              <%
                if (templateFolder) {
              %>
              <!-- 匿名 -->
              <tr>
                <th class="txt_c no_w">
                  <gsmsg:write key="cmn.anonymity" />
                </th>
                <td class="no_w">
                  <span class="verAlignMid">
                    <html:radio name="enq010Form" property="enq010anony" value="0" styleId="search_anony_00" />
                    <label for="search_anony_00">
                      <gsmsg:write key="cmn.all" />
                    </label>
                    <html:radio name="enq010Form" property="enq010anony" value="1" styleClass="ml10" styleId="search_anony_01" />
                    <label for="search_anony_01">
                      <gsmsg:write key="cmn.except.anonymity" />
                    </label>
                    <html:radio name="enq010Form" property="enq010anony" value="2" styleClass="ml10" styleId="search_anony_02" />
                    <label for="search_anony_02">
                      <gsmsg:write key="cmn.anonymity" />
                    </label>
                  </span>
                </td>
              </tr>
              <%
                }
              %>

            </table>

            <%
              if (openDetailSearch != Enq010Const.SEARCH_DETAIL_OFF) {
            %>
            <div class="txt_c" id="enq010searchDetailBtnArea">
              <%
                } else {
              %>
              <div class="txt_c display_n" id="enq010searchDetailBtnArea">
                <%
                  }
                %>
                <button type="button" name="btn_search" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('enq010Search');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
                  <gsmsg:write key="cmn.search" />
                </button>
              </div>
            </div>



            <%
              if (openFolder.intValue() == Enq010Const.FOLDER_RECEIVE) {
            %>
            <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq010_receive.jsp" />
            <%
              } else if (openFolder.intValue() == Enq010Const.FOLDER_SEND) {
            %>
            <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq010_send.jsp" />
            <% } else if (openFolder.intValue() == Enq010Const.FOLDER_DRAFT) { %>
            <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq010_draft.jsp" />
            <% } %>

            </td>
            </tr>

          </table>
        </div>

      </div>
</div>

      <span id="tooltip_area"></span>
  </html:form>

  <div id="dialogSmlNotice" title='<gsmsg:write key="shortmail.notification" />' class="display_n">
    <div class="verAlignMid mt15 mb0">
      <img class="classic-display mr10" src="../main/images/classic/header_info.png" alt="cmn.info">
      <img class="original-display mr10" src="../common/images/original/icon_info_32.png" alt="cmn.info">
      <gsmsg:write key="enq.enq010.03" />
    </div>
    <div class="ml40 mt5">
      <span class="verAlignMid">
        <input type="radio" name="enq010ansedSendKbn" id="sendKbn0" value="0" checked>
        <label for="sendKbn0" class="dialog_checkbox"><gsmsg:write key="enq.enq010.04" /></label>
      </span>
      <br>
      <span class="verAlignMid">
        <input type="radio" name="enq010ansedSendKbn" id="sendKbn1" value="1">
        <label for="sendKbn1" class="dialog_checkbox"><gsmsg:write key="enq.enq010.05" /></label>
      </span>
    </div>
  </div>

  <div id="smlPop" title="" class="display_none">
    <div id="smlCreateArea" class="w100 h100"></div>
  </div>

  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</body>
</html:html>