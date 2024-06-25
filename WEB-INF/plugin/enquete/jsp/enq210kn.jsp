<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>
<%@ page import="jp.groupsession.v2.enq.enq210.Enq210Form" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enqDelCheck.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../enquete/js/enq210kn.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>


<% boolean editFlg = false; %>
<logic:equal name="enq210knForm" property="enqEditMode" value="<%= String.valueOf(GSConstEnquete.EDITMODE_EDIT) %>">
<% editFlg = true; %>
</logic:equal>
<% boolean backPage970 = false; %>
<logic:equal name="enq210knForm" property="enq970BackPage" value="1">
<% backPage970 = true; %>
</logic:equal>

<% if (backPage970) { %>
<title>GROUPSESSION <gsmsg:write key="enq.enq970.01" /></title>
<% } else { %>
<%   if (editFlg) { %>
<title>GROUPSESSION <gsmsg:write key="enq.plugin" /> <gsmsg:write key="enq.enq210kn.02" /></title>
<%   } else { %>
<title>GROUPSESSION <gsmsg:write key="enq.plugin" /> <gsmsg:write key="enq.enq210kn.01" /></title>
<%   } %>
<% } %>

</head>
<body>
<html:form styleId="enqForm" action="/enquete/enq210kn">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="cmd" value="">
<input type="hidden" name="SEQ" value="">

<jsp:include page="/WEB-INF/plugin/enquete/jsp/enq210_hiddenParams.jsp" />
<html:hidden property="enq210editMode" />
<html:hidden property="enq210DescText" />
<html:hidden property="tempClickBtn" />
<html:hidden property="answerDataReset" />
<html:hidden property="backScreen" />
<input type="hidden" name="enq210DelAnsFlg" value="false">

<logic:notEmpty name="enq210knForm" property="enq010priority">
<logic:iterate id="svPriority" name="enq210knForm" property="enq010priority">
  <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq010status">
<logic:iterate id="svStatus" name="enq210knForm" property="enq010status">
  <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq010svPriority">
<logic:iterate id="svPriority" name="enq210knForm" property="enq010svPriority">
  <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq010svStatus">
<logic:iterate id="svStatus" name="enq210knForm" property="enq010svStatus">
  <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq010statusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq210knForm" property="enq010statusAnsOver">
  <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq010svStatusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq210knForm" property="enq010svStatusAnsOver">
  <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="enq210knForm" property="enq210answerGroup">
<logic:iterate id="answerUser" name="enq210knForm" property="enq210answerList">
  <input type="hidden" name="enq210answerList" value="<bean:write name="answerUser" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="enq210knForm" property="enq230selectEnqSid">
  <logic:iterate id="sv230SelectEnqSid" name="enq210knForm" property="enq230selectEnqSid">
    <input type="hidden" name="enq230selectEnqSid" value="<bean:write name='sv230SelectEnqSid' />" >
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq230priority">
  <logic:iterate id="sv230Priority" name="enq210knForm" property="enq230priority">
    <input type="hidden" name="enq230priority" value='<bean:write name="sv230Priority" />'>
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq230svPriority">
<logic:iterate id="svPriority" name="enq210knForm" property="enq230svPriority">
  <input type="hidden" name="enq230svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq230svStatus">
<logic:iterate id="svStatus" name="enq210knForm" property="enq230svStatus">
  <input type="hidden" name="enq230svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<jsp:include page="/WEB-INF/plugin/enquete/jsp/enq970_hiddenParams.jsp" />

<logic:notEmpty name="enq210knForm" property="enq970selectEnqSid">
  <logic:iterate id="svEnq970selectEnqSid" name="enq210knForm" property="enq970selectEnqSid">
    <input type="hidden" name="enq970selectEnqSid" value='<bean:write name="svEnq970selectEnqSid" />'>
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq970priority">
  <logic:iterate id="svEnq970priority" name="enq210knForm" property="enq970priority">
    <input type="hidden" name="enq970priority" value='<bean:write name="svEnq970priority" />'>
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq210knForm" property="enq970status">
  <logic:iterate id="svEnq970status" name="enq210knForm" property="enq970status">
    <input type="hidden" name="enq970status" value='<bean:write name="svEnq970status" />'>
  </logic:iterate>
