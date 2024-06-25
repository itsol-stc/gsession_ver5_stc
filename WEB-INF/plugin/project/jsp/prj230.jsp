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

<bean:define id="prjMdl" name="prj230Form" property="projectItem" />
<bean:define id="prjSid" name="prj230Form" property="prj060prjSid" />
<bean:define id="todoSid" name="prj230Form" property="prj060todoSid" />

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj060.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj230.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<logic:equal name="prj230Form" property="todoEdit" value="true">
  <body onload="rateChange(-1);moveComment();showLengthId($('#inputstr')[0], <%=maxLengthToDoCmt%>, 'inputlength');showLengthId($('#inputstr2')[0], <%=maxLengthReason%>, 'inputlength2');" onunload="calWindowClose();">
</logic:equal>
<logic:notEqual name="prj230Form" property="todoEdit" value="true">
  <body onload="moveComment();" onunload="calWindowClose();">
</logic:notEqual>

<html:form action="/project/prj230">

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


  <html:hidden property="prj060prjSid" />
  <html:hidden property="prj060todoSid" />

  <logic:notEmpty name="prj230Form" property="prj040scMemberSid" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj040scMemberSid" scope="request">
      <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj040svScMemberSid" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj040svScMemberSid" scope="request">
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

  <logic:notEmpty name="prj230Form" property="prj030sendMember" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj030sendMember" scope="request">
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
  <logic:notEmpty name="prj230Form" property="prj070scTantou" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070scTantou" scope="request">
      <input type="hidden" name="prj070scTantou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070scTourokusya" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070scTourokusya" scope="request">
      <input type="hidden" name="prj070scTourokusya" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070svScTantou" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070svScTantou" scope="request">
      <input type="hidden" name="prj070svScTantou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070svScTourokusya" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070svScTourokusya" scope="request">
      <input type="hidden" name="prj070svScTourokusya" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070svScJuuyou" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070svScJuuyou" scope="request">
      <input type="hidden" name="prj070svScJuuyou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070scJuuyou" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070scJuuyou" scope="request">
      <input type="hidden" name="prj070scJuuyou" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070SearchTarget" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070SearchTarget" scope="request">
      <input type="hidden" name="prj070SearchTarget" value="<bean:write name="item"/>">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="prj230Form" property="prj070SvSearchTarget" scope="request">
    <logic:iterate id="item" name="prj230Form" property="prj070SvSearchTarget" scope="request">
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
      <li></li>
    </ul>
  </div>
  <div class="wrapper">

  <div class="sub_title fs_18 txt_l fw_b">
    <logic:equal name="prjMdl" property="prjBinSid" value="0">
      <img class="classic-display" src="../project/images/classic/icon_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" >
      <img class="original-display" src="../project/images/original/plugin_project.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
    </logic:equal>
    <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
      <img src="../project/prj230.do?CMD=getImageFile&prj060prjSid=<bean:write name="prj230Form" property="prj060prjSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />&prj060todoSid=<bean:write name="prj230Form" property="prj060todoSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="prj_img_ico wp30hp30">
    </logic:notEqual>
  <bean:write name="prjMdl" property="projectName" />
  </div>

    <div class="txt_l mt5">
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
      <span class="fs_16">
        <bean:write name="prjMdl" property="todoTitle" filter="false" />
      </span>
    </div>

    <logic:messagesPresent message="false">
     <div class="mt15">
      <html:errors />
      </div>
    </logic:messagesPresent>

    <table class="table-left">

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
        <td class="txt_l w30">
          <logic:equal name="prj230Form" property="addUserStatus" value="<%=jkbnToroku%>">
            <span>
              <bean:write name="prj230Form" property="addUserName" />
            </span>
          </logic:equal>
          <logic:notEqual name="prj230Form" property="addUserStatus" value="<%=jkbnToroku%>">
            <del>
              <span>
                <bean:write name="prj230Form" property="addUserName" />
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
        <td class="txt_l" nowrap>
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
        <td class="txt_t no_w">
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
            <img class="classic-display" src="../common/images/classic/icon_star_blue.png"  alt="<gsmsg:write key="project.58" />">
            <img class="classic-display" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.58" />">
            <img class="classic-display" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.58" />">
            <span class="original-display">
              <i class="icon-star  importance-blue"></i> <i class="icon-star_line"></i> <i class="icon-star_line"></i>
            </span>
          </logic:equal>
          <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE)%>">
            <img class="classic-display" src="../common/images/classic/icon_star_gold.png"  alt="<gsmsg:write key="project.59" />">
            <img class="classic-display" src="../common/images/classic/icon_star_gold.png"  alt="<gsmsg:write key="project.59" />">
            <img class="classic-display" src="../common/images/classic/icon_star_white.png"  alt="<gsmsg:write key="project.59" />">
            <span class="original-display">
              <i class="icon-star  importance-yellow"></i> <i class="icon-star  importance-yellow"></i> <i class="icon-star_line"></i>
            </span>
          </logic:equal>
          <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH)%>">
            <img class="classic-display" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
            <img class="classic-display" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
            <img class="classic-display" src="../common/images/classic/icon_star_red.png"  alt="<gsmsg:write key="project.60" />">
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
            <logic:notEmpty name="prj230Form" property="userList" scope="request">
              <logic:iterate id="memMdl" name="prj230Form" property="userList" scope="request">
                <logic:equal name="memMdl" property="status" value="<%=jkbnToroku%>">
                  <bean:write name="memMdl" property="sei" />&nbsp;<bean:write name="memMdl" property="mei" />
                </logic:equal>
                <logic:notEqual name="memMdl" property="status" value="<%=jkbnToroku%>">
                  <del>
                    <bean:write name="memMdl" property="sei" />&nbsp;<bean:write name="memMdl" property="mei" />
                  </del>
                </logic:notEqual>
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
          <logic:notEmpty name="prj230Form" property="binfList" scope="request">
            <logic:iterate id="fileMdl" name="prj230Form" property="binfList" scope="request">
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

    <!-- 下テーブル -->
    <table class="table-top">
      <tr>
        <th class="table-header1 table_title-color txt_l border_none">
          <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
          <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
          <gsmsg:write key="project.prj060.6" />
        </th>
      </tr>
      <tr>
        <td class="border_none">
          <gsmsg:write key="project.prj060.7" />：
          <span>
            <bean:write name="prjMdl" property="rate" />%(<bean:write name="prjMdl" property="statusName" />)
          </span>
          <br>
          <div>
            <logic:equal name="prj230Form" property="todoEdit" value="true">
              <bean:define id="prjStsMdl" name="prj230Form" property="prjStatusMdl" />

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
          </div>
          <logic:equal name="prj230Form" property="todoEdit" value="true">
            <div id="zisseki_fr" class="prj_pad_tb1">
              <div>
                <gsmsg:write key="project.prj060.8" />
              </div>

              <span>
                <gsmsg:write key="cmn.start" />：
              </span>
              <span class="verAlignMid">
                <html:text name="prj230Form" property="prj060SelectDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatefr"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>

                <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selDatefr')[0], 1)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDatefr')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </button>
                <span>
                  <a class="fw_b todayBtn original-display" onclick="return moveDay($('#selDatefr')[0], 2)">
                    <gsmsg:write key='cmn.today' />
                  </a>
                </span>
                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDatefr')[0], 3)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
                <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="return clearDate($('#selDatefr'))">
                  <gsmsg:write key="cmn.clear" />
                </button>
              </span>
            </div>

            <div id="zisseki_to" class="prj_pad_tb1 mt5">

              <span>
                <gsmsg:write key="cmn.end" />：
              </span>
              <span class="verAlignMid">
                <html:text name="prj230Form" property="prj060SelectDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>

                <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveDay($('#selDateto')[0], 1)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left "></i>
                </button>
                <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selDateto')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </button>
                <span>
                  <a class="fw_b todayBtn original-display" onclick="return moveDay($('#selDateto')[0], 2)">
                    <gsmsg:write key='cmn.today' />
                  </a>
                </span>
                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selDateto')[0], 3)">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right "></i>
                </button>
                <button type="button" value="<gsmsg:write key="cmn.clear" />" class="baseBtn ml5" onclick="return clearDate($('#selDateto'))">
                  <gsmsg:write key="cmn.clear" />
                </button>
              </span>
            </div>

            <div id="kosu" class="prj_pad_tb1 mt5">
              <span>
                <gsmsg:write key="project.prj060.5" />：
              </span>
              <html:text name="prj230Form" property="prj060ResultKosu" styleClass="wp50" maxlength="5" />
            </div>
          </logic:equal>

          <table class="w100 mt5">
            <tr class="txt_b">
              <td class="border_none p0">
                <span>
                  <gsmsg:write key="project.36" />
                </span>
              </td>
            </tr>
            <tr>
              <td class="border_none p0">
                <textarea name="prj060riyu" class="w100" rows="3" class="text_gray textarea_base" onkeyup="showLengthStr(value, <%=maxLengthReason%>, 'inputlength2');" id="inputstr2"><bean:write name="prj230Form" property="prj060riyu" /></textarea>
                <div>
                  <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>
                  <span class="formCounter_max"> / <%=maxLengthReason%><gsmsg:write key="cmn.character" /></span>
                </div>
              </td>
            </tr>
            <logic:equal name="prj230Form" property="useSmail" value="true">
              <logic:equal name="prjMdl" property="prjMyKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KBN_MY_PRJ_DEF)%>">
                <tr class="txt_b">
                  <td class="border_none p0">
                    <br>
                    <span>
                      <gsmsg:write key="shortmail.notification" />
                    </span>
                  </td>
                </tr>
                <tr>

                  <td class="border_none p0">
                    <span class="verAlignMid">
                      <logic:equal name="prj230Form" property="prj060smailKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.TODO_MAIL_FREE)%>">
                        <html:radio property="prj060MailSend" styleId="smail1" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.NOT_SEND)%>" />
                        <label for="smail1"><%=textNo%></label>
                        <html:radio styleClass="ml10" property="prj060MailSend" styleId="smail3" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_TANTO)%>" />
                        <label for="smail3"><%=textCmnStaff%></label>
                      </logic:equal>
                      <html:radio styleClass="ml10" property="prj060MailSend" styleId="smail4" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER)%>" />
                      <label for="smail4"><%=textProjectAdm%></label>
                      <html:radio styleClass="ml10" property="prj060MailSend" styleId="smail5" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_LEADER_AND_TANTO)%>" />
                      <label for="smail5"><%=textProjectLeaderAndTanto%></label>
                      <html:radio styleClass="ml10" property="prj060MailSend" styleId="smail2" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEND_ALL_MEMBER)%>" />
                      <label for="smail2"><%=textAllMem%></label>
                    </span>
                  </td>
                </tr>
              </logic:equal>
            </logic:equal>
          </table>
          </logic:equal>

          <div class="txt_c">
            <button type="button" value="<gsmsg:write key="cmn.update" />" class="baseBtn changeStatus">
              <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="original-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.update" />
            </button>
          </div>
        </td>
      </tr>
    </table>
    <div>
      <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="return window.close();">
        <img class="classic-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="original-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    </div>
  </div>

  <div id="dialogChangeStatus" title="状態変更確認" class="display_n">
       <table class="w100 h100">
        <tr>
          <td>
            <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
          </td>
          <td class="w100 pl10">
             <span class="fs_13">状態を変更してもよろしいですか？</span>
          </td>
        </tr>
      </table>
  </div>

</html:form>
</body>
</html:html>
