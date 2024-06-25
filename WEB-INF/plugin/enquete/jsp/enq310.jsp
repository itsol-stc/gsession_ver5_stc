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
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<gsjsmsg:js filename="gsjsmsg.js" />
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enq310.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../smail/css/smail.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

<% boolean backPage970 = false; %>
<logic:equal name="enq310Form" property="enq970BackPage" value="1">
<% backPage970 = true; %>
</logic:equal>
<% if (backPage970) { %>
  <title>GROUPSESSION <gsmsg:write key="enq.enq970.01" /></title>
<% } else { %>
  <title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
<% } %>

</head>
<body class="body_03">
  <html:form action="/enquete/enq310">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="enq310selectQue" value="">
    <input type="hidden" name="enq310selectQueSub" value="">
    <input type="hidden" name="enq320viewMode" value="">
    <input type="hidden" name="enq330ans" value="">
    <input type="hidden" name="enq330svAns" value="">
    <html:hidden property="ansEnqSid" />
    <html:hidden property="backScreen" />

    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp"%>
    <logic:notEmpty name="enq310Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq310Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq310Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq310Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq310Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq310Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq310Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq970_hiddenParams.jsp" />

    <logic:notEmpty name="enq310Form" property="enq970selectEnqSid">
      <logic:iterate id="svEnq970selectEnqSid" name="enq310Form" property="enq970selectEnqSid">
        <input type="hidden" name="enq970selectEnqSid" value='<bean:write name="svEnq970selectEnqSid" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq970priority">
      <logic:iterate id="svEnq970priority" name="enq310Form" property="enq970priority">
        <input type="hidden" name="enq970priority" value='<bean:write name="svEnq970priority" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq310Form" property="enq970status">
      <logic:iterate id="svEnq970status" name="enq310Form" property="enq970status">
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
          <gsmsg:write key="enq.47" />
        </li>

        <li>
          <div>
            <button type="button" value="<gsmsg:write key='cmn.csv' />" class="baseBtn" onclick="buttonPush('export');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.csv" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.csv" />">
              <gsmsg:write key='cmn.csv' />
            </button>
            <button type="button" value="<gsmsg:write key='cmn.pdf' />" class="baseBtn" onclick="buttonPush('pdf')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <gsmsg:write key='cmn.pdf' />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq310backTo020');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper w85 mrl_auto">
      <div class="txt_l">
        <img class="classic-display p5" src="../enquete/images/classic/header_enquete.png" alt="<gsmsg:write key='enq.plugin' />">
        <img class="original-display p5" src="../enquete/images/original/menu_icon_single.png" alt="<gsmsg:write key='enq.plugin' />">
        <span class="fs_20 fw_b txt_m">
          <bean:write name="enq310Form" property="enq310enqTitle" />
        </span>
      </div>
      <table class="table-left">

        <!-- 基本情報 重要度 -->
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="project.prj050.4" />
          </th>
          <td class="no_w w30">
            <span>
              <logic:equal name="enq310Form" property="enq310priority" value="0">
                <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                <span class="original-display">
                  <i class="icon-star importance-blue"></i>
                  <i class="icon-star_line"></i>
                  <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="enq310Form" property="enq310priority" value="1">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                <span class="original-display">
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star importance-yellow"></i>
                  <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="enq310Form" property="enq310priority" value="2">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                <span class="original-display">
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
                  <i class="icon-star importance-red"></i>
              </logic:equal>
            </span>
          </td>
          <!-- 基本情報 発信者 -->
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.25" />
          </th>
          <td class="no_w w30" colspan="3">
            <bean:define id="sdFlg" name="enq310Form" property="enq310senderDelFlg" type="java.lang.Boolean" />
            <bean:define id="ukoFlg" name="enq310Form" property="enq310senderUkoFlg" type="java.lang.Integer" />
            <span class=" <%if (sdFlg) {%> text_deluser_enq<%} else if (ukoFlg == 1) {%> mukoUser<%}%>">
              <bean:write name="enq310Form" property="enq310sender" />
            </span>
          </td>
        </tr>

        <!-- 基本情報 アンケート内容 -->
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.18" />
          </th>
          <td class="w80" colspan="3">
            <htmlframe:write attrClass="w100">
              <bean:write name="enq310Form" property="enq310enqContent" filter="false" />
            </htmlframe:write>
          </td>
        </tr>
        <!-- 基本情報 回答期限 -->
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.19" />
          </th>

          <td class="no_w w30">
            <bean:write name="enq310Form" property="enq310ansLimitDate" />
          </td>
          <!-- 基本情報 結果公開期限 -->
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.enq210.11" />
          </th>
          <td class="no_w w30">
            <bean:define id="ansOpen" name="enq310Form" property="enq310ansOpen" type="java.lang.Integer" />
            <%
      if (ansOpen == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {
    %>
            <bean:write name="enq310Form" property="enq310ansPubFrDate" />
            <logic:empty name="enq310Form" property="enq310pubLimitDate">～&nbsp;<gsmsg:write key="main.man200.9" />
            </logic:empty>
            <logic:notEmpty name="enq310Form" property="enq310pubLimitDate">&nbsp;～
              <bean:write name="enq310Form" property="enq310pubLimitDate" />
            </logic:notEmpty>
            <%
      } else {
    %>
            <gsmsg:write key="cmn.private" />
            <%
      }
    %>
          </td>
        </tr>

        <tr>
          <!-- 対象人数 -->
          <th class="txt_c txt_m no_w w20">
            <gsmsg:write key="cmn.number.of.candidates" />
          </th>
          <td class="txt_l no_w w30">
            <div>
              <span>
                <gsmsg:write key="cmn.whole" />
              </span>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <span class="answered">
                <a href="#!" onclick="enq310Taisyo('enq310list', 0);return false;" class="answered"><bean:write name="enq310Form" property="enq310answerCountAll" /><gsmsg:write key="cmn.persons" /></a>
              </span>
            </div>
            <div>
              <gsmsg:write key="enq.44" />
              &nbsp;&nbsp;
              <span>
                <a href="#!" onclick="enq310Taisyo('enq310list', 1);return false;" class="answered"><bean:write name="enq310Form" property="enq310answerCountAr" /><gsmsg:write key="cmn.persons" /></a>
              </span>
              <span>[<bean:write name="enq310Form" property="enq310answerCountArPer" />%]</span>
            </div>
            <div>
              <gsmsg:write key="enq.45" />
              <span>
                <a href="#!" onclick="enq310Taisyo('enq310list', 2);return false;" class="answered"><bean:write name="enq310Form" property="enq310answerCountUn" /><gsmsg:write key="cmn.persons" /></a>
              </span>
              <span>[<bean:write name="enq310Form" property="enq310answerCountUnPer" />%]</span>

            </div>
          </td>
          <!-- 基本情報 注意事項 -->
          <th class="txt_c txt_m no_w w20">
            <gsmsg:write key="enq.27" />
          </th>
          <td class="txt_l txt_m w30">
            <span>
              <bean:define id="anony" name="enq310Form" property="enq310anony" type="java.lang.Integer" />
              <bean:define id="ansOpen" name="enq310Form" property="enq310ansOpen" type="java.lang.Integer" />
              <%
            if (anony == GSConstEnquete.ANONYMUS_ON && ansOpen == GSConstEnquete.KOUKAI_ON) {
          %>
              <gsmsg:write key="enq.69" />
              <%
            } else if (anony == GSConstEnquete.ANONYMUS_ON) {
          %>
              <gsmsg:write key="enq.31" />
              <%
            } else if (ansOpen == GSConstEnquete.KOUKAI_ON) {
          %>
              <gsmsg:write key="enq.32" />
              <%
            }
          %>
            </span>
          </td>
        </tr>
      </table>

      <!-- 回答情報 -->
      <div class="w100 mt20 txt_l fs_16 fw_b">
        <gsmsg:write key="enq.46" />
      </div>

      <!-- 結果一覧 -->
      <table class="table-top" cellpadding="5" cellspacing="0">
        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="enq.48" />
            </span>
          </th>
          <th class="no_w">
            <span>
              <gsmsg:write key="enq.49" />
            </span>
          </th>
        </tr>

        <logic:notEmpty name="enq310Form" property="queList">
          <logic:iterate id="queData" name="enq310Form" property="queList">
            <tr>
              <!-- 設問内容 -->
              <td class="txt_l">
                <table>
                  <tr>
                    <td class="border_none txt_l">
                      <div class="wp100">
                        <gsmsg:write key="enq.12" />
                        <bean:write name="queData" property="no" />
                        <br>
                        <span>
                          [<bean:write name="queData" property="queKbnName" />]
                        </span>
                        <logic:equal name="queData" property="require" value="1">
                          <br>
                          <span class="cl_fontWarn">
                            <gsmsg:write key="enq.28" />
                          </span>
                        </logic:equal>
                      </div>
                    </td>
                    <td class="w80 txt_l txt_t border_none p0">
                      <div class="mb10">
                        <bean:write name="queData" property="question" />
                      </div>
                      <table>
                        <logic:notEmpty name="queData" property="subList">
                          <logic:iterate id="subQueData" name="queData" property="subList">
                            <tr>
                              <td class="txt_l no_w border_none p0">
                                <logic:equal name="subQueData" property="queSubSeq" value="-1">
                                  <gsmsg:write key="cmn.other" />
                                </logic:equal>
                                <logic:notEqual name="subQueData" property="queSubSeq" value="-1">
                                  <bean:write name="subQueData" property="dspName" />
                                </logic:notEqual>
                              </td>
                              <td class="txt_r pl10 no_w border_none">
                                <a href="#!" onclick="enq310DetailSelect(<bean:write name="queData" property="queSeq" />, <bean:write name="subQueData" property="queSubSeq" />);" class="answered">
                                  <bean:write name="subQueData" property="answer" /><gsmsg:write key="cmn.persons" />
                                </a>
                              </td>
                              <td class="txt_r pl10 no_w border_none">
                                <bean:write name="subQueData" property="answerAllPer" />%
                              </td>
                            </tr>
                          </logic:iterate>
                        </logic:notEmpty>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>

              <td class="txt_l txt_t p0">
                <table>
                  <tr>
                    <td class="txt_l pb0 no_w border_none">
                      <gsmsg:write key="enq.44" />
                    </td>
                    <td class="txt_r pl20 no_w pb0 border_none">
                      <a href="#!" onclick="enq310Detail(<bean:write name="queData" property="queSeq" />);return false;" class="answered">
                        <bean:write name="queData" property="answerCountAr" /><gsmsg:write key="cmn.persons" />
                      </a>
                    </td>
                    <td class="txt_r pl20 no_w pb0 border_none">
                      <bean:write name="queData" property="answerCountArPer" />%
                    </td>
                    <td class="txt_r border_none" rowspan="2"></td>
                  </tr>

                  <tr>
                    <td class="txt_l border_none border_none">
                      <gsmsg:write key="enq.45" />
                    </td>
                    <td class="txt_r pl10 no_w border_none">
                      <bean:write name="queData" property="answerCountUn" /><gsmsg:write key="cmn.persons" />
                    </td>
                    <td class="txt_r pl10 no_w border_none">
                      <bean:write name="queData" property="answerCountUnPer" />%
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key='cmn.csv' />" class="baseBtn" onclick="buttonPush('export');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.csv" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.csv" />">
          <gsmsg:write key='cmn.csv' />
        </button>
        <button type="button" value="<gsmsg:write key='cmn.pdf' />" class="baseBtn" onclick="buttonPush('pdf')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <gsmsg:write key='cmn.pdf' />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq310backTo020');">
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