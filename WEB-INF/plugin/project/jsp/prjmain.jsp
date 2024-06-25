<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="project.prjmain.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="../project/css/project.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
</head>

<body>
<html:form action="/project/prjmain">
<html:hidden property="prjMainSort" />
<html:hidden property="prjMainOrder" />
<bean:define id="orderKey" name="prjmainForm" property="prjMainOrder" />
<bean:define id="sortKbn" name="prjmainForm" property="prjMainSort" />
<% int iOrderKey = ((Integer) orderKey).intValue(); %>
<% int iSortKbn = ((Integer) sortKbn).intValue(); %>
<%-- ソートオーダー --%>
<% int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC; %>
<% int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;  %>
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
                       jp.groupsession.v2.prj.GSConstProject.SORT_PROJECT_TITLE,
                       jp.groupsession.v2.prj.GSConstProject.SORT_TODO_TITLE,
                       jp.groupsession.v2.prj.GSConstProject.SORT_TODO_STATUS,
                       jp.groupsession.v2.prj.GSConstProject.SORT_TODO_START_PLAN,
                       jp.groupsession.v2.prj.GSConstProject.SORT_TODO_LIMIT_PLAN
                       };
  String[] title_width01 = new String[] { "w25", "w35", "w10", "w15", "w15"};

  String[] titleList01 = new String[] {
                        projectTitle,
                        title,
                        todoStatus,
                        todoStartPlan,
                        todoLimitPlan
                       };
%>

<logic:notEmpty name="prjmainForm" property="projectList" scope="request">
  <table class="table-top w100 mwp300 table_col-even mb0">
    <tr>
      <th class="table_title-color txt_l border_right_none" colspan="2">
        <img src="../project/images/classic/menu_icon_single.gif" class="mainPlugin_icon" alt="<gsmsg:write key="cmn.project" />">
        <a href="<bean:write name="prjmainForm" property="prjTopUrl" />">
          <gsmsg:write key="cmn.project" />
        </a>
      </th>
      <th class="table_title-color txt_r border_left_none" colspan="3">
        <button class="mainBtn" type="button" value="<gsmsg:write key="project.32" />" onClick="location.href='../project/prj050.do?CMD=addTodo'">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="project.32" />">
          <gsmsg:write key="project.32" />
        </button>
      </th>
    </tr>
    <tr>
    <%
      for (int i = 0; i < sortKeyList01.length; i++) {
        if (iSortKbn == sortKeyList01[i]) {
          if (iOrderKey == order_desc) {
    %>
            <th class="table_header-evt js_listPrjSortClick cursor_p p0 pl5 pr5 no_w bgC_header2 <%= title_width01[i] %>" data-target="<%= sortKeyList01[i] %>" data-sort="<%= order_asc %>"<% if (i == 0) {%> scope="col"<% } %>>
              <span class="cl_fontBody no_w">
                <%= titleList01[i] %>
                <span class="classic-display">▼</span>
                <span class="original-display txt_m"><i class="icon-sort_down"></i></span>
              </span>
            </th>
    <%
          } else {
    %>
            <th class="table_header-evt js_listPrjSortClick cursor_p p0 pl5 pr5 no_w bgC_header2 <%= title_width01[i] %>" data-target="<%= sortKeyList01[i] %>" data-sort="<%= order_desc %>"<% if (i == 0) {%> scope="col"<% } %>>
              <span class="cl_fontBody no_w">
                <%= titleList01[i] %>
                <span class="classic-display">▲</span>
                <span class="original-display txt_m"><i class="icon-sort_up"></i></span>
              </span>
            </th>
    <%
          }
        } else {
    %>
          <th class="table_header-evt js_listPrjSortClick cursor_p p0 pl5 pr5 no_w bgC_header2 <%= title_width01[i] %>" data-target="<%= sortKeyList01[i] %>" data-sort="<%= order_asc %>"<% if (i == 0) {%> scope="col"<% } %>>
            <span class="cl_fontBody no_w">
              <%= titleList01[i] %>
            </span>
          </th>
    <%
        }
      }
    %>
    </tr>
    <logic:iterate id="prjMdl" name="prjmainForm" property="projectList" scope="request" indexId="idx">
      <tr>
        <td>
          <a href="../project/prj030.do?prj030scrId=main&prj030prjSid=<bean:write name="prjMdl" property="projectSid" />">
          <logic:equal name="prjMdl" property="prjBinSid" value="0">
            <img src="../project/images/classic/icon_project.png" name="pitctImage" class="btn_classicImg-display" alt="<gsmsg:write key="cmn.icon" />">
            <img class="btn_originalImg-display" src="../project/images/original/plugin_project.png" alt="<gsmsg:write key="cmn.icon" />">
          </logic:equal>
          <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
            <img class="wp30hp30" src="../project/prjmain.do?CMD=getImageFile&prjMainPrjSid=<bean:write name="prjMdl" property="projectSid" />&prjMainPrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
          </logic:notEqual>
          <bean:write name="prjMdl" property="projectName" />
          </a>
        </td>
        <td>
          <logic:equal name="prjMdl" property="keikoku" value="<%= String.valueOf(jp.groupsession.v2.prj.GSConstProject.KEIKOKU_ARI) %>">
            <img src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />" class="btn_classicImg-display">
            <img class="btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
          </logic:equal>
          <a href="../project/prj060.do?prj060scrId=main&prj060prjSid=<bean:write name="prjMdl" property="projectSid" />&prj060todoSid=<bean:write name="prjMdl" property="todoSid" />"><bean:write name="prjMdl" property="todoTitle" filter="false" /></a>
          <logic:equal name="prjMdl" property="prjTodoEdit" value="true">
            <a href="../project/prj050.do?prj050scrId=main&prj050prjSid=<bean:write name="prjMdl" property="projectSid" />&prj050todoSid=<bean:write name="prjMdl" property="todoSid" />&prj050cmdMode=1">
              <img src="../common/images/classic/icon_edit_3.png" class="btn_classicImg-display prj_img_todo_edit" alt="<gsmsg:write key="project.56" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="project.56" />">
            </a>
          </logic:equal>
          <span class="flo_r">
            <logic:notEqual name="prjMdl" property="prjTodoCommentCnt" value="0">
              <a href="../project/prj060.do?prj060scrId=main&prj060prjSid=<bean:write name="prjMdl" property="projectSid" />&prj060todoSid=<bean:write name="prjMdl" property="todoSid" />&prjmvComment=1">
                <img src="../common/images/classic/icon_comment.png" alt="<gsmsg:write key="cmn.comment" />" class="btn_classicImg-display">
                <img class="btn_originalImg-display" src="../common/images/original/icon_comment.png" alt="<gsmsg:write key="cmn.comment" />">
                <bean:write name="prjMdl" property="prjTodoCommentCnt" />
              </a>
            </logic:notEqual>
          </span>
        </td>
        <td class="txt_c">
          <bean:write name="prjMdl" property="rate" />%<br>(<bean:write name="prjMdl" property="statusName" />)
        </td>
        <td class="txt_c">
          <bean:write name="prjMdl" property="strStartDate" />
        </td>
        <td class="txt_c">
          <bean:write name="prjMdl" property="strEndDate" />
        </td>
      </tr>
    </logic:iterate>
  </table>
</logic:notEmpty>

</html:form>

</body>
</html:html>