</logic:notEmpty>

<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
<bean:define id="enq210editMode" name="enq210knForm" property="enq210editMode" type="java.lang.Integer" />
<% int editMode = enq210editMode.intValue(); %>
<% if (editMode == Enq210Form.EDITMODE_NORMAL) { %>
  <% if (!editFlg) { %>
    <input type="hidden" name="helpPrm" value="0">
  <% } else { %>
    <input type="hidden" name="helpPrm" value="1">
  <% } %>
<% } else if (editMode == Enq210Form.EDITMODE_TEMPLATE) { %>
  <% if (!editFlg) { %>
    <input type="hidden" name="helpPrm" value="2">
  <% } else { %>
    <input type="hidden" name="helpPrm" value="3">
  <% } %>
<% } else { %>
  <input type="hidden" name="helpPrm" value="4">
<% } %>


<bean:define id="enq210editMode" name="enq210knForm" property="enq210editMode" type="java.lang.Integer" />
  <% boolean enqTemplateFlg = enq210editMode.intValue() == jp.groupsession.v2.enq.enq210.Enq210Form.EDITMODE_TEMPLATE; %>
  <% if (!backPage970) { %>
  <div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
      <img class="header_pluginImg" src="../enquete/images/original/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
    </li>
    <li><gsmsg:write key="enq.plugin" /></li>
    <% if (editFlg) { %>
      <% if (enqTemplateFlg) { %>
      <li class="pageTitle_subFont"> <gsmsg:write key="enq.enq210kn.04" /> </li>
      <% } else { %>
      <li class="pageTitle_subFont"> <gsmsg:write key="enq.enq210kn.02" /> </li>
      <% } %>
      <% } else { %>
      <% if (enqTemplateFlg) { %>
      <li class="pageTitle_subFont"> <gsmsg:write key="enq.enq210kn.03" /> </li>
      <% } else { %>
      <li class="pageTitle_subFont"> <gsmsg:write key="enq.enq210kn.01" /> </li>
      <% } %>

    <% } %>
  <% } else { %>
  <div class="kanriPageTitle w80 mrl_auto">
     <ul>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.admin.setting" /></li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="enq.enq970.01" />
      </li>
    <% } %>
      <li>
        <div>
        <logic:notEqual name="enq210knForm" property="enq970BackPage" value="1">
<%--    <input type="button" value="<gsmsg:write key="man.final" />" class="btn_base1" onclick="buttonPush('decision');"> --%>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" id ="kakuteibtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq210knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
        </div>
      </li>
    </ul>
  </div>

<div class="wrapper w80 mrl_auto">

