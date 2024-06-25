<%@page import="jp.groupsession.v2.prj.model.StatusHistoryModel"%>
<%@page import="jp.groupsession.v2.prj.model.TodocommentModel"%>
<%@page import="jp.groupsession.v2.prj.model.UserModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
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
  String editTodoClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_EDIT_CLICK;
  String deleteClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_DEL_CLICK;
  String backClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_BACK_CLICK;
  String commentClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_COMMENT_CLICK;
  String commentDelClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_COMMENT_DELETE_CLICK;
  String updateClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_STATUS_UPDATE_CLICK;
  String statusDelClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_STATUS_DELETE_CLICK;
  String fileDl = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_FILE_DL;
  String zissekiClick = jp.groupsession.v2.prj.prj060.Prj060Action.CMD_ZISSEKI_CLICK;

  String keikokuAri = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEIKOKU_ARI);

  String prj060 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ060;
  String mode_edit = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_EDIT;

  String jkbnToroku = String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU);
%>

<%-- 定数値 --%>

<%
  String maxLengthReason = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MAX_LENGTH_STATUS_REASON);
  String maxLengthToDoCmt = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MAX_LENGTH_TODO_CMT);
%>
<bean:define id="prjMdl" name="prj060Form" property="projectItem" />
<bean:define id="prjMdl" name="prj060Form" property="projectItem" />
<bean:define id="prjSid" name="prj060Form" property="prj060prjSid" />
<bean:define id="todoSid" name="prj060Form" property="prj060todoSid" />

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj060.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<logic:equal name="prj060Form" property="todoEdit" value="true">
  <body onload="rateChange(-1);moveComment();showLengthId($('#inputstr')[0], <%=maxLengthToDoCmt%>, 'inputlength');showLengthId($('#inputstr2')[0], <%=maxLengthReason%>, 'inputlength2');">
</logic:equal>
<logic:notEqual name="prj060Form" property="todoEdit" value="true">
  <body onload="moveComment();">
</logic:notEqual>

