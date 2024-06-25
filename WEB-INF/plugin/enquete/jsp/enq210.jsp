<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@ page import="jp.groupsession.v2.enq.enq010.Enq010Const"%>
<%@ page import="jp.groupsession.v2.enq.enq210.Enq210Form"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../enquete/js/enq210.js?<%=GSConst.VERSION_PARAM%>"></script>

<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enqEditor.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../smail/css/smail.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />


<%
  boolean editFlg = false;
%>
<logic:equal name="enq210Form" property="enqEditMode" value="<%=String.valueOf(GSConstEnquete.EDITMODE_EDIT)%>">
  <%
    editFlg = true;
  %>
</logic:equal>

<%
  if (editFlg) {
%>
<title>GROUPSESSION <gsmsg:write key="enq.plugin" /> <gsmsg:write key="enq.enq210.02" /></title>
<%
  } else {
%>
<title>GROUPSESSION <gsmsg:write key="enq.plugin" /> <gsmsg:write key="enq.enq210.01" /></title>
<%
  }
%>

</head>
<body class="body_03" onload="changeAttached(<bean:write name="enq210Form" property="enq210AttachKbn" />);changeSeqType();">
  <html:form action="/enquete/enq210">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="cmd" value="">
    <input type="hidden" name="SEQ" value="">
    <input type="hidden" name="enq210DescText" value="">
    <input type="hidden" name="enq210templateId" value="">
    <input type="hidden" name="enq210editQueIndex" value="">
    <input type="hidden" name="enq210FrYear" value="">
    <input type="hidden" name="enq210FrMonth" value="">
    <input type="hidden" name="enq210FrDay" value="">
    <input type="hidden" name="enq210AnsYear" value="">
    <input type="hidden" name="enq210AnsMonth" value="">
    <input type="hidden" name="enq210AnsDay" value="">
    <input type="hidden" name="enq210AnsPubFrYear" value="">
    <input type="hidden" name="enq210AnsPubFrMonth" value="">
    <input type="hidden" name="enq210AnsPubFrDay" value="">
    <input type="hidden" name="enq210ToYear" value="">
    <input type="hidden" name="enq210ToMonth" value="">
    <input type="hidden" name="enq210ToDay" value="">
    
    <input type="hidden" name="enq220initFlg" value="">
    <input type="hidden" name="enq220editMode" value="">
    <input type="hidden" name="enq110DspMode" value="1">
    <input type="hidden" name="yearRangeMinFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMaxFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMinTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMaxTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">


    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp"%>
    <html:hidden property="enq210editMode" />
    <html:hidden property="enq210scrollQuestonFlg" />
    <html:hidden property="enq210queType" />
    <html:hidden property="enq210AttachKbn" />
    <html:hidden property="tempClickBtn" />

    <!-- テンプレート画面の検索条件 -->
    <html:hidden property="enq230initFlg" />
    <html:hidden property="enq230type" />
    <html:hidden property="enq230keyword" />
    <html:hidden property="enq230keywordType" />
    <html:hidden property="enq230anony" />
    <html:hidden property="enq230pageTop" />
    <html:hidden property="enq230pageBottom" />
    <html:hidden property="enq230svType" />
    <html:hidden property="enq230svKeyword" />
    <html:hidden property="enq230svKeywordType" />
    <html:hidden property="enq230svAnony" />
    <logic:notEmpty name="enq210Form" property="enq230selectEnqSid">
      <logic:iterate id="sv230SelectEnqSid" name="enq210Form" property="enq230selectEnqSid">
        <input type="hidden" name="enq230selectEnqSid" value="<bean:write name='sv230SelectEnqSid' />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq230priority">
      <logic:iterate id="sv230Priority" name="enq210Form" property="enq230priority">
        <input type="hidden" name="enq230priority" value="<bean:write name='sv230Priority' />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq230svPriority">
      <logic:iterate id="svPriority" name="enq210Form" property="enq230svPriority">
        <input type="hidden" name="enq230svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq230svStatus">
      <logic:iterate id="svStatus" name="enq210Form" property="enq230svStatus">
        <input type="hidden" name="enq230svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq210Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq210Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq210Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq210Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq210Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq210Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq210Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq210Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="enq210Seq" />
    <html:hidden property="enq210initFlg" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <bean:define id="enq210editMode" name="enq210Form" property="enq210editMode" type="java.lang.Integer" />

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
          <%
            int editMode = enq210editMode.intValue();
          %>
          <%
            if (editMode == Enq210Form.EDITMODE_NORMAL) {
          %>
          <%
            if (!editFlg) {
          %>
          <input type="hidden" name="helpPrm" value="0">
          <%
            } else {
          %>
          <input type="hidden" name="helpPrm" value="1">
          <%
            }
          %>
          <%
            } else if (editMode == Enq210Form.EDITMODE_TEMPLATE) {
          %>
          <%
            if (!editFlg) {
          %>
          <input type="hidden" name="helpPrm" value="2">
          <%
            } else {
          %>
          <input type="hidden" name="helpPrm" value="3">
          <%
            }
          %>
          <%
            } else {
          %>
          <input type="hidden" name="helpPrm" value="4">
          <%
            }
          %>
          <%
            boolean enqTemplateFlg = enq210editMode.intValue() == Enq210Form.EDITMODE_TEMPLATE;
          %>
          <%
            if (editFlg) {
          %>
          <%
            if (enqTemplateFlg) {
          %>
          <gsmsg:write key="enq.enq210.10" />
          <%
            } else {
          %>
          <gsmsg:write key="enq.enq210.02" />
          <%
            }
          %>
          <%
            } else {
          %>
          <%
            if (enqTemplateFlg) {
          %>
          <gsmsg:write key="enq.enq210.09" />
          <%
            } else {
          %>
          <gsmsg:write key="enq.enq210.01" />
          <%
            }
          %>
          <%
            }
          %>
        </li>
        <li>
          <div>
            <bean:define id="enq210folder" name="enq210Form" property="enq010folder" type="java.lang.Integer" />
            <%
              if (editFlg) {
            %>
            <button type="button" value="<gsmsg:write key="cmn.register.copy2"/>" class="baseBtn" onClick="buttonPush('enq210copyNew');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
              <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
              <gsmsg:write key="cmn.register.copy2" />
            </button>
            <%
              }
            %>
            <%
              if ((enq210editMode.intValue() == Enq210Form.EDITMODE_NORMAL && !editFlg)
                      || enq210editMode.intValue() == Enq210Form.EDITMODE_DRAFT) {
            %>
            <button type="button" value="<gsmsg:write key="cmn.save.draft" />" class="baseBtn" onclick="enq210Draft();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
              <gsmsg:write key="cmn.save.draft" />
            </button>
            <%
              }
            %>
            <%
              if (enqTemplateFlg) {
            %>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onclick="enq210Entry();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <%
              }
            %>
            <%
              if (!enqTemplateFlg) {
            %>
            <button type="button" value="<gsmsg:write key="enq.05" />" class="baseBtn" onclick="enq210Entry();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="enq.05" />
            </button>
            <%
              }
            %>
            <%
              if (editFlg) {
            %>
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="buttonPush('enq210delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <%
              }
            %>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq210back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <div class="txt_r">
        <% if (!enqTemplateFlg) { %>
         <button type="button" value="<gsmsg:write key="cmn.template" />" class="baseBtn" onclick="enq210DspTemplate();">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
           <gsmsg:write key="cmn.template" />
         </button>
         <% } %>
         <button type="button" value="<gsmsg:write key="ptl.6" />" class="baseBtn" onclick="setParams();buttonPush('enq210preview');">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="ptl.6" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="ptl.6" />">
           <gsmsg:write key="ptl.6" />
         </button>
      </div>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>


      <logic:equal name="enq210Form" property="enq210Alert" value="1">
           <%
              if ((editFlg && !enqTemplateFlg)
                        && (editFlg && (enq210editMode.intValue() != Enq210Form.EDITMODE_DRAFT))
                        && (editFlg && (enq210editMode.intValue() != Enq210Form.EDITMODE_TEMPLATE))) {
            %>
          <div class="txt_l">
          <span class="cl_fontWarn">
            <gsmsg:write key="cmn.warning" />
            :
            <gsmsg:write key="enq.66" />
            </span>
            </div>
           <%
              }
            %>
      </logic:equal>

      <table class="table-left">

        <!-- 基本情報 重要度 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="project.prj050.4" />
          </th>
          <td class="txt_l" colspan="3">
            <div class="display_inline-block txt_m">
              <span class="verAlignMid">
              <html:radio name="enq210Form" property="enq210Juuyou" value="0" styleId="search_juuyou_0" onclick="changeJuuyou2(0);" />
              <label for="search_juuyou_0">
                <gsmsg:write key="project.58" />
              </label>
              <html:radio styleClass="ml10" name="enq210Form" property="enq210Juuyou" value="1" styleId="search_juuyou_1" onclick="changeJuuyou2(1);" />
              <label for="search_juuyou_1">
                <gsmsg:write key="project.59" />
              </label>
              <html:radio styleClass="ml10" name="enq210Form" property="enq210Juuyou" value="2" styleId="search_juuyou_2" onclick="changeJuuyou2(2);" />
              <label for="search_juuyou_2">
                <gsmsg:write key="project.60" />
              </label>
              </span>
            </div>

            <div class="display_inline-block txt_m ml10">
              <div id="star_1" class="star">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <span class="original-display">
                  <i class="icon-star importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
              </div>
              <div id="star_2" class="star">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <span class="original-display">
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star_line"></i>
                </span>
              </div>
              <div id="star_3" class="star">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <span class="original-display">
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                </span>
              </div>
            </div>
            </span>
          </td>
        </tr>

        <tr>
          <!-- 基本情報 種類 -->
          <%
            String strColVal = "1";
          %>
          <%
            String strWidth = "w30";
          %>
          <%
            if (enqTemplateFlg) {
                  strColVal = "3";
                  strWidth = "w90";
                }
          %>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="cmn.type2" />
          </th>
          <td class="<%=strWidth%>" colspan="<%=strColVal%>">
            <span>
              <html:select property="enq210Syurui" styleClass="ver_align_middle">
                <logic:notEmpty name="enq210Form" property="enqTypeList">
                  <html:optionsCollection name="enq210Form" property="enqTypeList" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
            </span>
          </td>
          <!-- 基本情報 発信者 -->
          <%
            if (!enqTemplateFlg) {
          %>
          <th class="txt_l no_w">
            <gsmsg:write key="cir.2" />
          </th>
          <td>
            <span>
              <html:select property="enq210Send" styleClass="txt_m wp200">
                <logic:notEmpty name="enq210Form" property="enqSenderList">
                  <html:optionsCollection name="enq210Form" property="enqSenderList" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
              &nbsp;&nbsp;&nbsp;&nbsp;
            </span>
          </td>
          <%
            }
          %>
        </tr>

        <!-- 基本情報 タイトル -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="cmn.title" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.asterisk" />
            </span>
          </th>
          <td colspan="3">
            <span>
              <html:text name="enq210Form" property="enq210Title" maxlength="100" styleClass="wp400" />
            </span>
          </td>
        </tr>

        <!-- 基本情報 説明文 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="cmn.explanation" />
          </th>
          <td colspan="3">

            <input type="hidden" name="attachment_ID_list" value="2">
            <input type="hidden" name="attachmentFileListFlg2" value="false" />
            <input type="hidden" name="attachmentMode2" value="7" />
            <input type="hidden" name="attachmentPluginId2" value="<%= GSConstEnquete.PLUGIN_ID_ENQUETE %>" />
            <input type="hidden" name="attachmentTempDirId2" value="enq210" />
            <input type="hidden" name="attachmentTempDirPlus2" value="<%= GSConstEnquete.ENQ_FILE + "/bodyFile" %>" />

            <span>
              <input type="hidden" name="enqEditorSize" value="400">
              <table>
                <tr>
                  <td class="border_none">
                    <span id="attachmentFileErrorArea2"></span>
                    <span>
                      <div class="setsumon display_blank" id="attachment_FormArea2">
                        <html:textarea name="enq210Form" property="enq210Desc" rows="5" cols="40" styleClass="text_base" styleId="enqDescArea" />
                      </div>
                      <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">
                    </span>
                  </td>
                </tr>
              </table>
            </span>
          </td>
        </tr>

        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="cmn.attached" />
          </th>
          <td colspan="3">
            <span class="display_flex verAlignMid">
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
                pluginId="<%= GSConstEnquete.PLUGIN_ID_ENQUETE %>"
                tempDirId="enq210"
                tempDirPlus="<%= GSConstEnquete.ENQ_FILE %>"
                formId="1" />
            </span>
          </td>
        </tr>

        <tr id="enq210attachPosition">
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="cmn.position" />
          </th>
          <td colspan="3">
            <span class="verAlignMid">
              <html:radio name="enq210Form" property="enq210AttachPos" value="0" styleId="iti_0" />
              <label for="iti_0">
                <gsmsg:write key="cmn.up2" />
              </label>
              <html:radio name="enq210Form" property="enq210AttachPos" value="1" styleClass="ml10" styleId="iti_1" />
              <label for="iti_1">
                <gsmsg:write key="cmn.down2" />
              </label>
            </span>
          </td>
        </tr>

        <tr id="enq210attachUrl">
          <th class="txt_c no_w" colspan="2">URL</th>
          <td colspan="3">
            <span>
              <html:text name="enq210Form" property="enq210Url" size="80" maxlength="100" />
            </span>
          </td>
        </tr>

        <%
          if (!enqTemplateFlg) {
        %>
        <!-- 基本情報 公開開始日 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="enq.53" />
          </th>
          <td colspan="3">
            <span class="verAlignMid">
              <html:text name="enq210Form" property="enq210FrDate" styleId="enq210FrDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
              <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return enq210moveDay($('#enq210FrDate')[0], 1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return enq210moveDay($('#enq210FrDate')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onclick="return enq210moveDay($('#enq210FrDate')[0], 2);">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return enq210moveDay($('#enq210FrDate')[0], 3)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
            </span>
          </td>
        </tr>

        <!-- 基本情報 回答期限 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="enq.19" />
          </th>
          <td colspan="3">
            <span class="verAlignMid">
              <html:text name="enq210Form" property="enq210AnsDate" styleId="enq210AnsDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
              <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return enq210moveDay($('#enq210AnsDate')[0], 1)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return enq210moveDay($('#enq210AnsDate')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <a class="fw_b todayBtn original-display" onclick="return enq210moveDay($('#enq210AnsDate')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </a>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return enq210moveDay($('#enq210AnsDate')[0], 3)">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
            </span>
          </td>
        </tr>
        <%
          }
        %>

        <!-- 基本情報 結果公開 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="enq.07" />
          </th>
          <td colspan="3">
            <div>
              <div class="verAlignMid">
                <html:checkbox name="enq210Form" property="enq210AnsOpen" value="1" styleId="koukai" onclick="return checkEnq210AnsOpen();" />
                <label for="koukai">
                  <gsmsg:write key="cmn.publish" />
                </label>
              </div>
              <div class="mt5">
                <gsmsg:write key="cmn.asterisk" />
                <gsmsg:write key="enq.enq210.14" />
              </div>
            </div>
            <%
              if (!enqTemplateFlg) {
            %>
            <div>
              <span class="verAlignMid">
                <gsmsg:write key="cmn.start" />:
                <html:text name="enq210Form" property="enq210AnsPubFrDate" styleId="enq210AnsPubFrDate" maxlength="10" styleClass="ml5 txt_c wp95 datepicker js_frDatePicker"/>
                <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <button class="webIconBtn" type="button" name="enq210AnsPubFrForward" value="&nbsp;" onclick="return enq210moveDay($('#enq210AnsPubFrDate')[0], 1)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button class="baseBtn classic-display" type="button" name="enq210AnsPubFrToday" value="<gsmsg:write key='cmn.today' />" onClick="return enq210moveDay($('#enq210AnsPubFrDate')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </button>
                <div class="js_frTodayBtnClick">
                <a class="fw_b todayBtn original-display" onclick="return enq210moveDay($('#enq210AnsPubFrDate')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
                </div>
                <button class="webIconBtn" type="button" name="enq210AnsPubFrNext" value="&nbsp;" onclick="return enq210moveDay($('#enq210AnsPubFrDate')[0], 3)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
              </span>
            </div>
            <div class="mt5">
              <span class="verAlignMid">
                <gsmsg:write key="main.src.man250.30" />:
                <html:text name="enq210Form" property="enq210ToDate" maxlength="10" styleId="enq210ToDate" styleClass="ml5 txt_c wp95 datepicker js_toDatePicker"/>
                <span class="mr5 picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <button class="webIconBtn" type="button" name="enq210ToDateForward" class="webIconBtn" value="&nbsp;" onclick="return enq210moveDay($('#enq210ToDate')[0], 1)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button class="baseBtn classic-display" type="button" name="enq210ToDateToday" value="<gsmsg:write key='cmn.today' />" onClick="return enq210moveDay($('#enq210ToDate')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </button>
                <div class="js_toTodayBtnClick">
                <a class="fw_b todayBtn original-display" onclick="return enq210moveDay($('#enq210ToDate')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
                </div>
                <button class="webIconBtn" type="button" name="enq210ToDateNext" value="&nbsp;" onclick="return enq210moveDay($('#enq210ToDate')[0], 3)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
                &nbsp;&nbsp;&nbsp;
                <html:checkbox name="enq210Form" property="enq210ToKbn" value="1" styleId="enq210ToKbn" onclick="return enq210ToDateKbn();" />
                <label for="enq210ToKbn">
                  <gsmsg:write key="enq.enq210.18" />
                </label>
              </span>
            </div>

            <%
              }
            %>
          </td>
        </tr>
        <%
          if (!enqTemplateFlg) {
        %>
        <logic:equal name="enq210Form" property="enq210pluginSmailUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE)%>">
          <!-- ショートメール通知 -->
          <tr>
            <th class="txt_l no_w" colspan="2">
              <gsmsg:write key="shortmail.notification" />
            </th>
            <td colspan="3">
              <div class="verAlignMid">
                <html:checkbox name="enq210Form" property="enq210smailInfo" value="<%=String.valueOf(Enq210Form.SML_INFO_SEND)%>" styleId="smailInfo" />
                <label for="smailInfo">
                  <gsmsg:write key="cmn.notify" />
                </label>
              </div>
              <div class="mt5">
                <gsmsg:write key="cmn.asterisk" />
                <gsmsg:write key="enq.enq210.16" />
              </div>
            </td>
          </tr>
        </logic:equal>
        <%
          }
        %>

        <!-- 匿名 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="cmn.anonymity" />
          </th>
          <td colspan="3">
            <div class="verAlignMid">
              <html:checkbox name="enq210Form" property="enq210Anony" value="1" styleId="anony" />
              <label for="anony">
                <gsmsg:write key="enq.06" />
              </label>
            </div>
            <div class="mt5">
              <gsmsg:write key="cmn.asterisk" />
              <gsmsg:write key="enq.enq210.15" />
            </div>
          </td>
        </tr>

        <!-- 基本情報 対象者 -->
        <tr>
          <th class="txt_l no_w" colspan="2">
            <gsmsg:write key="enq.10" />
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.asterisk" />
            </span>
          </th>

          <td colspan="3">
            <ui:usrgrpselector name="enq210Form" property="enq210answerListUI" styleClass="hp215" />
          </td>
        </tr>
      </table>

      <!-- 設問情報 タイトル -->
      <div class="fs_16 txt_l fw_b mt20">
        <gsmsg:write key="enq.04" />
      </div>

      <table>
        <tr>
          <td class="pt5 txt_t w10">
            <!-- 追加ボタン -->
            <table class="table-top mt0">
              <th class="txt_c">
                <span class="no_w">
                  <gsmsg:write key="enq.67" />
                </span>
              </th>
              <tr class="js_listHover cursor_p" onclick="addQuestion(1);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="enq.enq210.03" /></span>
                </td>
              </tr>
              <tr class="js_listHover cursor_p" onclick="addQuestion(2);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="enq.enq210.04" /></span>
                </td>
              </tr>
              <tr class="js_listHover cursor_p" onclick="addQuestion(3);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="enq.enq210.05" /></span>
                </td>
              </tr>
              <tr class="js_listHover cursor_p"  onclick="addQuestion(4);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="enq.enq210.05" /></span><br><span class="cl_linkDef"><gsmsg:write key="enq.68" /></span>
                </td>
              </tr>
              <tr class="js_listHover cursor_p" onclick="addQuestion(5);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="enq.enq210.07" /></span>
                </td>
              </tr>
              <tr class="js_listHover cursor_p"  onclick="addQuestion(6);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="enq.enq210.08" /></span>
                </td>
              </tr>
              <tr class="js_listHover cursor_p"  onclick="addQuestion(0);">
                <td class="txt_l txt_m no_w">
                  <span class="cl_linkDef"><gsmsg:write key="cmn.comment" /></span>
                </td>
              </tr>
              </tbody>
            </table>
          </td>

          <td>&nbsp;</td>

          <td class="pt10 txt_t w90">
            <!-- 設問情報 -->
            <div class="txt_l">
              <div class="verAlignMid">
                <input type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onclick="sortQuestion('enq210upQuestion');"> <input type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="sortQuestion('enq210downQuestion');">
                <span class="ml20 verAlignMid">
                  <gsmsg:write key="enq.09" />
                  <html:radio name="enq210Form" property="enq210queSeqType" value="1" styleClass="ml10" styleId="jidou" onclick="changeSeqType();" />
                  <label for="jidou">
                    <gsmsg:write key="cmn.auto" />
                  </label>
                  <html:radio name="enq210Form" property="enq210queSeqType" value="0" styleClass="ml10" styleId="syudou" onclick="changeSeqType();" />
                  <label for="syudou">
                    <gsmsg:write key="cmn.manual" />
                  </label>
                </span>
              </div>
            </div>
            <table class="table-top w100" id="enq210question">
              <tr>
                <th>
                  <span></span>
                </th>
                <th class="w25">
                  <span>
                    <gsmsg:write key="enq.09" />
                  </span>
                </th>
                <th class="w75">
                  <span>
                    <gsmsg:write key="enq.12" />
                  </span>
                </th>
                <th></th>
                <th></th>
              </tr>

              <tbody id="enqTbl">
                <logic:notEmpty name="enq210Form" property="ebaList">
                  <input type="hidden" name="ebaListSize" value="<bean:write name="enq210Form" property="ebaListSize" />">
                  <logic:iterate id="ebaData" name="enq210Form" property="ebaList" indexId="lineIdx">
                    <bean:define id="intQueIndex" name="ebaData" property="enq210queIndex" type="java.lang.Integer" />
                    <%
                          String queIndex = String.valueOf(intQueIndex.intValue());
                        %>
                    <%
                          String radioNo = "radioNo_" + queIndex;
                        %>
                    <tr>
                      <td class="txt_c txt_m js_tableTopCheck cursor_p">
                        <html:radio name="enq210Form" property="enq210queIndex" styleId="<%=radioNo%>" value="<%=queIndex%>" />
                      </td>
                      <td class="txt_l">
                        <span id="enq210qnoMan_<%=queIndex%>">
                          <bean:write name="ebaData" property="enq210QueNo" />
                        </span>
                        <span id="enq210qnoAuto_<%=queIndex%>">
                          <logic:greaterThan name="ebaData" property="enq210AutoQueNo" value="0">
                            <bean:write name="ebaData" property="enq210AutoQueNo" />
                          </logic:greaterThan>
                        </span>
                        <logic:equal name="ebaData" property="enq210Require" value="1">
                          <br>
                          <span>
                            <gsmsg:write key="cmn.required" />
                          </span>
                        </logic:equal>
                      </td>
                      <td class="txt_l">
                        <span>
                          <gsmsg:write key="cmn.type2" />：<bean:write name="ebaData" property="enq210SyuruiName" />
                          <br>
                          <logic:notEmpty name="ebaData" property="enq210Question">
                            <gsmsg:write key="enq.12" />：<bean:write name="ebaData" property="enq210Question" />
                            <br>
                          </logic:notEmpty>
                          <logic:notEmpty name="ebaData" property="enq210QueDesc">
                            <span class="txt_t"><gsmsg:write key="ptl.8" />：</span><htmlframe:write attrClass="w100"><bean:write name="ebaData" property="enq210QueDesc" filter="false" /></htmlframe:write><br>
                          </logic:notEmpty>
                          <logic:equal name="ebaData" property="enq210QueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_SINGLE)%>">
                            <gsmsg:write key="enq.enq210.12" />：
                            <logic:notEmpty name="ebaData" property="queSubList">
                              <logic:iterate id="queChoice" name="ebaData" property="queSubList">
                               [<bean:write name="queChoice" property="enqDspName" />]
                              </logic:iterate>
                            </logic:notEmpty>
                            <br>
                          </logic:equal>
                          <logic:equal name="ebaData" property="enq210QueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_MULTIPLE)%>">
                            <gsmsg:write key="enq.enq210.12" />：
                            <logic:notEmpty name="ebaData" property="queSubList">
                              <logic:iterate id="queChoice" name="ebaData" property="queSubList">
                                [<bean:write name="queChoice" property="enqDspName" />]
                              </logic:iterate>
                            </logic:notEmpty>
                            <br>
                          </logic:equal>
                          <logic:notEmpty name="ebaData" property="enq210AttachName">
                            <gsmsg:write key="cmn.attached" />：<bean:write name="ebaData" property="enq210AttachName" />
                            <br>
                          </logic:notEmpty>
                          <logic:notEmpty name="ebaData" property="enq210initTxt">
                            <logic:equal name="ebaData" property="enq210QueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_TEXTAREA)%>">
                              <gsmsg:write key="ntp.10" />：<br><bean:write name="ebaData" property="enq210viewInitTxt" filter="false" />
                            </logic:equal>
                            <logic:notEqual name="ebaData" property="enq210QueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_TEXTAREA)%>">
                              <gsmsg:write key="ntp.10" />：<bean:write name="ebaData" property="enq210initTxt" />
                            </logic:notEqual>
                            <br>
                          </logic:notEmpty>
                          <logic:notEmpty name="ebaData" property="enq210rangeTxtFr">
                            <logic:notEmpty name="ebaData" property="enq210rangeTxtTo">
                              <gsmsg:write key="cmn.input.range" />：<bean:write name="ebaData" property="enq210rangeTxtFr" />～<bean:write name="ebaData" property="enq210rangeTxtTo" />
                              <br>
                            </logic:notEmpty>
                          </logic:notEmpty>
                          <logic:notEmpty name="ebaData" property="enq210unitNum">
                            <gsmsg:write key="ntp.102" />：<bean:write name="ebaData" property="enq210unitNum" />
                            <br>
                          </logic:notEmpty>
                          <logic:equal name="ebaData" property="enq210QueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_DAY)%>">
                            <logic:notEmpty name="ebaData" property="enq210initDspDate">
                              <gsmsg:write key="ntp.10" />：<bean:write name="ebaData" property="enq210initDspDate" />
                              <br>
                            </logic:notEmpty>
                            <logic:notEmpty name="ebaData" property="enq210rangeTxtFrDsp">
                              <logic:notEmpty name="ebaData" property="enq210rangeTxtToDsp">
                                <gsmsg:write key="cmn.input.range" />：<bean:write name="ebaData" property="enq210rangeTxtFrDsp" />～<bean:write name="ebaData" property="enq210rangeTxtToDsp" />
                                <br>
                              </logic:notEmpty>
                            </logic:notEmpty>
                          </logic:equal>
                        </span>
                      </td>
                      <td class="txt_c txt_m no_w">
                        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" name="btn_change" onClick="editQuestion(<%=queIndex%>);">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                          <gsmsg:write key="cmn.edit" />
                        </button>
                      </td>
                      <td class="txt_c txt_m no_w">
                        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" name="btn_delete" onClick="deleteQuestion(<%=queIndex%>);">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          <gsmsg:write key="cmn.delete" />
                        </button>
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
              </tbody>
            </table>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <%
              if (editFlg) {
            %>
        <button type="button" value="<gsmsg:write key="cmn.register.copy2"/>" class="baseBtn" onClick="buttonPush('enq210copyNew');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
          <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2"/>">
          <gsmsg:write key="cmn.register.copy2" />
        </button>
        <%
              }
            %>
        <%
              if ((enq210editMode.intValue() == Enq210Form.EDITMODE_NORMAL && !editFlg)
                      || enq210editMode.intValue() == Enq210Form.EDITMODE_DRAFT) {
            %>
        <button type="button" value="<gsmsg:write key="cmn.save.draft" />" class="baseBtn" onclick="enq210Draft();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
          <gsmsg:write key="cmn.save.draft" />
        </button>
        <%
              }
            %>
        <%
              if (enqTemplateFlg) {
            %>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onclick="enq210Entry();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <%
              }
            %>
        <%
              if (!enqTemplateFlg) {
            %>
        <button type="button" value="<gsmsg:write key="enq.05" />" class="baseBtn" onclick="enq210Entry();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.ok" />">

          <gsmsg:write key="enq.05" />
        </button>
        <%
              }
            %>
        <%
              if (editFlg) {
            %>
        <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="buttonPush('enq210delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <%
              }
            %>
        <%
              if (!enqTemplateFlg) {
            %>
        <%
              }
            %>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq210back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  <div class="display_n" id="enq210_template" title="">
    <div class="of_a wp450 hp300">
      <table class="w100 h100">
        <tr>
          <td class="txt_c txt_t w90">
            <table class="table-top mt0 mb0 table_col-even">
              <logic:notEmpty name="enq210Form" property="enq210TemplatelList">
                <logic:iterate id="templateData" name="enq210Form" property="enq210TemplatelList" indexId="templateIdx">
                  <tr class ="js_listHover cursor_p"  id="<bean:write name="templateData" property="value" />">
                    <td class="js_listClick txt_l w100">
                      <span class="cl_linkDef"><bean:write name="templateData" property="label" />
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </table>
          </td>
        </tr>
      </table>
    </div>
  </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>