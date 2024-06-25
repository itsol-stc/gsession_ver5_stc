
<%@page import="jp.groupsession.v2.prj.model.UserModel"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.prj.model.TodoTantoModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>


<%-- CMD定数 --%>
<%
  String prjEditClick = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_PRJ_EDIT_CLICK;
  String back030 = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_BACK_CLICK;
  String todoAdd = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_TODO_ADD;
  String todoEdit = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_TODO_EDIT;
  String todoRef = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_TODO_TITLE_CLICK;
  String todoSearch = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_TODO_SEARCH;
  String cirSend = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_CIR_SEND;
  String smlSend = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_SML_SEND;
  String memberEdit = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_MEMBER_EDIT;
  String smlNew = jp.groupsession.v2.sml.GSConstSmail.MSG_CREATE_MODE_NEW;
  String todoImport = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_TODO_IMPORT;
  String detailDir = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_DETAIL_DIR;
  String prev = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_PAGE_PREVEW;
  String next = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_PAGE_NEXT;
  String cmdDelTo = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_DEL_TODO;
  String editStatus = jp.groupsession.v2.prj.prj030.Prj030Action.CMD_EDIT_STATUS;
%>
<gsmsg:define id="projectTitle" msgkey="cmn.project" />
<gsmsg:define id="title" msgkey="cmn.title" />
<gsmsg:define id="todoNum" msgkey="project.prj050.5" />
<gsmsg:define id="todoTanto" msgkey="cmn.staff" />
<gsmsg:define id="todoWeight" msgkey="project.prj050.4" />
<gsmsg:define id="todoStatus" msgkey="cmn.status" />
<gsmsg:define id="todoStartPlan" msgkey="project.100" />
<gsmsg:define id="todoLimitPlan" msgkey="project.src.66" />
<%
  int[] sortKeyList01 = new int[] {
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_TITLE,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_NO,
      -1,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_WEIGHT,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_STATUS,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_START_PLAN,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_LIMIT_PLAN
  };
  String[] title_width01 = new String[] { "40", "10", "15", "10", "10", "10", "10" };
  String[] titleList01 = new String[] {
      title,
      todoNum,
      todoTanto,
      todoWeight,
      todoStatus,
      todoStartPlan,
      todoLimitPlan
  };
  String dspId = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ030;
%>

<logic:notEqual name="prj030Form" property="prjTodoEdit" value="true">
  <%
    title_width01[0] = "33";
  %>
</logic:notEqual>

<%
  String prj030 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ030;
  String search_ok = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEARCH_FLG_OK);
  String mode_add = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_ADD;
  String mode_edit = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_EDIT;
%>

<%-- ソートオーダー --%>
<%
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<bean:define id="orderKey" name="prj030Form" property="prj030order" />
<bean:define id="sortKbn" name="prj030Form" property="prj030sort" />
<bean:define id="prjSid" name="prj030Form" property="prj030prjSid" />
<%
  int iOrderKey = ((Integer) orderKey).intValue();
%>
<%
  int iSortKbn = ((Integer) sortKbn).intValue();
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /> <gsmsg:write key="project.prj030.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/jquery-ui-1.8.16.custom.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj030.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>

<logic:equal name="prj030Form" property="useAddress" value="true">
  <script src="../address/js/adrPopup.js?<%=GSConst.VERSION_PARAM%>"></script>
</logic:equal>