<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

  <table class="table-left">
    <!-- 基本情報 重要度 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="project.prj050.4" />
      </th>
      <td class="w75" colspan="3">

        <div class="wp400 display_inline verAlignMid">
          <div class="mr10">
            <logic:equal name="enq210knForm" property="enq210Juuyou" value="0">
              <gsmsg:write key="project.58" />
            </logic:equal>
            <logic:equal name="enq210knForm" property="enq210Juuyou" value="1">
              <gsmsg:write key="project.59" />
            </logic:equal>
            <logic:equal name="enq210knForm" property="enq210Juuyou" value="2">
              <gsmsg:write key="project.60" />
            </logic:equal>
          </div>

            <logic:equal name="enq210knForm" property="enq210Juuyou" value="0">
              <div id="star_1" >
                 <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                <i class="btn_originalImg-display icon-star importance-blue"></i>
                <i class="btn_originalImg-display icon-star_line  importance-gray"></i>
                <i class="btn_originalImg-display icon-star_line  importance-gray"></i>
              </div>
            </logic:equal>
            <logic:equal name="enq210knForm" property="enq210Juuyou" value="1">
              <div id="star_2">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.59" />">
                <i class="btn_originalImg-display icon-star importance-yellow"></i>
                <i class="btn_originalImg-display icon-star importance-yellow"></i>
                <i class="btn_originalImg-display icon-star_line  importance-gray"></i>
              </div>
            </logic:equal>
            <logic:equal name="enq210knForm" property="enq210Juuyou" value="2">
              <div id="star_3">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                <i class="btn_originalImg-display icon-star importance-red"></i>
                <i class="btn_originalImg-display icon-star importance-red"></i>
                <i class="btn_originalImg-display icon-star importance-red"></i>
              </div>
            </logic:equal>
        </div>

      </td>
    </tr>
    <tr>
    <!-- 基本情報 種類 -->
    <% String strColVal = "1"; %>
    <% String strWidth = "w25"; %>
    <% if (enqTemplateFlg) { strColVal = "3"; strWidth="w90"; } %>
      <th>
        <gsmsg:write key="cmn.type2" />
      </th>
      <td colspan="<%= strColVal %>" class="<%= strWidth %>">
        <bean:write name="enq210knForm" property="enq210knViewSyurui" />
      </td>
    <!-- 基本情報 発信者 -->
    <% if (!enqTemplateFlg) { %>
      <th class="w25">
        <gsmsg:write key="cir.2" />
      </th>
      <td class="w25">
        <bean:define id="sdFlg" name="enq210knForm" property="enq210knSenderDelFlg" type="java.lang.Boolean" />
        <bean:define id="ukoFlg" name="enq210knForm" property="enq210knSenderUkoFlg" type="java.lang.Integer" />
          <span class="<% if (sdFlg) { %> text_deluser_enq<% } else if (ukoFlg == 1) { %> mukoUser<% }%>">
        <bean:write name="enq210knForm" property="enq210knSenderName" />
      </td>
    <% } %>
    </tr>
    <!-- 基本情報 タイトル -->
    <tr>
      <th>
        <gsmsg:write key="cmn.title" />
      </th>
      <td colspan="3">
        <bean:write name="enq210knForm" property="enq210Title" />
      </td>
    </tr>
      <!-- 基本情報 説明文 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.explanation" />
      </th>
      <td colspan="3">
        <htmlframe:write attrClass="w100">
          <bean:write name="enq210knForm" property="enq210Desc" filter="false" />
        </htmlframe:write>
      </td>
     </tr>
     <bean:define id="attachKbn" name="enq210knForm" property="enq210AttachKbn" type="java.lang.Integer" />
     <tr>
       <th><gsmsg:write key="cmn.attached" /></th>
       <td colspan="3">
         <% if (attachKbn.intValue() == 1 || attachKbn.intValue() == 2) { %><bean:write name="enq210knForm" property="enq210fileName" />
         <% } %>&nbsp;
       </td>
     </tr>
     <% if (attachKbn.intValue() == 1 || attachKbn.intValue() == 2) { %>
     <tr>
       <th class="bor_l1">
         <gsmsg:write key="cmn.attached" /><gsmsg:write key="cmn.position" />
       </th>
       <td colspan="3">
         <logic:equal name="enq210knForm" property="enq210AttachPos" value="0"><gsmsg:write key="cmn.up2" /></logic:equal>
         <logic:equal name="enq210knForm" property="enq210AttachPos" value="1"><gsmsg:write key="cmn.down2" /></logic:equal>
       </td>
     </tr>
     <% } %>
     <!-- 基本情報 公開開始日 -->
     <% if (!enqTemplateFlg) { %>
     <tr>
       <th><gsmsg:write key="enq.53" /></th>
       <td colspan="3">
         <bean:define id="frYear" name="enq210knForm" property="enq210FrYear" type="java.lang.Integer" />
         <bean:define id="frMonth" name="enq210knForm" property="enq210FrMonth" type="java.lang.Integer" />
         <bean:define id="frDay" name="enq210knForm" property="enq210FrDay" type="java.lang.Integer" />
         <gsmsg:write key="cmn.date4" arg0="<%= String.valueOf(frYear) %>" arg1="<%= String.valueOf(frMonth) %>" arg2="<%= String.valueOf(frDay) %>"/>
       </td>
     </tr>
     <!-- 基本情報 回答期限 -->
     <tr>
       <th><gsmsg:write key="enq.19" /></th>
       <td colspan="3">
         <bean:define id="ansYear" name="enq210knForm" property="enq210AnsYear" type="java.lang.Integer" />
         <bean:define id="ansMonth" name="enq210knForm" property="enq210AnsMonth" type="java.lang.Integer" />
         <bean:define id="ansDay" name="enq210knForm" property="enq210AnsDay" type="java.lang.Integer" />
         <gsmsg:write key="cmn.date4" arg0="<%= String.valueOf(ansYear) %>" arg1="<%= String.valueOf(ansMonth) %>" arg2="<%= String.valueOf(ansDay) %>"/>
        </td>
      </tr>
      <% } %>

      <!-- 基本情報 結果公開 -->
      <tr>
        <th><gsmsg:write key="enq.07" /></th>
        <td colspan="3">
          <logic:equal name="enq210knForm" property="enq210AnsOpen" value="1">
          <gsmsg:write key="cmn.publish" />
          <% if (!enqTemplateFlg) { %>
          <br>
          <gsmsg:write key="cmn.start" /><gsmsg:write key="cmn.colon" />
          <bean:define id="ansPubFrYear" name="enq210knForm" property="enq210AnsPubFrYear" type="java.lang.Integer" />
          <bean:define id="ansPubFrMonth" name="enq210knForm" property="enq210AnsPubFrMonth" type="java.lang.Integer" />
          <bean:define id="ansPubFrDay" name="enq210knForm" property="enq210AnsPubFrDay" type="java.lang.Integer" />
          <gsmsg:write key="cmn.date4" arg0="<%= String.valueOf(ansPubFrYear) %>" arg1="<%= String.valueOf(ansPubFrMonth) %>" arg2="<%= String.valueOf(ansPubFrDay) %>"/>
          <br>
          <gsmsg:write key="main.src.man250.30" /><gsmsg:write key="cmn.colon" />
          <logic:equal name="enq210knForm" property="enq210ToKbn" value="1">
            <gsmsg:write key="main.man200.9" />
          </logic:equal>
          <logic:notEqual name="enq210knForm" property="enq210ToKbn" value="1">
            <bean:define id="toYear" name="enq210knForm" property="enq210ToYear" type="java.lang.Integer" />
            <bean:define id="toMonth" name="enq210knForm" property="enq210ToMonth" type="java.lang.Integer" />
            <bean:define id="toDay" name="enq210knForm" property="enq210ToDay" type="java.lang.Integer" />
            <gsmsg:write key="cmn.date4" arg0="<%= String.valueOf(toYear) %>" arg1="<%= String.valueOf(toMonth) %>" arg2="<%= String.valueOf(toDay) %>"/>
          </logic:notEqual>
          <% } %>
          </logic:equal>
          <logic:notEqual name="enq210knForm" property="enq210AnsOpen" value="1">
            <gsmsg:write key="cmn.not.publish" />
          </logic:notEqual>
        </td>
      </tr>

      <% if (!enqTemplateFlg) { %>
      <logic:notEqual name="enq210knForm" property="enq970BackPage" value="1">
      <logic:equal name="enq210knForm" property="enq210pluginSmailUse" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE) %>">
      <!-- ショートメール通知 -->
      <tr>
        <th><gsmsg:write key="shortmail.notification" /></th>
        <td colspan="3">
          <logic:equal name="enq210knForm" property="enq210smailInfo" value="<%= String.valueOf(Enq210Form.SML_INFO_SEND) %>">
            <gsmsg:write key="cmn.notify" />
          </logic:equal>
        </td>
      </tr>
      </logic:equal>
      </logic:notEqual>
      <% } %>

      <!-- 基本情報 匿名 -->
      <tr>
        <th>
          <gsmsg:write key="cmn.anonymity" />
        </th>
        <td colspan="3">
          <logic:equal name="enq210knForm" property="enq210Anony" value="1">
            <gsmsg:write key="enq.06" />
          </logic:equal>
        </td>
      </tr>
      <!-- 基本情報 対象者 -->
      <tr>
        <th><gsmsg:write key="enq.10" /></th>
        <td colspan="3">
          <logic:notEmpty name="enq210knForm" property="selectAnswerCombo">
          <logic:iterate id="answerUser" name="enq210knForm" property="selectAnswerCombo" type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
            <span class="<%if (answerUser.getUsrUkoFlg() == 1) {%>mukoUser<% } %>"><bean:write name="answerUser" property="label" /></span><br>
          </logic:iterate>
          </logic:notEmpty>
          <logic:empty name="enq210knForm" property="selectAnswerCombo">&nbsp;</logic:empty>

          <logic:notEmpty name="enq210knForm" property="enq210knDelAnswerList">
          <logic:iterate id="delAnswer" name="enq210knForm" property="enq210knDelAnswerList">
            <br><span class="delete_border"><bean:write name="delAnswer" /></span>
          </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </table>

    <!-- 設問情報 タイトル -->
    <div class="w100 mt20 txt_l pageTitle_sub">
      <span class="fs_16 txt_l fw_b">
        <gsmsg:write key="enq.04" />
      </span>
      <span>
    <% if (!backPage970) { %>
      <span class="fw_b ml40"><gsmsg:write key="enq.09" /></span>
        <logic:equal name="enq210knForm" property="enq210queSeqType" value="1" ><gsmsg:write key="cmn.auto" /></logic:equal>
        <logic:notEqual name="enq210knForm" property="enq210queSeqType" value="1"><gsmsg:write key="cmn.manual" /></logic:notEqual>
    <% } else { %>&nbsp;<% } %>
      </span>


