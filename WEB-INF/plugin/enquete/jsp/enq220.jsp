<%@page import="jp.groupsession.v2.enq.enq210.Enq210Form"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
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
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enq220.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>


<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enqEditor.js?<%=GSConst.VERSION_PARAM%>"></script>
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
</head>
<body class="body_03" onload="changeAttached(<bean:write name="enq220Form" property="enq220AttachKbn" />); initImageView('enqImgName');">
  <html:form action="/enquete/enq220">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="SEQ" value="">
    <input type="hidden" name="enq210scrollQuestonFlg" value="1">
    <input type="hidden" name="enq220QueDescText" value="">
    <input type="hidden" name="enq220deleteRow" value="">
    <input type="hidden" name="yearRangeMinFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMaxFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMinTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMaxTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />

    <html:hidden property="enq210queType" />
    <html:hidden property="enq210queIndex" />
    <html:hidden property="enq210editQueIndex" />
    <html:hidden property="enq220editMode" />
    <html:hidden property="enq220initFlg" />
    <html:hidden property="enq220viewDetailFlg" />
    <html:hidden property="enq220queId" />
    <html:hidden property="enq220files" />
    <html:hidden property="enq220AttachKbn" />
    <html:hidden property="enq220scrollQuestonFlg" />
    <html:hidden property="tempClickBtn" />


    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq210_hiddenParams.jsp"%>
    <html:hidden property="enq210editMode" />

    <logic:notEmpty name="enq220Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq220Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq220Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq220Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq220Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq220Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq220Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq220Form" property="enq210answerGroup">
      <logic:iterate id="answerUser" name="enq220Form" property="enq210answerList">
        <input type="hidden" name="enq210answerList" value="<bean:write name="answerUser" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq220Form" property="enq230selectEnqSid">
      <logic:iterate id="sv230SelectEnqSid" name="enq220Form" property="enq230selectEnqSid">
        <input type="hidden" name="enq230selectEnqSid" value="<bean:write name='sv230SelectEnqSid' />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq230priority">
      <logic:iterate id="sv230Priority" name="enq220Form" property="enq230priority">
        <input type="hidden" name="enq230priority" value='<bean:write name="sv230Priority" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq230svPriority">
      <logic:iterate id="svPriority" name="enq220Form" property="enq230svPriority">
        <input type="hidden" name="enq230svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq220Form" property="enq230svStatus">
      <logic:iterate id="svStatus" name="enq220Form" property="enq230svStatus">
        <input type="hidden" name="enq230svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="helpPrm" value='<bean:write name="enq220Form" property="enq210queType" />'>

    <bean:define id="enq210editMode" name="enq220Form" property="enq210editMode" type="java.lang.Integer" />
    <% boolean enqTemplateFlg = enq210editMode.intValue() == Enq210Form.EDITMODE_TEMPLATE; %>

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
          <logic:equal name="enq220Form" property="enq220editMode" value="1">
            <gsmsg:write key="enq.enq220.04" />
          </logic:equal>
          <logic:notEqual name="enq220Form" property="enq220editMode" value="1">
            <gsmsg:write key="enq.enq220.03" />
          </logic:notEqual>
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="enq220Entry();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq220back');">
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

      <table class="table-left" id="enq220detailArea1">
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="cmn.title" />
          </th>
          <td class="border_none"  colspan="4">
            <div class="component_bothEnd">
              <div>
                <span> <bean:write name="enq220Form" property="enq210Title" />
                </span>
              </div>
              <div>
                <button type="button" value="<gsmsg:write key='cmn.detail' />" class="baseBtn no_w" onClick="enq220changeBasicDetail(1);" id="enq210detailBtn0">
              <gsmsg:write key='cmn.detail' />
            </button></div>
            </div>
          </td>
        </tr>
      </table>

      <table class="table-left" id="enq220detailArea">
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="cmn.title" />
          </th>
          <td class="border_right_none" colspan="4">
            <div class="component_bothEnd">
            <div>
            <span>
              <bean:write name="enq220Form" property="enq210Title" />
            </span>
            </div>
            <div>
             <button type="button" value="<gsmsg:write key='cmn.close' />" class="baseBtn no_w" onClick="enq220changeBasicDetail(0);" id="enq210detailBtn1">
               <gsmsg:write key='cmn.close' />
            </button>
            </div>
            </div>
          </td>
        </tr>

        <tr>
          <!-- 基本情報 重要度 -->
          <th class="w20 txt_c no_w">
            <gsmsg:write key="enq.24" />
          </th>
          <td class="w30 no_w">
            <span>
              <logic:equal name="enq220Form" property="enq210Juuyou" value="0">
                <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <span class="original-display">
                  <i class="icon-star importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="enq220Form" property="enq210Juuyou" value="1">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <span class="original-display">
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="enq220Form" property="enq210Juuyou" value="2">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <span class="original-display">
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                </span>
              </logic:equal>
            </span>
          </td>
          <!-- 基本情報 発信者 -->
          <th class="w20 txt_c no_w">
            <gsmsg:write key="cir.2" />
          </th>
          <td class="w30">
            <span>
              <bean:write name="enq220Form" property="enq220ViewSender" />
            </span>
          </td>
        </tr>

        <!-- 基本情報 アンケート内容 -->
        <tr>
          <th class="w20 txt_c no_w">
            <gsmsg:write key="enq.18" />
          </th>
          <td class="w30" colspan="3">
            <span>
              <!-- 説明 -->
              <% boolean tempfileFlg = false; %>
              <logic:notEmpty name="enq220Form" property="enq210fileName">
                <% tempfileFlg = true; %>
              </logic:notEmpty>
              <bean:define id="attachKbn" name="enq220Form" property="enq210AttachKbn" type="java.lang.Integer" />
              <bean:define id="attachPos" name="enq220Form" property="enq210AttachPos" type="java.lang.Integer" />
              <table class="w90">
                <tr>
                  <td class="border_none p0">
                    <span>
                      <% if (attachKbn != 0 && attachPos == 0) { %>
                      <%   if (attachKbn == 1 && tempfileFlg) { %>
                      <img src='../enquete/enq220.do?CMD=enq220getImageFile' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                      <table>
                        <tr>
                          <td class="txt_l txt_m border_none">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">

                          </td>
                          <td class="txt_l txt_m border_none">
                            <a href="#" onClick="buttonPush('enq220download')" class="cl_linkDef">
                              <bean:write name="enq220Form" property="enq210fileName" />
                            </a>
                            <br>
                          </td>
                        </tr>
                      </table>
                      <%   } else if (attachKbn == 2 && tempfileFlg) { %>
                      <logic:notEmpty name="enq220Form" property="enq210fileName">
                        <table>
                          <tr>
                            <td class="txt_l txt_m border_none">
                              <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                              <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                            </td>
                            <td class="txt_l txt_m border_none">
                              <a href="#" onClick="buttonPush('enq220download')" class="cl_linkDef">
                                <bean:write name="enq220Form" property="enq210fileName" />
                              </a>
                              <br>
                            </td>
                          </tr>
                        </table>
                      </logic:notEmpty>
                      <% } %>
                      <% } %>
                      <bean:write name="enq220Form" property="enq220ViewDesc" filter="false" />
                      <% if (attachKbn != 0 && attachPos == 1) { %>
                      <%   if (attachKbn == 1 && tempfileFlg) { %>
                      <img src='../enquete/enq220.do?CMD=enq220getImageFile' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                      <table>
                        <tr>
                          <td class="txt_l txt_m">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">

                          </td>
                          <td class="txt_l txt_m">
                            <a href="#" onClick="buttonPush('enq220download')" class="cl_linkDef">
                              <bean:write name="enq220Form" property="enq210fileName" />
                            </a>
                            <br>
                          </td>
                        </tr>
                      </table>
                      <%   } else if (attachKbn == 2 && tempfileFlg) { %>
                      <logic:notEmpty name="enq220Form" property="enq210fileName">
                        <table>
                          <tr>
                            <td class="txt_l txt_m">
                              <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                              <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">

                            </td>
                            <td class="txt_l txt_m">
                              <a href="#" onClick="buttonPush('enq220download')" class="cl_linkDef">
                                <bean:write name="enq220Form" property="enq210fileName" />
                              </a>
                              <br>
                            </td>
                          </tr>
                        </table>
                      </logic:notEmpty>
                      <% } %>
                      <% } %>
                    </span>
                  </td>
                </tr>
              </table>
            </span>
          </td>
        </tr>
        <bean:define id="ansOpen" name="enq220Form" property="enq210AnsOpen" type="java.lang.Integer" />
        <% if (!enqTemplateFlg) { %>
        <!-- 基本情報 回答期限 -->
        <tr>
          <th class="w20 txt_c no_w">
            <gsmsg:write key="enq.19" />
          </th>
          <td class="w30 no_w">
            <bean:write name="enq220Form" property="enq220ViewAnsDate" />
          </td>
          <!-- 基本情報 結果公開期限 -->
          <th class="w20 txt_c no_w">
            <gsmsg:write key="enq.enq210.11" />
          </th>
          <td class="w30 no_w">
            <% if (ansOpen ==  GSConstEnquete.KOUKAI_ON) {%>
            <bean:write name="enq220Form" property="enq220ViewAnsPubDateFrom" />
            &nbsp;～&nbsp;
            <logic:notEqual name="enq220Form" property="enq210ToKbn" value="1">
              <bean:write name="enq220Form" property="enq220ViewPubDateTo" />
            </logic:notEqual>
            <logic:equal name="enq220Form" property="enq210ToKbn" value="1">
              <gsmsg:write key="main.man200.9" />
            </logic:equal>
            <% } else { %>
            <gsmsg:write key="cmn.private" />
            <% } %>
          </td>
        </tr>
        <% } %>

        <!-- 基本情報 注意事項 -->
        <tr>
          <th class="w20 txt_c no_w">
            <gsmsg:write key="cmn.hints" />
          </th>
          <td class="w80" colspan="3" >
            <span>
              <bean:define id="anony" name="enq220Form" property="enq210Anony" type="java.lang.Integer" />
              <% if (anony == GSConstEnquete.ANONYMUS_ON && ansOpen == GSConstEnquete.KOUKAI_ON) { %>
              <gsmsg:write key="enq.69" />
              <% } else if (anony == GSConstEnquete.ANONYMUS_ON) { %>
              <gsmsg:write key="enq.31" />
              <% } else if (ansOpen == GSConstEnquete.KOUKAI_ON) { %>
              <gsmsg:write key="enq.32" />
              <% } else { %>
              &nbsp;
              <% } %>
            </span>
          </td>
        </tr>
      </table>

      <!-- 設問情報 -->
      <div class="fs_16 fw_b txt_l">
        <gsmsg:write key="enq.04" />
      </div>

      <table class="table-left mt0">
        <!-- 設問情報 設問種類 -->
        <tr>
          <th class="w20 txt_c no_w" colspan="2">
            <gsmsg:write key="enq.64" />
          </th>
          <td class="no_w" colspan="3">
            <span>
              <bean:write name="enq220Form" property="enq220SyuruiName" />
            </span>
          </td>
        </tr>

        <logic:notEqual name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_COMMENT) %>">
          <tr>
            <!-- 設問番号 -->
            <logic:equal name="enq220Form" property="enq210queSeqType" value="1">
              <th class="w20 txt_c no_w" colspan="2">
                <gsmsg:write key="enq.09" />
              </th>
            </logic:equal>
            <logic:notEqual name="enq220Form" property="enq210queSeqType" value="1">
              <th class="w20 txt_c no_w" colspan="2">
                <gsmsg:write key="enq.09" />
                <span class="cl_fontWarn"
                  <gsmsg:write key="cmn.asterisk" />
                </span>
              </th>
            </logic:notEqual>

            <td class="no_w w30">
              <span>
                <logic:equal name="enq220Form" property="enq210queSeqType" value="1">
                  <html:hidden name="enq220Form" property="enq220QueNo" />
                  <bean:define id="queIndex" name="enq220Form" property="enq210queIndex" type="java.lang.Integer" />
                  <bean:write name="enq220Form" property="enq220autoQueNo" />
                </logic:equal>
                <logic:notEqual name="enq220Form" property="enq210queSeqType" value="1">
                  <html:text name="enq220Form" property="enq220QueNo" styleClass="que_no" maxlength="10" />
                </logic:notEqual>
              </span>
            </td>
            <!-- 必須 -->
            <th class="w20 txt_c no_w">
              <gsmsg:write key="cmn.required" />
            </th>
            <td class="w30 no_w">
              <span class="verAlignMid">
                <html:checkbox name="enq220Form" property="enq220Require" styleId="hissu" value="1" />
                <label for="hissu">
                  <gsmsg:write key="cmn.required" />
                </label>
              </span>
            </td>
          </tr>
        </logic:notEqual>

        <logic:notEqual name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_COMMENT) %>">
          <!-- 設問情報 設問 -->
          <tr>
            <th class="w20 txt_c no_w" colspan="2">
              <gsmsg:write key="enq.12" />
              <span class="cl_fontWarn">
                <gsmsg:write key="cmn.asterisk" />
              </span>
            </th>
            <td class="no_w" colspan="3">
              <span>
                <html:text name="enq220Form" property="enq220Question" styleClass="que_question" maxlength="100" />
              </span>
            </td>
          </tr>
        </logic:notEqual>

        <!-- 設問情報 説明文 -->
        <tr>
          <th class="w20 txt_c no_w" colspan="2">
            <gsmsg:write key="cmn.explanation" />
          </th>
          <td class="no_w" colspan="3">
            <input type="hidden" name="attachment_ID_list" value="2">
            <input type="hidden" name="attachmentFileListFlg2" value="false" />
            <input type="hidden" name="attachmentMode2" value="7" />
            <input type="hidden" name="attachmentPluginId2" value="<%= GSConstEnquete.PLUGIN_ID_ENQUETE %>" />
            <input type="hidden" name="attachmentTempDirId2" value="enq210" />
            <input type="hidden" name="attachmentTempDirPlus2" value="<%= GSConstEnquete.ENQ_FILE + "/bodyFile" %>" />

            <input type="hidden" name="enqEditorSize" value="350">
            <table>
              <tr>
                <td class="border_none">
                  <span>
                    <span id="attachmentFileErrorArea2"></span>
                    <div class="setsumon display_blank" id="attachment_FormArea2">
                      <html:textarea name="enq220Form" property="enq220QueDesc" cols="40" rows="5" styleClass="text_base" styleId="enqDescArea" />
                    </div>
                    <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">
                  </span>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <th class="w20 txt_c no_w" colspan="2">
            <gsmsg:write key="cmn.attached" />
          </th>
          <td class="no_w" colspan="3">
            <html:hidden name="enq220Form" property="enq220fileName" />
            <bean:define id="queId" name="enq220Form" property="enq220queId" type="String"/>
            <% String tempDirPlus = String.valueOf(GSConstEnquete.ENQ_QUESTION) + "/" + String.valueOf(GSConstEnquete.ENQ_QUESTION_EDIT); %>
            <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
              pluginId="<%= GSConstEnquete.PLUGIN_ID_ENQUETE %>"
              tempDirId="enq210"
              tempDirPlus="<%= tempDirPlus %>"
              formId="1" />
          </td>
        </tr>

        <tr id="enq220attachPosition">
          <th class="txt_c no_w" colspan="2">
            <gsmsg:write key="cmn.position" />
          </th>
          <td class="no_w" colspan="3">
            <span class="verAlignMid">
              <html:radio name="enq220Form" property="enq220AttachPos" value="0" styleId="iti_0" />
              <label for="iti_0">
                <gsmsg:write key="cmn.up2" />
              </label>
              <html:radio name="enq220Form" property="enq220AttachPos" value="1" styleClass="ml10" styleId="iti_1" />
              <label for="iti_1">
                <gsmsg:write key="cmn.down2" />
              </label>
            </span>
          </td>
        </tr>

        <logic:equal name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_COMMENT) %>">
          <!-- コメント -->
          <!-- 横線位置 -->
          <tr>
            <th class="w20 txt_c no_w" colspan="2">
              <gsmsg:write key="cmn.horizon.line.position" />
            </th>
            <td class="no_w" colspan="3">
              <span class="verAlignMid">
                <html:radio name="enq220Form" property="enq220LinePos" value="<%= String.valueOf(GSConstEnquete.EQM_LINE_KBN_NONE) %>" styleId="linePos_0" />
                <label for="linePos_0">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220LinePos" styleClass="ml10" value="<%= String.valueOf(GSConstEnquete.EQM_LINE_KBN_TOP) %>" styleId="linePos_1" />
                <label for="linePos_1">
                  <gsmsg:write key="cmn.up2" />
                </label>
                <html:radio name="enq220Form" property="enq220LinePos" styleClass="ml10" value="<%= String.valueOf(GSConstEnquete.EQM_LINE_KBN_BOTTOM) %>" styleId="linePos_2" />
                <label for="linePos_2">
                  <gsmsg:write key="cmn.down2" />
                </label>
                <html:radio name="enq220Form" property="enq220LinePos" styleClass="ml10" value="<%= String.valueOf(GSConstEnquete.EQM_LINE_KBN_UPDOWN) %>" styleId="linePos_3" />
                <label for="linePos_3">
                  <gsmsg:write key="cmn.updown" />
                </label>
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_TEXT) %>">
          <!-- テキスト入力（1行・複数行） -->
          <!-- 初期値 -->
          <tr>
            <th class="txt_c" colspan="2">
              <gsmsg:write key="ntp.10" />
            </th>
            <td colspan="3">
              <span class="verAlignMid">
                <html:radio name="enq220Form" property="enq220DefKbn" value="<%= String.valueOf(GSConstEnquete.INIT_OFF) %>" styleId="init_0" onclick="changeInitArea(0);" />
                <label for="init_0">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220DefKbn" value="<%= String.valueOf(GSConstEnquete.INIT_ON) %>" styleClass="ml10" styleId="init_1" onclick="changeInitArea(0);" />
                <label for="init_1">
                  <gsmsg:write key="cmn.exist" />
                </label>
                <span id="enq220TxtInitArea">
                  <logic:equal name="enq220Form" property="enq210queType" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXTAREA) %>">
                    <html:textarea name="enq220Form" property="enq220DefTxt" styleClass="que_textarea_init ml5 mt5" />
                  </logic:equal>
                  <logic:notEqual name="enq220Form" property="enq210queType" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXTAREA) %>">
                  &nbsp;<html:text name="enq220Form" property="enq220DefTxt" styleClass="que_text_init ml5" maxlength="100" />
                  </logic:notEqual>
                </span>
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_INTEGER) %>">
          <!-- 数値入力 -->
          <!-- 初期値 -->
          <tr>
            <th class="txt_c no_w" colspan="2">
              <gsmsg:write key="ntp.10" />
            </th>
            <td class="no_w" colspan="3">
              <span class="verAlignMid">
                <html:radio name="enq220Form" property="enq220DefKbn" value="<%= String.valueOf(GSConstEnquete.INIT_OFF) %>" styleId="init_0" onclick="changeInitArea(1);" />
                <label for="init_0">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220DefKbn" value="<%= String.valueOf(GSConstEnquete.INIT_ON) %>" styleClass="ml10" styleId="init_1" onclick="changeInitArea(1);" />
                <label for="init_1">
                  <gsmsg:write key="cmn.exist" />
                </label>
                <span id="enq220IntInitArea">
                  <html:text name="enq220Form" property="enq220DefNum" styleClass="que_text_int ml5 wp140" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_NUM) %>" />
                </span>
              </span>
            </td>
          </tr>
          <!-- 入力範囲 -->
          <tr>
            <th class="txt_c no_w" colspan="2">
              <gsmsg:write key="cmn.input.range" />
            </th>
            <td class="no_w" colspan="3">
              <span class="verAlignMid">
                <html:radio name="enq220Form" property="enq220RngKbn" value="<%= String.valueOf(GSConstEnquete.RNG_OFF) %>" styleId="rng_0" onclick="changeRangeArea(0);" />
                <label for="rng_0">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220RngKbn" value="<%= String.valueOf(GSConstEnquete.RNG_ON) %>" styleClass="ml10" styleId="rng_1" onclick="changeRangeArea(0);" />
                <label for="rng_1">
                  <gsmsg:write key="cmn.exist" />
                </label>
                <span id="enq220IntRangeArea">
                  <html:text name="enq220Form" property="enq220RngStrNum" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_NUM) %>" styleClass="que_text_int ml5 wp140" />
                  ～
                  <html:text name="enq220Form" property="enq220RngEndNum" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_NUM) %>" styleClass="que_text_int wp140" />
                </span>
              </span>
            </td>
          </tr>
          <!-- 単位 -->
          <tr>
            <th class="txt_c no_w" colspan="2">
              <gsmsg:write key="ntp.102" />
              <span class="cl_fontWarn">
                <gsmsg:write key="cmn.asterisk" />
              </span>
            </th>
            <td class="no_w" colspan="3">
              <span>
                <html:text name="enq220Form" property="enq220UnitNum" maxlength="10" styleClass="que_text_int" />
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_DAY) %>">
          <!-- 日付入力 -->
          <!-- 初期値 -->
          <tr>
            <th class="txt_c no_w" colspan="2">
              <gsmsg:write key="ntp.10" />
            </th>
            <td class="no_w" colspan="3">
              <span class="verAlignMid">
                <html:radio name="enq220Form" property="enq220DefKbn" value="<%= String.valueOf(GSConstEnquete.INIT_OFF) %>" styleId="init_0" onclick="changeInitArea(2);" />
                <label for="init_0">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220DefKbn" value="<%= String.valueOf(GSConstEnquete.INIT_ON) %>" styleClass="ml10" styleId="init_1" onclick="changeInitArea(2);" />
                <label for="init_1">
                  <gsmsg:write key="cmn.exist" />
                </label>
                <span id="enq220DateInitArea" class="verAlignMid">
                  <html:text name="enq220Form" property="enq220DefDate" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
                  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                </span>
              </span>
            </td>
          </tr>
          <!-- 入力範囲 -->
          <tr>
            <th class="txt_c no_w" colspan="2">
              <gsmsg:write key="cmn.input.range" />
            </th>
            <td class="no_w" colspan="3">
              <span class="verAlignMid">
                <html:radio name="enq220Form" property="enq220RngKbn" value="<%= String.valueOf(GSConstEnquete.RNG_OFF) %>" styleId="rng_0" onclick="changeRangeArea(1);" />
                <label for="rng_0">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220RngKbn" value="<%= String.valueOf(GSConstEnquete.RNG_ON) %>" styleClass="ml10" styleId="rng_1" onclick="changeRangeArea(1);" />
                <label for="rng_1">
                  <gsmsg:write key="cmn.exist" />
                </label>
                <span id="enq220DateRangeArea" class="verAlignMid">
                  <html:text name="enq220Form" property="enq220RngStrDate" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
                  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  <span class="ml5 mr5">～</span>
                  <html:text name="enq220Form" property="enq220RngEndDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                </span>
              </span>
            </td>
          </tr>
        </logic:equal>

      </table>


      <logic:equal name="enq220Form" property="enq220DspMode" value="<%= String.valueOf(GSConstEnquete.DSP_MODE_CHOICE) %>">
        <table class="w100">
          <tr>
            <td class="txt_l">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onclick="enq220UpRow(enq220selectRow);">
                <gsmsg:write key="cmn.up" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="enq220DownRow(enq220selectRow);">
                <gsmsg:write key="cmn.down" />
              </button>
            </td>
          </tr>
        </table>
        <!-- 選択項目 -->
        <table class="table-top mt0" id="enq220question">
          <tr>
            <th></th>
            <th>
              <gsmsg:write key="cmn.display.name" />
              </span>
              <span class="cl_fontWarn">
                <gsmsg:write key="cmn.asterisk" />
            </th>
            <th class="w5 no_w">
              <gsmsg:write key="ntp.10" />
            </th>
            <th class="w5"></th>
          </tr>
            <logic:notEmpty name="enq220Form" property="subList">
              <% int subFormCount = 0; %>
              <% String[] subParamName = {"lineNo", "enqDspSec", "enqDspName", "enqDefFlg", "eqsRadioOff_", "eqsRadioOn_", "enqIndex" }; %>
              <logic:iterate id="subData" name="enq220Form" property="subList" indexId="lineIdx">
                <% String lineNo = String.valueOf(lineIdx.intValue()); %>
                <% String lineFrmName = "subList[" + lineNo + "]."; %>
                <% String trNo = "choRow_" + lineNo ; %>
                <% String paramName = lineFrmName + subParamName[0]; %>
                <% String radioNo = "radioNo_" + lineNo; %>
                <tr id="<%= trNo %>">
                  <input type="hidden" name="rowIdx" value="<%= lineNo %>">
                  <input type="hidden" name="<%= lineFrmName + subParamName[6] %>" value="<%= lineNo %>">
                  <html:hidden property="<%= lineFrmName + subParamName[1] %>" styleClass="enqDspSec" />
                  <td class="txt_c txt_m no_w js_tableTopCheck cursor_p">
                    <html:radio name="enq220Form" property="enq220selectRow" styleId="<%= radioNo %>" value="<%= lineNo %>" />
                  </td>
                  <td class="txt_l txt_m no_w">
                    <html:text property="<%= lineFrmName + subParamName[2] %>" styleClass="que_dsp_name" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EQS_DSP_NAME) %>" />
                  </td>
                  <td class="txt_l txt_m no_w">
                    <div class="verAlignMid">
                    <% String enq220checkScript = "enq220ChechCheckBox(" + lineNo + ")"; %>
                    <html:checkbox property="<%= lineFrmName + subParamName[3] %>" styleClass="mr1" styleId="<%= subParamName[5] + lineNo %>" value="<%= String.valueOf(GSConstEnquete.CHOICE_INIT_ON) %>" onclick="<%= enq220checkScript %>" />
                    <label for="<%= subParamName[5] + lineNo %>" />
                    <gsmsg:write key="cmn.select" />
                    </label>
                    </div>
                  </td>
                  <td class="txt_c txt_m no_w">
                    <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.delete" />" name="btn_delete" onClick="enq220delRow(<%= lineNo %>);">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                      <gsmsg:write key="cmn.delete" />
                    </button>
                  </td>

                  </td>
                </tr>
                <% subFormCount++; %>
              </logic:iterate>
              <input type="hidden" name="enq230subFormListCount" value="<%= String.valueOf(subFormCount) %>">
            </logic:notEmpty>

          <tfoot>
            <tr>
              <td class="txt_c txt_m no_w"></td>
              <td class="txt_c txt_m no_w"></td>
              <td class="txt_l txt_m no_w"></td>
              <td class="txt_c txt_m">

                <button type="button" name="btn_add" class="baseBtn no_w" value="<gsmsg:write key="cmn.add" />" onClick="addRow();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.add" />">
                  <gsmsg:write key="cmn.add" />


                </button>
              </td>
            </tr>
          </tfoot>

        </table>

        <table class="table-left">
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="enq.40" />
            </th>
            <td class="no_w">
              <span class="verAlignMid w100">
                <html:radio name="enq220Form" property="enq220Other" value="<%= String.valueOf(GSConstEnquete.OTHER_OFF) %>" styleId="other_01" />
                <label for="other_01">
                  <gsmsg:write key="cmn.no3" />
                </label>
                <html:radio name="enq220Form" property="enq220Other" styleClass="ml10" value="<%= String.valueOf(GSConstEnquete.OTHER_TEXT) %>" styleId="other_02" />
                <label for="other_02">
                  <gsmsg:write key="cmn.exist2" />
                </label>
                <html:radio name="enq220Form" property="enq220Other" styleClass="ml10" value="<%= String.valueOf(GSConstEnquete.OTHER_TEXTAREA) %>" styleId="other_03" />
                <label for="other_03">
                  <gsmsg:write key="cmn.exist3" />
                </label>
              </span>
              <div>
                <gsmsg:write key="cmn.asterisk" />
                <gsmsg:write key="enq.enq220.05" />
              </div>
            </td>
          </tr>
        </table>
      </logic:equal>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="enq220Entry();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq220back');">
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