<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@ page import="jp.groupsession.v2.enq.enq110.Enq110Const"%>
<%@ page import="jp.groupsession.v2.enq.enq210.Enq210Form"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/popup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enqDelCheck.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../enquete/js/enq110.js?<%=GSConst.VERSION_PARAM%>"></script>

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

<title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
</head>
<body class="body_03" onload="initImageView('enqImgName');">
  <html:form styleId="enqForm" action="/enquete/enq110">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="enq110DownloadFlg" value="">
    <input type="hidden" name="enq110BinSid" value="">
    <input type="hidden" name="enq110PreTempDirKbn" value="">
    <input type="hidden" name="enq110TempDir" value="">

    <html:hidden property="cmd" />
    <html:hidden property="ansEnqSid" />
    <html:hidden property="enq210editMode" />
    <html:hidden property="enq110DspMode" />
    <html:hidden property="enq110queDate" />
    <html:hidden property="enq210DescText" />
    <html:hidden property="enq110BackTo" />
    <input type="hidden" name="enq210DelAnsFlg" value="false">

    <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq210_hiddenParams.jsp" />
    <logic:notEmpty name="enq110Form" property="enq230selectEnqSid">
      <logic:iterate id="sv230SelectEnqSid" name="enq110Form" property="enq230selectEnqSid">
        <input type="hidden" name="enq230selectEnqSid" value="<bean:write name='sv230SelectEnqSid' />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110Form" property="enq230priority">
      <logic:iterate id="sv230Priority" name="enq110Form" property="enq230priority">
        <input type="hidden" name="enq230priority" value="<bean:write name='sv230Priority' />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq110Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq110Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq110Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq110Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq110Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq110Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq110Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq110Form" property="enq210answerList">
      <logic:iterate id="answerUser" name="enq110Form" property="enq210answerList">
        <input type="hidden" name="enq210answerList" value="<bean:write name="answerUser" />">
      </logic:iterate>
    </logic:notEmpty>


    <html:hidden property="tempClickBtn" />
    <html:hidden property="answerDataReset" />
    <input type="hidden" name="yearRangeMinFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />
    <input type="hidden" name="yearRangeMaxFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>" />

    <!-- HEADER -->
    <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
    <bean:define id="enq210editMode" name="enq110Form" property="enq210editMode" type="java.lang.Integer" />
    <%
      int editMode = enq210editMode;
    %>
    <%
      if (editMode == Enq210Form.EDITMODE_TEMPLATE) {
    %>
    <input type="hidden" name="helpPrm" value="<%=String.valueOf(Enq210Form.EDITMODE_TEMPLATE)%>">
    <%
      } else {
    %>
    <input type="hidden" name="helpPrm" value='<bean:write name="enq110Form" property="enq110DspMode" />'>
    <%
      }
    %>

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
          <logic:equal name="enq110Form" property="enq110DspMode" value="0">
            <gsmsg:write key="enq.enq110.01" />
          </logic:equal>
          <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
            <gsmsg:write key="cmn.preview" />
          </logic:notEqual>
        </li>
        <li>
          <logic:equal name="enq110Form" property="enq110DspMode" value="<%=String.valueOf(Enq110Const.DSP_MODE_ANSWER)%>">
            <button type="button" value="<gsmsg:write key='cmn.ok' />" class="baseBtn" onclick="setParams();buttonPush('enq110answer');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key='cmn.ok' />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key='cmn.ok' />">
              <gsmsg:write key='cmn.ok' />
            </button>
          </logic:equal>
          <logic:equal name="enq110Form" property="enq110DspMode" value="<%=String.valueOf(Enq110Const.DSP_MODE_PREVIEW)%>">
            <logic:equal name="enq110Form" property="enq210editMode" value="<%=String.valueOf(Enq210Form.EDITMODE_TEMPLATE)%>">
              <button type="button" value="<gsmsg:write key='cmn.final' />" class="baseBtn" onclick="setParams();buttonPush('enq110commit');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <gsmsg:write key='cmn.final' />
              </button>
            </logic:equal>
            <logic:notEqual name="enq110Form" property="enq210editMode" value="<%=String.valueOf(Enq210Form.EDITMODE_TEMPLATE)%>">
              <button type="button" value="<gsmsg:write key='enq.05' />" id="kakuteibtn" class="baseBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key='enq.05' />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key='enq.05' />">
                <gsmsg:write key='enq.05' />
              </button>
            </logic:notEqual>
          </logic:equal>
          <button type="button" value="<gsmsg:write key='cmn.back2' />" class="baseBtn" onclick="buttonPush('enq110back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <div class="txt_l">
        <img class="classic-display p5" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key='enq.plugin' />">
        <img class="original-display p5" src="../enquete/images/original/menu_icon_single.png" alt="<gsmsg:write key='enq.plugin' />">
        <span class="fs_20 fw_b txt_m">
          <bean:write name="enq110Form" property="enq110Title" />
        </span>
      </div>

      <div class="content_area">

        <!-- 基本情報 -->
        <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq110_kihoninfo.jsp" />

        <!-- 回答部 -->
        <logic:notEmpty name="enq110Form" property="enq110QueList">
          <%
            String[] quePrmName = { "emnSid", "eqmSeq", "eqmDspSec", "eqmQueKbn", "eqmRequire",
                      "eqmRngStrNum", "eqmRngEndNum", "eqmRngStrDat", "eqmRngEndDat", "eqmQueSec",
                      "eqsSeq", "eqsDspName", "eqmOther", "eqmUnitNum" };
          %>
          <%
            String[] ansPrmName = { "eqmAnsText", "eqmAnsTextarea", "eqmAnsNum", "eqmSelectAnsYear",
                      "eqmSelectAnsMonth", "eqmSelectAnsDay", "eqmSelOther", "eqmSelRbn", "eqmSelChk" };
          %>
          <%
            int qnoAuto = 1;
          %>
          <logic:iterate id="mainList" name="enq110Form" property="enq110QueList" indexId="mIdx" scope="request">

            <%
              String mIndex = String.valueOf(mIdx);
            %>
            <%
              String formName = "enq110QueList[" + mIndex + "].";
            %>

            <!-- パラメータを保持 -->
            <html:hidden property="<%=formName + quePrmName[0]%>" />
            <html:hidden property="<%=formName + quePrmName[1]%>" />
            <html:hidden property="<%=formName + quePrmName[2]%>" />
            <html:hidden property="<%=formName + quePrmName[3]%>" />
            <html:hidden property="<%=formName + quePrmName[4]%>" />
            <html:hidden property="<%=formName + quePrmName[5]%>" />
            <html:hidden property="<%=formName + quePrmName[6]%>" />
            <html:hidden property="<%=formName + quePrmName[7]%>" />
            <html:hidden property="<%=formName + quePrmName[8]%>" />
            <html:hidden property="<%=formName + quePrmName[9]%>" />
            <html:hidden property="<%=formName + quePrmName[12]%>" />
            <html:hidden property="<%=formName + quePrmName[13]%>" />

            <logic:equal name="mainList" property="eqmQueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_COMMENT)%>">
              <!-- コメント -->

              <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq110_quet_comment.jsp">
                <jsp:param value="<%=mIdx%>" name="mIdx" />
              </jsp:include>
            </logic:equal>

            <logic:notEqual name="mainList" property="eqmQueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_COMMENT)%>">
              <!-- コメント以外 -->
              <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq110_quet_other.jsp">
                <jsp:param value="<%=mIdx%>" name="mIdx" />
              </jsp:include>
            </logic:notEqual>
          </logic:iterate>
        </logic:notEmpty>
      </div>
      <div class="footerBtn_block">
        <logic:equal name="enq110Form" property="enq110DspMode" value="<%=String.valueOf(Enq110Const.DSP_MODE_ANSWER)%>">
          <button type="button" value="<gsmsg:write key='cmn.ok' />" class="baseBtn" onclick="setParams();buttonPush('enq110answer');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key='cmn.ok' />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key='cmn.ok' />">
            <gsmsg:write key='cmn.ok' />
          </button>
        </logic:equal>
        <logic:equal name="enq110Form" property="enq110DspMode" value="<%=String.valueOf(Enq110Const.DSP_MODE_PREVIEW)%>">
          <logic:equal name="enq110Form" property="enq210editMode" value="<%=String.valueOf(Enq210Form.EDITMODE_TEMPLATE)%>">
            <button type="button" value="<gsmsg:write key='cmn.final' />" class="baseBtn" onclick="setParams();buttonPush('enq110commit');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <gsmsg:write key='cmn.final' />
            </button>
          </logic:equal>
          <logic:notEqual name="enq110Form" property="enq210editMode" value="<%=String.valueOf(Enq210Form.EDITMODE_TEMPLATE)%>">
            <button type="button" value="<gsmsg:write key='enq.05' />" id="kakuteibtn" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key='enq.05' />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key='enq.05' />">
              <gsmsg:write key='enq.05' />
            </button>
          </logic:notEqual>
        </logic:equal>
        <button type="button" value="<gsmsg:write key='cmn.back2' />" class="baseBtn" onclick="buttonPush('enq110back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
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
    <div id="enqAnswerDelete" title="" class="display_n txt_m">
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
          <td class="loading_area w100 txt_m">
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
