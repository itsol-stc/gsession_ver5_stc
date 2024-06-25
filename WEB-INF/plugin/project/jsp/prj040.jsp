<%@page import="jp.groupsession.v2.prj.model.UserModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<!DOCTYPE html>

<%-- CMD定数 --%>
<%
  String prjAddClick = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_PRJ_ADD_CLICK;
  String prjNameClick = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_PRJ_NAME_CLICK;
  String backClick = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_BACK_CLICK;
  String prjSelectMember = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_SCT_MEM;
  String searchClick = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_SEARCH;
  String export = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_EXPORT;
  String prev = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_PAGE_PREVEW;
  String next = jp.groupsession.v2.prj.prj040.Prj040Action.CMD_PAGE_NEXT;
%>

<%
  String prj040 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ040;
  String mode_add = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_ADD;
%>
<gsmsg:define id="textProjectName" msgkey="project.40" />
<gsmsg:define id="textProjectYosan" msgkey="project.10" />
<gsmsg:define id="textProjectStatus" msgkey="cmn.status" />
<gsmsg:define id="textProjectStart" msgkey="main.src.man250.29" />
<gsmsg:define id="textProjectEnd" msgkey="main.src.man250.30" />

<%
  int[] sortKeyList01 = new int[]{jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_ID,
      jp.groupsession.v2.prj.GSConstProject.SORT_PROJECT_NAME,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_YOSAN,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_STATUS,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_START,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_END};
  String[] title_width01 = new String[]{"10", "40", "10", "10", "10", "10"};
  String[] titleList01 = new String[]{jp.groupsession.v2.prj.GSConstProject.SORT_STR_PRJECT_ID,
      textProjectName, textProjectYosan, textProjectStatus, textProjectStart, textProjectEnd};
%>

<%-- ソートオーダー --%>
<%
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj040.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
</head>

