<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.prj.model.UserModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html:hidden property="selectingTodoDay" />
<html:hidden property="selectingTodoPrj" />
<html:hidden property="selectingTodoSts" />

<gsmsg:define id="projectName" msgkey="project.40" />
<gsmsg:define id="projectYosan" msgkey="project.10" />
<gsmsg:define id="projectStatus" msgkey="cmn.status" />
<gsmsg:define id="todoWeight" msgkey="project.prj050.4" />
<gsmsg:define id="todoStatus" msgkey="cmn.status" />
<gsmsg:define id="projectStart" msgkey="main.src.man250.29" />
<gsmsg:define id="projectEnd" msgkey="main.src.man250.30" />

<%
  int[] sortKeyList01 = new int[] {
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_ID,
      jp.groupsession.v2.prj.GSConstProject.SORT_PROJECT_NAME,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_YOSAN,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_STATUS,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_START,
      jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_END
  };
  String[] title_width01 = new String[] { "w10", "w40", "w10", "w10", "w10", "w10" };
  String[] titleList01 = new String[] {
      jp.groupsession.v2.prj.GSConstProject.SORT_STR_PRJECT_ID,
      projectName,
      projectYosan,
      projectStatus,
      projectStart,
      projectEnd
  };

  String jkbnToroku = String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU);
%>
<table class="table-left m0 bgC_lightGray border_top_none">
  <%-- includeされるプロジェクトタブモード時の明細 --%>
  <tr>
    <td>
      <div class="component_bothEnd">
        <div>
          <span>
            <gsmsg:write key="cmn.project" />
          </span>
          <logic:notEmpty name="prj010Form" property="targetProjectLabel">
            <html:select styleClass="wp200" property="selectingProject" onchange="return buttonPush('changeCombo');">
              <html:optionsCollection name="prj010Form" property="targetProjectLabel" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
        </div>

        <div>
          <button type="button" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.advanced.search" />" onclick="buttonPush('<%=prjSearch%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <gsmsg:write key="cmn.advanced.search" />
          </button>
          <logic:equal name="prj010Form" property="prjAdd" value="true">
            <button type="button" name="btn_prjadd" class="baseBtn" value="<gsmsg:write key="cmn.new.registration" />" onclick="buttonPush('<%=prjAdd%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
              <gsmsg:write key="cmn.new.registration" />
            </button>
          </logic:equal>
        </div>
      </div>
    </td>
  </tr>
</table>

<!-- ページング -->
<bean:size id="count1" name="prj010Form" property="pageLabel" scope="request" />
<logic:greaterThan name="count1" value="1">
  <div class="paging mt5">
    <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo" property="prj010page1" onchange="changePage(1);">
      <html:optionsCollection name="prj010Form" property="pageLabel" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('next');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
</logic:greaterThan>


<table class="table-top m0 mt5 mb5">
  <logic:notEmpty name="prj010Form" property="projectList" scope="request">
    <tr>
      <%
        for (int i = 0; i < sortKeyList01.length; i++) {

            if (i == 3) {
      %>
      <th class="w10 no_w">
        <span>
          <gsmsg:write key="cmn.admin" />
        </span>
      </th>
      <%
        }

            if (iSortKbn == sortKeyList01[i]) {
              if (iOrderKey == order_desc) {
      %>
      <th class="<%=title_width01[i]%> no_w">
        <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
          <span><%=titleList01[i]%><span class="classic-display">▼</span>
            <span class="original-display">
              <i class="icon-sort_down"></i>
            </span>
          </span>
          </span>
        </a>
      </th>
      <%
        } else {
      %>
      <th class="<%=title_width01[i]%> no_w">
        <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_desc%>);">
          <span><%=titleList01[i]%><span class="classic-display">▲</span>
            <span class="original-display">
              <i class="icon-sort_up"></i>
            </span>
          </span>
        </a>
      </th>
      <%
        }
            } else {
      %>
      <th class="<%=title_width01[i]%> no_w">
        <a href="#!" onClick="return onTitleLinkSubmit(<%=sortKeyList01[i]%>, <%=order_asc%>);">
          <span><%=titleList01[i]%></span>
        </a>
      </th>
      <%
        }
          }
      %>
    </tr>

    <logic:iterate id="prjMdl" name="prj010Form" property="projectList" scope="request" indexId="idx">
      <bean:define id="backclass" value="td_line_color" />
      <bean:define id="backpat" value="<%=String.valueOf((idx.intValue() % 2) + 1)%>" />
      <bean:define id="back" value="<%=String.valueOf(backclass) + String.valueOf(backpat)%>" />

      <tr class="<%=String.valueOf(back)%> js_listHover js_listClick cursor_p cl_linkDef" id="<bean:write name="prjMdl" property="projectSid" />">
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
            <img src="../project/prj010.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prjMdl" property="projectSid" />&prj010PrjBinSid=<bean:write name="prjMdl" property="prjBinSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" onload="initImageView('pitctImage');" class="wp30hp30">
          </logic:notEqual>
            <span>
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
                <span class="<%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>">
                  <logic:equal name="userMdl" property="status" value="<%=jkbnToroku%>">
                    <bean:write name="userMdl" property="sei" />&nbsp;<bean:write name="userMdl" property="mei" />
                  </logic:equal>
                  <logic:notEqual name="userMdl" property="status" value="<%=jkbnToroku%>">
                    <del>
                      <bean:write name="userMdl" property="sei" />
                      &nbsp;
                      <bean:write name="userMdl" property="mei" />
                    </del>
                  </logic:notEqual>
                </span>
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