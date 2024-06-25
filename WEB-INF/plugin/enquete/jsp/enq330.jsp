<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>

<!--[if IE]><script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js"></script><![endif]-->
<script type="text/javascript" src="../common/js/jplot/jquery.jqplot.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.barRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.pieRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.highlighter.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.categoryAxisRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.canvasAxisTickRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.canvasTextRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.pointLabels.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/jplot/jqplot.cursor.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enq330.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />

<link rel=stylesheet href='../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<% boolean backPage970 = false; %>
<logic:equal name="enq330Form" property="enq970BackPage" value="1">
<% backPage970 = true; %>
</logic:equal>
<% if (backPage970) { %>
  <title>GROUPSESSION <gsmsg:write key="enq.enq970.01" /></title>
<% } else { %>
  <title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
<% } %>
</head>
<body class="body_03" onload="setInit();">
  <html:form action="/enquete/enq330">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="cmd" value="">
    <input type="hidden" name="sch010SelectUsrSid" value="">
    <input type="hidden" name="sch010SelectUsrKbn" value="">
    
    <input type="hidden" name="enq330graphBf" value="<bean:write name="enq330Form" property="enq330graph" />">

    <html:hidden property="ansEnqSid" />
    <html:hidden property="enq310selectQue" />
    <html:hidden property="enq310selectQueSub" />
    <html:hidden property="enq330queKbn" />
    <html:hidden property="enq330sortKey" />
    <html:hidden property="enq330order" />
    <html:hidden property="enq330viewDetailFlg" />
    <html:hidden property="enq330scrollQuestonFlg" />
    <html:hidden property="enq330svGroup" />
    <html:hidden property="enq330svUser" />
    <html:hidden property="enq330svAnsText" />
    <html:hidden property="enq330svAnsNumKbn" />
    <html:hidden property="enq330svAnsNumFrom" />
    <html:hidden property="enq330svAnsNumTo" />
    <html:hidden property="enq330svAnsDateFromYear" />
    <html:hidden property="enq330svAnsDateFromMonth" />
    <html:hidden property="enq330svAnsDateFromDay" />
    <html:hidden property="enq330svAnsDateToYear" />
    <html:hidden property="enq330svAnsDateToMonth" />
    <html:hidden property="enq330svAnsDateToDay" />
    <html:hidden property="backScreen" />
    <input type="hidden" name="yearRangeMinFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMaxFr" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMinTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    <input type="hidden" name="yearRangeMaxTo" value="<%= GSConstEnquete.YEARCOMBO_RANGE %>">
    
    <logic:notEmpty name="enq330Form" property="enq330svAns">
      <logic:iterate id="svAns" name="enq330Form" property="enq330svAns">
        <input type="hidden" name="enq330svAns" value="<bean:write name="svAns" />">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp"%>
    <input type="hidden" name="helpPrm" value='<bean:write name="enq330Form" property="enq330queKbn" /><bean:write name="enq330Form" property="enq310anony" />'>

    <logic:notEmpty name="enq330Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq330Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq330Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq330Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq330Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq330Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq330Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq970_hiddenParams.jsp" />

    <logic:notEmpty name="enq330Form" property="enq970selectEnqSid">
      <logic:iterate id="svEnq970selectEnqSid" name="enq330Form" property="enq970selectEnqSid">
        <input type="hidden" name="enq970selectEnqSid" value='<bean:write name="svEnq970selectEnqSid" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq970priority">
      <logic:iterate id="svEnq970priority" name="enq330Form" property="enq970priority">
        <input type="hidden" name="enq970priority" value='<bean:write name="svEnq970priority" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq330Form" property="enq970status">
      <logic:iterate id="svEnq970status" name="enq330Form" property="enq970status">
        <input type="hidden" name="enq970status" value='<bean:write name="svEnq970status" />'>
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w85 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
          <img class="header_pluginImg" src="../enquete/images/original/header_enquete.png" alt="<gsmsg:write key="enq.plugin" />">
        </li>
        <li>
          <gsmsg:write key="enq.plugin" />
        </li>
        <li class="pageTitle_subFont">
         <gsmsg:write key="enq.57" />
         </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq330back');">
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

      <table class="table-left" cellpadding="4" id="enq330detailArea1">
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="cmn.title" />
          </th>
          <td class="border_none">
            <div class="component_bothEnd">
              <div>
                <bean:write name="enq330Form" property="enq310enqTitle" />
              </div>
              <div>
                <button type="button" value="<gsmsg:write key='cmn.detail' />" class="baseBtn no_w" onClick="enq330changeBasicDetail(1);" id="enq330detailBtn0">
                  <gsmsg:write key='cmn.detail' />
                </button>
              </div>
            </div>
          </td>
        </tr>
      </table>

      <table class="table-left" id="enq330detailArea">
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="cmn.title" />
          </th>
          <td colspan="4">
            <div class="component_bothEnd">
              <div>
                <bean:write name="enq330Form" property="enq310enqTitle" />
              </div>
              <div>
                <button type="button" value="<gsmsg:write key='cmn.close' />" class="baseBtn no_w" onClick="enq330changeBasicDetail(0);" id="enq330detailBtn1">
                  <gsmsg:write key='cmn.close' />
                </button>
              </div>
            </div>
          </td>
        </tr>

        <!-- 基本情報 重要度 -->
        <tr>
          <th class="txt_c no_w">
            <gsmsg:write key="enq.24" />
          </th>
          <td class="no_w">
            <span>
              <logic:equal name="enq330Form" property="enq310priority" value="0">
                <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" alt="<gsmsg:write key="enq.33" />">
                <span class="original-display">
                  <i class="icon-star importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="enq330Form" property="enq310priority" value="1">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" alt="<gsmsg:write key="enq.34" />">
                <span class="original-display">
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star_line"></i>
              </logic:equal>
              <logic:equal name="enq330Form" property="enq310priority" value="2">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" alt="<gsmsg:write key="enq.35" />">
                <span class="original-display">
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
              </logic:equal>
            </span>
          </td>
          <!-- 基本情報 発信者 -->
          <th class="txt_c no_w">
            <gsmsg:write key="enq.25" />
          </th>
          <td class="no_w">
            <bean:define id="sdFlg" name="enq330Form" property="enq310senderDelFlg" type="java.lang.Boolean" />
            <bean:define id="ukoFlg" name="enq330Form" property="enq310senderUkoFlg" type="java.lang.Integer" />
            <span class="text_base2<% if (sdFlg) { %> text_deluser_enq<% } else if (ukoFlg == 1) { %> mukoUser<% } %>">
              <bean:write name="enq330Form" property="enq310sender" />
            </span>
          </td>
        </tr>

        <!-- 基本情報 アンケート内容 -->
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.26" />
          </th>
          <td colspan="3" class="80">
            <htmlframe:write attrClass="w100" attrId="enq330detailArea_htmlframe">
              <bean:write name="enq330Form" property="enq310enqContent" filter="false" />
            </htmlframe:write>
          </td>
        </tr>

        <tr>
          <!-- 基本情報 回答期限 -->
          <th class="txt_c no_w">
            <gsmsg:write key="enq.19" />
          </th>
          <td class="no_w">
            <bean:write name="enq330Form" property="enq310ansLimitDate" />
          </td>
          <!-- 基本情報 結果公開期限 -->
          <th class="txt_c no_w">
            <gsmsg:write key="enq.enq210.11" />
          </th>
          <td class="no_w">
            <bean:define id="ansOpen" name="enq330Form" property="enq310ansOpen" type="java.lang.Integer" />
            <% if (ansOpen == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {%>
            <bean:write name="enq330Form" property="enq310ansPubFrDate" />
            <logic:empty name="enq330Form" property="enq310pubLimitDate">
              ～&nbsp;<gsmsg:write key="main.man200.9" />
            </logic:empty>
            <logic:notEmpty name="enq330Form" property="enq310pubLimitDate">
             &nbsp;～
              <bean:write name="enq330Form" property="enq310pubLimitDate" />
            </logic:notEmpty>
            <% } else {%>
            <gsmsg:write key="cmn.private" />
            <% } %>
          </td>
        </tr>
        <tr>
          <!-- 対象人数 -->

          <th class="txt_c txt_m no_w">
            <gsmsg:write key="enq.58" />
          </th>
          <td class="txt_l no_w">

            <div>
              <span>
                <gsmsg:write key="cmn.whole" />
              </span>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <span>
                <bean:write name="enq330Form" property="enq310answerCountAll" /><gsmsg:write key="cmn.persons" />
              </span>
            </div>
            <div>
              <span>
                <gsmsg:write key="enq.44" />
              </span>
              &nbsp;&nbsp;
              <span>
                <bean:write name="enq330Form" property="enq310answerCountAr" /><gsmsg:write key="cmn.persons" />
              </span>
              <span>
                [<bean:write name="enq330Form" property="enq310answerCountArPer" />%]
              </span>
            </div>
            <div>
              <span>
                <gsmsg:write key="enq.45" />
              </span>
              <span>
                <bean:write name="enq330Form" property="enq310answerCountUn" /><gsmsg:write key="cmn.persons" />
                <span>[<bean:write name="enq330Form" property="enq310answerCountUnPer" />%]
                </span>
                <input type="hidden" name="enq330graphLabelNoAns" value="<gsmsg:write key="anp.ans.notyet" />"> <input type="hidden" name="enq330graphValueNoAns" value="<bean:write name="enq330Form" property="enq310answerCountUnNum" />">
            </div>
          </td>

          <!-- 基本情報 注意事項 -->
          <th class="txt_c txt_m no_w">
            <gsmsg:write key="cmn.hints" />
          </th>
          <td class="txt_l txt_m">
            <span>
              <bean:define id="anony" name="enq330Form" property="enq310anony" type="java.lang.Integer" />
              <bean:define id="ansOpen" name="enq330Form" property="enq310ansOpen" type="java.lang.Integer" />
              <% if (anony == GSConstEnquete.ANONYMUS_ON && ansOpen == GSConstEnquete.KOUKAI_ON) { %>
              <span>
                <gsmsg:write key="enq.69" />
              </span>
              <% } else if (anony == GSConstEnquete.ANONYMUS_ON) { %>
              <span>
                <gsmsg:write key="enq.31" />
              </span>
              <% } else if (ansOpen == GSConstEnquete.KOUKAI_ON) { %>
              <span>
                <gsmsg:write key="enq.32" />
              </span>
              <% } %>
            </span>
          </td>
        </tr>
      </table>

      <table  class="w100 table_ans bgC_body">
        <!-- 設問情報 -->
        <tr>
          <td class="bor1 w100 p0">
            <!-- 上部 -->
            <div class="bgC_header2">
              <table class="m0 w100">

                <tr>
                  <td class="txt_l txt_t no_w p5 w3">
                    <span class=fw_b>
                      <gsmsg:write key="enq.37" />
                    </span>
                  </td>
                  <td class="txt_l txt_t no_w p5 w5">
                    <span class=fw_b>
                      <bean:write name="enq330Form" property="enq330queNo" />
                    </span>
                  </td>
                  <td class="txt_l txt_t p5" rowspan="3">
                    <span class=fw_b>
                      <bean:write name="enq330Form" property="enq330question" />
                    </span>
                  </td>
                </tr>
                    <logic:equal name="enq330Form" property="enq330queRequire" value="1">
                <tr>
                  <td class="txt_l txt_t no_w pl5 lh100" colspan="2">
                      <span class="cl_fontWarn">
                        <gsmsg:write key="cmn.asterisk" />
                        <gsmsg:write key="cmn.required" />
                      </span>
                      <br>
                  </td>
                </tr>
                    </logic:equal>
                <tr>
                  <td class="txt_l txt_t pl5 no_w" colspan="2">
                    <span>
                      [<bean:write name="enq330Form" property="enq330queKbnName" />]
                    </span>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>

            <!-- 下部 -->
            <div class="area_question">
              <table class="w100">

                <tr>
                  <td class="txt_l txt_t pl10">
                    <bean:define id="enqQueKbn" name="enq330Form" property="enq330queKbn" type="java.lang.Integer" />
                    <logic:notEmpty name="enq330Form" property="queSubList">
                      <table class="table_setsumon w20" cellpadding="5" cellspacing="0">

                        <tr>
                          <td class="txt_l">&nbsp;</td>
                          <td class="w30 txt_r no_w p0">
                            <gsmsg:write key="reserve.use.num" />
                          </td>
                          <td class="w30 txt_r no_w p0 pl5">
                            <gsmsg:write key="cmn.whole" />
                          </td>
                          <td class="w30 txt_r no_w p0 pl5">
                            <gsmsg:write key="enq.22" />
                          </td>
                        </tr>
                        <logic:iterate id="queSubData" name="enq330Form" property="queSubList">
                          <tr>
                            <td class="txt_l txt_t no_w p0">
                              <bean:write name="queSubData" property="dspName" />
                              <input type="hidden" name="enq330graphLabel" value="<bean:write name="queSubData" property="ansDspName" />"> <input type="hidden" name="enq330graphValue" value="<bean:write name="queSubData" property="answerNum" />">
                            </td>
                            <td class="txt_r no_w p0 pl10">
                              <bean:write name="queSubData" property="answer" /><gsmsg:write key="cmn.persons" />
                            </td>
                            <td class="txt_r no_w p0 pl5">
                              <bean:write name="queSubData" property="answerAllPer" />%
                            </td>
                            <td class="txt_r no_w p0 pl5">
                              <bean:write name="queSubData" property="answerArPer" />%
                            </td>
                          </tr>
                        </logic:iterate>
                        </tbody>
                      </table>
                    </logic:notEmpty>
                    <% if  (enqQueKbn == GSConstEnquete.SYURUI_INTEGER || enqQueKbn == GSConstEnquete.SYURUI_DAY) {%>
                    <table class="table_setsumon w90" cellpadding="50" cellspacing="10">
                      <tr>
                        <td class="txt_l p0" colspan="4">
                          <% if  (enqQueKbn == GSConstEnquete.SYURUI_DAY) {%>
                          <span><gsmsg:write key="enq.29" />：<bean:write name="enq330Form" property="enq330answerMinValue" /></span>
                          <span class="ml10"><gsmsg:write key="enq.30" />：<bean:write name="enq330Form" property="enq330answerMaxValue" /></span>
                          <% } else { %>
                          <span><gsmsg:write key="enq.29" />：<bean:write name="enq330Form" property="enq330answerMinValue" /></span>
                          <span class="ml10"><gsmsg:write key="enq.30" />：<bean:write name="enq330Form" property="enq330answerMaxValue" /></span>
                          <span class="ml10"><gsmsg:write key="enq.59" />：<bean:write name="enq330Form" property="enq330answerAvgValue" /></span>
                          <% } %>
                        </td>
                      </tr>
                    </table>
                    <% } %>
                  </td>
                </tr>

                <% if  (enqQueKbn == GSConstEnquete.SYURUI_SINGLE || enqQueKbn == GSConstEnquete.SYURUI_MULTIPLE) {%>
                <tr id="enq330graphArea">
                  <td class="txt_l txt_t pt25 pl25">
                    <div id="image3">
                      <div id="enq330GraphArea" class="wp500 hp350"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="txt_c txt_t">
                    <div>
                      <span class="verAlignMid">
                        <br>
                        <html:radio name="enq330Form" property="enq330graph" styleId="graph0" value="0" onclick="enq330changeGraph();" />
                        <label for="graph0">
                          <span>
                            <gsmsg:write key="cmn.bar.graph" />
                          </span>
                        </label>
                        <html:radio name="enq330Form" property="enq330graph" styleClass="ml10" styleId="graph1" value="1" onclick="enq330changeGraph();" />
                        <label for="graph1">
                          <span>
                            <gsmsg:write key="cmn.bar.graph" />(<gsmsg:write key="enq.61" />）
                          </span>
                        </label>
                      </span>
                    </div>
                    <%   if  (enqQueKbn == GSConstEnquete.SYURUI_SINGLE) {%>
                    <div>
                      <span class="verAlignMid">
                        <br>
                        <html:radio name="enq330Form" property="enq330graph" styleId="graph2" value="2" onclick="enq330changeGraph();" />
                        <label for="graph2">
                          <span>
                            <gsmsg:write key="cmn.pie.graph" />
                          </span>
                        </label>
                        <html:radio name="enq330Form" property="enq330graph" styleClass="ml10" styleId="graph3" value="3" onclick="enq330changeGraph();" />
                        <label for="graph3">
                          <span>
                            <gsmsg:write key="cmn.pie.graph" />(<gsmsg:write key="enq.61" />）
                          </span>
                        </label>
                        <br>
                      </span>
                    </div>
                    <% } else
                { %>&nbsp;<% } %>
                  </td>
                </tr>
                <% } %>
              </table>

              <table>
                <tr>
                  <td class="txt_l txt_t pl10 no_w">
                    <gsmsg:write key="enq.44" />
                  </td>
                  <td class="txt_l no_w">
                    &nbsp;&nbsp;
                    <bean:write name="enq330Form" property="enq330answerCountAr" /><gsmsg:write key="cmn.persons" />
                  </td>
                </tr>
                <tr>
                  <td class="txt_l txt_t pl10 no_w">
                    <gsmsg:write key="enq.45" />
                  </td>
                  <td class="no_w">
                    &nbsp;&nbsp;
                    <bean:write name="enq330Form" property="enq330answerCountUn" /><gsmsg:write key="cmn.persons" />
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </table>

      <!-- 検索条件 -->
      <div class="bor1 p5 bgC_lightGray">
        <table class="table-left">

          <logic:equal name="enq330Form" property="enq310anony" value="<%= String.valueOf(GSConstEnquete.EMN_ANONNY_NON) %>">
            <tr>
              <th class="txt_c no_w">
                <gsmsg:write key="cmn.target" />
              </th>
              <td class="txt_l no_w">
                <span>
                  <gsmsg:write key="cmn.group" />
                </span>
                <html:select styleClass="wp200" property="enq330group" onchange="buttonPush('init');">
                  <html:optionsCollection name="enq330Form" property="groupCombo" value="value" label="label" />
                </html:select>
                &nbsp;&nbsp;
                <span>
                  <gsmsg:write key="cmn.user" />
                </span>
                <html:select property="enq330user">
                  <logic:iterate id="user" name="enq330Form" property="userCombo" type="UsrLabelValueBean">
                    <html:option styleClass="<%=user.getCSSClassNameOption()%>" value="<%=user.getValue()%>">
                      <bean:write name="user" property="label" />
                    </html:option>
                  </logic:iterate>
                </html:select>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <th class="txt_c no_w w20">
              <gsmsg:write key="enq.22" />
            </th>
            <td>

              <% if  (enqQueKbn == GSConstEnquete.SYURUI_TEXT || enqQueKbn == GSConstEnquete.SYURUI_TEXTAREA) {%>
              <html:text name="enq330Form" property="enq330ansText" maxlength="100" styleClass="que_text_init" />
              <% } %>

              <% if  (enqQueKbn == GSConstEnquete.SYURUI_INTEGER || enqQueKbn == GSConstEnquete.SYURUI_DAY) {%>
              <span class="verAlignMid">
                <html:radio name="enq330Form" property="enq330ansNumKbn" value="0" styleId="ansNumKbn0" onclick="changeSearchArea(0);" />
                <label for="ansNumKbn0">
                  <gsmsg:write key="cmn.single" />
                </label>
                <html:radio name="enq330Form" property="enq330ansNumKbn" value="1" styleClass="ml10" styleId="ansNumKbn1" onclick="changeSearchArea(0);" />
                <label class="mr10" for="ansNumKbn1">
                  <gsmsg:write key="cmn.range" />
                </label>
              <% if  (enqQueKbn == GSConstEnquete.SYURUI_INTEGER) {%>
              <html:text name="enq330Form" property="enq330ansNumFrom" maxlength="16" styleClass="ans_text_num2 wp140" /><span id="enq330SearchArea"><gsmsg:write key="tcd.142" /><html:text name="enq330Form" property="enq330ansNumTo" maxlength="16" styleClass="ans_text_num2 wp140" />
              </span>
                </div>
                <% } %>
                <% if  (enqQueKbn == GSConstEnquete.SYURUI_DAY) {%>
                <div class="no_w">
                  <span class="verAlignMid">
                    <html:text name="enq330Form" property="enq330ansDateFrom" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </span>
                  <span id="enq330SearchArea" class="verAlignMid">
                    <gsmsg:write key="tcd.142" />
                    <html:text name="enq330Form" property="enq330ansDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </span>
                </div>
                <% } %>
                <% } %>

                <% if  (enqQueKbn == GSConstEnquete.SYURUI_SINGLE || enqQueKbn == GSConstEnquete.SYURUI_MULTIPLE) { %>
                <logic:notEmpty name="enq330Form" property="queSubList">
                  <span class="verAlignMid">
                    <logic:iterate id="queSubData" name="enq330Form" property="queSubList" indexId="idx">
                      <% String selAnsId = "answer" + String.valueOf(idx.intValue()); %>
                      <html:multibox name="enq330Form" property="enq330ans" styleId="<%= selAnsId %>">
                        <bean:write name="queSubData" property="queSubSeq" />
                      </html:multibox>
                      <label for="<%= selAnsId %>">
                        <span class="mr10">
                          <bean:write name="queSubData" property="dspName" />
                        </span>
                      </label>
                    </logic:iterate>
                  </span>
                </logic:notEmpty>
                <% } %>
            </td>
          </tr>

        </table>
        <div class="mb5">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('enq330search');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>

      </div>
      <div class="component_bothEnd">
        <div class="txt_l mt10 mb5">
          <gsmsg:write key="bbs.bbs041.2" />：
          <bean:write name="enq330Form" property="enq330searchCount" />
          <gsmsg:write key="cmn.number" />
        </div>
        <logic:notEmpty name="enq330Form" property="pageList">
          <div class="paging txt_r mt10 mb10">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');;">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="enq330pageTop" onchange="enq330changePage(0);">
              <html:optionsCollection name="enq330Form" property="pageList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:notEmpty>
      </div>
      <!-- 結果一覧 -->
      <table class="table-top m0" cellpadding="5" cellspacing="0" id="enq330ansListArea">
        <bean:define id="enqSortKey" name="enq330Form" property="enq330sortKey" type="java.lang.Integer" />
        <bean:define id="enqOrder" name="enq330Form" property="enq330order" type="java.lang.Integer" />
        <% String sortSign = ""; %>
        <% String nextOrder = ""; %>
        <tr>
          <th class="no_w js_tableTopCheck js_tableTopCheck-header cursor_p">
            <% int titleSortKey = 1; %>
            <logic:equal name="enq330Form" property="enq310anony" value="1">
              <span>
                <gsmsg:write key="cmn.group" />
              </span>
            </logic:equal>
            <logic:notEqual name="enq330Form" property="enq310anony" value="1">
              <% if (enqSortKey.intValue() == titleSortKey) { %>
              <%   if (enqOrder.intValue() == 1) { sortSign="<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>"; nextOrder = "0"; } else { sortSign="<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>"; nextOrder = "1"; } %>
              <% } else { nextOrder = "0"; sortSign = ""; } %>
              <a href="#" onClick="enqClickTitle(<%= String.valueOf(titleSortKey) %>, <%= nextOrder %>);">
                <gsmsg:write key="cmn.group" /><%= sortSign %></a>
            </logic:notEqual>
          </th>
          <th class="w20 no_w cursor_p">
            <% titleSortKey = 2; %>
            <logic:equal name="enq330Form" property="enq310anony" value="1">
              <span>
                <gsmsg:write key="cmn.user" />
              </span>
            </logic:equal>
            <logic:notEqual name="enq330Form" property="enq310anony" value="1">
              <% if (enqSortKey.intValue() == titleSortKey) { %>
              <%   if (enqOrder.intValue() == 1) { sortSign="<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>"; nextOrder = "0"; } else { sortSign="<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>"; nextOrder = "1"; } %>
              <% } else { nextOrder = "0"; sortSign = ""; } %>
              <a href="#" onClick="enqClickTitle(<%= String.valueOf(titleSortKey) %>, <%= nextOrder %>);">
                <gsmsg:write key="cmn.user" /><%= sortSign %></a>
            </logic:notEqual>
          </th>
          <th class="w20 no_w cursor_p">
            <% titleSortKey = 0; %>
            <% if (enqSortKey.intValue() == titleSortKey) { %>
            <%   if (enqOrder.intValue() == 1) { sortSign="<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>"; nextOrder = "0"; } else { sortSign="<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>"; nextOrder = "1"; } %>
            <% } else { nextOrder = "0"; sortSign = ""; } %>
            <a href="#" onClick="enqClickTitle(<%= String.valueOf(titleSortKey) %>, <%= nextOrder %>);">
              <gsmsg:write key="enq.51" /><%= sortSign %></a>
          </th>
          <th class="no_w w40">
            <span>
              <gsmsg:write key="enq.60" />
            </span>
          </th>
        </tr>

        <logic:notEmpty name="enq330Form" property="ansList">
          <logic:iterate id="ansData" name="enq330Form" property="ansList">
            <bean:define id="sdFlg" name="ansData" property="userDelFlg" type="java.lang.Boolean" />
            <bean:define id="enq330anony" name="ansData" property="anony" type="java.lang.Boolean" />
            <bean:define id="ukoFlg" name="ansData" property="usrUkoFlg" type="java.lang.Integer" />
             <% if (!enq330anony) { %>
              <tr class="js_listHover cursor_p" onClick="openUserInfoWindow(<bean:write name="ansData" property="userSid" />);">
             <% } else { %>
              <tr>
             <% } %>
              <!-- グループ -->
              <td class="txt_l txt_t">
                <bean:write name="ansData" property="group" />
              </td>
              <!-- ユーザ -->
              <td class="txt_l txt_t">
                <bean:define id="sdFlg" name="ansData" property="userDelFlg" type="java.lang.Boolean" />
                <bean:define id="enq330anony" name="ansData" property="anony" type="java.lang.Boolean" />
                <bean:define id="ukoFlg" name="ansData" property="usrUkoFlg" type="java.lang.Integer" />
                <% boolean userLinkFlg = (!sdFlg && !enq330anony); %>
                <% if (sdFlg) { %><span class="text_deluser_enq cl_linkDef">
                  <% } else if (ukoFlg == 1) {%><span class="mukoUser">
                    <% }%>
                    <% if (!enq330anony) { %>
                      <span class="cl_linkDef"><bean:write name="ansData" property="user" /></span>
                    <% } %>
                    <% if (sdFlg | ukoFlg == 1) { %>
                  </span>
                  <% } %>

              </td>
              <!-- 回答日 -->
              <td class="txt_l txt_t">
                <bean:write name="ansData" property="ansDate" />
              </td>
              <!-- 回答値 -->
              <td class="txt_l txt_t">
                <bean:write name="ansData" property="ansValue" />
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>

      </table>

      <logic:notEmpty name="enq330Form" property="pageList">
        <div class="paging txt_r mt10">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');;">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="enq330pageBottom" onchange="enq330changePage(1);">
            <html:optionsCollection name="enq330Form" property="pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>

      <div class="footerBtn_block mt10">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq330back');">
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