<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<!DOCTYPE html>

<gsmsg:define id="projectTitle" msgkey="cmn.project" />
<gsmsg:define id="title" msgkey="cmn.title" />
<gsmsg:define id="todoNum" msgkey="project.prj050.5" />
<gsmsg:define id="todoTanto" msgkey="cmn.staff" />
<gsmsg:define id="todoWeight" msgkey="project.prj050.4" />
<gsmsg:define id="todoStatus" msgkey="cmn.status" />
<gsmsg:define id="todoStartPlan" msgkey="project.100" />
<gsmsg:define id="todoLimitPlan" msgkey="project.src.66" />
<%-- CMD定数 --%>
<%
  String todoAddClick = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_TODO_ADD_CLICK;
  String todoNameClick = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_TODO_REF_CLICK;
  String prjSelectMember = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_SCT_MEM;
  String prjSelectAddUser = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_SCT_ADD_USR;
  String searchClick = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_SEARCH;
  String export = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_EXPORT;
  String prev = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_PAGE_PREVEW;
  String next = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_PAGE_NEXT;
  String backClick = jp.groupsession.v2.prj.prj070.Prj070Action.CMD_BACK_CLICK;

  String low = String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_LOW);
  String middle = String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE);
  String high = String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH);

  String id_low = "juyou" + low;
  String id_middle = "juyou" + middle;
  String id_high = "juyou" + high;

  int[] sortKeyList01 = new int[] {
      jp.groupsession.v2.prj.GSConstProject.SORT_PROJECT_TITLE,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_NO,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_TITLE,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_WEIGHT,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_STATUS,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_START_PLAN,
      jp.groupsession.v2.prj.GSConstProject.SORT_TODO_LIMIT_PLAN
  };
  String[] title_width01 = new String[] { "20", "5", "30", "5", "10", "10", "10" };

  String[] titleList01 = new String[] {
      projectTitle,
      todoNum,
      title,
      todoWeight,
      todoStatus,
      todoStartPlan,
      todoLimitPlan
  };

  String prj070 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ070;
  String mode_add = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_ADD;
%>

<%-- ソートオーダー --%>
<%
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<bean:define id="orderKey" name="prj070Form" property="prj070order" />
<bean:define id="sortKbn" name="prj070Form" property="prj070sort" />
<%
  int iOrderKey = ((Integer) orderKey).intValue();
%>
<%
  int iSortKbn = ((Integer) sortKbn).intValue();
%>

<%-- キーワード検索区分 --%>
<%
  String keyWordAnd = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEY_WORD_KBN_AND);
  String keyWordOr = String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEY_WORD_KBN_OR);
%>

<%-- 検索対象 --%>
<%
  String targetTitle = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEARCH_TARGET_TITLE);
  String targetNaiyou = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEARCH_TARGET_NAIYOU);
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
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj070.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>


