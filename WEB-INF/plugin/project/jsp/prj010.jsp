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
  String tabProjectClick = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_TAB_PROJECT;
  String tabTodoClick = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_TAB_TODO;
  String prjAdd = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PRJ_ADD;
  String prjMain = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PRJ_TITLE_CLICK;
  String prjSearch = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PRJ_SEARCH;
  String todoAdd = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_TODO_ADD;
  String todoEdit = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_TODO_EDIT;
  String todoRef = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_TODO_TITLE_CLICK;
  String todoSearch = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_TODO_SEARCH;
  String adminConf = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_ADMIN_CONFIG;
  String personalConf = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PERSONAL_CONFIG;
  String prev = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PAGE_PREVEW;
  String next = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PAGE_NEXT;
  String init = jp.groupsession.v2.prj.prj010.Prj010Action.CMD_PAGE_INIT;
%>


<%-- 処理(タブ)モード --%>
<%
  String modeProject = jp.groupsession.v2.prj.GSConstProject.MODE_PROJECT;
  String modeTodo = jp.groupsession.v2.prj.GSConstProject.MODE_TODO;
%>

<%-- ソートオーダー --%>
<%
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<%
  String prj010 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ010;
  String search_ok = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SEARCH_FLG_OK);
  String mode_add = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_ADD;
  String mode_edit = jp.groupsession.v2.prj.GSConstProject.CMD_MODE_EDIT;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/jquery-ui-1.8.16.custom.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj010.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<style type="text/css">

</style>

</head>

