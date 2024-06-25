<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/reserve" prefix="reserve" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<%@ page import="jp.groupsession.v2.rsv.rsv010.Rsv010Form"%>

<!DOCTYPE html>

<%
  int sort_name = GSConstReserve.RSV_SORT_NAME;
%>
<%
  int sort_from = GSConstReserve.RSV_SORT_FROM;
%>
<%
  int sort_to = GSConstReserve.RSV_SORT_TO;
%>
<%
  int sort_content = GSConstReserve.RSV_SORT_CONTENT;
%>
<%
  int sort_sisetu = GSConstReserve.RSV_SORT_SISETU;
%>
<%
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>
<%
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" />[<gsmsg:write key="reserve.rsv100.1" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../reserve/js/rsv100.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../reserve/js/sisetuPopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/reservepopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onkeydown="return keyPress(event.keyCode);" onload="rsv100ChgDateKbn();" onunload="windowClose();">
  <html:form action="/reserve/rsv100">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="cmd" value="">
    <html:hidden property="rsv100selectedFromYear" />
    <html:hidden property="rsv100selectedFromMonth" />
    <html:hidden property="rsv100selectedFromDay" />
    <html:hidden property="rsv100selectedToYear" />
    <html:hidden property="rsv100selectedToMonth" />
    <html:hidden property="rsv100selectedToDay" />
    <html:hidden property="rsv100svDateKbn" />
    <html:hidden property="rsv100svFromYear" />
    <html:hidden property="rsv100svFromMonth" />
    <html:hidden property="rsv100svFromDay" />
    <html:hidden property="rsv100svToYear" />
    <html:hidden property="rsv100svToMonth" />
    <html:hidden property="rsv100svToDay" />
    <html:hidden property="rsv100svGrp1" />
    <html:hidden property="rsv100svGrp2" />
    <html:hidden property="rsv100InitFlg" />
    <html:hidden property="rsv100SearchFlg" />
    <html:hidden property="rsv100SortKey" />
    <html:hidden property="rsv100OrderKey" />

    <html:hidden property="rsv100svKeyWord" />
    <html:hidden property="rsv100svSearchCondition" />
    <html:hidden property="rsv100svTargetMok" />
    <html:hidden property="rsv100svTargetNiyo" />
    <html:hidden property="rsv100svApprStatus" />
    <html:hidden property="rsv100svSelectedKey1" />
    <html:hidden property="rsv100svSelectedKey2" />
    <html:hidden property="rsv100svSelectedKey1Sort" />
    <html:hidden property="rsv100svSelectedKey2Sort" />
    <html:hidden property="rsv100SearchSvFlg" />
    <html:hidden property="rsv100svSelectSisKbn" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHiddenSiborikomi.jsp"%>

    <logic:notEmpty name="rsv100Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv100Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv100Form" property="rsvSelectedIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv100Form" property="rsvSelectedIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="reserve.rsv100.1" />
        </li>
        <li>
          <div>
            <logic:notEmpty name="rsv100Form" property="rsv100resultList">
              <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
                <gsmsg:write key="cmn.pdf" />
              </button>
            </logic:notEmpty>
            <logic:equal name="rsv100Form" property="rsvGroupEditFlg" value="true">
              <button type="button" name="btn_sisetu_settei" class="baseBtn" value="<gsmsg:write key="reserve.settings" />" onclick="setDateParam();buttonPush('sisetu_settei');">
                <img class="btn_classicImg-display" src="../reserve/images/classic/icon_resv_settei.png" alt="<gsmsg:write key="reserve.settings" />">
                <img class="btn_originalImg-display" src="../reserve/images/original/icon_resv_settei.png" alt="<gsmsg:write key="reserve.settings" />">
                <gsmsg:write key="reserve.settings" />
              </button>
            </logic:equal>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <bean:define id="thisForm" name="rsv100Form" type="Rsv010Form"></bean:define>
      <reserve:rsvlistHeadPane  thisForm="<%=thisForm %>" rsv020BtnEv="setDateParam();buttonPush('nikkan');" rsv010BtnEv="setDateParam();buttonPush('syukan');" rsv100BtnEv="" useRsvFilter="false">
        <jsp:attribute name="searchPane">
          <reserve:rsvlistHead_rsv100/>
        </jsp:attribute>
      </reserve:rsvlistHeadPane>

      <table class="table-left display-fixed">
        <%
          String headName = "";
              jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(
                  request);
              String msgTanto = gsMsg.getMessage("reserve.use.name.1");
              String msgUser = gsMsg.getMessage("reserve.use.name.2");
        %>

        <logic:equal name="rsv100Form" property="rsv100svSelectSisKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_HEYA)%>">
          <%
            headName = msgTanto;
          %>
        </logic:equal>
        <logic:equal name="rsv100Form" property="rsv100svSelectSisKbn" value="<%=String.valueOf(GSConstReserve.RSK_KBN_CAR)%>">
          <%
            headName = msgUser;
          %>
        </logic:equal>

        <bean:define id="searchFlg" name="rsv100Form" property="rsv100SearchFlg" />
        <logic:equal name="searchFlg" value="true">
          <tr>
            <th class="txt_c wp100 no_w">
              <span>
                <gsmsg:write key="reserve.output.item" />
              </span>
            </th>
            <td colspan="4">
              <table class="w100">
                <tr>
                  <td class="w100 border_none">
                    <div>
                      <span class="no_w verAlignMid mr10">
                        <html:multibox styleId="roomId" name="rsv100Form" property="rsv100CsvOutField" value="1" />
                        <label for="roomId">
                          <gsmsg:write key="reserve.55" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="room" name="rsv100Form" property="rsv100CsvOutField" value="2" />
                        <label for="room">
                          <gsmsg:write key="cmn.facility" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid">
                        <logic:equal name="rsv100Form" property="rsvAdmFlg" value="true">
                          <html:multibox styleId="userId" name="rsv100Form" property="rsv100CsvOutField" value="3" styleClass="" />
                          <label for="userId" class="mr10">
                            <gsmsg:write key="cmn.user.id" />
                          </label>
                        </logic:equal>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="user" name="rsv100Form" property="rsv100CsvOutField" value="4" />
                        <label for="user">
                          <gsmsg:write key="reserve.73" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="useMok" name="rsv100Form" property="rsv100CsvOutField" value="5" />
                        <label for="useMok">
                          <gsmsg:write key="reserve.72" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="dateFr" name="rsv100Form" property="rsv100CsvOutField" value="6" />
                        <label for="dateFr">
                          <gsmsg:write key="reserve.rsv100.14" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="timeFr" name="rsv100Form" property="rsv100CsvOutField" value="7" />
                        <label for="timeFr">
                          <gsmsg:write key="cmn.starttime" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="dateTo" name="rsv100Form" property="rsv100CsvOutField" value="8" />
                        <label for="dateTo">
                          <gsmsg:write key="reserve.rsv100.15" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="timeTo" name="rsv100Form" property="rsv100CsvOutField" value="9" />
                        <label for="timeTo">
                          <gsmsg:write key="cmn.endtime" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="biko" name="rsv100Form" property="rsv100CsvOutField" value="10" />
                        <label for="biko">
                          <gsmsg:write key="cmn.content" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="edit" name="rsv100Form" property="rsv100CsvOutField" value="11" />
                        <label for="edit">
                          <gsmsg:write key="cmn.edit.permissions" />
                        </label>
                      </span><!--
                   --><span class="no_w verAlignMid mr10">
                        <html:multibox styleId="public" name="rsv100Form" property="rsv100CsvOutField" value="12" />
                        <label for="public">
                          <gsmsg:write key="cmn.public.kbn" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeya mr10 verAlignMid">
                        <html:multibox styleId="usekbn" name="rsv100Form" property="rsv100CsvOutField" value="13" />
                        <label for="usekbn">
                          <gsmsg:write key="reserve.use.kbn" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeyaCar mr10 verAlignMid">
                        <html:multibox styleId="contact" name="rsv100Form" property="rsv100CsvOutField" value="14" />
                        <label for="contact">
                          <gsmsg:write key="reserve.contact" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeya mr10 verAlignMid">
                        <html:multibox styleId="guide" name="rsv100Form" property="rsv100CsvOutField" value="15" />
                        <label for="guide">
                          <gsmsg:write key="reserve.guide" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeya mr10 verAlignMid">
                        <html:multibox styleId="parknum" name="rsv100Form" property="rsv100CsvOutField" value="16" />
                        <label for="parknum">
                          <gsmsg:write key="reserve.park.num" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldCar mr10 verAlignMid">
                        <html:multibox styleId="dest" name="rsv100Form" property="rsv100CsvOutField" value="17" />
                        <label for="dest">
                          <gsmsg:write key="reserve.dest" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeyaCar mr10 verAlignMid">
                        <html:multibox styleId="busyo" name="rsv100Form" property="rsv100CsvOutField" value="18" />
                        <label for="busyo">
                          <gsmsg:write key="reserve.busyo" />
                        </label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeyaCar mr10 verAlignMid">
                        <html:multibox styleId="usename" name="rsv100Form" property="rsv100CsvOutField" value="19" />
                        <label for="usename"><%=headName%></label>
                      </span><!--
                   --><span class="no_w csvOutFieldHeyaCar verAlignMid">
                        <html:multibox styleId="usenum" name="rsv100Form" property="rsv100CsvOutField" value="20" />
                        <label for="usenum">
                          <gsmsg:write key="reserve.use.num" />
                        </label>
                      </span>
                    </div>
                  </td>
                  <td class="border_none">
                    <span class="no_w">
                      <button type="button" name="btn_usrimp" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('export');">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                        <gsmsg:write key="cmn.export" />
                      </button>
                    </span>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:equal>
      </table>

      <logic:notEmpty name="rsv100Form" property="rsv100resultList">
        <bean:size id="count1" name="rsv100Form" property="rsv100PageList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging mt20">
            <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <logic:notEmpty name="rsv100Form" property="rsv100PageList">
              <html:select styleClass="paging_combo" property="rsv100PageTop" onchange="changePage(0);">
                <html:optionsCollection name="rsv100Form" property="rsv100PageList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <logic:notEmpty name="rsv100Form" property="rsv100resultList">
        <%
          jp.groupsession.v2.rsv.biz.RsvCommonBiz rsvCmnBiz = new jp.groupsession.v2.rsv.biz.RsvCommonBiz();
        %>

        <table class="table-top table-fixed" >
          <tr>
            <th class="w30">
              <a href="#!" onClick="return selectChange(1);" class="text_tlw">
                <gsmsg:write key="reserve.73" />
              </a>
            </th>
            <th class="w20">
              <a href="#!" onClick="return selectChange(2);" class="text_tlw">
                <gsmsg:write key="cmn.facility" />
              </a>
            </th>
            <th class="w10">
              <a href="#!" onClick="return selectChange(3);" class="text_tlw">
                <gsmsg:write key="reserve.rsv100.14" />
              </a>
            </th>
            <th class="w10">
              <a href="#!" onClick="return selectChange(4);" class="text_tlw">
                <gsmsg:write key="reserve.rsv100.15" />
              </a>
            </th>
            <th class="w30">
              <a href="#!" onClick="return selectChange(5);" class="text_tlw">
                <gsmsg:write key="reserve.72" />
              </a>
            </th>
          </tr>

          <bean:define id="mod" value="0" />
          <logic:iterate id="list" name="rsv100Form" property="rsv100resultList" indexId="idx" scope="request">
            <% String listHover = "js_listHover"; %>
            <logic:equal name="list" property="public" value="<%=String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS)%>">
              <% listHover = ""; %>
            </logic:equal>
            <% String rsvEditClass = " js_rsvLinkClick cursor_p"; %>
            <logic:notEqual name="list" property="viewFlg" value="true">
              <% rsvEditClass = ""; %>
            </logic:notEqual>
            <tr class="<%= listHover %><%= rsvEditClass %>" id="<bean:write name="list" property="rsySisetuSid"/>">
              <td class="txt_l txt_m">
                <logic:equal name="list" property="usrJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                  <bean:define id="mukoUserClass">textLink</bean:define>
                  <logic:equal name="list" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass">mukoUser</bean:define>
                  </logic:equal>
                  <input type="hidden" name="usrSid" value="<bean:write name="list" property="rsyAuid"/>">
                  <span class="<%=mukoUserClass%> js_usrPop cursor_p"><bean:write name="list" property="rsySeiMei" /></span>
                </logic:equal>
                <logic:equal name="list" property="usrJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <del>
                    <bean:write name="list" property="rsySeiMei" />
                  </del>
                  <br>
                </logic:equal>
              </td>

              <td class="txt_l txt_m">
                <bean:write name="list" property="rsySisetu" />
              </td>
              <td class="txt_c txt_m">
                <bean:write name="list" property="rsyFrom" />
              </td>
              <td class="txt_c txt_m">
                <bean:write name="list" property="rsyTo" />
              </td>
              <td class="txt_l txt_m">
                <bean:define id="rsvSisApprStatus" name="list" property="rsyApprStatus" type="java.lang.Integer" />
                <bean:define id="rsvSisApprKbn" name="list" property="rsyApprKbn" type="java.lang.Integer" />
                <%
                  String[] mokApprStatus = rsvCmnBiz.getMokApprStatus(request.getLocale(),
                              rsvSisApprStatus.intValue(), rsvSisApprKbn.intValue());
                %>
                <logic:notEqual name="list" property="public" value="<%=String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS)%>">
                  <span class="<%=mokApprStatus[3]%>" ><%=mokApprStatus[2]%><bean:write name="list" property="rsyContent" />
                  </span>
                </logic:notEqual>
                <logic:equal name="list" property="public" value="<%=String.valueOf(GSConstReserve.PUBLIC_KBN_PLANS)%>">
                  <span>
                    <bean:write name="list" property="rsyContent" />
                  </span>
                </logic:equal>
              </td>
            </tr>
          </logic:iterate>
          </logic:notEmpty>
        </table>

        <div>

          <logic:notEmpty name="rsv100Form" property="rsv100resultList">
            <bean:size id="count1" name="rsv100Form" property="rsv100PageList" scope="request" />
            <logic:greaterThan name="count1" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <logic:notEmpty name="rsv100Form" property="rsv100PageList">
                  <html:select styleClass="paging_combo" property="rsv100PageBottom" onchange="changePage(1);">
                    <html:optionsCollection name="rsv100Form" property="rsv100PageList" value="value" label="label" />
                  </html:select>
                </logic:notEmpty>
                <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </logic:notEmpty>
        </div>
    </div>

    <div id="reservePop" title="" class="display_n">
      <div id="rsvCreateArea" class="w100 h100 ofy_h"></div>
    </div>


    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>