<body>

  <html:form action="/project/prj040">

    <input type="hidden" name="CMD" id="CMD" value="">

    <input type="hidden" name="prj040scStartYearFrom" value="">
    <input type="hidden" name="prj040scStartMonthFrom" value="">
    <input type="hidden" name="prj040scStartDayFrom" value="">
    <input type="hidden" name="prj040scStartYearTo" value="">
    <input type="hidden" name="prj040scStartMonthTo" value="">
    <input type="hidden" name="prj040scStartDayTo" value="">
    <input type="hidden" name="prj040scEndYearFrom" value="">
    <input type="hidden" name="prj040scEndMonthFrom" value="">
    <input type="hidden" name="prj040scEndDayFrom" value="">
    <input type="hidden" name="prj040scEndYearTo" value="">
    <input type="hidden" name="prj040scEndMonthTo" value="">
    <input type="hidden" name="prj040scEndDayTo" value="">

    <html:hidden property="prj010cmdMode" />
    <html:hidden property="prj010page1" />
    <html:hidden property="prj010page2" />
    <html:hidden property="prj010sort" />
    <html:hidden property="prj010order" />
    <html:hidden property="prj010Init" />
    <html:hidden property="selectingProject" />
    <html:hidden property="selectingTodoDay" />
    <html:hidden property="selectingTodoPrj" />
    <html:hidden property="selectingTodoSts" />

    <html:hidden property="prj040searchFlg" />
    <html:hidden property="prj040sort" />
    <html:hidden property="prj040order" />
    <html:hidden property="prj040svScPrjId" />
    <html:hidden property="prj040svScStatusFrom" />
    <html:hidden property="prj040svScStatusTo" />
    <html:hidden property="prj040svScPrjName" />
    <html:hidden property="prj040svScStartYearFrom" />
    <html:hidden property="prj040svScStartMonthFrom" />
    <html:hidden property="prj040svScStartDayFrom" />
    <html:hidden property="prj040svScStartYearTo" />
    <html:hidden property="prj040svScStartMonthTo" />
    <html:hidden property="prj040svScStartDayTo" />
    <html:hidden property="prj040svScEndYearFrom" />
    <html:hidden property="prj040svScEndMonthFrom" />
    <html:hidden property="prj040svScEndDayFrom" />
    <html:hidden property="prj040svScEndYearTo" />
    <html:hidden property="prj040svScEndMonthTo" />
    <html:hidden property="prj040svScEndDayTo" />
    <html:hidden property="prj040svScYosanFr" />
    <html:hidden property="prj040svScYosanTo" />

    <logic:notEmpty name="prj040Form" property="prj040scMemberSid" scope="request">
      <logic:iterate id="item" name="prj040Form" property="prj040scMemberSid" scope="request">
        <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj040Form" property="prj040svScMemberSid" scope="request">
      <logic:iterate id="item" name="prj040Form" property="prj040svScMemberSid" scope="request">
        <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prj020scrId" value="<%=prj040%>" />
    <html:hidden property="prj020cmdMode" value="<%=mode_add%>" />

    <html:hidden property="prj030scrId" value="<%=prj040%>" />
    <html:hidden property="prj030prjSid" value="" />

    <bean:define id="orderKey" name="prj040Form" property="prj040order" />
    <bean:define id="sortKbn" name="prj040Form" property="prj040sort" />
    <%
      int iOrderKey = ((Integer) orderKey).intValue();
    %>
    <%
      int iSortKbn = ((Integer) sortKbn).intValue();
    %>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
          <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
        </li>
        <li>
          <gsmsg:write key="cmn.project" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="project.prj040.1" />
        </li>
        <li>
          <div>
            <logic:equal name="prj040Form" property="prjAdd" value="true">
              <button type="button" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="setDateParam();buttonPush('<%=prjAddClick%>');">
                <img class="classic-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('<%=backClick%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <!-- ページコンテンツ start -->
      <table class="table-left">
        <tr>
            <td class="bgC_header1 txt_l table_title-color" colspan="4">
            <img class="btn_classicImg-display table_header_icon_search" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <span class="table_title-color"><gsmsg:write key="cmn.advanced.search" /></span>
          </td>
        </tr>

        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="project.31" />
          </th>
          <td class="w40">
            <html:text property="prj040scPrjId" styleClass="wp130" size="20" />
          </td>
          <th class="w10 no_w">
            <gsmsg:write key="cmn.status" />
          </th>
          <td class="w40">
            <html:text property="prj040scStatusFrom" maxlength="3" styleClass="wp50" />% ～ <html:text property="prj040scStatusTo" maxlength="3" styleClass="wp50" />%
          </td>
        </tr>

        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="project.40" />
          </th>
          <td>
            <html:text property="prj040scPrjName" maxlength="50" styleClass="wp250" />
          </td>
          <th class="w10 no_w">
            <gsmsg:write key="cmn.squad" />
          </th>
          <td>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onclick="setDateParam();buttonPush('<%=prjSelectMember.toString()%>');">
              <gsmsg:write key="cmn.select" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" onClick="clearUserList();">
              <gsmsg:write key="cmn.clear" />
            </button>
            <div id="selectMember">
              <logic:notEmpty name="prj040Form" property="memberList" scope="request">
                <logic:iterate id="memMdl" name="prj040Form" property="memberList" scope="request" type="CmnUsrmInfModel">
                  <div class="<%=UserUtil.getCSSClassNameNormal(memMdl.getUsrUkoFlg())%> display_inline">
                    <bean:write name="memMdl" property="usiSei" />
                    &nbsp;
                    <bean:write name="memMdl" property="usiMei" />
                  </div>&nbsp;&nbsp;
                </logic:iterate>
              </logic:notEmpty>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="project.10" />
          </th>
          <td colspan="3">
            <html:text property="prj040scYosanFr" styleClass="wp150" maxlength="14" />
            &nbsp;
            <span>
              <gsmsg:write key="project.103" />
            </span>
            &nbsp;～&nbsp;
            <html:text property="prj040scYosanTo" styleClass="wp150" maxlength="14" />
            &nbsp;
            <span>
              <gsmsg:write key="project.103" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="cmn.start" />
          </th>
          <td colspan="3">
            <div class="verAlignMid w100">
              <html:text name="prj040Form" property="prj040StartDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatesf" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDatesf'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
              &nbsp;～&nbsp;
              <html:text name="prj040Form" property="prj040StartDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatest" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDatest'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="cmn.end" />
          </th>
          <td colspan="3">
            <div class="verAlignMid w100">
              <html:text name="prj040Form" property="prj040EndDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDateef" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDateef'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
              &nbsp;～&nbsp;
              <html:text name="prj040Form" property="prj040EndDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateet" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDateet'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
            </div>
          </td>
        </tr>
      </table>

      <div class="display_inline searchBtn pos_rel w100">

        <div>
          <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onclick="buttonPush('<%=searchClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>

        <div class="flo_r searchExportBtn pos_abs">
          <logic:notEmpty name="prj040Form" property="projectList" scope="request">
            <button type="button" name="export" value="<gsmsg:write key="cmn.export" />" class="baseBtn m0" onclick="buttonPush('<%=export%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <gsmsg:write key="cmn.export" />
            </button>
          </logic:notEmpty>
        </div>
      </div>

      <logic:notEmpty name="prj040Form" property="projectList" scope="request">

      <bean:size id="count1" name="prj040Form" property="pageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging mt10">
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%=prev%>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="prj040page1" onchange="changePage(1);">
            <html:optionsCollection name="prj040Form" property="pageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%=next%>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <!-- 下テーブル -->
      <table class="table-top">
        <tr>
          <%
            for (int i = 0; i < sortKeyList01.length; i++) {

                  if (i == 3) {
          %>
          <th class="w10">
            <span>
              <gsmsg:write key="cmn.admin" />
            </span>
          </th>
          <%
            }

                  if (iSortKbn == sortKeyList01[i]) {
                    if (iOrderKey == order_desc) {
          %>
          <th width="<%=title_width01[i]%>%">
            <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
              <span><%=titleList01[i]%><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span>
              </span>
            </a>
          </th>
          <%
            } else {
          %>
          <th width="<%=title_width01[i]%>%">
            <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_desc%>);">
              <span><%=titleList01[i]%><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span>
              </span>
            </a>
          </th>
          <%
            }
                  } else {
          %>
          <th width="<%=title_width01[i]%>%">
            <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
              <span><%=titleList01[i]%></span>
            </a>
          </th>
          <%
            }
                }
          %>
        </tr>

        <logic:notEmpty name="prj040Form" property="projectList" scope="request">
          <logic:iterate id="prjMdl" name="prj040Form" property="projectList" scope="request" indexId="idx">
            <tr class="js_listHover js_listClick cursor_p" data-prjName="<%=prjNameClick%>" data-prjId="<bean:write name="prjMdl" property="projectSid" />">
              <td>
                <span>
                  <bean:write name="prjMdl" property="projectId" />
                </span>
              </td>
              <td class="txt_l lh200">
                <logic:equal name="prjMdl" property="prjBinSid" value="0">
                  <img class="classic-display" src="../project/images/classic/icon_project.png" alt="<gsmsg:write key="cmn.icon" />">
                  <img class="original-display" src="../project/images/original/plugin_project.png" alt="<gsmsg:write key="cmn.icon" />">
                </logic:equal>
                <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
                  <img src="../project/prj040.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prjMdl" property="projectSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />" name="pitctImage" width="30" height="30" alt="<gsmsg:write key="cmn.icon" />" border="1" onload="initImageView('pitctImage');" class="prj_img_ico">
                </logic:notEqual>
                  <span class="cl_linkDef">
                    <bean:write name="prjMdl" property="projectName" />
                  </span>
              </td>
              <td class="txt_r">
                <span>
                  <bean:write name="prjMdl" property="strYosan" />
                </span>
              </td>
              <td class="txt_c">
                <span>
                  <logic:notEmpty name="prjMdl" property="memberList">
                    <logic:iterate id="userMdl" name="prjMdl" property="memberList" type="UserModel">
                      <logic:equal name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <span class="<%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>">
                          <bean:write name="userMdl" property="sei" />
                          &nbsp;
                          <bean:write name="userMdl" property="mei" />
                        </span>
                      </logic:equal>
                      <logic:notEqual name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <del>
                          <bean:write name="userMdl" property="sei" />
                          &nbsp;
                          <bean:write name="userMdl" property="mei" />
                        </del>
                      </logic:notEqual>
                      <br>
                    </logic:iterate>
                  </logic:notEmpty>
                </span>
              </td>
              <td class="txt_c">
                <logic:equal name="prjMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
                  <span>
                    <bean:write name="prjMdl" property="rate" />%<br>(<bean:write name="prjMdl" property="statusName" />)
                  </span>
                </logic:equal>
              </td>
              <td class="txt_c no_w">
                <span>
                  <bean:write name="prjMdl" property="strStartDate" />
                </span>
              </td>
              <td class="txt_c no_w">
                <span>
                  <bean:write name="prjMdl" property="strEndDate" />
                </span>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>

    <bean:size id="count1" name="prj040Form" property="pageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%=prev%>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="prj040page2" onchange="changePage(2);">
            <html:optionsCollection name="prj040Form" property="pageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%=next%>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>
      </logic:notEmpty>

    </div>


    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>

</body>
</html:html>