<!-- 設問情報 -->
  <table class="table-top mt0 mt5 ">
    <tr>
      <th class="w20">
        <gsmsg:write key="enq.09" />
      </th>
      <th>
        <gsmsg:write key="enq.12" />
      </th>
    </tr>
    <tbody id="enqTbl">
    <logic:notEmpty name="enq210knForm" property="ebaList">
    <logic:iterate id="ebaData" name="enq210knForm" property="ebaList" indexId="lineIdx">
    <tr class="bgC_tableCell">
      <td class="txt_l">
        <logic:equal name="enq210knForm" property="enq210queSeqType" value="1">
          <logic:greaterThan name="ebaData" property="enq210AutoQueNo" value="0">
            <bean:write name="ebaData" property="enq210AutoQueNo" />
          </logic:greaterThan>
        </logic:equal>
        <logic:notEqual name="enq210knForm" property="enq210queSeqType" value="1">
          <bean:write name="ebaData" property="enq210QueNo" />
        </logic:notEqual>
        <logic:equal name="ebaData" property="enq210Require" value="1">
          <br><gsmsg:write key="cmn.required" />
        </logic:equal>
      </td>
      <td class="txt_l">
        <gsmsg:write key="cmn.type2" /><gsmsg:write key="cmn.colon" /><bean:write name="ebaData" property="enq210SyuruiName" /><br>
        <logic:notEmpty name="ebaData" property="enq210Question">
          <gsmsg:write key="enq.12" /><gsmsg:write key="cmn.colon" /><bean:write name="ebaData" property="enq210Question" /><br>
        </logic:notEmpty>
        <logic:notEmpty name="ebaData" property="enq210QueDesc">
          <div><gsmsg:write key="ptl.8" /><gsmsg:write key="cmn.colon" />
            <htmlframe:write attrClass="w100">
              <bean:write name="ebaData" property="enq210QueDesc" filter="false" />
            </htmlframe:write>
          </div>
        </logic:notEmpty>
        <logic:equal name="ebaData" property="enq210QueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_SINGLE) %>" >
          <gsmsg:write key="enq.enq210.12"/><gsmsg:write key="cmn.colon" />
          <logic:notEmpty name="ebaData" property="queSubList">
            <logic:iterate id="queChoice" name="ebaData" property="queSubList">
              [<bean:write name="queChoice" property="enqDspName" />]
            </logic:iterate>
          </logic:notEmpty>
          <br>
        </logic:equal>

        <logic:equal name="ebaData" property="enq210QueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_MULTIPLE) %>" >
          <gsmsg:write key="enq.enq210.12"/><gsmsg:write key="cmn.colon" />
            <logic:notEmpty name="ebaData" property="queSubList">
              <logic:iterate id="queChoice" name="ebaData" property="queSubList">
                [<bean:write name="queChoice" property="enqDspName" />]
              </logic:iterate>
            </logic:notEmpty>
            <br>
        </logic:equal>

        <logic:notEmpty name="ebaData" property="enq210AttachName">
          <gsmsg:write key="cmn.attached" /><gsmsg:write key="cmn.colon" /><bean:write name="ebaData" property="enq210AttachName" /><br>
        </logic:notEmpty>
        <logic:notEmpty name="ebaData" property="enq210initTxt">
          <gsmsg:write key="ntp.10" /><gsmsg:write key="cmn.colon" />
          <logic:equal name="ebaData" property="enq210QueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXTAREA) %>">
            <br><bean:write name="ebaData" property="enq210viewInitTxt" filter="false"/>
          </logic:equal>
          <logic:notEqual name="ebaData" property="enq210QueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXTAREA) %>">
            <bean:write name="ebaData" property="enq210initTxt" />
          </logic:notEqual>
          <br>
        </logic:notEmpty>

        <logic:notEmpty name="ebaData" property="enq210rangeTxtFr">
          <logic:notEmpty name="ebaData" property="enq210rangeTxtTo">
            <gsmsg:write key="cmn.input.range" /><gsmsg:write key="cmn.colon" />
            <bean:write name="ebaData" property="enq210rangeTxtFr" /><gsmsg:write key="tcd.153" />
            <bean:write name="ebaData" property="enq210rangeTxtTo" /><br>
          </logic:notEmpty>
        </logic:notEmpty>
        <logic:notEmpty name="ebaData" property="enq210unitNum">
          <gsmsg:write key="ntp.102" />：<bean:write name="ebaData" property="enq210unitNum" /><br>
        </logic:notEmpty>
        <logic:equal name="ebaData" property="enq210QueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_DAY) %>" >
          <logic:notEmpty name="ebaData" property="enq210initDspDate">
            <gsmsg:write key="ntp.10" /><gsmsg:write key="cmn.colon" /><bean:write name="ebaData" property="enq210initDspDate" /><br>
          </logic:notEmpty>
          <logic:notEmpty name="ebaData" property="enq210rangeTxtFrDsp">
            <logic:notEmpty name="ebaData" property="enq210rangeTxtToDsp">
              <gsmsg:write key="cmn.input.range" /><gsmsg:write key="cmn.colon" />
              <bean:write name="ebaData" property="enq210rangeTxtFrDsp" /><gsmsg:write key="tcd.153" />
              <bean:write name="ebaData" property="enq210rangeTxtToDsp" /><br>
            </logic:notEmpty>
          </logic:notEmpty>
        </logic:equal>
      </td>
    </tr>
    </logic:iterate>
    </logic:notEmpty>
    </tbody>
  </table>


  <div class="footerBtn_block">
    <logic:notEqual name="enq210knForm" property="enq970BackPage" value="1">