<html:form action="/project/prj060">

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

  <html:hidden property="prj060SelectYearFr" />
  <html:hidden property="prj060SelectMonthFr" />
  <html:hidden property="prj060SelectDayFr" />
  <html:hidden property="prj060SelectYearTo" />
  <html:hidden property="prj060SelectMonthTo" />
  <html:hidden property="prj060SelectDayTo" />

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

  <logic:notEmpty name="prj060Form" property="prj040scMemberSid" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj040scMemberSid" scope="request">
      <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj040svScMemberSid" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj040svScMemberSid" scope="request">
      <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <html:hidden property="prj030scrId" />
  <html:hidden property="prj030prjSid" />
  <html:hidden property="prj030sort" />
  <html:hidden property="prj030order" />
  <html:hidden property="prj030page1" />
  <html:hidden property="prj030page2" />
  <html:hidden property="prj030Init" />
  <html:hidden property="prjmvComment" />
  <html:hidden property="selectingDate" />
  <html:hidden property="selectingStatus" />
  <html:hidden property="selectingCategory" />
  <html:hidden property="selectingMember" />

  <logic:notEmpty name="prj060Form" property="prj030sendMember" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj030sendMember" scope="request">
      <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <html:hidden property="prj070scrId" />
  <html:hidden property="prj070scPrjSid" />
  <html:hidden property="prj070scCategorySid" />
  <html:hidden property="prj070scStatusFrom" />
  <html:hidden property="prj070scStatusTo" />
  <html:hidden property="prj070scKaisiYoteiYear" />
  <html:hidden property="prj070scKaisiYoteiMonth" />
  <html:hidden property="prj070scKaisiYoteiDay" />
  <html:hidden property="prj070scKigenYear" />
  <html:hidden property="prj070scKigenMonth" />
  <html:hidden property="prj070scKigenDay" />
  <html:hidden property="prj070scKaisiJissekiYear" />
  <html:hidden property="prj070scKaisiJissekiMonth" />
  <html:hidden property="prj070scKaisiJissekiDay" />
  <html:hidden property="prj070scSyuryoJissekiYear" />
  <html:hidden property="prj070scSyuryoJissekiMonth" />
  <html:hidden property="prj070scSyuryoJissekiDay" />
  <html:hidden property="prj070scTitle" />
  <html:hidden property="prj070KeyWordkbn" />
  <html:hidden property="prj070svScPrjSid" />
  <html:hidden property="prj070svScCategorySid" />
  <html:hidden property="prj070svScStatusFrom" />
  <html:hidden property="prj070svScStatusTo" />
  <html:hidden property="prj070svScKaisiYoteiYear" />
  <html:hidden property="prj070svScKaisiYoteiMonth" />
  <html:hidden property="prj070svScKaisiYoteiDay" />
  <html:hidden property="prj070svScKigenYear" />
  <html:hidden property="prj070svScKigenMonth" />
  <html:hidden property="prj070svScKigenDay" />
  <html:hidden property="prj070svScKaisiJissekiYear" />
  <html:hidden property="prj070svScKaisiJissekiMonth" />
  <html:hidden property="prj070svScKaisiJissekiDay" />
  <html:hidden property="prj070svScSyuryoJissekiYear" />
  <html:hidden property="prj070svScSyuryoJissekiMonth" />
  <html:hidden property="prj070svScSyuryoJissekiDay" />
  <html:hidden property="prj070svScTitle" />
  <html:hidden property="prj070SvKeyWordkbn" />
  <html:hidden property="prj070page1" />
  <html:hidden property="prj070page2" />
  <html:hidden property="prj070sort" />
  <html:hidden property="prj070order" />
  <html:hidden property="prj070searchFlg" />
  <html:hidden property="prj070InitFlg" />
  <gsmsg:define id="textNo" msgkey="cmn.no3" />
  <gsmsg:define id="textAllMem" msgkey="project130" />
  <gsmsg:define id="textCmnStaff" msgkey="cmn.staff" />
  <gsmsg:define id="textProjectAdm" msgkey="project.src.32" />
  <gsmsg:define id="textProjectLeaderAndTanto" msgkey="project.src.64" />
  <logic:notEmpty name="prj060Form" property="prj070scTantou" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070scTantou" scope="request">
      <input type="hidden" name="prj070scTantou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070scTourokusya" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070scTourokusya" scope="request">
      <input type="hidden" name="prj070scTourokusya" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070svScTantou" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070svScTantou" scope="request">
      <input type="hidden" name="prj070svScTantou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070svScTourokusya" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070svScTourokusya" scope="request">
      <input type="hidden" name="prj070svScTourokusya" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070svScJuuyou" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070svScJuuyou" scope="request">
      <input type="hidden" name="prj070svScJuuyou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070scJuuyou" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070scJuuyou" scope="request">
      <input type="hidden" name="prj070scJuuyou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070SearchTarget" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070SearchTarget" scope="request">
      <input type="hidden" name="prj070SearchTarget" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj060Form" property="prj070SvSearchTarget" scope="request">
    <logic:iterate id="item" name="prj060Form" property="prj070SvSearchTarget" scope="request">
      <input type="hidden" name="prj070SvSearchTarget" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <html:hidden property="prj060TodoTitle" />
  <html:hidden property="prj060scrId" />
  <html:hidden property="prj060prjSid" />
  <html:hidden property="prj060todoSid" />
  <html:hidden property="prj060schUrl" />

  <html:hidden property="prj050scrId" value="<%=prj060%>" />
  <html:hidden property="prj050cmdMode" value="<%=mode_edit%>" />
  <html:hidden property="prj050prjSid" value="<%=String.valueOf(prjSid)%>" />
  <html:hidden property="prj050todoSid" value="<%=String.valueOf(todoSid)%>" />

  <html:hidden property="commentSid" />
  <html:hidden property="historySid" />
  <html:hidden property="binSid" />
  <html:hidden property="selectDir" />


  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

  <div class="pageTitle">
    <ul>
      <li>
        <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="project.29" />">
        <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="project.29" />">
      </li>
      <li>
        <gsmsg:write key="cmn.project" />
      </li>
      <li class="pageTitle_subFont">
        <gsmsg:write key="project.prj060.1" />
      </li>
      <li>
        <div>
          <logic:equal name="prj060Form" property="todoEdit" value="true">
            <button type="button" value="<gsmsg:write key="cmn.fixed" />" class="baseBtn" onclick="setDateParam();buttonPush('<%=editTodoClick%>');">
              <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
              <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
              <gsmsg:write key="cmn.fixed" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('<%=deleteClick%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('<%=backClick%>')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </li>
    </ul>
  </div>
  <div class="wrapper">

  <div class="sub_title fs_18 txt_l fw_b">
  <logic:equal name="prjMdl" property="prjBinSid" value="0">
    <img class="classic-display" src="../project/images/classic/icon_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" >
    <img class="original-display" src="../project/images/original/plugin_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
  </logic:equal>
  <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
    <img src="../project/prj060.do?CMD=getImageFile&prj060prjSid=<bean:write name="prj060Form" property="prj060prjSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />&prj060todoSid=<bean:write name="prj060Form" property="prj060todoSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="prj_img_ico wp30hp30">
  </logic:notEqual>
  <bean:write name="prjMdl" property="projectName" />
  </div>

    <div class="fs_16 txt_l mt5">
      <logic:equal name="prjMdl" property="keikoku" value="<%=keikokuAri%>">
        <logic:equal name="prjMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
          <img class="classic-display img-18" src="../project/images/classic/icon_todo_30.png" alt="TODO">
          <img class="original-display" src="../project/images/original/icon_todo.png" alt="TODO">
        </logic:equal>
        <logic:notEqual name="prjMdl" property="rate" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.RATE_MAX)%>">
          <img class="classic-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
          <img class="original-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
        </logic:notEqual>
      </logic:equal>
      <logic:notEqual name="prjMdl" property="keikoku" value="<%=keikokuAri%>">
        <img class="classic-display img-18" src="../project/images/classic/icon_todo_30.png" alt="TODO">
        <img class="original-display" src="../project/images/original/icon_todo.png" alt="TODO">
      </logic:notEqual>
      <bean:write name="prjMdl" property="todoTitle" filter="false" />
    </div>


    <logic:messagesPresent message="false">
    <div class="mt15">
      <html:errors />
    </div>
    </logic:messagesPresent>

    <div class="wrapper_2column">
      <div class="w65">

        <table class="table-left" >

          <tr>
            <th class="w25 no_w">
              <span>
                <gsmsg:write key="project.prj050.5" />
              </span>
            </th>
            <td class="txt_l w35">
              <span>
                <bean:write name="prjMdl" property="strKanriNo" />
              </span>
            </td>
            <th class="w10 no_w">
              <span>
                <gsmsg:write key="cmn.registant" />
              </span>
            </th>

            <td class="w30 txt_l">
              <logic:equal name="prj060Form" property="addUserStatus" value="<%=jkbnToroku%>">
                <bean:define id="ukoFlg" name="prj060Form" property="addUsrUkoFlg" type="Integer" />
                <span class="<%=UserUtil.getCSSClassNameNormal(ukoFlg)%>">
                  <bean:write name="prj060Form" property="addUserName" />
                </span>
              </logic:equal>
              <logic:notEqual name="prj060Form" property="addUserStatus" value="<%=jkbnToroku%>">
                <del>
                  <span>
                    <bean:write name="prj060Form" property="addUserName" />
                  </span>
                </del>
              </logic:notEqual>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.prj060.2" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="prjMdl" property="strStartDate" />

                <%
                   String kikan = "";
                %>
                <logic:notEmpty name="prjMdl" property="strStartDate">
                  <%
                       kikan = "～";
                   %>
                </logic:notEmpty>
                <logic:notEmpty name="prjMdl" property="strEndDate">
                  <%
                   kikan = "～";
                   %>
                </logic:notEmpty>
                <%=kikan%>

                <bean:write name="prjMdl" property="strEndDate" />
              </span>
            </td>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.prj060.3" />
              </span>
            </th>
            <td class="no_w">
              <span>
                <bean:write name="prjMdl" property="strYoteiKosu" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.prj060.4" />
              </span>
            </th>
            <td class="txt_l" class="no_w">
              <span>

                <bean:write name="prjMdl" property="strStartJissekiDate" />

                <%
                kikan = "";
                %>
                <logic:notEmpty name="prjMdl" property="strStartJissekiDate">
                  <%
                    kikan = "～";
                  %>
                </logic:notEmpty>
                <logic:notEmpty name="prjMdl" property="strEndJissekiDate">
                  <%
                    kikan = "～";
                  %>
                </logic:notEmpty>
                <%=kikan%>

                <bean:write name="prjMdl" property="strEndJissekiDate" />

              </span>
            </td>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.prj060.5" />
              </span>
            </th>
            <td valign="top" class="no_w">
              <span>
                <bean:write name="prjMdl" property="strJissekiKosu" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.label" />
              </span>
            </th>
            <td class="no_w">
              <span>
                <bean:write name="prjMdl" property="category" />
              </span>
            </td>
            <th class="no_w">
              <span>
                <gsmsg:write key="project.prj050.4" />
              </span>
            </th>

            <%-- <td class="no_w"><span><bean:write name="prjMdl" property="strJuyo" /></span></td> --%>
            <td class="no_w">
              <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_LOW)%>">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png"  alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.58" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.58" />">
                <span class="original-display">
                  <i class="icon-star  importance-blue"></i> <i class="icon-star_line"></i> <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE)%>">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png"  alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png"  alt="<gsmsg:write key="project.59" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.59" />">
                <span class="original-display">
                  <i class="icon-star  importance-yellow"></i> <i class="icon-star  importance-yellow"></i> <i class="icon-star_line"></i>
                </span>
              </logic:equal>
              <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH)%>">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
                <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
                <span class="original-display">
                  <i class="icon-star  importance-red"></i> <i class="icon-star  importance-red"></i> <i class="icon-star  importance-red"></i>
                </span>
              </logic:equal>
            </td>
          </tr>

          <logic:equal name="prjMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
            <tr>
              <th class="no_w">
                <span>
                  <gsmsg:write key="project.113" />
                </span>
              </th>
              <td colspan="3">
                <logic:notEmpty name="prj060Form" property="userList" scope="request">
                  <logic:iterate id="memMdl" name="prj060Form" property="userList" scope="request" type="UserModel">

                    <logic:equal name="memMdl" property="status" value="<%=jkbnToroku%>">
                      <span class="<%=UserUtil.getCSSClassNameNormal(memMdl.getUsrUkoFlg())%>">
                        <bean:write name="memMdl" property="sei" />
                        &nbsp;
                        <bean:write name="memMdl" property="mei" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="memMdl" property="status" value="<%=jkbnToroku%>">
                      <del>
                        <span>
                          <bean:write name="memMdl" property="sei" />
                          &nbsp;
                          <bean:write name="memMdl" property="mei" />
                        </span>
                      </del>
                    </logic:notEqual>
                    &nbsp;
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.content" />
              </span>
            </th>
            <td class="txt_l" colspan="3">
              <span>
                <bean:write name="prjMdl" property="naiyou" filter="false" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.attach.file" />
              </span>
            </th>
            <td class="no_w" colspan="3">
              <logic:notEmpty name="prj060Form" property="binfList" scope="request">
                <logic:iterate id="fileMdl" name="prj060Form" property="binfList" scope="request">
                  <a href="#!" onClick="return fileDl('<%=fileDl%>', '<bean:write name="fileMdl" property="binSid" />');">
                    <span class="textLink">
                      <bean:write name="fileMdl" property="binFileName" />
                      <bean:write name="fileMdl" property="binFileSizeDsp" />
                    </span>
                  </a>
                  <br>
                </logic:iterate>
              </logic:notEmpty>
            </td>
          </tr>
        </table>

        <div class="txt_l mb10">
          <span>URL:</span>
          <input type="text" value="<bean:write name="prj060Form" property="prj060TodoUrl" />" class="wp550" readOnly="true" />
        </div>

        <table class="table-top">
          <tr>
            <th class="txt_l">
              <span id="prj060CmtPnt">
                <img class="classic-display" src="../project/images/classic/icon_todo_ref_comment.gif">
                <img class="original-display" src="../common/images/original/icon_comment.png">
                <gsmsg:write key="cmn.comment" />
              </span>
            </th>
          </tr>

          <tr>
            <td class="border_none">
              <logic:notEmpty name="prj060Form" property="todoComList" scope="request">
                <logic:iterate id="comMdl" name="prj060Form" property="todoComList" scope="request" type="TodocommentModel">
                  <div class="w100 bgC_header2 component_bothEnd p5">
                    <div>
                      <img class="classic-display" src="../common/images/classic/icon_comment.png">
                      <img class="original-display" src="../common/images/original/icon_comment.png">
                      &nbsp;
                      <bean:write name="comMdl" property="strPcmAdate" />
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                      <logic:equal name="comMdl" property="status" value="<%=jkbnToroku%>">
                        <span class="<%=UserUtil.getCSSClassNameNormal(comMdl.getUsrUkoFlg())%>">
                          <bean:write name="comMdl" property="sei" />
                          &nbsp;
                          <bean:write name="comMdl" property="mei" />
                        </span>
                      </logic:equal>
                      <logic:notEqual name="comMdl" property="status" value="<%=jkbnToroku%>">
                        <del>
                          <bean:write name="comMdl" property="sei" />&nbsp;<bean:write name="comMdl" property="mei" /></del>
                      </logic:notEqual>

                    </div>
                    <div>

                      <logic:equal name="prj060Form" property="todoDelete" value="true">
                        <a href="#!" onClick="setDateParam();return deleteCmt('<%=commentDelClick%>', '<bean:write name="comMdl" property="pcmSid" />');">
                          <span class="txt_r">
                            <img class="classic-display" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete" />">
                            <img class="original-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          </span>
                        </a>

                      </logic:equal>
                      <logic:notEqual name="prj060Form" property="todoDelete" value="true">
                        <logic:equal name="comMdl" property="deleteKbn" value="true">
                          <a href="#!" onClick="return deleteCmt('<%=commentDelClick%>', '<bean:write name="comMdl" property="pcmSid" />');">
                            <span class="txt_r bgC_header2">
                              <img class="classic-display" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete" />">
                              <img class="original-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                            </span>
                          </a>
                        </logic:equal>
                      </logic:notEqual>
                    </div>
                  </div>
                  <div>
                    <bean:write name="comMdl" property="pcmComment" filter="false" />
                  </div>
                  <div class="borderLine mb5"></div>
                </logic:iterate>
              </logic:notEmpty>
            </td>
          </tr>

          <logic:equal name="prj060Form" property="todoEdit" value="true">
            <tr class="bgC_tableCell">
              <td>
                <table class="table-top border_none m0">
                  <tr>
                    <td class="w100 border_none">
                      <table class="w100">
                        <tr>
                          <td class="txt_m border_none">
                            <span>
                              <gsmsg:write key="cmn.comment" />
                            </span>
                          </td>
                          <td class="no_w txt_m hp100 border_none">
                            <textarea name="prj060comment" rows="5" class="ofx_h w100 hp100" onkeyup="showLengthStr(value, <%=maxLengthToDoCmt%>, 'inputlength');" id="inputstr"><bean:write name="prj060Form" property="prj060comment" /></textarea>
                            <div>
                              <span><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength">0</span>
                              <span class="formCounter_max">&nbsp;/&nbsp;<%=maxLengthToDoCmt%><gsmsg:write key="cmn.character" /></span>
                              <span class="flo_r mt5 border_none">
                                <button type="button" value="<gsmsg:write key="project.122" />" class="baseBtn" onclick="buttonPush('<%=commentClick%>');">
                                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="project.122" />">
                                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="project.122" />">
                                  <gsmsg:write key="project.122" />
                                </button>
                              </span>
                            </div>
                          </td>
                        </tr>

                        <logic:equal name="prj060Form" property="useSmail" value="true">
                          <logic:equal name="prjMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
                            <tr>
                              <td class="border_none">&nbsp;</td>
                              <td class="border_none txt_b" colspan="2">
                                <span>
                                  <gsmsg:write key="shortmail.notification" />
                                </span>
                              </td>
                            </tr>
                            <tr>
                            <tr>
                              <td class="border_none">&nbsp;</td>
                              <td class="border_none" colspan="2">
                                <span class="verAlignMid">
                                  <logic:equal name="prj060Form" property="prj060smailKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_FREE)%>">
                                    <html:radio property="prj060CommentMailSend" styleId="cmtSmail1" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.NOT_SEND)%>" />
                                    <label for="cmtSmail1"><%=textNo%></label>
                                    <html:radio styleClass="ml10" property="prj060CommentMailSend" styleId="cmtSmail3" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_TANTO)%>" />
                                    <label for="cmtSmail3"><%=textCmnStaff%></label>
                                     </logic:equal>
                                  <html:radio styleClass="ml10" property="prj060CommentMailSend" styleId="cmtSmail4" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER)%>" />
                                  <label for="cmtSmail4"><%=textProjectAdm%></label>
                                  <html:radio styleClass="ml10" property="prj060CommentMailSend" styleId="cmtSmail5" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER_AND_TANTO)%>" />
                                  <label for="cmtSmail5"><%=textProjectLeaderAndTanto%></label>
                                  <html:radio styleClass="ml10" property="prj060CommentMailSend" styleId="cmtSmail2" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_ALL_MEMBER)%>" />
                                  <label for="cmtSmail2"><%=textAllMem%></label>
                                </span>
                              </td>
                            </tr>
                          </logic:equal>
                        </logic:equal>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>
        </table>
      </div>

      <div class="ml10 w35">

        <table class="w100">
          <tr>
            <td>
              <table class="table-top">
                <tr>
                  <th class="no_w w20 txt_l">
                    <img class="classic-display" src="../project/images/classic/icon_history.gif" alt="<gsmsg:write key="cmn.edit" />">
                    <img class="original-display" src="../common/images/original/icon_log.png" alt="<gsmsg:write key="cmn.edit" />">
                    <gsmsg:write key="project.prj060.6" />
                  </th>
                </tr>
                <tr>
                  <td>
                    <gsmsg:write key="project.prj060.7" />：
                    <span>
                      <bean:write name="prjMdl" property="rate" />%(<bean:write name="prjMdl" property="statusName" />)
                    </span>
                    <div>
                      <logic:equal name="prj060Form" property="todoEdit" value="true">
                        <bean:define id="prjStsMdl" name="prj060Form" property="prjStatusMdl" />

                        <logic:notEmpty name="prjStsMdl" property="todoStatusList">
                          <logic:iterate id="prjStatus" name="prjStsMdl" property="todoStatusList">
                            <bean:define id="idbase" value="prj060status" />
                            <bean:define id="ptsSid" name="prjStatus" property="ptsSid" />
                            <bean:define id="idname" value="<%=String.valueOf(idbase) + String.valueOf(ptsSid)%>" />
                            <bean:define id="ptsRate" name="prjStatus" property="ptsRate" />

                            <span class="verAlignMid">
                            <html:radio property="prj060status" styleId="<%=idname%>" value="<%=String.valueOf(ptsSid)%>" onclick='<%="rateChange(" + ptsSid + ")"%>' />
                              <label class="mr10" for="<%=idname%>">
                                <bean:write name="prjStatus" property="ptsRate" />%(<bean:write name="prjStatus" property="ptsName" />)
                              </label>
                            </span>
                          </logic:iterate>
                        </logic:notEmpty>
                      </logic:equal>
                    </div>
                    <logic:equal name="prj060Form" property="todoEdit" value="true">
                      <div id="zisseki_fr">
                        <div>
                          <gsmsg:write key="project.prj060.8" />
                        </div>
                        <table>
                          <tr>
                            <td class="border_none p0 no_w">
                              <span>
                                <gsmsg:write key="cmn.start" />：
                              </span>
                            </td>
                            <td class="border_none verAlignMid">
                              <div class="verAlignMid">
                                <html:text name="prj060Form" property="prj060SelectDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatefr"/>
                                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                              </div>
                              <div class="verAlignMid ml5">
                                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDatefr')[0], 1)">
                                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                                  <i class="icon-paging_left "></i>
                                </button>
                                <button type="button" class="baseBtn classic-display no_w" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDatefr')[0], 2)">
                                  <gsmsg:write key='cmn.today' />
                                </button>
                                <span>
                                  <a class="fw_b todayBtn original-display no_w" onclick="return moveDay($('#selDatefr')[0], 2)">
                                    <gsmsg:write key='cmn.today' />
                                  </a>
                                </span>
                                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDatefr')[0], 3)">
                                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                                  <i class="icon-paging_right "></i>
                                </button>
                                <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn no_w ml5" onclick="return clearDate($('#selDatefr'))">
                                  <gsmsg:write key="cmn.clear" />
                                </button>
                              </div>
                              </td>
                                 <tr>
                        </table>

                      </div>

                      <div id="zisseki_to">
                        <table>
                          <tr>
                            <td class="border_none p0 no_w">
                              <span>
                                <gsmsg:write key="cmn.end" />：
                              </span>
                            </td>
                            <td class="border_none verAlignMid">
                              <div class="verAlignMid">
                                <html:text name="prj060Form" property="prj060SelectDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto"/>
                                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
                              </div>
                              <div class="verAlignMid ml5">
                                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDateto')[0], 1)">
                                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                                  <i class="icon-paging_left "></i>
                                </button>
                                <button type="button" class="baseBtn classic-display no_w" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDateto')[0], 2)">
                                  <gsmsg:write key='cmn.today' />
                                </button>
                                <span>
                                  <a class="fw_b todayBtn original-display no_w" onclick="return moveDay($('#selDateto')[0], 2)">
                                    <gsmsg:write key='cmn.today' />
                                  </a>
                                </span>
                                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDateto')[0], 3)">
                                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                                  <i class="icon-paging_right "></i>
                                </button>
                                <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5 no_w" onclick="return clearDate($('#selDateto'))">
                                  <gsmsg:write key="cmn.clear" />
                                </button>
                              </div>

                            </td>
                          </tr>
                        </table>
                      </div>

                      <div id="kosu" class="mt5">
                        <span>
                          <gsmsg:write key="project.prj060.5" />：
                        </span>
                        <html:text name="prj060Form" property="prj060ResultKosu" size="5" maxlength="5" />
                      </div>

                    </logic:equal>

                    <table class="w100">
                      <tr class="txt_b">
                        <td class="txt_l border_none p0">
                          <span>
                            <gsmsg:write key="project.36" />
                          </span>
                        </td>
                      </tr>
                      <tr>
                        <td class="border_none">
                          <textarea name="prj060riyu" rows="3" class="w100" onkeyup="showLengthStr(value, <%=maxLengthReason%>, 'inputlength2');" id="inputstr2"><bean:write name="prj060Form" property="prj060riyu" /></textarea>
                          <br>
                          <span>
                            <gsmsg:write key="cmn.current.characters" />:
                          </span>
                          <span id="inputlength2">0</span>
                          <span class="formCounter_max">/<%=maxLengthReason%><gsmsg:write key="cmn.character" />
                          </span>
                        </td>
                      </tr><br>

                      <logic:equal name="prj060Form" property="useSmail" value="true">
                        <logic:equal name="prjMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
                          <tr class="txt_b">
                            <td class="border_none p0">
                              <span>
                                <gsmsg:write key="shortmail.notification" />
                              </span>
                            </td>
                          </tr>
                          <tr class="txt_b">
                            <td class="border_none">
                              <div class="verAlignMid">
                                <logic:equal name="prj060Form" property="prj060smailKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_FREE)%>">
                                  <html:radio property="prj060MailSend" styleId="smail1" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.NOT_SEND)%>" />
                                  <label for="smail1"><%=textNo%></label>
                                  <html:radio styleClass="ml10" property="prj060MailSend" styleId="smail3" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_TANTO)%>" />
                                  <label for="smail3"><%=textCmnStaff%></label>
                                </logic:equal>
                                <html:radio styleClass="ml10" property="prj060MailSend" styleId="smail4" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER)%>" />
                                <label class="mr10" for="smail4"><%=textProjectAdm%></label>
                              </div>
                                <div class="verAlignMid">
                                <html:radio property="prj060MailSend" styleId="smail5" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER_AND_TANTO)%>" />
                                <label class="mr10" for="smail5"><%=textProjectLeaderAndTanto%></label>
                                </div>
                                <div class="verAlignMid">
                                <html:radio property="prj060MailSend" styleId="smail2" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_ALL_MEMBER)%>" />
                                <label for="smail2"><%=textAllMem%></label>
                                </div>
                            </td>
                          </tr>
                        </logic:equal>
                      </logic:equal>

                      <tr>
                        <td class="txt_c border_none">
                          <button type="button" value="<gsmsg:write key="cmn.update" />" class="baseBtn" onclick="buttonPush('<%=updateClick%>');">
                            <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                            <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                            <gsmsg:write key="cmn.update" />
                          </button>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
              <br>
            </td>
          </tr>

          <tr>
            <td>
              <table class="table-top" >
                <tr>
                  <th class="txt_l no_w">
                    <img class="classic-display" src="../project/images/classic/icon_history.gif">
                    <img class="original-display" src="../common/images/original/icon_log.png">
                    <gsmsg:write key="project.37" />
                  </th>
                </tr>

                <tr>
                  <td>

                    <logic:notEmpty name="prj060Form" property="todoHisList">
                      <table class="w100">

                        <logic:iterate id="prjHis" name="prj060Form" property="todoHisList" type="StatusHistoryModel">
                          <tr>
                            <td rowspan="2" class="no_w border_none classic-display">
                              <img src="../project/images/classic/icon_point_arrow.gif">
                            </td>
                            <td class="border_none" rowspan="2">
                              <span>
                                <bean:write name="prjHis" property="rate" />%(<bean:write name="prjHis" property="statusName" />)
                              </span>
                            </td>
                            <td class="no_w border_none">
                              <span>
                                <bean:write name="prjHis" property="strAddDate" />
                              </span>
                            </td>
                            <td rowspan="2" class="no_w border_none">

                              <logic:equal name="prj060Form" property="todoDelete" value="true">
                                <a href="#!" onClick="setDateParam();return deleteHistory('<%=statusDelClick%>', '<bean:write name="prjHis" property="hisSid" />');">
                                  <img class="classic-display" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete" />">
                                  <img class="original-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                                </a>
                              </logic:equal>
                            </td>
                          </tr>
                          <tr>
                            <td class="border_none">
                              <logic:equal name="prjHis" property="userStatus" value="<%=jkbnToroku%>">
                                <span class="<%=UserUtil.getCSSClassNameNormal(prjHis.getUsrUkoFlg())%>">
                                  <bean:write name="prjHis" property="sei" />&nbsp;<bean:write name="prjHis" property="mei" />
                                </span>
                              </logic:equal>
                              <logic:notEqual name="prjHis" property="userStatus" value="<%=jkbnToroku%>">
                                <del><bean:write name="prjHis" property="sei" />&nbsp;<bean:write name="prjHis" property="mei" /></del>
                              </logic:notEqual>
                            </td>
                          </tr>

                          <tr>
                            <td class="border_none" colspan="5">
                              <div>
                                <bean:write name="prjHis" property="reason" filter="false" />
                              </div>
                            </td>
                          </tr>
                        </logic:iterate>
                      </table>
                    </logic:notEmpty>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="footerBtn_block mt5">
      <logic:equal name="prj060Form" property="todoEdit" value="true">
        <button type="button" value="<gsmsg:write key="cmn.fixed" />" class="baseBtn" onclick="setDateParam();buttonPush('<%=editTodoClick%>');">
          <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
          <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
          <gsmsg:write key="cmn.fixed" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="setDateParam();buttonPush('<%=deleteClick%>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('<%=backClick%>')">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>


  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</html:form>

</body>
</html:html>
