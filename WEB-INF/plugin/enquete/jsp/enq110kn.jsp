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
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script type="text/javascript" src="../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/popup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
</head>
<body class="body_03" onload="initImageView('enqImgName');" onunload="windowClose();">
  <html:form action="/enquete/enq110kn">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="enq110DownloadFlg" value="">
    <input type="hidden" name="enq110BinSid" value="">
    <input type="hidden" name="enq110PreTempDirKbn" value="">
    <input type="hidden" name="enq110TempDir" value="">
    <html:hidden property="cmd" />
    <html:hidden property="ansEnqSid" />
    <html:hidden property="enq110DspMode" />
    <html:hidden property="enq110queDate" />
    <html:hidden property="enq110Title" />
    <html:hidden property="enq110knSvBackScreen" />
    <html:hidden property="enq110BackTo" />

    <!-- 回答入力画面でのパラメータ保持 -->
    <logic:notEmpty name="enq110knForm" property="enq110QueListToList">
      <logic:iterate id="mainList" name="enq110knForm" property="enq110QueListToList" indexId="mIdx">
        <%
          String mIndex = String.valueOf(mIdx);
        %>
        <%
          String formName = "enq110QueList[" + mIndex + "].";
        %>
        <%
          String[] quePrmName = { "emnSid", "eqmSeq", "eqmDspSec", "eqmQueKbn", "eqmRequire",
                      "eqmRngStrNum", "eqmRngEndNum", "eqmRngStrDat", "eqmRngEndDat", "eqmQueSec",
                      "", "eqsSeq", "eqsDspName", "eqmUnitNum" };
        %>
        <%
          String[] ansPrmName = { "eqmAnsText", "eqmAnsTextarea", "eqmAnsNum", "eqmSelectAnsYear",
                      "eqmSelectAnsMonth",
                      "eqmSelectAnsDay", "eqmSelOther", "eqmSelRbn", "eqmSelChk", "eqmSelectAnsDate" };
        %>
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
        <html:hidden property="<%=formName + quePrmName[13]%>" />

        <html:hidden property="<%=formName + ansPrmName[0]%>" />
        <html:hidden property="<%=formName + ansPrmName[1]%>" />
        <html:hidden property="<%=formName + ansPrmName[2]%>" />
        <html:hidden property="<%=formName + ansPrmName[3]%>" />
        <html:hidden property="<%=formName + ansPrmName[4]%>" />
        <html:hidden property="<%=formName + ansPrmName[5]%>" />
        <html:hidden property="<%=formName + ansPrmName[6]%>" />
        <html:hidden property="<%=formName + ansPrmName[7]%>" />
        <logic:notEmpty name="mainList" property="eqmSelChk">
          <logic:iterate id="chkList" name="mainList" property="eqmSelChk" indexId="chkIdx">
            <input type="hidden" name="<%=formName + ansPrmName[8]%>" value='<bean:write name="chkList"/>'>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="mainList" property="eqmSubList">
          <logic:iterate id="subList" name="mainList" property="eqmSubList" indexId="subIdx">
            <%
              String sFormName = "eqmSubList[" + subIdx + "].";
            %>
            <input type="hidden" name="<%=formName + sFormName + quePrmName[11]%>" value='<bean:write name="subList" property="eqsSeq"/>'>
            <input type="hidden" name="<%=formName + sFormName + quePrmName[12]%>" value='<bean:write name="subList" property="eqsDspName"/>'>
          </logic:iterate>
        </logic:notEmpty>
      </logic:iterate>
    </logic:notEmpty>

    <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq210_hiddenParams.jsp" />

    <logic:notEmpty name="enq110knForm" property="enq010priority">
      <logic:iterate id="svPriority" name="enq110knForm" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110knForm" property="enq010status">
      <logic:iterate id="svStatus" name="enq110knForm" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110knForm" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq110knForm" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110knForm" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq110knForm" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110knForm" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq110knForm" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq110knForm" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq110knForm" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq110knForm" property="enq210answerList">
      <logic:iterate id="answerUser" name="enq110knForm" property="enq210answerList">
        <input type="hidden" name="enq210answerList" value="<bean:write name="answerUser" />">
      </logic:iterate>
    </logic:notEmpty>


    <!-- HEADER -->
    <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
    <input type="hidden" name="helpPrm" value='<bean:write name="enq110knForm" property="enq110DspMode" />'>

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
          <logic:equal name="enq110knForm" property="enq110DspMode" value="0">
            <gsmsg:write key="enq.enq110kn.01" />
          </logic:equal>
          <logic:notEqual name="enq110knForm" property="enq110DspMode" value="0">
            <gsmsg:write key="enq.39" />
          </logic:notEqual>
        </li>
        <li>
          <div>
            <logic:equal name="enq110knForm" property="enq110DspMode" value="0">
              <button type="button" class="baseBtn" value="<gsmsg:write key='enq.22' />" onClick="buttonPush('enq110kncommit');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <gsmsg:write key='enq.22' />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq110knback');">
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

      <!-- 回答時の確認メッセージ -->
      <logic:equal name="enq110knForm" property="enq110DspMode" value="0">
        <div class="mt10 mb10 txt_l">
          <gsmsg:write key="enq.72" />
        </div>
      </logic:equal>


      <div class="txt_l">
        <img class="classic-display p5" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key='enq.plugin' />">
        <img class="original-display p5" src="../enquete/images/original/menu_icon_single.png" alt="<gsmsg:write key='enq.plugin' />">
        <span class="fs_20 fw_b txt_m">
          <bean:write name="enq110knForm" property="enq110Title" />
        </span>
      </div>

      <!-- BODY -->
      <div class="content_area">
        <!-- 基本情報 -->
        <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq110kn_kihoninfo.jsp" />

        <!-- 回答部 -->
        <logic:notEmpty name="enq110knForm" property="enq110knQueList">
          <logic:iterate id="mainList" name="enq110knForm" property="enq110knQueList" indexId="mIdx" scope="request">


            <logic:equal name="mainList" property="eqmQueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_COMMENT)%>">
              <!-- コメント -->
              <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq110kn_quet_comment.jsp">
                <jsp:param value="<%=mIdx%>" name="mIdx" />
              </jsp:include>
            </logic:equal>

            <logic:notEqual name="mainList" property="eqmQueKbn" value="<%=String.valueOf(GSConstEnquete.SYURUI_COMMENT)%>">
              <!-- コメント以外 -->
              <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq110kn_quet_other.jsp">
                <jsp:param value="<%=mIdx%>" name="mIdx" />
              </jsp:include>
            </logic:notEqual>

          </logic:iterate>
        </logic:notEmpty>
      </div>
      <div class="footerBtn_block">
        <logic:equal name="enq110knForm" property="enq110DspMode" value="0">
          <button type="button" class="baseBtn" value="<gsmsg:write key='enq.22' />" onClick="buttonPush('enq110kncommit');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key='enq.22' />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq110knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>


  </html:form>
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</body>
</html:html>