<%--    <input type="button" value="<gsmsg:write key="man.final" />" class="btn_base1" onclick="buttonPush('decision');"> --%>
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" id ="kakuteibtn">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <gsmsg:write key="cmn.final" />
     </button>
     </logic:notEqual>

     <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq210knback');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
     <gsmsg:write key="cmn.back" />
  </div>
</div>

    <!-- 回答済みかつ設問情報が変更されていない場合ダイアログ -->
    <div id="enqAnswerReset" title="" class="display_n">
      <div class="verAlignMid hp75 txt_m">
        <div>
          <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
          <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
        </div>
        <div class="pl10">
          <gsmsg:write key="enq.enq210kn.05" />
        </div>
      </div>
    </div>

    <!-- 回答済みかつ設問情報が変更されていた場合ダイアログ -->
    <div id="enqAnswerDelete" title="" class="display_n">
      <div class="verAlignMid hp75 txt_m">
        <div>
         <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
         <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
        </div>
        <div class="pl10">
        <gsmsg:write key="enq.enq210kn.06" />
        </div>
      </div>
    </div>

    <!-- 処理中 -->
    <div id="loading_pop" title="" class="display_n">
      <table class="w100 h100">
        <tr>
          <td class="loading_area w100 txt_c txt_m">
            <img class="classic-display" src="../common/images/classic/icon_loader.gif" />
            <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
          </td>
        </tr>
      </table>
    </div>


</html:form>

<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />

</body>
</html:html>