<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onunload="windowClose();">

  <html:form action="/project/prj030">

    <input type="hidden" name="CMD" id="CMD" value="">
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
    <html:hidden property="prj040scPrjId" />
    <html:hidden property="prj040scStatusFrom" />
    <html:hidden property="prj040scStatusTo" />
    <html:hidden property="prj040scPrjName" />
    <html:hidden property="prj040scStartYearFrom" />
    <html:hidden property="prj040scStartMonthFrom" />
    <html:hidden property="prj040scStartDayFrom" />
    <html:hidden property="prj040scStartYearTo" />
    <html:hidden property="prj040scStartMonthTo" />
    <html:hidden property="prj040scStartDayTo" />
    <html:hidden property="prj040scEndYearFrom" />
    <html:hidden property="prj040scEndMonthFrom" />
    <html:hidden property="prj040scEndDayFrom" />
    <html:hidden property="prj040scEndYearTo" />
    <html:hidden property="prj040scEndMonthTo" />
    <html:hidden property="prj040scEndDayTo" />
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
    <html:hidden property="prj040page1" />
    <html:hidden property="prj040page2" />
    <html:hidden property="prj040sort" />
    <html:hidden property="prj040order" />
    <html:hidden property="prj040scYosanFr" />
    <html:hidden property="prj040scYosanTo" />
    <html:hidden property="prj040svScYosanFr" />
    <html:hidden property="prj040svScYosanTo" />

    <logic:notEmpty name="prj030Form" property="prj040scMemberSid" scope="request">
      <logic:iterate id="item" name="prj030Form" property="prj040scMemberSid" scope="request">
        <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj030Form" property="prj040svScMemberSid" scope="request">
      <logic:iterate id="item" name="prj030Form" property="prj040svScMemberSid" scope="request">
        <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prj030scrId" />
    <html:hidden property="prj030prjSid" />
    <html:hidden property="prj030sort" />
    <html:hidden property="prj030order" />
    <html:hidden property="prj030Init" />
    <input type="hidden" name="prjmvComment">

    <html:hidden property="prj020scrId" value="<%=prj030%>" />
    <html:hidden property="prj020cmdMode" value="<%=mode_edit%>" />
    <html:hidden property="prj020prjSid" value="<%=String.valueOf(prjSid)%>" />

    <html:hidden property="prj070scrId" value="<%=prj030%>" />
    <html:hidden property="prj070scPrjSid" value="<%=String.valueOf(prjSid)%>" />
    <html:hidden property="prj070svScPrjSid" value="<%=String.valueOf(prjSid)%>" />
    <html:hidden property="prj070searchFlg" value="<%=search_ok%>" />

    <html:hidden property="prj050scrId" value="<%=prj030%>" />
    <html:hidden property="prj050prjSid" value="<%=String.valueOf(prjSid)%>" />
    <html:hidden property="prj050cmdMode" />
    <html:hidden property="prj050todoSid" />

    <html:hidden property="prj060scrId" value="<%=prj030%>" />
    <html:hidden property="prj060prjSid" value="<%=String.valueOf(prjSid)%>" />
    <html:hidden property="prj060todoSid" />

    <html:hidden property="movedDspId" />

    <input type="hidden" name="sml020ProcMode" value="<%=smlNew%>">

    <bean:define id="projectMdl" name="prj030Form" property="projectItem" />

    <logic:notEmpty name="prj030Form" property="treeFormLv1" scope="request">
      <logic:iterate id="lv1" name="prj030Form" property="treeFormLv1" scope="request">
        <input type="hidden" name="treeFormLv1" value="<bean:write name="lv1"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj030Form" property="treeFormLv2" scope="request">
      <logic:iterate id="lv2" name="prj030Form" property="treeFormLv2" scope="request">
        <input type="hidden" name="treeFormLv2" value="<bean:write name="lv2"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj030Form" property="treeFormLv3" scope="request">
      <logic:iterate id="lv3" name="prj030Form" property="treeFormLv3" scope="request">
        <input type="hidden" name="treeFormLv3" value="<bean:write name="lv3"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj030Form" property="treeFormLv4" scope="request">
      <logic:iterate id="lv4" name="prj030Form" property="treeFormLv4" scope="request">
        <input type="hidden" name="treeFormLv4" value="<bean:write name="lv4"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj030Form" property="treeFormLv5" scope="request">
      <logic:iterate id="lv5" name="prj030Form" property="treeFormLv5" scope="request">
        <input type="hidden" name="treeFormLv5" value="<bean:write name="lv5"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj030Form" property="treeFormLv6" scope="request">
      <logic:iterate id="lv6" name="prj030Form" property="treeFormLv6" scope="request">
        <input type="hidden" name="treeFormLv6" value="<bean:write name="lv6"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="selectDir" />
    <html:hidden property="prj030SepKey" />

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
          <gsmsg:write key="project.prj030.1" />
        </li>
        <li>
          <div>
            <logic:equal name="prj030Form" property="prjEdit" value="true">
              <button type="button" value="<gsmsg:write key="cmn.fixed" />" class="baseBtn" onclick="buttonPush('<%=prjEditClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
                <gsmsg:write key="cmn.fixed" />
              </button>
            </logic:equal>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('<%=back030%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="mb5">
      <span class="fs_18 fw_b">
        <logic:equal name="projectMdl" property="prjBinSid" value="0">
          <img class="classic-display" src="../project/images/classic/icon_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" >
          <img class="original-display" src="../project/images/original/plugin_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
        </logic:equal>
        <logic:notEqual name="projectMdl" property="prjBinSid" value="0">
          <img src="../project/prj030.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prj030Form" property="prj030prjSid" />&prj010PrjBinSid=<bean:write name="projectMdl" property="prjBinSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" /> onload="initImageView('pitctImage');" class="prj_img_ico wp30hp30">
        </logic:notEqual>
        <bean:write name="projectMdl" property="projectName" />
      </span>
    </div>

    <div class="wrapper_2column">


      <div class="side_multi-left bgC_none">

        <bean:define id="rate" name="projectMdl" property="rate" />

        <% String oriRate = String.valueOf(rate); %>

        <% int roundRate = Integer.valueOf(oriRate); %>
        <% int notEndRate; %>
        <%
          roundRate -= Integer.valueOf(roundRate) % 10;
          notEndRate = 100 - roundRate;
        %>

        <logic:equal name="projectMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
          <table class="txt_l w100">
            <tr>
              <td class="no_w">
                <span></span>
              </td>
              <logic:equal name="projectMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MIN)%>">
                <logic:equal name="projectMdl" property="notEndRate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
                  <td class="w<%=notEndRate%> txt_c no_w fw_b bor1">
                    <span>
                      <gsmsg:write key="project.27" />
                      <bean:write name="rate" />%
                    </span>
                  </td>
                </logic:equal>
              </logic:equal>

              <logic:equal name="projectMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
                <logic:equal name="projectMdl" property="notEndRate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MIN)%>">
                  <td class="w<%=roundRate%> bgC_progress txt_c no_w bor1">
                    <span class="cl_white fw_b fs_14">
                      <gsmsg:write key="project.27" />
                      <bean:write name="rate" />%
                    </span>
                  </td>
                </logic:equal>
              </logic:equal>

              <logic:greaterThan name="projectMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MIN)%>">
                <logic:lessThan name="projectMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
                  <td class="w<%=roundRate%> no_w bgC_progress txt_c bor1">
                    <span class="cl_white fw_b fs_14">
                      <gsmsg:write key="project.27" />
                      <bean:write name="rate" />%
                    </span>
                  </td>
                  <td class="w<%=notEndRate%> txt_c no_w bor1"></td>
                </logic:lessThan>
              </logic:greaterThan>
            </tr>
          </table>
        </logic:equal>


        <logic:equal name="projectMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
          <!-- メインに表示する member -->
          <table class="table-left">
            <tr>
              <th class="border_right_none bgC_header1">
                <input type="checkbox" name="allCheck" onClick="changeChk();">
              </th>
              <th class="no_w bgC_header1" colspan=3>
                <div class="component_bothEnd">
                  <div>
                    <bean:size id="count" name="prj030Form" property="userList" scope="request" />
                    <bean:define id="memberCount" value="<%=String.valueOf(count)%>" />
                    <span class="table_title-color fw_n fs_14">
                      <gsmsg:write key="cmn.member" /><%=memberCount%><gsmsg:write key="cmn.name3" />
                    </span>
                  </div>
                  <div>
                    <logic:equal name="prj030Form" property="useCircular" value="true">
                      <button type="button" class="iconBtn-border" value="&nbsp;" onclick="return buttonPush('<%=cirSend%>');">
                        <img class="classic-display img-18" src="../project/images/classic/icon_circular.gif">
                        <img class="original-display img-18" src="../project/images/original/icon_circular.png">
                      </button>
                    </logic:equal>
                    <logic:equal name="prj030Form" property="useSmail" value="true">
                      <button type="button" class="iconBtn-border" value="&nbsp;" onclick="return buttonPush('<%=smlSend%>');">
                        <img class="classic-display img-18" src="../project/images/classic/icon_smail.gif">
                        <img class="original-display img-18" src="../project/images/original/plugin_smail.png">
                      </button>
                    </logic:equal>
                  </div>
                </div>
              </th>

              <logic:notEmpty name="prj030Form" property="userList" scope="request">
                <logic:iterate id="userMdl" name="prj030Form" property="userList" scope="request" indexId="idx" type="UserModel">

                  <% String zskStatusClass = "bgC_tableCell"; %>
                  <logic:equal name="prj030Form" property="useZaiseki" value="true">
                    <logic:equal name="userMdl" property="zaisekiKbn" value="1">
                      <% zskStatusClass="zsk_listColor-zaiseki"; %>
                    </logic:equal>
                    <logic:equal name="userMdl" property="zaisekiKbn" value="2">
                      <% zskStatusClass="zsk_listColor-huzai"; %>
                    </logic:equal>
                    <logic:equal name="userMdl" property="zaisekiKbn" value="0">
                      <% zskStatusClass="zsk_listColor-sonota"; %>
                    </logic:equal>
                  </logic:equal>
                  <tr class="<%= zskStatusClass %>">

                  <td class="border_right_none">
                      <logic:equal name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <html:multibox name="prj030Form" property="prj030sendMember">
                          <bean:write name="userMdl" property="userSid" />
                        </html:multibox>
                      </logic:equal>
                    </td>
                    <td class="txt_c border_right_none">
                      <logic:equal name="userMdl" property="adminKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_POWER_NORMAL)%>">
                        <img class="classic-display" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="cmn.member" />">
                        <img class="original-display" src="../common/images/original/icon_user.png" alt="<gsmsg:write key="cmn.member" />">
                      </logic:equal>
                      <logic:equal name="userMdl" property="adminKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_POWER_ADMIN)%>">
                        <img class="classic-display" src="../project/images/classic/icon_person_r.gif" alt="<gsmsg:write key="cmn.admin" />">
                        <img class="original-display" src="../project/images/original/icon_person_red.png" alt="<gsmsg:write key="cmn.admin" />">
                      </logic:equal>
                    </td>
                  <td class="w100 txt_l classic-display">
                      <logic:equal name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <span class="cl_linkDef <%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>">
                          <a href="#!" onClick="return openUserInfoWindow('<bean:write name="userMdl" property="userSid" />');">
                            <bean:write name="userMdl" property="sei" />&nbsp;<bean:write name="userMdl" property="mei" />
                          </a>
                        </span>
                      </logic:equal>
                      <logic:notEqual name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <span class="cl_linkDef">
                          <del>
                            <bean:write name="userMdl" property="sei" />&nbsp;<bean:write name="userMdl" property="mei" />
                          </del>
                        </span>
                      </logic:notEqual>
                    </td>
                    <td class="w100 txt_l border_right_none original-display">
                      <logic:equal name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <span class="cl_linkDef <%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>">
                          <a href="#!" onClick="return openUserInfoWindow('<bean:write name="userMdl" property="userSid" />');">
                            <bean:write name="userMdl" property="sei" />&nbsp;<bean:write name="userMdl" property="mei" />
                          </a>
                        </span>
                      </logic:equal>
                      <logic:notEqual name="userMdl" property="status" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <span class="cl_linkDef">
                          <del>
                            <bean:write name="userMdl" property="sei" />&nbsp;<bean:write name="userMdl" property="mei" />
                          </del>
                        </span>
                      </logic:notEqual>
                    </td>
                   <td class="original-display no_w">
                    <logic:equal name="prj030Form" property="useZaiseki" value="true">
                    <logic:equal name="userMdl" property="zaisekiKbn" value="1">
                      <span class="original-display zsk_listStatus-zaiseki">
                      <gsmsg:write key="cmn.zaiseki" />
                      </span>
                    </logic:equal>
                    <logic:equal name="userMdl" property="zaisekiKbn" value="2">
                      <span class="original-display zsk_listStatus-huzai">
                      <gsmsg:write key="cmn.absence" />
                      </span>
                    </logic:equal>
                    <logic:equal name="userMdl" property="zaisekiKbn" value="0">
                      <span class="original-display zsk_listStatus-sonota">
                      <gsmsg:write key="cmn.other" />
                      </span>
                    </logic:equal>
                    </logic:equal>
                    </td>
                  </tr>
                </logic:iterate>

                <logic:notEmpty name="prj030Form" property="outMemberList" scope="request">
                  <bean:size id="outMemCnt" name="prj030Form" property="outMemberList" scope="request" />
                  <bean:define id="outMemberCount" value="<%=String.valueOf(outMemCnt)%>" />

                  <tr>
                    <th class="txt_l bgC_header1" colspan="4">
                      <span>
                        <span class="table_title-color fw_n fs_14"><gsmsg:write key="project.28" /><%=outMemberCount%><gsmsg:write key="cmn.name3" /></span>
                      </span>
                    </th>
                  </tr>

                  <logic:iterate id="outMemMdl" name="prj030Form" property="outMemberList" scope="request" indexId="idx">
                    <tr class="bor_b1">
                      <td class="border_right_none"></td>
                      <td class="txt_c border_right_none">
                        <img class="classic-display" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="cmn.member" />">
                        <img class="original-display" src="../common/images/original/icon_user.png" alt="<gsmsg:write key="cmn.admin" />">
                      </td>
                      <td class="w100 txt_l" colspan="2">
                        <span class="cl_linkDef">
                          <a href="#!" onClick="openAddressInfoWindow('<bean:write name="outMemMdl" property="adrSid" />');">
                            <bean:write name="outMemMdl" property="adrName" />
                          </a>
                        </span>
                      </td>
                    </tr>

                  </logic:iterate>
                </logic:notEmpty>

                <logic:equal name="prj030Form" property="prjEdit" value="true">
                  <logic:greaterThan name="memberCount" value="0">
                    <tr>
                      <td colspan="4" class="txt_r bgC_gray fw_b">
                        <a href="#!" onclick="return memberEdit('<%=memberEdit%>', '<%=dspId%>');">
                          <span>
                            [<gsmsg:write key="project.29" />]
                          </span>
                        </a>
                      </td>
                    </tr>
                  </logic:greaterThan>
                </logic:equal>

              </logic:notEmpty>
          </table>
          <br>
        </logic:equal>


        <table class="table-top m0">
          <tr>
            <th class="no_w txt_l" nowrap>
              <gsmsg:write key="project.30" />
            </th>
          </tr>
          <tr>
            <td>
              <span>
                <logic:notEmpty name="projectMdl" property="strStartDate">
                <span class="ml10"><gsmsg:write key="cmn.period" />:</span>
                </logic:notEmpty>
                <logic:empty name="projectMdl" property="strStartDate">
                  <logic:notEmpty name="projectMdl" property="strEndDate">
                <span class="ml10"><gsmsg:write key="cmn.period" />:</span>
                </logic:notEmpty>
                </logic:empty>

                <bean:write name="projectMdl" property="strStartDate" />

                <%
                  String kikan = "";
                %>
                <logic:notEmpty name="projectMdl" property="strStartDate">
                  <%
                    kikan = "～";
                  %>
                </logic:notEmpty>
                <logic:notEmpty name="projectMdl" property="strEndDate">
                  <%
                    kikan = "～";
                  %>
                </logic:notEmpty>
                <%=kikan%>
                <bean:write name="projectMdl" property="strEndDate" />

                <br>
                <logic:notEmpty name="projectMdl" property="strYosan">
                <span class="ml10"><gsmsg:write key="project.10" />:</span>
                <bean:write name="projectMdl" property="strYosan" />
                </logic:notEmpty>
              </span>
            </td>
          </tr>
        </table>
        <br>

        <table class="table-top">
          <tr>
            <th class="no_w txt_l">
              <gsmsg:write key="project.21" />
            </th>
          </tr>
          <logic:notEmpty name="projectMdl" property="mokuhyou">
            <tr>
              <td>
                <bean:write name="projectMdl" property="mokuhyou" filter="false" />
              </th>
            </tr>
          </logic:notEmpty>
        </table>
        <br>

        <table class="table-top">
          <tr>
            <th class="txt_l" nowrap>
              <gsmsg:write key="cmn.content" />
            </th>
          </tr>
          <logic:notEmpty name="projectMdl" property="naiyou">
            <tr>
              <td>
                <bean:write name="projectMdl" property="naiyou" filter="false" />
              </th>
            </tr>
          </logic:notEmpty>
        </table>

      </div>
      <div class="main">

        <!-- エラーメッセージ -->
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>

        <!-- TODO -->
        <div class="p5 bgC_header1 bor1 w100 table_title-color fw_n">
          <div class="component_bothEnd">
          <div class="pr5">
           <span class="fw_b">TODO</span>
          </div>

          <div>

          <div class="display_inline-block">
            <span> <gsmsg:write key="project.100" />
            </span>
            <logic:notEmpty name="prj030Form" property="targetDateLabel">
              <html:select property="selectingDate" onchange="buttonPush('<%=init%>');">
                <html:optionsCollection name="prj030Form" property="targetDateLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </div>

          <div class="display_inline-block">
            <span> <gsmsg:write key="cmn.status" />
            </span>
            <logic:notEmpty name="prj030Form" property="targetStatusLabel">
              <html:select styleClass="wp110" property="selectingStatus" onchange="buttonPush('<%=init%>');">
                <html:optionsCollection name="prj030Form" property="targetStatusLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </div>
          <div class="display_inline-block">
            <span> <gsmsg:write key="cmn.label" />
            </span>
            <logic:notEmpty name="prj030Form" property="targetCategoryLabel">
              <html:select property="selectingCategory" onchange="buttonPush('<%=init%>');">
                <html:optionsCollection name="prj030Form" property="targetCategoryLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </div>

          <div class="display_inline-block">
            <span> <gsmsg:write key="cmn.member" />
            </span>
            <logic:notEmpty name="prj030Form" property="targetMemberLabel">
              <html:select property="selectingMember" onchange="buttonPush('<%=init%>');" styleClass="wp200">
                <logic:iterate id="user" name="prj030Form" property="targetMemberLabel" type="UsrLabelValueBean">
                  <html:option value="<%=user.getValue()%>" styleClass="<%=user.getCSSClassNameOption()%>">
                    <bean:write name="user" property="label" />
                  </html:option>
                </logic:iterate>
              </html:select>
            </logic:notEmpty>
            </div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.advanced.search" />" onclick="buttonPush('<%=todoSearch%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <gsmsg:write key="cmn.advanced.search" />
          </button>
            </div>
          </div>
        </div>

        <div class="mt10 flo_r verAlignMid no_w">
          <logic:equal name="prj030Form" property="prjTodoEdit" value="true">
            <logic:notEmpty name="prj030Form" property="projectList" scope="request">
              <span class="cursor_p" onClick="return doOpenDialog('<bean:write name="prj030Form" property="prj030prjSid" />');">
                <span>▼<gsmsg:write key="project.date.change" />
                </span>
              </span>
              <span class="ml5">
                <gsmsg:write key="project.20" />:
              </span>
              <html:select property="prj030selectEditStatus" styleClass="ml5 wp110">
                <html:optionsCollection name="prj030Form" property="editStatusLabel" value="value" label="label" />
              </html:select>
              <button type="button" value="<gsmsg:write key="cmn.change" />" class="baseBtn ml5" onclick="buttonPush('<%=editStatus%>');">
                <gsmsg:write key="cmn.change" />
              </button>
            </logic:notEmpty>
          </logic:equal>
          <!-- logic:equal name="prj030Form" property="prjTodoEdit" value="true" -->

          <button type="button" class="baseBtn ml5" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('<%=todoImport%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <gsmsg:write key="cmn.import" />
          </button>
          <!-- /logic:equal -->
          <!-- logic:equal name="prj030Form" property="prjTodoEdit" value="true" -->
          <button type="button" class="baseBtn ml5" value="<gsmsg:write key="cmn.new.registration" />" onclick="buttonPushAdd('<%=todoAdd%>', <%=mode_add%>);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
            <gsmsg:write key="cmn.new.registration" />

          </button>
          <!-- /logic:equal -->

          <logic:equal name="prj030Form" property="prjTodoEdit" value="true">
            <logic:notEmpty name="prj030Form" property="projectList" scope="request">
              <button type="button" name="btn_tododel" class="baseBtn ml5" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('<%=cmdDelTo%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:notEmpty>
          </logic:equal>

          <bean:size id="count1" name="prj030Form" property="pageLabel" scope="request" />
          <logic:greaterThan name="count1" value="1">
            <div class="paging">
              <button type="button" class="webIconBtn" onClick="buttonPush('<%=prev%>');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />"> <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo" property="prj030page1" onchange="changePage(1);">
                <html:optionsCollection name="prj030Form" property="pageLabel" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="buttonPush('<%=next%>');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />"> <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:greaterThan>

        </div>

        <table class="table-top">
          <tr>
            <logic:equal name="prj030Form" property="prjTodoEdit" value="true">
              <th class="txt_c w5 js_tableTopCheck js_tableTopCheck-header cursor_p">
                <input type="checkbox" name="prj030AllCheck" value="1" onClick="chgCheckAll('prj030AllCheck', 'prj030selectTodo');chgCheckAllChange('prj030AllCheck', 'prj030selectTodo');">
              </th>
            </logic:equal>

            <%
                for (int i = 0; i < sortKeyList01.length; i++) {
                        if (i != 1) {

                          String cursorClass = "cursor_p";
                          if (sortKeyList01[i] == -1) { cursorClass = ""; }
                    %>
            <th class="w<%=title_width01[i]%> no_w <%= cursorClass %>">
              <%
                  } else {
               %>
               <span class="mr5 ml5">/</span>
              <%
                }
              if (iSortKbn == sortKeyList01[i]) {
                if (iOrderKey == order_desc) { %>
              <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
                <span><%=titleList01[i]%><span class="classic-display">▼</span>
                  <span class="original-display">
                    <i class="icon-sort_down"></i>
                  </span>
                </span>
                </span>
              </a>
              <% } else { %>
              <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_desc%>);">
                <span><%=titleList01[i]%><span class="classic-display">▲</span>
                  <span class="original-display">
                    <i class="icon-sort_up"></i>
                  </span>
                </span>
                </span>
              </a>
              <% }
              } else { %>
              <% if (sortKeyList01[i] == -1) { %>
              <%-- 担当者名 --%>
              <span><%=titleList01[i]%></span>
              <% } else { %>
              <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
                <span><%=titleList01[i]%></span>
              </a>
              <% } %>
              <% } %>

              <% if (i >= 1) { %>
            </th>
            <% }
              } %>
          </tr>

          <logic:notEmpty name="prj030Form" property="projectList" scope="request">
            <logic:iterate id="prjMdl" name="prj030Form" property="projectList" scope="request" indexId="idx">

              <tr class="checkSelected n<bean:write name="prjMdl" property="todoSid" />" id="<bean:write name="prjMdl" property="todoSid" />">
                <logic:equal name="prj030Form" property="prjTodoEdit" value="true">
                  <td class="txt_c js_tableTopCheck judgCheck cursor_p">
                    <html:multibox name="prj030Form" property="prj030selectTodo">
                      <bean:write name="prjMdl" property="todoSid" />
                    </html:multibox>

                  </td>
                </logic:equal>

                <td>
                  <table class="w100">
                    <tr>
                      <td class="border_none">
                        <logic:equal name="prjMdl" property="keikoku" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEIKOKU_ARI)%>">
                          <logic:notEqual name="prjMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
                            <img class="classic-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                            <img class="original-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                          </logic:notEqual>
                        </logic:equal>
                        <a href="#!" onClick="return viewTodo('<%=todoRef%>', '<bean:write name="prjMdl" property="todoSid" />');">
                          <span>
                            <bean:write name="prjMdl" property="todoTitle" filter="false" />
                          </span>
                        </a>
                        <logic:equal name="prjMdl" property="prjTodoEdit" value="true">
                          <a href="#!" onClick="return editTodo('<%=todoEdit%>', '<%=mode_edit%>', '<bean:write name="prjMdl" property="projectSid" />', '<bean:write name="prjMdl" property="todoSid" />');">
                            <img class="classic-display" src="../common/images/classic/icon_edit_3.png" alt="<gsmsg:write key="project.56" />">
                            <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="project.56" />">
                          </a>
                        </logic:equal>
                      </td>
                      <td class="txt_r txt_m no_w border_none">
                        <logic:notEqual name="prjMdl" property="prjTodoCommentCnt" value="0">
                          <a href="#!" onClick="return viewTodoComment('<%=todoRef%>', '<bean:write name="prjMdl" property="todoSid" />');">
                            <img class="classic-display" src="../common/images/classic/icon_comment.png" alt="<gsmsg:write key="cmn.comment" />">
                            <img class="original-display" src="../common/images/original/icon_comment.png" alt="<gsmsg:write key="cmn.comment" />">
                            <span>
                              <bean:write name="prjMdl" property="prjTodoCommentCnt" />
                            </span>
                          </a>
                        </logic:notEqual>
                      </td>
                    </tr>
                    <tr>
                      <td class="border_none" colspan="2">
                        <span class="mr10">
                          <bean:write name="prjMdl" property="strKanriNo" />
                        </span>
                        <logic:notEqual name="prjMdl" property="categoryValue" value="-1">
                          <span class="baseLabel">
                            <bean:write name="prjMdl" property="category" />
                          </span>
                        </logic:notEqual>
                      </td>
                    </tr>
                  </table>
              </td>

              <td class="txt_l">
                <logic:notEmpty name="prjMdl" property="todoTantoList">
                  <logic:iterate id="tantoMdl" name="prjMdl" property="todoTantoList" indexId="tantoIdx" type="TodoTantoModel">
                    <%
                      if (tantoIdx.intValue() > 0) {
                     %><br>
                            <%
                              }
                            %>

                    <logic:equal name="tantoMdl" property="delUser" value="false">
                      <span class="<%=UserUtil.getCSSClassNameNormal(tantoMdl.getUsrUkoFlg())%>">
                        <bean:write name="tantoMdl" property="userName" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="tantoMdl" property="delUser" value="false">
                      <span>
                        <del>
                          <bean:write name="tantoMdl" property="userName" />
                        </del>
                      </span>
                    </logic:notEqual>

                  </logic:iterate>
                </logic:notEmpty>
              </td>

              <%-- <td class="txt_c"><span><bean:write name="prjMdl" property="strJuyo" /></span></td> --%>

              <td class="txt_c no_w">
                <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_LOW)%>">
                  <img class="classic-display" src="../common/images/classic/icon_star_blue.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <span class="original-display">
                    <i class="icon-star  importance-blue"></i> <i class="icon-star_line"></i> <i class="icon-star_line"></i>
                  </span>
                </logic:equal>
                <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE)%>">
                  <img class="classic-display" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.59" />">
                  <span class="original-display">
                    <i class="icon-star  importance-yellow"></i> <i class="icon-star  importance-yellow"></i> <i class="icon-star_line"></i>
                  </span>
                </logic:equal>
                <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH)%>">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <img class="classic-display" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <span class="original-display">
                    <i class="icon-star  importance-red"></i> <i class="icon-star  importance-red"></i> <i class="icon-star  importance-red"></i>
                  </span>
                </logic:equal>
              </td>

              <td class="txt_c no_w">
                <span>
                  <bean:write name="prjMdl" property="rate" />
                  %<br>(
                  <bean:write name="prjMdl" property="statusName" />
                  )
                </span>
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
        </td>
        </tr>
        </table>

        <div>

         <bean:size id="count1" name="prj030Form" property="pageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('<%=prev%>');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="prj030page2" onchange="changePage(2);">
              <html:optionsCollection name="prj030Form" property="pageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('<%=next%>');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
        </div>

      </div>
    </div>


    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

    <div id="dialog-form" title="<gsmsg:write key="project.date.change" />" class="display_n hp100">
      <fieldset>

        <div class="flo_l">
          <input type="radio" name="plusMinus" id="radioPl" value="plus" checked>
          <label for="radioPl">
            <b>＋</b>
          </label>
          <br> <input type="radio" name="plusMinus" id="radioMi" value="minus">
          <label for="radioMi">
            <b>－</b>
          </label>
        </div>
        <div class="flo_r w80 display_inline-block display_inline txt_l mt10">
          <b><gsmsg:write key="project.move.days" />:</b> <select name="prj010SelMonth" id="selMonth">
            <option value="0" selected="selected"><gsmsg:write key="cmn.months" arg0="0" /></option>
            <option value="1"><gsmsg:write key="cmn.months" arg0="1" /></option>
            <option value="2"><gsmsg:write key="cmn.months" arg0="2" /></option>
            <option value="3"><gsmsg:write key="cmn.months" arg0="3" /></option>
            <option value="4"><gsmsg:write key="cmn.months" arg0="4" /></option>
            <option value="5"><gsmsg:write key="cmn.months" arg0="5" /></option>
            <option value="6"><gsmsg:write key="cmn.months" arg0="6" /></option>
            <option value="7"><gsmsg:write key="cmn.months" arg0="7" /></option>
            <option value="8"><gsmsg:write key="cmn.months" arg0="8" /></option>
            <option value="9"><gsmsg:write key="cmn.months" arg0="9" /></option>
            <option value="10"><gsmsg:write key="cmn.months" arg0="10" /></option>
            <option value="11"><gsmsg:write key="cmn.months" arg0="11" /></option>
            <option value="12"><gsmsg:write key="cmn.months" arg0="12" /></option>
          </select> <select name="prj010SelDay" id="selDay">
            <option value="1">0
              <gsmsg:write key="cmn.day" /></option>
            <option value="1" selected="selected">1
              <gsmsg:write key="cmn.day" /></option>
            <option value="2">2
              <gsmsg:write key="cmn.day" /></option>
            <option value="3">3
              <gsmsg:write key="cmn.day" /></option>
            <option value="4">4
              <gsmsg:write key="cmn.day" /></option>
            <option value="5">5
              <gsmsg:write key="cmn.day" /></option>
            <option value="6">6
              <gsmsg:write key="cmn.day" /></option>
            <option value="7">7
              <gsmsg:write key="cmn.day" /></option>
            <option value="8">8
              <gsmsg:write key="cmn.day" /></option>
            <option value="9">9
              <gsmsg:write key="cmn.day" /></option>
            <option value="10">10
              <gsmsg:write key="cmn.day" /></option>
            <option value="11">11
              <gsmsg:write key="cmn.day" /></option>
            <option value="12">12
              <gsmsg:write key="cmn.day" /></option>
            <option value="13">13
              <gsmsg:write key="cmn.day" /></option>
            <option value="14">14
              <gsmsg:write key="cmn.day" /></option>
            <option value="15">15
              <gsmsg:write key="cmn.day" /></option>
            <option value="16">16
              <gsmsg:write key="cmn.day" /></option>
            <option value="17">17
              <gsmsg:write key="cmn.day" /></option>
            <option value="18">18
              <gsmsg:write key="cmn.day" /></option>
            <option value="19">19
              <gsmsg:write key="cmn.day" /></option>
            <option value="20">20
              <gsmsg:write key="cmn.day" /></option>
            <option value="21">21
              <gsmsg:write key="cmn.day" /></option>
            <option value="22">22
              <gsmsg:write key="cmn.day" /></option>
            <option value="23">23
              <gsmsg:write key="cmn.day" /></option>
            <option value="24">24
              <gsmsg:write key="cmn.day" /></option>
            <option value="25">25
              <gsmsg:write key="cmn.day" /></option>
            <option value="26">26
              <gsmsg:write key="cmn.day" /></option>
            <option value="27">27
              <gsmsg:write key="cmn.day" /></option>
            <option value="28">28
              <gsmsg:write key="cmn.day" /></option>
            <option value="29">29
              <gsmsg:write key="cmn.day" /></option>
            <option value="30">30
              <gsmsg:write key="cmn.day" /></option>
            <option value="31">31
              <gsmsg:write key="cmn.day" /></option>
          </select>

        </div>

        <br> <br clear="all">
        <hr>
        <input type="checkbox" name="holSetCheck" value="<gsmsg:write key="main.holiday.setting" />" id="holSet" checked>
        <label for="holSet">
          <b><gsmsg:write key="project.lef.holiday" /></b>
        </label>
      </fieldset>
    </div>

   <div id="dialog-error" title="<gsmsg:write key="cmn.warning" />" class="display_n">
     <table class="w100 h100">
        <tr>
          <td class="w15">
            <img class="classic-display" src="../common/images/classic/icon_warn_2.gif">
            <img class="original-display" src="../common/images/original/icon_warn_63.png">
          </td>
          <td class="w85">
            <span class="fs_13"><gsmsg:write key="cmn.select.3" arg0="TODO" /></span>
          </td>
        </tr>
      </table>
</div>
  </html:form>

</body>
</html:html>