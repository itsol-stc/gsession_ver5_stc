<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
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
<%@ page import="jp.groupsession.v2.enq.enq320.Enq320Const"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../enquete/js/enq320.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../smail/css/smail.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

<% boolean backPage970 = false; %>
<logic:equal name="enq320Form" property="enq970BackPage" value="1">
<% backPage970 = true; %>
</logic:equal>
<% if (backPage970) { %>
  <title>GROUPSESSION <gsmsg:write key="enq.enq970.01" /></title>
<% } else { %>
  <title>GROUPSESSION <gsmsg:write key="enq.plugin" /></title>
<% } %>
</head>
<body class="body_03">
  <html:form action="/enquete/enq320">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="enq110answer" value="">

    <html:hidden property="ansEnqSid" />
    <html:hidden property="enq310selectQue" />
    <html:hidden property="enq310selectQueSub" />
    <html:hidden property="enq320svGroup" />
    <html:hidden property="enq320svUser" />
    <html:hidden property="enq320svStsAns" />
    <html:hidden property="enq320svStsNon" />
    <html:hidden property="enq320sortKey" />
    <html:hidden property="enq320order" />
    <html:hidden property="enq320scrollQuestonFlg" />
    <html:hidden property="enq320viewMode" />
    <html:hidden property="backScreen" />

    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp"%>

    <logic:notEmpty name="enq320Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq320Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq320Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq320Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq320Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq320Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq320Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq970_hiddenParams.jsp" />

    <logic:notEmpty name="enq320Form" property="enq970selectEnqSid">
      <logic:iterate id="svEnq970selectEnqSid" name="enq320Form" property="enq970selectEnqSid">
        <input type="hidden" name="enq970selectEnqSid" value='<bean:write name="svEnq970selectEnqSid" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq970priority">
      <logic:iterate id="svEnq970priority" name="enq320Form" property="enq970priority">
        <input type="hidden" name="enq970priority" value='<bean:write name="svEnq970priority" />'>
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq320Form" property="enq970status">
      <logic:iterate id="svEnq970status" name="enq320Form" property="enq970status">
        <input type="hidden" name="enq970status" value='<bean:write name="svEnq970status" />'>
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="helpPrm" value='<bean:write name="enq320Form" property="enq310anony" />'>

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
          <gsmsg:write key="enq.50" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq320back');">
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
          <bean:write name="enq320Form" property="enq310enqTitle" />
        </span>
      </div>

      <table class="table-left" cellpadding="4">

        <!-- 基本情報 重要度 -->
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="project.prj050.4" />
          </th>
          <td class="w30 no_w">
            <span>
              <span>
                <logic:equal name="enq320Form" property="enq310priority" value="0">
                  <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
                  <span class="original-display">
                    <i class="icon-star importance-blue"></i>
                    <i class="icon-star_line"></i>
                    <i class="icon-star_line"></i>
                  </span>
                </logic:equal>
                <logic:equal name="enq320Form" property="enq310priority" value="1">
                  <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
                  <span class="original-display">
                    <i class="icon-star importance-yellow"></i>
                    <i class="icon-star importance-yellow"></i>
                    <i class="icon-star_line"></i>
                </logic:equal>
                <logic:equal name="enq320Form" property="enq310priority" value="2">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
                  <span class="original-display">
                    <i class="icon-star importance-red"></i>
                    <i class="icon-star importance-red"></i>
                    <i class="icon-star importance-red"></i>
                </logic:equal>
              </span>
            </span>
          </td>
          <!-- 基本情報 発信者 -->
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.25" />
          </th>
          <td class="w30 no_w">
            <bean:define id="sdFlg" name="enq320Form" property="enq310senderDelFlg" type="java.lang.Boolean" />
            <bean:define id="ukoFlg" name="enq320Form" property="enq310senderUkoFlg" type="java.lang.Integer" />
            <span class="<%if (sdFlg) {%> text_deluser_enq<%} else if (ukoFlg == 1) {%> mukoUser<%}%>">
              <bean:write name="enq320Form" property="enq310sender" />
            </span>
          </td>
        </tr>

        <!-- 基本情報 アンケート内容 -->
        <tr>
          <th class="txt_c no_w w20">
            <gsmsg:write key="enq.18" />
          </th>
          <td colspan="3" class="w80">
            <htmlframe:write attrClass="w100">
              <bean:write name="enq320Form" property="enq310enqContent" filter="false" />
            </htmlframe:write>
          </td>
        </tr>

        <tr>
          <!-- 基本情報 回答期限 -->
          <th class="txt_c no_w">
            <gsmsg:write key="enq.19" />
          </th>
          <td class="no_w">
            <bean:write name="enq320Form" property="enq310ansLimitDate" />
          </td>
          <!-- 基本情報 結果公開期限 -->
          <th class="txt_c no_w">
            <gsmsg:write key="enq.enq210.11" />
          </th>
          <td class="no_w">
            <bean:define id="ansOpen" name="enq320Form" property="enq310ansOpen" type="java.lang.Integer" />
            <%
              if (ansOpen == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {
            %>
            <bean:write name="enq320Form" property="enq310ansPubFrDate" />
            <logic:empty name="enq320Form" property="enq310pubLimitDate">
              ～&nbsp;<gsmsg:write key="main.man200.9" />
            </logic:empty>
            <logic:notEmpty name="enq320Form" property="enq310pubLimitDate">
        &nbsp;～
              <bean:write name="enq320Form" property="enq310pubLimitDate" />
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
          <th class="txt_c txt_m no_w">
            <gsmsg:write key="cmn.number.of.candidates" />
          </th>
          <td class="txt_l no_w">
            <div>
              <span>
                <gsmsg:write key="cmn.whole" />
              </span>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <span>
                <bean:write name="enq320Form" property="enq310answerCountAll" /><gsmsg:write key="cmn.persons" />
              </span>
            </div>
            <div>
              <span>
                <gsmsg:write key="enq.44" />
              </span>
              &nbsp;&nbsp;
              <span>
                <bean:write name="enq320Form" property="enq310answerCountAr" /><gsmsg:write key="cmn.persons" />
              </span>
              <span>
                [<bean:write name="enq320Form" property="enq310answerCountArPer" />%]
              </span>
            </div>
            <div>
              <span>
                <gsmsg:write key="enq.45" />
              </span>
              <span>
                <bean:write name="enq320Form" property="enq310answerCountUn" /><gsmsg:write key="cmn.persons" />
                <span>
                  [<bean:write name="enq320Form" property="enq310answerCountUnPer" />%]
                </span>
            </div>
          </td>
          <!-- 基本情報 注意事項 -->
          <th class="txt_c txt_m no_w">
            <gsmsg:write key="cmn.hints" />
          </th>
          <td class="txt_l txt_m">
            <span>
              <bean:define id="anony" name="enq320Form" property="enq310anony" type="java.lang.Integer" />
              <bean:define id="ansOpen" name="enq320Form" property="enq310ansOpen" type="java.lang.Integer" />
              <%
                if (anony == GSConstEnquete.ANONYMUS_ON && ansOpen == GSConstEnquete.KOUKAI_ON) {
              %>
                <span><gsmsg:write key="enq.69" /></span>
              <%
                } else if (anony == GSConstEnquete.ANONYMUS_ON) {
              %>
                <span><gsmsg:write key="enq.31" /></span>
              <%
                } else if (ansOpen == GSConstEnquete.KOUKAI_ON) {
              %>
                <span><gsmsg:write key="enq.32" /></span>
              <%
                }
              %>
            </span>
          </td>
        </tr>
      </table>

      <!-- 回答情報 -->
      <div class="mt20 fs_16 txt_l fw_b mb10"><gsmsg:write key="enq.46" /></div>

      <div class="bor1 p5 bgC_lightGray mb10">
        <table class="table-left" cellpadding="5">
          <logic:equal name="enq320Form" property="enq310anony" value="<%=String.valueOf(GSConstEnquete.EMN_ANONNY_NON)%>">
            <tr>
              <th class="txt_c no_w w20">
                <gsmsg:write key="cmn.target" />
              </th>
              <td class="txt_l no_w w80">
                <span>
                  <gsmsg:write key="cmn.group" />
                </span>
                <html:select styleClass="wp200" property="enq320group" onchange="buttonPush('init');">
                  <html:optionsCollection name="enq320Form" property="groupCombo" value="value" label="label" />
                </html:select>
                &nbsp;&nbsp;
                <span>
                  <gsmsg:write key="cmn.user" />
                </span>
                <html:select property="enq320user">
                  <logic:iterate id="user" name="enq320Form" property="userCombo" type="UsrLabelValueBean">
                    <html:option styleClass="<%=user.getCSSClassNameOption()%>" value="<%=user.getValue()%>">
                      <bean:write name="user" property="label" />
                    </html:option>
                  </logic:iterate>
                </html:select>
              </td>
            </tr>
          </logic:equal>
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="cmn.status" />
            </th>
            <td class="no_w">
              <span class="verAlignMid">
                <html:checkbox name="enq320Form" property="enq320stsAns" value="1" styleId="answered" />
                <label for="answered">
                  <gsmsg:write key="enq.22" />
                </label>
                <html:checkbox name="enq320Form" property="enq320stsNon" value="1" styleClass="ml10" styleId="unanswered" />
                <label for="unanswered">
                  <gsmsg:write key="enq.21" />
                </label>
              </span>
            </td>
          </tr>
        </table>
      <div class="mb5">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('enq320search');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
        </div>
      </div>
      <logic:notEmpty name="enq320Form" property="pageList">
      <div class="paging txt_r mb10">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');;">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo" property="enq320pageTop" onchange="enq320changePage(0);">
          <html:optionsCollection name="enq320Form" property="pageList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
      </logic:notEmpty>

      <!-- 結果一覧 -->
      <table class="table-top m0" cellpadding="5" cellspacing="0" id="enq320ansListArea">
        <bean:define id="enqSortKey" name="enq320Form" property="enq320sortKey" type="java.lang.Integer" />
        <bean:define id="enqOrder" name="enq320Form" property="enq320order" type="java.lang.Integer" />
        <%
          String sortSign = "";
        %>
        <%
          String nextOrder = "";
        %>
        <%
          int titleSortKey = 0;
        %>
        <tr>
          <th class="no_w js_tableTopCheck js_tableTopCheck-header cursor_p">
            <%
              titleSortKey = Enq320Const.SORTKEY_JOUTAI;
            %>
            <%
              if (enqSortKey.intValue() == titleSortKey) {
            %>
            <%
              if (enqOrder.intValue() == 1) {
                      sortSign = "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";
                      nextOrder = "0";
                    } else {
                      sortSign = "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";
                      nextOrder = "1";
                    }
            %>
            <%
              } else {
                    nextOrder = "0";
                    sortSign = "";
                  }
            %>
            <a href="#" onClick="enqClickTitle(<%=String.valueOf(titleSortKey)%>, <%=nextOrder%>);">
              <gsmsg:write key="cmn.status" /><%=sortSign%></a>
          </th>
          <th class="no_w cursor_p table_header-evt js_table_header-evt">
            <%
              titleSortKey = Enq320Const.SORTKEY_GROUP;
            %>
            <logic:equal name="enq320Form" property="enq310anony" value="<%=String.valueOf(GSConstEnquete.ANONYMUS_ON)%>">
              <span>
                <gsmsg:write key="cmn.group" />
            </logic:equal>
            <logic:notEqual name="enq320Form" property="enq310anony" value="<%=String.valueOf(GSConstEnquete.ANONYMUS_ON)%>">
              <%
                if (enqSortKey.intValue() == titleSortKey) {
              %>
              <%
                if (enqOrder.intValue() == 1) {
                          sortSign = "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";
                          nextOrder = "0";
                        } else {
                          sortSign = "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";
                          nextOrder = "1";
                        }
              %>
              <%
                } else {
                        nextOrder = "0";
                        sortSign = "";
                      }
              %>
              <a href="#" onClick="enqClickTitle(<%=String.valueOf(titleSortKey)%>, <%=nextOrder%>);">
                <gsmsg:write key="cmn.group" /><%=sortSign%></a>
            </logic:notEqual>
          </th>
          <th class="no_w cursor_p">
            <%
              titleSortKey = Enq320Const.SORTKEY_USER;
            %>
            <logic:equal name="enq320Form" property="enq310anony" value="<%=String.valueOf(GSConstEnquete.ANONYMUS_ON)%>">
              <span>
                <gsmsg:write key="cmn.user" />
              </span>
            </logic:equal>
            <logic:notEqual name="enq320Form" property="enq310anony" value="<%=String.valueOf(GSConstEnquete.ANONYMUS_ON)%>">
              <%
                if (enqSortKey.intValue() == titleSortKey) {
              %>
              <%
                if (enqOrder.intValue() == 1) {
                          sortSign = "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";
                          nextOrder = "0";
                        } else {
                          sortSign = "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";
                          nextOrder = "1";
                        }
              %>
              <%
                } else {
                        nextOrder = "0";
                        sortSign = "";
                      }
              %>
              <a href="#" onClick="enqClickTitle(<%=String.valueOf(titleSortKey)%>, <%=nextOrder%>);">
                <gsmsg:write key="cmn.user" /><%=sortSign%></a>
            </logic:notEqual>
          </th>
          <th class="no_w cursor_p table_header-evt js_table_header-evt">
            <%
              titleSortKey = Enq320Const.SORTKEY_ANSDATE;
            %>
            <%
              if (enqSortKey.intValue() == titleSortKey) {
            %>
            <%
              if (enqOrder.intValue() == 1) {
                      sortSign = "<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>";
                      nextOrder = "0";
                    } else {
                      sortSign = "<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>";
                      nextOrder = "1";
                    }
            %>
            <%
              } else {
                    nextOrder = "0";
                    sortSign = "";
                  }
            %>
            <a href="#" onClick="enqClickTitle(<%=String.valueOf(titleSortKey)%>, <%=nextOrder%>);">
              <gsmsg:write key="enq.51" /><%=sortSign%></a>
          </th>
        </tr>
        <% String listClick = ""; %>

        <logic:notEmpty name="enq320Form" property="ansList">
          <logic:iterate id="ansData" name="enq320Form" property="ansList">

            <bean:define id="ansDataAnony" name="ansData" property="anony" type="java.lang.Integer" />
            <bean:define id="ansDataStatus" name="ansData" property="status" type="java.lang.Integer" />

            <bean:define id="sdFlg" name="ansData" property="userDelFlg" type="java.lang.Boolean" />
            <bean:define id="ukoFlg" name="ansData" property="usrUkoFlg" type="java.lang.Integer" />

            <%
              if (ansDataAnony.intValue() != GSConstEnquete.EMN_ANONNY_ANONNY && ansDataStatus.intValue() == 1) {
            %>
            <%
              listClick =" js_listClick";
            %>
            <%
              } else {
            %>
            <%
              listClick = "";
            %>
            <%
              }
            %>

           <logic:equal name="ansData" property="status" value="1">
             <% if (ansDataAnony.intValue() != GSConstEnquete.EMN_ANONNY_ANONNY && ansDataStatus.intValue() == 1) { %>
              <tr class="js_listHover cursor_p" id="<bean:write name="ansData" property="userSid" />">
              <% } else { %>
                <tr>
              <% } %>

            </logic:equal>
             <logic:equal name="ansData" property="status" value="0">
               <tr>
            </logic:equal>

              <!-- 状態 -->
              <td class="txt_l txt_m<%= listClick %>">
                <logic:equal name="ansData" property="status" value="1">
                  <gsmsg:write key="enq.22" />
                </logic:equal>
                <logic:equal name="ansData" property="status" value="0">
                  <gsmsg:write key="enq.21" />
                </logic:equal>
              </td>
              <!-- グループ -->
              <td class="txt_l<%= listClick %>">
                <bean:write name="ansData" property="group" />
              </td>
              <!-- ユーザー -->

              <td class="txt_l<%= listClick %>">
                <%
                  if (sdFlg) {
                %><span class="text_deluser_enq">
                  <%
                    } else if (ukoFlg == 1) {
                  %><span class="mukoUser">
                    <%
                      }
                    %>
                    <%
                      if (ansDataAnony.intValue() != GSConstEnquete.EMN_ANONNY_ANONNY
                                  && ansDataStatus.intValue() == 1) {
                    %>
                    <span class="cl_linkDef">
                      <bean:write name="ansData" property="user" />
                    </span>
                    <%
                      } else {
                    %>
                    <bean:write name="ansData" property="user" />
                    <%
                      }
                    %>
                    <%
                      if (sdFlg || ukoFlg == 1) {
                    %>
                  </span>
                  <%
                    }
                  %>

              </td>
              <!-- 回答日 -->
              <td class="txt_l<%= listClick %>">
                <logic:notEmpty name="ansData" property="ansDate">
                  <bean:write name="ansData" property="ansDate" />
                </logic:notEmpty>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
      <logic:notEmpty name="enq320Form" property="pageList">
      <div class="paging txt_r mt10">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');;">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo" property="enq320pageBottom" onchange="enq320changePage(1);">
          <html:optionsCollection name="enq320Form" property="pageList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
      </logic:notEmpty>

      <div class="footerBtn_block mt10">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq320back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>