<html:form action="/project/prj010">
  <input type="hidden" name="helpPrm" value="<bean:write name="prj010Form" property="prj010cmdMode" />">
  <input type="hidden" name="CMD" id="CMD" value="">
  <html:hidden property="prj010cmdMode" />
  <html:hidden property="prj010sort" />
  <html:hidden property="prj010order" />
  <html:hidden property="prj010Init" />
  <html:hidden property="prj010IeFlg" />
  <html:hidden property="todoKanryoCnt" />
  <html:hidden property="todoMikanryoCnt" />
  <html:hidden property="todoSinkotyuCnt" />
  <html:hidden property="prj070scrId" value="<%=prj010%>" />
  <html:hidden property="prj070searchFlg" value="<%=search_ok%>" />
  <html:hidden property="prj040searchFlg" value="<%=search_ok%>" />
  <html:hidden property="prj050scrId" value="<%=prj010%>" />
  <html:hidden property="prj050cmdMode" />
  <html:hidden property="prj050prjSid" />
  <html:hidden property="prj050todoSid" />
  <html:hidden property="prj020scrId" value="<%=prj010%>" />
  <html:hidden property="prj020cmdMode" value="<%=mode_add%>" />
  <html:hidden property="prj030scrId" value="<%=prj010%>" />
  <html:hidden property="prj030prjSid" value="" />
  <html:hidden property="prj060scrId" value="<%=prj010%>" />
  <html:hidden property="prj060prjSid" />
  <html:hidden property="prj060todoSid" />
  <html:hidden property="prjTmpMode" />
  <input type="hidden" name="prjmvComment">

  <bean:define id="orderKey" name="prj010Form" property="prj010order" />
  <bean:define id="sortKbn" name="prj010Form" property="prj010sort" />
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
            <gsmsg:write key="project.prj010.2" />
          </li>
          <li>
          </li>
        </ul>
      </div>
      <div class="wrapper_2column">

        <div class="border_none mr5">

          <table class="table-top m0 wp300">
            <tr>
              <th>
                <div class="component_bothEnd">
                  <div>
                    <img class="classic-display" src="../project/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.project" />"></img>
                    <img class="original-display" src="../project/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.project" />"></img>
                    <span>
                      <gsmsg:write key="cmn.join.project" />
                    </span>
                  </div>
                  <div
                    <button type="button" class=baseBtn value="<gsmsg:write key="cmn.list" />" onclick="buttonPush('<%=prjSearch%>');">
                     <img class="classic-display" src="../common/images/classic/icon_list.png" alt="<gsmsg:write key="cmn.list" />" ></img>
                      <img class="original-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />" ></img>
                      <gsmsg:write key="cmn.list" />
                    </button>
                  </div>
                </div>
              </th>
            </tr>
            <tr>
              <logic:notEmpty name="prj010Form" property="allProjectList" scope="request">
                <logic:iterate id="prjMdl" name="prj010Form" property="allProjectList" scope="request" indexId="idx">
                  <tr class="js_listHover js_listClick cursor_p" id="<bean:write name="prjMdl" property="projectSid" />">
                    <td>
                      <logic:equal name="prjMdl" property="prjBinSid" value="0">
                      <img class="classic-display" src="../project/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.project" />"></img>
                      <img class="original-display" src="../project/images/original/icon_project.png" alt="<gsmsg:write key="cmn.project" />"></img>
                      </logic:equal>
                      <logic:notEqual name="prjMdl" property="prjBinSid" value="0">
                        <img src="../project/prj010.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prjMdl" property="projectSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" onload="initImageView('pitctImage');" class="prj_img_ico wp20hp20">
                      </logic:notEqual>
                      <span class="cl_linkDef">
                        <bean:write name="prjMdl" property="projectName" />
                      </span>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </tr>
          </table>

          <logic:equal name="prj010Form" property="prj010cmdMode" value="<%=modeTodo%>">

            <bean:define id="kanryo" name="prj010Form" property="todoKanryoCnt" />
            <bean:define id="mikanryo" name="prj010Form" property="todoMikanryoCnt" />
            <bean:define id="sinkotyu" name="prj010Form" property="todoSinkotyuCnt" />

            <%
              int kanryoCnt = ((Integer) kanryo).intValue();
                    int mikanryoCnt = ((Integer) mikanryo).intValue();
                    int sinkotyuCnt = ((Integer) sinkotyu).intValue();
                    int allCnt = kanryoCnt + mikanryoCnt + sinkotyuCnt;

                    if (allCnt > 0) {
            %>
            <logic:notEqual name="prj010Form" property="prj010IeFlg" value="0">
              <script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js?<%=GSConst.VERSION_PARAM%>"></script>
            </logic:notEqual>
            <script type="text/javascript" src="../common/js/graph_circle_1_0_2/graph/circle.js?<%=GSConst.VERSION_PARAM%>"></script>

            <script type="text/javascript">
              window.onload = function() {
                var cg = new html5jp.graph.circle("sample");
                if (!cg) {
                  return;
                }
                var items = [
                    [ "<gsmsg:write key="cmn.complete" />",
                        <bean:write name="kanryo" />,
                        "#0c5bc7" ],
                    [
                        "<gsmsg:write key="rng.application.ongoing" />",
                        <bean:write name="sinkotyu" />,
                        "#d6d4fe" ],
                    [
                        "<gsmsg:write key="project.prj010.8" />",
                        <bean:write name="mikanryo" />,
                        "#828282" ] ];
                var params = {
                  shadow : false,
                  caption : true,
                  legend : false,
                  captionNum : false,
                  startAngle : -45
                };
                cg.draw(items, params);
              };
            </script>

            <br>

            <table class="table-top wp300">
              <tr>
                <th class="txt_l pt10 pb10 pl5">
                  <span>
                    <gsmsg:write key="project.prj010.5" />
                  </span>
                </th>
              </tr>
              <tr>
                <td class="txt_c p0">
                  <div>
                    <canvas id="sample"></canvas>
                  </div>
                </td>
              </tr>
              <tr>
                <td class="p0">

                </div>
                  <ul class="m0 p10 bgC_tableCell" id="legend">
                    <li class="kanryo m0 w5">
                      <span class="categoryName display_inline-block no_w">
                        <gsmsg:write key="cmn.complete" />（<bean:write name="kanryo" /><gsmsg:write key="cmn.number" />）
                      </span>
                    </li>
                    <li class="sinkotyu m0 mt5 w5">
                      <span class="categoryName display_inline-block no_w">
                        <gsmsg:write key="rng.application.ongoing" />（<bean:write name="sinkotyu" /><gsmsg:write key="cmn.number" />）
                      </span>
                    </li>
                    <li class="mikanryo m0 mt5 w5">
                      <span class="categoryName display_inline-block no_w">
                        <gsmsg:write key="project.prj010.8" />（<bean:write name="mikanryo" /><gsmsg:write key="cmn.number" />）
                      </span>
                    </li>
                  </ul>

                </td>
              </tr>
            </table>

            <%
              }
            %>

          </logic:equal>
        </div>
        <div class="main">
          <logic:messagesPresent message="false">
            <html:errors />
          </logic:messagesPresent>

          <logic:equal name="prj010Form" property="prj010cmdMode" value="<%=modeTodo%>">
            <ul class="tabHeader w100">
              <li class="tabHeader_tab-on bgC_lightGray bgI_none mwp100 pl10 pr10 pt5 pb5 txt_c js_tab border_bottom_none" onclick="changeTab('<%=tabTodoClick%>');">TODO</li>
              <li class="tabHeader_tab-off mwp100 pl10 pr10 pt5 pb5 txt_c js_tab" onclick="changeTab('<%=tabProjectClick%>');">
                <gsmsg:write key="cmn.project" />
              </li>
              <li class="tabHeader_space"></li>
            </ul>
          </logic:equal>

          <logic:equal name="prj010Form" property="prj010cmdMode" value="<%=modeProject%>">
            <ul class="tabHeader w100">
              <li class="tabHeader_tab-off mwp100 pl10 pr10 pt5 pb5 txt_c js_tab" onclick="changeTab('<%=tabTodoClick%>');">TODO</li>
              <li class="tabHeader_tab-on mwp100 bgC_lightGray bgI_none pl10 pr10 pt5 pb5 txt_c js_tab border_bottom_none" onclick="changeTab('<%=tabProjectClick%>');">
                <gsmsg:write key="cmn.project" />
              </li>
              <li class="tabHeader_space"></li>
            </ul>
          </logic:equal>

          <logic:equal name="prj010Form" property="prj010cmdMode" value="<%=modeTodo%>">
            <%@ include file="/WEB-INF/plugin/project/jsp/prj010_sub01.jsp"%>
          </logic:equal>
          <logic:equal name="prj010Form" property="prj010cmdMode" value="<%=modeProject%>">
            <%@ include file="/WEB-INF/plugin/project/jsp/prj010_sub02.jsp"%>
          </logic:equal>

          <bean:size id="count1" name="prj010Form" property="pageLabel" scope="request" />
          <logic:greaterThan name="count1" value="1">
            <div class="paging">
              <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo" property="prj010page2" onchange="changePage(2);">
                <html:optionsCollection name="prj010Form" property="pageLabel" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:greaterThan>
        </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

<map name="imagemap">
  <area shape="rect" coords="0,3,97,26" href="javascript:changeTab('<%= tabTodoClick %>');" alt="TODO">
  <area shape="rect" coords="104,3,197,26" href="javascript:changeTab('<%= tabProjectClick %>');" alt="<gsmsg:write key="cmn.project" />">
</map>

</html:form>
</html:html>