<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/project/prj070">

    <input type="hidden" name="CMD" id="CMD" value="">
    <input type="hidden" name="prj070scKaisiYoteiYear" value="">
    <input type="hidden" name="prj070scKaisiYoteiMonth" value="">
    <input type="hidden" name="prj070scKaisiYoteiDay" value="">
    <input type="hidden" name="prj070scKigenYear" value="">
    <input type="hidden" name="prj070scKigenMonth" value="">
    <input type="hidden" name="prj070scKigenDay" value="">
    <input type="hidden" name="prj070scKaisiJissekiYear" value="">
    <input type="hidden" name="prj070scKaisiJissekiMonth" value="">
    <input type="hidden" name="prj070scKaisiJissekiDay" value="">
    <input type="hidden" name="prj070scSyuryoJissekiYear" value="">
    <input type="hidden" name="prj070scSyuryoJissekiMonth" value="">
    <input type="hidden" name="prj070scSyuryoJissekiDay" value="">

    <html:hidden property="prj010cmdMode" />
    <html:hidden property="prj010page1" />
    <html:hidden property="prj010page2" />
    <html:hidden property="prj010sort" />
    <html:hidden property="prj010Init" />
    <html:hidden property="prj010order" />
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

    <logic:notEmpty name="prj070Form" property="prj040scMemberSid" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj040scMemberSid" scope="request">
        <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj070Form" property="prj040svScMemberSid" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj040svScMemberSid" scope="request">
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
    <html:hidden property="selectingDate" />
    <html:hidden property="selectingStatus" />
    <html:hidden property="selectingCategory" />
    <html:hidden property="selectingMember" />

    <logic:notEmpty name="prj070Form" property="prj030sendMember" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj030sendMember" scope="request">
        <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prj070scrId" />
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
    <html:hidden property="prj070sort" />
    <html:hidden property="prj070order" />
    <html:hidden property="prj070searchFlg" />
    <html:hidden property="prj070InitFlg" />

    <logic:notEmpty name="prj070Form" property="prj070scTantou" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj070scTantou" scope="request">
        <input type="hidden" name="prj070scTantou" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj070Form" property="prj070scTourokusya" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj070scTourokusya" scope="request">
        <input type="hidden" name="prj070scTourokusya" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj070Form" property="prj070svScTantou" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj070svScTantou" scope="request">
        <input type="hidden" name="prj070svScTantou" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj070Form" property="prj070svScTourokusya" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj070svScTourokusya" scope="request">
        <input type="hidden" name="prj070svScTourokusya" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj070Form" property="prj070svScJuuyou" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj070svScJuuyou" scope="request">
        <input type="hidden" name="prj070svScJuuyou" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj070Form" property="prj070SvSearchTarget" scope="request">
      <logic:iterate id="item" name="prj070Form" property="prj070SvSearchTarget" scope="request">
        <input type="hidden" name="prj070SvSearchTarget" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prj050scrId" value="<%=prj070%>" />
    <html:hidden property="prj050cmdMode" value="<%=mode_add%>" />

    <html:hidden property="prj060scrId" value="<%=prj070%>" />
    <html:hidden property="prj060prjSid" value="" />
    <html:hidden property="prj060todoSid" value="" />

    <html:hidden property="selectDir" />

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
          <gsmsg:write key="project.123" />
        </li>
        <li>
          <div>
            <button type="button" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="setDateParam();buttonPush('<%=todoAddClick%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=backClick%>');">
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
      <table class="table-left m0 mb10">

        <tr>
          <td class="bgC_header1 txt_l" colspan="4">
            <img class="classic-display table_header_icon_search" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <span class="table_title-color"><gsmsg:write key="cmn.advanced.search" /></span>
          </td>
        </tr>

        <tr>
          <th class="txt_c no_w w10">
            <gsmsg:write key="cmn.project" />
          </th>
          <td class="w40">
            <div>
              <html:select property="prj070scPrjSid" styleClass="wp300" onchange="changeProject();">
                <html:optionsCollection name="prj070Form" property="projectLabel" value="value" label="label" />
              </html:select>
            </div>
            <div class="verAlignMid">
              <logic:equal name="prj070Form" property="prj070scPrjSid" value="-1">
                <html:checkbox property="prj070JoinOnlyFlg" styleId="onlyFlg" value="true">
                  <label for="onlyFlg">
                    <gsmsg:write key="project.prj070.7" />
                  </label>
                </html:checkbox>
              </logic:equal>
              <logic:notEqual name="prj070Form" property="prj070scPrjSid" value="-1">
                <html:checkbox property="prj070JoinOnlyFlg" styleId="onlyFlg" disabled="true">
                  <label for="onlyFlg">
                    <gsmsg:write key="project.prj070.7" />
                  </label>
                </html:checkbox>
              </logic:notEqual>
            </div>
          </td>
          <!-- 管理番号入力欄 -->
          <th class="txt_c no_w">
            <gsmsg:write key="project.prj050.5" />
          </th>
          <td colspan="1">
            <html:text property="prj070scKanriNumber" styleClass="wp80" maxlength="8"/>
          </td>
        </tr>

        <tr>
          <th class="txt_c">
            <gsmsg:write key="project.prj050.4" />
          </th>
          <td>
          <span class="verAlignMid">
            <html:multibox name="prj070Form" property="prj070scJuuyou" styleId="<%=id_low%>">
              <%=low%>
            </html:multibox>
            <label class="mr10" for="<%=id_low%>" onclick="return clickLabel(this);">
              <gsmsg:write key="project.58" />
              <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" alt="<gsmsg:write key="project.58" />">
              <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
              <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
              <span class="original-display">
                <i class="icon-star  importance-blue"></i>
                <i class="icon-star_line"></i>
                <i class="icon-star_line"></i>
              </span>
            </label>
            <html:multibox name="prj070Form" property="prj070scJuuyou" styleId="<%=id_middle%>">
              <%=middle%>
            </html:multibox>
            <label class="mr10" for="<%=id_middle%>" onclick="return clickLabel(this);">
              <gsmsg:write key="project.59" />
              <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
              <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.59" />">
              <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.59" />">
              <span class="original-display">
                <i class="icon-star  importance-yellow"></i>
                <i class="icon-star  importance-yellow"></i>
                <i class="icon-star_line"></i>
              </span>
            </label>
            <html:multibox name="prj070Form" property="prj070scJuuyou" styleId="<%=id_high%>">
              <%=high%>
            </html:multibox>
            <label for="<%=id_high%>" onclick="return clickLabel(this);">
              <gsmsg:write key="project.60" />
              <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
              <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
              <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
              <span class="original-display">
                <i class="icon-star  importance-red"></i>
                <i class="icon-star  importance-red"></i>
                <i class="icon-star  importance-red"></i>
              </span>
            </label>
            </span>
          </td>

          <%
            String tantoRow = "";
          %>
          <logic:notEmpty name="prj070Form" property="categoryLabel">
          <% tantoRow = " rowspan=\"2\""; %>
          </logic:notEmpty>

          <th class="w10 txt_c no_w" <%=tantoRow%>>
            <gsmsg:write key="project.113" />
          </th>
          <td class="w40" <%=tantoRow%>>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onclick="setDateParam();buttonPush('<%=prjSelectMember%>');">
              <gsmsg:write key="cmn.select" />
            </button>
           <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" onClick="clearUserList();">
             <gsmsg:write key="cmn.clear" />
           </button>
            <div id="selectMember">
              <logic:notEmpty name="prj070Form" property="memberList" scope="request">
                <logic:iterate id="memMdl" name="prj070Form" property="memberList" scope="request" type="CmnUsrmInfModel">
                  <div class="<%=UserUtil.getCSSClassNameNormal(memMdl.getUsrUkoFlg())%> display_inline mr10">
                    <bean:write name="memMdl" property="usiSei" />&nbsp;<bean:write name="memMdl" property="usiMei" />
                  </div>
                </logic:iterate>
              </logic:notEmpty>
            </div>
          </td>
        </tr>

        <logic:notEmpty name="prj070Form" property="categoryLabel">
          <tr>
            <th class="w10 txt_c no_w">
              <gsmsg:write key="cmn.label" />
            </th>
            <td class="w40">
              <html:select property="prj070scCategorySid" styleClass="select01">
                <html:optionsCollection name="prj070Form" property="categoryLabel" value="value" label="label" />
              </html:select>
            </td>
          </tr>
        </logic:notEmpty>

        <tr>
          <th class="no_w txt_c">
            <gsmsg:write key="project.prj070.2" />
          </th>
          <td class="no_w">
            <div class="verAlignMid w100">
              <html:text name="prj070Form" property="prj070strPlanDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatesp" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDatesp'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
            </div>
          </td>
          <th class="no_w txt_c">
            <gsmsg:write key="project.prj070.4" />
          </th>
          <td class="no_w">
            <div class="verAlignMid w100">
              <html:text name="prj070Form" property="prj070endPlanDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateep" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDateep'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
            </div>
          </td>
        </tr>

        <tr>
          <th class="no_w txt_c">
            <gsmsg:write key="project.prj070.5" />
          </th>
          <td class="no_w">
            <div class="verAlignMid w100">
             <html:text name="prj070Form" property="prj070strResultDater" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatesr" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDatesr'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
            </div>
          </td>
          <th class="no_w txt_c">
            <gsmsg:write key="project.prj070.6" />
          </th>
          <td class="no_w">
            <div class="verAlignMid w100">
              <html:text name="prj070Form" property="prj070endResultDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateer" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <button type="button" value="<gsmsg:write key="cmn.without.specifying" />" class="baseBtn ml5" onclick="clearDate($('#selDateer'))">
                <gsmsg:write key="cmn.without.specifying" />
              </button>
            </div>
          </td>
        </tr>

        <tr>
          <th class="txt_c">
            <gsmsg:write key="cmn.keyword" />
          </th>
          <td>
            <html:text property="prj070scTitle" styleClass="wp250" maxlength="100"/>
            <div class="verAlignMid">
              <html:radio name="prj070Form" property="prj070KeyWordkbn" value="<%=keyWordAnd%>" styleId="keyKbn_01" />
              <label for="keyKbn_01">
                <gsmsg:write key="cmn.contains.all.and" />
              </label>
              <html:radio name="prj070Form" property="prj070KeyWordkbn" value="<%=keyWordOr%>" styleClass="ml10" styleId="keyKbn_02" />
              <label for="keyKbn_02">
                <gsmsg:write key="cmn.orcondition" />
              </label>
            </div>
          </td>
          <th class="no_w txt_c">
            <gsmsg:write key="cmn.search2" />
          </th>
          <td>
            <span class="verAlignMid">
            <html:multibox styleId="search_scope_01" name="prj070Form" property="prj070SearchTarget" value="<%=targetTitle%>" />
            <label for="search_scope_01">
              <gsmsg:write key="cmn.title" />
            </label>
            <html:multibox styleClass="ml10" styleId="search_scope_02" name="prj070Form" property="prj070SearchTarget" value="<%=targetNaiyou%>" />
            <label for="search_scope_02">
              <gsmsg:write key="cmn.content" />
            </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w txt_c">
            <gsmsg:write key="cmn.registant" />
          </th>
          <td colspan="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onclick="setDateParam();buttonPush('<%=prjSelectAddUser%>');">
              <gsmsg:write key="cmn.select" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" onClick="clearAddUserList();">
              <gsmsg:write key="cmn.clear" />
            </button>
            <div id="selectAddUser">
              <logic:notEmpty name="prj070Form" property="addUserList" scope="request">
                <logic:iterate id="memMdl" name="prj070Form" property="addUserList" scope="request" type="CmnUsrmInfModel">
                  <div class="<%=UserUtil.getCSSClassNameNormal(memMdl.getUsrUkoFlg())%> display_inline mr10">
                    <bean:write name="memMdl" property="usiSei" />&nbsp;<bean:write name="memMdl" property="usiMei" />
                  </div>
                </logic:iterate>
              </logic:notEmpty>
            </div>
          </td>
          <th class="no_w txt_c">
            <gsmsg:write key="cmn.status" />
          </th>
          <td class="no_w">
            <html:text property="prj070scStatusFrom" styleClass="wp50 mr5" maxlength="3" />%<gsmsg:write key="tcd.153" /><html:text property="prj070scStatusTo" styleClass="wp50 ml5" maxlength="3" />%
          </td>
        </tr>
      </table>
      <div class="display_inline searchBtn w100">
        <div>
          <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onclick="buttonPush('<%=searchClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>
        <div class="flo_r searchExportBtn pos_abs">
          <logic:notEmpty name="prj070Form" property="projectList" scope="request">
            <button type="button" name="export" value="<gsmsg:write key="cmn.export" />" class="baseBtn m0" onclick="buttonPush('<%=export%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <gsmsg:write key="cmn.export" />
            </button>
          </logic:notEmpty>
        </div>
      </div>

      <logic:notEmpty name="prj070Form" property="projectList" scope="request">
      <bean:size id="count1" name="prj070Form" property="pageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging mt5">
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%=prev%>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="prj070page1" onchange="changePage(1);">
            <html:optionsCollection name="prj070Form" property="pageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%=next%>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <table class="table-top">
        <tr>
          <th class="w5 cursor_p"></th>
          <%
            for (int i = 0; i < sortKeyList01.length; i++) {
                  if (iSortKbn == sortKeyList01[i]) {
                    if (iOrderKey == order_desc) {
          %>
          <th class="w<%=title_width01[i]%> no_w cursor_p">
            <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
              <span><%=titleList01[i]%><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span>
              </span>
            </a>
          </th>
          <%
            } else {
          %>
          <th class="w<%=title_width01[i]%> no_w cursor_p">
            <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_desc%>);">
              <span><%=titleList01[i]%><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span>
              </span>
            </a>
          </th>
          <%
            }
                  } else {
          %>
          <th class="w<%=title_width01[i]%> cursor_p">
            <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
              <span><%=titleList01[i]%></span>
            </a>
          </th>
          <%
            }
                }
          %>
        </tr>

        <logic:notEmpty name="prj070Form" property="projectList" scope="request">
          <logic:iterate id="prjMdl" name="prj070Form" property="projectList" scope="request" indexId="idx">

            <tr class="js_listHover cursor_p" onClick="return viewTodo('<%=todoNameClick%>', '<bean:write name="prjMdl" property="projectSid" />', '<bean:write name="prjMdl" property="todoSid" />');">
              <td class="txt_c">
                <logic:equal name="prjMdl" property="keikoku" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEIKOKU_ARI)%>">
                  <img class="classic-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                  <img class="original-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                </logic:equal>
              </td>
              <td class="txt_l">
                <logic:equal name="prjMdl" property="prjBinSid" value="0">
                  <img class="classic-display" src="../project/images/classic/icon_project.png" alt="<gsmsg:write key="cmn.icon" />">
                  <img class="original-display" src="../project/images/original/plugin_project.png" alt="<gsmsg:write key="cmn.icon" />">
                </logic:equal>
                <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
                  <img src="../project/prj070.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prjMdl" property="projectSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />" name="pitctImage"  alt="<gsmsg:write key="cmn.icon" />"    onload="initImageView('pitctImage');" class="prj_img_ico wp30 hp30">
                </logic:notEqual>
                <span>
                  <bean:write name="prjMdl" property="projectName" />
                </span>
              </td>
              <td class="txt_c">
                <span>
                  <bean:write name="prjMdl" property="strKanriNo" />
                </span>
              </td>
              <td>
                <span class="cl_linkDef">
                  <bean:write name="prjMdl" property="todoTitle" filter="false" />
                </span>
              </td>

              <td class="txt_c no_w">
                <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_LOW)%>">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_blue.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <span class="original-display">
                    <i class="icon-star  importance-blue"></i>
                    <i class="icon-star_line"></i>
                    <i class="icon-star_line"></i>
                  </span>
                </logic:equal>
                <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_MIDDLE)%>">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_gold.png" alt="<gsmsg:write key="project.58" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_white.png" alt="<gsmsg:write key="project.58" />">
                  <span class="original-display">
                    <span class="original-display">
                      <i class="icon-star  importance-yellow"></i>
                      <i class="icon-star  importance-yellow"></i>
                      <i class="icon-star_line"></i>
                    </span>
                  </span>
                </logic:equal>
                <logic:equal name="prjMdl" property="juyo" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.WEIGHT_KBN_HIGH)%>">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <img class="classic-display pb5" src="../common/images/classic/icon_star_red.png" alt="<gsmsg:write key="project.60" />">
                  <span class="original-display">
                    <i class="icon-star  importance-red"></i>
                    <i class="icon-star  importance-red"></i>
                    <i class="icon-star  importance-red"></i>
                  </span>
                </logic:equal>
              </td>

              <td class="txt_c">
                <span>
                  <bean:write name="prjMdl" property="rate" />%<br>(<bean:write name="prjMdl" property="statusName" />)
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

      <bean:size id="count1" name="prj070Form" property="pageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%= prev %>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="prj070page2" onchange="changePage(2);">
            <html:optionsCollection name="prj070Form" property="pageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="setDateParam();buttonPush('<%= next %>');">
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